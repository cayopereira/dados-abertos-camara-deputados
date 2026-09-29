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
}