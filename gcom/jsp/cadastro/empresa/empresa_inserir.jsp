<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ page import="gcom.util.Util" %>
<%@ page import="gcom.util.ConstantesSistema" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
 <%@ include file="/jsp/util/titulo.jsp"%>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

 <html:javascript staticJavascript="false"  formName="InserirEmpresaActionForm" />

 <script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
 <script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
 <script>

	function validaEnterEmpresa(tecla, caminhoActionReload, nomeCampo) {
	
		var form = document.InserirEmpresaActionForm;
		validaEnterComMensagem(tecla, caminhoActionReload, nomeCampo, "Código da Empresa");
	}
	
	function validaForm() {
	  	var form = document.forms[0];
	  	var formValidacao = document.forms[0];
	 	if(validateInserirEmpresaActionForm(form)){
   			submeterFormPadrao(form);
	 	}
	}
	
 </script>
</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');">
<div id="formDiv">
<html:form 	action="/inserirEmpresaAction" enctype="multipart/form-data"
			method="post" focus="codigoEmpresa">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<input type="hidden" name="tipoPesquisa" />

	<html:hidden property="okEmpresa" />

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
					<td width="11"><img border="0"
						src="<bean:message key='caminho.imagens'/>parahead_left.gif" /></td>
					<td class="parabg">Inserir Empresa</td>
					<td width="11" valign="top"><img border="0"
						src="<bean:message key='caminho.imagens'/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">

				<tr>
					<td colspan="2">Para adicionar uma empresa, informe os dados
					abaixo:</td>
				</tr>
				<tr>
					<td><strong>C&oacute;digo da Empresa: <font
						color="#FF0000">*</font></strong></td>
					<td><strong> <html:text property="codigoEmpresa"
						maxlength="4" size="9"
						onchange="return validaEnterEmpresa(event, 'exibirInserirEmpresaAction.do', 'codigoEmpresa');"
						onkeyup="javascript:verificaNumeroInteiro(this);"
						 />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<logic:present name="corEmpresa">
						<logic:equal name="corEmpresa" value="exception">
							<html:text property="mensagemEmpresa" size="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>
						<logic:notEqual name="corEmpresa" value="exception">
							<html:text property="mensagemEmpresa" size="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> </strong></td>
				</tr>

				<tr>
					<td><strong>Nome Empresa: <font color="#FF0000">*</font></strong></td>
					<td align="right">
					<div align="left"><strong><html:text
						property="nomeEmpresa" maxlength="50" size="50" /></strong></div>
					</td>
				</tr>

				<tr>
					<td><strong>Email Empresa: </strong></td>
					<td align="right">
					<div align="left"><strong><html:text
						property="emailEmpresa" maxlength="40" size="50" /></strong></div>
					</td>
				</tr>

				<tr>
					<td><strong>Nome Abreviado: <font color="#FF0000">*</font></strong></td>
					<td align="right">
					<div align="left"><strong><html:text
						property="nomeEmpresaAbreviado" maxlength="10" size="10" /></strong></div>
					</td>
				</tr>
				<tr>
					<td align="left"><strong> Logotipo: </strong></td>
					<td colspan="3" align="left"><input type="file" style=""
						name="logoEmpresa" size="50" /></td>
					<td></td>
				</tr>
				<tr>
					<td><strong> <font color="#FF0000"></font></strong></td>
					<td align="right">
					<div align="left"><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios</div>
					</td>
				</tr>
				<tr>
					<td><strong> <font color="#FF0000"> <input
						name="btLimpar" class="bottonRightCol" value="Limpar"
						type="button"
						onclick="window.location.href='/gsan/exibirInserirEmpresaAction.do?menu=sim';">
					<input type="button" name="Button" class="bottonRightCol"
						value="Cancelar" tabindex="6"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
						style="width: 80px" /> </font></strong></td>
					<td align="right"><input type="button" onclick="javascript:validaForm();" name="botaoInserir"
						class="bottonRightCol" value="Inserir"></td>
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