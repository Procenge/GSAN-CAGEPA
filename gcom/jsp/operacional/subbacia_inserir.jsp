<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@ page import="gcom.util.ConstantesSistema"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
	
<script type='text/javascript' src='<c:out value='${pageContext.request.contextPath}'/>/dwr/engine.js'> </script>
<script type='text/javascript' src='<c:out value='${pageContext.request.contextPath}'/>/dwr/util.js'> </script>
<script type='text/javascript' src='<c:out value='${pageContext.request.contextPath}'/>/dwr/interface/AjaxService.js'> </script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false"
	formName="InserirSubBaciaActionForm" />

<script>
	function validarForm(form) {
		if(validateInserirSubBaciaActionForm(form)) {
			submeterFormPadrao(form);
		}
	}

	function carregarComboSubSistema() {
		form = document.forms[0];
		form.action = "/gsan/exibirInserirSubBaciaAction.do?consultaSistema=1";
		form.submit();
	}

	function carregarComboBacia() {
		form = document.forms[0];
		form.action = "/gsan/exibirInserirSubBaciaAction.do?consultaSubSistema=1";
		form.submit();
	}
</script>

</head>

<body leftmargin="5" topmargin="5"
	onload="setarFoco('${requestScope.nomeCampo}');">

<html:form action="/inserirSubBaciaAction.do" name="InserirSubBaciaActionForm"
	type="gcom.gui.operacional.InserirSubBaciaActionForm">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="130" valign="top" class="leftcoltext">
			<div align="center">
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>

			<%@ include file="/jsp/util/informacoes_usuario.jsp"%>

			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>

			<%@ include file="/jsp/util/mensagens.jsp"%>

			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			</div>
			</td>

			<td width="625" valign="top" class="centercoltext">
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
					<td class="parabg">Inserir Sub-Bacia</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>

			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td>Para adicionar uma sub-bacia, informe os dados abaixo:</td>
					<td align="right"></td>
				</tr>
			</table>

			<table width="100%" border="0">
				<tr>
					<td><strong>Código:<font color="#FF0000">*</font></strong></td>
					<td><html:text property="codigo" size="2" maxlength="2"
						onkeypress="return isCampoNumerico(event);" tabindex="1" /></td>
				</tr>
				<tr>
					<td><strong>Decrição:<font color="#FF0000">*</font></strong></td>
					<td><html:text property="descricao" size="30" maxlength="30"
						tabindex="2" /></td>
				</tr>
				<tr>
					<td><strong>Decrição Abreviada:</strong></td>
					<td><html:text property="descricaoAbreviada" size="10"
						maxlength="6" tabindex="3" /></td>
				</tr>
				<tr>
					<td><strong>Sistema de Esgoto:<font
						color="#FF0000">*</font></strong></td>
					<td><html:select property="idSistema"
						style="width: 200px;" tabindex="4" onchange="limparComboGenerico(idBacia);carregarComboSubSistema();">
						<html:option
							value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present name="colecaoSistema">
							<html:options collection="colecaoSistema"
								labelProperty="descricaoComId" property="id" />
						</logic:present>
					</html:select></td>
				</tr>
				<tr>
					<td><strong>Sub-Sistema de Esgoto:<font
						color="#FF0000">*</font></strong></td>
					<td><html:select property="idSubSistema"
						style="width: 200px;" tabindex="4" onchange="carregarComboBacia();">
						<html:option
							value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present name="colecaoSubSistema">
							<html:options collection="colecaoSubSistema"
								labelProperty="descricaoComCodigo" property="id" />
						</logic:present>
					</html:select></td>
				</tr>
				<tr>
					<td><strong>Bacia:<font
						color="#FF0000">*</font></strong></td>
					<td><html:select property="idBacia"
						style="width: 200px;" tabindex="6">
						<html:option
							value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present name="colecaoBacia">
							<html:options collection="colecaoBacia"
								labelProperty="descricaoComCodigoEId" property="id" />
						</logic:present>
					</html:select></td>
				</tr>
				<tr>
					<td><strong>Extens&atilde;o (m):<font color="#FF0000">*</font></strong></td>
					<td><html:text property="extensao" size="10" maxlength="10"
						onkeyup="javascript:formataValorDecimal(this, 4, event);" onblur="javascript:formataValorDecimal(this, 4, null);" tabindex="7" /></td>
				</tr>
				<tr>
					<td><strong>Di&acirc;metro (mm):<font color="#FF0000">*</font></strong></td>
					<td><html:text property="diametro" size="7" maxlength="7"
						onkeyup="javascript:formataValorDecimal(this, 4, event);" onblur="javascript:formataValorDecimal(this, 4, null);" tabindex="8" /></td>
				</tr>
				<tr>
					<td><strong>Material:<font
						color="#FF0000">*</font></strong></td>
					<td><html:select property="idMaterial"
						style="width: 200px;" tabindex="9">
						<html:option
							value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present name="colecaoMaterialRedeEsgoto">
							<html:options collection="colecaoMaterialRedeEsgoto"
								labelProperty="descricaoComId" property="id" />
						</logic:present>
					</html:select></td>
				</tr>
				<tr>
					<td></td>
					<td><font color="#FF0000">*</font>&nbsp;Campos obrigatórios</td>
				</tr>
				<tr>
					<td colspan="2"><input type="button" name="Button"
						class="bottonRightCol" value="Desfazer" tabindex="10"
						onClick="javascript:window.location.href='/gsan/exibirInserirSubBaciaAction.do?desfazer=S'"
						style="width: 80px" />&nbsp; <input type="button" name="Button"
						class="bottonRightCol" value="Cancelar" tabindex="11"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
						style="width: 80px" /></td>
					<td align="right"><gcom:controleAcessoBotao name="Button"
						value="Inserir" tabindex="12"
						onclick="javascript:validarForm(document.forms[0]);"
						url="inserirSubBaciaAction.do" /></td>
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

