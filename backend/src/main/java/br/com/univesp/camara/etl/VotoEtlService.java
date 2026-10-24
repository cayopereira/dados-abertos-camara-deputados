package br.com.univesp.camara.etl;

import br.com.univesp.camara.client.CamaraClient;
import br.com.univesp.camara.model.Deputado;
import br.com.univesp.camara.model.Votacao;
import br.com.univesp.camara.model.Voto;
import br.com.univesp.camara.repository.DeputadoRepository;
import br.com.univesp.camara.repository.VotacaoRepository;
import br.com.univesp.camara.repository.VotoRepository;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VotoEtlService {
    private final CamaraClient client;
    private final VotacaoRepository votacaoRepo;
    private final VotoRepository votoRepo;
    private final DeputadoRepository deputadoRepo;

    public VotoEtlService(CamaraClient client, VotacaoRepository votacaoRepo, VotoRepository votoRepo, DeputadoRepository deputadoRepo) {
        this.client = client;
        this.votacaoRepo = votacaoRepo;
        this.votoRepo = votoRepo;
        this.deputadoRepo = deputadoRepo;
    }

    public void sincronizarVotos(int limite) {
        System.out.println(">>> ETL: sincronizando votos de até " + limite + " votações...");
        Set<Long> idsDeputados = deputadoRepo.findAll().stream().map(Deputado::getId).collect(Collectors.toSet());

        List<Votacao> pendentes = votacaoRepo.findByVotosCarregadosFalseOrderByDataDesc(PageRequest.of(0, limite));

        int votosSalvos = 0;
        int ignorados = 0;

        for (Votacao votacao : pendentes) {
            try {

                List<CamaraClient.VotoApi> votoApis = client.buscarVotos(votacao.getId());
                List<Voto> votos = new ArrayList<>();
                for (CamaraClient.VotoApi v : votoApis) {
                    if (!idsDeputados.contains(v.deputadoId())) {
                        ignorados++;
                        continue;
                    }
                    Voto voto = new Voto();
                    voto.setVotacao(votacao);
                    voto.setDeputado(deputadoRepo.getReferenceById(v.deputadoId()));
                    voto.setTipoVoto(v.tipoVoto());
                    voto.setSiglaPartido(v.siglaPartido());
                    voto.setDataRegistroVoto(v.dataRegistro());
                    votos.add(voto);
                }
                votoRepo.saveAll(votos);
                votacao.setVotosCarregados(true);
                votacaoRepo.save(votacao);
                votosSalvos += votos.size();
                Thread.sleep(500);
            } catch (Exception e) {
                System.err.println(" - Falha na votação " + votacao.getId() + ": " + e.getMessage());
            }
        }
        System.out.println(">>> ETL: " + votosSalvos + " votos salvos, " + ignorados + " ignorados");
    }
}
