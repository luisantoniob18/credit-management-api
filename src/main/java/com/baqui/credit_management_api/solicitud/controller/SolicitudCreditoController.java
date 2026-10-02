package com.baqui.credit_management_api.solicitud.controller;

import com.baqui.credit_management_api.solicitud.dto.SolicitudCreditoRequest;
import com.baqui.credit_management_api.solicitud.dto.SolicitudCreditoResponse;
import com.baqui.credit_management_api.solicitud.service.SolicitudCreditoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudCreditoController {
    private final SolicitudCreditoService solicitudService;

    public SolicitudCreditoController(
            SolicitudCreditoService solicitudService
    ) {
        this.solicitudService = solicitudService;
    }

    @PostMapping
    public ResponseEntity<SolicitudCreditoResponse> crear(
            @Valid @RequestBody SolicitudCreditoRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(solicitudService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<SolicitudCreditoResponse>>
    obtenerTodos() {

        return ResponseEntity.ok(
                solicitudService.obtenerTodos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitudCreditoResponse>
    obtenerPorId(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                solicitudService.obtenerPorId(id)
        );
    }

    @PutMapping("/{id}/revision")
    public ResponseEntity<SolicitudCreditoResponse>
    enviarARevision(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                solicitudService.enviarARevision(id)
        );
    }

    @PutMapping("/{id}/aprobar")
    public ResponseEntity<SolicitudCreditoResponse>
    aprobar(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                solicitudService.aprobar(id)
        );
    }

    @PutMapping("/{id}/rechazar")
    public ResponseEntity<SolicitudCreditoResponse>
    rechazar(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                solicitudService.rechazar(id)
        );
    }
}
