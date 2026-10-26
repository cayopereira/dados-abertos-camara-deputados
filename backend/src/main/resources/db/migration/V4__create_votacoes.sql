CREATE TABLE votacoes (
    id                      VARCHAR(30) PRIMARY KEY,
    data                    DATE,
    data_hora_registro      TIMESTAMP,
    descricao               TEXT,
    aprovacao               BOOLEAN,
    sigla_orgao             VARCHAR(20),
    uri                     VARCHAR(255),
    uri_evento              VARCHAR(255),
    uri_orgao               VARCHAR(255),
    uri_proposicao_objeto   VARCHAR(255),
    proposicao_objeto       TEXT,
    created_at              TIMESTAMP NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_votacoes_data ON votacoes(data);
CREATE INDEX idx_votacoes_orgao ON votacoes(sigla_orgao);
