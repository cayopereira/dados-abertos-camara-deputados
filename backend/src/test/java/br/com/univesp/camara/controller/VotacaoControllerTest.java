package br.com.univesp.camara.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VotacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveRetornarListaDeVotacoesComSucesso() throws Exception {
        // Testa se o endpoint de listar votações responde com status 200 (OK)
        mockMvc.perform(get("/api/votacoes"))
                .andExpect(status().isOk());
    }

    @Test
    public void deveRetornarNotFoundParaVotacaoInexistente() throws Exception {
        // Testa se o endpoint de detalhes retorna 404 para um ID que não existe
        mockMvc.perform(get("/api/votacoes/id-que-nao-existe"))
                .andExpect(status().isNotFound());
    }
}