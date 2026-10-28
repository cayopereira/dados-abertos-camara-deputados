package br.com.univesp.camara.repository;

import br.com.univesp.camara.model.ProposicaoTema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProposicaoTemaRepository extends JpaRepository<ProposicaoTema, Long> {
    @Query("SELECT p.proposicaoId FROM ProposicaoTema p WHERE p.temaCod = :cod")
    List<Long> findProposicaoIdsByTemaCod(@Param("cod") Integer cod);
}
