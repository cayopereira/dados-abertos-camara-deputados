package br.com.univesp.camara.dto;

import br.com.univesp.camara.model.Votacao;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class VotacaoDetalheDto {
    private String id;
    private LocalDate data;
    private LocalDateTime dataHoraRegistro;
    private String descricao;
    private Boolean aprovacao;
    private String siglaOrgao;
    private String proposicaoObjeto;
    private boolean votosCarregados;
    private Long totalVotos;
    private List<ItemPlacar> placar;

    public record ItemPlacar(String tipoVoto, long total){

    }

    public static VotacaoDetalheDto from(Votacao v, List<Object[]> linhas) {
        VotacaoDetalheDto dto = new VotacaoDetalheDto();

        dto.setId(v.getId());
        dto.setData(v.getData());
        dto.setDataHoraRegistro(v.getDataHoraRegistro());
        dto.setDescricao(v.getDescricao());
        dto.setAprovacao(v.getAprovacao());
        dto.setSiglaOrgao(v.getSiglaOrgao());
        dto.setProposicaoObjeto(v.getProposicaoObjeto());
        dto.setVotosCarregados(v.isVotosCarregados());

        List<ItemPlacar> placar = linhas.stream()
                .map(l -> new ItemPlacar((String) l[0], ((Number) l[1]).longValue()))
                .toList();
        dto.setPlacar(placar);
        dto.setTotalVotos(placar.stream().mapToLong(ItemPlacar::total).sum());
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

    public Long getTotalVotos() {
        return totalVotos;
    }

    public void setTotalVotos(Long totalVotos) {
        this.totalVotos = totalVotos;
    }

    public List<ItemPlacar> getPlacar() {
        return placar;
    }

    public void setPlacar(List<ItemPlacar> placar) {
        this.placar = placar;
    }
}
