<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="gcom.util.ConstantesSistema"%>

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
	formName="CancelarBoletoBancarioActionForm" />

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript">
	function required () {
		this.aa = new Array("idMotivoCancelamento", "Informe Motivo do Cancelamento.", new Function ("varName", " return this[varName];"));
	}

	function confirmar(){
		var form = document.forms[0];

		if(validateRequired(form) && confirm('Confirma cancelamento?')){
			form.submit();
		}
	}
</script>

</head>

<logic:present name="closePage" scope="session">
	<logic:equal name="closePage" value="S" scope="session">
		<body leftmargin="5" topmargin="5" onload="chamarReload('exibirManterBoletoBancarioAction.do?action=finalizarOperacao');window.close();">
	</logic:equal>
</logic:present>

<logic:notPresent name="closePage" scope="session">
	<body leftmargin="5" topmargin="5" onload="resizePageSemLink(487,230);">
</logic:notPresent>

<html:form action="/cancelarBoletoBancarioAction"
	name="CancelarBoletoBancarioActionForm"
	type="gcom.gui.cobranca.CancelarBoletoBancarioActionForm"
	method="post">

	<table width="450" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="450" valign="top" class="centercoltext">
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Cancelar Boleto Bancário</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>

			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para cancelar o boleto bancário, informe os
					dados abaixo:</td>
				</tr>
			</table>

			<table width="100%" border="0">
				<tr>
					<td width="40%"><strong>Motivo do Cancelamento<font
						color="#FF0000">*</font>:</strong></td>
					<td><html:select property="idMotivoCancelamento"
						style="width: 250px;">
						<html:option
							value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present name="colecaoBoletoBancarioMotivoCancelamento">
							<html:options collection="colecaoBoletoBancarioMotivoCancelamento"
								labelProperty="descricao" property="id" />
						</logic:present>
					</html:select></td>
				</tr>
				<tr>
					<td align="right">
					<div align="left"><strong><font color="#FF0000">*</font></strong>Campos
					obrigat&oacute;rios</div>
					</td>
				</tr>
				<tr>
					<!--html:hidden name="CancelarBoletoBancarioActionForm"
						property="idRegistrosCancelamento" /-->

					<td><strong></strong></td>
					<td align="right"><input type="button" class="bottonRightCol"
						style="" value="Confirmar" onclick="javascript:confirmar();" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>



</html:form>


</body>
</html:html>
