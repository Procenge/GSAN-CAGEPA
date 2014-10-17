<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>

<html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
</head>


<body leftmargin="5" topmargin="5"
	onload="javascript:resizePageSemLink(790, 520);">
<table width="760" border="0" cellspacing="5" cellpadding="0">
	<tr>
		<td width="760" valign="top" class="centercoltext">
		<table height="100%">
			<tr>
				<td></td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="11"><img
					src="<bean:message key="caminho.imagens"/>parahead_left.gif"
					border="0" /></td>
				<td class="parabg">Pesquisa de Hidr&ocirc;metro</td>
				<td width="11"><img
					src="<bean:message key="caminho.imagens"/>parahead_right.gif"
					border="0" /></td>
			</tr>
		</table>
		<p>&nbsp;</p>
		<table width="100%" border="0" bgcolor="#90c7fc">
			<tr align="left" bgcolor="#90c7fc">
				<td width="16%">
				<div align="center"><strong>N&uacute;mero</strong></div>
				</td>
				<td width="15%">
				<div align="center"><strong>Data de Aquisi&ccedil;&atilde;o</strong></div>
				</td>
				<td width="15%">
				<div align="center"><strong>Ano de Fabrica&ccedil;&atilde;o</strong></div>
				</td>
				<td width="9%">
				<div align="center"><strong>Marca</strong></div>
				</td>
				<td width="25%">
				<div align="center"><strong>Capacidade</strong></div>
				</td>
				<td width="20%">
				<div align="center"><strong>Situa&ccedil;&atilde;o</strong></div>
				</td>
			</tr>

			<%--Esquema de paginação--%>
				<%String cor = "#cbe5fe";%>
			<pg:pager isOffset="true" index="half-full" maxIndexPages="10"
				export="currentPageNumber=pageNumber;pageOffset"
				maxPageItems="10" items="${sessionScope.totalRegistros}">
				
				<pg:param name="pg" />
				<pg:param name="q" />


				<c:forEach items="${sessionScope.colecaoHidrometros}"
					var="hidrometro">
					<pg:item>
						<%if (cor.equalsIgnoreCase("#FFFFFF")) {
						cor = "#cbe5fe";%>
										<tr bgcolor="#cbe5fe">
											<%} else {
						cor = "#FFFFFF";%>
										<tr bgcolor="#FFFFFF">
						<%}%>
						
							<td>
							<div align="center">
							
							
							
								<logic:present name="consultaHidrometro">
									<a href="javascript:enviarIdParaInserir(${hidrometro.id})">
										<bean:write name="hidrometro" property="numero" /></a>
								</logic:present>
				
								<logic:notPresent name="consultaHidrometro">
									<logic:notEmpty name="caminhoRetornoTelaPesquisaHidrometro">
										          
											<a href="javascript:enviarDadosParametros('<bean:write name="caminhoRetornoTelaPesquisaHidrometro"/>','<bean:write name="hidrometro" property="numero"/>', 
												'<bean:write name="hidrometro" property="hidrometroMarca.descricao"/> FAB.:<bean:write name="hidrometro" property="anoFabricacao"/>', 'hidrometro');">
												<bean:write name="hidrometro" property="numero" /> 
											</a>						            
							               
										
							        
									</logic:notEmpty> 
									<logic:empty name="caminhoRetornoTelaPesquisaHidrometro">
										        
											<a href="javascript:enviarDados('<bean:write name="hidrometro" property="numero"/>', '<bean:write name="hidrometro" property="hidrometroMarca.descricao"/> FAB.:<bean:write name="hidrometro" property="anoFabricacao"/>', 'hidrometro');">
												<bean:write name="hidrometro" property="numero" /> 
											</a>							          
							            
											
							         
									</logic:empty>
								</logic:notPresent>
							
							
							
							
							
							
							
							
							
							</div>
							</td>
							<td>
							<div align="center"><bean:write name="hidrometro"
								property="dataAquisicao" formatKey="date.format" /></div>
							</td>
							<td>
							<div align="center">${hidrometro.anoFabricacao}</div>
							</td>
							<td>
							<div>${hidrometro.hidrometroMarca.descricao}</div>
							</td>
							<td>
							<div>${hidrometro.hidrometroCapacidade.descricao}</div>
							</td>
							<td>
							<div>${hidrometro.hidrometroSituacao.descricao}</div>
							</td>
						</tr>

					</pg:item>
				</c:forEach>
		</table>
		<p>&nbsp;</p>
		
		<table width="100%" border="0">
			<tr>
				<td><div align="center"><strong><%@ include
					file="/jsp/util/indice_pager_novo.jsp"%></strong></div>
				</td>
			</tr>
			<tr>
				<td height="24"><input type="button" class="bottonRightCol"
					value="Voltar Pesquisa"
					onclick="window.location.href='<html:rewrite page="/exibirPesquisarHidrometroAction.do?objetoConsulta=1"/>'" /></td>
			</tr>
		</table>
		</pg:pager> <%-- Fim do esquema de paginação --%></td>
	</tr>
</table>

</body>
</html>
