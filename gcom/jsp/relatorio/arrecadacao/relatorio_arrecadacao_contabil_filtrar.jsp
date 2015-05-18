<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>

<%@ page import="gcom.util.ConstantesSistema"%>

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
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false" formName="GerarRelatorioArrecadacaoContabilActionForm" />

<SCRIPT LANGUAGE="JavaScript">
<!--


function validarForm(form){

	if (validateGerarRelatorioArrecadacaoContabilActionForm(form)){
	
		var DATA_ATUAL = document.getElementById("DATA_ATUAL").value;
	
		var pdInicial = returnObject(form, "pdInicial");
		var pdFinal = returnObject(form, "pdFinal");
		
		var realizarSubmit = true;
		
		if (pdInicial.value.length > 0 && comparaData(pdInicial.value, ">", DATA_ATUAL )){
			realizarSubmit = false;
			alert("Data Inicial do Crédito posterior à data corrente " + DATA_ATUAL + ".");
			pdInicial.focus();
		}
		else if (pdFinal.value.length > 0 && comparaData(pdFinal.value, ">", DATA_ATUAL )){
			realizarSubmit = false;
			alert("Data Final do Crédito posterior à data corrente " + DATA_ATUAL + ".");
			pdFinal.focus();
		}
		else if ((pdFinal.value.length > 0 && pdFinal.value.length > 0) && comparaData(pdInicial.value, ">", pdFinal.value )){
			realizarSubmit = false;
			alert("Data Final do Crédito é anterior à Data Inicial.");
			pdFinal.focus();
		}
		
		var bancoInicioAux = returnObject(form, "pnCDAgenteInicial");
		var pnCDAgenteFinalAux = returnObject(form, "pnCDAgenteFinal");
				
 		if (bancoInicioAux.value > pnCDAgenteFinalAux.value && realizarSubmit){
			realizarSubmit = false;
			alert("Arrecador Inicial maior que Final.");
			bancoInicioAux.focus();
		}	
		
		if (realizarSubmit){
			form.target = "_blank";
			form.submit();
			form.target = "";			
		}
		
	}
}

function replicar(objetoOriginal, objetoReplicacao){

	objetoReplicacao.value = objetoOriginal.value; 

}

//-->
</SCRIPT>


</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');">

<html:form action="/gerarRelatorioArrecadacaoContabilAction"
	name="GerarRelatorioArrecadacaoContabilActionForm" 
	type="gcom.gui.relatorio.arrecadacao.GerarRelatorioArrecadacaoContabilActionForm"  
	method="post">

<input type="hidden" id="DATA_ATUAL" value="${requestScope.dataAtual}" />
<input type="hidden" name="campoOrigem" value=""/>
<input type="hidden" name="acao" value="gerarRelatorio"/>

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

				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
					<tr>
						<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
						<td class="parabg">Filtrar Arrecadação Contábil</td>
						<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
					</tr>
				</table>
				<p>&nbsp;</p>

				<table width="100%" border="0">
					<tr>
						<td colspan="2">Para filtrar arrecadação contábil, informe os dados abaixo:</td>
					</tr>
					
					<tr>
						<td width="183" height="30"><strong>&nbsp;Arrecadador:</strong></td>
						<td width="432">
						    <html:text property="pnCDAgenteInicial" size="4" maxlength="3" tabindex="1" />
							<strong>a</strong> 
						    <html:text property="pnCDAgenteFinal" size="4" maxlength="3" tabindex="1" />
						</td>
					</tr>
				
					<tr>
						<td colspan="2" width="183" height="30">

							<table width="100%" border="0">
								<tr>
									<td height="30" width="183"><strong>Data do Crédito:</strong></td>
									<td width="432">
										<html:text property="pdInicial" size="11" maxlength="10" tabindex="2" onkeyup="mascaraData(this, event);replicar(this, document.forms[0].pdFinal)" />
										<a href="javascript:abrirCalendario('GerarRelatorioArrecadacaoContabilActionForm', 'pdInicial');">
											<img align="absmiddle" src="<bean:message key='caminho.imagens'/>calendario.gif" width="20" border="0" alt="Exibir Calendário" tabindex="7" />
										</a>
										<strong>a</strong> 
										<html:text property="pdFinal" size="11" maxlength="10" tabindex="3" onkeyup="mascaraData(this, event);" /> 
										<a href="javascript:abrirCalendario('GerarRelatorioArrecadacaoContabilActionForm', 'pdFinal')">
											<img align="absmiddle" src="<bean:message key='caminho.imagens'/>calendario.gif" width="20" border="0" alt="Exibir Calendário" tabindex="9" />
										</a>
										dd/mm/aaaa
									</td>
								</tr>
							</table>

						</td>
					</tr>
					
					<tr>
						<td height="30"><strong>&nbsp;Situação:</strong></td>
						<td>
							<html:radio property="relatorio" value="RelatorioClassificacaoContabil-Geral.rpt" tabindex="4" /><strong>Geral&nbsp;</strong>
							<html:radio property="relatorio" value="RelatorioClassificacaoContabil-DividaAtiva.rpt" tabindex="5" /><strong>Dívida Ativa&nbsp;</strong>
							<html:radio property="relatorio" value="RelatorioClassificacaoContabil-Execucao.rpt" tabindex="6" /><strong>Execução</strong>
						</td>
					</tr>
			
				
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					
					<tr>
						<td colspan="4">
							<table width="100%">
								<tr>
									<td>
										<input name="Submit22" class="bottonRightCol" value="Limpar" type="button" onclick="window.location.href='/gsan/exibirGerarRelatorioArrecadacaoContabilAction.do?menu=sim';">
									</td>
									<td align="right">
									<input name="botaoGerar" type="button"	class="bottonRightCol" value="Gerar"
										onclick="validarForm(document.forms[0]);" tabindex="10">
									</td>
								</tr>
							</table>
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
