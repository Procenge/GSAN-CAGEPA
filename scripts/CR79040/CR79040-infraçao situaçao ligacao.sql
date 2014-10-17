CREATE TABLE GSAN_ADMIN.INFRACAO_LIGACAO_SITUACAO 
(INLS_ID NUMBER(4, 0) NOT NULL, 
 INLS_DSLIGACAOSITUACAO VARCHAR2(30 BYTE) NOT NULL, 
 INLS_DSABVLIGACAOSITUACAO CHAR(10) NOT NULL, 
 INLS_TMULTIMAALTERACAO TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP NOT NULL, 
 INLS_ICUSO NUMBER(4,0) DEFAULT 1 NOT NULL, 
 CONSTRAINT INFRACAO_LIGACAO_SITUACAO PRIMARY KEY (INLS_ID) ENABLE);

CREATE SEQUENCE GSAN_ADMIN.SQ_INFRACAO_LIGACAO_SITUACAO INCREMENT BY 1 MAXVALUE 999999999999999999999999999 MINVALUE 1 CACHE 20;

grant all privileges on GSAN_ADMIN.INFRACAO_LIGACAO_SITUACAO to gsan_oper;
grant all privileges on GSAN_ADMIN.SQ_INFRACAO_LIGACAO_SITUACAO to gsan_oper;

