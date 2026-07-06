package com.concurseirodf.backend.api.controller;

import com.concurseirodf.backend.api.dto.ProgressoDTO;
import com.concurseirodf.backend.domain.entity.Usuario;
import com.concurseirodf.backend.domain.service.ProgressoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/progresso")
@RequiredArgsConstructor
public class ProgressoController {

    private final ProgressoService progressoService;

    @GetMapping("/cargo/{cargoId}")
    public ResponseEntity<ProgressoDTO> getProgresso(
            @AuthenticationPrincipal Usuario usuario,
            @PathVariable UUID cargoId) {
        return ResponseEntity.ok(progressoService.getProgresso(usuario, cargoId));
    }

    @PutMapping("/cargo/{cargoId}")
    public ResponseEntity<ProgressoDTO> saveProgresso(
            @AuthenticationPrincipal Usuario usuario,
            @PathVariable UUID cargoId,
            @RequestBody ProgressoDTO dto) {
        return ResponseEntity.ok(progressoService.saveProgresso(usuario, cargoId, dto));
    }
}
