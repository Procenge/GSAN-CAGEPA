<%@page import="gcom.util.Util"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page import="gcom.util.ConstantesSistema"%>

<%@ page import="gcom.faturamento.debito.ClienteImovelCondominioHelper"%>
<%@ page import="gcom.faturamento.debito.ClienteUsuarioImovelCondominioHelper"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
		<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
		<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
		<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
		<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
		<script language="JavaScript">
			function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
				var form = document.forms[0];
				
				if (tipoConsulta == 'registroAtendimento') {
				      form.idRegistroAtendimento.value = codigoRegistro;
				      form.nomeRegistroAtendimento.value = descricaoRegistro;
				      form.nomeRegistroAtendimento.style.color = "#000000";
				      form.action = 'exibirInserirDebitoACobrarRateioMacromedidoresAction.do?objetoConsulta=1'
				      form.submit();
				    }
			}
			
			function pesquisarRegistroAtendimento(){
				var form = document.forms[0];		
				abrirPopup('exibirPesquisarRegistroAtendimentoAction.do', 400, 800);
			}
			
			function validarForm(){
				var form = document.forms[0];
				var concluir = true;
				
				if(form.idRegistroAtendimento.value == '')   { 
					alert('Informe o Registro Atendimento');
					concluir = false;
				}
				
				if(form.valorDebitoImovel.value == '')   { 
					alert('Informe o Valor do Débito');
					concluir = false;
				}
				
				if(form.mesAnoReferenciaFaturamento.value == '')   { 
					alert('Informe o Mês/Ano Faturamento');
					concluir = false;
				}
							
				if(concluir==true){
					var retorno = CheckboxNaoVazioMensagemGenerico("Nenhum registro selecionado para inserção", document.InserirDebitoACobrarRateioMacromedidoresActionForm.idImoveisCondominio);
					if(retorno){
						form.submit();
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
			
			function desfazer(){
				window.location.href='/gsan/exibirInserirDebitoACobrarRateioMacromedidoresAction.do?menu=sim';
			}



		</script>
	</head>
	<body leftmargin="5" topmargin="5" onload="javascript:setarFoco('${requestScope.nomeCampo}');">
		<html:form action="/inserirDebitoACobrarRateioMacromedidoresAction.do" method="post"
				name="InserirDebitoACobrarRateioMacromedidoresActionForm"
				type="gcom.gui.faturamento.debito.InserirDebitoACobrarRateioMacromedidoresActionForm">

				<table width="100%" border="0">
				<tr>
					<td colspan="2">Para inserir os d&eacute;bitos a cobrar, informe os dados abaixo:</td>
				</tr>
				<tr>
					<td><strong>RA - Registro de Atendimento:</strong></td>
					<td colspan="2">
						<html:text maxlength="20" property="idRegistroAtendimento"
							tabindex="1" size="9"
							onkeypress="javascript:validaEnterComMensagem(event, 'exibirInserirDebitoACobrarRateioMacromedidoresAction.do?objetoConsulta=1', 
							'idRegistroAtendimento','RA - Registro de Atendimento'); return isCampoNumerico(event);"
						/>
						<a href="javascript:pesquisarRegistroAtendimento();"> 
							<img width="23" height="21" src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" />
						</a> 
						<logic:present name="idRegistroAtendimentoNaoEncontrado" scope="request">
							<html:text maxlength="30" property="nomeRegistroAtendimento"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000"
								size="40" />
						</logic:present> 
						<logic:notPresent name="idRegistroAtendimentoNaoEncontrado" scope="request">
							<html:text maxlength="30" property="nomeRegistroAtendimento"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="40" />
						</logic:notPresent> 
						<a href="javascript:desfazer();"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
						</a>
					</td>
				</tr>
				</table>
				<table>
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
							<div>
								<strong>Situação de Esgoto:</strong>
							</div>
						</td>

						<td><html:text property="descricaoLigacaoEsgotoSituacao"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="15" maxlength="15" /></td>
						
						<td height="10">
							<div >
								<strong>Situação de Água:</strong>
							</div>
						</td>
						
						<td>
							<html:text property="descricaoLigacaoAguaSituacao"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="15" maxlength="15" />
						</td>
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
												<%
													int cont = 0;
												%>
												<logic:notEmpty name="listaClienteImovelCondominio">
													<logic:iterate name="listaClienteImovelCondominio"	id="clienteImovelCondominioHelper" type="ClienteImovelCondominioHelper">
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
												<bean:write name="InserirDebitoACobrarRateioMacromedidoresActionForm" property="enderecoImovel" />
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td colspan="5">
									<table width="100%" border="1" cellpadding="0" cellspacing="0">
									<tr bordercolor="#79bbfd">
											<td colspan="5" align="center" bgcolor="#79bbfd"><strong>Imóveis vinculados ao Imóvel Condomínio </strong></td>
										</tr>
										<tr>
											<td>
											<table width="100%" bgcolor="#90c7fc">
												<tr bgcolor="#90c7fc">
													<td align="center" width="5%" bgcolor="#90c7fc" >
														<div class="style9"><font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
															<strong><a href="javascript:facilitador(this);" id="0">Todos</a></strong>
														</font></div>
													</td>
													<td align="center" width="10%" bgcolor="#90c7fc">
														<div class="style9"><font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
															<strong>Matrícula</strong>
														</font></div>
													</td>
													<td align="center" width="35%" bgcolor="#90c7fc">
														<div class="style9"><font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
															<strong>Nome do usuário</strong>
														</font></div>
													</td>
													<td align="center" width="25%" bgcolor="#90c7fc">
														<div class="style9"><font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
															<strong>Situação da Ligação de Água</strong>
														</font></div>
													</td>
													<td align="center" width="25%" bgcolor="#90c7fc">
														<div class="style9"><font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
															<strong>Situação da Ligação de Esgoto</strong>
														</font></div>
													</td>
												</tr>
												<tr>
													<td width="100%" colspan="9">
													<div style="width: 100%; height: 100%; overflow: auto;">
													<table width="100%" align="left" bgcolor="#99CCFF">
														<%
															cont = 0;
														%>
														<logic:notEmpty name="colecaoClienteUsuarioImovelCondominioHelper">
															<logic:iterate name="colecaoClienteUsuarioImovelCondominioHelper"	id="clienteUsuarioImovelCondominioHelper"
																	type="ClienteUsuarioImovelCondominioHelper">
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
																		<div align="center"><input type="checkbox" name="idImoveisCondominio"
																			value="<bean:write name="clienteUsuarioImovelCondominioHelper" property="idImovel"/>" />
																		</div>
																	</td>
																	<td width="10%" bordercolor="#90c7fc" align="center">
																		<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
																			<bean:write name="clienteUsuarioImovelCondominioHelper" property="idImovel" /> <!-- MATRÍCULA -->
																		</font>
																	</td>
																	<td width="35%" bordercolor="#90c7fc" align="center">
																		<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
																			<bean:write name="clienteUsuarioImovelCondominioHelper" property="nomeCliente" /> <!-- MATRÍCULA --> <!-- NOME -->
																		</font>
																	</td>
																	<td width="25%" bordercolor="#90c7fc" align="center">
																		<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
																			<bean:write name="clienteUsuarioImovelCondominioHelper" property="descricaoLigacaoAguaSituacao" /> <!-- DESCRIÇÃO AGUA -->
																		</font>
																	</td>
																	<td width="25%" bordercolor="#90c7fc" align="center">
																		<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
																			<bean:write name="clienteUsuarioImovelCondominioHelper" property="descricaoLigacaoEsgotoSituacao" /> <!-- DESCRIÇÃO ESGOTO -->
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
										</tr>
								</table>
							</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td  width="200"><strong>Tipo de débitos de rateio :<strong></strong></strong></td>
								<td>
									<html:text property="tipoDebitosRateio"
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000"
												size="40" />
								</td>
							</tr>							
							<tr>
								<td  width="200"><strong>Número de prestações:<strong></strong></strong></td>
								<td>
									<html:text property="numeroPrestacoes"
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000"
												size="5" />
								</td>
							</tr>
							<tr>
								<td  width="200"><strong>Tipo de Ligação:<strong></strong></strong></td>
								<td align="right">
									<div align="left">
										<span class="style1">
											<html:select property="idTipoLigacao" style="width: 200px; " tabindex="11" disabled="true"size="1">
												<html:options collection="colecaoLigacaoTipo" labelProperty="descricao" property="id"/>
											</html:select>
										</span>
									</div>
								</td>
							</tr>
							<tr>
								<td  width="200"><strong>Valor do débito por imóvel:<strong><font color="#FF0000">*</font></strong></strong></td>
								<td>
									<html:text maxlength="9" property="valorDebitoImovel" tabindex="1" size="15" onkeyup="formataValorMonetario(this, 11);" onkeypress="return isCampoNumerico(event);"/>
								</td>
							</tr>
							<tr>
								<td  width="200"><strong>Mês/Ano faturamento:<strong><font color="#FF0000">*</font></strong></strong></td>
								<td colspan="3" align="right">
									<div align="left">
										<span class="style1"> 
											<html:text property="mesAnoReferenciaFaturamento" maxlength="7" size="7" onkeyup="mascaraAnoMes(this, event)" onkeypress="return isCampoNumerico(event);"/> mm/aaaa
										</span>
									</div>
								</td>
							</tr>
							
							<tr>
								<td>&nbsp;</td>
							</tr>
				</table>
				<table width="100%">
					<tr>
						<td valign="top">
							<input name="button" type="button"
							class="bottonRightCol" 
							value="Desfazer" 
							onclick="desfazer();">
							&nbsp;
							<input type="button" name="ButtonCancelar" 
							class="bottonRightCol" 
							value="Cancelar"
							onClick="javascript:document.forms[0].target='';window.location.href='/gsan/telaPrincipal.do'">
						</td>
						<td>
							<div align="right">
								<input name="inserir" type="button"
								class="bottonRightCol" value="Inserir Débito"
								onclick="javascript:validarForm();">
							</div>
						</td>
					</tr>
				</table>	
		</html:form>
	</body>
</html:html>

