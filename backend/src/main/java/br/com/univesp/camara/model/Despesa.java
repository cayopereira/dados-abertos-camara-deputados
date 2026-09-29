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

    @Column(name = "nome_fornecedor", length = 255)
    private String nomeFornecedor;

    @Column(name = "cnpj_cpf_fornecedor", length = 20)
    private String cnpjCpfFornecedor;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}