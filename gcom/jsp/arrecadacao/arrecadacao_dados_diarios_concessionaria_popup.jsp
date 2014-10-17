<%@page import="java.util.Collection"%>
<%@page import="gcom.arrecadacao.bean.ArrecadacaoDadosDiariosHelper"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
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
<html:form action="/exibirConsultarDadosDiariosAgenteArrecadadorAction.do"
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
					<td class="parabg">Consultar Dados Diários da Arrecadação - Concessionária</td>
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
						BigDecimal valorTotal = (BigDecimal)session.getAttribute("valorTotal");
						String referencia = (String)session.getAttribute("referencia");
						Collection<ArrecadacaoDadosDiariosHelper> colecaoDadosDiariosHelper = (Collection<ArrecadacaoDadosDiariosHelper>) session.getAttribute("colecaoDadosDiarios");
						Integer anoMesArrecadacao = (Integer) session.getAttribute("anoMesArrecadacao");
						%>
						<tr bgcolor="#90c7fc">
							<td colspan="4">
								<div align="center"><strong>M&ecirc;s/Ano:</strong><strong>
								<%= Util.formatarAnoMesParaMesAno(referencia) %>
								- <%=(Integer.parseInt(referencia) < anoMesArrecadacao?"DEFINITIVO":"PARCIAL") %></strong></div>
							</td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td colspan="1" align="left" width="73%">
								<strong><%= colecaoDadosDiariosHelper.iterator().next().getConcessionaria().getNome()%></strong>
							</td>
							<td colspan="2" align="right" width="10%">
								<strong>Valor:</strong>
							</td>
							<td colspan="1" align="right" width="17%">
								<strong>
								<!--<a href="javascript:abrirPopup('exibirConsultarDadosDiariosValoresDiariosAction.do?referencia=<%= referencia%>', 475, 600);"> -->
										<%= Util.formatarMoedaReal(valorTotal) %>
								<!--</a> -->
								</strong>
							</td>
						</tr>
					</table>
					<table width="100%" bgcolor="#99CCFF" dwcopytype="CopyTableRow">
						<!--header da tabela interna -->
						<tr bordercolor="#FFFFFF" bgcolor="#90c7fc">
							<td>
							<div align="center" class="style9"><strong>Arrecadador</strong></div>
							</td>
							<td width="15%">
							<div align="center" class="style9"><strong>Valor</strong></div>

							</td>
							
							<td>
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
								<logic:present name="arrecadacaoDadosDiarios" property="arrecadador.cliente.nome">
							      <div align="left">
							        <bean:write name="arrecadacaoDadosDiarios" property="arrecadador.cliente.nome" />
								  </div>
								</logic:present>
							</td>
							<td>
								<logic:present name="arrecadacaoDadosDiarios" property="valorPagamentos">
							      <div align="right">
							      	<a href="javascript:abrirPopup('exibirConsultarDadosDiariosValoresDiariosAction.do?referencia=<bean:write name="arrecadacaoDadosDiarios" property="anoMesReferenciaArrecadacao" />&idArrecadadorPopup=<bean:write name="arrecadacaoDadosDiarios" property="arrecadador.id" />&idConcessionaria=<bean:write name="arrecadacaoDadosDiarios" property="concessionaria.id" />', 475, 600);">
							        	<bean:write name="arrecadacaoDadosDiarios" property="valorPagamentos" formatKey="money.format"/>
							        </a>	
								  </div>
								</logic:present>
							</td>							
							
							<% 
								BigDecimal percentualMultiplicacao = ((ArrecadacaoDadosDiariosHelper)arrecadacaoDadosDiarios).getValorPagamentos().multiply(new BigDecimal("100.00"));

								BigDecimal percentual = percentualMultiplicacao.divide(
										valorTotal,2,BigDecimal.ROUND_HALF_UP);
							%>								
							<td>
							<div align="right"><%= Util.formatarMoedaReal(percentual) %> %</div>
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
