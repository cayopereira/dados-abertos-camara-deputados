package br.com.univesp.camara.controller;

import br.com.univesp.camara.dto.DespesaDTO;
import br.com.univesp.camara.dto.ResumoDespesaDTO;
import br.com.univesp.camara.repository.DespesaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

@RestController
@RequestMapping("/api/deputados/{deputadoId}/despesas")
@CrossOrigin(origins = "*")
public class DespesaController {

    private final DespesaRepository despesaRepo;

    public DespesaController(DespesaRepository despesaRepo) {
        this.despesaRepo = despesaRepo;
    }

    @GetMapping
    public Page<DespesaDTO> listar(
            @PathVariable Long deputadoId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return despesaRepo
                .findByDeputadoIdOrderByAnoDescMesDesc(deputadoId, pageable)
                .map(DespesaDTO::from);
    }

    @GetMapping("/resumo")
    public ResponseEntity<ResumoDespesaDTO> resumo(
            @PathVariable Long deputadoId,
            @RequestParam(required = false) Integer ano) {

        int anoConsulta = (ano != null) ? ano : Year.now().getValue();

        List<Object[]> porTipoRaw = despesaRepo.resumoPorTipoDespesa(deputadoId, anoConsulta);
        List<Object[]> mensalRaw = despesaRepo.resumoMensal(deputadoId, anoConsulta);

        if (porTipoRaw.isEmpty() && mensalRaw.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        ResumoDespesaDTO resumo = new ResumoDespesaDTO();
        resumo.setAno(anoConsulta);

        List<ResumoDespesaDTO.ItemResumo> porTipo = porTipoRaw.stream()
                .map(r -> new ResumoDespesaDTO.ItemResumo(
                        (String) r[0],
                        r[1] != null ? (BigDecimal) r[1] : BigDecimal.ZERO))
                .toList();

        List<ResumoDespesaDTO.ItemResumo> mensal = mensalRaw.stream()
                .map(r -> new ResumoDespesaDTO.ItemResumo(
                        String.valueOf(r[0]),
                        r[1] != null ? (BigDecimal) r[1] : BigDecimal.ZERO))
                .toList();

        BigDecimal total = despesaRepo.totalPorDeputadoEAno(deputadoId, anoConsulta);
        resumo.setTotal(total != null ? total : BigDecimal.ZERO);
        resumo.setPorTipo(porTipo);
        resumo.setMensal(mensal);

        return ResponseEntity.ok(resumo);
    }
}