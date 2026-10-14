package com.baqui.credit_management_api.pago.service;

import com.baqui.credit_management_api.pago.dto.PagoRequest;
import com.baqui.credit_management_api.pago.dto.PagoResponse;

import java.util.List;

public interface PagoService {
    PagoResponse registrar(PagoRequest request);

    List<PagoResponse> obtenerPorCredito(
            Long creditoId
    );

    List<PagoResponse> obtenerPorCuota(
            Long cuotaId
    );

    PagoResponse obtenerPorId(Long id);
}
