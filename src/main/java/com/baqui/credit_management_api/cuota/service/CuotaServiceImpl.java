package com.baqui.credit_management_api.cuota.service;

import com.baqui.credit_management_api.credito.entity.Credito;
import com.baqui.credit_management_api.credito.repository.CreditoRepository;
import com.baqui.credit_management_api.cuota.dto.CuotaResponse;
import com.baqui.credit_management_api.cuota.dto.GenerarCuotasRequest;
import com.baqui.credit_management_api.cuota.entity.Cuota;
import com.baqui.credit_management_api.cuota.entity.EstadoCuota;
import com.baqui.credit_management_api.cuota.mapper.CuotaMapper;
import com.baqui.credit_management_api.cuota.repository.CuotaRepository;
import com.baqui.credit_management_api.exeption.ResourceNotFoundException;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CuotaServiceImpl implements CuotaService {
    private static final BigDecimal DOCE =
            BigDecimal.valueOf(12);

    private static final BigDecimal CIEN =
            BigDecimal.valueOf(100);

    private final CuotaRepository cuotaRepository;
    private final CreditoRepository creditoRepository;
    private final CuotaMapper cuotaMapper;

    public CuotaServiceImpl(
            CuotaRepository cuotaRepository,
            CreditoRepository creditoRepository,
            CuotaMapper cuotaMapper
    ) {
        this.cuotaRepository = cuotaRepository;
        this.creditoRepository = creditoRepository;
        this.cuotaMapper = cuotaMapper;
    }

    @Override
    @Transactional
    public List<CuotaResponse> generarCuotas(
            GenerarCuotasRequest request
    ) {

        Credito credito = creditoRepository
                .findById(request.getCreditoId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Crédito no encontrado"
                        )
                );

        if (!"ACTIVO".equals(credito.getEstado())) {
            throw new IllegalArgumentException(
                    "Solo se pueden generar cuotas para créditos activos"
            );
        }

        if (cuotaRepository.existsByCreditoId(
                credito.getCreditoId()
        )) {
            throw new IllegalArgumentException(
                    "El crédito ya tiene cuotas generadas"
            );
        }

        BigDecimal monto = credito.getMontoAprobado();

        BigDecimal tasaMensual = credito
                .getTasaInteres()
                .divide(
                        CIEN,
                        10,
                        RoundingMode.HALF_UP
                )
                .divide(
                        DOCE,
                        10,
                        RoundingMode.HALF_UP
                );

        int plazo = credito.getPlazoMeses();

        BigDecimal cuotaMensual =
                calcularCuotaMensual(
                        monto,
                        tasaMensual,
                        plazo
                );

        List<Cuota> cuotas = new ArrayList<>();

        BigDecimal saldo = monto;

        for (int i = 1; i <= plazo; i++) {

            BigDecimal interes = saldo
                    .multiply(tasaMensual)
                    .setScale(
                            2,
                            RoundingMode.HALF_UP
                    );

            BigDecimal abonoCapital =
                    cuotaMensual.subtract(interes);

            BigDecimal montoCuota = cuotaMensual;

            if (i == plazo) {
                abonoCapital = saldo;

                montoCuota = saldo
                        .add(interes)
                        .setScale(
                                2,
                                RoundingMode.HALF_UP
                        );
            }

            saldo = saldo
                    .subtract(abonoCapital)
                    .setScale(
                            2,
                            RoundingMode.HALF_UP
                    );

            Cuota cuota = new Cuota();

            cuota.setCreditoId(
                    credito.getCreditoId()
            );

            cuota.setNumeroCuota(i);

            cuota.setFechaVencimiento(
                    credito.getFechaInicio()
                            .plusMonths(i)
            );

            cuota.setMontoCuota(montoCuota);

            cuota.setMontoPagado(BigDecimal.ZERO);

            cuota.setSaldoCuota(montoCuota);

            cuota.setEstado(EstadoCuota.PENDIENTE);

            cuotas.add(cuota);
        }

        List<Cuota> guardadas =
                cuotaRepository.saveAll(cuotas);

        return guardadas.stream()
                .map(cuotaMapper::toResponse)
                .toList();
    }

    private BigDecimal calcularCuotaMensual(
            BigDecimal monto,
            BigDecimal tasaMensual,
            int plazo
    ) {

        BigDecimal unoMasTasa =
                BigDecimal.ONE.add(tasaMensual);

        BigDecimal potencia =
                unoMasTasa.pow(plazo);

        BigDecimal numerador =
                monto
                        .multiply(tasaMensual)
                        .multiply(potencia);

        BigDecimal denominador =
                potencia.subtract(BigDecimal.ONE);

        return numerador
                .divide(
                        denominador,
                        2,
                        RoundingMode.HALF_UP
                );
    }

    @Override
    @Transactional
    public List<CuotaResponse> obtenerPorCredito(
            Long creditoId
    ) {

        if (!creditoRepository.existsById(creditoId)) {
            throw new ResourceNotFoundException(
                    "Crédito no encontrado"
            );
        }

        return cuotaRepository
                .findByCreditoIdOrderByNumeroCuotaAsc(
                        creditoId
                )
                .stream()
                .map(cuotaMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public CuotaResponse obtenerPorId(Long id) {

        Cuota cuota = cuotaRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cuota no encontrada"
                        )
                );

        return cuotaMapper.toResponse(cuota);
    }
}