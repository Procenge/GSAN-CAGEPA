<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page isELIgnored="false"%>
<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro"%>
<%@ page import="gcom.util.ConstantesSistema"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<link rel="stylesheet"	href="<bean:message key="caminho.css"/>EstilosCompesa.css"	type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false" formName="AtualizarSubBaciaActionForm" />

<script language="JavaScript"	src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript">

    function validarForm() {
    var form = document.forms[0];
	  if(validateAtualizarSubBaciaActionForm(form)){	     
	   	submeterFormPadrao(form); 
   	  }
   	} 
</script>

</head>

<body leftmargin="5" topmargin="5"	onload="setarFoco('descricao');">

<html:form action="/atualizarSubBaciaAction.do"	name="AtualizarSubBaciaActionForm"
	type="gcom.gui.operacional.AtualizarSubBaciaActionForm" method="post"
	onsubmit="return validateAtualizarSubBaciaActionForm(this);">

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
					<td class="parabg">Atualizar Sub-Bacia</td>
					<td width="11" valign="top"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>

			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para atualizar o sub-bacia, informe os dados abaixo:</td>
				</tr>
				
				<tr>
				  <td  width="30%" class="style3"><strong>Código:<font color="#FF0000">*</font></strong></td>
				  <td  width="70%" colspan="2"><strong><b><span class="style2"> 
				  		<html:text property="codigo" size="2" maxlength="2" onkeypress="return isCampoNumerico(event);" tabindex="1" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/> </span></b></strong></td>
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
				  <td  width="30%"class="style3"><strong>Sistema de Esgoto:<font color="#FF0000">*</font></strong></td>
				  <td  width="70%" colspan="2">
				  			<html:select property="sistema" tabindex="4" disabled="true" style="background-color:#EFEFEF; border:0; color: #000000">
								<html:option value="-1"> &nbsp; </html:option>
								<html:options collection="colecaoSistema" property="id" labelProperty="descricaoComId"/>
							</html:select></td>
				</tr>
				
				<tr>
				  <td  width="30%"class="style3"><strong>Sub-Sistema de Esgoto:<font color="#FF0000">*</font></strong></td>
				  <td  width="70%" colspan="2">
				  			<html:select property="subSistema" tabindex="5" disabled="true" style="background-color:#EFEFEF; border:0; color: #000000">
								<html:option value="-1"> &nbsp; </html:option>
								<html:options collection="colecaoSubSistema" property="id" labelProperty="descricaoComCodigo"/>
							</html:select></td>
				</tr>	
			
				<tr>
				  <td  width="30%"class="style3"><strong>Bacia:<font color="#FF0000">*</font></strong></td>
				  <td  width="70%" colspan="2">
				  			<html:select property="bacia" tabindex="6" disabled="true" style="background-color:#EFEFEF; border:0; color: #000000">
								<html:option value="-1"> &nbsp; </html:option>
								<html:options collection="colecaoBacia" property="id" labelProperty="descricaoComCodigoEId"/>
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
					<td><strong>Indicador de uso:<font color="#FF0000">*</font></strong></td>
						<td><html:radio property="indicadorUso" value="1" tabindex="10"/><strong>Ativo
							<html:radio property="indicadorUso" value="2" tabindex="11"/>Inativo</strong> 
						</td>
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
						<input type="button" name="ButtonCancelar" class="bottonRightCol" value="Voltar"
						onClick="javascript:window.location.href='${sessionScope.caminhoRetornoVoltar}';">
						
						<input type="button" name="ButtonReset" class="bottonRightCol" value="Desfazer" 
						onclick="window.location.href='/gsan/exibirAtualizarSubBaciaAction.do?desfazer=S&reloadPage=1&idSubBacia=${idSubBacia}';">
						
						<input type="button"name="ButtonCancelar" class="bottonRightCol" value="Cancelar" 
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
