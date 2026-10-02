package com.baqui.credit_management_api.cuota.service;

import com.baqui.credit_management_api.cuota.dto.CuotaResponse;
import com.baqui.credit_management_api.cuota.dto.GenerarCuotasRequest;

import java.util.List;

public interface CuotaService {
    List<CuotaResponse> generarCuotas(
            GenerarCuotasRequest request
    );

    List<CuotaResponse> obtenerPorCredito(
            Long creditoId
    );

    CuotaResponse obtenerPorId(Long id);
}
