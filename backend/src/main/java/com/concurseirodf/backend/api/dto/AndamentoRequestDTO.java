package com.concurseirodf.backend.api.dto;

import java.time.LocalDate;

public record AndamentoRequestDTO(
    String titulo,
    String descricao,
    String linkDocumento,
    LocalDate dataPublicacao,
    Boolean extraidoPorIa
) {}
