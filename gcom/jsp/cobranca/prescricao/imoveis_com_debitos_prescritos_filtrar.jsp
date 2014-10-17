<%@page import="gcom.cobranca.bean.FiltroImoveisComDebitosPrescritosHelper"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gsan"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<%@ page import="gcom.util.ConstantesSistema"%>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/engine.js'> </script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/util.js'> </script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/interface/AjaxService.js'> </script>

<html:javascript staticJavascript="false"  formName="FiltrarImoveisComDebitosPrescritosActionForm"/>

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>

<style type="text/css">

.desabilitar {
 background-color:#EFEFEF;
 border:0;
 color: #000000;
}

</style>

<script language="JavaScript">
	
	function validarFormulario(){
		
		var form = document.forms[0];
		
		if(validateFiltrarImoveisComDebitosPrescritosActionForm(form) && validarCampos() && validaTodosPeriodos(form)){
		
			form.action = "/gsan/filtrarImoveisComDebitosPrescritosAction.do";
			submeterFormPadrao(form);
		}
	}
	
	function validarCampos(){
		
		var form = document.forms[0];
		
		
		var msg = verificarAtributosInicialFinal(form.idLocalidadeInicial,form.idLocalidadeFinal,"Código da Localidade Inicial", "Código da Localidade Final");
		
		if( msg != ""){
			alert(msg);
			return false;
		}else{
			msg = verificarAtributosInicialFinal(form.codigoSetorComercialInicial,form.codigoSetorComercialFinal,"Código do Setor Comercial Inicial", "Código do Setor Comercial Final");
			if( msg != ""){
				alert(msg);
				return false;
			}else{
				
				msg = verificarAtributosInicialFinal(form.numeroQuadraInicial,form.numeroQuadraFinal,"Quadra Inicial", "Quadra Final");
				if( msg != ""){
					alert(msg);
					return false;
				}
			}
		}
	
		return true;
	}

	
	function verificarAtributosInicialFinal(campoInicio, campoFim, nomeCampoInicio, nomeCampoFim){
		
		var msg = ""; 
        
		if (campoInicio.value.length > 0 && (campoFim.value == null || campoFim.value == '')) {
			campoFim.value = campoInicio.value;
		}else if (campoInicio.value.length > 0 && campoFim.value.length < 1){
			msg = "Informe "+nomeCampoFim+".";
		} else if (campoInicio.value.length < 1 && campoFim.value.length > 0) {
			msg = "Informe "+nomeCampoInicio+".";
		}else {
			if (parseInt(campoInicio.value) > parseInt(campoFim.value)){
				msg = nomeCampoFim+" deve ser maior ou igual a "+nomeCampoInicio+".";
			}
		}
		
		return msg;
	}
	
	
	
	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg){
		
		var form = document.forms[0];
			
		if (url == 'exibirPesquisarSetorComercialAction.do'){
			
			if (form.idLocalidadeFinal.value > form.idLocalidadeInicial.value){
				
				alert("Código da Localidade Final é maior que Código da Localidade Inicial, não é permitido informar o Setor Comercial.");
				
				limparOrigem(3);
				return;
			}
		}
		
		if (url == 'exibirPesquisarQuadraAction.do'){
			
			if (form.codigoSetorComercialFinal.value > form.codigoSetorComercialInicial.value){
				
				alert("Código do Setor Comercial Final é maior que Código da Setor Comercial Inicial, não é permitido informar a Quadra.");
				
				limparOrigem(5);
				return;
			}
		}
		
		
		if (objeto == null || codigoObjeto == null){
     		
			if(tipo == "" ){
      			abrirPopup(url,altura, largura);
     		}else{
	  			abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
	 		}
 		}else{
 			
			if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
				alert(msg);
			}else{
				
				abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto, altura, largura);
			}
		}
		
	}
	
  	function limpar(){

  		var form = document.forms[0];
  		
  		form.idPrescricaoComando.value = "";
    	form.tituloPrescricaoComando.value = "";
  		form.idImovel.value = '';
    	form.inscricaoImovel.value = '';
  		form.idGerenciaRegional.value = '';
		form.idGerenciaRegional.selectedIndex = 0;
  		form.idUnidadeNegocio.value = '';
		form.idUnidadeNegocio.selectedIndex = 0;
		form.idLocalidadeInicial.value = '';
		form.descricaoLocalidadeInicial.value = '';
		form.idLocalidadeFinal.value = '';
		form.descricaoLocalidadeFinal.value = '';
		form.codigoSetorComercialInicial.value = '';
		form.descricaoSetorComercialInicial.value = '';
		form.codigoSetorComercialFinal.value = '';
		form.descricaoSetorComercialFinal.value = '';
		form.numeroQuadraInicial.value = "";
	   	form.numeroQuadraFinal.value = "";
	   	form.indicadorSituacaoDebitoPrescrito[0].checked = false;
		form.indicadorSituacaoDebitoPrescrito[1].checked = false;
		form.indicadorSituacaoDebitoPrescrito[2].checked = true;
	   	form.idClienteRelacaoTipo.value = '';
		form.idClienteRelacaoTipo.selectedIndex = 0;
		form.idCliente.value ='';
		form.nomeCliente.value = '';
		form.arquivoCarregado.value = '';
		form.idCategoria.value = '';
		form.idCategoria.selectedIndex = 0;
		form.idSubCategoria.value = '';
		form.idSubCategoria.selectedIndex = 0;
		form.idLigacaoAguaSituacao.value='';
		form.idLigacaoAguaSituacao.selectedIndex = 0;
		form.idLigacaoEsgotoSituacao.value='';
		form.idLigacaoEsgotoSituacao.selectedIndex = 0;
		form.periodoReferenciaDebitoInicial.value = '';
		form.periodoReferenciaDebitoFinal.value = '';
		form.periodoVencimentoDebitoInicial.value = '';
		form.periodoVencimentoDebitoFinal.value = '';
		
  		form.action='exibirFiltrarImoveisComDebitosPrescritosAction.do?menu=sim';
	    form.submit();
  	}
  	
  	function replicarLocalidade(){
		var form = document.forms[0];
		
		if (form.idLocalidadeFinal.value != form.idLocalidadeInicial.value){
			
			limparOrigem(3);
  		}
		
		if (form.idLocalidadeFinal.value == '' || form.idLocalidadeFinal.value == form.idLocalidadeInicial.value){
			
			form.idLocalidadeFinal.value = form.idLocalidadeInicial.value;
			form.codigoSetorComercialInicial.focus;
		}
	}
	
	function replicarSetorComercial(){
		var form = document.forms[0];
		
		if (form.codigoSetorComercialFinal.value != form.codigoSetorComercialInicial.value){
		
			limparOrigem(5);
		}
		
		if (form.codigoSetorComercialFinal.value == '' || form.codigoSetorComercialFinal.value == form.codigoSetorComercialInicial.value){
			
			form.codigoSetorComercialFinal.value = form.codigoSetorComercialInicial.value;
		}
	} 
	
	function replicarQuadra(){
		
		var form = document.forms[0];
		
		if (form.numeroQuadraFinal.value != form.numeroQuadraInicial.value){
			
			limparOrigem(4);
		}
		
		if (form.numeroQuadraFinal.value == '' 
				|| form.numeroQuadraFinal.value == form.numeroQuadraInicial.value){
			
			form.numeroQuadraFinal.value = form.numeroQuadraInicial.value;	
		}
	}
	
	function limparOrigem(tipo){
		var form = document.forms[0];
		
		
		switch(tipo){
		
			case 1: //De descrição localidade inicial pra baixo
	
				form.descricaoLocalidadeInicial.value = "";
				form.idLocalidadeFinal.value = "";
				form.descricaoLocalidadeFinal.value = "";
				form.codigoSetorComercialInicial.value = "";
			    form.codigoSetorComercialFinal.value = "";
			    form.descricaoSetorComercialInicial.value = "";
				form.descricaoSetorComercialFinal.value = "";
				form.numeroQuadraInicial.value = "";
			   	form.numeroQuadraFinal.value = "";
				break;
			    
			case 2: //De descrição setor inicial para baixo
	
			   	form.descricaoSetorComercialInicial.value = "";
			   	form.codigoSetorComercialFinal.value = "";
			  	form.descricaoSetorComercialFinal.value = "";
			   	form.numeroQuadraInicial.value = "";
			   	form.numeroQuadraFinal.value = "";
			   	form.numeroQuadraInicial.value = "";
			   	form.numeroQuadraFinal.value = "";
			   
			   break
			   
			case 3: //De código setor inicial para baixo
	
			   form.codigoSetorComercialInicial.value = "";
			   form.descricaoSetorComercialInicial.value = "";
			   form.codigoSetorComercialFinal.value = "";
			   form.descricaoSetorComercialFinal.value = "";
			   form.numeroQuadraInicial.value = "";
			   form.numeroQuadraFinal.value = "";
			   break;
			case 4: //De quadra final para baixo
				
			   form.numeroQuadraFinal.value = "";
			   break;
			case 5: //De quadra inicial para baixo
				
			   form.numeroQuadraInicial.value = "";
			   form.numeroQuadraFinal.value = "";
			   break;
			
		}
	}
	
	function limparBorrachaOrigem(tipo){
		var form = document.forms[0];
		
		switch(tipo){
			case 1: //De id localidade inicial pra baixo

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
				break;
			case 2: //De código setor inicial para baixo
		     	
		     	form.codigoSetorComercialInicial.value = "";
		     	form.descricaoSetorComercialInicial.value = "";
		     	form.codigoSetorComercialFinal.value = "";
		     	form.descricaoSetorComercialFinal.value = "";
		     	form.numeroQuadraInicial.value = "";
			   	form.numeroQuadraFinal.value = "";
		     	break;
			case 3: //De quadra para baixo
		     	
				form.numeroQuadraInicial.value = "";
			   	form.numeroQuadraFinal.value = "";
		     	break;
			case 4: //Elo
		     	
				form.idLocalidadeElo.value = "";
				form.descricaoLocalidadeElo.value = "";
		     	break;
			case 5: //Cliente
		     	
				form.idCliente.value = "";
				form.nomeCliente.value = "";
		     	break;
			case 6: //Imovel
		     	
				form.idImovel.value = "";
		    	form.inscricaoImovel.value = "";
		     	break;
			case 7: //Comando
		     	
				form.idPrescricaoComando.value = "";
		    	form.tituloPrescricaoComando.value = "";
		     	break;
		}
		
		form.action = 'exibirFiltrarImoveisComDebitosPrescritosAction.do?menu=sim';
	  	form.submit();
	}
	
	function limparBorrachaDestino(tipo){
		
		var form = document.forms[0];

		switch(tipo){
			case 1: // De id localidade final pra baixo
				
				form.idLocalidadeFinal.value = "";
				form.descricaoLocalidadeFinal.value = "";
				form.codigoSetorComercialFinal.value = "";
		   		form.descricaoSetorComercialFinal.value = "";
		   		form.numeroQuadraFinal.value = "";
				break;
			case 2: // De codigo setor final pra baixo	   
		   		
				form.codigoSetorComercialFinal.value = ""; 
		   		form.descricaoSetorComercialFinal.value = "";
		   		form.numeroQuadraFinal.value = "";
		   		break;
			case 3: // De quadra final pra baixo    
				
				form.numeroQuadraFinal.value = "";
		   		break;
		}
	}
	
	
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

		var form = document.forms[0];
		
		if (tipoConsulta == 'prescricaoComando') {

		     form.idPrescricaoComando.value = codigoRegistro;
		     form.tituloPrescricaoComando.value = descricaoRegistro;
		     form.tituloPrescricaoComando.style.color = "#000000";
		     
		     form.action = 'exibirFiltrarImoveisComDebitosPrescritosAction.do?objetoConsulta=13';
			 form.submit();
	    }
		
		if (tipoConsulta == 'imovel') {

		     form.idImovel.value = codigoRegistro;
		     form.inscricaoImovel.value = descricaoRegistro;
		     form.inscricaoImovel.style.color = "#000000";
		     
		     form.action = 'exibirFiltrarImoveisComDebitosPrescritosAction.do?objetoConsulta=12';
			 form.submit();
	    }
		
		if (tipoConsulta == 'elo') {
      		
      		form.idLocalidadeElo.value = codigoRegistro;
	  		form.descricaoLocalidadeElo.value = descricaoRegistro;
	  		form.descricaoLocalidadeElo.style.color = "#000000";
	  		
			form.action = 'exibirFiltrarImoveisComDebitosPrescritosAction.do?objetoConsulta=10';
		  	form.submit();
		}

		if (tipoConsulta == 'localidadeOrigem') {
      		
      		form.idLocalidadeInicial.value = codigoRegistro;
	  		form.descricaoLocalidadeInicial.value = descricaoRegistro;
	  		form.descricaoLocalidadeInicial.style.color = "#000000";
	  		
	  	
	  		if (form.idLocalidadeFinal.value == '' || form.idLocalidadeFinal.value == form.idLocalidadeInicial.value){
	  			
	  			form.idLocalidadeFinal.value = codigoRegistro;
	      		form.descricaoLocalidadeFinal.value = descricaoRegistro;
		  		form.descricaoLocalidadeFinal.style.color = "#000000";
	  		}
	  		
	  		if (form.idLocalidadeFinal.value != form.idLocalidadeInicial.value){
				
				limparOrigem(3);
				form.action = 'exibirFiltrarImoveisComDebitosPrescritosAction.do?objetoConsulta=1';
			  	form.submit();
			}
		}

		if (tipoConsulta == 'localidadeDestino') {
		
      		form.idLocalidadeFinal.value = codigoRegistro;
      		form.descricaoLocalidadeFinal.value = descricaoRegistro;
	  		form.descricaoLocalidadeFinal.style.color = "#000000";

			if (form.idLocalidadeFinal.value != form.idLocalidadeInicial.value){
				
				limparOrigem(3);
				form.action = 'exibirFiltrarImoveisComDebitosPrescritosAction.do?objetoConsulta=3';
			  	form.submit();
			}
		}
		
		if (tipoConsulta == 'cliente') {
      		
      		form.idCliente.value = codigoRegistro;
	  		form.nomeCliente.value = descricaoRegistro;
	  		form.nomeCliente.style.color = "#000000";
			form.action = 'exibirFiltrarImoveisComDebitosPrescritosAction.do?objetoConsulta=11';
		  	form.submit();
		}
	}
	
	function recuperarDadosQuatroParametros(idRegistro, descricaoRegistro, codigoRegistro, tipoConsulta) {
	
		var form = document.forms[0];

		if (tipoConsulta == 'setorComercialOrigem') {
		  	
			form.codigoSetorComercialInicial.value = codigoRegistro;
		  	form.descricaoSetorComercialInicial.value = descricaoRegistro;
		  	form.descricaoSetorComercialInicial.style.color = "#000000"; 
		  	
		  	if (form.codigoSetorComercialFinal.value == '' || form.codigoSetorComercialFinal.value == form.codigoSetorComercialInicial.value){
		  		
		  		form.codigoSetorComercialFinal.value = codigoRegistro;
		  		form.descricaoSetorComercialFinal.value = descricaoRegistro;
		  		form.descricaoSetorComercialFinal.style.color = "#000000";
		  	}
		  	
			if (form.idLocalidadeFinal.value != form.idLocalidadeInicial.value){
				
				limparOrigem(3);
			}
			
			if (form.codigoSetorComercialFinal.value != form.codigoSetorComercialInicial.value){
				
				limparOrigem(5);
			}
		  	
		  	form.action = 'exibirFiltrarImoveisComDebitosPrescritosAction.do?objetoConsulta=2';
		  	form.submit();
		}

		if (tipoConsulta == 'setorComercialDestino') {
		  	
			form.codigoSetorComercialFinal.value = codigoRegistro;
		  	form.descricaoSetorComercialFinal.value = descricaoRegistro;
		  	form.descricaoSetorComercialFinal.style.color = "#000000";
		  	
			if (form.codigoSetorComercialFinal.value != form.codigoSetorComercialInicial.value){
				
				limparOrigem(5);
				limparOrigem(8);
			}
			
		  	form.action = 'exibirFiltrarImoveisComDebitosPrescritosAction.do?objetoConsulta=4';
		  	form.submit();
		}
		
		if (tipoConsulta == 'quadraOrigem') {
		    
		    form.numeroQuadraInicial.value = codigoRegistro;
			form.numeroQuadraFinal.value = codigoRegistro;
			
			if (form.codigoSetorComercialFinal.value != form.codigoSetorComercialInicial.value){
				
				limparOrigem(5);
				limparOrigem(8);
			}
			
			form.action = 'exibirFiltrarImoveisComDebitosPrescritosAction.do?objetoConsulta=5';
		  	form.submit();
	  	}
		
		if (tipoConsulta == 'quadraDestino') {
		    
			form.numeroQuadraFinal.value = codigoRegistro;
			
			if (form.codigoSetorComercialFinal.value != form.codigoSetorComercialInicial.value){
				
				limparOrigem(5);
				limparOrigem(8);
			}
			
			form.action = 'exibirFiltrarImoveisComDebitosPrescritosAction.do?objetoConsulta=6';
		  	form.submit();
	  	}
	}
	
	function verificarBloqueioCampos(tipo){
		var form = document.forms[0];
	
		
		switch(tipo){
			case 1: 

				if (form.idLocalidadeFinal.value != form.idLocalidadeInicial.value){
					
					limparOrigem(3);
				}
			break;
			case 2: 
				
				if (form.codigoSetorComercialFinal.value != form.codigoSetorComercialInicial.value){
					
					limparOrigem(5);
					limparOrigem(8);
				}
			break;
			case 3: 
				
				if ((form.numeroQuadraFinal.value != form.numeroQuadraInicial.value)){
					
					limparOrigem(4);
				}
			break;
		}
	}
	
	function validaTodosPeriodos(form) {

    	if (comparaData(form.periodoRelacionamentoInicial.value, '>', form.periodoRelacionamentoFinal.value)){

			alert('Data Final do Período da Relação é anterior à Data Inicial do Período da Relação');
			return false;
		}
    	
    	if (comparaData(form.periodoVencimentoDebitoInicial.value, '>', form.periodoVencimentoDebitoFinal.value)){

			alert('Data Final do Período de Vencimento do Débito é anterior à Data Inicial do Período de Vencimento do Débito');
			return false;
		}
    	
    	if (comparaMesAno(form.periodoReferenciaDebitoInicial.value ,'>',form.periodoReferenciaDebitoFinal.value)){
    		
    		alert('Ano/Mês Final do Período de Referência do Débito é anterior ao Ano/Mês Inicial do Período de Referência do Débito');
			return false;
    	}

		return true;
    }
	
	function replicaDataPeriodoRelacao() {
		
		var form = document.forms[0];
		form.periodoRelacionamentoFinal.value = form.periodoRelacionamentoInicial.value;
	}
	
	function replicaDataPeriodoReferenciaDebito() {
		
		var form = document.forms[0];
		form.periodoReferenciaDebitoFinal.value = form.periodoReferenciaDebitoInicial.value;
	}
	
	function replicaDataPeriodoVencimentoDebito() {
		
		var form = document.forms[0];
		form.periodoVencimentoDebitoFinal.value = form.periodoVencimentoDebitoInicial.value;
	}
	
	function carregarSubcategorias() {
    	
		var form = document.forms[0];
    	var campo = form.idCategoria;
    	var count = 0;
    	var temSelecionado = 0;
    	var idCategoria;
    	var idSubCategoria = form.idSubCategoria;
    	
    	for(i = 1; i <= campo.length; i++){
			
    		if(campo[i - 1].selected){
				
				count ++;
				idCategoria = campo[i - 1].value;
				temSelecionado = 1;
			}
		}
    	
    	if (count == 1 && idCategoria != "-1") {
    		
    		form.idSubCategoria.disabled = false;
    		
    		AjaxService.carregaSubcategorias(idCategoria, {callback: 
				function(list) {
	    		
    				//Função que remove caso exista os valores da combo.  
	                DWRUtil.removeAllOptions(idSubCategoria);  
	                
    				//Adicionando valores na combo.  
	                DWRUtil.addOptions(idSubCategoria, {'-1':' '});
    				
	    
	                DWRUtil.addOptions(idSubCategoria, list);
	                
	     
	                
	             
    			}
    		});
    	} else {
    		
    		form.idSubCategoria.length = 0;
    		form.idSubCategoria.value = "-1";
    		form.idSubCategoria.disabled = true;
    	}
    }
	
	function controleCategoriaSubCategoria() {
    	
		var form = document.forms[0];
    	var obj = form.idCategoria;
    	
    	if (obj.selectedIndex == 0) {
    		form.idSubCategoria.disabled = true;
    		form.idSubCategoria[0].selected = true;
    	}else {
    		if (form.idCategoria.selectedIndex == 0 || form.idCategoria.selectedIndex == -1) {
    			form.idSubCategoria.disabled = true;
    			form.idSubCategoria.value = "-1";
    		}else {
    			form.idSubCategoria.disabled = false;
    		}
    	}
    }
	
	function validaArquivo(){
		
		var form = document.forms[0];
		form.action = 'exibirFiltrarImoveisComDebitosPrescritosAction.do?menu=sim';
		form.submit();
	}
	
	function desabilitarDemaisCamposCliente(){

		var form = document.forms[0];
  		
		var informouCliente = false;
		if (form.idCliente.value != null && form.idCliente.value != ''){
			
			informouCliente = true;
		}
		
		if (informouCliente == false){
			
			form.idClienteRelacaoTipo.value = '';
			form.idClienteRelacaoTipo.selectedIndex = 0;
			form.periodoRelacionamentoInicial.value = '';
			form.periodoRelacionamentoFinal.value = '';
			
		  	form.idClienteRelacaoTipo.disabled = true;
		  	form.periodoRelacionamentoInicial.disabled = true;
		  	form.periodoRelacionamentoFinal.disabled = true;
			
  		  	form.idClienteRelacaoTipo.className = 'desabilitar';
  		  	form.periodoRelacionamentoInicial.className = 'desabilitar';
  		  	form.periodoRelacionamentoFinal.className = 'desabilitar';
		}else{
			
		  	form.idClienteRelacaoTipo.disabled = false;
		  	form.periodoRelacionamentoInicial.disabled = false;
		  	form.periodoRelacionamentoFinal.disabled = false;
		  	
		  	form.idClienteRelacaoTipo.className = '';
		  	form.periodoRelacionamentoInicial.className = '';
		  	form.periodoRelacionamentoFinal.className = '';
		}
  	}
	
	function iniciarTela(){
		
		desabilitarDemaisCamposCliente();
		controleCategoriaSubCategoria();
	}
	
