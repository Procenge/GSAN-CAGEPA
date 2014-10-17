<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gcom.util.ConstantesSistema"%>

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false" 
	formName="FiltrarContasPreFaturadasActionForm" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">

function filtrar(){

	var form = document.forms[0];

	if (validateFiltrarContasPreFaturadasActionForm(form) 
			&& validarLocalidadeDiferentes() && validarSetoresComerciaisDiferentes()
			&& validarRotasDiferentes() && verificarSetoresComerciaisMenores()
			&& verificarLocalidadeMenores()){

		form.action =  '/gsan/filtrarContasPreFaturadaAction.do'
		form.submit();
	}
}

function duplicarRota(){

	var formulario = document.forms[0]; 
	formulario.rotaDestinoID.value = formulario.rotaOrigemID.value;
} 

function duplicarLocalidade(){

	var formulario = document.forms[0]; 
	formulario.localidadeDestinoID.value = formulario.localidadeOrigemID.value;
}  

function duplicarSetorComercial(){

	var formulario = document.forms[0]; 
	formulario.setorComercialDestinoCD.value = formulario.setorComercialOrigemCD.value;

	if(formulario.setorComercialOrigemCD.value!= '' && formulario.setorComercialDestinoCD.value != ''
		&& formulario.setorComercialDestinoCD.value > formulario.setorComercialOrigemCD.value){

	    if(formulario.rotaOrigemID != undefined){

			formulario.rotaOrigemID.value = "";
			formulario.rotaDestinoID.value = "";
   	   		formulario.rotaOrigemID.disabled = true;
   	   		formulario.rotaDestinoID.disabled = true;
	    }
	}
}  

function duplicaPeriodoReferenciaContas(){

	var formulario = document.forms[0]; 
	formulario.dataReferenciaContaFinal.value = formulario.dataReferenciaContaInicial.value;
}  
  
function duplicaPeriodoVencimentoContas(){

	var formulario = document.forms[0]; 
	formulario.dataVencimentoContaFinal.value = formulario.dataVencimentoContaInicial.value;
}  

function limpar(){
	var formulario = document.forms[0]; 

	formulario.dataReferenciaContaInicial.value = "";
	formulario.dataReferenciaContaFinal.value = "";
	formulario.dataVencimentoContaInicial.value = "";
	formulario.dataVencimentoContaFinal.value = "";
	formulario.faturamentoGrupoID[formulario.faturamentoGrupoID.selectedIndex].value = '-1';
	formulario.imovelID.value = "";
	formulario.matriculaImovel.value = "";
	formulario.localidadeOrigemID.value = "";
	formulario.localidadeDestinoID.value = "";
	formulario.nomeLocalidadeOrigem.value = "";
	formulario.nomeLocalidadeDestino.value = "";
	formulario.setorComercialOrigemCD.value = "";
	formulario.setorComercialOrigemID.value = "";
	formulario.nomeSetorComercialOrigem.value = "";
	formulario.setorComercialDestinoCD.value = "";
	formulario.setorComercialDestinoID.value = "";
	formulario.nomeSetorComercialDestino.value = "";
	formulario.rotaOrigemID.value = "";
	formulario.rotaDestinoID.value = "";
	formulario.descricaoRotaOrigem.value = "";
	formulario.descricaoRotaDestino.value = "";
	formulario.inscricaoTipo.value = "";

	formulario.imovelID.disabled = false;
	formulario.localidadeOrigemID.disabled = false;
	formulario.localidadeDestinoID.disabled = false;
	formulario.setorComercialOrigemCD.disabled = false;
	formulario.setorComercialDestinoCD.disabled = false;
	

    if(formulario.rotaOrigemID != undefined){
    	formulario.rotaOrigemID.disabled = false;
    	formulario.rotaDestinoID.disabled = false;
    }

    formulario.faturamentoGrupoID.disabled = false;	

    formulario.action =  '/gsan/exibirFiltrarContasPreFaturadasAction.do'
    formulario.submit();
}

function validarLocalidadeDiferentes(){

	var form = document.forms[0];

	if(form.localidadeOrigemID.value != '' && form.localidadeDestinoID.value == ''){

		alert("Informe a Localidade Final");
		form.localidadeDestinoID.focus();
		return false;
	}
	
	if(form.localidadeOrigemID.value == '' && form.localidadeDestinoID.value != ''){

		alert("Informe a Localidade Inicial");
		form.localidadeOrigemID.focus();
		return false;
	}
	
	return true;
	
}

function validarSetoresComerciaisDiferentes(){

	var form = document.forms[0];

	if(form.setorComercialOrigemCD.value!= '' && form.setorComercialDestinoCD.value == ''){

		alert("Informe o Setor Comercial Final");
		form.setorComercialDestinoCD.focus();
		return false;
	}
	
	if(form.setorComercialOrigemCD.value == '' && form.setorComercialDestinoCD.value != ''){

		alert("Informe o Setor Comercial Inicial");
		form.setorComercialDestinoCD.focus();
		return false;
	}

	return true;
}

function validarRotasDiferentes(){

	var form = document.forms[0];

    if(form.rotaOrigemID != undefined){

		if(form.rotaOrigemID.value!= '' && form.rotaDestinoID.value == ''){

			alert("Informe a Rota Final");
			form.rotaDestinoID.focus();
			return false;
		}

		if(form.rotaOrigemID.value == '' && form.rotaDestinoID.value != ''){

			alert("Informe a Rota Inicial");
			form.rotaOrigemID.focus();
			return false;
		}
	}
	
	return true;
}

function verificarSetoresComerciaisMenores(){

	var form = document.forms[0];

	if(form.setorComercialOrigemCD.value != '' && form.setorComercialDestinoCD.value != ''){

		if(form.setorComercialDestinoCD.value < form.setorComercialOrigemCD.value){

			alert("Setor Comercial Final menor que o Setor Comercial Inicial");	
			form.setorComercialDestinoCD.focus();
			return false;
		}else{

			return true;
		}
	}else{

		return true;
	}
}

function verificarLocalidadeMenores(){

	var form = document.forms[0];
	if(form.localidadeOrigemID.value != '' && form.localidadeDestinoID.value != ''){

		if(form.localidadeDestinoID.value < form.localidadeOrigemID.value){

			alert("Localidade Final menor que a Localidade Inicial");	
			form.localidadeDestinoID.focus();
			return false;
		}else{

			return true;
		}
	
	}else{

		return true;
	}
}

