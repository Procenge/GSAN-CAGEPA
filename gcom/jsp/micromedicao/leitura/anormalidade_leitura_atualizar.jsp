<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

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
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="AtualizarAnormalidadeLeituraActionForm" />

<SCRIPT LANGUAGE="JavaScript">
<!--

	function validarForm(form){
				
		if(testarCampoValorZero(document.AtualizarAnormalidadeLeituraActionForm.descricao, 'Descrição') 
		&& testarCampoValorZero(document.AtualizarAnormalidadeLeituraActionForm.abreviatura, 'Descrição Abreviada')
		&& validateCamposMensagem()) { 		
			if(validateAtualizarAnormalidadeLeituraActionForm(form)){			
   				submeterFormPadrao(form);			
			}
			
	}
	}


	function validateCamposMensagem(){
 		var form = document.forms[0];
 		
 		// incluído novamente por bug no Validator.xml
 		if (form.indicadorRelativoHidrometro.value == '') {
 		  alert('Informe Anormalidade Relativa a Hidrômetro.');
 		  return false;
 		}
 		
 		if (form.osAutomatico.value == '') {
 		  alert('Informe Anormalidade Emite OS Automática.');
 		  return false;
 		}
 		
 		if (form.mensagemImpressaoConta.value.length > 200){
	     alert('Mensagem para Impressão em Conta maior que 200 caracteres.');
	     form.mensagemImpressaoConta.focus();
	     return false;
	    }
	    if (form.sugestaoAgenteManutencao.value.length > 200){
	     alert('Mensagem para Manutenção maior que 200 caracteres.');
	     form.sugestaoAgenteManutencao.focus();
	     return false;
	    }
	    if (form.sugestaoAgentePrevencao.value.length > 200){
	     alert('Mensagem para Prevenção de Acidentes maior que 200 caracteres.');
	     form.sugestaoAgentePrevencao.focus();
	     return false;
	    }
	    return true;
 	}


	function desabiltaCombo(){
	var form = document.forms[0];
	if(form.osAutomatico[1].checked){
	form.tipoServico.value="-1";
	form.tipoServico.disabled = true;
	
	form.numeroIncidenciasGeracaoOS.value = "";
	form.numeroIncidenciasGeracaoOS.disabled = true;
	}else{ 
		if (form.osAutomatico[0].checked){
			form.tipoServico.disabled = false;
		
			form.numeroIncidenciasGeracaoOS.disabled = false;
		}
	}
	
}

		
	function limparForm() {
		var form = document.AtualizarAnormalidadeLeituraActionForm;
		form.descricao.value = "";
		form.abreviatura.value = "";
	    form.indicadorRelativoHidrometro.value = "";
	    form.indicadorImovelSemHidrometro.value = "";
		form.usoRestritoSistema.value = "";
	    form.perdaTarifaSocial.value = "";
	    form.osAutomatico.value = "";
		form.tipoServico.value = "";
	    form.consumoLeituraNaoInformado.value = "";
	    form.consumoLeituraInformado.value = "";
		form.leituraLeituraNaoturaInformado.value = "";
	    form.leituraLeituraInformado.value = "";
		form.tipoDocumento.value = "";
		form.numeroRepeticoesGeracaoOS.value = "";
		form.aceiteLeitura.value = "";
		form.impressaoRelatorioCriticaFiscalizacao.value = "";
		form.indicadorRetencaoConta.value = "";
		form.indicadorIsencaoCobrancaAgua.value = "";
		form.indicadorIsencaoCobrancaEsgoto.value = "";
		form.indicadorConcessaoCreditoConsumo.value = "";
		form.mensagemImpressaoConta.value = "";
		form.sugestaoAgenteManutencao.value = "";
		form.sugestaoAgentePrevencao.value = "";	
	}
	
		function reload() {
		var form = document.AtualizarAnormalidadeLeituraActionForm;
		form.action = "/gsan/exibirAtualizarAnormalidadeLeituraAction.do";
		form.submit();
	} 

function redirecionarSubmitAtualizar(idLocalidade) {
	urlRedirect = "/gsan/exibirAtualizarAnormalidadeLeituraAction.do?idRegistroInseridoAtualizar=" + idLocalidade;
	redirecionarSubmit(urlRedirect);
}

function desfazer(){
 (document.forms[0]).reset();
 desabiltaCombo();
}

//-->
</SCRIPT>

</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:desabiltaCombo();">

