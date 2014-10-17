<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ page import="gcom.util.ConstantesSistema"%>
<%@ page import="gcom.cobranca.CobrancaDocumentoItem" %>
<%@ page import="gcom.arrecadacao.pagamento.GuiaPagamentoPrestacao" %>
<%@ page import="gcom.util.Util" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">


<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<script language="JavaScript">
	function email(){
		this.aa = new Array("emailNegociacao", "E-mail inv�lido.", new Function ("varName", " return this[varName];"));
	}

	function required(){
		this.aa = new Array("emailNegociacao", "� necess�rio informar o e-mail para o re-envio do documento da negocia��o.", new Function ("varName", " return this[varName];"));
	}

	function reEnviarDocumento(){
		var form = document.forms[0];

		if(validateRequired(form) && validateEmail(form)){
			form.action = 'exibirDocumentoCobrancaItemAction.do?cobrancaDocumentosID=<%=request.getParameter("cobrancaDocumentosID")%>&reEnviarDocumento=S';
			form.submit();
		}
	}
</script>

</head>

<body leftmargin="5" topmargin="5" onload="javascript:setarFoco('${requestScope.nomeCampo}');resizePageSemLink(660,650);">

<html:form action="/exibirApresentarItensDocumentoCobrancaAction" method="post"
	type="gcom.gui.cadastro.imovel.DocumentoCobrancaItemActionForm"
	name="DocumentoCobrancaItemActionForm">

<html:hidden property="idCobrancaNegociacaoAtendimento"/>
<html:hidden property="habilitarReEnviarDocumento"/>

<table width="610" border="0" cellspacing="5" cellpadding="0">
  <tr>

	<td width="600" valign="top" class="centercoltext">

        <table height="100%">
        <tr>
          <td></td>
        </tr>
      	</table>

      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
          <td class="parabg">Consultar Itens do Documento de Cobran�a</td>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
        </tr>
      </table>
      <p>&nbsp;</p>

