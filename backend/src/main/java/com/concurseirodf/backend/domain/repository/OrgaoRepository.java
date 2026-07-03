package com.concurseirodf.backend.domain.repository;

import com.concurseirodf.backend.domain.entity.Orgao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrgaoRepository extends JpaRepository<Orgao, UUID> {
}
