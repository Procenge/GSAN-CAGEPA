<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="gcom.util.ConstantesSistema" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="CriterioCobrancaActionForm" dynamicJavascript="false" />


<script language="JavaScript">

 var bCancel = false;

    function validateCriterioCobrancaActionForm(form) {
        if (bCancel)
      return true;
        else
      return testarCampoValorDecimalComZero(CriterioCobrancaActionForm.valorDebitoMinimo,'Valor do Débito Mínimo') && 
      testarCampoValorDecimalComZero(CriterioCobrancaActionForm.valorDebitoMaximo,'Valor do Débito Máximo') && 
      testarCampoValorInteiroComZero(CriterioCobrancaActionForm.qtdContasMinima,'Quantidade de Contas Mínima') && 
      testarCampoValorInteiroComZero(CriterioCobrancaActionForm.qtdContasMaxima,'Quantidade de Contas Máxima') &&
      testarCampoValorDecimalComZero(CriterioCobrancaActionForm.vlMinimoDebitoCliente,'Valor Mínimo do Débito para Cliente com Débito Automático') &&
      testarCampoValorInteiroComZero(CriterioCobrancaActionForm.qtdMinContasCliente,'Quantidade Mínima de Contas para Cliente com Débito Automático') &&
      testarCampoValorDecimalComZero(CriterioCobrancaActionForm.vlMinimoContasMes,'Valor Mínimo da Conta do Mês')&& 
      testarCampoValorInteiroComZero(CriterioCobrancaActionForm.quantidadeMinimaParcelasAtraso,'Quantidade Mínima de Parcelas em Atraso');
   }

	function desfazer(){
	 	form = document.forms[0];
	 	form.reset();
	 
	}

 
	function validarForm(form){
		if(validateCriterioCobrancaActionForm(form)){
	   	 	submeterFormPadrao(form);
		}
	}

</script>
</head>

<logic:equal name="fechaPopup" value="false" scope="request">
	<body leftmargin="5" topmargin="5" onload="javascript:resizePageSemLink(585, 440);javascript:setarFoco('${requestScope.nomeCampo}');">
</logic:equal>
<logic:equal name="fechaPopup" value="true" scope="request">
	<body leftmargin="5" topmargin="5" onload="chamarReload('${sessionScope.retornarTela}');window.close()">
</logic:equal>


