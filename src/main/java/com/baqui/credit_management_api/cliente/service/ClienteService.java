package com.baqui.credit_management_api.cliente.service;

import com.baqui.credit_management_api.cliente.dto.ClienteRequest;
import com.baqui.credit_management_api.cliente.dto.ClienteResponse;

import java.util.List;
public interface ClienteService {
    ClienteResponse crear(ClienteRequest request);

    List<ClienteResponse> obtenerTodos();

    ClienteResponse obtenerPorId(Long id);

    ClienteResponse actualizar(Long id, ClienteRequest request);

    void eliminar(Long id);
}
