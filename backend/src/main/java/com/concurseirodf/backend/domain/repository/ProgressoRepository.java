package com.concurseirodf.backend.domain.repository;

import com.concurseirodf.backend.domain.entity.Progresso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProgressoRepository extends JpaRepository<Progresso, UUID> {
    Optional<Progresso> findByUsuarioIdAndCargoId(UUID usuarioId, UUID cargoId);
}
