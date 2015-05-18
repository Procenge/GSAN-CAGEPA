<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="gcom.util.ConstantesSistema"%>

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<%-- Carrega validações do validator --%>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="AtualizarLigacaoEsgotoActionForm" />

<%-- Carrega javascripts da biblioteca --%>
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>

<%-- Novos Javascripts --%>
<script language="JavaScript">
	
	/* Valida Form */
	function validaForm() {
		var form = document.AtualizarLigacaoEsgotoActionForm;
		/* Validate */
		if (validateAtualizarLigacaoEsgotoActionForm(form)) {
			if (form.localInstalacaoRamal.value == '-1') {
				alert('Informar Local de Instalação do Ramal');
				return;
			}
			submeterFormPadrao(form);
		}
	}
	/* Limpa Form Exibir */	 
	function limparForm() {
		var form = document.AtualizarLigacaoEsgotoActionForm;
		form.idOrdemServico.value = "";
		limparFormAtualizar();	
	}
	/* Limpa Form Atualizar */
	function limparFormAtualizar() {
		var form = document.AtualizarLigacaoEsgotoActionForm;
		form.nomeOrdemServico.value = "";		
		form.matriculaImovel.value = "";
		form.inscricaoImovel.value = "";
		form.clienteUsuario.value = "";
		form.cpfCnpjCliente.value = "";
		form.situacaoLigacaoAgua.value = "";
		form.situacaoLigacaoEsgoto.value = "";
		form.diametroLigacao.selectedIndex = 0;
		form.materialLigacao.selectedIndex = 0;
		form.perfilLigacao.selectedIndex = 0;
		form.percentualColeta.value = "";
		form.percentualEsgoto.value = "";
		form.dataLigacao.value = "";
		form.localInstalacaoRamal.value = "-1";
		form.idFuncionario.value = "";
		form.descricaoFuncionario.value = "";
		
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

	/* Chama Popup */ 
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

	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
		var form = document.forms[0];
	    if (tipoConsulta == 'ordemServico') {
	    	form.idOrdemServico.value = codigoRegistro;
	      	form.action='exibirAtualizarLigacaoEsgotoAction.do';
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
	}

	function resetar() {
		var form = document.forms[0];
		
		limparFormAtualizar();
		
		form.action = "exibirAtualizarLigacaoEsgotoAction.do?menu=sim";
		form.submit();
	}  
	
	
</script>
</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:verificaForm();setarFoco('${requestScope.nomeCampo}');">

<html:form action="/atualizarLigacaoEsgotoAction.do"
	name="AtualizarLigacaoEsgotoActionForm"
	type="gcom.gui.atendimentopublico.ligacaoesgoto.AtualizarLigacaoEsgotoActionForm"
	method="post">

	<html:hidden property="veioEncerrarOS" />
	<html:hidden property="redirect" />

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
					<td class="parabg">Atualizar Ligação de Esgoto</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" />
					</td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<!--Inicio da Tabela Ligação de Esgoto -->
			<table width="100%" border="0">
				<tr>
					<td height="31">
					<table width="100%" border="0" align="center">
						<tr>
							<td height="10" colspan="2">Para efetuar a atualização da ligação
							de esgoto, informe os dados abaixo:</td>
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

							<td><span class="style2"> <html:text property="idOrdemServico"
								size="8" maxlength="9"
								onkeypress="validaEnterComMensagem(event, 'exibirAtualizarLigacaoEsgotoAction.do', 'idOrdemServico','Ordem de Serviço');"
								onkeyup="limparFormAtualizar();" /> <a
								href="javascript:chamarPopup('exibirPesquisarOrdemServicoAction.do', 'ordemServico', null, null, 600, 500, '',document.forms[0].idOrdemServico);">

							<img width="23" height="21" border="0"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar OS" /></a> <logic:present name="osEncontrada"
								scope="session">
								<html:text property="nomeOrdemServico" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000"
									size="50" />
							</logic:present> <logic:notPresent name="osEncontrada"
								scope="session">
								<html:text property="nomeOrdemServico" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000"
									size="50" />
							</logic:notPresent> <a href="javascript:limparForm();"> <img
								border="0" title="Apagar"
								src="<bean:message key='caminho.imagens'/>limparcampo.gif" /> </a>

							</span></td>
						</tr>


						<tr bgcolor="#cbe5fe">
							<td align="center" colspan="2">
							<table width="100%" border="0" bgcolor="#99CCFF">
								<tr bgcolor="#99CCFF">
									<td height="18" colspan="2">
									<div align="center"><span class="style2"> Dados do Imóvel </span>
									</div>
									</td>
								</tr>

								<tr bgcolor="#cbe5fe">
									<td>
									<table border="0" width="100%">
										<tr>
											<td width="37%" height="10"><strong>Matr&iacute;cula do
											Im&oacute;vel:</strong></td>

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
									<div align="center"><span class="style2"> Dados da
									Liga&ccedil;&atilde;o de Esgoto</span></div>
									</td>
								</tr>
								<tr bgcolor="#cbe5fe">
									<td>
									<table border="0" width="100%">
										<tr>
											<td>
												<strong> Data da Liga&ccedil;&atilde;o:
												<strong><font color="#FF0000">*</font></strong>
												</strong>
											</td>

											<td colspan="2">
												<strong> 
												<html:text property="dataLigacao" 
													size="11" 
													maxlength="10" 
													tabindex="3" 
													onkeyup="mascaraData(this, event)"/>
													
												<a href="javascript:abrirCalendario('AtualizarLigacaoEsgotoActionForm', 'dataLigacao');">
													<img border="0" 
														src="<bean:message key='caminho.imagens'/>calendario.gif" 
														width="16" 
														height="15" 
														border="0" 
														alt="Exibir Calendário" 
														tabindex="4"/></a> (dd/mm/aaaa)

												</strong>
											</td>
										</tr>
									
									
										<tr>
											<td width="42%" height="10"><strong> Diametro da
											Liga&ccedil;&atilde;o: <span class="style2"> <strong> <font
												color="#FF0000">*</font> </strong> </span> <span
												class="style2"></span> </strong></td>
											<!-- Seleção Diametro de Ligação -->
											<td colspan="2"><strong> <html:select
												property="diametroLigacao" style="width: 230px;">
												<html:option
													value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
												<logic:present name="colecaoDiametroLigacaoEsgoto"
													scope="session">
													<html:options collection="colecaoDiametroLigacaoEsgoto"
														labelProperty="descricao" property="id" />
												</logic:present>
											</html:select> </strong></td>
										</tr>
										<!-- Material Ligação -->
										<tr>
											<td><strong> Material da Liga&ccedil;&atilde;o: <span
												class="style2"> <strong> <font color="#FF0000">*</font> </strong>
											</span> </strong></td>
											<td colspan="2"><strong> <html:select
												property="materialLigacao" style="width: 230px;">
												<html:option
													value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
												<logic:present name="colecaoMaterialLigacaoEsgoto"
													scope="session">
													<html:options collection="colecaoMaterialLigacaoEsgoto"
														labelProperty="descricao" property="id" />
												</logic:present>
											</html:select> </strong></td>
										</tr>
										<tr>
											<td class="style3"><strong> Perfil da Liga&ccedil;&atilde;o:
											<span class="style2"> <strong> <font color="#FF0000">*</font>
											</strong> </span> </strong></td>
											<td colspan="2"><strong> <html:select
												property="perfilLigacao" style="width: 230px;"
												onchange="javascript:redirecionarSubmit('exibirAtualizarLigacaoEsgotoAction.do?redirect=true');">
												<html:option
													value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
												<logic:present name="colecaoPerfilLigacaoEsgoto"
													scope="session">
													<html:options collection="colecaoPerfilLigacaoEsgoto"
														labelProperty="descricao" property="id" />
												</logic:present>
											</html:select> </strong></td>
										</tr>
										<logic:present name="pPermitirInformarPercentualColeta">
											<tr>
												<td class="style3"><b>Percentual de Coleta:<font
													color="#ff0000">*</font></b></td>

												<td colspan="2"><html:text property="percentualColeta"
													onkeyup="formataValorMonetario(this, 6);"
													style="text-align: right;" size="7" maxlength="6" /> %</td>
											</tr>
										</logic:present>
										
										<logic:notPresent name="pPermitirInformarPercentualColeta">
											<tr>
												<td class="style3"><b>Percentual de Coleta:<font
													color="#ff0000">*</font></b></td>

												<td colspan="2"><html:text property="percentualColeta"
													readonly="true"
													style="background-color:#EFEFEF; border:0;text-align: right;"
													size="7" maxlength="6" /> %</td>
											</tr>
										</logic:notPresent>
										
										<tr>
											<td class="style3"><strong> Percentual de Esgoto: <span
												class="style2"> <strong> <strong> <strong> <font
												color="#FF0000"></font> </strong> </strong> </strong> </span>
											</strong></td>
											<td colspan="2"><strong> <b><span class="style2"> <html:text
												property="percentualEsgoto" readonly="true"
												style="background-color:#EFEFEF; border:0; text-align:right;"
												size="8" /> % </span></b> </strong></td>
										</tr>
										
										<logic:present name="imovelPossuiPoco" scope="session">
											<tr>
												<td class="style3"><b>Consumo Fixo Poço:</b></td>

												<td colspan="2"><html:text property="consumoFixoPoco"
													style="border:0;text-align: right;"
													size="9" maxlength="9" />
												</td>
											</tr>
										</logic:present>
										
										<tr>
											<td><strong>Com Caixa de Gordura?<font color="#FF0000">*</font></strong></td>
											<td><strong> <html:radio property="indicadorCaixaGordura"
												value="1" /> SIM<html:radio
												property="indicadorCaixaGordura" value="2" /> NÃO</strong></td>

										</tr>
										<tr>
											<td class="style3"><strong>Local de Instalação do Ramal:<span class="style2"></span><font color="#FF0000">*</font></strong></td>
											<td colspan="2"><strong> <html:select
												property="localInstalacaoRamal" style="width: 230px;">
												<logic:present name="colecaoLocalInstalacaoRamal"
													scope="session">
													<html:option
														value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
													<html:options collection="colecaoLocalInstalacaoRamal"
														labelProperty="descricao" property="id" />
												</logic:present>
											</html:select> </strong></td>

										</tr>
										<tr>
											<td WIDTH="20%"><strong>Matrícula Funcionário:</strong></td>
											<td width="80%" colspan="3"><html:text property="idFuncionario"
												size="8" maxlength="8" tabindex="1"
												onkeypress="limparDescricao();validaEnterComMensagem(event, 'exibirAtualizarLigacaoEsgotoAction.do', 'idFuncionario', 'Funcionário');" />
											<a 
												href="javascript:abrirPopup('exibirAtualizarLigacaoEsgotoAction.do?pesquisarFuncionario=OK',400,400);">
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
						
												<logic:empty name="AtualizarLigacaoEsgotoActionForm"
													property="idFuncionario">
													<html:text property="descricaoFuncionario" value="" size="30"
														readonly="true"
														style="background-color:#EFEFEF; border:0; color: #ff0000" />
												</logic:empty>
												<logic:notEmpty name="AtualizarLigacaoEsgotoActionForm"
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
					<td colspan="2">
					<table width="100%">
						<tr>
							<td width="40%" align="left"><logic:equal
								parameter="veioEncerrarOS" value="TRUE">
								<input type="button" name="ButtonCancelar"
									class="bottonRightCol" value="Voltar"
									onClick="javascript:history.back();'">
							</logic:equal> <input type="button" name="ButtonReset"
								class="bottonRightCol" value="Desfazer" onClick="resetar();"> <input
								type="button" name="ButtonCancelar" class="bottonRightCol"
								value="Cancelar"
								onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">

							</td>
							<td align="right"><input name="Button" type="button"
								class="bottonRightCol" value="Atualizar" onclick="validaForm();">
							</td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<!-- Fim do Corpo - Leonardo Regis -->

	<!-- Rodapé -->
	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</html:html>
