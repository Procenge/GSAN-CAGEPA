<%@page import="java.util.Collection"%>
<%@page import="gcom.util.ConstantesSistema"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="gcom.faturamento.conta.Conta"%>
<%@page import="gcom.util.Util"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>


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
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
	
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="ManterContaConjuntoImovelActionForm" />

<script language="JavaScript">
function validaMesAnoVazio(form){
  if(form.mesAnoConta.value == ""){
		alert('Informe Mês/Ano');
		return false;
  }	
  return true;
}

function limparContas(form){
  form.quatidadeConta.value = "";
}

function validaForm(form){

	var retorno = false;
	
	if (validateManterContaConjuntoImovelActionForm(form) &&
		verificaAnoMesMensagemPersonalizada(form.mesAnoConta, "Mês e Ano da Conta inicial inválido") &&
		verificaAnoMesMensagemPersonalizada(form.mesAnoContaFinal, "Mês e Ano da Conta final inválido")){
	
		if (comparaMesAno(form.mesAnoConta.value, '>', form.mesAnoContaFinal.value)){
			form.mesAnoConta.focus();
			alert("Mês e Ano da Conta inicial maior que o Mês e Ano da Conta final");		
		}
		else if (form.dataVencimentoInicial.value.length > 0 &&
				 form.dataVencimentoFinal.value.length > 0 &&
				 comparaData(form.dataVencimentoInicial.value, '>', form.dataVencimentoFinal.value)){
			form.dataVencimentoInicial.focus();
			alert("Data Vencimento da Conta inicial maior que a Data Vencimento da Conta final");		
		}
		else{
			retorno = true;
		}
	}
	
	return retorno;
}

function pesquisarContas(form){
  
  limparMotivosDisponiveis()
  
  if(validaForm(form)){
    form.action = 'exibirManterContaConjuntoImovelAction.do?quantidadeConta=ok'
  	form.submit();
  }
  else{
  	return false;
  }
  
}

function cancelarConta(form){
  
	var mensagem = "Selecione as contas para efetuar a operação desejada.";

  	if(CheckboxNaoVazioMensagemGenerico(mensagem, "")){
    	
    	var urlCancelamentoConta = "/gsan/exibirCancelarConjuntoContaAction.do?mesAno="+ form.mesAnoConta.value;
    	urlCancelamentoConta = urlCancelamentoConta + "&mesAnoFim="+ form.mesAnoContaFinal.value;  
    	
//     	urlCancelamentoConta = urlCancelamentoConta + "&idsContasSelecionadas=" + obterValorCheckboxMarcado();  
    	
    	if (form.idGrupoFaturamento != null && form.idGrupoFaturamento.value.length > 0){
    		urlCancelamentoConta = urlCancelamentoConta + "&idGrupoFaturamento=" + form.idGrupoFaturamento.value;
    	}
    	
    	if (form.dataVencimentoInicial.value.length > 0){
    		urlCancelamentoConta = urlCancelamentoConta + "&dataVencimentoContaInicial=" + form.dataVencimentoInicial.value;
    		
    		if (form.dataVencimentoFinal.value.length > 0){
    			urlCancelamentoConta = urlCancelamentoConta + "&dataVencimentoContaFinal=" + form.dataVencimentoFinal.value;
    		}
    		else{
    			urlCancelamentoConta = urlCancelamentoConta + "&dataVencimentoContaFinal=" + form.dataVencimentoInicial.value;
    		}  
    	}
    	
    	abrirPopup(urlCancelamentoConta, 295, 450);  
  	
  	}
}

function retirarDebitoCobradoConta(form){
  
  if(validaForm(form)){
  
	var mensagem = "Selecione as contas para efetuar a operação desejada.";

	if(CheckboxNaoVazioMensagemGenerico(mensagem, "")){
    	
    	var urlCancelamentoConta = "/gsan/exibirRetirarDebitoCobradoConjuntoContaAction.do?mesAno="+ form.mesAnoConta.value;  
    	urlCancelamentoConta = urlCancelamentoConta + "&mesAnoFim="+ form.mesAnoContaFinal.value;
    	
//     	urlCancelamentoConta = urlCancelamentoConta + "&idsContasSelecionadas=" + obterValorCheckboxMarcado();  
    	
    	if (form.idGrupoFaturamento != null && form.idGrupoFaturamento.value.length > 0){
    		urlCancelamentoConta = urlCancelamentoConta + "&idGrupoFaturamento=" + form.idGrupoFaturamento.value;
    	}
    	
    	if (form.dataVencimentoInicial.value.length > 0){
    		urlCancelamentoConta = urlCancelamentoConta + "&dataVencimentoContaInicial=" + form.dataVencimentoInicial.value;
    		
    		if (form.dataVencimentoFinal.value.length > 0){
    			urlCancelamentoConta = urlCancelamentoConta + "&dataVencimentoContaFinal=" + form.dataVencimentoFinal.value;
    		}
    		else{
    			urlCancelamentoConta = urlCancelamentoConta + "&dataVencimentoContaFinal=" + form.dataVencimentoInicial.value;
    		}  
    	}
    	
    	urlCancelamentoConta = urlCancelamentoConta +"&primeiraVez=sim";
    	
    	abrirPopup(urlCancelamentoConta, 295, 450);  
  	
  	}
  }
}

