<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>

<%@ page import="gcom.cadastro.imovel.Categoria" %>
<%@ page import="gcom.cadastro.imovel.Subcategoria" %>
<%@ page import="gcom.faturamento.debito.DebitoCobrado" isELIgnored="false"%>
<%@ page import="gcom.faturamento.credito.CreditoRealizado" %>
<%@ page import="gcom.util.Util" %>
<%@ page import="gcom.util.ConstantesSistema" %>
<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro" %>

<%@ page import="gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao" %>
<%@ page import="gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao" %>
<%@ page import="gcom.gui.GcomAction" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">


<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/engine.js'> </script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/util.js'> </script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/interface/AjaxService.js'> </script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="RetificarContaActionForm"/>

<SCRIPT LANGUAGE="JavaScript">

<!--

function configurarCreditoDisponivel(idCreditoRealizado, campo){
			
	AjaxService.configurarCreditoARealizarImovel(idCreditoRealizado, campo.value, {callback: 
		function(dados) {

			if(dados['msg'] != null){
				
				campo.value = dados['valor'];
				alert(dados['msg']);
								
			}	
									
		}, async: false} 
	);
		
}

/**
*
* inserir = false // Somente calcular os valores da conta
* inserir = true // calcular os valores da conta e inserir a mesma no BD
*
**/
function validarForm(form, retificar){

	form.motivoRetificacaoID.disabled = false;
	
	if (validateRetificarContaActionForm(form)){
	
		if (retificar){
			// validacoes dos dados de Leituras
			if ((form.dataLeituraAtualMedicaoHistoricoAgua.value != '' && form.numeroLeituraAtualMedicaoHistoricoAgua.value == '') ||
			      (form.dataLeituraAtualMedicaoHistoricoAgua.value == '' && form.numeroLeituraAtualMedicaoHistoricoAgua.value != '')){
		      
			    alert("Dados de Leitura Água e Data de Leitura de Água devem ser informados. ");
				form.dataLeituraAtualMedicaoHistoricoAgua.focus();  
				return;
			}
			
			if ((form.dataLeituraAtualMedicaoHistoricoAgua.value != '' && !form.numeroLeituraAnteriorMedicaoHistoricoAgua.disabled && form.numeroLeituraAnteriorMedicaoHistoricoAgua.value == '') ||
				      (form.dataLeituraAtualMedicaoHistoricoAgua.value == '' && form.numeroLeituraAnteriorMedicaoHistoricoAgua.value != '')){
			      
				    alert("Dados de Leitura Água e Data de Leitura de Água devem ser informados. ");
					form.dataLeituraAtualMedicaoHistoricoAgua.focus();  
					return;
			}
		
			if ((form.dataLeituraAtualMedicaoHistoricoEsgoto.value != '' && form.numeroLeituraAtualMedicaoHistoricoEsgoto.value == '') ||
			      (form.dataLeituraAtualMedicaoHistoricoEsgoto.value == '' && form.numeroLeituraAtualMedicaoHistoricoEsgoto.value != '')){
		      
			    alert("Dados de Leitura Esgoto e Data de Leitura de Esgoto devem ser informados. ");
				form.dataLeituraAtualMedicaoHistoricoEsgoto.focus();  
				return;
			}
			
			if ((form.dataLeituraAtualMedicaoHistoricoEsgoto.value != '' && !form.numeroLeituraAnteriorMedicaoHistoricoEsgoto.disabled && form.numeroLeituraAnteriorMedicaoHistoricoEsgoto.value == '') ||
				      (form.dataLeituraAtualMedicaoHistoricoEsgoto.value == '' && form.numeroLeituraAnteriorMedicaoHistoricoEsgoto.value != '')){
			      
				    alert("Dados de Leitura Esgoto e Data de Leitura de Esgoto devem ser informados. ");
					form.dataLeituraAtualMedicaoHistoricoEsgoto.focus();  
					return;
			}

			urlRedirect = "/gsan/retificarContaAction.do";
		}
		else{
			urlRedirect = "/gsan/calcularValoresRetificarContaAction.do";
		}				 
		
		var msgDataVencimento = "Data de Vencimento anterior à data corrente.";
		var msgDataVencimento60 = "Data de Vencimento posterior a data corrente mais " + document.getElementById("QTD_DIAS_VENC_PARAM").value + " dias.";
		var DATA_ATUAL = document.getElementById("DATA_ATUAL").value;
		var DATA_ATUAL_60 = document.getElementById("DATA_ATUAL_60").value;
		var ANO_LIMITE = document.getElementById("ANO_LIMITE").value;
 		var PERMITE_ALTERAR_VENCIMENTO =  document.getElementById("PERMITE_ALTERAR_VENCIMENTO").value;
 				
		var listBoxAgua = form.situacaoAguaConta;
		var listBoxEsgoto = form.situacaoEsgotoConta;
		var indicadorEsgotoFaturavel = form.indicadorEsgotoFaturavel;
		var indicadorAguaFaturavel = form.indicadorAguaFaturavel;
	
		if ((form.vencimentoConta.value.substring(6, 10) * 1) < (ANO_LIMITE * 1)){
			alert("Ano do vencimento da conta não deve ser menor que " + ANO_LIMITE + ".");
			form.vencimentoConta.focus();
		}
		else if( form.existeColecao.value == 1){
			valido = false;
			alert("Informe Categorias e Economias.");
		}else if (comparaData(form.vencimentoConta.value, "<", DATA_ATUAL )){
			
				if(!comparaData(form.vencimentoConta.value, "=", form.vencimentoContaAnterior.value )){						
			
					if(PERMITE_ALTERAR_VENCIMENTO == 'false'){				
				 	 alert(msgDataVencimento);
				  	return;
					}	
				}				 
				
				if ( indicadorAguaFaturavel == 1 && form.consumoAgua.value.length < 1){
					alert("O preenchimento do campo Consumo de Água é obrigatório, caso a Situação de Água seja Ligado ou Cortado.");
					form.consumoAgua.focus();
				}
				
				else if ((form.situacaoAguaConta.value != form.SITUACAO_AGUA_NA_BASE.value || form.consumoAgua.value != form.CONSUMO_AGUA_NA_BASE.value)
						&& listBoxAgua.options[listBoxAgua.selectedIndex].id == 1 && (form.consumoAgua.value == "" || form.consumoAgua.value == 0)){
					
					alert("Informe Consumo de Água.");
					form.consumoAgua.focus();
				}
				else if (form.consumoAgua.value.length > 0 && form.consumoAgua.value != 0 && 
					form.consumoAgua.value <= listBoxAgua.options[listBoxAgua.selectedIndex].name){
				
					alert("Consumo informado menor que consumo mínimo para situação da ligação de água, valor tem que ser maior que " + listBoxAgua.options[listBoxAgua.selectedIndex].name);
					form.consumoAgua.focus();
				}
		
				else if (form.consumoEsgoto.value.length > 0 && form.consumoEsgoto.value != 0 && 
					form.consumoEsgoto.value <= listBoxEsgoto.options[listBoxEsgoto.selectedIndex].name){
				
					alert("Volume informado menor que volume mínimo para situação da ligação de esgoto, valor tem que ser maior que " + listBoxesgoto.name);
					form.consumoEsgoto.focus();
				}
				
				else if (indicadorEsgotoFaturavel == 1 && form.percentualEsgoto.value.length < 1){
						alert("O preenchimento do campo Percentual de Esgoto é obrigatório, caso a Situação de Esgoto seja Ligado.");
						form.percentualEsgoto.focus();
				}
				else if (obterQuantidadeInteiro(form.percentualEsgoto.value) > 3 ||
						 obterQuantidadeFracao(form.percentualEsgoto.value) > 2){
						alert("Percentual de Esgoto inválido.");
						form.percentualEsgoto.focus();
				}
				else if (form.consumoAgua.value.length > 0 && form.consumoEsgoto.value.length > 0 &&
						 (form.consumoAgua.value * 1) > (form.consumoEsgoto.value * 1)){
						alert("Consumo de Esgoto não deve ser menor que o Consumo de Água");
						form.consumoEsgoto.value = form.consumoAgua.value;
						form.consumoEsgoto.focus();
				}
				else if( form.existeColecao.value == 1 ){
					alert("Informe Categorias e Economias.");
				}else if (validarCamposDinamicos(form)) {
					redirecionarSubmit(urlRedirect);
				}
			
		}
		else if (comparaData(form.vencimentoConta.value, ">", DATA_ATUAL_60 )){
			
				if(!comparaData(form.vencimentoConta.value, "=", form.vencimentoContaAnterior.value )){	
					if(PERMITE_ALTERAR_VENCIMENTO == 'false'){
					  alert(msgDataVencimento60);
					  return;
					}		
				}			
				
				if (indicadorAguaFaturavel == 1 && form.consumoAgua.value.length < 1){
					alert("O preenchimento do campo Consumo de Água é obrigatório, caso a Situação de Água seja Ligado ou Cortado.");
					form.consumoAgua.focus();
				}				
				else if ((form.situacaoAguaConta.value != form.SITUACAO_AGUA_NA_BASE.value || form.consumoAgua.value != form.CONSUMO_AGUA_NA_BASE.value)
						&& listBoxAgua.options[listBoxAgua.selectedIndex].id == 1 && (form.consumoAgua.value == "" || form.consumoAgua.value == 0)){
					
					alert("Informe Consumo de Água.");
					form.consumoAgua.focus();
				}
				else if (form.consumoAgua.value.length > 0 && form.consumoAgua.value != 0 && 
					form.consumoAgua.value <= listBoxAgua.options[listBoxAgua.selectedIndex].name){
				
					alert("Consumo informado menor que consumo mínimo para situação da ligação de água, valor tem que ser maior que " + listBoxAgua.options[listBoxAgua.selectedIndex].name);
					form.consumoAgua.focus();
				}			
				
				else if (form.consumoEsgoto.value.length > 0 && form.consumoEsgoto.value != 0 && 
					form.consumoEsgoto.value <= listBoxEsgoto.options[listBoxEsgoto.selectedIndex].name){
				
					alert("Volume informado menor que volume mínimo para situação da ligação de esgoto, valor tem que ser maior que " + listBoxesgoto.name);
					form.consumoEsgoto.focus();
				}			
				
				else if (obterQuantidadeInteiro(form.percentualEsgoto.value) > 3 ||
						 obterQuantidadeFracao(form.percentualEsgoto.value) > 2){
						alert("Percentual de Esgoto inválido.");
						form.percentualEsgoto.focus();
				}
				else if (form.consumoAgua.value.length > 0 && form.consumoEsgoto.value.length > 0 &&
						 (form.consumoAgua.value * 1) > (form.consumoEsgoto.value * 1)){
						alert("Consumo de Esgoto não deve ser menor que Consumo de Água.");
						form.consumoEsgoto.value = form.consumoAgua.value;
						form.consumoEsgoto.focus();
				}
				else if( form.existeColecao.value == 1 ){
					alert("Informe Categorias e Economias.");
				}else if (validarCamposDinamicos(form)){
					
					redirecionarSubmit(urlRedirect);
				}			
		}
		else if (indicadorAguaFaturavel == 1 && form.consumoAgua.value.length < 1){
				alert("O preenchimento do campo Consumo de Água é obrigatório, caso a Situação de Água seja Ligado ou Cortado.");
				form.consumoAgua.focus();
		}
		
		else if ((form.situacaoAguaConta.value != form.SITUACAO_AGUA_NA_BASE.value || form.consumoAgua.value != form.CONSUMO_AGUA_NA_BASE.value)
				&& listBoxAgua.options[listBoxAgua.selectedIndex].id == 1 && (form.consumoAgua.value == "" || form.consumoAgua.value == 0)){
					
			alert("Informe Consumo de Água.");
			form.consumoAgua.focus();
		}
		else if (form.consumoAgua.value.length > 0 && form.consumoAgua.value != 0 && 
				form.consumoAgua.value <= listBoxAgua.options[listBoxAgua.selectedIndex].name){
				
			alert("Consumo informado menor que consumo mínimo para situação da ligação de água, valor tem que ser maior que " + listBoxAgua.options[listBoxAgua.selectedIndex].name);
			form.consumoAgua.focus();
		}

		else if (form.consumoEsgoto.value.length > 0 && form.consumoEsgoto.value != 0 && 
				form.consumoEsgoto.value <= listBoxEsgoto.options[listBoxEsgoto.selectedIndex].name){
				
			alert("Volume informado menor que volume mínimo para situação da ligação de esgoto, valor tem que ser maior que " + listBoxesgoto.name);
			form.consumoEsgoto.focus();
		}
		
	
		else if (obterQuantidadeInteiro(form.percentualEsgoto.value) > 3 ||
				 obterQuantidadeFracao(form.percentualEsgoto.value) > 2){
				alert("Percentual de Esgoto inválido.");
				form.percentualEsgoto.focus();
		}
		else if (form.consumoAgua.value.length > 0 && form.consumoEsgoto.value.length > 0 &&
				(form.consumoAgua.value * 1) > (form.consumoEsgoto.value * 1)){
				 alert("Consumo de Esgoto não deve ser menor que Consumo de Água.");
				 form.consumoEsgoto.value = form.consumoAgua.value;
				 form.consumoEsgoto.focus();
		}
		else if (validarCamposDinamicos(form)){
			//converteVirgula(form.percentualEsgoto);
			redirecionarSubmit(urlRedirect);
		}
	}
}


	function validaMotivoRetificacaoInformado() {
		var form = document.forms[0];
		
		AjaxService.validarMotivoRetificacaoInformado( form.motivoRetificacaoID.value, form.idImovel.value, function(erro){
			
			if(erro){
				form.action = "exibirRetificarContaAction.do?idMotivoRetificacao="+form.motivoRetificacaoID.value;
				form.submit();
			}
		});
		
	}

