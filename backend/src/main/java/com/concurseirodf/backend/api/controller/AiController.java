package com.concurseirodf.backend.api.controller;

import com.concurseirodf.backend.domain.service.EditalAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ia")
@RequiredArgsConstructor
public class AiController {

    private final EditalAiService editalAiService;

    @PostMapping("/extrair-disciplinas")
    public ResponseEntity<String> extrairDisciplinas(@RequestBody String trechoEdital) {
        return ResponseEntity.ok(editalAiService.extrairDisciplinas(trechoEdital));
    }
}