function desabilitarFaturamentoGrupoImovel(){

	var form = document.forms[0];
	
	if(form.localidadeOrigemID.value != ""){
		form.imovelID.disabled = true;
		form.imovelID.value = "";
		form.faturamentoGrupoID.disabled = true;
		form.faturamentoGrupoID.value = "";
	}else{
		form.imovelID.disabled = false;
		form.faturamentoGrupoID.disabled = false;	
	}
}


function validarGrupoFaturamento(){

	var form = document.forms[0];

	var ok = true;
	if(form.localidadeOrigemID.value != ""){

		ok = false;
	}
	if(form.localidadeDestinoID.value != ""){

		ok = false;
	}

	if(ok == true){

		var grupoCobrancaSelecionado = false;

		for (i = 0; i < form.faturamentoGrupoID.length; i++){

		   if((i != 0) && (form.faturamentoGrupoID[i].selected)){
		   		grupoCobrancaSelecionado = true;
		   }
		}

		if(grupoCobrancaSelecionado == true){

			form.imovelID.value = "";
			form.imovelID.disabled = true;
	    	form.localidadeOrigemID.disabled = true;
			form.localidadeOrigemID.value = "";
			form.localidadeDestinoID.disabled = true;
			form.setorComercialOrigemCD.disabled = true;
	        form.setorComercialDestinoCD.disabled = true;
	        
		    if(form.rotaOrigemID != undefined){

				form.rotaOrigemID.value = "";
				form.rotaDestinoID.value = "";
    	   		form.rotaOrigemID.disabled = true;
    	   		form.rotaDestinoID.disabled = true;
		    }
		}else{

			form.imovelID.disabled = false;
	    	form.localidadeOrigemID.disabled = false;
			form.localidadeDestinoID.disabled = false;
	        form.setorComercialOrigemCD.disabled = false;
	        form.setorComercialDestinoCD.disabled = false;
	        
		    if(form.rotaOrigemID != undefined){

    	   		form.rotaOrigemID.disabled = false;
	    	   	form.rotaDestinoID.disabled = false;
		    }	
		}
	}
}

function validarGrupoFaturamentoParaBloquear(){

	var form = document.forms[0];
	
	var ok = 1;

	if(form.imovelID.value != ""){

		ok = 2;
	}
	
	if(form.localidadeOrigemID.value != ""){

		ok = 2;
	}
	
	if(form.localidadeDestinoID.value != ""){

		ok = 2;
	}
	
	if(ok == 2){

		form.faturamentoGrupoID.disabled = true;
	}else{

		form.faturamentoGrupoID.disabled = false;
	}
		
}

function validarLocalidade(){

	var form = document.forms[0];

	if(form.localidadeOrigemID.value != form.localidadeDestinoID.value){

	    form.setorComercialDestinoCD.value = "";
	    form.setorComercialDestinoID.value = "";		   
 	    form.nomeSetorComercialDestino.value = "";
		form.setorComercialOrigemCD.value = "";
		form.setorComercialOrigemID.value = "";
		form.nomeSetorComercialOrigem.value = "";
		form.rotaOrigemID.value = "";
   		form.rotaDestinoID.value = "";	
   	    form.descricaoRotaOrigem.value = "";	
        form.descricaoRotaDestino.value = "";	
		alert("Para informar o Setor Comercial, a Localidade Inicial e Final precisam ser iguais");
	}
}

function validarSetorComercial(){

	var form = document.forms[0];

	if(form.setorComercialOrigemCD.value != form.setorComercialDestinoCD.value){

		form.rotaOrigemID.value = "";
   		form.rotaDestinoID.value = "";	
   	    form.descricaoRotaOrigem.value = "";	
        form.descricaoRotaDestino.value = "";	
		alert("Para informar a Rota, o Setor Comercial Inicial e Final precisam ser iguais");
	}
}


function limparDestino(tipo){


	var form = document.forms[0];

	switch(tipo){
		case 1: 
			//De localidade pra baixo
			form.localidadeDestinoID.value = "";
			form.nomeLocalidadeDestino.value = "";		
			form.setorComercialOrigemCD.value = "";
			form.setorComercialOrigemID.value = "";
			form.nomeSetorComercialOrigem.value = "";
		    form.setorComercialDestinoCD.value = "";
	        form.setorComercialOrigemCD.disabled = false;
	        form.setorComercialDestinoCD.disabled = false;
	        
	   		form.rotaOrigemID.value = "";
       		form.rotaDestinoID.value = "";	
       	    form.descricaoRotaOrigem.value = "";	
	        form.descricaoRotaDestino.value = "";		   
		    
		case 2: 
			//De setor para baixo
   	       form.setorComercialDestinoCD.value = "";
		   form.setorComercialDestinoID.value = ""; 
		   form.nomeSetorComercialDestino.value = "";		   
	   	   form.rotaOrigemID.value = "";
 	   	   form.rotaDestinoID.value = "";
 	   	   form.descricaoRotaOrigem.value = "";	
 	       form.descricaoRotaDestino.value = "";		   
	}
}


function limparOrigem(tipo){
	var form = document.forms[0];

	switch(tipo){
		case 1: 
			//De localidara pra baixo
			form.localidadeOrigemID.value = "";
			form.nomeLocalidadeOrigem.value = "";
			form.faturamentoGrupoID.disabled = false;			
			form.localidadeDestinoID.value = "";
			form.nomeLocalidadeDestino.value = "";
	        form.setorComercialOrigemCD.disabled = false;
	        form.setorComercialDestinoCD.disabledy = false;
	        
	   		form.rotaOrigemID.value = "";
       		form.rotaDestinoID.value = "";		   
   	   		form.rotaOrigemID.disabled = false;
   	   		form.rotaDestinoID.disabled = false;
   	   	    form.descricaoRotaOrigem.value = "";	
	        form.descricaoRotaDestino.value = "";	
		case 2: 

		   //De setor para baixo
		   form.setorComercialOrigemCD.value = "";
		   form.setorComercialOrigemID.value = "";
		   form.nomeSetorComercialOrigem.value = "";
		   form.setorComercialDestinoCD.value = "";
		   form.setorComercialDestinoID.value = "";		   
		   form.nomeSetorComercialDestino.value = "";
	   	   form.rotaOrigemID.value = "";
	   	   form.rotaDestinoID.value = "";
	   	   form.descricaoRotaOrigem.value = "";	
	       form.descricaoRotaDestino.value = "";			   
	}
}

