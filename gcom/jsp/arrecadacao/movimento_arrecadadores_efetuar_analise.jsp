<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<%@ page import="gcom.arrecadacao.bean.FiltrarMovimentoArrecadadoresHelper"%>
<%@ page import="gcom.util.Pagina,gcom.util.ConstantesSistema"%>
<%@ page import="java.util.Collection"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<SCRIPT LANGUAGE="JavaScript">
<!--



//-->
</SCRIPT>


</head>

<body leftmargin="5" topmargin="5">
<form action="/filtrarMovimentoArrecadadoresAction"><%@ include
	file="/jsp/util/cabecalho.jsp"%> <%@ include file="/jsp/util/menu.jsp"%>

<table width="770" border="0" cellspacing="4" cellpadding="0">
	<tr>
		<td width="149" valign="top" class="leftcoltext">
		<div align="center">
		<%--<p align="left">&nbsp;</p>--%>
		<%--<p align="left">&nbsp;</p>--%>
		<%--<p align="left">&nbsp;</p>--%>

		<%@ include file="/jsp/util/informacoes_usuario.jsp"%>

		<%--<p align="left">&nbsp;</p>--%>
		<%--<p align="left">&nbsp;</p>--%>
		<%--<p align="left">&nbsp;</p>--%>
		<%--<p align="left">&nbsp;</p>--%>
		<%--<p align="left">&nbsp;</p>--%>
		<%--<p align="left">&nbsp;</p>--%>
		<%--<p align="left">&nbsp;</p>--%>
		<%--<p align="left">&nbsp;</p>--%>
		<%--<p align="left">&nbsp;</p>--%>
		<%--<p align="left">&nbsp;</p>--%>
		<%--<p align="left">&nbsp;</p>--%>
		<%--<p align="left">&nbsp;</p>--%>

		<%@ include file="/jsp/util/mensagens.jsp"%>

		<%--<p align="left">&nbsp;</p>--%>
		<%--<p align="left">&nbsp;</p>--%>
		<%--<p align="left">&nbsp;</p>--%>
		<%--<p align="left">&nbsp;</p>--%>
		<%--<p align="left">&nbsp;</p>--%>
		<%--<p align="left">&nbsp;</p>--%>
		<%--<p align="left">&nbsp;</p>--%>
		</div>
		</td>

		<td valign="top" class="centercoltext">

		<p>&nbsp;</p>

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="11"><img border="0"
					src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
				<td class="parabg">Efetuar An�lise do Movimento dos Arrecadadores</td>
				<td width="11"><img border="0"
					src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
			</tr>
			<tr>
				<td height="5" colspan="3"></td>
			</tr>
		</table>



		<table width="590" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td colspan="4" height="23"><font color="#000000"
					style="font-size: 10px"
					face="Verdana, Arial, Helvetica, sans-serif"><strong>Movimentos
				encontrados:</strong></font></td>
			</tr>
			<tr>
				<td bgcolor="#000000" height="2"></td>
			</tr>
			<tr>
				<td>
				<table width="590" bgcolor="#99CCFF">
					<tr bgcolor="#99CCFF">
						<td align="center" width="70"><FONT COLOR="#000000"><strong>Arrec.</strong></FONT></td>
						<td align="center" width="70"><FONT COLOR="#000000"><strong>Remessa</strong></FONT></td>
						<td align="center" width="85"><FONT COLOR="#000000"><strong>Servi�o</strong></FONT></td>
						<td align="center" width="70"><FONT COLOR="#000000"><strong>NSA</strong></FONT></td>
						<td align="center" width="70"><FONT COLOR="#000000"><strong>Dt.Ger.</strong></FONT></td>
						<td align="center" width="70"><FONT COLOR="#000000"><strong>Qtd.Reg.</strong></FONT></td>
						<logic:equal name="indEmpresaTrabalhaConcessionaria" value="<%=""+ConstantesSistema.SIM%>">
							<td align="center" width="70"><FONT COLOR="#000000"><strong>Concess.</strong></FONT></td>
						</logic:equal>
						<td align="center" width="70"><FONT COLOR="#000000"><strong>Valor
						Informado</strong></FONT></td>
						<td align="center" width="85"><FONT COLOR="#000000"><strong>Dt.Proc.</strong></FONT></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td>

				<table width="590" bgcolor="#99CCFF">

					<%String cor = "#FFFFFF";%>

					<%--Esquema de pagina��o--%>
					<pg:pager isOffset="true" index="half-full" maxIndexPages="10"
						export="currentPageNumber=pageNumber;pageOffset" maxPageItems="10"
						items="${sessionScope.totalRegistros}">
						<pg:param name="q" />

						<logic:iterate name="colecaoFiltrarMovimentoArrecadadoresHelper"
							id="filtrarMovimentoArrecadadoresHelper" type="FiltrarMovimentoArrecadadoresHelper">
							<pg:item>

								<%if (cor.equalsIgnoreCase("#FFFFFF")) {
				cor = "#cbe5fe";%>
								<tr bgcolor="#FFFFFF">
									<%} else {
				cor = "#FFFFFF";%>
								<tr bgcolor="#cbe5fe">
									<%}%>

									<logic:present name="filtrarMovimentoArrecadadoresHelper"
										property="arrecadadorMovimento">
										<td align="center" width="70"><bean:write
											name="filtrarMovimentoArrecadadoresHelper" property="arrecadadorMovimento.codigoBanco" /></td>
									</logic:present>
									<logic:notPresent name="filtrarMovimentoArrecadadoresHelper"
										property="arrecadadorMovimento.codigoBanco">
										<td align="center" width="70">&nbsp;</td>
									</logic:notPresent>


									<logic:present name="filtrarMovimentoArrecadadoresHelper"
										property="arrecadadorMovimento.codigoRemessa">
										<logic:equal name="filtrarMovimentoArrecadadoresHelper"
											property="arrecadadorMovimento.codigoRemessa"
											value="<%=""+ConstantesSistema.CODIGO_ENVIO%>">
											<td align="left" width="70"><%=ConstantesSistema.ENVIO.toUpperCase()%></td>
										</logic:equal>
										<logic:notEqual name="filtrarMovimentoArrecadadoresHelper"
											property="arrecadadorMovimento.codigoRemessa"
											value="<%=""+ConstantesSistema.CODIGO_ENVIO%>">
											<td align="left" width="70"><%=ConstantesSistema.RETORNO.toUpperCase()%></td>
										</logic:notEqual>

									</logic:present>
									<logic:notPresent name="filtrarMovimentoArrecadadoresHelper"
										property="arrecadadorMovimento.codigoRemessa">
										<td align="center" width="70">&nbsp;</td>
									</logic:notPresent>


									<logic:present name="filtrarMovimentoArrecadadoresHelper"
										property="arrecadadorMovimento.descricaoIdentificacaoServico">
										<td align="left" width="85"><bean:write
											name="filtrarMovimentoArrecadadoresHelper"
											property="arrecadadorMovimento.descricaoIdentificacaoServico" /></td>
									</logic:present>
									<logic:notPresent name="filtrarMovimentoArrecadadoresHelper"
										property="arrecadadorMovimento.descricaoIdentificacaoServico">
										<td align="left" width="85">&nbsp;</td>
									</logic:notPresent>


									<logic:present name="filtrarMovimentoArrecadadoresHelper"
										property="arrecadadorMovimento.numeroSequencialArquivo">
										<td align="center" width="70">
											<a href="/gsan/exibirApresentarAnaliseMovimentoArrecadadoresAction.do?botao=nao&arrecadadorMovimentoID=<bean:write
														name="filtrarMovimentoArrecadadoresHelper" property="arrecadadorMovimento.id" />&idConcessionaria=<bean:write
														name="filtrarMovimentoArrecadadoresHelper" property="idConcessionaria" />&nomeConcessionaria=<bean:write
														name="filtrarMovimentoArrecadadoresHelper" property="nomeConcessionaria" />">
											<bean:write name="filtrarMovimentoArrecadadoresHelper"
												property="arrecadadorMovimento.numeroSequencialArquivo" />
										</a></td>
									</logic:present>
									<logic:notPresent name="filtrarMovimentoArrecadadoresHelper"
										property="arrecadadorMovimento.numeroSequencialArquivo">
										<td align="center" width="70">&nbsp;</td>
									</logic:notPresent>


									<logic:present name="filtrarMovimentoArrecadadoresHelper"
										property="arrecadadorMovimento.dataGeracao">
										<td align="center" width="70"><bean:write
											name="filtrarMovimentoArrecadadoresHelper" property="arrecadadorMovimento.dataGeracao"
											formatKey="date.format" /></td>
									</logic:present>
									<logic:notPresent name="filtrarMovimentoArrecadadoresHelper"
										property="arrecadadorMovimento.dataGeracao">
										<td align="center" width="70">&nbsp;</td>
									</logic:notPresent>


									<logic:present name="filtrarMovimentoArrecadadoresHelper"
										property="arrecadadorMovimento.numeroRegistrosMovimento">
										<td align="center" width="70"><bean:write
											name="filtrarMovimentoArrecadadoresHelper"
											property="arrecadadorMovimento.numeroRegistrosMovimento" /></td>
									</logic:present>
									<logic:notPresent name="filtrarMovimentoArrecadadoresHelper"
										property="arrecadadorMovimento.numeroRegistrosMovimento">
										<td align="center" width="70">&nbsp;</td>
									</logic:notPresent>
