<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="gcom.util.ConstantesSistema" isELIgnored="false"%>
<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="FiltrarAnormalidadeLeituraActionForm"
	dynamicJavascript="true" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">

function validaForm(form){

	if(testarCampoValorZero(document.FiltrarAnormalidadeLeituraActionForm.descricao, 'Descrição') &&
	testarCampoValorZero(document.FiltrarAnormalidadeLeituraActionForm.tipoServico, 'Tipo de Serviço')) {

		if(validateFiltrarAnormalidadeLeituraActionForm(form)){

    		submeterFormPadrao(form);
		}
	}

function limparForm(form) {
		form.descricao.value = "";
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
	}
}

    function verificarChecado(valor){
		form = document.forms[0];
		if(valor == "1"){
		 	form.indicadorAtualizar.checked = true;
		 }else{
		 	form.indicadorAtualizar.checked = false;
		}
	}

		function desabiltaCombo(){
	var form = document.forms[0];
	if(form.osAutomatico[1].checked){
	form.tipoServico.value="-1";
	form.tipoServico.disabled = true;
	
	form.numeroIncidenciasGeracaoOS.value = "";
	form.numeroIncidenciasGeracaoOS.disabled = true;
	}else{ if (form.osAutomatico[0].checked || form.osAutomatico[2].checked){
	form.tipoServico.value="-1";
	form.tipoServico.disabled = false;
	
	form.numeroIncidenciasGeracaoOS.disabled = false;
	}
}
	
	} 

</script>


</head>

<body leftmargin="5" topmargin="5"
	onload="verificarChecado('${sessionScope.indicadorAtualizar}'); setaFocus();">
