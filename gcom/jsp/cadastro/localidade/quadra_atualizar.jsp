<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>
<%@ page import="gcom.util.ConstantesSistema" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="AtualizarQuadraActionForm"/>

<SCRIPT LANGUAGE="JavaScript">
<!--

function habilitacaoSistemaAbastecimento(indicadorRedeEsgotoAux){
	var form = document.forms[0];
		if (indicadorRedeEsgotoAux[0].checked){
			form.sistemaAbastecimentoID.disabled = true;
			form.sistemaAbastecimentoID.value = "-1"; 
			form.distritoOperacionalID.disabled = true;
			form.distritoOperacionalID.value = "-1"; 
			form.zonaAbastecimentoID.disabled = true;
			form.zonaAbastecimentoID.value = "-1"; 
		}else{
			form.sistemaAbastecimentoID.disabled = false;
			form.distritoOperacionalID.disabled = false;
			form.zonaAbastecimentoID.disabled = false;
		}
   }

function habilitacaoSistemaEsgotoBacia(indicadorRedeEsgotoAux){
	var form = document.forms[0];
		//sem rede
		if (indicadorRedeEsgotoAux[0].checked){
			form.sistemaEsgotoID.disabled = true;
			form.sistemaEsgotoID.value = "-1"; 
			form.subSistemaEsgotoID.disabled = true;
			form.subSistemaEsgotoID.value = "-1";
			form.baciaID.disabled = true;
			form.baciaID.value = "-1";  
		}else{
			form.sistemaEsgotoID.disabled = false;
			form.subSistemaEsgotoID.disabled = false;
			form.baciaID.disabled = false;
		}
   }
   
function limpar(tipo){

	var form = document.forms[0];

	switch (tipo){
        case 1:

   		   form.localidadeID.value = "";
		   form.localidadeNome.value = "";
		   form.setorComercialID.value = "";
		   form.setorComercialCD.value = "";
		   form.setorComercialNome.value = "";
		   
		   //Coloca o foco no objeto selecionado
		   form.localidadeID.focus();
		   break;
		case 2:

		   form.setorComercialID.value = "";
		   form.setorComercialCD.value = "";
		   form.setorComercialNome.value = "";

		   //Coloca o foco no objeto selecionado
		   form.setorComercialCD.focus();
		   break;
		case 4:

		   form.distritoOperacionalID.value = "-1";
		   //Coloca o foco no objeto selecionado
		   form.distritoOperacionalID.focus();
		   break;
		case 5:

		   form.setorCensitarioID.value = "";
		   form.setorCensitarioDescricao.value = "PESQUISA N�O DISPON�VEL";
		   form.setorCensitarioDescricao.style.color = "#ff0000";
		   //Coloca o foco no objeto selecionado
		   form.setorCensitarioID.focus();
		   break;
		case 7:

		   form.setorComercialID.value = "";
		   form.setorComercialCD.value = "";
		   form.setorComercialNome.value = "";
		   form.localidadeNome.value = "";
		   
		   break;
		case 9 :

			form.rotaID.value = "";
   			form.rotaDescricao.value = "";
		    form.rotaDescricao.style.color = "#000000";
		   //Coloca o foco no objeto selecionado
		   form.rotaID.focus();
		   break;
		case 10:
			
		   //Coloca o foco no objeto selecionado
		   form.distritoOperacionalID.focus();
		   break;
		case 11:

		   form.setorCensitarioDescricao.value = "PESQUISA N�O DISPON�VEL";
		   form.setorCensitarioDescricao.style.color = "#ff0000";
		   //Coloca o foco no objeto selecionado
		   form.setorCensitarioID.focus();
		   break;   
		case 12 :
   
		   //Coloca o foco no objeto selecionado
		   form.rotaID.focus();   
		   break;   
	   default:
          break;
	}
}


function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg){

	if (objeto == null || codigoObjeto == null){
		abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
	}
	else{
		if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
			alert(msg);
		}
		else{
			abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
		}
	}
}


