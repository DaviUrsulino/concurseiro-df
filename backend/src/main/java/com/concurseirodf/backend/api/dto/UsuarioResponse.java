package com.concurseirodf.backend.api.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class UsuarioResponse {
    private UUID id;
    private String nome;
    private String email;
    private String nivelEscolaridade;
    private BigDecimal pretensaoSalarial;
}
