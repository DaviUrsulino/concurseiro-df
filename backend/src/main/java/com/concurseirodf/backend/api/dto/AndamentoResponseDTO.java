package com.concurseirodf.backend.api.dto;

import java.time.LocalDate;
import java.util.UUID;

public record AndamentoResponseDTO(
    UUID id,
    String titulo,
    String descricao,
    String linkDocumento,
    LocalDate dataPublicacao,
    Boolean extraidoPorIa
) {}
