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
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="InserirGrupoActionForm" dynamicJavascript="false" />
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>

<script>
<!--
    var bCancel = false;

    function validateInserirGrupoActionForm(form) {
        if (bCancel)
      return true;
        else
       return  validateRequired(form) && validateCaracterEspecial(form) ;
   }

    function caracteresespeciais () {
      this.aa = new Array("descricao", "Descri��o possui caracteres especiais.", new Function ("varName", " return this[varName];"));
      this.ab = new Array("descricaoAbreviada", "Descri��o Abreviada possui caracteres especiais.", new Function ("varName", " return this[varName];"));
    }
    
    function required () {
      this.aa = new Array("descricao", "Informe Descri��o.", new Function ("varName", " return this[varName];"));
      this.ab = new Array("descricaoAbreviada", "Informe a Descri��o Abreviada.", new Function ("varName", " return this[varName];"));
    }
-->
</script>

</head>

<body onload="document.forms[0].descricao.focus();">

<html:form
    action="/inserirGrupoWizardAction"
    method="post"
    onsubmit="return validateInserirGrupoActionForm(this);"
>

<jsp:include page="/jsp/util/wizard/navegacao_abas_wizard_valida_avancar.jsp?numeroPagina=1"/>

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

<table width="770" border="0" cellspacing="4" cellpadding="0">

<input type="hidden" name="numeroPagina" value="1"/>

  <tr>
    <td width="149" valign="top" class="leftcoltext">
      <div align="center">
        <%--<p align="left">&nbsp;</p>--%>
        <%--<p align="left">&nbsp;</p>--%>
        <%--<p align="left">&nbsp;</p>--%>
        
		<%@ include file="/jsp/util/informacoes_usuario.jsp"%>
		
        <%--<p align="left">&nbsp;</p>--%>
        <%--<p align="left">&nbsp;</p>--%>
        <%--<p align="left">&nbsp;</p>--%>
        <%--<p align="left">&nbsp;</p>--%>
        <%--<p align="left">&nbsp;</p>--%>
        <%--<p align="left">&nbsp;</p>--%>
        <%--<p align="left">&nbsp;</p>--%>
        <%--<p align="left">&nbsp;</p>--%>
        <%--<p align="left">&nbsp;</p>--%>
        <%--<p align="left">&nbsp;</p>--%>
        <%--<p align="left">&nbsp;</p>--%>
        <%--<p align="left">&nbsp;</p>--%>

		<%@ include file="/jsp/util/mensagens.jsp"%>

        <%--<p align="left">&nbsp;</p>--%>
        <%--<p align="left">&nbsp;</p>--%>
        <%--<p align="left">&nbsp;</p>--%>
        <%--<p align="left">&nbsp;</p>--%>
        <%--<p align="left">&nbsp;</p>--%>
        <%--<p align="left">&nbsp;</p>--%>
        <%--<p align="left">&nbsp;</p>--%>
      </div>
    </td>
    <td width="625" valign="top" class="centercoltext">
    
      <table height="100%">
        <tr>
          <td></td>
        </tr>
      </table>
    
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
          <td class="parabg">Inserir Grupo - Dados Gerais</td>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
        </tr>
      </table>
    
      <p>&nbsp;</p>
    
      <table border="0" width="100%">
        <tr>
          <td colspan="2">Para adicionar o grupo, informe os dados abaixo:</td>
        </tr>
        <tr>
          <td width="26%"><strong>Descri��o:<font color="#ff0000">*</font></strong></td>
          <td width="74%">
			<html:text maxlength="40" property="descricao" size="40" />
          </td>
        </tr>
        
        <tr> 
          <td width="26%"><strong>Descri��o Abreviada:<font color="#ff0000">*</font></strong></td>
          <td width="74%">
			<html:text maxlength="10" property="descricaoAbreviada" size="10" />
          </td>
        </tr>
        
        <tr> 
          <td width="26%">&nbsp;</td>
          <td width="74%"><strong><font color="#ff0000">*</font></strong>Campo obrigat�rio</td>
        </tr>
        
        <tr>
          <td colspan="2"><div align="right"><jsp:include page="/jsp/util/wizard/navegacao_botoes_wizard_valida_avancar.jsp?numeroPagina=1"/></div></td>
        </tr>
        
     </table>
   </td>
 </tr>
</table>

<%@ include file="/jsp/util/rodape.jsp"%>

</body>
</html:form>
</html:html>
