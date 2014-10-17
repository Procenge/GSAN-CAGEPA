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
	formName="InserirBancoActionForm" />

<SCRIPT LANGUAGE="JavaScript">

function validarForm(formulario){

	if (validateInserirBancoActionForm(formulario)){	
		
		formulario.action = "/gsan/inserirBancoAction.do";
		submeterFormPadrao(formulario);
	}
}


</SCRIPT>

</head>

<body leftmargin="5" topmargin="5"
	onload="document.forms[0].removerEndereco.value=''; setarFoco('${requestScope.nomeCampo}');">

<html:form action="/exibirInserirBancoAction" method="post">
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
					<td class="parabg">Inserir Banco</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para adicionar um Banco, informe os
					dados abaixo:</td>
					<!-- Banco -->
				<tr>
					<td><strong>Código:<font color="#FF0000">*</font></strong></td>
					<td colspan="2"><html:text property="idBanco" size="6" maxlength="6"
						tabindex="2" /></td>
				</tr>


				<tr>
					<td><strong>Nome:<font color="#FF0000">*</font></strong></td>
					<td colspan="2"><html:text property="nome" size="40" maxlength="40"
						tabindex="2" /></td>
				</tr>


				<tr>
					<td><strong>Nome Abreviado:<font color="#FF0000">*</font></strong></td>
					<td colspan="2"><html:text property="nomeAbreviado" size="10" maxlength="10"
						tabindex="2" /></td>
				</tr>				
				<tr>
					<td></td>
					<td><font color="#FF0000">*</font>&nbsp;Campos obrigat&oacute;rios</td>
				</tr>

				<tr>
					<td><input name="Button" type="button" class="bottonRightCol"
						value="Desfazer" align="left"
						onclick="window.location.href='<html:rewrite page="/exibirInserirBancoAction.do?menu=sim"/>'">
					<input type="button" name="Submit23" class="bottonRightCol"
						value="Cancelar"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"></td>
					<td align="right">
					
					<gcom:controleAcessoBotao name="Botao" value="Inserir"
						onclick="validarForm(document.forms[0]);"
						url="inserirBancoAction.do" tabindex="13" /></td>
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

