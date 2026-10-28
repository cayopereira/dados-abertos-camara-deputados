package br.com.univesp.camara.repository;

import br.com.univesp.camara.dto.TemaResumoDto;
import br.com.univesp.camara.model.Tema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TemaRepository extends JpaRepository<Tema, Integer> {

    @Query("SELECT new br.com.univesp.camara.dto.TemaResumoDto(t.cod, t.nome, COUNT(DISTINCT v.id)) " +
            "FROM Tema t "+
            "LEFT JOIN ProposicaoTema pt ON pt.temaCod = t.cod " +
            "LEFT JOIN Votacao v ON v.proposicaoId = pt.proposicaoId " +
            "GROUP BY t.cod, t.nome ORDER BY COUNT(DISTINCT v.id) DESC, t.nome")
    List<TemaResumoDto> findResumos();
}
