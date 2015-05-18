<%@page import="gcom.faturamento.debito.DebitoCreditoSituacao"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@ page import="gcom.util.Util"%>
<%@ page import="gcom.cobranca.bean.ObterDebitoImovelOuClienteHelper"%>
<%@ page import="gcom.cobranca.bean.GuiaPagamentoValoresHelper"%>
<%@ page import="gcom.cobranca.bean.ContaValoresHelper" isELIgnored="false"%>
<%@ page import="gcom.util.ConstantesSistema" isELIgnored="false"%>
<%@ page import="java.util.Collection" isELIgnored="false"%>
<%@ page import="gcom.faturamento.conta.Conta"%>
<%@ page import="gcom.faturamento.debito.DebitoACobrar"%>
<%@ page import="gcom.cadastro.cliente.ClienteImovel"%>
<%@ page import="gcom.cadastro.imovel.ImovelCobrancaSituacao"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"	href="<bean:message key="caminho.css"/>EstilosCompesa.css"	type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="ConsultarImovelActionForm" dynamicJavascript="true" />
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>


<script language="JavaScript">

<!--

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

function exibirMsgs(){
	var form = document.forms[0];
	
	var objIndicadorPrimeiroAcessoImovelDebito = returnObject(form,"indicadorPrimeiroAcessoImovelDebito");
	var objIndicadorImovelEmExecucaoFiscal = returnObject(form,"indicadorImovelEmExecucaoFiscal");
	
	if(objIndicadorImovelEmExecucaoFiscal != 'undefined' 
			&& objIndicadorImovelEmExecucaoFiscal.value == '1'
			&& objIndicadorPrimeiroAcessoImovelDebito.value == "1"){
		alert('Atenção: Imóvel em Execução Fiscal.');
		form.indicadorPrimeiroAcessoImovelDebito.value = "2";
	}
	
	if(form.idImovelDebitos.value == '' ){
		form.indicadorPrimeiroAcessoImovelDebito.value = "1";
	}

}

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

    var form = document.forms[0];

    if (tipoConsulta == 'imovel') {
      form.idClienteRelacaoImovelSelecionado.value = "";
      form.idImovelDebitos.value = codigoRegistro;
	  form.action = 'consultarImovelWizardAction.do?action=exibirConsultarImovelDebitosAction&indicadorNovo=OK&limparForm=S'
	  form.submit();
    }
    
}

function limparForm(){
   	var form = document.forms[0];
	form.action = 'consultarImovelWizardAction.do?action=exibirConsultarImovelDebitosAction&limparForm=OK'
	form.submit();
}

function verificarExibicaoRelatorio() {
	var form = document.forms[0];
	
	if (form.idImovelDebitos.value.length > 0 && form.matriculaImovelDebitos.value.length > 0) {
		<% 
			if(session.getAttribute("exibirPopUpTaxaDebitoACobrar") == null ) {
		%>
			toggleBox('demodiv',1);
		<%  } else { %>
				abrirPopupDeNome('gerarRelatorioDebitosConsultarAction.do?menu=nao&popup=sim&imovel='+form.idImovelDebitos.value, 400, 800, 'EmitirRelatorioDebitoConsulta', 'yes');
		<% } %>
		
	} else {
		alert('Informe Imóvel');
	}
}

function limparImovelClienteSelecionado() {
	var form = document.forms[0];

	if (form.idClienteRelacaoImovelSelecionado != undefined) {
		form.idClienteRelacaoImovelSelecionado.value = ''
	}

}

function limparImovelTecla() {

	var form = document.forms[0];
	
	form.matriculaImovelDebitos.value = "";
	
	if (form.digitoVerificadorImovelDebitos != undefined) {
		form.digitoVerificadorImovelDebitos.value = "";
	}
	
	form.situacaoAguaDebitos.value = "";
	form.situacaoEsgotoDebitos.value = "";
	form.tipoLigacao.value = "";

}

function gerarExtratoDebito() {

	var form = document.forms[0];
	
	if (form.matriculaImovelDebitos.value != null && form.matriculaImovelDebitos.value != "") {

		<% 
			if(session.getAttribute("exibirPopUpTaxaDebitoACobrar") == null ) {
		%>
				form.action = 'gerarRelatorioExtratoDebitoAction.do?consultarDebitoImovel=1';
	    		form.submit();
		<%  } else { %>
				abrirPopupDeNome('gerarRelatorioExtratoDebitoAction.do?consultarDebitoImovel=1&menu=nao&popup=sim&imovel='+form.idImovelDebitos.value, 400, 800, 'EmitirExtratoDebito', 'yes');
		<% } %>
			
	} else {
		alert("Pesquise o imóvel");
	}

}

function abrirInserirDebitoACobrar(){
	var form = document.forms[0];
    
    if (form.idImovelDebitos.value == '') {
		alert('Informe a matrícula do imóvel.');
	} else {
		abrirPopupDeNome('exibirInserirDebitoACobrarAction.do?menu=nao&limparForm=true', 400, 800, 'InserirDebito', 'yes');
	}	
}

function abrirInserirCreditoARealizar(){
	var form = document.forms[0];
    
    if (form.idImovelDebitos.value == '') {
		alert('Informe a matrícula do imóvel.');
	} else {
		abrirPopupDeNome('exibirInserirCreditoARealizarAction.do?menu=nao', 400, 800, 'InserirCredito', 'yes');
	}	
}

function abrirManterContas(){
	var form = document.forms[0];
    
    if (form.idImovelDebitos == null || form.idImovelDebitos.value == '') {
		alert('Informe a matrícula do imóvel.');
	} else {
	    var stringUrl = form.idImovelDebitos.value;
		abrirPopupDeNome('exibirManterContaAction.do?idImovelRequest='+stringUrl+'&limparForm=true', 400, 800, 'ManterContas', 0);
	}	

}

function abrirInserirGuiaPagamento(){
	var form = document.forms[0];
    
    if (form.idImovelDebitos.value == '') {
		alert('Informe a matrícula do imóvel.');
	} else {
		abrirPopupDeNome('exibirInserirGuiaPagamentoAction.do?menu=nao', 400, 800, 'InserirGuia', 'yes');
	}	
}

function abrirEfetuarParcelamento(){
    var form = document.forms[0];
    
    if (form.idImovelDebitos.value == '') {
		alert('Informe a matrícula do imóvel.');
	} else {
		form.action = 'exibirEfetuarParcelamentoDebitosAction.do?menu=sim&guardarMatriculaImovel='+form.idImovelDebitos.value;
		form.submit();
	}	
}

function abrirPopupExtratoDebito(){
    var form = document.forms[0];
    
    if (form.idImovelDebitos.value == '') {
		alert('Informe a matrícula do imóvel.');
	} else {
		abrirPopupDeNome('exibirDebitoCreditoDadosSelecaoExtratoAction.do?menu=nao&popup=sim&imovel='+form.idImovelDebitos.value, 400, 800, 'EfetuarParcelamento', 'yes');
	}	
}

function gerarRelatorioAvisoEOrdemCorteIndividual() {
	var form = document.forms[0];

	if (form.idImovelDebitos.value != null && form.idImovelDebitos.value != "") {
		form.action = 'gerarRelatorioAvisoEOrdemCorteIndividualAction.do?imovel='+form.idImovelDebitos.value;
		form.submit();
	} else {
		alert('Informe a matrícula do imóvel.');
	}
}

//-->
</script>

<logic:present name="indicadorFauramentoTitularDebito" scope="request">
	<SCRIPT LANGUAGE="JavaScript">
	<!--
	
	  	function marcarClienteOrigemId(objeto) {
	  		var form = document.forms[0];
	  		
			var i = 0;
			for (i = 0; i < document.forms[0].idClienteImovel.length; i++) { 
			    if (document.forms[0].idClienteImovel[i].checked == true) {
			    	form.idClienteRelacaoImovelSelecionado.value = document.forms[0].valorClienteImovel[i].value;
			    	form.action = 'consultarImovelWizardAction.do?action=exibirConsultarImovelDebitosAction&indicadorNovo=OK&limparForm=S';
			    	form.submit();
			    }
			}	  		
	  	}

	//-->
	</SCRIPT>
</logic:present>
	
</head>

