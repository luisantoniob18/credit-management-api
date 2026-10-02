package com.baqui.credit_management_api.solicitud.repository;

import com.baqui.credit_management_api.solicitud.entity.SolicitudCredito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface SolicitudCreditoRepository extends JpaRepository<SolicitudCredito, Long> {
    List<SolicitudCredito> findByClienteClienteId(Long clienteId);

    List<SolicitudCredito> findByEstado(String estado);

    boolean existsByClienteClienteIdAndEstado(
            Long clienteId,
            String estado
    );
}