<logic:equal name="indEmpresaTrabalhaConcessionaria" value="<%=""+ConstantesSistema.SIM%>">
									<logic:present name="filtrarMovimentoArrecadadoresHelper"
										property="idConcessionaria">
										<td align="center" width="70"  title=" ${filtrarMovimentoArrecadadoresHelper.nomeConcessionaria}"><bean:write
											name="filtrarMovimentoArrecadadoresHelper" property="idConcessionaria" /></td>
									</logic:present>
									<logic:notPresent name="filtrarMovimentoArrecadadoresHelper"
										property="idConcessionaria">
										<td align="right" width="70">&nbsp;</td>
									</logic:notPresent>
</logic:equal>

									<logic:present name="filtrarMovimentoArrecadadoresHelper"
										property="arrecadadorMovimento.valorTotalMovimento">
										<td align="right" width="70"><bean:write
											name="filtrarMovimentoArrecadadoresHelper" property="arrecadadorMovimento.valorTotalMovimento"
											formatKey="money.format" /></td>
									</logic:present>
									<logic:notPresent name="filtrarMovimentoArrecadadoresHelper"
										property="arrecadadorMovimento.valorTotalMovimento">
										<td align="right" width="70">&nbsp;</td>
									</logic:notPresent>


									<logic:present name="filtrarMovimentoArrecadadoresHelper"
										property="arrecadadorMovimento.ultimaAlteracao">
										<td align="center" width="85"><bean:write
											name="filtrarMovimentoArrecadadoresHelper" property="arrecadadorMovimento.ultimaAlteracao"
											formatKey="datehour.format" /></td>
									</logic:present>
									<logic:notPresent name="filtrarMovimentoArrecadadoresHelper"
										property="arrecadadorMovimento.ultimaAlteracao">
										<td align="center" width="85">&nbsp;</td>
									</logic:notPresent>


								</tr>


							</pg:item>
						</logic:iterate>
				</table>

				</td>
			</tr>
			<tr bordercolor="#90c7fc">
				<td>
				<table width="100%">
					<tr>
						<td><input name="Button" type="button" class="bottonRightCol"
							value="Voltar Filtro" onmouseover="Tip('Mensagem');" nmouseout = "UnTip()"
							onclick="window.location.href='/gsan/exibirFiltrarMovimentoArrecadadoresAction.do'"
							align="left" style="width: 80px;"></td>
						<td valign="top">
						<div align="right"><a href="javascript:toggleBox('demodiv',1);"> <img
							border="0" src="<bean:message key="caminho.imagens"/>print.gif"
							title="Imprimir Movimentos Arrecadadores" /> </a></div>
						</td>
					</tr>
				</table>
				<table width="100%" border="0">
					<tr>
						<td>
						<div align="center"><strong><%@ include
							file="/jsp/util/indice_pager_novo.jsp"%></strong></div>
						</td>
						</pg:pager>
						<%-- Fim do esquema de pagina��o --%>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>

<jsp:include
	page="/jsp/relatorio/escolher_tipo_relatorio.jsp?relatorio=gerarRelatorioMovimentoArrecadadorManterAction.do" />
<%@ include file="/jsp/util/rodape.jsp"%>
</form>
</body>
</html:html>
