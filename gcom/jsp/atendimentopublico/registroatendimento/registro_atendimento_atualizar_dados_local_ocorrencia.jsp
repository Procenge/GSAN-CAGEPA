<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page import="gcom.atendimentopublico.registroatendimento.LocalOcorrencia"%>
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
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="AtualizarRegistroAtendimentoActionForm"
	dynamicJavascript="false" />

<SCRIPT LANGUAGE="JavaScript">

<!--
window.onmousemove = verificarCamposHistoryBack;
	var bCancel = false; 

    function validateAtualizarRegistroAtendimentoActionForm(form) {                                                                   
        if (bCancel) 
      return true; 
        else 
       return validateCaracterEspecial(form) && validateInteger(form) && validateInteiroZeroPositivo(form) && validarCamposRequeridos(form); 
   } 

    function caracteresespeciais () { 
     this.ag = new Array("idImovel", "Im�vel possui caracteres especiais.", new Function ("varName", " return this[varName];"));
    // this.ah = new Array("pontoReferencia", "Im�vel possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ai = new Array("idMunicipio", "Munic�pio possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.aj = new Array("cdBairro", "Bairro possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ak = new Array("idLocalidade", "Localidade possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.al = new Array("cdSetorComercial", "Setor Comercial possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.am = new Array("nnQuadra", "Quadra possui caracteres especiais.", new Function ("varName", " return this[varName];"));
    // this.ap = new Array("descricaoLocalOcorrencia", "Parecer possui caracteres especiais.", new Function ("varName", " return this[varName];"));
    } 

    function IntegerValidations () { 
     this.ab = new Array("idImovel", "Im�vel deve ser num�rico(a).", new Function ("varName", " return this[varName];"));
     this.ac = new Array("idMunicipio", "Munic�pio deve ser num�rico(a).", new Function ("varName", " return this[varName];"));
     this.ad = new Array("idLocalidade", "Localidade deve ser num�rico(a).", new Function ("varName", " return this[varName];"));
    }
    
    
    function InteiroZeroPositivoValidations () { 
     this.ar = new Array("cdBairro", "Bairro deve somente conter n�meros positivos ou zero.", new Function ("varName", " return this[varName];"));
     this.as = new Array("cdSetorComercial", "Setor Comercial deve somente conter n�meros positivos ou zero.", new Function ("varName", " return this[varName];"));
     this.at = new Array("nnQuadra", "Quadra deve somente conter n�meros positivos ou zero.", new Function ("varName", " return this[varName];"));
    }
    
    
    function validarCamposRequeridos(form){
    
    	var retorno = false;
    	var msgAlert = "";
    	 
    	if (dadosLocalOcorrenciaInformados() || form.descricaoLocalOcorrencia.value.length > 0){
    	
    		if (form.descricaoLocalOcorrencia.value.length > 0){
    			alert("O RA - Registro de Atendimento ficar� bloqueado at� que seja informado o endere�o da ocorr�ncia");
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
    				msgAlert = "Informe Im�vel \n";			
    			}
    			
    			if (form.idBairroArea.disabled && !botaoEndereco.disabled && enderecoInformado == null){
    				msgAlert = "Informe Endere�o \n";
    			}
    			
    			if (!form.idMunicipio.readOnly && form.idMunicipio.value.length < 1){
    				msgAlert = msgAlert + "Informe Munic�pio \n";			
    			}
    			
    			if (!form.cdBairro.readOnly && form.cdBairro.value.length < 1){
    				msgAlert = msgAlert + "Informe Bairro \n";			
    			}
    			
    			if (!form.idBairroArea.disabled && form.idBairroArea.value < 0){
    				msgAlert = msgAlert + "Informe �rea do Bairro \n";			
    			}
    			
    			if (!form.idLocalidade.readOnly && form.idLocalidade.value.length < 1){
    				msgAlert = msgAlert + "Informe Localidade \n";			
    			}
    			
    			if (!form.nnQuadra.readOnly && form.nnQuadra.value.length > 0 && form.cdSetorComercial.value.length < 1){
    				msgAlert = msgAlert + "Informe Setor Comercial \n";			
    			}
    			
    			if (!form.idDivisaoEsgoto.disabled && form.idDivisaoEsgoto.value < 0){
    				msgAlert = msgAlert + "Informe Divis�o de Esgoto \n";			
    			}
    			
    			if (!form.idPavimentoRua.disabled && form.idPavimentoRua.value < 0 && 
    				(pavimentoRuaObrigatorio == "SIM" || localOcorrencia.id == "1")){
    				msgAlert = msgAlert + "Informe Pavimento da Rua \n";			
    			}
    			
    			if (!form.idPavimentoCalcada.disabled && form.idPavimentoCalcada.value < 0 && 
    				(pavimentoCalcadaObrigatorio == "SIM" || localOcorrencia.name == "1")){
    				msgAlert = msgAlert + "Informe Pavimento da Cal�ada \n";			
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
    		alert("Informe os  Dados da Identifica��o do Local da Ocorr�ncia ou a Descri��o do Local da Ocorr�ncia");
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
			   redirecionarSubmit("atualizarRegistroAtendimentoWizardAction.do?action=exibirAtualizarRegistroAtendimentoDadosLocalOcorrenciaAction&limparImovel=OK");
			   break;
		  case 2:
		   	   form.cdBairro.value = "";
		       form.descricaoBairro.value = "";
		       form.idBairro.value = "";
		       form.descricaoMunicipio.value = "";
		       
		       if (form.idBairroArea.options.length > 1){
		       		redirecionarSubmit("atualizarRegistroAtendimentoWizardAction.do?action=exibirAtualizarRegistroAtendimentoDadosLocalOcorrenciaAction&removerColecaoBairroArea=OK&campoFoco=idMunicipio");
		       }
		       
		       break;
		  case 3:
		       form.idMunicipio.value = "";
		       form.descricaoMunicipio.value = "";
		       form.cdBairro.value = "";
		       form.idBairro.value = "";
		       form.descricaoBairro.value = "";
 			   
 			   if (form.idBairroArea.options.length > 1){
		       		redirecionarSubmit("atualizarRegistroAtendimentoWizardAction.do?action=exibirAtualizarRegistroAtendimentoDadosLocalOcorrenciaAction&removerColecaoBairroArea=OK&campoFoco=idMunicipio");
		       }
		       
		       //Coloca o foco no objeto selecionado
		       form.idMunicipio.focus();
		       break;
		  case 4:
		   	   form.cdBairro.value = "";
		       form.idBairro.value = "";
		       form.descricaoBairro.value = "";
			   
			   if (form.idBairroArea.options.length > 1){
		       		redirecionarSubmit("atualizarRegistroAtendimentoWizardAction.do?action=exibirAtualizarRegistroAtendimentoDadosLocalOcorrenciaAction&removerColecaoBairroArea=OK&campoFoco=cdBairro");
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
		   	   form.idBairro.value = "";
		       form.descricaoBairro.value = "";
			   
			   if (form.idBairroArea.options.length > 1){
		       		redirecionarSubmit("atualizarRegistroAtendimentoWizardAction.do?action=exibirAtualizarRegistroAtendimentoDadosLocalOcorrenciaAction&removerColecaoBairroArea=OK&campoFoco=cdBairro");
		       }
		       
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
		}
	
		if (tipoConsulta == 'localidade') {
      		form.idLocalidade.value = codigoRegistro;
	  		form.descricaoLocalidade.value = descricaoRegistro;
	  		form.localidadeNome.style.color = "#000000";
	  		
	  		limpar(6);
		}
		
		if (tipoConsulta == 'imovel') {
      		form.idImovel.value = codigoRegistro;
      
      		redirecionarSubmit("atualizarRegistroAtendimentoWizardAction.do?action=exibirAtualizarRegistroAtendimentoDadosLocalOcorrenciaAction&pesquisarImovel=OK");
    	}

		if (tipoConsulta == 'bairro') {
			form.cdBairro.value = codigoRegistro;
	  		form.idBairro.value = "";
	  		form.descricaoBairro.value = descricaoRegistro;
	  		form.descricaoBairro.style.color = "#000000";
	  		
	  		redirecionarSubmit("atualizarRegistroAtendimentoWizardAction.do?action=exibirAtualizarRegistroAtendimentoDadosLocalOcorrenciaAction&pesquisarBairro=OK");
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
					abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisaBairro=" + "/exibirAtualizarQuadra.do", altura, largura);
				}
			}	
		}
	}
	
	
	function verificarCompatibilidadeDivisaoEsgoto(){
		
		var form = document.forms[0];
		
		if (form.idDivisaoEsgoto.value > 0){
			redirecionarSubmit('atualizarRegistroAtendimentoWizardAction.do?action=exibirAtualizarRegistroAtendimentoDadosLocalOcorrenciaAction&verificarCompatibilidade=OK');
		}
		else{
			habilitarDesabilitarDescricaoLocalOcorrencia();
		}
	}
	
	//Remover Endere�o
	function remover(){
		redirecionarSubmit('atualizarRegistroAtendimentoWizardAction.do?action=exibirAtualizarRegistroAtendimentoDadosLocalOcorrenciaAction&removerEndereco=OK');
	}
	
	function dadosLocalOcorrenciaInformados(){
		
		var retorno = false;
		var form = document.forms[0];
		
		for(indice = 0; indice < form.elements.length; indice++){
			
			if ((form.elements[indice].type == "text" ||  
				 form.elements[indice].type == "textarea") 
				 && form.elements[indice].name != "descricaoLocalOcorrencia" 
				 && form.elements[indice].name != "idUnidadeAtual"
				 && form.elements[indice].name != "descricaoUnidadeAtual"
				 && form.elements[indice].value.length > 0) {
				retorno = true;
				break;
			}
			else if (form.elements[indice].type == "select-one" && form.elements[indice].value > 0 && form.elements[indice].name != "ultimoacesso"){
				retorno = true;
				break;
			}
		}
		
		return retorno;	
	}
	
	function habilitarDesabilitarDescricaoLocalOcorrencia(){
	
		var form = document.forms[0];
		
		if (dadosLocalOcorrenciaInformados()){
			form.descricaoLocalOcorrencia.readOnly = true;
		}
		else{
			form.descricaoLocalOcorrencia.readOnly = false;
		}
	}
	
	function habilitarDesabilitarDadosLocalOcorrencia(campoReferencia){
	
		var form = document.forms[0];
		
		for(indice = 0; indice < form.elements.length; indice++){
			
			if ((form.elements[indice].type == "text" ||  
				 form.elements[indice].type == "textarea") 
				 && form.elements[indice].name != "descricaoLocalOcorrencia"
				 && form.elements[indice].name != "idUnidadeAtual"
				 && form.elements[indice].name != "descricaoUnidadeAtual"
				 && form.elements[indice].style.border == ""
				 && campoReferencia.value.length > 0) {
				form.elements[indice].readOnly = true;
			}
			else if ((form.elements[indice].type == "text" ||  
				 form.elements[indice].type == "textarea") 
				 && form.elements[indice].name != "descricaoLocalOcorrencia"
				 && form.elements[indice].name != "idUnidadeAtual"
				 && form.elements[indice].name != "descricaoUnidadeAtual"
				 && form.elements[indice].style.border == ""
				 && campoReferencia.value.length < 1) {
				
				form.elements[indice].readOnly = false; 
			}
			else if (form.elements[indice].type == "select-one" && campoReferencia.value.length > 0 && form.elements[indice].name != "ultimoacesso"){
				
				form.elements[indice].disabled = true;
			}
			else if (form.elements[indice].type == "select-one" && campoReferencia.value.length < 1){
				
				form.elements[indice].disabled = false;
			}
		}
		
		//Lupas
		if (campoReferencia.value.length > 0){
			return false;
		}
		else{
			return true;
		}
	}
	//[SB0005] - Habilita/Desabilita Dados da Identifica��o do Local de Ocorr�ncia e dados Solicitante
	
	function verificarCamposOnLoad(){
	 var form = document.forms[0];
	 
	 habilitarDesabilitarDescricaoLocalOcorrencia();
	 habilitarDesabilitarDadosLocalOcorrencia(form.descricaoLocalOcorrencia);
	 
	 if(form.descricaoLocalOcorrencia.value == ''){ 
		 if(form.solicitacaoTipoRelativoFaltaAgua.value == 'SIM'){
		     form.idMunicipio.readOnly = false;
		     form.cdBairro.readOnly = false;
		     form.idBairroArea.disabled = false;
		 }else{
			 form.idMunicipio.readOnly = true;
		     form.cdBairro.readOnly = true;
		     form.idBairroArea.disabled = true;
		 }
		 
		 if(form.solicitacaoTipoRelativoAreaEsgoto.value == 'SIM'){
	       form.idDivisaoEsgoto.disabled= false;
	     }else{
		   form.idDivisaoEsgoto.disabled = true;
	    }
	 }
	 
	 
	 if(form.idImovel.value != ''){
	  form.idLocalidade.readOnly = true;
	  document.getElementById("lupaLocalidade").href = '#';
	  document.getElementById("limparLocalidade").href = '#';
	  form.cdSetorComercial.readOnly = true;
	  document.getElementById("lupaSetorComercial").href = '#';
	  document.getElementById("limparSetorComercial").href = '#';
	  form.nnQuadra.readOnly = true;
	  //form.idDivisaoEsgoto.disabled = true;
	  form.idMunicipio.readOnly = true;
	  form.cdBairro.readOnly = true;
	  
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
	   form.idLocalidade.readOnly = false;
	   form.cdSetorComercial.readOnly = false;
	   form.nnQuadra.readOnly = false;
	   form.idPavimentoCalcada.disabled = false;
	   form.idPavimentoRua.disabled = false;
	  }
	 }
	    
	 
	}
	
	function carregarIndicadorLocalOcorrencia(campo){
	var localOcorrencia = campo.options[campo.selectedIndex];
	var form = document.forms[0];
	form.indRuaLocalOcorrencia.value = localOcorrencia.id;
	form.indCalcadaLocalOcorrencia.value = localOcorrencia.nome;
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

	function limparDescricao(campoID,numeroLimpar){
	 if(!campoID.readOnly){
	  if(numeroLimpar != ''){
	   limpar(numeroLimpar);
	  }
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
						// Remover o que j� existe
			      		redirecionarSubmit("atualizarRegistroAtendimentoWizardAction.do?action=exibirAtualizarRegistroAtendimentoDadosLocalOcorrenciaAction&limparContaRevisao=OK");
					}
						
				} else {
					selecionarContaRevisao(abrirPopUpEnviarContaRevisao, especificacao);
				}

			}
			
		}
	}

