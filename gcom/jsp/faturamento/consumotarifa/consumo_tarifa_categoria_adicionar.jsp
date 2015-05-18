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
<%@ page import="gcom.util.ConstantesSistema"%>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="InserirCategoriaConsumoTarifaActionForm" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript">
	function redirecionaForm(){
		var formRed = "/gsan/inserirConsumoTarifaAction.do";
			redirecionarSubmit(formRed);
	}
	function validarForm(form){
		form.submit();          
	}
</script>	


<logic:notPresent name="indicadorTarifaCosumoPorSubCategoria" scope="session">
	<script language="JavaScript">	
	  function required () {
	     	this.aa = new Array("consumoMinimo", "Informe Consumo M�nimo.", new Function ("varName", " return this[varName];"));
	    	this.ab = new Array("valorTarifaMinima", "Informe Valor da Tarifa M�nima.", new Function ("varName", " return this[varName];"));
	    	
	    	this.ah = new Array("slcCategoria", "Informe Categoria.", new Function ("varName", " return this[varName];"));
	    	
	    	if(document.forms[0].valorTarifaMinimaEsgoto != null){
	    		this.ai = new Array("valorTarifaMinimaEsgoto", "Informe Valor da Tarifa M�nima Esgoto.", new Function ("varName", " return this[varName];"));
	    	}
	    }
	</script>	
</logic:notPresent>

<logic:present name="indicadorTarifaCosumoPorSubCategoria" scope="session">
	<script>	
	  function required () {
	     	this.aa = new Array("consumoMinimo", "Informe Consumo M�nimo.", new Function ("varName", " return this[varName];"));
	    	this.ab = new Array("valorTarifaMinima", "Informe Valor da Tarifa M�nima.", new Function ("varName", " return this[varName];"));
	    	
	    	this.ah = new Array("slcCategoria", "Informe Categoria.", new Function ("varName", " return this[varName];"));
	    	this.fa = new Array("slcSubCategoria", "Informe SubCategoria.", new Function ("varName", " return this[varName];"));
	    	
	    	if(document.forms[0].valorTarifaMinimaEsgoto != null){
	    		this.ai = new Array("valorTarifaMinimaEsgoto", "Informe Valor da Tarifa M�nima Esgoto.", new Function ("varName", " return this[varName];"));
	    	}
	    }
	</script>	
</logic:present>		  
	
<script language="JavaScript">	    
	    function caracteresespeciais () {
	     	this.ac = new Array("consumoMinimo", "Consumo M�nimo deve somente conter n�meros positivos.", new Function ("varName", " return this[varName];"));
	     	this.ad = new Array("valorTarifaMinima", "Valor da Tarifa M�nima deve somente conter n�meros positivos.", new Function ("varName", " return this[varName];"));
		}
	    function FloatValidations () {
	     	this.ae = new Array("valorTarifaMinima", "Valor da Tarifa M�nima deve somente conter n�meros decimais positivos ou zero.", new Function ("varName", " return this[varName];"));
	    }
	    function IntegerValidations () {
	     	this.ag = new Array("consumoMinimo", "Consumo M�nimo deve somente conter n�meros positivos.", new Function ("varName", " return this[varName];"));
		}

		function redirecionaForm(){
			var formRed = "/gsan/manterConsumoTarifaExistenteAction.do";
				redirecionarSubmit(formRed);
		}
		
		function validaForm(){
			var form = document.forms[0];
			var stringLocation = "";
			
			if(validateCaracterEspecial(form) && validateRequired(form) && testarCampoValorInteiroComZero(form.consumoMinimo, 'Consumo M�nimo') && validateDecimal(form)){

				var formRed ='exibirInserirCategoriaConsumoTarifaAction.do?form=exibirInserirCategoriaFaixaConsumoTarifaAction';
				redirecionarSubmit(formRed);
					
			}
		}
	
	
		function modificarSubCategoria() {
			var form = document.forms[0];

			form.action = "/gsan/exibirInserirCategoriaConsumoTarifaAction.do"
			form.submit();			
		}
	
		
		function inserirCategoriaConsumoTarifa() {
			var form = document.forms[0];

			form.submit();			
		}		

