<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>
<%@ page import="gcom.util.ConstantesSistema" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="SelecionarPagamentosAvisoBancarioActionForm" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">
<!--
var aviso = 0;

function repetirDataDevolucao() {
	var form = document.SelecionarPagamentosAvisoBancarioActionForm;
	form.dataDevolucaoFinal.value = form.dataDevolucaoInicial.value;
}

function repetirDataPagamento() {
	var form = document.SelecionarPagamentosAvisoBancarioActionForm;
	form.dataPagamentoFinal.value = form.dataPagamentoInicial.value;
}
	
function setAvisoBancario(tipo) {
  aviso = tipo;
}

function recuperarDadosCincoParametros(codigoRegistro, descricaoRegistro1, descricaoRegistro2, descricaoRegistro3, tipoConsulta) { 
	var form = document.SelecionarPagamentosAvisoBancarioActionForm;
 	if (tipoConsulta == 'avisoBancario') {
		  if (aviso == 1) {
			form.codigoAgenteArrecadadorO.value = descricaoRegistro1;
			form.dataLancamentoAvisoO.value = descricaoRegistro2;
			form.numeroSequencialAvisoO.value = descricaoRegistro3;
			form.avisoBancarioO.value = codigoRegistro;
	      } else if (aviso == 2) {
			form.codigoAgenteArrecadadorD.value = descricaoRegistro1;
			form.dataLancamentoAvisoD.value = descricaoRegistro2;
			form.numeroSequencialAvisoD.value = descricaoRegistro3;
			form.avisoBancarioD.value = codigoRegistro;	      
	      }
	      aviso = 0;
	}
}

function limparAvisoBancarioOrigem() {
	var form = document.SelecionarPagamentosAvisoBancarioActionForm;
		form.codigoAgenteArrecadadorO.value = "";
		form.dataLancamentoAvisoO.value = "";
		form.numeroSequencialAvisoO.value = "";
		form.avisoBancarioO.value = "";
}

function limparAvisoBancarioDestino() {
	var form = document.SelecionarPagamentosAvisoBancarioActionForm;
		form.codigoAgenteArrecadadorD.value = "";
		form.dataLancamentoAvisoD.value = "";
		form.numeroSequencialAvisoD.value = "";
		form.avisoBancarioD.value = "";
}
 
function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){
	if(objetoRelacionado.disabled != true){
		if (objeto == null || codigoObjeto == null){
			abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
		}
		else{
			if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
				alert(msg);
			}
			else{
				abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
			}
		}
	}
}

	
function validarForm(form){ 

  if(validateSelecionarPagamentosAvisoBancarioActionForm(form)){
	  if((form.dataDevolucaoInicial.value == "" || form.dataDevolucaoFinal.value == "") && (form.dataPagamentoInicial.value == "" || form.dataPagamentoFinal.value == "")
			  && (form.idArrecadacaoForma.value == "" || form.idArrecadacaoForma.value == "-1")){
		    alert("Informe pelo menos um critério de seleção: Período de Devolução ou Período de Pagamento ou Forma de Arrecadação");
	  } else if (form.codigoAgenteArrecadadorO.value == form.codigoAgenteArrecadadorD.value &&
			    form.dataLancamentoAvisoO.value == form.dataLancamentoAvisoD.value &&
			    form.numeroSequencialAvisoO.value == form.numeroSequencialAvisoD.value) {
				alert('Aviso Bancário de Origem tem que ser diferente do Aviso Bancário de Destino');
				return;
	  }
	form.submit();
  } 
}


function selecionaRadioDevolucao() {
	var form  = document.forms[0];
	var i = 0;
	for(i = 0; i < form.idSituacaoDevolucao.length; i++) {
		if (form.idSituacaoDevolucao[i].checked == true) {
			return;
		}
	}
	for(i = 0; i < form.idSituacaoDevolucao.length; i++) {
		if (form.idSituacaoDevolucao[i].value == '1') {
			form.idSituacaoDevolucao[i].checked = true;
		}
	}
}

