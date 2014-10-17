<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="gcom.cadastro.cliente.ClienteImovel"%>
<%@ page import="gcom.cadastro.imovel.ImovelSubcategoria"%>
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
	formName="AtualizarSituacaoContaActionForm" />

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

	function validarForm(formulario) {
		formulario.action = "/gsan/atualizarSituacaoContaAction.do";
		submeterFormPadrao(formulario);
		
	}

	function contaAnterior(){
		var form = document.forms[0];
 		form.action = "exibirAtualizarSituacaoContaAction.do?idConta=" + form.idAnterior.value;
 		submeterFormPadrao(form);
	}

	function proximaConta(){
 		var form = document.forms[0];
 		form.action = "exibirAtualizarSituacaoContaAction.do?idConta=" + form.idPosterior.value;
 		submeterFormPadrao(form);
	}

	
</script>

</head>

<body leftmargin="5" topmargin="5" onload="extendeTabela('DadosConta',true);extendeTabela('DadosGeraisConta',true);">

<html:form action="/exibirAtualizarSituacaoContaAction"
	name="AtualizarSituacaoContaActionForm"
	type="gcom/gui/faturamento/conta/AtualizarSituacaoContaActionForm"
	method="post">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<input type="hidden" value="${sessionScope.idContaPosterior}" name="idPosterior" />
	<input type="hidden" value="${sessionScope.idContaAnterior}" name="idAnterior" />

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
					<td class="parabg">Atualizar Situa&ccedil;&atilde;o da Conta</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>

			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td colspan="2">Dados Gerais da Conta Pré-Faturada:</td>
				</tr>
							    <tr>
					<td>						      			
									
						<table cellspacing="0" cellpadding="0" width="100%" border="0">
										
						<tr>
							<logic:present name="desabilitaBotaoAnterior" scope="session">				
								<td align="left" width="3%"><img
									src="<bean:message key="caminho.imagens"/>voltar.gif"
									onclick="contaAnterior();"></td>
								<td align="left" width="97%"><input type="button"
									name="Button" class="buttonAbaRodaPe" value="Anterior"
									onclick="contaAnterior();" /></td>
							</logic:present>				
							<logic:present name="desabilitaBotaoProximo" scope="session">	
								<td align="right" width="97%"><input type="button"
									name="Button" class="buttonAbaRodaPe" value="Próximo"
									onclick="proximaConta();" /></td>
								<td align="right" width="3%"><img
									src="<bean:message key="caminho.imagens"/>avancar.gif"
									onclick="proximaConta()"></td>
							</logic:present>
						</tr>
						</table>
					</td>
			    </tr>
				
			</table>

			<table width="100%" border="0">
				<tr>
					<td><strong>Im&oacute;vel:</strong></td>
					<td width="80%"><html:text property="imovel" readonly="true"
						style="background-color:#EFEFEF; border:0;" size="15" /></td>
				</tr>
				<tr>
					<td><strong>Refer&ecirc;ncia da Conta:</strong></td>
					<td width="80%"><html:text property="dataReferenciaConta" readonly="true"
						style="background-color:#EFEFEF; border:0;" size="15" /></td>
				</tr>
				<tr>
					<td><strong>Grupo Faturamento:</strong></td>
					<td width="80%"><html:text property="faturamentoGrupoDsAbreviado"
						readonly="true" style="background-color:#EFEFEF; border:0;"
						size="15" /></td>
				</tr>
				<tr>	
					<td><strong>Vencimento da Conta:</strong></td>
					<td width="80%"><html:text property="dataVencimentoConta" readonly="true"
						style="background-color:#EFEFEF; border:0;" size="15" /></td>
				</tr>
				<tr>
					<td><strong>Valor da Conta:</strong></td>
					<td width="80%"><html:text property="valorConta"
						readonly="true" style="background-color:#EFEFEF; border:0;"
						size="15" /></td>
				</tr>
				<tr>	
					<td><strong>Situa&ccedil;&atilde;o Atual:</strong></td>
					<td width="80%"><html:text property="situacaoConta" readonly="true"
						style="background-color:#EFEFEF; border:0;" size="15" /></td>
				</tr>
				<!-- 1.7.	Dados do Imóvel -->
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
									<td width="25%"><strong>Rota:</strong></td>
									<td><html:text property="rota"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="20" /></td>
									<td><strong>Segmento:</strong></td>
									<td><html:text property="segmento"
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
											<div class="style9">${AtualizarSituacaoContaActionForm.enderecoImovel}
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
				<!-- 1.5 Dados da Conta -->
				<tr>
					<td colspan="4">
					<div id="layerHideDadosConta" style="display: block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('DadosConta',true);" /><b>Dados
							da Conta</b></a></span></td>
						</tr>
					</table>
					</div>

					<div id="layerShowDadosConta" style="display: none">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('DadosConta',false);" /><b>Dados
							da Conta</b></a></span></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td>
							<table width="100%">
								<!-- 1.8.1.	Dados Gerais da Conta -->
								<tr>
									<td colspan="4">
									<div id="layerHideDadosGeraisConta" style="display: block">
									<table width="100%" border="0" bgcolor="#99CCFF">
										<tr bgcolor="#99CCFF">
											<td align="center"><span class="style2"> <a
												href="javascript:extendeTabela('DadosGeraisConta',true);" /><b>Dados
											Gerais da Conta</b></a></span></td>
										</tr>
									</table>
									</div>

									<div id="layerShowDadosGeraisConta" style="display: none">
									<table width="100%" border="0" bgcolor="#99CCFF">
										<tr bgcolor="#99CCFF">
											<td align="center"><span class="style2"> <a
												href="javascript:extendeTabela('DadosGeraisConta',false);" /><b>Dados
											Gerais da Conta</b></a></span></td>
										</tr>
										<tr bgcolor="#cbe5fe">
											<td>
											<table width="100%">
												<tr>
													<td><strong>Situação da Conta:</strong></td>
													<td><html:text
														property="dsSitucaoDebitoCredito"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="30" /></td>
												</tr>
												<tr>
													<td width="35%"><strong>Mês/ano de Referência da Conta:</strong></td>
													<td><html:text
														property="refereciaConta"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="30" /></td>
												</tr>
												<tr>
													<td><strong>Mês/ano de Referência Contábil:</strong></td>
													<td><html:text
														property="referenciaContabil"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="30" /></td>
												</tr>
												<tr>
													<td><strong>Situação da Ligação de Água:</strong></td>
													<td><html:text
														property="dsLigacaoAguaSituacao"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="30" /></td>
												</tr>
												<tr>
													<td><strong>Situação da Ligação de Esgoto:</strong></td>
													<td><html:text
														property="dsLigacaoEsgotoSituacao"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="30" /></td>
												</tr>
												<tr>
													<td><strong>Perfil do Imóvel:</strong></td>
													<td><html:text
														property="dsImovelPerfil"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="30" /></td>
												</tr>
												<tr>
													<td><strong>Vencimento da Conta:</strong></td>
													<td><html:text
														property="dtVencimentoConta"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="30" /></td>
												</tr>
												<tr>
													<td><strong>Validade da Conta:</strong></td>
													<td><html:text
														property="dtValidadeConta"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="30" /></td>
												</tr>
												<tr>
													<td><strong>Percentual de Esgoto:</strong></td>
													<td><html:text
														property="pcEsgoto"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="30" /></td>
												</tr>
												<tr>
													<td><strong>Fixo de Esgoto:</strong></td>
													<td><html:text
														property="nnConsumoMinimoEsgoto"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="30" /></td>
												</tr>
												<tr>
													<td><strong>Tarifa:</strong></td>
													<td><html:text
														property="dsConsumoTarifa"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="30" /></td>
												</tr>
												<tr>
													<td><strong>Valor de Água:</strong></td>
													<td><html:text
														property="vlAgua"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="30" /></td>
												</tr>
												<tr>
													<td><strong>Valor de Esgoto:</strong></td>
													<td><html:text
														property="vlEsgoto"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="30" /></td>
												</tr>
												<tr>
													<td><strong>Valor dos Débitos:</strong></td>
													<td><html:text
														property="vlDebitos"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="30" /></td>
												</tr>
												<tr>
													<td><strong>Valor dos Créditos:</strong></td>
													<td><html:text
														property="vlCreditos"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="30" /></td>
												</tr>
												<tr>
													<td><strong>Valor dos Impostos:</strong></td>
													<td><html:text
														property="vlImpostos"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="30" /></td>
												</tr>
												<tr>
													<td width="25%"><strong>Valor Total da Conta:</strong></td>
													<td colspan="3"><html:text
														property="vlTotalConta"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="30" /></td>
												</tr>
												<tr>
													<td><strong>Débito em Conta:</strong></td>
													<td><html:text
														property="icDebitoConta"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="30" /></td>
												</tr>
												<logic:equal name="conta"
													property="indicadorDebitoConta" value="<%=""+ConstantesSistema.SIM%>">
													<tr>
														<td><strong>Banco:</strong></td>
														<td><html:text
															property="nmBanco"
															readonly="true"
															style="background-color:#EFEFEF; border:0;" size="30" /></td>
													</tr>
													<tr>
														<td><strong>Agência:</strong></td>
														<td><html:text
															property="nmAgencia"
															readonly="true"
															style="background-color:#EFEFEF; border:0;" size="30" /></td>
													</tr>
													<tr>
														<td><strong>Conta:</strong></td>
														<td><html:text
															property="dsIdentificacaoClienteBCO"
															readonly="true"
															style="background-color:#EFEFEF; border:0;" size="30" /></td>
													</tr>
												</logic:equal>
											</table>
											</td>
										</tr>
									</table>
									</div>
									</td>
								</tr>
								<!-- 1.8.2.	Dados Retificação/Cancelamento/Revisão -->
								<tr>
									<td colspan="4">
									<div id="layerHideRetificacaoCancelamentoRevisao" style="display: block">
									<table width="100%" border="0" bgcolor="#99CCFF">
										<tr bgcolor="#99CCFF">
											<td align="center"><span class="style2"> <a
												href="javascript:extendeTabela('RetificacaoCancelamentoRevisao',true);" /><b>Dados
												da Retificação / Cancelamento / Revisão</b></a></span></td>
										</tr>
									</table>
									</div>
									<div id="layerShowRetificacaoCancelamentoRevisao" style="display: none">
									<table width="100%" border="0" bgcolor="#99CCFF">
										<tr bgcolor="#99CCFF">
											<td align="center"><span class="style2"> <a
												href="javascript:extendeTabela('RetificacaoCancelamentoRevisao',false);" /><b>Dados
												da Retificação / Cancelamento / Revisão</b></a></span></td>
										</tr>
										<tr bgcolor="#cbe5fe">
											<td>
											<table width="100%">
												<tr>
													<td><strong>Data da Retificação:</strong></td>
													<td width="63%"><html:text
														property="dtRetificacao"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="30" /></td>
												</tr>
												<tr>
													<td><strong>Motivo da Retificação:</strong></td>
													<td width="63%"><html:text
														property="dsMotivoRetificacaoConta"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="30" /></td>
												</tr>
												<tr>
													<td><strong>Data do Cancelamento:</strong></td>
													<td width="63%"><html:text
														property="dtCancelamento"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="30" /></td>
												</tr>
												<tr>
													<td><strong>Motivo do Cancelamento:</strong></td>
													<td width="63%"><html:text
														property="dsMotivoCancelamentoConta"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="30" /></td>
												</tr>
												<tr>
													<td><strong>Data da Revisão:</strong></td>
													<td width="63%"><html:text
														property="dtRevisao"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="30" /></td>
												</tr>
												<tr>
													<td><strong>Motivo da Revisão:</strong></td>
													<td width="63%"><html:text
														property="dsMotivoRevisaoConta"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="30" /></td>
												</tr>
												<tr>
													<td><strong>Usuário Responsável pela Revisão:</strong></td>
													<td width="63%">
														<html:text property="usuarioResposavelRevisao"
														readonly="true"	style="background-color:#EFEFEF; border:0;" size="5" />
														<html:text property="nomeUsuarioResposavelRevisao" readonly="true"
														style="background-color:#EFEFEF; border:0;" size="21" /></td>
												</tr>
											</table>
											</td>
										</tr>
									</table>
									</div>
									</td>
								</tr>

								<!-- 1.8.3.	Dados do Último Faturamento -->
								<tr>
									<td colspan="4">
									<div id="layerHideDadosUltimoFaturamento" style="display: block">
									<table width="100%" border="0" bgcolor="#99CCFF">
										<tr bgcolor="#99CCFF">
											<td align="center"><span class="style2"> <a
												href="javascript:extendeTabela('DadosUltimoFaturamento',true);" /><b>Dados
											do Último Faturamento</b></a></span></td>
										</tr>
									</table>
									</div>
									<div id="layerShowDadosUltimoFaturamento" style="display: none">
									<table width="100%" border="0" bgcolor="#99CCFF">
										<tr bgcolor="#99CCFF">
											<td align="center"><span class="style2"> <a
												href="javascript:extendeTabela('DadosUltimoFaturamento',false);" /><b>Dados
											do Último Faturamento</b></a></span></td>
										</tr>
										<tr bgcolor="#cbe5fe">
											<td>
											<table width="100%">
												<tr>
													<td><strong>Leitura Anterior:</strong></td>
													<td width="62%"><html:text property="nnLeituraAnteriorFaturamento"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="30" /></td>
												</tr>
												<tr>	
													<td><strong>Data da Leitura Anterior:</strong></td>
													<td width="62%"><html:text property="dtLeiruraAnteriorFaturamento"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="30" /></td>
												</tr>
												<tr>
													<td><strong>Anormalidade de Leitura Anterior:</strong></td>
													<td width="62%"><html:text property="dsLeituraAnormalidade"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="30" /></td>
												</tr>
												<tr>
													<td><strong>Dias de Consumo Anterior:</strong></td>
													<td width="62%"><html:text property="diasConsumoAnterior"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="30" /></td>
												</tr>
												<tr>
													<td><strong>Anormalidade de Consumo Anterior:</strong></td>
													<td width="62%"><html:text property="dsConsumoAnormalidade"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="30" /></td>
												</tr>
												<tr>
													<td><strong>Tipo de Leitura Anterior:</strong></td>
													<td width="62%"><html:text property="dsLeituraSituacao"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="30" /></td>
												</tr>
												<tr>
													<td><strong>Tipo de Consumo Anterior:</strong></td>
													<td width="62%"><html:text property="dsConsumoTipo"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="30" /></td>
												</tr>
											</table>
											</td>
										</tr>
									</table>
									</div>
									</td>
								</tr>
								<!-- 1.8.4.	Clientes da Conta -->
								<tr>
									<td colspan="4">
									<div id="layerHideDadosClientesConta" style="display: block">
									<table width="100%" border="0" bgcolor="#99CCFF">
										<tr bgcolor="#99CCFF">
											<td align="center"><span class="style2"> <a
												href="javascript:extendeTabela('DadosClientesConta',true);" /><b>Dados
											dos Clientes da Conta</b></a></span></td>
										</tr>
									</table>
									</div>
									<div id="layerShowDadosClientesConta" style="display: none">
									<table width="100%" border="0" bgcolor="#99CCFF">
										<tr bgcolor="#99CCFF">
											<td align="center"><span class="style2"> <a
												href="javascript:extendeTabela('DadosClientesConta',false);" /><b>Dados
											dos Clientes da Conta</b></a></span></td>
										</tr>
										<tr bgcolor="#cbe5fe">
											<td>
											<table width="100%" align="center" bgcolor="#90c7fc" border="0">
												<tr bordercolor="90c7fc">
													<td bgcolor="90c7fc" align="center"><strong>Nome do Cliente </strong></td>
													<td align="center" bgcolor="90c7fc"><strong>Nome na Conta </strong></td>
													<td align="center" bgcolor="90c7fc"><strong>Tipo de Rela&ccedil;&atilde;o </strong></td>
													<td align="center" bgcolor="90c7fc"><strong>CPF/CNPJ</strong></td>
												</tr>
												<%String cor = "#cbe5fe";%>	
												<logic:present name="colecaoClienteConta" scope="session">
													<logic:iterate name="colecaoClienteConta"
														id="clienteConta">
												
													<%if (cor.equalsIgnoreCase("#cbe5fe")) {
															cor = "#FFFFFF";%>
														<tr bgcolor="#FFFFFF">
															<%} else {
															cor = "#cbe5fe";%>
														<tr bgcolor="#cbe5fe">
															<%}%>
														<td align="center"><bean:write name="clienteConta" property="cliente.nome"/></td>
														<logic:equal name="clienteConta"
															property="indicadorNomeConta" value="<%=""+ConstantesSistema.SIM%>">
															<td align="center">Sim</td>
														</logic:equal>
														<logic:notEqual name="clienteConta"
															property="indicadorNomeConta" value="<%=""+ConstantesSistema.SIM%>">
															<td align="center">Não</td>
														</logic:notEqual>
														<td align="center"><bean:write name="clienteConta" property="clienteRelacaoTipo.descricao"/></td>
														<logic:equal name="clienteConta"
															property="cliente.clienteTipo.indicadorPessoaFisicaJuridica" value="<%=""+ConstantesSistema.SIM%>">
															<td align="center"><bean:write name="clienteConta" property="cliente.cpf"/></td>
														</logic:equal>
														<logic:notEqual name="clienteConta"
															property="cliente.clienteTipo.indicadorPessoaFisicaJuridica" value="<%=""+ConstantesSistema.SIM%>">
															<td align="center"><bean:write name="clienteConta" property="cliente.cnpj"/></td>
														</logic:notEqual>
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
								<!-- 1.8.5.	Categorias da Conta -->
								<tr>
									<td colspan="4">
									<div id="layerHideDadosCategoriasConta" style="display: block">
									<table width="100%" border="0" bgcolor="#99CCFF">
										<tr bgcolor="#99CCFF">
											<td align="center"><span class="style2"> <a
												href="javascript:extendeTabela('DadosCategoriasConta',true);" /><b>Dados
											das Categorias da Conta</b></a></span></td>
										</tr>
									</table>
									</div>
									<div id="layerShowDadosCategoriasConta" style="display: none">
									<table width="100%" border="0" bgcolor="#99CCFF">
										<tr bgcolor="#99CCFF">
											<td align="center"><span class="style2"> <a
												href="javascript:extendeTabela('DadosCategoriasConta',false);" /><b>Dados
											das Categorias da Conta</b></a></span></td>
										</tr>
										<tr bgcolor="#cbe5fe">
											<td>
											<table width="100%" align="center" bgcolor="#90c7fc" border="0">
												<tr bordercolor="90c7fc">
													<td bgcolor="90c7fc" align="center"><strong>Categoria </strong></td>
													<td bgcolor="90c7fc" align="center"><strong>Descrição da Categoria </strong></td>
													<td bgcolor="90c7fc" align="center"><strong>Quantidade de Economias </strong></td>
												</tr>
												<%String corCategoria = "#cbe5fe";%>	
												<logic:present name="colecaoContaCategoria" scope="session">
													<logic:iterate name="colecaoContaCategoria"
														id="contaCategoria">
												
													<%if (cor.equalsIgnoreCase("#cbe5fe")) {
															cor = "#FFFFFF";%>
														<tr bgcolor="#FFFFFF">
															<%} else {
															cor = "#cbe5fe";%>
														<tr bgcolor="#cbe5fe">
															<%}%>
															<td align="center"><bean:write name="contaCategoria" property="comp_id.categoria.id"/></td>
															<td align="center"><bean:write name="contaCategoria" property="comp_id.categoria.descricaoAbreviada"/></td>
															<td align="center"><bean:write name="contaCategoria" property="quantidadeEconomia"/></td>
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
				<tr>
					<td align="right">
					<div align="left"><strong><font color="#FF0000">*</font></strong>Campos
					obrigat&oacute;rios</div>
					</td>
				</tr>
				<tr>
					<td colspan="2" width="100%">
					<table width="100%">
						<tr>
							<td>
								<input type="button" name="buttonFiltro" class="bottonRightCol" value="Voltar"
									onClick="javascript:window.location.href='/gsan/exibirResultadoFiltrarContasPreFaturadasAction.do?paginacao=sim&indicadorAtualizar=2'">

								<input name="Submit22" class="bottonRightCol" value="Desfazer"
									type="button" onclick="window.location.href='/gsan/exibirFiltrarContasPreFaturadasAction.do?desfazer=S';">
								<input name="Submit23" class="bottonRightCol" value="Cancelar" type="button" onclick="window.location.href='/gsan/telaPrincipal.do'">
							</td>
							<td align="right"><input name="Submit24" 
								type="button" class="bottonRightCol" value="Atualizar"
								onclick="javascript:validarForm(document.forms[0]);"
								url="atualizarSituacaoContaAction.do?anterior=idAnterior&posterior=idPosterior"/></td>
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
