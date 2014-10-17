<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gcom.util.ConstantesSistema"%>
<head>
<html:html>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<html:javascript staticJavascript="false"  formName="InserirComandoAcaoCobrancaEventualCriterioRotaActionForm" />
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="InserirComandoAcaoCobrancaEventualCriterioRotaActionForm" 
	dynamicJavascript="false" 
	/>
	
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/engine.js'> </script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/util.js'> </script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/interface/AjaxService.js'> </script>
	
<script language="JavaScript">
<!-- Begin

function verificarExtendeTabela(){
	var form = document.forms[0];

	if(form.indicadorDocumentos[0].checked == true){
		eval('layerHideCriteriosComando').style.display = 'none';
		eval('layerShowCriteriosComando').style.display = 'block';

		eval('layerHideOutrosCriterios').style.display = 'block';
		eval('layerShowOutrosCriterios').style.display = 'none';

		//habilitar Critérios do Comando
		form.cobrancaAcaoAtividadeComando.disabled = false;
		
		form.sequencialInicialDocumentos.disabled = false;
		
		form.sequencialFinalDocumentos.disabled = false;
		
		form.localidadeInicial.disabled = false;
		
		form.localidadeFinal.disabled = false;
		
		form.empresaCriterioPeloComando.disabled = false;
		
		//desabilitar Outros critérios
		form.cobrancaAcao.disabled = true;
		
		form.empresa.disabled = true;
		
		form.imovel.disabled = true;
		
		form.dataInicialGeracao.disabled = true;
		
		form.dataFinalGeracao.disabled = true;
		
	}else{

		eval('layerHideOutrosCriterios').style.display = 'none';
		eval('layerShowOutrosCriterios').style.display = 'block';

		eval('layerHideCriteriosComando').style.display = 'block';
		eval('layerShowCriteriosComando').style.display = 'none';

		//desabilitar Critérios do Comando
		form.cobrancaAcaoAtividadeComando.disabled = true;
		
		form.sequencialInicialDocumentos.disabled = true;
		
		form.sequencialFinalDocumentos.disabled = true;
		
		form.localidadeInicial.disabled = true;
		
		form.localidadeFinal.disabled = true;
		
		form.empresaCriterioPeloComando.disabled = true;

		//desabilitar Outros critérios
		form.cobrancaAcao.disabled = false;
		
		form.empresa.disabled = false;
		
		form.imovel.disabled = false;
		
		form.dataInicialGeracao.disabled = false;
		
		form.dataFinalGeracao.disabled = false;
		
	}
	
}

function extendeTabela(tabela,display){
	var form = document.forms[0];

	if(display){
			eval('layerHide'+tabela).style.display = 'none';
			eval('layerShow'+tabela).style.display = 'block';
	}else{
		eval('layerHide'+tabela).style.display = 'block';
			eval('layerShow'+tabela).style.display = 'none';
	}
}

function limparOrigem(tipo){
	var form = document.forms[0];
	

	switch(tipo){
		case 1: //De localidade pra baixo
			
			form.localidadeInicial.value = "";
			form.descricaoLocalidadeInicial.value = "";

			form.localidadeFinal.value = "";
			form.descricaoLocalidadeFinal.value = "";

			form.setorComercialInicial.disabled = true;
			form.setorComercialFinal.disabled = true;

			form.setorComercialInicial.value = "";
			form.descricaoSetorComercialInicial.value = "";
			
			form.setorComercialFinal.value = "";
			form.descricaoSetorComercialFinal.value = "";
			
			form.setorComercialInicialCodigo.value = "";
			form.setorComercialFinalCodigo.value = "";

			form.quadraInicial.disabled = true;
			form.quadraFinal.disabled = true;

			form.quadraInicial.value = "";
			form.quadraFinal.value = "";

			break;
			
		case 2: //De setor para baixo

			
			form.setorComercialInicial.value = "";
			form.descricaoSetorComercialInicial.value = "";
			
			form.setorComercialFinal.value = "";
			form.descricaoSetorComercialFinal.value = "";

			form.setorComercialInicialCodigo.value = "";
			form.setorComercialFinalCodigo.value = "";

			form.quadraInicial.disabled = false;
			form.quadraFinal.disabled = false;

			form.quadraInicial.value = "";
			form.quadraFinal.value = "";

			break;

		case 3:
			form.quadraInicial.value = "";
			form.quadraFinal.value = "";

			break;
	}
}

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

	verificarExtendeTabela();

	var form = document.forms[0];
	if (tipoConsulta == 'cobrancaAcaoAtividadeComando') {
  		form.cobrancaAcaoAtividadeComando.value = codigoRegistro;
  		form.cobrancaAcaoAtividadeComandoDescricao.value = descricaoRegistro;
  		form.cobrancaAcaoAtividadeComandoDescricao.style.color = "#000000";
  		
	}else if(form.tipoLocalidade.value == 'I'){
		if (tipoConsulta == 'localidade') {
	  		form.localidadeInicial.value = codigoRegistro;
	  		form.descricaoLocalidadeInicial.value = descricaoRegistro;
	  		form.descricaoLocalidadeInicial.style.color = "#000000";
	  		if(form.localidadeFinal.value == ""){
	      		form.localidadeFinal.value = codigoRegistro;
		  		form.descricaoLocalidadeFinal.value = descricaoRegistro;
		  		form.descricaoLocalidadeFinal.style.color = "#000000";
	  		}
	  		form.setorComercialInicialCodigo.disabled = false;
	  		form.setorComercialFinalCodigo.disabled = false;
		}
	} else if(form.tipoLocalidade.value == 'F'){
		if (tipoConsulta == 'localidade') {
      		form.localidadeFinal.value = codigoRegistro;
	  		form.descricaoLocalidadeFinal.value = descricaoRegistro;
	  		form.descricaoLocalidadeFinal.style.color = "#000000";
	}
		
	}else if (tipoConsulta == 'imovel') {
  		form.imovel.value = codigoRegistro;
  		form.inscricaoImovel.value = descricaoRegistro;
	  	form.inscricaoImovel.style.color = "#000000";
	}

}

