<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ page import="gcom.util.ConstantesSistema" %>
<%@ page import="gcom.atendimentopublico.registroatendimento.LocalOcorrencia" %>
<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="InserirRegistroAtendimentoActionForm" dynamicJavascript="false" />

<SCRIPT LANGUAGE="JavaScript">

<!--
window.onmousemove = verificarCamposHistoryBack;
	var bCancel = false; 

    function validateInserirRegistroAtendimentoActionForm(form) {                                                                   
        if (bCancel) 
      return true; 
        else 
       return validateCaracterEspecial(form) && validateInteger(form) && validateInteiroZeroPositivo(form) && validarCamposRequeridos(form); 
   } 
   
   function caracteresespeciais () { 
     this.ah = new Array("idImovel", "Imóvel possui caracteres especiais.", new Function ("varName", " return this[varName];"));
    // this.ai = new Array("pontoReferencia", "Imóvel possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.aj = new Array("idMunicipio", "Município possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ak = new Array("cdBairro", "Bairro possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.al = new Array("idLocalidade", "Localidade possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.am = new Array("cdSetorComercial", "Setor Comercial possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.an = new Array("nnQuadra", "Quadra possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ao = new Array("idUnidadeDestino", "Unidade Destino possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     //this.ap = new Array("parecerUnidadeDestino", "Parecer possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     //this.aq = new Array("descricaoLocalOcorrencia", "Descrição possui caracteres especiais.", new Function ("varName", " return this[varName];"));
    }

	function IntegerValidations () { 
		this.ac = new Array("idImovel", "Imóvel deve ser numérico(a).", new Function ("varName", " return this[varName];"));
		this.ad = new Array("idMunicipio", "Município deve ser numérico(a).", new Function ("varName", " return this[varName];"));
		this.ae = new Array("idLocalidade", "Localidade deve ser numérico(a).", new Function ("varName", " return this[varName];"));
		this.af = new Array("idUnidadeDestino", "Unidade Destino deve ser numérico(a).", new Function ("varName", " return this[varName];"));
    }
    
    
    function InteiroZeroPositivoValidations () { 
     this.ar = new Array("cdBairro", "Bairro deve somente conter números positivos ou zero.", new Function ("varName", " return this[varName];"));
     this.as = new Array("nnQuadra", "Quadra deve somente conter números positivos ou zero.", new Function ("varName", " return this[varName];"));
     this.at = new Array("cdSetorComercial", "Setor Comercial deve somente conter números positivos ou zero.", new Function ("varName", " return this[varName];"));
    }
    
    
    function validarCamposRequeridos(form){
    
    	var retorno = false;
    	var msgAlert = "";

	if(form.motivoAtendimentoIncompleto.value==null){
		
		if (dadosLocalOcorrenciaInformados() || form.descricaoLocalOcorrencia.value.length > 0){
	    	
    		if (form.descricaoLocalOcorrencia.value.length > 0){
    			alert("O RA - Registro de Atendimento ficará bloqueado até que seja informado o endereço da ocorrência");
    			retorno = true;
    		}
    		else{
    			
    			var imovelObrigatorio = form.imovelObrigatorio.value;
    			var idImovel = form.idImovel.value;
    			var pavimentoRuaObrigatorio = form.pavimentoRuaObrigatorio.value;
    			var pavimentoCalcadaObrigatorio = form.pavimentoCalcadaObrigatorio.value;
    			var localOcorrencia = form.idLocalOcorrencia.options[form.idLocalOcorrencia.selectedIndex];
    			
    			var botaoEndereco = document.getElementById("botaoEndereco");
    			var enderecoInformado = document.getElementById("enderecoInformado");
    			
    			if (imovelObrigatorio == "SIM" && idImovel == ''){
    				msgAlert = "Informe Imóvel \n";			
    			}
    			
    			if (form.idBairroArea.disabled && !botaoEndereco.disabled && enderecoInformado == null){
    				msgAlert = "Informe Endereço \n";
    			}
    			
    			if (!form.idMunicipio.disabled && form.idMunicipio.value.length < 1){
    				msgAlert = msgAlert + "Informe Município \n";			
    			}
    			
    			if (!form.cdBairro.disabled && form.cdBairro.value.length < 1){
    				msgAlert = msgAlert + "Informe Bairro \n";			
    			}
    			
    			if (!form.idBairroArea.disabled && form.idBairroArea.value < 0){
    				msgAlert = msgAlert + "Informe Área do Bairro \n";			
    			}
    			
    			if (!form.idLocalidade.disabled && form.idLocalidade.value.length < 1){
    				msgAlert = msgAlert + "Informe Localidade \n";			
    			}
    			
    			if (!form.nnQuadra.disabled && form.nnQuadra.value.length > 0 && form.cdSetorComercial.value.length < 1){
    				msgAlert = msgAlert + "Informe Setor Comercial \n";			
    			}
    			
    			if (!form.idDivisaoEsgoto.disabled && form.idDivisaoEsgoto.value < 0){
    				msgAlert = msgAlert + "Informe Divisão de Esgoto \n";			
    			}
    	/*		
    			if (!form.parecerUnidadeDestino.readOnly && form.parecerUnidadeDestino.value.length < 1 &&
    				form.idUnidadeDestino.value.length > 0){
    				msgAlert = msgAlert + "Informe Parecer \n";			
    			}*/
    			
    			if (!form.idPavimentoRua.disabled && form.idPavimentoRua.value < 0 && 
    				(pavimentoRuaObrigatorio == "SIM" || localOcorrencia.id == "1")){
    				msgAlert = msgAlert + "Informe Pavimento da Rua \n";			
    			}
    			
    			if (!form.idPavimentoCalcada.disabled && form.idPavimentoCalcada.value < 0 && 
    				(pavimentoCalcadaObrigatorio == "SIM" || localOcorrencia.name == "1")){
    				msgAlert = msgAlert + "Informe Pavimento da Calçada \n";			
    			}
    			
    			if (msgAlert.length < 1){
    				retorno = true;
    			}
    			else{
    				alert(msgAlert);
    			}
    		}
    	}
    	else{
    		form.idImovel.focus();
    		alert("Informe os  Dados da Identificação do Local da Ocorrência ou a Descrição do Local da Ocorrência");
    	}

		}else{

			retorno=true;
	
		}
    	
    	return retorno;
    }
    
    
    function obterIndicadorRuaLocalOcorrencia(){
    
    }
    
    
    function obterIndicadorCalcadaLocalOcorrencia(){
    
    }
    
	function limpar(situacao){
	
		var form = document.forms[0];
	
		switch (situacao){
	      case 1:
			   
			   redirecionarSubmit("inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosLocalOcorrenciaAction&limparImovel=OK");
			   
			   break;
		  case 2:
		   	   form.cdBairro.value = "";
		       form.descricaoBairro.value = "";
		       form.idBairro.value = "";
		       form.descricaoMunicipio.value = "";
		       
		       if (form.idBairroArea.options.length > 1){
		       		redirecionarSubmit("inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosLocalOcorrenciaAction&removerColecaoBairroArea=OK&campoFoco=idMunicipio" + "&sugerirUnidadeDestino=S");
		       }
		       
		       break;
		  case 3:
		       form.idMunicipio.value = "";
		       form.descricaoMunicipio.value = "";
		       form.cdBairro.value = "";
		       form.idBairro.value = "";
		       form.descricaoBairro.value = "";
 			   
 			   if (form.idBairroArea.options.length > 1){
		       		redirecionarSubmit("inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosLocalOcorrenciaAction&removerColecaoBairroArea=OK&campoFoco=idMunicipio" + "&sugerirUnidadeDestino=S");
		       }
		       
		       //Coloca o foco no objeto selecionado
		       form.idMunicipio.focus();
		       break;
		  case 4:
		   	   form.cdBairro.value = "";
		       form.idBairro.value = "";
		       form.descricaoBairro.value = "";
			   
			   if (form.idBairroArea.options.length > 1){
		       		redirecionarSubmit("inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosLocalOcorrenciaAction&removerColecaoBairroArea=OK&campoFoco=cdBairro" + "&sugerirUnidadeDestino=S");
		       }
		       
		       //Coloca o foco no objeto selecionado
		       form.cdBairro.focus();
		       break;
		       
		  case 5:
		  	   form.descricaoLocalidade.value = "";
		       form.cdSetorComercial.value = "";
		       form.idSetorComercial.value = "";
		       form.descricaoSetorComercial.value = "";
		       form.idQuadra.value = "";
		       form.nnQuadra.value = "";
		       
		       var msgQuadra = document.getElementById("msgQuadra");
	
			   if (msgQuadra != null){
					msgQuadra.innerHTML = "";
			   }
			   
			   //Coloca o foco no objeto selecionado
		       form.idLocalidade.focus();
		       
		       break;
		   
		   case 6:
		  	   form.idSetorComercial.value = "";
		       form.descricaoSetorComercial.value = "";
		       form.idQuadra.value = "";
		       form.nnQuadra.value = "";
		       
		       var msgQuadra = document.getElementById("msgQuadra");
	
			   if (msgQuadra != null){
					msgQuadra.innerHTML = "";
			   }
			   
			   //Coloca o foco no objeto selecionado
		       form.cdSetorComercial.focus();
		       
		       break;
		       
		    case 7:
		  	   form.idQuadra.value = "";
		  	   var msgQuadra = document.getElementById("msgQuadra");
	
			   if (msgQuadra != null){
					msgQuadra.innerHTML = "";
			   }
			   
			   break;
		       
		    case 8:
		       form.idLocalidade.value = "";
		  	   form.descricaoLocalidade.value = "";
		       form.cdSetorComercial.value = "";
		       form.idSetorComercial.value = "";
		       form.descricaoSetorComercial.value = "";
		       form.idQuadra.value = "";
		       form.nnQuadra.value = "";
		       
		       var msgQuadra = document.getElementById("msgQuadra");
	
			   if (msgQuadra != null){
					msgQuadra.innerHTML = "";
			   }
			   
			   //Coloca o foco no objeto selecionado
		       form.idLocalidade.focus();
		       
		       break;
		   
		   case 9:
		  	   form.cdSetorComercial.value = "";
		       form.idSetorComercial.value = "";
		       form.descricaoSetorComercial.value = "";
		       form.idQuadra.value = "";
		       form.nnQuadra.value = "";
		       
		       var msgQuadra = document.getElementById("msgQuadra");
	
			   if (msgQuadra != null){
					msgQuadra.innerHTML = "";
			   }
			   
			   //Coloca o foco no objeto selecionado
		       form.cdSetorComercial.focus();
		       
		       break;
		       
		   case 10:
		  	   form.idUnidadeDestino.value = "";
		       form.descricaoUnidadeDestino.value = "";
		       form.parecerUnidadeDestino.value = "";
		       
			   //Coloca o foco no objeto selecionado
		       form.idUnidadeDestino.focus();
		       
		       verificarCamposOnLoad();
		       
		       break;
		       
		   case 11:
		   	   form.idBairro.value = "";
		       form.descricaoBairro.value = "";
			   
			   if (form.idBairroArea.options.length > 1){
		       		redirecionarSubmit("inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosLocalOcorrenciaAction&removerColecaoBairroArea=OK&campoFoco=cdBairro" + "&sugerirUnidadeDestino=S");
		       }
		       
		       break;
		       
		   case 12:
		  	   form.descricaoUnidadeDestino.value = "";
		       form.parecerUnidadeDestino.value = "";
		       
			   break;
		   
		   case 13:
		  	   form.parecerUnidadeDestino.value = "";
		       
			   //Coloca o foco no objeto selecionado
		       form.parecerUnidadeDestino.focus();
		       
		       break;
		   default:
	          break;
		}
	}

	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

		var form = document.forms[0];

		if (tipoConsulta == 'municipio') {
      		form.idMunicipio.value = codigoRegistro;
	  		form.descricaoMunicipio.value = descricaoRegistro;
	  		form.descricaoMunicipio.style.color = "#000000";
	  		
	  		limpar(4);

	  		redirecionarSubmit("inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosLocalOcorrenciaAction&pesquisarMunicipio=OK" + "&sugerirUnidadeDestino=S");
		}
	
		if (tipoConsulta == 'localidade') {
      		form.idLocalidade.value = codigoRegistro;
	  		form.descricaoLocalidade.value = descricaoRegistro;
	  		form.descricaoLocalidade.style.color = "#000000";
	  		
	  		limpar(6);

	  		redirecionarSubmit("inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosLocalOcorrenciaAction&pesquisarLocalidade=OK" + "&sugerirUnidadeDestino=S");
		}
		
		if (tipoConsulta == 'unidadeOrganizacional') {
      		form.idUnidadeDestino.value = codigoRegistro;
      		form.descricaoUnidadeDestino.value = descricaoRegistro;
      		form.descricaoUnidadeDestino.style.color = "#000000";
      
      		redirecionarSubmit("inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosLocalOcorrenciaAction&pesquisarUnidadeDestino=OK");
    	}
    	if (tipoConsulta == 'imovel') {
      		form.idImovel.value = codigoRegistro;
      
      		redirecionarSubmit("inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosLocalOcorrenciaAction&pesquisarImovel=OK");
    	}
		if (tipoConsulta == 'setorComercial') {
      		form.cdSetorComercial.value = codigoRegistro;
	  		form.descricaoSetorComercial.value = descricaoRegistro;
	  		form.descricaoSetorComercial.style.color = "#000000";

	  		redirecionarSubmit("inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosLocalOcorrenciaAction&pesquisarSetorComercial=OK"  + "&sugerirUnidadeDestino=S");
		}

		if (tipoConsulta == 'bairro') {
      		form.cdBairro.value = codigoRegistro;
	  		form.idBairro.value = "";
	  		form.descricaoBairro.value = descricaoRegistro;
	  		form.descricaoBairro.style.color = "#000000";
	  		
	  		redirecionarSubmit("inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosLocalOcorrenciaAction&pesquisarBairro=OK&descricaoBairro=" + descricaoRegistro + "&sugerirUnidadeDestino=S");
		}    	
	}
	
	
	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg){

		if (habilitarDesabilitarDadosLocalOcorrencia(document.forms[0].descricaoLocalOcorrencia)){
	
			if (objeto == null || codigoObjeto == null){
				abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
			}
			else{
				if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
					alert(msg);
				}	
				else{
					abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisaBairro=" + "inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosLocalOcorrenciaAction", altura, largura);
				}
			}	
		}
	}
	
	
	function verificarCompatibilidadeDivisaoEsgoto(){
		
		var form = document.forms[0];
		
		if (form.idDivisaoEsgoto.value > 0){
			redirecionarSubmit('inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosLocalOcorrenciaAction&verificarCompatibilidade=OK');
		}
		else{
			habilitarDesabilitarDescricaoLocalOcorrencia();
		}
	}
	
	//Remover Endereço
	function remover(){
		redirecionarSubmit('inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosLocalOcorrenciaAction&removerEndereco=OK');
	}
	
	function dadosLocalOcorrenciaInformados(){
		
		var retorno = false;
		var form = document.forms[0];
		
		for(indice = 0; indice < form.elements.length; indice++){
			
			if ((form.elements[indice].type == "text" ||  
				 form.elements[indice].type == "textarea") 
				 && form.elements[indice].name != "descricaoLocalOcorrencia"
				 && form.elements[indice].value.length > 0) {
				
				retorno = true;
				break;
			}
			else if (form.elements[indice].type == "select-one" && form.elements[indice].value > 0 && 
					form.elements[indice].name != "ultimoacesso"){
				retorno = true;
				break;
			}
		}
		
		var enderecoInformado = document.getElementById("enderecoInformado");
		
		if (!retorno){
			if (enderecoInformado != null){
				retorno = true;
			}
		}
		
		return retorno;	
	}
	
	function habilitarDesabilitarDescricaoLocalOcorrencia(){
	
		var form = document.forms[0];
		
		if (dadosLocalOcorrenciaInformados()){
			form.descricaoLocalOcorrencia.disabled = true;
		}
		else{
			form.descricaoLocalOcorrencia.disabled = false;
		}
	}
	
	function habilitarDesabilitarDadosLocalOcorrencia(campoReferencia){
	
		var form = document.forms[0];
		
		for(indice = 0; indice < form.elements.length; indice++){
			
			if ((form.elements[indice].type == "text" ||  
				 form.elements[indice].type == "textarea") 
				 && form.elements[indice].name != "descricaoLocalOcorrencia"
				 && form.elements[indice].style.border == ""
				 && campoReferencia.value.length > 0) {
				
				form.elements[indice].disabled = true;
			}
			else if ((form.elements[indice].type == "text" ||  
				 form.elements[indice].type == "textarea") 
				 && form.elements[indice].name != "descricaoLocalOcorrencia"
				 && form.elements[indice].style.border == ""
				 && campoReferencia.value.length < 1) {
				
				form.elements[indice].disabled = false; 
			}
			else if (form.elements[indice].type == "select-one" && campoReferencia.value.length > 0 && form.elements[indice].name != "ultimoacesso"){
				
				form.elements[indice].disabled = true;
			}
			else if (form.elements[indice].type == "select-one" && campoReferencia.value.length < 1){
				
				form.elements[indice].disabled = false;
			}
		}
		
		//=================================================================
		if(form.descricaoLocalOcorrencia.value == ''){ 
			 if(form.solicitacaoTipoRelativoFaltaAgua.value == 'SIM'){
			     form.idMunicipio.disabled = false;
			     form.cdBairro.disabled = false;
			     form.idBairroArea.disabled = false;
			 }else{
				 form.idMunicipio.disabled = true;
			     form.cdBairro.disabled = true;
			     form.idBairroArea.disabled = true;
			 }
			 
			 if(form.solicitacaoTipoRelativoAreaEsgoto.value == 'SIM'){
		       form.idDivisaoEsgoto.disabled = false;
		     }else{
			   form.idDivisaoEsgoto.disabled = true;
		    }
		 }
	 	
	 	if(form.idImovel.value != ''){
	  		form.idLocalidade.disabled = true;
	  		form.cdSetorComercial.disabled = true;
	  		form.nnQuadra.disabled = true;
	  		//form.idDivisaoEsgoto.disabled = true;
	  		form.idMunicipio.disabled = true;
	        form.cdBairro.disabled = true;
	  
	  		if(form.idPavimentoCalcada.value != ''){
	    		if(form.imovelObrigatorio.value =='SIM'){
	     			form.idPavimentoCalcada.disabled = true;
	    		}else{
	    			form.idPavimentoCalcada.disabled = false;
	    		}
	   		}else{
	    		form.idPavimentoCalcada.disabled = false;
	   		}
	   		
	   		if(form.idPavimentoRua.value != ''){
	    		if(form.imovelObrigatorio.value =='SIM'){
	     			form.idPavimentoRua.disabled = true;
	    		}else{
	    			form.idPavimentoRua.disabled = false;
	    		}
	   		}else{
	   			form.idPavimentoRua.disabled = false;
	   		}
	 	}else{
	  		if(form.descricaoLocalOcorrencia.value == ''){
	   		   form.idLocalidade.disabled = false;
			   form.cdSetorComercial.disabled = false;
			   form.nnQuadra.disabled = false;
			   form.idPavimentoCalcada.disabled = false;
			   form.idPavimentoRua.disabled = false;
	  		}
	 	}
		//=============================================================
		
		//Lupas
		if (campoReferencia.value.length > 0){
			return false;
		}
		else{
			return true;
		}
	}
	
	
	function carregarIndicadorLocalOcorrencia(campo){
		var localOcorrencia = campo.options[campo.selectedIndex];
		var form = document.forms[0];
		form.indRuaLocalOcorrencia.value = localOcorrencia.id;
		form.indCalcadaLocalOcorrencia.value = localOcorrencia.nome;
	}
	
	
	//[SB0005] - Habilita/Desabilita Dados da Identificação do Local de Ocorrência e dados Solicitante
	
	function verificarCamposOnLoad(){
		var form = document.forms[0];
	 
	 habilitarDesabilitarDescricaoLocalOcorrencia();
	 habilitarDesabilitarDadosLocalOcorrencia(form.descricaoLocalOcorrencia);
	 
		 if(form.descricaoLocalOcorrencia.value == ''){ 
			 if(form.solicitacaoTipoRelativoFaltaAgua.value == 'SIM'){
			     form.idMunicipio.disabled = false;
			     form.cdBairro.disabled = false;
			     form.idBairroArea.disabled = false;
			 }else{
				 form.idMunicipio.disabled = true;
			     form.cdBairro.disabled = true;
			     form.idBairroArea.disabled = true;
			 }
			 
			 if(form.solicitacaoTipoRelativoAreaEsgoto.value == 'SIM'){
		       	form.idDivisaoEsgoto.disabled = false;
		     }else{
		     	form.idDivisaoEsgoto.disabled = true;
		    }
		 }
	 	
	 	if(form.idImovel.value != ''){
	  		form.idLocalidade.disabled = true;
	  		form.cdSetorComercial.disabled = true;
	  		form.nnQuadra.disabled = true;
	  		//form.idDivisaoEsgoto.disabled = true;
	  		form.idMunicipio.disabled = true;
	        form.cdBairro.disabled = true;
	  
	  		if(form.idPavimentoCalcada.value != ''){
	    		if(form.imovelObrigatorio.value =='SIM'){
	     			form.idPavimentoCalcada.disabled = true;
	    		}else{
	    			form.idPavimentoCalcada.disabled = false;
	    		}
	   		}else{
	    		form.idPavimentoCalcada.disabled = false;
	   		}
	   		
	   		if(form.idPavimentoRua.value != ''){
	    		if(form.imovelObrigatorio.value =='SIM'){
	     			form.idPavimentoRua.disabled = true;
	    		}else{
	    			form.idPavimentoRua.disabled = false;
	    		}
	   		}else{
	   			form.idPavimentoRua.disabled = false;
	   		}
	 	}else{
	  		if(form.descricaoLocalOcorrencia.value == ''){
	   		   form.idLocalidade.disabled = false;
			   form.cdSetorComercial.disabled = false;
			   form.nnQuadra.disabled = false;
			   form.idPavimentoCalcada.disabled = false;
			   form.idPavimentoRua.disabled = false;
	  		}
	 	}
	}
	
	
	function consultarRAsPendentes(){
		
		var form = document.forms[0];
		var idImovel = form.idImovel.value;
		
		if (!isNaN(idImovel) && idImovel.length > 0 && idImovel.indexOf(',') == -1 &&
			idImovel.indexOf('.') == -1 && ((idImovel * 1) > 0)){
			
			abrirPopup("/gsan/exibirConsultarRegistroAtendimentoPendentesImovelAction.do?idImovel=" + 
			idImovel + "&situacao=1", 400, 690);
		}
	}
	
	function consultarDebitos() {
		
		var form = document.forms[0];
		var idImovel = form.idImovel.value;
		
		if (!isNaN(idImovel) && idImovel.length > 0 && idImovel.indexOf(',') == -1 &&
			idImovel.indexOf('.') == -1 && ((idImovel * 1) > 0)){
			
			abrirPopup('consultarDebitoAction.do?ehPopup=true&codigoImovel='+idImovel, 550, 735);
		}		
	}

	function selecionarContaRevisao(abrirPopUpEnviarContaRevisao, especificacao) {
		
		var form = document.forms[0];
		var idImovel = form.idImovel.value;
		
		if (!isNaN(idImovel) && idImovel.length > 0 && idImovel.indexOf(',') == -1 &&
			idImovel.indexOf('.') == -1 && ((idImovel * 1) > 0)){
			
			abrirPopup('exibirSelecionarContaRevisaoAction.do?ehPopup=true&codigoImovel='+idImovel + '&abrirPopUpEnviarContaRevisao=' + abrirPopUpEnviarContaRevisao + '&especificacao=' + especificacao, 450, 550);
		}		
	}

	function verificarCamposHistoryBack(){
	 var form = document.forms[0];
	 
	 if(form.descricaoLocalOcorrencia.value == ''){ 
		 if(form.solicitacaoTipoRelativoFaltaAgua.value == 'SIM'){
		     form.idBairroArea.disabled = false;
		 }else{
			 form.idBairroArea.disabled = true;
		 }
		 
		 if(form.solicitacaoTipoRelativoAreaEsgoto.value == 'SIM'){
	       form.idDivisaoEsgoto.disabled = false;
	     }else{
		   form.idDivisaoEsgoto.disabled = true;
	    }
	 }
	 
	 
	 if(form.idImovel.value != ''){
	  //form.idDivisaoEsgoto.disabled = true;
	  //form.idBairroArea.disabled = true;
	  
	  if(form.idPavimentoCalcada.value != ''){
	    if(form.imovelObrigatorio.value =='SIM'){
	     form.idPavimentoCalcada.disabled = true;
	    }else{
	    form.idPavimentoCalcada.disabled = false;
	    }
	   }else{
	    form.idPavimentoCalcada.disabled = false;
	   }
	   if(form.idPavimentoRua.value != ''){
	    if(form.imovelObrigatorio.value =='SIM'){
	     form.idPavimentoRua.disabled = true;
	    }else{
	    form.idPavimentoRua.disabled = false;
	    }
	   }else{
	   form.idPavimentoRua.disabled = false;
	   }
	 }else{
	  if(form.descricaoLocalOcorrencia.value == ''){
	   form.idPavimentoCalcada.disabled = false;
	   form.idPavimentoRua.disabled = false;
	  }
	 }
	    
	 
	}

	function enviarContaRevisaoConfirm(msg, abrirPopUpEnviarContaRevisao, especificacao) {

		if (abrirPopUpEnviarContaRevisao != null
				&& abrirPopUpEnviarContaRevisao != undefined
				&& abrirPopUpEnviarContaRevisao != 'N') {

			var form = document.forms[0];
			var idImovel = form.idImovel.value;
			
			if (!isNaN(idImovel) && idImovel.length > 0 && idImovel.indexOf(',') == -1 &&
				idImovel.indexOf('.') == -1 && ((idImovel * 1) > 0)){

				if (msg != null
					&& msg != undefined
					&& msg != '') {
					
					var op = confirm(msg);
					
					if (op == true) {
						selecionarContaRevisao(abrirPopUpEnviarContaRevisao, especificacao);
					} else {
						// Remover o que já existe
			      		redirecionarSubmit("inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosLocalOcorrenciaAction&limparContaRevisao=OK");
					}
						
				} else {
					selecionarContaRevisao(abrirPopUpEnviarContaRevisao, especificacao);
				}
				
			}
			
		}
	}

	function redirecionarSubmitMunicipio() {

		var form = document.forms[0];
		var pesquisaComMunicipio = false;

		pesquisaComMunicipio = validarCamposNumericos(form);

		if (pesquisaComMunicipio) {
			pesquisaComMunicipio = false;
			if (!isCampoBrancoOuNull(form.idMunicipio)) {
				pesquisaComMunicipio = true;
			}
		}

	    form.descricaoMunicipio.value = "";
	    form.cdBairro.value = "";
	    form.idBairro.value = "";
	    form.descricaoBairro.value = "";

	    var removerAreaBairro = '';
	    
		if (form.idBairroArea.options.length > 1){
	    	removerAreaBairro = '&removerColecaoBairroArea=OK&campoFoco=idMunicipio';
	    }

	    //Coloca o foco no objeto selecionado
	    form.idMunicipio.focus();

		var pesquisarMunicipio = '';

		if (pesquisaComMunicipio) {
			pesquisarMunicipio = '&pesquisarMunicipio=OK';
		} else {
			form.idMunicipio.value = '';
		}
		
		redirecionarSubmit("inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosLocalOcorrenciaAction" + removerAreaBairro + pesquisarMunicipio + '&sugerirUnidadeDestino=S');
	}

	function redirecionarSubmitBairro() {
		var form = document.forms[0];
		var pesquisaComBairro = false;

		if (isCampoBrancoOuNull(form.idMunicipio)) {
			alert("Informe Município");
			form.cdBairro.value = "";
	        form.idBairro.value = "";
			return;
		}

		if (!validaCampoInteiro(form.idMunicipio)) {
			alert("Município deve ser numérico(a).");
			form.idMunicipio.value = '';
			return;
		}

		pesquisaComBairro = validarCamposNumericos(form);

		if (pesquisaComBairro) {
			pesquisaComBairro = false;
			if (!isCampoBrancoOuNull(form.cdBairro)) {
				pesquisaComBairro = true;
			}
		}
		
	    form.descricaoBairro.value = "";
	   
	    //Coloca o foco no objeto selecionado
	    form.cdBairro.focus();
		
		var pesquisarBairro = '';

		if (pesquisaComBairro) {
			pesquisarBairro = '&pesquisarBairro=OK';
		} else {
			form.cdBairro.value = "";
	        form.idBairro.value = "";
		}
		redirecionarSubmit("inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosLocalOcorrenciaAction" + pesquisarBairro + '&sugerirUnidadeDestino=S');
	}

	function redirecionarSubmitLocalidade() {

		var form = document.forms[0];
		var pesquisarLocalidade = '';
		var pesquisaComLocalidade = false;

		pesquisaComLocalidade = validarCamposNumericos(form);

		if (pesquisaComLocalidade) {
			pesquisaComLocalidade = false;
			if (!isCampoBrancoOuNull(form.idLocalidade)) {
				pesquisaComLocalidade = true;
			}
		}

		form.descricaoLocalidade.value = "";
		form.cdSetorComercial.value = "";
		form.idSetorComercial.value = "";
		form.descricaoSetorComercial.value = "";
		form.idQuadra.value = "";
		form.nnQuadra.value = "";
		
		var msgQuadra = document.getElementById("msgQuadra");
		
		if (msgQuadra != null){
			msgQuadra.innerHTML = "";
		}
		 
		 //Coloca o foco no objeto selecionado
		form.idLocalidade.focus();
		
		var pesquisarLocalidade = '';

		if (pesquisaComLocalidade) {
			pesquisarLocalidade = '&pesquisarLocalidade=OK';
		} else {
			form.idLocalidade.value = '';
		}
		
		redirecionarSubmit("inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosLocalOcorrenciaAction" + pesquisarLocalidade + '&sugerirUnidadeDestino=S');
	}

	function redirecionarSubmitSetorComercial() {
		var form = document.forms[0];
		var pesquisarSetor = '';
		var pesquisaComSetor = false;

		if (isCampoBrancoOuNull(form.idLocalidade)) {
			alert("Informe Localidade");
			form.cdSetorComercial.value = "";
	        form.idSetorComercial.value = "";
			return;
		}

		if (!validaCampoInteiro(form.idLocalidade)) {
			alert("Localidade deve ser numérico(a).");
			form.idLocalidade.value = '';
			return;
		}

		pesquisaComSetor = validarCamposNumericos(form);

		if (pesquisaComSetor) {
			pesquisaComSetor = false;
			if (!isCampoBrancoOuNull(form.cdSetorComercial)) {
				pesquisaComSetor = true;
			}
		}

        form.descricaoSetorComercial.value = "";
        form.idQuadra.value = "";
        form.nnQuadra.value = "";
        
        var msgQuadra = document.getElementById("msgQuadra");

	    if (msgQuadra != null){
		 	msgQuadra.innerHTML = "";
	    }
	   
	    //Coloca o foco no objeto selecionado
        form.cdSetorComercial.focus();
		
		var pesquisarSetor = '';

		if (pesquisaComSetor) {
			pesquisarSetor = '&pesquisarSetorComercial=OK';
		} else {
			form.cdSetorComercial.value = "";
	        form.idSetorComercial.value = "";
		}
		redirecionarSubmit("inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosLocalOcorrenciaAction" + pesquisarSetor + '&sugerirUnidadeDestino=S');
	}


	function validarCamposNumericos(form){
		// Só valida se foi informado algo, e se não é número
		// Se um dos campos informados tiver algo que nãos seja número, retorna false
    	var retorno = false;
    	var msgAlert = "";

		if (!validaCampoInteiro(form.idMunicipio)){
			msgAlert = msgAlert + "Município deve ser numérico(a).\n";
			//form.idMunicipio.value = '';
			//form.cdBairro.value = '';
		}
		
		if (!validaCampoInteiro(form.cdBairro)){
			msgAlert = msgAlert + "Bairro deve ser numérico(a).\n";
			//form.cdBairro.value = '';
		}

		if (!validaCampoInteiro(form.idLocalidade)){
			msgAlert = msgAlert + "Localidade deve ser numérico(a).\n";
			//limpar(8);
		}

		if (!validaCampoInteiro(form.cdSetorComercial)){
			msgAlert = msgAlert + "Setor Comercial deve ser numérico(a).\n";
			//limpar(9);
		}
		
		if (msgAlert.length < 1){
			retorno = true;
		} else {
			alert(msgAlert);
		}
    	
    	return retorno;
    }
    	
