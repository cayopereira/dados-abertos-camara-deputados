ALTER TABLE votacoes ADD COLUMN proposicao_id BIGINT;

UPDATE votacoes
SET proposicao_id = substring(uri_proposicao_objeto from '[0-9]+$')::BIGINT
WHERE uri_proposicao_objeto IS NOT NULL;

CREATE INDEX idx_votacoes_proposicao ON votacoes (proposicao_id);