<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@page import="gcom.util.ConstantesSistema"%>


<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="ConsultarComandoOsSeletivaActionForm" dynamicJavascript="false" />
</head>

<body leftmargin="5" topmargin="5">
<html:form action="/filtrarComandoOsSeletivaAction.do" 
	name="ConsultarComandoOsSeletivaActionForm"
	type="gcom.gui.atendimentopublico.ordemservico.ConsultarComandoOsSeletivaActionForm" method="post">
	
<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp"%>

<table width="770" border="0" cellspacing="5" cellpadding="0">
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
		<td width="625" valign="top" class="centercoltext">
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
				<td class="parabg">Resultado Consultar Comando Ordem Servi&ccedil;o Seletiva</td>
                <td width="11" valign="top"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
              </tr>
            </table>
            <!-- Início do Corpo -->
            <p>&nbsp;</p>
            <table width="100%" cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td colspan="11">
						<font color="#000000" style="font-size:10px" face="Verdana, Arial, Helvetica, sans-serif">
						<strong></strong> </font></td>
				</tr>
				<tr>
					<td colspan="11">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="11">
						<table width="100%" bgcolor="#99CCFF">
			 				<tr bordercolor="#90c7fc" bgcolor="#90c7fc"> 
								<td width="14%" ><div align="center"><strong>Título</strong></div></td>
				                <td width="25%"><div align="center"><strong>Empresa</strong></div></td>
						        <td width="9%" ><div align="center"><strong>Tipo do Serviço</strong></div></td>
				                <td width="9%" ><div align="center"><strong>Data do Comando</strong></div></td>
				                <td width="9%" ><div align="center"><strong>Data Realização</strong></div></td>
			                  	<td width="8%"><div align="center"><strong>Qtde. OSs</strong></div></td>
							</tr>
							<tr>
								<%--Esquema de paginação--%>
								<pg:pager isOffset="true" index="half-full" maxIndexPages="10"
										  export="currentPageNumber=pageNumber;pageOffset"
							             maxPageItems="10" items="${sessionScope.totalRegistros}">
									<pg:param name="q" />
									<pg:param name="pg"/>
									<logic:present name="colecaoComandoHelper">
									<%--Esquema de paginação--%>
									<c:set var="count" value="0"/>
									<logic:iterate name="colecaoComandoHelper" id="helper">
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
						                        		
						                        			<a  title="Consultar Dados do Comando da Ordem de Serviço Seletiva" href="javascript:window.location.href='<html:rewrite page="/exibirConsultarComandoOsSeletivaoAction.do?numeroComando=${helper.id}"/>';">
																<bean:write name="helper" property="titulo" />
															</a>
													</div>
												</td>

												<td bordercolor="#90c7fc">
						                        	<div align="center">
														<logic:notEmpty name="helper" property="firmaDescricao">
															<bean:write name="helper" property="firmaDescricao"/> 
														</logic:notEmpty>
													</div>	
												</td>

												<td bordercolor="#90c7fc">
						                        	<div align="center">
													<logic:notEmpty name="helper" property="servicoTipoDescricao">
															<bean:write name="helper" property="servicoTipoDescricao"/> 
													</logic:notEmpty>
													</div>	
												</td>

												<td bordercolor="#90c7fc">
						                        	<div align="center">
														<logic:notEmpty name="helper" property="dataComando">
															<bean:write name="helper" property="dataComando"  format="dd/MM/yyyy" /> 
														</logic:notEmpty>
													</div>	
												</td>
												<td bordercolor="#90c7fc">
						                        	<div align="center">
														<logic:notEmpty name="helper" property="dataRealizacaoComando">
															<bean:write name="helper" property="dataRealizacaoComando" format="dd/MM/yyyy" /> 
														</logic:notEmpty>
													</div>	
												</td>
													<td bordercolor="#90c7fc">
						                        	<div align="center">
														<logic:notEmpty name="helper" property="quantidadeMaximaOrdem">
															<bean:write name="helper" property="quantidadeMaximaOrdem" /> 
														</logic:notEmpty>
													</div>	
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
				<td>
					<div align="center">
						<strong><%@ include	file="/jsp/util/indice_pager_novo.jsp"%></strong>
					</div>
				</td>
			</tr>
			<tr>
				<td height="24"><input type="button" class="bottonRightCol"
					value="Voltar Pesquisa"
					onclick="window.location.href='exibirFiltrarComandoOsSeletivaoAction.do?objetoConsultaOs=0';"/></td>
					<td valign="top">
					
                    <div align="right"><a href="javascript:toggleBox('demodiv',1);">
							<img border="0"
								src="<bean:message key="caminho.imagens"/>print.gif"
								title="Imprimir Comandos OS Seletiva" /> </a></div></td>
			</tr>
		</table>
		</pg:pager></td>
	</tr>

</table>

<jsp:include
		page="/jsp/relatorio/escolher_tipo_relatorio.jsp?relatorio=gerarRelatorioComandoOsSeletivaAction.do" />
		
</body>
</html:form>
</html:html>
