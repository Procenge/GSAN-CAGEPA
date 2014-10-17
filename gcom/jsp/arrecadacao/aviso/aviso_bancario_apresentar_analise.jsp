<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ page import="gcom.arrecadacao.pagamento.Pagamento"%>
<%@ page import="gcom.arrecadacao.Devolucao"%>
<%@ page import="gcom.arrecadacao.aviso.AvisoBancario"%>
<%@ page import="gcom.util.Util" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>

<SCRIPT LANGUAGE="JavaScript">


<!--




//-->
</SCRIPT>


</head>

<body leftmargin="5" topmargin="5">

<html:form action="/exibirApresentarAnaliseAvisoBancarioAction" method="post">


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

	<td width="625" valign="top" class="centercoltext">

        <table height="100%">
        <tr>
          <td></td>
        </tr>
      	</table>

      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
          <td class="parabg">Apresentar Análise do Aviso Bancário</td>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
        </tr>
      </table>
      <p>&nbsp;</p>
      <table width="100%" border="0">
      <tr>
      	<td>Dados do Movimento do Arrecadador:</td>
      </tr>
      </table>
      
      <table width="100%" border="0">
      <tr>
		<td height="20" width="150"><strong>Arrecadador:</strong></td>
		<td width="435" colspan="3">
			<html:text property="codigoNomeArrecadador" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
		</td>
	  </tr>
	  <tr>
		<td height="20" width="150"><strong>Data de Lançamento:</strong></td>
		<td>
			<html:text property="dataLancamento" size="11" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
		</td>
	  </tr>
	  <tr>
		<td height="20" width="150"><strong>Sequencial:</strong></td>
		<td>
			<html:text property="sequencial" size="11" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
		</td>
	  </tr>
	  <tr>
		<td height="20" width="150"><strong>Nº do Documento:</strong></td>
		<td>
			<html:text property="numeroDocumento" size="11" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
		</td>
	  </tr>
	  
		<tr>
			<td colspan="5">
			
			<table width="100%" cellpadding="0" cellspacing="0">
			
				<tr>
					<td>
					<table width="100%" border="0" bgcolor="#90c7fc">
						<tr>
							<td bgcolor="#79bbfd" colspan="5" height="20"><div align="center"><strong>Dados do Aviso</strong></div></td>
						</tr>
						<logic:equal name="ApresentarAnaliseAvisoBancarioActionForm" property="tipoAviso" value="<%=AvisoBancario.CREDITO%>">
							<tr>
								<td bgcolor="#79bbfd" colspan="5" height="20"><div align="left"><strong>Tipo: Crédito</strong></div></td>
							</tr>
						</logic:equal>
						<logic:equal name="ApresentarAnaliseAvisoBancarioActionForm" property="tipoAviso" value="<%=AvisoBancario.DEBITO%>">
							<tr>
								<td bgcolor="#79bbfd" colspan="5" height="20"><div align="left"><strong>Tipo: Débito</strong></div></td>
							</tr>
						</logic:equal>
						<tr bgcolor="#90c7fc">
							
							<td width="25%"><div align="right"><strong>Valor Informado</strong></div></td>
							<td width="25%"><div align="right"><strong>Valor Acertos</strong></div></td>
							<td width="25%"><div align="right"><strong>Valor Deduções</strong></div></td>
							<td width="25%"><div align="right"><strong>Valor Realizado</strong></div></td>
						</tr>
					</table>
					</td>
				</tr>

				<tr>
					<td>
					<table width="100%" align="center" bgcolor="#90c7fc">
						<tr bgcolor="#FFFFFF">
						<logic:equal name="ApresentarAnaliseAvisoBancarioActionForm" property="tipoAviso" value="<%=AvisoBancario.CREDITO%>">
							<td width="25%" align="right"><bean:write name="ApresentarAnaliseAvisoBancarioActionForm" property="valorArrecadacaoInformado"/></td>
						</logic:equal>
						<logic:equal name="ApresentarAnaliseAvisoBancarioActionForm" property="tipoAviso" value="<%=AvisoBancario.DEBITO%>">
							<td width="25%" align="right"><bean:write name="ApresentarAnaliseAvisoBancarioActionForm" property="valorDevolucaoInformado"/></td>
						</logic:equal>
							<td  width="25%" align="right">
									<logic:present name="ApresentarAnaliseAvisoBancarioActionForm" property="valorAcertos">
										<logic:equal name="ApresentarAnaliseAvisoBancarioActionForm" property="valorAcertos" value="0,00">
											<bean:write name="ApresentarAnaliseAvisoBancarioActionForm" property="valorAcertos"/>
										</logic:equal>		
										<logic:notEqual name="ApresentarAnaliseAvisoBancarioActionForm" property="valorAcertos" value="0,00">
											
											  <a href="javascript:abrirPopup('exibirConsultarAcertosArrecadacaoAvisoBancarioPopupAction.do?idAvisoBancario=<bean:write name="ApresentarAnaliseAvisoBancarioActionForm" property="idAvisoBancario"/>', 400, 550);">
										          <bean:write name="ApresentarAnaliseAvisoBancarioActionForm" property="valorAcertos"/>
									          </a>
										</logic:notEqual>							
									</logic:present>
							</td>
							<td width="25%" align="right">
								<logic:present name="ApresentarAnaliseAvisoBancarioActionForm" property="valorSomatorioDeducoes">
									<logic:equal name="ApresentarAnaliseAvisoBancarioActionForm" property="valorSomatorioDeducoes" value="0,00">
										<bean:write name="ApresentarAnaliseAvisoBancarioActionForm" property="valorSomatorioDeducoes"/>
									</logic:equal>		
									<logic:notEqual name="ApresentarAnaliseAvisoBancarioActionForm" property="valorSomatorioDeducoes" value="0,00">
										
										  <a href="javascript:abrirPopup('exibirConsultarDeducoesAvisoBancarioPopupAction.do?idAvisoBancario=<bean:write name="ApresentarAnaliseAvisoBancarioActionForm" property="idAvisoBancario"/>', 520, 550);">
									          <bean:write name="ApresentarAnaliseAvisoBancarioActionForm" property="valorSomatorioDeducoes"/>
								          </a>
									</logic:notEqual>							
								</logic:present>
							</td>
							<td width="25%" align="right"><bean:write name="ApresentarAnaliseAvisoBancarioActionForm" property="valorRealizado"/></td>
						</tr>
					</table>
					</td>
				</tr>
				
			</table>
			</td>
		</tr>
						
		<tr>
			<td colspan="3">
			<table width="100%" cellpadding="0" cellspacing="0">
			
				<tr>
					<td>
					<table width="100%" border="0" bgcolor="#90c7fc">
						<tr bgcolor="#90c7fc">
							<td align="center" rowspan="2" width="17%"><strong>Data Prevista</strong></td>
							<td align="center" rowspan="2" width="17%"><strong>Data Real</strong></td>
							<td align="right" rowspan="2" width="26%"><strong>Mês/ Ano Arrecadação</strong></td>
							<td align="center" colspan="3" width="40%"><strong>Conta Bancária</strong></td>
						</tr>
						<tr bgcolor="#90c7fc"> 
							<td align="right" bgcolor="cbe5fe" width="13%"><strong>Banco</strong></td>
							<td align="right" bgcolor="cbe5fe" width="14%"><strong>Agência</strong></td>
							<td align="right" bgcolor="cbe5fe" width="13%"><strong>Número</strong></td>
						</tr>
					</table>
					</td>
				</tr>

				<tr>
					<td>
					<table width="100%" align="center" bgcolor="#90c7fc">
						<tr bgcolor="#FFFFFF">
							<td width="17%" align="center"><bean:write name="ApresentarAnaliseAvisoBancarioActionForm" property="dataPrevistaCredito"/></td>
							<td width="17%" align="center"><bean:write name="ApresentarAnaliseAvisoBancarioActionForm" property="dataRealCredito"/></td>
							<td  width="26%" align="right"><bean:write name="ApresentarAnaliseAvisoBancarioActionForm" property="anoMesArrecadacao"/></td>
							<td  width="13%" align="right"><bean:write name="ApresentarAnaliseAvisoBancarioActionForm" property="bancoContaBancaria"/></td>
							<td  width="14%" align="right"><bean:write name="ApresentarAnaliseAvisoBancarioActionForm" property="agenciaContaBancaria"/></td>
							<td  width="13%" align="right"><bean:write name="ApresentarAnaliseAvisoBancarioActionForm" property="numeroContaBancaria"/></td>
						</tr>
					</table>
					</td>
				</tr>
				
			</table>
			</td>
		</tr>
		
	  <tr>
		<td height="10" colspan="34">&nbsp;</td>
      </tr>
	  
	  <tr>
		<td ><strong>Situação:</strong></td>
		<td colspan="3" align="left">
			<html:text property="situacao" size="11" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
		</td>
	  </tr>
	
		<tr>
			<td colspan="3">
			<table width="100%" cellpadding="0" cellspacing="0">
			
				<tr>
					<td>
					<table width="100%" border="0" bgcolor="#90c7fc">
						<tr bgcolor="#90c7fc">
							<td align="center" colspan="5"><strong>Valores do Aviso</strong></td>
						</tr>
						
						<tr bgcolor="#90c7fc"> 
							<td align="right" bgcolor="cbe5fe" width="25%"><strong>Valor Informado</strong></td>
							<td align="right" bgcolor="cbe5fe" width="25%"><strong>Valor Acertos</strong></td>
							<td align="right" bgcolor="cbe5fe" width="25%"><strong>Valor Calculado</strong></td>
							<td align="right" bgcolor="cbe5fe" width="25%"><strong>Diferença</strong></td>
						</tr>
					</table>
					</td>
				</tr>

				<tr>
					<td>
					<table width="100%" align="center" bgcolor="#90c7fc">
						<tr bgcolor="#FFFFFF">
							<td width="25%" align="right"><bean:write name="ApresentarAnaliseAvisoBancarioActionForm" property="valorInformado"/></td>
							<td  width="25%" align="right">
								<logic:present name="ApresentarAnaliseAvisoBancarioActionForm" property="valorAcertos">
									<logic:equal name="ApresentarAnaliseAvisoBancarioActionForm" property="valorAcertos" value="0,00">
										<bean:write name="ApresentarAnaliseAvisoBancarioActionForm" property="valorAcertos"/>
									</logic:equal>		
									<logic:notEqual name="ApresentarAnaliseAvisoBancarioActionForm" property="valorAcertos" value="0,00">
										
										  <a href="javascript:abrirPopup('exibirConsultarAcertosArrecadacaoAvisoBancarioPopupAction.do?idAvisoBancario=<bean:write name="ApresentarAnaliseAvisoBancarioActionForm" property="idAvisoBancario"/>', 400, 550);">
									          <bean:write name="ApresentarAnaliseAvisoBancarioActionForm" property="valorAcertos"/>
								          </a>
									</logic:notEqual>							
								</logic:present>
							</td>
							<td  width="25%" align="right" title="<bean:write name="ApresentarAnaliseAvisoBancarioActionForm" property="descricaoValorTotalPagamentos"/>"><bean:write name="ApresentarAnaliseAvisoBancarioActionForm" property="valorCalculado"/></td>
							<td  width="25%" align="right"><bean:write name="ApresentarAnaliseAvisoBancarioActionForm" property="valorDiferenca"/></td>
						</tr>
					</table>
					</td>
				</tr>

			</table>
			</td>
		</tr>
		
		 <tr>
			<td align="right" colspan="5">
				<logic:present name="avisoBancarioHelper" property="avisoBancario.arrecadadorMovimento">
					<logic:notPresent scope="session" name="habilitarBotao">
						<INPUT type="button" onclick="window.location.href='exibirApresentarAnaliseMovimentoArrecadadoresAction.do?arrecadadorMovimentoID=${requestScope.idMovimentoArrecadador}'" name="botaoConsultarMovimento" class="bottonRightCol" value="Consultar Movimento" tabindex="2" >
					</logic:notPresent>
				</logic:present>
				
				<logic:notPresent name="avisoBancarioHelper" property="avisoBancario.arrecadadorMovimento">
					<logic:notPresent scope="session" name="habilitarBotao">
						<INPUT type="button" name="botaoConsultarMovimento" class="bottonRightCol" value="Consultar Movimento" tabindex="2" disabled="true">
					</logic:notPresent>
				</logic:notPresent>
			</td>
		  </tr>
		
	  <tr>
		<td height="10" colspan="5">&nbsp;</td>
      </tr>
      
      <tr>
      	<td colspan="5">
      		<table width="100%" cellpadding="0" cellspacing="0">
			<tr> 
                <td> 
					
					<table width="100%" bgcolor="#90c7fc">
					<tr>
						<td colspan="10" align="center" bgcolor="#79bbfd"><strong>Pagamentos</strong></td>
				    </tr>
                    <tr bgcolor="#90c7fc"> 
						<td width="10%"><div align="center"><strong>Doc.</strong></div></td>
						<td width="9%"><div align="center"><strong>n.Doc</strong></div></td>
						<td width="9%"><div align="center"><strong>Parc.</strong></div></td>
						<td width="11%"><div align="center"><strong>Dt. Pag.</strong></div></td>
                        <td width="8%"><div align="center"><strong>Locali.</strong></div></td>
                        <td width="9%"><div align="center"><strong>Imóvel</strong></div></td>
                        <td width="9%"><div align="center"><strong>Cliente</strong></div></td>
                        <td width="9%"><div align="center"><strong>Ref.Pag.</strong></div></td>
                        <td width="12%"><div align="center"><strong>Vl. Pag.</strong></div></td>
                        <td width="4%"><div align="center"><strong>Sit.</strong></div></td>
                    </tr>
                    </table>
					
				</td>
            </tr>
            </table>
            
            <% String cor = "#cbe5fe";%>

			<logic:notEmpty name="avisoBancarioHelper" property="colecaoPagamentos">

			<div style="width: 100%; height: 100; overflow: auto;">

			<table width="100%" cellpadding="0" cellspacing="0">
            <tr> 
				<td> 
				
					<table width="100%" align="center" bgcolor="#90c7fc">
				
					<logic:iterate name="avisoBancarioHelper" property="colecaoPagamentos" id="pagamento" type="Pagamento">
                            
										
							<%	if (cor.equalsIgnoreCase("#cbe5fe")){
									cor = "#FFFFFF";%>
									<tr bgcolor="#FFFFFF">
							<%} else{
									cor = "#cbe5fe";%>
									<tr bgcolor="#cbe5fe">
							<%}%> 
							
								<td align="left" width="10%">
									<logic:present name="pagamento" property="documentoTipo">
										<bean:write name="pagamento" property="documentoTipo.descricaoAbreviado"/>
									</logic:present>
									<logic:notPresent name="pagamento" property="documentoTipo">
										&nbsp;
									</logic:notPresent>
								</td>
								<td align="center" width="9%">
									
									
									<logic:present name="pagamento" property="conta">
										<bean:write name="pagamento" property="conta.id"/>
									</logic:present>									
									
									<logic:present name="pagamento" property="guiaPagamentoGeral">
										<bean:write name="pagamento" property="guiaPagamentoGeral.guiaPagamento.id"/>
									</logic:present>
									
									<logic:present name="pagamento" property="debitoACobrar">
										<bean:write name="pagamento" property="debitoACobrar.id"/>
									</logic:present>
									
									<logic:present name="pagamento" property="preParcelamentoOpcao">
										<bean:write name="pagamento" property="preParcelamentoOpcao.id"/>
									</logic:present>
									
										
									
								</td>
								<td align="center" width="9%">
									<logic:present name="pagamento" property="numeroPrestacao">
										<bean:write name="pagamento" property="numeroPrestacao"/>
									</logic:present>
									<logic:notPresent name="pagamento" property="numeroPrestacao">
										&nbsp;
									</logic:notPresent>
								</td>
								<td width="11%">
									<div align="center">
									<logic:present name="pagamento" property="dataPagamento">
										<bean:write name="pagamento" property="dataPagamento" formatKey="date.format"/>
									</logic:present>
									<logic:notPresent name="pagamento" property="dataPagamento">
										&nbsp;
									</logic:notPresent>
									</div>
								</td>
								<td width="8%">
									<div align="center">
									<logic:present name="pagamento" property="localidade">
										<bean:write name="pagamento" property="localidade.id"/>
									</logic:present>
									<logic:notPresent name="pagamento" property="localidade">
										&nbsp;
									</logic:notPresent>
									</div>
								</td>
								<td width="9%">
									<div align="center">
									<logic:present name="pagamento" property="imovel">
										<bean:write name="pagamento" property="imovel.id"/>
									</logic:present>
									<logic:notPresent name="pagamento" property="imovel">
										&nbsp;
									</logic:notPresent>
									</div>
								</td>
								<td width="9%">
									<div align="center">
									<logic:present name="pagamento" property="cliente">
										<bean:write name="pagamento" property="cliente.id"/>
									</logic:present>
									<logic:notPresent name="pagamento" property="cliente">
										&nbsp;
									</logic:notPresent>
									</div>
								</td>
								<td width="9%">
									<div align="center">
									<logic:present name="pagamento" property="anoMesReferenciaPagamento">
										<logic:notEqual name="pagamento" property="anoMesReferenciaPagamento" value="0">
											<%= Util.formatarAnoMesParaMesAno(pagamento.getAnoMesReferenciaPagamento()) %>
										</logic:notEqual>
										<logic:equal name="pagamento" property="anoMesReferenciaPagamento" value="0">
											&nbsp;
										</logic:equal>
									</logic:present>
									<logic:notPresent name="pagamento" property="anoMesReferenciaPagamento">
										&nbsp;
									</logic:notPresent>	
									</div>
								</td>
								<td width="12%">
									<div align="right">
									<logic:present name="pagamento" property="valorPagamento">
										<bean:write name="pagamento" property="valorPagamento" formatKey="money.format"/>
									</logic:present>
									<logic:notPresent name="pagamento" property="valorPagamento">
										&nbsp;
									</logic:notPresent>
									</div>
								</td>
								<td width="4%">
									<div align="left">
									<logic:present name="pagamento" property="pagamentoSituacaoAtual">
										<bean:write name="pagamento" property="pagamentoSituacaoAtual.descricaoAbreviada"/>
									</logic:present>
									<logic:notPresent name="pagamento" property="pagamentoSituacaoAtual">
										&nbsp;
									</logic:notPresent>
									</div>
								</td>
							</tr>
							

						</logic:iterate>
						
					</table>
				</td>
            </tr>
			</table>
			</div>

			</logic:notEmpty>
      
      	</td>
	  </tr>
	   
	  
      <tr>
      	<td colspan="3">
      		<table width="100%" cellpadding="0" cellspacing="0">
			<tr> 
                <td> 
					<table width="100%" bgcolor="#90c7fc">
					<tr>
					   <td colspan="9" align="center" bgcolor="#79bbfd"><strong>Devoluções</strong></td>
			        </tr>
                    <tr bgcolor="#90c7fc"> 
						<td width="10%"><div align="center"><strong>Doc.</strong></div></td>
						<td width="13%"><div align="center"><strong>Dt. Pag.</strong></div></td>
                        <td width="10%"><div align="center"><strong>Locali.</strong></div></td>
                        <td width="10%"><div align="center"><strong>Imóvel</strong></div></td>
                        <td width="10%"><div align="center"><strong>Cliente</strong></div></td>
                        <td width="10%"><div align="center"><strong>Ref.Pagto.</strong></div></td>
                        <td width="10%"><div align="center"><strong>Débito</strong></div></td>
                        <td width="13%"><div align="center"><strong>Vl. Pag.</strong></div></td>
                        <td width="13%"><div align="center"><strong>Sit.</strong></div></td>
                    </tr>
                    </table>
					
				</td>
            </tr>
            </table>

			<logic:notEmpty name="avisoBancarioHelper" property="colecaoDevolucoes">

			<div style="width: 100%; height: 100; overflow: auto;">

			<table width="100%" cellpadding="0" cellspacing="0">
            <tr> 
				<td> 
				
					<% cor = "#cbe5fe";%>
				
					<table width="100%" align="center" bgcolor="#90c7fc">
				
					<logic:iterate name="avisoBancarioHelper" property="colecaoDevolucoes" id="devolucao" type="Devolucao">
                            
										
							<%	if (cor.equalsIgnoreCase("#cbe5fe")){
									cor = "#FFFFFF";%>
									<tr bgcolor="#FFFFFF">
							<%} else{
									cor = "#cbe5fe";%>
									<tr bgcolor="#cbe5fe">
							<%}%> 
							
								<td align="left" width="10%">
									<logic:present name="devolucao" property="guiaDevolucao">
										<bean:write name="devolucao" property="guiaDevolucao.documentoTipo.descricaoAbreviado"/>
									</logic:present>
									<logic:notPresent name="devolucao" property="guiaDevolucao">
										&nbsp;
									</logic:notPresent>
								</td>
								<td width="13%">
									<div align="center">
									<logic:present name="devolucao" property="dataDevolucao">
										<bean:write name="devolucao" property="dataDevolucao" formatKey="date.format"/>
									</logic:present>
									<logic:notPresent name="devolucao" property="dataDevolucao">
										&nbsp;
									</logic:notPresent>
									</div>
								</td>
								<td width="10%">
									<div align="center">
									<logic:present name="devolucao" property="localidade">
										<bean:write name="devolucao" property="localidade.id"/>
									</logic:present>
									<logic:notPresent name="devolucao" property="localidade">
										&nbsp;
									</logic:notPresent>
									</div>
								</td>
								<td width="10%">
									<div align="center">
									<logic:present name="devolucao" property="imovel">
										<bean:write name="devolucao" property="imovel.id"/>
									</logic:present>
									<logic:notPresent name="devolucao" property="imovel">
										&nbsp;
									</logic:notPresent>
									</div>
								</td>
								<td width="10%">
									<div align="center">
									<logic:present name="devolucao" property="cliente">
										<bean:write name="devolucao" property="cliente.id"/>
									</logic:present>
									<logic:notPresent name="devolucao" property="cliente">
										&nbsp;
									</logic:notPresent>
									</div>
								</td>
								<td width="10%">
									<div align="center">
									<logic:present name="devolucao" property="anoMesReferenciaDevolucao">
										<logic:notEqual name="devolucao" property="anoMesReferenciaDevolucao" value="0">
											<%= Util.formatarAnoMesParaMesAno(devolucao.getAnoMesReferenciaDevolucao()) %>
										</logic:notEqual>
										<logic:equal name="devolucao" property="anoMesReferenciaDevolucao" value="0">
											&nbsp;
										</logic:equal>
									</logic:present>
									<logic:notPresent name="devolucao" property="anoMesReferenciaDevolucao">
										&nbsp;
									</logic:notPresent>	
									</div>
								</td>
								<td width="10%">
									<div align="center">
									<logic:present name="devolucao" property="debitoTipo">
										<bean:write name="devolucao" property="debitoTipo.id"/>
									</logic:present>
									<logic:notPresent name="devolucao" property="debitoTipo">
										&nbsp;
									</logic:notPresent>
									</div>
								</td>
								<td width="13%">
									<div align="right">
									<logic:present name="devolucao" property="valorDevolucao">
										<bean:write name="devolucao" property="valorDevolucao" formatKey="money.format"/>
									</logic:present>
									<logic:notPresent name="devolucao" property="valorDevolucao">
										&nbsp;
									</logic:notPresent>
									</div>
								</td>
								<td width="13%">
									<div align="left">
									<logic:present name="devolucao" property="devolucaoSituacaoAtual">
										<bean:write name="devolucao" property="devolucaoSituacaoAtual.descricaoAbreviada"/>
									</logic:present>
									<logic:notPresent name="devolucao" property="devolucaoSituacaoAtual">
										&nbsp;
									</logic:notPresent>
									</div>
								</td>
							</tr>
							

						</logic:iterate>
						
					</table>
				</td>
            </tr>

			</table>
			</div>

			</logic:notEmpty>
      
      	</td>
	  </tr>
	  
	  <tr>
				<td><strong>Tipo do Relatório:</strong></td>
				<td></td>
				<td align="right">	
						<strong> Pagamentos Classificados </strong>
						<html:radio property="relatorioTipo" value="1" tabindex="5">
						</html:radio><br/>
						<strong> Pagamentos Não Classificados </strong>
						<html:radio property="relatorioTipo" value="2" tabindex="5">
						</html:radio><br/>
						<strong> Ambos </strong> 
						<html:radio property="relatorioTipo" value="3" tabindex="5">
						</html:radio>
				</td>
	 </tr>
	 
	  
	  <tr>
            <td colspan="3" align="right" valign="top">
							<div align="right"><a href="javascript:toggleBox('demodiv',1);">
							<img border="0"
								src="<bean:message key="caminho.imagens"/>print.gif"
								title="Imprimir Pagamentos" /> </a></div>
							</td>
       </tr>
	  
