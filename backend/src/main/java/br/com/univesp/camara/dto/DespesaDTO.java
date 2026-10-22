package br.com.univesp.camara.dto;

import br.com.univesp.camara.model.Despesa;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class DespesaDTO {

    private Long id;
    private Integer ano;
    private Integer mes;
    private String tipoDespesa;
    private String codDocumento;
    private LocalDate dataDocumento;
    private BigDecimal valorDocumento;
    private BigDecimal valorGlosa;
    private BigDecimal valorLiquido;
    private String nomeFornecedor;
    private String cnpjCpfFornecedor;

    public static DespesaDTO from(Despesa d) {
        DespesaDTO dto = new DespesaDTO();
        dto.setId(d.getId());
        dto.setAno(d.getAno());
        dto.setMes(d.getMes());
        dto.setTipoDespesa(d.getTipoDespesa());
        dto.setCodDocumento(d.getCodDocumento());
        dto.setDataDocumento(d.getDataDocumento());
        dto.setValorDocumento(d.getValorDocumento());
        dto.setValorGlosa(d.getValorGlosa());
        dto.setValorLiquido(d.getValorLiquido());
        dto.setNomeFornecedor(d.getNomeFornecedor());
        dto.setCnpjCpfFornecedor(d.getCnpjCpfFornecedor());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}