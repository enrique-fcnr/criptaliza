-----------------------------------------
 -- DML CRIPTALIZA
-----------------------------------------

-- ============================================================
-- 1. POPULANDO T_CLIENTE
-- ============================================================
INSERT INTO T_CLIENTE (cd_cliente, nm_cliente, email, telefone, idioma)
VALUES (seq_cliente.NEXTVAL, 'João Silva', 'joao.silva@email.com', '11988887777', 'PT-BR');

INSERT INTO T_CLIENTE (cd_cliente, nm_cliente, email, telefone, idioma)
VALUES (seq_cliente.NEXTVAL, 'Maria Oliveira', 'maria.invest@email.com', '21977776666', 'PT-BR');

-- ============================================================
-- 2. POPULANDO T_INVESTIDOR
-- ============================================================
INSERT INTO T_INVESTIDOR (cd_investidor, cd_cliente, nm_persona, nr_pontuacao)
VALUES (seq_investidor.NEXTVAL, 1, 'Holder Convicto', 85);

INSERT INTO T_INVESTIDOR (cd_investidor, cd_cliente, nm_persona, nr_pontuacao)
VALUES (seq_investidor.NEXTVAL, 2, 'Day Trader Cripto', 40);

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