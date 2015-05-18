<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page isELIgnored="false"%>
<%@ page import="gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao" %>
<%@ page import="gcom.gui.cobranca.BoletoBancarioHelper" %>
<%@ page import="gcom.cadastro.cliente.ClienteTipo"%>
<%@ page import="gcom.cadastro.cliente.ClienteImovel" isELIgnored="false"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page import="gcom.gui.cobranca.EfetuarParcelamentoDebitosActionForm"%>
<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="EfetuarParcelamentoDebitosActionForm" dynamicJavascript="false" />

<logic:equal name="exibirParcelamentoCobrancaBancaria" value="S">

<script language="JavaScript">

function verificaExistenciaMatricula(){
	var form = document.forms[0];
	
	var ObSituacoesCobrancaDescricoes = returnObject(form,"situacoesCobrancaDescricoes");
	var ObjIndicadorPrimeiroAcesso = returnObject(form,"indicadorPrimeiroAcesso");
	
	if(ObSituacoesCobrancaDescricoes.value != "" && ObjIndicadorPrimeiroAcesso.value == "1"){
		alert('Atenção: imóvel em situação de cobrança: ' + ObSituacoesCobrancaDescricoes.value);
		ObjIndicadorPrimeiroAcesso.value = "2";
	}
	
	if( form.matriculaImovel.value == "" ){
	 	var ObjNomeCliente = returnObject(form,"nomeCliente");
	 	var ObjSituacaoAgua = returnObject(form,"situacaoAgua");
	 	var ObjSituacaoEsgoto = returnObject(form,"situacaoEsgoto");
	 	var ObjCpfCnpj = returnObject(form,"cpfCnpj");
	 	var ObjInicioIntervaloParcelamento = returnObject(form,"inicioIntervaloParcelamento");
	 	var ObjFimIntervaloParcelamento = returnObject(form,"fimIntervaloParcelamento");
	 	var ObjResolucaoDiretoria = returnObject(form,"resolucaoDiretoria");
	 	
		ObjNomeCliente.value = "";
		ObjSituacaoAgua.value = "";
		ObjSituacaoEsgoto.value = "";
		ObjCpfCnpj.value = "";
	 	ObjInicioIntervaloParcelamento.value = "";
	 	ObjFimIntervaloParcelamento.value = "";
	 	ObjResolucaoDiretoria.value = "";
	 	ObSituacoesCobrancaDescricoes.value = "";
	 	ObjIndicadorPrimeiroAcesso.value = "1";
	 	
		//if(!form.indicadorCreditoARealizar[0].checked && !form.indicadorCreditoARealizar[1].checked ){
		//	form.indicadorCreditoARealizar[1].checked = false;
		//}	
		
		//if(!form.indicadorDebitosACobrar[0].checked && !form.indicadorDebitosACobrar[1].checked ){
		//	form.indicadorDebitosACobrar[1].checked = false;
		//}
			
		// Se existir indicador de Restabelecimento no caso de imovel surprimido
		//if( form.indicadorRestabelecimento ){
			//if(!form.indicadorRestabelecimento[0].checked && !form.indicadorRestabelecimento[1].checked  ){
				//form.indicadorRestabelecimento[1].checked = false;
			//}	 
		//}
		
		//if(!form.indicadorContasRevisao[0].checked && !form.indicadorContasRevisao[1].checked ){
		//	form.indicadorContasRevisao[1].checked = false;
		//}
			
		//if(!form.indicadorGuiasPagamento[0].checked && !form.indicadorGuiasPagamento[1].checked ){
		//	form.indicadorGuiasPagamento[1].checked = false;
		//}
			
		if(!form.indicadorAcrescimosImpotualidade[0].checked && !form.indicadorAcrescimosImpotualidade[1].checked ){
			form.indicadorAcrescimosImpotualidade[1].checked = false;
		}

		if(!form.indicadorParcelamentoCobrancaBancaria[0].checked && !form.indicadorParcelamentoCobrancaBancaria[1].checked ){
			form.indicadorParcelamentoCobrancaBancaria[0].checked = false;
		}		

	    document.getElementById("endereco").innerHTML = '&nbsp;';
	    document.getElementById("numeroParcelamento").innerHTML = '&nbsp;';
	    document.getElementById("numeroReparcelamento").innerHTML = '&nbsp;';
	    document.getElementById("numeroReparcelamentoConsecutivos").innerHTML = '&nbsp;';
	    document.getElementById("valorTotalContaValores").innerHTML = '&nbsp;';
	    document.getElementById("valorGuiasPagamento").innerHTML = '&nbsp;';
	    document.getElementById("valorAcrescimosImpontualidade").innerHTML = '&nbsp;';
	    document.getElementById("valorDebitoACobrarServico").innerHTML = '&nbsp;';
	    document.getElementById("valorDebitoACobrarParcelamento").innerHTML = '&nbsp;';
	    document.getElementById("valorCreditoARealizar").innerHTML = '&nbsp;';
	    document.getElementById("valorDebitoTotalAtualizado").innerHTML = '&nbsp;';
	    document.getElementById("valorTotalSucumbencia").innerHTML = '&nbsp;';
	    document.getElementById("valorAcrescimosSucumbencia").innerHTML = '&nbsp;';
	}

	//if(!form.indicadorCreditoARealizar[0].checked && !form.indicadorCreditoARealizar[1].checked){
	//	form.indicadorCreditoARealizar[0].checked = true;
	//}	
	
	//if(!form.indicadorDebitosACobrar[0].checked && !form.indicadorDebitosACobrar[1].checked){
	//	form.indicadorDebitosACobrar[0].checked = true;
	//}
		
	//Se existir indicador de Restabelecimento no caso de imovel surprimido
	//if( form.indicadorRestabelecimento ){
		//if(!form.indicadorRestabelecimento[0].checked && !form.indicadorRestabelecimento[1].checked ){
			//form.indicadorRestabelecimento[0].checked = true;
		//}	
	//}
	
	//if(!form.indicadorContasRevisao[0].checked && !form.indicadorContasRevisao[1].checked ){
	//	form.indicadorContasRevisao[0].checked = true;
	//}	
	
	//if(!form.indicadorGuiasPagamento[0].checked && !form.indicadorGuiasPagamento[1].checked ){
	//	form.indicadorGuiasPagamento[0].checked = true;
	//}
		
	if(!form.indicadorAcrescimosImpotualidade[0].checked && !form.indicadorAcrescimosImpotualidade[1].checked ){
		form.indicadorAcrescimosImpotualidade[0].checked = true;
	}

	if(!form.indicadorParcelamentoCobrancaBancaria[0].checked && !form.indicadorParcelamentoCobrancaBancaria[1].checked ){
		form.indicadorParcelamentoCobrancaBancaria[1].checked = true;
	}	
}

function validaTodosRadioButton(){
	var form = document.forms[0];
	var mensagem = "";
	if(form.indicadorRestabelecimento){
		if(validaRadioButton(form.indicadorRestabelecimento,"'Com restabelecimento?'") != ""){
			mensagem = mensagem + validaRadioButton(form.indicadorRestabelecimento,"'Com restabelecimento?'")+"\n";
		}
	}
	if(validaRadioButton(form.indicadorParcelamentoCobrancaBancaria,"'Parcelamento de Cobrança Bancária?'") != ""){
		mensagem = mensagem + validaRadioButton(form.indicadorParcelamentoCobrancaBancaria,"'Parcelamento de Cobrança Bancária?'")+"\n";
	}
	if(validaRadioButton(form.indicadorCobrancaParcelamento,"'Cobrança do Parcelamento'") != ""){
		mensagem = mensagem + validaRadioButton(form.indicadorCobrancaParcelamento,"'Cobrança do Parcelamento'")+"\n";
	}
	if(validaRadioButton(form.indicadorContasRevisao,"'Considerar contas em revisão?'") != ""){
		mensagem = mensagem + validaRadioButton(form.indicadorContasRevisao,"'Considerar contas em revisão?'")+"\n";
	}
	if(validaRadioButton(form.indicadorGuiasPagamento,"'Considerar guias de pagamento?'") != ""){
		mensagem = mensagem + validaRadioButton(form.indicadorGuiasPagamento,"'Considerar guias de pagamento?'")+"\n";
	}
	if(validaRadioButton(form.indicadorAcrescimosImpotualidade,"'Considerar Acréscimos por Impontualidade?'") != ""){
		mensagem = mensagem + validaRadioButton(form.indicadorAcrescimosImpotualidade,"'Considerar Acréscimos por Impontualidade?'")+"\n";
	}
	if(validaRadioButton(form.indicadorCreditoARealizar,"'Considerar créditos a realizar?'") != ""){
		mensagem = mensagem + validaRadioButton(form.indicadorCreditoARealizar,"'Considerar créditos a realizar?'")+"\n";
	}	
	if(validaRadioButton(form.indicadorDebitosACobrar,"'Considerar débitos a cobrar?'") != ""){
		mensagem = mensagem + validaRadioButton(form.indicadorDebitosACobrar,"'Considerar débitos a cobrar?'")+"\n";
	}
 
	if(mensagem != ""){
		alert(mensagem);
		return false;
	}else{
		return true;
	}
}

</script>

</logic:equal>

<logic:notEqual name="exibirParcelamentoCobrancaBancaria" value="S">

<script language="JavaScript">

