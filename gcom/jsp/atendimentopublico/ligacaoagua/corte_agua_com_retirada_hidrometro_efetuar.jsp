<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="gcom.atendimentopublico.bean.PercentualCobrancaHelper"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<%@ page import="gcom.util.ConstantesSistema"%>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="EfetuarCorteLigacaoAguaComRetiradaHidrometroActionForm" />

<script language="JavaScript">
	
	function validaForm() {
		var form = document.EfetuarCorteLigacaoAguaComRetiradaHidrometroActionForm;
		
		if(form.indicadorObrigatoriedadeFuncionario.value == 1 && trim(form.idFuncionario.value) == ''){
			alert("Matrícula Funcionário é um campo obrigatório");
			return false;
		}
		
	   	 if (validateEfetuarCorteLigacaoAguaComRetiradaHidrometroActionForm(form) && validaDebito()){
	  		//submeterFormPadrao(form);
	  		submitForm(form);
		 }
	}


	function limpar(){

		var form = document.forms[0];
		
		form.idFuncionario.value = "";
		form.descricaoFuncionario.value = "";
				   
		//Coloca o foco no objeto selecionado
		form.idFuncionario.focus();
	}

	function limparDescricao(){
		
		var form = document.forms[0];
		form.descricaoFuncionario.value = "";
	}
	
	
	function limparForm() {
		var form = document.forms[0];
	
		form.idOrdemServico.value = "";
		form.idFuncionario.value = "";
		form.descricaoFuncionario.value = "";
		form.tipoCorte.value = "-1";
		limparOS();
		if(form.idTipoDebito != null){
		  form.idTipoDebito.value = "";
		  form.descricaoTipoDebito.value = "";
		  form.valorDebito.value = "";
		  if(form.motivoNaoCobranca != null){	
		    form.motivoNaoCobranca.selectedIndex = 0;
		  }
		  limpaDadosCobranca();
		}
	 }
	
	function limparOS() {
		var form = document.EfetuarCorteLigacaoAguaComRetiradaHidrometroActionForm;
		form.nomeOrdemServico.value = "";
		form.matriculaImovel.value = "";
		form.inscricaoImovel.value = "";
		form.clienteUsuario.value = "";
		form.cpfCnpjCliente.value = "";
		form.situacaoLigacaoAgua.value = "";
		form.situacaoLigacaoEsgoto.value = "";
		form.dataCorte.value = "";
		form.motivoCorte.selectedIndex = 0;
		form.numLeituraCorte.value = "";
		form.numSeloCorte.value = "";	
		form.idFuncionario.value = "";
		form.descricaoFuncionario.value = "";
		
	}

	//Recupera Dados Popup
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
		var form = document.forms[0];
	    if (tipoConsulta == 'ordemServico') {
	    	form.idOrdemServico.value = codigoRegistro;
	      	form.action='exibirEfetuarCorteLigacaoAguaComRetiradaHidrometroAction.do';
	      	form.submit();
	    } else {
    	 	if (tipoConsulta=='funcionario'){
	  			form.idFuncionario.value = codigoRegistro;
	  			form.descricaoFuncionario.value = descricaoRegistro;
	  			form.descricaoFuncionario.style.color = "#000000";
	      	}else{
	      		form.idCliente.value = codigoRegistro;  
	      		form.descricaoCliente.value = descricaoRegistro;
	      		form.descricaoCliente.style.color = "#000000";
	      	}	  
		}
	}
	
	//Chama Popup
	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){
		if(objetoRelacionado.disabled != true){
			if (objeto == null || codigoObjeto == null){
				abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
			}
			else{
				if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
					alert(msg);
				}
				else{
					abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisaOrdemServico=" + tipo, altura, largura);
				}
			}
		}
	}	 
 	//verifica se existe alguma restrição 
	//para exibição alguma campo no formulario
    function verificaForm() {

       	var form = document.forms[0];

		if(form.veioEncerrarOS.value == 'true'){
			form.idOrdemServico.disabled=true;
		}else{
			form.idOrdemServico.disabled=false;
		}
	 }
	
	function limpaDadosCobranca() {
		var form = document.forms[0];
		
		form.percentualCobranca.selectedIndex = 0;
		form.quantidadeParcelas.value = "";
		limpaValorParcela();
	}
	
	function limpaValorParcela() {
		var form = document.forms[0];
		form.valorParcelas.value = '';
	}
	
	function calcularValores(){
	
		var form = document.EfetuarCorteLigacaoAguaComRetiradaHidrometroActionForm;
	   	if (validateEfetuarCorteLigacaoAguaComRetiradaHidrometroActionForm(form) && validaDebito()){
  	
	   		form.action='exibirEfetuarCorteLigacaoAguaComRetiradaHidrometroAction.do?calculaValores=S';
	      	form.submit();
		}
	}
	
	//verifica se o motivo da não cobraça naum foi informado
	//libera o campo quantidade de parcelas 
	function habilitarQtdParcelaButton(){
		var form = document.forms[0];
		if(form.motivoNaoCobranca.value == "-1"){	
			form.percentualCobranca.value = "-1"
			form.valorParcelas.value = "";
			form.percentualCobranca.disabled = false;
			form.quantidadeParcelas.disabled = false;
			form.valorParcelas.value = "";
			form.buttonCalcular.disabled = false;
		}else{
			form.percentualCobranca.value = "-1"
			form.percentualCobranca.disabled = true;
			form.quantidadeParcelas.disabled = true;
			form.quantidadeParcelas.value = "1";
			form.valorParcelas.value = "";
			form.buttonCalcular.disabled = true;
		}
	}		

