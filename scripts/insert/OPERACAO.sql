-- OPERACAO
-- Hebert Falcão
INSERT INTO OPERACAO
 (OPER_ID,
 FNCD_ID,
 OPER_DSOPERACAO,
 OPER_DSABREVIADO,
 OPER_DSCAMINHOURL,
 OPTP_ID)
VALUES
 ('178138',
 '1096',
 'Exibir Consumo por Faixa de Área e Categoria do Imóvel',
 'EGUCI',
 'exibirImovelConsumoFaixaAreaCategoriaPopupAction.do',
 '4');

INSERT INTO OPERACAO
 (OPER_ID,
 FNCD_ID,
 OPER_DSOPERACAO,
 OPER_DSABREVIADO,
 OPER_DSCAMINHOURL,
 OPTP_ID)
VALUES
 ('178139',
 '1097',
 'Inserir Consumo por Faixa de Área e Categoria do Imóvel',
 'IGUCI',
 'inserirImovelConsumoFaixaAreaCategoriaPopupAction.do',
 '4');
 
 -- Bruno Ferreira dos Santos
 INSERT INTO OPERACAO 
 (OPER_ID, 
 FNCD_ID, 
 OPER_DSOPERACAO, 
 OPER_DSABREVIADO, 
 OPER_DSCAMINHOURL, 
 OPTP_ID) 
VALUES 
 ('1480', 
 '1095', 
 'Inserir Cliente Responsável', 
 'ICR', 
 'inserirClienteResponsavelAction.do', 
 '4');
 
 INSERT INTO OPERACAO 
 (OPER_ID, 
 FNCD_ID, 
 OPER_DSOPERACAO, 
 OPER_DSABREVIADO, 
 OPER_DSCAMINHOURL, 
 OPTP_ID) 
VALUES 
 ('2015', 
 '2015', 
 'Atualizar Cliente Responsável', 
 'ACR', 
 'atualizarClienteResponsavelAction.do', 
 '4');

-- Péricles Tavares

