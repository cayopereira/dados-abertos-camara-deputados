package br.com.univesp.camara.dto;

import br.com.univesp.camara.model.Votacao;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class VotacaoDto {

    private String id;
    private LocalDate data;
    private LocalDateTime dataHoraRegistro;
    private String descricao;
    private Boolean aprovacao;
    private String siglaOrgao;
    private String proposicaoObjeto;
    private boolean votosCarregados;

    public static VotacaoDto from(Votacao v){
        VotacaoDto dto = new VotacaoDto();

        dto.setId(v.getId());
        dto.setData(v.getData());
        dto.setDataHoraRegistro(v.getDataHoraRegistro());
        dto.setDescricao(v.getDescricao());
        dto.setAprovacao(v.getAprovacao());
        dto.setSiglaOrgao(v.getSiglaOrgao());
        dto.setProposicaoObjeto(v.getProposicaoObjeto());
        dto.setVotosCarregados(v.isVotosCarregados());
        return dto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalDateTime getDataHoraRegistro() {
        return dataHoraRegistro;
    }

    public void setDataHoraRegistro(LocalDateTime dataHoraRegistro) {
        this.dataHoraRegistro = dataHoraRegistro;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getAprovacao() {
        return aprovacao;
    }

    public void setAprovacao(Boolean aprovacao) {
        this.aprovacao = aprovacao;
    }

    public String getSiglaOrgao() {
        return siglaOrgao;
    }

    public void setSiglaOrgao(String siglaOrgao) {
        this.siglaOrgao = siglaOrgao;
    }

    public String getProposicaoObjeto() {
        return proposicaoObjeto;
    }

    public void setProposicaoObjeto(String proposicaoObjeto) {
        this.proposicaoObjeto = proposicaoObjeto;
    }

    public boolean isVotosCarregados() {
        return votosCarregados;
    }

    public void setVotosCarregados(boolean votosCarregados) {
        this.votosCarregados = votosCarregados;
    }
}
