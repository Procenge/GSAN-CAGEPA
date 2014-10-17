<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ page import="gcom.util.ConstantesSistema" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<%@page import="gcom.cadastro.sistemaparametro.SistemaParametro"%><html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="ClienteActionForm" dynamicJavascript="false" />

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>

<!-- Validações de JavaScript para o Cliente DESO -->
<logic:equal name="paramInformarVencimentoCliente" value="<%="" + ConstantesSistema.NAO%>" scope="session">
<script>

var bCancel = false;

function validateClienteActionForm(form) {
	if (bCancel)
		return true;
	else
		return validateCaracterEspecial(form) 
		&& validateRequired(form) 
		&& validateEmail(form)
		&& validateLong(form);
}

function IntegerValidations () {
	
}

</script>
</logic:equal>

<!-- Validações de JavaScript para o Cliente ADA -->
<logic:equal name="paramInformarVencimentoCliente" value="<%="" + ConstantesSistema.SIM%>" scope="session">
<script>

var bCancel = false;

function validateClienteActionForm(form) {
	if (bCancel)
		return true;
	else
		return validateCaracterEspecial(form) 
		&& validateRequired(form) 
		&& validateEmail(form)
		&& validateLong(form)
	    && validarDiaVencimento();
}

function IntegerValidations () {
	this.aa = new Array("diaVencimento", "Dia do Vencimento deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
}

function validarDiaVencimento(){
var form = document.forms[0];
	
		if( form.diaVencimento.value != null &&  form.diaVencimento.value != ''&&
			 (form.diaVencimento.value < 1 || form.diaVencimento.value > 30)){	
				alert('Dia do vencimento de ser entre 01 e 30.');
				form.diaVencimento.focus();
				return false;
		}else{
			return true;
		}
	
}

</script>
</logic:equal>

<script>
<!--

function caracteresespeciais () {
	this.aa = new Array("nome", "Nome possui caracteres especiais.", new Function ("varName", " return this[varName];"));
	this.ab = new Array("nomeAbreviado", "Nome Abreviado possui caracteres especiais.", new Function ("varName", " return this[varName];"));
}

function required () {
	this.aa = new Array("nome", "Informe Nome.", new Function ("varName", " return this[varName];"));
	this.ab = new Array("tipoPessoa", "Informe Tipo do Cliente.", new Function ("varName", " return this[varName];"));
}

function email () {
	this.aa = new Array("email", "E-Mail inválido.", new Function ("varName", " return this[varName];"));
}

function pesquisarRA() {
	var form = document.forms[0];
	if(form.numeroRA.value != ""){
		redirecionarSubmit("exibirInserirClienteAction.do?idRA="+form.numeroRA.value);
	}	
}

function pesquisarRAEnter() {
	var form = document.forms[0];
	validaEnter('event', 'exibirInserirClienteAction.do?idRA='+form.numeroRA.value,'numeroRA');
}

// Função para redirecionar para a atualização quando é feita a pesquisa pela lupa do nome do cliente
function redirecionarSubmitAtualizar(id) {
 	urlRedirect = "/gsan/exibirAtualizarClienteAction.do?idRegistroAtualizacao=" + id;
 	redirecionarSubmit(urlRedirect);
}
-->
</script>
</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');">
<div id="formDiv">
<html:form
    action="/inserirClienteWizardAction"
    method="post"
    onsubmit="return validateClienteActionForm(this);"
>

<jsp:include page="/jsp/util/wizard/navegacao_abas_wizard_valida_avancar.jsp?numeroPagina=1"/>

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

<input type="hidden" name="numeroPagina" value="1"/>
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
		<td width="655" valign="top" class="centercoltext">
			<table height="100%">
				<tr>
			       <td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
					<td class="parabg">Inserir Cliente</td>
					<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0" dwcopytype="CopyTableRow">
				<tr>
					<td>
						Para adicionar o nome e tipo do cliente, informe os dados abaixo:
					<td align="right"></td>
    			</tr>
    		</table>
    
    		<table width="100%" border="0">
				<% if (((String)session.getAttribute("parametroEmpresa")).equals(SistemaParametro.INDICADOR_EMPRESA_DESO.toString())) { %>
				<tr>
					<td width="18%">
						<strong>R.A.:</strong>
					</td>
					<td width="82%">
						<input text id="numeroRA" maxlength="9" name="numeroRA" size="9" tabindex="0" onchange="javascript:pesquisarRA();" onkeypress="javascript:pesquisarRAEnter();"/>
					</td>
				</tr>
				<% } %>
				<tr>
					<td width="18%">
						<strong>Nome:<font color="#FF0000">*</font></strong>
					</td>
					<td width="82%">
						<html:text maxlength="50" property="nome" size="50" tabindex="1"/>
						<a href="javascript:abrirPopup('exibirPesquisarClienteAction.do?limparForm=ok&indicadorUsoTodos=1&consultaCliente=sim', 400, 800);">
 							<img width="23" height="21" border="0" title="Pesquisar Cliente"
 								src="<bean:message key="caminho.imagens"/>pesquisa.gif"/></a>
					</td>
				</tr>
				<tr>
					<td>
						<strong>Nome Abreviado:</strong>
					</td>
                    <td>
						<html:text maxlength="20" property="nomeAbreviado" size="20" tabindex="2"/>
                    </td>
                </tr>
				<tr>
					<td>
						<strong>Tipo do Cliente:<font color="#FF0000">*</font></strong>
					</td>
					<td>
						<html:select property="tipoPessoa" tabindex="3">
							<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<html:options collection="colecaoTipoPessoa" labelProperty="descricaoComId" property="id" />
	                   	</html:select>
	                </td>
                </tr>
                <% if (((String)session.getAttribute("parametroEmpresa")).equals(SistemaParametro.INDICADOR_EMPRESA_DESO.toString())) { %>
                <tr>
					<td>
						<strong>Cliente Especial:</strong>
					</td>
					<td>
						<html:select property="clienteTipoEspecial" tabindex="3">
							<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<html:options collection="colecaoClienteTipoEspecial" labelProperty="descricao" property="id" />
	                   	</html:select>
	                </td>
                </tr>
                <% } %>
                <tr>
					<td>
						<strong>E-Mail:</strong>
					</td>
					<td>
						<html:text maxlength="40" property="email" size="40" tabindex="4"/>
					</td>
				</tr>
				<logic:equal name="paramInformarVencimentoCliente" value="<%="" + ConstantesSistema.SIM%>" scope="session">
				<tr>
					<td><strong> Dia do Vencimento: </strong></td>
					<td><html:text maxlength="2" property="diaVencimento" size="2"
						tabindex="5" onkeypress="return isCampoNumerico(event);"/></td>
				</tr>
				</logic:equal>
				<logic:present scope="session" name="indicadorExibirCampoContaBraille">
					<tr>
						<td>
							<strong>Conta Impressa em Braille?<font color="#FF0000">*</font></strong>
						</td>
	                    <td align="right" colspan="2">
	                	  <div align="left">
	                		<html:radio property="indicadorContaBraille" value="<%=ConstantesSistema.SIM.toString()%>" disabled="false" /><strong>Sim</strong>
							<html:radio property="indicadorContaBraille" value="<%=ConstantesSistema.NAO.toString()%>" disabled="false" /><strong>Não</strong>
						  </div>
						</td>
	                </tr>
                </logic:present>
				<tr>
					<td><strong></strong></td>
                    <td>
                    	<strong><font color="#FF0000">*</font></strong>Campo obrigat&oacute;rio
                    </td>
				</tr>
				<tr>
					<td colspan="2"><div align="right"><jsp:include page="/jsp/util/wizard/navegacao_botoes_wizard_valida_avancar.jsp?numeroPagina=1"/></div></td>
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
document.getElementById('botaoConcluir').onclick = function() { botaoAvancarTelaEspera('/gsan/inserirClienteWizardAction.do?concluir=true&action=inserirClienteNomeTipoAction'); }
</script>



</html:html>