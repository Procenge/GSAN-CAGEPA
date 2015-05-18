<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@ page import="gcom.atendimentopublico.ordemservico.ServicoAssociado"%>
<%@ page import="gcom.atendimentopublico.ordemservico.ServicoTipoTramite"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>	
<html:javascript staticJavascript="false"  formName="AtualizarTipoServicoActionForm" dynamicJavascript="true" />

<script language="JavaScript">

	function validarIndicadorPagamentoAntecipado() {
			var form=document.forms[0];
			
		if (form.indicadorPagamentoAntecipado[0].checked) {
			form.numeroMaximoGuiaPrestacaoAntecipadaPermitidas.readOnly  = false;	
		} else if (form.indicadorPagamentoAntecipado[1].checked) {
			form.numeroMaximoGuiaPrestacaoAntecipadaPermitidas.value = "";			
			form.numeroMaximoGuiaPrestacaoAntecipadaPermitidas.readOnly = true;
		}	
		
	}

	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado) {
		if(objetoRelacionado.disabled != true) {
			if (objeto == null || codigoObjeto == null) {
				abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
			} else {
				if (codigoObjeto.length < 1 || isNaN(codigoObjeto)) {
					alert(msg);
				} else {
					abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
				}
			}
		}
	}	 

	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
  		var form=document.forms[0];
    	if (tipoConsulta == 'hidrometro') {
      		form.numeroHidrometro.value = codigoRegistro;
    	} else if (tipoConsulta == 'tipoDebito') { 	
	 	  	form.idTipoDebito.value = codigoRegistro;
	 	  	form.descricaoTipoDebito.value = descricaoRegistro; 
    	} else if (tipoConsulta == 'servicoPerfilTipo') { 	
	 	  	form.idPerfilServico.value = codigoRegistro;
	 	  	form.descricaoPerfilServico.value = descricaoRegistro; 
		} else if (tipoConsulta == 'servicoTipoReferencia') {
			form.idTipoServicoReferencia.value = codigoRegistro;
			form.descricaoTipoServicoReferencia.value = descricaoRegistro; 
			controlaTipoServicoReferenciaOnKeyUp();
		}
  	}	
  	
  	function recuperarDadosQuatroParametros(codigoRegistro, descricaoRegistro, codigoAuxiliar, tipoConsulta) {
  		if (tipoConsulta == 'atividade') {
	 		insertRowTableAtividades(codigoRegistro, descricaoRegistro, codigoAuxiliar);
  		} else if (tipoConsulta == 'material') {
	 		insertRowTableMateriais(codigoRegistro, descricaoRegistro, codigoAuxiliar);
 		}else if (tipoConsulta == 'servicoValorLocalidade'){
 			insertRowTableServicoTipoValorLocalidade(codigoRegistro, descricaoRegistro, codigoAuxiliar);
 		}
  	}
  
	function limpar(tipo) {
		var form = document.forms[0];
		if (tipo == 'tipoDebito') {
			form.idTipoDebito.value = "";
			form.descricaoTipoDebito.value = "";		
			reload();
		} else if (tipo == 'tipoCrebito') {
			form.tipoCredito.disabled = "true";		
			form.tipoCredito.selectedIndex = -1;
			reload();		
		} else if (tipo == 'perfil') {
			form.perfilServico.value = "";
			form.descricaoPerfilServico.value = ""; 
		} else if (tipo == 'referencia') {
			form.idTipoServicoReferencia.value = "";
			form.descricaoTipoServicoReferencia.value = ""; 
			form.idTipoServicoReferencia.disabled = false;
			form.lupaServicoTipoReferencia.disabled = false;
		}
	}  
	
	function reload() {
		var form = document.forms[0];
		form.action = "/gsan/exibirAtualizarTipoServicoAction.do";
		form.submit();
	}  
	
	function pesquisarTipoCredito() {
		var form = document.forms[0];	
		form.idTipoDebito.value = "";
		form.descricaoTipoDebito.value = "";		
		reload();
	}  
	
	function popupTipoDebito() {
		chamarPopup('exibirPesquisarTipoDebitoAction.do', 'tipoDebito', null, null, 550, 760, '',document.forms[0].idTipoDebito);	
	}
	
	function popupPerfilServico() {
		chamarPopup('exibirPesquisarTipoPerfilServicoAction.do', 'perfilServico', null, null, 550, 760, '',document.forms[0].perfilServico);
	}
	
	function popupServicoTipoReferencia() {
		chamarPopup('exibirPesquisarTipoServicoReferenciaAction.do?menu=sim', 'referencia', null, null, 550, 760, '',document.forms[0].idTipoServicoReferencia);
	}
	
	function pesquisarTipoDebito() {
		var form = document.forms[0];	
		form.tipoCredito.disabled = "true";		
		form.tipoCredito.selectedIndex = -1;
		reload();		
	}  
	
	function insertRowTableAtividades(codigoRegistro, descricaoRegistro, codigoAuxiliar) {
		var form = document.forms[0];	
		form.idAtividade.value = codigoRegistro;
		form.servicoTipoAtividadeOrdemExecucao.value = codigoAuxiliar;
		form.descricaoAtividadeTipoServico.value = descricaoRegistro;
		form.idAtividadeTipoServico.value = "$" + codigoRegistro + "$" + codigoAuxiliar + "$";
		form.method.value = "addServicoTipoAtividade";
		reload();
	}
	
	function insertRowTableServicoTipoValorLocalidade(codigoRegistro, descricaoRegistro, codigoAuxiliar) {
		var form = document.forms[0];	
		form.valorLocalidadeId.value = codigoRegistro;
		form.servicoTipoValorLocalidadeValor.value = codigoAuxiliar;
		form.servicoTipoValorLocalidadeDescricao.value = descricaoRegistro;
		form.method.value = "addServicoTipoValorLocalidade";
		reload();
	}	

	
	function insertRowTableMateriais(codigoRegistro, descricaoRegistro, codigoAuxiliar) {
		var form = document.forms[0];	
		form.idMaterial.value = codigoRegistro;
		form.servicoTipoMaterialQuantidadePadrao.value = codigoAuxiliar;
		form.descricaoMaterialTipoServico.value = descricaoRegistro;
		form.idMaterialTipoServico.value = "$" + codigoRegistro + "$" + codigoAuxiliar + "$";
		form.method.value = "addServicoTipoMaterial";
		reload();
	}	
	

	function removeRowTableServicoTipoReferencia(id) {
		var form = document.forms[0];	
		form.method.value = "removeRowTableServicoTipoReferencia";
		reload();
	}
	
	
	function removeRowTableAtividades(id) {
		var form = document.forms[0];	
		form.idAtividadeTipoServico.value = id;
		form.method.value = "removeServicoTipoAtividade";
		reload();
	}
	
	function removeRowTableServicoTipoValorLocalidade(id) {
		var form = document.forms[0];	
		form.valorLocalidadeId.value = id;
		form.method.value = "removeServicoTipoValorLocalidade";
		reload();
	}


	function removeAllRowsTableAtividades() {
		var form = document.forms[0];	
		form.method.value = "removeAllServicoTipoAtividade";
		reload();
	}
	
	function removeRowTableMateriais(id) {
		var form = document.forms[0];	
		form.idMaterialTipoServico.value = id;
		form.method.value = "removeServicoTipoMaterial";
		reload();
	}
	function removeAllRowsTableMateriais() {
		var form = document.forms[0];	
		form.method.value = "removeAllServicoTipoMaterial";
		reload();
	}
	
	function removeRowTableAssociadas(id) {
		var form = document.forms[0];	
		form.action= "/gsan/pesquisarRemoverServicoTipoAssociadoManterAction.do?idServicoAssociadoRemover=" + id;
		form.submit();
	}

	function removeRowTableServicoTipoTramite(id) {
		var form = document.forms[0];	
		form.action= "/gsan/pesquisarRemoverServicoTipoTramiteManterAction.do?idServicoTipoTramiteRemover=" + id;
		form.submit();
	}

	function validaForm() {
	  	var form = document.forms[0];
	  	form.action = "/gsan/atualizarTipoServicoAction.do";
		if(validateAtualizarTipoServicoActionForm(form)) {
		  	if(validaTodosRadioButton()) {		     		  		
				submeterFormPadrao(form);   		  
   	      	}
   	    }
	}
	 
  	function validaTodosRadioButton() {
		var form = document.forms[0];
		
		if (!form.atualizacaoComercial[0].checked
				&& !form.atualizacaoComercial[1].checked
				&& !form.atualizacaoComercial[2].checked) {
			alert("Informe Indicador Atualização Comercial.");
			return false;
		}
		if (!form.pavimento[0].checked
				&& !form.pavimento[1].checked
				&& !form.pavimento[2].checked) {
			alert("Informe Indicador Pavimento.");
			return false;
		}
		if (!form.servicoTerceirizado[0].checked
				&& !form.servicoTerceirizado[1].checked) {
			alert("Informe Indicador Serviço Terceirizado.");
			return false;
		}
		if (!form.codigoServico[0].checked
				&& !form.codigoServico[1].checked) {
			alert("Informe Código do Tipo de Serviço.");
			return false;
		}
		if (!form.indicadorAtividadeUnica[0].checked
				&& !form.indicadorAtividadeUnica[1].checked) {
			alert("Informe Atividade Única.");
			return false;
		}
		
		if (!form.indicadorVistoria[0].checked
				&& !form.indicadorVistoria[1].checked) {
			alert("Informe Indicativo de Vistoria.");
			return false;
		}
		
		if (!form.indicadorAfericaoHidrometro[0].checked
				&& !form.indicadorAfericaoHidrometro[1].checked) {
			alert("Informe Indicativo de Aferição de Hidrômetro.");
			return false;
		}
		
		if (!form.indicadorPagamentoAntecipado[0].checked
				&& !form.indicadorPagamentoAntecipado[1].checked) {
			alert("Informe Indicativo de Pagamento Antecipado.");		
			return false;
		}		
		
		if (!form.indicadorFiscalizacaoInfracao[0].checked
				&& !form.indicadorFiscalizacaoInfracao[1].checked) {
			alert("Informe Indicativo de Fiscalização de Infração.");
			return false;
		}
		if (!form.tipoRemuneracao[0].checked
				&& !form.tipoRemuneracao[1].checked) {
			alert("Informe Tipo de Remuneração.");
			return false;
		}
		if (!form.indicadorDeslocamento[0].checked
				&& !form.indicadorDeslocamento[1].checked
				&& !form.indicadorDeslocamento[2].checked) {
			alert("Informe Indicador Deslocamento.");
			return false;
		}
		if (!form.indicadorHorariosExecucao[0].checked
				&& !form.indicadorHorariosExecucao[1].checked
				&& !form.indicadorHorariosExecucao[2].checked) {
			alert("Informe Indicador Horários Execução.");
			return false;
		}
		if (!form.indicadorCausaOcorrencia[0].checked
				&& !form.indicadorCausaOcorrencia[1].checked
				&& !form.indicadorCausaOcorrencia[2].checked) {
			alert("Informe Indicador Causa Ocorrência.");
			return false;
		}
		if (!form.indicadorRedeRamalAgua[0].checked
				&& !form.indicadorRedeRamalAgua[1].checked
				&& !form.indicadorRedeRamalAgua[2].checked) {
			alert("Informe Indicador Rede/Ramal de Água.");
			return false;
		}
		if (!form.indicadorRedeRamalEsgoto[0].checked
				&& !form.indicadorRedeRamalEsgoto[1].checked
				&& !form.indicadorRedeRamalEsgoto[2].checked) {
			alert("Informe Indicador Rede/Ramal de Esgoto.");
			return false;
		}
		if (!form.indicadorMaterial[0].checked
				&& !form.indicadorMaterial[1].checked
				&& !form.indicadorMaterial[2].checked) {
			alert("Informe Indicador Material");
			return false;
		}
		if (!form.indicadorVala[0].checked
				&& !form.indicadorVala[1].checked
				&& !form.indicadorVala[2].checked) {
			alert("Informe Indicador Vala");
			return false;
		}
		if (!form.indicadorOrdemSeletiva[0].checked
				&& !form.indicadorOrdemSeletiva[1].checked) {
			alert("Informe Indicador Ordem Seletiva");
			return false;
		}
		if (!form.indicadorServicoCritico[0].checked
				&& !form.indicadorServicoCritico[1].checked) {
			alert("Informe Indicador Serviço Crítico");
			return false;
		}
		
		if (!form.indicadorPermiteAlterarValor[0].checked
				&& !form.indicadorPermiteAlterarValor[1].checked) {
			alert("Informe Indicador Permitir Alterar Valor do Débito");
			return false;
		}
		
		
		
		return true;
   	}
   	
   	function inserirAtividade() {
		var form = document.forms[0];		
		if (form.indicadorAtividadeUnica[0].checked) {
			alert('Não é permitido informar atividades para Atividade Única');
			return false;
		} else {
   			chamarPopup('exibirPesquisarServicoTipoAtividadeAction.do', 'servicoTipoAtividade', null, null, 300, 620, '','');
   		}
   	}
   	
   	function inserirServicoTipoValorLocalidade() { 		
	   	chamarPopup('exibirPesquisarServicoTipoValorLocalidadeAction.do?limparCampos=ok&', 'servicoTipoValorLocalidade', null, null, 300, 620, '','');	   	
   	}

	 
	function desfazer() {
		var form = document.forms[0];
		form.action = "/gsan/exibirFiltrarTipoServicoAction.do?menu=sim";
		form.submit();
	}  
	
	function pesquisarTipoOSReferencia() {
		var form = document.forms[0];
		if (form.servicoTipoReferencia.selectedIndex != 0) {
			reload();
		} else {
			form.trocaServico[0].checked = false;
			form.trocaServico[1].checked = false;			
			form.trocaServico[0].disabled = false;
			form.trocaServico[1].disabled = false;			
			form.situacao[0].checked = false;
			form.situacao[1].checked = false;			
			form.situacao[0].disabled = false;
			form.situacao[1].disabled = false;			
			form.atendimentoMotivoEncerramento.disabled = false;
			form.atendimentoMotivoEncerramento.selectedIndex = 0;			
		}
	}  

	function pesquisarAtendimentoMotivoEncerramento() {
		var form = document.forms[0];
		if (form.atendimentoMotivoEncerramento.selectedIndex != 0) {
			if (form.deferimento[0].checked == true
					|| form.deferimento[1].checked == true) {
				reload();
			} else {
				alert("Informe Indicador de Deferimento.");		
				form.atendimentoMotivoEncerramento.selectedIndex = 0;
			}
		}
	}
	
	function controlaTipoServicoReferencia(valor){
		var form = document.forms[0];
		
		if(valor=='B'){
			form.idTipoServicoReferencia.disabled = true;
			form.lupaServicoTipoReferencia.disabled = true;
		}else{
			form.idTipoServicoReferencia.disabled = false;
			form.lupaServicoTipoReferencia.disabled = false;
		}
	}
	
	function controlaTipoServicoReferenciaOnKeyUp() {
		var form = document.forms[0];
		
		if(form.idTipoServicoReferencia.value == ''){
			form.submitServicoTipoReferencia.disabled = false;
		}else{
			form.submitServicoTipoReferencia.disabled = true;
		}
	}
	
	function atualizarColecaoServicoTipoValorLocalidade(id, valor){

		var form = document.forms[0];
		var valorAtualizado = valor.value;
 		form.action = "/gsan/exibirAtualizarTipoServicoAction.do?atualizarIdLocalidade="+id+"&servicoValorLocalidade="+valorAtualizado;
		form.submit();

	}
	
