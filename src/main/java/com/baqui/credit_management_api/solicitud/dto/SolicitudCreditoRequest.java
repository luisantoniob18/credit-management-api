package com.baqui.credit_management_api.solicitud.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
public class SolicitudCreditoRequest {
    @NotNull(message = "El cliente es obligatorio")
    @Positive(message = "El ID del cliente debe ser positivo")
    private Long clienteId;

    @NotNull(message = "El monto solicitado es obligatorio")
    @DecimalMin(
            value = "0.01",
            message = "El monto debe ser mayor que 0"
    )
    private BigDecimal montoSolicitado;

    @NotNull(message = "El plazo es obligatorio")
    @Min(value = 6, message = "El plazo mínimo es de 6 meses")
    @Max(value = 60, message = "El plazo máximo es de 60 meses")
    private Integer plazoMeses;

    @NotNull(message = "La tasa de interés es obligatoria")
    @DecimalMin(
            value = "0.00",
            message = "La tasa no puede ser negativa"
    )
    private BigDecimal tasaInteres;

    @NotBlank(message = "El tipo de crédito es obligatorio")
    private String tipoCredito;

    public SolicitudCreditoRequest() {
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public BigDecimal getMontoSolicitado() {
        return montoSolicitado;
    }

    public void setMontoSolicitado(BigDecimal montoSolicitado) {
        this.montoSolicitado = montoSolicitado;
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

    public String getTipoCredito() {
        return tipoCredito;
    }

    public void setTipoCredito(String tipoCredito) {
        this.tipoCredito = tipoCredito;
    }
}
