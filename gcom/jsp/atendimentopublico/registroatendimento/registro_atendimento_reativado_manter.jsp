<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js">
</script>

</head>
<body leftmargin="5" topmargin="5">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>
<table width="770" border="0" cellspacing="5" cellpadding="0">
	<tr>
		<td width="115" valign="top" class="leftcoltext">
			<div align="center">
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				<%@ include file="/jsp/util/informacoes_usuario.jsp"%>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				<%@ include file="/jsp/util/mensagens.jsp"%>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
				<p align="left">&nbsp;</p>
			</div>
		</td>
		<td valign="top" class="centercoltext">
            <table>
              <tr><td></td></tr>
            </table>
            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
                <td class="parabg">Registros de Atendimento Reativados</td>
                <td width="11" valign="top"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
              </tr>
            </table>
            <!-- Início do Corpo -->
            <p>&nbsp;</p>
            <table width="100%" cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td colspan="11">
						<font color="#000000" style="font-size:10px" face="Verdana, Arial, Helvetica, sans-serif">
						<strong>Registros de Atendimento encontrados: <bean:write scope="session" name="totalRegistros"/></strong> </font></td>
				</tr>
				<tr>
					<td colspan="11">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="11">
						<table width="100%" bgcolor="#99CCFF">
			 				<tr bordercolor="#90c7fc" bgcolor="#90c7fc"> 
								<td width="9%" ><div align="center"><strong>RA de Origem</strong></div></td>
								<td width="9%" ><div align="center"><strong>RA Reativado</strong></div></td>
			                    <td width="40%"><div align="center"><strong>Especifica&ccedil;&atilde;o</strong></div></td>			                    
            			        <td width="9%" ><div align="center"><strong>Data Reativa&ccedil;&atilde;o</strong></div></td>
			                    <td width="10%" ><div align="center"><strong>Matrícula Atendente</strong></div></td>
			                    <td width="3%" ><div align="center"><strong>Sit.</strong></div></td>
			                    <td width="20%"><div align="center"><strong>Unidade Atual</strong></div></td>
			                </tr>
			                <tr>
							<pg:pager isOffset="true" maxIndexPages="10" export="currentPageNumber=pageNumber;pageOffset" 
									  index="half-full" maxPageItems="10" items="${sessionScope.totalRegistros}">
								<pg:param name="q" />
								<pg:param name="pg" />
								<%--Esquema de paginação--%>
								<c:set var="count" value="0"/>
								<logic:iterate name="colecaoRAHelper" id="helper">
									<pg:item>
			                      		<c:set var="count" value="${count+1}"/>
				                        <c:choose>
		                        			<c:when test="${count%2 == '1'}">
		                        				<tr bgcolor="#FFFFFF">
		                        			</c:when>
		                        			<c:otherwise>
		                        				<tr bgcolor="#cbe5fe">
		                        			</c:otherwise>
		                        		</c:choose>											
										<td bordercolor="#90c7fc">
				                        	<div>
												<bean:write name="helper" property="registroAtendimento.registroAtendimentoReativacao.id" /> 
											</div>	
										</td>
										<td bordercolor="#90c7fc">
				                        	<div align="center">
				                        		<logic:equal name="sistemaParametro" property="parmId" scope="session" value="<%=SistemaParametro.INDICADOR_EMPRESA_DESO.toString()%>">
					                        		<a href="javascript:window.location.href='<html:rewrite page="/exibirConsultarRegistroAtendimentoAction.do?raReativado=1&numeroRA=${helper.registroAtendimento.id}"/>';" 
					                        		   title="Consultar RA - Registro de Atendimento" onmouseover="window.status='Consultar Registro de Atendimento'; return true"
					                        		   onmouseout="window.status=''; return true">
														<bean:write name="helper" property="registroAtendimento.id" /> 
													</a>
												</logic:equal>
												<logic:equal name="sistemaParametro" property="parmId" scope="session" value="<%=SistemaParametro.INDICADOR_EMPRESA_ADA.toString()%>">
					                        		<a href="javascript:window.location.href='<html:rewrite page="/exibirConsultarRegistroAtendimentoAction.do?raReativado=0&numeroRA=${helper.registroAtendimento.id}"/>';" 
					                        		   title="Consultar RA - Registro de Atendimento" onmouseover="window.status='Consultar Registro de Atendimento'; return true"
					                        		   onmouseout="window.status=''; return true">
														<bean:write name="helper" property="registroAtendimento.id" /> 
													</a>
												</logic:equal>
											</div>
										</td>
										<td bordercolor="#90c7fc">
				                        	<div>
												<bean:write name="helper" property="registroAtendimento.solicitacaoTipoEspecificacao.descricao" /> 
											</div>	
										</td>										
										<td bordercolor="#90c7fc">
				                        	<div align="center">
												<logic:notEmpty name="helper" property="registroAtendimento.registroAtendimento">
													<bean:write name="helper" property="registroAtendimento.registroAtendimento"  format="dd/MM/yyyy" /> 
												</logic:notEmpty>
											</div>	
										</td>
										<td bordercolor="#90c7fc">
				                        	<div align="center">
												<bean:write name="helper" property="registroAtendimento.registroAtendimentoUnidade.usuario.login" />
											</div>	
										</td>
										<td bordercolor="#90c7fc">
				                        	<div align="center">
												<logic:notEmpty name="helper" property="situacao">
													<bean:write name="helper" property="situacao" /> 
												</logic:notEmpty>
											</div>	
										</td>
										<td bordercolor="#90c7fc">
				                        	<div align="center">
												<logic:notEmpty name="helper" property="unidadeAtual">
													<bean:write name="helper" property="unidadeAtual.descricao" />
												</logic:notEmpty>
											</div>	
										</td>
									</tr>
								</pg:item>
							</logic:iterate>
						</table>
						<%-- Fim do esquema de paginação --%>
						<table align="center">
							<tr align="center">
								<td align="center">
									<div align="center">
										<strong><%@ include	file="/jsp/util/indice_pager_novo.jsp"%>
										</strong>
									</div>
								</td>
							</tr>
						</table>
						</pg:pager>
					</td>
				</tr>
                <tr>
					<td colspan="5">
						<table width="100%" border="0">
							<tr>
								<td colspan="5">
									<table width="100%" border="0">
										<tr>
											<td valign="top">
												<input name="button" type="button" class="bottonRightCol" value="Voltar Filtro" onclick="window.location.href='<html:rewrite page="/exibirFiltrarRegistroAtendimentoAction.do"/>'" align="left" style="width: 80px;"></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
 			</table>
			<!-- Fim do Corpo -->
		</td>
	</tr>
</table>		
<p>&nbsp;</p>
<%@ include file="/jsp/util/rodape.jsp"%>

</body>
</html:html>