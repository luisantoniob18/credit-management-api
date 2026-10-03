package com.baqui.credit_management_api.cuota.controller;

import com.baqui.credit_management_api.cuota.dto.CuotaResponse;
import com.baqui.credit_management_api.cuota.dto.GenerarCuotasRequest;
import com.baqui.credit_management_api.cuota.service.CuotaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuotas")
public class CuotaController {
    private final CuotaService cuotaService;

    public CuotaController(CuotaService cuotaService) {
        this.cuotaService = cuotaService;
    }

    @PostMapping("/generar")
    @ResponseStatus(HttpStatus.CREATED)
    public List<CuotaResponse> generarCuotas(
            @Valid @RequestBody GenerarCuotasRequest request
    ) {
        return cuotaService.generarCuotas(request);
    }

    @GetMapping("/credito/{creditoId}")
    public List<CuotaResponse> obtenerPorCredito(
            @PathVariable Long creditoId
    ) {
        return cuotaService.obtenerPorCredito(
                creditoId
        );
    }

    @GetMapping("/{id}")
    public CuotaResponse obtenerPorId(
            @PathVariable Long id
    ) {
        return cuotaService.obtenerPorId(id);
    }
}
