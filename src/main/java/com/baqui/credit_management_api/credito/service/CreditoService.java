package com.baqui.credit_management_api.credito.service;

import com.baqui.credit_management_api.credito.dto.CreditoRequest;
import com.baqui.credit_management_api.credito.dto.CreditoResponse;

import java.util.List;

public interface CreditoService {
    CreditoResponse crear(CreditoRequest request);

    List<CreditoResponse> obtenerTodos();

    CreditoResponse obtenerPorId(Long id);
}