function retirarValorAguaEsgotoConta(form){
	  
	  if(validaForm(form)){
	  
		var mensagem = "Selecione as contas para efetuar a operação desejada.";

		if(CheckboxNaoVazioMensagemGenerico(mensagem, "")){
	    	
	    	var urlCancelamentoConta = "/gsan/exibirRetirarValorAguaEsgotoConjuntoContaAction.do?mesAno="+ form.mesAnoConta.value;  
	    	urlCancelamentoConta = urlCancelamentoConta + "&mesAnoFim="+ form.mesAnoContaFinal.value;
	    	
//	     	urlCancelamentoConta = urlCancelamentoConta + "&idsContasSelecionadas=" + obterValorCheckboxMarcado();  
	    	
	    	if (form.idGrupoFaturamento != null && form.idGrupoFaturamento.value.length > 0){
	    		urlCancelamentoConta = urlCancelamentoConta + "&idGrupoFaturamento=" + form.idGrupoFaturamento.value;
	    	}
	    	
	    	if (form.dataVencimentoInicial.value.length > 0){
	    		urlCancelamentoConta = urlCancelamentoConta + "&dataVencimentoContaInicial=" + form.dataVencimentoInicial.value;
	    		
	    		if (form.dataVencimentoFinal.value.length > 0){
	    			urlCancelamentoConta = urlCancelamentoConta + "&dataVencimentoContaFinal=" + form.dataVencimentoFinal.value;
	    		}
	    		else{
	    			urlCancelamentoConta = urlCancelamentoConta + "&dataVencimentoContaFinal=" + form.dataVencimentoInicial.value;
	    		}  
	    	}
	    	
	    	abrirPopup(urlCancelamentoConta, 295, 450);  
	  	
	  	}
	  }
	}

function alterarVencimento(form){
  
  if(validaForm(form)){
  
	var mensagem = "Selecione as contas para efetuar a operação desejada.";

  	if(CheckboxNaoVazioMensagemGenerico(mensagem, "")){
    	
    	var urlCancelamentoConta = "/gsan/exibirAlterarVencimentoConjuntoContaAction.do?mesAno="+ form.mesAnoConta.value;
    	urlCancelamentoConta = urlCancelamentoConta + "&mesAnoFim="+ form.mesAnoContaFinal.value; 
    	
//     	urlCancelamentoConta = urlCancelamentoConta + "&idsContasSelecionadas=" + obterValorCheckboxMarcado();  
    	
    	if (form.idGrupoFaturamento != null && form.idGrupoFaturamento.value.length > 0){
    		urlCancelamentoConta = urlCancelamentoConta + "&idGrupoFaturamento=" + form.idGrupoFaturamento.value;
    	}
    	
    	if (form.dataVencimentoInicial.value.length > 0){
    		urlCancelamentoConta = urlCancelamentoConta + "&dataVencimentoContaInicial=" + form.dataVencimentoInicial.value;
    		
    		if (form.dataVencimentoFinal.value.length > 0){
    			urlCancelamentoConta = urlCancelamentoConta + "&dataVencimentoContaFinal=" + form.dataVencimentoFinal.value;
    		}
    		else{
    			urlCancelamentoConta = urlCancelamentoConta + "&dataVencimentoContaFinal=" + form.dataVencimentoInicial.value;
    		}  
    	}
    	
    	abrirPopup(urlCancelamentoConta, 295, 450);  
  	
  	}
  }
}

/* Replica Data de Vencimento */
function replicaDataVencimento() {
	var form = document.forms[0];
	form.dataVencimentoFinal.value = form.dataVencimentoInicial.value
}

function replicaAnoMes() {
	var form = document.forms[0];
	form.mesAnoContaFinal.value = form.mesAnoConta.value
}

