<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>

<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page import="gcom.util.ConstantesSistema"%>
<%@page import="gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao"%><html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false" formName="AtualizarAdicionarSolicitacaoEspecificacaoActionForm" />



<script language="JavaScript">


	function verificaDebito(obj) {
		if (obj.value == 2) {
			document.getElementById("divIndicadorTipoVerificarDebito").style.display = 'none';
		} else {
			document.getElementById("divIndicadorTipoVerificarDebito").style.display = 'block';
		}
	
	}	
    
    window.onmousemove = iniciarJsp;
	    
	function validaRadioButton(nomeCampo,mensagem){
		var alerta = "";
		if(!nomeCampo[0].checked && !nomeCampo[1].checked){
			alerta = mensagem +" deve ser selecionado.";
		}
		return alerta;
	}

	function validaRadioButton3(nomeCampo,mensagem){
		var alerta = "";
		if(!nomeCampo[0].checked && !nomeCampo[1].checked && !nomeCampo[2].checked){
			alerta = mensagem +" deve ser selecionado.";
		}
		return alerta;
	}

	function iniciarJsp(){
		var form = document.forms[0];
	
	}

	function validaTodosRadioButton(){
		var form = document.forms[0];
		var mensagem = "";
		if(validaRadioButton(form.indicadorPavimentoCalcada," Pavimento Calçada Obrigatória?") != ""){
				mensagem = mensagem + validaRadioButton(form.indicadorPavimentoCalcada," Pavimento Calçada Obrigatória?")+"\n";
		}
		if(validaRadioButton(form.indicadorPavimentoRua,"Pavimento Rua Obrigatória?") != ""){
			mensagem = mensagem + validaRadioButton(form.indicadorPavimentoRua," Pavimento Rua Obrigatória?")+"\n";
		}
		if(validaRadioButton(form.indicadorLigacaoAgua,"Refere-se a Ligação de Água?") != ""){
			mensagem = mensagem + validaRadioButton(form.indicadorLigacaoAgua,"Refere-se a Ligação de Água?")+"\n";
		}
		
		if(validaRadioButton(form.indicadorCobrancaMaterial," Cobrança de Material?") != ""){
			mensagem = mensagem + validaRadioButton(form.indicadorCobrancaMaterial," Cobrança de Material?")+"\n";
		}
		if(validaRadioButton(form.indicadorParecerEncerramento," Parecer de Encerramento Obrigatório?") != ""){
			mensagem = mensagem + validaRadioButton(form.indicadorParecerEncerramento," Parecer de Encerramento Obrigatório?")+"\n";
		}
		if(validaRadioButton(form.indicadorGerarDebito,"Gera Débito?") != ""){
			mensagem = mensagem + validaRadioButton(form.indicadorGerarDebito,"Gera Débito?")+"\n";
		}
		if(validaRadioButton(form.indicadorGerarCredito,"Gera Crédito?") != ""){
			mensagem = mensagem + validaRadioButton(form.indicadorGerarCredito,"Gera Crédito?")+"\n";
		}
		if(validaRadioButton(form.indicadorCliente,"Cliente Obrigatório?") != ""){
			mensagem = mensagem + validaRadioButton(form.indicadorCliente,"Cliente Obrigatório?")+"\n";
		}
		if(validaRadioButton(form.indicadorVerificarDebito,"Existe Verificação de Débito?") != ""){
			mensagem = mensagem + validaRadioButton(form.indicadorVerificarDebito,"Existe Verificação de Débito?")+"\n";
		} else {
			if(form.indicadorVerificarDebito[0].checked){
				if (validaRadioButton3(form.indicadorTipoVerificarDebito,"Informe se Imovel, Cliente ou Ambos") != "") {
					mensagem = mensagem + validaRadioButton(form.indicadorTipoVerificarDebito,"Informe se Imovel, Cliente ou Ambos")+"\n";
				}else{
					if(form.indicadorTipoVerificarDebito[1].checked || form.indicadorTipoVerificarDebito[2].checked){
						if (validaRadioButton3(form.indicadorConsiderarApenasDebitoTitularAtual,"Informe se Considerar apenas o Débito do Titular Atual do Imóvel") != "") {
							mensagem = mensagem + validaRadioButton(form.indicadorConsiderarApenasDebitoTitularAtual,"Informe Considerar apenas o Débito do Titular Atual do Imóvel")+"\n";
						}
					}
				}
			}
		}
		
		if(validaRadioButton(form.indicadorMatriculaImovel,"Matrícula do Imóvel Obrigatória?") != ""){
			mensagem = mensagem + validaRadioButton(form.indicadorMatriculaImovel,"Matrícula do Imóvel Obrigatória?")+"\n";
		}
		if(validaRadioButton(form.indicadorColetor,"Disponível para Coletor?") != ""){
			mensagem = mensagem + validaRadioButton(form.indicadorColetor,"Disponível para Coletor?")+"\n";
		}
	if(validaRadioButton(form.indicadorObrigatoriedadeRgRA,"Exige Preenchimento do RG?") != ""){
		mensagem = mensagem + validaRadioButton(form.indicadorObrigatoriedadeRgRA,"Exige Preenchimento do RG?")+"\n";
	}
	if(validaRadioButton(form.indicadorObrigatoriedadeCpfCnpjRA,"Exige Preenchimento do CPF/CNPJ?") != ""){
		mensagem = mensagem + validaRadioButton(form.indicadorObrigatoriedadeCpfCnpjRA,"Exige Preenchimento do CPF/CNPJ?")+"\n";
	}
	if(validaRadioButton(form.indicadorCalculoDataPrevistaAtendimento,"Data Prevista de Atendimento") != ""){
		mensagem = mensagem + validaRadioButton(form.indicadorCalculoDataPrevistaAtendimento,"Data Prevista de Atendimento")+"\n";
	}
	if(form.parmId.value == '2'){
		if(validaRadioButton(form.indicadorContaEmRevisao,"Coloca Contas em Revisão?") != ""){
			mensagem = mensagem + validaRadioButton(form.indicadorContaEmRevisao,"Coloca Contas em Revisão?")+"\n";
		}
		var contaEmRevisao = form.indicadorContaEmRevisao[0].checked;
		if(contaEmRevisao){
			if(validaRadioButton(form.indicadorMensagemAlertaRevisao,"Exibe Mensagem de Alerta?") != ""){
				mensagem = mensagem + validaRadioButton(form.indicadorMensagemAlertaRevisao,"Exibe Mensagem de Alerta?")+"\n";
			}
		}
	}
	 
		if(mensagem != ""){
			alert(mensagem);
			return false;
		}else{
			return true;
		}
	}

	function validarForm(form){
		if(validateAtualizarAdicionarSolicitacaoEspecificacaoActionForm(form) && validaTodosRadioButton()){
			if (form.idSolicitacaoTipoEspecificacaoMensagem.value == '') {
				form.descricaoSolicitacaoTipoEspecificacaoMensagem.value = '';
				form.descricaoSolicitacaoTipoEspecificacaoMensagem.title = '';
			}

		    form.action="atualizarAdicionarSolicitacaoEspecificacaoAction.do";
		    form.submit();
		}
	}

	function desfazer(){
		form = document.forms[0];
	 	form.action='exibirAtualizarAdicionarSolicitacaoEspecificacaoAction.do?limpaSessao=1';
	 	form.submit();
	}

	function desfazerAtualizar(){
		form = document.forms[0];
		form.action='exibirAtualizarAdicionarSolicitacaoEspecificacaoAction.do?desfazer=sim';
		form.submit();
	}

	function desabilitaCamposDebitoCredito(){
		var form = document.forms[0];
		//indicador igual a sim
		if(form.indicadorGerarDebito[0].checked){
			form.indicadorGerarCredito[0].disabled = true;
			form.indicadorGerarCredito[1].disabled = true;
			form.indicadorGerarCredito[1].checked = true;
		}
	}

	function desabilitaCamposDebitoCreditoOnClick(){
		var form = document.forms[0];
		//indicador igual a sim
		if(form.indicadorGerarDebito[0].checked){
			form.indicadorGerarCredito[0].disabled = true;
			form.indicadorGerarCredito[1].disabled = true;
			form.indicadorGerarCredito[1].checked = true;
		}else{
			form.indicadorGerarCredito[0].disabled = false;
			form.indicadorGerarCredito[1].disabled = false;
			form.indicadorGerarCredito[1].checked = false;
		
		}
	}

	function verificarCampoDesabilitado(campo,redirect,limparCampo){
	 	if(!campo.disabled){
	  		redirecionarSubmit(redirect);
	  		limparCampo = ''; 
	 	}
	}