<html:form action="/atualizarCriterioCobrancaLinhaAction" 
           name="CriterioCobrancaActionForm"
	       type="gcom.gui.cobranca.CriterioCobrancaActionForm"
           method="post"
	       onsubmit="return validateCriterioCobrancaActionForm(this);">
	<table width="570" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="500" valign="top" class="centercoltext">
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
					<td class="parabg">Atualizar Linha do Critério de Cobrança</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">Preencha os campos para atualizar uma linha no critério de cobrança:</td>
				</tr>
				<tr>
					<td width="50%"><strong>Perfil do Imóvel:<font color="#FF0000">*</font></strong></td>
					<td width="50%"><html:text maxlength="17" property="descricaoImovelPerfil" size="17" disabled="true"/>
					</td>
				</tr>
				<tr>
					<td width="50%"><strong>Categoria:<font color="#FF0000">*</font></strong></td>
					<td width="50%"><html:text maxlength="17" property="descricaoCategoria" size="17" disabled="true"/></td>
				</tr>
				
				<tr>
			       <td><strong>Situação de Medição:</strong></td>
			       <td>
						<html:select property="situacaoMedicao" style="width: 200px;" tabindex="4">			
						  <html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						  <html:option value="1">Hidrometrada</html:option>
						  <html:option value="2">Não hidrometrada</html:option>
						</html:select>
				   </td>
			    </tr>
			    
				<tr> 
          		    <td><strong>Nº dias para Data de Vencimento:<font color="#FF0000">*</font></strong></td>
                    <td><html:text property="diasParaVencimento" size="2" maxlength="2" style="text-align: right;"/></td>
                </tr>
				
				<tr> 
                    <td><strong>Data de Ligação Limite:</strong></td>
				     <td width="40%"><html:text maxlength="10" property="dataLimite" size="10" onkeyup="javascript:mascaraData(this,event);" tabindex="2"/>
                      <img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" onclick="abrirCalendario('CriterioCobrancaActionForm', 'dataLimite')" width="20" border="0" align="absmiddle" alt="Exibir Calendário" /> dd/mm/aaaa </td>
               </tr>
               
	           <tr>
					<td width="220"><strong>Referência de Débito Inicial:&nbsp;</strong></td>
					<td align="left"><html:text property="referenciaDebitoInicial" size="9" maxlength="7" onkeyup="mascaraAnoMes(this, event);"></html:text> mm/aaaa</td>
			   </tr>
			   <tr>
					<td width="220"><strong>Referência de Débito Final:&nbsp;</strong></td>
					<td align="left"><html:text property="referenciaDebitoFinal" size="9" maxlength="7" onkeyup="mascaraAnoMes(this, event);"></html:text> mm/aaaa</td>
			   </tr>
				
				<tr>
					<td><strong>Intervalo de Valor do Débito:</strong></td>
					<td>
						<logic:present name="desabilita">
							<html:text maxlength="17" property="valorDebitoMinimo" size="17"
							tabindex="1" style="text-align: right;" onkeyup="formataValorMonetario(this, 13)" disabled="true"/>a
							<html:text maxlength="17" property="valorDebitoMaximo" size="17"
							tabindex="2" style="text-align: right;" onkeyup="formataValorMonetario(this, 13)" disabled="true"/>												
						</logic:present>
								
						<logic:notPresent name="desabilita">
							<html:text maxlength="17" property="valorDebitoMinimo" size="17"
							tabindex="1" style="text-align: right;" onkeyup="formataValorMonetario(this, 13)"/>a
							<html:text maxlength="17" property="valorDebitoMaximo" size="17"
							tabindex="2" style="text-align: right;" onkeyup="formataValorMonetario(this, 13)"/>									
						</logic:notPresent>
				
					</td>
				</tr>
				<tr>
					<td><strong>Intervalo de Quantidade de Contas:</strong></td>
					<td>
						<logic:present name="desabilita">
							<html:text maxlength="4" property="qtdContasMinima" size="5"
							tabindex="3"  disabled="true" />a
							<html:text maxlength="4" property="qtdContasMaxima" size="5"
							tabindex="4"  disabled="true" />
						</logic:present>
								
						<logic:notPresent name="desabilita">
							<html:text maxlength="4" property="qtdContasMinima" size="5"
							tabindex="3" />a
							<html:text maxlength="4" property="qtdContasMaxima" size="5"
							tabindex="4" />
						</logic:notPresent>
					</td>
				</tr>

				<tr>
					<td><strong>Valor M&iacute;nimo do D&eacute;bito para Cliente 
                         com D&eacute;bito Autom&aacute;tico:</strong></td>
					<td>
						<logic:present name="desabilita">
							<html:text maxlength="17" property="vlMinimoDebitoCliente" size="17" tabindex="5" style="text-align: right;" onkeyup="formataValorMonetario(this, 13)" disabled="true" />
						</logic:present>
						
						<logic:notPresent name="desabilita">
							<html:text maxlength="17" property="vlMinimoDebitoCliente" size="17" tabindex="5" style="text-align: right;" onkeyup="formataValorMonetario(this, 13)"/>
						</logic:notPresent>
					
					
					</td>
				</tr>
				<tr>
					<td><strong>Quantidade M&iacute;nima de Contas para Cliente 
                         com D&eacute;bito Autom&aacute;tico:</strong></td>
					<td>
						<logic:present name="desabilita">
							<html:text maxlength="4" property="qtdMinContasCliente" size="5" tabindex="6" disabled="true" />
						</logic:present>
						
						<logic:notPresent name="desabilita">
							<html:text maxlength="4" property="qtdMinContasCliente" size="5" tabindex="6" />
						</logic:notPresent>
					</td>
				</tr>
				<tr> 
          		    <td><strong>Valor M&iacute;nimo da Conta do M&ecirc;s:</strong></td>
                    <td>
                   		<logic:present name="desabilita">
							<html:text property="vlMinimoContasMes" size="17" maxlength="17" style="text-align: right;" onkeyup="formataValorMonetario(this, 13)" tabindex="7" disabled="true"/>
						</logic:present>
						
						<logic:notPresent name="desabilita">
							<html:text property="vlMinimoContasMes" size="17" maxlength="17" style="text-align: right;" onkeyup="formataValorMonetario(this, 13)" tabindex="7"/>	
						</logic:notPresent>
                    
                    </td>
                </tr>
                 <tr> 
          		    <td><strong>Quantidade Mínima de Parcelas em Atraso:</strong></td>
                    <td>
                      <logic:present name="desabilita">
							<html:text property="quantidadeMinimaParcelasAtraso" size="2" maxlength="2" style="text-align: right;" disabled="true"/>
						</logic:present>
						
						<logic:notPresent name="desabilita">
							<html:text property="quantidadeMinimaParcelasAtraso" size="2" maxlength="2" style="text-align: right;"/>	
						</logic:notPresent>
                   </td>
                   
                </tr>
                <tr>
					<td><strong>Intervalo de Quantidade de Dias Vencidos:</strong></td>
					<td><html:text maxlength="5" property="qtdDiasVencMinima" size="6"
						tabindex="5" />a
						<html:text maxlength="5" property="qtdDiasVencMaxima" size="6"
						tabindex="6" />
					</td>
				</tr>
                <tr> 
                   <td>&nbsp;</td>
                   <td><strong><font color="#FF0000">*</font></strong> Campos 
                     obrigat&oacute;rios</td>
               </tr>
               <tr> 
                  <td> 
                  <input name="button" type="button"
						class="bottonRightCol" value="Desfazer" onclick="desfazer();">
				  </td>	
                  <td colspan="3">
                  <div align="right"> 
                   <input name="Button" type="button" class="bottonRightCol" value="Atualizar" onclick="validarForm(document.forms[0]);" tabindex="8">
                   <input name="Button2" type="button" class="bottonRightCol" value="Fechar" onClick="javascript:window.close();">
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
