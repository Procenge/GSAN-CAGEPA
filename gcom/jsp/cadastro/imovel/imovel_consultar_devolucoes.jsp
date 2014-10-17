
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>
<%@page import="gcom.arrecadacao.Devolucao"%>
<%@page import="gcom.arrecadacao.DevolucaoHistorico"%>
<%@ page import="gcom.util.ConstantesSistema" isELIgnored="false"%>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<!--

function fechar(){
		window.close();
-->

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>


<script language="JavaScript">
function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

   	var form = document.forms[0];

    if (tipoConsulta == 'imovel') {
      form.idImovelDevolucoesImovel.value = codigoRegistro;
      form.matriculaImovelDevolucoesImovel.value = descricaoRegistro;
      form.matriculaImovelDevolucoesImovel.style.color = "#000000";
	  form.action = 'consultarImovelWizardAction.do?action=exibirConsultarImovelDevolucoesAction&indicadorNovo=OK'
	  form.submit();
    }
    
}

function limparForm(){
   	var form = document.forms[0];
	form.action = 'consultarImovelWizardAction.do?action=exibirConsultarImovelDevolucoesAction&limparForm=OK'
	form.submit();
}

function abrirInserirDevolucao(){
	var form = document.forms[0];
    
    if (form.idImovelDevolucoesImovel.value == '') {
		alert('Informe a matrícula do imóvel.');
	} else {
		abrirPopupDeNome('exibirInserirDevolucoesAction.do?menu=nao', 400, 800, 'InserirDevolucao', 'yes');
	}	
}
	
</script>


</head>
<body leftmargin="5" topmargin="5" onload="javascript:setarFoco('idImovelDevolucoesImovel')">


