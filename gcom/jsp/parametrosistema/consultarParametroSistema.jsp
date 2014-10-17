<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>
<%@ page import="gcom.util.ConstantesSistema"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
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
<script language="JavaScript">
	function validaForm() {
		var form = document.forms[0];
		form.submit();
	}

	function limpar() {
		var form = document.forms[0];
		form.id.value = "";
		form.descricao.value = "";
	}
</script>
</head>

<body leftmargin="5" topmargin="5" onload="limpar();">
	<html:form
		action="/consultarParametroSistema.do?acao=filtrarParametroSistema"
		name="ParametroSistemaActionForm"
		type="gcom.gui.seguranca.sistema.ParametroSistemaActionForm"
		method="post">

		<table width="100%" border="0">
			<tr>
				<td>Para filtrar os Parâmetros do sistema,
						informe os dados abaixo:</td>
			</tr>
		</table>
		<br>
		<table width="100%" border="0">

			<tr>

				<td><strong>Código:</strong></td>
				<td><html:text property="codigo" size="42" tabindex="2"
						maxlength="255" /></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><html:radio property="tipoPesquisaCodigo"
						value="<%=ConstantesSistema.TIPO_PESQUISA_INICIAL.toString()%>" />
					Iniciando pelo texto <html:radio property="tipoPesquisaCodigo"
						tabindex="5"
						value="<%=ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString()%>" />
					Contendo o texto</td>
			</tr>

			<tr>

				<td><br>
				<strong>Valor:</strong></td>
				<td><br>
				<html:text property="valor" size="42" tabindex="3" maxlength="255" /></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><html:radio property="tipoPesquisaValor"
						value="<%=ConstantesSistema.TIPO_PESQUISA_INICIAL.toString()%>" />
					Iniciando pelo texto <html:radio property="tipoPesquisaValor"
						tabindex="5"
						value="<%=ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString()%>" />
					Contendo o texto</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><br>
				<strong>Indicador de Uso:</strong></td>
				<td align="right">
					<div align="left">
						<html:radio property="uso" tabindex="4"
							value="<%=ConstantesSistema.INDICADOR_USO_ATIVO.toString()%>" />
						<strong>Ativo</strong>
						<html:radio property="uso" tabindex="5"
							value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>" />
						<strong>Inativo</strong>
						<html:radio property="uso" tabindex="5" value="" />
						<strong>Todos</strong>
					</div>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><input name="Submit22" class="bottonRightCol"
					value="Limpar" type="button"
					onclick="window.location.href='/gsan/consultarParametroSistema.do?acao=consultarParametroSistema';">
					<input type="button" name="ButtonCancelar" class="bottonRightCol"
					value="Cancelar"
					onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">

				</td>
				<td align="right"></td>
				<td align="right"><gcom:controleAcessoBotao name="Button"
						value="Filtrar" onclick="javascript:validaForm();"
						url="consultarParametroSistema.do?acao=filtrarParametroSistema" /></td>
			</tr>
		</table>

	</html:form>
</body>
</html>