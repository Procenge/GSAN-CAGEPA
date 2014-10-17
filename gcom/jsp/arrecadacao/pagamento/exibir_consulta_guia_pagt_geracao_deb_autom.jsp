<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

<%@ page import="gcom.util.ConstantesSistema,java.util.Collection"%>
<%@ page import="gcom.gui.faturamento.bean.GuiaPagamentoPrestacaoHelper"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>/validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script>
<!--
function facilitador(objeto){

	if (objeto.id == "0" || objeto.id == undefined){
		objeto.id = "1";
		marcarTodos();
	}
	else{
		objeto.id = "0";
		desmarcarTodos();
	}
}

function verficarSelecaoImprimirGuiaPagamento(objeto){
	 if (CheckboxNaoVazio(objeto)){
		document.forms[0].action = "/gsan/gerarRelatorioEmitirGuiaPagamentoActionDebitoAutomatico.do"
		document.forms[0].submit();
	}
 }

function verficarSelecaoGerarDebitoAutomatico(objeto){

	 if (CheckboxNaoVazio(objeto)){
		document.forms[0].action = "/gsan/gerarDebitoAutomaticoGuiaPagamentoAction.do"
		document.forms[0].submit();
	}
}

function CheckboxNaoVazio(campo){
    form = document.forms[0];
	retorno = false;

	for(indice = 0; indice < form.elements.length; indice++){
		if (form.elements[indice].type == "checkbox" && form.elements[indice].checked == true) {
			retorno = true;
			break;
		}
	}

	if (!retorno){
		alert('Selecione pelo menos uma prestação para efetuar a operação.'); 
	}
	
	return retorno;
}

//-->
</script>
</head>

<body leftmargin="5" topmargin="5" >

