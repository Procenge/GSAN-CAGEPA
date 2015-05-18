<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<%@ page import="gcom.util.ConstantesSistema,java.util.Collection"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
<html:html>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script><script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="ConsultarPagamentoActionForm" />

<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/engine.js'> </script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/util.js'> </script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/interface/AjaxService.js'> </script>


<script language="JavaScript">
<!-- Begin 
var bCancel = false; 

//function validateConsultarPagamentoActionForm(form) {                                                                   
	//if (bCancel) 
		//return true; 
//	else 
	//	return validateDate(form)
		// && validateCaracterEspecial(form)  
		// && validaMesAno(form)
		//; 
//} 

function caracteresespeciais () { 
	//this.aa = new Array("matriculaImovel", "Matrícula do Imóvel possui caracteres especiais.", new Function ("varName", " return this[varName];"));
	//this.ab = new Array("fimIntervaloParcelamento", "Mês/Ano de Referência final possui caracteres especiais.", new Function ("varName", " return this[varName];"));
} 

/*function DateValidations(){ 
	this.aa = new Array("dataPagamentoInicio", "Data de Pagamento Inicial não é uma data válida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
	this.ab = new Array("dataPagamentoFim", "Data de Pagamento Final não é uma data válida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
} 
*/
function validaMesAno(mesAno){
	if(mesAno.value != ""){
		return verificaAnoMesMensagemPersonalizada(mesAno,"Mês/Ano de Referência inválido");
	}else{
		return true;
	}
}

function replicaDados(campoOrigem, campoDestino){
	campoDestino.value = campoOrigem.value;
}
/*function validaData(data){ 
	if(verificaData(data)){
		return false;
	}
}*/


//Vivianne Sousa		
function verificarChecado(valor){
	form = document.forms[0];

	if (form.indicadorAtualizar != null) {
		if (valor == "1") {
			form.indicadorAtualizar.checked = true;
		} else {
			form.indicadorAtualizar.checked = false;
		}
	}
}


function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	var form = document.ConsultarPagamentoActionForm;
	if (tipoConsulta == 'imovel') {
		form.idImovel.value = codigoRegistro;
		form.inscricao.value = descricaoRegistro;
		form.idCliente.value = "";
		form.idCliente.readOnly = true;
		form.clienteRelacaoTipo.disabled = true;
		form.localidadeInicial.readOnly = true;
		form.localidadeFinal.readOnly = true;
		form.codigoSetorComercialInicial.readOnly = true;
		form.codigoSetorComercialFinal.readOnly = true;
	}
	if (tipoConsulta == 'cliente') {
		form.idCliente.value = codigoRegistro;
		form.nomeCliente.value = descricaoRegistro;
		form.idImovel.value = "";
		form.idImovel.readOnly = true;
		form.localidadeInicial.readOnly = true;
		form.localidadeFinal.readOnly = true;
		form.clienteRelacaoTipo.disabled = false;
		form.codigoSetorComercialInicial.readOnly = true;
		form.codigoSetorComercialFinal.readOnly = true;
	}
	if (tipoConsulta == 'localidadeOrigem') {
		form.localidadeInicial.value = codigoRegistro;
		form.descricaoLocalidadeInicial.value = descricaoRegistro;
		form.localidadeFinal.value = codigoRegistro;
		form.descricaoLocalidadeFinal.value = descricaoRegistro;
		form.idImovel.readOnly = true;
		form.idCliente.readOnly = true;
		form.clienteRelacaoTipo.disabled = true;
	}	
	if (tipoConsulta == 'localidadeDestino') {
		form.localidadeFinal.value = codigoRegistro;
		form.descricaoLocalidadeFinal.value = descricaoRegistro;
		form.idImovel.readOnly = true;
		form.idCliente.readOnly = true;
		form.clienteRelacaoTipo.disabled = true;
	}
  	if (tipoConsulta == 'arrecadador') {
    	form.codigoArrecadadorAuxiliar.value = codigoRegistro;
    	form.nomeArrecadadorAuxiliar.value = descricaoRegistro;
    	form.nomeArrecadadorAuxiliar.style.color = "#000000";
  	}
}
 
function recuperarDadosCincoParametros(codigoRegistro, descricaoRegistro1, descricaoRegistro2, descricaoRegistro3, tipoConsulta) { 
	var form = document.ConsultarPagamentoActionForm;
 	if (tipoConsulta == 'avisoBancario') {
		form.idAvisoBancario.value = codigoRegistro;
		form.codigoAgenteArrecadador.value = descricaoRegistro1;
		form.dataLancamentoAviso.value = descricaoRegistro2;
		form.numeroSequencialAviso.value = descricaoRegistro3;
		form.idCliente.value = "";	
		form.nomeCliente.value = "";
		form.idCliente.readOnly = true;
		form.clienteRelacaoTipo.value = "";
		form.clienteRelacaoTipo.disabled = true;
		form.idImovel.value = "";	
		form.inscricao.value = "";
		form.idImovel.readOnly = true;
		form.localidadeInicial.value = "";
		form.descricaoLocalidadeInicial.value = "";
		form.localidadeInicial.readOnly = true;
		form.localidadeFinal.value = "";
		form.descricaoLocalidadeFinal.value = "";
		form.localidadeFinal.readOnly = true;
		form.codigoSetorComercialInicial.value = "";
		form.descricaoSetorComercialInicial.value = "";
		form.codigoSetorComercialFinal.value = "";
		form.descricaoSetorComercialFinal.value = "";
		form.codigoSetorComercialInicial.readOnly = true;
		form.codigoSetorComercialFinal.readOnly = true;
	}
}

function recuperarDadosSeisParametros(codigoRegistro, descricaoRegistro1, descricaoRegistro2, descricaoRegistro3, descricaoRegistro4, tipoConsulta) { 
	var form = document.ConsultarPagamentoActionForm;
 	if (tipoConsulta == 'movimentoArrecadador') {
		form.idArrecadador.value = codigoRegistro;
		form.codigoBanco.value = descricaoRegistro1;
		form.codigoRemessa.value = descricaoRegistro2;
		form.identificacaoServico.value = descricaoRegistro3;
		form.numeroSequencial.value = descricaoRegistro4;
		form.idCliente.value = "";	
		form.nomeCliente.value = "";
		form.idCliente.readOnly = true;
		form.clienteRelacaoTipo.value = "";
		form.clienteRelacaoTipo.disabled = true;
		form.idImovel.value = "";	
		form.inscricao.value = "";
		form.idImovel.readOnly = true;
		form.localidadeInicial.value = "";
		form.descricaoLocalidadeInicial.value = "";
		form.localidadeInicial.readOnly = true;
		form.localidadeFinal.value = "";
		form.descricaoLocalidadeFinal.value = "";
		form.localidadeFinal.readOnly = true;
		form.codigoSetorComercialInicial.value = "";
		form.descricaoSetorComercialInicial.value = "";
		form.codigoSetorComercialFinal.value = "";
		form.descricaoSetorComercialFinal.value = "";
		form.codigoSetorComercialInicial.readOnly = true;
		form.codigoSetorComercialFinal.readOnly = true;
	}
}

