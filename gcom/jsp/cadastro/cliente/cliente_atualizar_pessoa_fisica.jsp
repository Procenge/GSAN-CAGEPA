<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@ page import="gcom.util.ConstantesSistema"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="ClienteActionForm" dynamicJavascript="false"	/>

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">

var indicadorUso = '<c:out value="${ClienteActionForm.map.indicadorUso}"/>';

<!--
function verificarGrupoRG(){
	
	if(indicadorUso != '2' ){

		var form = document.ClienteActionForm;
		var rg = form.rg.value;
		var dataEmissao = form.dataEmissao.value;
		var orgaoExpedidor = form.idOrgaoExpedidor[form.idOrgaoExpedidor.selectedIndex].value;
		var estado = form.idUnidadeFederacao[form.idUnidadeFederacao.selectedIndex].value;

		//Faz a verificação do grupo do RG, onde se um campo for preenchido, todos terão que ser preenchidos
		if (rg == '' && orgaoExpedidor == '-1' && estado == '-1') {
			return true;
		} else {
	    	if(rg != '' && orgaoExpedidor != '-1' && estado != '-1') {
	   			return true;
	        } else {
				alert('O preenchimento dos campos RG, Órgão Expedidor e Estado é obrigatório, caso algum deles seja informado.');
				return false;
		    }
		}
	
	}else{

		return true;

	}

}

var bCancel = false;

function validateClienteActionForm(form) {
	if (bCancel)
		return true;
	else
		return validateRequired(form) 
		&& testarCampoValorZero(document.ClienteActionForm.rg, 'RG') 
		&& validateCaracterEspecial(form) 
		&& validateLong(form) 
		&& validateDate(form) 
		&& verificarGrupoRG() 
		&& validateCpf(form) 
		&& validarCpf();
}

function required () {

	if(indicadorUso != '2'){

		this.ab = new Array("idPessoaSexo", "Informe Sexo.", new Function ("varName", " return this[varName];"));
		//this.ac = new Array("cpf", "Informe CPF.", new Function ("varName", " return this[varName];"));
		this.ad = new Array("rg", "Informe RG.", new Function ("varName", " return this[varName];"));

		<c:if test="${nomeMaeClienteObrigatorio eq '1'}">

			this.ae = new Array("nomeMae", "Informe o Nome da Mãe.", new Function ("varName", " return this[varName];"));	
	
		</c:if>

		<c:if test="${dataNascClienteObrigatoria eq '1'}">

			this.af = new Array("dataNascimento", "Informe a Data de Nascimento", new Function ("varName", " return this[varName];"));	

		</c:if>
		<c:if test="${indicadorRaca eq '1'}">
		this.ag = new Array("idRaca", "Informe Raça/Cor.", new Function ("varName", " return this[varName];"));
		</c:if>
	}	
		
}

function caracteresespeciais () {
	this.ac = new Array("cpf", "CPF possui caracteres especiais.", new Function ("varName", " return this[varName];"));
	this.ad = new Array("rg", "RG possui caracteres especiais.", new Function ("varName", " return this[varName];"));
	this.ae = new Array("nomeMae", "Nome da Mãe possui caracteres especiais.", new Function ("varName", " return this[varName];"));	
}

function validarCpf() {
	var form = document.forms[0];
	if (form.cpf != null &&
	form.cpf.value == '' ) {

// 		if(indicadorUso != '2'){

// 			alert('Informe CPF.');
// 			return false;

// 		}else{

			return true;

// 		}
		
	} else {
		if (!isCpf(form.cpf.value)) {
			alert('CPF Inválido.');
			return false;
		} else {
			return true;
		}
	}
} 


function isCpf(cpf) {
	
	var soma;
	var resto;
	var i;
	 
	if ( (cpf.length != 11) ||
		 (cpf == "00000000000") || (cpf == "11111111111") ||
		 (cpf == "22222222222") || (cpf == "33333333333") ||
		 (cpf == "44444444444") || (cpf == "55555555555") ||
		 (cpf == "66666666666") || (cpf == "77777777777") ||
		 (cpf == "88888888888") || (cpf == "99999999999") ) {
	 
		return false;
	}
	 
	soma = 0;
	 
	for (i = 1; i <= 9; i++) {
	
		soma += Math.floor(cpf.charAt(i-1)) * (11 - i);
	
	}
	 
	resto = 11 - (soma - (Math.floor(soma / 11) * 11));
	 
	if ( (resto == 10) || (resto == 11) ) {
		resto = 0;
	}
	 
	if ( resto != Math.floor(cpf.charAt(9)) ) {
		return false;
	}
	 
	soma = 0;
	 
	for (i = 1; i<=10; i++) {
		soma += cpf.charAt(i-1) * (12 - i);
	}
	 
	resto = 11 - (soma - (Math.floor(soma / 11) * 11));
	 
	if ( (resto == 10) || (resto == 11) ) {
		resto = 0;
	}
	 
	if (resto != Math.floor(cpf.charAt(10)) ) {
		return false;
	}
	 
	 return true;
}

function cpf () {
	this.aa = new Array("cpf", "CPF inválido.", new Function ("varName", " return this[varName];"));
}

