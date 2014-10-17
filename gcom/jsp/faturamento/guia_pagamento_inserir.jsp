<%@page import="gcom.util.ConstantesSistema"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<%@ page import="gcom.gui.GcomAction"%>
<%@ page import="gcom.util.Util"%>
<%@ page import="java.math.BigDecimal"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="InserirGuiaPagamentoActionForm" />

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">
<!--
	// Verifica se o usuário tem permissão especial para inserir Guia de Pagamento sem RA(38)
	var inserirGuiaPagamentoSemRa = ${requestScope.inserirGuiaPagamentoSemRa};

    //Recebe os dados do(s) popup(s)
    function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

		var form = document.InserirGuiaPagamentoActionForm;

       	if (tipoConsulta == 'cliente') {
       		form.codigoCliente.value = codigoRegistro;
        	form.action = 'exibirInserirGuiaPagamentoAction.do'
        	form.submit();
      	}

       	if (tipoConsulta == 'imovel') {
        	form.idImovel.value = codigoRegistro;
        	form.action = 'exibirInserirGuiaPagamentoAction.do'
        	form.submit();
      	}
      
	   	if (tipoConsulta == 'tipoDebito') {
        	form.idTipoDebito.value = codigoRegistro;
        	form.descricaoTipoDebito.value = descricaoRegistro;
      	}
 
       	if (tipoConsulta == 'registroAtendimento') {
        	form.registroAtendimento.value = codigoRegistro;
			form.nomeRegistroAtendimento.value = descricaoRegistro;
			
			form.action='exibirInserirGuiaPagamentoAction.do?objetoConsulta=1';
	      	form.submit();
			
			
     	}
       	
       	if (tipoConsulta == 'ordemServico') {
        	form.ordemServico.value = codigoRegistro;
        	form.descricaoOrdemServico.value = descricaoRegistro;

			form.action='exibirInserirGuiaPagamentoAction.do?objetoConsulta=2';
	      	form.submit();
        	
      	}
      	
    }

    function limparImovel() {
        var form = document.InserirGuiaPagamentoActionForm;
		
		form.codigoCliente.disabled = false;
        form.idImovel.value = "";
        form.inscricaoImovel.value = "";
        form.nomeClienteUsuario.value = "";
        form.situacaoAgua.value = "";
        form.situacaoEsgoto.value = "";
        form.localidade.value = "";
    }
    
    function limparCliente() {
        var form = document.InserirGuiaPagamentoActionForm;
	
		form.idImovel.disabled = false;
        form.codigoCliente.value = "";
        form.nomeCliente.value = "";
        form.cpf.value = "";
        form.profissao.value = "";
        form.ramoAtividade.value = "";
    }
    
    function limparTipoDebito() {
        var form = document.InserirGuiaPagamentoActionForm;

		if(form.idTipoDebito.disabled == false){
	        form.idTipoDebito.value = "";
	        form.descricaoTipoDebito.value = "";
		}
    }

	function validaEnterCliente(event, caminho, campo) {
		var form = document.InserirGuiaPagamentoActionForm;
		validaEnter(event, caminho, campo);
		
		if(form.codigoCliente.value.length > 0) {
			form.idImovel.disabled = true;
		    if (event.keyCode == 13){
				form.idImovel.disabled = true;
			}	
        } else {
			form.idImovel.disabled = false;
			form.codigoCliente.value = "";
	        form.nomeCliente.value = "";
	        form.cpf.value = "";
	        form.profissao.value = "";
	        form.ramoAtividade.value = "";
		}
	}
	
	function controleImovel(form){
		form.codigoCliente.disabled = true;
	}
	
	function controleCliente(form){
		form.idImovel.disabled = true;
	}
	
	function validaEnterImovel(event, caminho, campo) {
		var form = document.InserirGuiaPagamentoActionForm;
		validaEnter(event, caminho, campo);
		
		if(form.idImovel.value.length > 0) {
			form.codigoCliente.disabled = true;
			if (event.keyCode == 13){
				form.registroAtendimento.focus();
			}	
		} else {
			form.codigoCliente.disabled = false;
			form.idImovel.value = "";
	        form.inscricaoImovel.value = "";
	        form.nomeClienteUsuario.value = "";
	        form.situacaoAgua.value = "";
	        form.situacaoEsgoto.value = "";
	        form.localidade.value = "";
		}
	}
	
	function habilitarPesquisa(objeto,action) {
		if (objeto.disabled == false) {
			abrirPopup(action, 600, 500);
		}	
	}
    
	function validarForm(form){
	
		var msgDataVencimento = "Data de Vencimento anterior à data corrente.";
		var msgDataVencimento60 = "Data de vencimento posterior a data corrente mais 60 dias.";
		var DATA_ATUAL = document.getElementById("DATA_ATUAL").value;
		var DATA_ATUAL_60 = document.getElementById("DATA_ATUAL_60").value;
		urlRedirect = "/gsan/inserirGuiaPagamentoAction.do"
		
		if (!isNaN("${sessionScope.exibirNuContratoParcOrgaoPublico}") && "${sessionScope.exibirNuContratoParcOrgaoPublico}" != null
				&& "${sessionScope.exibirNuContratoParcOrgaoPublico}" == <%=ConstantesSistema.SIM.intValue()%>){
			if(document.InserirGuiaPagamentoActionForm.numeroContratoParcelOrgaoPublico.value == null ||
					document.InserirGuiaPagamentoActionForm.numeroContratoParcelOrgaoPublico.value == ""){
				alert("Informe o número do contrato de parcelamento do orgão público.");
				document.InserirGuiaPagamentoActionForm.numeroContratoParcelOrgaoPublico.focus();
				return;
			}
		}
		
		if(document.InserirGuiaPagamentoActionForm.codigoCliente.value == '' && document.InserirGuiaPagamentoActionForm.idImovel.value == '' ){
			alert('Informe Matrícula do Imóvel ou Código do Cliente');
		} else {
			if (testarCampoValorZero(document.InserirGuiaPagamentoActionForm.registroAtendimento, 'RA - Registro de Atendimento')){
				// Verifica se o usuário tem permissão especial para inserir Guia de Pagamento sem RA(38)
				if (!inserirGuiaPagamentoSemRa) {
					var RA = document.InserirGuiaPagamentoActionForm.registroAtendimento;
					if (trim(RA.value) == '') {
						alert('Informe RA - Registro de Atendimento.');
						RA.focus();
						return;
					}
				}
			}else {
				return;
			}

			if(document.InserirGuiaPagamentoActionForm.numeroTotalPrestacao.value > 32767){
				alert('Número Total de Pretações não pode ser superior a 32767.');
  				return;
			}
			
			if(testarCampoValorZero(document.InserirGuiaPagamentoActionForm.ordemServico, 'Ordem de Serviço')
   				&& testarCampoValorZero(document.InserirGuiaPagamentoActionForm.valorTotalDebito, 'Valor do Débito') 
   				&& testarCampoValorZero(document.InserirGuiaPagamentoActionForm.dataVencimento, 'Data de Vencimento')
   				&& validateRequired(form) 
   				&& validateLong(form)){
				
				if(validateInserirGuiaPagamentoActionForm(form)){
				
	   				if(document.InserirGuiaPagamentoActionForm.numeroTotalPrestacao.value != 1 &&
	   					document.InserirGuiaPagamentoActionForm.qtdeDiasVencimento.value == 0){
	   				 alert('Quantidade de Dias entre os Vencimentos deve somente conter números positivos.');
	   				 return;
	   				}
	   				
					if (comparaData(form.dataVencimento.value, "<", DATA_ATUAL )){
						if (confirm(msgDataVencimento)){
							redirecionarSubmit(urlRedirect);
						}
					} else if (comparaData(form.dataVencimento.value, ">", DATA_ATUAL_60 )){
						if (confirm(msgDataVencimento60)){
							redirecionarSubmit(urlRedirect);
						}				
					} else {
						redirecionarSubmit(urlRedirect);
					}
				}
			}
			
			//Validar duplo clique no botão inserir
			var dc = form.duploClique.value;
			if(form.duploClique.value == ""){
	  			form.duploClique.value = "1";
	  		}else{
				return false;
			}
		}
	}
	
	function habDesabIndEmitirObservacao() {
		
		var form = document.forms[0];
		
		if (form.indicadorEmissaoObservacaoRA != null){
			if(form.registroAtendimento.value == "" && form.ordemServico.value == ""){
				form.indicadorEmissaoObservacaoRA[1].checked = true;
				form.indicadorEmissaoObservacaoRA[0].disabled = true;
  	  			form.indicadorEmissaoObservacaoRA[1].disabled = true;
			} else {
				form.indicadorEmissaoObservacaoRA[0].disabled = false;
  	  			form.indicadorEmissaoObservacaoRA[1].disabled = false;
			}		
		}
		
  	}
	
	function limparPesquisaRA() {

    	var form = document.forms[0];

		if(form.registroAtendimento.disabled == false){
	    	form.registroAtendimento.value = "";
	    	form.nomeRegistroAtendimento.value = "";
		}
		if(form.ordemServico.disabled){
			form.ordemServico.disabled = false;
	    	form.ordemServico.value = "";
	    	form.descricaoOrdemServico.value = "";
		}
		
  	}

	function limparPesquisaOs() {

    	var form = document.forms[0];

		if(form.registroAtendimento.disabled){
			form.registroAtendimento.disabled = false;
			limparPesquisaRA();
		}

    	form.ordemServico.value = "";
    	form.descricaoOrdemServico.value = "";
		
		if(form.habilitaTipoDebito.value == 'false'){

  			form.habilitaTipoDebito.value = 'true';
  			form.idTipoDebito.disabled = false;
  			limparTipoDebito();
  		}
  	}
  	
  	function habilitaCampo(){
  		var form = document.forms[0];
  		
  		if((form.ordemServico.value != null && form.ordemServico.value != "") && 
  			(form.registroAtendimento.value != null && form.registroAtendimento.value != "" ) ){
  		
  			form.registroAtendimento.disabled = true;
  		}
  		
  		this.habDesabIndEmitirObservacao();
  		
  		if(form.habilitaTipoDebito.value == 'false'){
  			form.idTipoDebito.disabled = true;
  		}
  	
  	}
	function bloquearOrdemServico(){
 		var form = document.forms[0];
		if(form.registroAtendimento.value != ""){
			form.ordemServico.disabled = true;
		} else{
			form.ordemServico.disabled = false;
    		limparPesquisaOs();
		}
	}
	
	function bloquearRegistroAtendimento(){
	 	var form = document.forms[0];
	
		if(form.ordemServico.value != ""){
			form.registroAtendimento.disabled = true;
		}else{
			form.registroAtendimento.disabled = false;		
			limparPesquisaRA();
		}
	}

	function remover(objeto) {
		if (confirm ("Confirma remoção?")) {
			redirecionarSubmit('removerInserirGuiaPagamentoColecaoPrestacaoAction.do');
		}
	}

	function reload() {
		var form = document.forms[0];
		form.action = "exibirInserirGuiaPagamentoAction.do";
		form.submit();
	}
