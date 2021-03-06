<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@ page import="gcom.cobranca.bean.ContaValoresHelper" isELIgnored="false"%>
<%@ page import="gcom.cobranca.bean.GuiaPagamentoValoresHelper" isELIgnored="false"%>
<%@ page import="gcom.cobranca.bean.DebitoACobrarValoresHelper" isELIgnored="false"%>
<%@ page import="gcom.util.ConstantesSistema" isELIgnored="false"%>
<%@ page import="java.util.Collection" isELIgnored="false"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<%@page import="gcom.gui.cobranca.EfetuarParcelamentoDebitosWizardAction"%>
<%@page import="gcom.cobranca.bean.ContaHelper"%><html:html>
<head>
	<%@ include file="/jsp/util/titulo.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
	<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
	<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
	<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/engine.js'> </script>
	<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/util.js'> </script>
	<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/interface/AjaxService.js'> </script>


<script language="JavaScript">
<!--
// Faz as valida��es do formul�rio
	function validateEfetuarParcelamentoDebitosActionForm(){
		return true;
	}
	
	function enviar() {
		redirecionarSubmit('efetuarParcelamentoDebitosWizardAction.do?action=exibirEfetuarParcelamentoDebitosProcesso2Action&calcula=1');
	}
	
	function limparRadioButtonTodos() {
		redirecionarSubmit('efetuarParcelamentoDebitosWizardAction.do?action=exibirEfetuarParcelamentoDebitosProcesso2Action&limpaCombo=1');
	}

	function limpaRadioButton(nomeCampo) {
		// Este m�todo est� causando falha no pareclamento, o evento correto � o bot�o de Limpar EP/NB 
	    /*
		var form = document.forms[0];
	  	if(nomeCampo.checked && nomeCampo.id != "0") {
	   		nomeCampo.checked = false;
			nomeCampo.id = "0";
		} else {		
			nomeCampo.id = "1";
			for (i=0; i < form.elements.length; i++){
				if (form.elements[i].type == "radio" && form.elements[i].name == nomeCampo.name	&& form.elements[i].checked == false) {
					form.elements[i].id = 0;
					
				}
			}
		}*/
	}
	
	// Sempre que entrar na tela verifica se 'chavesPrestacoes' tem itens, caso positivo marcar 'checkbox'
	function marcarPrestacoesSelecionadas() {
		var form = document.forms[0];
		var chavesPrestacoes = form.chavesPrestacoes.value;

		if (chavesPrestacoes != null && chavesPrestacoes != '' && chavesPrestacoes != 'undefined') {
			
			myString = new String(form.chavesPrestacoes.value);
			splitString = myString.split("$");
			
			for (i=0; i<splitString.length; i++) {
			
				var elementos = form.elements['prestacoesSelecionadas'];
				
				var docLength = elementos.length;
				if (typeof docLength == 'undefined') {
					if (splitString[i] == elementos.value) {
						elementos.checked = true;
					}
				} else {
					for (j=0; j<docLength; j++) {
						if (splitString[i] == elementos[j].value) {
							elementos[j].checked = true;
						}
					}
				}
			}
		}
	}
	
	function marcarSucumbenciasContasSelecionadas() {

		var form = document.forms[0];
		var chavesSucumbenciasConta = form.chavesSucumbenciasConta.value;

		if (chavesSucumbenciasConta != null && chavesSucumbenciasConta != '' && chavesSucumbenciasConta != 'undefined') {
			
			myString = new String(form.chavesSucumbenciasConta.value);
			splitString = myString.split("$");
			
			for (i=0; i<splitString.length; i++) {
			
				var elementos = form.elements['sucumbenciasContasSelecionadas'];
				
				var docLength = elementos.length;
				if (typeof docLength == 'undefined') {
					if (splitString[i] == elementos.value) {
						elementos.checked = true;
					}
				} else {
					for (j=0; j<docLength; j++) {
						if (splitString[i] == elementos[j].value) {
							elementos[j].checked = true;
						}
					}
				}
			}
		}
	}

	function marcarSucumbenciasGuiasSelecionadas() {

		var form = document.forms[0];
		var chavesSucumbenciasGuia = form.chavesSucumbenciasGuia.value;

		if (chavesSucumbenciasGuia != null && chavesSucumbenciasGuia != '' && chavesSucumbenciasGuia != 'undefined') {
			
			myString = new String(form.chavesSucumbenciasGuia.value);
			splitString = myString.split("$");
			
			for (i=0; i<splitString.length; i++) {
			
				var elementos = form.elements['sucumbenciasGuiasSelecionadas'];
				
				var docLength = elementos.length;
				if (typeof docLength == 'undefined') {
					if (splitString[i] == elementos.value) {
						elementos.checked = true;
					}
				} else {
					for (j=0; j<docLength; j++) {
						if (splitString[i] == elementos[j].value) {
							elementos[j].checked = true;
						}
					}
				}
			}
		}
	}
	
	// Sempre que entrar na tela verifica se 'chaveDebitosACobrar' tem itens, caso positivo marcar 'checkbox'
	function marcarDebitoACobrarSelecionados() {

		var form = document.forms[0];
		var chavesDebitosACobrar = form.chavesDebitosACobrar.value;

		if (chavesDebitosACobrar != null && chavesDebitosACobrar != '' && chavesDebitosACobrar != 'undefined') {
			
			myString = new String(form.chavesDebitosACobrar.value);
			splitString = myString.split("$");
			
			for (i=0; i<splitString.length; i++) {
			
				var elementos = form.elements['debitoSelecionado'];
				
				var docLength = elementos.length;
				if (typeof docLength == 'undefined') {
					if (splitString[i].toUpperCase() == elementos.value.toUpperCase()) {
						elementos.checked = true;
					}
				} else {
					for (j=0; j<docLength; j++) {
						if (splitString[i].toUpperCase() == elementos[j].value.toUpperCase()) {
							elementos[j].checked = true;
						}
					}
				}
			}
		}
	}	
	
	// Para cada item marcado ou desmarcado, atualizar 'chave' no form
	function montarChaveDebitosACobrar(objeto) {

		var form = document.forms[0];
		var retorno = form.chavesDebitosACobrar.value;
		var valorObjeto = objeto.value;
		var selecionados = form.elements['debitoSelecionado'];

		if (objeto.checked) {

			if (!chaveJaSelecionada(valorObjeto,retorno)) {
				if (retorno != null && retorno != '' && retorno != 'undefined') {
					retorno = retorno+"$"+valorObjeto;
				} else {
					retorno = valorObjeto;
				}
			}
		    
			for (i=0; i< selecionados.length; i++) {
				if(selecionados[i].value == valorObjeto) {
					selecionados[i].checked = true;
				}
			}
		} else {
			myString = new String(form.chavesDebitosACobrar.value);
			splitString = myString.split("$");
			
			for (i=0; i< splitString.length; i++) {

				if (splitString[i].toUpperCase() == (new String(valorObjeto).toUpperCase())) {
					splitString.splice( i, 1 );
					break;
				}
			}
			
			retorno = "";
			for (i=0; i< splitString.length; i++) {
				if(retorno == ""){
					retorno = splitString[i];
				} else {
					retorno = retorno+"$"+splitString[i];
				}
			}

			for (i=0; i< selecionados.length; i++) {
				if (selecionados[i].value == valorObjeto) {
					selecionados[i].checked = false;
				}
			}
		}
		
		form.chavesDebitosACobrar.value = retorno;
	}	
	

	// Para cada item marcado ou desmarcado, atualizar 'chave' no form
	function montarChavePrestacoes(objeto) {

		var form = document.forms[0];
		var retorno = form.chavesPrestacoes.value;
		var valorObjeto = objeto.value;
		var selecionados = form.elements['prestacoesSelecionadas'];
		var selecionadosParcelamento = form.elements['prestacoesSelecionadasParcelamento'];

		if (objeto.checked) {

			if (!chaveJaSelecionada(valorObjeto,retorno)) {
				if (retorno != null && retorno != '' && retorno != 'undefined') {
					retorno = retorno+"$"+valorObjeto;
				} else {
					retorno = valorObjeto;
				}
			}
		    
			for (i=0; i< selecionados.length; i++) {
				if(selecionados[i].value == valorObjeto) {
					selecionados[i].checked = true;					
					
					if (selecionadosParcelamento[i].value != null
							&& selecionadosParcelamento[i].value != 'undefined' 
							&& selecionadosParcelamento[i].value != '') {
						selecionarPrestacoesPorParcelamentoId(selecionadosParcelamento[i].value,1);
					}
				}
			}
		} else {
			myString = new String(form.chavesPrestacoes.value);
			splitString = myString.split("$");
			
			for (i=0; i< splitString.length; i++) {

				if (splitString[i] == valorObjeto) {
					splitString.splice( i, 1 );
					break;
				}
			}
			
			retorno = "";
			for (i=0; i< splitString.length; i++) {
				if(retorno == ""){
					retorno = splitString[i];
				} else {
					retorno = retorno+"$"+splitString[i];
				}
			}

			for (i=0; i< selecionados.length; i++) {
				if (selecionados[i].value == valorObjeto) {
					selecionados[i].checked = false;		
					
					if (selecionadosParcelamento[i].value != null
							&& selecionadosParcelamento[i].value != 'undefined' 
							&& selecionadosParcelamento[i].value != '') {
						selecionarPrestacoesPorParcelamentoId(selecionadosParcelamento[i].value,2);
					}
				}
			}
		}
		form.chavesPrestacoes.value = retorno;
	}
	
	// Marcar ou desmarcar todos os itens
	function selecionarPrestacoesPorParcelamentoId(ParcelamentoId, marcar) {
		var chaves = "";
		var form = document.forms[0];
		var selecionados = form.elements['prestacoesSelecionadas'];
		var selecionadosParcelamento = form.elements['prestacoesSelecionadasParcelamento'];

		// Se o Parcelamento Id for Igual desmarcar Todos
		if (marcar == 2) {
			for (i=0; i < selecionados.length; i++) {
				if (selecionadosParcelamento[i].value == ParcelamentoId) {
					selecionados[i].checked = false;
				}
			}
		
		} else {
			// Se o Parcelamento Id for Igual Marcar Todos
			for (i=0; i < selecionados.length; i++) {
				if (selecionadosParcelamento[i].value == ParcelamentoId) {
					selecionados[i].checked = true;
					if(chaves == "") {
						chaves = selecionados[i].value;
					} else {
						chaves = chaves + "$" + selecionados[i].value;
					}
				}
			}
		}
		form.chavesPrestacoes.value = chaves;
	}	

	// Para cada item marcado ou desmarcado, atualizar 'chave' no form
	function montarChaveSucumbenciasConta(objeto) {
		
		var form = document.forms[0];
		var retorno = form.chavesSucumbenciasConta.value;
		var valorObjeto = objeto.value;
		var selecionados = form.elements['sucumbenciasContasSelecionadas'];

		if (objeto.checked) {

			if (!chaveJaSelecionada(valorObjeto,retorno)) {
				if (retorno != null && retorno != '' && retorno != 'undefined') {
					retorno = retorno+"$"+valorObjeto;
				} else {
					retorno = valorObjeto;
				}
			}
		    
			for (i=0; i< selecionados.length; i++) {
				if(selecionados[i].value == valorObjeto) {
					selecionados[i].checked = true;
				}
			}
		} else {

			myString = new String(form.chavesSucumbenciasConta.value);
			splitString = myString.split("$");
			
			for (i=0; i< splitString.length; i++) {

				if (splitString[i] == valorObjeto) {
					splitString.splice( i, 1 );
					break;
				}
			}
			
			retorno = "";
			for (i=0; i< splitString.length; i++) {
				if(retorno == ""){
					retorno = splitString[i];
				} else {
					retorno = retorno+"$"+splitString[i];
				}
			}

			for (i=0; i< selecionados.length; i++) {
				if (selecionados[i].value == valorObjeto) {
					selecionados[i].checked = false;
				}
			}
		}
		form.chavesSucumbenciasConta.value = retorno;
	}

	function montarChaveSucumbenciasGuia(objeto) {
		
		var form = document.forms[0];
		var retorno = form.chavesSucumbenciasGuia.value;
		var valorObjeto = objeto.value;
		var selecionados = form.elements['sucumbenciasGuiasSelecionadas'];

		if (objeto.checked) {

			if (!chaveJaSelecionada(valorObjeto,retorno)) {
				if (retorno != null && retorno != '' && retorno != 'undefined') {
					retorno = retorno+"$"+valorObjeto;
				} else {
					retorno = valorObjeto;
				}
			}
		    
			for (i=0; i< selecionados.length; i++) {
				if(selecionados[i].value == valorObjeto) {
					selecionados[i].checked = true;
				}
			}
		} else {

			myString = new String(form.chavesSucumbenciasGuia.value);
			splitString = myString.split("$");
			
			for (i=0; i< splitString.length; i++) {

				if (splitString[i] == valorObjeto) {
					splitString.splice( i, 1 );
					break;
				}
			}
			
			retorno = "";
			for (i=0; i< splitString.length; i++) {
				if(retorno == ""){
					retorno = splitString[i];
				} else {
					retorno = retorno+"$"+splitString[i];
				}
			}

			for (i=0; i< selecionados.length; i++) {
				if (selecionados[i].value == valorObjeto) {
					selecionados[i].checked = false;
				}
			}
		}
		form.chavesSucumbenciasGuia.value = retorno;
	}

	function chaveJaSelecionada(valorObjeto, chavePesquisa) {
		var retorno = false;
		var form = document.forms[0];

		if (chavePesquisa != null && chavePesquisa != "") {

			myString = new String(chavePesquisa);
			splitString = myString.split("$");

			for (i=0; i< splitString.length; i++) {
				if(splitString[i] == valorObjeto) {
					retorno = true;
					break;
				}
			}
		}
		return retorno;
	}
	
	
	// Marcar ou desmarcar todos os itens
	function selecionarPrestacoes() {
		var chaves = "";
		var form = document.forms[0];
		var selecionados = form.elements['prestacoesSelecionadas'];

		// Se o 1� estiver marcado, desmarcar todos
		if (selecionados[1] != null && selecionados[1].checked) {
			for (i=0; i < selecionados.length; i++) {
				selecionados[i].checked = false;
			}
		
		} else {
			// Se o 1� n�o estiver marcado, marcar todos
			for (i=0; i < selecionados.length; i++) {
				selecionados[i].checked = true;
				if(chaves == "") {
					chaves = selecionados[i].value;
				} else {
					chaves = chaves + "$" + selecionados[i].value;
				}
			}
		}
		form.chavesPrestacoes.value = chaves;
	}

	function configurarConta(idConta, tipo, checked){

		if(!checked && tipo == 'EP'){

			tipo = 'NB';
			
		}

		AjaxService.configurarConta( idConta, tipo, function(dados){});

	}

	// Marcar ou desmarcar todos os itens de debitos
	function selecionarDebitos() {
		var form = document.forms[0];
		var selecionados = form.elements['debitoSelecionado'];
		var docLength = selecionados.length;
		var chaves = "";

		// Caso s� tenha 1 elemento, docLength ser� 'undefined' 
		if (typeof docLength == 'undefined') {

			if (selecionados.checked) {
				selecionados.checked = false;
			} else {
				selecionados.checked = true;
				chaves = selecionados.value;
			}
			
		// Caso s� tenha mais de 1 elemento
		} else {

			// Se o 1� estiver marcado, desmarcar todos
			if (selecionados[1] != null && selecionados[1].checked) {
				for (i=0; i < selecionados.length; i++) {
					selecionados[i].checked = false;
				}
			} else {
				// Se o 1� n�o estiver marcado, marcar todos
				for (i=0; i < selecionados.length; i++) {
					selecionados[i].checked = true;
					if(chaves == "") {
						chaves = selecionados[i].value;
					} else {
						chaves = chaves + "$" + selecionados[i].value;
					}
				}
			}
		}
		form.chavesDebitosACobrar.value = chaves;
	}

