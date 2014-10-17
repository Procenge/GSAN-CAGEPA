<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>

<%@ page import="gcom.util.ConstantesSistema"%>

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="PesquisarUnidadeNegocioActionForm"
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
				<td class="parabg">Pesquisa de Unidades de Negócio</td>

				<td width="11"><img border="0"
					src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
			</tr>
			<tr>
				<td height="5"></td>
			</tr>
		</table>


		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td></td>
			</tr>
			<tr>
				<!--<td colspan="4" bgcolor="#3399FF"> -->
				<td></td>
			</tr>

			<tr bordercolor="#000000">
				<td width="30%" align="center" bgcolor="#90c7fc"><strong>ID</strong></td>
				<td width="30%" align="center" bgcolor="#90c7fc"><strong>Descri&ccedil;&atilde;o</strong></td>
			</tr>
			<tr>
				<td colspan="5">
				<table width="100%" bgcolor="#99CCFF">

					<%--Esquema de paginação--%>
					<pg:pager isOffset="true" index="half-full" maxIndexPages="10"
							export="currentPageNumber=pageNumber;pageOffset"
							maxPageItems="10" items="${sessionScope.totalRegistros}">
							<pg:param name="pg" />
							<pg:param name="q" />
						<logic:present name="colecaoUnidadeNegocio">
							<%int cont = 1;%>
							<logic:iterate name="colecaoUnidadeNegocio"
								id="unidadeNegocio">
								<pg:item>
							<%	cont = cont + 1;
									if (cont % 2 == 0) {	%>
									<tr bgcolor="#FFFFFF">
								<%	} else {	%>
									<tr bgcolor="#cbe5fe">
								<%	}	%>
									
									<logic:notEmpty name="caminhoRetornoTelaPesquisaUnidadeNegocio">
											<td width="30%" align="center">
													<bean:write name="unidadeNegocio" property="id" />
											</td>
									</logic:notEmpty>
		
									<logic:empty name="caminhoRetornoTelaPesquisaUnidadeNegocio">
											<td width="30%" align="center">
													<bean:write name="unidadeNegocio" property="id" />
											</td>
									</logic:empty>

									<logic:notEmpty name="caminhoRetornoTelaPesquisaUnidadeNegocio">
											<td width="30%">
												<a href="javascript:enviarDadosParametros('<bean:write name="caminhoRetornoTelaPesquisaUnidadeNegocio"/>','<bean:write name="unidadeNegocio" property="id"/>', '<bean:write name="unidadeNegocio" property="nome"/>', 'unidadeNegocio');">
													<bean:write name="unidadeNegocio" property="nome" />
												</a>
											</td>
									</logic:notEmpty>
		
									<logic:empty name="caminhoRetornoTelaPesquisaUnidadeNegocio">
											<td width="30%">
												<a href="javascript:enviarDados('<bean:write name="unidadeNegocio" property="id"/>', '<bean:write name="unidadeNegocio" property="nome"/>', 'unidadeNegocio');">
													<bean:write name="unidadeNegocio" property="nome" />
												</a>
											</td>
									</logic:empty>
									
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
					<td colspan="2">
					<div align="center"><strong><%@ include
						file="/jsp/util/indice_pager_novo.jsp"%></strong></div>
					</td>
				</tr>
				</pg:pager>
			<tr>
				<td height="24"><input type="button" class="bottonRightCol"
					value="Voltar Pesquisa"
					onclick="window.location.href='/gsan/exibirPesquisarUnidadeNegocioAction.do'"/></td>
			</tr>
		</table>
		</td>
	</tr>

</table>

</body>

</html:html>
