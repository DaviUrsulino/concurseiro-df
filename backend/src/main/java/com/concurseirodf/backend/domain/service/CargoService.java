package com.concurseirodf.backend.domain.service;

import com.concurseirodf.backend.api.dto.DisciplinaRequestDTO;
import com.concurseirodf.backend.api.dto.DisciplinaResponseDTO;
import com.concurseirodf.backend.api.exception.ResourceNotFoundException;
import com.concurseirodf.backend.domain.entity.Cargo;
import com.concurseirodf.backend.domain.entity.Disciplina;
import com.concurseirodf.backend.domain.repository.CargoRepository;
import com.concurseirodf.backend.domain.repository.DisciplinaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CargoService {

    private final CargoRepository cargoRepository;
    private final DisciplinaRepository disciplinaRepository;

    public Cargo getCargoEntity(UUID id) {
        return cargoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cargo não encontrado com id: " + id));
    }

    @Transactional
    public DisciplinaResponseDTO addDisciplina(UUID cargoId, DisciplinaRequestDTO dto) {
        Cargo cargo = getCargoEntity(cargoId);

        Disciplina disciplina = new Disciplina();
        disciplina.setCargo(cargo);
        disciplina.setNome(dto.nome());
        disciplina.setTopicosJson(dto.topicosJson());

        Disciplina saved = disciplinaRepository.save(disciplina);
        return new DisciplinaResponseDTO(
                saved.getId(), saved.getNome(), saved.getTopicosJson()
        );
    }

    public List<DisciplinaResponseDTO> getDisciplinas(UUID cargoId) {
        getCargoEntity(cargoId);
        
        return disciplinaRepository.findByCargoId(cargoId).stream()
                .map(d -> new DisciplinaResponseDTO(d.getId(), d.getNome(), d.getTopicosJson()))
                .collect(Collectors.toList());
    }
}
