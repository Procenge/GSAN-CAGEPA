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

 <html:javascript staticJavascript="false"  formName="AtualizarNormaProcedimentalActionForm" />

 <script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
 <script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>

 <script>

function validarForm(form){

	if(validateAtualizarNormaProcedimentalActionForm(form)){
		form.submit();
	}
}

function abrirVisualizarArquivo(){
	var form = document.forms[0];

	abrirPopup('exibirConteudoNormaProcedimentalAction.do?id=' + form.codigoNormaProcedimental.value, 600, 800);
}

	
 </script>
</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');">
<div id="formDiv">
<html:form action="/atualizarNormaProcedimentalAction.do" method="post"
	name="AtualizarNormaProcedimentalActionForm"
	enctype="multipart/form-data"
	type="gcom.cadastro.atendimento.AtualizarNormaProcedimentalActionForm"
	onsubmit="return validateAtualizarNormaProcedimentalActionForm(this);">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<input type="hidden" name="tipoPesquisa" />

	<html:hidden property="okNormaProcedimental" />

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
					<td class="parabg">Atualizar Norma Procedimental</td>
					<td width="11" valign="top"><img border="0" src="<bean:message key='caminho.imagens'/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			 
			 <table width="100%" border="0">
			 	<tr> 
                	<td colspan="2">Para atualizar a Norma Procedimental, informe os dados abaixo:</td>
              	</tr>
              	
              	<tr>
                	<td><strong>C&oacute;digo: </strong></td>
                	<td><html:hidden property="codigoNormaProcedimental" /> 
						<bean:write name="AtualizarNormaProcedimentalActionForm" property="codigoNormaProcedimental" /></td>
              	</tr>
              	
              	<tr>
					<td align="left"><strong> Conteúdo: </strong></td>
					<td colspan="3" align="left"><input type="file" style="textbox"	name="conteudoNormaProcedimental" size="38" />	
					
						<logic:notEmpty name="conteudoNormaProcedimental">
						  <a href="javascript:abrirVisualizarArquivo();">
							<img src="imagens/imgfolder.gif" style="width:18px;height:18px">
						  </a>
						</logic:notEmpty>
					</td>
				</tr>	
              	
              	<tr>
					<td><strong>Título: <font color="#FF0000">*</font></strong></td>
					<td align="right">
					<div align="left"><strong><html:text
						property="tituloNormaProcedimental" maxlength="50" size="50" /></strong></div>
					</td>
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
              	
              	</table>
              	
              	<table width="100%" border="0">
	              <tr>
					<td>
						<logic:present name="manter" scope="session">
							<input type="button" name="ButtonReset" class="bottonRightCol" value="Voltar"
								onClick="javascript:window.location.href='/gsan/exibirManterNormaProcedimentalAction.do'">
						</logic:present>

						<logic:notPresent name="manter" scope="session">
							<input type="button" name="ButtonReset"  class="bottonRightCol" value="Voltar"
								onClick="javascript:window.location.href='/gsan/exibirFiltrarNormaProcedimentalAction.do'">
						</logic:notPresent> 
					<input name="Button" type="button" class="bottonRightCol"
						value="Desfazer" align="left"
						onclick="window.location.href='<html:rewrite page="/exibirAtualizarNormaProcedimentalAction.do?codigoNormaProcedimental=${AtualizarNormaProcedimentalActionForm.codigoNormaProcedimental}"/>'">
					<input name="Button" type="button" class="bottonRightCol"
							value="Cancelar" align="left"
							onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"></td>
					<td align="right"><input name="Button" type="button"
						class="bottonRightCol" value="Atualizar" align="right"
						onClick="javascript:validarForm(document.forms[0]);"></td>
				</tr>
	              
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