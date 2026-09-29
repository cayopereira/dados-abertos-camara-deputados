package br.com.univesp.camara.repository;

import br.com.univesp.camara.model.Despesa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    Page<Despesa> findByDeputadoIdOrderByAnoDescMesDesc(Long deputadoId, Pageable pageable);

    List<Despesa> findByDeputadoIdAndAno(Long deputadoId, Integer ano);

    boolean existsByDeputadoIdAndAno(Long deputadoId, Integer ano);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("DELETE FROM Despesa d WHERE d.deputado.id = :deputadoId AND d.ano = :ano")
    void deleteByDeputadoIdAndAno(@Param("deputadoId") Long deputadoId,
                                  @Param("ano") Integer ano);

    @Query("SELECT d.tipoDespesa, SUM(d.valorLiquido) FROM Despesa d " +
            "WHERE d.deputado.id = :deputadoId AND d.ano = :ano " +
            "GROUP BY d.tipoDespesa ORDER BY SUM(d.valorLiquido) DESC")
    List<Object[]> resumoPorTipoDespesa(@Param("deputadoId") Long deputadoId,
                                        @Param("ano") Integer ano);

    @Query("SELECT d.mes, SUM(d.valorLiquido) FROM Despesa d " +
            "WHERE d.deputado.id = :deputadoId AND d.ano = :ano " +
            "GROUP BY d.mes ORDER BY d.mes")
    List<Object[]> resumoMensal(@Param("deputadoId") Long deputadoId,
                                @Param("ano") Integer ano);

    @Query("SELECT SUM(d.valorLiquido) FROM Despesa d " +
            "WHERE d.deputado.id = :deputadoId AND d.ano = :ano")
    java.math.BigDecimal totalPorDeputadoEAno(@Param("deputadoId") Long deputadoId,
                                              @Param("ano") Integer ano);
}