function recuperarDadosQuatroParametros(idRegistro, descricaoRegistro, codigoRegistro, tipoConsulta) {
	
	var form = document.forms[0];

	if (tipoConsulta == 'setorComercialOrigem') {
      form.setorComercialCD.value = codigoRegistro;
      form.setorComercialID.value = idRegistro;
	  form.setorComercialNome.value = descricaoRegistro;
	  form.setorComercialNome.style.color = "#000000";
	}
	
	if (tipoConsulta == 'rota') {
	      form.rotaID.value = codigoRegistro;
		  form.rotaDescricao.value = descricaoRegistro;
		  form.rotaDescricao.style.color = "#000000";
	}	

	if (tipoConsulta == 'rota') {
	      form.rotaID.value = codigoRegistro;
		  form.rotaDescricao.value = descricaoRegistro;
		  form.rotaDescricao.style.color = "#000000";
	}	

}


function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

	var form = document.forms[0];

	if (tipoConsulta == 'localidade') {
      form.localidadeID.value = codigoRegistro;
	  form.localidadeNome.value = descricaoRegistro;
	  form.localidadeNome.style.color = "#000000";
	}

	if (tipoConsulta == 'setorCensitario') {
      form.setorCensitarioID.value = codigoRegistro;
	  form.setorCensitarioNome.value = descricaoRegistro;
	  form.setorCensitarioNome.style.color = "#000000";
	}

}


function validarForm(formulario){

	if (validateAtualizarQuadraActionForm(formulario)){

		var objSistemaEsgotoID = returnObject(formulario, "sistemaEsgotoID");
		var objSubSistemaEsgotoID = returnObject(formulario, "subSistemaEsgotoID");
		var objBaciaID = returnObject(formulario, "baciaID");
		var objDistritoOperacionalID = returnObject(formulario, "distritoOperacionalID");
		var objSistemaAbastecimentoID = returnObject(formulario, "sistemaAbastecimentoID");
		var objZonaAbastecimentoID = returnObject(formulario, "zonaAbastecimentoID");
		var objSetorCensitarioID = returnObject(formulario, "setorCensitarioID");
		var objRotaID = returnObject(formulario, "rotaID");
		var objPerfilQuadra = returnObject(formulario, "perfilQuadra");
	
		var objIndicadorAguaHTML = document.getElementById("indicadorRedeAguaHTML");
		var objIndicadorEsgotoHTML = document.getElementById("indicadorRedeEsgotoHTML");
		validarIndicadorRedeAguaAux();
		validarIndicadorRedeEsgotoAux();
		if(objPerfilQuadra.value == -1){
			alert("Informe Perfil da Quadra.");
			objPerfilQuadra.focus();
		}
		else if(!objSistemaEsgotoID.disabled && objSistemaEsgotoID.options[objSistemaEsgotoID.options.selectedIndex].value == "-1"){
			alert("Informe Sistema de Esgoto.");
			objSistemaEsgotoID.focus();
		}	
		else if(!objSubSistemaEsgotoID.disabled && objSubSistemaEsgotoID.lenght == "-1"){
			alert("Sistema de Esgoto selecionado n�o contem Sub-Sistema de Esgoto");
			objSistemaEsgotoID.focus();
		}	
		else if(!objSistemaEsgotoID.disabled && objSistemaEsgotoID.options[objSistemaEsgotoID.options.selectedIndex].value == "-1"){
			alert("Informe Sub-Sistema de Esgoto.");
			objSubSistemaEsgotoID.focus();
		}		
		else if (!objBaciaID.disabled && objBaciaID.length == "1"){
			alert("Sub-Sistema de Esgoto selecionado n�o cont�m Bacia.");
			objSubSistemaEsgotoID.focus();
		}
		else if (!objBaciaID.disabled && objBaciaID.options[objBaciaID.options.selectedIndex].value == "-1") {
			alert("Informe Bacia.");
			objSubSistemaEsgotoID.focus();
		} 
		else if (!objSistemaAbastecimentoID.disabled && objSistemaAbastecimentoID.value == "-1") {
			alert("Informe Sistema de Abastecimento.");
			objSistemaAbastecimentoID.focus();
		}
		else if (!objDistritoOperacionalID.disabled && objDistritoOperacionalID.value == "-1") {
			alert("Informe Distrito Operacional.");
			objDistritoOperacionalID.focus();
		}
		else if (!objDistritoOperacionalID.disabled && objZonaAbastecimentoID.value == "-1") {
			alert("Informe Zona de Abastecimento.");
			objZonaAbastecimentoID.focus();
		}
		else if(objSetorCensitarioID.value.length > 0 && !testarCampoValorZero(objSetorCensitarioID, "Setor Censit�rio")){
			objSetorCensitarioID.focus();
		}
		else if(objRotaID.value.length < 1){
			alert("Informe Rota.");
			objRotaID.focus();
		}
		else if(objDistritoOperacionalID.value.length > 0 && !testarCampoValorZero(objDistritoOperacionalID, "Distrito Operacional")){
			objDistritoOperacionalID.focus();
		}
		else if(objSetorCensitarioID.value.length > 0 && !testarCampoValorZero(objSetorCensitarioID, "Setor Censit�rio")){
			objSetorCensitarioID.focus();
		}
		else if(objSetorCensitarioID.value.length > 0 && !testarCampoValorZero(objSetorCensitarioID, "Setor Censit�rio")){
			objSetorCensitarioID.focus();
		}
		else if (validateAtualizarQuadraActionForm(formulario)){
			formulario.action = "/gsan/atualizarQuadraAction.do";
			submeterFormPadrao(formulario);
		}
	}
}

