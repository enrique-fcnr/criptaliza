-- ============================================================
-- Apagar tabelas (filhas antes das pais, evitando erros de FK)
-- ============================================================
DROP TABLE T_INVESTIDOR_TRADE  CASCADE CONSTRAINTS;
DROP TABLE T_TRADE             CASCADE CONSTRAINTS;
DROP TABLE T_PERFIL_INVESTIDOR CASCADE CONSTRAINTS;
DROP TABLE T_ORDEM             CASCADE CONSTRAINTS;
DROP TABLE T_CARTEIRA          CASCADE CONSTRAINTS;
DROP TABLE T_INVESTIDOR        CASCADE CONSTRAINTS;
DROP TABLE T_CLIENTE           CASCADE CONSTRAINTS;

-- ============================================================
-- Apagar sequências
-- ============================================================
DROP SEQUENCE seq_investidor_trade;
DROP SEQUENCE seq_trade;
DROP SEQUENCE seq_perfil_investidor;
DROP SEQUENCE seq_ordem;
DROP SEQUENCE seq_carteira;
DROP SEQUENCE seq_investidor;
DROP SEQUENCE seq_cliente;


-- ============================================================
-- Criar sequences
-- ============================================================
CREATE SEQUENCE seq_cliente          START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE seq_investidor       START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE seq_carteira         START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE seq_ordem            START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE seq_perfil_investidor START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE seq_trade            START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE seq_investidor_trade START WITH 1 INCREMENT BY 1 NOCACHE;


-- ============================================================
-- Tabela de Clientes
-- ============================================================
CREATE TABLE T_CLIENTE (
    cd_cliente NUMBER(10)    NOT NULL,
    nm_cliente VARCHAR2(100) NOT NULL,
    email      VARCHAR2(100) NOT NULL,
    telefone   VARCHAR2(20)  NOT NULL,
    idioma     VARCHAR2(50)
);

-- Chave Primária
ALTER TABLE T_CLIENTE
ADD CONSTRAINT PK_CLIENTE PRIMARY KEY (cd_cliente);

-- Comentário da Tabela
COMMENT ON TABLE T_CLIENTE IS 'Tabela mestre que armazena as informações cadastrais e de contato dos clientes da plataforma Criptaliza.';

-- Comentários das Colunas
COMMENT ON COLUMN T_CLIENTE.cd_cliente IS 'Identificador único e exclusivo do cliente. Chave primária (PK) alimentada pela seq_cliente.';
COMMENT ON COLUMN T_CLIENTE.nm_cliente IS 'Nome completo ou razão social do cliente cadastrado.';
COMMENT ON COLUMN T_CLIENTE.email      IS 'Endereço de e-mail principal, utilizado para comunicações oficiais e login no sistema.';
COMMENT ON COLUMN T_CLIENTE.telefone   IS 'Número de telefone de contato, incluindo DDD e, se necessário, código do país (DDI).';
COMMENT ON COLUMN T_CLIENTE.idioma     IS 'Preferência de idioma do cliente para a internacionalização da interface (Ex: PT-BR, EN-US, ES).';


-- ============================================================
-- Tabela de Investidores
-- ============================================================
CREATE TABLE T_INVESTIDOR (
    cd_investidor NUMBER(10)   NOT NULL,
    cd_cliente    NUMBER(10)   NOT NULL,
    nm_persona    VARCHAR2(50) NOT NULL,
    nr_pontuacao  NUMBER(3)    NOT NULL
);

-- Chave Primária
ALTER TABLE T_INVESTIDOR
ADD CONSTRAINT PK_INVESTIDOR PRIMARY KEY (cd_investidor);

-- Chave Estrangeira ligando Investidor ao Cliente
ALTER TABLE T_INVESTIDOR
ADD CONSTRAINT FK_INVESTIDOR_CLIENTE
FOREIGN KEY (cd_cliente) REFERENCES T_CLIENTE (cd_cliente);

-- Comentários (Dicionário de Dados)
COMMENT ON TABLE T_INVESTIDOR IS 'Tabela que vincula o cliente a perfis de investimento específicos.';
COMMENT ON COLUMN T_INVESTIDOR.cd_investidor IS 'ID autoincremental do perfil de investidor.';
COMMENT ON COLUMN T_INVESTIDOR.cd_cliente    IS 'FK referenciando a tabela T_CLIENTE.';
COMMENT ON COLUMN T_INVESTIDOR.nm_persona    IS 'Tipo de perfil (Ex: Holder, Trader).';
COMMENT ON COLUMN T_INVESTIDOR.nr_pontuacao  IS 'Pontuação de risco (0-100).';


-- ============================================================
-- Tabela de Carteira
-- ============================================================
CREATE TABLE T_CARTEIRA (
    cd_carteira   NUMBER(10)   NOT NULL,
    cd_investidor NUMBER(10)   NOT NULL,
    nm_carteira   VARCHAR2(50) NOT NULL,
    dt_criacao    DATE         DEFAULT SYSDATE NOT NULL
);

