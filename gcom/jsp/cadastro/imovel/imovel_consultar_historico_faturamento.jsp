<%@page import="gcom.faturamento.debito.DebitoCreditoSituacao"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@ page import="gcom.util.Util"%>
<%@ page import="gcom.faturamento.conta.Conta" isELIgnored="false"%>
<%@ page import="gcom.faturamento.conta.ContaHistorico"
	isELIgnored="false"%>
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

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="ConsultarHistoricoFaturamentoActionForm" />

<script language="JavaScript">
<!--

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

    var form = document.forms[0];

    if (tipoConsulta == 'imovel') {
      form.idImovelHistoricoFaturamento.value = codigoRegistro;
      form.matriculaImovelHistoricoFaturamento.value = descricaoRegistro;
      form.matriculaImovelHistoricoFaturamento.style.color = "#000000";
	  form.action = 'consultarImovelWizardAction.do?action=exibirConsultarImovelHistoricoFaturamentoAction&indicadorNovo=OK'
	  form.submit();
    }
    
}

function limparForm(){
   	var form = document.forms[0];
	form.action = 'consultarImovelWizardAction.do?action=exibirConsultarImovelHistoricoFaturamentoAction&limparForm=OK'
	form.submit();
}

function limparImovelTecla() {

	var form = document.forms[0];
	
	form.matriculaImovelHistoricoFaturamento.value = "";

	if (form.digitoVerificadorImovelHistoricoFaturamento != undefined) {
		form.digitoVerificadorImovelHistoricoFaturamento.value = "";
	}
		
	form.situacaoAguaHistoricoFaturamento.value = "";
	form.situacaoEsgotoHistoricoFaturamento.value = "";
	form.tipoLigacao.value = "";

}


function verificarExibicaoRelatorio() {
    var form = document.forms[0];
    
    if (form.idImovelHistoricoFaturamento.value.length > 0) {
        toggleBox('demodiv',1);
    } else {
        alert('Informe Imóvel');
    }  
}
//-->
</SCRIPT>

</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('idImovelHistoricoFaturamento')">

