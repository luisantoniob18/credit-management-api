package com.baqui.credit_management_api.cuota.repository;

import com.baqui.credit_management_api.cuota.entity.Cuota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CuotaRepository extends JpaRepository<Cuota, Long> {
    List<Cuota> findByCreditoIdOrderByNumeroCuotaAsc(
            Long creditoId
    );

    boolean existsByCreditoId(Long creditoId);

    long countByCreditoId(Long creditoId);
}