</script>
</head>

<logic:equal name="testeInserir" value="false" scope="request">
	<body leftmargin="5" topmargin="5"
		onload="resizePageSemLink(640,400); setarFoco('${requestScope.slcCategoria}');">
</logic:equal>
<logic:equal name="testeInserir" value="true" scope="request">
	<body leftmargin="5" topmargin="5"
		onload="resizePageSemLink(640,400); chamarReload('exibirInserirConsumoTarifaAction.do');window.close();">
</logic:equal>
<body leftmargin="5" topmargin="5"
	onload="resizePageSemLink(640,400); setarFoco('${requestScope.slcCategoria}');">

<html:form action="/inserirCategoriaConsumoTarifaAction"
	name="InserirCategoriaConsumoTarifaActionForm"
	onsubmit="return validateInserirCategoriaConsumoTarifaActionForm(this);"
	type="gcom.gui.faturamento.consumotarifa.InserirCategoriaConsumoTarifaActionForm"
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
					<td class="parabg">Informar Categoria</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td>Preencha os campos para inserir uma categoria na
					tarifa de consumo</td>
					<td align="right"></td>											
				</tr>
			</table>	
			<table width="100%" border="0">	
				<tr>
					<td width="27%" height="24"><strong>Categoria:<font color="#FF0000">*</font></strong></td>
					<td colspan="3">
					
						<logic:notPresent name="indicadorTarifaCosumoPorSubCategoria" scope="session">
							<html:select property="slcCategoria" style="width: 230px;">
								<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
								<html:options collection="colecaoCategoria"
									labelProperty="descricao" property="id" />
							</html:select>
						</logic:notPresent>
						
						<logic:present name="indicadorTarifaCosumoPorSubCategoria" scope="session">
							<html:select property="slcCategoria" onchange="javascript:modificarSubCategoria()" >
								<html:option
									value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
								<html:options collection="colecaoCategoria"
									labelProperty="descricao" property="id" />
							</html:select>
						</logic:present>						
							
					</td>
				</tr>
				
				<logic:present name="indicadorTarifaCosumoPorSubCategoria" scope="session">
					<tr>
						<td width="27%" height="24"><strong>SubCategoria:<font color="#FF0000">*</font></strong></td>
						<td colspan="3">
							<html:select property="slcSubCategoria">
							<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<html:options collection="colecaoSubCategoria"	labelProperty="descricao" property="id" />
						</html:select></td>
					</tr>	
				</logic:present>			
				
				<tr>
					<td height="24"><strong>Consumo M&iacute;nimo:<font color="#FF0000">*</font></strong></td>
					<td colspan="3"><html:text maxlength="6" property="consumoMinimo" onkeypress="return isCampoNumerico(event);"
						size="6" /></td>
				</tr>
				<tr>
					<td height="24"><strong>Valor da Tarifa M&iacute;nima:<font
						color="#FF0000">*</font></strong></td>
					
					<logic:equal name="pQuantidadeDecimaisValorTarifa" value="4">
						<td colspan="3"><html:text style="text-align:right;" maxlength="18"
							property="valorTarifaMinima" size="18"
							onkeyup="formataValorMonetarioQuatroDecimais(this, 18)" /></td>
					</logic:equal>
					<logic:equal name="pQuantidadeDecimaisValorTarifa" value="2">
						<td colspan="3"><html:text style="text-align:right;" maxlength="18"
							property="valorTarifaMinima" size="18"
							onkeyup="formataValorMonetario(this, 18)"/></td>
					</logic:equal>
				</tr>				
				
				<logic:present name="indicadorTarifaEsgotoPropria" scope="session">
					<tr>
						<td height="24"><strong>Valor da Tarifa M�nima de Esgoto:<font
							color="#FF0000">*</font></strong></td>
						<logic:equal name="pQuantidadeDecimaisValorTarifa" value="4">
								<td colspan="3"><html:text style="text-align:right;" maxlength="18"
									property="valorTarifaMinimaEsgoto" size="18"
									onkeyup="formataValorMonetarioQuatroDecimais(this, 18)" />
								</td>
						</logic:equal>
						
						<logic:equal name="pQuantidadeDecimaisValorTarifa" value="2">
								<td colspan="3"><html:text style="text-align:right;" maxlength="18"
									property="valorTarifaMinimaEsgoto" size="18"
									onkeyup="formataValorMonetario(this, 18)" />
								</td>
						</logic:equal>										
					</tr>
				</logic:present>
				
				
				<tr>
					<td height="25" colspan="3"><strong>Faixas de Consumo:</strong></td>

					<td align="right" width="13%" height="25"><input type="button"
						name="adicionar2" class="bottonRightCol" value="Adicionar"
						onClick="validaForm();"></td>
				</tr>
				<tr>
					<td height="24" colspan="4">
					<table width="100%" bgcolor="#99CCFF">
						<tr>
							<td>
							<div align="center" class="style9"><strong>Remover</strong></div>
							</td>
							<td>
							<div align="center" class="style9"><strong>Limite Superior</strong></div>
							</td>
							<td>
							<div align="center" class="style9"><strong>Valor da Tarifa na
							Faixa</strong></div>
							</td>
						</tr>
						<%String cor = "#FFFFFF";%>
						<logic:present name="colecaoFaixa" scope="session">
							<logic:iterate indexId="posicao" name="colecaoFaixa" id="faixa">
								<%if (cor.equalsIgnoreCase("#FFFFFF")) {
								cor = "#cbe5fe";%>
								<tr bgcolor="#FFFFFF">
									<%} else {
								cor = "#FFFFFF";%>
								<tr bgcolor="#cbe5fe">
									<%}%>
									<td>
									<div align="center" class="style9"><img
										src="<bean:message key='caminho.imagens'/>Error.gif"
										width="14" height="14" style="cursor:hand;"
										onclick="redirecionarSubmit('excluirCategoriaFaixaConsumoTarifaAction.do?posicao=<bean:write name="faixa" property="ultimaAlteracao.time" />');"></div>
									</td>
									<td>
									<div align="center" class="style9"><bean:write name="faixa"
										property="numeroConsumoFaixaIFim" /></div>
									</td>
									<td>
										<logic:equal name="pQuantidadeDecimaisValorTarifa" value="4">
											<div align="center" class="style9"><input type="text"
												style="text-align:right;" maxlength="17" size="17"
												name="valorConsumoTarifa<bean:write name="faixa" property="ultimaAlteracao.time" />"
												value="<bean:write name="faixa" property="valorConsumoTarifa" formatKey="money.quatrodecimais.format"/>"
												onkeyup="formataValorMonetarioQuatroDecimais(this, 18)"></div>
										</logic:equal>
										<logic:equal name="pQuantidadeDecimaisValorTarifa" value="2">
											<div align="center" class="style9"><input type="text"
												style="text-align:right;" maxlength="17" size="17"
												name="valorConsumoTarifa<bean:write name="faixa" property="ultimaAlteracao.time" />"
												value="<bean:write name="faixa" property="valorConsumoTarifa" formatKey="money.format"/>"
												onkeyup="formataValorMonetario(this, 18)"></div>
										</logic:equal>
									</td>
								</tr>
							</logic:iterate>
						</logic:present>
					</table>
					</td>
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
					<div align="right"><input type="hidden" name="testeInserir"> <input
						name="Button" type="submit" class="bottonRightCol" value="Inserir"
						onClick="document.forms[0].testeInserir.value='true';inserirCategoriaConsumoTarifa();">
					<input name="Button2" type="button" class="bottonRightCol"
						value="Fechar" onClick="javascript:window.close();"></div>
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
