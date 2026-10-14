package com.baqui.credit_management_api.pago.repository;

import com.baqui.credit_management_api.pago.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByCreditoIdOrderByFechaPagoDesc(
            Long creditoId
    );

    List<Pago> findByCuotaIdOrderByFechaPagoDesc(
            Long cuotaId
    );

    List<Pago> findByFechaPagoBetween(
            LocalDate inicio,
            LocalDate fin
    );
}
