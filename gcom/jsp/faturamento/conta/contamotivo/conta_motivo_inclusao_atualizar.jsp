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

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="AtualizarContaMotivoInclusaoActionForm" />

<script language="JavaScript">
  
	function validaForm() {
	  	var form = document.forms[0];
	  	form.action = "/gsan/atualizarContaMotivoInclusaoAction.do";
		submeterFormPadrao(form);
	}
	 
 
	function limparForm() {
		var form = document.AtualizarContaMotivoInclusaoActionForm;
		form.descricao.value = "";
		
	}
	
	
	function reload() {
		var form = document.AtualizarContaMotivoInclusaoActionForm;
		form.action = "/gsan/exibirAtualizarContaMotivoInclusaoAction.do";
		form.submit();
	}  
	

</script>

</head>

<body leftmargin="5" topmargin="5";">

<html:form action="/atualizarContaMotivoInclusaoAction"
	name="AtualizarContaMotivoInclusaoActionForm"
	type="gcom.gui.faturamento.conta.contamotivo.AtualizarContaMotivoInclusaoActionForm"
	method="post" scope="session">

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
					<td class="parabg">Atualizar Motivo de Inclusao de Conta</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td height="10" colspan="3">Para Atualizar um motivo de inclusão de conta, informe
					os dados abaixo:</td>
				</tr>

				<!-- Descricao -->
				<tr>
					<td><strong>Código:<font color="#FF0000">*</font></strong></td>
					<td colspan="2"><span class="style2"> <html:text
						property="id" size="10" maxlength="50" readonly="true" /> </span></td>
				</tr>
				<tr>
					<td><strong>Descri&ccedil;&atilde;o:<font color="#FF0000">*</font></strong></td>
					<td colspan="2"><span class="style2"> <html:text
						property="descricao" size="50" maxlength="50" /> </span></td>
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
						name="inserir" scope="request">
						<input type="button" name="ButtonReset" class="bottonRightCol"
							value="Voltar"
							onClick="javascript:window.location.href='/gsan/exibirFiltrarContaMotivoInclusaoAction.do?menu=sim'">
					</logic:present><logic:notPresent name="inserir" scope="request">
						<input type="button" name="ButtonReset" class="bottonRightCol"
							value="Voltar" onClick="javascript:history.back();">
					</logic:notPresent><input type="button" name="ButtonReset"
						class="bottonRightCol" value="Desfazer"
						onClick="(document.forms[0]).reset()"> <input type="button"
						name="ButtonCancelar" class="bottonRightCol" value="Cancelar"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
					</font></td>
					<td align="right">
					  <gcom:controleAcessoBotao name="Button" value="Atualizar" onclick="javascript:validaForm(document.forms[0]);" url="atualizarContaMotivoInclusaoAction.do"/>
					  <%-- <input type="button" name="Button" class="bottonRightCol" value="Atualizar" onClick="javascript:validarForm(document.forms[0]);" /> --%>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>

</html:form>
</html:html>
