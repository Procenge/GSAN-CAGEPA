<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>

<%@page import="java.util.Collection"%>
<%@page import="gcom.util.Util"%>

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
	if (CheckboxNaoVazioMensagemGenerico('É necessário selecionar pelo menos um boleto para o sistema efetuar o cancelamento.', form.idRegistrosCancelamento)) {
		abrirPopupComSubmit('exibirCancelarBoletoBancarioAction.do',230,487,'CancelarBoleto');
	}
}
</script>
</head>

<body leftmargin="5" topmargin="5">

<html:form action="/exibirManterBoletoBancarioAction"
	name="CancelarBoletoBancarioActionForm"
	type="gcom.gui.cobranca.CancelarBoletoBancarioActionForm" method="post">

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
					<td class="parabg">Manter Boleto Bancário</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>
				</tr>
			</table>

			<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="7" height="23"><font style="font-size: 10px;"
						color="#000000" face="Verdana, Arial, Helvetica, sans-serif"> <strong>Dados do boleto bancário:</strong> </font></td>
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
									<td align="center" width="17%"><Font color="#000000"><strong>Arrecadador</strong></font></td>
									<td align="center"><font color="#000000"><strong>Nosso Número</strong></font></td>
									<td align="center"><font color="#000000"><strong>Imóvel</strong></font></td>
									<td align="center"><font color="#000000"><strong>Data de Vencimento</strong></font></td>
									<td align="center"><font color="#000000"><strong>Valor</strong></font></td>
									<td align="center"><font color="#000000"><strong>Data de Entrada</strong></font></td>
									<td align="center"><font color="#000000"><strong>Situação Atual</strong></font></td>
								</tr>
								<pg:pager isOffset="true" index="half-full" maxIndexPages="10"
									export="currentPageNumber=pageNumber;pageOffset"
									maxPageItems="10" items="${sessionScope.totalRegistros}">
									<pg:param name="pg" />
									<pg:param name="q" />
									<%int cont = 0;%>
									<logic:iterate name="colecaoBoletoBancarioFiltroHelper" id="helper">
										<pg:item>
											<%cont = cont + 1;
												if (cont % 2 == 0) {%>
											<tr bgcolor="#FFFFFF">
												<%} else {%>
											<tr bgcolor="#cbe5fe">
												<%}%>

												<td>
													<div align="center">
														<logic:equal name="helper" property="habilitarCancelamento" value="true">
															<input type="checkbox" name="idRegistrosCancelamento" value="<bean:write name="helper" property="boletoBancario.id"/>">
														</logic:equal>
														<logic:equal name="helper" property="habilitarCancelamento" value="false">
															<input type="checkbox" disabled="disabled" name="idRegistrosCancelamento" value="-1">
														</logic:equal>
													</div>
												</td>
												<td>
													<div align="center">
														<logic:notEmpty name="helper" property="boletoBancario.arrecadador">
															<bean:write name="helper" property="boletoBancario.arrecadador.codigoAgente" />
														</logic:notEmpty>
													</div>
												</td>
												<td>
													<div align="center">
														<html:link
															href="/gsan/exibirAtualizarBoletoBancarioAction.do"
															paramId="idBoletoBancario" paramProperty="boletoBancario.id"
															paramName="helper">
															<bean:write name="helper" property="boletoBancario.nossoNumero" />
														</html:link>
													</div>
												</td>
												<td>
													<div align="center">
														<logic:notEmpty name="helper" property="boletoBancario.imovel">
															<bean:write name="helper" property="boletoBancario.imovel.id" />
														</logic:notEmpty>
													</div>
												</td>
												<td>
													<div align="center">
                                                    	<bean:write name="helper" property="boletoBancario.dataVencimento" formatKey="date.format"/>
													</div>
												</td>
												<td>
													<div align="right">
														<bean:write name="helper" property="boletoBancario.valorDebito" formatKey="money.format"/>
													</div>
												</td>
												<td>
													<div align="center">
														<logic:notEmpty name="helper" property="boletoBancarioSituacaoHistorico">
															<bean:write name="helper" property="boletoBancarioSituacaoHistorico.dataEntrada" formatKey="date.format"/>
														</logic:notEmpty>														
													</div>
												</td>
												<td>
													<div align="center">
														<logic:notEmpty name="helper" property="boletoBancario.boletoBancarioSituacao">
															<bean:write name="helper" property="boletoBancario.boletoBancarioSituacao.descricao" />
														</logic:notEmpty>
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
											<input type="button" name="Submit24" class="bottonRightCol" value="Cancelar Boleto" onClick="validarForm(document.forms[0]);">
										</font>

										<logic:present name="voltar">
											<logic:equal name="voltar" value="filtrar">
												<input name="button" type="button" class="bottonRightCol" value="Voltar Filtro" onclick="window.location.href='<html:rewrite page="/exibirFiltrarBoletoBancarioAction.do"/>'" align="left" style="width: 80px;">
											</logic:equal>
											<logic:equal name="voltar" value="totalizador">
												<input name="button" type="button" class="bottonRightCol" value="Voltar" onclick="window.location.href='<html:rewrite page="/exibirTotalizadorBoletoBancarioAction.do"/>'" align="left" style="width: 80px;">
											</logic:equal>
										</logic:present>
										<logic:notPresent name="voltar">
											<input name="button" type="button" class="bottonRightCol" value="Voltar Filtro" onclick="window.location.href='<html:rewrite page="/exibirFiltrarBoletoBancarioAction.do"/>'" align="left" style="width: 80px;">
										</logic:notPresent>
									</td>
									<td align="right" valign="top">
			                     		<a href="javascript:toggleBox('demodiv',1);">
			                             	<img align="right" border="0" src="<bean:message key='caminho.imagens'/>print.gif" title="Imprimir Boleto Bancário"/>
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

	<jsp:include page="/jsp/relatorio/escolher_tipo_relatorio.jsp?relatorio=gerarRelatorioBoletosBancariosAction.do"/>

	<%@ include file="/jsp/util/rodape.jsp"%>

</html:form>
</body>
</html:html>