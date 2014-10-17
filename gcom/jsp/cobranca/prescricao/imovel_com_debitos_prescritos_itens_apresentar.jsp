<%@page import="gcom.faturamento.debito.DebitoCreditoSituacao"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="gcom.cadastro.cliente.ClienteImovel"%>
<%@ page import="gcom.cadastro.imovel.ImovelSubcategoria"%>
<%@ page import="gcom.cobranca.bean.PrescricaoContaHelper"%>
<%@ page import="gcom.cobranca.bean.PrescricaoGuiaPrestacaoHelper"%>
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
	formName="ApresentarItensDebitosPrescritosActionForm" />

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
	
	function facilitador(objeto,nome){
		
		if (objeto.value == "0" || objeto.id == undefined){

			objeto.value = "1";
			marcarTodosDebitos(nome);
			
		} else{
			
			objeto.value = "0";
			desmarcarTodosDebitos(nome);
		}
	}
	
	
	function marcarTodosDebitos(nome){
	
		for (var i=0;i < document.forms[0].elements.length;i++){
			
			var elemento = document.forms[0].elements[i];
			
			if (elemento.type == "checkbox" && elemento.name == nome && elemento.value != '-1'){
				
				elemento.checked = true;
			}
		}
	}

	
	function desmarcarTodosDebitos(nome) {
	
		for (var i=0;i < document.forms[0].elements.length;i++){
			
			var elemento = document.forms[0].elements[i];
			
			if (elemento.type == "checkbox" && elemento.name == nome){
				elemento.checked = false;
			}
		}
	}
	
	function verificarSubmitContas(check1) {

		var form = document.forms[0];
		var desmarcarConta = false;
		
		if (!verificarMarcados(check1)){
		
			alert('É necessário selecionar pelo menos uma conta para o sistema efetuar a desmarcação.');
			return false;	
		}else{
			
			desmarcarConta = true;
		}
		
		if (desmarcarConta){
			
			if (confirm('Confirma Desmarcação da Prescrição?')) {
			    
				form.action = '/gsan/desmarcarPrescricaoDebitosAction.do';
	 			form.submit();
			    return true;
		    }
			
			return false;
		}
	}
	
	function verificarSubmitGuias(check2) {

		var form = document.forms[0];
		var desmarcarGuia = false;
	
		if (!verificarMarcados(check2)){
			
			alert('É necessário selecionar pelo menos uma prestação para o sistema efetuar a desmarcação.');
			return false;	
		}else{
			
			desmarcarGuia = true;
		}
		
		if (desmarcarGuia){
			
			if (confirm('Confirma Desmarcação da Prescrição?')) {
			    
				form.action = '/gsan/desmarcarPrescricaoDebitosAction.do';
	 			form.submit();
			    return true;
		    }
			
			return false;
		}
	}
	
	function verificarMarcados(checkbox) {
		
		if (checkbox != undefined) {
			
			if (checkbox.length == undefined) {
				
				if (checkbox.checked) {
					
					return true;
				}
				
			} else {
				
				for (var i=0;i < checkbox.length;i++){
						
					if (checkbox[i].checked) {
							
						return true;
					}
				}	
			}
		
			return false;
		}
	}

</script>

</head>

<body leftmargin="5" topmargin="5">

