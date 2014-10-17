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

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="FiltrarLocalidadeDadoOperacionalActionForm"/>


<SCRIPT LANGUAGE="JavaScript">
<!--
function limpar(){
	var form = document.forms[0];
	form.idLocalidade.value = "";
	form.descricaoLocalidade.value = "";
	//Coloca o foco no objeto selecionado
	form.idLocalidade.focus();
}

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

	var form = document.forms[0];

	if (tipoConsulta == 'localidade') {
      form.idLocalidade.value = codigoRegistro;
      form.descricaoLocalidade.value = descricaoRegistro;
	  form.descricaoLocalidade.style.color = "#000000";
	}

}

function validarForm(formulario){
	if (validateFiltrarLocalidadeDadoOperacionalActionForm(formulario)){
		submeterFormPadrao(formulario);
	}
}


function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg){

	if (objeto == null || codigoObjeto == null){
		abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
	}
	else{
		if (codigoObjeto.length < 1){
			alert(msg);
		}
		else{
			abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
		}
	}
}

function verificarChecado(valor){
		form = document.forms[0];
		if(valor == "1"){
		 	form.indicadorAtualizar.checked = true;
		 }else{
		 	form.indicadorAtualizar.checked = false;
		}
}

function replicarReferencia(){
	var form = document.forms[0];
	form.mesAnoReferenciaFinal.value = form.mesAnoReferenciaInicial.value;
}

//-->
</SCRIPT>
</head>

<body leftmargin="5" topmargin="5" onload="javascript:verificarChecado('${sessionScope.indicadorAtualizar}');setarFoco('${requestScope.nomeCampo}');">

<html:form action="/filtrarLocalidadeDadoOperacionalAction" method="post"
	name="FiltrarLocalidadeDadoOperacionalActionForm"
	type="gcom.gui.operacional.localidadedadooperacional.FiltrarLocalidadeDadoOperacionalActionForm">


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
          <td class="parabg">Filtrar Dados Operacionais da Localidade</td>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
        </tr>
      </table>
      <p>&nbsp;</p>

      <table width="100%" border="0">
      	<tr>
      		<td>Para atualizar um dados operacionais da localidade, informe os dados 
            	abaixo:</td>
	        <td width="84">
				<input type="checkbox" name="indicadorAtualizar" value="1" /><strong>Atualizar</strong>
			</td>
			<td align="right"></td>
    	</tr>
   	</table>
   	<table width="100%" border="0">
      <tr>
      	<td HEIGHT="30"><strong>Localidade:</strong></td>
        <td>
        	<html:text property="idLocalidade" size="5" maxlength="3" onkeypress="validaEnterComMensagem(event, 'exibirFiltrarLocalidadeDadoOperacionalAction.do?objetoConsulta=1', 'idLocalidade', 'Localidade');"/>
			<a	href="javascript:abrirPopup('exibirPesquisarLocalidadeAction.do?indicadorUsoTodos=1', 250, 495);"><img
						src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"
						width="23" height="21" title="Pesquisar"></a> 
			<logic:present name="corLocalidade">

				<logic:equal name="corLocalidade" value="exception">
					<html:text property="descricaoLocalidade" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
				</logic:equal>

				<logic:notEqual name="corLocalidade" value="exception">
					<html:text property="descricaoLocalidade" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
				</logic:notEqual>

			</logic:present>

			<logic:notPresent name="corLocalidade">

				<logic:empty name="FiltrarLocalidadeDadoOperacionalActionForm" property="idLocalidade">
					<html:text property="descricaoLocalidade" value="" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
				</logic:empty>
				<logic:notEmpty name="FiltrarLocalidadeDadoOperacionalActionForm" property="idLocalidade">
					<html:text property="descricaoLocalidade"size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
				</logic:notEmpty>
				

			</logic:notPresent>

			<a href="javascript:limpar();"> 
				<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
			</a>
		</td>
      </tr>
	  <tr height="30"> 
          <td><strong>Per&iacute;odo de Refer&ecirc;ncia:</strong></td>
          <td>
            <html:text property="mesAnoReferenciaInicial"  size="7" maxlength="7" onkeyup="javascript:replicarReferencia();" onkeypress="javascript:mascaraAnoMes(this,event),replicarReferencia();"/>
            <strong>&nbsp;a&nbsp;</strong> 
            <html:text property="mesAnoReferenciaFinal"  size="7" maxlength="7" onkeypress="javascript:mascaraAnoMes(this,event);"/>&nbsp;(mm/aaaa) 
          </td>
      </tr>
	  <tr>
        <td height="30"><strong>Indicador de uso:</strong></td>
        <td>
			<html:radio property="indicadorUso" value="<%="" + ConstantesSistema.INDICADOR_USO_ATIVO.toString()%>"/><strong>Ativo&nbsp;
			<html:radio property="indicadorUso" value="<%="" + ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>"/>Inativo</strong>&nbsp;
			<html:radio property="indicadorUso"	value="<%="" + ConstantesSistema.TODOS.toString()%>" /><strong>Todos</strong>
		</td>
      </tr>
      <tr>
		<td><strong> <font color="#ff0000"> <input name="Submit22"
			class="bottonRightCol" value="Limpar" type="button"
			onclick="window.location.href='/gsan/exibirFiltrarLocalidadeDadoOperacionalAction.do?menu=sim';">
		</font></strong></td>
		<td align="right">
		<gcom:controleAcessoBotao name="Button" value="Filtrar" onclick="javascript:validarForm(document.forms[0]);" url="filtrarLocalidadeDadoOperacionalAction.do"/>
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
