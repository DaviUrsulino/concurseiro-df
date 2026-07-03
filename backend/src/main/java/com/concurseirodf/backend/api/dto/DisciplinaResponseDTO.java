package com.concurseirodf.backend.api.dto;

import java.util.UUID;

public record DisciplinaResponseDTO(
    UUID id,
    String nome,
    String topicosJson
) {}
