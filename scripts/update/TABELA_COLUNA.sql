--Péricles Tavares
update TABELA_COLUNA set TBCO_DSCOLUNA = 'ID da Tabela Setor de Abastecimento' where TBCO_ID = 40276;
update TABELA_COLUNA set TBCO_DSCOLUNA = 'Descricao do Setor de Abastecimento' where TBCO_ID = 40277;
update TABELA_COLUNA set TBCO_DSCOLUNA = 'Descricao do Setor de Abastecimento' where TBCO_ID = 40278;
update TABELA_COLUNA set TBCO_DSCOLUNA = 'ID do Sistema de Abastecimento' where TBCO_ID = 40281;
UPDATE TABELA_COLUNA set TBCO_NMCOLUNA = lower(TBCO_NMCOLUNA) where TABE_ID = 435 AND TBCO_ID = TBCO_ID

-- Anderson Italo
UPDATE TABELA_COLUNA SET TBCO_ICPRIMARYKEY = 1 WHERE TBCO_ID=40289
UPDATE TABELA_COLUNA SET TBCO_NMCOLUNA = 'sabs_id' WHERE TBCO_ID=40289;
UPDATE TABELA_COLUNA SET TBCO_NMCOLUNA = 'sabs_dssistemaabastecimento' WHERE TBCO_ID=40290;
UPDATE TABELA_COLUNA SET TBCO_NMCOLUNA = 'sabs_dsabreviado' WHERE TBCO_ID=40291;
UPDATE TABELA_COLUNA SET TBCO_NMCOLUNA = 'sabs_icuso' WHERE TBCO_ID=40292;
UPDATE TABELA_COLUNA SET TBCO_NMCOLUNA = 'sabs_tmultimalateracao' WHERE TBCO_ID=40293;
UPDATE TABELA_COLUNA SET TBCO_NMCOLUNA = 'sabs_cdsistemaabastecimento' WHERE TBCO_ID=45851;
update TABELA_COLUNA set TBCO_DSCOLUNA = 'IDENTIFICADOR' WHERE TBCO_ID=40289;
update TABELA_COLUNA set TBCO_DSCOLUNA = 'DESCRICAO' WHERE TBCO_ID=40290;
update TABELA_COLUNA set TBCO_DSCOLUNA = 'DESCRICAO ABREVIADA' WHERE TBCO_ID=40291;

-- Anderson Italo
UPDATE TABELA_COLUNA SET TBCO_ICPRIMARYKEY = 1 WHERE TBCO_ID = 38434;
UPDATE TABELA_COLUNA SET TBCO_ICPRIMARYKEY = 1 WHERE TBCO_ID = 38880;
UPDATE TABELA_COLUNA SET TBCO_NMCOLUNA = lower(TBCO_NMCOLUNA) WHERE TABE_ID = 267;
UPDATE TABELA_COLUNA SET TBCO_NMCOLUNA = lower(TBCO_NMCOLUNA) WHERE TABE_ID = 295;
UPDATE TABELA_COLUNA SET TBCO_DSCOLUNA = 'ID DA FONTE DE DADOS CENSITARIOS (TAB. FONTE_DADOS_CENSITARIOS)' WHERE TBCO_ID=38435;

-- Isaac Silva
UPDATE "GSAN_ADMIN"."TABELA_COLUNA" SET TBCO_DSCOLUNA = 'ID DO SISTEMA ESGOTO', TBCO_ICPRIMARYKEY = '1' WHERE TBCO_ID = 46057;
UPDATE "GSAN_ADMIN"."TABELA_COLUNA" SET TBCO_DSCOLUNA = 'DESCRICAO DO SISTEMA ESGOTO' WHERE TBCO_ID = 46058;
UPDATE "GSAN_ADMIN"."TABELA_COLUNA" SET TBCO_DSCOLUNA = 'DESCRICAO ABREVIADA DOS SISTEMA ESGOTO' WHERE TBCO_ID = 46059;
UPDATE "GSAN_ADMIN"."TABELA_COLUNA" SET TBCO_DSCOLUNA = 'INDICADOR DE USO DO SISTEMA ESGOTO (1- ATIVO, 2 - INATIVO)' WHERE TBCO_ID = 46060;

-- Isaac Silva
UPDATE TABELA_COLUNA set TBCO_NMCOLUNA = lower(TBCO_NMCOLUNA) WHERE TABE_ID=33 AND TBCO_ID >= 40969 and TBCO_ID <= 40993;

-- Isaac Silva - 
-- Isaac Silva - orgao_expedidor_rg
update tabela_coluna set tbco_nmcoluna = lower(tbco_nmcoluna) where tabe_id = 329;