<html:form action="/exibirFiltrarGuiaPagtGeracaoDebAutomAction"
	name="ExibirConsultaGuiaPagtGeracaoDebAutomActionForm"
	type="gcom.gui.arrecadacao.pagamento.ExibirConsultaGuiaPagtGeracaoDebAutomActionForm"
	method="post" onsubmit="return CheckboxNaoVazio(document.forms[0].idRegistrosGeracaoDebitoAutomatico)">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="4" cellpadding="0">
		<tr>
			<td width="149" valign="top" class="leftcoltext">
			<div align="center">
			<%@ include file="/jsp/util/informacoes_usuario.jsp"%>
			<%@ include file="/jsp/util/mensagens.jsp"%>
			</div>
			</td>

			<td valign="top" class="centercoltext">

			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Gerar Débito Automático para Guia de Pagamento</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
				<tr>
					<td height="5" colspan="3"></td>
				</tr>
			</table>

			<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="11" height="23"><font color="#000000"
						style="font-size:10px"
						face="Verdana, Arial, Helvetica, sans-serif"> <strong> Guia(s) de Pagamento
					encontrada(s): </strong> </font></td>
				</tr>
				<tr>
					<td colspan="11" bgcolor="#000000" height="2"></td>
				</tr>
				<tr>
					<td colspan="5">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="5">&nbsp;</td>
				</tr>
				<tr>
					<td><strong>Quantidade de Presta&ccedil;&otilde;es de Guias de Pagamento:</strong></td>
					<td width="50%"><html:text property="qtdPrestacaoGuiaPagamento" readonly="true" 
						value="${sessionScope.qtdGuiaPagamentoPrestacao}" style="background-color:#EFEFEF; border:0;" size="5" /></td>
				</tr>
				<tr>
					<td colspan="5">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="5">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="11">
					<table width="100%" bgcolor="#99CCFF">
						<tr bordercolor="#FFFFFF" bgcolor="#99CCFF">
							<td class="style1" align="center"><strong> <font
								color="#000000" style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <a
								onclick="this.focus();" id="0"
								href="javascript:facilitador(this);">Todos</a> </font> </strong>
							</td>

							<td bordercolor="#000000" bgcolor="#90c7fc">
							<div align="center"><font color="#000000" style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Guia Pagamento</strong>
							</font></div>
							</td>

							<td bordercolor="#000000" bgcolor="#90c7fc">
							<div align="center"><font color="#000000" style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Presta&ccedil;&atilde;o</strong>
							</font></div>
							</td>

							<td bordercolor="#000000" bgcolor="#90c7fc">
							<div align="center"><font color="#000000" style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor</strong> </font></div>
							</td>

							<td bordercolor="#000000" bgcolor="#90c7fc">
							<div align="center"><font color="#000000" style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Vencimento</strong> </font></div>
							</td>

							<td bordercolor="#000000" bgcolor="#90c7fc">
							<div align="center"><font color="#000000" style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Im&oacute;vel</strong> </font></div>
							</td>

							<td bordercolor="#000000" bgcolor="#90c7fc">
							<div align="center"><font color="#000000" style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Respons&aacute;vel</strong> </font></div>
							</td>
						</tr>
											
						
						<%--Esquema de paginação--%>
						<pg:pager isOffset="true" index="half-full" maxIndexPages="10"
							export="currentPageNumber=pageNumber;pageOffset"
							maxPageItems="10" items="${sessionScope.totalRegistros}">						
							<pg:param name="q" />
							<logic:present name="colecaoGuiaPagamentoPrestacao">
								<%int cont = 0;%>
								
							    <logic:iterate name="colecaoGuiaPagamentoPrestacao" id="guiaPagamentoPrestacaoHelper" 
									type="GuiaPagamentoPrestacaoHelper">
									<pg:item>
										<%cont = cont + 1;
										if (cont % 2 == 0) {%>
										<tr bgcolor="#cbe5fe">
										<%} else {%>			
										<tr bgcolor="#FFFFFF">
										<%}%>
										<td>
												<div align="center">
													<input type="checkbox" name="idRegistrosGeracaoDebitoAutomatico"
													value="<bean:write name="guiaPagamentoPrestacaoHelper" property="idGuiaComNumeroPrestacao"/>" />
												</div>
											</td>
											<td align="center">
												<font color="#000000" style="font-size:9px"	face="Verdana, Arial, Helvetica, sans-serif"> 
													<a href="javascript:abrirPopup('exibirConsultarGuiaPagamentoAction.do?guiaPagamentoId=<bean:define name="guiaPagamentoPrestacaoHelper" property="idGuiaPagamento" id="idGuiaPagamento" /><bean:write name="guiaPagamentoPrestacaoHelper" property="idGuiaPagamento" />', 500, 600);">
													<bean:write	name="guiaPagamentoPrestacaoHelper" property="idGuiaPagamento"/>
												 </font>
											</td>
											<td align="center">
												<div align="center" title="${guiaPagamentoPrestacaoHelper.descricaoTipoDebito}">
													<font color="#000000" style="font-size:9px"	face="Verdana, Arial, Helvetica, sans-serif"> 
														<bean:write	name="guiaPagamentoPrestacaoHelper" property="numeroPrestacao" /> / <bean:write	name="guiaPagamentoPrestacaoHelper" property="numeroPrestacaoTotal" />
													 </font>
												 </div>
											</td>
											<td>
												<div align="right">
												  <logic:present name="guiaPagamentoPrestacaoHelper" property="valorTotalPorPrestacao">
												    <bean:write name="guiaPagamentoPrestacaoHelper" property="valorTotalPorPrestacao" formatKey="money.format"/>
												  </logic:present> 
												  <logic:notPresent name="guiaPagamentoPrestacaoHelper" property="valorTotalPorPrestacao">
													&nbsp;
								  				  </logic:notPresent> 
												</div>
    										</td>
											<td>
												<div align="center">
												  <logic:present name="guiaPagamentoPrestacaoHelper" property="dataVencimento">
												    <bean:write name="guiaPagamentoPrestacaoHelper" property="dataVencimento" formatKey="date.format"/>
												  </logic:present> 
												  <logic:notPresent name="guiaPagamentoPrestacaoHelper" property="dataVencimento">
													&nbsp;
								  				  </logic:notPresent>	   
												</div>
											 </td>
											<td>
												<div align="center">
													<logic:notPresent name="guiaPagamentoPrestacaoHelper" property="idImovel">
														<font color="#000000" style="font-size:9px"	face="Verdana, Arial, Helvetica, sans-serif"> &nbsp; </font>
													</logic:notPresent>
													<logic:present name="guiaPagamentoPrestacaoHelper" property="idImovel">
														<font color="#000000" style="font-size:9px"	
															face="Verdana, Arial, Helvetica, sans-serif">
															<bean:write	name="guiaPagamentoPrestacaoHelper" property="idImovel" />
														 </font>
													</logic:present>
												</div>
											</td>
											<td>
												<div align="center">
													<logic:notPresent name="guiaPagamentoPrestacaoHelper" property="idCliente">
														<font color="#000000" style="font-size:9px"	face="Verdana, Arial, Helvetica, sans-serif"> &nbsp; </font>
													</logic:notPresent>
													<logic:present name="guiaPagamentoPrestacaoHelper" property="idCliente">
														<div align="center" title="${guiaPagamentoPrestacaoHelper.nomeCliente}">
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
																<bean:write	name="guiaPagamentoPrestacaoHelper" property="idCliente" />
															</font>
														</div>
													</logic:present>
												</div>
											</td>
										</tr>
								</pg:item>
								</logic:iterate>
							</logic:present>
					
					<table width="100%" border="0">
						<tr>
							<td>
							<div align="center"><strong><%@ include
								file="/jsp/util/indice_pager_novo.jsp"%></strong></div>
							</td>
							</pg:pager>
							<%-- Fim do esquema de paginação --%>

						</tr>
					</table>
					
						
					</td>
				</tr>
			</table>
			
			
				<table width="100%" border="0">
						<tr>
							<td colspan="5">&nbsp;</td>
						</tr>
						<tr>
							<td>
								<input name="button" type="button" class="bottonRightCol"
									value="Voltar Filtro"
									onclick="window.location.href='<html:rewrite page="/exibirFiltrarGuiaPagtGeracaoDebAutomAction.do"/>'"
									align="left" style="width: 80px;">
							</td>
							<td align="center">
								<input type="button" name="gerarDebito" value="Gerar Débito Automático"  class="bottonRightCol"
								   onclick="verficarSelecaoGerarDebitoAutomatico(document.forms[0].idRegistrosGeracaoDebitoAutomatico)"/>
							</td>
							<td align="right">
								<input type="button" name="imprimirGuiaPagamento" value="Imprimir Guia(s) de Pagamento" class="bottonRightCol" 
									onclick="verficarSelecaoImprimirGuiaPagamento(document.forms[0].idRegistrosGeracaoDebitoAutomatico)"/>
							</td>
						</tr>
					</table>
			
			
			
			
			
			</td>
		</tr>
		<!-- Rodapé -->
		<%@ include file="/jsp/util/rodape.jsp"%>
	</table>
</html:form>
</body>
</html:html>