-->
</script>
</head>

<body leftmargin="5" topmargin="5" onload="javascript:marcarPrestacoesSelecionadas();marcarSucumbenciasContasSelecionadas();marcarSucumbenciasGuiasSelecionadas();marcarDebitoACobrarSelecionados();">
<html:form action="/efetuarParcelamentoDebitosWizardAction"
	name="EfetuarParcelamentoDebitosActionForm"
	type="gcom.gui.cobranca.EfetuarParcelamentoDebitosActionForm"
	method="post">
	
	<html:hidden property="chavesPrestacoes"/>
	<html:hidden property="chavesSucumbenciasConta"/>
	<html:hidden property="chavesSucumbenciasGuia"/>
	<html:hidden property="chavesDebitosACobrar"/>

	<jsp:include
		page="/jsp/util/wizard/navegacao_abas_wizard.jsp?numeroPagina=2" />

	<%@ include file="/jsp/util/cabecalho.jsp"%>
<% boolean tem = false; %>	
	<logic:present name="popupEfetuarParcelamento" scope="request" >
	<logic:equal name="popupEfetuarParcelamento" scope="request" value="sim">
		<% tem = true; %>
		<input type="hidden" name="popupEfetuarParcelamento" value="sim"/> 
	</logic:equal>
	</logic:present>
	