function limparBorrachaDestino(tipo){
	var form = document.forms[0];

	switch(tipo){
		case 1: 
			//De localidade pra baixo
			form.localidadeDestinoID.value = "";
			form.nomeLocalidadeDestino.value = "";					
    		form.setorComercialDestinoCD.value = "";
	        form.setorComercialDestinoCD.disabled = false;
	   		form.rotaDestinoID.value = "";			   
   	   		form.rotaDestinoID.disabled = false;

		case 2: 
			//De setor para baixo		   
		   form.setorComercialDestinoID.value = ""; 
		   form.nomeSetorComercialDestino.value = "";		   
   		   form.setorComercialDestinoCD.value = "";
   		   form.rotaDestinoID.value = "";
	       form.descricaoRotaDestino.value = "";	
	}
}

function habilitarRota(){
	var form = document.forms[0];

	if(form.setorComercialDestinoCD.value == form.setorComercialOrigemCD.value){

  		form.rotaOrigemID.value = "";
 	   	form.rotaDestinoID.value = "";
 	    form.descricaoRotaOrigem.value = "";	
	    form.descricaoRotaDestino.value = "";	
	   	form.rotaOrigemID.disabled = false;
 	   	form.rotaDestinoID.disabled = false;	
	}else{

	   	form.rotaOrigemID.disabled = true;
   	   	form.rotaDestinoID.disabled = true;	

	}
}


function recuperarDadosQuatroParametros(idRegistro, descricaoRegistro, codigoRegistro, tipoConsulta) {

	var form = document.forms[0];

	if (tipoConsulta == 'setorComercialOrigem' && form.faturamentoGrupoID.value == "") {

      form.setorComercialOrigemCD.value = codigoRegistro;
      form.setorComercialOrigemID.value = idRegistro;
      form.action = 'exibirFiltrarContasPreFaturadasAction.do?objetoConsulta=2&inscricaoTipo=origem'
      
	  form.nomeSetorComercialOrigem.value = descricaoRegistro;
	  form.nomeSetorComercialOrigem.style.color = "#000000";

	  if(form.setorComercialDestinoCD.value == ""){

	      form.setorComercialDestinoCD.value = codigoRegistro;
    	  form.setorComercialDestinoID.value = idRegistro;
		  form.nomeSetorComercialDestino.value = descricaoRegistro;
		  form.nomeSetorComercialDestino.style.color = "#000000"; 
	  }
	  
      form.submit();
	  
	}

	if (tipoConsulta == 'setorComercialDestino' && form.faturamentoGrupoID.value == "") {
	
      form.setorComercialDestinoCD.value = codigoRegistro;
   	  form.setorComercialDestinoID.value = idRegistro;
      form.action = 'exibirFiltrarContasPreFaturadasAction.do?objetoConsulta=2&inscricaoTipo=destino'
	  form.nomeSetorComercialDestino.value = descricaoRegistro;
	  form.nomeSetorComercialDestino.style.color = "#000000"; 
      form.submit();

	}

}


function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

    var form = document.forms[0];

	if (tipoConsulta == 'localidadeOrigem' && form.faturamentoGrupoID.value == "") {

      form.localidadeOrigemID.value = codigoRegistro;
	  form.nomeLocalidadeOrigem.value = descricaoRegistro;
      form.action = 'exibirFiltrarContasPreFaturadasAction.do?objetoConsulta=1&inscricaoTipo=origem'
	  form.nomeLocalidadeOrigem.style.color = "#000000";

	  if(form.localidadeDestinoID.value == ""){

	      form.localidadeDestinoID.value = codigoRegistro;
		  form.nomeLocalidadeDestino.value = descricaoRegistro;
		  form.nomeLocalidadeDestino.style.color = "#000000";
	  }
	  
      form.submit();

	}

	if (tipoConsulta == 'localidade' && form.faturamentoGrupoID.value == "") {

    	form.localidadeDestinoID.value = codigoRegistro;
        form.action = 'exibirFiltrarContasPreFaturadasAction.do?objetoConsulta=1&inscricaoTipo=destino'
    	form.nomeLocalidadeDestino.value = descricaoRegistro;
 		form.nomeLocalidadeDestino.style.color = "#000000";    		

 		if(form.localidadeDestinoID.value > form.localidadeOrigemID.value){

	        form.setorComercialOrigemCD.disabled = true;
	        form.setorComercialDestinoCD.disabled = true;
	       
		    if(form.rotaOrigemID != undefined){

				form.rotaOrigemID.value = "";
				form.rotaDestinoID.value = "";
				form.descricaoRotaOrigem.value = "";	
		 	    form.descricaoRotaDestino.value = "";	
    	   		form.rotaOrigemID.disabled = true;
    	   		form.rotaDestinoID.disabled = true;
		    }
 		
 		}else{

	        form.setorComercialOrigemCD.disabled = false;
	        form.setorComercialDestinoCD.disabled = false;
	       
		    if(form.rotaOrigemID != undefined){
    	   		form.rotaOrigemID.disabled = false;
    	   		form.rotaDestinoID.disabled = false;
		    }
		} 		
		
		form.submit();         		
	}

    if (tipoConsulta == 'imovel') {
        form.imovelID.value = codigoRegistro;
        form.matriculaImovel.value = descricaoRegistro;
        form.matriculaImovel.style.color = "#000000";
        form.action='exibirFiltrarContasPreFaturadasAction.do?objetoConsulta=3';
        form.submit();
    }
	
    if (tipoConsulta == 'rotaInicial') {

    	form.rotaOrigemID.value = codigoRegistro;
    	form.descricaoRotaOrigem.value = descricaoRegistro;
		form.descricaoRotaOrigem.style.color = "#000000";
		form.rotaDestinoID.value = codigoRegistro;
		form.descricaoRotaDestino.value = descricaoRegistro;
		form.descricaoRotaDestino.style.color = "#000000";
	}

    if (tipoConsulta == 'rotaFinal') {

    	form.rotaDestinoID.value = codigoRegistro;
    	form.descricaoRotaDestino.value = descricaoRegistro;
		form.descricaoRotaDestino.style.color = "#000000";
	}

}

function validarHabilitarCampo(){

	var form = document.forms[0];
	
	var ok = 1;
	
	if(form.imovelID.value != ""){
		ok = 2;	
	}
	
	if(form.localidadeOrigemID.value != ""){

		ok = 2;
	}
	
	if(form.localidadeDestinoID.value != ""){

		ok = 2;
	}
	
	if(form.faturamentoGrupoID.value != ""){

		ok = 2;
	}
	
	if(ok == 1){
		
		form.faturamentoGrupoID.disabled = false;
		form.localidadeOrigemID.disabled = false;
		form.localidadeDestinoID.disabled = false;
		form.setorComercialOrigemCD.disabled = false;
	    form.setorComercialDestinoCD.disabled = false;
	    form.rotaOrigemID.disabled = false;
   	    form.rotaDestinoID.disabled = false;
	}
}

