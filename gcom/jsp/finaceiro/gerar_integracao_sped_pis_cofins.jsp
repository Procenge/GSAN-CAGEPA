<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gsan"%>

<%@ page import="gcom.util.ConstantesSistema" %>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>

<html:form method="post" name="ExecutarGerarIntegracaoSpedPisCofinsDynaForm" action="/executarGerarIntegracaoSpedPisCofins.do" type="gcom.gui.financeiro.ExibirExecutarGerarIntegracaoSpedPisCofinsAction">

	<table width="100%" border="0">
		<tr>
			<td><strong>Referencia base:</strong></td>
			<td></td>
			<td align="left">
			     <!-- input name="referenciaBase" type="month" id="referenciaBase" value="" -->
			     <html:text property="referenciaBase" maxlength="7" size="7" onkeyup="mascaraAnoMes(this, event);"/>
			</td>
		</tr>

		<tr>
			<td colspan="2">Clique no botão para executar o batch:</td>
		</tr>
	</table>

	<table width="100%" border="0">


		<tr>
			<td colspan="2"><input type="button" name="Button"
				class="bottonRightCol" value="Cancelar" tabindex="5"
				onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
				style="width: 80px" /></td>

			<td align="right"><gsan:controleAcessoBotao name="Button" value="Executar" tabindex="6" onclick="javascript:submeterFormPadrao(document.forms[0]);" url="executarGerarIntegracaoSpedPisCofins.do" /></td>
		</tr>
	</table>

</html:form>