</script>

</head>

<body leftmargin="5" topmargin="5" onload="javascript:iniciarTela();">

<html:form action="/exibirFiltrarImoveisComDebitosPrescritosAction.do"
	name="FiltrarImoveisComDebitosPrescritosActionForm"
	type="gcom.gui.cobranca.prescricao.FiltrarImoveisComDebitosPrescritosActionForm"
	enctype="multipart/form-data"
	method="post">

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

<html:hidden property="arquivoCarregado"/>

<table width="770" border="0" cellspacing="4" cellpadding="0">
	  <tr>
	    <td width="149" valign="top" class="leftcoltext">
	      <div align="center">
	
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
					<td class="parabg">Filtrar Imóveis com Débitos Prescritos</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				
				<tr>
					<td colspan="2">Para filtrar imóveis com débitos prescritos, informe os dados abaixo:</td>
				</tr>
				
				<tr>
					<td><strong>Comando:</strong></td>
										
					<td>
           		
	  					<html:text maxlength="9" 
							property="idPrescricaoComando" 
							size="9"
							onkeyup="javascript:verificaNumeroInteiro(this);"
							onkeypress="validaEnterComMensagem(event, 'exibirFiltrarImoveisComDebitosPrescritosAction.do?objetoConsulta=13','idPrescricaoComando','Imóvel');"
							/>	
						
						<a href="javascript:chamarPopup('exibirPesquisarComandoPrescricaoAction.do', 'prescricaoComando', null, null, 275, 480, '', document.forms[0].idPrescricaoComando);">
						<img width="23" 
							height="21" 
							border="0"
							src="<bean:message key="caminho.imagens"/>pesquisa.gif"
							title="Pesquisar Comando" /></a> 
						
						<logic:present name="comandoEncontrado" scope="request">
							<html:text property="tituloPrescricaoComando" 
								size="50"
								maxlength="50" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 

						<logic:notPresent name="comandoEncontrado" scope="request">
							<html:text property="tituloPrescricaoComando" 
								size="50"
								maxlength="50" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>
						
						<a href="javascript:limparBorrachaOrigem(7);"> 
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
							border="0" 
							title="Apagar" />
						</a>
					</td>
				</tr>
				
				<tr>
					<td><strong>Situação do Débito Prescrito:</strong></td>
					
					<td>
						<html:radio property="indicadorSituacaoDebitoPrescrito" value="<%=""+FiltroImoveisComDebitosPrescritosHelper.MARCADO%>" /> <strong>Marcado</strong>
						<html:radio property="indicadorSituacaoDebitoPrescrito" value="<%=""+FiltroImoveisComDebitosPrescritosHelper.DESMARCADO%>" /> <strong>Desmarcado</strong>
						<html:radio property="indicadorSituacaoDebitoPrescrito" value="<%=""+FiltroImoveisComDebitosPrescritosHelper.TODOS%>" /> <strong>Todos</strong>
					</td>
				</tr>
				
				<tr>
					<td><strong>Im&oacute;vel:</strong></td>
										
					<td>
           		
	  					<html:text maxlength="9" 
							property="idImovel" 
							size="9"
							onkeyup="javascript:verificaNumeroInteiro(this);"
							onkeypress="validaEnterComMensagem(event, 'exibirFiltrarImoveisComDebitosPrescritosAction.do?objetoConsulta=12','idImovel','Imóvel');"
							/>	
						
						<a href="javascript:chamarPopup('exibirPesquisarImovelAction.do', 'imovel', null, null, 275, 480, '', document.forms[0].idImovel);">
						<img width="23" 
							height="21" 
							border="0"
							src="<bean:message key="caminho.imagens"/>pesquisa.gif"
							title="Pesquisar Imóvel" /></a> 
						
						<logic:present name="imovelEncontrado" scope="request">
							<html:text property="inscricaoImovel" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 

						<logic:notPresent name="imovelEncontrado" scope="request">
							<html:text property="inscricaoImovel" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>
						
						<a href="javascript:limparBorrachaOrigem(6);"> 
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
							border="0" 
							title="Apagar" />
						</a>
					</td>
				</tr>
				
				<tr>
             		<td height="10" colspan="2"> 
	             		<div align="right"> 
	                 		<hr>
	               		</div>
	               		<div align="right"> </div>
               		</td>
           		</tr>
				
				<tr>
					<td colspan="2">
						<strong>Dados de Localização Geográfica do Imóvel:</strong>
						<table align="center">
							<tr>
								<td>
									<strong>Ger&ecirc;ncia Regional:</strong>
								</td>
			
								<td>
									<strong> 
									<html:select property="idGerenciaRegional" 
										style="width: 230px;">
										
										<html:option
											value="">&nbsp;
										</html:option>
								
										<logic:present name="colecaoGerenciaRegional" scope="request">
											<html:options collection="colecaoGerenciaRegional"
												labelProperty="nome" 
												property="id" />
										</logic:present>
									</html:select> 														
									</strong>
								</td>
							</tr>		
			
							<tr>
								<td>
									<strong>Unidade de Neg&oacute;cio:</strong>
								</td>
			
								<td>
									<strong> 
									<html:select property="idUnidadeNegocio" 
										style="width: 230px;">
										
										<html:option
											value="">&nbsp;
										</html:option>
								
										<logic:present name="colecaoUnidadeNegocio" scope="request">
											<html:options collection="colecaoUnidadeNegocio"
												labelProperty="nome" 
												property="id" />
										</logic:present>
									</html:select> 														
									</strong>
								</td>
							</tr>
							
							<tr>
								<td><strong>Elo:</strong></td>
								
								<td>
									
									<html:text maxlength="3" 
										property="idLocalidadeElo" 
										size="3" 
										onkeypress="javascript:validaEnter(event, 'exibirFiltrarImoveisComDebitosPrescritosAction.do?objetoConsulta=10', 'idLocalidadeElo');"/>
										
									<a href="javascript:chamarPopup('exibirPesquisarEloAction.do', null, null , null, 275, 480, null);">
										<img width="23" 
											height="21" 
											border="0" 
											src="<bean:message key="caminho.imagens"/>pesquisa.gif"
											title="Pesquisar Elo" /></a>
											
			
									<logic:present name="localidadeEloEncontrada" scope="request">
										<html:text property="descricaoLocalidadeElo" 
											size="30"
											maxlength="30" 
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: #000000" />
									</logic:present> 
			
									<logic:notPresent name="localidadeEloEncontrada" scope="request">
										<html:text property="descricaoLocalidadeElo" 
											size="30"
											maxlength="30" 
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: red" />
									</logic:notPresent>
			
									
									<a href="javascript:limparBorrachaOrigem(4);"> 
										<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
											border="0" 
											title="Apagar" /></a>
								</td>
							</tr>		

							<tr>
								<td><strong>Localidade Inicial:</strong></td>
								
								<td>
									
									<html:text maxlength="3" 
										property="idLocalidadeInicial" 
										size="3"
										onblur="javascript:replicarLocalidade();"
										onkeypress="javascript:limparOrigem(3);validaEnter(event, 'exibirFiltrarImoveisComDebitosPrescritosAction.do?objetoConsulta=1', 'idLocalidadeInicial');"/>
										
									<a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'origem', null , null, 275, 480, null);limparOrigem(3);">
										<img width="23" 
											height="21" 
											border="0" 
											src="<bean:message key="caminho.imagens"/>pesquisa.gif"
											title="Pesquisar Localidade" /></a>
											
			
									<logic:present name="localidadeInicialEncontrada" scope="request">
										<html:text property="descricaoLocalidadeInicial" 
											size="30"
											maxlength="30" 
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: #000000" />
									</logic:present> 
			
									<logic:notPresent name="localidadeInicialEncontrada" scope="request">
										<html:text property="descricaoLocalidadeInicial" 
											size="30"
											maxlength="30" 
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: red" />
									</logic:notPresent>
			
									
									<a href="javascript:limparBorrachaOrigem(1);"> 
										<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
											border="0" 
											title="Apagar" /></a>
								</td>
							</tr>
							
							<tr>
								<td><strong>Setor Comercial Inicial:</strong></td>
								
								<td>
									
									<html:text maxlength="3" 
										property="codigoSetorComercialInicial" 
										size="3"
										onblur="javascript:replicarSetorComercial();verificarBloqueioCampos(1);"
										onkeypress="javascript:limparOrigem(2);validaEnterDependencia(event, 'exibirFiltrarImoveisComDebitosPrescritosAction.do?objetoConsulta=2', document.forms[0].codigoSetorComercialInicial, document.forms[0].idLocalidadeInicial.value, 'Localidade Inicial');"/>
										
									<a href="javascript:chamarPopup('exibirPesquisarSetorComercialAction.do', 'setorComercialOrigem', 'idLocalidade', document.forms[0].idLocalidadeInicial.value , 275, 480, 'Informe Localidade Inicial');
									         limparOrigem(3);">
										<img width="23" 
											height="21" 
											border="0" 
											src="<bean:message key="caminho.imagens"/>pesquisa.gif"
											title="Pesquisar Setor Comercial" /></a>
											
			
									<logic:present name="setorComercialInicialEncontrado" scope="request">
										<html:text property="descricaoSetorComercialInicial" 
											size="30"
											maxlength="30" 
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: #000000" />
									</logic:present> 
			
									<logic:notPresent name="setorComercialInicialEncontrado" scope="request">
										<html:text property="descricaoSetorComercialInicial" 
											size="30"
											maxlength="30" 
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: red" />
									</logic:notPresent>
									
									<a href="javascript:limparBorrachaOrigem(2);"> 
										<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
											border="0" 
											title="Apagar" /></a>
								</td>
							</tr>
							
							<tr>
								<td><strong>Quadra Inicial:</strong></td>
								
								<td>
									
									<html:text maxlength="4" 
										property="numeroQuadraInicial" 
										size="4"
										onblur="javascript:replicarQuadra();verificarBloqueioCampos(2);"
										onkeypress="javascript:limparOrigem(4);validaEnterDependencia(event, 'exibirFiltrarImoveisComDebitosPrescritosAction.do?objetoConsulta=5', document.forms[0].numeroQuadraInicial, document.forms[0].codigoSetorComercialInicial.value, 'Setor Inicial');"/>
										
									<a href="javascript:chamarPopup('exibirPesquisarQuadraAction.do', 'quadraOrigem', 'codigoSetorComercial', document.forms[0].codigoSetorComercialInicial.value , 275, 480, 'Informe Setor Comercial Inicial');
									         limparOrigem(5);">
										<img width="23" 
											height="21" 
											border="0" 
											src="<bean:message key="caminho.imagens"/>pesquisa.gif"
											title="Pesquisar Quadra" /></a>
									
									<logic:present name="quadraInicialNaoEncontrada" scope="request">
										<span style="color: #ff0000" id="msgQuadraInicial">
											<logic:present name="msgQuadraInicial" scope="request">
												<bean:write scope="request" name="msgQuadraInicial" />
											</logic:present>
										</span>
									</logic:present>
									
									<a href="javascript:limparBorrachaOrigem(3);"> 
										<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
											border="0" 
											title="Apagar" /></a>
								</td>
							</tr>
			           		
							<tr>
								<td><strong>Localidade Final:</strong></td>
								
								<td>
									
									<html:text maxlength="3" 
										property="idLocalidadeFinal" 
										size="3"
										onblur="javascript:verificarBloqueioCampos(1);"
										onkeypress="validaEnter(event, 'exibirFiltrarImoveisComDebitosPrescritosAction.do?objetoConsulta=3', 'idLocalidadeFinal');"/>
										
									<a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'destino', null, null, 275, 480, null);limparBorrachaDestino(1);">
										<img width="23" 
											height="21" 
											border="0" 
											src="<bean:message key="caminho.imagens"/>pesquisa.gif"
											title="Pesquisar Localidade" /></a>
											 
			
									<logic:present name="localidadeFinalEncontrada" scope="request">
										<html:text property="descricaoLocalidadeFinal" 
											size="30"
											maxlength="30" 
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: #000000" />
									</logic:present> 
			
									<logic:notPresent name="localidadeFinalEncontrada" scope="request">
										<html:text property="descricaoLocalidadeFinal" 
											size="30"
											maxlength="30" 
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: red" />
									</logic:notPresent>
			
									
									<a href="javascript:limparBorrachaDestino(1);"> 
										<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
											border="0" 
											title="Apagar" /></a>
								</td>
							</tr>
							
							<tr>
								<td><strong>Setor Comercial Final:</strong></td>
								
								<td>
									
									<html:text maxlength="3" property="codigoSetorComercialFinal"
										size="3"
										onblur="javascript:verificarBloqueioCampos(1);"
										onkeypress="validaEnterDependencia(event, 'exibirFiltrarImoveisComDebitosPrescritosAction.do?objetoConsulta=4', document.forms[0].codigoSetorComercialFinal, document.forms[0].idLocalidadeFinal.value, 'Localidade Final');"
										/>
											
									<a href="javascript:chamarPopup('exibirPesquisarSetorComercialAction.do', 'setorComercialDestino', 'idLocalidade', document.forms[0].idLocalidadeFinal.value, 275, 480, 'Informe Localidade Final');
										        limparBorrachaDestino(2);">
										<img width="23" 
											height="21" 
											border="0" 
											src="<bean:message key="caminho.imagens"/>pesquisa.gif"
											title="Pesquisar Setor Comercial" /></a>
			
									<logic:present name="setorComercialFinalEncontrado" scope="request">
										<html:text property="descricaoSetorComercialFinal" 
											size="30"
											maxlength="30" 
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: #000000" />
									</logic:present> 
			
									<logic:notPresent name="setorComercialFinalEncontrado" scope="request">
										<html:text property="descricaoSetorComercialFinal" 
											size="30"
											maxlength="30" 
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: red" />
									</logic:notPresent>
									
									<a href="javascript:limparBorrachaDestino(2);"> 
										<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
											border="0" 
											title="Apagar" /></a>
								</td>
							</tr>
							
							<tr>
								<td><strong>Quadra Final:</strong></td>
								
								<td>
									
									<html:text maxlength="4" 
										property="numeroQuadraFinal" 
										size="4"
										onblur="javascript:verificarBloqueioCampos(2);"
										onkeypress="javascript:validaEnterDependencia(event, 'exibirFiltrarImoveisComDebitosPrescritosAction.do?objetoConsulta=6', document.forms[0].numeroQuadraFinal, document.forms[0].codigoSetorComercialFinal.value, 'Setor Comercial Final');"/>
										
									<a href="javascript:chamarPopup('exibirPesquisarQuadraAction.do', 'quadraDestino', 'codigoSetorComercial', document.forms[0].codigoSetorComercialFinal.value , 275, 480, 'Informe Setor Comercial Final');
									         limparBorrachaDestino(3);">
										<img width="23" 
											height="21" 
											border="0" 
											src="<bean:message key="caminho.imagens"/>pesquisa.gif"
											title="Pesquisar Quadra" /></a>
									
									<logic:present name="quadraFinalNaoEncontrada" scope="request">
										<span style="color: #ff0000" id="msgQuadraFinal">
											<logic:present name="msgQuadraFinal" scope="request">
												<bean:write scope="request" name="msgQuadraFinal" />
											</logic:present>
										</span>
									</logic:present>
									
									<a href="javascript:limparBorrachaDestino(3);"> 
										<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
											border="0" 
											title="Apagar" /></a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				
				<tr>
             		<td height="10" colspan="2"> 
	             		<div align="right"> 
	                 		<hr>
	               		</div>
	               		<div align="right"> </div>
               		</td>
           		</tr>
           		
           		<tr>
					<td colspan="2">
						<strong>Dados do Cliente:</strong>
						<table align="center">
			           		<tr>
								<td><strong>Cliente:</strong></td>
								
								<td>
									
									<html:text maxlength="10" 
										property="idCliente" 
										size="10"
										onblur="javascript:desabilitarDemaisCamposCliente();" 
										onkeypress="javascript:validaEnter(event, 'exibirFiltrarImoveisComDebitosPrescritosAction.do?objetoConsulta=11', 'idCliente');"/>
										
									<a href="javascript:chamarPopup('exibirPesquisarClienteAction.do', 'cliente', null, null , 275, 480, null)">
										<img width="23" 
											height="21" 
											border="0" 
											src="<bean:message key="caminho.imagens"/>pesquisa.gif"
											title="Pesquisar Cliente" /></a>
											
			
									<logic:present name="clienteEncontrado" scope="request">
										<html:text property="nomeCliente" 
											size="30"
											maxlength="30" 
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: #000000" />
									</logic:present> 
			
									<logic:notPresent name="clienteEncontrado" scope="request">
										<html:text property="nomeCliente" 
											size="30"
											maxlength="30" 
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: red" />
									</logic:notPresent>
			
									
									<a href="javascript:limparBorrachaOrigem(5);"> 
										<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
											border="0" 
											title="Apagar" /></a>
								</td>
							</tr>
							
							<tr>
								<td>
									<strong>Tipo da Relação:</strong>
								</td>
			
								<td>
									<strong> 
									<html:select property="idClienteRelacaoTipo" 
										style="width: 230px;">
										
										<html:option
											value="">&nbsp;
										</html:option>
								
										<logic:present name="colecaoClienteRelacaoTipo" scope="request">
											<html:options collection="colecaoClienteRelacaoTipo"
												labelProperty="descricao" 
												property="id" />
										</logic:present>
									</html:select> 														
									</strong>
								</td>
							</tr>
							
							<tr>
				                <td>
				                	<strong>Per&iacute;odo da Relação:</strong>
				                </td>
			                
				                <td>
				                	<span class="style2">
				                	
				                	<strong> 
										
										<html:text property="periodoRelacionamentoInicial" 
											size="11" 
											maxlength="10" 
											onkeyup="mascaraData(this, event);replicaDataPeriodoRelacao();"/>
										
										<a href="javascript:abrirCalendarioReplicando('FiltrarImoveisComDebitosPrescritosActionForm', 'periodoRelacionamentoInicial','periodoRelacionamentoFinal');">
										<img border="0" 
											src="<bean:message key='caminho.imagens'/>calendario.gif" 
											width="16" 
											height="15" 
											border="0" alt="Exibir Calendário"/>
										</a>
										
										a 
										
										<html:text property="periodoRelacionamentoFinal" 
											size="11" 
											maxlength="10" 
											onkeyup="mascaraData(this, event)"/>
											
										<a href="javascript:abrirCalendario('FiltrarImoveisComDebitosPrescritosActionForm', 'periodoRelacionamentoFinal');">
										<img border="0" 
											src="<bean:message key='caminho.imagens'/>calendario.gif" 
											width="16" 
											height="15" 
											border="0" 
											alt="Exibir Calendário" />
										</a>
										
										</strong>(dd/mm/aaaa)<strong> 
				                  	</strong>
				                  	</span>
				              	</td>
			              	</tr>
						</table>
					</td>
				</tr>
				
				<tr>
             		<td height="10" colspan="2"> 
	             		<div align="right"> 
	                 		<hr>
	               		</div>
	               		<div align="right"> </div>
               		</td>
           		</tr>
           		
           		<tr>
						<td width="20%"><strong> Arquivo:</strong>
						</td>
					<td>
						<html:file property="arquivoMatricula" accept="text" onchange="validaArquivo();"></html:file>
						<logic:notEmpty  name="arquivo" scope="session">
							<bean:write name="arquivo" property="fileName" scope="session"/>
						</logic:notEmpty>
						<logic:notEmpty  name="FiltrarImoveisComDebitosPrescritosActionForm" property="enderecoArquivoDownload">
							<br/><a href="exibirFiltrarImoveisComDebitosPrescritosAction.do?download=true"><bean:write name="FiltrarImoveisComDebitosPrescritosActionForm" property="arquivoDownload"></bean:write> </a>
						</logic:notEmpty>
						<logic:empty  name="FiltrarImoveisComDebitosPrescritosActionForm" property="enderecoArquivoDownload">
							<br/><bean:write name="FiltrarImoveisComDebitosPrescritosActionForm" property="arquivoDownload"></bean:write>
						</logic:empty>
					</td>
				</tr>
				
				<tr>
             		<td height="10" colspan="2"> 
	             		<div align="right"> 
	                 		<hr>
	               		</div>
	               		<div align="right"> </div>
               		</td>
           		</tr>
				
				<tr>
					<td colspan="2">
						<strong>Características dos Imóveis:</strong>
						<table align="center">
							<tr>
								<td>
									<strong>Categoria:</strong>
								</td>
			
								<td>
									<strong> 
									<html:select property="idCategoria" 
										style="width: 230px;"
										multiple="true"
										onchange="javascript:carregarSubcategorias();">
										
										<html:option
											value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
										</html:option>
								
										<logic:present name="colecaoCategoria" scope="request">
											<html:options collection="colecaoCategoria"
												labelProperty="descricao" 
												property="id"/>
										</logic:present>
									</html:select> 														
									</strong>
								</td>
							</tr>
							
							<tr>
								<td>
									<strong>Subcategoria:</strong>
								</td>
			
								<td>
									<strong> 
									<html:select property="idSubCategoria" 
										style="width: 230px;"
										multiple="true">
										
										<html:option
											value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
										</html:option>
								
										<logic:present name="colecaoSubCategoria" scope="request">
											<html:options collection="colecaoSubCategoria"
												labelProperty="descricao" 
												property="id" />
										</logic:present>
									</html:select> 														
									</strong>
								</td>
							</tr>
							
							<tr>
								<td>
									<strong>Situação da Ligação de Água:</strong>
								</td>
			
								<td>
									<strong> 
									<html:select property="idLigacaoAguaSituacao" 
										style="width: 230px;"
										multiple="true">
										
										<html:option
											value="">&nbsp;
										</html:option>
								
										<logic:present name="colecaoLigacaoAguaSituacao" scope="request">
											<html:options collection="colecaoLigacaoAguaSituacao"
												labelProperty="descricao" 
												property="id" />
										</logic:present>
									</html:select> 														
									</strong>
								</td>
							</tr>
							
							<tr>
								<td>
									<strong>Situação da Ligação de Esgoto:</strong>
								</td>
			
								<td>
									<strong> 
									<html:select property="idLigacaoEsgotoSituacao" 
										style="width: 230px;"
										multiple="true">
										
										<html:option
											value="">&nbsp;
										</html:option>
								
										<logic:present name="colecaoLigacaoEsgotoSituacao" scope="request">
											<html:options collection="colecaoLigacaoEsgotoSituacao"
												labelProperty="descricao" 
												property="id" />
										</logic:present>
									</html:select> 														
									</strong>
								</td>
							</tr>
							
						</table>
					</td>
				</tr>
				
				<tr>
             		<td height="10" colspan="2"> 
	             		<div align="right"> 
	                 		<hr>
	               		</div>
	               		<div align="right"> </div>
               		</td>
           		</tr>
				
				<tr>
					<td colspan="2">
						<strong>Dados do Débito:</strong>
						<table align="center">
						
							<tr>
				                <td>
				                	<strong>Per&iacute;odo de Referência do Débito:</strong>
				                </td>
			                
				                <td>
				                	<span class="style2">
				                	
				                	<strong> 
										
										<html:text property="periodoReferenciaDebitoInicial" 
											size="7" 
											maxlength="7" 
											onkeyup="mascaraAnoMes(this, event);replicaDataPeriodoReferenciaDebito();"/>
										a 
										<html:text property="periodoReferenciaDebitoFinal" 
											size="7" 
											maxlength="7" 
											onkeyup="mascaraAnoMes(this, event)"/>
										
										</strong>(mm/aaaa)<strong> 
				                  	</strong>
				                  	</span>
				              	</td>
			              	</tr>
			              	
			              	<tr>
				                <td>
				                	<strong>Per&iacute;odo de Vencimento do Débito:</strong>
				                </td>
			                
				                <td>
				                	<span class="style2">
				                	
				                	<strong> 
										
										<html:text property="periodoVencimentoDebitoInicial" 
											size="11" 
											maxlength="10" 
											onkeyup="mascaraData(this, event);replicaDataPeriodoVencimentoDebito();"/>
										
										<a href="javascript:abrirCalendarioReplicando('FiltrarImoveisComDebitosPrescritosActionForm', 'periodoVencimentoDebitoInicial','periodoVencimentoDebitoFinal');">
										<img border="0" 
											src="<bean:message key='caminho.imagens'/>calendario.gif" 
											width="16" 
											height="15" 
											border="0" alt="Exibir Calendário"/>
										</a>
										
										a 
										
										<html:text property="periodoVencimentoDebitoFinal" 
											size="11" 
											maxlength="10" 
											onkeyup="mascaraData(this, event)"/>
											
										<a href="javascript:abrirCalendario('FiltrarImoveisComDebitosPrescritosActionForm', 'periodoVencimentoDebitoFinal');">
										<img border="0" 
											src="<bean:message key='caminho.imagens'/>calendario.gif" 
											width="16" 
											height="15" 
											border="0" 
											alt="Exibir Calendário" />
										</a>
										
										</strong>(dd/mm/aaaa)<strong> 
				                  	</strong>
				                  	</span>
				              	</td>
			              	</tr>
			              	
						</table>
					</td>
				</tr>
				
				<tr>
             		<td height="10" colspan="2"> 
	             		<div align="right"> 
	                 		<hr>
	               		</div>
	               		<div align="right"> </div>
               		</td>
           		</tr>

				<tr>
					<td height="24" >
			          	
			          	<input type="button" 
			          		class="bottonRightCol" 
			          		value="Limpar" 
			          		onclick="javascript:limpar();"
			          		/>
			          		
					</td>
				
					<td align="right">
						<input type="button" 
								name="Button"
								class="bottonRightCol" 
								value="Cancelar" 
								onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
								style="width: 80px" />
						<gsan:controleAcessoBotao name="Button" 
							value="Filtrar"
							onclick="javascript:validarFormulario();"
							url="filtrarImoveisComDebitosPrescritosAction.do" />
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