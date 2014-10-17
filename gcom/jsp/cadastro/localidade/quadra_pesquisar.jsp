<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ page import="gcom.util.ConstantesSistema" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="PesquisarActionForm" dynamicJavascript="false" />

<script>

 var bCancel = false;
 
 	function validatePesquisarActionForm(form) {
      if (bCancel)
      	return true;
      else
       return validateCaracterEspecial(form) && validateLong(form);
   }
 
    function caracteresespeciais () {
     this.ab = new Array("nomeBairro", "Bairro possui caracteres especiais.", new Function ("varName", " return this[varName];"));
    }

    function limparForm() {
    	var form = document.PesquisarActionForm;
    	form.nomeBairro.value="";
    }
</script>

</head>

<body leftmargin="5" topmargin="5" onload="resizePageSemLink(480, 280);setarFoco('${requestScope.nomeCampo}');">
<html:form action="/pesquisarQuadraAction" method="post" onsubmit="return validatePesquisarActionForm(this);">
<table width="452 " border="0" cellspacing="5" cellpadding="0">
  <tr>
    <td width="452" valign="top" class="centercoltext">
    <table height="100%">
        <tr>
          <td></td>
        </tr>
      </table>
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11"><img  border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
          <td class="parabg">Pesquisar Quadra</td>
          <td width="11"><img  border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
        </tr>
      </table>
      <p>&nbsp;</p>
      
      <table width="100%" border="0">
        <tr>
          <td colspan="4">Preencha os campos para pesquisar uma quadra:</td>
        </tr>
      </table>
      
      <table width="100%" border="0">
		
		<tr>
          <td height="0"><strong>Bairro:</strong></td>
          <td colspan="3"><html:text maxlength="30" property="nomeBairro" size="40" tabindex="2"/></td>
        </tr>
        
        <tr>
			<td>&nbsp;</td>
			<td>
				<html:radio property="tipoPesquisa" value="<%=ConstantesSistema.TIPO_PESQUISA_INICIAL.toString()%>" /> Iniciando pelo texto 
				<html:radio property="tipoPesquisa" tabindex="5" value="<%=ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString()%>" /> Contendo o texto
			</td>
		</tr>
        <tr>
       <tr>
          <td>&nbsp;</td>
          <td colspan="3">&nbsp;</td>
        </tr>
        <tr>
          <td height="24" colspan="2">
          	<INPUT TYPE="button" class="bottonRightCol" value="Limpar" onclick="limparForm();"/>&nbsp;&nbsp;
          	<logic:present name="caminhoRetornoTelaPesquisaQuadra">
          		<INPUT TYPE="button" class="bottonRightCol" value="Voltar" onclick="redirecionarSubmit('${caminhoRetornoTelaPesquisaQuadra}.do')"/>
          	</logic:present>
          </td>
          <td align="right"><INPUT TYPE="submit" class="bottonRightCol" value="Pesquisar"/></td>
        </tr>
      </table>
      <p>&nbsp;</p></td>
  </tr>
</table>
</body>
</html:form>
</html:html>