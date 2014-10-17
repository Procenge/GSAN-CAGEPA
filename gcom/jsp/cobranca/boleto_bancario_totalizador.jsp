<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>

<%@page import="java.util.Collection"%>
<%@page import="gcom.util.Util"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>/validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script>

</script>
</head>

<body leftmargin="5" topmargin="5">

<html:form action="/exibirTotalizadorBoletoBancarioAction" method="post">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="130" valign="top" class="leftcoltext">
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

			<table>
				<tr>
					<td></td>

				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">Totalizador Boleto Bancário</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>
				</tr>
			</table>

			<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="7" height="23"><font style="font-size: 10px;"
						color="#000000" face="Verdana, Arial, Helvetica, sans-serif"> <strong>Dados do boleto bancário pelo imóvel:</strong> </font></td>
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
									<td align="center"><Font color="#000000"><strong>Arrecadador</strong></font></td>
									<td align="center"><font color="#000000"><strong>Imóvel</strong></font></td>
									<td align="center"><font color="#000000"><strong>Valor Total</strong></font></td>
								</tr>
								<pg:pager isOffset="true" index="half-full" maxIndexPages="10"
									export="currentPageNumber=pageNumber;pageOffset"
									maxPageItems="10" items="${sessionScope.totalRegistros}">
									<pg:param name="pg" />
									<pg:param name="q" />
									<%int cont = 0;%>
									<logic:iterate name="colecaoBoletoBancarioTotalizadorHelper" id="helper">
										<pg:item>
											<%cont = cont + 1;
												if (cont % 2 == 0) {%>
											<tr bgcolor="#FFFFFF">
												<%} else {%>
											<tr bgcolor="#cbe5fe">
												<%}%>
												<td>
													<div align="center">
														<bean:write name="helper" property="codigoAgenteArrecadador"/>
													</div>
												</td>
												<td>
													<div align="center">
														<a href="/gsan/exibirManterBoletoBancarioAction.do?codigoAgenteArrecadador=<bean:write name="helper" property="codigoAgenteArrecadador"/>&idImovel=<bean:write name="helper" property="idImovel"/>">
															<bean:write name="helper" property="idImovel"/>
														</a>
													</div>
												</td>
												<td>
													<div align="right">
														<bean:write name="helper" property="valorTotalDebito" formatKey="money.format"/>
													</div>
												</td>
											</tr>
										</pg:item>
									</logic:iterate>
							</table>
							<table width="100%" border="0">
								<tr>
									<td align="center"><strong><%@ include
										file="/jsp/util/indice_pager_novo.jsp"%></strong></td>
								</tr>
								</pg:pager>
								<tr>
									<td>
										<input name="button" type="button" class="bottonRightCol" value="Voltar Filtro"	onclick="window.location.href='<html:rewrite page="/exibirFiltrarBoletoBancarioAction.do"/>'" align="left" style="width: 80px;">
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
			</td>
		</tr>
	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>

</html:form>
</body>
</html:html>