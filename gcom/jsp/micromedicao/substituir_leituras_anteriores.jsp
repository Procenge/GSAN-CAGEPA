<%@page import="gcom.util.ConstantesSistema"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="gcom.micromedicao.medicao.MedicaoTipo"%>

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js" ></script>

<html:javascript staticJavascript="false" formName="SubstituirLeituraActionForm" dynamicJavascript="false"/>

<script language="JavaScript">
	function validarCamposDinamicos(form) {
		var retorno = true;

		for (x=0; x < form.elements.length; x++) {
			if (form.elements[x].type == "text" && form.elements[x].id.length > 1) {
				if (form.elements[x].id == "valor") {
					if (form.elements[x].value == "") {
						alert('Informe Leitura.');
						form.elements[x].focus();
						retorno = false;
						break;
					} else if (!validaCampoInteiro(form.elements[x])) {
						alert('O valor do campo deve somente conter número positivos.');
						form.elements[x].focus();
						retorno = false;
						break;
					}
				}
			}
		}

		return retorno;
	}

	function limparPesquisaImovel() {
		var form = document.forms[0];
		window.location.search='?peloMenu=true&menu=sim';
		window.location.replace;
	}

	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
		var form = document.SubstituirLeituraActionForm;

		if (tipoConsulta == 'imovel') {
			form.idImovelSubstituirLeitura.value = codigoRegistro;
			form.action = "/gsan/exibirSubstituirLeituraAnteriorAction.do?tipoConsulta=1";
			form.onsubmit = "";
			form.submit();
		}
	}

	function validaEnterImovel(tecla, caminhoActionReload, nomeCampo,mensagem) {
		var form = document.forms[0];
		var codigo;

		if (document.all) {
			codigo = tecla.keyCode;
		} else {
			codigo = tecla.which;
		}

		if (codigo == 13) {
			if (!validaEnterComMensagem(tecla, caminhoActionReload, nomeCampo, mensagem)) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}
</script>

</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');">

<html:form 
	action="/substituirLeiturasAnterioresAction" 
	name="SubstituirLeituraActionForm"
	type="gcom.gui.micromedicao.SubstituirLeituraActionForm"
	method="post"
	onsubmit="return validarCamposDinamicos(this);"
>

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

<table width="770" border="0" cellspacing="4" cellpadding="0">
	<tr>
		<td width="149" valign="top" class="leftcoltext">
			<div align="center">
				<%@ include file="/jsp/util/informacoes_usuario.jsp"%>

				<%@ include file="/jsp/util/mensagens.jsp"%>
			</div>
		</td>

		<td width="625" valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td>
					</td>
				</tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
					<td class="parabg">Substituir Leituras Anteriores</td>
					<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0" >
				<logic:present name="peloMenu">
					<tr>
						<td colspan="2">
							<table width="100%">
								<tr>
									<td width="12%">
										<strong>Imóvel:<font color="#FF0000">*</font></strong>
									</td>
									<td>
										<html:text maxlength="9" property="idImovelSubstituirLeitura" size="10"
											onkeypress="return validaEnterImovel(event, 'exibirSubstituirLeituraAnteriorAction.do?tipoConsulta=1', 'idImovelSubstituirLeitura','Imóvel');" />
										<a href="javascript:abrirPopup('exibirPesquisarImovelAction.do', 400, 800);">
											<img width="23" height="21" border="0"
												src="<bean:message key="caminho.imagens"/>pesquisa.gif"
												title="Pesquisar Imóvel" /></a><html:text property="inscricaoImovel" readonly="true" style="background-color:#EFEFEF; border:0; color: ${corTexto}" size="30"/><a
												href="javascript:limparPesquisaImovel();"><img
												src="<bean:message key="caminho.imagens"/>limparcampo.gif"
												border="0" title="Apagar Imóvel" />
										</a>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</logic:present>
   			  
				<logic:notPresent name="peloMenu">
					<tr>
						<td></td>
						<td></td>
					</tr>
				</logic:notPresent>

				<tr>
					<td colspan="2">
						<table width="100%">
							<tr>
								<td width="183"><strong>Endere&ccedil;o(s) do Imóvel</strong></td>
								<td width="432" align="right"></td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td colspan="2">
						<table width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td>
									<table width="100%" bgcolor="#99CCFF">
									<!--header da tabela interna -->
										<tr>
											<td bgcolor="#99CCFF" align="center"><strong>Endere&ccedil;o</strong></td>
										</tr>
									</table>
								</td>
							</tr>

							<tr>
								<td height="40">
									<div style="width: 100%; height: 100%; overflow: auto;">
										<table width="100%" align="center" bgcolor="#99CCFF">
										<!--corpo da segunda tabela-->
											<logic:present name="enderecoImovel">
												<% String cor = "#FFFFFF";%>
												<% if (cor.equalsIgnoreCase("#FFFFFF")){
												cor = "#cbe5fe";%>
												<tr bgcolor="#FFFFFF">
												<%} else{
												cor = "#FFFFFF";%>
												<tr bgcolor="#cbe5fe">
												<%}%>
													<td>
							                            <div align="center">${requestScope.enderecoImovel} &nbsp;</div>
													</td>
												</tr>
											</logic:present>
										</table>
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td colspan="2">
						<table width="100%" bgcolor="#99CCFF" >
							<tr bordercolor="#79bbfd"> 
								<td colspan="6" bgcolor="#79bbfd" height="20"><div align="center"><strong>Últimas Leituras</strong></div></td>
							</tr>
	
							<tr bordercolor="#99CCFF"> 
								<td width="16%" bgcolor="#90c7fc" align="center"><div align="center"><strong>Referência</strong></div></td>
								<td width="16%" bgcolor="#90c7fc" align="center"><div align="center"><strong>Tipo</strong></div></td>
								<td width="16%" bgcolor="#90c7fc" align="center"><div align="center"><strong>Leitura Atual</strong></div></td>
								<td width="16%" bgcolor="#90c7fc" align="center"><div align="center"><strong>Consumo Médio</strong></div></td>
								<td width="16%" bgcolor="#90c7fc" align="center"><div align="center"><strong>Situação</strong></div></td>
								<td width="20%" bgcolor="#90c7fc" align="center"><div align="center"><strong>Anormalidade</strong></div></td>
							</tr>

							<logic:present name="colecaoMedicaoHistorico">	        
								<tr bordercolor="#99CCFF"> 
									<td colspan="7" width="100%">
										<div style="width: 100%; height: 100%; overflow: auto;">
											<table width="100%" align="center" bgcolor="#99CCFF">
												<!--corpo da segunda tabela-->
												<%int cont=0;%>
												<logic:notEmpty name="colecaoMedicaoHistorico">
													<logic:iterate name="colecaoMedicaoHistorico" id="medicaoHistorico" scope="session">
														<%
														cont = cont+1;
														if (cont%2==0){%>
														<tr bgcolor="#cbe5fe">
														<%}else{ %>
														<tr bgcolor="#FFFFFF">
														<%}%>
															<td width="16%">
																<div align="center">
																	${medicaoHistorico.mesAno} &nbsp;
																</div>
															</td>
															<td width="16%">
																<div align="center">
																	<logic:notEmpty name="medicaoHistorico" property="medicaoTipo.id">
																		${medicaoHistorico.medicaoTipo.descricao}
																	</logic:notEmpty>
																</div>
															</td>
															<td width="16%">
																<div align="center"> 
																	<logic:equal name="medicaoHistorico" property="medicaoTipo.id" value="<%="" + MedicaoTipo.LIGACAO_AGUA%>">
																		<logic:notEmpty name="medicaoHistorico" property="id">
																			<input name="leituraAgua${medicaoHistorico.id}" type="text" size="7" maxlength="7"
																				value="${medicaoHistorico.leituraAtualFaturamento}" id="valor">
																		</logic:notEmpty>
																	</logic:equal>

																	<logic:equal name="medicaoHistorico" property="medicaoTipo.id" value="<%="" + MedicaoTipo.POCO%>">
																		<logic:notEmpty name="medicaoHistorico" property="id"> 
																			<input name="leituraPoco${medicaoHistorico.id}" type="text" size="7" maxlength="7"
																				value="${medicaoHistorico.leituraAtualFaturamento}" id="valor">
																		</logic:notEmpty>		                  	  
																	</logic:equal>
																</div>
															</td>
															<td width="16%">
																<div align="center"> 
																	<logic:equal name="medicaoHistorico" property="medicaoTipo.id" value="<%="" + MedicaoTipo.LIGACAO_AGUA%>">
																			<logic:equal name="medicaoHistorico" property="permiteAlterarConsumoMedio" value="<%="" + ConstantesSistema.SIM%>"> 
																				<logic:notEmpty name="medicaoHistorico" property="consumoMedioAux">
																					<input name="mediaAgua${medicaoHistorico.id}" type="text" size="7" maxlength="7"
																						value="${medicaoHistorico.consumoMedioAux}" id="valor">
																				</logic:notEmpty>																			</logic:equal>

																			<logic:equal name="medicaoHistorico" property="permiteAlterarConsumoMedio" value="<%="" + ConstantesSistema.NAO%>"> 
																				<logic:notEmpty name="medicaoHistorico" property="consumoMedioAux">
																					<input name="mediaAgua${medicaoHistorico.id}" type="text" size="7" maxlength="7"
																						value="${medicaoHistorico.consumoMedioAux}" id="valor" disabled="disabled">
																				</logic:notEmpty>
																			</logic:equal>
																	</logic:equal>

																	<logic:equal name="medicaoHistorico" property="medicaoTipo.id" value="<%="" + MedicaoTipo.POCO%>">
																		<logic:notEmpty name="medicaoHistorico" property="id">
																			<logic:equal name="medicaoHistorico" property="permiteAlterarConsumoMedio" value="<%="" + ConstantesSistema.SIM%>"> 
																				<logic:notEmpty name="medicaoHistorico" property="consumoMedioAux">
																					<input name="mediaPoco${medicaoHistorico.id}" type="text" size="7" maxlength="7"
																						value="${medicaoHistorico.consumoMedioAux}" id="valor">
																				</logic:notEmpty>																			</logic:equal>

																			<logic:equal name="medicaoHistorico" property="permiteAlterarConsumoMedio" value="<%="" + ConstantesSistema.NAO%>"> 
																				<logic:notEmpty name="medicaoHistorico" property="consumoMedioAux">
																					<input name="mediaPoco${medicaoHistorico.id}" type="text" size="7" maxlength="7"
																						value="${medicaoHistorico.consumoMedioAux}" id="valor" disabled="disabled">
																				</logic:notEmpty>
																			</logic:equal>
																		</logic:notEmpty>		                  	  
																	</logic:equal>
																</div>
															</td>
															<td width="16%">
																<div align="center">
																	<logic:notEmpty name="medicaoHistorico" property="leituraSituacaoAtual">
																		${medicaoHistorico.leituraSituacaoAtual.descricao}
																	</logic:notEmpty>
																</div>
															</td>
															<td width="20%">
																<div align="center">
																	<logic:notEmpty name="medicaoHistorico" property="leituraAnormalidadeFaturamento">
																		${medicaoHistorico.leituraAnormalidadeFaturamento.descricaoAbreviada}
																	</logic:notEmpty>
																</div>
															</td>
														</tr>
													</logic:iterate>
												</logic:notEmpty>
											</table>
										</div>
									</td>
								</tr>
							</logic:present>
						</table>
					</td>
				</tr>
	
				<tr>
					<td colspan="2">
						<table width="100%">
							<tr>
								<td align="left">
									<logic:notPresent name="peloMenu">
										<input type="button" value="Voltar" class="bottonRightCol" onclick="javascript:history.back();"/>
									</logic:notPresent>
									<input type="button" value="Desfazer" onclick="document.forms[0].reset();" class="bottonRightCol"/>
									<input name="Button" type="button" class="bottonRightCol" value="Cancelar" onclick="javascript:window.location.href='/gsan/telaPrincipal.do'">	
								</td>
								<td align="right" colspan="2">
									<input type="submit" value="Substituir" class="bottonRightCol"/>
								</td>
							</tr>
						</table>               		
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<%@ include file="/jsp/util/rodape.jsp"%>
<%@ include file="/jsp/util/tooltip.jsp" %>

</html:form>
</body>
</html:html>
