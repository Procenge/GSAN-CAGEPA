<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<%@ page import="gcom.util.ConstantesSistema" %>
<%@ page import="gcom.arrecadacao.aviso.bean.AvisoBancarioHelper" isELIgnored="false" %>

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

<html:form action="/exibirApresentarAnaliseMovimentoArrecadadoresAction" method="post">


<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

<table width="770" border="0" cellspacing="4" cellpadding="0">
  <tr>
    <td width="149" valign="top" class="leftcoltext">
      <div align="center">

	<%@ include file="/jsp/util/informacoes_usuario.jsp"%>

	<%@ include file="/jsp/util/mensagens.jsp"%>

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
          <td class="parabg">Apresentar An�lise do Movimento dos Arrecadadores</td>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
        </tr>
      </table>
      <p>&nbsp;</p>

      <table width="100%" border="0">
      <tr>
      	<td colspan="4">Dados do Movimento do Arrecadador:</td>
      </tr>
      <tr>
		<td height="20" width="150"><strong>Arrecadador:</strong></td>
		<td colspan="3">
			<html:text property="codigoNomeArrecadador" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
		</td>
	  </tr>
	  <tr>
		<td height="20" width="150"><strong>Remessa:</strong></td>
		<td width="200">
			<html:text property="codigoRemessa" size="15" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
		</td>
		<td height="20" width="70"><strong>Servi�o:</strong></td>
		<td>
			<html:text property="identificacaoServico" size="23" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
		</td>
	  </tr>			
	  <tr>
		<td height="20" width="150"><strong>N�mero Sequencial Arquivo (NSA):</strong></td>
		<td colspan="3">
			<html:text property="nsa" size="11" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
		</td>
	  </tr>
	  <tr>
		<td height="30" width="150"><strong>Data de Gera��o:</strong></td>
		<td colspan="3">
			<html:text property="dataGeracao" size="11" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
		</td>
	  </tr>
	  <logic:equal name="indEmpresaTrabalhaConcessionaria" value="<%=""+ConstantesSistema.SIM%>">
	  <tr>
		<td height="20" width="150"><strong>Concession�ria:</strong></td>
		<td colspan="3">
			<html:text property="idNomeConcessionaria" size="60" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
		</td>
	  </tr>
	  </logic:equal>
	  <tr>
      	<td colspan="4">
      			<table width="100%" align="center" bgcolor="#99CCFF" border="0">
				<tr>
					<td align="center"><strong>Registros</strong></td>
				</tr>
				<tr bgcolor="#cbe5fe">
					<td width="100%" align="center">
			
						<table width="100%" border="0">
						<tr>
					      	<td height="20" width="146"><strong>Total:</strong></td>
							<td colspan="3">
								<html:text property="numeroRegistrosMovimento" size="11" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
							</td>
						</tr>
						<tr>
							<td height="20" width="146"><strong>Em Ocorr�ncia:</strong></td>
							<td width="200">
								<html:text property="numeroRegistrosOcorrencia" size="11" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
							</td>
							<td height="20" width="80"><strong>N�o Aceitos:</strong></td>
							<td>
								<html:text property="numeroRegistrosNaoAceitos" size="11" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
							</td>
						</tr>			
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
      			<table width="100%" align="center" bgcolor="#99CCFF" border="0">
				<tr>
					<td align="center"><strong>Processamento</strong></td>
				</tr>
				<tr bgcolor="#cbe5fe">
					<td width="100%" align="center">
						<table width="100%" border="0">
						<tr>
							<td height="20" width="146"><strong>Data:</strong></td>
							<td width="200">
								<html:text property="dataProcessamento" size="11" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
							</td>
							<td height="20" width="80"><strong>Hora:</strong></td>
							<td>
								<html:text property="horaProcessamento" size="11" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
							</td>
						</tr>		
						</table>
					</td>
				</tr>
				</table>
      		</td>
      </tr>
	 
	  <tr>
		<td height="20" width="150"><strong>Valor Total do Movimento:</strong></td>
		<td colspan="2">
			<html:text property="valorTotalMovimento" size="15" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
		</td>
		<logic:notEmpty name="ApresentarAnaliseMovimentoArrecadadoresActionForm" property="mensagemMovimentoItensNaoAceitos">
			<td>
				<html:text property="mensagemMovimentoItensNaoAceitos" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #FF0000"/>
			</td>
		</logic:notEmpty>
	  </tr>
	  
	  <tr>
      	<td colspan="4" height="10"></td>
      </tr>
      
      <tr>
      	<td colspan="4">
      		
      		<table width="100%" cellpadding="0" cellspacing="0">
			<tr> 
                <td> 
					
					<table width="100%" bgcolor="#99CCFF">
                    <tr bgcolor="#99CCFF"> 
						<td width="14%"><div align="center"><strong>Dt. Lan�.</strong></td>
						<td width="7%"><div align="center"><strong>Sequ.</strong></div></td>
                        <td width="5%"><div align="center"><strong>Tipo</strong></div></td>
                        <td width="14%"><div align="center"><strong>Dt. Cred.</strong></div></td>
                        <td width="13%"><div align="right"><strong>Vl. Info.</strong></div></td>
                        <td width="11%"><div align="right"><strong>Vl. Acerto.</strong></div></td>
                        <td width="14%"><div align="right"><strong>Vl. Calc.</strong></div></td>
                        <td width="12%"><div align="right"><strong>Vl. Dife.</strong></div></td>
                        <td width="10%"><div align="left"><strong>Situa��o</strong></div></td>
                    </tr>
                    </table>
					
				</td>
            </tr>
            </table>

			<logic:present name="colecaoAvisosBancariosPorMovimentoArrecadador" scope="request">

			<div style="width: 100%; height: 100; overflow: auto;">

			<table width="100%" cellpadding="0" cellspacing="0">
            <tr> 
				<td> 
				
					<% String cor = "#FFFFFF";%>
				
					<table width="100%" align="center" bgcolor="#99CCFF">
				
					<logic:iterate name="colecaoAvisosBancariosPorMovimentoArrecadador" id="avisoBancarioHelper" type="AvisoBancarioHelper">
                            
										
							<%	if (cor.equalsIgnoreCase("#FFFFFF")){
									cor = "#cbe5fe";%>
									<tr bgcolor="#FFFFFF">
									
							<%} else{
									cor = "#FFFFFF";%>
									<tr bgcolor="#cbe5fe">
							<%}%> 
							
								<td align="center" width="14%">
									<logic:present name="avisoBancarioHelper" property="dataLancamento">
										<bean:write name="avisoBancarioHelper" property="dataLancamento" formatKey="date.format"/>
									</logic:present>
									<logic:notPresent name="avisoBancarioHelper" property="dataLancamento">
										&nbsp;
									</logic:notPresent>
								</td>
								<td width="7%">
									<div align="center">
									<logic:present name="avisoBancarioHelper" property="numeroSequencial">
										<html:link href="/gsan/exibirApresentarAnaliseAvisoBancarioAction.do" paramId="idAvisoBancario" paramProperty="idAvisoBancario" paramName="avisoBancarioHelper" title="<%="" + avisoBancarioHelper.getNumeroSequencial()%>"><bean:write name="avisoBancarioHelper" property="numeroSequencial"/></html:link>
									</logic:present>
									<logic:notPresent name="avisoBancarioHelper" property="numeroSequencial">
										&nbsp;
									</logic:notPresent>
									</div>
								</td>
								<td width="5%">
									<div align="center">
									<logic:present name="avisoBancarioHelper" property="descricaoIndicadorCreditoDebito">
										<bean:write name="avisoBancarioHelper" property="descricaoIndicadorCreditoDebito"/>
									</logic:present>
									<logic:notPresent name="avisoBancarioHelper" property="descricaoIndicadorCreditoDebito">
										&nbsp;
									</logic:notPresent>
									</div>
								</td>
								<td width="14%">
									<div align="center">
									<logic:present name="avisoBancarioHelper" property="dataRealizada">
										<bean:write name="avisoBancarioHelper" property="dataRealizada" formatKey="date.format"/>
									</logic:present>
									<logic:notPresent name="avisoBancarioHelper" property="dataRealizada">
										&nbsp;
									</logic:notPresent>
									</div>
								</td>
								<td width="13%">
									<div align="right">
									<logic:present name="avisoBancarioHelper" property="valorInformado">
										<bean:write name="avisoBancarioHelper" property="valorInformado" formatKey="money.format"/>
									</logic:present>
									<logic:notPresent name="avisoBancarioHelper" property="valorInformado">
										&nbsp;
									</logic:notPresent>
									</div>
								</td>
								<td width="11%">
									<div align="right">
									<logic:present name="avisoBancarioHelper" property="valorAcertos">
										<bean:write name="avisoBancarioHelper" property="valorAcertos" formatKey="money.format"/>
									</logic:present>
									<logic:notPresent name="avisoBancarioHelper" property="valorAcertos">
										&nbsp;
									</logic:notPresent>	
									</div>
								</td>
								<td width="14%">
									<div align="right">
									<logic:present name="avisoBancarioHelper" property="valorCalculado">
										<bean:write name="avisoBancarioHelper" property="valorCalculado" formatKey="money.format"/>
									</logic:present>
									<logic:notPresent name="avisoBancarioHelper" property="valorCalculado">
										&nbsp;
									</logic:notPresent>
									</div>
								</td>
								
								<td width="12%">
									<div align="right">
									<logic:present name="avisoBancarioHelper" property="valorDiferenca">
										<bean:write name="avisoBancarioHelper" property="valorDiferenca" formatKey="money.format"/>
									</logic:present>
									<logic:notPresent name="avisoBancarioHelper" property="valorDiferenca">
										&nbsp;
									</logic:notPresent>
									</div>
								</td>
		
								<td width="10%">
									<div align="left">
									<logic:present name="avisoBancarioHelper" property="situacao">
										<bean:write name="avisoBancarioHelper" property="situacao"/>
									</logic:present>
									<logic:notPresent name="avisoBancarioHelper" property="situacao">
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
			
			</logic:present>
      
      	</td>
	  </tr>      
      <tr>
      	<td colspan="2" align="left">
      	
      	
      <%-- 	<INPUT type="button" onclick="history.back();" name="botaoVoltar" class="bottonRightCol" value="Voltar" tabindex="2" style="width: 70px;"> --%>
      	
      	
      	
      	
				      	<logic:present scope="session" name="manter">
								<input name="Submit222"
									class="bottonRightCol" value="Voltar" type="button"
									onclick="window.location.href='/gsan/ExibirManterAvisoBancarioAction.do';">
							</logic:present>
							
							<logic:notPresent scope="session" name="manter">
							
								<logic:present scope="session" name="filtrar_manter">
									<input name="Submit222"
									class="bottonRightCol" value="Voltar" type="button"
									onclick="javascript:history.back();">
								</logic:present>
								
								<logic:notPresent scope="session" name="filtrar_manter">
									
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
      	
      	
      	
      	</td>
      	<td colspan="2" align="right">
<%-- <gcom:controleAcessoBotao name="botaoConsultarItens" value="Consultar Itens" onclick="javascript:abrirPopup('exibirConsultarItensMovimentoArrecadadorAction.do?arrecadadorMovimentoID=${requestScope.idArrecadadorMovimento}', 620, 760);" url="exibirConsultarItensMovimentoArrecadadorAction.do" tabindex="1"/> --%> 
     	  <gcom:controleAcessoBotao name="botaoConsultarItens" value="Consultar Itens" onclick="javascript:abrirPopup('exibirPesquisarItensMovimentoArrecadadorAction.do?arrecadadorMovimentoID=${requestScope.idArrecadadorMovimento}&limparPopup=OK', 360,600);" url="exibirPesquisarItensMovimentoArrecadadorAction.do" tabindex="1"/>
      	  
      	  <%-- <INPUT type="button" onclick="abrirPopup('exibirConsultarItensMovimentoArrecadadorAction.do?arrecadadorMovimentoID=${requestScope.idArrecadadorMovimento}', 620, 760)" name="botaoConsultarItens" class="bottonRightCol" value="Consultar Itens" tabindex="1"> --%>
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
</html:html>

