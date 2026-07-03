package com.concurseirodf.backend.api.controller;

import com.concurseirodf.backend.api.dto.AndamentoRequestDTO;
import com.concurseirodf.backend.api.dto.AndamentoResponseDTO;
import com.concurseirodf.backend.api.dto.ConcursoRequestDTO;
import com.concurseirodf.backend.api.dto.ConcursoResponseDTO;
import com.concurseirodf.backend.domain.enums.StatusConcurso;
import com.concurseirodf.backend.domain.service.ConcursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/concursos")
@RequiredArgsConstructor
public class ConcursoController {

    private final ConcursoService concursoService;

    @PostMapping
    public ResponseEntity<ConcursoResponseDTO> create(@Valid @RequestBody ConcursoRequestDTO request) {
        return new ResponseEntity<>(concursoService.create(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ConcursoResponseDTO>> findAll(
            @RequestParam(required = false) StatusConcurso status) {
        if (status != null) {
            return ResponseEntity.ok(concursoService.findByStatus(status));
        }
        return ResponseEntity.ok(concursoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConcursoResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(concursoService.findById(id));
    }

    @PostMapping("/{id}/andamentos")
    public ResponseEntity<AndamentoResponseDTO> addAndamento(
            @PathVariable UUID id, 
            @RequestBody AndamentoRequestDTO request) {
        return new ResponseEntity<>(concursoService.addAndamento(id, request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/andamentos")
    public ResponseEntity<List<AndamentoResponseDTO>> getAndamentos(@PathVariable UUID id) {
        return ResponseEntity.ok(concursoService.getAndamentos(id));
    }
}
