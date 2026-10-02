package com.baqui.credit_management_api.solicitud.service;

import com.baqui.credit_management_api.solicitud.dto.SolicitudCreditoRequest;
import com.baqui.credit_management_api.solicitud.dto.SolicitudCreditoResponse;

import java.util.List;
public interface SolicitudCreditoService {
    SolicitudCreditoResponse crear(
            SolicitudCreditoRequest request
    );

    List<SolicitudCreditoResponse> obtenerTodos();

    SolicitudCreditoResponse obtenerPorId(Long id);

    SolicitudCreditoResponse enviarARevision(Long id);

    SolicitudCreditoResponse aprobar(Long id);

    SolicitudCreditoResponse rechazar(Long id);
}
