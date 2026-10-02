package com.baqui.credit_management_api.cuota.mapper;

import com.baqui.credit_management_api.cuota.dto.CuotaResponse;
import com.baqui.credit_management_api.cuota.entity.Cuota;
import org.springframework.stereotype.Component;

@Component
public class CuotaMapper {
    public CuotaResponse toResponse(Cuota cuota) {

        CuotaResponse response = new CuotaResponse();

        response.setCuotaId(cuota.getCuotaId());
        response.setCreditoId(cuota.getCreditoId());
        response.setNumeroCuota(cuota.getNumeroCuota());
        response.setFechaVencimiento(
                cuota.getFechaVencimiento()
        );
        response.setMontoCuota(cuota.getMontoCuota());
        response.setMontoPagado(cuota.getMontoPagado());
        response.setSaldoCuota(cuota.getSaldoCuota());
        response.setEstado(cuota.getEstado());

        return response;
    }
}
