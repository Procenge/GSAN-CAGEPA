<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page import="gcom.util.Util" %>
<%@ page import="gcom.util.ConstantesSistema" isELIgnored="false"%>
<%@ page import="java.util.Collection" isELIgnored="false"%>
<%@page import="gcom.cadastro.aguaparatodos.FaturamentoHistoricoAguaParaTodos"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


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
	formName="FaturamentoHistoricoAguaParaTodosActionForm" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">
	
</script>

</head>

<logic:present name="closePage">
	<logic:equal name="closePage" value="S">
		<body leftmargin="5" topmargin="5" onload="window.close();">
	</logic:equal>
</logic:present>

<logic:notPresent name="closePage">
	<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampoFoco}');">
</logic:notPresent>

<html:form action="/consultarFaturamentoHistoricoAguaParaTodosAction.do" 
			name="FaturamentoHistoricoAguaParaTodosActionForm"
		type="gcom.gui.cadastro.aguaparatodos.FaturamentoHistoricoAguaParaTodosActionForm" method="post">
	<table width="720" border="0" cellpadding="0" cellspacing="5">
		<tr>
			<td width="720" valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Histórico Faturamento Água Para Todos</td>
					<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
			<table width="100%" border="0">
			<tr>
				<td>
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td height="0">
							<table width="100%" bgcolor="#90c7fc">
								<!--header da tabela interna -->
								<tr bordercolor="#FFFFFF" bgcolor="#90c7fc">
									<td width="20%" align="center"><strong>Código Referência</strong></td>
									<td width="50%" align="center"><strong>Código da Tarifa</strong></td>
									<td width="30%" align="center"><strong>Consumo</strong></td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td height="130">
							<div style="width: 100%; height: 100%; overflow: auto;">
							<table width="100%" align="left" bgcolor="#99CCFF">
								<!--corpo da segunda tabela-->
								<%int cont = 0;%>
								<logic:notEmpty name="FaturamentoHistoricoAguaParaTodosActionForm" property="colFaturamentoHistorico">
									<logic:iterate name="FaturamentoHistoricoAguaParaTodosActionForm" property="colFaturamentoHistorico" id="faturamentoHistoricoAguaParaTodos" type="FaturamentoHistoricoAguaParaTodos">
										<%cont = cont + 1;
										if (cont % 2 == 0) {%>
											<tr bgcolor="#cbe5fe">
										<%} else {%>
											<tr bgcolor="#FFFFFF">
										<%}%>
											<td width="20%" align="center">
												<div>${faturamentoHistoricoAguaParaTodos.anoMesReferencia}</div>
											</td>
											<td width="50%" align="center">
												<div>${faturamentoHistoricoAguaParaTodos.tarifa}</div>
											</td>
											<td width="30%" align="center">	
												<div>${faturamentoHistoricoAguaParaTodos.consumoMes}</div>
											</td>										
										</tr>
									</logic:iterate>
								</logic:notEmpty>
							</table>
							</div>
							</td>
						</tr>
					</table>
				</td>
				<tr>
					<td height="24">
					<div align="right"> 
						<input name="Button2" type="button" class="bottonRightCol" value="Fechar" onClick="window.close();">
					</div>
					</td>
				</tr>
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>
</html:form>
</body>
</html:html>