</script>
</head>



<body leftmargin="5" topmargin="5"
	onload="javascript:verificaForm();setarFoco('${requestScope.nomeCampo}');">

<div id="formDiv">

<html:form action="/efetuarCorteLigacaoAguaComRetiradaHidrometroAction.do"
	name="EfetuarCorteLigacaoAguaComRetiradaHidrometroActionForm"
	type="gcom.gui.atendimentopublico.ligacaoagua.EfetuarCorteLigacaoAguaComRetiradaHidrometroActionForm"
	method="post">

	<html:hidden property="veioEncerrarOS" />
	<html:hidden property="qtdeMaxParcelas" />
	<html:hidden property="indicadorObrigatoriedadeFuncionario" value="${ requestScope.indicadorObrigatoriedadeFuncionario }"/>

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="4" cellpadding="0">
		<tr>
			<td width="149" valign="top" class="leftcoltext">
			<div align="center">
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%@ include file="/jsp/util/informacoes_usuario.jsp"%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%@ include file="/jsp/util/mensagens.jsp"%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			</div>
			</td>
			<td width="615" valign="top" class="centercoltext">
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
					<td class="parabg">Efetuar Corte de Ligação de Água com Retirada de Hidrômetro</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>


			<!--Inicio da Tabela Corte Ligação Água -->
			<table width="100%" border="0">
				<tr>

					<td height="31">
					<table width="100%" border="0" align="center">
						<tr>
							<td height="10" colspan="2">Para efetuar o corte da ligação de
							água com retirada do hidrômetro, informe os dados abaixo:.</td>
						</tr>
						<tr>
							<td height="10" colspan="2">
							<hr>
							</td>
						</tr>

						<tr>
							<td height="12"><strong>Ordem de Servi&ccedil;o:<span
								class="style2"> <strong><font color="#FF0000">*</font></strong></span>
							</strong></td>
							<td><span class="style2"> <!-- Campo Ordem de Serviço --> <html:text
								property="idOrdemServico" size="9" maxlength="9"
								onkeypress="javascript:validaEnterComMensagem(event, 'exibirEfetuarCorteLigacaoAguaComRetiradaHidrometroAction.do', 'idOrdemServico','Ordem de Serviço');"
								onkeyup="limparOS();" /> <img
								src="<bean:message key='caminho.imagens'/>pesquisa.gif"
								width="23" height="21" name="imagem"
								onclick="chamarPopup('exibirPesquisarOrdemServicoAction.do', 'ordemServico', null, null, 275, 480, '',document.forms[0].idOrdemServico);"
								alt="Pesquisar"> <logic:present name="osEncontrada"
								scope="session">
								<html:text property="nomeOrdemServico" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000"
									size="50" />
							</logic:present> <logic:notPresent name="osEncontrada"
								scope="session">
								<html:text property="nomeOrdemServico" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000"
									size="48" />
							</logic:notPresent> <a href="javascript:limparForm();"> <img
								border="0" title="Apagar"
								src="<bean:message key='caminho.imagens'/>limparcampo.gif" /></a>
							</span></td>
						</tr>

						<tr bgcolor="#cbe5fe">
							<td align="center" colspan="2">
							<table width="100%" border="0" bgcolor="#99CCFF">
								<tr bgcolor="#99CCFF">
									<td height="18" colspan="2">
									<div align="center"><span class="style2"> Dados do Imóvel </span></div>
									</td>
								</tr>
								<tr bgcolor="#cbe5fe">

									<td>
									<table border="0" width="100%">
										<tr>
											<td width="37%" height="10"><strong><span class="style2">Matr&iacute;cula
											do Im&oacute;vel:<strong></strong></span></strong></td>
											<td width="58%"><html:text property="matriculaImovel"
												readonly="true" style="background-color:#EFEFEF; border:0;"
												size="15" maxlength="20" /> <html:text
												property="inscricaoImovel" readonly="true"
												style="background-color:#EFEFEF; border:0;" size="21"
												maxlength="20" /></td>
										</tr>
										<tr>
											<td><strong> Cliente Usu&aacute;rio:</strong></td>
											<td><html:text property="clienteUsuario" readonly="true"
												style="background-color:#EFEFEF; border:0;" size="40"
												maxlength="40" /></td>

										</tr>
										<tr>
											<td><strong>CPF ou CNPJ:</strong></td>
											<td><html:text property="cpfCnpjCliente" readonly="true"
												style="background-color:#EFEFEF; border:0;" size="40"
												maxlength="40" /></td>
										</tr>
										<tr>

											<td><strong>Situa&ccedil;&atilde;o da Liga&ccedil;&atilde;o
											de &Aacute;gua:</strong></td>
											<td><html:text property="situacaoLigacaoAgua" readonly="true"
												style="background-color:#EFEFEF; border:0;" size="40"
												maxlength="40" /></td>
										</tr>
										<tr>
											<td><strong>Situa&ccedil;&atilde;o da Liga&ccedil;&atilde;o
											de Esgoto:</strong></td>

											<td><html:text property="situacaoLigacaoEsgoto"
												readonly="true" style="background-color:#EFEFEF; border:0;"
												size="40" maxlength="40" /></td>
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
				<tr>
					<td height="31">
					<table width="100%" border="0" align="center">
						<tr bgcolor="#cbe5fe">
							<td align="center" colspan="2">
							<table width="100%" border="0" bgcolor="#99CCFF">

								<tr bgcolor="#99CCFF">
									<td height="18" colspan="2">
									<div align="center"><span class="style2"> Dados do Corte de
									Ligação de Água</span></div>
									</td>
								</tr>
								<tr bgcolor="#cbe5fe">
									<td>
									<table border="0" width="100%">
										<tr>

											<td width="42%" height="10"><strong>Data do Corte:<span
												class="style2"><strong><font color="#FF0000"></font></strong></span><span
												class="style2"></span></strong></td>
											<td colspan="2"><strong><b> <!-- Campo Data do Corte  --> <html:text
												property="dataCorte" readonly="true"
												style="background-color:#EFEFEF; border:0;" size="12"
												maxlength="20" /></b></strong></td>
										</tr>
										<tr>
											<td><strong> Motivo do Corte:<span class="style2"><strong><font
												color="#FF0000">*</font></strong></span></strong></td>
											<td colspan="2"><strong> <!--Campo Seleção Motivo do Corte -->
											<html:select property="motivoCorte" style="width: 230px;">
												<logic:present name="colecaoMotivoCorteLigacaoAgua"
													scope="session">
													<html:option
														value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
													<html:options collection="colecaoMotivoCorteLigacaoAgua"
														labelProperty="descricao" property="id" />
												</logic:present>
											</html:select> </strong></td>
										</tr>
										<tr>
											<td><strong> Tipo do Corte:<span class="style2"><strong><font
												color="#FF0000">*</font></strong></span></strong></td>
											<td colspan="2"><strong> <!--Campo Seleção Tipo do Corte -->
											<html:select property="tipoCorte" style="width: 230px;">
												<logic:present name="colecaoTipoCorteLigacaoAgua"
													scope="session">
													<html:option
														value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
													<html:options collection="colecaoTipoCorteLigacaoAgua"
														labelProperty="descricao" property="id" />
												</logic:present>
											</html:select> </strong></td>
										</tr>
										<tr>
											<td class="style3"><strong>Leitura do Corte:<span
												class="style2"><strong><font color="#FF0000"></font></strong></span></strong></td>
											<td colspan="2"><strong> <!-- Campo Material Ligação --> <html:text
												property="numLeituraCorte" size="12" maxlength="6"
												onkeyup="" onkeydown="" onkeypress="" /> </strong></td>
										</tr>
										<tr>
											<td class="style3"><strong>Número do Selo do Corte:<span
												class="style2"><strong><strong><strong><font color="#FF0000"></font></strong></strong></strong></span></strong></td>
											<td colspan="2"><strong> <html:text property="numSeloCorte"
												size="12" maxlength="8" onkeyup="" onkeydown=""
												onkeypress="" /> </strong></td>
										</tr>
										<tr>
											<logic:equal name="indicadorObrigatoriedadeFuncionario" value="1">
											<td WIDTH="20%"><strong>Matrícula Funcionário:<font color="#FF0000">*</font></strong></td>
											</logic:equal>
											<logic:notEqual name="indicadorObrigatoriedadeFuncionario" value="1">
											<td WIDTH="20%"><strong>Matrícula Funcionário:</strong></td>
											</logic:notEqual>
											<td width="80%" colspan="3"><html:text property="idFuncionario"
												size="8" maxlength="8" tabindex="1"
												onkeypress="limparDescricao();validaEnterComMensagem(event, 'exibirEfetuarCorteLigacaoAguaComRetiradaHidrometroAction.do', 'idFuncionario', 'Funcionário');" />
											<a 
												href="javascript:abrirPopup('exibirEfetuarCorteLigacaoAguaComRetiradaHidrometroAction.do?pesquisarFuncionario=OK',400,400);">
											<img src="/gsan/imagens/pesquisa.gif" alt="Pesquisar Funcionario"
												border="0" height="21" width="23"></a> <logic:present
												name="corFuncionario">
						
												<logic:equal name="corFuncionario" value="exception">
													<html:text property="descricaoFuncionario" size="30"
														readonly="true"
														style="background-color:#EFEFEF; border:0; color: #ff0000" />
												</logic:equal>
						
												<logic:notEqual name="corFuncionario" value="exception">
													<html:text property="descricaoFuncionario" size="30"
														readonly="true"
														style="background-color:#EFEFEF; border:0; color: #000000" />
												</logic:notEqual>
						
											</logic:present> <logic:notPresent name="corFuncionario">
						
												<logic:empty name="EfetuarCorteLigacaoAguaComRetiradaHidrometroActionForm"
													property="idFuncionario">
													<html:text property="descricaoFuncionario" value="" size="30"
														readonly="true"
														style="background-color:#EFEFEF; border:0; color: #ff0000" />
												</logic:empty>
												<logic:notEmpty name="EfetuarCorteLigacaoAguaComRetiradaHidrometroActionForm"
													property="idFuncionario">
													<html:text property="descricaoFuncionario" size="30"
														readonly="true"
														style="background-color:#EFEFEF; border:0; color: #000000" />
												</logic:notEmpty>
						
						
											</logic:notPresent> <a href="javascript:limpar();"> <img
												src="<bean:message key='caminho.imagens'/>limparcampo.gif"
												alt="Apagar" border="0"></a></td>
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
				<logic:notEmpty name="EfetuarCorteLigacaoAguaComRetiradaHidrometroActionForm"
					property="idTipoDebito">
					<tr>
						<td height="31">
						<table width="100%" border="0" align="center">
							<tr bgcolor="#cbe5fe">
								<td align="center" colspan="2">
								<table width="100%" border="0" bgcolor="#99CCFF">

									<tr bgcolor="#99CCFF">
										<td height="18" colspan="2">
										<div align="center"><span class="style2"> Dados da Geração do
										Débito</span></div>
										</td>
									</tr>
									<tr bgcolor="#cbe5fe">
										<td>
										<table border="0" width="100%">
											<tr>
												<td width="42%" height="10"><strong>Tipo de Débito:</strong></td>
												<td colspan="2">
													<html:text property="idTipoDebito" 
														readonly="true"
														style="background-color:#EFEFEF; border:0;" 
														size="5"
														maxlength="5" /> 
													
													<html:text property="descricaoTipoDebito"
														readonly="true" 
														style="background-color:#EFEFEF; border:0;"
														size="30" 
														maxlength="30" /> 
												</td>
											</tr>
											
											<!-- Colocado por Raphael Rossiter em 19/04/2007 -->
											<logic:notEqual name="EfetuarCorteLigacaoAguaComRetiradaHidrometroActionForm" property="alteracaoValor" value="OK">
												<tr>
													<td><strong>Valor do Débito:</strong></td>
													
													<td colspan="2">
														<html:text property="valorDebito" 
															readonly="true"
															style="background-color:#EFEFEF; border:0; text-align:right;"
															size="10" />
													</td>
												</tr>
											</logic:notEqual>
											
											<logic:equal name="EfetuarCorteLigacaoAguaComRetiradaHidrometroActionForm" property="alteracaoValor" value="OK">
												<tr>
													<td><strong>Valor do Débito:</strong></td>
													<td colspan="2">
														<html:text property="valorDebito" 
															style="text-align:right;"
															size="10" />
													</td>
												</tr>
											</logic:equal>
											
											
											<logic:present name="permissaoMotivoNaoCobranca">
												<tr>
													<td class="style3"><strong>Motivo da Não Cobrança:<font
														color="#FF0000">*</font></strong></td>

													<td colspan="2"><html:select
														property="motivoNaoCobranca" style="width: 230px;"
														onchange="habilitarQtdParcelaButton();">

														<html:option value="-1">&nbsp;</html:option>

														<logic:present name="colecaoNaoCobranca">
															<html:options collection="colecaoNaoCobranca"
																labelProperty="descricao" property="id" />
														</logic:present>
													</html:select></td>
												</tr>
											</logic:present>

											<logic:present name="permissaoPercentualCobrancaExcedente">

												<tr>
													<td class="style3"><strong>Percentual de Cobrança: </strong></td>
													<td colspan="2"><html:select
														property="percentualCobranca" style="width: 230px;">

														<html:option value="-1">&nbsp;</html:option>

														<logic:present name="colecaoPercentualCobranca">
															<html:options collection="colecaoPercentualCobranca"
																labelProperty="descricao" property="valor"/>
														</logic:present>
													</html:select></td>
												</tr>
												
												<logic:present name="temPermissaoQuantidadeParcelasExcedente">
													<tr>
														<td class="style3"><strong>Quantidade de Parcelas:<font
															color="#FF0000">*</font></strong></td>
														<td colspan="2">
															<html:text property="quantidadeParcelas" size="5" maxlength="5" />
														</td>
													</tr>
												</logic:present>
												
												<logic:notPresent name="temPermissaoQuantidadeParcelasExcedente">
													<tr>
														<td class="style3"><strong>Quantidade de Parcelas:<font 
															color="#FF0000">*</font></strong></td>
														<td colspan="2">
															<html:text property="quantidadeParcelas" readonly="true" value="1"
															style="background-color:#EFEFEF; border:0;text-align: right;" 
															size="5" maxlength="5"/>
														</td>
													</tr>
												</logic:notPresent>

											</logic:present>

											<logic:notPresent name="permissaoPercentualCobrancaExcedente">
												<tr>
													<td class="style3"><strong>Percentual de Cobrança: <font
														color="#FF0000">*</font></strong></td>
													<td colspan="2"><strong> <html:select
														property="percentualCobranca" style="width: 70px;">

														<html:option value="100">100%</html:option>
													</html:select> </strong></td>
												</tr>
												
												<logic:present name="temPermissaoQuantidadeParcelasExcedente">
													<tr>
														<td class="style3"><strong>Quantidade de Parcelas:<font
															color="#FF0000">*</font></strong></td>
														<td colspan="2">
															<html:text property="quantidadeParcelas" size="5" maxlength="5" />
														</td>
													</tr>
												</logic:present>
												
												<logic:notPresent name="temPermissaoQuantidadeParcelasExcedente">
													<tr>
														<td class="style3"><strong>Quantidade de Parcelas:<font 
															color="#FF0000">*</font></strong></td>
														<td colspan="2">
															<html:text property="quantidadeParcelas" readonly="true" value="1"
															style="background-color:#EFEFEF; border:0;text-align: right;" 
															size="5" maxlength="5"/>
														</td>
													</tr>
												</logic:notPresent>

											</logic:notPresent>

											<tr>
												<td class="style3"><strong>Valor da Parcela:</strong></td>
												<td colspan="2"><html:text property="valorParcelas"
													readonly="true"
													style="background-color:#EFEFEF; border:0;text-align: right;"
													size="15" maxlength="15" /></td>

												<td colspan="4" align="right"><input type="button"
													class="bottonRightCol" value="Calcular"
													name="buttonCalcular" tabindex="10"
													onclick="calcularValores();" style="width: 80px"></td>

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
				</logic:notEmpty>
				<tr>
					<td colspan="2">
					<table width="100%">

						<tr>
							<td width="40%" align="left"><logic:present
								name="caminhoRetornoIntegracaoComercial">
								<INPUT TYPE="button" class="bottonRightCol" value="Voltar"
									onclick="redirecionarSubmit('${caminhoRetornoIntegracaoComercial}')" />
							</logic:present> <input type="button" name="ButtonReset"
								class="bottonRightCol" value="Desfazer" onClick="limparForm();">

							<input type="button" name="ButtonCancelar" class="bottonRightCol"
								value="Cancelar"
								onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
							</td>

							<td align="right"><input name="Button" type="button"
								class="bottonRightCol" value="Efetuar" onclick="validaForm();">
							</td>
						</tr>
					</table>
					</td>

				</tr>
				<!--</tr></table></td>-->
			</table>
	</table>
	<!-- Fim do Corpo - Leandro Cavalcanti -->


	<%@ include file="/jsp/util/rodape.jsp"%>

</html:form>

</div>
<%@ include file="/jsp/util/telaespera.jsp"%>
</body>
</html:html>