function limparImovel() {
	var form = document.ConsultarPagamentoActionForm;
	if(form.idImovel.value != '' || form.inscricao.value != '' ){
		form.idImovel.value = "";
		form.inscricao.value = "";
		form.idCliente.readOnly = false;
		form.localidadeInicial.readOnly = false;
		form.localidadeFinal.readOnly = false;
		form.codigoSetorComercialInicial.readOnly = false;
		form.codigoSetorComercialFinal.readOnly = false;
	}	
}

function limparCliente() {
	var form = document.ConsultarPagamentoActionForm;
	if(form.idCliente.value != '' || form.nomeCliente.value != ''){
		form.idCliente.value = "";
		form.nomeCliente.value = "";
		form.clienteRelacaoTipo.value = "";
		form.idImovel.readOnly = false;
		form.clienteRelacaoTipo.disabled = true;
		form.localidadeInicial.readOnly = false;
		form.localidadeFinal.readOnly = false;
		form.codigoSetorComercialInicial.readOnly = false;
		form.codigoSetorComercialFinal.readOnly = false;
	}	
}

function limparLocalidadeInicial(){
	var form = document.ConsultarPagamentoActionForm;
	if(form.localidadeInicial.value != '' || form.descricaoLocalidadeInicial.value != ''){
		form.localidadeInicial.value = "";
		form.descricaoLocalidadeInicial.value = "";
		form.idImovel.readOnly = false;
		form.idCliente.readOnly = false;
		form.localidadeFinal.value = "";
		form.descricaoLocalidadeFinal.value = "";
		form.idImovel.readOnly = false;
		form.idCliente.readOnly = false;
		form.codigoSetorComercialInicial.value = "";
		form.descricaoSetorComercialInicial.value = "";
		form.codigoSetorComercialFinal.value = "";
		form.descricaoSetorComercialFinal.value = "";
	}

	form.action = "/gsan/exibirFiltrarPagamentoAction.do";
	form.submit();
}

function limparLocalidadeFinal() {
	var form = document.ConsultarPagamentoActionForm;
	if(form.localidadeFinal.value != '' || form.descricaoLocalidadeFinal.value != ''){
		form.localidadeFinal.value = "";
		form.descricaoLocalidadeFinal.value = "";
		form.idImovel.readOnly = false;
		form.idCliente.readOnly = false;
		form.codigoSetorComercialInicial.value = "";
		form.descricaoSetorComercialInicial.value = "";
		form.codigoSetorComercialFinal.value = "";
		form.descricaoSetorComercialFinal.value = "";
	}

	form.action = "/gsan/exibirFiltrarPagamentoAction.do";
	form.submit();
}

function limparSetorComercialInicial(){
	var form = document.ConsultarPagamentoActionForm;

	form.codigoSetorComercialInicial.value = "";
	form.descricaoSetorComercialInicial.value = "";
}

function limparSetorComercialFinal(){
	var form = document.ConsultarPagamentoActionForm;

	form.codigoSetorComercialFinal.value = "";
	form.descricaoSetorComercialFinal.value = "";
}

function limparArrecadador(){
	var form = document.ConsultarPagamentoActionForm;

	form.codigoArrecadadorAuxiliar.value = "";
	form.nomeArrecadadorAuxiliar.value = "";
}

function adicionarArrecadador(){
	form.action = "/gsan/exibirFiltrarPagamentoAction.do?operacao=adicionarArrecadador";
	form.submit();
}

function removerArrecadador(codigoArrecadador){
	form.action = "/gsan/exibirFiltrarPagamentoAction.do?operacao=removerArrecadador&codigo=" + codigoArrecadador;
	form.submit();
}

function chamarReloadSemParametro(){
	form.action = "/gsan/exibirFiltrarPagamentoAction.do";
	form.submit();
}

function chamarPopupComDependencia(url, tipo, objeto, codigoObjeto, altura, largura, msg){
	if (objeto == null || codigoObjeto == null){
		abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
	}else{
		if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
			alert(msg);
		}else{
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
	} else if (tipoConsulta == 'setorComercialDestino') {
      	form.codigoSetorComercialFinal.value = codigoRegistro;
	    form.descricaoSetorComercialFinal.value = descricaoRegistro;
      	form.descricaoSetorComercialFinal.style.color = "#000000";
	}
}

function limparAvisoBancario() {
	var form = document.ConsultarPagamentoActionForm;
	if(form.idAvisoBancario.value != ''){
		form.idAvisoBancario.value = "";
		form.codigoAgenteArrecadador.value = "";
		form.dataLancamentoAviso.value = "";
		form.numeroSequencialAviso.value = "";
		form.idImovel.readOnly = false;
		form.idCliente.readOnly = false;
		form.clienteRelacaoTipo.disabled = true;
		form.localidadeInicial.readOnly = false;
		form.localidadeFinal.readOnly = false;
		form.codigoSetorComercialInicial.readOnly = false;
		form.codigoSetorComercialFinal.readOnly = false;
	}	
}

function limparMovimento() {
	var form = document.ConsultarPagamentoActionForm;
	if(form.idArrecadador.value != ''){
		form.idArrecadador.value = "";
		form.codigoBanco.value = "";
		form.codigoRemessa.value = "";
		form.identificacaoServico.value = "";
		form.numeroSequencial.value = "";
		form.idImovel.readOnly = false;
		form.idCliente.readOnly = false;
		form.clienteRelacaoTipo.disabled = true;
		form.localidadeInicial.readOnly = false;
		form.localidadeFinal.readOnly = false;
		form.codigoSetorComercialInicial.readOnly = false;
		form.codigoSetorComercialFinal.readOnly = false;
	}	
}