function limparSolicitacaoTipoEspecificacaoMensagem(){
		var form = document.forms[0];
	form.idSolicitacaoTipoEspecificacaoMensagem.value= '';
	form.descricaoSolicitacaoTipoEspecificacaoMensagem.value= '';
	$('div[class=tooltip]').remove();
	}

	function desabilitaCamposMatriculaImovelOnClick(){
		var form = document.forms[0];
		//indicador igual a sim
		if(form.indicadorMatriculaImovel[0].checked){
			form.idSituacaoImovel.disabled = false;
		}else{
			form.idSituacaoImovel.disabled = true;
			form.idSituacaoImovel.value = '';
		}
	}
	
$(document).ready(function(){
	simple_tooltip("input[type=text][name=descricaoSolicitacaoTipoEspecificacaoMensagem][class=mensagemAutomatica]","tooltip", 1, 400, 400);
});

function verificarExibeMensagemAlerta(){
	var form = document.forms[0];
	var contaEmRevisao = form.indicadorContaEmRevisao[0].checked;
	if(contaEmRevisao){
		document.getElementById('exibeMensagemAlerta').style.visibility = 'visible';
	}else{
		document.getElementById('exibeMensagemAlerta').style.visibility = 'hidden';
	}	
}

function habilitarSelectComCor(radio, select, posicaoCursor){
	  
	   if(radio[0].checked){
		   
	     	select.disabled = false;
	     	select.className = '';
	     
	   }else{
	   	 	
		   	select.disabled = true;
	   	 	select.selectedIndex = posicaoCursor;
	   		select.className = 'desabilitar';
	   }
}

