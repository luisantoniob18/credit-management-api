package com.baqui.credit_management_api.cuota.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "cuota",
    indexes = {
        @Index(name = "cuotas_pkey", columnList = "cuota_id"),
            @Index(name = "uq_cuota_numero", columnList = "cuota_id, numero_cuota"),
            @Index(name = "idx_cuotas_credito", columnList = "credito_id"),
            @Index(name = "idx_cuotas_estado", columnList = "estado")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cuota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cuota_id")
    private Long cuotaId;

    @Column(name = "credito_id", nullable = false)
    private Long creditoId;

    @NotNull
    @Min(1)
    @Column(name = "numero_cuota", nullable = false)
    private Integer numeroCuota;

    @NotNull
    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @NotNull
    @Positive
    @Column(name = "monto_cuota", nullable = false, precision = 12, scale = 2)
    private BigDecimal montoCuota;

    @NotNull
    @PositiveOrZero
    @Column(name = "monto_pagado", nullable = false, precision = 12, scale = 2)
    @Builder.Default
    private BigDecimal montoPagado = BigDecimal.ZERO;

    @NotNull
    @PositiveOrZero
    @Column(name = "saldo_cuota", nullable = false, precision = 12, scale = 2)
    private BigDecimal saldoCuota;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    @Builder.Default
    private EstadoCuota estado = EstadoCuota.PENDIENTE;

}
