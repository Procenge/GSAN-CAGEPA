<%@page import="gcom.faturamento.debito.DebitoCreditoSituacao"%>
<%@page import="gcom.util.Util"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gsan" %>

<%@ page import="gcom.gui.faturamento.bean.GuiaPagamentoPrestacaoHelper"%>
<%@ page import="gcom.util.ConstantesSistema" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false"  
	formName="AtualizarGuiaPagamentoActionForm" 
	dynamicJavascript="false" />

<SCRIPT LANGUAGE="JavaScript">
	function validarForm(formulario) {
		
		submeterFormPadrao(formulario);
	}

	function verficarSelecaoImprimir(objeto){
		var form = document.AtualizarGuiaPagamentoActionForm;
		if (CheckboxNaoVazio(objeto)){
			form.action = "/gsan/gerarRelatorioEmitirGuiaPagamentoActionCancelar.do"
			form.submit();
		}
	 }

	function verficarSelecaoCancelar(objeto){
		var form = document.AtualizarGuiaPagamentoActionForm;
		if (CheckboxNaoVazio(objeto)){
			form.action = "/gsan/manterGuiaPagamentoAction.do"
			form.submit();
		}
	 }
		
	function CheckboxNaoVazio(campo){

	    form = document.forms[0];
		retorno = false;
	
		for(indice = 0; indice < form.elements.length; indice++){

			if (form.elements[indice].type == "checkbox" && form.elements[indice].checked == true) {

				retorno = true;
				break;
			}
		}
	
		if (!retorno){
			alert('Selecione pelo menos uma guia de pagamento para imprimir.'); 
		}
	
		return retorno;
	}

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
	
</SCRIPT>
</head>

<body leftmargin="5" topmargin="5">

