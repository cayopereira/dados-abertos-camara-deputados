package br.com.univesp.camara.controller;

import br.com.univesp.camara.dto.TemaResumoDto;
import br.com.univesp.camara.dto.VotacaoDto;
import br.com.univesp.camara.repository.TemaRepository;
import br.com.univesp.camara.repository.VotacaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/temas")
@CrossOrigin(origins = "*")
public class TemaController {

    private final TemaRepository temaRepo;
    private final VotacaoRepository votacaoRepo;


    public TemaController(TemaRepository temaRepo, VotacaoRepository votacaoRepo) {
        this.temaRepo = temaRepo;
        this.votacaoRepo = votacaoRepo;
    }

    @GetMapping
    public List<TemaResumoDto> listar(){
        return temaRepo.findResumos();
    }

    @GetMapping("/{cod}/votacoes")
    public ResponseEntity<Page<VotacaoDto>> votacoes(@PathVariable Integer cod,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "20")int size) {
        if (!temaRepo.existsById(cod)){
            return ResponseEntity.notFound().build();
        }
        Pageable pageable = PageRequest.of(Math.max(page, 0), Math.max(Math.min(size, 100), 1), Sort.by("dataHoraRegistro").descending());
        return ResponseEntity.ok(votacaoRepo.findByTema(cod, pageable).map(VotacaoDto::from));
    }
}
