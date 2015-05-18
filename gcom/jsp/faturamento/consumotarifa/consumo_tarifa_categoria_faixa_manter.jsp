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
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="InserirCategoriaFaixaConsumoTarifaActionForm" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript">
	function required () {
	 	this.aa = new Array("limiteSuperiorFaixa", "Limite Superior da Faixa.", new Function ("varName", " return this[varName];"));
		this.ab = new Array("valorM3Faixa", "Valor do m� da Faixa.", new Function ("varName", " return this[varName];"));

		
		if(document.forms[0].valorM3FaixaEsgoto != null){
			this.ai = new Array("valorM3FaixaEsgoto", "Valor do m� da Faixa para Esgoto.", new Function ("varName", " return this[varName];"));
		}
	}
	
	//function caracteresespeciais () {
	// 	this.ac = new Array("consumoMinimo", "Consumo M�nimo deve somente conter n�meros positivos.", new Function ("varName", " return this[varName];"));
	// 	this.ad = new Array("valorTarifaMinima", "Valor da Tarifa M�nima deve somente conter n�meros positivos.", new Function ("varName", " return this[varName];"));
	//}
	function FloatValidations () {
	 	this.ae = new Array("valorM3Faixa", "Valor do m� da Faixa.", new Function ("varName", " return this[varName];"));
	}
	
	function IntegerValidations () {
	 	this.ae = new Array("limiteSuperiorFaixa", "Limite Superior da Faixa.", new Function ("varName", " return this[varName];"));
	}

	function ValidarForm(){
		var formRed = "/gsan/manterCategoriaFaixaConsumoTarifaAction.do";
			redirecionarSubmit(formRed);
		}
	
	function exitbirManterCategoriaConsumoTarifaAction(){
		var formRed = "/gsan/exibirManterCategoriaConsumoTarifaAction.do";
			redirecionarSubmit(formRed);
		}	
</script>
</head>
<logic:equal name="testeInserir" value="false" scope="request">
	<body leftmargin="5" topmargin="5" onload="resizePageSemLink(640,240); setarFoco('${requestScope.limiteSuperiorFaixa}');">
</logic:equal>
<logic:notEqual name="testeInserir" value="false" scope="request">
	<body leftmargin="5" topmargin="5" onload="resizePageSemLink(640,240); setarFoco('${requestScope.limiteSuperiorFaixa}');">
</logic:notEqual>
<html:form action="/manterCategoriaFaixaConsumoTarifaAction"
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
					<td width="30%" height="24"><strong>Limite Superior da Faixa<font
						color="#FF0000">*</font>:</strong></td>
					<td width=70%" colspan="3"><html:text maxlength="8"
						property="limiteSuperiorFaixa" size="8" /></td>
				</tr>
				<tr>
					<td height="24"><strong>Valor do M3 da Faixa<font color="#FF0000">*</font>:</strong></td>
					
					<logic:equal name="pQuantidadeDecimaisValorTarifa" value="4">
						<td colspan="3"><html:text  property="valorM3Faixa" style="text-align:right;" maxlength="18" onkeyup="formataValorMonetarioQuatroDecimais(this, 18)"
							size="18" />
						</td>
					</logic:equal>
					<logic:equal name="pQuantidadeDecimaisValorTarifa" value="2">
						<td colspan="3"><html:text  property="valorM3Faixa" style="text-align:right;" maxlength="18" onkeyup="formataValorMonetario(this, 18)"
							size="18" />
						</td>
					</logic:equal>
				</tr>
				
				<logic:present name="indicadorTarifaEsgotoPropria" scope="session">
					<tr>
						<td height="24"><strong>Valor do M3 da Faixa para Esgoto<font color="#FF0000">*</font>:</strong></td>
						
						<logic:equal name="pQuantidadeDecimaisValorTarifa" value="4">
							<td colspan="3"><html:text  property="valorM3FaixaEsgoto" style="text-align:right;" maxlength="18" onkeyup="formataValorMonetarioQuatroDecimais(this, 18)"
								size="18" />
							</td>
						</logic:equal>
						<logic:equal name="pQuantidadeDecimaisValorTarifa" value="2">
							<td colspan="3"><html:text  property="valorM3FaixaEsgoto" style="text-align:right;" maxlength="18" onkeyup="formataValorMonetario(this, 18)"
								size="18" />
							</td>
						</logic:equal>
					</tr>
				</logic:present>
				
				<tr>
					<td colspan="1"><strong> <font color="#FF0000"></font></strong></td>
					<td align="right" colspan="3">
					<div align="left"><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios</div>
					</td>
				</tr>
				
				<tr>
					<td height="27" colspan="4">
					<div align="right"><input name="Button" type="button"
						class="bottonRightCol" value="Inserir" onClick="javascript:if(validateInserirCategoriaFaixaConsumoTarifaActionForm(document.forms[0])){document.forms[0].submit();}""> <input
						name="Button2" type="button" class="bottonRightCol" value="Fechar"
						onClick="javascript:exitbirManterCategoriaConsumoTarifaAction();"></div>
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
