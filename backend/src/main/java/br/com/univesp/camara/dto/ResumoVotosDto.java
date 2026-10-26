package br.com.univesp.camara.dto;

import java.util.List;

public record ResumoVotosDto(long totalVotos, List<ItemResumo> porTipo) {

    public record ItemResumo(String tipoVoto, long total){

    }

    public static ResumoVotosDto from(List<Object[]>linhas){
        List<ItemResumo> placarPorDeputados = linhas.stream()
                .map(l -> new ItemResumo((String) l[0], ((Number) l[1]).longValue()))
                .toList();
        long total = placarPorDeputados.stream().mapToLong(ItemResumo::total).sum();
        return new ResumoVotosDto(total, placarPorDeputados);
    }
}