<html:form action="/exibirConsultarImovelAction.do"
	name="ConsultarImovelActionForm"
	type="gcom.gui.cadastro.imovel.ConsultarImovelActionForm" method="post"
	onsubmit="return validateConsultarImovelActionForm(this);">

	<jsp:include
		page="/jsp/util/wizard/navegacao_abas_wizard_consulta.jsp?numeroPagina=4" />

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
							<td align="center"><strong>Dados do Imóvel</strong></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td width="100%" align="center">
							<table width="100%" border="0">
								<tr>
									<td bordercolor="#000000" width="25%"><strong>Im&oacute;vel:<font
										color="#FF0000">*</font></strong></td>
									<td width="75%" colspan="3"><html:text
										property="idImovelHistoricoFaturamento" maxlength="9" size="9"
										onkeypress="validaEnterComMensagem(event, 'consultarImovelWizardAction.do?action=exibirConsultarImovelHistoricoFaturamentoAction&indicadorNovo=OK&limparForm=S','idImovelHistoricoFaturamento','Im&oacute;vel');" 
										onkeyup="limparImovelTecla();"/>
									<a
										href="javascript:abrirPopup('exibirPesquisarImovelAction.do', 400, 800);">
									<img width="23" height="21"
										src="<bean:message key="caminho.imagens"/>pesquisa.gif"
										border="0" /></a> <logic:present
										name="idImovelHistoricoFaturamentoNaoEncontrado"
										scope="request">
										
										<logic:equal name="matriculaSemDigitoVerificador" value="0" scope="request">
											<html:text property="matriculaImovelHistoricoFaturamento"
												size="40" readonly="true"
												style="background-color:#EFEFEF; border:0; color: #ff0000" />
										</logic:equal>
										
										<logic:equal name="matriculaSemDigitoVerificador" value="1" scope="request">
											<html:text property="digitoVerificadorImovelHistoricoFaturamento"
												size="2" readonly="true"
												style="background-color:#EFEFEF; border:0; color: #ff0000" />
												
											<html:text property="matriculaImovelHistoricoFaturamento"
												size="31" readonly="true"
												style="background-color:#EFEFEF; border:0; color: #ff0000" />																						
										</logic:equal>

									</logic:present> <logic:notPresent
										name="idImovelHistoricoFaturamentoNaoEncontrado"
										scope="request">
										<logic:present name="valorMatriculaImovelHistoricoFaturamento"
											scope="request">
											
											<logic:equal name="matriculaSemDigitoVerificador" value="0" scope="request">											
												<html:text property="matriculaImovelHistoricoFaturamento"
													size="40" readonly="true"
													style="background-color:#EFEFEF; border:0; color: #000000" />
											</logic:equal>		
													
											<logic:equal name="matriculaSemDigitoVerificador" value="1" scope="request">
												<html:text property="digitoVerificadorImovelHistoricoFaturamento"
													size="2" readonly="true"
													style="background-color:#EFEFEF; border:0; color: #000000" />
													
												<html:text property="matriculaImovelHistoricoFaturamento"
													size="31" readonly="true"
													style="background-color:#EFEFEF; border:0; color: #000000" />																								
											</logic:equal>
																							
										</logic:present>
										<logic:notPresent
											name="valorMatriculaImovelHistoricoFaturamento"
											scope="request">
											
											<logic:equal name="matriculaSemDigitoVerificador" value="0" scope="request">											
												<html:text property="matriculaImovelHistoricoFaturamento"
													size="40" readonly="true"
													style="background-color:#EFEFEF; border:0; color: #000000" />
											</logic:equal>
											
											<logic:equal name="matriculaSemDigitoVerificador" value="1" scope="request">
												<html:text property="digitoVerificadorImovelHistoricoFaturamento"
													size="2" readonly="true"
													style="background-color:#EFEFEF; border:0; color: #000000" />
																								
												<html:text property="matriculaImovelHistoricoFaturamento"
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
									<td><html:text property="situacaoAguaHistoricoFaturamento"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000"
										size="15" maxlength="15" /></td>
									<td width="100"><strong>Situação de Esgoto:</strong></td>
									<td width="120"><html:text
										property="situacaoEsgotoHistoricoFaturamento" readonly="true"
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
								
							</table>
							</td>
						</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td colspan="3" height="10"></td>
				</tr>
				<tr>
					<td height="10">&nbsp;</td>
					<td></td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="3" height="5"></td>
				</tr>

				<tr>
					<td colspan="3">
					<table width="100%" border="0">
						<tr>
							<td colspan="4">
							<table width="100%" border="0" bgcolor="#99C7FC">
								<tr bgcolor="#79bbfd">
									<td bgcolor="#79bbfd" height="20" colspan="9"><strong>Histórico
									das Contas do Imóvel</strong></td>
								</tr>
								<% int qtdItens = 0;
								if ((request.getSession(true) != null) 
										&& (request.getSession(true).getAttribute("colecaoContaImovel") != null)) {
									qtdItens = qtdItens + ((Collection) request.getSession(true).getAttribute("colecaoContaImovel")).size();
								}
								if ((request.getSession(true) != null) 
										&& (request.getSession(true).getAttribute("colecaoContaHistoricoImovel") != null)) {
									qtdItens = qtdItens + ((Collection) request.getSession(true).getAttribute("colecaoContaHistoricoImovel")).size();
								}
								if (qtdItens <= ConstantesSistema.NUMERO_MAXIMO_REGISTROS_CONTAS_DEBITO) {
								%>
								<tr bordercolor="#FFFFFF" bgcolor="#99C7FC">
									<td width="10%" rowspan="2" bordercolor="#000000"
										bgcolor="#90c7fc">
									<div align="center"><font color="#000000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"><strong>M&ecirc;s/Ano</strong></font></div>
									</td>

									<td width="15%" rowspan="2" bordercolor="#000000"
										bgcolor="#90c7fc">
									<div align="center"><font color="#000000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"><strong>Vencimento</strong></font></div>
									</td>

									<td width="65%" colspan="6" bordercolor="#000000"
										bgcolor="#90c7fc">
									<div align="center"><font color="#000000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"><strong>VALORES
									DA CONTA</strong></font></div>
									</td>

									<td width="10%" rowspan="2" bordercolor="#000000"
										bgcolor="#90c7fc">
									<div align="left"><font color="#000000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"><strong>&nbsp;Situa&ccedil;&atilde;o</strong></font></div>
									</td>


								</tr>
								<tr bordercolor="#FFFFFF" bgcolor="#cbe5fe">

									<td width="10%" bordercolor="#cbe5fe" bgcolor="#cbe5fe">
									<div align="center"><font color="#000000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"><strong>&Aacute;gua</strong></font></div>
									</td>

									<td width="10%" bordercolor="#cbe5fe" bgcolor="#cbe5fe">
									<div align="center"></div>
									<div align="center"><font color="#000000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"><strong>Esgoto</strong></font></div>
									</td>
									<td width="10%" bordercolor="#cbe5fe" bgcolor="#cbe5fe">
									<div align="center"><font color="#000000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"><strong>D&eacute;bitos</strong></font></div>
									</td>
									<td width="10%" bordercolor="#cbe5fe" bgcolor="#cbe5fe">
									<div align="center"><font color="#000000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"><strong>Cr&eacute;ditos</strong></font></div>
									</td>
									<td width="10%" bordercolor="#cbe5fe" bgcolor="#cbe5fe">
									<div align="center"><font color="#000000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"><strong>Impostos</strong></font></div>
									</td>
									<td width="15%" bordercolor="#cbe5fe" bgcolor="#cbe5fe">
									<div align="center"><font color="#000000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"><strong>TOTAL</strong></font></div>
									</td>
								</tr>
								<%} else {%>
								<tr bordercolor="#FFFFFF" bgcolor="#99C7FC">


									<td width="10%" rowspan="2" bordercolor="#000000"
										bgcolor="#90c7fc">
									<div align="center"><font color="#000000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"><strong>M&ecirc;s/Ano</strong></font></div>
									</td>

									<td width="15%" rowspan="2" bordercolor="#000000"
										bgcolor="#90c7fc">
									<div align="center"><font color="#000000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"><strong>Vencimento</strong></font></div>
									</td>

									<td width="62%" colspan="6" bordercolor="#000000"
										bgcolor="#90c7fc">
									<div align="center"><font color="#000000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"><strong>VALORES
									DA CONTA</strong></font></div>
									</td>

									<td rowspan="2" bordercolor="#000000"
										bgcolor="#90c7fc">
									<div align="left"><font color="#000000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"><strong>Situa&ccedil;&atilde;o</strong></font></div>
									</td>


								</tr>
								<tr bordercolor="#FFFFFF" bgcolor="#cbe5fe">

									<td width="9%" bordercolor="#cbe5fe" bgcolor="#cbe5fe">
									<div align="center"><font color="#000000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"><strong>&Aacute;gua</strong></font></div>
									</td>

									<td width="10%" bordercolor="#cbe5fe" bgcolor="#cbe5fe">
									<div align="center"></div>
									<div align="center"><font color="#000000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"><strong>Esgoto</strong></font></div>
									</td>
									<td width="9%" bordercolor="#cbe5fe" bgcolor="#cbe5fe">
									<div align="center"><font color="#000000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"><strong>D&eacute;bitos</strong></font></div>
									</td>
									<td width="10%" bordercolor="#cbe5fe" bgcolor="#cbe5fe">
									<div align="center"><font color="#000000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"><strong>Cr&eacute;ditos</strong></font></div>
									</td>
									<td width="10%" bordercolor="#cbe5fe" bgcolor="#cbe5fe">
									<div align="center"><font color="#000000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"><strong>Impostos</strong></font></div>
									</td>
									<td width="14%" bordercolor="#cbe5fe" bgcolor="#cbe5fe">
									<div align="center"><font color="#000000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"><strong>TOTAL</strong></font></div>
									</td>
								</tr>
								<% }
								%>
							</table>

						<div style="width: 100%; height: 100; overflow: auto;">
						<table width="100%" bgcolor="#99C7FC">

						<logic:present name="colecaoCompleta">
						<%int cont = 0;%>
						<logic:iterate name="colecaoCompleta" id="contasObjeto" >

						<%
						Object objetoContaOuHistorico = (Object) pageContext.getAttribute("contasObjeto");
						
						cont = cont + 1;
						  if (cont % 2 == 0) {%>
							<tr bgcolor="#cbe5fe"> 
							<%} else {%>
							<tr bgcolor="#FFFFFF"> 
						<%}%>
						
						<% if(objetoContaOuHistorico instanceof Conta){ 
						
							Conta conta = (Conta) objetoContaOuHistorico;
							pageContext.setAttribute("conta", conta);
						%>
						<!-- Esquema pra exibir as contas =========================== -->
							
							<logic:present name="conta" property="contaMotivoRevisao">
									<td width="10%"><font color="#FF0000" style="font-size:9px"
										face="Verdana, Arial, Helvetica, sans-serif"> <a
										href="javascript:abrirPopup('exibirConsultarContaAction.do?tipoConsulta=conta&contaSemCodigoBarras=1&contaID=<%="" + conta.getId()%>' , 600, 800);"
										title="<%="" + Util.formatarMesAnoReferencia(conta.getReferencia())%>"><span
										style="color: #FF0000;">
											<%="" + Util.formatarMesAnoReferencia(conta.getReferencia())%></span></a> </font>
									</td>

											<td width="15%">
												<div align="center">
													<logic:present name="conta" property="dataVencimentoConta">
														<font color="#FF0000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
															<span style="color: #FF0000;"><%="" + Util.formatarData(conta.getDataVencimentoConta())%></span>
														</font>
													</logic:present>
													<logic:notPresent name="conta" property="dataVencimentoConta">
														<font color="#FF0000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
															&nbsp; 
														</font>
													</logic:notPresent>
												</div>
											</td>

											<td width="10%">
												<div align="right">
													<font color="#FF0000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
														<bean:write name="conta" property="valorAgua" formatKey="money.format" />
													</font>
												</div>
											</td>

											<td width="10%">
												<div align="right">
													<font color="#FF0000" style="font-size: 9px"
														face="Verdana, Arial, Helvetica, sans-serif">
														<bean:write name="conta" property="valorEsgoto"
															formatKey="money.format" />
													</font>
												</div>
											</td>

											<td width="10%">
												<div align="right">
													<logic:equal name="conta" property="debitos"
														value="0">
														<font color="#FF0000" style="font-size: 9px"
															face="Verdana, Arial, Helvetica, sans-serif">
															<bean:write name="conta" property="debitos"
																formatKey="money.format" />
														</font>
													</logic:equal>
													<logic:notEqual name="conta" property="debitos"
														value="0">
														<a
															href="javascript:abrirPopup('exibirConsultarDebitoCobradoAction.do?tipoConsulta=conta&contaID=<%="" + conta.getId()%>' , 500, 800);"
															title="<%="" + Util.formatarMesAnoReferencia(conta.getReferencia())%>">
															<font color="#FF0000" style="font-size: 9px"
															face="Verdana, Arial, Helvetica, sans-serif">
																<bean:write name="conta" property="debitos"
																	formatKey="money.format" />
														</font>
														</a>
													</logic:notEqual>
												</div>
											</td>

											<td width="10%">
												<div align="right">
													<logic:present name="conta"
														property="valorCreditos">
														<logic:equal name="conta"
															property="valorCreditos" value="0">
															<font color="#FF0000" style="font-size: 9px"
																face="Verdana, Arial, Helvetica, sans-serif">
																<bean:write name="conta"
																	property="valorCreditos"
																	formatKey="money.format" />
															</font>
														</logic:equal>
														<logic:notEqual name="conta"
															property="valorCreditos" value="0">
															<a
																href="javascript:abrirPopup('exibirConsultarCreditoRealizadoAction.do?tipoConsulta=conta&contaID=<%="" + conta.getId()%>' , 500, 800);"
																title="<%="" + Util.formatarMesAnoReferencia(conta.getReferencia())%>">
																<font color="#FF0000" style="font-size: 9px"
																face="Verdana, Arial, Helvetica, sans-serif">
																	<bean:write name="conta"
																		property="valorCreditos"
																		formatKey="money.format" />
															</font>
															</a>
														</logic:notEqual>
													</logic:present>
													<logic:notPresent name="conta"
														property="valorCreditos">
														<font color="#FF0000" style="font-size: 9px"
															face="Verdana, Arial, Helvetica, sans-serif">
															&nbsp; </font>
													</logic:notPresent>
												</div>
											</td>

											<td width="10%">
												<div align="right">
													<logic:present name="conta"
														property="valorImposto">
														<font color="#000000" style="font-size: 9px"
															face="Verdana, Arial, Helvetica, sans-serif">
															<bean:write name="conta"
																property="valorImposto"
																formatKey="money.format" />
														</font>
													</logic:present>
													<logic:notPresent name="conta"
														property="valorImposto">
														<font color="#000000" style="font-size: 9px"
															face="Verdana, Arial, Helvetica, sans-serif">
															0,00 </font>
													</logic:notPresent>
												</div>
											</td>

											<td width="15%">
												<div align="right">
													<font color="#000000" style="font-size: 9px"
														face="Verdana, Arial, Helvetica, sans-serif">
														<span style="color: #FF0000;"><%="" + Util.formatarMoedaReal(conta.getValorTotal())%></span>
													</font>
												</div>
											</td>

											<td>
												<div align="left">
													<logic:present name="conta"
														property="debitoCreditoSituacaoAtual">
														<font color="#FF0000" style="font-size: 9px"
															face="Verdana, Arial, Helvetica, sans-serif">
															<bean:write name="conta"
																property="debitoCreditoSituacaoAtual.descricaoAbreviada" />
														</font>
													</logic:present>
													<logic:notPresent name="conta" property="debitoCreditoSituacaoAtual">
														<font color="#FF0000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
															&nbsp; </font>
													</logic:notPresent>
												</div>
											</td>

							  </logic:present>
							<logic:notPresent name="conta" property="contaMotivoRevisao">
									 <td width="10%"><font color="#000000" style="font-size:9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <a
												href="javascript:abrirPopup('exibirConsultarContaAction.do?tipoConsulta=conta&contaSemCodigoBarras=1&contaID=<%="" + conta.getId()%>' , 600, 800);"
												title="<%="" + Util.formatarMesAnoReferencia(conta.getReferencia())%>"><span
												style="color: #000000;"><%=""
					+ Util.formatarMesAnoReferencia(conta.getReferencia())%></span></a> </font></td>

											<td width="15%">
											<div align="center"><logic:present name="conta"
												property="dataVencimentoConta">
												<font color="#000000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <span
													style="color: #000000;"><%=""
					+ Util.formatarData(conta.getDataVencimentoConta())%></span> </font>
											</logic:present> <logic:notPresent name="conta"
												property="dataVencimentoConta">
												<font color="#000000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> &nbsp; </font>
											</logic:notPresent></div>
											</td>

											<td width="10%">
											<div align="right"><font color="#000000"
												style="font-size:9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <bean:write
												name="conta" property="valorAgua" formatKey="money.format" />
											</font></div>
											</td>

											<td width="10%">
											<div align="right"><font color="#000000"
												style="font-size:9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <bean:write
												name="conta" property="valorEsgoto" formatKey="money.format" />
											</font></div>
											</td>

											<td width="10%">
											<div align="right"><logic:equal name="conta"
												property="debitos" value="0">
												<font color="#000000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <bean:write
													name="conta" property="debitos" formatKey="money.format" />
												</font>
											</logic:equal> <logic:notEqual name="conta"
												property="debitos" value="0">
												<a
													href="javascript:abrirPopup('exibirConsultarDebitoCobradoAction.do?tipoConsulta=conta&contaID=<%="" + conta.getId()%>' , 500, 800);"
													title="<%="" + Util.formatarMesAnoReferencia(conta.getReferencia())%>">
												<font color="#000000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <bean:write
													name="conta" property="debitos" formatKey="money.format" />
												</font> </a>
											</logic:notEqual></div>
											</td>

											<td width="10%">
											<div align="right"><logic:present name="conta"
												property="valorCreditos">
												<logic:equal name="conta" property="valorCreditos" value="0">
													<font color="#000000" style="font-size:9px"
														face="Verdana, Arial, Helvetica, sans-serif"> <bean:write
														name="conta" property="valorCreditos"
														formatKey="money.format" /> </font>
												</logic:equal>
												<logic:notEqual name="conta" property="valorCreditos"
													value="0">
													<a
														href="javascript:abrirPopup('exibirConsultarCreditoRealizadoAction.do?tipoConsulta=conta&contaID=<%="" + conta.getId()%>' , 500, 800);"
														title="<%="" + Util.formatarMesAnoReferencia(conta.getReferencia())%>">
													<font color="#000000" style="font-size:9px"
														face="Verdana, Arial, Helvetica, sans-serif"> <bean:write
														name="conta" property="valorCreditos"
														formatKey="money.format" /> </font> </a>
												</logic:notEqual>
											</logic:present> <logic:notPresent name="conta"
												property="valorCreditos">
												<font color="#000000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> &nbsp; </font>
											</logic:notPresent></div>
											</td>
											
											<td width="10%">
												<div align="right">
													<logic:present name="conta" property="valorImposto">
														<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
															<bean:write name="conta" property="valorImposto" formatKey="money.format" />
														</font>
													</logic:present>
													<logic:notPresent name="conta" property="valorImposto">
														<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
															0,00
														</font>
													</logic:notPresent>
												</div>
											</td>
											
											<td width="15%">
											<div align="right"><font color="#000000"
												style="font-size:9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <span
												style="color: #000000;"><%="" + Util.formatarMoedaReal(conta.getValorTotal())%></span> </font></div>
											</td>

											<td>
											<div align="left"><logic:present name="conta"
												property="debitoCreditoSituacaoAtual">
												
												<logic:equal name="conta" property="debitoCreditoSituacaoAtual.id" value="<%= DebitoCreditoSituacao.PRESCRITA.toString() %>">
													<font color="#ff0000" style="font-size: 9px"
														face="Verdana, Arial, Helvetica, sans-serif">
														<bean:write name="conta"
															property="debitoCreditoSituacaoAtual.descricaoAbreviada" />
													</font>
												</logic:equal>
												
												<logic:notEqual name="conta" property="debitoCreditoSituacaoAtual.id" value="<%= DebitoCreditoSituacao.PRESCRITA.toString() %>">
													<font color="#000000" style="font-size: 9px"
														face="Verdana, Arial, Helvetica, sans-serif">
														<bean:write name="conta"
															property="debitoCreditoSituacaoAtual.descricaoAbreviada" />
													</font>
												</logic:notEqual>
											</logic:present> <logic:notPresent name="conta"
												property="debitoCreditoSituacaoAtual">
												<font color="#000000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> &nbsp; </font>
											</logic:notPresent></div>
											</td>
							</logic:notPresent>

						<!-- Fim do Esquema pra exibir as contas =========================== -->

						<% } else if (objetoContaOuHistorico instanceof ContaHistorico) {
						
							ContaHistorico contaHistorico = (ContaHistorico) objetoContaOuHistorico;
							
							pageContext.setAttribute("contaHistorico", contaHistorico);
						%>

						<!-- Esquema pra exibir as contas do histórico =========================== -->
								<logic:present name="contaHistorico" property="contaMotivoRevisao">
								
									<td width="10%"><font color="#FF0000" style="font-size:9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <a
												href="javascript:abrirPopup('exibirConsultarContaAction.do?contaSemCodigoBarras=1&tipoConsulta=contaHistorico&contaID=<%="" + contaHistorico.getId()%>' , 600, 800);"
												title="<%="" + Util.formatarMesAnoReferencia(contaHistorico.getAnoMesReferenciaContabil())%>"><span
												style="color: #FF0000;">
												<%="" + Util.formatarMesAnoReferencia(contaHistorico .getAnoMesReferenciaContabil())%></span></a> 
												</font></td>

											<td width="15%">
											<div align="center"><logic:present name="contaHistorico"
												property="dataVencimentoConta">
												<font color="#FF0000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <span
													style="color: #FF0000;"><%=""
					+ Util
							.formatarData(contaHistorico
									.getDataVencimentoConta())%></span> </font>
											</logic:present> <logic:notPresent name="contaHistorico"
												property="dataVencimentoConta">
												<font color="#FF0000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> &nbsp; </font>
											</logic:notPresent></div>
											</td>

											<td width="10%">
											<div align="right"><font color="#FF0000"
												style="font-size:9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <bean:write
												name="contaHistorico" property="valorAgua"
												formatKey="money.format" /> </font></div>
											</td>

											<td width="10%">
											<div align="right"><font color="#FF0000"
												style="font-size:9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <bean:write
												name="contaHistorico" property="valorEsgoto"
												formatKey="money.format" /> </font></div>
											</td>

											<td width="10%">
											<div align="right"><logic:equal name="contaHistorico"
												property="valorDebitos" value="0">
												<font color="#FF0000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <bean:write
													name="contaHistorico" property="valorDebitos"
													formatKey="money.format" /> </font>
											</logic:equal> <logic:notEqual name="contaHistorico"
												property="valorDebitos" value="0">
												<a
													href="javascript:abrirPopup('exibirConsultarDebitoCobradoAction.do?tipoConsulta=contaHistorico&contaID=<%="" + contaHistorico.getId()%>' , 500, 800);"
													title="<%="" + Util.formatarMesAnoReferencia(contaHistorico.getAnoMesReferenciaConta())%>">
												<font color="#FF0000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <bean:write
													name="contaHistorico" property="valorDebitos"
													formatKey="money.format" /> </font> </a>
											</logic:notEqual></div>
											</td>

											<td width="10%">
											<div align="right"><logic:present name="contaHistorico"
												property="valorCreditos">
												<logic:equal name="contaHistorico" property="valorCreditos"
													value="0">
													<font color="#FF0000" style="font-size:9px"
														face="Verdana, Arial, Helvetica, sans-serif"> <bean:write
														name="contaHistorico" property="valorCreditos"
														formatKey="money.format" /> </font>
												</logic:equal>
												<logic:notEqual name="contaHistorico"
													property="valorCreditos" value="0">
													<a
														href="javascript:abrirPopup('exibirConsultarCreditoRealizadoAction.do?tipoConsulta=contaHistorico&contaID=<%="" + contaHistorico.getId()%>' , 500, 800);"
														title="<%="" + Util.formatarMesAnoReferencia(contaHistorico.getAnoMesReferenciaConta())%>">
													<font color="#FF0000" style="font-size:9px"
														face="Verdana, Arial, Helvetica, sans-serif"> <bean:write
														name="contaHistorico" property="valorCreditos"
														formatKey="money.format" /> </font> </a>
												</logic:notEqual>
											</logic:present> <logic:notPresent name="contaHistorico"
												property="valorCreditos">
												<font color="#FF0000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> &nbsp; </font>
											</logic:notPresent></div>
											</td>

											<td width="10%">
												<div align="right">
													<logic:present name="contaHistorico" property="valorImposto">
														<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
															<bean:write name="contaHistorico" property="valorImposto" formatKey="money.format" />
														</font>
													</logic:present>
													<logic:notPresent name="contaHistorico" property="valorImposto">
														<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
															0,00
														</font>
													</logic:notPresent>
												</div>
											</td>

											<td width="15%">
											<div align="right"><font color="#000000"
												style="font-size:9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <span
												style="color: #FF0000;"><%=""
					+ Util.formatarMoedaReal(contaHistorico.getValorTotal())%></span> </font></div>
											</td>

											<td>
											<div align="left"><logic:present name="contaHistorico"
												property="debitoCreditoSituacaoAtual">
												<font color="#FF0000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <bean:write
													name="contaHistorico"
													property="debitoCreditoSituacaoAtual.descricaoAbreviada" />
												</font>
											</logic:present> <logic:notPresent name="contaHistorico"
												property="debitoCreditoSituacaoAtual">
												<font color="#FF0000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> &nbsp; </font>
											</logic:notPresent></div>
											</td>
								</logic:present>
								
									<logic:notPresent name="contaHistorico" property="contaMotivoRevisao">
										<td width="10%"><font color="#000000" style="font-size:9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <a
												href="javascript:abrirPopup('exibirConsultarContaAction.do?contaSemCodigoBarras=1&tipoConsulta=contaHistorico&contaID=<%="" + contaHistorico.getId()%>' , 600, 800);"
												title="<%="" + Util.formatarMesAnoReferencia(contaHistorico.getAnoMesReferenciaContabil())%>"><span
												style="color: #000000;">
												<%="" + Util.formatarMesAnoReferencia(contaHistorico.getAnoMesReferenciaContabil())%></span></a> 
												</font></td>

											<td width="15%">
											<div align="center"><logic:present name="contaHistorico"
												property="dataVencimentoConta">
												<font color="#000000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <span
													style="color: #000000;">
													<%="" + Util.formatarData(contaHistorico.getDataVencimentoConta())%></span> 
												</font>
											</logic:present> <logic:notPresent name="contaHistorico"
												property="dataVencimentoConta">
												<font color="#000000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> &nbsp; </font>
											</logic:notPresent></div>
											</td>

											<td width="10%">
											<div align="right"><font color="#000000"
												style="font-size:9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <bean:write
												name="contaHistorico" property="valorAgua"
												formatKey="money.format" /> </font></div>
											</td>

											<td width="10%">
											<div align="right"><font color="#000000"
												style="font-size:9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <bean:write
												name="contaHistorico" property="valorEsgoto"
												formatKey="money.format" /> </font></div>
											</td>

											<td width="10%">
											<div align="right"><logic:equal name="contaHistorico"
												property="valorDebitos" value="0">
												<font color="#000000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <bean:write
													name="contaHistorico" property="valorDebitos"
													formatKey="money.format" /> </font>
											</logic:equal> <logic:notEqual name="contaHistorico"
												property="valorDebitos" value="0">
												<a
													href="javascript:abrirPopup('exibirConsultarDebitoCobradoAction.do?tipoConsulta=contaHistorico&contaID=<%="" + contaHistorico.getId()%>' , 500, 800);"
													title="<%="" + Util.formatarMesAnoReferencia(contaHistorico.getAnoMesReferenciaConta())%>">
												<font color="#000000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <bean:write
													name="contaHistorico" property="valorDebitos"
													formatKey="money.format" /> </font> </a>
											</logic:notEqual></div>
											</td>

											<td width="10%">
											<div align="right"><logic:present name="contaHistorico"
												property="valorCreditos">
												<logic:equal name="contaHistorico" property="valorCreditos"
													value="0">
													<font color="#000000" style="font-size:9px"
														face="Verdana, Arial, Helvetica, sans-serif"> <bean:write
														name="contaHistorico" property="valorCreditos"
														formatKey="money.format" /> </font>
												</logic:equal>
												<logic:notEqual name="contaHistorico"
													property="valorCreditos" value="0">
													<a
														href="javascript:abrirPopup('exibirConsultarCreditoRealizadoAction.do?tipoConsulta=contaHistorico&contaID=<%="" + contaHistorico.getId()%>' , 500, 800);"
														title="<%="" + Util.formatarMesAnoReferencia(contaHistorico.getAnoMesReferenciaConta())%>">
													<font color="#000000" style="font-size:9px"
														face="Verdana, Arial, Helvetica, sans-serif"> <bean:write
														name="contaHistorico" property="valorCreditos"
														formatKey="money.format" /> </font> </a>
												</logic:notEqual>
											</logic:present> <logic:notPresent name="contaHistorico"
												property="valorCreditos">
												<font color="#000000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> &nbsp; </font>
											</logic:notPresent></div>
											</td>

											<td width="10%">
												<div align="right">
													<logic:present name="contaHistorico" property="valorImposto">
														<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
															<bean:write name="contaHistorico" property="valorImposto" formatKey="money.format" />
														</font>
													</logic:present>
													<logic:notPresent name="contaHistorico" property="valorImposto">
														<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
															0,00
														</font>
													</logic:notPresent>
												</div>
											</td>

											<td width="15%">
											<div align="right"><font color="#000000"
												style="font-size:9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <span
												style="color: #000000;"><%=""
					+ Util.formatarMoedaReal(contaHistorico.getValorTotal())%></span> </font></div>
											</td>

											<td>
											<div align="left"><logic:present name="contaHistorico"
												property="debitoCreditoSituacaoAtual">
												
												<logic:equal name="contaHistorico" property="debitoCreditoSituacaoAtual.id" value="<%= DebitoCreditoSituacao.PRESCRITA.toString() %>">
													<font color="#ff0000" style="font-size: 9px"
														face="Verdana, Arial, Helvetica, sans-serif">
														<bean:write name="contaHistorico"
															property="debitoCreditoSituacaoAtual.descricaoAbreviada" />
													</font>
												</logic:equal>
												
												<logic:notEqual name="contaHistorico" property="debitoCreditoSituacaoAtual.id" value="<%= DebitoCreditoSituacao.PRESCRITA.toString() %>">
													<font color="#000000" style="font-size: 9px"
														face="Verdana, Arial, Helvetica, sans-serif">
														<bean:write name="contaHistorico"
															property="debitoCreditoSituacaoAtual.descricaoAbreviada" />
													</font>
												</logic:notEqual>
											</logic:present> <logic:notPresent name="contaHistorico"
												property="debitoCreditoSituacaoAtual">
												<font color="#000000" style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> &nbsp; </font>
											</logic:notPresent></div>
											</td>
									</logic:notPresent>
								
								
						<!-- Fim do Esquema pra exibir as contas do histórico =========================== -->
						
						<%}%>
						
						
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
            <p>&nbsp;</p>
            <table width="100%" border="0">
                <tr>
                    <td align="right">
                        <div align="right">
                            <a href="javascript:verificarExibicaoRelatorio();">
                                <img border="0" src="<bean:message key="caminho.imagens"/>print.gif" title="Imprimir Pagamentos" /> 
                            </a>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                    <div align="right"><jsp:include
                        page="/jsp/util/wizard/navegacao_botoes_wizard_consulta.jsp?numeroPagina=4" /></div>
                    </td>
                </tr>
            </table>

            </td>
        </tr>
    </table>
<jsp:include
        page="/jsp/relatorio/escolher_tipo_relatorio.jsp?relatorio=gerarRelatorioHistoricoFaturamentoAction.do" />
<logic:notPresent name="montarPopUp">   
        <%@ include file="/jsp/util/rodape.jsp"%>
</logic:notPresent>

</html:form>

</body>
<!-- imovel_consultar_historico_faturamento.jsp -->
</html:html>
