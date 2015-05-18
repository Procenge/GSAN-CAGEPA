<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/menus-taglib.tld" prefix="menu"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

</head>


<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>

<html:javascript staticJavascript="false"  formName="AtividadeActionForm" dynamicJavascript="false" />

<script language="JavaScript">
function limpar(){
	var form = document.forms[0];
	form.descricao.value = '';
	form.descricaoAbreviada.value = '';
	form.valorHora.value = '';
	form.indicadorAtividadeUnica[1].checked = true;
	form.indicadorUso[0].checked = true;
}
</script>


<body leftmargin="5" topmargin="5">
<html:form action="/filtrarAtividadeAction" method="post"
	name="AtividadeActionForm" 
	type="gcom.gui.atendimentopublico.ordemservico.AtividadeActionForm">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="4" cellpadding="0">
		<tr>
			<td width="149" valign="top" class="leftcoltext">
			<div align="center"><%@ include
				file="/jsp/util/informacoes_usuario.jsp"%></div>
			</td>
			<td width="625" valign="top" class="centercoltext">
			<table>
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img
						src="<bean:message key="caminho.imagens"/>parahead_left.gif"
						border="0" /></td>
					<td class="parabg">Filtro de Atividade</td>

					<td width="11"><img
						src="<bean:message key="caminho.imagens"/>parahead_right.gif"
						border="0" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td width="80%">Preencha os campos para pesquisar uma atividade:</td>
					<td width="20%" align="right"><input type="checkbox" value="1" checked="checked" name="atualizar"/>
					<strong>Atualizar</strong></td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td width="19%"><strong>Descri&ccedil;&atilde;o:</strong></td>
					<td width="81%">
						<html:text property="descricao" size="50" maxlength="30"/>
					<br>
					<font color="red"><html:errors property="descricao" /></font></td>
				</tr>
				<tr>
					<td width="19%"><strong>Descri&ccedil;&atilde;o Abreviada:</strong></td>
					<td width="81%">
						<html:text property="descricaoAbreviada" maxlength="5" size="8" />
					<br>
					<font color="red"><html:errors property="descricaoAbreviada" /></font>
					</td>
				</tr>
				<logic:equal name="permiteCobrarHora" value="1" scope="session">
				<tr>
					<td width="19%"><strong>Valor hora:</strong></td>
					<td width="81%">
						<html:text property="valorHora" maxlength="5" size="8" />
					<br>
					<font color="red"><html:errors property="valorHora" /></font>
					</td>
				</tr>
				</logic:equal>
				
				<tr>
					<td><strong>Indicador Uso:<font color="#FF0000">*</font></strong></td>
					<td><strong> 
					<html:radio property="indicadorUso" value="1"/> Ativo
					<html:radio property="indicadorUso" value="2"/> Inativo
					<html:radio property="indicadorUso" value="3"/> Todos
					</strong></td>
				</tr>
				
				<tr>
					<td><strong>Indicador de Atividade Única:<font color="#FF0000">*</font></strong></td>
					<td><strong> <html:radio property="indicadorAtividadeUnica" value="1"/> Sim
					<html:radio property="indicadorAtividadeUnica" value="2"/> N&atilde;o
					</strong></td>
				</tr>
				
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td height="0"><input name="Button" type="button"
						class="bottonRightCol" value="Limpar" align="left"
						onclick="javascript:limpar();">&nbsp;<input type="button"
						name="ButtonCancelar" class="bottonRightCol" value="Cancelar"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"></td>
					<td align="right"><html:submit styleClass="bottonRightCol"
						value="Filtrar" /></td>

				</tr>
				
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr><tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr><tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr><tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr><tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr><tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr><tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr><tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr><tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr><tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				
			</table>
			<p>&nbsp;</p>
	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</body>
</html:html>