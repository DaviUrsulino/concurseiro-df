package com.concurseirodf.backend.domain.repository;

import com.concurseirodf.backend.domain.entity.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, UUID> {
    List<Disciplina> findByCargoId(UUID cargoId);
}
