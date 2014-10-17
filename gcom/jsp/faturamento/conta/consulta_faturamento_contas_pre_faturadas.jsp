<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript">
<!-- Begin
  
-->
</script>
</head>

<body leftmargin="5" topmargin="5">
<form><%@ include file="/jsp/util/cabecalho.jsp"%> <%@ include
	file="/jsp/util/menu.jsp"%>

<table width="770" border="0" cellspacing="4" cellpadding="0">
	<tr>
		<td width="149" valign="top" class="leftcoltext">

		<div align="center">
		<%@ include file="/jsp/util/informacoes_usuario.jsp"%>
		<%@ include file="/jsp/util/mensagens.jsp"%>
		</div>
		</td>
		<td valign="top" class="centercoltext"><!--Início Tabela Reference a Páginação da Tela de Processo-->
			<table>
                <tbody><tr> 
                  <td></td>
                </tr>
              </tbody></table>
              <table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
                <tbody><tr> 
                  <td width="11"><img src="imagens/parahead_left.gif" border="0"></td>

                  <td class="parabg">Consulta Faturamento de Contas Pré-Faturadas</td>
                  <td valign="top" width="11"><img src="imagens/parahead_right.gif" border="0"></td>
                </tr>
              </tbody></table> 
			<table width="100%" border="0">
			<tr>
				<td colspan="2"><strong>Contas Pr&eacute-Faturadas:</strong></td>
			</tr>
				<tr>
					<!--<td colspan="4" bgcolor="#3399FF"> -->
					<td colspan="5" bgcolor="#000000" height="2" valign="baseline"></td>
				</tr>
				<tr>
					<td>
					<table width="100%" align="center" bgcolor="#90c7fc" border="0"
						cellpadding="0" cellspacing="0">
						<tr bgcolor="#cbe5fe">
							<td width="100%" align="center">
							<table width="100%" bgcolor="#99CCFF">
								<!--corpo da segunda tabela-->
								<tr bordercolor="#FFFFFF" bgcolor="#79BBFD">
									<td>
									<div align="center"><strong>Im&oacute;vel</strong></div>
									</td>
									<td>
									<div align="center"><strong>Refer&ecirc;ncia da Conta</strong></div>
									</td>
									<td>
									<div align="center"><strong>Grupo Faturamento</strong></div>
									</td>
									<td>
									<div align="center"><strong>Vencimento da Conta</strong></div>
									</td>
									<td>
									<div align="center"><strong>Valor da Conta</strong></div>
									</td>
									<td>
									<div align="center"><strong>Situa&ccedil;&atilde;o Atual</strong></div>
									</td>
								</tr>
								
								<%String cor = "#FFFFFF";%>
								
								<pg:pager isOffset="true" index="half-full" maxIndexPages="10"
									export="currentPageNumber=pageNumber;pageOffset"
									maxPageItems="10" items="${sessionScope.totalRegistros}">
									<pg:param name="pg" />
									<pg:param name="q" />
									<logic:present name="colecaoContasPreFaturadas">
										<%int cont = 0;%>
										<logic:iterate name="colecaoContasPreFaturadas" id="conta"
											scope="session">
											<pg:item>
												<%if (cor.equalsIgnoreCase("#cbe5fe")) {
													cor = "##FFFFFF";%>
												<tr bgcolor="#cbe5fe">
													<%} else {
													cor = "#cbe5fe";%>
												<tr bgcolor="#FFFFFF">
													<%}%>
													<td bordercolor="#90c7fc">
														<div align="center">
															<logic:present name="conta"	property="imovel">
																<bean:write name="conta" property="imovel.id" />
															</logic:present>
														</div>
													</td>
													<td bordercolor="#90c7fc">
														<div align="center">
															<logic:present name="conta"	property="referencia">
																<html:link page="/exibirAtualizarSituacaoContaAction.do"
																	paramName="conta" paramProperty="id"
																	paramId="idConta">
																	<bean:write name="conta" property="formatarAnoMesParaMesAno" />
																</html:link>
															</logic:present>
														</div>
													</td>
			
													<td bordercolor="#90c7fc">
														<div align="center">
															<logic:present name="conta"	property="rota">
																	<bean:write name="conta" property="rota.faturamentoGrupo.descricaoAbreviada" />
															</logic:present>
														</div>
													</td>
			
													<td width="9%" bordercolor="#90c7fc">
														<div align="center">
															<bean:write	name="conta" format="dd/MM/yyyy" property="dataVencimentoConta" />
														</div>
													</td>
			
													<td width="10%" bordercolor="#90c7fc">
														<div align="center">
															<logic:present name="conta" property="valorTotal">
																<bean:write name="conta" property="valorTotal" formatKey="money.format" />
															</logic:present>
														</div>
													</td>
			
													<td bordercolor="#90c7fc">
														<div align="center">
															<logic:present name="conta" property="debitoCreditoSituacaoAtual">
																<bean:write name="conta" property="debitoCreditoSituacaoAtual.descricaoAbreviada" />
															</logic:present>
														</div>
													</td>
												</tr>
											</pg:item>
										</logic:iterate>
									</logic:present>
									<tr>
										<td colspan="2">
										<div align="center"><strong><%@ include
											file="/jsp/util/indice_pager_novo.jsp"%></strong></div>
										</td>
									</tr>
								</pg:pager>
							</table>
							</td>
						</tr>
					</table>
					<table width="100%" border="0">
						<tr>
							<td width="233">
								<input type="button" name="buttonFiltro" class="bottonRightCol" value="Voltar Filtro"
									onClick="javascript:window.location.href='/gsan/exibirFiltrarContasPreFaturadasAction.do?paginacao=sim'">
							</td>
							<td valign="top">
								<div align="right">
									<a href="javascript:toggleBox('demodiv',1);">
								
										<img border="0"
											src="<bean:message key="caminho.imagens"/>print.gif"
											title="Imprimir Relação das Contas Pré-faturadas" /> 
									</a>
								</div>
							</td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>

<jsp:include
		page="/jsp/relatorio/escolher_tipo_relatorio.jsp?relatorio=gerarRelatorioContasPreFaturadasAction.do" />
<%@ include file="/jsp/util/rodape.jsp"%></form>
</body>
</html:html>