function emitir2ViaConta(form){
  
  if(validaForm(form)){
  
	var mensagem = "Selecione as contas para efetuar a operação desejada.";

	if(CheckboxNaoVazioMensagemGenerico(mensagem, "")){
    	
    	var urlCancelamentoConta = "gerarRelatorio2ViaContaAction.do?mesAno="+ form.mesAnoConta.value+"&qtdeContas="+form.quatidadeConta.value;  
    	urlCancelamentoConta = urlCancelamentoConta + "&mesAnoFim="+ form.mesAnoContaFinal.value;  
    	
//     	urlCancelamentoConta = urlCancelamentoConta + "&idsContasSelecionadas=" + obterValorCheckboxMarcado();  
    	form.contaSelected.value = obterValorCheckboxMarcado();
    	
    	if (form.idGrupoFaturamento != null && form.idGrupoFaturamento.value.length > 0){
    		urlCancelamentoConta = urlCancelamentoConta + "&idGrupoFaturamento=" + form.idGrupoFaturamento.value;
    	}
    	
    	if (form.dataVencimentoInicial.value.length > 0){
    		urlCancelamentoConta = urlCancelamentoConta + "&dataVencimentoContaInicial=" + form.dataVencimentoInicial.value;
    		
    		if (form.dataVencimentoFinal.value.length > 0){
    			urlCancelamentoConta = urlCancelamentoConta + "&dataVencimentoContaFinal=" + form.dataVencimentoFinal.value;
    		}
    		else{
    			urlCancelamentoConta = urlCancelamentoConta + "&dataVencimentoContaFinal=" + form.dataVencimentoInicial.value;
    		}  
    	}
    	
    	urlCancelamentoConta += "&indicadorOperacao=manterConjuntoConta";
    	
    	form.action = urlCancelamentoConta;
  	 	form.submit();  
  	 	
  	
  	}
  }
}

function voltar(form){
  form.action = 'exibirFiltrarImovelInserirManterContaAction.do'
  form.submit();
}

function habilitarBotoes(){

	var form = document.forms[0];
	var idGrupoFaturamento = form.idGrupoFaturamento;
	
	if (idGrupoFaturamento != null && idGrupoFaturamento.value.length > 0){
		
		form.botaoCancelar.disabled = true;
		form.botaoRetirarDebitoCobrado.disabled = true;
	}
	else{
		
		form.botaoCancelar.disabled = false;
		form.botaoRetirarDebitoCobrado.disabled = false;
		form.botaoEmitirSegundaVia.disabled = false;
	}
	
}


function limparMotivosDisponiveis() {
    var i;
    var temSelecionado = 0;
    var select = document.forms[0].motivosRevisaoDisponiveis;
    
    for(i=0;i<select.options.length;i++) {

     	if (select.options[i].selected == true) {    
     		temSelecionado = 1;
			break;
        }
    }

    if (temSelecionado == 0) {
		select.selectedIndex = 0;
	}
}

function facilitador(objeto){
	if (objeto.id == "0" || objeto.id == undefined){
		objeto.id = "1";
		marcarTodos();
	}
	else{
		objeto.id = "0";
		desmarcarTodos();
	}
}

function emitir2ViaConta(form){
	  
	  if(validaForm(form)){
	  
		var mensagem = "Selecione as contas para efetuar a operação desejada.";

		if(CheckboxNaoVazioMensagemGenerico(mensagem, "")){
	    	
	    	var urlCancelamentoConta = "gerarRelatorio2ViaContaAction.do?mesAno="+ form.mesAnoConta.value+"&qtdeContas="+form.quatidadeConta.value;  
	    	urlCancelamentoConta = urlCancelamentoConta + "&mesAnoFim="+ form.mesAnoContaFinal.value;  
	    	  
	    	form.contaSelected.value = obterValorCheckboxMarcado();
	    	
	    	if (form.idGrupoFaturamento != null && form.idGrupoFaturamento.value.length > 0){
	    		urlCancelamentoConta = urlCancelamentoConta + "&idGrupoFaturamento=" + form.idGrupoFaturamento.value;
	    	}
	    	
	    	if (form.dataVencimentoInicial.value.length > 0){
	    		urlCancelamentoConta = urlCancelamentoConta + "&dataVencimentoContaInicial=" + form.dataVencimentoInicial.value;
	    		
	    		if (form.dataVencimentoFinal.value.length > 0){
	    			urlCancelamentoConta = urlCancelamentoConta + "&dataVencimentoContaFinal=" + form.dataVencimentoFinal.value;
	    		}
	    		else{
	    			urlCancelamentoConta = urlCancelamentoConta + "&dataVencimentoContaFinal=" + form.dataVencimentoInicial.value;
	    		}  
	    	}
	    	
	    	urlCancelamentoConta += "&indicadorOperacao=manterConjuntoConta";
	    	
	    	form.action = urlCancelamentoConta;
	  	 	form.submit();  
	  	 	
	  	
	  	}
	  }
	}
	
