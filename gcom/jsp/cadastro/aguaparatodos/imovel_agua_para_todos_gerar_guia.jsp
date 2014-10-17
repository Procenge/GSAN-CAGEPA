<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@page isELIgnored="false"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gcom.util.ConstantesSistema"%>
<%@ page import="java.util.Collection" isELIgnored="false"%>
<%@ page import="gcom.cobranca.bean.ContaValoresHelper"	isELIgnored="false"%>
<%@ page import="gcom.faturamento.conta.Conta"%>


<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="GerarGuiaImovelAguaParaTodosActionForm" />
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

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

    var form = document.forms[0];

    if (tipoConsulta == 'imovel') {
      form.matricula.value = codigoRegistro;
      pesquisarImovelAguaParaTodos();
    }
 }
function pesquisarImovelAguaParaTodos() {
	var form = document.forms[0];
	redirecionarSubmit("exibirGerarGuiaImovelAguaParaTodosAction.do?matriculaImovel="+form.matricula.value);	
}

function pesquisarImovelAguaParaTodosEnter(event) {
	var form = document.forms[0];
	validaEnter(event, 'exibirGerarGuiaImovelAguaParaTodosAction.do?matriculaImovel='+form.matricula.value,'matricula');
}

function gerarGuia() {
	var form = document.forms[0];
	form.submit();
	return true;	
}

function excluir() {
	var form = document.forms[0];
	form.flagOperacao.value = '3';
	form.submit();
	return true;	
}
function renovar() {
	var form = document.forms[0];
	form.flagOperacao.value = '4';
	form.submit();
	return true;	
}

function abrirManterContas(){
	var form = document.forms[0];
    
    if (form.matricula == null || form.matricula.value == '') {
		alert('Informe a matrícula do imóvel.');
	} else {
	    var stringUrl = form.matricula.value;
		abrirPopupDeNome('exibirManterContaAction.do?idImovelRequest='+stringUrl, 400, 800, 'ManterContas', 0);
	}	

}
</script>
</head>


