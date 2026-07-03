package com.concurseirodf.backend.domain.service;

import com.concurseirodf.backend.api.dto.BancaRequestDTO;
import com.concurseirodf.backend.api.dto.BancaResponseDTO;
import com.concurseirodf.backend.api.exception.ResourceNotFoundException;
import com.concurseirodf.backend.domain.entity.Banca;
import com.concurseirodf.backend.domain.repository.BancaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BancaService {

    private final BancaRepository bancaRepository;

    public BancaResponseDTO create(BancaRequestDTO dto) {
        Banca banca = new Banca();
        banca.setNome(dto.nome());
        banca.setSiteOficial(dto.siteOficial());

        Banca saved = bancaRepository.save(banca);
        return mapToDTO(saved);
    }

    public List<BancaResponseDTO> findAll() {
        return bancaRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public BancaResponseDTO findById(UUID id) {
        Banca banca = getBancaEntity(id);
        return mapToDTO(banca);
    }

    public Banca getBancaEntity(UUID id) {
        return bancaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Banca não encontrada com id: " + id));
    }

    private BancaResponseDTO mapToDTO(Banca banca) {
        return new BancaResponseDTO(
                banca.getId(),
                banca.getNome(),
                banca.getSiteOficial()
        );
    }
}
