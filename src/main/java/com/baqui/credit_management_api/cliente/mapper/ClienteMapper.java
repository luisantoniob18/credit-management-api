package com.baqui.credit_management_api.cliente.mapper;

import com.baqui.credit_management_api.cliente.dto.ClienteRequest;
import com.baqui.credit_management_api.cliente.dto.ClienteResponse;
import com.baqui.credit_management_api.cliente.entity.Cliente;
import org.springframework.stereotype.Component;
@Component
public class ClienteMapper {
    public Cliente toEntity(ClienteRequest request) {

        Cliente cliente = new Cliente();

        cliente.setDpi(request.getDpi());
        cliente.setNombre(request.getNombre());
        cliente.setApellido(request.getApellido());
        cliente.setEmail(request.getEmail());
        cliente.setTelefono(request.getTelefono());
        cliente.setFechaNacimiento(request.getFechaNacimiento());
        cliente.setDireccion(request.getDireccion());

        return cliente;
    }

    public ClienteResponse toResponse(Cliente cliente) {

        ClienteResponse response = new ClienteResponse();

        response.setClienteId(cliente.getClienteId());
        response.setDpi(cliente.getDpi());
        response.setNombre(cliente.getNombre());
        response.setApellido(cliente.getApellido());
        response.setEmail(cliente.getEmail());
        response.setTelefono(cliente.getTelefono());
        response.setFechaNacimiento(cliente.getFechaNacimiento());
        response.setDireccion(cliente.getDireccion());
        response.setEstado(cliente.getEstado());
        response.setFechaRegistro(cliente.getFechaRegistro());

        return response;
    }

    public void updateEntity(
            Cliente cliente,
            ClienteRequest request
    ) {

        cliente.setDpi(request.getDpi());
        cliente.setNombre(request.getNombre());
        cliente.setApellido(request.getApellido());
        cliente.setEmail(request.getEmail());
        cliente.setTelefono(request.getTelefono());
        cliente.setFechaNacimiento(request.getFechaNacimiento());
        cliente.setDireccion(request.getDireccion());
    }
}
