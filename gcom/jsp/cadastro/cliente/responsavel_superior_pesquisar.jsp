<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="PesquisarActionForm" dynamicJavascript="false" />
<script>
var bCancel = false;

    function validatePesquisarActionForm(form) {
        if (bCancel)
      return true;
        else
       return testarCampoValorZero(document.PesquisarActionForm.cnpj, 'CNPJ')&& validateCaracterEspecial(form) && validateLong(form) && validateCnpj(form) ;
   }

    function caracteresespeciais () {
     this.ab = new Array("cnpj", "CNPJ possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ac = new Array("nome", "Nome possui caracteres especiais.", new Function ("varName", " return this[varName];"));
    }

    function IntegerValidations () {
     this.ab = new Array("cnpj", "CNPJ deve somente conter dígitos.", new Function ("varName", " return this[varName];"));
    }

    function cnpj () {
     this.aa = new Array("cnpj", "CNPJ inválido.", new Function ("varName", " return this[varName];"));
    }
</script>
</head>

<body leftmargin="5" topmargin="5" onload="window.focus();javascript:resizePageSemLink(500, 280);javascript:setarFoco('${requestScope.nomeCampo}');">

<html:form
  action="/pesquisarResponsavelSuperiorAction"
  method="post"
  onsubmit="return validatePesquisarActionForm(this);"
>

<table width="460" border="0" cellspacing="5" cellpadding="0">
  <tr>
    <td width="460" valign="top" class="centercoltext">
      <table height="100%">
        <tr>
          <td></td>
        </tr>
      </table>
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11"><img  border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
          <td class="parabg">Pesquisar Respons&aacute;vel Superior</td>
          <td width="11"><img  border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
        </tr>
      </table>
      <p>&nbsp;</p>
      <table width="100%" border="0">
        <tr>
          <td colspan="2">Preencha os campos para pesquisar um respons&aacute;vel:</td>
          <td align="right"></td>
        </tr>
      </table>
        
      <table width="100%" border="0">
        <tr>
          <td width="14%"><strong>CNPJ:</strong></td>
          <td width="86%"><html:text maxlength="14" property="cnpj" size="14"/>
	    <br><font color="red"><html:errors property="cnpj"/></font></td>
        </tr>
        <tr>
          <td ><strong>Nome:</strong></td>
          <td><html:text maxlength="50" property="nome" size="50"/>
          <br><font color="red"><html:errors property="nome"/></font>
          </td>
        </tr>
        <tr>

        <tr>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>
			<input value="Limpar" class="bottonRightCol" type="button" onclick="javascript:window.location.href='/gsan/exibirPesquisarResponsavelSuperiorAction.do?menu=sim'">
		  </td>
          <td align="right"><html:submit styleClass="bottonRightCol" value="Pesquisar"/></td>
        </tr>
      </table>
      <p>&nbsp;</p></td>
  </tr>
</table>
</html:form>
</body>
</html:html>
