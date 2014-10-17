<%@page import="gcom.util.ConstantesSistema"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="gcom.util.Util"%>
<%@page import="gcom.faturamento.conta.Conta"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.HashMap"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<%@ page import="gcom.cadastro.cliente.Cliente"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js" ></script>
<html:javascript staticJavascript="false" formName="GerarFaturaClienteResponsavelActionForm" />
<script language="JavaScript">

	function extendeTabela(tabela,display){
		var form = document.forms[0];
	
		if(display){
				eval('layerHide'+tabela).style.display = 'none';
				eval('layerShow'+tabela).style.display = 'block';
		}else{
			eval('layerHide'+tabela).style.display = 'block';
				eval('layerShow'+tabela).style.display = 'none';
		}
	}

    function validarForm(){
       var form = document.forms[0];
       
       if (validarContas(form)) {
            submeterFormPadrao(form);
	   } else {
		   alert('Selecione pelo menos uma conta.');
	   }
    }
    
    
    function validarContas(form){
    	var checkboxContas = form.contas;
    	var numContas = checkboxContas.length;
    	var contasSelecionadas = false;
    	
    	if(numContas != "" && numContas > 0){
    		for(i=0; i<numContas; i++){
    			if(checkboxContas[i].checked){
    				contasSelecionadas = true;			
    			}
    		}
    	}else{
    		if(checkboxContas.checked){
    			contasSelecionadas = true;	
    		}		
    	}			
    	return contasSelecionadas;	
    }
	
</script>

