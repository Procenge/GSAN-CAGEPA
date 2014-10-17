<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gcom.util.ConstantesSistema"%>

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false" formName="ExibirFiltrarGuiaPagtGeracaoDebAutomActionForm" />
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">

function filtrar(){
	var form = document.forms[0];

	if (validateExibirFiltrarGuiaPagtGeracaoDebAutomActionForm(form)){
		if(validarPreenchimentoCampos()){
			form.action =  '/gsan/filtrarGuiaPagtGeracaoDebAutomAction.do'
			form.submit();
		}
	}
}

function validarPreenchimentoCampos(){
    var form = document.forms[0];

    if(form.dataVencimentoGuiaPagamentoInicial.value == "" && form.dataVencimentoGuiaPagamentoFinal.value == ""
        && form.clienteResponsavel.value == ""){
			alert('Informe pelo menos uma opção de seleção');
			return;
	}

    
	if(form.dataVencimentoGuiaPagamentoInicial.value == "" && form.dataVencimentoGuiaPagamentoFinal.value == ""){
		if(form.clienteResponsavel.value == ""){
			alert('Cliente Responsável é obrigatório, caso o Período de Vencimento não seja informado!');
			return;
		}
	}

    if(form.clienteResponsavel.value == ""){
		if (form.dataVencimentoGuiaPagamentoInicial.value == "" && form.dataVencimentoGuiaPagamentoFinal.value == "") {
			alert('Período de Vencimento é obrigatório, caso o Cliente Responsável não seja informado!');
			return;
		} 
	}

    return true;
}

function validarDatasVencimento(){
	var form = document.forms[0]; 

	if(form.dataVencimentoGuiaPagamentoInicial.value != ""){
		if(verificaDataMensagem(form.dataVencimentoGuiaPagamentoInicial, 'Informe o Período de Vencimento Inicial no formato correto dd/mm/aaaa')){
			if(form.dataVencimentoGuiaPagamentoFinal.value != ""){
				verificaDataMensagem(form.dataVencimentoGuiaPagamentoFinal, 'Informe o Período de Vencimento Final no formato correto dd/mm/aaaa');
		    }
		}
    }else{
		if(form.dataVencimentoGuiaPagamentoInicial.value != "" && form.dataVencimentoGuiaPagamentoFinal.value != ""){
			verificaDataMensagem(form.dataVencimentoGuiaPagamentoFinal, 'Informe o Período de Vencimento Final no formato correto dd/mm/aaaa');
	    }
    }
}  
 
function duplicaPeriodoVencimento(){
	var form = document.forms[0]; 

	form.dataVencimentoGuiaPagamentoFinal.value = form.dataVencimentoGuiaPagamentoInicial.value;
}  

function limpar(){
	var form = document.forms[0]; 
	
	form.dataVencimentoGuiaPagamentoInicial.value = "";
	form.dataVencimentoGuiaPagamentoFinal.value = "";
	form.clienteResponsavel.value = "";
	form.nomeClienteResponsavel.value = "";
	form.indicadorTipoGuia.value = "2";
	form.indicadorTipoGuia[0].checked = true;

	form.action =  '/gsan/exibirFiltrarGuiaPagtGeracaoDebAutomAction.do?limpar=S'
	form.submit();
}

//Recebe os dados do(s) popup(s)
function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	var form = document.forms[0]; 
	
	if (tipoConsulta == 'cliente') {
		form.clienteResponsavel.value = codigoRegistro;
		form.nomeClienteResponsavel.value = descricaoRegistro;
		form.nomeClienteResponsavel.style.color = "#000000";
	    setarFoco('clienteResponsavel');
  	}
}

function limparClienteResponsavel() {
	var form = document.forms[0]; 
	
	form.clienteResponsavel.value = "";
	form.nomeClienteResponsavel.value = "";
}

function limparPeriodoVencimento() {
	var form = document.forms[0]; 

	if(form.dataVencimentoGuiaPagamentoInicial.value == ""){
		form.dataVencimentoGuiaPagamentoFinal.value = "";
	}
}

function validarEnterClienteResponsavel(event){
	var form = document.forms[0]; 
		
	validaEnterComMensagem(event, 'exibirFiltrarGuiaPagtGeracaoDebAutomAction.do?objetoConsulta=2','clienteResponsavel','Cliente Responsável');
}


