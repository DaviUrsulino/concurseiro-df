package com.concurseirodf.backend.api.dto;

import com.concurseirodf.backend.domain.enums.StatusConcurso;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ConcursoRequestDTO(
    UUID orgaoId,
    UUID bancaId,
    String titulo,
    StatusConcurso status,
    String linkPaginaOficial,
    LocalDate dataProva,
    List<CargoRequestDTO> cargos
) {}