function selecionaRadioPagamento() {
	var form  = document.forms[0];
	var i = 0;
	for(i = 0; i < form.idSituacaoPagamento.length; i++) {
		if (form.idSituacaoPagamento[i].checked == true) {
			return;
		}
	}
	for(i = 0; i < form.idSituacaoPagamento.length; i++) {
		if (form.idSituacaoPagamento[i].value == '1') {
			form.idSituacaoPagamento[i].checked = true;
		}
	}
}

//-->
</script>
</head>
<body leftmargin="5" topmargin="5" onload="javascript:setarFoco('${requestScope.nomeCampo}');selecionaRadioDevolucao();selecionaRadioPagamento();">
<html:form action="/selecionarPagamentosAvisoBancarioAction.do" name="SelecionarPagamentosAvisoBancarioActionForm" 
	type="gcom.gui.arrecadacao.aviso.SelecionarPagamentosAvisoBancarioActionForm" method="post"
	onsubmit="return validateSelecionarPagamentosAvisoBancarioActionForm(this);">	
	
	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="755" border="0" cellspacing="5" cellpadding="0">
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

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Movimentar Pagamentos/Devoluções entre Avisos Bancários</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td>Para selecionar os avisos bancários, informe os dados abaixo:</td>
					<td colspan="4">&nbsp;</td>
				</tr>
			</table>

			<table width="100%" border="0">
				<tr>
					<td ><strong>Aviso Banc&aacute;rio Origem:<font color="#FF0000">*</font></strong>
					</td>
					<td width="390" colspan="3"><html:text property="codigoAgenteArrecadadorO"
						size="3" maxlength="3" readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000" /> <html:text
						property="dataLancamentoAvisoO" size="10" maxlength="10"
						readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000" /> <html:text
						property="numeroSequencialAvisoO" size="3" maxlength="3"
						readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000" /> <html:hidden
						property="avisoBancarioO" /> 
						<a href="javascript:setAvisoBancario(1); abrirPopup('exibirPesquisarAvisoBancarioAction.do?menu=sim', 475, 765);">
							<img width="23" height="21"	src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" title="Pesquisar Aviso Bancário Origem"/></a>
						<a href="javascript:limparAvisoBancarioOrigem();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Limpar Aviso Bancário Origem" /></a></td>
				</tr>
				<tr>
					<td ><strong>Aviso Banc&aacute;rio Destino:<font color="#FF0000">*</font></strong>
					</td>
					<td width="390" colspan="3"><html:text property="codigoAgenteArrecadadorD"
						size="3" maxlength="3" readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000" /> <html:text
						property="dataLancamentoAvisoD" size="10" maxlength="10"
						readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000" /> <html:text
						property="numeroSequencialAvisoD" size="3" maxlength="3"
						readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000" /> <html:hidden
						property="avisoBancarioD" /> 
						<a href="javascript:setAvisoBancario(2); abrirPopup('exibirPesquisarAvisoBancarioAction.do?menu=sim', 475, 765);">
							<img width="23" height="21"	src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" title="Pesquisar Aviso Bancário Destino"/></a>
						<a href="javascript:limparAvisoBancarioDestino();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Limpar Aviso Bancário Destino" /></a></td>
				</tr>
				<tr>
					<td colspan="4">
					<hr>
					</td>
				</tr>
				<tr>
					<td colspan="4">Para selecionar os pagamentos/devoluções a serem transferidos, informe os dados abaixo:</td>
				</tr>
				<tr>
					<td colspan="1"><strong>Período de Devolução:</strong></td>								
					<td colspan="3">
					<strong>
					<html:text tabindex="8" property="dataDevolucaoInicial" maxlength="10"
						size="10" onkeyup="mascaraData(this, event);repetirDataDevolucao();" />
						<a onfocus="repetirDataDevolucao();"
						href="javascript:abrirCalendario('SelecionarPagamentosAvisoBancarioActionForm', 'dataDevolucaoInicial')">
					<img border="0"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>					
					&nbsp;a&nbsp;</strong>
					<html:text tabindex="8" property="dataDevolucaoFinal" maxlength="10"
						size="10" onkeyup="mascaraData(this, event);" /> <a
						href="javascript:abrirCalendario('SelecionarPagamentosAvisoBancarioActionForm', 'dataDevolucaoFinal')">
					<img border="0"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>&nbsp;dd/mm/aaaa
					</td>
				</tr>
				<tr>
					<td colspan="1"><strong>Situação de Devolução:</strong></td>	
					<td colspan="3">
						<html:radio property="idSituacaoDevolucao" value="0" ><strong>Contabilizadas</strong></html:radio>
						<html:radio property="idSituacaoDevolucao" value="1" ><strong>Não Contabilizadas</strong></html:radio>
						<html:radio property="idSituacaoDevolucao" value="2" ><strong>Todas</strong></html:radio>
					</td>
				</tr>
				<tr>
					<td colspan="1"><strong>Período de Pagamento:</strong></td>							
					<td colspan="3">
						<strong>
						<html:text tabindex="8" property="dataPagamentoInicial" maxlength="10"
						size="10" onkeyup="mascaraData(this, event);repetirDataPagamento();" /> <a
						onfocus="repetirDataPagamento();"
						href="javascript:abrirCalendario('SelecionarPagamentosAvisoBancarioActionForm', 'dataPagamentoInicial')">
					<img border="0"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
					&nbsp;a&nbsp;</strong>
					
					<html:text tabindex="8" property="dataPagamentoFinal" maxlength="10"
						size="10" onkeyup="mascaraData(this, event);" /> <a
						href="javascript:abrirCalendario('SelecionarPagamentosAvisoBancarioActionForm', 'dataPagamentoFinal')">
					<img border="0"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>&nbsp;dd/mm/aaaa
					</td>
				</tr>
				<tr>
					<td colspan="1"><strong>Situação de Pagamento:</strong></td>	
					<td colspan="4">
						<html:radio property="idSituacaoPagamento" value="0" ><strong>Contabilizadas</strong></html:radio>
						<html:radio property="idSituacaoPagamento" value="1" ><strong>Não Contabilizadas</strong></html:radio>
						<html:radio property="idSituacaoPagamento" value="2" ><strong>Todas</strong></html:radio>
					</td>
				</tr>
			    <tr> 
                  <td><strong>Forma de Arrecadação:</strong></td>
                  <td colspan="3"><span class="style2"><strong> 
					<html:select multiple="true" property="idArrecadacaoForma" style="width: 420px;">
						<html:options collection="colecaoArrecadacaoForma" labelProperty="descricao" property="id" />
					</html:select>
                    </strong><strong> </strong></span></td>
                </tr>
                <tr> 
                  <td><strong>Tipo de Documento dos Pagamentos:</strong></td>
                  <td colspan="3"><span class="style2"><strong> 
					<html:select multiple="true" property="idTipoDocumentoPagamento" style="width: 420px;">
						<html:options collection="colecaoDocumentoTipo" labelProperty="descricaoDocumentoTipo" property="id" />
					</html:select>
                    </strong><strong> </strong></span></td>
                </tr>
				<tr>
					<td colspan="4"></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td colspan="3"><strong><font color="#FF0000">*</font></strong> Campos obrigat&oacute;rios</td>
					<td>&nbsp;</td>
				</tr>
			</table>
			<table border="0" width="100%">
				<tr>
					<td colspan="2">
					<input tabindex="9" name="Button" type="button" class="bottonRightCol"
						value="Desfazer" align="left"
						onclick="window.location.href='<html:rewrite page="/exibirSelecionarPagamentosAvisoBancarioAction.do?menu=sim"/>'">
					<input tabindex="10" name="Button" type="button" class="bottonRightCol" value="Cancelar" align="left"
						onclick="window.location.href='/gsan/telaPrincipal.do'"></td>
					<td colspan="2" align="right">
					<%-- <gcom:controleAcessoBotao name="Button" value="Selecionar" onclick="javascript:validarForm(document.forms[0]);" url="inserirDevolucoesAction.do"/> --%>
						 <input tabindex="11" type="button" name="Button" class="bottonRightCol" value="Selecionar" onClick="javascript:validarForm(document.forms[0]);" />
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