<html:form action="/filtrarAnormalidadeLeituraAction"
	name="FiltrarAnormalidadeLeituraActionForm"
	type="gcom.gui.micromedicao.leitura.FiltrarAnormalidadeLeituraActionForm"
	method="post"
	onsubmit="return validateFiltrarAnormalidadeLeituraActionForm(this);">

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
			<td width="615" valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>


			<!--Início Tabela Reference a Páginação da Tela de Processo-->
			<table>
				<tr>
					<td></td>

				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">Filtrar Anormalidade de Leitura</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>
				</tr>

			</table>
			<!--Fim Tabela Reference a Páginação da Tela de Processo-->
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td height="10" colspan="2">Para filtrar uma Anormalidade de
					Leitura, informe os dados abaixo:</td>
					<td width="80" align="right"><html:checkbox property="indicadorAtualizar" value="1" /><strong>Atualizar</strong></td>
				<tr>
					<td><strong>Descri&ccedil;&atilde;o:</strong></td>
					<td colspan="2"><span class="style2"> <html:text
						property="descricao" size="25" maxlength="25" tabindex="1" /> </span></td>
				</tr>
				
				<tr>
					<td>&nbsp;</td>
					<td><html:radio property="tipoPesquisa"	tabindex="2"
						value="<%=ConstantesSistema.TIPO_PESQUISA_INICIAL.toString()%>" />
						Iniciando pelo texto
						<html:radio	property="tipoPesquisa" tabindex="3"
						value="<%=ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString()%>" />
						Contendo o texto
					</td>
				</tr>				
				
				<tr>
					<td><strong>Mensagem a ser Impressa em Conta:</strong></td>
					<td><html:radio property="mensagemImpressaoConta" tabindex="4" value="1"/><strong>Possui</strong>
						<html:radio	property="mensagemImpressaoConta" tabindex="5" value="2"/><strong>Não Possui</strong>
						<html:radio	property="mensagemImpressaoConta" tabindex="6" value="3"/><strong>Todos</strong>
					</td>
				</tr>
				
				<tr>
					<td><strong>Mensagem de Manutenção:</strong></td>
					<td><html:radio property="sugestaoAgenteManutencao" tabindex="7" value="1"/><strong>Possui</strong>
						<html:radio	property="sugestaoAgenteManutencao" tabindex="8" value="2"/><strong>Não Possui</strong>
						<html:radio	property="sugestaoAgenteManutencao" tabindex="9" value="3"/><strong>Todos</strong>
					</td>
				</tr>
				
				<tr>
					<td><strong>Mensagem de Prevenção Acidentes:</strong></td>
					<td><html:radio property="sugestaoAgentePrevencao" tabindex="10" value="1"/><strong>Possui</strong>
						<html:radio	property="sugestaoAgentePrevencao" tabindex="11" value="2"/><strong>Não Possui</strong>
						<html:radio	property="sugestaoAgentePrevencao" tabindex="12" value="3"/><strong>Todos</strong>
					</td>
				</tr>
				<!-- Abreviatura -->

				<tr>
					<td><strong>Anormalidade Relativa a Hidrômetro:</strong></td>
					<td><strong> <html:radio property="indicadorRelativoHidrometro"
						value="1" tabindex="13" /> <strong>Sim <html:radio
						property="indicadorRelativoHidrometro" value="2" tabindex="14" /> N&atilde;o</strong>
						<strong><html:radio property="indicadorRelativoHidrometro"
										value="<%=ConstantesSistema.TODOS.toString()%>" /> Todos</strong>
					</strong></td>

				</tr>
				<tr>
					<td><strong>Anormalidade Aceita para Ligação sem Hidrômetro:</strong></td>
					<td><strong> <html:radio property="indicadorImovelSemHidrometro"
						value="1" tabindex="15" /> <strong>Sim <html:radio
						property="indicadorImovelSemHidrometro" value="2" tabindex="16" /> N&atilde;o</strong>
						<strong><html:radio property="indicadorImovelSemHidrometro"
										value="<%=ConstantesSistema.TODOS.toString()%>" /> Todos</strong>
					</strong></td>

				</tr>
				<tr>
					<td><strong>Anormalidade de Uso Restrito do Sistema:</strong></td>
					<td><strong> <html:radio property="usoRestritoSistema" value="1" tabindex="17" />
					<strong>Sim <html:radio property="usoRestritoSistema" value="2" tabindex="18" />
					N&atilde;o</strong> 
					<strong><html:radio property="usoRestritoSistema"
										value="<%=ConstantesSistema.TODOS.toString()%>" /> Todos</strong></strong></td>

				</tr>
				<tr>
					<td><strong>Anormalidade Acarreta Perda Tarifa Social:</strong></td>
					<td><strong> <html:radio property="perdaTarifaSocial" value="1" tabindex="19" />
					<strong>Sim <html:radio property="perdaTarifaSocial" value="2" tabindex="20" />
					N&atilde;o</strong> 
					<strong><html:radio property="perdaTarifaSocial"
										value="<%=ConstantesSistema.TODOS.toString()%>" /> Todos</strong></strong></td>

				</tr>
				<tr>
					<td><strong>Aceite de Leitura:</strong></td>
					<td><strong> <html:radio property="aceiteLeitura" value="1" tabindex="21"/>
					<strong>Sim <html:radio property="aceiteLeitura" value="2"  tabindex="22"/>
					N&atilde;o</strong> 
					<strong><html:radio property="aceiteLeitura"
										value="<%=ConstantesSistema.TODOS.toString()%>" /> Todos</strong></strong></td>

				</tr>
				<tr>
					<td><strong>Impressão Relatório de Crítica/Fiscalização:</strong></td>
					<td><strong> <html:radio property="impressaoRelatorioCriticaFiscalizacao" value="1" tabindex="23" />
					<strong>Sim <html:radio property="impressaoRelatorioCriticaFiscalizacao" value="2" tabindex="24" />
					N&atilde;o</strong> 
					<strong><html:radio property="impressaoRelatorioCriticaFiscalizacao"
										value="<%=ConstantesSistema.TODOS.toString()%>" /> Todos </strong></strong></td>

				</tr>
				<tr>
					<td><strong>Retenção de Conta:</strong></td>
					<td><strong> <html:radio property="indicadorRetencaoConta" value="1" tabindex="25" />
					<strong>Sim <html:radio property="indicadorRetencaoConta" value="2" tabindex="26" />
					N&atilde;o</strong> 
					<strong><html:radio property="indicadorRetencaoConta"
										value="<%=ConstantesSistema.TODOS.toString()%>" /> Todos </strong></strong></td>
				</tr>
				<tr>
					<td><strong>Isenção para Cobrança de Água:</strong></td>
					<td><strong> <html:radio property="indicadorIsencaoCobrancaAgua" value="1" tabindex="27" />
					<strong>Sim <html:radio property="indicadorIsencaoCobrancaAgua" value="2" tabindex="28" />
					N&atilde;o</strong> 
					<strong><html:radio property="indicadorIsencaoCobrancaAgua"
										value="<%=ConstantesSistema.TODOS.toString()%>" /> Todos </strong></strong></td>
				</tr>
				<tr>
					<td><strong>Isenção para Cobrança de Esgoto:</strong></td>
					<td><strong> <html:radio property="indicadorIsencaoCobrancaEsgoto" value="1" tabindex="29" />
					<strong>Sim <html:radio property="indicadorIsencaoCobrancaEsgoto" value="2" tabindex="30" />
					N&atilde;o</strong> 
					<strong><html:radio property="indicadorIsencaoCobrancaEsgoto"
										value="<%=ConstantesSistema.TODOS.toString()%>" /> Todos </strong></strong></td>
				</tr>
				<tr>
					<td><strong>Concessão de Crédito de Consumo:</strong></td>
					<td><strong> <html:radio property="indicadorConcessaoCreditoConsumo"
						value="1" tabindex="31" /> <strong>Sim <html:radio
						property="indicadorConcessaoCreditoConsumo" value="2" tabindex="32" /> N&atilde;o</strong>
					</strong>
					<strong><html:radio property="indicadorConcessaoCreditoConsumo"
										value="<%=ConstantesSistema.TODOS.toString()%>" /> Todos </strong></td>
				</tr>
				<tr>
					<td><strong>Anormalidade Emite OS Automática:</strong></td>
					<td><strong> <html:radio property="osAutomatico" value="1"
						onchange="javascript:desabiltaCombo();" tabindex="33" /> <strong>Sim <html:radio
						property="osAutomatico" value="2"
						onchange="javascript:desabiltaCombo();" tabindex="34" /> N&atilde;o</strong>
						<strong><html:radio property="osAutomatico"
										value="<%=ConstantesSistema.TODOS.toString()%>" onchange="javascript:desabiltaCombo();" /> Todos </strong> </strong></td>

				</tr>

				<!-- Tipo de Serviço -->

				<tr>
					<td><strong>Tipo de Serviço:</strong></td>
					<td colspan="2" align="left"><html:select property="tipoServico" tabindex="35" >
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoTipoServico"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>
				<tr>
					<td><strong>Incidências para Geração de OS:</strong></td>
					<td colspan="2"><span class="style2"> <html:text
						property="numeroIncidenciasGeracaoOS" size="5" maxlength="2" tabindex="36" /> </span></td>
				</tr>
				
				<tr>
					<td><strong>Tipo do Documento:</strong></td>
					<td colspan="2" align="left"><html:select property="tipoDocumento" tabindex="37">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoDocumentoTipo"
							labelProperty="descricaoDocumentoTipo" property="id" />
					</html:select></td>
				</tr>
				<!--Consumo a Ser Cobrado (anormalidade informada e leitura não informada)-->

				<tr>
					<td><strong>Consumo a Ser Cobrado (anormalidade informada e leitura
					não informada):</strong></td>
					<td colspan="2" align="left"><html:select
						property="consumoLeituraNaoInformado" tabindex="38">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoLeituraAnormalidadeConsumo"
							labelProperty="descricaoConsumoACobrar" property="id" />
					</html:select></td>
				</tr>

				<tr>
					<td><strong>Consumo a Ser Cobrado (anormalidade informada e leitura
					informada):</strong></td>
					<td colspan="2" align="left"><html:select
						property="consumoLeituraInformado" tabindex="39">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoLeituraAnormalidadeConsumo"
							labelProperty="descricaoConsumoACobrar" property="id" />
					</html:select></td>
				</tr>

				<tr>
					<td><strong>Leitura para faturamento (anormalidade informada e
					leitura não informada):</strong></td>
					<td colspan="2" align="left"><html:select
						property="leituraLeituraNaoturaInformado" tabindex="40">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoLeituraAnormalidadeLeitura"
							labelProperty="descricaoFaturamento" property="id" />
					</html:select></td>
				</tr>

				<tr>
					<td><strong>Leitura para faturamento (anormalidade informada e
					leitura informada):</strong></td>
					<td colspan="2" align="left"><html:select
						property="leituraLeituraInformado" tabindex="41">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoLeituraAnormalidadeLeitura"
							labelProperty="descricaoFaturamento" property="id" />
					</html:select></td>
				</tr>
				
				<tr>
					<td><strong>Indicador de uso:</strong></td>
					<td><html:radio property="indicadorUso" tabindex="42" value="1"/><strong>Ativo</strong>
						<html:radio	property="indicadorUso" tabindex="43" value="2"/><strong>Inativo</strong>
						<html:radio	property="indicadorUso" tabindex="44" value="3"/><strong>Todos</strong>
					</td>
				</tr>		
								
				<tr>
					<td><strong> <font color="#FF0000"> <input type="button"
						name="Submit22" class="bottonRightCol" value="Limpar"
						onClick="javascript:window.location.href='/gsan/exibirFiltrarAnormalidadeLeituraAction.do?menu=sim'"><!-- <input type="button"
								name="Submit23" class="bottonRightCol" value="Cancelar"
								onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"> -->
					</font> </strong></td>
					<td colspan="2" align="right"><input type="button" name="Submit2"
						class="bottonRightCol" value="Filtrar"
						onclick="validaForm(document.forms[0]);"></td>
				</tr>
			</table>
			<p>&nbsp;</p>
		</tr>
		<!-- Rodapé -->
		<%@ include file="/jsp/util/rodape.jsp"%>
	</table>
	<p>&nbsp;</p>

	<tr>

	</tr>

</html:form>
</body>
</html:html>

