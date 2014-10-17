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
<html:javascript staticJavascript="false"  formName="CancelarContaActionForm"/>

<SCRIPT LANGUAGE="JavaScript">
<!--

function validarForm(form){
	var raValidado = false;
	
	var idsContas = "";
	for(indice = 0; indice < window.opener.document.forms[0].elements.length; indice++){
		if (window.opener.document.forms[0].elements[indice].type == "checkbox" && window.opener.document.forms[0].elements[indice].checked == true) {
			idsContas = idsContas + window.opener.document.forms[0].elements[indice].value + ",";
		}
	}
	idsContas = idsContas.substring(0, idsContas.length - 1);
	form.contaSelected.value = idsContas;
	

	if (isNaN("${sessionScope.indicadorObrigatoriedadeRA}") && "${sessionScope.indicadorObrigatoriedadeRA}" != null && 
			isNaN("${sessionScope.contaMotivoCancelamentoSelecionada}") && "${sessionScope.contaMotivoCancelamentoSelecionada}" != null){
		if(form.numeroRA.value == null || form.numeroRA.value == ""){
			form.numeroRA.focus();
			alert("Informe o número do RA - Registro de Atendimento.");
		}else{
			raValidado = true;
		}
	}else{
		raValidado = true;
	}
	
	if(raValidado){
		if (form.motivoCancelamentoContaID.value == -1){
			alert("Informe Motivo do Cancelamento.");
		}else{
			if (confirm("Confirma cancelamento das contas?")){
				submeterFormPadrao(form);
			}
		}
	}
}

function limparPesquisaRA() {
	var form = document.forms[0];
	form.numeroRA.value = "";

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
	
    if (tipoConsulta == 'registroAtendimento') {
      form.numeroRA.value = codigoRegistro;	      
    }
}

function verificarExibirRA(){

	var form = document.forms[0];
    form.action='exibirCancelarConjuntoContaAction.do';
    form.submit();

}

//-->
</SCRIPT>

</head>

<logic:present name="reloadPage">
	<body leftmargin="5" topmargin="5" onload="chamarSubmitComUrl('exibirManterContaConjuntoImovelAction.do?cancelar=1'); window.close();">
</logic:present>

<logic:notPresent name="reloadPage">
	<body leftmargin="5" topmargin="5" onload="resizePageSemLink(480, 220);">
</logic:notPresent>

<html:form action="/cancelarConjuntoContaAction"
	name="CancelarContaActionForm"
	type="gcom.gui.faturamento.conta.CancelarContaActionForm"
	method="post">
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
	          <logic:present name="indicadorObrigatoriedadeRA" scope="session">
	          	<html:select property="motivoCancelamentoContaID" style="width: 250px;" tabindex="1" onchange="verificarExibirRA();">
		          	<logic:present name="colecaoContaMotivoCancelamentoSelecionada">
						<html:options collection="colecaoContaMotivoCancelamentoSelecionada" labelProperty="descricaoMotivoCancelamentoConta" property="id"/>
					</logic:present>
					<logic:present name="colecaoMotivoCancelamentoConta">
						<html:options collection="colecaoMotivoCancelamentoConta" labelProperty="descricaoMotivoCancelamentoConta" property="id"/>
					</logic:present>
				</html:select>
	          </logic:present>
	          <logic:notPresent name="indicadorObrigatoriedadeRA" scope="session">
	          	 <logic:present name="colecaoContaMotivoCancelamentoSelecionada" scope="session">
		          	<html:select property="motivoCancelamentoContaID" style="width: 250px;" tabindex="1" onchange="verificarExibirRA();">
			          <logic:present name="colecaoContaMotivoCancelamentoSelecionada">
						<html:options collection="colecaoContaMotivoCancelamentoSelecionada" labelProperty="descricaoMotivoCancelamentoConta" property="id"/>
					</logic:present>
						<logic:present name="colecaoMotivoCancelamentoConta">
							<html:options collection="colecaoMotivoCancelamentoConta" labelProperty="descricaoMotivoCancelamentoConta" property="id"/>
						</logic:present>
					</html:select>	          	 
	          	 </logic:present>
	          	 <logic:notPresent name="colecaoContaMotivoCancelamentoSelecionada" scope="session">
		          	<html:select property="motivoCancelamentoContaID" style="width: 250px;" tabindex="1" onchange="verificarExibirRA();">
			          	<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present name="colecaoMotivoCancelamentoConta">
							<html:options collection="colecaoMotivoCancelamentoConta" labelProperty="descricaoMotivoCancelamentoConta" property="id"/>
						</logic:present>
					</html:select>
	          	 </logic:notPresent>
	          </logic:notPresent>
          </td>
        </tr>
        <logic:present name="indicadorObrigatoriedadeRA" scope="session">
		     <tr>
		          <td width="150" height="20"><strong>N&uacute;mero do RA:<font
								color="#FF0000">*</font></strong></td>          
		   		  <td> 
					<html:text property="numeroRA" size="9" maxlength="9" onkeypress="return isCampoNumerico(event);" />&nbsp;
					<a href="javascript:chamarPopup('exibirPesquisarRegistroAtendimentoAction.do', 'registroAtendimento', null, null, 600, 730, '', document.forms[0].numeroRA);">
					
					<img width="23" 
						height="21" 
						border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar RA" /></a> 
		
				<a href="javascript:limparPesquisaRA();"> 
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" 
						title="Apagar" /></a>
		       	 </td>        
	        </tr>
	    </logic:present>  
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



