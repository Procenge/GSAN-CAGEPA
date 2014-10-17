<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gcom.util.ConstantesSistema" isELIgnored="false"%>
<%@page import="gcom.arrecadacao.pagamento.Pagamento"%>
<%@page import="gcom.arrecadacao.pagamento.PagamentoHistorico"%>
<%@page import="gcom.util.Util" isELIgnored="false"%>
<%@page import="gcom.arrecadacao.pagamento.GuiaPagamentoPrestacaoHistorico"%>
<%@page import="gcom.arrecadacao.pagamento.GuiaPagamentoPrestacao"%>
<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">

</script>

<style>
.fontePequena {
font-size: 11px;
face: Verdana, Arial, Helvetica, sans-serif;
}

</style>

</head>

<body leftmargin="5" topmargin="5">

<html:form action="/exibirConsultarPagamentoAction" method="post">

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
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Consultar Pagamentos do Cliente</td>
					<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0" style="fonteReduzida">
				<tr>
					<td width="25%"><strong>C�digo do Cliente:</strong></td>
					<td>
					<div align="left"><html:text property="idCliente" readonly="true"
						style="background-color:#EFEFEF; border:0; font-size:9px;"
						size="10" maxlength="10" /></div>
					</td>
				</tr>
				<tr>
					<td width="25%"><strong>Nome do Cliente:</strong></td>
					<td>
					<div align="left"><html:text property="nomeCliente" readonly="true"
						style="background-color:#EFEFEF; border:0; font-size:9px;"
						size="50" maxlength="45" /></div>
					</td>
				</tr>
				<tr>
					<td><strong>CPF/CNPJ:</strong></td>
					<td><html:text property="cpfCnpj" readonly="true"
						style="background-color:#EFEFEF; border:0; font-size:9px;"
						size="20" maxlength="17" />
					</td>
				</tr>
				<tr>
					<td><strong>Profiss&atilde;o:</strong></td>
					<td>
					 <div align="left"><html:text property="profissao" readonly="true"
						style="background-color:#EFEFEF; border:0; font-size:9px"
						size="30" maxlength="30" />
					 </div>
					</td>
				</tr>
				<tr>
					<td><strong>Ramo de Atividade:</strong></td>
					<td><html:text property="ramoAtividade" readonly="true"
						style="background-color:#EFEFEF; border:0; font-size:9px;"
						size="20" maxlength="20" />
					</td>
				</tr>

				<tr>
					<td colspan="4">
					<table width="100%" bgcolor="#99CCFF" class="fontePequena">
						<tr bordercolor="#FFFFFF" bgcolor="#79BBFD">
							<td align="center"><strong>Endere&ccedil;o</strong></td>
						</tr>
						<tr>
							<logic:present name="clienteEndereco" scope="session">
								<td bgcolor="#ffffff">
								 <div align="center"><span id="endereco"> <bean:write
									name="clienteEndereco" property="enderecoFormatado"
									scope="session" /> </span>
								 </div>
								</td>
							</logic:present>
							<logic:notPresent name="clienteEndereco" scope="session">
								<td bgcolor="#ffffff">&nbsp;</td>
							</logic:notPresent>
						</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td><strong>Telefone para Contato:</strong></td>
					<td><html:text property="telefone" readonly="true"
						style="background-color:#EFEFEF; border:0; font-size:9px;"
						size="20" maxlength="15" />
					</td>
				</tr>

				<tr>
					<td colspan="4" height="10"></td>
				</tr>

				<tr>
					<td colspan="4">
					<table width="100%" align="center" bgcolor="#90c7fc" border="0"
						cellpadding="0" cellspacing="0" class="fontePequena">
						<tr bgcolor="#79bbfd" height="20">
							<td align="center"><strong>Pagamentos das Contas</strong></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td width="100%" align="center">
							<table width="100%" bgcolor="#90c7fc" class="fontePequena">
								<tr bordercolor="#000000">
									<td width="10%" bgcolor="#90c7fc" align="center" rowspan="2"><strong>Im�vel</strong></td>
									<td width="10%" bgcolor="#90c7fc" align="center" rowspan="2"><strong>M�s/Ano Conta</strong></td>
									<td width="13%" bgcolor="#90c7fc" align="center" rowspan="2"><strong>Valor da Conta</strong></td>
									<td width="13%" bgcolor="#90c7fc" align="center" rowspan="2"><strong>Valor do Pag.</strong></td>
									<td width="13%" bgcolor="#90c7fc" align="center" rowspan="2"><strong>Data do Pag.</strong></td>
									<td width="28%" bgcolor="#90c7fc" align="center" colspan="2"><strong>Situa��o</strong></td>
								</tr>
								<tr>
									<td width="14%" bgcolor="#cbe5fe" align="center"><strong>Anterior</strong></td>
									<td width="14%" bgcolor="#cbe5fe" align="center"><strong>Atual</strong></td>
								</tr>
								
								<%int cont = 1;%>

								<%if ((Integer) session.getAttribute("qtdePagContas") <= ConstantesSistema.NUMERO_MAXIMO_REGISTROS_CONSULTA_PAGAMENTO) {%>

								<logic:present name="colecaoPagamentosClienteConta" scope="request">
									<logic:iterate name="colecaoPagamentosClienteConta" id="pagamento" type="Pagamento">
										<%cont = cont + 1;
										
										if (cont % 2 == 0) {%>
										
										<tr bgcolor="#FFFFFF">
										
											<%} else {%>
										
										<tr bgcolor="#cbe5fe">
											<%}%>
											
											<td width="10%" align="center">${pagamento.imovel.id}</td>
											<td width="12%" align="center">
												
												<logic:notEmpty	name="pagamento" property="conta">
												 <logic:notEmpty name="pagamento" property="conta.id">
													<logic:notEmpty name="pagamento" property="conta.referencia">
														<a href="javascript:abrirPopup('exibirConsultarContaAction.do?tipoConsulta=conta&contaID=<%="" + pagamento.getConta().getId() %>' , 600, 800);">${pagamento.conta.formatarAnoMesParaMesAno}</a>
													</logic:notEmpty>
												 </logic:notEmpty>
											    </logic:notEmpty>
											    
											    <logic:empty name="pagamento" property="conta">
													${pagamento.formatarAnoMesPagamentoParaMesAno}
												</logic:empty>
											</td>
											<td width="12%" align="right">
												<bean:write name="pagamento" property="conta.valorTotal" formatKey="money.format" />&nbsp;
											</td>
											<td width="12%" align="right">
												<bean:write name="pagamento" property="valorPagamento" formatKey="money.format" />&nbsp;
											</td>
											
											<td width="12%" align="center">
												<a href="javascript:abrirPopup('exibirConsultarPagamentoPopupAction.do?idPagamento=${pagamento.id}' , 210, 510);">
													<bean:write name="pagamento" property="dataPagamento" formatKey="date.format" />
												</a>&nbsp;
											</td>
												
											<td width="14%">${pagamento.pagamentoSituacaoAnterior.descricaoAbreviada}&nbsp;</td>
											<td width="14%">${pagamento.pagamentoSituacaoAtual.descricaoAbreviada}&nbsp;
											</td>
										</tr>
									</logic:iterate>
								</logic:present>

								<!--  Historico  -->


								<logic:present name="colecaoPagamentosHistoricoClienteConta" scope="request">
									<%cont = 1;%>
									<logic:iterate name="colecaoPagamentosHistoricoClienteConta" id="pagamentoHistorico" type="PagamentoHistorico">
										<%cont = cont + 1;
										  
										if (cont % 2 == 0) {%>
										
										<tr bgcolor="#FFFFFF">
										
										<%} else {%>
										
										<tr bgcolor="#cbe5fe">
										
										<%}%>
											<td width="10%" align="center"><font color="#ff0000"> ${pagamentoHistorico.imovel.id} </font> &nbsp;</td>
											<td width="12%" align="center">
												<logic:present name="pagamentoHistorico" property="anoMesReferenciaPagamento">
													<font color="#ff0000">${pagamentoHistorico.formatarAnoMesReferenciaPagamento}</font>
												</logic:present>
											</td>
											<td width="12%" align="right">
												<font color="#ff0000"><bean:write name="pagamentoHistorico" property="conta.valorTotal" formatKey="money.format" /> </font> &nbsp;
											</td>
											<td width="12%" align="right">
												<font color="#ff0000"><bean:write name="pagamentoHistorico" property="valorPagamento" formatKey="money.format" /> </font> &nbsp;
											</td>
											
											<td width="12%" align="center">
												<a style="color: #FF0000;" href="javascript:abrirPopup('exibirConsultarPagamentoPopupAction.do?idPagamentoHistorico=${pagamentoHistorico.id}' , 210, 510);">
													<bean:write name="pagamentoHistorico" property="dataPagamento" formatKey="date.format" /> 
												</a>&nbsp;
											</td>
											
											<td width="14%">
												<font color="#ff0000">${pagamentoHistorico.pagamentoSituacaoAnterior.descricaoAbreviada}</font> &nbsp;
											</td>
											<td width="14%">
												<font color="#ff0000">${pagamentoHistorico.pagamentoSituacaoAtual.descricaoAbreviada}</font> &nbsp;
											</td>
										</tr>
									</logic:iterate>
								</logic:present>

								<%} else {%>
								
								<tr>
									<td height="190" colspan="7">
									  <div style="width: 100%; height: 100%; overflow: auto;">
										<table width="100%">

										<logic:present name="colecaoPagamentosClienteConta" scope="request">
											<logic:iterate name="colecaoPagamentosClienteConta" id="pagamento" type="Pagamento">
												<%cont = cont + 1;
												
												if (cont % 2 == 0) {%>
												
												<tr bgcolor="#FFFFFF">
												
												<%} else {%>
												
												<tr bgcolor="#cbe5fe">
												
												<%}%>
												
													<td width="10%" align="center">
														${pagamento.imovel.id}&nbsp;
													</td>
													<td width="12%" align="center">
														
														<logic:notEmpty name="pagamento" property="conta">
														<logic:notEmpty name="pagamento" property="conta.id">
															<logic:notEmpty name="pagamento" property="conta.referencia">
																<a href="javascript:abrirPopup('exibirConsultarContaAction.do?tipoConsulta=conta&contaID=<%="" + pagamento.getConta().getId() %>' , 600, 800);">${pagamento.conta.formatarAnoMesParaMesAno}</a>
															</logic:notEmpty>
														</logic:notEmpty>
														</logic:notEmpty>
														
														<logic:empty name="pagamento" property="conta">
															${pagamento.formatarAnoMesPagamentoParaMesAno}
														</logic:empty>
													</td>
													<td width="12%" align="right">
														<bean:write name="pagamento" property="conta.valorTotal" formatKey="money.format" />&nbsp;
													</td>
													<td width="12%" align="right">
														<bean:write name="pagamento" property="valorPagamento" formatKey="money.format" />&nbsp;
													</td>
													
													<td width="14%" align="center">
														<a href="javascript:abrirPopup('exibirConsultarPagamentoPopupAction.do?idPagamento=${pagamento.id}' , 210, 510);">
															<bean:write name="pagamento" property="dataPagamento" formatKey="date.format" />
														</a>&nbsp;
													</td>
													
													<td width="14.5%">${pagamento.pagamentoSituacaoAnterior.descricaoAbreviada}&nbsp;</td>
													<td width="11.5%">${pagamento.pagamentoSituacaoAtual.descricaoAbreviada}&nbsp;</td>
												</tr>
											</logic:iterate>
										</logic:present>

										<!--  Historico  -->


										<logic:present name="colecaoPagamentosHistoricoClienteConta" scope="request">
											<%cont = 1;%>
											<logic:iterate name="colecaoPagamentosHistoricoClienteConta" id="pagamentoHistorico" type="PagamentoHistorico">
												<%cont = cont + 1;
												
												if (cont % 2 == 0) {%>
												
												<tr bgcolor="#FFFFFF">
												
												<%} else {%>
												
												<tr bgcolor="#cbe5fe">
												
												<%}%>
													<td width="10%" align="center">
														<font color="#ff0000">${pagamentoHistorico.imovel.id} </font> &nbsp;
													</td>
													<td width="12%" align="center">
														<logic:present name="pagamentoHistorico" property="anoMesReferenciaPagamento">
															<font color="#ff0000">${pagamentoHistorico.formatarAnoMesReferenciaPagamento}</font>
														</logic:present>
													</td>
													<td width="12%" align="right">
														<font color="#ff0000"><bean:write name="pagamentoHistorico" property="conta.valorTotal" formatKey="money.format" /> </font> &nbsp;
													</td>
													<td width="12%" align="right">
														<font color="#ff0000"> <bean:write name="pagamentoHistorico" property="valorPagamento" formatKey="money.format" /> </font> &nbsp;
													</td>
												
													<td width="14%" align="center">
														<a style="color: #FF0000;"
															href="javascript:abrirPopup('exibirConsultarPagamentoPopupAction.do?idPagamentoHistorico=${pagamentoHistorico.id}' , 210, 510);">
														 	<bean:write name="pagamentoHistorico" property="dataPagamento" formatKey="date.format" /> 
														</a>&nbsp;
													</td>
												
													<td width="14.5%">
														<font color="#ff0000">${pagamentoHistorico.pagamentoSituacaoAnterior.descricaoAbreviada}</font> &nbsp;
													</td>
													<td width="11.5%">
														<font color="#ff0000">${pagamentoHistorico.pagamentoSituacaoAtual.descricaoAbreviada}</font> &nbsp;
													</td>
												</tr>
											</logic:iterate>
										</logic:present>
									</table>
									</div>
									</td>
								</tr>

								<%}%>

							</table>
							</td>
						</tr>
					</table>
					</td>
				</tr>

				<%-- alterado por pedro alexandre dia 27/02/2007 --%>
				<tr>
					<td colspan="4">
					<table width="80%">
						<tr>
							<td>
								<div align="left"><strong>Total Atual</strong></div>
							</td>

							<td width="9%">
								<div align="right"><strong>Qtd -</strong></div>
							</td>
							<td width="10%">
								<div align="right"><bean:write name="qtdePagContasAtual" format="###,###" /></div>
							</td>

							<td width="38%">
								<div align="right"><strong>Valor do Pagamento -</strong></div>
							</td>
							<td width="22%">
								<div align="right"><bean:write name="vlPagContasAtual" formatKey="money.format" /></div>
							</td>
						</tr>
						<tr>
							<td>
								<div align="left"><strong>Total Hist&oacute;rico</strong></div>
							</td>
							<td>
								<div align="right"><strong>Qtd -</strong></div>
							</td>
							<td>
								<div align="right"><bean:write name="qtdePagContasHistorico" format="###,###" /></div>
							</td>
							<td>
								<div align="right"><strong>Valor do Pagamento -</strong></div>
							</td>
							<td>
								<div align="right"><bean:write name="vlPagContasHistorico" formatKey="money.format" /></div>
							</td>
						</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td>
					<hr>
					</td>
				</tr>
				<%-- fim altera��o ***************************** --%>

				<tr>
					<td colspan="4">
					<table width="100%" align="center" bgcolor="#90c7fc" border="0" cellpadding="0" cellspacing="0" class="fontePequena">
						<tr bgcolor="#79bbfd" height="20">
							<td align="center"><strong>Pagamentos das Guias de Pagamento</strong></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td width="100%" align="center">
							<table width="100%" bgcolor="#90c7fc" class="fontePequena">
								<tr bordercolor="#000000">
									<td width="10%" bgcolor="#90c7fc" align="center" rowspan="2"><strong>Im�vel</strong></td>
									<td width="10%" bgcolor="#90c7fc" align="center" rowspan="2"><strong>Cliente</strong></td>
									<td width="15%" bgcolor="#90c7fc" align="center" rowspan="2"><strong>Nr. Documento</strong></td>
									<td width="10%" bgcolor="#90c7fc" align="center" rowspan="2"><strong>Valor da Guia de Pagto.</strong></td>
									<td width="10%" bgcolor="#90c7fc" align="center" rowspan="2"><strong>Valor do Pag.</strong></td>
									<td width="9%" bgcolor="#90c7fc" align="center" rowspan="2"><strong>Data do Pag.</strong></td>
									<td width="28%" bgcolor="#90c7fc" align="center" colspan="2"><strong>Situa��o</strong>
									</td>
								</tr>
								<tr>
									<td width="14%" bgcolor="#cbe5fe" align="center"><strong>Anterior</strong></td>
									<td width="14%" bgcolor="#cbe5fe" align="center"><strong>Atual</strong></td>
								</tr>
								<%int contad = 1;%>

								<%if ((Integer) session.getAttribute("qtdePagGuiaPagamento") <= ConstantesSistema.NUMERO_MAXIMO_REGISTROS_CONSULTA_PAGAMENTO) {%>

								<logic:present name="colecaoPagamentosClienteGuiaPagamento" scope="request">
									<logic:iterate name="colecaoPagamentosClienteGuiaPagamento" id="pagamento" type="Pagamento">
										<%contad = contad + 1;
										
										if (contad % 2 == 0) {%>
										
										<tr bgcolor="#FFFFFF">
										
										<%} else {%>
										
										<tr bgcolor="#cbe5fe">
										
										<%}%>
										
											<td width="10%" align="center">${pagamento.imovel.id}&nbsp;</td>
											<td width="10%" align="center">${pagamento.cliente.id}&nbsp;</td>
											<td width="15%" align="center">
											
											<logic:present name="pagamento" property="guiaPagamentoGeral">
												<logic:notEmpty name="pagamento" property="guiaPagamentoGeral.guiaPagamento">
													<a href="javascript:abrirPopup('exibirConsultarGuiaPagamentoAction.do?guiaPagamentoId=<%="" + pagamento.getGuiaPagamentoGeral().getId() %>')">${pagamento.guiaPagamentoGeral.id}/${pagamento.numeroPrestacao}</a>&nbsp;
												</logic:notEmpty>
												<logic:notEmpty name="pagamento" property="guiaPagamentoGeral.guiaPagamentoHistorico">
													${pagamento.guiaPagamentoGeral.id}/${pagamento.numeroPrestacao}&nbsp;
												</logic:notEmpty>
											</logic:present>
											
											<logic:notPresent name="pagamento" property="guiaPagamentoGeral">
												${pagamento.debitoTipo.descricao}													
											</logic:notPresent>																						
											
											</td>

											<%--valor da guia de pagamento --%>
											<td width="10%" align="right">
											  <logic:notEmpty name="pagamento" property="guiaPagamentoGeral">
											  	<bean:define id="valorObtido" type="java.lang.String" value="nao" ></bean:define>
												<logic:notEmpty name="pagamento" property="guiaPagamentoGeral.guiaPagamento">
													<logic:notEmpty name="pagamento" property="guiaPagamentoGeral.guiaPagamento.guiasPagamentoPrestacao">
														<logic:iterate name="pagamento" property="guiaPagamentoGeral.guiaPagamento.guiasPagamentoPrestacao" id="prestacoes">
														     <% if ((((GuiaPagamentoPrestacao) prestacoes).getComp_id().getNumeroPrestacao().intValue() == pagamento.getNumeroPrestacao().intValue()) && "nao".equals(valorObtido)) { %>
														     <%=	pagamento.getGuiaPagamentoGeral().getGuiaPagamento().obterValorFormatadoPrestacaoGuia(pagamento.getNumeroPrestacao().shortValue()) %>
														     <% valorObtido = "sim";
														     	} %>									    		
														</logic:iterate>																
													</logic:notEmpty>
													<logic:notEmpty name="pagamento" property="guiaPagamentoGeral.guiaPagamento.guiasPagamentoPrestacaoHistorico">
														<logic:iterate name="pagamento" property="guiaPagamentoGeral.guiaPagamento.guiasPagamentoPrestacaoHistorico" id="prestacoesHistorico">
															    <% if ((((GuiaPagamentoPrestacaoHistorico) prestacoesHistorico).getComp_id().getNumeroPrestacao().intValue() == pagamento.getNumeroPrestacao().intValue()) && "nao".equals(valorObtido)) { %>
														    	 <%=	pagamento.getGuiaPagamentoGeral().getGuiaPagamento().obterValorFormatadoPrestacaoGuiaHistorico(pagamento.getNumeroPrestacao().shortValue()) %>
																<% valorObtido = "sim";
																  } %>													   
														</logic:iterate>																
													</logic:notEmpty>
												</logic:notEmpty>
												<logic:notEmpty name="pagamento" property="guiaPagamentoGeral.guiaPagamentoHistorico">
													<logic:iterate name="pagamento" property="guiaPagamentoGeral.guiaPagamentoHistorico.guiasPagamentoPrestacaoHistorico" id="prestacoesHistorico">
														<% if ((((GuiaPagamentoPrestacaoHistorico) prestacoesHistorico).getComp_id().getNumeroPrestacao().intValue() == pagamento.getNumeroPrestacao().intValue()) && "nao".equals(valorObtido)) { %>
													   	<%=	pagamento.getGuiaPagamentoGeral().getGuiaPagamentoHistorico().obterValorFormatadoPrestacaoGuiaHistorico(pagamento.getNumeroPrestacao().shortValue()) %>
		     											<% valorObtido = "sim";
		     												} %>
													</logic:iterate>
												</logic:notEmpty>
											  </logic:notEmpty>	
											</td>
											<%--fim valor da guia de pagamento --%>

											<td width="10%" align="right">
												<bean:write name="pagamento" property="valorPagamento" formatKey="money.format" />&nbsp;
											</td>
											<td width="9%">
												<a href="javascript:abrirPopup('exibirConsultarPagamentoPopupAction.do?idPagamento=${pagamento.id}' , 210, 510);">
													<bean:write name="pagamento" property="dataPagamento" formatKey="date.format" />
												</a>
											</td>
											<td width="14%">
												${pagamento.pagamentoSituacaoAnterior.descricaoAbreviada}&nbsp;
											</td>
											<td width="14%">
												${pagamento.pagamentoSituacaoAtual.descricaoAbreviada}&nbsp;
											</td>
										</tr>
									</logic:iterate>
								</logic:present>

								<!-- Historico -->

								<logic:present name="colecaoPagamentosHistoricoClienteGuiaPagamento" scope="request">
									<logic:iterate name="colecaoPagamentosHistoricoClienteGuiaPagamento" id="pagamentoHistorico" type="PagamentoHistorico">
										<%contad = contad + 1;
										
										if (contad % 2 == 0) {%>
										
										<tr bgcolor="#FFFFFF">
										
										<%} else {%>
										
										<tr bgcolor="#cbe5fe">
										
										<%}%>
										
											<td width="10%" align="center">
												<font color="#ff0000">${pagamentoHistorico.imovel.id} </font> &nbsp;
											</td>

											<td width="10%" align="center">
												<font color="#ff0000">${pagamentoHistorico.cliente.id} </font> &nbsp;
											</td>

											<td width="15%" align="center">
											 <logic:notEmpty name="pagamentoHistorico" property="guiaPagamentoGeral"> 
											 	<logic:notEmpty name="pagamentoHistorico" property="guiaPagamentoGeral.guiaPagamento">
														<a href="javascript:abrirPopup('exibirConsultarGuiaPagamentoAction.do?guiaPagamentoId=<%="" + pagamentoHistorico.getGuiaPagamentoGeral().getId() %>')"><font
															color="#ff0000">${pagamentoHistorico.guiaPagamentoGeral.id}/${pagamentoHistorico.numeroPrestacao}</font></a>&nbsp;
											 	</logic:notEmpty>
											 	<logic:notEmpty name="pagamentoHistorico" property="guiaPagamentoGeral.guiaPagamentoHistorico">
											 	  		<font color="#ff0000">${pagamentoHistorico.guiaPagamentoGeral.id}/${pagamentoHistorico.numeroPrestacao}</font>&nbsp;
											 	</logic:notEmpty>
											 </logic:notEmpty>	
											 <logic:notPresent name="pagamento" property="guiaPagamentoGeral">
												<font color="#ff0000">${pagamentoHistorico.debitoTipo.descricao}</font>													
											 </logic:notPresent>
											</td>

											<td width="10%" align="right">
												<logic:notEmpty name="pagamentoHistorico" property="guiaPagamentoGeral">
													<bean:define id="valorObtido" type="java.lang.String" value="nao" ></bean:define>
													<logic:notEmpty name="pagamentoHistorico" property="guiaPagamentoGeral.guiaPagamento">
														<logic:notEmpty name="pagamentoHistorico" property="guiaPagamentoGeral.guiaPagamento.guiasPagamentoPrestacao">
															<logic:iterate name="pagamentoHistorico" property="guiaPagamentoGeral.guiaPagamento.guiasPagamentoPrestacao" id="prestacoes">
																<% if ((((GuiaPagamentoPrestacao) prestacoes).getComp_id().getNumeroPrestacao().intValue() == pagamentoHistorico.getNumeroPrestacao().intValue()) && "nao".equals(valorObtido)) { %>
															     <%=	pagamentoHistorico.getGuiaPagamentoGeral().getGuiaPagamento().obterValorFormatadoPrestacaoGuia(pagamentoHistorico.getNumeroPrestacao().shortValue()) %>
			     												<% valorObtido = "sim";
			     													} %>
															</logic:iterate>														
														</logic:notEmpty>
														<logic:notEmpty name="pagamentoHistorico" property="guiaPagamentoGeral.guiaPagamento.guiasPagamentoPrestacaoHistorico">
															<logic:iterate name="pagamentoHistorico" property="guiaPagamentoGeral.guiaPagamento.guiasPagamentoPrestacaoHistorico" id="prestacoesHistorico">
																<% if ((((GuiaPagamentoPrestacaoHistorico) prestacoesHistorico).getComp_id().getNumeroPrestacao().intValue() == pagamentoHistorico.getNumeroPrestacao().intValue()) && "nao".equals(valorObtido)) { %>
															     <%=	pagamentoHistorico.getGuiaPagamentoGeral().getGuiaPagamento().obterValorFormatadoPrestacaoGuiaHistorico(pagamentoHistorico.getNumeroPrestacao().shortValue()) %>
			     												<% valorObtido = "sim";
			     													} %>
															</logic:iterate>														
														</logic:notEmpty>
													</logic:notEmpty>
													<logic:notEmpty name="pagamentoHistorico" property="guiaPagamentoGeral.guiaPagamentoHistorico">														
														<font color="#ff0000">
															<logic:iterate name="pagamentoHistorico" property="guiaPagamentoGeral.guiaPagamentoHistorico.guiasPagamentoPrestacaoHistorico" id="prestacoesHistorico">
															<% if ((((GuiaPagamentoPrestacaoHistorico) prestacoesHistorico).getComp_id().getNumeroPrestacao().intValue() == pagamentoHistorico.getNumeroPrestacao().intValue()) && "nao".equals(valorObtido)) { %>
														     <%=	pagamentoHistorico.getGuiaPagamentoGeral().getGuiaPagamentoHistorico().obterValorFormatadoPrestacaoGuiaHistorico(pagamentoHistorico.getNumeroPrestacao().shortValue()) %>
		     												<% valorObtido = "sim";
		     													} %>
															</logic:iterate>																
														</font>&nbsp;														
													</logic:notEmpty>
												</logic:notEmpty>	
											</td>

											<td width="10%" align="right"><font color="#ff0000"> <bean:write
												name="pagamentoHistorico" property="valorPagamento"
												formatKey="money.format" /> </font> &nbsp;
											</td>

											<td width="9%">
												<a style="color: #FF0000;"
													href="javascript:abrirPopup('exibirConsultarPagamentoPopupAction.do?idPagamentoHistorico=${pagamentoHistorico.id}' , 210, 510);">
												 <bean:write name="pagamentoHistorico" property="dataPagamento" formatKey="date.format" />  
												 </a>&nbsp;
											</td>
												
											<td width="14%">
												<font color="#ff0000">${pagamentoHistorico.pagamentoSituacaoAnterior.descricaoAbreviada}</font> &nbsp;
											</td>
											<td width="14%">
												<font color="#ff0000">${pagamentoHistorico.pagamentoSituacaoAtual.descricaoAbreviada}</font> &nbsp;
											</td>
										</tr>
									</logic:iterate>
								</logic:present>

								<%} else {%>
								<tr>
									<td height="190" colspan="8">
									<div style="width: 100%; height: 100%; overflow: auto;">
									<table width="100%">

										<logic:present name="colecaoPagamentosClienteGuiaPagamento"
											scope="request">
											<logic:iterate name="colecaoPagamentosClienteGuiaPagamento"
												id="pagamento" type="Pagamento">
												<%contad = contad + 1;
				if (contad % 2 == 0) {%>
												<tr bgcolor="#FFFFFF">
													<%} else {%>
												<tr bgcolor="#cbe5fe">
													<%}%>
													<td width="10%" align="center"><logic:present
														name="pagamento" property="imovel">
																			${pagamento.imovel.id}
																		</logic:present> <logic:notPresent name="pagamento"
														property="imovel">
																			&nbsp;
																		</logic:notPresent></td>
													<td width="10%" align="center">
													${pagamento.cliente.id}&nbsp;</td>

													<td width="15%" align="center">
													  <logic:notEmpty name="pagamento" property="guiaPagamentoGeral">
													 	<logic:notEmpty name="pagamento" property="guiaPagamentoGeral.guiaPagamento">
															<a href="javascript:abrirPopup('exibirConsultarGuiaPagamentoAction.do?guiaPagamentoId=<%="" + pagamento.getGuiaPagamentoGeral().getId() %>')"><font
																	color="#ff0000">${pagamento.guiaPagamentoGeral.id}/${pagamento.numeroPrestacao}</font>
															</a>&nbsp;
														</logic:notEmpty> 
														<logic:notEmpty name="pagamento" property="guiaPagamentoGeral.guiaPagamentoHistorico">
															<font color="#ff0000">${pagamento.guiaPagamentoGeral.id}/${pagamento.numeroPrestacao}</font>&nbsp;
														</logic:notEmpty>
													  </logic:notEmpty>
													<logic:empty name="pagamento" property="guiaPagamentoGeral">														
															<logic:notEmpty name="pagamento" property="debitoTipo">
																<logic:present name="pagamento" property="debitoTipo.descricao">
																		${pagamento.debitoTipo.descricao}
																</logic:present>
															</logic:notEmpty>														
													</logic:empty>
													
													</td>

													<td width="10%" align="right"><bean:write name="pagamento"
														property="valorPagamento" formatKey="money.format" />&nbsp;
													</td>
													<td width="10%" align="right">
													  <logic:notEmpty name="pagamento" property="guiaPagamentoGeral">
													  	<bean:define id="valorObtido" type="java.lang.String" value="nao" ></bean:define>
														<logic:notEmpty name="pagamento" property="guiaPagamentoGeral.guiaPagamento">
															<logic:notEmpty name="pagamento" property="guiaPagamentoGeral.guiaPagamento.guiasPagamentoPrestacao">
																<logic:iterate name="pagamento" property="guiaPagamentoGeral.guiaPagamento.guiasPagamentoPrestacao" id="prestacoes">
																     <% if ((((GuiaPagamentoPrestacao) prestacoes).getComp_id().getNumeroPrestacao().intValue() == pagamento.getNumeroPrestacao().intValue()) && "nao".equals(valorObtido)) { %>
																     <%=	pagamento.getGuiaPagamentoGeral().getGuiaPagamento().obterValorFormatadoPrestacaoGuia(pagamento.getNumeroPrestacao().shortValue()) %>
																     <% valorObtido = "sim";
																     	} %>									    		
																</logic:iterate>																
															</logic:notEmpty>
															<logic:notEmpty name="pagamento" property="guiaPagamentoGeral.guiaPagamento.guiasPagamentoPrestacaoHistorico">
																	<logic:iterate name="pagamento" property="guiaPagamentoGeral.guiaPagamento.guiasPagamentoPrestacaoHistorico" id="prestacoesHistorico">
																		    <% if ((((GuiaPagamentoPrestacaoHistorico) prestacoesHistorico).getComp_id().getNumeroPrestacao().intValue() == pagamento.getNumeroPrestacao().intValue()) && "nao".equals(valorObtido)) { %>
																	    	 <%=	pagamento.getGuiaPagamentoGeral().getGuiaPagamento().obterValorFormatadoPrestacaoGuiaHistorico(pagamento.getNumeroPrestacao().shortValue()) %>
																			<% valorObtido = "sim";
																				} %>													   
																	</logic:iterate>																
															</logic:notEmpty>
														</logic:notEmpty>
														<logic:notEmpty name="pagamento" property="guiaPagamentoGeral.guiaPagamentoHistorico">
															<logic:iterate name="pagamento" property="guiaPagamentoGeral.guiaPagamentoHistorico.guiasPagamentoPrestacaoHistorico" id="prestacoesHistorico">
																<% if ((((GuiaPagamentoPrestacaoHistorico) prestacoesHistorico).getComp_id().getNumeroPrestacao().intValue() == pagamento.getNumeroPrestacao().intValue()) && "nao".equals(valorObtido)) { %>
													   			<%=	pagamento.getGuiaPagamentoGeral().getGuiaPagamentoHistorico().obterValorFormatadoPrestacaoGuiaHistorico(pagamento.getNumeroPrestacao().shortValue()) %>
		     													<% valorObtido = "sim";
		     														} %>
															</logic:iterate>
														</logic:notEmpty>
													  </logic:notEmpty>	
													</td>
													
													<td width="10.5%">
														<a 
														href="javascript:abrirPopup('exibirConsultarPagamentoPopupAction.do?idPagamento=${pagamento.id}' , 210, 510);">
														<bean:write name="pagamento" property="dataPagamento" formatKey="date.format" />
														</a>
													</td>
													
													<td width="15%">
													${pagamento.pagamentoSituacaoAnterior.descricaoAbreviada}&nbsp;
													</td>
													<td width="11.5%">
													${pagamento.pagamentoSituacaoAtual.descricaoAbreviada}&nbsp;
													</td>
												</tr>
											</logic:iterate>
										</logic:present>

										<!-- Historico -->

										<logic:present
											name="colecaoPagamentosHistoricoClienteGuiaPagamento"
											scope="request">
											<logic:iterate
												name="colecaoPagamentosHistoricoClienteGuiaPagamento"
												id="pagamentoHistorico" type="PagamentoHistorico">
												<%contad = contad + 1;
				if (contad % 2 == 0) {%>
												<tr bgcolor="#FFFFFF">
													<%} else {%>
												<tr bgcolor="#cbe5fe">
													<%}%>
													<td width="10%" align="center"><logic:present
														name="pagamentoHistorico" property="imovel">
														<font color="#ff0000"> ${pagamentoHistorico.imovel.id} </font>
													</logic:present> <logic:notPresent
														name="pagamentoHistorico" property="imovel">
																			&nbsp;
																		</logic:notPresent></td>
													<td width="10%" align="center"><font color="#ff0000">
													${pagamentoHistorico.cliente.id} </font> &nbsp;</td>
													
													<td width="15%" align="center">
														<logic:notEmpty name="pagamentoHistorico" property="guiaPagamentoGeral">
															<logic:notEmpty name="pagamentoHistorico" property="guiaPagamentoGeral.guiaPagamento">
																<a href="javascript:abrirPopup('exibirConsultarGuiaPagamentoAction.do?guiaPagamentoId=<%="" + pagamentoHistorico.getGuiaPagamentoGeral().getId() %>')">
																	<font color="#ff0000">${pagamentoHistorico.guiaPagamentoGeral.id}/${pagamentoHistorico.numeroPrestacao}</font></a>&nbsp;
															</logic:notEmpty>	
															<logic:notEmpty name="pagamentoHistorico" property="guiaPagamentoGeral.guiaPagamentoHistorico">																
																	<font color="#ff0000">${pagamentoHistorico.guiaPagamentoGeral.id}/${pagamentoHistorico.numeroPrestacao}</font>&nbsp;
															</logic:notEmpty>		
														</logic:notEmpty>
														<logic:notPresent name="pagamentoHistorico" property="guiaPagamentoGeral">
															${pagamentoHistorico.debitoTipo.descricao}													
														</logic:notPresent>
													</td>

													<td width="10%" align="right"><font color="#ff0000"> <bean:write
														name="pagamentoHistorico" property="valorPagamento"
														formatKey="money.format" /> </font></td>
													<td width="10%" align="right">
													  <logic:notEmpty name="pagamentoHistorico" property="guiaPagamentoGeral">
													  	<bean:define id="valorObtido" type="java.lang.String" value="nao" ></bean:define>
													  	<font color="#ff0000">
														<logic:notEmpty name="pagamentoHistorico" property="guiaPagamentoGeral.guiaPagamento">
															<logic:notEmpty name="pagamentoHistorico" property="guiaPagamentoGeral.guiaPagamento.guiasPagamentoPrestacao">
																<logic:iterate name="pagamentoHistorico" property="guiaPagamentoGeral.guiaPagamento.guiasPagamentoPrestacao" id="prestacoes">
																	<% if ((((GuiaPagamentoPrestacao) prestacoes).getComp_id().getNumeroPrestacao().intValue() == pagamentoHistorico.getNumeroPrestacao().intValue()) && "nao".equals(valorObtido)) { %>
																     <%=	pagamentoHistorico.getGuiaPagamentoGeral().getGuiaPagamento().obterValorFormatadoPrestacaoGuia(pagamentoHistorico.getNumeroPrestacao().shortValue()) %>
			    		 											<% valorObtido = "sim";
			    		 												} %>
																</logic:iterate>														
															</logic:notEmpty>
															<logic:notEmpty name="pagamentoHistorico" property="guiaPagamentoGeral.guiaPagamento.guiasPagamentoPrestacaoHistorico">
																<logic:iterate name="pagamentoHistorico" property="guiaPagamentoGeral.guiaPagamento.guiasPagamentoPrestacaoHistorico" id="prestacoesHistorico">
																	<% if ((((GuiaPagamentoPrestacaoHistorico) prestacoesHistorico).getComp_id().getNumeroPrestacao().intValue() == pagamentoHistorico.getNumeroPrestacao().intValue()) && "nao".equals(valorObtido)) { %>
																     <%=	pagamentoHistorico.getGuiaPagamentoGeral().getGuiaPagamento().obterValorFormatadoPrestacaoGuiaHistorico(pagamentoHistorico.getNumeroPrestacao().shortValue()) %>
			    		 											<% valorObtido = "sim";
			    		 												} %>
																</logic:iterate>														
															</logic:notEmpty>
														</logic:notEmpty> 
														<logic:notEmpty name="pagamentoHistorico" property="guiaPagamentoGeral.guiaPagamentoHistorico">
															<logic:iterate name="pagamentoHistorico" property="guiaPagamentoGeral.guiaPagamentoHistorico.guiasPagamentoPrestacaoHistorico" id="prestacoesHistorico">
																<% if ((((GuiaPagamentoPrestacaoHistorico) prestacoesHistorico).getComp_id().getNumeroPrestacao().intValue() == pagamentoHistorico.getNumeroPrestacao().intValue()) && "nao".equals(valorObtido)) { %>
														    		<%=	pagamentoHistorico.getGuiaPagamentoGeral().getGuiaPagamentoHistorico().obterValorFormatadoPrestacaoGuiaHistorico(pagamentoHistorico.getNumeroPrestacao().shortValue()) %>
		     													<% valorObtido = "sim";
		     														} %>
															</logic:iterate>
														</logic:notEmpty> 
														</font>
													  </logic:notEmpty>	
													</td>
													
													<td width="10.9%"> 
														<a style="color: #FF0000;"
														href="javascript:abrirPopup('exibirConsultarPagamentoPopupAction.do?idPagamentoHistorico=${pagamentoHistorico.id}' , 210, 510);">
														<bean:write name="pagamentoHistorico" property="dataPagamento" formatKey="date.format" />
														</a>
													</td>
													
													<td width="14.6%"><font color="#ff0000">
													${pagamentoHistorico.pagamentoSituacaoAnterior.descricaoAbreviada}
													&nbsp; </font></td>
													<td width="11.5%"><font color="#ff0000">
													${pagamentoHistorico.pagamentoSituacaoAtual.descricaoAbreviada}
													&nbsp; </font></td>
												</tr>
											</logic:iterate>
										</logic:present>



									</table>
									</div>
									</td>
								</tr>

								<%}%>

							</table>
							</td>
						</tr>
					</table>
					</td>
				</tr>

				<%-- alterado por pedro alexandre dia 27/02/2007 --%>
				<tr>
					<td colspan="4">
					<table width="80%">
						<tr>
							<td>
							<div align="left"><strong>Total Atual</strong></div>
							</td>

							<td width="9%">
							<div align="right"><strong>Qtd -</strong></div>
							</td>
							<td width="10%">
							<div align="right"><bean:write name="qtdePagGuiaPagamentoAtual"
								format="###,###" /></div>
							</td>

							<td width="38%">
							<div align="right"><strong>Valor do Pagamento -</strong></div>
							</td>
							<td width="22%">
							<div align="right"><bean:write name="vlPagGuiaPagamentoAtual"
								formatKey="money.format" /></div>
							</td>
						</tr>
						<tr>
							<td>
							<div align="left"><strong>Total Hist&oacute;rico</strong></div>
							</td>

							<td>
							<div align="right"><strong>Qtd -</strong></div>
							</td>
							<td>
							<div align="right"><bean:write
								name="qtdePagGuiaPagamentoHistorico" format="###,###" /></div>
							</td>

							<td>
							<div align="right"><strong>Valor do Pagamento -</strong></div>
							</td>
							<td>
							<div align="right"><bean:write name="vlPagGuiaPagamentoHistorico"
								formatKey="money.format" /></div>
							</td>
						</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td>
					<hr>
					</td>
				</tr>
				<%-- fim altera��o ***************************** --%>

				<tr>
					<td colspan="4">
					<table width="100%" align="center" bgcolor="#90c7fc" border="0"
						cellpadding="0" cellspacing="0" class="fontePequena">
						<tr bgcolor="#79bbfd" height="20">
							<td align="center"><strong>Pagamentos dos D�bitos a Cobrar</strong>
							</td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td width="100%" align="center">
							<table width="100%" bgcolor="#90c7fc" class="fontePequena">
								<tr bordercolor="#000000">
									<td width="11%" bgcolor="#90c7fc" align="center" rowspan="2"><strong>Im�vel</strong>
									</td>
									<td width="16%" bgcolor="#90c7fc" align="center" rowspan="2"><strong>Tipo
									do D�bito</strong></td>
									<td width="12%" bgcolor="#90c7fc" align="center" rowspan="2"><strong>Valor
									a Ser Cobrado</strong></td>
									<td width="12%" bgcolor="#90c7fc" align="center" rowspan="2"><strong>Valor
									do Pag.</strong></td>
									<td width="12%" bgcolor="#90c7fc" align="center" rowspan="2"><strong>Data
									do Pag.</strong></td>
									<td width="28%" bgcolor="#90c7fc" align="center" colspan="2"><strong>Situa��o</strong>
									</td>
								</tr>
								<tr>
									<td width="14%" bgcolor="#cbe5fe" align="center"><strong>Anterior</strong></td>
									<td width="14%" bgcolor="#cbe5fe" align="center"><strong>Atual</strong></td>
								</tr>
								<%int contador = 1;%>

								<%-- Esquema de Pagina��o --%>
								<%if ((Integer) session.getAttribute("qtdePagDebitoACobrar") <= ConstantesSistema.NUMERO_MAXIMO_REGISTROS_CONSULTA_PAGAMENTO) {%>

								<logic:present name="colecaoPagamentosClienteDebitoACobrar"
									scope="request">
									<logic:iterate name="colecaoPagamentosClienteDebitoACobrar"
										id="pagamento" type="Pagamento">
										<%contador = contador + 1;
				if (contador % 2 == 0) {%>
										<tr bgcolor="#FFFFFF">
											<%} else {

				%>
										<tr bgcolor="#cbe5fe">
											<%}%>
											<td width="11%" align="center"><logic:notEmpty
												name="pagamento" property="imovel">
													${pagamento.imovel.id}&nbsp;
												</logic:notEmpty></td>

											<td width="16%" align="center"><logic:notEmpty
												name="pagamento" property="debitoACobrar">
												<logic:notEmpty name="pagamento"
													property="debitoACobrar.debitoTipo.descricao">
													<a
														href="javascript:abrirPopup('exibirConsultarDebitoACobrarAction.do?imovelID=<bean:write name="pagamento" property="debitoACobrar.imovel.id" />&debitoID=<bean:write name="pagamento" property="debitoACobrar.id" />', 560,660);">${pagamento.debitoACobrar.debitoTipo.descricao}&nbsp;</a>
												</logic:notEmpty>
											</logic:notEmpty><logic:empty name="pagamento"
												property="debitoACobrar">${pagamento.debitoTipo.descricao} </logic:empty></td>

											<td width="12%" align="right"><logic:notEmpty
												name="pagamento"
												property="debitoACobrar.valorRestanteCobrado">
												<bean:write name="pagamento"
													property="debitoACobrar.valorRestanteCobrado"
													formatKey="money.format" />&nbsp;
												</logic:notEmpty></td>
											<td width="12%" align="right"><bean:write name="pagamento"
												property="valorPagamento" formatKey="money.format" />&nbsp;
											</td>
											
											<td width="12%" align="center">
												<a 
														href="javascript:abrirPopup('exibirConsultarPagamentoPopupAction.do?idPagamento=${pagamento.id}' , 210, 510);">
												<bean:write name="pagamento" property="dataPagamento" formatKey="date.format" />
												</a>	
											&nbsp;</td>
												
											<td width="14%">
											${pagamento.pagamentoSituacaoAnterior.descricaoAbreviada}&nbsp;
											</td>
											<td width="14%">
											${pagamento.pagamentoSituacaoAtual.descricaoAbreviada}&nbsp;
											</td>
										</tr>
									</logic:iterate>
								</logic:present>


								<!-- Historico -->

								<logic:present
									name="colecaoPagamentosHistoricoClienteDebitoACobrar"
									scope="request">
									<logic:iterate
										name="colecaoPagamentosHistoricoClienteDebitoACobrar"
										id="pagamentoHistorico" type="PagamentoHistorico">
										<%contador = contador + 1;
				if (contador % 2 == 0) {%>
										<tr bgcolor="#FFFFFF">
											<%} else {

				%>
										<tr bgcolor="#cbe5fe">
											<%}%>
											<td width="11%" align="center"><logic:notEmpty
												name="pagamentoHistorico" property="imovel">
												<font color="#ff0000"> ${pagamentoHistorico.imovel.id} </font>
													&nbsp;
												</logic:notEmpty></td>

											<td width="16%" align="center"><logic:notEmpty
												name="pagamentoHistorico" property="debitoACobrar">
												<logic:notEmpty name="pagamentoHistorico"
													property="debitoACobrar.debitoTipo.descricao">
													<a
														href="javascript:abrirPopup('exibirConsultarDebitoACobrarAction.do?imovelID=<bean:write name="pagamentoHistorico" property="debitoACobrar.imovel.id" />&debitoID=<bean:write name="pagamentoHistorico" property="debitoACobrar.id" />', 560,660);"><font
														color="#ff0000">${pagamentoHistorico.debitoACobrar.debitoTipo.descricao}</font>&nbsp;</a>
												</logic:notEmpty>
											</logic:notEmpty><logic:empty name="pagamentoHistorico"
												property="debitoACobrar">${pagamentoHistorico.debitoTipo.descricao} </logic:empty></td>

											<td width="12%" align="right"><logic:notEmpty
												name="pagamentoHistorico" property="debitoACobrar">
												<logic:notEmpty name="pagamentoHistorico"
													property="debitoACobrar.valorRestanteCobrado">

													<font color="#ff0000"> <bean:write
														name="pagamentoHistorico"
														property="debitoACobrar.valorRestanteCobrado"
														formatKey="money.format" /> </font>	
															&nbsp;
												</logic:notEmpty>
											</logic:notEmpty></td>
											<td width="12%" align="right"><font color="#ff0000"> <bean:write
												name="pagamentoHistorico" property="valorPagamento"
												formatKey="money.format" /> </font> &nbsp;</td>
										
											<td width="12%" align="center">
												<a style="color: #FF0000;"
													href="javascript:abrirPopup('exibirConsultarPagamentoPopupAction.do?idPagamentoHistorico=${pagamentoHistorico.id}' , 210, 510);">
												 <bean:write name="pagamentoHistorico" property="dataPagamento" formatKey="date.format" />
												</a>	
											&nbsp;</td>
											
											<td width="14%"><font color="#ff0000">
											${pagamentoHistorico.pagamentoSituacaoAnterior.descricaoAbreviada}
											</font> &nbsp;</td>
											<td width="14%"><font color="#ff0000">
											${pagamentoHistorico.pagamentoSituacaoAtual.descricaoAbreviada}
											</font> &nbsp;</td>
										</tr>
									</logic:iterate>
								</logic:present>


								<%} else {%>
								<tr>
									<td height="190" colspan="7">
									<div style="width: 100%; height: 100%; overflow: auto;">
									<table width="100%">

										<logic:present name="colecaoPagamentosClienteDebitoACobrar"
											scope="request">
											<logic:iterate name="colecaoPagamentosClienteDebitoACobrar"
												id="pagamento" type="Pagamento">
												<%contador = contador + 1;
				if (contador % 2 == 0) {%>
												<tr bgcolor="#FFFFFF">
													<%} else {

				%>
												<tr bgcolor="#cbe5fe">
													<%}%>
													<td width="11%" align="center"><logic:notEmpty
														name="pagamento" property="imovel">
																		${pagamento.imovel.id}&nbsp;
																	</logic:notEmpty></td>

													<td width="16%" align="center"><logic:notEmpty
														name="pagamento" property="debitoACobrar">
														<logic:notEmpty name="pagamento"
															property="debitoACobrar.debitoTipo.descricao">
															<a
																href="javascript:abrirPopup('exibirConsultarDebitoACobrarAction.do?imovelID=<bean:write name="pagamento" property="debitoACobrar.imovel.id" />&debitoID=<bean:write name="pagamento" property="debitoACobrar.id" />', 560,660);">${pagamento.debitoACobrar.debitoTipo.descricao}&nbsp;</a>
														</logic:notEmpty>
													</logic:notEmpty><logic:empty name="pagamento"
														property="debitoACobrar">${pagamento.debitoTipo.descricao} </logic:empty></td>

													<td width="12%" align="right"><logic:notEmpty
														name="pagamento" property="debitoACobrar">
														<logic:notEmpty name="pagamento"
															property="debitoACobrar.valorRestanteCobrado">
															<bean:write name="pagamento"
																property="debitoACobrar.valorRestanteCobrado"
																formatKey="money.format" />&nbsp;
																	</logic:notEmpty>
													</logic:notEmpty></td>
													<td width="12%" align="right"><bean:write name="pagamento"
														property="valorPagamento" formatKey="money.format" />&nbsp;
													</td>
													
													<td width="14%" align="center">
														<a 
														href="javascript:abrirPopup('exibirConsultarPagamentoPopupAction.do?idPagamento=${pagamento.id}' , 210, 510);">
														<bean:write name="pagamento" property="dataPagamento" formatKey="date.format" />
														</a>
													&nbsp; </td>
													
													<td width="14.5%">
													${pagamento.pagamentoSituacaoAnterior.descricaoAbreviada}&nbsp;
													</td>
													<td width="11.5%">
													${pagamento.pagamentoSituacaoAtual.descricaoAbreviada}&nbsp;
													</td>
												</tr>
											</logic:iterate>
										</logic:present>


										<!-- Historico -->

										<logic:present
											name="colecaoPagamentosHistoricoClienteDebitoACobrar"
											scope="request">
											<logic:iterate
												name="colecaoPagamentosHistoricoClienteDebitoACobrar"
												id="pagamentoHistorico" type="PagamentoHistorico">
												<%contador = contador + 1;
				if (contador % 2 == 0) {%>
												<tr bgcolor="#FFFFFF">
													<%} else {

				%>
												<tr bgcolor="#cbe5fe">
													<%}%>
													<td width="11%" align="center"><logic:notEmpty
														name="pagamentoHistorico" property="imovel">
														<font color="#ff0000"> ${pagamentoHistorico.imovel.id} </font>
																		&nbsp;
																	</logic:notEmpty></td>

													<td width="16%" align="center"><logic:notEmpty
														name="pagamentoHistorico" property="debitoACobrar">
														<logic:notEmpty name="pagamentoHistorico"
															property="debitoACobrar.debitoTipo.descricao">
															<a
																href="javascript:abrirPopup('exibirConsultarDebitoACobrarAction.do?imovelID=<bean:write name="pagamentoHistorico" property="debitoACobrar.imovel.id" />&debitoID=<bean:write name="pagamentoHistorico" property="debitoACobrar.id" />', 560,660);"><font
																color="#ff0000">${pagamentoHistorico.debitoACobrar.debitoTipo.descricao}</font></a>
														</logic:notEmpty>
													</logic:notEmpty><logic:empty name="pagamentoHistorico"
														property="debitoACobrar">${pagamentoHistorico.debitoTipo.descricao} </logic:empty></td>

													<td width="12%" align="right"><logic:notEmpty
														name="pagamentoHistorico" property="debitoACobrar">
														<logic:notEmpty name="pagamentoHistorico"
															property="debitoACobrar.valorRestanteCobrado">

															<font color="#ff0000"> <bean:write
																name="pagamentoHistorico"
																property="debitoACobrar.valorRestanteCobrado"
																formatKey="money.format" /> </font>	
																				&nbsp;
																	</logic:notEmpty>
													</logic:notEmpty></td>
													<td width="12%" align="right"><font color="#ff0000"> <bean:write
														name="pagamentoHistorico" property="valorPagamento"
														formatKey="money.format" /> </font> &nbsp;</td>
													
													<td width="14%" align="center">
														<a style="color: #FF0000;"
														href="javascript:abrirPopup('exibirConsultarPagamentoPopupAction.do?idPagamentoHistorico=${pagamentoHistorico.id}' , 210, 510);">
														 <bean:write name="pagamentoHistorico" property="dataPagamento" formatKey="date.format" /> 
														 </a>
													&nbsp;</td>
													
													<td width="14.5%"><font color="#ff0000">
													${pagamentoHistorico.pagamentoSituacaoAnterior.descricaoAbreviada}
													</font> &nbsp;</td>
													<td width="11.5%"><font color="#ff0000">
													${pagamentoHistorico.pagamentoSituacaoAtual.descricaoAbreviada}
													</font> &nbsp;</td>
												</tr>
											</logic:iterate>
										</logic:present>


									</table>
									</div>
									</td>
								</tr>

								<%}%>


							</table>
							</td>
						</tr>
					</table>
					</td>
				</tr>

				<%-- alterado por pedro alexandre dia 27/02/2007 --%>
				<tr>
					<td colspan="4">
					<table width="80%">
						<tr>
							<td>
							<div align="left"><strong>Total Atual</strong></div>
							</td>

							<td width="9%">
							<div align="right"><strong>Qtd -</strong></div>
							</td>
							<td width="10%">
							<div align="right"><bean:write name="qtdePagDebitoACobrarAtual"
								format="###,###" /></div>
							</td>

							<td width="38%">
							<div align="right"><strong>Valor do Pagamento -</strong></div>
							</td>
							<td width="22%">
							<div align="right"><bean:write name="vlPagDebitoACobrarAtual"
								formatKey="money.format" /></div>
							</td>
						</tr>
						<tr>
							<td>
							<div align="left"><strong>Total Hist&oacute;rico</strong></div>
							</td>

							<td>
							<div align="right"><strong>Qtd -</strong></div>
							</td>
							<td>
							<div align="right"><bean:write
								name="qtdePagDebitoACobrarHistorico" format="###,###" /></div>
							</td>

							<td>
							<div align="right"><strong>Valor do Pagamento -</strong></div>
							</td>
							<td>
							<div align="right"><bean:write name="vlPagDebitoACobrarHistorico"
								formatKey="money.format" /></div>
							</td>
						</tr>
					</table>
					</td>
				</tr>

				<tr>
					<td>
					<hr>
					</td>
				</tr>
				<%-- fim altera��o ***************************** --%>

				<tr>
					<td colspan="4">
					<table width="100%" border="0">
						<tr>
							<td>	
								<input type="button" name="ButtonCancelar" class="bottonRightCol" value="Cancelar" onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
								<input type="button" name="ButtonReset" class="bottonRightCol" value="Voltar Filtro" onClick="javascript:history.back();">
								<logic:present name="colecaoPagamento" scope="session">
									<input value="Autenticar" class="bottonRightCol" type="button" onclick="javascript:abrirPopup('exibirAutenticarPagamentoAction.do' , 600, 800);">
								</logic:present>
							</td>
							
							<td align="right">
								<div align="right">
									<a href="javascript:toggleBox('demodiv',1);">
										<img border="0" src="<bean:message key="caminho.imagens"/>print.gif" title="Imprimir Pagamentos" /> 
									</a>
								</div>
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
</body>
</html:form>
</html:html>
