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

function pesquisar(){
	var form = document.forms[0];
	form.submit();
}

</script>

<body leftmargin="5" topmargin="5" onload="resizePageSemLink(500, 400);">
	<html:form action="/pesquisarInfracaoTipoAction.do"
	   name="InfracaoTipoActionForm"
	   type="gcom.gui.cobranca.InfracaoTipoActionForm"
	   method="post" >
	  
		<table width="100%" border="0" class="centercoltext">
	   		<tr><td width="502" valign="top" colspan="3">
	   		<table width="100%" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Pesquisar Infracao Tipo</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p></td>
			</tr>
	   		<tr>
				<td colspan ="2">Para pesquisar um tipo de infração, informe os dados abaixo:</td>
			</tr>
			
			<tr>
				<td><strong>C&oacute;digo:</strong></td>
				<td><html:text property="id" size="5" tabindex="2" maxlength="4" onkeypress="return isCampoNumerico(event);"/></td>
			</tr>
			<tr>
				<td><strong> Descri&ccedil;&atilde;o:</strong></td>
				<td><html:text property="descricao" size="30" tabindex="3" maxlength="30" /></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><html:radio property="tipoPesquisa" value="<%=ConstantesSistema.TIPO_PESQUISA_INICIAL.toString()%>" />
					 Iniciando pelo texto
					<html:radio property="tipoPesquisa" tabindex="5" value="<%=ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString()%>" /> 
					 Contendo o texto
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><strong>Indicador de Uso:</strong></td>
				<td align="right">
					<div align="left">
						<html:radio property="indicadorUso" tabindex="4" value="<%=ConstantesSistema.INDICADOR_USO_ATIVO.toString()%>" />
						<strong>Ativo</strong> 
						<html:radio property="indicadorUso" tabindex="5" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>" />
						<strong>Inativo</strong> 
						<html:radio property="indicadorUso" tabindex="5" value="" /> 
						<strong>Todos</strong></div>					
				</td>
			</tr>
			
			<tr>
				<td></td>
			</tr>				
			
			<tr>
				<td></td>
			</tr>
			<tr>
				<td colspan="2">
					<font color="#FF0000"> 
						<logic:present name="inserir" scope="request">
							<input type="button" 
								   name="ButtonReset" 
								   class="bottonRightCol"
								   value="Voltar"
								   onClick="javascript:window.location.href='/gsan/exibirFiltrarClienteTipo.do?menu=sim'">
						</logic:present>
									
						<logic:notPresent name="inserir" scope="request">
							
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
					<!--<gcom:controleAcessoBotao name="Button" value="Pesquisar" onclick="javascript:validaForm(document.forms[0]);" url="pesquisarInfracaoTipoAction.do"/>-->
					<input type ="button" value="Pesquisar" onclick="pesquisar();"class="bottonRightCol" >
				</td>
			</tr>
	 	</table>
	</html:form>
</body>
</html>