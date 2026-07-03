package com.concurseirodf.backend.domain.service;

import com.concurseirodf.backend.api.dto.OrgaoRequestDTO;
import com.concurseirodf.backend.api.dto.OrgaoResponseDTO;
import com.concurseirodf.backend.api.exception.ResourceNotFoundException;
import com.concurseirodf.backend.domain.entity.Orgao;
import com.concurseirodf.backend.domain.repository.OrgaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrgaoService {

    private final OrgaoRepository orgaoRepository;

    public OrgaoResponseDTO create(OrgaoRequestDTO dto) {
        Orgao orgao = new Orgao();
        orgao.setNome(dto.nome());
        orgao.setSigla(dto.sigla());
        orgao.setEsfera(dto.esfera());

        Orgao saved = orgaoRepository.save(orgao);
        return mapToDTO(saved);
    }

    public List<OrgaoResponseDTO> findAll() {
        return orgaoRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public OrgaoResponseDTO findById(UUID id) {
        Orgao orgao = getOrgaoEntity(id);
        return mapToDTO(orgao);
    }

    public Orgao getOrgaoEntity(UUID id) {
        return orgaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Órgão não encontrado com id: " + id));
    }

    private OrgaoResponseDTO mapToDTO(Orgao orgao) {
        return new OrgaoResponseDTO(
                orgao.getId(),
                orgao.getNome(),
                orgao.getSigla(),
                orgao.getEsfera()
        );
    }
}
