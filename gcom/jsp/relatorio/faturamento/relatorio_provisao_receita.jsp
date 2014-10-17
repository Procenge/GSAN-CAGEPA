<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<%@ page import="gcom.util.ConstantesSistema"%>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="GerarRelatorioDynaForm"/>
<script>

function desfazer() {
	var form = document.forms[0];
	if(form.limparTela.value == 's') {
		esconderCombos();	
		form.mesAno.value = '';
		for(i = 0; i < form.psTipoRelatorio.length; i++){
			form.psTipoRelatorio[i].checked = false;
		}
		esconderLocalidade();
		limparLocalidade();
	} else {
		esconderCombos();
	}
}

function limparCampos() {
	var form = document.forms[0];	
	esconderCombos();	
	form.mesAno.value = '';	
	for(i = 0; i < form.psTipoRelatorio.length; i++){
		form.psTipoRelatorio[i].checked = false;
	}
	esconderLocalidade();
	limparLocalidade();	
}

function limparCombos() {
	var form = document.forms[0];
	form.gerenciaRegionalporLocalidadeId.value = '-1';
	form.gerenciaRegionalId.value = '-1';
	form.psCodigoUnidade.value = '-1';
}

function validaCamposVazios(valor, nomeCampo) {

	var retorno = true;
	if(isBrancoOuNulo(valor.value)) {
		alert('Preencha o campo : '+nomeCampo);
		valor.focus();
		retorno = false;
	}
	return retorno;
}

function validarForm(){
	var form = document.GerarRelatorioDynaForm; 

	if(validaCamposVazios(form.mesAno, 'Mês/Ano do Faturamento')) {
		
		if(form.ehRelatorioBatch.value == 2) {
		    form.target = "_blank";
		}
		
		form.submit();	
		form.target = ""; // Quando já gerado um relatorio o target fica == _blank ao informar localidade e pressionar Enter o target erradamente estava _blank por isso setar para branco.
	}
	
	  
}

</script>

</head>

<body leftmargin="5" topmargin="5" onload="javascript:desfazer();">

<html:form action="/gerarRelatorioAction.do"
	name="GerarRelatorioDynaForm"
	type="org.apache.struts.action.DynaActionForm"
	method="post">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>
	
	<input type="hidden" name="acao" value="gerarRelatorio"/>
	<input type="hidden" name="relatorio" value="RelatorioProvisaoReceita.rpt"/>
	<%--  ehRelatorioBatch indica se o Relatrio será processado como uma Rotina Batch  --%>
	<%--  1 - Indica que SIM, é uma Rotina Batch                                       --%>
	<%--  2 - Indica que NAO, não é uma Rotina Batch e o Relatório será gerado online  --%>
	<input type="hidden" name="ehRelatorioBatch" value="1"/>
	
	
	
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
			<td valign="top" bgcolor="#003399" class="centercoltext">
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
					<td class="parabg">Relatório Provisão de Receita</td>

					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
				<tr>
					<td height="5" colspan="3"></td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">
					
					<p>&nbsp;</p>
					</td>
				</tr>
				<tr>
					<td width="26%">
						<strong>Mês/Ano da Provisão:</strong>
					</td>
					<td colspan="2">
					
						<input type="text" name="mesAno" size="7" maxlength="7" value="<c:out value="${mesAno}"/>" readonly="readonly">
					
						<strong>&nbsp; </strong>mm/aaaa						
					</td>
				</tr>
			
			</table>
			<table width="100%" border="0">
				<tr>
					<td valign="top">
					
					
						
					</td>
					<td valign="top">
					  <div align="right">
					  
					  <input name="botaoGerar" type="button" 
					  class="bottonRightCol" value="Gerar Relatório" 
					  onclick="javascript:validarForm();" />
					
					</div>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
	</table>	
	<%@ include file="/jsp/util/rodape.jsp"%>
</body>
</html:form>
</html:html>
