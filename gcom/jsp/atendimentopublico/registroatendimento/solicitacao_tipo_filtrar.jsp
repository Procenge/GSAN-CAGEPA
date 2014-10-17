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
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false" 
	formName="FiltrarTipoSolicitacaoEspecificacaoActionForm" />

<script language="JavaScript">

function validaRadioButton(nomeCampo,mensagem){
	var alerta = "";
	if(!nomeCampo[0].checked && !nomeCampo[1].checked){
		alerta = mensagem +" deve ser selecionado.";
	}
	return alerta;
}
function validaTodosRadioButton(){
	var form = document.forms[0];
	var mensagem = "";
	if(validaRadioButton(form.indicadorFaltaAgua,"Falta D'água") != ""){
			mensagem = mensagem + validaRadioButton(form.indicadorFaltaAgua," Falta D'água")+"\n";
	}
	if(validaRadioButton(form.indicadorTarifaSocial,"Tarifa Social") != ""){
			mensagem = mensagem + validaRadioButton(form.indicadorTarifaSocial,"Tarifa Social")+"\n";
	}
	if(mensagem != ""){
		alert(mensagem);
		return false;
	}else{
		return true;
	}
}

    

 
function validarForm(form){

if(validateFiltrarTipoSolicitacaoEspecificacaoActionForm(form)){
    document.forms[0].target='';
    form.action="filtrarTipoSolicitacaoEspecificacaoAction.do";
    submeterFormPadrao(form);
}
}

function desfazer(){
 form = document.forms[0];
 document.forms[0].target='';
 form.action='exibirFiltrarTipoSolicitacaoEspecificacaoAction.do?limpaSessao=1';
 form.submit();
}

function abrirPopupComSubmit(form,altura, largura){
 var height = window.screen.height - 160;
 var width = window.screen.width;
 var top = (height - altura)/2;
 var left = (width - largura)/2;
 window.open('', 'Pesquisar','top=' + top + ',left='+ left +',location=no,screenY=0,screenX=0,menubar=no,status=no,toolbar=no,scrollbars=yes,resizable=no,width=' + largura + ',height=' + altura);
 form.target='Pesquisar';
 form.action = 'recuperarDadosFiltrarTipoSolicitacaoEspecificacaoAction.do?retornarTela=exibirFiltrarTipoSolicitacaoEspecificacaoAction.do';
 submeterFormPadrao(form);
}

function limparServicoTipo(){
	var form = document.forms[0];
	form.idServicoTipo.value = "";
	form.descricaoServicoTipo.value = "";
}

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta){
	var form = document.forms[0];

	if (tipoConsulta == 'tipoServico'){
		form.idServicoTipo.value = codigoRegistro;
		form.descricaoServicoTipo.value = descricaoRegistro;
		form.descricaoServicoTipo.style.color = "#000000";
	}
}
</script>

</head>

<body leftmargin="5" topmargin="5"
	onload="setarFoco('${requestScope.nomeCampo}');">

<html:form action="/filtrarTipoSolicitacaoEspecificacaoAction"
	name="FiltrarTipoSolicitacaoEspecificacaoActionForm"
	type="gcom.gui.atendimentopublico.registroatendimento.FiltrarTipoSolicitacaoEspecificacaoActionForm"
	method="post">

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
					<td class="parabg">Filtrar Tipo de Solicitação com Especificações</td>

					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
				<tr>
					<td height="5" colspan="3"></td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td width="80%" colspan="2">Para filtrar um tipo de solicitação,
					informe os dados abaixo:</td>
					<td width="20%" align="right"><html:checkbox property="atualizar"
						value="1" /><strong>Atualizar</strong></td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td width="40%"><strong>Descrição do Tipo de Solicitação:</strong></td>
					<td colspan="2"><html:text property="descricao" size="40"
						maxlength="50" tabindex="1" /></td>
				</tr>
				<tr>
					<td width="40%"><strong>Grupo de Solicitação do Tipo:</strong></td>
					<td colspan="2"><html:select property="idgrupoTipoSolicitacao"
						tabindex="2">
						<html:option value="">&nbsp;</html:option>
						<html:options collection="colecaoSolicitacaoTipoGrupo"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>
				<tr>
					<td width="40%"><strong>Falta D'água:</strong></td>
					<td colspan="2"><html:radio property="indicadorFaltaAgua" value="1"
						tabindex="3" /> <strong>Sim</strong>&nbsp; <html:radio
						property="indicadorFaltaAgua" value="2" tabindex="4" /> <strong>N&atilde;o</strong>&nbsp;
					<html:radio property="indicadorFaltaAgua" value="3" tabindex="4" />
					<strong>Todos</strong>
				</tr>
				<tr>
					<td width="40%"><strong>Tarifa Social:</strong></td>
					<td colspan="2"><html:radio property="indicadorTarifaSocial" value="1"
						tabindex="3" /> <strong>Sim</strong>&nbsp; <html:radio
						property="indicadorTarifaSocial" value="2" tabindex="4" /> <strong>N&atilde;o</strong>&nbsp;
					<html:radio property="indicadorTarifaSocial" value="3" tabindex="4" />
					<strong>Todos</strong>
				</tr>

				<tr>
					<td width="40%"><strong>Serviço Tipo:</strong></td>
					<td colspan="2"><html:text property="idServicoTipo" size="5" maxlength="6" onkeyup="javascript:verificaNumeroInteiro(this);validaEnterComMensagem(event, 'exibirFiltrarTipoSolicitacaoEspecificacaoAction.do?objetoConsulta=1', 'idServicoTipo', 'Serviço Tipo');" />

					<a href="javascript:abrirPopup('exibirPesquisarTipoServicoAction.do',500,800);">
					<img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" title="Pesquisar Servico Tipo" />
					</a>
					<logic:present name="servicoTipoNaoEncontrado" scope="request">
						<html:text property="descricaoServicoTipo" size="35" maxlength="45" readonly="true" style="background-color:#EFEFEF; border:0; color: red" />
					</logic:present>
					<logic:notPresent name="servicoTipoNaoEncontrado" scope="request">
						<html:text property="descricaoServicoTipo" size="35" maxlength="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
					</logic:notPresent>
					<a href="javascript:limparServicoTipo();document.forms[0].idServicoTipo.focus();">
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
					</a></td>
				</tr>

				<tr>
					<td width="40%"><strong>Indicador de Uso:</strong></td>
					<td colspan="2"><html:radio property="indicadorUso"
						value="<%=ConstantesSistema.INDICADOR_USO_ATIVO.toString()%>"
						tabindex="3" /> <strong>Ativo</strong>&nbsp; <html:radio
						property="indicadorUso"
						value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>"
						tabindex="4" /> <strong>Inativo</strong>&nbsp; <html:radio
						property="indicadorUso" value="3" tabindex="4" /> <strong>Todos</strong>
				</tr>
				
				<tr>
					<td colspan="3">&nbsp;</td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td valign="top"><input name="button" type="button"
						class="bottonRightCol" value="Limpar"
						onclick="window.location.href='/gsan/exibirFiltrarTipoSolicitacaoEspecificacaoAction.do?menu=sim'">&nbsp;</td>
					<td valign="top">
					<div align="right"><input name="button" type="button"
						class="bottonRightCol" value="Filtrar"
						onclick="validarForm(document.forms[0]);" tabindex="8"></div>
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
