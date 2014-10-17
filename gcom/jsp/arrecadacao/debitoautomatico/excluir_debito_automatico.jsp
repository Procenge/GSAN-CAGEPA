<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<%@ page import="gcom.util.Util"%>
<%@ page import="java.math.BigDecimal"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">


<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="ExibirExcluirDebitoAutomaticoActionForm" />


<SCRIPT LANGUAGE="JavaScript">
<!--

function limparForm() {

	var form = document.forms[0];

	form.idImovel.value = '';
	form.inscricaoImovel.value = '';
	form.codigoBanco.value = '';
	form.codigoAgencia.value = '';
	form.identificacaoClienteBanco.value = '';
	form.dataOpcao.value = '';
	form.dataInclusao.value = '';
	
}

  function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

    var form = document.forms[0];
	
    if (tipoConsulta == 'imovel') {
      form.idImovel.value = codigoRegistro;
      form.submit();
    }
 }

 function submitForm() {
	 var form = document.forms[0];
     form.action = 'excluirDebitoAutomaticoAction.do';	
     form.submit();
 }
 
//-->
</SCRIPT>

</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');">

<html:form action="/exibirExcluirDebitoAutomaticoAction" method="post">

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

			<td width="625" valign="top" class="centercoltext">

			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Excluir Débito Automático</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td>Para excluir o débito automático, informe os dados abaixo:</td>
					<td align="right"></td>					
				</tr>
			</table>

			<table width="100%" border="0">
				<tr>
					<td height="10" width="200"><strong>Matrícula do Imóvel:<font
						color="#FF0000">*</font></strong></td>
					<td width="403"><html:text property="idImovel" maxlength="9"
						size="9"
						onkeypress="validaEnterComMensagem(event, 'exibirExcluirDebitoAutomaticoAction.do', 'idImovel', 'Matrícula do Imóvel')" />
					<a
						href="javascript:abrirPopup('exibirPesquisarImovelAction.do', 400, 800);">
					<img width="23" height="21"
						src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" /></a>
					
					<logic:present name="corInscricao">
	
						<logic:equal name="corInscricao" value="exception">
							<html:text property="inscricaoImovel" size="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
						</logic:equal>
		
						<logic:notEqual name="corInscricao" value="exception">
							<html:text property="inscricaoImovel" size="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
						</logic:notEqual>
		
					</logic:present>
		
					<logic:notPresent name="corInscricao">
		
						<logic:empty name="ExibirExcluirDebitoAutomaticoActionForm" property="idImovel">
							<html:text property="inscricaoImovel" size="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
						</logic:empty>
						<logic:notEmpty name="ExibirExcluirDebitoAutomaticoActionForm" property="idImovel">
							<html:text property="inscricaoImovel" size="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
						</logic:notEmpty>
						
		
					</logic:notPresent>
					
					<a href="javascript:limparForm();" tabindex="1"> 
						<img border="0" src="<bean:message key='caminho.imagens'/>limparcampo.gif" style="cursor: hand;" /> 
					</a>	
					</td>
				</tr>
				<tr>
					<td colspan="2"><hr></td>
				</tr>
				<tr>
					<td>
						<strong>Banco:</strong>
					</td>
					<td>
						<html:text property="codigoBanco" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" size="50"/>
					</td>
				</tr>
				<tr>
					<td>
						<strong>Agência:</strong>
					</td>
					<td>
						<html:text property="codigoAgencia" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" size="50"/>
					</td>
				</tr>
				<tr>
					<td>
						<strong>Identificação do cliente no banco:</strong>
					</td>
					<td>
						<html:text property="identificacaoClienteBanco" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" size="50"/>
					</td>
				</tr>
				<tr>
					<td>
						<strong>Data da Opção:</strong>
					</td>
					<td>
						<html:text property="dataOpcao" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" size="50"/>
					</td>
				</tr>
				<tr>
					<td>
						<strong>Data da Inclusão:</strong>
					</td>
					<td>
						<html:text property="dataInclusao" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" size="50"/>
					</td>
				</tr>
				<tr>
	                <td height="24">&nbsp;</td>
	                <td colspan="2"> <strong><font color="#FF0000">*</font></strong> Campos obrigat&oacute;rios</td>
             	</tr>
             	<tr>
	                <td colspan="2"><hr></td>	                
             	</tr>
				<tr>
					<td align="left">
						<input type="button" name="ButtonLimpar" class="bottonRightCol" value="Limpar"
						onClick="javascript:limparForm();">
					
						<input type="button" name="ButtonCancelar" class="bottonRightCol" value="Cancelar"
						onClick="javascript:document.forms[0].target='';window.location.href='/gsan/telaPrincipal.do'">
					</td>
					<td align="right">
						<input type="button" name="ButtonConcluir" class="bottonRightCol" value="Concluir"
						onClick="javascript:submitForm();">
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
