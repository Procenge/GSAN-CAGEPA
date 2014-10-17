<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>
<%@ page import="gcom.util.ConstantesSistema"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
	
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>	
<script language="JavaScript">
function validaForm(){	
	var form = document.forms[0]; 
	form.submit();
}

function pesquisaSubcategoria(){
	redirecionarSubmit('exibirAtualizarInfracaoPerfilAction.do?pesquisaSubCat=1');
}
</script>

<body leftmargin="5" topmargin="5">
	<html:form action="/atualizarInfracaoPerfilAction.do"
	   name="InfracaoPerfilActionForm"
	   type="gcom.gui.cobranca.InfracaoPerfilActionForm"
	   method="post" >
	  
		<table width="100%" border="0">
	   		<tr>
				<td height="10" colspan="3"> Para atualizar um Perfil de Infração, informe os dados abaixo:</td>
			</tr>
			<tr>
				<td><strong>Codigo:<font color="#FF0000">*</font></strong></td>
				<td colspan="2">
					<span class="style2">
						<html:text property="id" readonly="true" size="4" maxlength="3"/>
					</span></td>
			</tr>
			<tr>
				<td><strong>Tipo Infração:<font color="#FF0000">*</font></strong></td>
				<td colspan="2">
					<span class="style2">
						<html:select property="idInfracaoTipo" >
							<html:option value="-1">&nbsp;</html:option>
							<html:options property="id" labelProperty="descricao"  collection="colecaoInfracaoTipo"/>
						</html:select>
					</span></td>
			</tr>
			<tr>
				<td><strong>Perfil do Imóvel:<font color="#FF0000">*</font></strong></td>
				<td colspan="2">
					<span class="style2">
						<html:select property="idImovelPerfil" >
							<html:option value="-1">&nbsp;</html:option>
							<html:options property="id" labelProperty="descricao"  collection="colecaoImovelPerfil"/>
						</html:select>
					</span></td>
			</tr>

			<tr>
				<td><strong>Categoria:<font color="#FF0000">*</font></strong></td>
				<td colspan="2">
					<span class="style2">
						<html:select property="idCategoria"  onchange="javascript:pesquisaSubcategoria();">
							<html:option value="-1">&nbsp;</html:option>
							<html:options property="id" labelProperty="descricao"  collection="colecaoCategoria"/>
						</html:select>
					</span></td>
			</tr>
			 
			<tr>
				<td><strong>Subcategoria:<font color="#FF0000">*</font></strong></td>
				<td colspan="2">
					<span class="style2">
						<logic:present name="colecaoSubcategoria" scope="session">
							<html:select property="idSubcategoria">
								<html:option value="-1">&nbsp;</html:option>
								<html:options property="id" labelProperty="descricao"  collection="colecaoSubcategoria"/>
							</html:select>										
						</logic:present>
						<logic:notPresent name="colecaoSubcategoria" scope="session">
							<html:select property="idSubcategoria" >
								<html:option value="-1">&nbsp;</html:option>
							</html:select>
						</logic:notPresent>
					</span></td>
			</tr>
			

			<tr>
				<td></td>
			</tr>				
			
			<tr>
				<td></td>
				<td align="right">
					<div align="left">
						<strong><font color="#FF0000">*</font></strong>Campos obrigatórios</div></td></tr>
			<tr>
				<td colspan="2">
					<font color="#FF0000"> 
						<logic:present name="atualizar" scope="request">
							<input type="button" 
								   name="ButtonReset" 
								   class="bottonRightCol"
								   value="Voltar"
								   onClick="javascript:window.location.href='/gsan/exibirFiltrarClienteTipo.do?menu=sim'">
						</logic:present>
									
						<logic:notPresent name="atualizar" scope="request">
							
							<input type="button" 
								   name="ButtonReset" 
								   class="bottonRightCol"
								   value="Voltar" 
								   onClick="javascript:history.back();">
						
						</logic:notPresent>
						
						<input type="button" 
							   name="ButtonReset"
							   class="bottonRightCol" 
							   value="Desfazer"
							   onClick="(document.forms[0]).reset()"> 
						
						<input type="button"
							   name="ButtonCancelar" 
							   class="bottonRightCol" 
							   value="Cancelar"
							   onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
					</font>
				</td>
				<td align="right">
					<gcom:controleAcessoBotao name="Button" value="Atualizar" onclick="javascript:validaForm(document.forms[0]);" url="atualizarInfracaoPerfilAction.do"/>
				</td>
			</tr>
	 	</table>
	</html:form>
</body>
</html>