-- Chave Primária
ALTER TABLE T_CARTEIRA
ADD CONSTRAINT PK_CARTEIRA PRIMARY KEY (cd_carteira);

-- Chave Estrangeira ligando à Carteira ao Investidor
ALTER TABLE T_CARTEIRA
ADD CONSTRAINT FK_CARTEIRA_INVESTIDOR
FOREIGN KEY (cd_investidor) REFERENCES T_INVESTIDOR (cd_investidor);

-- Comentários (Dicionário de Dados)
COMMENT ON TABLE T_CARTEIRA IS 'Tabela que armazena os agrupamentos de ativos (carteiras) de cada investidor.';
COMMENT ON COLUMN T_CARTEIRA.cd_carteira   IS 'ID único da carteira. Gerado pela seq_carteira.';
COMMENT ON COLUMN T_CARTEIRA.cd_investidor IS 'FK referenciando o Investidor dono desta carteira.';
COMMENT ON COLUMN T_CARTEIRA.nm_carteira   IS 'Nome personalizado (Ex: Carteira de Longo Prazo, Criptos de Risco).';
COMMENT ON COLUMN T_CARTEIRA.dt_criacao    IS 'Data de abertura da carteira no sistema.';


-- ============================================================
-- Tabela de Ordem
-- ============================================================
CREATE TABLE T_ORDEM (
    cd_ordem      NUMBER(10)   NOT NULL,
    cd_carteira   NUMBER(10)   NOT NULL,
    nm_ativo      VARCHAR2(20) NOT NULL,
    nr_quantidade NUMBER(18,8) NOT NULL,
    vl_preco      NUMBER(18,2) NOT NULL,
    tp_ordem      VARCHAR2(20) NOT NULL,
    st_ordem      VARCHAR2(20) NOT NULL
);

-- Chave Primária
ALTER TABLE T_ORDEM
ADD CONSTRAINT PK_ORDEM PRIMARY KEY (cd_ordem);

-- Chave Estrangeira ligando à Carteira
ALTER TABLE T_ORDEM
ADD CONSTRAINT FK_ORDEM_CARTEIRA
FOREIGN KEY (cd_carteira) REFERENCES T_CARTEIRA (cd_carteira);

-- Comentários (Dicionário de Dados)
COMMENT ON TABLE T_ORDEM IS 'Tabela que registra as intenções de compra e venda de ativos de uma carteira.';
COMMENT ON COLUMN T_ORDEM.cd_ordem      IS 'ID único da ordem (PK).';
COMMENT ON COLUMN T_ORDEM.cd_carteira   IS 'FK referenciando a carteira que gerou a ordem.';
COMMENT ON COLUMN T_ORDEM.nm_ativo      IS 'Nome ou Ticker do ativo (Ex: BTC).';
COMMENT ON COLUMN T_ORDEM.nr_quantidade IS 'Quantidade de ativos da ordem.';
COMMENT ON COLUMN T_ORDEM.vl_preco      IS 'Preço unitário do ativo no momento da ordem.';
COMMENT ON COLUMN T_ORDEM.tp_ordem      IS 'Tipo da operação: COMPRA ou VENDA.';
COMMENT ON COLUMN T_ORDEM.st_ordem      IS 'Status atual da ordem.';


-- ============================================================
-- Tabela de Perfil do Investidor (1:1 com T_INVESTIDOR)
-- ============================================================
CREATE TABLE T_PERFIL_INVESTIDOR (
    cd_perfil_investidor NUMBER(10)    NOT NULL,
    cd_investidor        NUMBER(10)    NOT NULL,
    tx_objetivo          VARCHAR2(200),
    tx_tolerancia_risco  VARCHAR2(50),
    tx_horizonte         VARCHAR2(50),
    tx_experiencia       VARCHAR2(50),
    tx_preferencias      VARCHAR2(200),
    tx_prevencoes        VARCHAR2(200)
);

-- Chave Primária
ALTER TABLE T_PERFIL_INVESTIDOR
ADD CONSTRAINT PK_PERFIL_INVESTIDOR PRIMARY KEY (cd_perfil_investidor);

-- Chave Estrangeira ligando ao Investidor (relação 1:1)
ALTER TABLE T_PERFIL_INVESTIDOR
ADD CONSTRAINT FK_PERFIL_INVESTIDOR
FOREIGN KEY (cd_investidor) REFERENCES T_INVESTIDOR (cd_investidor);

