package com.concurseirodf.backend.domain.entity;

import com.concurseirodf.backend.domain.enums.StatusConcurso;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_concurso")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Concurso {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "orgao_id", nullable = false)
    private Orgao orgao;

    @ManyToOne
    @JoinColumn(name = "banca_id")
    private Banca banca;

    @Column(nullable = false)
    private String titulo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusConcurso status;

    @Column(name = "link_pagina_oficial")
    private String linkPaginaOficial;

    @Column(name = "data_prova")
    private LocalDate dataProva;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;
}
