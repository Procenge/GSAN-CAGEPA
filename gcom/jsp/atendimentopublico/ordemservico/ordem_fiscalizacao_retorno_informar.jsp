<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page
	import="gcom.atendimentopublico.ordemservico.FiscalizacaoSituacao"%>
<%@ page
	import="gcom.atendimentopublico.ordemservico.bean.SituacaoEncontradaHelper"%>

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
	formName="InformarRetornoOSFiscalizacaoActionForm" />

<script language="JavaScript">
  	function validaForm(){
    	var form = document.forms[0];
	  	
	  	if(validateInformarRetornoOSFiscalizacaoActionForm(form)){
			if(validaTodosRadioButton()){		     
		  		submeterFormPadrao(form);
		  			
   	    	}
   	  	}
  	}
  	
  	function desabilitaCombo(valor){
  		var form = document.forms[0];
  		if(valor == '2'){
			form.atendimentoMotivoEncerramento.value = "-1";
			form.atendimentoMotivoEncerramento.disabled = true;
		}else{
			form.atendimentoMotivoEncerramento.disabled = false;
		}
  	
  	}
  	
  	function validaRadioButton(nomeCampo,mensagem){
		
		var alerta = "";
		if(!nomeCampo[0].checked && !nomeCampo[1].checked){
			alerta = "Informe " + mensagem +".";
		}
		return alerta;
   	}
   	
   	 function validaRadioButtonTres(nomeCampo,mensagem){
		
		var alerta = "";
		if(!nomeCampo[0].checked && !nomeCampo[1].checked && !nomeCampo[2].checked){
			alerta = "Informe " + mensagem +".";
		}
		return alerta;
   	}
   
  	function validaTodosRadioButton(){
		
		var form = document.forms[0];
		var mensagem = "";
		
		if(validaRadioButtonTres(form.indicadorDocumentoEntregue,"Documento Entregue") != ""){
			mensagem = mensagem + validaRadioButtonTres(form.indicadorDocumentoEntregue,"Documento Entregue")+"\n";
		}
		
		if (form.indicadorGeracaoDebito != undefined){
			if(validaRadioButton(form.indicadorGeracaoDebito,"Geração do Débito") != ""){
				mensagem = mensagem + validaRadioButton(form.indicadorGeracaoDebito,"Geração do Débito")+"\n";
			}
		}
		
		
		if(mensagem != ""){
			alert(mensagem);
			return false;
		}else{
			return true;
		}
   	}
	 
	function limparForm() {
	
		var form = document.InformarRetornoOSFiscalizacaoActionForm;
	
		form.idOrdemServico.focus();
		form.idOrdemServico.value = "";
		form.nomeOrdemServico.value = "";
		form.matriculaImovel.value = "";
		form.inscricaoImovel.value = "";
		form.clienteUsuario.value = "";
		form.cpfCnpjCliente.value = "";
		form.situacaoLigacaoAgua.value = "";
		form.situacaoLigacaoEsgoto.value = "";
		form.ocorrencia.value = "";
		form.situacao.value = "";
		form.indicadorTipoMedicao[0].checked = false;
		form.indicadorTipoMedicao[1].checked = false;	
		form.indicadorDocumentoEntregue[0].checked = false;
		form.indicadorDocumentoEntregue[1].checked = false;
		form.indicadorDocumentoEntregue[2].checked = false;
		form.indicadorGeracaoDebito[0].checked = false;
		form.indicadorGeracaoDebito[1].checked = false;	
		form.atendimentoMotivoEncerramento.value = "-1";
		
	 }
	 
	 function limparDecricaoEfetuar() {
	
		var form = document.InformarRetornoOSFiscalizacaoActionForm;
	
		form.nomeOrdemServico.value = "";
		form.matriculaImovel.value = "";
		form.inscricaoImovel.value = "";
		form.clienteUsuario.value = "";
		form.cpfCnpjCliente.value = "";
		form.situacaoLigacaoAgua.value = "";
		form.situacaoLigacaoEsgoto.value = "";
		form.ocorrencia.value = "";
		
	 }
	 
	 
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
   		var form = document.forms[0];
   
    	if (tipoConsulta == 'ordemServico') {
	    	form.idOrdemServico.value = codigoRegistro;
	      	form.action='exibirEfetuarInstalacaoHidrometroAction.do';
	      	form.submit();
	    }
  	}
  	
  	
  	function exibirTipoMedicao(opcaoMedicao){
  		
  		var form = document.forms[0];

	  	if (opcaoMedicao == "1"){
	  		document.getElementById("tipoMedicao").style.display = 'block';
	  	}
	  	else{
	  		document.getElementById("tipoMedicao").style.display = 'none';
	  	}
	}
	
	
	function exibirGeracaoDebito(opcaoGeracao){
  		
  		var form = document.forms[0];

	  	if (form.indicadorGeracaoDebito != undefined){
		  	
		  	if (opcaoGeracao == "1"){
		  		document.getElementById("geracaoDebito").style.display = 'block';
		  	
		  		form.indicadorGeracaoDebito[0].checked = true;
		  		form.indicadorGeracaoDebito[1].checked = false;
		  	}
		  	else{
		  		document.getElementById("geracaoDebito").style.display = 'none';
		  	
		  		form.indicadorGeracaoDebito[0].checked = false;
		  		form.indicadorGeracaoDebito[1].checked = true;
		  	}
		  	
	  	}
	}

