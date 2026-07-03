package com.concurseirodf.backend.api.dto;

import com.concurseirodf.backend.domain.enums.StatusConcurso;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ConcursoResponseDTO(
    UUID id,
    OrgaoResponseDTO orgao,
    BancaResponseDTO banca,
    String titulo,
    StatusConcurso status,
    String linkPaginaOficial,
    LocalDate dataProva,
    LocalDateTime criadoEm,
    List<CargoResponseDTO> cargos
) {}