<html:form action="/exibirConsultarImovelAction.do"
	name="ConsultarImovelActionForm"
	type="gcom.gui.cadastro.imovel.ConsultarImovelActionForm" method="post"
	onsubmit="return validateConsultarImovelActionForm(this);">

	<jsp:include
		page="/jsp/util/wizard/navegacao_abas_wizard_consulta.jsp?numeroPagina=7" />

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

		<!-- Início do Corpo - Fernanda Paiva  07/02/2006  -->
		<table width="100%" border="0">
				<tr>
					<td colspan="4">
					<table width="100%" align="center" bgcolor="#99CCFF" border="0">
						<tr>
							<td align="center"><strong>Dados do Imóvel</strong></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td width="100%" align="center">
							<table width="100%" border="0">
								<tr>
									<td bordercolor="#000000" width="25%"><strong>Im&oacute;vel:<font
										color="#FF0000">*</font></strong></td>
									<td width="75%" colspan="3"><html:text 
										property="idImovelDevolucoesImovel" maxlength="9" size="9"
										
										onkeypress="validaEnterComMensagem(event, 
										'consultarImovelWizardAction.do?action=exibirConsultarImovelDevolucoesAction&indicadorNovo=OK&limparForm=S',
										'idImovelDevolucoesImovel','Im&oacute;vel');"
										/> 
									<a
										href="javascript:abrirPopup('exibirPesquisarImovelAction.do', 400, 800);">
									<img width="23" height="21"
										src="<bean:message key="caminho.imagens"/>pesquisa.gif"
										border="0" /></a> <logic:present
										name="idImovelDevolucoesImovelNaoEncontrado" scope="request">
										<html:text property="matriculaImovelDevolucoesImovel" size="40"
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: #ff0000" />

									</logic:present> <logic:notPresent
										name="idImovelDevolucoesImovelNaoEncontrado" scope="request">
										<logic:present name="valorMatriculaImovelDevolucoesImovel"
											scope="request">
											<html:text property="matriculaImovelDevolucoesImovel"
												size="40" readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000" />
										</logic:present>
										<logic:notPresent name="valorMatriculaImovelDevolucoesImovel"
											scope="request">
											<html:text property="matriculaImovelDevolucoesImovel"
												size="40" readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000" />
										</logic:notPresent>
									</logic:notPresent> <a href="javascript:limparForm();"> <img
										src="<bean:message key="caminho.imagens"/>limparcampo.gif"
										border="0" title="Apagar" /></a></td>

								</tr>
								<tr>
									<td height="10">
									<div class="style9"><strong>Situação de Água:</strong></div>
									</td>
									<td><html:text property="situacaoAguaDevolucoesImovel"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000"
										size="15" maxlength="15" /></td>
									<td width="90"><strong>Situação de Esgoto:</strong></td>
									<td width="120"><html:text
										property="situacaoEsgotoDevolucoesImovel" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000"
										size="15" maxlength="15" /></td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					</td>
				</tr>
				
				
				
				
				
				
				
				
				<tr>
					<td colspan="4">
					<table width="100%" align="center" bgcolor="#99CCFF" border="0"
						cellpadding="0" cellspacing="0" class="fontePequena">
						<tr bgcolor="#79bbfd" height="20">
							<td align="center">
								<strong>Devoluções das Contas</strong>
							</td>
						</tr>
						<%int cont = 1;%>
						<tr bgcolor="#cbe5fe">
							<td width="100%" align="center">
							<table width="100%" bgcolor="#90c7fc" class="fontePequena">
								<tr bordercolor="#000000">
									<td width="14%" bgcolor="#90c7fc" align="center" rowspan="2"><strong>Mês/Ano</strong></td>
									<td width="19%" bgcolor="#90c7fc" align="center" rowspan="2"><strong>Valor da Conta</strong></td>
									<td width="19%" bgcolor="#90c7fc" align="center" rowspan="2"><strong>Valor da Devol.</strong></td>
									<td width="16%" bgcolor="#90c7fc" align="center" rowspan="2"><strong>Data da Devol.</strong></td>
									<td width="32%" bgcolor="#90c7fc" align="center" colspan="2"><strong>Situação</strong></td>
								</tr>
								<tr>
									<td width="16%" bgcolor="#cbe5fe" align="center"><strong>Anterior</strong></td>
									<td width="16%" bgcolor="#cbe5fe" align="center"><strong>Atual</strong></td>
								</tr>
								<% if ( (session.getAttribute("qtdeDevContas") == null)) {%>
									<!-- Para carregar a tabela vazia -->	
								<% }else if ( (session.getAttribute("qtdeDevContas") != null) && ((Integer) session.getAttribute("qtdeDevContas")
									<= ConstantesSistema.NUMERO_MAXIMO_REGISTROS_CONSULTA_DEVOLUCAO)) {%>
				
								
								<!-- Devolução -->
								<logic:present name="colecaoDevolucaoImovelConta"
									scope="session">
									
									<logic:iterate name="colecaoDevolucaoImovelConta"
										id="devolucao" type="Devolucao">
										<%cont = cont + 1;
											if (cont % 2 == 0) {%>
										<tr bgcolor="#FFFFFF">
											<%} else {

											%>
										<tr bgcolor="#cbe5fe">
											<%}%>
											
											<%-- jah tava comentado 										
											<td width="14%" align="center">
												<a href="javascript:abrirPopup('exibirConsultarContaAction.do?tipoConsulta=conta&contaID=<%="" + devolucao.getGuiaDevolucao().getConta().getId() %>' , 600, 800);">${devolucao.guiaDevolucao.contaGeral.formatarAnoMesParaMesAno}&nbsp;</a>
											</td>
											--%>									
											<logic:present name="devolucao" property="guiaDevolucao">
												<logic:present name="devolucao" property="guiaDevolucao.contaGeral" >
													<logic:equal name="devolucao" property="guiaDevolucao.contaGeral.indicadorHistorico" value="2">
														<td width="14%" align="center">
															<a href="javascript:abrirPopup('exibirConsultarContaAction.do?tipoConsulta=conta&contaID=<%="" + devolucao.getGuiaDevolucao().getContaGeral().getConta().getId() %>' , 600, 800);">${devolucao.guiaDevolucao.contaGeral.conta.formatarAnoMesParaMesAno}&nbsp;</a>
														</td>
													</logic:equal>
													<logic:equal name="devolucao" property="guiaDevolucao.contaGeral.indicadorHistorico" value="1">
														<td width="14%" align="center">
															<a href="javascript:abrirPopup('exibirConsultarContaAction.do?tipoConsulta=contaHistorico&contaID=<%="" + devolucao.getGuiaDevolucao().getContaGeral().getContaHistorico().getId() %>' , 600, 800);">${devolucao.guiaDevolucao.contaGeral.contaHistorico.formatarAnoMesParaMesAno}&nbsp;</a>
														</td>
													</logic:equal>
												</logic:present>
												<logic:notPresent name="devolucao" property="guiaDevolucao.contaGeral" >
														<td width="14%" align="center">&nbsp;</td>
												</logic:notPresent>
											</logic:present>			
											<logic:notPresent name="devolucao" property="guiaDevolucao" >
													<td width="14%" align="center">&nbsp;</td>
											</logic:notPresent>												
									
									
											<logic:present name="devolucao" property="guiaDevolucao">
												<logic:present name="devolucao" property="guiaDevolucao.contaGeral" >
													<logic:equal name="devolucao" property="guiaDevolucao.contaGeral.indicadorHistorico" value="2">
														<logic:present name="devolucao" property="guiaDevolucao.contaGeral.conta.valorTotal" >
															<td width="19%" align="right">${devolucao.guiaDevolucao.contaGeral.conta.valorTotal}&nbsp;</td>
														</logic:present>
														<logic:notPresent name="devolucao" property="guiaDevolucao.contaGeral.conta.valorTotal" >
															<td width="19%" align="center">&nbsp;</td>
														</logic:notPresent>
													</logic:equal>
													<logic:equal name="devolucao" property="guiaDevolucao.contaGeral.indicadorHistorico" value="1">
														<logic:present name="devolucao" property="guiaDevolucao.contaGeral.contaHistorico.valorTotal" >
															<td width="19%" align="right">${devolucao.guiaDevolucao.contaGeral.contaHistorico.valorTotal}&nbsp;</td>
														</logic:present>
														<logic:notPresent name="devolucao" property="guiaDevolucao.contaGeral.contaHistorico.valorTotal" >
															<td width="19%" align="center">&nbsp;</td>
														</logic:notPresent>
													</logic:equal>
												</logic:present>
												<logic:notPresent name="devolucao" property="guiaDevolucao.contaGeral" >
													<td width="19%" align="center">&nbsp;</td>
												</logic:notPresent>
											</logic:present>
											<logic:notPresent name="devolucao" property="guiaDevolucao" >
												<td width="19%" align="center">&nbsp;</td>
											</logic:notPresent>
											
												
											<logic:present name="devolucao" property="valorDevolucao" >
												<td width="19%" align="right">${devolucao.valorDevolucao}&nbsp;</td>
											</logic:present>
											<logic:notPresent name="devolucao" property="valorDevolucao" >
												<td width="19%" align="center">&nbsp;</td>
											</logic:notPresent>
											
											<logic:present name="devolucao" property="dataDevolucao" >
												<td width="16%" align="center"><bean:write name="devolucao" property="dataDevolucao" formatKey="date.format" />&nbsp;</td>
											</logic:present>
											<logic:notPresent name="devolucao" property="dataDevolucao" >
												<td width="16%" align="center">&nbsp;</td>
											</logic:notPresent>
											
											<logic:present name="devolucao" property="devolucaoSituacaoAnterior" >
												<logic:present name="devolucao" property="devolucaoSituacaoAnterior.descricaoAbreviada" >
													<td width="16%">${devolucao.devolucaoSituacaoAnterior.descricaoAbreviada}&nbsp;</td>
												</logic:present>
												<logic:notPresent name="devolucao" property="devolucaoSituacaoAnterior.descricaoAbreviada" >
													<td width="16%" align="center">&nbsp;</td>
												</logic:notPresent>
											</logic:present>
											<logic:notPresent name="devolucao" property="devolucaoSituacaoAnterior" >
												<td width="16%" align="center">&nbsp;</td>
											</logic:notPresent>
											
											<logic:present name="devolucao" property="devolucaoSituacaoAtual" >
												<logic:present name="devolucao" property="devolucaoSituacaoAtual.descricaoAbreviada" >
													<td width="16%">${devolucao.devolucaoSituacaoAtual.descricaoAbreviada}&nbsp;</td>
												</logic:present>
												<logic:notPresent name="devolucao" property="devolucaoSituacaoAtual.descricaoAbreviada" >
													<td width="16%" align="center">&nbsp;</td>
												</logic:notPresent>
											</logic:present>
											<logic:notPresent name="devolucao" property="devolucaoSituacaoAtual" >
												<td width="16%" align="center">&nbsp;</td>
											</logic:notPresent>
											
										</tr>
									</logic:iterate>
								</logic:present>
								
								
								<!-- Devolução Histórico -->
								
								<logic:present name="colecaoDevolucaoHistoricoImovelConta"
									scope="session">
									
									<logic:iterate name="colecaoDevolucaoHistoricoImovelConta"
										id="devolucaoHistorico" type="DevolucaoHistorico">
										<%cont = cont + 1;
											if (cont % 2 == 0) {%>
										<tr bgcolor="#FFFFFF">
											<%} else {

											%>
										<tr bgcolor="#cbe5fe">
											<%}%>
											
											<%-- jah tava comentado 										
											<td width="14%" align="center">
												<a href="javascript:abrirPopup('exibirConsultarContaAction.do?tipoConsulta=conta&contaID=<%="" + devolucao.getGuiaDevolucao().getConta().getId() %>' , 600, 800);">${devolucao.guiaDevolucao.contaGeral.formatarAnoMesParaMesAno}&nbsp;</a>
											</td>
											--%>									
											<td width="14%" align="center">
												<logic:present name="devolucaoHistorico" property="anoMesReferenciaArrecadacao">
														<font color="#ff0000" >${devolucaoHistorico.formatarAnoMesParaMesAnoArrecadacao}</font>
												</logic:present>
											 &nbsp;
											 </td>										
											<logic:present name="devolucaoHistorico" property="guiaDevolucao">
												<logic:present name="devolucaoHistorico" property="guiaDevolucao.contaGeral" >
													<logic:equal name="devolucao" property="guiaDevolucao.contaGeral.indicadorHistorico" value="2">
														<logic:present name="devolucaoHistorico" property="guiaDevolucao.contaGeral.conta.valorTotal" >
																<td width="19%" align="right"><font color="#ff0000" >${devolucaoHistorico.guiaDevolucao.contaGeral.conta.valorTotal}</font>&nbsp;</td>
														</logic:present>
														<logic:notPresent name="devolucaoHistorico" property="guiaDevolucao.contaGeral.conta.valorTotal" >
															<td width="19%" align="center">&nbsp;</td>
														</logic:notPresent>
													</logic:equal>
													<logic:equal name="devolucao" property="guiaDevolucao.contaGeral.indicadorHistorico" value="1">
														<logic:present name="devolucaoHistorico" property="guiaDevolucao.contaGeral.contaHistorico.valorTotal" >
																<td width="19%" align="right"><font color="#ff0000" >${devolucaoHistorico.guiaDevolucao.contaGeral.contaHistorico.valorTotal}</font>&nbsp;</td>
														</logic:present>
														<logic:notPresent name="devolucaoHistorico" property="guiaDevolucao.contaGeral.contaHistorico.valorTotal" >
															<td width="19%" align="center">&nbsp;</td>
														</logic:notPresent>
													</logic:equal>
												</logic:present>
												<logic:notPresent name="devolucaoHistorico" property="guiaDevolucao.contaGeral" >
													<td width="19%" align="center">&nbsp;</td>
												</logic:notPresent>
											</logic:present>
											<logic:notPresent name="devolucaoHistorico" property="guiaDevolucao" >
												<td width="19%" align="center">&nbsp;</td>
											</logic:notPresent>
											
											<logic:present name="devolucaoHistorico" property="valorDevolucao" >
												<td width="19%" align="right">
												<font color="#ff0000" >${devolucaoHistorico.valorDevolucao}</font>
												&nbsp;</td>
											</logic:present>
											<logic:notPresent name="devolucaoHistorico" property="valorDevolucao" >
												<td width="19%" align="center">&nbsp;</td>
											</logic:notPresent>
											
											<logic:present name="devolucaoHistorico" property="dataDevolucao" >
													<td width="16%" align="center"><font color="#ff0000" ><bean:write name="devolucaoHistorico" property="dataDevolucao" formatKey="date.format" /></font>&nbsp;</td>
											</logic:present>
											<logic:notPresent name="devolucaoHistorico" property="dataDevolucao" >
												<td width="16%" align="center">&nbsp;</td>
											</logic:notPresent>
											
											<logic:present name="devolucaoHistorico" property="devolucaoSituacaoAnterior" >
												<logic:present name="devolucaoHistorico" property="devolucaoSituacaoAnterior.descricaoAbreviada" >
														<td width="16%"><font color="#ff0000" >${devolucaoHistorico.devolucaoSituacaoAnterior.descricaoAbreviada}</font>&nbsp;</td>
												</logic:present>
												<logic:notPresent name="devolucaoHistorico" property="devolucaoSituacaoAnterior.descricaoAbreviada" >
													<td width="16%" align="center">&nbsp;</td>
												</logic:notPresent>
											</logic:present>
											<logic:notPresent name="devolucaoHistorico" property="devolucaoSituacaoAnterior" >
												<td width="16%" align="center">&nbsp;</td>
											</logic:notPresent>
											
											<logic:present name="devolucaoHistorico" property="devolucaoSituacaoAtual" >
												<logic:present name="devolucaoHistorico" property="devolucaoSituacaoAtual.descricaoAbreviada" >
													<td width="16%"><font color="#ff0000" >${devolucaoHistorico.devolucaoSituacaoAtual.descricaoAbreviada}</font>&nbsp;</td>
												</logic:present>
												<logic:notPresent name="devolucaoHistorico" property="devolucaoSituacaoAtual.descricaoAbreviada" >
													<td width="16%" align="center">&nbsp;</td>
												</logic:notPresent>
											</logic:present>
											<logic:notPresent name="devolucaoHistorico" property="devolucaoSituacaoAtual" >
												<td width="16%" align="center">&nbsp;</td>
											</logic:notPresent>
										</tr>
									</logic:iterate>
								</logic:present>
								
								<% }else if ( (session.getAttribute("qtdeDevContas") != null) && ((Integer) session.getAttribute("qtdeDevContas")
									> ConstantesSistema.NUMERO_MAXIMO_REGISTROS_CONSULTA_DEVOLUCAO)) {%>


									<tr>
										<td height="190" colspan="6">
											<div style="width: 100%; height: 100%; overflow: auto;">
												<table width="100%">
								
													<!-- Devolução -->
													<logic:present name="colecaoDevolucaoImovelConta"
														scope="session">
														
														<logic:iterate name="colecaoDevolucaoImovelConta"
															id="devolucao" type="Devolucao">
															<%cont = cont + 1;
																if (cont % 2 == 0) {%>
															<tr bgcolor="#FFFFFF">
																<%} else {
					
																%>
															<tr bgcolor="#cbe5fe">
																<%}%>
																
																<%-- jah tava comentado 										
																<td width="14%" align="center">
																	<a href="javascript:abrirPopup('exibirConsultarContaAction.do?tipoConsulta=conta&contaID=<%="" + devolucao.getGuiaDevolucao().getConta().getId() %>' , 600, 800);">${devolucao.guiaDevolucao.contaGeral.formatarAnoMesParaMesAno}&nbsp;</a>
																</td>
																--%>									
																<logic:present name="devolucao" property="guiaDevolucao">
																	<logic:present name="devolucao" property="guiaDevolucao.contaGeral" >
																		<logic:equal name="devolucao" property="guiaDevolucao.contaGeral.indicadorHistorico" value="1">
																			<td width="14%" align="center">
																				<a href="javascript:abrirPopup('exibirConsultarContaAction.do?tipoConsulta=contaHistorico&contaID=<%="" + devolucao.getGuiaDevolucao().getContaGeral().getContaHistorico().getId() %>' , 600, 800);">${devolucao.guiaDevolucao.contaGeral.contaHistorico.formatarAnoMesParaMesAno}&nbsp;</a>
																			</td>
																		</logic:equal>
																		<logic:equal name="devolucao" property="guiaDevolucao.contaGeral.indicadorHistorico" value="2">
																			<td width="14%" align="center">
																				<a href="javascript:abrirPopup('exibirConsultarContaAction.do?tipoConsulta=conta&contaID=<%="" + devolucao.getGuiaDevolucao().getContaGeral().getConta().getId() %>' , 600, 800);">${devolucao.guiaDevolucao.contaGeral.conta.formatarAnoMesParaMesAno}&nbsp;</a>
																			</td>
																		</logic:equal>
																	</logic:present>
																	<logic:notPresent name="devolucao" property="guiaDevolucao.contaGeral" >
																			<td width="14%" align="center">&nbsp;</td>
																	</logic:notPresent>
																</logic:present>			
																<logic:notPresent name="devolucao" property="guiaDevolucao" >
																		<td width="14%" align="center">&nbsp;</td>
																</logic:notPresent>												
														
														
																<logic:present name="devolucao" property="guiaDevolucao">
																	<logic:present name="devolucao" property="guiaDevolucao.contaGeral" >
																		<logic:equal name="devolucao" property="guiaDevolucao.contaGeral.indicadorHistorico" value="1">
																			<logic:present name="devolucao" property="guiaDevolucao.contaGeral.contaHistorico.valorTotal" >
																				<td width="19%" align="right">${devolucao.guiaDevolucao.contaGeral.contaHistorico.valorTotal}&nbsp;</td>
																			</logic:present>
																			<logic:notPresent name="devolucao" property="guiaDevolucao.contaGeral.contaHistorico.valorTotal" >
																				<td width="19%" align="center">&nbsp;</td>
																			</logic:notPresent>
																		</logic:equal>
																		<logic:equal name="devolucao" property="guiaDevolucao.contaGeral.indicadorHistorico" value="2">
																			<logic:present name="devolucao" property="guiaDevolucao.contaGeral.conta.valorTotal" >
																				<td width="19%" align="right">${devolucao.guiaDevolucao.contaGeral.conta.valorTotal}&nbsp;</td>
																			</logic:present>
																			<logic:notPresent name="devolucao" property="guiaDevolucao.contaGeral.conta.valorTotal" >
																				<td width="19%" align="center">&nbsp;</td>
																			</logic:notPresent>
																		</logic:equal>
																	</logic:present>
																	<logic:notPresent name="devolucao" property="guiaDevolucao.contaGeral" >
																		<td width="19%" align="center">&nbsp;</td>
																	</logic:notPresent>
																</logic:present>
																<logic:notPresent name="devolucao" property="guiaDevolucao" >
																	<td width="19%" align="center">&nbsp;</td>
																</logic:notPresent>
																
																	
																<logic:present name="devolucao" property="valorDevolucao" >
																	<td width="20%" align="right">${devolucao.valorDevolucao}&nbsp;</td>
																</logic:present>
																<logic:notPresent name="devolucao" property="valorDevolucao" >
																	<td width="20%" align="center">&nbsp;</td>
																</logic:notPresent>
																
																<logic:present name="devolucao" property="dataDevolucao" >
																	<td width="16%" align="center"><bean:write name="devolucao" property="dataDevolucao" formatKey="date.format" />&nbsp;</td>
																</logic:present>
															<logic:notPresent name="devolucao" property="dataDevolucao" >
																<td width="16%" align="center">&nbsp;</td>
															</logic:notPresent>
															
															<logic:present name="devolucao" property="devolucaoSituacaoAnterior" >
																<logic:present name="devolucao" property="devolucaoSituacaoAnterior.descricaoAbreviada" >
																	<td width="16.5%">${devolucao.devolucaoSituacaoAnterior.descricaoAbreviada}&nbsp;</td>
																</logic:present>
																<logic:notPresent name="devolucao" property="devolucaoSituacaoAnterior.descricaoAbreviada" >
																	<td width="16.5%" align="center">&nbsp;</td>
																</logic:notPresent>
															</logic:present>
															<logic:notPresent name="devolucao" property="devolucaoSituacaoAnterior" >
																<td width="16.5%" align="center">&nbsp;</td>
															</logic:notPresent>
															
															<logic:present name="devolucao" property="devolucaoSituacaoAtual" >
																<logic:present name="devolucao" property="devolucaoSituacaoAtual.descricaoAbreviada" >
																	<td width="13.5%">${devolucao.devolucaoSituacaoAtual.descricaoAbreviada}&nbsp;</td>
																</logic:present>
																<logic:notPresent name="devolucao" property="devolucaoSituacaoAtual.descricaoAbreviada" >
																	<td width="13.5%" align="center">&nbsp;</td>
																</logic:notPresent>
															</logic:present>
															<logic:notPresent name="devolucao" property="devolucaoSituacaoAtual" >
																<td width="13.5%" align="center">&nbsp;</td>
															</logic:notPresent>
															
														</tr>
													</logic:iterate>
												</logic:present>
												
												
												<!-- Devolução Histórico -->
												
												<logic:present name="colecaoDevolucaoHistoricoImovelConta"
													scope="session">
													
													<logic:iterate name="colecaoDevolucaoHistoricoImovelConta"
														id="devolucaoHistorico" type="DevolucaoHistorico">
														<%cont = cont + 1;
															if (cont % 2 == 0) {%>
														<tr bgcolor="#FFFFFF">
															<%} else {
				
															%>
														<tr bgcolor="#cbe5fe">
															<%}%>
															
															<%-- jah tava comentado 										
															<td width="14%" align="center">
																<a href="javascript:abrirPopup('exibirConsultarContaAction.do?tipoConsulta=conta&contaID=<%="" + devolucao.getGuiaDevolucao().getConta().getId() %>' , 600, 800);">${devolucao.guiaDevolucao.contaGeral.formatarAnoMesParaMesAno}&nbsp;</a>
															</td>
															--%>									
															<td width="14%" align="center">
																<logic:present name="devolucaoHistorico" property="anoMesReferenciaArrecadacao">
																		<font color="#ff0000" >${devolucaoHistorico.formatarAnoMesParaMesAnoArrecadacao}</font>
																</logic:present>
														 &nbsp;
														 </td>										
														<logic:present name="devolucaoHistorico" property="guiaDevolucao">
															<logic:present name="devolucaoHistorico" property="guiaDevolucao.contaGeral" >
																<logic:equal name="devolucao" property="guiaDevolucao.contaGeral.indicadorHistorico" value="1">
																	<logic:present name="devolucaoHistorico" property="guiaDevolucao.contaGeral.contaHistorico.valorTotal" >
																			<td width="19%" align="right"><font color="#ff0000" >${devolucaoHistorico.guiaDevolucao.contaGeral.contaHistorico.valorTotal}</font>&nbsp;</td>
																	</logic:present>
																	<logic:notPresent name="devolucaoHistorico" property="guiaDevolucao.contaGeral.contaHistorico.valorTotal" >
																		<td width="19%" align="center">&nbsp;</td>
																	</logic:notPresent>
																</logic:equal>
																<logic:equal name="devolucao" property="guiaDevolucao.contaGeral.indicadorHistorico" value="2">
																	<logic:present name="devolucaoHistorico" property="guiaDevolucao.contaGeral.conta.valorTotal" >
																			<td width="19%" align="right"><font color="#ff0000" >${devolucaoHistorico.guiaDevolucao.contaGeral.conta.valorTotal}</font>&nbsp;</td>
																	</logic:present>
																	<logic:notPresent name="devolucaoHistorico" property="guiaDevolucao.contaGeral.conta.valorTotal" >
																		<td width="19%" align="center">&nbsp;</td>
																	</logic:notPresent>
																</logic:equal>
															</logic:present>
															<logic:notPresent name="devolucaoHistorico" property="guiaDevolucao.contaGeral" >
																<td width="19%" align="center">&nbsp;</td>
															</logic:notPresent>
														</logic:present>
														<logic:notPresent name="devolucaoHistorico" property="guiaDevolucao" >
															<td width="19%" align="center">&nbsp;</td>
														</logic:notPresent>
														
														<logic:present name="devolucaoHistorico" property="valorDevolucao" >
															<td width="20%" align="right">
															<font color="#ff0000" >${devolucaoHistorico.valorDevolucao}</font>
															&nbsp;</td>
														</logic:present>
														<logic:notPresent name="devolucaoHistorico" property="valorDevolucao" >
															<td width="20%" align="center">&nbsp;</td>
														</logic:notPresent>
														
														<logic:present name="devolucaoHistorico" property="dataDevolucao" >
																<td width="16%" align="center"><font color="#ff0000" ><bean:write name="devolucaoHistorico" property="dataDevolucao" formatKey="date.format" /></font>&nbsp;</td>
														</logic:present>
														<logic:notPresent name="devolucaoHistorico" property="dataDevolucao" >
															<td width="16%" align="center">&nbsp;</td>
														</logic:notPresent>
														
														<logic:present name="devolucaoHistorico" property="devolucaoSituacaoAnterior" >
															<logic:present name="devolucaoHistorico" property="devolucaoSituacaoAnterior.descricaoAbreviada" >
																	<td width="16.5%"><font color="#ff0000" >${devolucaoHistorico.devolucaoSituacaoAnterior.descricaoAbreviada}</font>&nbsp;</td>
															</logic:present>
															<logic:notPresent name="devolucaoHistorico" property="devolucaoSituacaoAnterior.descricaoAbreviada" >
																<td width="16.5%" align="center">&nbsp;</td>
															</logic:notPresent>
														</logic:present>
														<logic:notPresent name="devolucaoHistorico" property="devolucaoSituacaoAnterior" >
															<td width="16.5%" align="center">&nbsp;</td>
														</logic:notPresent>
														
														<logic:present name="devolucaoHistorico" property="devolucaoSituacaoAtual" >
															<logic:present name="devolucaoHistorico" property="devolucaoSituacaoAtual.descricaoAbreviada" >
																<td width="13.5%"><font color="#ff0000" >${devolucaoHistorico.devolucaoSituacaoAtual.descricaoAbreviada}</font>&nbsp;</td>
															</logic:present>
															<logic:notPresent name="devolucaoHistorico" property="devolucaoSituacaoAtual.descricaoAbreviada" >
																<td width="13.5%" align="center">&nbsp;</td>
															</logic:notPresent>
														</logic:present>
														<logic:notPresent name="devolucaoHistorico" property="devolucaoSituacaoAtual" >
															<td width="13.5%" align="center">&nbsp;</td>
														</logic:notPresent>
														
													</tr>
												</logic:iterate>
											</logic:present>
									
									
													</table>
												</div>
											</td>
										</tr>
					
									<%}%>
									
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
						<table width="100%" align="center" bgcolor="#99CCFF" border="0"
						cellpadding="0" cellspacing="0" class="fontePequena">
						<tr bgcolor="#79bbfd" height="20">
							<td align="center">
								<strong>Devoluções das Guias de Pagamento</strong>
							</td>
						</tr>
						<%int contad = 1;%>
						<tr bgcolor="#cbe5fe">
							<td width="100%" align="center">

							<table width="100%" bgcolor="#90c7fc" class="fontePequena">
								<tr bordercolor="#000000">
									<td width="20%" bgcolor="#90c7fc" align="center" rowspan="2"><strong>Cliente</strong></td>
									<td width="15%" bgcolor="#90c7fc" align="center" rowspan="2"><strong>Valor da Guia de Pagto.</strong></td>
									<td width="15%" bgcolor="#90c7fc" align="center" rowspan="2"><strong>Valor da Devol.</strong></td>
									<td width="11%" bgcolor="#90c7fc" align="center" rowspan="2"><strong>Data da Devol.</strong></td>
									<td width="32%" bgcolor="#90c7fc" align="center" colspan="2"><strong>Situação</strong></td>
								</tr>
								<tr>
									<td width="16%" bgcolor="#cbe5fe" align="center"><strong>Anterior</strong></td>
									<td width="16%" bgcolor="#cbe5fe" align="center"><strong>Atual</strong></td>
								</tr>

								<%if ( (session.getAttribute("qtdeDevGuiaPagamento") == null)) {%>
										<!-- Para apresentar a tabela vazia -->
								
								<%}else if ( (session.getAttribute("qtdeDevGuiaPagamento") != null) && ((Integer) session.getAttribute("qtdeDevGuiaPagamento")
									<= ConstantesSistema.NUMERO_MAXIMO_REGISTROS_CONSULTA_DEVOLUCAO)) {%>
				
								<!-- Devoluções -->
								<logic:present name="colecaoDevolucaoImovelGuiaPagamento"
									scope="session">
									
									<logic:iterate name="colecaoDevolucaoImovelGuiaPagamento"
										id="devolucao" type="Devolucao">
										<%contad = contad + 1;
											if (contad % 2 == 0) {%>
										<tr bgcolor="#FFFFFF">
											<%} else {

											%>
										<tr bgcolor="#cbe5fe">
											<%}%>
											<td width="20%" align="center">${devolucao.cliente.id}&nbsp;</td>
											<td width="15%" align="right">
												<logic:present name="devolucao" property="guiaDevolucao">
													<logic:present name="devolucao" property="guiaDevolucao.guiaPagamentoGeral">
														<logic:equal name="devolucao" property="guiaDevolucao.guiaPagamentoGeral.indicadorHistorico" value="1">
															${devolucao.guiaDevolucao.guiaPagamentoGeral.guiaPagamentoHistorico.valorDebito}
														</logic:equal>
														<logic:equal name="devolucao" property="guiaDevolucao.guiaPagamentoGeral.indicadorHistorico" value="2">
															${devolucao.guiaDevolucao.guiaPagamentoGeral.guiaPagamento.valorDebito}
														</logic:equal>
													</logic:present>
												</logic:present>
												&nbsp;
											</td>
											<td width="15%" align="right">
												${devolucao.valorDevolucao}&nbsp;
											</td>
											<td width="11%" align="center">
												<bean:write name="devolucao" property="dataDevolucao" formatKey="date.format" />&nbsp;
											</td>
											<td width="16%">
												<logic:present name="devolucao" property="devolucaoSituacaoAnterior">
													${devolucao.devolucaoSituacaoAnterior.descricaoAbreviada}
												</logic:present>
												&nbsp;	
											</td>
											<td width="16%">
												<logic:present name="devolucao" property="devolucaoSituacaoAtual">
													${devolucao.devolucaoSituacaoAtual.descricaoAbreviada}
												</logic:present>
												&nbsp;
											</td>
										</tr>
									</logic:iterate>
								</logic:present>
								
								<!-- Devoluções Histórico-->
								<logic:present name="colecaoDevolucaoHistoricoImovelGuiaPagamento"
									scope="session">
									
									<logic:iterate name="colecaoDevolucaoHistoricoImovelGuiaPagamento"
										id="devolucaoHistorico" type="DevolucaoHistorico">
										<%contad = contad + 1;
											if (contad % 2 == 0) {%>
										<tr bgcolor="#FFFFFF">
											<%} else {

											%>
										<tr bgcolor="#cbe5fe">
											<%}%>
											<td width="12%" align="center"><font color="#ff0000" >${devolucaoHistorico.cliente.id}</font>&nbsp;</td>
											<td width="15%" align="center">
												<logic:present name="devolucaoHistorico" property="guiaDevolucao">
													<logic:present name="devolucaoHistorico" property="guiaDevolucao.guiaPagamentoGeral">
														<logic:equal name="devolucao" property="guiaDevolucao.guiaPagamentoGeral.indicadorHistorico" value="1">
														   <logic:present name="devolucaoHistorico" property="guiaDevolucao.guiaPagamentoGeral.guiaPagamentoHistorico.debitoTipo">
																 <a href="javascript:abrirPopup('exibirConsultarGuiaPagamentoAction.do?guiaPagamentoId=<%="" + devolucaoHistorico.getGuiaDevolucao().getGuiaPagamentoGeral().getGuiaPagamentoHistorico().getId() %>')"><font color="#ff0000" >${devolucaoHistorico.guiaDevolucao.guiaPagamentoGeral.guiaPagamentoHistorico.debitoTipo.descricao}</font></a>	
															</logic:present>
														</logic:equal>
														<logic:equal name="devolucao" property="guiaDevolucao.guiaPagamentoGeral.indicadorHistorico" value="2">
														   <logic:present name="devolucaoHistorico" property="guiaDevolucao.guiaPagamentoGeral.guiaPagamento.debitoTipo">
																 <a href="javascript:abrirPopup('exibirConsultarGuiaPagamentoAction.do?guiaPagamentoId=<%="" + devolucaoHistorico.getGuiaDevolucao().getGuiaPagamentoGeral().getGuiaPagamentoHistorico().getId() %>')"><font color="#ff0000" >${devolucaoHistorico.guiaDevolucao.guiaPagamentoGeral.guiaPagamento.debitoTipo.descricao}</font></a>	
															</logic:present>
														</logic:equal>
													</logic:present>
												</logic:present>
												&nbsp;
											</td>
											<td width="15%" align="right">
												<logic:present name="devolucaoHistorico" property="guiaDevolucao">
													<logic:present name="devolucaoHistorico" property="guiaDevolucao.guiaPagamentoGeral">
														<logic:equal name="devolucao" property="guiaDevolucao.guiaPagamentoGeral.indicadorHistorico" value="1">
															<font color="#ff0000" >${devolucaoHistorico.guiaDevolucao.guiaPagamentoGeral.guiaPagamentoHistorico.valorDebito}</font>
														</logic:equal>
														<logic:equal name="devolucao" property="guiaDevolucao.guiaPagamentoGeral.indicadorHistorico" value="2">
															<font color="#ff0000" >${devolucaoHistorico.guiaDevolucao.guiaPagamentoGeral.guiaPagamento.valorDebito}</font>
														</logic:equal>
													</logic:present>
												</logic:present>
												&nbsp;
											</td>
											<td width="15%" align="right">
												<font color="#ff0000" >${devolucaoHistorico.valorDevolucao}</font>&nbsp;
											</td>
											<td width="11%" align="center">
												<font color="#ff0000" ><bean:write name="devolucaoHistorico" property="dataDevolucao" formatKey="date.format" /></font>&nbsp;
											</td>
											<td width="16%">
												<logic:present name="devolucaoHistorico" property="devolucaoSituacaoAnterior">
													<font color="#ff0000" >${devolucaoHistorico.devolucaoSituacaoAnterior.descricaoAbreviada}</font>
												</logic:present>
												&nbsp;	
											</td>
											<td width="16%">
												<logic:present name="devolucaoHistorico" property="devolucaoSituacaoAtual">
													<font color="#ff0000" >${devolucaoHistorico.devolucaoSituacaoAtual.descricaoAbreviada}</font>
												</logic:present>
												&nbsp;
											</td>
										</tr>
									</logic:iterate>
								</logic:present>
								
								<%}else if ( (session.getAttribute("qtdeDevGuiaPagamento") != null) && ((Integer) session.getAttribute("qtdeDevGuiaPagamento")
									> ConstantesSistema.NUMERO_MAXIMO_REGISTROS_CONSULTA_DEVOLUCAO)) {%>

									<tr>
										<td height="190" colspan="7">
											<div style="width: 100%; height: 100%; overflow: auto;">
												<table width="100%">
								
													<!-- Devoluções -->
													<logic:present name="colecaoDevolucaoImovelGuiaPagamento"
														scope="session">
														
														<logic:iterate name="colecaoDevolucaoImovelGuiaPagamento"
															id="devolucao" type="Devolucao">
															<%contad = contad + 1;
																if (contad % 2 == 0) {%>
															<tr bgcolor="#FFFFFF">
																<%} else {
					
																%>
															<tr bgcolor="#cbe5fe">
																<%}%>
																<td width="12%" align="center">${devolucao.cliente.id}&nbsp;</td>
																<td width="15%" align="center">
																	<logic:present name="devolucao" property="guiaDevolucao">
																		<logic:present name="devolucao" property="guiaDevolucao.guiaPagamento">
																			<logic:equal name="devolucao" property="guiaDevolucao.guiaPagamentoGeral.indicadorHistorico" value="1">
																			   <logic:present name="devolucao" property="guiaDevolucao.guiaPagamentoGeral.guiaPagamentoHistorico.debitoTipo">
																					<a href="javascript:abrirPopup('exibirConsultarGuiaPagamentoAction.do?guiaPagamentoId=<%="" + devolucao.getGuiaDevolucao().getGuiaPagamentoGeral().getGuiaPagamentoHistorico().getId() %>')">${devolucao.guiaDevolucao.guiaPagamentoGeral.guiaPagamentoHistorico.debitoTipo.descricao}</a>	
																				</logic:present>
																			</logic:equal>
																			<logic:equal name="devolucao" property="guiaDevolucao.guiaPagamentoGeral.indicadorHistorico" value="2">
																			   <logic:present name="devolucao" property="guiaDevolucao.guiaPagamentoGeral.guiaPagamento.debitoTipo">
																					<a href="javascript:abrirPopup('exibirConsultarGuiaPagamentoAction.do?guiaPagamentoId=<%="" + devolucao.getGuiaDevolucao().getGuiaPagamentoGeral().getGuiaPagamento().getId() %>')">${devolucao.guiaDevolucao.guiaPagamentoGeral.guiaPagamento.debitoTipo.descricao}</a>	
																				</logic:present>
																			</logic:equal>
																		</logic:present>
																	</logic:present>
																	&nbsp;
																</td>
																<td width="15%" align="right">
																	<logic:present name="devolucao" property="guiaDevolucao">
																		<logic:present name="devolucao" property="guiaDevolucao.guiaPagamentoGeral">
																			<logic:equal name="devolucao" property="guiaDevolucao.guiaPagamentoGeral.indicadorHistorico" value="1">
																				${devolucao.guiaDevolucao.guiaPagamentoGeral.guiaPagamentoHistorico.valorDebito}
																			</logic:equal>
																			<logic:equal name="devolucao" property="guiaDevolucao.guiaPagamentoGeral.indicadorHistorico" value="2">
																				${devolucao.guiaDevolucao.guiaPagamentoGeral.guiaPagamento.valorDebito}
																			</logic:equal>
																		</logic:present>
																	</logic:present>
																	&nbsp;
																</td>
																<td width="15%" align="right">
																	${devolucao.valorDevolucao}&nbsp;
																</td>
																<td width="12.9%" align="center">
																	<bean:write name="devolucao" property="dataDevolucao" formatKey="date.format" />&nbsp;
																</td>
																<td width="16.6%">
																	<logic:present name="devolucao" property="devolucaoSituacaoAnterior">
																		${devolucao.devolucaoSituacaoAnterior.descricaoAbreviada}
																	</logic:present>
																	&nbsp;	
																</td>
																<td width="13.5%">
																	<logic:present name="devolucao" property="devolucaoSituacaoAtual">
																		${devolucao.devolucaoSituacaoAtual.descricaoAbreviada}
																	</logic:present>
																	&nbsp;
																</td>
															</tr>
														</logic:iterate>
													</logic:present>
													
													<!-- Devoluções Histórico-->
													<logic:present name="colecaoDevolucaoHistoricoImovelGuiaPagamento"
														scope="session">
														
														<logic:iterate name="colecaoDevolucaoHistoricoImovelGuiaPagamento"
															id="devolucaoHistorico" type="DevolucaoHistorico">
															<%contad = contad + 1;
																if (contad % 2 == 0) {%>
															<tr bgcolor="#FFFFFF">
																<%} else {
					
																%>
															<tr bgcolor="#cbe5fe">
																<%}%>
																<td width="12%" align="center"><font color="#ff0000" >${devolucaoHistorico.cliente.id}</font>&nbsp;</td>
																<td width="15%" align="center">
																	<logic:present name="devolucaoHistorico" property="guiaDevolucao">
																		<logic:present name="devolucaoHistorico" property="guiaDevolucao.guiaPagamentoGeral">
																			<logic:equal name="devolucao" property="guiaDevolucao.guiaPagamentoGeral.indicadorHistorico" value="1">
																			   <logic:present name="devolucaoHistorico" property="guiaDevolucao.guiaPagamentoGeral.guiaPagamentoHistorico.debitoTipo">
																					<a href="javascript:abrirPopup('exibirConsultarGuiaPagamentoAction.do?guiaPagamentoId=<%="" + devolucaoHistorico.getGuiaDevolucao().getGuiaPagamentoGeral().getGuiaPagamentoHistorico().getId() %>')"><font color="#ff0000" >${devolucaoHistorico.guiaDevolucao.guiaPagamentoGeral.guiaPagamentoHistorico.debitoTipo.descricao}</font></a>
																			   </logic:present>
																			</logic:equal>
																			<logic:equal name="devolucao" property="guiaDevolucao.guiaPagamentoGeral.indicadorHistorico" value="2">
																			   <logic:present name="devolucaoHistorico" property="guiaDevolucao.guiaPagamentoGeral.guiaPagamento.debitoTipo">
																					<a href="javascript:abrirPopup('exibirConsultarGuiaPagamentoAction.do?guiaPagamentoId=<%="" + devolucaoHistorico.getGuiaDevolucao().getGuiaPagamentoGeral().getGuiaPagamento().getId() %>')"><font color="#ff0000" >${devolucaoHistorico.guiaDevolucao.guiaPagamentoGeral.guiaPagamento.debitoTipo.descricao}</font></a>
																			   </logic:present>
																			</logic:equal>
																		</logic:present>
																	</logic:present>
																	&nbsp;
																</td>
																<td width="15%" align="right">
																	<logic:present name="devolucaoHistorico" property="guiaDevolucao">
																		<logic:present name="devolucaoHistorico" property="guiaDevolucao.guiaPagamentoGeral">
																			<logic:equal name="devolucao" property="guiaDevolucao.guiaPagamentoGeral.indicadorHistorico" value="1">
																				<font color="#ff0000" >${devolucaoHistorico.guiaDevolucao.guiaPagamentoGeral.guiaPagamentoHistorico.valorDebito}</font>
																			</logic:equal>
																			<logic:equal name="devolucao" property="guiaDevolucao.guiaPagamentoGeral.indicadorHistorico" value="2">
																				<font color="#ff0000" >${devolucaoHistorico.guiaDevolucao.guiaPagamentoGeral.guiaPagamento.valorDebito}</font>
																			</logic:equal>
																		</logic:present>
																	</logic:present>
																	&nbsp;
																</td>
																<td width="15%" align="right">
																	<font color="#ff0000" >${devolucaoHistorico.valorDevolucao}</font>&nbsp;
																</td>
																<td width="12.9%" align="center">
																	<font color="#ff0000" ><bean:write name="devolucaoHistorico" property="dataDevolucao" formatKey="date.format" /></font>&nbsp;
																</td>
																<td width="16.6%">
																	<logic:present name="devolucaoHistorico" property="devolucaoSituacaoAnterior">
																		<font color="#ff0000" >${devolucaoHistorico.devolucaoSituacaoAnterior.descricaoAbreviada}</font>
																	</logic:present>
																	&nbsp;	
																</td>
																<td width="13.5%">
																	<logic:present name="devolucaoHistorico" property="devolucaoSituacaoAtual">
																		<font color="#ff0000" >${devolucaoHistorico.devolucaoSituacaoAtual.descricaoAbreviada}</font>
																	</logic:present>
																	&nbsp;
																</td>
															</tr>
														</logic:iterate>
													</logic:present>
								
								
												</table>
											</div>
										</td>
									</tr>
				
								<%}%>
								
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
					<table width="100%" align="center" bgcolor="#99CCFF" border="0"
						cellpadding="0" cellspacing="0" class="fontePequena">
						<tr bgcolor="#79bbfd" height="20">
							<td align="center">
								<strong>Devoluções dos Débitos a Cobrar</strong>
							</td>
						</tr>
						<%int contador = 1;%>
						<tr bgcolor="#cbe5fe">
							<td width="100%" align="center">
							<table width="100%" bgcolor="#90c7fc" class="fontePequena">
								<tr bordercolor="#000000">
									<td width="19%" bgcolor="#90c7fc" align="center" rowspan="2"><strong>Tipo do Débito</strong></td>
									<td width="17%" bgcolor="#90c7fc" align="center" rowspan="2"><strong>Valor a Ser Cobrado</strong></td>
									<td width="17%" bgcolor="#90c7fc" align="center" rowspan="2"><strong>Valor da Devol.</strong></td>
									<td width="15%" bgcolor="#90c7fc" align="center" rowspan="2"><strong>Data da Devol.</strong></td>
									<td width="32%" bgcolor="#90c7fc" align="center" colspan="2"><strong>Situação</strong></td>
								</tr>
								<tr>
									<td width="16%" bgcolor="#cbe5fe" align="center"><strong>Anterior</strong></td>
									<td width="16%" bgcolor="#cbe5fe" align="center"><strong>Atual</strong></td>
								</tr>

								<%--Esquema de paginação--%>
								<%if ( (session.getAttribute("qtdeDevDebitoACobrar") == null) ) {%>
										<!-- Para carregar a tabela vazia -->

								<%}else if ( (session.getAttribute("qtdeDevDebitoACobrar") != null) && ((Integer) session.getAttribute("qtdeDevDebitoACobrar")
									<= ConstantesSistema.NUMERO_MAXIMO_REGISTROS_CONSULTA_DEVOLUCAO)) {%>
				
								
								<!-- Devoluções -->
 								<logic:present name="colecaoDevolucaoImovelDebitoACobrar"
									scope="session">
									
									<logic:iterate name="colecaoDevolucaoImovelDebitoACobrar"
										id="devolucao" type="Devolucao">
										<%contador = contador + 1;
											if (contador % 2 == 0) {%>
										<tr bgcolor="#FFFFFF">
											<%} else {

											%>
										<tr bgcolor="#cbe5fe">
											<%}%>											
											<logic:equal name="devolucao" property="guiaDevolucao.debitoACobrarGeral.indicadorHistorico" value="1">
												<td width="19%" align="center">
													<a href="javascript:abrirPopup('exibirConsultarDebitoACobrarAction.do?imovelID=<bean:write name="devolucao" property="guiaDevolucao.debitoACobrarGeral.debitoACobrarHistorico.imovel.id" />&debitoID=<bean:write name="devolucao" property="guiaDevolucao.debitoACobrarGeral.debitoACobrarHistorico.id" />', 560, 660);">${devolucao.guiaDevolucao.debitoACobrarGeral.debitoACobrarHistorico.debitoTipo.descricao}
													&nbsp;</a>
												</td>
												<td width="17%" align="right">${devolucao.guiaDevolucao.debitoACobrarGeral.debitoACobrarHistorico.valorRestanteCobrado}&nbsp;</td>
											</logic:equal>
											<logic:equal name="devolucao" property="guiaDevolucao.debitoACobrarGeral.indicadorHistorico" value="2">
												<td width="19%" align="center">
													<a href="javascript:abrirPopup('exibirConsultarDebitoACobrarAction.do?imovelID=<bean:write name="devolucao" property="guiaDevolucao.debitoACobrarGeral.debitoACobrar.imovel.id" />&debitoID=<bean:write name="devolucao" property="guiaDevolucao.debitoACobrarGeral.debitoACobrar.id" />', 560, 660);">${devolucao.guiaDevolucao.debitoACobrarGeral.debitoACobrar.debitoTipo.descricao}
													&nbsp;</a>
												</td>
												<td width="17%" align="right">${devolucao.guiaDevolucao.debitoACobrarGeral.debitoACobrar.valorRestanteCobrado}&nbsp;</td>
											</logic:equal>
											
											
											<td width="17%" align="right">${devolucao.valorDevolucao}&nbsp;</td>
											<td width="15%" align="center"><bean:write name="devolucao" property="dataDevolucao" formatKey="date.format" />&nbsp;</td>
											<td width="16%">${devolucao.devolucaoSituacaoAnterior.descricaoAbreviada}&nbsp;</td>
											<td width="16%">${devolucao.devolucaoSituacaoAtual.descricaoAbreviada}&nbsp;</td>
										</tr>
									</logic:iterate>
								</logic:present>
								
								<!-- Devoluções Histórico-->
 								<logic:present name="colecaoDevolucaoHistoricoImovelDebitoACobrar"
									scope="session">
									
									<logic:iterate name="colecaoDevolucaoHistoricoImovelDebitoACobrar"
										id="devolucaoHistorico" type="DevolucaoHistorico">
										<%contador = contador + 1;
											if (contador % 2 == 0) {%>
										<tr bgcolor="#FFFFFF">
											<%} else {

											%>
										<tr bgcolor="#cbe5fe">
											<%}%>
											<logic:equal name="devolucao" property="guiaDevolucao.debitoACobrarGeral.indicadorHistorico" value="1">
												<td width="19%" align="center">
												 <a href="javascript:abrirPopup('exibirConsultarDebitoACobrarAction.do?imovelID=<bean:write name="devolucaoHistorico" property="guiaDevolucao.debitoACobrarGeral.debitoACobrarHistorico.imovel.id" />&debitoID=<bean:write name="devolucaoHistorico" property="guiaDevolucao.debitoACobrarGeral.debitoACobrarHistorico.id" />', 560, 660);"><font color="#ff0000" >${devolucaoHistorico.guiaDevolucao.debitoACobrarGeral.debitoACobrarHistorico.debitoTipo.descricao}</font>&nbsp;</a></td>
												<td width="17%" align="right"><font color="#ff0000" >${devolucaoHistorico.guiaDevolucao.debitoACobrarGeral.debitoACobrarHistorico.valorRestanteCobrado}</font>&nbsp;</td>
											</logic:equal>
											<logic:equal name="devolucao" property="guiaDevolucao.debitoACobrarGeral.indicadorHistorico" value="2">
												<td width="19%" align="center">
												 <a href="javascript:abrirPopup('exibirConsultarDebitoACobrarAction.do?imovelID=<bean:write name="devolucaoHistorico" property="guiaDevolucao.debitoACobrarGeral.debitoACobrar.imovel.id" />&debitoID=<bean:write name="devolucaoHistorico" property="guiaDevolucao.debitoACobrarGeral.debitoACobrar.id" />', 560, 660);"><font color="#ff0000" >${devolucaoHistorico.guiaDevolucao.debitoACobrarGeral.debitoACobrar.debitoTipo.descricao}</font>&nbsp;</a></td>
												<td width="17%" align="right"><font color="#ff0000" >${devolucaoHistorico.guiaDevolucao.debitoACobrarGeral.debitoACobrar.valorRestanteCobrado}</font>&nbsp;</td>
											</logic:equal>
											<td width="17%" align="right"><font color="#ff0000" >${devolucaoHistorico.valorRestanteCobrado}</font>&nbsp;</td>
											<td width="15%" align="center"><font color="#ff0000" ><bean:write name="devolucaoHistorico" property="dataDevolucao" formatKey="date.format" /></font>&nbsp;</td>
											<td width="16%"><font color="#ff0000" >${devolucaoHistorico.devolucaoSituacaoAnterior.descricaoAbreviada}</font>&nbsp;</td>
											<td width="16%"><font color="#ff0000" >${devolucaoHistorico.devolucaoSituacaoAtual.descricaoAbreviada}</font>&nbsp;</td>
										</tr>
									</logic:iterate>
								</logic:present>
								
								
								<%}else if ( (session.getAttribute("qtdeDevDebitoACobrar") != null) && ((Integer) session.getAttribute("qtdeDevDebitoACobrar")
									> ConstantesSistema.NUMERO_MAXIMO_REGISTROS_CONSULTA_DEVOLUCAO)) {%>

									<tr>
										<td height="190" colspan="6">
											<div style="width: 100%; height: 100%; overflow: auto;">
												<table width="100%">
								
													<!-- Devoluções -->
					 								<logic:present name="colecaoDevolucaoImovelDebitoACobrar"
														scope="session">
														
														<logic:iterate name="colecaoDevolucaoImovelDebitoACobrar"
															id="devolucao" type="Devolucao">
															<%contador = contador + 1;
																if (contador % 2 == 0) {%>
															<tr bgcolor="#FFFFFF">
																<%} else {
					
																%>
															<tr bgcolor="#cbe5fe">
																<%}%>
																<logic:equal name="devolucao" property="guiaDevolucao.debitoACobrarGeral.indicadorHistorico" value="1">
																	<td width="19%" align="center">
																	<a href="javascript:abrirPopup('exibirConsultarDebitoACobrarAction.do?imovelID=<bean:write name="devolucao" property="guiaDevolucao.debitoACobrarGeral.debitoACobrarHistorico.imovel.id" />&debitoID=<bean:write name="devolucao" property="guiaDevolucao.debitoACobrarGeral.debitoACobrarHistorico.id" />', 560, 660);">${devolucao.guiaDevolucao.debitoACobrarGeral.debitoACobrarHistorico.debitoTipo.descricao}
																	&nbsp;</a>
																	</td>
																	<td width="17%" align="right">${devolucao.guiaDevolucao.debitoACobrarGeral.debitoACobrarHistorico.valorRestanteCobrado}&nbsp;</td>
																</logic:equal>
																<logic:equal name="devolucao" property="guiaDevolucao.debitoACobrarGeral.indicadorHistorico" value="2">
																	<td width="19%" align="center">
																	<a href="javascript:abrirPopup('exibirConsultarDebitoACobrarAction.do?imovelID=<bean:write name="devolucao" property="guiaDevolucao.debitoACobrarGeral.debitoACobrar.imovel.id" />&debitoID=<bean:write name="devolucao" property="guiaDevolucao.debitoACobrarGeral.debitoACobrar.id" />', 560, 660);">${devolucao.guiaDevolucao.debitoACobrarGeral.debitoACobrar.debitoTipo.descricao}
																	&nbsp;</a>
																	</td>
																	<td width="17%" align="right">${devolucao.guiaDevolucao.debitoACobrarGeral.debitoACobrar.valorRestanteCobrado}&nbsp;</td>
																</logic:equal>
																<td width="17%" align="right">${devolucao.valorDevolucao}&nbsp;</td>
																<td width="16.8%" align="center"><bean:write name="devolucao" property="dataDevolucao" formatKey="date.format" />&nbsp;</td>
																<td width="16.7%">${devolucao.devolucaoSituacaoAnterior.descricaoAbreviada}&nbsp;</td>
																<td width="13.5%">${devolucao.devolucaoSituacaoAtual.descricaoAbreviada}&nbsp;</td>
															</tr>
														</logic:iterate>
													</logic:present>
													
													<!-- Devoluções Histórico-->
					 								<logic:present name="colecaoDevolucaoHistoricoImovelDebitoACobrar"
														scope="session">
														
														<logic:iterate name="colecaoDevolucaoHistoricoImovelDebitoACobrar"
															id="devolucaoHistorico" type="DevolucaoHistorico">
															<%contador = contador + 1;
																if (contador % 2 == 0) {%>
															<tr bgcolor="#FFFFFF">
																<%} else {
					
																%>
															<tr bgcolor="#cbe5fe">
																<%}%>
																<logic:equal name="devolucao" property="guiaDevolucao.debitoACobrarGeral.indicadorHistorico" value="1">
																	<td width="19%" align="center">
																	 <a href="javascript:abrirPopup('exibirConsultarDebitoACobrarAction.do?imovelID=<bean:write name="devolucaoHistorico" property="guiaDevolucao.debitoACobrarGeral.debitoACobrarHistorico.imovel.id" />&debitoID=<bean:write name="devolucaoHistorico" property="guiaDevolucao.debitoACobrarGeral.debitoACobrarHistorico.id" />', 560, 660);"><font color="#ff0000" >${devolucaoHistorico.guiaDevolucao.debitoACobrarGeral.debitoACobrarHistorico.debitoTipo.descricao}&nbsp;</font></a></td>
																	<td width="17%" align="right"><font color="#ff0000" >${devolucaoHistorico.guiaDevolucao.debitoACobrarGeral.debitoACobrarHistorico.valorRestanteCobrado}</font>&nbsp;</td>
																</logic:equal>
																<logic:equal name="devolucao" property="guiaDevolucao.debitoACobrarGeral.indicadorHistorico" value="2">
																	<td width="19%" align="center">
																	 <a href="javascript:abrirPopup('exibirConsultarDebitoACobrarAction.do?imovelID=<bean:write name="devolucaoHistorico" property="guiaDevolucao.debitoACobrarGeral.debitoACobrar.imovel.id" />&debitoID=<bean:write name="devolucaoHistorico" property="guiaDevolucao.debitoACobrarGeral.debitoACobrar.id" />', 560, 660);"><font color="#ff0000" >${devolucaoHistorico.guiaDevolucao.debitoACobrarGeral.debitoACobrar.debitoTipo.descricao}&nbsp;</font></a></td>
																	<td width="17%" align="right"><font color="#ff0000" >${devolucaoHistorico.guiaDevolucao.debitoACobrarGeral.debitoACobrar.valorRestanteCobrado}</font>&nbsp;</td>
																</logic:equal>
																<td width="17%" align="right"><font color="#ff0000" >${devolucaoHistorico.valorRestanteCobrado}</font>&nbsp;</td>
																<td width="16.8%" align="center"><font color="#ff0000" ><bean:write name="devolucaoHistorico" property="dataDevolucao" formatKey="date.format" /></font>&nbsp;</td>
																<td width="16.7%"><font color="#ff0000" >${devolucaoHistorico.devolucaoSituacaoAnterior.descricaoAbreviada}</font>&nbsp;</td>
																<td width="13.5%"><font color="#ff0000" >${devolucaoHistorico.devolucaoSituacaoAtual.descricaoAbreviada}</font>&nbsp;</td>
															</tr>
														</logic:iterate>
													</logic:present>
								
								
								
								
								
												</table>
											</div>
										</td>
									</tr>
				
								<%}%>
								
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
					<table width="100%" align="center" bgcolor="#99CCFF" border="0"
						cellpadding="0" cellspacing="0" class="fontePequena">
						<tr bgcolor="#79bbfd" height="20">
							<td align="center">
								<strong>Devoluções de Valores</strong>
							</td>
						</tr>	
						<%int contado = 1;%>					
						<tr bgcolor="#cbe5fe">
							<td width="100%" align="center">

							<table width="100%" bgcolor="#90c7fc" class="fontePequena">
								<tr bordercolor="#000000">
									<td width="15%" bgcolor="#90c7fc" align="center" rowspan="2"><strong>Cliente</strong></td>
									<td width="20%" bgcolor="#90c7fc" align="center" rowspan="2"><strong>Tipo do Débito</strong></td>
									<td width="11%" bgcolor="#90c7fc" align="center" rowspan="2"><strong>Valor da Guia de Devol.</strong></td>
									<td width="11%" bgcolor="#90c7fc" align="center" rowspan="2"><strong>Valor da Devol.</strong></td>
									<td width="11%" bgcolor="#90c7fc" align="center" rowspan="2"><strong>Data da Devol.</strong></td>
									<td width="32%" bgcolor="#90c7fc" align="center" colspan="2"><strong>Situação</strong></td>
								</tr>
								<tr>
									<td width="16%" bgcolor="#cbe5fe" align="center"><strong>Anterior</strong></td>
									<td width="16%" bgcolor="#cbe5fe" align="center"><strong>Atual</strong></td>
								</tr>
								<%if ( (session.getAttribute("qtdeDevDevolucaoValores") == null) ) {%>
										<!-- Para carregar a tabela vazia -->

								<%}else if ( (session.getAttribute("qtdeDevDevolucaoValores") != null) && ((Integer) session.getAttribute("qtdeDevDevolucaoValores")
									<= ConstantesSistema.NUMERO_MAXIMO_REGISTROS_CONSULTA_DEVOLUCAO)) {%>
				
								<!-- Devoluções -->
								<logic:present name="colecaoDevolucaoImovelDevolucaoValor"
									scope="session">
									
									<logic:iterate name="colecaoDevolucaoImovelDevolucaoValor"
										id="devolucao" type="Devolucao">
										<%contado = contado + 1;
											if (contado % 2 == 0) {%>
										<tr bgcolor="#FFFFFF">
											<%} else {

											%>
										<tr bgcolor="#cbe5fe">

											<%}%>
											<td width="15%" align="center">${devolucao.cliente.id}&nbsp;</td>
											<td width="20%">${devolucao.debitoTipo.descricao}&nbsp;</td>
											<td width="11%" align="right">${devolucao.guiaDevolucao.valorDevolucao}&nbsp;</td>
											<td width="11%" align="right">${devolucao.valorDevolucao}&nbsp;</td>
											<td width="11%" align="center"><bean:write name="devolucao" property="dataDevolucao" formatKey="date.format" />&nbsp;</td>
											<td width="16%">${devolucao.devolucaoSituacaoAnterior.descricaoAbreviada}&nbsp;</td>
											<td width="16%">${devolucao.devolucaoSituacaoAtual.descricaoAbreviada}&nbsp;</td>
										</tr>
									</logic:iterate>
								</logic:present>
								
								<!-- Devoluções Histórico-->
								<logic:present name="colecaoDevolucaoHistoricoImovelDevolucaoValor"
									scope="session">
									
									<logic:iterate name="colecaoDevolucaoHistoricoImovelDevolucaoValor"
										id="devolucaoHistorico" type="DevolucaoHistorico">
										<%contado = contado + 1;
											if (contado % 2 == 0) {%>
										<tr bgcolor="#FFFFFF">
											<%} else {

											%>
										<tr bgcolor="#cbe5fe">

											<%}%>
											<td width="15%" align="center"><font color="#ff0000" >${devolucaoHistorico.cliente.id}</font>&nbsp;</td>
											<td width="20%"><font color="#ff0000" >${devolucaoHistorico.debitoTipo.descricao}</font>&nbsp;</td>
											<td width="11%" align="right"><font color="#ff0000" >${devolucaoHistorico.guiaDevolucao.valorDevolucao}</font>&nbsp;</td>
											<td width="11%" align="right"><font color="#ff0000" >${devolucaoHistorico.valorDevolucao}</font>&nbsp;</td>
											<td width="11%" align="center"><font color="#ff0000" ><bean:write name="devolucaoHistorico" property="dataDevolucao" formatKey="date.format" /></font>&nbsp;</td>
											<td width="16%"><font color="#ff0000" >${devolucaoHistorico.devolucaoSituacaoAnterior.descricaoAbreviada}</font>&nbsp;</td>
											<td width="16%"><font color="#ff0000" >${devolucaoHistorico.devolucaoSituacaoAtual.descricaoAbreviada}</font>&nbsp;</td>
										</tr>
									</logic:iterate>
								</logic:present>
								
								
								<%}else if ( (session.getAttribute("qtdeDevDevolucaoValores") != null) && ((Integer) session.getAttribute("qtdeDevDevolucaoValores")
									> ConstantesSistema.NUMERO_MAXIMO_REGISTROS_CONSULTA_DEVOLUCAO)) {%>

									<tr>
										<td height="190" colspan="7">
											<div style="width: 100%; height: 100%; overflow: auto;">
												<table width="100%">
								
													<!-- Devoluções -->
													<logic:present name="colecaoDevolucaoImovelDevolucaoValor"
														scope="session">
														
														<logic:iterate name="colecaoDevolucaoImovelDevolucaoValor"
															id="devolucao" type="Devolucao">
															<%contado = contado + 1;
																if (contado % 2 == 0) {%>
															<tr bgcolor="#FFFFFF">
																<%} else {
					
																%>
															<tr bgcolor="#cbe5fe">
					
																<%}%>
																<td width="15%" align="center">${devolucao.cliente.id}&nbsp;</td>
																<td width="20%">${devolucao.debitoTipo.descricao}&nbsp;</td>
																<td width="11%" align="right">${devolucao.guiaDevolucao.valorDevolucao}&nbsp;</td>
																<td width="11%" align="right">${devolucao.valorDevolucao}&nbsp;</td>
																<td width="12.8%" align="center"><bean:write name="devolucao" property="dataDevolucao" formatKey="date.format" />&nbsp;</td>
																<td width="16.7%">${devolucao.devolucaoSituacaoAnterior.descricaoAbreviada}&nbsp;</td>
																<td width="13.5%">${devolucao.devolucaoSituacaoAtual.descricaoAbreviada}&nbsp;</td>
															</tr>
														</logic:iterate>
													</logic:present>
													
													<!-- Devoluções Histórico-->
													<logic:present name="colecaoDevolucaoHistoricoImovelDevolucaoValor"
														scope="session">
														
														<logic:iterate name="colecaoDevolucaoHistoricoImovelDevolucaoValor"
															id="devolucaoHistorico" type="DevolucaoHistorico">
															<%contado = contado + 1;
																if (contado % 2 == 0) {%>
															<tr bgcolor="#FFFFFF">
																<%} else {
					
																%>
															<tr bgcolor="#cbe5fe">
					
																<%}%>
																<td width="15%" align="center"><font color="#ff0000" >${devolucaoHistorico.cliente.id}</font>&nbsp;</td>
																<td width="20%"><font color="#ff0000" >${devolucaoHistorico.debitoTipo.descricao}</font>&nbsp;</td>
																<td width="11%" align="right"><font color="#ff0000" >${devolucaoHistorico.guiaDevolucao.valorDevolucao}</font>&nbsp;</td>
																<td width="11%" align="right"><font color="#ff0000" >${devolucaoHistorico.valorDevolucao}</font>&nbsp;</td>
																<td width="12.8%" align="center"><font color="#ff0000" ><bean:write name="devolucaoHistorico" property="dataDevolucao" formatKey="date.format" /></font>&nbsp;</td>
																<td width="16.7%"><font color="#ff0000" >${devolucaoHistorico.devolucaoSituacaoAnterior.descricaoAbreviada}</font>&nbsp;</td>
																<td width="13.5%"><font color="#ff0000" >${devolucaoHistorico.devolucaoSituacaoAtual.descricaoAbreviada}</font>&nbsp;</td>
															</tr>
														</logic:iterate>
													</logic:present>
								
												</table>
											</div>
										</td>
									</tr>
				
								<%}%>
								
							</table>
							</td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
		<p>&nbsp;</p>
		
		<table width="100%" border="0">
		  <logic:notPresent name="montarPopUp">
			<tr>
				<td>
					<div align="left">
						<a href="javascript:abrirInserirDevolucao();">
								<strong>Inserir Devolução</strong>
					 	</a>
					</div> 	
				</td>
			</tr>
			</logic:notPresent>
			<tr>
				<td colspan="2">
				<div align="right"><jsp:include
					page="/jsp/util/wizard/navegacao_botoes_wizard_consulta.jsp?numeroPagina=7" /></div>
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
</table>
<logic:notPresent name="montarPopUp">	
		<%@ include file="/jsp/util/rodape.jsp"%>
</logic:notPresent>
</html:form>
</body>
<!-- imovel_consultar_devolucoes.jsp -->
</html:html>
