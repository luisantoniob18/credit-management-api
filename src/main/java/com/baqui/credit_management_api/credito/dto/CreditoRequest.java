package com.baqui.credit_management_api.credito.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CreditoRequest {
    @NotNull
    @Positive
    private Long solicitudId;

    public Long getSolicitudId() {
        return solicitudId;
    }

    public void setSolicitudId(Long solicitudId) {
        this.solicitudId = solicitudId;
    }
}
