package br.com.univesp.camara.repository;

import br.com.univesp.camara.model.Deputado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeputadoRepository extends JpaRepository<Deputado, Long> {

    Page<Deputado> findByLegislatura(Integer legislatura, Pageable pageable);

    Page<Deputado> findByNomeContainingIgnoreCaseAndLegislatura(
            String nome, Integer legislatura, Pageable pageable);

    Page<Deputado> findBySiglaPartidoAndLegislatura(
            String partido, Integer legislatura, Pageable pageable);

    Page<Deputado> findBySiglaUfAndLegislatura(
            String uf, Integer legislatura, Pageable pageable);
}