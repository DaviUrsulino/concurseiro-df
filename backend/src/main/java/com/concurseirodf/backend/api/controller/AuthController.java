package com.concurseirodf.backend.api.controller;

import com.concurseirodf.backend.api.dto.AuthenticationRequest;
import com.concurseirodf.backend.api.dto.AuthenticationResponse;
import com.concurseirodf.backend.api.dto.RegisterRequest;
import com.concurseirodf.backend.domain.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService service;
    private final com.concurseirodf.backend.domain.service.PasswordResetService passwordResetService;

    @PostMapping("/registrar")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody com.concurseirodf.backend.domain.dto.ForgotPasswordRequest request) {
        passwordResetService.createPasswordResetTokenForUser(request.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody com.concurseirodf.backend.domain.dto.ResetPasswordRequest request) {
        boolean result = passwordResetService.resetPassword(request.getToken(), request.getNewPassword());
        if (result) {
            return ResponseEntity.ok("Senha redefinida com sucesso.");
        } else {
            return ResponseEntity.badRequest().body("Token inválido ou expirado.");
        }
    }
}
