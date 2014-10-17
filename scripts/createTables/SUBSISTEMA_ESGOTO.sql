CREATE TABLE "GSAN_ADMIN"."SUBSISTEMA_ESGOTO" (
  SSES_ID NUMBER PRIMARY KEY NOT NULL,
  SSES_CDSUBSISTEMA NUMBER NOT NULL,
  SSES_DSSUBSISTEMA VARCHAR(30) NOT NULL,
  SSES_DSABREVSUBSISTEMA VARCHAR(6),
  SESG_ID NUMBER NOT NULL,
  SSES_ICUSO NUMBER(1),
  SSES_TMULTIMAALTERACAO TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  CONSTRAINT FK1_SUBSISTEMA_ESGOTO FOREIGN KEY (SESG_ID) REFERENCES "GSAN_ADMIN"."SISTEMA_ESGOTO" (SESG_ID) ENABLE
);

grant all privileges on gsan_admin.subsistema_esgoto to gsan_oper;
CREATE OR REPLACE PUBLIC SYNONYM SUBSISTEMA_ESGOTO FOR GSAN_ADMIN.SUBSISTEMA_ESGOTO;

-- Coment�rios da Tabela
COMMENT ON COLUMN "GSAN_ADMIN"."SUBSISTEMA_ESGOTO"."SSES_ID"
IS
  'ID DO SUBSISTEMA DE ESGOTO';
COMMENT ON COLUMN "GSAN_ADMIN"."SUBSISTEMA_ESGOTO"."SSES_CDSUBSISTEMA"
IS
  'CODIGO DO SUBSISTEMA DE ESGOTO';
COMMENT ON COLUMN "GSAN_ADMIN"."SUBSISTEMA_ESGOTO"."SSES_DSSUBSISTEMA"
IS
  'DESCRICAO DO SUBSISTEMA DE ESGOTO';
COMMENT ON COLUMN "GSAN_ADMIN"."SUBSISTEMA_ESGOTO"."SSES_DSABREVSUBSISTEMA"
IS
  'DESCRICAO ABREVIADA DO SUBSISTEMA DE ESGOTO';
COMMENT ON COLUMN "GSAN_ADMIN"."SUBSISTEMA_ESGOTO"."SESG_ID"
IS
  'ID DO SISTEMA DE ESGOTO AO QUAL O SUBSISTEMA ESTA VINCULADO';
COMMENT ON COLUMN "GSAN_ADMIN"."SUBSISTEMA_ESGOTO"."SSES_ICUSO"
IS
  'INDICADOR DE USO.
1 - ATIVO (DEFAULT)
2 - INATIVO';
COMMENT ON COLUMN "GSAN_ADMIN"."SUBSISTEMA_ESGOTO"."SSES_TMULTIMAALTERACAO"
IS
  'DATA/HORA DA ULTIMA ALTERACAO';