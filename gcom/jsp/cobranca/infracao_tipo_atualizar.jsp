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


function limpar(){
	var form = document.forms[0];
	form.descricao.value = "";
	form.descricaoAbreviada.value = ""; 
}
</script>

<body leftmargin="5" topmargin="5">
	<html:form action="/atualizarInfracaoTipoAction.do"
		name="InfracaoTipoActionForm"
		type="gcom.gui.cobranca.InfracaoTipoActionForm" method="post">

		
		
					<table width="100%" border="0">
						<tr>
							<td height="10" colspan="3">Para atualizar um tipo de infração, informe os dados abaixo:</td>
						</tr>
						<tr>
							<td><strong>Código:</strong></td>
							<td><html:text property="id" readonly="true" size="3" /></td>
						</tr>
						<tr>
							<td><strong>Descrição:<font color="#FF0000">*</font></strong></td>
							<td colspan="2">
								<span class="style2"> 
									
									<html:text property="descricao" size="42" maxlength="30" />  
								</span>
							</td>
						</tr>
						
						<tr>
							<td><strong>Descrição Abreviada:<font color="#FF0000">*</font></strong></td>
							<td colspan="2">
								<span class="style2"> 
									
									<html:text property="descricaoAbreviada" size="12" maxlength="10" />  
								</span>
							</td>
						</tr>
				<tr>
					<td><strong>Indicador de Uso:</strong></td>
					<td align="right">
						<div align="left">
							<html:radio property="indicadorUso" tabindex="3" value="<%=ConstantesSistema.INDICADOR_USO_ATIVO.toString()%>" /> <strong>Ativo</strong>
							<html:radio property="indicadorUso" tabindex="4" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>" /> <strong>Inativo</strong></div>
					</td>
				</tr>
						<tr>
							<td></td>
						</tr>				
						<tr>
							<td></td>
							<td align="right">
								<div align="left">
									<strong>
										<font color="#FF0000">*</font>
									</strong>
									Campos obrigatórios
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<font color="#FF0000"> 
									<input type="button" 
										   name="ButtonReset"
										   class="bottonRightCol" 
  										   value="Limpar"
										   onClick="limpar();">  
									<input type="button"
										   name="ButtonCancelar" 
										   class="bottonRightCol" 
										   value="Cancelar"
										   onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
								</font>
							</td>
							<td align="right">
							  <gcom:controleAcessoBotao name="Button" value="Atualizar" onclick="javascript:validaForm(document.forms[0]);" url="atualizarInfracaoTipoAction.do"/>
							</td>
						</tr>
					</table>
		
	</html:form>
</body>
</html>