-- Comentários (Dicionário de Dados)
COMMENT ON TABLE T_PERFIL_INVESTIDOR IS 'Tabela que armazena o perfil detalhado de cada investidor, incluindo objetivos, tolerância a risco e preferências. Relacionamento 1:1 com T_INVESTIDOR.';
COMMENT ON COLUMN T_PERFIL_INVESTIDOR.cd_perfil_investidor IS 'ID único do perfil do investidor (PK). Gerado pela seq_perfil_investidor.';
COMMENT ON COLUMN T_PERFIL_INVESTIDOR.cd_investidor        IS 'FK referenciando o investidor dono deste perfil. Relação 1:1 com T_INVESTIDOR.';
COMMENT ON COLUMN T_PERFIL_INVESTIDOR.tx_objetivo          IS 'Descrição do objetivo financeiro do investidor (Ex: Aposentadoria, Renda passiva).';
COMMENT ON COLUMN T_PERFIL_INVESTIDOR.tx_tolerancia_risco  IS 'Nível de tolerância a risco declarado pelo investidor (Ex: Conservador, Moderado, Arrojado).';
COMMENT ON COLUMN T_PERFIL_INVESTIDOR.tx_horizonte         IS 'Horizonte de tempo do investimento (Ex: Curto prazo, Longo prazo).';
COMMENT ON COLUMN T_PERFIL_INVESTIDOR.tx_experiencia       IS 'Nível de experiência do investidor no mercado de criptoativos (Ex: Iniciante, Intermediário, Avançado).';
COMMENT ON COLUMN T_PERFIL_INVESTIDOR.tx_preferencias      IS 'Preferências de ativos ou estratégias de investimento declaradas pelo investidor.';
COMMENT ON COLUMN T_PERFIL_INVESTIDOR.tx_prevencoes        IS 'Ativos, setores ou estratégias que o investidor deseja evitar.';


-- ============================================================
-- Tabela de Trade (filha de T_ORDEM)
-- ============================================================
CREATE TABLE T_TRADE (
    cd_trade      NUMBER(10)   NOT NULL,
    cd_ordem      NUMBER(10)   NOT NULL,
    vl_preco_exec NUMBER(18,2) NOT NULL,
    nr_quantidade NUMBER(18,8) NOT NULL,
    dt_exec       TIMESTAMP    DEFAULT SYSTIMESTAMP NOT NULL
);

-- Chave Primária
ALTER TABLE T_TRADE
ADD CONSTRAINT PK_T_TRADE PRIMARY KEY (cd_trade);

-- Chave Estrangeira ligando à Ordem
ALTER TABLE T_TRADE
ADD CONSTRAINT FK_TRADE_ORDEM
FOREIGN KEY (cd_ordem) REFERENCES T_ORDEM (cd_ordem);

-- Comentários (Dicionário de Dados)
COMMENT ON TABLE T_TRADE IS 'Tabela que registra as execuções efetivas (trades) geradas a partir das ordens. Cada trade representa uma transação realizada no mercado.';
COMMENT ON COLUMN T_TRADE.cd_trade      IS 'ID único do trade (PK). Gerado pela seq_trade.';
COMMENT ON COLUMN T_TRADE.cd_ordem      IS 'FK referenciando a ordem que originou este trade.';
COMMENT ON COLUMN T_TRADE.vl_preco_exec IS 'Preço unitário pelo qual o ativo foi efetivamente negociado na execução.';
COMMENT ON COLUMN T_TRADE.nr_quantidade IS 'Quantidade de ativos efetivamente negociada neste trade.';
COMMENT ON COLUMN T_TRADE.dt_exec       IS 'Data e hora exata da execução do trade, com precisão de timestamp.';


-- ============================================================
-- Tabela de Investidor-Trade (junção M:N entre T_INVESTIDOR e T_TRADE)
-- ============================================================
CREATE TABLE T_INVESTIDOR_TRADE (
    cd_investidor_trade NUMBER(10) NOT NULL,
    cd_investidor       NUMBER(10) NOT NULL,
    cd_trade            NUMBER(10) NOT NULL
);

-- Chave Primária
ALTER TABLE T_INVESTIDOR_TRADE
ADD CONSTRAINT PK_INVESTIDOR_TRADE PRIMARY KEY (cd_investidor_trade);

-- Chave Estrangeira ligando ao Investidor
ALTER TABLE T_INVESTIDOR_TRADE
ADD CONSTRAINT FK_IT_INVESTIDOR
FOREIGN KEY (cd_investidor) REFERENCES T_INVESTIDOR (cd_investidor);

-- Chave Estrangeira ligando ao Trade
ALTER TABLE T_INVESTIDOR_TRADE
ADD CONSTRAINT FK_IT_TRADE
FOREIGN KEY (cd_trade) REFERENCES T_TRADE (cd_trade);

-- Comentários (Dicionário de Dados)
COMMENT ON TABLE T_INVESTIDOR_TRADE IS 'Tabela de junção que representa o relacionamento muitos-para-muitos (M:N) entre investidores e trades, permitindo rastrear quais investidores participaram de cada trade.';
COMMENT ON COLUMN T_INVESTIDOR_TRADE.cd_investidor_trade IS 'ID único do vínculo investidor-trade (PK). Gerado pela seq_investidor_trade.';
COMMENT ON COLUMN T_INVESTIDOR_TRADE.cd_investidor       IS 'FK referenciando o investidor participante do trade.';
COMMENT ON COLUMN T_INVESTIDOR_TRADE.cd_trade            IS 'FK referenciando o trade associado ao investidor.';
