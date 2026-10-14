package com.baqui.credit_management_api.pago.service;

import com.baqui.credit_management_api.credito.entity.Credito;
import com.baqui.credit_management_api.credito.repository.CreditoRepository;
import com.baqui.credit_management_api.cuota.entity.Cuota;
import com.baqui.credit_management_api.cuota.entity.EstadoCuota;
import com.baqui.credit_management_api.cuota.repository.CuotaRepository;
import com.baqui.credit_management_api.exeption.ResourceNotFoundException;
import com.baqui.credit_management_api.pago.dto.PagoRequest;
import com.baqui.credit_management_api.pago.dto.PagoResponse;
import com.baqui.credit_management_api.pago.entity.Pago;
import com.baqui.credit_management_api.pago.mapper.PagoMapper;
import com.baqui.credit_management_api.pago.repository.PagoRepository;
import jakarta.transaction.Transactional;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PagoServiceImpl implements PagoService {
    private final PagoRepository pagoRepository;
    private final CuotaRepository cuotaRepository;
    private final CreditoRepository creditoRepository;
    private final PagoMapper pagoMapper;

    public PagoServiceImpl(
            PagoRepository pagoRepository,
            CuotaRepository cuotaRepository,
            CreditoRepository creditoRepository,
            PagoMapper pagoMapper
    ) {
        this.pagoRepository = pagoRepository;
        this.cuotaRepository = cuotaRepository;
        this.creditoRepository = creditoRepository;
        this.pagoMapper = pagoMapper;
    }

    @Override
    @Transactional
    public PagoResponse registrar(PagoRequest request) {

        Cuota cuota = cuotaRepository
                .findById(request.getCuotaId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cuota no encontrada"
                        )
                );

        Credito credito = creditoRepository
                .findById(cuota.getCreditoId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Crédito no encontrado"
                        )
                );

        if (!"ACTIVO".equals(credito.getEstado())) {
            throw new IllegalArgumentException(
                    "El crédito no está activo"
            );
        }

        if ("PAGADA".equals(cuota.getEstado())) {
            throw new IllegalArgumentException(
                    "La cuota ya está pagada"
            );
        }

        BigDecimal montoPago = request.getMonto();

        if (montoPago.compareTo(
                cuota.getSaldoCuota()
        ) > 0) {

            throw new IllegalArgumentException(
                    "El monto del pago excede el saldo de la cuota"
            );
        }

        BigDecimal nuevoMontoPagado =
                cuota.getMontoPagado()
                        .add(montoPago);

        BigDecimal nuevoSaldoCuota =
                cuota.getSaldoCuota()
                        .subtract(montoPago);

        cuota.setMontoPagado(
                nuevoMontoPagado
        );

        cuota.setSaldoCuota(
                nuevoSaldoCuota
        );

        if (nuevoSaldoCuota.compareTo(
                BigDecimal.ZERO
        ) == 0) {

            cuota.setEstado(EstadoCuota.PAGADA);

        } else {

            cuota.setEstado(EstadoCuota.PARCIAL);
        }

        cuotaRepository.save(cuota);

        BigDecimal nuevoSaldoCredito =
                credito.getSaldoActual()
                        .subtract(montoPago);

        credito.setSaldoActual(
                nuevoSaldoCredito
        );

        if (nuevoSaldoCredito.compareTo(
                BigDecimal.ZERO
        ) == 0) {

            credito.setEstado("PAGADO");
        }

        creditoRepository.save(credito);

        Pago pago = new Pago();

        pago.setCuotaId(
                cuota.getCuotaId()
        );

        pago.setCreditoId(
                credito.getCreditoId()
        );

        pago.setMonto(montoPago);

        pago.setFechaPago(
                LocalDateTime.now()
        );

        pago.setMetodoPago(
                request.getMetodoPago()
        );

        pago.setReferencia(
                request.getReferencia()
        );

        Pago guardado =
                pagoRepository.save(pago);

        PagoResponse response =
                pagoMapper.toResponse(guardado);

        response.setSaldoCuota(
                cuota.getSaldoCuota()
        );

        response.setEstadoCuota(
                cuota.getEstado()
        );

        response.setSaldoCredito(
                credito.getSaldoActual()
        );

        response.setEstadoCredito(
                credito.getEstado()
        );

        return response;
    }

    @Override
    @Transactional
    public List<PagoResponse> obtenerPorCredito(
            Long creditoId
    ) {
        if (!creditoRepository.existsById(creditoId)) {
            throw new ResourceNotFoundException(
                    "Crédito no encontrado"
            );
        }

        return pagoRepository
                .findByCreditoIdOrderByFechaPagoDesc(
                        creditoId
                )
                .stream()
                .map(pagoMapper::toResponse)
                .toList();
    }

    ;

    @Override
    @Transactional
    public List<PagoResponse> obtenerPorCuota(
            Long cuotaId
    ) {

        if (!cuotaRepository.existsById(cuotaId)) {
            throw new ResourceNotFoundException(
                    "Cuota no encontrada"
            );
        }

        return pagoRepository
                .findByCuotaIdOrderByFechaPagoDesc(
                        cuotaId
                )
                .stream()
                .map(pagoMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public PagoResponse obtenerPorId(Long id) {

        Pago pago = pagoRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Pago no encontrado"
                        )
                );

        return pagoMapper.toResponse(pago);
    }
}
