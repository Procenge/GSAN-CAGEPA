<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ page import="gcom.util.ConstantesSistema" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<%@page import="gcom.cadastro.sistemaparametro.SistemaParametro"%>
<%@page import="gcom.cadastro.cliente.ClienteTipo"%><html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="ClienteActionForm" dynamicJavascript="false" />

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script>
<!--
var bCancel = false;

function validateClienteActionForm(form) {
	var form = document.forms[0];
	if (bCancel) {
		return true;
	} else {
		if (form.banco.value != -1 && form.agencia.value == '-1') {
			alert('Informe a agência.');
			return false;
		}
		
		if (form.agencia.value != -1 && form.contaBancaria.value == '') {
			alert('Informe o numero da conta.');
			return false;
		}

		if (form.contaBancaria.value != '' && form.agencia.value == -1) {
			alert('Informe a agência.');
			return false;
		}
		 	
		return true;
		
	}
}

// Função para redirecionar para a atualização quando é feita a pesquisa pela lupa do nome do cliente
function redirecionarSubmitAtualizar(id) {
 	urlRedirect = "/gsan/exibirAtualizarClienteAction.do?idRegistroAtualizacao=" + id;
 	redirecionarSubmit(urlRedirect);
}

function checkDadosAdcionais(checkDadosAdcionais, 
		indMulta,
		indJuros,
		indCorrecao,
		indImpostoFederal,
		idClienteResponsavel) {
	var form = document.forms[0]; 
	form.indDadosAdicionais.checked = checkDadosAdcionais == '1';
	form.indMulta.checked = indMulta == '1';
	form.indJuros.checked = indJuros == '1';
	form.indCorrecao.checked = indCorrecao == '1';
	form.indImpostoFederal.checked = indImpostoFederal == '1';
	form.idRegistroAtualizacao.value = idClienteResponsavel;
}

function setarIDRegistroAtualizado(idRegistroAtualizado){
	var form = document.forms[0];
	form.idRegistroAtualizacao.value = idRegistroAtualizado;
}

function checkPadrao() {
	var form = document.forms[0];
	if (form.indDadosAdicionais[0].checked == true) {
		form.indMulta[0].checked = true;
		form.indJuros[0].checked = true;
		form.indCorrecao[0].checked = true;
	}
}
-->
</script>
</head>
<%//String checkDadosAdcionais = (String)session.getAttribute("checkDadosAdcionais"); %>
<%//String indMulta = ((Short)session.getAttribute("indMulta")).toString(); %>
<%//String indJuros = ((Short)session.getAttribute("indJuros")).toString(); %>
<%//String indCorrecao = ((Short)session.getAttribute("indCorrecao")).toString(); %>
<%//String indImpostoFederal = ((Short)session.getAttribute("indImpostoFederal")).toString(); %>
<%String idRegistroAtualizacao = (String)session.getAttribute("idRegistroAtualizacao");%>
<!-- <body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');checkDadosAdcionais('<%//=checkDadosAdcionais%>', '<%//=indMulta%>', '<%//=indJuros%>', '<%//=indCorrecao%>', '<%//=indImpostoFederal%>', '<%//=idRegistroAtualizacao%>');"> -->
<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');setarIDRegistroAtualizado('<%=idRegistroAtualizacao%>');">
<div id="formDiv">
<html:form
    action="/atualizarClienteWizardAction"
    method="post"
    onsubmit="return validateClienteActionForm(this);"
>

<jsp:include page="/jsp/util/wizard/navegacao_abas_wizard_valida_avancar.jsp?numeroPagina=5"/>

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