<% if (!tem) { %>
		<%@ include file="/jsp/util/menu.jsp" %>
<% } %>

<table width="770" border="0" cellspacing="4" cellpadding="0">

<% if (!tem) { %>
			<td width="149" valign="top" class="leftcoltext">
			<input type="hidden" name="numeroPagina" value="1"/>
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
<% } %>
			<td width="625" valign="top" class="centercoltext">
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
					<td class="parabg">Efetuar Parcelamento de D�bitos</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<!-- In�cio do Corpo - Roberta Costa -->
			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td colspan="4">Para efetuar o parcelamento de d&eacute;bitos
					informe o im&oacute;vel:</td>
				</tr>
				<tr>
					<td colspan="4">
					<table border="0">
						<tr>
							<td><strong>Matr&iacute;cula do Im&oacute;vel:</strong></td>
							<td><html:text property="matriculaImovel" size="10"
								readonly="true" style="background-color:#EFEFEF; border:0" /></td>
						</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td colspan="4">
					<table border="0" bgcolor="#99CCFF" width="100%">
						<tr bgcolor="#99CCFF">
							<td colspan="2" align="center"><strong>Dados do Im�vel</strong></td>
						</tr>
						<tr>
							<td>
							<table border="0" bgcolor="#cbe5fe" width="100%">
								<tr>
									<td width="32%"><strong>Inscri&ccedil;&atilde;o do
									Im&oacute;vel:</strong></td>
									<td><html:text property="inscricaoImovel" size="25"
										readonly="true" style="background-color:#EFEFEF; border:0" />
									</td>
								</tr>
								<tr>
									<td><strong>Cliente Usu&aacute;rio:</strong></td>
									<td><html:text property="nomeCliente" size="45" readonly="true"
										style="background-color:#EFEFEF; border:0" /></td>
								</tr>
								<tr>
									<td><strong>CPF ou CNPJ:</strong></td>
									<td><html:text property="cpfCnpj" size="45" readonly="true"
										style="background-color:#EFEFEF; border:0" /></td>
										<html:hidden property="cpfClienteParcelamentoDigitado" />
								</tr>
								<tr>
									<td><strong>Situa&ccedil;&atilde;o da Liga&ccedil;&atilde;o de
									&Aacute;gua:</strong></td>
									<td><html:text property="situacaoAgua" size="45"
										readonly="true" style="background-color:#EFEFEF; border:0" />
									</td>
								</tr>
								<tr>
									<td><strong>Situa&ccedil;&atilde;o da Liga&ccedil;&atilde;o de
									Esgoto:</strong></td>
									<td><html:text property="situacaoEsgoto" size="45"
										readonly="true" style="background-color:#EFEFEF; border:0" />
									</td>
								</tr>
								<tr> 
									<td><strong>Perfil do Im�vel:</strong></td>
									<td>
										<html:text property="descricaoPerfilImovel" size="45"
											readonly="true" style="background-color:#EFEFEF; border:0" />
									</td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td colspan="4"><br>
						<table width="100%" align="center" bgcolor="#99CCFF" border="0">
							<tr>
								<td align="center">
									<strong>Endere&ccedil;o do Im&oacute;vel</strong>
								</td>
							</tr>
							<tr bgcolor="#cbe5fe">
								<td align="center" bgcolor="#FFFFFF" height="20">
									<span id="endereco">
										<logic:present name="EfetuarParcelamentoDebitosActionForm" property="endereco">
											<bean:write name="EfetuarParcelamentoDebitosActionForm" 
												property="endereco"/>
										</logic:present>
									</span>	
									<logic:notPresent name="EfetuarParcelamentoDebitosActionForm" property="endereco">
									&nbsp;
									</logic:notPresent>													
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="4" height="23"><br>
					<font color="#000000"> <strong>Valor dos D&eacute;bitos do
					Im&oacute;vel:</strong> </font></td>
				</tr>
				<tr>
					<td colspan="4">
					<table width="100%" bgcolor="#99CCFF">
						<tr bgcolor="#90c7fc">
							<td align="center" width="30%"><strong> Contas</strong></td>
							<td align="center" width="30%"><strong>Guias de Pagamento</strong>
							</td>
							<td align="center" width="40%"><strong>Acr&eacute;scimos
							Impontualidade</strong></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td align="right" height="20" bgcolor="#FFFFFF">
								<bean:write
								name="EfetuarParcelamentoDebitosActionForm"
								property="valorTotalContaValores" />
							</td>
							<td align="right" bgcolor="#FFFFFF">
								<bean:write
								name="EfetuarParcelamentoDebitosActionForm"
								property="valorGuiasPagamento" />
							</td>
							<td align="right" bgcolor="#FFFFFF">
							<logic:notEqual
								name="EfetuarParcelamentoDebitosActionForm" property="valorAcrescimosImpontualidade"
								value="0,00">
								<a href="javascript:abrirPopup('exibirValorAtualizacaoConsultarPopupAction.do?multa=<bean:write name="EfetuarParcelamentoDebitosActionForm" property="valorMulta" />&juros=<bean:write name="EfetuarParcelamentoDebitosActionForm" property="valorJurosMora" />&atualizacao=<bean:write name="EfetuarParcelamentoDebitosActionForm" property="valorAtualizacaoMonetaria" />', 300, 650);">
								<bean:write name="EfetuarParcelamentoDebitosActionForm"
									property="valorAcrescimosImpontualidade" formatKey="money.format" />
								</a>
							</logic:notEqual>
							<logic:equal name="EfetuarParcelamentoDebitosActionForm"
								property="valorAcrescimosImpontualidade" value="0,00">
								<bean:write name="EfetuarParcelamentoDebitosActionForm"
									property="valorAcrescimosImpontualidade" formatKey="money.format" />
							</logic:equal>
							</td>
						</tr>
					</table>
					<br>
					<table width="100%" bgcolor="#99CCFF">
						<tr bgcolor="#90c7fc">
							<td align="center" colspan="2"><strong>D&eacute;bitos a Cobrar</strong>
							</td>
							<td align="center" rowspan="2"><strong>Cr&eacute;ditos a Realizar</strong>
							</td>
							<td align="center" rowspan="2"><strong>D&eacute;bito Total
							Atualizado</strong></td>
						</tr>
						<tr bgcolor="#90c7fc">
							<td align="center" bgcolor="#cbe5fe" width="20%"><strong>Servi&ccedil;o</strong></td>
							<td align="center" bgcolor="#cbe5fe"><strong>Parcelamento</strong></td>
						</tr>
						<tr bgcolor="cbe5fe">
							<td align="right" height="20" bgcolor="#FFFFFF">
								<logic:notEqual name="EfetuarParcelamentoDebitosActionForm" 
									property="valorDebitoACobrarServico" value="0,00">
									<a href="javascript:abrirPopup('exibirConsultarDebitoACobrarAction.do?imovelID=
										<bean:define name="EfetuarParcelamentoDebitosActionForm" property="matriculaImovel" id="imovel" />
											<bean:write name="EfetuarParcelamentoDebitosActionForm" property="matriculaImovel" />&parcelamentoID=', 600, 800);">
										<bean:write name="EfetuarParcelamentoDebitosActionForm" property="valorDebitoACobrarServico"  formatKey="money.format"/>
									</a>
								</logic:notEqual>
								<logic:equal name="EfetuarParcelamentoDebitosActionForm" 
									property="valorDebitoACobrarServico" value="0,00">
									<bean:write	name="EfetuarParcelamentoDebitosActionForm" property="valorDebitoACobrarServico" formatKey="money.format" />
								</logic:equal>
							</td>
							<td align="right" bgcolor="#FFFFFF">
								<logic:notEqual name="EfetuarParcelamentoDebitosActionForm" 
									property="valorDebitoACobrarParcelamento" value="0,00">
									<a href="javascript:abrirPopup('exibirConsultarDebitoACobrarAction.do?imovelID=
										<bean:define name="EfetuarParcelamentoDebitosActionForm" property="matriculaImovel" id="imovel" />
											<bean:write name="EfetuarParcelamentoDebitosActionForm" property="matriculaImovel" />&parcelamentoID=', 600, 800);">
										<bean:write name="EfetuarParcelamentoDebitosActionForm" property="valorDebitoACobrarParcelamento"  formatKey="money.format"/>
									</a>
								</logic:notEqual>
								<logic:equal name="EfetuarParcelamentoDebitosActionForm" 
									property="valorDebitoACobrarParcelamento" value="0,00">
									<bean:write	name="EfetuarParcelamentoDebitosActionForm" property="valorDebitoACobrarParcelamento" formatKey="money.format" />
								</logic:equal>
							</td>
							<td align="right" bgcolor="#FFFFFF">
								<logic:notEqual name="EfetuarParcelamentoDebitosActionForm" 
									property="valorCreditoARealizar" value="0,00">
									<a href="javascript:abrirPopup('exibirConsultarCreditoARealizarAction.do?imovelID=<bean:define name="EfetuarParcelamentoDebitosActionForm" property="matriculaImovel" id="imovel" />
										<bean:write name="EfetuarParcelamentoDebitosActionForm" property="matriculaImovel" />&parcelamentoID=', 600, 800);">
											<bean:write name="EfetuarParcelamentoDebitosActionForm" property="valorCreditoARealizar" formatKey="money.format"/>
									</a>
								</logic:notEqual>
								<logic:equal name="EfetuarParcelamentoDebitosActionForm" 
									property="valorCreditoARealizar" value="0,00">
									<bean:write	name="EfetuarParcelamentoDebitosActionForm" property="valorCreditoARealizar" formatKey="money.format" />
								</logic:equal>
							</td>
							<td align="right" bgcolor="#FFFFFF"><bean:write
								name="EfetuarParcelamentoDebitosActionForm"
								property="valorDebitoTotalAtualizado" />
							</td>
						</tr>
					</table>

					<br>
					<logic:notEqual name="EfetuarParcelamentoDebitosActionForm" property="valorTotalSucumbenciaImovel" value="0,00">
					<table width="100%" bgcolor="#99CCFF">
						<tr bgcolor="#90c7fc">  
							<td align="center" width="50%">
								<strong>Sucumb�ncia Anterior</strong>
							</td>
							<td align="center" width="50%">
								<strong>Acr&eacute;scimos Sucumb�ncia Ant.</strong>
							</td>
						</tr>
						<tr bgcolor="#cbe5fe"> 
							<td align="right" height="20" bgcolor="#FFFFFF">
								<span id="valorTotalSucumbencia">
								<bean:write name="EfetuarParcelamentoDebitosActionForm" 
									property="valorTotalSucumbenciaImovel"/>
								</span>	
							</td>
							<td align="right" bgcolor="#FFFFFF">
								<span id="valorAcrescimosSucumbencia">
								<logic:notEqual
									name="EfetuarParcelamentoDebitosActionForm" property="valorAcrescimosSucumbenciaImovel"
									value="0,00">
									<a href="javascript:abrirPopup('exibirValorAtualizacaoConsultarPopupAction.do?
										multa=<bean:write name="EfetuarParcelamentoDebitosActionForm" property="valorMultaSucumbenciaImovel" />&
										juros=<bean:write name="EfetuarParcelamentoDebitosActionForm" property="valorJurosMoraSucumbenciaImovel" />&
										atualizacao=<bean:write name="EfetuarParcelamentoDebitosActionForm" property="valorAtualizacaoMonetariaSucumbenciaImovel" />', 220, 605);">
									<bean:write name="EfetuarParcelamentoDebitosActionForm"
										property="valorAcrescimosSucumbenciaImovel" formatKey="money.format" />
									</a>
								</logic:notEqual> 
								<logic:equal name="EfetuarParcelamentoDebitosActionForm"
									property="valorAcrescimosSucumbenciaImovel" value="0,00">
									<bean:write name="EfetuarParcelamentoDebitosActionForm"
										property="valorAcrescimosSucumbenciaImovel" formatKey="money.format" />
								</logic:equal>
								</span>	
							</td>
						</tr>
					</table>
					</logic:notEqual>
				
				</td>
				</tr>
				<tr>
					<td><br>
					</td>
				</tr>
				<tr>
					<td colspan="4" height="23"><strong>Contas em D&eacute;bito:</strong></td>
				</tr>
				<%int cont = 0;%>
				<tr>
					<td colspan="4">
					<table width="100%" bgcolor="#99CCFF">
						<logic:empty name="colecaoContaValores" scope="session">
							<tr bgcolor="#90c7fc">
								<td align="center" width="30"><strong>EP</strong></td>
								<td align="center" width="30"><strong>NB</strong></td>
								
								<logic:equal name="pIndicadorPossuiDividaAtiva" value="<%="" + ConstantesSistema.SIM%>" scope="session">
									<td align="center" width="30"><strong>SC</strong></td>
								</logic:equal>
								
								<td align="center" width="20"><strong>&nbsp;</strong></td>
								<td align="center" width="62"><strong>M�s/Ano</strong></td>
								<td align="center" width="90"><strong>Vencimento</strong></td>
								<td align="center" width="80"><strong>Valor Conta</strong></td>
								<td align="center" width="80"><strong>Acr�sc. Impont.</strong></td>
								
								<logic:equal name="pIndicadorPossuiDividaAtiva" value="<%="" + ConstantesSistema.SIM%>" scope="session">
									<td align="center" width="160">
										<table bgcolor="#99CCFF" width="100%">
											<tr align="center" bgcolor="#90c7fc">
												<td align="center" width="100%" colspan="2"><strong>Sucumb�ncia</strong></td>
											</tr>
											<tr bgcolor="#90c7fc">
												<td align="center" width="50%" bgcolor="#cbe5fe"><strong>Valor</strong></td>
												<td align="center" width="50%" bgcolor="#cbe5fe"><strong>Acr�sc.</strong></td>
											</tr>
										</table>
									</td>
								</logic:equal>
							</tr>
						</logic:empty>
						<logic:notEmpty name="colecaoContaValores" scope="session">
							<tr bgcolor="#90c7fc">
								<td align="center" width="30"><strong>EP</strong></td>
								<td align="center" width="30"><strong>NB</strong></td>
								
								<logic:equal name="pIndicadorPossuiDividaAtiva" value="<%="" + ConstantesSistema.SIM%>" scope="session">
									<td align="center" width="30"><strong>SC</strong></td>
								</logic:equal>
								
								<td align="center" width="20"><strong>&nbsp;</strong></td>
								<td align="center" width="62"><strong>M�s/Ano</strong></td>
								<td align="center" width="90"><strong>Vencimento</strong></td>
								<td align="center" width="80"><strong>Valor Conta</strong></td>
								<td align="center" width="80"><strong>Acr�sc. Impont.</strong></td>
								
								<logic:equal name="pIndicadorPossuiDividaAtiva" value="<%="" + ConstantesSistema.SIM%>" scope="session">
									<td align="center" width="160">
										<table bgcolor="#99CCFF" width="100%">
											<tr align="center" bgcolor="#90c7fc">
												<td align="center" width="100%" colspan="2"><strong>Sucumb�ncia</strong></td>
											</tr>
											<tr bgcolor="#cbe5fe">
												<td align="center" width="50%"><strong>Valor</strong></td>
												<td align="center" width="50%"><strong>Acr�sc.</strong></td>
											</tr>
										</table>
									</td>
								</logic:equal>								
							</tr>
							<tr>
								<td height="100" colspan="9">
								<div style="width: 100%; height: 100%; overflow: auto;">
								<table width="100%">
									<logic:iterate name="colecaoContaValores"
										type="ContaValoresHelper" id="contaValores">
										<%cont = cont + 1;
										if (cont % 2 == 0) {%>
										<tr bgcolor="#cbe5fe" width="100%">
										<%} else {%>
										<tr bgcolor="#FFFFFF" width="100%">
										<%}%>
											<td align="center" height="20" width="30">
												<c:if test="${contaValores.indicadorContasDebito eq 1}" var="igual1">
												
													<% if (!EfetuarParcelamentoDebitosWizardAction.isCobrancaBancaria(((ContaHelper)contaValores.getConta()).getIdContaMotivoRevisao()!=null?((ContaHelper)contaValores.getConta()).getIdContaMotivoRevisao().toString():null)) { %>
													<input type="radio"
														name="indicadorContasDebito<bean:write name="contaValores" property="conta.id"/>"
														value="1" checked="true" onclick="limpaRadioButton(this); configurarConta('<c:out value="${contaValores.conta.id}"/>', 'EP', this.checked);"  id="1">
													<% } else { %>
													<input type="radio"
														name="indicadorContasDebito<bean:write name="contaValores" property="conta.id"/>"
														value="1" onclick="limpaRadioButton(this);"  id="1" disabled="disabled">
													<% } %>
												
												</c:if> 
												<c:if test="${!igual1}">
												
													<% if (!EfetuarParcelamentoDebitosWizardAction.isCobrancaBancaria(((ContaHelper)contaValores.getConta()).getIdContaMotivoRevisao()!=null?((ContaHelper)contaValores.getConta()).getIdContaMotivoRevisao().toString():null)) { %>
													<input type="radio"
														name="indicadorContasDebito<bean:write name="contaValores" property="conta.id"/>"
														value="1" onclick="limpaRadioButton(this); configurarConta('<c:out value="${contaValores.conta.id}"/>', 'EP', this.checked);"  id="0">
													<% } else { %>
													<input type="radio"
														name="indicadorContasDebito<bean:write name="contaValores" property="conta.id"/>"
														value="1" onclick="limpaRadioButton(this);"  id="0" disabled="disabled">
													<% } %>
												
												</c:if>
											</td>
											<td align="center" height="20" width="30">
												<logic:present name="bloquearSelecaoDebitoExecucaoFiscal" scope="session">
													<input type="radio" name="indicadorContasDebito<bean:write name="contaValores" property="conta.id"/>"
														value="2" id="0" disabled="disabled">
												</logic:present>
												<logic:notPresent name="bloquearSelecaoDebitoExecucaoFiscal" scope="session">
													<c:if test="${contaValores.indicadorContasDebito eq 2}"	var="igual2">
														<input type="radio"
															name="indicadorContasDebito<bean:write name="contaValores" property="conta.id"/>"
															value="2" checked="true" onclick="limpaRadioButton(this); configurarConta('<c:out value="${contaValores.conta.id}"/>', 'NB', this.checked);"   id ="1">
													</c:if> 
													<c:if test="${!igual2}">
														<input type="radio"
															name="indicadorContasDebito<bean:write name="contaValores" property="conta.id"/>"
															value="2" onclick="limpaRadioButton(this); configurarConta('<c:out value="${contaValores.conta.id}"/>', 'NB', this.checked);"  id="0">
													</c:if>
												</logic:notPresent>
											</td>

											<logic:equal name="pIndicadorPossuiDividaAtiva" value="<%="" + ConstantesSistema.SIM%>" scope="session">
												<td align="center" height="20" width="30">
													<logic:present name="permitirSelecionarDebitoComposicaoCalculoSucumbencia" scope="session">
														<c:choose>
															<c:when test="${contaValores.conta.indicadorExecucaoFiscal == 1}">
																<input type="checkbox" name="sucumbenciasContasSelecionadas" value="<bean:write name="contaValores" property="conta.id"/>" onchange="javascript:montarChaveSucumbenciasConta(this);" />
		
															</c:when>
															<c:otherwise>
																<input type="checkbox" name="sucumbenciasContasSelecionadas" disabled="disabled" 
																	value="<bean:write name="contaValores" property="conta.id"/>" />
															</c:otherwise>
														</c:choose>
													</logic:present>
													<logic:notPresent name="permitirSelecionarDebitoComposicaoCalculoSucumbencia" scope="session">
														<input type="checkbox" name="sucumbenciasContasSelecionadas" disabled="disabled" 
																	value="<bean:write name="contaValores" property="conta.id"/>" />
													</logic:notPresent>
												</td>
											</logic:equal>

											<td align="center" height="20" width="20">
												<c:choose>
													<c:when test="${contaValores.conta.indicadorExecucaoFiscal == 1}">E</c:when>
													<c:when test="${contaValores.conta.indicadorDividaAtiva == 1}">A</c:when>
													<c:otherwise>N</c:otherwise>
												</c:choose>
											</td>
											
											<td width="62" align="center">
											<logic:notEmpty name="contaValores" property="conta">
									
												<logic:notEmpty name="contaValores" property="conta.dataRevisao">
										          <a href="javascript:abrirPopup('exibirConsultarContaAction.do?contaID=<bean:define name="contaValores" property="conta" id="conta" /><bean:write name="conta" property="id" />&tipoConsulta=conta', 600, 800);">
											      <% if (!EfetuarParcelamentoDebitosWizardAction.isCobrancaBancaria(((ContaHelper)contaValores.getConta()).getIdContaMotivoRevisao()!=null?((ContaHelper)contaValores.getConta()).getIdContaMotivoRevisao().toString():null)) { %>
											           <font color="#ff0000"><bean:write name="contaValores" property="conta.formatarAnoMesParaMesAno"/></font>
											      <% } else { %>
											      	   <font color="#0000FF"><bean:write name="contaValores" property="conta.formatarAnoMesParaMesAno"/></font>
											      <% } %>
										          </a>
										        </logic:notEmpty>  
												<logic:empty name="contaValores" property="conta.dataRevisao">
										          <a href="javascript:abrirPopup('exibirConsultarContaAction.do?contaID=<bean:define name="contaValores" property="conta" id="conta" /><bean:write name="conta" property="id" />&tipoConsulta=conta', 600, 800);">
											           <bean:write name="contaValores" property="conta.formatarAnoMesParaMesAno" />
											      </a>
												</logic:empty>
									         </logic:notEmpty>
									         <logic:empty name="contaValores" property="conta">
									              <bean:write name="contaValores"
											        property="conta.formatarAnoMesParaMesAno" />
								             </logic:empty> 
								              
											</td>
											<td width="90" align="center">
												<bean:write name="contaValores" property="conta.dataVencimentoConta" formatKey="date.format" />
											</td>
											<td width="80" align="right">
												<bean:write name="contaValores" property="conta.valorSemSucumbencia" formatKey="money.format" />
											</td>
											<td width="80" align="right">
												<bean:write name="contaValores" property="valorTotalContaValores" formatKey="money.format" />
											</td>
											
											<logic:equal name="pIndicadorPossuiDividaAtiva" value="<%="" + ConstantesSistema.SIM%>" scope="session">
												<td width="70" align="right">
													<bean:write name="contaValores" property="conta.valorSucumbencias" formatKey="money.format" />
												</td>
												<td width="70" align="right">
													<bean:write name="contaValores" property="valorTotalAcrescimoSucumbencia" formatKey="money.format" />
												</td>
											</logic:equal>
										</tr>
									</logic:iterate>
								</table>
								</div>
								</td>
							</tr>
						</logic:notEmpty>
					</table>
					</td>
				</tr>
				<tr>
					<td width="25%">
						<input type="button" name="" value="Limpar EP/NB" class="bottonRightCol" onClick="limparRadioButtonTodos()" />
					</td>
					<td colspan="3" height="23">
						<font color="#000000" style="font-size:10px" face="Verdana, Arial, Helvetica, sans-serif"> 
							<strong>EP-Entrada de Parcelamento; NB-Conta Paga e Ainda N&atilde;o Baixada; 
								<logic:equal name="pIndicadorPossuiDividaAtiva" value="<%="" + ConstantesSistema.SIM%>" scope="session">
									<br>SC-Integra a base de c�lculo do valor de Sucumb�ncia
								</logic:equal>
							</strong>
						</font><br>
						<font color="#ff0000" style="font-size:10px" face="Verdana, Arial, Helvetica, sans-serif"> 
							Contas em Revis�o
						</font><br>
						<font color="#0000ff" style="font-size:10px" face="Verdana, Arial, Helvetica, sans-serif"> 
							Contas em Cobran�a Banc�ria
						</font><br>
						<font color="#000000" style="font-size:10px" face="Verdana, Arial, Helvetica, sans-serif"> 
							<logic:equal name="pIndicadorPossuiDividaAtiva" value="<%="" + ConstantesSistema.SIM%>" scope="session">
								N-Normal; A-Inscrita em D�vida Ativa; E-Execu��o Fiscal 
							</logic:equal>
							
							<logic:equal name="pIndicadorPossuiDividaAtiva" value="<%="" + ConstantesSistema.NAO%>" scope="session">
								N-Normal
							</logic:equal>							
						</font><br>
					</td>
				</tr>
				<tr>
					<td colspan="4" align="right"><input type="button" name=""
						value="Calcular" class="bottonRightCol" onClick="enviar();" /></td>
				</tr>
				<tr>
					<td colspan="4" height="23"><strong>Guias de Pagamento em
					D&eacute;bito:</strong></td>
				</tr>
				<% cont = 0;%>
				<tr>
					<td colspan="4">
					<table width="100%" bgcolor="#99CCFF">
						<logic:empty name="colecaoGuiaPagamentoValores" scope="session">
							<tr bgcolor="#90c7fc">
								<td align="center" width="30"><strong>Todos</strong></td>
								
								<logic:equal name="pIndicadorPossuiDividaAtiva" value="<%="" + ConstantesSistema.SIM%>" scope="session">
									<td align="center" width="30"><strong>SC</strong></td>
								</logic:equal>
								
								<td align="center" width="20"><strong>&nbsp;</strong></td>
								<td align="center" width="80"><strong>N�mero da Guia</strong></td>
								<td align="center" width="30"><strong>Prest.</strong></td>
								<td align="center" width="80"><strong>Data de Emiss�o</strong></td>
								<td align="center" width="80"><strong>Data de Vencimento</strong></td>
								<td align="center" width="70"><strong>Valor</strong></td>
								<td align="center" width="70"><strong>Acr�sc. por Impont.</strong></td>
								
								<logic:equal name="pIndicadorPossuiDividaAtiva" value="<%="" + ConstantesSistema.SIM%>" scope="session">
									<td align="center" width="120">
										<table bgcolor="#99CCFF" width="100%">
											<tr align="center" bgcolor="#90c7fc">
												<td align="center" width="100%" colspan="2"><strong>Sucumb�ncia</strong></td>
											</tr>
											<tr bgcolor="#cbe5fe">
												<td align="center" width="50%"><strong>Valor</strong></td>
												<td align="center" width="50%"><strong>Acr�sc.</strong></td>
											</tr>
										</table>
									</td>
								</logic:equal>
							</tr>
						</logic:empty>
						<logic:notEmpty name="colecaoGuiaPagamentoValores" scope="session">

							<tr bgcolor="#90c7fc">
								<logic:present name="bloquearSelecaoDebitoExecucaoFiscal" scope="session">
									<td align="center" width="30"><strong>Todos</strong></td>
								</logic:present>
								<logic:notPresent name="bloquearSelecaoDebitoExecucaoFiscal" scope="session">
									<td align="center" width="30"><a href="javascript:selecionarPrestacoes();"><strong>Todos</strong></a></td>
								</logic:notPresent>
								
								<logic:equal name="pIndicadorPossuiDividaAtiva" value="<%="" + ConstantesSistema.SIM%>" scope="session">
									<td align="center" width="30"><strong>SC</strong></td>
								</logic:equal>
								
								<td align="center" width="20"><strong>&nbsp;</strong></td>
								<td align="center" width="80"><strong>N�mero da Guia</strong></td>
								<td align="center" width="30"><strong>Prest.</strong></td>
								<td align="center" width="80"><strong>Data de Emiss�o</strong></td>
								<td align="center" width="80"><strong>Data de Vencimento</strong></td>
								<td align="center" width="70"><strong>Valor</strong></td>
								<td align="center" width="70"><strong>Acr�sc. por Impont.</strong></td>
								
								<logic:equal name="pIndicadorPossuiDividaAtiva" value="<%="" + ConstantesSistema.SIM%>" scope="session">
									<td align="center" width="120">
										<table bgcolor="#99CCFF" width="100%">
											<tr align="center" bgcolor="#90c7fc">
												<td align="center" width="100%" colspan="2"><strong>Sucumb�ncia</strong></td>
											</tr>
											<tr bgcolor="#cbe5fe">
												<td align="center" width="50%"><strong>Valor</strong></td>
												<td align="center" width="50%"><strong>Acr�sc.</strong></td>
											</tr>
										</table>
									</td>
								</logic:equal>
							</tr>
							<tr>
								<td height="100" colspan="10">
								<div style="width: 100%; height: 100%; overflow: auto;">
								<table width="100%">
									<logic:iterate name="colecaoGuiaPagamentoValores"
										type="GuiaPagamentoValoresHelper" id="guiaPagamentoValores">
											<%cont = cont + 1;
											if (cont % 2 == 0) {%>
											<tr bgcolor="#cbe5fe">
											<%} else {%>
											<tr bgcolor="#FFFFFF">
											<%}%>

											<td width="30">
												<logic:present name="bloquearSelecaoDebitoExecucaoFiscal" scope="session">
													<input type="checkbox" name="prestacoesSelecionadas" disabled="disabled"
														value="<bean:write name="guiaPagamentoValores" property="idGuiaPagamento"/>-<bean:write name="guiaPagamentoValores" property="numeroPrestacao"/>" />
												</logic:present>
												<logic:notPresent name="bloquearSelecaoDebitoExecucaoFiscal" scope="session">
													<input type="checkbox" name="prestacoesSelecionadas" onchange="javascript:montarChavePrestacoes(this);"
														value="<bean:write name="guiaPagamentoValores" property="idGuiaPagamento"/>-<bean:write name="guiaPagamentoValores" property="numeroPrestacao"/>" />
												</logic:notPresent>
												<input type="hidden" name="prestacoesSelecionadasParcelamento"
															value="<bean:write name="guiaPagamentoValores" property="idParcelamento"/>" />												
					                		</td>
					                		
					                		<logic:equal name="pIndicadorPossuiDividaAtiva" value="<%="" + ConstantesSistema.SIM%>" scope="session">
						                		<td align="center" height="20" width="30">
													<logic:present name="permitirSelecionarDebitoComposicaoCalculoSucumbencia" scope="session">
														<c:choose>
															<c:when test="${guiaPagamentoValores.indicadorExecucaoFiscal == 1}">
																<input type="checkbox" name="sucumbenciasGuiasSelecionadas" 
																value="<bean:write name="guiaPagamentoValores" property="idGuiaPagamento"/>-<bean:write name="guiaPagamentoValores" property="numeroPrestacao"/>"
																onchange="javascript:montarChaveSucumbenciasGuia(this);" />
		
															</c:when>
															<c:otherwise>
																<input type="checkbox" name="sucumbenciasGuiasSelecionadas" disabled="disabled" 
																	value="<bean:write name="guiaPagamentoValores" property="idGuiaPagamento"/>-<bean:write name="guiaPagamentoValores" property="numeroPrestacao"/>" />
															</c:otherwise>
														</c:choose>
													</logic:present>
													<logic:notPresent name="permitirSelecionarDebitoComposicaoCalculoSucumbencia" scope="session">
														<input type="checkbox" name="sucumbenciasGuiasSelecionadas" disabled="disabled" 
																	value="<bean:write name="guiaPagamentoValores" property="idGuiaPagamento"/>-<bean:write name="guiaPagamentoValores" property="numeroPrestacao"/>" />
													</logic:notPresent>
												</td>
											</logic:equal>
											
											
											<td align="center" height="20" width="20">
												<c:choose>
													<c:when test="${guiaPagamentoValores.indicadorExecucaoFiscal == 1}">E</c:when>
													<c:when test="${guiaPagamentoValores.indicadorDividaAtiva == 1}">A</c:when>
													<c:otherwise>N</c:otherwise>
												</c:choose>
											</td>

											<td width="80" align="center" height="20">
												<a href="javascript:abrirPopup('exibirConsultarGuiaPagamentoAction.do?guiaPagamentoId=<bean:write name="guiaPagamentoValores" property="idGuiaPagamento" />', 600, 800);">
													<span style="color: #000000;"><bean:write name="guiaPagamentoValores" property="idGuiaPagamento" /></span>
												</a>
											</td>

											<td width="30" align="center"><bean:write name="guiaPagamentoValores" property="numeroPrestacao" /></td>
											<td width="80" align="center"><bean:write name="guiaPagamentoValores" property="dataEmissao" formatKey="date.format" /></td>
											<td width="80" align="center"><bean:write name="guiaPagamentoValores" property="dataVencimento" formatKey="date.format" /></td>
											<td width="70" align="right"><bean:write name="guiaPagamentoValores" property="valorTotalPrestacaoSemSucumbencia" formatKey="money.format" /></td>
											<td width="70" align="right"><bean:write name="guiaPagamentoValores" property="valorAcrescimosImpontualidadeSemSucumbencia" formatKey="money.format" /></td>
											
											<logic:equal name="pIndicadorPossuiDividaAtiva" value="<%="" + ConstantesSistema.SIM%>" scope="session">
												<td width="60" align="right"><bean:write name="guiaPagamentoValores" property="valorTotalPrestacaoSucumbencia" formatKey="money.format" /></td>
												<td width="60" align="right"><bean:write name="guiaPagamentoValores" property="valorTotalAcrescimoSucumbencia" formatKey="money.format" /></td>
											</logic:equal>
										</tr>
									</logic:iterate>
								</table>
								</div>
								</td>
							</tr>

						</logic:notEmpty>
					</table>
					</td>
				</tr>

				<logic:equal name="pIndicadorSelecionarDebitoACobrarParcelamento" value="<%="" + ConstantesSistema.SIM%>" scope="session">
				<tr>
					<td><br></td>
				</tr>
				<tr>
					<td colspan="4" height="23"><strong>D�bitos A Cobrar:</strong></td>
				</tr>
				<% cont = 0;%>
				<tr>
					<td colspan="4">
					<table width="100%" bgcolor="#99CCFF">
						<logic:empty name="colecaoDebitoACobrarValores" scope="session">
							<tr bgcolor="#90c7fc">
								<td align="center" width="10%"><strong>Todos</strong></td>
								<td align="center" width="15%"><strong>Tipo do D�bito</strong></td>
								<td align="center" width="10%"><strong>M�s/Ano Refer�ncia</strong></td>
								<td align="center" width="15%"><strong>M�s/Ano Cobran�a </strong></td>
								<td align="center" width="15%"><strong>Parcelas a cobrar</strong></td>
								<td align="center" width="20%"><strong>Valor a cobrar</strong></td>
								<td align="center" width="15%"><strong>Dias Suspens�o Lan�amento</strong></td>
							</tr>
						</logic:empty>
						<logic:notEmpty name="colecaoDebitoACobrarValores" scope="session">
							<%if (((Collection) session.getAttribute("colecaoDebitoACobrarValores")).size() 
									<= ConstantesSistema.NUMERO_MAXIMO_REGISTROS_DEBITO_A_COBRAR) {%>
							<tr bgcolor="#90c7fc">
								<logic:present name="bloquearSelecaoDebitoExecucaoFiscal" scope="session">
									<td align="center" width="10%"><strong>Todos</strong></td>
								</logic:present>
								<logic:notPresent name="bloquearSelecaoDebitoExecucaoFiscal" scope="session">
									<td align="center" width="10%"><a href="javascript:selecionarDebitos();"><strong>Todos</strong></a></td>
								</logic:notPresent>
								<td align="center" width="15%"><strong>Tipo do D�bito</strong></td>
								<td align="center" width="10%"><strong>M�s/Ano Refer�ncia</strong></td>
								<td align="center" width="15%"><strong>M�s/Ano Cobran�a </strong></td>
								<td align="center" width="15%"><strong>Parcelas a cobrar</strong></td>
								<td align="center" width="20%"><strong>Valor a cobrar</strong></td>
								<td align="center" width="15%"><strong>Dias Suspens�o Lan�amento</strong></td>
							</tr>
							<logic:iterate name="colecaoDebitoACobrarValores"
								type="DebitoACobrarValoresHelper" id="debitoACobrarValores">
								<%cont = cont + 1;
								if (cont % 2 == 0) {%>
								<tr bgcolor="#cbe5fe">
								<%} else {%>
								<tr bgcolor="#FFFFFF">
								<%}%>
					                <td width="10%">
										<logic:present name="bloquearSelecaoDebitoExecucaoFiscal" scope="session">
											<input type="checkbox" name="debitoSelecionado"	disabled="disabled" value="<bean:write name="debitoACobrarValores" property="idRegistro"/>" onchange="javascript:montarChaveDebitosACobrar(this);" />
										</logic:present>
										<logic:notPresent name="bloquearSelecaoDebitoExecucaoFiscal" scope="session">
											<input type="checkbox" name="debitoSelecionado"	value="<bean:write name="debitoACobrarValores" property="idRegistro"/>" onchange="javascript:montarChaveDebitosACobrar(this);" />
										</logic:notPresent>
			                		</td>
									<td width="15%" align="center" height="20"><bean:write name="debitoACobrarValores" property="descricaoDebitoTipo" /></td>
									<td width="10%" align="center"><bean:write name="debitoACobrarValores" property="mesAnoReferenciaDebito" /></td>
									<td width="15%" align="center"><bean:write name="debitoACobrarValores" property="mesAnoCobrancaDebito" /></td>
									<td width="15%" align="center"><bean:write name="debitoACobrarValores" property="numeroParcelasACobrar" /></td>
									<td width="20%" align="right"><bean:write name="debitoACobrarValores" property="valorRestanteASerCobrado" formatKey="money.format" /></td>
									<td width="15%" align="right"><bean:write name="debitoACobrarValores" property="numeroDiasSuspensao" /></td>
								</tr>
							</logic:iterate>
							<%} else {%>
							<tr bgcolor="#90c7fc">
								<logic:present name="bloquearSelecaoDebitoExecucaoFiscal" scope="session">
									<td align="center" width="10%"><strong>Todos</strong></td>
								</logic:present>
								<logic:notPresent name="bloquearSelecaoDebitoExecucaoFiscal" scope="session">
									<td align="center" width="10%"><a href="javascript:selecionarDebitos();"><strong>Todos</strong></a></td>
								</logic:notPresent>
								<td align="center" width="15%"><strong>Tipo do D�bito</strong></td>
								<td align="center" width="10%"><strong>M�s/Ano Refer�ncia</strong></td>
								<td align="center" width="15%"><strong>M�s/Ano Cobran�a </strong></td>
								<td align="center" width="15%"><strong>Parcelas a cobrar</strong></td>
								<td align="center" width="20%"><strong>Valor a cobrar</strong></td>
								<td align="center" width="15%"><strong>Dias Suspens�o Lan�amento</strong>
								</td>
							</tr>
							<tr>
								<td height="100" colspan="7">
								<div style="width: 100%; height: 100%; overflow: auto;">
								<table width="100%">
									<logic:iterate name="colecaoDebitoACobrarValores"
										type="DebitoACobrarValoresHelper" id="debitoACobrarValores">
											<%cont = cont + 1;
											if (cont % 2 == 0) {%>
											<tr bgcolor="#cbe5fe">
											<%} else {%>
											<tr bgcolor="#FFFFFF">
											<%}%>
					                		<td width="10%">
												<logic:present name="bloquearSelecaoDebitoExecucaoFiscal" scope="session">
													<input type="checkbox" name="debitoSelecionado"	disabled="disabled" value="<bean:write name="debitoACobrarValores" property="idRegistro"/>" onchange="javascript:montarChaveDebitosACobrar(this);" />
												</logic:present>
												<logic:notPresent name="bloquearSelecaoDebitoExecucaoFiscal" scope="session">
													<input type="checkbox" name="debitoSelecionado"	value="<bean:write name="debitoACobrarValores" property="idRegistro"/>" onchange="javascript:montarChaveDebitosACobrar(this);" />
												</logic:notPresent>
					                		</td>
                                            <td width="15%" align="center" height="20"><bean:write name="debitoACobrarValores" property="descricaoDebitoTipo" /></td>
											<td width="10%" align="center"><bean:write name="debitoACobrarValores" property="mesAnoReferenciaDebito" /></td>
											<td width="15%" align="center"><bean:write name="debitoACobrarValores" property="mesAnoCobrancaDebito" /></td>
											<td width="15%" align="center"><bean:write name="debitoACobrarValores" property="numeroParcelasACobrar" /></td>
											<td width="20%" align="right"><bean:write name="debitoACobrarValores" property="valorRestanteASerCobrado" formatKey="money.format" /></td>
											<td width="15%" align="right"><bean:write name="debitoACobrarValores" property="numeroDiasSuspensao" /></td>
										</tr>
									</logic:iterate>
								</table>
								</div>
								</td>
							</tr>
							<%}%>
						</logic:notEmpty>
					</table>
					</td>
				</tr>
				</logic:equal>

				<tr>
					<td colspan="4"><jsp:include
						page="/jsp/util/wizard/navegacao_botoes_wizard_valida_avancar_valida_voltar.jsp?numeroPagina=2" />
					</td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<!-- Fim do Corpo - Roberta Costa --></td>
		</tr>
	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>

</html:form>
<script>
document.getElementById('botaoConcluir').style.visibility='hidden';
</script>

</body>
<!-- parcelamento_debito_efetuar_processo2.jsp -->
</html:html>
