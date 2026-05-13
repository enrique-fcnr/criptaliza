-----------------------------------------
 -- DML CRIPTALIZA
-----------------------------------------

-- ============================================================
-- 0. LIMPEZA DOS DADOS (DELETE)
-- ============================================================
-- Deletamos na ordem inversa das dependências para não dar erro de FK
DELETE FROM T_INVESTIDOR_TRADE;
DELETE FROM T_TRADE;
DELETE FROM T_ORDEM;
DELETE FROM T_CARTEIRA;
DELETE FROM T_PERFIL_INVESTIDOR;
DELETE FROM T_INVESTIDOR;
DELETE FROM T_CLIENTE;

-- Opcional: Reiniciar as sequences (se desejar que os IDs comecem do 1 novamente)
-- DROP SEQUENCE seq_cliente; CREATE SEQUENCE seq_cliente... (e assim por diante)

COMMIT;

-- ============================================================
-- 1. POPULANDO T_CLIENTE
-- ============================================================
-- 1. Cliente: Beatriz Santos
INSERT INTO T_CLIENTE (cd_cliente, nm_cliente, email, telefone, idioma)
VALUES (seq_cliente.NEXTVAL, 'Beatriz Santos', 'beatriz.santos@email.com', '11912345678', 'PT-BR');

-- 2. Cliente: Ricardo Montgomery (Investidor estrangeiro)
INSERT INTO T_CLIENTE (cd_cliente, nm_cliente, email, telefone, idioma)
VALUES (seq_cliente.NEXTVAL, 'Ricardo Montgomery', 'rick.mont@global.com', '14155550199', 'EN-US');

-- 3. Cliente: Helena Ferreira
INSERT INTO T_CLIENTE (cd_cliente, nm_cliente, email, telefone, idioma)
VALUES (seq_cliente.NEXTVAL, 'Helena Ferreira', 'helena.fer@uol.com.br', '31988774433', 'PT-BR');

-- 4. Cliente: Carlos Eduardo (Dudu)
INSERT INTO T_CLIENTE (cd_cliente, nm_cliente, email, telefone, idioma)
VALUES (seq_cliente.NEXTVAL, 'Carlos Eduardo', 'cadu.invest@outlook.com', '41999887766', 'PT-BR');

-- 5. Cliente: Sofia Martinez
INSERT INTO T_CLIENTE (cd_cliente, nm_cliente, email, telefone, idioma)
VALUES (seq_cliente.NEXTVAL, 'Sofia Martinez', 'sofia.mtz@gmail.com', '51955443322', 'ES-ES');


-- ============================================================
-- 2. POPULANDO T_INVESTIDOR
-- ============================================================
-- 1. Perfil Conservador (Alta aversão ao risco / Baixa tolerância)
INSERT INTO T_INVESTIDOR (cd_investidor, cd_cliente, nm_persona, nr_pontuacao)
VALUES (seq_investidor.NEXTVAL, 1, 'Conservador', 90);

-- 2. Perfil Moderado (Equilíbrio entre segurança e lucro)
INSERT INTO T_INVESTIDOR (cd_investidor, cd_cliente, nm_persona, nr_pontuacao)
VALUES (seq_investidor.NEXTVAL, 2, 'Moderado', 60);

-- 3. Perfil Arrojado (Alta tolerância ao risco / Foco em rentabilidade)
INSERT INTO T_INVESTIDOR (cd_investidor, cd_cliente, nm_persona, nr_pontuacao)
VALUES (seq_investidor.NEXTVAL, 3, 'Arrojado', 35);

-- ============================================================
-- 3. POPULANDO T_PERFIL_INVESTIDOR (Relação 1:1)
-- ============================================================
-- Inserindo o Perfil para o Investidor 1
INSERT INTO T_PERFIL_INVESTIDOR (
    cd_perfil_investidor, 
    cd_investidor, 
    tx_objetivo, 
    tx_tolerancia_risco, 
    tx_horizonte, 
    tx_experiencia
) VALUES (
    seq_perfil_investidor.NEXTVAL, 
    1, 
    'Aposentadoria com Bitcoin', 
    'Arrojado', 
    '10 anos', 
    'Avançado'
);