function controleImovel(){
	var form = document.ConsultarPagamentoActionForm;
	if(form.idImovel.value.length > 0){
		form.idCliente.readOnly = true;
		form.localidadeInicial.readOnly = true;
		form.localidadeFinal.readOnly = true;
		form.codigoSetorComercialInicial.readOnly = true;
		form.codigoSetorComercialFinal.readOnly = true;
	}
	else
	{
		form.idCliente.readOnly = false;
		form.localidadeInicial.readOnly = false;
		form.localidadeFinal.readOnly = false;
		form.codigoSetorComercialInicial.readOnly = false;
		form.codigoSetorComercialFinal.readOnly = false;
	}
}

function validaEnterImovel(tecla, caminhoActionReload, nomeCampo) {
	var form = document.ConsultarPagamentoActionForm;
	
	validaEnterComMensagem(tecla, caminhoActionReload, nomeCampo, "Matrícula do Imóvel");
	
	if(form.idImovel.value.length > 0) {
		form.idCliente.value = "";	
		form.nomeCliente.value = "";
		form.localidadeInicial.value = "";
		form.localidadeFinal.value = "";
		form.descricaoLocalidadeInicial.value = "";
		form.descricaoLocalidadeFinal.value = "";
		form.clienteRelacaoTipo.disabled = true;
		form.idCliente.readOnly = true;
		form.localidadeInicial.readOnly = true;
		form.localidadeFinal.readOnly = true;
		form.codigoSetorComercialInicial.value = "";
		form.descricaoSetorComercialInicial.value = "";
		form.codigoSetorComercialFinal.value = "";
		form.descricaoSetorComercialFinal.value = "";
		form.codigoSetorComercialInicial.readOnly = true;
		form.codigoSetorComercialFinal.readOnly = true;
	} else {
	    form.idCliente.readOnly = false;
		form.localidadeInicial.readOnly = false;
		form.localidadeFinal.readOnly = false;
		form.idImovel.value = "";
        form.inscricao.value = "";
        form.nomeCliente.value = "";
		form.localidadeInicial.value = "";
		form.localidadeFinal.value = "";
		form.descricaoLocalidadeInicial.value = "";
		form.descricaoLocalidadeFinal.value = "";
		form.codigoSetorComercialInicial.value = "";
		form.descricaoSetorComercialInicial.value = "";
		form.codigoSetorComercialFinal.value = "";
		form.descricaoSetorComercialFinal.value = "";
		form.codigoSetorComercialInicial.readOnly = false;
		form.codigoSetorComercialFinal.readOnly = false;
	}
}

function controleCliente(){
	var form = document.ConsultarPagamentoActionForm;
	if(form.idCliente.value.length > 0){
		form.idImovel.readOnly = true;
		form.localidadeInicial.readOnly = true;
		form.localidadeFinal.readOnly = true;
		form.clienteRelacaoTipo.disabled = false;
		form.codigoSetorComercialInicial.readOnly = true;
		form.codigoSetorComercialFinal.readOnly = true;
	}else{
		form.idImovel.readOnly = false;
		form.localidadeInicial.readOnly = false;
		form.localidadeFinal.readOnly = false;
		form.clienteRelacaoTipo.disabled = true;
		form.codigoSetorComercialInicial.readOnly = false;
		form.codigoSetorComercialFinal.readOnly = false;
	}
}

function validaEnterCliente(tecla, caminhoActionReload, nomeCampo) {
	var form = document.ConsultarPagamentoActionForm;
	
	validaEnterComMensagem(tecla, caminhoActionReload, nomeCampo, "Código do Cliente");
	if(form.idCliente.value.length > 0) {
    	form.idImovel.value = "";	
		form.inscricao.value = "";
		form.localidadeInicial.value = "";
		form.localidadeFinal.value = "";
		form.descricaoLocalidadeInicial.value = "";
		form.descricaoLocalidadeFinal.value = "";
		form.idImovel.readOnly = true;
		form.localidadeInicial.readOnly = true;
		form.localidadeFinal.readOnly = true;
		form.codigoSetorComercialInicial.value = "";
		form.descricaoSetorComercialInicial.value = "";
		form.codigoSetorComercialFinal.value = "";
		form.descricaoSetorComercialFinal.value = "";
		form.codigoSetorComercialInicial.readOnly = true;
		form.codigoSetorComercialFinal.readOnly = true;
    } else {
		form.idImovel.readOnly = false;
		form.localidadeInicial.readOnly = false;
		form.localidadeFinal.readOnly = false;
		form.idCliente.value = "";
        form.nomeCliente.value = "";
		form.localidadeInicial.value = "";
		form.localidadeFinal.value = "";
		form.descricaoLocalidadeInicial.value = "";
		form.descricaoLocalidadeFinal.value = "";
		form.codigoSetorComercialInicial.value = "";
		form.descricaoSetorComercialInicial.value = "";
		form.codigoSetorComercialFinal.value = "";
		form.descricaoSetorComercialFinal.value = "";
		form.codigoSetorComercialInicial.readOnly = false;
		form.codigoSetorComercialFinal.readOnly = false;
	}
}

function controleLocalidadeInicial(){
	var form = document.ConsultarPagamentoActionForm;
	if(form.localidadeInicial.value.length > 0){
		form.idImovel.readOnly = true;
		form.idCliente.readOnly = true;
	}
	else
	{
		form.idImovel.readOnly = false;
		form.idCliente.readOnly = false;
	}
}

function validaEnterLocalidadeInicial(tecla, caminhoActionReload, nomeCampo) {
	var form = document.ConsultarPagamentoActionForm;
	
	validaEnterComMensagem(tecla, caminhoActionReload, nomeCampo, "Localidade Inicial");
	
	if(form.localidadeInicial.value.length > 0) {
		form.idImovel.readOnly = true;
    	form.idImovel.value = "";	
		form.inscricao.value = "";
		form.idCliente.readOnly = true;
		form.idCliente.value = "";	
		form.nomeCliente.value = "";
		form.clienteRelacaoTipo.disabled = true;
    } else {
		form.idImovel.readOnly = false;
		form.idCliente.readOnly = false;
		form.idCliente.value = "";
        form.nomeCliente.value = "";
	}
}

function controleLocalidadeFinal(){
	var form = document.ConsultarPagamentoActionForm;
	if(form.localidadeFinal.value.length > 0){
		form.idImovel.readOnly = true;
		form.idCliente.readOnly = true;
	}else{
		form.idImovel.readOnly = false;
		form.idCliente.readOnly = false;
	}
}

