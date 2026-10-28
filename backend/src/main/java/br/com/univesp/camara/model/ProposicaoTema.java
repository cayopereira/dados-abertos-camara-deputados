package br.com.univesp.camara.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "proposicao_temas")
public class ProposicaoTema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "proposicao_id", nullable = false)
    private Long proposicaoId;

    @Column(name = "tema_cod", nullable = false)
    private Integer temaCod;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist(){this.createdAt = LocalDateTime.now();}
}
