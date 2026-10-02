package com.baqui.credit_management_api.credito.mapper;

import com.baqui.credit_management_api.cliente.entity.Cliente;
import com.baqui.credit_management_api.credito.dto.CreditoResponse;
import com.baqui.credit_management_api.credito.entity.Credito;
import org.springframework.stereotype.Component;

@Component
public class CreditoMapper {
    public CreditoResponse toResponse(
            Credito credito,
            Cliente cliente
    ) {

        CreditoResponse response = new CreditoResponse();

        response.setCreditoId(credito.getCreditoId());
        response.setSolicitudId(credito.getSolicitudId());
        response.setClienteId(credito.getClienteId());

        response.setNombreCliente(
                cliente.getNombre() + " " + cliente.getApellido()
        );

        response.setMontoAprobado(credito.getMontoAprobado());
        response.setSaldoActual(credito.getSaldoActual());
        response.setPlazoMeses(credito.getPlazoMeses());
        response.setTasaInteres(credito.getTasaInteres());
        response.setFechaInicio(credito.getFechaInicio());
        response.setFechaFin(credito.getFechaFin());
        response.setEstado(credito.getEstado());

        return response;
    }
}
