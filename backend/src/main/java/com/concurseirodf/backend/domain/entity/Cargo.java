package com.concurseirodf.backend.domain.entity;

import com.concurseirodf.backend.domain.enums.NivelCargo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tb_cargo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cargo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concurso_id", nullable = false)
    private Concurso concurso;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NivelCargo nivel;

    private BigDecimal salario;

    @Column(name = "vagas_imediatas")
    private Integer vagasImediatas;

    @Column(name = "vagas_cadastro_reserva")
    private Integer vagasCadastroReserva;

    @Column(name = "conteudo_programatico", columnDefinition = "TEXT")
    private String conteudoProgramatico;
}