//Verifica o valor do objeto radio em tempo de execu��o
function verificarMarcacao(valor, tipoIndicador){
	if(tipoIndicador == "Agua"){
		document.getElementById("indicadorRedeAguaHTML").value = valor;
	}
	else{
		document.getElementById("indicadorRedeEsgotoHTML").value = valor;
	}
}

function atualizarDadosIndicador(formulario){
	var objIndicadorRedeAgua = returnObject(formulario, "indicadorRedeAguaAux");
	var objIndicadorRedeEsgoto = returnObject(formulario, "indicadorRedeEsgotoAux");
	var valorIndicadorAgua = objIndicadorRedeAgua.value;
	var valorIndicadorEsgoto = objIndicadorRedeEsgoto.value;
	
	for(x=0; x < formulario.elements.length; x++){
		if(formulario.elements[x].type == "radio"){
			if(formulario.elements[x].name == "indicadorRedeEsgotoAux"){
				if (formulario.elements[x].checked){
					valorIndicadorEsgoto = formulario.elements[x].value;
				}
			}
			else{
				if (formulario.elements[x].checked){
					valorIndicadorAgua = formulario.elements[x].value;
				}
			}
		}
	}

	document.getElementById("indicadorRedeAguaHTML").value = valorIndicadorAgua;
	document.getElementById("indicadorRedeEsgotoHTML").value = valorIndicadorEsgoto;
}

function validarIndicadorRedeAguaAux(){

    var form = document.forms[0];
    
    var indice;
    var array = new Array(form.indicadorRedeAguaAux.length);
    var selecionado = "";
    var formulario = document.forms[0]; 
    for(indice = 0; indice < form.elements.length; indice++){
	   if (form.elements[indice].type == 'radio' && form.elements[indice].checked == true && formulario.elements[indice].name == "indicadorRedeAguaAux") {
	      selecionado = form.elements[indice].value;
	      indice = form.elements.length;
	   }
    }    
	if(selecionado == ''){
		alert('Informe Rede de �gua.');
		indicadorRedeAguaAux.focus();
	}	
}