function recuperarDadosQuatroParametros(idRegistro, descricaoRegistro, codigoRegistro, tipoConsulta) {

	var form = document.forms[0];

	if (tipoConsulta == 'setorComercialOrigem') {
  		form.setorComercialInicial.value = idRegistro;
  		form.descricaoSetorComercialInicial.value = descricaoRegistro;
  		form.descricaoSetorComercialInicial.style.color = "#000000";

  		form.setorComercialInicialCodigo.value = codigoRegistro;
  		if(form.setorComercialFinal.value == ""){
      		form.setorComercialFinal.value = idRegistro;
	  		form.descricaoSetorComercialFinal.value = descricaoRegistro;
	  		form.setorComercialFinalCodigo.value = codigoRegistro;
	  		form.descricaoSetorComercialFinal.style.color = "#000000";
  		}

  		form.quadraInicial.disabled = false;
  		form.quadraFinal.disabled = false;
  		
	}else if (tipoConsulta == 'setorComercialDestino') {
  		form.setorComercialFinal.value = idRegistro;
  		form.descricaoSetorComercialFinal.value = descricaoRegistro;
  		form.setorComercialFinalCodigo.value = codigoRegistro;
	  	form.descricaoSetorComercialFinal.style.color = "#000000";
	}else if (tipoConsulta == 'quadraOrigem') {
  		form.quadraInicial.value = idRegistro;
  		if(form.quadraFinal.value == ""){
      		form.quadraFinal.value = idRegistro;
  		}
	}else if (tipoConsulta == 'quadraDestino') {
  		form.quadraFinal.value = codigoRegistro;
	}
	
}

function pesquisarQuadra(destino){
	   var form = document.forms[0];
	   var msg = '';

	   if(form.localidadeInicial.value == ""){
		   msg = 'Informe Localidade Inicial.\n';
	   }
	   if(form.localidadeFinal.value == ""){
		   msg = msg+ 'Informe Localidade Final.\n';
	   }
	   if(form.setorComercialInicialCodigo.value == ""){
		   msg = msg +'Informe Setor Comercial Inicial.\n';
	   }
	   if(form.setorComercialFinalCodigo.value == ""){
		   msg = msg+ 'Informe Setor Comercial Final.\n';
	   }

	   if( msg != '' ){
		   alert(msg);
	   }else{
			var msgDois = '';
			
		   if(form.localidadeInicial.value != form.localidadeFinal.value){
			   msgDois = 'Localidades diferentes.\n';
		   }
		   if(form.setorComercialInicialCodigo.value != form.setorComercialFinalCodigo.value){
			   msgDois = msgDois + 'Setores Comeriais diferentes.\n';
		   }
		   if( msgDois != '' ){
			   alert(msgDois);
			}else{
				abrirPopup('exibirPesquisarQuadraAction.do?idLocalidade='+form.localidadeInicial.value+'&codigoSetorComercial='+form.setorComercialInicialCodigo.value+'&tipo='+destino, 250, 495);
			}
		}
	}

function habilitarQuadra(){
	var form = document.forms[0];

	if(form.setorComercialFinalCodigo.value == form.setorComercialInicialCodigo.value){
	   form.quadraInicial.value = "";
 	   form.quadraFinal.value = "";
	   form.quadraInicial.disabled = false;
 	   form.quadraFinal.disabled = false;	
	}else{	
	   form.quadraInicial.value = "";
 	   form.quadraFinal.value = "";
	   form.quadraInicial.disabled = true;
   	   form.quadraFinal.disabled = true;	

   	   if(form.setorComercialFinalCodigo.value == ''){
   		form.descricaoSetorComercialFinal.value = '';
   	   }

	}
}

function replicarLocalidade(){
	var form = document.forms[0];
	if(form.localidadeFinal.value == ""){
		form.localidadeFinal.value = form.localidadeInicial.value;	
	}
	
}

