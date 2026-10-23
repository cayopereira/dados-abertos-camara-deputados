package br.com.univesp.camara.etl;

import br.com.univesp.camara.client.CamaraClient;
import br.com.univesp.camara.model.Votacao;
import br.com.univesp.camara.repository.VotacaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VotacaoEtlService {
    private static final LocalDate INICIO_LEGISLATURA = LocalDate.of(2023, 2, 1);

    private final CamaraClient client;
    private final VotacaoRepository votacaoRepo;

    public VotacaoEtlService(CamaraClient client, VotacaoRepository votacaoRepo) {
        this.client = client;
        this.votacaoRepo = votacaoRepo;
    }

    //ele assume que tudo antes do ponto de partida já está carregado
    public LocalDate pontoDePartida() {
        return votacaoRepo.findTopByOrderByDataHoraRegistroDesc().map(Votacao::getData).orElse(INICIO_LEGISLATURA);
    }

    public void sincronizarVotacoes() {
        sincronizarVotacoes(pontoDePartida(), LocalDate.now());
    }

    public void sincronizarVotacoes(LocalDate inicio, LocalDate fim) {
        if (inicio.isAfter(fim)) {
            System.err.println(">>> ETL: período inválido (" + inicio + " > " + fim + ")");
            return;
        }

        System.out.println(">>> ETL: votação de " + inicio + " a " + fim);

        int total = 0;
        LocalDate atual = inicio;

        while (!atual.isAfter(fim)) {
            LocalDate fimJanela = atual.plusMonths(3);
            if (fimJanela.isAfter(fim)) fimJanela = fim;

            try {
                List<Votacao> votacoes = client.buscarVotacoes(atual, fimJanela);
                votacaoRepo.saveAll(votacoes);
                total += votacoes.size();
                System.out.println(" - " + atual + " a " + fimJanela + ": " + votacoes.size() + " votações");
                Thread.sleep(300);
            } catch (Exception e) {
                System.err.println(" - Falha na janela " + atual + " a " + fimJanela + ": " + e.getMessage());
                break;
            }

            atual = fimJanela.plusDays(1);
        }
        System.out.println(">>> ETL: " + total + " votações sincronizadas no total.");
    }
}