function desabilitaMotivoCaucionamento() {
	var form = document.forms[0];
	var INDICADOR_OPERACAO =  document.getElementById("INDICADOR_OPERACAO").value;
	
	if(INDICADOR_OPERACAO == "caucionar"){
		form.motivoRetificacaoID.disabled = true;
	}else{
		form.motivoRetificacaoID.disabled = false;
	}
}

function removerCategoria(idCategoria){
 	var form = document.forms[0];
 	
 	if (validarCamposDinamicos(form)){
 		form.action = "/gsan/removerSelecaoCategoriaActionRetificarConta.do?idCategoria=" + idCategoria + "&mapeamento=exibirRetificarConta";
 		if (confirm("Confirma remoção?")){
			form.submit();
		}
	}
 }
 
 

 function habilitacaoCamposAgua(){
		var form = document.forms[0];
	 
	 	if (form.indicadorAguaFaturavel.value == 2){
		
	 		form.consumoAgua.value = "";
	 		form.consumoAgua.disabled = true;
	 	}
	 	else{
	 		form.consumoAgua.disabled = false;
	 	}
	}

	function verificarIndicadorFaturavelLigacaoAguaSituacao(idLigacaoAguaSituacao,listBoxAgua, consumoAgua){

		var form = document.forms[0];
		
		AjaxService.verificaIndicadorFaturavelLigacaoAguaSituacao(idLigacaoAguaSituacao, function(indicadorFaturavel) {
				form.indicadorAguaFaturavel.value = indicadorFaturavel;
				habilitacaoCamposAgua();				
			  });
	}
 
 
 function validarCamposDinamicos(form){
 
 	var camposValidos = true;
 
 	for (i=0; i < form.elements.length; i++) {
    
    	if (form.elements[i].type == "text" && form.elements[i].id.length > 1){
    		
			switch (form.elements[i].id){
			
				case "categoria":
				
					if (form.elements[i].value.length < 1){
						alert("Informe Quantidade de Economias.");
						form.elements[i].focus();
						camposValidos = false;
					}
					else if (isNaN(form.elements[i].value) || form.elements[i].value.indexOf('.') != -1){
						alert("Quantidade de Economias deve conter apenas valores inteiros.");
						form.elements[i].focus();
						camposValidos = false;
					}
					else if (!testarCampoValorZero(form.elements[i], "Quantidade de Categorias")){
						form.elements[i].focus();
						camposValidos = false;
					}
					
				
					break;
					
				case "debito":
				
					var value = form.elements[i].value;
					value = value.replace(/\./g, '');
					value = value.replace(/,/g, '.');
				
					if (value.length < 1){
						alert("Informe Valor do Débito.");
						form.elements[i].focus();
						camposValidos = false;
					}
					else if (isNaN(value) || value < 0){
						alert("Valor do Débito deve somente conter números positivos.");
						form.elements[i].focus();
						camposValidos = false;
					}
					else if (!testarCampoValorZeroDecimal(form.elements[i], "Valor do Débito")){
						form.elements[i].focus();
						camposValidos = false;
					}
					
					break;
					
				case "credito":
				
					var value = form.elements[i].value;
					value = value.replace(/\./g, '');
					value = value.replace(/,/g, '.');
				
					if (value.length < 1){
						alert("Informe Valor do Crédito.");
						form.elements[i].focus();
						camposValidos = false;
					}
					else if (isNaN(value) || value < 0){
						alert("Valor do Crédito deve somente conter numéros positivos.");
						form.elements[i].focus();
						camposValidos = false;
					}
					else if (!testarCampoValorZeroDecimal(form.elements[i], "Valor do Crédito")){
						form.elements[i].focus();
						camposValidos = false;
					}
			
					break;
					
				default:
					break;
			}	
    	}
    	
    	
    	if (!camposValidos){
    		break;
    	}
    }
    
    return camposValidos;
}
 
 function preencherConsumoEsgoto() {
	 var form = document.forms[0];

	 if (form.consumoAgua.value.length > 0 && form.consumoEsgoto.value.length > 0){
				 form.consumoEsgoto.value = form.consumoAgua.value;
	}
}	
 
//-->
</SCRIPT>

<SCRIPT LANGUAGE="JavaScript">

