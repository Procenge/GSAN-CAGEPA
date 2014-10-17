<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page import="gcom.util.ConstantesSistema"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<html:javascript formName="InformarSistemaParametrosActionForm"
	dynamicJavascript="false" staticJavascript="true" />

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script>

var bCancel = false;

function validateInformarSistemaParametrosActionForm(form) {
	if (bCancel)
		return true;
	else
		return testarCampoValorZero(document.InformarSistemaParametrosActionForm.logradouro, 'Logradouro')
		&& validateCaracterEspecial(form) 
		&& validateRequired(form)
		//&& validateInteger(form)  
		&& validateCnpj(form)
		&& validateEmail(form);
}
function IntegerValidations () {
	this.aa = new Array("cnpj", "CNPJ deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	
}

function cnpj () {
	this.aa = new Array("cnpj", "CNPJ inválido.", new Function ("varName", " return this[varName];"));
}

function caracteresespeciais () {
	this.aa = new Array("nomeEstado", "Nome do Estado possui caracteres especiais.", new Function ("varName", " return this[varName];"));
	this.ab = new Array("nomeEmpresa", "Nome da Empresa possui caracteres especiais.", new Function ("varName", " return this[varName];"));
	this.ac = new Array("abreviaturaEmpresa", "Abreviatura da Empresa possui caracteres especiais.", new Function ("varName", " return this[varName];"));
	this.ad = new Array("cnpj", "CNPJ possui caracteres especiais.", new Function ("varName", " return this[varName];"));
}

function required () {
	
	this.aa = new Array("nomeEstado", "Informe Nome do Estado.", new Function ("varName", " return this[varName];"));
	this.ab = new Array("nomeEmpresa", "Informe Nome da Empresa.", new Function ("varName", " return this[varName];"));
	this.ac = new Array("abreviaturaEmpresa", "Informe Abreviatura da Empresa.", new Function ("varName", " return this[varName];"));
	this.ad = new Array("cnpj", "Informe CNPJ.", new Function ("varName", " return this[varName];"));
	this.ae = new Array("logradouro", "Informe Logradouro.", new Function ("varName", " return this[varName];"));	

}

function email () {
	this.aa = new Array("email", "E-Mail inválido.", new Function ("varName", " return this[varName];"));
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
					abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisaLogradouro=" + tipo, altura, largura);
				}
			}
		}
	}

	//Recupera Dados Popup
function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
		var form = document.forms[0];
	    if (tipoConsulta == 'logradouro') {
	    	form.logradouro.value = codigoRegistro;
	      	form.action='exibirInformarParametrosSistemaAction.do';
	      	form.submit();
	    }
	}
	
	
	function limparLogradouro(){
	var form = document.forms[0];
	form.logradouro.value = '';
	form.nomeLogradouro.value = '';	
	verificaBloqueio();
	}
	
	function verificaBloqueio(){
	var form = document.forms[0];
		if(form.logradouro.value == '' ||form.logradouro.value == null ){
		form.bairro.disabled = true;
		form.bairro.value = '-1';
		form.cep.disabled = true;
		form.cep.value = '-1';
		}else{
		form.bairro.disabled = false;
		form.cep.disabled = false;
			
		}
	}	


</script>
</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:verificaBloqueio();setarFoco('${requestScope.nomeCampo}');">

