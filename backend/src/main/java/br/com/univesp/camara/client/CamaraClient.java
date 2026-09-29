package br.com.univesp.camara.client;

import br.com.univesp.camara.model.Deputado;
import br.com.univesp.camara.model.Despesa;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        List<Deputado> deputados = new ArrayList<>();
        int pagina = 1;
        boolean temMais = true;

        while (temMais) {
            String url = baseUrl + "/deputados?idLegislatura=" + legislaturaAtual
                    + "&itens=100&pagina=" + pagina + "&ordem=ASC&ordenarPor=nome";

            try {
                String json = restTemplate.getForObject(url, String.class);
                JsonNode root = mapper.readTree(json);
                JsonNode dados = root.get("dados");

                if (dados == null || dados.isEmpty()) {
                    temMais = false;
                    continue;
                }

                for (JsonNode d : dados) {
                    Deputado dep = new Deputado();
                    dep.setId(d.get("id").asLong());
                    dep.setNome(d.get("nome").asText());
                    dep.setNomeEleitoral(d.path("nomeEleitoral").asText(null));
                    dep.setSiglaPartido(d.path("siglaPartido").asText(null));
                    dep.setSiglaUf(d.path("siglaUf").asText(null));
                    dep.setUrlFoto(d.path("urlFoto").asText(null));
                    dep.setEmail(d.path("email").asText(null));
                    dep.setLegislatura(legislaturaAtual);
                    deputados.add(dep);
                }

                // Verifica se há próxima página
                JsonNode links = root.get("links");
                temMais = false;
                for (JsonNode link : links) {
                    if ("next".equals(link.get("rel").asText())) {
                        temMais = true;
                        break;
                    }
                }
                pagina++;

                Thread.sleep(500); // respeita a API
            } catch (Exception e) {
                System.err.println("Erro na página " + pagina + ": " + e.getMessage());
                temMais = false;
            }
        }

        return deputados;
    }

    public List<Despesa> buscarDespesas(Long deputadoId, int ano) {
        List<Despesa> despesas = new ArrayList<>();
        int pagina = 1;
        boolean temMais = true;

        while (temMais) {
            String url = baseUrl + "/deputados/" + deputadoId + "/despesas"
                    + "?idLegislatura=" + legislaturaAtual
                    + "&ano=" + ano
                    + "&itens=50"
                    + "&pagina=" + pagina
                    + "&ordem=ASC"
                    + "&ordenarPor=mes";

            try {
                String json = restTemplate.getForObject(url, String.class);
                JsonNode root = mapper.readTree(json);
                JsonNode dados = root.get("dados");

                if (dados == null || dados.isEmpty()) {
                    break;
                }

                for (JsonNode d : dados) {
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

                    // LOG TEMPORÁRIO
                    System.out.println("   -> codDocumento=" + despesa.getCodDocumento()
                            + " tipo=" + despesa.getTipoDespesa()
                            + " valor=" + despesa.getValorLiquido());

                    despesas.add(despesa);
                }

                // Verifica se há próxima página
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

            } catch (Exception e) {
                System.err.println("Erro ao buscar despesas do deputado " + deputadoId +
                        " (pág. " + pagina + "): " + e.getMessage());
                temMais = false;
            }
        }
        return despesas;
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