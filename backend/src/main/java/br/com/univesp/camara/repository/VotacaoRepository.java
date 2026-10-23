package br.com.univesp.camara.repository;

import br.com.univesp.camara.model.Votacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VotacaoRepository extends JpaRepository<Votacao, String> {

    Page<Votacao> findByDataBetween(LocalDate inicio, LocalDate fim, Pageable pageable);

    Page<Votacao> findBySiglaOrgao(String siglaOrgao, Pageable pageable);

    Page<Votacao> findAllByOrderByDataHoraRegistroDesc(Pageable pageable);

    List<Votacao> findByVotosCarregadosFalseOrderByDataAsc(Pageable pageable);

    Optional<Votacao> findTopByOrderByDataHoraRegistroDesc();
}