//-->
</SCRIPT>


</head>

<logic:notPresent name="msgAlert">
		<body leftmargin="5" topmargin="5" onload="enviarContaRevisaoConfirm('${requestScope.msgEnviarContaRevisao}', '${requestScope.abrirPopUpEnviarContaRevisao}', '<bean:write name="InserirRegistroAtendimentoActionForm" property="especificacao" scope="session"/>');setarFoco('${requestScope.nomeCampo}'); verificarCamposOnLoad(); limitTextArea(document.forms[0].descricaoLocalOcorrencia, 200, document.getElementById('utilizadoLocalOcorrencia'), document.getElementById('limiteLocalOcorrencia')); limitTextArea(document.forms[0].parecerUnidadeDestino, 400, document.getElementById('utilizadoParecer'), document.getElementById('limiteParecer'));">
</logic:notPresent>

<logic:present name="msgAlert">
	
	<logic:present name="msgAlert2">
		<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}'); alert('${requestScope.msgAlert}'); alert('${requestScope.msgAlert2}');enviarContaRevisaoConfirm('${requestScope.msgEnviarContaRevisao}', '${requestScope.abrirPopUpEnviarContaRevisao}', '<bean:write name="InserirRegistroAtendimentoActionForm" property="especificacao" scope="session"/>');verificarCamposOnLoad(); limitTextArea(document.forms[0].descricaoLocalOcorrencia, 200, document.getElementById('utilizadoLocalOcorrencia'), document.getElementById('limiteLocalOcorrencia')); limitTextArea(document.forms[0].parecerUnidadeDestino, 400, document.getElementById('utilizadoParecer'), document.getElementById('limiteParecer'));">
	</logic:present>
	
	<logic:notPresent name="msgAlert2">
		<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}'); alert('${requestScope.msgAlert}');enviarContaRevisaoConfirm('${requestScope.msgEnviarContaRevisao}', '${requestScope.abrirPopUpEnviarContaRevisao}', '<bean:write name="InserirRegistroAtendimentoActionForm" property="especificacao" scope="session"/>');verificarCamposOnLoad(); limitTextArea(document.forms[0].descricaoLocalOcorrencia, 200, document.getElementById('utilizadoLocalOcorrencia'), document.getElementById('limiteLocalOcorrencia')); limitTextArea(document.forms[0].parecerUnidadeDestino, 400, document.getElementById('utilizadoParecer'), document.getElementById('limiteParecer'));">
	</logic:notPresent>
	
