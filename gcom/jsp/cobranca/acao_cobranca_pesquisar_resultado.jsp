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
<html:form action="/pesquisarAcaoCobrancaAction"
	name="PesquisarAcaoCobrancaForm"
	type="gcom.gui.cobranca.PesquisarAcaoCobrancaActionForm"
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
						<td class="parabg">Pesquisar Ação de Cobrança</td>

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
						  <div align="center"><strong>Código da Ação</strong></div>
						</td>
						<td width="30%" bgcolor="#90c7fc">
						  <div align="center"><strong>Descrição</strong></div>
						</td>
						<td bgcolor="#90c7fc" width="30%">
						  <div align="center"><strong>Estágio da Cobrança</strong></div>
						</td>
						<td bgcolor="#90c7fc" width="15%">
						  <div align="center"><strong>Tipo de documento a ser gerado</strong></div>
						</td>
						<td bgcolor="#90c7fc" width="15%">
						  <div align="center"><strong>Tipo de Serviço da Ordem de Serviço </strong></div>
						</td>
					</tr>
					<% String cor = "#cbe5fe";%>
					<pg:pager isOffset="true" index="half-full" maxIndexPages="10"
							export="currentPageNumber=pageNumber;pageOffset"
							maxPageItems="10" items="${sessionScope.totalRegistros}">
							<pg:param name="pg" />
							<pg:param name="q" />
						<logic:present name="colecaoAcaoCobranca" scope="session">
							<logic:iterate name="colecaoAcaoCobranca" id="acao">
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
											<logic:notEmpty name="caminhoRetornoTelaPesquisaAcaoCobranca">
												<a href="javascript:enviarDadosParametros('<bean:write name="caminhoRetornoTelaPesquisaAcaoCobranca"/>', '<bean:write name="acao" property="id"/>', '<bean:write name="acao" property="descricaoCobrancaAcao"/>', 'acaoCobranca');">
													<bean:write name="acao" property="id" />
												</a>
											</logic:notEmpty>
											<logic:empty name="caminhoRetornoTelaPesquisaAcaoCobranca">
												<a href="javascript:enviarDados('<bean:write name="acao" property="id"/>', '<bean:write name="acao" property="descricaoCobrancaAcao"/>', 'acaoCobranca');">
												<bean:write name="acao" property="id" /></a>
											</logic:empty>
										</logic:notPresent>
										</div>
										</td>
										<td>
										<div align="center">

											<logic:notEmpty	name="acao" property="descricaoCobrancaAcao">
												<bean:write name="acao" property="descricaoCobrancaAcao" /> 
											</logic:notEmpty> 
										
											<logic:empty name="acao" property="descricaoCobrancaAcao">
												&nbsp; 
											</logic:empty> 
											
											
										</div>
										</td>
										<td>
										  <div align="center">
										  
											<logic:notEmpty	name="acao" property="cobrancaEstagio">
												<bean:write name="acao" property="cobrancaEstagio.id" />-<bean:write name="acao" property="cobrancaEstagio.descricao" />
											</logic:notEmpty> 							   
											<logic:empty name="acao" property="cobrancaEstagio">
												&nbsp;
											</logic:empty> 	
										  
										  </div>
										</td>
										<td>
										  <div align="center">
										  	<bean:write name="acao" property="documentoTipo.id" />
										  </div>
										</td>
										<td>
										  <div align="center">
											
											<logic:notEmpty	name="acao" property="servicoTipo">
												<bean:write name="acao" property="servicoTipo.id" /> 
											</logic:notEmpty> 
										
											<logic:empty name="acao" property="servicoTipo">
												&nbsp; 
											</logic:empty> 
											
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
						onclick="javascript:window.location.href='exibirPesquisarAcaoCobrancaAction.do';"></div>

					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
</body>
</html:html>
