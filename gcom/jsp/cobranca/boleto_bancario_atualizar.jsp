<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="gcom.cadastro.cliente.ClienteImovel"%>
<%@ page import="gcom.cadastro.imovel.ImovelSubcategoria"%>
<%@ page import="gcom.cobranca.BoletoBancarioOcorrencias"%>
<%@ page import="gcom.gui.cobranca.BoletoBancarioContaHelper"%>
<%@ page import="gcom.gui.cobranca.BoletoBancarioMovimentacaoHelper"%>
<%@ page import="gcom.gui.faturamento.bean.GuiaPagamentoPrestacaoHelper"%>
<%@ page import="gcom.util.ConstantesSistema"%>
<%@ page import="gcom.util.Util"%>

<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false"
	formName="AtualizarBoletoBancarioActionForm" />

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript">
function extendeTabela(tabela,display){
	var form = document.forms[0];

	if(display){
	eval('layerHide'+tabela).style.display = 'none';
	eval('layerShow'+tabela).style.display = 'block';
	}else{
	eval('layerHide'+tabela).style.display = 'block';
	eval('layerShow'+tabela).style.display = 'none';
	}
	}


	function required () {
		this.aa = new Array("idBoletoBancarioLancamentoEnvio", "Informe Lançamento de Envio.", new Function ("varName", " return this[varName];"));
	}

	function validarForm(formulario) {
		var form = document.forms[0];

		if (validateRequired(form) 
				&& verificaDataMensagemPersonalizada(formulario.dataVencimentoTitulo, 'Data de Vencimento do Título  é inválida.')
				&& confirm('Confirma alteração?')) {
			formulario.action = "/gsan/atualizarBoletoBancarioAction.do";
			submeterFormPadrao(formulario);
		}
	}

</script>

</head>

<body leftmargin="5" topmargin="5">

