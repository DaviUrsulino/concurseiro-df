package com.concurseirodf.backend.api.dto;

public record AuthenticationResponse(
        String token,
        String nome,
        String email,
        String nivelEscolaridade
) {
}
