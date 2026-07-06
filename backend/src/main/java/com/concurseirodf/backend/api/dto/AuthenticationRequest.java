package com.concurseirodf.backend.api.dto;

public record AuthenticationRequest(
        String email,
        String senha
) {
}
