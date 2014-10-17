<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>

<%@page import="java.util.Collection"%>
<%@page import="gcom.util.Util"%>
<%@page import="gcom.cadastro.imovel.ImovelCobrancaSituacao"%>

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
function facilitador(objeto){
	if (objeto.value == "0"){
		objeto.value = "1";
		marcarTodosExceto(-1);
	}
	else{
		objeto.value = "0";
		desmarcarTodos();
	}
}

function validarForm() {

	var form = document.forms[0];
	if (CheckboxNaoVazioMensagemGenerico('É necessário selecionar pelo menos um imóvel para o sistema efetuar a retirada da cobrança administrativa', form.idRegistrosRetirada)) {
		abrirPopupComSubmit('exibirInformarMotivoRetiradaCobrancaAdministrativaAction.do',230,487,'RetirarImovelCobrancaAdministrativa');
	}
}

</script>
</head>

<body leftmargin="5" topmargin="5">

<html:form action="/exibirManterImovelCobrancaAdministrativaAction"
	name="RetirarImovelCobrancaAdministrativaActionForm"
	type="gcom.gui.cobranca.cobrancaadministrativa.RetirarImovelCobrancaAdministrativaActionForm" method="post">

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
					<td class="parabg">Manter Imóvel em Cobrança Administrativa</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>
				</tr>
			</table>

			<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="7" height="23"><font style="font-size: 10px;"
						color="#000000" face="Verdana, Arial, Helvetica, sans-serif"> <strong>Dados do Imóvel em Cobrança Administrativa:</strong> </font></td>
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
									<td width="7%">
										<div align="center">
											<strong>
												<a href="javascript:facilitador(this);">Todos</a>
											</strong>
										</div>
									</td>
									<td align="center" width="17%"><Font color="#000000"><strong>Imóvel</strong></font></td>
									<td align="center"><font color="#000000"><strong>Cliente Usuário</strong></font></td>
									<td align="center"><font color="#000000"><strong>Empresa</strong></font></td>
									<td align="center"><font color="#000000"><strong>Dt.Ini.Cobr.</strong></font></td>
									<td align="center"><font color="#000000"><strong>Dt.Retir.Cobr.</strong></font></td>
									<td align="center"><font color="#000000"><strong>Qtd. Débitos</strong></font></td>
									<td align="center"><font color="#000000"><strong>Valor Débitos</strong></font></td>
									<td align="center"><font color="#000000"><strong>Situação Atual</strong></font></td>
								</tr>
								<pg:pager isOffset="true" index="half-full" maxIndexPages="10"
									export="currentPageNumber=pageNumber;pageOffset"
									maxPageItems="10" items="${sessionScope.totalRegistros}">
									<pg:param name="pg" />
									<pg:param name="q" />
									<%int cont = 0;%>
									<logic:iterate name="colecaoImovelCobrancaSituacao" id="imovelCobrancaSituacao" type="ImovelCobrancaSituacao">
										<pg:item>
											<%cont = cont + 1;
												if (cont % 2 == 0) {%>
											<tr bgcolor="#FFFFFF">
												<%} else {%>
											<tr bgcolor="#cbe5fe">
												<%}%>

												<td width="7%">
													<div align="center">
														<logic:empty name="imovelCobrancaSituacao" property="dataRetiradaCobranca">
															<input type="checkbox"
																name="idRegistrosRetirada"
																value="<bean:write name="imovelCobrancaSituacao" property="id"/>">
														</logic:empty>
														<logic:notEmpty name="imovelCobrancaSituacao" property="dataRetiradaCobranca">
															<input type="checkbox"
																name="idRegistrosRetirada"
																value="-1"
																disabled="disabled">
														</logic:notEmpty>
														
													</div>
												</td>
												<td>
													<div align="center">
														<html:link
															href="/gsan/exibirAtualizarImovelCobrancaAdministrativaAction.do"
															paramId="idImovelCobrancaSituacao" paramProperty="id"
															paramName="imovelCobrancaSituacao">
															<bean:write name="imovelCobrancaSituacao" property="imovel.id" />
														</html:link>
													</div>
												</td>
												<td>
													<div align="center">
                                                    	<bean:write name="imovelCobrancaSituacao" property="cliente.nome"/>
													</div>
												</td>
												<td>
													<div align="center">
															
														<a href="javascript:abrirPopup('exibirConsultarDadosContratoEmpresaCobrancaAdministrativaAction.do?idEmpresa=${imovelCobrancaSituacao.cobrancaAcaoAtividadeComando.empresa.id}' , 260, 510);"
															title="<%="" + imovelCobrancaSituacao.getCobrancaAcaoAtividadeComando().getEmpresa().getDescricao()%>">
															
															<bean:write name="imovelCobrancaSituacao" property="cobrancaAcaoAtividadeComando.empresa.id" />
														</a>
													</div>
												</td>
												<td>
													<div align="center">
                                                    	<bean:write name="imovelCobrancaSituacao" property="dataImplantacaoCobranca" formatKey="date.format"/>
													</div>
												</td>
												<td>
													<div align="center">
														<bean:write name="imovelCobrancaSituacao" property="dataRetiradaCobrancaFormatada" />
													</div>
												</td>
												<td>
													<div align="center">
														<bean:write name="imovelCobrancaSituacao" property="quantidadeDebito"/>
													</div>
												</td>
												<td>
													<div align="right">
														<bean:write name="imovelCobrancaSituacao" property="valorDebito" formatKey="money.format"/>
													</div>
												</td>
												<td>
													<div align="left">														
														<bean:write name="imovelCobrancaSituacao" property="situacaoAtual"/>														
													</div>
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
										<font color="#FF0000">
										
										<input type="button" name="Submit24" class="bottonRightCol" value="Retirar" onClick="validarForm();">
										</font> 
										<input name="button" 
											type="button"
											class="bottonRightCol" 
											value="Voltar Filtro"
											onclick="window.location.href='<html:rewrite page="/exibirFiltrarImovelCobrancaAdministrativaAction.do"/>'"
											align="left" 
											style="width: 80px;">
									</td>
																		<td align="right" valign="top">
			                     		<a href="javascript:toggleBox('demodiv',1);">
			                             	<img align="right" border="0" src="<bean:message key='caminho.imagens'/>print.gif" title="Imprimir Relação de Imóveis em Cobrança Administrativa"/>
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
	
	<jsp:include page="/jsp/relatorio/escolher_tipo_relatorio.jsp?relatorio=gerarRelatorioImoveisEmCobrancaAdministrativaAction.do"/>
	
	<%@ include file="/jsp/util/rodape.jsp"%>

</html:form>
</body>
</html:html>