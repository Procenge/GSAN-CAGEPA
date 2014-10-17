<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

</head>

<body leftmargin="5" topmargin="5">

<table width="437" border="0" cellspacing="5" cellpadding="0">
	<tr>
		<td width="427" height="346" valign="top" class="centercoltext">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0">

			<tr>
				<td width="11"><img
					src="<bean:message key="caminho.imagens"/>parahead_left.gif"
					border="0" /></td>
				<td class="parabg">Processo Iniciado: Unidades de Processamento</td>

				<td width="11"><img
					src="<bean:message key="caminho.imagens"/>parahead_right.gif"
					border="0" /></td>
			</tr>
		</table>


		<p>&nbsp;</p>
		<p><strong>${requestScope.descricao}</strong></p>

		<p>&nbsp;</p>
		<table width="100%" border="0">
			<tr>
				<td width="37%" height="112">
				<div align="center">
				<table width="99%" bgcolor="#99CCFF">
					<!--header da tabela interna -->
					<tr bordercolor="#FFFFFF" bgcolor="#99CCFF">
						<td width="53%">
						<div align="center"><strong>Unidade:
						${requestScope.unidadeProcessamento}</strong></div>
						</td>
					</tr>

					<tr bordercolor="#FFFFFF" bgcolor="#FFFFFF">
						<td height="17" bgcolor="#FFFFFF"><strong>PROCESSADAS:
						${requestScope.tamanhoColecaoFinalizada}</strong> <input class="bottonRightCol" type="button" value="Atualizar" onclick="javascript:window.location.href=window.location.href"/></td>
					</tr>
					<%int cont = 0;%>
					<logic:iterate name="colecaoFinalizada" id="unidadeIniciada">
						<%cont = cont + 1;
					if (cont % 2 == 0) {%>
						<tr bgcolor="#cbe5fe">
							<%} else {%>
						<tr bgcolor="#FFFFFF">
							<%}%>
							<td height="17" bgcolor="#cbe5fe">${unidadeIniciada.codigoRealUnidadeProcessamento}</td>
						</tr>
					</logic:iterate>
					<tr bordercolor="#FFFFFF" bgcolor="#FFFFFF">
						<td height="17" bgcolor="#FFFFFF"><strong>A PROCESSAR:
						${requestScope.tamanhoColecaoInacabada}</strong> <input class="bottonRightCol" type="button" value="Atualizar" onclick="javascript:window.location.href=window.location.href"/></td>
					</tr>
					<%cont = 0;%>
					<logic:iterate name="colecaoInacabada" id="idUnidadeIniciada">
						<%cont = cont + 1;
					if (cont % 2 == 0) {%>
						<tr bgcolor="#cbe5fe">
							<%} else {%>
						<tr bgcolor="#FFFFFF">
							<%}%>

							<td height="17" bgcolor="#cbe5fe">${idUnidadeIniciada}</td>
						</tr>
					</logic:iterate>

				</table>
				</div>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>


<p>&nbsp;</p>


</body>
</html:html>