function replicarSetorComercial(){
	var form = document.forms[0];
	if(form.setorComercialFinalCodigo.value == ""){
		form.setorComercialFinalCodigo.value = form.setorComercialInicialCodigo.value;
	}	
}

function replicarQuadra(){
	var form = document.forms[0];
	if(form.quadraFinal.value == ""){
		form.quadraFinal.value = form.quadraInicial.value;
	}	
}

function limparComando(){

	document.forms[0].cobrancaAcaoAtividadeComando.value = "";
	document.forms[0].cobrancaAcaoAtividadeComandoDescricao.value = "";
}

function limparImovel(){

	document.forms[0].imovel.value = "";
	document.forms[0].inscricaoImovel.value = "";
}

function limparDestino(tipo){

	var form = document.forms[0];

	switch(tipo){
		case 1: //De localidade pra baixo
			if(form.localidadeFinal.value != ''){
				form.localidadeFinal.value = '';
				form.descricaoLocalidadeFinal.value = '';		
				form.setorComercialInicialCodigo.value = '';
				form.descricaoSetorComercialInicial.value = '';
			    form.setorComercialFinalCodigo.value = '';
			    form.descricaoSetorComercialFinal.value = '';
			    form.quadraInicial.value = '';
			    form.quadraFinal.value = '';

			    habilitaDesabilitaSetorComercialQuadra();
			}
			
		case 2: //De setor para baixo
			if(form.setorComercialFinalCodigo.value != ''){
				
		    form.setorComercialFinalCodigo.value = '';
		    form.descricaoSetorComercialFinal.value = '';	
		    form.quadraInicial.value = '';
		    form.quadraFinal.value = '';

			}
		case 3:
			form.quadraFinal.value = "";
	}
}

function habilitaDesabilitaSetorComercialQuadra(){

	if(document.forms[0].localidadeInicial.value != ''){
		
		document.forms[0].setorComercialInicialCodigo.disabled = false;
		document.forms[0].setorComercialFinalCodigo.disabled = false;
		
	}else{
		document.forms[0].setorComercialInicialCodigo.disabled = true;
		document.forms[0].setorComercialInicialCodigo.value = '';
		document.forms[0].descricaoSetorComercialInicial.value = '';
		
		document.forms[0].setorComercialFinalCodigo.disabled = true;
		document.forms[0].setorComercialFinalCodigo.value = '';
		document.forms[0].descricaoSetorComercialFinal.value = '';

		document.forms[0].localidadeFinal.value = '';

		if(document.forms[0].descricaoLocalidadeInicial.value != "LOCALIDADE INEXISTENTE"){
			document.forms[0].descricaoLocalidadeInicial.value = '';	
		}
		
		if(document.forms[0].descricaoLocalidadeFinal.value != "LOCALIDADE INEXISTENTE"){
			document.forms[0].descricaoLocalidadeFinal.value = '';
		}
		
	}

	if(document.forms[0].setorComercialInicialCodigo.value != ''){
		
		document.forms[0].quadraInicial.disabled = false;
		document.forms[0].quadraFinal.disabled = false;
		
	}else{

		document.forms[0].setorComercialInicial.value = '';
		document.forms[0].setorComercialInicialCodigo.value = '';

		if(document.forms[0].descricaoSetorComercialInicial.value != "SETOR COMERCIAL INEXISTENTE"){
			document.forms[0].descricaoSetorComercialInicial.value = '';
		}

		document.forms[0].setorComercialFinal.value = '';
		document.forms[0].setorComercialFinalCodigo.value = '';

		if(document.forms[0].descricaoSetorComercialFinal.value != "SETOR COMERCIAL INEXISTENTE"){
			document.forms[0].descricaoSetorComercialFinal.value = '';
		}
		
		document.forms[0].quadraInicial.disabled = true;
		document.forms[0].quadraInicial.value = '';
		
		document.forms[0].quadraFinal.disabled = true;
		document.forms[0].quadraFinal.value = '';
	}
	
}

function verificaImovelHabilitado(){

	 var form = document.forms[0];
	 
		if(form.imovel.disabled == false){
			abrirPopup('exibirPesquisarImovelAction.do', 400, 800);
		}
}

function verificaCobrancaAcaoAtividadeHabilitado(){

	 var form = document.forms[0];
	 
		if(form.cobrancaAcaoAtividadeComando.disabled == false){
			abrirPopup('exibirPesquisarComandoAcaoCobrancaAction.do?limparForm=OK', 400, 750);
		}
}

function verificaLocalidadeInicialHabilitado(){

	 var form = document.forms[0];
	 
		if(form.localidadeInicial.disabled == false){
			abrirPopup('exibirPesquisarLocalidadeAction.do?indicadorUsoTodos=1', 'origem', null, null, 285, 480, '',document.forms[0].localidadeInicial);
		}
}

function verificaLocalidadeFinalHabilitado(){

	 var form = document.forms[0];
	 
		if(form.localidadeFinal.disabled == false){
			abrirPopup('exibirPesquisarLocalidadeAction.do?indicadorUsoTodos=1', 'destino', null, null, 275, 480, '',document.forms[0].localidadeFinal);
		}
}

