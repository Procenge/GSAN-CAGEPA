<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

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

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false" formName="MeioSolicitacaoActionForm" />

<script language="JavaScript">
  
	function validaForm() {	
		var form = document.forms[0];
		if (validateMeioSolicitacaoActionForm(form)) {		
			submeterFormPadrao(form);
		}	
	}	
 
	function limparForm(){
		var form = document.forms[0];
		
		form.descricao.value = '';
		form.descricaoAbreviada.value = '';
		form.indicadorLiberacaoDocIdent[0].checked = true;
		form.indicadorUso[0].checked = true;
	}
	
	function reload() {
		var form = document.forms[0];
		form.action = "/gsan/exibirAtualizarMeioSolicitacaoAction.do";
		form.submit();
	}  
	

</script>

</head>

<body leftmargin="5" topmargin="5";">

<html:form action="/atualizarMeioSolicitacaoAction"
	name="MeioSolicitacaoActionForm"
	type="gcom.gui.atendimentopublico.registroatendimento.MeioSolicitacaoActionForm"
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

			<!-- centercoltext -->

			<td width="625" valign="top" class="centercoltext">

			<table>
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">Atualizar Meio Solicitação</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td height="10" colspan="3">Para atualizar um Meio Solicitação, informe
					os dados abaixo:</td>
				</tr>

				<!-- Descricao -->

				<tr>
					<td><strong>Descri&ccedil;&atilde;o:<font color="#FF0000">*</font></strong></td>
					<td colspan="2"><span class="style2"> <html:text
						property="descricao" size="50" maxlength="30"/> </span></td>
				</tr>

				<!-- Descricao Abreviada -->


					<tr>
					<td><strong>Descri&ccedil;&atilde;o Abreviada:<font color="#FF0000">*</font></strong></td>
					<td colspan="2"><span class="style2"> <html:text
						property="descricaoAbreviada" size="8" maxlength="5" /> </span></td>
				</tr>

				<!-- Indicador de Uso -->

				<tr>
			        <td height="30"><strong>Indicador de uso:</strong></td>
			        <td>
						<html:radio property="indicadorUso" value="1"/><strong>Ativo
						<html:radio property="indicadorUso" value="2"/>Inativo</strong>
					</td>
   			   </tr>
   			   
   			   <!-- Indicador de Atividade Única -->
   			   
   			   <tr>
					<td><strong>Indicador de Liberação do Documento de Identidade:<font color="#FF0000">*</font></strong></td>
					<td><strong>
					<html:radio property="indicadorLiberacaoDocIdent" value="1"/> Sim
					<html:radio property="indicadorLiberacaoDocIdent" value="2"/> N&atilde;o
					</strong></td>
				</tr>
				
   				<tr>
					<td><strong> <font color="#FF0000"></font></strong></td>
					<td align="right">
					<div align="left"><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios</div>
					</td>
				</tr>
				<tr>
					<td colspan="2"><font color="#FF0000"> <logic:present
						name="filtrar" scope="request">
						<input type="button" name="ButtonReset" class="bottonRightCol"
							value="Voltar"
							onClick="javascript:window.location.href='/gsan/exibirFiltrarMeioSolicitacaoAction.do'">
					</logic:present><logic:notPresent name="filtrar" scope="request">
						<input type="button" name="ButtonReset" class="bottonRightCol"
							value="Voltar" onClick="javascript:history.back();">
					</logic:notPresent><input type="button" name="ButtonReset"
						class="bottonRightCol" value="Desfazer"
						onClick="(document.forms[0]).reset()"> <input type="button"
						name="ButtonCancelar" class="bottonRightCol" value="Cancelar"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
					</font></td>
					<td align="right">
					  <%-- <gcom:controleAcessoBotao name="Button" value="Atualizar" onclick="javascript:validaForm(document.forms[0]);" url="atualizarAtividadeAction.do"/> --%>
					  <input type="button" name="Button" class="bottonRightCol" value="Atualizar" onClick="javascript:validaForm(document.forms[0]);" />
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>

</html:form>
</html:html>
