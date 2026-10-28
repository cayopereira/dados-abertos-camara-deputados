CREATE TABLE proposicao_temas(
        id BIGSERIAL PRIMARY KEY,
        proposicao_id BIGINT NOT NULL,
        tema_cod INTEGER NOT NULL REFERENCES temas(cod),
        created_at TIMESTAMP NOT NULL DEFAULT NOW(),
        UNIQUE(proposicao_id, tema_cod)
);
CREATE INDEX idx_proposicao_temas_tema ON proposicao_temas(tema_cod);