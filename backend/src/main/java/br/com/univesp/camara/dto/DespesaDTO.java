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
}