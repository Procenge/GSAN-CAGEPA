<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page import="gcom.util.Util"%>
<%@ page import="gcom.cobranca.parcelamento.ParcelamentoFaixaValor" %>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript">
<!--
function fechar(){
  window.close();
}
-->
</script>

</head>

<body leftmargin="5" topmargin="5" onload="javascript:resizePageSemLink(500, 500);">

<table width="500" border="0" cellspacing="5" cellpadding="0">
	<tr>
		<td width="100%" valign="top" class="centercoltext">
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
					<td class="parabg">Consultar Percentual por Faixa de Valor</td>

					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
				<tr>
					<td height="5" colspan="3"></td>
				</tr>
			</table>
			<p>&nbsp;</p>

			<table width="100%">
				<tr>
					<td colspan="3">
						<table width="100%">
							<!-- início da tabela de Percentual Por Faixa de Valor -->
								<tr >
									<td colspan= "2">
										<table width="100%" border="0" bgcolor="#90c7fc">
											<tr bgcolor="#90c7fc" height="18">
												<td align="center"><strong>Valor Mínimo</strong></td>
												<td align="center"><strong>Valor Fixo de Entrada</strong></td>
												<td align="center"><strong>Percentual</strong></td>
												<td align="center"><strong>Opções do Débito</strong></td>
											</tr>
						
											
											<logic:present name="collectionParcelamentoFaixaValorConsulta">
												<%int cont = 1;%>
												<logic:iterate name="collectionParcelamentoFaixaValorConsulta" 
												id="parcelamentoFaixaValor"
												type="ParcelamentoFaixaValor">
														<%cont = cont + 1;
															if (cont % 2 == 0) {%>
														<tr bgcolor="#FFFFFF">
															<%} else {
						
															%>
														<tr bgcolor="#cbe5fe">
															<%}%>
																						       								       
													       <td align="center">
																<div>
																	<bean:write	name="parcelamentoFaixaValor" property="valorFaixa" formatKey="money.format" />
																</div>
														   </td>
														   <td align="center">
																<div>
																	<bean:write	name="parcelamentoFaixaValor" property="valorFixoEntrada" formatKey="money.format" />
																</div>
														   </td>
														   <td align="center">
																<div>
																	<bean:write	name="parcelamentoFaixaValor" property="percentualFaixa" formatKey="money.format" />
																</div>
														    </td>
														    <td align="center">
																<div>
																	<% if (parcelamentoFaixaValor.getIndicadorPercentualFaixaValor().intValue() == 1){ %>
																		<%="Original"%>
																	<% } else { %>
																		<%="Atualizado"%>
																	<% }%>
																</div>
														    </td>
														</tr>
												</logic:iterate>
											</logic:present>
											
											</table>
									</td>
								</tr> 
							<!-- final da tabela de Percentual Por Faixa de Valor -->
							</table>
					</td>
				</tr>
			</table>

		
			<table width="100%">
				<tr>
					<td colspan="2" align="right" width="90%">
						<input type="button" name="" value="Fechar" class="bottonRightCol" 
						onclick="javascript:fechar();"/>
					</td>				
				</tr>
			</table>

		</td>
	</tr>
</table>

</body>
</html:html>
