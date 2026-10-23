// TODO: REMOVER EM PRODUÇÃO

package br.com.univesp.camara.controller;

import br.com.univesp.camara.client.CamaraClient;
import br.com.univesp.camara.etl.EtlService;
import br.com.univesp.camara.etl.VotacaoEtlService;
import br.com.univesp.camara.etl.VotoEtlService;
import br.com.univesp.camara.model.Votacao;
import br.com.univesp.camara.model.Voto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    private final EtlService etlService;
    private final CamaraClient camaraClient;
    private final VotacaoEtlService votacaoEtlService;
    private final VotoEtlService votoEtlService;

    public AdminController(EtlService etlService, CamaraClient camaraClient, VotacaoEtlService votacaoEtlService, VotoEtlService votoEtlService) {
        this.etlService = etlService;
        this.camaraClient = camaraClient;
        this.votacaoEtlService = votacaoEtlService;
        this.votoEtlService = votoEtlService;
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

    //curl.exe -X POST http://localhost:8080/api/admin/etl/votacoes pra carregar votacoes
    @PostMapping("/etl/votacoes")
    public ResponseEntity<Map<String, Object>> sincronizarVotacoes(@RequestParam(required = false) LocalDate inicio, LocalDate fim) {

        LocalDate de = (inicio != null) ? inicio : votacaoEtlService.pontoDePartida();
        LocalDate ate = (fim != null) ? fim : LocalDate.now();


        if (de.isAfter(ate)) {
            return ResponseEntity.badRequest().body(Map.of("status", "erro", "mensagem", "A data inicial não pode ser posterior à final."));
        }
        votacaoEtlService.sincronizarVotacoes(de, ate);
        return ResponseEntity.ok(Map.of("status", "ok", "inicio", de.toString(), "fim", toString(), "mensagem", "Votações sincronizadas (incremental)."));

    }

    @PostMapping("/etl/votos")
    public ResponseEntity<Map<String, Object>> sincronizarVotos(@RequestParam(defaultValue = "5") int limite) {
        votoEtlService.sincronizarVotos(limite);
        return ResponseEntity.ok(Map.of("status", "ok", "limite", limite, "mensagem", "votos sincronizados"));
    }
}