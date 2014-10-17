<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>

<%@page import="java.util.Collection"%>
<%@page import="gcom.util.Util"%>
<%@page import="gcom.cobranca.bean.ImovelComDebitosPrescritosHelper"%>

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

</script>
</head>

<body leftmargin="5" topmargin="5">

<html:form action="/exibirAcompanharImovelComDebitosPrescritosAction"
	name="ManutencaoRegistroActionForm"
	type="gcom.gui.ManutencaoRegistroActionForm" method="post">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="130" valign="top" class="leftcoltext">
			<div align="center">

			<%@ include file="/jsp/util/informacoes_usuario.jsp"%>

			<%@ include file="/jsp/util/mensagens.jsp"%>

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
					<td class="parabg">Acompanhar Imóveis com Débitos Prescritos</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>
				</tr>
			</table>

			<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="7" height="23"><font style="font-size: 10px;"
						color="#000000" face="Verdana, Arial, Helvetica, sans-serif"> <strong>Dados dos Imóveis com Débitos Prescritos:</strong> </font></td>
				</tr>
				<tr>
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
									<td>
										<table width="100%">
											<tr bgcolor="#90c7fc">
												<td align="center" width="20%"><Font color="#000000"><strong>Imóvel</strong></font></td>
												<td align="center" width="40%"><Font color="#000000"><strong>Contas Prescritas</strong></font>
													<table width="100%" bgcolor="#90c7fc">
														<tr>
															<td align="center" width="50%"><Font color="#000000"><strong>Marcadas</strong></font>
																<table width="100%">
																	<tr>
																		<td align="center" width="50%"><Font color="#000000"><strong>Qtde.</strong></font></td>
																		<td align="center" width="50%"><Font color="#000000"><strong>Valor</strong></font></td>
																	</tr>
																</table>
															</td>
															<td align="center" width="50%"><Font color="#000000"><strong>Desmarcadas</strong></font>
																<table width="100%">
																	<tr>
																		<td align="center" width="50%"><Font color="#000000"><strong>Qtde.</strong></font></td>
																		<td align="center" width="50%"><Font color="#000000"><strong>Valor</strong></font></td>
																	</tr>
																</table>
															</td>
														</tr>
													</table>
												</td>
												<td align="center" width="40%"><Font color="#000000"><strong>Prestações de Guia Prescritas</strong></font>
													<table width="100%" bgcolor="#90c7fc">
														<tr>
															<td align="center" width="50%"><Font color="#000000"><strong>Marcadas</strong></font>
																<table width="100%">
																	<tr>
																		<td align="center" width="50%"><Font color="#000000"><strong>Qtde.</strong></font></td>
																		<td align="center" width="50%"><Font color="#000000"><strong>Valor</strong></font></td>
																	</tr>
																</table>
															</td>
															<td align="center" width="50%"><Font color="#000000"><strong>Desmarcadas</strong></font>
																<table width="100%">
																	<tr>
																		<td align="center" width="50%"><Font color="#000000"><strong>Qtde.</strong></font></td>
																		<td align="center" width="50%"><Font color="#000000"><strong>Valor</strong></font></td>
																	</tr>
																</table>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<pg:pager isOffset="true" index="half-full" maxIndexPages="10"
									export="currentPageNumber=pageNumber;pageOffset"
									maxPageItems="10" items="${sessionScope.totalRegistros}">
									<pg:param name="pg" />
									<pg:param name="q" />
									<%int cont = 0;%>
									<logic:iterate name="colecaoImovelComDebitosPrescritosHelper" id="imovelComDebitosPrescritosHelper" type="ImovelComDebitosPrescritosHelper">
										<pg:item>
											<%cont = cont + 1;
												if (cont % 2 == 0) {%>
											<tr bgcolor="#FFFFFF">
												<%} else {%>
											<tr bgcolor="#cbe5fe">
												<%}%>
												
												<td>
													<table width="100%">
														<tr>
															<td align="center" width="20%">
																<div align="center">
																	<html:link
																		href="/gsan/exibirApresentarItensDebitosPrescritosAction.do"
																		paramId="idImovelComDebitoPrescrito" paramProperty="idImovel"
																		paramName="imovelComDebitosPrescritosHelper">
																		<bean:write name="imovelComDebitosPrescritosHelper" property="idImovel" />
																	</html:link>
																</div>
															</td>
															<td align="center" width="40%">
																<table width="100%">
																	<tr>
																		<td align="center" width="50%">
																			<table width="100%">
																				<tr>
																					<td align="center" width="50%">
																						<bean:write name="imovelComDebitosPrescritosHelper" property="quantidadeContasMarcadas"/>
																					</td>
																					<td align="center" width="50%">
																						<bean:write name="imovelComDebitosPrescritosHelper" property="valorContasMarcadas" formatKey="money.format"/>
																					</td>
																				</tr>
																			</table>
																		</td>
																		<td align="center" width="50%">
																			<table width="100%">
																				<tr>
																					<td align="center" width="50%">
																						<bean:write name="imovelComDebitosPrescritosHelper" property="quantidadeContasDesmarcadas"/>
																					</td>
																					<td align="center" width="50%">
																						<bean:write name="imovelComDebitosPrescritosHelper" property="valorContasDesmarcadas" formatKey="money.format"/>
																					</td>
																				</tr>
																			</table>
																		</td>
																	</tr>
																</table>
															</td>
															<td align="center" width="40%">
																<table width="100%">
																	<tr>
																		<td align="center" width="50%">
																			<table width="100%">
																				<tr>
																					<td align="center" width="50%">
																						<bean:write name="imovelComDebitosPrescritosHelper" property="quantidadeGuiasPrestacaoMarcadas"/>
																					</td>
																					<td align="center" width="50%">
																						<bean:write name="imovelComDebitosPrescritosHelper" property="valorGuiasPrestacaoMarcadas" formatKey="money.format"/>
																					</td>
																				</tr>
																			</table>
																		</td>
																		<td align="center" width="50%">
																			<table width="100%">
																				<tr>
																					<td align="center" width="50%">
																						<bean:write name="imovelComDebitosPrescritosHelper" property="quantidadeGuiasPrestacaoDesmarcadas"/>
																					</td>
																					<td align="center" width="50%">
																						<bean:write name="imovelComDebitosPrescritosHelper" property="valorGuiasPrestacaoDesmarcadas" formatKey="money.format"/>
																					</td>
																				</tr>
																			</table>
																		</td>
																	</tr>
																</table>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</pg:item>
									</logic:iterate>
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
											onclick="window.location.href='<html:rewrite page="/exibirFiltrarImoveisComDebitosPrescritosAction.do"/>'"
											align="left" 
											style="width: 80px;">
									</td>
																		<td align="right" valign="top">
			                     		<a href="javascript:toggleBox('demodiv',1);">
			                             	<img align="right" border="0" src="<bean:message key='caminho.imagens'/>print.gif" title="Imprimir Relação de Imóveis com Débitos Prescritos"/>
			                             </a>
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
	
	<jsp:include page="/jsp/relatorio/escolher_tipo_relatorio.jsp?relatorio=gerarRelatorioAcompanhamentoDebitosPrescritosAction.do"/>
	
	<%@ include file="/jsp/util/rodape.jsp"%>

</html:form>
</body>
</html:html>