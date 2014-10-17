<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="gcom.util.ConstantesSistema" %>
<%@ page import="gcom.atendimentopublico.bean.PercentualCobrancaHelper"%>

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
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="EfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm"
	dynamicJavascript="true" />

<script language="JavaScript">
	
	function validaForm() {
		var form = document.EfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm;
		
		if(form.indicadorObrigatoriedadeFuncionario.value == 1 && trim(form.idFuncionario.value) == ''){
			alert("Matrícula Funcionário é um campo obrigatório");
			return false;
		}
		
		if (validateEfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm(form) && validaDebito()){
			if (form.situacaoCavalete[0].checked == false && form.situacaoCavalete[1].checked == false) {
				alert("Informe Cavalete");
			} else {
				if(form.motivoNaoCobranca != null && form.motivoNaoCobranca.value == "-1" && form.valorParcelas.value == ''){
			  		alert('Calcule o Valor da Parcela.');		  		
				} else {

					if (quantidadeParcelasMaiorQueZero()) {
						submeterFormPadrao(form);
				   	}
				}
			}
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
		var form = document.EfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm;

		form.idOrdemServico.value = "";
		form.nomeOrdemServico.value = "";

		form.matriculaImovel.value = "";
		form.inscricaoImovel.value = "";
		form.clienteUsuario.value = "";
		form.cpfCnpjCliente.value = "";
		form.situacaoLigacaoAgua.value = "";
		form.situacaoLigacaoEsgoto.value = "";
		form.numeroHidrometro.value = "";
		form.localInstalacao.selectedIndex = 0;
		form.protecao.selectedIndex = 0;
		form.leituraInstalacao.value = "";
		form.numeroSelo.value = "";
		form.idFuncionario.value = "";
		form.descricaoFuncionario.value = "";
		form.leituraRetirada.value = "";
		form.situacaoHidrometro.value = "-1";
		form.localArmazenagem.value = "-1";
		form.numeroHidrometroNovo.value = "";
		form.situacaoCavalete[0].checked = false;
		form.situacaoCavalete[1].checked = false;	

		if(form.idTipoDebito != null){
			form.idTipoDebito.value = "";
			form.descricaoTipoDebito.value = "";
			form.valorDebito.value = "";
			if(form.motivoNaoCobranca != null){
			  form.motivoNaoCobranca.selectedIndex = 0;
			}
			form.percentualCobranca.selectedIndex = 0;
			form.quantidadeParcelas.value = "";
			form.valorParcelas.value = "";
		}

		form.action = 'exibirEfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroAction.do?desfazer=sim';
		form.submit();
	 }

	 function limparDecricaoEfetuar() {
		var form = document.EfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm;

		form.nomeOrdemServico.value = "";
		form.matriculaImovel.value = "";
		form.inscricaoImovel.value = "";
		form.clienteUsuario.value = "";
		form.cpfCnpjCliente.value = "";
		form.situacaoLigacaoAgua.value = "";
		form.situacaoLigacaoEsgoto.value = "";
		form.dataRestabelecimento.value = "";
		form.idFuncionario.value = "";
		form.descricaoFuncionario.value = "";

		if(form.idTipoDebito != null){
			form.idTipoDebito.value = "";
			form.descricaoTipoDebito.value = "";
			form.valorDebito.value = "";
			if(form.motivoNaoCobranca != null){
			  form.motivoNaoCobranca.selectedIndex = 0;
			}
			form.percentualCobranca.selectedIndex = 0;
			form.quantidadeParcelas.value = "";
			form.valorParcelas.value = "";
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
					abrirPopup(url + "?" + "menu=sim&" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisaOrdemServico=" + tipo, altura, largura);
				}
			}
		}
	}
	 
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
		var form = document.forms[0];

		if (tipoConsulta == 'hidrometro') {
      		//form.numeroHidrometro.value = codigoRegistro;

	      	form.action='exibirEfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroAction.do?objetoConsulta=1&numeroHidrometroNovo=' + codigoRegistro;
	      	form.submit();
      		
    	} else if (tipoConsulta == 'ordemServico') {

	    	form.idOrdemServico.value = codigoRegistro;
	      	form.action='exibirEfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroAction.do';

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
	
	//verifica se existe alguma restrição 
	//para exibição alguma campo no formulario
    function verificaForm() {

       	var form = document.forms[0];

		if(form.veioEncerrarOS.value == 'true'){
			form.idOrdemServico.disabled=true;
		}else{
			form.idOrdemServico.disabled=false;
		}

		if(form.idTipoDebito != null && form.idTipoDebito.value != ""){
			// Tem Débitos
			if(form.motivoNaoCobranca == null || form.motivoNaoCobranca.value == "-1"){	
				//form.percentualCobranca.value = "-1"
				form.percentualCobranca.disabled = false;
				//form.quantidadeParcelas.value = "";
				form.quantidadeParcelas.disabled = false;
				//form.valorParcelas.value = "";
				form.buttonCalcular.disabled = false;
			}else{
				form.valorDebito.value = "";
				form.percentualCobranca.value = "-1"
				form.percentualCobranca.disabled = true;
				form.quantidadeParcelas.value = "1";
				form.quantidadeParcelas.disabled = true;
				form.valorParcelas.value = "";
				form.buttonCalcular.disabled = true;
			}
		}
	}
	
	//function limparNumeroHidrometro() {
		//var form = document.forms[0];
		//form.numeroHidrometro.value = "";
	//}

	function limparNumeroHidrometroNovo() {
		var form = document.forms[0];
		form.numeroHidrometroNovo.value = "";
	}

	function calcularValores(){

		var form = document.EfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm;

	   	if (validateEfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm(form) && validaDebito()){
		   	if (quantidadeParcelasMaiorQueZero()) {
		   		form.action='exibirEfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroAction.do?calculaValores=S';
		      	form.submit();
		   	}

		   	// else {
				// alert('Quantidade de Parcelas deve ser maior que zero');
			// }
		}

	}

	function quantidadeParcelasMaiorQueZero() {
		
		var form = document.EfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm;
		var retorno = true;	
		
		if(form.idTipoDebito != null && form.idTipoDebito.value != ""){
		// Tem Débitos
			if(form.motivoNaoCobranca == null || form.motivoNaoCobranca.value == "-1"){
				if(form.quantidadeParcelas != null && form.quantidadeParcelas.value != ""){

					if (form.valorDebito != null && form.valorDebito.value != "") {
						//var valor = form.quantidadeParcelas.value;
						//var valorInteiro = form.valorDebito.value * 1;
						if(!testarCampoValorZeroDecimal(form.valorDebito, 'Valor do Débito')) {
							retorno = false;
						}
					} else {
						alert('Informe: Valor do Débito');
						retorno = false;
					}

					if(!testarCampoValorZeroDecimal(form.quantidadeParcelas, 'Quantidade de Parcelas')) {
						retorno = false;
					}
		
				} else {
					alert('Informe: Quantidade de Parcelas');
					retorno = false;	
				}
			}
		}

		return retorno;
	}
	
	//verifica se o motivo da não cobraça naum foi informado
	//libera o campo quantidade de parcelas 
	function habilitarQtdParcelaButton(){
		var form = document.forms[0];

		if(form.idTipoDebito != null && form.idTipoDebito.value != ""){
			// Tem Débitos
			if(form.motivoNaoCobranca == null || form.motivoNaoCobranca.value == "-1"){	
				form.percentualCobranca.value = "-1"
				form.percentualCobranca.disabled = false;
				form.quantidadeParcelas.disabled = false;
				form.valorParcelas.value = "";
				form.buttonCalcular.disabled = false;
			}else{
				form.percentualCobranca.value = "-1"
				form.percentualCobranca.disabled = true;
				form.quantidadeParcelas.value = "";
				form.quantidadeParcelas.disabled = true;
				form.valorParcelas.value = "";
				form.buttonCalcular.disabled = true;
			}
		}
	}

	function campoBrancoOuNulo(campo){
		if (campo == undefined || 
			campo == null || 
			campo == 'null' || 
			campo == 'undefined' || 
			campo.value == '') {
			return true;
		}
		return false;
	}
			
</script>
</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('${requestScope.nomeCampo}');verificaForm();">

<html:form action="/efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroAction.do"
	name="EfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm"
	type="gcom.gui.atendimentopublico.EfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm"
	method="post">


	<html:hidden property="veioEncerrarOS" />
	<html:hidden property="indicadorObrigatoriedadeFuncionario" value="${ requestScope.indicadorObrigatoriedadeFuncionario }"/>

	<logic:notPresent name="semMenu">

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
				</logic:notPresent> <logic:present name="semMenu">
					<table width="550" border="0" cellspacing="5" cellpadding="0">
						<tr>
							<td width="615" valign="top" class="centercoltext">
							<table height="100%">
								<tr>
									<td></td>
								</tr>
							</table>
							</logic:present>
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td width="11"><img border="0"
										src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
									<td class="parabg">Efetuar Restabelecimento Ligação Água com Substituição de
									Hidrômetro</td>
									<td width="11"><img border="0"
										src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
								</tr>
							</table>
							<p>&nbsp;</p>


							<!--Inicio da Tabela Ligação Agua -->
							<table width="100%" border="0">
								<tr>

									<td height="31">
									<table width="100%" border="0" align="center">
										<tr>
											<td height="10" colspan="2">Para efetuar o restabelecimento da
											liga&ccedil;&atilde;o de &aacute;gua com substituição de
											hidrômetro, informe os dados abaixo:</td>
										</tr>
										<tr>
											<td height="10" colspan="2">
											<hr>
											</td>
										</tr>
										<tr>

											<td height="10"><strong>Ordem de Servi&ccedil;o:<span
												class="style2"> <strong><font color="#FF0000">*</font></strong></span>
											</strong></td>

											<td><span class="style2"> <html:text
												property="idOrdemServico" size="8" maxlength="9"
												onkeypress="validaEnterComMensagem(event, 'exibirEfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroAction.do', 'idOrdemServico','Ordem de Serviço');"
												onkeyup="javascript:limparDecricaoEfetuar()" /> <a
												href="javascript:chamarPopup('exibirPesquisarOrdemServicoAction.do', 'ordemServico', null, null, 600, 500, '',document.forms[0].idOrdemServico);">
											<img width="23" height="21" border="0"
												src="<bean:message key="caminho.imagens"/>pesquisa.gif"
												title="Pesquisar OS" /></a> <logic:present
												name="OrdemServioInexistente">
												<html:text property="nomeOrdemServico" readonly="true"
													style="background-color:#EFEFEF; border:0; color: #ff0000"
													size="37" maxlength="40" />
											</logic:present> <logic:notPresent
												name="OrdemServioInexistente">
												<html:text property="nomeOrdemServico" readonly="true"
													style="background-color:#EFEFEF; border:0; color: #000000"
													size="37" maxlength="40" />
											</logic:notPresent> <a href="javascript:limparForm();"> <img
												border="0" title="Apagar"
												src="<bean:message key='caminho.imagens'/>limparcampo.gif" />
											</a> </span></td>
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
															do Im&oacute;vel:</span></strong></td>

															<td width="58%"><html:text property="matriculaImovel"
																readonly="true"
																style="background-color:#EFEFEF; border:0;" size="15"
																maxlength="20" /> <html:text property="inscricaoImovel"
																readonly="true"
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

															<td><strong>Situa&ccedil;&atilde;o da
															Liga&ccedil;&atilde;o de &Aacute;gua:</strong></td>
															<td><html:text property="situacaoLigacaoAgua"
																readonly="true"
																style="background-color:#EFEFEF; border:0;" size="40"
																maxlength="40" /></td>
														</tr>
														<tr>
															<td><strong>Situa&ccedil;&atilde;o da
															Liga&ccedil;&atilde;o de Esgoto:</strong></td>

															<td><html:text property="situacaoLigacaoEsgoto"
																readonly="true"
																style="background-color:#EFEFEF; border:0;" size="40"
																maxlength="40" /></td>
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
													<div align="center"><span class="style2"> Dados do
													Restabelecimento </span></div>
													</td>
												</tr>
												<tr bgcolor="#cbe5fe">
													<td>
													<table border="0" width="100%">
														<tr>
															<td width="42%" height="10"><strong>Data do
															Restabelecimento:<font color="#FF0000">*</font> </strong>
															</td>

															<td colspan="2"><strong><b> <html:text
																property="dataRestabelecimento" readonly="true"
																style="background-color:#EFEFEF; border:0;" size="20"
																maxlength="20" /> </b></strong></td>
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
																onkeypress="limparDescricao();validaEnterComMensagem(event, 'exibirEfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroAction.do', 'idFuncionario', 'Funcionário');" />
															<a 
																href="javascript:abrirPopup('exibirEfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroAction.do?pesquisarFuncionario=OK',400,400);">
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
										
																<logic:empty name="EfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm"
																	property="idFuncionario">
																	<html:text property="descricaoFuncionario" value="" size="30"
																		readonly="true"
																		style="background-color:#EFEFEF; border:0; color: #ff0000" />
																</logic:empty>
																<logic:notEmpty name="EfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm"
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
								<tr>
									<td height="31">
									<table width="100%" border="0" align="center">
										<tr bgcolor="#cbe5fe">
											<td align="center" colspan="2">
											<table width="100%" border="0" bgcolor="#99CCFF">
												<tr bgcolor="#99CCFF">

													<td height="18" colspan="2">
													<div align="center"><span class="style2"> Dados da
													Substitui&ccedil;&atilde;o</span></div>
													</td>
												</tr>
												<tr bgcolor="#cbe5fe">
													<td>
													<table border="0" width="100%">
														<tr>
															<td width="37%" class="style3"><strong>N&uacute;mero do
															Hidr&ocirc;metro Atual:<font color="#FF0000">*</font></strong></td>

															<td width="58%"><html:text property="numeroHidrometro"
																size="11" readonly="true" />															
															</td>
														</tr>
														<tr>
															<td height="10"><strong>Tipo de Medi&ccedil;&atilde;o:</strong></td>
															<td colspan="2">
																<strong>
																	<b>
																		<html:text property="tipoMedicao" readonly="true"
																			style="background-color:#EFEFEF; border:0;" size="20" maxlength="25"/>
																	</b>
																</strong>
															</td>
															<html:hidden name="EfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm" property="idTipoMedicao"/>
														</tr>
														<tr>
															<td height="10"><strong>Data da Retirada:<font
																color="#FF0000">*</font></strong></td>
															<td colspan="2"><strong><b>
																<html:text property="dataRetirada" styleClass="style2"
																	size="10" readonly="true" style="background-color:#EFEFEF; border:0;"
																	maxlength="10" onkeyup="mascaraData(this, event);" onkeypress="return isCampoNumerico(event);"
																/> </b></strong>
															</td>
														</tr>
														<tr>
															<td height="10"><strong>N&uacute;mero da Leitura:</strong></td>
															<td colspan="2"><strong><b>
															<html:text property="leituraRetirada" 
																styleClass="style2" size="11"
																maxlength="10" onkeypress="return isCampoNumerico(event);"/> </b></strong></td>
														</tr>
														<tr>
															<td height="10"><strong>Situa&ccedil;&atilde;o do Hidr&ocirc;metro:<font color="#FF0000">*</font></strong></td>															
																<td><html:select property="situacaoHidrometro" >
																<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
																<html:options collection="colecaoHidrometroSituacao"
																	labelProperty="descricao" property="id" />
															</html:select> <font size="1">&nbsp; </font></td>
														</tr>
														<tr>
															<td height="10"><strong>Local de Armazenagem:<font color="#FF0000">*</font></strong></td>
															<td><html:select property="localArmazenagem" >
																<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
																<html:options collection="colecaoHidrometroLocalArmazenagem"
																	labelProperty="descricao" property="id" />
															</html:select> <font size="1">&nbsp; </font></td>
														</tr>
														<tr>
															<td height="10" colspan="2">
															<hr>
															</td>
														</tr>
														<tr>
															<td width="37%" class="style3"><strong>N&uacute;mero do
															Novo Hidr&ocirc;metro:<font color="#FF0000">*</font></strong></td>

															<td width="58%"><html:text property="numeroHidrometroNovo"
																size="11" 
																onkeypress="validaEnterString(event, 'exibirEfetuarReligacaoAguaComSubstituicaoHidrometroAction.do?objetoConsulta=1', 'numeroHidrometroNovo','Número do Hidrômetro Novo');"/>
																
																<a href="javascript:abrirPopup('exibirPesquisarHidrometroAction.do',600,640);"><img
																	src="/gsan/imagens/pesquisa.gif" alt="Pesquisar"
																	border="0" height="21" width="23">
																</a> 
																<a href="javascript:limparNumeroHidrometroNovo();"><img
																	border="0" title="Apagar"
																	src="<bean:message key='caminho.imagens'/>limparcampo.gif" />
																</a>															
															</td>
														</tr>
														<tr>
															<td height="10"><strong>Data da Instala&ccedil;&atilde;o:</strong></td>
															<td colspan="2"><strong><b> <!-- Campo Data da Ligacao -->
															<html:text property="dataInstalacao" readonly="true"
																style="background-color:#EFEFEF; border:0;" size="10"
																maxlength="10" /> </b></strong></td>
														</tr>
														<tr>
															<td><strong>Local de Instala&ccedil;&atilde;o:<font color="#FF0000">*</font></strong></td>
															<td><html:select property="localInstalacao" tabindex="4">
																<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
																<html:options
																	collection="colecaoHidrometroLocalInstalacao"
																	labelProperty="descricao" property="id" />
															</html:select> <font size="1">&nbsp; </font></td>
														</tr>
														<tr>
															<td><strong>Prote&ccedil;&atilde;o:<font color="#FF0000">*</font></strong></td>
															<td><html:select property="protecao" tabindex="4">
																<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
																<html:options collection="colecaoHidrometroProtecao"
																	labelProperty="descricao" property="id" />
															</html:select> <font size="1">&nbsp; </font></td>
														</tr>
														<tr>
															<td><strong>Troca de Prote&ccedil;&atilde;o:<font color="#FF0000">*</font></strong></td>
															<td><strong> 
																<html:radio property="indicadorTrocaProtecao" value="1"/>Sim<strong>
																<html:radio	property="indicadorTrocaProtecao" value="2"/>N&atilde;o</strong>
															</strong></td>
														</tr>
														<tr>
															<td><strong>Troca de Registro:<font color="#FF0000">*</font></strong></td>
															<td><strong> 
																<html:radio property="indicadorTrocaRegistro" value="1"/><strong>Sim
																<html:radio	property="indicadorTrocaRegistro" value="2"/>N&atilde;o</strong>
															</strong></td>
														</tr>
														<tr>
															<td class="style3"><strong>Leitura de Instala&ccedil;&atilde;o:<font color="#FF0000">*</font></strong></td>
															<td colspan="2"><strong><b><span class="style2"> <html:text
																property="leituraInstalacao" size="11" maxlength="10" onkeypress="return isCampoNumerico(event);" /> </span></b></strong>
															</td>
														</tr>
														<tr>
															<td class="style3"><strong>N&uacute;mero do Selo:<font color="#FF0000">*</font></strong></td>
															<td colspan="2"><strong><b><span class="style2"> <html:text
																property="numeroSelo" size="13" maxlength="12"
																onkeyup="" onkeydown="" onkeypress="" /> </span></b></strong></td>
														</tr>
														<tr>
															<td><strong>Cavalete:<font color="#FF0000">*</font></strong></td>
															<td><strong> <html:radio property="situacaoCavalete"
																value="1" /> <strong>Com <html:radio
																property="situacaoCavalete" value="2" /> Sem</strong> </strong></td>
														</tr>
														<!-- <tr>
															<td><strong>Motivo da Substitui&ccedil;&atilde;o:<font color="#FF0000">*</font></strong></td>
															<td><html:select property="motivoSubstituicao" tabindex="4">
																<html:option value="-1">&nbsp;</html:option>
																<html:options collection="colecaoHidrometroMotivoMovimentacao"
																	labelProperty="descricao" property="id" />
															</html:select> <font size="1">&nbsp; </font></td>
														</tr> -->
														 <tr>
											             	<td height="24">&nbsp;</td>
											                <td colspan="2"> <strong><font color="#FF0000">*</font></strong> Campos obrigat&oacute;rios</td>
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
								<logic:notEmpty
									name="EfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm"
									property="idTipoDebito">
									<tr>
										<td height="31">
										<table width="100%" border="0" align="center">
											<tr bgcolor="#cbe5fe">
												<td align="center" colspan="2">
												<table width="100%" border="0" bgcolor="#99CCFF">

													<tr bgcolor="#99CCFF">
														<td height="18" colspan="2">
														<div align="center"><span class="style2"> Dados da Geração
														do Débito</span></div>
														</td>
													</tr>
													<tr bgcolor="#cbe5fe">
														<td>
														<table border="0" width="100%">

															<tr>
																<td width="42%" height="10"><strong>Tipo de Débito:</strong>
																</td>

																<td colspan="2"><strong><b> <html:text
																	property="idTipoDebito" readonly="true"
																	style="background-color:#EFEFEF; border:0;" size="5"
																	maxlength="5" /> <html:text
																	property="descricaoTipoDebito" readonly="true"
																	style="background-color:#EFEFEF; border:0;" size="30"
																	maxlength="30" /> </b></strong></td>
															</tr>
															
															<!-- Colocado por Raphael Rossiter em 19/04/2007 -->
															<logic:notEqual name="EfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm" property="alteracaoValor" value="OK">
																<tr>
																	<td><strong>Valor do Débito:</strong></td>
																	
																	<td colspan="2">
																		<html:text property="valorDebito" 
																			readonly="true"
																			style="background-color:#EFEFEF; border:0; text-align:right;"
																			size="10" maxlength="10" onkeyup="formataValorMonetario(this, 13);" onkeypress="return isCampoNumerico(event);" />
																	</td>
																</tr>
															</logic:notEqual>
															
															<logic:equal name="EfetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometroActionForm" property="alteracaoValor" value="OK">
																<tr>
																	<td><strong>Valor do Débito:</strong></td>
																	<td colspan="2">
																		<html:text property="valorDebito" 
																			style="text-align:right;"
																			size="10" maxlength="10" onkeyup="formataValorMonetario(this, 13);" onkeypress="return isCampoNumerico(event);" />
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
												class="bottonRightCol" value="Desfazer"
												onClick="limparForm();"> <input type="button"
												name="ButtonCancelar" class="bottonRightCol"
												value="Cancelar"
												onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
											</td>

											<td align="right"><input name="Button" type="button"
												class="bottonRightCol" value="Efetuar"
												onclick="validaForm();"></td>
										</tr>
									</table>
									</td>

								</tr>
							</table>
							</td>
						</tr>
					</table>
					<logic:notPresent name="semMenu">
						<%@ include file="/jsp/util/rodape.jsp"%>
					</logic:notPresent></td>
			</tr>
		</table>
</html:form>
</body>
</html:html>
