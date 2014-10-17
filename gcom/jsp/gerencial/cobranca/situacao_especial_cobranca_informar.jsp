<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="InformarResumoSituacaoEspecialFaturamentoActionForm" />

<script language="JavaScript">
 

</script>
</head>
<body leftmargin="5" topmargin="5">
<html:form action="/informarResumoSituacaoEspecialCobrancaAction.do"
	name="InformarResumoSituacaoEspecialCobrancaActionForm"
	type="gcom.gui.gerencial.faturamento.InformarResumoSituacaoEspecialCobrancaActionForm"
	method="post">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

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
			<td width="615" valign="top" class="centercoltext">
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
					<td class="parabg">Consultar Resumo de Situação Especial de Cobranca</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table border="0" width="100%">
				<tr>
					<td colspan="2">Para gerar o relatório ou a consulta, informe os dados abaixo:</td>
				</tr>
				<tr>
					<td><strong>Situação:</strong></td>
					<td align="right">
					<div align="left"><html:select
						property="situacaoTipo" style="width: 400px;"
						multiple="mutiple" size="6">
						<logic:present name="colecaoCobSitTipo">
							<html:option value="" />
							<html:options collection="colecaoCobSitTipo"
								labelProperty="descricao" property="id" />
						</logic:present>
					</html:select></div>
					</td>
				</tr>
				<tr>
				<td>
				</td>
				</tr>
								<tr>
					<td><strong>Motivo:<font color="#ff0000"></font></strong></td>

					<td align="right">
					<div align="left"><html:select
						property="situacaoMotivo" style="width: 400px;"
						multiple="mutiple" size="6">
						<logic:present name="colecaoCobSitMotivo">
							<html:option value="" />
							<html:options collection="colecaoCobSitMotivo"
								labelProperty="descricao" property="id" />
						</logic:present>
					</html:select></div>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td align="right">&nbsp;</td>
				</tr>
				
				<tr>
					<td><input name="Submit22"
						class="bottonRightCol" value="Limpar" type="button"
						onclick="window.location.href='/gsan/exibirInformarResumoSituacaoEspecialCobrancaAction.do?menu=sim';">
					</td>
					<td align="right" colspan="2"><input name="botao" class="bottonRightCol"
						value="Gerar Consulta/Relatório" onclick="document.forms[0].submit();"
						type="button"></td>
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