<!--
 
 	function removerDebitoCobrado(form){
	 	var form = document.forms[0];
	 	
	 	form.action = "/gsan/removerSelecaoDebitoCobradoActionRetificarConta.do?colecaoDebitoCobrardoRemover=" + obterValorCheckboxMarcado() +
	 			"&idImovel="+form.idImovel.value +"&mapeamento=exibirRetificarConta";
	 	
	 	if (confirm("Confirma remoção?")){
			form.submit();
		}
 	}
 	function removerCreditoRealizado(form){
 		 		
	 	var form = document.forms[0];
	 	
	 	form.action = "/gsan/removerSelecaoCreditoRealizadoAction.do?colecaoCreditoRemover=" + obterValorCheckboxMarcado()+"&idImovel="+form.idImovel.value;
	 	
		if (confirm("Confirma remoção?")){
			form.submit();
		}
 	}

 	function habilitacaoCamposEsgoto(){

		var form = document.forms[0];
		
		if (form.indicadorEsgotoFaturavel.value == 1){

			form.consumoEsgoto.disabled = false;
			form.percentualEsgoto.disabled = false;
			form.ligacaoEsgotoPerfilId.disabled = false;
	 	}else{

			form.consumoEsgoto.value = "";
			form.consumoEsgoto.disabled = true;
			
			form.percentualEsgoto.disabled = true;
			form.percentualEsgoto.value = "";
			
			form.ligacaoEsgotoPerfilId.disabled = true;
			form.ligacaoEsgotoPerfilId.value = 0;
	  	}
	  	
	}


	function verificarIndicadorFaturavel(idLigacaoEsgotoSituacao){

		var form = document.forms[0];
		
		AjaxService.verificaIndicadorFaturavelLigacaoEsgotoSituacao(idLigacaoEsgotoSituacao, function(indicadorFaturavel) {

				form.indicadorEsgotoFaturavel.value = indicadorFaturavel;
				habilitacaoCamposEsgoto();
				
			  });
	}
	 

	function carregarPercentualPerfil(percentualPerfil){
		
		var form = document.forms[0];
	
		if (form.situacaoEsgotoConta.value != null && form.situacaoEsgotoConta.value != -1){
	
			AjaxService.verificaIndicadorFaturavelLigacaoEsgotoSituacao(form.situacaoEsgotoConta.value, 
				       function(indicadorFaturavel) {
		
				  			if (indicadorFaturavel == 1 && percentualPerfil != 0){
				  				form.percentualEsgoto.readonly = "true";
				  				form.percentualEsgoto.value = percentualPerfil;
				  				alert('Será atribuído ao valor da conta ' + percentualPerfil + '% do valor de água como valor de esgoto.');	
					  		}else{
					  			form.percentualEsgoto.readonly= "false";
					  			form.percentualEsgoto.value = "";
						  	}
						  });
		}else{
	
			form.percentualEsgoto.value = "";
		}
	}
 

	function voltarTelaManter(){
		window.location.href= "exibirManterContaAction.do?idImovelRequest=" + document.forms[0].idImovel.value;
	}
	
	function facilitador(objeto){

		if (objeto.id == "0" || objeto.id == undefined){
			objeto.id = "1";
			 marcarDesmarcarTodos(objeto.id);
		}
		else{
			objeto.id = "0";
			 marcarDesmarcarTodos(objeto.id);
		}
	}
	
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	
	    var form = document.forms[0];
	
	     if (tipoConsulta == 'cliente') {
	    	 
		      form.idCliente.value = codigoRegistro;
		      form.nomeCliente.value = descricaoRegistro;
		      form.nomeCliente.style.color = "#000000";
		      form.action='exibirRetificarContaAction.do';
		      submeterFormPadrao(form);
	    }
	 }
	
	function limparCliente(){
		
		var form = document.forms[0];
	    form.idCliente.value = "";
	    form.nomeCliente.value = "";
	    form.nomeCliente.style.color = "#000000";
	}
 
//-->
</SCRIPT>


<logic:present name="colecaoCategoria">

	<SCRIPT LANGUAGE="JavaScript">
	<!--
	function concatenarParametro(){
	 	abrirPopup("exibirAdicionarDebitoCobradoContaAction.do?imovel=" + document.forms[0].idImovel.value, 395, 450);
	 }
	 
	 function concatenarParametroCredito(){
	 	abrirPopup("exibirAdicionarCreditoRealizadoContaAction.do?imovel=" + document.forms[0].idImovel.value+"&pRemPhad=1", 395, 450);
	 }
	 
	//-->
	</SCRIPT>
	
</logic:present>

<logic:present name="colecaoSubcategoria">

	<SCRIPT LANGUAGE="JavaScript">
	<!--
	function concatenarParametro(){
	 	abrirPopup("exibirAdicionarDebitoCobradoContaAction.do?imovel=" + document.forms[0].idImovel.value, 395, 450);
	 }
	 
	 function concatenarParametroCredito(){
	 	abrirPopup("exibirAdicionarCreditoRealizadoContaAction.do?imovel=" + document.forms[0].idImovel.value, 395, 450);
	 }
	 
	//-->
	</SCRIPT>
	
</logic:present>

</head>

<script type="text/javascript" src="<c:out value='${pageContext.request.contextPath}'/>/dwr/engine.js"> </script>
<script type="text/javascript" src="<c:out value='${pageContext.request.contextPath}'/>/dwr/util.js"> </script>
<script type="text/javascript" src="<c:out value='${pageContext.request.contextPath}'/>/dwr/interface/AjaxService.js"> </script>

<body leftmargin="5" topmargin="5" onload="desabilitaMotivoCaucionamento();">

<html:form action="/exibirRetificarContaAction" method="post">

<INPUT TYPE="hidden" ID="DATA_ATUAL" value="${requestScope.dataAtual}"/>
<INPUT TYPE="hidden" ID="DATA_ATUAL_60" value="${requestScope.dataAtual60}"/>
<INPUT TYPE="hidden" ID="ANO_LIMITE" value="${requestScope.anoLimite}"/>
<INPUT TYPE="hidden" ID="QTD_DIAS_VENC_PARAM" value="${requestScope.qtdDiasVencimentoConta}"/>
<INPUT TYPE="hidden" ID="PERMITE_ALTERAR_VENCIMENTO" value="${requestScope.permiteAlterarVencimentoAnteriorOuPosteriorDataCorrente}"/>
<INPUT TYPE="hidden" ID="INDICADOR_OPERACAO" value="${sessionScope.indicadorOperacao}" name="indicadorOperacao"/>
<INPUT TYPE="hidden" ID="SITUACAO_AGUA_NA_BASE" value="${requestScope.SITUACAO_AGUA_NA_BASE}"/>
<INPUT TYPE="hidden" ID="CONSUMO_AGUA_NA_BASE" value="${requestScope.CONSUMO_AGUA_NA_BASE}"/>

