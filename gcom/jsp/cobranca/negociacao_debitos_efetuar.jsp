<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro"%>
<%@ page import="gcom.cadastro.cliente.ClienteImovel"%>
<%@ page import="gcom.cadastro.cliente.ClienteTipo"%>
<%@ page import="gcom.cobranca.bean.NegociacaoDebitoOpcoesRDHelper"%>
<%@ page import="gcom.util.ConstantesSistema"%>
<%@ page import="gcom.util.Util"%>

<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<html:javascript staticJavascript="false"
	formName="EfetuarNegociacaoDebitosActionForm" />

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">
	function limparImovel(){
		var form = document.forms[0];
		form.inscricaoImovel.value = "";
		form.situacaoAgua.value = "";
		form.situacaoEsgoto.value = "";
		form.numeroQuadra.value = "";
	}

	function desfazer(){
	   	var form = document.forms[0];
		form.action = 'exibirEfetuarNegociacaoDebitosAction.do?&desfazer=S'
		form.submit();
	}

	function reload(){
		var form = document.forms[0];
		form.action = "exibirEfetuarNegociacaoDebitosAction.do";
		form.submit();
	} 
</script>

<logic:equal name="EfetuarNegociacaoDebitosActionForm" property="indicadorPessoaFisicaJuridica" value="<%=ClienteTipo.INDICADOR_PESSOA_FISICA.toString()%>">
	<script>
	function required(){
		this.aa = new Array("idImovel", "É necessário informar o Imóvel.", new Function ("varName", " return this[varName];"));
		this.ab = new Array("cpfCnpjCliente", "É necessário informar o CPF do cliente usuário.", new Function ("varName", " return this[varName];"));
		this.ac = new Array("emailCliente", "É necessário informar o e-mail do cliente usuário.", new Function ("varName", " return this[varName];"));
		this.ad = new Array("negociacaoIdRD", "É necessário selecionar uma proposta de negociação.", new Function ("varName", " return this[varName];"));
	}

	function email(){
		this.aa = new Array("emailCliente", "E-mail não informado corretamente.", new Function ("varName", " return this[varName];"));
	}

	function cpf(){
		this.aa = new Array("cpfCnpjCliente", "CPF inválido.", new Function ("varName", " return this[varName];"));
	}

	function concluir(){
		var form = document.forms[0];

		if(validateRequired(form) && (!isBrancoOuNulo(form.cpfCnpjClienteAntes.value) || validateCpf(form)) && validateEmail(form)){
			form.action = 'efetuarNegociacaoDebitosAction.do';
			form.submit();
		}
	}
	</script>
</logic:equal>

<logic:notEqual name="EfetuarNegociacaoDebitosActionForm" property="indicadorPessoaFisicaJuridica" value="<%=ClienteTipo.INDICADOR_PESSOA_FISICA.toString()%>">
	<script>
	function required(){
		this.aa = new Array("idImovel", "É necessário informar o Imóvel.", new Function ("varName", " return this[varName];"));
		this.ab = new Array("cpfCnpjCliente", "É necessário informar o CNPJ do cliente usuário.", new Function ("varName", " return this[varName];"));
		this.ac = new Array("emailCliente", "É necessário informar o e-mail do cliente usuário.", new Function ("varName", " return this[varName];"));
		this.ad = new Array("negociacaoIdRD", "É necessário selecionar uma proposta de negociação.", new Function ("varName", " return this[varName];"));
	}

	function email(){
		this.aa = new Array("emailCliente", "E-mail não informado corretamente.", new Function ("varName", " return this[varName];"));
	}

	function cnpj(){
		this.aa = new Array("cpfCnpjCliente", "CNPJ inválido.", new Function ("varName", " return this[varName];"));
	}

	function concluir(){
		var form = document.forms[0];

		if(validateRequired(form) && (!isBrancoOuNulo(form.cpfCnpjClienteAntes.value) || validateCnpj(form)) && validateEmail(form)){
			form.action = 'efetuarNegociacaoDebitosAction.do';
			form.submit();
		}
	}
	</script>
