package br.com.univesp.camara.etl;

import br.com.univesp.camara.client.CamaraClient;
import br.com.univesp.camara.model.ProposicaoTema;
import br.com.univesp.camara.model.Tema;
import br.com.univesp.camara.repository.ProposicaoTemaRepository;
import br.com.univesp.camara.repository.TemaRepository;
import br.com.univesp.camara.repository.VotacaoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TemaEtlService {
    private final CamaraClient client;
    private final TemaRepository temaRepo;
    private final ProposicaoTemaRepository proposicaoTemaRepo;
    private final VotacaoRepository votacaoRepo;

    public TemaEtlService(CamaraClient client, TemaRepository temaRepo, ProposicaoTemaRepository proposicaoTemaRepo, VotacaoRepository votacaoRepo) {
        this.client = client;
        this.temaRepo = temaRepo;
        this.proposicaoTemaRepo = proposicaoTemaRepo;
        this.votacaoRepo = votacaoRepo;
    }

    public void sincronizarTemas() {
        System.out.println(">>> ETL: sincronizando temas...");
        List<CamaraClient.TemaApi> temaApis = client.buscarTemas();
        List<Tema> temas = new ArrayList<>();

        for (CamaraClient.TemaApi t : temaApis) {
            if (t.nome() == null){
                System.err.println("  - tema " + t.cod() + " sem nome, ignorado");
                continue;
            }
            Tema tema = new Tema();
            tema.setCod(t.cod());
            tema.setNome(t.nome());
            temas.add(tema);
        }
        temaRepo.saveAll(temas);
    }

    public void sincronizarProposicoesTemas(){
        System.out.println(">>> ETL: sincronizando proposições por tema...");

        Set<Long> relevantes = new HashSet<>(votacaoRepo.findProposicaoIdsDistintos());
        if (relevantes.isEmpty()){
            System.err.println(">>> ETL: nenhuma votação com proposição.");
            return;
        }

        int total = 0;
        for (Tema tema : temaRepo.findAll()){
            try {
                List<Long> ids = client.buscarProposicoesDoTema(tema.getCod());
                Set<Long> jaGravados = new HashSet<>(proposicaoTemaRepo.findProposicaoIdsByTemaCod(tema.getCod()));

                List<Long> novosIds = ids.stream()
                        .distinct()
                        .filter(id -> id != 0)
                        .filter(relevantes::contains)
                        .filter(id -> !jaGravados.contains(id))
                        .toList();

                List<ProposicaoTema> novos = new ArrayList<>();
                for (Long id : novosIds){
                    ProposicaoTema proposicaoTema = new ProposicaoTema();
                    proposicaoTema.setProposicaoId(id);
                    proposicaoTema.setTemaCod(tema.getCod());
                    novos.add(proposicaoTema);
                }
                proposicaoTemaRepo.saveAll(novos);
                total += novos.size();

                System.out.println("   - tema " + tema.getCod() + " (" + tema.getNome() + "): "
                        + ids.size() + " na API, " + novos.size() + " gravados");
            } catch (Exception e) {
                System.err.println("   - Falha no tema " + tema.getCod() + ": " + e.getMessage());
            }
        }
        System.out.println(">>> ETL: " + total + " vínculos tema-proposição gravados.");
    }
}