function validaEnterLocalidadeFinal(tecla, caminhoActionReload, nomeCampo) {
	var form = document.ConsultarPagamentoActionForm;
	
	validaEnterComMensagem(tecla, caminhoActionReload, nomeCampo, "Localidade Final");
	
	if(form.localidadeFinal.value.length > 0) {
		form.idImovel.readOnly = true;
    	form.idImovel.value = "";	
		form.inscricao.value = "";
		form.idCliente.readOnly = true;
		form.idCliente.value = "";	
		form.nomeCliente.value = "";
		form.clienteRelacaoTipo.disabled = true;
    } else {
		form.idImovel.readOnly = false;
		form.idCliente.readOnly = false;
		form.idCliente.value = "";
        form.nomeCliente.value = "";
	}
}

function controleAvisoBancario(){
	var form = document.ConsultarPagamentoActionForm;
	if( form.idImovel.value == '' && form.idCliente.value == '' && form.idArrecadador.value == '' 
		&& form.localidadeInicial.value == '' &&  form.localidadeFinal.value == '' ){
		chamarPopup('exibirPesquisarAvisoBancarioAction.do?limparForm=1', 'aviso', null, null, 475, 765, '',document.forms[0].idAvisoBancario);
   	}
}

function controleMovimentoArrecadador(){
	var form = document.ConsultarPagamentoActionForm;
	
	if( form.idImovel.value == '' && form.idCliente.value == '' && form.idAvisoBancario.value == '' 
		&& form.localidadeInicial.value == '' &&  form.localidadeFinal.value == '' ){
		chamarPopup('exibirPesquisarMovimentoArrecadadorAction.do?', 'arrecadador', null, null, 475, 765, '',document.forms[0].idArrecadador);
   	}
}

function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){
	if(objetoRelacionado.readOnly != true){
		if (objeto == null || codigoObjeto == null){
			abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
		}else{
			if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
				alert(msg);
			}else{
				abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
			}
		}
	}
}

function controleCamposAvisoBancarioMovimentoArrecadador() {
	
	var form = document.ConsultarPagamentoActionForm;

	if (form.idAvisoBancario.value != null && form.idAvisoBancario.value != '') {
		form.idImovel.readOnly = true;
		form.idCliente.readOnly = true;
		form.clienteRelacaoTipo.disabled = true;
		form.localidadeInicial.readOnly = true;
		form.localidadeFinal.readOnly = true;
		form.codigoSetorComercialInicial.readOnly = true;
		form.codigoSetorComercialFinal.readOnly = true;
	} else if (form.idArrecadador.value != null && form.idArrecadador.value != '') {
		form.idImovel.readOnly = true;
		form.idCliente.readOnly = true;
		form.clienteRelacaoTipo.disabled = true;
		form.localidadeInicial.readOnly = true;
		form.localidadeFinal.readOnly = true;
		form.codigoSetorComercialInicial.readOnly = true;
		form.codigoSetorComercialFinal.readOnly = true;
	}
}


function validarForm(form){
	/*
	urlRedirect = "/gsan/consultarPagamentoAction.do"

	enviarSelectMultiplo('ConsultarPagamentoActionForm','debitoTipoSelecionados');
  	if(form.idCliente.value != "" && form.idImovel.value != ""){
		redirecionarSubmit(urlRedirect);
	}else{
		alert("É necessário informar o código do imóvel ou o código do cliente para realizar a busca.");
	}
	*/
	
	var retorno = true;
	var objPeriodoArrecadacaoInicio = returnObject(form, "periodoArrecadacaoInicio");
	var objPeriodoArrecadacaoFim    = returnObject(form, "periodoArrecadacaoFim");
	var objPeriodoPagamentoInicio = returnObject(form, "periodoPagamentoInicio");
	var objPeriodoPagamentoFim    = returnObject(form, "periodoPagamentoFim");
	var objDataPagamentoInicio = returnObject(form, "dataPagamentoInicio");
	var objDataPagamentoFim    = returnObject(form, "dataPagamentoFim");
	
	
	if (validateConsultarPagamentoActionForm(form))
	{
	
	if(form.idCliente.value == '' && 
		form.idImovel.value == '' && 
		(form.localidadeInicial.value == '' || form.localidadeFinal.value == '')&&
		form.idAvisoBancario.value == '' && 
		form.codigoAgenteArrecadador.value == '' &&
		form.idArrecadador.value == '' ){
		
		alert('Informe Matrícula do Imóvel ou Código do Cliente ou Intervalo de Localidades ou Aviso Bancário ou Movimento Arrecadador.');
		
	}else {
	
		// Validações do caso de uso
		if ((objPeriodoArrecadacaoInicio.value != "" && objPeriodoArrecadacaoFim.value != "" ) 
			&& (comparaMesAno(objPeriodoArrecadacaoInicio.value, ">", objPeriodoArrecadacaoFim.value))){
			alert("Mês/Ano Final do Período Refer. Arrecadação é anterior ao Mês/Ano Inicial do Período Refer. Arrecadação.");
			return false;
	    }else if ((objPeriodoPagamentoInicio.value != "" && objPeriodoPagamentoFim.value != "" ) 
			&& (comparaMesAno(objPeriodoPagamentoInicio.value, ">", objPeriodoPagamentoFim.value))){
			alert("Mês/Ano Final do Período Refer. Pagamento é anterior ao Mês/Ano Inicial do Período Refer. Pagamento.");
			return false;
		}else if ((objDataPagamentoInicio.value != "" && objDataPagamentoFim.value != "" ) 
	    	&& (comparaData(objDataPagamentoInicio.value, ">", objDataPagamentoFim.value))){
			alert("Data Final do Período de Pagamento é anterior à Data Inicial do Período de Pagamento.");
			return false;
		}
		
		enviarSelectMultiplo('ConsultarPagamentoActionForm','debitoTipoSelecionados');
		submeterFormPadrao(form);
	}
		
		//if( retorno == true ){
		//	submeterFormPadrao(form);
		//}	
		
		}
		
	//}
	//End --> 
}


