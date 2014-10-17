<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page isErrorPage="true" import="java.io.*"%>

<html:html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>

EstilosCompesa.css"
	type="text/css">

<script type="text/javascript">
<!--
function toggleBox(szDivID, iState) // 1 visible, 0 hidden
{
    if(document.layers)	   //NN4+
    {
       document.layers[szDivID].visibility = iState ? "show" : "hide";
    }
    else if(document.getElementById)	  //gecko(NN6) + IE 5+
    {
        var obj = document.getElementById(szDivID);
        obj.style.visibility = iState ? "visible" : "hidden";
    }
    else if(document.all)	// IE 4
    {
        document.all[szDivID].style.visibility = iState ? "visible" : "hidden";
    }
}
// -->
</script>

</head>

<body leftmargin="5" topmargin="5">
<table width="100%" border="0" cellspacing="5" cellpadding="0">
	<tr>
		<td width="100%" valign="top" class="centercoltext">
		<table height="100%">
			<tr>
				<td></td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="17"><img border="0"
					src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>

				<td width="100%" class="parabg">Erro</td>

				<td width="3%" class="parabg"><html:link
					href="javascript:window.close();">
					<img border="0"
						src="<bean:message key="caminho.imagens"/>close.gif" alt="Fechar" />
				</html:link></td>
				<td width="4%"><img border="0"
					src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
			</tr>
		</table>
		<table width="100%" border="0">
			<tr>
				<td width="6%" align="left"><img
					src="<bean:message key="caminho.imagens"/>erro.gif" /></td>

				<td><span style="font-size:12px;font-weight: bold;">Erro de Sistema
				<br>
				</span></td>
			</tr>
			<tr>
				<td colspan="2"><a href="javascript:history.back()"><strong>Voltar</strong></a></td>
			</tr>
			<tr>
				<td colspan="2" align="right"><font
					onclick="toggleBox('demodiv',0);"
					onmouseover="toggleBox('demodiv',1);"><strong>Visualizar Log
				Servidor</strong></font>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<div ID="demodiv">
<p>&nbsp;</p>
<table
	style="position: absolute;left: 10px; 133px;height: 70px;border: 1px solid #000000;background-color: #cbe5fe;z-index: 2;overflow: auto;">
	<tr>
		<td><pre><strong> Mensagem : <bean:write
			name="javax.servlet.error.message" scope="request" /><br> Código Erro : <bean:write name="javax.servlet.error.status_code"
			scope="request" /> </strong></pre></td>
	</tr>
</table>
</div>

</body>

</html:html>

