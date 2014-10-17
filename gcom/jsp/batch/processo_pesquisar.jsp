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

 <script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="PesquisarAtividadeActionForm"/>
<script>

 var bCancel = false;

 	function validarForm(form){
		if(validatePesquisarProcessoActionForm(form)){
    		submeterFormPadrao(form);
		}
	}

	/* Limpa Form */	 
	function limparForm() {
		redirecionarSubmit("exibirPesquisarProcessoAction.do?limparForm=OK");
	}   

</script>
</head>

<body leftmargin="5" topmargin="5" onload="window.focus();resizePageSemLink(520, 320);setarFoco('${requestScope.nomeCampo}');">
<html:form
  action="/pesquisarProcessoAction"
  method="post"
  onsubmit="return validatePesquisarProcessoActionForm(this);"
>
<table width="480" border="0" cellspacing="5" cellpadding="0">
  <tr>
    <td width="440" valign="top" class="centercoltext">
    <table height="100%">
        <tr>
          <td></td>
        </tr>
      </table>
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11"><img  border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
          <td class="parabg">Pesquisar Processo</td>
          <td width="11"><img  border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
        </tr>
      </table>
      <p>&nbsp;</p>
      
      <table width="100%" border="0">
        <tr>
          <td colspan="2">Preencha os campos para pesquisar um processo:</td>
        </tr>
        <tr>
          <td width="15%"><strong>Descrição:</strong></td>
          <td width="85%"><html:text maxlength="40" property="descricao" size="35"/>
          </td>
        </tr>
        <tr>
		  <td width="15%">&nbsp;</td>
		  <td width="85%">
		    <html:radio property="tipoPesquisa"
			   value="<%=ConstantesSistema.TIPO_PESQUISA_INICIAL.toString()%>" />Iniciando pelo texto
			<html:radio property="tipoPesquisa" tabindex="5"
			   value="<%=ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString()%>" />Contendo o texto
		  </td>
		</tr>        
        <tr>
          <td width="15%"><strong>Descrição Abreviada:</strong></td>
          <td width="85%"><html:text maxlength="10" property="descricaoAbreviada" size="10"/>
          </td>
        </tr>
		<tr>
			<td width="26%"><strong>Tipo do Processo:<font color="#FF0000">*</font></strong></td>
			<td width="74%" colspan="2"><html:select property="idProcessoTipo"
				tabindex="2">
				<html:option
					value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
				<html:options collection="colecaoProcessoTipo"
					labelProperty="descricao" property="id" />
			</html:select></td>
		</tr>     
        <tr>
          <td width="15%">&nbsp;</td>
          <td width="85%">&nbsp;</td>
        </tr>
        <tr>
          <td>
          	<INPUT TYPE="button" class="bottonRightCol" value="Limpar" onclick="javascript:limparForm(document.forms[0]);"/>
         </td>
          <td>
          	<table width="100%" border="0">
          	<tr>
          		<td align="right"><INPUT TYPE="submit" class="bottonRightCol" value="Pesquisar" onClick="javascript:validarForm();"/></td>
          	</tr>
          	</table>
          </tr>
      </table>
	</td>
  </tr>
</table>
</body>
</html:form>
</html:html>
