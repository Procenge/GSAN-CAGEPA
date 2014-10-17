<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<%@ page
	import="gcom.gerencial.bean.CobrancaAcaoPerfilIndicadorHelper,gcom.gerencial.bean.CobrancaAcaoPerfilHelper"%>
<SCRIPT LANGUAGE="JavaScript">
<!--

function fechar(){
		window.close();
}
</SCRIPT>
</head>
<body leftmargin="5" topmargin="5">
<html:form action="/exibirConsultarResumoAcaoCobrancaPopupAction.do"
	name="ResumoAcaoCobrancaActionForm"
	type="gcom.gui.gerencial.cobranca.ResumoAcaoCobrancaActionForm"
	method="post">
	<table width="570" border="0" cellpadding="0" cellspacing="5">
		<tr>
			<td width="560" valign="top" class="centercoltext">
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
					<td class="parabg">Consultar Motivo de Documentos Não Entregues</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<table>
				
				<tr>
					<td><strong>Ação de Cobrança:</strong></td>
					<td>${requestScope.cobrancaAcao}</td>
				</tr>
				<tr>
					<td><strong>Situação da Ação:</strong></td>
					<td>${requestScope.cobrancaAcaoSituacao}</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>

			<table bgcolor="#90c7fc" border="0">
				<tr>
					<td bgcolor="#79bbfd" align="center"><strong> Código </strong></td>
					<td bgcolor="#79bbfd" align="center"><strong> Descrição </strong></td>
					<td bgcolor="#79bbfd" align="center"><strong> Quantidade </strong></td>				
					<td bgcolor="#79bbfd" align="center"><strong> Valor </strong></td>
					
				</tr>
				
				<logic:notEmpty name="colecao">

					<logic:iterate name="colecao"
						id="cobrancaAcaoSituacaoHelper">
				
						  <tr>
							
							<td width="17%" bgcolor="#cbe5fe" align="center" rowspan="1">
							<bean:write name="cobrancaAcaoSituacaoHelper" property="id" /></td>
							
							<td width="17%" bgcolor="#cbe5fe" align="center" rowspan="1">
							<bean:write name="cobrancaAcaoSituacaoHelper" property="descricao" /></td>

							<td width="17%" bgcolor="#cbe5fe" align="center" rowspan="1">
							<bean:write name="cobrancaAcaoSituacaoHelper" property="quantidadeDocumento" /></td>
							
							<td width="17%" bgcolor="#cbe5fe" align="center" rowspan="1">
							<bean:write name="cobrancaAcaoSituacaoHelper" property="valorDoumento" /></td>
                          
                         </tr>
						
					
				
					</logic:iterate>
				</logic:notEmpty>
				
			</table>
			<table border="0" width="100%">
				<tr>
					<td colspan="3">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="3" align="right"><input name="Button" type="button"
						class="bottonRightCol" value="Fechar"
						onClick="javascript:fechar();"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
<body>
</html:html>
