package br.com.univesp.camara.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "deputados")
public class Deputado {

    @Id
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "nome_eleitoral")
    private String nomeEleitoral;

    @Column(name = "sigla_partido", length = 20)
    private String siglaPartido;

    @Column(name = "sigla_uf", length = 2)
    private String siglaUf;

    @Column(name = "url_foto", length = 500)
    private String urlFoto;

    private String email;

    private String situacao;

    @Column(nullable = false)
    private Integer legislatura;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}