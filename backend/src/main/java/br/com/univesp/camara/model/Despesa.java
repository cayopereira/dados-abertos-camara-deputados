package br.com.univesp.camara.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "despesas")
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deputado_id", nullable = false)
    private Deputado deputado;

    @Column(nullable = false)
    private Integer ano;

    @Column(nullable = false)
    private Integer mes;

    @Column(name = "tipo_despesa", length = 200)
    private String tipoDespesa;

    @Column(name = "cod_documento", length = 100)
    private String codDocumento;

    @Column(name = "data_documento")
    private LocalDate dataDocumento;

    @Column(name = "valor_documento", precision = 12, scale = 2)
    private BigDecimal valorDocumento;

    @Column(name = "valor_glosa", precision = 12, scale = 2)
    private BigDecimal valorGlosa;

    @Column(name = "valor_liquido", precision = 12, scale = 2)
    private BigDecimal valorLiquido;

    @Column(name = "nome_fornecedor")
    private String nomeFornecedor;

    @Column(name = "cnpj_cpf_fornecedor", length = 20)
    private String cnpjCpfFornecedor;

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

    public Deputado getDeputado() {
        return deputado;
    }

    public void setDeputado(Deputado deputado) {
        this.deputado = deputado;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public String getTipoDespesa() {
        return tipoDespesa;
    }

    public void setTipoDespesa(String tipoDespesa) {
        this.tipoDespesa = tipoDespesa;
    }

    public String getCodDocumento() {
        return codDocumento;
    }

    public void setCodDocumento(String codDocumento) {
        this.codDocumento = codDocumento;
    }

    public LocalDate getDataDocumento() {
        return dataDocumento;
    }

    public void setDataDocumento(LocalDate dataDocumento) {
        this.dataDocumento = dataDocumento;
    }

    public BigDecimal getValorDocumento() {
        return valorDocumento;
    }

    public void setValorDocumento(BigDecimal valorDocumento) {
        this.valorDocumento = valorDocumento;
    }

    public BigDecimal getValorGlosa() {
        return valorGlosa;
    }

    public void setValorGlosa(BigDecimal valorGlosa) {
        this.valorGlosa = valorGlosa;
    }

    public BigDecimal getValorLiquido() {
        return valorLiquido;
    }

    public void setValorLiquido(BigDecimal valorLiquido) {
        this.valorLiquido = valorLiquido;
    }

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }

    public String getCnpjCpfFornecedor() {
        return cnpjCpfFornecedor;
    }

    public void setCnpjCpfFornecedor(String cnpjCpfFornecedor) {
        this.cnpjCpfFornecedor = cnpjCpfFornecedor;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}