</table>


<table width="100%" border="0">
      <tr>
      	<td>
				
			<logic:present scope="session" name="manter">
				<input name="Submit222"
					class="bottonRightCol" value="Voltar" type="button"
					onclick="javascript:history.back();">
			</logic:present>
			
			<logic:notPresent scope="session" name="manter">
			
				<logic:present scope="session" name="filtrar_manter">
					<input name="Submit222"
					class="bottonRightCol" value="Voltar" type="button"
					onclick="javascript:history.back();">
				</logic:present>
				
				<logic:notPresent scope="session" name="filtrar_manter">
					
					<logic:present scope="session" name="acao">
						<input name="Submit222"
							class="bottonRightCol" value="Voltar" type="button"
								onclick="javascript:history.back();">
					</logic:present>
					
					<logic:notPresent scope="session" name="acao">
					
						<logic:present scope="session" name="analiseMovimento">
							<input name="Submit222"
								class="bottonRightCol" value="Voltar" type="button"
									onclick="javascript:history.back();">
						</logic:present>
						
						<logic:notPresent scope="session" name="analiseMovimento">
							<input name="Submit222"
								class="bottonRightCol" value="Voltar" type="button"
								onclick="window.location.href='/gsan/filtrarMovimentoArrecadadoresAction.do';">
						</logic:notPresent>
					
					</logic:notPresent>
					
				</logic:notPresent>
				
			</logic:notPresent>
				
				

			<input name="Submit23" class="bottonRightCol" value="Cancelar"
				type="button"
				onclick="window.location.href='/gsan/telaPrincipal.do'"> 
				
		</td>
      </tr>
      </table>
      
      
      
      
     
      
	</td>
  </tr>
</table>
 <jsp:include
		page="/jsp/relatorio/escolher_tipo_relatorio.jsp?relatorio=gerarRelatorioPagamentoAction.do" />      
<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>


</body>
</html:html>

