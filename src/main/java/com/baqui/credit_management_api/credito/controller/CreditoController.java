package com.baqui.credit_management_api.credito.controller;

import com.baqui.credit_management_api.credito.dto.CreditoRequest;
import com.baqui.credit_management_api.credito.dto.CreditoResponse;
import com.baqui.credit_management_api.credito.service.CreditoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/creditos")
public class CreditoController {
    private final CreditoService creditoService;

    public CreditoController(CreditoService creditoService) {
        this.creditoService = creditoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreditoResponse crear(
            @Valid @RequestBody CreditoRequest request
    ) {
        return creditoService.crear(request);
    }

    @GetMapping
    public List<CreditoResponse> obtenerTodos() {
        return creditoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public CreditoResponse obtenerPorId(
            @PathVariable Long id
    ) {
        return creditoService.obtenerPorId(id);
    }
}
