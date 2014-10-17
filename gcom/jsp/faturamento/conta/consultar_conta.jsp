<%@page import="gcom.faturamento.debito.DebitoCreditoSituacao"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page import="gcom.util.Util"%>
<%@ page import="gcom.faturamento.conta.Conta"%>
<%@ page import="gcom.faturamento.conta.ContaHistorico"%>
<%@ page import="gcom.faturamento.conta.ContaPrescricaoHistorico"%>
<%@ page import="gcom.cadastro.cliente.ClienteConta"%>


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

<script language="JavaScript">
<!--
function fechar(){
  window.close();
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


-->
</script>

</head>

<body leftmargin="5" topmargin="5" onload="javascript:resizePageSemLink(800,550);">

<table width="770" border="0" cellspacing="5" cellpadding="0">
	<tr>
		<td width="750" valign="top" class="centercoltext">
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
				<td class="parabg">Consultar Conta</td>
				<td width="11"><img border="0"
					src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
			</tr>
		</table>

		<p>&nbsp;</p>

		<table width="100%" border="0">
			<tr>
				<td align="right"></td>
			</tr>
		</table>

		<%--FIM CONTA --%> <logic:present name="conta" scope="session">
			<table width="100%" border="0">
				<tr>
					<td height="25"><strong>Matr&iacute;cula do Im&oacute;vel:</strong></td>
					<td width="120"><html:text name="conta" property="imovel.id"
						size="12" maxlength="10" readonly="true"
						style="background-color:#EFEFEF; border:0" /></td>
					<td><strong>M&ecirc;s e Ano da Conta:</strong></td>
					<td>
						<input type="text" name="referencia" size="10"
						value=<%="" +Util.formatarMesAnoReferencia(((Conta)session.getAttribute("conta")).getReferencia())%>
						readonly="true" style="background-color:#EFEFEF; border:0" />
					</td>
				</tr>
				<!--============================ KASSIA ALBUQUERQUE	 30/08/2007	============================ -->
				<tr>
					<td height="25">
						<strong>Mês e Ano contábil:</strong>
					</td>
						<td width="120">
							<input type="text" name="mesAnoContabil" size="10"
							value=<%="" +Util.formatarMesAnoReferencia(((Conta)session.getAttribute("conta")).getReferenciaContabil())%>
							readonly="true" style="background-color:#EFEFEF; border:0" />
						</td>
					<td>
						<strong>Mês e Ano de baixa contábil:</strong>
					</td>
					<logic:present name="conta" property="referenciaBaixaContabil" scope="session">
						<td>
							<input type="text" name="mesAnoBaixaContabil" size="10"
							value=<%="" +Util.formatarMesAnoReferencia(((Conta)session.getAttribute("conta")).getReferenciaBaixaContabil())%>
							readonly="true" style="background-color:#EFEFEF; border:0" />
						</td>
					</logic:present>
					<logic:notPresent name="conta" property="referenciaBaixaContabil" scope="session">
						<td>
							<input type="text" name="mesAnoBaixaContabil" size="10" value=""
							readonly="true" style="background-color:#EFEFEF; border:0" />
						</td>
					</logic:notPresent>
				</tr>
				<!--============================ KASSIA ALBUQUERQUE	30/08/2007 ============================ -->
				<tr>
					<td width="183" height="25"><strong>Situa&ccedil;&atilde;o da Conta:</strong>
					</td>
					<td colspan="3">
						<logic:equal name="conta" property="debitoCreditoSituacaoAtual.id" value="<%= DebitoCreditoSituacao.PRESCRITA.toString() %>">
							<span style="color: #ff0000;"><html:text name="conta"
								property="debitoCreditoSituacaoAtual.descricaoDebitoCreditoSituacao"
								size="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000;" /></span>
						</logic:equal>
						<logic:notEqual name="conta" property="debitoCreditoSituacaoAtual.id" value="<%= DebitoCreditoSituacao.PRESCRITA.toString() %>">
							<html:text name="conta"
								property="debitoCreditoSituacaoAtual.descricaoDebitoCreditoSituacao"
								size="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0;" />
						</logic:notEqual>
					</td>
				</tr>

				<tr>
					<td><strong>Situa&ccedil;&atilde;o da Lig. de &Aacute;gua:</strong></td>
					<td><logic:present name="conta" property="ligacaoAguaSituacao">
						<html:text name="conta" property="ligacaoAguaSituacao.descricao"
							size="30" readonly="true"
							style="background-color:#EFEFEF; border:0" />
					</logic:present> <logic:notPresent name="conta"
						property="ligacaoAguaSituacao">
						<input type="text" name="ligacaoAguaSituacao" size="30"
							readonly="true" style="background-color:#EFEFEF; border:0" />
					</logic:notPresent></td>

					<td width="175"><strong>Situa&ccedil;&atilde;o da Lig. de Esgoto:</strong></td>
					<td width="122"><logic:present name="conta"
						property="ligacaoEsgotoSituacao">
						<html:text name="conta" property="ligacaoEsgotoSituacao.descricao"
							size="30" readonly="true"
							style="background-color:#EFEFEF; border:0" />
					</logic:present> <logic:notPresent name="conta"
						property="ligacaoEsgotoSituacao">
						<input type="text" name="ligacaoEsgotoSituacao" size="30"
							readonly="true" style="background-color:#EFEFEF; border:0" />
					</logic:notPresent></td>
				</tr>

				<tr>
					<td><strong>Motivo N&atilde;o Entrega:</strong></td>
					<td colspan="2"><logic:present name="conta"
						property="motivoNaoEntregaDocumento">
						<html:text name="conta"
							property="motivoNaoEntregaDocumento.descricao"
							size="30" readonly="true"
							style="background-color:#EFEFEF; border:0" />
					</logic:present> <logic:notPresent name="conta"
						property="motivoNaoEntregaDocumento">
						<input type="text" name="motivoNaoEntregaDocumento" size="30"
							readonly="true" style="background-color:#EFEFEF; border:0" />
					</logic:notPresent></td>
				</tr>

				<tr>
					<td width="50%"><strong>Cobran&ccedil;a de Multa:</strong></td>
					<td><logic:equal name="conta" property="indicadorCobrancaMulta"
						value="1">
						<input type="text" value="SIM" name="indicadorCobrancaMulta"
							size="4" readonly="true"
							style="background-color:#EFEFEF; border:0" />
					</logic:equal> <logic:equal name="conta"
						property="indicadorCobrancaMulta" value="2">
						<input type="text" value="NÃO" name="indicadorCobrancaMulta"
							size="4" readonly="true"
							style="background-color:#EFEFEF; border:0" />
					</logic:equal></td>

					<td width="50%"><strong>Altera&ccedil;&atilde;o de Vencimento:</strong></td>
					<td><logic:present name="conta"
						property="indicadorAlteracaoVencimento">
						<logic:equal name="conta" property="indicadorAlteracaoVencimento"
							value="1">
							<input type="text" value="SIM"
								name="indicadorAlteracaoVencimento" size="4" readonly="true"
								style="background-color:#EFEFEF; border:0" />
						</logic:equal>
						<logic:equal name="conta" property="indicadorAlteracaoVencimento"
							value="2">
							<input type="text" value="NÃO"
								name="indicadorAlteracaoVencimento" size="4" readonly="true"
								style="background-color:#EFEFEF; border:0" />
						</logic:equal>
					</logic:present> <logic:notPresent name="conta"
						property="indicadorAlteracaoVencimento">
						<input type="text" name="indicadorAlteracaoVencimento" size="4"
							readonly="true" style="background-color:#EFEFEF; border:0" />
					</logic:notPresent></td>
				</tr>

 				<!--============================ Alteração do caso de uso UC0204 ============================ -->
				<tr>
					<td colspan="4">
					<div id="layerHideLigacaoAgua" style="display:block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('LigacaoAgua',true);" /> <b>Histórico
							de Medição e Consumo da Ligação de Agua</b> </a> </span></td>
						</tr>
					</table>
					</div>

					<div id="layerShowLigacaoAgua" style="display:none">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('LigacaoAgua',false);" /> <b>Histórico
							de Medição e Consumo da Ligação de Agua</b> </a> </span></td>
						</tr>

						<tr>
							<td colspan="9">
							<table width="100%" bgcolor="#99CCFF">

								<tr>
									<td colspan="10" bgcolor="#79bbfd" align="center"><strong>Dados	da Medi&ccedil;&atilde;o do M&ecirc;s</strong></td>
								</tr>
								<!--header da tabela interna -->
								<tr bgcolor="#99CCFF" class="styleFontePeqNegrito">
								
									<td align="center" width="10%">Leitura Fat.</td>
									<td align="center" width="10%">Dt. Leitura Fat.</td>
									<td align="center" width="15%">Anormalidade Leitura Fat.</td>
									<td align="center" width="10%">Leitura Anter.</td>
									<td align="center" width="10%">Dt. Leitura Ant.</td>
									<td align="center" width="10%">Dias de Consumo</td>
									<td align="center" width="15%">Anormalidade de Consumo</td>
									<td align="center" width="10%">Situa&ccedil;&atilde;o da Leitura Atual</td>
									<td align="center" width="10%">Tipo de Consumo</td>	
									
								</tr>
								<tr bgcolor="#FFFFFF" class="styleFontePequena">
								
									<td align="center" width="10%">${sessionScope.consultarImovelActionForm.leituraAtualFaturada}&nbsp;</td>
									<td align="center" width="10%">${sessionScope.consultarImovelActionForm.dtLeituraFaturada}&nbsp;</td>
									<td align="center" width="15%">${sessionScope.consultarImovelActionForm.anormalidadeLeituraFaturada}&nbsp;</td>
									<td align="center" width="10%">${sessionScope.consultarImovelActionForm.leituraAnterior}&nbsp;</td>
									<td align="center" width="10%">${sessionScope.consultarImovelActionForm.dtLeituraAnterior}&nbsp;</td>
									<td align="center" width="10%">${sessionScope.consultarImovelActionForm.diasConsumo}&nbsp;</td>
									<td align="center" width="15%">${sessionScope.consultarImovelActionForm.anormalidadeConsumo}&nbsp;</td>
									<td align="center" width="10%">${sessionScope.consultarImovelActionForm.situacaoLeituraAtual}&nbsp;</td>
									<td align="center" width="10%">${sessionScope.consultarImovelActionForm.consumoTipo}&nbsp;</td>
									
								</tr>

							</table>
							</td>
						</tr>

					</table>
					</div>
					</td>
				</tr>
				
 				<!--============================ Alteração do caso de uso UC0204 ============================ -->
 				<tr>
					<td><strong>Consumo de &Aacute;gua:</strong></td>
					<td><logic:present name="conta" property="consumoAgua">
						<html:text name="conta" property="consumoAgua" size="7"
							readonly="true" style="background-color:#EFEFEF; border:0" /> m&#179;
					</logic:present> <logic:notPresent name="conta"
						property="consumoAgua">
						<input type="text" name="consumoAgua" size="7" readonly="true"
							style="background-color:#EFEFEF; border:0" />
					</logic:notPresent></td>
					<td><strong>Tarifa:</strong></td>
					<td>
						<input type="text" name="consumoTarifa" size="31" readonly="true" maxlength="31"
							value="<%=((Conta)session.getAttribute("conta")).getConsumoTarifa().getDescricao()%>" style="background-color:#EFEFEF; border:0" />
					</td>
				</tr>

				<tr>
					<td><strong>Consumo de Esgoto:</strong></td>
					<td><logic:present name="conta" property="consumoEsgoto">
						<html:text name="conta" property="consumoEsgoto" size="7"
							readonly="true" style="background-color:#EFEFEF; border:0" /> m&#179;
				</logic:present> <logic:notPresent name="conta"
						property="consumoEsgoto">
						<input type="text" name="consumoEsgoto" size="7" readonly="true"
							style="background-color:#EFEFEF; border:0" />
					</logic:notPresent></td>
					<td><strong>Percentual de Esgoto:</strong></td>
					<td><input type="text" name="percentualEsgoto"
						value=<%="" + Util.formatarMoedaReal(((Conta)session.getAttribute("conta")).getPercentualEsgoto())%>
						size="5" readonly="true"
						style="background-color:#EFEFEF; border:0" /></td>
				</tr>
				<tr>
					<td><strong>Crédito de Consumo:</strong></td>
					<td><logic:present name="conta" property="creditoConsumo">
						<html:text name="conta" property="creditoConsumo" size="7"
							readonly="true" style="background-color:#EFEFEF; border:0" /> m&#179;
					</logic:present> <logic:notPresent name="conta"
						property="creditoConsumo">
						<input type="text" name="creditoConsumo" size="7" readonly="true"
							style="background-color:#EFEFEF; border:0" />
					</logic:notPresent></td>
					
					<td><strong>Fixo de Esgoto:</strong></td>
					<td><logic:present name="conta" property="consumoMinimoEsgoto">
						<html:text name="conta" property="consumoMinimoEsgoto" size="7"
							readonly="true" style="background-color:#EFEFEF; border:0" /> m&#179;
					</logic:present> <logic:notPresent name="conta"
						property="consumoMinimoEsgoto">
						<input type="text" name="consumoMinimoEsgoto" size="7" readonly="true"
							style="background-color:#EFEFEF; border:0" />
					</logic:notPresent></td>
				</tr>
				<tr>
					<td><strong>Valor de &Aacute;gua:</strong></td>

					<td><input type="text" name="valorAgua"
						value=<%="" + Util.formatarMoedaReal(((Conta)session.getAttribute("conta")).getValorAgua())%>
						size="20" readonly="true"
						style="background-color:#EFEFEF; border:0; text-align: right;" />
					</td>
					<td><strong>Valor de Esgoto:</strong></td>
					<td><input type="text" name="valorEsgoto"
						value=<%="" + Util.formatarMoedaReal(((Conta)session.getAttribute("conta")).getValorEsgoto())%>
						size="20" readonly="true"
						style="background-color:#EFEFEF; border:0; text-align: right;" /></td>
				</tr>

				<tr>
					<td><strong>Valor dos D&eacute;bitos:</strong></td>
					<td><input type="text" name="debitos"
						value=<%="" + Util.formatarMoedaReal(((Conta)session.getAttribute("conta")).getDebitos())%>
						size="20" readonly="true"
						style="background-color:#EFEFEF; border:0; text-align: right;" /></td>

					<td><strong>Valor dos Cr&eacute;ditos:</strong></td>
					<td><logic:present name="conta" property="valorCreditos">
						<input type="text" name="valorCreditos"
							value=<%="" + Util.formatarMoedaReal(((Conta)session.getAttribute("conta")).getValorCreditos())%>
							size="20" readonly="true"
							style="background-color:#EFEFEF; border:0; text-align: right;" />
					</logic:present> <logic:notPresent name="conta"
						property="valorCreditos">
						<input type="text" name="valorCreditos" size="20" readonly="true"
							style="background-color:#EFEFEF; border:0; text-align: right;" />
					</logic:notPresent></td>
				</tr>

				<tr>
					<td><strong>Valor dos Impostos:</strong></td>
					<td><input type="text" name="impostos"
						value=<%="" + Util.formatarMoedaReal(((Conta)session.getAttribute("conta")).getValorImposto())%>
						size="20" readonly="true"
						style="background-color:#EFEFEF; border:0; text-align: right;" /></td>

					<td><strong>Valor Total da Conta:</strong></td>
					<td><input type="text" name="valorTotalConta"
						value=<%="" + Util.formatarMoedaReal(((Conta)session.getAttribute("conta")).getValorTotal())%>
						readonly="true"
						style="background-color:#EFEFEF; border:0; text-align: right;" /></td>
				</tr>

				<tr>
					<td><strong>D&eacute;bito Autom&aacute;tico:</strong></td>
					<td><logic:present name="conta" property="indicadorDebitoConta">
						<logic:equal name="conta" property="indicadorDebitoConta"
							value="1">
							<input type="text" value="SIM" name="indicadorDebitoConta"
								size="4" readonly="true"
								style="background-color:#EFEFEF; border:0" />
						</logic:equal>
						<logic:equal name="conta" property="indicadorDebitoConta"
							value="2">
							<input type="text" value="NÃO" name="indicadorDebitoConta"
								size="4" readonly="true"
								style="background-color:#EFEFEF; border:0" />
						</logic:equal>
					</logic:present> <logic:notPresent name="conta"
						property="indicadorDebitoConta">
						<input type="text" name="indicadorDebitoConta" size="4"
							readonly="true" style="background-color:#EFEFEF; border:0" />
					</logic:notPresent></td>
				</tr>

				<tr>
					<td><strong>Motivo da Inclus&atilde;o:</strong></td>
					<logic:present name="conta"
						property="contaMotivoInclusao">
						<td><html:text name="conta"
							property="contaMotivoInclusao.descricaoMotivoInclusaoConta"
							size="35" readonly="true"
							style="background-color:#EFEFEF; border:0" /></td>
					</logic:present> <logic:notPresent name="conta"
						property="contaMotivoInclusao">
						<td><input type="text" name="contaMotivoInclusao" size="35"
							readonly="true" style="background-color:#EFEFEF; border:0" /></td>
					</logic:notPresent>
					
					<logic:present name="conta" property="indicadorDebitoConta">
						<logic:equal name="conta" property="indicadorDebitoConta"
							value="1">
						<logic:present name="debitoAutomatico" property="agencia">
							<logic:present name="debitoAutomatico" property="agencia.banco">
							    <td><strong>Banco:</strong></td>
							    <td>
								<html:text name="debitoAutomatico" property="agencia.banco.descricao" 
								size="35" maxlength="10" readonly="true"
								style="background-color:#EFEFEF; border:0" />
							    </td>
							</logic:present>
						</logic:present>
						</logic:equal>
					</logic:present> 
					
				</tr>

				<tr>
					<td><strong>Motivo da Retifica&ccedil;&atilde;o:</strong></td>
					<logic:present name="conta"
						property="contaMotivoRetificacao">
						<td><html:text name="conta"
							property="contaMotivoRetificacao.descricaoMotivoRetificacaoConta"
							size="35" readonly="true"
							style="background-color:#EFEFEF; border:0" /></td>
					</logic:present> <logic:notPresent name="conta"
						property="contaMotivoRetificacao">
						<td><input type="text" name="contaMotivoRetificacao" size="35"
							readonly="true" style="background-color:#EFEFEF; border:0" /></td>
					</logic:notPresent>
					
					<logic:present name="conta" property="indicadorDebitoConta">
						<logic:equal name="conta" property="indicadorDebitoConta"
							value="1">
						<logic:present name="debitoAutomatico" property="agencia">
							<logic:present name="debitoAutomatico" property="agencia.banco">
							    <td><strong>Agencia:</strong></td>
							    <td>
							    <html:text name="debitoAutomatico" property="agencia.nomeAgencia"
								size="35" maxlength="10" readonly="true"
								style="background-color:#EFEFEF; border:0" />
							    </td>
							</logic:present>
						</logic:present>
						</logic:equal>
					</logic:present> 
				</tr>

				<tr>
					<td><strong>Motivo do Cancelamento:</strong></td>
					<logic:present name="conta"
						property="contaMotivoCancelamento">
						<td><html:text name="conta"
							property="contaMotivoCancelamento.descricaoMotivoCancelamentoConta"
							size="35" readonly="true"
							style="background-color:#EFEFEF; border:0" /></td>
					</logic:present> <logic:notPresent name="conta"
						property="contaMotivoCancelamento">
						<td><input type="text" name="contaMotivoCancelamento" size="35"
							readonly="true" style="background-color:#EFEFEF; border:0" /></td>
					</logic:notPresent>
					
				<logic:present name="conta" property="indicadorDebitoConta">
						<logic:equal name="conta" property="indicadorDebitoConta"
							value="1">
						<logic:present name="debitoAutomatico" property="agencia">
							<logic:present name="debitoAutomatico" property="agencia.banco">
							    <td><strong>Conta:</strong></td>
							    <td>
							    <html:text name="debitoAutomatico" property="identificacaoClienteBanco"
								size="12" maxlength="10" readonly="true"
								style="background-color:#EFEFEF; border:0" />
							    </td>
							</logic:present>
						</logic:present>
						</logic:equal>
					</logic:present> 
				</tr>

				<tr>
					<td><strong>Motivo da Revis&atilde;o:</strong></td>
					<td colspan="3"><logic:present name="conta"
						property="contaMotivoRevisao">
						<html:text name="conta"
							property="contaMotivoRevisao.descricaoMotivoRevisaoConta"
							size="35" readonly="true"
							style="background-color:#EFEFEF; border:0" />
					</logic:present> <logic:notPresent name="conta"
						property="contaMotivoRevisao">
						<input type="text" name="contaMotivoRevisao" size="35"
							readonly="true" style="background-color:#EFEFEF; border:0" />
					</logic:notPresent></td>
				</tr>
				
				<tr>
					<td><strong>Matrícula do Imóvel Origem:</strong></td>
					<td colspan="3">
						
						<logic:present name="conta" property="origem">
							<html:text name="conta" property="origem.conta.imovel.id"
							size="12" maxlength="10" readonly="true"
							style="background-color:#EFEFEF; border:0" />
						</logic:present>
						
						<logic:notPresent name="conta" property="origem">
							<input type="text" name="imovelOrigem" size="12" maxlength="10"
							readonly="true" style="background-color:#EFEFEF; border:0" />
						</logic:notPresent>
						
					</td>
				</tr>
			</table>
		</logic:present> <%--FIM CONTA --%> <%-- INICIO CONTA HISTORICO --%> <logic:present
			name="contaHistorico" scope="session">
			<table width="100%" border="0">
				<tr>
					<td height="25"><strong>Matr&iacute;cula do Im&oacute;vel:</strong></td>
					<td width="120"><html:text name="contaHistorico"
						property="imovel.id" size="12" maxlength="10" readonly="true"
						style="background-color:#EFEFEF; border:0" /></td>
					<td><strong>M&ecirc;s e Ano da Conta:</strong></td>
					<td><input type="text" name="referencia" size="10"
						value=<%="" +Util.formatarMesAnoReferencia(((ContaHistorico)session.getAttribute("contaHistorico")).getAnoMesReferenciaConta())%>
						readonly="true" style="background-color:#EFEFEF; border:0" /></td>
				</tr>
				<!--============================ KASSIA ALBUQUERQUE	30/08/2007 ============================ -->
				<tr>
					<td height="25">
						<strong>Mês e Ano contábil:</strong>
					</td>
					<logic:present name="contaHistorico" property="anoMesReferenciaContabil" scope="session">
						<td width="120">
							<input type="text" name="mesAnoContabil" size="10"
							value=<%="" +Util.formatarMesAnoReferencia(((ContaHistorico)session.getAttribute("contaHistorico")).getAnoMesReferenciaContabil())%>
							readonly="true" style="background-color:#EFEFEF; border:0" />
						</td>
					</logic:present>
					<logic:notPresent name="contaHistorico" property="anoMesReferenciaContabil" scope="session">
						<td width="120">
							<input type="text" name="mesAnoContabil" size="10" value=""
							readonly="true" style="background-color:#EFEFEF; border:0" />
						</td>
					</logic:notPresent>
					<td>
						<strong>Mês e Ano de baixa contábil:</strong>
					</td>
					
					<logic:present name="contaHistorico" property="anoMesReferenciaBaixaContabil" scope="session">
						<td>
							<input type="text" name="mesAnoBaixaContabil" size="10"
							value=<%="" +Util.formatarMesAnoReferencia(((ContaHistorico)session.getAttribute("contaHistorico")).getAnoMesReferenciaBaixaContabil())%>
							readonly="true" style="background-color:#EFEFEF; border:0" />
						</td>
					</logic:present>
					<logic:notPresent name="contaHistorico" property="anoMesReferenciaBaixaContabil" scope="session">
						<td>
							<input type="text" name="mesAnoBaixaContabil" size="10" value=""
							readonly="true" style="background-color:#EFEFEF; border:0" />
						</td>
					</logic:notPresent>
				</tr>
				<!--============================ KASSIA ALBUQUERQUE	30/08/2007 ============================ -->
				<tr>
					<td width="183" height="25"><strong>Situa&ccedil;&atilde;o da
					Conta:</strong></td>
					<td colspan="3"><html:text name="contaHistorico"
						property="debitoCreditoSituacaoAtual.descricaoDebitoCreditoSituacao"
						size="30" readonly="true"
						style="background-color:#EFEFEF; border:0" /></td>
				</tr>

				<tr>
					<td><strong>Situa&ccedil;&atilde;o da Lig. de &Aacute;gua:</strong></td>
					<td><logic:present name="contaHistorico"
						property="ligacaoAguaSituacao">
						<html:text name="contaHistorico"
							property="ligacaoAguaSituacao.descricao" size="20"
							readonly="true" style="background-color:#EFEFEF; border:0" />
					</logic:present> <logic:notPresent name="contaHistorico"
						property="ligacaoAguaSituacao">
						<input type="text" name="ligacaoAguaSituacao" size="20"
							readonly="true" style="background-color:#EFEFEF; border:0" />
					</logic:notPresent></td>

					<td width="175"><strong>Situa&ccedil;&atilde;o da Lig. de Esgoto:</strong></td>
					<td width="122"><logic:present name="contaHistorico"
						property="ligacaoEsgotoSituacao">
						<html:text name="contaHistorico"
							property="ligacaoEsgotoSituacao.descricao" size="20"
							readonly="true" style="background-color:#EFEFEF; border:0" />
					</logic:present> <logic:notPresent name="contaHistorico"
						property="ligacaoEsgotoSituacao">
						<input type="text" name="ligacaoEsgotoSituacao" size="20"
							readonly="true" style="background-color:#EFEFEF; border:0" />
					</logic:notPresent></td>
				</tr>

				<tr>
					<td><strong>Motivo N&atilde;o Entrega:</strong></td>
					<td colspan="2"><logic:present name="contaHistorico"
						property="motivoNaoEntregaDocumento">
						<html:text name="contaHistorico"
							property="motivoNaoEntregaDocumento.descricao"
							size="30" readonly="true"
							style="background-color:#EFEFEF; border:0" />
					</logic:present> <logic:notPresent name="contaHistorico"
						property="motivoNaoEntregaDocumento">
						<input type="text" name="motivoNaoEntregaDocumento" size="30"
							readonly="true" style="background-color:#EFEFEF; border:0" />
					</logic:notPresent></td>
				</tr>

				<tr>
					<td width="50%"><strong>Cobran&ccedil;a de Multa:</strong></td>
					<td><logic:equal name="contaHistorico"
						property="indicadorCobrancaMulta" value="1">
						<input type="text" value="SIM" name="indicadorCobrancaMulta"
							size="4" readonly="true"
							style="background-color:#EFEFEF; border:0" />
					</logic:equal> <logic:equal name="contaHistorico"
						property="indicadorCobrancaMulta" value="2">
						<input type="text" value="NÃO" name="indicadorCobrancaMulta"
							size="4" readonly="true"
							style="background-color:#EFEFEF; border:0" />
					</logic:equal></td>

					<td width="50%"><strong>Altera&ccedil;&atilde;o de Vencimento:</strong></td>
					<td><logic:present name="contaHistorico"
						property="indicadorAlteracaoVencimento">
						<logic:equal name="contaHistorico"
							property="indicadorAlteracaoVencimento" value="1">
							<input type="text" value="SIM"
								name="indicadorAlteracaoVencimento" size="4" readonly="true"
								style="background-color:#EFEFEF; border:0" />
						</logic:equal>
						<logic:equal name="contaHistorico"
							property="indicadorAlteracaoVencimento" value="2">
							<input type="text" value="NÃO"
								name="indicadorAlteracaoVencimento" size="4" readonly="true"
								style="background-color:#EFEFEF; border:0" />
						</logic:equal>
					</logic:present> <logic:notPresent name="contaHistorico"
						property="indicadorAlteracaoVencimento">
						<input type="text" name="indicadorAlteracaoVencimento" size="4"
							readonly="true" style="background-color:#EFEFEF; border:0" />
					</logic:notPresent></td>
				</tr>


			<!--============================ Alteração do caso de uso UC0204 ============================ -->
				<tr>
					<td colspan="4">
					<div id="layerHideLigacaoAgua" style="display:block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('LigacaoAgua',true);" /> <b>Histórico
							de Medição e Consumo da Ligação de Agua</b> </a> </span></td>
						</tr>
					</table>
					</div>

					<div id="layerShowLigacaoAgua" style="display:none">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('LigacaoAgua',false);" /> <b>Histórico
							de Medição e Consumo da Ligação de Agua</b> </a> </span></td>
						</tr>

						<tr>
							<td colspan="9">
							<table border="0" width="100%" bgcolor="#99CCFF">

								<tr>
									<td colspan="10" bgcolor="#79bbfd" align="center"><strong>Dados	da Medi&ccedil;&atilde;o do M&ecirc;s</strong></td>
								</tr>
								<!--header da tabela interna -->
								<tr bgcolor="#99CCFF" class="styleFontePeqNegrito">
								
									<td align="center" width="10%">Leitura Fat.</td>
									<td align="center" width="10%">Dt. Leitura Fat.</td>
									<td align="center" width="15%">Anormalidade Leitura Fat.</td>
									<td align="center" width="10%">Leitura Anter.</td>
									<td align="center" width="10%">Dt. Leitura Ant.</td>
									<td align="center" width="10%">Dias de Consumo</td>
									<td align="center" width="15%">Anormalidade de Consumo</td>
									<td align="center" width="10%">Situa&ccedil;&atilde;o da Leitura Atual</td>
									<td align="center" width="10%">Tipo de Consumo</td>	
									
								</tr>
								<tr bgcolor="#FFFFFF" class="styleFontePequena">
								
									<td align="center" width="10%">${sessionScope.consultarImovelActionForm.leituraAtualFaturada}&nbsp;</td>
									<td align="center" width="10%">${sessionScope.consultarImovelActionForm.dtLeituraFaturada}&nbsp;</td>
									<td align="center" width="15%">${sessionScope.consultarImovelActionForm.anormalidadeLeituraFaturada}&nbsp;</td>
									<td align="center" width="10%">${sessionScope.consultarImovelActionForm.leituraAnterior}&nbsp;</td>
									<td align="center" width="10%">${sessionScope.consultarImovelActionForm.dtLeituraAnterior}&nbsp;</td>
									<td align="center" width="10%">${sessionScope.consultarImovelActionForm.diasConsumo}&nbsp;</td>
									<td align="center" width="15%">${sessionScope.consultarImovelActionForm.anormalidadeConsumo}&nbsp;</td>
									<td align="center" width="10%">${sessionScope.consultarImovelActionForm.situacaoLeituraAtual}&nbsp;</td>
									<td align="center" width="10%">${sessionScope.consultarImovelActionForm.consumoTipo}&nbsp;</td>
									
								</tr>

							</table>
							</td>
						</tr>

					</table>
					</div>
					</td>
				</tr>
				
 		<!--============================ Alteração do caso de uso UC0204 ============================ -->


				<tr>
					<td><strong>Consumo de &Aacute;gua:</strong></td>
					<td><logic:present name="contaHistorico"
						property="consumoAgua">
						<html:text name="contaHistorico" property="consumoAgua" size="7"
							readonly="true" style="background-color:#EFEFEF; border:0" /> m&#179;
					</logic:present> <logic:notPresent name="contaHistorico"
						property="consumoAgua">
						<input type="text" name="consumoAgua" size="7" readonly="true"
							style="background-color:#EFEFEF; border:0" />
					</logic:notPresent></td>
					<td><strong>Tarifa:</strong></td>
					<td>
						<input type="text" name="creditoConsumo" size="20" readonly="true"
							value=<%="" + ((ContaHistorico)session.getAttribute("contaHistorico")).getConsumoTarifa().getDescricao()%> style="background-color:#EFEFEF; border:0" />
					</td>
				</tr>

				<tr>
					<td><strong>Consumo de Esgoto:</strong></td>
					<td><logic:present name="contaHistorico" property="consumoEsgoto">
						<html:text name="contaHistorico" property="consumoEsgoto" size="7"
							readonly="true" style="background-color:#EFEFEF; border:0" /> m&#179;
				</logic:present> <logic:notPresent name="contaHistorico"
						property="consumoEsgoto">
						<input type="text" name="consumoEsgoto" size="7" readonly="true"
							style="background-color:#EFEFEF; border:0" />
					</logic:notPresent></td>
					<td><strong>Percentual de Esgoto:</strong></td>
					<td><input type="text" name="percentualEsgoto"
						value=<%="" + Util.formatarMoedaReal(((ContaHistorico)session.getAttribute("contaHistorico")).getPercentualEsgoto())%>
						size="5" readonly="true"
						style="background-color:#EFEFEF; border:0" /></td>
				</tr>
				
				<tr>
				<td><strong>Fixo de Esgoto:</strong></td>
					<td><logic:present name="contaHistorico" property="consumoMinimoEsgoto">
						<html:text name="contaHistorico" property="consumoMinimoEsgoto" size="7"
							readonly="true" style="background-color:#EFEFEF; border:0" /> m&#179;
					</logic:present> <logic:notPresent name="contaHistorico"
						property="consumoMinimoEsgoto">
						<input type="text" name="consumoMinimoEsgoto" size="7" readonly="true"
							style="background-color:#EFEFEF; border:0" />
					</logic:notPresent></td>	
					
				</tr>			
				
				<tr>
					<td><strong>Valor de &Aacute;gua:</strong></td>
					<td><input type="text" name="valorAgua"
						value=<%="" + Util.formatarMoedaReal(((ContaHistorico)session.getAttribute("contaHistorico")).getValorAgua())%>
						size="20" readonly="true"
						style="background-color:#EFEFEF; border:0" /></td>
					<td><strong>Valor de Esgoto:</strong></td>
					<td><input type="text" name="valorEsgoto"
						value=<%="" + Util.formatarMoedaReal(((ContaHistorico)session.getAttribute("contaHistorico")).getValorEsgoto())%>
						size="20" readonly="true"
						style="background-color:#EFEFEF; border:0" /></td>
				</tr>

				<tr>
					<td><strong>Valor dos D&eacute;bitos:</strong></td>
					<td><input type="text" name="debitos"
						value=<%="" + Util.formatarMoedaReal(((ContaHistorico)session.getAttribute("contaHistorico")).getValorDebitos())%>
						size="20" readonly="true"
						style="background-color:#EFEFEF; border:0" /></td>

					<td><strong>Valor dos Cr&eacute;ditos:</strong></td>
					<td><logic:present name="contaHistorico" property="valorCreditos">
						<input type="text" name="valorCreditos"
							value=<%="" + Util.formatarMoedaReal(((ContaHistorico)session.getAttribute("contaHistorico")).getValorCreditos())%>
							size="20" readonly="true"
							style="background-color:#EFEFEF; border:0" />
					</logic:present> <logic:notPresent name="contaHistorico"
						property="valorCreditos">
						<input type="text" name="valorCreditos" size="20" readonly="true"
							style="background-color:#EFEFEF; border:0" />
					</logic:notPresent></td>
				</tr>

				<tr>
					<td><strong>Valor dos Impostos:</strong></td>
					<td><input type="text" name="impostos"
						value=<%="" + Util.formatarMoedaReal(((ContaHistorico)session.getAttribute("contaHistorico")).getValorImposto())%>
						size="20" readonly="true"
						style="background-color:#EFEFEF; border:0" /></td>

					<td><strong>Valor Total da Conta:</strong></td>
					<td><input type="text" name="valorTotalConta"
						value=<%="" + Util.formatarMoedaReal(((ContaHistorico)session.getAttribute("contaHistorico")).getValorTotal())%>
						readonly="true" style="background-color:#EFEFEF; border:0" /></td>
				</tr>

				<tr>
					<td><strong>D&eacute;bito Autom&aacute;tico:</strong></td>
					<td><logic:present name="contaHistorico"
						property="indicadorDebitoConta">
						<logic:equal name="contaHistorico" property="indicadorDebitoConta"
							value="1">
							<input type="text" value="SIM" name="indicadorDebitoConta"
								size="4" readonly="true"
								style="background-color:#EFEFEF; border:0" />
						</logic:equal>
						<logic:equal name="contaHistorico" property="indicadorDebitoConta"
							value="2">
							<input type="text" value="NÃO" name="indicadorDebitoConta"
								size="4" readonly="true"
								style="background-color:#EFEFEF; border:0" />
						</logic:equal>
					</logic:present> <logic:notPresent name="contaHistorico"
						property="indicadorDebitoConta">
						<input type="text" name="indicadorDebitoConta" size="4"
							readonly="true" style="background-color:#EFEFEF; border:0" />
					</logic:notPresent></td>
				</tr>

				<tr>
					<td><strong>Motivo da Inclus&atilde;o:</strong></td>
					<logic:present name="contaHistorico"
						property="contaMotivoInclusao">
						<td>
						<html:text name="contaHistorico"
							property="contaMotivoInclusao.descricaoMotivoInclusaoConta"
							size="35" readonly="true"
							style="background-color:#EFEFEF; border:0" /></td>
					</logic:present> <logic:notPresent name="contaHistorico"
						property="contaMotivoInclusao">
					<td>	<input type="text" name="contaMotivoInclusao" size="35"
							readonly="true" style="background-color:#EFEFEF; border:0" /></td>
					</logic:notPresent>
					
					<logic:present name="contaHistorico" property="indicadorDebitoConta">
						<logic:equal name="contaHistorico" property="indicadorDebitoConta"
							value="1">
						<logic:present name="debitoAutomatico" property="agencia">
							<logic:present name="debitoAutomatico" property="agencia.banco">
							    <td><strong>Banco:</strong></td>
							    <td>
								<html:text name="debitoAutomatico" property="agencia.banco.descricao" 
								size="35" maxlength="10" readonly="true"
								style="background-color:#EFEFEF; border:0" />
							    </td>
							</logic:present>
						</logic:present>
						</logic:equal>
					</logic:present> 
					
				</tr>

				<tr>
					<td><strong>Motivo da Retifica&ccedil;&atilde;o:</strong></td>
					<logic:present name="contaHistorico"
						property="contaMotivoRetificacao">
						<td><html:text name="contaHistorico"
							property="contaMotivoRetificacao.descricaoMotivoRetificacaoConta"
							size="35" readonly="true"
							style="background-color:#EFEFEF; border:0" /></td>
					</logic:present> <logic:notPresent name="contaHistorico"
						property="contaMotivoRetificacao">
						<td><input type="text" name="contaMotivoRetificacao" size="35"
							readonly="true" style="background-color:#EFEFEF; border:0" /></td>
					</logic:notPresent>
					
					<logic:present name="contaHistorico" property="indicadorDebitoConta">
						<logic:equal name="contaHistorico" property="indicadorDebitoConta"
							value="1">
						<logic:present name="debitoAutomatico" property="agencia">
							<logic:present name="debitoAutomatico" property="agencia.banco">
							    <td><strong>Agencia:</strong></td>
							    <td>
							    <html:text name="debitoAutomatico" property="agencia.nomeAgencia"
								size="35" maxlength="10" readonly="true"
								style="background-color:#EFEFEF; border:0" />
							    </td>
							</logic:present>
						</logic:present>
						</logic:equal>
					</logic:present>
					
				</tr>

				<tr>
					<td><strong>Motivo do Cancelamento:</strong></td>
					<logic:present name="contaHistorico"
						property="contaMotivoCancelamento">
						<td><html:text name="contaHistorico"
							property="contaMotivoCancelamento.descricaoMotivoCancelamentoConta"
							size="35" readonly="true"
							style="background-color:#EFEFEF; border:0" /></td>
					</logic:present> <logic:notPresent name="contaHistorico"
						property="contaMotivoCancelamento">
						<td><input type="text" name="contaMotivoCancelamento" size="35"
							readonly="true" style="background-color:#EFEFEF; border:0" /></td>
					</logic:notPresent>
					
					<logic:present name="contaHistorico" property="indicadorDebitoConta">
						<logic:equal name="contaHistorico" property="indicadorDebitoConta"
							value="1">
						<logic:present name="debitoAutomatico" property="agencia">
							<logic:present name="debitoAutomatico" property="agencia.banco">
							    <td><strong>Conta:</strong></td>
							    <td>
							    <html:text name="debitoAutomatico" property="identificacaoClienteBanco"
								size="14" maxlength="14" readonly="true"
								style="background-color:#EFEFEF; border:0" />
							    </td>
							</logic:present>
						</logic:present>
						</logic:equal>
					</logic:present> 
				</tr>

				<tr>
					<td><strong>Motivo da Revis&atilde;o:</strong></td>
					<td colspan="3"><logic:present name="contaHistorico"
						property="contaMotivoRevisao">
						<html:text name="contaHistorico"
							property="contaMotivoRevisao.descricaoMotivoRevisaoConta"
							size="35" readonly="true"
							style="background-color:#EFEFEF; border:0" />
					</logic:present> <logic:notPresent name="contaHistorico"
						property="contaMotivoRevisao">
						<input type="text" name="contaMotivoRevisao" size="35"
							readonly="true" style="background-color:#EFEFEF; border:0" />
					</logic:notPresent></td>
				</tr>
				
				<tr>
					<td><strong>Matrícula do Imóvel Origem:</strong></td>
					<td colspan="3">
						
						<logic:present name="conta" property="origem">
							<html:text name="conta" property="origem.conta.imovel.id"
							size="12" maxlength="10" readonly="true"
							style="background-color:#EFEFEF; border:0" />
						</logic:present>
						
						<logic:notPresent name="conta" property="origem">
							<input type="text" name="imovelOrigem" size="12" maxlength="10"
							readonly="true" style="background-color:#EFEFEF; border:0" />
						</logic:notPresent>
						
					</td>
				</tr>
			</table>
		</logic:present> 
		<!--============================== CLIENTES ===================================== -->
		<hr>
		<strong> CLIENTES: </strong>
		<table width="100%" border="0" bgcolor="#90c7fc">
			<tr bordercolor="#000000">
				<td width="28%" bgcolor="#90c7fc" align="center">
					<div class="style9">
						<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
							<strong>Nome do Cliente</strong>
						</font>
					</div>
				</td>
				<td width="17%" bgcolor="#90c7fc" align="center">
					<div class="style9">
						<font color="#000000" style="font-size:9px"	face="Verdana, Arial, Helvetica, sans-serif">
							<strong>Tipo da Rela&ccedil;&atilde;o</strong>
						</font>
					</div>
				</td>
				<td width="19%" bgcolor="#90c7fc" align="center">
					<div class="style9">
						<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
							<strong>Data In&iacute;cio Rela&ccedil;&atilde;o</strong>
						</font>
					</div>
				</td>
				<td width="16%" bgcolor="#90c7fc" align="center">
					<div class="style9">
						<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
							<strong>Telefone</strong>
						</font>
					</div>
				</td>
				<td width="20%" bgcolor="#90c7fc" align="center">
					<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
						<strong>CPF/CNPJ</strong>
					</font>
				</td>
			</tr>
			<tr>
				<td width="100%" colspan="5">
					<div style="width: 100%; height: 100%; overflow: auto;">
					<table width="100%" align="left" bgcolor="#99CCFF">
						<!--corpo da segunda tabela-->
						<%int cont2 = 0;%>
						<logic:present name="clientesConta">
							<logic:notEmpty name="clientesConta">
								<logic:iterate name="clientesConta" id="clienteConta" type="ClienteConta">
									<%cont2 = cont2 + 1;
										if (cont2 % 2 == 0) {%>
									<tr bgcolor="#cbe5fe">
										<%} else {%>
									<tr bgcolor="#FFFFFF">
										<%}%>
										<td width="28%" bordercolor="#90c7fc" align="left">
											<div class="style9">
												<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
													<logic:present name="clienteConta" property="cliente">
														<bean:write name="clienteConta" property="cliente.nome" />
													</logic:present>
												</font>
											</div>
										</td>
										<td width="17%" align="left">
											<div class="style9">
												<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
													<logic:present name="clienteConta" property="clienteRelacaoTipo">
														<bean:write name="clienteConta" property="clienteRelacaoTipo.descricao" />
													</logic:present>
												</font>
											</div>
										</td>
										<td width="19%" align="center">
											<logic:notEmpty name="clienteConta" property="cliente">
												<bean:define name="clienteConta" property="cliente" id="cliente" />
													<logic:notEmpty name="cliente" property="clienteImoveis">
														<bean:define name="cliente" property="clienteImoveis" id="clienteImoveis" />
														<logic:iterate name="clienteImoveis" id="clienteImovel">
															<div class="style9">
																<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"></font>
															</div>
															<bean:write name="clienteImovel" property="dataInicioRelacao" formatKey="date.format"/>
														</logic:iterate>
													</logic:notEmpty>
											</logic:notEmpty>
										</td>
										<td width="16%" align="right">
											<logic:notEmpty name="clienteConta" property="cliente">
												<bean:define name="clienteConta" property="cliente" id="cliente" />
													<logic:notEmpty name="cliente" property="clienteFones">
														<bean:define name="cliente" property="clienteFones" id="clienteFones" />
														<logic:iterate name="clienteFones" id="clienteFone">
															<div class="style9">
																<div align="center">
																	<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"></font>
																</div>
																<bean:write name="clienteFone" property="dddTelefone" />
															</div>
														</logic:iterate>
													</logic:notEmpty>
											</logic:notEmpty>
										</td>
										<td width="20%" align="right">
											<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
												<logic:notEmpty name="clienteConta" property="cliente.cpf">
													<bean:write name="clienteConta" property="cliente.cpfFormatado" />
												</logic:notEmpty>
												<logic:notEmpty name="clienteConta" property="cliente.cnpj">
													<bean:write name="clienteConta" property="cliente.cnpjFormatado" />
												</logic:notEmpty>
											</font>
										</td>
									</tr>
								</logic:iterate>
							</logic:notEmpty>
						</logic:present>
					</table>
				</div>
				</td>
			</tr>
		</table>
		
		<!--============================== DATAS ===================================== -->
		<hr>
		<strong> DATAS: </strong>
		<table width="100%" border="0" bgcolor="#90c7fc">
			<tr bordercolor="#FFFFFF" bgcolor="#90c7fc">
				<td width="15%">
				<div align="center"><strong>Vencimento</strong></div>
				</td>
				<td width="14%">
				<div align="center"><strong>Validade</strong></div>
				</td>
				<td width="18%">
				<div align="center"><strong>Inclus&atilde;o</strong></div>
				</td>
				<td width="16%">
				<div align="center"><strong>Retifica&ccedil;&atilde;o</strong></div>
				</td>
				<td width="19%">
				<div align="center"><strong>Cancelamento</strong></div>
				</td>
				<td height="20">
				<div align="center"><strong>Revis&atilde;o</strong></div>
				</td>
			</tr>

			<%--INICIO CONTA --%>
			<logic:present name="conta" scope="session">

				<tr bordercolor="#FFFFFF" bgcolor="#FFFFFF">
					<td width="12%">
					<div align="center"><logic:present name="conta"
						property="dataVencimentoConta">
						<span style="color: #000000;"><%=""
								+ Util.formatarData(((Conta) session
										.getAttribute("conta"))
										.getDataVencimentoConta())%></span>
					</logic:present> <logic:notPresent name="conta"
						property="dataVencimentoConta">
					&nbsp;
				  </logic:notPresent></div>
					</td>

					<td width="12%">
					<div align="center"><logic:present name="conta"
						property="dataValidadeConta">
						<span style="color: #000000;"><%=""
								+ Util.formatarData(((Conta) session
										.getAttribute("conta"))
										.getDataValidadeConta())%></span>
					</logic:present> <logic:notPresent name="conta"
						property="dataValidadeConta">
					&nbsp;
				  </logic:notPresent></div>
					</td>

					<td width="12%">
					<div align="center"><logic:present name="conta"
						property="dataInclusao">
						<span style="color: #000000;"><%=""
								+ Util.formatarData(((Conta) session
										.getAttribute("conta"))
										.getDataInclusao())%></span>
					</logic:present> <logic:notPresent name="conta"
						property="dataInclusao">
					&nbsp;
				  </logic:notPresent></div>
					</td>

					<td width="12%">
					<div align="center"><logic:present name="conta"
						property="dataRetificacao">
						<span style="color: #000000;"><%=""
								+ Util.formatarData(((Conta) session
										.getAttribute("conta"))
										.getDataRetificacao())%></span>
					</logic:present> <logic:notPresent name="conta"
						property="dataRetificacao">
					&nbsp;
				  </logic:notPresent></div>
					</td>

					<td width="12%">
					<div align="center"><logic:present name="conta"
						property="dataCancelamento">
						<span style="color: #000000;"><%=""
								+ Util.formatarData(((Conta) session
										.getAttribute("conta"))
										.getDataCancelamento())%></span>
					</logic:present> <logic:notPresent name="conta"
						property="dataCancelamento">
					&nbsp;
				  </logic:notPresent></div>
					</td>

					<td width="12%">
					<div align="center"><logic:present name="conta"
						property="dataRevisao">
						<span style="color: #000000;"><%=""
								+ Util.formatarData(((Conta) session
										.getAttribute("conta"))
										.getDataRevisao())%></span>
					</logic:present> <logic:notPresent name="conta"
						property="dataRevisao">
					&nbsp;
				  </logic:notPresent></div>
					</td>
				</tr>
				
				<tr bordercolor="#FFFFFF">
					<td colspan="2" width="15%" bgcolor="#90c7fc">
						<div align="center"><strong>Matr&iacute;cula dos Funcion&aacute;rios:</strong></div>
					</td>

					<td width="12%" bgcolor="#FFFFFF">
						<div align="center">
							<logic:present name="funcionarioContaHelp" scope="request">
								<bean:write name="funcionarioContaHelp" property="inclusao"/>
							</logic:present>
						</div>
					</td>

					<td width="12%" bgcolor="#FFFFFF">
						<div align="center">
							<logic:present name="funcionarioContaHelp" scope="request">
								<bean:write name="funcionarioContaHelp" property="retificacao"/>
							</logic:present>
						</div>
					</td>

					<td width="12%" bgcolor="#FFFFFF">
						<div align="center">
							<logic:present name="funcionarioContaHelp" scope="request">
								<bean:write name="funcionarioContaHelp" property="cancelamento"/>
							</logic:present>
						</div>
					</td>

					<td width="12%" bgcolor="#FFFFFF">
						<div align="center">
							<logic:present name="funcionarioContaHelp" scope="request">
								<bean:write name="funcionarioContaHelp" property="revisao"/>
							</logic:present>
						</div>
					</td>
				</tr>
			</logic:present>
			<%--FIM CONTA --%>

			<%--INICIO CONTA HISTORICO--%>
			<logic:present name="contaHistorico" scope="session">

				<tr bordercolor="#FFFFFF" bgcolor="#FFFFFF">
					<td width="12%">
					<div align="center"><logic:present name="contaHistorico"
						property="dataVencimentoConta">
						<span style="color: #000000;"><%=""
								+ Util.formatarData(((ContaHistorico) session
										.getAttribute("contaHistorico"))
										.getDataVencimentoConta())%></span>
					</logic:present> <logic:notPresent name="contaHistorico"
						property="dataVencimentoConta">
					&nbsp;
				  </logic:notPresent></div>
					</td>

					<td width="12%">
					<div align="center"><logic:present name="contaHistorico"
						property="dataValidadeConta">
						<span style="color: #000000;"><%=""
								+ Util.formatarData(((ContaHistorico) session
										.getAttribute("contaHistorico"))
										.getDataValidadeConta())%></span>
					</logic:present> <logic:notPresent name="contaHistorico"
						property="dataValidadeConta">
					&nbsp;
				  </logic:notPresent></div>
					</td>

					<td width="12%">
					<div align="center"><logic:present name="contaHistorico"
						property="dataInclusao">
						<span style="color: #000000;"><%=""
								+ Util.formatarData(((ContaHistorico) session
										.getAttribute("contaHistorico"))
										.getDataInclusao())%></span>
					</logic:present> <logic:notPresent name="contaHistorico"
						property="dataInclusao">
					&nbsp;
				  </logic:notPresent></div>
					</td>

					<td width="12%">
					<div align="center"><logic:present name="contaHistorico"
						property="dataRetificacao">
						<span style="color: #000000;"><%=""
								+ Util.formatarData(((ContaHistorico) session
										.getAttribute("contaHistorico"))
										.getDataRetificacao())%></span>
					</logic:present> <logic:notPresent name="contaHistorico"
						property="dataRetificacao">
					&nbsp;
				  </logic:notPresent></div>
					</td>

					<td width="12%">
					<div align="center"><logic:present name="contaHistorico"
						property="dataCancelamento">
						<span style="color: #000000;"><%=""
								+ Util.formatarData(((ContaHistorico) session
										.getAttribute("contaHistorico"))
										.getDataCancelamento())%></span>
					</logic:present> <logic:notPresent name="contaHistorico"
						property="dataCancelamento">
					&nbsp;
				  </logic:notPresent></div>
					</td>

					<td width="12%">
					<div align="center"><logic:present name="contaHistorico"
						property="dataRevisao">
						<span style="color: #000000;"><%=""
								+ Util.formatarData(((ContaHistorico) session
										.getAttribute("contaHistorico"))
										.getDataRevisao())%></span>
					</logic:present> <logic:notPresent name="contaHistorico"
						property="dataRevisao">
					&nbsp;
				  </logic:notPresent></div>
					</td>
				</tr>
				
				<tr bordercolor="#FFFFFF">
					<td colspan="2" width="15%" bgcolor="#90c7fc">
						<div align="center"><strong>Matr&iacute;cula dos Funcion&aacute;rios:</strong></div>
					</td>

					<td width="12%" bgcolor="#FFFFFF">
						<div align="center">
							<logic:present name="funcionarioContaHelp" scope="request">
								<bean:write name="funcionarioContaHelp" property="inclusao"/>
							</logic:present>
						</div>
					</td>

					<td width="12%" bgcolor="#FFFFFF">
						<div align="center">
							<logic:present name="funcionarioContaHelp" scope="request">
								<bean:write name="funcionarioContaHelp" property="retificacao"/>
							</logic:present>
						</div>
					</td>

					<td width="12%" bgcolor="#FFFFFF">
						<div align="center">
							<logic:present name="funcionarioContaHelp" scope="request">
								<bean:write name="funcionarioContaHelp" property="cancelamento"/>
							</logic:present>
						</div>
					</td>

					<td width="12%" bgcolor="#FFFFFF">
						<div align="center">
							<logic:present name="funcionarioContaHelp" scope="request">
								<bean:write name="funcionarioContaHelp" property="revisao"/>
							</logic:present>
						</div>
					</td>
				</tr>
			</logic:present>
			<%--FIM CONTA HISTORICO--%>

		</table>
		<!--========================================================================== -->

		<!--============================ CATEGORIAS ================================== -->
		<hr>
		<p></p>
		<strong> CATEGORIAS: </strong>
		<table width="100%" border="0" bgcolor="#90c7fc">
			<tr bordercolor="#FFFFFF" bgcolor="#90c7fc">
				<td width="30%">
				<div align="left"><strong>Categoria</strong></div>
				</td>
				<td width="70%">
				<div align="center"><strong>Quantidade de Economias </strong></div>
				</td>
			</tr>

			<logic:present name="colecaoContaCategoria">
				<%int cont = 0;%>
				<logic:iterate name="colecaoContaCategoria" id="contaCategoria">

					<%cont = cont + 1;
						if (cont % 2 == 0) {%>
					<tr bgcolor="#cbe5fe">
						<%} else {

						%>
					<tr bgcolor="#FFFFFF">
						<%}%>
						<td>
						<div align="left"><bean:write name="contaCategoria"
							property="comp_id.categoria.descricao" /></div>
						</td>

						<td>
						<div align="center"><bean:write name="contaCategoria"
							property="quantidadeEconomia" /></div>
						</td>

					</tr>
				</logic:iterate>
			</logic:present>
		</table>
		<!--========================================================================== -->

		<!--============================ IMPOSTOS RETIDOS ============================ -->
		<hr>
		<p></p>
		<strong> IMPOSTOS DEDUZIDOS: </strong>
		<table width="100%" border="0" bgcolor="#90c7fc">
			<tr bordercolor="#FFFFFF" bgcolor="#90c7fc">
				<td>
				<div align="left" class="style9"><strong>Imposto</strong></div>
				</td>
				<td>
				<div align="center" class="style9"><strong>Valor do Imposto</strong></div>
				</td>
			</tr>
			
			<logic:present name="colecaoContaImpostosDeduzidos">
				<%int cont1 = 0;%>
				<logic:iterate name="colecaoContaImpostosDeduzidos" id="contaImpostosDeduzidos">

					<%cont1 = cont1 + 1;
						if (cont1 % 2 == 0) {%>
					<tr bgcolor="#cbe5fe">
						<%} else {

						%>
					<tr bgcolor="#FFFFFF">
						<%}%>
						<td>
						<div align="left"><bean:write name="contaImpostosDeduzidos"
							property="impostoTipo.descricao" /></div>
						</td>

						<td>
						<div align="right"><bean:write name="contaImpostosDeduzidos"
							property="valorImposto" formatKey="money.format" /></div>
						</td>

					</tr>
					
				</logic:iterate>
			</logic:present>
			
		</table>
		
		<!--========================================================================== -->
		<!-- DADOS DE PRESCRIÇÃO DA CONTA -->
		<hr>
		<p></p>
		<strong> HISTÓRICO DE PRESCRIÇÃO DA CONTA: </strong>
		<logic:present name="colecaoContaPrescricaoHistorico">
			<table>
				<tr bordercolor="#FFFFFF" bgcolor="#90c7fc">
					<td>
						<div align="left" class="style9"><strong>Evento</strong></div>
					</td>
					<td>
						<div align="center" class="style9"><strong>Data do Evento</strong></div>
					</td>
					<td>
						<div align="right" class="style9"><strong>Valor da Conta</strong></div>
					</td>
					<td>
						<div align="left" class="style9"><strong>Usuário Responsável</strong></div>
					</td>
				</tr>
			
				<%int cont3 = 0;%>
				<logic:iterate name="colecaoContaPrescricaoHistorico" id="contaPrescricaoHistorico">

					<%cont3 = cont3 + 1;
						if (cont3 % 2 == 0) {%>
					<tr bgcolor="#cbe5fe">
						<%} else {

						%>
					<tr bgcolor="#FFFFFF">
						<%}%>
						
						<td>
							<div align="left"><bean:write name="contaPrescricaoHistorico"
							property="descricaoEvento" /></div>
						</td>
						
						<td>
							<div align="center"><bean:write name="contaPrescricaoHistorico"
							property="dataEvento" formatKey="date.format" /></div>
						</td>

						<td>
							<div align="right"><bean:write name="contaPrescricaoHistorico"
							property="valorConta" formatKey="money.format" /></div>
						</td>
						
						<td>
							<div align="left"><bean:write name="contaPrescricaoHistorico"
							property="usuario.descricaoUsuario" /></div>
						</td>

					</tr>
					
				</logic:iterate>
			</table>
		</logic:present>
		
		<table width="100%">
			<tr>
				<td height="24"><logic:present
					name="caminhoRetornoTelaConsultaConta">
					<input type="button" class="bottonRightCol" value="Voltar"
						style="width: 70px;"
						onclick="javascript:history.back();" />
				</logic:present>
				
				<td align="right" width="90%">
					<logic:notPresent name="indicadorEmitir2aViaConta">
						<logic:present name="emitirSegundaVia" scope="request">
							<input type="button" name="" value="Emitir 2ª Via de Conta"	class="bottonRightCol"
							onclick="javascript:window.location.href='<html:rewrite page="/gerarRelatorio2ViaContaAction.do?idNomeRelatorio=1"/>'" />
						</logic:present> 
						
						<logic:notPresent name="emitirSegundaVia" scope="request">
							<input type="button" name="" value="Emitir 2ª Via de Conta"
								class="bottonRightCol" disabled="true"/>
						</logic:notPresent>
					</logic:notPresent>	
					<logic:present name="indicadorEmitir2aViaConta">
						<input type="button" name="" value="Emitir 2ª Via de Conta"
								class="bottonRightCol" disabled="true"/>
					</logic:present> 
				</td>

				<td colspan="1" align="right"><input name="Button" type="button"
					class="bottonRightCol" value="Fechar"
					onClick="javascript:fechar();"></td>
			</tr>
		</table>

		</td>
	</tr>

</table>
</body>
</html:html>