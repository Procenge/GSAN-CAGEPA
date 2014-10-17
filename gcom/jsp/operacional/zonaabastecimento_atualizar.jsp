<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>
<%@ page isELIgnored="false"%>
<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro"%>
<%@ page import="gcom.util.ConstantesSistema"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<link rel="stylesheet"	href="<bean:message key="caminho.css"/>EstilosCompesa.css"	type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false" formName="AtualizarZonaAbastecimentoActionForm" />

<script language="JavaScript"	src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript">
	function validarForm() {
		var form = document.forms[0];
		if (validateAtualizarZonaAbastecimentoActionForm(form) && validaFaixaPressao(form)) {
			submeterFormPadrao(form);
		}
	}

	function validaFaixaPressao(form) {		
		var inferior = parseFloat(form.faixaPressaoInferior.value);
		var superior = parseFloat(form.faixaPressaoSuperior.value);

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

<body leftmargin="5" topmargin="5"	onload="setarFoco('descricao');">

<html:form action="/atualizarZonaAbastecimentoAction.do" name="AtualizarZonaAbastecimentoActionForm"
	type="gcom.gui.operacional.AtualizarZonaAbastecimentoActionForm" method="post"
	onsubmit="return validateAtualizarZonaAbastecimentoActionForm(this);">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="115" valign="top" class="leftcoltext">
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


			<td valign="top" class="centercoltext">
			<table>
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Atualizar Zona de Abastecimento</td>
					<td width="11" valign="top"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>

			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para atualizar a Zona de Abastecimento, informe os dados abaixo:</td>
				</tr>
				
				<tr>
				  <td  width="30%" class="style3"><strong>Código:<font color="#FF0000">*</font></strong></td>
				  <td  width="70%" colspan="2"><strong><b><span class="style2"> 
			  		<html:text property="codigo" size="2" maxlength="2" tabindex="1" onkeypress="return isCampoNumerico(event);" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/> </span></b></strong>
			  	  </td>
				</tr>
				
				<tr>
				  <td  width="30%" class="style3"><strong>Descrição:<font color="#FF0000">*</font></strong></td>
				  <td  width="70%" colspan="2"><strong><b><span class="style2"> 
				  		<html:text  property="descricao" size="30" maxlength="30" tabindex="2"/> </span></b></strong></td>
				</tr>	
			
				<tr>
				  <td  width="30%" class="style3"><strong>Descrição Abreviada:</strong></td>
				  <td  width="70%" colspan="2"><strong><b><span class="style2"> 
				  		<html:text  property="descricaoAbreviada" size="6" maxlength="6" tabindex="3"/> </span></b></strong></td>
				</tr>
				
				<tr>
				  <td  width="30%"class="style3"><strong>Sistema de Abastecimento:<font color="#FF0000">*</font></strong></td>
				  <td  width="70%" colspan="2">
				  			<html:select property="sistemaAbastecimento" tabindex="4" disabled="true" style="background-color:#EFEFEF; border:0; color: #000000">
								<html:option value="-1"> &nbsp; </html:option>
								<html:options collection="colecaoSistemaAbastecimento" property="id" labelProperty="descricaoComCodigo"/>
							</html:select></td>
				</tr>
				
				<tr>
					<td><strong>Distrito Operacional:<font
						color="#FF0000">*</font></strong></td>
					<td>
						<html:select property="idDistritoOperacional" tabindex="5" disabled="true" style="width: 200px; background-color:#EFEFEF; border:0; color: #000000">
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
						<html:select property="idLocalidade" disabled="true" tabindex="6" style="width: 200px; background-color:#EFEFEF; border:0; color: #000000">
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
				</tr>
				<tr>
				</tr>
				<tr>
				</tr>
				
				<tr>
					<td width="500" colspan="2">
					<div align="center" ><font color="#FF0000">*</font> Campos obrigatórios</div>
					</td>
				</tr>
			
			</table>
			<table width="100%">
				<tr>
					<td width="40%" align="left">
						<input type="button" name="ButtonCancelar" class="bottonRightCol" value="Voltar" tabindex="9"
						onClick="javascript:history.back();">
						
						<input type="button" name="ButtonReset" class="bottonRightCol" value="Desfazer" tabindex="10"
						onclick="window.location.href='/gsan/exibirAtualizarZonaAbastecimentoAction.do?desfazer=S&reloadPage=1&idZonaAbastecimento=${idZonaAbastecimento}';">
						
						<input type="button"name="ButtonCancelar" class="bottonRightCol" value="Cancelar" tabindex="11"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
					</td>
					<td align="right">
						<input type="button" name="Button" class="bottonRightCol"
						value="Atualizar" onclick="javascript:validarForm();" tabindex="12" />
					</td>
				</tr>
			</table>
		</tr>


	</table>
	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</html:html>
