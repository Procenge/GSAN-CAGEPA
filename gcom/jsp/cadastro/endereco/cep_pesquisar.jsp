<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ page import="gcom.cadastro.endereco.Cep" %>
<%@ page import="gcom.util.ConstantesSistema" %>

<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key='caminho.css'/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="PesquisarCepActionForm"/>

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>

<SCRIPT LANGUAGE="JavaScript">
<!--
function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	var form = document.PesquisarActionForm;
	alert('ok');
	//alert(tipoConsulta);
	if (tipoConsulta == 'municipio') {
		form.idMunicipio.value = codigoRegistro;
		form.nomeMunicipio.value = descricaoRegistro;
	}
}

function limparPesquisaMunicipio(){

	var form = document.forms[0];

	form.idMunicipio.value = "";
	form.nomeMunicipio.value = "";

	form.idMunicipio.focus();
}


function validarForm(form){

	var objMunicipio = returnObject(form, "idMunicipio");
	var voltarSituacao = false;
	
	if (objMunicipio.disabled){
		objMunicipio.disabled = false;
		voltarSituacao = true;
	}
	
	var objNomeLogradouro = returnObject(form, "nomeLogradouro");
	
	if (validatePesquisarCepActionForm(form)){
	
		if (objMunicipio.value.length > 0 &&
			!testarCampoValorZero(objMunicipio, "Município ")){
			objMunicipio.focus();
		}
		else{
			redirecionarSubmit('pesquisarCepAction.do');
		} 
	}
	else if (voltarSituacao){
		objMunicipio.disabled = true;
	}
}


//-->
</SCRIPT>

</head>


<BODY TOPMARGIN="5" LEFTMARGIN="5" onload="resizePageSemLink(600, 300); setarFoco('${requestScope.nomeCampo}');">


<html:form action="/exibirPesquisarCepAction" method="post">