function enviarTotalizador(){
	var form = document.ConsultarPagamentoActionForm;
	var indicadorTotalizarPorDataPagamento = form.indicadorTotalizarPorDataPagamento.value;
	AjaxService.carregaTotalizadorDiarioPagamento(indicadorTotalizarPorDataPagamento, {callback: 
		function(indicadorTotalizarPorDataPagamento) {
		
			form.totalizadorMes[0].disabled =  indicadorTotalizarPorDataPagamento != "1";
			form.totalizadorMes[1].disabled =  indicadorTotalizarPorDataPagamento != "1";
			
			
			if(indicadorTotalizarPorDataPagamento == "1"){
				form.totalizadorMes[0].click();
				
			}
				
		}
	});
	
}
</script>
</head>
<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');verificarChecado('${sessionScope.indicadorAtualizar}');">
<html:form action="/consultarPagamentoAction" 
	name="ConsultarPagamentoActionForm"
	type="gcom.gui.arrecadacao.pagamento.ConsultarPagamentoActionForm"
	onsubmit="return validateConsultarPagamentoActionForm(this);"
	method="post">
	
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
			<table height="100%">
				<tr><td></td></tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td width="11">
						<img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" />
					</td>
					<td class="parabg">Filtrar Pagamentos</td>
					<td width="11">
						<img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" />
					</td>
				</tr>
			</table>
			<!-- Início do Corpo - Roberta Costa -->
			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td colspan="3">
						<p>Para filtrar os pagamentos, informe os dados abaixo:</p>
					</td>
					<td align="right">
						&nbsp;
						
						<%if(request.getAttribute("tela") != null || session.getAttribute("tela") != null  && !"estorno".equals(session.getAttribute("tela"))){%>	
							<input type="checkbox" name="indicadorAtualizar" value="1" /><strong>Atualizar</strong>
						<%} %>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<table width="100%" border="0">
							<tr>
								<td width="30%"><strong>Matrícula do Imóvel:</strong></td>
								<td>
									<html:text property="idImovel" size="9" maxlength="9" 
										onkeyup="validaEnterImovel(event, 'exibirFiltrarPagamentoAction.do', 'idImovel');document.forms[0].inscricao.value=''"
										onkeypress="document.forms[0].inscricao.value='';" />
									<a href="javascript:chamarPopup('exibirPesquisarImovelAction.do', 'imovel', null, null, 275, 480, '',document.forms[0].idImovel);">	
										<img width="23" height="21" src="<bean:message key="caminho.imagens"/>pesquisa.gif"
											style="cursor:hand;" alt="Pesquisar" border="0"/></a> 
									<logic:present name="matriculaInexistente" scope="request">
										<html:text property="inscricao" size="35" maxlength="35"
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: #ff0000" />
									</logic:present>
									<logic:notPresent name="matriculaInexistente" scope="request">
										<html:text property="inscricao" size="35" maxlength="35"
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: #000000" />
									</logic:notPresent> 
									<a href="javascript:limparImovel();">
										<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"border="0" title="Apagar" />
									</a>
								</td>
							</tr>
							<tr>
							<tr>
								<td><strong>Código do Cliente:</strong></td>
								<td>
									<strong>
									<html:text property="idCliente" size="9" maxlength="9" 
										onkeyup="return validaEnterCliente(event, 'exibirFiltrarPagamentoAction.do', 'idCliente');document.forms[0].nomeCliente.value=''"
										onkeypress="document.forms[0].nomeCliente.value='';" />
									<a href="javascript:chamarPopup('exibirPesquisarClienteAction.do', 'cliente', null, null, 400, 800, '',document.forms[0].idCliente);">	
										<img width="23" height="21" src="<bean:message key="caminho.imagens"/>pesquisa.gif"
											style="cursor:hand;" alt="Pesquisar" border="0" /></a> 
									<logic:present name="clienteInexistente" scope="request">
										<html:text property="nomeCliente" size="35" maxlength="35"
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: #ff0000" />
									</logic:present>
									<logic:notPresent name="clienteInexistente" scope="request">
										<html:text property="nomeCliente" size="35" maxlength="35"
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: #000000" />
									</logic:notPresent>
									<a href="javascript:limparCliente();"> 
										<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" /></a>
									</strong>
								</td>
							</tr>
							<tr>
								<td><strong>Tipo da Relação:</strong></td>
								<td>
									<html:select property="clienteRelacaoTipo" style="width: 230px;">
										<logic:present name="colecaoClienteRelacaoTipo">
											<html:option value="">&nbsp;</html:option>
											<html:options collection="colecaoClienteRelacaoTipo" labelProperty="descricao" property="id" />
										</logic:present>
									</html:select>
								</td>
							</tr>
							<tr>
								<td><strong>Localidade Inicial:</strong></td>
								<td>
									<strong>
									<html:text property="localidadeInicial" size="3" maxlength="3" 
										onkeypress="document.forms[0].descricaoLocalidadeInicial.value='';document.forms[0].descricaoLocalidadeFinal.value='';" 
										onkeyup="
										replicaDados(document.forms[0].localidadeInicial, document.forms[0].localidadeFinal);
										return validaEnterLocalidadeInicial(event, 'exibirFiltrarPagamentoAction.do', 'localidadeInicial');
										document.forms[0].descricaoLocalidadeInicial.value=''"
										onblur="javascript:chamarReloadSemParametro();"/>
									<a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'origem', null, null, 400, 800, '',document.forms[0].localidadeInicial);">	
										<img width="23" height="21" src="<bean:message key="caminho.imagens"/>pesquisa.gif"
											style="cursor:hand;" alt="Pesquisar" border="0"/></a>
									<logic:present name="localidadeInexistente" scope="request">
										<html:text property="descricaoLocalidadeInicial" readonly="true"
											style="background-color:#EFEFEF; border:0; color: #ff0000"
											size="40" maxlength="40" />
									</logic:present>
									<logic:notPresent name="localidadeInexistente" scope="request">
										<html:text property="descricaoLocalidadeInicial" readonly="true"
											style="background-color:#EFEFEF; border:0" size="40"
											maxlength="40" />
									</logic:notPresent>		
									<a href="javascript:limparLocalidadeInicial();"> 
										<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" /></a>
									</strong>
								</td>
							</tr>
							<tr>
								<td><strong>Localidade Final:</strong></td>
								<td>
									<strong><html:text property="localidadeFinal" size="3" maxlength="3"
										onkeypress="document.forms[0].descricaoLocalidadeFinal.value='';"
										onkeyup="return validaEnterLocalidadeFinal(event, 'exibirFiltrarPagamentoAction.do', 'localidadeFinal');
										document.forms[0].descricaoLocalidadeFinal.value=''"
										onblur="javascript:chamarReloadSemParametro();"/>
									<a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'destino', null, null, 400, 800, '',document.forms[0].localidadeFinal);">	
										<img width="23" height="21" src="<bean:message key="caminho.imagens"/>pesquisa.gif"
											style="cursor:hand;"  alt="Pesquisar" border="0"/></a> 
									<logic:present name="localidadeInexistente" scope="request">
										<html:text property="descricaoLocalidadeFinal" readonly="true"
											style="background-color:#EFEFEF; border:0; color: #ff0000"
											size="40" maxlength="40" />
									</logic:present>
									<logic:notPresent name="localidadeInexistente" scope="request">
										<html:text property="descricaoLocalidadeFinal" readonly="true"
											style="background-color:#EFEFEF; border:0" size="40"
											maxlength="40" />
									</logic:notPresent>		
									<a href="javascript:limparLocalidadeFinal();"> 
										<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" /></a>
									</strong>
								</td>
							</tr>

					        <logic:present name="habilitaSetor" scope="request">
									<logic:equal name="habilitaSetor" value="true">
							        <tr>
							           <td><strong>Setor Comercial Inicial:</strong></td>
							           <td>
										   <html:text maxlength="3" property="codigoSetorComercialInicial" size="3" onkeypress="validaEnterDependenciaComMensagem(event, 'exibirFiltrarPagamentoAction.do', document.forms[0].codigoSetorComercialInicial, document.forms[0].localidadeInicial.value, 'Localidade Inicial', 'Setor comercial Inicial');"
										   		onkeyup="replicaDados(document.forms[0].codigoSetorComercialInicial, document.forms[0].codigoSetorComercialFinal);javascript:verificaNumeroInteiro(this);"
										   		/>
										   <a href="javascript:chamarPopupComDependencia('exibirPesquisarSetorComercialAction.do', 'setorComercialOrigem','idLocalidade', document.ConsultarPagamentoActionForm.localidadeInicial.value, 275, 480, 'Informe Localidade Inicial.');">
										   <img
													src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"
													width="23" height="21" title="Pesquisar Setor Comercial"></a> 
											<logic:present name="setorComercialInicialInexistente" scope="request">
												<html:text property="descricaoSetorComercialInicial" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
											</logic:present>
							
											<logic:notPresent name="setorComercialInicialInexistente" scope="request">
													<html:text property="descricaoSetorComercialInicial" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
											</logic:notPresent>
										   
											<a href="javascript:limparSetorComercialInicial();"> 
												<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
											</a>
										</td>
							        </tr>
							        <tr>
							           <td><strong>Setor Comercial Final:</strong></td>
							           <td>
										   <html:text maxlength="3" property="codigoSetorComercialFinal" size="3" onkeypress="validaEnterDependenciaComMensagem(event, 'exibirFiltrarPagamentoAction.do', document.forms[0].codigoSetorComercialFinal, document.forms[0].localidadeFinal.value, 'Localidade Final', 'Setor comercial Final');"
										   onkeyup="javascript:verificaNumeroInteiro(this);" 
										   />
										   <a href="javascript:chamarPopupComDependencia('exibirPesquisarSetorComercialAction.do', 'setorComercialDestino', 'idLocalidade', document.ConsultarPagamentoActionForm.localidadeFinal.value, 275, 480, 'Informe Localidade Final.');">
										   <img
													src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"
													width="23" height="21" title="Pesquisar Setor Comercial"></a> 
											<logic:present name="setorComercialFinalInexistente" scope="request">
												<html:text property="descricaoSetorComercialFinal" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
											</logic:present>
							
											<logic:notPresent name="setorComercialFinalInexistente" scope="request">
													<html:text property="descricaoSetorComercialFinal" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
											</logic:notPresent>
										   
											<a href="javascript:limparSetorComercialFinal();"> 
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

							<tr>
								<td><strong>Aviso Bancário:</strong></td>
								<td colspan="3">
									<strong>
									<html:hidden property="idAvisoBancario"/>
									<html:text property="codigoAgenteArrecadador" size="3" maxlength="3"
										readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
									<html:text property="dataLancamentoAviso" size="10" maxlength="10"
										readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" /> 
									<html:text property="numeroSequencialAviso" size="3" maxlength="3"
										readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" /> 
									<a href="javascript:controleAvisoBancario();"> 	
										<img width="23" height="21" src="<bean:message key="caminho.imagens"/>pesquisa.gif"
											style="cursor:hand;" alt="Pesquisar" border="0"/></a> 
									<a href="javascript:limparAvisoBancario();"> 
										<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" /></a>
									</strong>
								</td>
							</tr>
							<tr>
								<td height="29"><strong>Movimento Arrecadador:</strong></td>
								<td colspan="3">
									<html:hidden property="idArrecadador"/>
									<html:text property="codigoBanco" size="3" maxlength="3" disabled="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
									<html:text property="codigoRemessa" size="3" maxlength="3" disabled="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
									<html:text property="identificacaoServico" size="30" maxlength="30" disabled="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
									<html:text property="numeroSequencial" size="9" maxlength="9" disabled="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
									<strong>
									<a href="javascript:controleMovimentoArrecadador();"> 	
										<img width="23" height="21" src="<bean:message key="caminho.imagens"/>pesquisa.gif"
											 style="cursor:hand;" alt="Pesquisar" border="0"/></a> 
									<a href="javascript:limparMovimento();">
										<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" /></a>
									</strong>
								</td>
							</tr>
						<%if(session.getAttribute("tela") == null ){%>	
							<tr>
								<td><strong>Opção de Pagamento:</strong></td>
								<td colspan="3">
									<strong><html:radio property="opcaoPagamento" value="atual" />Atual</strong> 
			    					<strong><html:radio property="opcaoPagamento" value="historico" />Histórico</strong>
			    					<strong><html:radio property="opcaoPagamento" value="ambos" />Ambos</strong>
			    				</td>
							</tr>
						<%} else if ("estorno".equals(session.getAttribute("tela"))) {%>
							<tr>
								<td><strong>Opção de Pagamento:</strong></td>
								<td colspan="3">
			    					<strong><html:radio property="opcaoPagamento" value="historico"  />Histórico</strong>
			    				</td>
							</tr>
						<%}%>
						
						</table>		
					</td>
				</tr>
				<tr><td colspan="4" height="24"><hr></td></tr>
				<tr>
					<td colspan="4">
						<table width="100%" border="0">
							<tr>
								<td width="30%"><strong>Período Refer. Arrecadação:</strong></td>
								<td>
									<strong>
									<html:text maxlength="7" property="periodoArrecadacaoInicio" size="7"
										onkeyup="mascaraAnoMes(this, event); replicaDados(document.forms[0].periodoArrecadacaoInicio, document.forms[0].periodoArrecadacaoFim);" /> 
									<strong> a</strong>
									<html:text maxlength="7" property="periodoArrecadacaoFim" size="7" onkeyup="mascaraAnoMes(this, event);" />
									</strong> (mm/aaaa)
								</td>
							</tr>
							<tr>
								<td><strong>Período Refer. Faturamento:</strong></td>
								<td>
									<strong>
									<html:text maxlength="7" property="periodoPagamentoInicio" size="7"
										onkeyup="mascaraAnoMes(this, event); replicaDados(document.forms[0].periodoPagamentoInicio, document.forms[0].periodoPagamentoFim);" /> 
									<strong> a</strong> 
									<html:text maxlength="7" property="periodoPagamentoFim" size="7" onkeyup="mascaraAnoMes(this, event);" /> 
									</strong> (mm/aaaa)
								</td>
							</tr>
							<tr>
								<td><strong>Período de Pagamento:</strong></td>
								<td>
									<strong>
									<html:text maxlength="10" property="dataPagamentoInicio" size="10"
										onkeyup="mascaraData(this, event);  replicaDados(document.forms[0].dataPagamentoInicio, document.forms[0].dataPagamentoFim);" /> 
									<a href="javascript:abrirCalendarioReplicando('ConsultarPagamentoActionForm', 'dataPagamentoInicio','dataPagamentoFim');">
										<img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif"
											width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
									a</strong> 
									<html:text maxlength="10" property="dataPagamentoFim" size="10" onkeyup="mascaraData(this, event);" /> 
									<a href="javascript:abrirCalendario('ConsultarPagamentoActionForm', 'dataPagamentoFim')">
										<img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif"
											width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
									(dd/mm/aaaa)
								</td>
							</tr>
						</table>		
					</td>
				</tr>
				<tr><td colspan="4" height="24"><hr></td></tr>
				<tr>
					<td colspan="4">
						<table width="100%" border="0">
							<tr>
								<td width="30%"><strong>Situação do Pagamento:</strong></td>
								<td>
									<strong> 
									<html:select property="pagamentoSituacao" style="width: 230px;"
										multiple="mutiple" size="4">
									<logic:present name="colecaoPagamentoSituacao">
										<html:option value=""/>
										<html:options collection="colecaoPagamentoSituacao"
											labelProperty="descricao" property="id" />
									</logic:present>
									</html:select>
									</strong>
								</td>
							</tr>
							<tr>
								<td><strong>Forma de Arrecadação:</strong></td>
								<td>
									<strong>
									<html:select property="arrecadacaoForma" style="width: 410px;"
										multiple="mutiple" size="4">
									<logic:present name="colecaoArrecadacaoForma">
										<html:option value="" />
										<html:options collection="colecaoArrecadacaoForma"
											labelProperty="descricao" property="id" />
									</logic:present>
									</html:select>	
									</strong>
								</td>
							</tr>
							<tr>
								<td><strong>Tipo do Documento:</strong></td>
								<td>
									<strong> 
									<html:select property="documentoTipo" style="width: 230px;" 
										multiple="mutiple" size="4">
									<logic:present name="colecaoDocumentoTipo">
										<html:option value="" />
										<html:options collection="colecaoDocumentoTipo"
											labelProperty="descricaoDocumentoTipo" property="id" />
									</logic:present>
									</html:select>
									</strong>
								</td>
							</tr>
							<tr>
								<td><strong>Categoria:</strong></td>
								<td>
									<strong> 
										<html:select property="categoria" style="width: 230px; height: 84px" multiple="mutiple" size="4">
											<logic:present name="colecaoCategoria">
												<html:option value="" />
												<html:options collection="colecaoCategoria" labelProperty="descricao" property="id" />
											</logic:present>
										</html:select>
									</strong>
								</td>
							</tr>							
							<tr><td colspan="2"><strong>Tipo de Débito:</strong></td></tr>
							<tr>
								<td colspan="2">
									<table width="100%" border="0" bgcolor="#90c7fc" bordercolor="#79BBFD">
										<tr bgcolor="#99CCFF">
											<td width="47%" height="23"><strong>Tipos de Débito Disponíveis</strong></td>
											<td width="6%">&nbsp;&nbsp;&nbsp;&nbsp;</td>
											<td width="47%"><strong>Tipos de Débito Selecionados</strong></td>
										</tr>
										<tr bgcolor="#cbe5fe">
											<td align="center">
												<html:select property="debitoTipo" size="6" multiple="true" style="width:260px">
													<html:options collection="colecaoDebitoTipo" labelProperty="descricao" property="id" />
												</html:select>
											</td>
											<td align="center" width="5" valign="center">
												<table align="center">
													<tr>
														<td align="center">
															<input type="button" class="bottonRightCol"
																onClick="javascript:MoverTodosDadosSelectMenu1PARAMenu2('ConsultarPagamentoActionForm', 'debitoTipo', 'debitoTipoSelecionados');" value=" &gt;&gt; ">
														</td>
													</tr>
													<tr>
														<td align="center">
															<input type="button" class="bottonRightCol"
																onClick="javascript:MoverDadosSelectMenu1PARAMenu2('ConsultarPagamentoActionForm', 'debitoTipo', 'debitoTipoSelecionados');" value=" &nbsp;&gt;  ">
														</td>
													</tr>
													<tr>
														<td align="center">
															<input type="button" class="bottonRightCol"
																onClick="javascript:MoverDadosSelectMenu2PARAMenu1('ConsultarPagamentoActionForm', 'debitoTipo', 'debitoTipoSelecionados');" value=" &nbsp;&lt;  ">
														</td>
													</tr>
													<tr>
														<td align="center"><input type="button" class="bottonRightCol"
															onClick="javascript:MoverTodosDadosSelectMenu2PARAMenu1('ConsultarPagamentoActionForm', 'debitoTipo', 'debitoTipoSelecionados');" value=" &lt;&lt; ">
														</td>
													</tr>
												</table>
											</td>
											<td align="center">
												<html:select property="debitoTipoSelecionados" size="6" multiple="true" style="width:260px">
												</html:select>
											</td>		
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td width="130"><strong>Arrecadador:</strong></td>
								<td colspan="2"><html:text maxlength="4" property="codigoArrecadadorAuxiliar"
									tabindex="1" size="4" onkeyup="javascript:verificaNumeroInteiro(this);"
									onkeypress="javascript:validaEnterComMensagem(event, 'exibirFiltrarPagamentoAction.do', 'codigoArrecadadorAuxiliar', 'Arrecadador');" />
								<a
									href="javascript:abrirPopup('exibirPesquisarArrecadadorAction.do');">
								<img width="23" height="21" border="0"
									src="<bean:message key="caminho.imagens"/>pesquisa.gif"
									title="Pesquisar Arrecadador" /></a>

 			                    <logic:present name="arrecadadorInexistente" scope="request">
									<html:text maxlength="40" property="nomeArrecadadorAuxiliar" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000"
										size="40" />
								</logic:present>

				                <logic:notPresent name="arrecadadorInexistente"	scope="request">
									<html:text maxlength="30" property="nomeArrecadadorAuxiliar" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000"
										size="40" />
								</logic:notPresent>

			                    <a href="javascript:limparArrecadador();"> <img
								 src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								 border="0" title="Apagar" /></a></td>
							</tr>
							<tr>
                                <td align="right" colspan="2">
						            <input type="button" class="bottonRightCol" value="Adicionar" id="botaoArrecadador"
							            onclick="javascript:adicionarArrecadador();">
							    </td>
							</tr>

							<tr>
								<td colspan="3" height="70" valign="top">
								<table width="100%" cellpadding="0" cellspacing="0">
									<tr>
										<td>
										<table width="100%" border="0" bgcolor="#90c7fc">
											<tr bgcolor="#90c7fc" height="18">
												<td width="15%" align="center"><strong>Remover</strong></td>
												<td align="center"><strong>Arrecadador</strong></td>
											</tr>
										</table>
										</td>
									</tr>
									<logic:present name="colecaoArrecadadores">
										<tr>
											<td>
											<table width="100%" align="center" bgcolor="#99CCFF">
												<!--corpo da segunda tabela-->
												<%String cor = "#cbe5fe";%>
												<logic:iterate name="colecaoArrecadadores" id="arrecadador">
													<%if (cor.equalsIgnoreCase("#cbe5fe")) {
							                        cor = "#FFFFFF";%>
													<tr bgcolor="#FFFFFF" height="18">
													<%} else {
							                        cor = "#cbe5fe";%>
													<tr bgcolor="#cbe5fe" height="18">
													<%}%>
														<td width="15%" align="center"><img
															src="<bean:message key='caminho.imagens'/>Error.gif"
															width="14" height="14" style="cursor:hand;" alt="Remover"
															onclick="javascript:if(confirm('Confirma remoção?')){removerArrecadador(${arrecadador.codigoAgente});}"></td>
														<td><bean:write	name="arrecadador" property="codigoComNomeCliente" /></td>
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
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td><strong>Totalizar por Data de Pagamento?<font color="#FF0000">*</font></strong></td>
								<td><strong> <html:radio property="indicadorTotalizarPorDataPagamento" value="1" onchange="enviarTotalizador();" /> Sim
								<html:radio property="indicadorTotalizarPorDataPagamento" value="2" onchange="enviarTotalizador();" /> N&atilde;o
								</strong></td>
							</tr>
							
							<tr>
								<td><strong> <html:radio property="totalizadorMes" value="false" disabled="${indicadorTotalizarPorDataPagamento != 1}" /> Di&aacute;rio
								<html:radio property="totalizadorMes" value="true" disabled="${indicadorTotalizarPorDataPagamento != 1}" /> Mensal
								</strong></td>
							</tr>
							
							
							<tr>
								<td>&nbsp;</td>
							</tr>
						</table>		
					</td>
				</tr>

		        <tr>
		      		<td><strong> <font color="#FF0000"></font></strong></td>
		        		<td align="right">
		        			<div align="left"><strong><font color="#FF0000">*</font></strong>Campos obrigat&oacute;rios</div>
		        		</td>
		        	</tr>
				<tr>
					
					<%if(request.getAttribute("tela") != null || session.getAttribute("tela") != null ){ 
						session.removeAttribute("telaManter");
					%>		
						<td colspan="3">		
							<input value="Limpar" class="bottonRightCol" type="button" onclick="window.location.href='/gsan/exibirFiltrarPagamentoAction.do?menu=sim&tela=manterPagamento';">	
						</td>	
						<td align="right">
						  <gcom:controleAcessoBotao name="botaoFiltrar" value="Filtrar" onclick="validarForm(document.forms[0]);" url="consultarPagamentoAction.do"/>
						  <%-- <input value="Filtrar" class="bottonRightCol" type="button" onclick="validarForm(document.forms[0]);">--%>
						</td>						
					<%}else{ %>
						<td colspan="3">
							<input value="Limpar" class="bottonRightCol" type="button" onclick="javascript:window.location.href='/gsan/exibirFiltrarPagamentoAction.do?menu=sim'">
						</td>	
						<td align="right">
						<gcom:controleAcessoBotao name="botaoFiltrar" value="Filtrar"
							  onclick="javascript:if (document.forms[0].localidadeInicial.value != null 
							&& document.forms[0].localidadeInicial.value != ''){toggleBox('demodiv',1);} 
							else {validarForm(document.forms[0]);};" url="consultarPagamentoAction.do"/>
							
							<%-- <input value="Filtrar" class="bottonRightCol" type="button" 
							onclick="javascript:if (document.forms[0].localidadeInicial.value != null 
							&& document.forms[0].localidadeInicial.value != ''){toggleBox('demodiv',1);} 
							else {validarForm(document.forms[0]);};">--%>
						</td>
					<%} %>
				</tr>
				
				<tr>
				<!-- Fim do Corpo - Roberta Costa  11/01/2006  -->
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>
	<jsp:include
		page="/jsp/relatorio/escolher_tipo_relatorio.jsp?relatorio=consultarPagamentoAction.do" />
	<%@ include file="/jsp/util/rodape.jsp"%>
	<script language="JavaScript">
	<!--
		var form = document.ConsultarPagamentoActionForm;
		if(form.idImovel.value.length > 0) {
			controleImovel();
		}
		if(form.idCliente.value.length > 0) {
			controleCliente();
		}else{
			form.clienteRelacaoTipo.value = "";
			form.clienteRelacaoTipo.disabled = true;	
		}	
		if(form.localidadeInicial.value.length > 0) {
			controleLocalidadeInicial();
		}	
		if(form.localidadeFinal.value.length > 0) {
			controleLocalidadeFinal();
		}	
		
		controleCamposAvisoBancarioMovimentoArrecadador();
		
	-->
	</script>

</html:form>
</body>
</html:html>