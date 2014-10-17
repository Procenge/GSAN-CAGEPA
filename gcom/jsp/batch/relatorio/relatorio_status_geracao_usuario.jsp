<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
<html:html>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
</head>

<body leftmargin="5" topmargin="5" onload="resizePageSemLink(810, 610);">

<table width="770" border="0" cellspacing="4" cellpadding="0">
	<tr>
		<td width="765" valign="top" class="centercoltext">
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
				<td class="parabg">Status Gera��o Relat�rio por Usu�rio</td>
				<td width="11"><img border="0"
					src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
			</tr>
		</table>

		<p>&nbsp;</p>
		<p><strong>${requestScope.nomeProcesso}</strong></p>
		<table width="100%" border="0">
			<tr>
				<td height="31">
				<%int cont = 0;%>
				<%--Esquema de pagina��o--%>
				<pg:pager isOffset="true" index="half-full" maxIndexPages="10"
					export="currentPageNumber=pageNumber;pageOffset"
					maxPageItems="10" items="${sessionScope.totalRegistros}">
					<pg:param name="pg" />
					<pg:param name="q" />
				<table width="100%" bgcolor="#99CCFF">
					<!--header da tabela interna -->
					<tr bordercolor="#FFFFFF" bgcolor="#99CCFF">
						<td width="12%">
						<div align="center"><strong>Data Solicita��o</strong></div>
						</td>
						<td width="40%">
						<div align="center" class="style9"><strong>Observa��o Arquivo</strong></div>
						</td>
						<td width="10%">
						<div align="center" class="style9"><strong>Usu&aacute;rio
						Solicitante</strong></div>
						</td>
						<td width="15%">
						<div align="center"><strong>Divis&atilde;o</strong></div>
						</td>
						<td width="12%">
						<div align="center" class="style9"><strong>Status</strong></div>
						</td>
						<td bgcolor="#99CCFF">
						<p align="center"><strong>Dispon&iacute;vel para </strong></p>
						<p align="center"><strong>Download At&eacute;</strong></p>
						</td>
					</tr>
					<logic:iterate name="colecaoRelatoriosDadosUsuario" id="dados">
					<pg:item>
					<%cont = cont + 1;
					if (cont % 2 == 0) {%>
						<tr bgcolor="#cbe5fe" bordercolor="#FFFFFF">
					<%} else {%>
						<tr bgcolor="#FFFFFF" bordercolor="#FFFFFF">
					<%}%>
							<td align="center">${dados[6]}</td>
							<td height="" ><c:if test="${empty dados[7]}" var="descricao" >  </c:if>
								<c:if test="${!descricao}">
								  <div align="center">${dados[7]} </div> </c:if>
							</td>
							<td height="17" align="center">${dados[0]}</td>
							<td >
							<div align="center">${dados[1]}</div>
							</td>
							<td >
							<c:if test="${dados[2]}">
								<a href="/gsan/exibirRelatorioBatchAction.do?idRelatorioGerado=${dados[8]}">
							</c:if>							
								<div align="center">${dados[5]}<br></div>
							<c:if test="${dados[2]}">
								</a>
							</c:if>							
							</td>
							<td>
							<div align="center">${dados[3]}</div>
							</td>
						</tr>
					</pg:item>
					</logic:iterate>
				</table>
				<table width="100%" border="0">
					<tr>
						<td>
						<div align="center"><strong><%@ include
							file="/jsp/util/indice_pager_novo.jsp"%></strong></div>
						</td>
					</tr>
				</table>
				</pg:pager>
				</td>
			</tr>
		</table>
		<p>&nbsp;</p>
		</td>
	</tr>
</table>
</body>
</html:html>
