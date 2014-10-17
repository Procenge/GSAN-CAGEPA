<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="PesquisarTabelaActionForm" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">
function limparForm(){

	var form = document.forms[0];
	form.nomeTabela.value = '';
	form.descricaoTabela.value = '';
	
	}

function validarForm(form){
	var form = document.forms[0];
	submeterFormPadrao(form);
	
}

</script>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body leftmargin="5" topmargin="5" onload="resizePageSemLink(485, 280);">

<html:form action="/pesquisarTabelaAction"  name="PesquisarTabelaActionForm"
	type="gcom.gui.seguranca.acesso.PesquisarTabelaAction"  method="post">




	<table width="470" border="0" cellspacing="4" cellpadding="0">
		<tr>
			<td width="770" valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td width="11">
						<img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" />
					</td>
					<td class="parabg">Pesquisar Tabela</td>
					<td width="11">
						<img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" />
					</td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td height="28">Preencha os campos para pesquisar uma Tabela:</td>
					<td align="right"></td>
    			</tr>
    		</table>
    
    		<table width="100%" border="0">
				<tr>
					<td height="28"><strong>C&oacute;digo:</strong></td>
					<td>
						<html:text maxlength="6" property="idTabela"  onblur="javascript:verificaNumero(this);" size="7" tabindex="1" />
					</td>
				</tr>				
				
				<tr>
					<td height="28"><strong>Nome:</strong></td>
					<td>
						<html:text maxlength="40" property="nomeTabela" size="40" tabindex="2" />
					</td>
				</tr>
				
				<tr>
					<td height="28"><strong>Descrição:</strong></td>
					<td>
						<html:text maxlength="40" property="descricaoTabela" size="40" tabindex="3" />
					</td>
				</tr>
				
			<tr>
					<td height="24" colspan="3" width="80%">
						<input type="button" class="bottonRightCol" align="left" tabindex="4" value="Limpar" onclick="javascript:limparForm();"/>&nbsp;
					</td>
					<td>
						
						<input type="button" name="Button" class="bottonRightCol" align="right" tabindex="5" value="Pesquisar"
							onClick="javascript:validarForm(this);" />
					</td>
				</tr>
			</table>
				
			</td>
		</tr>
	</table>


</html:form>
</body>
</html:html>
