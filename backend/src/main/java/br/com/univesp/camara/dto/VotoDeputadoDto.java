package br.com.univesp.camara.dto;

import br.com.univesp.camara.model.Votacao;
import br.com.univesp.camara.model.Voto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record VotoDeputadoDto(String tipoVoto, String siglaPartido, LocalDateTime dataRegistroVoto, String votacaoId,
                              LocalDate dataVotacao, Boolean aprovacao, String descricao, String siglaOrgao) {

    public static VotoDeputadoDto from(Voto v) {
        Votacao votacao = v.getVotacao();
        return new VotoDeputadoDto(
                v.getTipoVoto(), v.getSiglaPartido(), v.getDataRegistroVoto(),
                votacao.getId(), votacao.getData(), votacao.getAprovacao(),
                votacao.getDescricao(), votacao.getSiglaOrgao());
    }
}