function setarAtualizar(){
	var form = document.forms[0];
	
	if (form.indicadorAtualizar.value == "1"){
		form.indicadorAtualizar.value = "2"; 
	}else{
		form.indicadorAtualizar.value = "1";
	}
}

</script>
</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:document.forms[0].indicadorAtualizar.checked=true;">

<form action="/exibirFiltrarGuiaPagtGeracaoDebAutomAction"
	name="ExibirFiltrarGuiaPagtGeracaoDebAutomActionForm" method="post"><%@ include
	file="/jsp/util/cabecalho.jsp"%> <%@ include
	file="/jsp/util/menu.jsp"%> <html:hidden
	property="indicadorAtualizar" value="1" />

<table width="770" border="0" cellspacing="4" cellpadding="0">
	<tr>
		<td width="149" valign="top" class="leftcoltext">

		<div align="center"><%@ include
			file="/jsp/util/informacoes_usuario.jsp"%> <%@ include
			file="/jsp/util/mensagens.jsp"%></div>
		</td>
		<td valign="top" class="centercoltext">

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
				<td class="parabg">Filtrar Guia(s) de Pagamento para geração de Débito Automático</td>
				<td width="11" valign="top"><img border="0"
					src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
			</tr>
		</table>

		<p>&nbsp;</p>

		<table bordercolor="#000000" width="100%" cellspacing="0">
			<tr>
				<td>Para filtrar a(s) guia(s) de Pagamento para geração de débito automático,<br> 
					 informe os dados abaixo:
				</td>
				<td width="84"><input name="atualizar" type="checkbox"
					checked="checked" onclick="javascript:setarAtualizar();" value="1">
				<strong>Atualizar</strong></td>
			</tr>
		</table>

		<p>&nbsp;</p>

		<table width="100%" border="0" dwcopytype="CopyTableRow">
			<tr>
				<td height="17"><strong>Per&iacute;odo de Vencimento:<font color="#FF0000">*</font></strong></td>
				<td colspan="4"><strong>
					<html:text maxlength="10" name="ExibirFiltrarGuiaPagtGeracaoDebAutomActionForm"
					property="dataVencimentoGuiaPagamentoInicial" size="10"
					onkeyup="javascript:mascaraData(this, event);duplicaPeriodoVencimento();limparPeriodoVencimento();" onblur="validarDatasVencimento();"/>
				<a
					href="javascript:abrirCalendarioReplicando('ExibirFiltrarGuiaPagtGeracaoDebAutomActionForm', 'dataVencimentoGuiaPagamentoInicial',
					'dataVencimentoGuiaPagamentoFinal')">
				<img border="0"
					src="<bean:message key='caminho.imagens'/>calendario.gif"
					width="20" border="0" align="middle" alt="Exibir Calendário" /></a>
					<strong>&nbsp;a&nbsp;</strong> 
					<html:text maxlength="10" name="ExibirFiltrarGuiaPagtGeracaoDebAutomActionForm"
					property="dataVencimentoGuiaPagamentoFinal" size="10"
					onkeyup="javascript:mascaraData(this, event);" onblur="validarDatasVencimento();"/>
				<a
					href="javascript:abrirCalendario('ExibirFiltrarGuiaPagtGeracaoDebAutomActionForm', 'dataVencimentoGuiaPagamentoFinal')">
				<img border="0"
					src="<bean:message key='caminho.imagens'/>calendario.gif"
					width="20" border="0" align="middle" alt="Exibir Calendário" /></a> </strong></td>
			</tr>
			<tr>
				<td colspan="5">
				<hr>
				</td>
			</tr>
			<tr>
				<td width="30%"><strong>Cliente Responsável:<font color="#FF0000">*</font></strong></td>
				<td>
					<html:text property="clienteResponsavel" name="ExibirFiltrarGuiaPagtGeracaoDebAutomActionForm" size="9" maxlength="9"
						onkeyup="validaEnter(event, 'exibirFiltrarGuiaPagtGeracaoDebAutomAction.do', 'clienteResponsavel');"
						onkeypress="javascript:validarEnterClienteResponsavel(event);" /> 
					<a href="javascript:abrirPopup('exibirPesquisarClienteAction.do', 'cliente', null, null, 275, 480, '',document.forms[0].clienteResponsavel);">
						<img width="23" height="21"	src="<bean:message key="caminho.imagens"/>pesquisa.gif"
							style="cursor: hand;" alt="Pesquisar" border="0" />
					</a>
					<logic:present name="clienteInexistente" scope="request">
						<html:text property="nomeClienteResponsavel" name="ExibirFiltrarGuiaPagtGeracaoDebAutomActionForm" 
							size="30" maxlength="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" />
					</logic:present>
				 	<logic:notPresent name="clienteInexistente" scope="request">
						<html:text property="nomeClienteResponsavel" name="ExibirFiltrarGuiaPagtGeracaoDebAutomActionForm" 
							size="30" maxlength="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
					</logic:notPresent>
					<a href="javascript:limparClienteResponsavel();"> 
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"	border="0" title="Apagar" />
					</a>
				</td>
			</tr>
			<tr>
				<td colspan="5">
				<hr>
				</td>
			</tr>
			<tr>
				<td width="30%"><strong>Considerar Guias:</strong></td>
				<td align="left">
					<table width="100%" border="0">
						<tr>
							<logic:present name="ExibirFiltrarGuiaPagtGeracaoDebAutomActionForm" property="indicadorTipoGuia">
								<logic:equal name="ExibirFiltrarGuiaPagtGeracaoDebAutomActionForm" property="indicadorTipoGuia" value="1">
									<td nowrap><input type="radio" name="indicadorTipoGuia" value="1" checked="checked">&nbsp;Vencidas</td>
									<td nowrap><input type="radio" name="indicadorTipoGuia" value="2">&nbsp;N&atilde;o Vencidas</td>
									<td nowrap><input type="radio" name="indicadorTipoGuia" value="3">&nbsp;Vencidas e N&atilde;o Vencidas</td>
								</logic:equal>
								<logic:equal name="ExibirFiltrarGuiaPagtGeracaoDebAutomActionForm" property="indicadorTipoGuia" value="2">
									<td nowrap><input type="radio" name="indicadorTipoGuia" value="1">&nbsp;Vencidas</td>
									<td nowrap><input type="radio" name="indicadorTipoGuia" value="2" checked="checked">&nbsp;N&atilde;o Vencidas</td>
									<td nowrap><input type="radio" name="indicadorTipoGuia" value="3">&nbsp;Vencidas e N&atilde;o Vencidas</td>
								</logic:equal>
								<logic:equal name="ExibirFiltrarGuiaPagtGeracaoDebAutomActionForm" property="indicadorTipoGuia" value="3">
									<td nowrap><input type="radio" name="indicadorTipoGuia" value="1">&nbsp;Vencidas</td>
									<td nowrap><input type="radio" name="indicadorTipoGuia" value="2">&nbsp;N&atilde;o Vencidas</td>
									<td nowrap><input type="radio" name="indicadorTipoGuia" value="3" checked="checked">&nbsp;Vencidas e N&atilde;o Vencidas</td>
								</logic:equal>
							</logic:present>
							<logic:notPresent name="ExibirFiltrarGuiaPagtGeracaoDebAutomActionForm" property="indicadorTipoGuia">
								<td nowrap><input type="radio" name="indicadorTipoGuia" value="1">&nbsp;Vencidas</td>
								<td nowrap><input type="radio" name="indicadorTipoGuia" value="2" checked="checked">&nbsp;N&atilde;o Vencidas</td>
								<td nowrap><input type="radio" name="indicadorTipoGuia" value="3">&nbsp;Vencidas e N&atilde;o Vencidas</td>
							</logic:notPresent>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="5">
				<hr>
				</td>
			</tr>
			<tr>
				<td colspan="5">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="3" align="left">
				<table border="0" align="left">
					<tr>
						<td align="left"><input name="Button32222" type="button"
							class="bottonRightCol" value="Limpar"
							onClick="javascript:limpar()" /> <input name="Button"
							type="button" class="bottonRightCol" value="Cancelar"
							align="left"
							onclick="window.location.href='/gsan/telaPrincipal.do'">

						</td>
					</tr>
				</table>
				</td>
				<td>
				<table border="0" align="right">
					<tr>
						<td align="right"><input name="Button" type="button"
							class="bottonRightCol" name="botaoFiltrar" value="Filtrar"
							onclick="javascript:filtrar();" /></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>

		<p>&nbsp;</p>
		</td>
	</tr>
</table>
</form>
<%@ include file="/jsp/util/rodape.jsp"%>
</body>
</html:html>
