<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<%@ page import="gcom.util.Util"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@page isELIgnored="false"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false" formName="ParcelamentoTermoTestemunhasActionForm" dynamicJavascript="false" />

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
	
<script language="JavaScript">
<!--

function validarForm(){
	var form = document.forms[0];

	if (validateParcelamentoTermoTestemunhasActionForm(form)) {
		form.action = "/gsan/exibirParcelamentoTermoTestemunhasAction.do?atualizar=sim"
		form.submit();
	}
}

function validateParcelamentoTermoTestemunhasActionForm(form) {
	return validateRequired(form) 
		&& validateCaracterEspecial(form)
		&& validateCpf(form);
}

function caracteresespeciais() {
	this.aa = new Array("nomeTestemunha1", "Nome da Testemunha 1 possui caracteres especiais.", new Function ("varName", " return this[varName];"));
	this.ab = new Array("nomeTestemunha2", "Nome da Testemunha 2 possui caracteres especiais.", new Function ("varName", " return this[varName];"));
	this.ac = new Array("cpfTestemunha1", "CPF da Testemunha 1 possui caracteres especiais.", new Function ("varName", " return this[varName];"));
	this.ad = new Array("cpfTestemunha2", "CPF da Testemunha 2 possui caracteres especiais.", new Function ("varName", " return this[varName];"));
}

function required() {
	this.aa = new Array("nomeTestemunha1", "Informe Nome da Testemunha 1.", new Function ("varName", " return this[varName];"));
	this.ab = new Array("nomeTestemunha2", "Informe Nome da Testemunha 2.", new Function ("varName", " return this[varName];"));
	this.ac = new Array("cpfTestemunha1", "Informe CPF da Testemunha 1.", new Function ("varName", " return this[varName];"));
	this.ad = new Array("cpfTestemunha2", "Informe CPF da Testemunha 2.", new Function ("varName", " return this[varName];"));
}

function cpf(){
	this.ac = new Array("cpfTestemunha1", "CPF da Testemunha 1 inválido.", new Function ("varName", " return this[varName];"));
	this.ad = new Array("cpfTestemunha2", "CPF da Testemunha 2 inválido.", new Function ("varName", " return this[varName];"));
}
 
//-->
</script>
</head>

<body leftmargin="5" topmargin="5">

<html:form action="/exibirParcelamentoTermoTestemunhasAction.do"
	name="ParcelamentoTermoTestemunhasActionForm"
	type="gcom.gui.cobranca.parcelamento.ParcelamentoTermoTestemunhasActionForm" method="post">
	
	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<html:hidden property="id"/>
	<html:hidden property="parcelamentoId"/>

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
				<td class="parabg">Informar Testemunhas do Termo de Parcelamento</td>
				<td width="11"><img border="0"
					src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
			</tr>
		</table>
		<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td colspan="3">
					<table width="100%" align="center" bgcolor="#99CCFF" border="0">
						<tr>
							<td><strong>Testemunha 1:</strong></td>
						</tr>

						<tr bgcolor="#cbe5fe"> 
							<td> 
								Nome:<strong><font color="#FF0000">*</font></strong>
							</td>
							<td colspan="2">
								<html:text property="nomeTestemunha1" maxlength="60" size="60"/>
							</td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td> 
								CPF:<strong><font color="#FF0000">*</font></strong>
							</td>
							<td colspan="2">
								<html:text property="cpfTestemunha1" maxlength="11" size="10" />
							</td>
							
						</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td colspan="3" height="10"></td>
				</tr>
				<tr>
					<td colspan="3">
					<table width="100%" align="center" bgcolor="#99CCFF" border="0">
						<tr bordercolor="#90c7fc">
							<td bgcolor="#90c7fc"><strong>Testemunha 2:</strong></td>
						</tr>

						<tr bgcolor="#cbe5fe"> 
							<td> 
								Nome:<strong><font color="#FF0000">*</font></strong>
							</td>
							<td colspan="2">
								<html:text property="nomeTestemunha2" maxlength="60" size="60" />
							</td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td> 
								CPF:<strong><font color="#FF0000">*</font></strong>
							</td>
							<td colspan="2">
								<html:text property="cpfTestemunha2" maxlength="11" size="10" />
							</td>
						</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td colspan="3" height="10"></td>
				</tr>
				<tr>
					<td colspan="3" align="left"><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios</td>
				</tr>
				
				<tr>
					<td align="left">
						<input name="Voltar" type="button" class="bottonRightCol" value="Fechar" style="width: 80px"
							onClick="javascript:history.back();" />
					</td>
					
					<td align="right" colspan="2">
					
					<!-- 
						<input type="button" name="ButtonConfirmar" class="bottonRightCol" value="Confirmar" 
							onClick="javascript:confirmarForm()">
					 -->
						<input type="button" name="ButtonAtualizar" class="bottonRightCol" value="Atualizar/Emitir"
							onclick="javascript:validarForm();" />
					</td>
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