function retirarContaDeRevisao(form){

	var mensagem = "Selecione as contas para efetuar a operação desejada.";
	
	if (CheckboxNaoVazioMensagemGenerico(mensagem, "")){
		
		form.contaSelected.value = obterValorCheckboxMarcado();
		var urlRetirarRevisaoConta = "/gsan/retirarRevisaoConjuntoContaAction.do";  
		form.action = urlRetirarRevisaoConta;
		
		if (confirm("Confirma retirada de revisão?")){
			
			submeterFormPadrao(form);
		}
	}
}

function alterarClienteResponsavelContas(form){
	  
	var mensagem = "Selecione as contas para efetuar a operação desejada.";

  	if(CheckboxNaoVazioMensagemGenerico(mensagem, "")){
    	
    	var urlPopup = "/gsan/exibirAlterarClienteResponsavelConjuntoContaAction.do";  
    	abrirPopup(urlPopup, 295, 450);  
  	}
}
</script>
</head>

<logic:present name="reloadPage">
	<logic:equal name="reloadPage" value="grupo" >
		<body leftmargin="5" topmargin="5" onload="habilitarBotoes();">
	</logic:equal>
	<logic:equal name="reloadPage" value="OK">
		<body leftmargin="5" topmargin="5" onload="pesquisarContas(document.forms[0]);habilitarBotoes();">	
	</logic:equal>
</logic:present>

<logic:notPresent name="reloadPage">
	<body leftmargin="5" topmargin="5" onload="habilitarBotoes();">
</logic:notPresent>

