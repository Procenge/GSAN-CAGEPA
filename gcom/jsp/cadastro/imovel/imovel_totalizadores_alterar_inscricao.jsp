<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<%@page import="gcom.cadastro.sistemaparametro.SistemaParametro"%><html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>

<SCRIPT LANGUAGE="JavaScript">
<!--

function validarForm(form){

	var mensagem = "";
	
	if(validaRadioButton(form.indicadorAlteracaoRota,"'Uma Opção para Confirmar a Alteração da Inscrição'") != ""){
		mensagem = mensagem + validaRadioButton(form.indicadorAlteracaoRota,"'Uma Opção para Confirmar a Alteração da Inscrição'")+"\n";
	}

	// Confirma a validação do formulário
	if(mensagem != ""){
		alert(mensagem);
	}else{
		form.submit();
	}

}

function validaRadioButton(nomeCampo,mensagem){
	var alerta = "";
	if(!nomeCampo[0].checked && !nomeCampo[1].checked){
		alerta = mensagem +" deve ser selecionada.";
	}
	return alerta;
}

//-->
</SCRIPT>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="AlterarImovelInscricaoActionForm" />

</head>

<body leftmargin="5" topmargin="5" onload="javascript:setarFoco('${requestScope.nomeCampo}');">


<html:form action="/alterarImovelInscricaoAction" method="post">


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

	<td width="625" valign="top" class="centercoltext">

	  <table height="100%">
        <tr>
          <td></td>
        </tr>
      </table>

      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
          <td class="parabg">Alterar Inscri&ccedil;&atilde;o de Im&oacute;vel</td>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
        </tr>
      </table>
      <p>&nbsp;</p>

      <table width="100%" border="0" >
        </table>
        <table width="100%" border="0" >
        <tr>
        	<td>&nbsp;</td>
        </tr>
        <tr>
            <td>Total de Im&oacute;veis com a mesma Rota da Quadra:</td>
            <td>
				<strong><bean:write name="AlterarImovelInscricaoActionForm" property="totalImovelMesmaRota" /></strong>
			</td>
        </tr>
        <tr>
            <td>Total de Im&oacute;veis com Rota diferente da Rota da Quadra:</td>
            <td>
				<strong><bean:write name="AlterarImovelInscricaoActionForm" property="totalImovelRotaDiferente" /></strong>
			</td>
        </tr>
        <tr>
        	<td>&nbsp;</td>
        </tr>
        <tr>
            <td colspan="2"><strong>Selecione uma Op&ccedil;&atilde;o para Confirmar a Altera&ccedil;&atilde;o da Inscri&ccedil;&atilde;o:</strong></td>
            <td align="right"></td>
        </tr>
        <tr>
        	<td>&nbsp;</td>
        </tr>
        <tr>
        	<td>
        		<html:radio property="indicadorAlteracaoRota" value="1"/>Com a substitui&ccedil;&atilde;o das rotas de todos os im&oacute;veis da quadra origem para as rotas correspondentes da quadra destino.
			</td>
        </tr>
        <tr>
        	<td>
        		<html:radio property="indicadorAlteracaoRota" value="2"/>Com a substitui&ccedil;&atilde;o das rotas dos im&oacute;veis da quadra origem com a mesma rota para as rotas correspondentes da quadra destino, permanecendo os demais com suas atuais rotas.
			</td>
        </tr>
        
        <tr>
        	<td>&nbsp;<BR></td>
        </tr>
        
        <tr>
			<td><input type="button" name="ButtonReset"  class="bottonRightCol" value="Voltar"
				onClick="javascript:history.back();"> &nbsp;
				<input type="button" name="Button" class="bottonRightCol" value="Cancelar" 
				onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
				style="width: 80px"/></td>
			<td align="right">
			<gcom:controleAcessoBotao name="Button" value="Confirmar"
							  onclick="validarForm(document.AlterarImovelInscricaoActionForm);" url="alterarImovelInscricaoAction.do"/>
			<!-- 
			<INPUT type="button" class="bottonRightCol" value="Alterar Inscrição" onclick="validarForm(document.AlterarImovelInscricaoActionForm);" tabindex="11"> --></td>
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
