-- Bruno Ferreira dos Santos
UPDATE TABELA SET TABE_NMTABELA = 'municipio' WHERE TABE_NMTABELA = 'MUNICIPIO';
UPDATE TABELA SET TABE_DSTABELA = 'zona_abastecimento', TABE_NMTABELA = 'zona_abastecimento' WHERE TABE_ID = 479;

-- Anderson Italo
UPDATE TABELA SET TABE_NMTABELA = 'localidade_dados_censitarios', TABE_DSTABELA = 'localidade_dados_censitarios' WHERE TABE_ID=267;
UPDATE TABELA SET TABE_NMTABELA = 'municipio_dados_censitarios', TABE_DSTABELA = 'municipio_dados_censitarios' WHERE TABE_ID=295;

-- Isaac Silva
UPDATE TABELA set TABE_NMTABELA = lower(TABE_NMTABELA) WHERE TABE_ID=33;

-- Isaac Silva - orgao_expedidor_rg
update tabela set tabe_nmtabela = lower(tabe_nmtabela) where tabe_id = 329;

-- Isaac Silva - pessoa_sexo
update tabela set tabe_nmtabela = lower(tabe_nmtabela) where tabe_id = 358;

-- Isaac Silva - unidade_federacao
update tabela set tabe_nmtabela = lower(tabe_nmtabela) where tabe_id = 459;

-- Isaac Silva - RAMO_ATIVIDADE
update tabela set tabe_nmtabela = lower(tabe_nmtabela) where tabe_id = 387;

-- Isaac Silva - profissao
update tabela set tabe_nmtabela = lower(tabe_nmtabela) where tabe_id = 368;

-- Isaac Silva - CLIENTE_TIPO
update tabela set tabe_nmtabela = lower(tabe_nmtabela) where tabe_id = 44;

-- Isaac Silva - CLIENTE_FONE
update tabela set TABE_NMTABELA = lower(TABE_NMTABELA) where tabe_id = 37;

-- Isaac Silva - cliente_endereco
update tabela set tabe_nmtabela = lower(tabe_nmtabela) where tabe_id = 36;

--Ailton Sousa
update tabela set TABE_NMTABELA = lower(TABE_NMTABELA) where tabe_id = 436;

-- Bruno Ferreira dos Santos
UPDATE TABELA SET TABE_NMTABELA = 'hidrometro_instalacao_hist' WHERE TABE_ID = 205

-- Isaac Silva
UPDATE TABELA SET TABE_NMTABELA = LOWER(TABE_NMTABELA) WHERE TABE_ID = 503;

-- Isaac Silva - HIDROMETRO
update TABELA set TABE_NMTABELA = LOWER(TABE_NMTABELA) where TABE_ID = 201;