<html:form action="/atualizarAnormalidadeLeituraAction" method="post">

	<INPUT TYPE="hidden" name="removerEndereco">
	<INPUT TYPE="hidden" name="limparCampos" id="limparCampos">


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

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Atualizar Anormalidade de Leitura</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para Atualizar uma Anormalidade de Leitura, informe
					os dados abaixo:</td>
					<!-- Descricao -->
				<tr>
					<td><strong>Descri&ccedil;&atilde;o:<font color="#FF0000">*</font></strong></td>
					<td colspan="2"><span class="style2"> <html:text
						property="descricao" size="25" maxlength="25" /> </span></td>
				</tr>

				<!-- Abreviatura -->

				<tr>
					<td><strong>Abreviatura:</strong></td>
					<td colspan="2"><span class="style2"> <html:text
						property="abreviatura" size="5" maxlength="5" /> </span></td>
				</tr>
				
				<tr>
					<td><strong>Mensagem a ser Impressa em Conta:</strong></td>
					<td colspan="2" align="left"><html:textarea rows="3"   property="mensagemImpressaoConta" tabindex="3" style=" width : 192px;"/>
					</td>
				</tr>
				<tr>
					<td><strong>Mensagem para Manutenção:</strong></td>
					<td colspan="2" align="left"><html:textarea rows="3"   property="sugestaoAgenteManutencao"  tabindex="4" style=" width : 192px;"/>
					</td>
				</tr>
				<tr>
					<td><strong>Mensagem para Prevenção de Acidentes:</strong></td>
					<td colspan="2" align="left"><html:textarea rows="3"   property="sugestaoAgentePrevencao" tabindex="5" style=" width : 192px;"/>
					</td>
				</tr>

				<tr>
					<td><strong>Anormalidade Relativa a Hidrômetro:<font
						color="#FF0000">*</font></strong></td>
					<td><strong> <html:radio property="indicadorRelativoHidrometro"
						value="1" /> <strong>Sim <html:radio
						property="indicadorRelativoHidrometro" value="2" /> N&atilde;o</strong>
					</strong></td>

				</tr>
				<tr>
					<td><strong>Anormalidade Aceita para Ligação sem Hidrômetro:</strong></td>
					<td><strong> <html:radio property="indicadorImovelSemHidrometro"
						value="1" /> <strong>Sim <html:radio
						property="indicadorImovelSemHidrometro" value="2" /> N&atilde;o</strong>
					</strong></td>

				</tr>
				<tr>
					<td><strong>Anormalidade de Uso Restrito do Sistema:</strong></td>
					<td><strong> <html:radio property="usoRestritoSistema" value="1" />
					<strong>Sim <html:radio property="usoRestritoSistema" value="2" />
					N&atilde;o</strong> </strong></td>

				</tr>
				<tr>
					<td><strong>Anormalidade Acarreta Perda Tarifa Social:</strong></td>
					<td><strong> <html:radio property="perdaTarifaSocial" value="1" />
					<strong>Sim <html:radio property="perdaTarifaSocial" value="2" />
					N&atilde;o</strong> </strong></td>
				</tr>
				<tr>
					<td><strong>Aceite de Leitura:</strong></td>
					<td><strong> <html:radio property="aceiteLeitura"
						value="1" tabindex="14" /> <strong>Sim <html:radio
						property="aceiteLeitura" value="2" tabindex="15" /> N&atilde;o</strong>
					</strong></td>

				</tr>
				<tr>
					<td><strong>Impressão em Relatório de Crítica/Fiscalização:</strong></td>
					<td><strong> <html:radio property="impressaoRelatorioCriticaFiscalizacao"
						value="1" tabindex="16" /> <strong>Sim <html:radio
						property="impressaoRelatorioCriticaFiscalizacao" value="2" tabindex="17" /> N&atilde;o</strong>
					</strong></td>

				</tr>
				<tr>
					<td><strong>Retenção de Conta:</strong></td>
					<td><strong> <html:radio property="indicadorRetencaoConta"
						value="1" tabindex="18" /> <strong>Sim <html:radio
						property="indicadorRetencaoConta" value="2" tabindex="19" /> N&atilde;o</strong>
					</strong></td>

				</tr>
				<tr>
					<td><strong>Isenção para Cobrança de Água:</strong></td>
					<td><strong> <html:radio property="indicadorIsencaoCobrancaAgua"
						value="1" tabindex="20" /> <strong>Sim <html:radio
						property="indicadorIsencaoCobrancaAgua" value="2" tabindex="21" /> N&atilde;o</strong>
					</strong></td>
				</tr>
				<tr>
					<td><strong>Isenção para Cobrança de Esgoto:</strong></td>
					<td><strong> <html:radio property="indicadorIsencaoCobrancaEsgoto"
						value="1" tabindex="22" /> <strong>Sim <html:radio
						property="indicadorIsencaoCobrancaEsgoto" value="2" tabindex="23" /> N&atilde;o</strong>
					</strong></td>
				</tr>
				<tr>
					<td><strong>Concessão de Crédito de Consumo:</strong></td>
					<td><strong> <html:radio property="indicadorConcessaoCreditoConsumo"
						value="1" tabindex="24" /> <strong>Sim <html:radio
						property="indicadorConcessaoCreditoConsumo" value="2" tabindex="25" /> N&atilde;o</strong>
					</strong></td>
				</tr>
				<tr>
					<td><strong>Anormalidade Emite OS Automática:<font color="#FF0000">*</font></strong></td>
					<td><strong> <html:radio property="osAutomatico" value="1"
						onchange="javascript:desabiltaCombo();" /> <strong>Sim <html:radio
						property="osAutomatico" value="2"
						onchange="javascript:desabiltaCombo();" /> N&atilde;o</strong> </strong></td>

				</tr>

				<!-- Tipo de Serviço -->

				<tr>
					<td><strong>Tipo de Serviço:</strong></td>
					<td colspan="2" align="left"><html:select property="tipoServico">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoTipoServico"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>
				
				<tr>
					<td><strong>Incidências para Geração de OS:</strong></td>
					<td colspan="2"><span class="style2"> <html:text
						property="numeroIncidenciasGeracaoOS" size="5" maxlength="2" tabindex="29" /> </span></td>
				</tr>
				<tr>
					<td><strong>Tipo do Documento:</strong></td>
					<td colspan="2" align="left"><html:select property="tipoDocumento" tabindex="30">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoDocumentoTipo"
							labelProperty="descricaoDocumentoTipo" property="id" />
					</html:select></td>
				</tr>
				<!--Consumo a Ser Cobrado (anormalidade informada e leitura não informada)-->

				<tr>
					<td><strong>Consumo a Ser Cobrado (anormalidade informada e leitura
					não informada):<font color="#FF0000">*</font></strong></td>
					<td colspan="2" align="left"><html:select
						property="consumoLeituraNaoInformado">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoLeituraAnormalidadeConsumo"
							labelProperty="descricaoConsumoACobrar" property="id" />
					</html:select></td>
				</tr>

				<tr>
					<td><strong>Consumo a Ser Cobrado (anormalidade informada e leitura
					informada):<font color="#FF0000">*</font></strong></td>
					<td colspan="2" align="left"><html:select
						property="consumoLeituraInformado">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoLeituraAnormalidadeConsumo"
							labelProperty="descricaoConsumoACobrar" property="id" />
					</html:select></td>
				</tr>

				<tr>
					<td><strong>Leitura para faturamento (anormalidade informada e
					leitura não informada):<font color="#FF0000">*</font></strong></td>
					<td colspan="2" align="left"><html:select
						property="leituraLeituraNaoturaInformado">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoLeituraAnormalidadeLeitura"
							labelProperty="descricaoFaturamento" property="id" />
					</html:select></td>
				</tr>

				<tr>
					<td><strong>Leitura para faturamento (anormalidade informada e
					leitura informada):<font color="#FF0000">*</font></strong></td>
					<td colspan="2" align="left"><html:select
						property="leituraLeituraInformado">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoLeituraAnormalidadeLeitura"
							labelProperty="descricaoFaturamento" property="id" />
					</html:select></td>
				</tr>
				
				<tr>
					<td><strong>Indicador de uso:<font color="#FF0000">*</font></strong></td>
						<td><html:radio property="indicadorUso" value="1" tabindex="5"/><strong>Ativo
							<html:radio property="indicadorUso" value="2" tabindex="6"/>Inativo</strong> 
						</td>
				</tr>				
				
				<tr>
					<td><strong> <font color="#FF0000"></font></strong></td>
					<td align="right">
					<div align="left"><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios</div>
					</td>
				</tr>

				<tr>
					<td colspan="2"><font color="#FF0000"> <logic:present
						name="inserir" scope="request">
						<input type="button" name="ButtonReset" class="bottonRightCol"
							value="Voltar"
							onClick="javascript:window.location.href='/gsan/exibirFiltrarAnormalidadeLeitura.do?menu=sim'">
					</logic:present><logic:notPresent name="inserir" scope="request">
						<input type="button" name="ButtonReset" class="bottonRightCol"
							value="Voltar" onClick="javascript:history.back();">
					</logic:notPresent><input type="button" name="ButtonReset"
						class="bottonRightCol" value="Desfazer"
						onClick="javascript:desfazer();"> <input type="button"
						name="ButtonCancelar" class="bottonRightCol" value="Cancelar"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
					</font></td>
					<td align="right">
					  <gcom:controleAcessoBotao name="Button" value="Atualizar" onclick="javascript:validarForm(document.forms[0]);" url="atualizarAnormalidadeLeituraAction.do"/>
					  <%-- <input type="button" name="Button" class="bottonRightCol" value="Atualizar" onClick="javascript:validarForm(document.forms[0]);" /> --%>
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

