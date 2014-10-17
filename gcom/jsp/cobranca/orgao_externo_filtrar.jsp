<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>
<%@ page import="gcom.util.Pagina, gcom.util.ConstantesSistema"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="PesquisarActionForm" dynamicJavascript="false" />
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="Javascript">
<!--

     var bCancel = false;

    function validatePesquisarActionForm(form) {
    	return true;
    }

    function caracteresespeciais  () {
    	this.ab = new Array("codigo", "Código  deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    	this.ac = new Array("descricao", "Descricao possui caracteres especiais.", new Function ("varName", " return this[varName];"));
    }

    function IntegerValidations  () {
     	this.ac = new Array("codigo", "Código deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    }

	function validarForm(){	
		var form = document.forms[0]; 
    	form.submit();
	}
-->
</script>

</head>


<body leftmargin="5" topmargin="5" onload="javascript:setarFoco('${requestScope.nomeCampo}');">
<html:form action="/filtrarOrgaoExternoAction.do" method="post">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="4" cellpadding="0">
		<tr>
			<td width="149" valign="top" class="leftcoltext">

			<div align="center">
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>

			<%@ include file="/jsp/util/informacoes_usuario.jsp"%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>

			<%@ include file="/jsp/util/mensagens.jsp"%>

			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>


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
					<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Filtrar Org&atilde;o Externo</td>
					<td width="11" valign="top"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>

			<p>&nbsp;</p>
			<table width="100%" cellspacing="0">
				<tr>
					<td>
						Para manter o(s) org&atilde;o(s) externo(s), informe os dados abaixo:
					</td>
					<td width="84"><input name="atualizar" type="checkbox" checked="checked" value="1"> <strong>Atualizar</strong></td>
					<td align="right"></td>
    			</tr>
   			</table>
   			
   			<table width="100%" border="0">
				
				<tr>
					<td><strong>C&oacute;digo:</strong></td>
					<td><html:text property="codigo" size="5" tabindex="2" maxlength="4" onkeyup="javascript:verificaNumeroInteiro(this);"/></td>
				</tr>
				<tr>
					<td><strong> Descri&ccedil;&atilde;o:</strong></td>
					<td><html:text property="descricao" size="50" tabindex="3" maxlength="30" /></td>
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
										onclick="window.location.href='/gsan/exibirFiltrarOrgaoExternoAction.do?menu=sim';">
							</font>
						</strong>
					</td>
					<td align="right"></td>
					<td align="right">
						<gcom:controleAcessoBotao name="Button" value="Filtrar" onclick="javascript:validarForm();" url="filtrarOrgaoExternoAction.do"/>
					</td>
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