-->
</script>


</head>

<body leftmargin="5" topmargin="5" onload="javascript:habilitaCampo();setarFoco('${requestScope.nomeCampo}');">

<html:form action="/exibirInserirGuiaPagamentoAction" method="post"
    name="InserirGuiaPagamentoActionForm"
    type="gcom.gui.faturamento.InserirGuiaPagamentoActionForm"
	onsubmit="return validateInserirGuiaPagamentoActionForm(this);">

	<INPUT TYPE="hidden" ID="DATA_ATUAL" value="${requestScope.dataAtual}" />
	<INPUT TYPE="hidden" ID="DATA_ATUAL_60" value="${requestScope.dataAtual60}" />
	<html:hidden property="duploClique" />

	<html:hidden property="habilitaTipoDebito" />
	

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
					<td class="parabg">Inserir Guia de Pagamento</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td>Para inserir a guia de pagamento, informe os dados
					abaixo:</td>
					<td align="right"></td>												
				</tr>
				</table>
				<table width="100%" border="0">
				<tr>
					<td height="10" width="110">
						<strong>Matrícula do Imóvel:</strong>
					</td>
					
					<td align="left">
						<html:text property="idImovel" 
							maxlength="9"
							size="9"
							onkeypress="return isCampoNumerico(event);"
							onkeyup="validaEnterImovel(event, 'exibirInserirGuiaPagamentoAction.do', 'idImovel');" />
							
						<a href="javascript:habilitarPesquisa(document.forms[0].idImovel,'exibirPesquisarImovelAction.do');"> 
							<img width="23" 
								height="21"
								src="<bean:message key='caminho.imagens'/>pesquisa.gif" 
								border="0" /></a>
								
						<a href="javascript:limparImovel();">
							<img border="0" 
								src="<bean:message key='caminho.imagens'/>limparcampo.gif"
								style="cursor: hand;" /></a>
					</td>
				</tr>
				<tr>
					<td colspan="4" height="10"></td>
				</tr>
				<tr>
					<td colspan="4">

					<table width="100%" align="center" bgcolor="#99CCFF" border="0">
						<tr>
							<td><strong>Dados do Imóvel:</strong></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td width="100%" align="center">

							<table width="100%" border="0">

								<tr>
									<td><strong>Inscri&ccedil;&atilde;o do Im&oacute;vel:</strong></td>
									<td colspan="3" align="right">
									<div align="left"><html:text property="inscricaoImovel"
										readonly="true" style="background-color:#EFEFEF; border:0"
										size="20" maxlength="20" /></div>
									</td>
								</tr>
								<tr>
									<td><strong>Nome do Cliente Usu&aacute;rio:</strong></td>

									<td colspan="3" align="right">
									<div align="left"><a href="usuario_pesquisar.htm"> </a> <span
										class="style1"> <html:text property="nomeClienteUsuario"
										readonly="true" style="background-color:#EFEFEF; border:0"
										size="50" maxlength="45" /></span></div>
									</td>
								</tr>
								<tr>
									<td><strong> Situa&ccedil;&atilde;o de &Aacute;gua: </strong></td>

									<td align="right">
									<div align="left"><html:text property="situacaoAgua"
										readonly="true" style="background-color:#EFEFEF; border:0"
										size="20" maxlength="20" /></div>
									</td>
								</tr>
								<tr>
									<td align="left">
									<div align="left"><strong> Situa&ccedil;&atilde;o de Esgoto:</strong></div>
									</td>
									<td align="left"><html:text property="situacaoEsgoto"
										readonly="true" style="background-color:#EFEFEF; border:0"
										size="20" maxlength="20" /></td>
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
					<td align="left">
						<strong>Código do Cliente:</strong>
					</td>
					
					<td>
						<html:text property="codigoCliente" 
						maxlength="9" 
						size="9"
						onkeyup="return validaEnterCliente(event, 'exibirInserirGuiaPagamentoAction.do', 'codigoCliente'); " />
						
						<a href="javascript:habilitarPesquisa(document.forms[0].codigoCliente,'exibirPesquisarClienteAction.do');">
							<img width="23" 
								height="21"
								src="<bean:message key='caminho.imagens'/>pesquisa.gif" 
								border="0" /></a>
						
						<a href="javascript:limparCliente();">
							<img border="0"
							src="<bean:message key='caminho.imagens'/>limparcampo.gif"
							style="cursor: hand;" /></a>
					</td>
				</tr>


				<tr>
					<td colspan="4" height="10"></td>
				</tr>
				<tr>
					<td colspan="4">

					<table width="100%" align="center" bgcolor="#99CCFF">
						<tr>
							<td><strong>Dados do Cliente:</strong></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td width="100%" align="center">

							<table width="100%" border="0">
								<tr>
									<td width="180"><strong>CPF/CNPJ:</strong></td>

									<td colspan="3" align="left"><html:text property="cpf"
										readonly="true" style="background-color:#EFEFEF; border:0"
										size="20" maxlength="17" /></td>
								</tr>
								<tr>
									<td width="180"><strong>Nome do Cliente:</strong></td>
									<td colspan="3" align="left">
									<div align="left"><html:text property="nomeCliente"
										readonly="true" style="background-color:#EFEFEF; border:0"
										size="50" maxlength="45" /></div>
									</td>
								</tr>
								<tr>
									<td width="180"><strong> Profiss&atilde;o: </strong></td>

									<td align="right">
									<div align="left"><html:text property="profissao"
										readonly="true" style="background-color:#EFEFEF; border:0"
										size="30" maxlength="30" /></div>
									</td>
								</tr>

								<tr>
									<td width="180"><strong> Ramo de Atividade:</strong></td>
									<td align="right">
									<div align="left"><html:text property="ramoAtividade"
										readonly="true" style="background-color:#EFEFEF; border:0"
										size="20" maxlength="20" /></div>
									</td>

								</tr>
							</table>
							</td>
						</tr>
					</table>
					</td>
				</tr>

				<tr>
					<td width="180">
						<logic:equal name="inserirGuiaPagamentoSemRa" value="false" scope="request">
							<strong>RA - Registro de Atendimento:<font color="#FF0000">*</font></strong>
						</logic:equal>
						<logic:equal name="inserirGuiaPagamentoSemRa" value="true" scope="request">
							<strong>RA - Registro de Atendimento:</strong>
						</logic:equal>
					</td>

					<td colspan="3" align="right">
						<div align="left">

						<html:text maxlength="9" 
							tabindex="1"
							property="registroAtendimento" 
							size="10"
							onfocus="javascript:bloquearOrdemServico();"
							onkeyup="javascript:bloquearOrdemServico(), habDesabIndEmitirObservacao();"
							onkeypress="validaEnterComMensagem(event, 'exibirInserirGuiaPagamentoAction.do?objetoConsulta=1','registroAtendimento','Registro Atendimento');"/>
							
							<a href="javascript:javascript:habilitarPesquisa(document.forms[0].registroAtendimento,'exibirPesquisarRegistroAtendimentoAction.do');">
								<img width="23" 
									height="21" 
									border="0"
									src="<bean:message key="caminho.imagens"/>pesquisa.gif"
									title="Pesquisar RA" /></a> 

							<logic:present name="numeroRAEncontrada" scope="request">
								<html:text property="nomeRegistroAtendimento" 
									size="35"
									maxlength="43" 
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</logic:present> 

							<logic:notPresent name="numeroRAEncontrada" scope="request">
								<html:text property="nomeRegistroAtendimento" 
									size="35"
									maxlength="43" 
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: red" />
							</logic:notPresent>

							<a href="javascript:limparPesquisaRA();"> 
								<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
									border="0" 
									title="Apagar" /></a>
						
						</div>
					</td>
				</tr>
				<tr>
					<td width="180"><strong>Ordem de Servi&ccedil;o:</strong></td>

					<td colspan="3" align="right">
						
						<div align="left">
					
						<html:text maxlength="9" 
							tabindex="1"
							property="ordemServico" 
							size="10"
							onfocus="javascript:bloquearRegistroAtendimento();"
							onkeyup="javascript:bloquearRegistroAtendimento(), habDesabIndEmitirObservacao();"
							onkeypress="validaEnterComMensagem(event, 'exibirInserirGuiaPagamentoAction.do?objetoConsulta=2','ordemServico','Ordem de Serviço');"/>
							
							<a href="javascript:javascript:habilitarPesquisa(document.forms[0].ordemServico,'exibirPesquisarOrdemServicoAction.do');">
								<img width="23" 
									height="21" 
									border="0"
									src="<bean:message key="caminho.imagens"/>pesquisa.gif"
									title="Pesquisar RA" /></a> 

							<logic:present name="ordemServicoEncontrada" scope="request">
								<html:text property="descricaoOrdemServico" 
									size="35"
									maxlength="43" 
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</logic:present> 

							<logic:notPresent name="ordemServicoEncontrada" scope="request">
								<html:text property="descricaoOrdemServico" 
									size="35"
									maxlength="43" 
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: red" />
							</logic:notPresent>

							<a href="javascript:limparPesquisaOs();"> 
								<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
									border="0" 
									title="Apagar" /></a>
						</div>
					</td>
				</tr>				
				
				<logic:equal name="paramExibirObservacaoRA" value="<%="" + ConstantesSistema.SIM%>" scope="session">
				<tr>
					<td><strong>Emitir a observação do RA na guia?<font color="#FF0000">*</font></strong></td>
					<td><strong> <span class="style1"><strong> <html:radio
						property="indicadorEmissaoObservacaoRA"
						value="<%=(ConstantesSistema.SIM).toString()%>"
						tabindex="7" /> <strong>SIM <html:radio
						property="indicadorEmissaoObservacaoRA"
						value="<%=(ConstantesSistema.NAO).toString()%>" />
						NÃO </strong></strong></span></strong></td>
				</tr>
				</logic:equal>
				
				<tr>
					<td width="180"><strong>Localidade:<strong><font color="#FF0000"> </font></strong></strong></td>
					<td colspan="3" align="right">
					<div align="left"><html:text property="localidade" size="10"
						maxlength="3" readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000" /></div>
					</td>
				</tr>
				<tr>
					<td width="180"><strong>Data de Vencimento:<strong><font color="#FF0000">*</font></strong></strong></td>
					<td align="right" colspan="3">
						<div align="left">
							<html:text property="dataVencimento" size="10" maxlength="10" onkeyup="mascaraData(this, event);" onblur="reload();" /> 
								<a href="javascript:abrirCalendario('InserirGuiaPagamentoActionForm', 'dataVencimento')">
									<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a> dd/mm/aaaa
							</div>
					</td>
				</tr>
				
				<tr>	
					<td width="180"><strong>Dias entre  Vencimentos:<strong><font color="#FF0000">*</font></strong></strong></td>
					<td align="left">
						<div align="left">
								<html:text property="qtdeDiasVencimento" size="10" maxlength="2"/>
						</div>
					</td>
				</tr>
				<tr>
					<td width="180"><strong>Número de Prestações:<strong><font color="#FF0000">*</font></strong></strong></td>
					<td align="left">
						<div align="left">
								<html:text property="numeroTotalPrestacao" size="10" maxlength="6" onkeypress="return isCampoNumerico(event);" onblur="javascript:reload();"/>
						</div>
					</td>
				</tr>
				<tr>
					<td width="" colspan="6">	
						<table width="100%" align="center" bgcolor="#99CCFF">
							<tr>
								<td colspan="5">							
						    	  	<strong>Tipos do D&eacute;bito:</strong>
						    	<td align="right">
									<input type="button" class="bottonRightCol" value="Adicionar" tabindex="6" id="botaoDebitoTipo"
										onclick="javascript:reload();abrirPopupComSubmit('exibirInserirGuiaPagamentoPrestacaoAction.do?limparPopup=SIM&retornarTela=exibirInserirGuiaPagamentoAction.do', 300, 600,'Pesquisar');">									      		
							    </td>
							</tr>	
					    </table>	
					</td>
				</tr>

				<tr>
					<td colspan="4">
					<table width="100%" cellpadding="0" cellspacing="0" >						
						<tr>						
							<td colspan="3" width="40%">
								<table width="100%" bgcolor="#90c7fc" border="0">
									<!--header da tabela interna -->
									<tr bgcolor="#90c7fc">
										<td width="15%" align="center">
											<strong>Remover</strong>
										</td>
										<td width="35%" align="left">
											<strong>Débito</strong>
										</td>
										<td width="20%" align="right">
											<strong>Valor Prestação</strong>
										</td>
										<td width="20%" align="right">
											<strong>Valor  Débito</strong>
										</td>
									</tr>
									<tr>
								<td colspan="4">
									<div style="width: 100%; height: 100%; overflow: auto;">
									<table width="100%" bgcolor="#99CCFF">
										<logic:notPresent name="colecaoGuiaPrestacaoHelper">
											<input type="hidden" id="validarEndereco" value="0">
										</logic:notPresent>
										<logic:present name="colecaoGuiaPrestacaoHelper">
											<%int cont = 0;%>
											<!--  Campo que vai guardar o valor do endereço a ser removido -->
											<input type="hidden" name="prestacaoRemoverSelecao" value="" />
											<logic:iterate name="colecaoGuiaPrestacaoHelper" id="prestacoes" scope="session">
												<%cont = cont + 1;
												if (cont % 2 == 0) {%>
												<tr bgcolor="#cbe5fe">
													<%} else {
			
												%>
												<tr bgcolor="#FFFFFF">
													<%}%>
													<td width="15%">
														<div align="center"><font color="#333333"> 
															<img width="14" height="14" border="0" src="<bean:message key="caminho.imagens"/>Error.gif"
															onclick="javascript:remover(document.forms[0].prestacaoRemoverSelecao.value='<%=""+GcomAction.obterTimestampIdObjeto(prestacoes)%>');" />
															</font>
														</div>
													</td>
													<td width="35%" align="left"><div><bean:write name="prestacoes" property="descricaoTipoDebito"/></div></td>
													<td width="20%" align="right"><bean:write name="prestacoes" property="valorPrestacaoTipoDebito" formatKey="money.format"/></td>
													<td width="20%" align="right"><bean:write name="prestacoes" property="valorTipoDebito" formatKey="money.format"/></td>
												</tr>				
												</logic:iterate>
											</logic:present>
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
				<tr>
					<td align="right" colspan="3"><strong>Valor Total do D&eacute;bito:<strong><font color="#FF0000">*</font></strong></strong></td>
					<td align="right">
					<div align="right">
						<logic:present name="valorTotalGuiaPagamento">
							<bean:define name="valorTotalGuiaPagamento" id="valorGuia" />
							&nbsp;&nbsp;<html:text property="valorTotalDebito" size="18" maxlength="13" value='<%= Util.formatarMoedaReal(((BigDecimal)valorGuia), 2)  %>' onkeyup="formataValorMonetario(this, 13)" style="text-align:right;" readonly="true" />
						</logic:present>
						<logic:notPresent name="valorTotalGuiaPagamento">
							<html:text property="valorTotalDebito" size="13" maxlength="13" style="text-align:right;" readonly="true" />							
						</logic:notPresent>
						
					</div>
					</td>
				</tr>
				<logic:present name="exibirNuContratoParcOrgaoPublico" scope="session">
				<tr>
					<td width="15%" colspan="3"><strong>Nº Contrato Parcelamento Órgão Público:<strong><font color="#FF0000">*</font></strong></strong></td>
					<td><html:text property="numeroContratoParcelOrgaoPublico" 
						size="9" 
						tabindex="1"
						maxlength="9" 
						onkeypress="return isCampoNumerico(event);"/>
					</td>
				</tr>
				</logic:present>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td><div align="left"><font color="#FF0000">*</font> Campo
					Obrigat&oacute;rio</div></td>
					<td colspan="3" align="right">
					<div align="left"></div>
					<div align="left"><strong></strong></div>					
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>

				<tr>
					<td colspan="3"><strong> <font color="#ff0000"> <input name="Submit22"
						class="bottonRightCol" value="Desfazer" type="button"
						onclick="window.location.href='/gsan/exibirInserirGuiaPagamentoAction.do?menu=sim';">

					<input name="Submit23" class="bottonRightCol" value="Cancelar"
						type="button"
						onclick="window.location.href='/gsan/telaPrincipal.do'"> </font></strong></td>
					
					<td colspan="3">
						<strong>
							 <font color="#ff0000">
								<input name="Button" class="bottonRightCol" value="Alterar Dados Prestações" type="button"
										  onclick="javascript:abrirPopupComSubmit('exibirAlterarDadosDasPrestacoesAction.do?numeroTotalPrestacoes=' + document.InserirGuiaPagamentoActionForm.numeroTotalPrestacao.value + '&retornarTela=exibirInserirGuiaPagamentoAction.do', 400, 600);"/>
							</font>
						</strong>
					</td>
					
					<td align="right">
					<gcom:controleAcessoBotao name="Button" value="Inserir"
							  onclick="javascript:validarForm(document.forms[0]);" url="inserirGuiaPagamentoAction.do"/>
					</td>
				</tr>
			</table>

			<p>&nbsp;</p>

			</td>
		</tr>
	</table>


	<%@ include file="/jsp/util/rodape.jsp"%>

	<logic:notEqual name="InserirGuiaPagamentoActionForm"
		property="idImovel" value="">
		<script language="JavaScript">
	<!--
		controleImovel(document.forms[0]);
	-->
	</script>
	</logic:notEqual>

	<logic:notEqual name="InserirGuiaPagamentoActionForm"
		property="codigoCliente" value="">
		<script language="JavaScript">
	<!--
		controleCliente(document.forms[0]);
	-->
	</script>
	</logic:notEqual>

</html:form>
</body>
</html:html>