<html:form action="/informarParametrosSistemaWizardAction" method="post"
	onsubmit="return validateInformarSistemaParametrosActionForm(this);">

	<jsp:include
		page="/jsp/util/wizard/navegacao_abas_wizard_valida_avancar.jsp?numeroPagina=1" />

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<input type="hidden" name="numeroPagina" value="1" />
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
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Informar Parâmetros do Sistema</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0" dwcopytype="CopyTableRow">
				<tr>
					<td>Para informar parâmetros do sistema, informe os dados abaixo:
					<td align="right"></td>
				</tr>
			</table>

			<table width="100%" border="0">

				<tr>
					<td colspan="2" align="center"><strong>Dados Gerais da Empresa</strong></td>
				</tr>
				<tr>
					<td width="25%" align="left"><strong>Nome do Estado:<font
						color="#FF0000">*</font></strong></td>
					<td width="82%"><html:text maxlength="25" property="nomeEstado"
						size="25" /></td>
				</tr>
				<tr>
					<td width="25%" align="left"><strong>Nome da Empresa:<font
						color="#FF0000">*</font></strong></td>
					<td><html:text maxlength="45" property="nomeEmpresa" size="45" /></td>
				</tr>
				<tr>
					<td width="25%" align="left"><strong>Abreviatura da Empresa:<font
						color="#FF0000">*</font></strong></td>
					<td><html:text maxlength="10" property="abreviaturaEmpresa"
						size="10" /></td>
				</tr>
				<tr>
					<td width="25%" align="left"><strong> CNPJ: <font color="#FF0000">*</font></strong></td>
					<td width="87%"><html:text property="cnpj" size="14" maxlength="14" />
					<font size="1"> &nbsp; </font></td>
				</tr>

				<tr>

					<td width="25%" align="left"><strong>Logradouro:<font
						color="#FF0000">*</font></strong></td>
					<td colspan="2"><html:text maxlength="5" property="logradouro"
						size="5"
						onkeypress="validaEnterComMensagem(event, 'exibirInformarParametrosSistemaAction.do', 'logradouro','Código do Logradouro');" /><a
						href="javascript:chamarPopup('exibirPesquisarLogradouroAction.do', 'origem', null, null, 275, 480, '',document.forms[0].logradouro);"><img
						width="23" height="21" border="0" style="cursor:hand;"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Logradouro" /></a> <logic:notPresent
						name="LogradouroInexistente" scope="request">
						<html:text property="nomeLogradouro" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000"
							size="37" maxlength="40" />
					</logic:notPresent> <logic:present name="LogradouroInexistente"
						scope="request">
						<html:text property="nomeLogradouro" size="30" maxlength="30"
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: red" />
					</logic:present> <a href="javascript:limparLogradouro();"><img
						border="0" title="Apagar"
						src="<bean:message key='caminho.imagens'/>limparcampo.gif" /></a></td>
				</tr>

				<tr>
					<td width="25%" align="left"><strong>Número:</strong></td>
					<td><html:text maxlength="5" property="numero" size="5" onkeyup="javascript:verificaNumeroInteiro(this);"/></td>
				</tr>

				<tr>
					<td width="25%" align="left"><strong>Complemento:</strong></td>
					<td><html:text maxlength="25" property="complemento" size="25" /></td>
				</tr>

				<tr>
					<td width="25%" align="left"><strong>Bairro:</strong></td>
					<td><html:select property="bairro">
						<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="colecaoLogradouroBairro"
							labelProperty="bairro.nome" property="bairro.id" />
					</html:select></td>
				</tr>
				<tr>
					<td width="25%" align="left"><strong>CEP:</strong></td>
					<td><html:select property="cep">
						<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="colecaoLogradouroCep"
							labelProperty="cep.codigo" property="cep.cepId" />
					</html:select></td>
				</tr>
				<tr>
					<td width="25%" align="left"><strong>Endereço de Referência:</strong></td>
					<td><html:select property="enderecoReferencia">
						<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="colecaoEnderecoReferencia"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>
				<tr>
					<td width="25%" align="left"><strong>Número do Telefone:</strong></td>
					<td><html:text maxlength="9" property="numeroTelefone" size="9" onkeyup="javascript:verificaNumeroInteiro(this);"/></td>
				</tr>
				<tr>
					<td width="25%" align="left"><strong>Ramal:</strong></td>
					<td><html:text maxlength="4" property="ramal" size="4" onkeyup="javascript:verificaNumeroInteiro(this);"/></td>
				</tr>
				<tr>
					<td width="25%" align="left"><strong>Fax:</strong></td>
					<td><html:text maxlength="9" property="fax" size="9" onkeyup="javascript:verificaNumeroInteiro(this);"/></td>
				</tr>
				<tr>
					<td width="25%" align="left"><strong>E-Mail:</strong></td>
					<td><html:text styleClass="input" maxlength="40" property="email" size="40" /></td>
				</tr>
				<tr>
					<td width="25%" align="left"><strong>Nome Imagem Logomarca:<font
						color="#FF0000"></font></strong></td>
					<td width="82%">
						<html:select property="imagemLogomarca" styleClass="input">
						<html:option value="">&nbsp;</html:option>
						<logic:present name="nomesImagens">
						<html:options name="nomesImagens" />
						</logic:present>
						</html:select> 
						<html:text property="imagemLogomarca" readonly="true" size="15" styleClass="input" />
						(140 x 59)
					</td>
				</tr>
				<tr>
					<td width="25%" align="left"><strong>Nome Imagem Cabe&ccedil;alho:<font
						color="#FF0000"></font></strong></td>
					<td width="82%">
						<html:select property="imagemCabecalho" styleClass="input">
						<html:option value="">&nbsp;</html:option>
						<logic:present name="nomesImagens">
						<html:options name="nomesImagens" />
						</logic:present>
						</html:select> 
						<html:text property="imagemCabecalho" readonly="true" size="15" styleClass="input" />
						(84 x 59)
					</td>
				</tr>
				<tr>
					<td width="25%" align="left"><strong>Nome Imagem Principal:<font
						color="#FF0000"></font></strong></td>
					<td width="82%">
						<html:select property="imagemPrincipal" styleClass="input">
						<html:option value="">&nbsp;</html:option>
						<logic:present name="nomesImagens">
						<html:options name="nomesImagens" />
						</logic:present>
						</html:select> 
						<html:text property="imagemPrincipal" readonly="true" size="15" styleClass="input" />
						(103 x 160)
					</td>
				</tr>
				<tr>
					<td width="25%" align="left"><strong>Nome Imagem Secund&aacute;ria:<font
						color="#FF0000"></font></strong></td>
					<td width="82%">
						<html:select property="imagemSecundaria" styleClass="input">
						<html:option value="">&nbsp;</html:option>
						<logic:present name="nomesImagens">
						<html:options name="nomesImagens" />
						</logic:present>
						</html:select>
						<html:text property="imagemSecundaria" readonly="true" size="15" styleClass="input" /> 
						(167 x 49)
					</td>
				</tr>
				<tr>
					<td width="25%" align="left"><strong>Nome Imagem Relat&oacute;rio:<font
						color="#FF0000"></font></strong></td>
					<td width="82%">
						<html:select property="imagemRelatorio" styleClass="input">
							<html:option value="">&nbsp;</html:option>
							<logic:present name="nomesImagens">
								<html:options name="nomesImagens" />
							</logic:present>
						</html:select> 
						<html:text property="imagemRelatorioPatch" readonly="true" size="15" styleClass="input" />
						(89 x 97)
					</td>
				</tr>
				<tr>
					<td width="25%" align="left"><strong>Nome Imagem Conta:<font
						color="#FF0000"></font></strong></td>
					<td width="82%">
						<html:select property="imagemConta" styleClass="input">
							<html:option value="">&nbsp;</html:option>
							<logic:present name="nomesImagens" >
								<html:options name="nomesImagens" />
							</logic:present>
						</html:select> 
						<html:text property="imagemContaPatch" readonly="true" size="15" styleClass="input" />
						(89 x 97)
					</td>
				</tr>				
				<tr>
					<td><strong></strong></td>
					<td><strong><font color="#FF0000">*</font></strong>Campo
					obrigat&oacute;rio</td>
				</tr>
				<tr>
					<td colspan="2">
					<div align="right"><jsp:include
						page="/jsp/util/wizard/navegacao_botoes_wizard_valida_avancar.jsp?numeroPagina=1" /></div>
					</td>
				</tr>
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>
</body>
</html:form>
</html:html>
