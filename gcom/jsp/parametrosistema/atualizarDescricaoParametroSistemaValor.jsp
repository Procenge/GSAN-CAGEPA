<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>

<%@ page import="gcom.util.Pagina, gcom.util.ConstantesSistema"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<% if ( request.getAttribute("flagClose")!= null && request.getAttribute("flagClose").equals("true") ) { %> 
<script>window.close();</script> 
<% } %> 
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<script>

 var bCancel = false;

        
    
    function limparForm(){
    	var form = document.forms[0];
    	
    	form.descricaoValor.value = "";
    }
    
    function validarForm(form){
		form.submit();
    }
</script>
</head>

<body leftmargin="5" topmargin="5"
	onload="window.focus();resizePageSemLink(480, 298);setarFoco('${requestScope.descricaoValor}');">
	<html:form method="post" action="atualizarParametroSistemaValor.do"
		>

		<input name="chavePrimaria" type="hidden" id="chavePrimaria"
			value="<c:out value='${chavePrimaria}'/>" />
		<input name="valor" type="hidden" id="valor"
			value="<c:out value='${valor}'/>" />
		<input name="acao" type="hidden" id="acao"
			value="atualizarParametroSistemaValor">

		<table width="445" border="0" cellspacing="5" cellpadding="0">
			<tr>
				<td width="445" valign="top" class="centercoltext">
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
							<td class="parabg">Atualizar Descrição do Valor - Parâmetro</td>
							<td width="11"><img border="0"
								src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
						</tr>
					</table>
					<p>&nbsp;</p>
					<table width="100%" border="0">
						<tr>
							<td colspan="4">Para atualizar a descrição do valor 
								<strong> </strong>, informe os dados abaixo.</td>
							<td align="right"></td>
						</tr>
					</table>

					<table width="100%" border="0">

						<tr>
							<td height="0"><strong>Descrição:</strong></td>

							<td colspan="3"><textarea class="textAreaParametro" id="descricaoValor" name="descricaoValor" cols="40" rows="4" maxlength="255"><c:out value='${descricaoValor}'/></textarea></td>
						</tr>

						<tr>
							<td>&nbsp;</td>
							<td colspan="3">&nbsp;</td>
						</tr>
						<tr>
							<td height="24" colspan="3" width="80%"><INPUT TYPE="button"
								class="bottonRightCol" value="Limpar"
								onclick="limparForm();" />
								</td>
							<td align="right"><input type="submit" onclick="this.form.action.value='atualizarParametroSistemaValor.do?acao=atualizarParametroSistemaValor'"
									class="bottonRightCol" value="Atualizar" /></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>
