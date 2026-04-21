-- Apagar tabelas (na ordem inversa da criação para evitar erros de FK)
DROP TABLE T_INVESTIDOR CASCADE CONSTRAINTS;
DROP TABLE T_CLIENTE CASCADE CONSTRAINTS;
DROP TABLE T_CARTEIRA CASCADE CONSTRAINTS;
DROP TABLE T_ORDEM CASCADE CONSTRAINTS;

-- Apagar sequências
DROP SEQUENCE seq_cliente;
DROP SEQUENCE seq_investidor;
DROP SEQUENCE seq_carteira;
DROP SEQUENCE seq_ordem;



-- Criar sequences:
CREATE SEQUENCE seq_cliente START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE seq_investidor START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE seq_carteira START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE seq_ordem START WITH 1 INCREMENT BY 1 NOCACHE;


-- Tabela de Clientes
CREATE TABLE T_CLIENTE (
    cd_cliente NUMBER(10) NOT NULL,
    nm_cliente VARCHAR2(100) NOT NULL,
    email      VARCHAR2(100) NOT NULL,
    telefone   VARCHAR2(20) NOT NULL,
    idioma     VARCHAR2(50)
);

-- CHAVES PRIMÁRIAS (PK)
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


-- Tabela de Investidores
CREATE TABLE T_INVESTIDOR (
    cd_investidor NUMBER(10)   NOT NULL,
    cd_cliente    NUMBER(10)   NOT NULL,
    nm_persona    VARCHAR2(50) NOT NULL,
    nr_pontuacao  NUMBER(3)    NOT NULL
);


-- Adicionando a Chave Estrangeira que liga Investidor ao Cliente
ALTER TABLE T_INVESTIDOR
ADD CONSTRAINT PK_INVESTIDOR PRIMARY KEY (cd_investidor);

-- CHAVES ESTRANGEIRAS (FK)
ALTER TABLE T_INVESTIDOR
ADD CONSTRAINT FK_INVESTIDOR_CLIENTE
FOREIGN KEY (cd_cliente) REFERENCES T_CLIENTE (cd_cliente);

-- Comentários T_INVESTIDOR
COMMENT ON TABLE T_INVESTIDOR IS 'Tabela que vincula o cliente a perfis de investimento específicos.';
COMMENT ON COLUMN T_INVESTIDOR.cd_investidor IS 'ID autoincremental do perfil de investidor.';
COMMENT ON COLUMN T_INVESTIDOR.cd_cliente IS 'FK referenciando a tabela T_CLIENTE.';
COMMENT ON COLUMN T_INVESTIDOR.nm_persona IS 'Tipo de perfil (Ex: Holder, Trader).';
COMMENT ON COLUMN T_INVESTIDOR.nr_pontuacao IS 'Pontuação de risco (0-100).';


-- Tabela de Carteira:
CREATE TABLE T_CARTEIRA (
    cd_carteira   NUMBER(10)   NOT NULL,
    cd_investidor NUMBER(10)   NOT NULL,
    nm_carteira   VARCHAR2(50) NOT NULL,
    dt_criacao    DATE         DEFAULT SYSDATE NOT NULL
);


-- Constraints em Separado
-- Primary Key
ALTER TABLE T_CARTEIRA
ADD CONSTRAINT PK_CARTEIRA PRIMARY KEY (cd_carteira);

-- Foreign Key ligando com o Investidor
ALTER TABLE T_CARTEIRA
ADD CONSTRAINT FK_CARTEIRA_INVESTIDOR
FOREIGN KEY (cd_investidor) REFERENCES T_INVESTIDOR (cd_investidor);

-- Comentários (Dicionário de Dados)
COMMENT ON TABLE T_CARTEIRA IS 'Tabela que armazena os agrupamentos de ativos (carteiras) de cada investidor.';
COMMENT ON COLUMN T_CARTEIRA.cd_carteira   IS 'ID único da carteira. Gerado pela seq_carteira.';
COMMENT ON COLUMN T_CARTEIRA.cd_investidor IS 'FK referenciando o Investidor dono desta carteira.';
COMMENT ON COLUMN T_CARTEIRA.nm_carteira   IS 'Nome personalizado (Ex: Carteira de Longo Prazo, Criptos de Risco).';
COMMENT ON COLUMN T_CARTEIRA.dt_criacao    IS 'Data de abertura da carteira no sistema.';

-- Tabela de Ordem:
- Tabela de Ordem Corrigida:
CREATE TABLE T_ORDEM (
    cd_ordem      NUMBER(10)      NOT NULL,
    cd_carteira   NUMBER(10)      NOT NULL,
    nm_ativo      VARCHAR2(20)    NOT NULL,
    nr_quantidade NUMBER(18,8)    NOT NULL,
    vl_preco      NUMBER(18,2)    NOT NULL, -- ADICIONE ESTA LINHA PARA CORRIGIR O ERRO
    tp_ordem      VARCHAR2(20)    NOT NULL,
    st_ordem      VARCHAR2(20)    NOT NULL
);


-- Constraints em Separado
-- Primary Key
ALTER TABLE T_ORDEM
ADD CONSTRAINT PK_ORDEM PRIMARY KEY (cd_ordem);

-- Foreign Key ligando à Carteira
ALTER TABLE T_ORDEM
ADD CONSTRAINT FK_ORDEM_CARTEIRA
FOREIGN KEY (cd_carteira) REFERENCES T_CARTEIRA (cd_carteira);

-- Comentários (Dicionário de Dados)
COMMENT ON TABLE T_ORDEM IS 'Tabela que registra as intenções de compra e venda de ativos de uma carteira.';
COMMENT ON COLUMN T_ORDEM.cd_ordem IS 'ID único da ordem (PK).';
COMMENT ON COLUMN T_ORDEM.cd_carteira IS 'FK referenciando a carteira que gerou a ordem.';
COMMENT ON COLUMN T_ORDEM.nm_ativo IS 'Nome ou Ticker do ativo (Ex: BTC).';
COMMENT ON COLUMN T_ORDEM.nr_quantidade IS 'Quantidade de ativos da ordem.';
COMMENT ON COLUMN T_ORDEM.tp_ordem IS 'Tipo da operação: COMPRA ou VENDA.';
COMMENT ON COLUMN T_ORDEM.st_ordem IS 'Status atual da ordem.';
COMMENT ON COLUMN T_ORDEM.vl_preco IS 'Preço unitário do ativo no momento da ordem.';


