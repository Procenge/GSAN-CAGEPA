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
<script language="JavaScript"	src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript">
function fechar(){
	window.close();
}
</script>
</head>

<body leftmargin="5" topmargin="5" onload="resizePageSemLink(950, 500);">

<html:form action="/pesquisarImovelAction" method="post" onsubmit="return validatePesquisarActionForm(this);">
	<table width="760" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="760" valign="top" class="centercoltext">
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
					<td class="parabg">Alterações de Inscrição de Imóvel</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<!-- Grid com as alterações de inscrição dos Imóveis -->
			<table width="100%" border="0">
			<tr>
				<td colspan="3">
				<table width="100%" align="center" bgcolor="#90c7fc" border="0">
					<tr bordercolor="000000">
						<td align="center" bgcolor="#79bbfd" rowspan="2"><strong>Imóvel</strong></td>
						<td align="center" bgcolor="#79bbfd" colspan="8"><strong>Inscrição Anterior</strong></td>
						<td align="center" bgcolor="#79bbfd" colspan="8"><strong>Inscrição Atual</strong></td>
					</tr>
					<tr bordercolor="#cbe5fe">
						<td align="center" bgcolor="#cbe5fe"><strong>Localidade</strong></td>
						<td align="center" bgcolor="#cbe5fe"><strong>Setor Comercial</strong></td>
						<td align="center" bgcolor="#cbe5fe"><strong>Quadra</strong></td>
						<td align="center" bgcolor="#cbe5fe"><strong>Lote</strong></td>
						<td align="center" bgcolor="#cbe5fe"><strong>Sublote</strong></td>
						<td align="center" bgcolor="#cbe5fe"><strong>Testada do Lote</strong></td>
						<td align="center" bgcolor="#cbe5fe"><strong>Rota</strong></td>
						<td align="center" bgcolor="#cbe5fe"><strong>Grupo de Faturamento</strong></td>

						<td align="center" bgcolor="#cbe5fe"><strong>Localidade</strong></td>
						<td align="center" bgcolor="#cbe5fe"><strong>Setor Comercial</strong></td>
						<td align="center" bgcolor="#cbe5fe"><strong>Quadra</strong></td>
						<td align="center" bgcolor="#cbe5fe"><strong>Lote</strong></td>
						<td align="center" bgcolor="#cbe5fe"><strong>Sublote</strong></td>
						<td align="center" bgcolor="#cbe5fe"><strong>Testada do Lote</strong></td>
						<td align="center" bgcolor="#cbe5fe"><strong>Rota</strong></td>
						<td align="center" bgcolor="#cbe5fe"><strong>Grupo de Faturamento</strong></td>
					</tr>
					
					<%String cor = "#cbe5fe";%>	
					<logic:present name="listaImoveisComAlteracaoAgendada">
						<logic:iterate name="listaImoveisComAlteracaoAgendada" id="imovelAlterarInscricaoHelper">
					
						<%if (cor.equalsIgnoreCase("#cbe5fe")) {
								cor = "#FFFFFF";%>
							<tr bgcolor="#FFFFFF">
						<%} else {
								cor = "#cbe5fe";%>
							<tr bgcolor="#cbe5fe">
						<%}%>
							<td align="center"><bean:write name="imovelAlterarInscricaoHelper" property="idImovel"/></td>

							<td align="right"><bean:write name="imovelAlterarInscricaoHelper" property="idLocalidadeAnterior"/></td>
							<td align="right"><bean:write name="imovelAlterarInscricaoHelper" property="codigoSetorComercialAnterior"/></td>
							<td align="right"><bean:write name="imovelAlterarInscricaoHelper" property="numeroQuadraAnterior"/></td>
							<td align="right"><bean:write name="imovelAlterarInscricaoHelper" property="loteAnterior"/></td>
							<td align="right"><bean:write name="imovelAlterarInscricaoHelper" property="subLoteAnterior"/></td>
							<td align="right"><bean:write name="imovelAlterarInscricaoHelper" property="testadaLoteAnterior" /></td>
							<td align="right"><bean:write name="imovelAlterarInscricaoHelper" property="codigoRotaAnterior" /></td>
							<td align="right"><bean:write name="imovelAlterarInscricaoHelper" property="idGrupoFaturamentoAnterior" /></td>

							<td align="right"><bean:write name="imovelAlterarInscricaoHelper" property="idLocalidadeAtual"/></td>
							<td align="right"><bean:write name="imovelAlterarInscricaoHelper" property="codigoSetorComercialAtual"/></td>
							<td align="right"><bean:write name="imovelAlterarInscricaoHelper" property="numeroQuadraAtual"/></td>
							<td align="right"><bean:write name="imovelAlterarInscricaoHelper" property="loteAtual"/></td>
							<td align="right"><bean:write name="imovelAlterarInscricaoHelper" property="subLoteAtual"/></td>
							<td align="right"><bean:write name="imovelAlterarInscricaoHelper" property="testadaLoteAtual" /></td>
							<td align="right"><bean:write name="imovelAlterarInscricaoHelper" property="codigoRotaAtual" /></td>
							<td align="right"><bean:write name="imovelAlterarInscricaoHelper" property="idGrupoFaturamentoAtual" /></td>
						</tr>
						</logic:iterate>
					</logic:present>
				</table>
				</td>
			</tr>

				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="3" align="right"><input name="Button" type="button"
						class="bottonRightCol" value="Fechar"
						onClick="javascript:fechar();"></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>
</html:form>
</body>
</html:html>
