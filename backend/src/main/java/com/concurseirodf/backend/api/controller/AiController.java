package com.concurseirodf.backend.api.controller;

import com.concurseirodf.backend.api.dto.DisciplinaRequestDTO;
import com.concurseirodf.backend.domain.service.EditalAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ia")
@RequiredArgsConstructor
public class AiController {

    private final EditalAiService editalAiService;

    @PostMapping("/extrair-disciplinas")
    public ResponseEntity<List<DisciplinaRequestDTO>> extrairDisciplinas(@RequestBody String trechoEdital) {
        return ResponseEntity.ok(editalAiService.extrairDisciplinas(trechoEdital));
    }
}
