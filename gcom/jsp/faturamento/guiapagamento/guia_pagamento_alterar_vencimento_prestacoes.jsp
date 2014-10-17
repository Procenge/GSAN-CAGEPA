<%@page import="gcom.util.Util"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ page import="gcom.cobranca.parcelamento.ParcelamentoItem"
	isELIgnored="false"%>
<%@ page import="gcom.gui.faturamento.bean.GuiaPagamentoPrestacaoHelper"%>


<html>
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
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="AtualizarGuiaPagamentoActionForm" dynamicJavascript="false" />
	
<SCRIPT LANGUAGE="JavaScript">
	function validarForm(formulario) {
		
		submeterFormPadrao(formulario);
	}

	function validarAlterarVencimento(){

		for (var i=0;i<document.forms[0].dataVencimento.length;i++){
			if (document.forms[0].dataVencimento[i].value == ""){
				alert("Data Inválida.");
				return;
			}
		}
		
		var form = document.AtualizarGuiaPagamentoActionForm;
			form.action = "/gsan/alterarVencimentoPrestacoesGuiaPagamentoAction.do"
			form.submit();
	 }
	
	function validarData(pData, indice, event){

		if(pData.value != ""){
			
			verificaData(pData,event);

			var dataAnterior = null;
			var dataPosterior = null;
			var dataAlterada = pData.value.substr(6,4) + "-" + pData.value.substr(3,2)+ "-" + pData.value.substr(0,2);
			
			var dataAnteriorFormatada = null;
			var dataPosteriorFormatada = null;
			
			if(document.forms[0].dataVencimentoHidden[indice-1] != null && document.forms[0].dataVencimentoHidden[indice-1].value != ""){
				dataAnterior = document.forms[0].dataVencimentoHidden[indice-1].value;
				dataAnteriorFormatada = dataAnterior.substr(8,2) + "/" + dataAnterior.substr(5,2) + "/" + dataAnterior.substr(0,4);
			}
			
			if(document.forms[0].dataVencimentoHidden[indice+1] != null && document.forms[0].dataVencimentoHidden[indice+1].value != ""){
				dataPosterior = document.forms[0].dataVencimentoHidden[indice+1].value;
				dataPosteriorFormatada = dataPosterior.substr(8,2) + "/" + dataPosterior.substr(5,2) + "/" + dataPosterior.substr(0,4);
			}

			// primeira data
			if(indice == 0){

				if (compararDatas(dataAlterada, dataPosterior) != -1){
					alert("Esta Data de Vencimento não pode ser posterior ou igual a "+ dataPosteriorFormatada +". Informe outra data.");
					document.forms[0].dataVencimento[indice].value = "";
				}
				
				return;
			}
			// última data
			if (indice == document.forms[0].dataVencimentoHidden.length-1){

				if (compararDatas(dataAlterada, dataAnterior) != 1){
					alert("Esta Data de Vencimento não pode ser anterior ou igual a "+ dataAnteriorFormatada +". Informe outra data.");	
					document.forms[0].dataVencimento[indice].value = "";
				}
				
				return;
			}
			
			if (compararDatas(dataAlterada, dataPosterior) != -1){
				alert("Esta Data de Vencimento não pode ser posterior ou igual a "+ dataPosteriorFormatada +". Informe outra data.");

				document.forms[0].dataVencimento[indice].value = "";
				return;
			} else if (compararDatas(dataAlterada, dataAnterior) != 1){
				alert("Esta Data de Vencimento não pode ser anterior ou igual a "+ dataAnteriorFormatada +". Informe outra data.");

				document.forms[0].dataVencimento[indice].value = "";
				return;
			}		
						
		}		
	}
	
function abrirCalendarioLocal(indice){
	
	abrirCalendario('InserirGuiaPagamentoActionForm', 'dataVencimento');
	document.forms[0].dataVencimento[indice].focus();
}


	
</SCRIPT>
	
</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:resizePageSemLink(800,680);">
	
<html:form action="/alterarVencimentoPrestacoesGuiaPagamentoAction.do" method="post">