</head>
<body leftmargin="5" topmargin="5">
<html:form
	action="/gerarFaturaClienteResponsavelAction"
	method="post"
	name="GerarFaturaClienteResponsavelActionForm"
	type="gcom.gui.faturamento.GerarFaturaClienteResponsavelActionForm">
	
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
			<tr>
				<td></td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="11"><img border="0"
					src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
				<td class="parabg">Gerar Fatura do Cliente Responsável</td>
				<td width="11" valign="top"><img border="0"
					src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
			</tr>
		</table>

		<table width="100%" border="0">

			<%	
				HashMap mapClienteContas = (HashMap) request.getAttribute("mapClienteContas");
			
				if(mapClienteContas != null && !mapClienteContas.isEmpty()) {					
				    Collection<Cliente> colecaoClientes = (Collection<Cliente>) request.getAttribute("colecaoClientesOrdenada"); 
				    Collection<Conta> colecaoContas = null;
			%>
			
			<tr>
				<td colspan="2">Selecione as contas que deseja incluir na fatura:</td>
			</tr>
			
			<% if(!Util.isVazioOrNulo(colecaoClientes)) {
				   for(Cliente cliente : colecaoClientes) {
					
				%>
    			
				<!-- Lista de Contas de um cliente -->
		        <tr bgcolor="#cbe5fe">
						<td>
							<div id="layerHideDadosCliente<%=cliente.getId()%>" style="display:none">
								<table width="650" bgcolor="#90c7fc" border="0" style="">
									<tr bordercolor="#79bbfd">
										<td align="center" bgcolor="#79bbfd" colspan="20">
											<a href="javascript:extendeTabela('DadosCliente<%=cliente.getId()%>',true);" />
											   <b><%=cliente.getNome()%></b>
											</a>
										</td>
									</tr>
								</table>
							</div>
							
							<div id="layerShowDadosCliente<%=cliente.getId()%>" style="display:block">						
								<table width="650" bgcolor="#90c7fc" border="0" style="">
									<tr bordercolor="#79bbfd">
										<td align="center" bgcolor="#79bbfd" colspan="20">
											<a href="javascript:extendeTabela('DadosCliente<%=cliente.getId()%>',false);" />
											   <b><%=cliente.getNome()%></b>
											</a>									
										</td>
									</tr>						
															
									<tr bgcolor="#90c7fc">
										<td align="center" width="8%"><div align="center"><strong><a href="javascript:marcarDesmarcarTodos(<%=cliente.getId()%>);" id="0">Todos</a></strong></div></td>										
										<td width="8%"><div align="center"><strong>Imóvel</strong></div></td>
										<td width="9%"><div align="center"><strong>Refe.</strong></div></td>
										<td width="9%"><div align="center"><strong>Venc.</strong></div></td>
										<td width="8%"><div align="center"><strong>Vl. Água</strong></div></td>
										<td width="8%"><div align="center"><strong>Vl. Esgoto</strong></div></td>
										<td width="8%"><div align="center"><strong>Vl. Débitos</strong></div></td>
										<td width="8%"><div align="center"><strong>Vl. Créditos</strong></div></td>
										<td width="8%"><div align="center"><strong>Vl. Impostos</strong></div></td>
										<td width="8%"><div align="center"><strong>Vl. Conta</strong></div></td>
										<td width="9%"><div align="center"><strong>Mot. Revisão</strong></div></td>
										<td width="9%"><div align="center"><strong>Situação Conta</strong></div></td>
									</tr>
									
						            <%			
											colecaoContas = (Collection<Conta>)mapClienteContas.get(cliente);
											if(!Util.isVazioOrNulo(colecaoContas)) {
												
												
												if(colecaoContas.size() >= ConstantesSistema.NUMERO_MAXIMO_REGISTROS_CONTAS) {
													
									%>
									<tr>
										<td height="300" colspan="12">
											<div style="width: 100%; height: 100%; overflow: auto;">
												<table width="100%">
														
									<%
												}

												// Iniciando totalizadores
												int cont = 0;
												BigDecimal totalValorAgua = BigDecimal.ZERO;
												BigDecimal totalValorEsgoto = BigDecimal.ZERO;
												BigDecimal totalValorDebitos = BigDecimal.ZERO;
												BigDecimal totalValorCreditos = BigDecimal.ZERO;
												BigDecimal totalValorImpostos = BigDecimal.ZERO;
												BigDecimal totalValorConta = BigDecimal.ZERO;
												
												for(Conta conta : colecaoContas) {
													
												    // Acumulando totalizadores
												    cont = cont + 1;
													totalValorAgua = conta.getValorAgua() != null ? totalValorAgua.add(conta.getValorAgua()) : totalValorAgua;
													totalValorEsgoto = conta.getValorEsgoto() != null ? totalValorEsgoto.add(conta.getValorEsgoto()) : totalValorEsgoto;
													totalValorDebitos = conta.getDebitos() != null ? totalValorDebitos.add(conta.getDebitos()) : totalValorDebitos;
													totalValorCreditos = conta.getValorCreditos() != null ? totalValorCreditos.add(conta.getValorCreditos()) : totalValorCreditos;
													totalValorImpostos = conta.getValorImposto() != null ? totalValorImpostos.add(conta.getValorImposto()) : totalValorImpostos;
													totalValorConta = conta.getValorTotal() != null ? totalValorConta.add(conta.getValorTotal()) : totalValorConta;

													String corSituacaoConta = "#000000"; // PRETO
													if(Util.isNaoNuloBrancoZero(conta.getContaMotivoRevisao())){
														corSituacaoConta = "#ff0000"; // VERMELHO 
													} else if(Util.isNaoNuloBrancoZero(conta.getIndicadorPagamento())){
														corSituacaoConta = "#0000FF"; // AZUL
													}else if(Util.isNaoNuloBrancoZero(conta.getIndicadorCobrancaAdministrativa())
																				&& (conta.getIndicadorCobrancaAdministrativa().equals(ConstantesSistema.INDICADOR_USO_ATIVO))) {
														corSituacaoConta = "#008200"; // VERDE
													}													
													
												    if (cont % 2 == 0) {
									%>
												        <tr bgcolor="#cbe5fe">
									<%              } else {	%>
												        <tr bgcolor="#FFFFFF">
								    <%              } %>									
												
												<td align="center" width="8%" valign="middle">
												    <input type="checkbox" name="contas" id="<%="" + cliente.getId().intValue()%>" value="<%="" + cliente.getId().intValue()%>-<%="" + conta.getId().intValue()%>">												    
												</td>
												
												<!-- Imóvel -->
												<td width="8%" align="center" title="<%=conta.getImovel().getNomeImovel()%>">													
													<font color="<%=corSituacaoConta %>"><%=""+conta.getImovel().getId()%></a></font>
												</td>
												
												<!-- Referência da Conta -->
												<td width="9%" align="center">					
												    <a href="javascript:abrirPopup('exibirConsultarContaAction.do?contaID=<%=conta.getId()%>&tipoConsulta=conta', 600, 800);">
												        <font color="<%=corSituacaoConta %>"><%=""+ Util.formatarMesAnoReferencia(conta.getReferencia())%></font>
												    </a>
												</td>
												
												<!-- Vencimento -->
												<td width="9%">
													<div align="center">
														<font color="<%=corSituacaoConta %>"><%="" + Util.formatarData(conta.getDataVencimentoConta())%></font>
													</div>
												</td>
												
												<!-- Valor de Água -->
												<td width="8%">
												    <div align="right">
												        <font color="<%=corSituacaoConta %>"><%="" + Util.formatarMoedaReal(conta.getValorAgua()).trim()%></font>
												    </div>
												</td>
												
												<!-- Valor de Esgoto -->
												<td width="8%">
												    <div align="right">
												        <font color="<%=corSituacaoConta %>"><%="" + Util.formatarMoedaReal(conta.getValorEsgoto()).trim()%></font>
												    </div>
												</td>
												
												<!-- Valor de Débitos -->
												<td width="8%">
												    <div align="right">
												    <% if(conta.getDebitos() != null && conta.getDebitos().compareTo(BigDecimal.ZERO) > 0) { %>
												        <a href="javascript:abrirPopup('exibirConsultarDebitoCobradoAction.do?contaID=<%=conta.getId()%>&tipoConsulta=conta', 600, 800);">
												            <font color="<%=corSituacaoConta %>"><%="" + Util.formatarMoedaReal(conta.getDebitos()).trim()%></font>
												        </a>
												    <% } else { %>
												            <font color="<%=corSituacaoConta %>"><%="" + Util.formatarMoedaReal(conta.getDebitos()).trim()%></font>												    
												    <% } %>
												    </div>
												</td>
												
												<!-- Valor de Créditos -->
												<td width="8%">
												    <div align="right">
												    <% if(conta.getValorCreditos() != null && conta.getValorCreditos().compareTo(BigDecimal.ZERO) > 0) { %>
												        <a href="javascript:abrirPopup('exibirConsultarCreditoRealizadoAction.do?contaID=<%=conta.getId()%>&tipoConsulta=conta', 600, 800);">
    												        <font color="<%=corSituacaoConta %>"><%="" + Util.formatarMoedaReal(conta.getValorCreditos()).trim()%></font>
												        </a>
												    <% } else { %>
												            <font color="<%=corSituacaoConta %>"><%="" + Util.formatarMoedaReal(conta.getValorCreditos()).trim()%></font>										    
												    <% } %>
												    </div>
												</td>
												
												<!-- Valor de Impostos -->
												<td width="8%">
												    <div align="right">
												        <font color="<%=corSituacaoConta %>"><%="" + Util.formatarMoedaReal(conta.getValorImposto()).trim()%></font>
												    </div>
												</td>
												
												<!-- Valor Total da Conta -->
												<td width="8%">
												    <div align="right">
												        <font color="<%=corSituacaoConta %>"><%="" + Util.formatarMoedaReal(conta.getValorTotal()).trim()%></font>
												    </div>
												</td>
												
												<!-- Motivo de Revisão -->
													<% if(conta.getContaMotivoRevisao() != null) { %>
													<td width="9%" title="<%=conta.getContaMotivoRevisao().getDescricaoMotivoRevisaoConta()%>">
														<div align="center">
															<font color="<%=corSituacaoConta %>"><%="" + conta.getContaMotivoRevisao().getId()%></font>
														</div>
													</td>
													<% } else { %> 
	  												<td width="9%"><div align="center">&nbsp;</div></td>
													<% } %>
												
												<!-- Situação da Conta -->
													<% if(conta.getDebitoCreditoSituacaoAtual() != null) { %>
	  												<td width="9%" title="<%=conta.getDebitoCreditoSituacaoAtual().getDescricaoDebitoCreditoSituacao()%>">
														<div align="center">
															<font color="<%=corSituacaoConta %>"><%="" + conta.getDebitoCreditoSituacaoAtual().getId()%></font>
														</div>
													</td>
													<% } else { %> 
	  												<td width="9%"><div align="center">&nbsp;</div></td>
													<% } %>
												</tr>
									    
									<%
												} // Fim do for(Conta conta : colecaoContas)
									%>
									
									<!-- Inicio Ultima linha de TOTAL -->
									
									<%if (cont % 2 == 0) { %>
										<tr bgcolor="#FFFFFF">
									<%} else {%>
										<tr bgcolor="#cbe5fe">
									<%}%>
						
										<td bgcolor="#90c7fc" align="center">
											<div class="style9" align="center">
												<span style="color: #000000;"> 
													<strong>Total</strong>
												</span>
											</div>
										</td>
						
										<td colspan="3" align="center">
											<strong><%=cont%> &nbsp; doc(s)</strong>
										</td>
						
										<td align="right">
											<strong>
												<div align="right">
													<span style="color: #000000;"> 
														<%="" + Util.formatarMoedaReal(totalValorAgua).trim()%>
													</span>
												</div>
											</strong>
										</td>
										
										<td align="rigth">
											<div align="right">
												<strong>
													<span style="color: #000000;">
														<%="" + Util.formatarMoedaReal(totalValorEsgoto).trim()%>
													</span>
												</strong>
											</div>
										</td>
										
										<td align="right">
											<div align="right">
												<strong>
													<span style="color: #000000;">
														<%="" + Util.formatarMoedaReal(totalValorDebitos).trim()%>
													</span>
												</strong>
											</div>
										</td>
										
										<td align="right">
											<div align="right">
												<strong>											
													<span style="color: #000000;"> 
														<%="" + Util.formatarMoedaReal(totalValorCreditos).trim()%>
													</span>
												</strong>
											</div>
										</td>
										
										<td align="right">
											<div align="right">
												<strong>
													<span style="color: #000000;">
														<%="" + Util.formatarMoedaReal(totalValorImpostos).trim()%>
													</span>
												</strong>
											</div>
										</td>
										
										<td align="right">
											<div align="right">
												<strong>
													<span style="color: #000000;">
														<%="" + Util.formatarMoedaReal(totalValorConta).trim()%>
													</span>
												</strong>
											</div>
										</td>
										
										<td align="left">
											<div align="left">
												<span style="color: #000000;">&nbsp;</span>
											</div>
										</td>
										<td align="left">
											<div align="left">
												<span style="color: #000000;">&nbsp;</span>
											</div>
										</td>
									</tr>
									
									<!-- Fim Ultima linha de TOTAL -->
									
									<tr>									
										<td colspan="12">
											<div style="width: 100%; height: 100%;">
												<table width="100%">
													<tr>
														<td width="60%">
															<div align="right">Legenda:</div>
														 </td>
														
														 <td width="40%">
															<div align="left"><font color="#FF0000"> Contas em revisão </font></div>
														</td>
													</tr>
													 <tr>
														 <td width="60%">
															<div align="right">&nbsp;</div>
														 </td>
														 <td width="40%">
															<div align="left"><font color="#0000FF"> Contas pagas e não baixadas </font></div>
														</td>
													</tr>
													 <tr>
														 <td width="60%">
															<div align="right">&nbsp;</div>
														 </td>
														 <td width="40%">
															<div align="left"><font color="#008200"> Contas em cobrança administrativa</font></div>
														</td>
													</tr>
												</table>
											</div>
										</td>
									
									</tr>																										
									
							<%
							
										if(colecaoContas.size() >= ConstantesSistema.NUMERO_MAXIMO_REGISTROS_CONTAS) {
								
							%>
											</table>
										</div>
									</td>
								</tr>
							<%
										}
							
									} // Fim do if(colecaoContas != null && !colecaoContas.isEmpty())
							%>					
							
									
								</table>
							</div>
						</td>
					</tr>    			
			<%
				    } // Fim do for(Cliente cliente : colecaoClientes)

			    } // Fim do if(!Util.isVazioOrNulo(colecaoClientes))
			%>		

			<%
			    } else { // Else do if(mapClienteContas != null && !mapClienteContas.isEmpty())
			%>
			<tr>
				<td colspan="2">Não há contas para geração da fatura do cliente.</td>
			</tr>
			<%
			    } // Fim do else
			%>


			
	        
	        
		</table>

		<table width="100%">
			<tr>
				<td align="left">
					<input name="Button" 
						type="button" 
						class="bottonRightCol"
						value="Voltar Filtro" 
						align="left"
						onclick="history.back();">
						&nbsp;
				</td>
				
				<td align="right">
					<gcom:controleAcessoBotao name="Button" 
				  		value="Gerar" 
				  		onclick="javascript:validarForm();" 
				  		url="gerarFaturaClienteResponsavelAction.do"/>
				  	
				</td>
			</tr>
		</table>
	</tr>


</table>
<%@ include file="/jsp/util/rodape.jsp"%></html:form>
</body>
</html:html>