//-->
</SCRIPT>


</head>

<logic:notPresent name="msgAlert">
	<body leftmargin="5" topmargin="5" onload="enviarContaRevisaoConfirm('${requestScope.msgEnviarContaRevisao}', '${requestScope.abrirPopUpEnviarContaRevisao}', '<bean:write name="AtualizarRegistroAtendimentoActionForm" property="especificacao" scope="session"/>'); setarFoco('${requestScope.nomeCampo}'); verificarCamposOnLoad(); limitTextArea(document.forms[0].descricaoLocalOcorrencia, 200, document.getElementById('utilizadoLocalOcorrencia'), document.getElementById('limiteLocalOcorrencia'));">
</logic:notPresent>

<logic:present name="msgAlert">

	<logic:present name="msgAlert2">
		<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}'); alert('${requestScope.msgAlert}'); alert('${requestScope.msgAlert2}'); enviarContaRevisaoConfirm('${requestScope.msgEnviarContaRevisao}', '${requestScope.abrirPopUpEnviarContaRevisao}', '<bean:write name="AtualizarRegistroAtendimentoActionForm" property="especificacao" scope="session"/>');verificarCamposOnLoad(); limitTextArea(document.forms[0].descricaoLocalOcorrencia, 200, document.getElementById('utilizadoLocalOcorrencia'), document.getElementById('limiteLocalOcorrencia'));">
	</logic:present>

	<logic:notPresent name="msgAlert2">
		<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}'); alert('${requestScope.msgAlert}'); enviarContaRevisaoConfirm('${requestScope.msgEnviarContaRevisao}', '${requestScope.abrirPopUpEnviarContaRevisao}', '<bean:write name="AtualizarRegistroAtendimentoActionForm" property="especificacao" scope="session"/>'); verificarCamposOnLoad(); limitTextArea(document.forms[0].descricaoLocalOcorrencia, 200, document.getElementById('utilizadoLocalOcorrencia'), document.getElementById('limiteLocalOcorrencia'));">
	</logic:notPresent>

