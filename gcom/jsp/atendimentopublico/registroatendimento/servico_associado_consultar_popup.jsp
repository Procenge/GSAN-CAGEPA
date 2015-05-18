
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

<!--

function fechar(){
		window.close();
-->

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false" 
	formName="ConsultarServicoAssociadoActionForm" />



<script language="JavaScript">


	function redirecionaSubmit(caminhoAction) {
   	var form = document.forms[0];
   	form.action = caminhoAction;
   	form.submit();
   	return true;
 }

</script>


</head>
<body leftmargin="5" topmargin="5" onload="resizePageSemLink(690, 400);">


<html:form
	action="/exibirConsultarServicoAssociadoAction.do"
	name="ConsultarServicoAssociadoActionForm"
	type="gcom.gui.atendimentopublico.registroatendimento.ConsultarServicoAssociadoActionForm"
	method="post"
	onsubmit="">

	<table width="635" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="635" valign="top" class="centercoltext">
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
					<td class="parabg">Serviços Associados à Especificação</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">	
				

				<tr bgcolor="#cbe5fe">
					<td align="center" colspan="2">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td height="18" colspan="6" align="center"><strong>Dados Gerais
							do Registros de Atendimento</strong></td>
						</tr>
						<tr bgcolor="#cbe5fe">
						<tr>
						<tr bordercolor="#000000">
							<td width="20%" bgcolor="#90c7fc">
							<div align="center"><strong>Código do Serviço </strong></div>
							</td>

							<td width="30%" bgcolor="#90c7fc">
							<div align="center"><strong>Descrição do Serviço </strong></div>
							</td>
							<td width="20%" bgcolor="#90c7fc">
							<div align="center"><strong>Valor do Serviço </strong></div>
							</td>
						
						
						<logic:present name="exibirServicoTipoValorLocalidade">
						     
						     <td width="30%" bgcolor="#90c7fc">
							  <div align="center"><strong>Valor do Serviço por Localidade </strong></div>
							 </td>
							 
						 </logic:present>

						</tr>
						<logic:present
							name="colecaoServicoAssociadoValorHelper">
							<%int cont = 0;%>
							<logic:iterate
								name="colecaoServicoAssociadoValorHelper"
								id="servicoAssociadoValorHelper">
								<%cont = cont + 1;
			if (cont % 2 == 0) {%>
								<tr bgcolor="#FFFFFF">
									<%} else {

			%>
								<tr bgcolor="#cbe5fe">
									<%}%>									
									<td width="20%" align="center">${servicoAssociadoValorHelper.idServicoTipo}</td>
									<td width="30%" align="left">${servicoAssociadoValorHelper.descricaoServicoTipo}</td>
									<td width="20%" align="center">${servicoAssociadoValorHelper.valorServico}</td>			
									
									<logic:present name="exibirServicoTipoValorLocalidade">
								    <td width="30%" align="center">${servicoAssociadoValorHelper.listaServicoTipoValorLocalidade}</td>			
									 </logic:present>
												
								</tr>
							</logic:iterate>
						</logic:present>
					</table>
				</tr>
				<tr>
					<td align="right"><input name="button" type="button" class="bottonRightCol"
						value="Fechar"
						onclick="window.close();"
						align="right"></td>

				</tr>
			</table>
			</td>
		</tr>
	</table>

	
</html:form>
</body>
</html:html>