<html:form action="/filtrarImovelInserirManterContaAction"
	name="ManterContaConjuntoImovelActionForm"
	type="gcom.gui.faturamento.conta.ManterContaConjuntoImovelActionForm"
	method="post"
	onsubmit="return validateManterContaConjuntoImovelActionForm(this);"
	focus="mesAnoConta">

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

			<td width="625" valign="top" class="centercoltext">

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
					<td class="parabg">Manter Contas de um Conjunto de Im&oacute;veis</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td>Para manter a(s) conta(s), informe o mês e o ano abaixo:</td>					
				</tr>
			</table>

			<table width="100%" border="0">
				<logic:notPresent name="cliente">				
				<tr>
					<td height="10" width="160"><strong>Inscrição inicial:</strong></td>
					<td width="403"><html:text property="inscricaoInicial" size="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/></td>													
					<td width="43">&nbsp;</td>
				</tr>
				<tr>
					<td height="10" width="160"><strong>Inscrição Final:</strong></td>
					<td width="403"><html:text property="inscricaoFinal" size="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/></td>													
					<td width="43">&nbsp;</td>
				</tr>
				<tr>
					<td height="10" width="160"><strong>Grupo de Faturamento:</strong></td>
					<td width="403"><html:text property="idGrupoFaturamento" size="5" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/></td>													
					<td width="43">&nbsp;</td>
				</tr>
				</logic:notPresent>
				<logic:present name="cliente">
				<tr>
					<td width="30%"><strong>Cliente:</strong></td>
					<td colspan="3"><html:text property="codigoCliente" maxlength="9"
						size="9" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"/> 
					<html:text property="nomeCliente" size="38" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
					</td>
				</tr>				
				</logic:present>
				<tr>
					<td height="10" width="160"><strong>Quantidade de Imóveis:</strong></td>
					<td width="403"><html:text property="quatidadeImovel" size="15" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/></td>													
					<td width="43">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="3" height="10"></td>
				</tr>
				<tr>
					<td colspan="3" height="10"></td>
				</tr>
				<tr>
					<td colspan="3" height="10"></td>
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
							<td height="10" width="160"><strong>Mês e Ano da Conta:<font
						    color="#FF0000">*</font></strong></td>
							<td width="403">
							
								<html:text property="mesAnoConta"  onkeyup="mascaraAnoMes(this, event);replicaAnoMes();limparContas(document.forms[0]);"  maxlength="7" size="7" />
							 	a 
							 	<html:text property="mesAnoContaFinal"  onkeyup="mascaraAnoMes(this, event);limparContas(document.forms[0]);"  maxlength="7" size="7" />MM/AAAA</td>																								
							
							<td width="43">&nbsp;</td>
						  </tr>
						  
						  <tr>
							<td height="10" width="160"><strong>Data Vencimento da Conta:</strong></td>
							<td width="403">
							
								<html:text property="dataVencimentoInicial" size="11" maxlength="10" onkeyup="mascaraData(this, event);replicaDataVencimento();limparContas(document.forms[0]);"/>
								<a href="javascript:abrirCalendario('ManterContaConjuntoImovelActionForm', 'dataVencimentoInicial');">
								<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="16" height="15" border="0" alt="Exibir Calendário"/></a>
						
								a <html:text property="dataVencimentoFinal" size="11" maxlength="10" onkeyup="mascaraData(this, event);limparContas(document.forms[0]);"/>
								<a href="javascript:abrirCalendario('ManterContaConjuntoImovelActionForm', 'dataVencimentoFinal');">
								<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="16" height="15" border="0" alt="Exibir Calendário"/></a>
							
							</td>																								
							<td width="43">&nbsp;</td>
						  </tr>
			              <tr>
							<td><strong>Selecionar apenas as contas em revisão?<font color="#FF0000">*</font></strong></td>
							<td>
								<html:radio property="inContasRevisao" value="1" /> <strong>Sim</strong>
								<html:radio property="inContasRevisao" value="2" /> <strong>Não</strong>
							</td>
						  </tr>
						  <tr>
							<td width="110"><strong>Motivo de Revisão:</strong></td>
							<td colspan="2">
								<table width="100%" border=0 cellpadding=0 cellspacing=0 align="left">
									<tr>
										<td width="175">
											<html:select property="motivosRevisaoDisponiveis" 
												size="4" 
												multiple="true" 
												style="width:350px">
											<logic:notEmpty name="colecaoContaMotivoRevisao">	
												<html:options collection="colecaoContaMotivoRevisao" 
													labelProperty="descricaoMotivoRevisaoConta" 
													property="id" />
											</logic:notEmpty>
											</html:select>
										</td>							
									</tr>
								</table>
							</td>
						  </tr>							  
						  <tr>
							<td colspan="3" height="10"></td>
						 </tr>
						  <tr>
							<td width="43">&nbsp;</td>
							<td align="right">
							<logic:empty scope="session" name="retornoArquivo"> 
							<gcom:controleAcessoBotao
								name="Button" value="Selecionar"
								onclick="javascript:pesquisarContas(document.forms[0]);"
								url="consultarDebitoAction.do" /> 
							</logic:empty>
							<logic:notEmpty scope="session" name="retornoArquivo">
							<input type="button"class="bottonRightCol" disabled="disabled"
								name="Button" value="Selecionar"
								onclick="javascript:pesquisarContas(document.forms[0]);"
								url="consultarDebitoAction.do" /> 
							</logic:notEmpty>
							</td>
							<td width="43">&nbsp;</td>
						  </tr>
						  <tr>
							<td colspan="3" height="10"></td>
						 </tr>
						 <tr>
							<td colspan="3"><hr></td>
						</tr>
						  <tr>
							<td height="10" width="160"><strong>Quantidade de Contas:</strong></td>
							<td width="403"><html:text property="quatidadeConta" size="7" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/></td>													
						  </tr>
						  <tr>
							<td colspan="3" height="10"></td>
						 </tr>	
						 
						<tr>
							<td colspan="3">
		
							<table width="100%" border="0">
								<tr>
									<td colspan="4">
									<table width="100%" cellpadding="0" cellspacing="0">
										<tr>
											<td>
											<table width="800" border="0" bgcolor="#90c7fc">
												<tr>
													<td bgcolor="#79bbfd" colspan="12" height="20">
														<strong>Contas do Imóvel</strong>
													</td>
												</tr>
												<logic:present name="colecaoContaImovel">
												<tr bgcolor="#90c7fc">
													
													<td width="30">
														<div align="center">	
														<strong><a href="javascript:facilitador(this);" id="0">Todos</a></strong>
														</div>
													</td>
													<td width="70">
														<div align="center">	
														<strong>Imóvel</strong>
														</div>
													</td>
													<td width="70">
														<div align="center">
														<strong>Referência</strong>
														</div>
													</td>
													<td width="50">
														<div align="center">
														<strong>Vencimento</strong>
														</div>
													</td>
													<td width="50">
														<div align="center">
														<strong>Valor Água</strong>
														</div>
													</td>
													<td width="50">
														<div align="center">
														<strong>Valor Esgoto</strong>
														</div>
													</td>
													<td width="50">
														<div align="center">
														<strong>Valor Débito</strong>
														</div>
													</td>
													<td width="50">
														<div align="center">
														<strong>Valor Crédito</strong>
														</div>
													</td>
													<td width="50">
														<div align="center">
														<strong>Valor Imposto</strong>
														</div>
													</td>
													<td width="50">
														<div align="center">
														<strong>Valor Conta</strong>
														</div>
													</td>
													<td width="50">
														<div align="center">
														<strong>Motivo Revisão</strong>
														</div>
													</td>
													<td width="70">
														<div align="center">
														<strong>Situação</strong>
														</div>
													</td>
		
												</tr>
												</logic:present>
											</table>
		
											</td>
										</tr>
		
										<logic:present name="colecaoContaImovel">
		
											<tr>
												<td>
												
												<% String cor = "#cbe5fe";%>
		
												<div style="width: 100%; height: 100; overflow: auto;">
												
												<table width="800" align="center" bgcolor="#90c7fc">
												
												<logic:iterate name="colecaoContaImovel" id="conta" type="Conta">
		
										
														<%	if (cor.equalsIgnoreCase("#cbe5fe")){
															cor = "#FFFFFF";%>
															<tr bgcolor="#FFFFFF">
														<%} else{
															cor = "#cbe5fe";%>
															<tr bgcolor="#cbe5fe">
														<%}%>
														
														<%
			  											 String data = "";
															 if(((Conta)conta).getUltimaAlteracao() != null){
																data = new Long(((Conta)conta).getUltimaAlteracao().getTime()).toString();	 
															 }	
														%>
																			
														<%
															String corSituacaoConta = "#000000"; // PRETO
															if(Util.isNaoNuloBrancoZero(((Conta)conta).getContaMotivoRevisao())){
																corSituacaoConta = "#ff0000"; // VERMELHO 
															} else if(Util.isNaoNuloBrancoZero(((Conta)conta).getIndicadorPagamento())){
																corSituacaoConta = "#0000FF"; // AZUL
															}else if(Util.isNaoNuloBrancoZero(((Conta)conta).getIndicadorCobrancaAdministrativa())
																						&& (((Conta)conta).getIndicadorCobrancaAdministrativa().equals(ConstantesSistema.INDICADOR_USO_ATIVO))) {
																corSituacaoConta = "#008200"; // VERDE
															}
														%>

															<td align="center" width="40">
																<div>		
																<INPUT type="checkbox" name="idsContasSelecionadas"	value="<bean:write name="conta" property="id" />">
																</div>	
															</td>
															
															<td align="center" width="80">
																<div>
																	<logic:present name="conta"	property="imovel">
																		<font color="<%=corSituacaoConta %>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																			<bean:write name="conta" property="imovel.id" />
																		</font>
																	</logic:present>
																	<logic:notPresent name="conta" property="imovel"> &nbsp; </logic:notPresent>
																</div>
															</td>
															
															<td align="center" width="80">
																<div>
																	<strong>
																		<a href="javascript:abrirPopup('exibirConsultarContaAction.do?contaID=<bean:write name="conta" property="id" />&tipoConsulta=conta', 600, 800);">
																			 <font color="<%=corSituacaoConta %>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																				<bean:write name="conta" property="formatarAnoMesParaMesAno" />
																			</font>
																		</a>
																	</strong>
																</div>
															</td>
															<td align="center" width="80">
																<div>
																	<logic:present name="conta"	property="dataVencimentoConta">
																		<font color="<%=corSituacaoConta %>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
																			<%="" + Util.formatarData(conta.getDataVencimentoConta())%>
																		</font>
																	</logic:present>
																	<logic:notPresent name="conta" property="dataVencimentoConta">&nbsp;</logic:notPresent>
																</div>
															</td>
															<td align="center" width="50">
																<div>
																	<font color="<%=corSituacaoConta %>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
																		<%="" + Util.formatarMoedaReal(conta.getValorAgua()).trim()%>
																	</font>
																</div>
															</td>
															<td align="center" width="50">
																<div>
																	<font color="<%=corSituacaoConta %>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
																		<%="" + Util.formatarMoedaReal(conta.getValorEsgoto()).trim()%>
																	</font>
																</div>
															</td>
															<td align="center" width="50">
																<div>
																	<logic:notEqual name="conta" property="debitos" value="0">
																		<a href="javascript:abrirPopup('exibirConsultarDebitoCobradoAction.do?contaID=<bean:write name="conta" property="id" />&tipoConsulta=conta', 600, 800);">
																			<font color="<%=corSituacaoConta %>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
																				<%="" + Util.formatarMoedaReal(conta.getDebitos()).trim()%>
																			</font>																	
																		</a>
																	</logic:notEqual>
																	<logic:equal name="conta" property="debitos" value="0">
																		<font color="<%=corSituacaoConta %>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
																			<%="" + Util.formatarMoedaReal(conta.getDebitos()).trim()%>
																		</font>																	
																	</logic:equal>
																</div>
															</td>
															<td align="center" width="50">
																<div>
																	<logic:notEqual name="conta" property="valorCreditos" value="0">
																		<a href="javascript:abrirPopup('exibirConsultarCreditoRealizadoAction.do?contaID=<bean:write name="conta" property="id" />&tipoConsulta=conta', 600, 800);">
																			<font color="<%=corSituacaoConta %>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
																				<%="" + Util.formatarMoedaReal(conta.getValorCreditos()).trim()%>
																			</font>
																		</a>
																	</logic:notEqual>

																	<logic:equal name="conta" property="valorCreditos" value="0">
																		<font color="<%=corSituacaoConta %>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
																			<%="" + Util.formatarMoedaReal(conta.getValorCreditos()).trim()%>
																		</font>
																	</logic:equal>
																</div>
															</td>
															<td align="center" width="50">
																<div>
																	<font color="<%=corSituacaoConta %>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
																		<%="" + Util.formatarMoedaReal(conta.getValorImposto()).trim()%>
																	</font>
																</div>
															</td>
															<td align="center" width="50">
																<div>
																	<font color="<%=corSituacaoConta %>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
																		<%="" + Util.formatarMoedaReal(new BigDecimal(conta.getValorTotalConta())).trim()%>
																	</font>
																</div>
															</td>
															<td align="center" width="70">
																<div>
																	<logic:present name="conta" property="contaMotivoRevisao">
																		<font color="<%=corSituacaoConta %>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
																			<bean:write name="conta" property="contaMotivoRevisao.descricaoMotivoRevisaoConta" />
																		</font>
																	</logic:present> 
																	<logic:notPresent name="conta" property="contaMotivoRevisao">&nbsp;</logic:notPresent>
																</div>
															</td>
															<td align="center" width="70">
																<div>												
																	<logic:present name="conta" property="debitoCreditoSituacaoAtual">
																		<font color="<%=corSituacaoConta %>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
																			<bean:write name="conta" property="debitoCreditoSituacaoAtual.descricaoDebitoCreditoSituacao" />
																		</font>
																	</logic:present> 
																	<logic:notPresent name="conta" property="debitoCreditoSituacaoAtual">&nbsp;</logic:notPresent>
																</div>
															</td>
														</tr>
												</logic:iterate>
												<!-- Inicio Ultima linha de TOTAL -->
												<tr>
												<%if (cor.equalsIgnoreCase("#cbe5fe")) {
													cor = "#FFFFFF";%>
													<tr bgcolor="#FFFFFF">
												<%} else {
													cor = "#cbe5fe";%>
													<tr bgcolor="#cbe5fe">
												<%}%>
									
													<td bgcolor="#90c7fc" align="center">
														<div align="center" class="style9">	
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																<strong>Total</strong>
															</font>
														</div>
													</td>
													<td align="left">
														<div align="center" class="style9">	
														<%=((Collection) session.getAttribute("colecaoContaImovel")).size()%> &nbsp; doc(s)
														</div>
													</td>
													<td bgcolor="#90c7fc" align="left">
														<div align="center" class="style9">	
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
															</font>
														</div>
													</td>
													<td bgcolor="#90c7fc" align="left">
														<div align="center" class="style9">	
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
															</font>
														</div>
													</td>																						
													<td align="right">
														<div align="center" class="style9">	
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																<%=session.getAttribute("valorAgua")%>
															</font>
														</div>
													</td>
													<td align="right">
														<div align="center" class="style9">	
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																<%=session.getAttribute("valorEsgoto")%>
															</font>
														</div>
													</td>
													
													<td align="right">
														<div align="center" class="style9">	
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																<%=session.getAttribute("valorDebito")%>
															</font>
														</div>
													</td>
													
													<td align="right">
														<div align="center" class="style9">	
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																<%=session.getAttribute("valorCredito")%>
															</font>
														</div>
													</td>
													
													<td align="right">
														<div align="center" class="style9">	
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																<%=session.getAttribute("valorImposto")%>
															</font>
														</div>
													</td>
													
													<td align="right">
														<div align="center" class="style9">	
															<strong>
																<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																	<%=session.getAttribute("valorConta")%>
																</font>
															</strong>
														</div>
													</td>
												</tr>

												</table>
												</div>
												</td>
											</tr>
										</logic:present>
										
										<tr>
											<td colspan="10">
												<div style="width: 100%; height: 100%;">
													<table width="100%">
														<tr>
															<td width="45%">
																<div align="right">&nbsp;</div> 	
															</td>
															<td width="12%">
																<div align="right">Legenda:</div>
															 </td>
															
															 <td width="33%">
																<div align="left"><font color="#FF0000"> Contas em revisão </font></div>
															</td>
														</tr>
														 <tr>
															 <td width="45%">
																<div align="right">&nbsp;</div>
															 </td>
															 <td width="12%">
																<div align="right">&nbsp;</div>
															 </td>
															 <td width="33%">
																<div align="left"><font color="#0000FF"> Contas pagas e não baixadas </font></div>
															</td>
														</tr>
														 <tr>
															 <td width="45%">
																<div align="right">&nbsp;</div>
															 </td>
															 <td width="12%">
																<div align="right">&nbsp;</div>
															 </td>
															 <td width="33%">
																<div align="left"><font color="#008200"> Contas em cobrança administrativa</font></div>
															</td>
														</tr>
													</table>
												</div>
											</td>
										</tr>


									</table>
									</td>
								</tr>
							</table>
							</td>
						</tr>
						</table>
					  </td>
                    </tr>
                  </table>
                  </td>
                </tr>						  
				<tr>
					<td align="center" colspan="3"><strong><font color="#FF0000">*</font></strong> Campos obrigat&oacute;rios</td>
				</tr>
				<tr>
					<td colspan="3" height="10"></td>
				</tr>
				<html:hidden property="contaSelected" value="" />
				<tr>
					<td colspan="3" height="5">
						
											
						<gcom:controleAcessoBotao name="Button" 
						value="Cancelar uma ou mais contas " 
						onclick="cancelarConta(document.forms[0]);" url="exibirCancelarConjuntoContaAction.do" style="width: 200px"/> 
						
						
						<gcom:controleAcessoBotao 
						name="Button" value="Retirar Débito Cobrado de uma ou mais contas "
						onclick="retirarDebitoCobradoConta(document.forms[0]);" url="exibirRetirarDebitoCobradoConjuntoContaAction.do" style="width: 280px"/> 		

						<!--<gcom:controleAcessoBotao 
						name="Button" value="Retirar Revisão"
						 onclick="" url="exibirManterContaAction.do" style="width: 100px"/>
						-->
						
						<gcom:controleAcessoBotao
						name="botaoAlterarVencimento" value="Alterar Vencimento de uma ou mais contas "
						onclick="alterarVencimento(document.forms[0]);" url="exibirAlterarVencimentoConjuntoContaAction.do" style="width: 280px"/>
						
						<logic:equal name="paramPermitirRetirarValorAguaEsgotoConta" value="<%="" + ConstantesSistema.SIM%>" scope="session">
							<gcom:controleAcessoBotao 
							name="botaoRetirarValorAguaEsgotoConta" value="Retirar Valor de Água e Esgoto de uma ou mais contas"
							onclick="retirarValorAguaEsgotoConta(document.forms[0]);" url="exibirRetirarValorAguaEsgotoConjuntoContaAction.do" style="width: 330px"/> 
						</logic:equal>
						
						<logic:present name="permitirAlterarClienteConta"  scope="session">
							<gcom:controleAcessoBotao 
							name="botaoAlterarClienteResponsavelContas" value="Alterar Cliente Responsável de uma ou mais contas"
							onclick="alterarClienteResponsavelContas(document.forms[0]);" url="exibirAlterarClienteResponsavelConjuntoContaAction.do" style="width: 330px"/> 
						</logic:present>
						
					</td>
				</tr>
				<tr>
					<td colspan="3" height="5">	
						<gcom:controleAcessoBotao
						name="Button" value="Emitir 2ª Via de Conta de uma ou mais contas "
						onclick="emitir2ViaConta(document.forms[0]);" url="gerarRelatorio2ViaContaAction.do" style="width: 280px"/>
						<gcom:controleAcessoBotao
						name="Button" value="Retirar de Revisão uma ou mais contas "
						onclick="retirarContaDeRevisao(document.forms[0]);" url="retirarRevisaoConjuntoContaAction.do" style="width: 280px"/>
					</td>
				</tr>
				<tr>
					<td valign="top" colspan="3">
						<input type="button" name="ButtonCancelar" class="bottonRightCol" value="Cancelar"
						onClick="javascript:document.forms[0].target='';window.location.href='/gsan/telaPrincipal.do'">
						<input type="button" name="ButtonCancelar" class="bottonRightCol" value="Voltar Filtro"
						onClick="javascript:voltar(document.forms[0]);">
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
