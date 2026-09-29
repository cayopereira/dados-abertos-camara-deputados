CREATE TABLE despesas (
                          id                      BIGSERIAL PRIMARY KEY,
                          deputado_id             BIGINT NOT NULL REFERENCES deputados(id),
                          ano                     INTEGER NOT NULL,
                          mes                     INTEGER NOT NULL,
                          tipo_despesa            VARCHAR(200),
                          cod_documento           VARCHAR(100),
                          data_documento          DATE,
                          valor_documento         NUMERIC(12, 2),
                          valor_glosa             NUMERIC(12, 2),
                          valor_liquido           NUMERIC(12, 2),
                          nome_fornecedor         VARCHAR(255),
                          cnpj_cpf_fornecedor     VARCHAR(20),
                          created_at              TIMESTAMP NOT NULL DEFAULT NOW(),
                          UNIQUE (deputado_id, cod_documento)
);

CREATE INDEX idx_despesas_deputado ON despesas (deputado_id);
CREATE INDEX idx_despesas_ano_mes ON despesas (ano, mes);
CREATE INDEX idx_despesas_tipo ON despesas (tipo_despesa);
CREATE INDEX idx_despesas_ano ON despesas (ano);