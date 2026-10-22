package br.com.univesp.camara.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ResumoDespesaDTO {

    private Integer ano;
    private BigDecimal total;
    private List<ItemResumo> porTipo;
    private List<ItemResumo> mensal;

    @Data
    public static class ItemResumo {
        private String chave;
        private BigDecimal valor;

        public ItemResumo(String chave, BigDecimal valor) {
            this.chave = chave;
            this.valor = valor;
        }
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<ItemResumo> getPorTipo() {
        return porTipo;
    }

    public void setPorTipo(List<ItemResumo> porTipo) {
        this.porTipo = porTipo;
    }

    public List<ItemResumo> getMensal() {
        return mensal;
    }

    public void setMensal(List<ItemResumo> mensal) {
        this.mensal = mensal;
    }
}