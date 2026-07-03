package com.concurseirodf.backend.domain.repository;

import com.concurseirodf.backend.domain.entity.Banca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BancaRepository extends JpaRepository<Banca, UUID> {
}