function validarIndicadorRedeEsgotoAux(){

    var form = document.forms[0];
    
    var indice;
    var array = new Array(form.indicadorRedeEsgotoAux.length);
    var selecionado = "";
    var formulario = document.forms[0]; 
    for(indice = 0; indice < form.elements.length; indice++){
	   if (form.elements[indice].type == 'radio' && form.elements[indice].checked == true && formulario.elements[indice].name == "indicadorRedeEsgotoAux") {
	      selecionado = form.elements[indice].value;
	      indice = form.elements.length;
	   }
    }    
	if(selecionado == ''){
		alert('Informe Rede de Esgoto.');
		indicadorRedeEsgotoAux.focus();
	}	
}

function verificarPreechimentoSetorComercial() {

	var formulario = document.forms[0];

	if (formulario.setorComercialCD.value == "") {

		formulario.setorComercialCD.focus();
		return false;
	} else {
		
		return true;
	}
}

function validarPreenchimentoRota(evento){

	var formulario = document.forms[0];
	
	if(verificarPreechimentoSetorComercial()){ 

		validaEnterDependenciaComMensagemAceitaZERO(evento, 'exibirAtualizarQuadraAction.do?objetoConsulta=8', 
				formulario.rotaID, formulario.setorComercialCD.value, 'Setor Comercial','Rota'); 
	}else{ 

		alert('Informe Setor Comercial.'); 
		return false;
	}
}


//-->
</SCRIPT>

</head>

<body leftmargin="5" topmargin="5" onload="atualizarDadosIndicador(document.forms[0]); setarFoco('${requestScope.nomeCampo}');habilitacaoSistemaAbastecimento(document.forms[0].indicadorRedeAguaAux);
	habilitacaoSistemaEsgotoBacia(document.forms[0].indicadorRedeEsgotoAux);">

<INPUT TYPE="hidden" NAME="indicadorRedeAguaHTML" ID="indicadorRedeAguaHTML">
<INPUT TYPE="hidden" NAME="indicadorRedeEsgotoHTML" ID="indicadorRedeEsgotoHTML">

<html:form action="/exibirAtualizarQuadraAction" method="post">

<html:hidden property="quadraID"/>
<html:hidden property="idRotaAnterior"/>
<html:hidden property="distritoOperacionalIDAnterior"/>

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

