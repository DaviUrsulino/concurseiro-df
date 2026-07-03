package com.concurseirodf.backend.api.controller;

import com.concurseirodf.backend.api.dto.OrgaoRequestDTO;
import com.concurseirodf.backend.api.dto.OrgaoResponseDTO;
import com.concurseirodf.backend.domain.service.OrgaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orgaos")
@RequiredArgsConstructor
public class OrgaoController {

    private final OrgaoService orgaoService;

    @PostMapping
    public ResponseEntity<OrgaoResponseDTO> create(@Valid @RequestBody OrgaoRequestDTO request) {
        return new ResponseEntity<>(orgaoService.create(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrgaoResponseDTO>> findAll() {
        return ResponseEntity.ok(orgaoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrgaoResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(orgaoService.findById(id));
    }
}
