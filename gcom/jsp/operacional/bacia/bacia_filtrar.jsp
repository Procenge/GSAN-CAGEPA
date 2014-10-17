<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<%@ page import="gcom.util.ConstantesSistema"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="FiltrarBaciaActionForm"/>


<SCRIPT LANGUAGE="JavaScript">
<!--
function validarForm(formulario){
	if (validateFiltrarBaciaActionForm(formulario)){
		submeterFormPadrao(formulario);
	}
}

function carregarSubsistemas() {
	form = document.forms[0];
	document.getElementById("limparCampos").value = "1";
	form.action = "/gsan/exibirFiltrarBaciaAction.do?objetoConsulta=1";
	form.submit();
}

function verificarChecado(valor){
	form = document.forms[0];
	if(valor == "1"){
	 	form.indicadorAtualizar.checked = true;
	 }else{
	 	form.indicadorAtualizar.checked = false;
	}
}

//-->
</SCRIPT>
</head>

<body leftmargin="5" topmargin="5" onload="javascript:verificarChecado('${sessionScope.indicadorAtualizar}');setarFoco('${requestScope.nomeCampo}');">

<html:form action="/filtrarBaciaAction" method="post"
	name="FiltrarBaciaActionForm"
	type="gcom.gui.operacional.bacia.FiltrarBaciaActionForm">

<INPUT TYPE="hidden" name="limparCampos" id="limparCampos">

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

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

      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
          <td class="parabg">Filtrar Bacia</td>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
        </tr>
      </table>
      <p>&nbsp;</p>

      <table width="100%" border="0">
      	<tr>
      		<td>Para atualizar uma bacia, informe os dados abaixo:</td>
	        <td width="84">
				<input type="checkbox" name="indicadorAtualizar" value="1" /><strong>Atualizar</strong>
			</td>
			<td align="right"></td>
    	</tr>
   	</table>
   	<table width="100%" border="0">
   	<tr>
		<td><strong>Código:</strong></td>
		<td><html:text property="codigo" size="4" maxlength="2" onkeypress="return isCampoNumerico(event);" tabindex="1" /></td>
	</tr>
	<tr>
		<td><strong>Decrição:</strong></td>
		<td><html:text property="descricao" size="40" maxlength="30" tabindex="2" /></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td valign="top">
		  <html:radio property="tipoPesquisaDescricao" value="<%=ConstantesSistema.TIPO_PESQUISA_INICIAL.toString()%>" /> Iniciando pelo texto
		  <html:radio property="tipoPesquisaDescricao" tabindex="5" value="<%=ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString()%>" /> Contendo o texto
		</td>
	</tr>
	<tr>
		<td><strong>Decrição Abreviada:</strong></td>
		<td><html:text property="descricaoAbreviada" size="10" maxlength="6" tabindex="3" /></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td valign="top">
		  <html:radio property="tipoPesquisaDescricaoAbreviada" value="<%=ConstantesSistema.TIPO_PESQUISA_INICIAL.toString()%>" /> Iniciando pelo texto
		  <html:radio property="tipoPesquisaDescricaoAbreviada" tabindex="5" value="<%=ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString()%>" /> Contendo o texto
		</td>
	</tr>
	<tr>
		<td><strong>Sistema de Esgoto:</strong></td>
		<td>
			<html:select property="idSistemaEsgoto" style="width: 200px;" tabindex="4" onchange="carregarSubsistemas();">
				<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
				<logic:present name="colecaoSistemaEsgoto">
					<html:options collection="colecaoSistemaEsgoto" labelProperty="descricaoComId" property="id" />
				</logic:present>
			</html:select>
		</td>
	</tr>
	<tr>
		<td>
			<strong>Subsistema de Esgoto:</strong>
		</td>
		<td>
			<html:select property="idSubsistemaEsgoto" style="width: 200px;" tabindex="5">
				<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
				<logic:present name="colecaoSubsistemaEsgoto">
					<html:options collection="colecaoSubsistemaEsgoto" labelProperty="descricaoComCodigo" property="id" />
				</logic:present>
			</html:select>
		</td>
	</tr>
     <tr>
		<td height="30"><strong>Indicador de Uso:</strong></td>
		<td>
			<html:radio property="indicadorUso" value="<%="" + ConstantesSistema.INDICADOR_USO_ATIVO.toString()%>"/><strong>Ativo&nbsp;
			<html:radio property="indicadorUso" value="<%="" + ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>"/>Inativo</strong>&nbsp;
			<html:radio property="indicadorUso"	value="<%="" + ConstantesSistema.TODOS.toString()%>" /><strong>Todos</strong>
		</td>
     </tr>
      <tr>
		<td>
			<strong>
				<font color="#ff0000"> <input name="Submit22" class="bottonRightCol" value="Limpar" type="button" onclick="window.location.href='/gsan/exibirFiltrarBaciaAction.do?menu=sim';"></font>
			</strong>
		</td>
		<td align="right">
		<gcom:controleAcessoBotao name="Button" value="Filtrar" onclick="javascript:validarForm(document.forms[0]);" url="filtrarBaciaAction.do"/>
		<!-- 
		<INPUT type="button" onclick="validarForm(document.forms[0]);" name="botaoFiltrar" class="bottonRightCol" value="Filtrar" tabindex="5" style="width: 70px;"> --></td>
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