<!-- ============================================================================================================================== -->

      <table width="100%" border="0">
      <tr> 
          <td colspan="5">
				
				<table width="100%" align="center" bgcolor="#99CCFF" border="0">
				<tr>
					<td>
					<strong>Dados do Im�vel:</strong>

					</td>
				</tr>
				<tr bgcolor="#cbe5fe">
					<td width="100%" align="center" colspan="2">
			
						<table width="100%" border="0">
						<tr> 
							<td height="10" width="150"><strong>Matr�cula:</strong></td>
							<td>
								<html:text property="matriculaImovel" size="25" readonly="true" style="background-color:#EFEFEF; border:0"/>
							</td>
						</tr>
						<tr> 
							<td height="10" width="150"><strong>Inscri��o:</strong></td>
							<td>
								<html:text property="inscricaoImovel" size="25" readonly="true" style="background-color:#EFEFEF; border:0"/>
							</td>
						</tr>
						<tr> 
							<td height="10"><strong>Situa��o de �gua:</strong></td>
							<td>
								<html:text property="situacaoAguaImovel" size="45" readonly="true" style="background-color:#EFEFEF; border:0"/>
							</td>
						</tr>
						<tr> 
							<td height="10"><strong>Situa��o de Esgoto:</strong></td>
							<td>
								<html:text property="situacaoEsgotoImovel" size="45" readonly="true" style="background-color:#EFEFEF; border:0"/>
							</td>
						</tr>
						<tr> 
							<td height="10" colspan="3">
							
								<table width="100%" bgcolor="#99CCFF" border="0">
                                 <tr bgcolor="#79BBFD">
                                   	<td align="center"><strong>Endere&ccedil;o</strong></td>
                                 </tr>
                                 <tr>
                                	<td bgcolor="#FFFFFF">
										<div align="center"><bean:write name="cobrancaDocumentoHelper" property="cobrancaDocumento.imovel.enderecoFormatado" scope="request"/></div>
									</td>
								</tr>
								</table>
								
							</td>
						</tr>
						</table>

					</td>
				</tr>
				</table>
		  </td>
      </tr>
      <tr>
      	<td colspan="5" height="10"></td>
      </tr>
      <tr>
      	<td colspan="4" height="10">
      		<table width="100%" align="center" bgcolor="#99CCFF" border="0">
				<tr>
					<td><strong>Empresa Respons�vel:</strong></td>
				</tr>
				<tr bgcolor="#cbe5fe">
					<td width="100%" align="center">
			
						<table width="100%" border="0">
						<tr> 
					        <td width="150">
								<html:text property="empresaNome" size="85" readonly="true" style="background-color:#EFEFEF; border:0;"/>
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
      	<td colspan="5">
      		<table width="100%" border="0">
			<tr> 
				<td height="10" width="155"><strong>Sequencial Documento:</strong></td>
		        <td>
					<html:text property="sequencial" size="10" readonly="true" style="background-color:#EFEFEF; border:0;"/>
		        </td>
		        <td colspan="3" width="380"></td>
			</tr>
			<tr>
				<td height="10"><strong>Vl. Documento:</strong></td>
		        <td>
					<html:text property="valorDocumento" size="10" readonly="true" style="background-color:#EFEFEF; border:0; text-align: right;"/>
		        </td>
		        <td height="10"><strong>Vl. Desconto:</strong></td>
		        <td>
					<html:text property="valorDesconto" size="10" readonly="true" style="background-color:#EFEFEF; border:0; text-align: right;"/>
		        </td>
			</tr>
			<tr> 
				<td height="10" width="155"><strong>Mot. N�o Entrega do Documento:</strong></td>
		        <td>
					<html:text property="motivoNaoEntregaDocumento" size="25" readonly="true" style="background-color:#EFEFEF; border:0;"/>
		        </td>
		        <td colspan="3" width="380"></td>
			</tr>
			<tr> 
				<td height="10" width="155"><strong>Parecer da Entrega/Devolu��o:</strong></td>
		        <td>
					<html:textarea property="descricaoParecer" cols="50" rows="4" readonly="true" style="background-color:#EFEFEF; border:0; overflow: auto;"/>
		        </td>
		        <td colspan="2" width="380"></td>
			</tr>
			<tr> 
				<td height="10" width="155"><strong>Qtde. Itens:</strong></td>
		        <td>
					<html:text property="qtdItens" size="10" readonly="true" style="background-color:#EFEFEF; border:0;"/>
		        </td>
		        <td colspan="3" width="380"></td>
			</tr>
			<tr> 
				<td height="10" width="155"><strong>Situa��o do Documento:</strong></td>
		        <td>
					<html:text property="descricaoCobrancaAcaoSituacao" size="30" readonly="true" style="background-color:#EFEFEF; border:0;"/>
		        </td>
		        <td colspan="2" width="380"></td>
			</tr>
			<tr> 
				<td height="10" width="155"><strong>Data de Entrega:</strong></td>
		        <td>
				<html:text property="dataEntrega" size="8" readonly="true" style="background-color:#EFEFEF; border:0;"/>
		        </td>
		        <td colspan="2" width="380"></td>
			</tr>
			</table>
      	</td>
      </tr>
      <tr>
      	<td colspan="4" height="10"></td>
      </tr>
      
      <logic:notEmpty name="DocumentoCobrancaItemActionForm" property="tipoDocumentoNegociacao">
	      <tr>
	          <td colspan="3">
					<table width="100%" align="center" bgcolor="#99CCFF" border="0">
					<tr>
						<td><strong>Negocia��o de D�bito:</strong></td>
					</tr>
					<tr bgcolor="#cbe5fe">
						<td width="100%" align="center">
							<table width="100%" border="0">
							<tr>
								<td height="10" width="150"><strong>Tipo do Documento:</strong></td>
						        <td colspan="2">
									<html:text property="tipoDocumentoNegociacao" size="25" readonly="true" style="background-color:#EFEFEF; border:0;"/>
						        </td>
							</tr>
							<tr>
						        <td height="10" width="150"><strong>RA - Registro de Atendimento:</strong></td>
						        <td colspan="2">
									<html:text property="idRegistroAtendimentoNegociacao" size="10" readonly="true" style="background-color:#EFEFEF; border:0;"/>
									<a href="javascript:abrirPopup('exibirConsultarRegistroAtendimentoPopupAction.do?numeroRA=<bean:write name="DocumentoCobrancaItemActionForm" property="idRegistroAtendimentoNegociacao"/>&caminhoTelaPesquisaRetorno=S', 400, 600 );"><img width="23" height="21" border="0" 
										src="<bean:message key="caminho.imagens"/>pesquisa_verde.gif" title="Consultar RA - Registro de Atendimento"/></a>
						        </td>
							</tr>
							<tr>
						        <td height="10" width="150"><strong>E-mail:</strong></td>
						        <logic:notEmpty name="DocumentoCobrancaItemActionForm" property="habilitarReEnviarDocumento">
						        	<logic:equal name="DocumentoCobrancaItemActionForm" property="habilitarReEnviarDocumento" value="<%=""+ConstantesSistema.SIM%>">
								        <td>
											<html:text property="emailNegociacao" size="33" maxlength="40"/>
								        </td>
								        <td>
								        	<input name="SubmitEmail" class="bottonRightCol" value="Reenviar Documento" type="button" onclick="javascript:reEnviarDocumento();">
								        </td>
						        	</logic:equal>

						        	<logic:equal name="DocumentoCobrancaItemActionForm" property="habilitarReEnviarDocumento" value="<%=""+ConstantesSistema.NAO%>">
								        <td>
											<html:text property="emailNegociacao" size="33" readonly="true" style="background-color:#EFEFEF; border:0;"/>
								        </td>
								        <td>
								        	<input name="SubmitEmail" class="bottonRightCol" value="Reenviar Documento" type="button" disabled="disabled"">
								        </td>
						        	</logic:equal>
						        </logic:notEmpty>

						        <logic:empty name="DocumentoCobrancaItemActionForm" property="habilitarReEnviarDocumento">
							        <td>
										<html:text property="emailNegociacao" size="33" readonly="true" style="background-color:#EFEFEF; border:0;"/>
							        </td>
							        <td>
							        	<input name="SubmitEmail" class="bottonRightCol" value="Reenviar Documento" type="button" disabled="disabled"">
							        </td>
						        </logic:empty>
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
      </logic:notEmpty>

      <tr> 
          <td colspan="5">
				
				<table width="100%" align="center" bgcolor="#99CCFF" border="0">
				<tr>
					<td><strong>Emiss�o:</strong></td>
				</tr>
				<tr bgcolor="#cbe5fe">
					<td width="100%" align="center">
			
						<table width="100%" border="0">
						<tr> 
							<td height="10" width="150"><strong>Forma:</strong></td>
					        <td width="150">
								<html:text property="formaEmissao" size="25" readonly="true" style="background-color:#EFEFEF; border:0;"/>
					        </td>
					        <td height="10" width="110"><strong>Data/Hora:</strong></td>
					        <td>
								<html:text property="dataHoraEmissao" size="17" readonly="true" style="background-color:#EFEFEF; border:0;"/>
					        </td>
						</tr>						
						</table>

					</td>
				</tr>
				</table>
		  </td>
      </tr>
      
      <%String cor = "#FFFFFF";%>
      
      <tr>
      	<td colspan="5" height="10"></td>
      </tr>
      <tr>
      	<td colspan="5">
      	
      		<table width="100%" border="0">
	  		<tr> 
          		<td height="17" colspan="5"><strong>Contas:</strong></td>
          	</tr>
      		<tr> 
          		<td colspan="5">
		  
					<table width="100%" cellpadding="0" cellspacing="0">
					<tr> 
                		<td> 
					
							<table width="100%" bgcolor="#99CCFF" border="0">
                    		<tr bgcolor="#99CCFF"> 

								<td align="center" width="10%"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><strong>M�s/Ano</strong></font></td>
								<td width="15%"><div align="center"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><strong>Vencimento</strong></font></div></td>
								<td width="10%"><div align="center"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><strong>�gua</strong></font></div></td>
								<td width="10%"><div align="center"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><strong>Esgoto</strong></font></div></td>
								<td width="10%"><div align="center"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><strong>D�bitos</strong></font></div></td>
								<td width="10%"><div align="center"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><strong>Cr�ditos</strong></font></div></td>
								<td width="10%"><div align="center"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><strong>Vl. Conta</strong></font></div></td>
								<td width="15%"><div align="center"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><strong>Situa��o</strong></font></div></td>
								<td width="10%"><div align="center"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><strong>Vl. Item Cobrado</strong></font></div></td>
							</tr>
                    		</table>
					
						
            		</tr>
            		
            		<logic:notEmpty name="cobrancaDocumentoHelper" property="colecaoCobrancaDocumentoItemConta">
            
            		<tr> 
						<td> 
					
							<div style="width: 100%; height: 100; overflow: auto;">	
							
							<%cor = "#FFFFFF";%>
							
							<table width="100%" align="center" bgcolor="#99CCFF">

								<logic:iterate name="cobrancaDocumentoHelper" property="colecaoCobrancaDocumentoItemConta" id="cobrancaDocumentoItemConta" type="CobrancaDocumentoItem">
                            
									
									
									<%	if (cor.equalsIgnoreCase("#FFFFFF")){
											cor = "#cbe5fe";%>
											<tr bgcolor="#FFFFFF">
									<%} else{
											cor = "#FFFFFF";%>
											<tr bgcolor="#cbe5fe">
									<%}%>
										<td width="10%">
										<div align="center">

											<logic:present name="cobrancaDocumentoItemConta" property="contaGeral">	
												<bean:define name="cobrancaDocumentoItemConta" property="contaGeral" id="contaGeral"/>
													<logic:present name="contaGeral" property="conta">
														<bean:define name="contaGeral" property="conta" id="conta"/>
															<logic:present name="conta" property="referencia">	
																	<a href="javascript:abrirPopup('exibirConsultarContaAction.do?tipoConsulta=conta&contaID=<%="" + cobrancaDocumentoItemConta.getContaGeral().getConta().getId()%>' , 600, 800);" title="<%="" + Util.formatarMesAnoReferencia(cobrancaDocumentoItemConta.getContaGeral().getConta().getReferencia())%>"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><%=""+ Util.formatarMesAnoReferencia(cobrancaDocumentoItemConta.getContaGeral().getConta().getReferencia())%></font></a>
															</logic:present>

													</logic:present>	
													<logic:present name="contaGeral" property="contaHistorico">
															<bean:define name="contaGeral" property="contaHistorico" id="contaHistorico"/>
															<logic:present name="contaHistorico" property="anoMesReferenciaConta">	
																<a href="javascript:abrirPopup('exibirConsultarContaAction.do?tipoConsulta=contaHistorico&contaID=<%="" + cobrancaDocumentoItemConta.getContaGeral().getContaHistorico().getId()%>' , 600, 800);" title="<%="" + Util.formatarMesAnoReferencia(cobrancaDocumentoItemConta.getContaGeral().getContaHistorico().getAnoMesReferenciaConta())%>"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><%=""+ Util.formatarMesAnoReferencia(cobrancaDocumentoItemConta.getContaGeral().getContaHistorico().getAnoMesReferenciaConta())%></font></a>
															</logic:present>
													</logic:present>
											</logic:present>
										</div>
										</td>
										<td width="15%">
										<div align="center">

											<logic:present name="cobrancaDocumentoItemConta" property="contaGeral">	
												<bean:define name="cobrancaDocumentoItemConta" property="contaGeral" id="contaGeral"/>
													<logic:present name="contaGeral" property="conta">
														<bean:define name="contaGeral" property="conta" id="conta"/>
															<logic:present name="conta" property="dataVencimentoConta">	
																<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="conta" property="dataVencimentoConta" formatKey="date.format" /></font>
															</logic:present>
													</logic:present>	
													
													<logic:present name="contaGeral" property="contaHistorico">
															<bean:define name="contaGeral" property="contaHistorico" id="contaHistorico"/>
															<logic:present name="contaHistorico" property="dataVencimentoConta">	
																<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="contaHistorico" property="dataVencimentoConta" formatKey="date.format" /></font>
															</logic:present>
													</logic:present>	
											</logic:present>
										</div>
										</td>
										<td width="10%">
										<div align="right">
											<logic:present name="cobrancaDocumentoItemConta" property="contaGeral">	
												<bean:define name="cobrancaDocumentoItemConta" property="contaGeral" id="contaGeral"/>
													<logic:present name="contaGeral" property="conta">
														<bean:define name="contaGeral" property="conta" id="conta"/>
															<logic:present name="conta" property="valorAgua">	
																<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="conta" property="valorAgua" formatKey="money.format" /></font>
															</logic:present>

													</logic:present>	
													<logic:present name="contaGeral" property="contaHistorico">
															<bean:define name="contaGeral" property="contaHistorico" id="contaHistorico"/>
															<logic:present name="contaHistorico" property="valorAgua">	
																<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="contaHistorico" property="valorAgua" formatKey="money.format" /></font>
															</logic:present>
													</logic:present>	
											</logic:present>
										</div>
										</td>
										<td width="10%">
										<div align="right">
										
											<logic:present name="cobrancaDocumentoItemConta" property="contaGeral">	
												<bean:define name="cobrancaDocumentoItemConta" property="contaGeral" id="contaGeral"/>
													<logic:present name="contaGeral" property="conta">
														<bean:define name="contaGeral" property="conta" id="conta"/>
															<logic:present name="conta" property="valorEsgoto">	
																<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="conta" property="valorEsgoto" formatKey="money.format" /></font>
															</logic:present>

													</logic:present>	
													<logic:present name="contaGeral" property="contaHistorico">
															<bean:define name="contaGeral" property="contaHistorico" id="contaHistorico"/>
															<logic:present name="contaHistorico" property="valorEsgoto">	
																<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="contaHistorico" property="valorEsgoto" formatKey="money.format" /></font>
															</logic:present>
													</logic:present>	
											</logic:present>
											
										</div>
										</td>
										<td width="10%">
										<div align="right">

											<logic:present name="cobrancaDocumentoItemConta" property="contaGeral">	
												<bean:define name="cobrancaDocumentoItemConta" property="contaGeral" id="contaGeral"/>
													<logic:present name="contaGeral" property="conta">
														<bean:define name="contaGeral" property="conta" id="conta"/>
															<logic:notEqual name="conta" property="debitos" value="0">
																<a href="javascript:abrirPopup('exibirConsultarDebitoCobradoAction.do?tipoConsulta=conta&contaID=<%="" + cobrancaDocumentoItemConta.getContaGeral().getConta().getId()%>' , 500, 800);" title="<%="" + Util.formatarMoedaReal(cobrancaDocumentoItemConta.getContaGeral().getConta().getDebitos())%>"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="conta" property="debitos" formatKey="money.format" /></font></a>
															</logic:notEqual>
															
															<logic:equal name="conta" property="debitos" value="0">
																<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="conta" property="debitos" formatKey="money.format" /></font>
															</logic:equal>	

													</logic:present>

													<logic:present name="contaGeral" property="contaHistorico">
															<bean:define name="contaGeral" property="contaHistorico" id="contaHistorico"/>

															<logic:notEqual name="contaHistorico" property="valorDebitos" value="0">
																<a href="javascript:abrirPopup('exibirConsultarDebitoCobradoAction.do?tipoConsulta=contaHistorico&contaID=<%="" + cobrancaDocumentoItemConta.getContaGeral().getContaHistorico().getId()%>' , 500, 800);" title="<%="" + Util.formatarMoedaReal(cobrancaDocumentoItemConta.getContaGeral().getContaHistorico().getValorDebitos())%>"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="contaHistorico" property="valorDebitos" formatKey="money.format" /></font></a>
															</logic:notEqual>
															
															<logic:equal name="contaHistorico" property="valorDebitos" value="0">
																<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="contaHistorico" property="valorDebitos" formatKey="money.format" /></font>
															</logic:equal>	
															
													</logic:present>	
											</logic:present>
											
										</div>
										</td>
										<td width="10%">
										<div align="right">
											
											<logic:present name="cobrancaDocumentoItemConta" property="contaGeral">	
												<bean:define name="cobrancaDocumentoItemConta" property="contaGeral" id="contaGeral"/>
													<logic:present name="contaGeral" property="conta">
														<bean:define name="contaGeral" property="conta" id="conta"/>
															<logic:notEqual name="conta" property="valorCreditos" value="0">
																<a href="javascript:abrirPopup('exibirConsultarCreditoRealizadoAction.do?tipoConsulta=conta&contaID=<%="" + cobrancaDocumentoItemConta.getContaGeral().getConta().getId()%>' , 500, 800);" title="<%="" + Util.formatarMoedaReal(cobrancaDocumentoItemConta.getContaGeral().getConta().getValorCreditos())%>"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="conta" property="valorCreditos" formatKey="money.format" /></font></a>
															</logic:notEqual>
															
															<logic:equal name="conta" property="valorCreditos" value="0">
																<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="conta" property="valorCreditos" formatKey="money.format" /></font>
															</logic:equal>

													</logic:present>

													<logic:present name="contaGeral" property="contaHistorico">
															<bean:define name="contaGeral" property="contaHistorico" id="contaHistorico"/>

															<logic:notEqual name="contaHistorico" property="valorCreditos" value="0">
																<a href="javascript:abrirPopup('exibirConsultarCreditoRealizadoAction.do?tipoConsulta=contaHistorico&contaID=<%="" + cobrancaDocumentoItemConta.getContaGeral().getContaHistorico().getId()%>' , 500, 800);" title="<%="" + Util.formatarMoedaReal(cobrancaDocumentoItemConta.getContaGeral().getContaHistorico().getValorCreditos())%>"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="contaHistorico" property="valorCreditos" formatKey="money.format" /></font></a>
															</logic:notEqual>
															
															<logic:equal name="contaHistorico" property="valorCreditos" value="0">
																<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="contaHistorico" property="valorCreditos" formatKey="money.format" /></font>
															</logic:equal>

													</logic:present>
											</logic:present>
										
											
										</div>
										</td>
										<td width="10%">
										<div align="right">

											<logic:present name="cobrancaDocumentoItemConta" property="contaGeral">	
												<bean:define name="cobrancaDocumentoItemConta" property="contaGeral" id="contaGeral"/>
													<logic:present name="contaGeral" property="conta">
														<bean:define name="contaGeral" property="conta" id="conta"/>
															<logic:present name="conta" property="valorTotal">	
																<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="conta" property="valorTotal" formatKey="money.format" /></font>
															</logic:present>

													</logic:present>	

													<logic:present name="contaGeral" property="contaHistorico">
															<bean:define name="contaGeral" property="contaHistorico" id="contaHistorico"/>
																<logic:present name="contaHistorico" property="valorTotal">	
																	<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="contaHistorico" property="valorTotal" formatKey="money.format" /></font>
																</logic:present>
													</logic:present>
											</logic:present>
										</div>
										</td>
										<td width="15%">
										<div align="center">
										
											<logic:present name="cobrancaDocumentoItemConta" property="contaGeral">	
												<bean:define name="cobrancaDocumentoItemConta" property="contaGeral" id="contaGeral"/>
													<logic:present name="contaGeral" property="conta">
														<bean:define name="contaGeral" property="conta" id="conta"/>
															<logic:present name="conta" property="debitoCreditoSituacaoAtual">	
																<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="conta" property="debitoCreditoSituacaoAtual.descricaoDebitoCreditoSituacao"/></font>
															</logic:present>

													</logic:present>
													<logic:present name="contaGeral" property="contaHistorico">
															<bean:define name="contaGeral" property="contaHistorico" id="contaHistorico"/>
															<logic:present name="contaHistorico" property="debitoCreditoSituacaoAtual">	
																<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="contaHistorico" property="debitoCreditoSituacaoAtual.descricaoDebitoCreditoSituacao"/></font>
															</logic:present>
													
													</logic:present>
											</logic:present>											
										</div>
										</td>
										<td width="10%">
										<div align="right">

											<logic:present name="cobrancaDocumentoItemConta" property="valorItemCobrado">
												<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="cobrancaDocumentoItemConta" property="valorItemCobrado" formatKey="money.format" /></font>
											</logic:present>
										
											<logic:notPresent name="cobrancaDocumentoItemConta" property="valorItemCobrado">
												&nbsp;
											</logic:notPresent>
											
										</div>
										</td>
									 
									 
									</tr>
									

								</logic:iterate>
								
								

								
								
								</table>

							</div>
						</td>
            		</tr>
            		
            		</logic:notEmpty>
            		
            		</table>
				</td>
			</tr>
			
			</table>
			
      	</td>
      </tr>


      <tr>
      	<td colspan="5" height="5"></td>
      </tr>
      <tr>
      	<td colspan="5">
      	
      		<table width="100%" border="0">
	  		<tr> 
          		<td height="17" colspan="5"><strong>D�bitos A Cobrar:</strong></td>
          	</tr>
      		<tr> 
          		<td colspan="5">
		  
					<table width="100%" cellpadding="0" cellspacing="0">
					<tr> 
                		<td> 
					
							<table width="100%" bgcolor="#99CCFF" border="0">
                    		<tr bgcolor="#99CCFF"> 

								<td align="center" width="30%"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><strong>Tipo do D�bito</strong></font></td>
								<td width="10%"><div align="center"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><strong>M�s/Ano Refer�ncia</strong></font></div></td>
								<td width="10%"><div align="center"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><strong>M�s/Ano Cobran�a</strong></font></div></td>
								<td width="10%"><div align="center"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><strong>Parcela</strong></font></div></td>
								<td width="20%"><div align="center"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><strong>Vl. Parcela</strong></font></div></td>
								<td width="20%"><div align="center"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><strong>Vl. Item Cobrado</strong></font></div></td>
							</tr>
                    		</table>
					
						
            		</tr>
            		
            		<logic:notEmpty name="cobrancaDocumentoHelper" property="colecaoCobrancaDocumentoItemDebitoACobrar">
            
            		<tr> 
						<td> 
					
							<div style="width: 100%; height: 100; overflow: auto;">	
							
							<%cor = "#FFFFFF";%>
							
							<table width="100%" align="center" bgcolor="#99CCFF">

								<logic:iterate name="cobrancaDocumentoHelper" property="colecaoCobrancaDocumentoItemDebitoACobrar" id="cobrancaDocumentoItemDebitoACobrar" type="CobrancaDocumentoItem">
                            
									
									
									<%	if (cor.equalsIgnoreCase("#FFFFFF")){
											cor = "#cbe5fe";%>
											<tr bgcolor="#FFFFFF">
									<%} else{
											cor = "#FFFFFF";%>
											<tr bgcolor="#cbe5fe">
									<%}%>
									 
										<td width="30%">
										<div align="center">
											<logic:present name="cobrancaDocumentoItemDebitoACobrar" property="debitoACobrarGeral">	
											
												<bean:define name="cobrancaDocumentoItemDebitoACobrar" property="debitoACobrarGeral" id="debitoACobrarGeral"/>
													<logic:present name="debitoACobrarGeral" property="debitoACobrar">
													
														<bean:define name="debitoACobrarGeral" property="debitoACobrar" id="debitoACobrar"/>
															<logic:present name="debitoACobrar" property="debitoTipo">	
															
																<a href="javascript:abrirPopup('exibirConsultarDebitoACobrarAction.do?imovelID=${requestScope.imovel}&debitoID=<%="" + cobrancaDocumentoItemDebitoACobrar.getDebitoACobrarGeral().getDebitoACobrar().getId() %>' , 500, 800);" title="<%="" + cobrancaDocumentoItemDebitoACobrar.getDebitoACobrarGeral().getDebitoACobrar().getDebitoTipo().getDescricao() %>"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="debitoACobrar" property="debitoTipo.descricao"/></font></a>
															</logic:present>
														</logic:present>		

													<logic:present name="debitoACobrarGeral" property="debitoACobrarHistorico">
														<bean:define name="debitoACobrarGeral" property="debitoACobrarHistorico" id="debitoACobrarHistorico"/>
															<logic:present name="debitoACobrarHistorico" property="debitoTipo">	
																<a href="javascript:abrirPopup('exibirConsultarDebitoACobrarAction.do?imovelID=${requestScope.imovel}&debitoID=<%="" + cobrancaDocumentoItemDebitoACobrar.getDebitoACobrarGeral().getDebitoACobrarHistorico().getId() %>' , 500, 800);" title="<%="" + cobrancaDocumentoItemDebitoACobrar.getDebitoACobrarGeral().getDebitoACobrarHistorico().getDebitoTipo().getDescricao() %>"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="debitoACobrarHistorico" property="debitoTipo.descricao"/></font></a>
															</logic:present>
													</logic:present>
											</logic:present>
										</div>
										</td>
										<td width="10%">
										<div align="center">

											<logic:present name="cobrancaDocumentoItemDebitoACobrar" property="debitoACobrarGeral">	
												<bean:define name="cobrancaDocumentoItemDebitoACobrar" property="debitoACobrarGeral" id="debitoACobrarGeral"/>
													<logic:present name="debitoACobrarGeral" property="debitoACobrar">
														<bean:define name="debitoACobrarGeral" property="debitoACobrar" id="debitoACobrar"/>
															<logic:present name="debitoACobrar" property="anoMesReferenciaDebito">
																<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><%=""+ Util.formatarMesAnoReferencia(cobrancaDocumentoItemDebitoACobrar.getDebitoACobrarGeral().getDebitoACobrar().getAnoMesReferenciaDebito())%></font>
															</logic:present>
														
															<logic:notPresent name="debitoACobrar" property="anoMesReferenciaDebito">
																&nbsp;
															</logic:notPresent>
													</logic:present>												

													<logic:present name="debitoACobrarGeral" property="debitoACobrarHistorico">
														<bean:define name="debitoACobrarGeral" property="debitoACobrarHistorico" id="debitoACobrarHistorico"/>
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><%=""+ Util.formatarMesAnoReferencia(cobrancaDocumentoItemDebitoACobrar.getDebitoACobrarGeral().getDebitoACobrarHistorico().getAnoMesReferenciaDebito())%></font>
													</logic:present>
											</logic:present>
												
										</div>
										</td>
										<td width="10%">
										<div align="center">

											<logic:present name="cobrancaDocumentoItemDebitoACobrar" property="debitoACobrarGeral">	
												<bean:define name="cobrancaDocumentoItemDebitoACobrar" property="debitoACobrarGeral" id="debitoACobrarGeral"/>
													<logic:present name="debitoACobrarGeral" property="debitoACobrar">
														<bean:define name="debitoACobrarGeral" property="debitoACobrar" id="debitoACobrar"/>
															<logic:present name="debitoACobrar" property="anoMesCobrancaDebito">
																<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><%=""+ Util.formatarMesAnoReferencia(cobrancaDocumentoItemDebitoACobrar.getDebitoACobrarGeral().getDebitoACobrar().getAnoMesCobrancaDebito())%></font>
															</logic:present>
														
															<logic:notPresent name="debitoACobrar" property="anoMesCobrancaDebito">
																&nbsp;
															</logic:notPresent>

													</logic:present>		

													<logic:present name="debitoACobrarGeral" property="debitoACobrarHistorico">
														<bean:define name="debitoACobrarGeral" property="debitoACobrarHistorico" id="debitoACobrarHistorico"/>

															<logic:present name="debitoACobrarHistorico" property="anoMesCobrancaDebito">
																<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><%=""+ Util.formatarMesAnoReferencia(cobrancaDocumentoItemDebitoACobrar.getDebitoACobrarGeral().getDebitoACobrarHistorico().getAnoMesCobrancaDebito())%></font>
															</logic:present>
														
															<logic:notPresent name="debitoACobrarHistorico" property="anoMesCobrancaDebito">
																&nbsp;
															</logic:notPresent>


													</logic:present>
											</logic:present>
										</div>
										</td>
										<td width="10%">
										<div align="center">

											<logic:present name="cobrancaDocumentoItemDebitoACobrar" property="debitoACobrarGeral">	
												<bean:define name="cobrancaDocumentoItemDebitoACobrar" property="debitoACobrarGeral" id="debitoACobrarGeral"/>
													<logic:present name="debitoACobrarGeral" property="debitoACobrar">
														<bean:define name="debitoACobrarGeral" property="debitoACobrar" id="debitoACobrar"/>
														
															<logic:present name="cobrancaDocumentoItemDebitoACobrar" property="numeroParcelaAntecipada">
																<logic:notEqual name="cobrancaDocumentoItemDebitoACobrar" property="numeroParcelaAntecipada" value="0">
																	<bean:write name="cobrancaDocumentoItemDebitoACobrar" property="numeroParcelaAntecipada" />
																</logic:notEqual>
															</logic:present>

															<logic:notPresent name="cobrancaDocumentoItemDebitoACobrar" property="numeroParcelaAntecipada">
																<logic:present name="debitoACobrar" property="numeroPrestacaoCobradas">
																	<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="debitoACobrar" property="numeroPrestacaoCobradas"/></font>
																</logic:present>
																/
																<logic:present name="debitoACobrar" property="numeroPrestacaoDebito">
																	<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="debitoACobrar" property="numeroPrestacaoDebito"/></font>
																</logic:present>
															</logic:notPresent>
															
															<logic:present name="cobrancaDocumentoItemDebitoACobrar" property="numeroParcelaAntecipada">
																<logic:equal name="cobrancaDocumentoItemDebitoACobrar" property="numeroParcelaAntecipada" value="0">
																	<logic:present name="debitoACobrar" property="numeroPrestacaoCobradas">
																		<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="debitoACobrar" property="numeroPrestacaoCobradas"/></font>
																	</logic:present>
																	/
																	<logic:present name="debitoACobrar" property="numeroPrestacaoDebito">
																		<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="debitoACobrar" property="numeroPrestacaoDebito"/></font>
																	</logic:present>
																</logic:equal>
															</logic:present>
													</logic:present>		

													<logic:present name="debitoACobrarGeral" property="debitoACobrarHistorico">
														<bean:define name="debitoACobrarGeral" property="debitoACobrarHistorico" id="debitoACobrarHistorico"/>

															<logic:present name="debitoACobrarHistorico" property="prestacaoCobradas">
																<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="debitoACobrarHistorico" property="prestacaoCobradas"/></font>
															</logic:present>
															
															/
															
															<logic:present name="debitoACobrarHistorico" property="prestacaoDebito">
																<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="debitoACobrarHistorico" property="prestacaoDebito"/></font>
															</logic:present>

													</logic:present>
											</logic:present>
											
										</div>
										</td>
										<td width="20%">
										<div align="right">
										
											<logic:present name="cobrancaDocumentoItemDebitoACobrar" property="debitoACobrarGeral">	
												<bean:define name="cobrancaDocumentoItemDebitoACobrar" property="debitoACobrarGeral" id="debitoACobrarGeral"/>
													<logic:present name="debitoACobrarGeral" property="debitoACobrar">
														<bean:define name="debitoACobrarGeral" property="debitoACobrar" id="debitoACobrar"/>

															<logic:present name="debitoACobrar" property="valorTotal">
																<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="debitoACobrar" property="valorTotal" formatKey="money.format" /></font>
															</logic:present>
														
															<logic:notPresent name="debitoACobrar" property="valorTotal">
																&nbsp;
															</logic:notPresent>
													</logic:present>		

													<logic:present name="debitoACobrarGeral" property="debitoACobrarHistorico">
														<bean:define name="debitoACobrarGeral" property="debitoACobrarHistorico" id="debitoACobrarHistorico"/>


															<logic:present name="debitoACobrarHistorico" property="valorDebito">
																<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="debitoACobrarHistorico" property="valorTotal" formatKey="money.format" /></font>
															</logic:present>
														
															<logic:notPresent name="debitoACobrarHistorico" property="valorDebito">
																&nbsp;
															</logic:notPresent>

													</logic:present>
											</logic:present>
											
											
										</div>
										</td>
										<td width="20%">
										<div align="right">
										
											<logic:present name="cobrancaDocumentoItemDebitoACobrar" property="valorItemCobrado">
												<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="cobrancaDocumentoItemDebitoACobrar" property="valorItemCobrado" formatKey="money.format" /></font>
											</logic:present>
										
											<logic:notPresent name="cobrancaDocumentoItemConta" property="valorItemCobrado">
												&nbsp;
											</logic:notPresent>
											
										</div>
										</td>
									</tr>
									

								</logic:iterate>
								
								</table>

							</div>
						</td>
            		</tr>
            		
            		</logic:notEmpty>
            		
            		</table>
				</td>
			</tr>
			
      <tr>
      	<td colspan="5" height="5"></td>
      </tr>
      <tr>
      	<td colspan="5">
      	
      		<table width="100%" border="0">
	  		<tr> 
          		<td height="17" colspan="5"><strong>Guias de Pagamento:</strong></td>
          	</tr>
      		<tr> 
          		<td colspan="5">
		  
					<table width="100%" cellpadding="0" cellspacing="0">
					<tr> 
                		<td> 
					
							<table width="100%" bgcolor="#99CCFF" border="0">
                    		<tr bgcolor="#99CCFF"> 

								<td align="center" width="30%"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><strong>Tipo do D�bito</strong></font></td>
								<td width="15%"><div align="center"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><strong>Emiss�o</strong></font></div></td>
								<td width="15%"><div align="center"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><strong>Vencimento</strong></font></div></td>
								<td width="20%"><div align="center"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><strong>Vl. Guia de Pagamento</strong></font></div></td>
								<td width="20%"><div align="center"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><strong>Vl. Item Cobrado</strong></font></div></td>
							</tr>
                    		</table>
					
						
            		</tr>
            		
            		<logic:notEmpty name="cobrancaDocumentoHelper" property="colecaoCobrancaDocumentoItemGuiaPagamento">
            
            		<tr> 
						<td> 
					
							<div style="width: 100%; height: 100; overflow: auto;">	
							
							<%cor = "#FFFFFF";%>
							
							<table width="100%" align="center" bgcolor="#99CCFF">

								<logic:iterate name="cobrancaDocumentoHelper" property="colecaoCobrancaDocumentoItemGuiaPagamento" id="cobrancaDocumentoItemGuiaPagamento" type="CobrancaDocumentoItem">
                            
									<logic:notEmpty name="cobrancaDocumentoItemGuiaPagamento" property="guiaPagamentoGeral.guiaPagamento.guiasPagamentoPrestacao">
									
										<logic:iterate name="cobrancaDocumentoItemGuiaPagamento" property="guiaPagamentoGeral.guiaPagamento.guiasPagamentoPrestacao" id="prestacao" type="GuiaPagamentoPrestacao">
											
											<%	if (cor.equalsIgnoreCase("#FFFFFF")){
													cor = "#cbe5fe";%>
													<tr bgcolor="#FFFFFF">
											<%} else {
													cor = "#FFFFFF";%>
													<tr bgcolor="#cbe5fe">
											<%}%>
											 
												<td width="30%">
												<div align="center">
													
													<logic:present name="prestacao" property="debitoTipo">	
														<a href="javascript:abrirPopup('exibirConsultarGuiaPagamentoAction.do?guiaPagamentoId=<%="" + prestacao.getComp_id().getGuiaPagamentoId() %>')" 
															title="<%="" + prestacao.getDebitoTipo().getDescricao() %>">
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
																<bean:write name="prestacao" property="debitoTipo.descricao"/>
															</font>
														</a>
													</logic:present>
												
													<logic:notPresent name="prestacao" property="debitoTipo">
														&nbsp;
													</logic:notPresent>
												    
												</div>
												</td>
												<td width="15%">
												<div align="center">
							
													<logic:present name="prestacao" property="dataEmissao">
														<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
															<bean:write name="prestacao" property="dataEmissao" formatKey="date.format"/>
														</font>
													</logic:present>
												
													<logic:notPresent name="prestacao" property="dataEmissao">
														&nbsp;
													</logic:notPresent>
													
												</div>
												</td>
												<td width="15%">
												<div align="center">
							
													<logic:present name="prestacao" property="dataVencimento">
														<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
															<bean:write name="prestacao" property="dataVencimento" formatKey="date.format"/>
														</font>
													</logic:present>
												
													<logic:notPresent name="prestacao" property="dataVencimento">
														&nbsp;
													</logic:notPresent>
													
												</div>
												</td>
												<td width="20%">
												<div align="right">
												
													<logic:present name="prestacao" property="valorPrestacao">
														<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
															<bean:write name="prestacao" property="valorPrestacao" formatKey="money.format" />
														</font>
													</logic:present>
												
													<logic:notPresent name="prestacao" property="valorPrestacao">
														&nbsp;
													</logic:notPresent>
													
												</div>
												</td>
												<td width="20%">
												<div align="right">
												
													<logic:present name="cobrancaDocumentoItemGuiaPagamento" property="valorItemCobrado">
														<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
															<bean:write name="cobrancaDocumentoItemGuiaPagamento" property="valorItemCobrado" formatKey="money.format" />
														</font>
													</logic:present>
												
													<logic:notPresent name="cobrancaDocumentoItemGuiaPagamento" property="valorItemCobrado">
														&nbsp;
													</logic:notPresent>
													
													
												</div>
												</td>
											</tr>
			
										</logic:iterate>
									</logic:notEmpty>

								</logic:iterate>
								
								</table>

							</div>
						</td>
            		</tr>
            		
            		</logic:notEmpty>
            		
            		</table>
				</td>
			</tr>
			
			</table>
			
      	</td>
      </tr>			
			
			</table>
			
      	</td>
      </tr>
      
      
      
      <tr>
      <td align="right">
      <td align="right"> 
	  <logic:notEmpty name="cobrancaDocumentoHelper" property="idOrdemServico">
		<input type="button" onclick="window.location.href='exibirConsultarDadosOrdemServicoPopupAction.do?voltar=s&numeroOS=${cobrancaDocumentoHelper.idOrdemServico}'"
		class="bottonRightCol" value="Ordem de Servi�o">
 	  </logic:notEmpty>

      <input name="Submit23" class="bottonRightCol" value="Fechar"
						type="button"
						onclick="javascript:window.close();">
      </td>
      </tr>
	  <!-- ============================================================================================================================== -->

      </table>
      <p>&nbsp;</p>
	</td>
  </tr>
</table>

</html:form>

</body>
</html:html>
