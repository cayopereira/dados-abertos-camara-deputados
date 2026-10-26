package br.com.univesp.camara.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "votos")
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "votacao_id", nullable = false)
    private Votacao votacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deputado_id", nullable = false)
    private Deputado deputado;

    @Column(name = "tipo_voto", length = 30)
    private String tipoVoto;

    @Column(name = "sigla_partido", length = 20)
    private String siglaPartido;

    @Column(name = "data_registro_voto")
    private LocalDateTime dataRegistroVoto;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Votacao getVotacao() {
        return votacao;
    }

    public void setVotacao(Votacao votacao) {
        this.votacao = votacao;
    }

    public Deputado getDeputado() {
        return deputado;
    }

    public void setDeputado(Deputado deputado) {
        this.deputado = deputado;
    }

    public String getTipoVoto() {
        return tipoVoto;
    }

    public void setTipoVoto(String tipoVoto) {
        this.tipoVoto = tipoVoto;
    }

    public String getSiglaPartido() {
        return siglaPartido;
    }

    public void setSiglaPartido(String siglaPartido) {
        this.siglaPartido = siglaPartido;
    }

    public LocalDateTime getDataRegistroVoto() {
        return dataRegistroVoto;
    }

    public void setDataRegistroVoto(LocalDateTime dataRegistroVoto) {
        this.dataRegistroVoto = dataRegistroVoto;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
