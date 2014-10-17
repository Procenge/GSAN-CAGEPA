<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page import="gcom.util.ConstantesSistema" isELIgnored="false"%>
<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="LeituraConsumoActionForm"
	dynamicJavascript="false" />

<script language="JavaScript">
<!-- Begin

     var bCancel = false;

    function validateLeituraConsumoActionForm(form) {
        if (bCancel)
      return true;
        else
       return testarCampoValorZero(document.LeituraConsumoActionForm.imovelFiltro, 'Mátricula do Imóvel') 
       && testarCampoValorZero(document.LeituraConsumoActionForm.imovelCondominioFiltro, 'Mat. do Imóvel Condomínio')
       && testarCampoValorZero(document.LeituraConsumoActionForm.localidadeFiltro, 'Localidade') 
       && testarCampoValorZero(document.LeituraConsumoActionForm.setorComercialFiltro, 'Setor Comercial')
       && testarCampoValorZero(document.LeituraConsumoActionForm.quadraInicialFiltro, 'Quadra Inicial')
       && testarCampoValorZero(document.LeituraConsumoActionForm.quadraFinalFiltro, 'Quadra Final')
       && testarCampoValorZero(document.LeituraConsumoActionForm.rotaInicialFiltro, 'Rota Inicial')
       && testarCampoValorZero(document.LeituraConsumoActionForm.rotaFinalFiltro, 'Rota Final')
       && validateCaracterEspecial(form)
       && validateLong(form) && validaQuadra() && validaRota() && validateRequired(form);
   }

    function caracteresespeciais () {
     this.ae = new Array("imovelFiltro", "Mátricula do Imóvel possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.af = new Array("imovelCondominioFiltro", "Mat. do Imóvel Condomínio possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.aa = new Array("localidadeFiltro", "Localidade possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ab = new Array("setorComercialFiltro", "Setor Comercial possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ac = new Array("quadraInicialFiltro", "Quadra Inicial possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ad = new Array("quadraFinalFiltro", "Quadra Final possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ag = new Array("rotaInicialFiltro", "Rota Inicial possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ah = new Array("rotaFinalFiltro", "Rota Final possui caracteres especiais.", new Function ("varName", " return this[varName];"));
    }

    function IntegerValidations () {
     this.aj = new Array("imovelFiltro", "Mátricula do Imóvel deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.al = new Array("imovelCondominioFiltro", "Mat. do Imóvel Condomínio deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.ae = new Array("localidadeFiltro", "Localidade deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.ag = new Array("setorComercialFiltro", "Setor Comercial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.ah = new Array("quadraInicialFiltro", "Quadra Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.ai = new Array("quadraFinalFiltro", "Quadra Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.aj = new Array("rotaInicialFiltro", "Rota Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.ak = new Array("rotaFinalFiltro", "Rota Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    }
    
    function required () {
	 this.aj = new Array("idGrupoFaturamentoFiltro", "Informe Grupo de Faturamento.", new Function ("varName", " return this[varName];"));
	}

 function validaQuadra(){
 	var form = document.forms[0];
 	if(form.parmId.value = "1"){
 	if((form.quadraInicialFiltro.value * 1) > (form.quadraFinalFiltro.value * 1)){
	 		alert("A Quadra Inicial deve ser menor ou igual à Quadra Final.");
	 		return false;
	 	}else{
	 		if(form.quadraInicialFiltro.value == "" && form.quadraFinalFiltro.value != ""){
	 			alert("Informe Quadra Inicial.");
	 			return false;
	 		}else if(form.quadraInicialFiltro.value != "" && form.quadraFinalFiltro.value == ""){
	 			alert("Informe Quadra Final.");
	 			return false;
	 		}else{
	 			return true;
	 		}
	 	}
 	}
 }
 
 function validaRota(){
	 	var form = document.forms[0];
	 	if(form.parmId.value = "2"){
		if((form.rotaInicialFiltro.value * 1) > (form.rotaFinalFiltro.value * 1)){
			 		alert("A Rota Inicial deve ser menor ou igual à Rota Final.");
			 		return false;
			 	}else{
			 		if(form.rotaInicialFiltro.value == "" && form.rotaFinalFiltro.value != ""){
			 			alert("Informe Rota Inicial.");
			 			return false;
			 		}else if(form.rotaInicialFiltro.value != "" && form.rotaFinalFiltro.value == ""){
			 			alert("Informe Rota Final.");
			 			return false;
			 		}else{
			 			return true;
			 		}
			 	}
		 	}
	 	}
//End -->
</script>


<SCRIPT LANGUAGE="JavaScript">
<!--
function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

	var form = document.LeituraConsumoActionForm;
	
	if (tipoConsulta == 'localidade') {
      form.localidadeFiltro.value = codigoRegistro;
	  form.nomeLocalidade.value = descricaoRegistro;
	  form.nomeLocalidade.style.color = "#000000";
	}
	
	if (tipoConsulta == 'setorComercial') {
      form.setorComercialFiltro.value = codigoRegistro;
	  form.setorComercialNome.value = descricaoRegistro;
	  form.setorComercialNome.style.color = "#000000";
	}
	
	if (tipoConsulta == 'quadraInicial') {
      form.quadraInicialFiltro.value = codigoRegistro;
	  form.quadraInicialID.value = idRegistro;
	  form.quadraFinalFiltro.value = codigoRegistro;
	  form.quadraFinalID.value = idRegistro;
    }

	if (tipoConsulta == 'quadraFinal') {
      form.quadraFinalFiltro.value = codigoRegistro;
	  form.quadraFinalID.value = idRegistro;
	}

	if (tipoConsulta == 'rotaInicial') {
      form.rotaInicialFiltro.value = codigoRegistro;
	  form.rotaInicialID.value = idRegistro;
	  form.rotaFinalFiltro.value = codigoRegistro;
	  form.rotaFinalID.value = idRegistro;
	}

	if (tipoConsulta == 'rotaFinal') {
	  form.rotaFinalFiltro.value = codigoRegistro;
	  form.rotaFinalID.value = idRegistro;
	}

	if (tipoConsulta == 'imovel') {
      form.imovelFiltro.value = codigoRegistro;
      form.action = "exibirFiltrarExcecoesLeiturasConsumosAction.do";
      submeterFormPadrao(form);
    }
    
    if (tipoConsulta == 'imovelCondominio') {
      form.imovelCondominioFiltro.value = codigoRegistro;
      form.action = "exibirFiltrarExcecoesLeiturasConsumosAction.do";
      submeterFormPadrao(form);
    }
}

function recuperarDadosQuatroParametros(idRegistro, descricaoRegistro, codigoRegistro, tipoConsulta) {

	var form = document.LeituraConsumoActionForm;
	
	if (tipoConsulta == 'quadraOrigem') {
      form.quadraInicialFiltro.value = codigoRegistro;
	  form.quadraInicialID.value = idRegistro;
	  form.quadraInicialNome.value = descricaoRegistro;
	  form.quadraInicialNome.style.color = "#000000";
	  form.quadraFinalFiltro.value = codigoRegistro;
	  form.quadraFinalID.value = idRegistro;
	  form.quadraFinalNome.value = descricaoRegistro;
	  form.quadraFinalNome.style.color = "#000000";
    }

	if (tipoConsulta == 'quadraDestino') {
      form.quadraFinalFiltro.value = codigoRegistro;
	  form.quadraFinalID.value = idRegistro;
	  form.quadraFinalNome.value = descricaoRegistro;
	  form.quadraFinalNome.style.color = "#000000";
	}

	if (tipoConsulta == 'rotaOrigem') {
      form.rotaInicialFiltro.value = codigoRegistro;
	  form.rotaInicialID.value = idRegistro;
	  form.rotaInicialNome.value = descricaoRegistro;
	  form.rotaInicialNome.style.color = "#000000";
	  form.rotaFinalFiltro.value = codigoRegistro;
	  form.rotaFinalID.value = idRegistro;
	  form.rotaFinalNome.value = descricaoRegistro;
	  form.rotaFinalNome.style.color = "#000000";
	}

	if (tipoConsulta == 'rotaDestino') {
      form.rotaFinalFiltro.value = codigoRegistro;
	  form.rotaFinalID.value = idRegistro;
	  form.rotaFinalNome.value = descricaoRegistro;
	  form.rotaFinalNome.style.color = "#000000";
	}
}

function limparPesquisaLocalidade() {
    var form = document.forms[0];

      form.localidadeFiltro.value = "";
      form.nomeLocalidade.value = "";
      form.localidadeFiltro.focus();

  }

function limparPesquisaQuadra() {
    var form = document.forms[0];
    if(form.parmId.value = "1"){
      form.quadraInicialFiltro.value = "";
      form.quadraFinalFiltro.value = "";
      form.quadraInicialFiltro.focus();
    }
  }

function limparPesquisaRota() {
    var form = document.forms[0];
    if(form.parmId.value = "2"){
      form.rotaInicialFiltro.value = "";
      form.rotaFinalFiltro.value = "";
      form.rotaInicialFiltro.focus();
    }
}

function limparPesquisaQuadraInicial() {
    var form = document.forms[0];
    if(form.parmId.value = "1"){
      form.quadraInicialFiltro.value = "";
      form.quadraFinalFiltro.value = "";
      form.quadraInicialFiltro.focus();
    }
  }
  
function limparPesquisaQuadraFinal() {
    var form = document.forms[0];
    if(form.parmId.value = "1"){
      form.quadraFinalFiltro.value = "";
      form.quadraFinalFiltro.focus();
    }
  }  

function limparPesquisaRotaInicial() {
    var form = document.forms[0];
    if(form.parmId.value = "2"){
      form.rotaInicialFiltro.value = "";
      form.rotaFinalFiltro.value = "";
      form.rotaInicialFiltro.focus();
    }
}
  
function limparPesquisaRotaFinal() {
    var form = document.forms[0];
    if(form.parmId.value = "2"){
      form.rotaFinalFiltro.value = "";
      form.rotaFinalFiltro.focus();
    }
} 

function limparPesquisaSetorComercial() {
    var form = document.forms[0];

      form.setorComercialFiltro.value = "";
      form.setorComercialNome.value = "";
      form.setorComercialNome.focus();

  }
  
  function limparPesquisaLocalidadeSemFocus() {
    var form = document.forms[0];

      form.localidadeFiltro.value = "";
      form.nomeLocalidade.value = "";

  }

function limparPesquisaQuadraSemFocus() {
    var form = document.forms[0];
    if(form.parmId.value = "1"){
      form.quadraInicialFiltro.value = "";
      form.quadraFinalFiltro.value = "";
    }
  }

function limparPesquisaQuadraInicialSemFocus() {
    var form = document.forms[0];
    if(form.parmId.value = "1"){
      form.quadraInicialFiltro.value = "";
      form.quadraFinalFiltro.value = "";
    }
  }
  
function limparPesquisaQuadraFinalSemFocus() {
    var form = document.forms[0];
    if(form.parmId.value = "1"){
      form.quadraFinalFiltro.value = "";
    }
}


function limparPesquisaRotaSemFocus() {
    var form = document.forms[0];
    if(form.parmId.value = "2"){
      form.rotaInicialFiltro.value = "";
      form.rotaFinalFiltro.value = "";
    }
  }

function limparPesquisaRotaInicialSemFocus() {
    var form = document.forms[0];
    if(form.parmId.value = "2"){
      form.rotaInicialFiltro.value = "";
      form.rotaFinalFiltro.value = "";
    }
  }
  
function limparPesquisaRotaFinalSemFocus() {
    var form = document.forms[0];
    if(form.parmId.value = "2"){
      form.rotaFinalFiltro.value = "";
    }
  }  

function limparPesquisaSetorComercialSemFocus() {
    var form = document.forms[0];
    if(form.parmId.value = "2"){
      form.setorComercialFiltro.value = "";
      form.setorComercialNome.value = "";
  }
}
  
 function limparForm(){
 	
 	var form = document.forms[0];
 	
 	limparPesquisaSetorComercial();
 	limparPesquisaQuadraFinal();
 	limparPesquisaQuadraInicial();
 	limparPesquisaRotaFinal();
 	limparPesquisaRotaInicial();
 	limparPesquisaLocalidade();
 	limparPesquisaLocalidade();
	limparPesquisaImovelPrincipal();
	limparPesquisaImovelCondominio();
	form.idGrupoFaturamentoFiltro.value = "";
	form.idEmpresaFiltro.value = "-1";
 }

function validarForm(form){
	// Campos relacionados
	var localidade = form.localidadeFiltro;
	var setorComercial = form.setorComercialFiltro;
	var quadraInicial = form.quadraInicialFiltro;
	var quadraFinal = form.quadraFinalFiltro;
	var rotaInicial = form.rotaInicialFiltro;
	var rotaFinal = form.rotaFinalFiltro;
	

	//Origem
	if (localidade.value.length > 0 && !testarCampoValorZero(localidade, "O código da localidade")){
		localidade.focus();
	}else if (setorComercial.value.length > 0 && 
			  !testarCampoValorZero(setorComercial, "O código do setor comercial")){
		setorComercial.focus();
	}else if (quadraInicial.value.length > 0 && !testarCampoValorZero(quadraInicial, "O número da quadra inicial")){
		quadraInicial.focus();
	}else if (quadraFinal.value.length > 0 && !testarCampoValorZero(quadraFinal, "O número da quadra Final")){
		quadraFinal.focus();
	}else if (rotaInicial.value.length > 0 && !testarCampoValorZero(rotaInicial, "O número da rota inicial")){
		rotaInicial.focus();
	}else if (rotaFinal.value.length > 0 && !testarCampoValorZero(rotaFinal, "O número da rota Final")){
		rotaFinal.focus();
	}
	
	// Confirma a validação do formulário
	if (validateLeituraConsumoActionForm(form)){
		redirecionarSubmit("/gsan/filtrarExcecoesLeiturasConsumosAction.do?nomeCaminhoMapping=efetuarAnaliseExcecoesLeiturasConsumos");
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

function limparPesquisaImovelPrincipal() {
    var form = document.forms[0];

      form.imovelFiltro.value = "";
      form.imovelMatriculaFiltro.value = "";
habilitaDesabilitaImovel();

  }
  
  function limparPesquisaImovelCondominio() {
    var form = document.forms[0];

      form.imovelCondominioFiltro.value = "";
      form.imovelMatriculaCondominioFiltro.value = "";
      habilitaDesabilitaImovel();


  }
  
  function habilitaDesabilitaImovel(){
  var form = document.forms[0];
  if(form.imovelFiltro.value !=''){
   form.imovelCondominioFiltro.disabled = true;
   form.imovelCondominioFiltro.value == '';
   form.imovelMatriculaCondominioFiltro.value =='';
  }else{
  form.imovelCondominioFiltro.disabled = false;
  }
   if(form.imovelCondominioFiltro.value !=''){
    form.imovelFiltro.disabled = true;
    form.imovelFiltro.value == '';
    form.imovelMatriculaFiltro.value =='';
   }else{
   form.imovelFiltro.disabled = false;
   }
}

function verificarCampoDesabilitado(campoHabilitado,caminho,height,width){
if(!campoHabilitado){
javascript:abrirPopup(caminho,height,width);
}

}

//-->
</SCRIPT>

</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('${requestScope.nomeCampo}');habilitaDesabilitaImovel();">


<html:form name="LeituraConsumoActionForm"
	type="gcom.gui.micromedicao.LeituraConsumoActionForm"
	action="/filtrarExcecoesLeiturasConsumosWizardAction" method="post"
	onsubmit="return validateLeituraConsumoActionForm(this);">

	<jsp:include
		page="/jsp/util/wizard/navegacao_abas_wizard_filtro.jsp?numeroPagina=1" />


	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>
	<html:hidden name="sistemaParametro" property="parmId"/>
	<table width="770" border="0" cellspacing="4" cellpadding="0">
		<input type="hidden" name="numeroPagina" value="1" />
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

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Filtrar Exceções de Leituras e Consumos</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			
			<table width="100%" border="0" >
			<tr>
			<td align="right"></td>
			</tr>
			</table>

			<table width="100%" border="0">
				<tr>
					<td><strong>Apresentação:</strong></td>
					<td><html:radio property="tipoApresentacao" value="1"/>
						    Normal
						    <html:radio property="tipoApresentacao" value="2" />Resumo
					</td>
				</tr>
				<tr>
					<td><strong>Matrícula do Imóvel:</strong></td>
					<td><html:text maxlength="9" property="imovelFiltro" size="9"
						tabindex="1"
						onkeypress="javascript:validaEnterComMensagem(event, 'exibirFiltrarExcecoesLeiturasConsumosAction.do', 'imovelFiltro', 'Mátricula do Imóvel');"
						onkeyup="document.forms[0].imovelMatriculaFiltro.value='';habilitaDesabilitaImovel();" /> <a
						href="javascript:verificarCampoDesabilitado(document.forms[0].imovelFiltro.disabled,'exibirPesquisarImovelAction.do?tipo=imovel', 400, 800);"><img
						width="23" height="21"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0" /></a>
					<logic:present name="codigoImovelNaoEncontrada" scope="request">
						<html:text property="imovelMatriculaFiltro" size="50"
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000" />
					</logic:present> <logic:notPresent name="codigoImovelNaoEncontrada"
						scope="request">
						<logic:empty name="LeituraConsumoActionForm"
							property="imovelFiltro">
							<html:text property="imovelMatriculaFiltro" size="50"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="LeituraConsumoActionForm"
							property="imovelFiltro">
							<html:text property="imovelMatriculaFiltro" size="50"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
					</logic:notPresent> <a
						href="javascript:limparPesquisaImovelPrincipal();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
				<tr>
					<td><strong>Mat. do Imóvel Condomínio:</strong></td>
					<td><html:text maxlength="9" property="imovelCondominioFiltro"
						size="9" tabindex="2"
						onkeypress="javascript:validaEnterComMensagem(event, 'exibirFiltrarExcecoesLeiturasConsumosAction.do', 'imovelCondominioFiltro', 'Mat. do Imóvel Condomínio:');"
						onkeyup="document.forms[0].imovelMatriculaCondominioFiltro.value='';habilitaDesabilitaImovel();" />
					<a
						href="javascript:verificarCampoDesabilitado(document.forms[0].imovelCondominioFiltro.disabled,'exibirPesquisarImovelAction.do?tipo=imovelCondominio', 400, 800);"><img
						width="23" height="21"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0" /></a>
					<logic:present name="codigoImovelCondominioNaoEncontrada"
						scope="request">
						<html:text property="imovelMatriculaCondominioFiltro" size="50"
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000" />
					</logic:present> <logic:notPresent
						name="codigoImovelCondominioNaoEncontrada" scope="request">
						<logic:empty name="LeituraConsumoActionForm"
							property="imovelCondominioFiltro">
							<html:text property="imovelMatriculaCondominioFiltro" size="50"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="LeituraConsumoActionForm"
							property="imovelCondominioFiltro">
							<html:text property="imovelMatriculaCondominioFiltro" size="50"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
					</logic:notPresent> <a
						href="javascript:limparPesquisaImovelCondominio();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
				<tr>
					<td><strong>Grupo de Faturamento:<font color="#FF0000">*</font></strong></td>
					<td><html:select property="idGrupoFaturamentoFiltro" tabindex="3">
						<html:option value="">&nbsp;</html:option>
						<html:options collection="colecaoFaturamentoGrupo"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>
				<tr>
					<td><strong>Empresa:</strong></td>
					<td><html:select property="idEmpresaFiltro" tabindex="4">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoEmpresa"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>
				<tr>
					<td width="19%"><strong>Localidade:</strong></td>
					<td width="81%" height="24"><html:text maxlength="3"
						property="localidadeFiltro" size="3" tabindex="5"
						onkeypress="javascript:limparPesquisaQuadraSemFocus();limparPesquisaRotaSemFocus();limparPesquisaSetorComercialSemFocus(); validaEnter(event, 'exibirFiltrarExcecoesLeiturasConsumosAction.do?objetoConsulta=1&inscricaoTipo=origem', 'localidadeFiltro');"
						onkeyup="document.forms[0].nomeLocalidade.value='';" /> <a
						href="javascript:abrirPopup('exibirPesquisarLocalidadeAction.do?tipo=imovelLocalidade', 400, 800);limparPesquisaQuadraSemFocus();limparPesquisaRotaSemFocus();limparPesquisaSetorComercialSemFocus();">
					<img width="23" height="21"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0" /></a>

					<logic:present name="codigoLocalidadeNaoEncontrada" scope="request">
						<html:text property="nomeLocalidade" size="50" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000"
							value="Localidade Inexistente" />
					</logic:present> <logic:notPresent
						name="codigoLocalidadeNaoEncontrada" scope="request">
						<logic:empty name="LeituraConsumoActionForm"
							property="localidadeFiltro">
							<html:text property="nomeLocalidade" size="50"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="LeituraConsumoActionForm"
							property="localidadeFiltro">
							<html:text property="nomeLocalidade" size="50"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
					</logic:notPresent> <a
						href="javascript:limparPesquisaQuadra();limparPesquisaRota();limparPesquisaSetorComercial();limparPesquisaLocalidade();">
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
				<tr>
					<td><strong>Setor Comercial:</strong></td>
					<td height="24"><html:text maxlength="3"
						property="setorComercialFiltro" size="3" tabindex="6"
						onkeypress="javascript:limparPesquisaQuadraSemFocus();limparPesquisaRotaSemFocus();return validaEnterDependencia(event, 'exibirFiltrarExcecoesLeiturasConsumosAction.do?objetoConsulta=2&inscricaoTipo=origem', this, document.forms[0].localidadeFiltro.value, 'Localidade');"
						onkeyup="document.forms[0].setorComercialNome.value='';" /> <a
						href="javascript:abrirPopupDependencia('exibirPesquisarSetorComercialAction.do?idLocalidade='+document.forms[0].localidadeFiltro.value+'&tipo=SetorComercial',document.forms[0].localidadeFiltro.value,'Localidade', 400, 800);limparPesquisaQuadraSemFocus();limparPesquisaRotaSemFocus();">
					<img width="23" height="21"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0" /></a>
					<logic:present name="codigoSetorComercialNaoEncontrada"
						scope="request">
						<input type="text" name="setorComercialNome" size="50"
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000"
							value="Setor Comercial Inexistente" />
					</logic:present> <logic:notPresent
						name="codigoSetorComercialNaoEncontrada" scope="request">
						<logic:empty name="LeituraConsumoActionForm"
							property="setorComercialFiltro">
							<html:text property="setorComercialNome" size="50"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="LeituraConsumoActionForm"
							property="setorComercialFiltro">
							<html:text property="setorComercialNome" size="50"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
					</logic:notPresent> <a
						href="javascript:limparPesquisaQuadra();limparPesquisaRota();limparPesquisaSetorComercial();">
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
				<logic:equal name="sistemaParametro" property="parmId" scope="session" value="<%=SistemaParametro.INDICADOR_EMPRESA_ADA.toString()%>">
				<tr>
					<td><strong>Quadra Inicial:</strong></td>
					<td height="24"><html:text maxlength="5"
						property="quadraInicialFiltro" size="5" tabindex="7"
						onkeypress="javascript:return validaEnterDependencia(event, 'exibirFiltrarExcecoesLeiturasConsumosAction.do?objetoConsulta=3&inscricaoTipo=origem', this, document.forms[0].setorComercialFiltro.value, 'Setor Comercial');"
						onkeyup="document.getElementById('quadraInicial').innerHTML = '';document.forms[0].quadraFinalFiltro.value=document.forms[0].quadraInicialFiltro.value" /> 
						<font color="#ff0000" size="2px"><span id="quadraInicial"> <bean:write name="LeituraConsumoActionForm"
							property="quadraInicialMensagem" /> </span></font>
						<html:hidden property="quadraInicialID" /></td>
				</tr>
				<tr>
					<td><strong>Quadra Final:</strong></td>
					<td height="24"><html:text maxlength="5"
						property="quadraFinalFiltro" size="5" tabindex="8"
						onkeypress="javascript:return validaEnterDependencia(event, 'exibirFiltrarExcecoesLeiturasConsumosAction.do?objetoConsulta=3&inscricaoTipo=Destino', this, document.forms[0].setorComercialFiltro.value, 'setorComercialFiltro');"
						onkeyup="document.getElementById('quadraFinal').innerHTML = '';" />
						<font color="#ff0000" size="2px"><span id="quadraFinal"> <bean:write name="LeituraConsumoActionForm"
							property="quadraFinalMensagem" /> </span></font>
						<html:hidden property="quadraFinalID" />
						<!-- Campos para validação -->
						<html:hidden property="rotaInicialFiltro"/>
						<html:hidden property="rotaFinalFiltro"/>
						<html:hidden property="rotaInicialID" />
						<html:hidden property="rotaFinalID" />
						</td>
					</tr>
				</logic:equal>
				<logic:equal name="sistemaParametro" property="parmId" scope="session" value="<%=SistemaParametro.INDICADOR_EMPRESA_DESO.toString()%>">
					<tr>
						<td><strong>Rota Inicial:</strong></td>
						<td height="24"><html:text maxlength="2"
							property="rotaInicialFiltro" size="3" tabindex="7"
							onkeypress="javascript:return validaEnterDependencia(event, 'exibirFiltrarExcecoesLeiturasConsumosAction.do?objetoConsulta=3&inscricaoTipo=origem', this, document.forms[0].setorComercialFiltro.value, 'Setor Comercial');"
							onkeyup="document.getElementById('rotaInicial').innerHTML = '';document.forms[0].rotaFinalFiltro.value=document.forms[0].rotaInicialFiltro.value" /> 
							<font color="#ff0000" size="2px"><span id="rotaInicial"> <bean:write name="LeituraConsumoActionForm"
								property="rotaInicialMensagem" /> </span></font>
							<html:hidden property="rotaInicialID" /></td>
					</tr>
					<tr>
						<td><strong>Rota Final:</strong></td>
						<td height="24"><html:text maxlength="2"
							property="rotaFinalFiltro" size="3" tabindex="8"
							onkeypress="javascript:return validaEnterDependencia(event, 'exibirFiltrarExcecoesLeiturasConsumosAction.do?objetoConsulta=3&inscricaoTipo=Destino', this, document.forms[0].setorComercialFiltro.value, 'setorComercialFiltro');"
							onkeyup="document.getElementById('rotaFinal').innerHTML = '';" />
							<font color="#ff0000" size="2px"><span id="rotaFinal"> <bean:write name="LeituraConsumoActionForm"
								property="rotaFinalMensagem" /> </span></font>
						<html:hidden property="rotaFinalID" /></td>
						<!-- Campos para validação -->
						<html:hidden property="quadraInicialFiltro"/>
						<html:hidden property="quadraFinalFiltro"/>
						<html:hidden property="quadraInicialID" />
						<html:hidden property="quadraFinalID" />
				</tr>
				</logic:equal>
				<tr>
					<td><strong>Ind. de Imóvel Condomínio:</strong></td>
					<td><html:radio property="indicadorImovelCondominioFiltro"
						value="S" tabindex="9" />Sim <html:radio
						property="indicadorImovelCondominioFiltro" value="N" tabindex="10" />Não
					<input type="radio" name="indicadorImovelCondominioFiltro"
						value="T" checked tabindex="11" />Todos</td>
				</tr>

				<tr>
					<td><strong>Relatório Med. Individualizada Com Rateio:</strong></td>
					<td><html:radio property="indicadorRateio"
						value="S" tabindex="9" />Sim <html:radio
						property="indicadorRateio" value="N" tabindex="10" />Não
					<input type="radio" name="indicadorRateio"
						value="A" checked tabindex="11" />Ambos</td>
				</tr>
					
				<tr>
					<td><strong>Ind. de Débito Automático:</strong></td>
					<td><html:radio property="indicadorDebitoAutomatico"
						value="S" tabindex="9" />Sim <html:radio
						property="indicadorDebitoAutomatico" value="N" tabindex="10" />Não
					<input type="radio" name="indicadorDebitoAutomatico"
						value="T" checked tabindex="11" />Todos</td>
				</tr>
								
				<tr>
					<td colspan="2">
					<div align="right"><jsp:include
						page="/jsp/util/wizard/navegacao_botoes_wizard_filtro.jsp?numeroPagina=1" /></div>
					</td>
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
