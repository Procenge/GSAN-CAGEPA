<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gsan" %>
<%@ page import="gcom.faturamento.bean.GuiaPagamentoHelper"%>
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
	src="<bean:message key="caminho.js"/>/validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
	
</head>

<body leftmargin="5" topmargin="5">

<html:form action="/exibirManterGuiaPagamentoAction"
	name="ManutencaoRegistroActionForm"
	type="gcom.gui.ManutencaoRegistroActionForm" method="post">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="130" valign="top" class="leftcoltext">
			<div align="center">
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>

			<%@ include file="/jsp/util/informacoes_usuario.jsp"%>

			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>

			<%@ include file="/jsp/util/mensagens.jsp"%>

			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			</div>
			</td>
			<td width="625" valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>

			<table>
				<tr>
					<td></td>

				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">Manter Guias de Pagamento</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>

				</tr>
			</table>

			<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="7" height="23"><font style="font-size: 10px;"
						color="#000000" face="Verdana, Arial, Helvetica, sans-serif"> <strong>Guia(s) de Pagamento
					cadastrado(s):</strong> </font></td>
				</tr>
				<tr>
					<!--<td colspan="4" bgcolor="#3399FF"> -->
					<td colspan="7" bgcolor="#000000" height="2" valign="baseline"></td>
				</tr>
				<tr>
					<td>
					<table width="100%" align="center" bgcolor="#90c7fc" border="0"
						cellpadding="0" cellspacing="0">
						<tr bgcolor="#cbe5fe">
							<td width="100%" align="center">
								<table width="100%" bgcolor="#90c7fc">
									<tr bordercolor="#FFFFFF" bgcolor="#79BBFD">
										
										<td >
											<div align="center"><strong>Número da Guia</strong></div>
										</td>
										
										<logic:present name="exibirNuContratoParcOrgaoPublico" scope="session">
											<td >
												<div align="center"><strong>No. Contrato Parcel. Órgão Público</strong></div>
											</td>
										</logic:present>
										
										<td>
											<div align="center"><strong>Imóvel</strong></div>
										</td>
										
										<td>
											<div align="center"><strong>Responsável</strong></div>
										</td>
										
										<td>
											<div align="center"><strong>Cliente</strong></div>
										</td>
										
										<td>
											<div align="center"><strong>Val. Total</strong></div>
										</td>
										
										<td>
											<div align="center"><strong>Val. Pago</strong></div>
										</td>
										
										<td>
											<div align="center"><strong>Val. Pendente</strong></div>
										</td>
										
										<td>
											<div align="center"><strong>Prest. Total</strong></div>
										</td>
										
										<td>
											<div align="center"><strong>Prest. Pago</strong></div>
										</td>
										
										<td>
											<div align="center"><strong>Prest. Pendente</strong></div>
										</td>
										
										<td>
											<div align="center"><strong>Numero RA</strong></div>
										</td>										
										
									</tr>
	
									<pg:pager isOffset="true" index="half-full" maxIndexPages="10"
										export="currentPageNumber=pageNumber;pageOffset"
										maxPageItems="10" items="${sessionScope.totalRegistros}">
	
										<pg:param name="pg" />
										<pg:param name="q" />
	
										<%int cont = 0;%>
										<logic:notEmpty name="colecaoGuiaPagamento">
											<logic:iterate name="colecaoGuiaPagamento" id="guiaPagamento"
												type="GuiaPagamentoHelper">
		
											<pg:item>
												<%cont = cont + 1;
												if (cont % 2 == 0) {%>
													<tr bgcolor="#cbe5fe">
												<%} else {%>
													<tr bgcolor="#FFFFFF">
												<%}%>
		
													<td align="center">
														<html:link
															href="/gsan/exibirAtualizarGuiaPagamentoAction.do?manter=sim"
															paramId="idRegistroAtualizacao" paramProperty="numeroGuia"
															paramName="guiaPagamento"
															title="Exibir Prestações da Guia">
															<bean:write name="guiaPagamento" property="numeroGuia"/>
														</html:link>
													</td>													
													
													<logic:present name="exibirNuContratoParcOrgaoPublico" scope="session">
														<td>
															<logic:notEmpty	name="guiaPagamento" property="numeroContratoParcelOrgaoPublico">
																<div align="center" title="${guiaPagamento.numeroContratoParcelOrgaoPublico}"><bean:write name="guiaPagamento" property="numeroContratoParcelOrgaoPublico"/></div>
															</logic:notEmpty>
															<logic:empty	name="guiaPagamento" property="numeroContratoParcelOrgaoPublico">
																<div align="center"></div>
															</logic:empty>
														</td>
													</logic:present>													
		
													<td>
														<logic:notEmpty	name="guiaPagamento" property="idImovel">
															<div align="center" title="${guiaPagamento.enderecoImovel}"><bean:write name="guiaPagamento" property="idImovel"/></div>
														</logic:notEmpty>
														<logic:empty	name="guiaPagamento" property="idImovel">
															<div align="center"></div>
														</logic:empty>
													</td>
													
													<td>
														<logic:notEmpty	name="guiaPagamento" property="idClienteResponsavel">
															<div align="center" title="${guiaPagamento.nomeClienteResponsavel}"><bean:write name="guiaPagamento" property="idClienteResponsavel"/></div>
														</logic:notEmpty>
														<logic:empty	name="guiaPagamento" property="idClienteResponsavel">
															<div align="center"></div>
														</logic:empty>
													</td>
														
													<td>
														<logic:notEmpty	name="guiaPagamento" property="idClienteGuia">
															<div align="center" title="${guiaPagamento.nomeClienteGuia}"><bean:write name="guiaPagamento" property="idClienteGuia"/></div>
														</logic:notEmpty>
														<logic:empty	name="guiaPagamento" property="idClienteGuia">
															<div align="center"></div>
														</logic:empty>
													</td>
													
													<td>
														<logic:notEmpty	name="guiaPagamento" property="valorDebitos">
															<div align="center"><bean:write name="guiaPagamento" property="valorDebitos" formatKey="money.format"/></div>
														</logic:notEmpty>
														<logic:empty	name="guiaPagamento" property="valorDebitos">
															<div align="center"></div>
														</logic:empty>
													</td>
													
													<td>
														<logic:notEmpty	name="guiaPagamento" property="valorPagamentos">
															<div align="center"><bean:write name="guiaPagamento" property="valorPagamentos" formatKey="money.format"/></div>
														</logic:notEmpty>
														<logic:empty	name="guiaPagamento" property="valorPagamentos">
															<div align="center"></div>
														</logic:empty>
													</td>
													
													<td>
														<logic:notEmpty	name="guiaPagamento" property="totalPendente">
															<div align="center"><bean:write name="guiaPagamento" property="totalPendente" formatKey="money.format"/></div>
														</logic:notEmpty>
														<logic:empty	name="guiaPagamento" property="totalPendente">
															<div align="center"></div>
														</logic:empty>
													</td>
													<td>
														<logic:notEmpty	name="guiaPagamento" property="numeroPrestacaoTotal">
															<div align="center"><bean:write name="guiaPagamento" property="numeroPrestacaoTotal"/></div>
														</logic:notEmpty>
														<logic:empty	name="guiaPagamento" property="numeroPrestacaoTotal">
															<div align="center"></div>
														</logic:empty>
													</td>
													
													<td>
														<logic:notEmpty	name="guiaPagamento" property="numeroPrestacaoPaga">
															<div align="center"><bean:write name="guiaPagamento" property="numeroPrestacaoPaga"/></div>
														</logic:notEmpty>
														<logic:empty	name="guiaPagamento" property="numeroPrestacaoPaga">
															<div align="center"></div>
														</logic:empty>
													</td>
													
													<td>
														<logic:notEmpty	name="guiaPagamento" property="numeroPrestacaoPendente">
															<div align="center"><bean:write name="guiaPagamento" property="numeroPrestacaoPendente"/></div>
														</logic:notEmpty>
														<logic:empty	name="guiaPagamento" property="numeroPrestacaoPendente">
															<div align="center"></div>
														</logic:empty>
													</td>
													
													<td>
														<logic:notEmpty	name="guiaPagamento" property="numeroRA">
															<div align="center"><bean:write name="guiaPagamento" property="numeroRA"/></div>
														</logic:notEmpty>
														<logic:empty	name="guiaPagamento" property="numeroRA">
															<div align="center"></div>
														</logic:empty>
													</td>													
													
												</pg:item>
											</logic:iterate>
										</logic:notEmpty>
								</table>
								<table width="100%" border="0">
									<tr>
										<td align="center"><strong><%@ include
											file="/jsp/util/indice_pager_novo.jsp"%></strong></td>
									</tr>
									</pg:pager>
									<tr>
										<td>
											<input name="button" 
												type="button"
												class="bottonRightCol" 
												value="Voltar Filtro"
												onclick="window.location.href='<html:rewrite page="/exibirFiltrarGuiaPagamentoAction.do?limpar=S"/>'"
												align="left" 
												style="width: 80px;">
										</td>
										<td valign="top">
										<div align="right"><a href="javascript:toggleBox('demodiv',1);">
										<img border="0"
											src="<bean:message key="caminho.imagens"/>print.gif"
											title="Imprimir Guia(s) de Pagamento" /> </a></div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					<p>&nbsp;</p>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<jsp:include
		page="/jsp/relatorio/escolher_tipo_relatorio.jsp?relatorio=gerarRelatorioGuiaPagamentoManterAction.do" />
	<%@ include file="/jsp/util/rodape.jsp"%>
	
</html:form>
</body>

<!--faturamento/guiapagamento/ guia_pagamento_manter.jsp -->
</html:html>
