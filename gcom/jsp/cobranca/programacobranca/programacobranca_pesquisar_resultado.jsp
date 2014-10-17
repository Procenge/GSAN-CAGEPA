<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript">

</script>
</head>
<!-- onload="resizePageSemLink(810,595);" -->
<body leftmargin="5" topmargin="5" onload="resizePageSemLink(810,615);">
<html:form action="/pesquisarProgramaCobrancaAction"
	name="PesquisarProgramaCobrancaForm"
	type="gcom.gui.cobranca.programacobranca.PesquisarProgramaCobrancaActionForm"
	method="post">

	<table width="770" border="0" cellspacing="4" cellpadding="0">
		<tr>
			<td width="770" valign="top" class="centercoltext">
			<table height="100%">

				<tr>
					<td></td>
				</tr>
			</table>
			<table align="center" border="0" cellpadding="0" cellspacing="0"
				width="100%">
				<tbody>
					<tr>
						<td width="11"><img border="0"
							src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
						<td class="parabg">Pesquisar Programa de Cobrança</td>

						<td width="11"><img border="0"
							src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>

					</tr>
				</tbody>
			</table>
			<p>&nbsp;</p>
			<table width="100%" bgcolor="#90c7fc">
				<tbody>
					<tr align="left" bgcolor="#90c7fc">
						<td bgcolor="#90c7fc">
						  <div align="center"><strong>Código Programa</strong></div>
						</td>
						<td width="24%" bgcolor="#90c7fc">
						  <div align="center"><strong>Nome do Programa</strong></div>
						</td>
						<td bgcolor="#90c7fc" width="24%">
						  <div align="center"><strong>Descrição</strong></div>
						</td>
						<td bgcolor="#90c7fc" width="10%">
						  <div align="center"><strong>Critério de Cobrança</strong></div>
						</td>
						<td bgcolor="#90c7fc" width="12%">
						  <div align="center"><strong>Data de Criação</strong></div>
						</td>
						<td bgcolor="#90c7fc" width="12%">
						<div align="center"><strong>Data de Início</strong></div>
						</td>
						<td bgcolor="#90c7fc" width="12%">
						<div align="center"><strong>Última Movimentação</strong></div>
						</td>
					</tr>
					<% String cor = "#cbe5fe";%>
					<pg:pager isOffset="true" index="half-full" maxIndexPages="10"
							export="currentPageNumber=pageNumber;pageOffset"
							maxPageItems="10" items="${sessionScope.totalRegistros}">
							<pg:param name="pg" />
							<pg:param name="q" />
						<logic:present name="colecaoProgramaCobranca" scope="session">
							<logic:iterate name="colecaoProgramaCobranca" id="programa">
								<pg:item>
										<%	if (cor.equalsIgnoreCase("#cbe5fe")){	
											cor = "#FFFFFF";%>
											<tr bgcolor="#FFFFFF" height="18">	
										<%} else{	
											cor = "#cbe5fe";%>
											<tr bgcolor="#cbe5fe" height="18">		
										<%}%>
										<td>
										<div align="center">
										<logic:notPresent name="tipoPesquisa">
											<logic:notEmpty name="caminhoRetornoTelaPesquisaProgramaCobranca">
												<a href="javascript:enviarDadosParametros('<bean:write name="caminhoRetornoTelaPesquisaProgramaCobranca"/>', '<bean:write name="programa" property="id"/>', '<bean:write name="programa" property="nome"/>', 'programaCobranca');">
													<bean:write name="programa" property="id" />
												</a>
											</logic:notEmpty>
											<logic:empty name="caminhoRetornoTelaPesquisaProgramaCobranca">
												<a href="javascript:enviarDados('<bean:write name="programa" property="id"/>', '<bean:write name="programa" property="nome"/>', 'programaCobranca');">
												<bean:write name="programa" property="id" /></a>
											</logic:empty>
										</logic:notPresent>
										</div>
										</td>
										<td>
										<div align="center">
											<bean:write name="programa" property="nome" />
										</div>
										</td>
										<td>
										  <div align="center">
											<bean:write name="programa" property="descricao" />
										  </div>
										</td>
										<td>
										  <div align="center">
										  	<bean:write name="programa" property="criterio.id" />
										  </div>
										</td>
										<td>
										  <div align="center">
											<bean:write name="programa" property="dataCriacao" format="dd/MM/yyyy" />
										  </div>
										</td>
										<td>
										  <div align="center">
											<bean:write name="programa" property="dataInicio" format="dd/MM/yyyy" />
										  </div>
										</td>
										<td>
										  <div align="center">
											<bean:write name="programa" property="dataUltimoMovimento" format="dd/MM/yyyy" />
										  </div>
										</td>
									</tr>
								</pg:item>
							</logic:iterate>
						</logic:present>
				</tbody>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td>
					<div align="center"><strong><%@ include
						file="/jsp/util/indice_pager_novo.jsp"%></strong></div>
					</td>
					</pg:pager>
				</tr>
				<tr>
					<td>
					<div align="left"><input type="button" name="butao"
						value="Voltar Pesquisa" class="bottonRightCol"
						onclick="javascript:window.location.href='exibirPesquisarProgramaCobrancaAction.do';"></div>

					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
</body>
</html:html>
