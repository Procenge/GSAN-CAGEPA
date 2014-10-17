<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

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
	formName="EmitirSegundaViaContaInternetActionForm"
	dynamicJavascript="true" />



<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script>
<!--
var bCancel = false;

function limparMatricula(form){
	form.matricula.value = '';
}

function validarForm(form){

	if(testarCampoValorZero(document.EmitirSegundaViaContaInternetActionForm.matricula, 'Matrícula')) {

		if(validateEmitirSegundaViaContaInternetActionForm(form)){
    		
    		form.submit();
		}
	}
}
-->
</script>
</head>

<body leftmargin="5" topmargin="5"
	onload="setarFoco('${requestScope.nomeCampo}')">

<html:form action="${requestScope.nomeAction}" method="post"
	onsubmit="return validateEmitirSegundaViaContaInternetActionForm(this);">

	<%@ include file="/jsp/util/cabecalho.jsp"%>


	<table width="770" border="0" cellspacing="4" cellpadding="0">
		<tr>

			<td width="655" valign="top" class="centercoltext">
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
					<td class="parabg"><font size="2"><strong>Emissão da 2ª Via de
					Conta - Atendimento Internet</strong></font></td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>

			<table width="100%" height="34" border="0" align="center"
				cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="3"><br>
					<br>
					<br>
					<div align="center"><img border="0"
						src="<bean:message key="caminho.imagens"/>gsan.gif" /></div>
					</td>
				</tr>

				<tr>


					<td colspan="3"><br>
					<br>
					Informe apenas os números da sua <strong>MATRÍCULA</strong>, que se
					encontra na parte superior da sua conta de água:</td>


				</tr>
				<logic:present name="caern">
				<tr>
					<td colspan="4"><font color="red"> <strong><br>
						As matrículas dos imóveis que possuem contas com vencimento até o dia 07 de maio devem ter o último dígito desconsiderado. 
						<br>Ex.: 1234567-8 Deve-se informar apenas 1234567. </strong></font>
					</td>
				</tr>
				</logic:present>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td width="15%" align="left"><strong>MATRÍCULA:</strong><font
						color="#FF0000">*</font></td>
					<td width="87%"><html:text property="matricula" size="9"
						maxlength="9" onkeyup="javascript:verificaNumeroInteiro(this);" />
					<font size="1"> &nbsp; </font></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td height="19"><strong> <font color="#FF0000"></font></strong></td>
					<td align="right">
					<div align="left"><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios</div>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>

				<tr>
					<td><input name="Button" type="button" class="bottonRightCol"
						value="Desfazer" align="left"
						onclick="javascript:limparMatricula(document.forms[0]);"></td>
					<logic:present name="veioMenu" scope="session">
						<td align="left"><input type="button" name="ButtonCancelar"
							class="bottonRightCol" value="Cancelar"
							onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
						</td>
					</logic:present>




					<td align="right" height="24"><input type="button" name="Button"
						class="bottonRightCol" value="Consultar"
						onClick="javascript:validarForm(document.forms[0])" /></td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>

			</table>
			</td>
		</tr>
	</table>

</html:form>
<%@ include file="/jsp/util/rodape.jsp"%>
</body>
</html:html>
