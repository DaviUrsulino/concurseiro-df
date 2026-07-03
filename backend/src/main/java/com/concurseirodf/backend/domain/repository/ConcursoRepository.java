package com.concurseirodf.backend.domain.repository;

import com.concurseirodf.backend.domain.entity.Concurso;
import com.concurseirodf.backend.domain.enums.StatusConcurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConcursoRepository extends JpaRepository<Concurso, UUID> {
    List<Concurso> findByStatus(StatusConcurso status);
}