</logic:notEqual>

</head>

<body leftmargin="5" topmargin="5">

<html:form action="/exibirEfetuarNegociacaoDebitosAction"
	name="EfetuarNegociacaoDebitosActionForm"
	type="gcom.gui.cobranca.EfetuarNegociacaoDebitosActionForm"
	method="post">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="130" valign="top" class="leftcoltext">
			<div align="center">
				<%@ include file="/jsp/util/informacoes_usuario.jsp"%>
				<%@ include	file="/jsp/util/mensagens.jsp"%></div>
			</td>

			<td width="625" valign="top" class="centercoltext">

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Efetuar Negociação de Débitos</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>

			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para efetuar uma negociação de débitos, informe os dados abaixo:</td>
				</tr>
			</table>

			<p>&nbsp;</p>

			<table width="100%" border="0">
				<html:hidden property="idImovelAntes"/>

				<!-- Imóvel -->
				<tr>
					<td colspan="4">
					<table width="100%" align="center" bgcolor="#99CCFF" border="0">
						<tr>
							<td align="center"><strong>Dados do Imóvel</strong></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td width="100%" align="center">
							<table width="100%" border="0">
								<tr>
									<td bordercolor="#000000" width="25%"><strong>Imóvel:<font color="#FF0000">*</font></strong></td>
									<td colspan="3">
										<html:text property="idImovel" maxlength="9" size="9"
											onkeypress="limparImovel();
												validaEnter(event, 'exibirEfetuarNegociacaoDebitosAction.do?objetoConsulta=1', 'idImovel');
												return isCampoNumerico(event);"/> <a href="javascript:abrirPopup('exibirPesquisarImovelAction.do', 400, 800);"><img 
											width="23" height="21" src="<bean:message key="caminho.imagens"/>pesquisa.gif"	border="0" /></a>

										<logic:present name="imovelNaoEncontrado" scope="request">
											<html:text property="inscricaoImovel" size="30" readonly="true"
												style="background-color:#EFEFEF; border:0; color: #ff0000" />
										</logic:present>

										<logic:notPresent name="imovelNaoEncontrado" scope="request">
											<html:text property="inscricaoImovel" size="30"	readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000" />
										</logic:notPresent>

										<a href="javascript:desfazer();">
											<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
										</a>
									</td>
								</tr>
								<tr>
									<td><strong>Situação de Água:</strong></td>
									<td>
										<html:text property="situacaoAgua" size="15" readonly="true"
											style="background-color:#EFEFEF; border:0; color: #000000" />
									</td>

									<td><strong>Situação de Esgoto:</strong></td>
									<td>
										<html:text property="situacaoEsgoto" readonly="true" size="15"
											style="background-color:#EFEFEF; border:0; color: #000000" />
									</td>
								</tr>
								<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
									<tr>
										<td><strong>Quadra:</strong></td>
										<td>
											<html:text property="numeroQuadra" size="9" readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000" />
										</td>
									</tr>
								</logic:equal>
								<logic:notEqual name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
									<html:hidden property="numeroQuadra"/>
								</logic:notEqual>
							</table>
							</td>
						</tr>
					</table>
					</td>
				</tr>

				<tr>
					<td colspan="4"></td>
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
							<div class="style9">${EfetuarNegociacaoDebitosActionForm.enderecoImovel}
							&nbsp;</div>
							</td>
						</tr>
					</table>
					</td>
				</tr>

				<tr>
					<td colspan="4"></td>
				</tr>

				<!-- Clientes -->
				<tr>
					<td colspan="4">
					<table width="100%" align="center" bgcolor="#90c7fc" border="0">
						<tr bordercolor="#79bbfd">
							<td colspan="5" align="center" bgcolor="#79bbfd"><strong>Lista
							dos clientes associados ao Imóvel</strong></td>
						</tr>
						<tr bordercolor="#000000">
							<td width="28%" bgcolor="#90c7fc" align="center">
							<div class="style9"><font color="#000000"
								style="font-size: 9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Nome
							do Cliente</strong> </font></div>
							</td>
							<td width="17%" bgcolor="#90c7fc" align="center">
							<div class="style9"><font color="#000000"
								style="font-size: 9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Tipo
							da Relação</strong> </font></div>
							</td>
							<td width="19%" bgcolor="#90c7fc" align="center">
							<div class="style9"><font color="#000000"
								style="font-size: 9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Data
							Início Relação</strong> </font></div>
							</td>
							<td width="16%" bgcolor="#90c7fc" align="center">
							<div class="style9"><font color="#000000"
								style="font-size: 9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Telefone</strong>
							</font></div>
							</td>
							<td width="20%" bgcolor="#90c7fc" align="center"><font
								color="#000000" style="font-size: 9px"
								face="Verdana, Arial, Helvetica, sans-serif"><strong>CPF/CNPJ</strong>
							</font></td>
						</tr>
						<tr>
							<td width="100%" colspan="5">
							<div style="width: 100%; height: 100%; overflow: auto;">
							<table width="100%" align="left" bgcolor="#99CCFF">
								<%int cont = 0;	%>
								<logic:notEmpty name="colecaoClientesImovel">
									<logic:iterate name="colecaoClientesImovel"
										id="imovelCliente" type="ClienteImovel">
										<%cont = cont + 1;
										if (cont % 2 == 0) {%>
										<tr bgcolor="#cbe5fe">
										<%} else {%>
										<tr bgcolor="#FFFFFF">
										<%}%>
												<td width="28%" bordercolor="#90c7fc" align="left">
												<div class="style9"><font color="#000000"
													style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <logic:present
													name="imovelCliente" property="cliente">
													<a
														href="javascript:abrirPopup('exibirConsultarClienteAction.do?desabilitarPesquisaCliente=SIM&codigoCliente='+<bean:write name="imovelCliente" property="cliente.id" />, 500, 800);">
													<bean:write name="imovelCliente" property="cliente.nome" />
													</a>
												</logic:present> </font></div>
												</td>

												<td width="17%" align="left">
												<div class="style9"><font color="#000000"
													style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <logic:present
													name="imovelCliente" property="clienteRelacaoTipo">
													<bean:write name="imovelCliente"
														property="clienteRelacaoTipo.descricao" />
												</logic:present> </font></div>
												</td>

												<td width="19%" align="center">
												<div class="style9"><font color="#000000"
													style="font-size:9px"
													face="Verdana, Arial, Helvetica, sans-serif"> </font></div>
												<bean:write name="imovelCliente"
													property="dataInicioRelacao" formatKey="date.format" /></td>

												<td width="16%" align="right"><logic:notEmpty
													name="imovelCliente" property="cliente">
													<bean:define name="imovelCliente" property="cliente"
														id="cliente" />
													<logic:notEmpty name="cliente" property="clienteFones">
														<bean:define name="cliente" property="clienteFones"
															id="clienteFones" />
														<logic:iterate name="clienteFones" id="clienteFone">
															<div class="style9">
															<div align="center"><font color="#000000"
																style="font-size:9px"
																face="Verdana, Arial, Helvetica, sans-serif"> </font></div>
															<bean:write name="clienteFone" property="dddTelefone" />
															</div>
														</logic:iterate>
													</logic:notEmpty>
												</logic:notEmpty></td>

												<td width="20%" align="right"><font color="#000000"
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
				<!-- Valores dos débitos do cliente -->
				<tr>
					<td colspan="4" height="23">
						<br>
						<font color="#000000">
							<strong>Valor dos Débitos do Cliente:</strong>
						</font>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<table width="100%" bgcolor="#99CCFF">
							<tr bgcolor="#90c7fc">  
								<td align="center" width="30%">
									<strong>Contas</strong>
								</td>
								<td align="center" width="30%">
									<strong>Guias de Pagamento</strong>
								</td>
								<td align="center" width="40%">
									<strong>Acréscimos Impontualidade</strong>
								</td>
							</tr>
							<tr bgcolor="#cbe5fe"> 
								<td align="right" height="20" bgcolor="#FFFFFF">
									<bean:write name="EfetuarNegociacaoDebitosActionForm" property="valorTotalConta"/>
								</td>
								<td align="right" bgcolor="#FFFFFF">
									<bean:write name="EfetuarNegociacaoDebitosActionForm" property="valorTotalGuia"/>
								</td>
								<td align="right" bgcolor="#FFFFFF">
									<logic:notEqual name="EfetuarNegociacaoDebitosActionForm" property="valorTotalAcrescimo" value="0,00">
										<a href="javascript:abrirPopup('exibirValorAtualizacaoConsultarPopupAction.do?
											multa=<bean:write name="EfetuarNegociacaoDebitosActionForm" property="valorTotalMulta" />&
											juros=<bean:write name="EfetuarNegociacaoDebitosActionForm" property="valorTotalJurosMora" />&
											atualizacao=<bean:write name="EfetuarNegociacaoDebitosActionForm" property="valorTotalAtualizacaoMonetaria" />', 220, 605);">
										<bean:write name="EfetuarNegociacaoDebitosActionForm" property="valorTotalAcrescimo" formatKey="money.format" />
										</a>
									</logic:notEqual> 
									<logic:equal name="EfetuarNegociacaoDebitosActionForm" property="valorTotalAcrescimo" value="0,00">
										<bean:write name="EfetuarNegociacaoDebitosActionForm" property="valorTotalAcrescimo" formatKey="money.format" />
									</logic:equal>
								</td>
							</tr>
						</table>
						<br>	
						<table width="100%" bgcolor="#99CCFF">
							<tr bgcolor="#90c7fc">
								<td align="center" colspan="2">
									<strong>Débitos a Cobrar</strong>
								</td>
								<td align="center" rowspan="2">
									<strong>Créditos a Realizar</strong>
								</td>
								<td align="center" rowspan="2">
									<strong>Débito Total Atualizado</strong>
								</td>
							</tr>
							<tr bgcolor="#90c7fc"> 
								<td align="center" bgcolor="cbe5fe" width="20%">
									<strong>Serviço</strong>
								</td>
								<td align="center" bgcolor="cbe5fe">
									<strong>Parcelamento</strong>
								</td>
							</tr>
							<tr bgcolor="cbe5fe">
								<td align="right" height="20" bgcolor="#FFFFFF">
									<bean:write	name="EfetuarNegociacaoDebitosActionForm" property="valorTotalRestanteServicosACobrar" formatKey="money.format" />
								</td>
								<td align="right" bgcolor="#FFFFFF">
									<bean:write	name="EfetuarNegociacaoDebitosActionForm" property="valorTotalRestanteParcelamentosACobrar" formatKey="money.format" />
								</td>
								<td align="right" bgcolor="#FFFFFF">
									<bean:write	name="EfetuarNegociacaoDebitosActionForm" property="valorTotalCreditoARealizar" formatKey="money.format" />
								</td>
								<td align="right" bgcolor="#FFFFFF">
									<bean:write name="EfetuarNegociacaoDebitosActionForm" property="valorDebitoTotalAtualizado"/>
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td colspan="4"></td>
				</tr>

				<!-- Dados do Cliente Usuário -->
				<html:hidden property="idClienteUsuario"/>
				<html:hidden property="indicadorPessoaFisicaJuridica"/>
				<html:hidden property="cpfCnpjClienteAntes"/>

				<tr>
					<td colspan="4">
					<table width="100%" align="center" bgcolor="#99CCFF" border="0">
						<tr>
							<td align="center"><strong>Dados do Cliente Usuário</strong></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td width="100%" align="center">
							<table width="100%" border="0">
								<tr>
									<td width="20%"><strong>Nome:</strong></td>
									<td>
										<html:text property="nomeCliente" size="45"
											readonly="true" style="background-color:#EFEFEF; border:0" />
									</td>
								</tr>
								<tr>
									<logic:notEmpty name="EfetuarNegociacaoDebitosActionForm" property="indicadorPessoaFisicaJuridica">
										<logic:equal name="EfetuarNegociacaoDebitosActionForm" property="indicadorPessoaFisicaJuridica"
														value="<%=ClienteTipo.INDICADOR_PESSOA_FISICA.toString()%>">
											<td><strong>CPF:<font color="#FF0000">*</font></strong></td>
											<td>
												<logic:notEmpty name="EfetuarNegociacaoDebitosActionForm" property="idClienteUsuario">
													<logic:notEmpty name="EfetuarNegociacaoDebitosActionForm" property="cpfCnpjClienteAntes">
														<html:text property="cpfCnpjCliente" size="12"
															readonly="true" style="background-color:#EFEFEF; border:0" />
													</logic:notEmpty>
		
													<logic:empty name="EfetuarNegociacaoDebitosActionForm" property="cpfCnpjClienteAntes">
														<html:text property="cpfCnpjCliente" size="12" maxlength="11" />
													</logic:empty>
												</logic:notEmpty>
											</td>
										</logic:equal>

										<logic:notEqual name="EfetuarNegociacaoDebitosActionForm" property="indicadorPessoaFisicaJuridica"
														value="<%=ClienteTipo.INDICADOR_PESSOA_FISICA.toString()%>">
											<td><strong>CNPJ:<font color="#FF0000">*</font></strong></td>
											<td>
												<logic:notEmpty name="EfetuarNegociacaoDebitosActionForm" property="idClienteUsuario">
													<logic:notEmpty name="EfetuarNegociacaoDebitosActionForm" property="cpfCnpjClienteAntes">
														<html:text property="cpfCnpjCliente" size="16"
															readonly="true" style="background-color:#EFEFEF; border:0" />
													</logic:notEmpty>
		
													<logic:empty name="EfetuarNegociacaoDebitosActionForm" property="cpfCnpjClienteAntes">
														<html:text property="cpfCnpjCliente" size="16" maxlength="14" />
													</logic:empty>
												</logic:notEmpty>
											</td>
										</logic:notEqual>
									</logic:notEmpty>

									<logic:empty name="EfetuarNegociacaoDebitosActionForm" property="indicadorPessoaFisicaJuridica">
										<td><strong>CPF/CNPJ:<font color="#FF0000">*</font></strong></td>
										<td>
											<html:text property="cpfCnpjCliente" size="16"
												readonly="true" style="background-color:#EFEFEF; border:0" />
										</td>
									</logic:empty>
								</tr>
								<tr>
									<td><strong>E-mail:<font color="#FF0000">*</font></strong></td>
									<td>
										<logic:notEmpty name="EfetuarNegociacaoDebitosActionForm" property="idClienteUsuario">
											<html:text property="emailCliente" size="45" maxlength="40" />
										</logic:notEmpty>

										<logic:empty name="EfetuarNegociacaoDebitosActionForm" property="idClienteUsuario">
											<html:text property="emailCliente" size="45"
												readonly="true" style="background-color:#EFEFEF; border:0" />
										</logic:empty>
									</td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					</td>
				</tr>

				<tr>
					<td colspan="4"></td>
				</tr>

				<tr>
					<td colspan="4">
						<table width="100%" bgcolor="#99CCFF">
						<tr bordercolor="#79bbfd">
							<td align="center" colspan="5" bgcolor="#79bbfd"><strong>Resolução Diretoria</strong></td>
						</tr>
						<logic:present name="colecaoNegociacaoDebitoOpcoesRDHelper">
							<logic:iterate name="colecaoNegociacaoDebitoOpcoesRDHelper" type="NegociacaoDebitoOpcoesRDHelper" id="helper">
								<tr bgcolor="#cbe5fe">
									<td align="center" rowspan="4">
										<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
											<a href="javascript:reload();abrirPopupComSubmit('exibirEfetuarNegociacaoDebitosParcelamentoPopupAction.do?idResolucaoDiretoria=${helper.idResolucaoDiretoria}', 600, 700,'Negociação');">
												<bean:write name="helper" property="numeroResolucaoDiretoria" />
											</a>
										</font>
									</td>
									<td align="center">
										<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
											<strong>Valor Débito Atualizado</strong>
									    </font>
									</td>
									<td align="center">
										<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
											<strong>Desconto(%)</strong>
									    </font>
									</td>
									<td align="center">
										<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
											<strong>Valor Desconto</strong>
									    </font>
									</td>
									<td align="center">
										<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
											<strong>Valor Débito Desconto</strong>
									    </font>
									</td>
								</tr>

								<tr bgcolor="#FFFFFF">
									<!-- Valor Débito Atualizado -->
									<td align="center">
										<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
									    	<bean:write name="helper" property="valorDebitoTotalAtualizado" formatKey="money.format"/>
									    </font>
									</td>
									<!-- Desconto(%) -->
									<td align="center">
										<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
									    	<bean:write name="helper" property="percentualTotalDesconto" />
									    </font>
									</td>
									<!-- Valor Desconto -->
									<td align="center">
										<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
									    	<bean:write name="helper" property="valorTotalDesconto" formatKey="money.format"/>
									    </font>
									</td>
									<!-- Valor Débito Desconto -->
									<td align="center">
										<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
									    	<bean:write name="helper" property="valorDebitoAposDesconto" formatKey="money.format"/>
									    </font>
									</td>
								</tr>

								<tr bgcolor="#cbe5fe">
									<td align="center">
										<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
											<strong>Valor Entrada</strong>
									    </font>
									</td>
									<td align="center">
										<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
											<strong>Valor Total Juros</strong>
									    </font>
									</td>
									<td align="center">
										<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
											<strong>Parcelas</strong>
									    </font>
									</td>
									<td align="center">
										<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
											<strong>Valor Parcela</strong>
									    </font>
									</td>
								</tr>

								<tr bgcolor="#FFFFFF">
									<!-- Valor Entrada -->
									<td align="center">
										<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
									    	<bean:write name="helper" property="valorMinimoEntrada"  formatKey="money.format"/>
									    </font>
									</td>
									<!-- Valor Total Juros -->
									<td align="center">
										<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
									   		<bean:write name="helper" property="valorTotalJurosFinanciamento" formatKey="money.format"/>
									    </font>
									</td>
									<!-- Parcelas -->
									<td align="center">
										<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
									  	  <bean:write name="helper" property="quantidadeParcelas" />
									    </font>
									</td>
									<!-- Valor Parcela -->
									<td align="center">
										<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
									  		 <bean:write name="helper" property="valorParcela" formatKey="money.format"/>
									    </font>
									</td>
								</tr>
								<tr>
									<td>
										<font color="#000000" style="font-size: 4px" face="Verdana, Arial, Helvetica, sans-serif">
									   		&nbsp;
									    </font>
									</td>
								</tr>
							</logic:iterate>
						</logic:present>
						</table>
					</td>
				</tr>

				<tr>
					<td colspan="4"></td>
				</tr>

				<!-- Dados da Negociação -->
				<html:hidden property="negociacaoIdRD"/>
				<html:hidden property="negociacaoIndicadorPagamentoCartaoCredito"/>
				<html:hidden property="negociacaoValorDescontoPagamentoAVista"/>
				<html:hidden property="negociacaoValorDescontoInatividade"/>
				<html:hidden property="negociacaoValorDescontoAntiguidade"/>
				<html:hidden property="negociacaoNumeroMesesEntreParcelas"/>
				<html:hidden property="negociacaoNumeroParcelasALancar"/>
				<html:hidden property="negociacaoNumeroMesesInicioCobranca"/>
				<html:hidden property="negociacaoNumeroDiasVencimentoDaEntrada"/>
				<html:hidden property="negociacaoTaxaJuros"/>

				<tr>
					<td colspan="4">
					<table width="100%" align="center" bgcolor="#99CCFF" border="0">
						<tr>
							<td align="center"><strong>Dados da Negociação - Selecionada</strong></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td width="100%" align="center">
							<table width="100%" border="0">
								<tr>
									<td width="25%"><strong>Resolução Diretoria:</strong></td>
									<td>
										<html:text property="negociacaoNumeroRD" size="15"
											readonly="true" style="background-color:#EFEFEF; border:0" />
									</td>
								</tr>
								<tr>
									<td width="25%"><strong>Valor Débito Atualizado:</strong></td>
									<td>
										<html:text property="negociacaoValorDebitoTotalAtualizado" size="15"
											readonly="true" style="background-color:#EFEFEF; border:0; text-align:right" />
									</td>
									<td width="25%"><strong>Desconto(%):</strong></td>
									<td>
										<html:text property="negociacaoPercentualTotalDesconto" size="15"
											readonly="true" style="background-color:#EFEFEF; border:0; text-align:right" />
									</td>
								</tr>
								<tr>
									<td width="25%"><strong>Valor Desconto:</strong></td>
									<td>
										<html:text property="negociacaoValorTotalDesconto" size="15"
											readonly="true" style="background-color:#EFEFEF; border:0; text-align:right" />
									</td>
									<td width="25%"><strong>Valor Débito Desconto:</strong></td>
									<td>
										<html:text property="negociacaoValorDebitoAposDesconto" size="15"
											readonly="true" style="background-color:#EFEFEF; border:0; text-align:right" />
									</td>
								</tr>
								<tr>
									<td width="25%"><strong>Valor Entrada:</strong></td>
									<td>
										<html:text property="negociacaoValorEntrada" size="15"
											readonly="true" style="background-color:#EFEFEF; border:0; text-align:right" />
									</td>
									<td width="25%"><strong>Valor Total Juros:</strong></td>
									<td>
										<html:text property="negociacaoValorTotalJurosFinanciamento" size="15"
											readonly="true" style="background-color:#EFEFEF; border:0; text-align:right" />
									</td>
								</tr>
								<tr>
									<td width="25%"><strong>Parcelas:</strong></td>
									<td>
										<html:text property="negociacaoQuantidadeParcelas" size="15"
											readonly="true" style="background-color:#EFEFEF; border:0; text-align:right" />
									</td>
									<td width="25%"><strong>Valor Parcela:</strong></td>
									<td>
										<html:text property="negociacaoValorParcela" size="15"
											readonly="true" style="background-color:#EFEFEF; border:0; text-align:right" />
									</td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					</td>
				</tr>

				<tr>
					<td align="right">
					<div align="left"><strong><font color="#FF0000">*</font></strong>Campos
					obrigat&oacute;rios</div>
					</td>
				</tr>
				<tr>
					<td colspan="2" width="100%">
					<table width="100%">
						<tr>
							<td>
								<input name="Submit22" class="bottonRightCol" value="Desfazer"
									type="button" onclick="desfazer();">
								<input name="Submit23" class="bottonRightCol" value="Cancelar"
									type="button" onclick="window.location.href='/gsan/telaPrincipal.do'">
							</td>
							<td align="right">
								<input name="Submit24" class="bottonRightCol" value="Concluir"
									type="button" onclick="concluir();">
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