function IntegerValidations () {
	this.aa = new Array("cpf", "CPF deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    //this.ab = new Array("rg", "RG deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
}

function DateValidations () {
	this.aa = new Array("dataEmissao", "Data de Emissão inválida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
	this.ab = new Array("dataNascimento", "Data de Nascimento inválida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
}
-->
</script>
</head>
<body leftmargin="5" topmargin="5"
	onload="setarFoco('${requestScope.nomeCampo}');">
<div id="formDiv">	
<html:form action="/atualizarClienteWizardAction" method="post">
	<jsp:include page="/jsp/util/wizard/navegacao_abas_wizard_valida_avancar.jsp?numeroPagina=2" />

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="4" cellpadding="0">
		<tr>
			<td width="149" valign="top" class="leftcoltext">
			<input type="hidden" name="numeroPagina" value="2" />
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
					<td class="parabg">Atualizar Cliente</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0" dwcopytype="CopyTableRow">
				<tr>
					<td>Para adicionar um cliente pessoa f&iacute;sica,
					informe os dados abaixo:</td>
				<td align="right"></td>
    			</tr>
    		</table>
    
    		<table width="100%" border="0">
				<tr>
					<td width="18%" height="31"><strong>CPF:<font color="#FF0000">*</font></strong></td>
					<td width="82%"><html:text property="cpf" size="11" maxlength="11"
						tabindex="1" /></td>
				</tr>
				<tr>
					<td height="24" colspan="2">
					<hr>
					</td>
				</tr>
				<tr>
					<td height="24"><strong>RG:<font color="#FF0000">*</font></strong></td>
					<td><html:text property="rg" size="13" maxlength="13" tabindex="2" /></td>
				</tr>
				<tr>
					<td height="24"><strong>Data de Emiss&atilde;o:</strong></td>
					<td>
						<html:text property="dataEmissao" size="10" maxlength="10"
							tabindex="3" onkeyup="javascript:mascaraData(this, event);" />
						<a href="javascript:abrirCalendario('ClienteActionForm', 'dataEmissao')">
							<img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif"
								width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
						<font size="2">dd/mm/aaaa</font>
					</td>
				</tr>
				<tr>
					<td height="24"><strong>&Oacute;rg&atilde;o Expedidor:</strong></td>
					<td>
						<html:select property="idOrgaoExpedidor" tabindex="4">
							<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<html:options collection="orgaosExpedidores" labelProperty="descricaoAbreviada" property="id" />
						</html:select>
					</td>
				</tr>
				<tr>
					<td height="24"><strong>Estado:</strong></td>
					<td><html:select property="idUnidadeFederacao" tabindex="5">
						<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="unidadesFederacao" labelProperty="sigla"
							property="id" />
					</html:select></td>
				</tr>
				
				<tr>
					<td height="24" colspan="2">
					<hr>
					</td>
				</tr>
				<tr>
					<td height="24"><strong>Data de Nascimento:<c:if test="${dataNascClienteObrigatoria eq '1'}"><font color="#FF0000">*</font></c:if></strong></td>
					<td>
						<html:text property="dataNascimento" size="10" maxlength="10"
							tabindex="6" onkeyup="javascript:mascaraData(this, event);" />
						<a href="javascript:abrirCalendario('ClienteActionForm', 'dataNascimento')">
						<img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif"
							width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
						<font size="2">dd/mm/aaaa</font>
					</td>
				</tr>
				<tr>
					<td height="24"><strong>Profiss&atilde;o:</strong></td>
					<td>
						<html:select property="idProfissao" tabindex="7">
							<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<html:options collection="profissoes" labelProperty="descricaoComId"	property="id" />
						</html:select>
					</td>
				</tr>
				<tr>
					<td height="24"><strong>Sexo:<font color="#FF0000">*</font></strong></td>
					<td>
						<html:select property="idPessoaSexo" tabindex="8">
							<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<html:options collection="pessoasSexos" labelProperty="descricaoComId" property="id" />
						</html:select>
					</td>
				</tr>
				<tr>
					<td height="24"><strong>Nome da Mãe:<c:if test="${nomeMaeClienteObrigatorio eq '1'}"><font color="#FF0000">*</font></c:if></strong></td>
					<td><html:text property="nomeMae" size="50" maxlength="50" tabindex="9" /></td>
				</tr>
				
					<tr>
						<td height="24"><strong>Raça/Cor:<logic:equal name="indicadorRaca" value="1"><font color="#FF0000">*</font></logic:equal></strong></td>
						<td><html:select property="idRaca" tabindex="5">
							<html:option
								value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<html:options collection="colecaoRacas" labelProperty="descricao"
								property="id" />
						</html:select>
						</td>
					</tr>
					<tr>
						<td height="24"><strong>Nacionalidade:</strong></td>
						<td>
						<html:select property="idNacionalidade" tabindex="5">
							<html:option
								value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<html:options collection="colecaoNacionalidade" labelProperty="descricao"
								property="id" />
						</html:select>
						</td>
					</tr>
					<tr>
						<td height="24"><strong>Estado Civil:</strong></td>
						<td><html:select property="idEstadoCivil" tabindex="11">
							<html:option
								value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<html:options collection="colecaoEstadoCivil" labelProperty="descricao"
								property="id" />
						</html:select>
						</td>
					</tr>
				
				<tr>
					<td height="24"><strong></strong></td>
					<td>
						<strong><font color="#FF0000">*</font></strong> Campo obrigat&oacute;rio
					</td>
				</tr>
				<tr>
					<td colspan="2" align="right">
						<jsp:include page="/jsp/util/wizard/navegacao_botoes_wizard_valida_avancar.jsp?numeroPagina=2" />
					</td>
				</tr>

			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>
	<%@ include file="/jsp/util/tooltip.jsp"%>
	</html:form>
	</div>
</body>


<%@ include file="/jsp/util/telaespera.jsp"%>

<script>
document.getElementById('botaoConcluir').onclick = function() { botaoAvancarTelaEspera('/gsan/atualizarClienteWizardAction.do?concluir=true&action=atualizarClientePessoaAction'); }
</script>

</html:html>
