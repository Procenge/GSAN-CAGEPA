<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

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


<SCRIPT LANGUAGE="JavaScript">
<!--

  function habilitaSQlS(){
	var form = document.SituacaoEspecialFaturamentoInformarActionForm;
	if (form.localidadeOrigemID.value.length < 1){
	 	form.setorComercialOrigemCD.disabled = false;
	 	form.quadraOrigemID.disabled = false;
     	form.loteOrigem.disabled = false;
       	form.subloteOrigem.disabled = false;
	 	form.setorComercialDestinoCD.disabled = false;
	 	form.quadraDestinoID.disabled = false;
     	form.loteDestino.disabled = false;
       	form.subloteDestino.disabled = false;
    }
  }

	//Vivianne
	function desfazer(){
		var form = document.SituacaoEspecialFaturamentoInformarActionForm;
		limparBorrachaOrigem(1);
		limpar(3);
		limpar(1);
		limpar(13)
		bloquearM();
		form.idImovel.focus;
	}

	//Vivianne
	function limparMatricula(){
		var form = document.SituacaoEspecialFaturamentoInformarActionForm;
		form.inscricaoImovel.value = '';
		limparEndereco();
	}
	
	//Vivianne
	function limparEndereco(){
	var form = document.SituacaoEspecialFaturamentoInformarActionForm;
		if ((form.idImovel.value == '') && (!form.idImovel.disabled)){
			form.action = 'exibirSituacaoEspecialFaturamentoInformarAction.do';
			form.submit();
		}
	}
	
	//Vivianne
	function replicarLocalidade(){
		var formulario = document.forms[0]; 
		formulario.localidadeDestinoID.value = formulario.localidadeOrigemID.value;
		formulario.setorComercialOrigemCD.focus;
	}  
	
	//Vivianne
	function replicarSetorComercial(){
		var formulario = document.forms[0]; 
		formulario.setorComercialDestinoCD.value = formulario.setorComercialOrigemCD.value;
		formulario.quadraOrigemID.focus;
	} 
	
	//Vivianne
	function replicarQuadra(quadraOrigem){
		var formulario = document.forms[0];
		formulario.quadraDestinoID.value = quadraOrigem;
		formulario.loteOrigem.focus;
	} 
	
	//savio
	function quantidadeImovel(){
	var form = document.SituacaoEspecialFaturamentoInformarActionForm;
	return (form.quantidadeImoveisCOMSituacaoEspecialFaturamento.value * 1) + (form.quantidadeImoveisSEMSituacaoEspecialFaturamento.value * 1); 
	
	}
	
	//savio
	 function replicarLote(){
	    var form = document.SituacaoEspecialFaturamentoInformarActionForm;
		form.loteDestino.value = form.loteOrigem.value;
	  }
	
	//savio
	  function replicarSubLote(){
	    var form = document.SituacaoEspecialFaturamentoInformarActionForm;
		form.subloteDestino.value = form.subloteOrigem.value;
	  }
	  
	//Rossiter
	 function replicarRota(){
	    var form = document.SituacaoEspecialFaturamentoInformarActionForm;
		form.cdRotaFinal.value = form.cdRotaInicial.value;
	  }
	  
	 //Rossiter
	 function replicarSequencialRota(){
	    var form = document.SituacaoEspecialFaturamentoInformarActionForm;
		form.sequencialRotaFinal.value = form.sequencialRotaInicial.value;
	  }
	
	  //savio
	  function verificarBotoes(){
	  var form = document.SituacaoEspecialFaturamentoInformarActionForm;
	  	if(form.idImovel.value == "" && form.localidadeOrigemID.value == ""
	  		&& form.cdRotaInicial.value == "" && form.nomeBairro.value == ""){
	  		
	  		//form.selecionar.disabled = true;
	  		form.inserirManter.disabled = true;
	  		//form.retirar.disabled = true;
	  		if(form.quantidadeImoveisCOMSituacaoEspecialFaturamento != null && 
	  				form.quantidadeImoveisCOMSituacaoEspecialFaturamento.disabled != true){
	  			form.quantidadeImoveisCOMSituacaoEspecialFaturamento.value = '';
	  		}
	  		if(form.quantidadeImoveisSEMSituacaoEspecialFaturamento != null &&
	  				form.quantidadeImoveisSEMSituacaoEspecialFaturamento.disabled != true){
	  			form.quantidadeImoveisSEMSituacaoEspecialFaturamento.value = '';
	  		}
	  	}
	  }
	
	function limparMsgQuadraInexistente() {
		var msgQuadraOrigem = document.getElementById("msgQuadraOrigem");
		var msgQuadraDestino = document.getElementById("msgQuadraDestino");
		
		if (msgQuadraOrigem != null){
			msgQuadraOrigem.innerHTML = "";
		}
		if (msgQuadraDestino != null){
			msgQuadraDestino.innerHTML = "";
		}
	}
	
	function limpar(tipo){
		var form = document.SituacaoEspecialFaturamentoInformarActionForm;
		switch (tipo){
		//savio
	     case 1:
			   form.idImovel.value = "";
			   form.inscricaoImovel.value = "";
			   //document.getElementById("endereco").innerHTML = "&nbsp;";
			   limparEndereco();
			   bloquearLSQLS();
		   
			   break;   
		   case 2:
			   form.setorComercialOrigemCD.value = "";
			   form.setorComercialOrigemID.value = "";
			   form.nomeSetorComercialOrigem.value = "";
			   //form.quadraOrigemNM.value = "";
			   form.nomeQuadraOrigem.value = "";
			   form.quadraOrigemID.value = "";
			  
			   //Coloca o foco no objeto selecionado
			   form.setorComercialOrigemCD.focus();
			   break;
			   //savio
		   case 3:
		       form.nomeBairro.value = "";
			   form.localidadeDestinoID.value = "";
			   form.nomeLocalidadeDestino.value = "";
			   form.setorComercialDestinoCD.value = "";
			   form.setorComercialDestinoID.value = "";
			   form.nomeSetorComercialDestino.value = "";
			   form.quadraDestinoNM.value = "";
			   form.quadraDestinoID.value = "";
			   form.localidadeOrigemID.value = "";
			   form.nomeLocalidadeOrigem.value = "";
			   form.setorComercialOrigemCD.value = "";
			   form.setorComercialOrigemID.value = "";
			   form.nomeSetorComercialOrigem.value = "";
			   form.nomeQuadraOrigem.value = "";
			   form.quadraOrigemID.value = "";
			   
			   form.cdRotaInicial.value = "";
			   form.sequencialRotaInicial.value = "";
			   form.cdRotaFinal.value = "";
			   form.sequencialRotaFinal.value = "";
		
			   break;  
		   case 4:
			   form.setorComercialDestinoCD.value = "";
			   form.setorComercialDestinoID.value = "";
			   form.nomeSetorComercialDestino.value = "";
			   form.quadraDestinoNM.value = "";
			   form.quadraDestinoID.value = "";
	
			   //Coloca o foco no objeto selecionado
			   form.setorComercialDestinoCD.focus();
			   break;
			case 5:
			   form.nomeQuadraOrigem.value = "";
			   form.quadraOrigemID.value = "";
		
			   //Coloca o foco no objeto selecionado
			   form.quadraOrigemID.focus();
			   break;
			case 6:
			   form.quadraDestinoNM.value = "";
			   form.quadraDestinoID.value = "";
	
			   //Coloca o foco no objeto selecionado
			   form.quadraDestinoID.focus();
			   break;
			case 7:
			   form.setorComercialOrigemCD.value = "";
			   form.setorComercialOrigemID.value = "";
			   form.nomeSetorComercialOrigem.value = "";
			   form.nomeQuadraOrigem.value = "";
			   form.quadraOrigemID.value = "";
			   
			   form.loteOrigem.value = "";
			   form.subloteOrigem.value = "";
			   //Coloca o foco no objeto selecionado
			   form.localidadeOrigemID.focus();
			   break;
			case 8:
			   form.nomeQuadraOrigem.value = "";
			   form.quadraOrigemID.value = "";
	
			   form.loteOrigem.value = "";
			   form.subloteOrigem.value = "";
	
			   //Coloca o foco no objeto selecionado
			   form.setorComercialOrigemCD.focus();
			   break;
			case 9:
			   form.setorComercialDestinoCD.value = "";
			   form.setorComercialDestinoID.value = "";
			   form.nomeSetorComercialDestino.value = "";
			   form.quadraDestinoNM.value = "";
			   form.quadraDestinoID.value = "";
	   
			   form.loteDestino.value = "";
			   form.subloteDestino.value = "";
			
			   //Coloca o foco no objeto selecionado
			   form.localidadeDestinoID.focus();
			   break;
			 case 10:
			   form.quadraDestinoNM.value = "";
			   form.quadraDestinoID.value = "";

			   form.loteDestino.value = "";
			   form.subloteDestino.value = "";
	
			   //Coloca o foco no objeto selecionado
			   form.setorComercialDestinoCD.focus();
			   break;
			 case 11:
			   form.sequencialRotaInicial.value = "";
			   form.sequencialRotaFinal.value = "";
			   break;
			 case 12:
			   
			   if (form.cdRotaInicial.value != form.cdRotaFinal.value){
					form.sequencialRotaFinal.value = "";		   
			   }
			   break;
			 case 13:

				form.arquivoDownload.value = null;
				
		   default:
	          break;
		}
	}
	
	function bloquearLSQLS(){
		var form = document.SituacaoEspecialFaturamentoInformarActionForm;
		
		if(form.idImovel.value != "" ){//se matricula imovel nao for vazio entao deshabilita todos os texts
			form.localidadeOrigemID.disabled = true;
			form.setorComercialOrigemCD.disabled = true;
			form.quadraOrigemID.disabled = true;
			form.loteOrigem.disabled = true;
			form.subloteOrigem.disabled = true;
			form.nomeBairro.disabled = true;
			
			form.localidadeDestinoID.disabled = true;
			form.setorComercialDestinoCD.disabled = true;
			form.quadraDestinoID.disabled = true;
			form.loteDestino.disabled = true;
			form.subloteDestino.disabled = true;
			
			form.cdRotaInicial.disabled = true;	
			form.sequencialRotaInicial.disabled = true;
			form.cdRotaFinal.disabled = true;	
			form.sequencialRotaFinal.disabled = true;
			
			limpar(3);
			
			return false;
		}
	
		if(form.idImovel.value == ""){//se matricula imovel for vazio entao habilita todos os texts
	
			//document.getElementById("endereco").innerHTML = '&nbsp;';
			form.inscricaoImovel.value = "";
			form.localidadeOrigemID.disabled = false;
			form.setorComercialOrigemCD.disabled = false;
			form.quadraOrigemID.disabled = false;
			form.loteOrigem.disabled = false;
			form.subloteOrigem.disabled = false;
			
			form.localidadeDestinoID.disabled = false;
			form.setorComercialDestinoCD.disabled = false;
			form.quadraDestinoID.disabled = false;
			form.loteDestino.disabled = false;
			form.subloteDestino.disabled = false;
			
			form.cdRotaInicial.disabled = false;	
			form.sequencialRotaInicial.disabled = false;
			form.cdRotaFinal.disabled = false;	
			form.sequencialRotaFinal.disabled = false;
			form.nomeBairro.disabled = false;
			
			verificarBotoes();		
		}
	}
	
	
	function bloquearM(){
		var form = document.SituacaoEspecialFaturamentoInformarActionForm;
		
		if (form.nomeBairro.value != ""){
		 form.selecionar.disabled = false;
		}
		if(form.localidadeOrigemID.value != "" || form.cdRotaInicial.value != ""){
		  form.idImovel.disabled = true;
		  form.selecionar.disabled = false;
		  //limpar(1);
		}
		if(form.localidadeOrigemID.value == "" && form.cdRotaInicial.value == ""){
		  form.idImovel.disabled = false;
		  verificarBotoes();	
		}	
	}
	
	function chamarValidarRetirarSituacaoEspecialFaturamento(form){
		form.action = 'exibirSituacaoEspecialFaturamentoRetirarAction.do';
		form.submit();
	}
	
	function chamarExibirSituacaoEspecialFaturamentoInserirAtualizar(form){
		form.action = 'exibirSituacaoEspecialFaturamentoInserirAtualizarAction.do';
		form.submit();
	}
	
	function selecionarQuantidadeDeImoveis(form){
		if(form.idImovel.value == '' && validarForm(form)){
		   	form.action = 'exibirSituacaoEspecialFaturamentoInformarAction.do?consultaQuantidadeImoveis=1&bloquear=todos';
		    submeterFormPadrao(form);
		}else{
			 if(form.idImovel.value != '' && testarCampoValorZero(document.SituacaoEspecialFaturamentoInformarActionForm.idImovel, 'Matrícula Imóvel')){
		    	form.action = 'exibirSituacaoEspecialFaturamentoInformarAction.do?consultaQuantidadeImoveis=1&bloquear=matricula';
				submeterFormPadrao(form);
			 }
		}
	}
	
	function recuperarDadosQuatroParametros(idRegistro, descricaoRegistro, codigoRegistro, tipoConsulta) {
		var form = document.SituacaoEspecialFaturamentoInformarActionForm;
		if (tipoConsulta == 'setorComercialOrigem') {
	      form.setorComercialOrigemCD.value = codigoRegistro;
	      form.setorComercialOrigemID.value = idRegistro;
		  form.nomeSetorComercialOrigem.value = descricaoRegistro;
		  form.nomeSetorComercialOrigem.style.color = "#000000";
		  form.setorComercialDestinoCD.value = codigoRegistro;
	      form.setorComercialDestinoID.value = idRegistro;
		  form.nomeSetorComercialDestino.value = descricaoRegistro;
		  form.nomeSetorComercialDestino.style.color = "#000000"; 
		  desabilitaIntervaloDiferente(2);
		  form.quadraOrigemID.focus();
		}
	
		if (tipoConsulta == 'setorComercialDestino') {
	      form.setorComercialDestinoCD.value = codigoRegistro;
	      form.setorComercialDestinoID.value = idRegistro;
		  form.nomeSetorComercialDestino.value = descricaoRegistro;
		  form.nomeSetorComercialDestino.style.color = "#000000"; 
		  desabilitaIntervaloDiferente(2);
	  	  form.quadraDestinoID.focus();
		}
	}
	
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
		var form = document.SituacaoEspecialFaturamentoInformarActionForm;
		if (tipoConsulta == 'localidadeOrigem') {
	      form.localidadeOrigemID.value = codigoRegistro;
		  form.nomeLocalidadeOrigem.value = descricaoRegistro;
		  form.localidadeDestinoID.value = codigoRegistro;
	      form.nomeLocalidadeDestino.value = descricaoRegistro;
		  form.nomeLocalidadeOrigem.style.color = "#000000";
		  form.nomeLocalidadeDestino.style.color = "#000000";
		  desabilitaIntervaloDiferente(1);  
		  form.setorComercialOrigemCD.focus();
		  bloquearM()
		}
	
		if (tipoConsulta == 'localidade') {
	      form.localidadeDestinoID.value = codigoRegistro;
	      form.nomeLocalidadeDestino.value = descricaoRegistro;
		  form.nomeLocalidadeDestino.style.color = "#000000";
		  desabilitaIntervaloDiferente(1);
		  form.setorComercialDestinoCD.focus();
		}
	
		if(tipoConsulta == 'imovel'){
	         form.idImovel.value = codigoRegistro;
	         document.SituacaoEspecialFaturamentoInformarActionForm.action = 'exibirSituacaoEspecialFaturamentoInformarAction.do?bloquear=todos';
	         //document.SituacaoEspecialFaturamentoInformarActionForm.submit();
	    }
	}
	
	function validarRota(form){
	
		var retorno = false;
		
		var sequencialRotaInicial = form.sequencialRotaInicial;
		var rotaFinal = form.cdRotaFinal;
		var sequencialRotaFinal = form.sequencialRotaFinal;
		
		if (rotaFinal.value.length < 1){
			alert("Informe Rota Final");
			rotaFinal.focus();
			retorno = true;
		}
		else if (sequencialRotaInicial.value.length > 0 && sequencialRotaFinal.value.length < 1){
			alert("Informe Seq. da Rota Final");
			sequencialRotaFinal.focus();
			retorno = true;
		}
		return retorno;
	}
	
	function validarForm(form){
	   	// Campos relacionados a inscrição de origem
	   	var bairro = form.nomeBairro;
		var localidadeOrigem = form.localidadeOrigemID;
		var setorComercialOrigem = form.setorComercialOrigemCD;
		var quadraOrigem = form.quadraOrigemID;
		var loteOrigem = form.loteOrigem;
		var subloteOrigem = form.subloteOrigem;
	
		// Campos relacionados a inscrição de destino
		var localidadeDestino = form.localidadeDestinoID;
		var setorComercialDestino = form.setorComercialDestinoCD;
		var quadraDestino = form.quadraDestinoID;
		var loteDestino = form.loteDestino;
		var subloteDestino = form.subloteDestino;
		var arquivo = form.arquivoMatricula;
	
		var obrigatorio = false;
	
		//Origem
		if (localidadeOrigem.value.length < 1 && bairro.value.length < 1 && arquivo.value.lenght < 1){
			alert("Informe Localidade Inicial ou Bairro.");
			nomeBairro.focus();
			obrigatorio = true;
		}else if (!testarCampoValorZero(localidadeOrigem, "Localidade Inicial")){
			localidadeOrigem.focus();
			obrigatorio = true;
		}else if (setorComercialOrigem.value.length > 0 && 
				  !testarCampoValorZero(setorComercialOrigem, "Setor Comercial Inicial")){
	
			setorComercialOrigem.focus();
			obrigatorio = true;
		}else if (quadraOrigem != null && quadraOrigem.value != null && quadraOrigem.value.length > 0 && !testarCampoValorZero(quadraOrigem, "Quadra Inicial")){
			quadraOrigem.focus();
			obrigatorio = true;
		}else{
			obrigatorio = campoObrigatorio(setorComercialOrigem, quadraOrigem, "Informe Setor Comercial Inicial.");
			if (!obrigatorio){
				obrigatorio = campoObrigatorio(quadraOrigem, loteOrigem, "Informe Quadra Inicial.");
			}
			if (!obrigatorio){
				obrigatorio = campoObrigatorio(loteOrigem, subloteOrigem, "Informe Lote Inicial.");
			}
		}
	
		//Destino
		if (!obrigatorio){
			if (localidadeDestino.value.length < 1 && bairro.value.length < 1 && arquivo.value.lenght < 1){
				alert("Informe Localidade Final ou Bairro.");
				localidadeDestino.focus();
				obrigatorio = true;
			}else if (!testarCampoValorZero(localidadeDestino, "Localidade Final")){
				localidadeDestino.focus();
				obrigatorio = true;
			}else if (setorComercialDestino != null && setorComercialDestino.value && setorComercialDestino.value.length > 0 && 
				  !testarCampoValorZero(setorComercialDestino, "Setor Comercial Final")){
	
				setorComercialDestino.focus();
				obrigatorio = true;
			}else if (quadraDestino != null && quadraDestino.value && quadraDestino.value.length > 0 && !testarCampoValorZero(quadraDestino, "Quadra Final")){
				quadraDestino.focus();
				obrigatorio = true;
			}else{
				obrigatorio = campoObrigatorio(setorComercialDestino, quadraDestino, "Informe Setor Comercial Final.");
				if (!obrigatorio){
					obrigatorio = campoObrigatorio(quadraDestino, loteDestino, "Informe Quadra Final.");
				}
				if (!obrigatorio){
				obrigatorio = campoObrigatorio(loteDestino, subloteDestino, "Informe Lote Final.");
				}
			}
		}
	
		//Origem - Destino
		if (!obrigatorio){
			obrigatorio = campoObrigatorio(setorComercialOrigem,setorComercialDestino,"Informe Setor Comercial Inicial.");
			if (!obrigatorio){
				obrigatorio = campoObrigatorio(quadraOrigem,quadraDestino, "Informe Quadra Inicial.");
				if (!obrigatorio){
					obrigatorio = campoObrigatorio(loteOrigem,loteDestino, "Informe Lote Inicial.");
					if (!obrigatorio){
					obrigatorio = campoObrigatorio(subloteOrigem, subloteDestino, "Informe Sublote Inicial.");
					}
				}
			}
		}
		
		//Destino - Origem
		if (!obrigatorio){
			obrigatorio = campoObrigatorio(setorComercialDestino, setorComercialOrigem, "Informe Setor Comercial Final.");
			if (!obrigatorio){
				obrigatorio = campoObrigatorio(quadraDestino, quadraOrigem, "Informe Quadra Final.");
				if (!obrigatorio){
					obrigatorio = campoObrigatorio(loteDestino, loteOrigem, "Informe Lote Final.");
					if (!obrigatorio){
					obrigatorio = campoObrigatorio(subloteDestino, subloteOrigem, "Informe Sublote Final.");
					}
				}
			}
		}
		
		//Valida campos da inscrição inicial > os campos da inscrição final
		if (localidadeOrigem.value.length > 0 && localidadeDestino.value.length > 0 && parseInt(localidadeOrigem.value) > parseInt(localidadeDestino.value)){
			alert('Localidade Final deve ser maior ou igual a Localidade Inicial.');
			obrigatorio = true;
			localidadeDestino.focus();
		}
		if (setorComercialOrigem.value > setorComercialDestino.value){
			alert('Setor Comercial Final deve ser maior ou igual a Setor Comercial Inicial.');
			obrigatorio = true;
			setorComercialDestino.focus();
		}
		if (quadraOrigem.value > quadraDestino.value){
			alert('Quadra Final deve ser maior ou igual a Quadra Inicial.');
			obrigatorio = true;
			quadraDestino.focus();
		}
		if (loteOrigem.value > loteDestino.value){
			alert('Lote Final deve ser maior ou igual a Lote Inicial.');
			obrigatorio = true;
			loteDestino.focus();
		}
		if (subloteOrigem.value > subloteDestino.value){
			alert('Sublote Final deve ser maior ou igual a Sublote Inicial.');
			obrigatorio = true;
			subloteDestino.focus();
		}
		
		var rotaInicial = form.cdRotaInicial;
		
		if (!obrigatorio && rotaInicial.value.length > 0){
			obrigatorio = validarRota(form);
		}
		
		// Confirma a validação do formulário
		if (!obrigatorio && validateSituacaoEspecialFaturamentoInformarActionForm(form)){
			return true;	
		}else{
			return false;
		}
	}
	
	function campoObrigatorio(campoDependencia, dependente, msg){
		if (dependente==null || dependente.value==null || dependente.value.length < 1){
			return false
		} else if (campoDependencia!=null && campoDependencia.value.length < 1){
			alert(msg);
			campoDependencia.focus();
			return true
		}
	}
	
	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, telaRetorno, campo){
		if(!campo.disabled){
		  if (objeto == null || codigoObjeto == null){
		     if (tipo == "" ){
		         abrirPopup(url,altura, largura);
		     }else{
			     abrirPopup(url + "?" + "tipo=" + tipo + "&caminhoRetornoTelaPesquisa=" + telaRetorno, altura, largura);
			 }
		  } else {
			 if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
				alert(msg);
		 	 }else{
				abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + telaRetorno, altura, largura);
			 }
		  }
	   } 
	}
	
	function validarLocalidade(){
		var form = document.SituacaoEspecialFaturamentoInformarActionForm;
		if( form.localidadeOrigemID.value == form.localidadeDestinoID.value ){
			form.setorComercialOrigemID.disabled = false;
			form.setorComercialDestinoID.disabled = false;
		}else if( form.localidadeOrigemID.value != form.localidadeDestinoID.value ){
			form.setorComercialOrigemID.disabled = true;
			form.setorComercialDestinoID.disabled = true;
			form.setorComercialOrigemID.value = '';
			form.setorComercialDestinoID.value = '';
			form.quadraOrigemID.value = '';
			form.quadraDestinoID.value = '';
		}else if( form.setorComercialOrigemID.value != form.setorComercialDestinoID.value ){
				form.quadraOrigemID.disabled = false;
				form.quadraDestinoID.disabled = false;
		}
	}
	
	function limparBorrachaOrigem(tipo){
		var form = document.SituacaoEspecialFaturamentoInformarActionForm;
	
		switch(tipo){
			case 1: //De localidara pra baixo
			    if(!form.localidadeOrigemID.disabled){
				form.localidadeOrigemID.value = "";
				form.nomeLocalidadeOrigem.value = "";
				form.idImovel.disabled = false;
				form.localidadeDestinoID.value = "";
				form.nomeLocalidadeDestino.value = "";
				verificarBotoes();
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
			   if(!form.setorComercialOrigemCD.disabled){
			   form.nomeQuadraOrigem.value = "";
			   form.quadraOrigemID.value = "";
	
			   form.loteOrigem.value = "";
			   form.subloteOrigem.value = "";
	
			   form.quadraDestinoNM.value = "";
			   form.quadraDestinoID.value = "";
	        
			   form.loteDestino.value = "";
			   form.subloteDestino.value = "";
			   desabilitaIntervaloDiferente(3);
	 		   limparMsgQuadraInexistente();
			   }
		}
	}
	
	function limparBorrachaDestino(tipo){
		var form = document.SituacaoEspecialFaturamentoInformarActionForm;
	
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
	           form.subloteDestino.value = "";      
		}
	}
	
	function limparOrigem(tipo){
		var form = document.SituacaoEspecialFaturamentoInformarActionForm;
	
		switch(tipo){
			case 1: //De localidade pra baixo
	
				form.nomeLocalidadeOrigem.value = "";
				form.idImovel.disabled = false;
				form.localidadeDestinoID.value = "";
				form.nomeLocalidadeDestino.value = "";
				form.setorComercialOrigemCD.value = "";
			    form.setorComercialOrigemID.value = "";
				verificarBotoes();
				habilitaSQlS();
				
			case 2: //De setor para baixo
	
			   form.nomeSetorComercialOrigem.value = "";
			   form.setorComercialDestinoCD.value = "";
			   form.setorComercialDestinoID.value = "";		   
			   form.nomeSetorComercialDestino.value = "";
			  // form.quadraOrigemNM.value = "";
			   form.nomeQuadraOrigem.value = "";
			   form.quadraOrigemID.value = "";
	///		   alert("limpar origem 2");
			case 3://De quadra pra baixo
	
			   form.loteOrigem.value = "";
			   form.subloteOrigem.value = "";
	
			   form.quadraDestinoNM.value = "";
			   form.quadraDestinoID.value = "";
	        
			   form.loteDestino.value = "";
			   form.subloteDestino.value = "";
			   limparMsgQuadraInexistente();
		}
	}
	
	function limparDestino(tipo){
		var form = document.SituacaoEspecialFaturamentoInformarActionForm;
	
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
			   if(form.quadraOrigemID.value != form.quadraDestinoID.value){
		           form.loteDestino.value = "";
		           form.subloteDestino.value = "";
		       }    
		}
	}
	
	//savio
	function desabilitaIntervaloDiferente(tipo){
		var form = document.SituacaoEspecialFaturamentoInformarActionForm;
		
		switch(tipo){
			case 1: //De localidade 
			    if(form.localidadeOrigemID.value != form.localidadeDestinoID.value){
			        limparBorrachaOrigem(2);
			        form.setorComercialOrigemCD.disabled = true;
				 	form.quadraOrigemID.disabled = true;
			     	form.loteOrigem.disabled = true;
	             	form.subloteOrigem.disabled = true;
				 	form.setorComercialDestinoCD.disabled = true;
				 	form.quadraDestinoID.disabled = true;
			     	form.loteDestino.disabled = true;
	             	form.subloteDestino.disabled = true;
	             	
	             	form.loteOrigem.value = "";
	             	form.subloteOrigem.value = "";	
				 
				  }else{
				 	form.setorComercialOrigemCD.disabled = false;
				 	form.quadraOrigemID.disabled = false;
			     	form.loteOrigem.disabled = false;
	             	form.subloteOrigem.disabled = false;
				 	form.setorComercialDestinoCD.disabled = false;
				 	form.quadraDestinoID.disabled = false;
			     	form.loteDestino.disabled = false;
	             	form.subloteDestino.disabled = false;
				  }
				break;					
				   		   
			case 2: //De setor Comercial		   
			
			    if(form.setorComercialOrigemCD.value != form.setorComercialDestinoCD.value){
				  	form.quadraOrigemID.disabled = true;
			     	form.loteOrigem.disabled = true;
	             	form.subloteOrigem.disabled = true;
				 	form.quadraDestinoID.disabled = true;
			     	form.loteDestino.disabled = true;
	             	form.subloteDestino.disabled = true;
	             
	             	form.loteOrigem.value = "";
	             	form.subloteOrigem.value = "";
	             	form.nomeQuadraOrigem.value = "";	
	             	form.quadraOrigemID.value = "";
	             	form.loteDestino.value = "";
	             	form.subloteDestino.value = "";
	             	form.quadraDestinoNM.value = "";	
	             	form.quadraDestinoID.value = "";
	             	form.quadraMensagemOrigem.value = "";
	
	  			  }else{
				 	form.quadraOrigemID.disabled = false;
			     	form.loteOrigem.disabled = false;
	             	form.subloteOrigem.disabled = false;
				 	form.quadraDestinoID.disabled = false;
			     	form.loteDestino.disabled = false;
	             	form.subloteDestino.disabled = false;			
				  }
				
				break;
	           
			case 3://De quadra 
			     if(form.quadraOrigemID.value != form.quadraDestinoID.value){
				  	form.loteOrigem.disabled = true;
	             	form.subloteOrigem.disabled = true;
				 	form.loteDestino.disabled = true;
	             	form.subloteDestino.disabled = true;

	             	form.loteOrigem.value = "";
	             	form.subloteOrigem.value = "";
	             	form.loteDestino.value = "";
	             	form.subloteDestino.value = "";	
				 
				  }else{
				 	form.loteOrigem.disabled = false;
	             	form.subloteOrigem.disabled = false;
				 	form.loteDestino.disabled = false;
	             	form.subloteDestino.disabled = false;
				  }
				
				break;
			case 4://De Lote
			     if(form.loteOrigem.value != form.loteDestino.value){
				  	form.subloteOrigem.disabled = true;
				 	form.subloteDestino.disabled = true;
	             
	             	form.subloteOrigem.value = "";
	             	form.subloteDestino.value = "";	
				 
				  }else{
				 	form.subloteOrigem.disabled = false;
				 	form.subloteDestino.disabled = false;
				  }
				break;
			}  
	}
	
	//savio
	function limparSubloteOrigem(){
	var form = document.SituacaoEspecialFaturamentoInformarActionForm;
	 if(form.loteOrigem.value == ""){
	  form.loteDestino.value = "";
	  form.subloteDestino.value = "";
	  form.subloteOrigem.value = "";
	 }
	}

    var bCancel = false; 

    function validateSituacaoEspecialFaturamentoInformarActionForm(form) {                                                                   
        if (bCancel) 
      return true; 
        else 
       return validateCaracterEspecial(form) && validateLong(form); 
   } 

    function caracteresespeciais () { 
     this.aa = new Array("localidadeOrigemID", "Localidade Inicial possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ab = new Array("setorComercialOrigemCD", "Setor Comercial Inicial possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ac = new Array("quadraOrigemID", "Quadra Inicial possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ad = new Array("loteOrigem", "Lote Inicial possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ai = new Array("subloteOrigem", "Sublote Inicial possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ae = new Array("localidadeDestinoID", "Localidade Final possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.af = new Array("setorComercialDestinoCD", "Setor Comercial Final possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ag = new Array("quadraDestinoID", "Quadra Final possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ah = new Array("loteDestino", "Lote Final possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.aj = new Array("subloteDestino", "Sublote Final possui caracteres especiais.", new Function ("varName", " return this[varName];"));
    } 

    function IntegerValidations () { 
     this.aa = new Array("localidadeOrigemID", "Localidade Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.ab = new Array("setorComercialOrigemCD", "Setor Comercial Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.ac = new Array("quadraOrigemID", "Quadra Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.ad = new Array("loteOrigem", "Lote Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.ai = new Array("subloteOrigem", "Sublote Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.ae = new Array("localidadeDestinoID", "Localidade Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.af = new Array("setorComercialDestinoCD", "Setor Comercial Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.ag = new Array("quadraDestinoID", "Quadra Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.ah = new Array("loteDestino", "Lote Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.aj = new Array("subloteDestino", "Sublote Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    } 
    
    function inserirImoveis(){
    	var form = document.SituacaoEspecialFaturamentoInformarActionForm;
		form.action = 'exibirSituacaoEspecialFaturamentoInserirAtualizarAction.do?consultarImoveis=consultarImoveis';
		form.submit();
		self.close();
    }
    
    function fechar(){
		self.close();
	}
	
	function inserirImoveisNaPrincipal(botaoOrigem){
		chamarReload('exibirSituacaoEspecialFaturamentoInserirAtualizarAction.do?consultarImoveis=S');
		self.close();
	}

	function  habilitaBotao(){
		//objetoPesquisa.value = (objetoPesquisa.value * 1);
   	 	var form = document.forms[0];
		desabilitaCampos();
        form.action = 'exibirSituacaoEspecialFaturamentoInformarAction.do?objetoConsulta=1&bloquear=matricula';
		toUpperCase(form);
        form.submit();
	}
	

-->    
</script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="SituacaoEspecialFaturamentoInformarActionForm"
	dynamicJavascript="false" />

</head>

<body leftmargin="5" topmargin="5" onload="javascript:resizePageSemLink(660, 630);">


<html:form
	action="/validarSituacaoEspecialFaturamentoInserirAtualizarAction"
	type="gcom.gui.faturamento.SituacaoEspecialFaturamentoInformarActionForm"
	name="SituacaoEspecialFaturamentoInformarActionForm" enctype="multipart/form-data" method="post">

	<table width="600" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="595" valign="top" class="centercoltext">

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Pesquisar imóvel para inserir a situa&ccedil;&atilde;o
							especial de faturamento</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			
			
			
			
			
			<table width="100%" border="0">
				<tr>
					<td width="20%"><strong> Matricula:</strong>
					</td>
					<td>
						<logic:notEqual parameter="bloquear" value="matricula">
							<html:text maxlength="9" property="idImovel" size="9"
								onkeypress="validaEnterComMensagem(event, 'exibirSituacaoEspecialFaturamentoInformarAction.do?bloquear=todos', 'idImovel','Matrícula do Imóvel')"
								onkeyup="bloquearLSQLS();limparMatricula();" tabindex="1" />
							<a href="javascript:redirecionarSubmit('recuperarDadosPesquisarImoveisSituacaoEspecialAction.do?caminhoRetornoTelaPesquisaImovel=exibirSituacaoEspecialFaturamentoInformarAction&tipo=matricula');"><img
							width="23" height="21" border="0"
							src="<bean:message key="caminho.imagens"/>pesquisa.gif"
							title="Pesquisar Matrícula" /></a>
							
							<logic:equal name="corImovel" value="exception">
								<html:text property="inscricaoImovel" size="30" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000"
									 />
							</logic:equal>
	
							<logic:notEqual name="corImovel" value="exception">
								<html:text property="inscricaoImovel" size="30" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000"
									/>
							</logic:notEqual>

							<a href="javascript:limpar(1);"> <img
								src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" title="Apagar Matrícula" /></a>
						</logic:notEqual> 
					
						<logic:equal parameter="bloquear" value="matricula">
							<html:text maxlength="9" property="idImovel" size="9"
								disabled="true"
								onkeypress="validaEnter(event, 'exibirSituacaoEspecialFaturamentoInformarAction.do?bloquear=todos', 'idImovel')"
								onkeyup="bloquearLSQLS();document.forms[0].inscricaoImovel.value = '';" />
	
							<a href="javascript:redirecionarSubmit('recuperarDadosPesquisarImoveisSituacaoEspecialAction.do?caminhoRetornoTelaPesquisaImovel=exibirSituacaoEspecialFaturamentoInformarAction&tipo=matricula');"><img
							width="23" height="21" border="0"
							src="<bean:message key="caminho.imagens"/>pesquisa.gif"
							title="Pesquisar Matrícula" /></a>
							<logic:equal name="corImovel" value="exception">
								<html:text property="inscricaoImovel" size="30" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000"
									disabled="true" />
							</logic:equal>
	
							<logic:notEqual name="corImovel" value="exception">
								<html:text property="inscricaoImovel" size="30" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000"
									/>
							</logic:notEqual>
	
							<a href="javascript:limpar(1);"> <img
								src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" title="Apagar Matrícula" /></a>
						</logic:equal>
					</td>
				</tr>
				
				<tr>
					<td width="20%"><strong> Arquivo:</strong>
					</td>
					<td>
						<html:file property="arquivoMatricula" onchange="bloquearLSQLS();habilitaBotao();" accept="text" ></html:file>
						<logic:notEmpty  name="arquivo" scope="session">
							<bean:write name="arquivo" property="fileName" scope="session"/>
						</logic:notEmpty>
						<logic:notEmpty  name="SituacaoEspecialFaturamentoInformarActionForm" property="arquivoDownload">
						<br/><a href="exibirSituacaoEspecialFaturamentoInformarAction.do?objetoConsulta=1&bloquear=matricula&download=true&consultaQuantidadeImoveis=1"><bean:write name="SituacaoEspecialFaturamentoInformarActionForm" property="arquivoDownload"></bean:write> </a>
						</logic:notEmpty>
					</td>
				</tr>
				
				<tr>
					<td colspan="3">
						<table width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td>
									<table width="100%" border="0" bgcolor="#90c7fc">
									<tr bgcolor="#90c7fc" height="18">
										<td align="center"><strong>Endere&ccedil;o</strong></td>
									</tr>
									</table>
								</td>
							</tr>
							
							<logic:present name= "enderecoFormatado" scope= "request">
								<tr>
									<td>
										<table width="100%" align="center" bgcolor="#99CCFF">
											<!--corpo da segunda tabela-->
												<tr bgcolor="#FFFFFF" height="18">	
													<td>
														<div align="center"><span id="endereco"><bean:write
															name="SituacaoEspecialFaturamentoInformarActionForm" property="endereco" />&nbsp;</span></div>
														<html:hidden property="endereco" />
													</td>
												</tr>
										</table>
								  	</td>
								</tr>
							</logic:present>
							
						</table>
					</td>
				</tr>
				
				<tr>
					<td colspan="3">
					<hr>
					</td>
				</tr>
				<tr>
					<td><strong>Bairro:</strong></td>
					<td colspan="3"><html:text maxlength="30" property="nomeBairro" size="56" tabindex="2" onkeyup = "javascript:bloquearM();" /> 	
					</td>
				</tr>
				<tr>
					<td colspan="4">
					<hr>
					</td>
				</tr>
				<tr>
					<td colspan="3">Informe os dados da inscri&ccedil;&atilde;o
					inicial:</td>
				</tr>
				

					<tr>
						<td><strong>Localidade:</strong></td>
						<td colspan="2"><html:text maxlength="3" property="localidadeOrigemID"
							size="3"
							onkeypress="limparOrigem(1);validaEnterComMensagem(event, 'exibirSituacaoEspecialFaturamentoInformarAction.do?objetoConsulta=1&inscricaoTipo=origem&bloquear=matricula', 'localidadeOrigemID','Código da Localidade');"
							tabindex="3" 
							onblur="javascript:replicarLocalidade();" 
							onkeyup = "javascript:bloquearM();limparOrigem(1);"
							/>
						<a href="javascript:redirecionarSubmit('recuperarDadosPesquisarImoveisSituacaoEspecialAction.do?caminhoRetornoTelaPesquisa=exibirSituacaoEspecialFaturamentoInformarAction&tipo=origemPopup');
							        bloquearM();limparOrigem(1);"><img
							width="23" height="21" border="0" 
							src="<bean:message key="caminho.imagens"/>pesquisa.gif"
							title="Pesquisar Localidade" /></a>
					
							<logic:present
							name="corLocalidadeOrigem">

							<logic:equal name="corLocalidadeOrigem" value="exception">
								<html:text property="nomeLocalidadeOrigem" size="45"
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000" />
							</logic:equal>

							<logic:notEqual name="corLocalidadeOrigem" value="exception">
								<html:text property="nomeLocalidadeOrigem" size="45"
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</logic:notEqual>

						</logic:present> <logic:notPresent name="corLocalidadeOrigem">

							<logic:empty name="SituacaoEspecialFaturamentoInformarActionForm"
								property="localidadeOrigemID">
								<html:text property="nomeLocalidadeOrigem" value="" size="45"
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000" />
							</logic:empty>
							<logic:notEmpty name="SituacaoEspecialFaturamentoInformarActionForm"
								property="localidadeOrigemID">
								<html:text property="nomeLocalidadeOrigem" size="45"
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: 	#000000" />
							</logic:notEmpty>
						</logic:notPresent> 
						
						<a href="javascript:limparBorrachaOrigem(1);limparMsgQuadraInexistente();document.forms[0].localidadeOrigemID.focus();">
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
							border="0" title="Apagar Localidade" /> </a>
					</td>
					</tr>

					<tr>
						<td><strong>Setor Comercial:</strong></td>
						<td colspan="2"><html:text maxlength="3" property="setorComercialOrigemCD"
							size="3"
							onkeypress="limparOrigem(2);validaEnterDependencia(event, 'exibirSituacaoEspecialFaturamentoInformarAction.do?objetoConsulta=2&inscricaoTipo=origem&bloquear=matricula', document.forms[0].setorComercialOrigemCD, document.forms[0].localidadeOrigemID.value, 'Localidade Inicial');"
							tabindex="4"
							onblur ="javascript:replicarSetorComercial();"
							onkeyup = "limparOrigem(2);" />
													
							<a href="javascript:redirecionarSubmit('recuperarDadosPesquisarImoveisSituacaoEspecialAction.do?caminhoRetornoTelaPesquisa=exibirSituacaoEspecialFaturamentoInformarAction&tipo=setorComercialOrigem');"><img
								width="23" height="21"
								 border="0" 
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Setor Comercial" /></a>
	
							<logic:present name="corSetorComercialOrigem">
								<logic:equal name="corSetorComercialOrigem" value="exception">
									<html:text property="nomeSetorComercialOrigem" size="45"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:equal>
								<logic:notEqual name="corSetorComercialOrigem" value="exception">
									<html:text property="nomeSetorComercialOrigem" size="45"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEqual>
	
							</logic:present> <logic:notPresent name="corSetorComercialOrigem">
								<logic:empty name="SituacaoEspecialFaturamentoInformarActionForm"
									property="setorComercialOrigemCD">
									<html:text property="nomeSetorComercialOrigem" value=""
										size="45" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:empty>
								<logic:notEmpty name="SituacaoEspecialFaturamentoInformarActionForm"
									property="setorComercialOrigemCD">
									<html:text property="nomeSetorComercialOrigem" size="45"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEmpty>
							</logic:notPresent> 
								
							<a href="javascript:limparBorrachaOrigem(2);document.forms[0].setorComercialOrigemCD.focus();">
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
							border="0" title="Apagar Setor Comercial" /> </a>
							<html:hidden property="setorComercialOrigemID" />
						</td>
					</tr>
					<tr>
						<td><strong>Quadra:</strong></td>
						<td  colspan="2" >
							<html:text maxlength="5" property="quadraOrigemID"
								size="5"
								onkeypress="limparOrigem(3);validaEnterDependencia(event, 'exibirSituacaoEspecialFaturamentoInformarAction.do?objetoConsulta=3&inscricaoTipo=origem&bloquear=matricula', this, document.forms[0].setorComercialOrigemCD.value, 'Setor Comercial Inicial');"
								tabindex="5"
								onblur = "replicarQuadra(this.value);" />
							
						    <a href="javascript:redirecionarSubmit('recuperarDadosPesquisarImoveisSituacaoEspecialAction.do?caminhoRetornoTelaPesquisa=exibirSituacaoEspecialFaturamentoInformarAction&tipo=quadraOrigem');">     
						         <img width="23" height="21" border="0" style="cursor:hand;"
									src="<bean:message key="caminho.imagens"/>pesquisa.gif"
									title="Pesquisar Quadra" />
							</a>    
						
						
						
							<logic:present name="corQuadraOrigem">
								<logic:equal name="corQuadraOrigem" value="exception">
									<html:text property="nomeQuadraOrigem" size="45"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000"
										disabled="true" />
								</logic:equal>
		
								<logic:notEqual name="corQuadraOrigem" value="exception">
									<html:text property="nomeQuadraOrigem" size="45"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000"
										disabled="true" />
								</logic:notEqual>
							</logic:present>
					
							<logic:notPresent name="corQuadraOrigem">
								<logic:empty name="SituacaoEspecialFaturamentoInformarActionForm" property="quadraOrigemID">
									<html:text property="nomeQuadraOrigem" value="" size="45"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000"
										disabled="true" />
								</logic:empty>
								<logic:notEmpty name="SituacaoEspecialFaturamentoInformarActionForm" property="quadraOrigemID">
									<html:text property="nomeQuadraOrigem" size="45"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000"
										disabled="true" />
								</logic:notEmpty>
							</logic:notPresent>
							
							<a href="javascript:limparBorrachaOrigem(3);">
								<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" style="cursor:hand"; title="Apagar Quadra" /> 
							</a>
							
						</td>
					</tr>
					
					<tr>
						<td><strong>Lote:</strong></td>
						<td colspan="2">
							<html:text maxlength="4" property="loteOrigem" size="3" tabindex="6" onkeyup="limparSubloteOrigem();" onblur = "replicarLote();"/>
						</td>
					</tr>
					<tr>
						<td><strong>Sublote:</strong></td>
						<td colspan="2"><html:text maxlength="3" property="subloteOrigem" size="3"
							tabindex="7" 
							onblur = "replicarSubLote();"/></td>
					</tr>
					<tr>
						<td colspan="3">
							<hr>
						</td>
					</tr>

					<tr>
						<td colspan="3">Informe os dados da inscri&ccedil;&atilde;o final:</td>
					</tr>
					<tr>
						<td><strong>Localidade:</strong></td>
						<td colspan="2"><html:text maxlength="3" property="localidadeDestinoID"
							size="3"
							onclick="validarLocalidade()"
							onkeypress="limparDestino(1);validaEnter(event, 'exibirSituacaoEspecialFaturamentoInformarAction.do?objetoConsulta=1&inscricaoTipo=destino&bloquear=matricula&bloquear=matricula&campoDesabilita=1', 'localidadeDestinoID');"
							tabindex="8"
							onkeyup="desabilitaIntervaloDiferente(1);" />
							
													
						<a href="javascript:redirecionarSubmit('recuperarDadosPesquisarImoveisSituacaoEspecialAction.do?caminhoRetornoTelaPesquisa=exibirSituacaoEspecialFaturamentoInformarAction&tipo=destinoPopup');"><img
							width="23" height="21" border="0" 
							src="<bean:message key="caminho.imagens"/>pesquisa.gif"
							title="Pesquisar Localidade" /></a>
							
							<logic:present name="corLocalidadeDestino">

							<logic:equal name="corLocalidadeDestino" value="exception">
								<html:text property="nomeLocalidadeDestino" size="45"
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000" />
							</logic:equal>

							<logic:notEqual name="corLocalidadeDestino" value="exception">
								<html:text property="nomeLocalidadeDestino" size="45"
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</logic:notEqual>

						</logic:present> <logic:notPresent name="corLocalidadeDestino">

							<logic:empty name="SituacaoEspecialFaturamentoInformarActionForm"
								property="localidadeDestinoID">
								<html:text property="nomeLocalidadeDestino" value="" size="45"
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000" />
							</logic:empty>
							<logic:notEmpty name="SituacaoEspecialFaturamentoInformarActionForm"
								property="localidadeDestinoID">
								<html:text property="nomeLocalidadeDestino" size="45"
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: 	#000000" />
							</logic:notEmpty>
						</logic:notPresent> 
						
					<a href="javascript:limparBorrachaDestino(1);document.forms[0].localidadeDestinoID.focus();">
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar Localidade" /> </a>
							
							</td>
					</tr>
					<tr>
						<td><strong>Setor Comercial:</strong></td>
						<td colspan="2"><html:text maxlength="3" property="setorComercialDestinoCD"
							size="3"
							onkeypress="limparDestino(2);validaEnterDependencia(event, 'exibirSituacaoEspecialFaturamentoInformarAction.do?objetoConsulta=2&inscricaoTipo=destino&bloquear=matricula&campoDesabilita=2', document.forms[0].setorComercialDestinoCD, document.forms[0].localidadeDestinoID.value, 'Localidade Final');"
							tabindex="9"
							onkeyup="desabilitaIntervaloDiferente(2);" />
												
						<a href="javascript:redirecionarSubmit('recuperarDadosPesquisarImoveisSituacaoEspecialAction.do?caminhoRetornoTelaPesquisa=exibirSituacaoEspecialFaturamentoInformarAction&tipo=setorComercialDestino');;
							        limparDestino(2);"><img
							width="23" height="21" border="0" 
							src="<bean:message key="caminho.imagens"/>pesquisa.gif"
							title="Pesquisar Setor Comercial" /></a>
						
						 <logic:present name="corSetorComercialDestino">

							<logic:equal name="corSetorComercialDestino" value="exception">
								<html:text property="nomeSetorComercialDestino" size="45"
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000" />
							</logic:equal>

							<logic:notEqual name="corSetorComercialDestino" value="exception">
								<html:text property="nomeSetorComercialDestino" size="45"
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</logic:notEqual>

						</logic:present> <logic:notPresent name="corSetorComercialDestino">

							<logic:empty name="SituacaoEspecialFaturamentoInformarActionForm"
								property="setorComercialDestinoCD">
								<html:text property="nomeSetorComercialDestino" value=""
									size="45" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000" />
							</logic:empty>
							<logic:notEmpty name="SituacaoEspecialFaturamentoInformarActionForm"
								property="setorComercialDestinoCD">
								<html:text property="nomeSetorComercialDestino" size="45"
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</logic:notEmpty>

						</logic:notPresent> 
						
											<a href="javascript:limparBorrachaDestino(2);document.forms[0].setorComercialDestinoCD.focus();">
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar Setor Comercial" /> </a>
	
							 <html:hidden
							property="setorComercialDestinoID" /></td>
					</tr>
				
					
					
					<tr>
						<td><strong>Quadra:</strong></td>
						<td  colspan="2" ><html:text maxlength="5" property="quadraDestinoID" size="5"
							onkeypress="limparDestino(3);validaEnterDependencia(event, 'exibirSituacaoEspecialFaturamentoInformarAction.do?objetoConsulta=3&inscricaoTipo=destino&bloquear=matricula', this, document.forms[0].setorComercialDestinoCD.value, 'Setor Comercial Final');"
							tabindex="10" />
							
						    <a href="javascript:redirecionarSubmit('recuperarDadosPesquisarImoveisSituacaoEspecialAction.do?caminhoRetornoTelaPesquisa=exibirSituacaoEspecialFaturamentoInformarAction&tipo=quadraDestino');">     
						         <img width="23" height="21" border="0" style="cursor:hand;"
									src="<bean:message key="caminho.imagens"/>pesquisa.gif"
									title="Pesquisar Quadra" />
							</a>    
					<logic:present name="corQuadraDestino">
						<logic:equal name="corQuadraDestino" value="exception">
							<html:text property="quadraDestinoNM" size="45"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000"
								disabled="true" />
						</logic:equal>

						<logic:notEqual name="corQuadraDestino" value="exception">
							<html:text property="quadraDestinoNM" size="45"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								disabled="true" />
						</logic:notEqual>
					</logic:present>
					
					<logic:notPresent name="corQuadraDestino">
						<logic:empty name="SituacaoEspecialFaturamentoInformarActionForm"
							property="quadraDestinoID">
							<html:text property="quadraDestinoNM" value="" size="45"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000"
								disabled="true" />
						</logic:empty>
						<logic:notEmpty name="SituacaoEspecialFaturamentoInformarActionForm"
							property="quadraDestinoID">
							<html:text property="quadraDestinoNM" size="45"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								disabled="true" />
						</logic:notEmpty>
					</logic:notPresent>
							
					<a href="javascript:limparBorrachaDestino(3);">
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" style="cursor:hand"; title="Apagar Quadra" /> </a>
					
						<!--<html:hidden property="quadraDestinoID" />--></td>
					
					
					
					
		<!--  				<td><strong>Quadra:</strong></td>
						<td colspan="2"><html:text maxlength="3" property="quadraDestinoID" size="3"
							onkeypress="limparDestino(3);validaEnterDependencia(event, 'exibirSituacaoEspecialFaturamentoInformarAction.do?objetoConsulta=3&inscricaoTipo=destino&bloquear=matricula&campoDesabilita=3', document.forms[0].quadraDestinoID, document.forms[0].setorComercialDestinoCD.value, 'Setor Comercial Final');"
							tabindex="9"
							onkeyup="desabilitaIntervaloDiferente(3);" />
							
						<logic:present name="corQuadraDestino">
						 	<logic:equal name="corQuadraDestino" value="exception">
									<span style="color:#ff0000" id="msgQuadraDestino"><bean:write name="SituacaoEspecialFaturamentoInformarActionForm" property="quadraMensagemDestino"/></span>
							</logic:equal>	
			            </logic:present>
					 <html:hidden property="quadraDestinoID" /></td>
				-->
					</tr>
					<tr>
						<td><strong>Lote:</strong></td>
						<td colspan="2"><html:text maxlength="4" property="loteDestino" size="3"
							tabindex="11" onkeyup="desabilitaIntervaloDiferente(4);" /></td>
					</tr>
					<tr>
						<td><strong>Sublote:</strong></td>
						<td colspan="2"><html:text maxlength="3" property="subloteDestino" size="3"
							tabindex="12" /></td>
					</tr>

				
				<!-- ROTA INICIAL -->
				
				<tr> 
            	  <td colspan="3" HEIGHT="15"></td>
            	</tr>
				<tr> 
            	  <td colspan="3"> 
            	  
            	  	<table width="100%" align="center" bgcolor="#99CCFF" border="0">
                	<tr> 
                      <td><strong>Informe os dados da Rota Inicial:</strong></td>
                	</tr>
                	<tr bgcolor="#cbe5fe"> 
                  	  	<td width="100%" align="center"> 
                  	    
                  	    <table width="100%" border="0">
						 	<tr>
								<td height="10" width="120"><strong>Rota:</strong></td>
								<td><html:text property="cdRotaInicial" maxlength="4" size="7"
								onkeyup = "bloquearM();limpar(11);replicarRota();"/></td>																								
						  	</tr>
						  
						  	<tr>
								<td height="10"><strong>Seq. da Rota:</strong></td>
								<td><html:text property="sequencialRotaInicial" maxlength="6" size="7"
								onkeyup="replicarSequencialRota();"/></td>																								
						  	</tr>
						  
						</table>
					  	
					  	</td>
                    </tr>
                  	</table>
                  
                  </td>
                </tr>
				
				<!-- FIM ROTA INICIAL -->
				
				<tr> 
            	  <td colspan="3" HEIGHT="5"></td>
            	</tr>
				
				<!-- ROTA FINAL -->
				
				<tr> 
            	  <td colspan="3"> 
            	  
            	  	<table width="100%" align="center" bgcolor="#99CCFF" border="0">
                	<tr> 
                      <td><strong>Informe os dados da Rota Final:</strong></td>
                	</tr>
                	<tr bgcolor="#cbe5fe"> 
                  	  	<td width="100%" align="center"> 
                  	    
                  	    <table width="100%" border="0">
						 	<tr>
								<td height="10" width="120"><strong>Rota:</strong></td>
								<td><html:text property="cdRotaFinal" maxlength="4" size="7"
								onkeyup = "bloquearM();limpar(12);"/></td>																								
						  	</tr>
						  	<tr>
								<td height="10"><strong>Seq. da Rota:</strong></td>
								<td><html:text property="sequencialRotaFinal" maxlength="6" size="7"/></td>																								
						  	</tr>
						  
						</table>
					  	
					  	</td>
                    </tr>
                  	</table>
                  
                  </td>
                </tr>
				
				<!-- FIM ROTA FINAL -->
				
				
				
				<tr>
					<td>&nbsp;</td>

					<td  colspan="3" align="right">
					
					 <logic:empty name="bloquear">
							<input type="button" name="selecionar" disabled="disabled"
								onClick="javascript:selecionarQuantidadeDeImoveis(document.SituacaoEspecialFaturamentoInformarActionForm);"
								class="bottonRightCol" value="Selecionar">
						</logic:empty> 
						
						<logic:notEmpty name="bloquear"> 
							<input type="button" name="selecionar"
									onClick="javascript:selecionarQuantidadeDeImoveis(document.SituacaoEspecialFaturamentoInformarActionForm);"
									class="bottonRightCol" value="Selecionar" tabindex="12">
						</logic:notEmpty> 
					</td>
				</tr>
				
				<tr> 
            	  <td colspan="3" HEIGHT="20"></td>
            	 </tr>
            	 
				<tr>
					<td colspan="3">
					<hr>
					</td>
				</tr>

				<tr>
					<td colspan="2" height="9"><strong> <font color="#FF0000"></font></strong>
					<div align="left"></div>
					<div align="left"><strong> <font color="#FF0000"></font></strong></div>
					<div align="left"><strong>Quantidade de im&oacute;veis COM
					situa&ccedil;&atilde;o especial de faturamento : </strong></div>
					</td>
					<td align="right" height="9" ><html:text
						property="quantidadeImoveisCOMSituacaoEspecialFaturamento"
						size="8" maxlength="8" readonly="true" /></td>
				</tr>
				<logic:equal scope="session" name="botaoOrigem" value="Inserir">
					<tr>
						<td colspan="2" height="9"><strong> <font color="#FF0000"></font></strong>
						<div align="left"></div>
						<div align="left"><strong> <font color="#FF0000"></font></strong></div>
						<div align="left"><strong>Quantidade de im&oacute;veis SEM
						situa&ccedil;&atilde;o especial de faturamento : </strong></div>
						</td>
						<td align="right" height="9"><html:text
							property="quantidadeImoveisSEMSituacaoEspecialFaturamento"
							size="8" maxlength="8" readonly="true" /></td>
					</tr>
				</logic:equal>
	
				<tr>
					<td>&nbsp;</td>
					<td align="right">&nbsp;</td>
				</tr>
			</table>
			<table border="0" width="100%">
				<tr>
					<td colspan="1"><input name="Button" type="button" class="bottonRightCol"
						value="Desfazer" align="left"
						onclick="javascript:desfazer();">
						<input type="button" name="ButtonCancelar" class="bottonRightCol" value="Cancelar"
							onClick="javascript:fechar();">
					</td>
					<td colspan="1" align="right">

						<logic:present name="liberarBotaoInserir" scope="request">
							<input type="button" name="inserirManter" class="bottonRightCol"
								value="<%=(String)session.getAttribute("botaoOrigem") %>" tabindex="13" 
								onClick="javascript:inserirImoveisNaPrincipal('<%=(String)session.getAttribute("botaoOrigem") %>');">
						</logic:present> 
						<logic:notPresent name="liberarBotaoInserir"
							scope="request">
							<input type="button" name="inserirManter" class="bottonRightCol"
								value="<%=(String)session.getAttribute("botaoOrigem") %>" disabled="disabled">
						</logic:notPresent> 
					</td>
				</tr>
			</table>
</td>
</tr>
</table>
</html:form>

</body>
</html:html>