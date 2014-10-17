<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

<%@ page import="gcom.util.ConstantesSistema"%>


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
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false"
	formName="InserirZonaAbastecimentoActionForm" />

<script>
	function validarForm(form) {
		if(validateInserirZonaAbastecimentoActionForm(form) && validaFaixaPressao(form)) {
			submeterFormPadrao(form);
		}
	}

	function validaFaixaPressao(form) {		
		var inferior = form.faixaPressaoInferior.value;
		var superior = form.faixaPressaoSuperior.value;

		//retira zeros desnecess?rios ao in?cio do n?mero
		while (inferior.length > 0 && inferior.charAt(0) == "0")
			inferior = inferior.substring(1);
		
		while (superior.length > 0 && superior.charAt(0) == "0")
			superior = superior.substring(1);	

		form.faixaPressaoInferior.value = inferior;
		form.faixaPressaoSuperior.value = superior;	
		if( inferior > superior ) {
			alert("Faixa de Pressão Inferior não pode ser maior que a Superior.");
			form.faixaPressaoInferior.focus();
			return false;
		} else {
			return true;
		}
	}
</script>

</head>

<body leftmargin="5" topmargin="5"
	onload="setarFoco('${requestScope.nomeCampo}');">

<html:form action="/inserirZonaAbastecimentoAction.do" name="InserirZonaAbastecimentoActionForm"
	type="gcom.gui.operacional.InserirZonaAbastecimentoActionForm">

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
					<td class="parabg">Inserir Zona de Abastecimento</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>

			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td>Para adicionar uma Zona de Abastecimento, informe os dados abaixo:</td>
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
					<td><strong>Sistema de Abastecimento:<font
						color="#FF0000">*</font></strong></td>
					<td>
						<html:select property="idSistemaAbastecimento" style="width: 290px;" tabindex="4" onchange="javascript:pesquisaColecaoReload('exibirInserirZonaAbastecimentoAction.do','idSistemaAbastecimento');">
							<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<logic:present name="colecaoSistemaAbastecimento" scope="session">
								<logic:notEmpty name="colecaoSistemaAbastecimento" scope="session">
									<html:options collection="colecaoSistemaAbastecimento" labelProperty="descricaoComCodigo" property="id" />
								</logic:notEmpty>
							</logic:present>
						</html:select>
					</td>
				</tr>
				<tr>
					<td><strong>Distrito Operacional:<font
						color="#FF0000">*</font></strong></td>
					<td>
						<html:select property="idDistritoOperacional" style="width: 290px;" tabindex="5" onchange="javascript:pesquisaColecaoReload('exibirInserirZonaAbastecimentoAction.do','idDistritoOperacional');">
						<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<logic:present name="colecaoDistritoOperacional" scope="session">
								<logic:notEmpty name="colecaoDistritoOperacional" scope="session">
									<html:options collection="colecaoDistritoOperacional" labelProperty="idComDescricao" property="id" />
								</logic:notEmpty>
							</logic:present>
						</html:select>
					</td>
				</tr>
				<tr>
					<td><strong>Localidade:<font color="#FF0000">*</font></strong></td>
					<td>
						<html:select property="idLocalidade" style="width: 290px;" tabindex="6">
							<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<logic:present name="colecaoLocalidade" scope="session">
								<logic:notEmpty name="colecaoLocalidade" scope="session">
									<html:options collection="colecaoLocalidade" labelProperty="descricaoComId" property="id" />
								</logic:notEmpty>
							</logic:present>
						</html:select>
					</td>
				</tr>
				<tr>
					<td><strong>Faixa de Pressão Inferior:<font color="#FF0000">*</font></strong></td>
					<td><html:text property="faixaPressaoInferior" size="11" maxlength="11"	onkeyup="javascript:formataValorDecimal(this, 4, event);" onblur="javascript:formataValorDecimal(this, 4, null);" tabindex="7"/></td>
				</tr>
				<tr>
					<td><strong>Faixa de Pressão Superior:<font color="#FF0000">*</font></strong></td>
					<td><html:text property="faixaPressaoSuperior" size="11" maxlength="11"	onkeyup="javascript:formataValorDecimal(this, 4, event);" onblur="javascript:formataValorDecimal(this, 4, null);" tabindex="8"/></td>
				</tr>
				<tr>
					<td></td>
					<td><font color="#FF0000">*</font>&nbsp;Campos obrigatórios</td>
				</tr>
				<tr>
					<td colspan="2"><input type="button" name="Button"
						class="bottonRightCol" value="Desfazer" tabindex="9"
						onClick="javascript:window.location.href='/gsan/exibirInserirZonaAbastecimentoAction.do?desfazer=S'"
						style="width: 80px" />&nbsp; <input type="button" name="Button"
						class="bottonRightCol" value="Cancelar" tabindex="10"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
						style="width: 80px" /></td>
					<td align="right"><gcom:controleAcessoBotao name="Button"
						value="Inserir" tabindex="11"
						onclick="javascript:validarForm(document.forms[0]);"
						url="inserirZonaAbastecimentoAction.do" /></td>
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

