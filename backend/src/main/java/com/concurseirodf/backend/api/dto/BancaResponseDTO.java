package com.concurseirodf.backend.api.dto;

import java.util.UUID;

public record BancaResponseDTO(
    UUID id,
    String nome,
    String siteOficial
) {}
