package com.concurseirodf.backend.api.controller;

import com.concurseirodf.backend.api.dto.BancaRequestDTO;
import com.concurseirodf.backend.api.dto.BancaResponseDTO;
import com.concurseirodf.backend.domain.service.BancaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/bancas")
@RequiredArgsConstructor
public class BancaController {

    private final BancaService bancaService;

    @PostMapping
    public ResponseEntity<BancaResponseDTO> create(@RequestBody BancaRequestDTO request) {
        return new ResponseEntity<>(bancaService.create(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BancaResponseDTO>> findAll() {
        return ResponseEntity.ok(bancaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BancaResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(bancaService.findById(id));
    }
}
