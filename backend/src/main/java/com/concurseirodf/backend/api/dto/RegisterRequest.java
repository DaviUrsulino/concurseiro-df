package com.concurseirodf.backend.api.dto;

import java.math.BigDecimal;

public record RegisterRequest(
        String nome,
        String email,
        String senha,
        String nivelEscolaridade,
        BigDecimal pretensaoSalarial
) {
}
