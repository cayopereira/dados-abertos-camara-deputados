package br.com.univesp.camara.controller;

import br.com.univesp.camara.model.Deputado;
import br.com.univesp.camara.repository.DeputadoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deputados")
@CrossOrigin(origins = "*")
public class DeputadoController {

    private static final int LEGISLATURA_ATUAL = 57;

    private final DeputadoRepository repository;

    public DeputadoController(DeputadoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Page<Deputado> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String partido,
            @RequestParam(required = false) String uf,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        var pageable = PageRequest.of(page, size, Sort.by("nome"));

        if (nome != null && !nome.isBlank()) {
            return repository.findByNomeContainingIgnoreCaseAndLegislatura(nome, LEGISLATURA_ATUAL, pageable);
        }
        if (partido != null && !partido.isBlank()) {
            return repository.findBySiglaPartidoAndLegislatura(partido, LEGISLATURA_ATUAL, pageable);
        }
        if (uf != null && !uf.isBlank()) {
            return repository.findBySiglaUfAndLegislatura(uf, LEGISLATURA_ATUAL, pageable);
        }
        return repository.findByLegislatura(LEGISLATURA_ATUAL, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Deputado> detalhar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}