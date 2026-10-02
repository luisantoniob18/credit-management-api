package com.baqui.credit_management_api.cuota.service;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
        name = "cuotas",
        indexes = {
                @Index(name = "cuotas_pkey", columnList = "cuota_id"),
                @Index(name = "idx_cuotas_credito", columnList = "credito_id"),
                @Index(name = "idx_cuotas_estado", columnList = "estado")
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_cuota_credito_numero",
                        columnNames = {"credito_id", "numero_cuota"}
                )
        }
)
public class CuotaServiceImpl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cuota_id")
    private Long cuotaId;

    @Column(name = "credito_id", nullable = false)
    private Long creditoId;

    @Column(name = "numero_cuota", nullable = false)
    private Integer numeroCuota;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @Column(
            name = "monto_cuota",
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal montoCuota;

    @Column(
            name = "monto_pagado",
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal montoPagado = BigDecimal.ZERO;

    @Column(
            name = "saldo_cuota",
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal saldoCuota;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado = "PENDIENTE";

    public Long getCuotaId() {
        return cuotaId;
    }

    public void setCuotaId(Long cuotaId) {
        this.cuotaId = cuotaId;
    }

    public Long getCreditoId() {
        return creditoId;
    }

    public void setCreditoId(Long creditoId) {
        this.creditoId = creditoId;
    }

    public Integer getNumeroCuota() {
        return numeroCuota;
    }

    public void setNumeroCuota(Integer numeroCuota) {
        this.numeroCuota = numeroCuota;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public BigDecimal getMontoCuota() {
        return montoCuota;
    }

    public void setMontoCuota(BigDecimal montoCuota) {
        this.montoCuota = montoCuota;
    }

    public BigDecimal getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(BigDecimal montoPagado) {
        this.montoPagado = montoPagado;
    }

    public BigDecimal getSaldoCuota() {
        return saldoCuota;
    }

    public void setSaldoCuota(BigDecimal saldoCuota) {
        this.saldoCuota = saldoCuota;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
