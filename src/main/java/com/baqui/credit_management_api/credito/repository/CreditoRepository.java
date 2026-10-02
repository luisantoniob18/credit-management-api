package com.baqui.credit_management_api.credito.repository;

import com.baqui.credit_management_api.credito.entity.Credito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CreditoRepository extends JpaRepository<Credito, Long> {
    Optional<Credito> findBySolicitudId(Long solicitudId);

    List<Credito> findByClienteId(Long clienteId);

    List<Credito> findByEstado(String estado);

    boolean existsBySolicitudId(Long solicitudId);
}