function verificarPeriodoGeracaoHabilitado(){

	 var form = document.forms[0];
	 
		if(form.dataInicialGeracao.disabled == false && form.dataFinalGeracao.disabled == false){
			abrirCalendario('FiltrarEntregaDocumentoCobrancaActionForm', 'dataInicialGeracao');
			abrirCalendario('FiltrarEntregaDocumentoCobrancaActionForm', 'dataFinalGeracao');
		}
}

function chamarFiltro(){

	var form = document.forms[0];

	if(form.indicadorDocumentos[0].checked == true){
		if(form.cobrancaAcaoAtividadeComando.value == null ||
				form.cobrancaAcaoAtividadeComando.value == "" ){
				alert("Informe o Comando");
				return false;
		}
	}else if(form.indicadorDocumentos[1].checked == true){
		if(form.cobrancaAcao.value == null ||
				form.cobrancaAcao.value == "" ){
				alert("Informe a Ação Cobrança");
				return false;
		}
	}
	
	submeterFormPadrao(form);
	return true;
}

function verificarDataEntrega(data){

	if(data.value != null && data.value != ''){
		return verificaData(data);
	}else{
		return true;
	}
}

function desabilitaIntervaloDiferente(tipo){
	var form = document.forms[0];
	
	switch(tipo){
		case 1: //De localidade 
		    if(form.localidadeFinal.value != form.localidadeInicial.value){

		        form.setorComercialInicial.value = "";
			 	form.setorComercialFinal.value = "";
			 	
				form.setorComercialInicialCodigo.value = "";
				form.setorComercialFinalCodigo.value = "";
				
			 	form.quadraInicial.value = "";
				form.quadraFinal.value = "";
				form.quadraInicial.disabled = true;
				form.quadraFinal.disabled = true;
			    form.setorComercialInicialCodigo.disabled = true;
			 	form.setorComercialFinalCodigo.disabled = true;
			 
			  }
			break;					
		case 2: //De setor Comercial		   
		    if(form.setorComercialFinalCodigo.value != form.setorComercialInicialCodigo.value){
			    
				 form.quadraInicial.disabled = true;
				 form.quadraFinal.disabled = true;

				 form.quadraInicial.value = "";
	             form.quadraFinal.value = "";
  			  }
			break;
		case 3://De Quadra 

		     if(form.quadraFinal.value != form.quadraInicial.value){

			  }

			break;
		}
	
}

function replicarCampoSeVazioVerificarDigitos(campoFinal, campoInicial){

	if (campoFinal.value == "") {
		campoFinal.value = campoInicial.value;
	}else{
		segundoDigitoFinal = campoFinal.value.substring(1,2);
		terceiroDigitoFinal = campoFinal.value.substring(2,3);

		if(segundoDigitoFinal == ""){
			campoFinal.value = campoInicial.value;
		}else if(terceiroDigitoFinal == ""){
			campoFinal.value = campoInicial.value;
		}
		
	}
	
}

-->
</script>



</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:verificarExtendeTabela();habilitaDesabilitaSetorComercialQuadra();mensagem('${requestScope.mensagem}');setarFoco('${requestScope.nomeCampo}');">