<html:form action="/atualizarGuiaPagamentoAction.do" method="post">
	
	
	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp" %>

	<table width="770" border="0" cellspacing="5" cellpadding="0">
  		<tr>
    		<td width="130" valign="top" class="leftcoltext">
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

			<td width="625" valign="top" class="centercoltext">

	        <table height="100%">
	        	<tr>
	          		<td></td>
	        	</tr>
	      	</table>

	      	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	        	<tr>
	          		<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
	          		<td class="parabg">Exibir Prestações da Guia de Pagamento</td>
	          		<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
	        	</tr>
	      	</table>
	      	
      		<p>&nbsp;</p>

	      	<table width="100%" border="0">
		      	<tr>
		      		<td colspan="2">Para alterar a Guia de Pagamento, informe os dados abaixo:</td>
		      	</tr>
	      	</table>
    
      		<table width="100%" border="0">
      		
				<tr>
					<td colspan="4">

						<table width="100%" align="center" bgcolor="#99CCFF" border="0">
							
							<tr>
								<td><strong>Dados do Imóvel:</strong></td>
							</tr>
							
							<tr bgcolor="#cbe5fe">
								<td width="100%" align="center">
	
									<table width="100%" border="0">
									
										<tr>
											<td><strong>Matrícula:</strong></td>
											<td colspan="3" align="right">
												<div align="left">
													<html:text property="idImovel"
														readonly="true" 
														style="background-color:#EFEFEF; border:0"
														size="9" 
														maxlength="9" />
												</div>
											</td>
										</tr>
		
										<tr>
											<td><strong>Inscri&ccedil;&atilde;o:</strong></td>
											<td colspan="3" align="right">
												<div align="left">
													<html:text property="inscricaoImovel"
														readonly="true" 
														style="background-color:#EFEFEF; border:0"
														size="20" 
														maxlength="20" />
												</div>
											</td>
										</tr>
										<tr>
											<td><strong>Nome do Cliente Usu&aacute;rio:</strong></td>
		
											<td colspan="3" align="right">
												<div align="left">
													<a href="usuario_pesquisar.htm"> </a> 
													<span class="style1"> 
														<html:text property="nomeClienteUsuario"
															readonly="true" 
															style="background-color:#EFEFEF; border:0"
															size="50" 
															maxlength="45" />
													</span>
												</div>
											</td>
										</tr>
										<tr>
											<td><strong>Nome do Cliente Responsável:</strong></td>
		
											<td colspan="3" align="right">
												<div align="left">
													<a href="usuario_pesquisar.htm"> </a> 
													<span class="style1"> 
														<html:text property="nomeClienteResponsavel"
															readonly="true" 
															style="background-color:#EFEFEF; border:0"
															size="50" 
															maxlength="45" />
													</span>
												</div>
											</td>
										</tr>
										<tr>
											<td><strong> Situa&ccedil;&atilde;o de &Aacute;gua: </strong></td>
		
											<td align="right" colspan="3">
												<div align="left">
													<html:text property="situacaoAgua"
														readonly="true" 
														style="background-color:#EFEFEF; border:0"
														size="20" 
														maxlength="20" />
												</div>
											</td>
										</tr>
										<tr>
											<td align="left">
											<div align="left"><strong> Situa&ccedil;&atilde;o de Esgoto:</strong></div>
											</td>
											<td align="left" colspan="3">
												<html:text property="situacaoEsgoto" 
												readonly="true"
												style="background-color:#EFEFEF; border:0" 
												size="20"
												maxlength="20" />
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				
				<tr>
					<td colspan="4" height="10"></td>
				</tr>
				
				<tr>
					<td colspan="4">

					<table width="100%" align="center" bgcolor="#99CCFF">
						<tr>
							<td><strong>Dados do Cliente:</strong></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td width="100%" align="center">

							<table width="100%" border="0">
							
								<tr>
									<td width="180"><strong>Código:</strong></td>

									<td colspan="3" align="left">
										<html:text property="codigoCliente"
											readonly="true" 
											style="background-color:#EFEFEF; border:0"
											size="9" 
											maxlength="9" />
									</td>
								</tr>
								<tr>
									<td width="180"><strong>CPF/CNPJ:</strong></td>

									<td colspan="3" align="left">
										<html:text property="cpf"
											readonly="true" 
											style="background-color:#EFEFEF; border:0"
											size="20" 
											maxlength="17" />
									</td>
								</tr>
								<tr>
									<td width="180"><strong>Nome do Cliente:</strong></td>
									<td colspan="3" align="left">
										<div align="left">
											<html:text property="nomeCliente"
												readonly="true" 
												style="background-color:#EFEFEF; border:0"
												size="50" 
												maxlength="45" />
										</div>
									</td>
								</tr>
								<tr>
									<td width="180"><strong> Profiss&atilde;o: </strong></td>

									<td align="left" colspan="3">
										<html:text property="profissao"
											readonly="true" 
											style="background-color:#EFEFEF; border:0"
											size="30" 
											maxlength="30" />
									</td>
								</tr>

								<tr>
									<td width="180"><strong> Ramo de Atividade:</strong></td>
									<td align="left" colspan="3">
										<html:text property="ramoAtividade" 
											readonly="true"
											style="background-color:#EFEFEF; border:0" 
											size="20"
											maxlength="20" />
									</td>

								</tr>
							</table>
							</td>
						</tr>
					</table>
					</td>
				</tr>
				
				<tr>
					<td colspan="4" height="10"></td>
				</tr>
				
				<tr>
					<td colspan="4">

						<table width="100%" align="center" bgcolor="#99CCFF" border="0"
							cellpadding="0" cellspacing="0">
							<tr>
								<td><strong>Guias de Pagamento:</strong></td>
							</tr>
							<tr bgcolor="#cbe5fe">
								<td width="100%" align="center">
	
								<table width="100%" cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td></td>
									</tr>
									<tr>
										<td colspan="10" bgcolor="#000000" height="2"></td>
									</tr>
	
									<tr bordercolor="#000000">
										<td width="8%" bgcolor="#90c7fc" align="center">
										<div align="center"><strong><a
											href="javascript:facilitador(this);">Todos</a></strong></div></td>									
										<td width="12%" bgcolor="#90c7fc" align="center"><strong>Tipo do Documento</strong></td>
										<td width="10%" bgcolor="#90c7fc" align="center"><strong>Guia</strong></td>
										<td width="10%" bgcolor="#90c7fc" align="left"><strong>Prestação</strong></td>
										
										<logic:present name="exibirNuContratoParcOrgaoPublico" scope="session">
											<td width="12%" bgcolor="#90c7fc" align="center"><strong>No. Contrato Parcel. Órgão Público</strong></td>
										</logic:present>
										
										<td width="12%" bgcolor="#90c7fc" align="center"><strong>Valor da Prestação</strong></td>
										<td width="12%" bgcolor="#90c7fc" align="center"><strong>Emissão</strong></td>
										<td width="12%" bgcolor="#90c7fc" align="center"><strong>Vencimento</strong></td>
										<td width="8%" bgcolor="#90c7fc" align="center"><strong>Paga</strong></td>
										<td width="10%" bgcolor="#90c7fc" align="center"><strong>Situação</strong></td>
									</tr>
	
									<tr>
										<td colspan="10"> 
										<div style="width: 100%; height: 100; overflow: auto;">
										<table width="100%" bgcolor="#99CCFF">
													<logic:present name="colecaoGuiasPrestacoes">
													<%int cont = 0;%>
													<logic:iterate name="colecaoGuiasPrestacoes" id="guiaPagamentoPrestacao" type="GuiaPagamentoPrestacaoHelper">
															<%cont = cont + 1;
		
															if (cont % 2 == 0) {%>
																	<tr bgcolor="#cbe5fe">
															<%} else { %>
																	<tr bgcolor="#FFFFFF">
															<%}%>
															
															<%
				
																GuiaPagamentoPrestacaoHelper guiaHelperTemp = ((GuiaPagamentoPrestacaoHelper) guiaPagamentoPrestacao);
																
																String corSituacaoGuia = "#000000"; // PRETO
																String corSituacaoGuiaPrescrita = "#000000"; // PRETO
																if(guiaHelperTemp.getIdOcorrenciaHistorico() != null){
																	
																	corSituacaoGuia = "#ff0000"; // VERMELHO 
																	corSituacaoGuiaPrescrita= "#ff0000"; // VERMELHO
																}
																
																if(guiaHelperTemp.getIdDebitoCreditoSituacao().toString().equals(DebitoCreditoSituacao.PRESCRITA.toString())){
																	
																	corSituacaoGuiaPrescrita = "#ff0000"; // VERMELHO 
																}
															%>
															
																<td width="8%"><div align="center"><input type="checkbox" name="idRegistrosRemocao" value='<bean:write name="guiaPagamentoPrestacao" property="idGuiaPagamento" /><bean:write name="guiaPagamentoPrestacao" property="numeroPrestacao" />' /></div></td>

																<td width="15%" align="left">
																	<a href="javascript:abrirPopup('exibirConsultarGuiaPagamentoAction.do?guiaPagamentoId=<bean:write name="guiaPagamentoPrestacao" property="idGuiaPagamento" />', 600, 900);">
																		<font color="<%=corSituacaoGuia %>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
																			<bean:write name="guiaPagamentoPrestacao" property="descricaoDocumentoTipo" />&nbsp;
																		</font>
																	</a>
																</td>

																<td width="9%" align="center">
																	<font color="<%=corSituacaoGuia %>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
																		<bean:write name="guiaPagamentoPrestacao" property="idGuiaPagamento" />
																	</font>
																</td>
																
																<td width="11%" align="center" title="${guiaPagamentoPrestacao.debitoTipoHint}">
																	<font color="<%=corSituacaoGuia %>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
																		<bean:write name="guiaPagamentoPrestacao" property="numeroPrestacao" /> / <bean:write name="guiaPagamentoPrestacao" property="numeroPrestacaoTotal" />
																	</font>
																</td>
																
																<logic:present name="exibirNuContratoParcOrgaoPublico" scope="session">
																	
																	<td width="15%" align="center">
																		<font color="<%=corSituacaoGuia %>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
																			<bean:write name="guiaPagamentoPrestacao" property="numeroContratoParcelOrgaoPublico" />
																		</font>
																	</td>
																	
																</logic:present>

																<td width="12%" align="center">
																	<font color="<%=corSituacaoGuia %>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
																		<bean:write name="guiaPagamentoPrestacao" property="valorTotalPorPrestacao" formatKey="money.format" />
																	</font>
																</td>
																
																
																<td width="13%" align="center">
																	<font color="<%=corSituacaoGuia %>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
																		<bean:write name="guiaPagamentoPrestacao" property="dataEmissao" formatKey="date.format" />
																	</font>
																</td>
																
																
																<td width="13%" align="center">
																	<font color="<%=corSituacaoGuia %>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
																		<bean:write name="guiaPagamentoPrestacao" property="dataVencimento" formatKey="date.format" />
																	</font>
																</td>
																
																<td width="8%" align="center" title="${guiaPagamentoPrestacao.indicadorPagamentoHint}">
																	<font color="<%=corSituacaoGuia %>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
																		<bean:write name="guiaPagamentoPrestacao" property="dsIndicadorPagamento" />
																	</font>
																</td>
																

																<td width="10%" align="center">
																	<font color="<%=corSituacaoGuiaPrescrita %>" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
																		<bean:write name="guiaPagamentoPrestacao" property="descricaoDebitoCreditoSituacao" />
																	</font>
																</td>
																
															</tr>
													</logic:iterate>
												</logic:present>
										</table>
										</div>
										</td>
									</tr>
								</table>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="9">
						<div align="left"><font color="#ff0000">Prestações já baixadas para o histórico ou Prescritas</font></div>
					</td>
				</tr>

	      		<tr>
	      			<td colspan="2" width="100%">
	      			<table width="100%">
						<tr>
							<td width="40%" align="left">
								  
								<input type="button"
									name="ButtonCancelar" 
									class="bottonRightCol" 
									value="Voltar"
									onClick="window.history.go(-1)"> 
								<input type="button"
									name="ButtonReset" 
									class="bottonRightCol" 
									value="Desfazer"
									onClick="window.location.href='/gsan/exibirFiltrarGuiaPagamentoAction.do?idRegistroAtualizacao=<bean:write name="AtualizarGuiaPagamentoActionForm" property="idRegistroAtualizacao" />'"> 
								<input type="button"
									name="ButtonCancelar" 
									class="bottonRightCol" 
									value="Cancelar"
									onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
							</td>
							<td align="right">
								<input type="button" 
								 	name="" 
								 	value="Cancelar Guia(s) de Pagamento" 
								 	class="bottonRightCol" 
									onclick="verficarSelecaoCancelar(document.AtualizarGuiaPagamentoActionForm.idRegistrosRemocao)"
									style="width:200px"/>
								  
								 <input type="button" 
								 	name="" 
								 	value="Imprimir Guia(s) de Pagamento" 
								 	class="bottonRightCol" 
									onclick="verficarSelecaoImprimir(document.AtualizarGuiaPagamentoActionForm.idRegistrosRemocao)"
									style="width:200px"/>
									
								<input type="button" 
								 	name="" 
								 	value="Alterar Vencimento Prestações" 
								 	class="bottonRightCol" 
									onclick="abrirPopup('exibirAlterarVencimentoPrestacoesGuiaPagamentoAction.do')"
									style="width:200px"/>
							</td>
						</tr>
		      			
	      			</table>
	      			</td>
	      		</tr>
      	</table>
        <p>&nbsp;</p>
	</td>
  </tr>
</table>

<%@ include file="/jsp/util/rodape.jsp"%>

</html:form>


</body>

<!-- /faturamento/guiadepagamento/guia_pagamento_atualizar.jsp -->
</html:html>
