<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ page import="java.util.Collection" isELIgnored="false"%>

<%@ page import="gcom.arrecadacao.bean.ArrecadadorMovimentoItemHelper"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta name="generator"
	content="Web page layout created by Xara Webstyle 4 - http://www.xara.com/webstyle/" />
<link rel="stylesheet"
	href="<bean:message key='caminho.css'/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript"
	src="<bean:message key='caminho.js'/>util.js"></script>

<script language="JavaScript">
window.onmousemove = dimensaoPagina;
function dimensaoPagina(){
	resizePageSemLink(780, 610);
}

function resizePageSemLink(largura, altura){

	   //Para abrir o popup centralizado
		var height = window.screen.height - 160;
		var width = window.screen.width;
		var top = 50;
		var left = (width - largura)/2;
		resizeNow(largura, altura, top, left);
  }

  function resizeNow(largura, altura, top, left){
       window.resizeTo(largura, altura);
       window.moveTo(left , top);
  }
</script>
</head>

<body leftmargin="5" topmargin="5" onload="resizePageSemLink(780, 610);">
<table width="720" border="0" cellpadding="0" cellspacing="5">
	<tr>
		<td width="710" valign="top" class="centercoltext">
		<table height="100%">
			<tr>
				<td></td>
			</tr>
		</table>
		<table width="710" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="11"><img
					src="<bean:message key='caminho.imagens'/>parahead_left.gif"
					editor="Webstyle4"
					moduleid="Album Photos (Project)\toptab_page2_parahead_left.xws"
					border="0" /></td>
				<td class="parabg">Consultar Itens do Movimento dos Arrecadadores</td>
				<td width="11"><img
					src="<bean:message key='caminho.imagens'/>parahead_right.gif"
					editor="Webstyle4"
					moduleid="Album Photos (Project)\toptab_page2_parahead_right.xws"
					border="0" /></td>
			</tr>
		</table>
		<p>&nbsp;</p>

		<table width="710" cellpadding="0" cellspacing="0">
			<tr>
				<td colspan="2">

				<table width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td>
						<table width="100%" bgcolor="#90c7fc">
							<tr bgcolor="#90c7fc">
								<td width="10%">
								<div align="center"><strong>Registro</strong>
								</td>
								<td width="10%">
								<div align="center"><strong>Tipo Movimento</strong></div>
								</td>
								<td width="10%">
								<div align="center"><strong>Tipo Pagamento</strong></div>
								</td>
								<td width="10%">
								<div align="center"><strong>Identif.</strong></div>
								</td>
								<td width="10%">
								<div align="center"><strong>Ocorrência</strong></div>
								</td>
								<td width="10%">
								<div align="center"><strong>Ind. Aceitação</strong></div>
								</td>
								<td width="10%">
								<div align="center"><strong>Vl no Movimento</strong></div>
								</td>
								<td width="10%">
								<div align="center"><strong>Vl do Pagament.</strong></div>
								</td>
								<logic:present name="indicadorAjuste">
									<logic:equal name="indicadorAjuste" value="true">
										<td width="9%">
										<div align="center"><strong>Ajuste</strong></div>
										</td>
										<td width="9%">
										<div align="center"><strong>Nova Ocorrência</strong></div>
										</td>
									</logic:equal>
								</logic:present>
								<td width="2%">
								<div align="center"><strong></strong></div>
								</td>
								
							
						</table>
						</td>
					</tr>
				</table>

				<logic:present name="colecaoArrecadadorMovimentoItemHelper"
					scope="request">

					<div style="width: 100%; height: 400; overflow: auto;">

					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td><%String cor = "#FFFFFF";%>

							<table width="100%" align="center" bgcolor="#99CCFF">

								<logic:iterate name="colecaoArrecadadorMovimentoItemHelper"
									id="arrecadadorMovimentoItemHelper"
									type="ArrecadadorMovimentoItemHelper">


									<%if (cor.equalsIgnoreCase("#FFFFFF")) {
							cor = "#cbe5fe";%>
									<tr bgcolor="#FFFFFF">
										<%} else {
							cor = "#FFFFFF";%>
									<tr bgcolor="#cbe5fe">
										<%}%>

										<td align="center" width="10%" height="25">
											<logic:present name="arrecadadorMovimentoItemHelper" property="codigoRegistro">
												<bean:write name="arrecadadorMovimentoItemHelper" property="codigoRegistro" />
											</logic:present> 
											<logic:notPresent name="arrecadadorMovimentoItemHelper"	property="codigoRegistro">
												&nbsp;
											</logic:notPresent>
										</td>
										
										<td width="10%" align="left">
											<div align="left">
											<logic:present name="arrecadadorMovimentoItemHelper" property="tipoMovimento">
												<bean:write name="arrecadadorMovimentoItemHelper" property="tipoMovimento" />
											</logic:present>
										    <logic:notPresent name="arrecadadorMovimentoItemHelper" property="tipoMovimento">
												&nbsp;
											</logic:notPresent>
											</div>
										</td>
										
										<td width="10%" align="left">
											<div align="left">
											<logic:present name="arrecadadorMovimentoItemHelper" property="tipoPagamento">
												<logic:equal name="arrecadadorMovimentoItemHelper" property="tipoPagamento" value="DEBITO A COBRAR">
													<a href="javascript:abrirPopup('exibirPagamentoPopupAction.do?idPagamentoHistorico=${arrecadadorMovimentoItemHelper.id}' , 210, 510);" >
														<bean:write name="arrecadadorMovimentoItemHelper" property="tipoPagamento" />
													</a>	
												</logic:equal>
												<logic:notEqual name="arrecadadorMovimentoItemHelper" property="tipoPagamento" value="DEBITO A COBRAR">
													<bean:write name="arrecadadorMovimentoItemHelper" property="tipoPagamento" />
												</logic:notEqual>
											</logic:present>
											    <logic:notPresent name="arrecadadorMovimentoItemHelper" property="tipoPagamento">
													&nbsp;
												</logic:notPresent>
											</div>
										</td>
										
										<td width="10%">
											<div align="center">
												<logic:present name="arrecadadorMovimentoItemHelper" property="identificacao">
													<bean:write name="arrecadadorMovimentoItemHelper" property="identificacao" />
												</logic:present> 
												<logic:notPresent name="arrecadadorMovimentoItemHelper" property="identificacao">
													&nbsp;
												</logic:notPresent>
											</div>
										</td>
										
										<td width="10%">
											<div align="left">
												<logic:present name="arrecadadorMovimentoItemHelper" property="ocorrencia">
													<html:link
														href="/gsan/exibirApresentarDadosConteudoRegistroMovimentoArrecadadorAction.do"
														paramId="arrecadadorMovimentoItemID" paramProperty="id"
														paramName="arrecadadorMovimentoItemHelper"
														title="<%="" + arrecadadorMovimentoItemHelper.getOcorrencia()%>">
														<bean:write name="arrecadadorMovimentoItemHelper"
															property="ocorrencia" />
													</html:link>
												</logic:present> 
												<logic:notPresent name="arrecadadorMovimentoItemHelper" property="ocorrencia">
												&nbsp;
												</logic:notPresent>
											</div>
										</td>
										
										<td width="10%">
											<div align="left">
												<logic:present name="arrecadadorMovimentoItemHelper" property="descricaoIndicadorAceitacao">
													<bean:write name="arrecadadorMovimentoItemHelper" property="descricaoIndicadorAceitacao" />
												</logic:present> 
												<logic:notPresent name="arrecadadorMovimentoItemHelper" property="descricaoIndicadorAceitacao">
														&nbsp;
												</logic:notPresent>
											</div>
										</td>
										<td width="10%">
											<div align="right">
												<logic:present name="arrecadadorMovimentoItemHelper" property="vlMovimento">
													<bean:write name="arrecadadorMovimentoItemHelper" property="vlMovimento" />
												</logic:present> 
												<logic:notPresent name="arrecadadorMovimentoItemHelper" property="vlMovimento">
														&nbsp;
												</logic:notPresent>
											</div>
										</td>
										
										<td width="10%">
											<div align="right">
												<logic:present name="arrecadadorMovimentoItemHelper" property="vlPagamento">
													<bean:write name="arrecadadorMovimentoItemHelper" property="vlPagamento" />
												</logic:present> 
												<logic:notPresent name="arrecadadorMovimentoItemHelper" property="vlPagamento">
														&nbsp;
												</logic:notPresent>
											</div>
										</td>
										<logic:present name="indicadorAjuste">
										<logic:equal name="indicadorAjuste" value="true">
										<td width="9%">
											<div align="center">
												<logic:present name="arrecadadorMovimentoItemHelper" property="ajustado">
													<bean:write name="arrecadadorMovimentoItemHelper" property="ajustado" />
												</logic:present> 
												<logic:notPresent name="arrecadadorMovimentoItemHelper" property="ajustado">
														&nbsp;
												</logic:notPresent>
											</div>
										</td>
										<td width="9%">
											<div align="center">
												<logic:present name="arrecadadorMovimentoItemHelper" property="nvDescricaoOcorrencia">
													<bean:write name="arrecadadorMovimentoItemHelper" property="nvDescricaoOcorrencia" />
												</logic:present> 
												<logic:notPresent name="arrecadadorMovimentoItemHelper" property="nvDescricaoOcorrencia">
														&nbsp;
												</logic:notPresent>
											</div>
										</td>
										</logic:equal>
										</logic:present>
									</tr>
								</logic:iterate>
								
								<logic:notEmpty name="colecaoArrecadadorMovimentoItemHelper">
								<%if (cor.equalsIgnoreCase("#cbe5fe")) {
									cor = "#FFFFFF";%>
								<tr bgcolor="#FFFFFF">
									<%} else {
								cor = "#cbe5fe";%>
								<tr bgcolor="#cbe5fe">
									<%}%>
									<td bgcolor="#90c7fc" colspan="6">
										<div align="center" class="style9">
											<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
											<strong>Total</strong> </font>
										</div>
									</td>
									<td>
										<div align="right" class="style9">
										<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
										<%=session.getAttribute("valorDadosMovimento")%> </font></div>
									</td>
									<td>
										<div align="right" class="style9">
										<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
										<%=session.getAttribute("valorDadosPagamento")%> </font></div>
									</td>
								</tr>
							</logic:notEmpty>
							</table>
							</td>
						</tr>
					</table>
					</div>

				</logic:present></td>
			</tr>


			<tr>
				<td align="left">
				<input name="Submit222"
								class="bottonRightCol" value="Voltar" type="button"
								onclick="window.location.href='/gsan/exibirPesquisarItensMovimentoArrecadadorAction.do?arrecadadorMovimentoID=1022&limparPopup=OK';">	
					</td>
				<td align="right"><input type="button" name="fechar" class="bottonRightCol"
					value="Fechar" tabindex="2" style="width: 70px;"
					onclick="javascript:window.close();"></td>
			</tr>
			
			<tr>
				<td>&nbsp;</td>
			</tr>
		</table>

		</td>
	</tr>

</table>
</body>

</html:html>


