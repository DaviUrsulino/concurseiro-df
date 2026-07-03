package com.concurseirodf.backend.domain.service;

import com.concurseirodf.backend.api.dto.ConcursoResponseDTO;
import com.concurseirodf.backend.api.exception.ResourceNotFoundException;
import com.concurseirodf.backend.domain.entity.Concurso;
import com.concurseirodf.backend.domain.entity.Orgao;
import com.concurseirodf.backend.domain.enums.StatusConcurso;
import com.concurseirodf.backend.domain.repository.CargoRepository;
import com.concurseirodf.backend.domain.repository.ConcursoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConcursoServiceTest {

    @Mock
    private ConcursoRepository concursoRepository;

    @Mock
    private CargoRepository cargoRepository;

    @InjectMocks
    private ConcursoService concursoService;

    private Concurso concursoMock;
    private Orgao orgaoMock;

    @BeforeEach
    void setUp() {
        orgaoMock = new Orgao(UUID.randomUUID(), "SEDF", "Secretaria de Educação", "DISTRITAL");
        concursoMock = new Concurso(
                UUID.randomUUID(), orgaoMock, null, "Concurso SEDF 2026",
                StatusConcurso.PREVISTO, "http://sedf.df.gov.br",
                LocalDate.now(), LocalDateTime.now()
        );
    }

    @Test
    void findById_ShouldReturnConcurso_WhenExists() {
        // Arrange
        UUID id = concursoMock.getId();
        when(concursoRepository.findById(id)).thenReturn(Optional.of(concursoMock));
        when(cargoRepository.findByConcursoId(id)).thenReturn(Collections.emptyList());

        // Act
        ConcursoResponseDTO result = concursoService.findById(id);

        // Assert
        assertNotNull(result);
        assertEquals(concursoMock.getTitulo(), result.titulo());
        assertEquals(concursoMock.getStatus(), result.status());
        verify(concursoRepository, times(1)).findById(id);
    }

    @Test
    void findById_ShouldThrowException_WhenNotExists() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(concursoRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> concursoService.findById(id));
        verify(concursoRepository, times(1)).findById(id);
    }
}
