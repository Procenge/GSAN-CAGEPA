<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="gcom.micromedicao.Rota"%>
<%@ page import="gcom.util.ConstantesSistema"%>

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
	formName="EncerrarFaturamentoActionForm" />

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript">

	function desfazer() {
		var form = document.forms[0];
		form.action = "/gsan/exibirEncerrarFaturamentoAction.do";
		submeterFormPadrao(form);
	}
</script>

</head>

<body leftmargin="5" topmargin="5">

<html:form action="/exibirEncerrarFaturamentoAction"
	name="EncerrarFaturamentoActionForm"
	type="gcom.gui.faturamento.EncerrarFaturamentoActionForm"
	method="post">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="130" valign="top" class="leftcoltext">
			<div align="center"><%@ include
				file="/jsp/util/informacoes_usuario.jsp"%> <%@ include
				file="/jsp/util/mensagens.jsp"%></div>
			</td>

			<td width="625" valign="top" class="centercoltext">

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Rotas</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>

			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td colspan="5"></td>
				</tr>

				<tr>
					<td colspan="5">
					<table width="100%" align="center" bgcolor="#90c7fc" border="0">
						<logic:equal name="tipoConsulta" value="<%=ConstantesSistema.CONSULTA_ROTAS_FATURADAS + "" %>">
							<tr bordercolor="#79bbfd">
								<td colspan="5" align="center" bgcolor="#79bbfd">
									<strong>Rotas Faturadas no Grupo <bean:write name="EncerrarFaturamentoActionForm" property="idFaturamentoGrupo" /></strong>
								</td>
							</tr>
						</logic:equal>
						<logic:notEqual name="tipoConsulta" value="<%=ConstantesSistema.CONSULTA_ROTAS_FATURADAS + "" %>">
							<tr bordercolor="#79bbfd">
								<td colspan="5" align="center" bgcolor="#79bbfd">
									<strong>Rotas do Grupo <bean:write name="EncerrarFaturamentoActionForm" property="idFaturamentoGrupo" /> Não Faturadas</strong>
								</td>
							</tr>
						</logic:notEqual>
						<tr bordercolor="#000000">
							<td width="40%" bgcolor="#90c7fc" align="center">
							<div class="style9">
								<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
									<strong>Código da Rota</strong>
								</font>
							</div>
							</td>
							<td width="13%" bgcolor="#90c7fc" align="center">
							<div class="style9">
								<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
									<strong>Grupo de Faturamento da Rota </strong>
								</font>
							</div>
							</td>
							<td width="17%" bgcolor="#90c7fc" align="center">
							<div class="style9">
								<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
									<strong>Localidade</strong>
								</font>
							</div>
							</td>
							<td width="15%" bgcolor="#90c7fc" align="center">
							<div class="style9">
								<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
									<strong>Setor Comercial</strong>
								</font>
							</div>
							</td>							
						</tr>
						<tr>
							<td width="100%" colspan="5">
							<div style="width: 100%; height: 100%; overflow: auto;">
							<table width="100%" align="left" bgcolor="#99CCFF">
								<%int cont = 0;	%>
								<logic:notEmpty name="colecaoRota">
									<logic:iterate name="colecaoRota" id="rota" type="Rota">
										<%cont = cont + 1;
										if (cont % 2 == 0) {%>
										<tr bgcolor="#cbe5fe">
										<%} else {%>
										<tr bgcolor="#FFFFFF">
										<%}%>
												<td width="40%" align="left">
												<div class="style9">
													<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
														<bean:write name="rota" property="id" />
													</font>
												</div>
												</td>
												<td width="13%" align="center">
												<div class="style9">
													<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
														<bean:write name="rota" property="faturamentoGrupo.id" />
													</font>
												</div>
												</td>
												<td width="17%" align="center">
												<div class="style9">
													<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
														<bean:write name="rota" property="setorComercial.localidade.id" />
													</font>
												</div>
												</td>
												<td width="15%" align="center">
												<div class="style9">
													<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
														<bean:write name="rota" property="setorComercial.codigo" />
													</font>
												</div>												
											</tr>
									</logic:iterate>
								</logic:notEmpty>
							</table>
							</div>
							</td>
						</tr>
					</table>
					</td>
				</tr>

				<tr>
					<td colspan="2" width="100%">
					<table width="100%">
						<tr>
							<td>
								<input name="Submit22" class="bottonRightCol" value="Voltar"
									type="button" onclick="desfazer();">
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

	<%@ include file="/jsp/util/rodape.jsp"%>

</html:form>

</body>
</html:html>
