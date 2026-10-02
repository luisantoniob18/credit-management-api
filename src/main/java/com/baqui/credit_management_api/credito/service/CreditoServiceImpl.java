package com.baqui.credit_management_api.credito.service;

import com.baqui.credit_management_api.cliente.entity.Cliente;
import com.baqui.credit_management_api.cliente.repository.ClienteRepository;
import com.baqui.credit_management_api.credito.dto.CreditoRequest;
import com.baqui.credit_management_api.credito.dto.CreditoResponse;
import com.baqui.credit_management_api.credito.entity.Credito;
import com.baqui.credit_management_api.credito.mapper.CreditoMapper;
import com.baqui.credit_management_api.credito.repository.CreditoRepository;
import com.baqui.credit_management_api.exeption.ResourceNotFoundException;
import com.baqui.credit_management_api.solicitud.entity.SolicitudCredito;
import com.baqui.credit_management_api.solicitud.repository.SolicitudCreditoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CreditoServiceImpl implements CreditoService{
    private final CreditoRepository creditoRepository;
    private final SolicitudCreditoRepository solicitudRepository;
    private final ClienteRepository clienteRepository;
    private final CreditoMapper creditoMapper;

    public CreditoServiceImpl(
            CreditoRepository creditoRepository,
            SolicitudCreditoRepository solicitudRepository,
            ClienteRepository clienteRepository,
            CreditoMapper creditoMapper
    ) {
        this.creditoRepository = creditoRepository;
        this.solicitudRepository = solicitudRepository;
        this.clienteRepository = clienteRepository;
        this.creditoMapper = creditoMapper;
    }

    @Override
    @Transactional
    public CreditoResponse crear(CreditoRequest request) {

        SolicitudCredito solicitud = solicitudRepository
                .findById(request.getSolicitudId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Solicitud no encontrada"
                        )
                );

        if (!"APROBADA".equals(solicitud.getEstado())) {
            throw new IllegalArgumentException(
                    "La solicitud debe estar APROBADA para crear el crédito"
            );
        }

        if (creditoRepository.existsBySolicitudId(
                request.getSolicitudId()
        )) {
            throw new IllegalArgumentException(
                    "La solicitud ya tiene un crédito asociado"
            );
        }

        Cliente cliente = clienteRepository
                .findById(solicitud.getCliente().getClienteId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cliente no encontrado"
                        )
                );

        if (!"ACTIVO".equals(cliente.getEstado())) {
            throw new IllegalArgumentException(
                    "El cliente no está activo"
            );
        }

        LocalDate fechaInicio = LocalDate.now();

        LocalDate fechaFin = fechaInicio.plusMonths(
                solicitud.getPlazoMeses()
        );

        Credito credito = new Credito();

        credito.setSolicitudId(solicitud.getSolicitudId());
        credito.setClienteId(cliente.getClienteId());
        credito.setMontoAprobado(solicitud.getMontoSolicitado());
        credito.setSaldoActual(solicitud.getMontoSolicitado());
        credito.setPlazoMeses(solicitud.getPlazoMeses());
        credito.setTasaInteres(solicitud.getTasaInteres());
        credito.setFechaInicio(fechaInicio);
        credito.setFechaFin(fechaFin);
        credito.setEstado("ACTIVO");

        Credito guardado = creditoRepository.save(credito);

        return creditoMapper.toResponse(guardado, cliente);
    }

    @Override
    @Transactional
    public List<CreditoResponse> obtenerTodos() {

        return creditoRepository.findAll()
                .stream()
                .map(credito -> {

                    Cliente cliente = clienteRepository
                            .findById(credito.getClienteId())
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Cliente no encontrado"
                                    )
                            );

                    return creditoMapper.toResponse(
                            credito,
                            cliente
                    );
                })
                .toList();
    }

    @Override
    @Transactional
    public CreditoResponse obtenerPorId(Long id) {

        Credito credito = creditoRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Crédito no encontrado"
                        )
                );

        Cliente cliente = clienteRepository
                .findById(credito.getClienteId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cliente no encontrado"
                        )
                );

        return creditoMapper.toResponse(
                credito,
                cliente
        );
    }
}
