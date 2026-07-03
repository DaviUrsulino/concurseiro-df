package com.concurseirodf.backend.api.dto;

import com.concurseirodf.backend.domain.enums.NivelCargo;
import java.math.BigDecimal;

public record CargoRequestDTO(
    String nome,
    NivelCargo nivel,
    BigDecimal salario,
    Integer vagasImediatas,
    Integer vagasCadastroReserva
) {}
