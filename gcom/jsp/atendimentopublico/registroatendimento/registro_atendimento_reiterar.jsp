<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@ page import="gcom.util.ConstantesSistema" %>
<%@ page import="gcom.atendimentopublico.registroatendimento.RegistroAtendimento" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false" formName="ReiterarRegistroAtendimentoActionForm" />

<SCRIPT LANGUAGE="JavaScript">
function validarForm() {
	var form = document.forms[0];
		if(validateReiterarRegistroAtendimentoActionForm(form)){	     
			submeterFormPadrao(form); 
		}
   	}
   
</SCRIPT>


</head>

<body leftmargin="5" topmargin="5">

<div id="formDiv">
<html:form action="/reiterarRegistroAtendimentoAction" 
name="ReiterarRegistroAtendimentoActionForm" 
type="gcom.gui.atendimentopublico.registroatendimento.ReiterarRegistroAtendimentoActionForm" 
onsubmit="return validateReiterarRegistroAtendimentoActionForm(this);" 
method="post">

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
          <td class="parabg">Reiterar RA - Registro de Atendimento</td>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
        </tr>
      </table>
      
      <p>&nbsp;</p>
      
      <table width="100%" border="0" align="center">
      <tr>
      	<td colspan="4">Dados das últimas reiterações:</td>
      </tr>
       <logic:notEmpty name="colecaoRAsReiteradas" scope="session">
       		<tr HEIGHT="30">
	       		<td width="10%" align="left"><strong>Data</strong></td>
	       		<td width="10%" align="left"><strong>Hora</strong></td>
	       		<td width="30%" align="center"><strong>Funcionário</strong></td>
	       		<td width="50%" align="center"><strong>Observação</strong></td>
       		</tr>
      		<logic:iterate name="colecaoRAsReiteradas" scope="session" id="raReiterada">
	      		<tr HEIGHT="30">
					<td width="10%" align="left"><bean:write name="raReiterada" property="ultimaAlteracao" format="dd/MM/yyyy" /></td>
					<td width="10%" align="left"><bean:write name="raReiterada" property="ultimaAlteracao" format="hh:mm:ss" /></td>
					<td width="30%" align="center"><logic:present name="raReiterada" property="registroAtendimentoUnidade.usuario.funcionario">
						<bean:write name="raReiterada" property="registroAtendimentoUnidade.usuario.funcionario.id"/> - <bean:write name="raReiterada" property="registroAtendimentoUnidade.usuario.funcionario.nome"/>
					</logic:present> </td>
					<td width="50%" align="center"><html:textarea name="raReiterada" property="observacao" readonly="true" rows="3" cols="35"/></td>
				</tr>
      		</logic:iterate>
      </logic:notEmpty>
       <tr><td><br/></td></tr>
      <tr>
      	<td colspan="4">Para reiterar o RA - Registro de Atendimento, informe os dados gerais abaixo:</td>
      </tr>
      <tr><td><br/></td></tr>
      <tr>
      	<td HEIGHT="30"><strong>Meio de Solicitação:<font color="#FF0000">*</font></strong></td>
        <td colspan="3">
			<html:select property="meioSolicitacao" style="width: 200px;font-size:11px;" tabindex="9">
				<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
				<html:options collection="colecaoMeioSolicitacao" labelProperty="descricao" property="id"/>
			</html:select>
		</td>
      </tr>
      
      <tr>
      	<td HEIGHT="30"><strong>Observacao:</strong></td>
        <td colspan="3">
			<html:textarea property="observacaoNova" cols="50" rows="5" onkeyup="limitTextArea(document.forms[0].observacaoNova, 400, document.getElementById('utilizado'), document.getElementById('limite'));"/><br>
			<strong><span id="utilizado">0</span>/<span id="limite">400</span></strong>
		</td>
      </tr>
      </table>
      
      <table width="100%" border="0">
      <tr>
      	<td >
      		<input name="Button" type="button" class="bottonRightCol" value="Desfazer" align="left"	
      			onclick="javascript:history.back()">
			<input type="button" name="ButtonCancelar" class="bottonRightCol" value="Cancelar" 
				onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
		</td>	
		<td align="right">
			<input name="Button" type="button" class="bottonRightCol" value="Confirmar" onclick="validarForm();">
		</td>
      </tr>
      </table>
      
      <p>&nbsp;</p>
	</td>
  </tr>
</table>



<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</div>

<%@ include file="/jsp/util/telaespera.jsp"%>

</body>
</html:html>

