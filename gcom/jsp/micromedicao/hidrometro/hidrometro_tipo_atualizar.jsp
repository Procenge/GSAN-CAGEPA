<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@page isELIgnored="false"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<link rel="stylesheet"	href="<bean:message key="caminho.css"/>EstilosCompesa.css"	type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false" formName="AtualizarHidrometroTipoActionForm" dynamicJavascript="true" />

<script language="JavaScript"	src="<bean:message key="caminho.js"/>util.js"></script>


<script language="JavaScript">

    function validarForm() {
	    var form = document.forms[0];
	    if(validateAtualizarHidrometroTipoActionForm(form)){	
			submeterFormPadrao(form); 
		}
   	} 
</script>
</head>

<body leftmargin="5" topmargin="5"	onload="setarFoco('${requestScope.nomeCampo}');">

<html:form action="/atualizarHidrometroTipoAction.do"	name="AtualizarHidrometroTipoActionForm"
	type="gcom.gui.micromedicao.hidrometro.AtualizarHidrometroTipoActionForm"	method="post">

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
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Atualizar Tipo de Hidrômetro</td>
					<td width="11" valign="top"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>

			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para atualizar o Tipo do Hidrômetro, informe os dados abaixo:</td>
				</tr>
				
				<tr>
					<td width="162"><strong>Identificador:<font color="#FF0000">*</font></strong></td>
					<td><html:hidden property="idHidrometroTipo"/> 
						<bean:write name="AtualizarHidrometroTipoActionForm" property="idHidrometroTipo" /></td>
				</tr>
								
				<tr>
				  <td  width="30%" class="style3"><strong>Descrição:<font color="#FF0000">*</font></strong></td>
				  <td  width="70%" colspan="2"><strong><b><span class="style2"> 
				  		<html:text  property="descricaoTipoHidrometro" size="35" maxlength="20" tabindex="1"/></span></b></strong>
				  		</td>
				</tr>	
			
				<tr>
				  <td  width="30%" class="style3"><strong>Descrição Abreviada:</strong></td>
				  <td  width="70%" colspan="2"><strong><b><span class="style2"> 
				  		<html:text  property="descricaoAbreviada" size="5" maxlength="5" tabindex="2"/> </span></b></strong></td>
				</tr>
				<tr>
					<td><strong>Indicador de uso:<font color="#FF0000">*</font></strong></td>
						<td><html:radio property="indicadorUso" value="1" tabindex="5"/><strong>Ativo
							<html:radio property="indicadorUso" value="2" tabindex="6"/>Inativo</strong> 
						</td>
				</tr>
				
				<tr>
				    <td></td>
					<td>
					<div align="left" ><font color="#FF0000">*</font> Campos obrigatórios</div>
					</td>
				</tr>								
			</table>
			<table width="100%">
				<tr>
					<td width="40%" align="left">
						<input type="button" name="ButtonCancelar" class="bottonRightCol" value="Voltar"
						onClick="javascript:window.location.href='${sessionScope.caminhoRetornoVoltar}';">
						
						<input type="button" name="ButtonReset" class="bottonRightCol" value="Desfazer" 
						onclick="window.location.href='/gsan/exibirAtualizarHidrometroTipoAction.do?desfazer=S&reloadPage=1&idHidrometroTipo=${idHidrometroTipo}';">
						
						<input type="button"name="ButtonCancelar" class="bottonRightCol" value="Cancelar" 
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
					</td>
					<td align="right">
						<input type="button" name="Button" class="bottonRightCol"
						value="Atualizar" onclick="javascript:validarForm();" tabindex="7" />
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>		
				</tr>			
				<tr>
					<td>&nbsp;</td>		
				</tr>			
				<tr>
					<td>&nbsp;</td>		
				</tr>			
				<tr>
					<td>&nbsp;</td>		
				</tr>			
				<tr>
					<td>&nbsp;</td>		
				</tr>			
			</table>
		</tr>
	</table>
	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</html:html>