<html:form action="/exibirAtualizarBoletoBancarioAction"
	name="AtualizarBoletoBancarioActionForm"
	type="gcom.gui.cobranca.AtualizarBoletoBancarioActionForm"
	method="post">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="130" valign="top" class="leftcoltext">
			<div align="center"><%@ include
				file="/jsp/util/informacoes_usuario.jsp"%> <%@ include
				file="/jsp/util/mensagens.jsp"%></div>
			</td>

			<td width="625" valign="top" class="centercoltext">

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Atualizar Boleto Bancário</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>

			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para alterar o boleto bancário, informe os
					dados abaixo:</td>
				</tr>
			</table>

			<table width="100%" border="0">
				<tr>
					<td width="25%"><strong>Arrecadador:</strong></td>
					<td colspan="4" width="75%"><html:text
						property="codigoAgenteArrecadador" readonly="true"
						style="background-color:#EFEFEF; border:0;" size="4" /> <html:text
						property="nomeAgenteArrecadador" readonly="true"
						style="background-color:#EFEFEF; border:0;" size="46" /></td>
				</tr>
				<tr>
					<td><strong>Número Boleto:</strong></td>
					<td><html:text property="numeroSequencialBoletoBancario"
						readonly="true" style="background-color:#EFEFEF; border:0;"
						size="10" /></td>
					<td><strong>Imóvel:</strong></td>
					<td><html:text property="idImovel" readonly="true"
						style="background-color:#EFEFEF; border:0;" size="9" /></td>
				</tr>
				<!-- 1.4 Dados do Imóvel -->
				<tr>
					<td colspan="4">
					<div id="layerHideDadosImovel" style="display: block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('DadosImovel',true);" /><b>Dados
							do Imóvel</b></a></span></td>
						</tr>
					</table>
					</div>

					<div id="layerShowDadosImovel" style="display: none">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('DadosImovel',false);" /><b>Dados
							do Imóvel</b></a></span></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td>
							<table width="100%">
								<tr>
									<td width="25%"><strong>Inscrição:</strong></td>
									<td><html:text property="inscricaoFormatadaImovel"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="20" /></td>
									<td><strong>Perfil do Imóvel:</strong></td>
									<td><html:text property="descricaoImovelPerfil"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="20" /></td>
								</tr>
								<tr>
									<td><strong>Situação de Água:</strong></td>
									<td><html:text property="descricaoLigacaoAguaSituacao"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="15" /></td>
									<td><strong>Situação de Esgoto:</strong></td>
									<td><html:text property="descricaoLigacaoEsgotoSituacao"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="15" /></td>
								</tr>
								<!-- 1.4.6 Endereço do Imóvel -->
								<tr>
									<td colspan="4">
									<table width="100%" align="center" bgcolor="#99CCFF" border="0">
										<tr>
											<td align="center">
											<div class="style9"><strong>Endereço do Imóvel</strong></div>
											</td>
										</tr>
										<tr bgcolor="#FFFFFF">
											<td align="center">
											<div class="style9">${AtualizarBoletoBancarioActionForm.enderecoImovel}
											&nbsp;</div>
											</td>
										</tr>
									</table>
									</td>
								</tr>
								<!-- 1.4.7 Subcategorias -->
								<%
									int cont = 0;
											int quantidadeTotalEconomias = 0;
								%>
								<tr>
									<td colspan="4">
									<table width="100%" align="center" bgcolor="#90c7fc" border="0">
										<tr bordercolor="#79bbfd">
											<td colspan="4" align="center" bgcolor="#79bbfd"><strong>Lista
											das subcategorias e quantidades de economias do imóvel</strong></td>
										</tr>
										<tr bordercolor="#000000">
											<td width="19%" bgcolor="#90c7fc" align="center">
											<div class="style9"><font color="#000000"
												style="font-size: 9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <strong>Categoria</strong>
											</font></div>
											</td>
											<td width="56%" bgcolor="#90c7fc" align="center">
											<div class="style9"><font color="#000000"
												style="font-size: 9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <strong>Subcategoria</strong></font>
											</div>
											</td>
											<td width="25%" bgcolor="#90c7fc" align="center">
											<div class="style9"><font color="#000000"
												style="font-size: 9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <strong>Economias</strong>
											</font></div>
											</td>
										</tr>
										<tr>
											<td width="100%" colspan="3">
											<div style="width: 100%; height: 100%; overflow: auto;">
											<table width="100%" align="left" bgcolor="#99CCFF">
												<!--corpo da segunda tabela-->
												<%
													cont = 0;
												%>
												<logic:notEmpty name="colecaoImovelSubcategorias">
													<logic:iterate name="colecaoImovelSubcategorias"
														id="imovelSubcategoria" type="ImovelSubcategoria">
														<%
															cont = cont + 1;
																			if (cont % 2 == 0) {
														%>
														<tr bgcolor="#cbe5fe">
															<%
																} else {
															%>
															<tr bgcolor="#FFFFFF">
																<%
																	}
																%>

																<td width="19%" align="left">
																<div align="center"><font color="#000000"
																	style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif"> <logic:present
																	name="imovelSubcategoria" property="comp_id">

																	<bean:define name="imovelSubcategoria"
																		property="comp_id" id="comp_id" />
																	<logic:present name="comp_id" property="subcategoria">


																		<bean:define name="comp_id" property="subcategoria"
																			id="subcategoria" />

																		<logic:present name="subcategoria"
																			property="categoria">

																			<bean:define name="subcategoria" property="categoria"
																				id="categoria" />
																			<bean:write name="categoria" property="descricao" />
																		</logic:present>
																	</logic:present>
																</logic:present> </font></div>
																</td>
																<td width="56%" align="left">
																<div align="center"><font color="#000000"
																	style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif"> <logic:present
																	name="imovelSubcategoria" property="comp_id">

																	<bean:define name="imovelSubcategoria"
																		property="comp_id" id="comp_id" />
																	<logic:present name="comp_id" property="subcategoria">
																		<bean:define name="comp_id" property="subcategoria"
																			id="subcategoria" />
																		<bean:write name="subcategoria" property="descricao" />
																	</logic:present>
																</logic:present> </font></div>
																</td>
																<td width="25%" align="right">
																<div align="right"><font color="#000000"
																	style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif"> <logic:present
																	name="imovelSubcategoria"
																	property="quantidadeEconomias">
																	<bean:write name="imovelSubcategoria"
																		property="quantidadeEconomias" />
																</logic:present> </font></div>
																</td>
																<%
																	quantidadeTotalEconomias = ((ImovelSubcategoria) imovelSubcategoria)
																							.getQuantidadeEconomias()
																							+ quantidadeTotalEconomias;
																%>
															</tr>
													</logic:iterate>
													<tr bgcolor="#FFFFFF">
														<td width="75%" colspan="2" bgcolor="#90c7fc"
															align="center">
														<div class="style9" align="center"><font
															color="#000000" style="font-size: 9px"
															face="Verdana, Arial, Helvetica, sans-serif"> <strong>Total
														de Economias</strong></font></div>
														</td>
														<td width="25%" align="right">
														<div align="right"><font color="#000000"
															style="font-size: 9px"
															face="Verdana, Arial, Helvetica, sans-serif"> <%=quantidadeTotalEconomias%></font></div>
														</td>
													</tr>
												</logic:notEmpty>
											</table>
											</div>
											</td>
										</tr>
									</table>
									</td>
								</tr>
								<!-- 1.4.8 Clientes -->
								<tr>
									<td colspan="4">
									<table width="100%" align="center" bgcolor="#90c7fc" border="0">
										<tr bordercolor="#79bbfd">
											<td colspan="4" align="center" bgcolor="#79bbfd"><strong>Lista
											dos clientes associados ao imóvel</strong></td>
										</tr>
										<tr bordercolor="#000000">
											<td width="40%" bgcolor="#90c7fc" align="center">
											<div class="style9"><font color="#000000"
												style="font-size: 9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <strong>Nome
											do Cliente</strong> </font></div>
											</td>
											<td width="28%" bgcolor="#90c7fc" align="center">
											<div class="style9"><font color="#000000"
												style="font-size: 9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <strong>Tipo
											da Rela&ccedil;&atilde;o</strong> </font></div>
											</td>
											<td width="32%" bgcolor="#90c7fc" align="center"><font
												color="#000000" style="font-size: 9px"
												face="Verdana, Arial, Helvetica, sans-serif"><strong>CPF/CNPJ</strong>
											</font></td>
										</tr>
										<tr>
											<td width="100%" colspan="4">
											<div style="width: 100%; height: 100%; overflow: auto;">
											<table width="100%" align="left" bgcolor="#99CCFF">
												<!--corpo da segunda tabela-->
												<%
													cont = 0;
												%>
												<logic:notEmpty name="colecaoClientesImovel">
													<logic:iterate name="colecaoClientesImovel"
														id="imovelCliente" type="ClienteImovel">
														<%
															cont = cont + 1;
																			if (cont % 2 == 0) {
														%>
														<tr bgcolor="#cbe5fe">
															<%
																} else {
															%>
															<tr bgcolor="#FFFFFF">
																<%
																	}
																%>
																<td width="40%" bordercolor="#90c7fc" align="left">
																<div class="style9"><font color="#000000"
																	style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif"> <logic:present
																	name="imovelCliente" property="cliente">
																	<bean:write name="imovelCliente"
																		property="cliente.nome" />
																</logic:present> </font></div>
																</td>
																<td width="28%" align="left">
																<div class="style9"><font color="#000000"
																	style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif"> <logic:present
																	name="imovelCliente" property="clienteRelacaoTipo">
																	<bean:write name="imovelCliente"
																		property="clienteRelacaoTipo.descricao" />
																</logic:present> </font></div>
																</td>
																<td width="32%" align="right"><font color="#000000"
																	style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
																	name="imovelCliente" property="cliente.cpf">
																	<bean:write name="imovelCliente"
																		property="cliente.cpfFormatado" />
																</logic:notEmpty> <logic:notEmpty name="imovelCliente"
																	property="cliente.cnpj">
																	<bean:write name="imovelCliente"
																		property="cliente.cnpjFormatado" />
																</logic:notEmpty> </font></td>
															</tr>
													</logic:iterate>
												</logic:notEmpty>
											</table>
											</div>
											</td>
										</tr>
									</table>
									</td>
								</tr>

							</table>
							</td>
						</tr>
					</table>
					</div>
					</td>
				</tr>
				<!-- 1.5 Dados do Boleto -->
				<tr>
					<td colspan="4">
					<div id="layerHideDadosBoleto" style="display: block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('DadosBoleto',true);" /><b>Dados
							do Boleto</b></a></span></td>
						</tr>
					</table>
					</div>

					<div id="layerShowDadosBoleto" style="display: none">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('DadosBoleto',false);" /><b>Dados
							do Boleto</b></a></span></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td>
							<table width="100%">
								<tr>
									<td width="25%"><strong>Nosso Número:</strong></td>
									<td colspan="3"><html:text property="nossoNumero"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="17" /> <logic:notEmpty
										name="AtualizarBoletoBancarioActionForm"
										property="mensagemBoletoLegado">
										<strong><em><font color="#FF0000">${AtualizarBoletoBancarioActionForm.mensagemBoletoLegado}</font></em></strong>
									</logic:notEmpty></td>
								</tr>
								<tr>
									<td width="25%"><strong>Ocorrências da Migração:</strong></td>
									<td colspan="3"><html:text property="ocorrenciaMigracao"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="10" /> <logic:notEmpty
										name="AtualizarBoletoBancarioActionForm">
									</logic:notEmpty></td>
								</tr>
								<tr>
									<td width="25%"><strong>Referência do Faturamento:</strong></td>
									<td><html:text property="anoMesReferenciaBoletoBancario"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="7" /></td>
									<td><strong>Data de Entrada:</strong></td>
									<td><html:text property="dataEntradaBoletoBancario"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="10" /></td>
								</tr>
								<tr>
									<td width="25%"><strong>Data de Vencimento:</strong></td>
									<td><html:text property="dataVencimentoBoletoBancario"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="10" /></td>
									<td width="25%"><strong>Valor:</strong></td>
									<td><html:text property="valorDebitoBoletoBancario"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="10" /></td>
								</tr>
								<tr>
									<td><strong>Situação Atual:</strong></td>
									<td colspan="3"><html:text
										property="descricaoSituacaoBoletoBancario" readonly="true"
										style="background-color:#EFEFEF; border:0;" size="45" /></td>
								</tr>
								<!-- 1.5.7. Dados do Documento do Boleto -->
								<tr>
									<td colspan="4">
									<div id="layerHideDadosDocumentoBoleto" style="display: block">
									<table width="100%" border="0" bgcolor="#99CCFF">
										<tr bgcolor="#99CCFF">
											<td align="center"><span class="style2"> <a
												href="javascript:extendeTabela('DadosDocumentoBoleto',true);" /><b>Dados
											do Documento do Boleto</b></a></span></td>
										</tr>
									</table>
									</div>

									<div id="layerShowDadosDocumentoBoleto" style="display: none">
									<table width="100%" border="0" bgcolor="#99CCFF">
										<tr bgcolor="#99CCFF">
											<td align="center"><span class="style2"> <a
												href="javascript:extendeTabela('DadosDocumentoBoleto',false);" /><b>Dados
											do Documento do Boleto</b></a></span></td>
										</tr>
										<tr bgcolor="#cbe5fe">
											<td>
											<table width="100%">
												<tr>
													<td width="25%"><strong>Tipo do Documento:</strong></td>
													<td colspan="3"><html:text
														property="descricaoDocumentoTipoBoletoBancario"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="45" /></td>
												</tr>

												<!-- 1.5.7.2. Documento de Cobrança -->
												<logic:present name="colecaoBoletoBancarioContaHelper">
													<tr>
														<td colspan="4">
														<table width="100%" align="center" bgcolor="#90c7fc"
															border="0">
															<tr bordercolor="#000000">
																<td width="15%" bgcolor="#90c7fc" align="center">
																<div class="style9"><font color="#000000"
																	style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif"> <strong>Referência</strong>
																</font></div>
																</td>
																<td width="15%" bgcolor="#90c7fc" align="center">
																<div class="style9"><font color="#000000"
																	style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif"> <strong>Vencimento</strong>
																</font></div>
																</td>
																<td width="15%" bgcolor="#90c7fc" align="center"><font
																	color="#000000" style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif"><strong>Valor
																do Débito</strong> </font></td>
																<td width="25%" bgcolor="#90c7fc" align="center"><font
																	color="#000000" style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif"><strong>Situação
																do Item</strong> </font></td>
																<td width="15%" bgcolor="#90c7fc" align="center"><font
																	color="#000000" style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif"><strong>Valor
																Pago</strong> </font></td>
																<td width="15%" bgcolor="#90c7fc" align="center"><font
																	color="#000000" style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif"><strong>Cliente
																Usuário</strong> </font></td>
															</tr>
															<tr>
																<td width="100%" colspan="6">
																<div style="width: 100%; height: 100%; overflow: auto;">
																<table width="100%" align="left" bgcolor="#99CCFF">
																	<!--corpo da segunda tabela-->
																	<%
																		cont = 0;
																	%>
																	<logic:notEmpty name="colecaoBoletoBancarioContaHelper">
																		<logic:iterate name="colecaoBoletoBancarioContaHelper"
																			id="boletoBancarioConta"
																			type="BoletoBancarioContaHelper">
																			<%
																				cont = cont + 1;
																									if (cont % 2 == 0) {
																			%>
																			<tr bgcolor="#cbe5fe">
																				<%
																					} else {
																				%>
																				<tr bgcolor="#FFFFFF">
																					<%
																						}
																					%>
																					<td width="15%" align="center">
																					<div class="style9"><font color="#000000"
																						style="font-size: 9px"
																						face="Verdana, Arial, Helvetica, sans-serif">
																					<%=Util
												.formatarAnoMesParaMesAno(boletoBancarioConta
														.getReferencia())%> </font></div>
																					</td>
																					<td width="15%" bordercolor="#90c7fc"
																						align="center">
																					<div class="style9"><font color="#000000"
																						style="font-size: 9px"
																						face="Verdana, Arial, Helvetica, sans-serif">
																					<bean:write name="boletoBancarioConta"
																						property="dataVencimentoConta"
																						formatKey="date.format" /> </font></div>
																					</td>
																					<td width="15%" align="right" title="${boletoBancarioConta.detalheValorConta}"><font
																						color="#000000" style="font-size: 9px"
																						face="Verdana, Arial, Helvetica, sans-serif">
																					<bean:write name="boletoBancarioConta"
																						property="valorDebito" formatKey="money.format" />
																					</font></td>
																					<td width="25%" align="center"><font
																						color="#000000" style="font-size: 9px"
																						face="Verdana, Arial, Helvetica, sans-serif">
																					<bean:write name="boletoBancarioConta"
																						property="descricaoDebitoCreditoSituacao" />
																					</font></td>
																					<td width="15%" align="right"><font
																						color="#000000" style="font-size: 9px"
																						face="Verdana, Arial, Helvetica, sans-serif">
																					<bean:write name="boletoBancarioConta"
																						property="valorPago" formatKey="money.format" />
																					</font></td>
																					<td width="15%" align="center">
																					<div class="style9"><font color="#000000"
																						style="font-size: 9px"
																						face="Verdana, Arial, Helvetica, sans-serif">
																					<bean:write name="boletoBancarioConta"
																						property="idCliente" /> </font></div>
																					</td>
																				</tr>
																		</logic:iterate>

																		<!-- Totalizador -->
																		<%
																			if (cont % 2 == 0) {
																		%>
																		<tr bgcolor="#cbe5fe">
																			<%
																				} else {
																			%>
																			<tr bgcolor="#FFFFFF">
																				<%
																					}
																				%>

																				<td colspan="2" bgcolor="#90c7fc" align="center">
																				<div class="style9" align="center"><font
																					color="#000000" style="font-size: 9px"
																					face="Verdana, Arial, Helvetica, sans-serif">
																				<strong>Total</strong> </font></div>
																				</td>
																				<td align="right">
																				<div align="right"><font color="#000000"
																					style="font-size: 9px"
																					face="Verdana, Arial, Helvetica, sans-serif">
																				 <%=session.getAttribute("valorTotalDebitoConta")%></font></div>
																				</td>
																				<td align="right" bgcolor="#90c7fc">
																				<div class="style9" align="center"><font
																					color="#000000" style="font-size: 9px"
																					face="Verdana, Arial, Helvetica, sans-serif">
																				<strong>&nbsp;</strong> </font></div>
																				</td>
																				<td align="right">
																				<div align="right"><font color="#000000"
																					style="font-size: 9px"
																					face="Verdana, Arial, Helvetica, sans-serif">
																				 </font></div>
																				<%=session.getAttribute("valorTotalPagoConta")%></td>
																				<td align="right" bgcolor="#90c7fc">
																				<div class="style9" align="center"><font
																					color="#000000" style="font-size: 9px"
																					face="Verdana, Arial, Helvetica, sans-serif">
																				<strong>&nbsp;</strong> </font></div>
																				</td>
																			</tr>
																	</logic:notEmpty>
																</table>
																</div>
																</td>
															</tr>
														</table>
														</td>
													</tr>
												</logic:present>
												<!-- 1.5.7.3. Guia de Pagamento -->
												<logic:present name="colecaoGuiaPagamentoPrestacao">
													<tr>
														<td colspan="8">
														<table width="100%" align="center" bgcolor="#90c7fc"
															border="0">
															<tr bordercolor="#000000">
															
															   <td width="10%" bgcolor="#90c7fc" align="center">							
																
																<div class="style9"><font color="#000000"
																	style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif"> <strong>Guia Pagamento</strong> </font></div>
																</td>
																
																<td width="15%" bgcolor="#90c7fc" align="center">
																<div class="style9"><font color="#000000"
																	style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif"> <strong>Número Prestação</strong> </font></div>
																</td>
															
																<td width="15%" bgcolor="#90c7fc" align="center">
																<div class="style9"><font color="#000000"
																	style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif"> <strong>Data
																de Emissão</strong> </font></div>
																</td>
																<td width="10%" bgcolor="#90c7fc" align="center">
																<div class="style9"><font color="#000000"
																	style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif"> <strong>Tipo
																de Débito</strong> </font></div>
																</td>
																<td width="15%" bgcolor="#90c7fc" align="center"><font
																	color="#000000" style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif"><strong>Valor do Débito</strong>
																</font></td>
																<td width="10%" bgcolor="#90c7fc" align="center"><font
																	color="#000000" style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif"><strong>Situação
																do Item</strong> </font></td>
																<td width="15%" bgcolor="#90c7fc" align="center"><font
																	color="#000000" style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif"><strong>Valor
																Pago</strong> </font></td>
																<td width="10%" bgcolor="#90c7fc" align="center"><font
																	color="#000000" style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif"><strong>Cliente
																Usuário</strong> </font></td>
															</tr>
															<tr>
																<td width="100%" colspan="8">
																<div style="width: 100%; height: 100%; overflow: auto;">
																<table width="100%" align="left" bgcolor="#99CCFF">
																	<!--corpo da segunda tabela-->
																	<%
																		cont = 0;
																	%>
																	<logic:notEmpty name="colecaoGuiaPagamentoPrestacao">
																		<logic:iterate name="colecaoGuiaPagamentoPrestacao"
																			id="guiaPagamentoPrestacao"
																			type="GuiaPagamentoPrestacaoHelper">
																			<%
																				cont = cont + 1;
																									if (cont % 2 == 0) {
																			%>
																			<tr bgcolor="#cbe5fe">
																				<%
																					} else {
																				%>
																				<tr bgcolor="#FFFFFF">
																					<%
																						}
																					%>
																					
																					<td width="10%" bordercolor="#90c7fc" align="left">
																					<div class="style9"><font color="#000000"
																						style="font-size: 9px"
																						face="Verdana, Arial, Helvetica, sans-serif">
																					<bean:write name="guiaPagamentoPrestacao"
																						property="idGuiaPagamento"  />
																					</font></div>
																					</td>
																					
																					
																					<td width="15%" bordercolor="#90c7fc" align="center">
																					<div class="style9"><font color="#000000"
																						style="font-size: 9px"
																						face="Verdana, Arial, Helvetica, sans-serif">
																					<bean:write name="guiaPagamentoPrestacao"
																						property="numeroPrestacao"  />
																					</font></div>
																					</td>
																					
																					
																					<td width="15%" bordercolor="#90c7fc" align="center">
																					<div class="style9"><font color="#000000"
																						style="font-size: 9px"
																						face="Verdana, Arial, Helvetica, sans-serif">
																					<bean:write name="guiaPagamentoPrestacao"
																						property="dataEmissao" formatKey="date.format" />
																					</font></div>
																					</td>
																					<td width="10%" align="left">
																					<div class="style9"><font color="#000000"
																						style="font-size: 9px"
																						face="Verdana, Arial, Helvetica, sans-serif">
																					<bean:write name="guiaPagamentoPrestacao"
																						property="descricaoTipoDebito" /> </font></div>
																					</td>
																					<td width="15%" align="right"><font
																						color="#000000" style="font-size: 9px"
																						face="Verdana, Arial, Helvetica, sans-serif">
																					<bean:write name="guiaPagamentoPrestacao"
																						property="valorPrestacaoTipoDebito"
																						formatKey="money.format" /> </font></td>

																					<td width="10%" align="right"><font
																						color="#000000" style="font-size: 9px"
																						face="Verdana, Arial, Helvetica, sans-serif">
																					<bean:write name="guiaPagamentoPrestacao"
																						property="descricaoDebitoCreditoSituacao" /> </font></td>

																					<td width="15%" align="right"><font
																						color="#000000" style="font-size: 9px"
																						face="Verdana, Arial, Helvetica, sans-serif">
																					<bean:write name="guiaPagamentoPrestacao"
																						property="valorPagamentoStr"
																						formatKey="money.format" /> </font></td>

																					<td width="10%" align="center"><font
																						color="#000000" style="font-size: 9px"
																						face="Verdana, Arial, Helvetica, sans-serif">
																					<logic:notEmpty name="guiaPagamentoPrestacao"
																						property="idCliente">
																						<bean:write name="guiaPagamentoPrestacao"
																							property="idCliente" />
																					</logic:notEmpty> </font></td>
																				</tr>
																		</logic:iterate>
																	</logic:notEmpty>
																</table>
																</div>
																</td>
															</tr>
														</table>
														</td>
													</tr>
												</logic:present>
											</table>
											</td>
										</tr>
									</table>
									</div>
									</td>
								</tr>
								<!-- 1.5.8 Movimentações -->
								<tr>
									<td colspan="4">
									<div id="layerHideMovimentacoes" style="display: block">
									<table width="100%" border="0" bgcolor="#99CCFF">
										<tr bgcolor="#99CCFF">
											<td align="center"><span class="style2"> <a
												href="javascript:extendeTabela('Movimentacoes',true);" /><b>Movimentações</b></a></span></td>
										</tr>
									</table>
									</div>
									<div id="layerShowMovimentacoes" style="display: none">
									<table width="100%" border="0" bgcolor="#99CCFF">
										<tr bgcolor="#99CCFF">
											<td align="center"><span class="style2"> <a
												href="javascript:extendeTabela('Movimentacoes',false);" /><b>Movimentações</b></a></span></td>
										</tr>
										<tr bgcolor="#cbe5fe">
											<td>
											<table width="100%">
												<tr>
													<td colspan="4">
													<table width="100%" align="center" bgcolor="#79bbfd"
														border="0">
														<tr bordercolor="#000000">
															<td width="13%" bgcolor="#90c7fc" align="center">
															<div class="style9"><font color="#000000"
																style="font-size: 9px"
																face="Verdana, Arial, Helvetica, sans-serif"> <strong>Data</strong>
															</font></div>
															</td>
															<td width="9%" bgcolor="#90c7fc" align="center">
															<div class="style9"><font color="#000000"
																style="font-size: 9px"
																face="Verdana, Arial, Helvetica, sans-serif"> <strong>Tipo</strong>
															</font></div>
															</td>
															<td width="30%" bgcolor="#90c7fc" align="center"><font
																color="#000000" style="font-size: 9px"
																face="Verdana, Arial, Helvetica, sans-serif"><strong>Lançamento</strong>
															</font></td>
															<td width="48%" bgcolor="#90c7fc" align="center"><font
																color="#000000" style="font-size: 9px"
																face="Verdana, Arial, Helvetica, sans-serif"><strong>Ocorrências</strong>
															</font></td>
														</tr>
														<tr>
															<td width="100%" colspan="4">
															<div style="width: 100%; height: 100%; overflow: auto;">
															<table width="100%" align="left" bgcolor="#99CCFF">
																<!--corpo da segunda tabela-->
																<%
																	cont = 0;
																%>
																<logic:notEmpty
																	name="colecaoBoletoBancarioMovimentacaoHelper">
																	<logic:iterate
																		name="colecaoBoletoBancarioMovimentacaoHelper"
																		id="movimentacaoHelper"
																		type="BoletoBancarioMovimentacaoHelper">
																		<%
																			cont = cont + 1;
																							if (cont % 2 == 0) {
																		%>
																		<tr bgcolor="#cbe5fe">
																			<%
																				} else {
																			%>
																			<tr bgcolor="#FFFFFF">
																				<%
																					}
																				%>
																				<td width="13%" bordercolor="#90c7fc" align="left">
																				<div class="style9"><font color="#000000"
																					style="font-size: 9px"
																					face="Verdana, Arial, Helvetica, sans-serif">
																				<logic:present name="movimentacaoHelper"
																					property="boletoBancarioMovimentacao">
																					<bean:write name="movimentacaoHelper"
																						property="boletoBancarioMovimentacao.dataMovimentacao"
																						formatKey="date.format" />
																				</logic:present> </font></div>
																				</td>
																				<td width="9%" align="left">
																				<div class="style9"><font color="#000000"
																					style="font-size: 9px"
																					face="Verdana, Arial, Helvetica, sans-serif">
																				<logic:present name="movimentacaoHelper"
																					property="boletoBancarioMovimentacao">
																					<logic:present name="movimentacaoHelper"
																						property="boletoBancarioMovimentacao.boletoBancarioLancamentoEnvio">
                                                                    Envio
																	</logic:present>
																					<logic:present name="movimentacaoHelper"
																						property="boletoBancarioMovimentacao.boletoBancarioLancamentoRetorno">
                                                                    Retorno
																	</logic:present>
																				</logic:present> </font></div>
																				</td>
																				<td width="30%" align="left">
																				<div class="style9"><font color="#000000"
																					style="font-size: 9px"
																					face="Verdana, Arial, Helvetica, sans-serif">
																				<logic:present name="movimentacaoHelper"
																					property="boletoBancarioMovimentacao">
																					<logic:present name="movimentacaoHelper"
																						property="boletoBancarioMovimentacao.boletoBancarioLancamentoEnvio">
																						<bean:write name="movimentacaoHelper"
																							property="boletoBancarioMovimentacao.boletoBancarioLancamentoEnvio.descricaoLancamentoEnvio" />
																					</logic:present>
																					<logic:present name="movimentacaoHelper"
																						property="boletoBancarioMovimentacao.boletoBancarioLancamentoRetorno">
																						<bean:write name="movimentacaoHelper"
																							property="boletoBancarioMovimentacao.boletoBancarioLancamentoRetorno.descricaoLancamentoRetorno" />
																					</logic:present>
																				</logic:present> </font></div>
																				</td>

																				<logic:present name="movimentacaoHelper"
																					property="boletoBancarioMovimentacao">
																					<logic:present name="movimentacaoHelper"
																						property="boletoBancarioMovimentacao.boletoBancarioLancamentoEnvio">
																						<td width="48%" rowspan="2">
																					</logic:present>
																					<logic:notPresent name="movimentacaoHelper"
																						property="boletoBancarioMovimentacao.boletoBancarioLancamentoEnvio">
																						<td width="48%">
																					</logic:notPresent>
																				</logic:present>
																				<div
																					style="width: 100%; height: 30; overflow: auto;">
																				<font color="#000000" style="font-size: 9px"
																					face="Verdana, Arial, Helvetica, sans-serif">
																				<table width="100%" bgcolor="#99C7FC">
																					<logic:present name="movimentacaoHelper"
																						property="colecaoBoletoBancarioOcorrencias">

																						<%
																							int cont2 = 0;
																						%>
																						<logic:iterate name="movimentacaoHelper"
																							property="colecaoBoletoBancarioOcorrencias"
																							id="ocorrencia" type="BoletoBancarioOcorrencias">

																							<%
																								cont2 = cont2 + 1;
																														if (cont2 % 2 == 0) {
																							%>
																							<tr bgcolor="#cbe5fe">
																								<%
																									} else {
																								%>
																								<tr bgcolor="#FFFFFF">
																									<%
																										}
																									%>
																									<bean:write name="ocorrencia"
																										property="boletoBancarioMotivoOcorrencia.codigoComDescricao" />
																								</tr>
																						</logic:iterate>
																					</logic:present>
																				</table>
																				</font></div>
																				</td>
																			</tr>
																			<logic:present name="movimentacaoHelper"
																				property="situacaoEnvio">
																				<%
																					if (cont % 2 == 0) {
																				%>
																				<tr bgcolor="#cbe5fe">
																					<%
																						} else {
																					%>
																					<tr bgcolor="#FFFFFF">
																						<%
																							}
																						%>
																						<td colspan="3" align="left"><font
																							color="#000000" style="font-size: 9px"
																							face="Verdana, Arial, Helvetica, sans-serif">Situação:&nbsp;
																						<bean:write name="movimentacaoHelper"
																							property="situacaoEnvio" /> </font></td>
																					</tr>
																			</logic:present>
																	</logic:iterate>
																</logic:notEmpty>
															</table>
															</div>
															</td>
														</tr>

													</table>
													</td>
												</tr>
											</table>
											</td>
										</tr>
									</table>
									</div>
									</td>
								</tr>

								<!-- 1.5.9 Dados de Pagamento do Boleto -->
								<tr>
									<td colspan="4">
									<div id="layerHideDadosPagamentoBoleto" style="display: block">
									<table width="100%" border="0" bgcolor="#99CCFF">
										<tr bgcolor="#99CCFF">
											<td align="center"><span class="style2"> <a
												href="javascript:extendeTabela('DadosPagamentoBoleto',true);" /><b>Dados
											de Pagamento do Boleto</b></a></span></td>
										</tr>
									</table>
									</div>
									<div id="layerShowDadosPagamentoBoleto" style="display: none">
									<table width="100%" border="0" bgcolor="#99CCFF">
										<tr bgcolor="#99CCFF">
											<td align="center"><span class="style2"> <a
												href="javascript:extendeTabela('DadosPagamentoBoleto',false);" /><b>Dados
											de Pagamento do Boleto</b></a></span></td>
										</tr>
										<tr bgcolor="#cbe5fe">
											<td>
											<table width="100%">
												<tr>
													<td><strong>Agência do Pagamento:</strong></td>
													<td><html:text property="agenciaPagamento"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="10" /></td>
												</tr>
												<tr>
													<td width="25%"><strong>Data do Pagamento:</strong></td>
													<td><html:text property="dataPagamentoBoletoBancario"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="10" /></td>
													<td><strong>Data do Crédito:</strong></td>
													<td><html:text property="dataCreditoBoletoBancario"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="10" /></td>
												</tr>
												<tr>
													<td><strong>Valor Pago:</strong></td>
													<td><html:text property="valorPagoBoletoBancario"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="10" /></td>
													<td><strong>Valor Tarifa:</strong></td>
													<td><html:text property="valorTarifaBoletoBancario"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="10" /></td>
												</tr>
												<tr>
													<td><strong>Valor Multa/Juros:</strong></td>
													<td><html:text property="valorMultaJurosBoletoBancario"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="10" /></td>
												</tr>
											</table>
											</td>
										</tr>
									</table>
									</div>
									</td>
								</tr>
								<!-- 1.5.10 Dados de Cancelamento do Boleto -->
								<tr>
									<td colspan="4">
									<div id="layerHideDadosCancelamentoBoleto"
										style="display: block">
									<table width="100%" border="0" bgcolor="#99CCFF">
										<tr bgcolor="#99CCFF">
											<td align="center"><span class="style2"> <a
												href="javascript:extendeTabela('DadosCancelamentoBoleto',true);" /><b>Dados
											de Cancelamento do Boleto</b></a></span></td>
										</tr>
									</table>
									</div>
									<div id="layerShowDadosCancelamentoBoleto"
										style="display: none">
									<table width="100%" border="0" bgcolor="#99CCFF">
										<tr bgcolor="#99CCFF">
											<td align="center"><span class="style2"> <a
												href="javascript:extendeTabela('DadosCancelamentoBoleto',false);" /><b>Dados
											de Cancelamento do Boleto</b></a></span></td>
										</tr>
										<tr bgcolor="#cbe5fe">
											<td>
											<table width="100%">
												<tr>
													<td width="25%"><strong>Data do Cancelamento:</strong></td>
													<td><html:text
														property="dataCancelamentoBoletoBancario" readonly="true"
														style="background-color:#EFEFEF; border:0;" size="10" /></td>
												</tr>
												<tr>
													<td><strong>Motivo do Cancelamento:</strong></td>
													<td><html:text
														property="descricaoMotivoCancelamentoBoletoBancario"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="45" /></td>
												</tr>
											</table>
											</td>
										</tr>
									</table>
									</div>
									</td>
								</tr>
								<!-- 1.5.11. Dados do Parcelamento do Boleto -->
								<tr>
									<td colspan="4">
									<div id="layerHideDadosParcelamentoBoleto" style="display: block">
									<table width="100%" border="0" bgcolor="#99CCFF">
										<tr bgcolor="#99CCFF">
											<td align="center"><span class="style2"> <a
												href="javascript:extendeTabela('DadosParcelamentoBoleto',true);" /><b>Dados
											do Parcelamento do Boleto</b></a></span></td>
										</tr>
									</table>
									</div>
									<div id="layerShowDadosParcelamentoBoleto" style="display: none">
									<table width="100%" border="0" bgcolor="#99CCFF">
										<tr bgcolor="#99CCFF">
											<td align="center"><span class="style2"> <a
												href="javascript:extendeTabela('DadosParcelamentoBoleto',false);" /><b>Dados
											do Parcelamento do Boleto</b></a></span></td>
										</tr>
										<tr bgcolor="#cbe5fe">
											<td>
											<table width="100%" align="center" bgcolor="#90c7fc" border="0">
												<tr bordercolor="90c7fc">
													<td bgcolor="90c7fc" align="center"><strong>Data</strong></td>
													<td align="center" bgcolor="90c7fc"><strong>Hora</strong></td>
													<td align="center" bgcolor="90c7fc"><strong>D&eacute;bito Atualizado</strong></td>
													<td align="center" bgcolor="90c7fc"><strong>Desconto Concedido</strong></td>
													<td align="center" bgcolor="90c7fc"><strong>Valor da Entrada</strong></td>
													<td align="center" bgcolor="90c7fc"><strong>N&uacute;mero de Presta&ccedil;&otilde;es</strong></td>
													<td align="center" bgcolor="90c7fc"><strong>Valor da Presta&ccedil;&atilde;o</strong></td>
													<td align="center" bgcolor="90c7fc"><strong>Forma de Cobran&ccedil;a</strong></td>
													<td align="center" bgcolor="90c7fc"><strong>Situa&ccedil;&atilde;o</strong></td>
												</tr>
												<%String cor = "#cbe5fe";%>	
												<logic:present name="colecaoParcelamento" scope="request">
													<logic:iterate name="colecaoParcelamento"
														id="parcelamento">
												
													<%if (cor.equalsIgnoreCase("#cbe5fe")) {
															cor = "#FFFFFF";%>
														<tr bgcolor="#FFFFFF">
															<%} else {
															cor = "#cbe5fe";%>
														<tr bgcolor="#cbe5fe">
															<%}%>
														<td align="center">
							
															<a href="javascript:abrirPopup('exibirConsultarParcelamentoDebitoPopupAction.do?codigoImovel=<bean:define name="parcelamento"
															property="imovel" id="imovel" />
															<bean:write name="imovel" property="id" />&codigoParcelamento=<bean:write name="parcelamento" property="id" />', 400, 800);">
															<bean:write name="parcelamento" property="parcelamento" formatKey="date.format" /></a>
														</td>
														<td align="center"><bean:write name="parcelamento" property="parcelamento" formatKey="hour.format"/></td>
														<td align="right"><bean:write name="parcelamento" property="valorDebitoAtualizado"  format="0.00"/></td>
														<td align="right"><bean:write name="parcelamento" property="valorDesconto"  format="0.00"/></td>
														<td align="right"><bean:write name="parcelamento" property="valorEntrada"  format="0.00"/></td>
														<td align="right"><bean:write name="parcelamento" property="numeroPrestacoes"/></td>
														<td align="right"><bean:write name="parcelamento" property="valorPrestacao"  format="0.00"/></td>
														<td align="right"><bean:write name="parcelamento" property="cobrancaForma.descricaoAbreviada"/></td>
														<td align="left"><bean:write name="parcelamento" property="parcelamentoSituacao.descricaoAbreviada" /></td>
													</tr>
													</logic:iterate>
												</logic:present>
											</table>

											</td>
										</tr>
									</table>
									</div>
									</td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					</div>
					</td>
				</tr>

				<logic:equal name="AtualizarBoletoBancarioActionForm"
					property="permitirAlteracao" value="<%=""+ConstantesSistema.SIM%>">
					<!-- 1.6 Dados da Alteração -->
					<tr>
						<td colspan="4">
						<table width="100%" border="0" bgcolor="#99CCFF">
							<tr bgcolor="#99CCFF">
								<td align="center"><span class="style2"> <b>Dados
								da Alteração</b></span></td>
							</tr>
							<tr bgcolor="#cbe5fe">
								<td>
								<table width="100%">
									<tr>
										<td width="25%"><strong>Lançamento de Envio<font
											color="#FF0000">*</font>:</strong></td>
										<td><html:select
											property="idBoletoBancarioLancamentoEnvio"
											style="width: 350px;">
											<html:option
												value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
											<logic:present name="colecaoBoletoBancarioLancamentoEnvio">
												<html:options
													collection="colecaoBoletoBancarioLancamentoEnvio"
													labelProperty="descricaoComId" property="id" />
											</logic:present>
										</html:select></td>
									</tr>
									<tr>
										<td><strong>Data de Vencimento do Título:</strong></td>
										<td><html:text property="dataVencimentoTitulo" size="11"
											maxlength="10" onkeyup="mascaraData(this, event);" /> <a
											href="javascript:abrirCalendario('AtualizarBoletoBancarioActionForm', 'dataVencimentoTitulo')">
										<img border="0"
											src="<bean:message key='caminho.imagens'/>calendario.gif"
											width="20" border="0" align="absmiddle"
											alt="Exibir Calendário" /></a> dd/mm/aaaa</td>
									</tr>
								</table>
								</td>
							</tr>
						</table>
						</td>
					</tr>
				</logic:equal>

				<tr>
					<td align="right">
					<div align="left"><strong><font color="#FF0000">*</font></strong>Campos
					obrigat&oacute;rios</div>
					</td>
				</tr>

				<tr>

					<html:hidden name="AtualizarBoletoBancarioActionForm"
						property="idBoletoBancario" />
					<html:hidden name="AtualizarBoletoBancarioActionForm"
						property="idSituacaoBoletoBancario" />
					<html:hidden name="AtualizarBoletoBancarioActionForm"
						property="permitirAlteracao" />

					<td colspan="2" width="100%">
					<table width="100%">
						<tr>
							<td>
							<logic:present name="voltarAtualizar">
								<logic:equal name="voltarAtualizar" value="filtrar">
									<input name="button" type="button" class="bottonRightCol" value="Voltar" onclick="window.location.href='<html:rewrite page="/exibirFiltrarBoletoBancarioAction.do"/>'" align="left" style="width: 80px;">
								</logic:equal>
								<logic:equal name="voltarAtualizar" value="manter">
									<input name="button" type="button" class="bottonRightCol" value="Voltar" onclick="window.location.href='<html:rewrite page="/exibirManterBoletoBancarioAction.do"/>'" align="left" style="width: 80px;">
								</logic:equal>
							</logic:present>
							<logic:notPresent name="voltarAtualizar">
								<input name="button" type="button" class="bottonRightCol" value="Voltar" onclick="window.location.href='<html:rewrite page="/exibirManterBoletoBancarioAction.do"/>'" align="left" style="width: 80px;">
							</logic:notPresent>

							<logic:equal name="AtualizarBoletoBancarioActionForm"
								property="permitirAlteracao"
								value="<%=""+ConstantesSistema.SIM%>">
								<input name="Submit22" class="bottonRightCol" value="Desfazer"
									type="button"
									onclick="window.location.href='/gsan/exibirAtualizarBoletoBancarioAction.do?desfazer=S';">
							</logic:equal> <logic:equal name="AtualizarBoletoBancarioActionForm"
								property="permitirAlteracao"
								value="<%=""+ConstantesSistema.NAO%>">
								<input name="Submit22" class="bottonRightCol" value="Desfazer"
									type="button" disabled="disabled">
							</logic:equal> <input name="Submit23" class="bottonRightCol" value="Cancelar"
								type="button"
								onclick="window.location.href='/gsan/telaPrincipal.do'">
							</td>

							<logic:equal name="AtualizarBoletoBancarioActionForm"
								property="permitirAlteracao"
								value="<%=""+ConstantesSistema.SIM%>">
								<td align="right"><gcom:controleAcessoBotao name="Button"
									value="Atualizar"
									onclick="javascript:validarForm(document.forms[0]);"
									url="atualizarBoletoBancarioAction.do" /></td>
							</logic:equal>
							<logic:equal name="AtualizarBoletoBancarioActionForm"
								property="permitirAlteracao"
								value="<%=""+ConstantesSistema.NAO%>">
								<td align="right"><input name="Submit24"
									class="bottonRightCol" value="Atualizar" type="button"
									disabled="disabled"></td>
							</logic:equal>
						</tr>
					</table>
					</td>
				</tr>
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>

</html:form>


</body>
</html:html>