INSERT INTO operacao (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (1483,1098,'Inserir Sub-Sistema Esgoto','InsSubEsg','inserirSubSistemaEsgotoAction.do',current_timestamp,null,null,4,null);
INSERT INTO operacao (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (1485,1099,'Manter Sub-Sistema Esgoto','ManSubEsg','atualizarSubSistemaEsgotoAction.do',current_timestamp,null,null,3,null);
INSERT INTO operacao (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (1487,2000,'Inserir Bacia Esgoto','InsBacia','inserirBaciaAction.do',current_timestamp,null,null,4,null);
INSERT INTO operacao (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (1489,2001,'Manter Bacia','ManBacia','atualizarBaciaAction.do',current_timestamp,null,null,3,null);
INSERT INTO operacao (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (1491,2002,'Inserir Sub-Bacia','InsSubBac','inserirSubBaciaAction.do',current_timestamp,null,null,4,null);
INSERT INTO operacao (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (1493,2003,'Manter Sub-Bacia','ManSubBac','atualizarSubBaciaEsgotoAction.do',current_timestamp,null,null,3,null);
INSERT INTO operacao (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (1495,2004,'Inserir Dados Operacionais da Localidade','InsDadOpLo','inserirLocalidadeDadoOperacionalAction.do',current_timestamp,null,null,4,null);
INSERT INTO operacao (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (1497,2005,'Manter Dados Operacionais da Localidade','ManDadOpLo','atualizarDadosOperacionaisLocalidadeAction.do',current_timestamp,null,null,3,null);
INSERT INTO operacao (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (1499,2006,'Manter Dados Consitários','ManDadCon','atualizarDadosConsitariosAction.do',current_timestamp,null,null,3,null);
INSERT INTO operacao (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (1501,2007,'Inserir Sistema de Abastecimento','InsSisAba','inserirSistemaAbastecimentoAction.do',current_timestamp,null,null,4,null);
INSERT INTO operacao (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (1503,2008,'Manter Sistema de Abastecimento','ManDadCon','atualizarSistemaAbastecimentoAction.do',current_timestamp,null,null,3,null);
INSERT INTO operacao (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (1505,2009,'Inserir Unidade Operacional','InsUniOpe','inserirUnidadeOperacionalAction.do',current_timestamp,null,45887,4,45887);
INSERT INTO operacao (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (1507,2010,'Manter Unidade Operacional','ManUniOpe','atualizarUnidadeOperacionalAction.do',current_timestamp,null,null,3,null);
INSERT INTO operacao (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (1509,2011,'Inserir Zona Abastecimento','InsZonAba','inserirZonaAbastecimentoAction.do',current_timestamp,null,45887,4,45887);
insert into operacao values (16524,2012,'Remover Zona Abastecimento','RemZonAba','removerZonaAbastecimentoAction.do',sysdate,null,null,2,null);
INSERT INTO operacao (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (1511,2012,'Manter Zona Abastecimento','ManUniOpe','atualizarZonaAbastecimentoAction.do',current_timestamp,null,null,3,null);
INSERT INTO operacao (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (16522,2012,'Exibir Manter Zona Abastecimento','ManUniOpe','exibirAtualizarZonaAbastecimentoAction.do',current_timestamp,null,null,3,null);
INSERT INTO operacao (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (16523,2012,'Exibir Filtar Zona Abastecimento','ManZonAba','filtrarZonaAbastecimentoAction.do',current_timestamp,null,null,3,null);
INSERT INTO operacao (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (1513,2013,'Inserir Setor Abastecimento','InsSetAba','inserirSetorAbastecimentoAction.do',current_timestamp,null,null,4,null);
INSERT INTO operacao (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (1515,2014,'Manter Setor Abastecimento','ManSetAba','atualizarSetorAbastecimentoAction.do',current_timestamp,null,null,3,null);


-- Ailton Sousa

INSERT INTO operacao 
(OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (1516,1099,'Filtrar Sub-Sistema Esgoto','FilSubEsg','filtrarSubsistemaEsgotoAction.do',current_timestamp,null,null,5,null);

INSERT INTO operacao 
(OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (1517,1099,'Exibir Atualizar Sub-Sistema Esgoto','ExAtSubEsg','exibirAtualizarSubsistemaEsgotoAction.do',current_timestamp,null,null,3,null);

INSERT INTO operacao 
(OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (1518,1099,'Exibir Manter Sub-Sistema Esgoto','ExMaSubEsg','exibirManterSubsistemaEsgotoAction.do',current_timestamp,null,null,3,null);

INSERT INTO operacao 
(OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (1519,1099,'Remover Sub-Sistema Esgoto','RemSubEsg','removerSubsistemaEsgotoAction.do',current_timestamp,null,null,2,null);

--Bruno Ferreira dos Santos
insert into operacao values (3053,2039,'Consultar Imóvel Agua para Todos','CIAPT','consultarImovelAguaParaTodosAction.do',sysdate,null,null,6,null)	
insert into operacao values (2535,2038,'Manter Imovel Agua para Todos','MIAPT','manterImovelAguaParaTodosAction.do',sysdate,null,null,3,null)	

-- Anderson Italo
INSERT INTO OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (SQ_OPERACAO.nextVal,2016,'Gerar Relatorio Logradouro Geral','GerRelLoGE','gerarRelatorioLogradouroGeralAction.do',current_timestamp, null,null,13,null);

--Bruno Ferreira dos Santos
insert into operacao values (3053,2039,'Consultar Imóvel Agua para Todos','CIAPT','consultarImovelAguaParaTodosAction.do',sysdate,null,null,6,null);
insert into operacao values (3571,2039,'Faturamento Historico Agua Para Todos','FHAPT','consultarFaturamentoHistoricoAguaParaTodosAction.do',sysdate,null,null,6,null);
insert into operacao values (3572,2003,'Remover Sub-Bacia','RemSubBac','removerSubBaciaAction.do',sysdate,null,null,2,null);
insert into operacao values (12378,2003,'Manter Sub-Bacia','ManSubBac','atualizarSubBaciaAction.do',sysdate,null,null,3,null);	
insert into operacao values (12379,2003,'Filtrar Sub-Bacia','FilSubBac','filtrarSubBaciaAction.do',sysdate,null,null,5,null);	
insert into operacao values (12380,2003,'Exibir Atualizar Sub-Bacia','ExAtSubBac','exibirAtualizarSubBaciaAction.do',sysdate,null,null,3,null);	
insert into operacao values (12381,2003,'Exibir Manter Sub-Bacia','ExMaSubBac','exibirManterSubBaciaAction.do',sysdate,null,null,3,null);	

-- Isaac Silva

INSERT INTO operacao 
(OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (3054,2005,'Filtrar Dados Operacionais da Localidade','FilDaOpL','filtrarLocalidadeDadoOperacionalAction.do',current_timestamp,null,null,5,null);

INSERT INTO operacao 
(OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (3055,2005,'Exibir Atualizar Dados Operacionais da Localidade','ExAtDaOpL','exibirAtualizarLocalidadeDadoOperacionalAction.do',current_timestamp,null,null,3,null);

INSERT INTO operacao 
(OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (3056,2005,'Exibir Manter Dados Operacionais da Localidade','ExMaDaOpL','exibirManterLocalidadeDadoOperacionalAction.do',current_timestamp,null,null,3,null);

INSERT INTO operacao 
(OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (3057,2005,'Remover Dados Operacionais da Localidade','RemDaOpL','removerLocalidadeDadoOperacionalAction.do',current_timestamp,null,null,2,null);

-- Péricles Tavares
INSERT INTO OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (12377,2009,'REMOVER ENDERECO INSERIR UNIDADE OPERACIONAL','RemEndInsU','removerInserirUnidadeOperacionalColecaoEnderecoAction.do',current_timestamp,null,null,2,null);
INSERT INTO OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (12895,2010,'Exibir Atualizar Unidade Operacional Action','ExAtuaUO','exibirAtualizarUnidadeOperacionalAction.do',current_timestamp,null,null,3,null);
INSERT INTO OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (13413,2010,'REMOVER ENDERECO ALTERAR UNIDADE OPERACIONAL','RemEndAltU','removerAlterarUnidadeOperacionalColecaoEnderecoAction.do',current_timestamp,null,null,2,null);
INSERT INTO OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (13931,2010,'Remover Unidade Operacional','RemUniOpe','removerUnidadeOperacionalAction.do',current_timestamp,null,45887,2,45887);

-- Isaac Silva

INSERT INTO operacao 
(OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (14967,2001,'Filtrar Bacia','FilBac','filtrarBaciaAction.do',current_timestamp,null,null,5,null);

INSERT INTO operacao 
(OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (15485,2001,'Exibir Atualizar Bacia','ExAtBac','exibirAtualizarBaciaAction.do',current_timestamp,null,null,3,null);

INSERT INTO operacao 
(OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (16003,2001,'Exibir Manter Bacia','ExMaBac','exibirManterBaciaAction.do',current_timestamp,null,null,3,null);

INSERT INTO operacao 
(OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (16521,2001,'Remover Bacia','RemBac','removerBaciaAction.do',current_timestamp,null,null,2,null);

-- Anderson Italo
INSERT INTO OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,
OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (SQ_OPERACAO.nextVal,769,'Consultar Sistema de Abastecimento','CoSiAb','exibirAtualizarSistemaAbastecimentoAction.do',{ts '2011-02-01 16:51:50.570'},null,null,6,null);

-- Isaac Silva
INSERT INTO operacao (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (22737,2057,'Efetuar Suprimir Esgoto Judicialmente','EfSImEJ','efetuarSuprimirImovelEsgotoJudicialAction.do',current_timestamp,null,null,12,null);
INSERT INTO operacao (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (23255,2058,'Efetuar Religar Esgoto Suprimido Judicialmente','EfRSImEJ','efetuarReligarSuprimidoImovelEsgotoJudicialAction.do',current_timestamp,null,null,12,null);

-- Anderson Italo
INSERT INTO OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,
OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (SQ_OPERACAO.nextVal,2059,'Inserir Dados Censitários','InDaCe','inserirDadosCensitariosAction.do',current_timestamp,null,null,4,null);

INSERT INTO OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,
OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (SQ_OPERACAO.nextVal,2077,'Remover Dados Censitários','ReDaCe','removerDadosCensitariosAction.do',current_timestamp,null,null,2,null);

INSERT INTO OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,
OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (SQ_OPERACAO.nextVal,2098,'Atualizar Dados Censitários','AtuDaC','atualizarDadosCensitariosAction.do',current_timestamp,null,null,3,null);

INSERT INTO OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,
OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (SQ_OPERACAO.nextVal,2098,'Consultar Dados Censitarios','CoDaCe','exibirAtualizarDadosCensitariosAction.do',current_timestamp,null,null,6,null);

-- Péricles Tavares
INSERT INTO OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (34651,2099,'Gerar Relatorio Micromedidores','GerRelMicr','gerarRelatorioMicromedidoresAction.do',current_timestamp,null,null,13,null);
INSERT INTO OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (35687,2101,'Gerar Relatorio Macromedidores e Micromedidos associados c','GerRelMacr','/exibirGerarRelatorioMacromedidoresMicromedidosAssociadosUltimosConsumos',current_timestamp,null,null,13,null);

-- Anderson Italo
INSERT INTO OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (SQ_OPERACAO.nextVal,2100,'Gerar Relatorio Grandes Consumidores','GerRelGrCo','gerarRelatorioGrandesConsumidoresAction.do',current_timestamp,null,null,13,null);

-- Ailton Sousa
INSERT INTO OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (SQ_OPERACAO.nextVal,19,'Inserir Setor Comercial Vencimento','InSeCoVenc',null,current_timestamp,null,null,4,null);

INSERT INTO OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (SQ_OPERACAO.nextVal,269,'Atualizar Setor Comercial Vencimento','AtSeCoVenc',null,current_timestamp,null,null,3,null);

INSERT INTO OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (SQ_OPERACAO.nextVal,20,'Remover Setor Comercial Vencimento','ReSeCoVenc',null,current_timestamp,null,null,2,null);

-- Ailton Sousa
INSERT INTO operacao 
(OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (26364,2102,'Inserir Solicitacao Tipo Especificacao Mensagem','InSoTpEMsg','inserirMensagemTipoSolicitacaoEspecificacaoAction.do',current_timestamp,null,null,4,null);

INSERT INTO operacao 
(OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (26365,2103,'Manter Solicitacao Tipo Especificacao Mensagem','MnSoTpEMsg','atualizarMensagemTipoSolicitacaoEspecificacaoAction.do',current_timestamp,null,null,3,null);

INSERT INTO operacao 
(OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (26366,2103,'Filtrar Solicitacao Tipo Especificacao Mensagem','FiSoTpEMsg','filtrarMensagemTipoSolicitacaoEspecificacaoAction.do',current_timestamp,null,null,5,null);

INSERT INTO operacao 
(OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (26367,2103,'Exibir Atualizar Solicitacao Tipo Especificacao Mensagem','EASoTpEMsg','exibirAtualizarMensagemTipoSolicitacaoEspecificacaoAction.do',current_timestamp,null,null,3,null);

INSERT INTO operacao 
(OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (26368,2103,'Exibir Manter Solicitacao Tipo Especificacao Mensagem','EMSoTpEMsg','exibirManterMensagemTipoSolicitacaoEspecificacaoAction.do',current_timestamp,null,null,3,null);

INSERT INTO operacao 
(OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (26369,2103,'Remover Solicitacao Tipo Especificacao Mensagem','ReSoTpEMsg','removerMensagemTipoSolicitacaoEspecificacaoAction.do',current_timestamp,null,null,2,null);

-- Isaac Silva
-- ClienteImovel
Insert into OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) values (53817,7,'Imovel Atualizar Cliente Imovel',null,null,current_timestamp,null,null,3,null);
insert into OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) values (54335,7,'Imovel Remover Cliente Imovel',null,null,current_timestamp,null,null,2,null);
Insert into OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) values (54853,6,'Imovel Inserir Cliente Imovel',null,null,current_timestamp,null,null,4,null);

-- ImovelSubCategoria
Insert into OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) values (55371,7,'Imovel Atualizar Imovel SubCategoria',null,null,current_timestamp,null,null,3,null);
insert into OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) values (55889,7,'Imovel Remover Imovel SubCategoria',null,null,current_timestamp,null,null,2,null);
insert into OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) values (56407,6,'Imovel Inserir Imovel SubCategoria',null,null,current_timestamp,null,null,4,null);

-- ImovelConsumoFaixaAreaCategoria
INSERT INTO OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (178140,7,'Imovel Atualizar Imovel Consumo Faixa Area Categoria',NULL,NULL,CURRENT_TIMESTAMP,NULL,NULL,3,NULL);

INSERT INTO OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (178141,7,'Imovel Remover Imovel Consumo Faixa Area Categoria',NULL,NULL,CURRENT_TIMESTAMP,NULL,NULL,2,NULL);

INSERT INTO OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (178142,6,'Imovel Inserir Imovel Consumo Faixa Area Categoria',NULL,NULL,CURRENT_TIMESTAMP,NULL,NULL,4,NULL);

-- Anderson Italo
INSERT INTO OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (SQ_OPERACAO.nextVal,2117,'Gerar Relatorio Usuarios em Debito Automatico','GerRelUsDa','gerarRelatorioUsuarioDebitoAutomaticoAction.do',current_timestamp,null,null,13,null);

-- Ailton Sousa
INSERT INTO operacao 
(OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (178143,2104,'Inserir Consumo por Faixa de Área e Categoria','InsGruUsu','inserirConsumoFaixaAreaCategoriaAction.do',current_timestamp,null,null,4,null);

INSERT INTO operacao 
(OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (178144,2105,'Manter Consumo por Faixa de Área e Categoria','ManGruUsu','atualizarConsumoFaixaAreaCategoriaAction.do',current_timestamp,null,null,3,null);

INSERT INTO operacao 
(OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (178145,2105,'Filtrar Consumo por Faixa de Área e Categoria','FilGruUsu','filtrarConsumoFaixaAreaCategoriaAction.do',current_timestamp,null,null,5,null);

INSERT INTO operacao 
(OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (178146,2105,'Exibir Atualizar Consumo por Faixa de Área e Categoria','ExAtGruUsu','exibirAtualizarConsumoFaixaAreaCategoriaAction.do',current_timestamp,null,null,3,null);

INSERT INTO operacao 
(OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (178147,2105,'Exibir Manter Consumo por Faixa de Área e Categoria','ExMaGruUsu','exibirManterConsumoFaixaAreaCategoriaAction.do',current_timestamp,null,null,3,null);

INSERT INTO operacao 
(OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (178148,2105,'Remover Consumo por Faixa de Área e Categoria','RemGruUsu','removerConsumoFaixaAreaCategoriaAction.do',current_timestamp,null,null,2,null);

-- Isaac Silva
insert into "OPERACAO" (oper_id,fncd_id,oper_dsoperacao,oper_dsabreviado,oper_dscaminhourl,oper_tmultimaalteracao,oper_idoperacaopesquisa,tbco_id,optp_id,tbco_idargumento) values (74537,216,'Exibir Selecionar Conta Revisao','ExSeCoRev','exibirSelecionarContaRevisaoAction.do',current_timestamp,null,null,6,null);
insert into "OPERACAO" (oper_id,fncd_id,oper_dsoperacao,oper_dsabreviado,oper_dscaminhourl,oper_tmultimaalteracao,oper_idoperacaopesquisa,tbco_id,optp_id,tbco_idargumento) values (75055,216,'Selecionar Conta Revisao','SeCoRev','selecionarContaRevisaoAction.do',current_timestamp,null,null,6,null);

-- Anderson Italo
INSERT INTO OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (SQ_OPERACAO.nextVal,2137,'Gerar Relatorio Resumo Registro de Atendimento','GerRelReRa','gerarRelatorioResumoRegistroAtendimentoAction.do',current_timestamp,null,null,13,null);

-- Pericles Tavares
INSERT INTO OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO)
VALUES (SQ_OPERACAO.nextVal,587,'Remover Inserir Distrito Operacional Colecao Endereco','remover','removerInserirDistritoOperacionalColecaoEnderecoAction.do',current_timestamp,null,null,2,null);

INSERT INTO OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO)
VALUES (SQ_OPERACAO.nextVal,595,'Remover Atualizar Distrito Operacional Colecao Endereco','remover','removerAtualizarDistritoOperacionalColecaoEnderecoAction.do',current_timestamp,null,null,2,null);
VALUES (SQ_OPERACAO.nextVal,2137,'Gerar Relatorio Resumo Registro de Atendimento','GerRelReRa','gerarRelatorioResumoResgistroAtendimentoAction.do',current_timestamp,null,null,13,null);

-- Ailton Sousa
INSERT INTO operacao 
(OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (26376,2106,'Inserir Tramite por Especificacao','InsTraEsp','inserirTramiteEspecificacaoAction.do',current_timestamp,null,null,4,null);

INSERT INTO operacao 
(OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (26377,2107,'Manter Tramite por Especificacao','ManTraEsp','atualizarTramiteEspecificacaoAction.do',current_timestamp,null,null,3,null);

INSERT INTO operacao 
(OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (26378,2107,'Filtrar Tramite por Especificacao','FilTraEsp','filtrarTramiteEspecificacaoAction.do',current_timestamp,null,null,5,null);

INSERT INTO operacao 
(OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (26379,2107,'Exibir Atualizar Tramite por Especificacao','ExAtTraEsp','exibirAtualizarTramiteEspecificacaoAction.do',current_timestamp,null,null,3,null);

INSERT INTO operacao 
(OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (26380,2107,'Exibir Manter Tramite por Especificacao','ExMaTraEsp','exibirManterTramiteEspecificacaoAction.do',current_timestamp,null,null,3,null);

INSERT INTO operacao 
(OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (26381,2107,'Remover Tramite por Especificacao','RemTraEsp','removerTramiteEspecificacaoAction.do',current_timestamp,null,null,2,null);

-- Isaac Silva
Insert into OPERACAO (OPER_ID,FNCD_ID,OPER_TMULTIMAALTERACAO,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) values (95257,2158,current_timestamp,'Relatorio Estatística de Atendimento por Atendente','ReEsAtAte','gerarRelatorioEstatisticaAtendimentoAtendenteAction.do',null,null,13,null);

-- Anderson Italo
INSERT INTO OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (SQ_OPERACAO.nextVal,2177,'Gerar Relatorio Gestao Registro de Atendimento','GerRelGeRa','gerarRelatorioGestaoRegistroAtendimentoAction.do',current_timestamp,null,null,13,null);

-- Péricles Tavares
INSERT INTO OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (SQ_OPERACAO.nextVal,599,'Remover Dado Distrito Operacional Action','ReDaDisOp','removerDadoDistritoOperacionalAction.do',current_timestamp,null,null,2,null);
INSERT INTO OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (SQ_OPERACAO.nextVal,599,'Atualizar Dado Distrito Operacional Action','AtuDaDisOp','atualizarDadoDistritoOperacionalAction.do',current_timestamp,null,null,3,null);
INSERT INTO OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (SQ_OPERACAO.nextVal,599,'Exibir Atualizar Dado Distrito Operacional Action','ExAtuDaDis','exibirAtualizarDadoDistritoOperacionalAction.do',current_timestamp,null,null,3,null);
INSERT INTO OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (SQ_OPERACAO.nextVal,599,'Inserir Dado Distrito Operacional Action','InseDadoDi','inserirDadoDistritoOperacionalAction.do',current_timestamp,null,null,4,null);
INSERT INTO OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (SQ_OPERACAO.nextVal,599,'Exibir Manter Dado Distrito Operacional Action','DaDisOpe','exibirManterDadoDistritoOperacionalAction.do',current_timestamp,null,null,3,null);
INSERT INTO OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (SQ_OPERACAO.nextVal,599,'Remover AtualizarDistritoOperacional Colecao Endereco Action','ReAtDisEn','removerAtualizarDistritoOperacionalColecaoEnderecoAction.do',current_timestamp,null,null,2,null);

-- Anderson Italo
INSERT INTO OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) 
VALUES (SQ_OPERACAO.nextVal,2177,'Gerar Relatorio Manter Feriado','GerRelMaFe','gerarRelatorioFeriadoManterAction.do',current_timestamp,null,null,13,null);

INSERT INTO OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (157417,2197,'Relatório de Produtividade de Equipe','RPE','gerarRelatorioAcompanhamentoExecucaoOSAction.do',TO_TIMESTAMP('04/04/11 10:47:20,995000000','DD/MM/RR HH24:MI:SS,FF'),NULL,NULL,13,NULL);

INSERT INTO OPERACAO (OPER_ID, FNCD_ID, OPER_DSOPERACAO, OPER_DSABREVIADO, OPER_DSCAMINHOURL, OPTP_ID) VALUES ('157418', '2198', 'Relatorio Res Ordens de Servico Nao Exec por Equipe', 'RPPE3', 'gerarRelatorioAcompanhamentoExecucaoOSAction.do', '13')

INSERT INTO OPERACAO (OPER_ID, FNCD_ID, OPER_DSOPERACAO, OPER_DSABREVIADO, OPER_DSCAMINHOURL, OPTP_ID) VALUES ('157419', '2199', 'Relatorio Res Ordens de Servico Encerradas', 'RPPE4', 'gerarRelatorioAcompanhamentoExecucaoOSAction.do', '13')

-- Hebert Falcão
-- CR84384
INSERT INTO OPERACAO (OPER_ID,FNCD_ID,OPER_DSOPERACAO,OPER_DSABREVIADO,OPER_DSCAMINHOURL,OPER_TMULTIMAALTERACAO,OPER_IDOPERACAOPESQUISA,TBCO_ID,OPTP_ID,TBCO_IDARGUMENTO) VALUES (178137,2220,'Consultar Historico de Instalacao de Hidrometro Popup','ConHisPo','consultarHistoricoInstalacaoHidrometroPopupAction.do',current_timestamp,NULL,NULL,6,NULL);