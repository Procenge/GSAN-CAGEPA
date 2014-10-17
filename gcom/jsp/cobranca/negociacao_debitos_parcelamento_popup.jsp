<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="gcom.cobranca.bean.OpcoesParcelamentoHelper"%>
<%@ page import="gcom.util.ConstantesSistema"%>
<%@ page import="java.util.Collection"%>

<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<html:javascript staticJavascript="false"
	formName="EfetuarNegociacaoDebitosParcelamentoPopupActionForm" />

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">
	function enviar(acao){
		redirecionarSubmit('exibirEfetuarNegociacaoDebitosParcelamentoPopupAction.do?' + acao + '=S');
	}

	function desmarcarRadio(nomeCampo){
		var form = document.EfetuarNegociacaoDebitosParcelamentoPopupActionForm;

		for(i = 0; i < form.elements.length; i++){
			if(form.elements[i].type == "radio"
					&& form.elements[i].name == nomeCampo 
					&& form.elements[i].checked == true){
				form.elements[i].checked = false;
				form.elements[i].id = 0;
			}
		}
	}
</script>

</head>

<logic:present name="closePage" scope="session">
	<logic:equal name="closePage" value="S" scope="session">
		<body leftmargin="5" topmargin="5" onload="chamarReload('exibirEfetuarNegociacaoDebitosAction.do?selecionarNegociacao=S');window.close();">
	</logic:equal>
</logic:present>

<logic:notPresent name="closePage" scope="session">
	<body leftmargin="5" topmargin="5" onload="resizePageSemLink(700,690);">
</logic:notPresent>

