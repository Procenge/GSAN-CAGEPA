<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

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
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="InserirAnormalidadeLeituraActionForm" />

<script language="JavaScript">
  
	function validaForm() {
	  	var form = document.forms[0];
	  	form.action = "/gsan/inserirAnormalidadeLeituraAction.do";
	  	
	  	if (validateCamposMensagem() == false){
	  	  return false;
	  	}
	  	
		if(validateInserirAnormalidadeLeituraActionForm(form)) {	     				
			submeterFormPadrao(form);   		     	      	
   	    }
	}
	 
 	function validateCamposMensagem(){
 		var formulario = document.forms[0];
 		 		
 		if (formulario.mensagemImpressaoConta.value.length > 200){
	     alert('Mensagem para Impressão em Conta maior que 200 caracteres.');
	     formulario.mensagemImpressaoConta.focus();
	     return false;
	    }
	    if (formulario.sugestaoAgenteManutencao.value.length > 200){
	     alert('Mensagem para Manutenção maior que 200 caracteres.');
	     formulario.sugestaoAgenteManutencao.focus();
	     return false;
	    }
	    if (formulario.sugestaoAgentePrevencao.value.length > 200){
	     alert('Mensagem para Prevenção de Acidentes maior que 200 caracteres.');
	     formulario.sugestaoAgentePrevencao.focus();
	     return false;
	    }
	    return true;
 	}
 
	function limparForm() {
		var form = document.InserirAnormalidadeLeituraActionForm;
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
	
		function desabiltaCombo(){
	var form = document.forms[0];
	if(form.osAutomatico[1].checked){
	form.tipoServico.value="-1";
	form.tipoServico.disabled = true;
	
	form.numeroIncidenciasGeracaoOS.value = "";
	form.numeroIncidenciasGeracaoOS.disabled = true;
	}else{ if (form.osAutomatico[0].checked){
	form.tipoServico.value="-1";
	form.tipoServico.disabled = false;
		
	form.numeroIncidenciasGeracaoOS.disabled = false;
	}
}
	
	}  
	
	function reload() {
		var form = document.InserirAnormalidadeLeituraActionForm;
		form.action = "/gsan/exibirInserirAnormalidadeLeituraAction.do";
		form.submit();
	}  
	

</script>

</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:desabiltaCombo();">

<html:form action="/inserirAnormalidadeLeituraAction"
	name="InserirAnormalidadeLeituraActionForm"
	type="gcom.gui.micromedicao.leitura.InserirAnormalidadeLeituraActionForm"
	method="post">

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

			<!-- centercoltext -->

			<td width="625" valign="top" class="centercoltext">

			<table>
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">Inserir Anormalidade de Leitura</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td height="10" colspan="3">Para adicionar a anormalidade de
					leitura, informe os dados abaixo:</td>
				</tr>
				<tr>
					<td height="10" colspan="3">
					<hr>
					</td>
				</tr>


				<!-- Descricao -->

				<tr>
					<td><strong>Descri&ccedil;&atilde;o:<font color="#FF0000">*</font></strong></td>
					<td colspan="2"><span class="style2"> <html:text
						property="descricao" size="25" maxlength="25" tabindex="1" /> </span></td>
				</tr>

				<!-- Abreviatura -->

				<tr>
					<td><strong>Abreviatura:</strong></td>
					<td colspan="2"><span class="style2"> <html:text
						property="abreviatura" size="5" maxlength="5" tabindex="2" /> </span></td>
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
						value="1" tabindex="6" /> <strong>Sim <html:radio
						property="indicadorRelativoHidrometro" value="2" tabindex="7" /> N&atilde;o</strong>
					</strong></td>

				</tr>
				<tr>
					<td><strong>Anormalidade Aceita para Ligação sem Hidrômetro:</strong></td>
					<td><strong> <html:radio property="indicadorImovelSemHidrometro"
						value="1" tabindex="8" /> <strong>Sim <html:radio
						property="indicadorImovelSemHidrometro" value="2" tabindex="9" /> N&atilde;o</strong>
					</strong></td>

				</tr>
				<tr>
					<td><strong>Anormalidade de Uso Restrito do Sistema:</strong></td>
					<td><strong> <html:radio property="usoRestritoSistema" value="1" tabindex="10" />
					<strong>Sim <html:radio property="usoRestritoSistema" value="2" tabindex="11" />
					N&atilde;o</strong> </strong></td>

				</tr>
				<tr>
					<td><strong>Anormalidade Acarreta Perda Tarifa Social:</strong></td>
					<td><strong> <html:radio property="perdaTarifaSocial" value="1" tabindex="12" />
					<strong>Sim <html:radio property="perdaTarifaSocial" value="2" tabindex="13" />
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
						onchange="javascript:desabiltaCombo();" tabindex="26" /> <strong>Sim <html:radio
						property="osAutomatico" value="2"
						onchange="javascript:desabiltaCombo();" tabindex="27" /> N&atilde;o</strong> </strong></td>

				</tr>

				<!-- Tipo de Serviço -->

				<tr>
					<td><strong>Tipo de Serviço:</strong></td>
					<td colspan="2" align="left"><html:select property="tipoServico" tabindex="28">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoTipoServico"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>

				<!--Consumo a Ser Cobrado (anormalidade informada e leitura não informada)-->

										
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
				<tr>
					<td><strong>Consumo a Ser Cobrado (anormalidade informada e leitura
					não informada):<font color="#FF0000">*</font></strong></td>
					<td colspan="2" align="left"><html:select
						property="consumoLeituraNaoInformado" tabindex="31">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoLeituraAnormalidadeConsumo"
							labelProperty="descricaoConsumoACobrar" property="id" />
					</html:select></td>
				</tr>

				<tr>
					<td><strong>Consumo a Ser Cobrado (anormalidade informada e leitura
					informada):<font color="#FF0000">*</font></strong></td>
					<td colspan="2" align="left"><html:select
						property="consumoLeituraInformado" tabindex="32">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoLeituraAnormalidadeConsumo"
							labelProperty="descricaoConsumoACobrar" property="id" />
					</html:select></td>
				</tr>

				<tr>
					<td><strong>Leitura para faturamento (anormalidade informada e
					leitura não informada):<font color="#FF0000">*</font></strong></td>
					<td colspan="2" align="left"><html:select
						property="leituraLeituraNaoturaInformado" tabindex="33">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoLeituraAnormalidadeLeitura"
							labelProperty="descricaoFaturamento" property="id" />
					</html:select></td>
				</tr>

				<tr>
					<td><strong>Leitura para faturamento (anormalidade informada e
					leitura informada):<font color="#FF0000">*</font></strong></td>
					<td colspan="2" align="left"><html:select
						property="leituraLeituraInformado" tabindex="34">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoLeituraAnormalidadeLeitura"
							labelProperty="descricaoFaturamento" property="id" />
					</html:select></td>
				</tr>		
				<!-- Botões -->

				<tr>
					<td align="left"><input name="Button" type="button"
						class="bottonRightCol" value="Desfazer" align="left"
						onclick="window.location.href='<html:rewrite page="/exibirInserirAnormalidadeLeituraAction.do?desfazer=S"/>'">
					<input type="button" name="ButtonCancelar" class="bottonRightCol"
						value="Cancelar"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
					</td>
					<td colspan="2" align="right"><input name="Button" type="button"
						class="bottonRightCol" value="Inserir" onclick="validaForm();"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>

</html:form>
</html:html>
