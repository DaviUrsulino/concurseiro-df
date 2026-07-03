package com.concurseirodf.backend.domain.repository;

import com.concurseirodf.backend.domain.entity.Andamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AndamentoRepository extends JpaRepository<Andamento, UUID> {
    List<Andamento> findByConcursoId(UUID concursoId);
}
