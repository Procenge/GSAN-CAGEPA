<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page import="gcom.util.ConstantesSistema"%>

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>

EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="PesquisarLeituraAnormalidadeActionForm"
	dynamicJavascript="false" />

<script>

 var bCancel = false;

    function validatePesquisarLeituraAnormalidadeActionForm(form) {
        if (bCancel) 
      return true;
      else 
       return validateCaracterEspecial(form);
   }

    function caracteresespeciais () {
     this.aa = new Array("descricao", "A descrição da anormalidade de leitura possui caracteres especiais.", new Function ("varName", " return this[varName];"));
    }

	function validarForm(form){

		if(validatePesquisarLeituraAnormalidadeActionForm(form)){
    		form.submit();
		}
	}
	
	function limpar(){
		var form = document.forms[0];
		
		form.descricao.value = "";
		form.numeroIncidenciasGeracaoOS.value = "";
		
		form.mensagemImpressaoConta.value = "";
		form.sugestaoAgenteManutencao.value = "";
		form.sugestaoAgentePrevencao.value = "";
		
		form.anormalidadeRelativaHidrometro.value = "<%=ConstantesSistema.TODOS.toString()%>";
		form.anormalidadeSemHidrometro.value = "<%=ConstantesSistema.TODOS.toString()%>";
		form.anormalidadeRestritoSistema.value = "<%=ConstantesSistema.TODOS.toString()%>";
		form.anormalidadePerdaTarifaSocial.value = "<%=ConstantesSistema.TODOS.toString()%>";
		form.anormalidadeOrdemServicoAutomatica.value = "<%=ConstantesSistema.TODOS.toString()%>";
		form.aceiteLeitura.value = "<%=ConstantesSistema.TODOS.toString()%>";
		form.impressaoRelatorioCriticaFiscalizacao.value = "<%=ConstantesSistema.TODOS.toString()%>";
		form.indicadorRetencaoConta.value = "<%=ConstantesSistema.TODOS.toString()%>";
		form.indicadorIsencaoCobrancaAgua.value = "<%=ConstantesSistema.TODOS.toString()%>";
		form.indicadorIsencaoCobrancaEsgoto.value = "<%=ConstantesSistema.TODOS.toString()%>";
		form.indicadorConcessaoCreditoConsumo.value = "<%=ConstantesSistema.TODOS.toString()%>";
		
		
	
		form.tipoServico.value = "<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>";
		form.tipoDocumento.value = "<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>";
		form.consumoCobradoLeituraNaoInformada.value = "<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>";
		form.consumoCobradoLeituraInformada.value = "<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>";
		form.leituraFaturamentoLeituraNaoInformada.value = "<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>";
		form.leituraFaturamentoLeituraInformada.value = "<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>";
		
	}
	
	function desmarcarTodosRadios(campo) {
	   for ( i = 0; i < document.forms[0].elements[campo].length; i++ ) {
	      document.forms[0].elements[campo][i].checked = false;
	   }
	}

</script>

</head>

