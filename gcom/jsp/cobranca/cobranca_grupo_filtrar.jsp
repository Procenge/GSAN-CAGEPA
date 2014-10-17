<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<%@ page import="gcom.util.ConstantesSistema" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<html:javascript staticJavascript="false"  formName="CobrancaGrupoActionForm" />

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script>
function validarForm(){

	var form = document.forms[0];
	
		submeterFormPadrao(form);
	
}
</script>
</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');">
<div id="formDiv">
<html:form action="/filtrarCobrancaGrupoAction"   
	name="CobrancaGrupoActionForm"
  	type="gcom.gui.cobranca.CobrancaGrupoActionForm"
  	method="post"
  	>

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

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
		<td width="655" valign="top" class="centercoltext">
			<!--Início Tabela Reference a Páginação da Tela de Processo-->
            <table>
              <tr>
                <td></td>
              </tr>
            </table>
            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td width="11"><img border="0" src="<bean:message key='caminho.imagens'/>parahead_left.gif"/></td>
                <td class="parabg">Filtrar Grupo de Cobrança </td>
                <td width="11" valign="top"><img border="0" src="<bean:message key='caminho.imagens'/>parahead_right.gif"/></td>
              </tr>
            </table>
			<p>&nbsp;</p>
			
            <table width="100%" border="0">
              <tr>
	           <td width="100%" colspan=2>
		          <table width="100%">
		          	<tr>
		          		<td>Para Filtrar o(s) grupo(s)de Cobrança, informe os dados abaixo: </td>
		          		<td align="right"><input type="checkbox" name="indicadorAtualizar" value="1" checked/><strong>Atualizar</strong>
		          	</td>	
		          </table>
	           </td>
	          </tr>
              <tr>
                <td><strong>Descrição: </strong></td>
                <td>
                  	<html:text property="descricao" maxlength="60" size="60" tabindex="1"/>
                </td>
              </tr>

			  <tr>
				<td width="20%"><strong>Descrição Abreviada:</strong></td>
				<td width="80%"><html:text maxlength="8" property="descAbreviada" size="8" tabindex="2" /></td>
			  </tr>
			  		<tr>
                  <td><strong>Mês/Ano Referência:</strong></td>
                  <td><html:text maxlength="7" property="anoMesReferencia" size="7" onkeyup="mascaraAnoMes(this, event);" onblur="verificaAnoMes(this)" tabindex="3"/>
                    &nbsp; mm/aaaa</td>
                </tr>  
			  
			  <tr>
				<td><strong>Indicador de Uso:<font color="#FF0000">*</font></strong></td>
				<td align="right">
				  <div align="left">
					<html:radio property="indicadorUso" tabindex="9" value="<%=ConstantesSistema.INDICADOR_USO_ATIVO.toString()%>" /><strong>Ativo</strong>
					<html:radio property="indicadorUso"	tabindex="9" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>" /><strong>Inativo</strong>
					
				  </div>
				</td>
			  </tr>
			  <tr> 
                <td><strong> <font color="#FF0000"></font></strong></td>
                <td align="right"> <div align="left"><strong><font color="#FF0000">*</font></strong> 
                    Campos obrigat&oacute;rios</div></td>
              </tr>

			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>
					<input name="Button" type="button" class="bottonRightCol" value="Limpar" align="left"
					onclick="javascript:window.location.href='/gsan/exibirFiltrarCobrancaGrupoAction.do?menu=sim'">
				</td>
				<td align="right"></td>
				<td align="right">
					<gcom:controleAcessoBotao name="Button" value="Filtrar"
											  onclick="javascript:validarForm();" 
											  url="filtrarCobrancaGrupoAction.do" />
				</td>
			</tr>

            </table>
          <p>&nbsp;</p></tr>
		<tr>
		  	<td colspan="3">
			</td>
		</tr>
</table>

<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</div>
</body>

<%@ include file="/jsp/util/telaespera.jsp"%>

<script>

</script>



</html:html>