function verificaExistenciaMatricula(){
	var form = document.forms[0];
	
	var ObSituacoesCobrancaDescricoes = returnObject(form,"situacoesCobrancaDescricoes");
	var ObjIndicadorPrimeiroAcesso = returnObject(form,"indicadorPrimeiroAcesso");
	
	if(ObSituacoesCobrancaDescricoes.value != "" && ObjIndicadorPrimeiroAcesso.value == "1"){
		alert('Atenção: imóvel em situação de cobrança: ' + ObSituacoesCobrancaDescricoes.value);
		ObjIndicadorPrimeiroAcesso.value = "2";
	}
	
	if( form.matriculaImovel.value == "" ){
	 	var ObjNomeCliente = returnObject(form,"nomeCliente");
	 	var ObjSituacaoAgua = returnObject(form,"situacaoAgua");
	 	var ObjSituacaoEsgoto = returnObject(form,"situacaoEsgoto");
	 	var ObjCpfCnpj = returnObject(form,"cpfCnpj");
	 	var ObjInicioIntervaloParcelamento = returnObject(form,"inicioIntervaloParcelamento");
	 	var ObjFimIntervaloParcelamento = returnObject(form,"fimIntervaloParcelamento");
	 	var ObjResolucaoDiretoria = returnObject(form,"resolucaoDiretoria");
	 	
		ObjNomeCliente.value = "";
		ObjSituacaoAgua.value = "";
		ObjSituacaoEsgoto.value = "";
		ObjCpfCnpj.value = "";
	 	ObjInicioIntervaloParcelamento.value = "";
	 	ObjFimIntervaloParcelamento.value = "";
	 	ObjResolucaoDiretoria.value = "";
	 	ObSituacoesCobrancaDescricoes.value = "";
	 	ObjIndicadorPrimeiroAcesso.value = "1";
	 	
		//if(!form.indicadorCreditoARealizar[0].checked && !form.indicadorCreditoARealizar[1].checked ){
		//	form.indicadorCreditoARealizar[1].checked = false;
		//}	
		
		//if(!form.indicadorDebitosACobrar[0].checked && !form.indicadorDebitosACobrar[1].checked ){
		//	form.indicadorDebitosACobrar[1].checked = false;
		//}
			
		// Se existir indicador de Restabelecimento no caso de imovel surprimido
		//if( form.indicadorRestabelecimento ){
			//if(!form.indicadorRestabelecimento[0].checked && !form.indicadorRestabelecimento[1].checked  ){
				//form.indicadorRestabelecimento[1].checked = false;
			//}	 
		//}
		
		//if(!form.indicadorContasRevisao[0].checked && !form.indicadorContasRevisao[1].checked ){
		//	form.indicadorContasRevisao[1].checked = false;
		//}
			
		//if(!form.indicadorGuiasPagamento[0].checked && !form.indicadorGuiasPagamento[1].checked ){
		//	form.indicadorGuiasPagamento[1].checked = false;
		//}
			
		if(!form.indicadorAcrescimosImpotualidade[0].checked && !form.indicadorAcrescimosImpotualidade[1].checked ){
			form.indicadorAcrescimosImpotualidade[1].checked = false;
		}

		document.getElementById("endereco").innerHTML = '&nbsp;';
	    document.getElementById("numeroParcelamento").innerHTML = '&nbsp;';
	    document.getElementById("numeroReparcelamento").innerHTML = '&nbsp;';
	    document.getElementById("numeroReparcelamentoConsecutivos").innerHTML = '&nbsp;';
	    document.getElementById("valorTotalContaValores").innerHTML = '&nbsp;';
	    document.getElementById("valorGuiasPagamento").innerHTML = '&nbsp;';
	    document.getElementById("valorAcrescimosImpontualidade").innerHTML = '&nbsp;';
	    document.getElementById("valorDebitoACobrarServico").innerHTML = '&nbsp;';
	    document.getElementById("valorDebitoACobrarParcelamento").innerHTML = '&nbsp;';
	    document.getElementById("valorCreditoARealizar").innerHTML = '&nbsp;';
	    document.getElementById("valorDebitoTotalAtualizado").innerHTML = '&nbsp;';
	    document.getElementById("valorTotalSucumbencia").innerHTML = '&nbsp;';
	    document.getElementById("valorAcrescimosSucumbencia").innerHTML = '&nbsp;';
	}

	//if(!form.indicadorCreditoARealizar[0].checked && !form.indicadorCreditoARealizar[1].checked){
	//	form.indicadorCreditoARealizar[0].checked = true;
	//}	
	
	//if(!form.indicadorDebitosACobrar[0].checked && !form.indicadorDebitosACobrar[1].checked){
	//	form.indicadorDebitosACobrar[0].checked = true;
	//}
		
	// Se existir indicador de Restabelecimento no caso de imovel surprimido
//	if( form.indicadorRestabelecimento ){
		//if(!form.indicadorRestabelecimento[0].checked && !form.indicadorRestabelecimento[1].checked ){
			//form.indicadorRestabelecimento[0].checked = true;
		//}	
//	}
	
	//if(!form.indicadorContasRevisao[0].checked && !form.indicadorContasRevisao[1].checked ){
	//	form.indicadorContasRevisao[0].checked = true;
	//}	
	
	//if(!form.indicadorGuiasPagamento[0].checked && !form.indicadorGuiasPagamento[1].checked ){
	//	form.indicadorGuiasPagamento[0].checked = true;
	//}
		
	if(!form.indicadorAcrescimosImpotualidade[0].checked && !form.indicadorAcrescimosImpotualidade[1].checked ){
		form.indicadorAcrescimosImpotualidade[0].checked = true;
	}

}

function validaTodosRadioButton(){
	var form = document.forms[0];
	var mensagem = "";
	if(form.indicadorRestabelecimento){
		if(validaRadioButton(form.indicadorRestabelecimento,"'Com restabelecimento?'") != ""){
			mensagem = mensagem + validaRadioButton(form.indicadorRestabelecimento,"'Com restabelecimento?'")+"\n";
		}
	}
	if(validaRadioButton(form.indicadorCobrancaParcelamento,"'Cobrança do Parcelamento'") != ""){
		mensagem = mensagem + validaRadioButton(form.indicadorCobrancaParcelamento,"'Cobrança do Parcelamento'")+"\n";
	}
	if(validaRadioButton(form.indicadorContasRevisao,"'Considerar contas em revisão?'") != ""){
		mensagem = mensagem + validaRadioButton(form.indicadorContasRevisao,"'Considerar contas em revisão?'")+"\n";
	}
	if(validaRadioButton(form.indicadorGuiasPagamento,"'Considerar guias de pagamento?'") != ""){
		mensagem = mensagem + validaRadioButton(form.indicadorGuiasPagamento,"'Considerar guias de pagamento?'")+"\n";
	}
	if(validaRadioButton(form.indicadorAcrescimosImpotualidade,"'Considerar Acréscimos por Impontualidade?'") != ""){
		mensagem = mensagem + validaRadioButton(form.indicadorAcrescimosImpotualidade,"'Considerar Acréscimos por Impontualidade?'")+"\n";
	}
	if(validaRadioButton(form.indicadorCreditoARealizar,"'Considerar créditos a realizar?'") != ""){
		mensagem = mensagem + validaRadioButton(form.indicadorCreditoARealizar,"'Considerar créditos a realizar?'")+"\n";
	}	
	if(validaRadioButton(form.indicadorDebitosACobrar,"'Considerar débitos a cobrar?'") != ""){
		mensagem = mensagem + validaRadioButton(form.indicadorDebitosACobrar,"'Considerar débitos a cobrar?'")+"\n";
	}
 
	if(mensagem != ""){
		alert(mensagem);
		return false;
	}else{
		return true;
	}
}

</script>

</logic:notEqual>

<logic:equal name="EfetuarParcelamentoDebitosActionForm" property="indicadorPessoaFisicaJuridica" value="<%=ClienteTipo.INDICADOR_PESSOA_FISICA.toString()%>">
	<script>
	function validateEfetuarParcelamentoDebitosActionForm(form) {
		if (bCancel) 
			return true; 
		else 
			return validateCaracterEspecial(form) 
				&& validateRequired(form) 
				&& validateLong(form) 
				&& validateDate(form) 
				&& validaTodosRadioButton() 
				&& validaMesAnoInicio(form.inicioIntervaloParcelamento)
				&& validaMesAnoFim(form.fimIntervaloParcelamento)
				&& validaMesAnoInicioMesAnoFimRequerid()
				&& validaMesAnoInicioMenorQueMesAnoFim()
				&& validarCpf();
	}

	function cpf(){ 
		this.aa = new Array("cpfClienteParcelamentoDigitado", "CPF inválido.", new Function ("varName", " return this[varName];"));
	} 

	function validarCpf() {
		var form = document.forms[0];

		if (form.cpfClienteParcelamento != null && 
				form.cpfClienteParcelamento.value == '' && 
				form.cpfClienteParcelamentoDigitado.value == '') {
			alert('Informe CPF.');

			return false;
		} else {
			if (!isBrancoOuNulo(form.cpfClienteParcelamentoDigitado)) {
				return validateCpf(form);
			} else {
				return true;
			}
		}
	}
	</script>
</logic:equal>

<logic:notEqual name="EfetuarParcelamentoDebitosActionForm" property="indicadorPessoaFisicaJuridica" value="<%=ClienteTipo.INDICADOR_PESSOA_FISICA.toString()%>">
	<script>
	function validateEfetuarParcelamentoDebitosActionForm(form) {                                                                   
		if (bCancel) 
			return true; 
		else 
			return validateCaracterEspecial(form) 
				&& validateRequired(form) 
				&& validateLong(form) 
				&& validateDate(form) 
				&& validaTodosRadioButton() 
				&& validaMesAnoInicio(form.inicioIntervaloParcelamento)
				&& validaMesAnoFim(form.fimIntervaloParcelamento)
				&& validaMesAnoInicioMesAnoFimRequerid()
				&& validaMesAnoInicioMenorQueMesAnoFim()
				&& validarCnpj();
	}

	function cnpj(){
		this.aa = new Array("cpfClienteParcelamentoDigitado", "CNPJ inválido.", new Function ("varName", " return this[varName];"));
	}

	function validarCnpj() {
		var form = document.forms[0];

		if (form.cpfClienteParcelamento != null && 
				form.cpfClienteParcelamento.value == '' && 
				form.cpfClienteParcelamentoDigitado.value == '') {
			alert('Informe CNPJ.');

			return false;
		} else {
			if (!isBrancoOuNulo(form.cpfClienteParcelamentoDigitado)) {
				return validateCnpj(form);
			} else {
				return true;
			}
		}
	}
	</script>
</logic:notEqual>

<script>

	function validaEnterMatriculaImovel() {
		var form = document.forms[0];
		
		var obj = form.idClienteRelacaoImovelSelecionado;
		if(obj != null && obj != undefined && obj != 'undefined'){
			form.idClienteRelacaoImovelSelecionado.value = '';
		}
		
		validaEnterComMensagem(event, 'exibirEfetuarParcelamentoDebitosAction.do?menu=sim', 'matriculaImovel','Matrícula do Imóvel');
	}
	
</script>


<script language="JavaScript">
<!-- Begin 
 
var bCancel = false; 

function habilitaMotivoRevisao() {
	 var form = document.forms[0];
	 form.idMotivoRevisao.disabled = !form.indicadorContasRevisao[0].checked;
	
}

function isBrancoOuNulo(obj){
	if(obj == null || obj == undefined || obj == 'undefined' || obj.value == ''){
		return true;
	}else{
		return false;
	}
}

function validaMesAnoInicio(mesAno){
	if(mesAno.value != ""){
		return verificaAnoMesMensagemPersonalizada(mesAno,"Período Inicial do Intervalo do Parcelamento inválido.");
	}else{
		return true;
	}
}

function validaMesAnoFim(mesAno){
	if(mesAno.value != ""){
		return verificaAnoMesMensagemPersonalizada(mesAno,"Período Final do Intervalo do Parcelamento inválido.");
	}else{
		return true;
	}
}

function validaMesAnoInicioMenorQueMesAnoFim(){

var form = document.forms[0];
var mesAnoInicio = form.inicioIntervaloParcelamento.value;
var mesAnoFim = form.fimIntervaloParcelamento.value;

 if ((mesAnoInicio!= '' && mesAnoFim != '') &&
  (comparaMesAno(mesAnoInicio, ">", mesAnoFim))){
 	alert('Período Inicial do Intervalo do Parcelamento não pode ser maior que Período Final do Intervalo do Parcelamento.')
 	return false;
 }else{
 	return true;
 }
}

function validaMesAnoInicioMesAnoFimRequerid(){

var form = document.forms[0];
var mesAnoInicio = form.inicioIntervaloParcelamento.value;
var mesAnoFim = form.fimIntervaloParcelamento.value;

 if (mesAnoInicio == '' && mesAnoFim != ''){
 	alert('Informe Período Inicial do Intervalo do Parcelamento.')
 	return false;
 }else if (mesAnoInicio != '' && mesAnoFim == ''){
 	alert('Informe Período Final do Intervalo do Parcelamento.')
 	return false;	
 }else {
 	return true;
 }
}

function caracteresespeciais() { 
	this.aa = new Array("matriculaImovel", "Matrícula do Imóvel possui caracteres especiais.", new Function ("varName", " return this[varName];"));
	this.ab = new Array("inicioIntervaloParcelamento", "Período Inicial do Intervalo do Parcelamento possui caracteres especiais.", new Function ("varName", " return this[varName];"));
	this.ac = new Array("fimIntervaloParcelamento", "Período Final do Intervalo do Parcelamento possui caracteres especiais.", new Function ("varName", " return this[varName];"));
} 