-- Inserindo o Perfil para o Investidor 2
INSERT INTO T_PERFIL_INVESTIDOR (
    cd_perfil_investidor, 
    cd_investidor, 
    tx_objetivo, 
    tx_tolerancia_risco, 
    tx_horizonte, 
    tx_experiencia
) VALUES (
    seq_perfil_investidor.NEXTVAL, 
    2, 
    'Renda extra diária', 
    'Agressivo', 
    'Curto Prazo', 
    'Intermediário'
);

-- Não esqueça do commit para salvar as alterações
COMMIT;
-- ============================================================
-- 4. POPULANDO T_CARTEIRA
-- ============================================================
INSERT INTO T_CARTEIRA (cd_carteira, cd_investidor, nm_carteira, dt_criacao)
VALUES (seq_carteira.NEXTVAL, 1, 'Hold de Valor', TO_DATE('2024-01-10', 'YYYY-MM-DD'));

INSERT INTO T_CARTEIRA (cd_carteira, cd_investidor, nm_carteira, dt_criacao)
VALUES (seq_carteira.NEXTVAL, 2, 'Especulação Altcoins', SYSDATE);

-- ============================================================
-- 5. POPULANDO T_ORDEM
-- ============================================================
INSERT INTO T_ORDEM (cd_ordem, cd_carteira, nm_ativo, nr_quantidade, vl_preco, tp_ordem, st_ordem)
VALUES (seq_ordem.NEXTVAL, 1, 'BTC', 0.05, 350000.00, 'COMPRA', 'EXECUTADA');

INSERT INTO T_ORDEM (cd_ordem, cd_carteira, nm_ativo, nr_quantidade, vl_preco, tp_ordem, st_ordem)
VALUES (seq_ordem.NEXTVAL, 2, 'ETH', 1.5, 12000.00, 'COMPRA', 'EXECUTADA');

-- ============================================================
-- 6. POPULANDO T_TRADE
-- ============================================================
INSERT INTO T_TRADE (cd_trade, cd_ordem, vl_preco_exec, nr_quantidade, dt_exec)
VALUES (seq_trade.NEXTVAL, 1, 349500.00, 0.05, CURRENT_TIMESTAMP);

INSERT INTO T_TRADE (cd_trade, cd_ordem, vl_preco_exec, nr_quantidade, dt_exec)
VALUES (seq_trade.NEXTVAL, 2, 12050.00, 1.5, CURRENT_TIMESTAMP);

-- ============================================================
-- 7. POPULANDO T_INVESTIDOR_TRADE (M:N)
-- ============================================================
INSERT INTO T_INVESTIDOR_TRADE (cd_investidor_trade, cd_investidor, cd_trade)
VALUES (seq_investidor_trade.NEXTVAL, 1, 1);

INSERT INTO T_INVESTIDOR_TRADE (cd_investidor_trade, cd_investidor, cd_trade)
VALUES (seq_investidor_trade.NEXTVAL, 2, 2);


-- Verificar Clientes
SELECT * FROM T_CLIENTE;

-- Verificar Investidores (Personas)
SELECT * FROM T_INVESTIDOR;

-- Verificar Perfis Detalhados (O que deu erro antes)
SELECT * FROM T_PERFIL_INVESTIDOR;

-- Verificar Carteiras criadas
SELECT * FROM T_CARTEIRA;

-- Verificar Ordens enviadas
SELECT * FROM T_ORDEM;

-- Verificar Trades executados
SELECT * FROM T_TRADE;

-- Verificar a tabela de junção (M:N)
SELECT * FROM T_INVESTIDOR_TRADE;
-- Confirmação das alterações
COMMIT;