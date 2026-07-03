package com.concurseirodf.backend.api.dto;

import com.concurseirodf.backend.domain.enums.NivelCargo;
import java.math.BigDecimal;
import java.util.UUID;

public record CargoResponseDTO(
    UUID id,
    String nome,
    NivelCargo nivel,
    BigDecimal salario,
    Integer vagasImediatas,
    Integer vagasCadastroReserva
) {}
