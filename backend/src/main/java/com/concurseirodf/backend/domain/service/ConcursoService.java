package com.concurseirodf.backend.domain.service;

import com.concurseirodf.backend.api.dto.*;
import com.concurseirodf.backend.api.exception.ResourceNotFoundException;
import com.concurseirodf.backend.domain.entity.Banca;
import com.concurseirodf.backend.domain.entity.Cargo;
import com.concurseirodf.backend.domain.entity.Concurso;
import com.concurseirodf.backend.domain.entity.Orgao;
import com.concurseirodf.backend.domain.enums.StatusConcurso;
import com.concurseirodf.backend.domain.repository.CargoRepository;
import com.concurseirodf.backend.domain.repository.ConcursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConcursoService {

    private final ConcursoRepository concursoRepository;
    private final CargoRepository cargoRepository;
    private final OrgaoService orgaoService;
    private final BancaService bancaService;

    @Transactional
    public ConcursoResponseDTO create(ConcursoRequestDTO dto) {
        Orgao orgao = orgaoService.getOrgaoEntity(dto.orgaoId());
        Banca banca = null;
        if (dto.bancaId() != null) {
            banca = bancaService.getBancaEntity(dto.bancaId());
        }

        Concurso concurso = new Concurso();
        concurso.setOrgao(orgao);
        concurso.setBanca(banca);
        concurso.setTitulo(dto.titulo());
        concurso.setStatus(dto.status());
        concurso.setLinkPaginaOficial(dto.linkPaginaOficial());
        concurso.setDataProva(dto.dataProva());

        Concurso savedConcurso = concursoRepository.save(concurso);

        List<Cargo> cargos = null;
        if (dto.cargos() != null && !dto.cargos().isEmpty()) {
            cargos = dto.cargos().stream().map(cargoDto -> {
                Cargo cargo = new Cargo();
                cargo.setConcurso(savedConcurso);
                cargo.setNome(cargoDto.nome());
                cargo.setNivel(cargoDto.nivel());
                cargo.setSalario(cargoDto.salario());
                cargo.setVagasImediatas(cargoDto.vagasImediatas());
                cargo.setVagasCadastroReserva(cargoDto.vagasCadastroReserva());
                return cargo;
            }).collect(Collectors.toList());
            cargoRepository.saveAll(cargos);
        }

        return mapToDTO(savedConcurso, cargos);
    }

    public List<ConcursoResponseDTO> findAll() {
        return concursoRepository.findAll().stream()
                .map(this::mapToDTOWithoutCargos)
                .collect(Collectors.toList());
    }

    public List<ConcursoResponseDTO> findByStatus(StatusConcurso status) {
        return concursoRepository.findByStatus(status).stream()
                .map(this::mapToDTOWithoutCargos)
                .collect(Collectors.toList());
    }

    public ConcursoResponseDTO findById(UUID id) {
        Concurso concurso = getConcursoEntity(id);
        List<Cargo> cargos = cargoRepository.findByConcursoId(id);
        return mapToDTO(concurso, cargos);
    }

    public Concurso getConcursoEntity(UUID id) {
        return concursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Concurso não encontrado com id: " + id));
    }

    private ConcursoResponseDTO mapToDTO(Concurso concurso, List<Cargo> cargos) {
        OrgaoResponseDTO orgaoDTO = new OrgaoResponseDTO(
                concurso.getOrgao().getId(),
                concurso.getOrgao().getNome(),
                concurso.getOrgao().getSigla(),
                concurso.getOrgao().getEsfera()
        );

        BancaResponseDTO bancaDTO = null;
        if (concurso.getBanca() != null) {
            bancaDTO = new BancaResponseDTO(
                    concurso.getBanca().getId(),
                    concurso.getBanca().getNome(),
                    concurso.getBanca().getSiteOficial()
            );
        }

        List<CargoResponseDTO> cargosDTO = null;
        if (cargos != null) {
            cargosDTO = cargos.stream().map(c -> new CargoResponseDTO(
                    c.getId(), c.getNome(), c.getNivel(), c.getSalario(),
                    c.getVagasImediatas(), c.getVagasCadastroReserva()
            )).collect(Collectors.toList());
        }

        return new ConcursoResponseDTO(
                concurso.getId(),
                orgaoDTO,
                bancaDTO,
                concurso.getTitulo(),
                concurso.getStatus(),
                concurso.getLinkPaginaOficial(),
                concurso.getDataProva(),
                concurso.getCriadoEm(),
                cargosDTO
        );
    }

    private ConcursoResponseDTO mapToDTOWithoutCargos(Concurso concurso) {
        return mapToDTO(concurso, null);
    }
}
