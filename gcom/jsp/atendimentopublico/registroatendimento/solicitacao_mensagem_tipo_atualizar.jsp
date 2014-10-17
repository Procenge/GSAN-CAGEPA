<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<link rel="stylesheet"	href="<bean:message key="caminho.css"/>EstilosCompesa.css"	type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false" formName="AtualizarMensagemTipoSolicitacaoEspecificacaoActionForm" />

<script language="JavaScript"	src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript">

function validarForm() {
 	var form = document.forms[0];
	if(validateAtualizarMensagemTipoSolicitacaoEspecificacaoActionForm(form)){	     
	  	submeterFormPadrao(form); 
	 }
}

function validateAtualizarMensagemTipoSolicitacaoEspecificacaoActionForm(form){
	return validateRequired(form);
}

function required () {
     this.aa = new Array("descricaoMensagem", "Informe a Descrição.", new Function ("varName", " return this[varName];"));
}

function limparForm() {
	var form = document.forms[0];
	form.descricaoMensagem.value = "";
    form.descricaoAbreviada.value = "";
}

</script>

</head>

<body leftmargin="5" topmargin="5"
	onload="setarFoco('${requestScope.nomeCampo}');">

<html:form action="/atualizarMensagemTipoSolicitacaoEspecificacaoAction"
	name="AtualizarMensagemTipoSolicitacaoEspecificacaoActionForm"
	type="gcom.gui.atendimentopublico.registroatendimento.AtualizarMensagemTipoSolicitacaoEspecificacaoActionForm"
	method="post"
	onsubmit="return validateAtualizarMensagemTipoSolicitacaoEspecificacaoActionForm(this);">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>
	<table width="770" border="0" cellspacing="5" cellpadding="0">
		<tr>

			<td width="115" valign="top" class="leftcoltext">
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
			<td width="600" valign="top" bgcolor="#003399" class="centercoltext">
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
					<td class="parabg">Atualizar Mensagem Tipo de Solicitação Especificação</td>

					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para atualizar a mensagem tipo solicitação especificação, informe os dados abaixo:</td>
				</tr>
				
				<tr>
				  <td  width="30%" class="style3"><strong>Descrição:<font color="#FF0000">*</font></strong></td>
				  <td  width="70%" colspan="2"><strong><b><span class="style2"> 
				  		<html:textarea property="descricaoMensagem" cols="38" rows="4" tabindex="1" onkeyup="limitTextArea(document.forms[0].descricaoMensagem, 200, document.getElementById('utilizado'), document.getElementById('limite'));"/></span><br>
						<strong><span id="utilizado">0</span>/<span id="limite">200</span></strong></td>
				</tr>	
			
				<tr>
				  <td  width="30%" class="style3"><strong>Descrição Abreviada:</strong></td>
				  <td  width="70%" colspan="2"><strong><b><span class="style2"> 
				  		<html:text  property="descricaoAbreviada" size="50" maxlength="50" tabindex="2"/> </span></b></strong></td>
				</tr>
			
				<tr>
					<td><strong>Indicador de uso:<font color="#FF0000">*</font></strong></td>
						<td><html:radio property="indicadorUso" value="1" tabindex="5"/><strong>Ativo
							<html:radio property="indicadorUso" value="2" tabindex="6"/>Inativo</strong> 
						</td>
				</tr>
				
				<tr>
				</tr>
				<tr>
				</tr>
				<tr>
				</tr>
				
				<tr>
					<td width="500" colspan="2">
					<div align="center" ><font color="#FF0000">*</font> Campos obrigatórios</div>
					</td>
				</tr>
			
			</table>
			<table width="100%">
				<tr>
					<td width="40%" align="left">
						<input type="button" name="ButtonCancelar" class="bottonRightCol" value="Voltar"
						onClick="javascript:window.location.href='${sessionScope.caminhoRetornoVoltar}';">
						
						<input type="button" name="ButtonReset" class="bottonRightCol" value="Desfazer" 
						onclick="window.location.href='/gsan/exibirAtualizarMensagemTipoSolicitacaoEspecificacaoAction.do?desfazer=S&reloadPage=1&idMensagemTipoSolicitacao=${idMensagemTipoSolicitacao}';">
						
						<input type="button"name="ButtonCancelar" class="bottonRightCol" value="Cancelar" 
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
					</td>
					<td align="right">
						<input type="button" name="Button" class="bottonRightCol"
						value="Atualizar" onclick="javascript:validarForm();" tabindex="7" />
					</td>
				</tr>
			</table>
		</tr>


	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>
</body>
</html:form>
</html:html>
