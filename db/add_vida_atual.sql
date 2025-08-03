-- Script para adicionar a coluna vida_atual na tabela herois
ALTER TABLE herois ADD COLUMN vida_atual INTEGER NOT NULL DEFAULT 0;

-- Atualizar todos os registros existentes para que vida_atual seja igual a vida_maxima
UPDATE herois SET vida_atual = vida_maxima;
