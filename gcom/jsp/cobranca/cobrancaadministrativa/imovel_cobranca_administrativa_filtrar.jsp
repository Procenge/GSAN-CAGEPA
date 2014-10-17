<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<%@ page import="gcom.util.ConstantesSistema"%>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
	<html:javascript staticJavascript="false"  formName="FiltrarImovelCobrancaAdministrativaActionForm"/>

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>

<script language="JavaScript">

	function validarForm(){
   		var form = document.forms[0];
   		
	   		if(validateFiltrarImovelCobrancaAdministrativaActionForm(form)){

				if(validaTodosPeriodos(form)){
					enviarSelectMultiplo('FiltrarImovelCobrancaAdministrativaActionForm','idsEmpresaSelecionadas');
					enviarSelectMultiplo('FiltrarImovelCobrancaAdministrativaActionForm','idsGerenciaRegionalSelecionadas');
					enviarSelectMultiplo('FiltrarImovelCobrancaAdministrativaActionForm','idsUnidadeNegocioSelecionadas');
					enviarSelectMultiplo('FiltrarImovelCobrancaAdministrativaActionForm','idsCategoriaSelecionadas');
					enviarSelectMultiplo('FiltrarImovelCobrancaAdministrativaActionForm','idsSubcategoriaSelecionadas');
					enviarSelectMultiplo('FiltrarImovelCobrancaAdministrativaActionForm','idsLigacaoAguaSituacaoSelecionadas');
					enviarSelectMultiplo('FiltrarImovelCobrancaAdministrativaActionForm','idsLigacaoEsgotoSituacaoSelecionadas');
					enviarSelectMultiplo('FiltrarImovelCobrancaAdministrativaActionForm','idsMotivoRetiradaSelecionados');
					form.action = 'filtrarImovelCobrancaAdministrativaAction.do';
		   			form.submit();
				}
		  	}
    }

	function chamarPopupComDependencia(url, tipo, objeto, codigoObjeto, altura, largura, msg){

		if (objeto == null || codigoObjeto == null){
			abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
		}
		else{
			if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
				alert(msg);
			}
			else{
				abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto, altura, largura);
			}
		}
	}

	function recuperarDadosQuatroParametros(idRegistro, descricaoRegistro, codigoRegistro, tipoConsulta) {

		var form = document.forms[0];

		if (tipoConsulta == 'setorComercialOrigem') {

			form.codigoSetorComercialInicial.value = codigoRegistro;
		    form.descricaoSetorComercialInicial.value = descricaoRegistro;
	      	form.descricaoSetorComercialInicial.style.color = "#000000";

	      	form.codigoSetorComercialFinal.value = codigoRegistro;
		    form.descricaoSetorComercialFinal.value = descricaoRegistro;
	      	form.descricaoSetorComercialFinal.style.color = "#000000";
		}

		if (tipoConsulta == 'setorComercialDestino') {

	      	form.codigoSetorComercialFinal.value = codigoRegistro;
		    form.descricaoSetorComercialFinal.value = descricaoRegistro;
	      	form.descricaoSetorComercialFinal.style.color = "#000000";
		}
	}

	function limparForm(){

		var form = document.forms[0];

    	form.idComando.value = "";
    	form.descricaoComando.value = ""; 

    	form.idImovel.value = "";
    	form.inscricaoImovel.value = "";
    		
    	form.idCliente.value = "";
    	form.nomeCliente.value = "";

    	form.arquivoDownload="";
    	form.arquivoMatricula="";
    	form.enderecoArquivoDownload="";
    	form.arquivoCarregado="";

		MoverTodosDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsEmpresa', 'idsEmpresaSelecionadas');
		MoverTodosDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsGerenciaRegional', 'idsGerenciaRegionalSelecionadas');
		MoverTodosDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsUnidadeNegocio', 'idsUnidadeNegocioSelecionadas');
		MoverTodosDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsCategoria', 'idsCategoriaSelecionadas');
		MoverTodosDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsSubcategoria', 'idsSubcategoriaSelecionadas');
		MoverTodosDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsLigacaoAguaSituacao', 'idsLigacaoAguaSituacaoSelecionadas');
		MoverTodosDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsLigacaoEsgotoSituacao', 'idsLigacaoEsgotoSituacaoSelecionadas');
		MoverTodosDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsMotivoRetirada', 'idsMotivoRetiradaSelecionados');


		form.periodoInclusaoInicial.value="";
		form.periodoInclusaoFinal.value="";
		form.periodoRetiradaInicial.value="";
		form.periodoRetiradaFinal.value="";
		form.valorDebitoInicial.value="";
		form.valorDebitoFinal.value="";
		form.idLocalidadeInicial.value = "";
		form.descricaoLocalidadeInicial.value = "";
		form.idLocalidadeFinal.value = "";
		form.descricaoLocalidadeFinal.value = "";
   		form.codigoSetorComercialInicial.value = "";
   		form.descricaoSetorComercialInicial.value = "";
   		form.codigoSetorComercialFinal.value = "";
   		form.descricaoSetorComercialFinal.value = "";
   		form.numeroQuadraInicial.value = "";

   
   		var quadraInicialInexistente = document.getElementById("alertaQuadraInicialInexistente");
		
		if (quadraInicialInexistente != null){

			quadraInicialInexistente.innerHTML = "";
		}

		var quadraFinalInexistente = document.getElementById("alertaQuadraFinalInexistente");
		
		if (quadraFinalInexistente != null){

			quadraFinalInexistente.innerHTML = "";
		}


   		form.numeroQuadraFinal.value = "";
   		form.indicadorSituacaoCobrancaAdministrativa[2].checked = true;

   		form.action = "/gsan/exibirFiltrarImovelCobrancaAdministrativaAction.do?reload=true&limpar=true";
		form.submit();
	}

	function limparFormSemSubmit(){

		var form = document.forms[0];

    	form.idComando.value = "";
    	form.descricaoComando.value = ""; 

    	form.idImovel.value = "";
    	form.inscricaoImovel.value = "";
    		
    	form.idCliente.value = "";
    	form.nomeCliente.value = "";

    	form.arquivoDownload="";
    	form.arquivoMatricula="";
    	form.enderecoArquivoDownload="";
    	form.arquivoCarregado="";

		MoverTodosDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsEmpresa', 'idsEmpresaSelecionadas');
		MoverTodosDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsGerenciaRegional', 'idsGerenciaRegionalSelecionadas');
		MoverTodosDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsUnidadeNegocio', 'idsUnidadeNegocioSelecionadas');
		MoverTodosDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsCategoria', 'idsCategoriaSelecionadas');
		MoverTodosDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsSubcategoria', 'idsSubcategoriaSelecionadas');
		MoverTodosDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsLigacaoAguaSituacao', 'idsLigacaoAguaSituacaoSelecionadas');
		MoverTodosDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsLigacaoEsgotoSituacao', 'idsLigacaoEsgotoSituacaoSelecionadas');
		MoverTodosDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsMotivoRetirada', 'idsMotivoRetiradaSelecionados');


		form.periodoInclusaoInicial.value="";
		form.periodoInclusaoFinal.value="";
		form.periodoRetiradaInicial.value="";
		form.periodoRetiradaFinal.value="";
		form.valorDebitoInicial.value="";
		form.valorDebitoFinal.value="";
		form.idLocalidadeInicial.value = "";
		form.descricaoLocalidadeInicial.value = "";
		form.idLocalidadeFinal.value = "";
		form.descricaoLocalidadeFinal.value = "";
   		form.codigoSetorComercialInicial.value = "";
   		form.descricaoSetorComercialInicial.value = "";
   		form.codigoSetorComercialFinal.value = "";
   		form.descricaoSetorComercialFinal.value = "";
   		form.numeroQuadraInicial.value = "";

   
   		var quadraInicialInexistente = document.getElementById("alertaQuadraInicialInexistente");
		
		if (quadraInicialInexistente != null){

			quadraInicialInexistente.innerHTML = "";
		}

		var quadraFinalInexistente = document.getElementById("alertaQuadraFinalInexistente");
		
		if (quadraFinalInexistente != null){

			quadraFinalInexistente.innerHTML = "";
		}


   		form.numeroQuadraFinal.value = "";
   		form.indicadorSituacaoCobrancaAdministrativa[2].checked = true;
	}

	function limparComando() {


    	var form = document.forms[0];

    	form.idComando.value = "";
    	form.descricaoComando.value = ""; 
    	form.descricaoComando.style.color = "#000000";   	
  	}

	function limparImovel() {

    	var form = document.forms[0];

    	form.idImovel.value = "";
    	form.inscricaoImovel.value = "";
    	form.inscricaoImovel.style.color = "#000000";  
    	
     	form.action = "/gsan/exibirFiltrarImovelCobrancaAdministrativaAction.do?reload=true";
		form.submit();

    	
  	}

	function limparCliente() {

    	var form = document.forms[0];

    	form.idCliente.value = "";
    	form.nomeCliente.value = "";
    	form.inscricaoImovel.style.color = "#000000";

    	form.action = "/gsan/exibirFiltrarImovelCobrancaAdministrativaAction.do?reload=true";
		form.submit();
  	}

	function replicaDados(campoOrigem, campoDestino){
		campoDestino.value = campoOrigem.value;
	}

	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	    var form = document.forms[0];
	    
	    if (tipoConsulta == 'imovel') {

	      form.idImovel.value = codigoRegistro;
	      form.inscricaoImovel.value = descricaoRegistro;
	      form.inscricaoImovel.style.color = "#000000";
	    
	    } else if (tipoConsulta == 'cliente') {

		    form.idCliente.value = codigoRegistro;
		    form.nomeCliente.value = descricaoRegistro;
	      	form.nomeCliente.style.color = "#000000";
	    
   	    } else if (tipoConsulta == 'cobrancaAcaoAtividadeComando') {

	      	form.descricaoComando.style.color = "#000000";
	      	form.action = "/gsan/exibirFiltrarImovelCobrancaAdministrativaAction.do?idComandoValidar=" + codigoRegistro;
			form.submit();
	    
   	    }else if(tipoConsulta == 'localidadeOrigem') {		
      		
   	    	form.idLocalidadeInicial.value = codigoRegistro;
		    form.descricaoLocalidadeInicial.value = descricaoRegistro;
	      	form.descricaoLocalidadeInicial.style.color = "#000000";

	      	form.idLocalidadeFinal.value = codigoRegistro;
		    form.descricaoLocalidadeFinal.value = descricaoRegistro;
	      	form.descricaoLocalidadeFinal.style.color = "#000000";
	      	
	  	}else if(tipoConsulta == 'localidadeDestino') {		
      		
   	    	form.idLocalidadeFinal.value = codigoRegistro;
		    form.descricaoLocalidadeFinal.value = descricaoRegistro;
	      	form.descricaoLocalidadeFinal.style.color = "#000000";
	      	
	  	}else if(form.campoOrigem.value == 'numeroQuadraInicial') {		

			valorAnterior = form.numeroQuadraInicial.value;
			
      		form.numeroQuadraInicial.value = codigoRegistro;
	  	
	  		if((form.numeroQuadraFinal.value == '') 
	  		  		|| (form.numeroQuadraFinal.value == valorAnterior)) {

	  			form.numeroQuadraFinal.value = codigoRegistro;  		
	  		}

		} else if(form.campoOrigem.value == 'numeroQuadraFinal') {		
      		
      		form.numeroQuadraFinal.value = codigoRegistro;
      		
	  	} 

		form.campoOrigem.value = "";
	}

	function validaArquivo(){

		var form = document.forms[0];
		form.action = "/gsan/exibirFiltrarImovelCobrancaAdministrativaAction.do?reload=true";
		form.submit();
	}
	
	function limparQuandoReplicar(tipo){

		var form = document.FiltrarImovelCobrancaAdministrativaActionForm;
	
		switch (tipo){

			case 1:
		 	   		form.codigoSetorComercialInicial.value = "";
	 		   		form.descricaoSetorComercialInicial.value = "";
	 	       		form.codigoSetorComercialFinal.value = "";
	 		   		form.descricaoSetorComercialFinal.value = "";

		 		   	var quadraInicialInexistente = document.getElementById("alertaQuadraInicialInexistente");
		 			
		 			if (quadraInicialInexistente != null){
	
		 				quadraInicialInexistente.innerHTML = "";
		 			}
	
		 			var quadraFinalInexistente = document.getElementById("alertaQuadraFinalInexistente");
		 			
		 			if (quadraFinalInexistente != null){
	
		 				quadraFinalInexistente.innerHTML = "";
		 			}
	 		   		
	 		   		form.numeroQuadraFinal.value = "";
	 		   		form.numeroQuadraInicial.value="";
	  	    break;   
			case 2:

					var quadraInicialInexistente = document.getElementById("alertaQuadraInicialInexistente");
					
					if (quadraInicialInexistente != null){
	
						quadraInicialInexistente.innerHTML = "";
					}
	
					var quadraFinalInexistente = document.getElementById("alertaQuadraFinalInexistente");
					
					if (quadraFinalInexistente != null){
	
						quadraFinalInexistente.innerHTML = "";
					}
				 	form.numeroQuadraInicial.value = "";
		 		   	form.numeroQuadraFinal.value = "";
	  	    break;
		   default:
	          break;
		}
	}

	function limparComBorracha(tipo){

		var form = document.FiltrarImovelCobrancaAdministrativaActionForm;
	
		switch (tipo){

			case 1:
					form.idLocalidadeInicial.value = "";
					form.descricaoLocalidadeInicial.value = "";
					form.idLocalidadeFinal.value = "";
					form.descricaoLocalidadeFinal.value = "";
		 	   		form.codigoSetorComercialInicial.value = "";
	 		   		form.descricaoSetorComercialInicial.value = "";
	 	       		form.codigoSetorComercialFinal.value = "";
	 		   		form.descricaoSetorComercialFinal.value = "";
	 		   		form.numeroQuadraInicial.value = "";
	 		   		form.numeroQuadraFinal.value = "";

		 		   	var quadraInicialInexistente = document.getElementById("alertaQuadraInicialInexistente");
		 			
		 			if (quadraInicialInexistente != null){
	
		 				quadraInicialInexistente.innerHTML = "";
		 			}
	
		 			var quadraFinalInexistente = document.getElementById("alertaQuadraFinalInexistente");
		 			
		 			if (quadraFinalInexistente != null){
	
		 				quadraFinalInexistente.innerHTML = "";
		 			}

		 			form.action = "/gsan/exibirFiltrarImovelCobrancaAdministrativaAction.do?reload=true";
					form.submit();
	 		   		
	  	    break;
			case 2:
				
					form.idLocalidadeFinal.value = "";
					form.descricaoLocalidadeFinal.value = "";
		 	   		form.codigoSetorComercialInicial.value = "";
	 		   		form.descricaoSetorComercialInicial.value = "";
	 	       		form.codigoSetorComercialFinal.value = "";
	 		   		form.descricaoSetorComercialFinal.value = "";
	 		   		form.numeroQuadraInicial.value = "";
	 		   		form.numeroQuadraFinal.value = "";

		 		   	var quadraInicialInexistente = document.getElementById("alertaQuadraInicialInexistente");
		 			
		 			if (quadraInicialInexistente != null){
	
		 				quadraInicialInexistente.innerHTML = "";
		 			}
	
		 			var quadraFinalInexistente = document.getElementById("alertaQuadraFinalInexistente");
		 			
		 			if (quadraFinalInexistente != null){
	
		 				quadraFinalInexistente.innerHTML = "";
		 			}

		 			form.action = "/gsan/exibirFiltrarImovelCobrancaAdministrativaAction.do?reload=true";
					form.submit();
  	    	break;
			case 3:

		 	   		form.codigoSetorComercialInicial.value = "";
	 		   		form.descricaoSetorComercialInicial.value = "";
	 	       		form.codigoSetorComercialFinal.value = "";
	 		   		form.descricaoSetorComercialFinal.value = "";
	 		   		form.numeroQuadraInicial.value = "";
	 		   		form.numeroQuadraFinal.value = "";

		 		   	var quadraInicialInexistente = document.getElementById("alertaQuadraInicialInexistente");
		 			
		 			if (quadraInicialInexistente != null){
	
		 				quadraInicialInexistente.innerHTML = "";
		 			}
	
		 			var quadraFinalInexistente = document.getElementById("alertaQuadraFinalInexistente");
		 			
		 			if (quadraFinalInexistente != null){
	
		 				quadraFinalInexistente.innerHTML = "";
		 			}

		 			form.action = "/gsan/exibirFiltrarImovelCobrancaAdministrativaAction.do?reload=true";
					form.submit();
	    	break;
			case 4:
				
	 	       		form.codigoSetorComercialFinal.value = "";
	 		   		form.descricaoSetorComercialFinal.value = "";
	 		   		form.numeroQuadraInicial.value = "";
	 		   		form.numeroQuadraFinal.value = "";
		 		   	var quadraInicialInexistente = document.getElementById("alertaQuadraInicialInexistente");
		 			
		 			if (quadraInicialInexistente != null){
	
		 				quadraInicialInexistente.innerHTML = "";
		 			}
	
		 			var quadraFinalInexistente = document.getElementById("alertaQuadraFinalInexistente");
		 			
		 			if (quadraFinalInexistente != null){
	
		 				quadraFinalInexistente.innerHTML = "";
		 			}

		 			form.action = "/gsan/exibirFiltrarImovelCobrancaAdministrativaAction.do?reload=true";
					form.submit();
	 		   		
	    	break;   
		   default:
	          break;
		}
	}
    
    function validaTodosPeriodos(form) {

    	if (comparaData(form.periodoInclusaoInicial.value, '>', form.periodoInclusaoFinal.value)){

			alert('Data Final do Período de Inclusão é anterior à Data Inicial do Período de Inclusão');
			return false;

		} else if (comparaData(form.periodoRetiradaInicial.value, '>', form.periodoRetiradaFinal.value)){

			alert('Data Final do Período da Geração é anterior à Data Inicial do Período da Geração');
			return false;

		}

		return true;
    }

	function validaForm(){
		var form = document.forms[0];
		
		if(form.numeroRA.value != null && form.numeroRA.value != ''){

			limparDocumentoCobranca();

   	    	form.documentoCobranca.readOnly = true;
   	    	form.documentoCobranca.className = 'desabilitar';

    		form.descricaoDocumentoCobranca.readOnly = true;
    		form.descricaoDocumentoCobranca.className = 'desabilitar';

		}else if(form.documentoCobranca.value != null && form.documentoCobranca.value != ''){

			limparPesquisaRA();
			
			form.numeroRA.readOnly = true;
			form.numeroRA.className = 'desabilitar';
   	    	
			form.descricaoRA.readOnly = true;
			form.descricaoRA.className = 'desabilitar';
   		
		}

		if (form.desabilitaCampos.value == null || form.desabilitaCampos.value == ''
   	   		|| form.desabilitaCampos.value == false){
   	   		
			if(	form.situacaoOrdemServico.value == '1' || 
				form.situacaoOrdemServico.value == '3' ||
				form.situacaoOrdemServico.value == '4' ){
	
				limparPeriodoEncerramento();
				
				form.periodoEncerramentoInicial.readOnly = true;
				form.periodoEncerramentoInicial.className = 'desabilitar';
				
				form.periodoEncerramentoFinal.readOnly = true;
				form.periodoEncerramentoFinal.className = 'desabilitar';
			
			}else{
	
				form.periodoEncerramentoInicial.removeAttribute('readOnly');
				form.periodoEncerramentoInicial.className = '';
				
				form.periodoEncerramentoFinal.removeAttribute('readOnly');
				form.periodoEncerramentoFinal.className = '';
			
			}
	
			if (form.programada[0].checked != true) {
				desabilitaProgramacaoEquipe();
			}
		}
	}
	 
	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){
		
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
	
	function MoveSelectedDataFromMenu1TO2(form, object, selectedObject){

		var form = document.forms[0];
		
		if (form.tipoServico.value != '-1') {

			MoverDadosSelectMenu1PARAMenu2(form, object, selectedObject);
		}
	}
	
	function MoveSelectedDataFromMenu2TO1(form, object, selectedObject){
		var form = document.forms[0];
		
		if (form.tipoServico.value != '-1') {

			MoverDadosSelectMenu2PARAMenu1(form, object, selectedObject);
		}
	}

	function limparPesquisaQuadra(inicial) {
	    var form = document.forms[0];

	    if (inicial == 'true'){

	      	form.numeroQuadraInicial.value = "";

			var quadraInicialInexistente = document.getElementById("alertaQuadraInicialInexistente");
		
			if (quadraInicialInexistente != null){

				quadraInicialInexistente.innerHTML = "";
			}

			form.numeroQuadraFinal.value = "";

			var quadraFinalInexistente = document.getElementById("alertaQuadraFinalInexistente");
		
			if (quadraFinalInexistente != null){

				quadraFinalInexistente.innerHTML = "";
			} 
	    }else{

	    	form.numeroQuadraFinal.value = "";

			var quadraFinalInexistente = document.getElementById("alertaQuadraFinalInexistente");
		
			if (quadraFinalInexistente != null){

				quadraFinalInexistente.innerHTML = "";
			} 
		}

		form.action = "/gsan/exibirFiltrarImovelCobrancaAdministrativaAction.do?reload=true";
		form.submit();
		
	  }

	 function pesquisaLupaComDependencia(url, idDependencia, nomeMSG, altura, largura, campo){

		 document.forms[0].campoOrigem.value = campo;

		 abrirPopupDependencia(url, idDependencia, nomeMSG, altura, largura);
	 }



	 function chamarReloadSemParametro(){
		 var form = document.FiltrarImovelCobrancaAdministrativaActionForm;

		   if(validaTodosPeriodos(form)){
				
			   	enviarSelectMultiplo('FiltrarImovelCobrancaAdministrativaActionForm','idsEmpresaSelecionadas');
				enviarSelectMultiplo('FiltrarImovelCobrancaAdministrativaActionForm','idsGerenciaRegionalSelecionadas');
				enviarSelectMultiplo('FiltrarImovelCobrancaAdministrativaActionForm','idsUnidadeNegocioSelecionadas');
				enviarSelectMultiplo('FiltrarImovelCobrancaAdministrativaActionForm','idsCategoriaSelecionadas');
				enviarSelectMultiplo('FiltrarImovelCobrancaAdministrativaActionForm','idsSubcategoriaSelecionadas');
				enviarSelectMultiplo('FiltrarImovelCobrancaAdministrativaActionForm','idsLigacaoAguaSituacaoSelecionadas');
				enviarSelectMultiplo('FiltrarImovelCobrancaAdministrativaActionForm','idsLigacaoEsgotoSituacaoSelecionadas');
				enviarSelectMultiplo('FiltrarImovelCobrancaAdministrativaActionForm','idsMotivoRetiradaSelecionados');
		   }

			form.action = "/gsan/exibirFiltrarImovelCobrancaAdministrativaAction.do?reload=true";
			form.submit();

	 }

	function verificarLimpar(limpar){
	
		if (limpar != null && limpar == 'true'){
	
			limparFormSemSubmit();
		}
	}

	 function extendeTabela(tabela,display){
			var form = document.forms[0];

			if(display){
	 			eval('layerHide'+tabela).style.display = 'none';
	 			eval('layerShow'+tabela).style.display = 'block';
			}else{
				eval('layerHide'+tabela).style.display = 'block';
	 			eval('layerShow'+tabela).style.display = 'none';
			}

			if (tabela== 'DadosLocalizacaoGeografica'){

				if(display){

					form.action = "/gsan/exibirFiltrarImovelCobrancaAdministrativaAction.do?reload=true&mostrarCampos=1";
				}else{

					form.action = "/gsan/exibirFiltrarImovelCobrancaAdministrativaAction.do?reload=true&mostrarCampos=2";
				}
				
				form.submit();
			}else{

				if(display){

					form.action = "/gsan/exibirFiltrarImovelCobrancaAdministrativaAction.do?reload=true&mostrarCampos=3";
				}else{

					form.action = "/gsan/exibirFiltrarImovelCobrancaAdministrativaAction.do?reload=true&mostrarCampos=4";
				}
				form.submit();
			}
		}

	function extendeTabelaComVerificacao(mostrarCampos){

		var form = document.forms[0];

		if (mostrarCampos == '1'){

 			eval('layerHideDadosLocalizacaoGeografica').style.display = 'none';
 			eval('layerShowDadosLocalizacaoGeografica').style.display = 'block';
			
		}else if (mostrarCampos == '2'){

			eval('layerHideDadosLocalizacaoGeografica').style.display = 'block';
 			eval('layerShowDadosLocalizacaoGeografica').style.display = 'none';
 			
		}else if (mostrarCampos == '3'){

 			eval('layerHideDadosAdicionais').style.display = 'none';
 			eval('layerShowDadosAdicionais').style.display = 'block';
			
		}else if (mostrarCampos == '4'){

			eval('layerHideDadosAdicionais').style.display = 'block';
 			eval('layerShowDadosAdicionais').style.display = 'none';
		}
	}
				
</script>

</head>

<body leftmargin="5" topmargin="5" onload="verificarLimpar('${requestScope.limpar}');extendeTabelaComVerificacao('${sessionScope.mostrarCampos}')">

<html:form action="/filtrarImovelCobrancaAdministrativaAction" name="FiltrarImovelCobrancaAdministrativaActionForm" 
	type="gcom.gui.cobranca.cobrancaadministrativa.FiltrarImovelCobrancaAdministrativaActionForm" 
	enctype="multipart/form-data"
	method="post" >



<%@ include file="/jsp/util/cabecalho.jsp"%>

<input type="hidden" name="campoOrigem" value=""/>
<html:hidden property="arquivoCarregado"/>

<table width="770" border="0" cellspacing="4" cellpadding="0">
	  <tr>
	    <td valign="top" class="leftcoltext">
	      <div align="left">
	
			<%@ include file="/jsp/util/informacoes_usuario.jsp"%>
	
			<%@ include file="/jsp/util/mensagens.jsp"%>
	
	      	</div>
	      	</td>
			<td width="600" valign="top" class="centercoltext">
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
					<td class="parabg">Filtrar Imóvel Cobrança Administrativa</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td>Para consultar Imóvel(is) em Cobrança Administrativa, informe os dados abaixo:</td>
				</tr>
				<tr>
					<td>	
						<table>
							<tr>
								<td><strong>Comando de Cobrança:</strong></td>
								
								<td>
									
									<html:text maxlength="9" 
										property="idComando" 
										size="9"
										onkeyup="javascript:verificaNumeroInteiro(this);"
										onkeypress="validaEnterComMensagem(event, 'exibirFiltrarImovelCobrancaAdministrativaAction.do?objetoConsulta=4','idComando','Comando de Cobrança');" />
										
										<a href="javascript:chamarPopup('exibirPesquisarComandoAcaoCobrancaAction.do', 'comandoCobranca', null, null, 600, 730, '', document.forms[0].idComando);">
											
											<img width="23" 
												height="21" 
												border="0"
												src="<bean:message key="caminho.imagens"/>pesquisa.gif"
												title="Pesquisar Comando" /></a> 
			
										<logic:present name="comandoExistente" scope="request">
											
											<html:text property="descricaoComando" 
												size="40"
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000" />
										</logic:present> 
			
										<logic:present name="comandoInexistente" scope="request">
											
											<html:text property="descricaoComando" 
												size="40"
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: red" />
												
										</logic:present>
			
										<a href="javascript:limparComando();"> 
											<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
												border="0" 
												title="Apagar" /></a>
									</td>
							</tr>
							
							<tr>
								<td width="110">
									<strong>Empresa de Cobrança:</strong>					
								</td>
								<td>
								<table width="100%" border=0 cellpadding=0 cellspacing=0 align="left">
									<tr>
										<td width="175">
										
											<div align="left"><strong>Disponíveis</strong></div>
			
											<html:select property="idsEmpresa" 
												size="6" 
												multiple="true" 
												style="width:190px">
											<logic:notEmpty name="colecaoEmpresa">	
												<html:options collection="colecaoEmpresa" 
													labelProperty="descricao" 
													property="id" />
											</logic:notEmpty>
											</html:select>
										</td>
			
										<td width="5"><br>
											<table width="50" align="center">
												<tr>
													<td align="center">
														<input type="button" 
															class="bottonRightCol"
															onclick="javascript:MoverTodosDadosSelectMenu1PARAMenu2('FiltrarImovelCobrancaAdministrativaActionForm', 'idsEmpresa', 'idsEmpresaSelecionadas');chamarReloadSemParametro();"
															value=" &gt;&gt; ">
													</td>
												</tr>
				
												<tr>
													<td align="center">
														<input type="button" 
															class="bottonRightCol"
															onclick="javascript:MoverDadosSelectMenu1PARAMenu2('FiltrarImovelCobrancaAdministrativaActionForm', 'idsEmpresa', 'idsEmpresaSelecionadas');chamarReloadSemParametro();"
															value=" &nbsp;&gt;  ">
													</td>
												</tr>
				
												<tr>
													<td align="center">
														<input type="button" 
															class="bottonRightCol"
															onclick="javascript:MoverDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsEmpresa', 'idsEmpresaSelecionadas');chamarReloadSemParametro();"
															value=" &nbsp;&lt;  ">
													</td>
												</tr>
				
												<tr>
													<td align="center">
														<input type="button" 
															class="bottonRightCol"
															onclick="javascript:MoverTodosDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsEmpresa', 'idsEmpresaSelecionadas');chamarReloadSemParametro();"
															value=" &lt;&lt; ">
													</td>
												</tr>
											</table>
										</td>
			
										<td>
											<div align="left">
												<strong>Selecionadas</strong>
											</div>
											
											<html:select property="idsEmpresaSelecionadas" 
												size="6" 
												multiple="true" 
												style="width:190px">
												
												<logic:empty name="colecaoIdsEmpresaSelecionadas">	
														<html:option value=" ">&nbsp;</html:option>
												</logic:empty>	
											   				
												 <logic:notEmpty name="colecaoIdsEmpresaSelecionadas">	
													<html:options collection="colecaoIdsEmpresaSelecionadas" 
													labelProperty="descricao" 
													property="id" />
												</logic:notEmpty>
											
											</html:select>
										</td>
									</tr>
								</table>
								</td>
							</tr>
							<tr>
			             		<td height="10" colspan="3"> 
				             		<div align="center"> 
				                 		<hr>
				               		</div>
				               		<div align="center"> </div>
			               		</td>
			           		</tr>
							<tr>
								<td><strong>Im&oacute;vel:</strong></td>
													
								<td>
								
								<logic:present name="habilitaImovel" scope="request">
 									<logic:equal name="habilitaImovel" value="true">
       									<html:text maxlength="9" 
										property="idImovel" 
										size="9"
										onkeyup="javascript:verificaNumeroInteiro(this);"
										onkeypress="validaEnterComMensagem(event, 'exibirFiltrarImovelCobrancaAdministrativaAction.do?reload=true','idImovel','Imóvel');"
										onblur="javascript:chamarReloadSemParametro();"/>
												
										
										<a href="javascript:chamarPopup('exibirPesquisarImovelAction.do', 'imovel', null, null, 275, 480, '', document.forms[0].idImovel);chamarReloadSemParametro();">
										<img width="23" 
											height="21" 
											border="0"
											src="<bean:message key="caminho.imagens"/>pesquisa.gif"
											title="Pesquisar Imóvel" /></a> 
			
										<logic:present name="imovelExistente" scope="request">
											<html:text property="inscricaoImovel" 
												size="30"
												maxlength="30" 
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000" />
										</logic:present> 
			
										<logic:present name="imovelInexistente" scope="request">
											<html:text property="inscricaoImovel" 
												size="30"
												maxlength="30" 
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: red" />
										</logic:present>
										
										<a href="javascript:limparImovel();"> 
										<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
											border="0" 
											title="Apagar" />
										</a>
									</logic:equal>

									<logic:equal name="habilitaImovel" value="false">
										<html:text maxlength="9" 
										property="idImovel" 
										size="9" disabled="true"/>
									
											<html:text property="inscricaoImovel" 
												size="30"
												maxlength="30" 
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: red" />
										
									</logic:equal>

								</logic:present> 

								<logic:notPresent name="habilitaImovel" scope="request">
										<html:text maxlength="9" 
										property="idImovel" 
										size="9"
										onkeyup="javascript:verificaNumeroInteiro(this);"
										onkeypress="validaEnterComMensagem(event, 'exibirFiltrarImovelCobrancaAdministrativaAction.do?reload=true','idImovel','Imóvel');"
										onblur="javascript:chamarReloadSemParametro();"/>
												
										
										<a href="javascript:chamarPopup('exibirPesquisarImovelAction.do', 'imovel', null, null, 275, 480, '', document.forms[0].idImovel);chamarReloadSemParametro();">
										<img width="23" 
											height="21" 
											border="0"
											src="<bean:message key="caminho.imagens"/>pesquisa.gif"
											title="Pesquisar Imóvel" /></a> 
			
										<logic:present name="imovelExistente" scope="request">
											<html:text property="inscricaoImovel" 
												size="30"
												maxlength="30" 
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000" />
										</logic:present> 
			
										<logic:present name="imovelInexistente" scope="request">
											<html:text property="inscricaoImovel" 
												size="30"
												maxlength="30" 
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: red" />
										</logic:present>
										
										<a href="javascript:limparImovel();"> 
										<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
											border="0" 
											title="Apagar" />
										</a>
								</logic:notPresent> 
								
										
								</td>
							</tr>
							
							<tr>
								<td><strong>Cliente:</strong></td>
								
								<td>
									<logic:present name="habilitaCliente" scope="request">
 										<logic:equal name="habilitaCliente" value="true">
      									 <html:text maxlength="9" 
										property="idCliente" 
										size="9"
										onkeyup="javascript:verificaNumeroInteiro(this);"
										onkeypress="validaEnterComMensagem(event, 'exibirFiltrarImovelCobrancaAdministrativaAction.do?reload=true','idCliente','Cliente');"
										onblur="javascript:chamarReloadSemParametro();" />
										
										<a href="javascript:chamarPopup('exibirPesquisarClienteAction.do', 'cliente', null, null, 275, 480, '', document.forms[0].idCliente);chamarReloadSemParametro();">
										<img width="23" 
											height="21" 
											border="0"
											src="<bean:message key="caminho.imagens"/>pesquisa.gif"
											title="Pesquisar Cliente" /></a> 			
			
										<logic:present name="clienteExistente" scope="request">
											<html:text property="nomeCliente" 
												size="45"
												maxlength="45" 
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000" />
										</logic:present> 
			
										<logic:present name="clienteInexistente" scope="request">
											<html:text property="nomeCliente" 
												size="45"
												maxlength="45" 
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: red" />
										</logic:present>
										
										<a href="javascript:limparCliente();"> 
											<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
												border="0" 
												title="Apagar" />
										</a>		
									    </logic:equal>

										<logic:equal name="habilitaCliente" value="false">
											<html:text maxlength="9" property="idCliente" size="9" disabled="true"/>
											<html:text property="nomeCliente" 
												size="45"
												maxlength="45" 
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: red" />
									
									    </logic:equal>

									</logic:present> 

									<logic:notPresent name="habilitaCliente" scope="request">
										<html:text maxlength="9" 
										property="idCliente" 
										size="9"
										onkeyup="javascript:verificaNumeroInteiro(this);"
										onkeypress="validaEnterComMensagem(event, 'exibirFiltrarImovelCobrancaAdministrativaAction.do?reload=true','idCliente','Cliente');"
										onblur="javascript:chamarReloadSemParametro();"/>
										
										<a href="javascript:chamarPopup('exibirPesquisarClienteAction.do', 'cliente', null, null, 275, 480, '', document.forms[0].idCliente);chamarReloadSemParametro();">
										<img width="23" 
											height="21" 
											border="0"
											src="<bean:message key="caminho.imagens"/>pesquisa.gif"
											title="Pesquisar Cliente" /></a> 			
			
										<logic:present name="clienteExistente" scope="request">
											<html:text property="nomeCliente" 
												size="45"
												maxlength="45" 
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000" />
										</logic:present> 
			
										<logic:present name="clienteInexistente" scope="request">
											<html:text property="nomeCliente" 
												size="45"
												maxlength="45" 
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: red" />
										</logic:present>
										
										<a href="javascript:limparCliente();"> 
											<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
												border="0" 
												title="Apagar" />
										</a>		
									</logic:notPresent> 
									
									
									
										
									</td>
							</tr>
							
							<tr>
								<td width="20%"><strong> Arquivo de Imóveis:</strong>
								</td>
								
								<logic:present name="habilitaArquivo" scope="request">
 									<logic:equal name="habilitaArquivo" value="true">
       									<td>
											<html:file property="arquivoMatricula" accept="text" onchange="validaArquivo();"></html:file>
												<logic:notEmpty  name="arquivo" scope="session">
													<bean:write name="arquivo" property="fileName" scope="session"/>
												</logic:notEmpty>
											<logic:notEmpty  name="FiltrarImovelCobrancaAdministrativaActionForm" property="enderecoArquivoDownload">
												<br/><a href="exibirFiltrarImovelCobrancaAdministrativaAction.do?download=true&reload=true">
												<bean:write name="FiltrarImovelCobrancaAdministrativaActionForm" property="arquivoDownload"></bean:write> </a>
											</logic:notEmpty>
											<logic:empty  name="FiltrarImovelCobrancaAdministrativaActionForm" property="enderecoArquivoDownload">
												<br/><bean:write name="FiltrarImovelCobrancaAdministrativaActionForm" property="arquivoDownload"></bean:write>
											</logic:empty>
										</td>
								    </logic:equal>

									<logic:equal name="habilitaArquivo" value="false">
									<td>
										<html:file property="arquivoMatricula" accept="text" disabled="true"></html:file>											
									</td>
									</logic:equal>

						       </logic:present> 

							 <logic:notPresent name="habilitaArquivo" scope="request">
								<td>
									<html:file property="arquivoMatricula" accept="text" onchange="validaArquivo();"></html:file>
									<logic:notEmpty  name="arquivo" scope="session">
										<bean:write name="arquivo" property="fileName" scope="session"/>
									</logic:notEmpty>
									<logic:notEmpty  name="FiltrarImovelCobrancaAdministrativaActionForm" property="enderecoArquivoDownload">
										<br/><a href="exibirFiltrarImovelCobrancaAdministrativaAction.do?download=true&reload=true">
											<bean:write name="FiltrarImovelCobrancaAdministrativaActionForm" property="arquivoDownload"></bean:write> </a>
									</logic:notEmpty>
									<logic:empty  name="FiltrarImovelCobrancaAdministrativaActionForm" property="enderecoArquivoDownload">
										<br/><bean:write name="FiltrarImovelCobrancaAdministrativaActionForm" property="arquivoDownload"></bean:write>
									</logic:empty>
								</td>
							 </logic:notPresent> 
								
							
							</tr>
							
							<tr>
								<td colspan="2">
								<div id="layerHideDadosLocalizacaoGeografica" style="display: block">
								<table width="100%" border="0" bgcolor="#99CCFF">
									<tr bgcolor="#99CCFF">
										<td align="center"><span class="style2"> <a
											href="javascript:extendeTabela('DadosLocalizacaoGeografica',true);" /><b>Dados
										de Localização Geográfica</b></a></span></td>
									</tr>
								</table>
								</div>
			
								<div id="layerShowDadosLocalizacaoGeografica" style="display: none">
								<table width="100%" border="0" bgcolor="#99CCFF">
									<tr bgcolor="#99CCFF">
										<td align="center"><span class="style2"> <a
											href="javascript:extendeTabela('DadosLocalizacaoGeografica',false);" /><b>Dados
										de Localização Geográfica</b></a></span></td>
									</tr>
									<tr bgcolor="#cbe5fe">
										<td>
											<table width="100%">
											
											<tr>
												<td width="110">
													<strong>Gerência Regional:</strong>					
												</td>
												<td>
												<table width="100%" border=0 cellpadding=0 cellspacing=0 align="left">
													<tr>
														<td width="175">
														
															<div align="left"><strong>Disponíveis</strong></div>
							
															<logic:present name="habilitaGerenciaRegional" scope="request">
															
															         	<logic:equal name="habilitaGerenciaRegional" value="true">
																			  <html:select property="idsGerenciaRegional" 
																				size="6" 
																				multiple="true" 
																				style="width:190px" >
																				<logic:notEmpty name="colecaoGerenciaRegional">	
																				<html:options collection="colecaoGerenciaRegional" 
																				labelProperty="nome" 
																				property="id" />
																				</logic:notEmpty>
																			</html:select>
																		</logic:equal>
																		
																		<logic:equal name="habilitaGerenciaRegional" value="false">
																			<html:select property="idsGerenciaRegional" 
																				size="6" 
																				multiple="true" 
																				style="width:190px" disabled="true">
																				<logic:notEmpty name="colecaoGerenciaRegional">	
																				<html:options collection="colecaoGerenciaRegional" 
																				labelProperty="nome" 
																				property="id" />
																				</logic:notEmpty>
																			</html:select>
																		</logic:equal>
															
																				
															</logic:present> 
															
															<logic:notPresent name="habilitaGerenciaRegional" scope="request">
															
															   <html:select property="idsGerenciaRegional" 
																size="6" 
																multiple="true" 
																style="width:190px">
																<logic:notEmpty name="colecaoGerenciaRegional">	
																<html:options collection="colecaoGerenciaRegional" 
																	labelProperty="nome" 
																	property="id" />
																</logic:notEmpty>
															</html:select>
																				
															</logic:notPresent> 
																
														</td>
							
														<td width="5"><br>
															<table width="50" align="center">
															
																<logic:present name="habilitaGerenciaRegional" scope="request">
																
																	<logic:equal name="habilitaGerenciaRegional" value="true">
																	<tr>
																	<td align="center">
																		<input type="button" 
																			class="bottonRightCol"
																			onclick="javascript:MoverTodosDadosSelectMenu1PARAMenu2('FiltrarImovelCobrancaAdministrativaActionForm', 'idsGerenciaRegional', 'idsGerenciaRegionalSelecionadas');chamarReloadSemParametro();"
																			value=" &gt;&gt; " >
																	</td>
																</tr>
								
																<tr>
																	<td align="center">
																		<input type="button" 
																			class="bottonRightCol"
																			onclick="javascript:MoverDadosSelectMenu1PARAMenu2('FiltrarImovelCobrancaAdministrativaActionForm', 'idsGerenciaRegional', 'idsGerenciaRegionalSelecionadas');chamarReloadSemParametro();"
																			value=" &nbsp;&gt;  ">
																	</td>
																</tr>
								
																<tr>
																	<td align="center">
																		<input type="button" 
																			class="bottonRightCol"
																			onclick="javascript:MoverDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsGerenciaRegional', 'idsGerenciaRegionalSelecionadas');chamarReloadSemParametro();"
																			value=" &nbsp;&lt;  ">
																	</td>
																</tr>
								
																<tr>
																	<td align="center">
																		<input type="button" 
																			class="bottonRightCol"
																			onclick="javascript:MoverTodosDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsGerenciaRegional', 'idsGerenciaRegionalSelecionadas');chamarReloadSemParametro();"
																			value=" &lt;&lt; ">
																	</td>
																</tr>
																	
																	</logic:equal>
																	
																
																	<logic:equal name="habilitaGerenciaRegional" value="false">
																	<tr>
																	<td align="center">
																		<input type="button" 
																			class="bottonRightCol"
																			onclick="javascript:MoverTodosDadosSelectMenu1PARAMenu2('FiltrarImovelCobrancaAdministrativaActionForm', 'idsGerenciaRegional', 'idsGerenciaRegionalSelecionadas');"
																			value=" &gt;&gt; " disabled="disabled">
																	</td>
																</tr>
								
																<tr>
																	<td align="center">
																		<input type="button" 
																			class="bottonRightCol"
																			onclick="javascript:MoverDadosSelectMenu1PARAMenu2('FiltrarImovelCobrancaAdministrativaActionForm', 'idsGerenciaRegional', 'idsGerenciaRegionalSelecionadas');"
																			value=" &nbsp;&gt;  " disabled="disabled">
																	</td>
																</tr>
								
																<tr>
																	<td align="center">
																		<input type="button" 
																			class="bottonRightCol"
																			onclick="javascript:MoverDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsGerenciaRegional', 'idsGerenciaRegionalSelecionadas');"
																			value=" &nbsp;&lt;  " disabled="disabled">
																	</td>
																</tr>
								
																<tr>
																	<td align="center">
																		<input type="button" 
																			class="bottonRightCol"
																			onclick="javascript:MoverTodosDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsGerenciaRegional', 'idsGerenciaRegionalSelecionadas');"
																			value=" &lt;&lt; " disabled="disabled">
																	</td>
																</tr>
																	
																	
																	</logic:equal>
																
																</logic:present>
																
																<logic:notPresent name="habilitaGerenciaRegional" scope="request">
																<tr>
																	<td align="center">
																		<input type="button" 
																			class="bottonRightCol"
																			onclick="javascript:MoverTodosDadosSelectMenu1PARAMenu2('FiltrarImovelCobrancaAdministrativaActionForm', 'idsGerenciaRegional', 'idsGerenciaRegionalSelecionadas');chamarReloadSemParametro();"
																			value=" &gt;&gt; ">
																	</td>
																</tr>
								
																<tr>
																	<td align="center">
																		<input type="button" 
																			class="bottonRightCol"
																			onclick="javascript:MoverDadosSelectMenu1PARAMenu2('FiltrarImovelCobrancaAdministrativaActionForm', 'idsGerenciaRegional', 'idsGerenciaRegionalSelecionadas');chamarReloadSemParametro();"
																			value=" &nbsp;&gt;  ">
																	</td>
																</tr>
								
																<tr>
																	<td align="center">
																		<input type="button" 
																			class="bottonRightCol"
																			onclick="javascript:MoverDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsGerenciaRegional', 'idsGerenciaRegionalSelecionadas');chamarReloadSemParametro();"
																			value=" &nbsp;&lt;  ">
																	</td>
																</tr>
								
																<tr>
																	<td align="center">
																		<input type="button" 
																			class="bottonRightCol"
																			onclick="javascript:MoverTodosDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsGerenciaRegional', 'idsGerenciaRegionalSelecionadas');chamarReloadSemParametro();"
																			value=" &lt;&lt; ">
																	</td>
																</tr>
																</logic:notPresent>
															
																
															</table>
														</td>
							
														<td>
															<div align="left">
																<strong>Selecionadas</strong>
															</div>
															
															<html:select property="idsGerenciaRegionalSelecionadas" 
																size="6" 
																multiple="true" 
																style="width:190px" >
																
																 <logic:empty name="colecaoGerenciaRegionalSelecionadas">	
																		<html:option value=" ">&nbsp;</html:option>
															   	</logic:empty>			
																
																  <logic:notEmpty name="colecaoGerenciaRegionalSelecionadas">	
																	<html:options collection="colecaoGerenciaRegionalSelecionadas" 
																	labelProperty="nome" 
																	property="id" />
																</logic:notEmpty>	
																
															</html:select>
														</td>
													</tr>
												</table>
												</td>
											</tr>
							
											<tr>
												<td width="110">
													<strong>Unidade de Negócio:</strong>					
												</td>
												<td>
												<table width="100%" border=0 cellpadding=0 cellspacing=0 align="left">
													<tr>
														<td width="175">
														
															<div align="left"><strong>Disponíveis</strong></div>
															
															<logic:present name="habilitaUnidadeNegocio" scope="request">
				 												<logic:equal name="habilitaUnidadeNegocio" value="true">
				       												<html:select property="idsUnidadeNegocio" 
																	size="6" 
																	multiple="true" 
																	style="width:190px">
																	<logic:notEmpty name="colecaoUnidadeNegocio">	
																	<html:options collection="colecaoUnidadeNegocio" 
																	labelProperty="nome" 
																	property="id" />
																	</logic:notEmpty>							
																	</html:select>
																</logic:equal>
				
															   <logic:equal name="habilitaUnidadeNegocio" value="false">
																	<html:select property="idsUnidadeNegocio" 
																	size="6" 
																	multiple="true" 
																	style="width:190px" disabled="true">
																	<logic:notEmpty name="colecaoUnidadeNegocio">	
																		<html:options collection="colecaoUnidadeNegocio" 
																			labelProperty="nome" 
																			property="id" />
																		</logic:notEmpty>										
																	</html:select>
															   </logic:equal>
				
															</logic:present> 
				
														 <logic:notPresent name="habilitaUnidadeNegocio" scope="request">
																<html:select property="idsUnidadeNegocio" 
																	size="6" 
																	multiple="true" 
																	style="width:190px">
																	<logic:notEmpty name="colecaoUnidadeNegocio">	
																		<html:options collection="colecaoUnidadeNegocio" 
																		labelProperty="nome" 
																		property="id" />
																		</logic:notEmpty>							
																	</html:select>
														 </logic:notPresent> 
															
														
														</td>
							
														<td width="5"><br>
															<table width="50" align="center">
																
																<logic:present name="habilitaUnidadeNegocio" scope="request">
				 													<logic:equal name="habilitaUnidadeNegocio" value="true">
				      											 <tr>
																	<td align="center">
																		<input type="button" 
																			class="bottonRightCol"
																			onclick="javascript:MoverTodosDadosSelectMenu1PARAMenu2('FiltrarImovelCobrancaAdministrativaActionForm', 'idsUnidadeNegocio', 'idsUnidadeNegocioSelecionadas');chamarReloadSemParametro();"
																			value=" &gt;&gt; ">
																	</td>
																</tr>
								
																<tr>
																	<td align="center">
																		<input type="button" 
																			class="bottonRightCol"
																			onclick="javascript:MoverDadosSelectMenu1PARAMenu2('FiltrarImovelCobrancaAdministrativaActionForm', 'idsUnidadeNegocio', 'idsUnidadeNegocioSelecionadas');chamarReloadSemParametro();"
																			value=" &nbsp;&gt;  ">
																	</td>
																</tr>
								
																<tr>
																	<td align="center">
																		<input type="button" 
																			class="bottonRightCol"
																			onclick="javascript:MoverDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsUnidadeNegocio', 'idsUnidadeNegocioSelecionadas');chamarReloadSemParametro();"
																			value=" &nbsp;&lt;  ">
																	</td>
																</tr>
								
																<tr>
																	<td align="center">
																		<input type="button" 
																			class="bottonRightCol"
																			onclick="javascript:MoverTodosDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsUnidadeNegocio', 'idsUnidadeNegocioSelecionadas');chamarReloadSemParametro();"
																			value=" &lt;&lt; ">
																	</td>
																</tr>
																	 </logic:equal>
				
																	<logic:equal name="habilitaUnidadeNegocio" value="false">
																<tr>
																	<td align="center">
																		<input type="button" 
																			class="bottonRightCol"
																			onclick="javascript:MoverTodosDadosSelectMenu1PARAMenu2('FiltrarImovelCobrancaAdministrativaActionForm', 'idsUnidadeNegocio', 'idsUnidadeNegocioSelecionadas');"
																			value=" &gt;&gt; " disabled="disabled">
																	</td>
																</tr>
								
																<tr>
																	<td align="center">
																		<input type="button" 
																			class="bottonRightCol"
																			onclick="javascript:MoverDadosSelectMenu1PARAMenu2('FiltrarImovelCobrancaAdministrativaActionForm', 'idsUnidadeNegocio', 'idsUnidadeNegocioSelecionadas');"
																			value=" &nbsp;&gt;  " disabled="disabled">
																	</td>
																</tr>
								
																<tr>
																	<td align="center">
																		<input type="button" 
																			class="bottonRightCol"
																			onclick="javascript:MoverDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsUnidadeNegocio', 'idsUnidadeNegocioSelecionadas');"
																			value=" &nbsp;&lt;  " disabled="disabled">
																	</td>
																</tr>
								
																<tr>
																	<td align="center">
																		<input type="button" 
																			class="bottonRightCol"
																			onclick="javascript:MoverTodosDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsUnidadeNegocio', 'idsUnidadeNegocioSelecionadas');"
																			value=" &lt;&lt; " disabled="disabled">
																	</td>
																</tr>
																	</logic:equal>
				
																</logic:present> 
				
																<logic:notPresent name="habilitaUnidadeNegocio" scope="request">
																<tr>
																	<td align="center">
																		<input type="button" 
																			class="bottonRightCol"
																			onclick="javascript:MoverTodosDadosSelectMenu1PARAMenu2('FiltrarImovelCobrancaAdministrativaActionForm', 'idsUnidadeNegocio', 'idsUnidadeNegocioSelecionadas');chamarReloadSemParametro();"
																			value=" &gt;&gt; ">
																	</td>
																</tr>
								
																<tr>
																	<td align="center">
																		<input type="button" 
																			class="bottonRightCol"
																			onclick="javascript:MoverDadosSelectMenu1PARAMenu2('FiltrarImovelCobrancaAdministrativaActionForm', 'idsUnidadeNegocio', 'idsUnidadeNegocioSelecionadas');chamarReloadSemParametro();"
																			value=" &nbsp;&gt;  ">
																	</td>
																</tr>
								
																<tr>
																	<td align="center">
																		<input type="button" 
																			class="bottonRightCol"
																			onclick="javascript:MoverDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsUnidadeNegocio', 'idsUnidadeNegocioSelecionadas');chamarReloadSemParametro();"
																			value=" &nbsp;&lt;  ">
																	</td>
																</tr>
								
																<tr>
																	<td align="center">
																		<input type="button" 
																			class="bottonRightCol"
																			onclick="javascript:MoverTodosDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsUnidadeNegocio', 'idsUnidadeNegocioSelecionadas');chamarReloadSemParametro();"
																			value=" &lt;&lt; ">
																	</td>
																</tr>
																</logic:notPresent> 
																
															</table>
														</td>
							
														<td>
															<div align="left">
																<strong>Selecionadas</strong>
															</div>
															
															<html:select property="idsUnidadeNegocioSelecionadas" 
																size="6" 
																multiple="true" 
																style="width:190px" >
															
																 <logic:empty name="colecaoIdsUnidadeNegocioSelecionados">	
																		<html:option value=" ">&nbsp;</html:option>
																</logic:empty>	
															   				
																 <logic:notEmpty name="colecaoIdsUnidadeNegocioSelecionados">	
																	<html:options collection="colecaoIdsUnidadeNegocioSelecionados" 
																	labelProperty="nome" 
																	property="id" />
																</logic:notEmpty>	
																
															
															</html:select>
														</td>
													</tr>
												</table>
												</td>
											</tr>
											<tr>
							             		<td height="10" colspan="3"> 
								             		<div align="center"> 
								                 		<hr>
								               		</div>
								               		<div align="center"> </div>
							               		</td>
							           		</tr>
											<logic:present name="habilitaInscricao" scope="request">
 												<logic:equal name="habilitaInscricao" value="true">
       												 <tr>
										            <td><strong>Localidade Inicial:</strong></td>
										            <td>
														<html:text maxlength="3" property="idLocalidadeInicial" size="3" onkeypress="validaEnterComMensagem(event, 'exibirFiltrarImovelCobrancaAdministrativaAction.do?objetoConsulta=1&inscricaoTipo=origem&reload=true','idLocalidadeInicial','Localidade Inicial');" 
															onkeyup="javascript:replicaDados(document.forms[0].idLocalidadeInicial, document.forms[0].idLocalidadeFinal);verificaNumeroInteiro(this);"
															onblur="javascript:limparQuandoReplicar(1);chamarReloadSemParametro();"/>
										                <a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'origem', null, null, 275, 480, '',document.forms[0].idLocalidadeInicial);">
										                <img
																src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"
																width="23" height="21" title="Pesquisar Localidade"></a> 
														<logic:present name="localidadeInicialInexistente">
																<html:text property="descricaoLocalidadeInicial" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
														</logic:present>
										
														<logic:present name="localidadeInicialExistente">
															<html:text property="descricaoLocalidadeInicial"size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: 	#000000"/>
														</logic:present>
														<a href="javascript:limparComBorracha(1);"> 
															<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
														</a>
													</td>
										        </tr>
										        <tr>
										            <td><strong>Localidade Final:</strong></td>
										            <td>
														<html:text maxlength="3" property="idLocalidadeFinal" size="3" onkeypress="validaEnterComMensagem(event, 'exibirFiltrarImovelCobrancaAdministrativaAction.do?objetoConsulta=1&inscricaoTipo=destino&reload=true','idLocalidadeFinal','Localidade Final');" 
															onkeyup="javascript:verificaNumeroInteiro(this);" 
															onblur="javascript:limparQuandoReplicar(1);chamarReloadSemParametro();"/>
										                <a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'destino', null, null, 275, 480, '',document.forms[0].idLocalidadeInicial);">
										                <img
																src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"
																width="23" height="21" title="Pesquisar Localidade"></a> 
														<logic:present name="localidadeFinalInexistente">
																<html:text property="descricaoLocalidadeFinal" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
														</logic:present>
										
														<logic:present name="localidadeFinalExistente">
															<html:text property="descricaoLocalidadeFinal"size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: 	#000000"/>
														</logic:present>
														<a href="javascript:limparComBorracha(2);"> 
															<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
														</a>
													</td>
										        </tr>
										        <logic:present name="habilitaSetor" scope="request">
 													<logic:equal name="habilitaSetor" value="true">
												        <tr>
												           <td><strong>Setor Comercial Inicial:</strong></td>
												           <td>
														   
															   <html:text maxlength="3" property="codigoSetorComercialInicial" size="3" onkeypress="validaEnterDependenciaComMensagem(event, 'exibirFiltrarImovelCobrancaAdministrativaAction.do?objetoConsulta=2&inscricaoTipo=origem&reload=true', document.forms[0].codigoSetorComercialInicial, document.forms[0].idLocalidadeInicial.value, 'Localidade Inicial', 'Setor comercial Inicial');"
															   		onkeyup="replicaDados(document.forms[0].codigoSetorComercialInicial, document.forms[0].codigoSetorComercialFinal);javascript:verificaNumeroInteiro(this);"
															   		onblur="javascript:limparQuandoReplicar(2);chamarReloadSemParametro();"/>
															   <a href="javascript:chamarPopupComDependencia('exibirPesquisarSetorComercialAction.do', 'setorComercialOrigem','idLocalidade', document.FiltrarImovelCobrancaAdministrativaActionForm.idLocalidadeInicial.value, 275, 480, 'Informe Localidade Inicial.');">
															   <img
																		src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"
																		width="23" height="21" title="Pesquisar Setor Comercial"></a> 
																<logic:present name="setorComercialInicialInexistente">
																	<html:text property="descricaoSetorComercialInicial" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
																</logic:present>
												
																<logic:present name="setorComercialInicialExistente">
																		<html:text property="descricaoSetorComercialInicial" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
																</logic:present>
															   
																<a href="javascript:limparComBorracha(3);"> 
																	<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
																</a>
												
															</td>
												        </tr>
												        <tr>
												           <td><strong>Setor Comercial Final:</strong></td>
												           <td>
														   
															   <html:text maxlength="3" property="codigoSetorComercialFinal" size="3" onkeypress="validaEnterDependenciaComMensagem(event, 'exibirFiltrarImovelCobrancaAdministrativaAction.do?objetoConsulta=2&inscricaoTipo=destino&reload=true', document.forms[0].codigoSetorComercialFinal, document.forms[0].idLocalidadeFinal.value, 'Localidade Final', 'Setor comercial Final');"
															   onkeyup="javascript:verificaNumeroInteiro(this);" 
															   onblur="javascript:limparQuandoReplicar(2);chamarReloadSemParametro();"/>
															   
															   <a href="javascript:chamarPopupComDependencia('exibirPesquisarSetorComercialAction.do', 'setorComercialDestino', 'idLocalidade', document.FiltrarImovelCobrancaAdministrativaActionForm.idLocalidadeFinal.value, 275, 480, 'Informe Localidade Final.');">
															   <img
																		src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"
																		width="23" height="21" title="Pesquisar Setor Comercial"></a> 
																<logic:present name="setorComercialFinalInexistente">
																	<html:text property="descricaoSetorComercialFinal" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
																</logic:present>
												
																<logic:present name="setorComercialFinalExistente">
																		<html:text property="descricaoSetorComercialFinal" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
																</logic:present>
															   
																<a href="javascript:limparComBorracha(4);"> 
																	<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
																</a>
												
															</td>
												        </tr>
											        </logic:equal>
											        <logic:notEqual name="habilitaSetor" value="true">
											        	<tr>
												           <td><strong>Setor Comercial Inicial:</strong></td>
												           <td>
														   
															   	<html:text maxlength="3" property="codigoSetorComercialInicial" size="3" disabled="true"/>						
																<html:text property="descricaoSetorComercialInicial" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
																
															</td>
												        </tr>
												        <tr>
												           <td><strong>Setor Comercial Final:</strong></td>
												           <td>
														   
															   	<html:text maxlength="3" property="codigoSetorComercialFinal" size="3" disabled="true"/>						
																<html:text property="descricaoSetorComercialFinal" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
												
															</td>
												        </tr>
											        </logic:notEqual>
										        </logic:present>
										       
										       <logic:present name="habilitaQuadra" scope="request">
 													<logic:equal name="habilitaQuadra" value="true">
												        <tr>
															<td><strong>Quadra Inicial:</strong></td>
															
															<td>
																
																<html:text maxlength="5" 
																	property="numeroQuadraInicial"							
																	size="5"
																	onblur="javascript:replicaDados(document.forms[0].numeroQuadraInicial, document.forms[0].numeroQuadraFinal);chamarReloadSemParametro();"
																	onkeyup="javascript:verificaNumeroInteiro(this);"
																	onkeypress="validaEnterDependenciaComMensagem(event, 'exibirFiltrarImovelCobrancaAdministrativaAction.do?objetoConsulta=3&inscricaoTipo=origem&reload=true', document.forms[0].numeroQuadraInicial, document.forms[0].codigoSetorComercialInicial.value, 'Setor Comercial Inicial', 'Quadra Inicial');"/>
																	<a
																		href="javascript:pesquisaLupaComDependencia('exibirPesquisarQuadraAction.do?idLocalidade='+document.forms[0].idLocalidadeInicial.value+'&codigoSetorComercial='+document.forms[0].codigoSetorComercialInicial.value+'&tipo=Quadra&retornarSeteParametros=S',document.forms[0].codigoSetorComercialInicial.value,'Setor Comercial Inicial', 400, 800,'numeroQuadraInicial');"
																		title="Pesquisar Quadra">
																		<img border="0"
																		src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0" />
																	</a> 
																	<logic:present name="quadraInicialInexistente" scope="request">
																		<span style="color: #ff0000" id="alertaQuadraInicialInexistente"><bean:write
																			name="FiltrarImovelCobrancaAdministrativaActionForm"
																			property="mensagemQuadraInicialInexistente" /></span>
																	</logic:present> 
																	
																	<a href="javascript:limparPesquisaQuadra('true');document.forms[0].numeroQuadraInicial.focus();">
																		<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
																		border="0" title="Apagar" /> 
																	</a>
															</td>
														</tr>
														
														<tr>
															<td><strong>Quadra Final:</strong></td>
															
															<td>
																
																<html:text maxlength="5" 
																	property="numeroQuadraFinal" 
																	size="5"
																	onkeyup="javascript:verificaNumeroInteiro(this);"
																	onblur="javascript:chamarReloadSemParametro();"
																	onkeypress="validaEnterDependenciaComMensagem(event, 'exibirFiltrarImovelCobrancaAdministrativaAction.do?objetoConsulta=3&inscricaoTipo=destino&reload=true', document.forms[0].numeroQuadraFinal, document.forms[0].numeroQuadraInicial.value, 'Quadra Inicial', 'Quadra Final');"/>
																<a
																	href="javascript:pesquisaLupaComDependencia('exibirPesquisarQuadraAction.do?idLocalidade='+document.forms[0].idLocalidadeFinal.value+'&codigoSetorComercial='+document.forms[0].codigoSetorComercialFinal.value+'&tipo=Quadra&retornarSeteParametros=S',document.forms[0].codigoSetorComercialFinal.value,'Setor Comercial Final', 400, 800,'numeroQuadraFinal');"
																	title="Pesquisar Quadra">
																	<img border="0"
																	src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0" />
																</a> 
																<logic:present name="quadraFinalInexistente" scope="request">
																	<span style="color: #ff0000" id="alertaQuadraFinalInexistente"><bean:write
																		name="FiltrarImovelCobrancaAdministrativaActionForm"
																		property="mensagemQuadraFinalInexistente" /></span>
																</logic:present> 
																
																<a href="javascript:limparPesquisaQuadra('false');document.forms[0].numeroQuadraFinal.focus();">
																	<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
																	border="0" title="Apagar" /> 
																</a>
															</td>
														</tr>
													</logic:equal>
													<logic:notEqual name="habilitaQuadra" value="true">
												        <tr>
															<td><strong>Quadra Inicial:</strong></td>
															
															<td>
																
																<html:text maxlength="5" 
																	property="numeroQuadraInicial"							
																	size="5"
																	disabled="true"/>
																	
																	<span style="color: #ff0000" id="alertaQuadraInicialInexistente"><bean:write
																	name="FiltrarImovelCobrancaAdministrativaActionForm"
																	property="mensagemQuadraInicialInexistente" /></span>
															</td>
														</tr>
														
														<tr>
															<td><strong>Quadra Final:</strong></td>
															
															<td>
																
																<html:text maxlength="5" 
																	property="numeroQuadraFinal"							
																	size="5"
																	disabled="true"/>
																	
																	<span style="color: #ff0000" id="alertaQuadraFinalInexistente"><bean:write
																	name="FiltrarImovelCobrancaAdministrativaActionForm"
																	property="mensagemQuadraFinalInexistente" /></span>
															</td>
														</tr>
													</logic:notEqual>
												</logic:present>
												</logic:equal>

												<logic:equal name="habilitaInscricao" value="false">
												 <tr>
										            <td><strong>Localidade Inicial:</strong></td>
										            <td>
														<html:text maxlength="3" property="idLocalidadeInicial" size="3"  disabled="true"/>             
														
														<html:text property="descricaoLocalidadeInicial" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
													
													</td>
										        </tr>
										        <tr>
										            <td><strong>Localidade Final:</strong></td>
										            <td>
														<html:text maxlength="3" property="idLocalidadeFinal" size="3" disabled="true"/>						              
										
														<html:text property="descricaoLocalidadeFinal"size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: 	#000000"/>
														
													</td>
										        </tr>
										        <tr>
										           <td><strong>Setor Comercial Inicial:</strong></td>
										           <td>
												   
													   <html:text maxlength="3" property="codigoSetorComercialInicial" size="3" disabled="true"/>
													  
														<html:text property="descricaoSetorComercialInicial" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
													
													</td>
										        </tr>
										        <tr>
										           <td><strong>Setor Comercial Final:</strong></td>
										           <td>
												   
													   <html:text maxlength="3" property="codigoSetorComercialFinal" size="3" disabled="true"/>												   
													  
														<html:text property="descricaoSetorComercialFinal" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
														
													</td>
										        </tr>
										       
										        <tr>
													<td><strong>Quadra Inicial:</strong></td>
													
													<td>
														
														<html:text maxlength="5" 
															property="numeroQuadraInicial"							
															size="5"
															disabled="true"/>
															
															
																<span style="color: #ff0000" id="alertaQuadraInicialInexistente"><bean:write
																	name="FiltrarImovelCobrancaAdministrativaActionForm"
																	property="mensagemQuadraInicialInexistente" /></span>
															
															
															
													</td>
												</tr>
												
												<tr>
													<td><strong>Quadra Final:</strong></td>
													
													<td>
														
														<html:text maxlength="5" 
															property="numeroQuadraFinal" 
															size="5"
															disabled="true"/>
													
															<span style="color: #ff0000" id="alertaQuadraFinalInexistente"><bean:write
																name="FiltrarImovelCobrancaAdministrativaActionForm"
																property="mensagemQuadraFinalInexistente" /></span>
														
													</td>
												</tr>
												</logic:equal>

											</logic:present> 

											<logic:notPresent name="habilitaInscricao" scope="request">
 												<tr>
										            <td><strong>Localidade Inicial:</strong></td>
										            <td>
														<html:text maxlength="3" property="idLocalidadeInicial" size="3" onkeypress="validaEnterComMensagem(event, 'exibirFiltrarImovelCobrancaAdministrativaAction.do?objetoConsulta=1&inscricaoTipo=origem&reload=true','idLocalidadeInicial','Localidade Inicial');" 
															onkeyup="javascript:replicaDados(document.forms[0].idLocalidadeInicial, document.forms[0].idLocalidadeFinal);verificaNumeroInteiro(this);"
															onblur="javascript:limparQuandoReplicar(1);chamarReloadSemParametro();"/>
										                <a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'origem', null, null, 275, 480, '',document.forms[0].idLocalidadeInicial);">
										                <img
																src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"
																width="23" height="21" title="Pesquisar Localidade"></a> 
														<logic:present name="localidadeInicialInexistente">
																<html:text property="descricaoLocalidadeInicial" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
														</logic:present>
										
														<logic:present name="localidadeInicialExistente">
															<html:text property="descricaoLocalidadeInicial"size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: 	#000000"/>
														</logic:present>
														<a href="javascript:limparComBorracha(1);"> 
															<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
														</a>
													</td>
										        </tr>
										        <tr>
										            <td><strong>Localidade Final:</strong></td>
										            <td>
														<html:text maxlength="3" property="idLocalidadeFinal" size="3" onkeypress="validaEnterDependenciaComMensagem(event, 
															'exibirFiltrarImovelCobrancaAdministrativaAction.do?objetoConsulta=1&inscricaoTipo=destino&reload=true', 
															document.forms[0].idLocalidadeFinal, document.forms[0].idLocalidadeInicial.value, 'Localidade Inicial', 'Localidade Final');" 
															onkeyup="javascript:verificaNumeroInteiro(this);" 
															onblur="javascript:limparQuandoReplicar(1);chamarReloadSemParametro();"/>
										          
										                <a href="javascript:chamarPopupComDependencia('exibirPesquisarLocalidadeAction.do', 'localidadeDestino','idLocalidade', document.FiltrarImovelCobrancaAdministrativaActionForm.idLocalidadeInicial.value, 275, 480, 'Informe Localidade Inicial.');">
										                <img
																src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"
																width="23" height="21" title="Pesquisar Localidade"></a> 
														<logic:present name="localidadeFinalInexistente">
																<html:text property="descricaoLocalidadeFinal" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
														</logic:present>
										
														<logic:present name="localidadeFinalExistente">
															<html:text property="descricaoLocalidadeFinal"size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: 	#000000"/>
														</logic:present>
														<a href="javascript:limparComBorracha(2);"> 
															<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
														</a>
													</td>
										        </tr>
										         <logic:notPresent name="habilitaSetor" scope="request">
											        <tr>
											           <td><strong>Setor Comercial Inicial:</strong></td>
											           <td>
													   
														   <html:text maxlength="3" property="codigoSetorComercialInicial" size="3" onkeypress="validaEnterDependenciaComMensagem(event, 'exibirFiltrarImovelCobrancaAdministrativaAction.do?objetoConsulta=2&inscricaoTipo=origem&reload=true', document.forms[0].codigoSetorComercialInicial, document.forms[0].idLocalidadeInicial.value, 'Localidade Inicial', 'Setor comercial Inicial');"
														   		onkeyup="replicaDados(document.forms[0].codigoSetorComercialInicial, document.forms[0].codigoSetorComercialFinal);javascript:verificaNumeroInteiro(this);"
														   		onblur="javascript:limparQuandoReplicar(2);chamarReloadSemParametro();"/>
														   <a href="javascript:chamarPopupComDependencia('exibirPesquisarSetorComercialAction.do', 'setorComercialOrigem','idLocalidade', document.FiltrarImovelCobrancaAdministrativaActionForm.idLocalidadeInicial.value, 275, 480, 'Informe Localidade Inicial.');">
														   <img
																	src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"
																	width="23" height="21" title="Pesquisar Setor Comercial"></a> 
															<logic:present name="setorComercialInicialInexistente">
																<html:text property="descricaoSetorComercialInicial" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
															</logic:present>
											
															<logic:present name="setorComercialInicialExistente">
																	<html:text property="descricaoSetorComercialInicial" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
															</logic:present>
														   
															<a href="javascript:limparComBorracha(3);"> 
																<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
															</a>
											
														</td>
											        </tr>
											        <tr>
											           <td><strong>Setor Comercial Final:</strong></td>
											           <td>
													   
														   <html:text maxlength="3" property="codigoSetorComercialFinal" size="3" onkeypress="validaEnterDependenciaComMensagem(event, 'exibirFiltrarImovelCobrancaAdministrativaAction.do?objetoConsulta=2&inscricaoTipo=destino&reload=true', document.forms[0].codigoSetorComercialFinal, document.forms[0].idLocalidadeFinal.value, 'Localidade Final', 'Setor comercial Final');"
														   onkeyup="javascript:verificaNumeroInteiro(this);" 
														   onblur="javascript:limparQuandoReplicar(2);chamarReloadSemParametro();"/>
														   
														   <a href="javascript:chamarPopupComDependencia('exibirPesquisarSetorComercialAction.do', 'setorComercialDestino', 'idLocalidade', document.FiltrarImovelCobrancaAdministrativaActionForm.idLocalidadeFinal.value, 275, 480, 'Informe Localidade Final.');">
														   <img
																	src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"
																	width="23" height="21" title="Pesquisar Setor Comercial"></a> 
															<logic:present name="setorComercialFinalInexistente">
																<html:text property="descricaoSetorComercialFinal" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
															</logic:present>
											
															<logic:present name="setorComercialFinalExistente">
																	<html:text property="descricaoSetorComercialFinal" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
															</logic:present>
														   
															<a href="javascript:limparComBorracha(4);"> 
																<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
															</a>
											
														</td>
											        </tr>
											        
										        </logic:notPresent>
										       
										        <logic:notPresent name="habilitaQuadra" scope="request">
											        <tr>
														<td><strong>Quadra Inicial:</strong></td>
														
														<td>
															
															<html:text maxlength="5" 
																property="numeroQuadraInicial"							
																size="5"
																onblur="javascript:replicaDados(document.forms[0].numeroQuadraInicial, document.forms[0].numeroQuadraFinal);chamarReloadSemParametro();"
																onkeyup="javascript:verificaNumeroInteiro(this);"
																onkeypress="validaEnterDependenciaComMensagem(event, 'exibirFiltrarImovelCobrancaAdministrativaAction.do?objetoConsulta=3&inscricaoTipo=origem&reload=true', document.forms[0].numeroQuadraInicial, document.forms[0].codigoSetorComercialInicial.value, 'Setor Comercial Inicial', 'Quadra Inicial');"/>
																<a
																	href="javascript:pesquisaLupaComDependencia('exibirPesquisarQuadraAction.do?idLocalidade='+document.forms[0].idLocalidadeInicial.value+'&codigoSetorComercial='+document.forms[0].codigoSetorComercialInicial.value+'&tipo=Quadra&retornarSeteParametros=S',document.forms[0].codigoSetorComercialInicial.value,'Setor Comercial Inicial', 400, 800,'numeroQuadraInicial');"
																	title="Pesquisar Quadra">
																	<img border="0"
																	src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0" />
																</a> 
																<logic:present name="quadraInicialInexistente" scope="request">
																	<span style="color: #ff0000" id="alertaQuadraInicialInexistente"><bean:write
																		name="FiltrarImovelCobrancaAdministrativaActionForm"
																		property="mensagemQuadraInicialInexistente" /></span>
																</logic:present> 
																
																<a href="javascript:limparPesquisaQuadra('true');document.forms[0].numeroQuadraInicial.focus();">
																	<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
																	border="0" title="Apagar" /> 
																</a>
														</td>
													</tr>
													
													<tr>
														<td><strong>Quadra Final:</strong></td>
														
														<td>
															
															<html:text maxlength="5" 
																property="numeroQuadraFinal" 
																size="5"
																onkeyup="javascript:verificaNumeroInteiro(this);"
																onblur="javascript:chamarReloadSemParametro();"
																onkeypress="validaEnterDependenciaComMensagem(event, 'exibirFiltrarImovelCobrancaAdministrativaAction.do?objetoConsulta=3&inscricaoTipo=destino&reload=true', document.forms[0].numeroQuadraFinal, document.forms[0].numeroQuadraInicial.value, 'Quadra Inicial', 'Quadra Final');"/>
															<a
																href="javascript:pesquisaLupaComDependencia('exibirPesquisarQuadraAction.do?idLocalidade='+document.forms[0].idLocalidadeFinal.value+'&codigoSetorComercial='+document.forms[0].codigoSetorComercialFinal.value+'&tipo=Quadra&retornarSeteParametros=S',document.forms[0].codigoSetorComercialFinal.value,'Setor Comercial Final', 400, 800,'numeroQuadraFinal');"
																title="Pesquisar Quadra">
																<img border="0"
																src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0" />
															</a> 
															<logic:present name="quadraFinalInexistente" scope="request">
																<span style="color: #ff0000" id="alertaQuadraFinalInexistente"><bean:write
																	name="FiltrarImovelCobrancaAdministrativaActionForm"
																	property="mensagemQuadraFinalInexistente" /></span>
															</logic:present> 
															
															<a href="javascript:limparPesquisaQuadra('false');document.forms[0].numeroQuadraFinal.focus();">
																<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
																border="0" title="Apagar" /> 
															</a>
														</td>
													</tr>
												</logic:notPresent>
											</logic:notPresent> 

											</table>
										</td>
									</tr>
								</table>
								</div>
								</td>
							</tr>
							
							<tr>
			             		<td height="10" colspan="3"> 
				             		<div align="center"> 
				                 		<hr>
				               		</div>
				               		<div align="center"> </div>
			               		</td>
			           		</tr>
			           		
			           		<tr>
								<td class="style1"><strong>Valor do Débito:</strong></td>
			
								<td class="style1"><strong> <html:text
									property="valorDebitoInicial" size="13" style="text-align:right;"
									onkeyup="formataValorMonetario(this, 13); replicaDados(document.FiltrarImovelCobrancaAdministrativaActionForm.valorDebitoInicial, document.FiltrarImovelCobrancaAdministrativaActionForm.valorDebitoFinal);"
									maxlength="13" /> a</strong> <strong> <html:text
									property="valorDebitoFinal" style="text-align:right;"
									onkeyup="formataValorMonetario(this, 13);" size="13"
									maxlength="13" /></strong></td>
							</tr>
							
							<tr>
								<td class="style1"><strong>Per&iacute;odo de Inclus&atilde;o:</strong></td>
			
								<td ><strong> <html:text property="periodoInclusaoInicial"
									size="10" 
									onkeyup="mascaraData(this, event); replicaDados(document.FiltrarImovelCobrancaAdministrativaActionForm.periodoInclusaoInicial, document.FiltrarImovelCobrancaAdministrativaActionForm.periodoInclusaoFinal);"
									maxlength="10" /> <a
									href="javascript:abrirCalendarioReplicando('FiltrarImovelCobrancaAdministrativaActionForm', 'periodoInclusaoInicial','periodoInclusaoFinal')">
								<img border="0"
									src="<bean:message key="caminho.imagens"/>calendario.gif"
									width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
								a</strong> <html:text property="periodoInclusaoFinal" size="10"
									maxlength="10" onkeyup="mascaraData(this, event);" /> <a
									href="javascript:abrirCalendario('FiltrarImovelCobrancaAdministrativaActionForm', 'periodoInclusaoFinal')">
								<img border="0"
									src="<bean:message key="caminho.imagens"/>calendario.gif"
									width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
								dd/mm/aaaa</td>
							</tr>
							
							<tr>
								<td class="style1"><strong>Per&iacute;odo de Retirada:</strong></td>
			
								<td><strong> <html:text property="periodoRetiradaInicial"
									size="10"
									onkeyup="mascaraData(this, event); replicaDados(document.FiltrarImovelCobrancaAdministrativaActionForm.periodoRetiradaInicial, document.FiltrarImovelCobrancaAdministrativaActionForm.periodoRetiradaFinal);"
									maxlength="10" /> <a
									href="javascript:abrirCalendarioReplicando('FiltrarImovelCobrancaAdministrativaActionForm', 'periodoRetiradaInicial','periodoRetiradaFinal')">
								<img border="0"
									src="<bean:message key="caminho.imagens"/>calendario.gif"
									width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
								a</strong> <html:text property="periodoRetiradaFinal" size="10"
									maxlength="10" onkeyup="mascaraData(this, event);" /> <a
									href="javascript:abrirCalendario('FiltrarImovelCobrancaAdministrativaActionForm', 'periodoRetiradaFinal')">
								<img border="0"
									src="<bean:message key="caminho.imagens"/>calendario.gif"
									width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
								dd/mm/aaaa</td>
							</tr>

							
							<tr>
								<td colspan="2">		
									<table>
									  <tr>
									  		<td ><strong>Situa&ccedil;&atilde;o da Cobrança Administrativa:</strong>
						                     	<html:radio property="indicadorSituacaoCobrancaAdministrativa" value="1">Pendente</html:radio>
						                     	<html:radio property="indicadorSituacaoCobrancaAdministrativa" value="2">Encerrado</html:radio>						               
						                    	<html:radio property="indicadorSituacaoCobrancaAdministrativa" value="3">Todos</html:radio>
						 			  		</td>
									  </tr>
									</table>			
								</td>
							</tr>
							
							<tr>
			             		<td height="10" colspan="3"> 
				             		<div align="center"> 
				                 		<hr>
				               		</div>
				               		<div align="center"> </div>
			               		</td>
			           		</tr>
							
							<tr>
								<td colspan="2">
									<div id="layerHideDadosAdicionais" style="display: block">
									<table width="100%" border="0" bgcolor="#99CCFF">
										<tr bgcolor="#99CCFF">
											<td align="center"><span class="style2"> <a
												href="javascript:extendeTabela('DadosAdicionais',true);" /><b>Dados
											Adicionais</b></a></span></td>
										</tr>
									</table>
									</div>
				
									<div id="layerShowDadosAdicionais" style="display: none">
									<table width="100%" border="0" bgcolor="#99CCFF">
										<tr bgcolor="#99CCFF">
											<td align="center"><span class="style2"> <a
												href="javascript:extendeTabela('DadosAdicionais',false);" /><b>Dados
											Adicionais</b></a></span></td>
										</tr>
										<tr bgcolor="#cbe5fe">
											<td>
												<table width="100%">
													<tr>
														<td width="110">
															<strong>Categoria:</strong>					
														</td>
														<td>
														<table width="100%" border=0 cellpadding=0 cellspacing=0 align="left">
															<tr>
																<td width="175">
																
																	<div align="left"><strong>Disponíveis</strong></div>
									
																	<html:select property="idsCategoria" 
																		size="6" 
																		multiple="true" 
																		style="width:190px">
																	<logic:notEmpty name="colecaoCategoria">	
																		<html:options collection="colecaoCategoria" 
																			labelProperty="descricao" 
																			property="id" />
																	</logic:notEmpty>
																	</html:select>
																</td>
									
																<td width="5"><br>
																	<table width="50" align="center">
																		<tr>
																			<td align="center">
																				<input type="button" 
																					class="bottonRightCol"
																					onclick="javascript:MoverTodosDadosSelectMenu1PARAMenu2('FiltrarImovelCobrancaAdministrativaActionForm', 'idsCategoria', 'idsCategoriaSelecionadas');chamarReloadSemParametro();"
																					value=" &gt;&gt; ">
																			</td>
																		</tr>
										
																		<tr>
																			<td align="center">
																				<input type="button" 
																					class="bottonRightCol"
																					onclick="javascript:MoverDadosSelectMenu1PARAMenu2('FiltrarImovelCobrancaAdministrativaActionForm', 'idsCategoria', 'idsCategoriaSelecionadas');chamarReloadSemParametro();"
																					value=" &nbsp;&gt;  ">
																			</td>
																		</tr>
										
																		<tr>
																			<td align="center">
																				<input type="button" 
																					class="bottonRightCol"
																					onclick="javascript:MoverDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsCategoria', 'idsCategoriaSelecionadas');chamarReloadSemParametro();"
																					value=" &nbsp;&lt;  ">
																			</td>
																		</tr>
										
																		<tr>
																			<td align="center">
																				<input type="button" 
																					class="bottonRightCol"
																					onclick="javascript:MoverTodosDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsCategoria', 'idsCategoriaSelecionadas');chamarReloadSemParametro();"
																					value=" &lt;&lt; ">
																			</td>
																		</tr>
																	</table>
																</td>
									
																<td>
																	<div align="left">
																		<strong>Selecionadas</strong>
																	</div>
																	
																	<html:select property="idsCategoriaSelecionadas" 
																		size="6" 
																		multiple="true" 
																		style="width:190px">
																	
																		<logic:empty name="colecaoIdsCategoriaSelecionadas">	
																				<html:option value=" ">&nbsp;</html:option>
																		</logic:empty>	
																	   				
																		 <logic:notEmpty name="colecaoIdsCategoriaSelecionadas">	
																			<html:options collection="colecaoIdsCategoriaSelecionadas" 
																			labelProperty="descricao" 
																			property="id" />
																		</logic:notEmpty>
																	</html:select>
																</td>
															</tr>
														</table>
														</td>
													</tr>
													
													<tr>
														<td width="110">
															<strong>Subcategoria:</strong>					
														</td>
														<td>
														<table width="100%" border=0 cellpadding=0 cellspacing=0 align="left">
															<tr>
																<td width="175">
																
																	<div align="left"><strong>Disponíveis</strong></div>
									
																		<logic:present name="habilitaSubCategoria" scope="request">
															
															         		<logic:equal name="habilitaSubCategoria" value="true">
																				<html:select property="idsSubcategoria" 
																					size="6" 
																					multiple="true" 
																					style="width:190px">
																				<logic:notEmpty name="colecaoSubCategoria">	
																					<html:options collection="colecaoSubCategoria" 
																						labelProperty="descricao" 
																						property="id" />
																				</logic:notEmpty>
																				</html:select>
																			</logic:equal>
																			<logic:notEqual name="habilitaSubCategoria" value="true">
																				<html:select property="idsSubcategoria" 
																					size="6" 
																					multiple="true" 
																					style="width:190px" disabled="true">
																					<logic:notEmpty name="colecaoSubCategoria">	
																					<html:options collection="colecaoSubCategoria" 
																					labelProperty="descricao" 
																					property="id" />
																					</logic:notEmpty>
																				</html:select>
																			</logic:notEqual>
																		</logic:present>
																		<logic:notPresent name="habilitaSubCategoria" scope="request">
																			<html:select property="idsSubcategoria" 
																				size="6" 
																				multiple="true" 
																				style="width:190px">
																				<logic:notEmpty name="colecaoSubCategoria">	
																				<html:options collection="colecaoSubCategoria" 
																				labelProperty="descricao" 
																				property="id" />
																				</logic:notEmpty>
																			</html:select>
																		</logic:notPresent>
																		
																</td>
									
																<td width="5"><br>
																	<table width="50" align="center">
																		<logic:present name="habilitaSubCategoria" scope="request">
															
															         		<logic:equal name="habilitaSubCategoria" value="true">
																				<tr>
																					<td align="center">
																						<input type="button" 
																							class="bottonRightCol"
																							onclick="javascript:MoverTodosDadosSelectMenu1PARAMenu2('FiltrarImovelCobrancaAdministrativaActionForm', 'idsSubcategoria', 'idsSubcategoriaSelecionadas');chamarReloadSemParametro();"
																							value=" &gt;&gt; ">
																					</td>
																				</tr>
												
																				<tr>
																					<td align="center">
																						<input type="button" 
																							class="bottonRightCol"
																							onclick="javascript:MoverDadosSelectMenu1PARAMenu2('FiltrarImovelCobrancaAdministrativaActionForm', 'idsSubcategoria', 'idsSubcategoriaSelecionadas');chamarReloadSemParametro();"
																							value=" &nbsp;&gt;  ">
																					</td>
																				</tr>
												
																				<tr>
																					<td align="center">
																						<input type="button" 
																							class="bottonRightCol"
																							onclick="javascript:MoverDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsSubcategoria', 'idsSubcategoriaSelecionadas');chamarReloadSemParametro();"
																							value=" &nbsp;&lt;  ">
																					</td>
																				</tr>
												
																				<tr>
																					<td align="center">
																						<input type="button" 
																							class="bottonRightCol"
																							onclick="javascript:MoverTodosDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsSubcategoria', 'idsSubcategoriaSelecionadas');chamarReloadSemParametro();"
																							value=" &lt;&lt; ">
																					</td>
																				</tr>
																			</logic:equal>
																			<logic:notEqual name="habilitaSubCategoria" value="true">
																				<tr>
																					<td align="center">
																						<input type="button" 
																							class="bottonRightCol"
																							onclick="javascript:MoverTodosDadosSelectMenu1PARAMenu2('FiltrarImovelCobrancaAdministrativaActionForm', 'idsSubcategoria', 'idsSubcategoriaSelecionadas');chamarReloadSemParametro();"
																							value=" &gt;&gt; "
																							disabled="disabled">
																					</td>
																				</tr>
												
																				<tr>
																					<td align="center">
																						<input type="button" 
																							class="bottonRightCol"
																							onclick="javascript:MoverDadosSelectMenu1PARAMenu2('FiltrarImovelCobrancaAdministrativaActionForm', 'idsSubcategoria', 'idsSubcategoriaSelecionadas');chamarReloadSemParametro();"
																							value=" &nbsp;&gt;  "
																							disabled="disabled">
																					</td>
																				</tr>
												
																				<tr>
																					<td align="center">
																						<input type="button" 
																							class="bottonRightCol"
																							onclick="javascript:MoverDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsSubcategoria', 'idsSubcategoriaSelecionadas');chamarReloadSemParametro();"
																							value=" &nbsp;&lt;  "
																							disabled="disabled">
																					</td>
																				</tr>
												
																				<tr>
																					<td align="center">
																						<input type="button" 
																							class="bottonRightCol"
																							onclick="javascript:MoverTodosDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsSubcategoria', 'idsSubcategoriaSelecionadas');chamarReloadSemParametro();"
																							value=" &lt;&lt; "
																							disabled="disabled">
																					</td>
																				</tr>
																			</logic:notEqual>
																		</logic:present>
																		<logic:notPresent name="habilitaSubCategoria">
																			<tr>
																					<td align="center">
																						<input type="button" 
																							class="bottonRightCol"
																							onclick="javascript:MoverTodosDadosSelectMenu1PARAMenu2('FiltrarImovelCobrancaAdministrativaActionForm', 'idsSubcategoria', 'idsSubcategoriaSelecionadas');chamarReloadSemParametro();"
																							value=" &gt;&gt; ">
																					</td>
																				</tr>
												
																				<tr>
																					<td align="center">
																						<input type="button" 
																							class="bottonRightCol"
																							onclick="javascript:MoverDadosSelectMenu1PARAMenu2('FiltrarImovelCobrancaAdministrativaActionForm', 'idsSubcategoria', 'idsSubcategoriaSelecionadas');chamarReloadSemParametro();"
																							value=" &nbsp;&gt;  ">
																					</td>
																				</tr>
												
																				<tr>
																					<td align="center">
																						<input type="button" 
																							class="bottonRightCol"
																							onclick="javascript:MoverDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsSubcategoria', 'idsSubcategoriaSelecionadas');chamarReloadSemParametro();"
																							value=" &nbsp;&lt;  ">
																					</td>
																				</tr>
												
																				<tr>
																					<td align="center">
																						<input type="button" 
																							class="bottonRightCol"
																							onclick="javascript:MoverTodosDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsSubcategoria', 'idsSubcategoriaSelecionadas');chamarReloadSemParametro();"
																							value=" &lt;&lt; ">
																					</td>
																				</tr>
																		</logic:notPresent>
																	</table>
																</td>
									
																<td>
																	<div align="left">
																		<strong>Selecionadas</strong>
																	</div>
																	
																	<html:select property="idsSubcategoriaSelecionadas" 
																		size="6" 
																		multiple="true" 
																		style="width:190px">
																	
																		<logic:empty name="colecaoIdsSubcategoriaSelecionadas">	
																				<html:option value=" ">&nbsp;</html:option>
																		</logic:empty>	
																	   				
																		 <logic:notEmpty name="colecaoIdsSubcategoriaSelecionadas">	
																			<html:options collection="colecaoIdsSubcategoriaSelecionadas" 
																			labelProperty="descricao" 
																			property="id" />
																		</logic:notEmpty>
																	</html:select>
																</td>
															</tr>
														</table>
														</td>
													</tr>
													
													<tr>
														<td width="110">
															<strong>Situação da Ligação de Água:</strong>					
														</td>
														<td>
														<table width="100%" border=0 cellpadding=0 cellspacing=0 align="left">
															<tr>
																<td width="175">
																
																	<div align="left"><strong>Disponíveis</strong></div>
									
																	<html:select property="idsLigacaoAguaSituacao" 
																		size="6" 
																		multiple="true" 
																		style="width:190px">
																	<logic:notEmpty name="colecaoLigacaoAguaSituacao">	
																		<html:options collection="colecaoLigacaoAguaSituacao" 
																			labelProperty="descricao" 
																			property="id" />
																	</logic:notEmpty>
																	</html:select>
																</td>
									
																<td width="5"><br>
																	<table width="50" align="center">
																		<tr>
																			<td align="center">
																				<input type="button" 
																					class="bottonRightCol"
																					onclick="javascript:MoverTodosDadosSelectMenu1PARAMenu2('FiltrarImovelCobrancaAdministrativaActionForm', 'idsLigacaoAguaSituacao', 'idsLigacaoAguaSituacaoSelecionadas');chamarReloadSemParametro();"
																					value=" &gt;&gt; ">
																			</td>
																		</tr>
										
																		<tr>
																			<td align="center">
																				<input type="button" 
																					class="bottonRightCol"
																					onclick="javascript:MoverDadosSelectMenu1PARAMenu2('FiltrarImovelCobrancaAdministrativaActionForm', 'idsLigacaoAguaSituacao', 'idsLigacaoAguaSituacaoSelecionadas');chamarReloadSemParametro();"
																					value=" &nbsp;&gt;  ">
																			</td>
																		</tr>
										
																		<tr>
																			<td align="center">
																				<input type="button" 
																					class="bottonRightCol"
																					onclick="javascript:MoverDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsLigacaoAguaSituacao', 'idsLigacaoAguaSituacaoSelecionadas');chamarReloadSemParametro();"
																					value=" &nbsp;&lt;  ">
																			</td>
																		</tr>
										
																		<tr>
																			<td align="center">
																				<input type="button" 
																					class="bottonRightCol"
																					onclick="javascript:MoverTodosDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsLigacaoAguaSituacao', 'idsLigacaoAguaSituacaoSelecionadas');chamarReloadSemParametro();"
																					value=" &lt;&lt; ">
																			</td>
																		</tr>
																	</table>
																</td>
									
																<td>
																	<div align="left">
																		<strong>Selecionadas</strong>
																	</div>
																	
																	<html:select property="idsLigacaoAguaSituacaoSelecionadas" 
																		size="6" 
																		multiple="true" 
																		style="width:190px">
																		
																		<logic:empty name="colecaoIdsLigacaoAguaSituacaoSelecionadas">	
																				<html:option value=" ">&nbsp;</html:option>
																		</logic:empty>	
																	   				
																		 <logic:notEmpty name="colecaoIdsLigacaoAguaSituacaoSelecionadas">	
																			<html:options collection="colecaoIdsLigacaoAguaSituacaoSelecionadas" 
																			labelProperty="descricao" 
																			property="id" />
																		</logic:notEmpty>
																	</html:select>
																</td>
															</tr>
														</table>
														</td>
													</tr>
													
													<tr>
														<td width="110">
															<strong>Situação da Ligação de Esgoto:</strong>					
														</td>
														<td>
														<table width="100%" border=0 cellpadding=0 cellspacing=0 align="left">
															<tr>
																<td width="175">
																
																	<div align="left"><strong>Disponíveis</strong></div>
									
																	<html:select property="idsLigacaoEsgotoSituacao" 
																		size="6" 
																		multiple="true" 
																		style="width:190px">
																	<logic:notEmpty name="colecaoLigacaoEsgotoSituacao">	
																		<html:options collection="colecaoLigacaoEsgotoSituacao" 
																			labelProperty="descricao" 
																			property="id" />
																	</logic:notEmpty>
																	</html:select>
																</td>
									
																<td width="5"><br>
																	<table width="50" align="center">
																		<tr>
																			<td align="center">
																				<input type="button" 
																					class="bottonRightCol"
																					onclick="javascript:MoverTodosDadosSelectMenu1PARAMenu2('FiltrarImovelCobrancaAdministrativaActionForm', 'idsLigacaoEsgotoSituacao', 'idsLigacaoEsgotoSituacaoSelecionadas');chamarReloadSemParametro();"
																					value=" &gt;&gt; ">
																			</td>
																		</tr>
										
																		<tr>
																			<td align="center">
																				<input type="button" 
																					class="bottonRightCol"
																					onclick="javascript:MoverDadosSelectMenu1PARAMenu2('FiltrarImovelCobrancaAdministrativaActionForm', 'idsLigacaoEsgotoSituacao', 'idsLigacaoEsgotoSituacaoSelecionadas');chamarReloadSemParametro();"
																					value=" &nbsp;&gt;  ">
																			</td>
																		</tr>
										
																		<tr>
																			<td align="center">
																				<input type="button" 
																					class="bottonRightCol"
																					onclick="javascript:MoverDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsLigacaoEsgotoSituacao', 'idsLigacaoEsgotoSituacaoSelecionadas');chamarReloadSemParametro();"
																					value=" &nbsp;&lt;  ">
																			</td>
																		</tr>
										
																		<tr>
																			<td align="center">
																				<input type="button" 
																					class="bottonRightCol"
																					onclick="javascript:MoverTodosDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsLigacaoEsgotoSituacao', 'idsLigacaoEsgotoSituacaoSelecionadas');chamarReloadSemParametro();"
																					value=" &lt;&lt; ">
																			</td>
																		</tr>
																	</table>
																</td>
									
																<td>
																	<div align="left">
																		<strong>Selecionadas</strong>
																	</div>
																	
																	<html:select property="idsLigacaoEsgotoSituacaoSelecionadas" 
																		size="6" 
																		multiple="true" 
																		style="width:190px">
																		
																		<logic:empty name="colecaoIdsLigacaoEsgotoSituacaoSelecionadas">	
																				<html:option value=" ">&nbsp;</html:option>
																		</logic:empty>	
																	   				
																		 <logic:notEmpty name="colecaoIdsLigacaoEsgotoSituacaoSelecionadas">	
																			<html:options collection="colecaoIdsLigacaoEsgotoSituacaoSelecionadas" 
																			labelProperty="descricao" 
																			property="id" />
																		</logic:notEmpty>
																	</html:select>
																</td>
															</tr>
														</table>
														</td>
													</tr>
													
													<tr>
														<td width="110">
															<strong>Motivo da Retirada:</strong>					
														</td>
														<td colspan="2">
														<table width="100%" border=0 cellpadding=0 cellspacing=0 align="left">
															<tr>
																<td width="175">
																
																	<div align="left"><strong>Disponíveis</strong></div>
									
																	<html:select property="idsMotivoRetirada" 
																		size="6" 
																		multiple="true" 
																		style="width:190px">
																	<logic:notEmpty name="colecaoImovelCobrancaMotivoRetirada">	
																		<html:options collection="colecaoImovelCobrancaMotivoRetirada" 
																			labelProperty="descricao" 
																			property="id" />
																	</logic:notEmpty>
																	</html:select>
																</td>
									
																<td width="5"><br>
																	<table width="50" align="center">
																		<tr>
																			<td align="center">
																				<input type="button" 
																					class="bottonRightCol"
																					onclick="javascript:MoverTodosDadosSelectMenu1PARAMenu2('FiltrarImovelCobrancaAdministrativaActionForm', 'idsMotivoRetirada', 'idsMotivoRetiradaSelecionados');chamarReloadSemParametro();"
																					value=" &gt;&gt; ">
																			</td>
																		</tr>
										
																		<tr>
																			<td align="center">
																				<input type="button" 
																					class="bottonRightCol"
																					onclick="javascript:MoverDadosSelectMenu1PARAMenu2('FiltrarImovelCobrancaAdministrativaActionForm', 'idsMotivoRetirada', 'idsMotivoRetiradaSelecionados');chamarReloadSemParametro();"
																					value=" &nbsp;&gt;  ">
																			</td>
																		</tr>
										
																		<tr>
																			<td align="center">
																				<input type="button" 
																					class="bottonRightCol"
																					onclick="javascript:MoverDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsMotivoRetirada', 'idsMotivoRetiradaSelecionados');chamarReloadSemParametro();"
																					value=" &nbsp;&lt;  ">
																			</td>
																		</tr>
										
																		<tr>
																			<td align="center">
																				<input type="button" 
																					class="bottonRightCol"
																					onclick="javascript:MoverTodosDadosSelectMenu2PARAMenu1('FiltrarImovelCobrancaAdministrativaActionForm', 'idsMotivoRetirada', 'idsMotivoRetiradaSelecionados');chamarReloadSemParametro();"
																					value=" &lt;&lt; ">
																			</td>
																		</tr>
																	</table>
																</td>
									
																<td>
																	<div align="left">
																		<strong>Selecionados</strong>
																	</div>
																	
																	<html:select property="idsMotivoRetiradaSelecionados" 
																		size="6" 
																		multiple="true" 
																		style="width:190px">
																		<logic:empty name="colecaoIdsMotivoRetiradaSelecionados">	
																				<html:option value=" ">&nbsp;</html:option>
																		</logic:empty>	
																	   				
																		 <logic:notEmpty name="colecaoIdsMotivoRetiradaSelecionados">	
																			<html:options collection="colecaoIdsMotivoRetiradaSelecionados" 
																			labelProperty="descricao" 
																			property="id" />
																		</logic:notEmpty>
																	</html:select>
																</td>
															</tr>
														</table>
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
									</div>
								</td>
							</tr>	
							<tr>
								<td colspan="2">
									<table>
										<tr>
											<td height="24" align="left" width="100%">
									          	<input type="button" 
									          		class="bottonRightCol" 
									          		value="Limpar" 
									          		onclick="window.location.href='/gsan/exibirFiltrarImovelCobrancaAdministrativaAction.do?menu=sim';"/>
											</td>
											
											<td>
												<input type="button" 
														name="Button"
														class="bottonRightCol" 
														value="Cancelar" 
														onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
														style="width: 80px" />
											</td>
											
											<td align="right">
												<input type="button" 
													name="Button" 
													id="botaoFiltrar"
													class="bottonRightCol" 
													value="Filtrar" 
													onClick="javascript:validarForm()" />
											</td>
										</tr>
									</table>
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
<%@ include file="/jsp/util/rodape.jsp"%>	
</html:form>
</body>
</html:html>