<table width="550" border="0" cellpadding="0" cellspacing="5">
  <tr>
    <td width="540" valign="top" class="centercoltext"> <table height="100%">
        <tr>
          <td></td>
        </tr>
      </table>
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11"><img src="<bean:message key='caminho.imagens'/>parahead_left.gif" editor="Webstyle4" moduleid="Album Photos (Project)\toptab_page2_parahead_left.xws" border="0" /></td>
          <td class="parabg">Pesquisar CEPs</td>
          <td width="11"><img src="<bean:message key='caminho.imagens'/>parahead_right.gif" editor="Webstyle4" moduleid="Album Photos (Project)\toptab_page2_parahead_right.xws" border="0" /></td>
        </tr>
      </table>
      <p>&nbsp;</p>

      <table width="100%" border="0">
      <tr>
		<td colspan="2">Preencha os campos para selecionar CEPs:</td>
      	<td align="right"></td>
      </tr>
     </table>
    
    		
		
	  <!-- FORMULARIO --------------------------------------------------------------------------------------------------------------- -->
	  <table width="100%" border="0">
	  <tr>
			<td><strong>Munic&iacute;pio:<font color="#FF0000">*</font></strong></td>
			<td colspan="2">
				
				<logic:empty name="PesquisarCepActionForm" property="idMunicipio">
				
					<html:text maxlength="4"  property="idMunicipio"  style="width: 50;" size="4" tabindex="1"
					onkeypress="return validaEnter(event, 'exibirPesquisarCepAction.do?pesquisaMunicipio=true', 'idMunicipio');" />
			
					<a href="javascript:redirecionarSubmit('exibirPesquisarMunicipioAction.do?caminhoRetornoTelaPesquisaMunicipio=exibirPesquisarCepAction');">
					<img width="23" height="21" border="0"
					src="<bean:message key='caminho.imagens'/>pesquisa.gif" title="Pesquisar Município" /></a>
				
				</logic:empty>

				<logic:notEmpty name="PesquisarCepActionForm" property="idMunicipio">

					<logic:present name="municipioInformado" scope="session">
				
						<html:text maxlength="4" property="idMunicipio"  style="width: 50;" size="4" tabindex="1" disabled="true"/>
			
						<img width="23" height="21" border="0" src="<bean:message key='caminho.imagens'/>pesquisa.gif" title="Pesquisar Município" />

					</logic:present>

					<logic:notPresent name="municipioInformado" scope="session">
				
						<html:text maxlength="4" property="idMunicipio" size="4" tabindex="1"
						onkeypress="return validaEnter(event, 'exibirPesquisarCepAction.do', 'idMunicipio');" />
			
						<a href="javascript:abrirPopup('exibirPesquisarMunicipioAction.do');">
						<img width="23" height="21" border="0"
						src="<bean:message key='caminho.imagens'/>pesquisa.gif" title="Pesquisar Município" /></a>

					</logic:notPresent>
				
				</logic:notEmpty>
			
				<logic:present name="idMunicipioNaoEncontrado">
			
					<logic:equal name="idMunicipioNaoEncontrado" value="exception">
						
						<html:text property="nomeMunicipio" size="40" maxlength="30"
						readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" />
					
					</logic:equal>
			
					<logic:notEqual name="idMunicipioNaoEncontrado" value="exception">
			
						<html:text property="nomeMunicipio" size="40" maxlength="30"
						readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
			
					</logic:notEqual>
			
				</logic:present> 
			
				<logic:notPresent name="idMunicipioNaoEncontrado">
			
					<logic:empty name="PesquisarCepActionForm" property="idMunicipio">
						
						<html:text property="nomeMunicipio" value="" size="40"
						maxlength="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" />
					
					</logic:empty>

					<logic:notEmpty name="PesquisarCepActionForm" property="idMunicipio">
				
						<html:text property="nomeMunicipio" size="40" maxlength="30" readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000" />
			
					</logic:notEmpty>

				</logic:notPresent>
			
				<logic:empty name="PesquisarCepActionForm" property="idMunicipio">
					
					<a href="javascript:limparPesquisaMunicipio();"> <img
					src="<bean:message key='caminho.imagens'/>limparcampo.gif"
					border="0" title="Apagar" /></a>
			
				</logic:empty>

				<logic:notEmpty name="PesquisarCepActionForm" property="idMunicipio">

					<logic:present name="municipioInformado" scope="session">
				
						<img src="<bean:message key='caminho.imagens'/>limparcampo.gif"
						border="0"/>

					</logic:present>

					<logic:notPresent name="municipioInformado" scope="session">
				
						<a href="javascript:limparPesquisaMunicipio();"> <img
						src="<bean:message key='caminho.imagens'/>limparcampo.gif"
						border="0" title="Apagar" /></a>

					</logic:notPresent>
			
				</logic:notEmpty>
				
			</td>
	  </tr>
	   <tr>
         <tr>
          <td width="10%" height="5"><strong>Faixa:</strong></td>
          <td colspan="2">
            <logic:notPresent name="travarFaixa" scope="request">
        	 <html:text property="faixaInicial" style="width: 50;" maxlength="6" />
        	</logic:notPresent>
        	 <logic:present name="travarFaixa" scope="request">
        	 <html:text property="faixaInicial" style="width: 50;" maxlength="6" disabled="true"/>
        	</logic:present>
        </tr>
   		 <tr>
          <td height="24"><strong>Lado Cep:</strong></td>
          <td colspan="3">
          <html:select property="codigoLado" style="width: 80;">
			<html:option value="-1">&nbsp;</html:option>
			<html:option value="<%=ConstantesSistema.PAR.toString()%>">Par</html:option>
			<html:option value="<%=ConstantesSistema.IMPAR.toString() %>">Ímpar</html:option>
			<html:option value="<%=ConstantesSistema.AMBOS.toString()%>">Ambos</html:option>
          </html:select></td>
        </tr>
	    
	  <!-- FIM DO FORMULÁRIO -------------------------------------------------------------------------------------------------------- -->

      <tr>
      <td colspan="3">
	      <table width="100%">
    			<tr>     
    				<td width="70%">
			              <INPUT type="reset" class="bottonRightCol" value="Limpar" tabindex="3" style="width: 70px;" name="botaoLimpar" 
			              		onclick="redirecionarSubmit('exibirPesquisarCepAction.do?limpar=sim');">
			              <logic:present name="caminhoRetornoTelaInformarEndereco">
			          		<INPUT TYPE="button" class="bottonRightCol" value="Voltar" onclick="redirecionarSubmit('${caminhoRetornoTelaInformarEndereco}.do?objetoConsulta=1')"/>
			          	</logic:present>
		          	</td>
		          	<td colspan="2" align="right">
		              <INPUT type="button" class="bottonRightCol" value="Pesquisar" tabindex="4" onclick="validarForm(document.forms[0]);" style="width: 70px;" name="botaoPesquisar">
		            </td>
	              </tr>
       		</table>
		 </td>
      </tr>
      </table>

      <p>&nbsp;</p></td>
  </tr>
</table>

</html:form>

</body>

</html:html>
