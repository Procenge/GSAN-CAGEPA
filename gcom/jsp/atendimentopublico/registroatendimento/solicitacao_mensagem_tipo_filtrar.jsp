<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>

<%@page import="gcom.util.ConstantesSistema"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
	<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
	<html:javascript staticJavascript="false"  formName="FiltrarMensagemTipoSolicitacaoEspecificacaoActionForm" dynamicJavascript="true" />
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript">

function verificarChecado(valor){
	form = document.forms[0];
	if(valor == "1"){
	 	form.indicadorAtualizar.checked = true;
	 }else{
	 	form.indicadorAtualizar.checked = false;
	}
}

function setaFocus(){
	var form = document.FiltrarMensagemTipoSolicitacaoEspecificacaoActionForm;
	form.descricaoMensagem.focus();
}

function verificarValorAtualizar(){
	var form = document.FiltrarMensagemTipoSolicitacaoEspecificacaoActionForm;
   	
   	if (form.indicadorAtualizar.checked == true) {
   		form.indicadorAtualizar.value = '1';
   	} else {
   		form.indicadorAtualizar.value = '';
   	}
   	
}

function validarForm() {
  var form = document.forms[0];
  
  form.action = 'filtrarMensagemTipoSolicitacaoEspecificacaoAction.do';
  if(validateFiltrarMensagemTipoSolicitacaoEspecificacaoActionForm(form)){	
  		submeterFormPadrao(form); 
	  	} 
 }

</script>

</head>

<body leftmargin="5" topmargin="5"
	onload="verificarChecado('${indicadorAtualizar}');setaFocus();">

<html:form action="/filtrarTipoSolicitacaoEspecificacaoAction"
	name="FiltrarMensagemTipoSolicitacaoEspecificacaoActionForm"
	type="gcom.gui.atendimentopublico.registroatendimento.FiltrarMensagemTipoSolicitacaoEspecificacaoActionForm"
	method="post"
	onsubmit="return validateFiltrarMensagemTipoSolicitacaoEspecificacaoActionForm(this);">

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
					<td class="parabg">Filtrar Mensagem Tipo de Solicitação Especificação</td>

					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
				<tr>
					<td height="5" colspan="3"></td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td width="80%" colspan="2">Para filtrar uma mensagem tipo de solicitação,
					informe os dados abaixo:</td>
					<td align="right"><input type="checkbox" name="indicadorAtualizar" value="1"
						onclick="javascript:verificarValorAtualizar();" /><strong>Atualizar</strong>
					</td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td><strong>Descrição da Mensagem:<strong></td>
					<td><html:text property="descricaoMensagem" size="50"
						maxlength="200" tabindex="1" /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><html:radio property="tipoPesquisa"	tabindex="2"
						value="<%=ConstantesSistema.TIPO_PESQUISA_INICIAL.toString()%>" />
						Iniciando pelo texto
						<html:radio	property="tipoPesquisa" tabindex="3"
						value="<%=ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString()%>" />
						Contendo o texto
					</td>
				</tr>
				<tr>
					<td><strong>Indicador de Uso:</strong></td>
					<td><html:radio property="indicadorUso"
						value="<%=ConstantesSistema.INDICADOR_USO_ATIVO.toString()%>"
						tabindex="4" /> <strong>Ativo</strong>&nbsp; <html:radio
						property="indicadorUso"
						value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>"
						tabindex="5" /> <strong>Inativo</strong>&nbsp; <html:radio
						property="indicadorUso" value="" tabindex="6" /> <strong>Todos</strong>
				</tr>
				
				<tr>
					<td colspan="3">&nbsp;</td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td valign="top"><input name="button" type="button"
						class="bottonRightCol" value="Limpar"
						onclick="window.location.href='/gsan/exibirFiltrarMensagemTipoSolicitacaoEspecificacaoAction.do?menu=sim'">&nbsp;</td>
					<td valign="top">
					<div align="right"><input name="button" type="button"
						class="bottonRightCol" value="Filtrar"
						onclick="validarForm(document.forms[0]);" tabindex="7"></div>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>
</body>
</html:form>
</html:html>
