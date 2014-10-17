<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>

<%@page import="gcom.util.ConstantesSistema"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<html:javascript staticJavascript="false"
	formName="PesquisarMensagemTipoSolicitacaoEspecificacaoActionForm"
	dynamicJavascript="true" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript">
	function setaFocus() {
		var form = document.PesquisarMensagemTipoSolicitacaoEspecificacaoActionForm;
		form.descricao.focus();
	}

	function limparForm(){
		var form = document.forms[0];
		form.descricao.value = "";
	}
</script>

</head>

<body leftmargin="5" topmargin="5"
	onload="resizePageSemLink(500, 250);setarFoco();">

<html:form action="/pesquisarMensagemTipoSolicitacaoEspecificacaoAction"
	method="post"
	onsubmit="return validatePesquisarMensagemTipoSolicitacaoEspecificacaoActionForm(this)">
	<table width="450" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="450" valign="top" bgcolor="#003399" class="centercoltext">
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
					<td class="parabg">Pesquisar Mensagem Tipo de Solicitação
					Especificação</td>

					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
				<tr>
					<td height="5" colspan="3"></td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td colspan="3">Preencha os campos para pesquisar uma mensagem
					tipo de solicitação:</td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td><strong>Descrição da Mensagem:<font
						color="#FF0000">*</font><strong></td>
					<td><html:text property="descricao" size="50" maxlength="200"
						tabindex="1" /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><html:radio property="tipoPesquisa" tabindex="2"
						value="<%=ConstantesSistema.TIPO_PESQUISA_INICIAL.toString()%>" />
					Iniciando pelo texto <html:radio property="tipoPesquisa"
						tabindex="3"
						value="<%=ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString()%>" />
					Contendo o texto</td>
				</tr>

				<tr>
					<td colspan="3">&nbsp;</td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td align="left"><logic:present
						name="caminhoRetornoTelaPesquisaMensagemTipoSolicitacaoEspecificacao">
						<input type="button" class="bottonRightCol" value="Voltar"
							onclick="redirecionarSubmit('${caminhoRetornoTelaPesquisaMensagemTipoSolicitacaoEspecificacao}.do')" />
					</logic:present> <input type="button" class="bottonRightCol" value="Limpar"
						onclick="document.forms[0].reset();limparForm();" /></td>
					<td colspan="3" align="right"><input type="submit"
						class="bottonRightCol" value="Pesquisar" /></td>
				</tr>
			</table>
	</table>
</body>
</html:form>
</html:html>
