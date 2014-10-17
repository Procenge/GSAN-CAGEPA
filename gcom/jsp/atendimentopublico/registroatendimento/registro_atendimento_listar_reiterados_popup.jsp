<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript">
	function consultarRAPopup(id) {
		var form = document.forms[0];
		form.action = 'exibirConsultarRegistroAtendimentoPopupAction.do?numeroRA='+id+'&caminhoTelaPesquisaRetorno=true';
		form.submit();
	}
</script>
	
</head>

<body leftmargin="5" topmargin="5">

<html:form action="/exibirConsultarRegistroAtendimentoPopupAction.do"
	name="ConsultarRegistroAtendimentoPopupActionForm"
	type="gcom.gui.atendimentopublico.registroatendimento.ConsultarRegistroAtendimentoPopupActionForm"
	method="post">
	<table width="100%" border="0" bgcolor="#99CCFF">
	 <tr>
	  <td>
	  
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
				<td class="parabg">Dados Gerais	do Registros de Atendimento</td>
				<td width="11"><img border="0"
					src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
			</tr>
		</table>
		<p>&nbsp;</p>

	<table>
		<tr bordercolor="#000000">
			<td width="16%" bgcolor="#90c7fc">
			<div align="center"><strong>Número do RA</strong></div>
			</td>
			<td width="20%" bgcolor="#90c7fc">
			<div align="center"><strong>Tipo da Solicitação</strong></div>
			</td>
			<td width="30%" bgcolor="#90c7fc">
			<div align="center"><strong>Especificação</strong></div>
			</td>
			<td width="20%" bgcolor="#90c7fc">
			<div align="center"><strong>Data de Atendimento</strong></div>
			</td>
			<td width="14%" bgcolor="#90c7fc">
			<div align="center"><strong>Situação</strong></div>
			</td>
		</tr>
		<logic:present name="colecaoRegistroAtendimentoHelper">
			<%int cont = 0;%>
			<logic:iterate name="colecaoRegistroAtendimentoHelper" id="registroAtendimentoHelper">
				<%cont = cont + 1; if (cont % 2 == 0) {%>
				<tr bgcolor="#cbe5fe">
					<%} else {%>
				<tr bgcolor="#FFFFFF">
					<%}%>
					<td width="16%" align="center">
					<a href="javascript:consultarRAPopup(<bean:write name="registroAtendimentoHelper" property="idRegistroAtendimento"/>);">
					${registroAtendimentoHelper.idRegistroAtendimento}</a></td>
					<td width="30%" align="center">${registroAtendimentoHelper.tipoSolicitacao}</td>
					<td width="33%" align="left">${registroAtendimentoHelper.especificacao}</td>
					<td width="21%" align="center">${registroAtendimentoHelper.dataAtendimento}</td>
					<td width="21%" align="center">${registroAtendimentoHelper.situacao}</td>									
				</tr>
			</logic:iterate>
		</logic:present>
		</table>

		<table>
			<tr>
				<td>
					<input name="ButtonFechar" type="button" class="bottonRightCol" 
						value="Fechar" onclick="javascript:window.close();" style="width: 70px;">
				</td>
				<td>
					&nbsp;
				</td>
				<logic:present name="botaoTipo">
					<logic:equal name="botaoTipo" value="voltar"> 
						<td>
							<input name="ButtonVoltar" type="button" class="bottonRightCol" 
								value="Voltar" onclick="history.back();" style="width: 70px;">
						</td>
					</logic:equal>
				</logic:present>
				</td>
			</tr>
		</table>

	  </td>
	 </tr>
	</table>
	</html:form>
</body>

</html:html>
