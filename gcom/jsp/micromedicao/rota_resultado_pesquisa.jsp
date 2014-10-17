<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<html:html>

<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
</head>

<body leftmargin="5" topmargin="5" onload="resizePageSemLink(660, 430);">
<table width="630" border="0" cellspacing="5" cellpadding="0">
	<tr>
		<td width="630" valign="top" class="centercoltext">
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
				<td class="parabg">Pesquisa de Rota</td>
				<td width="11"><img border="0"
					src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
			</tr>
		</table>
		<p>&nbsp;</p>
		<table width="100%" border="0" bgcolor="#90c7fc">
			<tr align="left">
				<td width="13%" align="center"><strong>C�d. Rota</strong></td>
				<td width="30%" align="center"><strong>Localidade</strong></td>
				<td width="17%" align="center"><strong>C&oacute;digo do Setor</strong></td>
				<td width="20%" align="center"><strong>Grupo Faturamento</strong></td>
				<td width="20%" align="center"><strong>Grupo Cobran�a</strong></td>
			</tr>
			<%--Esquema de pagina��o--%>
			<pg:pager isOffset="true" index="half-full" maxIndexPages="10"
				export="currentPageNumber=pageNumber;pageOffset" maxPageItems="10"
				items="${sessionScope.totalRegistros}">
				<pg:param name="q" />

				<%int cont = 0;
					String destino = (String) session.getAttribute("destino");
				
				%>


				<logic:iterate name="rotas" id="rota">
					<pg:item>
						<%cont = cont + 1;
			if (cont % 2 == 0) {%>
						<tr bgcolor="#cbe5fe">
							<%} else {

			%>
						<tr bgcolor="#FFFFFF">
							<%}%>

							<td width="13%" align="center">
							<div align="center">
								<!--  
								<a href="javascript:enviarDados('<bean:write name="rota" property="id"/>', '<bean:write name="rota" property="descricao"/>','rota');">
									<bean:write name="rota" property="codigo"/>
								</a>-->
							<logic:present name="destinoCampo">
								<a href="javascript:enviarDadosQuatroParametros('<bean:write name="rota" property="id"/>', '<bean:write name="rota" property="descricao"/>', '${sessionScope.destinoCampo}','rota');">
									  <bean:write name="rota" property="codigo"/> 
								</a>
							</logic:present>				
							<logic:notPresent name="destinoCampo">
								<a href="javascript:enviarDadosQuatroParametros('<bean:write name="rota" property="id"/>', '<bean:write name="rota" property="descricao"/>', '<bean:write name="rota" property="codigo"/>','rota');">
									  <bean:write name="rota" property="codigo"/> 
								</a>
							</logic:notPresent>				

							</div>
							</td>
							<td width="30%" align="center">
								<logic:present
								name="rota"
								property="setorComercial">
								<bean:define name="rota"
									property="setorComercial" id="setorComercial" />
									<logic:present
									name="setorComercial"
									property="localidade">
										<bean:define name="setorComercial"
											property="localidade" id="localidade" />
										<bean:write name="localidade"
											property="descricao" />
									</logic:present>							
								</logic:present>							
							</td>
							<td width="17%" align="center">
								<logic:present
								name="rota"
								property="setorComercial">
								<bean:define name="rota"
									property="setorComercial" id="setorComercial" />
									
								<bean:write name="setorComercial"
									property="codigo" />
									
								</logic:present>							
							
							</td>
							<td width="20%" align="center">
								<logic:present
								name="rota"
								property="faturamentoGrupo">
								<bean:define name="rota"
									property="faturamentoGrupo" id="faturamentoGrupo" />
									
								<bean:write name="faturamentoGrupo"
									property="descricaoAbreviada" />
									
								</logic:present>								
							</td>
							<td width="20%" align="center">
								<logic:present
								name="rota"
								property="cobrancaGrupo">
								<bean:define name="rota"
									property="cobrancaGrupo" id="cobrancaGrupo" />
									
								<bean:write name="cobrancaGrupo"
									property="descricaoAbreviada" />
									
								</logic:present>								
							</td>
					</pg:item>
				</logic:iterate>
		</table>

		<table width="100%" border="0">
			<tr>
				<td>
				<div align="center"><strong><%@ include
					file="/jsp/util/indice_pager_novo.jsp"%></strong></div>
				</td>
				</pg:pager>
				<%-- Fim do esquema de pagina��o --%>
			</tr>
		</table>
		<table width="100%" border="0">
			<tr>
				<td height="24"><input type="button" class="bottonRightCol"
					value="Fechar" onclick="javascript:window.close();" /></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html:html>