<body leftmargin="5" topmargin="5" onload="resizePageSemLink(750, 585);">
<html:form action="/pesquisarLeituraAnormalidadeAction"
	method="post"
	onsubmit="return validatePesquisarLeituraAnormalidadeActionForm(this);">
	<table width="750" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="745" valign="top" class="centercoltext">
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
					<td class="parabg">Pesquisar Anormalidade de Leitura</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			
			<table width="100%" border="0">
				<tr>
					<td colspan="4">Preencha os campos para pesquisar uma anormalidade
					de leitura:</td>
					<td align="right"></td>
				</tr>
			</table>
				
			<table width="100%" border="0">
				<tr>
					<td width="60%"><strong>Descri&ccedil;&atilde;o da Anormalidade de
					Leitura:</strong></td>
					<td width="40%" colspan="3"><html:text property="descricao"
						size="25" maxlength="25" /></td>
				</tr>
				<tr>
					<td colspan="3" align="right">
					<html:radio property="tipoPesquisa"	tabindex="2"
						value="<%=ConstantesSistema.TIPO_PESQUISA_INICIAL.toString()%>" />
						Iniciando pelo texto
						<html:radio	property="tipoPesquisa" tabindex="3"
						value="<%=ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString()%>" />
						Contendo o texto
					</td>
				</tr>	
				<tr>				
				</tr>
				<tr>
					<td colspan="4">

					<table width="100%" align="center" bgcolor="#99CCFF" border="0">
						<tr bgcolor="#cbe5fe">
							<td width="100%" align="center">

							<table width="100%" border="0">
								<tr>
									<td width="60%"><strong>Mensagem a ser Impressa em Conta:</strong></td>
									<td width="40%"><html:radio property="mensagemImpressaoConta" tabindex="4" value="1"/><strong>Possui</strong>
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
								<tr>
									<td><strong>Anormalidade Relativa a Hidr&ocirc;metro:</strong></td>
									<td align="right">
									<div align="left"><strong> <label> <html:radio
										property="anormalidadeRelativaHidrometro"
										value="<%=ConstantesSistema.SIM.toString()%>" /> Sim</label>
									<label> <html:radio property="anormalidadeRelativaHidrometro"
										value="<%=ConstantesSistema.NAO.toString()%>" /> N&atilde;o</label>
									<label> <html:radio property="anormalidadeRelativaHidrometro"
										value="<%=ConstantesSistema.TODOS.toString()%>" />Todos</label>
									</strong></div>
									</td>
								</tr>
								<tr>
									<td><strong>Anormalidade Aceita para Liga&ccedil;&atilde;o sem
									Hidr&ocirc;metro:</strong></td>
									<td align="right">
									<div align="left"><strong> <label> <html:radio
										property="anormalidadeSemHidrometro"
										value="<%=ConstantesSistema.SIM.toString()%>" /> Sim</label>
									<label> <html:radio property="anormalidadeSemHidrometro"
										value="<%=ConstantesSistema.NAO.toString()%>" /> N&atilde;o</label>
										<label> <html:radio property="anormalidadeSemHidrometro"
										value="<%=ConstantesSistema.TODOS.toString()%>" /> Todos</label>
									</strong></div>
									</td>
								</tr>
								<tr>
									<td><strong>Anormalidade de Uso Restrito do Sistema:</strong></td>
									<td align="right">
									<div align="left"><strong> <label> <html:radio
										property="anormalidadeRestritoSistema"
										value="<%=ConstantesSistema.SIM.toString()%>" /> Sim</label>
									<label> <html:radio property="anormalidadeRestritoSistema"
										value="<%=ConstantesSistema.NAO.toString()%>" /> N&atilde;o</label>
										<label> <html:radio property="anormalidadeRestritoSistema"
										value="<%=ConstantesSistema.TODOS.toString()%>" /> Todos</label>
									</strong></div>
									</td>
								</tr>
								<tr>
									<td><strong>Anormalidade Acarreta Perda Tarifa Social:</strong></td>
									<td align="right">
									<div align="left"><strong> <label> <html:radio
										property="anormalidadePerdaTarifaSocial"
										value="<%=ConstantesSistema.SIM.toString()%>" /> Sim</label>
									<label> <html:radio property="anormalidadePerdaTarifaSocial"
										value="<%=ConstantesSistema.NAO.toString()%>" /> N&atilde;o</label>
										<label><html:radio property="anormalidadePerdaTarifaSocial"
										value="<%=ConstantesSistema.TODOS.toString()%>" /> Todos</label>
									</strong></div>
									</td>
								</tr>
								<tr>
									<td><strong>Aceite de Leitura:</strong></td>
									<td><strong> <html:radio property="aceiteLeitura" value="1" tabindex="21"/>
										<strong>Sim <html:radio property="aceiteLeitura" value="2"  tabindex="22"/>
										N&atilde;o</strong> 
										<label><html:radio property="aceiteLeitura"
										value="<%=ConstantesSistema.TODOS.toString()%>" /> Todos</label>
										</strong></td>

								</tr>
								<tr>
									<td><strong>Impressão Relatório de Crítica/Fiscalização:</strong></td>
									<td><strong> <html:radio property="impressaoRelatorioCriticaFiscalizacao" value="1" tabindex="23" />
										<strong>Sim <html:radio property="impressaoRelatorioCriticaFiscalizacao" value="2" tabindex="24" />
										N&atilde;o</strong> 
										<label><html:radio property="impressaoRelatorioCriticaFiscalizacao"
										value="<%=ConstantesSistema.TODOS.toString()%>" /> Todos</label></strong></td>

								</tr>
								<tr>
									<td><strong>Retenção de Conta:</strong></td>
									<td><strong> <html:radio property="indicadorRetencaoConta" value="1" tabindex="25" />
										<strong>Sim <html:radio property="indicadorRetencaoConta" value="2" tabindex="26" />
									N&atilde;o</strong> 
									<label><html:radio property="indicadorRetencaoConta"
										value="<%=ConstantesSistema.TODOS.toString()%>" /> Todos</label></strong></td>
								</tr>
								<tr>
									<td><strong>Isenção para Cobrança de Água:</strong></td>
									<td><strong> <html:radio property="indicadorIsencaoCobrancaAgua" value="1" tabindex="27" />
										<strong>Sim <html:radio property="indicadorIsencaoCobrancaAgua" value="2" tabindex="28" />
										N&atilde;o</strong> 
										<label><html:radio property="indicadorIsencaoCobrancaAgua"
										value="<%=ConstantesSistema.TODOS.toString()%>" /> Todos</label></strong></td>
								</tr>
								<tr>
									<td><strong>Isenção para Cobrança de Esgoto:</strong></td>
									<td><strong> <html:radio property="indicadorIsencaoCobrancaEsgoto" value="1" tabindex="29" />
										<strong>Sim <html:radio property="indicadorIsencaoCobrancaEsgoto" value="2" tabindex="30" />
										N&atilde;o</strong> 
										<label><html:radio property="indicadorIsencaoCobrancaEsgoto"
										value="<%=ConstantesSistema.TODOS.toString()%>" /> Todos</label></strong></td>
								</tr>
								<tr>
									<td><strong>Concessão de Crédito de Consumo:</strong></td>
									<td><strong> <html:radio property="indicadorConcessaoCreditoConsumo"
										value="1" tabindex="31" /> <strong>Sim <html:radio
										property="indicadorConcessaoCreditoConsumo" value="2" tabindex="32" /> N&atilde;o</strong>
										<label><html:radio property="indicadorConcessaoCreditoConsumo"
										value="<%=ConstantesSistema.TODOS.toString()%>" /> Todos</label></strong></td>
								</tr>
								<tr>
									<td><strong>Anormalidade Emite OS Autom&aacute;tica:</strong></td>
									<td align="right">
									<div align="left"><strong> <label> <html:radio
										property="anormalidadeOrdemServicoAutomatica"
										value="<%=ConstantesSistema.SIM.toString()%>" /> Sim</label>
									<label> <html:radio
										property="anormalidadeOrdemServicoAutomatica"
										value="<%=ConstantesSistema.NAO.toString()%>" /> N&atilde;o</label>
										<label><html:radio
										property="anormalidadeOrdemServicoAutomatica"
										value="<%=ConstantesSistema.TODOS.toString()%>" /> Todos</label>
									</strong></div>
									</td>
								<tr>
									<td><strong>Tipo de Servi&ccedil;o da OS Autom&aacute;tica: </strong></td>
									<td align="right">
									<div align="left"><html:select property="tipoServico">
										<html:option
											value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
										<html:options collection="colecaoTipoServico"
											labelProperty="descricao" property="id" />	
									</html:select></div>
									</td>
								</tr>
								<tr>
									<td><strong>Incidências para Geração de OS:</strong></td>
										<td><span class="style2"> <html:text
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
							</table>
							</td>
						</tr>
					</table>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="4">

					<table width="100%" align="center" bgcolor="#99CCFF" border="0">
						<tr bgcolor="#cbe5fe">
							<td width="100%" align="center">

							<table width="100%" border="0">
								<tr>
									<td><strong>Consumo a Ser Cobrado (leitura n&atilde;o
									informada):</strong></td>
									<td align="right">
									<div align="left"><html:select
										property="consumoCobradoLeituraNaoInformada">
										<html:option
											value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
										<html:options collection="leiturasAnormalidadesConsumo"
											labelProperty="descricaoConsumoACobrar" property="id" />
									</html:select></div>
									</td>
								</tr>
								<tr>
									<td><strong>Consumo a Ser Cobrado (leitura informada):</strong></td>
									<td align="right">
									<div align="left"><html:select
										property="consumoCobradoLeituraInformada">
										<html:option
											value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
										<html:options collection="leiturasAnormalidadesConsumo"
											labelProperty="descricaoConsumoACobrar" property="id" />
									</html:select></div>
									</td>
								</tr>
								<tr>
									<td><strong>Leitura para faturamento (leitura n&atilde;o
									informada): </strong></td>
									<td align="right">
									<div align="left"><html:select
										property="leituraFaturamentoLeituraNaoInformada">
										<html:option
											value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
										<html:options collection="leiturasAnormalidadesLeitura"
											labelProperty="descricaoFaturamento" property="id" />
									</html:select></div>
									</td>
								</tr>
								<tr>
									<td><strong>Leitura para faturamento (leitura informada):</strong></td>
									<td align="right">
									<div align="left"><html:select
										property="leituraFaturamentoLeituraInformada">
										<html:option
											value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
										<html:options collection="leiturasAnormalidadesLeitura"
											labelProperty="descricaoFaturamento" property="id" />
									</html:select></div>
									</td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td height="24"><input type="button" name="Button" class="bottonRightCol"
						value="Limpar" onClick="javascript:limpar();" /></td>
					<td align="right"><input type="button" name="Button" class="bottonRightCol"
						value="Pesquisar" onClick="javascript:validarForm(document.forms[0])" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>
</html:form>
</body>
</html:html>
