<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

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
		form.mesAno.value = '';
		for(i = 0; i < form.psTipoRelatorio.length; i++){
			form.psTipoRelatorio[i].checked = false;
		}
		form.psTipoRelatorio[0].checked = true;
		for(i = 0; i < form.psRelatorioTipo.length; i++){
			form.psRelatorioTipo[i].checked = false;
		}
		form.psRelatorioTipo[0].checked = true;
	}
}

function limparCampos() {
	var form = document.forms[0];
	form.mesAno.value = '';	
	for(i = 0; i < form.psTipoRelatorio.length; i++){
		form.psTipoRelatorio[i].checked = false;
	}
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

function validaRadiosESubordinados(radio, nomeCampo, form) {
	var valor = '';
	var retorno = true;
	<c:if test="${indicadorMostraTotalizadores eq '1'}">
	for(i=0; i < radio.length; i++) {
		if (radio[i].checked) {
			valor = radio[i].value;	
		}
	}
	if(valor == '') {
		alert('Selecione uma : '+nomeCampo);			
		return false;
	}		
	</c:if>
	return retorno;
}


function validarForm(){
	var form = document.GerarRelatorioConciliacaoBancariaDynaForm; 

	if(validaCamposVazios(form.mesAno, '<%=session.getAttribute("descricaoAMReferencia")%>') && validaRadiosESubordinados(form.psTipoRelatorio, 'Opção de Totalização', form)) {
		
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

<html:form action="/gerarRelatorioConciliacaoAction.do"
	name="GerarRelatorioConciliacaoBancariaDynaForm"
	type="org.apache.struts.action.DynaActionForm"
	method="post">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>
	
	<input type="hidden" name="acao" value="gerarRelatorio"/>
	<input type="hidden" name="relatorio" value="${requestScope.relatorio}"/>
	<input type="hidden" name="limparTela" value="${requestScope.limparTela}"/>
	<input type="hidden" name="relatorioTipoReloadPage" value="${requestScope.relatorioTipoReloadPage}"/>
	<%--  ehRelatorioBatch indica se o Relatrio será processado como uma Rotina Batch  --%>
	<%--  1 - Indica que SIM, é uma Rotina Batch                                       --%>
	<%--  2 - Indica que NAO, não é uma Rotina Batch e o Relatório será gerado online  --%>
	<input type="hidden" name="ehRelatorioBatch" value="${requestScope.ehRelatorioBatch}"/>
	
	
	
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
					<td class="parabg">${requestScope.tituloRelatorio}</td>

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
					<p>Para gerar o relat&oacute;rio, informe os dados abaixo:</p>
					<p>&nbsp;</p>
					</td>
				</tr>
				<tr>
					<td width="26%">
						<strong><%=session.getAttribute("descricaoAMReferencia") %>:<font color="#FF0000">*</font></strong>
					</td>
					<td colspan="2">
						<html:text property="mesAno" size="7" maxlength="7" 
						onkeyup="javascript:mascaraAnoMes(this, event);" 
						onblur="javascript:verificaAnoMes(this);"/>
						<strong>&nbsp; </strong>mm/aaaa						
					</td>
				</tr>
				<logic:equal name="indicadorMostraTotalizadores" value="<%=ConstantesSistema.ATIVO%>">
				<tr>
					<td>
						<strong>Op&ccedil;&atilde;o de Totaliza&ccedil;&atilde;o:<font color="#FF0000">*</font></strong>
					</td>
					
					<logic:equal name="indicadorMostraTotalizadorEstado" value="<%=ConstantesSistema.ATIVO%>">
					
						<td colspan="2" align="left">
							<html:radio property="psTipoRelatorio" value="E">
								<strong>Estado</strong>
							</html:radio>
						</td>
					</logic:equal>
				</tr>
				<logic:equal name="indicadorMostraTotalizadorEstadoGerencia" value="<%=ConstantesSistema.ATIVO%>">
					<tr>
						<td>&nbsp;</td>
						<td colspan="2" align="left">						
							<html:radio property="psTipoRelatorio" value="EG">
								<strong>Estado por Ger&ecirc;ncia Regional</strong>
							</html:radio>
						</td>
					</tr>
				</logic:equal>
				<logic:equal name="indicadorMostraTotalizadorEstadoLocalidade" value="<%=ConstantesSistema.ATIVO%>">
					<tr>
						<td>&nbsp;</td>
						<td colspan="2" align="left">						
							<html:radio property="psTipoRelatorio" value="EL">
								<strong>Estado por Localidade</strong>
							</html:radio>
						</td>
					</tr>
				</logic:equal>
					<tr>
						<td>
						<strong>Tipo do Relatório:<font color="#FF0000">*</font></strong>
						</td>
						<td colspan="1" align="left">
							
							<html:radio property="psRelatorioTipo" value="3">
								<strong>Receita</strong>
							</html:radio>					
							<html:radio property="psRelatorioTipo" value="1.1.2.1">
								<strong>Contas a Receber</strong>
							</html:radio>
						</td>
					</tr>
				<tr>
					<td>&nbsp;</td>
					<td colspan="2" align="left"><font color="#FF0000">*</font> Campo
					Obrigat&oacute;rio</td>
				</tr>	
				</logic:equal>
			</table>
			<table width="100%" border="0">
				<tr>
					<td valign="top"><input name="button" type="button"
						class="bottonRightCol" value="Desfazer" onclick="limparCampos();">&nbsp;<input type="button"
						name="ButtonCancelar" class="bottonRightCol" value="Cancelar"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"></td>
					<td valign="top">
					  <div align="right">
					  
					  <input name="botaoGerar" type="button" 
					  class="bottonRightCol" value="Gerar Relatório" 
					  onclick="javascript:validarForm();" />
					  <%-- <gcom:controleAcessoBotao name="botaoGerar" value="Gerar" onclick="javascript:gerar();" url="gerarMovimentoDebitoAutomaticoBancoAction.do" tabindex="7"/> --%>
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