function verificaDebitoTitular(obj) {
	
	var form = document.forms[0];
	
	if (form.indicadorVerificarDebito[0].checked && (obj[1].checked || obj[2].checked)) {
		
		if (form.indicadorConsiderarApenasDebitoTitularAtual[0].checked){
			
			form.idClienteRelacaoTipo.className = '';
			form.idClienteRelacaoTipo.disabled = false;
		}
		
		document.getElementById("divIndicadorConsideraApenasDebitoAtualTitular").style.display = 'block';
		
	} else {
		
		form.idClienteRelacaoTipo.value = -1;
		form.idClienteRelacaoTipo.className = 'desabilitar';
		form.idClienteRelacaoTipo.disabled = true;
		form.indicadorConsiderarApenasDebitoTitularAtual[1].checked = true;
		document.getElementById("divIndicadorConsideraApenasDebitoAtualTitular").style.display = 'none';
	}

}
</script>

<style type="text/css">
.mensagemAutomatica {}
</style>

</head>
<logic:notPresent name="fecharPopup" scope="request">
	<body leftmargin="5" topmargin="5"
		onload="javascript:resizePageSemLink(652, 700);setarFoco('${requestScope.nomeCampo}');desabilitaCampos();desabilitaCamposDebitoCredito();desabilitaCamposMatriculaImovelOnClick();habilitarSelectComCor(document.forms[0].indicadorConsiderarApenasDebitoTitularAtual, document.forms[0].idClienteRelacaoTipo, 0);verificaDebitoTitular(document.forms[0].indicadorTipoVerificarDebito);">
</logic:notPresent>
<logic:present name="fecharPopup" scope="request">
	<body leftmargin="5" topmargin="5"
		onload="chamarReload('exibirAtualizarTipoSolicitacaoEspecificacaoAction.do');window.close();">
</logic:present>
<body leftmargin="5" topmargin="5"
	onload="javascript:resizePageSemLink(652, 700);setarFoco('${requestScope.nomeCampo}');desabilitaCampos();desabilitaCamposDebitoCredito();desabilitaCamposMatriculaImovelOnClick();habilitarSelectComCor(document.forms[0].indicadorConsiderarApenasDebitoTitularAtual, document.forms[0].idClienteRelacaoTipo, 0);verificaDebitoTitular(document.forms[0].indicadorTipoVerificarDebito);">





