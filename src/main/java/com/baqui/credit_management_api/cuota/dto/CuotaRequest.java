package com.baqui.credit_management_api.cuota.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CuotaRequest {
    @NotNull
    @Positive
    private Long creditoId;

    public Long getCreditoId() {
        return creditoId;
    }

    public void setCreditoId(Long creditoId) {
        this.creditoId = creditoId;
    }
}
