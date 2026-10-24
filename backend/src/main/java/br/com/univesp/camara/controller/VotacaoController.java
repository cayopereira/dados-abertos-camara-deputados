package br.com.univesp.camara.controller;

import br.com.univesp.camara.dto.VotacaoDto;
import br.com.univesp.camara.repository.VotacaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Locale;

@RestController
@RequestMapping("/api/votacoes")
@CrossOrigin(origins = "http://localhost:5173")
public class VotacaoController {

    private final VotacaoRepository votacaoRepo;

    public VotacaoController(VotacaoRepository votacaoRepo) {
        this.votacaoRepo = votacaoRepo;
    }

    @GetMapping
    public Page<VotacaoDto> listar(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate fim,
            @RequestParam(required = false) String orgao,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        var pageable = PageRequest.of(page, Math.min(size, 100), Sort.by("dataHoraRegistro").descending());


        if(inicio != null || fim != null){
            LocalDate de = (inicio != null) ? inicio : LocalDate.of(2023, 2, 1);
            LocalDate ate = (fim != null) ? fim : LocalDate.now();
            return votacaoRepo.findByDataBetween(de, ate, pageable).map(VotacaoDto::from);
        }
        if (orgao != null && !orgao.isBlank()){
            String sigla = orgao.trim().toUpperCase(Locale.ROOT);
            return votacaoRepo.findBySiglaOrgao(sigla, pageable).map(VotacaoDto::from);
        }

        return votacaoRepo.findAll(pageable).map(VotacaoDto::from);
    }
}
