<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="gcom.util.ConstantesSistema" %>
<%@ page import="gcom.util.Util" %>
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
      return validateRequired(form) &&
      testarCampoValorDecimalComZero(CriterioCobrancaActionForm.valorDebitoMinimo,'Valor do D�bito M�nimo') && 
      testarCampoValorDecimalComZero(CriterioCobrancaActionForm.valorDebitoMaximo,'Valor do D�bito M�ximo') && 
      testarCampoValorInteiroComZero(CriterioCobrancaActionForm.qtdContasMinima,'Quantidade de Contas M�nima') && 
      testarCampoValorInteiroComZero(CriterioCobrancaActionForm.qtdContasMaxima,'Quantidade de Contas M�xima') &&
      testarCampoValorDecimalComZero(CriterioCobrancaActionForm.vlMinimoDebitoCliente,'Valor M�nimo do D�bito para Cliente com D�bito Autom�tico') &&
      testarCampoValorInteiroComZero(CriterioCobrancaActionForm.qtdMinContasCliente,'Quantidade M�nima de Contas para Cliente com D�bito Autom�tico') &&
      testarCampoValorDecimalComZero(CriterioCobrancaActionForm.vlMinimoContasMes,'Valor M�nimo da Conta do M�s')&& 
      testarCampoValorInteiroComZero(CriterioCobrancaActionForm.quantidadeMinimaParcelasAtraso,'Quantidade M�nima de Parcelas em Atraso') &&
      verificaDataMensagemPersonalizada(form.dataLimite, "Data de Liga��o Limite inv�lida.");
   }

   
    function required () { 
	this.aa = new Array("parImovelPerfil", "Informe Perfil do Im�vel.", new Function ("varName", " return this[varName];"));
	this.ab = new Array("parCategoria", "Informe Categoria.", new Function ("varName", " return this[varName];"));
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
	<body leftmargin="5" topmargin="5" onload="javascript:resizePageSemLink(600, 480);javascript:setarFoco('${requestScope.nomeCampo}');">
</logic:equal>
<logic:equal name="fechaPopup" value="true" scope="request">
	<body leftmargin="5" topmargin="5" onload="chamarReload('${sessionScope.retornarTela}');window.close()">
</logic:equal>

<html:form action="/adicionarCriterioCobrancaLinhaAction" 
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
					<td class="parabg">Adicionar Linha do Crit�rio de Cobran�a</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">Preencha os campos para inserir uma linha no crit�rio de cobran�a:</td>
				</tr>
				<tr>
					<td width="50%"><strong>Perfil do Im�vel:<font color="#FF0000">*</font></strong></td>
					<td width="50%"><html:select multiple="true" property="parImovelPerfil" tabindex="1" style="width: 200px;font-size:11px;">
                        <logic:iterate name="colecaoImovelPerfil" id="imovelPerfil" >
							<option value="<bean:write name="imovelPerfil" property="id" />;<bean:write name="imovelPerfil" property="descricao" />" ><bean:write name="imovelPerfil" property="descricao" /></option>
                        </logic:iterate>
			
					</html:select></td>
				</tr>
				<tr>
					<td width="50%"><strong>Categoria:<font color="#FF0000">*</font></strong></td>
					<td width="50%"><html:select multiple="true" property="parCategoria" tabindex="2" style="width: 200px;font-size:11px;">
						<logic:iterate name="colecaoCategoria" id="categoria" >
							<option value="<bean:write name="categoria" property="id" />;<bean:write name="categoria" property="descricao" />" ><bean:write name="categoria" property="descricao" /></option>
                        </logic:iterate>
					</html:select></td>
				</tr>
				<tr>
			       <td><strong>Situa��o de Medi��o:</strong></td>
			       <td>
						<html:select property="situacaoMedicao" style="width: 200px;" tabindex="3">			
						  <html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						  <html:option value="1">Hidrometrada</html:option>
						  <html:option value="2">N�o hidrometrada</html:option>
						</html:select>
				   </td>
			    </tr>
			    
				<tr> 
          		    <td><strong>N� dias para Data de Vencimento:<font color="#FF0000">*</font></strong></td>
                    <td><html:text property="diasParaVencimento" size="2" maxlength="2" style="text-align: right;" tabindex="4"/></td>
                </tr>
				
				<tr> 
                    <td><strong>Data de Liga��o Limite:</strong></td>
				     <td width="40%"><html:text maxlength="10" property="dataLimite" size="10" onkeyup="javascript:mascaraData(this,event);" tabindex="5"/>
                      <img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" onclick="abrirCalendario('CriterioCobrancaActionForm', 'dataLimite')" width="20" border="0" align="absmiddle" alt="Exibir Calend�rio" /> dd/mm/aaaa </td>
               </tr>
               
	           <tr>
					<td width="220"><strong>Refer�ncia de D�bito Inicial:&nbsp;</strong></td>
					<td align="left"><html:text property="referenciaDebitoInicial" size="9" maxlength="7" onkeyup="mascaraAnoMes(this, event);" tabindex="6"></html:text> mm/aaaa</td>
			   </tr>
			   <tr>
					<td width="220"><strong>Refer�ncia de D�bito Final:&nbsp;</strong></td>
					<td align="left"><html:text property="referenciaDebitoFinal" size="9" maxlength="7" onkeyup="mascaraAnoMes(this, event);" tabindex="7"></html:text> mm/aaaa</td>
			   </tr>
			    
				<tr>
					<td><strong>Intervalo de Valor do D�bito:</strong></td>
					<td><html:text maxlength="17" property="valorDebitoMinimo" size="17"
						tabindex="8" style="text-align: right;" onkeyup="formataValorMonetario(this, 13)"/>a
						<html:text maxlength="17" property="valorDebitoMaximo" size="17"
						tabindex="9" style="text-align: right;" onkeyup="formataValorMonetario(this, 13)"/>
					</td>
				</tr>
				<tr>
					<td><strong>Intervalo de Quantidade de Contas:</strong></td>
					<td><html:text maxlength="4" property="qtdContasMinima" size="5"
						tabindex="10" />a
						<html:text maxlength="4" property="qtdContasMaxima" size="5"
						tabindex="11" />
					</td>
				</tr>

				<tr>
					<td><strong>Valor M&iacute;nimo do D&eacute;bito para Cliente 
                         com D&eacute;bito Autom&aacute;tico:</strong></td>
					<td><html:text maxlength="17" property="vlMinimoDebitoCliente" size="17" tabindex="12" style="text-align: right;" onkeyup="formataValorMonetario(this, 13)"/></td>
				</tr>
				<tr>
					<td><strong>Quantidade M&iacute;nima de Contas para Cliente 
                         com D&eacute;bito Autom&aacute;tico:</strong></td>
					<td><html:text maxlength="4" property="qtdMinContasCliente" size="5" tabindex="13" /></td>
				</tr>
				<tr> 
          		    <td><strong>Valor M&iacute;nimo da Conta do M&ecirc;s:</strong></td>
                    <td><html:text property="vlMinimoContasMes" size="17" maxlength="17" style="text-align: right;" onkeyup="formataValorMonetario(this, 13)" tabindex="14"/></td>
                </tr>
                <tr> 
          		    <td><strong>Quantidade M�nima de Parcelas em Atraso:</strong></td>
                    <td><html:text property="quantidadeMinimaParcelasAtraso" size="2" maxlength="2" style="text-align: right;" tabindex="15"/></td>
                </tr>
                <tr>
					<td><strong>Intervalo de Quantidade de Dias Vencidos:</strong></td>
					<td><html:text maxlength="5" property="qtdDiasVencMinima" size="6"
						tabindex="16" />a
						<html:text maxlength="5" property="qtdDiasVencMaxima" size="6"
						tabindex="17" />
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
						class="bottonRightCol" value="Desfazer" onclick="desfazer();" tabindex="18">
				  </td>	
                  <td colspan="3">
                  <div align="right"> 
                   <input name="Button" type="button" class="bottonRightCol" value="Inserir" onclick="validarForm(document.forms[0]);" tabindex="19">
                   <input name="Button2" type="button" class="bottonRightCol" value="Fechar" onClick="javascript:window.close();" tabindex="20">
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
