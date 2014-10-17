<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page import="gcom.util.Util" %>
<%@ page import="gcom.util.ConstantesSistema" isELIgnored="false"%>
<%@ page import="java.util.Collection" isELIgnored="false"%>
<%@ page import="gcom.cadastro.imovel.ImovelConsumoFaixaAreaCategoria"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false" formName="ConsultarImovelAguaParaTodosActionForm" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">

function exibeHistorico(valor1, valor2) {
	abrirPopupComSubmit('consultarFaturamentoHistoricoAguaParaTodosAction.do?idImovel='+valor1+'&dataHabilitacao='+valor2,350, 750, 'Teste');
}

</script>

</head>

<logic:present name="closePage">
	<logic:equal name="closePage" value="S">
		<body leftmargin="5" topmargin="5" onload="window.close();">
	</logic:equal>
</logic:present>

<logic:notPresent name="closePage">
	<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampoFoco}');">
</logic:notPresent>

<html:form action="/consultarFaturamentoHistoricoAguaParaTodosAction.do" name="ConsultarImovelAguaParaTodosActionForm"
	type="gcom.gui.cadastro.aguaparatodos.ConsultarImovelAguaParaTodosActionForm"
	method="post">
	<table width="720" border="0" cellpadding="0" cellspacing="5">
		<tr>
			<td width="720" valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Detalhe Imóvel Água Para Todos</td>
					<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
			<table width="100%" border="0">
			<tr>
				<td>
					<table width="100%" border="0">
						<tr>
						  <td><strong>Matrícula:</strong></td>						
						  <td colspan="2">
							<html:hidden property="matricula"/>
							<bean:write name="ConsultarImovelAguaParaTodosActionForm" property="matricula"/>
				  		  </td>
				  		</tr>	
						<tr>
						  <td><strong>NIC:</strong></td>						
						  <td colspan="2">
							<html:hidden property="nic"/>
							<bean:write name="ConsultarImovelAguaParaTodosActionForm" property="nic"/>
				  		  </td>
				  		  <logic:equal name="ConsultarImovelAguaParaTodosActionForm" property="situacao" value="9">
				  		  	<td>Imóvel ainda não habilitado</td>
				  		  </logic:equal>
				  		  <logic:equal name="ConsultarImovelAguaParaTodosActionForm" property="situacao" value="0">
				  		  	<td>Imóvel Habilitado</td>
				  		  </logic:equal>
				  		  <logic:equal name="ConsultarImovelAguaParaTodosActionForm" property="situacao" value="1">
				  		  	<td>Excluído</td>
				  		  </logic:equal>
				  		</tr>	
				  		<tr>
						  <td><strong>Nome Contribuinte:</strong></td>						
						  <td colspan="2">
							<html:hidden property="nomeContribuinte"/>
							<bean:write name="ConsultarImovelAguaParaTodosActionForm" property="nomeContribuinte"/>
				  		  </td>
				  		</tr>	
				  		<tr>
						  <td><strong>Data de Cadastramento:</strong></td>						
						  <td colspan="2">
							<html:hidden property="dataCadastramento"/>
							<bean:write name="ConsultarImovelAguaParaTodosActionForm" property="dataCadastramento"/>
				  		  </td>
				  		</tr>
						<tr>
						  <td><strong>Data de Habilitação:</strong></td>						
						  <td colspan="2">
							<html:hidden property="dataHabilitacao"/>
							<bean:write name="ConsultarImovelAguaParaTodosActionForm" property="dataHabilitacao"/>
				  		  </td>
				  		</tr>
				  		<tr>
						  <td><strong>Funcionário que realizou a inclusão:</strong></td>						
						  <td colspan="2">
							<html:hidden property="usuarioInclusao"/>
							<bean:write name="ConsultarImovelAguaParaTodosActionForm" property="usuarioInclusao"/>
				  		  </td>
				  		</tr>
				  		<tr>
						  <td><strong>Referência inicial Faturamento:</strong></td>						
						  <td colspan="2">
							<html:hidden property="dataReferenciaFaruramentoInicial"/>
							<bean:write name="ConsultarImovelAguaParaTodosActionForm" property="dataReferenciaFaruramentoInicial"/>
				  		  </td>
				  		</tr>
				  		<logic:equal name="ConsultarImovelAguaParaTodosActionForm" property="situacao" value="1">
				  		<tr>
						  <td><strong>Data da Exclusão:</strong></td>						
						  <td colspan="2">
							<html:hidden property="dataExclusao"/>
							<bean:write name="ConsultarImovelAguaParaTodosActionForm" property="dataExclusao"/>
				  		  </td>
				  		</tr>
				  		<tr>
						  <td><strong>Motivo da Exclusão:</strong></td>						
						  <td colspan="2">
							<html:hidden property="motivoExclusao"/>
							<bean:write name="ConsultarImovelAguaParaTodosActionForm" property="motivoExclusao"/>
				  		  </td>
				  		</tr>
				  		<tr>
						  <td><strong>Funcionário que realizou a exclusão:</strong></td>						
						  <td colspan="2">
							<html:hidden property="usuarioExclusao"/>
							<bean:write name="ConsultarImovelAguaParaTodosActionForm" property="usuarioExclusao"/>
				  		  </td>
				  		</tr>
				  		<tr>
						  <td><strong>Referência final Faturamento:</strong></td>						
						  <td colspan="2">
							<html:hidden property="dataReferenciaFaruramentoFinal"/>
							<bean:write name="ConsultarImovelAguaParaTodosActionForm" property="dataReferenciaFaruramentoFinal"/>
				  		  </td>
				  		</tr>
				  		</logic:equal>
				  		<tr>
						  <td><strong>Código da Tarifa:</strong></td>						
						  <td colspan="2">
						  	<logic:notEqual name="ConsultarImovelAguaParaTodosActionForm" property="codigoTarifa" value="0">
								<html:hidden property="codigoTarifa"/>
								<bean:write name="ConsultarImovelAguaParaTodosActionForm" property="codigoTarifa"/>
							</logic:notEqual>
							<logic:equal name="ConsultarImovelAguaParaTodosActionForm" property="codigoTarifa" value="0">
								<html:hidden property="codigoTarifa"/>
							</logic:equal>
				  		  </td>
				  		</tr>
				  		<tr>
						  <td><strong>Data de Validade da Tarifa:</strong></td>						
						  <td colspan="2">
							<html:hidden property="dataValidadeTarifa"/>
							<bean:write name="ConsultarImovelAguaParaTodosActionForm" property="dataValidadeTarifa"/>
				  		  </td>
				  		</tr>
					</table>
				</td>
				<tr>
					<td height="24">
					<div align="right"> 
					<logic:equal name="ConsultarImovelAguaParaTodosActionForm" property="flagBotaoHist" value="1">
						<input name="Button1" type="button" class="bottonRightCol" value="Exibir Histórico de Faturamento" 
						onClick="javascript:exibeHistorico(<bean:write name="ConsultarImovelAguaParaTodosActionForm" property="matricula"/>, <bean:write name="ConsultarImovelAguaParaTodosActionForm" property="dataHabilitacaoTime"/>)">
					</logic:equal>
						<input name="Button2" type="button" class="bottonRightCol" value="Fechar" onClick="window.close();">
					</div>
					</td>
				</tr>
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>
</html:form>
</body>
</html:html>

