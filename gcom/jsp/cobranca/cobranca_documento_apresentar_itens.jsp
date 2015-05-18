<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

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


</head>

<body leftmargin="5" topmargin="5">

<html:form action="/exibirApresentarItensDocumentoCobrancaAction" method="post">

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

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
      
      <logic:present name="imovel" scope="request">
    
      
      <tr> 
          <td colspan="4">
				
				<table width="100%" align="center" bgcolor="#99CCFF" border="0">
				<tr>
					<td><strong>Dados do Im�vel:</strong></td>
				</tr>
				<tr bgcolor="#cbe5fe">
					<td width="100%" align="center">
			
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
							<td height="10" colspan="2">
							
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
      
      
        </logic:present>
        
        
      <tr>
      	<td colspan="4" height="10"></td>
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
      	<td colspan="4">
      		<table width="100%" border="0">
			<tr> 
				<td height="10" width="155"><strong>Sequencial Documento:</strong></td>
		        <td>
					<html:text property="sequencial" size="10" readonly="true" style="background-color:#EFEFEF; border:0;"/>
		        </td>
		        <td colspan="2" width="380"></td>
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
		        <td colspan="2" width="380"></td>
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
		        <td colspan="2" width="380"></td>
			</tr>
			<tr> 
				<td height="10" width="155"><strong>Situa��o do Documento:</strong></td>
		        <td>
					<html:text property="descricaoCobrancaAcaoSituacao" size="30" readonly="true" style="background-color:#EFEFEF; border:0;"/>
		        </td>
		        <td colspan="2" width="380"></td>
			</tr>
			<tr> 
				<td height="10" width="155"><strong>Data da Situa��o do Documento :</strong></td>
		        <td>
				<html:text property="dataEntrega" size="8" readonly="true" style="background-color:#EFEFEF; border:0;"/>
		        </td>
		        <td colspan="2" width="380"></td>
			</tr>
			</table>
      	</td>
      </tr>
      <tr> 
          <td colspan="4">
				
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
      	<td colspan="4" height="10"></td>
      </tr>
      <tr>
      	<td colspan="4">
      	
      		<table width="100%" border="0">
	  		<tr> 
          		<td height="17" colspan="4"><strong>Contas:</strong></td>
          	</tr>
      		<tr> 
          		<td colspan="4">
		  
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
									<logic:notEmpty name="cobrancaDocumentoItemConta" property="contaGeral">
									
									
											<%	if (cor.equalsIgnoreCase("#FFFFFF")){
													cor = "#cbe5fe";%>
													<tr bgcolor="#FFFFFF">
											<%} else{
													cor = "#FFFFFF";%>
													<tr bgcolor="#cbe5fe">
											<%}%>
											 
												<td width="10%">
												<div align="center">
		
		    	                        		    <logic:notEmpty name="cobrancaDocumentoItemConta" property="contaGeral.conta">
														<logic:present name="cobrancaDocumentoItemConta" property="contaGeral.conta.referencia">	
															<a href="javascript:abrirPopup('exibirConsultarContaAction.do?tipoConsulta=conta&contaID=<%="" + cobrancaDocumentoItemConta.getContaGeral().getId()%>' , 600, 800);" title="<%="" + Util.formatarMesAnoReferencia(cobrancaDocumentoItemConta.getContaGeral().getConta().getId())%>"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><%=""+ Util.formatarMesAnoReferencia(cobrancaDocumentoItemConta.getContaGeral().getConta().getReferencia())%></font></a>
														</logic:present>

														<logic:notPresent name="cobrancaDocumentoItemConta" property="contaGeral.conta.referencia">
															&nbsp;
														</logic:notPresent>
													</logic:notEmpty>

		    	                        		    <logic:empty name="cobrancaDocumentoItemConta" property="contaGeral.conta">
														<logic:present name="cobrancaDocumentoItemConta" property="contaGeral.contaHistorico.anoMesReferenciaConta">	
															<a href="javascript:abrirPopup('exibirConsultarContaAction.do?tipoConsulta=contaHistorico&contaID=<%="" + cobrancaDocumentoItemConta.getContaGeral().getId()%>' , 600, 800);" title="<%="" + Util.formatarMesAnoReferencia(cobrancaDocumentoItemConta.getContaGeral().getContaHistorico().getId())%>"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><%=""+ Util.formatarMesAnoReferencia(cobrancaDocumentoItemConta.getContaGeral().getContaHistorico().getAnoMesReferenciaConta())%></font></a>
														</logic:present>

														<logic:notPresent name="cobrancaDocumentoItemConta" property="contaGeral.contaHistorico.anoMesReferenciaConta">
															&nbsp;
														</logic:notPresent>
													</logic:empty>
												</div>
												</td>
												<td width="15%">
												<div align="center">
												
													<logic:notEmpty name="cobrancaDocumentoItemConta" property="contaGeral.conta">
														<logic:present name="cobrancaDocumentoItemConta" property="contaGeral.conta.dataVencimentoConta">
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="cobrancaDocumentoItemConta" property="contaGeral.conta.dataVencimentoConta" formatKey="date.format" /></font>
														</logic:present>
													
														<logic:notPresent name="cobrancaDocumentoItemConta" property="contaGeral.conta.dataVencimentoConta">
															&nbsp;
														</logic:notPresent>
													</logic:notEmpty>
													
													<logic:empty name="cobrancaDocumentoItemConta" property="contaGeral.conta">
														<logic:present name="cobrancaDocumentoItemConta" property="contaGeral.contaHistorico.dataVencimentoConta">
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="cobrancaDocumentoItemConta" property="contaGeral.contaHistorico.dataVencimentoConta" formatKey="date.format" /></font>
														</logic:present>
													
														<logic:notPresent name="cobrancaDocumentoItemConta" property="contaGeral.contaHistorico.dataVencimentoConta">
															&nbsp;
														</logic:notPresent>
													</logic:empty>
												</div>
												</td>
												<td width="10%">
												<div align="right">
												
													<logic:notEmpty name="cobrancaDocumentoItemConta" property="contaGeral.conta">
														<logic:present name="cobrancaDocumentoItemConta" property="contaGeral.conta.valorAgua">
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="cobrancaDocumentoItemConta" property="contaGeral.conta.valorAgua" formatKey="money.format" /></font>
														</logic:present>
													
														<logic:notPresent name="cobrancaDocumentoItemConta" property="contaGeral.conta.valorAgua">
															&nbsp;
														</logic:notPresent>
													</logic:notEmpty>

													<logic:empty name="cobrancaDocumentoItemConta" property="contaGeral.conta">
														<logic:present name="cobrancaDocumentoItemConta" property="contaGeral.contaHistorico.valorAgua">
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="cobrancaDocumentoItemConta" property="contaGeral.contaHistorico.valorAgua" formatKey="money.format" /></font>
														</logic:present>
													
														<logic:notPresent name="cobrancaDocumentoItemConta" property="contaGeral.contaHistorico.valorAgua">
															&nbsp;
														</logic:notPresent>
													</logic:empty>

												</div>
												</td>
												<td width="10%">
												<div align="right">
												
													<logic:notEmpty name="cobrancaDocumentoItemConta" property="contaGeral.conta">
														<logic:present name="cobrancaDocumentoItemConta" property="contaGeral.conta.valorEsgoto">
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="cobrancaDocumentoItemConta" property="contaGeral.conta.valorEsgoto" formatKey="money.format" /></font>
														</logic:present>
													
														<logic:notPresent name="cobrancaDocumentoItemConta" property="contaGeral.conta.valorEsgoto">
															&nbsp;
														</logic:notPresent>
													</logic:notEmpty>

													<logic:empty name="cobrancaDocumentoItemConta" property="contaGeral.conta">
														<logic:present name="cobrancaDocumentoItemConta" property="contaGeral.contaHistorico.valorEsgoto">
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="cobrancaDocumentoItemConta" property="contaGeral.contaHistorico.valorEsgoto" formatKey="money.format" /></font>
														</logic:present>
													
														<logic:notPresent name="cobrancaDocumentoItemConta" property="contaGeral.contaHistorico.valorEsgoto">
															&nbsp;
														</logic:notPresent>
													</logic:empty>

												</div>
												</td>
												<td width="10%">
												<div align="right">
												
													<logic:notEmpty name="cobrancaDocumentoItemConta" property="contaGeral.conta">
														<logic:present name="cobrancaDocumentoItemConta" property="contaGeral.conta.debitos">
															
															<logic:notEqual name="cobrancaDocumentoItemConta" property="contaGeral.conta.debitos" value="0">
																<a href="javascript:abrirPopup('exibirConsultarDebitoCobradoAction.do?tipoConsulta=conta&contaID=<%="" + cobrancaDocumentoItemConta.getContaGeral().getId()%>' , 500, 800);" title="<%="" + Util.formatarMoedaReal(cobrancaDocumentoItemConta.getContaGeral().getConta().getDebitos())%>"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="cobrancaDocumentoItemConta" property="contaGeral.conta.debitos" formatKey="money.format" /></font></a>
															</logic:notEqual>
															
															<logic:equal name="cobrancaDocumentoItemConta" property="contaGeral.conta.debitos" value="0">
																<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="cobrancaDocumentoItemConta" property="contaGeral.conta.debitos" formatKey="money.format" /></font>
															</logic:equal>
															
														</logic:present>
													
														<logic:notPresent name="cobrancaDocumentoItemConta" property="contaGeral.conta.debitos">
															&nbsp;
														</logic:notPresent>
													</logic:notEmpty>

													<logic:empty name="cobrancaDocumentoItemConta" property="contaGeral.conta">
														<logic:present name="cobrancaDocumentoItemConta" property="contaGeral.contaHistorico.valorDebitos">
															
															<logic:notEqual name="cobrancaDocumentoItemConta" property="contaGeral.contaHistorico.valorDebitos" value="0">
																<a href="javascript:abrirPopup('exibirConsultarDebitoCobradoAction.do?tipoConsulta=contaHistorico&contaID=<%="" + cobrancaDocumentoItemConta.getContaGeral().getId()%>' , 500, 800);" title="<%="" + Util.formatarMoedaReal(cobrancaDocumentoItemConta.getContaGeral().getContaHistorico().getValorDebitos())%>"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="cobrancaDocumentoItemConta" property="contaGeral.contaHistorico.valorDebitos" formatKey="money.format" /></font></a>
															</logic:notEqual>
															
															<logic:equal name="cobrancaDocumentoItemConta" property="contaGeral.contaHistorico.valorDebitos" value="0">
																<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="cobrancaDocumentoItemConta" property="contaGeral.contaHistorico.valorDebitos" formatKey="money.format" /></font>
															</logic:equal>
															
														</logic:present>
													
														<logic:notPresent name="cobrancaDocumentoItemConta" property="contaGeral.contaHistorico.valorDebitos">
															&nbsp;
														</logic:notPresent>
													</logic:empty>

												</div>
												</td>
												<td width="10%">
												<div align="right">
													
													<logic:notEmpty name="cobrancaDocumentoItemConta" property="contaGeral.conta">
														<logic:present name="cobrancaDocumentoItemConta" property="contaGeral.conta.valorCreditos">
															
															<logic:notEqual name="cobrancaDocumentoItemConta" property="contaGeral.conta.valorCreditos" value="0">
																<a href="javascript:abrirPopup('exibirConsultarCreditoRealizadoAction.do?tipoConsulta=conta&contaID=<%="" + cobrancaDocumentoItemConta.getContaGeral().getId()%>' , 500, 800);" title="<%="" + Util.formatarMoedaReal(cobrancaDocumentoItemConta.getContaGeral().getConta().getValorCreditos())%>"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="cobrancaDocumentoItemConta" property="contaGeral.conta.valorCreditos" formatKey="money.format" /></font></a>
															</logic:notEqual>
															
															<logic:equal name="cobrancaDocumentoItemConta" property="contaGeral.conta.valorCreditos" value="0">
																<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="cobrancaDocumentoItemConta" property="contaGeral.conta.valorCreditos" formatKey="money.format" /></font>
															</logic:equal>
															
														</logic:present>
													
														<logic:notPresent name="cobrancaDocumentoItemConta" property="contaGeral.conta.valorCreditos">
															&nbsp;
														</logic:notPresent>
													</logic:notEmpty>

													<logic:empty name="cobrancaDocumentoItemConta" property="contaGeral.conta">
														<logic:present name="cobrancaDocumentoItemConta" property="contaGeral.contaHistorico.valorCreditos">
															
															<logic:notEqual name="cobrancaDocumentoItemConta" property="contaGeral.contaHistorico.valorCreditos" value="0">
																<a href="javascript:abrirPopup('exibirConsultarCreditoRealizadoAction.do?tipoConsulta=contaHistorico&contaID=<%="" + cobrancaDocumentoItemConta.getContaGeral().getId()%>' , 500, 800);" title="<%="" + Util.formatarMoedaReal(cobrancaDocumentoItemConta.getContaGeral().getContaHistorico().getValorCreditos())%>"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="cobrancaDocumentoItemConta" property="contaGeral.contaHistorico.valorCreditos" formatKey="money.format" /></font></a>
															</logic:notEqual>
															
															<logic:equal name="cobrancaDocumentoItemConta" property="contaGeral.contaHistorico.valorCreditos" value="0">
																<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="cobrancaDocumentoItemConta" property="contaGeral.contaHistorico.valorCreditos" formatKey="money.format" /></font>
															</logic:equal>
															
														</logic:present>
													
														<logic:notPresent name="cobrancaDocumentoItemConta" property="contaGeral.contaHistorico.valorCreditos">
															&nbsp;
														</logic:notPresent>
													</logic:empty>

												</div>
												</td>
												<td width="10%">
												<div align="right">

													<logic:notEmpty name="cobrancaDocumentoItemConta" property="contaGeral.conta">
														<logic:present name="cobrancaDocumentoItemConta" property="contaGeral.conta.valorTotal">
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="cobrancaDocumentoItemConta" property="contaGeral.conta.valorTotal" formatKey="money.format" /></font>
														</logic:present>

														<logic:notPresent name="cobrancaDocumentoItemConta" property="contaGeral.conta.valorTotal">
															&nbsp;
														</logic:notPresent>
													</logic:notEmpty>

													<logic:empty name="cobrancaDocumentoItemConta" property="contaGeral.conta">
														<logic:present name="cobrancaDocumentoItemConta" property="contaGeral.contaHistorico.valorTotal">
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="cobrancaDocumentoItemConta" property="contaGeral.contaHistorico.valorTotal" formatKey="money.format" /></font>
														</logic:present>

														<logic:notPresent name="cobrancaDocumentoItemConta" property="contaGeral.contaHistorico.valorTotal">
															&nbsp;
														</logic:notPresent>
													</logic:empty>

												</div>
												</td>
												<td width="15%">
												<div align="center">
												
													<logic:notEmpty name="cobrancaDocumentoItemConta" property="contaGeral.conta">
														<logic:present name="cobrancaDocumentoItemConta" property="contaGeral.conta.debitoCreditoSituacaoAtual">
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="cobrancaDocumentoItemConta" property="contaGeral.conta.debitoCreditoSituacaoAtual.descricaoDebitoCreditoSituacao"/></font>
														</logic:present>

														<logic:notPresent name="cobrancaDocumentoItemConta" property="contaGeral.conta.debitoCreditoSituacaoAtual">
															&nbsp;
														</logic:notPresent>
													</logic:notEmpty>

													<logic:empty name="cobrancaDocumentoItemConta" property="contaGeral.conta">
														<logic:present name="cobrancaDocumentoItemConta" property="contaGeral.contaHistorico.debitoCreditoSituacaoAtual">
															<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="cobrancaDocumentoItemConta" property="contaGeral.contaHistorico.debitoCreditoSituacaoAtual.descricaoDebitoCreditoSituacao"/></font>
														</logic:present>

														<logic:notPresent name="cobrancaDocumentoItemConta" property="contaGeral.contaHistorico.debitoCreditoSituacaoAtual">
															&nbsp;
														</logic:notPresent>
													</logic:empty>

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
      
      <tr>
      	<td colspan="4" height="5"></td>
      </tr>
      <tr>
      	<td colspan="4">
      	
      		<table width="100%" border="0">
	  		<tr> 
          		<td height="17" colspan="4"><strong>D�bitos A Cobrar:</strong></td>
          	</tr>
      		<tr> 
          		<td colspan="4">
		  
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

											<logic:present name="cobrancaDocumentoItemDebitoACobrar" property="debitoACobrarGeral.debitoACobrar.debitoTipo">	
												<a href="javascript:abrirPopup('exibirConsultarDebitoACobrarAction.do?imovelID=${requestScope.imovel}&debitoID=<%="" + cobrancaDocumentoItemDebitoACobrar.getDebitoACobrarGeral().getId() %>' , 500, 800);" title="<%="" + cobrancaDocumentoItemDebitoACobrar.getDebitoACobrarGeral().getDebitoACobrar().getDebitoTipo().getDescricao() %>"><font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="cobrancaDocumentoItemDebitoACobrar" property="debitoACobrarGeral.debitoACobrar.debitoTipo.descricao"/></font></a>
											</logic:present>
										
											<logic:notPresent name="cobrancaDocumentoItemDebitoACobrar" property="debitoACobrarGeral.debitoACobrar.debitoTipo">
												&nbsp;
											</logic:notPresent>
										
										</div>
										</td>
										<td width="10%">
										<div align="center">
					
											<logic:present name="cobrancaDocumentoItemDebitoACobrar" property="debitoACobrarGeral.debitoACobrar.anoMesReferenciaDebito">
												<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><%=""+ Util.formatarMesAnoReferencia(cobrancaDocumentoItemDebitoACobrar.getDebitoACobrarGeral().getDebitoACobrar().getAnoMesReferenciaDebito())%></font>
											</logic:present>
										
											<logic:notPresent name="cobrancaDocumentoItemDebitoACobrar" property="debitoACobrarGeral.debitoACobrar.anoMesReferenciaDebito">
												&nbsp;
											</logic:notPresent>
											
										</div>
										</td>
										<td width="10%">
										<div align="center">
					
											<logic:present name="cobrancaDocumentoItemDebitoACobrar" property="debitoACobrarGeral.debitoACobrar.anoMesCobrancaDebito">
												<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><%=""+ Util.formatarMesAnoReferencia(cobrancaDocumentoItemDebitoACobrar.getDebitoACobrarGeral().getDebitoACobrar().getAnoMesCobrancaDebito())%></font>
											</logic:present>
										
											<logic:notPresent name="cobrancaDocumentoItemDebitoACobrar" property="debitoACobrarGeral.debitoACobrar.anoMesCobrancaDebito">
												&nbsp;
											</logic:notPresent>
											
										</div>
										</td>
										<td width="10%">
										<div align="center">
											
											<logic:present name="cobrancaDocumentoItemDebitoACobrar" property="numeroParcelaAntecipada">
												<logic:notEqual name="cobrancaDocumentoItemDebitoACobrar" property="numeroParcelaAntecipada" value="0">
												<bean:write name="cobrancaDocumentoItemDebitoACobrar" property="numeroParcelaAntecipada" />
												
												</logic:notEqual>
											</logic:present>
											
											<logic:present name="cobrancaDocumentoItemDebitoACobrar" property="numeroParcelaAntecipada">
												<logic:equal name="cobrancaDocumentoItemDebitoACobrar" property="numeroParcelaAntecipada" value="0">
													<logic:present name="cobrancaDocumentoItemDebitoACobrar" property="debitoACobrarGeral.debitoACobrar.numeroPrestacaoCobradas">
														<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="cobrancaDocumentoItemDebitoACobrar" property="debitoACobrarGeral.debitoACobrar.numeroPrestacaoCobradas"/></font>
													</logic:present>
													/
													<logic:present name="cobrancaDocumentoItemDebitoACobrar" property="debitoACobrarGeral.debitoACobrar.numeroPrestacaoDebito">
														<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="cobrancaDocumentoItemDebitoACobrar" property="debitoACobrarGeral.debitoACobrar.numeroPrestacaoDebito"/></font>
													</logic:present>
												</logic:equal>
											</logic:present>

											<logic:notPresent name="cobrancaDocumentoItemDebitoACobrar" property="numeroParcelaAntecipada">
												<logic:present name="cobrancaDocumentoItemDebitoACobrar" property="debitoACobrarGeral.debitoACobrar.numeroPrestacaoCobradas">
													<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="cobrancaDocumentoItemDebitoACobrar" property="debitoACobrarGeral.debitoACobrar.numeroPrestacaoCobradas"/></font>
												</logic:present>
												/
												<logic:present name="cobrancaDocumentoItemDebitoACobrar" property="debitoACobrarGeral.debitoACobrar.numeroPrestacaoDebito">
													<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="cobrancaDocumentoItemDebitoACobrar" property="debitoACobrarGeral.debitoACobrar.numeroPrestacaoDebito"/></font>
												</logic:present>
											</logic:notPresent>
											
										</div>
										</td>
										<td width="20%">
										<div align="right">
										
											<logic:present name="cobrancaDocumentoItemDebitoACobrar" property="debitoACobrarGeral.debitoACobrar.valorRestanteCobrado">
												<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"><bean:write name="cobrancaDocumentoItemDebitoACobrar" property="debitoACobrarGeral.debitoACobrar.valorRestanteCobrado" formatKey="money.format" /></font>
											</logic:present>
										
											<logic:notPresent name="cobrancaDocumentoItemDebitoACobrar" property="debitoACobrarGeral.debitoACobrar.valorRestanteCobrado">
												&nbsp;
											</logic:notPresent>
											
											
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
			
			</table>
			
      	</td>
      </tr>
      
      
      <tr>
      	<td colspan="4" height="5"></td>
      </tr>
      <tr>
      	<td colspan="4">
      	
      		<table width="100%" border="0">
	  		<tr> 
          		<td height="17" colspan="4"><strong>Guias de Pagamento:</strong></td>
          	</tr>
      		<tr> 
          		<td colspan="4">
		  
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
      <tr>
      <td align="left">
      <input name="Submit23" class="bottonRightCol" value="Voltar"
						type="button"
						onclick="javascript:history.back();">
      </td>
      </tr>
	  <!-- ============================================================================================================================== -->

      </table>
      <p>&nbsp;</p>
	</td>
  </tr>
</table>

<%@ include file="/jsp/util/rodape.jsp"%>

</html:form>


</body>
</html:html>