<body leftmargin="5" topmargin="5" onload="javascript:setarFoco('idImovelDebitos');exibirMsgs();">
<html:form action="/exibirConsultarImovelAction.do"
	name="ConsultarImovelActionForm"
	type="gcom.gui.cadastro.imovel.ConsultarImovelActionForm" method="post"
	onsubmit="return validateConsultarImovelActionForm(this);">


	<jsp:include page="/jsp/util/wizard/navegacao_abas_wizard_consulta.jsp?numeroPagina=5" />
	
	<html:hidden property="indicadorPrimeiroAcessoImovelDebito"/>
	<html:hidden property="indicadorImovelEmExecucaoFiscal"/>	
	
	<logic:present name="montarPopUp">
	 <table width="800" border="0" cellspacing="5" cellpadding="0">
		<tr>
		<td valign="top" class="centercoltext"><!--Início Tabela Reference a Páginação da Tela de Processo-->
			
			<p>&nbsp;</p>			
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">&nbsp;</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>						
				</tr>				
			</table>
			<!--Fim Tabela Reference a Páginação da Tela de Processo-->		
	
	</logic:present>
	
	<logic:notPresent name="montarPopUp">	
	
		<%@ include file="/jsp/util/cabecalho.jsp"%>
		<%@ include file="/jsp/util/menu.jsp"%>	
		
		<table width="800" border="0" cellspacing="5" cellpadding="0">
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
			
			<p>&nbsp;</p>			
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">&nbsp;</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>						
				</tr>				
			</table>
			<!--Fim Tabela Reference a Páginação da Tela de Processo-->
	
	</logic:notPresent>
	
			
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="4">
					<table width="100%" align="center" bgcolor="#99CCFF" border="0">
						<tr>
							<td align="center"><strong>Dados do Imóvel </strong></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td width="100%" align="center">
							<table width="100%" border="0">
								<tr>
									<td bordercolor="#000000" width="25%"><strong>Im&oacute;vel:<font
										color="#FF0000">*</font></strong></td>
									<td width="75%" colspan="3"><html:text
										property="idImovelDebitos" maxlength="9" size="9"
										onkeypress="limparImovelClienteSelecionado();validaEnterComMensagem(event, 'consultarImovelWizardAction.do?action=exibirConsultarImovelDebitosAction&indicadorNovo=OK&limparForm=S','idImovelDebitos','Im&oacute;vel');" 
										onkeyup="limparImovelTecla();" />
									<a
										href="javascript:abrirPopup('exibirPesquisarImovelAction.do', 400, 800);">
									<img width="23" height="21"
										src="<bean:message key="caminho.imagens"/>pesquisa.gif"
										border="0" /></a> <logic:present
										name="idImovelDebitosNaoEncontrado" scope="request">
										
										<logic:equal name="matriculaSemDigitoVerificador" value="0" scope="request">
											<html:text property="matriculaImovelDebitos" size="40"
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: #ff0000" />										
										</logic:equal>
										
										<logic:equal name="matriculaSemDigitoVerificador" value="1" scope="request">
											<html:text property="digitoVerificadorImovelDebitos" size="2"
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: #ff0000" />											
											<html:text property="matriculaImovelDebitos" size="31"
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: #ff0000" />										
										</logic:equal>										

									</logic:present> <logic:notPresent
										name="idImovelDebitosNaoEncontrado" scope="request">
										<logic:present name="valorMatriculaImovelDebitos"
											scope="request">
											<logic:equal name="matriculaSemDigitoVerificador" value="0" scope="request">
												<html:text property="matriculaImovelDebitos" size="40"
													readonly="true"
													style="background-color:#EFEFEF; border:0; color: #000000" />
											</logic:equal>

											<logic:equal name="matriculaSemDigitoVerificador" value="1" scope="request">
												<html:text property="digitoVerificadorImovelDebitos" size="2"
													readonly="true"
													style="background-color:#EFEFEF; border:0; color: #000000" />											
												<html:text property="matriculaImovelDebitos" size="31"
													readonly="true"
													style="background-color:#EFEFEF; border:0; color: #000000" />
											</logic:equal>											
											
										</logic:present>
										<logic:notPresent name="valorMatriculaImovelDebitos"
											scope="request">
											<logic:equal name="matriculaSemDigitoVerificador" value="0" scope="request">
												<html:text property="matriculaImovelDebitos" size="40"
													readonly="true"
													style="background-color:#EFEFEF; border:0; color: #000000" />
											</logic:equal>
											
											<logic:equal name="matriculaSemDigitoVerificador" value="1" scope="request">
												<html:text property="digitoVerificadorImovelDebitos" size="2"
													readonly="true"
													style="background-color:#EFEFEF; border:0; color: #000000" />
																								
												<html:text property="matriculaImovelDebitos" size="31"
													readonly="true"
													style="background-color:#EFEFEF; border:0; color: #000000" />
											</logic:equal>											
											
										</logic:notPresent>
									</logic:notPresent> <a href="javascript:limparForm();"> <img
										src="<bean:message key="caminho.imagens"/>limparcampo.gif"
										border="0" title="Apagar" /></a></td>

								</tr>
								<tr>
									<td height="10">
									<div class="style9"><strong>Situação de Água:</strong></div>
									</td>
									<td><html:text property="situacaoAguaDebitos" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000"
										size="15" maxlength="15" /></td>
									<td width="146"><strong>Situação de Esgoto:</strong></td>
									<td width="123"><html:text property="situacaoEsgotoDebitos"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000"
										size="15" maxlength="15" /></td>
								</tr>
								<tr>
								
								<td height="10">
									<div class="style9"><strong>Tipo de Ligação:</strong></div>
									</td>
									<td><html:text property="tipoLigacao"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000"
										size="15" maxlength="15" /></td>
							
									<td width="90"></td>
									<td width="120"></td>
								</tr>
								
								
								
								
								<c:if test="${not empty msgImovelProcessoCorte}">
												
									<tr height="40px">
										
										<td colspan="4" align="center">
										
											<font color="red" size="3">
											
												<c:out value="${msgImovelProcessoCorte}"/>
											
											</font>	
										
										</td>
																			
									</tr>						
								
								</c:if>
								<c:if test="${not empty msgSituacaoImovelCampanhaPremiacao}">
												
									<tr height="40px">
										
										<td colspan="4" align="center">
										
											<font color="red" size="3">
											
												<c:out value="${msgSituacaoImovelCampanhaPremiacao}"/>
											
											</font>	
										
										</td>
																			
									</tr>						
								
								</c:if>
								
							    <logic:present name="indicadorFauramentoTitularDebito" scope="request">
							    <tr>
								<td colspan="4">			    
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
																
							</table>
							</td>
						</tr>
					</table>
					</td>
				</tr>

				<tr>
					<td colspan="4">
					<table width="100%" border="0">
						<tr>
							<td colspan="4">
							<table width="100%" align="center" bgcolor="#90c7fc" border="0">
								<tr bordercolor="#79bbfd">
									<td colspan="5" align="center" bgcolor="#79bbfd"><strong>Clientes</strong></td>
								</tr>
								<tr bordercolor="#000000">
									<td width="28%" bgcolor="#90c7fc" align="center">
									<div class="style9"><font color="#000000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <strong>Nome do
									Cliente</strong> </font></div>
									</td>
									<td width="17%" bgcolor="#90c7fc" align="center">
									<div class="style9"><font color="#000000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <strong>Tipo da
									Rela&ccedil;&atilde;o</strong> </font></div>
									</td>
									<td width="19%" bgcolor="#90c7fc" align="center">
									<div class="style9"><font color="#000000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <strong>Data
									In&iacute;cio Rela&ccedil;&atilde;o</strong></font></div>
									</td>
									<td width="16%" bgcolor="#90c7fc" align="center">
									<div class="style9"><font color="#000000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <strong>Telefone</strong></font>
									</div>
									</td>
									<td width="20%" bgcolor="#90c7fc" align="center"><font
										color="#000000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"><strong>CPF/CNPJ</strong>
									</font></td>
								</tr>
								<tr>
									<td width="100%" colspan="5">
									<div style="width: 100%; height: 100%; overflow: auto;">
								
									<table width="100%" align="left" bgcolor="#99CCFF">
										<!--corpo da segunda tabela-->
										<%int cont = 0;%>
										<logic:notEmpty name="imovelClientes">
											<logic:iterate name="imovelClientes" id="imovelCliente"
												type="ClienteImovel">
												<%cont = cont + 1;
												if (cont % 2 == 0) {%>
												<tr bgcolor="#cbe5fe">
													<%} else {%>
												<tr bgcolor="#FFFFFF">
													<%}%>
													<td width="28%" bordercolor="#90c7fc" align="left">
													<div class="style9"><font color="#000000"
														style="font-size:9px"
														face="Verdana, Arial, Helvetica, sans-serif"> <logic:present
														name="imovelCliente" property="cliente">
														<a
															href="javascript:abrirPopup('exibirConsultarClienteAction.do?desabilitarPesquisaCliente=SIM&codigoCliente='+<bean:write name="imovelCliente" property="cliente.id" />, 500, 800);">
														<bean:write name="imovelCliente" property="cliente.nome" />
														</a>
													</logic:present> </font></div>
													</td>
													<td width="17%" align="left">
													<div class="style9"><font color="#000000"
														style="font-size:9px"
														face="Verdana, Arial, Helvetica, sans-serif"> <logic:present
														name="imovelCliente" property="clienteRelacaoTipo">
														<bean:write name="imovelCliente"
															property="clienteRelacaoTipo.descricao" />
													</logic:present> </font></div>
													</td>
													<td width="19%" align="center">
													<div class="style9"><font color="#000000"
														style="font-size:9px"
														face="Verdana, Arial, Helvetica, sans-serif"> </font></div>
													<bean:write name="imovelCliente"
														property="dataInicioRelacao" formatKey="date.format" /></td>

													<td width="16%" align="right"><logic:notEmpty
														name="imovelCliente" property="cliente">
														<bean:define name="imovelCliente" property="cliente"
															id="cliente" />
														<logic:notEmpty name="cliente" property="clienteFones">
															<bean:define name="cliente" property="clienteFones"
																id="clienteFones" />
															<logic:iterate name="clienteFones" id="clienteFone">
																<div class="style9">
																<div align="center"><font color="#000000"
																	style="font-size:9px"
																	face="Verdana, Arial, Helvetica, sans-serif"> </font></div>
																<bean:write name="clienteFone" property="dddTelefone" />
																</div>
															</logic:iterate>
														</logic:notEmpty>
													</logic:notEmpty></td>
													<td width="20%" align="right"><font color="#000000"
														style="font-size:9px"
														face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
														name="imovelCliente" property="cliente.cpf">
														<bean:write name="imovelCliente"
															property="cliente.cpfFormatado" />
													</logic:notEmpty> <logic:notEmpty name="imovelCliente"
														property="cliente.cnpj">
														<bean:write name="imovelCliente"
															property="cliente.cnpjFormatado" />
													</logic:notEmpty> </font></td>
												</tr>
											</logic:iterate>
										</logic:notEmpty>
									</table>
									</div>
									</td>
								</tr>

							</table>
							</td>
						</tr>
				
				</table>
				

						<%
								String pctQtdColunasContas="10";
						
								if (session.getAttribute("exibirDividaAtivaColuna") != null) {
									pctQtdColunasContas      = "11";
								}						
						%>
				<tr>
					<td colspan="4">
					<table width="100%" align="center" bgcolor="#90c7fc" border="0">
						<%String cor = "#cbe5fe";%>
						<%cor = "#cbe5fe";%>
						<tr bordercolor="#79bbfd">
							<td colspan="<%= pctQtdColunasContas%>" align="center" bgcolor="#79bbfd" width="100%">
							<strong>Contas</strong>
							</td>
						</tr>
					</table>
					<table width="100%" align="center" bgcolor="#90c7fc" border="0">	
			<!-- Inicio do bloco das coleções de Conta. -->
			<logic:notEmpty name="colecaoContaValores" scope="session">
						
						<%
						boolean isColecaoComQtdeMAIORQueMaximoRegistroContaDebito = true;
						
						String pctMesAnoTitle   = "9%";
						String pctVencimento    = "12%";
						String pctAgua          = "9%";
						String pctEsgoto        = "9%";
						String pctDebito        = "10%";
						String pctCreditos 		= "9%";
						String pctImpostos 		= "9%";
						String pctConta      	= "10%";
						String pctAcrecimoImpon = "9%";
						String pctSituacao      = "14%";
						String pctDividaAtiva   = "0%";
						
						if (session.getAttribute("exibirDividaAtivaColuna") != null) {
							pctSituacao      = "9%";
							pctDividaAtiva   = "5%";
						}
						
						String alinhamentoMesAno = "center";
						String alinhamentoVencimento = "center";
						
						
						if (((Collection) session.getAttribute("colecaoContaValores")).size() <= ConstantesSistema.NUMERO_MAXIMO_REGISTROS_CONTAS_DEBITO) {
							isColecaoComQtdeMAIORQueMaximoRegistroContaDebito = false;
							pctMesAnoTitle   = "9%"; 
							pctVencimento    = "12%"; 
							pctAgua          = "9%";
							pctEsgoto        = "9%";
							pctDebito        = "10%";
							pctCreditos 	 = "9%";
							pctImpostos 	 = "9%";
							pctConta      	 = "10%";
							pctAcrecimoImpon = "9%";
							pctSituacao      = "14%";
							pctDividaAtiva   = "0%";
							
							if (session.getAttribute("exibirDividaAtivaColuna") != null) {
								pctSituacao      = "9%";
								pctDividaAtiva   = "5%";
							}							
							
						 } %>
						
			<!-- INICIO CABEÇALHO CONTAS -->						
			<tr bordercolor="#000000">
				<td width="<%=pctMesAnoTitle%>" bgcolor="#90c7fc" align="center">
					<div align="center" class="style9">
						<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
							<strong>M&ecirc;s/Ano</strong>
						</font>
					</div>
				</td>
				
				<td width="<%=pctVencimento%>" bgcolor="#90c7fc" align="center">
					<div align="center" class="style9">
						<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
							<strong>Vencimento</strong>
						</font>
					</div>
				</td>
				
				<td width="<%= pctAgua%>" bgcolor="#90c7fc" align="center">
					<div align="center" class="style9">
						<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
							<strong>Valor de &Aacute;gua </strong> 
						</font>
					</div>
				</td>
				
				<td width="<%= pctEsgoto%>" bgcolor="#90c7fc" align="center">
					<div align="center" class="style9">
						<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
							<strong>Valor de Esgoto</strong> 
						</font>
					</div>
				</td>
				
				<td width="<%=pctDebito%>" bgcolor="#90c7fc" align="center">
					<div align="center" class="style9">
						<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
							<strong>Valor dos <br> D&eacute;bitos</strong> 
						</font>
					</div>
				</td>
				
				<td width="<%=pctCreditos%>" bgcolor="#90c7fc" align="center">
					<div align="center" class="style9">
						<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
							<strong>Valor dos Creditos</strong>
						</font>
					</div>
				</td>
				
				<td width="<%= pctImpostos%>" bgcolor="#90c7fc" align="center">
				  <div align="center" class="style9">
					<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
					  <strong>Valor dos	Impostos</strong> 
					</font>
				  </div>
				</td>

				<td width="<%=pctConta %>" bgcolor="#90c7fc" align="center">
					<div align="center" class="style9">
						<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
						<strong>Valor da Conta</strong> 
						</font>
					</div>
				</td>
				
				<td width="<%= pctAcrecimoImpon%>" bgcolor="#90c7fc" align="center">
					<div align="center" class="style9">
						<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
							<strong>Acr&eacute;sc. Impont.</strong>
						</font>
					</div>
				</td>
				
				<td width="<%= pctSituacao%>" bgcolor="#90c7fc" align="center">
					<div align="center" class="style9">
						<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
							<strong>Sit.</strong>
						</font>
					</div>
				</td>
				
				<logic:present name="exibirDividaAtivaColuna" scope="session">
					<td width="<%= pctDividaAtiva%>" bgcolor="#90c7fc" align="center">
						<div align="center" class="style9">
							<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
								<strong>Dívida Ativa</strong>
							</font>
						</div>
					</td>				
				</logic:present>
				
			</tr>
			<!-- FIM CABEÇALHO CONTAS -->
						
			
			<!-- Inicio Bloco de Iterações de colecaoContaValores -->			

			<!--Inicio  Condição quando tem mais de 6 registros para aparecer a barra de rolagem  -->
			<% if(isColecaoComQtdeMAIORQueMaximoRegistroContaDebito){ %>
				<td height="100" colspan="<%= pctQtdColunasContas%>">
					<div style="width: 100%; height: 100%; overflow: auto;">
						<table width="100%">
			<% } %>
				
				<logic:iterate name="colecaoContaValores" id="contavaloreshelper">
				<logic:notEmpty name="contavaloreshelper" property="conta">
				
				<%if (cor.equalsIgnoreCase("#cbe5fe")) {
					cor = "#FFFFFF";%>
					<tr bgcolor="#FFFFFF">
				<%} else {
					cor = "#cbe5fe";%>
					<tr bgcolor="#cbe5fe">
				<%}%>
				
				
				<%
				
					Conta contaTemp = ((ContaValoresHelper) contavaloreshelper).getConta();
					String corSituacaoConta = "#000000"; // PRETO
					String corSituacaoContaPrescrita = "#000000"; // PRETO
					
					if(Util.isNaoNuloBrancoZero(contaTemp.getContaMotivoRevisao()) && !Util.isNaoNuloBrancoZero(contaTemp.getIndicadorPagamento())){
						
						corSituacaoConta = "#ff0000"; // VERMELHO
						corSituacaoContaPrescrita = "#ff0000"; // VERMELHO
						
					} else if(Util.isNaoNuloBrancoZero(contaTemp.getIndicadorPagamento())){
						
						corSituacaoConta = "#0000FF"; // AZUL
						corSituacaoContaPrescrita = "#0000FF"; // AZUL
						
					}else if(Util.isNaoNuloBrancoZero(contaTemp.getIndicadorCobrancaAdministrativa())
												&& (contaTemp.getIndicadorCobrancaAdministrativa().equals(ConstantesSistema.INDICADOR_USO_ATIVO))) {
						
						corSituacaoConta = "#008200"; // VERDE
						corSituacaoContaPrescrita = "#008200"; // VERDE
					}
				
					if(contaTemp.getDebitoCreditoSituacaoAtual().getId().toString().equals(DebitoCreditoSituacao.PRESCRITA.toString())){
						
						corSituacaoContaPrescrita = "#ff0000"; // VERMELHO 
					}
				%>
			
				<td width="<%=pctMesAnoTitle%>" align="<%=alinhamentoMesAno %>">
					<div align="<%=alinhamentoMesAno %>" class="style9">
						<font color="<%=corSituacaoConta %>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
							<strong>
								<a href="javascript:abrirPopup('exibirConsultarContaAction.do?contaID=<bean:define name="contavaloreshelper" property="conta" id="conta" />
									<bean:write name="conta" property="id" />&tipoConsulta=conta', 600, 800);">
									<bean:define name="contavaloreshelper" property="conta" id="conta" /> 
									<font color="<%=corSituacaoConta %>"> <!-- Link permanece na mesma cor! -->
										<bean:write name="conta" property="formatarAnoMesParaMesAno" />
									</font>
								</a>
							</strong>
						</font>
					</div>
				</td>
			
				<td width="<%=pctVencimento%>"  align="<%=alinhamentoVencimento %>">
					<div align="center">
						<font color="<%=corSituacaoConta %>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
							<bean:define name="contavaloreshelper" property="conta" id="conta" /> 
							<bean:write name="conta" property="dataVencimentoConta" formatKey="date.format" /> 
						</font>
					</div>			
				</td>
	
				<td width="<%=pctAgua%>"  align="right">
					<div align="right">
						<font color="<%=corSituacaoConta %>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
							<bean:define name="contavaloreshelper" property="conta" id="conta" /> 
							<bean:write name="conta" property="valorAgua" formatKey="money.format" />
						</font>
					</div>
				</td>			
				
				<td width="<%=pctEsgoto%>" align="right">
					<div align="right" class="style9">
						<font color="<%=corSituacaoConta %>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
							<bean:define name="contavaloreshelper" property="conta" id="conta" />
							<bean:write name="conta" property="valorEsgoto" formatKey="money.format" />
						</font>
					</div>
				</td>
				
				<td width="<%=pctDebito%>" align="right">
					<div align="right">
						<font color="<%=corSituacaoConta %>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
							<logic:notEqual name="contavaloreshelper" property="conta.debitos" value="0">
								<a href="javascript:abrirPopup('exibirConsultarDebitoCobradoAction.do?contaID=<bean:define name="contavaloreshelper" property="conta" id="conta" /><bean:write name="conta" property="id" />&tipoConsulta=conta', 600, 800);">
									<font color="<%=corSituacaoConta %>">
										<bean:write name="contavaloreshelper" property="conta.debitos" formatKey="money.format" /> 
									</font>
								</a>
							</logic:notEqual> 
							<logic:equal name="contavaloreshelper" property="conta.debitos" value="0">
								<bean:write name="contavaloreshelper" property="conta.debitos" formatKey="money.format" />
							</logic:equal> 
						</font>
					</div>
				</td>
				
				<td width="<%=pctCreditos%>" align="right">
					<div align="right">
						<font color="<%=corSituacaoConta %>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
							<logic:notEqual name="contavaloreshelper" property="conta.valorCreditos" value="0">	
								<a href="javascript:abrirPopup('exibirConsultarCreditoRealizadoAction.do?contaID=<bean:define name="contavaloreshelper"	property="conta" id="conta" /><bean:write name="conta" property="id" />&tipoConsulta=conta', 600, 800);">
									<font color="<%=corSituacaoConta %>">
										<bean:write name="contavaloreshelper" property="conta.valorCreditos" formatKey="money.format" />
									</font>
								</a>
							</logic:notEqual> 
							<logic:equal name="contavaloreshelper" property="conta.valorCreditos" value="0">
								<font color="<%=corSituacaoConta %>">
									<bean:write name="contavaloreshelper" property="conta.valorCreditos" formatKey="money.format" />
								</font>
							</logic:equal> 
						</font>
					</div>				
				</td>
				
				<td width="<%=pctImpostos%>" align="right">
					<div align="right">
						<font color="<%=corSituacaoConta %>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
							<bean:define name="contavaloreshelper" property="conta" id="conta" /> 
							<bean:write name="conta" property="valorImposto" formatKey="money.format" />
						</font>
					</div>				
				</td>
				
				<td width="<%=pctConta%>" align="right">
					<div align="right">
						<font color="<%=corSituacaoConta %>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
							<strong>
								<bean:define name="contavaloreshelper" property="conta" id="conta" /> 
								<bean:write name="conta" property="valorTotal" formatKey="money.format" />
							</strong> 
						</font>
					</div>
				</td>
				
				<td width="<%=pctAcrecimoImpon %>" align="right">
					<div align="right" class="style9">
						<font color="<%=corSituacaoConta %>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
							<logic:notEqual name="contavaloreshelper" property="valorTotalContaValores" value="0">
								<a href="javascript:abrirPopup('exibirValorAtualizacaoConsultarPopupAction.do?multa=<bean:write name="contavaloreshelper" property="valorMulta" />&juros=<bean:write name="contavaloreshelper" property="valorJurosMora" />&atualizacao=<bean:write name="contavaloreshelper" property="valorAtualizacaoMonetaria" />', 300, 650);">
									<font color="<%=corSituacaoConta %>">
										<bean:write name="contavaloreshelper" property="valorTotalContaValores" formatKey="money.format" />
									</font>
								</a>
							</logic:notEqual> 
							<logic:equal name="contavaloreshelper" property="valorTotalContaValores" value="0">
								<bean:write name="contavaloreshelper" property="valorTotalContaValores" formatKey="money.format" />
							</logic:equal> 
						</font>
					</div>
				</td>
				
				<td width="<%= pctSituacao%>" align="center">
					<div align="center" class="style9">
						<font color="<%=corSituacaoContaPrescrita %>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
							<bean:define name="contavaloreshelper" property="conta" id="conta" />
							<bean:define name="conta" property="debitoCreditoSituacaoAtual" id="debitoCreditoSituacaoAtual" />
							<bean:write name="debitoCreditoSituacaoAtual" property="descricaoAbreviada" />
						</font>
					</div>
				</td>
				
				<logic:present name="exibirDividaAtivaColuna" scope="session">
					<td width="<%= pctDividaAtiva%>" align="center">
						<div align="center" class="style9">
							<font color="<%=corSituacaoConta %>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
								<logic:equal name="contavaloreshelper" property="conta.indicadorDividaAtiva" value="2">
									<logic:equal name="contavaloreshelper" property="conta.indicadorExecucaoFiscal" value="2">
										N
									</logic:equal>
								</logic:equal>
	
								<logic:equal name="contavaloreshelper" property="conta.indicadorDividaAtiva" value="1">
									<logic:equal name="contavaloreshelper" property="conta.indicadorExecucaoFiscal" value="2">
										A
									</logic:equal>
								</logic:equal>
							
								<logic:equal name="contavaloreshelper" property="conta.indicadorExecucaoFiscal" value="1">
									E
								</logic:equal>
							</font>
						</div>
					</td>				
				</logic:present>

				<!-- FIM DO TR que está no bloco de if else -->	
				</tr>
			</logic:notEmpty>
			</logic:iterate>
			
			
			<!-- Inicio Ultima linha de TOTAL -->
			
			<%if (cor.equalsIgnoreCase("#cbe5fe")) {
				cor = "#FFFFFF";%>
				<tr bgcolor="#FFFFFF">
			<%} else {
				cor = "#cbe5fe";%>
				<tr bgcolor="#cbe5fe">
			<%}%>

				<td bgcolor="#90c7fc" align="center">
					<div class="style9" align="center">
						<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
							<strong>Total</strong>
						</font>
					</div>
				</td>

				<td align="left">
					<%=((Collection) session.getAttribute("colecaoContaValores")).size()%> &nbsp; doc(s)
				</td>

				<td align="right">
					<div align="right">
						<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
							<%=session.getAttribute("valorAgua")%>
						</font>
					</div>
				</td>
				
				<td align="right">
					<div align="right">
						<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
							<%=session.getAttribute("valorEsgoto")%>
						</font>
					</div>
				</td>
				
				<td align="right">
					<div align="right">
						<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
							<%=session.getAttribute("valorDebito")%>
						</font>
					</div>
				</td>
				
				<td align="right">
					<div align="right">
						<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
							<%=session.getAttribute("valorCredito")%>
						</font>
					</div>
				</td>
				
				<td align="right">
					<div align="right">
						<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
							<%=session.getAttribute("valorImposto")%>
						</font>
					</div>
				</td>
				
				<td align="right">
					<div align="right">
						<strong>
							<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
								<%=session.getAttribute("valorConta")%>
							</font>
						</strong>
					</div>
				</td>
				
				<td align="right">
					<div align="right" class="style9">
						<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
							<%=session.getAttribute("valorAcrescimo")%>
						</font>
					</div>
				</td>
				
				<td align="left">
					<div align="left">
						<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
						</font>
					</div>
				</td>
			</tr>
			
			<!-- Fim Ultima linha de TOTAL -->

			<!--FIM  Condição quando tem mais de 6 registros para aparecer a barra de rolagem  -->
			<% if(isColecaoComQtdeMAIORQueMaximoRegistroContaDebito){ %>
				
						</table>
					</div>
				</td>

			<% } %>
			

			
			<!-- FIM Bloco de Iterações de colecaoContaValores -->			
						
						
			<!-- FIM do bloco das coleções de Conta Valores "colecaoContaValores". -->						
			</logic:notEmpty>
						
						
						
						<!-- Colocar as legendas e Link de Monter Contas. -->
						<logic:notPresent name="montarPopUp">
						<tr>
						
						
							<td colspan="<%= pctQtdColunasContas%>">
								<div style="width: 100%; height: 100%;">
									<table width="100%">
										<tr>
											<td width="55%">
												<div align="left">
													<a href="javascript:abrirManterContas();">
														<strong>Manter Contas</strong>
											 		</a>
												</div> 	
											</td>
											
											<td width="12%">
												<div align="right">Legenda:</div>
											 </td>
											
											 <td width="33%">
												<div align="left"><font color="#FF0000"> Contas em revisão </font></div>
											</td>
										</tr>
										 <tr>
											 <td width="55%">
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
											 <td width="55%">
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
						
						</logic:notPresent>
					</table>
					</td>
				</tr>
				
				
			<logic:present name="colecaoImovelCobrancaSituacaoAberto" scope="session" >
					<logic:notEmpty name="colecaoImovelCobrancaSituacaoAberto" scope="session" >
						<tr>
							<td colspan="6" width="100%">
								<br>
								<div id="layerHideImovelCobrancaSituacaoAberto" style="display:block">
									<table width="100%" border="0" bgcolor="#99CCFF">
										<tr bgcolor="#99CCFF">
											<td height="18" colspan="4" align="center">
												<span class="style2"> 
													<a href="javascript:extendeTabela('ImovelCobrancaSituacaoAberto',true);"> 
														<b>Situações de Cobrança em Aberto</b>
													</a>
												</span>
											</td>
										</tr>
									</table>
								</div>
								<div id="layerShowImovelCobrancaSituacaoAberto" style="display:none">
									<table width="100%" border="0" bgcolor="#99CCFF">
										<tr bgcolor="#90c7fc">
											<td height="18" colspan="5" align="center">
												<span class="style2"> 
													<a href="javascript:extendeTabela('ImovelCobrancaSituacaoAberto',false);"> 
														<b>Situações de Cobrança em Aberto</b>
													</a>
												</span>
											</td>
										</tr>
									</table>	
									<table width="100%" border="0" bgcolor="#99CCFF">	
			                			<tr bgcolor="#90c7fc"> 
			                  				<td width="51%"><div align="center"><strong>Descrição da Situação</strong></div></td>
			                  				<td width="13%"><div align="center"><strong>Período de Referência</strong></div></td>
			                  				<td width="13%"><div align="center"><strong>Data Implantação</strong></div></td>
			                  				<td width="13%"><div align="center"><strong>Cliente Alvo</strong></div></td>
			                  				<td width="20%"><div align="center"><strong>Processo<br>Administrativo</strong></div></td> 
			                			</tr>
			                			<%cor = "#cbe5fe";%>
										<logic:iterate name="colecaoImovelCobrancaSituacaoAberto"
											id="imovelCobrancaSituacaoAberto" >
											<%if (cor.equalsIgnoreCase("#cbe5fe")) {
							cor = "#FFFFFF";%>
											<tr bgcolor="#FFFFFF">
												<%} else {
							cor = "#cbe5fe";%>
							
											<tr bgcolor="#cbe5fe">
												<%}%>
												<td>
												<div align="left" class="style9"><font color="#000000"
													style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
													name="imovelCobrancaSituacaoAberto" property="cobrancaSituacao.descricao">
													<bean:write name="imovelCobrancaSituacaoAberto" property="cobrancaSituacao.descricao"
														/>
												</logic:notEmpty> </font></div>
												</td>
												<td>
												<div align="center" class="style9"><font color="#000000"
													style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
													name="imovelCobrancaSituacaoAberto" property="anoMesReferenciaInicio">
													<%= Util.formatarAnoMesParaMesAno(((ImovelCobrancaSituacao)imovelCobrancaSituacaoAberto).getAnoMesReferenciaInicio().toString()) %>													
												</logic:notEmpty> </font></div>
												</td>
												<td>
												<div align="center" class="style9"><font color="#000000"
													style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
													name="imovelCobrancaSituacaoAberto" property="dataImplantacaoCobranca">
													<bean:write name="imovelCobrancaSituacaoAberto" property="dataImplantacaoCobranca"
														formatKey="date.format" />
												</logic:notEmpty> </font></div>
												</td>
												
												<td>
												<div align="center" class="style9"><font color="#000000"
													style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
													name="imovelCobrancaSituacaoAberto" property="cliente.id">
													<a
															href="javascript:abrirPopup('exibirConsultarClienteAction.do?desabilitarPesquisaCliente=SIM&codigoCliente='+<bean:write name="imovelCobrancaSituacaoAberto" property="cliente.id" />, 500, 800);">
														<bean:write name="imovelCobrancaSituacaoAberto" property="cliente.id" />
													</a>
												</logic:notEmpty> </font></div>
												</td>												
													
												<td>
												<div align="center" class="style9"><font color="#000000"
													style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
													name="imovelCobrancaSituacaoAberto" property="numeroProcessoAdministrativoExecucaoFiscal">
													<bean:write name="imovelCobrancaSituacaoAberto" property="numeroProcessoAdministrativoExecucaoFiscal"
														 />
												</logic:notEmpty> </font></div>
												</td>																									
											</tr>
										</logic:iterate>							
									</table>
								</div>				
							</td>
						</tr>
					</logic:notEmpty>
				</logic:present>					
				
				
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>				
				<tr>

					<td align="left"><strong>Situa&ccedil;&atilde;o de Cobran&ccedil;a:</strong></td>

					<td colspan="3" align="left"><html:text
						property="situacaoCobrancaDadosComplementares" readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000"
						size="50" maxlength="50" /></td>

				</tr>
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>
				
				<tr>
					<td colspan="4">
					<table width="100%" align="center" bgcolor="#90c7fc" border="0">
						<tr bordercolor="#79bbfd">
							<td colspan="6" bgcolor="#79bbfd" align="center"><strong>D&eacute;bitos
							A Cobrar</strong></td>
						</tr>
						<tr bordercolor="#000000">
							<td width="50%" bgcolor="#90c7fc">
							<div align="center" class="style9"><font color="#000000"
								style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Tipo do
							D&eacute;bito</strong> </font></div>
							</td>
							<td width="13%" bgcolor="#90c7fc">
							<div align="center" class="style9"><font color="#000000"
								style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>M&ecirc;s/Ano
							Refer&ecirc;ncia</strong> </font></div>
							</td>
							<td width="10%" bgcolor="#90c7fc">
							<div align="center" class="style9"><font color="#000000"
								style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>M&ecirc;s/Ano
							Cobran&ccedil;a</strong> </font></div>
							</td>
							<td width="10%" bgcolor="#90c7fc">
							<div align="center" class="style9"><font color="#000000"
								style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Parcelas a
							cobrar</strong> </font></div>
							</td>
							<td width="10%" bgcolor="#90c7fc" height="20">
							<div align="center" class="style9"><font color="#000000"
								style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Dias Suspensão Lançamento</strong> </font></div>
							</td>
							<td width="17%" bgcolor="#90c7fc" height="20">
							<div align="center" class="style9"><font color="#000000"
								style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor a
							cobrar</strong> </font></div>
							</td>
						</tr>
						<%cor = "#cbe5fe";%>
						
						
						
						
						
						<logic:present name="colecaoDebitoACobrar">
							<logic:iterate name="colecaoDebitoACobrar" id="debitoacobrar">
								<%if (cor.equalsIgnoreCase("#cbe5fe")) {
				cor = "#FFFFFF";%>
								<tr bgcolor="#FFFFFF">
									<%} else {
				cor = "#cbe5fe";%>
								<tr bgcolor="#cbe5fe">
									<%}%>
									<td>
									<div align="left" class="style9"><font color="#000000"
										style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
										name="debitoacobrar" property="imovel">
										<a
											href="javascript:abrirPopup('exibirConsultarDebitoACobrarAction.do?imovelID=<bean:define name="debitoacobrar" property="imovel" id="imovel" /><bean:write name="imovel" property="id" />&debitoID=<bean:write name="debitoacobrar" property="id" />&debitosAgrupados=<c:out value="${debitosAgrupados}"/>', 570, 720);">
										<bean:define name="debitoacobrar" property="debitoTipo"
											id="debitoTipo" /> <logic:notEmpty name="debitoTipo"
											property="descricao">
											<bean:write name="debitoTipo" property="descricao" />
										</logic:notEmpty> </a>
									</logic:notEmpty> </font></div>
									</td>
									<td>
									<div align="center" class="style9"><font color="#000000"
										style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
										name="debitoacobrar" property="anoMesReferenciaDebito">
										<%= Util.formatarAnoMesParaMesAno(((DebitoACobrar)debitoacobrar).getAnoMesReferenciaDebito().toString()) %>
									</logic:notEmpty> </font></div>
									</td>
									<td>
									<div align="center" class="style9"><font color="#000000"
										style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
										name="debitoacobrar" property="anoMesCobrancaDebito">
										<%= Util.formatarAnoMesParaMesAno(((DebitoACobrar)debitoacobrar).getAnoMesCobrancaDebito().toString()) %>
									</logic:notEmpty> </font></div>
									</td>
									<td>
									<div align="center" class="style9"><font color="#000000"
										style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
										name="debitoacobrar" property="parcelasRestante">
										<bean:write name="debitoacobrar" property="parcelasRestante" />
									</logic:notEmpty> </font></div>
									</td>
									<td>
									<div align="center" class="style9"><font color="#000000"
										style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
										name="debitoacobrar" property="numeroDiasSuspensao">
										<bean:write name="debitoacobrar" property="numeroDiasSuspensao"/>
									</logic:notEmpty> </font></div>
									</td>
									<td>
										<div align="right" class="style9">
										
											<font color="#000000" style="font-size:9px"	face="Verdana, Arial, Helvetica, sans-serif"> 
												
												<logic:notEmpty	name="debitoacobrar" property="valorTotal">
												
													<bean:write name="debitoacobrar" property="valorTotal"	formatKey="money.format" />
											
												</logic:notEmpty>
												 
											</font>
											
										</div>									
									</td>
								</tr>
							</logic:iterate>
													
							<logic:notEmpty name="colecaoDebitoACobrar">
								<%if (cor.equalsIgnoreCase("#cbe5fe")) {
				cor = "#FFFFFF";%>
								<tr bgcolor="#FFFFFF">
									<%} else {
				cor = "#cbe5fe";%>
								<tr bgcolor="#cbe5fe">
									<%}%>
									<td bgcolor="#90c7fc">
									<div align="center" class="style9"><font color="#000000"
										style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <strong>Total</strong>
									</font></div>
									</td>
									<td>
									<%=((Collection) session.getAttribute("colecaoDebitoACobrar")).size()%>
									&nbsp;
									doc(s)
									</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>
									<div align="right" class="style9"><font color="#000000"
										style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <%=session.getAttribute("valorDebitoACobrar")%>
									</font></div>
									</td>
								</tr>
							</logic:notEmpty>
						</logic:present>
						
						
						  <logic:notPresent name="montarPopUp">
						<tr>
						   <td colspan="3">
								<div align="left">
									<a href="javascript:abrirInserirDebitoACobrar();">
										<strong>Inserir Débito a Cobrar</strong>
							 		</a>
								</div> 	
							</td>
						</tr>
						</logic:notPresent>
					</table>
					</td>
				</tr>
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>				
				
				<tr>
					<td colspan="4">
					<table width="100%" align="center" bgcolor="#90c7fc" border="0">
						<tr bordercolor="#79bbfd">
							<td colspan="5" bgcolor="#79bbfd" align="center"><strong>Cr&eacute;ditos
							A Realizar</strong></td>
						</tr>
						<tr bordercolor="#000000">
							<td width="53%" bgcolor="#90c7fc" height="20">
							<div align="center" class="style9"><font color="#000000"
								style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Tipo do
							Cr&eacute;dito</strong> </font></div>
							</td>
							<td width="10%" bgcolor="#90c7fc">
							<div align="center" class="style9"><font color="#000000"
								style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>M&ecirc;s/Ano
							Refer&ecirc;ncia</strong> </font></div>
							</td>
							<td width="10%" bgcolor="#90c7fc">
							<div align="center" class="style9"><font color="#000000"
								style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>M&ecirc;s/Ano
							Cobran&ccedil;a</strong> </font></div>
							</td>
							<td width="10%" bgcolor="#90c7fc">
							<div align="center" class="style9"><font color="#000000"
								style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Parcelas a
							creditar</strong> </font></div>
							</td>
							<td width="17%" bgcolor="#90c7fc" height="20">
							<div align="center" class="style9"><font color="#000000"
								style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor a
							creditar</strong> </font></div>
							</td>
						</tr>
						<%cor = "#cbe5fe";%>
						<logic:present name="colecaoCreditoARealizar">
							<logic:iterate name="colecaoCreditoARealizar"
								id="creditoarealizar">
								<%if (cor.equalsIgnoreCase("#cbe5fe")) {
				cor = "#FFFFFF";%>
								<tr bgcolor="#FFFFFF">
									<%} else {
				cor = "#cbe5fe";%>
								<tr bgcolor="#cbe5fe">
									<%}%>
									<td><logic:notEmpty name="creditoarealizar"
										property="creditoTipo">
										<div align="left" class="style9"><font color="#000000"
											style="font-size:9px"
											face="Verdana, Arial, Helvetica, sans-serif"> <a
											href="javascript:abrirPopup('exibirConsultarCreditoARealizarAction.do?imovelID=<bean:define name="creditoarealizar" property="imovel" id="imovel" /><bean:write name="imovel" property="id" />&creditoID=<bean:write name="creditoarealizar" property="id" />', 570, 720);">
										<bean:define name="creditoarealizar" property="creditoTipo"
											id="creditoTipo" /> <logic:notEmpty name="creditoTipo"
											property="descricao">
											<bean:write name="creditoTipo" property="descricao" />
										</logic:notEmpty> </a> </font></div>
									</logic:notEmpty></td>
									<td>
									<div align="center" class="style9"><font color="#000000"
										style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
										name="creditoarealizar" property="anoMesReferenciaCredito">
										<bean:write name="creditoarealizar"
											property="formatarAnoMesReferenciaCredito" />
									</logic:notEmpty> </font></div>
									</td>
									<td>
									<div align="center" class="style9"><font color="#000000"
										style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
										name="creditoarealizar" property="anoMesCobrancaCredito">
										<bean:write name="creditoarealizar"
											property="formatarAnoMesCobrancaCredito" />
									</logic:notEmpty> </font></div>
									</td>
									<td>
									<div align="center" class="style9"><font color="#000000"
										style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
										name="creditoarealizar" property="parcelasRestante">
										<bean:write name="creditoarealizar"
											property="parcelasRestante" />
									</logic:notEmpty> </font></div>
									</td>
									<td>
									<div align="right" class="style9"><font color="#000000"
										style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
										name="creditoarealizar" property="valorTotal">
										<bean:write name="creditoarealizar" property="valorTotal"
											formatKey="money.format" />
									</logic:notEmpty> </font></div>
									</td>
								</tr>
							</logic:iterate>
							<logic:notEmpty name="colecaoCreditoARealizar">
								<%if (cor.equalsIgnoreCase("#cbe5fe")) {
				cor = "#FFFFFF";%>
								<tr bgcolor="#FFFFFF">
									<%} else {
				cor = "#cbe5fe";%>
								<tr bgcolor="#cbe5fe">
									<%}%>
									<td bgcolor="#90c7fc">
									<div align="center" class="style9"><font color="#000000"
										style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <strong>Total</strong>
									</font></div>
									</td>
									<td>
									<%=((Collection) session.getAttribute("colecaoCreditoARealizar")).size()%>
									&nbsp;
									doc(s)
									</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>
									<div align="right" class="style9"><font color="#000000"
										style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <%=session.getAttribute("valorCreditoARealizar")%>
									</font></div>
									</td>
								</tr>
							</logic:notEmpty>
						</logic:present>
						  <logic:notPresent name="montarPopUp">
						<tr>
						   <td colspan="3">
								<div align="left">
									<a href="javascript:abrirInserirCreditoARealizar();">
										<strong>Inserir Crédito A Realizar</strong>
							 		</a>
								</div> 	
							</td>
						</tr>
						</logic:notPresent>
					</table>
					</td>
				</tr>
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>				
				
				<tr>
						<%
								String pctQtdColunasGuias="6";
						
								String pctNumeroGuia    = "15%";
								String pctNumeroParcela = "15%";
								String pctDataEmissao   = "18%";
								String pctDataVencimento = "18%";
								String pctValorParcela   = "20%";
								String pctSituacaoGuia 	 = "14%";
								String pctDividaAtivaGuia  = "0%";
						
								if (session.getAttribute("exibirDividaAtivaColuna") != null) {
									pctNumeroGuia    = "14%";
									pctNumeroParcela = "15%";
									pctDataEmissao   = "17%";
									pctDataVencimento = "17%";
									pctValorParcela   = "16%";
									pctSituacaoGuia 	 = "13%";
									pctDividaAtivaGuia  = "8%";
									
									pctQtdColunasGuias      = "7";
								}						
						%>
										
					<td colspan="5">
					<table width="100%" align="center" bgcolor="#90c7fc" border="0">
						<tr bordercolor="#79bbfd">
							<td colspan="<%= pctQtdColunasGuias%>" bgcolor="#79bbfd" align="center"><strong>Guias de Pagamento</strong></td>
						</tr>
						<tr bordercolor="#000000">
							<td width="<%= pctNumeroGuia%>" bgcolor="#90c7fc"><div align="center" class="style9"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> <strong>Número da Guia</strong> </font></div></td>							
							<td width="<%= pctNumeroParcela%>" bgcolor="#90c7fc"><div align="center" class="style9"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> <strong>Número Parcela</strong> </font></div></td>
							<td width="<%= pctDataEmissao%>" bgcolor="#90c7fc"><div align="center" class="style9"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> <strong>Data de Emiss&atilde;o</strong> </font></div></td>
							<td width="<%= pctDataVencimento%>" bgcolor="#90c7fc"><div align="center" class="style9"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> <strong>Data de Vencimento</strong> </font></div></td>
							<td width="<%= pctValorParcela%>" bgcolor="#90c7fc"><div align="center" class="style9"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor da Parcela</strong> </font></div></td>
							<td width="<%= pctSituacaoGuia%>" bgcolor="#90c7fc"><div align="center" class="style9"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> <strong>Situação Guia</strong> </font></div></td>
							
							<logic:present name="exibirDividaAtivaColuna" scope="session">
								<td width="<%= pctDividaAtivaGuia%>" bgcolor="#90c7fc" align="center">
									<div align="center" class="style9">
										<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
											<strong>Dívida Ativa</strong>
										</font>
									</div>
								</td>				
							</logic:present>							
						</tr>
						<%cor = "#cbe5fe";%>
						
						<logic:present name="colecaoGuiaPagamentoValores">
							<logic:iterate name="colecaoGuiaPagamentoValores" id="guiapagamentohelper">
									
									
						<%
						
							GuiaPagamentoValoresHelper guiaPgtoHelper = ((GuiaPagamentoValoresHelper) guiapagamentohelper);
						
							String corSituacaoGuia = "#000000"; // PRETO
							String corSituacaoGuiaPrescrita = "#000000"; // PRETO
							
							if(Util.isNaoNuloBrancoZero(guiaPgtoHelper.getDesativarSelecao()) 
														&& guiaPgtoHelper.getDesativarSelecao().equals(ConstantesSistema.INDICADOR_USO_ATIVO)){
								
								corSituacaoGuia = "#ff0000"; // VERMELHO
								corSituacaoGuiaPrescrita = "#ff0000"; // VERMELHO
								
							}else if(Util.isNaoNuloBrancoZero(guiaPgtoHelper.getIndicadorCobrancaAdministrativa())
														&& (guiaPgtoHelper.getIndicadorCobrancaAdministrativa().equals(ConstantesSistema.INDICADOR_USO_ATIVO))) {
								
								corSituacaoGuia = "#008200"; // VERDE
								corSituacaoGuiaPrescrita = "#008200"; // VERDE
							}
							
							if(guiaPgtoHelper.getIdDebitoCreditoSituacaoAtual().toString().equals(DebitoCreditoSituacao.PRESCRITA.toString())) {
			
								corSituacaoGuiaPrescrita = "#ff0000"; // VERMELHO
							}
						%>									
									
								<%if (cor.equalsIgnoreCase("#cbe5fe")) {
									cor = "#FFFFFF";%>
									<tr bgcolor="#FFFFFF">
								<%} else {
									cor = "#cbe5fe";%>
									<tr bgcolor="#cbe5fe">
								<%}%>
									<td>
										<div align="right" class="style9">
											<font color="<%=corSituacaoGuia%>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
												<logic:notPresent name="ehPopup">
													<a href="javascript:abrirPopup('exibirConsultarGuiaPagamentoAction.do?guiaPagamentoId=<bean:define name="guiapagamentohelper" property="idGuiaPagamento" id="idGuiaPagamento" /><bean:write name="guiapagamentohelper" property="idGuiaPagamento" />', 600, 900);">
												</logic:notPresent>
												<logic:present name="ehPopup">
													<a href="javascript:abrirPopup('exibirConsultarGuiaPagamentoAction.do?caminhoRetornoTelaConsultaGuiaPagamento=exibirConsultarContaAction&guiaPagamentoId=<bean:define name="guiapagamentohelper" property="idGuiaPagamento" id="idGuiaPagamento" /><bean:write name="guiapagamentohelper" property="idGuiaPagamento" />', 600, 900);">
												</logic:present>
												<font color="<%=corSituacaoGuia%>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
														<bean:write name="guiapagamentohelper" property="idGuiaPagamento" />
												</font>
											</font>
										</div>
									</td>

									<td>
										<div align="center" class="style9">
											<font color="<%=corSituacaoGuia%>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
													<bean:write name="guiapagamentohelper" property="numeroPrestacao" />
											</font>
										</div>
									</td>

									<td>
										<div align="center" class="style9">
											<font color="<%=corSituacaoGuia%>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
												<bean:write name="guiapagamentohelper" property="dataEmissao" formatKey="date.format" />  
											</font>
										</div>
									</td>

									<td>
										<div align="center" class="style9">
											<font color="<%=corSituacaoGuia%>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
												<bean:write name="guiapagamentohelper" property="dataVencimento" formatKey="date.format" />  
											</font>
										</div>
									</td>

									<td>
										<div align="right" class="style9">
											<font color="<%=corSituacaoGuia%>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
												<bean:write name="guiapagamentohelper" property="valorTotalPrestacao" formatKey="money.format" />
											</font>
										</div>
									</td>
									
									<td>
										<div align="center" class="style9">
											<font color="<%=corSituacaoGuiaPrescrita%>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
													<bean:write name="guiapagamentohelper" property="descricaoDebitoCreditoSituacaoAtual" />
											</font>
										</div>
									</td>
									
									<logic:present name="exibirDividaAtivaColuna" scope="session">
										<td align="center">
											<div align="center" class="style9">
												<font color="<%=corSituacaoGuia %>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
													<logic:equal name="guiapagamentohelper" property="indicadorDividaAtiva" value="2">
														<logic:equal name="guiapagamentohelper" property="indicadorExecucaoFiscal" value="2">
															N
														</logic:equal>
													</logic:equal>
						
													<logic:equal name="guiapagamentohelper" property="indicadorDividaAtiva" value="1">
														<logic:equal name="guiapagamentohelper" property="indicadorExecucaoFiscal" value="2">
															A
														</logic:equal>
													</logic:equal>
												
													<logic:equal name="guiapagamentohelper" property="indicadorExecucaoFiscal" value="1">
														E
													</logic:equal>
												</font>
											</div>
										</td>				
									</logic:present>										
								</tr>
							</logic:iterate>
							
							<logic:notEmpty name="colecaoGuiaPagamentoValores">
								<%if (cor.equalsIgnoreCase("#cbe5fe")) {
									cor = "#FFFFFF";%>
									<tr bgcolor="#FFFFFF">
								<%} else {
									cor = "#cbe5fe";%>
									<tr bgcolor="#cbe5fe">
								<%}%>
									<td bgcolor="#90c7fc">
										<div align="center" class="style9">
											<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
												<strong>Total</strong>
											</font>
										</div>
									</td>
									<td><%=((Collection) session.getAttribute("colecaoGuiaPagamentoValores")).size()%> &nbsp; doc(s)</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>
										<div align="right" class="style9">
											<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
												<%=session.getAttribute("valorGuiaPagamento")%> 
											</font>
										</div>
									</td>
								</tr>
							</logic:notEmpty>
						</logic:present>
						
						<logic:notPresent name="montarPopUp">		
							<tr>
							   <td colspan="6">
							   <div style="width: 100%; height: 100%;">
									<table width="100%">
										<tr>
											<td width="55%">
												<div align="left">
													<a href="javascript:abrirInserirGuiaPagamento();">
														<strong>Inserir Guia de Pagamento</strong>
											 		</a>
												</div> 	
											</td>
											
											<td width="12%">
												<div align="right">Legenda:</div>
											 </td>
											
											 <td width="33%">
												<div align="left"><font color="#FF0000"> Guias em cobrança bancária </font></div>
											</td>
										</tr>
										 <tr>
											 <td width="55%">
												<div align="right">&nbsp;</div>
											 </td>
											 <td width="12%">
												<div align="right">&nbsp;</div>
											 </td>
											 <td width="33%">
												<div align="left"><font color="#008200"> Guias em cobrança administrativa</font></div>
											</td>
										</tr>
									</table>
								</div>
								</td>
							</tr>
						</logic:notPresent>
						<tr>
						</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>				
				<tr>
					<td colspan="4">
					<table width="100%" align="center" bgcolor="#90c7fc" border="0">
						<tr bordercolor="#000000">
							<td width="50%" bgcolor="#90c7fc">
							<div align="center" class="style9"><font color="#000000"
								style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor
							Total dos Débitos</strong> </font></div>
							</td>
							<td width="50%" bgcolor="#90c7fc">
							<div align="center" class="style9"><font color="#000000"
								style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor
							Total dos Débitos Atualizado</strong> </font></div>
							</td>
						</tr>
						<%if((session.getAttribute("valorTotalSemAcrescimo")!= null) || (session.getAttribute("valorTotalComAcrescimo") != null)){ %>
							<tr bgcolor="#FFFFFF">
								<td>
								<div align="right" class="style9"><font color="#000000"
									style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <%=session.getAttribute("valorTotalSemAcrescimo")!= null?session.getAttribute("valorTotalSemAcrescimo"):""%>
								</font></div>
								</td>
								<td>
								<div align="right" class="style9"><font color="#000000"
									style="font-size:9px"
									face="Verdana, Arial, Helvetica, sans-serif"> <%=session.getAttribute("valorTotalComAcrescimo")!=null?session.getAttribute("valorTotalComAcrescimo"):""%>
								</font></div>
								</td>
							</tr>
						<%} %>
					</table>
					</td>
				</tr>
				<tr>
					<td align="right">
						  <div align="right">
							   <a href="javascript:verificarExibicaoRelatorio();">
							   	<img border="0" src="<bean:message key="caminho.imagens"/>print.gif" title="Imprimir Débitos" /> 
							   </a>
						  </div>
					</td>
				</tr>
				
				<tr>
					<td height="23">
						<table width="100%">
							<tr>  
								<logic:empty name="colecaoContaValores">
									<td align="right">
										<input type="button" name="" value="Extrato de Débito Completo" class="bottonRightCol"  disabled="true"/>
									</td>
								</logic:empty>
								<logic:notEmpty name="colecaoContaValores">
									<logic:present name="indicadorEmitirExtratoDebitos">
										<td align="right">											
											<input type="button" name="" value="Extrato de Débito Completo" class="bottonRightCol"  disabled="true" >
										</td>
									</logic:present>
									<logic:notPresent name="indicadorEmitirExtratoDebitos">
										<td align="right">											
											<input type="button" name="" value="Extrato de Débito Completo" class="bottonRightCol" onclick="gerarExtratoDebito()" >
										</td>
									</logic:notPresent>
								</logic:notEmpty>
							</tr>
						</table>
					</td>			
				</tr>
			
				
			</table>
			
		
			
			<p>&nbsp;</p>
			<table width="100%" border="0">
			  <logic:notPresent name="montarPopUp">
				<tr>
					<logic:notPresent name="indicadorEfetuarParcelamento">
						<td colspan="3">
							<div align="left">
								<a href="javascript:abrirEfetuarParcelamento();">
										<strong>Efetuar Parcelamento</strong>
							 	</a>
							</div> 	
						</td>
					</logic:notPresent>
					<logic:present name="indicadorEfetuarParcelamento">
						<td colspan="3">
							<div align="left">
								<a href="javascript:abrirEfetuarParcelamento();"  disabled="true" onclick="return false">
										<strong>Efetuar Parcelamento</strong>
							 	</a>
							</div> 	
						</td>
					</logic:present>
				</tr>
				
					<logic:notPresent name="indicadorEmitirExtratoDebitosSelecao">
						<tr>
							<td colspan="3">
								<div align="left">
									<a href="javascript:abrirPopupExtratoDebito();">
											<strong>Extrato de Débito com Seleção</strong>
								 	</a>
								</div> 	
							</td>
						</tr>
					</logic:notPresent>
					
					<logic:present name="indicadorEmitirExtratoDebitosSelecao"> 
						<tr>
							<td colspan="3">
								<div align="left">
									<a href="javascript:abrirPopupExtratoDebito();" disabled="true" onclick="return false">
											<strong>Extrato de Débito com Seleção</strong>
								 	</a>
								</div> 	
							</td>
						</tr>
					</logic:present>
				
				</logic:notPresent>
				<!-- <tr>
					<td colspan="3">
						<div align="left">
							<a href="javascript:abrirPopupExtratoDebito();">
									<strong>Extrato de Débito com Seleção</strong>
						 	</a>
						</div> 	
					</td>
				</tr> -->

				<logic:present name="indicadorEmiteAvisoEOrdemCorteIndividual" scope="session">
				<tr>
					<logic:notPresent name="indicadorGerarArquivoEOrdemCorte">
						<td colspan="3">
							<div align="left">
								<a href="javascript:gerarRelatorioAvisoEOrdemCorteIndividual();">
										<strong>Emitir Aviso de Corte / Ordem de Corte</strong>
							 	</a>
							</div> 	
						</td>
					</logic:notPresent>
					<logic:present name="indicadorGerarArquivoEOrdemCorte">
						<td colspan="3">
							<div align="left">
								<a href="javascript:gerarRelatorioAvisoEOrdemCorteIndividual();" disabled="true" onclick="return false">
										<strong>Emitir Aviso de Corte / Ordem de Corte</strong>
							 	</a>
							</div> 	
						</td>
					</logic:present>
				</tr>
				</logic:present>

				<tr>
					<td colspan="2">
					<div align="right"><jsp:include
						page="/jsp/util/wizard/navegacao_botoes_wizard_consulta.jsp?numeroPagina=5" /></div>
					</td>
				</tr>
			</table>
			</td>
		</tr>		
		
	</table>
	<jsp:include
		page="/jsp/relatorio/escolher_tipo_relatorio.jsp?relatorio=gerarRelatorioDebitosConsultarAction.do" />		
		
		<logic:notPresent name="montarPopUp">	
		<%@ include file="/jsp/util/rodape.jsp"%>
		</logic:notPresent>
	<p>&nbsp;</p>
	<!-- Fim do Corpo - Fernanda Paiva -->
	
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
</body>
<!-- imovel_consultar_debitos.jsp -->
</html:html>