<table width="670" border="0" cellspacing="5" cellpadding="0"  align="center">
	<tr>
		<td width="670" valign="top" class="centercoltext">
		<table height="100%">
			<tr>
				<td></td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td width="11"><img	src="<bean:message key="caminho.imagens"/>parahead_left.gif" border="0" /></td>
				<td class="parabg">Alterar Vencimento das Prestações</td>
				<td width="11"><img	src="<bean:message key="caminho.imagens"/>parahead_right.gif" border="0" /></td>
			</tr>
		</table>
		<p>&nbsp;</p>
		
		<table width="100%" border="0">
		<tr>
				<td colspan="4">

					<table width="100%" align="center" bgcolor="#99CCFF">
						<tr>
							<td><strong>Dados Gerais da Guia de Pagamento:</strong></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td width="100%" align="center">

							<table width="100%" border="0">

			<tr>
				<td width="194"><strong>Número da Guia:</strong></td>
				<td width="395" align="right">
				<div align="left"><input name="idRegistroAtualizacao" type="text"
					readonly style="background-color:#EFEFEF; border:0" size="8"
					maxlength="3" value="<bean:write name="guiaPagamento" property="id"/>">
				</div>
				</td>
			</tr>
			
			
			<logic:present name="exibirNuContratoParcOrgaoPublico" scope="session">
				<tr>
					<td width="194"><strong>No. Contrato Parcel. Órgão Público:</strong></td>
					<td width="395" align="right">
					<div align="left"><input name="nuContratoParcelOrgaoPublico" type="text"
						readonly style="background-color:#EFEFEF; border:0" size="10"
						maxlength="12" value="<bean:write name="guiaPagamento" property="numeroContratoParcelOrgaoPublico"/>">
					</div>
					</td>
				</tr>
			</logic:present>
			
			<tr>
				<td class="style1"><strong>Matr&iacute;cula do Im&oacute;vel:</strong></td>

				<td class="style1">
					<span class="style1"> 
						<logic:notEmpty name="guiaPagamento" property="imovel">
							<input  name="matriculaImovel" type="text" readonly="true"  style="background-color:#EFEFEF; border:0" size="9" maxlength="9"
							value='<bean:write name="guiaPagamento" property="imovel.id"/>'>
						</logic:notEmpty>	 
					</span>
					<span class="style1"> 
						<logic:notEmpty name="guiaPagamento" property="imovel">
							<input name="inscricaoImovel" type="text" readonly="true" style="background-color:#EFEFEF; border:0" size="20" maxlength="20"
							value='<bean:write name="guiaPagamento" property="imovel.inscricaoFormatada"/>'>
						</logic:notEmpty>	 
					</span>
				</td>
			</tr>
			
			<tr>
				<td class="style1"><strong>Endereço:</strong></td>
				<td class="style1"><span class="style1"> 
					<logic:notEmpty name="guiaPagamento" property="imovel">
						<textarea rows="3" cols="40" readonly = "readonly" style="background-color:#EFEFEF; border:0"> <bean:write name="guiaPagamento" property="imovel.enderecoFormatado" />
						</textarea>						
					</logic:notEmpty>	 
					<logic:empty name="guiaPagamento" property="imovel">
						<input name="imovelEndereco" type="text" readonly="readonly" style="background-color:#EFEFEF; border:0" value="" size="50" maxlength="45">
					</logic:empty>
					</span>
				</td>
			</tr>		
			

			<tr>
				<td class="style1"><strong>C&oacute;digo do Cliente:</strong></td>
				<td class="style1"><span class="style1"> 
					<logic:notEmpty name="guiaPagamento" property="cliente">
						<input name="codigoCliente" type="text" readonly="true" style="background-color:#EFEFEF; border:0" size="9" maxlength="9" value='<bean:write name="guiaPagamento" property="cliente.id" />'>
					</logic:notEmpty>
					<logic:empty name="guiaPagamento" property="cliente">
						<input name="codigoCliente" type="text" readonly="true" style="background-color:#EFEFEF; border:0" size="9" maxlength="9" value="">
					</logic:empty>
					 </span>
				</td>
			</tr>
			<tr>
				<td class="style1"><strong>CPF/CNPJ:</strong></td>
				<td>	
					<logic:notEmpty name="guiaPagamento" property="cliente">
						<logic:equal name="guiaPagamento" property="cliente.clienteTipo.indicadorPessoaFisicaJuridica" value="1">
							<input name="cpfCnpjCliente" type="text" readonly="true" style="background-color:#EFEFEF; border:0" size="17" maxlength="17"
							value='<bean:write name="guiaPagamento" property="cliente.cpf" />'>
						</logic:equal>
						<logic:notEqual name="guiaPagamento" property="cliente.clienteTipo.indicadorPessoaFisicaJuridica" value="1">
							<input name="cpfCnpjCliente" type="text" readonly="true" style="background-color:#EFEFEF; border:0" size="17" maxlength="17"
							value='<bean:write name="guiaPagamento" property="cliente.cnpj" />'>
						</logic:notEqual>	
					</logic:notEmpty>			
				</td>
			</tr>

			<tr>
				<td class="style1"><strong>Nome do Cliente:</strong></td>
				<td class="style1"><span class="style1"> 
					<logic:notEmpty name="guiaPagamento" property="cliente">
						<input name="nomeCliente" type="text" readonly="readonly" style="background-color:#EFEFEF; border:0" value='<bean:write name="guiaPagamento" property="cliente.nome" />' size="50" maxlength="45">
					</logic:notEmpty>	 
					<logic:empty name="guiaPagamento" property="cliente">
						<input name="nomeCliente" type="text" readonly="readonly" style="background-color:#EFEFEF; border:0" value="" size="50" maxlength="45">
					</logic:empty>
					</span>
				</td>
			</tr>
			
			<tr>
				<td><strong>Situa&ccedil;&atilde;o da Guia de Pagamento:</strong></td>
				<td align="right">
				<div align="left"><input name="situacaoGuiaPagamento" type="text"
					readonly style="background-color:#EFEFEF; border:0" size="30"
					maxlength="30"
					value='<bean:write name="guiaPagamento" property="debitoCreditoSituacaoAtual.descricaoDebitoCreditoSituacao" />'></div>
				</td>
			</tr>	
			
			<tr>
				<td><strong>Valor:</strong></td>
				<td align="right">
				<div align="left"><input name="guiaPagamento" type="text"
					readonly style="background-color:#EFEFEF; border:0" size="30"
					maxlength="30"
					value='<bean:write name="guiaPagamento" property="valorDebito"  formatKey="money.format"/>'></div>
				</td>	
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
							cellpadding="0" cellspacing="0">
							<tr>
								<td><strong>Dados das Prestações:</strong></td>
							</tr>
							<tr bgcolor="#cbe5fe">
								<td width="100%" align="center">
	
								<table width="100%" cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td></td>
									</tr>
									<tr>
										<td colspan="10" bgcolor="#000000" height="2"></td>
									</tr>
	
									<tr bordercolor="#000000">
										<td width="25%" bgcolor="#90c7fc" align="left"><strong>Prestação</strong></td>										
										
										<td width="25%" bgcolor="#90c7fc" align="center"><strong>Valor da Prestação</strong></td>

										<td width="35%" bgcolor="#90c7fc" align="center"><strong>Vencimento</strong></td>
									</tr>
									
									<tr>
										<td colspan="10"> 
										<div style="width: 100%; height: 180; overflow: auto;">
										<table width="100%" bgcolor="#99CCFF">
													<logic:present name="colecaoGuiasPrestacoes">
													<%int cont = 0; int indiceDtVenc = -1;%>
													<logic:iterate name="colecaoGuiasPrestacoesAlterarVencimento" id="guiaPagamentoPrestacao" type="GuiaPagamentoPrestacaoHelper">
															<%cont = cont + 1;
		
															if (cont % 2 == 0) {%>
																	<tr bgcolor="#cbe5fe">
															<%} else { %>
																	<tr bgcolor="#FFFFFF">
															<%}%>
															

																	<td width="25%" align="center">
																		<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
																			<bean:write name="guiaPagamentoPrestacao" property="numeroPrestacao" />
																		</font>																																				
																	</td>
		
																	<td width="25%" align="center">
																		<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
																			<bean:write name="guiaPagamentoPrestacao" property="valorTotalPorPrestacao" formatKey="money.format" />
																		</font>
																	</td>

																<logic:present name="guiaPagamentoPrestacao" property="idOcorrenciaHistorico">
																<%indiceDtVenc += 1;%>
																<td width="35%" align="center">
																	<html:text style="background-color: #DCDCDC" name="guiaPagamentoPrestacao" property="dataVencimento" size="10" maxlength="10"
																		onkeyup="mascaraData(this,event)"  tabindex="2"  readonly="true"
																		value="<%="" + Util.formatarData(guiaPagamentoPrestacao.getDataVencimento())%>"/>																		
																</td>
																<input type="hidden" id="dataVencimentoHidden" name="dataVencimentoHidden" value="<%=guiaPagamentoPrestacao.getDataVencimento()%>" />																
																</logic:present>
																
																<logic:notPresent name="guiaPagamentoPrestacao" property="idOcorrenciaHistorico">
																<%indiceDtVenc += 1;%>
																<td width="35%" align="center">
																	<html:text name="guiaPagamentoPrestacao" property="dataVencimento" size="10" maxlength="10"
																		onkeyup="mascaraData(this,event)" onblur="<%="validarData(this,"+indiceDtVenc+",event)"%>" tabindex="2" 
																		value="<%="" + Util.formatarData(guiaPagamentoPrestacao.getDataVencimento())%>"/>																		
																		
																</td>
																<input type="hidden" id="dataVencimentoHidden" name="dataVencimentoHidden" value="<%=guiaPagamentoPrestacao.getDataVencimento()%>" />
																</logic:notPresent>												
															</tr>
													</logic:iterate>
												</logic:present>
										</table>
										</div>
										</td>
									</tr>
	
									
								</table>
						</table>
					</td>
				</tr>	
						
			<tr>
				<td colspan="2">
				<hr>
				</td>
			</tr>
			<table width="100%" border="0">
				<tr>
					<td height="24"><logic:present
						name="caminhoRetornoTelaConsultaGuiaPagamento">
						<input type="button" class="bottonRightCol" value="Voltar"
							style="width: 70px;" onclick="javascript:history.back();" />
					</logic:present>
					<td align="right"><input name="Button" type="button"
						class="bottonRightCol" value="Fechar"
						onClick="javascript:window.close();"></td>
						
					<td align="center">					
						<input type="button" 
									 	name="" 
									 	value="Confirmar" 
									 	class="bottonRightCol" 
										onclick="validarAlterarVencimento()"
										style="width:100px"/>
					</td>
				</tr>
			</table>
		
		</table>
		</td>
		</tr>
	</table>

</html:form>
</body>
<!-- guia_pagamento_consulta.jsp -->
</html>
