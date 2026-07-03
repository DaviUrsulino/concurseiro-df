package com.concurseirodf.backend.api.dto;

import com.concurseirodf.backend.domain.enums.StatusConcurso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ConcursoRequestDTO(
    @NotNull(message = "O ID do órgão é obrigatório")
    UUID orgaoId,
    
    UUID bancaId,
    
    @NotBlank(message = "O título do concurso é obrigatório")
    String titulo,
    
    @NotNull(message = "O status do concurso é obrigatório")
    StatusConcurso status,
    
    String linkPaginaOficial,
    LocalDate dataProva,
    List<CargoRequestDTO> cargos
) {}