function pesquisarLocalidadeFinal(){

	if(validarExistenciaLocalidadeInicial()){

		chamarPopup('exibirPesquisarLocalidadeAction.do', 'destino', null, null, 275, 480, '',document.forms[0].localidadeDestinoID);
	}
	
}

function pesquisarSetorComercialFinal(){

	if(validarExistenciaSetorComercialInicial()){

		chamarPopup('exibirPesquisarSetorComercialAction.do', 'setorComercialDestino', 'idLocalidade', document.forms[0].localidadeDestinoID.value, 275, 480, 'Informe a Localidade da inscrição de destino',document.forms[0].setorComercialDestinoCD);
	}
}

function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){

	if(objetoRelacionado.disabled != true){

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
}

function chamarPopupImovel(objetoRelacionado){

	if(objetoRelacionado.disabled != true){
		abrirPopup('exibirPesquisarImovelAction.do', 400, 800);
	}
}

function validarExistenciaLocalidadeInicial(){

	var form = document.forms[0]; 
	if(form.localidadeOrigemID.value == ""){

		form.localidadeDestinoID.value = "";
		alert("Informe a Localidade Inicial");
		form.localidadeOrigemID.focus();
		return false;
	}
	
	return true;
}

function validarExistenciaSetorComercialInicial(){

	var form = document.forms[0]; 
	if(form.setorComercialOrigemCD.value == ""){

		form.setorComercialDestinoCD.value = "";
		alert("Informe o Setor Comercial Inicial");
		form.setorComercialOrigemCD.focus();
		return false;
	}
	
	return true;
}


function habilitacaoCampoRota(){

	var form = document.forms[0];
	
	var setorComercialOrigem = trim(form.setorComercialOrigemCD.value);
	var setorComercialDestino = trim(form.setorComercialDestinoCD.value);
	
	if (setorComercialOrigem.length > 0 && setorComercialDestino.length > 0){
		
		if (setorComercialOrigem != setorComercialDestino){
	
			form.rotaOrigemID.value = "";
			form.rotaDestinoID.value = "";
			form.descricaoRotaOrigem.value = "";	
	 	    form.descricaoRotaDestino.value = "";	
			form.rotaOrigemID.disabled = true;
			form.rotaDestinoID.disabled = true;
		}else{
	
			form.rotaOrigemID.disabled = false;
			form.rotaDestinoID.disabled = false;
		}
		
	}
}
  
function validarLocalidaFinalMaiorInicial(){

    var form = document.forms[0];

	if((form.localidadeDestinoID.value*1) > (form.localidadeOrigemID.value*1)){

        form.setorComercialOrigemCD.disabled = true;
        form.setorComercialDestinoCD.disabled = true;
        form.setorComercialOrigemCD.value = "";
        form.setorComercialDestinoCD.value = "";

        
	    if(form.rotaOrigemID != undefined){

			form.rotaOrigemID.value = "";
			form.rotaDestinoID.value = "";
			form.descricaoRotaOrigem.value = "";	
	 	    form.descricaoRotaDestino.value = "";	

   	   		form.rotaOrigemID.disabled = true;
   	   		form.rotaDestinoID.disabled = true;
	    }
	}else{

        form.setorComercialOrigemCD.disabled = false;
        form.setorComercialDestinoCD.disabled = false;
       
	    if(form.rotaOrigemID != undefined){

   	   		form.rotaOrigemID.disabled = false;
   	   		form.rotaDestinoID.disabled = false;
    	}
	 }		 
}  

function validarSetoresComerciais(){
    var form = document.forms[0];
    
	if(form.setorComercialDestinoCD.value > form.setorComercialOrigemCD.value){
       
	    if(form.rotaOrigemID != undefined){

			form.rotaOrigemID.value = "";
			form.rotaDestinoID.value = "";
			form.descricaoRotaOrigem.value = "";	
	 	    form.descricaoRotaDestino.value = "";	
   	   		form.rotaOrigemID.disabled = true;
   	   		form.rotaDestinoID.disabled = true;
	    }
	
	}else{
       
	    if(form.rotaOrigemID != undefined){

	   		form.rotaOrigemID.disabled = false;
    	   	form.rotaDestinoID.disabled = false;
	    }
	}
}

function mensagem(mensagem){

	if(mensagem.length > 0){

		alert(mensagem);
	}
}

function pesquisarRota(destino){
   var form = document.forms[0];
   var msg = '';

   if (destino=="rotaFinal"){

	   if(form.localidadeOrigemID.value == ""){
		   msg = 'Informe Localidade Inicial.\n';
	   }

	   if(form.localidadeDestinoID.value == ""){
		   msg = msg+ 'Informe Localidade Final.\n';
	   }

	   if(form.setorComercialOrigemCD.value == ""){
		   msg = msg +'Informe Setor Comercial Inicial.\n';
	   }

	   if(form.setorComercialDestinoCD.value == ""){
		   msg = msg+ 'Informe Setor Comercial Final.\n';
	   }

	   if(form.rotaOrigemID.value == ""){
		   msg = msg+ 'Informe Rota Inicial.\n';
	   }
   }else{

	   if(form.localidadeOrigemID.value == ""){
		   msg = 'Informe Localidade Inicial.\n';
	   }
	  
	   if(form.setorComercialOrigemCD.value == ""){
		   msg = msg +'Informe Setor Comercial Inicial.\n';
	   }
   }

   if( msg == '' ){

		var msgDois = '';
		
	   if(form.localidadeOrigemID.value != form.localidadeDestinoID.value){
		   msgDois = 'Localidades diferentes.\n';
	   }
	   if(form.setorComercialOrigemCD.value != form.setorComercialDestinoCD.value){
		   msgDois = msgDois + 'Setores Comeriais diferentes.\n';
	   }
	   if( msgDois == '' ){

			abrirPopup('exibirPesquisarRotaAction.do?idLocalidade='+form.localidadeOrigemID.value+'&codigoSetorComercial='+form.setorComercialOrigemCD.value+'&destino='+destino+'&restringirPesquisa=true&destinoCampo='+destino , 250, 495);
	   }else{
		   alert(msgDois);
	   }
   }else{

	   alert(msg);
   }
}

function limparPesquisaImovel(){

    var form = document.forms[0];
    form.action='exibirFiltrarContasPreFaturadasAction.do';
    form.imovelID.value = "";
    form.matriculaImovel.value = "";
    form.submit();
}

function validaEnterImovel(tecla, caminhoActionReload, nomeCampo,mensagem) {
	  
 	var form = document.forms[0];
 	var codigo;

 	if( document.all){

      codigo = tecla.keyCode;
    }else{

      codigo = tecla.which;
    }
         
    if(codigo == 13){

   		if(!validaEnterComMensagem(tecla,caminhoActionReload, nomeCampo,mensagem)){
   	   		
	    	return false;
	   	}else{

	    	return true;
	   	}
 	 }else{

 	 	 return true;
 	 }
}

function setarAtualizar(){

	var formulario = document.forms[0];
	
	if (formulario.indicadorAtualizar.value == "1"){

		formulario.indicadorAtualizar.value = "2"; 
	}else{

		formulario.indicadorAtualizar.value = "1";
	}
}

function limparRotaInicial(){

	var formulario = document.forms[0]; 
		
	formulario.rotaOrigemID.value = "";
	formulario.descricaoRotaOrigem.value = "";
	formulario.rotaDestinoID.value = "";
	formulario.descricaoRotaDestino.value = "";

    if(formulario.rotaOrigemID != undefined){
    	formulario.rotaOrigemID.disabled = false;
    	formulario.rotaDestinoID.disabled = false;
    }
}

function limparRotaFinal(){

	var formulario = document.forms[0]; 
		
	formulario.rotaDestinoID.value = "";
	formulario.descricaoRotaDestino.value = "";
    formulario.rotaDestinoID.disabled = false;
}

function validarSetoresComerciaisDiferentes(){

	var form = document.forms[0];

	if(form.setorComercialOrigemCD.value!= '' && form.setorComercialDestinoCD.value == ''){

		alert("Informe o Setor Comercial Final");
		form.setorComercialDestinoCD.focus();
		return false;
	}
	
	if(form.setorComercialOrigemCD.value == '' && form.setorComercialDestinoCD.value != ''){

		alert("Informe o Setor Comercial Inicial");
		form.setorComercialDestinoCD.focus();
		return false;
	}

	return true;
}

function verificarPreechimentoSetorComercial(campo) {

	var formulario = document.forms[0];

	if (campo == 'origem'){

		if (formulario.setorComercialOrigemCD.value == "") {
	
			formulario.setorComercialOrigemCD.focus();
			return false;
		} else {
			
			return true;
		}
	}else{

		if (formulario.setorComercialDestinoCD.value == "") {
			
			formulario.setorComercialDestinoCD.focus();
			return false;
		} else {
			
			return true;
		}
	}
}

function validarPreenchimentoRota(evento, campo){

	var formulario = document.forms[0];
	
	if(verificarPreechimentoSetorComercial(campo)){ 

		if (campo == 'origem'){

			validaEnterDependenciaComMensagemAceitaZERO(evento, 'exibirFiltrarContasPreFaturadasAction.do?objetoConsulta=5&inscricaoTipo=origem', 
				formulario.rotaOrigemID, formulario.setorComercialOrigemCD.value, 'Setor Comercial','Rota');
		}else{

			if (formulario.rotaOrigemID.value != "") {


				validaEnterDependenciaComMensagemAceitaZERO(evento, 'exibirFiltrarContasPreFaturadasAction.do?objetoConsulta=6&inscricaoTipo=destino', 
						formulario.rotaDestinoID, formulario.setorComercialDestinoCD.value, 'Setor Comercial','Rota');
			}else{

				alert('Informe Rota Inicial.');
			}
		} 
	}else{ 

		if (campo == 'origem'){

			alert('Informe Setor Comercial Inicial.');
		}else{

			alert('Informe Setor Comercial Final.');
		} 
		
		return false;
	}
}

function validarPreenchimentoImovel(){
	var form = document.forms[0];

	if(form.imovelID.value != ""){
		form.faturamentoGrupoID.disabled = true;
		form.faturamentoGrupoID.value = "";
		form.localidadeOrigemID.disabled = true;
		form.localidadeOrigemID.value = "";
        form.nomeLocalidadeOrigem.value = "";
		form.localidadeDestinoID.disabled = true;
		form.localidadeDestinoID.value = "";
		form.nomeLocalidadeDestino.value = "";
		form.setorComercialOrigemCD.disabled = true;
		form.setorComercialOrigemCD.value = "";
        form.nomeSetorComercialOrigem.value = "";
		form.setorComercialDestinoCD.disabled = true;
	    form.setorComercialDestinoCD.value = "";
        form.nomeSetorComercialDestino.value = "";
	    form.rotaOrigemID.disabled = true;
	    form.rotaOrigemID.value = "";
        form.descricaoRotaOrigem.value = "";	
	    form.rotaDestinoID.disabled = true;
	    form.rotaDestinoID.value = "";
        form.descricaoRotaDestino.value = "";			   
	}else{
		form.localidadeOrigemID.disabled = false;
		form.localidadeDestinoID.disabled = false;
		form.setorComercialOrigemCD.disabled = false;
	    form.setorComercialDestinoCD.disabled = false;
	    form.rotaOrigemID.disabled = false;
   	    form.rotaDestinoID.disabled = false;
	}
}

function limparDesfazer(desfazer){
	if (desfazer == 'S') {
		limpar();
	}
}

</script>
</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:mensagem('${requestScope.mensagem}');setarFoco('${requestScope.nomeCampo}');limparDesfazer('${requestScope.desfazer}');document.forms[0].indicadorAtualizar.checked=true;">

<form action="/exibirFiltrarContasPreFaturadasAction"
	name="FiltrarContasPreFaturadasActionForm" method="post"><%@ include
	file="/jsp/util/cabecalho.jsp"%> <%@ include file="/jsp/util/menu.jsp"%>
	
	<html:hidden property="indicadorAtualizar" value="1"/>

<table width="770" border="0" cellspacing="4" cellpadding="0">
	<tr>
		<td width="149" valign="top" class="leftcoltext">

		<div align="center">
		<%@ include file="/jsp/util/informacoes_usuario.jsp"%>
		<%@ include file="/jsp/util/mensagens.jsp"%>
		</div>
		</td>
		<td valign="top" class="centercoltext">
		
		<table>
			<tr>
				<td></td>
			</tr>
		</table>
		
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="11"><img border="0"
					src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
				<td class="parabg">Filtrar Contas Pré-Faturadas</td>
				<td width="11" valign="top"><img border="0"
					src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
			</tr>
		</table>

		<p>&nbsp;</p>
		
		<table bordercolor="#000000" width="100%" cellspacing="0" >
			<tr>
				<td>
				Para manter a(s) Conta(s) Pré-Faturada(s), informe os dados abaixo:
				</td>
				<td width="84">
				<input name="atualizar" type="checkbox"
					checked="checked" onclick="javascript:setarAtualizar();" value="1"> <strong>Atualizar</strong>
				</td>
   			</tr>
		</table>
		
		<p>&nbsp;</p>
		
		<table width="100%" border="0" dwcopytype="CopyTableRow">
			<tr>
				<td width="29%"><strong>Per&iacute;odo de Refer&ecirc;ncia do
				Faturamento:</strong></td>
				<td colspan="4"><strong><strong> <html:text maxlength="7" 
					name="FiltrarContasPreFaturadasActionForm"
					property="dataReferenciaContaInicial" size="7"
					onkeyup="javascript:mascaraAnoMes(this, event);duplicaPeriodoReferenciaContas();" />
				</strong> </strong>mm/aaaa<strong><strong><strong> a</strong> <html:text
					maxlength="7" name="FiltrarContasPreFaturadasActionForm" 
					property="dataReferenciaContaFinal" size="7"
					onkeyup="javascript:mascaraAnoMes(this, event);" /> </strong> </strong>mm/aaaa<strong>
				</strong></td>
			</tr>
			<tr>
				<td height="17"><strong>Per&iacute;odo de Vencimento</strong><strong>:</strong></td>
				<td colspan="4"><strong> <html:text maxlength="10" 
					name="FiltrarContasPreFaturadasActionForm"
					property="dataVencimentoContaInicial" size="10"
					onkeyup="javascript:mascaraData(this, event);duplicaPeriodoVencimentoContas();" />
				<a
					href="javascript:abrirCalendarioReplicando('FiltrarContasPreFaturadasActionForm', 'dataVencimentoContaInicial',
					'dataVencimentoContaFinal')">
				<img border="0"
					src="<bean:message key='caminho.imagens'/>calendario.gif"
					width="20" border="0" align="middle" alt="Exibir Calendário" /></a>
				<strong> a</strong> <html:text maxlength="10" 
					name="FiltrarContasPreFaturadasActionForm"
					property="dataVencimentoContaFinal" size="10"
					onkeyup="javascript:mascaraData(this, event);" /> <a
					href="javascript:abrirCalendario('FiltrarContasPreFaturadasActionForm', 'dataVencimentoContaFinal')">
				<img border="0"
					src="<bean:message key='caminho.imagens'/>calendario.gif"
					width="20" border="0" align="middle" alt="Exibir Calendário" /></a>
				</strong></td>
			</tr>
			<tr>
				<td colspan="5">
				<hr>
				</td>
			</tr>
			<tr>
				<td><strong>Matr&iacute;cula do Im&oacute;vel:</strong></td>
				<td colspan="4">
					<html:text maxlength="9" name="FiltrarContasPreFaturadasActionForm" property="imovelID" size="10"
						onkeypress="return validaEnterImovel(event, 'exibirFiltrarContasPreFaturadasAction.do?objetoConsulta=3', 'imovelID','Matrícula');"
						onkeyup="javascript:validarPreenchimentoImovel();" onblur="validarPreenchimentoImovel();" />
					<a
						href="javascript:chamarPopupImovel(document.forms[0].imovelID);">
						<img width="23" height="21" border="0"
							src="<bean:message key="caminho.imagens"/>pesquisa.gif"
							title="Pesquisar Imóvel" />
					</a> 
					
					<logic:present
						name="matInexistente" scope="request">
						<html:text name="FiltrarContasPreFaturadasActionForm" property="matriculaImovel" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #FF0000"
							size="22" maxlength="22" />
					</logic:present> 
					<logic:notPresent name="matInexistente"
						scope="request">
						<html:text name="FiltrarContasPreFaturadasActionForm" property="matriculaImovel" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000"
							size="22" maxlength="22" />
					</logic:notPresent> 
					<a href="javascript:limparPesquisaImovel();">
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar Imóvel" />
					</a>
				</td>
			</tr>
			
			<tr>
				<td colspan="5">
				<hr>
				</td>
			</tr>
			
			<tr>
				<td><strong>Grupo de Faturamento:</strong></td>
				<td colspan="4"><html:select property="faturamentoGrupoID" 
					onchange="validarGrupoFaturamento();"
					name="FiltrarContasPreFaturadasActionForm"
					style="width: 230px;" size="2">
					<logic:present name="colecaoFaturamentoGrupo">
						<html:option value="" />
						<html:options collection="colecaoFaturamentoGrupo"
							labelProperty="descricao" property="id" />
					</logic:present>
				</html:select></td>
			</tr>

			<tr>
				<td colspan="5">
				<hr>
				</td>
			</tr>

			<tr>
				<td colspan="5"><strong>Dados de Localiza&ccedil;&atilde;o
				Geogr&aacute;fica </strong></td>
			</tr>
			
			<tr>
				<td colspan="5">
				<hr>
				</td>
			</tr>
			
			<html:hidden property="inscricaoTipo"
				name="FiltrarContasPreFaturadasActionForm" />
				
			<tr>
				<td ><strong>Localidade Inicial:</strong></td>
				<td align="right" colspan="4">
					<div align="left">
						<html:text maxlength="3"
						property="localidadeOrigemID" size="3" 
						name="FiltrarContasPreFaturadasActionForm"
						onkeypress="limparDestino(1);
						validaEnterComMensagem(event, 'exibirFiltrarContasPreFaturadasAction.do?objetoConsulta=1&inscricaoTipo=origem',
					    'localidadeOrigemID', 'Localidade Inicial');"
					    onkeyup="javascript:duplicarLocalidade();desabilitarFaturamentoGrupoImovel();"
						onblur="desabilitarFaturamentoGrupoImovel();"/> 
						
					<a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'origem', null, null, 275, 480, '',document.forms[0].localidadeOrigemID);">
						<img width="23" height="21" border="0"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Localidade" />
					</a> 
					<strong> 
						<logic:present name="corLocalidadeOrigem">
					<logic:equal name="corLocalidadeOrigem" value="exception">
						<html:text property="nomeLocalidadeOrigem" size="25"
							maxlength="25" readonly="true"
							name="FiltrarContasPreFaturadasActionForm"
							style="background-color:#EFEFEF; border:0; color: #ff0000" />
					</logic:equal>

					<logic:notEqual name="corLocalidadeOrigem" value="exception">
						<html:text property="nomeLocalidadeOrigem" size="25"
							maxlength="25" readonly="true"
							name="FiltrarContasPreFaturadasActionForm"
							style="background-color:#EFEFEF; border:0; color: #000000" />
					</logic:notEqual>

				</logic:present> <logic:notPresent name="corLocalidadeOrigem">

					<logic:empty name="FiltrarContasPreFaturadasActionForm"
						property="localidadeOrigemID">
						<html:text property="nomeLocalidadeOrigem" value="" size="25"
							maxlength="25" readonly="true"
							name="FiltrarContasPreFaturadasActionForm"
							style="background-color:#EFEFEF; border:0; color: #ff0000" />
					</logic:empty>
					<logic:notEmpty
						name="FiltrarContasPreFaturadasActionForm"
						property="localidadeOrigemID">
						<html:text property="nomeLocalidadeOrigem" size="25"
							maxlength="25" readonly="true"
							name="FiltrarContasPreFaturadasActionForm"
							style="background-color:#EFEFEF; border:0; color: 	#000000" />
					</logic:notEmpty>
				</logic:notPresent> 
				
				<a href="javascript:limparOrigem(1)"> <img
				src="<bean:message key="caminho.imagens"/>limparcampo.gif"
				border="0" title="Apagar" /></a>
				</strong></div>
				</td>

			</tr>

			<tr>
				<td><strong>Setor Comercial Inicial: </strong></td>
				<td align="right" colspan="4">
				<div align="left">
					<html:text maxlength="3" 
						name="FiltrarContasPreFaturadasActionForm"
						property="setorComercialOrigemCD" size="3"
						onkeyup="javascript:duplicarSetorComercial();"
						onkeypress="limparDestino(2);
					    validaEnterDependenciaComMensagem(event, 'exibirFiltrarContasPreFaturadasAction.do?objetoConsulta=2&inscricaoTipo=origem&validarCriterio=naoAcao', document.forms[0].setorComercialOrigemCD, document.forms[0].localidadeOrigemID.value, 'Localidade Inicial', 'Setor Comercial Inicial');"
						onfocus="validarLocalidade();" /> 
						<a href="javascript:chamarPopup('exibirPesquisarSetorComercialAction.do', 
								'setorComercialOrigem', 'idLocalidade', document.forms[0].localidadeOrigemID.value, 275, 480, 
								'Informe a Localidade da inscrição de origem',document.forms[0].setorComercialOrigemCD);">
							
							<img width="23" height="21" border="0"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Setor Comercial" />
						</a>
					<logic:present name="corSetorComercialOrigem">
						<logic:equal name="corSetorComercialOrigem" value="exception">
							<html:text property="nomeSetorComercialOrigem" size="25"
								name="FiltrarContasPreFaturadasActionForm"
								maxlength="25" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>
						<logic:notEqual name="corSetorComercialOrigem" value="exception">
							<html:text property="nomeSetorComercialOrigem" size="25"
								name="FiltrarContasPreFaturadasActionForm"
								maxlength="25" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> <logic:notPresent name="corSetorComercialOrigem">
						<logic:empty name="FiltrarContasPreFaturadasActionForm"
							property="setorComercialOrigemCD">
							<html:text property="nomeSetorComercialOrigem" value="" size="25"
								maxlength="25" readonly="true"
								name="FiltrarContasPreFaturadasActionForm"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty
							name="FiltrarContasPreFaturadasActionForm"
							property="setorComercialOrigemCD">
							<html:text property="nomeSetorComercialOrigem" size="25"
								maxlength="25" readonly="true"
								name="FiltrarContasPreFaturadasActionForm"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
	
					</logic:notPresent> 
					<a href="javascript:limparOrigem(2);"> 
						<img src="<bean:message 
							key="caminho.imagens"/>limparcampo.gif"
							border="0" title="Apagar" />
					</a> 
					<html:hidden property="setorComercialOrigemID" name="FiltrarContasPreFaturadasActionForm" />
				</div>
				</td>
			</tr>
			
			<tr>
				<td><strong>Rota Inicial:<font color="#FF0000"> </font></strong></td>
				<td align="right" colspan="4">
				<div align="left">
						<html:text maxlength="3" 
						property="rotaOrigemID" size="3"
						onkeyup="duplicarRota();" 
						onkeypress="javascript:validarPreenchimentoRota(event,'origem');"
						onfocus="validarSetorComercial();"
						name="FiltrarContasPreFaturadasActionForm"/> 
						
						<a href="javascript:pesquisarRota('rotaInicial');">
							<img width="23" height="21" border="0"
								src="<bean:message 
									key="caminho.imagens"/>pesquisa.gif"
									title="Pesquisar Rota" />
						</a>
						
						<logic:present name="corRotaMensagemOrigem">

							<logic:equal name="corRotaMensagemOrigem" value="exception">
								<html:text name="FiltrarContasPreFaturadasActionForm" property="descricaoRotaOrigem" size="25" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
							</logic:equal>
			
							<logic:notEqual name="corRotaMensagemOrigem" value="exception">
								<html:text name="FiltrarContasPreFaturadasActionForm" property="descricaoRotaOrigem" size="25" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
							</logic:notEqual>
			
						</logic:present>
			
						<logic:notPresent name="corRotaMensagemOrigem">
							<html:text name="FiltrarContasPreFaturadasActionForm" property="descricaoRotaOrigem" size="25" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
						</logic:notPresent>
						
						<a	href="javascript:limparRotaInicial();">
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
							border="0" title="Apagar" /> </a>						
				</div>
				<div align="left"><strong></strong></div>
				<div align="left"></div>
				</td>
			</tr>
			
			<tr>
				<td colspan="5">
				<hr>
				</td>
			</tr>

			<tr>
				<td><strong>Localidade Final:<font color="#FF0000"> </font></strong></td>
				<td width="37%" align="left" colspan="4"><html:text maxlength="3"
					property="localidadeDestinoID" size="3" 
					name="FiltrarContasPreFaturadasActionForm"
					onkeyup="javascript:validarExistenciaLocalidadeInicial();validarLocalidaFinalMaiorInicial();"
					onkeypress="validaEnterComMensagem(event, 'exibirFiltrarContasPreFaturadasAction.do?objetoConsulta=1&inscricaoTipo=destino&validarCriterio=naoAcao', 'localidadeDestinoID', 'Localidade Final');" />
				
					<a href="javascript:pesquisarLocalidadeFinal();">
						<img width="23" height="21" border="0"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Localidade" />
					</a> 
					
					<logic:present name="corLocalidadeDestino">

					<logic:equal name="corLocalidadeDestino" value="exception">
						<html:text property="nomeLocalidadeDestino" size="25"
							maxlength="25" readonly="true"
							name="FiltrarContasPreFaturadasActionForm"
							style="background-color:#EFEFEF; border:0; color: #ff0000" />
					</logic:equal>

					<logic:notEqual name="corLocalidadeDestino" value="exception">
						<html:text property="nomeLocalidadeDestino" size="25"
							maxlength="25" readonly="true"
							name="FiltrarContasPreFaturadasActionForm"
							style="background-color:#EFEFEF; border:0; color: #000000" />
					</logic:notEqual>

				</logic:present> <logic:notPresent name="corLocalidadeDestino">

					<logic:empty name="FiltrarContasPreFaturadasActionForm"
						property="localidadeDestinoID">
						<html:text property="nomeLocalidadeDestino" value="" size="25"
							maxlength="25" readonly="true"
							name="FiltrarContasPreFaturadasActionForm"
							style="background-color:#EFEFEF; border:0; color: #ff0000" />
					</logic:empty>
					<logic:notEmpty
						name="FiltrarContasPreFaturadasActionForm"
						property="localidadeDestinoID">
						<html:text property="nomeLocalidadeDestino" size="25"
							maxlength="25" readonly="true"
							name="FiltrarContasPreFaturadasActionForm"
							style="background-color:#EFEFEF; border:0; color: 	#000000" />
					</logic:notEmpty>
				</logic:notPresent> 
				<a href="javascript:limparBorrachaDestino(1);"> <img
				src="<bean:message key="caminho.imagens"/>limparcampo.gif"
				border="0" title="Apagar" /></a> 				
				</td>
			</tr>

			<tr>
				<td><strong>Setor Comercial Final: </strong></td>
				<td align="left" colspan="4"><html:text maxlength="3"
					property="setorComercialDestinoCD" size="3" 
					name="FiltrarContasPreFaturadasActionForm"
					onkeyup="javascript:validarExistenciaSetorComercialInicial();habilitarRota();validarSetoresComerciais();"
					onkeypress="validaEnterDependenciaComMensagem(event, 'exibirFiltrarContasPreFaturadasAction.do?objetoConsulta=2&inscricaoTipo=destino&validarCriterio=naoAcao', document.forms[0].setorComercialDestinoCD, document.forms[0].localidadeDestinoID.value, 'Localidade Final', 'Setor Comercial Final');"
					onfocus="validarLocalidade();" /> 
					
					
					<a href="javascript:pesquisarSetorComercialFinal();">
						<img width="23" height="21" border="0"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Setor Comercial" />
					</a> 
					
					<logic:present name="corSetorComercialDestino">

					<logic:equal name="corSetorComercialDestino" value="exception">
						<html:text property="nomeSetorComercialDestino" size="25"
							maxlength="25" readonly="true"
							name="FiltrarContasPreFaturadasActionForm"
							style="background-color:#EFEFEF; border:0; color: #ff0000" />
					</logic:equal>

					<logic:notEqual name="corSetorComercialDestino" value="exception">
						<html:text property="nomeSetorComercialDestino" size="25"
							maxlength="25" readonly="true"
							name="FiltrarContasPreFaturadasActionForm"
							style="background-color:#EFEFEF; border:0; color: #000000" />
					</logic:notEqual>

				</logic:present> <logic:notPresent name="corSetorComercialDestino">

					<logic:empty name="FiltrarContasPreFaturadasActionForm"
						property="setorComercialDestinoCD">
						<html:text property="nomeSetorComercialDestino" size="25"
							maxlength="25" readonly="true"
							name="FiltrarContasPreFaturadasActionForm"
							style="background-color:#EFEFEF; border:0; color: #ff0000" />
					</logic:empty>
					<logic:notEmpty
						name="FiltrarContasPreFaturadasActionForm"
						property="setorComercialDestinoCD">
						<html:text property="nomeSetorComercialDestino" size="25"
							maxlength="25" readonly="true"
							name="FiltrarContasPreFaturadasActionForm"
							style="background-color:#EFEFEF; border:0; color: #000000" />
					</logic:notEmpty>

				</logic:notPresent> <html:hidden property="setorComercialDestinoID"
				name="FiltrarContasPreFaturadasActionForm" /> 
				<a href="javascript:limparBorrachaDestino(2);"> <img
				src="<bean:message key="caminho.imagens"/>limparcampo.gif"
				border="0" title="Apagar" /></a>
				</td>
			</tr>

			<tr>
				<td><strong>Rota Final:<font color="#FF0000"> </font></strong></td>
				<td align="right" colspan="4">
				<div align="left"><strong>
						<html:text maxlength="3" 
						property="rotaDestinoID" size="3"
						onkeypress="javascript:validarPreenchimentoRota(event,'destino');"
						name="FiltrarContasPreFaturadasActionForm"
						onfocus="validarSetorComercial();"/> 
						<a href="javascript:pesquisarRota('rotaFinal');">
						<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Rota" /></a>
						
						<logic:present name="corRotaMensagemDestino">

							<logic:equal name="corRotaMensagemDestino" value="exception">
								<html:text name="FiltrarContasPreFaturadasActionForm" property="descricaoRotaDestino" size="25" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
							</logic:equal>
			
							<logic:notEqual name="corRotaMensagemDestino" value="exception">
								<html:text name="FiltrarContasPreFaturadasActionForm" property="descricaoRotaDestino" size="25" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
							</logic:notEqual>
			
						</logic:present>
			
						<logic:notPresent name="corRotaMensagemDestino">
							<html:text name="FiltrarContasPreFaturadasActionForm" property="descricaoRotaDestino" size="25" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
						</logic:notPresent>
						
						<a	href="javascript:limparRotaFinal();">
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
							border="0" title="Apagar" /> </a>
													
				</strong></div>
				<div align="left"></div>
				</td>
			</tr>

			<tr>
				<td colspan="5">
				<hr>
				</td>
			</tr>

			<tr>
				<td colspan="5">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="3" align="left">
				<table border="0" align="left">
					<tr>
						<td align="left">
							
							<input name="Button32222" type="button"
							class="bottonRightCol" value="Limpar" 
							onClick="javascript:limpar()" />
							
							<input name="Button" type="button" class="bottonRightCol"
							value="Cancelar" align="left"
							onclick="window.location.href='/gsan/telaPrincipal.do'">
							
						</td>
					</tr>
				</table>
				</td>
				<td>
				<table border="0" align="right">
					<tr>
						<td align="right">
						  <input name="Button" type="button" class="bottonRightCol" 
						  name="botaoFiltrar" value="Filtrar" onclick="javascript:filtrar();"/>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>

		<p>&nbsp;</p>
		</td>
	</tr>
</table>
</form>
<script language="JavaScript">
	validarGrupoFaturamentoParaBloquear();
	validarHabilitarCampo();
	validarGrupoFaturamento();
	desabilitarFaturamentoGrupoImovel();
	validarPreenchimentoImovel();
	
</script>

<%@ include file="/jsp/util/rodape.jsp"%>
</body>
</html:html>
