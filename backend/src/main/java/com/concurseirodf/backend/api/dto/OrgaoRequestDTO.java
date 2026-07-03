package com.concurseirodf.backend.api.dto;

import jakarta.validation.constraints.NotBlank;

public record OrgaoRequestDTO(
    @NotBlank(message = "O nome não pode estar em branco")
    String nome,
    
    @NotBlank(message = "A sigla não pode estar em branco")
    String sigla,
    
    @NotBlank(message = "A esfera não pode estar em branco")
    String esfera
) {}
