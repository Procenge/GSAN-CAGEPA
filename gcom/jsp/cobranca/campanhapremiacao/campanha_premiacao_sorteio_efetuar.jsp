<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>
<%@ page import="gcom.util.ConstantesSistema"%>
<%@ page import="gcom.gui.GcomAction"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<head>
<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="EfetuarSorteioCampanhaPremiacaoActionForm" dynamicJavascript="false"
	/>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script>
<!--
var bCancel = false;

function validateClienteActionForm(form) {
	if (bCancel)
    	return true;
	else
    	return validateLong(form) 
    	&& validateCaracterEspecial(form) 
    	&& verificarTamanhoTelefone();
}

function validaForm(){
	
	var form = document.forms[0];
}


//-->
</script>
<body leftmargin="5" topmargin="5"
	onload="setarFoco('${requestScope.nomeCampo}');">
<div id="formDiv">
<html:form action="/efetuarSorteioCampanhaPremiacaoAction" method="post">
	
	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<input type="hidden" name="cancelarValidacao" value="true" />
	<table width="770" border="0" cellspacing="4" cellpadding="0">
		<tr>
			<td width="149" valign="top" class="leftcoltext">
			<input type="hidden" name="numeroPagina" value="4" />
			<div align="center">

			<%@ include file="/jsp/util/informacoes_usuario.jsp"%>

			<%@ include file="/jsp/util/mensagens.jsp"%>

			</div>
			</td>
			<td valign="top" class="centercoltext">
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
					<td class="parabg">Efetuar Sorteio da Campanha de Premiação</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>


			<table width="100%" border="0">
				<tr>
					<td colspan="2"></td>
					<td align="right"></td>
    			</tr>
    			<tr>
					<td>
						<strong> 
							<font color="#ff0000"> 
								<input type="button"
										name="ButtonCancelar" 
										class="bottonRightCol" 
										value="Cancelar"
										onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
										
								<input	name="Submit22" 
										class="bottonRightCol"
										value="Iniciar Sorteio" 
										type="button"
										onclick="window.location.href='/gsan/efetuarSorteioCampanhaPremiacaoAction.do';">								
							</font>
						</strong>
					</td>
					<td align="right"></td>
					<td align="right"></td>
    			</tr>
    		</table>
	</table>
	<%@ include file="/jsp/util/tooltip.jsp"%>
	<%@ include file="/jsp/util/rodape.jsp"%>
	</html:form>
	</div>
</body>
</html:html>