<%@page
	import="gcom.gui.relatorio.arrecadacao.GerarRelatorioSituacaoDosAvisosBancariosActionForm"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

<%@ page import="gcom.util.ConstantesSistema"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="GerarRelatorioSituacaoDosAvisosBancariosActionForm"
	dynamicJavascript="true" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript">
<!-- Begin
	function validarForm(form) {
		if (validateGerarRelatorioSituacaoDosAvisosBancariosActionForm(form)) {
			submeterFormPadrao(form);
		}
	}
	-->
</script>

</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('${requestScope.nomeCampo}');">

	<html:form action="/gerarRelatorioSituacaoDosAvisosBancariosAction"
		name="GerarRelatorioSituacaoDosAvisosBancariosActionForm"
		type="gcom.gui.relatorio.arrecadacao.GerarRelatorioSituacaoDosAvisosBancariosActionForm"
		method="post">

		<%@ include file="/jsp/util/cabecalho.jsp"%>
		<%@ include file="/jsp/util/menu.jsp"%>

		<table width="770" border="0" cellspacing="4" cellpadding="0">
			<tr>
				<td width="149" valign="top" class="leftcoltext">
					<div align="center">
						<%@ include file="/jsp/util/informacoes_usuario.jsp"%>

						<%@ include file="/jsp/util/mensagens.jsp"%>
					</div>
				</td>
				<td width="625" valign="top" bgcolor="#003399" class="centercoltext">
					<table height="100%">

						<tr>
							<td></td>
						</tr>
					</table>
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td width="11"><img border="0"
								src="imagens/parahead_left.gif" /></td>
							<td class="parabg">Relatório Situação dos Avisos Bancários</td>
							<td width="11" valign="top"><img border="0"
								src="imagens/parahead_right.gif" /></td>
						</tr>
					</table>
					<p>&nbsp;</p>
					<table width="100%" border="0">
						<tr>
							<td colspan="2">Para gerar o relatório, informe os dados
								abaixo:</td>
						</tr>
						<tr>
							<td width="26%"><strong>Mês/Ano Referência:<font
									color="#FF0000">*</font></strong></td>
							<td><html:text property="mesAnoReferencia" size="7"
									maxlength="7" onkeyup="mascaraAnoMes(this, event);" /></td>
						</tr>

						<tr>
							<td>
								<p>&nbsp;</p>
							</td>
						</tr>
						<tr>
							<td><strong> <font color="#FF0000"></font>
							</strong></td>
							<td colspan="3" align="right">
								<div align="left">
									<strong> <font color="#FF0000">*</font>
									</strong> Campos obrigat&oacute;rios
								</div>
							</td>
						</tr>
						<tr>
							<td><input name="Submit22" class="bottonRightCol"
								value="Limpar" type="button"
								onclick="window.location.href='/gsan/exibirGerarRelatorioSituacaoDosAvisosBancariosAction.do?menu=sim';">
							<td colspan="2" align="right"><gcom:controleAcessoBotao
									name="Button" value="Gerar"
									onclick="javascript:validarForm(document.forms[0]);"
									url="gerarRelatorioSituacaoDosAvisosBancariosAction.do" /></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

		<%@ include file="/jsp/util/rodape.jsp"%>

	</html:form>
</body>
<!-- movimento_arrecadadores_acompanhar.jsp -->
</html:html>
