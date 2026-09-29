// TODO: REMOVER EM PRODUÇÃO

package br.com.univesp.camara.controller;

import br.com.univesp.camara.etl.EtlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    private final EtlService etlService;

    public AdminController(EtlService etlService) {
        this.etlService = etlService;
    }

    @PostMapping("/etl/deputados")
    public ResponseEntity<Map<String, Object>> sincronizarDeputados() {
        etlService.sincronizarDeputados();
        return ResponseEntity.ok(Map.of("status", "ok", "mensagem", "Deputados sincronizados."));
    }

    @PostMapping("/etl/despesas/{deputadoId}")
    public ResponseEntity<Map<String, Object>> sincronizarDespesas(
            @PathVariable Long deputadoId,
            @RequestParam(required = false) Integer ano) {

        int anoConsulta = (ano != null) ? ano : Year.now().getValue();
        etlService.sincronizarDespesasDeputado(deputadoId, anoConsulta);

        return ResponseEntity.ok(Map.of(
                "status", "ok",
                "deputadoId", deputadoId,
                "ano", anoConsulta,
                "mensagem", "Despesas sincronizadas."
        ));
    }

    @PostMapping("/etl/despesas")
    public ResponseEntity<Map<String, Object>> sincronizarTodasDespesas() {
        etlService.sincronizarDespesasAnoAtual();
        return ResponseEntity.ok(Map.of("status", "ok", "mensagem", "Despesas de todos os deputados sincronizadas."));
    }
}