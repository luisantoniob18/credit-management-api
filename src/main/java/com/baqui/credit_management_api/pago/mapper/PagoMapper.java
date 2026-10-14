package com.baqui.credit_management_api.pago.mapper;

import com.baqui.credit_management_api.pago.dto.PagoResponse;
import com.baqui.credit_management_api.pago.entity.Pago;
import org.springframework.stereotype.Component;

@Component
public class PagoMapper {
    public PagoResponse toResponse(Pago pago) {

        PagoResponse response = new PagoResponse();

        response.setPagoId(pago.getPagoId());
        response.setCuotaId(pago.getCuotaId());
        response.setCreditoId(pago.getCreditoId());
        response.setMonto(pago.getMonto());
        response.setFechaPago(pago.getFechaPago());
        response.setMetodoPago(pago.getMetodoPago());
        response.setReferencia(pago.getReferencia());

        return response;
    }
}
