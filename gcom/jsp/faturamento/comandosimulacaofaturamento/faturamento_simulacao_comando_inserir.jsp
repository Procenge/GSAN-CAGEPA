<%@page import="gcom.faturamento.faturamentosimulacaocomando.FaturamentoSimulacaoComando"%>
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

<html:javascript staticJavascript="false"  formName="InserirComandoSimulacaoFaturamentoActionForm"/>

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
		
		if(validateInserirComandoSimulacaoFaturamentoActionForm(form)
			&& validarCampos()){
			
			form.action = "/gsan/inserirComandoSimulacaoFaturamentoAction.do";
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
				}else{
					
					msg = verificarAtributosInicialFinal(form.codigoRotaInicial,form.codigoRotaFinal,"Rota Inicial", "Rota Final");
					if( msg != ""){
						alert(msg);
						return false;
					}else{
						
						msg = verificarAtributosInicialFinal(form.loteInicial,form.loteFinal,"Lote Inicial", "Lote Final");
						if( msg != ""){
							alert(msg);
							return false;
						}else{
							
							msg = "";
							if (form.indicadorCriterioTipoConsumoAgua[0].checked == false && form.indicadorCriterioTipoConsumoAgua[1].checked == false 
									&& form.indicadorCriterioTipoConsumoAgua[2].checked == false) {
								
								msg = "É necessário informar Tipo de Consumo da Ligação de Àgua";
							}
							
							if( msg != ""){
								alert(msg);
								return false;
							}else{
								
								msg = "";
								if (form.indicadorCriterioTipoConsumoEsgoto[0].checked == false && form.indicadorCriterioTipoConsumoEsgoto[1].checked == false 
										&& form.indicadorCriterioTipoConsumoEsgoto[2].checked == false) {
									
									msg = "É necessário informar Tipo de Consumo da Ligação de Esgoto";
								}
								
								if( msg != ""){
									alert(msg);
									return false;
								}
							}
						}
					}
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
		
		if(form.idFaturamentoGrupo.selectedIndex <= 0){
			
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
					
					limparOrigem(6);
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
	}
	
  	function limpar(){

  		var form = document.forms[0];
  		
  		form.idFaturamentoGrupo.value = '';
		form.idFaturamentoGrupo.selectedIndex = 0;
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
	   	form.loteInicial.value = "";
	   	form.loteFinal.value = "";
	   	form.codigoRotaInicial.value = "";
	   	form.descricaoRotaInicial.value="";
	   	form.codigoRotaFinal.value = "";
	   	form.descricaoRotaFinal.value="";
	   	form.indicadorCriterioTipoConsumoAgua[0].checked = false;
		form.indicadorCriterioTipoConsumoAgua[1].checked = false;
		form.indicadorCriterioTipoConsumoAgua[2].checked = false;
		form.indicadorCriterioTipoConsumoEsgoto[0].checked = false;
		form.indicadorCriterioTipoConsumoEsgoto[1].checked = false;
		form.indicadorCriterioTipoConsumoEsgoto[2].checked = false;
	   	form.idConsumoTarifa.value = '';
		form.idConsumoTarifa.selectedIndex = 0;
		form.tituloComando.value = "";

  		form.action='exibirInserirComandoSimulacaoFaturamentoAction.do?menu=sim';
	    form.submit();
  	}
  	
  	function replicarLocalidade(){
		var formulario = document.forms[0];
		limparOrigem(3);
		
		if (form.idLocalidadeFinal.value == '' || form.idLocalidadeFinal.value == formulario.idLocalidadeInicial.value){
			
			formulario.idLocalidadeFinal.value = formulario.idLocalidadeInicial.value;
			formulario.codigoSetorComercialInicial.focus;
		}
	}
	
	function replicarSetorComercial(){
		var formulario = document.forms[0];
		limparOrigem(6);
		limparOrigem(8);
		
		if (form.codigoSetorComercialFinal.value == '' || form.codigoSetorComercialFinal.value == form.codigoSetorComercialInicial.value){
			
			formulario.codigoSetorComercialFinal.value = formulario.codigoSetorComercialInicial.value;
		}
	} 
	
	function replicarQuadra(){
		
		var formulario = document.forms[0];
		limparOrigem(4);
		
		if (formulario.numeroQuadraFinal.value == '' 
				|| formulario.numeroQuadraFinal.value == formulario.numeroQuadraInicial.value){
			
			formulario.numeroQuadraFinal.value = formulario.numeroQuadraInicial.value;
		}
	}
	
	function replicarLote(){
		
		var formulario = document.forms[0];
		
		if (formulario.loteFinal.value == '' 
				|| formulario.loteFinal.value == formulario.loteInicial.value){
			
			formulario.loteFinal.value = formulario.loteInicial.value;
		}
	}
	
	function replicarRota(){
		
		var formulario = document.forms[0];
		limparOrigem(4);
		
		if (formulario.codigoRotaFinal.value == '' 
				|| formulario.codigoRotaFinal.value == formulario.codigoRotaInicial.value){
			
			formulario.codigoRotaFinal.value = formulario.codigoRotaInicial.value;
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
			   	form.loteInicial.value = "";
			   	form.loteFinal.value = "";
			   	form.codigoRotaInicial.value = "";
			   	form.descricaoRotaInicial.value="";
			   	form.codigoRotaFinal.value = "";
			   	form.descricaoRotaFinal.value="";
				break;
			    
			case 2: //De descrição setor inicial para baixo
	
			   	form.descricaoSetorComercialInicial.value = "";
			   	form.codigoSetorComercialFinal.value = "";
			  	form.descricaoSetorComercialFinal.value = "";
			   	form.numeroQuadraInicial.value = "";
			   	form.numeroQuadraFinal.value = "";
			   	form.loteInicial.value = "";
			   	form.loteFinal.value = "";
			   	form.numeroQuadraInicial.value = "";
			   	form.numeroQuadraFinal.value = "";
			   	form.loteInicial.value = "";
			   	form.loteFinal.value = "";
			   	form.codigoRotaInicial.value = "";
			   	form.descricaoRotaInicial.value="";
			   	form.codigoRotaFinal.value = "";
			   	form.descricaoRotaFinal.value="";
			   
			   break
			   
			case 3: //De código setor inicial para baixo
	
			   form.codigoSetorComercialInicial.value = "";
			   form.descricaoSetorComercialInicial.value = "";
			   form.codigoSetorComercialFinal.value = "";
			   form.descricaoSetorComercialFinal.value = "";
			   form.numeroQuadraInicial.value = "";
			   form.numeroQuadraFinal.value = "";
			   form.loteInicial.value = "";
			   form.loteFinal.value = "";
			   form.codigoRotaInicial.value = "";
			   form.descricaoRotaInicial.value="";
			   form.codigoRotaFinal.value = "";
			   form.descricaoRotaFinal.value="";
			   break;
			   
			case 4: //De lote inicial para baixo
			  
			   form.loteInicial.value = "";
			   form.loteFinal.value = "";
			   break;
			   
			case 5: //De quadra final para baixo
				
			   form.numeroQuadraFinal.value = "";
			   form.loteInicial.value = "";
			   form.loteFinal.value = "";
			   break;
			case 6: //De quadra inicial para baixo
				
			   form.numeroQuadraInicial.value = "";
			   form.numeroQuadraFinal.value = "";
			   form.loteInicial.value = "";
			   form.loteFinal.value = "";
			   break;
			case 7: //De codigo rota final para baixo
				
				form.codigoRotaFinal.value = "";
			  	form.descricaoRotaFinal.value="";
			   	form.loteInicial.value = "";
			   	form.loteFinal.value = "";
			  	break;
			case 8: //De codigo rota inicial para baixo
				
				
				form.codigoRotaInicial.value = "";
			   	form.descricaoRotaInicial.value="";
			   	form.codigoRotaFinal.value = "";
			   	form.descricaoRotaFinal.value="";
			   	form.loteInicial.value = "";
			   	form.loteFinal.value = "";
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
			   	form.loteInicial.value = "";
			   	form.loteFinal.value = "";
			   	form.codigoRotaInicial.value = "";
				form.descricaoRotaInicial.value="";
				form.codigoRotaFinal.value = "";
				form.descricaoRotaFinal.value="";
				break;
			case 2: //De código setor inicial para baixo
		     	
		     	form.codigoSetorComercialInicial.value = "";
		     	form.descricaoSetorComercialInicial.value = "";
		     	form.codigoSetorComercialFinal.value = "";
		     	form.descricaoSetorComercialFinal.value = "";
		     	form.numeroQuadraInicial.value = "";
			   	form.numeroQuadraFinal.value = "";
			   	form.loteInicial.value = "";
			   	form.loteFinal.value = "";
			   	form.codigoRotaInicial.value = "";
				form.descricaoRotaInicial.value="";
				form.codigoRotaFinal.value = "";
				form.descricaoRotaFinal.value="";
		     	break;
			case 3: //De quadra para baixo
		     	
				form.numeroQuadraInicial.value = "";
			   	form.numeroQuadraFinal.value = "";
			   	form.loteInicial.value = "";
			   	form.loteFinal.value = "";
		     	break;
			case 4: //De código rota inicial para baixo
		     	
				form.codigoRotaInicial.value = "";
				form.descricaoRotaInicial.value="";
				form.codigoRotaFinal.value = "";
				form.descricaoRotaFinal.value="";
			   	form.loteInicial.value = "";
			   	form.loteFinal.value = "";
		     	break;
		}
		
		form.action = 'exibirInserirComandoSimulacaoFaturamentoAction.do?menu=sim';
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
		   		form.loteFinal.value = "";
				break;
			case 2: // De codigo setor final pra baixo	   
		   		
				form.codigoSetorComercialFinal.value = ""; 
		   		form.descricaoSetorComercialFinal.value = "";
		   		form.numeroQuadraFinal.value = "";
		   		form.loteFinal.value = "";
		   		break;
			case 3: // De quadra final pra baixo    
				
				form.numeroQuadraFinal.value = "";
				form.loteFinal.value = "";
		   		break;
			case 4: // De codigo rota final pra baixo
		     
				form.codigoRotaFinal.value = "";
				form.descricaoRotaFinal.value="";
			   	form.loteFinal.value = "";
		     	break;
		}
	}
	
	
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

		var form = document.forms[0];

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
				
				form.action = 'exibirInserirComandoSimulacaoFaturamentoAction.do?objetoConsulta=1';
			  	form.submit();
			}
		}

		if (tipoConsulta == 'localidadeDestino') {
		
      		form.idLocalidadeFinal.value = codigoRegistro;
      		form.descricaoLocalidadeFinal.value = descricaoRegistro;
	  		form.descricaoLocalidadeFinal.style.color = "#000000";

			if (form.idLocalidadeFinal.value != form.idLocalidadeInicial.value){
				
				limparOrigem(3);
				
				form.action = 'exibirInserirComandoSimulacaoFaturamentoAction.do?objetoConsulta=3';
			  	form.submit();
			}
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
				
				limparOrigem(6);
				limparOrigem(8);
			}
		  	
		  	form.action = 'exibirInserirComandoSimulacaoFaturamentoAction.do?objetoConsulta=2';
		  	form.submit();
		}

		if (tipoConsulta == 'setorComercialDestino') {
		  	
			form.codigoSetorComercialFinal.value = codigoRegistro;
		  	form.descricaoSetorComercialFinal.value = descricaoRegistro;
		  	form.descricaoSetorComercialFinal.style.color = "#000000";
		  	
			if (form.codigoSetorComercialFinal.value != form.codigoSetorComercialInicial.value){
				
				limparOrigem(6);
				limparOrigem(8);
			}
			
		  	form.action = 'exibirInserirComandoSimulacaoFaturamentoAction.do?objetoConsulta=4';
		  	form.submit();
		}
		
		if (tipoConsulta == 'quadraOrigem') {
		    
		    form.numeroQuadraInicial.value = codigoRegistro;
			form.numeroQuadraFinal.value = codigoRegistro;
			
			if (form.codigoSetorComercialFinal.value != form.codigoSetorComercialInicial.value){
				
				limparOrigem(6);
				limparOrigem(8);
			}
			
			if (form.numeroQuadraFinal.value != form.numeroQuadraInicial.value){
				
				limparOrigem(4);
			}
			
			form.action = 'exibirInserirComandoSimulacaoFaturamentoAction.do?objetoConsulta=5';
		  	form.submit();
	  	}
		
		if (tipoConsulta == 'quadraDestino') {
		    
			form.numeroQuadraFinal.value = codigoRegistro;
			
			if (form.codigoSetorComercialFinal.value != form.codigoSetorComercialInicial.value){
				
				limparOrigem(6);
				limparOrigem(8);
			}
			
			if (form.numeroQuadraFinal.value != form.numeroQuadraInicial.value){
				
				limparOrigem(4);
			}
			
			form.action = 'exibirInserirComandoSimulacaoFaturamentoAction.do?objetoConsulta=6';
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
					
					limparOrigem(6);
					limparOrigem(8);
				}
			break;
			case 3: 
				
				if ((form.numeroQuadraFinal.value != form.numeroQuadraInicial.value) || (form.codigoRotaFinal.value != form.codigoRotaInicial.value)){
					
					limparOrigem(4);
				}
			break;
		}
	}
	
	function desabilitarDemaisCampos(){

  		var form = document.forms[0];
  		
  		if (form.idFaturamentoGrupo.selectedIndex != 0){
	  		
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
  		   	form.loteInicial.value = "";
  		   	form.loteFinal.value = "";
  		   	form.codigoRotaInicial.value = "";
  		   	form.descricaoRotaInicial.value="";
  		   	form.codigoRotaFinal.value = "";
  		   	form.descricaoRotaFinal.value="";

	  		form.idGerenciaRegional.disabled = true;
	  		form.idUnidadeNegocio.disabled = true;
			form.idLocalidadeInicial.disabled = true;
			form.idLocalidadeFinal.disabled = true;
			form.codigoSetorComercialInicial.disabled = true;
			form.codigoSetorComercialFinal.disabled = true;
			form.numeroQuadraInicial.disabled = true;
		   	form.numeroQuadraFinal.disabled = true;
		   	form.loteInicial.disabled = true;
		   	form.loteFinal.disabled = true;
		   	form.codigoRotaInicial.disabled = true;
		   	form.codigoRotaFinal.disabled = true;
		   	
		   	form.idGerenciaRegional.className = 'desabilitar';
		   	form.idUnidadeNegocio.className = 'desabilitar';
		   	form.idLocalidadeInicial.className = 'desabilitar';
		   	form.idLocalidadeFinal.className = 'desabilitar';
			form.codigoSetorComercialInicial.className = 'desabilitar';
			form.codigoSetorComercialFinal.className = 'desabilitar';
			form.numeroQuadraInicial.className = 'desabilitar';
		   	form.numeroQuadraFinal.className = 'desabilitar';
		   	form.loteInicial.className = 'desabilitar';
		   	form.loteFinal.className = 'desabilitar';
		   	form.codigoRotaInicial.className = 'desabilitar';
		   	form.codigoRotaFinal.className = 'desabilitar';
		   
  		}else{
  			
  			form.idGerenciaRegional.disabled = false;
	  		form.idUnidadeNegocio.disabled = false;
			form.idLocalidadeInicial.disabled = false;
			form.idLocalidadeFinal.disabled = false;
			form.codigoSetorComercialInicial.disabled = false;
			form.codigoSetorComercialFinal.disabled = false;
			form.numeroQuadraInicial.disabled = false;
		   	form.numeroQuadraFinal.disabled = false;
		   	form.loteInicial.disabled = false;
		   	form.loteFinal.disabled = false;
		   	form.codigoRotaInicial.disabled = false;
		   	form.codigoRotaFinal.disabled = false;
		   	
		   	form.idGerenciaRegional.className = '';
		   	form.idUnidadeNegocio.className = '';
		   	form.idLocalidadeInicial.className = '';
		   	form.idLocalidadeFinal.className = '';
			form.codigoSetorComercialInicial.className = '';
			form.codigoSetorComercialFinal.className = '';
			form.numeroQuadraInicial.className = '';
		   	form.numeroQuadraFinal.className = '';
		   	form.loteInicial.className = '';
		   	form.loteFinal.className = '';
		   	form.codigoRotaInicial.className = '';
		   	form.codigoRotaFinal.className = '';
  		}
  	}
	
	function atualizarTela(){

  		var form = document.forms[0];
  		
  		form.action = 'exibirInserirComandoSimulacaoFaturamentoAction.do?menu=sim';
	  	form.submit();
	}
