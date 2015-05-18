<%@page import="gcom.faturamento.debito.DebitoCreditoSituacao"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<%@ page import="gcom.util.Util"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="gcom.faturamento.conta.Conta" isELIgnored="false"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="ManterContaActionForm" />


<SCRIPT LANGUAGE="JavaScript">
<!--

function retificar(idConta){

 	var form = document.forms[0];
    form.action = "/gsan/exibirRetificarContaAction.do?contaID="+idConta+"&idImovel="+form.idImovel.value+"&indicadorOperacao=retificar";
    form.submit();
}



function cancelarConta(form){
	
	if(!isQtdContasPermitida()){
		return;
	}

	var mensagem = "Nenhuma Conta foi selecionada para cancelamento.";

	if (validateManterContaActionForm(form)){
		if (CheckboxNaoVazioMensagemGenerico(mensagem, "")){
			if(confirm('TEM CERTEZA QUE DESEJA CANCELAR FATURA ?')){
				var urlCancelamentoConta = "/gsan/exibirCancelarContaAction.do?conta=" + obterValorCheckboxMarcado()+"&idImovel="+form.idImovel.value;  
				abrirPopup(urlCancelamentoConta, 295, 450);
			}
		}
	}
}


function colocarContaEmRevisao(form){
	
	if(!isQtdContasPermitida()){
		return;
	}

	var mensagem = "Nenhuma Conta foi selecionada para ser colocada em revisão.";

	if (validateManterContaActionForm(form)){
		if (CheckboxNaoVazioMensagemGenerico(mensagem, "")){
			var urlColocarRevisaoConta = "/gsan/exibirColocarRevisaoContaAction.do?conta=" + obterValorCheckboxMarcado()+"&idImovel="+form.idImovel.value;  
			abrirPopup(urlColocarRevisaoConta, 295, 450);
		}
	}
}


function retirarContaDeRevisao(form){
	
	if(!isQtdContasPermitida()){
		return;
	}

	var mensagem = "Nenhuma Conta foi selecionada para ser retirada de revisão.";

	if (validateManterContaActionForm(form)){
		if (CheckboxNaoVazioMensagemGenerico(mensagem, "")){
			var urlColocarRevisaoConta = "/gsan/retirarRevisaoContaAction.do?conta=" + obterValorCheckboxMarcado()+"&idImovel="+form.idImovel.value;  
			form.action = urlColocarRevisaoConta;
			
			if (confirm("Confirma retirada de revisão?")){
				submeterFormPadrao(form);
			}
		}
	}
}


function alterarVencimento(form){
	
	if(!isQtdContasPermitida()){
		return;
	}

	var mensagem = "Nenhuma Conta foi selecionada para ser alterado o vencimento.";

	if (validateManterContaActionForm(form)){
		if (CheckboxNaoVazioMensagemGenerico(mensagem, "")){
			var urlAlterarVencimentoConta = "/gsan/exibirAlterarVencimentoContaAction.do?conta=" + obterValorCheckboxMarcado()+"&idImovel="+form.idImovel.value;  
			form.action = urlAlterarVencimentoConta;
			abrirPopup(urlAlterarVencimentoConta, 295, 450);
		}
	}
}


function emitirSegundaViaConta(form){
	
	if(!isQtdContasPermitida()){
		return;
	}
	var mensagem = "Nenhuma Conta foi selecionada para emitir segunda via de conta.";

	if (validateManterContaActionForm(form)){
		if (CheckboxNaoVazioMensagemGenerico(mensagem, "")){
			var urlEmitirSegundaViaConta = "/gsan/gerarRelatorio2ViaContaAction.do?idNomeRelatorio=1&conta=" + obterValorCheckboxMarcado() + "&indicadorOperacao=emitir2viaConta";  
			form.action = urlEmitirSegundaViaConta;
			redirecionarSubmit(urlEmitirSegundaViaConta);
			
			/*if (confirm('A impressão da 2ª Via de Conta irá gerar taxa de cobranca. Confirma?')){
				redirecionarSubmit(urlEmitirSegundaViaConta);
			}*/
		}
	}

}

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

    var form = document.forms[0];

    if (tipoConsulta == 'imovel') {
      form.idImovel.value = codigoRegistro;
      form.submit();
    }
 }
 
 
 function limparForm(){
 	var form = document.forms[0];
 	
 	for (var i=0;i < form.elements.length;i++){
		var elemento = form.elements[i];
		if (elemento.type == "text"){
			elemento.value = "";
		}
	}
	
	redirecionarSubmit("exibirManterContaAction.do?limparForm=OK");
 }

