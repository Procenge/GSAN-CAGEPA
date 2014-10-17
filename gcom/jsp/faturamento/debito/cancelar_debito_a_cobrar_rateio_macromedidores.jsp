<%@page import="gcom.util.Util"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page import="gcom.util.ConstantesSistema"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="gcom.faturamento.debito.ClienteImovelCondominioHelper"%>
<%@ page import="gcom.faturamento.debito.DebitosACobrarRateioImoveisVinculadosHelper"%>

<html:html>
	<head>
		<%@ include file="/jsp/util/titulo.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
		<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
		<html:javascript staticJavascript="false" formName="FiltrarCancelarDebitoACobrarRateioMacromedidoresActionForm" />
		<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
		<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
		<script language="JavaScript">
			function desfazer(){
				window.location.href='/gsan/exibirFiltrarCancelarDebitoACobrarRateioMacromedidoresAction.do?menu=sim';
			}
			
			function validarForm(){
				var form = document.forms[0];
				verficarSelecao(form.idDebitoACobrar, 'checkbox');
			}
			
			function verficarSelecao(objeto, tipoObjeto){

		       var indice;
		       var array = new Array(objeto.length);
		       var selecionado = "";
			   var formulario = document.forms[0]; 

			   for(indice = 0; indice < formulario.elements.length; indice++){
				 if (formulario.elements[indice].type == tipoObjeto && formulario.elements[indice].checked == true) {
					selecionado = formulario.elements[indice].value;
					break;
				 }
			   }

		       if (selecionado.length < 1) {
		         alert('Nenhum registro selecionado para cancelamento.');
		       }else {
				 if (confirm ("Confirma cancelamento?")) {
				 redirecionarSubmit("/gsan/cancelarDebitoACobrarRateioMacromedidoresAction.do");
				 }
			   }
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
		</script>
	</head>

	<body leftmargin="5" topmargin="5" onload="javascript:setarFoco('${requestScope.nomeCampo}');">
		<html:form action="/cancelarDebitoACobrarRateioMacromedidoresAction.do" method="post"
				name="FiltrarCancelarDebitoACobrarRateioMacromedidoresActionForm"
				type="gcom.gui.faturamento.debito.FiltrarCancelarDebitoACobrarRateioMacromedidoresActionForm">
			<%@ include file="/jsp/util/cabecalho.jsp"%>
			<%@ include file="/jsp/util/menu.jsp"%>
	
			<table width="770" border="0" cellspacing="4" cellpadding="0">
	
				<tr>
					<td width="149" valign="top" class="leftcoltext">
						<div align="center">
							<%@ include file="/jsp/util/informacoes_usuario.jsp"%>
							<%@ include file="/jsp/util/mensagens.jsp"%>
						</div>
					</td>
					<td width="600" valign="top" class="centercoltext">
						<p>&nbsp;</p>
						<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
							<tr>
								<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
								<td class="parabg">Cancelar D&eacute;bito a Cobrar Rateio Macromedidores</td>
								<td width="11" valign="top"><img border="0" src="imagens/parahead_right.gif" /></td>
							</tr>
						</table>
						<p>&nbsp;</p>
						
						<table width="100%" border="0">
							<tr>
								<td colspan="4">Para Cancelar o(s) Im&oacute;vel(eis) Condom&iacute;nios, informe os dados abaixo:</td>
								<td align="right"></td>
							</tr>
						</table>
							
						<table width="100%" border="0">
							<tr>
								<td >
									<strong>Matrícula do Imóvel Condomínio:</strong>
								</td>
							<td ><html:text property="matriculaImovel"
									size="15" maxlength="28" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000; float: left" />

							</td>
							<td >
									<strong>Inscrição:</strong>
								</td>
							<td ><html:text property="inscricaoImovel" size="19"
									maxlength="28" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</td>

						</tr>
						<tr>
							<td height="10">
								<div >
									<strong>Situação de Água:</strong>
								</div>
							</td>
							<td><html:text property="descricaoLigacaoAguaSituacao"
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000"
									size="15" maxlength="15" /></td>
								
									
							<td><strong>Situação de Esgoto:</strong></td>

							<td><html:text property="descricaoLigacaoEsgotoSituacao"
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000"
									size="15" maxlength="15" /></td>
	
						</tr>
						<tr>
								
							</tr>
							<tr>
								<td class="style1">&nbsp;</td>
								<td colspan="3" class="style1">&nbsp;</td>
							</tr>
							<tr>
								<td colspan="4">
									<table width="100%" align="center" bgcolor="#90c7fc" border="0">
										<tr bordercolor="#79bbfd">
											<td colspan="5" align="center" bgcolor="#79bbfd"><strong>Lista dos Clientes Imóvel Condomínio</strong></td>
										</tr>
										<tr bordercolor="#FFFFFF">
											<td width="30%" bgcolor="#79bbfd" align="center">
											<div class="style9"><font color="#000000" style="font-size: 9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <strong>Nome do Cliente</strong> </font></div>
											</td>
											<td width="20%" bgcolor="#79bbfd" align="center">
											<div class="style9"><font color="#000000" style="font-size: 9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <strong>Tipo de Relação</strong> </font></div>
											</td>
											<td width="20%" bgcolor="#79bbfd" align="center"><font
												color="#000000" style="font-size: 9px"
												face="Verdana, Arial, Helvetica, sans-serif"><strong>Data de Início da Relação</strong>
											</font></td>
											<td width="15%" bgcolor="#79bbfd" align="center"><font
												color="#000000" style="font-size: 9px"
												face="Verdana, Arial, Helvetica, sans-serif"><strong>Telefone principal</strong>
											</font></td>
											<td width="15%" bgcolor="#79bbfd" align="center"><font
												color="#000000" style="font-size: 9px"
												face="Verdana, Arial, Helvetica, sans-serif"><strong>CPF/CNPJ</strong>
											</font></td>
										</tr>
										<tr>
											<td width="100%" colspan="5">
											<div style="width: 100%; height: 100%; overflow: auto;">
											<table width="100%" align="left" bgcolor="#99CCFF">
												<!--corpo da segunda tabela-->
												<%
													int cont = 0;
												%>
												<logic:notEmpty name="listaClienteImovelCondominio">
													<logic:iterate name="listaClienteImovelCondominio"	id="clienteImovelCondominioHelper"
															type="ClienteImovelCondominioHelper">
														<%
															cont = cont + 1;
															if (cont % 2 == 0) {
														%>
														<tr bgcolor="#cbe5fe">
														<%
															} else {
														%>
														<tr bgcolor="#FFFFFF">
														<%
															}
														%>
															<td width="30%" bordercolor="#90c7fc" align="left">
																<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
																<a
																	href="javascript:abrirPopup('exibirConsultarClienteAction.do?desabilitarPesquisaCliente=SIM&codigoCliente='+
																		<bean:write name="clienteImovelCondominioHelper" property="idCliente" />, 500, 800);">
																	<bean:write name="clienteImovelCondominioHelper" property="nomeCliente" />
																</a>
																</font>
															</td>
															<td width="20%" bordercolor="#90c7fc" align="left">
																<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
																	<bean:write name="clienteImovelCondominioHelper" property="descricaoTipoRelacaoCliente" />
																</font>
															</td>
															<td width="20%" bordercolor="#90c7fc" align="right">
																<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
																<%Util.formatarData(clienteImovelCondominioHelper.getDataInicioRelacao()); %>
																</font>
															</td>
															<td width="15%" bordercolor="#90c7fc" align="right">
																<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
																	<bean:write name="clienteImovelCondominioHelper" property="telefoneFormatado" />
																</font>
															</td>
															<td width="15%" bordercolor="#90c7fc" align="right">
																<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
																	<bean:write name="clienteImovelCondominioHelper" property="cpfCnpj" />
																</font>
															</td>
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
							<tr>
								<td class="style1">&nbsp;</td>
								<td colspan="3" class="style1">&nbsp;</td>
							</tr>
							<tr>
								<td colspan="5">
									<table width="100%" align="center" border="0">
										<tr bordercolor="#90c7fc">
											<td bgcolor="#90c7fc" align="center">
												<strong>Endereço do Imóvel</strong> 
											</td>
										</tr>
										<tr>
											<td bordercolor="#FFFFFF" bgcolor="#FFFFFF" align="center">
												<bean:write name="FiltrarCancelarDebitoACobrarRateioMacromedidoresActionForm" property="enderecoImovel" />
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td colspan="4">
									<table width="100%" align="center" bgcolor="#90c7fc" border="0">
										<tr bordercolor="#79bbfd">
											<td colspan="6" align="center" bgcolor="#79bbfd"><strong>Histórico de Medição Individualizada</strong></td>
										</tr>
										<tr>
											<td width="20%" bgcolor="#90c7fc" align="center">
											<div class="style9"><font color="#000000" style="font-size: 9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <strong>Tipo Rateio</strong> </font></div>
											</td>
											<td width="15%" bgcolor="#90c7fc" align="center">
											<div class="style9"><font color="#000000" style="font-size: 9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <strong>Quantidade Imóveis Vinculados</strong> </font></div>
											</td>
											<td width="15%" bgcolor="#90c7fc" align="center"><font
												color="#000000" style="font-size: 9px"
												face="Verdana, Arial, Helvetica, sans-serif"><strong>Tipo Consumo</strong>
											</font></td>
											<td width="15%" bgcolor="#90c7fc" align="center"><font
												color="#000000" style="font-size: 9px"
												face="Verdana, Arial, Helvetica, sans-serif"><strong>Consumo Água Medido</strong>
											</font></td>
											<td width="15%" bgcolor="#90c7fc" align="center"><font
												color="#000000" style="font-size: 9px"
												face="Verdana, Arial, Helvetica, sans-serif"><strong>Consumo Rateio</strong>
											</font></td>
											<td width="20%" bgcolor="#90c7fc" align="center"><font
												color="#000000" style="font-size: 9px"
												face="Verdana, Arial, Helvetica, sans-serif"><strong>Consumo de Água Faturado</strong>
											</font></td>
										</tr>
										<tr>
											<td width="100%" colspan="6">
												<div style="width: 100%; height: 100%; overflow: auto;">
												<c:if test="${(FiltrarCancelarDebitoACobrarRateioMacromedidoresActionForm.historicoMedicaoIndividualizadaHelper != null)}">
													<table width="100%" align="left" bgcolor="#99CCFF">
														<tr bgcolor="#FFFFFF">
															<td width="20%" bordercolor="#90c7fc" align="left">
																<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
																	<bean:write name="FiltrarCancelarDebitoACobrarRateioMacromedidoresActionForm" property="historicoMedicaoIndividualizadaHelper.dsRateioTipo" />
																</font>
															</td>
															<td width="15%" bordercolor="#90c7fc" align="right">
																<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
																	<bean:write name="FiltrarCancelarDebitoACobrarRateioMacromedidoresActionForm" property="historicoMedicaoIndividualizadaHelper.quantidadeImoveisVinculados" />
																</font>
															</td>
															<td width="15%" bordercolor="#90c7fc" align="right">
																<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
																<bean:write name="FiltrarCancelarDebitoACobrarRateioMacromedidoresActionForm" property="historicoMedicaoIndividualizadaHelper.dsConsumoTipo" />
																</font>
															</td>
															<td width="15%" bordercolor="#90c7fc" align="right">
																<c:if test="${(FiltrarCancelarDebitoACobrarRateioMacromedidoresActionForm.historicoMedicaoIndividualizadaHelper.consumoMedidoMes == 0)}">
																	&nbsp;
																</c:if>
																<c:if test="${(FiltrarCancelarDebitoACobrarRateioMacromedidoresActionForm.historicoMedicaoIndividualizadaHelper.consumoMedidoMes != 0)}">
																	<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
																		<bean:write name="FiltrarCancelarDebitoACobrarRateioMacromedidoresActionForm" property="historicoMedicaoIndividualizadaHelper.consumoMedidoMes" />
																	</font>
																</c:if>
															</td>
															<td width="15%" bordercolor="#90c7fc" align="right">
																<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
																	<bean:write name="FiltrarCancelarDebitoACobrarRateioMacromedidoresActionForm" property="historicoMedicaoIndividualizadaHelper.consumoRateio" />																							</font>
															</td>
															<td width="20%" bordercolor="#90c7fc" align="right">
																<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
																	<bean:write name="FiltrarCancelarDebitoACobrarRateioMacromedidoresActionForm" property="historicoMedicaoIndividualizadaHelper.consumoFaturadoMes" />																							</font>
															</td>
														</tr>
													</table>
												</c:if>
												</div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="style1">&nbsp;</td>
								<td colspan="3" class="style1">&nbsp;</td>
							</tr>
							<tr>
								<td colspan="4">
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr bordercolor="#79bbfd">
											<td colspan="6" align="center" bgcolor="#79bbfd"><strong>Débitos a Cobrar de Rateio dos
											Imóveis Vinculados</strong></td>
										</tr>
										<tr>
											<td>
											<table width="100%" bgcolor="#90c7fc">
												<tr bgcolor="#90c7fc">
													<td width="5%" bgcolor="#90c7fc">
														<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
															<strong><a href="javascript:facilitador(this);" id="0">Todos</a></strong>
														</font>
													</td>
													<td align="center" width="10%" bgcolor="#90c7fc">
														<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
															<strong>Matrícula</strong>
														</font>
													</td>
													<td align="center" width="25%" bgcolor="#90c7fc">
														<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
															<strong>Nome</strong>
														</font>
													</td>
													<td align="center" width="10%" bgcolor="#90c7fc">
														<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
															<strong>Tipo de Consumo</strong>
														</font>
													</td>
													<td align="center" width="10%" bgcolor="#90c7fc">
														<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
															<strong>Consumo de Água Medido</strong>
														</font>
													</td>
													<td align="center" width="8%" bgcolor="#90c7fc">
														<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
															<strong>Consumo Rateado</strong>
														</font>
													</td>
													<td align="center" width="10%" bgcolor="#90c7fc">
														<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
															<strong>Consumo de Água Faturado</strong>
														</font>
													</td>
													<td align="center" width="10%" bgcolor="#90c7fc">
														<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
															<strong>Tipo Débito</strong>
														</font>
													</td>
													<td align="center" width="12%" bgcolor="#90c7fc">
														<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
															<strong>Valor Rateio</strong>
														</font>
													</td>
												</tr>
												<tr>
													<td width="100%" colspan="9">
													<div style="width: 100%; height: 100%; overflow: auto;">
													<table width="100%" align="left" bgcolor="#99CCFF">
														<!--corpo da segunda tabela-->
														<%
															cont = 0;
														%>
														<logic:notEmpty name="listaDebitosACobrarRateioImoveisVinculadosHelper">
															<logic:iterate name="listaDebitosACobrarRateioImoveisVinculadosHelper"	id="debitosACobrarRateioImoveisVinculadosHelper"
																	type="DebitosACobrarRateioImoveisVinculadosHelper">
																<%
																	cont = cont + 1;
																	if (cont % 2 == 0) {
																%>
																<tr bgcolor="#cbe5fe">
																<%
																	} else {
																%>
																<tr bgcolor="#FFFFFF">
																<%
																	}
																%>
																	<td width="5%">
																		<div align="center"><input type="checkbox" name="idDebitoACobrar"
																			value="<bean:write name="debitosACobrarRateioImoveisVinculadosHelper" property="idDebitoACobrar"/>" />
																		</div>
																	</td>
																	<td width="10%" bordercolor="#90c7fc" align="left">
																		<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
																			<bean:write name="debitosACobrarRateioImoveisVinculadosHelper" property="idImovel" />
																		</font>
																	</td>
																	<td width="25%" bordercolor="#90c7fc" align="left">
																		<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
																			<bean:write name="debitosACobrarRateioImoveisVinculadosHelper" property="nomeCliente" />
																		</font>
																	</td>
																	<td width="10%" bordercolor="#90c7fc" align="center">
																		<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
																		<bean:write name="debitosACobrarRateioImoveisVinculadosHelper" property="dsTipoConsumo" />
																		</font>
																	</td>
																	<td width="10%" bordercolor="#90c7fc" align="right">
																		<c:if test="${(debitosACobrarRateioImoveisVinculadosHelper.consumoMedidoAgua == 0)}">
																			&nbsp;
																		</c:if>
																		<c:if test="${(debitosACobrarRateioImoveisVinculadosHelper.consumoMedidoAgua != 0)}">
																			<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
																				<bean:write name="debitosACobrarRateioImoveisVinculadosHelper" property="consumoMedidoAgua" />
																			</font>
																		</c:if>
																	</td>
																	<td width="8%" bordercolor="#90c7fc" align="right">
																		<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
																			<bean:write name="debitosACobrarRateioImoveisVinculadosHelper" property="consumoRateado" />
																		</font>
																	</td>
																	<td width="10%" bordercolor="#90c7fc" align="right">
																		<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
																			<bean:write name="debitosACobrarRateioImoveisVinculadosHelper" property="consumoAguaFaturado" />
																		</font>
																	</td>
																	<td width="10%" bordercolor="#90c7fc" align="right"
																		title="${debitosACobrarRateioImoveisVinculadosHelper.dsDebitoTipo}">
																		<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
																			<bean:write name="debitosACobrarRateioImoveisVinculadosHelper" property="idDebitoTipo" />
																		</font>
																	</td>
																	<td width="12%" bordercolor="#90c7fc" align="right">
																		<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
																			<bean:write name="debitosACobrarRateioImoveisVinculadosHelper" property="valorRateado" formatKey="money.format"/>
																		</font>
																	</td>
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
								</td>
							</tr>
							<tr>
								<td class="style1">&nbsp;</td>
								<td colspan="3" class="style1">&nbsp;</td>
							</tr>
							<tr bordercolor="#90c7fc">
								<td colspan="4">
									<table width="100%">
										<tr>
											<td valign="top"><input name="button" type="button"
												class="bottonRightCol" value="Desfazer" onclick="desfazer();">&nbsp;<input type="button"
												name="ButtonCancelar" class="bottonRightCol" value="Cancelar"
												onClick="javascript:document.forms[0].target='';window.location.href='/gsan/telaPrincipal.do'"></td>
											<td>
											<div align="right"><input name="cancelar" type="button"
												class="bottonRightCol" value="Cancelar Débito"
												onclick="javascript:validarForm();"></div>
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
						<p>&nbsp;</p>
						<%@ include file="/jsp/util/rodape.jsp"%>
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html:html>

