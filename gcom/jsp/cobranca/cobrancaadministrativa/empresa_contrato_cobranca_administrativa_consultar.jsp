<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="gcom.util.ConstantesSistema"%>
<%@page import="gcom.cobranca.contrato.CobrancaContrato"%>

<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false"
	formName="ConsultarDadosContratoEmpresaCobrancaAdministrativaActionForm" />

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript">
function fechar(){
	window.close();
}
</script>

</head>

<body leftmargin="5" topmargin="5" onload="resizePageSemLink(487,320);">

<html:form action="/exibirConsultarDadosContratoEmpresaCobrancaAdministrativaAction"
	name="ConsultarDadosContratoEmpresaCobrancaAdministrativaActionForm"
	type="org.apache.struts.action.DynaActionForm"
	method="post">

	<table width="450" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="450" valign="top" class="centercoltext">
					
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td width="11"><img border="0"
								src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
							<td class="parabg">Consultar Dados do Contrato da Empresa</td>
							<td width="11"><img border="0"
								src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
						</tr>
					</table>
					
					<p>&nbsp;</p>
					
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td><strong>Empresa:</strong></td>		
							<td>
								<html:text maxlength="9" 
									property="nomeEmpresa" 
									size="50"
									readonly="true"/>
							</td>
						</tr>
					</table>
		
					<p>&nbsp;</p>
		
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td colspan="7" height="23"><font style="font-size: 10px;"
								color="#000000" face="Verdana, Arial, Helvetica, sans-serif"> <strong>Dados dos Contratos:</strong> </font></td>
						</tr>
						<tr>
							<td colspan="7" bgcolor="#000000" height="2" valign="baseline"></td>
						</tr>
						<tr>
							<td>
								<table width="100%" align="center" bgcolor="#90c7fc" border="0"
									cellpadding="0" cellspacing="0">
									<tr bgcolor="#cbe5fe">
										<td width="100%" align="center">
											<table width="100%" bgcolor="#90c7fc">
												<tr bordercolor="#FFFFFF" bgcolor="#79BBFD">
					
													<td align="center"><font color="#000000"><strong>Número</strong></font></td>
													<td align="center"><font color="#000000"><strong>Dt.Contrato Início</strong></font></td>
													<td align="center"><font color="#000000"><strong>Dt.Contrato Fim</strong></font></td>
													<td align="center"><font color="#000000"><strong>Tipo Remun.</strong></font></td>
													<td align="center"><font color="#000000"><strong>Dt.Encer.</strong></font></td>
													<td align="center"><font color="#000000"><strong>Mot. Encer.</strong></font></td>
													
												</tr>
											
												<%int cont = 0;%>
												<logic:iterate name="colecaoCobrancaContrato" id="cobrancaContrato" type="CobrancaContrato">
													
														<%cont = cont + 1;
															if (cont % 2 == 0) {%>
														<tr bgcolor="#FFFFFF">
															<%} else {%>
														<tr bgcolor="#cbe5fe">
															<%}%>
				
															<td>
																<div align="center">
				                                                   	<bean:write name="cobrancaContrato" property="numeroContrato"/>
																</div>
															</td>
															<td>
																<div align="center">
																	<bean:write name="cobrancaContrato" property="dataInicial" formatKey="date.format"/>
																</div>
															</td>
															<td>
																<div align="center">
																	<logic:notEmpty name="cobrancaContrato" property="dataFinal">
																		<bean:write name="cobrancaContrato" property="dataFinal" formatKey="date.format"/>
																	</logic:notEmpty>
																</div>
															</td>
															<td>
																<div align="left">
																	<bean:write name="cobrancaContrato" property="contratoTipoRemuneracao.descricao"/>
																</div>
															</td>
															<td>
																<div align="center">
																	<logic:notEmpty name="cobrancaContrato" property="dataEncerramento">
																			<bean:write name="cobrancaContrato" property="dataEncerramento" formatKey="date.format"/>
																	</logic:notEmpty>
																</div>
															</td>
															<td>
																<div align="left">														
																	<logic:notEmpty name="cobrancaContrato" property="contratoMotivoCancelamento">
																			<bean:write name="cobrancaContrato" property="contratoMotivoCancelamento.descricaoMotivoCancelContrato" />
																	</logic:notEmpty>												
																</div>
															</td>
														</tr>
													
											</logic:iterate>
										</table>
									</td>
								</tr>
							</table>
							<p>&nbsp;</p>
						</td>
					</tr>
				</table>
			
				<p>&nbsp;</p>
				
				<table border="0" width="100%"> 			
					<tr>
						<td colspan="3">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="3" align="right"><input name="Button" type="button"
							class="bottonRightCol" value="Fechar"
							onClick="javascript:fechar();"></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>

</html:form>

</body>
</html:html>
