<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page import="gcom.arrecadacao.pagamento.Pagamento"%>
<%@page import="gcom.arrecadacao.pagamento.PagamentoHistorico"%>
<%@page import="gcom.cadastro.cliente.ClienteImovel"%>
<%@page import="gcom.util.Util" isELIgnored="false"%>
<%@ page import="gcom.util.ConstantesSistema" isELIgnored="false"%>

<%@page import="gcom.arrecadacao.pagamento.GuiaPagamentoPrestacaoHistorico"%>
<%@page import="gcom.arrecadacao.pagamento.GuiaPagamentoPrestacao"%>

<%@page import="java.math.BigDecimal"%><html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">
<!--
function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

   	var form = document.forms[0];

    if (tipoConsulta == 'imovel') {
      form.idImovelPagamentos.value = codigoRegistro;
      form.matriculaImovelPagamentos.value = descricaoRegistro;
      form.matriculaImovelPagamentos.style.color = "#000000";
	  form.action = 'consultarImovelWizardAction.do?action=exibirConsultarImovelPagamentosAction&indicadorNovo=OK'
	  form.submit();
    }
    
}

function limparForm(){
   	var form = document.forms[0];
	form.action = 'consultarImovelWizardAction.do?action=exibirConsultarImovelPagamentosAction&limparForm=OK'
	form.submit();
}

function verificarExibicaoRelatorio() {
	var form = document.forms[0];
	
	if (form.idImovelPagamentos.value.length > 0 && form.matriculaImovelPagamentos.value.length > 0) {
		toggleBox('demodiv',1);
	} else {
		alert('Informe Imóvel');
	}
	
}
function limparImovelTecla() {

	var form = document.forms[0];
	
	form.matriculaImovelPagamentos.value = "";

	if (form.digitoVerificadorImovelPagamentos != undefined) {
		form.digitoVerificadorImovelPagamentos.value = "";
	}
		
	form.situacaoAguaPagamentos.value = "";
	form.situacaoEsgotoPagamentos.value = "";
	form.tipoLigacao.value = "";

}
	
-->
</script>

<style>
.fontePequena {
font-size: 11px;
face: Verdana, Arial, Helvetica, sans-serif;
}

</style>

</head>

<body leftmargin="5" topmargin="5" onload="javascript:setarFoco('idImovelPagamentos')">

