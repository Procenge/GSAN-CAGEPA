<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ page import="gcom.cobranca.parcelamento.ParcelamentoItem"
	isELIgnored="false"%>


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
	onload="javascript:resizePageSemLink(600,700);">
<table width="570" border="0" cellspacing="5" cellpadding="0">
	<tr>
		<td width="570" valign="top" class="centercoltext">
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
				<td class="parabg">Consultar Guia de Pagamento</td>
				<td width="11"><img
					src="<bean:message key="caminho.imagens"/>parahead_right.gif"
					border="0" /></td>
			</tr>
		</table>
		<p>&nbsp;</p>
		<table width="100%" border="0">		
			<tr>
				<td colspan="2">Dados da guia de pagamento:</td>
			</tr>
			
			<tr>
				<td class="style1"><strong>Número da Guia:</strong></td>
				<td class="style1"><span class="style1"> 
					<logic:notEmpty name="guiaPagamento" property="id">
						<input name="idGuia" type="text" readonly="true" style="background-color:#EFEFEF; border:0" size="9" maxlength="9" value='<bean:write name="guiaPagamento" property="id" />'>
					</logic:notEmpty>
					<logic:empty name="guiaPagamento" property="id">
						<input name="idGuia" type="text" readonly="true" style="background-color:#EFEFEF; border:0" size="9" maxlength="9" value="">
					</logic:empty>
					 </span>
				</td>
			</tr>
			
			<logic:present name="exibirNuContratoParcOrgaoPublico" scope="session">
				<tr>
					<td width="194"><strong>No. Contrato Parcel. Órgão Público:</strong></td>
					<td width="395" align="right">
					<div align="left"><input name="nuContratoParcelOrgaoPublico" type="text"
						readonly style="background-color:#EFEFEF; border:0" size="10"
						maxlength="12" value="<bean:write name="guiaPagamento" property="numeroContratoParcelOrgaoPublico"/>">
					</div>
					</td>
				</tr>
			</logic:present>
			
			<tr>
				<td width="194"><strong> Localidade:</strong></td>
				<td width="395" align="right">
				<div align="left"><input name="codigoLocalidade" type="text"
					readonly style="background-color:#EFEFEF; border:0" size="3"
					maxlength="3" value="<bean:write name="guiaPagamento" property="localidade.id"/>">&nbsp;<input
					name="nomeLocalidade" type="text" readonly
					style="background-color:#EFEFEF; border:0" size="30" maxlength="30"
					value="<bean:write name="guiaPagamento" property="localidade.descricao"/>"></div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
				<hr>
				</td>
			</tr>
			<tr>
				<td class="style1"><strong>Matr&iacute;cula do Im&oacute;vel:</strong></td>

				<td class="style1">
					<span class="style1"> 
						<logic:notEmpty name="guiaPagamento" property="imovel">
							<input  name="matriculaImovel" type="text" readonly="true"  style="background-color:#EFEFEF; border:0" size="9" maxlength="9"
							value='<bean:write name="guiaPagamento" property="imovel.id"/>'>
						</logic:notEmpty>	 
					</span>
					<span class="style1"> 
						<logic:notEmpty name="guiaPagamento" property="imovel">
							<input name="inscricaoImovel" type="text" readonly="true" style="background-color:#EFEFEF; border:0" size="20" maxlength="20"
							value='<bean:write name="guiaPagamento" property="imovel.inscricaoFormatada"/>'>
						</logic:notEmpty>	 
					</span>
				</td>
			</tr>
			<tr>
				<td colspan="3">
				<table width="100%" align="center" bgcolor="#99CCFF" border="0">
					<tr>

						<td align="center"><strong>Endere&ccedil;o </strong></td>
					</tr>
					<tr bgcolor="#FFFFFF">
						<td>
						<div align="center">
							<logic:notEmpty name="guiaPagamento" property="imovel">
								<bean:write name="guiaPagamento" property="imovel.enderecoFormatado"/>
							</logic:notEmpty>	
							&nbsp;
						</div>
						</td>
					</tr>

				</table>
				</td>
			</tr>
			<tr>
				<td colspan="2">
				<hr>
				</td>
			</tr>
			<tr>
				<td class="style1"><strong>C&oacute;digo do Cliente:</strong></td>
				<td class="style1"><span class="style1"> 
					<logic:notEmpty name="guiaPagamento" property="cliente">
						<input name="codigoCliente" type="text" readonly="true" style="background-color:#EFEFEF; border:0" size="9" maxlength="9" value='<bean:write name="guiaPagamento" property="cliente.id" />'>
					</logic:notEmpty>
					<logic:empty name="guiaPagamento" property="cliente">
						<input name="codigoCliente" type="text" readonly="true" style="background-color:#EFEFEF; border:0" size="9" maxlength="9" value="">
					</logic:empty>
					 </span>
				</td>
			</tr>
			<tr>
				<td class="style1"><strong>CPF/CNPJ:</strong></td>
				<td>	
					<logic:notEmpty name="guiaPagamento" property="cliente">
						<logic:equal name="guiaPagamento" property="cliente.clienteTipo.indicadorPessoaFisicaJuridica" value="1">
							<input name="cpfCnpjCliente" type="text" readonly="true" style="background-color:#EFEFEF; border:0" size="17" maxlength="17"
							value='<bean:write name="guiaPagamento" property="cliente.cpf" />'>
						</logic:equal>
						<logic:notEqual name="guiaPagamento" property="cliente.clienteTipo.indicadorPessoaFisicaJuridica" value="1">
							<input name="cpfCnpjCliente" type="text" readonly="true" style="background-color:#EFEFEF; border:0" size="17" maxlength="17"
							value='<bean:write name="guiaPagamento" property="cliente.cnpj" />'>
						</logic:notEqual>	
					</logic:notEmpty>			
				</td>
			</tr>

			<tr>
				<td class="style1"><strong>Nome do Cliente:</strong></td>
				<td class="style1"><span class="style1"> 
					<logic:notEmpty name="guiaPagamento" property="cliente">
						<input name="nomeCliente" type="text" readonly="readonly" style="background-color:#EFEFEF; border:0" value='<bean:write name="guiaPagamento" property="cliente.nome" />' size="50" maxlength="45">
					</logic:notEmpty>	 
					<logic:empty name="guiaPagamento" property="cliente">
						<input name="nomeCliente" type="text" readonly="readonly" style="background-color:#EFEFEF; border:0" value="" size="50" maxlength="45">
					</logic:empty>
					</span>
				</td>
			</tr>
			<tr>
				<td colspan="2">
				<hr>
				</td>
			</tr>

			<tr>
				<td><strong>Tipo do Documento:</strong></td>
				<td align="right">
				<div align="left"><input name="tipoDebito" type="text" readonly
					style="background-color:#EFEFEF; border:0" size="50" maxlength="50"
					value='<bean:write name="guiaPagamento" property="documentoTipo.descricaoDocumentoTipo" />'></div>
				</td>

			</tr>
			
			<tr>
				<td><strong>Situa&ccedil;&atilde;o da Guia de Pagamento:</strong></td>
				<td align="right">
				<div align="left"><input name="situacaoGuiaPagamento" type="text"
					readonly style="background-color:#EFEFEF; border:0" size="30"
					maxlength="30"
					value='<bean:write name="guiaPagamento" property="debitoCreditoSituacaoAtual.descricaoDebitoCreditoSituacao" />'></div>
				</td>
			</tr>
			<tr>
				<td><strong>RA - Registro de Atendimento:</strong></td>
				<td align="right">
					<div align="left">
						<logic:notEmpty name="guiaPagamento" property="registroAtendimento">
							<input name="registroAtendimentoId" type="text" readonly="true" style="background-color:#EFEFEF; border:0" size="9" maxlength="9"
						value='<bean:write name="guiaPagamento" property="registroAtendimento.id" />'> <%-- Por enquanto este campo ficará em branco até que o campo DESCRICAO DO SERVIÇO entrar na tabela --%>
						</logic:notEmpty>
						<logic:empty name="guiaPagamento" property="registroAtendimento">
							<input name="ordemServicoId" type="text" readonly="true" style="background-color:#EFEFEF; border:0" size="9" maxlength="9">
						</logic:empty>
					</div>				
				</td>
			</tr>
			<tr>
				<td><strong>Ordem de Servi&ccedil;o:<strong></strong><font
					color="#FF0000"> </font></strong></td>
				<td align="right">
				<div align="left">
					<logic:notEmpty name="guiaPagamento" property="ordemServico">
						<input name="ordemServicoId" type="text" readonly="true" style="background-color:#EFEFEF; border:0" size="9" maxlength="9"
					value='<bean:write name="guiaPagamento" property="ordemServico.id" />'> <%-- Por enquanto este campo ficará em branco até que o campo DESCRICAO DO SERVIÇO entrar na tabela --%>
					</logic:notEmpty>
					<logic:empty name="guiaPagamento" property="ordemServico">
						<input name="ordemServicoId" type="text" readonly="true" style="background-color:#EFEFEF; border:0" size="9" maxlength="9">
					</logic:empty>				
				</div>
				<div align="left"><strong></strong></div>
				<div align="left"></div>
				</td>
			</tr>
			
			<tr>
				<td width="" colspan="2">	
					<table width="100%" align="center" bgcolor="#99CCFF">
						<tr>
							<td colspan="2">							
						    	 <strong>Tipos do D&eacute;bito:</strong>
						    </td>
						</tr>	
					   </table>	
				</td>
			</tr>

			<tr>
				<td colspan="2">
					<table width="100%" cellpadding="0" cellspacing="0" >						
						<tr>						
							<td colspan="2" width="40%">
								<table width="100%" bgcolor="#90c7fc" border="0">
									<!--header da tabela interna -->
									<tr bgcolor="#90c7fc">
										<td width="15%" align="center">
											<strong>Emissão</strong>
										</td>
										<td width="15%" align="center">
											<strong>Vencimento</strong>
										</td>
										<td width="25%" align="left">
											<strong>Débito</strong>
										</td>
										<td width="12%" align="right">
											<strong>Valor</strong>
										</td>
										<td width="12%" align="right">
											<strong>Prestação</strong>
										</td>
										<td width="8%" bgcolor="#90c7fc" align="center">
												<strong>Paga</strong>
										</td>
										<td width="10%" align="center">
											<strong>Situação</strong>
										</td>
									</tr>
									<tr>
								<td colspan="7">
									<div style="width: 100%; height: 100%; overflow: auto;">
									<table width="100%" bgcolor="#99CCFF">										
										<logic:present name="guiaPagamento" property="guiasPagamentoPrestacao">
											<%int cont = 0;%>
											<!--  Campo que vai guardar o valor do endereço a ser removido -->
											<input type="hidden" name="prestacaoRemoverSelecao" value="" />
										
											<logic:iterate name="guiaPagamento" property="guiasPagamentoPrestacao" id="prestacoes">
												<%cont = cont + 1;
												if (cont % 2 == 0) {%>
														<tr bgcolor="#cbe5fe">
													<%} else { %>
														<tr bgcolor="#FFFFFF">
													<%}%>
													<td width="15%">
														<div align="center"><bean:write name="prestacoes" property="dataEmissao" formatKey="date.format"/><font color="#333333"></font>
														</div>
													</td>
													<td width="15%">
														<div align="center"><bean:write name="prestacoes" property="dataVencimento" formatKey="date.format"/><font color="#333333"></font>
														</div>
													</td>
													<td width="25%" align="left"><div><bean:write name="prestacoes" property="debitoTipo.descricao" /></div></td>
													<td width="12%" align="right"><bean:write name="prestacoes" property="valorPrestacao" formatKey="money.format"/></td>
													<td width="12%" align="right"><bean:write name="prestacoes" property="comp_id.numeroPrestacao" /> / <bean:write name="guiaPagamento" property="numeroPrestacaoTotal"/> </td>

													<logic:notEmpty name="prestacoes" property="indicadorPagamentoHint">
														<td width="8%" align="center" title=" ${prestacoes.indicadorPagamentoHint}"><div><bean:write name="prestacoes" property="indicadorPagamentoPendenteStr" /></div>  </td>
													</logic:notEmpty>
													<logic:empty name="prestacoes" property="indicadorPagamentoHint">
														<td width="8%" align="center" ><div><bean:write name="prestacoes" property="indicadorPagamentoPendenteStr" /></div>  </td>
													</logic:empty>

													<td width="10%" align="center"><div><bean:write name="prestacoes" property="debitoCreditoSituacao.descricaoAbreviada" /></div></td>
												</tr>				
												</logic:iterate>
											</logic:present>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>				
					<tr>
						<td align="right" width="25%"></td>
						<td align="left" width="75%">	
							<strong>Valor da Guia de Pagamento:</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;						
							<bean:write name="guiaPagamento" property="valorDebito" formatKey="money.format"/>												
						</td>
					</tr>
						
			
						
			<tr>
				<td colspan="2">
				<hr>
				</td>
			</tr>
			<table width="100%" border="0">
				<tr>
					<td height="24"><logic:present
						name="caminhoRetornoTelaConsultaGuiaPagamento">
						<input type="button" class="bottonRightCol" value="Voltar"
							style="width: 70px;" onclick="javascript:history.back();" />
					</logic:present>
					<td align="right"><input name="Button" type="button"
						class="bottonRightCol" value="Fechar"
						onClick="javascript:window.close();"></td>
				</tr>
			</table>
		</table>
	</tr>
</table>

</body>
<!-- guia_pagamento_consulta.jsp -->
</html>
