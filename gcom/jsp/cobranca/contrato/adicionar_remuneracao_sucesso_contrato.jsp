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
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="AdicionarRemuneracaoSucessoContratoActionForm"/>
<SCRIPT LANGUAGE="JavaScript">
<!--

function validarForm(form){
	
	if (validateAdicionarRemuneracaoSucessoContratoActionForm(form)){
	
		if (testarCampoValorZero(form.percentual, "Percentual")){
			submeterFormPadrao(form);
		}
	}
}

function verificaCampoVazio(objeto1, objeto2) {
	var form = document.forms[0];
	if (objeto1.value != "") {
		objeto2.disabled = true;
	} else {
		objeto2.disabled = false;
	}
}

//-->
</SCRIPT>

</head>

<logic:present name="reloadPage">

	<logic:equal name="reloadPageURL" value="INSERIRCONTRATO">
		<body leftmargin="5" topmargin="5" onload="window.focus(); resizePageSemLink(480, 280); chamarSubmitComUrl('exibirInserirContratoCobrancaAction.do?reloadPage=1'); self.close();">
	</logic:equal>
	
	<logic:equal name="reloadPageURL" value="MANTERCONTRATO">
		<body leftmargin="5" topmargin="5" onload="resizePageSemLink(580, 350);	toUpperCase(opener.document.forms[0]); opener.reloadPagina();">
	</logic:equal>
	
</logic:present>

<logic:notPresent name="reloadPage">
	<body leftmargin="5" topmargin="5" onload="window.focus(); resizePageSemLink(480, 280);">
</logic:notPresent>


<html:form action="/adicionarRemuneracaoSucessoContratoAction" method="post">

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
          <td class="parabg">Adicionar Remuneração por Sucesso</td>
          <td width="11"><img src="<bean:message key="caminho.imagens"/>parahead_right.gif" editor="Webstyle4" moduleid="Album Photos (Project)\toptab_page2_parahead_right.xws" border="0" /></td>
        </tr>
      </table>
      <p>&nbsp;</p>

      <table width="100%" border="0">
        <tr>
          <td colspan="4">Preencha os campos abaixo para inserir uma remuneração por sucesso:</td>
        </tr>
        </table>
      <table width="100%" border="0">

        <tr>
          <td><strong>Dias Vencidos (até):<font color="#FF0000">*</font></strong></strong></td>
          <td colspan="3"><html:text
						property="diasVencidos" size="10" maxlength="9"
						onkeyup="javascript:verificaNumeroInteiro(this);"/>
          </td>
        </tr>
        <tr>
          <td><strong>Percentual de Remuneração:</strong></td>
          <td colspan="3"><html:text property="percentualRemuneracao" size="15" maxlength="6" 
			                		onkeyup="formataValorMonetario(this, 6); verificaCampoVazio(percentualRemuneracao, valorFixo);" 
			                		style="text-align:right;"/> ou
          </td>
        </tr>
        <tr>
          <td><strong>Valor Fixo:</td>
          <td colspan="3"><html:text property="valorFixo" size="15" style="text-align: right;" 
          							 onkeyup="formataValorMonetario(this, 11); verificaCampoVazio(valorFixo, percentualRemuneracao);"/>
          </td>
        </tr>
        <tr>
          <td><strong>Percentual sobre Parcela Paga:<font color="#FF0000">*</font></strong></td>
          <td colspan="3"><html:text property="percentualParcelaPaga" size="15" maxlength="6" 
			                		onkeyup="formataValorMonetario(this, 6)" style="text-align:right;" />
          </td>
        </tr>
        <tr>
          <td height="30" colspan="4"> 
          	<div align="right">
              <input type="button" onclick="validarForm(document.forms[0]);" class="bottonRightCol" value="Inserir" style="width: 70px;">&nbsp;
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



