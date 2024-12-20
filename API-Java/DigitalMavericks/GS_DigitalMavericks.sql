CREATE TABLE T_DGMA_USUARIO(
    CD_USUARIO                  NUMBER(10) NOT NULL,
    NM_USUARIO                  VARCHAR2(100) NOT NULL,
    DS_EMAIL                    VARCHAR2(100) NOT NULL,
    DS_TELEFONE                 VARCHAR2(15) NOT NULL,
    DT_REGISTRO                 DATE NOT NULL
);

ALTER TABLE T_DGMA_USUARIO ADD CONSTRAINT PK_USUARIO PRIMARY KEY (cd_usuario);
ALTER TABLE T_DGMA_USUARIO ADD CONSTRAINT UN_EMAIL_USUARIO UNIQUE (ds_email);

CREATE TABLE T_DGMA_SIMULACOES(
    CD_SIMULACAO                NUMBER(10) NOT NULL,
    CD_USUARIO                  NUMBER(10) NOT NULL,
    DS_PERCENT_ENERGIA_SOLAR    NUMBER(5,2) NOT NULL,
    DS_CUSTO_INICIAL            NUMBER(12,2) NOT NULL,
    DS_DURACAO_ANOS             NUMBER(3) NOT NULL,
    DT_SIMULACAO                DATE NOT NULL
);

ALTER TABLE T_DGMA_SIMULACOES ADD CONSTRAINT PK_SIMULACAO PRIMARY KEY (cd_simulacao);
ALTER TABLE T_DGMA_SIMULACOES ADD CONSTRAINT FK_CD_USUARIO FOREIGN KEY (cd_usuario) REFERENCES T_DGMA_USUARIO (cd_usuario);

CREATE TABLE T_DGMA_RESULTADO_SIMULACAO(
    CD_RESULTADO                NUMBER(10) NOT NULL,
    CD_SIMULACAO                NUMBER(10) NOT NULL,
    DS_EMISSAO_REDUZIDA         NUMBER(12,2) NOT NULL,
    DS_ECONOMIA_FINANCEIRA      NUMBER(12,2) NOT NULL,
    DS_BENEFICIO_AMBIENTAL      VARCHAR2(500) NOT NULL,
    DS_CUSTO_TOTAL_PROJETADO    NUMBER(12,2) NOT NULL
);

ALTER TABLE T_DGMA_RESULTADO_SIMULACAO ADD CONSTRAINT PK_RESULTADO PRIMARY KEY (cd_resultado);
ALTER TABLE T_DGMA_RESULTADO_SIMULACAO ADD CONSTRAINT FK_CD_SIMULACAO FOREIGN KEY (cd_simulacao) REFERENCES T_DGMA_SIMULACOES (cd_simulacao);

CREATE TABLE T_DGMA_CONTATOS(
    CD_CONTATO                  NUMBER(10) NOT NULL,
    NM_CONTATO                  VARCHAR2(100) NOT NULL,
    DS_EMAIL                    VARCHAR2(100) NOT NULL,
    DS_TELEFONE                  VARCHAR2(15) NOT NULL,
    DS_MENSAGEM                 VARCHAR2(500) NOT NULL,
    DT_CONTATO                  DATE NOT NULL
);

ALTER TABLE T_DGMA_CONTATOS ADD CONSTRAINT PK_CONTATO PRIMARY KEY (cd_contato);
ALTER TABLE T_DGMA_CONTATOS ADD CONSTRAINT UN_EMAIL_CONTATO UNIQUE (ds_email);

CREATE TABLE T_DGMA_CASO_ESTUDO(
    CD_CASO                     NUMBER(10) NOT NULL,
    DS_LOCALIZACAO              VARCHAR2(100) NOT NULL,
    DS_DESCRICAO                VARCHAR2(500) NOT NULL,
    DS_EMISSAO_REDUZIDA         NUMBER(12,2) NOT NULL,
    DS_ECONOMIA_GERADA          NUMBER(12,2) NOT NULL,
    DS_BENEFICIOS_AMBIENTAIS    VARCHAR2(500) NOT NULL
);

ALTER TABLE T_DGMA_CASO_ESTUDO ADD CONSTRAINT PK_CASO PRIMARY KEY (cd_caso);

CREATE TABLE T_DGMA_IMPACTO_ACUMULADO(
    CD_IMPACTO                  NUMBER(10) NOT NULL,
    DT_REGISTRO                 DATE NOT NULL,
    DS_EMISSAO_REDUZIDA_TOTAL   NUMBER(15,2) NOT NULL,
    DS_ECONOMIA_TOTAL           NUMBER(15,2) NOT NULL,
    DS_BENEFICIO_AMBIENTAL      VARCHAR2(500) NOT NULL
);

