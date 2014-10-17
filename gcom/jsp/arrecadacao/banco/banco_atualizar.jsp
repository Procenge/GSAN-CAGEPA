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

 <html:javascript staticJavascript="false"  formName="InserirBancoActionForm" />

 <script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
 <script language="JavaScript" src="<bean:message key="caminho.js"/>jquery-1.4.2.min.js"></script>
 <script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
 <script>

function validarForm(form){
	
	if(validateInserirBancoActionForm(form)){
		form.submit();
	}
}

 </script>
</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');">
<div id="formDiv">
<html:form action="/atualizarBancoAction.do" method="post"
	name="InserirBancoActionForm"
	enctype="multipart/form-data"
	type="org.apache.struts.action.DynaActionForm"
	onsubmit="return validateAtualizarBancoActionForm(this);">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>
	
	<INPUT TYPE="hidden" ID="indicadorUsoAux" name="indicadorUsoAux" value="${requestScope.indicadorUsoAux}" />
	
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
					<td class="parabg">Atualizar Banco</td>
					<td width="11" valign="top"><img border="0" src="<bean:message key='caminho.imagens'/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			 
			 <table width="100%" border="0">
			 	<tr> 
                	<td colspan="2">Para atualizar o banco, informe os dados abaixo:</td>
              	</tr>
              	
              	<tr>
					<td>
						<strong>Código:</strong>
					</td>
					<td>
						<html:hidden property="idBanco" /> 
							<bean:write name="InserirBancoActionForm" property="idBanco" /></td>
					</td>
				</tr>
				
				<tr>
					<td width="162"><strong>Nome:<font color="#FF0000">*</font></strong></td>
					<td><strong> <html:text property="nome" size="40" maxlength="40"></html:text> </strong></td>
				</tr>	
				<tr>
					<td width="162"><strong>Nome Abreviado:<font color="#FF0000">*</font></strong></td>
					<td><strong> <html:text property="nomeAbreviado" size="10" maxlength="10"></html:text> </strong></td>
				</tr>		
				<tr>
					<td><strong>Indicador de Uso<font color="#FF0000">*</font></strong></td>
					<td><strong> <html:radio property="indicadorUso" value="1" />
					<strong>Ativo <html:radio property="indicadorUso" value="2" />
					Inativo</strong> </strong></td>
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
								onClick="javascript:window.history.back();">
						</logic:present>

						<logic:notPresent name="manter" scope="session">
							<input type="button" name="ButtonReset"  class="bottonRightCol" value="Voltar"
								onClick="javascript:window.location.href='/gsan/exibirFiltrarBancoAction.do?limpar=S'">
						</logic:notPresent> 
					<input name="desfazer"
							class="bottonRightCol" value="Desfazer" type="button"
							onclick="window.location.href='/gsan/exibirAtualizarBancoAction.do?desfazer=S&idBanco=<bean:write name="InserirBancoActionForm" property="idBanco" />';">
					
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