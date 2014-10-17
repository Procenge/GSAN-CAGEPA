<%@page import="gcom.cobranca.bean.ResumoCobrancaAcaoRemuneracaoHelper"%>
<%@page import="java.util.Map"%>
<%@page import="gcom.util.Util"%>
<%@page import="java.math.BigDecimal"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page import="java.util.Locale"%>

<% session.setAttribute("currentLocale", new Locale("pt", "BR")); %> 

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
<%@ page
	import="gcom.gerencial.bean.CobrancaAcaoPerfilIndicadorHelper,gcom.gerencial.bean.CobrancaAcaoPerfilHelper"%>
<SCRIPT LANGUAGE="JavaScript">
<!--

function fechar(){
		window.close();
}
</SCRIPT>
</head>
<body leftmargin="5" topmargin="5">
<html:form action="/exibirConsultarResumoAcaoCobrancaPopupAction.do"
	name="ResumoAcaoCobrancaActionForm"
	type="gcom.gui.gerencial.cobranca.ResumoAcaoCobrancaActionForm"
	method="post">
	<table width="570" border="0" cellpadding="0" cellspacing="5">
		<tr>
			<td width="560" valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table><br>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Consultar Resumo das Ações de Cobrança Eventuais</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table><br>
			<table>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td><strong>Ação de Cobrança:</strong></td>
					<td>${requestScope.cobrancaAcao}</td>
				</tr>
				<tr>
					<td><strong>Situação da Ação:</strong></td>
					<td>${requestScope.cobrancaAcaoSituacao}</td>
				</tr>
				<logic:notEmpty name="idCobrancaAcaoDebito">
					<tr>
						<td><strong>Situação do Débito:</strong></td>
						<td>${requestScope.cobrancaAcaoDebito}</td>
					</tr>
				</logic:notEmpty>
			</table><br>

			<table bgcolor="#90c7fc" border="0">
				<tr>
					<td bgcolor="#79bbfd" align="center"><strong> Perfil do Imóvel</strong></td>
					<td bgcolor="#79bbfd" align="center"><strong> Limite ${requestScope.valorLimitePrioridade}</strong></td>
					<td bgcolor="#79bbfd" align="center"><strong> Quantidade Documentos</strong></td>
					<td bgcolor="#79bbfd" align="center"><strong> Percentual </strong></td>
					<td bgcolor="#79bbfd" align="center"><strong> Valor Documentos</strong></td>
					<td bgcolor="#79bbfd" align="center"><strong> Percentual</strong></td>
				</tr>
				<logic:notEmpty name="colecaoResumoCobrancaAcaoPerfil">



					<logic:iterate name="colecaoResumoCobrancaAcaoPerfil"
						id="resumoCobrancaAcaoPerfil">

						<tr>

							<%int tamanho = ((CobrancaAcaoPerfilHelper) resumoCobrancaAcaoPerfil)
									.getColecaoCobrancaAcaoPerfilIndicador()
									.size();

							%>
							<%if (tamanho == 1) {

							%>
							<td width="17%" bgcolor="#cbe5fe" align="center" rowspan="2"><bean:write
								name="resumoCobrancaAcaoPerfil" property="descricao" /></td>
							<%} else {

							%>
							<td width="17%" bgcolor="#cbe5fe" align="center" rowspan="3"><bean:write
								name="resumoCobrancaAcaoPerfil" property="descricao" /></td>

							<%}

							%>


							<%String cor = "#FFFFFF";%>
							<logic:iterate name="resumoCobrancaAcaoPerfil"
								property="colecaoCobrancaAcaoPerfilIndicador"
								id="resumoCobrancaAcaoPerfilIndicador">
								<%if (cor.equalsIgnoreCase("#FFFFFF")) {
									cor = "#cbe5fe";%>
								<td width="17%" align="center" bgcolor="#FFFFFF"><bean:write
									name="resumoCobrancaAcaoPerfilIndicador"
									property="descricaoIndicador" /></td>
								<td width="17%" align="center" bgcolor="#FFFFFF"><bean:write
									name="resumoCobrancaAcaoPerfilIndicador"
									property="quantidadeDocumento" formatKey="number.format" locale="currentLocale" /></td>
								<td width="17%" align="right" bgcolor="#FFFFFF"><%=((CobrancaAcaoPerfilIndicadorHelper) resumoCobrancaAcaoPerfilIndicador)
															.getPercentualQuantidade(""
																	+ request
																			.getAttribute("quantidadeTotal"))%></td>
								<td width="17%" align="right" bgcolor="#FFFFFF"><bean:write
									name="resumoCobrancaAcaoPerfilIndicador"
									property="valorDocumento" formatKey="money.format" locale="currentLocale" /></td>
								<td width="15%" align="right" bgcolor="#FFFFFF"><%=((CobrancaAcaoPerfilIndicadorHelper) resumoCobrancaAcaoPerfilIndicador)
															.getPercentualValor(""
																	+ request
																			.getAttribute("valorTotal"))%></td>
								<%} else {
									cor = "#FFFFFF";%>
								<td width="17%" align="center" bgcolor="#cbe5fe"><bean:write
									name="resumoCobrancaAcaoPerfilIndicador"
									property="descricaoIndicador" /></td>
								<td width="17%" align="center" bgcolor="#cbe5fe"><bean:write
									name="resumoCobrancaAcaoPerfilIndicador"
									property="quantidadeDocumento" formatKey="number.format" locale="currentLocale" /></td>
								<td width="17%" align="right" bgcolor="#cbe5fe"><%=((CobrancaAcaoPerfilIndicadorHelper) resumoCobrancaAcaoPerfilIndicador)
															.getPercentualQuantidade(""
																	+ request
																			.getAttribute("quantidadeTotal"))%></td>
								<td width="17%" align="right" bgcolor="#cbe5fe"><bean:write
									name="resumoCobrancaAcaoPerfilIndicador"
									property="valorDocumento" formatKey="money.format" locale="currentLocale" /></td>
								<td width="15%" align="right" bgcolor="#cbe5fe"><%=((CobrancaAcaoPerfilIndicadorHelper) resumoCobrancaAcaoPerfilIndicador)
															.getPercentualValor(""
																	+ request
																			.getAttribute("valorTotal"))%></td>
								<%}%>
						</tr>
					</logic:iterate>
					
					<tr>
						<td bgcolor="#FFFFFF" width="17%" align="center"><strong>TOTAL</strong></td>
						<td bgcolor="#FFFFFF" width="17%" align="center"><strong><bean:write
							name="resumoCobrancaAcaoPerfil" property="quantidadeDocumento"
							formatKey="number.format" locale="currentLocale" /></strong></td>
						<td bgcolor="#FFFFFF" width="17%" align="right"><strong><%=((CobrancaAcaoPerfilHelper) resumoCobrancaAcaoPerfil)
													.getPercentualQuantidade(""
															+ request
																	.getAttribute("quantidadeTotal"))%></strong></td>
						<td bgcolor="#FFFFFF" width="17%" align="right"><strong><bean:write
							name="resumoCobrancaAcaoPerfil" property="valorDocumento"
							formatKey="money.format" locale="currentLocale" /></strong></td>
						<td bgcolor="#FFFFFF" width="15%" align="right"><strong> <%=((CobrancaAcaoPerfilHelper) resumoCobrancaAcaoPerfil)
													.getPercentualValor(""
															+ request
																	.getAttribute("valorTotal"))%> </strong></td>
					</tr>
					</logic:iterate>
				</logic:notEmpty>
				<tr>
					<td bgcolor="#cbe5fe" width="34%" align="center" colspan="2"><strong>TOTAL</strong></td>
					<td bgcolor="#cbe5fe" width="17%" align="center"><strong>${requestScope.quantidadeTotal}</strong></td>
					<td bgcolor="#cbe5fe" width="17%" align="right"><strong>100,00</strong></td>
					<td bgcolor="#cbe5fe" width="17%" align="right"><strong>${requestScope.valorTotalFormatado}</strong></td>
					<td bgcolor="#cbe5fe" width="15%" align="right"><strong>100,00</strong></td>
				</tr>
			</table> <br>
			
			<logic:present name="colecaoResumoCobrancaAcaoRemuneracao">
				<logic:notEmpty name="colecaoResumoCobrancaAcaoRemuneracao">
				<br>
				<table bgcolor="#90c7fc" border="0">
				    <tr>
		               <td bgcolor="#79bbfd" align="center" colspan="3"><strong>Resumo de Percentual de Remuneração </strong></td>
		            </tr>
					<tr>
						<td bgcolor="#79bbfd" align="center"><strong>Faixa Remuneração</strong></td>
						<td bgcolor="#79bbfd" align="center"><strong>Valor Remuneração</strong></td>
						<td bgcolor="#79bbfd" align="center"><strong>Percentual</strong></td>
					</tr>
					<%
						BigDecimal valorItemTotal = null;
						BigDecimal valorPercentual = BigDecimal.ZERO;
						BigDecimal totalPorcentagemRemurecacao = BigDecimal.ZERO;
						String tipoDocAnterior = null;
						ResumoCobrancaAcaoRemuneracaoHelper item = null;
					%>
					<logic:iterate name="colecaoResumoCobrancaAcaoRemuneracao" id="itemResumoCobrancaAcaoRemuneracao">
					<% item = ((ResumoCobrancaAcaoRemuneracaoHelper) itemResumoCobrancaAcaoRemuneracao); %>
						<% //-- TOTAL -- %>
						<% if(!Util.isVazioOuBranco(tipoDocAnterior) && !item.getDescricaoDocumentoTipo().equals(tipoDocAnterior)){ %>
						<tr bgcolor="#79bbfd">
							<td align="center"><strong>TOTAL</strong></td>
							<td align="center"><strong>R$ <%=Util.formatarMoedaReal(valorItemTotal)%></strong></td>
							<td align="center"><strong> <%=Util.formatarMoedaReal(totalPorcentagemRemurecacao)%>%</strong></td>
						</tr>
						<% 
						   totalPorcentagemRemurecacao = BigDecimal.ZERO;
						 }
						%>
						<% //-- TOTAL -- %>
	
						<% //-- CABECALHO -- %>
						<% if(Util.isVazioOuBranco(tipoDocAnterior) || !tipoDocAnterior.equals(item.getDescricaoDocumentoTipo())){
							valorItemTotal = ((Map<String, BigDecimal>) request.getAttribute("mapValorTotalRemuneracao")).get(item.getDescricaoDocumentoTipo());
						%>
						<tr >
							<td bgcolor="#cbe5fe" colspan="3" align="left"><b>Tipo Documento: <bean:write name="itemResumoCobrancaAcaoRemuneracao" property="descricaoDocumentoTipo" /></b></td>
						</tr>
						<% } %>
						<% //-- CABECALHO -- %>
						<%
							valorPercentual = BigDecimal.ZERO.equals(valorItemTotal) ? BigDecimal.ZERO : new BigDecimal(Util.calcularPercentual(item.getValorRemuneracao().toString(), valorItemTotal.toString()));
							totalPorcentagemRemurecacao = totalPorcentagemRemurecacao.add(valorPercentual);
						%>
						<% //-- Body -- %>
						<tr bgcolor="#FFFFFF">
							<td width="40%" align="center">
							  <bean:write name="itemResumoCobrancaAcaoRemuneracao" property="percentualRemuneracao" formatKey="money.format" locale="currentLocale" />%
							</td>
							<td width="30%" align="center">
							  <bean:write name="itemResumoCobrancaAcaoRemuneracao" property="valorRemuneracao" formatKey="money.format" locale="currentLocale" />
							</td>
							<td width="30%" align="center"><%=Util.formatarMoedaReal(valorPercentual)%>%</td>
						</tr>
	
						<% tipoDocAnterior = item.getDescricaoDocumentoTipo(); %>
						<% //-- Body -- %>
					</logic:iterate>
					<% //-- TOTAL FINAL -- %>
					<tr bgcolor="#79bbfd">
						<td align="center"><strong>TOTAL</strong></td>
						<td align="center"><strong>R$ <%=Util.formatarMoedaReal(valorItemTotal)%></strong></td>
						<td align="center"><strong> <%=Util.formatarMoedaReal(totalPorcentagemRemurecacao)%>%</strong></td>
					</tr>
					<% //-- TOTAL FINAL -- %>
	
				</table><br>
	
				</logic:notEmpty>
			</logic:present>


					<table border="0" width="100%">
				<tr>
					<td colspan="3">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="3" align="right"><input name="Button" type="button"
						class="bottonRightCol" value="Fechar"
						onClick="javascript:fechar();"></td>
				</tr>
			</table>
			</td>
		</tr>
		
	</table>
</html:form>
<body>
</html:html>
