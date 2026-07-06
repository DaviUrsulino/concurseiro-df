package com.concurseirodf.backend.domain.service;

import com.concurseirodf.backend.api.dto.ProgressoDTO;
import com.concurseirodf.backend.domain.entity.Cargo;
import com.concurseirodf.backend.domain.entity.Progresso;
import com.concurseirodf.backend.domain.entity.Usuario;
import com.concurseirodf.backend.domain.repository.CargoRepository;
import com.concurseirodf.backend.domain.repository.ProgressoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProgressoService {

    private final ProgressoRepository progressoRepository;
    private final CargoRepository cargoRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ProgressoDTO getProgresso(Usuario usuario, UUID cargoId) {
        Progresso progresso = progressoRepository.findByUsuarioIdAndCargoId(usuario.getId(), cargoId)
                .orElse(null);
        
        if (progresso == null || progresso.getTopicosConcluidosJson() == null) {
            return new ProgressoDTO(new ArrayList<>());
        }
        
        try {
            List<String> topicos = objectMapper.readValue(progresso.getTopicosConcluidosJson(), new TypeReference<List<String>>() {});
            return new ProgressoDTO(topicos);
        } catch (JsonProcessingException e) {
            return new ProgressoDTO(new ArrayList<>());
        }
    }

    public ProgressoDTO saveProgresso(Usuario usuario, UUID cargoId, ProgressoDTO dto) {
        Progresso progresso = progressoRepository.findByUsuarioIdAndCargoId(usuario.getId(), cargoId)
                .orElseGet(() -> {
                    Progresso novo = new Progresso();
                    novo.setUsuario(usuario);
                    Cargo cargo = cargoRepository.findById(cargoId).orElseThrow(() -> new RuntimeException("Cargo não encontrado"));
                    novo.setCargo(cargo);
                    return novo;
                });
        
        try {
            String json = objectMapper.writeValueAsString(dto.topicosConcluidos());
            progresso.setTopicosConcluidosJson(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao processar tópicos", e);
        }
        
        progressoRepository.save(progresso);
        return dto;
    }
}
