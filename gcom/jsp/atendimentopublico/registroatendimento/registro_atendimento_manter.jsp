<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page import="gcom.util.ConstantesSistema"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>/validacao/ManutencaoRegistro.js">
</script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js">
</script>
<script>
<!--
function facilitador(objeto){

	if (objeto.id == "0" || objeto.id == undefined){
		objeto.id = "1";
		marcarTodos();
	}
	else{
		objeto.id = "0";
		desmarcarTodos();
	}
	
}

function gerarOS(objeto) {
	var form = document.forms[0];

	if (CheckboxNaoVazioMensagemGenerico("Selecione pelo menos um registro para gerar a ordem de serviço!",objeto)){
		if (confirm ("Gerar ordem de serviço para a(s) RA('s) selecionada(s)?")) {
			form.action = 'exibirGerarOrdemServicoAction.do?telaManterRA=sim&forward=exibirGerarOrdemServico&caminhoRetornoGerarOs=exibirFiltrarRegistroAtendimentoAction.do';
			form.submit();
		 }
	}
}

function cancelarRA(objeto) {
	var form = document.forms[0];
	
	if (CheckboxNaoVazioMensagemGenerico("Selecione pelo menos um registro de atendimento para ser Cancelada!",objeto)){
		if (confirm ("Cancelar RA('s) selecionada(s)?")) {
			form.action = 'exibirCancelarRegistroAtendimentoAction.do?telaManterRA=sim&caminhoRetorno=filtrarRegistroAtendimentoAction.do&numerosRAs='+ obterValorCheckboxMarcadoPorNome("idRegistrosRAGerarOrdemServico");
			form.submit();
		 }
	}
}

-->
</script>
</head>
<body leftmargin="5" topmargin="5">

