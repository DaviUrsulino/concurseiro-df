package com.concurseirodf.backend.domain.service;

import com.concurseirodf.backend.api.dto.ProgressoDTO;
import com.concurseirodf.backend.domain.entity.Cargo;
import com.concurseirodf.backend.domain.entity.Progresso;
import com.concurseirodf.backend.domain.entity.Usuario;
import com.concurseirodf.backend.domain.repository.CargoRepository;
import com.concurseirodf.backend.domain.repository.ProgressoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProgressoServiceTest {

    @Mock
    private ProgressoRepository progressoRepository;
    
    @Mock
    private CargoRepository cargoRepository;

    @InjectMocks
    private ProgressoService progressoService;

    @Test
    void testGetProgresso_whenNotExists() {
        Usuario usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        UUID cargoId = UUID.randomUUID();

        when(progressoRepository.findByUsuarioIdAndCargoId(usuario.getId(), cargoId)).thenReturn(Optional.empty());

        ProgressoDTO dto = progressoService.getProgresso(usuario, cargoId);

        assertNotNull(dto);
        assertEquals(0, dto.topicosConcluidos().size());
    }

    @Test
    void testUpdateProgresso() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        
        Cargo cargo = new Cargo();
        cargo.setId(UUID.randomUUID());

        ProgressoDTO dto = new ProgressoDTO(List.of("Tópico 1", "Tópico 2"));

        when(cargoRepository.findById(cargo.getId())).thenReturn(Optional.of(cargo));
        when(progressoRepository.findByUsuarioIdAndCargoId(usuario.getId(), cargo.getId())).thenReturn(Optional.empty());
        when(progressoRepository.save(any(Progresso.class))).thenAnswer(i -> i.getArguments()[0]);

        ProgressoDTO result = progressoService.updateProgresso(usuario, cargo.getId(), dto);

        assertNotNull(result);
        assertEquals(2, result.topicosConcluidos().size());
        verify(progressoRepository).save(any(Progresso.class));
    }
}