<input type="hidden" name="numeroPagina" value="1"/>
<table width="770" border="0" cellspacing="5" cellpadding="0">
	<tr>
		<td width="150" valign="top" class="leftcoltext">
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
		<td width="655" valign="top" class="centercoltext">
			<table height="100%">
				<tr>
			       <td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
					<td class="parabg">Atualizar Cliente<input type="hidden" name="idRegistroAtualizacao" value="" /></td>
					<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0" dwcopytype="CopyTableRow">
				<tr>
					<td>
						Para inserir dados adicionais do responsável, marque:<!-- <input type="checkbox" id="indDadosAdicionais" name="indDadosAdicionais" tabindex="1" value="1"/> -->
						<html:checkbox property="indDadosAdicionais" tabindex="1" value="true" onclick="javascript:checkPadrao();"></html:checkbox>
					<td align="right"></td>
    			</tr>
    			<input type="hidden" name="indDadosAdicionais" value="false">
    		</table>
    
    		<table width="100%" border="0">
				<tr>
					<td>
						<strong>Cobrança de Multa:</strong>
					</td>
                    <td>
						<!-- <input type="checkbox" id="indMulta" name="indMulta" tabindex="1" value="1" checked="checked"/> -->
						<html:checkbox property="indMulta" tabindex="1" value="true"></html:checkbox>
                    </td>
                </tr>
                <input type="hidden" name="indMulta" value="false">
                
                <tr>
					<td>
						<strong>Cobrança de Juros:</strong>
					</td>
                    <td>
						<!-- <input type="checkbox" id="indJuros" name="indJuros" tabindex="2" value="1" checked="checked"/> -->
						<html:checkbox property="indJuros" tabindex="2" value="true"></html:checkbox>
                    </td>
                </tr>
                <input type="hidden" name="indJuros" value="false">
                
                <tr>
					<td>
						<strong>Cobrança de Correção:</strong>
					</td>
                    <td>
						<!-- <input type="checkbox" id="indCorrecao" name="indCorrecao" tabindex="3" value="1" checked="checked"/> -->
						<html:checkbox property="indCorrecao" tabindex="3" value="true"></html:checkbox>
                    </td>
                </tr>
				<input type="hidden" name="indCorrecao" value="false">
								
				<tr>
					<td>
						<strong>Imposto Federal:</strong>
					</td>
                    <td>
						<% if (session.getAttribute("esferaPoder") != null && session.getAttribute("esferaPoder").equals(ClienteTipo.INDICADOR_ESFERA_PODER_FEDERAL.intValue())) { %>
							<input type="checkbox" id="indImpostoFederal" name="indImpostoFederal" tabindex="4" value="1" checked="checked"/>
						<% } else { %>
							<input type="checkbox" id="indImpostoFederal" name="indImpostoFederal" tabindex="4" value="1" disabled="disabled"/>
						<% } %>
                    </td>
                </tr>
                
                <tr>
					<td>
						<strong>Banco:</strong>
					</td>
                    <td>
						<html:select property="banco" tabindex="5" onchange="javascript:pesquisaColecaoReload('atualizarClienteWizardAction.do?action=exibirAtualizarClienteResponsavelAction&consultaBanco=1','banco');">
							<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<html:options collection="colecaoBanco" labelProperty="descricaoComId" property="id" />
	                   	</html:select>
                    </td>
                </tr>
				
				<tr>
					<td>
						<strong>Agencia:</strong>
					</td>
                    <td>
						<html:select property="agencia" tabindex="6">
							<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<html:options collection="colecaoAgencia" labelProperty="codigoAgencia" property="id" />
	                   	</html:select>
                    </td>
                </tr>
                
                <tr>
					<td>
						<strong>Numero da Conta:</strong>
					</td>
                    <td>
						<html:text maxlength="12" property="contaBancaria" size="12" tabindex="6"/>
                    </td>
                </tr>
				
				<tr>
					<td><strong></strong></td>
                    <td>
                    	<strong><font color="#FF0000">*</font></strong>Campo obrigat&oacute;rio
                    </td>
				</tr>
				<tr>
					<td colspan="2"><div align="right"><jsp:include page="/jsp/util/wizard/navegacao_botoes_wizard_valida_avancar.jsp?numeroPagina=5"/></div></td>
				</tr>
			</table>
			<p>&nbsp;</p>
		</td>
	</tr>
</table>

<%@ include file="/jsp/util/rodape.jsp"%>
</div>
</body>
</html:form>

<%@ include file="/jsp/util/telaespera.jsp"%>

<script>
document.getElementById('botaoConcluir').onclick = function() { botaoAvancarTelaEspera('/gsan/atualizarClienteWizardAction.do?concluir=true&action=atualizarClienteResponsavelAction'); }
</script>



</html:html>