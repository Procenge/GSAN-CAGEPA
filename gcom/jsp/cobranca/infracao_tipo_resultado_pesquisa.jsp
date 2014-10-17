<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>

<%@ page import="java.util.Collection,gcom.util.ConstantesSistema"%>
<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="InfracaoTipoActionForm"
	dynamicJavascript="false" />
</head>

<body leftmargin="5" topmargin="5" onload="resizePageSemLink(790, 410);">
<table width="750" border="0" cellspacing="5" cellpadding="0">
	<tr>
		<td width="750" valign="top" class="centercoltext">
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
				<td class="parabg">Pesquisa de Infra��o Tipo</td>

				<td width="11"><img border="0"
					src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
			</tr>
			<tr>
				<td height="5" colspan="3"></td>
			</tr>
		</table>

		<p>&nbsp;</p>
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td></td>
			</tr>
			<tr>
				<!--<td colspan="4" bgcolor="#3399FF"> -->
				<td colspan="6"></td>
			</tr>

			<tr bordercolor="#000000">
				<td width="12%" align="center" bgcolor="#90c7fc"><strong>C&oacute;digo</strong></td>
					<td width="34%" align="center" bgcolor="#90c7fc"><strong>Descri��o</strong></td>
			</tr>
			<tr>
				<td colspan="5">
					<table width="100%" bgcolor="#99CCFF">
					<%--Esquema de pagina��o--%>
						<pg:pager isOffset="true" index="half-full" maxIndexPages="10"
								export="currentPageNumber=pageNumber;pageOffset"
								maxPageItems="10" items="${sessionScope.totalRegistros}">
								<pg:param name="pg" />
								<pg:param name="q" />
									<logic:present name="colecaoInfracaoTipo">
										<%int cont = 1;%>
										<logic:iterate name="colecaoInfracaoTipo" id="unidadeEmpresa">
											<pg:item>
												<%cont = cont + 1;
												  if (cont % 2 == 0) {%>
												<tr bgcolor="#FFFFFF">
													<%} else { %>
												<tr bgcolor="#cbe5fe">
													<%}%>
													<td width="15%">
														<div align="center"><bean:write name="unidadeEmpresa" property="id" /></div>
													</td>
													<td width="40%">
														<logic:notEmpty name="caminhoRetornoTelaPesquisaTipoInfracao" scope="session">
															<a href="javascript:enviarDadosParametros('<bean:write name="caminhoRetornoTelaPesquisaTipoInfracao"/>','<bean:write name="unidadeEmpresa" property="id"/>', '<bean:write name="unidadeEmpresa" property="descricao"/>', 'tipoInfracao');">
																<bean:write name="unidadeEmpresa" property="descricao" /> &nbsp;</a>
														</logic:notEmpty> 
														
														<logic:empty name="caminhoRetornoTelaPesquisaTipoInfracao" scope="session">
															<a href="javascript:enviarDados('<bean:write name="unidadeEmpresa" property="id"/>', '<bean:write name="unidadeEmpresa" property="descricao"/>', 'tipoInfracao');">
																<bean:write name="unidadeEmpresa" property="descricao" /> &nbsp;</a>
														</logic:empty>
																
													</td>
												</tr>
											</pg:item>
										</logic:iterate>
									</logic:present>
								</table>
							</td>
						</tr>

					</table>
					<table width="100%" border="0">
						<tr>
							<td><strong><div align="center"><strong><%@ include
									file="/jsp/util/indice_pager_novo.jsp"%></strong></div>
							</td>
						</tr>
						<tr>
							<td height="24">
								<input type="button" class="bottonRightCol"
								value="Voltar Pesquisa"
								onclick="window.location.href='/gsan/exibirPesquisarInfracaoTipoAction.do'"/></td>
						</tr>
					</table>
				</pg:pager>
			</td>
		</tr>
	</table>

</body>

</html:html>