-- Isaac Silva - pessoa_sexo
update tabela_coluna set tbco_nmcoluna = lower(tbco_nmcoluna) where tabe_id = 358;

-- Isaac Silva - unidade_federacao
update tabela_coluna set tbco_nmcoluna = lower(tbco_nmcoluna) where tabe_id = 459;

-- Isaac Silva - RAMO_ATIVIDADE
update tabela_coluna set tbco_nmcoluna = lower(tbco_nmcoluna) where tabe_id = 387;

-- Isaac Silva - profissao
update tabela_coluna set tbco_nmcoluna = lower(tbco_nmcoluna) where tabe_id = 368;

-- Isaac Silva - CLIENTE_TIPO
update TABELA_COLUNA set TBCO_NMCOLUNA = LOWER(TBCO_NMCOLUNA) where TABE_ID = 44;

-- Isaac Silva -- cliente_fone
update tabela_coluna set tbco_nmcoluna = lower(tbco_nmcoluna) where tabe_id = 37;
update tabela_coluna set TBCO_ICPRIMARYKEY = 1 where tbco_id = 36241 and tabe_id = 37;

-- Isaac Silva -- cliente_endereco
update tabela_coluna set tbco_nmcoluna = lower(tbco_nmcoluna) where tabe_id = 36;
update tabela_coluna set TBCO_ICPRIMARYKEY = 1 where tbco_id = 36240 and tabe_id = 36;

--Ailton Sousa
update tabela_coluna set tbco_nmcoluna = lower(tbco_nmcoluna) where tabe_id = 436;
update tabela_coluna set TBCO_ICPRIMARYKEY = 1 where tbco_id = 40282 and tabe_id = 436;
update tabela_coluna set tbco_dscoluna = 'CODIGO DO SETOR COMERCIAL' where tbco_id = 40284 and tabe_id = 436;

-- Isaac Silva
update tabela_coluna set tbco_nmcoluna = lower(tbco_nmcoluna) where tabe_id = 503;
update tabela_coluna set TBCO_ICPRIMARYKEY = 1 where tbco_id = 45486 and tabe_id = 503;

-- Isaac Silva - Localidade
update TABELA_COLUNA set TBCO_ICPRIMARYKEY = 1 where TBCO_ID = 43181 and TABE_ID = 265;
UPDATE "GSAN_ADMIN"."TABELA_COLUNA" SET TBCO_DSCOLUNA = 'ID DO MUNICIPIO (TABELA MUNICIPIO)' WHERE TBCO_ID = 46230;
UPDATE "GSAN_ADMIN"."TABELA_COLUNA" SET TBCO_DSCOLUNA = 'ABASTECIMENTO SUSPENSO' WHERE TBCO_ID = 46234;
UPDATE "GSAN_ADMIN"."TABELA_COLUNA" SET TBCO_DSCOLUNA = 'ABASTECIMENTO MINIMO' WHERE TBCO_ID = 46235;
UPDATE "GSAN_ADMIN"."TABELA_COLUNA" SET TBCO_DSCOLUNA = 'LOCALIDADE VINCULADA' WHERE TBCO_ID = 46266;
UPDATE "GSAN_ADMIN"."TABELA_COLUNA" SET TBCO_DSCOLUNA = 'LOCALIDADE PARA C.E.F.' WHERE TBCO_ID = 46267;
UPDATE "GSAN_ADMIN"."TABELA_COLUNA" SET TBCO_DSCOLUNA = 'ULTIMA QUADRA' WHERE TBCO_ID = 46268;
UPDATE "GSAN_ADMIN"."TABELA_COLUNA" SET TBCO_DSCOLUNA = 'LOCALIDADE CONTABIL' WHERE TBCO_ID = 46236;

-- Isaac Silva - HIDROMETRO
update TABELA_COLUNA set TBCO_ICPRIMARYKEY = 1 where TBCO_ID = 42545 and TABE_ID = 201;

-- Isaac Silva - Imovel
update "GSAN_ADMIN"."TABELA_COLUNA" set TBCO_DSCOLUNA = 'ID DO IMOVEL (TABELA IMOVEL)' where TBCO_ID = 45486;
update "GSAN_ADMIN"."TABELA_COLUNA" set TBCO_DSCOLUNA = 'ID DO IMOVEL (TABELA IMOVEL)' where TBCO_ID = 46237;
update "GSAN_ADMIN"."TABELA_COLUNA" set TBCO_DSCOLUNA = 'ID DO IMOVEL (TABELA IMOVEL)' where TBCO_ID = 45587;

-- Isaac Silva - Imovel_Agua_Para_Todos
update "GSAN_ADMIN"."TABELA_COLUNA" set TBCO_DSCOLUNA = 'DATA EXCLUSAO DO PROGRAMA' where TBCO_ID = 46151;