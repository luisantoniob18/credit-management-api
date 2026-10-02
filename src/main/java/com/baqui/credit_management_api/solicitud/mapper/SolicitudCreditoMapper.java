package com.baqui.credit_management_api.solicitud.mapper;

import com.baqui.credit_management_api.solicitud.dto.SolicitudCreditoRequest;
import com.baqui.credit_management_api.solicitud.dto.SolicitudCreditoResponse;
import com.baqui.credit_management_api.solicitud.entity.SolicitudCredito;
import org.springframework.stereotype.Component;

@Component
public class SolicitudCreditoMapper {
    public SolicitudCredito toEntity(
            SolicitudCreditoRequest request
    ) {

        SolicitudCredito solicitud = new SolicitudCredito();

        solicitud.setMontoSolicitado(
                request.getMontoSolicitado()
        );

        solicitud.setPlazoMeses(
                request.getPlazoMeses()
        );

        solicitud.setTasaInteres(
                request.getTasaInteres()
        );

        solicitud.setTipoCredito(
                request.getTipoCredito()
        );

        return solicitud;
    }

    public SolicitudCreditoResponse toResponse(
            SolicitudCredito solicitud
    ) {

        SolicitudCreditoResponse response =
                new SolicitudCreditoResponse();

        response.setSolicitudId(
                solicitud.getSolicitudId()
        );

        response.setClienteId(
                solicitud.getCliente().getClienteId()
        );

        response.setNombreCliente(
                solicitud.getCliente().getNombre()
                        + " "
                        + solicitud.getCliente().getApellido()
        );

        response.setMontoSolicitado(
                solicitud.getMontoSolicitado()
        );

        response.setPlazoMeses(
                solicitud.getPlazoMeses()
        );

        response.setTasaInteres(
                solicitud.getTasaInteres()
        );

        response.setTipoCredito(
                solicitud.getTipoCredito()
        );

        response.setEstado(
                solicitud.getEstado()
        );

        response.setFechaSolicitud(
                solicitud.getFechaSolicitud()
        );

        response.setFechaResolucion(
                solicitud.getFechaResolucion()
        );

        return response;
    }
}