<body leftmargin="5" topmargin="5" onload="">
<html:form action="/gerarGuiaImovelAguaParaTodosAction.do"
	name="GerarGuiaImovelAguaParaTodosActionForm"
	type="gcom.gui.cadastro.aguaparatodos.GerarGuiaImovelAguaParaTodosActionForm"
	method="post">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="130" valign="top" class="leftcoltext">
			<div align="center">
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<%@ include file="/jsp/util/informacoes_usuario.jsp"%>

			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>

			<%@ include file="/jsp/util/mensagens.jsp"%>

			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			</div>
			</td>


			<td width="625" valign="top" class="centercoltext">
			
			<table>
				<tr>
					<td></td>
				</tr>
			</table>
			
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Gerar Guia de Pagamento Programa Água para Todos</td>
					<td width="11" valign="top"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>

			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para Gerar a Guia de Pagamento Programa Água para todos, informe os dados abaixo:</td>
				</tr>
				
				<tr>
					<td width="20%">
						<strong>Matrícula do Imóvel:</strong>
					</td>
					<td width="100%">
						<html:hidden property="id"/>
						<logic:equal name="GerarGuiaImovelAguaParaTodosActionForm" property="flagOperacao" value="0">
							<html:text property="matricula" maxlength="9" size="9" tabindex="0" onkeypress="javascript:pesquisarImovelAguaParaTodosEnter(event);"/>
						</logic:equal>
						<logic:notEqual name="GerarGuiaImovelAguaParaTodosActionForm" property="flagOperacao" value="0">
							<html:text property="matricula" maxlength="9" size="9" tabindex="0" disabled="true" onkeypress="javascript:pesquisarImovelAguaParaTodosEnter();"/>
						</logic:notEqual>
						<a href="javascript:abrirPopup('exibirPesquisarImovelAction.do', 400, 800);">
 							<img width="23" height="21" border="0" title="Pesquisar Imóvel."
 								src="<bean:message key="caminho.imagens"/>pesquisa.gif"/></a>
					</td>
				</tr>
				<table width="100%" border="0">
				<tr>
				<td>
				<logic:notEqual name="GerarGuiaImovelAguaParaTodosActionForm" property="flagOperacao" value="0">
					<hr>
					<table width="100%" border="0">
					
						<!-- Dados do Imóvel -->
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
											<td>
												<html:text property="inscricaoFormatadaImovel"
												readonly="true" style="background-color:#EFEFEF; border:0;"
												size="20" />											
                                            </td>
											<td><strong>Perfil do Imóvel:</strong></td>
											<td>
											  	<html:text property="descricaoImovelPerfil"
												readonly="true" style="background-color:#EFEFEF; border:0;"
												size="20" />
											</td>
										</tr>
										<tr>
											<td><strong>Situação de Água:</strong></td>
											<td>
												<html:text property="descricaoLigacaoAguaSituacao"
												readonly="true" style="background-color:#EFEFEF; border:0;"
												size="15" />
											</td>
											<td><strong>Situação de Esgoto:</strong></td>
											<td>
												<html:text property="descricaoLigacaoEsgotoSituacao"
												readonly="true" style="background-color:#EFEFEF; border:0;"
												size="15" />
											</td>
										</tr>
										<!-- Endereço do Imóvel -->
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
													<div class="style9">
													    ${GerarGuiaImovelAguaParaTodosActionForm.enderecoImovel}
														&nbsp;
													</div>
													</td>
												</tr>
											</table>
											</td>
										</tr>

										<!-- Informações do Cliente Usuário -->
										<tr>
											<td colspan="4">
											<table width="100%" align="center" bgcolor="#90c7fc" border="0">
												<tr bordercolor="#79bbfd">
													<td colspan="4" align="center" bgcolor="#79bbfd"><strong>
													Cliente Usuário</strong></td>
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
													da Rela&ccedil;&atilde;o</strong> </font></div>
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
														<tr bgcolor="#FFFFFF">
															<td width="40%" bordercolor="#90c7fc" align="left">
															<div class="style9">
														    <font color="#000000"
																style="font-size: 9px"
																face="Verdana, Arial, Helvetica, sans-serif"> 
															  	<html:hidden property="clienteNome"/>
																<bean:write name="GerarGuiaImovelAguaParaTodosActionForm" property="clienteNome"/>
															</font></div>
															</td>
															<td width="28%" align="left">
															<div class="style9"><font color="#000000"
																style="font-size: 9px"
																face="Verdana, Arial, Helvetica, sans-serif"> 
															  	<html:hidden property="clienteRelacaoTipo"/>
																<bean:write name="GerarGuiaImovelAguaParaTodosActionForm" property="clienteRelacaoTipo"/>
															</font></div>
															</td>
															<td width="32%" align="right"><font color="#000000"
																style="font-size: 9px"
																face="Verdana, Arial, Helvetica, sans-serif"> 
																
																<logic:notEmpty
																name="GerarGuiaImovelAguaParaTodosActionForm" property="clienteCpf">
																  	<html:hidden property="clienteCpf"/>
																	<bean:write name="GerarGuiaImovelAguaParaTodosActionForm" property="clienteCpf"/>
	  															</logic:notEmpty> 
	  															<logic:notEmpty 
	  															name="GerarGuiaImovelAguaParaTodosActionForm" property="clienteCnpj">
																  	<html:hidden property="clienteCnpj"/>
																	<bean:write name="GerarGuiaImovelAguaParaTodosActionForm" property="clienteCnpj"/>
	  														</logic:notEmpty> </font></td>
														</tr>
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





						<!-- Dados da Guia de Pagamento -->
						<tr>
							<td colspan="4">
							<div id="layerHideDadosGuia" style="display: none">
							<table width="100%" border="0" bgcolor="#99CCFF">
								<tr bgcolor="#99CCFF">
									<td align="center"><span class="style2"> <a
										href="javascript:extendeTabela('DadosGuia',true);" />
										<b>Dados da Guia de Pagamento para Inclusão no Programa Água Para Todos</b>
										</a></span></td>
								</tr>
							</table>
							</div>
		
							<div id="layerShowDadosGuia" style="display: block">
							<table width="100%" border="0" bgcolor="#99CCFF">
								<tr bgcolor="#99CCFF">
									<td align="center"><span class="style2"> <a
										href="javascript:extendeTabela('DadosGuia',false);" />
										<b>Dados da Guia de Pagamento para Inclusão no Programa Água Para Todos</b>
										</a></span></td>
								</tr>
								<tr bgcolor="#cbe5fe">
									<td>
									<table width="100%">
										<tr>
											<td width="25%"><strong>Tipo do Débito:</strong></td>
											<td>
												<html:text property="tipoDebito"
												readonly="true" style="background-color:#EFEFEF; border:0;"
												size="20" />											
                                            </td>
											<td><strong>Valor Padrão do Tipo do Débito:</strong></td>
											<td>
											  	<html:text property="valorPadraoTipoDebito"
												readonly="true" style="background-color:#EFEFEF; border:0;"
												size="15" />
											</td>
										</tr>
										<tr>
											<td><strong>Valor Limite do Tipo do Débito:</strong></td>
											<td>
												<html:text property="valorLimiteTipoDebito"
												readonly="true" style="background-color:#EFEFEF; border:0;"
												size="15" />
											</td>
											<td><strong>Valor da Guia:</strong></td>
											<td>
												<html:text property="valorGuia"
												readonly="true" style="background-color:#EFEFEF; border:0;"
												size="15" />
											</td>
										</tr>
										<tr>
											<td><strong>Quantidade de Contas:</strong></td>
											<td>
												<html:text property="quantidadeContas"
												readonly="true" style="background-color:#EFEFEF; border:0;"
												size="15" />
											</td>
											<td><strong>Valor das Contas:</strong></td>
											<td>
												<html:text property="valorTotalConta"
												readonly="true" style="background-color:#EFEFEF; border:0;"
												size="15" />
											</td>
										</tr>
										<!-- Dados das Contas -->
										<tr>
											<td colspan="4">
												<%String cor = "#cbe5fe";%>
												<%cor = "#cbe5fe";%>
												<div id="layerHideDadosContas" style="display: block">
												<table width="100%" border="0" bgcolor="#99CCFF">
												
													<tr bgcolor="#99CCFF">
														<td align="center"><span class="style2"> <a
															href="javascript:extendeTabela('DadosContas',true);" />
															<b>Contas</b>
															</a></span></td>
													</tr>
												</table>
												</div>
							
												<div id="layerShowDadosContas" style="display: none">
												<table width="100%" border="0" bgcolor="#99CCFF">
													<tr bgcolor="#99CCFF">
														<td colspan="9" align="center"><span class="style2"> <a
															href="javascript:extendeTabela('DadosContas',false);" />
															<b>Contas</b>
															</a></span></td>
													</tr>
													<logic:notEmpty name="colecaoContaValores" scope="session">
														<%if (((Collection) session.getAttribute("colecaoContaValores"))
												          .size() <= ConstantesSistema.NUMERO_MAXIMO_REGISTROS_CONTAS_DEBITO) {%>
														<tr bordercolor="#000000">
															<td width="20%" bgcolor="#90c7fc" align="center">
															<div class="style9"><font color="#000000" style="font-size:9px"
																face="Verdana, Arial, Helvetica, sans-serif"> <strong>M&ecirc;s/Ano</strong>
															</font></div>
															</td>
															<td width="7%" bgcolor="#90c7fc" align="center">
															<div class="style9"><font color="#000000" style="font-size:9px"
																face="Verdana, Arial, Helvetica, sans-serif"> <strong>Vencimento</strong>
															</font></div>
															</td>
															<td width="10%" bgcolor="#90c7fc" align="center">
															<div class="style9"><font color="#000000" style="font-size:9px"
																face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor de
															&Aacute;gua </strong> </font></div>
															</td>
															<td width="9%" bgcolor="#90c7fc" align="center">
															<div class="style9"><font color="#000000" style="font-size:9px"
																face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor de
															Esgoto</strong> </font></div>
															</td>
															<td width="8%" bgcolor="#90c7fc" align="center">
															<div class="style9"><font color="#000000" style="font-size:9px"
																face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor dos
															D&eacute;bitos</strong> </font></div>
															</td>
															<td width="8%" bgcolor="#90c7fc" align="center">
															<div class="style9"><font color="#000000" style="font-size:9px"
																face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor dos
															Creditos</strong> </font></div>
															</td>
															
															<td width="10%" bgcolor="#90c7fc" align="center">
															<div class="style9"><font color="#000000" style="font-size:9px" 
															    face="Verdana, Arial, Helvetica, sans-serif"> 
															      <strong>Valor dos Impostos</strong> 
															    </font>
															  </div>
															</td>
															
															<td width="10%" bgcolor="#90c7fc" align="center">
															<div class="style9"><font color="#000000" style="font-size:9px"
																face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor da
															Conta</strong> </font></div>
															</td>
															<td width="10%" bgcolor="#90c7fc" align="center">
															<div class="style9"><font color="#000000" style="font-size:9px"
																face="Verdana, Arial, Helvetica, sans-serif"> <strong>Situação Atual</strong>
															</font></div>
															</td>
														</tr>
														<logic:present name="colecaoContaValores">
															<logic:iterate name="colecaoContaValores"
																id="contavaloreshelper">
																<%if (cor.equalsIgnoreCase("#cbe5fe")) {
																	cor = "#FFFFFF";%>
																<tr bgcolor="#FFFFFF">
																	<%} else {
																	cor = "#cbe5fe";%>
																<tr bgcolor="#cbe5fe">
																	<%}%>
																	
																	<td align="left">
																		<logic:notEmpty name="contavaloreshelper" property="conta">
																		
																														
																			<logic:empty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																				<div align="center">
																					<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																						<a href="javascript:abrirPopup('exibirConsultarContaAction.do?contaID=<bean:define name="contavaloreshelper" property="conta" id="conta" /><bean:write name="conta" property="id" />&tipoConsulta=conta', 600, 800);">
																						<bean:define name="contavaloreshelper" property="conta" id="conta" /> 
																						<bean:write name="conta" property="formatarAnoMesParaMesAno" /></a> 
																					</font>
																				</div>
																			</logic:empty>
							
																			<logic:notEmpty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																				<div align="center">
																					<font color="#ff0000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																						<a href="javascript:abrirPopup('exibirConsultarContaAction.do?contaID=<bean:define name="contavaloreshelper" property="conta" id="conta" /><bean:write name="conta" property="id" />&tipoConsulta=conta', 600, 800);">
																						<bean:define name="contavaloreshelper" property="conta" id="conta" /> 
																						<font color="#ff0000"><bean:write name="conta" property="formatarAnoMesParaMesAno" /></font></a> 
																					</font>
																				</div>
																			</logic:notEmpty>
																		
																			
																		</logic:notEmpty>
																	</td>
																	<td align="left">
																		<logic:notEmpty name="contavaloreshelper" property="conta">
																			<logic:empty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																				<div align="center">
																					<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																						<bean:define name="contavaloreshelper" property="conta" id="conta" /> 
																						<bean:write name="conta" property="dataVencimentoConta" formatKey="date.format" /> 
																					</font>
																				</div>
																			</logic:empty>
							
																			<logic:notEmpty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																				<div align="center">
																					<font color="#ff0000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																						<bean:define name="contavaloreshelper" property="conta" id="conta" /> 
																						<bean:write name="conta" property="dataVencimentoConta" formatKey="date.format" /> 
																					</font>
																				</div>
																			</logic:notEmpty>
																		
																		</logic:notEmpty>
																	</td>
																	<td align="right">
																		<logic:notEmpty name="contavaloreshelper" property="conta">
																														
																			<logic:empty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																				<div align="right">
																					<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																						<bean:define name="contavaloreshelper" property="conta" id="conta" /> 
																						<bean:write name="conta" property="valorAgua" formatKey="money.format" />
																					</font>
																				</div>
																			</logic:empty>
							
																			<logic:notEmpty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																				<div align="right">
																					<font color="#ff0000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																						<bean:define name="contavaloreshelper" property="conta" id="conta" /> 
																						<bean:write name="conta" property="valorAgua" formatKey="money.format" />
																					</font>
																				</div>
																			</logic:notEmpty>
																		
																			
																		</logic:notEmpty>
																	</td>
																	<td align="rigth">
																		<logic:empty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																			<div align="right">
																				<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																					<logic:notEmpty name="contavaloreshelper" property="conta">
																						<bean:define name="contavaloreshelper" property="conta" id="conta" />
																						<bean:write name="conta" property="valorEsgoto" formatKey="money.format" />
																					</logic:notEmpty> 
																				</font>
																			</div>
																		</logic:empty>
							
																		<logic:notEmpty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																			<div align="right">
																				<font color="#ff0000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																					<logic:notEmpty name="contavaloreshelper" property="conta">
																						<bean:define name="contavaloreshelper" property="conta" id="conta" />
																						<bean:write name="conta" property="valorEsgoto" formatKey="money.format" />
																					</logic:notEmpty> 
																				</font>
																			</div>	
																		</logic:notEmpty>
																	</td>
																	
																	<td align="right">
																		<logic:notEmpty name="contavaloreshelper" property="conta">
																															
																			<logic:empty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																				<div align="right">
																					<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																						<logic:notEqual name="contavaloreshelper" property="conta.debitos" value="0">
																							<a href="javascript:abrirPopup('exibirConsultarDebitoCobradoAction.do?contaID=<bean:define name="contavaloreshelper" property="conta" id="conta" /><bean:write name="conta" property="id" />&tipoConsulta=conta', 600, 800);">
																							<bean:write name="contavaloreshelper" property="conta.debitos" formatKey="money.format" /> </a>
																						</logic:notEqual> 
																						<logic:equal name="contavaloreshelper" property="conta.debitos" value="0">
																							<bean:write name="contavaloreshelper" property="conta.debitos" formatKey="money.format" />
																						</logic:equal> 
																					</font>
																				</div>
																			</logic:empty>
							
																			<logic:notEmpty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																				<div align="right">
																					<font color="#ff0000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																						<logic:notEqual name="contavaloreshelper" property="conta.debitos" value="0">
																							<a href="javascript:abrirPopup('exibirConsultarDebitoCobradoAction.do?contaID=<bean:define name="contavaloreshelper" property="conta" id="conta" /><bean:write name="conta" property="id" />&tipoConsulta=conta', 600, 800);">
																							<font color="#ff0000"><bean:write name="contavaloreshelper" property="conta.debitos" formatKey="money.format" /></font> </a>
																						</logic:notEqual> 
																						<logic:equal name="contavaloreshelper" property="conta.debitos" value="0">
																							<font color="#ff0000"><bean:write name="contavaloreshelper" property="conta.debitos" formatKey="money.format" /></font>
																						</logic:equal> 
																					</font>
																				</div>
																			</logic:notEmpty>
																			
																			
																		</logic:notEmpty>
																	</td>
																	
																	<td align="right">
																		<logic:notEmpty name="contavaloreshelper" property="conta">
																			<logic:empty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																				<div align="right">
																					<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																						<logic:notEqual name="contavaloreshelper" property="conta.valorCreditos" value="0">	
																							<a href="javascript:abrirPopup('exibirConsultarCreditoRealizadoAction.do?contaID=<bean:define name="contavaloreshelper"	property="conta" id="conta" />
																								<bean:write name="conta" property="id" />&tipoConsulta=conta', 600, 800);">
																								<bean:write name="contavaloreshelper" property="conta.valorCreditos" formatKey="money.format" />
																							</a>
																						</logic:notEqual> 
																						<logic:equal name="contavaloreshelper" property="conta.valorCreditos" value="0">
																							<bean:write name="contavaloreshelper" property="conta.valorCreditos" formatKey="money.format" />
																						</logic:equal> 
																					</font>
																				</div>
																			</logic:empty>
							
																			<logic:notEmpty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																				<div align="right">
																					<font color="#ff0000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																						<logic:notEqual name="contavaloreshelper" property="conta.valorCreditos" value="0">	
																							<a href="javascript:abrirPopup('exibirConsultarCreditoRealizadoAction.do?contaID=<bean:define name="contavaloreshelper"	property="conta" id="conta" />
																								<bean:write name="conta" property="id" />&tipoConsulta=conta', 600, 800);">
																								<font color="#ff0000"><bean:write name="contavaloreshelper" property="conta.valorCreditos" formatKey="money.format" /></font>
																							</a>
																						</logic:notEqual> 
																						<logic:equal name="contavaloreshelper" property="conta.valorCreditos" value="0">
																							<font color="#ff0000"><bean:write name="contavaloreshelper" property="conta.valorCreditos" formatKey="money.format" /></font>
																						</logic:equal> 
																					</font>
																				</div>
																			</logic:notEmpty>
																		</logic:notEmpty>
																	</td>
																	
																	<td align="right">
																		<logic:notEmpty name="contavaloreshelper" property="conta">
																														
																			<logic:empty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																				<div align="right">
																					<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																						<bean:define name="contavaloreshelper" property="conta" id="conta" /> 
																						<bean:write name="conta" property="valorImposto" formatKey="money.format" />
																					</font>
																				</div>
																			</logic:empty> 
							
																			<logic:notEmpty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																				<div align="right">
																					<font color="#ff0000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																						<bean:define name="contavaloreshelper" property="conta" id="conta" /> 
																						<bean:write name="conta" property="valorImposto" formatKey="money.format" />
																					</font>
																				</div>
																			</logic:notEmpty>
																			
																		</logic:notEmpty>
																	</td>
																	
																	<td align="right">
																		<logic:notEmpty name="contavaloreshelper" property="conta">
																			<logic:empty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																				<div align="right">
																					<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																						<bean:define name="contavaloreshelper" property="conta" id="conta" /> 
																						<bean:write name="conta" property="valorTotal" formatKey="money.format" />
																					</font>
																				</div>
																			</logic:empty>
							
																			<logic:notEmpty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																				<div align="right">
																					<font color="#ff0000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																						<bean:define name="contavaloreshelper" property="conta" id="conta" /> 
																						<bean:write name="conta" property="valorTotal" formatKey="money.format" />
																					</font>
																				</div>
																			</logic:notEmpty>
																		</logic:notEmpty>
																	</td>

																	<td align="left">
																		<logic:notEmpty name="contavaloreshelper" property="conta">
																			<logic:empty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																				<div align="left">
																				<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																					<bean:define name="contavaloreshelper" property="conta" id="conta" /> 
																					<bean:define name="conta" property="debitoCreditoSituacaoAtual" id="debitoCreditoSituacaoAtual" /> 
																					<bean:write name="debitoCreditoSituacaoAtual" property="descricaoAbreviada" /> 
																				</font>
																			</div>
																			</logic:empty>
							
																			<logic:notEmpty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																				<div align="left">
																				<font color="#ff0000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																					<bean:define name="contavaloreshelper" property="conta" id="conta" /> 
																					<bean:define name="conta" property="debitoCreditoSituacaoAtual" id="debitoCreditoSituacaoAtual" /> 
																					<bean:write name="debitoCreditoSituacaoAtual" property="descricaoAbreviada" /> 
																				</font>
																			</div>
																			</logic:notEmpty>
																		</logic:notEmpty>
																	</td>
																</tr>
															</logic:iterate>
															
															<logic:notEmpty name="colecaoContaValores">
																<%if (cor.equalsIgnoreCase("#cbe5fe")) {
																	cor = "#FFFFFF";%>
																<tr bgcolor="#FFFFFF">
																	<%} else {
																cor = "#cbe5fe";%>
																<tr bgcolor="#cbe5fe">
																	<%}%>
																	<td bgcolor="#90c7fc" align="center">
																		<div class="style9" align="center">
																			<font color="#000000" style="font-size:9px"
																		face="Verdana, Arial, Helvetica, sans-serif"> <strong>Total</strong>
																	</font></div>
																	</td>
																	<td align="left">
																	
																		<%=((Collection) session.getAttribute("colecaoContaValores")).size()%>
																			&nbsp;
																			doc(s)
																	</td>
																	<td align="right">
																	<div align="right"><font color="#000000" style="font-size:9px"
																		face="Verdana, Arial, Helvetica, sans-serif"> 
																		<html:hidden property="valorTotalAgua"/>
																		<bean:write name="GerarGuiaImovelAguaParaTodosActionForm" property="valorTotalAgua"/>
																	</font></div>
																	</td>
																	<td align="rigth">
																	<div align="right"><font color="#000000" style="font-size:9px"
																		face="Verdana, Arial, Helvetica, sans-serif">
																		<html:hidden property="valorTotalEsgoto"/>
																		<bean:write name="GerarGuiaImovelAguaParaTodosActionForm" property="valorTotalEsgoto"/>
																	</font></div>
																	</td>
																	<td align="right">
																	<div align="right"><font color="#000000" style="font-size:9px"
																		face="Verdana, Arial, Helvetica, sans-serif"> 
																		<html:hidden property="valorTotalDebito"/>
																		<bean:write name="GerarGuiaImovelAguaParaTodosActionForm" property="valorTotalDebito"/>
																	</font></div>
																	</td>
																	<td align="right">
																	<div align="right"><font color="#000000" style="font-size:9px"
																		face="Verdana, Arial, Helvetica, sans-serif"> 
																		<html:hidden property="valorTotalCredito"/>
																		<bean:write name="GerarGuiaImovelAguaParaTodosActionForm" property="valorTotalCredito"/>
																	</font></div>
																	</td>
																	<td align="right">
																	<div align="right"><font color="#000000" style="font-size:9px"
																		face="Verdana, Arial, Helvetica, sans-serif"> 
																		<html:hidden property="valorTotalImposto"/>
																		<bean:write name="GerarGuiaImovelAguaParaTodosActionForm" property="valorTotalImposto"/>
																	</font></div>
																	</td>
																	<td align="right">
																	<div align="right"><font color="#000000" style="font-size:9px"
																		face="Verdana, Arial, Helvetica, sans-serif"> 
																		<html:hidden property="valorTotalConta"/>
																		<bean:write name="GerarGuiaImovelAguaParaTodosActionForm" property="valorTotalConta"/>
																	</font></div>
																	</td>
																	<td align="left">
																	<div align="left"><font color="#000000" style="font-size:9px"
																		face="Verdana, Arial, Helvetica, sans-serif"> </font></div>
																	</td>
																</tr>
															</logic:notEmpty>
														</logic:present>
														<%} else {%>
														<tr bordercolor="#000000">
															<td width="9%" bgcolor="#90c7fc">
															<div align="center" class="style9"><font color="#000000"
																style="font-size:9px"
																face="Verdana, Arial, Helvetica, sans-serif"> <strong>M&ecirc;s/Ano</strong>
															</font></div>
															</td>
															<td width="12%" bgcolor="#90c7fc">
															<div align="center" class="style9"><font color="#000000"
																style="font-size:9px"
																face="Verdana, Arial, Helvetica, sans-serif"> <strong>Vencimento</strong>
															</font></div>
															</td>
															<td width="8%" bgcolor="#90c7fc">
															<div align="center" class="style9"><font color="#000000"
																style="font-size:9px"
																face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor de
															&Aacute;gua </strong> </font></div>
															</td>
															<td width="8%" bgcolor="#90c7fc">
															<div align="center" class="style9"><font color="#000000"
																style="font-size:9px"
																face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor de
															Esgoto</strong> </font></div>
															</td>
															<td width="8%" bgcolor="#90c7fc">
															<div align="center" class="style9"><font color="#000000"
																style="font-size:9px"
																face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor dos
															<br>
															D&eacute;bitos</strong> </font></div>
															</td>
															<td width="10%" bgcolor="#90c7fc">
															<div align="center" class="style9"><font color="#000000"
																style="font-size:9px"
																face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor dos
															Creditos</strong> </font></div>
															</td>
															
															
															<td width="10%" bgcolor="#90c7fc">
															  <div align="center" class="style9">
															    <font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
															      <strong>Valor dos	Impostos</strong> 
															    </font>
															  </div>
															</td>
															
															
															
															<td width="8%" bgcolor="#90c7fc">
															<div align="center" class="style9"><font color="#000000"
																style="font-size:9px"
																face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor da
															Conta</strong> </font></div>
															</td>
															<td width="8%" bgcolor="#90c7fc">
															<div align="center" class="style9"><font color="#000000"
																style="font-size:9px"
																face="Verdana, Arial, Helvetica, sans-serif"> <strong>Situação Atual</strong>
															</font></div>
															</td>
														</tr>
														
														<tr>
															<td height="100" colspan="10">
															<div style="width: 100%; height: 100%; overflow: auto;">
															<table width="100%">
																<logic:present name="colecaoContaValores">
																	<logic:iterate name="colecaoContaValores"
																		id="contavaloreshelper">
																		<%if (cor.equalsIgnoreCase("#cbe5fe")) {
												cor = "#FFFFFF";%>
																		<tr bgcolor="#FFFFFF">
																			<%} else {
												cor = "#cbe5fe";%>
																		<tr bgcolor="#cbe5fe">
																			<%}%>
																			<td width="9%" align="left">
																				<div align="left" class="style9">
																				
																				<logic:empty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																					<font color="#000000"
																						style="font-size:9px"
																						face="Verdana, Arial, Helvetica, sans-serif"> 
																					<logic:notEmpty
																						name="contavaloreshelper" property="conta">
																						<a
																							href="javascript:abrirPopup('exibirConsultarContaAction.do?contaID=<bean:define name="contavaloreshelper" property="conta" id="conta" /><bean:write name="conta" property="id" />&tipoConsulta=conta', 600, 800);">
																						<bean:define name="contavaloreshelper" property="conta"
																							id="conta" /> <bean:write name="conta"
																							property="formatarAnoMesParaMesAno" /></a>
																					</logic:notEmpty> </font>
																				</logic:empty>
								
																				<logic:notEmpty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																					<font color="#ff0000"
																					style="font-size:9px"
																					face="Verdana, Arial, Helvetica, sans-serif">
																					 <logic:notEmpty name="contavaloreshelper" property="conta">
																					<a
																						href="javascript:abrirPopup('exibirConsultarContaAction.do?contaID=<bean:define name="contavaloreshelper" property="conta" id="conta" /><bean:write name="conta" property="id" />&tipoConsulta=conta', 600, 800);">
																					<bean:define name="contavaloreshelper" property="conta"
																						id="conta" /> <font color="#ff0000"><bean:write name="conta"
																						property="formatarAnoMesParaMesAno" /></font> </a>
																					</logic:notEmpty> </font>
																				</logic:notEmpty>
																				</div>
																			</td>
																			
																			<td width="12%" align="left">
																				<logic:empty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																					<div align="left" class="style9"><font color="#000000"
																					style="font-size:9px"
																					face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
																					name="contavaloreshelper" property="conta">
																					<bean:define name="contavaloreshelper" property="conta"
																						id="conta" />
																					<bean:write name="conta" property="dataVencimentoConta"
																						formatKey="date.format" />
																				</logic:notEmpty> </font></div>
																				</logic:empty>
								
																				<logic:notEmpty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																					<div align="left" class="style9"><font color="#ff0000"
																					style="font-size:9px"
																					face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
																					name="contavaloreshelper" property="conta">
																					<bean:define name="contavaloreshelper" property="conta"
																						id="conta" />
																					<bean:write name="conta" property="dataVencimentoConta"
																						formatKey="date.format" />
																					</logic:notEmpty> </font></div>
																				</logic:notEmpty>
																			</td>
																			
																			<td width="8%" align="right">
																															
																				<logic:empty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																					<div align="right" class="style9"><font color="#000000"
																							style="font-size:9px"
																							face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
																							name="contavaloreshelper" property="conta">
																							<bean:define name="contavaloreshelper" property="conta"
																								id="conta" />
																							<bean:write name="conta" property="valorAgua"
																								formatKey="money.format" />
																						</logic:notEmpty> </font></div>
																				</logic:empty>
								
																				<logic:notEmpty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																					<div align="right" class="style9"><font color="#ff0000"
																						style="font-size:9px"
																						face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
																						name="contavaloreshelper" property="conta">
																						<bean:define name="contavaloreshelper" property="conta"
																							id="conta" />
																						<bean:write name="conta" property="valorAgua"
																							formatKey="money.format" />
																					</logic:notEmpty> </font></div>
																				</logic:notEmpty>
																			</td>
																			
																			<td width="8%" align="right">
																				<logic:empty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																					<div align="right" class="style9"><font color="#000000"
																					style="font-size:9px"
																					face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
																					name="contavaloreshelper" property="conta">
																					<bean:define name="contavaloreshelper" property="conta"
																						id="conta" />
																					<bean:write name="conta" property="valorEsgoto"
																						formatKey="money.format" />
																				</logic:notEmpty> </font></div>
																				</logic:empty>
								
																				<logic:notEmpty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																					<div align="right" class="style9"><font color="#ff0000"
																					style="font-size:9px"
																					face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
																					name="contavaloreshelper" property="conta">
																					<bean:define name="contavaloreshelper" property="conta"
																						id="conta" />
																					<bean:write name="conta" property="valorEsgoto"
																						formatKey="money.format" />
																				</logic:notEmpty> </font></div>
																				</logic:notEmpty>
																			</td>
																			
																			<td width="8%" align="right">
																															
																			<logic:empty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																	<div align="right" class="style9"><font color="#000000"
																					style="font-size:9px"
																					face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
																					name="contavaloreshelper" property="conta">
																					<logic:notEqual name="contavaloreshelper"
																						property="conta.debitos" value="0">
																						<a
																							href="javascript:abrirPopup('exibirConsultarDebitoCobradoAction.do?contaID=<bean:define name="contavaloreshelper" property="conta" id="conta" /><bean:write name="conta" property="id" />&tipoConsulta=conta', 600, 800);">
																						<bean:write name="contavaloreshelper"
																							property="conta.debitos" formatKey="money.format" /> </a>
																					</logic:notEqual>
																					<logic:equal name="contavaloreshelper"
																						property="conta.debitos" value="0">
																						<bean:write name="contavaloreshelper"
																							property="conta.debitos" formatKey="money.format" />
																					</logic:equal>
																				</logic:notEmpty> </font></div>
																			</logic:empty>
							
																			<logic:notEmpty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																				<div align="right" class="style9"><font color="#ff0000"
																					style="font-size:9px"
																					face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
																					name="contavaloreshelper" property="conta">
																					<logic:notEqual name="contavaloreshelper"
																						property="conta.debitos" value="0">
																						<a
																							href="javascript:abrirPopup('exibirConsultarDebitoCobradoAction.do?contaID=<bean:define name="contavaloreshelper" property="conta" id="conta" /><bean:write name="conta" property="id" />&tipoConsulta=conta', 600, 800);">
																						<font color="#ff0000"><bean:write name="contavaloreshelper"
																							property="conta.debitos" formatKey="money.format" /> </font></a>
																					</logic:notEqual>
																					<logic:equal name="contavaloreshelper"
																						property="conta.debitos" value="0">
																						<bean:write name="contavaloreshelper"
																							property="conta.debitos" formatKey="money.format" />
																					</logic:equal>
																				</logic:notEmpty> </font></div>
																			</logic:notEmpty>
																			
																			
																				
																			</td>
																			<td width="10%" align="right">
																															
																			<logic:empty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																			<div align="right" class="style9"><font color="#000000"
																				style="font-size:9px"
																				face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
																				name="contavaloreshelper" property="conta">
																				<logic:notEqual name="contavaloreshelper"
																					property="conta.valorCreditos" value="0">
																					<a
																						href="javascript:abrirPopup('exibirConsultarCreditoRealizadoAction.do?contaID=<bean:define name="contavaloreshelper"	property="conta" id="conta" /><bean:write name="conta" property="id" />&tipoConsulta=conta', 600, 800);">
																					<bean:write name="contavaloreshelper"
																						property="conta.valorCreditos" formatKey="money.format" />
																					</a>
																				</logic:notEqual>
																				<logic:equal name="contavaloreshelper"
																					property="conta.valorCreditos" value="0">
																					<bean:write name="contavaloreshelper"
																						property="conta.valorCreditos" formatKey="money.format" />
																				</logic:equal>
																			</logic:notEmpty> </font></div>
																			</logic:empty>
							
																			<logic:notEmpty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																				<div align="right" class="style9"><font color="#ff0000"
																				style="font-size:9px"
																				face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
																				name="contavaloreshelper" property="conta">
																				<logic:notEqual name="contavaloreshelper"
																					property="conta.valorCreditos" value="0">
																					<a
																						href="javascript:abrirPopup('exibirConsultarCreditoRealizadoAction.do?contaID=<bean:define name="contavaloreshelper"	property="conta" id="conta" /><bean:write name="conta" property="id" />&tipoConsulta=conta', 600, 800);">
																					<font color="#ff0000"><bean:write name="contavaloreshelper"
																						property="conta.valorCreditos" formatKey="money.format" /></font>
																					</a>
																				</logic:notEqual>
																				<logic:equal name="contavaloreshelper"
																					property="conta.valorCreditos" value="0">
																					<bean:write name="contavaloreshelper"
																						property="conta.valorCreditos" formatKey="money.format" />
																				</logic:equal>
																			</logic:notEmpty> </font></div>
																			</logic:notEmpty>
																			</td>
																			
																			<td width="10%" align="right">
																															
																				<logic:empty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																					<div align="right" class="style9">
																					  <font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																					    <logic:notEmpty	name="contavaloreshelper" property="conta">
																							<bean:define name="contavaloreshelper" property="conta"	id="conta" />
																							<bean:write name="conta" property="valorImposto" formatKey="money.format" />
																						</logic:notEmpty> 
																					  </font>
																					</div>
																				</logic:empty>
								
																				<logic:notEmpty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																					<div align="right" class="style9">
																					  <font color="#ff0000"	style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
																					    <logic:notEmpty	name="contavaloreshelper" property="conta">
																						  <bean:define name="contavaloreshelper" property="conta"	id="conta" />
																						  <bean:write name="conta" property="valorImposto" formatKey="money.format" />
																					    </logic:notEmpty> 
																					  </font>
																					</div>
																				</logic:notEmpty>
																				
																			</td>
																			
																			<td width="8%" align="right">
																															
																				<logic:empty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																				<div align="right" class="style9"><font color="#000000"
																					style="font-size:9px"
																					face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
																					name="contavaloreshelper" property="conta">
																					<bean:define name="contavaloreshelper" property="conta"
																						id="conta" />
																					<bean:write name="conta" property="valorTotal"
																						formatKey="money.format" />
																				</logic:notEmpty> </font></div>
																				</logic:empty>
								
																				<logic:notEmpty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																					<div align="right" class="style9"><font color="#ff0000"
																					style="font-size:9px"
																					face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
																					name="contavaloreshelper" property="conta">
																					<bean:define name="contavaloreshelper" property="conta"
																						id="conta" />
																					<bean:write name="conta" property="valorTotal"
																						formatKey="money.format" />
																				</logic:notEmpty> </font></div>
																				</logic:notEmpty>
																			</td>
																			

																			<td width="7%" align="left">
																															
																			<logic:empty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																	<div align="left" class="style9"><font color="#000000"
																				style="font-size:9px"
																				face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
																				name="contavaloreshelper" property="conta">
																				<bean:define name="contavaloreshelper" property="conta"
																					id="conta" />
																				<bean:define name="conta"
																					property="debitoCreditoSituacaoAtual"
																					id="debitoCreditoSituacaoAtual" />
																				<bean:write name="debitoCreditoSituacaoAtual"
																					property="descricaoAbreviada" />
																			</logic:notEmpty> </font></div>
																			</logic:empty>
							
																			<logic:notEmpty name="contavaloreshelper" property="conta.contaMotivoRevisao">
																				<div align="left" class="style9"><font color="#ff0000"
																				style="font-size:9px"
																				face="Verdana, Arial, Helvetica, sans-serif"> <logic:notEmpty
																				name="contavaloreshelper" property="conta">
																				<bean:define name="contavaloreshelper" property="conta"
																					id="conta" />
																				<bean:define name="conta"
																					property="debitoCreditoSituacaoAtual"
																					id="debitoCreditoSituacaoAtual" />
																				<bean:write name="debitoCreditoSituacaoAtual"
																					property="descricaoAbreviada" />
																			</logic:notEmpty> </font></div>
																			</logic:notEmpty>																																		
																			
																			</td>
																		</tr>
																	</logic:iterate>
																	<logic:notEmpty name="colecaoContaValores">
																		<%if (cor.equalsIgnoreCase("#cbe5fe")) {
												cor = "#FFFFFF";%>
																		<tr bgcolor="#FFFFFF">
																			<%} else {
												cor = "#cbe5fe";%>
																		<tr bgcolor="#cbe5fe">
																			<%}%>
																			<td bgcolor="#90c7fc" align="center">
																			<div class="style9" align="center"><font color="#000000"
																				style="font-size:9px"
																				face="Verdana, Arial, Helvetica, sans-serif"> <strong>Total</strong>
																			</font></div>
																			</td>
																			<td align="left">
																			<%=((Collection) session.getAttribute("colecaoContaValores")).size()%>
																			&nbsp;
																			doc(s)
																			</td>
																			<td align="right">
																			<div align="right"><font color="#000000" style="font-size:9px"
																				face="Verdana, Arial, Helvetica, sans-serif"> 
																				<html:hidden property="valorTotalAgua"/>
																				<bean:write name="GerarGuiaImovelAguaParaTodosActionForm" property="valorTotalAgua"/>
																			</font></div>
																			</td>
																			<td align="rigth">
																			<div align="right"><font color="#000000" style="font-size:9px"
																				face="Verdana, Arial, Helvetica, sans-serif">
																				<html:hidden property="valorTotalEsgoto"/>
																				<bean:write name="GerarGuiaImovelAguaParaTodosActionForm" property="valorTotalEsgoto"/>
																			</font></div>
																			</td>
																			<td align="right">
																			<div align="right"><font color="#000000" style="font-size:9px"
																				face="Verdana, Arial, Helvetica, sans-serif"> 
																				<html:hidden property="valorTotalDebito"/>
																				<bean:write name="GerarGuiaImovelAguaParaTodosActionForm" property="valorTotalDebito"/>
																			</font></div>
																			</td>
																			<td align="right">
																			<div align="right"><font color="#000000" style="font-size:9px"
																				face="Verdana, Arial, Helvetica, sans-serif"> 
																				<html:hidden property="valorTotalCredito"/>
																				<bean:write name="GerarGuiaImovelAguaParaTodosActionForm" property="valorTotalCredito"/>
																			</font></div>
																			</td>
																			<td align="right">
																			<div align="right"><font color="#000000" style="font-size:9px"
																				face="Verdana, Arial, Helvetica, sans-serif"> 
																				<html:hidden property="valorTotalImposto"/>
																				<bean:write name="GerarGuiaImovelAguaParaTodosActionForm" property="valorTotalImposto"/>
																			</font></div>
																			</td>
																			<td align="right">
																			<div align="right"><font color="#000000" style="font-size:9px"
																				face="Verdana, Arial, Helvetica, sans-serif"> 
																				<html:hidden property="valorTotalConta"/>
																				<bean:write name="GerarGuiaImovelAguaParaTodosActionForm" property="valorTotalConta"/>
																			</font></div>
																			</td>
																			<td align="left">
																			<div align="left"><font color="#000000"
																				style="font-size:9px"
																				face="Verdana, Arial, Helvetica, sans-serif"> </font></div>
																			</td>
																		</tr>
																	</logic:notEmpty>
																</logic:present>
															</table>
															</div>
															</td>
														</tr>
														<%}
							
										%>
													</logic:notEmpty>													
												</table>
                                             </div>
											</td>
										</tr>

									</table>
									</td>
								</tr>
							</table>
							</div>
							</td>
						</tr>

					</table>
					<hr>
					</logic:notEqual>
	
														
						<tr>
							<td width="100%" colspan="2">
							<div align="center"><font color="#FF0000">*</font> Campos obrigatórios</div>
							<table width="100%" border="0">
								<tr>
									<td width="60%" align="left">
										<input type="button"
											name="ButtonCancelar" class="bottonRightCol" value="Voltar"
											onClick="javascript:history.back();">
	
										<input type="button" name="ButtonReset" class="bottonRightCol" value="Desfazer" 
											onclick="window.location.href='exibirGerarGuiaImovelAguaParaTodosAction.do?desfazer=S&reloadPage=1&idImovel=<bean:write 
											name="GerarGuiaImovelAguaParaTodosActionForm" property="matricula" />';">
	
										<input type="button" name="ButtonCancelar"
											class="bottonRightCol" value="Cancelar"
											onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
									</td>
									<logic:notEqual name="GerarGuiaImovelAguaParaTodosActionForm" property="flagOperacao" value="0">
										<td align="right"><input type="button" name="Button"
											class="bottonRightCol" value="Gerar"
											onclick="javascript:gerarGuia();" tabindex="7" />
										</td>
									</logic:notEqual>
								</tr>
							</table>
							</td>
						</tr>

					</td>
				</tr>
				</table>
			</table>
			</td>
		</tr>
	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>
	
</html:form>



</body>
</html:html>