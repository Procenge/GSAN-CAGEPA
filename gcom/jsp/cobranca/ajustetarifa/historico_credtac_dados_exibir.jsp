<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="gcom.cobranca.ajustetarifa.AjusteTarifaConta"%>
<%@ page import="gcom.faturamento.conta.Conta"%>
<%@ page import="gcom.faturamento.conta.ContaGeral"%>
<%@ page import="gcom.faturamento.conta.ContaHistorico"%>
<%@ page import="gcom.util.Util"%>
<%@ page import="gcom.faturamento.bean.DadosHistoricoCREDTACHelper"%>

<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false"
	formName="ExibirDadosHistoricoCREDTACActionForm" />

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript">

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

</script>

</head>

<body leftmargin="5" topmargin="5">

<html:form action="/exibirDadosHistoricoCREDTACAction"
	name="ExibirDadosHistoricoCREDTACActionForm"
	type="gcom.gui.cobranca.ajustetarifa.ExibirDadosHistoricoCREDTACActionForm"
	method="post">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	
	<input type="hidden" name="checkConta" value="0">
	<input type="hidden" name="checkGuia" value="0">

	<table width="770" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="130" valign="top" class="leftcoltext">
			<div align="center"><%@ include
				file="/jsp/util/informacoes_usuario.jsp"%> <%@ include
				file="/jsp/util/mensagens.jsp"%></div>
			</td>

			<td width="625" valign="top" class="centercoltext">

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Apresentar Dados Histórico CREDTAC</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>

			<p>&nbsp;</p>

			<table width="100%" border="0">
				
				<!-- Dados do Imóvel -->
				<tr>
					<td colspan="4">
					
						<table width="100%" border="0" bgcolor="#99CCFF">
							<tr bgcolor="#99CCFF">
								<td align="center"><span class="style2"> <b>Dados
								do Imóvel</b></span></td>
							</tr>
							<tr bgcolor="#cbe5fe">
								<td>
								<table width="100%">
								
									<tr>
										<td width="10%"><strong>Imóvel:</strong></td>
										<td width="90%"><html:text property="idImovel" readonly="true"
											style="background-color:#EFEFEF; border:0;" size="9" /></td>
									</tr>
									
									<tr>
										<td width="25%"><strong>Inscrição:</strong></td>
										<td><html:text property="inscricaoFormatadaImovel"
											readonly="true" style="background-color:#EFEFEF; border:0;"
											size="20" /></td>
									</tr>
									
									<tr>
										<td colspan="4">
										<table width="100%" align="center" bgcolor="#99CCFF" border="0">
											<tr>
												<td align="center">
												<div class="style9"><strong>Endereço do Imóvel</strong></div>
												</td>
											</tr>
											<tr bgcolor="#FFFFFF">
												<td align="center">
												<div class="style9">${ExibirDadosHistoricoCREDTACActionForm.enderecoImovel}
												&nbsp;</div>
												</td>
											</tr>
										</table>
										</td>
									</tr>
									
								</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				
				<!-- Dados de Ajuste Tarifa (Recálculo - CREDTAC) -->
				<tr>
					<td colspan="4">
					
						<table width="100%" border="0" bgcolor="#99CCFF">
							<tr bgcolor="#cbe5fe">
								<td>
									<table width="100%">
										<logic:present name="colecaoDadosHistoricoCREDTACHelper">
											<tr>
												<td width="100%"><strong>Dados do Recálculo:</strong></td>
											</tr>
		
											<!-- Dados do Recálculo Cabeçalho -->
											<tr>
												<td colspan="4">
													<table width="100%" align="center" bgcolor="#90c7fc"
														border="0">
														<tr bordercolor="#000000">
														
															<td width="25%" bgcolor="#90c7fc" align="center">
																<div class="style9"><font color="#000000"
																	style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif"> <strong>Situação de Água</strong>
																</font></div>
															</td>
															
															<td width="25%" bgcolor="#90c7fc" align="center">
															<div class="style9"><font color="#000000"
																style="font-size: 9px"
																face="Verdana, Arial, Helvetica, sans-serif"> <strong>Situação de Esgoto</strong>
															</font></div>
															</td>
																
															<td width="25%" bgcolor="#90c7fc" align="center"><font
																color="#000000" style="font-size: 9px"
																face="Verdana, Arial, Helvetica, sans-serif"><strong>Categoria</strong> </font></td>
																
															<td width="25%" bgcolor="#90c7fc" align="center"><font
																color="#000000" style="font-size: 9px"
																face="Verdana, Arial, Helvetica, sans-serif"><strong>Tarifa</strong> </font></td>										
															
														</tr>
														<tr>
															<td width="100%" colspan="4">
																<div style="width: 100%; height: 100%; overflow: auto;">
																	<table width="100%" align="left" bgcolor="#99CCFF">
												
																		<logic:notEmpty name="colecaoDadosHistoricoCREDTACHelper">
																			<logic:iterate name="colecaoDadosHistoricoCREDTACHelper"
																				id="dadosHistoricoCREDTACHelper"
																				type="DadosHistoricoCREDTACHelper">
																				
																				<tr bgcolor="#FFFFFF">
																				
																					
																						<td width="25%" align="center"><font
																							color="#000000" style="font-size: 9px"
																							face="Verdana, Arial, Helvetica, sans-serif">
																							
																							<logic:notEmpty name="dadosHistoricoCREDTACHelper" property="descricaoLigacaoAguaSituacao">
																								<bean:write name="dadosHistoricoCREDTACHelper" property="descricaoLigacaoAguaSituacao" />
																							</logic:notEmpty>
																						</font></td>
																						
																						<td width="25%" align="center"><font
																							color="#000000" style="font-size: 9px"
																							face="Verdana, Arial, Helvetica, sans-serif">
																							
																							<logic:notEmpty name="dadosHistoricoCREDTACHelper" property="descricaoLigacaoEsgotoSituacao">
																								<bean:write name="dadosHistoricoCREDTACHelper" property="descricaoLigacaoEsgotoSituacao" />
																							</logic:notEmpty>
																						</font></td>
																						
																						<td width="25%" align="center"><font
																							color="#000000" style="font-size: 9px"
																							face="Verdana, Arial, Helvetica, sans-serif">
																							
																							<logic:notEmpty name="dadosHistoricoCREDTACHelper" property="descricaoCategoria">
																								<bean:write name="dadosHistoricoCREDTACHelper" property="descricaoCategoria" />
																							</logic:notEmpty>
																						</font></td>
																						
																						<td width="25%" align="center"><font
																							color="#000000" style="font-size: 9px"
																							face="Verdana, Arial, Helvetica, sans-serif">
																							
																							<logic:notEmpty name="dadosHistoricoCREDTACHelper" property="tarifaConsumo">
																								<bean:write name="dadosHistoricoCREDTACHelper" property="tarifaConsumo" />
																							</logic:notEmpty>
																						</font></td>
																						
																					</tr>
																					
																					<!-- Dados do Recálculo Detalhe 1 -->
																					<tr bordercolor="#000000">
																						<td colspan="4">
																							<table width="100%">
																								<tr>
																									<td width="12%" bgcolor="#90c7fc" align="center">
																										<div class="style9"><font color="#000000"
																											style="font-size: 9px"
																											face="Verdana, Arial, Helvetica, sans-serif"> <strong>Data Cálculo</strong>
																										</font></div>
																									</td>
																									
																									<td width="12%" bgcolor="#90c7fc" align="center">
																									<div class="style9"><font color="#000000"
																										style="font-size: 9px"
																										face="Verdana, Arial, Helvetica, sans-serif"> <strong>Número Parcelas</strong>
																									</font></div>
																									</td>
																										
																									<td  width="76%" bgcolor="#90c7fc" align="center"><font
																										color="#000000" style="font-size: 9px"
																										face="Verdana, Arial, Helvetica, sans-serif"><strong>Desc. Processamento</strong> </font></td>
																								</tr>
																							</table>
																						</td>
																					</tr>
		
																					<tr bgcolor="#FFFFFF">
																					
																						<td colspan="4">
																							<table width="100%">
																								<tr>
																									<td width="12%" align="center"><font
																										color="#000000" style="font-size: 9px"
																										face="Verdana, Arial, Helvetica, sans-serif">
																										
																										<logic:notEmpty name="dadosHistoricoCREDTACHelper" property="dataCalculo">
																											<bean:write name="dadosHistoricoCREDTACHelper" property="dataCalculo" />
																										</logic:notEmpty>
																									</font></td>
																									
																									<td width="12%" align="center"><font
																										color="#000000" style="font-size: 9px"
																										face="Verdana, Arial, Helvetica, sans-serif">
																										
																										<logic:notEmpty name="dadosHistoricoCREDTACHelper" property="numeroParcelas">
																											<bean:write name="dadosHistoricoCREDTACHelper" property="numeroParcelas" />
																										</logic:notEmpty>
																									</font></td>
																									
																									<td width="76%" align="center"><font
																										color="#000000" style="font-size: 9px"
																										face="Verdana, Arial, Helvetica, sans-serif">
																										
																										<logic:notEmpty name="dadosHistoricoCREDTACHelper" property="descricaoProcessamento">
																											<bean:write name="dadosHistoricoCREDTACHelper" property="descricaoProcessamento" />
																										</logic:notEmpty>
																									</font></td>
																								</tr>
																							</table>
																						</td>
																					</tr>
																					
																					<!-- Dados do Recálculo Detalhe 2 -->
																					<tr bordercolor="#000000">
																						<td colspan="4">
																							<table width="100%">
																								<tr>
																									<td width="25%" bgcolor="#90c7fc" align="center">
																										<div class="style9"><font color="#000000"
																											style="font-size: 9px"
																											face="Verdana, Arial, Helvetica, sans-serif"> <strong>Total Anterior</strong>
																										</font></div>
																									</td>
																									
																									<td width="25%" bgcolor="#90c7fc" align="center">
																									<div class="style9"><font color="#000000"
																										style="font-size: 9px"
																										face="Verdana, Arial, Helvetica, sans-serif"> <strong>Total Atual</strong>
																									</font></div>
																									</td>
																										
																									<td  width="25%" bgcolor="#90c7fc" align="center"><font
																										color="#000000" style="font-size: 9px"
																										face="Verdana, Arial, Helvetica, sans-serif"><strong>Total do Crédito</strong> </font></td>
																									
																									<td  width="25%" bgcolor="#90c7fc" align="center"><font
																										color="#000000" style="font-size: 9px"
																										face="Verdana, Arial, Helvetica, sans-serif"><strong>Saldo</strong> </font></td>
																								</tr>
																							</table>
																						</td>
																					</tr>
		
																					<tr bgcolor="#FFFFFF">
																					
																						<td colspan="4">
																							<table width="100%">
																								<tr>
																									<td width="25%" align="center"><font
																										color="#000000" style="font-size: 9px"
																										face="Verdana, Arial, Helvetica, sans-serif">
																										
																										<logic:notEmpty name="dadosHistoricoCREDTACHelper" property="valorAnterior">
																											<bean:write name="dadosHistoricoCREDTACHelper" property="valorAnterior" />
																										</logic:notEmpty>
																									</font></td>
																									
																									<td width="25%" align="center"><font
																										color="#000000" style="font-size: 9px"
																										face="Verdana, Arial, Helvetica, sans-serif">
																										
																										<logic:notEmpty name="dadosHistoricoCREDTACHelper" property="valorAtual">
																											<bean:write name="dadosHistoricoCREDTACHelper" property="valorAtual" />
																										</logic:notEmpty>
																									</font></td>
																									
																									<td width="25%" align="center"><font
																										color="#000000" style="font-size: 9px"
																										face="Verdana, Arial, Helvetica, sans-serif">
																										
																										<logic:notEmpty name="dadosHistoricoCREDTACHelper" property="valorCredito">
																											<bean:write name="dadosHistoricoCREDTACHelper" property="valorCredito" />
																										</logic:notEmpty>
																									</font></td>
																									
																									<td width="25%" align="center"><font
																										color="#000000" style="font-size: 9px"
																										face="Verdana, Arial, Helvetica, sans-serif">
																										
																										<logic:notEmpty name="dadosHistoricoCREDTACHelper" property="valorSaldo">
																											<bean:write name="dadosHistoricoCREDTACHelper" property="valorSaldo" />
																										</logic:notEmpty>
																									</font></td>
																								</tr>
																							</table>
																						</td>
																					</tr>
																					
																					<!-- Dados do Recálculo Detalhe 3 -->
																					<tr bordercolor="#000000">
																						<td colspan="4">
																							<table width="100%">
																								<tr>
																									<td width="50%" bgcolor="#90c7fc" align="center">
																										<div class="style9"><font color="#000000"
																											style="font-size: 9px"
																											face="Verdana, Arial, Helvetica, sans-serif"> <strong>Residual</strong>
																										</font></div>
																									</td>
																									
																									<td width="50%" bgcolor="#90c7fc" align="center">
																									<div class="style9"><font color="#000000"
																										style="font-size: 9px"
																										face="Verdana, Arial, Helvetica, sans-serif"> <strong>Utilizado</strong>
																									</font></div>
																									</td>
																								</tr>
																							</table>
																						</td>
																					</tr>
		
																					<tr bgcolor="#FFFFFF">
																					
																						<td colspan="4">
																							<table width="100%">
																								<tr>
																									<td width="25%" align="center"><font
																										color="#000000" style="font-size: 9px"
																										face="Verdana, Arial, Helvetica, sans-serif">
																										
																										<logic:notEmpty name="dadosHistoricoCREDTACHelper" property="valorResidual">
																											<bean:write name="dadosHistoricoCREDTACHelper" property="valorResidual" />
																										</logic:notEmpty>
																									</font></td>
																									
																									<td width="25%" align="center"><font
																										color="#000000" style="font-size: 9px"
																										face="Verdana, Arial, Helvetica, sans-serif">
																										
																										<logic:notEmpty name="dadosHistoricoCREDTACHelper" property="valorUtilizado">
																											<bean:write name="dadosHistoricoCREDTACHelper" property="valorUtilizado" />
																										</logic:notEmpty>
																									</font></td>
																								</tr>
																							</table>
																						</td>
																					</tr>
																					
																					<!-- Exibir Dados do Dados do Recálculo Contas -->
																					<tr>
																						<td colspan="4">
																						<div id="layerHideDadosRecalculo<bean:write name="dadosHistoricoCREDTACHelper" property="ajusteTarifa.id" />" style="display: block">
																						<table width="100%" border="0" bgcolor="#99CCFF">
																							<tr bgcolor="#99CCFF">
																								<td align="center"><span class="style2"> <a
																									href="javascript:extendeTabela('DadosRecalculo<bean:write name="dadosHistoricoCREDTACHelper" property="ajusteTarifa.id" />',true);" ><b>Exibir Relação das Contas Recalculadas</b></a></span></td>
																							</tr>
																						</table>
																						</div>
																	
																						<div id="layerShowDadosRecalculo<bean:write name="dadosHistoricoCREDTACHelper" property="ajusteTarifa.id" />" style="display: none">
																						<table width="100%" border="0" bgcolor="#99CCFF">
																							<tr bgcolor="#99CCFF">
																								<td align="center"><span class="style2"> <a
																									href="javascript:extendeTabela('DadosRecalculo<bean:write name="dadosHistoricoCREDTACHelper" property="ajusteTarifa.id" />',false);" ><b>Exibir Relação das Contas Recalculadas</b></a></span></td>
																							</tr>
																							<tr bgcolor="#cbe5fe">
																								<td>
																								<table width="100%">
																									<logic:notEmpty name="dadosHistoricoCREDTACHelper" property="colecaoAjusteTarifaConta">
																										<tr>
																											<td width="100%"><strong>Relação das contas recalculadas:</strong></td>
																										</tr>
																	
																										<tr>
																											<td>
																											<table width="100%" align="center" bgcolor="#90c7fc"
																												border="0">
																												<tr bordercolor="#000000">
																												
																													<td width="10%" bgcolor="#90c7fc" align="center">
																														<div class="style9"><font color="#000000"
																															style="font-size: 9px"
																															face="Verdana, Arial, Helvetica, sans-serif"> <strong>Referência</strong>
																														</font></div>
																													</td>
																													
																													<td width="10%" bgcolor="#90c7fc" align="center">
																													<div class="style9"><font color="#000000"
																														style="font-size: 9px"
																														face="Verdana, Arial, Helvetica, sans-serif"> <strong>Situação</strong>
																													</font></div>
																													</td>
																														
																													<td width="14%" bgcolor="#90c7fc" align="center"><font
																														color="#000000" style="font-size: 9px"
																														face="Verdana, Arial, Helvetica, sans-serif"><strong>Valor Anterior Água</strong> </font></td>
																														
																													<td width="10%" bgcolor="#90c7fc" align="center"><font
																														color="#000000" style="font-size: 9px"
																														face="Verdana, Arial, Helvetica, sans-serif"><strong>Valor Atual Água</strong> </font></td>
																														
																													<td width="10%" bgcolor="#90c7fc" align="center"><font
																														color="#000000" style="font-size: 9px"
																														face="Verdana, Arial, Helvetica, sans-serif"><strong>Valor Anterior Esgoto</strong> </font></td>
																														
																													<td width="14%" bgcolor="#90c7fc" align="center"><font
																														color="#000000" style="font-size: 9px"
																														face="Verdana, Arial, Helvetica, sans-serif"><strong>Valor Atual Esgoto</strong> </font></td>
																														
																													<td width="16%" bgcolor="#90c7fc" align="center"><font
																														color="#000000" style="font-size: 9px"
																														face="Verdana, Arial, Helvetica, sans-serif"><strong>Valor Anterior Esgoto Especial</strong> </font></td>
																														
																													<td width="16%" bgcolor="#90c7fc" align="center"><font
																														color="#000000" style="font-size: 9px"
																														face="Verdana, Arial, Helvetica, sans-serif"><strong>Valor Atual Esgoto Especial</strong> </font></td>
																													
																												</tr>
																												<tr>
																													<td width="100%" colspan="11">
																													<div style="width: 100%; height: 100%; overflow: auto;">
																													<table width="100%" align="left" bgcolor="#99CCFF">
																														<!--corpo da segunda tabela-->
																														<%
																															int cont1 = 0;
																														%>
																														
																														<logic:iterate name="dadosHistoricoCREDTACHelper"
																															property="colecaoAjusteTarifaConta"
																															id="ajusteTarifaConta"
																															type="AjusteTarifaConta">
																															<%
																																cont1 = cont1 + 1;
																																if (cont1 % 2 == 0) {
																															%>
																															    <tr bgcolor="#cbe5fe">
																															<%} else {%>
																																<tr bgcolor="#FFFFFF">
																															<%}%>
																																
																																	<td width="10%" bordercolor="#90c7fc"
																																		align="center">
																																		<div class="style9">
																																			<font color="#000000"
																																				style="font-size: 9px"
																																				face="Verdana, Arial, Helvetica, sans-serif">  
																																				
																																				<logic:empty name="ajusteTarifaConta" property="contaGeral">
																																					NA
																																				</logic:empty>
																																				
																																				<logic:notEmpty name="ajusteTarifaConta" property="contaGeral">
																																				
																																					<logic:notEmpty name="ajusteTarifaConta" property="contaGeral.conta">
																																						<%=Util.formatarAnoMesParaMesAno(ajusteTarifaConta.getContaGeral().getConta().getReferencia())%>
																																					</logic:notEmpty>
																																					
																																					<logic:notEmpty name="ajusteTarifaConta" property="contaGeral.contaHistorico">
																																						<%=Util.formatarAnoMesParaMesAno(ajusteTarifaConta.getContaGeral().getContaHistorico().getAnoMesReferenciaConta())%>
																																					</logic:notEmpty>
																																					
																																				</logic:notEmpty>

																																			</font>
																																		</div>
																																	</td>
																																	
																																	<td width="10%" bordercolor="#90c7fc"
																																		align="center">
																																		<div class="style9">
																																			<font color="#000000"
																																				style="font-size: 9px"
																																				face="Verdana, Arial, Helvetica, sans-serif">
																																			<bean:write name="ajusteTarifaConta" property="situacaoFormatada" /> 
																																		</font>
																																		</div>
																																	</td>
																																	
																																	<td width="14%" align="center"><font
																																		color="#000000" style="font-size: 9px"
																																		face="Verdana, Arial, Helvetica, sans-serif">
																																		
																																		<logic:notEmpty name="ajusteTarifaConta" property="valorAguaAnterior">
																																			<bean:write name="ajusteTarifaConta" property="valorAguaAnterior" formatKey="money.format" />
																																		</logic:notEmpty>
																																	</font></td>
																																	
																																	<td width="10%" align="center"><font
																																		color="#000000" style="font-size: 9px"
																																		face="Verdana, Arial, Helvetica, sans-serif">
																																		
																																		<logic:notEmpty name="ajusteTarifaConta" property="valorAguaAtual">
																																			<bean:write name="ajusteTarifaConta" property="valorAguaAtual" formatKey="money.format" />
																																		</logic:notEmpty>
																																	</font></td>
																																	
																																	<td width="14%" align="center"><font
																																		color="#000000" style="font-size: 9px"
																																		face="Verdana, Arial, Helvetica, sans-serif">
																																		
																																		<logic:notEmpty name="ajusteTarifaConta" property="valorEsgotoAnterior">
																																			<bean:write name="ajusteTarifaConta" property="valorEsgotoAnterior" formatKey="money.format" />
																																		</logic:notEmpty>
																																	</font></td>
																																	
																																	<td width="10%" align="center"><font
																																		color="#000000" style="font-size: 9px"
																																		face="Verdana, Arial, Helvetica, sans-serif">
																																		
																																		<logic:notEmpty name="ajusteTarifaConta" property="valorEsgotoAtual">
																																			<bean:write name="ajusteTarifaConta" property="valorEsgotoAtual" formatKey="money.format" />
																																		</logic:notEmpty>
																																	</font></td>
																																	
																																	<td width="16%" align="center"><font
																																		color="#000000" style="font-size: 9px"
																																		face="Verdana, Arial, Helvetica, sans-serif">
																																		
																																		<logic:notEmpty name="ajusteTarifaConta" property="valorEsgotoEspecialAnterior">
																																			<bean:write name="ajusteTarifaConta" property="valorEsgotoEspecialAnterior" formatKey="money.format" />
																																		</logic:notEmpty>
																																	</font></td>
																																	
																																	<td width="16%" align="center"><font
																																		color="#000000" style="font-size: 9px"
																																		face="Verdana, Arial, Helvetica, sans-serif">
																																		
																																		<logic:notEmpty name="ajusteTarifaConta" property="valorEsgotoEspecialAtual">
																																			<bean:write name="ajusteTarifaConta" property="valorEsgotoEspecialAtual" formatKey="money.format" />
																																		</logic:notEmpty>
																																	</font></td>
																																	
																																</tr>
																														</logic:iterate>
		
																													</table>
																													</div>
																													</td>
																												</tr>
																											</table>
																											</td>
																										</tr>
																										
																									</logic:notEmpty>
																									
																									</table>
																								</td>
																							</tr>
																						</table>
																						</div>
																						</td>
																					</tr>
																			</logic:iterate>
																					<tr>
																						<td colspan="4" align="right">
																							<input name="Submit22" class="bottonRightCol" value="Consultar Log Transações"
																								type="button" onclick="javascript:abrirPopup('exibirLogTransacoesPopupAction.do?idRegistro=<bean:write name="dadosHistoricoCREDTACHelper" property="ajusteTarifa.id" />&idTabela=1885',500, 870);" align="left" style="width: 160px;">
																						</td>
																					</tr>
																		</logic:notEmpty>
																	</table>
																</div>
															</td>
														</tr>
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
					<td colspan="2" width="100%">
					<table width="100%">
						<tr>
							<td>
							<input name="button" class="bottonRightCol" value="Voltar"
								type="button" onclick="window.location.href='<html:rewrite page="/exibirConsultarHistoricoCREDTACAction.do"/>'" align="left" style="width: 80px;">

							<input name="Submit23" class="bottonRightCol" value="Cancelar"
								type="button" onclick="window.location.href='/gsan/telaPrincipal.do'">
							</td>
						</tr>
					</table>
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
</html:html>