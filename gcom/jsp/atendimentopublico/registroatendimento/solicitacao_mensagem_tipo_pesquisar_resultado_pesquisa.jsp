<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<html:html>

<%@ page import="java.util.Collection,gcom.util.ConstantesSistema"%>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>

</head>

<body leftmargin="5" topmargin="5" onload="resizePageSemLink(670, 450);">

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
				<td class="parabg">Pesquisa de Mensagem Tipo de Solicitação
				Especificação</td>
				<td width="11"><img border="0"
					src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
			</tr>
		</table>
		<p>&nbsp;</p>
		<table width="100%" border="0" bgcolor="#90c7fc">
			<tr align="left">
				<td width="10%" align="center"><strong>Descrição</strong></td>
			</tr>
			<%--Esquema de paginação--%>
			<pg:pager isOffset="true" index="half-full" maxIndexPages="10"
				export="currentPageNumber=pageNumber;pageOffset" maxPageItems="10"
				items="${sessionScope.totalRegistros}">
				<pg:param name="q" />

				<% int cont = 0; %>
				<logic:iterate name="colecaoSolicitacaoTipoEspecificacaoMensagem" id="solicitacaoTipoEspecificacaoMensagem">
					<pg:item>
						<%cont = cont + 1;
						if (cont % 2 == 0) {%>
							<tr bgcolor="#cbe5fe">								
						<%} else {	%>
							<tr bgcolor="#FFFFFF">
						<%}%>
								<logic:notEmpty name="caminhoRetornoTelaPesquisaMensagemTipoSolicitacaoEspecificacao">
									<td width="100%">
										<a href="javascript:enviarDadosParametros('<bean:write name="caminhoRetornoTelaPesquisaMensagemTipoSolicitacaoEspecificacao"/>','<bean:write name="solicitacaoTipoEspecificacaoMensagem" property="id"/>', '<bean:write name="solicitacaoTipoEspecificacaoMensagem" property="descricao"/>', 'solicitacaoTipoEspecificacaoMensagem');">
											<bean:write name="solicitacaoTipoEspecificacaoMensagem" property="descricao" />
										</a>
									</td>
								</logic:notEmpty>
	
								<logic:empty name="caminhoRetornoTelaPesquisaMensagemTipoSolicitacaoEspecificacao">
									<td width="100%">
										<a href="javascript:enviarDados('<bean:write name="solicitacaoTipoEspecificacaoMensagem" property="id"/>', '<bean:write name="solicitacaoTipoEspecificacaoMensagem" property="descricao"/>', 'solicitacaoTipoEspecificacaoMensagem');">
											<bean:write name="solicitacaoTipoEspecificacaoMensagem" property="descricao" />
										</a>
									</td>
								</logic:empty>
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
				</pg:pager>
				<%-- Fim do esquema de paginação --%>
			</tr>
		</table>
		<table width="100%" border="0">
			<tr>
				<td height="24"><input type="button" class="bottonRightCol"
					value="Voltar Pesquisa"
					onclick="window.location.href='<html:rewrite page="/exibirPesquisarMensagemTipoSolicitacaoEspecificacaoAction.do"/>'" /></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html:html>