function required() { 
	this.aa = new Array("matriculaImovel", "Informe Matrícula do Imóvel.", new Function ("varName", " return this[varName];"));
	this.bb = new Array("idClienteParcelamento", "Informe Cliente Responsável pelo Parcelamento.", new Function ("varName", " return this[varName];"));
	this.ab = new Array("dataParcelamento", "Informe Data do Parcelamento.", new Function ("varName", " return this[varName];"));
	this.ac = new Array("resolucaoDiretoria", "Informe RD do Parcelamento.", new Function ("varName", " return this[varName];"));
	this.ad = new Array("indicadorContasRevisao", "Informe Considerar Contas em Revisão (Sim ou Não).", new Function ("varName", " return this[varName];"));
	this.ae = new Array("indicadorGuiasPagamento", "Informe Considerar Guias de Pagamento (Sim ou Não).", new Function ("varName", " return this[varName];"));
	this.af = new Array("indicadorAcrescimosImpotualidade", "Informe Considerar Acréscimos por Impontualidade (Sim ou Não).", new Function ("varName", " return this[varName];"));
	this.ag = new Array("indicadorDebitosACobrar", "Informe Considerar Débitos a Cobrar (Sim ou Não).", new Function ("varName", " return this[varName];"));
	this.ah = new Array("indicadorCreditoARealizar", "Informe Considerar Créditos a Realizar (Sim ou Não).", new Function ("varName", " return this[varName];"));	
} 

