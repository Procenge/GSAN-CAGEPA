<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

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
<table width="770" border="0" cellspacing="4" cellpadding="0">
	<tr>
		<td width="149" valign="top" class="leftcoltext">
			<div align="center">
				<%--<p align="left">&nbsp;</p>--%>
				<%--<p align="left">&nbsp;</p>--%>
				<%--<p align="left">&nbsp;</p>--%>
				<%@ include file="/jsp/util/informacoes_usuario.jsp"%>
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
				<%@ include file="/jsp/util/mensagens.jsp"%>
				<%--<p align="left">&nbsp;</p>--%>
				<%--<p align="left">&nbsp;</p>--%>
				<%--<p align="left">&nbsp;</p>--%>
				<%--<p align="left">&nbsp;</p>--%>
				<%--<p align="left">&nbsp;</p>--%>
				<%--<p align="left">&nbsp;</p>--%>
				<%--<p align="left">&nbsp;</p>--%>
			</div>
		</td>
		<td valign="top" class="centercoltext">
            <table>
              <tr><td></td></tr>
            </table>
            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
                <td class="parabg">Manter RA - Registro de Atendimento Incompleto</td>
                <td width="11" valign="top"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
              </tr>
            </table>
            <!-- Início do Corpo -->
            <p>&nbsp;</p>
            <table width="100%" cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td colspan="11">
						<font color="#000000" style="font-size:10px" face="Verdana, Arial, Helvetica, sans-serif">
						<strong>Registros de Atendimento encontrados:</strong> </font></td>
				</tr>
				<tr>
					<td colspan="11">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="11">
						<table width="100%" bgcolor="#99CCFF">
			 				<tr bordercolor="#90c7fc" bgcolor="#90c7fc"> 
								<td width="9%" ><div align="center"><strong>Protocolo</strong></div></td>
			                    <td width="9%"><div align="center"><strong>DDD/Fone</strong></div></td>			                    
            			        <td width="35%" ><div align="center"><strong>Contato</strong></div></td>
			                    <td width="9%" ><div align="center"><strong>Retornado</strong></div></td>
			                    <td width="15%" ><div align="center"><strong>Motivo</strong></div></td>
			                    <td width="3%" ><div align="center"><strong>Data/Hora</strong></div></td>
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
				                        	<div align="center">
				                        		<a href="javascript:window.location.href='<html:rewrite page="/exibirConsultarRegistroAtendimentoIncompletoAction.do?numeroRA=${helper.id}"/>';" 
				                        		   title="Consultar RA - Registro de Atendimento Incompleto" onmouseover="window.status='Consultar Registro de Atendimento Incompleto'; return true"
				                        		   onmouseout="window.status=''; return true">
													<bean:write name="helper" property="id" /> 
												</a>
												
											</div>
										</td>
										<td bordercolor="#90c7fc">
				                        	<div>
												<bean:write name="helper" property="ddd" /> 
											</div>	
										</td>										
										<td bordercolor="#90c7fc">
				                        	<div align="center">
												
													<bean:write name="helper" property="nomeContato"   /> 
												
											</div>	
										</td>
										<td bordercolor="#90c7fc">
				                        	<div align="center">
												<logic:equal value="2" name="helper" property="indicadorRetornoChamada">
												NAO
												</logic:equal>
												<logic:equal value="1" name="helper" property="indicadorRetornoChamada">
												SIM
												</logic:equal>
												
											</div>	
										</td>
										<td bordercolor="#90c7fc">
				                        	<div align="center">		
												<bean:write name="helper" property="atendimentoIncompletoMotivo.descricao"   /> 

											</div>	
										</td>
										<td bordercolor="#90c7fc">
				                        	<div align="center">
												
												<bean:write name="helper" property="ultimaAlteracao" formatKey="date.format"/> 
												<bean:write name="helper" property="ultimaAlteracao" formatKey="hour.format"/>
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
												<input name="button" type="button" class="bottonRightCol" value="Voltar Filtro" onclick="window.location.href='<html:rewrite page="/exibirFiltrarRegistroAtendimentoIncompletoAction.do"/>'" align="left" style="width: 80px;"></td>
											<%-- <td valign="top">
												<div align="right"><a href="javascript:abrirPopupRelatorio('');">
												<img border="0"	src="<bean:message key="caminho.imagens"/>print.gif" title="Imprimir Registros de Atendimento" /> </a></div>
											</td>--%>
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