<html:form action="/exibirConsultarImovelAction.do"
	name="ConsultarImovelActionForm"
	type="gcom.gui.cadastro.imovel.ConsultarImovelActionForm" method="post"
	onsubmit="return validateConsultarImovelActionForm(this);">

	<jsp:include
		page="/jsp/util/wizard/navegacao_abas_wizard_consulta.jsp?numeroPagina=6" />

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
			<table width="100%" border="0" style="fonteReduzida">
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
										property="idImovelPagamentos" maxlength="9" size="9"
											onkeypress="validaEnterComMensagem(event, 'consultarImovelWizardAction.do?action=exibirConsultarImovelPagamentosAction&indicadorNovo=OK&limparForm=S','idImovelPagamentos','Im&oacute;vel');"
											onkeyup="limparImovelTecla();"
											/>
											 
									<a
										href="javascript:abrirPopup('exibirPesquisarImovelAction.do', 400, 800);">
									<img width="23" height="21"
										src="<bean:message key="caminho.imagens"/>pesquisa.gif"
										border="0" /></a> <logic:present
										name="idImovelPagamentosNaoEncontrado" scope="request">
										
										<logic:equal name="matriculaSemDigitoVerificador" value="0" scope="request">
											<html:text property="matriculaImovelPagamentos" size="40"
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: #ff0000" />
										</logic:equal>
										
										<logic:equal name="matriculaSemDigitoVerificador" value="1" scope="request">
											<html:text property="digitoVerificadorImovelPagamentos" size="2"
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: #ff0000" />
																						
											<html:text property="matriculaImovelPagamentos" size="31"
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: #ff0000" />
										</logic:equal>
																				
									</logic:present> <logic:notPresent
										name="idImovelPagamentosNaoEncontrado" scope="request">
										<logic:present name="valorMatriculaImovelPagamentos"
											scope="request">
											
											<logic:equal name="matriculaSemDigitoVerificador" value="0" scope="request">
												<html:text property="matriculaImovelPagamentos"
													size="40" readonly="true"
													style="background-color:#EFEFEF; border:0; color: #000000" />
											</logic:equal>			
													
											<logic:equal name="matriculaSemDigitoVerificador" value="1" scope="request">
												<html:text property="digitoVerificadorImovelPagamentos"
													size="2" readonly="true"
													style="background-color:#EFEFEF; border:0; color: #000000" />
																								
												<html:text property="matriculaImovelPagamentos"
													size="31" readonly="true"
													style="background-color:#EFEFEF; border:0; color: #000000" />
											</logic:equal>		
																								
										</logic:present>
										<logic:notPresent name="valorMatriculaImovelPagamentos"
											scope="request">
											
											<logic:equal name="matriculaSemDigitoVerificador" value="0" scope="request">
												<html:text property="matriculaImovelPagamentos"
													size="40" readonly="true"
													style="background-color:#EFEFEF; border:0; color: #000000" />
											</logic:equal>
											
											<logic:equal name="matriculaSemDigitoVerificador" value="1" scope="request">
												<html:text property="digitoVerificadorImovelPagamentos"
													size="2" readonly="true"
													style="background-color:#EFEFEF; border:0; color: #000000" />
																								
												<html:text property="matriculaImovelPagamentos"
													size="31" readonly="true"
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
									<td><html:text property="situacaoAguaPagamentos"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000"
										size="15" maxlength="15" /></td>
									<td width="90"><strong>Situação de Esgoto:</strong></td>
									<td width="120"><html:text
										property="situacaoEsgotoPagamentos" readonly="true"
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
										size="15" maxlength="15" />
									</td>
									<td width="90"></td>
									<td width="120"></td>
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
					<td colspan="4">
					<table width="100%" align="center" bgcolor="#99CCFF" border="0"
						cellpadding="0" cellspacing="0" class="fontePequena">

						<tr bgcolor="#79bbfd" height="20">
							<td align="center"><strong>Pagamentos das Contas</strong></td>
						</tr>
						<%int cont1 = 1;%>

						<tr bgcolor="#cbe5fe">
							<td width="100%" align="center">
							<table width="100%" bgcolor="#90c7fc" class="fontePequena">
								<tr bordercolor="#000000">

									<td bgcolor="#90c7fc" width="12%" align="center" rowspan="2"><strong>Mês/Ano
									Conta</strong></td>
									<td bgcolor="#90c7fc" width="14%" align="center" rowspan="2"><strong>Valor
									da Conta</strong></td>
									<td bgcolor="#90c7fc" width="14%" align="center" rowspan="2"><strong>Valor
									do Pag.</strong></td>
									<td bgcolor="#90c7fc" width="14%" align="center" rowspan="2"><strong>Data
									do Pag.</strong></td>
									<td bgcolor="#90c7fc" width="14%" align="center" rowspan="2"><strong>Arrecadador</strong>
									</td>
									<td bgcolor="#90c7fc" width="32%" align="center" colspan="2"><strong>Situação</strong>
									</td>
								</tr>
								<tr>
									<td width="16%" bgcolor="#cbe5fe" align="center"><strong>Anterior</strong></td>
									<td width="16%" bgcolor="#cbe5fe" align="center"><strong>Atual</strong></td>
								</tr>
								<%if( (session.getAttribute("qtdePagContas") == null)) {%>
										<!--  PAra carregar a tabela vazia -->

								<%}else if( (session.getAttribute("qtdePagContas") != null) && ((Integer) session.getAttribute("qtdePagContas") <= ConstantesSistema.NUMERO_MAXIMO_REGISTROS_CONSULTA_PAGAMENTO)) {%>

								<logic:present name="colecaoPagamentosImovelConta"
									scope="session">
									<logic:notEmpty name="colecaoPagamentosImovelConta"
										scope="session">
										<logic:iterate name="colecaoPagamentosImovelConta"
											id="pagamento" type="Pagamento">
											<%cont1 = cont1 + 1;
				if (cont1 % 2 == 0) {%>
											<tr bgcolor="#FFFFFF">
												<%} else {

				%>
											<tr bgcolor="#cbe5fe">
												<%}%>

												<td width="12%" align="center"> 
 												   <logic:notEmpty	name="pagamento" property="conta">
													   <logic:notEmpty name="pagamento" property="conta.id">
														   <logic:notEmpty name="pagamento" property="conta.referencia">
															   <a href="javascript:abrirPopup('exibirConsultarContaAction.do?tipoConsulta=conta&contaSemCodigoBarras=1&contaID=<%="" + pagamento.getConta().getId() %>' , 600, 800);">${pagamento.conta.formatarAnoMesParaMesAno}</a>
  														   </logic:notEmpty>
														   <logic:empty name="pagamento" property="conta.referencia">
																<font color="#ff0000">${pagamento.formatarAnoMesPagamentoParaMesAno}</font>															
														   </logic:empty>	
													   </logic:notEmpty>
												       <logic:empty name="pagamento" property="conta.id">
													         <font color="#ff0000">${pagamento.formatarAnoMesPagamentoParaMesAno}</font>
												       </logic:empty>														
												  </logic:notEmpty>
												  <logic:empty name="pagamento" property="conta">
													<font color="#ff0000">${pagamento.formatarAnoMesPagamentoParaMesAno}</font>
												  </logic:empty>	
												
												</td>
												<td width="14%" align="right"><logic:notEmpty
													name="pagamento" property="conta">
													<logic:notEmpty name="pagamento"
														property="conta.valorTotal">
														<bean:write name="pagamento" property="conta.valorTotal"
															formatKey="money.format" />&nbsp;
													</logic:notEmpty>
												</logic:notEmpty></td>
												<td width="14%" align="right"><bean:write name="pagamento"
													property="valorPagamento" formatKey="money.format" />&nbsp;
												</td>
												<td width="14%" align="center">
													<a href="javascript:abrirPopup('exibirConsultarDadosPagamentoAction.do?idPagamento=<%=""+ pagamento.getId()%>' ,800,600);">
														<bean:write name="pagamento" property="dataPagamento" formatKey="date.format" />
													</a>
												</td>
												<td width="14%" align="center">
												${pagamento.avisoBancario.arrecadador.cliente.nome}&nbsp;
												</td>
												<td width="16%">
												${pagamento.pagamentoSituacaoAnterior.descricaoAbreviada}&nbsp;
												</td>
												<td width="16%">
												${pagamento.pagamentoSituacaoAtual.descricaoAbreviada}&nbsp;
												</td>
											</tr>
										</logic:iterate>
									</logic:notEmpty>
								</logic:present>



								<!-- Pagamento Historico -->

								<logic:present name="colecaoPagamentosHistoricoImovelConta"
									scope="session">
									<logic:notEmpty name="colecaoPagamentosHistoricoImovelConta"
										scope="session">
										<logic:iterate name="colecaoPagamentosHistoricoImovelConta"
											id="pagamentoHistorico" type="PagamentoHistorico">
											<%cont1 = cont1 + 1;
				if (cont1 % 2 == 0) {%>
											<tr bgcolor="#FFFFFF">
												<%} else {

				%>
											<tr bgcolor="#cbe5fe">
												<%}%>

												<td width="12%" align="center"><%--<logic:notEmpty
															name="pagamentoHistorico" property="conta">
															<logic:notEmpty name="pagamentoHistorico" property="conta.id">
																<logic:notEmpty name="pagamentoHistorico"
																	property="conta.referencia">
																	<a
																		href="javascript:abrirPopup('exibirConsultarContaAction.do?tipoConsulta=conta&contaID=<%="" + pagamentoHistorico.getConta().getId() %>' , 600, 800);"><font color="#ff0000" > ${pagamentoHistorico.conta.formatarAnoMesParaMesAno}</font></a>
																</logic:notEmpty>
															</logic:notEmpty>
														</logic:notEmpty>--%> <logic:present
													name="pagamentoHistorico"
													property="anoMesReferenciaPagamento">
													<font color="#ff0000">${pagamentoHistorico.formatarAnoMesReferenciaPagamento}</font>
												</logic:present></td>
												<td width="14%" align="right"><logic:notEmpty
													name="pagamentoHistorico" property="conta">
													<logic:notEmpty name="pagamentoHistorico"
														property="conta.valorTotal">
														<font color="#ff0000"> <bean:write
															name="pagamentoHistorico" property="conta.valorTotal"
															formatKey="money.format" /> </font>&nbsp;
													</logic:notEmpty>
												</logic:notEmpty></td>
												<td width="14%" align="right"><font color="#ff0000"> <bean:write
													name="pagamentoHistorico" property="valorPagamento"
													formatKey="money.format" /> </font> &nbsp;</td>
												<td width="14%" align="center"><font color="#ff0000"> 
													<a style="color: #ff0000" href="javascript:abrirPopup('exibirConsultarDadosPagamentoAction.do?pagamentoHistorico=OK&idPagamento=<%=""+ pagamentoHistorico.getId()%>' ,800,600);">
														<bean:write name="pagamentoHistorico" property="dataPagamento" formatKey="date.format" />
													</a>
												</td>
													
												<td width="14%" align="center">
													<font color="#ff0000">${pagamentoHistorico.avisoBancario.arrecadador.cliente.nome}</font>&nbsp;
												</td>
												<td width="16%"><font color="#ff0000">
												${pagamentoHistorico.pagamentoSituacaoAnterior.descricaoAbreviada}
												</font> &nbsp;</td>
												<td width="16%"><font color="#ff0000">
												${pagamentoHistorico.pagamentoSituacaoAtual.descricaoAbreviada}
												</font> &nbsp;</td>
											</tr>
										</logic:iterate>
									</logic:notEmpty>
								</logic:present>


								<%}else if( (session.getAttribute("qtdePagContas") != null) && ((Integer) session.getAttribute("qtdePagContas") > ConstantesSistema.NUMERO_MAXIMO_REGISTROS_CONSULTA_PAGAMENTO)) {%>
																<tr>
									<td height="190" colspan="7">
									<div style="width: 100%; height: 100%; overflow: auto;">
									<table width="100%">


										<logic:present name="colecaoPagamentosImovelConta"
											scope="session">
											<logic:notEmpty name="colecaoPagamentosImovelConta"
												scope="session">
												<logic:iterate name="colecaoPagamentosImovelConta"
													id="pagamento" type="Pagamento">
													<%cont1 = cont1 + 1;
				if (cont1 % 2 == 0) {%>
													<tr bgcolor="#FFFFFF">
														<%} else {

				%>
													<tr bgcolor="#cbe5fe">
														<%}%>

														<td width="12%" align="center"> <logic:notEmpty
															name="pagamento" property="conta">
															<logic:notEmpty name="pagamento" property="conta.id">
																<logic:notEmpty name="pagamento"
																	property="conta.referencia">
																	<a
																		href="javascript:abrirPopup('exibirConsultarContaAction.do?tipoConsulta=conta&contaSemCodigoBarras=1&contaID=<%="" + pagamento.getConta().getId() %>' , 600, 800);">${pagamento.conta.formatarAnoMesParaMesAno}</a>
																</logic:notEmpty>
														<logic:empty name="pagamento"
															property="conta.referencia">
																${pagamento.formatarAnoMesPagamentoParaMesAno}															
														</logic:empty>	
																
															</logic:notEmpty>
												<logic:empty
													name="pagamento" property="conta.id">
													${pagamento.formatarAnoMesPagamentoParaMesAno}
												</logic:empty>																
														</logic:notEmpty>
												<logic:empty
													name="pagamento" property="conta">
													${pagamento.formatarAnoMesPagamentoParaMesAno}
												</logic:empty>	
														
														
														</td>
														<td width="13%" align="right"><logic:notEmpty
															name="pagamento" property="conta">
															<logic:notEmpty name="pagamento"
																property="conta.valorTotal">
																<bean:write name="pagamento" property="conta.valorTotal"
																	formatKey="money.format" />&nbsp;
													</logic:notEmpty>
														</logic:notEmpty></td>
														<td width="16%" align="right"><bean:write name="pagamento"
															property="valorPagamento" formatKey="money.format" />&nbsp;
														</td>
														<td width="14%" align="center">
															<a href="javascript:abrirPopup('exibirConsultarDadosPagamentoAction.do?idPagamento=<%=""+ pagamento.getId()%>' ,800,600);">
																<bean:write name="pagamento" property="dataPagamento" formatKey="date.format" />
															</a>
														</td>
														<td width="15%" align="center">
														${pagamento.avisoBancario.arrecadador.cliente.nome}&nbsp;
														</td>
														<td width="17%">
														${pagamento.pagamentoSituacaoAnterior.descricaoAbreviada}&nbsp;
														</td>
														<td width="13.5%">
														${pagamento.pagamentoSituacaoAtual.descricaoAbreviada}&nbsp;
														</td>
													</tr>
												</logic:iterate>
											</logic:notEmpty>
										</logic:present>



										<!-- Pagamento Historico -->

										<logic:present name="colecaoPagamentosHistoricoImovelConta"
											scope="session">
											<logic:notEmpty name="colecaoPagamentosHistoricoImovelConta"
												scope="session">
												<logic:iterate name="colecaoPagamentosHistoricoImovelConta"
													id="pagamentoHistorico" type="PagamentoHistorico">
													<%cont1 = cont1 + 1;
				if (cont1 % 2 == 0) {%>
													<tr bgcolor="#FFFFFF">
														<%} else {

				%>
													<tr bgcolor="#cbe5fe">
														<%}%>

														<td width="12%" align="center"><logic:present
															name="pagamentoHistorico"
															property="anoMesReferenciaPagamento">
															<font color="#ff0000">${pagamentoHistorico.formatarAnoMesReferenciaPagamento}</font>
														</logic:present></td>
														<td width="13%" align="right"><logic:notEmpty
															name="pagamentoHistorico" property="conta">
															<logic:notEmpty name="pagamentoHistorico"
																property="conta.valorTotal">
																<font color="#ff0000"> <bean:write
																	name="pagamentoHistorico" property="conta.valorTotal"
																	formatKey="money.format" /> </font>&nbsp;
													</logic:notEmpty>
														</logic:notEmpty></td>
														<td width="16%" align="right"><font color="#ff0000"> <bean:write
															name="pagamentoHistorico" property="valorPagamento"
															formatKey="money.format" /> </font> &nbsp;</td>
														<td width="14%" align="center"> 
															<a style="color: #ff0000" href="javascript:abrirPopup('exibirConsultarDadosPagamentoAction.do?pagamentoHistorico=OK&idPagamento=<%=""+ pagamentoHistorico.getId()%>' ,800,600);">
																<bean:write name="pagamentoHistorico" property="dataPagamento" formatKey="date.format" />
															</a>
														</td>
														<td width="15%" align="center">
														<font color="#ff0000">${pagamentoHistorico.avisoBancario.arrecadador.cliente.nome}</font>&nbsp;
														</td>
														<td width="17%"><font color="#ff0000">
														${pagamentoHistorico.pagamentoSituacaoAnterior.descricaoAbreviada}
														</font> &nbsp;</td>
														<td width="13.5%"><font color="#ff0000">
														${pagamentoHistorico.pagamentoSituacaoAtual.descricaoAbreviada}
														</font> &nbsp;</td>
													</tr>
												</logic:iterate>
											</logic:notEmpty>
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
							<td align="center"><strong>Pagamentos das Guias de Pagamento</strong>
							</td>
						</tr>
						<%int contad = 1;%>

						<tr bgcolor="#cbe5fe">
							<td width="100%" align="center">
							<table width="100%" bgcolor="#90c7fc" class="fontePequena">
								<tr bordercolor="#000000">

									<td width="12%" align="center" rowspan="2"><strong>Cliente</strong>
									</td>
									<td width="11%" align="center" rowspan="2"><strong>Nr. Documento</strong></td>
									<td width="11%" align="center" rowspan="2"><strong>Valor da
									Prestação</strong></td>
									<td width="11%" align="center" rowspan="2"><strong>Valor do
									Pag.</strong></td>
									<td width="11%" align="center" rowspan="2"><strong>Data do Pag.</strong>
									</td>
									<td  width="14%" align="center" rowspan="2"><strong>Arrecadador</strong>
									</td>
									<td width=30% " align="center" colspan="2"><strong>Situação</strong>
									</td>
								</tr>
								<tr>
									<td width="15%" bgcolor="#cbe5fe" align="center"><strong>Anterior</strong></td>
									<td width="15%" bgcolor="#cbe5fe" align="center"><strong>Atual</strong></td>
								</tr>
								<%if((session.getAttribute("qtdePagGuiaPagamento") == null)) {%>
									<!-- Para carregar a tabela vazia -->

								<%}else if((session.getAttribute("qtdePagGuiaPagamento") != null) && ((Integer) session.getAttribute("qtdePagGuiaPagamento") <= ConstantesSistema.NUMERO_MAXIMO_REGISTROS_CONSULTA_PAGAMENTO)) {%>

								<logic:present name="colecaoPagamentosImovelGuiaPagamento"
									scope="session">
									<logic:notEmpty name="colecaoPagamentosImovelGuiaPagamento"
										scope="session">
										<logic:iterate name="colecaoPagamentosImovelGuiaPagamento"
											id="pagamento" type="Pagamento">
											<%contad = contad + 1;
				if (contad % 2 == 0) {%>
											<tr bgcolor="#FFFFFF">
												<%} else {

				%>
											<tr bgcolor="#cbe5fe">
												<%}%>

												<td width="12%" align="center">
													${pagamento.cliente.id}&nbsp;
												</td>
												<td width="11%" align="center">
												   <logic:present name="pagamento" property="guiaPagamentoGeral"> 
														<logic:present name="pagamento" property="guiaPagamentoGeral.guiaPagamento">
															<a 
																href="javascript:abrirPopup('exibirConsultarGuiaPagamentoAction.do?guiaPagamentoId=<%="" + pagamento.getGuiaPagamentoGeral().getGuiaPagamento().getId() %>')">${pagamento.guiaPagamentoGeral.id}/${pagamento.numeroPrestacao} 
															</a>&nbsp;
														</logic:present>
														<logic:present name="pagamento" property="guiaPagamentoGeral.guiaPagamentoHistorico">
														    ${pagamento.guiaPagamentoGeral.id}/${pagamento.numeroPrestacao}
														</logic:present>
													</logic:present>
													<logic:notPresent name="pagamento" property="guiaPagamentoGeral"> 
															NAO IDENTIFICADO															
													</logic:notPresent>	
												</td>
												<td width="11%" align="right">
													
													<!-- /* -->														  														  
													<font color="#ff0000">
												  
												  		<c:out value="${pagamento.valorPgt}"/>
												 															   														   													  
													</font>
													
												</td>
												<td width="11%" align="right">
													<bean:write name="pagamento" property="valorPagamento" formatKey="money.format" />&nbsp;
												</td>
												<td width="11%" align="center">
													<a  href="javascript:abrirPopup('exibirConsultarDadosPagamentoAction.do?idPagamento=<%=""+ pagamento.getId()%>' ,800,600);">
														<bean:write name="pagamento" property="dataPagamento" formatKey="date.format" />
													</a>
												</td>	
													
												<td width="14%" align="center">
													${pagamento.avisoBancario.arrecadador.cliente.nome}&nbsp;
												</td>
												<td width="15%">
													${pagamento.pagamentoSituacaoAnterior.descricaoAbreviada}&nbsp;
												</td>
												<td width="15%">
													${pagamento.pagamentoSituacaoAtual.descricaoAbreviada}&nbsp;
												</td>
											</tr>
										</logic:iterate>
									</logic:notEmpty>
								</logic:present>


								<!--  Historico -->

								<logic:present
									name="colecaoPagamentosHistoricoImovelGuiaPagamento"
									scope="session">
									<logic:notEmpty
										name="colecaoPagamentosHistoricoImovelGuiaPagamento"
										scope="session">
										<logic:iterate name="colecaoPagamentosHistoricoImovelGuiaPagamento" id="pagamentoHistorico" type="PagamentoHistorico">
											<%contad = contad + 1;
												if (contad % 2 == 0) {%>
												<tr bgcolor="#FFFFFF">
												<%} else {

											%>
												<tr bgcolor="#cbe5fe">
											<%}%>

												<td width="12%" align="center">
												${pagamento.cliente.id}&nbsp;</td>
												<td width="11%" align="center">
													<logic:notEmpty name="pagamentoHistorico" property="guiaPagamentoGeral">
														<logic:notEmpty name="pagamentoHistorico" property="guiaPagamentoGeral.guiaPagamento">
															<a
																href="javascript:abrirPopup('exibirConsultarGuiaPagamentoAction.do?guiaPagamentoId=<%="" + pagamentoHistorico.getGuiaPagamentoGeral().getGuiaPagamento().getId() %>')"><font
																color="#ff0000">${pagamentoHistorico.guiaPagamentoGeral.id}/${pagamentoHistorico.numeroPrestacao}</font></a>&nbsp;
														</logic:notEmpty>
														<logic:notEmpty name="pagamentoHistorico" property="guiaPagamentoGeral.guiaPagamentoHistorico">
															<font color="#ff0000">${pagamentoHistorico.guiaPagamentoGeral.id}/${pagamentoHistorico.numeroPrestacao}</font>&nbsp;
														</logic:notEmpty>
													</logic:notEmpty>
												
												<logic:notPresent name="pagamentoHistorico" property="guiaPagamentoGeral">														
														<font color="#ff0000">	${pagamentoHistorico.debitoTipo.descricao}</font>																										
												</logic:notPresent>																	
														</td>
												<td width="11%" align="right">
												  
													<!-- /* -->														  														  
													<font color="#ff0000">
												  
												  		<c:out value="${pagamentoHistorico.valorPgt}"/>
												 															   														   													  
													</font>  
												  		
												</td>
												<td width="11%" align="right"><font color="#ff0000"> <bean:write
													name="pagamentoHistorico" property="valorPagamento"
													formatKey="money.format" /> </font> &nbsp;</td>
												<td width="11%" align="center">
													<a style="color: #ff0000" href="javascript:abrirPopup('exibirConsultarDadosPagamentoAction.do?pagamentoHistorico=OK&idPagamento=<%=""+ pagamentoHistorico.getId()%>' ,800,600);">
														<bean:write name="pagamentoHistorico" property="dataPagamento" formatKey="date.format" />
													</a>&nbsp;
												</td>
												<td width="14%" align="center">
													<font color="#ff0000">${pagamentoHistorico.avisoBancario.arrecadador.cliente.nome}</font>&nbsp;
												</td>
												
												<td width="15%"><font color="#ff0000">
												${pagamentoHistorico.pagamentoSituacaoAnterior.descricaoAbreviada}
												</font> &nbsp;</td>
												<td width="15%"><font color="#ff0000">
												${pagamentoHistorico.pagamentoSituacaoAtual.descricaoAbreviada}
												</font> &nbsp;</td>
											</tr>
										</logic:iterate>
									</logic:notEmpty>
								</logic:present>



								<%}else if((session.getAttribute("qtdePagGuiaPagamento") != null) && ((Integer) session.getAttribute("qtdePagGuiaPagamento") > ConstantesSistema.NUMERO_MAXIMO_REGISTROS_CONSULTA_PAGAMENTO)) {%>
								
								<tr>
									<td height="190" colspan="8">
									<div style="width: 100%; height: 100%; overflow: auto;">
									<table width="100%">

										<logic:present name="colecaoPagamentosImovelGuiaPagamento"
											scope="session">
											<logic:notEmpty name="colecaoPagamentosImovelGuiaPagamento"
												scope="session">
												<logic:iterate name="colecaoPagamentosImovelGuiaPagamento"
													id="pagamento" type="Pagamento">
													<%contad = contad + 1;
				if (contad % 2 == 0) {%>
													<tr bgcolor="#FFFFFF">
														<%} else {

				%>
													<tr bgcolor="#cbe5fe">
														<%}%>

														<td width="12%" align="center">
														${pagamento.cliente.id}&nbsp;</td>
														<td width="11%" align="center">
														  <logic:present name="pagamento" property="guiaPagamentoGeral">
															<logic:present name="pagamento" property="guiaPagamentoGeral.guiaPagamento">
																<a
																	href="javascript:abrirPopup('exibirConsultarGuiaPagamentoAction.do?guiaPagamentoId=<%="" + pagamento.getGuiaPagamentoGeral().getId() %>')">${pagamento.guiaPagamentoGeral.id}/${pagamento.numeroPrestacao}</a>&nbsp;
															</logic:present>
															<logic:present name="pagamento" property="guiaPagamentoGeral.guiaPagamentoHistorico">
																${pagamento.guiaPagamentoGeral.id}/${pagamento.numeroPrestacao}&nbsp;
															</logic:present>
															
														  </logic:present>
													<logic:notPresent name="pagamento" property="guiaPagamentoGeral">	  															
															${pagamento.debitoTipo.descricao}																											
													</logic:notPresent>			
													</td>
														<td width="11%" align="right">
														   
														   <!-- /* -->														  														  
															<font color="#ff0000">
														  
														  		<c:out value="${pagamento.valorPgt}"/>
														 															   														   													  
															</font>
														   
														</td>
														<td width="11%" align="right"><bean:write name="pagamento"
															property="valorPagamento" formatKey="money.format" />&nbsp;
														</td>
														<td width="11%" align="center">
															<a  href="javascript:abrirPopup('exibirConsultarDadosPagamentoAction.do?idPagamento=<%=""+ pagamento.getId()%>' ,800,600);">
																<bean:write name="pagamento" property="dataPagamento" formatKey="date.format" />
															</a>&nbsp;
														</td>
														<td width="14%" align="center">
															${pagamento.avisoBancario.arrecadador.cliente.nome}&nbsp;
														</td>	
														
														<td width="15.5%">
														${pagamento.pagamentoSituacaoAnterior.descricaoAbreviada}&nbsp;
														</td>
														<td width="13.5%">
														${pagamento.pagamentoSituacaoAtual.descricaoAbreviada}&nbsp;
														</td>
													</tr>
												</logic:iterate>
											</logic:notEmpty>
										</logic:present>


										<!--  Historico -->

										<logic:present name="colecaoPagamentosHistoricoImovelGuiaPagamento" scope="session">
											
											<logic:notEmpty name="colecaoPagamentosHistoricoImovelGuiaPagamento" scope="session">
												
												<logic:iterate name="colecaoPagamentosHistoricoImovelGuiaPagamento" id="pagamentoHistorico" type="PagamentoHistorico">
													
													<%contad = contad + 1;
														if (contad % 2 == 0) {%>
													<tr bgcolor="#FFFFFF">
														<%} else {

														%>
													<tr bgcolor="#cbe5fe">
														<%}%>

														<td width="12%" align="center">
															${pagamentoHistorico.cliente.id}&nbsp;
														</td>
														<td width="11%" align="center">
															<logic:notEmpty name="pagamentoHistorico" property="guiaPagamentoGeral">
																<logic:notEmpty name="pagamentoHistorico" property="guiaPagamentoGeral.guiaPagamento">
																	<a
																		href="javascript:abrirPopup('exibirConsultarGuiaPagamentoAction.do?guiaPagamentoId=<%="" + pagamentoHistorico.getGuiaPagamentoGeral().getId() %>')"><font
																		color="#ff0000">${pagamentoHistorico.guiaPagamentoGeral.id}/${pagamentoHistorico.numeroPrestacao}</font></a>&nbsp;
																</logic:notEmpty>
																												
																<logic:notEmpty name="pagamentoHistorico" property="guiaPagamentoGeral.guiaPagamentoHistorico">
																   <font color="#ff0000">${pagamentoHistorico.guiaPagamentoGeral.id}/${pagamentoHistorico.numeroPrestacao}</font>&nbsp;
																</logic:notEmpty>
																															
															</logic:notEmpty>
															<logic:notPresent name="pagamentoHistorico" property="guiaPagamentoGeral">
																${pagamentoHistorico.debitoTipo.descricao}													
															</logic:notPresent>			
														</td>
														<td width="11%" align="right">
															
														  	<!-- /* -->														  														  
															<font color="#ff0000">
														  
														  		<c:out value="${pagamentoHistorico.valorPgt}"/>
														 															   														   													  
															</font>	
														
														</td>
														<td width="11%" align="right"><font color="#ff0000"> <bean:write
															name="pagamentoHistorico" property="valorPagamento"
															formatKey="money.format" /> </font> &nbsp;</td>
														<td width="11%" align="center">
															<a style="color: #ff0000" href="javascript:abrirPopup('exibirConsultarDadosPagamentoAction.do?pagamentoHistorico=OK&idPagamento=<%=""+ pagamentoHistorico.getId()%>' ,800,600);">
																<bean:write name="pagamentoHistorico" property="dataPagamento" formatKey="date.format" />
															</a>
														</td>
															
														<td width="14%" align="center">
															<font color="#ff0000">${pagamentoHistorico.avisoBancario.arrecadador.cliente.nome}</font>&nbsp;
														</td>
														<td width="15.5%"><font color="#ff0000">
															${pagamentoHistorico.pagamentoSituacaoAnterior.descricaoAbreviada}
														</font> &nbsp;</td>
														<td width="13.5%"><font color="#ff0000">
															${pagamentoHistorico.pagamentoSituacaoAtual.descricaoAbreviada}
														</font> &nbsp;</td>
													</tr>
												</logic:iterate>
												
											</logic:notEmpty>
											
										</logic:present>

									</table>
									</div>
									</td>
								</tr>


								<%}

			%>

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
							<td align="center"><strong>Pagamentos dos Débitos a Cobrar</strong>
							</td>
						</tr>
						<%int contador = 1;%>
						<tr bgcolor="#cbe5fe">
							<td width="100%" align="center">
							<table width="100%" bgcolor="#90c7fc" class="fontePequena">
								<tr bordercolor="#000000">
									<td width="23%" align="center" rowspan="2"><strong>Tipo do Débito</strong></td>
									<td width="10%" align="center" rowspan="2"><strong>Valor a Ser Cobrado</strong></td>
									<td width="10%" align="center" rowspan="2"><strong>Valor do Pag.</strong></td>
									<td width="6%" align="center" rowspan="2"><strong>Prest.</strong></td>
									<td width="11%" align="center" rowspan="2"><strong>Data do Pag.</strong></td>
									<td width="14%" align="center" rowspan="2"><strong>Arrecadador</strong></td>
									<td width="20%" align="center" colspan="2"><strong>Situação</strong></td>
								</tr>
								<tr>
									<td width="10%" bgcolor="#cbe5fe" align="center"><strong>Anterior</strong></td>
									<td width="10%" bgcolor="#cbe5fe" align="center"><strong>Atual</strong></td>
								</tr>

								<%--Esquema de paginação--%>
								<%if((session.getAttribute("qtdePagDebitoACobrar") == null)) {%>
									<!-- Para carregar a tabela vazia -->

								<%}else if((session.getAttribute("qtdePagDebitoACobrar") != null) && ((Integer) session.getAttribute("qtdePagDebitoACobrar") <= ConstantesSistema.NUMERO_MAXIMO_REGISTROS_CONSULTA_PAGAMENTO)) {%>

								<logic:present name="colecaoPagamentosImovelDebitoACobrar" scope="session">
									<logic:iterate name="colecaoPagamentosImovelDebitoACobrar" id="pagamento" type="Pagamento">
										<%contador = contador + 1;
				if (contador % 2 == 0) {%>
										<tr bgcolor="#FFFFFF">
											<%} else {%>
										<tr bgcolor="#cbe5fe">
											<%}%>

											<td width="25%" align="center">
											<logic:notEmpty name="pagamento" property="debitoACobrar">
												<a href="javascript:abrirPopup('exibirConsultarDebitoACobrarAction.do?imovelID=<bean:write name="pagamento" property="debitoACobrar.imovel.id" />&debitoID=<bean:write name="pagamento" property="debitoACobrar.id" />', 560, 660);">
												${pagamento.debitoACobrar.debitoTipo.descricao}&nbsp;</a>
											</logic:notEmpty>
											<logic:empty name="pagamento" property="debitoACobrar">
												${pagamento.debitoACobrar.debitoTipo.descricao}&nbsp;
											</logic:empty>				
											
											</td>
											<td width="12%" align="right">
												<logic:notEmpty name="pagamento" property="debitoACobrar">
													<logic:notEmpty name="pagamento" property="debitoACobrar.valorDebito">
														<logic:notEmpty name="pagamento" property="debitoACobrar.numeroPrestacaoDebito">
															<logic:notEmpty name="pagamento" property="debitoACobrar.numeroPrestacaoCobradas">
																<bean:write name="pagamento" property="debitoACobrar.valorRestanteCobrado" formatKey="money.format" />&nbsp;
															</logic:notEmpty>
														</logic:notEmpty>
													</logic:notEmpty>
												</logic:notEmpty>
											</td>
											<td width="12%" align="right">
												<a  href="javascript:abrirPopup('exibirConsultarDadosPagamentoAction.do?idPagamento=<%=""+ pagamento.getId()%>' ,800,600);">
														<bean:write name="pagamento" property="valorPagamento" formatKey="money.format" />
												</a>&nbsp;
											</td>
											<td width="6%" align="center">
												<logic:notEmpty name="pagamento" property="numeroPrestacao">
													${pagamento.numeroPrestacao}
												</logic:notEmpty>
												<logic:empty name="pagamento" property="numeroPrestacao">
													&nbsp;
												</logic:empty>
											</td>
											<td width="11%" align="center">
												<bean:write name="pagamento" property="dataPagamento" formatKey="date.format" />&nbsp;
											</td>
											<td width="14%" align="center">
												${pagamento.avisoBancario.arrecadador.cliente.nome}&nbsp;
											</td>
											<td width="10%">
												${pagamento.pagamentoSituacaoAnterior.descricaoAbreviada}&nbsp;
											</td>
											<td width="10%">
												${pagamento.pagamentoSituacaoAtual.descricaoAbreviada}&nbsp;
											</td>
										</tr>
									</logic:iterate>
								</logic:present>


								<!-- Historico -->

								<logic:present name="colecaoPagamentosHistoricoImovelDebitoACobrar" scope="session">
									<logic:iterate name="colecaoPagamentosHistoricoImovelDebitoACobrar" id="pagamentoHistorico" type="PagamentoHistorico">

										<%contador = contador + 1;
				if (contador % 2 == 0) {%>
										<tr bgcolor="#FFFFFF">
											<%} else {%>
										<tr bgcolor="#cbe5fe">
											<%}%>

											<td width="25%" align="center">
											<logic:notEmpty name="pagamentoHistorico" property="debitoACobrar">
											<a href="javascript:abrirPopup('exibirConsultarDebitoACobrarAction.do?imovelID=<bean:write name="pagamentoHistorico" property="debitoACobrar.imovel.id" />&debitoID=<bean:write name="pagamentoHistorico" property="debitoACobrar.id" />', 560, 660);">
												<font color="#ff0000"> ${pagamentoHistorico.debitoACobrar.debitoTipo.descricao} </font>
												&nbsp;
											</a>
											</logic:notEmpty> 
											<logic:empty name="pagamentoHistorico" property="debitoACobrar">
												<font color="#ff0000">${pagamentoHistorico.debitoACobrar.debitoTipo.descricao}&nbsp;</font>
											</logic:empty>				
											
											</td>
											<td width="12%" align="right">
												<logic:notEmpty name="pagamentoHistorico" property="debitoACobrar">
													<logic:notEmpty name="pagamentoHistorico" property="debitoACobrar.valorDebito">
														<logic:notEmpty name="pagamentoHistorico" property="debitoACobrar.numeroPrestacaoDebito">
															<logic:notEmpty name="pagamentoHistorico" property="debitoACobrar.numeroPrestacaoCobradas">
																<font color="#ff0000"> 
																	<bean:write name="pagamentoHistorico" property="debitoACobrar.valorRestanteCobrado" formatKey="money.format" /> 
																</font>		
																		&nbsp;
															</logic:notEmpty>
														</logic:notEmpty>
													</logic:notEmpty>
												</logic:notEmpty>
											</td>
											<td width="12%" align="right">
												<font color="#ff0000"> 
													<bean:write name="pagamentoHistorico" property="valorPagamento" formatKey="money.format" />
												</font> &nbsp;
											</td>
											<td width="6%" align="center">
												<logic:notEmpty name="pagamentoHistorico" property="numeroPrestacao">
													${pagamentoHistorico.numeroPrestacao}
												</logic:notEmpty>
												<logic:empty name="pagamentoHistorico" property="numeroPrestacao">
													&nbsp;
												</logic:empty>
											</td>
											<td width="11%" align="center">
												<a style="color: #ff0000" href="javascript:abrirPopup('exibirConsultarDadosPagamentoAction.do?pagamentoHistorico=OK&idPagamento=<%=""+ pagamentoHistorico.getId()%>' ,800,600);">
													<bean:write name="pagamentoHistorico" property="dataPagamento"	formatKey="date.format" />
												</a>
											</td>
											<td width="14%" align="center">
												<font color="#ff0000">${pagamentoHistorico.avisoBancario.arrecadador.cliente.nome}</font>&nbsp;
											</td>
											<td width="10%"><font color="#ff0000">
												${pagamentoHistorico.pagamentoSituacaoAnterior.descricaoAbreviada}
											</font> &nbsp;</td>
											<td width="10%"><font color="#ff0000">
												${pagamentoHistorico.pagamentoSituacaoAtual.descricaoAbreviada}
											</font> &nbsp;</td>
										</tr>
									</logic:iterate>
								</logic:present>

								<%}else if((session.getAttribute("qtdePagDebitoACobrar") != null) && ((Integer) session.getAttribute("qtdePagDebitoACobrar") > ConstantesSistema.NUMERO_MAXIMO_REGISTROS_CONSULTA_PAGAMENTO)) {%>
								<tr>
									<td height="190" colspan="8">
									<div style="width: 100%; height: 100%; overflow: auto;">
									<table width="100%">

										<logic:present name="colecaoPagamentosImovelDebitoACobrar" scope="session">
											<logic:iterate name="colecaoPagamentosImovelDebitoACobrar" id="pagamento" type="Pagamento">
												<%contador = contador + 1;
				if (contador % 2 == 0) {%>
												<tr bgcolor="#FFFFFF">
													<%} else {%>
												<tr bgcolor="#cbe5fe">
													<%}%>

													<td width="22%" align="center">
													<logic:notEmpty name="pagamento" property="debitoACobrar">
														<a href="javascript:abrirPopup('exibirConsultarDebitoACobrarAction.do?imovelID=<bean:write name="pagamento" property="debitoACobrar.imovel.id" />&debitoID=<bean:write name="pagamento" property="debitoACobrar.id" />', 560, 660);">
														${pagamento.debitoACobrar.debitoTipo.descricao}&nbsp;</a>
													</logic:notEmpty>
													
													<logic:empty name="pagamento" property="debitoACobrar">
														${pagamento.debitoACobrar.debitoTipo.descricao}&nbsp;
													</logic:empty>				
													
													</td>
													<td width="12%" align="right">
														<logic:notEmpty name="pagamento" property="debitoACobrar">
															<logic:notEmpty name="pagamento" property="debitoACobrar.valorDebito">
																<logic:notEmpty name="pagamento" property="debitoACobrar.numeroPrestacaoDebito">
																	<logic:notEmpty name="pagamento" property="debitoACobrar.numeroPrestacaoCobradas">
																		<bean:write name="pagamento" property="debitoACobrar.valorRestanteCobrado" formatKey="money.format" />&nbsp;
																	</logic:notEmpty>
																</logic:notEmpty>
															</logic:notEmpty>
														</logic:notEmpty>														
													</td>
													<td width="12%" align="right">
														<bean:write name="pagamento" property="valorPagamento" formatKey="money.format" />&nbsp;
													</td>
													<td width="6%" align="center">
														<logic:notEmpty name="pagamento" property="numeroPrestacao">
															${pagamento.numeroPrestacao}
														</logic:notEmpty>
														<logic:empty name="pagamento" property="numeroPrestacao">
															&nbsp;
														</logic:empty>
													</td>
													<td width="11%" align="center">
														<a style="color: #ff0000" href="javascript:abrirPopup('exibirConsultarDadosPagamentoAction.do?idPagamento=<%=""+ pagamento.getId()%>' ,800,600);">
															<bean:write name="pagamento" property="dataPagamento"	formatKey="date.format" />
														</a>
													</td>
													<td width="14%" align="center">
														${pagamento.avisoBancario.arrecadador.cliente.nome}&nbsp;
													</td>
													<td width="10%">
														${pagamento.pagamentoSituacaoAnterior.descricaoAbreviada}&nbsp;
													</td>
													<td width="10%">
														${pagamento.pagamentoSituacaoAtual.descricaoAbreviada}&nbsp;
													</td>
												</tr>
											</logic:iterate>
										</logic:present>


										<!-- Historico -->

										<logic:present name="colecaoPagamentosHistoricoImovelDebitoACobrar" scope="session">
											<logic:iterate name="colecaoPagamentosHistoricoImovelDebitoACobrar" id="pagamentoHistorico" type="PagamentoHistorico">

												<%contador = contador + 1;
				if (contador % 2 == 0) {%>
												<tr bgcolor="#FFFFFF">
													<%} else {%>
												<tr bgcolor="#cbe5fe">
													<%}%>

													<td width="22%" align="center">
														<logic:notEmpty name="pagamentoHistorico" property="debitoACobrar">
															<a href="javascript:abrirPopup('exibirConsultarDebitoACobrarAction.do?imovelID=<bean:write name="pagamentoHistorico" property="debitoACobrar.imovel.id" />&debitoID=<bean:write name="pagamentoHistorico" property="debitoACobrar.id" />', 560, 660);">
																<font color="#ff0000"> ${pagamentoHistorico.debitoACobrar.debitoTipo.descricao} </font> &nbsp;
													     	</a>
														</logic:notEmpty>
														<logic:empty name="pagamentoHistorico" property="debitoACobrar">
															<font color="#ff0000">${pagamentoHistorico.debitoACobrar.debitoTipo.descricao}&nbsp;</font>
														</logic:empty>				
													</td>
													
													<td width="12%" align="right">
														<logic:notEmpty name="pagamentoHistorico" property="debitoACobrar">
															<logic:notEmpty name="pagamentoHistorico" property="debitoACobrar.valorDebito">
																<logic:notEmpty name="pagamentoHistorico" property="debitoACobrar.numeroPrestacaoDebito">
																	<logic:notEmpty name="pagamentoHistorico" property="debitoACobrar.numeroPrestacaoCobradas">
																		<font color="#ff0000"> 
																		<bean:write name="pagamentoHistorico" property="debitoACobrar.valorRestanteCobrado" formatKey="money.format" /> </font>	&nbsp;
																	</logic:notEmpty>
																</logic:notEmpty>
															</logic:notEmpty>
														</logic:notEmpty>
													</td>
													
													<td width="12%" align="right"><font color="#ff0000">
														<bean:write name="pagamentoHistorico" property="valorPagamento" formatKey="money.format" /> </font> &nbsp;
													</td>
													<td width="6%" align="center">
														<logic:notEmpty name="pagamentoHistorico" property="numeroPrestacao">
															${pagamentoHistorico.numeroPrestacao}
														</logic:notEmpty>
														<logic:empty name="pagamentoHistorico" property="numeroPrestacao">
															&nbsp;
														</logic:empty>
													</td>												
													<td width="11%" align="center">
														<a style="color: #ff0000" href="javascript:abrirPopup('exibirConsultarDadosPagamentoAction.do?pagamentoHistorico=OK&idPagamento=<%=""+ pagamentoHistorico.getId()%>' ,800,600);">
															<bean:write name="pagamentoHistorico" property="dataPagamento"	formatKey="date.format" />
														</a>
													</td>
													<td width="14%" align="center">
														<font color="#ff0000">${pagamentoHistorico.avisoBancario.arrecadador.cliente.nome}</font>&nbsp;
													</td>
													<td width="10%"><font color="#ff0000">
														${pagamentoHistorico.pagamentoSituacaoAnterior.descricaoAbreviada}
													</font> &nbsp;</td>
													<td width="10%"><font color="#ff0000">
														${pagamentoHistorico.pagamentoSituacaoAtual.descricaoAbreviada}
													</font> &nbsp;</td>
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
				<td align="right">
						  <div align="right">
						   <a href="javascript:verificarExibicaoRelatorio();">
							<img border="0"
								src="<bean:message key="caminho.imagens"/>print.gif"
								title="Imprimir Pagamentos" /> </a>
						  </div>
						</td>
					</tr>
					
					</table>
					<p>&nbsp;</p>
				<table width="100%" border="0">
					<tr>
						<td colspan="2">
						<div align="right"><jsp:include
							page="/jsp/util/wizard/navegacao_botoes_wizard_consulta.jsp?numeroPagina=6" /></div>
						</td>
					</tr>
				</table>
		</td>
	</tr>
</table>
<jsp:include
		page="/jsp/relatorio/escolher_tipo_relatorio.jsp?relatorio=gerarRelatorioPagamentoAction.do" />
<logic:notPresent name="montarPopUp">	
		<%@ include file="/jsp/util/rodape.jsp"%>
</logic:notPresent>
</html:form>
</body>
<!-- imovel_consultar_pagamentos.jsp -->
</html:html>
