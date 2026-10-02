package com.baqui.credit_management_api.credito.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
        name = "creditos",
        indexes = {
                @Index(name = "creditos_pkey", columnList = "credito_id"),
                @Index(name = "creditos_solicitud_id_key", columnList = "solicitud_id"),
                @Index(name = "idx_creditos_cliente", columnList = "cliente_id"),
                @Index(name = "idx_creditos_estado", columnList = "estado")
        }
)
public class Credito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credito_id")
    private Long creditoId;

    @Column(name = "solicitud_id", nullable = false, unique = true)
    private Long solicitudId;

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @Column(name = "monto_aprobado", nullable = false,
            precision = 12,
            scale = 2)
    private BigDecimal montoAprobado;

    @Column(name = "saldo_actual", nullable = false,
            precision = 12,
            scale = 2)
    private BigDecimal saldoActual;

    @Column(name = "plazo_meses", nullable = false)
    private Integer plazoMeses;

    @Column(
            name = "tasa_interes",
            nullable = false,
            precision = 5,
            scale = 2
    )
    private BigDecimal tasaInteres;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado = "ACTIVO";


    public Long getCreditoId() {
        return creditoId;
    }

    public void setCreditoId(Long creditoId) {
        this.creditoId = creditoId;
    }

    public Long getSolicitudId() {
        return solicitudId;
    }

    public void setSolicitudId(Long solicitudId) {
        this.solicitudId = solicitudId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public BigDecimal getMontoAprobado() {
        return montoAprobado;
    }

    public void setMontoAprobado(BigDecimal montoAprobado) {
        this.montoAprobado = montoAprobado;
    }

    public BigDecimal getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(BigDecimal saldoActual) {
        this.saldoActual = saldoActual;
    }

    public Integer getPlazoMeses() {
        return plazoMeses;
    }

    public void setPlazoMeses(Integer plazoMeses) {
        this.plazoMeses = plazoMeses;
    }

    public BigDecimal getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(BigDecimal tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
