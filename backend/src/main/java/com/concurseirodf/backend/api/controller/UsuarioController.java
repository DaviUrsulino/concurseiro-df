package com.concurseirodf.backend.api.controller;

import com.concurseirodf.backend.api.dto.UpdateUsuarioRequest;
import com.concurseirodf.backend.api.dto.UsuarioResponse;
import com.concurseirodf.backend.domain.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponse> getMeuPerfil(Authentication authentication) {
        // O authentication.getName() retorna o e-mail do usuário autenticado no JWT
        return ResponseEntity.ok(usuarioService.getMeuPerfil(authentication.getName()));
    }

    @PutMapping("/me")
    public ResponseEntity<UsuarioResponse> atualizarPerfil(
            Authentication authentication,
            @RequestBody UpdateUsuarioRequest request) {
        return ResponseEntity.ok(usuarioService.atualizarPerfil(authentication.getName(), request));
    }
}