</logic:present>

<!-- Disponibiliza os dados do cliente usuário do imóvel na Aba Nº 03 -->
<div id="formDiv">
<html:form action="/inserirRegistroAtendimentoWizardAction" method="post">

<!-- Parâmetro responsável pela formulação da tela de opção -->
<INPUT TYPE="hidden" name="telaOpcao" value="OK">

<html:hidden property="imovelObrigatorio"/>
<html:hidden property="pavimentoRuaObrigatorio"/>
<html:hidden property="pavimentoCalcadaObrigatorio"/>
<html:hidden property="tipoSolicitacao"/>

<html:hidden property="indRuaLocalOcorrencia"/>
<html:hidden property="indCalcadaLocalOcorrencia"/>

<input type="hidden" name="solicitacaoTipoRelativoFaltaAgua" value="${sessionScope.solicitacaoTipoRelativoFaltaAgua}"/>
	<input type="hidden" name="solicitacaoTipoRelativoAreaEsgoto" value="${sessionScope.solicitacaoTipoRelativoAreaEsgoto}"/>

<jsp:include page="/jsp/util/wizard/navegacao_abas_wizard_valida_avancar.jsp?numeroPagina=2"/>


<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

<table width="770" border="0" cellspacing="4" cellpadding="0">
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
          <td class="parabg">Inserir RA - Registro de Atendimento</td>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
        </tr>
      </table>
      <p>&nbsp;</p>

      <table width="100%" border="0">
      <tr>
      	<td colspan="3">Para inserir o RA - Registro de Atendimento, informe os dados do local da ocorrência:</td>
      </tr>
      <tr>
      	<td HEIGHT="30" WIDTH="100"><strong>Imóvel:</strong></td>
        <td colspan="2">
			<html:text property="idImovel" size="10" maxlength="9" tabindex="1" onkeypress="validaEnterComMensagem(event, 'inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosLocalOcorrenciaAction&pesquisarImovel=OK', 'idImovel', 'Matrícula do Imóvel');" onkeyup="habilitarDesabilitarDescricaoLocalOcorrencia();document.forms[0].inscricaoImovel.value='';"/>
			<a href="javascript:if(habilitarDesabilitarDadosLocalOcorrencia(document.forms[0].descricaoLocalOcorrencia)){abrirPopup('exibirPesquisarImovelAction.do', 490, 800);}">
			<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23" height="21" alt="Pesquisar" border="0"></a>

			<logic:present name="corImovel">

				<logic:equal name="corImovel" value="exception">
					<html:text property="inscricaoImovel" size="22" disabled="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
				</logic:equal>

				<logic:notEqual name="corImovel" value="exception">
					<html:text property="inscricaoImovel" size="22" disabled="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
				</logic:notEqual>

			</logic:present>

			<logic:notPresent name="corImovel">

				<logic:empty name="InserirRegistroAtendimentoActionForm" property="idImovel">
					<html:text property="inscricaoImovel" value="" size="22" disabled="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
				</logic:empty>
				<logic:notEmpty name="InserirRegistroAtendimentoActionForm" property="idImovel">
					<html:text property="inscricaoImovel" size="22" disabled="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
				</logic:notEmpty>
				

			</logic:notPresent>
        	
        	<a href="javascript:if(habilitarDesabilitarDadosLocalOcorrencia(document.forms[0].descricaoLocalOcorrencia)){limpar(1);}">
        	<img src="<bean:message key='caminho.imagens'/>limparcampo.gif" alt="Apagar" border="0"></a>
        	
        	&nbsp;
        	<input type="button" class="bottonRightCol" value="RAs Pendentes" tabindex="2"  id="botaoConsultarRAPendente" align="right" onclick="consultarRAsPendentes();" style="width: 100px;">	
       	</td>
	  </tr>
	  <tr>
         <td colspan="3" HEIGHT="10" align="right">
         	<input type="button" class="bottonRightCol" value="Consultar D&eacute;bitos" id="botaoConsultarDebitos" align="right" onclick="consultarDebitos();">	
         </td>
     </tr>
     
     <tr>
         <td colspan="3" HEIGHT="10" align="right">
		    <logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
		    	<input type="button" class="bottonRightCol" value="Colocar Conta Para Revis&atilde;o" id="botaoAdicionarSelecionarContaParaRevisao" onclick="javascript:selecionarContaRevisao('${requestScope.abrirPopUpEnviarContaRevisao}', '<bean:write name="InserirRegistroAtendimentoActionForm" property="especificacao" scope="session"/>');"/>
		    </logic:equal>
	   </td>
     </tr>
     
     <logic:present name="exibirBotaoImportarGis">
	     <tr>
	         <td colspan="3" HEIGHT="10" align="right">
	         	<input type="button" class="bottonRightCol" value="Importar Dados Gis" id="botaoRecuperarDados" align="right" onclick="redirecionarSubmit('inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosLocalOcorrenciaAction&recuperarDadosAcquaGis=OK')">	
	         </td>
	     </tr>
     </logic:present>
     
      <tr>
         <td colspan="3" HEIGHT="20"></td>
     </tr>
      <tr>
         <td colspan="3">

			<table width="100%" border="0">
	      	<tr>
	      		<td><strong>Endereço da Ocorrência:</strong></td>
	      		<td align="right">
	      		
	      			<logic:present name="colecaoEnderecos">
		 
						<logic:empty name="colecaoEnderecos">
							<input type="button" class="bottonRightCol" value="Adicionar" tabindex="3" id="botaoEndereco" onclick="javascript:if(habilitarDesabilitarDadosLocalOcorrencia(document.forms[0].descricaoLocalOcorrencia)){abrirPopupComSubmit('inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosLocalOcorrenciaAction&tipoPesquisaEndereco=registroAtendimento&operacao=1', 570, 700, 'Endereco');}">
						</logic:empty>
						<logic:notEmpty name="colecaoEnderecos">
							<input type="button" class="bottonRightCol" value="Adicionar" tabindex="3" id="botaoEndereco" disabled>
						</logic:notEmpty>
				 
				 	</logic:present>
		
				 	<logic:notPresent name="colecaoEnderecos">
					
						<logic:present name="habilitarAlteracaoEndereco">
							<logic:equal name="habilitarAlteracaoEndereco" value="SIM">
								<input type="button" class="bottonRightCol" value="Adicionar" tabindex="3"  id="botaoEndereco" onclick="javascript:if(habilitarDesabilitarDadosLocalOcorrencia(document.forms[0].descricaoLocalOcorrencia)){abrirPopupComSubmit('inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosLocalOcorrenciaAction&tipoPesquisaEndereco=registroAtendimento&operacao=1', 570, 700, 'Endereco');}">
						 	</logic:equal>
						 	<logic:notEqual name="habilitarAlteracaoEndereco" value="SIM">
						 		<input type="button" class="bottonRightCol" value="Adicionar" tabindex="3" id="botaoEndereco" disabled>
						 	</logic:notEqual>
						</logic:present>
					
						<logic:notPresent name="habilitarAlteracaoEndereco">
							<input type="button" class="bottonRightCol" value="Adicionar" tabindex="3"  id="botaoEndereco" onclick="javascript:if(habilitarDesabilitarDadosLocalOcorrencia(document.forms[0].descricaoLocalOcorrencia)){abrirPopupComSubmit('inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosLocalOcorrenciaAction&tipoPesquisaEndereco=registroAtendimento&operacao=1', 570, 700, 'Endereco');}">
						</logic:notPresent>
				 	
				 	</logic:notPresent>
	      		
	      		</td>
	      	</tr>
		 	</table>
		 </td>
     </tr>
     <tr>
         <td colspan="3" height="50" valign="top">
			<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<table width="100%" border="0" bgcolor="#90c7fc">
					<tr bgcolor="#90c7fc" height="18">
						<td width="10%" align="center"><strong>Remover</strong></td>
						<td align="center"><strong>Endereço da Ocorrência</strong></td>
					</tr>
					</table>
				</td>
			</tr>

			<logic:present name="colecaoEnderecos">
			
			<input type="hidden" id="enderecoInformado">

			<tr>
				<td>
					<table width="100%" align="center" bgcolor="#99CCFF">
						<!--corpo da segunda tabela-->

						<% String cor = "#cbe5fe";%>

						<logic:iterate name="colecaoEnderecos" id="endereco">
						
							<%	if (cor.equalsIgnoreCase("#cbe5fe")){	
								cor = "#FFFFFF";%>
								<tr bgcolor="#FFFFFF" height="18">	
							<%} else{	
								cor = "#cbe5fe";%>
								<tr bgcolor="#cbe5fe" height="18">		
							<%}%>
						
								<td width="10%" align="center">
									<logic:equal name="habilitarAlteracaoEndereco" value="SIM">
										<a href="javascript:if(confirm('Confirma remoção?')){remover();}" alt="Remover"><img src="<bean:message key='caminho.imagens'/>Error.gif" width="14" height="14" border="0"></a>
									</logic:equal>
									
									<logic:notEqual name="habilitarAlteracaoEndereco" value="SIM">
										
										<logic:present name="enderecoPertenceImovel">
											<img src="<bean:message key='caminho.imagens'/>Error.gif" width="14" height="14" border="0">
										</logic:present>
										
										<logic:notPresent name="enderecoPertenceImovel">
											<a href="javascript:if(confirm('Confirma remoção?')){remover();}" alt="Remover"><img src="<bean:message key='caminho.imagens'/>Error.gif" width="14" height="14" border="0"></a>
										</logic:notPresent>
										
									</logic:notEqual>
								</td>
								
								<td>
									<logic:equal name="habilitarAlteracaoEndereco" value="SIM">
										<a href="javascript:abrirPopup('exibirInserirEnderecoAction.do?exibirEndereco=OK&tipoPesquisaEndereco=registroAtendimento&operacao=1', 570, 700)"><bean:write name="endereco" property="enderecoFormatado"/></a>
									</logic:equal>
									
									<logic:notEqual name="habilitarAlteracaoEndereco" value="SIM">
										<bean:write name="endereco" property="enderecoFormatado"/>
									</logic:notEqual>
								</td>
							</tr>
						</logic:iterate>

					</table>
			  	</td>
			</tr>

			</logic:present>

			</table>
	   </td>
   	  </tr>
   	  <tr>
   	  	<td><strong>Referência:</strong></td>
        <td colspan="2">
			<html:text property="pontoReferencia" size="50" maxlength="60" tabindex="4" onkeyup="habilitarDesabilitarDescricaoLocalOcorrencia();"/>
		</td>
	  </tr>
	  
	  <logic:equal name="solicitacaoTipoRelativoFaltaAgua" value="SIM">
	  <tr>
        <td><strong>Município:</strong></td>
        <td colspan="2">
        
        	<logic:present name="desabilitarMunicipioBairro">
        	
        		<html:text property="idMunicipio" size="5" maxlength="4" tabindex="5" disabled="true"/>
			
				<img width="23" height="21" border="0"
				src="<bean:message key="caminho.imagens"/>pesquisa.gif"
				title="Pesquisar Município" />
			
				<html:text property="descricaoMunicipio" size="42" disabled="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
				
				
				<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
				border="0" title="Apagar" />
				
        	</logic:present>
        	
        	<logic:notPresent name="desabilitarMunicipioBairro">
			
				<html:text property="idMunicipio" size="5" maxlength="4" tabindex="5" onkeypress="validaEnterComMensagem(event, 'inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosLocalOcorrenciaAction&pesquisarMunicipio=OK&sugerirUnidadeDestino=S', 'idMunicipio', 'Município');" onkeyup="habilitarDesabilitarDescricaoLocalOcorrencia();"  onchange="javascript:redirecionarSubmitMunicipio();"/>
				
				<a href="javascript:if(habilitarDesabilitarDadosLocalOcorrencia(document.forms[0].descricaoLocalOcorrencia)){abrirPopup('exibirPesquisarMunicipioAction.do', 250, 495);}">
					<img width="23" height="21" border="0"
					src="<bean:message key="caminho.imagens"/>pesquisa.gif"
					title="Pesquisar Município" /></a>
				
				<logic:present name="corMunicipio">
	
					<logic:equal name="corMunicipio" value="exception">
						<html:text property="descricaoMunicipio" size="42" disabled="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
					</logic:equal>
	
					<logic:notEqual name="corMunicipio" value="exception">
						<html:text property="descricaoMunicipio" size="42" disabled="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
					</logic:notEqual>
	
				</logic:present>
	
				<logic:notPresent name="corMunicipio">
	
					<logic:empty name="InserirRegistroAtendimentoActionForm" property="idMunicipio">
						<html:text property="descricaoMunicipio" size="42" value="" disabled="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
					</logic:empty>
					<logic:notEmpty name="InserirRegistroAtendimentoActionForm" property="idMunicipio">
						<html:text property="descricaoMunicipio" size="42" disabled="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
					</logic:notEmpty>
					
				</logic:notPresent>
				<a	href="javascript:if(habilitarDesabilitarDadosLocalOcorrencia(document.forms[0].descricaoLocalOcorrencia)){limpar(3);};redirecionarSubmitMunicipio();">
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
					border="0" title="Apagar" /> </a>
					
			</logic:notPresent>
			
		</td>
      </tr>
      </logic:equal>
      
      <logic:notEqual name="solicitacaoTipoRelativoFaltaAgua" value="SIM">
	  <tr>
        <td><strong>Município:</strong></td>
        <td colspan="2">
			<html:text property="idMunicipio" size="5" maxlength="4" tabindex="5" disabled="true"/>
			
				<img width="23" height="21" border="0"
				src="<bean:message key="caminho.imagens"/>pesquisa.gif"
				title="Pesquisar Município" />
			
				<html:text property="descricaoMunicipio" size="42" disabled="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
				
				
				<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
				border="0" title="Apagar" /> 
			</td>
      </tr>
      </logic:notEqual>
      
      
      
      <logic:equal name="solicitacaoTipoRelativoFaltaAgua" value="SIM">
      <tr>
		<td><strong>Bairro:</strong></td>
		<td colspan="2">
			
			<logic:present name="desabilitarMunicipioBairro">
			
				<html:text property="cdBairro" size="5" maxlength="4" tabindex="6" disabled="true"/>
			
				<img width="23" height="21" border="0"
				src="<bean:message key="caminho.imagens"/>pesquisa.gif"
				title="Pesquisar Bairro" />
			
				<html:text property="descricaoBairro" size="42" disabled="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
				
				<html:hidden property="idBairro"/>
				<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
				border="0" title="Apagar" />
				
			</logic:present>
			
			<logic:notPresent name="desabilitarMunicipioBairro">
			
				<html:text property="cdBairro" size="5" maxlength="4" tabindex="6" onkeypress="validaEnterDependencia(event, 'inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosLocalOcorrenciaAction&pesquisarBairro=OK&sugerirUnidadeDestino=S', document.forms[0].cdBairro, document.forms[0].idMunicipio.value, 'Município');" onkeyup="limpar(11); habilitarDesabilitarDescricaoLocalOcorrencia();" onchange="javascript:redirecionarSubmitBairro();"/>
				
				<a	href="javascript:abrirPopupDependencia('exibirPesquisarBairroAction.do?idMunicipio='+document.forms[0].idMunicipio.value+'&indicadorUsoTodos=1', document.forms[0].idMunicipio.value, 'Município', 275, 480);">
					<img width="23" height="21" border="0"
					src="<bean:message key="caminho.imagens"/>pesquisa.gif"
					title="Pesquisar Bairro" /></a>
				
				<logic:present name="corBairro">
	
					<logic:equal name="corBairro" value="exception">
						<html:text property="descricaoBairro" size="42" disabled="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
					</logic:equal>
	
					<logic:notEqual name="corBairro" value="exception">
						<html:text property="descricaoBairro" size="42" disabled="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
					</logic:notEqual>
	
				</logic:present>
	
				<logic:notPresent name="corBairro">
				
					<logic:empty name="InserirRegistroAtendimentoActionForm" property="cdBairro">
						<html:text property="descricaoBairro" size="42" value="" disabled="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
					</logic:empty>
					<logic:notEmpty name="InserirRegistroAtendimentoActionForm" property="cdBairro">
						<html:text property="descricaoBairro" size="42" disabled="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
					</logic:notEmpty>
					
				</logic:notPresent>
	
				<html:hidden property="idBairro"/>
				<a	href="javascript:if(habilitarDesabilitarDadosLocalOcorrencia(document.forms[0].descricaoLocalOcorrencia)){limpar(4);};redirecionarSubmitBairro();">
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
					border="0" title="Apagar" /> </a>
					
			</logic:notPresent>
			
		</td>
	  </tr>
	  </logic:equal>
	    
	  <logic:notEqual name="solicitacaoTipoRelativoFaltaAgua" value="SIM">
	  <tr>
		<td><strong>Bairro:</strong></td>
		<td colspan="2">
				<html:text property="cdBairro" size="5" maxlength="4" tabindex="6" disabled="true"/>
			
				<img width="23" height="21" border="0"
				src="<bean:message key="caminho.imagens"/>pesquisa.gif"
				title="Pesquisar Bairro" />
			
				<html:text property="descricaoBairro" size="42" disabled="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
				
				<html:hidden property="idBairro"/>
				<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
				border="0" title="Apagar" /> 
		</td>
	  </tr>
	  </logic:notEqual>
	    
	    
	    
	    <logic:equal name="solicitacaoTipoRelativoFaltaAgua" value="SIM">
	    <tr>
        <td><strong>Área do Bairro:</strong></td>
        <td colspan="2">
			<html:select property="idBairroArea" style="width: 200px;" tabindex="7" onchange="habilitarDesabilitarDescricaoLocalOcorrencia();">
				<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
				<logic:present name="colecaoBairroArea">
					<html:options collection="colecaoBairroArea" labelProperty="nome" property="id"/>
				</logic:present>
			</html:select>
		</td>
      	</tr>
      	</logic:equal>
      	
      	<logic:notEqual name="solicitacaoTipoRelativoFaltaAgua" value="SIM">
   	    <tr>
        <td><strong>Área do Bairro:</strong></td>
        <td colspan="2">
			<html:select property="idBairroArea" style="width: 200px;" tabindex="7" disabled="true">
				<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
			</html:select>
		</td>
      	</tr>
   	    </logic:notEqual>
   	    
   	    
   	    <tr>
      		<td colspan="3" height="20"></td>
      	</tr>
      	
   	    
   	    <tr>
		   <td><strong>Localidade:</strong></td>
           <td colspan="2">
           		
           		<logic:present name="desabilitarLcalidadeSetorQuadra">
	           		<html:text maxlength="3" property="idLocalidade" size="4"  tabindex="8" disabled="true"/>
	            	<img border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/>
				</logic:present>
				
				<logic:notPresent name="desabilitarLcalidadeSetorQuadra">
	           		<html:text maxlength="3" property="idLocalidade" size="4"  tabindex="8" 
	            	onkeypress="validaEnterComMensagem(event, 'inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosLocalOcorrenciaAction&pesquisarLocalidade=OK&sugerirUnidadeDestino=S', 'idLocalidade', 'Localidade');" onkeyup="habilitarDesabilitarDescricaoLocalOcorrencia();" onchange="javascript:redirecionarSubmitLocalidade();"/>
	            	<a href="javascript:if(habilitarDesabilitarDadosLocalOcorrencia(document.forms[0].descricaoLocalOcorrencia)){abrirPopup('exibirPesquisarLocalidadeAction.do?tipo=imovelLocalidade', 400, 800); limpar(5);};redirecionarSubmitLocalidade();">
	                <img border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/></a>
				</logic:notPresent>

   		      	<logic:present name="corLocalidade">

					<logic:equal name="corLocalidade" value="exception">
						<html:text property="descricaoLocalidade" size="45" disabled="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
					</logic:equal>
	
					<logic:notEqual name="corLocalidade" value="exception">
						<html:text property="descricaoLocalidade" size="45" disabled="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
					</logic:notEqual>

				</logic:present>

				<logic:notPresent name="corLocalidade">

					<logic:empty name="InserirRegistroAtendimentoActionForm" property="idLocalidade">
						<html:text property="descricaoLocalidade" size="45" value="" disabled="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
					</logic:empty>
					<logic:notEmpty name="InserirRegistroAtendimentoActionForm" property="idLocalidade">
						<html:text property="descricaoLocalidade" size="45" disabled="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
					</logic:notEmpty>
				
				</logic:notPresent>
			
				<logic:present name="desabilitarLcalidadeSetorQuadra">
					<img
					src="<bean:message key="caminho.imagens"/>limparcampo.gif"
					border="0" title="Apagar" />
				</logic:present>
				
				<logic:notPresent name="desabilitarLcalidadeSetorQuadra">
					<a href="javascript:if(habilitarDesabilitarDadosLocalOcorrencia(document.forms[0].descricaoLocalOcorrencia)){limpar(8);};redirecionarSubmitLocalidade();"> <img
					src="<bean:message key="caminho.imagens"/>limparcampo.gif"
					border="0" title="Apagar" /></a>
				</logic:notPresent>
				                   
			</td>
        </tr>
                
        <tr>
            <td><strong>Setor Comercial:</strong></td>
            <td colspan="2">
            
            	<logic:present name="desabilitarLcalidadeSetorQuadra">
            		<html:text maxlength="3" property="cdSetorComercial" size="4"  
                	tabindex="9" disabled="true"/>
                	<img border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/>
   		      	</logic:present>
   		      	
   		      	<logic:notPresent name="desabilitarLcalidadeSetorQuadra">
            		<html:text maxlength="3" property="cdSetorComercial" size="4"  
                	tabindex="9" onkeypress="validaEnterDependencia(event, 'inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosLocalOcorrenciaAction&pesquisarSetorComercial=OK&sugerirUnidadeDestino=S', this, document.forms[0].idLocalidade.value, 'Localidade');" onkeyup="habilitarDesabilitarDescricaoLocalOcorrencia();" onchange="javascript:redirecionarSubmitSetorComercial();"/>
                	<a href="javascript:if(habilitarDesabilitarDadosLocalOcorrencia(document.forms[0].descricaoLocalOcorrencia)){abrirPopupDependencia('exibirPesquisarSetorComercialAction.do?idLocalidade='+document.forms[0].idLocalidade.value+'&tipo=SetorComercial',document.forms[0].idLocalidade.value,'Localidade', 400, 800);limpar(6);};">
					<img border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/></a>
   		      	</logic:notPresent>
   		      
   		      	<logic:present name="corSetorComercial">

					<logic:equal name="corSetorComercial" value="exception">
						<html:text property="descricaoSetorComercial" size="45" disabled="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
					</logic:equal>
	
					<logic:notEqual name="corSetorComercial" value="exception">
						<html:text property="descricaoSetorComercial" size="45" disabled="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
					</logic:notEqual>

				</logic:present>

				<logic:notPresent name="corSetorComercial">

					<logic:empty name="InserirRegistroAtendimentoActionForm" property="cdSetorComercial">
						<html:text property="descricaoSetorComercial" size="45" value="" disabled="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
					</logic:empty>
					<logic:notEmpty name="InserirRegistroAtendimentoActionForm" property="cdSetorComercial">
						<html:text property="descricaoSetorComercial" size="45" disabled="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
					</logic:notEmpty>
				
				</logic:notPresent>
				
				<html:hidden property="idSetorComercial"/>
				
				<logic:present name="desabilitarLcalidadeSetorQuadra">
					<img
					src="<bean:message key="caminho.imagens"/>limparcampo.gif"
					border="0" title="Apagar" />
            	</logic:present>
            	
            	<logic:notPresent name="desabilitarLcalidadeSetorQuadra">
					<a href="javascript:if(habilitarDesabilitarDadosLocalOcorrencia(document.forms[0].descricaoLocalOcorrencia)){limpar(9);};redirecionarSubmitSetorComercial();"> <img
					src="<bean:message key="caminho.imagens"/>limparcampo.gif"
					border="0" title="Apagar" /></a>
            	</logic:notPresent>
            	
            </td>
        </tr>
        <tr>
            <td><strong>Quadra:</strong></td>
            <td colspan="2">
            
            	<logic:present name="desabilitarLcalidadeSetorQuadra">
            		<html:text maxlength="5" property="nnQuadra" size="4"  tabindex="10" disabled="true"/>
				</logic:present>
				
				<logic:notPresent name="desabilitarLcalidadeSetorQuadra">
            		<html:text maxlength="5" property="nnQuadra" size="4"  tabindex="10"
                	onkeypress="validaEnterDependencia(event, 'inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosLocalOcorrenciaAction&pesquisarQuadra=OK', this, document.forms[0].idSetorComercial.value, 'Setor Comercial');" onkeyup="limpar(7); habilitarDesabilitarDescricaoLocalOcorrencia();"/>
				</logic:notPresent>
				
				<html:hidden property="idQuadra"/>
				
				<logic:present name="msgQuadra" scope="request">
					<span style="color:#ff0000" id="msgQuadra"><bean:write scope="request" name="msgQuadra"/></span>
                </logic:present>

            </td>
        </tr>
        
        
        <logic:equal name="solicitacaoTipoRelativoAreaEsgoto" value="SIM">
        <tr>
        <td><strong>Divisão de Esgoto: </strong></td>
        <td colspan="2">
			
			<logic:present name="desabilitarDivisaoEsgoto">
			
				<html:select property="idDivisaoEsgoto" style="width: 200px;" tabindex="11" disabled="true">
					<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
					<logic:present name="colecaoDivisaoEsgoto">
						<html:options collection="colecaoDivisaoEsgoto" labelProperty="descricao" property="id"/>
					</logic:present>
				</html:select>
			
			</logic:present>
			
			<logic:notPresent name="desabilitarDivisaoEsgoto">
			
				<html:select property="idDivisaoEsgoto" style="width: 200px;" tabindex="11" onchange="verificarCompatibilidadeDivisaoEsgoto();">
					<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
					<logic:present name="colecaoDivisaoEsgoto">
						<html:options collection="colecaoDivisaoEsgoto" labelProperty="descricao" property="id"/>
					</logic:present>
				</html:select>
			
			</logic:notPresent>
			
		</td>
      	</tr>
      	</logic:equal>
      	
      	<logic:notEqual name="solicitacaoTipoRelativoAreaEsgoto" value="SIM">
      	<tr>
        <td><strong>Divisão de Esgoto:</strong></td>
        <td colspan="2">
			<html:select property="idDivisaoEsgoto" style="width: 200px;" tabindex="11" disabled="true">
				<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
				<logic:present name="colecaoDivisaoEsgoto">
					<html:options collection="colecaoDivisaoEsgoto" labelProperty="descricao" property="id"/>
				</logic:present>
			</html:select>
		</td>
      	</tr>
      	</logic:notEqual>
      	
      	
      	<tr>
		<td><strong>Unidade Destino:</strong></td>
		<td colspan="2">
			
			<logic:present name="desabilitarUnidadeDestino">
			
				<html:text property="idUnidadeDestino" size="8" maxlength="8" tabindex="12" disabled="true"/>
			
				<img width="23" height="21" border="0"
				src="<bean:message key="caminho.imagens"/>pesquisa.gif"
				title="Pesquisar Unidade Destino" />
			
				<html:text property="descricaoUnidadeDestino" size="40" disabled="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
				
				<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
				border="0" title="Apagar" />
				
			</logic:present>
			
			<logic:notPresent name="desabilitarUnidadeDestino">
			
				<html:text property="idUnidadeDestino" size="8" maxlength="8" tabindex="12" onkeypress="validaEnterComMensagem(event, 'inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosLocalOcorrenciaAction&pesquisarUnidadeDestino=OK&pesquisarUnidadeDestinoEmEspecificacoTramite=N', 'idUnidadeDestino', 'Unidade Destino');" onkeyup="limpar(12); habilitarDesabilitarDescricaoLocalOcorrencia();"/>
				
				<a	href="javascript:if(habilitarDesabilitarDadosLocalOcorrencia(document.forms[0].descricaoLocalOcorrencia)){abrirPopup('exibirPesquisarUnidadeOrganizacionalAction.do', 410, 790);}">
					<img width="23" height="21" border="0"
					src="<bean:message key="caminho.imagens"/>pesquisa.gif"
					title="Pesquisar Unidade Destino" /></a>
				
				<logic:present name="corUnidadeDestino">
	
					<logic:equal name="corUnidadeDestino" value="exception">
						<html:text property="descricaoUnidadeDestino" size="40" disabled="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
					</logic:equal>
	
					<logic:notEqual name="corUnidadeDestino" value="exception">
						<html:text property="descricaoUnidadeDestino" size="40" disabled="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
					</logic:notEqual>
	
				</logic:present>
	
				<logic:notPresent name="corUnidadeDestino">
	
					<logic:empty name="InserirRegistroAtendimentoActionForm" property="idUnidadeDestino">
						<html:text property="descricaoUnidadeDestino" size="40" value="" disabled="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
					</logic:empty>
					
					<logic:notEmpty name="InserirRegistroAtendimentoActionForm" property="idUnidadeDestino">
						<html:text property="descricaoUnidadeDestino" size="40" disabled="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
					</logic:notEmpty>
					
				</logic:notPresent>
	
				<a	href="javascript:if(habilitarDesabilitarDadosLocalOcorrencia(document.forms[0].descricaoLocalOcorrencia)){limpar(10);}">
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
					border="0" title="Apagar" /> </a>
					
			</logic:notPresent>
			
			</td>
	  	</tr>
	  	
	  	<tr>
      		<td HEIGHT="30"><strong>Parecer do Trâmite:</strong></td>
        	<td colspan="2">
				<html:textarea property="parecerUnidadeDestino" cols="40" rows="2" onkeyup="habilitarDesabilitarDescricaoLocalOcorrencia(); limitTextArea(this, 400, document.getElementById('utilizadoParecer'), document.getElementById('limiteParecer'));"/><br>
				<strong><span id="utilizadoParecer">0</span>/<span id="limiteParecer">400</span></strong>
			</td>
      	</tr>
      	
      	
      	<tr>
      		<td colspan="3" height="20"></td>
      	</tr>
      	
      	
      	<tr>
        <td><strong>Local da Ocorrência:</strong></td>
        <td colspan="2">
			<html:select property="idLocalOcorrencia" style="width: 200px;" tabindex="13" onchange="habilitarDesabilitarDescricaoLocalOcorrencia();carregarIndicadorLocalOcorrencia(this);">
				<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
				<logic:present name="colecaoLocalOcorrencia">
					<logic:iterate id="localOcorrencia" name="colecaoLocalOcorrencia" type="LocalOcorrencia">
						<option value="<%=""+localOcorrencia.getId() %>" 
						id="<%=""+localOcorrencia.getIndicadorRua() %>"
						name="<%=""+localOcorrencia.getIndicadorCalcada() %>"><%=""+localOcorrencia.getDescricao() %></option>
					</logic:iterate>
				</logic:present>
			</html:select>
		</td>
      	</tr>
      	
      	<tr>
        <td><strong>Pavimento da Rua:</strong></td>
        <td colspan="2">
        
        	<logic:present name="desabilitarPavimentoRua">
				<html:select property="idPavimentoRua" style="width: 200px;" tabindex="14" disabled="true">
					<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
					<logic:present name="colecaoPavimentoRua">
						<html:options collection="colecaoPavimentoRua" labelProperty="descricao" property="id"/>
					</logic:present>
				</html:select>
			</logic:present>
			
			<logic:notPresent name="desabilitarPavimentoRua">
				<html:select property="idPavimentoRua" style="width: 200px;" tabindex="14" onchange="habilitarDesabilitarDescricaoLocalOcorrencia();">
					<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
					<logic:present name="colecaoPavimentoRua">
						<html:options collection="colecaoPavimentoRua" labelProperty="descricao" property="id"/>
					</logic:present>
				</html:select>
			</logic:notPresent>
			
		</td>
      	</tr>
      	
      	<tr>
        <td><strong>Pavimento da Calçada:</strong></td>
        <td colspan="2">
			
			<logic:present name="desabilitarPavimentoCalcada">
				<html:select property="idPavimentoCalcada" style="width: 200px;" tabindex="15" disabled="true">
					<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
					<logic:present name="colecaoPavimentoCalcada">
						<html:options collection="colecaoPavimentoCalcada" labelProperty="descricao" property="id"/>
					</logic:present>
				</html:select>
			</logic:present>
			
			<logic:notPresent name="desabilitarPavimentoCalcada">
				<html:select property="idPavimentoCalcada" style="width: 200px;" tabindex="15" onchange="habilitarDesabilitarDescricaoLocalOcorrencia();">
					<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
					<logic:present name="colecaoPavimentoCalcada">
						<html:options collection="colecaoPavimentoCalcada" labelProperty="descricao" property="id"/>
					</logic:present>
				</html:select>
			</logic:notPresent>
			
		</td>
      	</tr>
      	
      	
      	<tr>
      		<td colspan="3" height="20"></td>
      	</tr>
      	
      	<logic:present name="desabilitarDescricaoLocalOcorrencia">
	      	<tr>
	      		<td HEIGHT="30"><strong>Descrição do Local da Ocorrência:</strong></td>
	        	<td colspan="2">
					<html:textarea property="descricaoLocalOcorrencia" cols="40" rows="2" disabled="true" onkeyup="limitTextArea(this, 200, document.getElementById('utilizadoLocalOcorrencia'), document.getElementById('limiteLocalOcorrencia'));"/><br>
					<strong><span id="utilizado">0</span>/<span id="limite">200</span></strong>
				</td>
	      	</tr>
      	</logic:present>
      	
      	<logic:notPresent name="desabilitarDescricaoLocalOcorrencia">
	      	<tr>
	      		<td HEIGHT="30"><strong>Descrição Local Ocorrência:</strong></td>
	        	<td colspan="2">
					<html:textarea property="descricaoLocalOcorrencia" cols="40" rows="2" onkeyup="habilitarDesabilitarDadosLocalOcorrencia(this); limitTextArea(this, 200, document.getElementById('utilizadoLocalOcorrencia'), document.getElementById('limiteLocalOcorrencia'));"/><br>
					<strong><span id="utilizadoLocalOcorrencia">0</span>/<span id="limiteLocalOcorrencia">200</span></strong>
				</td>
	      	</tr>
      	</logic:notPresent>
      	
      <tr>
   	  	<td><strong>Coordenada Norte:</strong></td>
        <td colspan="2">
			<html:text property="coordenadaNorte" size="50" maxlength="60" disabled="true"/>
		</td>
	  </tr>
	  
	  <tr>
   	  	<td><strong>Coordenada Leste:</strong></td>
        <td colspan="2">
			<html:text property="coordenadaLeste" size="50" maxlength="60" disabled="true"/>
		</td>
	  </tr>
	  <tr>
      	<td HEIGHT=30><strong>Motivo Atendimento Incompleto:</strong></td>
        <td>
			<html:select property="motivoAtendimentoIncompleto" style="width: 350px;font-size:11px;" tabindex="14" > 
				<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
				<html:options collection="colecaoMotivoAtendimentoIncompleto" labelProperty="descricao" property="id"/>
			</html:select>
		</td>
      </tr>
        <tr>
      		<td colspan="3" height="10"></td>
      	</tr>
      	<tr>
        	<td colspan="3">
			<div align="right">
				<jsp:include page="/jsp/util/wizard/navegacao_botoes_wizard_valida_avancar_ra.jsp?numeroPagina=2"/>
			</div>
		</td>
      </tr>
      </table>
      <p>&nbsp;</p>
	</td>
  </tr>
</table>



<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</div>
<%@ include file="/jsp/util/telaespera.jsp"%>

<script>
document.getElementById('botaoConcluir').onclick = function() { botaoAvancarTelaEspera('/gsan/inserirRegistroAtendimentoWizardAction.do?concluir=true&action=inserirRegistroAtendimentoDadosLocalOcorrenciaAction'); }
</script>

</body>
</html:html>
