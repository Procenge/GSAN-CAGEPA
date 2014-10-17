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
	formName="InserirBaciaActionForm" />

<script>
	function validarForm(form) {
		if(validateInserirBaciaActionForm(form)) {
			form.action = "/gsan/inserirBaciaAction.do";
			submeterFormPadrao(form);
		}
	}

	function carregarSubsistemas() {
		form = document.forms[0];
		document.getElementById("limparCampos").value = "1";
		form.action = "/gsan/exibirInserirBaciaAction.do?objetoConsulta=1";
		form.submit();
	}

	function remover() {
		var form = document.forms[0];

		document.getElementById('limparCampos').value='1';
		form.removerEndereco.value = "1";
		form.submit();
	}
</script>

</head>

<body leftmargin="5" topmargin="5"
	onload="document.forms[0].removerEndereco.value=''; setarFoco('${requestScope.nomeCampo}');">

<html:form action="/exibirInserirBaciaAction" name="InserirBaciaActionForm"
	type="gcom.gui.operacional.InserirBaciaActionForm">

	<INPUT TYPE="hidden" name="removerEndereco">
	<INPUT TYPE="hidden" name="limparCampos" id="limparCampos">

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
					<td class="parabg">Inserir Bacia</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>

			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td>Para adicionar uma bacia, informe os dados abaixo:</td>
					<td align="right"></td>
				</tr>
			</table>

			<table width="100%" border="0">
				<tr>
					<td><strong>Código:<font color="#FF0000">*</font></strong></td>
					<td><html:text property="codigo" size="4" maxlength="2"
						onkeypress="return isCampoNumerico(event);" tabindex="1" /></td>
				</tr>
				<tr>
					<td><strong>Decrição:<font color="#FF0000">*</font></strong></td>
					<td><html:text property="descricao" size="40" maxlength="30"
						tabindex="2" /></td>
				</tr>
				<tr>
					<td><strong>Decrição Abreviada:</strong></td>
					<td><html:text property="descricaoAbreviada" size="10"
						maxlength="6" tabindex="3" /></td>
				</tr>
				<tr>
					<td><strong>Sistema de Esgoto:<font color="#FF0000">*</font></strong></td>
					<td><html:select property="idSistemaEsgoto"
						style="width: 200px;" tabindex="4"
						onchange="carregarSubsistemas();">
						<html:option
							value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present name="colecaoSistemaEsgoto">
							<html:options collection="colecaoSistemaEsgoto"
								labelProperty="descricaoComId" property="id" />
						</logic:present>
					</html:select></td>
				</tr>
				<tr>
					<td><strong>Subsistema de Esgoto:<font
						color="#FF0000">*</font></strong></td>
					<td><html:select property="idSubsistemaEsgoto"
						style="width: 200px;" tabindex="5">
						<html:option
							value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present name="colecaoSubsistemaEsgoto">
							<html:options collection="colecaoSubsistemaEsgoto"
								labelProperty="descricaoComCodigo" property="id" />
						</logic:present>
					</html:select></td>
				</tr>

				<tr>
					<td height="24" colspan="2" class="style1">
						<hr>
					</td>
				</tr>
				<tr>
					<td colspan="2">
					<table width="100%" border="0" bgcolor="#90c7fc">
						<tr bgcolor="#90c7fc" height="18">
							<td align="center"><strong>Capacidade</strong></td>
						</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td><strong>Unidade (m3/h):<font color="#FF0000">*</font></strong></td>
					<td><html:text property="numeroUnidade" size="16" maxlength="16"
						onkeyup="javascript:formataValorDecimal(this, 4, event);" onblur="javascript:formataValorDecimal(this, 4, null);" tabindex="6" /></td>
				</tr>
				<tr>
					<td><strong>Adução (m3/h):<font color="#FF0000">*</font></strong></td>
					<td><html:text property="numeroAducao" size="16" maxlength="16"
						onkeyup="javascript:formataValorDecimal(this, 4, event);" onblur="javascript:formataValorDecimal(this, 4, null);" tabindex="7" /></td>
				</tr>
				<tr>
					<td><strong>Demanda de energia (Kw):<font color="#FF0000">*</font></strong></td>
					<td><html:text property="numeroDemandaEnergia" size="16" maxlength="16"
						onkeyup="javascript:formataValorDecimal(this, 4, event);" onblur="javascript:formataValorDecimal(this, 4, null);" tabindex="8" /></td>
				</tr>
				<tr>
					<td height="24" colspan="2" class="style1">
						<hr>
					</td>
				</tr>

				<tr>
					<td><strong>Endere&ccedil;o:<font color="#FF0000"></font></strong></td>
					<td align="right"><logic:present name="colecaoEnderecos">
						<logic:empty name="colecaoEnderecos">
							<input type="button" class="bottonRightCol" value="Adicionar"
								tabindex="9" id="botaoEndereco"
								onclick="javascript:abrirPopup('exibirInserirEnderecoAction.do?tipoPesquisaEndereco=bacia&operacao=1', 570, 700);">
						</logic:empty>
						<logic:notEmpty name="colecaoEnderecos">
							<input type="button" class="bottonRightCol" value="Adicionar"
								tabindex="9" id="botaoEndereco"
								onclick="javascript:abrirPopup('exibirInserirEnderecoAction.do?tipoPesquisaEndereco=bacia&operacao=1', 570, 700);"
								disabled>
						</logic:notEmpty>
					</logic:present> <logic:notPresent name="colecaoEnderecos">
						<input type="button" class="bottonRightCol" value="Adicionar"
							tabindex="9" id="botaoEndereco"
							onclick="javascript:abrirPopup('exibirInserirEnderecoAction.do?tipoPesquisaEndereco=bacia&operacao=1', 570, 700);">
					</logic:notPresent></td>
				</tr>
				<tr>
					<td colspan="2" height="70" valign="top">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td>
							<table width="100%" border="0" bgcolor="#90c7fc">
								<tr bgcolor="#90c7fc" height="18">
									<td width="10%" align="center"><strong>Remover</strong></td>
									<td align="center"><strong>Endere&ccedil;o</strong></td>
								</tr>
							</table>
							</td>
						</tr>
						<logic:present name="colecaoEnderecos">
							<tr>
								<td>
								<table width="100%" align="center" bgcolor="#99CCFF">
									<!--corpo da segunda tabela-->
									<%String cor = "#cbe5fe";%>
									<logic:iterate name="colecaoEnderecos" id="endereco">
										<%if (cor.equalsIgnoreCase("#cbe5fe")) {
										cor = "#FFFFFF";%>
										<tr bgcolor="#FFFFFF" height="18">
										<%} else {
										cor = "#cbe5fe";%>
										<tr bgcolor="#cbe5fe" height="18">
										<%}%>
											<td width="10%" align="center"><img
												src="<bean:message key='caminho.imagens'/>Error.gif"
												width="14" height="14" style="cursor:hand;" alt="Remover"
												onclick="javascript:if(confirm('Confirma remoção?')){remover();}"></td>

											<td><a
												href="javascript:abrirPopup('exibirInserirEnderecoAction.do?exibirEndereco=OK&tipoPesquisaEndereco=bacia&operacao=1', 570, 700)"><bean:write
												name="endereco" property="enderecoFormatado" /></a></td>
										</tr>
									</logic:iterate>
								</table>
								</td>
							</tr>
						</logic:present>
					</table>
					</td>
				</tr>

				<tr>
					<td><strong>Altitude:</strong></td>
					<td><html:text property="numeroCota" size="9" maxlength="9"
						onkeypress="return isCampoNumerico(event);" tabindex="10" /></td>
				</tr>
				<tr>
					<td><strong>Latitude:</strong></td>
					<td><html:text property="numeroLatitude" size="9" maxlength="9"
						onkeypress="return isCampoNumerico(event);" tabindex="11" /></td>
				</tr>
				<tr>
					<td><strong>Longitude:</strong></td>
					<td><html:text property="numeroLongitude" size="9" maxlength="9"
						onkeypress="return isCampoNumerico(event);" tabindex="12" /></td>
				</tr>

				<tr>
					<td></td>
					<td><font color="#FF0000">*</font>&nbsp;Campos obrigatórios</td>
				</tr>
				<tr>
					<td><input type="button" name="Button"
						class="bottonRightCol" value="Desfazer" tabindex="13"
						onClick="javascript:window.location.href='/gsan/exibirInserirBaciaAction.do?desfazer=S'"
						style="width: 80px" />&nbsp;
						<input type="button" name="Button"
						class="bottonRightCol" value="Cancelar" tabindex="14"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
						style="width: 80px" /></td>
					<td align="right"><gcom:controleAcessoBotao name="Button"
						value="Inserir" tabindex="15"
						onclick="javascript:validarForm(document.forms[0]);"
						url="inserirBaciaAction.do" /></td>
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

