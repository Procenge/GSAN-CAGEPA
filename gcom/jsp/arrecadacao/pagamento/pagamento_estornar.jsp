<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="gcom.arrecadacao.pagamento.Pagamento"%>
<%@page import="gcom.arrecadacao.pagamento.PagamentoHistorico"%>
<%@page import="gcom.util.Util" isELIgnored="false"%>

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
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">
<!--

	
-->
</script>

<style>
.fontePequena {
font-size: 11px;
face: Verdana, Arial, Helvetica, sans-serif;
}

</style>

</head>

<body leftmargin="5" topmargin="5">

<html:form action="/estornarPagamentoAction.do"
	name="EstornarPagamentoActionForm"
	type="gcom.gui.arrecadacao.pagamento.EstornarPagamentoActionForm" 
	method="post">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

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
			<td width="625" valign="top" class="centercoltext">
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
					<td class="parabg">Estorno Pagamento</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0" style="fonteReduzida">
				<tr>
					<td colspan="4">
					<table width="100%" align="center" bgcolor="#99CCFF" border="0"
						cellpadding="0" cellspacing="0" class="fontePequena">

						<tr bgcolor="#79bbfd" height="20">
							<td align="center"><strong>Pagamentos</strong></td>
						</tr>

						<tr bgcolor="#cbe5fe">
							<td width="100%" align="center">
							<table width="100%" bgcolor="#90c7fc" class="fontePequena">
								<tr bordercolor="#000000">
									<td width="5%" align="center" rowspan="2"><strong>&nbsp;</strong></td>
									<td width="8%" align="center" rowspan="2"><strong>Imóvel</strong></td>
									<td width="8%" align="center" rowspan="2"><strong>Cliente</strong></td>
									<td width="27%" align="center" colspan="3"><strong>Aviso Bancário</strong></td>
									<td width="10%" align="center" rowspan="2"><strong>Tipo de Doc.</strong></td>
									<td width="10%" align="center" rowspan="2"><strong>Mês/Ano</strong></td>
									<td width="10%" align="center" rowspan="2"><strong>Valor do Pag.</strong></td>
									<td width="12%" align="center" rowspan="2"><strong>Data do Pag.</strong></td>
									<td width="10%" align="center" rowspan="2"><strong>Situação Atual</strong></td>
								</tr>
								<tr>
									<td width="8%" bgcolor="#cbe5fe" align="center"><strong>Agente</strong>
									</td>
									<td width="11%" bgcolor="#cbe5fe" align="center"><strong>Data Lanc.</strong>
									</td>
									<td width="8%" bgcolor="#cbe5fe" align="center"><strong>Seq.</strong>
									</td>
								</tr>

						<%--Esquema de paginação--%>
						<pg:pager isOffset="true" index="half-full" maxIndexPages="10"
							export="currentPageNumber=pageNumber;pageOffset"
							maxPageItems="10" items="${sessionScope.totalRegistros}">
							<pg:param name="pg" />
							<pg:param name="q" />

								<!--  Historico -->
								<logic:present
									name="colecaoPagamentosHistorico"
									scope="session">
									
									<%int cont = 1;
										Integer parametroAnoMesArrecadacao = (Integer) session.getAttribute("parametroAnoMesArrecadacao");
									%>

									<logic:iterate
										name="colecaoPagamentosHistorico" id="pagamentoHistoricoHelper" >
										
										<pg:item>
										
										<%cont = cont + 1;
											if (cont % 2 == 0) {%>
										<tr bgcolor="#FFFFFF">
											<%} else {%>
										<tr bgcolor="#cbe5fe">
											<%}%>
											<td width="5%" align="center">
												<c:if test="${(pagamentoHistoricoHelper.pagamentoHistorico.anoMesReferenciaArrecadacao < parametroAnoMesArrecadacao) 
																|| pagamentoHistoricoHelper.creditoARealizarHistorico || pagamentoHistoricoHelper.creditoARealizar}">
													<input type="radio" name="id" value="${pagamentoHistoricoHelper.pagamentoHistorico.id}" disabled="disabled" />
												</c:if>
												<c:if test="${(pagamentoHistoricoHelper.pagamentoHistorico.anoMesReferenciaArrecadacao >= parametroAnoMesArrecadacao)
																&& !pagamentoHistoricoHelper.creditoARealizarHistorico && !pagamentoHistoricoHelper.creditoARealizar}">  
													<input type="radio" name="id" value="${pagamentoHistoricoHelper.pagamentoHistorico.id}" />
												</c:if>
											</td>
											<td width="8%" align="center"><logic:notEmpty
												name="pagamentoHistoricoHelper" property="pagamentoHistorico.imovel">
												<font color="#000000"> ${pagamentoHistoricoHelper.pagamentoHistorico.imovel.id} </font>
												</logic:notEmpty></td>
											<td width="8%" align="center"><logic:notEmpty
												name="pagamentoHistoricoHelper" property="pagamentoHistorico.cliente">
												<font color="#000000"> ${pagamentoHistoricoHelper.pagamentoHistorico.cliente.id} </font>
												</logic:notEmpty></td>
												
											<logic:notEmpty name="pagamentoHistoricoHelper" property="pagamentoHistorico.avisoBancario">
												<td width="8%" align="center">
													<font color="#000000">
														${pagamentoHistoricoHelper.pagamentoHistorico.avisoBancario.arrecadador.codigoAgente}
													</font>
												</td>
												<td width="11%" align="center">
													<font color="#000000">
														<bean:write name="pagamentoHistoricoHelper" property="pagamentoHistorico.avisoBancario.dataLancamento" formatKey="date.format" />
													</font>
												</td>
												<td width="8%" align="center">
													<font color="#000000">
														${pagamentoHistoricoHelper.pagamentoHistorico.avisoBancario.numeroSequencial}
													</font>
												</td>
											</logic:notEmpty>
											<logic:empty name="pagamentoHistoricoHelper" property="pagamentoHistorico.avisoBancario">
												<td width="8%" align="center">&nbsp;</td>
												<td width="11%" align="center">&nbsp;</td>
												<td width="8%" align="center">&nbsp;</td>
											</logic:empty>

											<td width="10%" align="center">
											<logic:present 	name="pagamentoHistoricoHelper" property="pagamentoHistorico.documentoTipo">
												<a style="color: #000000;" href="javascript:abrirPopup('exibirPagamentoPopupAction.do?idPagamentoHistorico=${pagamentoHistoricoHelper.pagamentoHistorico.id}' , 210, 510);">
													<font color="#000000"> ${pagamentoHistoricoHelper.pagamentoHistorico.documentoTipo.descricaoDocumentoTipo}</font>
												</a>
											</logic:present>
											<logic:notPresent 	name="pagamentoHistoricoHelper" property="pagamentoHistorico.documentoTipo">
												<a style="color: #000000;" href="javascript:abrirPopup('exibirConsultarPagamentoPopupAction.do?idPagamentoHistorico=${pagamentoHistoricoHelper.pagamentoHistorico.id}' , 210, 510);">
													<font color="#000000">.</font>
												</a>
											</logic:notPresent></td>
											<td width="10%" align="center"><logic:present 	name="pagamentoHistoricoHelper" property="pagamentoHistorico.anoMesReferenciaPagamento">
												<bean:define name="pagamentoHistoricoHelper" property="pagamentoHistorico.anoMesReferenciaPagamento" id ="anoMesReferenciaPagamento"/>
												<font color="#000000"><%=Util.formatarAnoMesParaMesAno((Integer) anoMesReferenciaPagamento)%></font>
											</logic:present></td>
											<td width="10%" align="right"><font color="#000000"> <bean:write
												name="pagamentoHistoricoHelper" property="pagamentoHistorico.valorPagamento"
												formatKey="money.format" /> </font> </td>

											<td width="12%" align="center">
												<a style="color: #000000;"
														href="javascript:abrirPopup('exibirConsultarPagamentoPopupAction.do?idPagamentoHistorico=${pagamentoHistoricoHelper.pagamentoHistorico.id}' , 210, 510);">
												<bean:write name="pagamentoHistoricoHelper" property="pagamentoHistorico.dataPagamento" formatKey="date.format" />
												</a>
											</td>
											
											<td width="10%">
												<font color="#000000">
													${pagamentoHistoricoHelper.pagamentoHistorico.pagamentoSituacaoAtual.descricaoAbreviada}
												</font>
											</td>
										</tr>
										
										
										</pg:item>
										
										
									</logic:iterate>
								</logic:present>

							</table>
							
							<table width="100%" border="0">
								<tr>
									<td>
										<div align="center"><strong>
											<%@ include file="/jsp/util/indice_pager_novo.jsp"%></strong>
										</div>
									</td>
									</pg:pager>
									<%-- Fim do esquema de paginação --%>
								</tr>
							</table>
							
							</td>
						</tr>
					</table>
					</td>
				</tr>

				<tr>
					<td>
											<table width="100%" border="0">
												<tr>
													<td><input type="button" name="ButtonCancelar"
														class="bottonRightCol" value="Cancelar"
														onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
														&nbsp;&nbsp;
													<input type="button" name="ButtonReset" class="bottonRightCol"
														value="Voltar Filtro" onClick="window.location.href='/gsan/exibirFiltrarPagamentoAction.do?tela=estorno&primeiraVez=nao'"></td>
													<td align="right">
													<div align="right">
													<input type="button" name="ButtonReset" class="bottonRightCol"
														value="Estornar Pagamento"
														onclick="javascript:if(confirm('Confirma estorno?')){document.forms[0].submit();}">
														&nbsp;&nbsp;&nbsp;
													<a href="javascript:toggleBox('demodiv',1);">
													<img border="0"
														src="<bean:message key="caminho.imagens"/>print.gif"
														title="Imprimir Pagamentos" /> </a></div>
													</td>
												</tr>
											</table>

					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<jsp:include
		page="/jsp/relatorio/escolher_tipo_relatorio.jsp?relatorio=gerarRelatorioPagamentoAction.do" />
	
	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</body>
</html:html>