</script>

</head>

<body leftmargin="5" topmargin="5"
	onload="setarFoco('${requestScope.nomeCampo}');">

<html:form action="/informarRetornoOSFiscalizacaoAction.do"
	name="InformarRetornoOSFiscalizacaoActionForm"
	type="gcom.gui.atendimentopublico.ordemservico.InformarRetornoOSFiscalizacaoActionForm"
	method="post">





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
					<td class="parabg">Informar Retorno Ordem de Fiscalização</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td height="31">
					<table width="100%" border="0" align="center">
						<tr>
							<td height="10" colspan="2">Para informar o retorno da
							fiscalização , informe os dados abaixo:</td>
						</tr>
						<tr>
							<td height="10"><strong>Ordem de Servi&ccedil;o:<strong><font
								color="#FF0000">*</font></strong></strong></td>
							<td><!-- Campo Ordem de Serviço --> <html:text
								property="idOrdemServico" size="12" maxlength="10"
								onkeypress="validaEnterComMensagem(event, 'exibirInformarRetornoOSFiscalizacaoAction.do', 'idOrdemServico','Ordem de Serviço');"
								onkeyup="javascript:limparDecricaoEfetuar()" /> <a
								href="javascript:abrirPopup('exibirPesquisarOrdemServicoAction.do', 600, 500);">
							<img src="/gsan/imagens/pesquisa.gif" alt="Pesquisar" border="0"
								height="21" width="23"></a> <logic:present
								name="ordemServicoEncontrado">
								<logic:equal name="ordemServicoEncontrado" value="exception">
									<html:text property="nomeOrdemServico" size="50" maxlength="40"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:equal>
								<logic:notEqual name="ordemServicoEncontrado" value="exception">
									<html:text property="nomeOrdemServico" size="50" maxlength="40"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEqual>
							</logic:present> <logic:notPresent name="ordemServicoEncontrado">
								<logic:empty name="InformarRetornoOSFiscalizacaoActionForm"
									property="idOrdemServico">
									<html:text property="nomeOrdemServico" size="50" maxlength="40"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:empty>
								<logic:notEmpty name="InformarRetornoOSFiscalizacaoActionForm"
									property="idOrdemServico">
									<html:text property="nomeOrdemServico" size="50" maxlength="40"
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEmpty>
							</logic:notPresent> <a href="javascript:limparForm()"> <img
								border="0" title="Apagar"
								src="<bean:message key='caminho.imagens'/>limparcampo.gif" /></a></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td align="center" colspan="2">
							<table width="100%" border="0" bgcolor="#99CCFF">
								<tr bgcolor="#99CCFF">
									<td height="18" colspan="2">
									<div align="center"><strong>Dados do Imóvel </strong></div>
									</td>
								</tr>
								<tr bgcolor="#cbe5fe">
									<td>
									<table border="0" width="100%">
										<tr>
											<td width="37%" height="10"><strong>Matr&iacute;cula do
											Im&oacute;vel:<strong></strong></strong></td>
											<td width="58%"><html:text property="matriculaImovel"
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000"
												size="15" maxlength="20" /> <html:text
												property="inscricaoImovel" readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000"
												size="21" maxlength="20" /></td>
										</tr>
										<tr>
											<td><strong> Cliente Usu&aacute;rio:</strong></td>
											<td><html:text property="clienteUsuario" readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000"
												size="50" maxlength="50" /></td>
										</tr>
										<tr>
											<td><strong>CPF ou CNPJ:</strong></td>
											<td><html:text property="cpfCnpjCliente" readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000"
												size="20" maxlength="20" /></td>
										</tr>
										<tr>
											<td><strong>Situa&ccedil;&atilde;o da Liga&ccedil;&atilde;o
											de &Aacute;gua:</strong></td>
											<td><html:text property="situacaoLigacaoAgua" readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000"
												size="20" maxlength="20" /></td>
										</tr>
										<tr>
											<td><strong>Situa&ccedil;&atilde;o da Liga&ccedil;&atilde;o
											de Esgoto:</strong></td>

											<td><html:text property="situacaoLigacaoEsgoto"
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000"
												size="20" maxlength="20" /></td>
										</tr>
										<tr>
											<td><strong>Ocorrência:</strong></td>

											<td><html:text property="ocorrencia" readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000"
												size="35" maxlength="35" /></td>
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
									<div align="center"><strong>Dados do Retorno</strong></div>
									</td>
								</tr>
								<tr bgcolor="#cbe5fe">
									<td>
									<table border="0" width="100%">
										<tr>
											<td width="25%" class="style3"><strong>Situação Encontrada:<font
												color="#FF0000">*</font></strong></td>

											<td width="75%"><!-- O atributo id está se referindo ao campo indicadorOpcaoMedicao que será utilizado para
								            obrigar ou não o usuário de informar o tipo de medição -->
											<select name="situacao" tabindex="2"
												onchange="exibirTipoMedicao(this.options[this.selectedIndex].id); exibirGeracaoDebito(this.options[this.selectedIndex].name)">
												<option id="" value="">&nbsp;</option>

												<logic:iterate name="colecaoFiscalizacaoSituacao"
													id="situacaoEncontradaHelper"
													type="SituacaoEncontradaHelper">
													<option
														name="<%=""+ situacaoEncontradaHelper.getGeracaoDebito() %>"
														id="<%=""+ situacaoEncontradaHelper.getFiscalizacaoSituacao().getIndicadorOpcaoMedicao() %>"
														value="<%=""+ situacaoEncontradaHelper.getFiscalizacaoSituacao().getId() %>"><%=""
								+ situacaoEncontradaHelper
										.getFiscalizacaoSituacao()
										.getDescricaoFiscalizacaoSituacao()%></option>
												</logic:iterate>

											</select></td>
										</tr>
									</table>

									<!-- Caso a situação encontrada obrigue informar a situação de medição  -->
									<div id="tipoMedicao" style="display:none">

									<table border="0" width="100%">
										<tr>
											<td width="150"><strong>Tipo de Medição:</strong></td>
											<td><html:radio property="indicadorTipoMedicao" value="1" />
											<strong> Ligação de Agua </strong> <html:radio
												property="indicadorTipoMedicao" value="2" /> <strong> Poço</strong>
											</td>
										</tr>
									</table>

									</div>

									<table border="0" width="100%">
										<tr>
											<td width="150"><strong>Documento Entregue:<font
												color="#FF0000">*</font></strong></td>
											<td><html:radio property="indicadorDocumentoEntregue"
												value="1" /> <strong> Sol. Comparecimento </strong> <html:radio
												property="indicadorDocumentoEntregue" value="2" /> <strong>
											Auto de Infração</strong> <html:radio
												property="indicadorDocumentoEntregue" value="3" /> <strong>
											Nenhum</strong></td>
										</tr>
									</table>

									<div id="geracaoDebito" style="display:block"><logic:present
										name="disponibilizarNaoGeracaoDebito" scope="request">

										<table border="0" width="100%">
											<tr>
												<td width="150"><strong> Geração do Débito:<font
													color="#FF0000">*</font></strong></td>
												<td><html:radio property="indicadorGeracaoDebito" value="1" />
												<strong> Sim </strong> <html:radio
													property="indicadorGeracaoDebito" value="2" /> <strong>Não</strong>
												</td>
											</tr>
										</table>

									</logic:present></div>

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
									<div align="center"><strong>Dados Encerramento Ordem de Serviço</strong></div>
									</td>
								</tr>
								<tr bgcolor="#cbe5fe">
									<td>
									<table border="0" width="100%">

										<tr>
											<td width="150"><strong>Deseja Encerrar a Ordem de Serviço:<font
													color="#FF0000">*</font></strong></td>
											<td><html:radio property="indicadorEncerramentoOS" value="1"
												onclick="desabilitaCombo('1');" /> <strong>Sim</strong> <html:radio
												property="indicadorEncerramentoOS" value="2"
												onclick="desabilitaCombo('2');" /> <strong>Não</strong>
											</td>
										</tr>

										<tr>
											<td><strong>Motivo Encerramento Ordem Serviço:</strong></td>
											<td><html:select property="atendimentoMotivoEncerramento">
												<html:option value="-1">&nbsp;</html:option>
												<html:options
													collection="colecaoAtendimentoMotivoEncerramento"
													labelProperty="descricao" property="id" />
											</html:select> <font size="1">&nbsp; </font></td>
										</tr>


									</table>

									<!-- Caso a situação encontrada obrigue informar a situação de medição  -->
								
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
							<td colspan="2"><input name="Button" type="button"
								class="bottonRightCol" value="Limpar" onclick="limparForm();"></td>
							<td align="right"><input name="Button" type="button"
								class="bottonRightCol" value="Atualizar" onclick="validaForm();">
							</td>
						</tr>
					</table>
					</td>

				</tr>
				<!--</tr></table></td>-->
			</table>
	</table>
	<!-- Fim do Corpo-->

	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>

<script language="JavaScript">

</script>
</html:html>
