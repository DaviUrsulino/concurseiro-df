package com.concurseirodf.backend.domain.repository;

import com.concurseirodf.backend.domain.entity.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, UUID> {
    List<Cargo> findByConcursoId(UUID concursoId);
}