<html:form action="/exibirApresentarItensDebitosPrescritosAction"
	name="ApresentarItensDebitosPrescritosActionForm"
	type="gcom.gui.cobranca.prescricao.ApresentarItensDebitosPrescritosActionForm"
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
					<td class="parabg">Apresentar Itens de Débitos Prescritos</td>
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
											<div class="style9">${ApresentarItensDebitosPrescritosActionForm.enderecoImovel}
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
				
				<!-- Exibir Dados dos Itens do Débito do Imóvel -->
				<tr>
					<td colspan="4">
					<div id="layerHideDadosDocumento" style="display: block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('DadosDocumento',true);" ><b>Exibir Dados dos Itens de Débito Prescritos do Imóvel</b></a></span></td>
						</tr>
					</table>
					</div>

					<div id="layerShowDadosDocumento" style="display: none">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('DadosDocumento',false);" ><b>Exibir Dados dos Itens de Débito Prescritos do Imóvel</b></a></span></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td>
							<table width="100%">
								<logic:present name="colecaoPrescricaoContaHelper">
									<tr>
										<td width="100%"><strong>Dados das Contas:</strong></td>
									</tr>

									<tr>
										<td>
										<table width="100%" align="center" bgcolor="#90c7fc"
											border="0">
											<tr bordercolor="#000000">
												
												<td width="7%" align="center" >
													<div align="center"><strong><a href="javascript:facilitador(document.forms[0].checkConta,'idsContaDesmarcar');" id="0">Todos</a></strong></div>
												</td>
											
												<td width="10%" bgcolor="#90c7fc" align="center">
													<div class="style9"><font color="#000000"
														style="font-size: 9px"
														face="Verdana, Arial, Helvetica, sans-serif"> <strong>Referência</strong>
													</font></div>
												</td>
												
												<td width="10%" bgcolor="#90c7fc" align="center">
												<div class="style9"><font color="#000000"
													style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <strong>Vencimento</strong>
												</font></div>
												</td>
													
												<td width="10%" bgcolor="#90c7fc" align="center"><font
													color="#000000" style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"><strong>Valor Água</strong> </font></td>
													
												<td width="10%" bgcolor="#90c7fc" align="center"><font
													color="#000000" style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"><strong>Valor Esgoto</strong> </font></td>
													
												<td width="10%" bgcolor="#90c7fc" align="center"><font
													color="#000000" style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"><strong>Valor Débitos</strong> </font></td>
													
												<td width="10%" bgcolor="#90c7fc" align="center"><font
													color="#000000" style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"><strong>Valor Créditos</strong> </font></td>
													
												<td width="10%" bgcolor="#90c7fc" align="center"><font
													color="#000000" style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"><strong>Valor Impostos</strong> </font></td>
													
												<td width="10%" bgcolor="#90c7fc" align="center"><font
													color="#000000" style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"><strong>Valor Conta</strong> </font></td>
													
												<td width="7%" bgcolor="#90c7fc" align="center"><font
													color="#000000" style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"><strong>Situação</strong> </font></td>
													
												<td width="6%" bgcolor="#90c7fc" align="center"><font
													color="#000000" style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"><strong>Paga</strong> </font></td>
											</tr>
											<tr>
												<td width="100%" colspan="11">
												<div style="width: 100%; height: 100%; overflow: auto;">
												<table width="100%" align="left" bgcolor="#99CCFF">
													<!--corpo da segunda tabela-->
													<%
														cont = 0;
													%>
													<logic:notEmpty name="colecaoPrescricaoContaHelper">
														<logic:iterate name="colecaoPrescricaoContaHelper"
															id="prescricaoContaHelper"
															type="PrescricaoContaHelper">
															<%
																cont = cont + 1;
																if (cont % 2 == 0) {
															%>
															    <tr bgcolor="#cbe5fe">
															<%} else {%>
																<tr bgcolor="#FFFFFF">
															<%}%>
																
																	
																	<td align="center" width="7%">
																		<div align="center">
																			<logic:equal name="prescricaoContaHelper" property="indicadorHistorico" value="<%=""+ConstantesSistema.NAO%>">
																				<logic:equal name="prescricaoContaHelper" property="idDebitoCreditoSituacao" value="<%=""+ DebitoCreditoSituacao.PRESCRITA%>">
																					<input
																						type="checkbox" name="idsContaDesmarcar"
																						value="<%="" + prescricaoContaHelper.getIdConta().toString()%>">
																				</logic:equal>
																				<logic:notEqual name="prescricaoContaHelper" property="idDebitoCreditoSituacao" value="<%=""+DebitoCreditoSituacao.PRESCRITA%>">
																					<input
																						type="checkbox" name="idsContaBloqueadas" disabled="disabled"
																						value="<%="" + prescricaoContaHelper.getIdConta().toString()%>">
																				</logic:notEqual>
																			</logic:equal>
																			<logic:equal name="prescricaoContaHelper" property="indicadorHistorico" value="<%=""+ConstantesSistema.SIM%>">
																				<input
																					type="checkbox" name="idsContaBloqueadas" disabled="disabled"
																					value="<%="" + prescricaoContaHelper.getIdConta().toString()%>">
																			</logic:equal>
																		</div>
																	</td>
																	
																	<td width="10%" align="center">
																		<font color="#000000"
																				style="font-size: 9px"
																				face="Verdana, Arial, Helvetica, sans-serif">
																			
																			<logic:equal name="prescricaoContaHelper" property="indicadorHistorico" value="<%=""+ConstantesSistema.SIM%>">
																			
																			
																				 <logic:equal name="prescricaoContaHelper" property="indicadorPaga" value="<%="NÃO"%>">
																				
																				  <a href="javascript:abrirPopup('exibirConsultarContaAction.do?tipoConsulta=contaHistorico&contaID=<%="" + prescricaoContaHelper.getIdConta().toString()%>' , 600, 800);"
																					title="<%="" + Util.formatarAnoMesParaMesAno(prescricaoContaHelper.getReferencia())%>">
													
																					<%=Util.formatarAnoMesParaMesAno(prescricaoContaHelper.getReferencia())%> 
																				   </a>
																				 </logic:equal>  
																				 
																				  <logic:equal name="prescricaoContaHelper" property="indicadorPaga" value="<%="SIM"%>">
																				 <%=Util.formatarAnoMesParaMesAno(prescricaoContaHelper.getReferencia())%> 
																				  </logic:equal>
																				   
																				   																				
																			</logic:equal>
																			
																			
																			
																			
																			
																			<logic:equal name="prescricaoContaHelper" property="indicadorHistorico" value="<%=""+ConstantesSistema.NAO%>">
																				
																				<a href="javascript:abrirPopup('exibirConsultarContaAction.do?tipoConsulta=conta&contaID=<%="" + prescricaoContaHelper.getIdConta().toString()%>' , 600, 800);"
																					title="<%="" + Util.formatarAnoMesParaMesAno(prescricaoContaHelper.getReferencia())%>">
													
																					<%=Util.formatarAnoMesParaMesAno(prescricaoContaHelper.getReferencia())%> 
																				</a>
																				
																			</logic:equal>
																		</font>
																		
																	</td>
																	
																	<td width="10%" bordercolor="#90c7fc"
																		align="center">
																		<div class="style9">
																			<font color="#000000"
																				style="font-size: 9px"
																				face="Verdana, Arial, Helvetica, sans-serif">
																				<bean:write name="prescricaoContaHelper" property="dataVencimentoConta" formatKey="date.format" /> 
																			</font>
																		</div>
																	</td>
																	
																	<td width="10%" align="center"><font
																		color="#000000" style="font-size: 9px"
																		face="Verdana, Arial, Helvetica, sans-serif">
																		
																		<bean:write name="prescricaoContaHelper" property="valorAgua" formatKey="money.format" />
																	</font></td>
																	
																	<td width="10%" align="center"><font
																		color="#000000" style="font-size: 9px"
																		face="Verdana, Arial, Helvetica, sans-serif">
																		
																		<bean:write name="prescricaoContaHelper" property="valorEsgoto" formatKey="money.format" />
																	</font></td>
																	
																	<td width="10%" align="center">
																		<font color="#000000" style="font-size: 9px"
																			face="Verdana, Arial, Helvetica, sans-serif">
																			
																			<logic:equal name="prescricaoContaHelper" property="indicadorHistorico" value="<%=""+ConstantesSistema.SIM%>">
																				
																				<a href="javascript:abrirPopup('exibirConsultarDebitoCobradoAction.do?tipoConsulta=contaHistorico&contaID=<%="" + prescricaoContaHelper.getIdConta().toString()%>' , 500, 800);"
																					title="<%="" + Util.formatarMoedaReal(prescricaoContaHelper.getValorDebitos(), 2)%>">
													
																					<%=Util.formatarMoedaReal(prescricaoContaHelper.getValorDebitos(), 2)%> 
																				</a>
																				
																			</logic:equal>
																			<logic:equal name="prescricaoContaHelper" property="indicadorHistorico" value="<%=""+ConstantesSistema.NAO%>">
																				
																				<a href="javascript:abrirPopup('exibirConsultarDebitoCobradoAction.do?tipoConsulta=conta&contaID=<%="" + prescricaoContaHelper.getIdConta().toString()%>' , 500, 800);"
																					title="<%="" + Util.formatarMoedaReal(prescricaoContaHelper.getValorDebitos(), 2)%>">
													
																					<%=Util.formatarMoedaReal(prescricaoContaHelper.getValorDebitos(), 2)%> 
																				</a>
																				
																			</logic:equal>
																		</font>
																	</td>
																	
																	<td width="10%" align="center">
																		<font color="#000000" style="font-size: 9px"
																			face="Verdana, Arial, Helvetica, sans-serif">
																			
																			<logic:equal name="prescricaoContaHelper" property="indicadorHistorico" value="<%=""+ConstantesSistema.SIM%>">
																				
																				<a href="javascript:abrirPopup('exibirConsultarCreditoRealizadoAction.do?tipoConsulta=contaHistorico&contaID=<%="" + prescricaoContaHelper.getIdConta().toString()%>' , 500, 800);"
																					title="<%="" + Util.formatarMoedaReal(prescricaoContaHelper.getValorCreditos(), 2)%>">
													
																					<%=Util.formatarMoedaReal(prescricaoContaHelper.getValorCreditos(), 2)%> 
																				</a>
																				
																			</logic:equal>
																			<logic:equal name="prescricaoContaHelper" property="indicadorHistorico" value="<%=""+ConstantesSistema.NAO%>">
																				
																				<a href="javascript:abrirPopup('exibirConsultarCreditoRealizadoAction.do?tipoConsulta=conta&contaID=<%="" + prescricaoContaHelper.getIdConta().toString()%>' , 500, 800);"
																					title="<%="" + Util.formatarMoedaReal(prescricaoContaHelper.getValorCreditos(), 2)%>">
													
																					<%=Util.formatarMoedaReal(prescricaoContaHelper.getValorCreditos(), 2)%> 
																				</a>
																				
																			</logic:equal>
																		</font>	
																	</td>
																	
																	<td width="10%" align="center"><font
																		color="#000000" style="font-size: 9px"
																		face="Verdana, Arial, Helvetica, sans-serif">
																		
																		<bean:write name="prescricaoContaHelper" property="valorImpostos" formatKey="money.format" />
																	</font></td>
																	
																	<td width="10%" align="center"><font
																		color="#000000" style="font-size: 9px"
																		face="Verdana, Arial, Helvetica, sans-serif">
																		
																		<bean:write name="prescricaoContaHelper" property="valorConta" formatKey="money.format" />
																	</font></td>
																	
																	
																	<td width="7%" bordercolor="#90c7fc"
																		align="center">
																		<div class="style9">
																			<font color="#000000"
																				style="font-size: 9px"
																				face="Verdana, Arial, Helvetica, sans-serif">
																			<bean:write name="prescricaoContaHelper" property="descricaoDebitoCreditoSituacao" /> 
																		</font>
																		</div>
																	</td>
																	
																	<td width="6%" bordercolor="#90c7fc"
																		align="center">
																		<div class="style9">
																			<font color="#000000"
																				style="font-size: 9px"
																				face="Verdana, Arial, Helvetica, sans-serif">
																				<bean:write name="prescricaoContaHelper" property="indicadorPaga"/> 
																			</font>
																		</div>
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
																
																<td align="center">
																<div align="center"><font color="#000000"
																	style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif">
																<%=session.getAttribute("valorTotalAguaContas")%> </font></div>
																</td>
																
																<td align="center">
																<div align="center"><font color="#000000"
																	style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif">
																<%=session.getAttribute("valorTotalEsgotoContas")%> </font></div>
																</td>
																
																<td align="center">
																<div align="center"><font color="#000000"
																	style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif">
																<%=session.getAttribute("valorTotalDebitoContas")%> </font></div>
																</td>
																
																<td align="center">
																<div align="center"><font color="#000000"
																	style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif">
																<%=session.getAttribute("valorTotalCreditoContas")%> </font></div>
																</td>
																
																<td align="center">
																<div align="center"><font color="#000000"
																	style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif">
																<%=session.getAttribute("valorTotalImpostoContas")%> </font></div>
																</td>
																
																<td align="center">
																<div align="center"><font color="#000000"
																	style="font-size: 9px"
																	face="Verdana, Arial, Helvetica, sans-serif">
																<%=session.getAttribute("valorTotalContas")%> </font></div>
																</td>
																
																<td colspan="2" bgcolor="#90c7fc" align="left">
																
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
									
									<tr>
										<td colspan="2" width="100%">
										<table width="100%">
											<tr>
												<td>
												<input type="button"
													onclick="verificarSubmitContas(document.forms[0].idsContaDesmarcar);"
													class="bottonRightCol" value="Desmarcar Prescrição" style="width: 150px;">
												</td>
											</tr>
										</table>
										</td>
									</tr>
								</logic:present>
								
								</table>

								<table>
									<!-- Guia de Pagamento -->
									<logic:present name="colecaoPrescricaoGuiaPrestacaoHelper">
										<tr>
											<td width="100%"><strong>Dados das Prestações de Guia de Pagamento:</strong></td>
										</tr>
	
										<tr>
											<td>
											<table width="100%" align="center" bgcolor="#90c7fc"
												border="0">
												<tr bordercolor="#000000">
													
													<td align="center" width="7%">
														<div class="style9"><strong><a href="javascript:facilitador(document.forms[0].checkGuia,'idsGuiaPagamentoDesmarcar');"id="0">Todos</a></strong></div>
													</td>
												
												   <td width="10%" bgcolor="#90c7fc" align="center">							
													<div class="style9"><font color="#000000"
														style="font-size: 9px"
														face="Verdana, Arial, Helvetica, sans-serif"> <strong>Guia</strong> </font></div>
													</td>
													
													<td width="8%" bgcolor="#90c7fc" align="center">							
													<div class="style9"><font color="#000000"
														style="font-size: 9px"
														face="Verdana, Arial, Helvetica, sans-serif"> <strong>Prest.</strong> </font></div>
													</td>
													
													<td width="25%" bgcolor="#90c7fc" align="center">
													<div class="style9"><font color="#000000"
														style="font-size: 9px"
														face="Verdana, Arial, Helvetica, sans-serif"> <strong>Tipo
													de Débito</strong> </font></div>
													</td>
													
													<td width="12%" bgcolor="#90c7fc" align="center">
													<div class="style9"><font color="#000000"
														style="font-size: 9px"
														face="Verdana, Arial, Helvetica, sans-serif"> <strong>Dt. Emissão</strong> </font></div>
													</td>
													
													<td width="12%" bgcolor="#90c7fc" align="center">
													<div class="style9"><font color="#000000"
														style="font-size: 9px"
														face="Verdana, Arial, Helvetica, sans-serif"> <strong>Dt. Vencimento</strong> </font></div>
													</td>
													
													<td width="10%" bgcolor="#90c7fc" align="center"><font
														color="#000000" style="font-size: 9px"
														face="Verdana, Arial, Helvetica, sans-serif"><strong>Valor da Prest.</strong>
													</font></td>
													
													<td width="10%" bgcolor="#90c7fc" align="center"><font
														color="#000000" style="font-size: 9px"
														face="Verdana, Arial, Helvetica, sans-serif"><strong>Situação</strong> </font></td>
														
													<td width="6%" bgcolor="#90c7fc" align="center"><font
														color="#000000" style="font-size: 9px"
														face="Verdana, Arial, Helvetica, sans-serif"><strong>Paga</strong> </font></td>
												</tr>
												<tr>
													<td width="100%" colspan="9">
													<div style="width: 100%; height: 100%; overflow: auto;">
													<table width="100%" align="left" bgcolor="#99CCFF">
														<!--corpo da segunda tabela-->
														<%
															cont = 0;
														%>
														<logic:notEmpty name="colecaoPrescricaoGuiaPrestacaoHelper">
															<logic:iterate name="colecaoPrescricaoGuiaPrestacaoHelper"
																id="prescricaoGuiaPrestacaoHelper"
																type="PrescricaoGuiaPrestacaoHelper">
																<%
																	cont = cont + 1;
																	if (cont % 2 == 0) {
																%>
																<tr bgcolor="#cbe5fe">
																<% } else { %>
																<tr bgcolor="#FFFFFF">
																<% } %>
																		
																		<td align="center" width="7%">
																			<div align="center">
																				<logic:equal name="prescricaoGuiaPrestacaoHelper" property="indicadorHistorico" value="<%=""+ConstantesSistema.NAO%>">
																					<logic:equal name="prescricaoGuiaPrestacaoHelper" property="idDebitoCreditoSituacao" value="<%=""+DebitoCreditoSituacao.PRESCRITA%>">
																						<input
																							type="checkbox" name="idsGuiaPagamentoDesmarcar"
																							value="<%="" + prescricaoGuiaPrestacaoHelper.getIdComPrestacao().toString()%>">
																					</logic:equal>
																					<logic:notEqual name="prescricaoGuiaPrestacaoHelper" property="idDebitoCreditoSituacao" value="<%=""+DebitoCreditoSituacao.PRESCRITA%>">
																						<input
																							type="checkbox" name="idsGuiaPagamentoBloqueadas"
																							disabled="disabled"
																							value="<%="" + prescricaoGuiaPrestacaoHelper.getIdComPrestacao().toString()%>">
																					</logic:notEqual>
																				</logic:equal>
																				<logic:equal name="prescricaoGuiaPrestacaoHelper" property="indicadorHistorico" value="<%=""+ConstantesSistema.SIM%>">
																					<input
																						type="checkbox" name="idsGuiaPagamentoBloqueadas"
																						disabled="disabled"
																						value="<%="" + prescricaoGuiaPrestacaoHelper.getIdComPrestacao().toString()%>">
																				</logic:equal>
																			</div>
																		</td>
																		
																		<td width="10%" bordercolor="#90c7fc" align="center">
																			<div class="style9">
																				<font color="#000000"
																					style="font-size: 9px"
																					face="Verdana, Arial, Helvetica, sans-serif">
																					
																			
																			
																			<a href="javascript:abrirPopup('exibirConsultarGuiaPagamentoAction.do?guiaPagamentoId=<%="" + prescricaoGuiaPrestacaoHelper.getIdGuiaPagamento().toString()%>' , 600, 800);"
																					title="<%="" + prescricaoGuiaPrestacaoHelper.getIdGuiaPagamento()%>">
													
																				<bean:write name="prescricaoGuiaPrestacaoHelper"
																				property="idGuiaPagamento" />
																			
																			</a>
																				
																			
																					
																			
														
																				
																				
																		</font></div>
																		</td>
																		
																		<td width="8%" bordercolor="#90c7fc" align="center">
																			<div class="style9">
																				<font color="#000000"
																					style="font-size: 9px"
																					face="Verdana, Arial, Helvetica, sans-serif">
																			<bean:write name="prescricaoGuiaPrestacaoHelper"
																				property="numeroPrestacao" />
																		</font></div>
																		</td>
																		
																		<td width="25%" align="left">
																		<div class="style9"><font color="#000000"
																			style="font-size: 9px"
																			face="Verdana, Arial, Helvetica, sans-serif">
																		<bean:write name="prescricaoGuiaPrestacaoHelper"
																			property="descricaoTipoDebito" /> </font></div>
																		</td>
																		
																		<td width="12%" bordercolor="#90c7fc" align="center">
																		<div class="style9"><font color="#000000"
																			style="font-size: 9px"
																			face="Verdana, Arial, Helvetica, sans-serif">
																		<bean:write name="prescricaoGuiaPrestacaoHelper"
																			property="dataEmissao" formatKey="date.format" />
																		</font></div>
																		</td>
																		
																		<td width="12%" bordercolor="#90c7fc" align="center">
																		<div class="style9"><font color="#000000"
																			style="font-size: 9px"
																			face="Verdana, Arial, Helvetica, sans-serif">
																		<bean:write name="prescricaoGuiaPrestacaoHelper"
																			property="dataVencimento" formatKey="date.format" />
																		</font></div>
																		</td>
																		
																		<td width="10%" align="right"><font
																			color="#000000" style="font-size: 9px"
																			face="Verdana, Arial, Helvetica, sans-serif">
																		<bean:write name="prescricaoGuiaPrestacaoHelper"
																			property="valorPrestacao"
																			formatKey="money.format" /> </font></td>
	
																		<td width="10%" bordercolor="#90c7fc"
																			align="center">
																		<div class="style9"><font color="#000000"
																			style="font-size: 9px"
																			face="Verdana, Arial, Helvetica, sans-serif">
																		<bean:write name="prescricaoGuiaPrestacaoHelper"
																			property="descricaoDebitoCreditoSituacao" /> </font></div>
																		</td>
																		
																		<td width="6%" bordercolor="#90c7fc"
																			align="center">
																		<div class="style9"><font color="#000000"
																			style="font-size: 9px"
																			face="Verdana, Arial, Helvetica, sans-serif">
																		<bean:write name="prescricaoGuiaPrestacaoHelper"
																			property="indicadorPaga" /> </font></div>
																		</td>
																	</tr>
															</logic:iterate>
															
															<!-- Totalizador -->
															<%if (cont % 2 == 0) {%>
															<tr bgcolor="#cbe5fe">
															<%} else {%>
															<tr bgcolor="#FFFFFF">
															<%}%>
																	<td colspan="6" bgcolor="#90c7fc" align="center">
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
										
										<tr>
											<td colspan="2" width="100%">
											<table width="100%">
												<tr>
													<td>
													<input type="button"
														onclick="verificarSubmitGuias(document.forms[0].idsGuiaPagamentoDesmarcar);"
														class="bottonRightCol" value="Desmarcar Prescrição" style="width: 150px;">
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
					<td width="20%"><strong>Valor Total dos Débitos Prescritos:</strong></td>
					<td width="80%">
						<font color="#000000" style="background-color:#EFEFEF; border:0; font-size: 14;">
							<%=session.getAttribute("valorTotalDebitosPrescritosImovel")%>
						</font>
					</td>
				</tr>

				<tr>
					<td colspan="2" width="100%">
					<table width="100%">
						<tr>
							<td>
							<input name="button" class="bottonRightCol" value="Voltar"
								type="button" onclick="window.location.href='<html:rewrite page="/exibirAcompanharImovelComDebitosPrescritosAction.do"/>'" align="left" style="width: 80px;">

							<input name="Submit22" class="bottonRightCol" value="Desfazer"
								type="button" onclick="window.location.href='<html:rewrite page="/exibirFiltrarImoveisComDebitosPrescritosAction.do"/>'" align="left" style="width: 80px;">

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
