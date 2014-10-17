<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gcom.util.ConstantesSistema"%>
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="ImovelOutrosCriteriosActionForm" dynamicJavascript="false" />

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>cadastro/imovel/imovel_outros_criterios.js"></script>

<!-- Validações de JavaScript para o Cliente DESO -->
<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_ADA%>" scope="session">
<script>
<!-- Begin 

    var bCancel = false; 

    function validateImovelOutrosCriteriosActionForm(form) {                                                                   
        if (bCancel) 
       return true; 
        else 
       return validateCaracterEspecial(form) && validateLong(form)
       && testarCampoValorZero(document.ImovelOutrosCriteriosActionForm.localidadeOrigemID, 'Localidade Inicial')
       && testarCampoValorZero(document.ImovelOutrosCriteriosActionForm.setorComercialOrigemCD, 'Setor Comercial Inicial')
       && testarCampoValorZero(document.ImovelOutrosCriteriosActionForm.quadraOrigemNM, 'Quadra Inicial')
       && testarCampoValorZero(document.ImovelOutrosCriteriosActionForm.loteOrigem, 'Lote Inicial')
       && testarCampoValorZero(document.ImovelOutrosCriteriosActionForm.localidadeDestinoID, 'Localidade Final')
       && testarCampoValorZero(document.ImovelOutrosCriteriosActionForm.setorComercialDestinoCD, 'Setor Comercial Final')
       && testarCampoValorZero(document.ImovelOutrosCriteriosActionForm.quadraDestinoNM, 'Quadra Final')
       && testarCampoValorZero(document.ImovelOutrosCriteriosActionForm.loteDestino, 'Lote Final')
       && testarCampoValorZero(document.ImovelOutrosCriteriosActionForm.idMunicipio, 'Município')
       && testarCampoValorZero(document.ImovelOutrosCriteriosActionForm.idBairro, 'Bairro')
       && testarCampoValorZero(document.ImovelOutrosCriteriosActionForm.idLogradouro, 'Logradouro')
       && testarCampoValorZero(document.ImovelOutrosCriteriosActionForm.CEP, 'CEP')
	   && validarLocalidadeDiferentes()
	   && validarSetoresComerciaisDiferentes()
	   && validarQuadrasDiferentes()
	   && validarLotesDiferentes()
	   && verificarSetoresComerciaisMenores()
	   && verificarLocalidadeMenores()    
	   && verificarQuadraMenores()
	   && verificarLoteMenores()
	   && validarRota(form);    
   } 

    function caracteresespeciais () { 
	     this.aa = new Array("localidadeOrigemID", "Localidade Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     this.ab = new Array("setorComercialOrigemCD", "Setor Comercial  Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     this.ac = new Array("quadraOrigemNM", "Quadra  Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     this.ad = new Array("loteOrigem", "Lote  Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     this.ae = new Array("localidadeDestinoID", "Localidade Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     this.af = new Array("setorComercialDestinoCD", "Setor Comercial Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     this.ag = new Array("quadraDestinoNM", "Quadra Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     this.ah = new Array("loteDestino", "Lote Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     this.ai = new Array("idMunicipio", "Município deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     this.aj = new Array("idBairro", "Bairro deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
		 this.ak = new Array("CEP", "CEP deve somente conter números positivos.", new Function ("varName", " return this[varName];"));	 
		 this.al = new Array("idLogradouro", "Logradouro deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
		 this.am = new Array("cdRotaInicial", "Rota Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));	 
		 this.an = new Array("cdRotaFinal", "Rota Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));	 	 
		 this.ao = new Array("sequencialRotaInicial", "Seq. da Rota Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));	 
		 this.ap = new Array("sequencialRotaFinal", "Seq. da Rota Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    } 

    function IntegerValidations () { 
	     this.as = new Array("localidadeOrigemID", "Localidade Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     this.at = new Array("setorComercialOrigemCD", "Setor Comercial Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     this.au = new Array("quadraOrigemNM", "Quadra Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     this.av = new Array("loteOrigem", "Lote Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	
	     this.ax = new Array("localidadeDestinoID", "Localidade Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     this.ay = new Array("setorComercialDestinoCD", "Setor Comercial Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     this.az = new Array("quadraDestinoNM", "Quadra Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     this.aw = new Array("loteDestino", "Lote Final deve somente conter números p   ositivos.", new Function ("varName", " return this[varName];"));
	
		 this.ba = new Array("idMunicipio", "Município deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     this.bb = new Array("idBairro", "Bairro deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
		 this.bc = new Array("CEP", "CEP deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
		 this.bd = new Array("idLogradouro", "Logradouro deve somente conter números positivos.", new Function ("varName", " return this[varName];"));	 
		 this.be = new Array("cdRotaInicial", "Rota Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
		 this.bf = new Array("cdRotaFinal", "Rota Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));	 
		 this.bg = new Array("sequencialRotaInicial", "Seq. da Rota Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
		 this.bh = new Array("sequencialRotaFinal", "Seq. da Rota Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));	 
    } 
    

	 function limparForm(){
	 	
	 	var form = document.forms[0];

	    form.indicadorOrdenacao[0].checked = true;
	    form.indicadorOrdenacao[1].checked = false;
	 	
	    	    
	 }
 
	 function validarBairro(){
	 	
	 	var form = document.forms[0];
	 	
	 	if (form.idMunicipio.value.length < 1) {
			form.idBairro.value = "";
	 		alert('Informe Município');
	 	}
	 }
 
	 //Sávio Luiz
	 function replicarRota(){
	    var form = document.forms[0];
		form.cdRotaFinal.value = form.cdRotaInicial.value;
	  }
	  
	 //Sávio Luiz
	 function replicarSequencialRota(){
	    var form = document.forms[0];
		form.sequencialRotaFinal.value = form.sequencialRotaInicial.value;
	  }
  
  	function validarRota(form){

		var retorno = true;
		
		var sequencialRotaInicial = form.sequencialRotaInicial;
		var rotaInicial = form.cdRotaInicial;
		var rotaFinal = form.cdRotaFinal;
		var sequencialRotaFinal = form.sequencialRotaFinal;
		var localidadeOrigemID = form.localidadeOrigemID;
		
		if(rotaInicial.value.length > 0){
		
			if(localidadeOrigemID.value.length < 1){
			  alert("Informe Localidade Inicial");
			  localidadeOrigemID.focus();
			  return false;
			}
			
			if (rotaFinal.value.length < 1){
				alert("Informe Rota Final");
				rotaFinal.focus();
				return false;
			}
			if (sequencialRotaInicial.value.length > 0 && sequencialRotaFinal.value.length < 1){
				alert("Informe Seq. da Rota Final");
				sequencialRotaFinal.focus();
				return false;
			}
			if (sequencialRotaInicial.value.length < 1 && sequencialRotaFinal.value.length > 0){
				alert("Informe Seq. da Rota Inicial");
				sequencialRotaInicial.focus();
				return false;
			}
		}else{
		    if (rotaFinal.value.length > 0){
				alert("Informe Rota Inicial");
				rotaFinal.focus();
				return false;
			}
			if(sequencialRotaInicial.value.length > 0 || sequencialRotaFinal.value.length > 0){
			    alert("Informe Rota Inicial");
				rotaFinal.focus();
				return false;
			}
			if (sequencialRotaInicial.value.length > 0 && sequencialRotaFinal.value.length < 1){
				alert("Informe Seq. da Rota Final");
				sequencialRotaInicial.focus();
				return false;
			}
			if (sequencialRotaInicial.value.length < 1 && sequencialRotaFinal.value.length > 0){
				alert("Informe Seq. da Rota Inicial");
				sequencialRotaFinal.focus();
				return false;
			}
		}
	
		return retorno;
	}

	function limparBorrachaOrigem(tipo){
		var form = document.forms[0];
	
			
		switch(tipo){
			case 1: //De localidara pra baixo
			    if(!form.localidadeOrigemID.disabled){
				form.localidadeOrigemID.value = "";
				form.nomeLocalidadeOrigem.value = "";
				form.localidadeDestinoID.value = "";
				form.nomeLocalidadeDestino.value = "";
				
				desabilitaIntervaloDiferente(1);
				}else{
				break;
				}
				
			case 2: //De setor para baixo
			    if(!form.setorComercialOrigemCD.disabled){
			     form.setorComercialOrigemCD.value = "";
			     form.setorComercialOrigemID.value = "";
			     form.nomeSetorComercialOrigem.value = "";
		   
			     form.setorComercialDestinoCD.value = "";
			     form.setorComercialDestinoID.value = "";		   
			     form.nomeSetorComercialDestino.value = "";
			     desabilitaIntervaloDiferente(2);
			    }else{
			     break;
			    }
			case 3://De quadra pra baixo
			   if(!form.quadraOrigemNM.disabled){
			   form.quadraOrigemNM.value = "";
			   form.quadraOrigemID.value = "";
			   form.loteOrigem.value = "";
			   form.quadraDestinoNM.value = "";
			   form.quadraDestinoID.value = "";
			   form.loteDestino.value = "";
			   form.sequencialRotaInicial.value = "";
			   form.sequencialRotaFinal.value = "";
			   desabilitaIntervaloDiferente(3);
			   limparPesquisaRotaInicial();
			   limparMsgQuadraInexistente();
			   }
			}

	}

	/*

	Limpa campos da faixa final(localidade final, setor comercial final,
	quadra final, lote final) conforme o parametro passado
	*/
	function limparDestino(tipo){
		var form = document.ImovelOutrosCriteriosActionForm;
		
		switch(tipo){
			case 1: //De localidade pra baixo
				if(form.localidadeOrigemID.value != form.localidadeDestinoID.value){
					form.nomeLocalidadeDestino.value = "";					
					form.setorComercialDestinoCD.value = "";
					form.setorComercialDestinoID.value = ""; 
			    }
			case 2: //De setor para baixo		 
				if(form.setorComercialOrigemCD.value != form.setorComercialDestinoCD.value){  
				   form.nomeSetorComercialDestino.value = "";
				   form.quadraDestinoNM.value = "";
				   form.quadraDestinoID.value = "";		   
				}   		   		   
			case 3://De quadra pra baixo
			   if(form.quadraOrigemNM.value != form.quadraDestinoNM.value){
		           form.loteDestino.value = "";
		           form.sequencialRotaFinal.value = "";
		           limparPesquisaRotaFinal();
			   }    
		}
	
	}

	/*
	Limpa campos da faixa Inicial(localidade inicial, setor comercial inicial,
	quadra inicial, lote inicial) conforme o parametro passado
	*/
	
	function limparOrigem(tipo){
		var form = document.ImovelOutrosCriteriosActionForm;
	
			
		switch(tipo){
			case 1: //De localidade pra baixo
	
				form.nomeLocalidadeOrigem.value = "";
				form.localidadeDestinoID.value = "";
				form.nomeLocalidadeDestino.value = "";
				form.setorComercialOrigemCD.value = "";
			    form.setorComercialOrigemID.value = "";
				habilitaSQlS();
				
			case 2: //De setor para baixo
	
			   form.nomeSetorComercialOrigem.value = "";
			   form.setorComercialDestinoCD.value = "";
			   form.setorComercialDestinoID.value = "";		   
			   form.nomeSetorComercialDestino.value = "";
			   form.quadraOrigemNM.value = "";
			   form.quadraOrigemID.value = "";
			   
			case 3://De quadra pra baixo
	
			   form.quadraDestinoNM.value = "";
			   form.quadraDestinoID.value = "";
	        
			   form.loteDestino.value = "";
			   form.loteOrigem.value = "";
			   form.sequencialRotaInicial.value = "";
			   form.sequencialRotaFinal.value = "";
			   limparPesquisaRotaInicial();			   
			   limparMsgQuadraInexistente();		   
		}
	}

	/*
	Limpa os campos da faixa final pelo click na borracha(conforme parametro passado)
	*/
	function limparBorrachaDestino(tipo){
		var form = document.ImovelOutrosCriteriosActionForm;
	
		switch(tipo){
			case 1: //De localidade pra baixo
			     form.localidadeDestinoID.value = "";
				 form.nomeLocalidadeDestino.value = "";					
				 form.setorComercialDestinoCD.value = "";
				
			case 2: //De setor para baixo		   
			   form.setorComercialDestinoID.value = ""; 
			   form.nomeSetorComercialDestino.value = "";		   
	   		   form.setorComercialDestinoCD.value = "";
			   
			case 3://De quadra pra baixo
			   form.quadraDestinoNM.value = "";
			   form.quadraDestinoID.value = "";
	
	           form.loteDestino.value = "";
	           form.sequencialRotaFinal.value = "";
	           limparPesquisaRotaFinal();
		}
	}
	
	function desabilitaIntervaloDiferente(tipo){
		var form = document.forms[0];

		switch(tipo){
		case 1: //De localidade 
		    if(form.localidadeOrigemID.value != form.localidadeDestinoID.value){
		        limparBorrachaOrigem(2);
		        form.setorComercialOrigemCD.disabled = true;
			 	form.quadraOrigemNM.disabled = true;
		     	form.loteOrigem.disabled = true;

			 	form.setorComercialDestinoCD.disabled = true;
			 	form.quadraDestinoNM.disabled = true;
		     	form.loteDestino.disabled = true;

             	form.loteOrigem.value = "";
             	form.sequencialRotaInicial.value = "";
             	limparPesquisaRotaInicial();
			 
			  }else{
			 	form.setorComercialOrigemCD.disabled = false;
			 	form.quadraOrigemNM.disabled = false;
		     	form.loteOrigem.disabled = false;

			 	form.setorComercialDestinoCD.disabled = false;
			 	form.quadraDestinoNM.disabled = false;
		     	form.loteDestino.disabled = false;
			  }
			
			break;					
			   		   
		case 2: //De setor Comercial		   
		
		    if(form.setorComercialOrigemCD.value != form.setorComercialDestinoCD.value){
			  	form.quadraOrigemNM.disabled = true;
		     	form.loteOrigem.disabled = true;
			 	form.quadraDestinoNM.disabled = true;
		     	form.loteDestino.disabled = true;
             
             	form.loteOrigem.value = "";
             	form.sequencialRotaInicial.value = "";
             	form.quadraOrigemNM.value = "";	
             	form.quadraOrigemID.value = "";
             	limparPesquisaQuadraInicial();
             	form.loteDestino.value = "";
             	form.quadraDestinoNM.value = "";	
             	form.quadraDestinoID.value = "";
             	limparPesquisaRotaInicial();
             	limparPesquisaQuadraFinal();
             	
  			  }else{
			 	form.quadraOrigemNM.disabled = false;
		     	form.loteOrigem.disabled = false;
			 	form.quadraDestinoNM.disabled = false;
		     	form.loteDestino.disabled = false;
			  }
			
			break;
           
		case 3://De quadra 
		     if(form.quadraOrigemNM.value != form.quadraDestinoNM.value){
			  	form.loteOrigem.disabled = true;
			 	form.loteDestino.disabled = true;
             	
             	form.loteOrigem.value = "";
             	form.loteDestino.value = "";
             	form.sequencialRotaInicial.value = "";
             	form.sequencialRotaFinal.value = "";
             	limparPesquisaRotaInicial();			 
			  }else{
			 	form.loteOrigem.disabled = false;
			 	form.loteDestino.disabled = false;
			  }
			
			break;
		}
	}


	function abrirPopupRota(idLocalidade, codigoSetorComercial, campoRetorno){
		if(campoRetorno == 'cdRotaInicial'){
			abrirPopupDependencia('exibirPesquisarRotaAction.do?idLocalidade='+idLocalidade+'&codigoSetorComercial='+codigoSetorComercial+'&restringirPesquisa=true'+'&destinoCampo='+campoRetorno,codigoSetorComercial,'Setor Comercial Inicial', 400, 800);
		}else{
			abrirPopupDependencia('exibirPesquisarRotaAction.do?idLocalidade='+idLocalidade+'&codigoSetorComercial='+codigoSetorComercial+'&restringirPesquisa=true'+'&destinoCampo='+campoRetorno,codigoSetorComercial,'Setor Comercial Final', 400, 800);
		}
	}

	function limparPesquisaRotaInicial() {
	    var form = document.forms[0];

	      form.cdRotaInicial.value = "";
	      form.nomeRotaInicial.value = "";
	      limparPesquisaRotaFinal();
	}

	function limparPesquisaRotaFinal() {
	    var form = document.forms[0];

	      form.cdRotaFinal.value = "";
	      form.nomeRotaFinal.value = "";
	}

	function limparPesquisaDescricaoRotaInicial() {
	    var form = document.forms[0];
	      form.nomeRotaInicial.value = "";
	      form.cdRotaFinal.value = "";
	      form.nomeRotaFinal.value = "";
	}

	function limparPesquisaDescricaoRotaFinal() {
	    var form = document.forms[0];
	      form.nomeRotaFinal.value = "";
	}

	function recuperarDadosQuatroParametros(idRegistro, descricaoRegistro, codigoRegistro, tipoConsulta) {

		var form = document.forms[0];

		if (tipoConsulta == 'rota') {
			if(codigoRegistro == 'cdRotaInicial'){
				limparPesquisaRotaInicial()
				form.cdRotaInicial.value = idRegistro;
				form.nomeRotaInicial.value = descricaoRegistro;
				form.nomeRotaInicial.style.color = "#000000";
				limparPesquisaRotaFinal()
				form.cdRotaFinal.value = idRegistro;
				form.nomeRotaFinal.value = descricaoRegistro;
				form.nomeRotaFinal.style.color = "#000000";
			}else{
				limparPesquisaRotaFinal()
				form.cdRotaFinal.value = idRegistro;
				form.nomeRotaFinal.value = descricaoRegistro;
				form.nomeRotaFinal.style.color = "#000000";
			}
		}
	}
	
-->
</script>
</logic:equal>

<!-- Validações de JavaScript para o Cliente DESO -->
<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
<script>
<!-- Begin 

    var bCancel = false; 

    function validateImovelOutrosCriteriosActionForm(form) {                                                                   
        if (bCancel) 
       return true; 
        else 
       return validateCaracterEspecial(form) && validateLong(form)
       && testarCampoValorZero(document.ImovelOutrosCriteriosActionForm.localidadeOrigemID, 'Localidade Inicial')
       && testarCampoValorZero(document.ImovelOutrosCriteriosActionForm.setorComercialOrigemCD, 'Setor Comercial Inicial')
       && testarCampoValorZero(document.ImovelOutrosCriteriosActionForm.quadraOrigemNM, 'Quadra Inicial')
       && testarCampoValorZero(document.ImovelOutrosCriteriosActionForm.loteOrigem, 'Lote Inicial')
       && testarCampoValorZero(document.ImovelOutrosCriteriosActionForm.subloteInicial, 'Sublote Inicial')
       && testarCampoValorZero(document.ImovelOutrosCriteriosActionForm.localidadeDestinoID, 'Localidade Final')
       && testarCampoValorZero(document.ImovelOutrosCriteriosActionForm.setorComercialDestinoCD, 'Setor Comercial Final')
       && testarCampoValorZero(document.ImovelOutrosCriteriosActionForm.quadraDestinoNM, 'Quadra Final')
       && testarCampoValorZero(document.ImovelOutrosCriteriosActionForm.loteDestino, 'Lote Final')
       && testarCampoValorZero(document.ImovelOutrosCriteriosActionForm.subloteFinal, 'Sublote Final')
       && testarCampoValorZero(document.ImovelOutrosCriteriosActionForm.idMunicipio, 'Município')
       && testarCampoValorZero(document.ImovelOutrosCriteriosActionForm.idBairro, 'Bairro')
       && testarCampoValorZero(document.ImovelOutrosCriteriosActionForm.idLogradouro, 'Logradouro')
       && testarCampoValorZero(document.ImovelOutrosCriteriosActionForm.CEP, 'CEP')
	   && validarLocalidadeDiferentes()
	   && validarSetoresComerciaisDiferentes()
	   && validarQuadrasDiferentes()
	   && validarLotesDiferentes()
	   && verificarSetoresComerciaisMenores()
	   && verificarLocalidadeMenores()    
	   && verificarQuadraMenores()
	   && verificarLoteMenores()
	   && validarSublotesDiferentes()
	   && validarRota(form);    
   	} 

    function caracteresespeciais () { 
	     this.aa = new Array("localidadeOrigemID", "Localidade Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     this.ab = new Array("setorComercialOrigemCD", "Setor Comercial  Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     this.ac = new Array("quadraOrigemNM", "Quadra  Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     this.ad = new Array("loteOrigem", "Lote  Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     this.ae = new Array("localidadeDestinoID", "Localidade Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     this.af = new Array("setorComercialDestinoCD", "Setor Comercial Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     this.ag = new Array("quadraDestinoNM", "Quadra Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     this.ah = new Array("loteDestino", "Lote Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     this.ai = new Array("idMunicipio", "Município deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     this.aj = new Array("idBairro", "Bairro deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
		 this.ak = new Array("CEP", "CEP deve somente conter números positivos.", new Function ("varName", " return this[varName];"));	 
		 this.al = new Array("idLogradouro", "Logradouro deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
		 this.am = new Array("cdRotaInicial", "Rota Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));	 
		 this.an = new Array("cdRotaFinal", "Rota Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));	 	 
		 this.ao = new Array("segmentoInicial", "Segmento Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));	 
		 this.ap = new Array("segmentoFinal", "Segmento Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
		 this.aq = new Array("subloteInicial", "Sublote Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
		 this.ar = new Array("subloteFinal", "Sublote Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));	 	 	 
    } 

    function IntegerValidations () { 
	     this.as = new Array("localidadeOrigemID", "Localidade Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     this.at = new Array("setorComercialOrigemCD", "Setor Comercial Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     this.au = new Array("quadraOrigemNM", "Quadra Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     this.av = new Array("loteOrigem", "Lote Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	
	     this.ax = new Array("localidadeDestinoID", "Localidade Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     this.ay = new Array("setorComercialDestinoCD", "Setor Comercial Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     this.az = new Array("quadraDestinoNM", "Quadra Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     this.aw = new Array("loteDestino", "Lote Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	
		 this.ba = new Array("idMunicipio", "Município deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     this.bb = new Array("idBairro", "Bairro deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
		 this.bc = new Array("CEP", "CEP deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
		 this.bd = new Array("idLogradouro", "Logradouro deve somente conter números positivos.", new Function ("varName", " return this[varName];"));	 
		 this.be = new Array("cdRotaInicial", "Rota Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
		 this.bf = new Array("cdRotaFinal", "Rota Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));	 
		 this.bg = new Array("segmentoInicial", "SegmentoInicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
		 this.bh = new Array("segmentoFinal", "Segmento Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));	 
		 this.bi = new Array("subloteInicial", "Sublote Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
		 this.bj = new Array("subloteFinal", "Sublote Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));	 
    } 
    

	 function limparForm(){


		 	
	 	var form = document.forms[0];
	    form.indicadorOrdenacao[0].checked = true;
	    form.indicadorOrdenacao[1].checked = false;
	 }
 
 	function validarBairro(){
 	
	 	var form = document.forms[0];
	 	
	 	if (form.idMunicipio.value.length < 1) {
			form.idBairro.value = "";
	 		alert('Informe Município');
	 	}
 	}

 	//Sávio Luiz
 	function replicarRota(){
   		var form = document.forms[0];
		form.cdRotaFinal.value = form.cdRotaInicial.value;
		form.nomeRotaFinal.value = form.nomeRotaInicial.value;
  	}

  	function validarRota(form){

		var retorno = true;
		
		var segmentoInicial = form.segmentoInicial;
		var rotaInicial = form.cdRotaInicial;
		var rotaFinal = form.cdRotaFinal;
		var segmentoFinal = form.segmentoFinal;
		var localidadeOrigemID = form.localidadeOrigemID;
		
		if(rotaInicial.value.length > 0){
	
			if(localidadeOrigemID.value.length < 1){
			  alert("Informe Localidade Inicial");
			  localidadeOrigemID.focus();
			  return false;
			}
	        	
			if (rotaFinal.value.length < 1){
				alert("Informe Rota Final");
				rotaFinal.focus();
				return false;
			}
			if (segmentoInicial.value.length > 0 && segmentoFinal.value.length < 1){
				alert("Informe Segmento Final");
				segmentoFinal.focus();
				return false;
			}
			if (segmentoInicial.value.length < 1 && segmentoFinal.value.length > 0){
				alert("Informe Segmento Inicial");
				segmentoInicial.focus();
				return false;
			}
		}else{
		    if (rotaFinal.value.length > 0){
				alert("Informe Rota Inicial");
				rotaFinal.focus();
				return false;
			}
			if(segmentoInicial.value.length > 0 || segmentoFinal.value.length > 0){
			    alert("Informe Rota Inicial");
				rotaFinal.focus();
				return false;
			}
			if (segmentoInicial.value.length > 0 && segmentoFinal.value.length < 1){
				alert("Informe Segmento Final");
				segmentoInicial.focus();
				return false;
	      	}
			if (segmentoInicial.value.length < 1 && segmentoFinal.value.length > 0){
				alert("Informe Segmento Inicial");
				segmentoFinal.focus();
				return false;
		}
		}
	    
		return retorno;
	} 

  	function replicarSegmento(){
	    var form = document.forms[0];
		form.segmentoFinal.value = form.segmentoInicial.value;
  	} 

  	function limparBorrachaOrigem(tipo){
		var form = document.forms[0];
	
			
		switch(tipo){
			case 1: //De localidara pra baixo
			    if(!form.localidadeOrigemID.disabled){
				form.localidadeOrigemID.value = "";
				form.nomeLocalidadeOrigem.value = "";
				form.localidadeDestinoID.value = "";
				form.nomeLocalidadeDestino.value = "";
	
				desabilitaIntervaloDiferente(1);
				}else{
				break;
				}
				
			case 2: //De setor para baixo
			    if(!form.setorComercialOrigemCD.disabled){
			     form.setorComercialOrigemCD.value = "";
			     form.setorComercialOrigemID.value = "";
			     form.nomeSetorComercialOrigem.value = "";
		   
			     form.setorComercialDestinoCD.value = "";
			     form.setorComercialDestinoID.value = "";		   
			     form.nomeSetorComercialDestino.value = "";
			     desabilitaIntervaloDiferente(2);
			    }else{
			     break;
			    }
			case 3://De quadra pra baixo
			   if(!form.quadraOrigemNM.disabled){
			   form.quadraOrigemNM.value = "";
			   form.quadraOrigemID.value = "";
	
			   form.loteOrigem.value = "";
			   form.subloteInicial.value = "";
	
			   form.quadraDestinoNM.value = "";
			   form.quadraDestinoID.value = "";
	        
			   form.loteDestino.value = "";
			   form.subloteFinal.value = "";
			   form.subloteInicial.value = "";
			   form.segmentoInicial.value = "";
			   form.subloteFinal.value = "";
			   form.segmentoFinal.value = "";
			   limparPesquisaRotaInicial();
			   desabilitaIntervaloDiferente(3);
	           limparMsgQuadraInexistente();
			   }
			}

	}

	/*

	Limpa campos da faixa final(localidade final, setor comercial final,
	quadra final, lote final) conforme o parametro passado
	*/
	function limparDestino(tipo){
		var form = document.ImovelOutrosCriteriosActionForm;
		
		switch(tipo){
			case 1: //De localidade pra baixo
				if(form.localidadeOrigemID.value != form.localidadeDestinoID.value){
					form.nomeLocalidadeDestino.value = "";					
					form.setorComercialDestinoCD.value = "";
					form.setorComercialDestinoID.value = ""; 
			    }
			case 2: //De setor para baixo		 
				if(form.setorComercialOrigemCD.value != form.setorComercialDestinoCD.value){  
				   form.nomeSetorComercialDestino.value = "";
				   form.quadraDestinoNM.value = "";
				   form.quadraDestinoID.value = "";		   
				}   		   		   
			case 3://De quadra pra baixo
			   if(form.quadraOrigemNM.value != form.quadraDestinoNM.value){
		           form.loteDestino.value = "";
		           form.subloteFinal.value = "";
		           form.segmentoFinal.value = "";
		           limparPesquisaRotaFinal();
			   }    
		}
	
	}

	/*
	Limpa campos da faixa Inicial(localidade inicial, setor comercial inicial,
	quadra inicial, lote inicial) conforme o parametro passado
	*/
	
	function limparOrigem(tipo){
		var form = document.ImovelOutrosCriteriosActionForm;
	
			
		switch(tipo){
			case 1: //De localidade pra baixo
	
				form.nomeLocalidadeOrigem.value = "";
				form.localidadeDestinoID.value = "";
				form.nomeLocalidadeDestino.value = "";
				form.setorComercialOrigemCD.value = "";
			    form.setorComercialOrigemID.value = "";
				habilitaSQlS();
				
			case 2: //De setor para baixo
	
			   form.nomeSetorComercialOrigem.value = "";
			   form.setorComercialDestinoCD.value = "";
			   form.setorComercialDestinoID.value = "";		   
			   form.nomeSetorComercialDestino.value = "";
			   form.quadraOrigemNM.value = "";
			   form.quadraOrigemID.value = "";
			   
			case 3://De quadra pra baixo
	
			   form.quadraDestinoNM.value = "";
			   form.quadraDestinoID.value = "";
	        
			   form.loteDestino.value = "";
			   form.loteOrigem.value = "";
			   form.subloteInicial.value = "";
			   form.subloteFinal.value = "";
			   form.segmentoInicial.value = "";
			   form.segmentoFinal.value = "";
			   limparPesquisaRotaInicial();			   
			   limparMsgQuadraInexistente();		   
		}
	}
	

	/*
	Limpa os campos da faixa final pelo click na borracha(conforme parametro passado)
	*/
	function limparBorrachaDestino(tipo){
		var form = document.ImovelOutrosCriteriosActionForm;
	
		switch(tipo){
			case 1: //De localidade pra baixo
			     form.localidadeDestinoID.value = "";
				 form.nomeLocalidadeDestino.value = "";					
				 form.setorComercialDestinoCD.value = "";
				
			case 2: //De setor para baixo		   
			   form.setorComercialDestinoID.value = ""; 
			   form.nomeSetorComercialDestino.value = "";		   
	   		   form.setorComercialDestinoCD.value = "";
			   
			case 3://De quadra pra baixo
			   form.quadraDestinoNM.value = "";
			   form.quadraDestinoID.value = "";
	
	           form.loteDestino.value = "";
	           form.segmentoFinal.value = "";
	           form.subloteFinal.value = "";
	           limparPesquisaRotaFinal();
		}
	}
	
	function desabilitaIntervaloDiferente(tipo){
	var form = document.forms[0];
	
	switch(tipo){
		case 1: //De localidade 
		    if(form.localidadeOrigemID.value != form.localidadeDestinoID.value){
		        limparBorrachaOrigem(2);
		        form.setorComercialOrigemCD.disabled = true;
			 	form.quadraOrigemNM.disabled = true;
		     	form.loteOrigem.disabled = true;

			 	form.setorComercialDestinoCD.disabled = true;
			 	form.quadraDestinoNM.disabled = true;
		     	form.loteDestino.disabled = true;

             	form.loteOrigem.value = "";
             	form.subloteInicial.value = "";
             	form.segmentoInicial.value = "";
             	limparPesquisaRotaInicial();
			 
			  }else{
			 	form.setorComercialOrigemCD.disabled = false;
			 	form.quadraOrigemNM.disabled = false;
		     	form.loteOrigem.disabled = false;

			 	form.setorComercialDestinoCD.disabled = false;
			 	form.quadraDestinoNM.disabled = false;
		     	form.loteDestino.disabled = false;
			  }
			
			break;					
			   		   
		case 2: //De setor Comercial		   
		
		    if(form.setorComercialOrigemCD.value != form.setorComercialDestinoCD.value){
			  	form.quadraOrigemNM.disabled = true;
		     	form.loteOrigem.disabled = true;
			 	form.quadraDestinoNM.disabled = true;
		     	form.loteDestino.disabled = true;
             
             	form.loteOrigem.value = "";
             	form.subloteInicial.value = "";
              	form.segmentoInicial.value = "";
             	form.quadraOrigemNM.value = "";	
             	form.quadraOrigemID.value = "";
             	limparPesquisaQuadraInicial();
             	form.loteDestino.value = "";
             	form.quadraDestinoNM.value = "";	
             	form.quadraDestinoID.value = "";
             	limparPesquisaQuadraFinal();
             	limparPesquisaRotaInicial();

  			  }else{
			 	form.quadraOrigemNM.disabled = false;
		     	form.loteOrigem.disabled = false;
			 	form.quadraDestinoNM.disabled = false;
		     	form.loteDestino.disabled = false;
			  }
			
			break;
           
		case 3://De quadra 
		     if(form.quadraOrigemNM.value != form.quadraDestinoNM.value){
			  	form.loteOrigem.disabled = true;
			 	form.loteDestino.disabled = true;
             	
             	form.loteOrigem.value = "";
             	form.loteDestino.value = "";
             	form.subloteInicial.value = "";
             	form.segmentoInicial.value = "";
             	form.subloteFinal.value = "";
             	form.segmentoFinal.value = "";
             	limparPesquisaRotaInicial();
             				 
			  }else{
			 	form.loteOrigem.disabled = false;
			 	form.loteDestino.disabled = false;
			  }
			
			break;
		}
	} 

	function abrirPopupRota(idLocalidade, codigoSetorComercial, campoRetorno){
		if(campoRetorno == 'cdRotaInicial'){
			abrirPopupDependencia('exibirPesquisarRotaAction.do?idLocalidade='+idLocalidade+'&codigoSetorComercial='+codigoSetorComercial+'&restringirPesquisa=true'+'&destinoCampo='+campoRetorno,codigoSetorComercial,'Setor Comercial Inicial', 400, 800);
		}else{
			abrirPopupDependencia('exibirPesquisarRotaAction.do?idLocalidade='+idLocalidade+'&codigoSetorComercial='+codigoSetorComercial+'&restringirPesquisa=true'+'&destinoCampo='+campoRetorno,codigoSetorComercial,'Setor Comercial Final', 400, 800);
		}
	}

	function limparPesquisaRotaInicial() {
	    var form = document.forms[0];

	      form.cdRotaInicial.value = "";
	      form.nomeRotaInicial.value = "";
	      limparPesquisaRotaFinal();
	}

	function limparPesquisaRotaFinal() {
	    var form = document.forms[0];

	      form.cdRotaFinal.value = "";
	      form.nomeRotaFinal.value = "";
	}

	function limparPesquisaDescricaoRotaInicial() {
	    var form = document.forms[0];
	      form.nomeRotaInicial.value = "";
	      form.cdRotaFinal.value = "";
	      form.nomeRotaFinal.value = "";
	}

	function limparPesquisaDescricaoRotaFinal() {
	    var form = document.forms[0];
	      form.nomeRotaFinal.value = "";
	}

	function recuperarDadosQuatroParametros(idRegistro, descricaoRegistro, codigoRegistro, tipoConsulta) {

		var form = document.forms[0];

		if (tipoConsulta == 'rota') {
			if(codigoRegistro == 'cdRotaInicial'){
				limparPesquisaRotaInicial()
				form.cdRotaInicial.value = idRegistro;
				form.nomeRotaInicial.value = descricaoRegistro;
				form.nomeRotaInicial.style.color = "#000000";
				limparPesquisaRotaFinal()
				form.cdRotaFinal.value = idRegistro;
				form.nomeRotaFinal.value = descricaoRegistro;
				form.nomeRotaFinal.style.color = "#000000";
				form.segmentoInicial.focus();
			}else{
				limparPesquisaRotaFinal()
				form.cdRotaFinal.value = idRegistro;
				form.nomeRotaFinal.value = descricaoRegistro;
				form.nomeRotaFinal.style.color = "#000000";
				form.segmentoInicial.focus();
			}
		}
	}

	function configurarIndicador(){

		var checkedInscricao = document.forms[0].indicadorOrdenacao[0].checked;
		var checkedRotaLeitura = document.forms[0].indicadorOrdenacao[1].checked;
		var checkedNome = document.forms[0].indicadorOrdenacao[0].checked;
		var checkedEndereco = document.forms[0].indicadorOrdenacao[1].checked;

		if(!checkedInscricao && !checkedRotaLeitura){

			document.forms[0].indicadorOrdenacao[0].checked = true;

		}

		if(!checkedNome && !checkedEndereco){

			document.forms[0].indicadorOrdenacao[0].checked = true;

		}
	
	}
	
-->    
</script>
</logic:equal>

</head>
<body leftmargin="5" topmargin="5"
	onload="setarFoco('${requestScope.nomeCampo}'); configurarIndicador(); ">


<html:form action="/filtrarImovelOutrosCriteriosWizardAction"
	type="gcom.gui.cadastro.imovel.ImovelOutrosCriteriosActionForm"
	onsubmit="return validateImovelOutrosCriteriosActionForm(this);"
	name="ImovelOutrosCriteriosActionForm" method="post">

	<jsp:include
		page="/jsp/util/wizard/navegacao_abas_wizard_valida_avancar_valida_voltar.jsp?numeroPagina=1" />

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="4" cellpadding="0">
		<input type="hidden" name="numeroPagina" value="1" />
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
					<td width="11">
						<img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" />
					</td>
					<td class="parabg">Filtrar Imóvel   </td>
					<td width="11">
						<img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" />
					</td>
				</tr>
			</table>
			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td><strong>Gerência Regional:</strong></td>
					<td>
						<html:select
						property="idGerenciaRegional"
							name="ImovelOutrosCriteriosActionForm" 
							tabindex="1">
						<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
			            </html:option>
							<logic:iterate name="colecaoGerenciasRegionais" id="colecaoGerenciasRegionais">
								<html:option 
									value="${colecaoGerenciasRegionais.id}">
									<bean:write 
										name="colecaoGerenciasRegionais"
									property="nomeAbreviado" /> 
						           - <bean:write 
						           		name="colecaoGerenciasRegionais"
									property="nome" />
							</html:option>
						</logic:iterate>
						</html:select>
					</td>
				</tr>

				<tr>
					<td><strong>Unidade Negócio:</strong></td>
					<td>
						<html:select
							property="unidadeNegocio" 
							name="ImovelOutrosCriteriosActionForm"
						tabindex="1">
						<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
			            </html:option>
						<logic:present name="colecaoUnidadeNegocio">
								<logic:iterate name="colecaoUnidadeNegocio" id="colecaoUnidadeNegocios">
									<html:option 
										value="${colecaoUnidadeNegocios.id}">
										<bean:write 
											name="colecaoUnidadeNegocios"
										property="nomeAbreviado" /> 
							           - <bean:write 
							           		name="colecaoUnidadeNegocios"
										property="nome" />
								</html:option>
							</logic:iterate>
						</logic:present>
						</html:select>
					</td>
				</tr>
				<logic:present name="ImovelOutrosCriteriosActionForm" property="exibirOrdenacao">
					<logic:equal name="ImovelOutrosCriteriosActionForm" property="exibirOrdenacao" value="trueRelacaoDebitos">
						<tr>
							<td><strong>Ordenar Por:<font color="#FF0000"></font></strong></td>
							
							<td>
								<html:radio property="indicadorOrdenacao" value="1" tabindex="5">
									<strong> Inscricao </strong> 
								</html:radio>
								<html:radio property="indicadorOrdenacao" value="2" tabindex="5">
									<strong> Matricula </strong> 
								</html:radio>
								<html:radio property="indicadorOrdenacao" value="3" tabindex="5">
									<strong> Usuario </strong> 
								</html:radio>
								<html:radio property="indicadorOrdenacao" value="4" tabindex="5">
									<strong> Valor </strong> 
								</html:radio>
							</td>
						</tr>
					</logic:equal>
				</logic:present> 
				<logic:present name="ImovelOutrosCriteriosActionForm" property="exibirOrdenacao">
					<logic:equal name="ImovelOutrosCriteriosActionForm" property="exibirOrdenacao" value="true">
						<tr>
							<td><strong>Ordenar Por:<font color="#FF0000"></font></strong></td>
							
							<td>
								<html:radio property="indicadorOrdenacao" value="1" tabindex="5">
									<strong> Inscrição </strong> 
								</html:radio>
								<html:radio property="indicadorOrdenacao" value="2" tabindex="5">
									<strong> Rota de Leitura </strong> 
								</html:radio>
							</td>
						</tr>
					</logic:equal>
				</logic:present> 
				
				
					<logic:present name="ImovelOutrosCriteriosActionForm" property="exibirOrdenacaoNomeEndereco">
					<logic:equal name="ImovelOutrosCriteriosActionForm" property="exibirOrdenacaoNomeEndereco" value="true">
						<tr>
							<td><strong>Ordenar Por:<font color="#FF0000"></font></strong></td>
							
							<td>
								<html:radio property="indicadorOrdenacao" value="1" tabindex="5">
									<strong> Nome </strong> 
								</html:radio>
								<html:radio property="indicadorOrdenacao" value="2" tabindex="5">
									<strong> Endereço </strong> 
								</html:radio>
							</td>
						</tr>
					</logic:equal>
				</logic:present> 
				
							

				<tr height="1">
					<td colspan="4">&nbsp;</td>
				</tr>
				
				<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_ADA%>" scope="session">
				<tr>
					<td colspan="4">
						<table 
							width="100%" 
							align="center" 
							bgcolor="#99CCFF" 
							border="0">
		                	
		                	<tr> 
		                      <td colspan="2">
		                      	<strong>Informe os dados da inscri&ccedil;&atilde;o inicial:</strong>
		                      </td>
		                	</tr>
		                	
		                	<tr bgcolor="#cbe5fe"> 
		                  	  	<td width="100%" 
		                  	  		align="center" 
		                  	  		colspan="2"> 
		                  	    
			                  	    <table width="100%" border="0">
									
										<tr bgcolor="#cbe5fe"> 
											<html:hidden 
												property="inscricaoTipo" />
											<td width="20%"><strong>Localidade:</strong></td>
											<td>
												<html:text 
													tabindex="2" 
													maxlength="3"
													property="localidadeOrigemID" 
													size="5"
													onkeypress="form.target='';limparDestino(1);validaEnter(event, 'filtrarImovelOutrosCriteriosWizardAction.do?action=exibirFiltrarImovelOutrosCriteriosLocalizarImoveis&objetoConsulta=1&inscricaoTipo=origem', 'localidadeOrigemID');"
													onclick="validarLocalidade()" 
													onblur="duplicarLocalidade();" /> 
													<a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'origem', null, null, 275, 480, '');limparOrigem(1);">
													<img width="23" 
														height="21" 
														border="0"
														src="<bean:message 
															key="caminho.imagens"/>pesquisa.gif" 
															title="Pesquisar" />
													</a> 
													<logic:present name="corLocalidadeOrigem">
														<logic:equal name="corLocalidadeOrigem" value="exception">
															<html:text 
																property="nomeLocalidadeOrigem" 
																size="45"
																readonly="true"
																style="background-color:#EFEFEF; border:0; color: #ff0000" />
														</logic:equal>
														<logic:notEqual name="corLocalidadeOrigem" value="exception">
															<html:text 
																property="nomeLocalidadeOrigem" 
																size="45"
																readonly="true"
																style="background-color:#EFEFEF; border:0; color: #000000" />
														</logic:notEqual>
													</logic:present> 
													<logic:notPresent name="corLocalidadeOrigem">
														<logic:empty name="ImovelOutrosCriteriosActionForm" property="localidadeOrigemID">
															<html:text 
																property="nomeLocalidadeOrigem" 
																value="" 
																size="45"
																readonly="true"
																style="background-color:#EFEFEF; border:0; color: #ff0000" />
														</logic:empty>
														<logic:notEmpty name="ImovelOutrosCriteriosActionForm" property="localidadeOrigemID">
															<html:text 
																property="nomeLocalidadeOrigem" 
																size="45"
																readonly="true"
																style="background-color:#EFEFEF; border:0; color: 	#000000" />
														</logic:notEmpty>
													</logic:notPresent> 
													<a href="javascript:limparBorrachaOrigem(1);limparMsgQuadraInexistente();">
													<img src="<bean:message 
														key="caminho.imagens"/>limparcampo.gif"
														border="0" 
														title="Apagar" />
													</a>
												</td>
											</tr>
											
											<tr>
												<td><strong>Setor Comercial:</strong></td>
												<td><html:text 
													tabindex="3" 
													maxlength="3"
													property="setorComercialOrigemCD" 
													size="5"
													onkeyup="limparOrigem(2);"
													onkeypress="form.target='';limparDestino(2);validaEnterDependencia(event, 'filtrarImovelOutrosCriteriosWizardAction.do?action=exibirFiltrarImovelOutrosCriteriosLocalizarImoveis&objetoConsulta=2&inscricaoTipo=origem', document.forms[0].setorComercialOrigemCD, document.forms[0].localidadeOrigemID.value, 'Localidade Inicial.');"
													onblur="javascript:duplicarSetorComercial();" /> 
													<a href="javascript:chamarPopupComDependencia('exibirPesquisarSetorComercialAction.do', 'setorComercialOrigem','idLocalidade', document.ImovelOutrosCriteriosActionForm.localidadeOrigemID.value, 275, 480, 'Informe Localidade Inicial.');limparOrigem(2);">
														<img width="23" 
															height="21" 
															border="0"
															src="<bean:message key="caminho.imagens"/>pesquisa.gif"
														title="Pesquisar" />
													</a>
													<logic:present name="corSetorComercialOrigem">
													<logic:equal name="corSetorComercialOrigem" value="exception">
														<html:text 
															property="nomeSetorComercialOrigem" 
															size="45"
															readonly="true"
															style="background-color:#EFEFEF; border:0; color: #ff0000" />
													</logic:equal>
													<logic:notEqual name="corSetorComercialOrigem" value="exception">
														<html:text 
															property="nomeSetorComercialOrigem" 
															size="45"
															readonly="true"
															style="background-color:#EFEFEF; border:0; color: #000000" />
													</logic:notEqual>
													</logic:present> 
													<logic:notPresent name="corSetorComercialOrigem">
														<logic:empty name="ImovelOutrosCriteriosActionForm" property="setorComercialOrigemCD">
															<html:text 
																property="nomeSetorComercialOrigem" 
																value="" 
																size="45"
																readonly="true"
																style="background-color:#EFEFEF; border:0; color: #ff0000" />
														</logic:empty>
														<logic:notEmpty name="ImovelOutrosCriteriosActionForm" property="setorComercialOrigemCD">
															<html:text 
																property="nomeSetorComercialOrigem" 
																size="45"
																readonly="true"
																style="background-color:#EFEFEF; border:0; color: #000000" />
														</logic:notEmpty>
													</logic:notPresent> 
													<a href="javascript:limparBorrachaOrigem(2);limparMsgQuadraInexistente();">
														<img src="<bean:message 
															key="caminho.imagens"/>limparcampo.gif"
															border="0" 
															title="Apagar" />
													</a> 
													<html:hidden property="setorComercialOrigemID" />
												</td>
											</tr>
											<tr>
												<td width="183"><strong>Quadra:</strong></td>
												<td width="432">
													<html:text 
														tabindex="4" 
														maxlength="3"
														property="quadraOrigemNM" 
														size="5"
														onkeypress="form.target='';limparOrigem(3);validaEnterDependencia(event, 'filtrarImovelOutrosCriteriosWizardAction.do?action=exibirFiltrarImovelOutrosCriteriosLocalizarImoveis&objetoConsulta=3&inscricaoTipo=origem', document.forms[0].quadraOrigemNM, document.forms[0].setorComercialOrigemCD.value, 'Setor Comercial Inicial.');"
														onblur="javascript:duplicarQuadra();" /> 
														<logic:present name="corQuadraOrigem" scope="request">
															<span 
																style="color:#ff0000" 
																id="msgQuadraInicial">
																<bean:write scope="request" name="msgQuadraInicial" />
															</span>
														</logic:present>
													<html:hidden property="quadraOrigemID" />
												</td>
											</tr>
											<tr>
												<td><strong>Lote:</strong></td>
												<td>
													<logic:present name="parametroGerarRelatorio">
														
														<logic:equal name="parametroGerarRelatorio" value="RelatorioImoveisEndereco">
															<html:text 
																maxlength="4" 
																property="loteOrigem" 
																size="5"
																onblur="javascript:duplicarLote();" 
																tabindex="5"
																disabled="true" />
														</logic:equal>
														<logic:notEqual name="parametroGerarRelatorio" value="RelatorioImoveisEndereco">
															<html:text 
																maxlength="4" 
																property="loteOrigem" 
																size="5"
																onblur="javascript:duplicarLote();" 
																tabindex="5" />
														</logic:notEqual>
													</logic:present>			         
													<logic:notPresent name="parametroGerarRelatorio">
														<html:text 
															maxlength="4" 
															property="loteOrigem" 
															size="5"
															onblur="javascript:duplicarLote();" 
															tabindex="5" />
													</logic:notPresent>
												</td>
											</tr>
				   					</table>
						 		</td>
							</tr>
               			</table>
                  	</td>
				</tr>

				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>

				<tr>
					<td colspan="4">
					<table 
						width="100%" 
						align="center" 
						bgcolor="#99CCFF" 
						border="0">
	                	
	                	<tr> 
	                      <td colspan="2"><strong>Informe os dados da inscri&ccedil;&atilde;o Final:</strong></td>
	                	</tr>
	                	
	                	<tr bgcolor="#cbe5fe"> 
	                  	  	<td width="100%" 
	                  	  		align="center" 
	                  	  		colspan="2"> 
	                  	    
		                  	    <table 
		                  	    	width="100%" 
		                  	    	border="0">
									<tr bgcolor="#cbe5fe"> 
										<td width="20%"><strong>Localidade:</strong></td>
										<td><html:text 
											maxlength="3" 
											property="localidadeDestinoID"
											size="5" 
											onkeyup="desabilitaIntervaloDiferente(1);"
											onkeypress="limparDestino(1);validaEnter(event,'filtrarImovelOutrosCriteriosWizardAction.do?action=exibirFiltrarImovelOutrosCriteriosLocalizarImoveis&objetoConsulta=1&inscricaoTipo=destino', 'localidadeDestinoID');"
											tabindex="6" /> 
											<a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'destino', null, null, 275, 480, '');limparDestino(1);"><img
												width="23" 
												height="21" 
												border="0"
												src="<bean:message key="caminho.imagens"/>pesquisa.gif"
													title="Pesquisar" />
											</a> 
											<logic:present name="corLocalidadeDestino">
												<logic:equal name="corLocalidadeDestino" value="exception">
													<html:text 
														property="nomeLocalidadeDestino" 
														size="45"
														readonly="true"
														style="background-color:#EFEFEF; border:0; color: #ff0000" />
												</logic:equal>
						
												<logic:notEqual name="corLocalidadeDestino" value="exception">
													<html:text 
														property="nomeLocalidadeDestino" 
														size="45"
														readonly="true"
														style="background-color:#EFEFEF; border:0; color: #000000" />
												</logic:notEqual>
											</logic:present> 
											<logic:notPresent name="corLocalidadeDestino">
												<logic:empty name="ImovelOutrosCriteriosActionForm" property="localidadeDestinoID">
													<html:text 
														property="nomeLocalidadeDestino" 
														value="" 
														size="45"
														readonly="true"
														style="background-color:#EFEFEF; border:0; color: #ff0000" />
												</logic:empty>
												<logic:notEmpty name="ImovelOutrosCriteriosActionForm" property="localidadeDestinoID">
													<html:text property="nomeLocalidadeDestino" 
														size="45" 
														readonly="true"
														style="background-color:#EFEFEF; border:0; color: 	#000000" />
												</logic:notEmpty>
											</logic:notPresent> 
											<a href="javascript:limparBorrachaDestino(1);limparPesquisaQuadraFinal();">
												<img src="<bean:message 
													key="caminho.imagens"/>limparcampo.gif"
													border="0" 
													title="Apagar" />
											</a>
										</td>
									</tr>
									<tr>
										<td><strong>Setor Comercial:</strong></td>
										<td><html:text 
											maxlength="3" 
											property="setorComercialDestinoCD"
											size="5"
											onkeyup="limparDestino(2);desabilitaIntervaloDiferente(2);"
											onkeypress="form.target='';validaEnterDependencia(event, 'filtrarImovelOutrosCriteriosWizardAction.do?action=exibirFiltrarImovelOutrosCriteriosLocalizarImoveis&objetoConsulta=2&inscricaoTipo=destino', document.forms[0].setorComercialDestinoCD, document.forms[0].localidadeDestinoID.value, 'Localidade Final.');"
											tabindex="7" /> 
											<a href="javascript:chamarPopupComDependencia('exibirPesquisarSetorComercialAction.do', 'setorComercialDestino', 'idLocalidade', document.ImovelOutrosCriteriosActionForm.localidadeDestinoID.value, 275, 480, 'Informe Localidade Final.');limparDestino(2);"><img
												width="23" 
												height="21" 
												border="0"
												src="<bean:message 
													key="caminho.imagens"/>pesquisa.gif"
													title="Pesquisar" />
											</a> 
											<logic:present name="corSetorComercialDestino">
												<logic:equal name="corSetorComercialDestino" value="exception">
													<html:text 
														property="nomeSetorComercialDestino" 
														size="45"
														readonly="true"
														style="background-color:#EFEFEF; border:0; color: #ff0000" />
												</logic:equal>
												<logic:notEqual name="corSetorComercialDestino" value="exception">
													<html:text 
														property="nomeSetorComercialDestino" 
														size="45"
														readonly="true"
														style="background-color:#EFEFEF; border:0; color: #000000" />
												</logic:notEqual>
											</logic:present> 
											<logic:notPresent name="corSetorComercialDestino">
												<logic:empty name="ImovelOutrosCriteriosActionForm" property="setorComercialDestinoCD">
													<html:text 
														property="nomeSetorComercialDestino" 
														value=""
														size="45" 
														readonly="true"
														style="background-color:#EFEFEF; border:0; color: #ff0000" />
												</logic:empty>
												<logic:notEmpty name="ImovelOutrosCriteriosActionForm" property="setorComercialDestinoCD">
													<html:text 
														property="nomeSetorComercialDestino" 
														size="45"
														readonly="true"
														style="background-color:#EFEFEF; border:0; color: #000000" />
												</logic:notEmpty>
											</logic:notPresent> 
											<a href="javascript:limparBorrachaDestino(2);limparPesquisaQuadraFinal();">
												<img src="<bean:message 
													key="caminho.imagens"/>limparcampo.gif"
													border="0" 
													title="Apagar" />
											</a> 
											<html:hidden property="setorComercialDestinoID" />
										</td>
									</tr>
									<tr>
										<td><strong>Quadra:</strong></td>
										<td><html:text 
											maxlength="5" 
											property="quadraDestinoNM" 
											size="4"
											onkeyup="desabilitaIntervaloDiferente(3);"
											onkeypress="form.target='';limparDestino(3);validaEnterDependencia(event, 'filtrarImovelOutrosCriteriosWizardAction.do?action=exibirFiltrarImovelOutrosCriteriosLocalizarImoveis&objetoConsulta=3&inscricaoTipo=destino', document.forms[0].quadraDestinoNM, document.forms[0].setorComercialDestinoCD.value, 'Setor Comercial Final.');"
											tabindex="8" /> 
											<logic:present name="corQuadraDestino" scope="request">
												<span style="color:#ff0000" id="msgQuadraFinal">
													<bean:write scope="request" name="msgQuadraFinal" />
												</span>
											</logic:present> 
											<html:hidden property="quadraDestinoID" />
										</td>
									</tr>
									<tr>
										<td><strong>Lote:</strong></td>
										<td><logic:present name="parametroGerarRelatorio">
												<logic:equal name="parametroGerarRelatorio" value="RelatorioImoveisEndereco">
													<html:text maxlength="4" 
														property="loteDestino" 
														size="5"
														tabindex="9" 
														disabled="true" />
												</logic:equal>
												<logic:notEqual name="parametroGerarRelatorio" value="RelatorioImoveisEndereco">
													<html:text 
														maxlength="4" 
														property="loteDestino" 
														size="5"
														tabindex="9" />
												</logic:notEqual>
											</logic:present> 
											<logic:notPresent name="parametroGerarRelatorio">
												<html:text 
													maxlength="4" 
													property="loteDestino" 
													size="5"
													tabindex="9" />
											</logic:notPresent>
										</td>
									</tr>
							   </table>
					      </td>
						 </tr>
					   </table>
					</td>
				</tr>
			    </logic:equal>
				
			    <logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
				<tr>
				<td colspan="4">
				<table width="100%" align="center" bgcolor="#99CCFF" border="0">
                	<tr> 
	                      <td colspan="2"><strong>Informe os dados da inscri&ccedil;&atilde;o inicial:</strong></td>
                	</tr>
                	<tr bgcolor="#cbe5fe"> 
                  	  	<td width="100%" align="center" colspan="2"> 
                  	    
                  	    <table width="100%" border="0">
						<tr bgcolor="#cbe5fe"> 
							<html:hidden property="inscricaoTipo" />
							<td width="20%"><strong>Localidade:</strong></td>
										<td><html:text 
											tabindex="2" 
											maxlength="3"
											property="localidadeOrigemID" 
											size="5"
								onkeypress="form.target='';limparDestino(1);validaEnter(event, 'filtrarImovelOutrosCriteriosWizardAction.do?action=exibirFiltrarImovelOutrosCriteriosLocalizarImoveis&objetoConsulta=1&inscricaoTipo=origem', 'localidadeOrigemID');"
											onclick="validarLocalidade()" 
											onblur="duplicarLocalidade();" /> 
											<a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'origem', null, null, 275, 480, '');limparOrigem(1);"><img
												width="23" 
												height="21" 
												border="0"
												src="<bean:message 
													key="caminho.imagens"/>pesquisa.gif"
													title="Pesquisar" />
											</a> 
											<logic:present name="corLocalidadeOrigem">
								<logic:equal name="corLocalidadeOrigem" value="exception">
													<html:text 
														property="nomeLocalidadeOrigem" 
														size="45"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:equal>
		
								<logic:notEqual name="corLocalidadeOrigem" value="exception">
													<html:text 
														property="nomeLocalidadeOrigem" 
														size="45"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEqual>
											</logic:present> 
											<logic:notPresent name="corLocalidadeOrigem">
												<logic:empty name="ImovelOutrosCriteriosActionForm" property="localidadeOrigemID">
													<html:text 
														property="nomeLocalidadeOrigem" 
														value="" 
														size="45"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:empty>
												<logic:notEmpty name="ImovelOutrosCriteriosActionForm" property="localidadeOrigemID">
													<html:text 
														property="nomeLocalidadeOrigem" 
														size="45"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: 	#000000" />
								</logic:notEmpty>
											</logic:notPresent> 
											<a href="javascript:limparBorrachaOrigem(1);limparMsgQuadraInexistente();">
												<img src="<bean:message 
													key="caminho.imagens"/>limparcampo.gif"
													border="0" 
													title="Apagar" />
											</a>
										</td>
						</tr>
						<tr>
							<td><strong>Setor Comercial:</strong></td>
										<td><html:text 
											tabindex="3" 
											maxlength="3"
											property="setorComercialOrigemCD" 
											size="5"
								onkeyup="limparOrigem(2);"
								onkeypress="form.target='';limparDestino(2);validaEnterDependencia(event, 'filtrarImovelOutrosCriteriosWizardAction.do?action=exibirFiltrarImovelOutrosCriteriosLocalizarImoveis&objetoConsulta=2&inscricaoTipo=origem', document.forms[0].setorComercialOrigemCD, document.forms[0].localidadeOrigemID.value, 'Localidade Inicial.');"
											onblur="javascript:duplicarSetorComercial();" /> 
											<a href="javascript:chamarPopupComDependencia('exibirPesquisarSetorComercialAction.do', 'setorComercialOrigem','idLocalidade', document.ImovelOutrosCriteriosActionForm.localidadeOrigemID.value, 275, 480, 'Informe Localidade Inicial.');limparOrigem(2);"><img
												width="23" 
												height="21" 
												border="0"
												src="<bean:message 
													key="caminho.imagens"/>pesquisa.gif"
													title="Pesquisar" />
											</a> 
											<logic:present name="corSetorComercialOrigem">
								<logic:equal name="corSetorComercialOrigem" value="exception">
													<html:text 
														property="nomeSetorComercialOrigem" 
														size="45"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:equal>
		
								<logic:notEqual name="corSetorComercialOrigem" value="exception">
													<html:text 
														property="nomeSetorComercialOrigem" 
														size="45"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEqual>
											</logic:present> 
											<logic:notPresent name="corSetorComercialOrigem">
												<logic:empty name="ImovelOutrosCriteriosActionForm" property="setorComercialOrigemCD">
													<html:text 
														property="nomeSetorComercialOrigem" 
														value="" 
														size="45"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:empty>
												<logic:notEmpty name="ImovelOutrosCriteriosActionForm" property="setorComercialOrigemCD">
													<html:text 
														property="nomeSetorComercialOrigem" 
														size="45"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEmpty>
											</logic:notPresent> 
											<a href="javascript:limparBorrachaOrigem(2);limparMsgQuadraInexistente();">
												<img src="<bean:message 
													key="caminho.imagens"/>limparcampo.gif"
													border="0" 
													title="Apagar" />
											</a> 
											<html:hidden property="setorComercialOrigemID" />
										</td>
									</tr>


						<tr>
							<td><strong>Quadra:</strong></td>
							<td><html:text 
									tabindex="4" 
									maxlength="5"
									property="quadraOrigemNM" 
									size="4"
									onkeypress="form.target='';validaEnterDependencia(event, 'filtrarImovelOutrosCriteriosWizardAction.do?action=exibirFiltrarImovelOutrosCriteriosLocalizarImoveis&objetoConsulta=3&inscricaoTipo=origem', document.forms[0].quadraOrigemNM, document.forms[0].setorComercialOrigemCD.value, 'Setor Comercial Inicial.');"
									onblur="javascript:duplicarQuadra();" /> 
									<logic:present name="corQuadraOrigem" scope="request">
										<span style="color:#ff0000" id="msgQuadraInicial">
											<bean:write scope="request" name="msgQuadraInicial" />
										</span>
									</logic:present> 
								<html:hidden property="quadraOrigemID" />
							</td>
						</tr>
						<tr>
							<td><strong>Lote:</strong></td>
							<td>
								<html:text 
									maxlength="4" 
									property="loteOrigem" 
									size="5"
									onblur="javascript:duplicarLote();" 
									tabindex="5" />
							</td>
						</tr>
						<tr>
						  	<td><strong>Sublote:</strong></td>
			              	<td>
			              		<html:text 
									maxlength="3" 
									property="subloteInicial"
									onblur="javascript:duplicarSublote();"  
									size="4"
									tabindex="9" />
							</td>
						</tr>
					   </table>
					 </td>
					</tr>
                   </table>
                  </td>
                </tr>
				<tr>
					<td colspan="4">
					&nbsp;
					</td>
				</tr>
				<tr>
				<td colspan="4">
					<table 
						width="100%" 
						align="center" 
						bgcolor="#99CCFF" 
						border="0">
	                	
                	<tr> 
                      <td colspan="2"><strong>Informe os dados da inscri&ccedil;&atilde;o Final:</strong></td>
                	</tr>
                	<tr bgcolor="#cbe5fe"> 
	                  	  	<td width="100%" 
	                  	  		align="center" 
	                  	  		colspan="2"> 
                  	    
                  	    <table width="100%" border="0">
						<tr bgcolor="#cbe5fe"> 
							<td width="20%"><strong>Localidade:</strong></td>
										<td><html:text 
											maxlength="3" 
											property="localidadeDestinoID"
											size="5" 
								onkeypress="limparDestino(1);
								validaEnter(event,'filtrarImovelOutrosCriteriosWizardAction.do?action=exibirFiltrarImovelOutrosCriteriosLocalizarImoveis&objetoConsulta=1&inscricaoTipo=destino', 'localidadeDestinoID');"
											tabindex="6" /> 
											<a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'destino', null, null, 275, 480, '');limparDestino(1);"><img
												width="23" 
												height="21" 
												border="0"
												src="<bean:message 
												key="caminho.imagens"/>pesquisa.gif"
												title="Pesquisar" />
											</a> 
											<logic:present name="corLocalidadeDestino">
								<logic:equal name="corLocalidadeDestino" value="exception">
													<html:text 
														property="nomeLocalidadeDestino" 
														size="45"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:equal>
		
								<logic:notEqual name="corLocalidadeDestino" value="exception">
													<html:text 
														property="nomeLocalidadeDestino" 
														size="45"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEqual>
											</logic:present> 
											<logic:notPresent name="corLocalidadeDestino">
												<logic:empty name="ImovelOutrosCriteriosActionForm" property="localidadeDestinoID">
													<html:text 
														property="nomeLocalidadeDestino" 
														value="" 
														size="45"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:empty>
												<logic:notEmpty name="ImovelOutrosCriteriosActionForm" property="localidadeDestinoID">
													<html:text 
														property="nomeLocalidadeDestino" 
														size="45"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: 	#000000" />
								</logic:notEmpty>
											</logic:notPresent> 
											<a href="javascript:limparBorrachaDestino(1);limparPesquisaQuadraFinal();">
												<img src="<bean:message 
													key="caminho.imagens"/>limparcampo.gif"
													border="0" 
													title="Apagar" />
											</a>
										</td>
						</tr>
						<tr>
							<td><strong>Setor Comercial :</strong></td>
										<td><html:text 
											maxlength="3" 
											property="setorComercialDestinoCD"
								size="5"
											onkeyup="limparDestino(2);"
								onkeypress="form.target='';validaEnterDependencia(event, 'filtrarImovelOutrosCriteriosWizardAction.do?action=exibirFiltrarImovelOutrosCriteriosLocalizarImoveis&objetoConsulta=2&inscricaoTipo=destino', document.forms[0].setorComercialDestinoCD, document.forms[0].localidadeDestinoID.value, 'Localidade Final.');"
											tabindex="7" /> 
											<a href="javascript:chamarPopupComDependencia('exibirPesquisarSetorComercialAction.do', 'setorComercialDestino', 'idLocalidade', document.ImovelOutrosCriteriosActionForm.localidadeDestinoID.value, 275, 480, 'Informe Localidade Final.');limparDestino(2);"><img
												width="23" 
												height="21" 
												border="0"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
													title="Pesquisar" />
											</a> 
											<logic:present name="corSetorComercialDestino">
								<logic:equal name="corSetorComercialDestino" value="exception">
													<html:text 
														property="nomeSetorComercialDestino" 
														size="45"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:equal>
		
								<logic:notEqual name="corSetorComercialDestino" value="exception">
													<html:text 
														property="nomeSetorComercialDestino" 
														size="45"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEqual>
											</logic:present> 
											<logic:notPresent name="corSetorComercialDestino">
												<logic:empty name="ImovelOutrosCriteriosActionForm" property="setorComercialDestinoCD">
													<html:text 
														property="nomeSetorComercialDestino" 
														value=""
														size="45" 
														readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:empty>
												<logic:notEmpty name="ImovelOutrosCriteriosActionForm" property="setorComercialDestinoCD">
													<html:text 
														property="nomeSetorComercialDestino" 
														size="45"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEmpty>
											</logic:notPresent> 
											<a href="javascript:limparBorrachaDestino(2);limparPesquisaQuadraFinal();">
												<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
													border="0" 
													title="Apagar" />
											</a> 
											<html:hidden property="setorComercialDestinoID" />
										</td>
									</tr>
									<tr>
										<td><strong>Quadra:</strong></td>
										<td><html:text 
												maxlength="5" property="quadraDestinoNM" size="4"
												onkeypress="form.target='';validaEnterDependencia(event, 'filtrarImovelOutrosCriteriosWizardAction.do?action=exibirFiltrarImovelOutrosCriteriosLocalizarImoveis&objetoConsulta=3&inscricaoTipo=destino', document.forms[0].quadraDestinoNM, document.forms[0].setorComercialDestinoCD.value, 'Setor Comercial Final.');"
												tabindex="8" /> 
												<logic:present name="corQuadraDestino" scope="request">
													<span style="color:#ff0000" id="msgQuadraFinal">
													<bean:write scope="request" name="msgQuadraFinal" />
													</span>
												</logic:present> 
										 
											<html:hidden property="quadraDestinoID" /></td>
									</tr>
									
						<tr>
							<td><strong>Lote:</strong></td>
							<td>
										<html:text 
											maxlength="4" 
											property="loteDestino" 
											size="5"
											tabindex="9" />
							</td>
						</tr>
						<tr>
						  	<td><strong>Sublote:</strong></td>
			              	<td>
								<html:text 
									maxlength="3" 
									property="subloteFinal" 
									size="4"
									tabindex="9" />
							</td>
						</tr>
					   </table>
				      </td>
					 </tr>
				   </table>
			     </td>
			    </tr>
			    
			    </logic:equal>
			    
				<tr>
					<td colspan="4"><hr></td>
				</tr>
				
				<tr>
					<td></td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td><strong>Municipio:</strong></td>
					<td><logic:present name="parametroGerarRelatorio">
							<html:text 
								property="idMunicipio" 
								size="5" 
								maxlength="4"
								onkeypress="javascript:form.target='';limparUltimosCampos(1);validaEnter(event, 'filtrarImovelOutrosCriteriosWizardAction.do?action=exibirFiltrarImovelOutrosCriteriosLocalizarImoveis&objetoConsulta=1', 'idMunicipio');"
								tabindex="10" />
							<a href="javascript:chamarPopup('exibirPesquisarMunicipioAction.do', 'idMunicipio', null, document.ImovelOutrosCriteriosActionForm.idMunicipio.value, 250, 470, 'Informe Município.');">
							<img width="23" 
								height="21" 
								border="0"
							src="<bean:message key="caminho.imagens"/>pesquisa.gif"
									title="Pesquisar" />
							</a>					
						</logic:present> 
						<logic:notPresent name="parametroGerarRelatorio">
						<html:text 
							property="idMunicipio" 
							size="5" 
							maxlength="4"
							onkeypress="javascript:form.target='';limparUltimosCampos(1);validaEnter(event, 'filtrarImovelOutrosCriteriosWizardAction.do?action=exibirFiltrarImovelOutrosCriteriosLocalizarImoveis&objetoConsulta=1', 'idMunicipio');"
							tabindex="10" />
						<a href="javascript:chamarPopup('exibirPesquisarMunicipioAction.do', 'idMunicipio', null, document.ImovelOutrosCriteriosActionForm.idMunicipio.value, 250, 470, 'Informe Município.');">
							<img width="23" 
								height="21" 
								border="0"
							src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar" />
						</a>
						</logic:notPresent> 
						<logic:notEqual name="ImovelOutrosCriteriosActionForm" property="nomeMunicipio" value="Município inexistente">
							<html:text 
								property="nomeMunicipio" 
								size="45" 
								readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual> 
						<logic:equal name="ImovelOutrosCriteriosActionForm" property="nomeMunicipio" value="Município inexistente">
							<html:text 
								property="nomeMunicipio" 
								size="45" 
								readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal> 
						<a href="javascript:limparMBCL(1);"> 
							<img src="<bean:message 
								key="caminho.imagens"/>limparcampo.gif"
							border="0" 
							title="Apagar" />
						</a>
					</td>
				</tr>

				<tr>
					<td><strong>Bairro:</strong></td>
					<td><logic:present name="parametroGerarRelatorio">						
							<html:text 
								maxlength="3" 
								property="idBairro" 
								size="5"
								onkeyup="validarBairro();"
								onkeypress="form.target='';limparUltimosCampos(2);validaEnterDependencia(event, 'filtrarImovelOutrosCriteriosWizardAction.do?action=exibirFiltrarImovelOutrosCriteriosLocalizarImoveis&objetoConsulta=1', this, document.forms[0].idMunicipio.value, 'Município');"
								tabindex="11" />
							<a href="javascript:chamarPopup('exibirPesquisarBairroAction.do', 'idBairro', 'idMunicipio', document.ImovelOutrosCriteriosActionForm.idMunicipio.value, 275, 480, 'Informe Município.');">
								<img width="23" 
									height="21" 
									border="0"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
										title="Pesquisar" />
							</a>
											
					</logic:present> 
					<logic:notPresent name="parametroGerarRelatorio">
						<html:text 
							maxlength="3" 
							property="idBairro" 
							size="5"
							onkeyup="validarBairro();"
							onkeypress="form.target='';limparUltimosCampos(2);validaEnterDependencia(event, 'filtrarImovelOutrosCriteriosWizardAction.do?action=exibirFiltrarImovelOutrosCriteriosLocalizarImoveis&objetoConsulta=1', this, document.forms[0].idMunicipio.value, 'Município');"
							tabindex="11" />
						<a href="javascript:chamarPopup('exibirPesquisarBairroAction.do', 'idBairro', 'idMunicipio', document.ImovelOutrosCriteriosActionForm.idMunicipio.value, 275, 480, 'Informe Município.');">
							<img width="23" 
								height="21" 
								border="0"
							src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar" />
						</a>
					</logic:notPresent> 
						<logic:notEqual
							name="ImovelOutrosCriteriosActionForm" property="nomeBairro" value="Bairro inexistente">
							<html:text 
								property="nomeBairro" 
								size="45" 
								readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual> 
						<logic:equal name="ImovelOutrosCriteriosActionForm" property="nomeBairro" value="Bairro inexistente">
							<html:text 
								property="nomeBairro" 
								size="45" 
								readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal> 
						<a href="javascript:limparMBCL(2);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" />
						</a>
					</td>
				</tr>


				<tr>
					<td><strong>Logradouro:</strong></td>
					<td><logic:present name="parametroGerarRelatorio">
							<html:text 
								maxlength="9" 
								property="idLogradouro" 
								size="9"
								onkeypress="form.target='';limparUltimosCampos(3);validaEnterDependencia(event, 'filtrarImovelOutrosCriteriosWizardAction.do?action=exibirFiltrarImovelOutrosCriteriosLocalizarImoveis&objetoConsulta=1', this, document.forms[0].idMunicipio.value, 'município');"
								tabindex="12" />
								
							<a href="javascript:chamarPopup('exibirPesquisarLogradouroAction.do', 'idLogradouro', null, document.ImovelOutrosCriteriosActionForm.idLogradouro.value, 275, 480, 'Informe Logradouro.');">
							<img width="23" height="21" border="0"
									src="<bean:message 
										key="caminho.imagens"/>pesquisa.gif"
										title="Pesquisar" />
							</a>
						</logic:present> 
						<logic:notPresent name="parametroGerarRelatorio">
							<html:text 
								maxlength="9" 
								property="idLogradouro" 
								size="9"
							onkeypress="form.target='';limparUltimosCampos(3);validaEnterDependencia(event, 'filtrarImovelOutrosCriteriosWizardAction.do?action=exibirFiltrarImovelOutrosCriteriosLocalizarImoveis&objetoConsulta=1', this, document.forms[0].idMunicipio.value, 'município');"
							tabindex="12" />
							<a href="javascript:chamarPopup('exibirPesquisarLogradouroAction.do', 'idLogradouro', null, document.ImovelOutrosCriteriosActionForm.idLogradouro.value, 275, 480, 'Informe Logradouro.');">
						<img width="23" height="21" border="0"
							src="<bean:message key="caminho.imagens"/>pesquisa.gif"
									title="Pesquisar" />
							</a>
						</logic:notPresent> 
						<logic:notEqual name="ImovelOutrosCriteriosActionForm" property="nomeLogradouro" value="Logradouro inexistente">
							<html:text 
								property="nomeLogradouro" 
								size="45" 
								readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual> 
						<logic:equal name="ImovelOutrosCriteriosActionForm" property="nomeLogradouro" value="Logradouro inexistente">
						<html:text 
							property="nomeLogradouro" 
							size="45" 
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal> 
						<a href="javascript:limparMBCL(3);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" title="Apagar" />
						</a>
					</td>
				</tr>
				<tr>
					<td><strong>CEP:</strong></td>
					<td><logic:present name="parametroGerarRelatorio">
							<logic:equal name="parametroGerarRelatorio" value="RelatorioCadastroConsumidoresInscricao">
								<html:text 
									maxlength="8" 
									property="CEP" 
									size="8" 
									tabindex="13"
								onkeypress="limparUltimosCampos(4);validaEnter(event, 'filtrarImovelOutrosCriteriosWizardAction.do?action=exibirFiltrarImovelOutrosCriteriosLocalizarImoveis&objetoConsulta=1', 'CEP');"
								disabled="true" />
								<img width="23" 
									height="21" 
									border="0"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar" />
						</logic:equal>
						<logic:notEqual name="parametroGerarRelatorio"
							value="RelatorioCadastroConsumidoresInscricao">
								<html:text maxlength="8" property="CEP" size="8" tabindex="13"
									onkeypress="limparUltimosCampos(4);validaEnter(event, 'filtrarImovelOutrosCriteriosWizardAction.do?action=exibirFiltrarImovelOutrosCriteriosLocalizarImoveis&objetoConsulta=1', 'CEP');" />
								<a
									href="javascript:abrirPopup('exibirPesquisarCepAction.do?&indicadorUsoTodos=1', 400, 800);">
								<img width="23" height="21" border="0"
									src="<bean:message key="caminho.imagens"/>pesquisa.gif"
									title="Pesquisar" /></a>
						</logic:notEqual>
						</logic:present> 
						<logic:notPresent name="parametroGerarRelatorio">
							<html:text 
								maxlength="8" 
								property="CEP" 
								size="8" 
								tabindex="13"
								onkeypress="limparUltimosCampos(4);validaEnter(event, 'filtrarImovelOutrosCriteriosWizardAction.do?action=exibirFiltrarImovelOutrosCriteriosLocalizarImoveis&objetoConsulta=1', 'CEP');" />
							<a href="javascript:abrirPopup('exibirPesquisarCepAction.do?&indicadorUsoTodos=1', 400, 800);">
						<img width="23" height="21" border="0"
							src="<bean:message key="caminho.imagens"/>pesquisa.gif"
									title="Pesquisar" />
							</a>
						</logic:notPresent> 
						<logic:notEqual name="ImovelOutrosCriteriosActionForm" property="descricaoCep" value="CEP Inexistente">
							<html:text 
								property="descricaoCep" 
								size="45" 
								readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual> 
						<logic:equal name="ImovelOutrosCriteriosActionForm" property="descricaoCep" value="CEP Inexistente">
							<html:text 
								property="descricaoCep" 
								size="45" 
								readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal> 
						<a href="javascript:limparMBCL(4);"> 
							<img src="<bean:message 
								key="caminho.imagens"/>limparcampo.gif"
								border="0" title="Apagar" />
						</a>
					</td>
				</tr>

				<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_ADA%>" scope="session">
				
				<tr>
					<td colspan="4">
					<hr>
					</td>
				</tr>
				
				<tr>
				<td colspan="4">
				<table 
					width="100%" 
					align="center" 
					bgcolor="#99CCFF" 
					border="0">
                	
                	<tr> 
                      <td colspan="2"><strong>Informe os dados da Rota Inicial:</strong></td>
                	</tr>
                	<tr bgcolor="#cbe5fe"> 
                  	  	<td width="100%" align="center" colspan="2"> 
                  	    
                  	    <table width="100%" border="0">
						<tr>
							<td height="24"><strong>Rota:<font color="#FF0000"></font></strong></td>
							<td width="81%" height="24" colspan="2">
							<html:text maxlength="5" property="cdRotaInicial" size="5" tabindex="7"
								onblur = "replicarRota();"
								onkeypress="javascript:limparPesquisaDescricaoRotaInicial(); return validaEnterDependencia(event, 'filtrarImovelOutrosCriteriosWizardAction.do?action=exibirFiltrarImovelOutrosCriteriosLocalizarImoveis&objetoConsulta=4&inscricaoTipo=origem', document.forms[0].cdRotaInicial, document.forms[0].setorComercialOrigemCD.value, 'Setor Comercial Inicial.');" />
							<a href="javascript:abrirPopupRota(document.forms[0].localidadeOrigemID.value, document.forms[0].setorComercialOrigemCD.value, 'cdRotaInicial');">
							<img border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/></a>
			
							<logic:present name="rotaNaoEncontradaInicial" scope="request">
								<html:text property="nomeRotaInicial" size="55" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
							</logic:present> 
							
							<logic:notPresent name="rotaNaoEncontradaInicial" scope="request">
								<html:text property="nomeRotaInicial" size="55" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
							</logic:notPresent>
							
							<a href="javascript:limparPesquisaRotaInicial(); document.forms[0].cdRotaInicial.focus();">
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar"/></a>
							</td>
						</tr>
						  
						<tr bgcolor="#cbe5fe">
						    <td height="10"><strong>Seq. da Rota:</strong></td>
									<td><html:text 
											property="sequencialRotaInicial" 
											maxlength="6" 
											size="7" 
											onblur="replicarSequencialRota();"/>
									</td>																								
						</tr>
					</table>
					</td>
					</tr>	
						  
						
                    </table>
                    </td>
                    </tr>
                    <tr>
					<td colspan="4">&nbsp;</td>
				</tr>
				<tr>
				<td colspan="4">
					<table 
						width="100%" 
						align="center" 
						bgcolor="#99CCFF" 
						border="0">
                	<tr> 
                      <td colspan="2"><strong>Informe os dados da Rota Final:</strong></td>
                	</tr>
                	<tr bgcolor="#cbe5fe"> 
	                 	  	<td width="100%" 
	                 	  		align="center" 
	                 	  		colspan="2"> 
                  	    
                  	    <table width="100%" border="0">
						  
					<tr>
						<td height="24"><strong>Rota:<font color="#FF0000"></font></strong></td>
						<td width="81%" height="24" colspan="2">
						<html:text maxlength="5" property="cdRotaFinal" size="5" tabindex="7"
							onkeypress="javascript:limparPesquisaDescricaoRotaFinal(); return validaEnterDependencia(event, 'filtrarImovelOutrosCriteriosWizardAction.do?action=exibirFiltrarImovelOutrosCriteriosLocalizarImoveis&objetoConsulta=4&inscricaoTipo=destino', document.forms[0].cdRotaFinal, document.forms[0].setorComercialDestinoCD.value, 'Setor Comercial Final.');" />
						<a href="javascript:abrirPopupRota(document.forms[0].localidadeDestinoID.value, document.forms[0].setorComercialDestinoCD.value, 'cdRotaFinal');">
						<img border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/></a>
		
						<logic:present name="rotaNaoEncontradaFinal" scope="request">
							<html:text property="nomeRotaFinal" size="55" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
						</logic:present> 
						
						<logic:notPresent name="rotaNaoEncontradaFinal" scope="request">
							<html:text property="nomeRotaFinal" size="55" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
						</logic:notPresent>
						
						<a href="javascript:limparPesquisaRotaFinal(); document.forms[0].cdRotaFinal.focus();">
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar"/></a>
						</td>
					</tr>
							  
					<tr bgcolor="#cbe5fe">
							<td height="10"><strong>Seq. da Rota:</strong></td>
										<td><html:text 
												property="sequencialRotaFinal" 
												maxlength="6" 
												size="7" />
										</td>																								
					</tr>
					</table>
					</td>
					</tr>
					
                    </table>
                    </td>
                    </tr>
                    <tr>
					<td colspan="4"></td>
		   </tr>
		  </logic:equal>
				
			<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
				<tr>
				<td colspan="4">
				<hr>
				</td>
			</tr>
			<tr>
				<td height="24"><strong>Rota Inicial:<font color="#FF0000"></font></strong></td>
				<td width="81%" height="24" colspan="2">
				<html:text maxlength="5" property="cdRotaInicial" size="5" tabindex="7"
					onblur = "replicarRota();"
					onkeypress="javascript:limparPesquisaDescricaoRotaInicial(); return validaEnterDependencia(event, 'filtrarImovelOutrosCriteriosWizardAction.do?action=exibirFiltrarImovelOutrosCriteriosLocalizarImoveis&objetoConsulta=4&inscricaoTipo=origem', document.forms[0].cdRotaInicial, document.forms[0].setorComercialOrigemCD.value, 'Setor Comercial Inicial.');" />
				<a href="javascript:abrirPopupRota(document.forms[0].localidadeOrigemID.value, document.forms[0].setorComercialOrigemCD.value, 'cdRotaInicial');">
				<img border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/></a>

				<logic:present name="rotaNaoEncontradaInicial" scope="request">
					<html:text property="nomeRotaInicial" size="55" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
				</logic:present> 
				
				<logic:notPresent name="rotaNaoEncontradaInicial" scope="request">
					<html:text property="nomeRotaInicial" size="55" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
				</logic:notPresent>
				
				<a href="javascript:limparPesquisaRotaInicial(); document.forms[0].cdRotaInicial.focus();">
				<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar"/></a>
				</td>
			</tr>
			<tr>
				<td height="24"><strong>Rota Final:<font color="#FF0000"></font></strong></td>
				<td width="81%" height="24" colspan="2">
				<html:text maxlength="5" property="cdRotaFinal" size="5" tabindex="7"
					onkeypress="javascript:limparPesquisaDescricaoRotaFinal(); return validaEnterDependencia(event, 'filtrarImovelOutrosCriteriosWizardAction.do?action=exibirFiltrarImovelOutrosCriteriosLocalizarImoveis&objetoConsulta=4&inscricaoTipo=destino', document.forms[0].cdRotaFinal, document.forms[0].setorComercialDestinoCD.value, 'Setor Comercial Final.');" />
				<a href="javascript:abrirPopupRota(document.forms[0].localidadeDestinoID.value, document.forms[0].setorComercialDestinoCD.value, 'cdRotaFinal');">
				<img border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/></a>

				<logic:present name="rotaNaoEncontradaFinal" scope="request">
					<html:text property="nomeRotaFinal" size="55" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
				</logic:present> 
				
				<logic:notPresent name="rotaNaoEncontradaFinal" scope="request">
					<html:text property="nomeRotaFinal" size="55" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
				</logic:notPresent>
				
				<a href="javascript:limparPesquisaRotaFinal(); document.forms[0].cdRotaFinal.focus();">
				<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar"/></a>
				</td>
			</tr>
			<tr>
				<td><strong>Segmento Inicial:</strong></td>
				<td><html:text 
					property="segmentoInicial" 
					maxlength="6" 
					onblur="replicarSegmento();"
					size="7" />
				</td>																								
			</tr>
			<tr>
				<td><strong>Segmento Final:</strong></td>
				<td><html:text 
						property="segmentoFinal" 
						maxlength="6" 
						size="7" />
				</td>																								
			</tr>
			</logic:equal>
				
				<tr>
					<td colspan="2">
					<div align="right"><jsp:include
						page="/jsp/util/wizard/navegacao_botoes_wizard_valida_avancar_valida_voltar.jsp?numeroPagina=1" /></div>
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