CREATE OR REPLACE PUBLIC SYNONYM INFRACAO_LIGACAO_SITUACAO FOR GSAN_ADMIN.INFRACAO_LIGACAO_SITUACAO;
CREATE OR REPLACE PUBLIC SYNONYM SQ_INFRACAO_LIGACAO_SITUACAO FOR GSAN_ADMIN.SQ_INFRACAO_LIGACAO_SITUACAO;

 INSERT
   INTO FUNCIONALIDADE
  (
    FNCD_ID               ,
    MODU_ID               ,
    FNCD_DSFUNCIONALIDADE ,
    FNCD_TMULTIMAALTERACAO,
    FNCD_DSCAMINHOMENU    ,
    FNCD_DSCAMINHOURL     ,
    FNCD_ICPONTOENTRADA   ,
    FNCD_DSABREVIADO      ,
    FNCD_NNORDEMMENU      ,
    FNCD_ICNOVAJANELA
  )
  VALUES
  (
    1117,3,'Remover Situacao de Infracao do Ligacao',current_timestamp,'Gsan/Cobranca/Infracao/Situacao de Infracao do Ligacao','removerInfracaoLigacaoSituacaoAction,do',2,'ReOrEx',0,2
  );
  
 INSERT
   INTO FUNCIONALIDADE
  (
    FNCD_ID               ,
    MODU_ID               ,
    FNCD_DSFUNCIONALIDADE ,
    FNCD_TMULTIMAALTERACAO,
    FNCD_DSCAMINHOMENU    ,
    FNCD_DSCAMINHOURL     ,
    FNCD_ICPONTOENTRADA   ,
    FNCD_DSABREVIADO      ,
    FNCD_NNORDEMMENU      ,
    FNCD_ICNOVAJANELA
  )
  VALUES
  (
    1116,3,'Atualizar Situacao de Infracao do Ligacao',current_timestamp,'Gsan/Cobranca/Infracao/Situacao de Infracao do Ligacao','exibirAtualizarInfracaoLigacaoSituacaoAction,do',2,'ExAtuOE',0,2
  );
  
 INSERT
   INTO FUNCIONALIDADE
  (
    FNCD_ID               ,
    MODU_ID               ,
    FNCD_DSFUNCIONALIDADE ,
    FNCD_TMULTIMAALTERACAO,
    FNCD_DSCAMINHOMENU    ,
    FNCD_DSCAMINHOURL     ,
    FNCD_ICPONTOENTRADA   ,
    FNCD_DSABREVIADO      ,
    FNCD_NNORDEMMENU      ,
    FNCD_ICNOVAJANELA
  )
  VALUES
  (
    1115,3,'Manter Situacao de Infracao do Ligacao',current_timestamp,'Gsan/Cobranca/Infracao/Situacao de Infracao do Ligacao','exibirFiltrarInfracaoLigacaoSituacaoAction,do',1,'EFOrExA',0,2
  );
  
 INSERT
   INTO FUNCIONALIDADE
  (
    FNCD_ID               ,
    MODU_ID               ,
    FNCD_DSFUNCIONALIDADE ,
    FNCD_TMULTIMAALTERACAO,
    FNCD_DSCAMINHOMENU    ,
    FNCD_DSCAMINHOURL     ,
    FNCD_ICPONTOENTRADA   ,
    FNCD_DSABREVIADO      ,
    FNCD_NNORDEMMENU      ,
    FNCD_ICNOVAJANELA
  )
  VALUES
  (
    1114,3,'Inserir Situacao de Infracao do Ligacao',current_timestamp,'Gsan/Cobranca/Infracao/Situacao de Infracao do Ligacao','exibirInserirInfracaoLigacaoSituacaoAction,do',1,'EIOrEx',0,2
  );
  
   INSERT
   INTO OPERACAO
  (
    OPER_ID                ,
    FNCD_ID                ,
    OPER_DSOPERACAO        ,
    OPER_DSABREVIADO       ,
    OPER_DSCAMINHOURL      ,
    OPER_TMULTIMAALTERACAO ,
    OPER_IDOPERACAOPESQUISA,
    TBCO_ID                ,
    OPTP_ID                ,
    TBCO_IDARGUMENTO
  )
  VALUES
  (
    2554,1117,
    'Remover Situacao de Infracao do Ligacao'                           ,
    'ReOrEx'                                                            ,
    'removerInfracaoLigacaoSituacaoAction,do'                           ,
    current_timestamp													,
    NULL                                                                ,
    NULL                                                                ,
    2                                                                   ,
    NULL
  );
 INSERT
   INTO OPERACAO
  (
    OPER_ID                ,
    FNCD_ID                ,
    OPER_DSOPERACAO        ,
    OPER_DSABREVIADO       ,
    OPER_DSCAMINHOURL      ,
    OPER_TMULTIMAALTERACAO ,
    OPER_IDOPERACAOPESQUISA,
    TBCO_ID                ,
    OPTP_ID                ,
    TBCO_IDARGUMENTO
  )
  VALUES
  (
    2553,1116,
    'Atualizar Situacao de Infracao do Ligacao'                         ,
    'AtuOrEx'                                                           ,
    'atualizarInfracaoLigacaoSituacaoAction,do'                         ,
    current_timestamp													,
    NULL                                                                ,
    NULL                                                                ,
    3                                                                   ,
    NULL
  );
 INSERT
   INTO OPERACAO
  (
    OPER_ID                ,
    FNCD_ID                ,
    OPER_DSOPERACAO        ,
    OPER_DSABREVIADO       ,
    OPER_DSCAMINHOURL      ,
    OPER_TMULTIMAALTERACAO ,
    OPER_IDOPERACAOPESQUISA,
    TBCO_ID                ,
    OPTP_ID                ,
    TBCO_IDARGUMENTO
  )
  VALUES
  (
    2552,1115,
    'Filtrar Situacao de Infracao do Ligacao'                           ,
    'ForExAc'                                                           ,
    'filtrarInfracaoLigacaoSituacaoAction,do'                           ,
    current_timestamp													,
    NULL                                                                ,
    NULL                                                                ,
    5                                                                   ,
    NULL
  );
 INSERT
   INTO OPERACAO
  (
    OPER_ID                ,
    FNCD_ID                ,
    OPER_DSOPERACAO        ,
    OPER_DSABREVIADO       ,
    OPER_DSCAMINHOURL      ,
    OPER_TMULTIMAALTERACAO ,
    OPER_IDOPERACAOPESQUISA,
    TBCO_ID                ,
    OPTP_ID                ,
    TBCO_IDARGUMENTO
  )
  VALUES
  (
    2551,1114,
    'Inserir Situacao de Infracao do Ligacao'                           ,
    'InOrExt'                                                           ,
    'inserirInfracaoLigacaoSituacaoAction,do'                           ,
    current_timestamp													,
    NULL                                                                ,
    NULL                                                                ,
    4                                                                   ,
    NULL
  );
  
  
  
  insert into GSAN_ADMIN.GRUPO_FUNCIONALIDADE_OPERACAO (grup_id,oper_id,fncd_id,gfop_tmultimaalteracao)
  values (1,2554,1117,current_timestamp);
  commit;
   insert into GSAN_ADMIN.GRUPO_FUNCIONALIDADE_OPERACAO (grup_id,oper_id,fncd_id,gfop_tmultimaalteracao)
  values (1,2553,1116,current_timestamp);
  commit;
   insert into GSAN_ADMIN.GRUPO_FUNCIONALIDADE_OPERACAO (grup_id,oper_id,fncd_id,gfop_tmultimaalteracao)
  values (1,2552,1115,current_timestamp);
  commit;
   insert into GSAN_ADMIN.GRUPO_FUNCIONALIDADE_OPERACAO (grup_id,oper_id,fncd_id,gfop_tmultimaalteracao)
  values (1,2551,1114,current_timestamp);
  commit;
  