<html:hidden property="indicadorEsgotoFaturavel"/>
<html:hidden property="indicadorAguaFaturavel"/>
<html:hidden property="indicadorPermissaoAlterarPercentualEsgoto"/>
<html:hidden property="vencimentoContaAnterior"/>

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

      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
          	
          	<logic:present name="indicadorOperacao" scope="session">
				<logic:equal name="indicadorOperacao" value="caucionar">
					<td class="parabg">Caucionar Conta</td>
				</logic:equal>
				<logic:notEqual name="indicadorOperacao" value="caucionar">
					<td class="parabg">Retificar Conta</td>
				</logic:notEqual>
			</logic:present>
        
           	<logic:notPresent name="indicadorOperacao" scope="session">
				<td class="parabg">Retificar Conta</td>
			</logic:notPresent>
			
			
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
        </tr>
      </table>
      <p>&nbsp;</p>
      
	<table width="100%" border="0">
	<tr>
		<td align="right"></td>
	</tr>
	</table>

      <table width="100%" border="0">
      <tr> 
          <td colspan="4"></td>
      </tr>
      <tr> 
          <td colspan="4">
				
				<table width="100%" align="center" bgcolor="#99CCFF" border="0">
				<tr>
					<td><strong>Dados do Imóvel</strong></td>
				</tr>
				<tr bgcolor="#cbe5fe">
					<td width="100%" align="center">
			
						<table width="100%" border="0">
						<tr> 
							<td height="10" width="145"><strong>Matrícula do Imóvel:</strong></td>
				          	<td>
				          		<html:text property="idImovel" size="9" readonly="true" style="background-color:#EFEFEF; border:0"/>
				          		<html:text property="inscricaoImovel" size="32" readonly="true" style="background-color:#EFEFEF; border:0"/>
						  	</td>
						</tr>
						<tr> 
							<td height="10"><strong>Nome do Cliente Usuário Imóvel:</strong></td>
							<td>
								<html:text property="nomeClienteUsuario" size="45" readonly="true" style="background-color:#EFEFEF; border:0"/>
							</td>
						</tr>
						<tr>
							<td height="10"><strong>Nome do Cliente Responsável Imóvel:</strong></td>
							<td><html:text property="nomeClienteResponsavel" size="45"
								readonly="true" style="background-color:#EFEFEF; border:0" />
							</td>
						</tr>
						<tr> 
							<td height="10"><strong>Situação de Água:</strong></td>
							<td>
								<html:text property="situacaoAguaImovel" size="45" readonly="true" style="background-color:#EFEFEF; border:0"/>
							</td>
						</tr>
						<tr> 
							<td height="10"><strong>Situação de Esgoto:</strong></td>
							<td>
								<html:text property="situacaoEsgotoImovel" size="45" readonly="true" style="background-color:#EFEFEF; border:0"/>
							</td>
						</tr>
						</table>

					</td>
				</tr>
				</table>

		  </td>
      </tr>
      <tr>
      	<td colspan="4" height="10"></td>
      </tr>
            
      <tr> 
          <td colspan="4">
				
				<table width="100%" align="center" bgcolor="#99CCFF" border="0">
				<tr>
					<td><strong>Dados da Conta:</strong></td>
				</tr>
				<tr bgcolor="#cbe5fe">
					<td width="100%" align="center">
			
						<table width="100%" border="0">
						<tr> 
							<td width="145"><strong>Mês e Ano da Conta:</strong></td>
          					<td><html:text property="mesAnoConta" size="8" readonly="true" style="background-color:#EFEFEF; border:0"/></td>
						</tr>
						<tr> 
				          	<logic:present name="indicadorOperacao" scope="session">
								<logic:equal name="indicadorOperacao" value="caucionar">
						          	<td height="10"><strong>Motivo do Caucionamento:<font color="#FF0000">*</font></strong></td>
						          	<td>
										<html:select property="motivoRetificacaoID" style="width: 400px;" tabindex="3" onchange="javascript:desabilitaMotivoCaucionamento();" onblur="desabilitaMotivoCaucionamento();" onkeyup="desabilitaMotivoCaucionamento();">
											<logic:present name="colecaoMotivoRetificacaoConta">
												<html:options collection="colecaoMotivoRetificacaoConta" labelProperty="descricaoMotivoRevisaoConta" property="id"/>
											</logic:present>
										</html:select>
								  	</td>
								</logic:equal>
								<logic:notEqual name="indicadorOperacao" value="caucionar">
						          	<td height="10"><strong>Motivo da Retificação:<font color="#FF0000">*</font></strong></td>
						          	<td>
										<html:select property="motivoRetificacaoID" style="width: 400px;" tabindex="3" onchange="javascript:validaMotivoRetificacaoInformado();">
											<html:option value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>
											<logic:present name="colecaoMotivoRetificacaoConta">
												<html:options collection="colecaoMotivoRetificacaoConta" labelProperty="descricaoMotivoRetificacaoConta" property="id"/>
											</logic:present>
										</html:select>
								  	</td>
								</logic:notEqual>
							</logic:present>
				           	<logic:notPresent name="indicadorOperacao" scope="session">
					          	<td height="10"><strong>Motivo da Retificação:<font color="#FF0000">*</font></strong></td>
					          	<td>
									<html:select property="motivoRetificacaoID" style="width: 400px;" tabindex="3" onchange="javascript:validaMotivoRetificacaoInformado();">
										<html:option value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>
										<logic:present name="colecaoMotivoRetificacaoConta">
											<html:options collection="colecaoMotivoRetificacaoConta" labelProperty="descricaoMotivoRetificacaoConta" property="id"/>
										</logic:present>
									</html:select>
							  	</td>
							</logic:notPresent>
				      	</tr>		
						<tr> 
				          	<td height="10"><strong>Data de Vencimento:<font color="#FF0000">*</font></strong></td>
				          	<td>
								<html:text property="vencimentoConta" size="11" maxlength="10" tabindex="4" onkeyup="mascaraData(this, event);"/>
								<a href="javascript:abrirCalendario('RetificarContaActionForm', 'vencimentoConta')">
				                 <img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário"/></a>
				                 
				                dd/mm/aaaa
						  	</td>
				      	</tr>

						<logic:present name="exibirConsumoTarifa" scope="session">
							<tr>
					          	<td height="10"><strong>Tarifa de Consumo:<font color="#FF0000">*</font></strong></td>
					          	<td>
									<html:select property="consumoTarifaId">
										<html:option value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>
										<logic:present name="colecaoConsumoTarifa">
											<html:options collection="colecaoConsumoTarifa" labelProperty="descricao" property="id"/>
										</logic:present>
									</html:select>
							  	</td>
					      	</tr>
				      	</logic:present>
				      	<logic:notPresent name="exibirConsumoTarifa" scope="session">
							<html:hidden property="consumoTarifaId"/>				      	
				      	</logic:notPresent>
				      	
				      	<tr>
							<logic:present name="permitirAlterarClienteConta">
							  	<td height="10"><strong>Cliente Responsável da Conta:</strong></td>
								<td>
									<html:text property="idCliente" maxlength="9" size="9"
										onkeypress="javascript:validaEnter(event, 'exibirRetificarContaAction.do', 'idCliente');"/>
									
									<a href="javascript:abrirPopup('exibirPesquisarClienteAction.do?limparForm=OK', 400, 800);">
									<img border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" title="Pesquisar Cliente" /></a> 
									
									<logic:present name="codigoClienteNaoEncontrado" scope="request">
										<html:text property="nomeCliente" size="40" style="background-color:#EFEFEF; border:0; color: #ff0000" readonly="true"/>
									</logic:present> 
									
									<logic:notPresent name="codigoClienteNaoEncontrado" scope="request">
										<html:text property="nomeCliente" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
									</logic:notPresent> <a href="javascript:limparCliente();document.forms[0].idCliente.focus();">
									
									<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" /></a>
								</td>
								
							</logic:present>
								
							<logic:notPresent name="permitirAlterarClienteConta">
						  		<html:hidden property="idCliente" value=""/>	
							</logic:notPresent>
						</tr>

						</table>

					</td>
				</tr>
				</table>

		  </td>
      </tr>
      <tr>
      	<td colspan="4" height="10"></td>
      </tr>
      <tr> 
          <td colspan="4">
				
				<table width="100%" align="center" bgcolor="#99CCFF">
				<tr>
					<td><strong>Dados de Água:</strong></td>
				</tr>
				<tr bgcolor="#cbe5fe">
					<td width="100%" align="center">
			
						<table width="100%" border="0">
						<tr> 
							<td height="10" width="140"><strong>Situação de Água:</strong></td>
							<td>
								<html:select property="situacaoAguaConta" style="width: 190px;" tabindex="5" 								
									onchange="verificarIndicadorFaturavelLigacaoAguaSituacao(this.value, document.forms[0].consumoFaturadoAgua);">
									
									<logic:present name="colecaoSituacaoLigacaoAgua">
										
											<option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</option>
															    	
											<logic:iterate name="colecaoSituacaoLigacaoAgua" id="ligacaoAguaSituacao" type="LigacaoAguaSituacao">
													      				
												<logic:equal name="RetificarContaActionForm" property="situacaoAguaConta" value="<%="" + ligacaoAguaSituacao.getId() %>">
													<option SELECTED value="<%="" + ligacaoAguaSituacao.getId() %>" id="<%="" + ligacaoAguaSituacao.getIndicadorFaturamentoSituacao() %>" name="<%="" + ligacaoAguaSituacao.getConsumoMinimoFaturamento() %>"><%="" + ligacaoAguaSituacao.getDescricao() %></option>
												</logic:equal>
													      				
												<logic:notEqual name="RetificarContaActionForm" property="situacaoAguaConta" value="<%="" + ligacaoAguaSituacao.getId() %>">
													<option value="<%="" + ligacaoAguaSituacao.getId() %>" id="<%="" + ligacaoAguaSituacao.getIndicadorFaturamentoSituacao() %>" name="<%="" + ligacaoAguaSituacao.getConsumoMinimoFaturamento() %>"><%="" + ligacaoAguaSituacao.getDescricao() %></option>
												</logic:notEqual>
													      			
											</logic:iterate>
											
									</logic:present>
									
								</html:select>
							</td>
							<td colspan="2"></td>
						</tr>
						<tr> 
							<td height="10"><strong>Consumo de Água:</strong></td>
							
							<logic:notEqual name="indicadorDuplicarConsumoAguaParaConsumoEsgoto" value="<%="" + ConstantesSistema.SIM%>">
								<td>
									<html:text property="consumoAgua" size="10" maxlength="6" tabindex="6" style="text-align: right;"/>
								</td>
							</logic:notEqual>
							
							<logic:equal name="indicadorDuplicarConsumoAguaParaConsumoEsgoto" value="<%="" + ConstantesSistema.SIM%>">
								<td>
									<html:text property="consumoAgua" size="10" maxlength="6" tabindex="6" style="text-align: right;" onkeyup="preencherConsumoEsgoto();"/>
								</td>
							</logic:equal>

							<td><strong>Valor de Água:</strong></td>
							<td>
								<html:text property="valorAgua" size="18" readonly="true" style="background-color:#EFEFEF; border:0; text-align: right;"/>
							</td>
						</tr>
						</table>

					</td>
				</tr>
				</table>

		  </td>
      </tr>
      <tr>
      	<td colspan="4" height="10"></td>
      </tr>
      <tr> 
          <td colspan="4">
				
				<table width="100%" align="center" bgcolor="#99CCFF" border="0">
				<tr>
					<td><strong>Dados da Leitura Água:</strong></td>
				</tr>
				<tr bgcolor="#cbe5fe">
					<td width="100%" align="center">
			
						<table width="100%" border="0">
						<tr> 
							<td height="10"><strong>Mês e Ano da Leitura:</strong></td>
          					<td>
          						<html:text property="mesAnoMedicaoHistoricoAgua" size="8" readonly="true" style="background-color:#EFEFEF; border:0"/>
          					</td>
						</tr>
						<tr>
							<td height="10"><strong>Data Leitura Ant.:</strong></td>
				          	<logic:present name="isPermitidoAlterarDataLeituraAnterior" scope="request">
				          		<logic:equal name="isPermitidoAlterarDataLeituraAnterior" value="1" scope="request">
						          	<td>
										<html:text property="dataLeituraAnteriorMedicaoHistoricoAgua" size="11" maxlength="10" tabindex="4" onkeyup="mascaraData(this, event);"/>
										<a href="javascript:abrirCalendario('RetificarContaActionForm', 'dataLeituraAnteriorMedicaoHistoricoAgua')">
						                 <img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário"/></a>				                 
						                dd/mm/aaaa
								  	</td>
						  		</logic:equal>
						  		<logic:notEqual name="isPermitidoAlterarDataLeituraAnterior" value="1" scope="request">
						          	<td>
										<html:text property="dataLeituraAnteriorMedicaoHistoricoAgua" size="11" maxlength="10" tabindex="4" readonly="true" style="background-color:#EFEFEF; border:0"/>
									</td>
						  		</logic:notEqual>
						  	</logic:present>
						  	<logic:notPresent name="isPermitidoAlterarDataLeituraAnterior" scope="request">
						  		<td>
									<html:text property="dataLeituraAnteriorMedicaoHistoricoAgua" size="11" maxlength="10" tabindex="4" readonly="true" style="background-color:#EFEFEF; border:0"/>
								</td>
						  	</logic:notPresent>
						  	
						  	<logic:present name="exibirCampoLeituraAnteriorRetificacao">
						  	<td height="10"><strong>Leitura Anterior:</strong></td>
							<td>
								<html:text property="numeroLeituraAnteriorMedicaoHistoricoAgua" disabled="true" size="10" maxlength="6" tabindex="6" style="background-color:#EFEFEF; border:0; text-align: right;"/>
							</td>
							</logic:present>
							
							<logic:notPresent name="exibirCampoLeituraAnteriorRetificacao">
						  	<td height="10"><strong>Leitura Anterior:</strong></td>
							<td>
								<html:text property="numeroLeituraAnteriorMedicaoHistoricoAgua" size="10" maxlength="6" tabindex="6" style="text-align: right;"/>
							</td>
							</logic:notPresent>
						</tr>
						<tr> 							
							<td height="10"><strong>Data Leitura Fat.:</strong></td>
				          	<td>
								<html:text property="dataLeituraAtualMedicaoHistoricoAgua" size="11" maxlength="10" tabindex="4" onkeyup="mascaraData(this, event);"/>
								<a href="javascript:abrirCalendario('RetificarContaActionForm', 'dataLeituraAtualMedicaoHistoricoAgua')">
				                 <img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário"/></a>				                 
				                dd/mm/aaaa
						  	</td>
						  	
						  	<logic:present name="campoLeituraFaturamentoBloqueado">
						  	<td height="10"><strong>Leitura Fat.:</strong></td>
							<td>
								<html:text property="numeroLeituraAtualMedicaoHistoricoAgua" size="10" maxlength="6" tabindex="6" style="background-color:#EFEFEF; border:0; text-align: right;"/>
							</td>
							</logic:present>
							
							<logic:notPresent name="campoLeituraFaturamentoBloqueado">
						  	<td height="10"><strong>Leitura Fat.:</strong></td>
							<td>
								<html:text property="numeroLeituraAtualMedicaoHistoricoAgua" size="10" maxlength="6" tabindex="6" style="text-align: right;"/>
							</td>
							</logic:notPresent>
							
						</tr>						
						<tr> 
				          	<td height="10" colspan="1"><strong>Anormalidade Fat.:</strong></td>
				          	<td colspan="3">
								<html:select property="leituraAnormalidadeFaturamentoMedicaoHistoricoIdAgua" style="width: 400px;" tabindex="3">
									<html:option value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>
									<logic:present name="colecaoAnormalidadeLeitura">
										<html:options collection="colecaoAnormalidadeLeitura" labelProperty="descricao" property="id"/>
									</logic:present>
								</html:select>
						  	</td>
				      	</tr>								
						</table>

					</td>
				</tr>
				</table>

		  </td>
      </tr>
      <tr>
      	<td colspan="4" height="10"></td>
      </tr>
	  <tr> 
          <td colspan="4">
				
				<table width="100%" align="center" bgcolor="#99CCFF">
				<tr>
					<td><strong>Dados de Esgoto:</strong></td>
				</tr>
				<tr bgcolor="#cbe5fe">
					<td width="100%" align="center">
			
						<table width="100%" border="0">
						<tr> 
							<td height="10" width="140"><strong>Situação de Esgoto:</strong></td>
							<td>
								<html:select property="situacaoEsgotoConta" style="width: 190px;" tabindex="7" onchange="verificarIndicadorFaturavel(this.value, document.forms[0].consumoEsgoto, document.forms[0].percentualEsgoto);">
									
									<logic:present name="colecaoSituacaoLigacaoEsgoto">
										
											<option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</option>
															    	
											<logic:iterate name="colecaoSituacaoLigacaoEsgoto" id="ligacaoEsgotoSituacao" type="LigacaoEsgotoSituacao">
													      				
												<logic:equal name="RetificarContaActionForm" property="situacaoEsgotoConta" value="<%="" + ligacaoEsgotoSituacao.getId() %>">
													<option SELECTED value="<%="" + ligacaoEsgotoSituacao.getId() %>" id="<%="" + ligacaoEsgotoSituacao.getIndicadorFaturamentoSituacao() %>" name="<%="" + ligacaoEsgotoSituacao.getVolumeMinimoFaturamento() %>"><%="" + ligacaoEsgotoSituacao.getDescricao() %></option>
												</logic:equal>
													      			
												<logic:notEqual name="RetificarContaActionForm" property="situacaoEsgotoConta" value="<%="" + ligacaoEsgotoSituacao.getId() %>">
													<option value="<%="" + ligacaoEsgotoSituacao.getId() %>" id="<%="" + ligacaoEsgotoSituacao.getIndicadorFaturamentoSituacao() %>" name="<%="" + ligacaoEsgotoSituacao.getVolumeMinimoFaturamento() %>"><%="" + ligacaoEsgotoSituacao.getDescricao() %></option>
												</logic:notEqual>
													      				
											</logic:iterate>
											
									</logic:present>
									
								</html:select>
							</td>
							<td colspan="2"></td>
						</tr>
						<tr> 
		      				<td height="10" width="140">
		      					<strong>Perfil da Ligação de Esgoto:</strong>
		      				</td>
          					  	<td>
			     				<html:select property="ligacaoEsgotoPerfilId" style="width: 230px;"
			     					onchange="carregarPercentualPerfil(this.value);">
							       <logic:present name="colecaoLigacaoEsgotoPerfil">
								   		
							      		<html:option value="0">&nbsp;</html:option>
						      			<html:options collection="colecaoLigacaoEsgotoPerfil" labelProperty="descricao" property="percentualEsgotoConsumidaColetadaFormatado"></html:options>
							      	</logic:present>
  								    </html:select>
							</td>
							<td colspan="2"></td>
						</tr>
						<tr> 
							<td height="10"><strong>Consumo de Esgoto:</strong></td>
							<td>
								<html:text property="consumoEsgoto" size="10" maxlength="6" tabindex="8" style="text-align: right;"/>
							</td>
							<td><strong>Valor de Esgoto:</strong></td>
							<td>
								<html:text property="valorEsgoto" size="18" readonly="true" style="background-color:#EFEFEF; border:0; text-align: right;"/>
							</td>
						</tr>
						<tr> 
							<td height="10"><strong>Percentual de Esgoto:</strong></td>
							<td>
								<logic:equal name="RetificarContaActionForm" property="indicadorPermissaoAlterarPercentualEsgoto" value="<%="" + ConstantesSistema.SIM %>">
									<html:text property="percentualEsgoto" size="10" onkeyup="formataValorMonetario(this, 5);"/>%
								</logic:equal>
								<logic:notEqual name="RetificarContaActionForm" property="indicadorPermissaoAlterarPercentualEsgoto" value="<%="" + ConstantesSistema.SIM %>">
									<html:text property="percentualEsgoto" size="10" onkeyup="formataValorMonetario(this, 5);" readonly="true"/>%
								</logic:notEqual>
							</td>

							<td height="10"><strong>Fixo de Esgoto:</strong></td>
							<td>
								<html:text property="fixoEsgoto" readonly="true" size="10" style="background-color:#EFEFEF; border:0; text-align: right;"/>
							</td>
							<td colspan="2"></td>
						</tr>
						<tr>
							<td colspan="2"></td>
						</tr>
						
						</table>
						
					

					</td>
				</tr>
				</table>

		  </td>
      </tr>
      <tr>
      	<td colspan="4" height="5"></td>
      </tr>
      
      <tr> 
          <td colspan="4">
				
				<table width="100%" align="center" bgcolor="#99CCFF" border="0">
				<tr>
					<td><strong>Dados da Leitura de Esgoto:</strong></td>
				</tr>
				<tr bgcolor="#cbe5fe">
					<td width="100%" align="center">
			
						<table width="100%" border="0">
						<tr> 
							<td width="145" colspan="1"><strong>Mês e Ano da Leitura:</strong></td>
          					<td colspan="3"><html:text property="mesAnoMedicaoHistoricoEsgoto" size="8" readonly="true" style="background-color:#EFEFEF; border:0"/></td>
						</tr>
						<tr>
							<logic:present name="exibirCampoLeituraAnteriorRetificacao">
						  	<td height="10"><strong>Leitura Anterior:</strong></td>
							<td>
								<html:text property="numeroLeituraAnteriorMedicaoHistoricoEsgoto" disabled="true" size="10" maxlength="6" tabindex="6" style="background-color:#EFEFEF; border:0; text-align: right;"/>
							</td>
							</logic:present>
							
							<logic:notPresent name="exibirCampoLeituraAnteriorRetificacao">
						  	<td height="10"><strong>Leitura Anterior:</strong></td>
							<td>
								<html:text property="numeroLeituraAnteriorMedicaoHistoricoEsgoto" size="10" maxlength="6" tabindex="6" style="text-align: right;"/>
							</td>
							</logic:notPresent>
						</tr>
						<tr> 							
							<td height="10"><strong>Data Leitura Fat.:</strong></td>
				          	<td>
								<html:text property="dataLeituraAtualMedicaoHistoricoEsgoto" size="11" maxlength="10" tabindex="4" onkeyup="mascaraData(this, event);"/>
								<a href="javascript:abrirCalendario('RetificarContaActionForm', 'dataLeituraAtualMedicaoHistoricoEsgoto')">
				                 <img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário"/></a>				                 
				                dd/mm/aaaa
						  	</td>
						  	<td height="10"><strong>Leitura Fat.:</strong></td>
							<td>
								<html:text property="numeroLeituraAtualMedicaoHistoricoEsgoto" size="10" maxlength="6" tabindex="6" style="text-align: right;"/>
							</td>
						</tr>						
						<tr> 
				          	<td height="10" colspan="1"><strong>Anormalidade Fat.:</strong></td>
				          	<td colspan="3">
								<html:select property="leituraAnormalidadeFaturamentoMedicaoHistoricoIdEsgoto" style="width: 400px;" tabindex="3">
									<html:option value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>
									<logic:present name="colecaoAnormalidadeLeitura">
										<html:options collection="colecaoAnormalidadeLeitura" labelProperty="descricao" property="id"/>
									</logic:present>
								</html:select>
						  	</td>
				      	</tr>								
						</table>

					</td>
				</tr>
				</table>

		  </td>
      </tr>
      <tr>
      	<td colspan="4" height="10"></td>
      </tr>
      <tr>
      	<td colspan="4">
      		<table width="100%" border="0">
			<tr> 
				
				<td height="10" width="140"><strong>Valor dos Débitos:</strong></td>
		        <td width="160">
					<html:text property="valorDebito" size="21" readonly="true" style="background-color:#EFEFEF; border:0; text-align: right;"/>
		        </td>
		        <td colspan="2">
		    		<table width="100%" border="0">
					<tr> 
						<td height="10" width="140"><strong>Valor dos Créditos:</strong></td>
		        		<td>
							<html:text property="valorCredito" size="21" readonly="true" style="background-color:#EFEFEF; border:0; text-align: right;"/>
		        		</td>
		        	</tr>
		        	</table>
		        </td>
			</tr>
			<tr>
				<td height="10"><strong>Valor Total da Conta:</strong></td>
		        <td>
					<html:text property="valorTotal" size="21" readonly="true" style="background-color:#EFEFEF; border:0; text-align: right;"/>
		        </td>
		        <td colspan="2" align="right"></td>
			</tr>
			</table>
      	</td>
      </tr>
      <tr> 
          <td height="10" width="100"></td>
          <td colspan="3"><strong><font color="#FF0000">*</font></strong> 
                    Campos obrigat&oacute;rios
		  </td>
      </tr>
      
      <%-- Colocado por Raphael Rossiter em 17/04/2007
			Objetivo: Manipulação dos objetos que serão exibidos no formulário de acordo com a empresa --%>
								
	  <%-- ================================================================================= --%>
				
				<%int cont = 0;%>

				<logic:present name="colecaoCategoria">
				
					<tr>
						<td colspan="4" height="5"></td>
					</tr>
					<tr>
						<td colspan="4">
							
							<table width="100%" border="0">
							<tr>
								<td height="17" colspan="3"><strong>Categorias e Economias:</strong></td>
								<td align="right"><input type="button" class="bottonRightCol"
									value="Adicionar" tabindex="11" style="width: 80px"
									onclick="javascript:abrirPopup('exibirAdicionarCategoriaContaAction.do', 295, 450); "></td>
							</tr>

							<tr>
								<td colspan="4">

								<table width="100%" cellpadding="0" cellspacing="0">
									<tr>
										<td>

										<table width="100%" bgcolor="#99CCFF">
											<tr bgcolor="#99CCFF">

												<td align="center" width="10%"><strong>Remover</strong></td>
												<td width="60%">
												<div align="center"><strong>Categoria</strong></div>
												</td>
												<td width="30%">
												<div align="center"><strong>Quantidade de Economias</strong></div>
												</td>

											</tr>
										</table>

										</td>
									</tr>

									<tr>
										<td>

										<div style="width: 100%; height: 100; overflow: auto;">
										<%cont = 0;
										if(session.getAttribute("existeColecao") != null){
				   						%>
											<input type="hidden" name="existeColecao" value="1">
										<%}
				   						else{
										%>
											<input type="hidden" name="existeColecao" value="0">
										<%}%>

										<table width="100%" align="center" bgcolor="#99CCFF">

											<logic:iterate name="colecaoCategoria" id="categoria"
												type="Categoria">



												<%cont = cont + 1;
										if (cont % 2 == 0) {%>
										<tr bgcolor="#cbe5fe">
											<%} else {%>
										<tr bgcolor="#FFFFFF">
											<%}%>

											<td align="center" width="10%" valign="middle">
												<a href="javascript:removerCategoria(<%="" + categoria.getId().intValue()%>)">
													<img src="<bean:message key='caminho.imagens'/>Error.gif" border="0" >
												</a>
											</td>
											<td width="60%"><bean:write name="categoria"
												property="descricao" /></td>
											<td width="30%">
											<div align="center"><INPUT TYPE="text"
												NAME="categoria<%="" + categoria.getId().intValue()%>"
												size="6" id="categoria" maxlength="4"
												value="<%="" + categoria.getQuantidadeEconomiasCategoria()%>"
												style="text-align: right;" /></div>
											</td>
										</tr>

											</logic:iterate>
											</table>
											</div>
											</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td colspan="4" align="right">
										<input type="button" class="bottonRightCol" value="Calcular" tabindex="10"
											onclick="validarForm(document.forms[0], false);" style="width: 80px">
									</td>
								</tr>
							</table>
										
							
						</td>
					</tr>
					<tr>
						<td colspan="4" height="5"></td>
					</tr>
					<tr>
						<td colspan="4">
							<table width="100%" border="0">
								<tr>
									<td height="17" colspan="3"><strong>Débitos Cobrados:</strong></td>
									<td align="right">
										<logic:present name="colecaoCategoria">
											<input type="button" class="bottonRightCol" value="Adicionar"
												tabindex="11" style="width: 80px" onclick="concatenarParametro();">
										</logic:present>
										<logic:notPresent name="colecaoCategoria">
											<input type="button" class="bottonRightCol" value="Adicionar"
												tabindex="11" style="width: 80px" onclick="alert('É necessário informar o imóvel');" />
										</logic:notPresent>
										<input type="button" class="bottonRightCol" value="Remover"
												tabindex="11" style="width: 80px" onclick="removerDebitoCobrado(document.forms[0]);">
										</td>
								</tr>
								<tr>
									<td colspan="4">
										<table width="100%" cellpadding="0" cellspacing="0">
										<%int check1=1;%>
											<tr>
												<td>
													<table width="100%" bgcolor="#99CCFF">
														<tr bgcolor="#99CCFF">
															<td align="center" width="7%">
																<div align="center"><strong><a
																href="javascript:marcarDesmarcarTodos(<%=check1%>);" id="0">Todos</a></strong></div>
															</td>
															<td align="center" width="30%"><strong>Tipo do Débito</strong></td>
															<td align="center" width="20%"><strong>Mês/Ano do Débito</strong></td>
															<td align="center" width="20%"><strong>Mês/Ano da Cobrança</strong></td>
															<td align="center" width="20%"><strong>Valor do Débito</strong></td>
														</tr>
													</table>
												</td>
											</tr>
											<logic:present name="colecaoDebitoCobrado">
											<tr>
												<td>
													<div style="width: 100%; height: 100; overflow: auto;">
													<table width="100%" align="center" bgcolor="#99CCFF">
													<logic:iterate name="colecaoDebitoCobrado"
														id="debitoCobrado" type="DebitoCobrado">
														<%cont = cont + 1;
														if (cont % 2 == 0) {%>
														<tr bgcolor="#cbe5fe">
														<%} else {%>
														<tr bgcolor="#FFFFFF">
														<%}%>
														
														
														<td align="center" width="10%" valign="middle"><INPUT
															TYPE="checkbox" id="<%=check1%>"
															value="<%="" + GcomAction.obterTimestampIdObjeto(debitoCobrado)%>"></td>
														
														<td width="30%">
															<bean:write name="debitoCobrado" property="debitoTipo.descricao" />
														</td>
															<td align="center" width="20%">
																<logic:present name="debitoCobrado" property="anoMesReferenciaDebito">
																	<%=""+ Util.formatarMesAnoReferencia(debitoCobrado
																		.getAnoMesReferenciaDebito())%>
																</logic:present>
																<logic:notPresent name="debitoCobrado" property="anoMesReferenciaDebito">
																	&nbsp;
																</logic:notPresent>
															</td>
															<td align="center" width="20%">
																<logic:present name="debitoCobrado" property="anoMesCobrancaDebito">
																	<%=""+ Util.formatarMesAnoReferencia(debitoCobrado
																		.getAnoMesCobrancaDebito())%>
																</logic:present>
																<logic:notPresent name="debitoCobrado" property="anoMesCobrancaDebito">
																	&nbsp;
																</logic:notPresent>
															</td>
															<td align="center" width="20%">
																<input type="text"  name="debitoCobrado<%="" + GcomAction.obterTimestampIdObjeto(debitoCobrado) %>"
																	size="14" maxlength="14" id="debito" value="<%="" + Util.formatarMoedaReal(debitoCobrado.getValorPrestacao())%>"
																	style="text-align: right;" onkeyup="formataValorMonetario(this, 11)" />
															</td>
														</tr>
													</logic:iterate>
													</table>
													</div>
												</td>
											</tr>
											</logic:present>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
			      		<td colspan="4" height="5"></td>
			       	</tr>
			       	<tr>
			      		<td colspan="4">
			      	
			      		<table width="100%" border="0">
				  		<tr> 
			          		<td height="17" colspan="3"><strong>Créditos Realizados:</strong></td>
			          		<td align="right">
			          		
			          		<logic:present name="colecaoCategoria">
			          			<input type="button" class="bottonRightCol" value="Adicionar" tabindex="11" style="width: 80px" onclick="concatenarParametroCredito();">
			      			</logic:present>
			      			
			      			<logic:notPresent name="colecaoCategoria">
			          			<input type="button" class="bottonRightCol" value="Adicionar" tabindex="11" style="width: 80px" onclick="alert('É necessário informar o imóvel');">
			      			</logic:notPresent>
			      			<input type="button" class="bottonRightCol" value="Remover"
												tabindex="11" style="width: 80px" onclick="removerCreditoRealizado(document.forms[0]);">
			      			</td>
			      		</tr>
			      		
			      		
			      		<tr> 
			          		<td colspan="4">
					  
								<table width="100%" cellpadding="0" cellspacing="0">
								<tr> 
			                		<td> 
								<%int check2=2;%>
										<table width="100%" bgcolor="#99CCFF">
			                    		<tr bgcolor="#99CCFF"> 
			
											<td align="center" width="7%">
												<div align="center"><strong><a
												href="javascript:marcarDesmarcarTodos(<%=check2%>);" id="2">Todos</a></strong></div>
											</td>
											<td width="30%"><div align="center"><strong>Tipo do Crédito</strong></div></td>
											<td width="15%"><div align="center"><strong>Mês/Ano do Crédito</strong></div></td>
											<td width="15%"><div align="center"><strong>Mês/Ano da Cobrança</strong></div></td>
											<td width="20%"><div align="center"><strong>Valor do Crédito</strong></div></td>
								
										</tr>
			                    		</table>
								
									</td>
			            		</tr>
			            		
			            		<logic:present name="colecaoCreditoRealizado">
			            
			            		<tr> 
									<td> 
								
										<div style="width: 100%; height: 100; overflow: auto;">
										
										<%String cor = "#cbe5fe";%>
										
										<table width="100%" align="center" bgcolor="#99CCFF">	
			
											<logic:iterate name="colecaoCreditoRealizado" id="creditoRealizado" type="CreditoRealizado">
			                            
												
												<%	if (cor.equalsIgnoreCase("#cbe5fe")){
													cor = "#FFFFFF";%>
													<tr bgcolor="#FFFFFF">
												<%} else{
													cor = "#cbe5fe";%>
													<tr bgcolor="#cbe5fe">
												<%}%>
												 
												 <td align="center" width="10%" valign="middle"><INPUT
															TYPE="checkbox" id="<%=check2%>"
															value="<%="" + GcomAction.obterTimestampIdObjeto(creditoRealizado)%>"></td>
												
													<td width="30%" title="<bean:write name="creditoRealizado" property="hint"/>" style="cursor: pointer;">
													
														<bean:write name="creditoRealizado" property="creditoTipo.descricao"/>
														
													</td>
													<td width="20%">
													<div align="center">
			
														<logic:present name="creditoRealizado" property="anoMesReferenciaCredito">	
															<%=""+ Util.formatarMesAnoReferencia(creditoRealizado.getAnoMesReferenciaCredito())%>
														</logic:present>
													
														<logic:notPresent name="creditoRealizado" property="anoMesReferenciaCredito">
															&nbsp;
														</logic:notPresent>
													
													</div>
													</td>
													<td width="20%">
													<div align="center">
													
														<logic:present name="creditoRealizado" property="anoMesCobrancaCredito">
															<%=""+ Util.formatarMesAnoReferencia(creditoRealizado.getAnoMesCobrancaCredito())%>
														</logic:present>
													
														<logic:notPresent name="creditoRealizado" property="anoMesCobrancaCredito">
															&nbsp;
														</logic:notPresent>
														
													</div>
													</td>
													<td width="20%">
													<div align="center">
			
														<INPUT TYPE="text" NAME="creditoRealizado<%="" + GcomAction.obterTimestampIdObjeto(creditoRealizado)%>" size="14"
															 maxlength="14" value="<%="" + Util.formatarMoedaReal(creditoRealizado.getValorCredito())%>" 
															 id="credito"  
														 	 onkeyup="formataValorMonetario(this, 11)" 
															 <% if(creditoRealizado.getPgtAssociado() != null && creditoRealizado.getPgtAssociado() 
															 			&& (creditoRealizado.getPagamentoHistorico() != null && creditoRealizado.getPagamentoHistorico().getId() != null)) { %>
															 	<%=" readonly='true' style='text-align: right; background-color:#EFEFEF; border:0' "%>
															 <% } else { %>
															  	 onblur="configurarCreditoDisponivel(<%="" + GcomAction.obterTimestampIdObjeto(creditoRealizado)%>, this)"
															 	 <%=" style='text-align: right;' "%>
															 <% } %>
															 />
														
													</div>
													</td>
												</tr>
												
			
											</logic:iterate>
											</table>
										</div>
									</td>
			            		</tr>
			            		
			            		</logic:present>
			            		
			            		</table>
							</td>
						</tr>
						
						</table>
							
			      		</td>
			      	</tr>
				
				</logic:present>
				
				<%-- ================================================================================= --%>
				<%-- ================================================================================= --%>
				
				
				
				<%-- Colocado por Raphael Rossiter em 17/04/2007
					Objetivo: Manipulação dos objetos que serão exibidos no formulário de acordo com a empresa --%>
								
				<%-- ================================================================================= --%>
				
				<logic:present name="colecaoSubcategoria">
				
					<tr>
						<td colspan="4" height="5"></td>
					</tr>
					<tr>
						<td colspan="4">

							
							<table width="100%" border="0">
							<tr> 
								<td class="style1"><strong>Subcategorias e Economias:</strong><strong><font color="#FF0000">*</font></strong></td>
								<td colspan="2" class="style1"><div align="right"> 
									<input type="button" class="bottonRightCol" value="Adicionar" tabindex="9" style="width: 80px" 
										onclick="javascript:abrirPopup('exibirAdicionarCategoriaContaAction.do', 295, 450);"></div>
								</td>
							</tr>
							<tr> 
								<td colspan="4">
									<table width="100%" cellpadding="0" cellspacing="0">
										
										<tr> 
											<td> 
												
												<div style="width: 100%; height=150; overflow: auto;">
					   						    <%
					   						    cont = 0;
					   						    if(session.getAttribute("existeColecao") != null){
					   						    %>
													<input type="hidden" name="existeColecao" value="1">
												<%}
					   						    else{
												%>
													<input type="hidden" name="existeColecao" value="0">
												<%}%>
				                                
				                                <logic:present name="colecaoSubcategoria">
				                                
				                                <% Integer idCategoria = null; %>
		        		                        
		        		                        <table width="100%" align="center" bgcolor="#99CCFF">
											    
											    <logic:iterate name="colecaoSubcategoria" id="subcategoria" type="Subcategoria">
											    
											    	<% if (idCategoria == null || 
											    		   idCategoria.intValue() != subcategoria.getCategoria().getId().intValue()){ %>
											    	
											    		<tr bgcolor="#79bbfd"> 
															<td colspan="3"><strong><bean:write name="subcategoria" property="categoria.descricao"/></strong></td>
														</tr>
											    	
											    		<tr bgcolor="#99CCFF"> 
															<td align="center" width="10%"><strong>Remover</strong></td>
															<td align="center" width="60%"><strong>Subcategoria</strong></td>
															<td align="center" width="30%"><strong>Quantidade de Economias</strong></td>
														</tr>
														
														<% idCategoria = subcategoria.getCategoria().getId(); 
															cont = 0; %>
													
													<%} %>
												
													<%cont = cont + 1;
													if (cont % 2 == 0) {%>
													<tr bgcolor="#cbe5fe">
													<%} else {%>
													<tr bgcolor="#FFFFFF">
													<%}%>
													    <td align="center" width="10%" valign="middle">
														   <a href="javascript:removerSubcategoria(<%="" + subcategoria.getId().intValue()%>)">
															  <img src="<bean:message key='caminho.imagens'/>Error.gif" border="0" >
														   </a>
														</td>
														<td width="60%">
															<bean:write name="subcategoria" property="descricao"/>
														</td>
														<td width="30%" align="center">
													      <input type="text" name="subcategoria<%="" + subcategoria.getId().intValue()%>" size="6" maxlength="4" id="subcategoria" value="<%="" + subcategoria.getQuantidadeEconomias() %>" style="text-align: right;"/>
														</td>
													 </tr>
												
												</logic:iterate>
												
												</table>
												
												</logic:present>
												
												</div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td colspan="4" align="right">
									<input type="button" class="bottonRightCol" value="Calcular" tabindex="10"
										onclick="validarForm(document.forms[0], false);" style="width: 80px">
								</td>
							</tr>
							</table>
							
						</td>
					</tr>
					<tr>
						<td colspan="4" height="5"></td>
					</tr>
					<tr>
						<td colspan="4">
							<table width="100%" border="0">
								<tr>
									<td height="17" colspan="3"><strong>Débitos Cobrados:</strong></td>
									<td align="right">
										
										<logic:present name="colecaoSubcategoria">
											<input type="button" class="bottonRightCol" value="Adicionar"
												tabindex="11" style="width: 80px" onclick="concatenarParametro();">
										</logic:present>
										<logic:notPresent name="colecaoSubcategoria">
											<input type="button" class="bottonRightCol" value="Adicionar"
												tabindex="11" style="width: 80px" onclick="alert('É necessário informar o imóvel');" />
										</logic:notPresent>
										<input type="button" class="bottonRightCol" value="Remover"
												tabindex="11" style="width: 80px" onclick="removerDebitoCobrado(document.forms[0]);">
									</td>
								</tr>
								<tr>
									<td colspan="4">
										<table width="100%" cellpadding="0" cellspacing="0">
										<%int check1=1;%>
											<tr>
												<td>
													<table width="100%" bgcolor="#99CCFF">
														<tr bgcolor="#99CCFF">
															<td align="center" width="7%">
																<div align="center"><strong><a
																href="javascript:marcarDesmarcarTodos(<%=check1%>);" id="0">Todos</a></strong></div>
															</td>
															<td align="center" width="30%"><strong>Tipo do Débito</strong></td>
															<td align="center" width="20%"><strong>Mês/Ano do Débito</strong></td>
															<td align="center" width="20%"><strong>Mês/Ano da Cobrança</strong></td>
															<td align="center" width="20%"><strong>Valor do Débito</strong></td>
														</tr>
													</table>
												</td>
											</tr>
											
											<logic:present name="colecaoDebitoCobrado">
											<tr>
												<td>
													<div style="width: 100%; height: 100; overflow: auto;"><%cont = 0;%>
													<table width="100%" align="center" bgcolor="#99CCFF">
													<logic:iterate name="colecaoDebitoCobrado"
														id="debitoCobrado" type="DebitoCobrado">
														<%cont = cont + 1;
														if (cont % 2 == 0) {%>
														<tr bgcolor="#cbe5fe">
														<%} else {%>
														<tr bgcolor="#FFFFFF">
														<%}%>
															<td align="center" width="10%" valign="middle"><INPUT
															TYPE="checkbox" id="<%=check1%>"
															value="<%="" + GcomAction.obterTimestampIdObjeto(debitoCobrado)%>"></td>
															<td width="30%">
																<bean:write name="debitoCobrado" property="debitoTipo.descricao" />
															</td>
															<td align="center" width="20%">
																<logic:present name="debitoCobrado" property="anoMesReferenciaDebito">
																	<%=""+ Util.formatarMesAnoReferencia(debitoCobrado
																		.getAnoMesReferenciaDebito())%>
																</logic:present>
																<logic:notPresent name="debitoCobrado" property="anoMesReferenciaDebito">
																	&nbsp;
																</logic:notPresent>
															</td>
															<td align="center" width="20%">
																<logic:present name="debitoCobrado" property="anoMesCobrancaDebito">
																	<%=""+ Util.formatarMesAnoReferencia(debitoCobrado
																		.getAnoMesCobrancaDebito())%>
																</logic:present>
																<logic:notPresent name="debitoCobrado" property="anoMesCobrancaDebito">
																	&nbsp;
																</logic:notPresent>
															</td>
															<td align="center" width="20%">
																<input type="text"  name="debitoCobrado<%="" + GcomAction.obterTimestampIdObjeto(debitoCobrado) %>"
																	size="14" maxlength="14" id="debito" value="<%="" + Util.formatarMoedaReal(debitoCobrado.getValorPrestacao())%>"
																	style="text-align: right;" onkeyup="formataValorMonetario(this, 11)" />
															</td>
														</tr>
													</logic:iterate>
													</table>
													</div>
												</td>
											</tr>
											</logic:present>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
			      		<td colspan="4" height="5"></td>
			       	</tr>
			       	<tr>
			      		<td colspan="4">
			      	
			      		<table width="100%" border="0">
				  		<tr> 
			          		<td height="17" colspan="3"><strong>Créditos Realizados:</strong></td>
			          		<td align="right">
			          		
			          		<logic:present name="colecaoSubcategoria">
			          			<input type="button" class="bottonRightCol" value="Adicionar" tabindex="11" style="width: 80px" onclick="concatenarParametroCredito();">
			      			</logic:present>
			      			
			      			<logic:notPresent name="colecaoSubcategoria">
			          			<input type="button" class="bottonRightCol" value="Adicionar" tabindex="11" style="width: 80px" onclick="alert('É necessário informar o imóvel');">
			      			</logic:notPresent>
			      			</td>
			      		</tr>
			      		
			      		
			      		<tr> 
			          		<td colspan="4">
					  
								<table width="100%" cellpadding="0" cellspacing="0">
								<%int check2=2;%>
								<tr> 
			                		<td> 
								
										<table width="100%" bgcolor="#99CCFF">
			                    		<tr bgcolor="#99CCFF"> 
			
											<td align="center" width="7%">
												<div align="center"><strong><a
												href="javascript:marcarDesmarcarTodos(<%=check2%>);" id="2">Todos</a></strong></div>
											</td>
											<td width="30%"><div align="center"><strong>Tipo do Crédito</strong></div></td>
											<td width="15%"><div align="center"><strong>Mês/Ano do Crédito</strong></div></td>
											<td width="15%"><div align="center"><strong>Mês/Ano da Cobrança</strong></div></td>
											<td width="20%"><div align="center"><strong>Valor do Crédito</strong></div></td>
								
										</tr>
			                    		</table>
								
									</td>
			            		</tr>
			            		
			            		<logic:present name="colecaoCreditoRealizado">
			            
			            		<tr> 
									<td> 
								
										<div style="width: 100%; height: 100; overflow: auto;">
										
										<%String cor = "#cbe5fe";%>
										
										<table width="100%" align="center" bgcolor="#99CCFF">	
			
											<logic:iterate name="colecaoCreditoRealizado" id="creditoRealizado" type="CreditoRealizado">
			                            
												
												<%	if (cor.equalsIgnoreCase("#cbe5fe")){
													cor = "#FFFFFF";%>
													<tr bgcolor="#FFFFFF">
												<%} else{
													cor = "#cbe5fe";%>
													<tr bgcolor="#cbe5fe">
												<%}%>
												 
													 <td align="center" width="10%" valign="middle"><INPUT
															TYPE="checkbox" id="<%=check2%>"
															value="<%="" + GcomAction.obterTimestampIdObjeto(creditoRealizado)%>"></td>
													<td width="30%">
													
														<bean:write name="creditoRealizado" property="creditoTipo.descricao"/>
														
													</td>
													<td width="20%">
													<div align="center">
			
														<logic:present name="creditoRealizado" property="anoMesReferenciaCredito">	
															<%=""+ Util.formatarMesAnoReferencia(creditoRealizado.getAnoMesReferenciaCredito())%>
														</logic:present>
													
														<logic:notPresent name="creditoRealizado" property="anoMesReferenciaCredito">
															&nbsp;
														</logic:notPresent>
													
													</div>
													</td>
													<td width="20%">
													<div align="center">
													
														<logic:present name="creditoRealizado" property="anoMesCobrancaCredito">
															<%=""+ Util.formatarMesAnoReferencia(creditoRealizado.getAnoMesCobrancaCredito())%>
														</logic:present>
													
														<logic:notPresent name="creditoRealizado" property="anoMesCobrancaCredito">
															&nbsp;
														</logic:notPresent>
														
													</div>
													</td>
													<td width="20%">
													<div align="center">
			
														<INPUT TYPE="text" NAME="creditoRealizado<%="" + GcomAction.obterTimestampIdObjeto(creditoRealizado)%>" size="14" maxlength="14" value="<%="" + Util.formatarMoedaReal(creditoRealizado.getValorCredito())%>" id="credito" style="text-align: right;" onkeyup="formataValorMonetario(this, 11);" onblur="configurarCreditoDisponivel(<%="" + GcomAction.obterTimestampIdObjeto(creditoRealizado)%>, this)"/>
														
													</div>
													</td>
												</tr>
												
			
											</logic:iterate>
											</table>
										</div>
									</td>
			            		</tr>
			            		
			            		</logic:present>
			            		
			            		</table>
							</td>
						</tr>
						
						</table>
							
			      		</td>
			      	</tr>
				
				</logic:present>
				
				<%-- ================================================================================= --%>
				<%-- ================================================================================= --%>
				
				
      
   		</table>
   
   		<table width="100%" border="0">
      	<tr>
      		<td colspan="3" height="5">
				<input name="Button" type="button" class="bottonRightCol" value="Desfazer" align="left"
				onclick="window.location.href='<html:rewrite page="/exibirRetificarContaAction.do?contaID=${sessionScope.contaID}"/>'">
			
				<input name="Button" type="button" class="bottonRightCol" value="Cancelar" align="left"
				onclick="window.location.href='<html:rewrite page="/telaPrincipal.do"/>'">
			
				<input type="button" class="bottonRightCol" value="Voltar" style="width: 80px" onclick="voltarTelaManter();">
			</td>
      		<td align="right" height="5">
	          	<logic:present name="indicadorOperacao" scope="session">
					<logic:equal name="indicadorOperacao" value="caucionar">
		      			<input type="button" class="bottonRightCol" value="Caucionar" style="width: 80px" onclick="validarForm(document.forms[0], true);">
					</logic:equal>
					<logic:notEqual name="indicadorOperacao" value="caucionar">
		      			<input type="button" class="bottonRightCol" value="Retificar" style="width: 80px" onclick="validarForm(document.forms[0], true);">
					</logic:notEqual>
				</logic:present>
	           	<logic:notPresent name="indicadorOperacao" scope="session">
	      			<input type="button" class="bottonRightCol" value="Retificar" style="width: 80px" onclick="validarForm(document.forms[0], true);">
				</logic:notPresent>
      		</td>
      	</tr>   
      	</table>
   
   		<p>&nbsp;</p>
	
	</td>
  </tr>
</table>

<%@ include file="/jsp/util/rodape.jsp"%>

</html:form>

<SCRIPT LANGUAGE="JavaScript">
	
	desabilitaMotivoCaucionamento();

	habilitacaoCamposEsgoto();
	 habilitacaoCamposAgua();

</SCRIPT>

</body>
<!-- retificar_conta.jsp -->
</html:html>

