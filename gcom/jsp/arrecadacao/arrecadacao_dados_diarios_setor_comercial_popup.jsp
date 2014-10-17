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
<html:form action="/exibirConsultarDadosDiariosLocalidadeAction.do"
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
					<td class="parabg">Consultar Dados Di�rios da Arrecada��o - Setor Comercial</td>
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
						BigDecimal valorTotalElo = (BigDecimal)session.getAttribute("valorTotalElo");
						String referencia = (String)session.getAttribute("referencia");
						String nomeGerencia = (String)request.getAttribute("nomeGerencia");
						String nomeElo = (String)request.getAttribute("nomeElo");
						String idGerencia = (String)session.getAttribute("idGerencia");
						String idEloPopup = (String)request.getAttribute("idEloPopup");
						String nomeLocalidade = (String) request.getAttribute("nomeLocalidade");
						
						BigDecimal valorTotalGerencia = (BigDecimal)session.getAttribute("valorTotalGerencia");						
						
						
						String idUnidadeNegocio = (String)session.getAttribute("idUnidadeNegocio");
						String nomeUnidadeNegocio = (String)request.getAttribute("nomeUnidadeNegocio");
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
			                <td width="15%"><strong>Ger�ncia:</strong></td>
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
			                <td width="25%"><strong>Unidade Neg�cio:</strong></td>
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
						<tr>
			                <td width="15%"><strong>Elo:</strong></td>
			                <td width="35%" align="left"><strong> <%= nomeElo %></strong></td>
			                <td width="30%" align="right">
								<strong>Valor:</strong>
							</td>
							<td align="right" width="20%">
								<a href="javascript:abrirPopup('exibirConsultarDadosDiariosValoresDiariosAction.do?referencia=<%= referencia%>&idGerencia=<%= idGerencia%>&idEloPopup=<%= idEloPopup%>', 475, 600);">
								<strong><%= Util.formatarMoedaReal(valorTotalElo) %></strong>
								</a>
							</td>
						</tr>
						<tr>
			                <td width="15%"><strong>Localidade:</strong></td>
			                <td width="35%" align="left"><strong> <%= nomeLocalidade %></strong></td>
			                <td width="30%" align="right">
								<strong>Valor:</strong>
							</td>
							<td align="right" width="20%">
								<a href="javascript:abrirPopup('exibirConsultarDadosDiariosValoresDiariosAction.do?referencia=<%= referencia%>&idGerencia=<%= idGerencia%>&idEloPopup=<%= idEloPopup%>', 475, 600);">
								<strong><%= Util.formatarMoedaReal(valorTotalElo) %></strong>
								</a>
							</td>
						</tr>
					</table>
					<table width="100%" bgcolor="#99CCFF" dwcopytype="CopyTableRow">
						<!--header da tabela interna -->
						<tr bordercolor="#FFFFFF" bgcolor="#90c7fc">
							<td width="27%">
							<div align="center" class="style9"><strong>Setor Comercial</strong></div>
							</td>
							<td width="20%">
							<div align="center" class="style9"><strong>Valor</strong></div>

							</td>
							<td width="20%">
							<div align="center"><strong>Percentual Elo</strong></div>
							</td>
							<td width="23%">
							<div align="center"><strong>Percentual Ger�ncia</strong></div>
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
								<logic:present name="arrecadacaoDadosDiarios" property="setorComercial">
							      <div align="left">
							        <bean:write name="arrecadacaoDadosDiarios" property="setorComercial.descricao" />
								  </div>
								</logic:present>
							</td>
							<td>
								<logic:present name="arrecadacaoDadosDiarios" property="valorPagamentos">
							      <div align="right">
							      	<a href="javascript:abrirPopup('exibirConsultarDadosDiariosValoresDiariosAction.do?mostraUnidadeGerencia=1&referencia=<bean:write name="arrecadacaoDadosDiarios" property="anoMesReferenciaArrecadacao" />&idGerencia=<bean:write name="arrecadacaoDadosDiarios" property="gerenciaRegional.id" />&idEloPopup=<bean:write name="arrecadacaoDadosDiarios" property="localidade.localidade.id" />&idLocalidade=<bean:write name="arrecadacaoDadosDiarios" property="localidade.id" />&idSetorComercial=<bean:write name="arrecadacaoDadosDiarios" property="setorComercial.id" />', 475, 600);">
							        	<bean:write name="arrecadacaoDadosDiarios" property="valorPagamentos" formatKey="money.format"/>
							        </a>	
								  </div>
								</logic:present>
							</td>
							<% 
								BigDecimal percentualMultiplicacao = ((ArrecadacaoDadosDiarios)arrecadacaoDadosDiarios).getValorPagamentos().multiply(new BigDecimal("100.00"));

								BigDecimal percentual = percentualMultiplicacao.divide(
										valorTotal,2,BigDecimal.ROUND_HALF_UP);

								BigDecimal percentualMultiplicacaoElo = ((ArrecadacaoDadosDiarios)arrecadacaoDadosDiarios).getValorPagamentos().multiply(new BigDecimal("100.00"));

								BigDecimal percentualElo = percentualMultiplicacaoElo.divide(
										valorTotalElo,2,BigDecimal.ROUND_HALF_UP);

							%>								
							<td>
							<div align="right"><%= Util.formatarMoedaReal(percentualElo) %> %</div>
							</td>
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