<table width="770" border="0" cellspacing="4" cellpadding="0">
  <tr>
    <td width="149" valign="top" class="leftcoltext">
      <div align="center">

	<%@ include file="/jsp/util/informacoes_usuario.jsp"%>

	<%@ include file="/jsp/util/mensagens.jsp"%>

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
          <td class="parabg">Atualizar Quadra</td>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
        </tr>
      </table>
      <p>&nbsp;</p>

    <table width="100%" border="0">
    <tr>
      	<td colspan="2">Para atualizar uma quadra, informe os dados 
            abaixo:</td>
    	<td align="right"></td>
	</tr>
	</table>
			
	<table width="100%" border="0">
	<tr>

        <td><strong>Localidade:</strong></td>
        <td>
	        <bean:write name="AtualizarQuadraActionForm" property="localidadeID"/> - 
	        <bean:write name="AtualizarQuadraActionForm" property="localidadeNome"/>
	        <html:hidden property="localidadeID"/>
			<html:hidden property="localidadeNome"/>
		</td>
    </tr>
    <tr>
        <td><strong>Setor Comercial:<font color="#FF0000">*</font></strong></td>
        <td>
	        <bean:write name="AtualizarQuadraActionForm" property="setorComercialCD"/> - 
	        <bean:write name="AtualizarQuadraActionForm" property="setorComercialNome"/>
			<html:hidden property="setorComercialID"/>
			<html:hidden property="setorComercialCD"/>
	   </td>
   </tr>
   <tr>
	   <td><strong>Quadra:<font color="#FF0000">*</font></strong></td>
       <td><bean:write name="AtualizarQuadraActionForm" property="quadraNM"/> </td>
   </tr>
   
   <tr>
		<td><strong>Bairro:</strong></td>
		<td>
			<html:select property="bairroID" style="width: 200px;" tabindex="4">
				<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
				<logic:present name="colecaoBairro">
					<html:options collection="colecaoBairro" labelProperty="nome" property="id"/>
				</logic:present>
			</html:select>
			
		</td>
	</tr>
   
    <tr>
       <td><strong>Localiza��o:</strong></td>
       <td>
			<html:select property="areaTipoID" style="width: 200px;" tabindex="5">
			<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
				<html:options collection="colecaoAreaTipo" labelProperty="descricao" property="id"/>
			</html:select>
	   </td>
   </tr>
   
   <tr>
       <td><strong>Perfil da Quadra:<font color="#FF0000">*</font></strong></td>
       <td>
			<html:select property="perfilQuadra" style="width: 200px;" tabindex="4">
			<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
				<html:options collection="colecaoPerfilQuadra" labelProperty="descricao" property="id"/>
			</html:select>
	   </td>
   </tr>

   
   <tr>
	  <td colspan="2" height="5"></td>
   </tr>
     <tr>
      <td><strong>Rede de �gua:<font color="#FF0000">*</font></strong></td>
	  <td>
			<html:radio property="indicadorRedeAguaAux" value="1" tabindex="5" onclick="verificarMarcacao(1, 'Agua');habilitacaoSistemaAbastecimento(document.forms[0].indicadorRedeAguaAux);"/><strong>Sem Rede de �gua
			<html:radio property="indicadorRedeAguaAux" value="2" tabindex="6" onclick="verificarMarcacao(2, 'Agua');habilitacaoSistemaAbastecimento(document.forms[0].indicadorRedeAguaAux);"/>Com Rede de �gua
			<html:radio property="indicadorRedeAguaAux" value="3" tabindex="7" onclick="verificarMarcacao(3, 'Agua');habilitacaoSistemaAbastecimento(document.forms[0].indicadorRedeAguaAux);"/>Rede de �gua Parcial</strong>
	  </td>
   </tr>
   <tr>
	  <td><strong>Rede de Esgoto:<font color="#FF0000">*</font></strong></td>
	  <td>
	  		<html:radio property="indicadorRedeEsgotoAux" value="1" tabindex="8" onclick="verificarMarcacao(1, 'Esgoto');habilitacaoSistemaEsgotoBacia(document.forms[0].indicadorRedeEsgotoAux);"/><strong>Sem Rede de Esgoto
			<html:radio property="indicadorRedeEsgotoAux" value="2" tabindex="9" onclick="verificarMarcacao(2, 'Esgoto');habilitacaoSistemaEsgotoBacia(document.forms[0].indicadorRedeEsgotoAux);"/>Com Rede de Esgoto
			<html:radio property="indicadorRedeEsgotoAux" value="3" tabindex="10" onclick="verificarMarcacao(3, 'Esgoto');habilitacaoSistemaEsgotoBacia(document.forms[0].indicadorRedeEsgotoAux);"/>Rede de Esgoto Parcial</strong>
	  </td>
   </tr>
	<tr>
	  <td colspan="2" height="5"><hr></td>
   </tr>
   <tr>
	  <td><strong>Sistema Esgoto:<font color="#FF0000">*</font></strong></td>
	  <td>
			<html:select property="sistemaEsgotoID" style="width: 200px;" tabindex="13" onchange="redirecionarSubmit('exibirAtualizarQuadraAction.do?objetoConsulta=7');">
				<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
				<logic:present name="colecaoSistemaEsgoto">
					<html:options collection="colecaoSistemaEsgoto" labelProperty="descricao" property="id"/>
				</logic:present>
			</html:select>
	  </td>
   </tr>
   <tr>
	  <td><strong>Sub-Sistema Esgoto:<font color="#FF0000">*</font></strong></td>
	  <td>
			<html:select property="subSistemaEsgotoID" style="width: 200px;" tabindex="14" onchange="redirecionarSubmit('exibirAtualizarQuadraAction.do?objetoConsulta=10');">
				<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
				<logic:present name="colecaoSubSistemaEsgoto">
					<html:options collection="colecaoSubSistemaEsgoto" labelProperty="descricao" property="id"/>
				</logic:present>
			</html:select>
	  </td>
   </tr>
   <TR>
		<TD><strong>Bacia:<font color="#FF0000">*</font></strong></TD>
		<TD>
			<html:select property="baciaID" style="width: 200px;" tabindex="14">
				<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
				<logic:present name="colecaoBacia">
					<html:options collection="colecaoBacia" labelProperty="descricao" property="id"/>
				</logic:present>
			</html:select>
		</TD>
	</TR>
	<tr>
	  <td colspan="2" height="5"><hr></td>
   </tr>
   <tr>
	  <td><strong>Sistema de Abastecimento:<font color="#FF0000">*</font></strong></td>
	  <td>
			<html:select property="sistemaAbastecimentoID" style="width: 200px;" tabindex="15" onchange="redirecionarSubmit('exibirAtualizarQuadraAction.do?objetoConsulta=9');">
				<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
				<html:options collection="colecaoSistemaAbastecimento" labelProperty="descricao" property="id"/>
			</html:select>
	  </td>
   </tr>
   <tr>
	  <td><strong>Distrito Operacional:<font color="#FF0000">*</font></strong></td>
	  <td>
			<html:select property="distritoOperacionalID" style="width: 200px;" tabindex="16" onchange="redirecionarSubmit('exibirAtualizarQuadraAction.do?objetoConsulta=5');">
				<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
				<html:options collection="colecaoDistritoOperacional" labelProperty="descricao" property="id"/>
			</html:select>
	  </td>
   </tr>
   <tr>
	  <td><strong>Zona de Abastecimento:<font color="#FF0000">*</font></strong></td>
	  <td>
			<html:select property="zonaAbastecimentoID" style="width: 200px;" tabindex="17">
				<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
				<html:options collection="colecaoZonaAbastecimento" labelProperty="descricao" property="id"/>
			</html:select>
	  </td>
   </tr>
	<tr>
        <td><strong>Setor Censit�rio:</strong></td>
        <td>
			<html:text property="setorCensitarioID" size="5" maxlength="3" tabindex="18" 
			onkeyup="limpar(11);"onkeypress="validaEnterComMensagem(event, 'exibirAtualizarQuadraAction.do?objetoConsulta=6', 'setorCensitarioID','Setor Censit�rio');"/>
				<a	href="javascript:alert('Pesquisa n�o dispon�vel.');document.forms[0].setorCensitarioID.focus();">
				<img width="23" height="21" border="0"
				src="<bean:message key="caminho.imagens"/>pesquisa.gif"
				title="Pesquisar Setor Censit�rio" /></a>

			<logic:present name="corSetorCensitario">

				<logic:equal name="corSetorCensitario" value="exception">
					<html:text property="setorCensitarioDescricao" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
				</logic:equal>

				<logic:notEqual name="corSetorCensitario" value="exception">
					<html:text property="setorCensitarioDescricao" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
				</logic:notEqual>

			</logic:present>

			<logic:notPresent name="corSetorCensitario">

				<logic:empty name="AtualizarQuadraActionForm" property="setorCensitarioID">
					<html:text property="setorCensitarioDescricao" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" value="PESQUISA N�O DISPON�VEL"/>
				</logic:empty>
				<logic:notEmpty name="AtualizarQuadraActionForm" property="setorCensitarioID">
					<html:text property="setorCensitarioDescricao" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
				</logic:notEmpty>
				
			</logic:notPresent>
				<a	href="javascript:limpar(5);">
				<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
				border="0" title="Apagar" /> </a>

		</td>
    </tr>
	<tr>
        <td><strong>ZEIS:</strong></td>
        <td>
			<html:select property="zeisID" style="width: 200px;" tabindex="19">
				<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
				<html:options collection="colecaoZeis" labelProperty="descricao" property="id"/>
			</html:select>
		</td>
    </tr>
    
	<tr>
        <td><strong>Rota:<font color="#FF0000">*</font></strong></td>
        <td>
			<html:text property="rotaID" size="5" maxlength="4" tabindex="20" 
			onkeyup="limpar(12);" onkeypress="javascript:validarPreenchimentoRota(event);"/>
			<a href="javascript:abrirPopupDependencia('exibirPesquisarRotaAction.do?idLocalidade='+document.forms[0].localidadeID.value+'&codigoSetorComercial='+document.forms[0].setorComercialCD.value+'&restringirPesquisa=true',document.forms[0].setorComercialCD.value,'Setor Comercial', 400, 800);">
				<img width="23" height="21" border="0"
				src="<bean:message key="caminho.imagens"/>pesquisa.gif"
				title="Pesquisar Rota" /></a>

			<logic:present name="corRotaMensagem">

				<logic:equal name="corRotaMensagem" value="exception">
					<html:text property="rotaDescricao" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
				</logic:equal>

				<logic:notEqual name="corRotaMensagem" value="exception">
					<html:text property="rotaDescricao" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
				</logic:notEqual>

			</logic:present>

			<logic:notPresent name="corRotaMensagem">
				<html:text property="rotaDescricao" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
			</logic:notPresent>
			<a	href="javascript:limpar(9);">
				<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
				border="0" title="Apagar" /> </a>
		</td>
    </tr>
	<tr>
        <td height="30"><strong>Indicador de uso:</strong></td>
        <td>
			<html:radio property="indicadorUso" value="1"/><strong>Ativo
			<html:radio property="indicadorUso" value="2"/>Inativo</strong>
		</td>
      </tr>
   <tr>
       <td></td>
       <td><font color="#FF0000">*</font>&nbsp;Campos obrigat&oacute;rios</td>
   </tr>
       
   
   </table>

	<table height="100%">
	      <tr>
	   		<td width="100%" colspan=2>
			<logic:present name="voltar">
				<logic:equal name="voltar" value="filtrar">
					<input name="Button" type="button" class="bottonRightCol"
					value="Voltar" align="left"
					onclick="window.location.href='<html:rewrite page="/exibirFiltrarQuadraAction.do?desfazer=N"/>'">
				</logic:equal>
				<logic:equal name="voltar" value="manter">
					<input name="Button" type="button" class="bottonRightCol"
					value="Voltar" align="left"
					onclick="window.location.href='<html:rewrite page="/exibirManterQuadraAction.do"/>'">
				</logic:equal>
			</logic:present>
			<logic:notPresent name="voltar">
				<input name="Button" type="button" class="bottonRightCol"
				value="Voltar" align="left"
				onclick="window.location.href='<html:rewrite page="/exibirManterQuadraAction.do"/>'">
			</logic:notPresent>
	   		
	   		<input name="Button" type="button" class="bottonRightCol"
							value="Desfazer" align="left"
							onclick="window.location.href='<html:rewrite page="/exibirAtualizarQuadraAction.do?desfazer=S"/>'">
			
			<input name="Button" type="button" class="bottonRightCol"
							value="Cancelar" align="left"
							onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"></td>						
	   
	       <td align="right">
	       <gcom:controleAcessoBotao name="Button" value="Atualizar"
							  onclick="javascript:validarForm(document.forms[0]);" url="atualizarQuadraAction.do"/><!-- 
	       <INPUT type="button" class="bottonRightCol" value="Atualizar" tabindex="13" style="width: 70px;" onclick="validarForm(document.forms[0]);"> --></td>
	   </tr>
    </table> 



	<p>&nbsp;</p>

	</td>
  </tr>
</table>

<%@ include file="/jsp/util/rodape.jsp"%>

</html:form>
</body>
</html:html>


