<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="gcom.util.ConstantesSistema" %>

<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>

<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<SCRIPT LANGUAGE="JavaScript">
<!--

function validarForm(form){
	if (form.motivoCancelamentoContaID.value == -1){
		alert("Informe Motivo do Cancelamento.");
	}
	else if (confirm("Confirma cancelamento da conta?")){
		submeterFormPadrao(form);
	}
	
}

//-->
</SCRIPT>

</head>

<logic:present name="reloadPage">
	<body leftmargin="5" topmargin="5" onload="chamarSubmitComUrl('exibirManterContaAction.do?cancelar=1'); window.close();">
</logic:present>

<logic:notPresent name="reloadPage">
	<body leftmargin="5" topmargin="5" onload="resizePageSemLink(480, 220);">
</logic:notPresent>


<html:form action="/cancelarContaAction" method="post">

<html:hidden property="contaSelected"/>

<table width="452" border="0" cellpadding="0" cellspacing="5">
  <tr>
    <td width="442" valign="top" class="centercoltext"> 
      <table height="100%">
        <tr>
          <td></td>
        </tr>
      </table>
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11"><img src="<bean:message key="caminho.imagens"/>parahead_left.gif" editor="Webstyle4" moduleid="Album Photos (Project)\toptab_page2_parahead_left.xws" border="0" /></td>
          <td class="parabg">Cancelar Conta</td>
          <td width="11"><img src="<bean:message key="caminho.imagens"/>parahead_right.gif" editor="Webstyle4" moduleid="Album Photos (Project)\toptab_page2_parahead_right.xws" border="0" /></td>
        </tr>
      </table>
      <p>&nbsp;</p>

      <table width="100%" border="0">
        <tr>
          <td>Para cancelar a(s) conta(s) informe o motivo:</td>
          <td align="right"></td>
        </tr>
        </table>
         <table width="100%" border="0">
        <tr>
          <td width="150" height="20"><strong>Motivo do Cancelamento:<font
						color="#FF0000">*</font></strong></td>
          <td colspan="3">
          
          	<html:select property="motivoCancelamentoContaID" style="width: 250px;" tabindex="1">
	          	<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
				<logic:present name="colecaoMotivoCancelamentoConta">
					<html:options collection="colecaoMotivoCancelamentoConta" labelProperty="descricaoMotivoCancelamentoConta" property="id"/>
				</logic:present>
			</html:select>
          
          </td>
        </tr>
        <tr>
          <td colspan="4" height="5"></td>
        </tr>
        <tr>
          <td height="30" colspan="4"> 
          	<div align="right">
              <input type="button" onclick="validarForm(document.forms[0]);" class="bottonRightCol" value="Concluir" style="width: 70px;">&nbsp;
              <input type="button" onclick="window.close();" class="bottonRightCol" value="Fechar" style="width: 70px;">
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



