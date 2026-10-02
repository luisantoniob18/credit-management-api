package com.baqui.credit_management_api.cliente.service;

import com.baqui.credit_management_api.cliente.dto.ClienteRequest;
import com.baqui.credit_management_api.cliente.dto.ClienteResponse;
import com.baqui.credit_management_api.cliente.entity.Cliente;
import com.baqui.credit_management_api.cliente.mapper.ClienteMapper;
import com.baqui.credit_management_api.cliente.repository.ClienteRepository;
import com.baqui.credit_management_api.exeption.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService{
    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public ClienteServiceImpl(
            ClienteRepository clienteRepository,
            ClienteMapper clienteMapper
    ) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    @Override
    public ClienteResponse crear(ClienteRequest request) {

        if (clienteRepository.existsByDpi(request.getDpi())) {
            throw new IllegalArgumentException(
                    "Ya existe un cliente con el DPI proporcionado"
            );
        }

        if (request.getEmail() != null
                && clienteRepository.existsByEmail(request.getEmail())) {

            throw new IllegalArgumentException(
                    "Ya existe un cliente con el email proporcionado"
            );
        }

        Cliente cliente = clienteMapper.toEntity(request);

        Cliente clienteGuardado = clienteRepository.save(cliente);

        return clienteMapper.toResponse(clienteGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponse> obtenerTodos() {

        return clienteRepository.findAll()
                .stream()
                .map(clienteMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponse obtenerPorId(Long id) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cliente no encontrado con id: " + id
                        )
                );

        return clienteMapper.toResponse(cliente);
    }

    @Override
    public ClienteResponse actualizar(
            Long id,
            ClienteRequest request
    ) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cliente no encontrado con id: " + id
                        )
                );

        if (!cliente.getDpi().equals(request.getDpi())
                && clienteRepository.existsByDpi(request.getDpi())) {

            throw new IllegalArgumentException(
                    "Ya existe otro cliente con el DPI proporcionado"
            );
        }

        if (request.getEmail() != null
                && !request.getEmail().equals(cliente.getEmail())
                && clienteRepository.existsByEmail(request.getEmail())) {

            throw new IllegalArgumentException(
                    "Ya existe otro cliente con el email proporcionado"
            );
        }

        clienteMapper.updateEntity(cliente, request);

        Cliente actualizado = clienteRepository.save(cliente);

        return clienteMapper.toResponse(actualizado);
    }

    @Override
    public void eliminar(Long id) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cliente no encontrado con id: " + id
                        )
                );

        clienteRepository.delete(cliente);
    }
}