<html:form action="/atualizarAdicionarSolicitacaoEspecificacaoAction"
	name="AtualizarAdicionarSolicitacaoEspecificacaoActionForm"
	type="gcom.gui.atendimentopublico.registroatendimento.AtualizarAdicionarSolicitacaoEspecificacaoActionForm"
	method="post">


	<table width="615" border="0" cellspacing="5" cellpadding="0">
		<tr>
		<html:hidden name="sistemaParametro" property="parmId" />
			<td width="100%" valign="top" class="centercoltext">
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
					<td class="parabg"><html:hidden property="cabecalho" /> <bean:write
						name="AtualizarAdicionarSolicitacaoEspecificacaoActionForm"
						property="cabecalho" /> Especificação do Tipo da Solicitação</td>

					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
				<tr>
					<td height="5" colspan="3"></td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">
					<p>Preencha os campos para atualizar uma especificação para o tipo
					de solicitação:</p>
					<p>&nbsp;</p>
					</td>
				</tr>
				<tr>
					<td width="33%"><strong>Descrição:<font color="#FF0000">*</font></strong></td>
					<td><html:text property="descricaoSolicitacao" size="40"
						maxlength="50" tabindex="1" /></td>
				</tr>
				<tr>
					<td><strong> Prazo Previsão de Atendimento (em horas):</strong><strong><font
						color="#FF0000">*</font></strong></td>
					<td><html:text property="prazoPrevisaoAtendimento" size="4"
						maxlength="4" tabindex="2" onkeypress="return isCampoNumerico(event);" />&nbsp;horas</td>

				</tr>
				<tr>
					<td><strong>Data prevista de Atendimento:<font color="#FF0000">*</font></strong></td>
					<td>
					  <html:radio property="indicadorCalculoDataPrevistaAtendimento" value='<%= String.valueOf(SolicitacaoTipoEspecificacao.INDICADOR_CALCULO_DATA_PREVISTA_ATENDIMENTO_SIM) %>' tabindex="3"/> <strong>Úteis</strong>&nbsp; 
					  <html:radio property="indicadorCalculoDataPrevistaAtendimento" value='<%= String.valueOf(SolicitacaoTipoEspecificacao.INDICADOR_CALCULO_DATA_PREVISTA_ATENDIMENTO_NAO) %>' tabindex="3"/> <strong>Corridos</strong> 
					</td>
				</tr>
				<tr>
					<td><strong> Pavimento Calçada Obrigatória?:<font color="#FF0000">*</font></strong></td>
					<td><html:radio property="indicadorPavimentoCalcada" value="1"
						tabindex="3" /> <strong>Sim</strong>&nbsp; <html:radio
						property="indicadorPavimentoCalcada" value="2" tabindex="4" /> <strong>N&atilde;o</strong></td>
				</tr>
				<tr>
					<td><strong> Pavimento Rua Obrigatória?:<font color="#FF0000"></font><font
						color="#FF0000">*</font></strong></td>
					<td><html:radio property="indicadorPavimentoRua" value="1"
						tabindex="5" /> <strong>Sim</strong>&nbsp; <html:radio
						property="indicadorPavimentoRua" value="2" tabindex="5" /> <strong>N&atilde;o</strong>
					</td>
				</tr>
				<tr>
					<td><strong>Refere-se a Ligação de Água?:<font color="#FF0000"></font><font
						color="#FF0000">*</font></strong></td>
					<td><html:radio property="indicadorLigacaoAgua" value="1"
						tabindex="6" /> <strong>Sim</strong>&nbsp; <html:radio
						property="indicadorLigacaoAgua" value="2" tabindex="6" /> <strong>N&atilde;o</strong>
					</td>
				</tr>
				<tr>
					<td><strong> Cobrança de Material?:<font color="#FF0000"></font><font
						color="#FF0000">*</font></strong></td>
					<td><html:radio property="indicadorCobrancaMaterial" value="1"
						tabindex="7" /> <strong>Sim</strong>&nbsp; <html:radio
						property="indicadorCobrancaMaterial" value="2" tabindex="8" /> <strong>N&atilde;o</strong>
					</td>
				</tr>
				<tr>
					<td><strong>Parecer de Encerramento Obrigatório?:<font
						color="#FF0000"></font><font color="#FF0000">*</font></strong></td>
					<td><html:radio property="indicadorParecerEncerramento" value="1"
						tabindex="9" /> <strong>Sim</strong>&nbsp; <html:radio
						property="indicadorParecerEncerramento" value="2" tabindex="10" />
					<strong>N&atilde;o</strong></td>
				</tr>
				<tr>
					<td><strong>Gera Débito?:<font color="#FF0000">*</font></strong></td>
					<td><html:radio property="indicadorGerarDebito" value="1"
						tabindex="11" onclick="desabilitaCamposDebitoCreditoOnClick();" />
					<strong>Sim</strong>&nbsp; <html:radio
						property="indicadorGerarDebito" value="2" tabindex="11"
						onclick="desabilitaCamposDebitoCreditoOnClick();" /> <strong>N&atilde;o</strong>
					</td>
				</tr>
				<tr>
					<td><strong>Gera Crédito?:<font color="#FF0000">*</font></strong></td>

					<td><html:radio property="indicadorGerarCredito" value="1"
						tabindex="12" /> <strong>Sim</strong>&nbsp; <html:radio
						property="indicadorGerarCredito" value="2" tabindex="12" /> <strong>N&atilde;o</strong>
					</td>
				</tr>
				<tr>
					<td><strong>Gera Ordem de Serviço?:<font color="#FF0000">*</font></strong></td>

					<td><html:radio property="indicadorGerarOrdemServico" value="1"
						tabindex="12" /> <strong>Sim</strong>&nbsp; <html:radio
						property="indicadorGerarOrdemServico" value="2" tabindex="12" /> <strong>N&atilde;o</strong>
					</td>
				</tr>

				<tr>
					<td><strong>Cliente Obrigatório?:<font color="#FF0000">*</font></strong></td>

					<td><html:radio property="indicadorCliente" value="1" tabindex="13" />
					<strong>Sim</strong>&nbsp; <html:radio property="indicadorCliente"
						value="2" tabindex="13" /> <strong>N&atilde;o</strong></td>
				</tr>
				
				<tr>
					<td><strong>Existe Verificação de Débito?:<font color="#FF0000">*</font></strong></td>

					<td>
					    <html:radio property="indicadorVerificarDebito" value="1" tabindex="14" onclick="verificaDebito(this);verificaDebitoTitular(document.forms[0].indicadorTipoVerificarDebito);" />
					    <strong>Sim</strong>&nbsp; <html:radio property="indicadorVerificarDebito"
						 value="2" tabindex="14" onclick="verificaDebito(this);verificaDebitoTitular(document.forms[0].indicadorTipoVerificarDebito);"/> <strong>N&atilde;o</strong>
					</td>
				</tr>	
				<tr>
				<td></td>
				<td>
				<logic:equal name="AtualizarAdicionarSolicitacaoEspecificacaoActionForm" property="indicadorVerificarDebito" value="1">
				<div id="divIndicadorTipoVerificarDebito" style="display:block" >
					<table>						
						<tr>
							<td>
						    	<html:radio property="indicadorTipoVerificarDebito" value='<%= String.valueOf(SolicitacaoTipoEspecificacao.INDICADOR_DEBITOS_CLIENTES) %>' tabindex="14" onclick="verificaDebitoTitular(document.forms[0].indicadorTipoVerificarDebito);"/>
						    	<strong>Cliente</strong>&nbsp; <html:radio property="indicadorTipoVerificarDebito"
							 	value='<%= String.valueOf(SolicitacaoTipoEspecificacao.INDICADOR_DEBITOS_IMOVEIS) %>' tabindex="14" onclick="verificaDebitoTitular(document.forms[0].indicadorTipoVerificarDebito);"/> <strong>Imóvel</strong>
							 	<html:radio property="indicadorTipoVerificarDebito"
							 	value='<%= String.valueOf(SolicitacaoTipoEspecificacao.INDICADOR_DEBITOS_AMBOS) %>' tabindex="14" onclick="verificaDebitoTitular(document.forms[0].indicadorTipoVerificarDebito);"/> <strong>Ambos</strong>
							</td>
						</tr>	
					</table>				
				</div>
				</logic:equal>
				<logic:equal name="AtualizarAdicionarSolicitacaoEspecificacaoActionForm" property="indicadorVerificarDebito" value="2">
				<div id="divIndicadorTipoVerificarDebito" style="display:none" >
					<table>						
						<tr>
							<td>
						    	<html:radio property="indicadorTipoVerificarDebito" value='<%= String.valueOf(SolicitacaoTipoEspecificacao.INDICADOR_DEBITOS_CLIENTES) %>' tabindex="14" onclick="verificaDebitoTitular(document.forms[0].indicadorTipoVerificarDebito);"/>
						    	<strong>Cliente</strong>&nbsp; <html:radio property="indicadorTipoVerificarDebito"
							 	value='<%= String.valueOf(SolicitacaoTipoEspecificacao.INDICADOR_DEBITOS_IMOVEIS) %>' tabindex="14" onclick="verificaDebitoTitular(document.forms[0].indicadorTipoVerificarDebito);"/> <strong>Imóvel</strong>
							 	<html:radio property="indicadorTipoVerificarDebito"
							 	value='<%= String.valueOf(SolicitacaoTipoEspecificacao.INDICADOR_DEBITOS_AMBOS) %>' tabindex="14" onclick="verificaDebitoTitular(document.forms[0].indicadorTipoVerificarDebito);"/> <strong>Ambos</strong>
							</td>
						</tr>	
					</table>				
				</div>
				</logic:equal>
				<logic:equal name="AtualizarAdicionarSolicitacaoEspecificacaoActionForm" property="indicadorVerificarDebito" value="">
				<div id="divIndicadorTipoVerificarDebito" style="display:none" >
					<table>						
						<tr>
							<td>
						    	<html:radio property="indicadorTipoVerificarDebito" value='<%= String.valueOf(SolicitacaoTipoEspecificacao.INDICADOR_DEBITOS_CLIENTES) %>' tabindex="14" onclick="verificaDebitoTitular(document.forms[0].indicadorTipoVerificarDebito);"/>
						    	<strong>Cliente</strong>&nbsp; <html:radio property="indicadorTipoVerificarDebito"
							 	value='<%= String.valueOf(SolicitacaoTipoEspecificacao.INDICADOR_DEBITOS_IMOVEIS) %>' tabindex="14" onclick="verificaDebitoTitular(document.forms[0].indicadorTipoVerificarDebito);"/> <strong>Imóvel</strong>
							 	<html:radio property="indicadorTipoVerificarDebito"
							 	value='<%= String.valueOf(SolicitacaoTipoEspecificacao.INDICADOR_DEBITOS_AMBOS) %>' tabindex="14" onclick="verificaDebitoTitular(document.forms[0].indicadorTipoVerificarDebito);"/> <strong>Ambos</strong>
							</td>
						</tr>	
					</table>				
				</div>
				</logic:equal>
				<div id="divIndicadorConsideraApenasDebitoAtualTitular" style="display:block" >
					<table>						
						<tr> 
		                 	<td>
		                 		<strong>Consid. apenas o Débito do Titular Atual do Imóvel:<font color="#FF0000">*</font></strong>
		                 		<html:radio property="indicadorConsiderarApenasDebitoTitularAtual" value="<%=""+ConstantesSistema.SIM%>" onclick="javascript:habilitarSelectComCor(document.forms[0].indicadorConsiderarApenasDebitoTitularAtual, document.forms[0].idClienteRelacaoTipo, 0)"/><strong>Sim</strong>			    
		                  		<html:radio property="indicadorConsiderarApenasDebitoTitularAtual" value="<%=""+ConstantesSistema.NAO%>" onclick="javascript:habilitarSelectComCor(document.forms[0].indicadorConsiderarApenasDebitoTitularAtual, document.forms[0].idClienteRelacaoTipo, 0)"/><strong>N&atilde;o</strong>
		                 	</td> 
	                 	</tr>
			                  
	                  	<tr>
							<td>
								<strong>Titular Atual do Débito do Imóvel:</strong>
								<html:select property="idClienteRelacaoTipo">
	              					<html:options collection="colecaoClienteRelacaoTipo" labelProperty="descricao" property="id"/>
	             				</html:select> 
							</td>
	                 	</tr>	
					</table>				
				</div>
				</td> 	
				</tr>
				<tr>
					<td><strong>Matrícula do Imóvel Obrigatória?:<font color="#FF0000">*</font></strong></td>

					<td><html:radio property="indicadorMatriculaImovel" value="1" tabindex="15" onclick="desabilitaCamposMatriculaImovelOnClick();" />
					<strong>Sim</strong>&nbsp; 
					<html:radio property="indicadorMatriculaImovel" value="2" tabindex="15"	onclick="desabilitaCamposMatriculaImovelOnClick();" /> 
					<strong>N&atilde;o</strong>
					</td>
				</tr>
				<tr>
					<td><strong>Disponível para Coletor?:<font color="#FF0000">*</font></strong></td>
					<td>
					  <html:radio property="indicadorColetor" value="1" tabindex="16"/> <strong>Sim</strong>&nbsp; 
					  <html:radio property="indicadorColetor" value="2" tabindex="16"/> <strong>N&atilde;o</strong> 
					</td>
				</tr>	

				<tr>
					<td><strong>Exige Preenchimento do RG?:<font color="#FF0000">*</font></strong></td>
					<td>
					  <html:radio property="indicadorObrigatoriedadeRgRA" value="1" tabindex="17"/> <strong>Sim</strong>&nbsp; 
					  <html:radio property="indicadorObrigatoriedadeRgRA" value="2" tabindex="17"/> <strong>N&atilde;o</strong> 
					</td>
				</tr>
				<tr>
					<td><strong>Exige Preenchimento do CPF/CNPJ?:<font color="#FF0000">*</font></strong></td>
					<td>
					  <html:radio property="indicadorObrigatoriedadeCpfCnpjRA" value="1" tabindex="18"/> <strong>Sim</strong>&nbsp; 
					  <html:radio property="indicadorObrigatoriedadeCpfCnpjRA" value="2" tabindex="18"/> <strong>N&atilde;o</strong> 
					</td>
				</tr>
					
				<tr>
					<td><strong>Situação do Imóvel?:</strong></td>
					<td><html:select property="idSituacaoImovel" tabindex="19">
						<html:option value="">&nbsp;</html:option>
						<html:options collection="colecaoImovelSituacao"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>

				<tr>
					<td><strong>Mensagem Automática Padr&atilde;o:</strong></td>
					<td>
						<html:text maxlength="4" property="idSolicitacaoTipoEspecificacaoMensagem" size="4" tabindex="24"
							onkeypress="javascript:validaEnterComMensagem(event, 'exibirAtualizarAdicionarSolicitacaoEspecificacaoAction.do?objetoConsulta=1', 'idSolicitacaoTipoEspecificacaoMensagem', 'Mensagem Automática Padrão');"/>
						<a href="javascript:verificarCampoDesabilitado(document.forms[0].idSolicitacaoTipoEspecificacaoMensagem,'recuperarDadosPesquisarAtualizarAdicionarSolicitacaoEspecificacaoAction.do?caminhoRetornoTelaPesquisaMensagemTipoSolicitacaoEspecificacao=exibirAtualizarAdicionarSolicitacaoEspecificacaoAction', document.forms[0].descricaoSolicitacaoTipoEspecificacaoMensagem);">
							<img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" title="Mensagem Automática Padrão"/></a>

						<logic:present name="idSolicitacaoTipoEspecificacaoMensagemNaoEncontrado">
							<logic:equal name="idSolicitacaoTipoEspecificacaoMensagemNaoEncontrado" value="exception">
								<html:text property="descricaoSolicitacaoTipoEspecificacaoMensagem" size="40" maxlength="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
							</logic:equal>
							<logic:notEqual name="idSolicitacaoTipoEspecificacaoMensagemNaoEncontrado" value="exception">
								<html:text property="descricaoSolicitacaoTipoEspecificacaoMensagem" size="40" maxlength="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
							</logic:notEqual>
						</logic:present>

						<logic:notPresent name="idSolicitacaoTipoEspecificacaoMensagemNaoEncontrado">
							<logic:empty name="AtualizarAdicionarSolicitacaoEspecificacaoActionForm" property="idSolicitacaoTipoEspecificacaoMensagem">
								<html:text property="descricaoSolicitacaoTipoEspecificacaoMensagem" value="" size="40" maxlength="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
							</logic:empty>
							<logic:notEmpty name="AtualizarAdicionarSolicitacaoEspecificacaoActionForm"	property="idSolicitacaoTipoEspecificacaoMensagem">
								<html:text styleClass="mensagemAutomatica" property="descricaoSolicitacaoTipoEspecificacaoMensagem"	value="${sessionScope.AtualizarAdicionarSolicitacaoEspecificacaoActionForm.descricaoSolicitacaoTipoEspecificacaoMensagemTruncada}"
									size="40" maxlength="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"
									title="${sessionScope.AtualizarAdicionarSolicitacaoEspecificacaoActionForm.descricaoSolicitacaoTipoEspecificacaoMensagem}" />
							</logic:notEmpty>
						</logic:notPresent>

						<a href="javascript:limparSolicitacaoTipoEspecificacaoMensagem();">
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar"/>
						</a>
					</td>
				</tr>

				<tr>
					<td colspan="2">
					<hr>
					</td>
				</tr>

				<tr>
					<td><strong>Tipo do Serviço OS Possíveis</strong></td>
					<td align="right"><input type="button" name="adicionarTipoServico"
						class="bottonRightCol" value="Adicionar"
						onClick="javascript:redirecionarSubmit('exibirAtualizarAdicionarSolicitacaoEspecificacaoTipoServicoAction.do?retornarTelaPopup=atualizar&limpaSessao=S');"
						tabindex="25"></td>
				</tr>

				<tr>
					<td colspan="2">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr bordercolor="#000000">
							<td bgcolor="#90c7fc" align="center" width="15%" height="20">
							<div align="center"><strong>Remover</strong></div>
							</td>
							<td bgcolor="#90c7fc" width="45%"><strong>Tipo de Serviço</strong></td>
							<td bgcolor="#90c7fc" align="center" width="20%"><strong>Ordem de Execução</strong></td>
							<td bgcolor="#90c7fc" align="center" width="20%"><strong>Ordem de Serviço Automática</strong></td>
						</tr>
						<logic:present name="colecaoEspecificacaoServicoTipo">
							<tr>
								<td colspan="4">

								<div style="width: 100%; height: 100%; overflow: auto;">
								<table width="100%" bgcolor="#99CCFF">

									<%int cont = 0;%>
									<logic:iterate name="colecaoEspecificacaoServicoTipo"
										id="especificacaoServicoTipo">
										<%cont = cont + 1;
			if (cont % 2 == 0) {%>
										<tr bgcolor="#cbe5fe">
											<%} else {

			%>
										<tr bgcolor="#FFFFFF">
											<%}%>
											<td width="15%">
											<div align="center"><font color="#333333"> <img width="14"
												height="14" border="0"
												src="<bean:message key="caminho.imagens"/>Error.gif"
												onclick="javascript:if(confirm('Confirma remoção?')){redirecionarSubmit('removerSolicitacaoEspecificacaoAction.do?codigoSolicitacaoEspecificacao=<bean:write name="especificacaoServicoTipo" property="comp_id.idServicoTipo"/>&atualizar=sim');}" />
											</font></div>
											</td>
											<td width="45%"><bean:write name="especificacaoServicoTipo"
												property="servicoTipo.descricao" /></td>
											<td width="20%" align="center"><bean:write name="especificacaoServicoTipo"
												property="ordemExecucao" /></td>

											<logic:equal name="especificacaoServicoTipo" property="indicadorGeracaoAutomatica" value="1">
												<td width="20%" align="center">
													<input type="checkbox" name="indicadorGeracaoAutomatica<bean:write name="especificacaoServicoTipo" property="comp_id.idServicoTipo"/>" value="1" checked="checked"/>
												</td>
												<input type="hidden" name="indicadorGeracaoAutomatica<bean:write name="especificacaoServicoTipo" property="comp_id.idServicoTipo"/>" value="2">
											</logic:equal>

											<logic:notEqual name="especificacaoServicoTipo" property="indicadorGeracaoAutomatica" value="1">
												<td width="20%" align="center">
													<input type="checkbox" name="indicadorGeracaoAutomatica<bean:write name="especificacaoServicoTipo" property="comp_id.idServicoTipo"/>" value="1"/>
												</td>
												<input type="hidden" name="indicadorGeracaoAutomatica<bean:write name="especificacaoServicoTipo" property="comp_id.idServicoTipo"/>" value="2">
											</logic:notEqual>

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
				<logic:equal name="sistemaParametro" property="parmId" scope="session" value="<%=SistemaParametro.INDICADOR_EMPRESA_DESO.toString()%>">
					<tr>
						<td><strong>Coloca Contas em Revisão?:<font color="#FF0000">*</font></strong></td>
						<td>
						  <html:radio property="indicadorContaEmRevisao" value="1" tabindex="26" onclick="javascript:verificarExibeMensagemAlerta();"/> <strong>Sim</strong>&nbsp; 
						  <html:radio property="indicadorContaEmRevisao" value="2" tabindex="26" onclick="javascript:verificarExibeMensagemAlerta();"/> <strong>N&atilde;o</strong>
						</td>
					</tr>
					<logic:present name="AtualizarAdicionarSolicitacaoEspecificacaoActionForm" property="indicadorContaEmRevisao">
						<logic:equal name="AtualizarAdicionarSolicitacaoEspecificacaoActionForm" property="indicadorContaEmRevisao" value="<%=ConstantesSistema.SIM.toString()%>">
						<tr id="exibeMensagemAlerta" style="visibility: visible;">
					</logic:equal>
					<logic:equal name="AtualizarAdicionarSolicitacaoEspecificacaoActionForm" property="indicadorContaEmRevisao" value="<%=ConstantesSistema.NAO.toString()%>">
						<tr id="exibeMensagemAlerta" style="visibility: hidden;">
					</logic:equal>
					</logic:present>
					<logic:notPresent name="AtualizarAdicionarSolicitacaoEspecificacaoActionForm" property="indicadorContaEmRevisao">
						<tr id="exibeMensagemAlerta" style="visibility: hidden;">
					</logic:notPresent>
						<td><strong>Exibe Mensagem de Alerta?:<font color="#FF0000">*</font></strong></td>
						<td>
						  <html:radio property="indicadorMensagemAlertaRevisao" value="1" tabindex="27"/> <strong>Sim</strong>&nbsp; 
						  <html:radio property="indicadorMensagemAlertaRevisao" value="2" tabindex="27"/> <strong>N&atilde;o</strong> 
						</td>
					</tr>
				</logic:equal>
				<tr>
					<td colspan="2">
					<hr>
					</td>
				</tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
			</table>
			<table width="100%" border="0">

				<tr>
					<logic:present name="atualizar" scope="request">

						<td valign="top">
							<input name="button" type="button"
								class="bottonRightCol" value="Desfazer"
								onclick="desfazerAtualizar();">&nbsp;
							<input type="button"
								name="ButtonCancelar" class="bottonRightCol" value="Fechar"
								onClick="javascript:window.close();"></td>

						<td valign="top">
						<div align="right">
							<input name="Button" type="button"
								class="bottonRightCol" value="Atualizar"
								onclick="validarForm(document.forms[0]);" tabindex="28"></div>
						</td>
					</logic:present>
					
					<logic:notPresent name="atualizar" scope="request">
						<td valign="top">
							<input name="button" type="button"
								class="bottonRightCol" value="Desfazer" onclick="desfazer();">&nbsp;
							<input
								type="button" name="ButtonCancelar" class="bottonRightCol"
								value="Fechar" onClick="javascript:window.close();"></td>

						<td valign="top">
						<div align="right">
							<input name="Button" type="button"
								class="bottonRightCol" value="Inserir"
								onclick="validarForm(document.forms[0]);" tabindex="28"></div>
						</td>
					</logic:notPresent>
				</tr>



				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
</body>
</html:html>
