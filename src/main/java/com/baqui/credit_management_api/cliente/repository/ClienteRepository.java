package com.baqui.credit_management_api.cliente.repository;

import com.baqui.credit_management_api.cliente.entity.Cliente;
import  org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByDpi(String dpi);

    boolean existsByEmail(String email);

    Optional<Cliente> findByDpi(String dpi);

    Optional<Cliente> findByEmail(String email);
}
