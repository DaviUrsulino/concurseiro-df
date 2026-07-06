package com.concurseirodf.backend.api.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class UpdateUsuarioRequest {
    private String nome;
    private String nivelEscolaridade;
    private BigDecimal pretensaoSalarial;
    private String novaSenha; // opcional
}
