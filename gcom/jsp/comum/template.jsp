<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld"  %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>
<%@ taglib prefix="display" uri="displaytag" %>


<html:html>
<head>
<tiles:insert attribute="topo"/>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>displaytag.css"
	type="text/css">	
	
</head>

<body leftmargin="5" topmargin="5">

	<tiles:insert attribute="cabecalho"/>
	<tiles:insert attribute="menu"/>
	
<table width="770" border="0" cellspacing="4" cellpadding="0">

		<tr>
			<td width="149" valign="top" class="leftcoltext">

			<div align="center">
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<tiles:insert attribute="informacoes_usuario"/>
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
			
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			</div>
			</td>

			<td width="615" valign="top" class="centercoltext">
			<br/>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg"><tiles:getAsString name="titulo"/></td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" />
					</td>
				</tr>
			</table>
			
			<table height="100%">
				<tr>
					<td>
				
                <html:messages id="msg" message="true"><br/>
                 <span class="msg"><font color="blue"><b><c:out value='${msg}' /></b></font></span>
                 </html:messages>
                 <html:messages id="erro" message="false"><br/>
                   <span><font color="red"><b><c:out value='${erro}' /></b></font></span>
                 </html:messages>
                  
					</td>
				</tr>
			</table>

			<tiles:insert attribute="corpo"/>



					</td>
				</tr>

			</table>

			</td>
		</tr>

	</table>
	<tiles:insert attribute="rodape"/>

</body>
</html:html>
  
