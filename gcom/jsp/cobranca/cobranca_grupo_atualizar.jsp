<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

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

function validarForm(form){

	if(validateCobrancaGrupoActionForm(form)){
		form.submit();
	}
}
	
 </script>
</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');">
<div id="formDiv">
<html:form action="/atualizarCobrancaGrupoAction.do" method="post"
	name="CobrancaGrupoActionForm"
	enctype="multipart/form-data"
	type="gcom.gui.cobranca.CobrancaGrupoaActionForm"
	onsubmit="return validateCobrancaGrupoActionForm(this);">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<input type="hidden" name="tipoPesquisa" />

	

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
			<td width="655" valign="top" class="centercoltext"><!--Início Tabela Reference a Páginação da Tela de Processo-->
			<table>
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="<bean:message key='caminho.imagens'/>parahead_left.gif" /></td>
					<td class="parabg">Atualizar Grupo de Cobrança</td>
					<td width="11" valign="top"><img border="0" src="<bean:message key='caminho.imagens'/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			 
			 <table width="100%" border="0">
			 	<tr> 
                	<td colspan="2">Para atualizar o Grupo de Cobrança, informe os dados abaixo:</td>
              	</tr>
              	
              	<tr>
					<td><strong>Descrição: </strong></td>
					<td align="right">
					<div align="left"><strong><html:text
						property="descricao" maxlength="60" size="60" tabindex="1"/></strong></div>
					</td>
              	              	
              	<tr>
					<td><strong>Descrição Abreviada: </strong></td>
					<td align="right">
					<div align="left"><strong><html:text
						property="descAbreviada" maxlength="3" size="8" tabindex="2"/></strong></div>
					</td>
				</tr>
				<tr>
                  <td><strong>Mês/Ano Referência:</strong></td>
                  <td><html:text maxlength="7" property="anoMesReferencia" size="7" onkeyup="mascaraAnoMes(this, event);" onblur="verificaAnoMes(this)" tabindex="3"/>
                    &nbsp; mm/aaaa</td>
                </tr>				
				
				<tr>
                	<td height="28"><strong>Indicador de Uso: <font color="#FF0000">*</font></strong></td>
                	<td align="right" colspan="2">
                	  <div align="left">
                		<html:radio property="indicadorUso" value="1" disabled="false" /><strong>Ativo</strong>
						<html:radio property="indicadorUso" value="2" disabled="false" /><strong>Inativo</strong>
					  </div>
					</td>
              	</tr>
             	
              	<tr> 
                	<td><strong> <font color="#FF0000"></font></strong></td>
                	<td align="right"> <div align="left"><strong><font color="#FF0000">*</font></strong>Campos obrigat&oacute;rios</div></td>
              	</tr>
              	
              	<table width="100%" border="0">
	              <tr>
					<td>
						<logic:present name="manter" scope="session">
							<input type="button" name="ButtonReset" class="bottonRightCol" value="Voltar"
								onClick="javascript:window.location.href='/gsan/exibirManterCobrancaGrupoAction.do'">
						</logic:present>

						<logic:notPresent name="manter" scope="session">
							<input type="button" name="ButtonReset"  class="bottonRightCol" value="Voltar"
								onClick="javascript:window.location.href='/gsan/exibirFiltrarCobrancaGrupoAction.do'">
						</logic:notPresent> 
					<input name="Button" type="button" class="bottonRightCol"
						value="Desfazer" align="left"
						onclick="window.location.href='<html:rewrite page="/exibirAtualizarCobrancaGrupoAction.do?idCobrancaGrupo=${CobrancaGrupoActionForm.id}"/>'">
					<input name="Button" type="button" class="bottonRightCol"
							value="Cancelar" align="left"
							onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"></td>
					<td align="right"><input name="Button" type="button"
						class="bottonRightCol" value="Atualizar" align="right"
						onClick="javascript:validarForm(document.forms[0]);"></td>
				</tr>
	              
             	</table>
              	
			 </table>
			
			<p>&nbsp;</p>
		</tr>
		<tr>
			<td colspan="3"></td>
		</tr>
	</table>
	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</div>
</body>

<%@ include file="/jsp/util/telaespera.jsp"%>

</html:html>