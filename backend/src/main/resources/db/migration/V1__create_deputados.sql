CREATE TABLE deputados (
                           id              BIGINT PRIMARY KEY,
                           nome            VARCHAR(255) NOT NULL,
                           nome_eleitoral  VARCHAR(255),
                           sigla_partido   VARCHAR(20),
                           sigla_uf        VARCHAR(2),
                           url_foto        VARCHAR(500),
                           email           VARCHAR(255),
                           situacao        VARCHAR(100),
                           legislatura     INTEGER NOT NULL,
                           created_at      TIMESTAMP NOT NULL DEFAULT NOW(),
                           updated_at      TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_deputados_nome ON deputados (nome);
CREATE INDEX idx_deputados_partido ON deputados (sigla_partido);
CREATE INDEX idx_deputados_uf ON deputados (sigla_uf);