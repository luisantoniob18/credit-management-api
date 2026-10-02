package com.baqui.credit_management_api.solicitud.service;

import com.baqui.credit_management_api.cliente.entity.Cliente;
import com.baqui.credit_management_api.cliente.repository.ClienteRepository;
import com.baqui.credit_management_api.exeption.ResourceNotFoundException;
import com .baqui.credit_management_api.solicitud.dto.SolicitudCreditoRequest;
import com.baqui.credit_management_api.solicitud.dto.SolicitudCreditoResponse;
import com.baqui.credit_management_api.solicitud.entity.SolicitudCredito;
import com.baqui.credit_management_api.solicitud.mapper.SolicitudCreditoMapper;
import com.baqui.credit_management_api.solicitud.repository.SolicitudCreditoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class SolicitudCreditoServiceImpl implements SolicitudCreditoService {
    private final SolicitudCreditoRepository solicitudRepository;
    private final ClienteRepository clienteRepository;
    private final SolicitudCreditoMapper mapper;

    public SolicitudCreditoServiceImpl(
            SolicitudCreditoRepository solicitudRepository,
            ClienteRepository clienteRepository,
            SolicitudCreditoMapper mapper
    ) {
        this.solicitudRepository = solicitudRepository;
        this.clienteRepository = clienteRepository;
        this.mapper = mapper;
    }

    @Override
    public SolicitudCreditoResponse crear(
            SolicitudCreditoRequest request
    ) {

        Cliente cliente = clienteRepository
                .findById(request.getClienteId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cliente no encontrado con id: "
                                        + request.getClienteId()
                        )
                );

        if (!"ACTIVO".equals(cliente.getEstado())) {
            throw new IllegalArgumentException(
                    "El cliente no está activo"
            );
        }

        SolicitudCredito solicitud =
                mapper.toEntity(request);

        solicitud.setCliente(cliente);
        solicitud.setEstado("PENDIENTE");

        SolicitudCredito guardada =
                solicitudRepository.save(solicitud);

        return mapper.toResponse(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SolicitudCreditoResponse> obtenerTodos() {

        return solicitudRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SolicitudCreditoResponse obtenerPorId(Long id) {

        SolicitudCredito solicitud =
                buscarSolicitud(id);

        return mapper.toResponse(solicitud);
    }

    @Override
    public SolicitudCreditoResponse enviarARevision(Long id) {

        SolicitudCredito solicitud =
                buscarSolicitud(id);

        validarEstadoParaRevision(solicitud);

        solicitud.setEstado("EN_REVISION");

        return mapper.toResponse(solicitud);
    }

    @Override
    public SolicitudCreditoResponse aprobar(Long id) {

        SolicitudCredito solicitud =
                buscarSolicitud(id);

        if (!"EN_REVISION".equals(solicitud.getEstado())) {
            throw new IllegalArgumentException(
                    "Solo se pueden aprobar solicitudes en revisión"
            );
        }

        solicitud.setEstado("APROBADA");
        solicitud.setFechaResolucion(
                LocalDateTime.now()
        );

        return mapper.toResponse(solicitud);
    }

    @Override
    public SolicitudCreditoResponse rechazar(Long id) {

        SolicitudCredito solicitud =
                buscarSolicitud(id);

        if (!"EN_REVISION".equals(solicitud.getEstado())) {
            throw new IllegalArgumentException(
                    "Solo se pueden rechazar solicitudes en revisión"
            );
        }

        solicitud.setEstado("RECHAZADA");
        solicitud.setFechaResolucion(
                LocalDateTime.now()
        );

        return mapper.toResponse(solicitud);
    }

    private SolicitudCredito buscarSolicitud(Long id) {

        return solicitudRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Solicitud no encontrada con id: "
                                        + id
                        )
                );
    }

    private void validarEstadoParaRevision(
            SolicitudCredito solicitud
    ) {

        if (!"PENDIENTE".equals(solicitud.getEstado())) {

            throw new IllegalArgumentException(
                    "Solo las solicitudes pendientes "
                            + "pueden pasar a revisión"
            );
        }
    }
}
