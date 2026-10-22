// TODO: REMOVER EM PRODUÇÃO

package br.com.univesp.camara.controller;

import br.com.univesp.camara.client.CamaraClient;
import br.com.univesp.camara.etl.EtlService;
import br.com.univesp.camara.model.Votacao;
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

    public AdminController(EtlService etlService, CamaraClient camaraClient) {
        this.etlService = etlService;
        this.camaraClient = camaraClient;
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

//    //teste local pra votacoes é pra apagar depois
//    // http://localhost:8080/api/admin/teste/votacoes (pra ver se está funcionando)
//    @GetMapping("/teste/votacoes")
//    public List<Votacao> testarVotacoes() {
//        return camaraClient.buscarVotacoes(LocalDate.of(2025, 3, 1), LocalDate.of(2025, 3, 31));
//    }
//
//    //teste local pra votos é pra apagar depois
//    // http://localhost:8080/api/admin/teste/votos/2442848-50 (pra ver se está funcionando)
//    @GetMapping("/teste/votos/{votacaoId}")
//    public List<CamaraClient.VotoApi> testarVotos(@PathVariable String votacaoId) {
//        return camaraClient.buscarVotos(votacaoId);
//    }
}