<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>


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
				<td class="parabg">Log das Transações Efetuadas</td>

				<td width="11"><img
					src="<bean:message key="caminho.imagens"/>parahead_right.gif"
					border="0" /></td>
			</tr>
		</table>


		<p>&nbsp;</p>
		<% if(request.getAttribute("logTransacoes") == null || request.getAttribute("logTransacoes").equals("")) { %>
			<h1>Não há registro de Log.</h1>			
		<% } else { %>
			<html:textarea property="" value="${logTransacoes}" rows="30" readonly="true" cols="100"></html:textarea>						
		<% } %>

		<p>&nbsp;</p>
		
		</td>
	</tr>
</table>


<p>&nbsp;</p>


</body>
</html:html>
