package br.com.univesp.camara.dto;

import br.com.univesp.camara.model.Voto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VotoDto {
    private Long id;
    private String tipoVoto;
    private String siglaPartido;
    private LocalDateTime dataRegistroVoto;

    public static VotoDto from(Voto v) {
        VotoDto dto = new VotoDto();

        dto.setId(v.getId());
        dto.setTipoVoto(v.getTipoVoto());
        dto.setSiglaPartido(v.getSiglaPartido());
        dto.setDataRegistroVoto(v.getDataRegistroVoto());

        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
