<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="gcom.cadastro.cliente.ClienteImovel"%>
<%@ page import="gcom.cadastro.imovel.ImovelSubcategoria"%>
<%@ page import="gcom.gui.cobranca.CobrancaAdministrativaHelper"%>
<%@ page import="gcom.gui.cobranca.CobrancaAdministrativaContaHelper"%>
<%@ page import="gcom.gui.cobranca.CobrancaAdministrativaGuiaHelper"%>
<%@ page import="gcom.util.ConstantesSistema"%>
<%@ page import="gcom.util.Util"%>
<%@ page import="java.math.BigDecimal"%>

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
	formName="AtualizarImovelCobrancaAdministrativaActionForm" />

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

<html:form action="/exibirAtualizarImovelCobrancaAdministrativaAction"
	name="AtualizarImovelCobrancaAdministrativaActionForm"
	type="gcom.gui.cobranca.cobrancaadministrativa.AtualizarImovelCobrancaAdministrativaActionForm"
	method="post">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

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
					<td class="parabg">Atualizar Imóvel Cobrança Administrativa</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>

			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td width="10%"><strong>Imóvel:</strong></td>
					<td width="90%"><html:text property="idImovel" readonly="true"
						style="background-color:#EFEFEF; border:0;" size="9" /></td>
				</tr>
				
				<!-- 1.2 Dados do Imóvel -->
				<tr>
					<td colspan="4">
					<div id="layerHideDadosImovel" style="display: block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('DadosImovel',true);" /><b>Dados
							do Imóvel</b></a></span></td>
						</tr>
					</table>
					</div>

					<div id="layerShowDadosImovel" style="display: none">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('DadosImovel',false);" /><b>Dados
							do Imóvel</b></a></span></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td>
							<table width="100%">
								<tr>
									<td width="25%"><strong>Inscrição:</strong></td>
									<td><html:text property="inscricaoFormatadaImovel"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="20" /></td>
									<td><strong>Perfil do Imóvel:</strong></td>
									<td><html:text property="descricaoImovelPerfil"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="20" /></td>
								</tr>
								<tr>
									<td><strong>Situação de Água:</strong></td>
									<td><html:text property="descricaoLigacaoAguaSituacao"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="20" /></td>
									<td><strong>Situação de Esgoto:</strong></td>
									<td><html:text property="descricaoLigacaoEsgotoSituacao"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="20" /></td>
								</tr>
								<!-- 1.2.5 Endereço do Imóvel -->
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
											<div class="style9">${AtualizarImovelCobrancaAdministrativaActionForm.enderecoImovel}
											&nbsp;</div>
											</td>
										</tr>
									</table>
									</td>
								</tr>
								<!-- 1.2.6 Subcategorias -->
								<%
									int cont = 0;
									int quantidadeTotalEconomias = 0;
								%>
								<tr>
									<td colspan="4">
									<table width="100%" align="center" bgcolor="#90c7fc" border="0">
										<tr bordercolor="#79bbfd">
											<td colspan="4" align="center" bgcolor="#79bbfd"><strong>Lista
											das subcategorias e quantidades de economias do imóvel</strong></td>
										</tr>
										<tr bordercolor="#000000">
											<td width="19%" bgcolor="#90c7fc" align="center">
											<div class="style9"><font color="#000000"
												style="font-size: 9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <strong>Categoria</strong>
											</font></div>
											</td>
											<td width="56%" bgcolor="#90c7fc" align="center">
											<div class="style9"><font color="#000000"
												style="font-size: 9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <strong>Subcategoria</strong></font>
											</div>
											</td>
											<td width="25%" bgcolor="#90c7fc" align="center">
											<div class="style9"><font color="#000000"
												style="font-size: 9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <strong>Economias</strong>
											</font></div>
											</td>
										</tr>
										<tr>
											<td width="100%" colspan="3">
											<div style="width: 100%; height: 100%; overflow: auto;">
											<table width="100%" align="left" bgcolor="#99CCFF">
												<!--corpo da segunda tabela-->
												<%
													cont = 0;
												%>
												<logic:notEmpty name="colecaoImovelSubcategorias">
													<logic:iterate name="colecaoImovelSubcategorias"
														id="imovelSubcategoria" type="ImovelSubcategoria">
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
																<td width="19%" align="left">
																<div align="center"><font color="#000000"
																	style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif"> <logic:present
																	name="imovelSubcategoria" property="comp_id">

																	<bean:define name="imovelSubcategoria"
																		property="comp_id" id="comp_id" />
																	<logic:present name="comp_id" property="subcategoria">


																		<bean:define name="comp_id" property="subcategoria"
																			id="subcategoria" />

																		<logic:present name="subcategoria"
																			property="categoria">

																			<bean:define name="subcategoria" property="categoria"
																				id="categoria" />
																			<bean:write name="categoria" property="descricao" />
																		</logic:present>
																	</logic:present>
																</logic:present> </font></div>
																</td>
																<td width="56%" align="left">
																<div align="center"><font color="#000000"
																	style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif"> <logic:present
																	name="imovelSubcategoria" property="comp_id">

																	<bean:define name="imovelSubcategoria"
																		property="comp_id" id="comp_id" />
																	<logic:present name="comp_id" property="subcategoria">
																		<bean:define name="comp_id" property="subcategoria"
																			id="subcategoria" />
																		<bean:write name="subcategoria" property="descricao" />
																	</logic:present>
																</logic:present> </font></div>
																</td>
																<td width="25%" align="right">
																<div align="right"><font color="#000000"
																	style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif"> <logic:present
																	name="imovelSubcategoria"
																	property="quantidadeEconomias">
																	<bean:write name="imovelSubcategoria"
																		property="quantidadeEconomias" />
																</logic:present> </font></div>
																</td>
																<%
																	quantidadeTotalEconomias = ((ImovelSubcategoria) imovelSubcategoria)
																							.getQuantidadeEconomias()
																							+ quantidadeTotalEconomias;
																%>
															</tr>
													</logic:iterate>
													<tr bgcolor="#FFFFFF">
														<td width="75%" colspan="2" bgcolor="#90c7fc"
															align="center">
														<div class="style9" align="center"><font
															color="#000000" style="font-size: 9px"
															face="Verdana, Arial, Helvetica, sans-serif"> <strong>Total
														de Economias</strong></font></div>
														</td>
														<td width="25%" align="right">
														<div align="right"><font color="#000000"
															style="font-size: 9px"
															face="Verdana, Arial, Helvetica, sans-serif"> <%=quantidadeTotalEconomias%></font></div>
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
								<!-- 1.2.7 Clientes -->
								<tr>
									<td colspan="4">
									<table width="100%" align="center" bgcolor="#90c7fc" border="0">
										<tr bordercolor="#79bbfd">
											<td colspan="4" align="center" bgcolor="#79bbfd"><strong>Lista
											dos clientes associados ao imóvel</strong></td>
										</tr>
										<tr bordercolor="#000000">
											<td width="40%" bgcolor="#90c7fc" align="center">
											<div class="style9"><font color="#000000"
												style="font-size: 9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <strong>Nome
											do Cliente</strong> </font></div>
											</td>
											<td width="28%" bgcolor="#90c7fc" align="center">
											<div class="style9"><font color="#000000"
												style="font-size: 9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <strong>Tipo
											de Rela&ccedil;&atilde;o</strong> </font></div>
											</td>
											<td width="32%" bgcolor="#90c7fc" align="center"><font
												color="#000000" style="font-size: 9px"
												face="Verdana, Arial, Helvetica, sans-serif"><strong>CPF/CNPJ</strong>
											</font></td>
										</tr>
										<tr>
											<td width="100%" colspan="4">
											<div style="width: 100%; height: 100%; overflow: auto;">
											<table width="100%" align="left" bgcolor="#99CCFF">
												<!--corpo da segunda tabela-->
												<%
													cont = 0;
												%>
												<logic:notEmpty name="colecaoClientesImovel">
													<logic:iterate name="colecaoClientesImovel"
														id="imovelCliente" type="ClienteImovel">
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
																<td width="40%" bordercolor="#90c7fc" align="left">
																<div class="style9"><font color="#000000"
																	style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif"> <logic:present
																	name="imovelCliente" property="cliente">
																	<bean:write name="imovelCliente"
																		property="cliente.nome" />
																</logic:present> </font></div>
																</td>
																<td width="28%" align="left">
																<div class="style9"><font color="#000000"
																	style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif"> <logic:present
																	name="imovelCliente" property="clienteRelacaoTipo">
																	<bean:write name="imovelCliente"
																		property="clienteRelacaoTipo.descricao" />
																</logic:present> </font></div>
																</td>
																<td width="32%" align="right"><font color="#000000"
																	style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
																	name="imovelCliente" property="cliente.cpf">
																	<bean:write name="imovelCliente"
																		property="cliente.cpfFormatado" />
																</logic:notEmpty> <logic:notEmpty name="imovelCliente"
																	property="cliente.cnpj">
																	<bean:write name="imovelCliente"
																		property="cliente.cnpjFormatado" />
																</logic:notEmpty> </font></td>
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
					</table>
					</div>
					</td>
				</tr>
				<!-- 1.3 Dados da Cobrança Administrativa -->
				<tr>
					<td colspan="4">
					<div id="layerHideDadosCobrancaAdministrativa" style="display: none">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('DadosCobrancaAdministrativa',true);" /><b>Dados
							da Cobrança Administrativa</b></a></span></td>
						</tr>
					</table>
					</div>

					<div id="layerShowDadosCobrancaAdministrativa" style="display: block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('DadosCobrancaAdministrativa',false);" /><b>Dados
							da Cobrança Administrativa</b></a></span></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td>
							<table width="100%">
								<tr>
									<td width="25%"><strong>Empresa:</strong></td>
									<td colspan="3"><html:text property="empresa"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="50" />
									</td>
								</tr>
								<tr>
									<td width="25%"><strong>Data da Implantação:</strong></td>
									<td colspan="3"><html:text property="dataImplantacao"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="10" />
									</td>
								</tr>
								<tr>
									<td width="25%"><strong>Data da Retirada:</strong></td>
									<td><html:text property="dataRetirada"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="10" /></td>
									<td width="25%"><strong>Motivo da Retirada:</strong></td>
									<td><html:text property="motivoRetirada"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="30" /></td>
								</tr>
								<tr>
									<td width="25%"><strong>Data da Adimplência:</strong></td>
									<td><html:text property="dataAdimplencia"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="10" /></td>
									<td width="25%"><strong>Motivo da Adimplência:</strong></td>
									<td><html:text property="motivoAdimplencia"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="30" /></td>
								</tr>
								<tr>
									<td width="25%"><strong>Qtde. Itens Marcados:</strong></td>
									<td><html:text property="quantidadeDebitos"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="10" /></td>
									<td width="25%"><strong>Valor Itens Marcados:</strong></td>
									<td><html:text property="valorDebitos"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="15" /></td>
								</tr>

								<tr>
									<td align="right">
									<div align="left">&nbsp;</div>
									</td>
								</tr>

								<tr>
									<td colspan="4">
									<table width="100%" align="center" bgcolor="#90c7fc" border="0">
										<tr bordercolor="#79bbfd">
											<td colspan="4" align="center" bgcolor="#79bbfd"><strong>Situação dos Itens Marcados</strong></td>
										</tr>
										<tr bordercolor="#000000">
											<td width="40%" bgcolor="#90c7fc" align="center">
											<div class="style9"><font color="#000000" style="font-size: 9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <strong>Situação</strong> </font></div>
											</td>
											<td width="28%" bgcolor="#90c7fc" align="center">
											<div class="style9"><font color="#000000" style="font-size: 9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <strong>Quantidade</strong> </font></div>
											</td>
											<td width="32%" bgcolor="#90c7fc" align="center"><font
												color="#000000" style="font-size: 9px"
												face="Verdana, Arial, Helvetica, sans-serif"><strong>Valor</strong>
											</font></td>
										</tr>
										<tr>
											<td width="100%" colspan="4">
											<div style="width: 100%; height: 100%; overflow: auto;">
											<table width="100%" align="left" bgcolor="#99CCFF">
												<!--corpo da segunda tabela-->
												<%
													cont = 0;
												%>
												<logic:notEmpty name="colecaoItemTotalizado">
													<logic:iterate name="colecaoItemTotalizado"	id="itemTotalizado">
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
																<td width="40%" bordercolor="#90c7fc" align="left">
																	<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
																		<%=((Object[]) itemTotalizado)[1]%>
																	</font>
																</td>
																<td width="28%" bordercolor="#90c7fc" align="right">
																	<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
																		<%=((Object[]) itemTotalizado)[2]%>
																	</font>
																</td>
																<td width="32%" bordercolor="#90c7fc" align="right">
																	<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
																		<%=Util.formatarMoedaReal((BigDecimal)((Object[]) itemTotalizado)[3])%>
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
									<td align="right">
									<div align="left">&nbsp;</div>
									</td>
								</tr>
								<tr>
									<td width="25%"><strong>Qtde. Itens Remuneráveis:</strong></td>
									<td><html:text property="quantidadeItensRemunerados"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="10" /></td>
									<td width="25%"><strong>Valor Itens Remuneráveis:</strong></td>
									<td><html:text property="valorItensRemunerados"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="15" /></td>
								</tr>
								<tr>
									<td align="right">
									<div align="left">&nbsp;</div>
									</td>
								</tr>
								<tr>
									<td colspan="4">
									<table width="100%" align="center" bgcolor="#90c7fc" border="0">
										<tr bordercolor="#79bbfd">
											<td colspan="4" align="center" bgcolor="#79bbfd"><strong>Situação dos Itens Remuneráveis</strong></td>
										</tr>
										<tr bordercolor="#000000">
											<td width="30%" bgcolor="#90c7fc" align="center">
											<div class="style9"><font color="#000000" style="font-size: 9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <strong>Situação</strong> </font></div>
											</td>
											<td width="20%" bgcolor="#90c7fc" align="center">
											<div class="style9"><font color="#000000" style="font-size: 9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <strong>Quantidade</strong> </font></div>
											</td>
											<td width="20%" bgcolor="#90c7fc" align="center"><font
												color="#000000" style="font-size: 9px"
												face="Verdana, Arial, Helvetica, sans-serif"><strong>Valor Base da Remuneração</strong>
											</font></td>

											<logic:equal name="indRemunCobAdmPorComando" value="<%="" + ConstantesSistema.NAO%>" scope="session">
												<td width="20%" bgcolor="#90c7fc" align="center"><font
													color="#000000" style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"><strong>Valor da Remuneração Realizada</strong>
												</font></td>
											</logic:equal>
											<logic:notEqual name="indRemunCobAdmPorComando" value="<%="" + ConstantesSistema.NAO%>" scope="session">
												<td width="20%" bgcolor="#90c7fc" align="center">&nbsp;</td>
											</logic:notEqual>
										</tr>
										<tr>
											<td width="100%" colspan="4">
											<div style="width: 100%; height: 100%; overflow: auto;">
											<table width="100%" align="left" bgcolor="#99CCFF">
												<!--corpo da segunda tabela-->
												<%
													cont = 0;
												%>
												<logic:notEmpty name="colecaoItensRemunerados">
													<logic:iterate name="colecaoItensRemunerados" id="itemRemunerado">
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
																		<%=((Object[]) itemRemunerado)[0]%>
																	</font>
																</td>
																<td width="20%" bordercolor="#90c7fc" align="right">
																	<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
																		<%=((Object[]) itemRemunerado)[1]%>
																	</font>
																</td>
																<td width="20%" bordercolor="#90c7fc" align="right">
																	<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
																		<%=((Object[]) itemRemunerado)[2]%>
																	</font>
																</td>

                                                                <logic:equal name="indRemunCobAdmPorComando" value="<%="" + ConstantesSistema.NAO%>" scope="session">
																	<td width="20%" bordercolor="#90c7fc" align="right">
																		<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
																			<%=((Object[]) itemRemunerado)[3]%>
																		</font>
																	</td>
																</logic:equal>
                                                                <logic:notEqual name="indRemunCobAdmPorComando" value="<%="" + ConstantesSistema.NAO%>" scope="session">
																	<td width="20%" bordercolor="#90c7fc" align="right">
																		<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
																			&nbsp;
																		</font>
																	</td>
																</logic:notEqual>
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
					</table>
					</div>
					</td>
				</tr>

				<tr>
					<td align="right">
					<div align="left">&nbsp;</div>
					</td>
				</tr>

				<!-- Exibir Dados dos Itens do Débito do Imóvel -->
				<tr>
					<td colspan="4">
					<div id="layerHideDadosDocumento" style="display: block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('DadosDocumento',true);" ><b>Dados dos Débitos Vinculados à Cobrança Administrativa</b></a></span></td>
						</tr>
					</table>
					</div>

					<div id="layerShowDadosDocumento" style="display: none">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('DadosDocumento',false);" ><b>Dados dos Débitos Vinculados à Cobrança Administrativa</b></a></span></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td>
							<table width="100%">
								<logic:present name="colecaoCobrancaAdministrativaHelper">
									<tr>
										<td width="20%"><strong>Tipo do Documento:</strong></td>
										<td colspan="3"><html:text
											property="descricaoDocumentoTipoConta"
											readonly="true"
											style="background-color:#EFEFEF; border:0;" size="45" /></td>
									</tr>

									<tr>
										<td colspan="4">
										<table width="100%" align="center" bgcolor="#90c7fc"
											border="0">
											<tr bordercolor="#000000">
												<td width="16%" bgcolor="#90c7fc" align="center">
												<div class="style9"><font color="#000000"
													style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <strong>Referência da Conta</strong>
												</font></div>
												</td>
												<td width="16%" bgcolor="#90c7fc" align="center">
												<div class="style9"><font color="#000000"
													style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <strong>Data Vencimento da Conta</strong>
												</font></div>
												</td>
												<td width="16%" bgcolor="#90c7fc" align="center"><font
													color="#000000" style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"><strong>Valor do Débito</strong> </font></td>
												<td width="16%" bgcolor="#90c7fc" align="center"><font
													color="#000000" style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"><strong>Cliente Usuário</strong> </font></td>
												<td width="20%" bgcolor="#90c7fc" align="center"><font
													color="#000000" style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"><strong>Situação do Item</strong> </font></td>
												<td width="16%" bgcolor="#90c7fc" align="center"><font
													color="#000000" style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"><strong>Data da Situação</strong> </font></td>
											</tr>
											<tr>
												<td width="100%" colspan="9">
												<div style="width: 100%; height: 100%; overflow: auto;">
												<table width="100%" align="left" bgcolor="#99CCFF">
													<!--corpo da segunda tabela-->
													<%
														cont = 0;
													%>
													<logic:notEmpty name="colecaoCobrancaAdministrativaHelper">
														<logic:iterate name="colecaoCobrancaAdministrativaHelper"
															id="cobrancaAdministrativaConta"
															type="CobrancaAdministrativaHelper">
															<%
																cont = cont + 1;
																if (cont % 2 == 0) {
															%>
															    <tr bgcolor="#cbe5fe">
															<%} else {%>
																<tr bgcolor="#FFFFFF">
															<%}%>
																	<td width="16%" align="center">
																	<div class="style9"><font color="#000000"
																		style="font-size: 9px"
																		face="Verdana, Arial, Helvetica, sans-serif">
																	<%=Util.formatarAnoMesParaMesAno(cobrancaAdministrativaConta.getReferenciaConta())%> </font></div>
																	</td>
																	<td width="16%" bordercolor="#90c7fc"
																		align="center">
																	<div class="style9"><font color="#000000"
																		style="font-size: 9px"
																		face="Verdana, Arial, Helvetica, sans-serif">
																	<bean:write name="cobrancaAdministrativaConta"
																		property="dataVencimentoConta"
																		formatKey="date.format" /> </font></div>
																	</td>
																	<td width="16%" align="right"><font
																		color="#000000" style="font-size: 9px"
																		face="Verdana, Arial, Helvetica, sans-serif"
																		 title="valor Água = R$ <%=Util.formatarMoedaReal(cobrancaAdministrativaConta.getValorAgua())%> 
																		 		valor Esgoto =R$ <%=Util.formatarMoedaReal(cobrancaAdministrativaConta.getValorEsgoto())%>
																		 		valor Débitos =R$ <%=Util.formatarMoedaReal(cobrancaAdministrativaConta.getValorDebitos())%>
																		 		valor Créditos =R$ <%=Util.formatarMoedaReal(cobrancaAdministrativaConta.getValorCreditos())%>
																		 		valor Impostos =R$ <%=Util.formatarMoedaReal(cobrancaAdministrativaConta.getValorImpostos())%>">
																	<bean:write name="cobrancaAdministrativaConta"
																		property="valorDebito" formatKey="money.format" />
																	</font></td>
																	<td width="16%" align="right" title="${cobrancaAdministrativaConta.nomeClienteUsuario}">
																	<div class="style9"><font color="#000000"
																		style="font-size: 9px"
																		face="Verdana, Arial, Helvetica, sans-serif">
																	<bean:write name="cobrancaAdministrativaConta"
																		property="clienteUsuario" /> </font></div>
																	</td>
																	<td width="20%" bordercolor="#90c7fc"
																		align="center">
																	<div class="style9"><font color="#000000"
																		style="font-size: 9px"
																		face="Verdana, Arial, Helvetica, sans-serif">
																	<bean:write name="cobrancaAdministrativaConta"
																		property="situacaoItem" /> </font></div>
																	</td>
																	<td width="16%" bordercolor="#90c7fc"
																		align="center">
																	<div class="style9"><font color="#000000"
																		style="font-size: 9px"
																		face="Verdana, Arial, Helvetica, sans-serif">
																	<bean:write name="cobrancaAdministrativaConta"
																		property="dataSituacao"
																		formatKey="date.format" /> </font></div>
																	</td>
																</tr>
														</logic:iterate>

														<!-- Totalizador -->
														<%if (cont % 2 == 0) {%>
														<tr bgcolor="#cbe5fe">
														<%} else {%>
														<tr bgcolor="#FFFFFF">
														<%}%>
																<td colspan="2" bgcolor="#90c7fc" align="center">
																<div class="style9" align="center"><font
																	color="#000000" style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif">
																<strong>Total</strong> </font></div>
																</td>
																<td align="right">
																<div align="right"><font color="#000000"
																	style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif">
																<%=session.getAttribute("valorTotalDebitoConta")%> </font></div>
																</td>
																<td colspan="3" bgcolor="#90c7fc" align="left">
																<div align="left"><font color="#000000"
																	style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif">
																</font></div>
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

								<!-- Guia de Pagamento -->
								<logic:present name="colecaoCobrancaAdministrativaGuiaHelper">
									<tr>
										<td width="20%"><strong>Tipo do Documento:</strong></td>
										<td colspan="3"><html:text
											property="descricaoDocumentoTipoGuia"
											readonly="true"
											style="background-color:#EFEFEF; border:0;" size="45" /></td>
									</tr>

									<tr>
										<td colspan="7">
										<table width="100%" align="center" bgcolor="#90c7fc"
											border="0">
											<tr bordercolor="#000000">
											
											   <td width="14%" bgcolor="#90c7fc" align="center">							
												<div class="style9"><font color="#000000"
													style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <strong>Guia / Prestação</strong> </font></div>
												</td>
												<td width="14%" bgcolor="#90c7fc" align="center">
												<div class="style9"><font color="#000000"
													style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <strong>Dt. Emissão</strong> </font></div>
												</td>
												<td width="18%" bgcolor="#90c7fc" align="center">
												<div class="style9"><font color="#000000"
													style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <strong>Tipo
												de Débito</strong> </font></div>
												</td>
												<td width="10%" bgcolor="#90c7fc" align="center"><font
													color="#000000" style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"><strong>Valor do Débito</strong>
												</font></td>
												<td width="14%" bgcolor="#90c7fc" align="center"><font
													color="#000000" style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"><strong>Cliente Usuário</strong> </font></td>
												
												<td width="16%" bgcolor="#90c7fc" align="center"><font
													color="#000000" style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"><strong>Situação do Item</strong> </font></td>
												<td width="14%" bgcolor="#90c7fc" align="center"><font
													color="#000000" style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"><strong>Data da Situação</strong> </font></td>
											</tr>
											<tr>
												<td width="100%" colspan="7">
												<div style="width: 100%; height: 100%; overflow: auto;">
												<table width="100%" align="left" bgcolor="#99CCFF">
													<!--corpo da segunda tabela-->
													<%
														cont = 0;
													%>
													<logic:notEmpty name="colecaoCobrancaAdministrativaGuiaHelper">
														<logic:iterate name="colecaoCobrancaAdministrativaGuiaHelper"
															id="cobrancaAdministrativaGuia"
															type="CobrancaAdministrativaHelper">
															<%
																cont = cont + 1;
																if (cont % 2 == 0) {
															%>
															<tr bgcolor="#cbe5fe">
															<% } else { %>
															<tr bgcolor="#FFFFFF">
															<% } %>
																	<td width="14%" bordercolor="#90c7fc" align="center">
																	<div class="style9"><font color="#000000"
																		style="font-size: 9px"
																		face="Verdana, Arial, Helvetica, sans-serif">
																	<bean:write name="cobrancaAdministrativaGuia"
																		property="idGuiaPamento" />/<bean:write name="cobrancaAdministrativaGuia"
																		property="prestacao" />
																	</font></div>
																	</td>
																	<td width="14%" bordercolor="#90c7fc" align="center">
																	<div class="style9"><font color="#000000"
																		style="font-size: 9px"
																		face="Verdana, Arial, Helvetica, sans-serif">
																	<bean:write name="cobrancaAdministrativaGuia"
																		property="dataEmissao" formatKey="date.format" />
																	</font></div>
																	</td>
																	<td width="18%" align="left">
																	<div class="style9"><font color="#000000"
																		style="font-size: 9px"
																		face="Verdana, Arial, Helvetica, sans-serif">
																	<bean:write name="cobrancaAdministrativaGuia"
																		property="tipoDebito" /> </font></div>
																	</td>
																	<td width="10%" align="right"><font
																		color="#000000" style="font-size: 9px"
																		face="Verdana, Arial, Helvetica, sans-serif">
																	<bean:write name="cobrancaAdministrativaGuia"
																		property="valorDebito"
																		formatKey="money.format" /> </font></td>

																	<logic:empty name="cobrancaAdministrativaGuia"
																		property="clienteUsuario">
																	<td width="14%" align="center"><font
																		color="#000000" style="font-size: 9px"
																		face="Verdana, Arial, Helvetica, sans-serif">
																		&nbsp;
																	</font></td>
																	</logic:empty>

																	<logic:notEmpty name="cobrancaAdministrativaGuia"
																		property="clienteUsuario">
																	<td width="14%" align="center" ><font
																		color="#000000" style="font-size: 9px"
																		face="Verdana, Arial, Helvetica, sans-serif">
																		<bean:write name="cobrancaAdministrativaGuia"
																			property="clienteUsuario" />
																	</font></td>
																	</logic:notEmpty>

																	<td width="16%" bordercolor="#90c7fc"
																		align="center">
																	<div class="style9"><font color="#000000"
																		style="font-size: 9px"
																		face="Verdana, Arial, Helvetica, sans-serif">
																	<bean:write name="cobrancaAdministrativaGuia"
																		property="situacaoItem" /> </font></div>
																	</td>
																	<td width="14%" bordercolor="#90c7fc"
																		align="center">
																	<div class="style9"><font color="#000000"
																		style="font-size: 9px"
																		face="Verdana, Arial, Helvetica, sans-serif">
																	<bean:write name="cobrancaAdministrativaGuia"
																		property="dataSituacao"
																		formatKey="date.format" /> </font></div>
																	</td>
																</tr>
														</logic:iterate>
														
														<!-- Totalizador -->
														<%if (cont % 2 == 0) {%>
														<tr bgcolor="#cbe5fe">
														<%} else {%>
														<tr bgcolor="#FFFFFF">
														<%}%>
																<td colspan="3" bgcolor="#90c7fc" align="center">
																<div class="style9" align="center"><font
																	color="#000000" style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif">
																<strong>Total</strong> </font></div>
																</td>
																<td align="right">
																<div align="right"><font color="#000000"
																	style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif">
																<%=session.getAttribute("valorTotalDebitoGuia")%> </font></div>
																</td>
																<td colspan="3" bgcolor="#90c7fc" align="left">
																<div align="left"><font color="#000000"
																	style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif">
																</font></div>
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
								
								<!-- Debito a Cobrar -->
								<logic:present name="colecaoCobrancaAdministrativaDebitoHelper">
									<tr>
										<td width="20%"><strong>Tipo do Documento:</strong></td>
										<td colspan="8"><html:text
											property="descricaoDocumentoTipoDebito"
											readonly="true"
											style="background-color:#EFEFEF; border:0;" size="45" /></td>
									</tr>

									<tr>
										<td colspan="7">
										<table width="100%" border="0" align="center" bgcolor="#90c7fc"
											border="0">
											<tr bordercolor="#000000">
											
											   <td width="12%" bgcolor="#90c7fc" align="center">							
												<div class="style9"><font color="#000000"
													style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <strong>Referência do Débito</strong> </font></div>
												</td>
												<td width="14%" bgcolor="#90c7fc" align="center">
												<div class="style9"><font color="#000000"
													style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <strong>Tipo do Débito</strong> </font></div>
												</td>
												<td width="12%" bgcolor="#90c7fc" align="center">
												<div class="style9"><font color="#000000"
													style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <strong>Parcela</strong> </font></div>
												</td>
												<td width="10%" bgcolor="#90c7fc" align="center"><font
													color="#000000" style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"><strong>Débito</strong>
												</font></td>
												<td width="15%" bgcolor="#90c7fc" align="center"><font
													color="#000000" style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"><strong>Vl Rest a Ser Cob</strong>
												</font></td>
												<td width="12%" bgcolor="#90c7fc" align="center"><font
													color="#000000" style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"><strong>Cliente Usuário</strong> </font></td>
												
												<td width="12%" bgcolor="#90c7fc" align="center"><font
													color="#000000" style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"><strong>Situação do Item</strong> </font></td>
												<td width="10%" bgcolor="#90c7fc" align="center"><font
													color="#000000" style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"><strong>Data da Situação</strong> </font></td>
											</tr>
											<tr>
												<td width="100%" colspan="8">
												<div style="width: 100%; height: 100%; overflow: auto;">
												<table width="100%" align="left" bgcolor="#99CCFF">
													<!--corpo da segunda tabela-->
													<%
														cont = 0;
													%>
													<logic:notEmpty name="colecaoCobrancaAdministrativaDebitoHelper">
														<logic:iterate name="colecaoCobrancaAdministrativaDebitoHelper"
															id="cobrancaAdministrativaDebito"
															type="CobrancaAdministrativaHelper">
															<%
																cont = cont + 1;
																if (cont % 2 == 0) {
															%>
															<tr bgcolor="#cbe5fe">
															<% } else { %>
															<tr bgcolor="#FFFFFF">
															<% } %>
																	<td width="14%" bordercolor="#90c7fc" align="center">
																	<div class="style9"><font color="#000000"
																		style="font-size: 9px"
																		face="Verdana, Arial, Helvetica, sans-serif">
																			<logic:notEmpty name="cobrancaAdministrativaDebito" property="referenciaDebito">
    																			<%=Util.formatarAnoMesParaMesAno(cobrancaAdministrativaDebito.getReferenciaDebito())%>
    																		</logic:notEmpty>
    																		<logic:empty name="cobrancaAdministrativaDebito" property="referenciaDebito">
    																			&nbsp;
    																		</logic:empty>
																	</font></div>
																	</td>
																	<td width="14%" bordercolor="#90c7fc" align="center">
																	<div class="style9"><font color="#000000"
																		style="font-size: 9px"
																		face="Verdana, Arial, Helvetica, sans-serif">
																	<bean:write name="cobrancaAdministrativaDebito"
																		property="tipoDebito" />
																	</font></div>
																	</td>
																	<td width="14%" align="left">
																	<div class="style9"><font color="#000000"
																		style="font-size: 9px"
																		face="Verdana, Arial, Helvetica, sans-serif">
																	<bean:write name="cobrancaAdministrativaDebito"
																		property="prestacaoCobrada" />/<bean:write name="cobrancaAdministrativaDebito"
																		property="prestacaoDebito" /> </font></div>
																	</td>
																	<td width="15%" align="right"><font
																		color="#000000" style="font-size: 9px"
																		face="Verdana, Arial, Helvetica, sans-serif">
																	<bean:write name="cobrancaAdministrativaDebito"
																		property="valorDebito"
																		formatKey="money.format" /> </font></td>
																		
																	<td width="15%" align="right"><font
																		color="#000000" style="font-size: 9px"
																		face="Verdana, Arial, Helvetica, sans-serif">
																	<bean:write name="cobrancaAdministrativaDebito"
																		property="valorRestanteASerCobrado"
																		formatKey="money.format" /> </font></td>

																	<logic:empty name="cobrancaAdministrativaDebito"
																		property="clienteUsuario">
																	<td width="15%" align="center"><font
																		color="#000000" style="font-size: 9px"
																		face="Verdana, Arial, Helvetica, sans-serif">
																		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																	</font></td>
																	</logic:empty>

																	<logic:notEmpty name="cobrancaAdministrativaDebito"
																		property="clienteUsuario">
																	<td width="15%" align="center"><font
																		color="#000000" style="font-size: 9px"
																		face="Verdana, Arial, Helvetica, sans-serif">
																		<bean:write name="cobrancaAdministrativaDebito"
																			property="clienteUsuario" />
																	</font></td>
																	</logic:notEmpty>

																	<td width="10%" bordercolor="#90c7fc"
																		align="center">
																	<div class="style9"><font color="#000000"
																		style="font-size: 9px"
																		face="Verdana, Arial, Helvetica, sans-serif">
																	<bean:write name="cobrancaAdministrativaDebito"
																		property="situacaoItem" /> </font></div>
																	</td>
																	<td width="10%" bordercolor="#90c7fc"
																		align="center">
																	<div class="style9"><font color="#000000"
																		style="font-size: 9px"
																		face="Verdana, Arial, Helvetica, sans-serif">
																	<bean:write name="cobrancaAdministrativaDebito"
																		property="dataSituacao"
																		formatKey="date.format" /> </font></div>
																	</td>
																</tr>
														</logic:iterate>
														
														<!-- Totalizador -->
														<%if (cont % 2 == 0) {%>
														<tr bgcolor="#cbe5fe">
														<%} else {%>
														<tr bgcolor="#FFFFFF">
														<%}%>
																<td colspan="3" bgcolor="#90c7fc" align="center">
																<div class="style9" align="center"><font
																	color="#000000" style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif">
																<strong>Total</strong> </font></div>
																</td>
																<td align="right">
																<div align="right"><font color="#000000"
																	style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif">
																<%=session.getAttribute("valorTotalDebito")%> </font></div>
																</td>
																<td colspan="3" bgcolor="#90c7fc" align="left">
																<div align="left"><font color="#000000"
																	style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif">
																</font></div>
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
					</div>
					</td>
				</tr>

                <logic:equal name="indRemunCobAdmPorComando" value="<%="" + ConstantesSistema.NAO%>" scope="session">
				<tr>
					<td align="right">
					<div align="left">&nbsp;</div>
					</td>
				</tr>

				<!-- [SB0001B] - Dados da Remuneração da Cobrança Administrativa do Imóvel -->
				<tr>
					<td colspan="4">
					<div id="layerHideDadosRemuneracaoCobrancaAdministrativa" style="display: block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('DadosRemuneracaoCobrancaAdministrativa',true);" /><b>Dados
							da Remuneração da Cobrança Administrativa do Imóvel</b></a></span></td>
						</tr>
					</table>
					</div>

					<div id="layerShowDadosRemuneracaoCobrancaAdministrativa" style="display: none">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('DadosRemuneracaoCobrancaAdministrativa',false);" /><b>Dados
							da Remuneração da Cobrança Administrativa do Imóvel</b></a></span></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td>
							<table width="100%">
								<tr>
									<td width="45%"><strong>Percentual de Remuneração Padrão do Imóvel:</strong></td>
									<td colspan="3"><html:text property="percentualRemuneracao"
										readonly="true" style="background-color:#EFEFEF; border:0;"
										size="5" />
									</td>
								</tr>

								<tr>
									<td colspan="4">
									<table width="100%" align="center" bgcolor="#90c7fc" border="0">
										<tr bordercolor="#79bbfd">
											<td colspan="4" align="center" bgcolor="#79bbfd"><strong>Remuneração</strong></td>
										</tr>
										<tr bordercolor="#000000">
											<td width="40%" bgcolor="#90c7fc" align="center">
											<div class="style9"><font color="#000000" style="font-size: 9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <strong>Tipo de Remuneração</strong> </font></div>
											</td>
											<td width="28%" bgcolor="#90c7fc" align="center">
											<div class="style9"><font color="#000000" style="font-size: 9px"
												face="Verdana, Arial, Helvetica, sans-serif"> <strong>Percentual</strong> </font></div>
											</td>
											<td width="32%" bgcolor="#90c7fc" align="center"><font
												color="#000000" style="font-size: 9px"
												face="Verdana, Arial, Helvetica, sans-serif"><strong>Valor</strong>
											</font></td>
										</tr>
										<tr>
											<td width="100%" colspan="4">
											<div style="width: 100%; height: 100%; overflow: auto;">
											<table width="100%" align="left" bgcolor="#99CCFF">
												<!--corpo da segunda tabela-->
												<%
													cont = 0;
												%>
												<logic:notEmpty name="colecaoImovelCobAdmTotalizado">
													<logic:iterate name="colecaoImovelCobAdmTotalizado"	id="itemTotalizado">
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
																<td width="40%" bordercolor="#90c7fc" align="left">
																	<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
																		<%=((Object[]) itemTotalizado)[0]%>
																	</font>
																</td>
																<td width="28%" bordercolor="#90c7fc" align="right">
																	<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
																		<%=((Object[]) itemTotalizado)[1]%>
																	</font>
																</td>
																<td width="32%" bordercolor="#90c7fc" align="right">
																	<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
																		<%=((Object[]) itemTotalizado)[2]%>
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
        					<td colspan="4">
        					<div id="layerHideDadosDocumentoRemunerados" style="display: block">
        					<table width="100%" border="0" bgcolor="#99CCFF">
        						<tr bgcolor="#99CCFF">
        							<td align="center"><span class="style2"> <a
        								href="javascript:extendeTabela('DadosDocumentoRemunerados',true);" /><b>Documentos Remunerados</b></a></span></td>
        						</tr>
        					</table>
        					</div>
        
        					<div id="layerShowDadosDocumentoRemunerados" style="display: none">
        					<table width="100%" border="0" bgcolor="#99CCFF">
        						<tr bgcolor="#99CCFF">
        							<td align="center"><span class="style2"> <a
        								href="javascript:extendeTabela('DadosDocumentoRemunerados',false);" /><b>Documentos Remunerados</b></a></span></td>
        						</tr>
        						<tr bgcolor="#cbe5fe">
        							<td>
        							<table width="100%">
        								<logic:present name="colecaoContasDocumentosRemunerados">
        									<tr>
        										<td width="20%"><strong>Tipo do Documento:</strong></td>
        										<td colspan="3"><html:text
        											property="descricaoDocumentoTipoConta"
        											readonly="true"
        											style="background-color:#EFEFEF; border:0;" size="45" /></td>
        									</tr>
        
        									<tr>
        										<td colspan="4">
        										<table width="100%" align="center" bgcolor="#90c7fc"
        											border="0">
        											<tr bordercolor="#000000">
        												<td width="20%" bgcolor="#90c7fc" align="center">
        												<div class="style9"><font color="#000000"
        													style="font-size: 9px"
        													face="Verdana, Arial, Helvetica, sans-serif"> <strong>Referência da Conta</strong>
        												</font></div>
        												</td>
        												<td width="20%" bgcolor="#90c7fc" align="center"><font 
        												    color="#000000" style="font-size: 9px"
        													face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor da Conta</strong>
        												</font>
        												</td>
        												<td width="20%" bgcolor="#90c7fc" align="center"><font
        													color="#000000" style="font-size: 9px"
        													face="Verdana, Arial, Helvetica, sans-serif"><strong>Tipo da Remuneração</strong> </font></td>
        												<td width="20%" bgcolor="#90c7fc" align="center"><font
        													color="#000000" style="font-size: 9px"
        													face="Verdana, Arial, Helvetica, sans-serif"><strong>Percentual</strong> </font></td>
        												<td width="20%" bgcolor="#90c7fc" align="center"><font
        													color="#000000" style="font-size: 9px"
        													face="Verdana, Arial, Helvetica, sans-serif"><strong>Valor da Remuneração</strong> </font></td>
        											</tr>
        											<tr>
        												<td width="100%" colspan="9">
        												<div style="width: 100%; height: 100%; overflow: auto;">
        												<table width="100%" align="left" bgcolor="#99CCFF">
        													<!--corpo da segunda tabela-->
        													<%
        														cont = 0;
        													%>
        													<logic:notEmpty name="colecaoContasDocumentosRemunerados">
        														<logic:iterate name="colecaoContasDocumentosRemunerados"
        															id="contasDocumentosRemunerados"
        															type="CobrancaAdministrativaContaHelper">
        															<%
        																cont = cont + 1;
        																if (cont % 2 == 0) {
        															%>
        															    <tr bgcolor="#cbe5fe">
        															<%} else {%>
        																<tr bgcolor="#FFFFFF">
        															<%}%>
        																	<td width="20%" align="center">
        																	<div class="style9">
        																		<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
        																			<logic:notEmpty name="contasDocumentosRemunerados" property="referencia">
        																				<%=Util.formatarAnoMesParaMesAno(contasDocumentosRemunerados.getReferencia())%>
        																			</logic:notEmpty>
        																			<logic:empty name="contasDocumentosRemunerados" property="referencia">
        																				&nbsp;
        																			</logic:empty>
        																		</font>
        																	</div>
        																	</td>
        																	<td width="20%" align="right">
        																		<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
        																			<logic:notEmpty name="contasDocumentosRemunerados" property="valorConta">
        																				<bean:write name="contasDocumentosRemunerados" property="valorConta" formatKey="money.format" />
        																			</logic:notEmpty>
        																			<logic:empty name="contasDocumentosRemunerados" property="valorConta">
        																				&nbsp;
		        																	</logic:empty>
        																		</font>
        																	</td>
        																	<td width="20%" align="left">
        																		<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
        																			<bean:write name="contasDocumentosRemunerados" property="tipoRemuneracao" />
        																		</font>
        																	</td>
        																	<td width="20%" align="right">
        																		<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
        																			<logic:notEmpty name="contasDocumentosRemunerados" property="percentualRemuneracao">
        																				<bean:write name="contasDocumentosRemunerados" property="percentualRemuneracao" formatKey="money.format" />
        																			</logic:notEmpty>
        																			<logic:empty name="contasDocumentosRemunerados" property="percentualRemuneracao">
        																				&nbsp;
        																			</logic:empty>
        																		</font>
        																	</td>
        																	<td width="20%" align="right">
        																		<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
        																			<logic:notEmpty name="contasDocumentosRemunerados" property="valorRemuneracao">
        																				<bean:write name="contasDocumentosRemunerados" property="valorRemuneracao" formatKey="money.format" />
        																			</logic:notEmpty>
        																			<logic:empty name="contasDocumentosRemunerados" property="valorRemuneracao">
			        																	&nbsp;
        																			</logic:empty>
        																		</font>
        																	</td>
        																</tr>
        														</logic:iterate>
        
        														<!-- Totalizador -->
        														<%if (cont % 2 == 0) {%>
        														<tr bgcolor="#cbe5fe">
        														<%} else {%>
        														<tr bgcolor="#FFFFFF">
        														<%}%>
        																<td bgcolor="#90c7fc" align="center">
        																<div class="style9" align="center"><font
        																	color="#000000" style="font-size: 9px"
        																	face="Verdana, Arial, Helvetica, sans-serif">
        																<strong>Total</strong> </font></div>
        																</td>
        																<td align="right">
        																<div align="right"><font color="#000000"
        																	style="font-size: 9px"
        																	face="Verdana, Arial, Helvetica, sans-serif">
        																<%=session.getAttribute("valorTotalContasDocumentosRemunerados")%> </font></div>
        																</td>
        																<td colspan="2" bgcolor="#90c7fc" align="center">
        																<div class="style9" align="center"><font
        																	color="#000000" style="font-size: 9px"
        																	face="Verdana, Arial, Helvetica, sans-serif">
        																</font></div>
        																</td>
        																<td align="right">
        																<div align="right"><font color="#000000"
        																	style="font-size: 9px"
        																	face="Verdana, Arial, Helvetica, sans-serif">
        																<%=session.getAttribute("valorTotalRemuneracaoContaDocumentosRemunerados")%> </font></div>
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
        
        								<!-- Guia de Pagamento -->
        								<logic:present name="colecaoGuiasDocumentosRemunerados">
        									<tr>
        										<td align="right">
        										<div align="left">&nbsp;</div>
        										</td>
        									</tr>
        
        									<tr>
        										<td width="20%"><strong>Tipo do Documento:</strong></td>
        										<td colspan="3"><html:text
        											property="descricaoDocumentoTipoGuia"
        											readonly="true"
        											style="background-color:#EFEFEF; border:0;" size="45" /></td>
        									</tr>
        
        									<tr>
        										<td colspan="6">
        										<table width="100%" align="center" bgcolor="#90c7fc"
        											border="0">
        											<tr bordercolor="#000000">
        												<td width="20%" bgcolor="#90c7fc" align="center">
        												<div class="style9"><font color="#000000"
        													style="font-size: 9px"
        													face="Verdana, Arial, Helvetica, sans-serif"> <strong>Guia</strong> </font></div></td>
        												<td width="20%" bgcolor="#90c7fc" align="center"><font 
        												    color="#000000" style="font-size: 9px"
        													face="Verdana, Arial, Helvetica, sans-serif"><strong>Valor da Prestação</strong> </font></td>
        												<td width="20%" bgcolor="#90c7fc" align="center"><font
        													color="#000000" style="font-size: 9px"
        													face="Verdana, Arial, Helvetica, sans-serif"><strong>Tipo da Remuneração</strong> </font></td>
        												<td width="20%" bgcolor="#90c7fc" align="center"><font
        													color="#000000" style="font-size: 9px"
        													face="Verdana, Arial, Helvetica, sans-serif"><strong>Percentual</strong> </font></td>
        												<td width="20%" bgcolor="#90c7fc" align="center"><font
        													color="#000000" style="font-size: 9px"
        													face="Verdana, Arial, Helvetica, sans-serif"><strong>Valor da Remuneração</strong> </font></td>
        											</tr>
        											<tr>
        												<td width="100%" colspan="6">
        												<div style="width: 100%; height: 100%; overflow: auto;">
        												<table width="100%" align="left" bgcolor="#99CCFF">
        													<!--corpo da segunda tabela-->
        													<%
        														cont = 0;
        													%>
        													<logic:notEmpty name="colecaoGuiasDocumentosRemunerados">
        														<logic:iterate name="colecaoGuiasDocumentosRemunerados"
        															id="guiasDocumentosRemunerados"
        															type="CobrancaAdministrativaGuiaHelper">
        															<%
        																cont = cont + 1;
        																if (cont % 2 == 0) {
        															%>
        															<tr bgcolor="#cbe5fe">
        															<% } else { %>
        															<tr bgcolor="#FFFFFF">
        															<% } %>
        																	<td width="20%" align="center">
        																	<div class="style9">
        																		<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
        																			<logic:notEmpty name="guiasDocumentosRemunerados" property="id">
        																				<bean:write name="guiasDocumentosRemunerados" property="idComPrestacao" />
        																			</logic:notEmpty>
        																			<logic:empty name="guiasDocumentosRemunerados" property="id">
        																				&nbsp;
        																			</logic:empty>
        																		</font>
        																	</div>
        																	</td>
        																	<td width="20%" align="right">
        																		<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
        																			<logic:notEmpty name="guiasDocumentosRemunerados" property="id">
        																				<bean:write name="guiasDocumentosRemunerados" property="valorPrestacao" formatKey="money.format" />
        																			</logic:notEmpty>
        																			<logic:empty name="guiasDocumentosRemunerados" property="id">
        																				&nbsp;
		        																	</logic:empty>
        																		</font>
        																	</td>
        																	<td width="20%" align="left">
        																		<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
        																			<bean:write name="guiasDocumentosRemunerados" property="tipoRemuneracao" />
        																		</font>
        																	</td>
        																	<td width="20%" align="right">
        																		<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
        																			<logic:notEmpty name="guiasDocumentosRemunerados" property="percentualRemuneracao">
        																				<bean:write name="guiasDocumentosRemunerados" property="percentualRemuneracao" formatKey="money.format" />
        																			</logic:notEmpty>
        																			<logic:empty name="guiasDocumentosRemunerados" property="percentualRemuneracao">
        																				&nbsp;
        																			</logic:empty>
        																		</font>
        																	</td>
        																	<td width="20%" align="right">
        																		<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
        																			<logic:notEmpty name="guiasDocumentosRemunerados" property="valorRemuneracao">
        																				<bean:write name="guiasDocumentosRemunerados" property="valorRemuneracao" formatKey="money.format" />
        																			</logic:notEmpty>
        																			<logic:empty name="guiasDocumentosRemunerados" property="valorRemuneracao">
			        																	&nbsp;
        																			</logic:empty>
        																		</font>
        																	</td>
        																</tr>
        														</logic:iterate>
        														
        														<!-- Totalizador -->
        														<%if (cont % 2 == 0) {%>
        														<tr bgcolor="#cbe5fe">
        														<%} else {%>
        														<tr bgcolor="#FFFFFF">
        														<%}%>
        																<td bgcolor="#90c7fc" align="center">
        																<div class="style9" align="center"><font
        																	color="#000000" style="font-size: 9px"
        																	face="Verdana, Arial, Helvetica, sans-serif">
        																<strong>Total</strong> </font></div>
        																</td>
        																<td align="right">
        																<div align="right"><font color="#000000"
        																	style="font-size: 9px"
        																	face="Verdana, Arial, Helvetica, sans-serif">
        																<%=session.getAttribute("valorTotalGuiasDocumentosRemunerados")%> </font></div>
        																</td>
        																<td colspan="2" bgcolor="#90c7fc" align="center">
        																<div class="style9" align="center"><font
        																	color="#000000" style="font-size: 9px"
        																	face="Verdana, Arial, Helvetica, sans-serif">
        																</font></div>
        																</td>
        																<td align="right">
        																<div align="right"><font color="#000000"
        																	style="font-size: 9px"
        																	face="Verdana, Arial, Helvetica, sans-serif">
        																<%=session.getAttribute("valorTotalRemuneracaoGuiaDocumentosRemunerados")%> </font></div>
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
										
										<!-- Debito A Cobrar -->
										<logic:present name="colecaoDebitosDocumentosRemunerados">
        									<tr>
        										<td width="20%"><strong>Tipo do Documento:</strong></td>
        										<td colspan="3"><html:text
        											property="descricaoDocumentoTipoDebito"
        											readonly="true"
        											style="background-color:#EFEFEF; border:0;" size="45" /></td>
        									</tr>
        
        									<tr>
        										<td colspan="4">
        										<table width="100%" align="center" bgcolor="#90c7fc"
        											border="0">
        											<tr bordercolor="#000000">
        												<td width="20%" bgcolor="#90c7fc" align="center">
        												<div class="style9"><font color="#000000"
        													style="font-size: 9px"
        													face="Verdana, Arial, Helvetica, sans-serif"> <strong>Débito a Cobrar </strong>
        												</font></div>
        												</td>
        												<td width="20%" bgcolor="#90c7fc" align="center"><font 
        												    color="#000000" style="font-size: 9px"
        													face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor do Débito a Cobrar</strong>
        												</font>
        												</td>
        												<td width="20%" bgcolor="#90c7fc" align="center"><font
        													color="#000000" style="font-size: 9px"
        													face="Verdana, Arial, Helvetica, sans-serif"><strong>Tipo da Remuneração</strong> </font></td>
        												<td width="20%" bgcolor="#90c7fc" align="center"><font
        													color="#000000" style="font-size: 9px"
        													face="Verdana, Arial, Helvetica, sans-serif"><strong>Percentual</strong> </font></td>
        												<td width="20%" bgcolor="#90c7fc" align="center"><font
        													color="#000000" style="font-size: 9px"
        													face="Verdana, Arial, Helvetica, sans-serif"><strong>Valor da Remuneração</strong> </font></td>
        											</tr>
        											<tr>
        												<td width="100%" colspan="9">
        												<div style="width: 100%; height: 100%; overflow: auto;">
        												<table width="100%" align="left" bgcolor="#99CCFF">
        													<!--corpo da segunda tabela-->
        													<%
        														cont = 0;
        													%>
        													<logic:notEmpty name="colecaoDebitosDocumentosRemunerados">
        														<logic:iterate name="colecaoDebitosDocumentosRemunerados"
        															id="debitosDocumentosRemunerados"
        															type="CobrancaAdministrativaHelper">
        															<%
        																cont = cont + 1;
        																if (cont % 2 == 0) {
        															%>
        															    <tr bgcolor="#cbe5fe">
        															<%} else {%>
        																<tr bgcolor="#FFFFFF">
        															<%}%>
        																	<td width="20%" align="center">
        																	<div class="style9">
        																		<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
        																			<logic:notEmpty name="debitosDocumentosRemunerados" property="idDebitoACobrar">
        																				<bean:write name="debitosDocumentosRemunerados" property="idDebitoACobrar" />
        																			</logic:notEmpty>
        																			<logic:empty name="debitosDocumentosRemunerados" property="idDebitoACobrar">
        																				&nbsp;
        																			</logic:empty>
        																		</font>
        																	</div>
        																	</td>
        																	<td width="20%" align="right">
        																		<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
        																			<logic:notEmpty name="debitosDocumentosRemunerados" property="valorDebito">
        																				<bean:write name="debitosDocumentosRemunerados" property="valorDebito" formatKey="money.format" />
        																			</logic:notEmpty>
        																			<logic:empty name="debitosDocumentosRemunerados" property="valorDebito">
        																				&nbsp;
		        																	</logic:empty>
        																		</font>
        																	</td>
        																	<td width="20%" align="left">
        																		<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
        																			<bean:write name="debitosDocumentosRemunerados" property="tipoRemuneracao" />
        																		</font>
        																	</td>
        																	<td width="20%" align="right">
        																		<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
        																			<logic:notEmpty name="debitosDocumentosRemunerados" property="percentualRemuneracao">
        																				<bean:write name="debitosDocumentosRemunerados" property="percentualRemuneracao" formatKey="money.format" />
        																			</logic:notEmpty>
        																			<logic:empty name="debitosDocumentosRemunerados" property="percentualRemuneracao">
        																				&nbsp;
        																			</logic:empty>
        																		</font>
        																	</td>
        																	<td width="20%" align="right">
        																		<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
        																			<logic:notEmpty name="debitosDocumentosRemunerados" property="valorRemuneracao">
        																				<bean:write name="debitosDocumentosRemunerados" property="valorRemuneracao" formatKey="money.format" />
        																			</logic:notEmpty>
        																			<logic:empty name="debitosDocumentosRemunerados" property="valorRemuneracao">
			        																	&nbsp;
        																			</logic:empty>
        																		</font>
        																	</td>
        																</tr>
        														</logic:iterate>
        
        														<!-- Totalizador -->
        														<%if (cont % 2 == 0) {%>
        														<tr bgcolor="#cbe5fe">
        														<%} else {%>
        														<tr bgcolor="#FFFFFF">
        														<%}%>
        																<td bgcolor="#90c7fc" align="center">
        																<div class="style9" align="center"><font
        																	color="#000000" style="font-size: 9px"
        																	face="Verdana, Arial, Helvetica, sans-serif">
        																<strong>Total</strong> </font></div>
        																</td>
        																<td align="right">
        																<div align="right"><font color="#000000"
        																	style="font-size: 9px"
        																	face="Verdana, Arial, Helvetica, sans-serif">
        																<%=session.getAttribute("valorTotalDebitosDocumentosRemunerados")%> </font></div>
        																</td>
        																<td colspan="2" bgcolor="#90c7fc" align="center">
        																<div class="style9" align="center"><font
        																	color="#000000" style="font-size: 9px"
        																	face="Verdana, Arial, Helvetica, sans-serif">
        																</font></div>
        																</td>
        																<td align="right">
        																<div align="right"><font color="#000000"
        																	style="font-size: 9px"
        																	face="Verdana, Arial, Helvetica, sans-serif">
        																<%=session.getAttribute("valorTotalRemuneracaoDebitosDocumentosRemunerados")%> </font></div>
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
        					</div>
        					</td>
        				</tr>
        				
						<tr>
							<td align="right">
							<div align="left">&nbsp;</div>
							</td>
						</tr>
						
					</table>
					</div>
					</td>
				</tr>
				</logic:equal>

				<tr>
					<td colspan="2" width="100%">
					<table width="100%">
						<tr>
							<td>
							<input name="button" class="bottonRightCol" value="Voltar"
								type="button" onclick="window.location.href='<html:rewrite page="/exibirManterImovelCobrancaAdministrativaAction.do"/>'" align="left" style="width: 80px;">

							<input name="Submit22" class="bottonRightCol" value="Desfazer"
								type="button" onclick="window.location.href='<html:rewrite page="/exibirFiltrarImovelCobrancaAdministrativaAction.do"/>'" align="left" style="width: 80px;">

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
