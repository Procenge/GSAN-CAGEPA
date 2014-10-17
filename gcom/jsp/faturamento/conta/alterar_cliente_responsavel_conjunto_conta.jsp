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
<html:javascript staticJavascript="false"  formName="AlterarClienteResponsavelConjuntoContaActionForm"/>

<SCRIPT LANGUAGE="JavaScript">
<!--

function validarForm(form){
	
	var idsContas = "";
	for(indice = 0; indice < window.opener.document.forms[0].elements.length; indice++){
		if (window.opener.document.forms[0].elements[indice].type == "checkbox" && window.opener.document.forms[0].elements[indice].checked == true) {
			idsContas = idsContas + window.opener.document.forms[0].elements[indice].value + ",";
		}
	}
	idsContas = idsContas.substring(0, idsContas.length - 1);
	form.contaSelected.value = idsContas;
	
	var clienteInformado = true;
	
	if ((form.idCliente.value == '' || form.idCliente.value == '0')){
	      
	    alert("Cliente deve ser informado. ");
	    form.idCliente.focus();  
	    clienteInformado = false;
	}
	
	if (clienteInformado){
	
		if (confirm("Confirma alteração de cliente?")){
			
			submeterFormPadrao(form);
		}
	}
}

function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){
	if(objetoRelacionado.disabled != true){
		if (objeto == null || codigoObjeto == null){
			abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
		} else{
			if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
				alert(msg);
			} else{
				abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
			}
		}
	}
}

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	
    var form = document.forms[0];

    if (tipoConsulta == 'cliente') {
   	 
      form.idCliente.value = codigoRegistro;
      form.nomeCliente.value = descricaoRegistro;
      form.nomeCliente.style.color = "#000000";
      form.action='exibirAlterarClienteResponsavelConjuntoContaAction.do';
      submeterFormPadrao(form);
   }
}

function limparCliente(){
	
	var form = document.forms[0];
    form.idCliente.value = "";
    form.nomeCliente.value = "";
    form.nomeCliente.style.color = "#000000";
}

//-->
</SCRIPT>

</head>

<logic:present name="reloadPage">
	<body leftmargin="5" topmargin="5" onload="chamarSubmitComUrl('exibirManterContaConjuntoImovelAction.do'); window.close();">
</logic:present>

<logic:notPresent name="reloadPage">
	<body leftmargin="5" topmargin="5" onload="resizePageSemLink(480, 220);">
</logic:notPresent>

<html:form action="/alterarClienteResponsavelConjuntoContaAction"
	name="AlterarClienteResponsavelConjuntoContaActionForm"
	type="gcom.gui.faturamento.conta.AlterarClienteResponsavelConjuntoContaActionForm"
	method="post">
<table width="500" border="0" cellpadding="0" cellspacing="5">
  <tr>
    <td width="500" valign="top" class="centercoltext"> 
      <table height="100%">
        <tr>
          <td></td>
        </tr>
      </table>
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11"><img src="<bean:message key="caminho.imagens"/>parahead_left.gif" editor="Webstyle4" moduleid="Album Photos (Project)\toptab_page2_parahead_left.xws" border="0" /></td>
          <td class="parabg">Alterar Cliente Responsável Contas</td>
          <td width="11"><img src="<bean:message key="caminho.imagens"/>parahead_right.gif" editor="Webstyle4" moduleid="Album Photos (Project)\toptab_page2_parahead_right.xws" border="0" /></td>
        </tr>
      </table>
      <p>&nbsp;</p>

      <table width="100%" border="0">
        <tr>
          <td>Para alterar o cliente responsável da(s) conta(s) informe o motivo:</td>
          <td align="right"></td>
        </tr>
        </table>
         
         <table width="100%" border="0">
         
       <tr>
		  	<td height="10"><strong>Cliente:<font color="#FF0000">*</font></strong></td>
			<td>
				<html:text name="AlterarClienteResponsavelConjuntoContaActionForm" property="idCliente" maxlength="9" size="9"
					onkeypress="javascript:validaEnter(event, 'exibirAlterarClienteResponsavelConjuntoContaAction.do', 'idCliente');return isCampoNumerico(event);"
					onblur="javascript:verificaNumero(this);"/>
			
				<a href="javascript:redirecionarSubmit('exibirPesquisarClienteAction.do?limparForm=OK&caminhoRetornoTelaPesquisaCliente=exibirAlterarClienteResponsavelConjuntoContaAction');"> 
				<img border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" title="Pesquisar Cliente" /></a> 
				
				<logic:present name="codigoClienteNaoEncontrado" scope="request">
					<html:text name="AlterarClienteResponsavelConjuntoContaActionForm" property="nomeCliente" size="40" style="background-color:#EFEFEF; border:0; color: #ff0000" readonly="true"/>
				</logic:present> 
				
				<logic:notPresent name="codigoClienteNaoEncontrado" scope="request">
					<html:text name="AlterarClienteResponsavelConjuntoContaActionForm" property="nomeCliente" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
				</logic:notPresent> <a href="javascript:limparCliente();document.forms[0].idCliente.focus();">
				
				<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" /></a>
			</td>
		</tr>
	    <html:hidden property="contaSelected" value="" />
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



