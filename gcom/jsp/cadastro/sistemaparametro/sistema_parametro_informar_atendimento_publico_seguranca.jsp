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
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<html:javascript formName="InformarSistemaParametrosActionForm"	dynamicJavascript="false" staticJavascript="true" />

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>

<script LANGUAGE="JavaScript">
function carregarEspecificacao(obj) {
 	var idSolicitacaoTipo = obj.options[obj.selectedIndex].value;
  	var campoEspecificacao = document.getElementById("especificacao");
    
  	campoEspecificacao.length=0;
  	if (idSolicitacaoTipo != "-1") {
		  AjaxService.carregarEspecificacao( idSolicitacaoTipo, function(especificacao) {
				  for (key in especificacao){
					  var novaOpcao = new Option(key, especificacao[key]);
					  campoEspecificacao.options[campoEspecificacao.length] = novaOpcao;
		  		   }
				  });
  	} else {
  		var novaOpcao = new Option(" ","-1");
		campoEspecificacao.options[campoEspecificacao.length] = novaOpcao;
  	}	
}

function carregarEspecificacaoReiteracao(obj) {
 	var idSolicitacaoTipo = obj.options[obj.selectedIndex].value;
    var campoEspecificacaoReiteracao = document.getElementById("especificacaoReiteracao");
    	
  	campoEspecificacaoReiteracao.length=0;
  	if (idSolicitacaoTipo != "-1") {
		  AjaxService.carregarEspecificacao( idSolicitacaoTipo, function(especificacao) {
				  for (key in especificacao){
					  var novaOpcao = new Option(key, especificacao[key]);
					  campoEspecificacaoReiteracao.options[campoEspecificacaoReiteracao.length] = novaOpcao;
		  		   }
				  });
  	} else {
  		var novaOpcao = new Option(" ","-1");
		campoEspecificacaoReiteracao.options[campoEspecificacaoReiteracao.length] = novaOpcao;
  	}	
}

var bCancel = false;

