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
	form.id.value = "";
	form.descricao.value="";
}

</script>

<body leftmargin="5" topmargin="5" onload="limpar();">
	<html:form action="/filtrarInfracaoLigacaoSituacaoAction.do"
	   name="InfracaoLigacaoSituacaoActionForm"
	   type="gcom.gui.cobranca.InfracaoLigacaoSitucaoActionForm"
	   method="post" >
	  
	 	<table width="100%" border="0">
	  		<tr>
				<td>Para filtrar uma situação de infração de ligação, informe os dados abaixo:</td>
				<td align="right">
					<input type="checkbox"name="indicadorAtualizar" value="1" />
					<strong>Atualizar</strong></td>
				<td align="right">
			</tr>
	  	</table>
	
		<table width="100%" border="0">
				
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
						<div align="left"><html:radio property="indicadorUso" tabindex="4"
							value="<%=ConstantesSistema.INDICADOR_USO_ATIVO.toString()%>" />
						<strong>Ativo</strong> <html:radio property="indicadorUso" tabindex="5"
							value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>" />
						<strong>Inativo</strong> <html:radio property="indicadorUso"
							tabindex="5" value="" /> <strong>Todos</strong></div>					
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>
						<strong> 
							<font color="#ff0000"> 
								<input	name="Submit22" 
										class="bottonRightCol"
										value="Limpar" 
										type="button"
										onclick="window.location.href='/gsan/exibirFiltrarInfracaoLigacaoSituacaoAction.do?menu=sim';">
								<input  type="button"
										name="ButtonCancelar" 
										class="bottonRightCol" 
										value="Cancelar"
										onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
							</font>
						</strong>
					</td>
					<td align="right"></td>
					<td align="right">
						<gcom:controleAcessoBotao name="Button" value="Filtrar" onclick="javascript:validaForm();" url="filtrarInfracaoLigacaoSituacaoAction.do"/>
					</td>
				</tr>
			</table>
	</html:form>
</body>
</html>