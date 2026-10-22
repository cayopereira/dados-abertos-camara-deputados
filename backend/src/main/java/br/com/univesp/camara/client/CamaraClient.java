package br.com.univesp.camara.client;

import br.com.univesp.camara.CamaraApplication;
import br.com.univesp.camara.model.Deputado;
import br.com.univesp.camara.model.Despesa;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import br.com.univesp.camara.model.Votacao;
import br.com.univesp.camara.model.Voto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CamaraClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${camara.api.base-url}")
    private String baseUrl;

    @Value("${camara.api.legislatura-atual}")
    private Integer legislaturaAtual;

    public List<Deputado> buscarDeputados() {
        String url = baseUrl + "/deputados?idLegislatura=" + legislaturaAtual + "&itens=100&ordem=ASC&ordenarPor=nome";
        return buscarPaginado(url, this::paraDeputado);
    }

    private Deputado paraDeputado(JsonNode d) {
        Deputado dep = new Deputado();
        dep.setId(d.get("id").asLong());

        dep.setNome(d.get("nome").asText());
        dep.setNomeEleitoral(d.path("nomeEleitoral").asText(null));
        dep.setSiglaPartido(d.path("siglaPartido").asText(null));
        dep.setSiglaUf(d.path("siglaUf").asText(null));
        dep.setUrlFoto(d.path("urlFoto").asText(null));
        dep.setEmail(d.path("email").asText(null));
        dep.setLegislatura(legislaturaAtual);

        return dep;
    }

    public List<Despesa> buscarDespesas(Long deputadoId, int ano) {
        String url = baseUrl + "/deputados/" + deputadoId + "/despesas"
                + "?idLegislatura=" + legislaturaAtual
                + "&ano=" + ano
                + "&itens=100&ordem=ASC&ordenarPor=mes";
        return buscarPaginado(url, this::paraDespesa);
    }

    private Despesa paraDespesa(JsonNode d) {
        Despesa despesa = new Despesa();

        despesa.setAno(d.path("ano").asInt());
        despesa.setMes(d.path("mes").asInt());
        despesa.setTipoDespesa(textOrNull(d, "tipoDespesa"));
        despesa.setCodDocumento(textOrNull(d, "codDocumento"));

        // dataDocumento: pode vir como "2026-02-20T00:00:00" ou null
        String dataStr = textOrNull(d, "dataDocumento");
        despesa.setDataDocumento(
                dataStr != null ? LocalDate.parse(dataStr.substring(0, 10)) : null
        );

        despesa.setValorDocumento(decimalOrNull(d, "valorDocumento"));
        despesa.setValorGlosa(decimalOrNull(d, "valorGlosa"));
        despesa.setValorLiquido(decimalOrNull(d, "valorLiquido"));

        despesa.setNomeFornecedor(textOrNull(d, "nomeFornecedor"));
        despesa.setCnpjCpfFornecedor(textOrNull(d, "cnpjCpfFornecedor"));

        return despesa;
    }

    public List<Votacao> buscarVotacoes(LocalDate inicio, LocalDate fim) {
        String url = baseUrl + "/votacoes?dataInicio=" + inicio + "&dataFim=" + fim
                + "&itens=100&ordem=ASC&ordenarPor=dataHoraRegistro";
        return buscarPaginado(url, this::paraVotacao);
    }

    private Votacao paraVotacao(JsonNode d) {
        Votacao votacao = new Votacao();

        votacao.setId(d.get("id").asText());

        String dataStr = textOrNull(d, "data");
        votacao.setData(
                dataStr != null ? LocalDate.parse(dataStr.substring(0, 10)) : null
        );

        String dataStrHora = textOrNull(d, "dataHoraRegistro");
        votacao.setDataHoraRegistro(
                dataStrHora != null ? LocalDateTime.parse(dataStrHora) : null
        );

        votacao.setDescricao(textOrNull(d, "descricao"));

        JsonNode aprov = d.path("aprovacao");
        votacao.setAprovacao(aprov.isMissingNode() || aprov.isNull() ? null : aprov.asInt() == 1);

        votacao.setSiglaOrgao(d.path("siglaOrgao").asText(null));
        votacao.setUri(d.path("uri").asText(null));
        votacao.setUriEvento(d.path("uriEvento").asText(null));
        votacao.setUriOrgao(d.path("uriOrgao").asText(null));
        votacao.setUriProposicaoObjeto(d.path("uriProposicaoObjeto").asText(null));
        votacao.setProposicaoObjeto(d.path("proposicaoObjeto").asText(null));

        return votacao;
    }

    public List<VotoApi> buscarVotos(String votacaoId) {
        String url = baseUrl + "/votacoes/" + votacaoId + "/votos";
        return buscarSemPaginacao(url, this::paraVoto);
    }

    public record VotoApi(Long deputadoId, String tipoVoto, String siglaPartido, LocalDateTime dataRegistro) {
    }

    private VotoApi paraVoto(JsonNode d) {
        JsonNode dep = d.path("deputado_");
        String dataStrRegistro = textOrNull(d, "dataRegistroVoto");
        return new VotoApi(
                dep.path("id").asLong(),
                textOrNull(d, "tipoVoto"),
                textOrNull(dep, "siglaPartido"),
                dataStrRegistro != null ? LocalDateTime.parse(dataStrRegistro) : null);
    }

    //Buscador refatorado
    private <T> List<T> buscarPaginado(String urlBase, Function<JsonNode, T> conversor) {
        List<T> resultado = new ArrayList<>();
        int pagina = 1;
        boolean temMais = true;

        while (temMais) {
            String separador = urlBase.contains("?") ? "&" : "?";
            String url = urlBase + separador + "&pagina=" + pagina;
            try {
                String json = restTemplate.getForObject(url, String.class);
                JsonNode root = mapper.readTree(json);
                JsonNode dados = root.get("dados");

                if (dados == null || dados.isEmpty()) {
                    break;
                }

                for (JsonNode item : dados) {
                    resultado.add(conversor.apply(item));
                }

                JsonNode links = root.get("links");
                temMais = false;
                if (links != null) {
                    for (JsonNode link : links) {
                        if ("next".equals(link.path("rel").asText())) {
                            temMais = true;
                            break;
                        }
                    }
                }
                pagina++;
                Thread.sleep(300);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                temMais = false;
            } catch (Exception e) {
                System.err.println("Erro na página " + pagina + " de " + urlBase + ": " + e.getMessage());
                temMais = false;
            }
        }
        return resultado;
    }

    private <T> List<T> buscarSemPaginacao(String url, Function<JsonNode, T> conversor) {
        List<T> resultado = new ArrayList<>();
        try {
            String json = restTemplate.getForObject(url, String.class);
            JsonNode dados = mapper.readTree(json).path("dados");
            for (JsonNode item : dados) {
                resultado.add(conversor.apply(item));
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar " + url + ": " + e.getMessage());
        }
        return resultado;
    }

// Helpers para leitura segura de campos JSON

    private String textOrNull(JsonNode node, String field) {
        JsonNode value = node.get(field);
        if (value == null || value.isNull()) return null;
        String text = value.asText();
        return text.isBlank() ? null : text;
    }

    private BigDecimal decimalOrNull(JsonNode node, String field) {
        JsonNode value = node.get(field);
        if (value == null || value.isNull()) return null;
        try {
            return new BigDecimal(value.asText());
        } catch (NumberFormatException e) {
            return null;
        }
    }

}