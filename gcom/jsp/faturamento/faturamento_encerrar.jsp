<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="gcom.cobranca.bean.GupoFaturamentoHelper"%>
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
	function required () {
		this.aa = new Array("referencia", "Informe Referência Atual do Faturamento.", new Function ("varName", " return this[varName];"));
		this.ab = new Array("grupoSituacao", "Informe Situação dos Grupos de Faturamento.", new Function ("varName", " return this[varName];"));
	}

	function validarForm(formulario) {
		var form = document.forms[0];

		if (validateRequired(form)) {
			formulario.action = "/gsan/encerrarFaturamentoAction.do";
			submeterFormPadrao(formulario);
		}
	}

	function atualizarSelecao() {
		var form = document.forms[0];
		form.action = "/gsan/exibirEncerrarFaturamentoAction.do?operacao=1";
		submeterFormPadrao(form);
	}

	function encerrarFaturamento() {
		var form = document.forms[0];
		form.action = "/gsan/exibirEncerrarFaturamentoAction.do?operacao=2";
		submeterFormPadrao(form);
	}

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
					<td class="parabg">Encerrar Faturamento</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>

			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para encerrar o faturamento, informe os dados abaixo:</td>
				</tr>
			</table>

			<table width="100%" border="0">
				<tr>
					<td colspan="4">
					<table width="100%" align="center" border="0">
						<tr>
							<td width="40%"><strong>Referência Atual do Faturamento:<font color="#FF0000">*</font></strong></td>
							<td width="60%">
								<html:text property="referencia" readonly="true" style="background-color:#EFEFEF; border:0;" size="7"/>
							</td>
						</tr>

						<tr>
							<td><strong>Situação dos Grupos de Faturamento:<font color="#FF0000">*</font></strong></td>
						    <td>
						  		<html:radio property="grupoSituacao" value="<%=ConstantesSistema.FATURADOS.toString()%>" onclick="atualizarSelecao();"/><strong>Faturados
								<html:radio property="grupoSituacao" value="<%=ConstantesSistema.NAO_FATURADOS.toString()%>" onclick="atualizarSelecao();"/>Não Faturados
								<html:radio property="grupoSituacao" value="<%=ConstantesSistema.TODOS.toString()%>" onclick="atualizarSelecao();"/>Todos</strong>
						   </td>
						</tr>
					</table>
					</td>
				</tr>

				<tr>
					<td colspan="5"></td>
				</tr>

				<tr>
					<td colspan="5">
					<table width="100%" align="center" bgcolor="#90c7fc" border="0">
						<tr bordercolor="#79bbfd">
							<td colspan="5" align="center" bgcolor="#79bbfd">
								<strong>Grupos de Faturamento</strong>
							</td>
						</tr>
						<tr bordercolor="#000000">
							<td width="40%" bgcolor="#90c7fc" align="center">
							<div class="style9">
								<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
									<strong>Grupo</strong>
								</font>
							</div>
							</td>
							<td width="13%" bgcolor="#90c7fc" align="center">
							<div class="style9">
								<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
									<strong>Referência</strong>
								</font>
							</div>
							</td>
							<td width="17%" bgcolor="#90c7fc" align="center">
							<div class="style9">
								<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
									<strong>Situação</strong>
								</font>
							</div>
							</td>
							<td width="15%" bgcolor="#90c7fc" align="center">
							<div class="style9">
								<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
									<strong>Data Último Faturamento</strong>
								</font>
							</div>
							</td>
							<td width="15%" bgcolor="#90c7fc" align="center">
							<div class="style9">
								<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
									<strong>Rotas do Grupo Não Faturadas</strong>
								</font>
							</div>
							</td>
						</tr>
						<tr>
							<td width="100%" colspan="5">
							<div style="width: 100%; height: 100%; overflow: auto;">
							<table width="100%" align="left" bgcolor="#99CCFF">
								<%int cont = 0;	%>
								<logic:notEmpty name="colecaoGupoFaturamentoHelper">
									<logic:iterate name="colecaoGupoFaturamentoHelper" id="helper" type="GupoFaturamentoHelper">
										<%cont = cont + 1;
										if (cont % 2 == 0) {%>
										<tr bgcolor="#cbe5fe">
										<%} else {%>
										<tr bgcolor="#FFFFFF">
										<%}%>
												<td width="40%" align="left">
												<div class="style9">
													<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
														<logic:equal name="helper" property="situacao" value="<%=ConstantesSistema.SITUACAO_GRUPO_FATURADO%>">
															<a href="exibirRotasDoGrupoAction.do?tipoConsulta=<%=ConstantesSistema.CONSULTA_ROTAS_FATURADAS %>&idFaturamentoGrupo=<bean:write
																 name="helper" property="id" />">
																<bean:write name="helper" property="descricao" />&nbsp;
															</a>
														</logic:equal>
														<logic:notEqual name="helper" property="situacao" value="<%=ConstantesSistema.SITUACAO_GRUPO_FATURADO%>">
															<bean:write name="helper" property="descricao" />
														</logic:notEqual>
													</font>
												</div>
												</td>
												<td width="13%" align="center">
												<div class="style9">
													<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
														<bean:write name="helper" property="referencia" />
													</font>
												</div>
												</td>
												<td width="17%" align="center">
												<div class="style9">
													<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
														<bean:write name="helper" property="situacao" />
													</font>
												</div>
												</td>
												<td width="15%" align="center">
												<div class="style9">
													<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
														<bean:write name="helper" property="dataUltimoFaturamento" />
													</font>
												</div>
												<td width="15%" align="center">
												<div class="style9">
													<font color="#000000" style="font-size: 9px" face="Verdana, Arial, Helvetica, sans-serif">
														<logic:equal name="helper" property="situacao" value="<%=ConstantesSistema.SITUACAO_GRUPO_FATURADO%>">
															<a href="exibirRotasDoGrupoAction.do?tipoConsulta=<%=ConstantesSistema.CONSULTA_ROTAS_NAO_FATURADAS %>&idFaturamentoGrupo=<bean:write
																 name="helper" property="id" />">
																<bean:write name="helper" property="qtRotasNaoFaturadas" />
															</a>
														</logic:equal>
														<logic:notEqual name="helper" property="situacao" value="<%=ConstantesSistema.SITUACAO_GRUPO_FATURADO%>">
															<bean:write name="helper" property="qtRotasNaoFaturadas" />
														</logic:notEqual>														
													</font>
												</div>
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
				</tr>

				<tr>
					<td align="right">
					<div align="left"><strong><font color="#FF0000">*</font></strong>Campos
					obrigat&oacute;rios</div>
					</td>
				</tr>
				<tr>
					<td colspan="2" width="100%">
					<table width="100%">
						<tr>
							<td>
								<input name="Submit22" class="bottonRightCol" value="Desfazer"
									type="button" onclick="desfazer();">
								<input name="Submit23" class="bottonRightCol" value="Cancelar"
									type="button" onclick="window.location.href='/gsan/telaPrincipal.do'">
							</td>
							<td align="right">
								<input name="Submit24" class="bottonRightCol" value="Encerrar Faturamento"
									type="button" onclick="encerrarFaturamento();">
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
