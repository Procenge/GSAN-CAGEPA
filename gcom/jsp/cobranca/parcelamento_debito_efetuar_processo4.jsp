<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="gcom.cobranca.parcelamento.ParcelamentoConfiguracaoPrestacao"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="EfetuarParcelamentoDebitosActionForm" dynamicJavascript="false" />

<script language="JavaScript">
<!--
var bCancel = false; 
function validateEfetuarParcelamentoDebitosActionForm(form) {                                                                   
	if (bCancel) 
		return true; 
	else 
		return true; //validateCaracterEspecial(form) && validateRequired(form) && validateLong(form) && validateDate(form); 
} 

// Verifica se o número do campo do formulário é negativo
function numeroNegativo(valor,campo,descricao){
	if(valor < 0){ 
		alert(+descricao+' deve somente conter números positivos.');
		campo.focus();
		return true;
	}
}

-->
</script>
</head>

<body leftmargin="5" topmargin="5">
<html:form action="/efetuarParcelamentoDebitosWizardAction"
	name="EfetuarParcelamentoDebitosActionForm"
	type="gcom.gui.cobranca.EfetuarParcelamentoDebitosActionForm" 
	method="post">

<jsp:include page="/jsp/util/wizard/navegacao_abas_wizard.jsp?numeroPagina=4"/>	

<%@ include file="/jsp/util/cabecalho.jsp"%>
<% boolean tem = false; %>	
	<logic:present name="popupEfetuarParcelamento" scope="request" >
	<logic:equal name="popupEfetuarParcelamento" scope="request" value="sim">
		<% tem = true; %>
		<input type="hidden" name=popupEfetuarParcelamento value="sim"/> 
	</logic:equal>
	</logic:present>
	
<% if (!tem) { %>
		<%@ include file="/jsp/util/menu.jsp" %>
<% } %>

<table width="770" border="0" cellspacing="4" cellpadding="0">