<html:form action="/cancelarBoletoBancarioAction"
	name="EfetuarNegociacaoDebitosParcelamentoPopupActionForm"
	type="gcom.gui.cobranca.EfetuarNegociacaoDebitosParcelamentoPopupActionForm"
	method="post">

	<table width="663" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="663" valign="top" class="centercoltext">
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Opções de Parcelamento</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>

			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td colspan="2"></td>
				</tr>
			</table>

			<table width="100%" border="0">
				<html:hidden property="idResolucaoDiretoria"/>
				<html:hidden property="valorEntradaInformadoAntes"/>

				<tr>
					<td width="25%"><strong>Resolução Diretoria:</strong></td>
					<td>
						<html:text property="numeroResolucaoDiretoria" size="20"
							readonly="true" style="background-color:#EFEFEF; border:0" />
					</td>
				</tr>
				<tr>
					<td colspan="2" height="23"><br>
						<font color="#000000">
							<strong>Valor dos Descontos:</strong>
						</font>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table width="100%" bgcolor="#99CCFF">
							<tr bgcolor="#90c7fc">  
								<td align="center">
									<strong>Nos Acréscimos por Impontualidade</strong>
								</td>
								<td align="center">
									<strong>Por Antiguidade do Débito</strong>
								</td>
								<td align="center">
									<strong>Por Inatividade da Ligação de Água</strong>
								</td>
								<td align="center">
									<strong>Por Sanções Regulamentares</strong>
								</td>
								<td align="center">
									<strong>Por Tarifa Social</strong>
								</td>
								
							</tr>
							<tr bgcolor="#cbe5fe"> 
								<td align="right" bgcolor="#FFFFFF">
									<bean:write name="EfetuarNegociacaoDebitosParcelamentoPopupActionForm" 
											property="descontoAcrescimosImpontualidade"/>
								</td>
								<td align="right" bgcolor="#FFFFFF">
									<bean:write name="EfetuarNegociacaoDebitosParcelamentoPopupActionForm" 
											property="descontoAntiguidadeDebito"/>
								</td>
								<td align="right" bgcolor="#FFFFFF">
									<bean:write name="EfetuarNegociacaoDebitosParcelamentoPopupActionForm" 
											property="descontoInatividadeLigacaoAgua"/>
								</td>
								
								<td align="right" bgcolor="#FFFFFF">
									<bean:write name="EfetuarNegociacaoDebitosParcelamentoPopupActionForm" 
											property="descontoSancoesRDEspecial"/>
								</td>
								<td align="right" bgcolor="#FFFFFF">
									<bean:write name="EfetuarNegociacaoDebitosParcelamentoPopupActionForm" 
											property="descontoTarifaSocialRDEspecial"/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td width="25%"><strong>Valor Total dos Descontos:</strong></td>
					<td>
						<html:text property="valorTotalDescontos" size="20" readonly="true" 
							style="background-color:#EFEFEF; border:0; text-align:right"/>
					</td>
				</tr>
				<tr><td colspan="2"><hr></td></tr>
				<tr>
					<td colspan="2" height="23">
						<table width="100%">
							<tr>  
								<td><strong>Opção de Pagamento à Vista:</strong></td>
								
								<td align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:radio property="indicadorPagamentoCartaoCredito" value="0" onclick="desmarcarRadio('indicadorQuantidadeParcelas');"/>Pagamento à Vista </td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:radio property="indicadorPagamentoCartaoCredito" value="1" onclick="desmarcarRadio('indicadorQuantidadeParcelas');"/>Pagamento Cart&atilde;o</td>
							</tr>
						</table>
					</td>			
				</tr>
				<html:hidden property="indicadorPagamentoCartaoCredito" value=''/>
				<tr>
					<td colspan="2">
						<table width="100%" bgcolor="#99CCFF">
							<tr bgcolor="#90c7fc">  
								<td align="center">
									<strong>Valor Total do Débito Atualizado</strong>
								</td>
								<td align="center">
									<strong>Valor Total dos Impostos</strong>
								</td>
								
								<td align="center">
									<strong>Valor do Desconto para Pagamento à Vista</strong>
								</td>
								<td align="center">
									<strong>Valor do Pagamento à Vista</strong>
								</td>
							</tr>
							<tr bgcolor="#cbe5fe"> 
								<td align="right" bgcolor="#FFFFFF">
									<bean:write name="EfetuarNegociacaoDebitosParcelamentoPopupActionForm" 
											property="valorDebitoTotalAtualizado"/>
								</td>
								<td align="right" bgcolor="#FFFFFF">
									<bean:write name="EfetuarNegociacaoDebitosParcelamentoPopupActionForm" 
											property="valorTotalImpostosConta"/>
								</td>
								<td align="right" bgcolor="#FFFFFF">
									<bean:write name="EfetuarNegociacaoDebitosParcelamentoPopupActionForm" 
											property="valorDescontoPagamentoAVista"/>
								</td>
								<td align="right" bgcolor="#FFFFFF">
									<bean:write name="EfetuarNegociacaoDebitosParcelamentoPopupActionForm" 
											property="valorPagamentoAVista"/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr><td colspan="2"><hr></td></tr>
				<tr>
					<td colspan="2" height="23">
						<table width="100%">
							<tr>  
								<td><strong>Opção de Pagamento Parcelado:</strong></td>
							</tr>
						</table>
					</td>			
				</tr>
				<tr>
					<td colspan="2" height="23">
				        <table width="100%">
							<tr>  
								<td width="15%" align="left"><strong>Valor da Entrada:</strong></td>
								<td width="40%">
									<html:text property="valorEntradaInformado" maxlength="20" size="20" 
									onkeyup="formataValorMonetario(this,20)" style="text-align: right;"/>&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="button" name="calcularValorEntradaParaCalcular" value="Calcular" class="bottonRightCol" onClick="enviar('calcular');" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2" height="23">
						<font color="#000000">
							<strong>Para efetuar o parcelamento selecione a opção desejada:</strong>
						</font>
					</td>
				</tr>
				<%int cont = 0;%>
				<%int cor = 0;%>															
				<tr>
					<td colspan="2">
						<table width="100%" bgcolor="#99CCFF">
							<logic:empty name="colecaoOpcoesParcelamento" scope="session">
							<tr bgcolor="#90c7fc">  
								<td align="center" width="5%">
									&nbsp;
								</td>
								<td align="center">
									<strong>Parcelas</strong>
								</td>
								<td align="center">
									<strong>Valor da Entrada</strong>
								</td>
								<td align="center">
									<strong>Valor da Parcela</strong>
								</td>
								<td align="center">
									<strong>Taxa de Juros (%)</strong>
								</td>
							</tr>
							</logic:empty>
							<logic:notEmpty name="colecaoOpcoesParcelamento" scope="session">
				 			<%if (((Collection) session.getAttribute("colecaoOpcoesParcelamento")).size() <= ConstantesSistema.NUMERO_MAXIMO_REGISTROS_OPCAO_PARCELAMENTO) {%>
							<tr bgcolor="#90c7fc">  
								<td align="center" width="5%">
									&nbsp;
								</td>
								<td align="center">
									<strong>Parcelas</strong>
								</td>
								<td align="center">
									<strong>Valor da Entrada</strong>
								</td>
								<td align="center">
									<strong>Valor da Parcela</strong>
								</td>
								<td align="center">
									<strong>Taxa de Juros (%)</strong>
								</td>
							</tr>
							<logic:iterate name="colecaoOpcoesParcelamento" type="OpcoesParcelamentoHelper" id="opcoesParcelamento">
							<%cont = cont + 1;
							if (cont % 2 == 0) {%>
							<tr bgcolor="#cbe5fe">
							<%} else {%>
							<tr bgcolor="#FFFFFF">
							<%}%>
								<td align="center" width="5%">
									<% if ((((OpcoesParcelamentoHelper)opcoesParcelamento).getIndicadorQuantidadeParcelas()) != null 
											&& ((OpcoesParcelamentoHelper)opcoesParcelamento).getIndicadorQuantidadeParcelas()
												.equals(((OpcoesParcelamentoHelper)opcoesParcelamento).getQuantidadePrestacao().toString())){ %>
										<input type="radio" name="indicadorQuantidadeParcelas" value="${opcoesParcelamento.quantidadePrestacao}" checked="true" onclick="desmarcarRadio('indicadorPagamentoCartaoCredito');"/>
									<% } else { %>
										<input type="radio" name="indicadorQuantidadeParcelas" value="${opcoesParcelamento.quantidadePrestacao}" onclick="desmarcarRadio('indicadorPagamentoCartaoCredito');"/>
									<% } %>
								</td>
								<td align="center">
									<bean:write name="opcoesParcelamento" property="quantidadePrestacao"/>
								</td>
								<td align="right">
									<bean:write name="opcoesParcelamento" property="valorEntradaMinima" formatKey="money.format"/>
								</td>
								<td align="right">
									<bean:write name="opcoesParcelamento" property="valorPrestacao" formatKey="money.format"/>	
								</td>
								<td align="right">
									<bean:write name="opcoesParcelamento" property="taxaJuros" formatKey="money.format"/>	
								</td>
							</tr>
							</logic:iterate>
							<% }else{ %>
							<tr bgcolor="#90c7fc">  
								<td align="center" width="5%">
									&nbsp;
								</td>
								<td align="center" width="12%">
									<strong>Parcelas</strong>
								</td>
								<td align="center" width="28%">
									<strong>Valor da Entrada</strong>
								</td>
								<td align="center" width="29%">
									<strong>Valor da Parcela</strong>
								</td>
								<td align="center">
									<strong>Taxa de Juros (%)</strong>
								</td>
							</tr>
							<tr>
								<td height="100" colspan="6">
									<div style="width: 100%; height: 100%; overflow: auto;">
										<table width="100%">
											<logic:iterate name="colecaoOpcoesParcelamento" type="OpcoesParcelamentoHelper" id="opcoesParcelamento">
											<%cont = cont + 1;
											if (cont % 2 == 0) {%>
											<tr bgcolor="#cbe5fe">
											<%} else {%>
											<tr bgcolor="#FFFFFF">
											<%}%>
											<logic:equal name="opcoesParcelamento" property="indicadorValorPrestacaoMaiorValorLimite" value="2">
												<td align="center" width="5%">
													<% if ((((OpcoesParcelamentoHelper)opcoesParcelamento).getIndicadorQuantidadeParcelas()) != null 
														&& ((OpcoesParcelamentoHelper)opcoesParcelamento).getIndicadorQuantidadeParcelas()
															.equals(((OpcoesParcelamentoHelper)opcoesParcelamento).getQuantidadePrestacao())){ %>
														<input type="radio" name="indicadorQuantidadeParcelas" 
															value="${opcoesParcelamento.quantidadePrestacao}" checked="true" onclick="desmarcarRadio('indicadorPagamentoCartaoCredito');"/>
													<% } else { %>
														<input type="radio" name="indicadorQuantidadeParcelas" 
															value="${opcoesParcelamento.quantidadePrestacao}" onclick="desmarcarRadio('indicadorPagamentoCartaoCredito');"/>
													<% } %>
												</td>
												<td align="center" width="12%">
													<bean:write name="opcoesParcelamento" property="quantidadePrestacao"/>
												</td>
												<td align="right" width="29%">
													<bean:write name="opcoesParcelamento" property="valorEntradaMinima" formatKey="money.format"/>
												</td>
												<td align="right" width="30%">
													<bean:write name="opcoesParcelamento" property="valorPrestacao" formatKey="money.format"/>	
												</td>
												<td align="right">
													<bean:write name="opcoesParcelamento" property="taxaJuros" formatKey="money.format"/>	
												</td>
											</logic:equal>

											<logic:equal name="opcoesParcelamento" property="indicadorValorPrestacaoMaiorValorLimite" value="1">
												<%cor = 1;%>		

												<td align="center" width="5%">
													<% if ((((OpcoesParcelamentoHelper)opcoesParcelamento).getIndicadorQuantidadeParcelas()) != null 
														&& ((OpcoesParcelamentoHelper)opcoesParcelamento).getIndicadorQuantidadeParcelas()
															.equals(((OpcoesParcelamentoHelper)opcoesParcelamento).getQuantidadePrestacao())){ %>
														<input type="radio" name="indicadorQuantidadeParcelas" 
															value="${opcoesParcelamento.quantidadePrestacao}" checked="true" onclick="desmarcarRadio('indicadorPagamentoCartaoCredito');"/>
													<% } else { %>
														<input type="radio" name="indicadorQuantidadeParcelas" 
															value="${opcoesParcelamento.quantidadePrestacao}" onclick="desmarcarRadio('indicadorPagamentoCartaoCredito');"/>
													<% } %>
												</td>
												<td align="center" width="12%">
													<font color="#ff0000"> 
														<bean:write name="opcoesParcelamento" property="quantidadePrestacao"/>
													</font>
												</td>
												<td align="right" width="29%">
													<font color="#ff0000"> 
														<bean:write name="opcoesParcelamento" property="valorEntradaMinima" formatKey="money.format"/>
													</font>
												</td>
												<td align="right" width="30%">
													<font color="#ff0000"> 
														<bean:write name="opcoesParcelamento" property="valorPrestacao" formatKey="money.format"/>	
													</font>
												</td>
												<td align="right">
													<font color="#ff0000"> 
														<bean:write name="opcoesParcelamento" property="taxaJuros" formatKey="money.format"/>	
													</font>
												</td>
											</logic:equal>
											</tr>
											</logic:iterate>
										</table>
			           				</div>
		           				</td>
	           				</tr>
							<% } %>
							</logic:notEmpty>
						</table>
					</td>
				</tr>
				<html:hidden property="indicadorQuantidadeParcelas" value=''/>

				<% if (cor == 1) {%>
					<tr>
						<td colspan="2">
							<font color="#FF0000">Valor da parcela maior que Tarifa Social / Tarifa Mínima</font>
						</td>
					</tr>
				<%}%>

				<tr>
					<td><br></td>
				</tr>

				<tr>
					<td>
						<input name="Submit22" class="bottonRightCol" value="Voltar"
							type="button" onclick="window.close();">
					</td>
					<td align="right">
						<input name="Submit23" class="bottonRightCol" value="Selecionar"
							type="button" onclick="enviar('selecionarNegociacao');">
					</td>
				</tr>
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>

</html:form>

</body>
</html:html>
