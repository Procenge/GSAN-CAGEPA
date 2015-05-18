<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="gcom.util.Util" %>
<%@ page import="gcom.cobranca.parcelamento.ParcelamentoQuantidadePrestacao" %>
<%@ page import="gcom.cobranca.parcelamento.ParcelamentoQuantidadePrestacaoHelper" %>
<%@ page import="gcom.cobranca.parcelamento.ParcelamentoFaixaValor" %>
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
	
<style type="text/css" >
.gridPercentualPorFaixaValor {}
.gridQuantidadeMaximaPrestacoes {}
.gridQtdMaxPrestacao {}
</style>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="InserirPrestacoesParcelamentoPerfilActionForm" />
<script language="JavaScript" src="<bean:message key="caminho.js"/>jquery-1.4.2.min.js"></script>
<script language="JavaScript">
<!-- Begin

	//window.onbeforeunload=function(){return "Isto acarretará em perda de dados."}; 
	//window.onbeforeunload=fecharEsta;
	//var ret;
	//window.onbeforeunload = function(ret){return ret = "Deseja realmente não Adicionar Informações do \nParcelamento por Quantidade de Reparcelamentos ?"; fechando(ret);};

	//function fechando(r) {
		//fechar(document.forms[0]);
	//}

	//window.onbeforeunload=function fecharEsta(event) {

			   
		//retorno = false;
		
	   // opcaoEscolhida = confirm("Deseja realmente não Adicionar Informações do \nParcelamento por Quantidade de Reparcelamentos ?");

	   // if (opcaoEscolhida) {
	    //	retorno = true;
	    //	fechar(document.forms[0]);
		//} else {
		//	retorno = false;
		//}

	 // return retorno;
	//}

	
	
	
	$(document).ready(function(){

		//desabilitarCampoQtdPrestacao(document.forms[0]);
		
		desabilitarCampoEntradaParcelamento(document.forms[0]);
		desabilitarCampoPercentualPorFaixaValor(document.forms[0]);

		//Esse
	   $(".gridPercentualPorFaixaValor").each(function (i) {
	    	desabilitarCampoEmGridPercentualPorFaixaValor(this);
	    });
	    
	    

	    //$('.centercoltext a').each(function (i) {
	    	//var nomeObjetoAntigo = this.name;
	    	//timeNovoElemento = nomeObjetoAntigo.replace('consultar','');
	    	//desabilitarCamposQuantidadeMaximaPrestacoes(timeNovoElemento);
	    	//desabilitarCampoEmGridQuantidadeMaximaPrestacoes(this);
	    //});
	    
	    

	    // Esse
	    $(".gridQuantidadeMaximaPrestacoes").each(function (i) {
	    	desabilitarCampoEmGridQuantidadeMaximaPrestacoes(this);
	    });
	   
	});

	//function atualizarCampos() {
		//desabilitarCampoQtdPrestacao(document.forms[0]);
		//desabilitarCampoEntradaParcelamento(document.forms[0]);
		//desabilitarCampoPercentualPorFaixaValor(document.forms[0]);
	//}

	function isBrancoOuNulo(obj){
		if(obj == null || obj == undefined || obj == 'undefined' || obj == ''){
			return true;
		}else{
			return false;
		}
	}

	function desabilitarCampoEmGridPercentualPorFaixaValor(objeto){
		   
	    var nomeObjetoAntigo = objeto.name;
	    var prefixo = nomeObjetoAntigo.substring(0, 4);
	    var nomeNovoElemento = '';
	   
	    if (prefixo == 'perc') {
	        nomeNovoElemento = 'vlFixoEntrada' + nomeObjetoAntigo.replace('perc','');
	    } else {
	        nomeNovoElemento = 'perc' + nomeObjetoAntigo.replace('vlFixoEntrada','');
	    }

	    if (isBrancoOuNulo($.trim($("input[name="+ nomeObjetoAntigo +"]").val())) &&
	    		isBrancoOuNulo($.trim($("input[name="+ nomeNovoElemento +"]").val()))) {

	    	$("input[name=" + nomeObjetoAntigo + "]").attr('disabled', false);
	        $("input[name="+ nomeObjetoAntigo +"]").val('');
	        $("input[name="+ nomeNovoElemento +"]").attr('disabled', false);
	        $("input[name="+ nomeNovoElemento +"]").val('');

	    } else if (!isBrancoOuNulo($.trim($("input[name="+ nomeObjetoAntigo +"]").val()))) {
	        $("input[name=" + nomeObjetoAntigo + "]").attr('disabled', false);
	        $("input[name="+ nomeNovoElemento +"]").attr('disabled', true);
	        $("input[name="+ nomeNovoElemento +"]").val('');
	    } else {
	        $("input[name="+ nomeNovoElemento +"]").attr('disabled', false);
	        $("input[name=" + nomeObjetoAntigo + "]").attr('disabled', true);
	        $("input[name="+ nomeObjetoAntigo +"]").val('');
	    }
	}

	function desabilitarCampoEmGridQuantidadeMaximaPrestacoes(objeto){
		
	    var nomeObjetoAntigo = objeto.name;
	    var prefixo = nomeObjetoAntigo.substring(0, 7);
	    var timeNovoElemento = '';

	   	if (prefixo == 'txJuros') {
	   		timeNovoElemento = nomeObjetoAntigo.replace('txJuros','');
	   	} else if (prefixo == 'vlMinim') {
	    	timeNovoElemento = nomeObjetoAntigo.replace('vlMinimoEntrada','');
	    } else if (prefixo == 'percTar') {
	    	timeNovoElemento = nomeObjetoAntigo.replace('percTarMinImovel','');
	    } else if (prefixo == 'percMin') {
	    	timeNovoElemento = nomeObjetoAntigo.replace('percMinEntrada','');
	    } else if (prefixo == 'consult') {
	    	timeNovoElemento = nomeObjetoAntigo.replace('consultar','');
		}

	   	var linkConsultar = $('.gridQtdMaxPrestacao tr td:nth-child(10) a[name=consultar' + timeNovoElemento + ']');
	   	
	   	if (linkConsultar.attr("name") != null && linkConsultar.attr("name") != 'undefined' && linkConsultar.attr("name") != undefined && linkConsultar.attr("name") != '') {

			// Tem o link Consultar
	   		
	   		$("input[name=" + "vlMinimoEntrada" + timeNovoElemento + "]").attr('disabled', true);
	        $("input[name="+ "vlMinimoEntrada" + timeNovoElemento +"]").val('');

	        $("input[name=" + "percTarMinImovel" + timeNovoElemento + "]").attr('disabled', true);
	        $("input[name="+ "percTarMinImovel" + timeNovoElemento +"]").val('');

	        $("input[name=" + "percMinEntrada" + timeNovoElemento + "]").attr('disabled', true);
	        $("input[name="+ "percMinEntrada" + timeNovoElemento +"]").val('');

	        $("input[type=radio][name=indicadorEntradaParcelamento" + timeNovoElemento + "]:eq(0)").attr('checked', false);
	        $("input[type=radio][name=indicadorEntradaParcelamento" + timeNovoElemento + "]:eq(1)").attr('checked', false);
	        $("input[type=radio][name=indicadorEntradaParcelamento" + timeNovoElemento + "][value=1]").attr('disabled', true);
	        $("input[type=radio][name=indicadorEntradaParcelamento" + timeNovoElemento + "][value=2]").attr('disabled', true);
		} else {

			// Não tem o link consultar
			
		    if (isBrancoOuNulo($.trim($("input[name="+ "vlMinimoEntrada" + timeNovoElemento +"]").val())) &&
		    		isBrancoOuNulo($.trim($("input[name="+ "percTarMinImovel" + timeNovoElemento +"]").val())) && 
		    		isBrancoOuNulo($.trim($("input[name="+ "percMinEntrada" + timeNovoElemento +"]").val())) ) {
	
				$("input[name=" + "vlMinimoEntrada" + timeNovoElemento + "]").attr('disabled', false);
		        $("input[name="+ "vlMinimoEntrada" + timeNovoElemento +"]").val('');
	
		        $("input[name=" + "percTarMinImovel" + timeNovoElemento + "]").attr('disabled', false);
		        $("input[name="+ "percTarMinImovel" + timeNovoElemento +"]").val('');
	
		        $("input[name=" + "percMinEntrada" + timeNovoElemento + "]").attr('disabled', false);
		        $("input[name="+ "percMinEntrada" + timeNovoElemento +"]").val('');
	
		        $("input[type=radio][name=indicadorEntradaParcelamento" + timeNovoElemento + "][value=1]").attr('disabled', false);
		        $("input[type=radio][name=indicadorEntradaParcelamento" + timeNovoElemento + "][value=2]").attr('disabled', false);
						
			} else if (!isBrancoOuNulo($.trim($("input[name="+ "vlMinimoEntrada" + timeNovoElemento +"]").val()))) {
				
				$("input[name=" + "vlMinimoEntrada" + timeNovoElemento + "]").attr('disabled', false);
				
				$("input[name=" + "percTarMinImovel" + timeNovoElemento + "]").attr('disabled', true);
		        $("input[name="+ "percTarMinImovel" + timeNovoElemento +"]").val('');
	
		        $("input[name=" + "percMinEntrada" + timeNovoElemento + "]").attr('disabled', true);
		        $("input[name="+ "percMinEntrada" + timeNovoElemento +"]").val('');

		        $("input[type=radio][name=indicadorEntradaParcelamento" + timeNovoElemento + "]:eq(0)").attr('checked', false);
		        $("input[type=radio][name=indicadorEntradaParcelamento" + timeNovoElemento + "]:eq(1)").attr('checked', false);
		        
		        $("input[type=radio][name=indicadorEntradaParcelamento" + timeNovoElemento + "][value=1]").attr('disabled', true);
		        $("input[type=radio][name=indicadorEntradaParcelamento" + timeNovoElemento + "][value=2]").attr('disabled', true);
				
			} else if (!isBrancoOuNulo($.trim($("input[name="+ "percTarMinImovel" + timeNovoElemento +"]").val()))) {
	
				$("input[name=" + "percTarMinImovel" + timeNovoElemento + "]").attr('disabled', false);
	
				$("input[name=" + "vlMinimoEntrada" + timeNovoElemento + "]").attr('disabled', true);
		        $("input[name="+ "vlMinimoEntrada" + timeNovoElemento +"]").val('');
	
		        $("input[name=" + "percMinEntrada" + timeNovoElemento + "]").attr('disabled', true);
		        $("input[name="+ "percMinEntrada" + timeNovoElemento +"]").val('');

		        $("input[type=radio][name=indicadorEntradaParcelamento" + timeNovoElemento + "]:eq(0)").attr('checked', false);
		        $("input[type=radio][name=indicadorEntradaParcelamento" + timeNovoElemento + "]:eq(1)").attr('checked', false);
		    	
		        $("input[type=radio][name=indicadorEntradaParcelamento" + timeNovoElemento + "][value=1]").attr('disabled', true);
		        $("input[type=radio][name=indicadorEntradaParcelamento" + timeNovoElemento + "][value=2]").attr('disabled', true);
	
			} else if (!isBrancoOuNulo($.trim($("input[name="+ "percMinEntrada" + timeNovoElemento +"]").val()))) {
	
				$("input[name=" + "percMinEntrada" + timeNovoElemento + "]").attr('disabled', false);
				
				$("input[name=" + "vlMinimoEntrada" + timeNovoElemento + "]").attr('disabled', true);
		        $("input[name="+ "vlMinimoEntrada" + timeNovoElemento +"]").val('');
	
		        $("input[name=" + "percTarMinImovel" + timeNovoElemento + "]").attr('disabled', true);
		        $("input[name="+ "percTarMinImovel" + timeNovoElemento +"]").val('');
	
				$("input[type=radio][name=indicadorEntradaParcelamento" + timeNovoElemento + "][value=1]").attr('disabled', false);
		        $("input[type=radio][name=indicadorEntradaParcelamento" + timeNovoElemento + "][value=2]").attr('disabled', false);
			}

		}

	}

	function desabilitarCamposQuantidadeMaximaPrestacoes(time) {
	 	$("input[name=" + "vlMinimoEntrada" + timeNovoElemento + "]").attr('disabled', true);
        $("input[name="+ "vlMinimoEntrada" + timeNovoElemento +"]").val('');

        $("input[name=" + "percTarMinImovel" + timeNovoElemento + "]").attr('disabled', true);
        $("input[name="+ "percTarMinImovel" + timeNovoElemento +"]").val('');

        $("input[name=" + "percMinEntrada" + timeNovoElemento + "]").attr('disabled', true);
        $("input[name="+ "percMinEntrada" + timeNovoElemento +"]").val('');

        $("input[type=radio][name=indicadorEntradaParcelamento" + timeNovoElemento + "]:eq(0)").attr('checked', false);
        $("input[type=radio][name=indicadorEntradaParcelamento" + timeNovoElemento + "]:eq(1)").attr('checked', false);

        $("input[type=radio][name=indicadorEntradaParcelamento" + timeNovoElemento + "][value=1]").attr('disabled', true);
        $("input[type=radio][name=indicadorEntradaParcelamento" + timeNovoElemento + "][value=2]").attr('disabled', true);
	 }

	function desabilitarCampoQtdPrestacao(form){

		if (isBrancoOuNulo(form.quantidadeMaximaPrestacao.value) && isBrancoOuNulo(form.quantidadeMaxPrestacaoEspecial.value)){
	
			form.quantidadeMaximaPrestacao.value = "";
			form.quantidadeMaximaPrestacao.disabled = false;
			
			form.quantidadeMaxPrestacaoEspecial.value = "";
			form.quantidadeMaxPrestacaoEspecial.disabled = false;
			
		} else if (!isBrancoOuNulo(form.quantidadeMaximaPrestacao.value)){
	
			form.quantidadeMaxPrestacaoEspecial.value = "";
			form.quantidadeMaxPrestacaoEspecial.disabled = true;
			
		} else if (!isBrancoOuNulo(form.quantidadeMaxPrestacaoEspecial.value)){
	
			form.quantidadeMaximaPrestacao.value = "";
			form.quantidadeMaximaPrestacao.disabled = true;
			
		}

		habilitaBotaoAdicionar();
	}

	function desabilitarCampoEntradaParcelamento(form){
		
		if (isBrancoOuNulo(form.valorMinimoEntrada.value) && isBrancoOuNulo(form.percentualTarifaMinimaImovel.value) && isBrancoOuNulo(form.percentualMinimoEntrada.value)){

			form.valorMinimoEntrada.value = "";
			form.valorMinimoEntrada.disabled = false;
			
			form.percentualTarifaMinimaImovel.value = "";
			form.percentualTarifaMinimaImovel.disabled = false;

			form.percentualMinimoEntrada.value = "";
			form.percentualMinimoEntrada.disabled = false;
			
			form.indicadorOpcoesEntradaParcelamento[0].disabled=false;
			form.indicadorOpcoesEntradaParcelamento[1].disabled=false;
			
		} else if (!isBrancoOuNulo(form.valorMinimoEntrada.value)){

			form.percentualTarifaMinimaImovel.value = "";
			form.percentualTarifaMinimaImovel.disabled = true;

			form.percentualMinimoEntrada.value = "";
			form.percentualMinimoEntrada.disabled = true;

			$("input[type=radio][name=indicadorOpcoesEntradaParcelamento]:eq(0)").attr('checked', false);
			$("input[type=radio][name=indicadorOpcoesEntradaParcelamento]:eq(1)").attr('checked', false);
			form.indicadorOpcoesEntradaParcelamento[0].disabled=true;
			form.indicadorOpcoesEntradaParcelamento[1].disabled=true;
			
		} else if (!isBrancoOuNulo(form.percentualTarifaMinimaImovel.value)){

			form.valorMinimoEntrada.value = "";
			form.valorMinimoEntrada.disabled = true;
			
			form.percentualMinimoEntrada.value = "";
			form.percentualMinimoEntrada.disabled = true;

			$("input[type=radio][name=indicadorOpcoesEntradaParcelamento]:eq(0)").attr('checked', false);
			$("input[type=radio][name=indicadorOpcoesEntradaParcelamento]:eq(1)").attr('checked', false);
			form.indicadorOpcoesEntradaParcelamento[0].disabled=true;
			form.indicadorOpcoesEntradaParcelamento[1].disabled=true;

		} else if (!isBrancoOuNulo(form.percentualMinimoEntrada.value)){

			form.valorMinimoEntrada.value = "";
			form.valorMinimoEntrada.disabled = true;
			
			form.percentualTarifaMinimaImovel.value = "";
			form.percentualTarifaMinimaImovel.disabled = true;

			form.indicadorOpcoesEntradaParcelamento[0].disabled=false;
			form.indicadorOpcoesEntradaParcelamento[1].disabled=false;
		}

		habilitaBotaoAdicionar();
		
	}

	function desabilitarCampoPercentualPorFaixaValor(form){
		if (isBrancoOuNulo(form.valorMaxPercFaixaValor.value)){
			// Campo em Branco

			form.indicadorOpcoesPercentualFaixaValor[0].disabled=true;
			form.indicadorOpcoesPercentualFaixaValor[1].disabled=true;
			
			form.valorFixoEntrada.value = "";
			form.valorFixoEntrada.disabled = true;

			form.percentualPercFaixaValor.value = "";
			form.percentualPercFaixaValor.disabled = true;

		} else {
			// Campo com dados
			
			form.indicadorOpcoesPercentualFaixaValor[0].disabled=false;
			form.indicadorOpcoesPercentualFaixaValor[1].disabled=false;

			if (isBrancoOuNulo(form.valorFixoEntrada.value) && isBrancoOuNulo(form.percentualPercFaixaValor.value)) {

				form.percentualPercFaixaValor.value = "";
				form.percentualPercFaixaValor.disabled = false;
				form.valorFixoEntrada.value = "";
				form.valorFixoEntrada.disabled = false;
				
			} else if (!isBrancoOuNulo(form.valorFixoEntrada.value)){
				
				form.percentualPercFaixaValor.value = "";
				form.percentualPercFaixaValor.disabled = true;
				
			} else if (!isBrancoOuNulo(form.percentualPercFaixaValor.value)){
				
				form.valorFixoEntrada.value = "";
				form.valorFixoEntrada.disabled = true;
			}
		}

		// Controla Botão
		adicionarPercentualExibir();

		habilitaBotaoAdicionar();
	
	}

	function abrirPopupFaixaValor(altura, largura, qtdeMaximaPrestacao){
		abrirPopupDeNome('exibirConsultarPercentualFaixaValorPopupAction.do?primeiraVez=S&qtdeMaximaPrestacao='+ qtdeMaximaPrestacao, altura, largura, 'popupFaixaValor', '');
	}

	function adicionarPercentualFaixaValor(form){

		//document.forms[0].target='';
		//var form = document.forms[0];
		var submeter = true;

		if (isBrancoOuNulo(form.valorMaxPercFaixaValor.value)) {
			alert('Informe Valor Mínimo');
			submeter = false;
		} else if (isBrancoOuNulo(form.valorFixoEntrada.value) && isBrancoOuNulo(form.percentualPercFaixaValor.value)) {
			alert('Informe Valor Fixo de Entrada ou\nPercentual');
			submeter = false;
		} else if (!isBrancoOuNulo(form.valorFixoEntrada.value) && !isBrancoOuNulo(form.percentualPercFaixaValor.value)){
			alert('Informe Valor Fixo de Entrada ou\nPercentual');
			submeter = false;
		} else if (form.indicadorOpcoesPercentualFaixaValor[0] == null && form.indicadorOpcoesPercentualFaixaValor[1] == null){
			alert('Informe Opções Entrada - Percentual por Faixa de Valor');
			submeter = false;
		} else if ((form.indicadorOpcoesPercentualFaixaValor[0] != null && form.indicadorOpcoesPercentualFaixaValor[0].checked == false) && 
				(form.indicadorOpcoesPercentualFaixaValor[1] != null && form.indicadorOpcoesPercentualFaixaValor[1].checked == false)){
			alert('Informe Opções Entrada - Percentual por Faixa de Valor');
			submeter = false;
		}
		
		if (submeter) {
			//redirecionarSubmit('exibirAdicionarPercentualFaixaValorPopupAction.do?primeiraVez=S');
			//redirecionarSubmit('exibirInserirPrestacoesParcelamentoPerfilAction.do?exibirAdicionarPercentualFaixaValorPopupAction=S&primeiraVez=S');
			document.forms[0].target='';
		    form.action = "exibirInserirPrestacoesParcelamentoPerfilAction.do?exibirAdicionarPercentualFaixaValorPopupAction=S&primeiraVez=S";
		    submeterFormPadrao(form);
		}
	}

	function desabilitaPercentualTarifaMax () {
		var form = document.forms[0];
		if (form.percentualMinimoEntrada.value == ''){
			form.percentualTarifaMinimaImovel.disabled = false;
			//desabilita btão Adicionar Percentual Por Faixa de Valor
			form.adicionarPercentual.disabled = false;
		}else{
			form.percentualTarifaMinimaImovel.disabled = true;
			//habilita btão Adicionar Percentual Por Faixa de Valor
			form.adicionarPercentual.disabled = true;
		}
	}
	
	function desabilitaPercentualValoDebito () {
		var form = document.forms[0];
		if (form.percentualTarifaMinimaImovel.value == ''){
			//form.percentualMinimoEntrada.disabled = false;
			//desabilita btão Adicionar Percentual Por Faixa de Valor
			form.adicionarPercentual.disabled = false;
		}else{
			//form.percentualMinimoEntrada.disabled = true;
			//habilita btão Adicionar Percentual Por Faixa de Valor
			form.adicionarPercentual.disabled = true;
		}
	}
	
	function desabilitaBotaoAdicionarFaixaValor() {
		var form = document.forms[0];
		if (form.valorMinimoEntrada.value == ''){
			//form.percentualMinimoEntrada.disabled = false;
			//desabilita btão Adicionar Percentual Por Faixa de Valor
			form.adicionarPercentual.disabled = false;
		}else{
			//form.percentualMinimoEntrada.disabled = true;
			//habilita btão Adicionar Percentual Por Faixa de Valor
			form.adicionarPercentual.disabled = true;
		}
	}

	function adicionarPercentualExibir() {
		var form = document.forms[0];
		
		if (isBrancoOuNulo(form.valorMaxPercFaixaValor.value)){
			// Campo em Branco
			form.adicionarPercentual.disabled = true;

		} else {
			// Campo com dados
			
			if (isBrancoOuNulo(form.valorFixoEntrada.value) && isBrancoOuNulo(form.percentualPercFaixaValor.value)) {
				form.adicionarPercentual.disabled = true;
				
			} else if (!isBrancoOuNulo(form.valorFixoEntrada.value)){
				form.adicionarPercentual.disabled = false;
				
			} else if (!isBrancoOuNulo(form.percentualPercFaixaValor.value)){
				form.adicionarPercentual.disabled = false;
			}
		}
	}
	
	function desabilitaPercentualTarifaMinimaImovelTabela (percentualMinimoEntrada,percentualTarifaMinimaImovel) {
	
		if (percentualMinimoEntrada.value == ''){
			percentualTarifaMinimaImovel.disabled = false;
		}else{
			percentualTarifaMinimaImovel.disabled = true;
		}
		
	}
	
	function desabilitaPercentualValoDebitoTabela (percentualTarifaMinimaImovel,percentualMinimoEntrada) {

		if (percentualTarifaMinimaImovel.value == ''){
			percentualMinimoEntrada.disabled = false;
		}else{
			percentualMinimoEntrada.disabled = true;
		}
		
	}

	function validarForm(form){
		if(validateInserirPrestacoesParcelamentoPerfilActionForm(form) && verificaGridQuantidadeMaximaPrestacoes()){
			document.forms[0].target='';
	    	form.submit();
		}
	}

 	function caracteresespeciais () { 
     this.aa = new Array("quantidadeMaximaPrestacao", "Quantidade Máxima de Prestações possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ab = new Array("quantidadeMaxPrestacaoEspecial", "Quantidade Máxima de Prestações p/ Parcelamento com Permissão Especial possui caracteres especiais.", new Function ("varName", " return this[varName];"));
	}

    function DecimalZeroPositivoValidations() {
        this.an = new Array("percentualMinimoEntrada", "Percentual Valor Débito deve somente conter números decimais positivos ou zero.", new Function ("varName", " return this[varName];"));
        this.am = new Array("percentualTarifaMinimaImovel", "Percentual Tarifa Mínima deve somente conter números decimais positivos ou zero.", new Function ("varName", " return this[varName];"));
        this.am = new Array("percentualValorReparcelado", "Percentual Valor Reparcelado deve somente conter números decimais positivos ou zero.", new Function ("varName", " return this[varName];"));
    }

    function IntegerValidations () {
     this.aa = new Array("quantidadeMaximaPrestacao", "Quantidade Máxima de Prestações deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.ab = new Array("quantidadeMaxPrestacaoEspecial", "Quantidade Máxima de Prestações p/ Parcelamento com Permissão Especial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    }

 var bCancel = false; 
 function validaAdicionarPrestacao(form) {                                                                   
			
      if (bCancel) {
      	return true; 
      } else {
       	return  validateCaracterEspecial(form) && validateLong(form) && validateDecimalZeroPositivo(form);
       	}
   	  
   } 


   function adicionarPrestacao (form){
   	var PERCENTUAL_ENTRADA_MINIMA = document.getElementById("PERCENTUAL_ENTRADA_MINIMA").value;
   	
   
    	if (validaRequiredAdicionarPrestacao() && validaAdicionarPrestacao(form) ){
    	
    		if(isNaN(form.quantidadeMaximaPrestacao.value)){	
	 			alert('Quantidade Máxima de Prestações possui caracteres especiais.'); 
	 			form.quantidadeMaximaPrestacao.focus();	
	 		}else if (isNaN(form.quantidadeMaxPrestacaoEspecial.value)){	
	 			alert('Quantidade Máxima de Prestações p/ Parcelamento com Permissão Especial possui caracteres especiais.'); 
	 			form.quantidadeMaxPrestacaoEspecial.focus();	
			}else if(validaCampoZeroAdicionarPrestacao()){

				if (verificaGridPercentualPorFaixaValor()) {
					document.forms[0].target='';
				    form.action = "exibirInserirPrestacoesParcelamentoPerfilAction.do?adicionarPrestacao=S&limparCampos=S";
				    submeterFormPadrao(form);
				}
 			}   	
		}
	}

	function verificaGridPercentualPorFaixaValor(){

		valido = true;

		$(".gridPercentualPorFaixaValor").each(function (i) {

			var nomeObjetoAntigo = this.name;
		    var prefixo = nomeObjetoAntigo.substring(0, 4);
		    var nomeNovoElemento = '';
		    var time = '';
		   
		    if (prefixo == 'perc') {
		        nomeNovoElemento = 'vlFixoEntrada' + nomeObjetoAntigo.replace('perc','');
		        time = nomeObjetoAntigo.replace('perc','');
		    } else {
		        nomeNovoElemento = 'perc' + nomeObjetoAntigo.replace('vlFixoEntrada','');
		        time = nomeObjetoAntigo.replace('vlFixoEntrada','');
		    }
		    
		    if (isBrancoOuNulo($.trim($("input[name="+ nomeObjetoAntigo +"]").val())) &&
		    		isBrancoOuNulo($.trim($("input[name="+ nomeNovoElemento +"]").val()))) {

				if (valido){
		    		alert('Informe Valor Fixo de Entrada ou\nPercentual');
		    		valido = false;
				}
		    }

		    // else if ($.trim($("input[name=indicadorOpcoesPercentualFaixaValor"+ time +"]").) {
		    	//alert('Informe Opções do Débito');
		    	//return false;
		    //}
		});

		return valido;
	}

	function verificaGridQuantidadeMaximaPrestacoes(){

		var valido = true;

		if ( ($(".gridQtdMaxPrestacao tr") == null || $(".gridQtdMaxPrestacao tr") == 'undefined' || $(".gridQtdMaxPrestacao tr") == undefined || $(".gridQtdMaxPrestacao tr") == '') || ($(".gridQtdMaxPrestacao tr").size() < 2)) {
			alert('Adicione Informações por Quantidade Máxima de Prestações');
			return false;
		}

		$(".gridQuantidadeMaximaPrestacoes").each(function (i) {

			var nomeObjetoAntigo = this.name;
		    var prefixo = nomeObjetoAntigo.substring(0, 7);
		    var timeNovoElemento = '';

		   	if (prefixo == 'txJuros') {
		   		timeNovoElemento = nomeObjetoAntigo.replace('txJuros','');
		   	} else if (prefixo == 'vlMinim') {
		    	timeNovoElemento = nomeObjetoAntigo.replace('vlMinimoEntrada','');
		    } else if (prefixo == 'percTar') {
		    	timeNovoElemento = nomeObjetoAntigo.replace('percTarMinImovel','');
		    } else if (prefixo == 'percMin') {
		    	timeNovoElemento = nomeObjetoAntigo.replace('percMinEntrada','');
		    } else if (prefixo == 'consult') {
		    	timeNovoElemento = nomeObjetoAntigo.replace('consultar','');
			} else if (prefixo == 'numeroD') {
		    	timeNovoElemento = nomeObjetoAntigo.replace('numeroDiasVencimentoDaEntrada','');
			}

	   		if (isBrancoOuNulo($.trim($("input[name="+ "txJuros" + timeNovoElemento +"]").val()))){
		   		if (valido){
		    		alert('Informe Taxa de Juros a.m.');
		    		valido = false;
				}
		   	}

	   		if (isBrancoOuNulo($.trim($("input[name="+ "numeroDiasVencimentoDaEntrada" + timeNovoElemento +"]").val()))){
		   		if (valido){
		    		alert('Informe Dias Vencimento da Entrada');
		    		valido = false;
				}
		   	}

	   		if (valido) {
		   		var linkConsultar = $('.gridQtdMaxPrestacao tr td:nth-child(10) a[name=consultar' + timeNovoElemento + ']');

			   	if (linkConsultar.attr("name") == null || linkConsultar.attr("name") == 'undefined' || linkConsultar.attr("name") == undefined && linkConsultar.attr("name") == '') {

			   		var opcaoObjeto = $("input[type=radio][name=indicadorEntradaParcelamento" + timeNovoElemento + "]:checked");
			   		var opcaoValor = $.trim(opcaoObjeto.val());

			   		if (isBrancoOuNulo($.trim($("input[name="+ "vlMinimoEntrada" + timeNovoElemento +"]").val())) &&
			   				isBrancoOuNulo($.trim($("input[name="+ "percTarMinImovel" + timeNovoElemento +"]").val())) && 
			   				isBrancoOuNulo($.trim($("input[name="+ "percMinEntrada" + timeNovoElemento +"]").val()))) {
			
				    	if (valido){
				    		alert('Informe Valor Mín de Entrada ou\n% Tarifa Mínima ou\n% Valor Débito');
				    		valido = false;
						}
					} else if (!isBrancoOuNulo($.trim($("input[name="+ "percMinEntrada" + timeNovoElemento +"]").val())) &&
							(opcaoValor == null || 
							opcaoValor == 'undefined' || 
							opcaoValor == undefined ||
							opcaoValor == '')) {

						alert("Informe: Opções do Débito")
						valido = false;
					}
				}
	   		}
	    	
	    });

		return valido;
	}
	
   function fechar (form){
   document.forms[0].target='';
	   //form.action = "exibirInserirPrestacoesParcelamentoPerfilAction.do?fechar=S&qtdeMaximaReparcelamentoAux=form.qtdeMaximaReparcelamentoAux.value";
	   form.action = "exibirInserirPrestacoesParcelamentoPerfilAction.do?fechar=S";
	   submeterFormPadrao(form);	   
   }

	function validaRequiredAdicionarPrestacao () {
		var form = document.forms[0];
		var msg = '';
		var PERCENTUAL_VALOR_REPARCELAMENTO_READ_ONLY = document.getElementById("PERCENTUAL_VALOR_REPARCELAMENTO_READ_ONLY").value;
		
		if( form.quantidadeMaximaPrestacao.value  == '' ) {
			msg = msg + 'Informe Quantidade Máxima de Prestações.\n';
		}
		if( form.taxaJuros.value  == '' ) {
			msg = msg + 'Informe Taxa de Juros a.m..\n';
		}
		if( form.numeroDiasVencimentoDaEntrada.value  == '' ) {
			msg = msg + 'Informe Dias Vencimento da Entrada';
		}

		var valido = false;
		
		if ((<%=session.getAttribute("collectionParcelamentoFaixaValorVazia")%> == "1" || <%=session.getAttribute("collectionParcelamentoFaixaValorVazia")%> == null)){
			valido = isValidoEntradaParcelamento();

			if (!valido) {
				msg = msg + 'Informe: Entrada de Parcelamento ou\nEntrada - Percentual por Faixa de Valor';
			}
		}

		if(msg != ''){
			alert(msg);
			return false;
		}else{
			return true;
		}
	}

	function habilitaBotaoAdicionar(){
		if (isValidoEntradaParcelamento() == true || isValidoPercentualPorFaixaValor() == true) {
			$("input[type=button][name=adicionarQuantidadeMaximaPrestacoes]").attr('disabled', '');
		} else {
			$("input[type=button][name=adicionarQuantidadeMaximaPrestacoes]").attr('disabled', 'disabled');
		}
		
		//var form = document.forms[0];
		//} else if (isValidoEntradaParcelamento() == false || isValidoPercentualPorFaixaValor() == false) {
			//form.adicionarQuantidadeMaximaPrestacoes.disabled = true;
		//} else {
			//form.adicionarQuantidadeMaximaPrestacoes.disabled = false;
		//}
	}

	function isValidoEntradaParcelamento() {

		var retorno = false;

		var form = document.forms[0];
		
		//if (isBrancoOuNulo(form.valorMinimoEntrada.value)){
			//retorno = false;
		//} else if (!isBrancoOuNulo(form.percentualTarifaMinimaImovel.value) || !isBrancoOuNulo(form.percentualMinimoEntrada.value)){
			//retorno = true;
		//}
		if (!isBrancoOuNulo(form.valorMinimoEntrada.value)){
			retorno = true;
		} else if (!isBrancoOuNulo(form.percentualTarifaMinimaImovel.value)){
			retorno = true;
		} else if (!isBrancoOuNulo(form.percentualMinimoEntrada.value)){
			if ((form.indicadorOpcoesEntradaParcelamento[0] != null || form.indicadorOpcoesEntradaParcelamento[1] != null) && 
					(form.indicadorOpcoesEntradaParcelamento[0].checked != false || form.indicadorOpcoesEntradaParcelamento[1].checked != false)) {
				retorno = true;
			}
		}

		return retorno;
	}

	function isValidoPercentualPorFaixaValor() {

		retorno = true;
		
		if ((<%=session.getAttribute("collectionParcelamentoFaixaValorVazia")%> == "1" ||
				<%=session.getAttribute("collectionParcelamentoFaixaValorVazia")%> == null)){
			retorno = false;
		}

		return retorno;
	}
	
	function validaCampoZeroAdicionarPrestacao () {
		var form = document.forms[0];
		var msg = '';
		if( !testarValorZero(form.quantidadeMaximaPrestacao)) {
			msg = msg + 'Quantidade Máxima de Prestações deve somente conter números positivos.';
		}
		if( !testarValorZero(form.quantidadeMaxPrestacaoEspecial)) {
			msg = msg + 'Quantidade Máxima de Prestações p/ Parcelamento com Permissão Especial deve somente conter números positivos.';
		}
		if( msg != '' ){
			alert(msg);
			return false;
		}else{
			return true;
		}
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

-->
</script>

</head>

<logic:present name="reloadPage">
	
	<logic:equal name="reloadPage" value="INSERIRPERFIL">
	
		<body leftmargin="5" topmargin="5" onload="window.opener.location.href='/gsan/exibirInserirPerfilParcelamentoAction.do';window.close();">
	</logic:equal>
	
	<logic:equal name="reloadPage" value="ATUALIZARPERFIL">
		<body leftmargin="5" topmargin="5" onload="window.opener.location.href='/gsan/exibirAtualizarPerfilParcelamentoAction.do?reload=S';window.close();">
	</logic:equal>
	
	<logic:equal name="reloadPage" value="FECHARINSERIR">
		<body leftmargin="5" topmargin="5" onload="window.opener.location.href='/gsan/exibirInserirPerfilParcelamentoAction.do';window.close();">
	</logic:equal>
	
	<logic:equal name="reloadPage" value="FECHARATUALIZAR">
		<body leftmargin="5" topmargin="5" onload="window.opener.location.href='/gsan/exibirAtualizarPerfilParcelamentoAction.do?reload=S';window.close();">
	</logic:equal>
	
	
</logic:present>

<logic:notPresent name="reloadPage">
	<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');resizePageSemLink('750', '700');">
</logic:notPresent>



<html:form action="/inserirPrestacoesParcelamentoPerfilAction"
	name="InserirPrestacoesParcelamentoPerfilActionForm"
	type="gcom.gui.cobranca.parcelamento.InserirPrestacoesParcelamentoPerfilActionForm"
	method="post"
	onsubmit="return validateInserirPrestacoesParcelamentoPerfilActionForm(this);">
	
	<input type="hidden" id="PERCENTUAL_ENTRADA_MINIMA" value="${requestScope.percentualFinanciamentoEntradaMinima}"/>
	<input type="hidden" id="PERCENTUAL_VALOR_REPARCELAMENTO_READ_ONLY" value="${requestScope.percentualValorReparceladoReadOnly}"/>
	<html:hidden property="qtdeMaximaReparcelamentoAux"/>
	
	<table width="710" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="710" valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td width="11">
						<img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Adicionar Informações do Parcelamento por Quantidade de Reparcelamentos </td>
					<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr bgcolor="#90c7fc" height="18">
					<td align="center" colspan="6"><strong>Quantidade de Prestações</strong></td>
				</tr>
				<tr>
					<td colspan="6">
					Preencha os campos para inserir as informações do parcelamento por quantidade de reparcelamentos:
					</td>
				</tr>
				<tr>
					<td colspan="6">
					<p>&nbsp;</p>
					</td>
				</tr>
				
				<tr>
					<td><strong> Quantidade Máxima de Prestações:<font color="#FF0000">*</font></strong></td>
					
					<logic:equal name="readOnly" value="true">
						<td>
							<html:text property="quantidadeMaximaPrestacao" size="3" maxlength="3" tabindex="1" readonly="true" onkeypress="return isCampoNumerico(event);" />
						</td>
					</logic:equal>
					<logic:notEqual name="readOnly" value="true">
						<td>
							<html:text property="quantidadeMaximaPrestacao" size="3" maxlength="3" tabindex="1" onkeypress="return isCampoNumerico(event);" />
						</td>
					</logic:notEqual>
					
					<td><strong> Quantidade Máxima de Prestações p/ Parcelamento com Permissão Especial:</strong></td>
					<logic:equal name="readOnly" value="true">
						<td>
							<html:text property="quantidadeMaxPrestacaoEspecial" size="3" maxlength="3" tabindex="2" readonly="true" onkeypress="return isCampoNumerico(event);" />
						</td>
					</logic:equal>
					<logic:notEqual name="readOnly" value="true">
						<td>
							<html:text property="quantidadeMaxPrestacaoEspecial" size="3" maxlength="3" tabindex="2" onkeypress="return isCampoNumerico(event);" />
						</td>
					</logic:notEqual>
					
					<td><strong> Taxa de Juros a.m.:<font color="#FF0000">*</font></strong></td>
					<logic:equal name="readOnly" value="true">
						<td>
							<html:text property="taxaJuros" size="6" maxlength="6" tabindex="3" onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5)" style="text-align:right; background-color:#EFEFEF;border:0;color: #000000;" readonly="true"/>
						</td>
					</logic:equal>
					<logic:notEqual name="readOnly" value="true">
						<logic:present name="desabilitarTaxaJuros" scope="session">
							<td>
								<html:text property="taxaJuros" size="6" maxlength="6" tabindex="3" onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5)" style="text-align:right; background-color:#EFEFEF;border:0;color: #000000;" readonly="true"/>
							</td>
						</logic:present>
						<logic:notPresent name="desabilitarTaxaJuros" scope="session">
							<td>
								<html:text property="taxaJuros" size="6" maxlength="6" tabindex="3" onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5)" style="text-align:right;"/>
							</td>
						</logic:notPresent>
					</logic:notEqual>
					
				</tr>

				<tr>
					<td><strong>Meses entre Parcelas:</strong></td>
					<logic:equal name="readOnly" value="true">
						<td>
							<html:text property="numeroMesesEntreParcelas" size="2" maxlength="4" tabindex="4" readonly="true" onkeypress="return isCampoNumerico(event);" />
						</td>
					</logic:equal>
					<logic:notEqual name="readOnly" value="true">
						<td>
							<html:text property="numeroMesesEntreParcelas" size="2" maxlength="4" tabindex="4" onkeypress="return isCampoNumerico(event);" />
						</td>
					</logic:notEqual>

					<td><strong>Parcelas a Lançar:</strong></td>
					<logic:equal name="readOnly" value="true">
						<td>
							<html:text property="numeroParcelasALancar" size="2" maxlength="4" tabindex="5" readonly="true" onkeypress="return isCampoNumerico(event);" />
						</td>
					</logic:equal>
					<logic:notEqual name="readOnly" value="true">
						<td>
							<html:text property="numeroParcelasALancar" size="2" maxlength="4" tabindex="5" onkeypress="return isCampoNumerico(event);" />
						</td>
					</logic:notEqual>

					<td><strong>Meses para Início da Cobrança:</strong></td>
					<logic:equal name="readOnly" value="true">
						<td>
							<html:text property="numeroMesesInicioCobranca" size="6" maxlength="6" tabindex="6" readonly="true" onkeypress="return isCampoNumerico(event);" />
						</td>
					</logic:equal>
					<logic:notEqual name="readOnly" value="true">
						<td>
							<html:text property="numeroMesesInicioCobranca" size="6" maxlength="6" tabindex="6" onkeypress="return isCampoNumerico(event);" />
						</td>
					</logic:notEqual>
				</tr>

				<tr>
					<td><strong>Dias Vencimento da Entrada:<font color="#FF0000">*</font></strong></td>
					<logic:equal name="readOnly" value="true">
						<td>
							<html:text property="numeroDiasVencimentoDaEntrada" size="2" maxlength="4" tabindex="7" readonly="true" onkeypress="return isCampoNumerico(event);" />
						</td>
					</logic:equal>
					<logic:notEqual name="readOnly" value="true">
						<td>
							<html:text property="numeroDiasVencimentoDaEntrada" size="2" maxlength="4" tabindex="7" onkeypress="return isCampoNumerico(event);" />
						</td>
					</logic:notEqual>
				</tr>

				<!-- <tr>
					<td><strong>Entrada:<font color="#FF0000">*</font></strong></td>
				</tr> -->

				<tr bgcolor="#90c7fc" height="18">
					<td align="center" colspan="6"><strong>Entrada de Parcelamento</strong></td>
				</tr>
				
				<!-- Isilva -->
				<tr>
					<td><strong>Valor Mínimo de Entrada:</strong></td>
					
					<logic:notEmpty name="collectionParcelamentoFaixaValor">
						<logic:equal name="readOnly" value="true">
							<td>
								<html:text property="valorMinimoEntrada" size="14" maxlength="14" onkeypress="return isCampoNumerico(event);" onkeyup="desabilitarCampoEntradaParcelamento(document.forms[0]);formataValorMonetario(this, 11);desabilitaBotaoAdicionarFaixaValor();" tabindex="8" style="text-align:right;" readonly="true"/>
							</td>
						</logic:equal>
						<logic:notEqual name="readOnly" value="true">
							<td>
								<html:text property="valorMinimoEntrada" size="14" maxlength="14" onkeypress="return isCampoNumerico(event);" onkeyup="desabilitarCampoEntradaParcelamento(document.forms[0]);formataValorMonetario(this, 11);desabilitaBotaoAdicionarFaixaValor();" tabindex="8" style="text-align:right;" disabled="true" readonly="true"/>
							</td>
						</logic:notEqual>
					</logic:notEmpty>
					
					<logic:empty name="collectionParcelamentoFaixaValor">
						<logic:equal name="readOnly" value="true">
							<td>
								<html:text property="valorMinimoEntrada" size="14" maxlength="14" onkeypress="return isCampoNumerico(event);" onkeyup="desabilitarCampoEntradaParcelamento(document.forms[0]);formataValorMonetario(this, 11);desabilitaBotaoAdicionarFaixaValor();" tabindex="8" style="text-align:right;" readonly="true"/>
							</td>
						</logic:equal>
						<logic:notEqual name="readOnly" value="true">
							<td>
								<html:text property="valorMinimoEntrada" size="14" maxlength="14" onkeypress="return isCampoNumerico(event);" onkeyup="desabilitarCampoEntradaParcelamento(document.forms[0]);formataValorMonetario(this, 11);desabilitaBotaoAdicionarFaixaValor();" tabindex="8" style="text-align:right;" />
							</td>
						</logic:notEqual>
					</logic:empty>					
					
					<td><strong> Percentual Tarifa Mínima:</strong></td>
					<logic:notEmpty name="collectionParcelamentoFaixaValor">
					
						<logic:equal name="readOnly" value="true">
							<td>
								<html:text property="percentualTarifaMinimaImovel" size="6" maxlength="6" onkeypress="return isCampoNumerico(event);" onkeyup="desabilitarCampoEntradaParcelamento(document.forms[0]);formataValorMonetario(this, 5);desabilitaPercentualValoDebito();" tabindex="9" style="text-align:right;" readonly="true"/>
							</td>		
						</logic:equal>
						<logic:notEqual name="readOnly" value="true">
							<td>
								<html:text property="percentualTarifaMinimaImovel" size="6" maxlength="6" onkeypress="return isCampoNumerico(event);" onkeyup="desabilitarCampoEntradaParcelamento(document.forms[0]);formataValorMonetario(this, 5);desabilitaPercentualValoDebito();" tabindex="9"  style="text-align:right;" disabled="true" readonly="true" />
							</td>		
						</logic:notEqual>
					</logic:notEmpty>
					
					<logic:empty name="collectionParcelamentoFaixaValor">
						<logic:equal name="readOnly" value="true">
							<td>
								<html:text property="percentualTarifaMinimaImovel" size="6" maxlength="6" onkeypress="return isCampoNumerico(event);" onkeyup="desabilitarCampoEntradaParcelamento(document.forms[0]);formataValorMonetario(this, 5);desabilitaPercentualValoDebito();" tabindex="9"  style="text-align:right;" readonly="true" />
							</td>		
						</logic:equal>
						<logic:notEqual name="readOnly" value="true">
							<td>
								<html:text property="percentualTarifaMinimaImovel" size="6" maxlength="6" onkeypress="return isCampoNumerico(event);" onkeyup="desabilitarCampoEntradaParcelamento(document.forms[0]);formataValorMonetario(this, 5);desabilitaPercentualValoDebito();" tabindex="9"  style="text-align:right;" />
							</td>		
						</logic:notEqual>
					</logic:empty>
					
					
					<td><strong> Percentual Valor Reparcelado:</strong></td>
					<logic:equal name="readOnly" value="true">
						<td>
							<html:text property="percentualValorReparcelado" onkeypress="return isCampoNumerico(event);" size="6" maxlength="6" tabindex="10"  style="text-align:right;" readonly="true" onkeyup="formataValorMonetario(this, 5);"/>
						</td>		
					</logic:equal>
					<logic:notEqual name="readOnly" value="true">
						<logic:equal name="percentualValorReparceladoReadOnly" value="true">
							<td>
								<html:text property="percentualValorReparcelado" onkeypress="return isCampoNumerico(event);" size="6" maxlength="6" tabindex="10"  style="text-align:right;" onkeyup="formataValorMonetario(this, 5);" disabled="true"/>
							</td>
						</logic:equal>			
						<logic:notEqual name="percentualValorReparceladoReadOnly" value="true">
							<td>
								<html:text property="percentualValorReparcelado" onkeypress="return isCampoNumerico(event);" size="6" maxlength="6" tabindex="10"  style="text-align:right;" onkeyup="formataValorMonetario(this, 5);"/>
							</td>
						</logic:notEqual>	
						
					</logic:notEqual>
				</tr>
				
				<tr>
					<td><strong> Percentual Valor Débito:</strong></td>
					
					<logic:notEmpty name="collectionParcelamentoFaixaValor">
						<logic:equal name="readOnly" value="true">
							<td>
								<html:text property="percentualMinimoEntrada" onkeypress="return isCampoNumerico(event);" onkeyup="desabilitarCampoEntradaParcelamento(document.forms[0]);formataValorMonetario(this, 5);" size="6" maxlength="6" tabindex="11" style="text-align:right;" readonly="true"/>
							</td>
						</logic:equal>
						<logic:notEqual name="readOnly" value="true">
							<td>
								<html:text property="percentualMinimoEntrada" onkeypress="return isCampoNumerico(event);" onkeyup="desabilitarCampoEntradaParcelamento(document.forms[0]);formataValorMonetario(this, 5);" size="6" maxlength="6" tabindex="11" style="text-align:right;" disabled="true" readonly="true"/>
							</td>
						</logic:notEqual>
					</logic:notEmpty>
					
					<logic:empty name="collectionParcelamentoFaixaValor">
						<logic:equal name="readOnly" value="true">
							<td>
								<html:text property="percentualMinimoEntrada" onkeypress="return isCampoNumerico(event);" onkeyup="desabilitarCampoEntradaParcelamento(document.forms[0]);formataValorMonetario(this, 5);" size="6" maxlength="6" tabindex="11" style="text-align:right;" readonly="true"/>
							</td>
						</logic:equal>
						<logic:notEqual name="readOnly" value="true">
							<td>
								<html:text property="percentualMinimoEntrada" onkeypress="return isCampoNumerico(event);" size="6" maxlength="6" tabindex="11" style="text-align:right;"	onkeyup="desabilitarCampoEntradaParcelamento(document.forms[0]);formataValorMonetario(this, 5);desabilitaPercentualTarifaMax();"/>
							</td>
						</logic:notEqual>
					</logic:empty>
					
					<td><strong> Percentual Entrada Sugerida:</strong></td>
					<logic:notEmpty name="collectionParcelamentoFaixaValor">
					
						<logic:equal name="readOnly" value="true">
							<td>
								<html:text property="percentualEntradaSugerida" size="6" maxlength="6" onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);" tabindex="9" style="text-align:right;" readonly="true"/>
							</td>		
						</logic:equal>
						<logic:notEqual name="readOnly" value="true">
							<td>
								<html:text property="percentualEntradaSugerida" size="6" maxlength="6" onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);" tabindex="9"  style="text-align:right;" disabled="true" readonly="true" />
							</td>		
						</logic:notEqual>
					</logic:notEmpty>
					
					<logic:empty name="collectionParcelamentoFaixaValor">
						<logic:equal name="readOnly" value="true">
							<td>
								<html:text property="percentualEntradaSugerida" size="6" maxlength="6" onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);" tabindex="9"  style="text-align:right;" readonly="true" />
							</td>		
						</logic:equal>
						<logic:notEqual name="readOnly" value="true">
							<td>
								<html:text property="percentualEntradaSugerida" size="6" maxlength="6" onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);" tabindex="9"  style="text-align:right;" />
							</td>		
						</logic:notEqual>
					</logic:empty>
				</tr>
				
				<tr>
					<td><strong>Opções: <font color="#ff0000">*</font></strong></td>
					<logic:notEmpty name="collectionParcelamentoFaixaValor">
						<logic:equal name="readOnly" value="true">
							<td colspan="5">
									<strong>
										<html:radio property="indicadorOpcoesEntradaParcelamento" value="1" disabled="true"/>débito original&nbsp;&nbsp;
									</strong>
									<strong>
										<html:radio property="indicadorOpcoesEntradaParcelamento" value="2" disabled="true"/>débito atualizado
									</strong>
							</td>
							
						</logic:equal>
						<logic:notEqual name="readOnly" value="true">
							<td colspan="5">
									<strong>
										<html:radio property="indicadorOpcoesEntradaParcelamento" value="1" disabled="true"/>débito original&nbsp;&nbsp;
									</strong>
									<strong>
										<html:radio property="indicadorOpcoesEntradaParcelamento" value="2" disabled="true"/>débito atualizado
									</strong>
							</td>
						</logic:notEqual>
					</logic:notEmpty>
					
					<logic:empty name="collectionParcelamentoFaixaValor">
						<logic:equal name="readOnly" value="true">
							<td colspan="5">
								<strong>
									<html:radio property="indicadorOpcoesEntradaParcelamento" value="1" disabled="true"/>débito original&nbsp;&nbsp;
								</strong>
								<strong>
									<html:radio property="indicadorOpcoesEntradaParcelamento" value="2" disabled="true"/>débito atualizado
								</strong>
							</td>
						</logic:equal>
						<logic:notEqual name="readOnly" value="true">
							<td colspan="5">
								<strong>
									<html:radio property="indicadorOpcoesEntradaParcelamento" value="1"/>débito original&nbsp;&nbsp;
								</strong>
								<strong>
									<html:radio property="indicadorOpcoesEntradaParcelamento" value="2"/>débito atualizado
								</strong>
							</td>
						</logic:notEqual>
					</logic:empty>
				</tr>
				
				<tr>
					<td colspan="6">
					<p>&nbsp;</p>
					</td>
				</tr>
				
				<!-- Desconto nos Acréscimos -->
				
				<tr bgcolor="#90c7fc" height="18">
					<td align="center" colspan="6"><strong>Desconto nos Acréscimos</strong></td>
				</tr>
				<tr>
					<td colspan="6">
					<p>Preencha os campos para inserir os valores de desconto nos acréscimos:</p>
					<p>&nbsp;</p>
					</td>
				</tr>
				<tr>
					<td><strong> Percentual de Desconto em Juros Mora:</strong></td>
					<td>
						<html:text property="percentualDescontoJurosMora" tabindex="13" size="14" maxlength="14" onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 11);" style="text-align:right;" />
					</td>
					<td><strong> Percentual de Desconto em Multa:</strong></td>
					<td>
						<html:text property="percentualDescontoMulta" tabindex="13" size="14" maxlength="14" onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 11);" style="text-align:right;" />
					</td>
					<td><strong> Percentual de Desconto em Correção Monetária:</strong></td>
					<td>
						<html:text property="percentualDescontoCorrecaoMonetaria" tabindex="13" size="14" maxlength="14" onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 11);" style="text-align:right;" />
					</td>
				</tr>
				
				<tr>
					<td colspan="6">
					<p>&nbsp;</p>
					</td>
				</tr>
				<tr bgcolor="#90c7fc" height="18">
					<td align="center" colspan="6"><strong>Entrada - Percentual por Faixa de Valor</strong></td>
				</tr>
				<tr>
					<td colspan="6">
					<p>Preencha os campos para inserir um percentual por faixa de valor:</p>
					<p>&nbsp;</p>
					</td>
				</tr>
				<tr>
					<td><strong> Valor Mínimo:<font color="#FF0000">*</font></strong></td>
					<td>
						<html:text property="valorMaxPercFaixaValor" tabindex="13" size="14" maxlength="14" onkeypress="return isCampoNumerico(event);" onkeyup="desabilitarCampoPercentualPorFaixaValor(document.forms[0]);formataValorMonetario(this, 11);" style="text-align:right;" />
					</td>
					
					<td align="center"><strong>Opções: <font color="#ff0000">*</font></strong></td>
					<td colspan="3">
						<strong>
							<html:radio property="indicadorOpcoesPercentualFaixaValor" value="1"/>débito original&nbsp;
						</strong>
						<strong>
							<html:radio property="indicadorOpcoesPercentualFaixaValor" value="2"/>débito atualizado
						</strong>
					</td>
				</tr>
				
				<tr>
					
					<td><strong>Valor Fixo de Entrada:<font color="#ff0000">*</font></strong></td>
					<td>
						<html:text property="valorFixoEntrada" tabindex="14" size="14" maxlength="14" onkeypress="return isCampoNumerico(event);" onkeyup="desabilitarCampoPercentualPorFaixaValor(document.forms[0]);formataValorMonetario(this, 11)" style="text-align:right;" />
					</td>
					<td align="center"><strong> Percentual:<font color="#ff0000">*</font></strong></td>
					<td colspan="3">
						<html:text property="percentualPercFaixaValor" size="6" maxlength="6" tabindex="14" onkeypress="return isCampoNumerico(event);" onkeyup="desabilitarCampoPercentualPorFaixaValor(document.forms[0]);formataValorMonetario(this, 5)" style="text-align:right;" />
					</td>
				</tr>
			
				<!-- Isilva -->

				
				<tr>
					<logic:equal name="readOnly" value="true">
						<td align="right" colspan="6">
							<input name="adicionarPercentual" type="button" class="bottonRightCol" value="Adicionar" align="right" readonly="true" tabindex="15"/>
						</td>
					</logic:equal>
					<logic:notEqual name="readOnly" value="true">
						<td align="right" colspan="6">
							<input name="adicionarPercentual" type="button" class="bottonRightCol" value="Adicionar" align="right" tabindex="15" onclick="adicionarPercentualFaixaValor(document.forms[0]);" />
						</td>
					</logic:notEqual>
				</tr>
				
				<!-- início da tabela de Percentual Por Faixa de Valor -->
				
				
				<tr >
					<td colspan= "6">
					<%-- <div style="width: 100%; height: 90; overflow: auto;">--%>
						<table width="100%" border="0" bgcolor="#90c7fc" id="gridPercentualPorFaixaValor">
						
						<logic:empty name="collectionParcelamentoFaixaValor" scope="session">
							<tr bgcolor="#90c7fc" height="18">
								<td align="center" width="10%"><strong>Remover</strong></td>
								<td align="center"><strong>Valor Mínimo</strong></td>
								<td align="center"><strong>Valor Fixo de Entrada</strong></td>
								<td align="center"><strong>Percentual</strong></td>
								<td align="center"><strong>Opções do Débito</strong></td>
							</tr>
						</logic:empty>
						
						<logic:notEmpty name="collectionParcelamentoFaixaValor" scope="session">
						
						<%if (((Collection) session.getAttribute("collectionParcelamentoFaixaValor")).size() 
									<= ConstantesSistema.NUMERO_MAXIMO_REGISTROS_PERFIL_PARCELAMENTO) {%>
						
							<tr bgcolor="#90c7fc" height="18">
								<td align="center" width="10%"><strong>Remover</strong></td>
								<td align="center"><strong>Valor Mínimo</strong></td>
								<td align="center"><strong>Valor Fixo de Entrada</strong></td>
								<td align="center"><strong>Percentual</strong></td>
								<td align="center"><strong>Opções do Débito</strong></td>
							</tr>
							<logic:present name="collectionParcelamentoFaixaValor">
								<%int cont = 1;%>
								<logic:iterate name="collectionParcelamentoFaixaValor" 
								id="parcelamentoFaixaValor"
								type="ParcelamentoFaixaValor">
										<%cont = cont + 1;
											if (cont % 2 == 0) {%>
										<tr bgcolor="#FFFFFF">
											<%} else {

											%>
										<tr bgcolor="#cbe5fe">
											<%}%>
											
											<td width="10%">
												<logic:equal name="readOnly" value="true">
													<div align="center">
														<font color="#333333">
															<img width="14" height="14" border="0" src="<bean:message key="caminho.imagens"/>Error.gif" />
									            		</font>
									            	</div>
												</logic:equal>
												<logic:notEqual name="readOnly" value="true">
													<div align="center">
														<font color="#333333">
															<img width="14" height="14" border="0" src="<bean:message key="caminho.imagens"/>Error.gif" onclick="javascript:document.forms[0].target='';if(confirm('Confirma remoção?')){redirecionarSubmit('removerPercentualFaixaValorAction.do?valorMaximo=<bean:write name="parcelamentoFaixaValor" property="valorFaixa"/>');}" />
										            	</font>
										            </div>
												</logic:notEqual>
									       </td>
									       								       
									       <td align="center">
												<div><bean:write name="parcelamentoFaixaValor" property="valorFaixa" formatKey="money.format" /> &nbsp;</div>
											</td>
	
	
											<td align="center">
												<logic:equal name="readOnly" value="true">
													<input type="text" style="text-align: right;font-size: xx-small;" 
													align="center" name="vlFixoEntrada<bean:write name="parcelamentoFaixaValor" property="ultimaAlteracao.time"/>" 
													value="<%="" + Util.formatarMoedaReal(parcelamentoFaixaValor.getValorFixoEntrada())%>" 
													size="14" maxlength="14" onkeypress="return isCampoNumerico(event);" onkeyup="desabilitarCampoEmGridPercentualPorFaixaValor(this);formataValorMonetario(this, 11)" readonly="true"
													class="gridPercentualPorFaixaValor"/>
												</logic:equal>
												<logic:notEqual name="readOnly" value="true">
													<input type="text" style="text-align: right;font-size: xx-small;" 
													align="center" name="vlFixoEntrada<bean:write name="parcelamentoFaixaValor" property="ultimaAlteracao.time"/>" 
													value="<%="" + Util.formatarMoedaReal(parcelamentoFaixaValor.getValorFixoEntrada())%>" 
													size="14" maxlength="14" onkeypress="return isCampoNumerico(event);" onkeyup="desabilitarCampoEmGridPercentualPorFaixaValor(this);formataValorMonetario(this, 11)"
													class="gridPercentualPorFaixaValor" />
												</logic:notEqual>
											</td>
	
											<td align="center">
												<logic:equal name="readOnly" value="true">
													<input type="text" style="text-align: right;font-size: xx-small;" 
													align="center" name="perc<bean:write name="parcelamentoFaixaValor" property="ultimaAlteracao.time"/>" 
													value="<%="" + Util.formatarMoedaReal(parcelamentoFaixaValor.getPercentualFaixa())%>" readonly="true" 
													size="6" maxlength="6" onkeypress="return isCampoNumerico(event);" onkeyup="desabilitarCampoEmGridPercentualPorFaixaValor(this);formataValorMonetario(this, 5)" />
												</logic:equal>
												<logic:notEqual name="readOnly" value="true">
													<input type="text" style="text-align: right;font-size: xx-small;" 
													align="center" name="perc<bean:write name="parcelamentoFaixaValor" property="ultimaAlteracao.time"/>" 
													value="<%="" + Util.formatarMoedaReal(parcelamentoFaixaValor.getPercentualFaixa())%>" 
													size="6" maxlength="6" onkeypress="return isCampoNumerico(event);" onkeyup="desabilitarCampoEmGridPercentualPorFaixaValor(this);formataValorMonetario(this, 5)" />
												</logic:notEqual>
											</td>
											
											<td align="left">
												<strong>
													<logic:equal name="readOnly" value="true">
														<% if (parcelamentoFaixaValor.getIndicadorPercentualFaixaValor().intValue() == 1){ %>
															<input type="radio" name="indicadorOpcoesPercentualFaixaValor<bean:write name="parcelamentoFaixaValor" property="ultimaAlteracao.time"/>"
															checked="checked" value="1" readonly="true" />original
															&nbsp;&nbsp;
															<input type="radio" name="indicadorOpcoesPercentualFaixaValor<bean:write name="parcelamentoFaixaValor" property="ultimaAlteracao.time"/>"
															 value="2" readonly="true" />atualizado
														<% } else { %>
															<input type="radio" name="indicadorOpcoesPercentualFaixaValor<bean:write name="parcelamentoFaixaValor" property="ultimaAlteracao.time"/>"
															value="1" readonly="true" />original
															&nbsp;&nbsp;
															<input type="radio" name="indicadorOpcoesPercentualFaixaValor<bean:write name="parcelamentoFaixaValor" property="ultimaAlteracao.time"/>"
															checked="checked" value="2" readonly="true" />atualizado
														<%} %>
													</logic:equal>
													<logic:notEqual name="readOnly" value="true">
														<% if (parcelamentoFaixaValor.getIndicadorPercentualFaixaValor().intValue() == 1){ %>
															<input type="radio" name="indicadorOpcoesPercentualFaixaValor<bean:write name="parcelamentoFaixaValor" property="ultimaAlteracao.time"/>"
															checked="checked" value="1" />original
															&nbsp;&nbsp;
															<input type="radio" name="indicadorOpcoesPercentualFaixaValor<bean:write name="parcelamentoFaixaValor" property="ultimaAlteracao.time"/>"
															 value="2" />atualizado
														<% } else { %>
															<input type="radio" name="indicadorOpcoesPercentualFaixaValor<bean:write name="parcelamentoFaixaValor" property="ultimaAlteracao.time"/>"
															value="1" />original
															&nbsp;&nbsp;
															<input type="radio" name="indicadorOpcoesPercentualFaixaValor<bean:write name="parcelamentoFaixaValor" property="ultimaAlteracao.time"/>"
															checked="checked" value="2" />atualizado
														<%} %>
													</logic:notEqual>
												</strong>
											</td>
											
										</tr>
								</logic:iterate>
							</logic:present>
									
						<%} else {%>			
						
						
							<tr bgcolor="#90c7fc" height="18">
								<td align="center" width="10%"><strong>Remover</strong></td>
								<td align="center"><strong>Valor Mínimo</strong></td>
								<td align="center"><strong>Valor Fixo de Entrada</strong></td>
								<td align="center"><strong>Percentual</strong></td>
								<td align="center"><strong>Opções do Débito</strong></td>
							</tr>
							
							<tr>
								<td height="100" colspan="6">
									<div style="width: 100%; height: 100%; overflow: auto;">
										<table width="100%">
										
											<logic:present name="collectionParcelamentoFaixaValor">
												<%int cont = 1;%>
												<logic:iterate name="collectionParcelamentoFaixaValor" 
												id="parcelamentoFaixaValor"
												type="ParcelamentoFaixaValor">
														<%cont = cont + 1;
															if (cont % 2 == 0) {%>
														<tr bgcolor="#FFFFFF">
															<%} else {
				
															%>
														<tr bgcolor="#cbe5fe">
															<%}%>
															
															<td width="10%">
																<logic:equal name="readOnly" value="true">
																	<div align="center"><font color="#333333"> <img width="14"
														             height="14" border="0"
														             src="<bean:message key="caminho.imagens"/>Error.gif" />
													            </font></div>
																</logic:equal>
																<logic:notEqual name="readOnly" value="true">
																	<div align="center"><font color="#333333"> <img width="14"
														             height="14" border="0"
														             src="<bean:message key="caminho.imagens"/>Error.gif"
						 								             onclick="javascript:document.forms[0].target='';if(confirm('Confirma remoção?')){redirecionarSubmit('removerPercentualFaixaValorAction.do?valorMaximo=<bean:write name="parcelamentoFaixaValor" property="valorFaixa"/>');}" />
														            </font></div>
																</logic:notEqual>
													       </td>	
													       								       
													       <td align="center">
																<div>${parcelamentoFaixaValor.valorFaixa} &nbsp;</div>
															</td>
					
															<td align="center">
																<logic:equal name="readOnly" value="true">
																	<input type="text" style="text-align: right;font-size: xx-small;" 
																	align="center" name="vlFixoEntrada<bean:write name="parcelamentoFaixaValor" property="ultimaAlteracao.time"/>" 
																	value="<%="" + Util.formatarMoedaReal(parcelamentoFaixaValor.getValorFixoEntrada())%>" readonly="true"
																	size="14" maxlength="14" onkeypress="return isCampoNumerico(event);" onkeyup="desabilitarCampoEmGridPercentualPorFaixaValor(this);formataValorMonetario(this, 11)" 
																	class="gridPercentualPorFaixaValor" />
																</logic:equal>
																<logic:notEqual name="readOnly" value="true">
																	<input type="text" style="text-align: right;font-size: xx-small;" 
																	align="center" name="vlFixoEntrada<bean:write name="parcelamentoFaixaValor" property="ultimaAlteracao.time"/>" 
																	value="<%="" + Util.formatarMoedaReal(parcelamentoFaixaValor.getValorFixoEntrada())%>" 
																	size="14" maxlength="14" onkeypress="return isCampoNumerico(event);" onkeyup="desabilitarCampoEmGridPercentualPorFaixaValor(this);formataValorMonetario(this, 11)"
																	class="gridPercentualPorFaixaValor" />
																</logic:notEqual>
															</td>
					
															<td align="center">
																<logic:equal name="readOnly" value="true">
																	<input type="text" style="text-align: right;font-size: xx-small;" 
																	align="center" name="perc<bean:write name="parcelamentoFaixaValor" property="ultimaAlteracao.time"/>" 
																	value="<%="" + Util.formatarMoedaReal(parcelamentoFaixaValor.getPercentualFaixa())%>" readonly="true"
																	size="6" maxlength="6" onkeypress="return isCampoNumerico(event);" onkeyup="desabilitarCampoEmGridPercentualPorFaixaValor(this);formataValorMonetario(this, 5)" />
																</logic:equal>
																<logic:notEqual name="readOnly" value="true">
																	<input type="text" style="text-align: right;font-size: xx-small;" 
																	align="center" name="perc<bean:write name="parcelamentoFaixaValor" property="ultimaAlteracao.time"/>" 
																	value="<%="" + Util.formatarMoedaReal(parcelamentoFaixaValor.getPercentualFaixa())%>" 
																	size="6" maxlength="6" onkeypress="return isCampoNumerico(event);" onkeyup="desabilitarCampoEmGridPercentualPorFaixaValor(this);formataValorMonetario(this, 5)" />
																</logic:notEqual>
															</td>
				
				
															<td align="left">
																<strong>
																	<logic:equal name="readOnly" value="true">
																		<% if (parcelamentoFaixaValor.getIndicadorPercentualFaixaValor().intValue() == 1){ %>
																			<input type="radio" name="indicadorOpcoesPercentualFaixaValor<bean:write name="parcelamentoFaixaValor" property="ultimaAlteracao.time"/>"
																			checked="checked" value="1" readonly="true" />original
																			<br>
																			<input type="radio" name="indicadorOpcoesPercentualFaixaValor<bean:write name="parcelamentoFaixaValor" property="ultimaAlteracao.time"/>"
																			 value="2" readonly="true" />atualizado
																		<% } else { %>
																			<input type="radio" name="indicadorOpcoesPercentualFaixaValor<bean:write name="parcelamentoFaixaValor" property="ultimaAlteracao.time"/>"
																			value="1" readonly="true" />original
																			<br>
																			<input type="radio" name="indicadorOpcoesPercentualFaixaValor<bean:write name="parcelamentoFaixaValor" property="ultimaAlteracao.time"/>"
																			checked="checked" value="2" readonly="true" />atualizado
																		<%} %>
																	</logic:equal>
																	<logic:notEqual name="readOnly" value="true">
																		<% if (parcelamentoFaixaValor.getIndicadorPercentualFaixaValor().intValue() == 1){ %>
																			<input type="radio" name="indicadorOpcoesPercentualFaixaValor<bean:write name="parcelamentoFaixaValor" property="ultimaAlteracao.time"/>"
																			checked="checked" value="1" />original
																			<br>
																			<input type="radio" name="indicadorOpcoesPercentualFaixaValor<bean:write name="parcelamentoFaixaValor" property="ultimaAlteracao.time"/>"
																			 value="2" />atualizado
																		<% } else { %>
																			<input type="radio" name="indicadorOpcoesPercentualFaixaValor<bean:write name="parcelamentoFaixaValor" property="ultimaAlteracao.time"/>"
																			value="1" />original
																			<br>
																			<input type="radio" name="indicadorOpcoesPercentualFaixaValor<bean:write name="parcelamentoFaixaValor" property="ultimaAlteracao.time"/>"
																			checked="checked" value="2" />atualizado
																		<%} %>
																	</logic:notEqual>
																</strong>
															</td>
											
														</tr>
												</logic:iterate>
											</logic:present>
										</table>
									</div>	
								</td>		
							</tr>		
						<%}%>
						</logic:notEmpty>
						
						</table>
					</td>
				</tr>
		
				<!-- final da tabela de Percentual Por Faixa de Valor -->
				
				<tr>
					<td colspan="6">
					<p>&nbsp;</p>
					</td>
				</tr>
				
				<tr>
					<td align="right" colspan="6">
						<input name="adicionarQuantidadeMaximaPrestacoes" type="button" class="bottonRightCol" value="Adicionar" align="right" tabindex="16" onclick="adicionarPrestacao(document.forms[0]);" disabled="true" />
					</td>
				</tr>
				
				<tr bgcolor="#90c7fc" height="18">
					<td align="center" colspan="6"><strong>Informações por Quantidade Máxima de Prestações</strong></td>
				</tr>

				<!-- início da tabela de Informações por qtde de prestações -->
				<tr >
					<td colspan= "8">
					<div style="width: 100%; height: 190; overflow: auto;">
						<table class="gridQtdMaxPrestacao" width="100%" border="0" bgcolor="#90c7fc">
							<tr bgcolor="#90c7fc" height="18">
								<td width="08%" align="center"><strong>Remover</strong></td>
								<td width="10%" align="center"><strong>Qtde. Máxima Prestações</strong></td>
								<td width="10%" align="center"><strong>Qtde. Máx. Prest. p/ Parcel. Permissão Especial</strong></td>
								<td width="09%" align="center"><strong>Taxa de Juros a.m.</strong></td>
								<td width="09%" align="center"><strong>Valor Mín de Entrada</strong></td>
								<td width="09%" align="center"><strong>% Tarifa Mínima</strong></td>
								<td width="09%" align="center"><strong>% Valor Reparc.</strong></td>
								<td width="09%" align="center"><strong>% Valor Débito</strong></td>
								<td width="09%" align="center"><strong>Opções do Débito</strong></td>
								<td width="09%" align="center"><strong>% Faixa Valor</strong></td>
								<td width="09%" align="center"><strong>Dias Venc. da Entrada</strong></td>
							</tr>

							<%--Esquema de paginação--%>
							<logic:present name="collectionParcelamentoQuantidadePrestacaoHelper">
								<%int cont1 = 1;%>
								<logic:iterate name="collectionParcelamentoQuantidadePrestacaoHelper" 
								id="parcelamentoQuantidadePrestacaoHelper"
								type="ParcelamentoQuantidadePrestacaoHelper">
										<%cont1 = cont1 + 1;
											if (cont1 % 2 == 0) {%>
										<tr bgcolor="#FFFFFF">
											<%} else {%>
										<tr bgcolor="#cbe5fe">
											<%}%>
											
										<td width="08%" rowspan="3">
												<logic:equal name="readOnly" value="true">
													<div align="center"><font color="#333333"> <img width="14"
										             height="14" border="0"
										             src="<bean:message key="caminho.imagens"/>Error.gif" />
									            </font></div>
												</logic:equal>
												<logic:notEqual name="readOnly" value="true">
													<div align="center"><font color="#333333"> <img width="14"
										             height="14" border="0"
										             src="<bean:message key="caminho.imagens"/>Error.gif"
		 								             onclick="javascript:if(confirm('Confirma remoção?')){redirecionarSubmit('removerParcelamentoQuantidadePrestacaoAction.do?qtdeMaxPrestacao=<%="" + parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getQuantidadeMaximaPrestacao()%> ');}" />
										            </font></div>
												</logic:notEqual>
									       </td>	
									  							       
									       <td width="10%" align="center">
												<div>${parcelamentoQuantidadePrestacaoHelper.parcelamentoQuantidadePrestacao.quantidadeMaximaPrestacao} &nbsp;</div>
											</td>
											
																       
									       <td width="10%" align="center">
												<div>${parcelamentoQuantidadePrestacaoHelper.parcelamentoQuantidadePrestacao.quantidadeMaxPrestacaoEspecial} &nbsp;</div>
											</td>
	
											<td width="09%" align="center">
												
												<logic:present name="desabilitarTaxaJuros" scope="session">
													<input type="text" style="text-align: right;font-size: xx-small;" 
														align="center" name=<%="txJuros" + parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getUltimaAlteracao().getTime()%>
														value="0,00" 
														size="4" maxlength="6" disabled="disabled" onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5)" class="gridQuantidadeMaximaPrestacoes" />
												</logic:present>
												
												<logic:notPresent name="desabilitarTaxaJuros" scope="session">
													<input type="text" style="text-align: right;font-size: xx-small;" 
														align="center" name=<%="txJuros" + parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getUltimaAlteracao().getTime()%>
														value="<%="" + Util.formatarMoedaReal(parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getTaxaJuros())%>" 
														size="4" maxlength="6" onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5)" class="gridQuantidadeMaximaPrestacoes" />
												</logic:notPresent>
											</td>
											
											<td width="09%" align="center">
												<input type="text" style="text-align: right;font-size: xx-small;" 
													align="center" name=<%="vlMinimoEntrada" + parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getUltimaAlteracao().getTime()%>
													value="<%="" + (parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getValorMinimoEntrada() != null ? Util.formatarMoedaReal(parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getValorMinimoEntrada()) : "")%>" 
													size="12" maxlength="14" onkeypress="return isCampoNumerico(event);" onkeyup="desabilitarCampoEmGridQuantidadeMaximaPrestacoes(this);formataValorMonetario(this, 11)" />
											</td>
											
											<td width="09%" align="center">
												<input type="text" style="text-align: right;font-size: xx-small;" 
													size="4" maxlength="6" align="center" 
													name=<%="percTarMinImovel" + parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getUltimaAlteracao().getTime()%>
													value="<%="" + (parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getPercentualTarifaMinimaImovel() != null ? Util.formatarMoedaReal(parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getPercentualTarifaMinimaImovel()) : "")%>"
													style="text-align:right;" onkeypress="return isCampoNumerico(event);" onkeyup="desabilitarCampoEmGridQuantidadeMaximaPrestacoes(this);formataValorMonetario(this, 5)" />
											</td>
											<td width="09%" align="center">
												<input type="text" style="text-align: right;font-size: xx-small;" 
													size="4" maxlength="6" align="center" onkeyup="formataValorMonetario(this, 5)"
													name=<%="percVlReparcelado" + parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getUltimaAlteracao().getTime()%>
													value="<%="" + (parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getPercentualValorReparcelado() != null ? Util.formatarMoedaReal(parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getPercentualValorReparcelado()) : "")%>"
													style="text-align:right;" onkeypress="return isCampoNumerico(event);" />
											</td>
											
											<td width="09%" align="center">
												<input type="text" style="text-align: right;font-size: xx-small;" 
													size="4" maxlength="6" align="center" 
													name=<%="percMinEntrada" + parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getUltimaAlteracao().getTime()%>
													value="<%="" + (parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getPercentualMinimoEntrada() != null ? Util.formatarMoedaReal(parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getPercentualMinimoEntrada()) : "")%>"
													style="text-align:right;" onkeypress="return isCampoNumerico(event);" onkeyup="desabilitarCampoEmGridQuantidadeMaximaPrestacoes(this);formataValorMonetario(this, 5)" />
											</td>
											
											<td width="09%" align="center">
												<% if (parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getIndicadorEntradaParcelamento() != null){ %>
													<% if (parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getIndicadorEntradaParcelamento().intValue() == 1){ %>
														<input type="radio" name="<%="indicadorEntradaParcelamento" + parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getUltimaAlteracao().getTime()%>"
														checked="checked" value="1" />original
														<input type="radio" name="<%="indicadorEntradaParcelamento" + parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getUltimaAlteracao().getTime()%>"
														 value="2" />atualizado
													<% } else { %>
														<input type="radio" name="<%="indicadorEntradaParcelamento" + parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getUltimaAlteracao().getTime()%>"
														value="1" />original
														<input type="radio" name="<%="indicadorEntradaParcelamento" + parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getUltimaAlteracao().getTime()%>"
														checked="checked" value="2" />atualizado
													<%} %>
												<%} else { %>
														<input type="radio" name="<%="indicadorEntradaParcelamento" + parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getUltimaAlteracao().getTime()%>"
														value="1" />original
														<input type="radio" name="<%="indicadorEntradaParcelamento" + parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getUltimaAlteracao().getTime()%>"
														 value="2" />atualizado
												<%} %>
											</td>
												
											<td width="09%" align="center">
												<div align="center">
													<logic:notEmpty name="parcelamentoQuantidadePrestacaoHelper" property="collectionParcelamentoFaixaValor" >
														<a name="<%="consultar" + parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getUltimaAlteracao().getTime()%>"
														href="javascript:abrirPopupFaixaValor('160','520',
														<%="" + parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getQuantidadeMaximaPrestacao()%>);"
														>Consultar</a>
													</logic:notEmpty>
													&nbsp;
												</div> 
											</td>

											<td width="09%" align="center">
												<input type="text" style="text-align: right;font-size: xx-small;" 
													size="2" maxlength="4" 
													name=<%="numeroDiasVencimentoDaEntrada" + parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getUltimaAlteracao().getTime()%>
													value="<%="" + (parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getNumeroDiasVencimentoDaEntrada() != null ? parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getNumeroDiasVencimentoDaEntrada() : "")%>"
													style="text-align:right;" onkeypress="return isCampoNumerico(event);"/>
											</td>
										</tr>

										<% if (cont1 % 2 == 0) { %>
										<tr bgcolor="#FFFFFF">
										<%} else {%>
										<tr bgcolor="#cbe5fe">
										<%}%>
											<td colspan="3" align="center">
											    <strong>Meses entre Parcelas:</strong>
											    &nbsp;
												<input type="text" style="text-align: right;font-size: xx-small;" 
													size="2" maxlength="4"
													name=<%="numeroMesesEntreParcelas" + parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getUltimaAlteracao().getTime()%>
													value="<%="" + (parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getNumeroMesesEntreParcelas() != null ? parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getNumeroMesesEntreParcelas() : "")%>"
													style="text-align:right;" onkeypress="return isCampoNumerico(event);" />
											</td>

											<td colspan="3" align="center">
											    <strong>Parcelas a Lançar:</strong>
											    &nbsp;
												<input type="text" style="text-align: right;font-size: xx-small;" 
													size="2" maxlength="4"
													name=<%="numeroParcelasALancar" + parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getUltimaAlteracao().getTime()%>
													value="<%="" + (parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getNumeroParcelasALancar() != null ? parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getNumeroParcelasALancar() : "")%>"
													style="text-align:right;" onkeypress="return isCampoNumerico(event);" />
											</td>

											<td colspan="4" align="center">
											    <strong>Meses para Início da Cobran.:</strong>
											    &nbsp;
												<input type="text" style="text-align: right;font-size: xx-small;" 
													size="4" maxlength="6"
													name=<%="numeroMesesInicioCobranca" + parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getUltimaAlteracao().getTime()%>
													value="<%="" + (parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getNumeroMesesInicioCobranca() != null ? parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getNumeroMesesInicioCobranca() : "")%>"
													style="text-align:right;" onkeypress="return isCampoNumerico(event);" />
											</td>
										</tr>
										
										<% if (cont1 % 2 == 0) { %>
										<tr bgcolor="#FFFFFF">
										<%} else {%>
										<tr bgcolor="#cbe5fe">
										<%}%>
											<td colspan="2" align="center">
											<table>
											<td>
											    <strong>% Desc. Juros Mora:</strong></td>
												<td><input type="text" style="text-align: right;font-size: xx-small;" 
													size="3" maxlength="6"
													name=<%="percentualDescontoJurosMora" + parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getUltimaAlteracao().getTime()%>
													value="<%="" + (parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getPercentualDescontoJurosMora() != null ? Util.formatarMoedaReal(parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getPercentualDescontoJurosMora()) : "")%>"
													style="text-align:right;" onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"/></td>
											</table>
											</td>

											<td colspan="2" align="center">
											    <strong>% Desc. Multa:</strong>
												<input type="text" style="text-align: right;font-size: xx-small;" 
													size="3" maxlength="6"
													name=<%="percentualDescontoMulta" + parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getUltimaAlteracao().getTime()%>
													value="<%="" + (parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getPercentualDescontoMulta() != null ? Util.formatarMoedaReal(parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getPercentualDescontoMulta()) : "")%>"
													style="text-align:right;" onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"/>
											</td>

											<td colspan="3" align="center">
											<table>
											<td>
											    <strong>% Desc. Correção Monetária:</strong></td>
												<td><input type="text" style="text-align: right;font-size: xx-small;" 
													size="3" maxlength="6"
													name=<%="percentualDescontoCorrecaoMonetaria" + parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getUltimaAlteracao().getTime()%>
													value="<%="" + (parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getPercentualDescontoCorrecaoMonetaria() != null ? Util.formatarMoedaReal(parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getPercentualDescontoCorrecaoMonetaria()) : "")%>"
													style="text-align:right;" onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);"/></td>
											</table>
											</td>
											<td colspan="3" align="center">
											    <strong>% Entrada Sugerida:</strong>
												<input type="text" style="text-align: right;font-size: xx-small;" 
													size="3" maxlength="6"
													name=<%="percentualEntradaSugerida" + parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getUltimaAlteracao().getTime()%>
													value="<%="" + (parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getPercentualEntradaSugerida() != null ? Util.formatarMoedaReal(parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getPercentualEntradaSugerida()) : "")%>"
													style="text-align:right;" onkeypress="return isCampoNumerico(event);" onkeyup="formataValorMonetario(this, 5);" />
											</td>
											
										</tr>										
								</logic:iterate>
							</logic:present>
							
							</table>
						</div>
					</td>
				</tr>
              	<!-- final da tabela de Informações por qtde de prestações -->

				
				<tr>
					<td align="left">
						<input type="button" name="Button" class="bottonRightCol" value="Fechar" tabindex="17" onclick="window.close();" />
                    </td>
                    <td align="right" colspan="5">
						<logic:equal name="readOnly" value="true">
							<input type="button" name="Button" class="bottonRightCol" value="Inserir" tabindex="18" readonly="true"/>		
						</logic:equal>
						<logic:notEqual name="readOnly" value="true">
							<input type="button" name="Button" class="bottonRightCol" value="Inserir" tabindex="18" onClick="validarForm(document.forms[0]);" />							
						</logic:notEqual>                    
                    </td>
				</tr>
				
				
			</table>
			<p>&nbsp;</p>

	</table>
</html:form>
<!-- parcelamento_perfil_inserir_prestacoes_popup.jsp -->
</html:html>