function replicaDados(campoOrigem, campoDestino){
	campoDestino.value = campoOrigem.value;
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

function validarForm(formulario){

	limparMotivosDisponiveis();
	formulario.action = 'exibirManterContaAction.do';
	if (validateManterContaActionForm(formulario)){
		formulario.submit();
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

function caucionarConta(form){
	
	if(!isQtdContasPermitida()){
		return;
	}

	var mensagem = "Nenhuma Conta foi selecionada para ser caucionada.";
	var msgVariosRegistrosSelecionados = "Selecione apenas uma conta para caucionar.";
	
	if (validateManterContaActionForm(form)){
		if (CheckboxNaoVazioMensagemGenerico(mensagem, "") && obterQuantidadeCheckboxMarcado(msgVariosRegistrosSelecionados, "")){
			var urlCaucionarConta = "/gsan/exibirRetificarContaAction.do?contaID=" + obterValorCheckboxMarcado() + "&idImovel=" + form.idImovel.value + "&indicadorOperacao=caucionar";  
			form.action = urlCaucionarConta;
			
			if (confirm("Confirma caucionar a conta?")){
				submeterFormPadrao(form);
			}
		}
	}
}

function isQtdContasPermitida() {
	var mensagem = "Limite máximo de contas selecionadas excedido. \nPor favor selecione até <bean:write name="limiteContasSelecionadas" scope="session"/> contas.";
	
	return obterQuantidadeCheckboxMarcado(mensagem, null, <bean:write name="limiteContasSelecionadas" scope="session"/> );
	
}
 
//-->
</SCRIPT>

</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');">

<html:form action="/exibirManterContaAction" method="post">

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
					<td class="parabg">Manter Conta</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td>Para manter a(s) conta(s), informe os dados abaixo:</td>
					<td align="right"></td>					
				</tr>
			</table>

			<table width="100%" border="0">
				<tr>
					<td height="10" width="160"><strong>Matrícula do Imóvel:<font
						color="#FF0000">*</font></strong></td>
					<td width="403"><html:text property="idImovel" maxlength="9"
						size="9"
						onkeypress="validaEnterComMensagem(event, 'exibirManterContaAction.do', 'idImovel', 'Matrícula do Imóvel')" />
					<a
						href="javascript:abrirPopup('exibirPesquisarImovelAction.do', 400, 800);">
					<img width="23" height="21"
						src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" /></a>
					
					<logic:present name="corInscricao">
	
						<logic:equal name="corInscricao" value="exception">
							<html:text property="inscricaoImovel" size="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
						</logic:equal>
		
						<logic:notEqual name="corInscricao" value="exception">
							<html:text property="inscricaoImovel" size="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
						</logic:notEqual>
		
					</logic:present>
		
					<logic:notPresent name="corInscricao">
		
						<logic:empty name="ManterContaActionForm" property="idImovel">
							<html:text property="inscricaoImovel" size="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
						</logic:empty>
						<logic:notEmpty name="ManterContaActionForm" property="idImovel">
							<html:text property="inscricaoImovel" size="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
						</logic:notEmpty>
						
		
					</logic:notPresent>
					
					<a href="javascript:limparForm();" tabindex="1"> 
						<img border="0" src="<bean:message key='caminho.imagens'/>limparcampo.gif" style="cursor: hand;" /> 
					</a>	
					</td>
					<td width="43">&nbsp;</td>
				</tr>	
				<tr>
					<td><strong>Período de Referência das Contas:</strong></td>
					<td>
						<strong>
						<html:text maxlength="7" property="anoMesReferenciaContaInicio" size="7"
							onkeyup="mascaraAnoMes(this, event);  replicaDados(document.forms[0].anoMesReferenciaContaInicio, document.forms[0].anoMesReferenciaContaFim);" /> 						
						a</strong> 
						<html:text maxlength="7" property="anoMesReferenciaContaFim" size="7" onkeyup="mascaraAnoMes(this, event);" /> 
						(mm/aaaa)
					</td>
				</tr>
				<tr>
					<td><strong>Período de Vencimento das Contas:</strong></td>
					<td>
						<strong>
						<html:text maxlength="10" property="dataPagamentoContaInicio" size="10"
							onkeyup="mascaraData(this, event);  replicaDados(document.forms[0].dataPagamentoContaInicio, document.forms[0].dataPagamentoContaFim);" /> 
						<a href="javascript:abrirCalendarioReplicando('ManterContaActionForm', 'dataPagamentoContaInicio','dataPagamentoContaFim');">
							<img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif"
								width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
						a</strong> 
						<html:text maxlength="10" property="dataPagamentoContaFim" size="10" onkeyup="mascaraData(this, event);" /> 
						<a href="javascript:abrirCalendario('ManterContaActionForm', 'dataPagamentoContaFim')">
							<img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif"
								width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
						(dd/mm/aaaa)
					</td>
				</tr>	
				<tr>

					<td>
						<strong>Selecionar apenas as contas em revisão?</strong>
					</td>
					<td>
						<html:radio property="inContasRevisao" value="1" /> <strong>Sim</strong>
						<html:radio property="inContasRevisao" value="2" /> <strong>Não</strong>
					</td>
				</tr>
				<tr>
					<td width="110">
						<strong>Motivo de Revisão:</strong>					</td>
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
					<td height="10">&nbsp;</td>
					<td><strong><font color="#FF0000">*</font></strong> Campos
					obrigat&oacute;rios</td>
					<td>&nbsp;</td>
				</tr>

				<tr>
					<td width="65" align="right"></td>
					<td>
					<div align="right"><input name="btnfiltrar" class="bottonRightCol" 
  					    onclick="javascript:validarForm(document.forms[0]);" 
						value="Filtrar" type="button"></div>
					</td>
					
				</tr>
				
				<tr>
					<td colspan="3"><hr></td>
				</tr>
				
				<tr>
					<td colspan="3">

					<table width="100%" align="center" bgcolor="#90c7fc" border="0">
						<tr>
							<td><strong>Dados do Imóvel</strong></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td width="100%" align="center">

							<table width="100%" border="0">
								<tr>
									<td height="10"><strong>Nome do Cliente Usuário:</strong></td>
									<td><html:text property="nomeClienteUsuario" size="45"
										readonly="true" style="background-color:#EFEFEF; border:0" />
									</td>
								</tr>
								<tr>
									<td height="10"><strong>Nome do Cliente Responsável:</strong></td>
									<td><html:text property="nomeClienteResponsavel" size="45"
										readonly="true" style="background-color:#EFEFEF; border:0" />
									</td>
								</tr>
								<tr>
									<td height="10"><strong>Situação de Água:</strong></td>
									<td><html:text property="situacaoAguaImovel" size="45"
										readonly="true" style="background-color:#EFEFEF; border:0" />
									</td>
								</tr>
								<tr>
									<td height="10"><strong>Situação de Esgoto:</strong></td>
									<td><html:text property="situacaoEsgotoImovel" size="45"
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
					<td colspan="3">

					<table width="100%" border="0">
						<tr>
							<td colspan="4">
							<table width="100%" cellpadding="0" cellspacing="0">
								<tr>
									<td>
									<table width="100%" border="0" bgcolor="#90c7fc">
										<tr>
											<td bgcolor="#79bbfd" colspan="9" height="20">
												<strong>Contas do Imóvel</strong>
											</td>
										</tr>
										<tr bgcolor="#90c7fc">
										<%
												String pctQtdColunasContas="9";
										
												String pctReferencia    = "10%";
												String pctDataVencimento   = "12%";
												String pctValorConta = "11%";
												String pctValorAgua   = "11%";
												String pctValorEsgoto 	 = "11%";
												String pctValidade  = "12%";
												String pctRevisao  = "12%";
												String pctSituacao  = "14%";
												String pctDividaAtivaGuia  = "0%";
										
												
												if (session.getAttribute("exibirDividaAtivaColuna") != null) {
													pctReferencia    = "8%";
													pctDataVencimento   = "10%";
													pctValorConta = "8%";
													pctValorAgua   = "8%";
													pctValorEsgoto 	 = "8%";
													pctValidade  = "10%";
													pctRevisao  = "10%";
													pctSituacao  = "10%";
													pctDividaAtivaGuia  = "8%";
													
													pctQtdColunasContas      = "10";
												}						
										%>
											
											<td align="center" width="7%">
											<div align="center"><strong><a
												href="javascript:facilitador(this);" id="0">Todos</a></strong></div>
											</td>
											
											<td width="<%= pctReferencia%>">
											<div align="center"><strong>Refe.</strong></div>
											</td>
											<td width="<%= pctDataVencimento%>">
											<div align="center"><strong>Venc.</strong></div>
											</td>
											<td width="<%= pctValorConta%>">
											<div align="center"><strong>Valor</strong></div>
											</td>
											<td width="<%= pctValorAgua%>">
											<div align="center"><strong>Água</strong></div>
											</td>
											<td width="<%= pctValorEsgoto%>">
											<div align="center"><strong>Esgoto</strong></div>
											</td>
											<td width="<%= pctValidade%>">
											<div align="center"><strong>Validade</strong></div>
											</td>
											<td width="<%= pctRevisao%>">
											<div align="center"><strong>Revisão</strong></div>
											</td>
											<td width="<%= pctSituacao%>">
											<div align="center"><strong>Situação</strong></div>
											</td>
											
											<logic:present name="exibirDividaAtivaColuna" scope="session">
												<td width="<%= pctDividaAtivaGuia%>" >
													<div align="center" >
														<strong>Dívida Ativa</strong>
													</div>
												</td>				
											</logic:present>

										</tr>
									</table>

									</td>
								</tr>

								<logic:present name="colecaoContaImovel">

									<tr>
										<td>
										
										<% String cor = "#cbe5fe";%>

										<div style="width: 100%; height: 100; overflow: auto;">
										
										<table width="100%" align="center" bgcolor="#90c7fc">
										
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
													
													String corSituacaoContaPrescrita = "#000000";
													if(((Conta) conta).getDebitoCreditoSituacaoAtual().getId().toString().equals(DebitoCreditoSituacao.PRESCRITA.toString())){
														
														corSituacaoContaPrescrita = "#ff0000"; // VERMELHO 
													}
												%>
												
													<td align="center" width="7%" valign="middle"><INPUT
														TYPE="checkbox" NAME="conta"
														value="<%="" + conta.getId().intValue()%>-<%=data%>">
													</td>
													
													<td width="<%= pctReferencia%>" align="center">
														<a href="javascript:retificar('<%="" + conta.getId().intValue()%>')">
														<%=""+ Util.formatarMesAnoReferencia(conta.getReferencia())%> </a>
													</td>

												
													<td width="<%= pctDataVencimento%>">
													<div align="center"><logic:present name="conta"
														property="dataVencimentoConta">
														<span style="color: #000000;"><%=""
					+ Util.formatarData(conta.getDataVencimentoConta())%></span>
													</logic:present> <logic:notPresent name="conta"
														property="dataVencimentoConta">
												&nbsp;
											</logic:notPresent></div>
													</td>
													<td width="<%= pctValorConta%>"><div align="right"><span style="color: #000000;"><%="" + Util.formatarMoedaReal(new BigDecimal(conta.getValorTotalConta())).trim()%></span></div></td>
													<td width="<%= pctValorAgua%>">
													<div align="center"><logic:present name="conta"
														property="consumoAgua">
														<bean:write name="conta" property="consumoAgua" />
													</logic:present> <logic:notPresent name="conta"
														property="consumoAgua">
												&nbsp;
											</logic:notPresent></div>
													</td>
													<td width="<%= pctValorEsgoto%>">
													<div align="center"><logic:present name="conta"
														property="consumoEsgoto">
														<bean:write name="conta" property="consumoEsgoto" />
													</logic:present> <logic:notPresent name="conta"
														property="consumoEsgoto">
												&nbsp;
											</logic:notPresent></div>
													</td>
													<td width="<%= pctValidade%>">
													<div align="center"><logic:present name="conta"
														property="dataValidadeConta">
														<span style="color: #000000;"><%=""
							+ Util.formatarData(conta.getDataValidadeConta())%></span>
													</logic:present> <logic:notPresent name="conta"
														property="dataValidadeConta">
												&nbsp;
											</logic:notPresent></div>
													</td>
													<td width="<%= pctRevisao%>">
													<div align="center"><logic:present name="conta"
														property="dataRevisao">
														<span style="color: #000000;"><%="" + Util.formatarData(conta.getDataRevisao())%></span>
													</logic:present> <logic:notPresent name="conta"
														property="dataRevisao">
												&nbsp;
											</logic:notPresent></div>
													</td>
													<td width="<%= pctSituacao%>">
													<div align="center">
													
													<logic:present name="conta" property="debitoCreditoSituacaoAtual">
														<span style="color: <%=corSituacaoContaPrescrita %>;"><bean:write name="conta" property="debitoCreditoSituacaoAtual.descricaoDebitoCreditoSituacao" /></span>
													</logic:present> 
													
													<logic:notPresent name="conta" property="debitoCreditoSituacaoAtual">
														&nbsp;
													</logic:notPresent>
													
													</div>
													
													</td>
													
													<logic:present name="exibirDividaAtivaColuna" scope="session">
														<td width="<%= pctDividaAtivaGuia%>" align="center">
															<logic:equal name="conta" property="indicadorDividaAtiva" value="2">
																<logic:equal name="conta" property="indicadorExecucaoFiscal" value="2">
																	N
																</logic:equal>
															</logic:equal>
								
															<logic:equal name="conta" property="indicadorDividaAtiva" value="1">
																<logic:equal name="conta" property="conta.indicadorExecucaoFiscal" value="2">
																	A
																</logic:equal>
															</logic:equal>
														
															<logic:equal name="conta" property="indicadorExecucaoFiscal" value="1">
																E
															</logic:equal>
														</td>				
													</logic:present>														
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

					</table>

					</td>
				</tr>
				<tr>
					<td colspan="3" height="5">
						<gcom:controleAcessoBotao name="Button"
						value="Cancelar Conta" 
						onclick="cancelarConta(document.forms[0]);" url="cancelarContaAction.do" style="width: 103px"/> 

						<gcom:controleAcessoBotao 
						name="Button" value="Colocar Revisão"
						onclick="colocarContaEmRevisao(document.forms[0]);" url="colocarRevisaoContaAction.do" style="width: 103px"/> 

						<gcom:controleAcessoBotao 
						name="Button" value="Retirar Revisão"
						 onclick="retirarContaDeRevisao(document.forms[0]);" url="exibirManterContaAction.do" style="width: 100px"/>

						<gcom:controleAcessoBotao
						name="Button" value="Alterar Vencimento"
						onclick="alterarVencimento(document.forms[0]);" url="alterarVencimentoContaAction.do" style="width: 120px"/>
						
						 <gcom:controleAcessoBotao
						name="Button" value="Emitir 2ª Via de Conta"
						onclick="emitirSegundaViaConta(document.forms[0]);" url="gerarRelatorio2ViaContaAction.do" style="width: 140px"/>

						<logic:present name="EXIBIRCAUCIONAR">
							<logic:equal name="EXIBIRCAUCIONAR" value="1">
								<gcom:controleAcessoBotao
								name="Button" value="Caucionar Conta"
								onclick="caucionarConta(document.forms[0]);" url="exibirRetificarContaAction.do" style="width: 103px"/> 
							</logic:equal>
						</logic:present>
						
						<logic:notPresent name="EXIBIRCAUCIONAR">
							<gcom:controleAcessoBotao
							name="Button" value="Caucionar Conta"
							onclick="caucionarConta(document.forms[0]);" url="exibirRetificarContaAction.do" style="width: 103px"/> 
						</logic:notPresent>
						
						</td>
						
				</tr>
				<tr>
					<td valign="top">
						<input type="button" name="ButtonCancelar" class="bottonRightCol" value="Cancelar"
						onClick="javascript:document.forms[0].target='';window.location.href='/gsan/telaPrincipal.do'">
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
<!-- manter_conta.jsp -->
</html:html>
