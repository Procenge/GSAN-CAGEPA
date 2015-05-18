<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

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
	formName="InserirCategoriaFaixaConsumoTarifaActionForm" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript">
	function ValidarForm(){
		var formRed = "/gsan/inserirCategoriaFaixaConsumoTarifaAction.do";
		var validado=true;
		
     	if (document.forms[0].valorM3Faixa[0].disabled == false || document.forms[0].valorM3Faixa[1].disabled == false) {
     		if (document.forms[0].valorM3Faixa[0].value == "") {
     			validado=false;
     			alert("Informe Valor do m³ da Faixa.");
     		}
     	}		
		
     	if (document.forms[0].valorM3FaixaEsgoto != undefined &&
     			document.forms[0].valorM3FaixaEsgoto != 'undefined' &&
     			document.forms[0].valorM3FaixaEsgoto.disabled == false) {
     		if (document.forms[0].valorM3FaixaEsgoto.value == "") {
     			validado=false;
     			alert("Valor da Tarifa de Esgoto por m³.");
     		}
     	}		
		
     	if (validado) {
			redirecionarSubmit(formRed);
     	}
	}
</script>

<script language="JavaScript">	
  function required () {

     	this.aa = new Array("limiteSuperiorFaixa", "Informe Limite Superior da Faixa.", new Function ("varName", " return this[varName];"));

    }
</script>	


</head>
<logic:equal name="testeInserir" value="false" scope="request">
	<body leftmargin="5" topmargin="5"
		onload="resizePageSemLink(640,340); setarFoco('${requestScope.limiteSuperiorFaixa}');">
</logic:equal>
<logic:notEqual name="testeInserir" value="false" scope="request">
	<body leftmargin="5" topmargin="5"
		onload="resizePageSemLink(640,340); setarFoco('${requestScope.limiteSuperiorFaixa}');">
</logic:notEqual>

<html:form action="/inserirCategoriaFaixaConsumoTarifaAction"
	name="InserirCategoriaFaixaConsumoTarifaActionForm"
	onsubmit="return validateInserirCategoriaFaixaConsumoTarifaActionForm(this);"
	type="gcom.gui.faturamento.consumotarifa.InserirCategoriaFaixaConsumoTarifaActionForm"
	method="post">

	<table width="600" border="0" cellpadding="0" cellspacing="5">
		<tr>
			<td width="600" valign="top" class="centercoltext">
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
					<td class="parabg">Informar Faixa de Consumo</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="4">Preencha os campos para inserir uma faixa na tarifa
					de consumo</td>
				</tr>
				<tr>
					<td width="28%" height="24"><strong>Limite Superior da Faixa<font
						color="#FF0000">*</font>:</strong></td>
					<td width="14%" colspan="3"><html:text maxlength="8"
						property="limiteSuperiorFaixa" size="8" /></td>
				</tr>
				<tr>
					<td height="24"><strong>Valor do m³ da Faixa<font color="#FF0000">*</font>:</strong></td>
					<logic:equal name="pQuantidadeDecimaisValorTarifa" value="4">
						<td colspan="3"><html:text style="text-align:right;" maxlength="18"
							property="valorM3Faixa" size="18"
							onkeyup="formataValorMonetarioQuatroDecimais(this, 18)" />
						</td>
					</logic:equal>
					<logic:equal name="pQuantidadeDecimaisValorTarifa" value="2">
						<td colspan="3"><html:text style="text-align:right;" maxlength="18"
							property="valorM3Faixa" size="18"
							onkeyup="formataValorMonetario(this, 18)" />
						</td>
					</logic:equal>
				</tr>
				
				<tr>
					<td height="24"><strong>Valor da Tarifa de Esgoto por m³:</strong></td>
				<logic:present name="indicadorTarifaEsgotoPropria" scope="session">
						<logic:equal name="pQuantidadeDecimaisValorTarifa" value="4">
							<td colspan="3"><html:text style="text-align:right;" maxlength="18"
								property="valorM3FaixaEsgoto" size="18"
								onkeyup="formataValorMonetarioQuatroDecimais(this, 18)" />
							</td>
						</logic:equal>
						<logic:equal name="pQuantidadeDecimaisValorTarifa" value="2">
							<td colspan="3"><html:text style="text-align:right;" maxlength="18"
								property="valorM3FaixaEsgoto" size="18"
								onkeyup="formataValorMonetario(this, 18)" />
							</td>
						</logic:equal>
				</logic:present>
				
				<logic:notPresent name="indicadorTarifaEsgotoPropria" scope="session">	
					<logic:equal name="pQuantidadeDecimaisValorTarifa" value="4">				
						<td colspan="3"><html:text style="text-align:right;" maxlength="18"
							property="valorM3Faixa" size="18"
							onkeyup="formataValorMonetarioQuatroDecimais(this, 18)" disabled="true" />
						</td>
					</logic:equal>
					<logic:equal name="pQuantidadeDecimaisValorTarifa" value="2">				
						<td colspan="3"><html:text style="text-align:right;" maxlength="18"
							property="valorM3Faixa" size="18"
							onkeyup="formataValorMonetario(this, 18)" disabled="true" />
						</td>
					</logic:equal>
				</logic:notPresent>
				</tr>
				
				<tr>
					<td><strong> <font color="#FF0000"></font></strong></td>
					<td align="right">
					<div align="left"><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios</div>
					</td>
				</tr>
				<tr>
					<td height="27" colspan="4">
					<div align="right"><input name="Button" type="button"
						class="bottonRightCol" value="Inserir"
						onClick="javascript:if(validateInserirCategoriaFaixaConsumoTarifaActionForm(document.forms[0])){ValidarForm();}"">
					<input name="Button2" type="button" class="bottonRightCol"
						value="Fechar"
						onClick="javascript:redirecionarSubmit('/gsan/exibirInserirCategoriaConsumoTarifaAction.do');"></div>
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