</script>

</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('descricao');validarIndicadorPagamentoAntecipado();">

<html:form action="/atualizarTipoServicoAction.do"
	name="AtualizarTipoServicoActionForm"
	type="gcom.gui.atendimentopublico.ordemservico.AtualizarTipoServicoActionForm"
	method="post"
	onsubmit="return validateAtualizarTipoServicoActionForm(this);">

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

			<html:hidden property="method" />

			<html:hidden property="idAtividade" />
			<html:hidden property="idAtividadeTipoServico" />
			<html:hidden property="descricaoAtividadeTipoServico" />
			<html:hidden property="servicoTipoAtividadeOrdemExecucao" />
			
			<html:hidden property="valorLocalidadeId" />
			<html:hidden property="servicoTipoValorLocalidadeDescricao" />
			<html:hidden property="servicoTipoValorLocalidadeValor" />
			

			<html:hidden property="idMaterial" />
			<html:hidden property="idMaterialTipoServico" />
			<html:hidden property="descricaoMaterialTipoServico" />
			<html:hidden property="servicoTipoMaterialQuantidadePadrao" />

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
					<td class="parabg">Atualizar Tipo de Servi&ccedil;o</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>
				</tr>
			</table>
			<table width="100%" height="311" border="0">
				<tr>
					<td height="10" colspan="4">Para atualizar o tipo de
					servi&ccedil;o, informe os dados abaixo:</td>
				</tr>
				<tr>
					<td height="10" colspan="4">
					<hr>
					</td>
				</tr>

				<!-- Descricao do Tipo do Serviço -->

				<tr>
					<td><strong>Descri&ccedil;&atilde;o do Tipo de Servi&ccedil;o:<font
						color="#FF0000">*</font></strong></td>
					<td colspan="3"><span class="style2"> <html:text
						property="descricao" size="58" maxlength="50" /> </span></td>
				</tr>

				<!-- Descrição do Tipo de Abreviado -->

				<tr>
					<td><strong>Descri&ccedil;&atilde;o do Tipo de Abreviado:</strong></td>
					<td colspan="3"><span class="style2"> <html:text
						property="abreviada" size="30" maxlength="20" /> </span></td>
				</tr>

				<!-- Subgrupo -->

				<tr>
					<td><strong>Subgrupo:<font color="#FF0000">*</font></strong></td>
					<td colspan="3" align="left"><html:select property="subgrupo">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoSubgrupo"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>

				<!-- Indicador de Atualização Comercial -->

				<tr>
					<td><strong><span class="style2">Indicador
					Atualiza&ccedil;&atilde;o Comercial:<font color="#FF0000">*</font></span></strong></td>
					<td align="left"><label> <html:radio
						property="atualizacaoComercial"
						value="<%=gcom.util.ConstantesSistema.ATUALIZACAO_AUTOMATICA + ""%>" />
					<strong>Automática</strong></label></td>
					<td align="left"><label> <html:radio
						property="atualizacaoComercial"
						value="<%=gcom.util.ConstantesSistema.ATUALIZACAO_NENHUMA + ""%>" />
					<strong>Não Atualiza</strong></label></td>
					<td align="left"><label> <html:radio
						property="atualizacaoComercial"
						value="<%=gcom.util.ConstantesSistema.ATUALIZACAO_POSTERIOR + ""%>" />
					<strong>Posterior</strong></label></td>
				</tr>

				<!-- Indicador de Pavimento -->

				<tr>
					<td><strong><span class="style2">Indicador de Pavimento:<font
						color="#FF0000">*</font></span></strong></td>
					<td align="left" width="20%"><label> <html:radio
						property="pavimento" value="1" /> <strong>Obrigatório</strong></label></td>
					<td align="left"><label> <html:radio property="pavimento" value="2" />
					<strong>Não informar</strong></label></td>
					<td align="left">
						<label><html:radio property="pavimento" value="3" /><strong>Opcional</strong></label>
					</td>
				</tr>

				<!-- Indicador de Serviço Terceirizado -->

				<tr>
					<td><strong><span class="style2">Indicador Servi&ccedil;o
					Terceirizado:<font color="#FF0000">*</font></span></strong></td>
					<td align="left"><label> <html:radio property="servicoTerceirizado"
						value="1" /> <strong>Sim</strong></label></td>
					<td align="left"><label> <html:radio property="servicoTerceirizado"
						value="2" /> <strong>Não</strong></label></td>
				</tr>

				<!-- Código do Tipo de Serviço -->

				<tr>
					<td><strong><span class="style2">C&oacute;digo do Tipo de
					Servi&ccedil;o:<font color="#FF0000">*</font></span></strong></td>
					<td align="left" width="20%"><label> <html:radio
						property="codigoServico" value="O" /> <strong>Operacional</strong></label>
					</td>
					<td align="left"><label> <html:radio property="codigoServico"
						value="C" /> <strong>Comercial</strong></label></td>
				</tr>

				<!-- Valor do Serviço -->

				<tr>
					<td><strong>Valor do Servi&ccedil;o:<font color="#FF0000">*</font></strong></td>
					<td colspan="3"><span class="style2"> <html:text property="valorServico"
						size="14" maxlength="14" style="text-align: right;" onkeyup="javaScript:formataValorMonetario(this, 8);"/>
					</span></td>
				</tr>

				<!-- Tempo Médio de Execução -->

				<tr>
					<td><strong>Tempo M&eacute;dio de Execu&ccedil;&atilde;o:<font
						color="#FF0000">*</font></strong></td>
					<td colspan="3"><span class="style2"> <html:text
						property="tempoMedioExecucao" size="8" maxlength="8" /> </span></td>
				</tr>

				<!-- Tipo de Débito -->

				<tr>
					<td><strong>Tipo de D&eacute;bito: </strong></td>
					<td colspan="3"><span class="style2"> <html:text
						property="idTipoDebito" size="4" maxlength="4"
						onkeyup="validaEnterComMensagem(event, 'exibirAtualizarTipoServicoAction.do', 'idTipoDebito', 'Tipo do Débito');" />
					<a href="javascript:popupTipoDebito()"> <img
						src="imagens/pesquisa.gif" width="23" height="21" border="0"
						title="Pesquisar"></a> <c:if
						test="${not empty servicoTipo.debitoTipo}">
						<html:text property="descricaoTipoDebito" readonly="true"
							style="background-color:#EFEFEF; border:0; color:#000000"
							size="40" maxlength="40" />
					</c:if> <c:if test="${empty servicoTipo.debitoTipo}">
						<html:text property="descricaoTipoDebito" readonly="true"
							style="background-color:#EFEFEF; border:0; color:#ff0000"
							size="40" maxlength="40" />
					</c:if> <a href="javascript:limpar('tipoDebito');"> <img
						src="imagens/limparcampo.gif" width="23" height="21" border="0"
						title="Apagar"></a> </span></td>
				</tr>

				<!-- Tipo de Crédito -->

				<tr>
					<td width="200"><strong><span class="style2">Tipo de Crédito:</span></strong></td>
					<td colspan="3" align="left"><c:if
						test="${not empty servicoTipo.creditoTipo}">
						<html:select property="idTipoCredito" disabled="true">
							<html:option value="-1">&nbsp;</html:option>
							<html:options collection="colecaoCreditoTipo"
								labelProperty="descricao" property="id" />
						</html:select>
					</c:if> <c:if test="${empty servicoTipo.creditoTipo}">
						<html:select property="idTipoCredito"
							onchange="javascript:pesquisarTipoCredito();">
							<html:option value="-1">&nbsp;</html:option>
							<html:options collection="colecaoCreditoTipo"
								labelProperty="descricao" property="id" />
						</html:select>
					</c:if></td>
				</tr>

				<!-- Prioridade do Serviço -->

				<tr>
					<td width="200"><strong><span class="style2">Prioridade do
					Servi&ccedil;o:</span><font color="#FF0000">*</font></strong></td>
					<td colspan="3" align="left"><html:select
						property="idPrioridadeServico">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoPrioridadeServico"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>

				<!-- Perfil do Tipo de Serviço -->

				<tr>
					<td><strong>Perfil do Tipo de Servi&ccedil;o:<font color="#FF0000">*</font></strong>
					</td>
					<td colspan="3"><span class="style2"> <html:text
						property="perfilServico" size="4" maxlength="4"
						onkeyup="validaEnterComMensagem(event, 'exibirAtualizarTipoServicoAction.do', 'perfilServico', 'Tipo do Perfil');" />
					<a href="javascript:popupPerfilServico();"> <img
						src="imagens/pesquisa.gif" width="23" height="21" border="0"
						title="Pesquisar"></a> <c:if
						test="${not empty servicoTipo.servicoPerfilTipo}">
						<html:text property="descricaoPerfilServico" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000"
							size="40" maxlength="40" />
					</c:if> <c:if test="${empty servicoTipo.servicoPerfilTipo}">
						<html:text property="descricaoPerfilServico" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000"
							size="40" maxlength="40" />
					</c:if> <a href="javascript:limpar('perfil');"> <img
						src="imagens/limparcampo.gif" width="23" height="21" border="0"
						title="Apagar"></a> </span></td>
				</tr>

				<!-- Tipo do Serviço Referência -->

				<tr>
					<td><strong>Tipo de Servi&ccedil;o de Refer&ecirc;ncia: </strong></td>
					<td colspan="3"><span class="style2">
					<logic:notPresent name="servicoTipoReferencia">
						 <html:text
							property="idTipoServicoReferencia" size="4" maxlength="4"
							onkeyup="validaEnterComMensagem(event, 'exibirAtualizarTipoServicoAction.do', 'idTipoServicoReferencia', 'Tipo de Serviço de Referência');controlaTipoServicoReferenciaOnKeyUp();" />
						<a href="javascript:popupServicoTipoReferencia();"><img
							src="imagens/pesquisa.gif" width="23" height="21" border="0"
							title="Pesquisar" id="lupaServicoTipoReferencia"></a> <c:if
							test="${not empty servicoTipo.servicoTipoReferencia}">
							<html:text property="descricaoTipoServicoReferencia"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color:#000000"
								size="30" maxlength="30" />
						</c:if> <c:if test="${empty servicoTipo.servicoTipoReferencia}">
							<html:text property="descricaoTipoServicoReferencia"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color:#ff0000"
								size="30" maxlength="40" />
						</c:if> <a href="javascript:limpar('referencia');controlaTipoServicoReferenciaOnKeyUp();"><img
							src="imagens/limparcampo.gif" width="23" height="21" border="0"
							title="Apagar"></a>
							
							<!-- adicionar serviço tipo referencia -->
							<input type="button" name="submitServicoTipoReferencia"
								class="bottonRightCol" value="Adicionar"
								onclick="chamarPopup('exibirInserirTipoServicoReferenciaAction.do?semMenu=S', 'servicoTipoReferecia', null, null, 400, 660, '','');">
							
					</logic:notPresent> 
					<logic:present name="servicoTipoReferencia">
					 <html:text
							property="idTipoServicoReferencia" size="4" maxlength="4" disabled="true" />
							<img
							src="imagens/pesquisa.gif" width="23" height="21" border="0"
							title="Pesquisar" id="lupaServicoTipoReferencia"> 
							<html:text property="descricaoTipoServicoReferencia"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color:#000000"
								size="30" maxlength="40" value="${servicoTipoReferencia.descricao}"/>
							<a href="javascript:limpar('referencia');controlaTipoServicoReferenciaOnKeyUp();"><img
							src="imagens/limparcampo.gif" width="23" height="21" border="0"
							title="Apagar"></a>
							<!-- adicionar serviço tipo referencia -->
							<input disabled="true" type="button" name="submitServicoTipoReferencia"
								class="bottonRightCol" value="Adicionar"
								onclick="chamarPopup('exibirInserirTipoServicoReferenciaAction.do?semMenu=S', 'servicoTipoReferecia', null, null, 400, 660, '','');">
					</logic:present>
					</span>
					
					</td>
				</tr>

				<!-- Indicativo de Vistoria -->
				<tr>
					<td><strong><span class="style2">Indicativo de Vistoria:<font
						color="#FF0000">*</font></span></strong></td>
					<td align="left" width="20%"><label> <html:radio
						property="indicadorVistoria" value="1"/> 
						<strong>Sim</strong></label>
					</td>
					<td align="left"><label> <html:radio property="indicadorVistoria"
						value="2" /> <strong>Não</strong></label></td>
				</tr>
				
				<!-- Indicativo de Aferição de Hidrômetro -->
				<tr>
					<td><strong><span class="style2">Indicativo de Aferição de Hidrômetro:<font color="#FF0000">*</font></span></strong></td>
					<td align="left" width="20%">
						<label> 
							<html:radio property="indicadorAfericaoHidrometro" value="1"/> 
								<strong>Sim</strong>
						</label>
					</td>
					<td align="left">
						<label> 
							<html:radio property="indicadorAfericaoHidrometro" value="2" /> 
								<strong>Não</strong>
						</label>
					</td>
				</tr>
				
				<!-- Indicativo de Pagamento Antecipado -->

				<tr>
					<td><strong><span class="style2">Indicativo de Pagamento Antecipado:<font color="#FF0000">*</font></span></strong></td>
					<td align="left" width="20%">
						<label> 
							<html:radio property="indicadorPagamentoAntecipado" onchange="validarIndicadorPagamentoAntecipado()" value="1"/>
								<strong>Sim</strong>
						</label>
					</td>
					<td align="left">
						<label> 
							<html:radio property="indicadorPagamentoAntecipado" onchange="validarIndicadorPagamentoAntecipado()" value="2" /> 
								<strong>Não</strong>
						</label>
					</td>
				</tr>				
				
				<!-- Indicativo de Fiscalizacao de Infração -->
				<tr>
					<td><strong><span class="style2">Indicativo de Fiscalização de Infração:<font
						color="#FF0000">*</font></span></strong></td>
					<td align="left" width="20%"><label> <html:radio
						property="indicadorFiscalizacaoInfracao" value="1" /> <strong>Sim</strong></label>
					</td>
					<td align="left"><label> <html:radio property="indicadorFiscalizacaoInfracao"
						value="2" /> <strong>Não</strong></label></td>
				</tr>
				
				
				<tr>
					<td><strong>Valor Remuneração:</strong></td>
					<td colspan="3"><span class="style2"> <html:text property="valorRemuneracao"
						size="15" maxlength="14" style="text-align: right;" onkeyup="javaScript:formataValorMonetario(this, 8);"/>
					</span></td>
				</tr>
				
				
				<tr>
          			<td><strong>Percentual de Remuneração:</strong></td>
          			<td colspan="3"><html:text property="percentualRemuneracao" size="15" maxlength="5" 
			                		onkeyup="formataValorMonetario(this, 6)" 
			                		style="text-align:right;"/>
		          </td>
		        </tr>
		        
		        <tr>
		          <td><strong>Prazo de Execução de Serviço:</strong></td>
		          <td colspan="3"><html:text
								property="prazoExecucao" size="10" maxlength="4"
								onkeyup="javascript:verificaNumeroInteiro(this);"/>
		          </td>
		        </tr>
		        
		        <tr>
		          <td><strong>Número Maximo de Visitas Improdutivas Permitidas:</strong></td>
		          <td colspan="3"><html:text
								property="numeroMaximoVisitasImprodutivasPermitidas" size="10" maxlength="4"
								onkeyup="javascript:verificaNumeroInteiro(this);"/>
		          </td>
		        </tr>	
		        
		        <tr>
		          <td><strong>Número Maximo de Prestações de Guia de pagamento Permitidas:</strong></td>
		          <td colspan="3"><html:text
								property="numeroMaximoGuiaPrestacaoAntecipadaPermitidas" size="10" maxlength="3" readonly="true"
								onkeyup="javascript:verificaNumeroInteiro(this);"/>
		          </td>
		        </tr>		        	        
		        
		        <tr>
					<td><strong><span class="style2">Indicador Tipo Remuneração:<font color="#FF0000">*</font></span></strong></td>
					<td align="left" width="20%"><label> <html:radio
						property="tipoRemuneracao" value="1" /> <strong>Valor</strong></label></td>
					<td align="left"><label> <html:radio property="tipoRemuneracao" value="2" />
					<strong>Percentual</strong></label></td>
				</tr>
				
				<tr>
					<td>
						<strong><span class="style2">Layout da O.S.:<font color="#FF0000">*</font></span></strong>
					</td>
					<td colspan="3">
						<html:select property="idOrdemServicoLayout" >
							<html:option value="-1">&nbsp;</html:option>
							<html:options collection="colecaoOrdemServicoLayout" labelProperty="descricao" property="id" />
					</html:select>
					</td>
				</tr>
				
				<tr> 
					<td>
						<strong><span class="style2">Informar Deslocamento:<font color="#FF0000">*</font></span></strong>
					</td>
					<td align="left" width="20%">
						<label><html:radio property="indicadorDeslocamento" value="1" /><strong>Obrigatório</strong></label>
					</td>
					<td align="left">
						<label><html:radio property="indicadorDeslocamento" value="2" /><strong>Não informar</strong></label>
					</td>
					<td align="left">
						<label><html:radio property="indicadorDeslocamento" value="3" /><strong>Opcional</strong></label>
					</td>
				</tr>
				
				<tr> 
					<td>
						<strong><span class="style2">Informar Horários Execução:<font color="#FF0000">*</font></span></strong>
					</td>
					<td align="left" width="20%">
						<label><html:radio property="indicadorHorariosExecucao" value="1" /><strong>Obrigatório</strong></label>
					</td>
					<td align="left">
						<label><html:radio property="indicadorHorariosExecucao" value="2" /><strong>Não informar</strong></label>
					</td>
					<td align="left">
						<label><html:radio property="indicadorHorariosExecucao" value="3" /><strong>Opcional</strong></label>
					</td>
				</tr>
				
				<tr> 
					<td>
						<strong><span class="style2">Informar Causa Ocorrência:<font color="#FF0000">*</font></span></strong>
					</td>
					<td align="left" width="20%">
						<label><html:radio property="indicadorCausaOcorrencia" value="1" /><strong>Obrigatório</strong></label>
					</td>
					<td align="left">
						<label><html:radio property="indicadorCausaOcorrencia" value="2" /><strong>Não informar</strong></label>
					</td>
					<td align="left">
						<label><html:radio property="indicadorCausaOcorrencia" value="3" /><strong>Opcional</strong></label>
					</td>
				</tr>
				
				<tr> 
					<td>
						<strong><span class="style2">Informar Rede/Ramal de Água:<font color="#FF0000">*</font></span></strong>
					</td>
					<td align="left" width="20%">
						<label><html:radio property="indicadorRedeRamalAgua" value="1" /><strong>Obrigatório</strong></label>
					</td>
					<td align="left">
						<label><html:radio property="indicadorRedeRamalAgua" value="2" /><strong>Não informar</strong></label>
					</td>
					<td align="left">
						<label><html:radio property="indicadorRedeRamalAgua" value="3" /><strong>Opcional</strong></label>
					</td>
				</tr>
				
				<tr> 
					<td>
						<strong><span class="style2">Informar Rede/Ramal de Esgoto:<font color="#FF0000">*</font></span></strong>
					</td>
					<td align="left" width="20%">
						<label><html:radio property="indicadorRedeRamalEsgoto" value="1" /><strong>Obrigatório</strong></label>
					</td>
					<td align="left">
						<label><html:radio property="indicadorRedeRamalEsgoto" value="2" /><strong>Não informar</strong></label>
					</td>
					<td align="left">
						<label><html:radio property="indicadorRedeRamalEsgoto" value="3" /><strong>Opcional</strong></label>
					</td>
				</tr>
				
				<tr> 
					<td>
						<strong><span class="style2">Informar Material:<font color="#FF0000">*</font></span></strong>
					</td>
					<td align="left" width="20%">
						<label><html:radio property="indicadorMaterial" value="1" /><strong>Obrigatório</strong></label>
					</td>
					<td align="left">
						<label><html:radio property="indicadorMaterial" value="2" /><strong>Não informar</strong></label>
					</td>
					<td align="left">
						<label><html:radio property="indicadorMaterial" value="3" /><strong>Opcional</strong></label>
					</td>
				</tr>
				
				<tr> 
					<td>
						<strong><span class="style2">Informar Vala:<font color="#FF0000">*</font></span></strong>
					</td>
					<td align="left" width="20%">
						<label><html:radio property="indicadorVala" value="1" /><strong>Obrigatório</strong></label>
					</td>
					<td align="left">
						<label><html:radio property="indicadorVala" value="2" /><strong>Não informar</strong></label>
					</td>
					<td align="left">
						<label><html:radio property="indicadorVala" value="3" /><strong>Opcional</strong></label>
					</td>
				</tr>
				
				<tr>
					<td><strong><span class="style2">Indicador Ordem Seletiva:<font color="#FF0000">*</font></span></strong></td>
					<td align="left" width="20%">
						<label> <html:radio property="indicadorOrdemSeletiva" value="1"/> <strong>Sim</strong></label>
					</td>
					<td align="left">
						<label> <html:radio property="indicadorOrdemSeletiva" value="2" /> <strong>Não</strong></label>
					</td>
				</tr>
				
				<tr>
					<td><strong><span class="style2">Indicador Serviço Crítico:<font color="#FF0000">*</font></span></strong></td>
					<td align="left" width="20%">
						<label> <html:radio property="indicadorServicoCritico" value="1"/> <strong>Sim</strong></label>
					</td>
					<td align="left">
						<label> <html:radio property="indicadorServicoCritico" value="2" /> <strong>Não</strong></label>
					</td>
				</tr>
				
				<tr>
					<td><strong><span class="style2">Indicador Permitir Alterar Valor do Débito:<font color="#FF0000">*</font></span></strong></td>
					<td align="left" width="20%">
						<label> <html:radio property="indicadorPermiteAlterarValor" value="1"/> <strong>Sim</strong></label>
					</td>
					<td align="left">
						<label> <html:radio property="indicadorPermiteAlterarValor" value="2" /> <strong>Não</strong></label>
					</td>
				</tr>
				
				

				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>
				
				<!-- Atividade Única -->
				<tr>
					<td><strong><span class="style2">Atividade &Uacute;nica:<font
						color="#FF0000">*</font></span></strong></td>
					<td align="left" width="20%"><label> <html:radio
						property="indicadorAtividadeUnica" value="1"
						onclick="javascript:removeAllRowsTableAtividades();" /> <strong>Sim</strong></label>
					</td>
					<td align="left"><label> <html:radio property="indicadorAtividadeUnica"
						value="2" /> <strong>Não</strong></label></td>
				</tr>
				
				<!-- Atividades do Tipo de Serviço -->
				<tr>
					<td colspan="3"><strong> <font color="#000000">Atividades do Tipo
					de Servi&ccedil;o </font><font color="#FF0000">*</font></strong></td>
					<td align="right">
					<div align="right"><input type="button" name="Submit24"
						class="bottonRightCol" value="Adicionar"
						onclick="inserirAtividade();"></div>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<div align="left">
						<table width="100%" id="tableAtividades" align="center" bgcolor="#99CCFF">

						<!--corpo da segunda tabela-->
							<tr bordercolor="#FFFFFF" bgcolor="#79BBFD">
								<td width="14%">
									<div align="center"><strong>Remover</strong></div>
								</td>
								<td>
									<div align="center"><strong>Descri&ccedil;&atilde;o dos
									Atividades </strong></div>
								</td>
								<td>
									<div align="center"><strong>Ordem de Execu&ccedil;&atilde;o </strong></div>
								</td>
							</tr>
							
							<tbody>

							<c:forEach var="servicoTipoAtividade"
								items="${colecaoServicoTipoAtividade}"
								varStatus="i">

								<c:if test="${i.count%2 == '1'}">
									<tr bgcolor="#FFFFFF">
								</c:if>
								
								<c:if test="${i.count%2 == '0'}">
									<tr bgcolor="#cbe5fe">
								</c:if>
								
								<td>
									<div align="center">
										<a href="javascript:if(confirm('Confirma remoção?')){removeRowTableAtividades('$${servicoTipoAtividade.atividade.id}$${servicoTipoAtividade.numeroExecucao}$');}">
											<img src="imagens/Error.gif" 
												width="14" 
												height="14" 
												border="0"
												title="Remover"></a>
									</div>
								</td>
								
								<td>
									<div align="left">${servicoTipoAtividade.atividade.descricao}</div>
								</td>
								
								<td>
									<div align="center">${servicoTipoAtividade.numeroExecucao}</div>
								</td>
							</c:forEach>

							</tbody>
						</table>
					</div>
					</td>
				</tr>
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>
				<!-- Valores diferenciados por Localidade -->
				<tr>
					<td colspan="3"><strong> <font color="#000000">Valores diferenciados por Localidade </font></strong></td>
					<td align="right">
					<div align="right"><input type="button" name="Submit24"
						class="bottonRightCol" value="Adicionar"
						onclick="inserirServicoTipoValorLocalidade();"></div>
					</td>
				</tr>
				
				<tr>
					<td colspan="4"><strong> <font color="#FF0000"></font></strong>
					<div align="left">
					<table width="100%" id="tableServicoTipoValorLocalidade" align="center"
						bgcolor="#99CCFF">
						<!--corpo da segunda tabela-->
						<tr bordercolor="#FFFFFF" bgcolor="#79BBFD">
							<td width="14%">
							<div align="center"><strong>Remover</strong></div>
							</td>
							<td width="55%">
							<div align="center"><strong>Descri&ccedil;&atilde;o das
							Localidades </strong></div>
							</td>
							<td>
							<div align="center"><strong>Valor </strong></div>
							</td>
						</tr>
						<tbody>

							<c:forEach var="servicoTipoValorLocalidade"
								items="${AtualizarTipoServicoActionForm.servicoTipoValorLocalidade}"
								varStatus="i">

								<c:if test="${i.count%2 == '1'}">
									<tr bgcolor="#FFFFFF">
								</c:if>
								<c:if test="${i.count%2 == '0'}">
									<tr bgcolor="#cbe5fe">
								</c:if>
								<td>
								<div align="center">
									<a href="javascript:if(confirm('Confirma remoção?')){removeRowTableServicoTipoValorLocalidade('${servicoTipoValorLocalidade.localidade.id}');}">
										<img src="imagens/Error.gif" width="14" height="14" border="0" title="Remover"> 
									</a>
								</div>
								</td>
								<td>
								<div align="left">${servicoTipoValorLocalidade.localidade.descricao}</div>
								</td>
								<td>
									<span class="style2">  
										<input type="text" id="valorServicoLocalidadeAtual" 
											value="${servicoTipoValorLocalidade.valorServico}" 
											onkeyup="javaScript:formataValorMonetario(this, 8);"
											onblur= "atualizarColecaoServicoTipoValorLocalidade('${servicoTipoValorLocalidade.localidade.id}', this);"/>
									</span> 									
								<div align="center"></div>
								</td>
							
							</c:forEach>

						</tbody>
					</table>
					</div>
					</td>
				</tr>
				

				<!-- Materiais do Tipo de Serviço -->

				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="3"><strong> <font color="#FF0000"></font></strong>
					<div align="left"><strong><font color="#000000">Materiais </font><font
						color="#000000"> do Tipo de Servi&ccedil;o</font></strong></div>
					</td>
					<td>
					<div align="right"><input type="button" name="Submit242"
						class="bottonRightCol" value="Adicionar"
						onclick="chamarPopup('exibirPesquisarServicoTipoMaterialAction.do?limpar=S', 'servicoTipoMaterial', null, null, 300, 620, '','');">
					</div>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<div align="left">
						<table width="100%" id="tableMateriais" align="center" bgcolor="#99CCFF">

						<!--corpo da segunda tabela-->
						<tr bordercolor="#FFFFFF" bgcolor="#79BBFD">
							<td width="14%">
								<div align="center"><strong>Remover</strong></div>
							</td>
							
							<td width="55%">
								<div align="center"><strong>Descri&ccedil;&atilde;o dos Materiais
							</strong></div>
							</td>
							
							<td width="31%">
							<div align="center"><strong>Quantidade Padr&atilde;o </strong></div>
							</td>
						</tr>
						
						<tbody>

							<c:forEach var="servicoTipoMaterial"
								items="${colecaoServicoTipoMaterial}"
								varStatus="i">
						
								<c:if test="${i.count%2 == '1'}">
									<tr bgcolor="#FFFFFF">
								</c:if>
						
								<c:if test="${i.count%2 == '0'}">
									<tr bgcolor="#cbe5fe">
								</c:if>
						
								<td>
									<div align="center">
										<a href="javascript:if(confirm('Confirma remoção?')){removeRowTableMateriais('$${servicoTipoMaterial.material.id}$');}">
											<img src="imagens/Error.gif" 
												width="14" 
												height="14" 
												border="0"
												title="Remover"> </a></div>
								</td>
								
								<td>
									<div align="left">${servicoTipoMaterial.material.descricao}</div>
								</td>

								<td>
									<div align="center">${servicoTipoMaterial.quantidadePadrao}</div>
								</td>
							</c:forEach>
						</tbody>
					</table>
					</div>
					</td>
				</tr>
				
				<!-- Serviços Associados -->

				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="3"><strong> <font color="#FF0000"></font></strong>
						<div align="left"><strong><font color="#000000">Serviços Associados</font></strong></div>
					</td>
					<td>
					 <div align="right">
						<input type="button" name="Submit3" class="bottonRightCol" value="Adicionar" onclick="abrirPopup('exibirPesquisarServicoTipoAssociadoAction.do?operacao=adicionar', 'servicoAssociado', null, null, 600, 680, '','');">
					 </div>
					</td>
				</tr>
				<tr>
					<td colspan="4"><strong> <font color="#FF0000"></font></strong>
					<div align="left">
					<table width="100%" id="tableServicosAssociados" align="center" bgcolor="#99CCFF">
						<!--corpo da terceira tabela-->
						<tr bordercolor="#FFFFFF" bgcolor="#79BBFD">
							<td width="14%">
							 <div align="center"><strong>Remover</strong></div>
							</td>
							<td width="14%">
								<div align="center"><strong>Editar</strong></div>
							</td>
							<td width="14%">
							 <div align="center"><strong>Código</strong></div>
							</td>
							<td width="40%">
							 <div align="center"><strong>Descrição</strong></div>
							</td>
							<td width="18%">
							 <div align="center"><strong>Ordem de Execução</strong></div>
							</td>
						</tr>
						<tbody>
					<% int cont = 0; %>
						<logic:present name="colecaoServicoAssociado">
							<logic:iterate name="colecaoServicoAssociado" id="servicoAssociado" type="ServicoAssociado">
							
							<%cont = cont + 1;
							if (cont % 2 == 0) {%>
							<tr bgcolor="#cbe5fe">
							<%} else {%>
							<tr bgcolor="#FFFFFF">
							<%}%>
								<td align="center" width="10%" valign="middle">
								      <a href="javascript:removeRowTableAssociadas('${servicoAssociado.servicoTipoAssociado.id}')">
									   <img src="<bean:message key='caminho.imagens'/>Error.gif" border="0">
									</a>
								</td>
								<td>
									<div align="center">
										<a href="javascript: abrirPopup('exibirPesquisarServicoTipoAssociadoAction.do?operacao=editar&idServicoTipoAssociado=${servicoAssociado.servicoTipoAssociado.id}', 'servicoAssociado', null, null, 620, 620, '','');">
											<html:img src="imagens/16x_editar.gif" border="0" title="Editar"/>
										</a>
									</div>
								</td>
								<td>
									<div align="center">${servicoAssociado.servicoTipoAssociado.id}</div>
								</td>
								<td>
									<div align="left">${servicoAssociado.servicoTipoAssociado.descricao}</div>
								</td>
								<td>
									<div align="center">${servicoAssociado.sequencial}</div>
								</td>
							</logic:iterate>
						</logic:present>
						</tbody>
					</table>
					</div>
					</td>
				</tr>

				<!-- Trâmite -->
				<logic:present name="indicadorParametroTramite" scope="session">
					<tr>
						<td colspan="4">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="3"><strong><font color="#FF0000"></font></strong>
							<div align="left"><strong><font color="#000000">Trâmite</font></strong></div>
						</td>
						<td>
						 <div align="right">
							<input type="button" name="Submit243" class="bottonRightCol" value="Adicionar" onclick="abrirPopup('exibirPesquisarServicoTipoTramiteAction.do?operacao=adicionar', 'servicoTipoTramite', null, null, 635, 370, '','');">
						 </div>
						</td>
					</tr>
					<tr>
						<td colspan="4"><strong> <font color="#FF0000"></font></strong>
						<div align="left">
						<table width="100%" id="tableServicoTipoTramite" align="center" bgcolor="#99CCFF">
							<!--corpo da quarta tabela-->
							<tr bordercolor="#FFFFFF" bgcolor="#79BBFD">
								<td width="6%">
								 <div align="center"><strong>Remover</strong></div>
								</td>
								<td width="10%">
									<div align="center"><strong>Editar</strong></div>
								</td>
								<td width="12%">
								 <div align="center"><strong>Localidade</strong></div>
								</td>
								<td width="12%">
								 <div align="center"><strong>Setor Comercial</strong></div>
								</td>
								<td width="12%">
								 <div align="center"><strong>Unidade Origem</strong></div>
								</td>
								<td width="12%">
								 <div align="center"><strong>Unidade Destino</strong></div>
								</td>
								<td width="16%">
								 <div align="center"><strong>Bairro</strong></div>
								</td>
								<td width="12%">
								 <div align="center"><strong>Primeiro Tramite</strong></div>
								</td>
							</tr>
							<tbody>
						    <% cont = 0; %>
							<logic:present name="colecaoServicoTipoTramite">
								<logic:iterate name="colecaoServicoTipoTramite" id="servicoTipoTramite" type="ServicoTipoTramite">
								
								<%cont = cont + 1;
								if (cont % 2 == 0) {%>
								<tr bgcolor="#cbe5fe">
								<%} else {%>
								<tr bgcolor="#FFFFFF">
								<%}%>
									<td align="center" width="12%" valign="middle">
									    <a href="javascript:removeRowTableServicoTipoTramite('${servicoTipoTramite.id}')">
										   <img src="<bean:message key='caminho.imagens'/>Error.gif" border="0">
										</a>
									</td>
									<td>
										<div align="center">
											<a href="javascript: abrirPopup('exibirPesquisarServicoTipoTramiteAction.do?operacao=editar&idServicoTipoTramite=${servicoTipoTramite.id}', 'servicoTipoTramite', null, null, 635, 370, '','');">
												<html:img src="imagens/16x_editar.gif" border="0" title="Editar"/>
											</a>
										</div>
									</td>
									<td>
										<logic:notEmpty name="servicoTipoTramite" property="localidade">
											<div align="center" title="${servicoTipoTramite.localidade.descricao}">${servicoTipoTramite.localidade.id}</div>
										</logic:notEmpty>
									</td>
									<td>
										<logic:notEmpty name="servicoTipoTramite" property="setorComercial">
											<div align="center" title="${servicoTipoTramite.setorComercial.descricao}">${servicoTipoTramite.setorComercial.codigo}</div>
										</logic:notEmpty>
									</td>
									<td>
										<logic:notEmpty name="servicoTipoTramite" property="unidadeOrganizacionalOrigem">
											<div align="center" title="${servicoTipoTramite.unidadeOrganizacionalOrigem.descricao}">${servicoTipoTramite.unidadeOrganizacionalOrigem.id}</div>
										</logic:notEmpty>
									</td>
									<td>
										<logic:notEmpty name="servicoTipoTramite" property="unidadeOrganizacionalDestino">
											<div align="center" title="${servicoTipoTramite.unidadeOrganizacionalDestino.descricao}">${servicoTipoTramite.unidadeOrganizacionalDestino.id}</div>
										</logic:notEmpty>
									</td>
									<td>
										<logic:notEmpty name="servicoTipoTramite" property="bairro">
											<div align="center" title="${servicoTipoTramite.bairro.nome}">${servicoTipoTramite.bairro.nome}</div>
										</logic:notEmpty>
									</td>
									<td>
										<logic:notEmpty name="servicoTipoTramite" property="indicadorPrimeiroTramite">
											<div align="center" title="${servicoTipoTramite.indicadorPrimeiroTramite}">
														
											
											<logic:equal name="servicoTipoTramite" property="indicadorPrimeiroTramite" value="1">
												SIM
											</logic:equal>
											<logic:equal name="servicoTipoTramite" property="indicadorPrimeiroTramite" value="2">	
											    NÃO
											</logic:equal>
											</div>
										</logic:notEmpty>
									</td>
								</logic:iterate>
							</logic:present>
							</tbody>
						</table>
						</div>
						</td>
					</tr>
				</logic:present>

				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>

				<!-- Botões -->

				<tr>
					<td align="left">
					<input type="button" 
							name="ButtonReset"
							class="bottonRightCol" 
							value="Desfazer"
							onClick="window.location.href='<html:rewrite page="/exibirAtualizarTipoServicoAction.do?desfazer=S&idServico=${sessionScope.idServico}"/>'">
						<input type="button"
						name="ButtonCancelar" class="bottonRightCol" value="Cancelar"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
					</td>
					<td colspan="3" align="right"><input name="Button" type="button"
						class="bottonRightCol" value="Atualizar" onclick="validaForm();"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>

</html:form>
</html:html>
