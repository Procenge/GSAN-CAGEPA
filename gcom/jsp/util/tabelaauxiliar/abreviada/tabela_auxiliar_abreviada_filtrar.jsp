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

<script language="JavaScript">
function limpar(){
	var form = document.forms[0];
	form.id.value = '';
	form.descricao.value = '';
	form.descricaoAbreviada.value = '';
}

function validaCampoCodigo(){
	var form = document.forms[0];
	var titulo = form.titulo.value;

	if(form.id.value != ""){
		if (!validaCampoInteiroPositivo(form.id.value)){
			alert('O campo C�digo aceita apenas n�meros inteiros e positivos.');
			form.id.value = "";
			return false;
		}
	}
}
</script>


<body leftmargin="5" topmargin="5">
<html:form action="/filtrarTabelaAuxiliarAbreviadaAction" method="post" onsubmit="return validaCampoCodigo();">

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
					<td class="parabg">Filtro de <bean:write name="titulo"
						scope="session" /></td>

					<td width="11"><img
						src="<bean:message key="caminho.imagens"/>parahead_right.gif"
						border="0" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td width="80%">Preencha os campos para pesquisar um(a) <%=((String) session.getAttribute("titulo"))
									.toLowerCase()%>:</td>
					<td width="20%" align="right"><input type="checkbox" name="atualizar" 
						value="1" checked/><strong>Atualizar</strong></td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td><strong>C&oacute;digo:</strong></td>
					<td><input type="hidden" name="titulo" value="<%=((String) session.getAttribute("titulo"))
									.toLowerCase()%>" >
					<html:text property="id" maxlength="6" size="7" /><font size="1"></font> <br>
					<font color="red"><html:errors property="id" /></font></td>
				</tr>
				<tr>
					<td width="19%"><strong><bean:write name="descricao"
						scope="session" />:</strong></td>
					<td width="81%"><input type="text" name="descricao"
						maxlength="<bean:write name="tamMaxCampoDescricao" scope="session"/>"
						size="<bean:write name="tamMaxCampoDescricao" scope="session"/>" />
					<br>
					<font color="red"><html:errors property="descricao" /></font></td>
				</tr>
				<tr>
					<td width="19%"><strong><bean:write name="descricaoAbreviada"
						scope="session" />:</strong></td>
					<td width="81%"><input type="text" name="descricaoAbreviada"
						maxlength="<bean:write name="tamMaxCampoDescricaoAbreviada" scope="session"/>"
						size="<bean:write name="tamMaxCampoDescricaoAbreviada" scope="session"/>" />
					<br>
					<font color="red"><html:errors property="descricaoAbreviada" /></font>
					</td>
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
						value="Filtrar"/></td>

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
</body>
</html:form>
</html:html>