</logic:present>

<html:form action="/atualizarRegistroAtendimentoWizardAction"
	method="post">

	<!-- Par�metro respons�vel pela formula��o da tela de op��o -->
	<INPUT TYPE="hidden" name="telaOpcao" value="OK">

	<html:hidden property="imovelObrigatorio" />
	<html:hidden property="pavimentoRuaObrigatorio" />
	<html:hidden property="pavimentoCalcadaObrigatorio" />
	<html:hidden property="tipoSolicitacao"/>

	<html:hidden property="indRuaLocalOcorrencia" />
	<html:hidden property="indCalcadaLocalOcorrencia" />
	<input type="hidden" name="solicitacaoTipoRelativoFaltaAgua" value="${sessionScope.solicitacaoTipoRelativoFaltaAgua}"/>
	<input type="hidden" name="solicitacaoTipoRelativoAreaEsgoto" value="${sessionScope.solicitacaoTipoRelativoAreaEsgoto}"/>

	<jsp:include
		page="/jsp/util/wizard/navegacao_abas_wizard_valida_avancar.jsp?numeroPagina=2" />


	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

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

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Atualizar RA - Registro de Atendimento</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td colspan="3">Para atualizar o RA - Registro de Atendimento, informe
					os dados do local da ocorr�ncia:</td>
				</tr>
				<tr>
					<td HEIGHT="30" WIDTH="100"><strong>Im�vel:</strong></td>
					<td colspan="2">
					
					<%-- <logic:notEmpty name="AtualizarRegistroAtendimentoActionForm" property="idOrdemServico">
						
						<html:text property="idImovel" size="10" maxlength="9" tabindex="1" disabled="true" />&nbsp;
				
						<img src="<bean:message key='caminho.imagens'/>pesquisa.gif"
						width="23" height="21" alt="Pesquisar" border="0">&nbsp;
				
						<html:text property="inscricaoImovel" size="22" readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000" />&nbsp;
				
						<img src="<bean:message key='caminho.imagens'/>limparcampo.gif"
						alt="Apagar" border="0">&nbsp;			
				
						<input type="button" class="bottonRightCol" value="RAs Pendentes"
						tabindex="2" id="botaoConsultarRAPendente" align="right"
						disabled="true">
					
					</logic:notEmpty> 
					
					<logic:empty name="AtualizarRegistroAtendimentoActionForm" property="idOrdemServico"> --%>
						
						<html:text property="idImovel" size="10" maxlength="9"
							tabindex="1"
							onkeypress="validaEnterComMensagem(event, 'atualizarRegistroAtendimentoWizardAction.do?action=exibirAtualizarRegistroAtendimentoDadosLocalOcorrenciaAction&pesquisarImovel=OK', 'idImovel', 'Matr�cula do Im�vel');"
							onkeyup="habilitarDesabilitarDescricaoLocalOcorrencia();document.forms[0].inscricaoImovel.value = '';" />
						<a
							href="javascript:if(habilitarDesabilitarDadosLocalOcorrencia(document.forms[0].descricaoLocalOcorrencia)){abrirPopup('exibirPesquisarImovelAction.do', 490, 800);}">
						<img src="<bean:message key='caminho.imagens'/>pesquisa.gif"
							width="23" height="21" alt="Pesquisar" border="0"></a>

						<logic:present name="corImovel">

							<logic:equal name="corImovel" value="exception">
								<html:text property="inscricaoImovel" size="22" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000" />
							</logic:equal>

							<logic:notEqual name="corImovel" value="exception">
								<html:text property="inscricaoImovel" size="22" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</logic:notEqual>

						</logic:present>

						<logic:notPresent name="corImovel">

							<logic:empty name="AtualizarRegistroAtendimentoActionForm"
								property="idImovel">
								<html:text property="inscricaoImovel" value="" size="22"
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000" />
							</logic:empty>
							<logic:notEmpty name="AtualizarRegistroAtendimentoActionForm"
								property="idImovel">
								<html:text property="inscricaoImovel" size="22" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</logic:notEmpty>


						</logic:notPresent>


						<a
							href="javascript:if(document.forms[0].idImovel.value != '' && habilitarDesabilitarDadosLocalOcorrencia(document.forms[0].descricaoLocalOcorrencia)){limpar(1);}">
						<img src="<bean:message key='caminho.imagens'/>limparcampo.gif"
							alt="Apagar" border="0"></a>

						<input type="button" class="bottonRightCol" value="RAs Pendentes"
							tabindex="2" id="botaoConsultarRAPendente" align="right"
							onclick="consultarRAsPendentes();">
					
					<%-- </logic:empty> --%>
					
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
							<input type="button" class="bottonRightCol" value="Colocar Conta Para Revis&atilde;o" id="botaoAdicionarSelecionarContaParaRevisao" onclick="javascript:selecionarContaRevisao('${requestScope.abrirPopUpEnviarContaRevisao}', '<bean:write name="AtualizarRegistroAtendimentoActionForm" property="especificacao" scope="session"/>');"/>
						</logic:equal>
					</td>
				</tr>
				<tr>
					<td colspan="3" HEIGHT="20"></td>
				</tr>
				<tr>
					<td colspan="3">

					<table width="100%" border="0">
						<tr>
							<td><strong>Endere�o da Ocorr�ncia:</strong></td>
							<td align="right"><logic:present name="colecaoEnderecos">

								<logic:empty name="colecaoEnderecos">
									<input type="button" class="bottonRightCol" value="Adicionar"
										tabindex="3" id="botaoEndereco"
										onclick="javascript:if(habilitarDesabilitarDadosLocalOcorrencia(document.forms[0].descricaoLocalOcorrencia)){abrirPopup('exibirInserirEnderecoAction.do?tipoPesquisaEndereco=registroAtendimento&operacao=3', 570, 700);}">
								</logic:empty>
								<logic:notEmpty name="colecaoEnderecos">
									<input type="button" class="bottonRightCol" value="Adicionar"
										tabindex="3" id="botaoEndereco" disabled>
								</logic:notEmpty>

							</logic:present> <logic:notPresent name="colecaoEnderecos">

								<logic:present name="habilitarAlteracaoEndereco">
									<logic:equal name="habilitarAlteracaoEndereco" value="SIM">
										<input type="button" class="bottonRightCol" value="Adicionar"
											tabindex="3" id="botaoEndereco"
											onclick="javascript:if(habilitarDesabilitarDadosLocalOcorrencia(document.forms[0].descricaoLocalOcorrencia)){abrirPopupComSubmit('atualizarRegistroAtendimentoWizardAction.do?action=exibirAtualizarRegistroAtendimentoDadosLocalOcorrenciaAction&tipoPesquisaEndereco=registroAtendimento&operacao=3', 570, 700, 'Endereco');}">
									</logic:equal>
									<logic:notEqual name="habilitarAlteracaoEndereco" value="SIM">
										<input type="button" class="bottonRightCol" value="Adicionar"
											tabindex="3" id="botaoEndereco" disabled>
									</logic:notEqual>
								</logic:present>

								<logic:notPresent name="habilitarAlteracaoEndereco">
									<input type="button" class="bottonRightCol" value="Adicionar"
										tabindex="3" id="botaoEndereco"
										onclick="javascript:if(habilitarDesabilitarDadosLocalOcorrencia(document.forms[0].descricaoLocalOcorrencia)){abrirPopupComSubmit('atualizarRegistroAtendimentoWizardAction.do?action=exibirAtualizarRegistroAtendimentoDadosLocalOcorrenciaAction&tipoPesquisaEndereco=registroAtendimento&operacao=3', 570, 700, 'Endereco');}">
								</logic:notPresent>

							</logic:notPresent></td>
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
									<td align="center"><strong>Endere�o da Ocorr�ncia</strong></td>
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

									<%String cor = "#cbe5fe";%>

									<logic:iterate name="colecaoEnderecos" id="endereco">

										<%if (cor.equalsIgnoreCase("#cbe5fe")) {
								cor = "#FFFFFF";%>
										<tr bgcolor="#FFFFFF" height="18">
											<%} else {
								cor = "#cbe5fe";%>
										<tr bgcolor="#cbe5fe" height="18">
											<%}%>

											<td width="10%" align="center"><logic:equal
												name="habilitarAlteracaoEndereco" value="SIM">
												<a
													href="javascript:if(confirm('Confirma remo��o?')){remover();}"
													alt="Remover"><img
													src="<bean:message key='caminho.imagens'/>Error.gif"
													width="14" height="14" border="0"></a>
											</logic:equal> <logic:notEqual
												name="habilitarAlteracaoEndereco" value="SIM">
												<logic:present name="enderecoPertenceImovel">
											         <img src="<bean:message key='caminho.imagens'/>Error.gif" width="14" height="14" border="0">
										        </logic:present>
												<logic:notPresent name="enderecoPertenceImovel">
											     <a href="javascript:if(confirm('Confirma remo��o?')){remover();}" alt="Remover"><img src="<bean:message key='caminho.imagens'/>Error.gif" width="14" height="14" border="0"></a>
										        </logic:notPresent>
											</logic:notEqual></td>

											<td><logic:equal name="habilitarAlteracaoEndereco"
												value="SIM">
												<a
													href="javascript:abrirPopup('exibirInserirEnderecoAction.do?exibirEndereco=OK&tipoPesquisaEndereco=registroAtendimento&operacao=3', 570, 700)"><bean:write
													name="endereco" property="enderecoFormatado" /></a>
											</logic:equal> <logic:notEqual
												name="habilitarAlteracaoEndereco" value="SIM">
												<bean:write name="endereco" property="enderecoFormatado" />
											</logic:notEqual></td>
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
					<td><strong>Refer�ncia:</strong></td>
					<td colspan="2"><html:text property="pontoReferencia" size="50"
						maxlength="60" tabindex="4"
						onkeyup="habilitarDesabilitarDescricaoLocalOcorrencia();" /></td>
				</tr>



				<logic:equal name="solicitacaoTipoRelativoFaltaAgua" value="SIM">
					<tr>
						<td><strong>Munic�pio:</strong></td>
						<td colspan="2"><logic:present name="desabilitarMunicipioBairro">

							<html:text property="idMunicipio" size="5" maxlength="4"
								tabindex="5" readonly="true" />

							<img width="23" height="21" border="0"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Munic�pio" />

							<html:text property="descricaoMunicipio" size="45"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />


							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" title="Apagar" />

						</logic:present> <logic:notPresent
							name="desabilitarMunicipioBairro">

							<html:text property="idMunicipio" size="5" maxlength="4"
								tabindex="5"
								onkeypress="validaEnterComMensagem(event, 'atualizarRegistroAtendimentoWizardAction.do?action=exibirAtualizarRegistroAtendimentoDadosLocalOcorrenciaAction&pesquisarMunicipio=OK', 'idMunicipio', 'Munic�pio');"
								onkeyup="habilitarDesabilitarDescricaoLocalOcorrencia();limparDescricao(document.forms[0].idMunicipio,2);" />

							<a
								href="javascript:if(habilitarDesabilitarDadosLocalOcorrencia(document.forms[0].descricaoLocalOcorrencia)){abrirPopup('exibirPesquisarMunicipioAction.do', 250, 495);}">
							<img width="23" height="21" border="0"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Munic�pio" /></a>

							<logic:present name="corMunicipio">

								<logic:equal name="corMunicipio" value="exception">
									<html:text property="descricaoMunicipio" size="45"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:equal>

								<logic:notEqual name="corMunicipio" value="exception">
									<html:text property="descricaoMunicipio" size="45"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEqual>

							</logic:present>

							<logic:notPresent name="corMunicipio">

								<logic:empty name="AtualizarRegistroAtendimentoActionForm"
									property="idMunicipio">
									<html:text property="descricaoMunicipio" size="45" value=""
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:empty>
								<logic:notEmpty name="AtualizarRegistroAtendimentoActionForm"
									property="idMunicipio">
									<html:text property="descricaoMunicipio" size="45"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEmpty>

							</logic:notPresent>
							<a
								href="javascript:if(habilitarDesabilitarDadosLocalOcorrencia(document.forms[0].descricaoLocalOcorrencia)){limpar(3);}">
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" title="Apagar" /> </a>

						</logic:notPresent></td>
					</tr>
				</logic:equal>

				<logic:notEqual name="solicitacaoTipoRelativoFaltaAgua" value="SIM">
					<tr>
						<td><strong>Munic�pio:</strong></td>
						<td colspan="2"><html:text property="idMunicipio" size="5"
							maxlength="4" tabindex="5" readonly="true" /> <img width="23"
							height="21" border="0"
							src="<bean:message key="caminho.imagens"/>pesquisa.gif"
							title="Pesquisar Munic�pio" /> <html:text
							property="descricaoMunicipio" size="45" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000" /> <img
							src="<bean:message key="caminho.imagens"/>limparcampo.gif"
							border="0" title="Apagar" /></td>
					</tr>
				</logic:notEqual>



				<logic:equal name="solicitacaoTipoRelativoFaltaAgua" value="SIM">
					<tr>
						<td><strong>Bairro:</strong></td>
						<td colspan="2"><logic:present name="desabilitarMunicipioBairro">

							<html:text property="cdBairro" size="5" maxlength="4"
								tabindex="6" readonly="true" />

							<img width="23" height="21" border="0"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Bairro" />

							<html:text property="descricaoBairro" size="45" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />

							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" title="Apagar" />

						</logic:present> <logic:notPresent
							name="desabilitarMunicipioBairro">

							<html:text property="cdBairro" size="5" maxlength="4"
								tabindex="6"
								onkeypress="validaEnterDependencia(event, 'atualizarRegistroAtendimentoWizardAction.do?action=exibirAtualizarRegistroAtendimentoDadosLocalOcorrenciaAction&pesquisarBairro=OK', document.forms[0].cdBairro, document.forms[0].idMunicipio.value, 'Munic�pio');"
								onkeyup="habilitarDesabilitarDescricaoLocalOcorrencia();limparDescricao(document.forms[0].cdBairro,10);" />

							<a	href="javascript:abrirPopupDependencia('exibirPesquisarBairroAction.do?idMunicipio='+document.forms[0].idMunicipio.value+'&indicadorUsoTodos=1', document.forms[0].idMunicipio.value, 'Munic�pio', 275, 480);">
							<img width="23" height="21" border="0"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Bairro" /></a>

							<logic:present name="corBairro">

								<logic:equal name="corBairro" value="exception">
									<html:text property="descricaoBairro" size="45" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:equal>

								<logic:notEqual name="corBairro" value="exception">
									<html:text property="descricaoBairro" size="45" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEqual>

							</logic:present>

							<logic:notPresent name="corBairro">

								<logic:empty name="AtualizarRegistroAtendimentoActionForm"
									property="cdBairro">
									<html:text property="descricaoBairro" size="45" value=""
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:empty>
								<logic:notEmpty name="AtualizarRegistroAtendimentoActionForm"
									property="cdBairro">
									<html:text property="descricaoBairro" size="45" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEmpty>

							</logic:notPresent>

							<html:hidden property="idBairro" />
							<a
								href="javascript:if(habilitarDesabilitarDadosLocalOcorrencia(document.forms[0].descricaoLocalOcorrencia)){limpar(4);}">
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" title="Apagar" /> </a>

						</logic:notPresent></td>
					</tr>
				</logic:equal>

				<logic:notEqual name="solicitacaoTipoRelativoFaltaAgua" value="SIM">
					<tr>
						<td><strong>Bairro:</strong></td>
						<td colspan="2"><html:text property="cdBairro" size="5"
							maxlength="4" tabindex="6" readonly="true" /> <img width="23"
							height="21" border="0"
							src="<bean:message key="caminho.imagens"/>pesquisa.gif"
							title="Pesquisar Bairro" /> <html:text
							property="descricaoBairro" size="45" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000" /> <img
							src="<bean:message key="caminho.imagens"/>limparcampo.gif"
							border="0" title="Apagar" /></td>
					</tr>
				</logic:notEqual>



				<logic:equal name="solicitacaoTipoRelativoFaltaAgua" value="SIM">
					<tr>
						<td><strong>�rea do Bairro:</strong></td>
						<td colspan="2"><html:select property="idBairroArea"
							style="width: 200px;" tabindex="7"
							onchange="habilitarDesabilitarDescricaoLocalOcorrencia();">
							<html:option value="">&nbsp;</html:option>
							<logic:present name="colecaoBairroArea">
								<html:options collection="colecaoBairroArea"
									labelProperty="nome" property="id" />
							</logic:present>
						</html:select></td>
					</tr>
				</logic:equal>

				<logic:notEqual name="solicitacaoTipoRelativoFaltaAgua" value="SIM">
					<tr>
						<td><strong>�rea do Bairro:</strong></td>
						<td colspan="2"><html:select property="idBairroArea"
							style="width: 200px;" tabindex="7" disabled="true">
							<html:option value="">&nbsp;</html:option>
							<logic:present name="colecaoBairroArea">
								<html:options collection="colecaoBairroArea"
									labelProperty="nome" property="id" />
							</logic:present>
						</html:select></td>
					</tr>
				</logic:notEqual>


				<tr>
					<td colspan="3" height="20"></td>
				</tr>


				<tr>
					<td><strong>Localidade:</strong></td>
					<td colspan="2"><html:text maxlength="3" property="idLocalidade"
						size="4" tabindex="8"
						onkeypress="validaEnterComMensagem(event, 'atualizarRegistroAtendimentoWizardAction.do?action=exibirAtualizarRegistroAtendimentoDadosLocalOcorrenciaAction&pesquisarLocalidade=OK', 'idLocalidade', 'Localidade');"
						onkeyup="habilitarDesabilitarDescricaoLocalOcorrencia();limparDescricao(document.forms[0].idLocalidade,'',5);" /> <a id="lupaLocalidade"
						href="javascript:if(habilitarDesabilitarDadosLocalOcorrencia(document.forms[0].descricaoLocalOcorrencia)){abrirPopup('exibirPesquisarLocalidadeAction.do?tipo=imovelLocalidade', 400, 800); limpar(5);}">
					<img border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0" /></a>


					<logic:present name="corLocalidade">

						<logic:equal name="corLocalidade" value="exception">
							<html:text property="descricaoLocalidade" size="45"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>

						<logic:notEqual name="corLocalidade" value="exception">
							<html:text property="descricaoLocalidade" size="45"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>

					</logic:present> <logic:notPresent name="corLocalidade">

						<logic:empty name="AtualizarRegistroAtendimentoActionForm"
							property="idLocalidade">
							<html:text property="descricaoLocalidade" size="45" value=""
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="AtualizarRegistroAtendimentoActionForm"
							property="idLocalidade">
							<html:text property="descricaoLocalidade" size="45"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>

					</logic:notPresent> <a id="limparLocalidade"
						href="javascript:if(habilitarDesabilitarDadosLocalOcorrencia(document.forms[0].descricaoLocalOcorrencia)){limpar(8);}">
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>

				<tr>
					<td><strong>Setor Comercial:</strong></td>
					<td colspan="2"><html:text maxlength="3"
						property="cdSetorComercial" size="4" tabindex="9"
						onkeypress="validaEnterDependencia(event, 'atualizarRegistroAtendimentoWizardAction.do?action=exibirAtualizarRegistroAtendimentoDadosLocalOcorrenciaAction&pesquisarSetorComercial=OK', this, document.forms[0].idLocalidade.value, 'Localidade');"
						onkeyup="habilitarDesabilitarDescricaoLocalOcorrencia();limparDescricao(document.forms[0].cdBairro,6);" />
					<a id="lupaSetorComercial"
						href="javascript:if(habilitarDesabilitarDadosLocalOcorrencia(document.forms[0].descricaoLocalOcorrencia)){abrirPopupDependencia('exibirPesquisarSetorComercialAction.do?idLocalidade='+document.forms[0].idLocalidade.value+'&tipo=SetorComercial',document.forms[0].idLocalidade.value,'Localidade', 400, 800);limpar(6);}">
					<img border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0" /></a>


					<logic:present name="corSetorComercial">

						<logic:equal name="corSetorComercial" value="exception">
							<html:text property="descricaoSetorComercial" size="45"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>

						<logic:notEqual name="corSetorComercial" value="exception">
							<html:text property="descricaoSetorComercial" size="45"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>

					</logic:present> <logic:notPresent name="corSetorComercial">

						<logic:empty name="AtualizarRegistroAtendimentoActionForm"
							property="cdSetorComercial">
							<html:text property="descricaoSetorComercial" size="45" value=""
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="AtualizarRegistroAtendimentoActionForm"
							property="cdSetorComercial">
							<html:text property="descricaoSetorComercial" size="45"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>

					</logic:notPresent> <html:hidden property="idSetorComercial" /> <a id="limparSetorComercial"
						href="javascript:if(habilitarDesabilitarDadosLocalOcorrencia(document.forms[0].descricaoLocalOcorrencia)){limpar(9);}">
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
				<tr>
					<td><strong>Quadra:</strong></td>
					<td colspan="2"><html:text maxlength="5" property="nnQuadra"
						size="4" tabindex="10"
						onkeypress="validaEnterDependencia(event, 'atualizarRegistroAtendimentoWizardAction.do?action=exibirAtualizarRegistroAtendimentoDadosLocalOcorrenciaAction&pesquisarQuadra=OK', this, document.forms[0].idSetorComercial.value, 'Setor Comercial');"
						onkeyup="habilitarDesabilitarDescricaoLocalOcorrencia();limparDescricao(document.forms[0].cdBairro,7);" />

					<html:hidden property="idQuadra" /> <logic:present name="msgQuadra"
						scope="request">
						<span style="color:#ff0000" id="msgQuadra"><bean:write
							scope="request" name="msgQuadra" /></span>
					</logic:present></td>
				</tr>


				<logic:equal name="solicitacaoTipoRelativoAreaEsgoto" value="SIM">
					<tr>
						<td><strong>Divis�o de Esgoto:</strong></td>
						<td colspan="2"><html:select property="idDivisaoEsgoto"
							style="width: 200px;" tabindex="11"
							onchange="verificarCompatibilidadeDivisaoEsgoto();">
							<html:option value="">&nbsp;</html:option>
							<logic:present name="colecaoDivisaoEsgoto">
								<html:options collection="colecaoDivisaoEsgoto"
									labelProperty="descricao" property="id" />
							</logic:present>
						</html:select></td>
					</tr>
				</logic:equal>

				<logic:notEqual name="solicitacaoTipoRelativoAreaEsgoto" value="SIM">
					<tr>
						<td><strong>Divis�o de Esgoto:</strong></td>
						<td colspan="2"><html:select property="idDivisaoEsgoto"
							style="width: 200px;" tabindex="11" disabled="true">
							<html:option value="">&nbsp;</html:option>
							<logic:present name="colecaoDivisaoEsgoto">
								<html:options collection="colecaoDivisaoEsgoto"
									labelProperty="descricao" property="id" />
							</logic:present>
						</html:select></td>
					</tr>
				</logic:notEqual>


				<tr>
					<td><strong>Unidade Atual:</strong></td>
					<td><html:text property="idUnidadeAtual" size="10" readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000" />
					&nbsp; <html:text property="descricaoUnidadeAtual" size="40"
						readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000" /></td>
				</tr>


				<tr>
					<td colspan="3" height="20"></td>
				</tr>


				<tr>
					<td><strong>Local da Ocorr�ncia:</strong></td>
					<td colspan="2"><html:select property="idLocalOcorrencia"
						style="width: 200px;" tabindex="13"
						onchange="habilitarDesabilitarDescricaoLocalOcorrencia();carregarIndicadorLocalOcorrencia(this);">
						<html:option value="">&nbsp;</html:option>
						<logic:present name="colecaoLocalOcorrencia">
							<logic:iterate id="localOcorrencia" name="colecaoLocalOcorrencia"
								type="LocalOcorrencia">
								<option value="<%=""+localOcorrencia.getId() %>"
									id="<%=""+localOcorrencia.getIndicadorRua() %>"
									name="<%=""+localOcorrencia.getIndicadorCalcada() %>"><%=""
										+ localOcorrencia.getDescricao()%></option>
							</logic:iterate>
						</logic:present>
					</html:select></td>
				</tr>

				<tr>
					<td><strong>Pavimento da Rua:</strong></td>
					<td colspan="2"><html:select property="idPavimentoRua"
						style="width: 200px;" tabindex="14"
						onchange="habilitarDesabilitarDescricaoLocalOcorrencia();">
						<html:option value="">&nbsp;</html:option>
						<logic:present name="colecaoPavimentoRua">
							<html:options collection="colecaoPavimentoRua"
								labelProperty="descricao" property="id" />
						</logic:present>
					</html:select></td>
				</tr>

				<tr>
					<td><strong>Pavimento da Cal�ada:</strong></td>
					<td colspan="2"><html:select property="idPavimentoCalcada"
						style="width: 200px;" tabindex="15"
						onchange="habilitarDesabilitarDescricaoLocalOcorrencia();">
						<html:option value="">&nbsp;</html:option>
						<logic:present name="colecaoPavimentoCalcada">
							<html:options collection="colecaoPavimentoCalcada"
								labelProperty="descricao" property="id" />
						</logic:present>
					</html:select></td>
				</tr>


				<tr>
					<td colspan="3" height="20"></td>
				</tr>

				<logic:present name="desabilitarDescricaoLocalOcorrencia">
					<tr>
						<td HEIGHT="30"><strong>Descri��o:</strong></td>
						<td><html:textarea property="descricaoLocalOcorrencia" cols="40"
							rows="2" readonly="true" onkeyup="limitTextArea(this, 200, document.getElementById('utilizadoLocalOcorrencia'), document.getElementById('limiteLocalOcorrencia'));"/><br>
							<strong><span id="utilizadoLocalOcorrencia">0</span>/<span id="limiteLocalOcorrencia">200</span></strong></td>
					</tr>
				</logic:present>

				<logic:notPresent name="desabilitarDescricaoLocalOcorrencia">
					<tr>
						<td HEIGHT="30"><strong>Descri��o:</strong></td>
						<td><logic:notEmpty name="AtualizarRegistroAtendimentoActionForm"
							property="idImovel">
							<logic:notEmpty name="AtualizarRegistroAtendimentoActionForm"
								property="descricaoLocalOcorrencia">
								<html:textarea property="descricaoLocalOcorrencia" cols="40"
									rows="2" onkeyup="limitTextArea(this, 200, document.getElementById('utilizadoLocalOcorrencia'), document.getElementById('limiteLocalOcorrencia'));"/><br>
								<strong><span id="utilizadoLocalOcorrencia">0</span>/<span id="limiteLocalOcorrencia">200</span></strong>
							</logic:notEmpty>
							<logic:empty name="AtualizarRegistroAtendimentoActionForm"
								property="descricaoLocalOcorrencia">
								<html:textarea property="descricaoLocalOcorrencia" cols="40"
									rows="2" readonly="true"
									onkeyup="habilitarDesabilitarDadosLocalOcorrencia(this); limitTextArea(this, 200, document.getElementById('utilizadoLocalOcorrencia'), document.getElementById('limiteLocalOcorrencia'));" /><br>
								<strong><span id="utilizadoLocalOcorrencia">0</span>/<span id="limiteLocalOcorrencia">200</span></strong>
							</logic:empty>
						</logic:notEmpty> <logic:empty
							name="AtualizarRegistroAtendimentoActionForm" property="idImovel">
							<html:textarea property="descricaoLocalOcorrencia" cols="40"
								rows="2"
								onkeyup="habilitarDesabilitarDadosLocalOcorrencia(this); limitTextArea(this, 200, document.getElementById('utilizadoLocalOcorrencia'), document.getElementById('limiteLocalOcorrencia'));" /><br>
							<strong><span id="utilizadoLocalOcorrencia">0</span>/<span id="limiteLocalOcorrencia">200</span></strong>
						</logic:empty></td>
					</tr>
				</logic:notPresent>

				<tr>
					<td colspan="3" height="10"></td>
				</tr>
				<tr>
					<td colspan="3">
					<div align="right"><jsp:include
						page="/jsp/util/wizard/navegacao_botoes_wizard_valida_avancar_ra.jsp?numeroPagina=2" />
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

</body>
<!-- registro_atendimento_atualizar_dados_local_ocorrencia.jsp -->
</html:html>