<% if (!tem) { %>
			<td width="149" valign="top" class="leftcoltext">
			<input type="hidden" name="numeroPagina" value="1"/>
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
<% } %>
		<td width="625" valign="top" class="centercoltext">
	        <table height="100%">
		        <tr><td></td></tr>
	      	</table>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	        	<tr>
		          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
		          <td class="parabg">Efetuar Parcelamento de Débitos</td>
		          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
	    	    </tr>
		    </table>
			<!-- Início do Corpo - Roberta Costa -->
			<p>&nbsp;</p>
			
            <table width="100%" border="0">
				<tr> 
					<td colspan="2">Para efetuar o parcelamento de d&eacute;bitos informe o im&oacute;vel:</td>
				</tr>
				<tr> 
					<td colspan="2">
			            <table border="0">
							<tr> 
								<td>
									<strong>Matr&iacute;cula do Im&oacute;vel:</strong>
								</td>
								<td>
									<html:text property="matriculaImovel" size="10"
										readonly="true" style="background-color:#EFEFEF; border:0" />
								</td>
							</tr>
			            </table>
					</td>
				</tr>
				<tr> 
					<td colspan="2">
			            <table border="0" bgcolor="#99CCFF" width="100%">
							<tr bgcolor="#99CCFF">
								<td colspan="2" align="center"><strong>Dados do Imóvel:</strong></td>
							</tr>
							<tr>
								<td>
									<table border="0" bgcolor="#cbe5fe" width="100%">
										<tr> 
											<td width="32%"><strong>Inscri&ccedil;&atilde;o do Im&oacute;vel:</strong></td>
											<td>
												<html:text property="inscricaoImovel" size="25"
													readonly="true" style="background-color:#EFEFEF; border:0" />
											</td>
										</tr>
										<tr> 
											<td><strong>Cliente Usu&aacute;rio:</strong></td>
											<td>
												<html:text property="nomeCliente" size="45"
													readonly="true" style="background-color:#EFEFEF; border:0" />
											</td>
										</tr>
										<tr> 
											<td><strong>CPF ou CNPJ:</strong></td>
											<td>
												<html:text property="cpfCnpj" size="45"
													readonly="true" style="background-color:#EFEFEF; border:0" />
													<html:hidden property="cpfClienteParcelamentoDigitado" />
											</td>
										</tr>
										<tr> 
											<td><strong>Situa&ccedil;&atilde;o da Liga&ccedil;&atilde;o de &Aacute;gua:</strong></td>
											<td>
												<html:text property="situacaoAgua" size="45"
													readonly="true" style="background-color:#EFEFEF; border:0" />
											</td>
										</tr>
										<tr> 
											<td><strong>Situa&ccedil;&atilde;o da Liga&ccedil;&atilde;o de Esgoto:</strong></td>
											<td>
												<html:text property="situacaoEsgoto" size="45"
													readonly="true" style="background-color:#EFEFEF; border:0" />
											</td>
										</tr>
										<tr> 
											<td><strong>Perfil do Imóvel:</strong></td>
											<td>
												<html:text property="descricaoPerfilImovel" size="45"
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
					<td colspan="2"><br>
						<table width="100%" align="center" bgcolor="#99CCFF" border="0">
							<tr>
								<td align="center">
									<strong>Endere&ccedil;o do Im&oacute;vel</strong>
								</td>
							</tr>
							<tr bgcolor="#cbe5fe">
								<td align="center" bgcolor="#FFFFFF" height="20">
									<span id="endereco">
										<logic:present name="EfetuarParcelamentoDebitosActionForm" property="endereco">
											<bean:write name="EfetuarParcelamentoDebitosActionForm" 
												property="endereco"/>
										</logic:present>
									</span>	
									<logic:notPresent name="EfetuarParcelamentoDebitosActionForm" property="endereco">
									&nbsp;
									</logic:notPresent>													
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2" height="23"><br>
						<font color="#000000">
							<strong>Valor dos D&eacute;bitos do Im&oacute;vel:</strong>
						</font>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table width="100%" bgcolor="#99CCFF">
							<tr bgcolor="#90c7fc">  
								<td align="center" width="30%">
									<strong> Contas</strong>
								</td>
								<td align="center" width="30%">
									<strong>Guias de Pagamento</strong>
								</td>
								<td align="center" width="40%">
									<strong>Acr&eacute;scimos Impontualidade</strong>
								</td>
							</tr>
							<tr bgcolor="#cbe5fe"> 
								<td align="right" height="20" bgcolor="#FFFFFF">
									<bean:write name="EfetuarParcelamentoDebitosActionForm" 
										property="valorTotalContaValores"/>
								</td>
								<td align="right" bgcolor="#FFFFFF">
									<bean:write name="EfetuarParcelamentoDebitosActionForm" 
										property="valorGuiasPagamento"/>
								</td>
								<td align="right" bgcolor="#FFFFFF">
									<logic:notEqual
										name="EfetuarParcelamentoDebitosActionForm" property="valorAcrescimosImpontualidade"
										value="0,00">
										<a href="javascript:abrirPopup('exibirValorAtualizacaoConsultarPopupAction.do?multa=<bean:write name="EfetuarParcelamentoDebitosActionForm" property="valorMulta" />&juros=<bean:write name="EfetuarParcelamentoDebitosActionForm" property="valorJurosMora" />&atualizacao=<bean:write name="EfetuarParcelamentoDebitosActionForm" property="valorAtualizacaoMonetaria" />', 300, 650);">
										<bean:write name="EfetuarParcelamentoDebitosActionForm"
											property="valorAcrescimosImpontualidade" formatKey="money.format" />
										</a>
									</logic:notEqual>
									<logic:equal name="EfetuarParcelamentoDebitosActionForm"
										property="valorAcrescimosImpontualidade" value="0,00">
										<bean:write name="EfetuarParcelamentoDebitosActionForm"
											property="valorAcrescimosImpontualidade" formatKey="money.format" />
									</logic:equal>
								</td>
							</tr>
						</table>
						<br>	
						<table width="100%" bgcolor="#99CCFF">
							<tr bgcolor="#90c7fc">
								<td align="center" colspan="2">
									<strong>D&eacute;bitos a Cobrar</strong>
								</td>
								<td align="center" rowspan="2">
									<strong>Cr&eacute;ditos a Realizar</strong>
								</td>
								
								<td align="center" rowspan="2">
									<strong>Estorno Descontos</strong>
								</td>
								
								<td align="center" rowspan="2">
									<strong>D&eacute;bito Total Atualizado</strong>
								</td>								
								
							</tr>
							<tr bgcolor="#90c7fc"> 
								<td align="center">
									<strong>Servi&ccedil;o</strong>
								</td>
								<td align="center">
									<strong>Parcelamento</strong>
								</td>					
								
							</tr>
							<tr bgcolor="cbe5fe">
								<td align="right" bgcolor="#FFFFFF">
									<logic:notEqual name="EfetuarParcelamentoDebitosActionForm" 
										property="valorDebitoACobrarServico" value="0,00">
										<a href="javascript:abrirPopup('exibirConsultarDebitoACobrarAction.do?imovelID=
											<bean:define name="EfetuarParcelamentoDebitosActionForm" property="matriculaImovel" id="imovel" />
												<bean:write name="EfetuarParcelamentoDebitosActionForm" property="matriculaImovel" />&parcelamentoID=', 600, 800);">
											<bean:write name="EfetuarParcelamentoDebitosActionForm" property="valorDebitoACobrarServico"  formatKey="money.format"/>
										</a>
									</logic:notEqual>
									<logic:equal name="EfetuarParcelamentoDebitosActionForm" 
										property="valorDebitoACobrarServico" value="0,00">
										<bean:write	name="EfetuarParcelamentoDebitosActionForm" property="valorDebitoACobrarServico" formatKey="money.format" />
									</logic:equal>
								</td>
								<td align="right" bgcolor="#FFFFFF">
									<logic:notEqual name="EfetuarParcelamentoDebitosActionForm" 
										property="valorDebitoACobrarParcelamento" value="0,00">
										<a href="javascript:abrirPopup('exibirConsultarDebitoACobrarAction.do?imovelID=
											<bean:define name="EfetuarParcelamentoDebitosActionForm" property="matriculaImovel" id="imovel" />
												<bean:write name="EfetuarParcelamentoDebitosActionForm" property="matriculaImovel" />&parcelamentoID=', 600, 800);">
											<bean:write name="EfetuarParcelamentoDebitosActionForm" property="valorDebitoACobrarParcelamento"  formatKey="money.format"/>
										</a>
									</logic:notEqual>
									<logic:equal name="EfetuarParcelamentoDebitosActionForm" 
										property="valorDebitoACobrarParcelamento" value="0,00">
										<bean:write	name="EfetuarParcelamentoDebitosActionForm" property="valorDebitoACobrarParcelamento" formatKey="money.format" />
									</logic:equal>
								</td>
								<td align="right" bgcolor="#FFFFFF">
									<logic:notEqual name="EfetuarParcelamentoDebitosActionForm" 
										property="valorCreditoARealizar" value="0,00">
										<a href="javascript:abrirPopup('exibirConsultarCreditoARealizarAction.do?imovelID=<bean:define name="EfetuarParcelamentoDebitosActionForm" property="matriculaImovel" id="imovel" />
											<bean:write name="EfetuarParcelamentoDebitosActionForm" property="matriculaImovel" />&parcelamentoID=', 600, 800);">
												<bean:write name="EfetuarParcelamentoDebitosActionForm" property="valorCreditoARealizar" formatKey="money.format"/>
										</a>
									</logic:notEqual>
									<logic:equal name="EfetuarParcelamentoDebitosActionForm" 
										property="valorCreditoARealizar" value="0,00">
										<bean:write	name="EfetuarParcelamentoDebitosActionForm" property="valorCreditoARealizar" formatKey="money.format" />
									</logic:equal>
								</td>
								
								
								
								
								<td align="right" bgcolor="#FFFFFF">
									<bean:write name="EfetuarParcelamentoDebitosActionForm" 
										property="valorEstornoDescontos"/>
								</td>
								
								
								<td align="right" bgcolor="#FFFFFF">
									<bean:write name="EfetuarParcelamentoDebitosActionForm" 
										property="valorDebitoTotalAtualizado"/>
								</td>
								
								
								
								
								
							</tr>
						</table>
					</td>
				</tr>
				<tr><td><br></td></tr>
				<tr> 
					<td width="35%"><strong>Intervalo do Parcelamento:</strong></td>
					<td>
						<html:text property="inicioIntervaloParcelamento" size="7"
							readonly="true" style="background-color:#EFEFEF; border:0; text-align:center" />
						a 
						<html:text property="fimIntervaloParcelamento" size="7"
							readonly="true" style="background-color:#EFEFEF; border:0; text-align:center" />
					</td>
				</tr>
				<tr> 
					<td><strong>Valor do Desconto:</strong></td>
					<td>
						<html:text property="valorTotalDescontos" size="19"
							readonly="true" style="background-color:#EFEFEF; border:0; text-align:right" />
					</td>
				</tr>
				<tr> 
					<td><strong>Valor a ser Negociado:</strong></td>
					<td>
						<html:text property="valorNegociado" size="19"
							readonly="true" style="background-color:#EFEFEF; border:0; text-align:right"/>
					</td>
				</tr>
				<tr>
					<td colspan="2" height="23">
						<font color="#000000">
							<strong>Condi&ccedil;&otilde;es da Negocia&ccedil;&atilde;o:</strong>
						</font>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table width="100%" bgcolor="#99CCFF">
							<logic:empty name="EfetuarParcelamentoDebitosActionForm" property="parcelaEscolhida">
							<tr bgcolor="#90c7fc">  
								<td align="center">
									<strong>Valor da Entrada</strong>
								</td>
								
								<logic:empty name="colecaoParcelamentoConfiguracaoPrestacao" scope="session">
									<td align="center">
										<strong>Número de Parcelas</strong>
									</td>
									<td align="center">
										<strong>Valor da Parcela</strong>
									</td>
								</logic:empty>
								<logic:notEmpty name="colecaoParcelamentoConfiguracaoPrestacao" scope="session">
									<td colspan="2" align="center" bgcolor="#90c7fc"><strong>Opções Parc. Configurável</strong>
									
										<div style="width: 100%; height: 100%; overflow: auto;">
											<table width="100%" bgcolor="#99CCFF">
												<tr>
													<td align="center">
														<strong>Número de Parcela</strong>
													</td>
													<td align="right">
														<strong>Valor da Parcela</strong>
													</td>
												</tr>
											</table>
										</div>
										
									<td>
								</logic:notEmpty>
								<td align="center">
									<strong>Valor a ser Parcelado</strong>
								</td>
								<td align="center">
									<strong>Taxa de Juros (%)</strong>
								</td>
							</tr>
							</logic:empty>
			
							<logic:notEmpty name="EfetuarParcelamentoDebitosActionForm" property="parcelaEscolhida">
							<tr bgcolor="#90c7fc">  
																						
								<td align="center">
									<strong>Valor da Entrada</strong>
								</td>
								
								<td align="center">
									<strong>Vencimento Entrada</strong>
								</td>
								
								<td align="center">
									<strong>Valor Débito Atualizado</strong>
								</td>
								
								<td align="center">
									<strong>Valor Desconto</strong>
								</td>
								
								<td align="center">
									<strong>Valor Parcelado</strong>
								</td>
								
								<td align="center">
									<strong>Número de Parcelas</strong>
								</td>
								<td align="center">
									<strong>Valor da Parcela</strong>
								</td>
								<td align="center">
									<strong>Valor Final Financiamento</strong>
								</td>
								<td align="center">
									<strong>Tx Juros + Cor. Monetária (%)</strong>
								</td>
							</tr>
							
							<tr bgcolor="#cbe5fe"> 							
							 
								<td align="right" bgcolor="#FFFFFF">
									<bean:write name="EfetuarParcelamentoDebitosActionForm" 
										property="valorEntradaInformado"/>
								</td>
								
								<td align="right" bgcolor="#FFFFFF">
									<bean:write name="EfetuarParcelamentoDebitosActionForm" 
										property="dataVencimentoEntradaParcelamento"/>
								</td>
								
								<td align="right" bgcolor="#FFFFFF">
								  			   <bean:write name="EfetuarParcelamentoDebitosActionForm" 
										property="valorDebitoTotalAtualizado"/>
								</td>
								
								<td align="right" bgcolor="#FFFFFF">
								
							
							   <bean:write name="EfetuarParcelamentoDebitosActionForm" 
										property="valorTotalDescontos"/>				
								   
								</td>
								
								<td align="right" bgcolor="#FFFFFF">
								    <bean:write name="EfetuarParcelamentoDebitosActionForm" 
										property="valorASerParcelado"/>
										
										
								</td>
							
								<td align="right" bgcolor="#FFFFFF">
									<bean:write name="EfetuarParcelamentoDebitosActionForm" 
										property="parcelaEscolhida"/>
								</td>
								<td align="right" bgcolor="#FFFFFF">
									<bean:write name="EfetuarParcelamentoDebitosActionForm" 
										property="valorParcelaEscolhida"/>
								</td>
								<td align="right" bgcolor="#FFFFFF">
									 <bean:write name="EfetuarParcelamentoDebitosActionForm" 
										property="valorFinalFinanciamento"/>
								</td>
								<td align="right" bgcolor="#FFFFFF">
									<bean:write name="EfetuarParcelamentoDebitosActionForm" 
										property="taxaJurosEscolhida"/>
								</td>
							</tr>
							</logic:notEmpty>
							
							<logic:notEmpty name="colecaoParcelamentoConfiguracaoPrestacao" scope="session">
								<%int contAux = 0;%>
								<tr bgcolor="#cbe5fe">
									<td align="right" bgcolor="#FFFFFF">
										<bean:write name="EfetuarParcelamentoDebitosActionForm" 
											property="valorEntradaInformado"/>
									</td>
									
									<td colspan="3">
										<div style="width: 100%; height: 100%; overflow: auto;">
											<table width="100%">
												<logic:iterate name="colecaoParcelamentoConfiguracaoPrestacao" 
													id="parcQtdPrestacao" type="ParcelamentoConfiguracaoPrestacao">
													<%contAux = contAux + 1;
													if (contAux % 2 == 0) {%>
													<tr bgcolor="#cbe5fe">
													<%} else {%>
													<tr bgcolor="#FFFFFF">
													<%}%>	
				
														<td align="center">
															<bean:write name="parcQtdPrestacao" property="numeroPrestacaoFormatado" />
														</td>
					
														<td align="right">
															<div><bean:write name="parcQtdPrestacao" property="valorPrestacaoFormatado" /> &nbsp;</div>
														</td>
							
													</tr>
												</logic:iterate>
											</table>
										</div>
									</td>
									
									<td align="right" bgcolor="#FFFFFF">
								    	<bean:write name="EfetuarParcelamentoDebitosActionForm" 
											property="valorASerParcelado"/>
									</td>
									
									<td align="right" bgcolor="#FFFFFF">
										<bean:write name="EfetuarParcelamentoDebitosActionForm" 
											property="taxaJurosEscolhida"/>
									</td>
								
								</tr>
							</logic:notEmpty>
						</table>
					</td>
				</tr>
				<tr><td><br></td></tr>
				
				<tr>
			      <td><strong>Confirmação de Parcelamento:</strong></td>
				  <td>
						<html:radio property="indicadorConfirmacaoParcelamento" value="1" tabindex="5" disabled="true"/><strong>Sim
						<html:radio property="indicadorConfirmacaoParcelamento" value="2" tabindex="6" disabled="true"/>Não</strong>
				  </td>
			   </tr>
			   <tr>
					<td colspan="2">
						<jsp:include page="/jsp/util/wizard/navegacao_botoes_wizard_valida_avancar_valida_voltar.jsp?numeroPagina=4"/>
					</td>
				</tr>
			</table>
			
			<p>&nbsp;</p>
			<!-- Fim do Corpo - Roberta Costa -->
		</td>
	</tr>
</table>

<%@ include file="/jsp/util/rodape.jsp"%>

</html:form>
</body>
<!-- parcelamento_debito_efetuar_processo4.jsp -->
</html:html>