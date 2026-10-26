CREATE TABLE votos(
    id                      BIGSERIAL PRIMARY KEY,
    deputado_id             BIGINT NOT NULL REFERENCES deputados(id),
    votacao_id              VARCHAR(30) NOT NULL REFERENCES votacoes(id),
    tipo_voto               VARCHAR(30),
    sigla_partido           VARCHAR(20),
    data_registro_voto      TIMESTAMP,
    created_at              TIMESTAMP NOT NULL DEFAULT NOW(),
    UNIQUE(deputado_id, votacao_id)
);

CREATE INDEX idx_votos_votacao ON votos (votacao_id);
