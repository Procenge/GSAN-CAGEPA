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
	src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false"
	formName="GerarDeclaracaoAnualQuitacaoDebitosActionForm" />

<script>
	function validarForm(form) {
		if(validateGerarDeclaracaoAnualQuitacaoDebitosActionForm(form)) {
			form.action = "/gsan/gerarDeclaracaoAnualQuitacaoDebitosAction.do";
			submeterFormPadrao(form);
		}
	}
</script>

</head>

<body leftmargin="5" topmargin="5"
	onload="setarFoco('${requestScope.nomeCampo}');">

<html:form action="/exibirGerarDeclaracaoAnualQuitacaoDebitosAction" name="GerarDeclaracaoAnualQuitacaoDebitosActionForm"
	type="gcom.gui.faturamento.GerarDeclaracaoAnualQuitacaoDebitosActionForm">

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

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Gerar Declaração Anual de Quitação de Débitos</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>

			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td>Para gerar uma declaração, informe os dados abaixo:</td>
					<td align="right"></td>
				</tr>
			</table>

			<table width="100%" border="0">
				<tr>
					<td><strong>Grupo de Faturamento:<font color="#FF0000">*</font></strong></td>
					<td><html:select property="idFaturamentoGrupo" style="width: 200px;" tabindex="4">
						<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present name="colecaoFaturamentoGrupo">
							<html:options collection="colecaoFaturamentoGrupo" labelProperty="descricao" property="id" />
						</logic:present>
					</html:select></td>
				</tr>
				<tr>
					<td></td>
					<td><font color="#FF0000">*</font>&nbsp;Campos obrigatórios</td>
				</tr>
				<tr>
					<td><input type="button" name="Button"
						class="bottonRightCol" value="Desfazer" tabindex="13"
						onClick="javascript:window.location.href='/gsan/exibirGerarDeclaracaoAnualQuitacaoDebitosAction.do?desfazer=S'"
						style="width: 80px" />&nbsp;
						<input type="button" name="Button"
						class="bottonRightCol" value="Cancelar" tabindex="14"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
						style="width: 80px" /></td>
					<td align="right"><gcom:controleAcessoBotao name="Button"
						value="Gerar" tabindex="15"
						onclick="javascript:validarForm(document.forms[0]);"
						url="gerarDeclaracaoAnualQuitacaoDebitosAction.do" /></td>
				</tr>
			</table>

			<p>&nbsp;</p>

			</td>
		</tr>
	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>

</html:form>

</body>
</html:html>