function IntegerValidations () { 
	this.aa = new Array("matriculaImovel", "Matrícula do Imóvel deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
} 

function DateValidations () { 
	this.aa = new Array("dataParcelamento", "Data do Parcelamento inválida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
} 


function habilitarPesquisaCliente(form) {
	abrirPopup('exibirPesquisarClienteAction.do?indicadorUsoTodos=1', 520, 800);
}

function limparCliente() {
	var form = document.forms[0];
	form.idClienteParcelamento.value = "";
	form.nomeClienteParcelamento.value = "";
	form.foneClienteParcelamento.value = "";
	form.cpfClienteParcelamento.value = "";
	form.cpfClienteParcelamento.style.display = "none";
	form.cpfClienteParcelamentoDigitado.value = "";
	form.cpfClienteParcelamentoDigitado.style.display = "";
	form.cpfInexistente.value = "";
}

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
    var form = document.forms[0];

    if(tipoConsulta == 'imovel'){
      limparForm();      
      form.matriculaImovel.value = codigoRegistro;
      
      var obj = form.idClienteRelacaoImovelSelecionado;
      if(obj != null && obj != undefined && obj != 'undefined'){
      	form.idClienteRelacaoImovelSelecionado.value = '';
      }
      
      form.action = 'exibirEfetuarParcelamentoDebitosAction.do'
      form.submit();
    }
    
    if (tipoConsulta == 'cliente') {
		form.idClienteParcelamento.value = codigoRegistro;
		form.nomeClienteParcelamento.value = descricaoRegistro;
        form.action = 'efetuarParcelamentoDebitosWizardAction.do?action=exibirEfetuarParcelamentoDebitosProcesso1Action'
	    form.submit();
	}
}

function verificaLimparForm(){
	var form = document.forms[0];
	var matriculaImovel = form.matriculaImovel.value;
	
	if (form.inscricaoImovel.value != null && form.inscricaoImovel.value != ""){
		window.location.href = 'exibirEfetuarParcelamentoDebitosAction.do?guardarMatriculaImovel='+ matriculaImovel;
	}
}

function limparForm(){
	window.location.href = 'exibirEfetuarParcelamentoDebitosAction.do?menu=sim';
}

function verificaExistenciaMatricula(){
	var form = document.forms[0];
	
	var ObSituacoesCobrancaDescricoes = returnObject(form,"situacoesCobrancaDescricoes");
	var ObjIndicadorPrimeiroAcesso = returnObject(form,"indicadorPrimeiroAcesso");
	
	if(ObSituacoesCobrancaDescricoes.value != "" && ObjIndicadorPrimeiroAcesso.value == "1"){
		alert('Atenção: imóvel em situação de cobrança: ' + ObSituacoesCobrancaDescricoes.value);
		ObjIndicadorPrimeiroAcesso.value = "2";
	}
	
	if( form.matriculaImovel.value == "" ){
	 	var ObjNomeCliente = returnObject(form,"nomeCliente");
	 	var ObjSituacaoAgua = returnObject(form,"situacaoAgua");
	 	var ObjSituacaoEsgoto = returnObject(form,"situacaoEsgoto");
	 	var ObjCpfCnpj = returnObject(form,"cpfCnpj");
	 	var ObjInicioIntervaloParcelamento = returnObject(form,"inicioIntervaloParcelamento");
	 	var ObjFimIntervaloParcelamento = returnObject(form,"fimIntervaloParcelamento");
	 	var ObjResolucaoDiretoria = returnObject(form,"resolucaoDiretoria");
	 	
		ObjNomeCliente.value = "";
		ObjSituacaoAgua.value = "";
		ObjSituacaoEsgoto.value = "";
		ObjCpfCnpj.value = "";
	 	ObjInicioIntervaloParcelamento.value = "";
	 	ObjFimIntervaloParcelamento.value = "";
	 	ObjResolucaoDiretoria.value = "";
	 	ObSituacoesCobrancaDescricoes.value = "";
	 	ObjIndicadorPrimeiroAcesso.value = "1";
	 	
		if(!form.indicadorCreditoARealizar[0].checked && !form.indicadorCreditoARealizar[1].checked ){
			form.indicadorCreditoARealizar[1].checked = false;
		}	
		
		if(!form.indicadorDebitosACobrar[0].checked && !form.indicadorDebitosACobrar[1].checked ){
			form.indicadorDebitosACobrar[1].checked = false;
		}	
		// Se existir indicador de Restabelecimento no caso de imovel surprimido
		//if( form.indicadorRestabelecimento ){
			//if(!form.indicadorRestabelecimento[0].checked && !form.indicadorRestabelecimento[1].checked  ){
				//form.indicadorRestabelecimento[1].checked = false;
			//}	 
		//}
		if(!form.indicadorContasRevisao[0].checked && !form.indicadorContasRevisao[1].checked ){
			form.indicadorContasRevisao[1].checked = false;
		}	
		if(!form.indicadorGuiasPagamento[0].checked && !form.indicadorGuiasPagamento[1].checked ){
			form.indicadorGuiasPagamento[1].checked = false;
		}	
		if(!form.indicadorAcrescimosImpotualidade[0].checked && !form.indicadorAcrescimosImpotualidade[1].checked ){
			form.indicadorAcrescimosImpotualidade[1].checked = false;
		}	

	    document.getElementById("endereco").innerHTML = '&nbsp;';
	    document.getElementById("numeroParcelamento").innerHTML = '&nbsp;';
	    document.getElementById("numeroReparcelamento").innerHTML = '&nbsp;';
	    document.getElementById("numeroReparcelamentoConsecutivos").innerHTML = '&nbsp;';
	    document.getElementById("valorTotalContaValores").innerHTML = '&nbsp;';
	    document.getElementById("valorGuiasPagamento").innerHTML = '&nbsp;';
	    document.getElementById("valorAcrescimosImpontualidade").innerHTML = '&nbsp;';
	    document.getElementById("valorDebitoACobrarServico").innerHTML = '&nbsp;';
	    document.getElementById("valorDebitoACobrarParcelamento").innerHTML = '&nbsp;';
	    document.getElementById("valorCreditoARealizar").innerHTML = '&nbsp;';
	    document.getElementById("valorDebitoTotalAtualizado").innerHTML = '&nbsp;';
	    document.getElementById("valorTotalSucumbencia").innerHTML = '&nbsp;';
	    document.getElementById("valorAcrescimosSucumbencia").innerHTML = '&nbsp;';
	}
	if(!form.indicadorCreditoARealizar[0].checked && !form.indicadorCreditoARealizar[1].checked){
		form.indicadorCreditoARealizar[0].checked = true;
	}	
	
	if(!form.indicadorDebitosACobrar[0].checked && !form.indicadorDebitosACobrar[1].checked){
		form.indicadorDebitosACobrar[0].checked = true;
	}	
	// Se existir indicador de Restabelecimento no caso de imovel surprimido
//	if( form.indicadorRestabelecimento ){
		//if(!form.indicadorRestabelecimento[0].checked && !form.indicadorRestabelecimento[1].checked ){
			//form.indicadorRestabelecimento[0].checked = true;
		//}	
//	}
	if(!form.indicadorContasRevisao[0].checked && !form.indicadorContasRevisao[1].checked ){
		form.indicadorContasRevisao[0].checked = true;
	}	
	if(!form.indicadorGuiasPagamento[0].checked && !form.indicadorGuiasPagamento[1].checked ){
		form.indicadorGuiasPagamento[0].checked = true;
	}	
	if(!form.indicadorAcrescimosImpotualidade[0].checked && !form.indicadorAcrescimosImpotualidade[1].checked ){
		form.indicadorAcrescimosImpotualidade[0].checked = true;
	}	
}

function validaRadioButton(nomeCampo,mensagem){
	var alerta = "";
	if(!nomeCampo[0].checked && !nomeCampo[1].checked){
		alerta = mensagem +" deve ser selecionado.";
	}
	return alerta;
}
function validaTodosRadioButton(){
	var form = document.forms[0];
	var mensagem = "";
	if(form.indicadorRestabelecimento){
		if(validaRadioButton(form.indicadorRestabelecimento,"'Com restabelecimento?'") != ""){
			mensagem = mensagem + validaRadioButton(form.indicadorRestabelecimento,"'Com restabelecimento?'")+"\n";
		}
	}
	if(validaRadioButton(form.indicadorContasRevisao,"'Considerar contas em revisão?'") != ""){
		mensagem = mensagem + validaRadioButton(form.indicadorContasRevisao,"'Considerar contas em revisão?'")+"\n";
	}
	if(validaRadioButton(form.indicadorGuiasPagamento,"'Considerar guias de pagamento?'") != ""){
		mensagem = mensagem + validaRadioButton(form.indicadorGuiasPagamento,"'Considerar guias de pagamento?'")+"\n";
	}
	if(validaRadioButton(form.indicadorAcrescimosImpotualidade,"'Considerar Acréscimos por Impontualidade?'") != ""){
		mensagem = mensagem + validaRadioButton(form.indicadorAcrescimosImpotualidade,"'Considerar Acréscimos por Impontualidade?'")+"\n";
	}
	if(validaRadioButton(form.indicadorCreditoARealizar,"'Considerar créditos a realizar?'") != ""){
		mensagem = mensagem + validaRadioButton(form.indicadorCreditoARealizar,"'Considerar créditos a realizar?'")+"\n";
	}	
	if(validaRadioButton(form.indicadorDebitosACobrar,"'Considerar débitos a cobrar?'") != ""){
		mensagem = mensagem + validaRadioButton(form.indicadorDebitosACobrar,"'Considerar débitos a cobrar?'")+"\n";
	}
 
	if(mensagem != ""){
		alert(mensagem);
		return false;
	}else{
		return true;
	}
}

function desabilitaIntervaloParcelamento(){
	var form = document.forms[0];
	
	if((form.inicioIntervaloParcelamento.value == null || form.inicioIntervaloParcelamento.value == "") &&
		(form.fimIntervaloParcelamento.value == null || form.fimIntervaloParcelamento.value == "")){

		form.inicioIntervaloParcelamento.disabled = true;
		form.fimIntervaloParcelamento.disabled = true;
	}
	
}

function tratarOpcaoMeioCobrancaParcelamento() {
	 var form = document.forms[0];
	 //form.idMotivoRevisao.disabled = !form.indicadorContasRevisao[0].checked;
	 if(form.indicadorCobrancaParcelamento[0].checked){
		 form.action = 'exibirEfetuarParcelamentoDebitosAction.do?tratarOpcaoMeioCobrancaParcelamento=1&verificarRDComRestricao=1'
	 }else{
		 form.action = 'exibirEfetuarParcelamentoDebitosAction.do?tratarOpcaoMeioCobrancaParcelamento=2&verificarRDComRestricao=1'
	 }

	 form.submit();
	
}

function determinarParametrizacaoParcelamento(idBoleto) {
	var form = document.forms[0];

	form.action = 'exibirEfetuarParcelamentoDebitosAction.do?idBoletoNegociacao='+ idBoleto.value
	 
	form.submit();
	
}

function tratarOpcaoParcelamento() {
	 var form = document.forms[0];
	 //form.idMotivoRevisao.disabled = !form.indicadorContasRevisao[0].checked;
	 if(form.indicadorParcelamentoCobrancaBancaria[0].checked){
		 form.action = 'exibirEfetuarParcelamentoDebitosAction.do?tratarOpcaoParcelamento=1'
	 }else{
		 form.action = 'exibirEfetuarParcelamentoDebitosAction.do?tratarOpcaoParcelamento=2'
	 }

	 form.submit();
	
}

function verificarRDComRestricao() {
	 var form = document.forms[0];	
	 
	 if(form.resolucaoDiretoria.value != null && form.resolucaoDiretoria.value != ""){
		
		form.action = 'exibirEfetuarParcelamentoDebitosAction.do?verificarRDComRestricao=1'
		form.submit();	
	 }	 
	
}

function reload() {
	 var form = document.forms[0];	
	 var indicadorReload = "${sessionScope.reload}";
	 
	 if(form.resolucaoDiretoria.value != null && form.resolucaoDiretoria.value != ""){
		 if (!isNaN(indicadorReload) && indicadorReload != null && indicadorReload == 1){
			 form.action = 'exibirEfetuarParcelamentoDebitosAction.do'
					form.submit();	
		 }
	 }	 
	
}

-->
</script>

<logic:present name="indicadorFauramentoTitularDebito" scope="request">
	<SCRIPT LANGUAGE="JavaScript">
	<!--
	
	  	function marcarClienteOrigemId(objeto) {
	  		var form = document.forms[0];
	  		
	  		if (document.forms[0].idClienteImovel.length != undefined && document.forms[0].idClienteImovel.length != null) {
				var i = 0;
				for (i = 0; i < document.forms[0].idClienteImovel.length; i++) { 
				    if (document.forms[0].idClienteImovel[i].checked == true) {
				    	form.idClienteRelacaoImovelSelecionado.value = document.forms[0].valorClienteImovel[i].value;
				    	
				    	form.action = 'exibirEfetuarParcelamentoDebitosAction.do?menu=sim'
				    	form.submit();
				    }
				}
			} else {
		    	form.idClienteRelacaoImovelSelecionado.value = document.forms[0].valorClienteImovel.value;
		    	
		    	form.action = 'exibirEfetuarParcelamentoDebitosAction.do?menu=sim'
		    	form.submit();				
			}
	  	}

	//-->
	</SCRIPT>
</logic:present>

</head>

<logic:present name="parametrizacaoParcelamentoCobrancaBancaria" scope="session">
	<logic:equal name="parametrizacaoParcelamentoCobrancaBancaria" value="S">
	<body leftmargin="5" topmargin="5" onload="verificaExistenciaMatricula();desabilitaIntervaloParcelamento();reload();">
	</logic:equal>
</logic:present>

<logic:notPresent name="parametrizacaoParcelamentoCobrancaBancaria" scope="session">
	<body leftmargin="5" topmargin="5" onload="verificaExistenciaMatricula();habilitaMotivoRevisao();desabilitaIntervaloParcelamento();reload();">
</logic:notPresent>

<div id="formDiv">
<html:form action="/efetuarParcelamentoDebitosWizardAction"
	name="EfetuarParcelamentoDebitosActionForm"
	type="gcom.gui.cobranca.EfetuarParcelamentoDebitosActionForm"
	method="post">

<jsp:include page="/jsp/util/wizard/navegacao_abas_wizard.jsp?numeroPagina=1"/>	

<html:hidden property="situacoesCobrancaDescricoes"/>
<html:hidden property="indicadorPrimeiroAcesso"/>
<html:hidden property="indicadorImovelEmExecucaoFiscal"/>
<html:hidden property="atendimentoSemPreencherDocumentosObrigatorios"/>

<%@ include file="/jsp/util/cabecalho.jsp"%>

<% boolean tem = false; %>	
	<logic:present name="popupEfetuarParcelamento" scope="request" >
	<logic:equal name="popupEfetuarParcelamento" scope="request" value="sim">
		<% tem = true; %>
		<input type="hidden" name=popupEfetuarParcelamento value="sim"/> 
	</logic:equal>
	</logic:present>
	
<% if (!tem) { %>
		<%@ include file="/jsp/util/menu.jsp" %>
<% } %>

<table width="770" border="0" cellspacing="4" cellpadding="0">

<% if (!tem) { %>
			<td width="149" valign="top" class="leftcoltext">
			<input type="hidden" name="numeroPagina" value="1"/>
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
<% } %>
		<td width="625" valign="top" class="centercoltext">
	        <table height="100%">
		        <tr><td></td></tr>
	      	</table>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	        	<tr>
		          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
		          <td class="parabg">Efetuar Parcelamento de Débitos</td>
		          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
	    	    </tr>
		    </table>
			<!-- Início do Corpo - Roberta Costa -->
			<p>&nbsp;</p>
			
            <table width="100%" border="0">
				<tr> 
					<td colspan="2">Para efetuar o parcelamento de d&eacute;bitos informe o im&oacute;vel:</td>
				</tr>
				<tr> 
					<td colspan="2">
			            <table border="0">
							<tr> 
								<td width="30%">
									<strong>Matr&iacute;cula do Im&oacute;vel:<font color="#FF0000">*</font></strong>
								</td>
								<td>
									<html:hidden property="codigoImovelAntes" />
									<html:text property="matriculaImovel" maxlength="9" size="9"
										onkeypress="validaEnterMatriculaImovel();" 
										onkeyup="verificaNumeroInteiro(this);" onchange="javascript:verificaLimparForm();"/>
									<a href="javascript:abrirPopup('exibirPesquisarImovelAction.do', 400, 800);">
										<img width="23" height="21" src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" /></a>	
										<logic:present name="idImovelNaoEncontrado">
						                  <logic:equal name="idImovelNaoEncontrado" value="exception">
							                    <html:text property="inscricaoImovel" size="30"
								                   maxlength="30" readonly="true"
								                   style="background-color:#EFEFEF; border:0; color: #ff0000" />
						                  </logic:equal>
						                  <logic:notEqual name="idImovelNaoEncontrado" value="exception">
							                   <html:text property="inscricaoImovel" size="30"
								                maxlength="30" readonly="true"
								                style="background-color:#EFEFEF; border:0; color: #000000" />
						                  </logic:notEqual>
					                   </logic:present> 
					                   <logic:notPresent name="idImovelNaoEncontrado">
						                  <logic:empty name="EfetuarParcelamentoDebitosActionForm" property="inscricaoImovel">
							                    <html:text property="inscricaoImovel" size="30" value = ""
								                  maxlength="30" readonly="true"
								                  style="background-color:#EFEFEF; border:0; color: #ff0000" />
						                  </logic:empty>
						               <logic:notEmpty name="EfetuarParcelamentoDebitosActionForm" property="inscricaoImovel">
							                   <html:text property="inscricaoImovel" size="30"
								                  maxlength="30" readonly="true"
								                  style="background-color:#EFEFEF; border:0; color: #000000" />
						               </logic:notEmpty>
						               </logic:notPresent>
										
									<img border="0" src="<bean:message key='caminho.imagens'/>limparcampo.gif" onclick="limparForm();" style="cursor: hand;" />
								</td>
							</tr>
							
						    <logic:present name="indicadorFauramentoTitularDebito" scope="request">
							<td colspan="3">			    
								<table width="100%" align="center" bgcolor="#90c7fc" border="0">
								<tr>
									<td align="center" ><strong>Clientes com Débitos</strong></td>
								</tr>
							    </table>
						    	<html:hidden property="idClienteRelacaoImovelSelecionado"/>
								<%int cont = 0;%>
								<table width="100%" bgcolor="#99CCFF">
									<tr bgcolor="#90c7fc">
										<td align="center" width="7%"><strong></strong></td>
										<td align="center" width="18%"><strong>Tipo de Relação</strong></td>
										<td align="left" width="75%"><strong>Nome</strong></td>
									</tr>
									
									<logic:notEmpty name="colecaoRelacaoImovel" scope="session">
									<tr>
										<td height="100" colspan="3" >
										<div style="width: 100%; height: 100%; overflow: auto;">
										<table width="100%">
											<logic:iterate name="colecaoRelacaoImovel" type="ClienteImovel" id="clienteImovel">
												<%cont = cont + 1;
												if (cont % 2 == 0) {%>
												<tr bgcolor="#cbe5fe" width="100%">
												<%} else {%>
												<tr bgcolor="#FFFFFF" width="100%">
												<%}%>
								
												<td align="center" height="20" width="7%">
													<input type="hidden" name="valorClienteImovel"
														value="<bean:write name="clienteImovel" property="clienteRelacaoTipo.id"/>.<bean:write name="clienteImovel" property="cliente.id"/>" 
														 >
													
													<input type="radio" id="idClienteImovel" 
														name="idClienteImovel" 
														 onclick="javascript:marcarClienteOrigemId(this);">
					
												</td>
												<td align="center" height="20" width="18%">
													<bean:write name="clienteImovel" property="clienteRelacaoTipo.descricao"/>
												</td>
												<td align="center" height="20" width="75%">
													<bean:write name="clienteImovel" property="cliente.descricao"/>
												</td>																				
												</tr>
											</logic:iterate>							
										</table>
										</div>
										</td>
									</tr>
									</logic:notEmpty>							
								</table>			
							</td>
							</tr>				       
						   </logic:present>								
	
							<tr>
								<td><strong>Cliente Responsável pelo Parcelamento:<font color="#FF0000">*</font></strong></td>
								<td>
									<logic:present name="permiteInformarCliente" scope="session">
										<html:text property="idClienteParcelamento" size="9" maxlength="9"
											onkeyup="validaEnterComMensagem(event, 
											'efetuarParcelamentoDebitosWizardAction.do?action=exibirEfetuarParcelamentoDebitosProcesso1Action', 'idClienteParcelamento','Cliente');"
											onkeypress="document.forms[0].nomeClienteParcelamento.value='';
											document.forms[0].foneClienteParcelamento.value='';
											document.forms[0].cpfClienteParcelamento.value='';
											document.forms[0].cpfClienteParcelamentoDigitado.value='';" />
										
										<a href="javascript:habilitarPesquisaCliente(document.forms[0]);" >
											<img width="23" height="21"	src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" />
										</a>
										
										<logic:present name="clienteInexistente" scope="request">
											<html:text property="nomeClienteParcelamento" size="35" maxlength="35"
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: #ff0000" />
										</logic:present> 
										<logic:notPresent name="clienteInexistente"
											scope="request">
											<html:text property="nomeClienteParcelamento" size="35" maxlength="35"
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000" />
										</logic:notPresent>
										
										<a href="javascript:limparCliente();">
											<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" />
										</a>
									</logic:present>
									
									<logic:notPresent name="permiteInformarCliente" scope="session">
										<html:text property="idClienteParcelamento" size="9" maxlength="9" readonly="true"
											onkeyup="validaEnterComMensagem(event, 
											'efetuarParcelamentoDebitosWizardAction.do?action=exibirEfetuarParcelamentoDebitosProcesso1Action', 'idClienteParcelamento','Cliente');"
											onkeypress="document.forms[0].nomeClienteParcelamento.value='';
											document.forms[0].foneClienteParcelamento.value='';
											document.forms[0].cpfClienteParcelamento.value='';
											document.forms[0].cpfClienteParcelamentoDigitado.value='';"
											style="background-color:#EFEFEF; border:0; color: #000000" />
										
										<img width="23" height="21"	src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" />
										
										<logic:present name="clienteInexistente" scope="request">
											<html:text property="nomeClienteParcelamento" size="35" maxlength="35"
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: #ff0000" />
										</logic:present>
										<logic:notPresent name="clienteInexistente"
											scope="request">
											<html:text property="nomeClienteParcelamento" size="35" maxlength="35"
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000" />
										</logic:notPresent>
										
										<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" />
									</logic:notPresent>

								</td>
							</tr>
							<tr>
							
								<td><strong>Telefone:</strong></td>
			
								<td>
									<logic:present name="clienteInexistente" scope="request">
										<html:text property="foneClienteParcelamento" size="20" maxlength="20"
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: #ff0000" />
									</logic:present> 
									
									<logic:notPresent name="clienteInexistente"	scope="request">
										<html:text property="foneClienteParcelamento" size="20" maxlength="20"
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: #000000" />	
									</logic:notPresent>
								</td>
			
							</tr>						
						
							<html:hidden property="indicadorPessoaFisicaJuridica"/>

							<tr>
								<logic:notEmpty name="EfetuarParcelamentoDebitosActionForm" property="indicadorPessoaFisicaJuridica">

									<logic:equal name="EfetuarParcelamentoDebitosActionForm" property="indicadorPessoaFisicaJuridica"
													value="<%=ClienteTipo.INDICADOR_PESSOA_FISICA.toString()%>">
										<td><strong>CPF:<font color="#FF0000">*</font></strong></td>
										<td>
											<logic:present name="clienteInexistente" scope="request">
												<html:text property="cpfClienteParcelamentoDigitado" size="12" maxlength="11"
												onblur="document.forms[0].cpfClienteParcelamento.value=document.forms[0].cpfClienteParcelamentoDigitado.value;"/>
												<html:text property="cpfClienteParcelamento" size="12" maxlength="11" 
													readonly="true" style="background-color:#EFEFEF; border:0;display:none;" />
													<input type="text" name="cpfInexistente" value="" style="background-color:#cbe5fe; border:0; color: #FF0000" >
											</logic:present> 

											<logic:notPresent name="clienteInexistente"	scope="request">
												<logic:present name="cpfCliente" scope="request">
													<html:text property="cpfClienteParcelamento" size="12" maxlength="11" 
													readonly="true" style="background-color:#EFEFEF; border:0;" />

													<html:text property="cpfClienteParcelamentoDigitado" size="12" maxlength="11" style="display:none;"
													onblur="document.forms[0].cpfClienteParcelamento.value=document.forms[0].cpfClienteParcelamentoDigitado.value;" />
													<input type="text" name="cpfInexistente" value="" style="background-color:#cbe5fe; border:0; color: #FF0000" >
												</logic:present>

												<logic:notPresent name="cpfCliente" scope="request">
													<logic:present name="cpfInexistente" scope="request">
														<html:text property="cpfClienteParcelamento" size="12" maxlength="11" 
														readonly="true"	style="background-color:#EFEFEF; border:0;display:none;" />
														<html:text property="cpfClienteParcelamentoDigitado" size="12" maxlength="11" style="display:'';"
														onblur="document.forms[0].cpfClienteParcelamento.value=document.forms[0].cpfClienteParcelamentoDigitado.value;"/>											
														<input type="text" name="cpfInexistente" value="CPF INEXISTENTE" style="background-color:#cbe5fe; border:0; color: #FF0000" >
													</logic:present>

													<logic:notPresent name="cpfInexistente" scope="request">
														<html:text property="cpfClienteParcelamento" size="12" maxlength="11" 
														readonly="true"	style="background-color:#EFEFEF; border:0;display:none;" />
														<html:text property="cpfClienteParcelamentoDigitado" size="12" maxlength="11" style="display:'';"
														onblur="document.forms[0].cpfClienteParcelamento.value=document.forms[0].cpfClienteParcelamentoDigitado.value;"/>											
														<input type="text" name="cpfInexistente" value="" style="background-color:#cbe5fe; border:0; color: #FF0000" >
													</logic:notPresent>
												</logic:notPresent>
											</logic:notPresent>
										</td>
									</logic:equal>

									<logic:notEqual name="EfetuarParcelamentoDebitosActionForm" property="indicadorPessoaFisicaJuridica"
													value="<%=ClienteTipo.INDICADOR_PESSOA_FISICA.toString()%>">
										<td><strong>CNPJ:<font color="#FF0000">*</font></strong></td>
										<td>
											<logic:present name="clienteInexistente" scope="request">
												<html:text property="cpfClienteParcelamentoDigitado" size="16" maxlength="14"
												onblur="document.forms[0].cpfClienteParcelamento.value=document.forms[0].cpfClienteParcelamentoDigitado.value;"/>
												<html:text property="cpfClienteParcelamento" size="16" maxlength="14" 
													readonly="true" style="background-color:#EFEFEF; border:0;display:none;" />
													<input type="text" name="cpfInexistente" value="" style="background-color:#cbe5fe; border:0; color: #FF0000" >
											</logic:present> 

											<logic:notPresent name="clienteInexistente"	scope="request">
												<logic:present name="cpfCliente" scope="request">
													<html:text property="cpfClienteParcelamento" size="16" maxlength="14" 
													readonly="true" style="background-color:#EFEFEF; border:0;" />

													<html:text property="cpfClienteParcelamentoDigitado" size="16" maxlength="14" style="display:none;"
													onblur="document.forms[0].cpfClienteParcelamento.value=document.forms[0].cpfClienteParcelamentoDigitado.value;" />
													<input type="text" name="cpfInexistente" value="" style="background-color:#cbe5fe; border:0; color: #FF0000" >
												</logic:present>

												<logic:notPresent name="cpfCliente" scope="request">
													<logic:present name="cpfInexistente" scope="request">
														<html:text property="cpfClienteParcelamento" size="16" maxlength="14" 
														readonly="true"	style="background-color:#EFEFEF; border:0;display:none;" />
														<html:text property="cpfClienteParcelamentoDigitado" size="16" maxlength="14" style="display:'';"
														onblur="document.forms[0].cpfClienteParcelamento.value=document.forms[0].cpfClienteParcelamentoDigitado.value;"/>											
														<input type="text" name="cpfInexistente" value="CPF INEXISTENTE" style="background-color:#cbe5fe; border:0; color: #FF0000" >
													</logic:present>

													<logic:notPresent name="cpfInexistente" scope="request">
														<html:text property="cpfClienteParcelamento" size="16" maxlength="14" 
														readonly="true"	style="background-color:#EFEFEF; border:0;display:none;" />
														<html:text property="cpfClienteParcelamentoDigitado" size="16" maxlength="14" style="display:'';"
														onblur="document.forms[0].cpfClienteParcelamento.value=document.forms[0].cpfClienteParcelamentoDigitado.value;"/>											
														<input type="text" name="cpfInexistente" value="" style="background-color:#cbe5fe; border:0; color: #FF0000" >
													</logic:notPresent>
												</logic:notPresent>
											</logic:notPresent>
										</td>
									</logic:notEqual>
								</logic:notEmpty>

								<logic:empty name="EfetuarParcelamentoDebitosActionForm" property="indicadorPessoaFisicaJuridica">
									<td><strong>CPF/CNPJ:<font color="#FF0000">*</font></strong></td>
									<td>
										<logic:present name="clienteInexistente" scope="request">
											<html:text property="cpfClienteParcelamentoDigitado" size="11" maxlength="11"
											onblur="document.forms[0].cpfClienteParcelamento.value=document.forms[0].cpfClienteParcelamentoDigitado.value;"/>
											<html:text property="cpfClienteParcelamento" size="14" maxlength="14" 
												readonly="true" style="background-color:#EFEFEF; border:0;display:none;" />
												<input type="text" name="cpfInexistente" value="" style="background-color:#cbe5fe; border:0; color: #FF0000" >
										</logic:present> 

										<logic:notPresent name="clienteInexistente"	scope="request">
											<logic:present name="cpfCliente" scope="request">
												<html:text property="cpfClienteParcelamento" size="14" maxlength="14" 
												readonly="true" style="background-color:#EFEFEF; border:0;" />

												<html:text property="cpfClienteParcelamentoDigitado" size="11" maxlength="11" style="display:none;"
												onblur="document.forms[0].cpfClienteParcelamento.value=document.forms[0].cpfClienteParcelamentoDigitado.value;" />
												<input type="text" name="cpfInexistente" value="" style="background-color:#cbe5fe; border:0; color: #FF0000" >
											</logic:present>

											<logic:notPresent name="cpfCliente" scope="request">
												<logic:present name="cpfInexistente" scope="request">
													<html:text property="cpfClienteParcelamento" size="14" maxlength="14" 
													readonly="true"	style="background-color:#EFEFEF; border:0;display:none;" />
													<html:text property="cpfClienteParcelamentoDigitado" size="11" maxlength="11" style="display:'';"
													onblur="document.forms[0].cpfClienteParcelamento.value=document.forms[0].cpfClienteParcelamentoDigitado.value;"/>											
													<input type="text" name="cpfInexistente" value="CPF INEXISTENTE" style="background-color:#cbe5fe; border:0; color: #FF0000" >
												</logic:present>

												<logic:notPresent name="cpfInexistente" scope="request">
													<html:text property="cpfClienteParcelamento" size="14" maxlength="14" 
													readonly="true"	style="background-color:#EFEFEF; border:0;display:none;" />
													<html:text property="cpfClienteParcelamentoDigitado" size="11" maxlength="11" style="display:'';"
													onblur="document.forms[0].cpfClienteParcelamento.value=document.forms[0].cpfClienteParcelamentoDigitado.value;"/>											
													<input type="text" name="cpfInexistente" value="" style="background-color:#cbe5fe; border:0; color: #FF0000" >
												</logic:notPresent>
											</logic:notPresent>
										</logic:notPresent>
									</td>
								</logic:empty>
							</tr>

			            </table>
					</td>
				</tr>
				<tr> 
					<td colspan="2">
			            <table border="0" bgcolor="#99CCFF" width="100%">
							<tr bgcolor="#99CCFF">
								<td colspan="2" align="center"><strong>Dados do Imóvel</strong></td>
							</tr>
							<tr>
								<td>
									<table border="0" bgcolor="#cbe5fe" width="100%">
										<tr> 
											<td><strong>Cliente Usu&aacute;rio:</strong></td>
											<td>
												<html:text property="nomeCliente" size="45"
													readonly="true" style="background-color:#EFEFEF; border:0" />
											</td>
										</tr>
										<tr> 
											<td><strong>CPF ou CNPJ:</strong></td>
											<td>
												<html:text property="cpfCnpj" size="45"
													readonly="true" style="background-color:#EFEFEF; border:0" />
												<html:hidden property="perfilImovel"/>														
											</td>
										</tr>
										<tr> 
											<td><strong>Situa&ccedil;&atilde;o da Liga&ccedil;&atilde;o de &Aacute;gua:</strong></td>
											<td>
												<html:text property="situacaoAgua" size="45"
													readonly="true" style="background-color:#EFEFEF; border:0" />
												<html:hidden property="situacaoAguaId"/>	
											</td>
										</tr>
										<tr> 
											<td><strong>Situa&ccedil;&atilde;o da Liga&ccedil;&atilde;o de Esgoto:</strong></td>
											<td>
												<html:text property="situacaoEsgoto" size="45"
													readonly="true" style="background-color:#EFEFEF; border:0" />
												<html:hidden property="situacaoEsgotoId"/>	
											</td>
										</tr>
										<tr> 
											<td><strong>Perfil do Imóvel:</strong></td>
											<td>
												<html:text property="descricaoPerfilImovel" size="45"
													readonly="true" style="background-color:#EFEFEF; border:0" />
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2"><br>
						<table width="100%" align="center" bgcolor="#99CCFF" border="0">
							<tr>
								<td align="center">
									<strong>Endere&ccedil;o do Im&oacute;vel</strong>
								</td>
							</tr>
							<tr bgcolor="#cbe5fe">
								<td align="center" bgcolor="#FFFFFF" height="20">
									<span id="endereco">
										<logic:present name="EfetuarParcelamentoDebitosActionForm" property="endereco">
											<bean:write name="EfetuarParcelamentoDebitosActionForm" 
												property="endereco"/>
										</logic:present>
									</span>	
									<logic:notPresent name="EfetuarParcelamentoDebitosActionForm" property="endereco">
									&nbsp;
									</logic:notPresent>													
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2" height="23"><br>
						<font color="#000000">
							<strong>Quantidades de Parcelamentos / Reparcelamentos:</strong>
						</font>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table width="100%" bgcolor="#99CCFF">
							<tr bgcolor="#90c7fc"> 
								<td align="center" width="30%"><strong> Parcelamentos</strong></td>
								<td align="center" width="30%"><strong>Reparcelamentos</strong></td>
								<td align="center" width="*"><strong>Reparcelamentos Consecutivos </strong></td>
							</tr>
							<tr bgcolor="#cbe5fe""> 
								<td align="center" height="20" bgcolor="#FFFFFF">
									<span id="numeroParcelamento">
									<bean:write name="EfetuarParcelamentoDebitosActionForm" 
										property="numeroParcelamento"/>
									</span>	
								</td>
								<td align="center" bgcolor="#FFFFFF">
									<span id="numeroReparcelamento">
									<bean:write name="EfetuarParcelamentoDebitosActionForm" 
										property="numeroReparcelamento"/>
									</span>	
								</td>
								<td align="center" bgcolor="#FFFFFF">
									<span id="numeroReparcelamentoConsecutivos">
									<bean:write name="EfetuarParcelamentoDebitosActionForm" 
										property="numeroReparcelamentoConsecutivos"/>
									</span>	
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2" height="23"><br>
						<font color="#000000">
							<strong>Valor dos D&eacute;bitos do Im&oacute;vel:</strong>
						</font>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table width="100%" bgcolor="#99CCFF">
							<tr bgcolor="#90c7fc">  
								<td align="center" width="30%">
									<strong> Contas</strong>
								</td>
								<td align="center" width="30%">
									<strong>Guias de Pagamento</strong>
								</td>
								<td align="center" width="40%">
									<strong>Acr&eacute;scimos Impontualidade</strong>
								</td>
							</tr>
							<tr bgcolor="#cbe5fe"> 
								<td align="right" height="20" bgcolor="#FFFFFF">
									<span id="valorTotalContaValores">
									<bean:write name="EfetuarParcelamentoDebitosActionForm" 
										property="valorTotalContasImovel"/>
									</span>	
								</td>
								<td align="right" bgcolor="#FFFFFF">
									<span id="valorGuiasPagamento">
									<bean:write name="EfetuarParcelamentoDebitosActionForm" 
										property="valorGuiasPagamentoImovel"/>
									</span>	
								</td>
								<td align="right" bgcolor="#FFFFFF">
									<span id="valorAcrescimosImpontualidade">
									<logic:notEqual
										name="EfetuarParcelamentoDebitosActionForm" property="valorAcrescimosImpontualidadeImovel"
										value="0,00">
										<a href="javascript:abrirPopup('exibirValorAtualizacaoConsultarPopupAction.do?
											multa=<bean:write name="EfetuarParcelamentoDebitosActionForm" property="valorMultaImovel" />&
											juros=<bean:write name="EfetuarParcelamentoDebitosActionForm" property="valorJurosMoraImovel" />&
											atualizacao=<bean:write name="EfetuarParcelamentoDebitosActionForm" property="valorAtualizacaoMonetariaImovel" />', 220, 605);">
										<bean:write name="EfetuarParcelamentoDebitosActionForm"
											property="valorAcrescimosImpontualidadeImovel" formatKey="money.format" />
										</a>
									</logic:notEqual> 
									<logic:equal name="EfetuarParcelamentoDebitosActionForm"
										property="valorAcrescimosImpontualidadeImovel" value="0,00">
										<bean:write name="EfetuarParcelamentoDebitosActionForm"
											property="valorAcrescimosImpontualidadeImovel" formatKey="money.format" />
									</logic:equal>
									</span>	
								</td>
							</tr>
						</table>
						<br>	
						<table width="100%" bgcolor="#99CCFF">
							<tr bgcolor="#90c7fc">
								<td align="center" colspan="2">
									<strong>D&eacute;bitos a Cobrar</strong>
								</td>
								<td align="center" rowspan="2">
									<strong>Cr&eacute;ditos a Realizar</strong>
								</td>
								<td align="center" rowspan="2">
									<strong>D&eacute;bito Total Atualizado</strong>
								</td>
							</tr>
							<tr bgcolor="#90c7fc"> 
								<td align="center" bgcolor="cbe5fe" width="20%">
									<strong>Servi&ccedil;o</strong>
								</td>
								<td align="center" bgcolor="cbe5fe">
									<strong>Parcelamento</strong>
								</td>
							</tr>
							<tr bgcolor="cbe5fe">
								<td align="right" height="20" bgcolor="#FFFFFF">
									<span id="valorDebitoACobrarServico">
										<logic:notEqual name="EfetuarParcelamentoDebitosActionForm" 
											property="valorDebitoACobrarServicoImovel" value="0,00">
											<strong><a href="javascript:abrirPopup('exibirConsultarDebitoACobrarAction.do?imovelID=
												<bean:define name="EfetuarParcelamentoDebitosActionForm" property="matriculaImovel" id="imovel" />
													<bean:write name="EfetuarParcelamentoDebitosActionForm" property="matriculaImovel" />&parcelamentoID=', 600, 800);">
												<bean:write name="EfetuarParcelamentoDebitosActionForm" property="valorDebitoACobrarServicoImovel"  formatKey="money.format"/>
											</a><strong>
										</logic:notEqual>
										<logic:equal name="EfetuarParcelamentoDebitosActionForm" 
											property="valorDebitoACobrarServicoImovel" value="0,00">
											<bean:write	name="EfetuarParcelamentoDebitosActionForm" property="valorDebitoACobrarServicoImovel" formatKey="money.format" />
										</logic:equal>
									</span>	
									<html:hidden property="valorDebitoACobrarServicoCurtoPrazo"/>
									<html:hidden property="valorDebitoACobrarServicoLongoPrazo"/>
								</td>
								<td align="right" bgcolor="#FFFFFF">
									<span id="valorDebitoACobrarParcelamento">
										<logic:notEqual name="EfetuarParcelamentoDebitosActionForm" 
											property="valorDebitoACobrarParcelamentoImovel" value="0,00">
											<a href="javascript:abrirPopup('exibirConsultarDebitoACobrarAction.do?imovelID=
												<bean:define name="EfetuarParcelamentoDebitosActionForm" property="matriculaImovel" id="imovel" />
													<bean:write name="EfetuarParcelamentoDebitosActionForm" property="matriculaImovel" />&parcelamentoID=', 600, 800);">
												<bean:write name="EfetuarParcelamentoDebitosActionForm" property="valorDebitoACobrarParcelamentoImovel"  formatKey="money.format"/>
											</a>
										</logic:notEqual>
										<logic:equal name="EfetuarParcelamentoDebitosActionForm" 
											property="valorDebitoACobrarParcelamentoImovel" value="0,00">
											<bean:write	name="EfetuarParcelamentoDebitosActionForm" property="valorDebitoACobrarParcelamentoImovel" formatKey="money.format" />
										</logic:equal>
									</span>	
									<html:hidden property="valorDebitoACobrarParcelamentoCurtoPrazo"/>
									<html:hidden property="valorDebitoACobrarParcelamentoLongoPrazo"/>
								</td>
								<td align="right" bgcolor="#FFFFFF">
									<span id="valorCreditoARealizar">
										<logic:notEqual name="EfetuarParcelamentoDebitosActionForm" 
											property="valorCreditoARealizarImovel" value="0,00">
											<a href="javascript:abrirPopup('exibirConsultarCreditoARealizarAction.do?imovelID=<bean:define name="EfetuarParcelamentoDebitosActionForm" property="matriculaImovel" id="imovel" />
												<bean:write name="EfetuarParcelamentoDebitosActionForm" property="matriculaImovel" />&parcelamentoID=', 600, 800);">
													<bean:write name="EfetuarParcelamentoDebitosActionForm" property="valorCreditoARealizarImovel" formatKey="money.format"/>
											</a>
										</logic:notEqual>
										<logic:equal name="EfetuarParcelamentoDebitosActionForm" 
											property="valorCreditoARealizarImovel" value="0,00">
											<bean:write	name="EfetuarParcelamentoDebitosActionForm" property="valorCreditoARealizarImovel" formatKey="money.format" />
										</logic:equal>
									</span>	
								</td>
								<td align="right" bgcolor="#FFFFFF">
									<span id="valorDebitoTotalAtualizado">
									<bean:write name="EfetuarParcelamentoDebitosActionForm" 
										property="valorDebitoTotalAtualizadoImovel"/>
									</span>	
								</td>
							</tr>
						</table>

						<logic:notEqual name="EfetuarParcelamentoDebitosActionForm" property="valorTotalSucumbenciaImovel" value="0,00">
						<br>
						<table width="100%" bgcolor="#99CCFF">
							<tr bgcolor="#90c7fc">  
								<td align="center" width="50%">
									<strong>Sucumbência Anterior</strong>
								</td>
								<td align="center" width="50%">
									<strong>Acr&eacute;scimos Sucumbência Ant.</strong>
								</td>
							</tr>
							<tr bgcolor="#cbe5fe"> 
								<td align="right" height="20" bgcolor="#FFFFFF">
									<span id="valorTotalSucumbencia">
									<bean:write name="EfetuarParcelamentoDebitosActionForm" 
										property="valorTotalSucumbenciaImovel"/>
									</span>	
								</td>
								<td align="right" bgcolor="#FFFFFF">
									<span id="valorAcrescimosSucumbencia">
									<logic:notEqual
										name="EfetuarParcelamentoDebitosActionForm" property="valorAcrescimosSucumbenciaImovel"
										value="0,00">
										<a href="javascript:abrirPopup('exibirValorAtualizacaoConsultarPopupAction.do?
											multa=<bean:write name="EfetuarParcelamentoDebitosActionForm" property="valorMultaSucumbenciaImovel" />&
											juros=<bean:write name="EfetuarParcelamentoDebitosActionForm" property="valorJurosMoraSucumbenciaImovel" />&
											atualizacao=<bean:write name="EfetuarParcelamentoDebitosActionForm" property="valorAtualizacaoMonetariaSucumbenciaImovel" />', 220, 605);">
										<bean:write name="EfetuarParcelamentoDebitosActionForm"
											property="valorAcrescimosSucumbenciaImovel" formatKey="money.format" />
										</a>
									</logic:notEqual> 
									<logic:equal name="EfetuarParcelamentoDebitosActionForm"
										property="valorAcrescimosSucumbenciaImovel" value="0,00">
										<bean:write name="EfetuarParcelamentoDebitosActionForm"
											property="valorAcrescimosSucumbenciaImovel" formatKey="money.format" />
									</logic:equal>
									</span>	
								</td>
							</tr>
						</table>
						</logic:notEqual>

					</td>
				</tr>
				
				<tr> 
					<td width="45%"><br>
						<strong>Data do Parcelamento:<font color="#FF0000">*</font></strong>
					</td>
					<td><br>
						<html:text property="dataParcelamento" size="10" maxlength="10" tabindex="2" onkeyup="mascaraData(document.forms[0].dataParcelamento,this)"/>
						<a href="javascript:abrirCalendario('EfetuarParcelamentoDebitosActionForm', 'dataParcelamento')">
	             			<img border="0" align="top" src="<bean:message key='caminho.imagens'/>calendario.gif" width="20" border="0" align="middle" alt="Exibir Calendário"/></a> dd/mm/aaaa
					</td>
				</tr>
				
				<logic:equal name="exibirParcelamentoCobrancaBancaria" value="S">
				<tr> 
					<td><strong>Parcelamento de Cobran&ccedil;a Banc&aacute;ria?<font color="#FF0000">*</font></strong></td>
					<td>
						<strong>
							<html:radio property="indicadorParcelamentoCobrancaBancaria" value="1" onclick="javascript:tratarOpcaoParcelamento();" />Sim
							<html:radio property="indicadorParcelamentoCobrancaBancaria" value="2" onclick="javascript:tratarOpcaoParcelamento();" />N&atilde;o
						 </strong>
					</td>
				</tr>
				</logic:equal>
				
				<logic:present name="colecaoBoletoHelper" scope="session">
				<tr>
					<td colspan="2" height="23"><br>
						<font color="#000000">
							<strong>Boleto(s) Banc&aacute;rio(s) para Negocia&ccedil;&atilde;o:</strong>
						</font>
					</td>
				</tr>
				<%int cont = 0;%>
				<tr>
					<td colspan="2">
						<table width="100%" bgcolor="#99CCFF">
							<tr bgcolor="#90c7fc">  
								<td align="center" width="6%">
									<strong>&nbsp;</strong>
								</td>
								<td align="center" width="15%">
									<strong>Arrecadador</strong>
								</td>
								<td align="center" width="15%">
									<strong>Nosso N&uacute;mero</strong>
								</td>
								<td align="center" width="10%">
									<strong>Im&oacute;vel</strong>
								</td>
								<td align="center" width="12%">
									<strong>Data do Vencimento</strong>
								</td>
								<td align="center" width="10%">
									<strong>Valor</strong>
								</td>
								<td align="center" width="12%">
									<strong>Data de Entrada</strong>
								</td>
								<td align="center" width="20%">
									<strong>Situa&ccedil;&atilde;o Atual</strong>
								</td>
							</tr>
							<logic:iterate name="colecaoBoletoHelper"
								type="BoletoBancarioHelper" id="boletoHelper">
								<%cont = cont + 1;
								if (cont % 2 == 0) {%>
								<tr bgcolor="#cbe5fe">
								<%} else {%>
								<tr bgcolor="#FFFFFF">
								<%}%>
									<td align="center">
										<html:radio property="idBoletoNegociacao" value="${boletoHelper.id}" onclick="javascript:determinarParametrizacaoParcelamento(this);" />
									</td>
									<td align="center" title="${boletoHelper.arrecadador.cliente.nome}">
										<span>
										<bean:write name="boletoHelper" 
											property="arrecadador.codigoAgente"/>
										</span>	
									</td>
									<td align="center">
										<span>
										<bean:write name="boletoHelper" 
											property="numeroConvenioSequencialFormatado"/>
										</span>	
									</td>
									<td align="center">
										<span>
										<bean:write name="boletoHelper" 
											property="imovel.id"/>
										</span>
									</td>
									<td align="center">
										<span>
										<bean:write name="boletoHelper" 
											property="dataInicialVencimento" formatKey="date.format"/>
										</span>
									</td>
									<td align="right">
										<span>
										<bean:write name="boletoHelper" 
											property="valorDebito" formatKey="money.format"/>
										</span>
									</td>
									<td align="center">
										<span>
										<bean:write name="boletoHelper" 
											property="dataInicialEntrada" formatKey="date.format"/>
										</span>
									</td>
									<td align="center">
										<span>
										<bean:write name="boletoHelper" 
											property="boletoBancarioSituacao.descricao"/>
										</span>
									</td>
								</tr>
						    </logic:iterate>
						</table>
					<br>
					</td>
				</tr>
				</logic:present>
				
				<tr> 
					<td><strong>Cobran&ccedil;a do Parcelamento:<font color="#FF0000">*</font></strong></td>
					<td>
						<logic:present name="parametrizacaoParcelamentoCobrancaBancaria" scope="session">
							<logic:equal name="parametrizacaoParcelamentoCobrancaBancaria" value="S">
							<strong>
								<html:radio property="indicadorCobrancaParcelamento" value="1" disabled="true" />D&eacute;bito a Cobrar
								<html:radio property="indicadorCobrancaParcelamento" value="2" disabled="true" />Guia de Pagamento
							</strong>
							</logic:equal>
						</logic:present>
						 <logic:notPresent name="parametrizacaoParcelamentoCobrancaBancaria" scope="session">
						 	<logic:present name="parametrizacaoParcelamentoPadrao" scope="session">
								<logic:equal name="parametrizacaoParcelamentoPadrao" value="S">
								<strong>
									<html:radio property="indicadorCobrancaParcelamento" value="1" disabled="true" />D&eacute;bito a Cobrar
									<html:radio property="indicadorCobrancaParcelamento" value="2" disabled="true" />Guia de Pagamento
								</strong>
								</logic:equal>
							</logic:present>
							<logic:notPresent name="parametrizacaoParcelamentoPadrao" scope="session">
								<logic:present name="indicadorImovelEmExecucaoFiscal" scope="session">
									<logic:equal name="indicadorImovelEmExecucaoFiscal" value="S">
										<logic:present name="indicadorPermissaoExecucaoFiscalCobrancaConta" scope="session">
											<logic:equal name="indicadorPermissaoExecucaoFiscalCobrancaConta" value="S">
											<strong>
												<logic:notPresent name="indicadorTitularidadeDifenteDeUsuarioAtual" scope="session">
													<html:radio property="indicadorCobrancaParcelamento" value="1" onclick="javascript:tratarOpcaoMeioCobrancaParcelamento();" />D&eacute;bito a Cobrar
													<html:radio property="indicadorCobrancaParcelamento" value="2" onclick="javascript:tratarOpcaoMeioCobrancaParcelamento();" />Guia de Pagamento
												</logic:notPresent>
												
												<logic:present name="indicadorTitularidadeDifenteDeUsuarioAtual" scope="session">
													<html:radio property="indicadorCobrancaParcelamento" value="1" onclick="javascript:tratarOpcaoMeioCobrancaParcelamento();" disabled="true" />D&eacute;bito a Cobrar
													<html:radio property="indicadorCobrancaParcelamento" value="2" onclick="javascript:tratarOpcaoMeioCobrancaParcelamento();" disabled="true" />Guia de Pagamento
												</logic:present>												
											</strong>
											</logic:equal>
										</logic:present>
										<logic:notPresent name="indicadorPermissaoExecucaoFiscalCobrancaConta" scope="session">
										<strong>
											<html:radio property="indicadorCobrancaParcelamento" value="1" disabled="true" />D&eacute;bito a Cobrar
											<html:radio property="indicadorCobrancaParcelamento" value="2" disabled="true" />Guia de Pagamento
										</strong>
										</logic:notPresent>
								</logic:equal>
								</logic:present>
								<logic:notPresent name="indicadorImovelEmExecucaoFiscal" scope="session">
								<strong>
									<logic:notPresent name="indicadorTitularidadeDifenteDeUsuarioAtual" scope="session">
										<html:radio property="indicadorCobrancaParcelamento" value="1" onclick="javascript:tratarOpcaoMeioCobrancaParcelamento();" />D&eacute;bito a Cobrar
										<html:radio property="indicadorCobrancaParcelamento" value="2" onclick="javascript:tratarOpcaoMeioCobrancaParcelamento();" />Guia de Pagamento
									</logic:notPresent>
									
									<logic:present name="indicadorTitularidadeDifenteDeUsuarioAtual" scope="session">
										<html:radio property="indicadorCobrancaParcelamento" value="1" onclick="javascript:tratarOpcaoMeioCobrancaParcelamento();" disabled="true" />D&eacute;bito a Cobrar
										<html:radio property="indicadorCobrancaParcelamento" value="2" onclick="javascript:tratarOpcaoMeioCobrancaParcelamento();" disabled="true" />Guia de Pagamento
									</logic:present>									
								</strong>
								</logic:notPresent>
							</logic:notPresent>
						</logic:notPresent>
					</td>
				</tr>
				
				<tr> 
					<td><strong>RD do Parcelamento:<font color="#FF0000">*</font></strong></td>
					<td>
						<html:select property="resolucaoDiretoria" onchange="javascript:verificarRDComRestricao();">
							<html:option value="">&nbsp;</html:option>
							<html:options collection="colecaoResolucaoDiretoria" labelProperty="numeroResolucaoDiretoria" property="id" />
						</html:select>
					</td>
				</tr>
				<tr> 
					<td><strong>Intervalo do Parcelamento:<font color="#FF0000"></font></strong></td>
					<td>
						
						<logic:present name="indicadorAlteracaoPeriodoParcelamento" scope="session">
						
							<logic:equal name="indicadorAlteracaoPeriodoParcelamento" value="2">
								
								<html:text property="inicioIntervaloParcelamento" maxlength="7" size="7" readonly="true" />
								<html:hidden property="inicioIntervaloParcelamentoOriginal"/>
									a 
								<html:text property="fimIntervaloParcelamento" maxlength="7" size="7" readonly="true" />
							 	<html:hidden property="fimIntervaloParcelamentoOriginal"/>
									
							</logic:equal>
							
							<logic:equal name="indicadorAlteracaoPeriodoParcelamento" value="1">
						      
								<logic:present name="parametrizacaoParcelamentoCobrancaBancaria" scope="session">
									<logic:equal name="parametrizacaoParcelamentoCobrancaBancaria" value="S">
										<html:text property="inicioIntervaloParcelamento" maxlength="7" size="7" readonly="true" />
											<html:hidden property="inicioIntervaloParcelamentoOriginal"/>
											a 
											 <html:text property="fimIntervaloParcelamento" maxlength="7" size="7" readonly="true" />
							 				 <html:hidden property="fimIntervaloParcelamentoOriginal"/>
									</logic:equal>
									</logic:present>
									<logic:notPresent name="parametrizacaoParcelamentoCobrancaBancaria" scope="session">
										<html:text property="inicioIntervaloParcelamento" onkeyup="mascaraAnoMes(this, event);" maxlength="7" size="7" />
										<html:hidden property="inicioIntervaloParcelamentoOriginal"/>
										a 
							  			<html:text property="fimIntervaloParcelamento" onkeyup="mascaraAnoMes(this, event);" maxlength="7" size="7" />
							  			<html:hidden property="fimIntervaloParcelamentoOriginal"/>
									</logic:notPresent>
									
							</logic:equal>
						</logic:present>
						
						
					</td>
				</tr>
		
		
			<!-- 	<logic:equal name="EfetuarParcelamentoDebitosActionForm" property="situacaoAguaId" value="<%=LigacaoAguaSituacao.SUPRIMIDO.toString()%>">
				<tr> 
					<td><strong>Com Restabelecimento?<font color="#FF0000">*</font></strong></td>
					<td>
						<strong>
							<html:radio property="indicadorRestabelecimento" value="1"/>Sim
							<html:radio property="indicadorRestabelecimento" value="2"/>N&atilde;o
						</strong>
					</td>
				</tr>
				</logic:equal>
				<logic:equal name="EfetuarParcelamentoDebitosActionForm" property="situacaoAguaId" value="<%=LigacaoAguaSituacao.SUPR_PARC.toString()%>">
				<tr> 
					<td><strong>Com Restabelecimento?<font color="#FF0000">*</font></strong></td>
					<td>
						<strong>
							<html:radio property="indicadorRestabelecimento" value="1"/>Sim
							<html:radio property="indicadorRestabelecimento" value="2"/>N&atilde;o
						</strong>
					</td>
				</tr>
				</logic:equal>
				<logic:equal name="EfetuarParcelamentoDebitosActionForm" property="situacaoAguaId" value="<%=LigacaoAguaSituacao.SUPR_PARC_PEDIDO.toString()%>">
				<tr> 
					<td><strong>Com Restabelecimento?<font color="#FF0000">*</font></strong></td>
					<td>
						<strong>
							<html:radio property="indicadorRestabelecimento" value="1"/>Sim
							<html:radio property="indicadorRestabelecimento" value="2"/>N&atilde;o
						</strong>
					</td>
				</tr>
				</logic:equal> -->
				
				
				<logic:present name="parametrizacaoParcelamentoRestabelecimento" scope="session">
					<logic:equal name="parametrizacaoParcelamentoRestabelecimento" value="S">
				
				<tr> 
					<td><strong>Com Restabelecimento?<font color="#FF0000">*</font></strong></td>
					<td>						
							<strong>
								<strong>
									<html:radio property="indicadorRestabelecimento" value="1"/>Sim
									<html:radio property="indicadorRestabelecimento" value="2"/>N&atilde;o
								</strong>
						 	</strong>					
					 </td>
				</tr>
				
				   </logic:equal>
				</logic:present>
				
				<tr> 
					<td><strong>Considerar Contas em Revis&atilde;o?<font color="#FF0000">*</font></strong></td>
					<td>
						<logic:present name="parametrizacaoParcelamentoCobrancaBancaria" scope="session">
							<logic:equal name="parametrizacaoParcelamentoCobrancaBancaria" value="S">
							<strong>
								<html:radio property="indicadorContasRevisao" value="1" onclick="javascript:habilitaMotivoRevisao();" disabled="true" />Sim
								<html:radio property="indicadorContasRevisao" value="2" onclick="javascript:habilitaMotivoRevisao();" disabled="true" />N&atilde;o
						 	</strong>
						 	</logic:equal>
						</logic:present>
						<logic:notPresent name="parametrizacaoParcelamentoCobrancaBancaria" scope="session">
							<strong>
								<html:radio property="indicadorContasRevisao" value="1" onclick="javascript:habilitaMotivoRevisao();" />Sim
								<html:radio property="indicadorContasRevisao" value="2" onclick="javascript:habilitaMotivoRevisao();" />N&atilde;o
						 	</strong>
						</logic:notPresent>
					 </td>
				</tr>
				<tr>
					<td>
						<strong>Código do Motivo de Revisão</strong>
					</td>
					<td>
						<html:select multiple="true" property="idMotivoRevisao" style="width: 420px;" disabled="true">
						<logic:present name="colecaoMotivoRevisao">
							<html:options collection="colecaoMotivoRevisao" labelProperty="descricaoMotivoRevisaoConta" property="id" />
						</logic:present>
						</html:select>
					</td>
				</tr>
				<!-- <html:hidden property="indicadorGuiasPagamento" value="2"/>
				 <tr> 
					<td><strong>Considerar Guia s de Pagamento?<font color="#FF0000">*</font></strong></td>
					<td>
						<strong>
							<html:radio property="indicadorGuiasPagamento" value="1"/>Sim
							<html:radio property="indicadorGuiasPagamento" value="2"/>N&atilde;o
						</strong>
					</td>
				</tr>  -->
				<tr> 
					<td><strong>Considerar Acr&eacute;scimos por Impontualidade?<font color="#FF0000">*</font></strong></td>
					<td>
						<strong>
							
							<logic:equal name="temPermissaoAcrescimoImpontualidade" value="true">
							    <html:radio property="indicadorAcrescimosImpotualidade" value="1"/>Sim
								<html:radio property="indicadorAcrescimosImpotualidade" value="2"/>N&atilde;o
							</logic:equal>
							<logic:notEqual name="temPermissaoAcrescimoImpontualidade" value="true">
							   <html:radio property="indicadorAcrescimosImpotualidade" value="1" disabled="true"/>Sim
								<html:radio property="indicadorAcrescimosImpotualidade" value="2" disabled="true"/>N&atilde;o
							</logic:notEqual>
							
						</strong>
					</td>
				</tr>
				<tr> 
					<td><strong>Considerar D&eacute;bitos a Cobrar?<font color="#FF0000">*</font></strong></td>
					<td>
						<logic:present name="parametrizacaoParcelamentoCobrancaBancaria" scope="session">
							
							<strong>
								<html:radio property="indicadorDebitosACobrar" value="1" disabled="true"/>Sim
								<html:radio property="indicadorDebitosACobrar" value="2" disabled="true"/>N&atilde;o
							</strong>
							
						</logic:present>
						
						<logic:notPresent name="parametrizacaoParcelamentoCobrancaBancaria" scope="session">
							
							<logic:present name="desabilitarDebitoACobrar" scope="session">
								
								<strong>
									<html:radio property="indicadorDebitosACobrar" value="1" disabled="true"/>Sim
									<html:radio property="indicadorDebitosACobrar" value="2" disabled="true"/>N&atilde;o
								</strong>
								
							</logic:present>
							
							<logic:notPresent name="desabilitarDebitoACobrar" scope="session">
								
								<strong>
								    <html:radio property="indicadorDebitosACobrar" value="1"/>Sim
									<html:radio property="indicadorDebitosACobrar" value="2"/>N&atilde;o
								</strong>
									
							</logic:notPresent>
							
						</logic:notPresent>
					</td>
				</tr>
				
				<tr> 
					<td><strong>Considerar Cr&eacute;ditos a Realizar?<font color="#FF0000">*</font></strong></td>
					
					<td>
						<logic:present name="parametrizacaoParcelamentoCobrancaBancaria" scope="session">
							
							<strong>
								<html:radio property="indicadorCreditoARealizar" value="1" disabled="true"/>Sim
								<html:radio property="indicadorCreditoARealizar" value="2" disabled="true"/>N&atilde;o
							</strong>
							
						</logic:present>
						
						<logic:notPresent name="parametrizacaoParcelamentoCobrancaBancaria" scope="session">
							
							<logic:present name="desabilitarCreditoARealizar" scope="session">
								
								<strong>
									<html:radio property="indicadorCreditoARealizar" value="1" disabled="true"/>Sim
									<html:radio property="indicadorCreditoARealizar" value="2" disabled="true"/>N&atilde;o
								</strong>
								
							</logic:present>
							
							<logic:notPresent name="desabilitarCreditoARealizar" scope="session">
								
								<strong>
								    <html:radio property="indicadorCreditoARealizar" value="1"/>Sim
									<html:radio property="indicadorCreditoARealizar" value="2"/>N&atilde;o
								</strong>
									
							</logic:notPresent>
							
						</logic:notPresent>
					</td>
				</tr>

				<tr>
			
					<td><strong>Considerar Guias de Pagamento?<font color="#FF0000">*</font></strong></td>
					<td>
						<!-- 
						<strong>
							<input id="guia" type="radio" name="indicadorGuiasPagamento" value="1"/>Sim
							<input id="guia" type="radio" name="indicadorGuiasPagamento" value="2"/>N&atilde;o
						</strong>
						  -->
						<logic:present name="parametrizacaoParcelamentoCobrancaBancaria" scope="session">
							<logic:equal name="parametrizacaoParcelamentoCobrancaBancaria" value="S">
							<strong>
								<html:radio property="indicadorGuiasPagamento" value="1" disabled="true"/>Sim
								<html:radio property="indicadorGuiasPagamento" value="2" disabled="true"/>N&atilde;o
							</strong>
							</logic:equal>
						</logic:present>
						<logic:notPresent name="parametrizacaoParcelamentoCobrancaBancaria" scope="session">
							<strong>
								<html:radio property="indicadorGuiasPagamento" value="1"/>Sim
								<html:radio property="indicadorGuiasPagamento" value="2"/>N&atilde;o
							</strong>
						</logic:notPresent>
					</td>
				</tr>			
				<tr> 
					<td>&nbsp;</td>
					<td><font color="#FF0000">*</font> Campo Obrigat&oacute;rio</td>
				</tr>
				<tr>
					<td colspan="2">
						<jsp:include page="/jsp/util/wizard/navegacao_botoes_wizard_valida_avancar_valida_voltar.jsp?numeroPagina=1"/>
					</td>
				</tr>			
				<!-- Fim do Corpo - Roberta Costa  11/01/2006  -->
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>
	<%@ include file="/jsp/util/rodape.jsp"%>
	
	<logic:present name="indicadorFauramentoTitularDebito" scope="request">
		<SCRIPT LANGUAGE="JavaScript">
		<!--
		
			if (document.forms[0].idClienteImovel != undefined) {
				if (document.forms[0].idClienteImovel.length != undefined) {
					var i = 0;
					for (i = 0; i < document.forms[0].valorClienteImovel.length; i++) { 
					    if (document.forms[0].valorClienteImovel[i].value == document.forms[0].idClienteRelacaoImovelSelecionado.value) {
					    	document.forms[0].idClienteImovel[i].checked = true;
					    } else {
					    	document.forms[0].idClienteImovel[i].checked = false;
					    }
					}				
				} else {
				    if (document.forms[0].valorClienteImovel.value == document.forms[0].idClienteRelacaoImovelSelecionado.value) {
				    	document.forms[0].idClienteImovel.checked = true;			    	
				    }				
				}
			}
	
		//-->
		</SCRIPT>
	</logic:present>		
		
</html:form>
</div>
<%@ include file="/jsp/util/telaespera.jsp"%>

<script>
document.getElementById('botaoConcluir').style.visibility='hidden';
document.getElementById('botaoConcluir').onclick = function() { botaoAvancarTelaEspera('/gsan/efetuarParcelamentoDebitosWizardAction.do?concluir=true&action=processarProcesso1Action'); }
</script>

</body>
<!-- parcelamento_debito_efetuar_processo1.jsp -->
</html:html>