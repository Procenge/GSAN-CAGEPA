<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

	<head>
		<%@ include file="/jsp/util/titulo.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
		
		<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
		<html:javascript staticJavascript="false" formName="GerarRelatorioQuadroHidrometrosActionForm" dynamicJavascript="true" />
		<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>		
		<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>	
		<script language="JavaScript">
			function validarForm(form){
					form.submit();
			}
		</script>
	</head>

	<body leftmargin="5" topmargin="5" onload="javascript:setarFoco('${requestScope.nomeCampo}');">
	
		<html:form action="/gerarRelatorioQuadroHidrometrosAction" name="GerarRelatorioQuadroHidrometrosActionForm" 
					type="gcom.gui.relatorio.micromedicao.GerarRelatorioQuadroHidrometrosActionForm" method="post">
		
			<%@ include file="/jsp/util/cabecalho.jsp"%>
			<%@ include file="/jsp/util/menu.jsp"%>
		
			<table width="770" border="0" cellspacing="4" cellpadding="0">
				<tr>
					<td width="149" valign="top" class="leftcoltext">
					<div align="center">
						<%@ include file="/jsp/util/informacoes_usuario.jsp"%>
						<%@ include file="/jsp/util/mensagens.jsp"%>
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
							<td class="parabg">Gerar Relatório Quadro de Hidrômetros </td>
							<td width="11" valign="top"><img border="0"
								src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
						</tr>
					</table>
		
					<p>&nbsp;</p>
					<table width="100%" border="0">
						<tr>
							<td colspan="3">Para gerar o relatório quadro de hidrômetros, informe os dados abaixo:</td>
						</tr>
						<tr>
							<td>
								<strong>Data para Referência:<font color="#FF0000">*</font></strong>
							</td>
							<td align="left">
								<html:text property="dataReferencia" size="10" maxlength="10" onkeyup="mascaraData(this, event);" />
								<a href="javascript:abrirCalendario('GerarRelatorioQuadroHidrometrosActionForm', 'dataReferencia')">
								<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="20" border="0" align="middle" alt="Exibir Calendário" /></a>
								dd/mm/aaaa
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td align="left">
								<font color="#FF0000">*</font> Campos Obrigat&oacute;rios
							</td>
						</tr>
					</table>		
					<table>
						<tr>
							<td width="550" align="right">&nbsp;</td>
							<td align="right">
								<gcom:controleAcessoBotao name="Button" value="Gerar" onclick="validarForm(document.forms[0]);" url="gerarRelatorioQuadroHidrometrosAction.do" />
							</td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
			<%@ include file="/jsp/util/rodape.jsp"%>
		</html:form>
	</body>
</html:html>
