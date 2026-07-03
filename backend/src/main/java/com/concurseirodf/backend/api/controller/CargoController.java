package com.concurseirodf.backend.api.controller;

import com.concurseirodf.backend.api.dto.DisciplinaRequestDTO;
import com.concurseirodf.backend.api.dto.DisciplinaResponseDTO;
import com.concurseirodf.backend.domain.service.CargoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cargos")
@RequiredArgsConstructor
public class CargoController {

    private final CargoService cargoService;

    @PostMapping("/{id}/disciplinas")
    public ResponseEntity<DisciplinaResponseDTO> addDisciplina(
            @PathVariable UUID id, 
            @RequestBody DisciplinaRequestDTO request) {
        return new ResponseEntity<>(cargoService.addDisciplina(id, request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/disciplinas")
    public ResponseEntity<List<DisciplinaResponseDTO>> getDisciplinas(@PathVariable UUID id) {
        return ResponseEntity.ok(cargoService.getDisciplinas(id));
    }
}
