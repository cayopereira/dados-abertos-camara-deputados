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

    @Column(name = "email", length = 500)
    private String email;

    @Column(columnDefinition = "TEXT")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeEleitoral() {
        return nomeEleitoral;
    }

    public void setNomeEleitoral(String nomeEleitoral) {
        this.nomeEleitoral = nomeEleitoral;
    }

    public String getSiglaPartido() {
        return siglaPartido;
    }

    public void setSiglaPartido(String siglaPartido) {
        this.siglaPartido = siglaPartido;
    }

    public String getSiglaUf() {
        return siglaUf;
    }

    public void setSiglaUf(String siglaUf) {
        this.siglaUf = siglaUf;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Integer getLegislatura() {
        return legislatura;
    }

    public void setLegislatura(Integer legislatura) {
        this.legislatura = legislatura;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}