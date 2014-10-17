<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="java.math.BigDecimal,gcom.arrecadacao.ArrecadacaoDadosDiarios,gcom.util.Util,java.util.Date,java.util.Calendar,java.util.GregorianCalendar" %>
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
<html:form action="/exibirConsultarDadosDiariosValoresDiariosAction.do"
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
					<td class="parabg">Consultar Dados Diários da Arrecadação - Valores
					Diários</td>
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
						String nomeGerencia = (String)request.getAttribute("nomeGerencia");
						String descricaoLocalidade = (String)request.getAttribute("descricaoLocalidade");
						String descricaoElo = (String)request.getAttribute("descricaoElo");
						String nomeAgente = (String)request.getAttribute("nomeAgente");
						String nomeCategoria = (String)request.getAttribute("nomeCategoria");
						String nomePerfil = (String)request.getAttribute("nomePerfil");
						String nomeDocumento = (String)request.getAttribute("nomeDocumento");
						
						String nomeUnidadeNegocio = (String)session.getAttribute("nomeUnidadeNegocio");
						String mostraUnidadeGerencia = (String)request.getAttribute("mostraUnidadeGerencia");
						Integer anoMesArrecadacao = (Integer) session.getAttribute("anoMesArrecadacao");
						
						%>
					<tr bgcolor="#90c7fc">
						<td colspan="2">
							<div align="center"><strong>M&ecirc;s/Ano:</strong><strong>
							<%= Util.formatarAnoMesParaMesAno(referencia) %>
							- <%=(Integer.parseInt(referencia) < anoMesArrecadacao?"DEFINITIVO":"PARCIAL") %></strong></div>
						</td>
					</tr>
						<% 
						if (nomeGerencia != null)
						{
						%>
							<tr>
				                <td width="15%"><strong>Ger&ecirc;ncia:</strong></td>
				                <td width="85%" align="left"><strong> <%= nomeGerencia %></strong></td>
			                </tr>
						<%
						}
						if (nomeUnidadeNegocio != null && mostraUnidadeGerencia != null)
						{
						%>
							<tr>
					            <td width="25%"><strong>Unidade Negócio:</strong></td>
					            <td width="75%" align="left"><strong> <%= nomeUnidadeNegocio %></strong></td>
				            </tr>
						<%
						}
						
						
						if (descricaoElo != null)
						{
						%>
							<tr>
					            <td width="15%"><strong>Elo:</strong></td>
					            <td width="85%" align="left"><strong> <%= descricaoElo %></strong></td>
				            </tr>
						<%
						}
						if (descricaoLocalidade != null)
						{
						%>
						   <tr>
					          <td width="15%"><strong>Localidade:</strong></td>
					          <td width="85%" align="left"><strong> <%= descricaoLocalidade %></strong></td>
				           </tr>
						<%
						}
						if (nomeAgente != null)
						{
						%>
							<tr>
						        <td width="15%"><strong>Banco:</strong></td>
						    	<td width="85%" align="left"><strong> <%= nomeAgente %></strong></td>
					    	</tr>
						<%
						}
						if (nomeCategoria != null)
						{
						%>
							<tr>
						        <td width="15%"><strong>Categoria:</strong></td>
						    	<td width="85%" align="left"><strong> <%= nomeCategoria %></strong></td>
					    	</tr>
						<%
						}
						if (nomePerfil != null)
						{
						%>
							<tr>
						        <td width="15%"><strong>Perfil:</strong></td>
						    	<td width="85%" align="left"><strong> <%= nomePerfil %></strong></td>
					    	</tr>
						<%
						}
						if (nomeDocumento != null)
						{
						%>
							<tr>
						        <td width="25%"><strong>Tipo do Documento:</strong></td>
						    	<td width="75%" align="left"><strong> <%= nomeDocumento %></strong></td>
					    	</tr>
						<%
						}
						%>
					</table>
					<table width="100%" border="0">
						<tr bgcolor="#cbe5fe">
							<td width="82%" align="right">
							<strong>Valor:</strong>
							</td>
							<td align="right" width="18%">
								<strong><%= Util.formatarMoedaReal(valorTotal) %></strong>
							</td>
						</tr>
					</table>
					<table width="100%" bgcolor="#99CCFF" dwcopytype="CopyTableRow">
						<!--header da tabela interna -->
						<tr bordercolor="#FFFFFF" bgcolor="#90c7fc">
							<td width="25%">
							<div align="center" class="style9"><strong>Data</strong></div>
							</td>
							<td width="23%">
							<div align="center" class="style9"><strong>Valor Dia</strong></div>

							</td>
							<td width="16%">
							<div align="center"><strong>Percentual</strong></div>
							</td>
							<td width="18%" align="center"><strong>Valor At&eacute; o Dia</strong></td>
							<td width="18%">
							<div align="center"><strong>Percentual</strong></div>
							</td>
						</tr>
						<% 
						BigDecimal valorDia = new BigDecimal("0.00");
						String cor = "#cbe5fe";
						%>
						<logic:present name="colecaoDadosDiarios">
						<logic:iterate name="colecaoDadosDiarios" id="arrecadacaoDadosDiarios">
						<% if (cor.equalsIgnoreCase("#cbe5fe")) {
									cor = "#FFFFFF";%>
								<tr bgcolor="#FFFFFF">
								<%} else {
									cor = "#cbe5fe";%>
								<tr bgcolor="#cbe5fe">
								<%}%>
							<td height="17" align="center">

							<%
								Date dataPagamento = ((ArrecadacaoDadosDiarios)arrecadacaoDadosDiarios).getDataPagamento();
								Calendar calendarioDataPagamento = new GregorianCalendar();
								calendarioDataPagamento.setTime(dataPagamento);
								
								String anoMesDataPagamento = "";
								if ((calendarioDataPagamento.get(Calendar.MONTH) + 1) < 10) {
									anoMesDataPagamento = calendarioDataPagamento.get(Calendar.YEAR)+ "0"+ (calendarioDataPagamento.get(Calendar.MONTH) + 1);
								} else {
									anoMesDataPagamento = calendarioDataPagamento.get(Calendar.YEAR)+ "" + (calendarioDataPagamento.get(Calendar.MONTH) + 1);
								}
							
							%>
							
							 
								<logic:present name="arrecadacaoDadosDiarios" property="dataPagamento">

									<% 
									if(anoMesDataPagamento.equals(referencia)){
										%>
									        <bean:write name="arrecadacaoDadosDiarios" property="dataPagamento" formatKey="date.format"/>
										
									<%		
									}else{
										%>
										 <font color="#CC0000"> 
										        <bean:write name="arrecadacaoDadosDiarios" property="dataPagamento" formatKey="date.format"/>
										 </font>									
										
										<%
									}
									%>

								</logic:present>
							</td>
							<td>
								<logic:present name="arrecadacaoDadosDiarios" property="valorPagamentos">
									<% 
									if(anoMesDataPagamento.equals(referencia)){
										%>
										      <div align="right">
										        <bean:write name="arrecadacaoDadosDiarios" property="valorPagamentos" formatKey="money.format"/>
											</div>
									<%		
									}else{
										%>
										 <font color="#CC0000"> 
										      <div align="right">
										        <bean:write name="arrecadacaoDadosDiarios" property="valorPagamentos" formatKey="money.format"/>
											  </div>
										 </font>									
										
										<%
									}
									%>
								</logic:present>
							</td>
							<% 
								BigDecimal percentualMultiplicacao = ((ArrecadacaoDadosDiarios)arrecadacaoDadosDiarios).getValorPagamentos().multiply(new BigDecimal("100.00"));

								BigDecimal percentual = percentualMultiplicacao.divide(
										valorTotal,2,BigDecimal.ROUND_HALF_UP);

								valorDia = ((ArrecadacaoDadosDiarios)arrecadacaoDadosDiarios).getValorPagamentos().add(valorDia);
								
								BigDecimal percentualMultiplicacaoDoDia = valorDia.multiply(new BigDecimal("100.00"));

								BigDecimal percentualDoDia = percentualMultiplicacaoDoDia.divide(
										valorTotal,2,BigDecimal.ROUND_HALF_UP);
							%>								
							<td>
									<% 
									if(anoMesDataPagamento.equals(referencia)){
										%>
										<div align="right"><%= Util.formatarMoedaReal(percentual) %> %</div>
										
									<%		
									}else{
										%>
										 <font color="#CC0000"> 
											<div align="right"><%= Util.formatarMoedaReal(percentual) %> %</div>

										 </font>									
										
										<%
									}
									%>
							</td>
							<td>
									<% 
									if(anoMesDataPagamento.equals(referencia)){
										%>
											<div align="right"><%= Util.formatarMoedaReal(valorDia) %></div>
										
									<%		
									}else{
										%>
										 <font color="#CC0000"> 
											<div align="right"><%= Util.formatarMoedaReal(valorDia) %></div>
										 </font>									
										
										<%
									}
									%>
							</td>
							<td>
									<% 
									if(anoMesDataPagamento.equals(referencia)){
										%>
											<div align="right"><%= Util.formatarMoedaReal(percentualDoDia) %> %</div>
										
									<%		
									}else{
										%>
										 <font color="#CC0000"> 
											<div align="right"><%= Util.formatarMoedaReal(percentualDoDia) %> %</div>
										 </font>									
										
										<%
									}
									%>
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
