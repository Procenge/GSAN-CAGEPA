<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<%@ page import="gcom.util.Util"%>
<%@ page
	import="gcom.cobranca.parcelamento.ParcelamentoQuantidadeReparcelamentoHelper"%>
<%@ page
	import="gcom.cobranca.parcelamento.ParcelamentoDescontoAntiguidade"%>
<%@ page
	import="gcom.cobranca.parcelamento.ParcelamentoDescontoInatividade"%>
<%@ page import="gcom.util.ConstantesSistema" isELIgnored="false"%>
<%@ page import="java.util.Collection" isELIgnored="false"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
	
<style type="text/css">

.desabilitar {
 background-color:#EFEFEF;
 border:0;
 color: #000000;
}

</style>
	
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="AtualizarParcelamentoPerfilActionForm" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>jquery-1.4.2.min.js"></script>	


<script language="JavaScript">

	//function numero(value) {
	// if (value == null || !value.toString().match(/^[-]?d*.?d*$/)) return false;
	// return true;
	//}
	
	//$(document).ready(function() {
	
	//$("input:text[name=valorMinimoDebitoAParcelarFaixaDebito]").keypress(function() {
	  //var texto = $(this).val();
	  //if (!numero(texto)) {
		 //$(this).val(texto.substring(0, texto.length));
	  //}
	//});
	
	//$("input:text[name=valorMaximoDebitoAParcelarFaixaDebito]").keypress(function() {
	 // var texto = $(this).val();
	  //if (!numero(texto)) {
		// $(this).val(texto.substring(0, texto.length));
	  //}
	//});
	
	//});
	
	function validarIndicadorDebitoOriginalOuAtualizadoFaixaDebito(){
	    var selecionado = $.trim($("input:radio[name=indicadorDebitoOriginalOuAtualizadoFaixaDebito]:checked").val());
	
	    if(selecionado == null || selecionado == undefined || selecionado == 'undefined' || selecionado == ''){
			alert('Informe Op��o da Faixa de D�bito a Parcelar.');
			$("input:radio[name=indicadorDebitoOriginalOuAtualizadoFaixaDebito]:checked").focus();
			return false;
		}else{
			return true;
		}
	}
	
	function validarPrestacaoMinima(form){
	
		if (isBrancoOuNulo(form.percentualTarifaMinimaPrestacao.value) && isBrancoOuNulo(form.percentualValorDebitoCalculoValorMinimoPrestacao.value)
				&& isBrancoOuNulo(form.valorDebitoPrestacao.value)){
	
			alert('Informe:\nPercentual da Tarifa M�nima para C�lculo do Valor M�nimo da Presta��o ou\nPercentual do Valor do d�bito para C�lculo do Valor M�nimo da Presta��o ou\nValor M�nimo da Presta��o');
			return false;
		} else if (!(form.indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima[0].checked) && !(form.indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima[1].checked)) {
			alert('Informe Op��es da Presta��o M�nima');
			return false;
		}
	
		return true;
	}
	
	function controlarCampos() {
		var form = document.forms[0];
	var percentualTarifaMinimaPrestacaoObjeto = $("input:text[name=percentualTarifaMinimaPrestacao]");
	var percentualTarifaMinimaPrestacaoValor = $.trim(percentualTarifaMinimaPrestacaoObjeto.val());
	
	var percentualValorDebitoCalculoValorMinimoPrestacaoObjeto = $("input:text[name=percentualValorDebitoCalculoValorMinimoPrestacao]");
	var percentualValorDebitoCalculoValorMinimoPrestacaoValor = $.trim(percentualValorDebitoCalculoValorMinimoPrestacaoObjeto.val());
	
	var opcaoObjeto = $("input:radio[name=indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima]:checked");
	var opcaoValor = $.trim(opcaoObjeto.val());
	
	var valorDebitoPrestacaoObjeto = $("input:text[name=valorDebitoPrestacao]");
	var valorDebitoPrestacaoValor = $.trim(valorDebitoPrestacaoObjeto.val());
	
	if (isBrancoOuNulo(percentualTarifaMinimaPrestacaoValor) && isBrancoOuNulo(percentualValorDebitoCalculoValorMinimoPrestacaoValor)
			&& isBrancoOuNulo(opcaoValor) && isBrancoOuNulo(valorDebitoPrestacaoValor)) {
	
		//percentualTarifaMinimaPrestacaoObjeto.val('');
		form.percentualTarifaMinimaPrestacao.value = "";
		percentualTarifaMinimaPrestacaoObjeto.attr("disabled", false);
		
		//percentualValorDebitoCalculoValorMinimoPrestacaoObjeto.val('');
		form.percentualValorDebitoCalculoValorMinimoPrestacao.value = "";
		percentualValorDebitoCalculoValorMinimoPrestacaoObjeto.attr("disabled", false);
		
		opcaoObjeto.val('1');
		opcaoObjeto.attr("disabled", false);
		
		//valorDebitoPrestacaoObjeto.val('');
		form.valorDebitoPrestacao.value = "";
		valorDebitoPrestacaoObjeto.attr("disabled", false);
	}
	}

 function caracteresespeciais () { 
     this.aa = new Array("qtdeMaximaReparcelamento", "Reparcelamentos Consecutivos possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ab = new Array("quantidadeMinimaMesesDebito", "Qtde. M�nima Meses de D�bito p/ Desconto possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ac = new Array("quantidadeMaximaMesesInatividade", "Qtde. M�xima Meses de Inatividade da Lig. de �gua possui caracteres especiais.", new Function ("varName", " return this[varName];"));
  }

 function InteiroZeroPositivoValidations () {
     this.aa = new Array("qtdeMaximaReparcelamento", " Reparcelamentos Consecutivos deve somente conter n�meros positivos ou zero.", new Function ("varName", " return this[varName];"));
     this.ab = new Array("quantidadeMinimaMesesDebito", "Qtde. M�nima Meses de D�bito p/ Desconto deve somente conter n�meros positivos ou zero.", new Function ("varName", " return this[varName];"));
     this.ac = new Array("quantidadeMaximaMesesInatividade", "Qtde. M�xima Meses de Inatividade da Lig. de �gua deve somente conter n�meros positivos ou zero.", new Function ("varName", " return this[varName];"));
 }


 var bCancel = false; 
 function validaCaracterEspeciaisInteger(form) {                                                                   
			
      if (bCancel) {
      	return true; 
      } else {
       	return  validateCaracterEspecial(form) && validateInteiroZeroPositivo(form);
       	}
   	  
   }
 
	function validarForm(form){

		//if(validateAtualizarParcelamentoPerfilActionForm(form) && validarValor()){
		if (validateAtualizarParcelamentoPerfilActionForm(form) 
				&& validarIndicadorParcelarChequeDevolvido() 
				&& validarIndicadorParcelarSancoesMaisDeUmaConta() 
				&& validarValor() && validarPrestacaoMinima(form)
				&& validarCamposDescumprimentoPrestacoes()){

			if (<%=session.getAttribute("collectionParcelamentoQuantidadeReparcelamentoHelperVazia")%> == "1"){

				alert('Informe Reparcelamento Consecutivo.');
			}else{

				document.forms[0].target='';
				form.action = 'atualizarPerfilParcelamentoAction.do';
				submeterFormPadrao(form);
			}
		}
	}

	function validarValor() {
		var valorMinimoDebitoAParcelarFaixaDebito = $("input[name=valorMinimoDebitoAParcelarFaixaDebito]").val().replace(/[.,]/g,"");
		var valorMaximoDebitoAParcelarFaixaDebito = $("input[name=valorMaximoDebitoAParcelarFaixaDebito]").val().replace(/[.,]/g,"");

		if(valorMinimoDebitoAParcelarFaixaDebito == null || valorMinimoDebitoAParcelarFaixaDebito == undefined || valorMinimoDebitoAParcelarFaixaDebito == 'undefined' || $.trim(valorMinimoDebitoAParcelarFaixaDebito) == ""){
		   alert("Valor M�nimo de D�bito a Parcelar");
		   $("input[name=valorMinimoDebitoAParcelarFaixaDebito]").focus();
		   return false;
		}
		// else if (valorMinimoDebitoAParcelarFaixaDebito > 99999999999) {
			//alert("Valor M�nimo de D�bito a Parcelar n�o pode ser maior que 999.999.999,99");
			//return false;
		//}

		if(valorMaximoDebitoAParcelarFaixaDebito == null || valorMaximoDebitoAParcelarFaixaDebito == undefined || valorMaximoDebitoAParcelarFaixaDebito == 'undefined' || $.trim(valorMaximoDebitoAParcelarFaixaDebito) == ""){
		   alert("Valor M�ximo de D�bito a Parcelar");
		   $("input[name=valorMaximoDebitoAParcelarFaixaDebito]").focus();
		   return false;
		} 
		//else if (valorMaximoDebitoAParcelarFaixaDebito > 99999999999) {
			//alert("Valor M�ximo de D�bito a Parcelar n�o pode ser maior que 999.999.999,99");
			//return false;
		//}
		
		if (valorMinimoDebitoAParcelarFaixaDebito == valorMaximoDebitoAParcelarFaixaDebito) {
			alert("Valor M�nimo de D�bito a Parcelar e \nValor M�ximo de D�bito a Parcelar\nN�o podem ser iguais");
			return false;
		}

		// Depois ver isso
		//if (valorMinimoDebitoAParcelarFaixaDebito > valorMaximoDebitoAParcelarFaixaDebito) {
			//alert("Valor M�nimo de D�bito a Parcelar\nN�o podem ser maior que o\nValor M�ximo de D�bito a Parcelar");
			//return false;
		//}
		
		return true;
	}
   	
	//Testa se o campo foi digitado somente com zeros
	function testarValorZero(valor) {
		var retorno = true;
		var conteudo = valor.value.replace(",","");
		var conteudo = conteudo.replace(".","");
		
		if (trim(valor.value).length > 0){
			if (isNaN(valor.value)) {
				for (x= 0; x < conteudo.length; x++){
					if (conteudo.substr(x, 1) != "0"){
						retorno = true;
						break;
					}
					else{
						retorno = false;	
					}
				}
			}
			else {
				var intValorCampo = valor.value * 1;
				if (intValorCampo == 0) {
	        		retorno =  false;
				}
			}
		}
		return retorno;
	}

	function validarIndicadorParcelarSancoesMaisDeUmaConta(){

		var selecionado = $.trim($("input:radio[name=indicadorParcelarSancoesMaisDeUmaConta]:checked").val());

	    if(selecionado == null || selecionado == undefined || selecionado == 'undefined' || selecionado == ''){
	    	alert('Informe N�o parcelar com san��es em mais de uma conta.');
			$("input:radio[name=indicadorParcelarSancoesMaisDeUmaConta]:checked").focus();
			return false;
		}else{
			return true;
		}
	}


	function validarIndicadorParcelarChequeDevolvido(){
	    var selecionado = $.trim($("input:radio[name=indicadorParcelarChequeDevolvido]:checked").val());

	    if(selecionado == null || selecionado == undefined || selecionado == 'undefined' || selecionado == ''){
			alert('Informe N�o parcelar com situa��o de cobran�a.');
			$("input:radio[name=indicadorParcelarChequeDevolvido]:checked").focus();
			return false;
		}else{
			return true;
		}
	}
   
   function validaRequiredAdicionarParcelamentoDescontoAntiguidade () {
		var form = document.forms[0];
		var msg = '';
		
		if( form.quantidadeMinimaMesesDebito.value  == '' 
			|| form.percentualDescontoSemRestabelecimentoAntiguidade.value  == ''
			|| form.percentualDescontoComRestabelecimentoAntiguidade.value  == ''	
			|| form.percentualDescontoAtivo.value == '') {
		
			msg = msg + 'O preenchimento dos campos Qtde. M�nima Meses de D�bito p/ Desconto, Percentual de Desconto Sem Restabelecimento, Percentual de Desconto Com Restabelecimento e Percentual de Desconto Ativo � obrigat�rio.';
		
		}
		
		//if( form.quantidadeMinimaMesesDebito.value  == '' ) {
		//	msg = msg + 'Informe Qtde. M�nima Meses de D�bito p/ Desconto.\n';
		//}
		//if( form.percentualDescontoSemRestabelecimentoAntiguidade.value  == '' ) {
		//	msg = msg + 'Informe Percentual de Desconto Sem Restabelecimento.\n';
		//}
		//if( form.percentualDescontoComRestabelecimentoAntiguidade.value  == '' ) {
		//	msg = msg + 'Informe Percentual de Desconto Com Restabelecimento.\n';
		//}
		//if( form.percentualDescontoAtivo.value == '' ) {
		//	msg = msg + 'Informe Percentual de Desconto Ativo.';
		//}
		
		if( msg != '' ){
			alert(msg);
			return false;
		}else if (validaCaracterEspeciaisInteger(form)){
			return true;
		}
	}
	
	function validaCampoZeroAdicionarParcelamentoDescontoAntiguidade () {
		var form = document.forms[0];
		var msg = '';
// 		if( !testarValorZero(form.quantidadeMinimaMesesDebito)) {
// 			msg = msg + 'Qtde. M�nima Meses de D�bito p/ Desconto deve somente conter n�meros positivos.\n';
// 		}
// 		if( !testarValorZero(form.percentualDescontoSemRestabelecimentoAntiguidade)) {
// 			msg = msg + 'Percentual de Desconto Sem Restabelecimento deve somente conter n�meros decimais positivos.\n';
// 		}
// 		if( !testarValorZero(form.percentualDescontoComRestabelecimentoAntiguidade)) {
// 			msg = msg + 'Percentual de Desconto Com Restabelecimento deve somente conter n�meros decimais positivos.\n';
// 		}
// 		if( !testarValorZero(form.percentualDescontoAtivo)) {
// 			msg = msg + 'Percentual de Desconto Ativo deve somente conter n�meros decimais positivos.';
// 		}
		if( msg != '' ){
			alert(msg);
			return false;
		}else{
			return true;
		}
	}
   
   function adicionarParcelamentoDescontoAntiguidade (form){
   var PERCENTUAL_MAXIMO_ABATIMENTO = document.getElementById("PERCENTUAL_MAXIMO_ABATIMENTO").value;
   
	   if (validaRequiredAdicionarParcelamentoDescontoAntiguidade()){
			if(isNaN(form.quantidadeMinimaMesesDebito.value)){	
		 			alert('Qtde. M�nima Meses de D�bito p/ Desconto possui caracteres especiais.'); 
		 			form.quantidadeMinimaMesesDebito.focus();	
		 	}else if (validaCampoZeroAdicionarParcelamentoDescontoAntiguidade()){
				
				//if(parseFloat(form.percentualDescontoSemRestabelecimentoAntiguidade.value.replace(",", ".")) > parseFloat(PERCENTUAL_MAXIMO_ABATIMENTO.replace(",", "."))){
				//	alert('Percentual de Desconto Sem Restabelecimento � superior ao Percentual M�ximo de Desconto de ' + PERCENTUAL_MAXIMO_ABATIMENTO +  ' permitido para Financiamento' );
				//	form.percentualDescontoSemRestabelecimentoAntiguidade.focus();
				//}else if(parseFloat(form.percentualDescontoComRestabelecimentoAntiguidade.value.replace(",", ".")) > parseFloat(PERCENTUAL_MAXIMO_ABATIMENTO.replace(",", "."))){
				//	alert('Percentual de Desconto Com Restabelecimento � superior ao Percentual M�ximo de Desconto de ' + PERCENTUAL_MAXIMO_ABATIMENTO +  ' permitido para Financiamento' );
				//	form.percentualDescontoComRestabelecimentoAntiguidade.focus();
				//}else if(parseFloat(form.percentualDescontoAtivo.value.replace(",", ".")) > parseFloat(PERCENTUAL_MAXIMO_ABATIMENTO.replace(",", "."))){
				//	alert('Percentual de Desconto Ativo � superior ao Percentual M�ximo de Desconto de ' + PERCENTUAL_MAXIMO_ABATIMENTO +  ' permitido para Financiamento' );
				//	form.percentualDescontoAtivo.focus();
				//}else{ 		
					document.forms[0].target='';
					form.action = "exibirAtualizarPerfilParcelamentoAction.do?adicionarParcelamentoDescontoAntiguidade=S&reload=S";
			   		submeterFormPadrao(form);
		   		//}
		   		
			}
		}

   }
   
   function validaRequiredAdicionarParcelamentoDescontoInatividade () {
		var form = document.forms[0];
		var msg = '';
		
		if( form.quantidadeMaximaMesesInatividade.value  == '' 
			|| form.percentualDescontoSemRestabelecimentoInatividade.value  == ''
			|| form.percentualDescontoComRestabelecimentoInatividade.value  == '') {
		
			msg = msg + 'O preenchimento dos campos Qtde. M�xima Meses de Inatividade da Lig. de �gua, Percentual de Desconto Sem Restabelecimento, Percentual de Desconto Com Restabelecimento � obrigat�rio, caso algum deles seja informado.';
		
		}
		
		
		//if( form.quantidadeMaximaMesesInatividade.value  == '' ) {
		//	msg = msg + 'Informe Qtde. M�xima Meses de Inatividade da Lig. de �gua.\n';
		//}
		//if( form.percentualDescontoSemRestabelecimentoInatividade.value  == '' ) {
		//	msg = msg + 'Informe Percentual de Desconto Sem Restabelecimento.\n';
		//}
		//if( form.percentualDescontoComRestabelecimentoInatividade.value  == '' ) {
		//	msg = msg + 'Informe Percentual de Desconto Com Restabelecimento.';
		//}

		if( msg != '' ){
			alert(msg);
			return false;
		}else if (validaCaracterEspeciaisInteger(form)){
			return true;
		}
	}
	
	function validaCampoZeroAdicionarParcelamentoDescontoInatividade () {
		var form = document.forms[0];
		var msg = '';
// 		if( !testarValorZero(form.quantidadeMaximaMesesInatividade)) {
// 			msg = msg + 'Qtde. M�xima Meses de Inatividade da Lig. de �gua deve somente conter n�meros positivos.\n';
// 		}
// 		if( !testarValorZero(form.percentualDescontoSemRestabelecimentoInatividade)) {
// 			msg = msg + 'Percentual de Desconto Sem Restabelecimento deve somente conter n�meros decimais positivos.\n';
// 		}
// 		if( !testarValorZero(form.percentualDescontoComRestabelecimentoInatividade)) {
// 			msg = msg + 'Percentual de Desconto Com Restabelecimento deve somente conter n�meros decimais positivos.';
// 		}
		if( msg != '' ){
			alert(msg);
			return false;
		}else{
			return true;
		}
	}
   
   function adicionarParcelamentoDescontoInatividade (form){
   var PERCENTUAL_MAXIMO_ABATIMENTO = document.getElementById("PERCENTUAL_MAXIMO_ABATIMENTO").value;

	    if (validaRequiredAdicionarParcelamentoDescontoInatividade()){
			if(isNaN(form.quantidadeMaximaMesesInatividade.value)){	
	 			alert('Qtde. M�xima Meses de Inatividade da Lig. de �gua possui caracteres especiais.'); 
	 			form.quantidadeMaximaMesesInatividade.focus();	
	 		}else if (validaCampoZeroAdicionarParcelamentoDescontoInatividade()){
				document.forms[0].target='';	
			    form.action = "exibirAtualizarPerfilParcelamentoAction.do?adicionarParcelamentoDescontoInatividade=S&reload=S";
			    submeterFormPadrao(form);
			}
		}	
   }
   
	function verificaPercentualMaximoAbatimento(percentualDesconto){
	var PERCENTUAL_MAXIMO_ABATIMENTO = document.getElementById("PERCENTUAL_MAXIMO_ABATIMENTO").value;
	
		if(percentualDesconto.value!= "" && PERCENTUAL_MAXIMO_ABATIMENTO!= ""){

			if (testarCampoValorZero(percentualDesconto, ' Percentual de Desconto')){
				 if(parseFloat(percentualDesconto.value.replace(",", ".")) > parseFloat(PERCENTUAL_MAXIMO_ABATIMENTO.replace(",", "."))){
					alert('Percentual de Desconto � superior ao Percentual M�ximo de Desconto ' + PERCENTUAL_MAXIMO_ABATIMENTO +  ' permitido para Financiamento');
					percentualDesconto.focus();
	   			 }
			}			
		}
	}
	
	function abrirPopupComSubmitLink(form,altura, largura,qtdeMaxReparcelamento,readOnly){
		var height = window.screen.height - 160;
		var width = window.screen.width;
		var top = (height - altura)/2;
		var left = (width - largura)/2;
		window.open('', 'Pesquisar','top=' + top + ',left='+ left +',location=no,screenY=0,screenX=0,menubar=no,status=no,toolbar=no,scrollbars=yes,resizable=no,width=' + largura + ',height=' + altura);
		form.target='Pesquisar';
		if (readOnly){
			if (form.indicadorPermitirInformarNumeroValorParcelas[0].checked){
				
				form.action = 'exibirInserirPrestacoesParcelamentoPerfilAction.do?primeiraVez=S&qtdeMaximaReparcelamento='+ qtdeMaxReparcelamento + '&readOnly=true&adicionarReparcelamentosConsecutivos=true&desabilitarTaxaJuros=true';
			}else{
				
				form.action = 'exibirInserirPrestacoesParcelamentoPerfilAction.do?primeiraVez=S&qtdeMaximaReparcelamento='+ qtdeMaxReparcelamento + '&readOnly=true&adicionarReparcelamentosConsecutivos=true';
			}
		}else{
			
			if (form.indicadorPermitirInformarNumeroValorParcelas[0].checked){
				
				form.action = 'exibirInserirPrestacoesParcelamentoPerfilAction.do?primeiraVez=S&qtdeMaximaReparcelamento='+ qtdeMaxReparcelamento + '&adicionarReparcelamentosConsecutivos=true&desabilitarTaxaJuros=true' ;
			}else{
				
				form.action = 'exibirInserirPrestacoesParcelamentoPerfilAction.do?primeiraVez=S&qtdeMaximaReparcelamento='+ qtdeMaxReparcelamento + '&adicionarReparcelamentosConsecutivos=true' ;
			}
		}
		submeterFormPadrao(form);
	}

	function abrirPopupComSubmitBotao(form,altura,largura){
		var height = window.screen.height - 160;
		var width = window.screen.width;
		var top = (height - altura)/2;
		var left = (width - largura)/2;
		window.open('', 'Pesquisar','top=' + top + ',left='+ left +',location=no,screenY=0,screenX=0,menubar=no,status=no,toolbar=no,scrollbars=yes,resizable=no,width=' + largura + ',height=' + altura);
		form.target='Pesquisar';
		
		if (form.indicadorPermitirInformarNumeroValorParcelas[0].checked){
			
			form.action = 'exibirInserirPrestacoesParcelamentoPerfilAction.do?primeiraVez=S&adicionarReparcelamento=S&adicionarReparcelamentosConsecutivos=true&desabilitarTaxaJuros=true' ;
		}else{
			
			form.action = 'exibirInserirPrestacoesParcelamentoPerfilAction.do?primeiraVez=S&adicionarReparcelamento=S&adicionarReparcelamentosConsecutivos=true' ;
		}
		submeterFormPadrao(form);
	}
	
	
	
	function validaRequiredAdicionarReparcelamento () {
		var form = document.forms[0];
		var msg = '';
		if( form.qtdeMaximaReparcelamento.value  == '' ) {
			msg = 'Informe Qtde. M�xima Reparcelamentos Consecutivos.\n';
		}
		if( msg != '' ){
			alert(msg);
			return false;
		}else if (validaCaracterEspeciaisInteger(form)){
			return true;
		}
	}
	
	function adicionarReparcelamento (form){

		if (validaRequiredAdicionarReparcelamento()){
			if(isNaN(form.qtdeMaximaReparcelamento.value)){	
	 			alert('Qtde. M�xima Reparcelamentos Consecutivos possui caracteres especiais.');
	 			form.qtdeMaximaReparcelamento.focus();
			}else{
				abrirPopupComSubmitBotao(form, 665, 645);
			}
		}
	}

	function desabilitarCampo(form){
		if (isBrancoOuNulo(form.percentualTarifaMinimaPrestacao.value) && isBrancoOuNulo(form.percentualValorDebitoCalculoValorMinimoPrestacao.value)
				&& isBrancoOuNulo(form.valorDebitoPrestacao.value)){
			$("input[name='percentualTarifaMinimaPrestacao']").val("");
			$("input[name='percentualTarifaMinimaPrestacao']").removeAttr('readonly');
			$("input[name='percentualTarifaMinimaPrestacao']").removeAttr('class');
			
			$("input[name='percentualValorDebitoCalculoValorMinimoPrestacao']").val("");
			$("input[name='percentualValorDebitoCalculoValorMinimoPrestacao']").removeAttr("readonly");
			$("input[name='percentualValorDebitoCalculoValorMinimoPrestacao']").removeAttr('class'); 
			
			form.indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima[0].checked=true;
			form.indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima[0].disabled="";
			form.indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima[1].disabled="";
			
			$("input[name='valorDebitoPrestacao']").val("");
			$("input[name='valorDebitoPrestacao']").removeAttr("readonly");
			$("input[name='valorDebitoPrestacao']").removeAttr('class');
			
		} else if (!isBrancoOuNulo(form.percentualTarifaMinimaPrestacao.value)){
			$("input[name='percentualValorDebitoCalculoValorMinimoPrestacao']").val("");
			$("input[name='percentualValorDebitoCalculoValorMinimoPrestacao']").attr("readonly",true); 
			$("input[name='percentualValorDebitoCalculoValorMinimoPrestacao']").attr("class", 'desabilitar');
			
			form.indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima[0].checked=true;
			form.indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima[0].disabled="disabled";
			form.indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima[1].disabled="disabled";
			
			$("input[name='valorDebitoPrestacao']").val("");
			$("input[name='valorDebitoPrestacao']").attr("readonly",true);
			$("input[name='valorDebitoPrestacao']").attr("class", 'desabilitar');
			
		} else if (!isBrancoOuNulo(form.percentualValorDebitoCalculoValorMinimoPrestacao.value)){
			$("input[name='percentualTarifaMinimaPrestacao']").val("");
			$("input[name='percentualTarifaMinimaPrestacao']").attr('readonly', true);
			$("input[name='percentualTarifaMinimaPrestacao']").attr("class", 'desabilitar');
			
			$("input[name='valorDebitoPrestacao']").val("");
			$("input[name='valorDebitoPrestacao']").attr("readonly",true);
			$("input[name='valorDebitoPrestacao']").attr("class", 'desabilitar');
		} else if (!isBrancoOuNulo(form.valorDebitoPrestacao)){
			$("input[name='percentualTarifaMinimaPrestacao']").val("");
			$("input[name='percentualTarifaMinimaPrestacao']").attr('readonly', true);
			$("input[name='percentualTarifaMinimaPrestacao']").attr("class", 'desabilitar');
			
			$("input[name='percentualValorDebitoCalculoValorMinimoPrestacao']").val("");
			$("input[name='percentualValorDebitoCalculoValorMinimoPrestacao']").attr("readonly",true);
			$("input[name='percentualValorDebitoCalculoValorMinimoPrestacao']").attr("class", 'desabilitar'); 
			
			form.indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima[0].checked=true;
			form.indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima[0].disabled="disabled";
			form.indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima[1].disabled="disabled";
		}
	}

	function isBrancoOuNulo(obj){
		if(obj == null || obj == undefined || obj == 'undefined' || obj == ''){
			return true;
		}else{
			return false;
		}
	}

	function isBrancoOuZero(campo){ 
		
		if (campo == "0") {	
			return true;
		}
		if(campo == ""){		
			 return true;
		}

	 return false;
				
	}

	function habilitacaoCamposDescumprimentoPrestacoes(){

		var form = document.forms[0];

		if (form.indicadorCobrarMultaPorDescumprimentoPrestacao[0].checked){

			form.numeroPrestacacoesDescumpridasConsecutivas.disabled = false;
			form.percentualMultaPorPrestacacoesDescumpridas.disabled = false;
		}else{
			
			form.numeroPrestacacoesDescumpridasConsecutivas.disabled = true;
			form.numeroPrestacacoesDescumpridasConsecutivas.value = "";
			form.percentualMultaPorPrestacacoesDescumpridas.disabled = true;
			form.percentualMultaPorPrestacacoesDescumpridas.value = "";
		}
	}

	function validarCamposDescumprimentoPrestacoes(){

		var retorno = true;
		var form = document.forms[0];
		
		if (form.indicadorCobrarMultaPorDescumprimentoPrestacao[0].checked){

			if (form.numeroPrestacacoesDescumpridasConsecutivas.value == ""){

				alert("Informe N�mero de Presta��es Descumpridas Consecutivas para Cobran�a de Multa.");
				form.numeroPrestacacoesDescumpridasConsecutivas.focus();
				retorno = false;
			}else if (form.percentualMultaPorPrestacacoesDescumpridas.value == ""){

				alert("Informe Percentual da Multa.");
				form.percentualMultaPorPrestacacoesDescumpridas.focus();
				retorno = false;
			}
		}

		return retorno;
	}
	
	function verificaPermitirInformarNumeroValorParcelas() {
		
		var form = document.forms[0];
		
		if (form.indicadorPermitirCobrancaParcelamentoPorGuia[0].checked) {
			
			document.getElementById("divPermiteInformarNumeroValorParcela").style.display = 'block';
			
		} else {
			
			form.indicadorPermitirInformarNumeroValorParcelas[1].checked = true;
			document.getElementById("divPermiteInformarNumeroValorParcela").style.display = 'none';
		}
	}
	
</script>
</head>
<body leftmargin="5" topmargin="5"
	onload="setarFoco('${requestScope.nomeCampo}'); desabilitarCampo(document.AtualizarParcelamentoPerfilActionForm);verificaPermitirInformarNumeroValorParcelas();">
<html:form action="/atualizarPerfilParcelamentoAction"
	name="AtualizarParcelamentoPerfilActionForm"
	type="gcom.gui.cobranca.parcelamento.AtualizarParcelamentoPerfilActionForm"
	method="post"
	onsubmit="return validateAtualizarParcelamentoPerfilActionForm(this);">

	<input type="hidden" id="PERCENTUAL_MAXIMO_ABATIMENTO"
		value="${requestScope.percentualMaximoAbatimento}" />
		
	<html:hidden property="ultimaAlteracao"/>

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
					<td class="parabg">Atualizar Perfil de Parcelamento</td>
					<td width="11" valign="top"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>


			<p>&nbsp;</p>
			<table width="100%" border="0">

				<tr>
					<td colspan="2">Para atualizar um perfil de parcelamento, informe
					os dados abaixo:</td>
				</tr>

				<tr>
					<td><strong>N�mero da RD:</strong></td>
					<td><html:text property="numeroResolucaoDiretoria" size="15"
						maxlength="15" readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000" /></td>
				</tr>

				<tr>
					<td><strong>Tipo da Situa��o do Im�vel:</strong></td>
					<td><html:text property="imovelSituacaoTipo" size="20"
						maxlength="20" readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000" /></td>
				</tr>

				<tr>
					<td><strong>Perfil do Im�vel:</strong></td>
					<td><html:text property="imovelPerfil" size="20" maxlength="20"
						readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000" /></td>
				</tr>

				<tr>
					<td><strong>Subcategoria:</strong></td>
					<td><html:text property="subcategoria" size="50" maxlength="50"
						readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000" /></td>
				</tr>

				<tr>
					<td><strong> Percentual de Desconto sobre os Acr�scimos por
					Impontualidade:</strong></td>
					<td><logic:equal name="readOnly" value="true">
						<html:text property="percentualDescontoAcrescimo" tabindex="1" onkeypress="return isCampoNumerico(event);" size="6" maxlength="6" onkeyup="formataValorMonetario(this, 5);"
							style="text-align:right;" readonly="true" />
					</logic:equal> <logic:notEqual name="readOnly" value="true">
						<html:text property="percentualDescontoAcrescimo" tabindex="1"
							onkeypress="return isCampoNumerico(event);" size="6" maxlength="6" onkeyup="formataValorMonetario(this, 5);"
							style="text-align:right;" />
					</logic:notEqual></td>
				</tr>


				<tr> 
					<td><strong>N�o parcelar com situa��o de cobran�a: <font color="#FF0000">*</font></strong></td>
					<td>
						<strong>
							<html:radio property="indicadorParcelarChequeDevolvido" value="1"/>Sim
							<html:radio property="indicadorParcelarChequeDevolvido" value="2"/>N&atilde;o
						</strong>
					</td>
				</tr>
				
				<tr> 
					<td><strong>Cobrar Acr�scimos por Impontualidades: <font color="#FF0000">*</font></strong></td>
					<td>
						<strong>
							<html:radio property="indicadorCobrarAcrescimosImpontualidade" value="1"/>Sim
							<html:radio property="indicadorCobrarAcrescimosImpontualidade" value="2"/>N&atilde;o
						</strong>
					</td>
				</tr>
				
				<tr> 
					<td><strong>Permitir a Cobran�a do Parcelamento Atrav�s de Guia de Pagamento: <font color="#FF0000">*</font></strong></td>
					<td>
						<strong>
							<html:radio property="indicadorPermitirCobrancaParcelamentoPorGuia" value="1" onclick="javascript:verificaPermitirInformarNumeroValorParcelas();"/>Sim
							<html:radio property="indicadorPermitirCobrancaParcelamentoPorGuia" value="2" onclick="javascript:verificaPermitirInformarNumeroValorParcelas();"/>N&atilde;o
						</strong>
					</td>
				</tr>
				
				<tr>
					<td>
						<div id="divPermiteInformarNumeroValorParcela" style="display:block" >
							<table>
								<tr>
									<td><strong>Permitir informar n�mero/valor de parcelas:</strong></td>
									<td>
										<strong>
											<html:radio property="indicadorPermitirInformarNumeroValorParcelas" value="1"/>Sim
											<html:radio property="indicadorPermitirInformarNumeroValorParcelas" value="2"/>N&atilde;o
										</strong>
									</td>
								</tr>
							</table>
						</div>
					</td> 
				</tr>
				
				<tr> 
					<td><strong>Cobran�a de Multa em Caso de Descumprimento de Presta��es: <font color="#FF0000">*</font></strong></td>
					<td>
						<strong>
							<html:radio property="indicadorCobrarMultaPorDescumprimentoPrestacao" value="1" onclick="javascript:habilitacaoCamposDescumprimentoPrestacoes();"/>Sim
							<html:radio property="indicadorCobrarMultaPorDescumprimentoPrestacao" value="2" onclick="javascript:habilitacaoCamposDescumprimentoPrestacoes();"/>N&atilde;o
						</strong>
					</td>
				</tr>
				
				<tr>
              		<td><strong>N�mero de Presta��es Descumpridas Consecutivas para Cobran�a de Multa:</strong></td>
                	<td>
                		<html:text property="numeroPrestacacoesDescumpridasConsecutivas" size="8" maxlength="8" onkeypress="return isCampoNumerico(event);"
                			style="text-align:right;" />
                  	</td>
               	</tr>
               	
               	<tr>
              		<td><strong>Percentual da Multa:</strong></td>
                	<td>
                		<html:text property="percentualMultaPorPrestacacoesDescumpridas" size="6" maxlength="6" 
                			onkeyup="formataValorMonetario(this, 5)" onkeypress="return isCampoNumerico(event);"
                			style="text-align:right;" />
                		
                		<script type="text/javascript">
			    			habilitacaoCamposDescumprimentoPrestacoes();
			    		</script>
                  	</td>
               	</tr>
               	
               	<tr>
              		<td><strong>Consumo Economia:</strong></td>
                	<td>
                		<html:text property="numeroConsumoEconomia" size="8" maxlength="8" onkeypress="return isCampoNumerico(event);"
                			style="text-align:right;" />
                  	</td>
               	</tr>
               	<tr>
              		<td><strong>�rea Constru�da:</strong></td>
                	<td>
                		<html:text property="numeroAreaConstruida" size="8" maxlength="8" onkeypress="return isCampoNumerico(event);"
                			style="text-align:right;" />
                  	</td>
               	</tr>
               	<tr>
         			 <td width="30%"><strong>Refer�ncia Limite Inferior:</strong></td>
          			<td width="70%">
          				<html:text property="anoMesReferenciaLimiteInferior" size="7"  maxlength="7" 
          						   onkeypress="javascript: return isCampoNumerico(event);"
          						   onkeyup="javascript:mascaraAnoMes(this, event);" 
          						   onblur="javascript:return verificaAnoMes(this);"/>
         					&nbsp;mm/aaaa
         					<br/>
          			</td>
        		</tr>
        		<tr>
         			 <td width="30%"><strong>Refer�ncia Limite Superior:</strong></td>
          			<td width="70%">
          				<html:text property="anoMesReferenciaLimiteSuperior" size="7"  maxlength="7" 
          						   onkeypress="javascript: return isCampoNumerico(event);"
          						   onkeyup="javascript:mascaraAnoMes(this, event);" 
          						   onblur="javascript:return verificaAnoMes(this);"/>
         					&nbsp;mm/aaaa
         					<br/>
          			</td>
        		</tr>
				
				<tr>
              		<td><strong>Percentual de Desconto de Tarifa Social:</strong></td>
                	<td>
                		<html:text property="percentualDescontoAVista" size="6" maxlength="6" 
                			onkeyup="formataValorMonetario(this, 5)" onkeypress="return isCampoNumerico(event);"
                			style="text-align:right;" />
                		
                		<script type="text/javascript">
			    			//habilitacaoCamposDescumprimentoPrestacoes();
			    		</script>
                  	</td>
               	</tr>
               	<tr>
              		<td><strong>Percentual Quantidade M�nima da Fatura:</strong></td>
                	<td>
                		<html:text property="parcelaQuantidadeMinimaFatura" size="6" maxlength="6" 
                			onkeypress="return isCampoNumerico(event);"
                			style="text-align:right;" />
                		
                		<script type="text/javascript">
			    			//habilitacaoCamposDescumprimentoPrestacoes();
			    		</script>
                  	</td>
               	</tr>
               	<tr>
              		<td><strong>Percentual Desconto San��o:</strong></td>
                	<td>
                		<html:text property="percentualDescontoSancao" size="6" maxlength="6" 
                			onkeyup="formataValorMonetario(this, 5)" onkeypress="return isCampoNumerico(event);"
                			style="text-align:right;" />
                		
                		<script type="text/javascript">
			    			//habilitacaoCamposDescumprimentoPrestacoes();
			    		</script>
                  	</td>
               	</tr>
               	<tr>
              		<td><strong>Percentual da Quantidade de Economias:</strong></td>
                	<td>
                		<html:text property="quantidadeEconomias" size="6" maxlength="6" 
                			onkeypress="return isCampoNumerico(event);"
                			style="text-align:right;" />
                		
                		<script type="text/javascript">
			    			//habilitacaoCamposDescumprimentoPrestacoes();
			    		</script>
                  	</td>
               	</tr>
               	<tr>
              		<td><strong>Percentual da Capacidade do Hidr�metro:</strong></td>
                	<td>
                		<html:text property="capacidadeHidrometro" size="6" maxlength="6" 
                			onkeypress="return isCampoNumerico(event);"
                			style="text-align:right;" />
                		
                		<script type="text/javascript">
			    			//habilitacaoCamposDescumprimentoPrestacoes();
			    		</script>
                  	</td>
               	</tr>
				
				
				<tr bgcolor="#49A3FC">
					<td colspan="2" align="center"><strong>Faixa de D�bito a Parcelar</strong></td>
				</tr>
				
				<tr> 
                	<td><strong>Valor M�nimo de D�bito a Parcelar:<font color="#ff0000">*</font></strong></td>
                	<td>
                		<html:text property="valorMinimoDebitoAParcelarFaixaDebito" size="11" maxlength="11" tabindex="5" onkeyup="formataValorMonetario(this, 14)" style="text-align:right;" />
                  	</td>
              	</tr>
              	
              	<tr> 
                	<td><strong>Valor M�ximo de D�bito a Parcelar:<font color="#ff0000">*</font></strong></td>
                	<td>
                		<html:text property="valorMaximoDebitoAParcelarFaixaDebito" size="11" maxlength="11" 
                		tabindex="5" onkeyup="formataValorMonetario(this, 14)" 
                		style="text-align:right;" />
                  	</td>
              	</tr>
              	
              	<tr> 
					<td><strong>Op��es: <font color="#ff0000">*</font></strong></td>
					<td>
						<strong>
							<html:radio property="indicadorDebitoOriginalOuAtualizadoFaixaDebito" value="1"/>d�bito original
							<html:radio property="indicadorDebitoOriginalOuAtualizadoFaixaDebito" value="2"/>d�bito atualizado
						</strong>
					</td>
				</tr>
				
				<tr bgcolor="#49A3FC">
					<td colspan="2" align="center"><strong>Presta��o M�nima</strong></td>
				</tr>
				
				<tr> 
                	<td><strong> Percentual da Tarifa M�nima para C�lculo do Valor M�nimo da Presta��o:<font color="#FF0000">*</font></strong></td>
                
                	<td>
                	
                    <html:text property="percentualTarifaMinimaPrestacao" size="6" maxlength="6" onkeypress="return isCampoNumerico(event);" 
                		tabindex="5" onkeyup="desabilitarCampo(document.forms[0]);formataValorMonetario(this, 5)"
                		style="text-align:right;" />
                  	</td>
                 
               	</tr>
              	
              	<tr> 
                	<td><strong>Percentual do Valor do d�bito para C�lculo do Valor M�nimo da Presta��o:<font color="#FF0000">*</font></strong></td>
                	
                	<td>
                		<html:text property="percentualValorDebitoCalculoValorMinimoPrestacao" size="6" maxlength="6" onkeypress="return isCampoNumerico(event);"
                		tabindex="5" onkeyup="desabilitarCampo(document.forms[0]);formataValorMonetario(this, 5);"
                		style="text-align:right;" />
                  	</td>
                  	
              	</tr>
              	
              	<tr> 
					<td><strong>Op��es: <font color="#ff0000">*</font></strong></td>
					<td>
						<strong>
							<html:radio property="indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima" value="1"/>d�bito original
							<html:radio property="indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima" value="2"/>d�bito atualizado
						</strong>
					</td>
				</tr>
              	
              	
              	<tr> 
                	<td><strong> Valor M�nimo da Presta��o:<font color="#FF0000">*</font></strong></td>
                	
                	<td>
                		<html:text property="valorDebitoPrestacao" size="11" maxlength="11" onkeypress="return isCampoNumerico(event);"
                		tabindex="5" onkeyup="desabilitarCampo(document.forms[0]);formataValorMonetario(this, 14)"
                		style="text-align:right;" />
                  	</td>
                  	
              	</tr>
				
              	<tr bgcolor="#49A3FC">
					<td colspan="2" align="center"><strong> �nica Fatura</strong></td>
				</tr>
              	
              	
   				<tr>
            		<td><strong> Consumo m�nimo por economia:</strong></td>
	               	<td>
	               		<html:text property="consumoMinimo" size="6" maxlength="6" onkeypress="return isCampoNumerico(event);"
	               		tabindex="5" style="text-align:right;" />
	                 	</td>
	              	</tr>
				<tr>
					<td><strong> Percentual de varia��o consumo m�dio:</strong></td>
	                 	<td>
	               		<html:text property="percentualVariacaoConsumoMedio" size="6" maxlength="6" onkeypress="return isCampoNumerico(event);"
	               		tabindex="5" onkeyup="formataValorMonetario(this, 5)" style="text-align:right;" />
	                 	</td>
	
				</tr>			               	
	              	<tr> 
					<td><strong>N�o parcelar com san��es em mais de uma conta: <font color="#FF0000">*</font></strong></td>
					<td colspan="3">
						<strong>
							<html:radio property="indicadorParcelarSancoesMaisDeUmaConta" value="1"/>Sim
							<html:radio property="indicadorParcelarSancoesMaisDeUmaConta" value="2"/>N&atilde;o
						</strong>
					</td>
				</tr>
			               	
           
				

				<%-- in�cio da tabela de Quantidade M�xima de Reparcelamentos Consecutivos --%>
				<tr>
					<td>
					<p>&nbsp;</p>
					</td>
				</tr>

				<tr bgcolor="#49A3FC">
					<td colspan="2" align="center"><strong>Reparcelamentos Consecutivos</strong></td>
				</tr>

				<tr>
					<td><strong>Reparcelamentos Consecutivos:<font color="#FF0000">*</font></strong></td>
					<td><logic:equal name="readOnly" value="true">
						<html:text property="qtdeMaximaReparcelamento" size="3" onkeypress="return isCampoNumerico(event);"
							maxlength="3" tabindex="6" readonly="true" />
					</logic:equal> <logic:notEqual name="readOnly" value="true">
						<html:text property="qtdeMaximaReparcelamento" size="3" onkeypress="return isCampoNumerico(event);"
							maxlength="3" tabindex="6" />
					</logic:notEqual></td>
				</tr>

				<tr>
					<td><strong> Reparcelamentos Consecutivos Informado(s) </strong></td>
					<td align="right"><logic:equal name="readOnly" value="true">
						<input name="Button" type="button" class="bottonRightCol"
							value="Adicionar" align="right" />
					</logic:equal> <logic:notEqual name="readOnly" value="true">
						<input name="Button" type="button" class="bottonRightCol"
							value="Adicionar" align="right"
							onclick="adicionarReparcelamento(document.forms[0])" />
					</logic:notEqual></td>
				</tr>




				<%int cont = 0;%>
				<tr>
					<td colspan="2">
					<table width="100%" border="0" bgcolor="90c7fc">
						
						<logic:empty name="collectionParcelamentoQuantidadeReparcelamentoHelper"
							scope="session">
							<tr bgcolor="#90c7fc">
								<td align="center" width="10%"><strong>Remover</strong></td>
								<td align="center" width="30%"><strong>Reparcelamentos Consecutivos</strong></td>
								<td align="center" width="60%"><strong>Informa��es do Parcelamento por Quantidade de Reparcelamentos</strong></td>
							</tr>
							<tr>
								<td align="center" width="10%" bgcolor="#ffffff">&nbsp;</td>
								<td align="center" width="30%" bgcolor="#ffffff">&nbsp;</td>
								<td align="center" width="60%" bgcolor="#ffffff">&nbsp;</td>
							</tr>
						</logic:empty>
						
						<logic:notEmpty
							name="collectionParcelamentoQuantidadeReparcelamentoHelper"
							scope="session">
							<%if (((Collection) session
								.getAttribute("collectionParcelamentoQuantidadeReparcelamentoHelper"))
								.size() <= ConstantesSistema.NUMERO_MAXIMO_REGISTROS_PERFIL_PARCELAMENTO) {%>
							<tr bgcolor="#90c7fc">
								<td align="center" width="10%"><strong>Remover</strong></td>
								<td align="center" width="30%"><strong>Reparcelamentos Consecutivos</strong></td>
								<td align="center" width="60%"><strong>Informa��es do Parcelamento por Quantidade de Reparcelamentos</strong></td>
							</tr>
							<logic:iterate
								name="collectionParcelamentoQuantidadeReparcelamentoHelper"
								id="parcelamentoQuantidadeReparcelamentoHelper"
								type="ParcelamentoQuantidadeReparcelamentoHelper">
								<%cont = cont + 1;
									if (cont % 2 == 0) {%>
								<tr bgcolor="#cbe5fe">
									<%} else {%>
								<tr bgcolor="#FFFFFF">
									<%}%>
									<td width="10%">
										<logic:equal name="readOnly" value="true">
											<div align="center"><font color="#333333"> <img width="14"
												height="14" border="0"
												src="<bean:message key="caminho.imagens"/>Error.gif" /> </font></div>
										</logic:equal> 
										<logic:notEqual name="readOnly" value="true">
											<div align="center"><font color="#333333"> <img width="14"
												height="14" border="0"
												src="<bean:message key="caminho.imagens"/>Error.gif"
												onclick="javascript:document.forms[0].target='';if(confirm('Confirma remo��o?')){redirecionarSubmit('removerParcelamentoQuantidadeReparcelamentoHelperActionAtualizar.do?qtdeMaxReparcelamento=<bean:write name="parcelamentoQuantidadeReparcelamentoHelper" property="quantidadeMaximaReparcelamento"/>');}" />
											</font></div>
										</logic:notEqual>
									</td>

									<td width="30%">
									<div align="center"><logic:notPresent name="acao"
										scope="session">
										<logic:equal name="readOnly" value="true">
											<a
												href="javascript:abrirPopupComSubmitLink(document.forms[0],'','',<bean:write name="parcelamentoQuantidadeReparcelamentoHelper" 
													property="quantidadeMaximaReparcelamento" />,true);"><bean:write
												name="parcelamentoQuantidadeReparcelamentoHelper"
												property="quantidadeMaximaReparcelamento" /></a>&nbsp;
												
											<%-- <bean:write name="parcelamentoQuantidadeReparcelamentoHelper"
												property="quantidadeMaximaReparcelamento" />&nbsp; --%>
										</logic:equal>
										<logic:notEqual name="readOnly" value="true">
											<a
												href="javascript:abrirPopupComSubmitLink(document.forms[0],'','',<bean:write name="parcelamentoQuantidadeReparcelamentoHelper" 
													property="quantidadeMaximaReparcelamento" />,false);"><bean:write
												name="parcelamentoQuantidadeReparcelamentoHelper"
												property="quantidadeMaximaReparcelamento" /></a>&nbsp;
										</logic:notEqual>
									</logic:notPresent></div>
									</td>

									<td width="60%">
									<div>${parcelamentoQuantidadeReparcelamentoHelper.informacaoParcelamentoQtdeReparcelamento}
									&nbsp;</div>
									</td>

								</tr>
							</logic:iterate>
							<%} else {%>
							<tr bgcolor="#90c7fc">
								<td align="center" width="10%"><strong>Remover</strong></td>
								<td align="center" width="30%"><strong>Reparcelamentos
								Consecutivos</strong></td>
								<td align="center" width="60%"><strong>Informa��es do
								Parcelamento por Quantidade de Reparcelamentos</strong></td>
							</tr>
							<tr>
								<td height="100" colspan="6">
								<div style="width: 100%; height: 100%; overflow: auto;">
								<table width="100%">
									<logic:iterate
										name="collectionParcelamentoQuantidadeReparcelamentoHelper"
										id="parcelamentoQuantidadeReparcelamentoHelper"
										type="ParcelamentoQuantidadeReparcelamentoHelper">
										<%cont = cont + 1;
				if (cont % 2 == 0) {%>
										<tr bgcolor="#cbe5fe">
											<%} else {%>
										<tr bgcolor="#FFFFFF">
											<%}%>
											<td width="10%"><logic:equal name="readOnly" value="true">
												<div align="center"><font color="#333333"> <img width="14"
													height="14" border="0"
													src="<bean:message key="caminho.imagens"/>Error.gif" /> </font></div>
											</logic:equal> <logic:notEqual name="readOnly" value="true">
												<div align="center"><font color="#333333"> <img width="14"
													height="14" border="0"
													src="<bean:message key="caminho.imagens"/>Error.gif"
													onclick="javascript:document.forms[0].target='';if(confirm('Confirma remo��o?')){redirecionarSubmit('removerParcelamentoQuantidadeReparcelamentoHelperActionAtualizar.do?qtdeMaxReparcelamento=<bean:write name="parcelamentoQuantidadeReparcelamentoHelper" property="quantidadeMaximaReparcelamento"/>');}" />
												</font></div>
											</logic:notEqual></td>

											<td width="30%">
											<div align="center"><logic:notPresent name="acao"
												scope="session">
												<logic:equal name="readOnly" value="true">
													<a
														href="javascript:abrirPopupComSubmitLink(document.forms[0],'','',<bean:write name="parcelamentoQuantidadeReparcelamentoHelper" 
													property="quantidadeMaximaReparcelamento" />,true);"><bean:write
														name="parcelamentoQuantidadeReparcelamentoHelper"
														property="quantidadeMaximaReparcelamento" /></a>&nbsp;
													<%--  <bean:write
														name="parcelamentoQuantidadeReparcelamentoHelper"
														property="quantidadeMaximaReparcelamento" />&nbsp;--%>
													</logic:equal>
												<logic:notEqual name="readOnly" value="true">
													<a
														href="javascript:abrirPopupComSubmitLink(document.forms[0],'','',<bean:write name="parcelamentoQuantidadeReparcelamentoHelper" 
													property="quantidadeMaximaReparcelamento" />,false);"><bean:write
														name="parcelamentoQuantidadeReparcelamentoHelper"
														property="quantidadeMaximaReparcelamento" /></a>&nbsp;
													</logic:notEqual>
											</logic:notPresent></div>
											</td>

											<td width="60%">
											<div>${parcelamentoQuantidadeReparcelamentoHelper.informacaoParcelamentoQtdeReparcelamento}
											&nbsp;</div>
											</td>



										</tr>
									</logic:iterate>
								</table>
								</div>
								</td>
							</tr>
							<%}%>
						</logic:notEmpty>
					</table>
					</td>
				</tr>












				<%-- final da tabela de Quantidade M�xima de Reparcelamentos Consecutivos --%>

				<%-- in�cio da tabela de Descontos por Antiguidade --%>

				<tr>
					<td>
					<p>&nbsp;</p>
					</td>
				</tr>

				<tr bgcolor="#49A3FC">
					<td colspan="2" align="center"><strong>Desconto(s) por Antiguidade</strong></td>
				</tr>

				<tr>
					<td><strong>Qtde. M�nima Meses de D�bito p/ Desconto:</strong></td>
					<td><logic:equal name="readOnly" value="true">
						<html:text property="quantidadeMinimaMesesDebito" size="4" onkeypress="return isCampoNumerico(event);"
							maxlength="4" tabindex="8" readonly="true" />
					</logic:equal> <logic:notEqual name="readOnly" value="true">
						<html:text property="quantidadeMinimaMesesDebito" size="4" onkeypress="return isCampoNumerico(event);"
							maxlength="4" tabindex="8" />
					</logic:notEqual></td>
				</tr>

				<tr>
					<td><strong> Percentual de Desconto Sem Restabelecimento:</strong></td>
					<td><logic:equal name="readOnly" value="true">
						<html:text
							property="percentualDescontoSemRestabelecimentoAntiguidade"
							size="6" maxlength="6" tabindex="9" onkeypress="return isCampoNumerico(event);"
							onkeyup="formataValorMonetario(this, 5)" readonly="true"
							style="text-align:right;" />
					</logic:equal> <logic:notEqual name="readOnly" value="true">
						<html:text
							property="percentualDescontoSemRestabelecimentoAntiguidade"
							size="6" maxlength="6" tabindex="9" onkeypress="return isCampoNumerico(event);"
							onkeyup="formataValorMonetario(this, 5)"
							style="text-align:right;" />
					</logic:notEqual></td>
				</tr>

				<tr>
					<td><strong> Percentual de Desconto Com Restabelecimento:</strong></td>
					<td><logic:equal name="readOnly" value="true">
						<html:text
							property="percentualDescontoComRestabelecimentoAntiguidade"
							size="6" maxlength="6" tabindex="10" onkeypress="return isCampoNumerico(event);"
							onkeyup="formataValorMonetario(this, 5)"
							style="text-align:right;" readonly="true" />
					</logic:equal> <logic:notEqual name="readOnly" value="true">
						<html:text
							property="percentualDescontoComRestabelecimentoAntiguidade" onkeypress="return isCampoNumerico(event);"
							size="6" maxlength="6" tabindex="10" style="text-align:right;"
							onkeyup="formataValorMonetario(this, 5)" />
					</logic:notEqual></td>
				</tr>

				<tr>
					<td><strong> Percentual de Desconto Ativo: </strong></td>
					<td><logic:equal name="readOnly" value="true">
						<html:text property="percentualDescontoAtivo" size="6"
							maxlength="6" tabindex="11" onkeypress="return isCampoNumerico(event);"
							onkeyup="formataValorMonetario(this, 5)"
							style="text-align:right;" readonly="true" />
					</logic:equal> <logic:notEqual name="readOnly" value="true">
						<html:text property="percentualDescontoAtivo" size="6" onkeypress="return isCampoNumerico(event);"
							maxlength="6" tabindex="11" style="text-align:right;"
							onkeyup="formataValorMonetario(this, 5)" />
					</logic:notEqual></td>
				</tr>

				<tr>
					<td><strong> Desconto(s) por Antiguidade Informado(s) </strong></td>
					<td align="right"><logic:equal name="readOnly" value="true">
						<input name="Button" type="button" class="bottonRightCol"
							value="Adicionar" align="right" />
					</logic:equal> <logic:notEqual name="readOnly" value="true">
						<input name="Button" type="button" class="bottonRightCol"
							value="Adicionar" align="right"
							onclick="adicionarParcelamentoDescontoAntiguidade(document.forms[0])" />
					</logic:notEqual></td>
				</tr>


				<%int cont4 = 0;%>
				<tr>
					<td colspan="4">
					<table width="100%" border="0" bgcolor="90c7fc">

						<logic:empty name="collectionParcelamentoDescontoAntiguidade"
							scope="session">
							<tr bordercolor="#000000" bgcolor="#90c7fc">
								<td rowspan="2" align="center" width="10%"><strong>Remover</strong></td>
								<td rowspan="2" width="25%" align="center"><strong>Qtde. M�nima
								Meses de D�bito</strong></td>
								<td colspan="3 " align="center"><strong>Percentual de Desconto</strong></td>
							</tr>
							<tr bgcolor="#cbe5fe">
								<td width="25%" align="center"><strong>Sem Restabelecimento</strong></td>
								<td width="25%" align="center"><strong>Com Restabelecimento</strong></td>
								<td width="15%" align="center"><strong>Ativo</strong></td>
							</tr>
							<tr>
								<td align="center" width="10%" bgcolor="#ffffff">&nbsp;</td>
								<td align="center" width="25%" bgcolor="#ffffff">&nbsp;</td>
								<td align="center" width="25%" bgcolor="#ffffff">&nbsp;</td>
								<td align="center" width="25%" bgcolor="#ffffff">&nbsp;</td>
								<td align="center" width="15%" bgcolor="#ffffff">&nbsp;</td>
							</tr>
						</logic:empty>


						<logic:notEmpty name="collectionParcelamentoDescontoAntiguidade"
							scope="session">

							<%if (((Collection) session
					.getAttribute("collectionParcelamentoDescontoAntiguidade"))
					.size() <= ConstantesSistema.NUMERO_MAXIMO_REGISTROS_PERFIL_PARCELAMENTO) {%>
							<tr bordercolor="#000000" bgcolor="#90c7fc">
								<td rowspan="2" align="center" width="10%"><strong>Remover</strong></td>
								<td rowspan="2" width="25%" align="center"><strong>Qtde. M�nima
								Meses de D�bito</strong></td>
								<td colspan="3" align="center"><strong>Percentual de Desconto</strong></td>
							</tr>
							<tr bgcolor="#cbe5fe">
								<td width="25%" align="center"><strong>Sem Restabelecimento</strong></td>
								<td width="25%" align="center"><strong>Com Restabelecimento</strong></td>
								<td width="15%" align="center"><strong>Ativo</strong></td>
							</tr>

							<logic:iterate name="collectionParcelamentoDescontoAntiguidade"
								id="parcelamentoDescontoAntiguidade"
								type="ParcelamentoDescontoAntiguidade">
								<%cont4 = cont4 + 1;
				if (cont4 % 2 == 0) {%>
								<tr bgcolor="#cbe5fe">
									<%} else {%>
								<tr bgcolor="#FFFFFF">
									<%}%>

									<td width="10%"><logic:equal name="readOnly" value="true">
										<div align="center"><font color="#333333"> <img width="14"
											height="14" border="0"
											src="<bean:message key="caminho.imagens"/>Error.gif" /> </font></div>
									</logic:equal> <logic:notEqual name="readOnly" value="true">
										<div align="center"><font color="#333333"> <img width="14"
											height="14" border="0"
											src="<bean:message key="caminho.imagens"/>Error.gif"
											onclick="javascript:document.forms[0].target='';if(confirm('Confirma remo��o?')){redirecionarSubmit('removerParcelamentoDescontoAntiguidadeActionAtualizar.do?quantidadeMinimaMesesDeb=<bean:write name="parcelamentoDescontoAntiguidade" property="quantidadeMinimaMesesDebito"/>');}" />
										</font></div>
									</logic:notEqual></td>

									<td width="25%" align="center">
									<div>${parcelamentoDescontoAntiguidade.quantidadeMinimaMesesDebito}
									&nbsp;</div>
									</td>

									<td width="25%" align="center"><logic:equal name="readOnly"
										value="true">
										<input type="text"
											style="text-align: right;font-size: xx-small;" size="6"
											maxlength="6" align="center" onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
											name="vlSemRestAntiguidade<bean:write name="parcelamentoDescontoAntiguidade" property="ultimaAlteracao.time"/>"
											value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoAntiguidade.getPercentualDescontoSemRestabelecimento())%>"
											readonly="true" style="text-align:right;" />
									</logic:equal> <logic:notEqual name="readOnly" value="true">
										<input type="text"
											style="text-align: right;font-size: xx-small;" size="6"
											maxlength="6" align="center" 
											name="vlSemRestAntiguidade<bean:write name="parcelamentoDescontoAntiguidade" property="ultimaAlteracao.time"/>"
											value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoAntiguidade.getPercentualDescontoSemRestabelecimento())%>"
											onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
											style="text-align:right;" />
									</logic:notEqual></td>


									<td width="25%" align="center"><logic:equal name="readOnly"
										value="true">
										<input type="text"
											style="text-align: right;font-size: xx-small;" size="6"
											maxlength="6" align="center" onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
											name="vlComRestAntiguidade<bean:write name="parcelamentoDescontoAntiguidade" property="ultimaAlteracao.time"/>"
											value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoAntiguidade.getPercentualDescontoComRestabelecimento())%>"
											readonly="true" style="text-align:right;" />
									</logic:equal> <logic:notEqual name="readOnly" value="true">
										<input type="text"
											style="text-align: right;font-size: xx-small;" size="6"
											maxlength="6" align="center" name="vlComRestAntiguidade<bean:write name="parcelamentoDescontoAntiguidade" property="ultimaAlteracao.time"/>"
											value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoAntiguidade.getPercentualDescontoComRestabelecimento())%>"
											onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
											style="text-align:right;" />
									</logic:notEqual></td>



									<td width="15%" align="center"><logic:equal name="readOnly"
										value="true">
										<input type="text"
											style="text-align: right;font-size: xx-small;" size="6"
											maxlength="6" align="center" onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
											name="vlDescontoAtivo<bean:write name="parcelamentoDescontoAntiguidade" property="ultimaAlteracao.time"/>"
											value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoAntiguidade.getPercentualDescontoAtivo())%>"
											readonly="true" style="text-align:right;" />
									</logic:equal> <logic:notEqual name="readOnly" value="true">
										<input type="text"
											style="text-align: right;font-size: xx-small;" size="6"
											maxlength="6" align="center" onkeypress="return isCampoNumerico(event);"
											name="vlDescontoAtivo<bean:write name="parcelamentoDescontoAntiguidade" property="ultimaAlteracao.time"/>"
											value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoAntiguidade.getPercentualDescontoAtivo())%>"
											onkeyup="formataValorMonetario(this, 5)"
											style="text-align:right;" />
									</logic:notEqual></td>
								</tr>
							</logic:iterate>

							<%} else {%>

							<tr bordercolor="#000000" bgcolor="#90c7fc">
								<td rowspan="2" align="center" width="10%"><strong>Remover</strong></td>
								<td rowspan="2" width="23%" align="center"><strong>Qtde. M�nima
								Meses de D�bito</strong></td>
								<td colspan="3" align="center"><strong>Percentual de Desconto</strong></td>
							</tr>
							<tr bgcolor="#cbe5fe">
								<td width="24%" align="center"><strong>Sem Restabelecimento</strong></td>
								<td width="24%" align="center"><strong>Com Restabelecimento</strong></td>
								<td width="15%" align="center"><strong>Ativo</strong></td>
							</tr>
							<tr>
								<td height="100" colspan="6">
								<div style="width: 100%; height: 100%; overflow: auto;">
								<table width="100%">
									<logic:iterate name="collectionParcelamentoDescontoAntiguidade"
										id="parcelamentoDescontoAntiguidade"
										type="ParcelamentoDescontoAntiguidade">
										<%cont4 = cont4 + 1;
				if (cont4 % 2 == 0) {%>
										<tr bgcolor="#cbe5fe">
											<%} else {%>
										<tr bgcolor="#FFFFFF">
											<%}%>

											<td width="10%"><logic:equal name="readOnly" value="true">
												<div align="center"><font color="#333333"> <img width="14"
													height="14" border="0"
													src="<bean:message key="caminho.imagens"/>Error.gif" /> </font></div>
											</logic:equal> <logic:notEqual name="readOnly" value="true">
												<div align="center"><font color="#333333"> <img width="14"
													height="14" border="0"
													src="<bean:message key="caminho.imagens"/>Error.gif"
													onclick="javascript:document.forms[0].target='';if(confirm('Confirma remo��o?')){redirecionarSubmit('removerParcelamentoDescontoAntiguidadeActionAtualizar.do?quantidadeMinimaMesesDeb=<bean:write name="parcelamentoDescontoAntiguidade" property="quantidadeMinimaMesesDebito"/>');}" />
												</font></div>
											</logic:notEqual></td>





											<td width="25%" align="center">
											<div>${parcelamentoDescontoAntiguidade.quantidadeMinimaMesesDebito}
											&nbsp;</div>
											</td>





											<td width="26%" align="center"><logic:equal name="readOnly"
												value="true">
												<input type="text"
													style="text-align: right;font-size: xx-small;" size="6"
													maxlength="6" align="center" onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
													name="vlSemRestAntiguidade<bean:write name="parcelamentoDescontoAntiguidade" property="ultimaAlteracao.time"/>"
													value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoAntiguidade.getPercentualDescontoSemRestabelecimento())%>"
													readonly="true" style="text-align:right;" />
											</logic:equal> <logic:notEqual name="readOnly" value="true">
												<input type="text"
													style="text-align: right;font-size: xx-small;" size="6"
													maxlength="6" align="center" onkeypress="return isCampoNumerico(event);"
													name="vlSemRestAntiguidade<bean:write name="parcelamentoDescontoAntiguidade" property="ultimaAlteracao.time"/>"
													value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoAntiguidade.getPercentualDescontoSemRestabelecimento())%>"
													onkeyup="formataValorMonetario(this, 5);"
													style="text-align:right;" />
											</logic:notEqual></td>



											<td width="26%" align="center"><logic:equal name="readOnly"
												value="true">
												<input type="text"
													style="text-align: right;font-size: xx-small;" size="6"
													maxlength="6" align="center" onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
													name="vlComRestAntiguidade<bean:write name="parcelamentoDescontoAntiguidade" property="ultimaAlteracao.time"/>"
													value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoAntiguidade.getPercentualDescontoComRestabelecimento())%>"
													readonly="true" style="text-align:right;" />
											</logic:equal> <logic:notEqual name="readOnly" value="true">
												<input type="text"
													style="text-align: right;font-size: xx-small;" size="6"
													maxlength="6" align="center"
													name="vlComRestAntiguidade<bean:write name="parcelamentoDescontoAntiguidade" property="ultimaAlteracao.time"/>"
													value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoAntiguidade.getPercentualDescontoComRestabelecimento())%>"
													onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
													style="text-align:right;" />
											</logic:notEqual></td>



											<td width="14%" align="center"><logic:equal name="readOnly"
												value="true">
												<input type="text"
													style="text-align: right;font-size: xx-small;" size="6"
													maxlength="6" align="center" onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
													name="vlDescontoAtivo<bean:write name="parcelamentoDescontoAntiguidade" property="ultimaAlteracao.time"/>"
													value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoAntiguidade.getPercentualDescontoAtivo())%>"
													readonly="true" style="text-align:right;" />
											</logic:equal> <logic:notEqual name="readOnly" value="true">
												<input type="text"
													style="text-align: right;font-size: xx-small;" size="6"
													maxlength="6" align="center"
													name="vlDescontoAtivo<bean:write name="parcelamentoDescontoAntiguidade" property="ultimaAlteracao.time"/>"
													value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoAntiguidade.getPercentualDescontoAtivo())%>"
													onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
													style="text-align:right;" />
											</logic:notEqual></td>



										</tr>
									</logic:iterate>
								</table>
								</div>
								</td>
							</tr>
							<%}%>
						</logic:notEmpty>
					</table>
					</td>
				</tr>


				<%-- final da tabela de Descontos por Antiguidade --%>



				<%-- in�cio da tabela de Descontos por Inatividade --%>
				<tr>
					<td>
					<p>&nbsp;</p>
					</td>
				</tr>

				<tr bgcolor="#49A3FC">
					<td colspan="2" align="center"><strong>Desconto(s) por Inatividade</strong></td>
				</tr>

				<tr>
					<td><strong> Qtde. M�xima Meses de Inatividade da Lig. de �gua:</strong></td>
					<td><logic:equal name="readOnly" value="true">
						<html:text property="quantidadeMaximaMesesInatividade" size="4" onkeypress="return isCampoNumerico(event);"
							maxlength="4" tabindex="12" readonly="true" />
					</logic:equal> <logic:notEqual name="readOnly" value="true">
						<html:text property="quantidadeMaximaMesesInatividade" size="4" onkeypress="return isCampoNumerico(event);"
							maxlength="4" tabindex="12" />
					</logic:notEqual></td>
				</tr>

				<tr>
					<td><strong> Percentual de Desconto Sem Restabelecimento:</strong></td>
					<td><logic:equal name="readOnly" value="true">
						<html:text
							property="percentualDescontoSemRestabelecimentoInatividade"
							size="6" maxlength="6" tabindex="13"
							onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
							style="text-align:right;" readonly="true" />
					</logic:equal> <logic:notEqual name="readOnly" value="true">
						<html:text
							property="percentualDescontoSemRestabelecimentoInatividade"
							size="6" maxlength="6" tabindex="13"
							onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
							style="text-align:right;" />
					</logic:notEqual></td>
				</tr>

				<tr>
					<td><strong> Percentual de Desconto Com Restabelecimento:</strong></td>
					<td><logic:equal name="readOnly" value="true">
						<html:text
							property="percentualDescontoComRestabelecimentoInatividade"
							size="6" maxlength="6" tabindex="14"
							onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
							style="text-align:right;" readonly="true" />
					</logic:equal> <logic:notEqual name="readOnly" value="true">
						<html:text
							property="percentualDescontoComRestabelecimentoInatividade"
							size="6" maxlength="6" tabindex="14"
							onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
							style="text-align:right;" />
					</logic:notEqual></td>
				</tr>

            	<tr> 
              	<td><strong> Percentual de Desconto de Juros Mora Sem Restabelecimento:</strong></td>
				<td><logic:equal name="readOnly" value="true">
					<html:text
						property="percentualDescontoJurosMoraSemRestabelecimentoInatividade"
						size="6" maxlength="6" tabindex="15"
						onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
						style="text-align:right;" readonly="true" />
				</logic:equal> <logic:notEqual name="readOnly" value="true">
					<html:text
						property="percentualDescontoJurosMoraSemRestabelecimentoInatividade"
						size="6" maxlength="6" tabindex="15"
						onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
						style="text-align:right;" />
				</logic:notEqual></td>
            	</tr>

            	<tr> 
              	<td><strong> Percentual de Desconto de Juros Mora Com Restabelecimento:</strong></td>
				<td><logic:equal name="readOnly" value="true">
					<html:text
						property="percentualDescontoJurosMoraComRestabelecimentoInatividade"
						size="6" maxlength="6" tabindex="16"
						onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
						style="text-align:right;" readonly="true" />
				</logic:equal> <logic:notEqual name="readOnly" value="true">
					<html:text
						property="percentualDescontoJurosMoraComRestabelecimentoInatividade"
						size="6" maxlength="6" tabindex="16"
						onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
						style="text-align:right;" />
				</logic:notEqual></td>
            	</tr>

            	<tr> 
              	<td><strong> Percentual de Desconto de Multa Sem Restabelecimento:</strong></td>
				<td><logic:equal name="readOnly" value="true">
					<html:text
						property="percentualDescontoMultaSemRestabelecimentoInatividade"
						size="6" maxlength="6" tabindex="17"
						onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
						style="text-align:right;" readonly="true" />
				</logic:equal> <logic:notEqual name="readOnly" value="true">
					<html:text
						property="percentualDescontoMultaSemRestabelecimentoInatividade"
						size="6" maxlength="6" tabindex="17"
						onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
						style="text-align:right;" />
				</logic:notEqual></td>
            	</tr>

            	<tr> 
              	<td><strong> Percentual de Desconto de Multa Com Restabelecimento:</strong></td>
				<td><logic:equal name="readOnly" value="true">
					<html:text
						property="percentualDescontoMultaComRestabelecimentoInatividade"
						size="6" maxlength="6" tabindex="18"
						onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
						style="text-align:right;" readonly="true" />
				</logic:equal> <logic:notEqual name="readOnly" value="true">
					<html:text
						property="percentualDescontoMultaComRestabelecimentoInatividade"
						size="6" maxlength="6" tabindex="18"
						onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
						style="text-align:right;" />
				</logic:notEqual></td>
            	</tr>

				<tr>
					<td><strong> Desconto(s) por Inatividade Informado(s) </strong></td>
					<td align="right"><logic:equal name="readOnly" value="true">
						<input name="Button" type="button" class="bottonRightCol"
							value="Adicionar" align="right" tabindex="19"/>
					</logic:equal> <logic:notEqual name="readOnly" value="true">
						<input name="Button" type="button" class="bottonRightCol"
							value="Adicionar" align="right" tabindex="19"
							onclick="adicionarParcelamentoDescontoInatividade(document.forms[0])" />
					</logic:notEqual></td>
				</tr>

				<%int cont3 = 0;%>
				<tr>
					<td colspan="4">
					<table width="100%" border="0" bgcolor="90c7fc">
						<logic:empty name="collectionParcelamentoDescontoInatividade"
							scope="session">
							<tr bgcolor="#90c7fc">
								<td rowspan="2" align="center" width="10%"><strong>Remover</strong></td>
								<td rowspan="2" width="20%" align="center"><strong> Qtde. M�xima Meses da Lig. de �gua</strong></td>
								<td colspan="2" width="23%" align="center"><strong>Percentual de Desconto</strong></td>
								<td colspan="2" width="23%" align="center"><strong>Percentual de Desconto de Juros Mora</strong></td>
								<td colspan="2" width="23%" align="center"><strong>Percentual de Desconto de Multa</strong></td>
							</tr>
							<tr bgcolor="#cbe5fe">
								<td align="center"><strong>Sem Restab.</strong></td>
								<td align="center"><strong>Com Restab.</strong></td>
								<td align="center"><strong>Sem Restab.</strong></td>
								<td align="center"><strong>Com Restab.</strong></td>
								<td align="center"><strong>Sem Restab.</strong></td>
								<td align="center"><strong>Com Restab.</strong></td>
							</tr>
							<tr>
								<td align="center" width="10%" bgcolor="#ffffff">&nbsp;</td>
								<td align="center" width="20%" bgcolor="#ffffff">&nbsp;</td>
								<td align="center" width="12%" bgcolor="#ffffff">&nbsp;</td>
								<td align="center" width="12%" bgcolor="#ffffff">&nbsp;</td>
								<td align="center" width="12%" bgcolor="#ffffff">&nbsp;</td>
								<td align="center" width="12%" bgcolor="#ffffff">&nbsp;</td>
								<td align="center" width="12%" bgcolor="#ffffff">&nbsp;</td>
								<td align="center" width="12%" bgcolor="#ffffff">&nbsp;</td>
							</tr>
						</logic:empty>

						<logic:notEmpty name="collectionParcelamentoDescontoInatividade"
							scope="session">

							<%if (((Collection) session
					.getAttribute("collectionParcelamentoDescontoInatividade"))
					.size() <= ConstantesSistema.NUMERO_MAXIMO_REGISTROS_PERFIL_PARCELAMENTO) {%>
							<tr bgcolor="#90c7fc">
								<td rowspan="2" align="center" width="10%"><strong>Remover</strong></td>
								<td rowspan="2" width="20%" align="center"><strong> Qtde. M�xima Meses da Lig. de �gua</strong></td>
								<td colspan="2" width="23%" align="center"><strong>Percentual de Desconto</strong></td>
								<td colspan="2" width="23%" align="center"><strong>Percentual de Desconto de Juros Mora</strong></td>
								<td colspan="2" width="23%" align="center"><strong>Percentual de Desconto de Multa</strong></td>
							</tr>
							<tr bgcolor="#cbe5fe">
								<td align="center"><strong>Sem Restab.</strong></td>
								<td align="center"><strong>Com Restab.</strong></td>
								<td align="center"><strong>Sem Restab.</strong></td>
								<td align="center"><strong>Com Restab.</strong></td>
								<td align="center"><strong>Sem Restab.</strong></td>
								<td align="center"><strong>Com Restab.</strong></td>
							</tr>


							<logic:iterate name="collectionParcelamentoDescontoInatividade"
								id="parcelamentoDescontoInatividade"
								type="ParcelamentoDescontoInatividade">
								<%cont3 = cont3 + 1;
				if (cont3 % 2 == 0) {%>
								<tr bgcolor="#cbe5fe">
									<%} else {%>
								<tr bgcolor="#FFFFFF">
									<%}%>

									<td width="10%"><logic:equal name="readOnly" value="true">
										<div align="center"><font color="#333333"> <img width="14"
											height="14" border="0"
											src="<bean:message key="caminho.imagens"/>Error.gif" /> </font></div>
									</logic:equal> <logic:notEqual name="readOnly" value="true">
										<div align="center"><font color="#333333"> <img width="14"
											height="14" border="0"
											src="<bean:message key="caminho.imagens"/>Error.gif"
											onclick="javascript:document.forms[0].target='';if(confirm('Confirma remo��o?')){redirecionarSubmit('removerParcelamentoDescontoInatividadeActionAtualizar.do?quantidadeMaximaMesesInat=<bean:write name="parcelamentoDescontoInatividade" property="quantidadeMaximaMesesInatividade"/>');}" />
										</font></div>
									</logic:notEqual></td>

									<td width="20%" align="center">
									<div>${parcelamentoDescontoInatividade.quantidadeMaximaMesesInatividade}
									&nbsp;</div>
									</td>

									<td width="12%" align="center"><logic:equal name="readOnly"
										value="true">
										<input type="text"
											style="text-align: right;font-size: xx-small;" size="6"
											maxlength="6" align="center" onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
											name="vlSemRestInatividade<bean:write name="parcelamentoDescontoInatividade" property="ultimaAlteracao.time"/>"
											value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoInatividade.getPercentualDescontoSemRestabelecimento())%>"
											readonly="true" style="text-align:right;" />
									</logic:equal> <logic:notEqual name="readOnly" value="true">
										<input type="text"
											style="text-align: right;font-size: xx-small;" size="6"
											maxlength="6" align="center"
											name="vlSemRestInatividade<bean:write name="parcelamentoDescontoInatividade" property="ultimaAlteracao.time"/>"
											value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoInatividade.getPercentualDescontoSemRestabelecimento())%>"
											onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
											style="text-align:right;" />
									</logic:notEqual></td>

									<td width="12%" align="center"><logic:equal name="readOnly"
										value="true">
										<input type="text"
											style="text-align: right;font-size: xx-small;" size="6"
											maxlength="6" align="center" onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
											name="vlComRestInatividade<bean:write name="parcelamentoDescontoInatividade" property="ultimaAlteracao.time"/>"
											value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoInatividade.getPercentualDescontoComRestabelecimento())%>"
											readonly="true" style="text-align:right;" />
									</logic:equal> <logic:notEqual name="readOnly" value="true">
										<input type="text"
											style="text-align: right;font-size: xx-small;" size="6"
											maxlength="6" align="center"
											name="vlComRestInatividade<bean:write name="parcelamentoDescontoInatividade" property="ultimaAlteracao.time"/>"
											value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoInatividade.getPercentualDescontoComRestabelecimento())%>"
											onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
											style="text-align:right;" />
									</logic:notEqual></td>

									<td width="12%" align="center"><logic:equal name="readOnly"
										value="true">
										<input type="text"
											style="text-align: right;font-size: xx-small;" size="6"
											maxlength="6" align="center" onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
											name="vlJurosMoraSemRestInatividade<bean:write name="parcelamentoDescontoInatividade" property="ultimaAlteracao.time"/>"
											value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoInatividade.getPercentualDescontoJurosMoraSemRestabelecimento())%>"
											readonly="true" style="text-align:right;" />
									</logic:equal> <logic:notEqual name="readOnly" value="true">
										<input type="text"
											style="text-align: right;font-size: xx-small;" size="6"
											maxlength="6" align="center"
											name="vlJurosMoraSemRestInatividade<bean:write name="parcelamentoDescontoInatividade" property="ultimaAlteracao.time"/>"
											value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoInatividade.getPercentualDescontoJurosMoraSemRestabelecimento())%>"
											onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
											style="text-align:right;" />
									</logic:notEqual></td>

									<td width="12%" align="center"><logic:equal name="readOnly"
										value="true">
										<input type="text"
											style="text-align: right;font-size: xx-small;" size="6"
											maxlength="6" align="center" onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
											name="vlJurosMoraComRestInatividade<bean:write name="parcelamentoDescontoInatividade" property="ultimaAlteracao.time"/>"
											value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoInatividade.getPercentualDescontoJurosMoraComRestabelecimento())%>"
											readonly="true" style="text-align:right;" />
									</logic:equal> <logic:notEqual name="readOnly" value="true">
										<input type="text"
											style="text-align: right;font-size: xx-small;" size="6"
											maxlength="6" align="center"
											name="vlJurosMoraComRestInatividade<bean:write name="parcelamentoDescontoInatividade" property="ultimaAlteracao.time"/>"
											value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoInatividade.getPercentualDescontoJurosMoraComRestabelecimento())%>"
											onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
											style="text-align:right;" />
									</logic:notEqual></td>
									
									<td width="12%" align="center"><logic:equal name="readOnly"
										value="true">
										<input type="text"
											style="text-align: right;font-size: xx-small;" size="6"
											maxlength="6" align="center" onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
											name="vlMultaSemRestInatividade<bean:write name="parcelamentoDescontoInatividade" property="ultimaAlteracao.time"/>"
											value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoInatividade.getPercentualDescontoMultaSemRestabelecimento())%>"
											readonly="true" style="text-align:right;" />
									</logic:equal> <logic:notEqual name="readOnly" value="true">
										<input type="text"
											style="text-align: right;font-size: xx-small;" size="6"
											maxlength="6" align="center"
											name="vlMultaSemRestInatividade<bean:write name="parcelamentoDescontoInatividade" property="ultimaAlteracao.time"/>"
											value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoInatividade.getPercentualDescontoMultaSemRestabelecimento())%>"
											onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
											style="text-align:right;" />
									</logic:notEqual></td>

									<td width="12%" align="center"><logic:equal name="readOnly"
										value="true">
										<input type="text"
											style="text-align: right;font-size: xx-small;" size="6"
											maxlength="6" align="center" onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
											name="vlMultaComRestInatividade<bean:write name="parcelamentoDescontoInatividade" property="ultimaAlteracao.time"/>"
											value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoInatividade.getPercentualDescontoMultaComRestabelecimento())%>"
											readonly="true" style="text-align:right;" />
									</logic:equal> <logic:notEqual name="readOnly" value="true">
										<input type="text"
											style="text-align: right;font-size: xx-small;" size="6"
											maxlength="6" align="center"
											name="vlMultaComRestInatividade<bean:write name="parcelamentoDescontoInatividade" property="ultimaAlteracao.time"/>"
											value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoInatividade.getPercentualDescontoMultaComRestabelecimento())%>"
											onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
											style="text-align:right;" />
									</logic:notEqual></td>

								</tr>
							</logic:iterate>

							<%} else {%>

							<tr bgcolor="#90c7fc">
								<td rowspan="2" align="center" width="10%"><strong>Remover</strong></td>
								<td rowspan="2" width="20%" align="center"><strong> Qtde. M�xima Meses da Lig. de �gua</strong></td>
								<td colspan="2" width="23%" align="center"><strong>Percentual de Desconto</strong></td>
								<td colspan="2" width="23%" align="center"><strong>Percentual de Desconto de Juros Mora</strong></td>
								<td colspan="2" width="23%" align="center"><strong>Percentual de Desconto de Multa</strong></td>
							</tr>
							<tr bgcolor="#cbe5fe">
								<td align="center"><strong>Sem Restab.</strong></td>
								<td align="center"><strong>Com Restab.</strong></td>
								<td align="center"><strong>Sem Restab.</strong></td>
								<td align="center"><strong>Com Restab.</strong></td>
								<td align="center"><strong>Sem Restab.</strong></td>
								<td align="center"><strong>Com Restab.</strong></td>
							</tr>

							<tr>
								<td height="100" colspan="8">
								<div style="width: 100%; height: 100%; overflow: auto;">
								<table width="100%">
									<logic:iterate name="collectionParcelamentoDescontoInatividade"
										id="parcelamentoDescontoInatividade"
										type="ParcelamentoDescontoInatividade">
										<%cont3 = cont3 + 1;
				if (cont3 % 2 == 0) {%>
										<tr bgcolor="#cbe5fe">
											<%} else {%>
										<tr bgcolor="#FFFFFF">
											<%}%>

											<td width="10%"><logic:equal name="readOnly" value="true">
												<div align="center"><font color="#333333"> <img width="14"
													height="14" border="0"
													src="<bean:message key="caminho.imagens"/>Error.gif" /> </font></div>
											</logic:equal> <logic:notEqual name="readOnly" value="true">
												<div align="center"><font color="#333333"> <img width="14"
													height="14" border="0"
													src="<bean:message key="caminho.imagens"/>Error.gif"
													onclick="javascript:document.forms[0].target='';if(confirm('Confirma remo��o?')){redirecionarSubmit('removerParcelamentoDescontoInatividadeActionAtualizar.do?quantidadeMaximaMesesInat=<bean:write name="parcelamentoDescontoInatividade" property="quantidadeMaximaMesesInatividade"/>');}" />
												</font></div>
											</logic:notEqual></td>

											<td width="20%" align="center">
											<div>${parcelamentoDescontoInatividade.quantidadeMaximaMesesInatividade}
											&nbsp;</div>
											</td>

											<td width="12%" align="center"><logic:equal name="readOnly"
												value="true">
												<input type="text"
													style="text-align: right;font-size: xx-small;" size="6"
													maxlength="6" align="center" onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
													name="vlSemRestInatividade<bean:write name="parcelamentoDescontoInatividade" property="ultimaAlteracao.time"/>"
													value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoInatividade.getPercentualDescontoSemRestabelecimento())%>"
													readonly="true" style="text-align:right;" />
											</logic:equal> <logic:notEqual name="readOnly" value="true">
												<input type="text"
													style="text-align: right;font-size: xx-small;" size="6"
													maxlength="6" align="center"
													name="vlSemRestInatividade<bean:write name="parcelamentoDescontoInatividade" property="ultimaAlteracao.time"/>"
													value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoInatividade.getPercentualDescontoSemRestabelecimento())%>"
													onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
													style="text-align:right;" />
											</logic:notEqual></td>

											<td width="12%" align="center"><logic:equal name="readOnly"
												value="true">
												<input type="text"
													style="text-align: right;font-size: xx-small;" size="6"
													maxlength="6" align="center" onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
													name="vlComRestInatividade<bean:write name="parcelamentoDescontoInatividade" property="ultimaAlteracao.time"/>"
													value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoInatividade.getPercentualDescontoComRestabelecimento())%>"
													readonly="true" style="text-align:right;" />
											</logic:equal> <logic:notEqual name="readOnly" value="true">
												<input type="text"
													style="text-align: right;font-size: xx-small;" size="6"
													maxlength="6" align="center"
													name="vlComRestInatividade<bean:write name="parcelamentoDescontoInatividade" property="ultimaAlteracao.time"/>"
													value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoInatividade.getPercentualDescontoComRestabelecimento())%>"
													onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
													style="text-align:right;" />
											</logic:notEqual></td>

											<td width="12%" align="center"><logic:equal name="readOnly"
												value="true">
												<input type="text"
													style="text-align: right;font-size: xx-small;" size="6"
													maxlength="6" align="center" onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
													name="vlJurosMoraSemRestInatividade<bean:write name="parcelamentoDescontoInatividade" property="ultimaAlteracao.time"/>"
													value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoInatividade.getPercentualDescontoJurosMoraSemRestabelecimento())%>"
													readonly="true" style="text-align:right;" />
											</logic:equal> <logic:notEqual name="readOnly" value="true">
												<input type="text"
													style="text-align: right;font-size: xx-small;" size="6"
													maxlength="6" align="center"
													name="vlJurosMoraSemRestInatividade<bean:write name="parcelamentoDescontoInatividade" property="ultimaAlteracao.time"/>"
													value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoInatividade.getPercentualDescontoJurosMoraSemRestabelecimento())%>"
													onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
													style="text-align:right;" />
											</logic:notEqual></td>

											<td width="12%" align="center"><logic:equal name="readOnly"
												value="true">
												<input type="text"
													style="text-align: right;font-size: xx-small;" size="6"
													maxlength="6" align="center" onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
													name="vlJurosMoraComRestInatividade<bean:write name="parcelamentoDescontoInatividade" property="ultimaAlteracao.time"/>"
													value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoInatividade.getPercentualDescontoJurosMoraComRestabelecimento())%>"
													readonly="true" style="text-align:right;" />
											</logic:equal> <logic:notEqual name="readOnly" value="true">
												<input type="text"
													style="text-align: right;font-size: xx-small;" size="6"
													maxlength="6" align="center"
													name="vlJurosMoraComRestInatividade<bean:write name="parcelamentoDescontoInatividade" property="ultimaAlteracao.time"/>"
													value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoInatividade.getPercentualDescontoJurosMoraComRestabelecimento())%>"
													onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
													style="text-align:right;" />
											</logic:notEqual></td>

											<td width="12%" align="center"><logic:equal name="readOnly"
												value="true">
												<input type="text"
													style="text-align: right;font-size: xx-small;" size="6"
													maxlength="6" align="center" onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
													name="vlMultaSemRestInatividade<bean:write name="parcelamentoDescontoInatividade" property="ultimaAlteracao.time"/>"
													value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoInatividade.getPercentualDescontoMultaSemRestabelecimento())%>"
													readonly="true" style="text-align:right;" />
											</logic:equal> <logic:notEqual name="readOnly" value="true">
												<input type="text"
													style="text-align: right;font-size: xx-small;" size="6"
													maxlength="6" align="center"
													name="vlMultaSemRestInatividade<bean:write name="parcelamentoDescontoInatividade" property="ultimaAlteracao.time"/>"
													value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoInatividade.getPercentualDescontoMultaSemRestabelecimento())%>"
													onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
													style="text-align:right;" />
											</logic:notEqual></td>

											<td width="12%" align="center"><logic:equal name="readOnly"
												value="true">
												<input type="text"
													style="text-align: right;font-size: xx-small;" size="6"
													maxlength="6" align="center" onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
													name="vlMultaComRestInatividade<bean:write name="parcelamentoDescontoInatividade" property="ultimaAlteracao.time"/>"
													value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoInatividade.getPercentualDescontoMultaComRestabelecimento())%>"
													readonly="true" style="text-align:right;" />
											</logic:equal> <logic:notEqual name="readOnly" value="true">
												<input type="text"
													style="text-align: right;font-size: xx-small;" size="6"
													maxlength="6" align="center"
													name="vlMultaComRestInatividade<bean:write name="parcelamentoDescontoInatividade" property="ultimaAlteracao.time"/>"
													value="<%="" + Util.formatarMoedaReal(parcelamentoDescontoInatividade.getPercentualDescontoMultaComRestabelecimento())%>"
													onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"
													style="text-align:right;" />
											</logic:notEqual></td>
										</tr>
									</logic:iterate>
								</table>
								</div>
								</td>
							</tr>
							<%}%>
						</logic:notEmpty>
					</table>
					</td>
				</tr>


				<%-- final da tabela de Descontos por Inatividade --%>



				<tr>
					<td><strong> <font color="#FF0000"></font></strong></td>
					<td align="right">
					<div align="left"><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios</div>
					</td>
				</tr>


				<tr>


					<td><logic:present name="voltar">
						<logic:equal name="voltar" value="filtrar">
							<input name="Button" type="button" class="bottonRightCol"
								value="Voltar" align="left"
								onclick="window.location.href='<html:rewrite page="/exibirFiltrarPerfilParcelamentoAction.do?desfazer=N"/>'">
						</logic:equal>
						<logic:equal name="voltar" value="manter">
							<input name="Button" type="button" class="bottonRightCol"
								value="Voltar" align="left"
								onclick="window.location.href='<html:rewrite page="/exibirManterPerfilParcelamentoAction.do"/>'">
						</logic:equal>
					</logic:present> <logic:notPresent name="voltar">
						<input name="Button" type="button" class="bottonRightCol"
							value="Voltar" align="left"
							onclick="window.location.href='<html:rewrite page="/exibirManterPerfilParcelamentoAction.do"/>'">
					</logic:notPresent> <logic:equal name="readOnly" value="true">
						<input name="Button" type="button" class="bottonRightCol"
							value="Desfazer" align="left">
					</logic:equal> <logic:notEqual name="readOnly" value="true">
						<input name="Button" type="button" class="bottonRightCol"
							value="Desfazer" align="left"
							onclick="window.location.href='<html:rewrite page="/exibirAtualizarPerfilParcelamentoAction.do?desfazer=S"/>'">
					</logic:notEqual> <input name="Button" type="button"
						class="bottonRightCol" value="Cancelar" align="left"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"></td>
					<td align="right">
					  <logic:equal name="readOnly" value="true">
						<input name="Button" type="button" class="bottonRightCol" value="Atualizar" align="right">
					  </logic:equal> 
					<logic:notEqual name="readOnly" value="true">
					    <gcom:controleAcessoBotao name="Button" value="Atualizar" onclick="document.forms[0].target='';validarForm(document.AtualizarParcelamentoPerfilActionForm)" url="atualizarPerfilParcelamentoAction.do" align="right"/>
						<%-- <input name="Button" type="button" class="bottonRightCol" value="Atualizar" align="right" onClick="document.forms[0].target='';validarForm(document.AtualizarParcelamentoPerfilActionForm)"> --%>
					</logic:notEqual></td>
				</tr>

			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>
	<%@ include file="/jsp/util/rodape.jsp"%>




</html:form>

</body>
<!-- perfil_parcelamento_atualizar.jsp -->
</html:html>