<html:form action="/filtrarRegistroAtendimentoAction.do" 
		   name="FiltrarRegistroAtendimentoActionForm" 
		   type="gcom.gui.atendimentopublico.registroatendimento.FiltrarRegistroAtendimentoActionForm"
		   method="post">
		   
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
                <td class="parabg">Manter RA - Registro de Atendimento</td>
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
								<td width="5%" align="center">
									<strong>
									<a onclick="this.focus();" id="0" href="javascript:facilitador(this);">Todos</a>
									</strong>
								</td>
								<td width="5%"><div align="center"><strong>Nº do RA</strong></div></td>
			                    <td width="15%"><div align="center"><strong>Especifica&ccedil;&atilde;o</strong></div></td>	
            			        <td width="10%"><div align="center"><strong>Data Atend.</strong></div></td>
			                    
			                    <!-- Se o filtro for por RAs Reiteradas, não aparece -->
			                    <logic:notPresent name="isRAReiterado" scope="session">				                   
				                    <td width="10%"><div align="center"><strong>Data Prevista</strong></div></td>
			                    </logic:notPresent>
			                    
			                    <logic:present name="isRAReiterado" scope="session">
			                    	<td width="10%"><div align="center"><strong>Qtd. Reitera&ccedil;&atilde;o</strong></div></td>
			                    	<td width="10%"><div align="center"><strong>Última Reitera&ccedil;&atilde;o</strong></div></td>
			                    </logic:present>
			                    
			                    <td width="5%"><div align="center"><strong>Sit.</strong></div></td>
			                    <td width="15%"><div align="center"><strong>Unid. Atual</strong></div></td>
								<td width="30%"><div align="center"><strong>Endere&ccedil;o da Ocorr&ecirc;ncia</strong></div></td>									
								<td width="5%"><div align="center"><strong>Serv</strong></div></td>
								<td width="5%"><div align="center"><strong>Sit.OS</strong></div></td>
								<td width="5%"><div align="center"><strong>Pend.$</strong></div></td>
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
												<input type="checkbox" name="idRegistrosRAGerarOrdemServico" value="<bean:write name="helper" property="registroAtendimento.id"/>"/>
											</div>	
										</td>
										<logic:equal name="helper" property="indicadorOrdemServicoGerada" value="<%=ConstantesSistema.NAO.toString()%>">
											<td bordercolor="#90c7fc">
					                        	<div align="center">
					                        		<a href="javascript:window.location.href='<html:rewrite page="/exibirConsultarRegistroAtendimentoAction.do?numeroRA=${helper.registroAtendimento.id}"/>';" 
					                        		   title="Consultar RA - Registro de Atendimento" onmouseover="window.status='Consultar RA - Registro de Atendimento'; return true"
					                        		   onmouseout="window.status=''; return true">
														<font color="#ff0000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
															<bean:write name="helper" property="registroAtendimento.id" /> 
														</font>
													</a>
												</div>
											</td>
										</logic:equal>
										<logic:notEqual name="helper" property="indicadorOrdemServicoGerada" value="<%=ConstantesSistema.NAO.toString()%>">
											<td bordercolor="#90c7fc">
					                        	<div align="center">
					                        		<a href="javascript:window.location.href='<html:rewrite page="/exibirConsultarRegistroAtendimentoAction.do?numeroRA=${helper.registroAtendimento.id}"/>';" 
					                        		   title="Consultar RA - Registro de Atendimento" onmouseover="window.status='Consultar RA - Registro de Atendimento'; return true"
					                        		   onmouseout="window.status=''; return true">
														<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
															<bean:write name="helper" property="registroAtendimento.id" /> 
														</font>
													</a>
												</div>
											</td>
										</logic:notEqual>
										<logic:equal name="helper" property="indicadorOrdemServicoGerada" value="<%=ConstantesSistema.NAO.toString()%>">
											<td bordercolor="#90c7fc">
					                        	<div>
													<font color="#ff0000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
														<bean:write name="helper" property="registroAtendimento.solicitacaoTipoEspecificacao.descricao" />
													</font> 
												</div>	
											</td>
										</logic:equal>
										<logic:notEqual name="helper" property="indicadorOrdemServicoGerada" value="<%=ConstantesSistema.NAO.toString()%>">
											<td bordercolor="#90c7fc">
					                        	<div>
													<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
														<bean:write name="helper" property="registroAtendimento.solicitacaoTipoEspecificacao.descricao" /> 
													</font>
												</div>	
											</td>
										</logic:notEqual>
										<logic:equal name="helper" property="indicadorOrdemServicoGerada" value="<%=ConstantesSistema.NAO.toString()%>">
											<td bordercolor="#90c7fc">
					                        	<div align="center">
													<logic:notEmpty name="helper" property="registroAtendimento.registroAtendimento">
														<font color="#ff0000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
															<bean:write name="helper" property="registroAtendimento.registroAtendimento"  format="dd/MM/yyyy" /> 
														</font>
													</logic:notEmpty>
												</div>	
											</td>
										</logic:equal>
										<logic:notEqual name="helper" property="indicadorOrdemServicoGerada" value="<%=ConstantesSistema.NAO.toString()%>">
											<td bordercolor="#90c7fc">
					                        	<div align="center">
													<logic:notEmpty name="helper" property="registroAtendimento.registroAtendimento">
														<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
															<bean:write name="helper" property="registroAtendimento.registroAtendimento"  format="dd/MM/yyyy" />
														</font> 
													</logic:notEmpty>
												</div>	
											</td>
										</logic:notEqual>
										<!-- Se o filtro for por RAs Reiteradas, não aparece -->
			                    		<logic:notPresent name="isRAReiterado" scope="session">											
											<logic:equal name="helper" property="indicadorOrdemServicoGerada" value="<%=ConstantesSistema.NAO.toString()%>">
												<td bordercolor="#90c7fc">
						                        	<div align="center">
														<logic:notEmpty name="helper" property="registroAtendimento.dataPrevistaAtual">
															<font color="#ff0000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
																<bean:write name="helper" property="registroAtendimento.dataPrevistaAtual"  format="dd/MM/yyyy" /> 
															</font>
														</logic:notEmpty>
													</div>	
												</td>
											</logic:equal>
											<logic:notEqual name="helper" property="indicadorOrdemServicoGerada" value="<%=ConstantesSistema.NAO.toString()%>">
												<td bordercolor="#90c7fc">
						                        	<div align="center">
														<logic:notEmpty name="helper" property="registroAtendimento.dataPrevistaAtual">
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
																<bean:write name="helper" property="registroAtendimento.dataPrevistaAtual"  format="dd/MM/yyyy" /> 
															</font> 
														</logic:notEmpty>
													</div>	
												</td>
											</logic:notEqual>
										</logic:notPresent>
										<logic:present name="isRAReiterado" scope="session">
											<logic:equal name="helper" property="indicadorOrdemServicoGerada" value="<%=ConstantesSistema.NAO.toString()%>">
												<td bordercolor="#90c7fc">
						                        	<div align="center">
														<logic:notEmpty name="helper" property="registroAtendimento.quantidadeReiteracao">
															<font color="#ff0000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
																<bean:write name="helper" property="registroAtendimento.quantidadeReiteracao" /> 
															</font>
														</logic:notEmpty>
													</div>	
												</td>
											</logic:equal>
											<logic:notEqual name="helper" property="indicadorOrdemServicoGerada" value="<%=ConstantesSistema.NAO.toString()%>">
												<td bordercolor="#90c7fc">
						                        	<div align="center">
														<logic:notEmpty name="helper" property="registroAtendimento.quantidadeReiteracao">
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
																<bean:write name="helper" property="registroAtendimento.quantidadeReiteracao" /> 
															</font> 
														</logic:notEmpty>
													</div>	
												</td>
											</logic:notEqual>											
											<logic:equal name="helper" property="indicadorOrdemServicoGerada" value="<%=ConstantesSistema.NAO.toString()%>">
												<td bordercolor="#90c7fc">
						                        	<div align="center">
														<logic:notEmpty name="helper" property="registroAtendimento.ultimaReiteracao">
															<font color="#ff0000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
																<bean:write name="helper" property="registroAtendimento.ultimaReiteracao" format="dd/MM/yyyy" />
															</font>
														</logic:notEmpty>
													</div>	
												</td>
											</logic:equal>
											<logic:notEqual name="helper" property="indicadorOrdemServicoGerada" value="<%=ConstantesSistema.NAO.toString()%>">
												<td bordercolor="#90c7fc">
						                        	<div align="center">
														<logic:notEmpty name="helper" property="registroAtendimento.ultimaReiteracao">
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
																<bean:write name="helper" property="registroAtendimento.ultimaReiteracao" format="dd/MM/yyyy" />
															</font> 
														</logic:notEmpty>
													</div>	
												</td>
											</logic:notEqual>											
										</logic:present>
										<logic:equal name="helper" property="indicadorOrdemServicoGerada" value="<%=ConstantesSistema.NAO.toString()%>">
											<td bordercolor="#90c7fc">
					                        	<div align="center">
													<logic:notEmpty name="helper" property="situacao">
														<font color="#ff0000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
															<bean:write name="helper" property="situacao" /> 
														</font>
													</logic:notEmpty>
												</div>	
											</td>
										</logic:equal>
										<logic:notEqual name="helper" property="indicadorOrdemServicoGerada" value="<%=ConstantesSistema.NAO.toString()%>">
											<td bordercolor="#90c7fc">
					                        	<div align="center">
													<logic:notEmpty name="helper" property="situacao">
														<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
															<bean:write name="helper" property="situacao" /> 
														</font> 
													</logic:notEmpty>
												</div>	
											</td>
										</logic:notEqual>
										<logic:equal name="helper" property="indicadorOrdemServicoGerada" value="<%=ConstantesSistema.NAO.toString()%>">
											<td bordercolor="#90c7fc">
					                        	<div align="center">
													<logic:notEmpty name="helper" property="unidadeAtual">
														<font color="#ff0000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
															<bean:write name="helper" property="unidadeAtual.descricao" />
														</font>
													</logic:notEmpty>
												</div>	
											</td>
										</logic:equal>
										<logic:notEqual name="helper" property="indicadorOrdemServicoGerada" value="<%=ConstantesSistema.NAO.toString()%>">
											<td bordercolor="#90c7fc">
					                        	<div align="center">
													<logic:notEmpty name="helper" property="unidadeAtual">
														<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
															<bean:write name="helper" property="unidadeAtual.descricao" />
														</font>
													</logic:notEmpty>
												</div>	
											</td>
										</logic:notEqual>
										<logic:equal name="helper" property="indicadorOrdemServicoGerada" value="<%=ConstantesSistema.NAO.toString()%>">
											<td bordercolor="#90c7fc">
					                        	<div>
													<font color="#ff0000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
														<bean:write name="helper" property="enderecoOcorrencia" />
													</font> 
												</div>
											</td>
										</logic:equal>
										<logic:notEqual name="helper" property="indicadorOrdemServicoGerada" value="<%=ConstantesSistema.NAO.toString()%>">
											<td bordercolor="#90c7fc">
					                        	<div>
													<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
														<bean:write name="helper" property="enderecoOcorrencia" /> 
													</font>
												</div>
											</td>
										</logic:notEqual>			
										
										<logic:equal name="helper" property="indicadorOrdemServicoGerada" value="<%=ConstantesSistema.NAO.toString()%>">
																			
											<td bordercolor="#90c7fc">
					                        	<div>
													<font color="#ff0000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
															
														<bean:write name="helper" property="servicoOS" /> 
														
														
													</font> 
												</div>
											</td>																		
										
										</logic:equal>		
										<logic:notEqual name="helper" property="indicadorOrdemServicoGerada" value="<%=ConstantesSistema.NAO.toString()%>">
											<td bordercolor="#90c7fc">
					                        	<div>
													<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
															
														<bean:write name="helper" property="servicoOS" />  
														
														
													</font>
												</div>
											</td>
										</logic:notEqual>		
											
										
											
										<logic:equal name="helper" property="indicadorOrdemServicoGerada" value="<%=ConstantesSistema.NAO.toString()%>">
																			
											<td bordercolor="#90c7fc">
					                        	<div>
													<font color="#ff0000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
															
														<bean:write name="helper" property="situacaoOS" /> 
														
														
													</font> 
												</div>
											</td>																		
										
										</logic:equal>		
										<logic:notEqual name="helper" property="indicadorOrdemServicoGerada" value="<%=ConstantesSistema.NAO.toString()%>">
											<td bordercolor="#90c7fc">
					                        	<div>
													<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
															
														<bean:write name="helper" property="situacaoOS" />  
														
														
													</font>
												</div>
											</td>
										</logic:notEqual>		
											
									
										<td bordercolor="#90c7fc" align="center">
				                        	<div>
												<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
														
													 <logic:equal name="helper" property="quantidadeGuiasPendentes" value="0">
													 	Não
													 </logic:equal> 
													 
													 <logic:notEqual name="helper" property="quantidadeGuiasPendentes" value="0">
													 	Sim
													 </logic:notEqual> 													 
													
													
												</font>
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
												<input name="button" 
													type="button" 
													class="bottonRightCol" 
													value="Voltar Filtro" 
													onclick="window.location.href='<html:rewrite page="/exibirFiltrarRegistroAtendimentoAction.do"/>'" align="left" style="width: 80px;">
						                  	</td>
						                  	
						                  	<td colspan="6" align="right">
												<input name="button" 
													type="button" 
													class="bottonRightCol" 
													value="Cancelar" 
													onclick="javascript:cancelarRA(document.forms[0].idRegistrosRAGerarOrdemServico);" align="left" style="width: 80px;">
							                  	<input name="ButtonOrdemSer" 
							                  		type="button" 
							                  		class="bottonRightCol" 
							                  		value="Gerar O.S"
							                  		onClick="javascript:gerarOS(document.forms[0].idRegistrosRAGerarOrdemServico);"> 
											</td>
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

</html:form>

</body>
</html:html>