</script>

</head>

<body leftmargin="5" topmargin="5" onload="javascript:desabilitarDemaisCampos();">

<html:form action="/exibirInserirComandoSimulacaoFaturamentoAction.do"
	name="InserirComandoSimulacaoFaturamentoActionForm"
	type="gcom.gui.faturamento.comandosimulacaofaturamento.InserirComandoSimulacaoFaturamentoActionForm"
	method="post">

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
					<td class="parabg">Inserir Comando de Simulação de Faturamento</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				
				<tr>
					<td colspan="2">Para inserir o comando de simulação de faturamento, informe os dados abaixo:</td>
				</tr>
				
				<tr>
					<td>
						<strong>Grupo de Faturamento:</strong>
					</td>

					<td>
						<strong> 
						<html:select property="idFaturamentoGrupo" 
							style="width: 230px;"
							onchange="javascript:atualizarTela();">
							
							<html:option
								value="">&nbsp;
							</html:option>
					
							<logic:present name="colecaoFaturamentoGrupo" scope="request">
								<html:options collection="colecaoFaturamentoGrupo"
									labelProperty="descricao" 
									property="id" />
							</logic:present>
						</html:select> 														
						</strong>
					</td>
				</tr>

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
             		<td height="10" colspan="2"> 
	             		<div align="right"> 
	                 		<hr>
	               		</div>
	               		<div align="right"> </div>
               		</td>
           		</tr>
           		
				<tr>
					<td><strong>Localidade Inicial:</strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							property="idLocalidadeInicial" 
							size="3"
							onblur="javascript:replicarLocalidade();" 
							onkeypress="javascript:limparOrigem(3);validaEnter(event, 'exibirInserirComandoSimulacaoFaturamentoAction.do?objetoConsulta=1', 'idLocalidadeInicial');"/>
							
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
							onkeypress="javascript:limparOrigem(2);validaEnterDependencia(event, 'exibirInserirComandoSimulacaoFaturamentoAction.do?objetoConsulta=2', document.forms[0].codigoSetorComercialInicial, document.forms[0].idLocalidadeInicial.value, 'Localidade Inicial');"/>
							
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
						
						<html:text maxlength="5" 
							property="numeroQuadraInicial" 
							size="4"
							onblur="javascript:replicarQuadra();verificarBloqueioCampos(2);"
							onkeypress="javascript:limparOrigem(5);validaEnterDependencia(event, 'exibirInserirComandoSimulacaoFaturamentoAction.do?objetoConsulta=5', document.forms[0].numeroQuadraInicial, document.forms[0].codigoSetorComercialInicial.value, 'Setor Inicial');"/>
							
						<a href="javascript:chamarPopup('exibirPesquisarQuadraAction.do', 'quadraOrigem', 'codigoSetorComercial', document.forms[0].codigoSetorComercialInicial.value , 275, 480, 'Informe Setor Comercial Inicial');
						         limparOrigem(6);">
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
					<td><strong>Rota Inicial:</strong></td>
					
					<td>
						
						<html:text maxlength="5" 
							property="codigoRotaInicial" 
							size="5"
							onblur="javascript:replicarRota();verificarBloqueioCampos(2);"
							onkeypress="javascript:limparOrigem(7);validaEnterDependencia(event, 'exibirInserirComandoSimulacaoFaturamentoAction.do?objetoConsulta=7', document.forms[0].codigoRotaInicial, document.forms[0].codigoSetorComercialInicial.value, 'Setor Comercial Inicial');"/>
								

						<logic:present name="rotaInicialEncontrada" scope="request">
							<html:text property="descricaoRotaInicial" 
								size="40"
								maxlength="40" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 

						<logic:notPresent name="rotaInicialEncontrada" scope="request">
							<html:text property="descricaoRotaInicial" 
								size="40"
								maxlength="40" 
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
					<td><strong>Lote Inicial:</strong></td>
					
					<td>
					
						<html:text maxlength="5" 
							property="loteInicial"
							onblur="javascript:replicarLote();verificarBloqueioCampos(3);" 
							size="3"/>
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
					<td><strong>Localidade Final:</strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							property="idLocalidadeFinal" 
							size="3"
							onblur="javascript:verificarBloqueioCampos(1);"
							onkeypress="validaEnter(event, 'exibirInserirComandoSimulacaoFaturamentoAction.do?objetoConsulta=3', 'idLocalidadeFinal');"/>
							
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
							onkeypress="validaEnterDependencia(event, 'exibirInserirComandoSimulacaoFaturamentoAction.do?objetoConsulta=4', document.forms[0].codigoSetorComercialFinal, document.forms[0].idLocalidadeFinal.value, 'Localidade Final');"
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
						
						<html:text maxlength="5" 
							property="numeroQuadraFinal" 
							size="4"
							onblur="javascript:verificarBloqueioCampos(2);"
							onkeypress="javascript:validaEnterDependencia(event, 'exibirInserirComandoSimulacaoFaturamentoAction.do?objetoConsulta=6', document.forms[0].numeroQuadraFinal, document.forms[0].codigoSetorComercialFinal.value, 'Setor Comercial Final');"/>
							
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
				
				<tr>
					<td><strong>Rota Final:</strong></td>
					
					<td>
						
						<html:text maxlength="5" 
							property="codigoRotaFinal" 
							size="5"
							onblur="javascript:verificarBloqueioCampos(2);"
							onkeypress="javascript:validaEnterDependencia(event, 'exibirInserirComandoSimulacaoFaturamentoAction.do?objetoConsulta=8', document.forms[0].codigoRotaFinal, document.forms[0].codigoSetorComercialFinal.value, 'Setor Comercial Final');"/>
						
						<logic:present name="rotaFinalEncontrada" scope="request">
							<html:text property="descricaoRotaFinal" 
								size="40"
								maxlength="40" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 

						<logic:notPresent name="rotaFinalEncontrada" scope="request">
							<html:text property="descricaoRotaFinal" 
								size="40"
								maxlength="40" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>
						
						<a href="javascript:limparBorrachaDestino(4);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>
				
				<tr>
					<td><strong>Lote Final:</strong></td>
					
					<td>
					
						<html:text maxlength="5" 
							property="loteFinal"
							onblur="javascript:verificarBloqueioCampos(3);" 
							size="3"/>
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
					<td><strong>Tipo de Consumo da Ligação de Àgua:</strong><font color="#FF0000">*</font></td>
					<td>
						<html:radio property="indicadorCriterioTipoConsumoAgua" value="<%=""+FaturamentoSimulacaoComando.CONSUMO_MINIMO%>" /> <strong>Mínimo</strong>
						<html:radio property="indicadorCriterioTipoConsumoAgua" value="<%=""+FaturamentoSimulacaoComando.CONSUMO_ANTERIOR%>" /> <strong>Anterior</strong>
						<html:radio property="indicadorCriterioTipoConsumoAgua" value="<%=""+FaturamentoSimulacaoComando.CONSUMO_MEDIO%>" /> <strong>Médio</strong>
					</td>
				</tr>
				
				<tr>
					<td><strong>Tipo de Consumo da Ligação de Esgoto:</strong><font color="#FF0000">*</font></td>
					<td>
						<html:radio property="indicadorCriterioTipoConsumoEsgoto" value="<%=""+FaturamentoSimulacaoComando.CONSUMO_MINIMO%>" /> <strong>Mínimo</strong>
						<html:radio property="indicadorCriterioTipoConsumoEsgoto" value="<%=""+FaturamentoSimulacaoComando.CONSUMO_ANTERIOR%>" /> <strong>Anterior</strong>
						<html:radio property="indicadorCriterioTipoConsumoEsgoto" value="<%=""+FaturamentoSimulacaoComando.CONSUMO_MEDIO%>" /> <strong>Médio</strong>
					</td>
				</tr>
				
				<tr>
					<td>
						<strong>Tarifa de Consumo:</strong>
					</td>

					<td>
						<strong> 
						<html:select property="idConsumoTarifa" 
							style="width: 230px;">
							
							<html:option
								value="">&nbsp;
							</html:option>
					
							<logic:present name="colecaoConsumoTarifa" scope="request">
								<html:options collection="colecaoConsumoTarifa"
									labelProperty="descricao" 
									property="id" />
							</logic:present>
						</html:select> 														
						</strong>
					</td>
				</tr>
				
				<tr>
					<td>
						<strong>Título do Comando:</strong>
					</td>

					<td>
						<html:text property="tituloComando" 
								size="70"
								maxlength="70"/>
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
							value="Inserir"
							onclick="javascript:validarFormulario();"
							url="inserirComandoSimulacaoFaturamentoAction.do" />
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