<html:form action="/filtrarEntregaDocumentoCobrancaAction"
   name="FiltrarEntregaDocumentoCobrancaActionForm"
   type="gcom.gui.cobranca.FiltrarEntregaDocumentoCobrancaActionForm"
   method="post"
   onsubmit="return validateFiltrarEntregaDocumentoCobrancaActionForm(this);" enctype="multipart/form-data">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>
	
	<input type="hidden" name="tipoLocalidade" />
	
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


			<td valign="top" class="centercoltext"><!--Início Tabela Reference a Páginação da Tela de Processo-->
			<!--Início Tabela Reference a Páginação da Tela de Processo-->
			<table>
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">Filtrar Documentos de Cobrança 
					para Informar Entrega</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>
				</tr>
			</table>
			<!--Fim Tabela Reference a Páginação da Tela de Processo-->
			<p>&nbsp;</p>
			<table width="100%" border="0"  cellpadding="0" cellspacing="3">
				<tr>
					<td colspan="4">Para selecionar os documentos de cobrança para informar entrega, informe os dados abaixo:</td>
				</tr>
				<tr>
					<td width="30%"><strong>Selecionar Documentos:</strong></td>
					<td colspan="3"><html:radio property="indicadorDocumentos" value="1" tabindex="12" onclick="javascript:verificarExtendeTabela();"/> 
					                  <strong>Pelo Comando  
					                <html:radio	property="indicadorDocumentos" value="2" tabindex="13" onclick="javascript:verificarExtendeTabela();"/> 
					                Por Outros Critérios</strong>
					</td>
				</tr>
				
			
				<tr>
					<td width="16%"><strong>Ordenação dos Documentos:</strong></td>
					<td colspan="3"><html:radio property="indicadorOrdenacaoDocumentos" value="1" tabindex="12"/> 
					                  <strong>Pela Inscrição  
					                <html:radio	property="indicadorOrdenacaoDocumentos" value="2" tabindex="13"/> 
					                Pelo Sequencial</strong>
					</td>
				</tr>
				
			<tr>
					<td colspan="4">
					<div id="layerHideCriteriosComando" style="display:block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								 /> <b>Critérios do Comando</b> </a> </span></td>
						</tr>
					</table>
					</div>
					

					<div id="layerShowCriteriosComando" style="display:none">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								/> <b>Critérios do Comando</b> </a> </span></td>
						</tr>

						<tr>
							<td colspan="9">
							<table width="100%" bgcolor="#99CCFF">

				<tr>

					<td><strong>Comando:</strong></td>
					<td width="81%" align="right" colspan="2">
					<div align="left">
						<html:text maxlength="5"
							property="cobrancaAcaoAtividadeComando" 
							size="3" 
							tabindex="17"
							onkeypress="validaEnterComMensagem(event, 'exibirFiltrarEntregaDocumentoCobrancaAction.do', 'cobrancaAcaoAtividadeComando', 'Comando');"
							onkeyup="javaScript:verificaNumeroInteiro(this);"/> 
							
						<a href="javascript:verificaCobrancaAcaoAtividadeHabilitado();">
                       		<img border="0" 
                       			src="<bean:message key="caminho.imagens"/>pesquisa.gif" 
                       			border="0"/></a>

						<strong> 
						<logic:present name="idCobrancaAcaoAtividadeComandoNaoEncontrado">
							<logic:equal name="idCobrancaAcaoAtividadeComandoNaoEncontrado" value="exception">
								<html:text property="cobrancaAcaoAtividadeComandoDescricao" 
									size="40"
									maxlength="40" 
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000" 
									/>
							</logic:equal>

							<logic:notEqual name="idCobrancaAcaoAtividadeComandoNaoEncontrado" value="exception">
								<html:text property="cobrancaAcaoAtividadeComandoDescricao" 
									size="40"
									maxlength="40" 
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" 
									/>
							</logic:notEqual>

						</logic:present> 
						
						<logic:notPresent name="idCobrancaAcaoAtividadeComandoNaoEncontrado">
							<logic:empty name="FiltrarEntregaDocumentoCobrancaActionForm"
								property="cobrancaAcaoAtividadeComando">
							
							<html:text property="cobrancaAcaoAtividadeComandoDescricao" 
								value="" 
								size="40"
								maxlength="40" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" 
								/>
							</logic:empty>
							
							<logic:notEmpty name="FiltrarEntregaDocumentoCobrancaActionForm"
								property="cobrancaAcaoAtividadeComando">
								
								<html:text property="cobrancaAcaoAtividadeComandoDescricao" 
									size="40"
									maxlength="40" 
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: 	#000000" 
									/>
							</logic:notEmpty>
						</logic:notPresent> 
						
						<a href="javascript:limparComando();"> <img name="imagem"
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a>  					
						</strong></div>
					</td>

				</tr>
				
				<tr>
					<td width="25%"><strong>Empresa:</strong></td>
					<td colspan="2">
						<html:select name="FiltrarEntregaDocumentoCobrancaActionForm" 
									 property="empresaCriterioPeloComando" tabindex="7">
						  <html:option value="">&nbsp;</html:option>
						  <logic:present scope="session" name="colecaoEmpresa">
							<html:options name="session" collection="colecaoEmpresa"
										  labelProperty="descricao" property="id" />
						  </logic:present>			
						</html:select>
					</td>
				</tr>
				
				<tr>
			    <td><strong>Sequencial dos Documentos:</strong></td>
			    <td>
			      <strong> 
			        <html:text tabindex="6" property="sequencialInicialDocumentos" size="10" maxlength="10" onkeyup="javaScript:verificaNumeroInteiro(this);"/> 
				      a
			      </strong> 
			      
			      <html:text tabindex="7" property="sequencialFinalDocumentos" size="10" maxlength="10" onkeyup="javaScript:verificaNumeroInteiro(this);"/> 
			    </td>
			  </tr>	
				
				<tr>
					<td colspan="4">
					<hr>
					</td>
				</tr>
				
				<tr>

					<td><strong>Localidade Inicial:</strong></td>
					<td width="81%" align="right" colspan="2">
					<div align="left">
						<html:text maxlength="3"
							property="localidadeInicial" 
							size="3" 
							tabindex="17"
							onkeypress="validaEnterComMensagem(event, 'exibirFiltrarEntregaDocumentoCobrancaAction.do', 'localidadeInicial', 'Localidade Inicial');"
							onkeyup="javaScript:verificaNumeroInteiro(this);replicarCampoSeVazioVerificarDigitos(document.forms[0].localidadeFinal,this);"
							onblur="javascript:habilitaDesabilitaSetorComercialQuadra();desabilitaIntervaloDiferente(1);"/> 
							
						<a href="javascript:document.forms[0].tipoLocalidade.value='I';verificaLocalidadeInicialHabilitado();">
                       		<img border="0" 
                       			src="<bean:message key="caminho.imagens"/>pesquisa.gif" 
                       			border="0"/></a>

						<strong> 
						<logic:present name="idLocalidadeInicialNaoEncontrado">
							<logic:equal name="idLocalidadeInicialNaoEncontrado" value="exception">
								<html:text property="descricaoLocalidadeInicial" 
									size="40"
									maxlength="40" 
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000" 
									/>
							</logic:equal>

							<logic:notEqual name="idLocalidadeInicialNaoEncontrado" value="exception">
								<html:text property="descricaoLocalidadeInicial" 
									size="40"
									maxlength="40" 
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" 
									/>
							</logic:notEqual>

						</logic:present> 
						
						<logic:notPresent name="idLocalidadeInicialNaoEncontrado">
							<logic:empty name="FiltrarEntregaDocumentoCobrancaActionForm"
								property="localidadeInicial">
							
							<html:text property="descricaoLocalidadeInicial" 
								value="" 
								size="40"
								maxlength="40" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" 
								/>
							</logic:empty>
							
							<logic:notEmpty name="FiltrarEntregaDocumentoCobrancaActionForm"
								property="localidadeInicial">
								
								<html:text property="descricaoLocalidadeInicial" 
									size="40"
									maxlength="40" 
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: 	#000000" 
									/>
							</logic:notEmpty>
						</logic:notPresent> 
						
						<a href="javascript:limparOrigem(1);"> <img name="imagem"
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a>  					
						</strong></div>
					</td>

				</tr>

				<tr>
					<td><strong>Setor Comercial Inicial: </strong></td>
					<td align="right" colspan="2">
					<div align="left"><html:text maxlength="3" tabindex="18" 
						property="setorComercialInicialCodigo" size="3" 
						onblur="habilitaDesabilitaSetorComercialQuadra();desabilitaIntervaloDiferente(2);"
						onkeyup="javascript:verificaNumeroInteiro(this);replicarCampoSeVazioVerificarDigitos(document.forms[0].setorComercialFinalCodigo,this);"
						onkeypress="validaEnterDependenciaComMensagem(event, 'exibirFiltrarEntregaDocumentoCobrancaAction.do', document.forms[0].setorComercialInicialCodigo, document.forms[0].localidadeInicial.value, 'Localidade Inicial', 'Setor Comercial Inicial');"
						 /> 
						<a href="javascript:abrirPopupDependencia('exibirPesquisarSetorComercialAction.do?idLocalidade='+document.forms[0].localidadeInicial.value+'&tipo=setorComercialOrigem&indicadorUsoTodos=1',document.forms[0].localidadeInicial.value,'Localidade da inscrição de origem', 400, 800);">
                         <img border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/></a>
					<logic:present name="idSetorComercialInicialNaoEncontrado">
						<logic:equal name="idSetorComercialInicialNaoEncontrado" value="exception">
							<html:text property="descricaoSetorComercialInicial" size="40"
								maxlength="40" readonly="true" tabindex="18"
								style="background-color:#EFEFEF; border:0; color: #ff0000" 
								/>
						</logic:equal>

						<logic:notEqual name="idSetorComercialInicialNaoEncontrado" value="exception">
							<html:text property="descricaoSetorComercialInicial" size="40"
								maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" 
								/>
						</logic:notEqual>
					</logic:present> <logic:notPresent name="idSetorComercialInicialNaoEncontrado">
						<logic:empty
							name="FiltrarEntregaDocumentoCobrancaActionForm"
							property="setorComercialInicialCodigo">
							<html:text property="descricaoSetorComercialInicial" value="" size="40"
								maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" 
								/>
						</logic:empty>
						<logic:notEmpty
							name="FiltrarEntregaDocumentoCobrancaActionForm"
							property="setorComercialInicialCodigo">
							<html:text property="descricaoSetorComercialInicial" size="40"
								maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" 
								/>
						</logic:notEmpty>
						
					</logic:notPresent> 
						<a href="javascript:limparOrigem(2);"> <img name="imagem"
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a>  
						<html:hidden property="setorComercialInicial" />										
					</div>
					</td>
				</tr>
				<tr>
					<td><strong>Quadra Inicial:<font color="#FF0000"> </font></strong></td>
					<td align="left" colspan="3">
						<html:text maxlength="5" tabindex="19" 
						property="quadraInicial" size="4"
						name="FiltrarEntregaDocumentoCobrancaActionForm"
						onkeyup="javaScript:verificaNumeroInteiro(this);replicarCampoSeVazioVerificarDigitos(document.forms[0].quadraFinal,this);"
						onblur="desabilitaIntervaloDiferente(3);"
						/> 
						<a href="javascript:pesquisarQuadra('quadraOrigem');">
						<img width="23" height="21" border="0"         
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Quadra" /></a>	
						
						<a href="javascript:limparOrigem(3);"> <img name="imagem"
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a>  					
					</td>
				</tr>

				<tr>
					<td colspan="4">
					<hr>
					</td>
				</tr>


				<tr>
					<td><strong>Localidade Final:<font color="#FF0000"> </font></strong></td>
					<td width="37%" align="left" colspan="2"><html:text maxlength="3"
						property="localidadeFinal" size="3" tabindex="20"
						onkeyup="verificaNumeroInteiro(this);"
						onkeypress="validaEnterComMensagem(event, 'exibirFiltrarEntregaDocumentoCobrancaAction.do', 'localidadeFinal', 'Localidade Final');" 
						onblur="javascript:replicarLocalidade();desabilitaIntervaloDiferente(1);"/>
						
						<a href="javascript:document.forms[0].tipoLocalidade.value='F';verificaLocalidadeFinalHabilitado();">
                         <img border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/></a>
						<logic:present name="idLocalidadeFinalNaoEncontrado">

						<logic:equal name="idLocalidadeFinalNaoEncontrado" value="exception">
							<html:text property="descricaoLocalidadeFinal" size="40"
								maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" 
								/>
						</logic:equal>

						<logic:notEqual name="idLocalidadeFinalNaoEncontrado" value="exception">
							<html:text property="descricaoLocalidadeFinal" size="40"
								maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" 
								/>
						</logic:notEqual>

					</logic:present> <logic:notPresent name="idLocalidadeFinalNaoEncontrado">

						<logic:empty
							name="FiltrarEntregaDocumentoCobrancaActionForm"
							property="localidadeFinal">
							<html:text property="descricaoLocalidadeFinal" value="" size="40"
								maxlength="40" readonly="true" tabindex="20"
								style="background-color:#EFEFEF; border:0; color: #ff0000" 
								/>
						</logic:empty>
						<logic:notEmpty
							name="FiltrarEntregaDocumentoCobrancaActionForm"
							property="localidadeFinal">
							<html:text property="descricaoLocalidadeFinal" size="40"
								maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: 	#000000" 
								/>
						</logic:notEmpty>
					</logic:notPresent>
						<a href="javascript:limparDestino(1);"> <img name="imagem"
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a>  					
					</td>
				</tr>

				<tr>
					<td><strong>Setor Comercial Final: </strong></td>
					<td align="left" colspan="2"><html:text maxlength="3" 
						property="setorComercialFinalCodigo" size="3" tabindex="21"
						onkeyup="verificaNumeroInteiro(this);habilitarQuadra();"
						onkeypress="validaEnterComMensagem(event, 'exibirFiltrarEntregaDocumentoCobrancaAction.do', 'setorComercialFinalCodigo', 'Setor Comercial Final');"
						onblur="desabilitaIntervaloDiferente(2);"/> 
						 <a href="javascript:abrirPopupDependencia('exibirPesquisarSetorComercialAction.do?idLocalidade='+document.forms[0].localidadeInicial.value+'&tipo=setorComercialDestino&indicadorUsoTodos=1',document.forms[0].localidadeInicial.value,'Localidade da inscrição de origem', 400, 800);">
                         <img border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/></a>
						 <logic:present name="idSetorComercialFinalNaoEncontrado">

						<logic:equal name="idSetorComercialFinalNaoEncontrado" value="exception">
							<html:text property="descricaoSetorComercialFinal" size="40"
								maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000"
								 />
						</logic:equal>

						<logic:notEqual name="idSetorComercialFinalNaoEncontrado" value="exception">
							<html:text property="descricaoSetorComercialFinal" size="40"
								maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" 
								/>
						</logic:notEqual>

					</logic:present> <logic:notPresent name="idSetorComercialFinalNaoEncontrado">

						<logic:empty
							name="FiltrarEntregaDocumentoCobrancaActionForm"
							property="setorComercialFinal">
							<html:text property="descricaoSetorComercialFinal" size="40"
								maxlength="40" readonly="true" tabindex="21"
								style="background-color:#EFEFEF; border:0; color: #ff0000" 
								/>
						</logic:empty>
						<logic:notEmpty
							name="FiltrarEntregaDocumentoCobrancaActionForm"
							property="setorComercialFinal">
							<html:text property="descricaoSetorComercialFinal" size="40"
								maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" 
								/>
						</logic:notEmpty>

					</logic:notPresent> 
						<a href="javascript:limparDestino(2);"> <img name="imagem"
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a>  
						<html:hidden property="setorComercialFinal" />
					</td>
				</tr>
				<tr>
					<td><strong>Quadra Final:<font color="#FF0000"> </font></strong></td>
					<td align="right" colspan="3">
						<div align="left">
					<html:text maxlength="5" tabindex="22" 
						property="quadraFinal" size="4"
						name="FiltrarEntregaDocumentoCobrancaActionForm"
						onkeyup="javaScript:verificaNumeroInteiro(this);"
						onblur="desabilitaIntervaloDiferente(3);"/> 
						
						<a href="javascript:pesquisarQuadra('quadraDestino');">
						<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Rota" /></a>
						
						<a href="javascript:limparDestino(3);"> <img name="imagem"
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a> 
					</td>
				</tr>
				</table>
				</td>
			</tr>

		</table>
		</div>
		</td>
	</tr>
			
			
			<tr>
					<td colspan="4">
					<div id="layerHideOutrosCriterios" style="display:block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								 /> <b>Outros Critérios</b> </a> </span></td>
						</tr>
					</table>
					</div>
					

					<div id="layerShowOutrosCriterios" style="display:none">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								/> <b>Outros Critérios</b> </a> </span></td>
						</tr>

						<tr>
							<td colspan="9">
							<table width="100%" bgcolor="#99CCFF">

				<tr>
					<td width="16%"><strong>A&ccedil;&atilde;o de Cobran&ccedil;a:</strong></td>
					<td colspan="3"><html:select property="cobrancaAcao" style="height: 100px" multiple="true" tabindex="1" onchange="habilitaDesabilitaPrecedente();validaArrecadador();">
						<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present scope="session" name="collCobrancaAcao">
							<html:options name="session" collection="collCobrancaAcao"
								labelProperty="descricaoCobrancaAcao" property="id" />
						</logic:present>
					</html:select>
					</td>
				</tr>
				
				<tr>
					<td width="25%"><strong>Empresa:</strong></td>
					<td colspan="2">
						<html:select name="FiltrarEntregaDocumentoCobrancaActionForm" 
									 property="empresa" tabindex="7">
						  <html:option value="">&nbsp;</html:option>
						  <logic:present scope="session" name="colecaoEmpresa">
							<html:options name="session" collection="colecaoEmpresa"
										  labelProperty="descricao" property="id" />
						  </logic:present>			
						</html:select>
					</td>
				</tr>
				
				<tr>
					<td height="10" width="160"><strong>Matrícula do Imóvel:</strong></td>
					<td width="403"><html:text property="imovel" maxlength="9" tabindex="4"
						size="9" onkeyup="javascript:verificaNumeroInteiro(this);" 
						onkeypress="validaEnterComMensagem(event, 'exibirFiltrarEntregaDocumentoCobrancaAction.do', 'imovel', 'Matrícula do Imóvel')" />
					<a
						href="javascript:verificaImovelHabilitado();">
					<img width="23" height="21"
						src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" /></a>
					
					<logic:present name="idImovelNaoEncontrado">
	
						<logic:equal name="idImovelNaoEncontrado" value="exception">
							<html:text property="inscricaoImovel"  size="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
						</logic:equal>
		
						<logic:notEqual name="idImovelNaoEncontrado" value="exception">
							<html:text property="inscricaoImovel"  size="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
						</logic:notEqual>
		
					</logic:present>
		
					<logic:notPresent name="idImovelNaoEncontrado">
		
						<logic:empty name="FiltrarEntregaDocumentoCobrancaActionForm" property="imovel">
							<html:text property="inscricaoImovel" size="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
						</logic:empty>
						<logic:notEmpty name="FiltrarEntregaDocumentoCobrancaActionForm" property="imovel">
							<html:text property="inscricaoImovel" size="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
						</logic:notEmpty>
		
					</logic:notPresent>
					
					<a href="javascript:limparImovel();" > 
						<img border="0" src="<bean:message key='caminho.imagens'/>limparcampo.gif" style="cursor: hand;" /> 
					</a>	
					</td>									
				</tr>
				
				<tr>
			    <td><strong>Período de Geração:</strong></td>
			    <td>
			      <strong> 
			        <html:text tabindex="6" property="dataInicialGeracao" size="10" maxlength="10"  onkeyup="mascaraData(this,event);replicarCampo(document.forms[0].dataFinalGeracao,this);" onblur="verificarDataEntrega(this);"/> 
			        <a href="javascript:verificarPeriodoGeracaoHabilitado();">
				      <img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a> a
			      </strong> 
			      
			      <html:text tabindex="7" property="dataFinalGeracao" size="10" maxlength="10" onkeyup="mascaraData(this,event);" onblur="verificarDataEntrega(this);"/> 
			      <a href="javascript:verificarPeriodoGeracaoHabilitado();">
					<img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a> (dd/mm/aaaa)
			    </td>
			  </tr>	
				
				</table>
				</td>
			</tr>

		</table>
		</div>
		</td>
	</tr>
			
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>
				
				<tr>
					<td colspan="3" width="100%" height="1px" bgcolor="#000000"></td>
					
				</tr>				
					  <tr>
				<td align="left">
				
					<input type="button" name="ButtonReset"
					class="bottonRightCol" value="Limpar"
					onclick="window.location.href='<html:rewrite page="/exibirFiltrarEntregaDocumentoCobrancaAction.do?limparForm=OK"/>'">&nbsp;
					
					<input type="button"
						name="ButtonCancelar" class="bottonRightCol" value="Cancelar"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"></td>
				<td colspan="2" height="24" align="right">
				
				<input type="button" name="Button" class="bottonRightCol"
						 value="Filtrar"
						onClick="javascript:chamarFiltro();" /></td>
				<td></td>
			 </tr>
							
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>
	<%@ include file="/jsp/util/tooltip.jsp"%>
	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</body>


</html:html>
