<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<html:javascript staticJavascript="false" formName="ConsumoMedioImovelPopupActionForm" />

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript">
	function validarForm(form) {
		if (validateConsumoMedioImovelPopupActionForm(form)) {
			if (parseInt(form.consumoMedioFinal.value) < parseInt(form.consumoMedioInicial.value)) {
				alert('O Intervalo de Média do Imóvel Final deve ser Maior ou Igual a Inicial.');
				form.consumoMedioFinal.focus();
				return false;
			}

			if (!VerificaIntervalosPreenchidos(form.consumoMedioInicial, form.consumoMedioFinal,
					'Verifique o preenchimento do Intervalo de Média do Imóvel')) {
				return false;
			}

			document.forms[0].target = '';
	    	form.submit();
		}
	}

	function duplicarConsumoMedio() {
		var formulario = document.forms[0]; 
		formulario.consumoMedioFinal.value = formulario.consumoMedioInicial.value;
	}

	function VerificaIntervalosPreenchidos(interInicial, interFinal, msg) {
		if(interInicial.value != '' && interFinal.value == '') {
			alert(msg);
			interFinal.focus();
			return false;
		}else if (interInicial.value == '' && interFinal.value != '') {
			alert(msg);
			interInicial.focus();
			return false;
		}
		
		return true;
	}
</script>

</head>

<logic:present name="closePage" scope="session">
	<logic:equal name="closePage" value="S" scope="session">
		<body leftmargin="5" topmargin="5" onload="chamarReload('filtrarImovelEmissaoOrdensSeletivasWizardAction.do?action=exibirFiltrarImovelEmissaoOrdensSeletivasCaracteristicas');window.close();">
	</logic:equal>
</logic:present>

<logic:notPresent name="closePage" scope="session">
	<body leftmargin="5" topmargin="5">
</logic:notPresent>

<html:form action="/inserirConsumoMedioImovelPopupAction" method="post"
	onsubmit="return validateConsumoMedioImovelPopupActionForm(this);">
	<table width="520" border="0" cellpadding="0" cellspacing="5">
		<tr>
			<td width="520" valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Adicionar Dados do Consumo Médio do Imóvel para filtro do imóvel</td>
					<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>Preencha os campos de dados do consumo médio do imóvel para filtro do imóvel</td>
					<td align="right"></td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">
						<table border="0">
							<tr>
								<td width="100"><strong>Média do Imóvel:</strong></td>
								<td align="left"><html:text property="consumoMedioInicial"
									size="5" maxlength="4" onkeyup="duplicarConsumoMedio();" onkeypress="return isCampoNumerico(event);"></html:text></td>
								<td>&nbsp;a&nbsp;</td>
								<td align="left"><html:text property="consumoMedioFinal" size="5" maxlength="4" onkeypress="return isCampoNumerico(event);"></html:text></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td height="24">
					<div align="right"> 
						<input name="Button1" type="button" class="bottonRightCol" value="Inserir" onClick="validarForm(document.forms[0]);">
						<input name="Button2" type="button" class="bottonRightCol" value="Fechar" onClick="window.close();">
					</div>
					</td>
				</tr>
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>
</html:form>
</body>
</html:html>
