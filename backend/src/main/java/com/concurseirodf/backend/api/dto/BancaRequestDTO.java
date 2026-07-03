package com.concurseirodf.backend.api.dto;

import jakarta.validation.constraints.NotBlank;

public record BancaRequestDTO(
    @NotBlank(message = "O nome da banca não pode estar em branco")
    String nome,
    
    String siteOficial
) {}