ALTER TABLE T_DGMA_IMPACTO_ACUMULADO ADD CONSTRAINT PK_IMPACTO PRIMARY KEY (cd_impacto);

CREATE TABLE T_PARAMETROS_CLIMATICOS(
    CD_PARAMETRO                NUMBER(10) NOT NULL,
    DS_LOCALIZACAO              VARCHAR2(100) NOT NULL,
    DS_INSOLACAO_MEDIA          NUMBER(4,2) NOT NULL,
    DS_TEMPERATURA_MEDIA        NUMBER(4,2) NOT NULL,
    DT_REGISTRO                 DATE NOT NULL
);

ALTER TABLE T_PARAMETROS_CLIMATICOS ADD CONSTRAINT PK_PARAMETRO PRIMARY KEY (cd_parametro);

CREATE TABLE T_DGMA_RELATORIOS(
    CD_RELATORIO                NUMBER(10) NOT NULL,
    CD_USUARIO                  NUMBER(10) NOT NULL,
    CD_SIMULACAO                NUMBER(10) NOT NULL,
    DS_TIPO_RELATORIO           VARCHAR2(20) NOT NULL,
    DT_GERACAO                  DATE NOT NULL
);

ALTER TABLE T_DGMA_RELATORIOS ADD CONSTRAINT PK_RELATORIO PRIMARY KEY (cd_relatorio);
ALTER TABLE T_DGMA_RELATORIOS ADD CONSTRAINT FK_CD_USUARIO_RELATORIO FOREIGN KEY (cd_usuario) REFERENCES T_DGMA_USUARIO (cd_usuario);
ALTER TABLE T_DGMA_RELATORIOS ADD CONSTRAINT FK_CD_SIMULACAO_RELATORIO FOREIGN KEY (cd_simulacao) REFERENCES T_DGMA_SIMULACOES (cd_simulacao);

CREATE TABLE T_DGMA_CONFIGURACOES_PLATAFORMA(
    CD_CONFIGURACAO             NUMBER(10) NOT NULL,
    DS_DESCRICAO                VARCHAR2(200) NOT NULL,
    DS_VALOR                    VARCHAR2(100) NOT NULL,
    DT_ATUALIZACAO              DATE NOT NULL
);

ALTER TABLE T_DGMA_CONFIGURACOES_PLATAFORMA ADD CONSTRAINT PK_CONFIGURACAO PRIMARY KEY (cd_configuracao);

CREATE TABLE T_DGMA_LOGS_ACESSOS(
    CD_LOG                      NUMBER(10) NOT NULL,
    CD_USUARIO                  NUMBER(10) NOT NULL,
    DT_ACESSO                   DATE NOT NULL,
    DS_ACAO_REALIZADA           VARCHAR2(200) NOT NULL
);

ALTER TABLE T_DGMA_LOGS_ACESSOS ADD CONSTRAINT PK_LOG PRIMARY KEY (cd_log);
ALTER TABLE T_DGMA_LOGS_ACESSOS ADD CONSTRAINT FK_CD_USUARIO_LOG FOREIGN KEY (cd_usuario) REFERENCES T_DGMA_USUARIO (cd_usuario);

CREATE SEQUENCE sq_t_dgma_usuarios INCREMENT BY 1 START WITH 1 NOCACHE;
CREATE SEQUENCE sq_t_dgma_simulacoes INCREMENT BY 1 START WITH 1 NOCACHE;
CREATE SEQUENCE sq_t_dgma_resultado_simulacao INCREMENT BY 1 START WITH 1 NOCACHE;
CREATE SEQUENCE sq_t_dgma_contato INCREMENT BY 1 START WITH 1 NOCACHE;
CREATE SEQUENCE sq_t_dgma_casos_estudo INCREMENT BY 1 START WITH 1 NOCACHE;
CREATE SEQUENCE sq_t_dgma_impacto_acumulado INCREMENT BY 1 START WITH 1 NOCACHE;
CREATE SEQUENCE sq_t_dgma_parametros_climaticos INCREMENT BY 1 START WITH 1 NOCACHE;
CREATE SEQUENCE sq_t_dgma_relatorios INCREMENT BY 1 START WITH 1 NOCACHE;
CREATE SEQUENCE sq_t_dgma_configuracoes_plataforma INCREMENT BY 1 START WITH 1 NOCACHE;
CREATE SEQUENCE sq_t_dgma_logs_acessos INCREMENT BY 1 START WITH 1 NOCACHE;
