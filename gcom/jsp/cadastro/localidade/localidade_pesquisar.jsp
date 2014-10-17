<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>


<%@ page import="gcom.util.Pagina, gcom.util.ConstantesSistema"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="PesquisarActionForm"/>

<script>

 var bCancel = false;

    function validatePesquisarActionForm(form) {
        if (bCancel)
          return true;
        else
         return validateCaracterEspecial(form) && testarCampoValorZero(document.forms[0].idLocalidade, 'Código')
       && validateLong(form);
   }

    function caracteresespeciais () {
     this.ab = new Array("descricaoLocalidade", "Descrição possui caracteres especiais.", new Function ("varName", " return this[varName];"));
    }

    function IntegerValidations () {
     this.aa = new Array("idLocalidade", "Código deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    }
    
    function limparForm(){
    	var form = document.forms[0];
    	
    	form.idLocalidade.value = "";
    	form.descricaoLocalidade.value = "";
    	if (form.desabilitarGerenciaRegional.value == null || form.desabilitarGerenciaRegional.value == '2'){
    		form.idGerenciaRegional.value = "-1";
    	}
   		form.idUnidadeNegocio.value = "-1";
    }
    
    function validarForm(form){
		form.submit();
    }
    
    
</script>
</head>

<body leftmargin="5" topmargin="5" onload="window.focus();resizePageSemLink(480, 298);setarFoco('${requestScope.nomeCampo}');">
<html:form
  action="/pesquisarLocalidadeAction"
  method="post"
  onsubmit="return validatePesquisarActionForm(this)"
>

<html:hidden property="desabilitarGerenciaRegional"/>

<table width="445" border="0" cellspacing="5" cellpadding="0">
  <tr>
    <td width="445" valign="top" class="centercoltext">
    <table height="100%">
        <tr>
          <td></td>
        </tr>
      </table>
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11"><img  border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
          <td class="parabg">Pesquisar Localidade</td>
          <td width="11"><img  border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
        </tr>
      </table>
      <p>&nbsp;</p>
      <table width="100%" border="0">
        <tr>
          <td colspan="4">Preencha os campos para pesquisar uma localidade:</td>
        	<td align="right"></td>
    	</tr>
      </table>
    
      <table width="100%" border="0">
        <tr>
          <td width="14%"><strong>Código:</strong></td>
          <td width="86%" colspan="3"><html:text maxlength="3" property="idLocalidade" size="3" tabindex="1"/></td>
        </tr>
        <tr>
          <td height="0"><strong>Descrição:</strong></td>
          <td colspan="3"><html:text maxlength="30" property="descricaoLocalidade" size="35" tabindex="2"/>
          </td>
        </tr>
        <tr>
			<td>&nbsp;</td>
			<td><html:radio property="tipoPesquisa"
					value="<%=ConstantesSistema.TIPO_PESQUISA_INICIAL.toString()%>" />
				Iniciando pelo texto<html:radio property="tipoPesquisa"
					tabindex="5"
					value="<%=ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString()%>" />
				Contendo o texto</td>
			</tr>
        <tr>
          <td height="0"><strong>Gerência Regional:</strong></td>
          <logic:equal name="PesquisarActionForm" property="desabilitarGerenciaRegional" value="2">
	          <td colspan="3">
	             <html:select property="idGerenciaRegional" tabindex="3">
		       		<html:option value="-1">&nbsp;</html:option>
	               	<html:options collection="gerenciasRegionais" labelProperty="nome" property="id"/>
	             </html:select>
	          </td>
          </logic:equal>
         <logic:equal name="PesquisarActionForm" property="desabilitarGerenciaRegional" value="1">
	          <td colspan="3">
	             <html:select property="idGerenciaRegional" disabled="true" tabindex="3">
		       <html:option value=""></html:option>
	               <html:options collection="gerenciasRegionais" labelProperty="nome" property="id"/>
	             </html:select>
	          </td>
          </logic:equal>
        </tr>
		<tr>
			<td><strong>Unidade de Neg&oacute;cio:</strong></td>
			<td colspan="3">
			   <html:select property="idUnidadeNegocio" tabindex="3">
					<html:option value="-1">&nbsp;</html:option>
					<logic:present name="unidadesNegocios"> 
						 <html:options collection="unidadesNegocios" labelProperty="nome" property="id"/>
					</logic:present>				
			   </html:select>
			</td>
		</tr>
        <tr>
          <td>&nbsp;</td>
          <td colspan="3">&nbsp;</td>
        </tr>
        <tr>
          <td height="24" colspan="3" width="80%">
          	<INPUT TYPE="button" class="bottonRightCol" value="Limpar" onclick="document.forms[0].reset();limparForm();"/>
          	&nbsp;&nbsp;
          	<logic:present name="caminhoRetornoTelaPesquisaLocalidade">
          		<INPUT TYPE="button" class="bottonRightCol" value="Voltar" onclick="redirecionarSubmit('${caminhoRetornoTelaPesquisaLocalidade}.do')"/>
          	</logic:present>
          </td>
          <td align="right">
           <INPUT TYPE="submit" class="bottonRightCol" value="Pesquisar"/> </td>
        </tr>
      </table>
	</td>
  </tr>
</table>
</html:form>
</body>
</html:html>