function validateInformarSistemaParametrosActionForm(form) {
	if (bCancel)
		return true;
	else
		return  validateRequired(form)
		&& validateEmail(form)
		&& validateInteger(form);
		//validateCaracterEspecial(form) 
		//&& validateRequired(form)
		//&& validateInteger(form);
}
function IntegerValidations () {
	this.aa = new Array("diasMaximoReativarRA", "Dias Máximo para Reativar RA deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	this.ab = new Array("diasMaximoAlterarOS", "Dias Máximo para Alterar Dados da OS deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	this.ac = new Array("ultimoIDGeracaoRA", "Último ID Utilizado para Geração do RA Manual deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	this.ad = new Array("diasMaximoExpirarAcesso", "Dias Máximo para Expirar o Acesso deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	this.ae = new Array("diasMensagemExpiracaoSenha", "Dias para Começar Aparecer a	Mensagem de Expiração de Senha deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	this.af = new Array("numeroMaximoTentativasAcesso", "Número Máximo de Tentativas de	Acesso deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	this.ag = new Array("numeroMaximoFavoritosMenu", "Número Máximo de Favoritos no	Menu do Sistema deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	this.ah = new Array("idUnidadeOrganizacionalPresidencia", "Unidade Organizacional da Presidência deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
}

function email () {
	this.aa = new Array("emailResponsavel", "E-Mail do Responsável inválido.", new Function ("varName", " return this[varName];"));
}

/*function caracteresespeciais () {
	this.aa = new Array("nomeEstado", "Nome do Estado possui caracteres especiais.", new Function ("varName", " return this[varName];"));
	this.ab = new Array("nomeEmpresa", "Nome da Empresa possui caracteres especiais.", new Function ("varName", " return this[varName];"));
	this.ac = new Array("abreviaturaEmpresa", "Abreviatura da Empresa possui caracteres especiais.", new Function ("varName", " return this[varName];"));
	this.ad = new Array("cnpj", "CNPJ possui caracteres especiais.", new Function ("varName", " return this[varName];"));
}*/

function required () {
	this.aa = new Array("diasMaximoAlterarOS", "Informe Dias Máximo para Alterar Dados da OS.", new Function ("varName", " return this[varName];"));
	this.ab = new Array("ultimoIDGeracaoRA", "Informe Último ID Utilizado para Geração do RA Manual.", new Function ("varName", " return this[varName];"));
	this.ac = new Array("ipServidorSmtp", "Informe IP do Servidor SMTP.", new Function ("varName", " return this[varName];"));
	this.ad = new Array("emailResponsavel", "Informe E-mail do Responsável.", new Function ("varName", " return this[varName];"));
	this.ae = new Array("idUnidadeOrganizacionalPresidencia", "Informe Unidade Organizacional da Presidência .", new Function ("varName", " return this[varName];"));
}

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta){
  var form = document.InformarSistemaParametrosActionForm;
	if (tipoConsulta == 'unidadeOrganizacional') {
		form.idUnidadeOrganizacionalPresidencia.value = codigoRegistro;
		form.nomeUnidadeOrganizacionalPresidencia.value = descricaoRegistro;
		form.nomeUnidadeOrganizacionalPresidencia.style.color = "#000000";		
		setarFoco('diasMaximoExpirarAcesso');
	}
}

</script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/engine.js'> </script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/util.js'> </script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/interface/AjaxService.js'> </script>

</head>

<body leftmargin="5" topmargin="5">

<html:form action="/informarParametrosSistemaWizardAction" method="post"
	onsubmit="return validateInformarSistemaParametrosActionForm(this);">

	<jsp:include
		page="/jsp/util/wizard/navegacao_abas_wizard_valida_avancar.jsp?numeroPagina=5" />

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<input type="hidden" name="numeroPagina" value="5" />
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
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" align="center"><strong>Parâmetros para Atendimento
					ao Público:</strong></td>
				</tr>

				<tr>
					<td>&nbsp;</td>
				</tr>

				<tr>
					<td width="40%"><strong>Indicador de Sugestão de Trâmite:</strong></td>
					<td><strong> <html:radio property="indicadorSugestaoTramite"
						value="1" tabindex="1" /> Sim <html:radio property="indicadorSugestaoTramite"
						value="2" tabindex="2" /> N&atilde;o </strong></td>

				</tr>


				<tr>
					<td width="40%" align="left"><strong>Dias Máximo para Reativar RA:</strong></td>
					<td width="87%"><html:text property="diasMaximoReativarRA" size="2"
						maxlength="2" tabindex="3" /> <font size="1"> &nbsp; </font></td>
				</tr>

				<tr>
					<td width="40%" align="left"><strong>Dias Máximo para Alterar Dados
					da OS:<font color="#FF0000">*</font></strong></td>
					<td width="87%"><html:text property="diasMaximoAlterarOS" size="2"
						maxlength="2" tabindex="4" /> <font size="1"> &nbsp; </font></td>
				</tr>

				<tr>
					<td width="40%" align="left"><strong>Último ID Utilizado para
					Geração do RA Manual:<font color="#FF0000">*</font></strong></td>
					<td width="87%"><html:text property="ultimoIDGeracaoRA" size="5"
						maxlength="5" onkeyup="javascript:verificaNumeroInteiro(this);" tabindex="5" />
					<font size="1"> &nbsp; </font></td>
				</tr>
				<tr>
					<td width="40%" align="left"><strong>Unidade Organizacional 
					da Presidência: <font color="#FF0000">*</font></strong></td>
					<td width="87%"><html:text maxlength="8" property="idUnidadeOrganizacionalPresidencia"
						tabindex="6" size="6" onkeyup="javascript:limparUnidadeTecla();" onkeyup="javascript:verificaNumeroInteiro(this);" 
						onkeypress="javascript:validaEnterComMensagem(event, 'informarParametrosSistemaWizardAction.do?action=exibirInformarParametrosSistemaAtendimentoPublicoSegurancaAction', 'idUnidadeOrganizacionalPresidencia', 'Unidade Organizacional');" />						
					<a
						href="javascript:abrirPopup('exibirPesquisarUnidadeOrganizacionalAction.do');">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Unidade Organizacional" /></a> <logic:present
						name="idUnidadeNaoEncontrado" scope="request">
						<html:text maxlength="30" property="nomeUnidadeOrganizacionalPresidencia" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000"
							size="40" />
					</logic:present> <logic:notPresent name="idUnidadeNaoEncontrado"
						scope="request">
						<html:text maxlength="30" property="nomeUnidadeOrganizacionalPresidencia" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000"
							size="40" />
					</logic:notPresent> </td>
				</tr>
				
<!-- * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *  -->
<!-- * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *  -->
<!-- * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *  -->
				<tr>
		        <td HEIGHT="30"><strong>Tipo de Solicitação Padrão:<font color="#FF0000">*</font></strong></td>
		        <td>
					<html:select property="tipoSolicitacao" style="width: 350px;font-size:11px;" tabindex="11" onchange="carregarEspecificacao(this);" styleId="tipoSolicitacao">
						<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="colecaoSolicitacaoTipo" labelProperty="descricao" property="id" />
					</html:select>
				</td>
		      </tr>
		      <tr>
		        <td HEIGHT="30"><strong>Especificação Padrão:<font color="#FF0000">*</font></strong></td>
		        <td>
					<html:select property="especificacao" style="width: 350px;font-size:11px;" tabindex="12" styleId="especificacao"> 
							<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="colecaoEspecificacaoPadrao" labelProperty="descricao" property="id"/>
					</html:select>
				</td>
		      </tr>
		      
		      <tr>
		        <td HEIGHT="30"><strong>Tipo de Solicitação Reiteração:<font color="#FF0000">*</font></strong></td>
		        <td>
					<html:select property="tipoSolicitacaoReiteracao" style="width: 350px;font-size:11px;" tabindex="11" onchange="carregarEspecificacaoReiteracao(this);" styleId="tipoSolicitacaoReiteracao" >
						<html:options collection="colecaoSolicitacaoTipo" labelProperty="descricao" property="id" />
					</html:select>
				</td>
		      </tr>
		      
		      <tr>
		        <td HEIGHT="30"><strong>Especificação Reiteração:<font color="#FF0000">*</font></strong></td>
		        <td>
					<html:select property="especificacaoReiteracao" style="width: 350px;font-size:11px;" tabindex="12" styleId="especificacaoReiteracao"> 
						<html:options collection="colecaoEspecificacaoReiteracao" labelProperty="descricao" property="id"/>
					</html:select>
				</td>
		      </tr>
<!-- * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *  -->
<!-- * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *  -->
<!-- * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *  -->
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" align="center"><strong>Parâmetros para Segurança:</strong></td>

				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td width="40%" align="left"><strong>Dias Máximo para Expirar o
					Acesso:</strong></td>
					<td><html:text maxlength="2" property="diasMaximoExpirarAcesso"
						size="2" onkeyup="javascript:verificaNumeroInteiro(this);" tabindex="7" /></td>
				</tr>

				<tr>
					<td width="40%" align="left"><strong>Dias para Começar Aparecer a
					Mensagem de Expiração de Senha:</strong></td>
					<td><html:text maxlength="2" property="diasMensagemExpiracaoSenha"
						size="2" onkeyup="javascript:verificaNumeroInteiro(this);" tabindex="8" /></td>
				</tr>

				<tr>
					<td width="40%" align="left"><strong>Número Máximo de Tentativas de
					Acesso:</strong></td>
					<td><html:text maxlength="2"
						property="numeroMaximoTentativasAcesso" size="2"
						onkeyup="javascript:verificaNumeroInteiro(this);" tabindex="9" /></td>
				</tr>

				<tr>
					<td width="40%" align="left"><strong>Número Máximo de Favoritos no
					Menu do Sistema:</strong></td>
					<td><html:text maxlength="2" property="numeroMaximoFavoritosMenu"
						size="2" onkeyup="javascript:verificaNumeroInteiro(this);" tabindex="10" /></td>
				</tr>
				
				<tr>
					<td width="40%" align="left"><strong>IP do Servidor SMTP:</strong></td>
					<td><html:text maxlength="50" property="ipServidorSmtp"
						size="30" tabindex="11"/></td>
				</tr>
				
				<tr>
					<td width="40%" align="left"><strong>E-mail do Responsável:</strong></td>
					<td><html:text maxlength="80" property="emailResponsavel"
						size="35" tabindex="12"/></td>
				</tr>

				<tr>
					<td><strong></strong></td>
					<td><strong><font color="#FF0000">*</font></strong>Campo
					obrigat&oacute;rio</td>
				</tr>
				<tr>
					<td colspan="2">
					<div align="right"><jsp:include
						page="/jsp/util/wizard/navegacao_botoes_wizard_valida_avancar.jsp?numeroPagina=5" /></div>
					</td>
				</tr>
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</body>
</html:html>
