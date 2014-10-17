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

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="EfetuarSubstituicaoHidrometroActionForm" />

<script language="JavaScript"> 
	 function validaForm(){
    	var form = document.forms[0];
    	
    	if(form.indicadorObrigatoriedadeFuncionario.value == 1 && trim(form.idFuncionario.value) == ''){
			alert("Matr�cula Funcion�rio � um campo obrigat�rio");
			return false;
		}
	  	
	  	if(validateEfetuarSubstituicaoHidrometroActionForm(form)&& validaDebito()){
			if(validaTodosRadioButton()){		     
		  		submeterFormPadrao(form);
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
	 
  	function validaRadioButton(nomeCampo,mensagem){
		var alerta = "";
		if(!nomeCampo[0].checked && !nomeCampo[1].checked){
			alerta = "Informe " + mensagem +".";
		}
		return alerta;
   	}
   
  	function validaTodosRadioButton(){
		
		var form = document.forms[0];
		var mensagem = "";
		
		if(validaRadioButton(form.tipoMedicao,"Tipo de Medi��o") != ""){
			mensagem = mensagem + validaRadioButton(form.tipoMedicao,"Tipo de Medi��o")+"\n";
		}
		
		if(validaRadioButton(form.situacaoCavalete,"Situa��o do Cavalete") != ""){
			mensagem = mensagem + validaRadioButton(form.situacaoCavalete,"Situa��o do Cavalete")+"\n";
		} 
		if(mensagem != ""){
			alert(mensagem);
			return false;
		}else{
			return true;
		}
   	}
	 
	function limparForm() {

		var form = document.EfetuarSubstituicaoHidrometroActionForm;

		form.idOrdemServico.value = "";
		form.nomeOrdemServico.value = "";
		form.matriculaImovel.value = "";
		form.inscricaoImovel.value = "";
		form.clienteUsuario.value = "";
		form.cpfCnpjCliente.value = "";
		form.situacaoLigacaoAgua.value = "";
		form.situacaoLigacaoEsgoto.value = "";
		form.dataInstalacao.value = "";	
		form.leituraInstalacao.value = "";
		form.numeroSelo.value = "";
		form.numeroHidrometro.value = "";
		form.protecao.selectedIndex = 0;
		form.localInstalacao.selectedIndex = 0;
		form.situacaoCavalete[0].checked = false;
		form.situacaoCavalete[1].checked = false;	
		form.situacaoCavalete.value = "";
		form.tipoMedicao[0].checked = false;
		form.tipoMedicao[1].checked = false;	
		form.tipoMedicao.value = "";
		form.numeroHidrometroAtual.value = "";
        form.tipoMedicaoAtual.value = "";
        form.dataRetirada.value = "";
        form.numeroLeitura.value = "";
        form.situacaoHidrometro.selectedIndex = 0;
        form.localArmazenagemHidrometro.selectedIndex = 0;
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
   		
   		form.indicadorTrocaProtecao[0].checked = false;
		form.indicadorTrocaProtecao[1].checked = true;	
		
		form.indicadorTrocaRegistro[0].checked = false;
		form.indicadorTrocaRegistro[1].checked = true;
   		
   		form.idOrdemServico.focus();
	 }
	 
	 function limparDecricaoEfetuar() {

		var form = document.EfetuarSubstituicaoHidrometroActionForm;

		form.nomeOrdemServico.value = "";
		form.matriculaImovel.value = "";
		form.inscricaoImovel.value = "";
		form.clienteUsuario.value = "";
		form.cpfCnpjCliente.value = "";
		form.situacaoLigacaoAgua.value = "";
		form.situacaoLigacaoEsgoto.value = "";
		form.dataInstalacao.value = "";	
		form.numeroHidrometroAtual.value = "";
        form.tipoMedicaoAtual.value = "";
        form.dataRetirada.value = "";
        form.numeroLeitura.value = "";
        form.idFuncionario.value = "";
		form.descricaoFuncionario.value = "";
        form.situacaoHidrometro.selectedIndex = 0;
        form.localArmazenagemHidrometro.selectedIndex = 0;
		form.tipoMedicao[0].checked = false;
		form.tipoMedicao[1].checked = false;	
        if(form.idTipoDebito != null){
         form.idTipoDebito.value = "";
    	 form.descricaoTipoDebito.value = "";
	     form.valorDebito.value = "";
	    }		        
	 }  
	 
	function limparNumeroHidrometro(){
		var form = document.EfetuarSubstituicaoHidrometroActionForm;
	   	form.numeroHidrometro.value = "";
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

	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
   		var form = document.forms[0];
   
    	if (tipoConsulta == 'hidrometro') {
      		form.numeroHidrometro.value = codigoRegistro;
    	}else if (tipoConsulta == 'ordemServico') {
	    	form.idOrdemServico.value = codigoRegistro;
	      	form.action='exibirEfetuarSubstituicaoHidrometroAction.do';
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

	//verifica se existe alguma restri��o 
	//para exibi��o alguma campo no formulario
    function verificaForm() {

       	var form = document.forms[0];

		if(form.veioEncerrarOS.value == 'true'){
			form.idOrdemServico.disabled=true;
		}else{
			form.idOrdemServico.disabled=false;
		}       	
	}
	
	
	function calcularValores(){
	
		var form = document.EfetuarSubstituicaoHidrometroActionForm;
	   	if (validateEfetuarSubstituicaoHidrometroActionForm(form) && validaDebito()){
	   	
	   		form.action='exibirEfetuarSubstituicaoHidrometroAction.do?calculaValores=S';
	      	form.submit();
		}
	}
	
	//verifica se o motivo da n�o cobra�a naum foi informado
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

<html:form action="/efetuarSubstituicaoHidrometroAction.do"
	name="EfetuarSubstituicaoHidrometroActionForm"
	type="gcom.gui.atendimentopublico.hidrometro.EfetuarSubstituicaoHidrometroActionForm"
	method="post">

	<html:hidden property="veioEncerrarOS" />
	<html:hidden property="qtdeMaxParcelas" />

	<logic:notPresent name="semMenu">
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
									<td class="parabg">Efetuar Substitui��o de Hidr�metro</td>
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
											<td height="10" colspan="2">Para efetuar a substitui��o do
											hidr�metro, informe os dados abaixo:.</td>
										</tr>

										<tr>

											<td height="10"><strong>Ordem de Servi&ccedil;o:<span
												class="style2"> <strong><font color="#FF0000">*</font></strong></span>
											</strong></td>

											<td><span class="style2"> <html:text
												property="idOrdemServico" size="8" maxlength="9"
												onkeypress="validaEnterComMensagem(event, 'exibirEfetuarSubstituicaoHidrometroAction.do', 'idOrdemServico','Ordem de Servi�o');"
												onkeyup="javascript:limparDecricaoEfetuar()" /> <a
												href="javascript:chamarPopup('exibirPesquisarOrdemServicoAction.do', 'ordemServico', null, null, 600, 500, '',document.forms[0].idOrdemServico);">
											<img width="23" height="21" border="0"
												src="<bean:message key="caminho.imagens"/>pesquisa.gif"
												title="Pesquisar OS" /></a> <logic:present
												name="ordemServicoEncontrado">
												<logic:equal name="ordemServicoEncontrado" value="exception">
													<html:text property="nomeOrdemServico" size="50"
														maxlength="40" readonly="true"
														style="background-color:#EFEFEF; border:0; color: #ff0000" />
												</logic:equal>
												<logic:notEqual name="ordemServicoEncontrado"
													value="exception">
													<html:text property="nomeOrdemServico" size="50"
														maxlength="40" readonly="true"
														style="background-color:#EFEFEF; border:0; color: #000000" />
												</logic:notEqual>
											</logic:present> <logic:notPresent
												name="ordemServicoEncontrado">
												<logic:empty name="EfetuarSubstituicaoHidrometroActionForm"
													property="idOrdemServico">
													<html:text property="nomeOrdemServico" size="50"
														maxlength="40" readonly="true"
														style="background-color:#EFEFEF; border:0; color: #ff0000" />
												</logic:empty>
												<logic:notEmpty
													name="EfetuarSubstituicaoHidrometroActionForm"
													property="idOrdemServico">
													<html:text property="nomeOrdemServico" size="50"
														maxlength="40" readonly="true"
														style="background-color:#EFEFEF; border:0; color: #000000" />
												</logic:notEmpty>
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
													<div align="center">Dados do Im�vel</div>
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
															<td><strong>Situa&ccedil;&atilde;o da
															Liga&ccedil;&atilde;o de &Aacute;gua:</strong></td>
															<td><html:text property="situacaoLigacaoAgua"
																readonly="true"
																style="background-color:#EFEFEF; border:0; color: #000000"
																size="20" maxlength="20" /></td>
														</tr>
														<tr>
															<td><strong>Situa&ccedil;&atilde;o da
															Liga&ccedil;&atilde;o de Esgoto:</strong></td>

															<td><html:text property="situacaoLigacaoEsgoto"
																readonly="true"
																style="background-color:#EFEFEF; border:0; color: #000000"
																size="20" maxlength="20" /></td>
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
													Substitui��o do Hidr�metro</span></div>
													</td>
												</tr>
												<tr bgcolor="#cbe5fe">
													<td>
													<table border="0" width="100%">
														<tr>
															<td><strong> N�mero do Hidr�metro Atual:</strong></td>
															<td><html:text property="numeroHidrometroAtual"
																readonly="true"
																style="background-color:#EFEFEF; border:0; color: #000000"
																size="10" maxlength="10" /></td>
														</tr>
														<tr>
															<td><strong> Tipo de Medi��o:</strong></td>
															<td><html:text property="tipoMedicaoAtual"
																readonly="true"
																style="background-color:#EFEFEF; border:0; color: #000000"
																size="25" maxlength="25" /></td>
														</tr>
														<tr>
															<td><strong> Data da Retirada:</strong></td>
															<td><html:text property="dataRetirada" readonly="true"
																style="background-color:#EFEFEF; border:0; color: #000000"
																size="10" maxlength="10" /></td>
														</tr>
														<tr>
															<td><strong> N�mero da Leitura:</strong></td>
															<td colspan="2"><strong><b><span class="style2"> <html:text
																property="numeroLeitura" size="6" maxlength="6"
																onkeyup="" onkeydown="" onkeypress="" /> </span></b></strong>
															</td>
														</tr>
														<tr>
															<td><strong> Situa��o do Hidr�metro:<font color="#FF0000">*</font></strong></td>
															<td><html:select property="situacaoHidrometro"
																tabindex="4">
																<html:option value="-1">&nbsp;</html:option>
																<html:options collection="colecaoHidrometroSituacao"
																	labelProperty="descricao" property="id" />
															</html:select> <font size="1">&nbsp; </font></td>
														</tr>

														<tr>
															<td><strong> Local de Armazenagem: <font color="#FF0000">*</font>
															</strong></td>

															<td><html:select property="localArmazenagemHidrometro"
																tabindex="4">
																<html:option value="-1">&nbsp;</html:option>
																<html:options
																	collection="colecaoHidrometroLocalArmazenagem"
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
															Hidr&ocirc;metro Novo:<font color="#FF0000">*</font></strong></td>
															<td width="58%"><html:text property="numeroHidrometro"
																size="13" maxlength="10"
																onkeypress="validaEnterString(event, 'exibirEfetuarSubstituicaoHidrometroAction.do?pesquisaHidrometro=sim', 'numeroHidrometro','N�mero do Hidr�metro');" />
															<a
																href="javascript:abrirPopup('exibirPesquisarHidrometroAction.do',600,640);">
															<img src="/gsan/imagens/pesquisa.gif" alt="Pesquisar"
																border="0" height="21" width="23"></a> <a
																href="javascript:limparNumeroHidrometro()"> <img
																border="0" title="Apagar"
																src="<bean:message key='caminho.imagens'/>limparcampo.gif" /></a>
															</td>
														</tr>
														<tr>
															<td height="10"><strong>Data da Instala��o:<font
																color="#FF0000">*</font></strong></td>
															<td colspan="2"><strong><b> <!-- Campo Data da Ligacao -->
															<html:text property="dataInstalacao" readonly="true"
																style="background-color:#EFEFEF; border:0;" size="10"
																maxlength="10" /> </b></strong></td>
														</tr>
														<tr>
															<td><strong>Tipo de Medi��o:<font color="#FF0000">*</font></strong></td>
															<td><strong> <html:radio property="tipoMedicao" value="1"
																disabled="true" /> <strong>LIGA��O DE �GUA <html:radio
																property="tipoMedicao" value="2" disabled="true" />
															PO�O</strong> </strong></td>
														</tr>
														<tr>
															<td><strong>Local de Instala��o:<font color="#FF0000">*</font></strong></td>
															<td><html:select property="localInstalacao" tabindex="4">
																<html:option value="-1">&nbsp;</html:option>
																<html:options
																	collection="colecaoHidrometroLocalInstalacao"
																	labelProperty="descricao" property="id" />
															</html:select> <font size="1">&nbsp; </font></td>
														</tr>
														<tr>
															<td><strong>Prote��o:<font color="#FF0000">*</font></strong></td>
															<td><html:select property="protecao" tabindex="4">
																<html:option value="-1">&nbsp;</html:option>
																<html:options collection="colecaoHidrometroProtecao"
																	labelProperty="descricao" property="id" />
															</html:select> <font size="1">&nbsp; </font></td>
														</tr>
														<tr>
															<td><strong>Troca de Prote��o:</strong></td>
															<td><strong> 
																<html:radio property="indicadorTrocaProtecao" value="1"/><strong>SIM 
																<html:radio	property="indicadorTrocaProtecao" value="2"/>N�O</strong>
															</strong></td>
														</tr>
														
														<tr>
															<td><strong>Troca de Registro:</strong></td>
															<td><strong> 
																<html:radio property="indicadorTrocaRegistro" value="1"/><strong>SIM 
																<html:radio	property="indicadorTrocaRegistro" value="2"/>N�O</strong>
															</strong></td>
														</tr>
														<tr>
															<td class="style3"><strong>Leitura Instala��o:</strong></td>
															<td colspan="2"><strong><b><span class="style2"> <html:text
																property="leituraInstalacao" size="6" maxlength="6"
																onkeyup="" onkeydown="" onkeypress="" /> </span></b></strong></td>
														</tr>
														<tr>
															<td class="style3"><strong>N�mero do Selo:</strong></td>
															<td colspan="2"><strong><b><span class="style2"> <html:text
																property="numeroSelo" size="12" maxlength="12"
																onkeyup="" onkeydown="" onkeypress="" /> </span></b></strong></td>
														</tr>
														<tr>
															<td><strong>Cavalete:<font color="#FF0000">*</font></strong></td>
															<td><strong> <html:radio property="situacaoCavalete"
																value="1" /> <strong>COM <html:radio
																property="situacaoCavalete" value="2" /> SEM</strong> </strong></td>
														</tr>
														<tr>
															<logic:equal name="indicadorObrigatoriedadeFuncionario" value="1">
															<td WIDTH="20%"><strong>Matr�cula Funcion�rio:<font color="#FF0000">*</font></strong></td>
															</logic:equal>
															<logic:notEqual name="indicadorObrigatoriedadeFuncionario" value="1">
															<td WIDTH="20%"><strong>Matr�cula Funcion�rio:</strong></td>
															</logic:notEqual>
															<td width="80%" colspan="3"><html:text property="idFuncionario"
																size="8" maxlength="8" tabindex="1"
																onkeypress="limparDescricao();validaEnterComMensagem(event, 'exibirEfetuarSubstituicaoHidrometroAction.do', 'idFuncionario', 'Funcion�rio');" />
															<a 
																href="javascript:abrirPopup('exibirEfetuarSubstituicaoHidrometroAction.do?pesquisarFuncionario=OK',400,400);">
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
										
																<logic:empty name="EfetuarSubstituicaoHidrometroActionForm"
																	property="idFuncionario">
																	<html:text property="descricaoFuncionario" value="" size="30"
																		readonly="true"
																		style="background-color:#EFEFEF; border:0; color: #ff0000" />
																</logic:empty>
																<logic:notEmpty name="EfetuarSubstituicaoHidrometroActionForm"
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

								<%-- Dados da Gera��o --%>
								<logic:notEmpty name="EfetuarSubstituicaoHidrometroActionForm"
									property="idTipoDebito">
									<tr>
										<td height="31">
										<table width="100%" border="0" align="center">
											<tr bgcolor="#cbe5fe">
												<td align="center" colspan="2">
												<table width="100%" border="0" bgcolor="#99CCFF">
													<tr bgcolor="#99CCFF">
														<td height="18" colspan="2">
														<div align="center"><span class="style2"> Dados da
														Gera&ccedil;&atilde;o do D&eacute;bito</span></div>
														</td>
													</tr>
													<tr bgcolor="#cbe5fe">
														<td>
														<table border="0" width="100%">
															<tr>
																<td width="37%" class="style3"><strong> Tipo de
																D&eacute;bito: </strong></td>
																<td width="58%"><html:text property="idTipoDebito"
																	readonly="true"
																	style="background-color:#EFEFEF; border:0; color: #000000"
																	size="5" maxlength="5" /> <html:text
																	property="descricaoTipoDebito" readonly="true"
																	style="background-color:#EFEFEF; border:0; color: #000000"
																	size="35" maxlength="50" /></td>
															</tr>
															
															
															<!-- Colocado por Raphael Rossiter em 20/04/2007 -->
															<logic:notEqual name="EfetuarSubstituicaoHidrometroActionForm" property="alteracaoValor" value="OK">
															<tr>
																<td height="10"><strong> Valor do D&eacute;bito: </strong>
																</td>
																<td><html:text property="valorDebito" readonly="true"
																	style="background-color:#EFEFEF; border:0; color: #000000; text-align: right;"
																	size="20" maxlength="20" /></td>
															</tr>
															</logic:notEqual>
															
															<logic:equal name="EfetuarSubstituicaoHidrometroActionForm" property="alteracaoValor" value="OK">
															<tr>
																<td height="10"><strong> Valor do D&eacute;bito: </strong>
																</td>
																<td><html:text property="valorDebito" 
																	style="text-align: right;"
																	size="20" maxlength="20" /></td>
															</tr>
															</logic:equal>
															
															
															<logic:present name="permissaoMotivoNaoCobranca">
																<tr>
																	<td class="style3"><strong>Motivo da N�o Cobran�a:<font
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
																	<td class="style3"><strong>Percentual de Cobran�a: </strong></td>
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
																	<td class="style3"><strong>Percentual de Cobran�a: <font
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
					</table>
					<!-- Fim do Corpo-->

					<logic:notPresent name="semMenu">
						<%@ include file="/jsp/util/rodape.jsp"%>
					</logic:notPresent>

					</html:form>

					<script language="JavaScript">

</script>
					</html:html>