<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="java.util.Collection,java.util.Iterator,java.util.Map" %>
<%@ page import="java.math.BigDecimal,gcom.arrecadacao.ArrecadacaoDadosDiarios,gcom.util.Util" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--

function fechar(){
		window.close();
-->		
}
</SCRIPT>
</head>
<body leftmargin="5" topmargin="5">
<html:form action="/exibirConsultarDadosDiariosEloAction.do"
	name="FiltrarDadosDiariosArrecadacaoActionForm"
	type="gcom.gui.arrecadacao.FiltrarDadosDiariosArrecadacaoActionForm"
	method="post">
	<table width="570" border="0" cellpadding="0" cellspacing="5">
		<tr>
			<td width="560" valign="top" class="centercoltext">
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
					<td class="parabg">Consultar Dados Diários da Arrecadação - ELO</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td height="10">
					<table width="100%" border="0">
						<%
						BigDecimal valorTotal = (BigDecimal)session.getAttribute("valorTotalUnidadeNegocio");
						String referencia = (String)session.getAttribute("referencia");
						String idUnidadeNegocio = (String)session.getAttribute("idUnidadeNegocio");
						String nomeUnidadeNegocio = (String)request.getAttribute("nomeUnidadeNegocio");

						String idGerencia = (String)session.getAttribute("idGerencia");
						String nomeGerencia = (String)request.getAttribute("nomeGerencia");
						BigDecimal valorTotalGerencia = (BigDecimal)session.getAttribute("valorTotalGerencia");	
						Integer anoMesArrecadacao = (Integer) session.getAttribute("anoMesArrecadacao");
						
						%>
						<tr bgcolor="#90c7fc">
							<td colspan="4">
								<div align="center"><strong>M&ecirc;s/Ano:</strong><strong>
								<%= Util.formatarAnoMesParaMesAno(referencia) %>
								- <%=(Integer.parseInt(referencia) < anoMesArrecadacao?"DEFINITIVO":"PARCIAL") %></strong></div>
							</td>
						</tr>
						<tr>
			                <td width="15%"><strong>Gerência:</strong></td>
			                <td width="35%" align="left">
			                	 <strong> <%= nomeGerencia %></strong>
			                </td>
			                <td width="30%" align="right">
								<strong>Valor:</strong>
							</td>
							<td align="right" width="20%">
								<a
								href="javascript:abrirPopup('exibirConsultarDadosDiariosValoresDiariosAction.do?referencia=<%= referencia%>&idGerencia=<%= idGerencia%>', 475, 600);">
							<strong><%= Util.formatarMoedaReal(valorTotalGerencia) %></strong> </a>
							</td>
						</tr>

						<tr>
			                <td width="25%"><strong>Unidade Negócio:</strong></td>
			                <td width="30%" align="left">
			                	 <strong> <%= nomeUnidadeNegocio %></strong>
			                </td>
			                <td width="25%" align="right">
								<strong>Valor:</strong>
							</td>
							<td align="right" width="20%">
								<a
								href="javascript:abrirPopup('exibirConsultarDadosDiariosValoresDiariosAction.do?referencia=<%= referencia%>&idUnidadeNegocio=<%= idUnidadeNegocio%>', 475, 600);">
							<strong><%= Util.formatarMoedaReal(valorTotal) %></strong> </a>
							</td>
						</tr>
					</table>
					<table width="100%" bgcolor="#99CCFF" dwcopytype="CopyTableRow">
						<!--header da tabela interna -->
						<tr bordercolor="#FFFFFF" bgcolor="#90c7fc">
							<td width="46%">
							<div align="center" class="style9"><strong>ELO</strong></div>
							</td>
							<td width="27%">
							<div align="center" class="style9"><strong>Valor</strong></div>

							</td>
							<td width="27%">
							<div align="center"><strong>Percentual</strong></div>
							</td>
						</tr>
						<%String cor = "#cbe5fe"; %>
						<logic:present name="colecaoDadosDiarios">
						<logic:iterate name="colecaoDadosDiarios" id="arrecadacaoDadosDiarios">
						<% if (cor.equalsIgnoreCase("#cbe5fe")) {
									cor = "#FFFFFF";%>
								<tr bgcolor="#FFFFFF">
								<%} else {
									cor = "#cbe5fe";%>
								<tr bgcolor="#cbe5fe">
								<%}%>
							<td height="17">
							      <div align="left">
							        <a href="javascript:abrirPopup('exibirConsultarDadosDiariosLocalidadeAction.do?referencia=<bean:write name="arrecadacaoDadosDiarios" property="anoMesReferenciaArrecadacao" />&idGerencia=<bean:write name="arrecadacaoDadosDiarios" property="gerenciaRegional.id" />&idEloPopup=<bean:write name="arrecadacaoDadosDiarios" property="localidade.localidade.id" />&idUnidadeNegocio=<bean:write name="arrecadacaoDadosDiarios" property="unidadeNegocio.id" />', 475, 600);">
								        <bean:write name="arrecadacaoDadosDiarios" property="localidade.localidade.descricao" />
							        </a>
								  </div>
							</td>
							<td>
								<logic:present name="arrecadacaoDadosDiarios" property="valorPagamentos">
							      <div align="right">
							      	<a href="javascript:abrirPopup('exibirConsultarDadosDiariosValoresDiariosAction.do?referencia=<bean:write name="arrecadacaoDadosDiarios" property="anoMesReferenciaArrecadacao" />&idGerencia=<bean:write name="arrecadacaoDadosDiarios" property="gerenciaRegional.id" />&idEloPopup=<bean:write name="arrecadacaoDadosDiarios" property="localidade.localidade.id" />', 475, 600);">
							        	<bean:write name="arrecadacaoDadosDiarios" property="valorPagamentos" formatKey="money.format"/>
							        </a>
								  </div>
								</logic:present>
							</td>
							<% 
								BigDecimal percentualMultiplicacao = ((ArrecadacaoDadosDiarios)arrecadacaoDadosDiarios).getValorPagamentos().multiply(new BigDecimal("100.00"));

								BigDecimal percentual2 = percentualMultiplicacao.divide(
										valorTotal,2,BigDecimal.ROUND_HALF_UP);
							%>								
							<td>
								<div align="right"><%= Util.formatarMoedaReal(percentual2) %> %</div>
							</td>
						</tr>
					</logic:iterate>
					</logic:present>
					</table>
					<p></p>

					<p>&nbsp;</p>
					</td>
				</tr>
			</table>
			<table border="0" width="100%">
				<tr>
					<td colspan="3">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="3" align="right"><input name="Button" type="button"
						class="bottonRightCol" value="Fechar"
						onClick="javascript:fechar();"></td>
				</tr>
				<p>&nbsp;</p>
			</table>
			</td>
		</tr>
	</table>
</html:form>
<body>
</html:html>
