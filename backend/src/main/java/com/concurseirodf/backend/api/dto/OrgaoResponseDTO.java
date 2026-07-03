package com.concurseirodf.backend.api.dto;

import java.util.UUID;

public record OrgaoResponseDTO(
    UUID id,
    String nome,
    String sigla,
    String esfera
) {}
