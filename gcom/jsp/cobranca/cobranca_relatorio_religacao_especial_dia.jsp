<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="gcom.util.ConstantesSistema"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ include file="/jsp/util/titulo.jsp"%>
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
	
<script>
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
    	var form = document.forms[0];
       	form.idUnidade.value = codigoRegistro;
      	form.nomeUnidade.value = descricaoRegistro;
	}

	function validaCamposObrigatorios(){
		var periodoInicio = $("input[name=periodoInicio]").val();
		var periodoFim = $("input[name=periodoFim]").val();
		if($.trim(periodoInicio) == ""){
		   alert("Periodo início é obrigatório");
		   return false;
		}
		if($.trim(periodoFim) == ""){
		   alert("Periodo fim é obrigatório");
		   return false;
		}

		var tipoServico =  $("select#servicoTipo").val();
		
		if(tipoServico=="-1" || tipoServico==null || ""==tipoServico){
		   alert("Tipo de serviço é obrigatório");
		   return false;
		}
		
		var form = document.forms[0];

		if($("select[name=setor]").val() == "-1" || $("select[name=setor]").val() == null || "" == $("select[name=setor]").val()){
			form.setorSelecionado.value = null;
		} else {
			form.setorSelecionado.value = $("select[name=setor]").val();
		}

		if($("select[name=bairro]").val() == "-1" || $("select[name=bairro]").val() == null || "" == $("select[name=bairro]").val()){
			form.bairrosSelecionado.value = null;
		} else {
			form.bairrosSelecionado.value = $("select[name=bairro]").val();
		}

		if($("select[name=servicoTipo]").val() == "-1" || $("select[name=servicoTipo]").val() == null || "" == $("select[name=servicoTipo]").val()){
			form.servicoTipoSelecionado.value = null;
		} else {
			form.servicoTipoSelecionado.value = $("select[name=servicoTipo]").val();
		}
		
		form.submit();
	}

	function limparUnidade() {
		$("input[name=idUnidade]").val("");
		$("input[name=nomeUnidade]").val("");
	}

	function limparCampos(){
		$("input[name=idUnidade]").val("");
		$("input[name=periodoInicio]").val("");
		$("input[name=periodoFim]").val("");
		$("input[name=nomeUnidade]").val("");
		$("select[name=grupo]").val("");
		$("select[name=setor]").val("");
		$("select[name=bairro]").val("");
		$("select[name=servicoTipo]").val("");
		$("select[name=bairrosSelecionado]").val("");
		$("select[name=setorSelecionado]").val("");
		$("select[name=servicoTipoSelecionado]").val("");
	}
	
</script>
	
</head>
<body>
<html:form action="/relatorioReligacaoEspecialDiaAction"
	name="relacaoReligacaoEspecialDiaForm"
	type="org.apache.struts.action.DynaActionForm">
	<html:hidden property="acao" value="gerarRelatorioImoveisReligacaoEspecial" />
	<html:hidden property="limpar" value="false" />
	<html:hidden property="bairrosSelecionado" />
	<html:hidden property="setorSelecionado" />
	<html:hidden property="servicoTipoSelecionado" />

	<table width="100%" border="0">
		<tr>
			<td width="200"><strong>Unidade Organizacional:</strong></td>
			<td colspan="2">
				<html:text maxlength="4" property="idUnidade" tabindex="1" size="4" onkeypress="javascript:validaEnterComMensagem(event, 'relatorioReligacaoEspecialDiaAction.do?acao=exibirFiltrarRelacaoImovelEspecialDia&limpar=false', 'idUnidade', 'Unidade Organizacional');" />
				<a href="javascript:abrirPopup('exibirPesquisarUnidadeOrganizacionalAction.do');">
					<img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" title="Pesquisar Unidade Organizacional" />
				</a> 
				
				<logic:present name="idUnidadeEncontrada" scope="request">
					<logic:equal name="idUnidadeEncontrada" scope="request" value="true">
						<html:text maxlength="30" property="nomeUnidade" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" size="40" />
					</logic:equal>
					<logic:equal name="idUnidadeEncontrada" scope="request" value="exception">
						<html:text maxlength="30" property="nomeUnidade" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" size="40" />
					</logic:equal>
				</logic:present>
				<logic:notPresent name="idUnidadeEncontrada" scope="request">
					<html:text maxlength="30" property="nomeUnidade" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" size="40" />
				</logic:notPresent>
				
				<a href="javascript:limparUnidade();">
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
				</a>
			</td>
		</tr>
		<tr id="calendarioInicio">
			<td><strong>Período Inicio:<font color="red">*</font></strong></td>
			<td><html:text maxlength="10" property="periodoInicio" size="10" onkeypress="return isCampoNumerico(event);" onkeyup="javascript:mascaraData(this,event);" /> 
			<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif"
				onclick="abrirCalendario('relacaoReligacaoEspecialDiaForm', 'periodoInicio')"
				width="20" border="0" align="absmiddle" alt="Exibir Calendário" />
			dd/mm/aaaa</td>
		</tr>
		<tr id="calendarioFim">
			<td><strong>Período Fim:<font color="red">*</font></strong></td>
			<td colspan="2"><html:text maxlength="10" property="periodoFim"
				size="10" onkeypress="return isCampoNumerico(event);" onkeyup="javascript:mascaraData(this,event);" /> <img
				border="0" src="<bean:message key='caminho.imagens'/>calendario.gif"
				onclick="abrirCalendario('relacaoReligacaoEspecialDiaForm', 'periodoFim')"
				width="20" border="0" align="absmiddle" alt="Exibir Calendário" />
			dd/mm/aaaa</td>
		</tr>
		<tr>
			<td align="left"><strong>Grupo:</strong></td>
			<td align="left">
				<html:select property="grupo">
					<logic:present name="colecaoCobrancaGrupo" scope="request">
						<logic:notEmpty name="colecaoCobrancaGrupo" scope="request">
							<html:option value="-1">&nbsp;</html:option>
							<html:options collection="colecaoCobrancaGrupo" property="id" labelProperty="descricao" />
						</logic:notEmpty>
					</logic:present>
				</html:select>
			</td>
		</tr>
			<td align="left"><strong>Setor:</strong></td>
			<td align="left">
				<html:select property="setor" multiple="mutiple" style="width: 280px; height: 100px" size="6">
					<logic:present name="colecaoSetorComercial" scope="request">
						<logic:notEmpty name="colecaoSetorComercial" scope="request">
							<html:option value="" />
							<html:options collection="colecaoSetorComercial" property="id" labelProperty="codigo" />
						</logic:notEmpty>
					</logic:present>
				</html:select>
			</td>
		</tr>
		<tr>
			<td align="left"><strong>Bairro:</strong></td>
			<td align="left">
				<html:select property="bairro" multiple="mutiple" style="width: 280px; height: 100px" size="6">
						<logic:present name="colecaoBairro" scope="request">
							<logic:notEmpty name="colecaoBairro" scope="request">
								<html:option value="" />
								<html:options collection="colecaoBairro" property="id" labelProperty="nome" />
							</logic:notEmpty>
						</logic:present>
					</html:select>
			</td>
		</tr>
			<td align="left"><strong>Tipo de Serviço:<font color="red">*</font></strong></td>
			<td align="left">
				<html:select styleId="servicoTipo" property="servicoTipo" name="relacaoReligacaoEspecialDiaForm" style="width: 280px; height: 100px" multiple="mutiple" size="6">
					<logic:present name="colecaoServicoTipo" scope="request">
						<logic:notEmpty name="colecaoServicoTipo" scope="request">
							<html:option value="" />
							<html:options collection="colecaoServicoTipo" labelProperty="descricao" property="id" />
						</logic:notEmpty>
					</logic:present>
				</html:select>
			</td>
		</tr>
		<tr>
		  <td height="35" colspan="2"></td>
		</tr>
		<tr>
		  <td>
		      <input type="button" value="Voltar" onclick="javascript:history.back();" class="bottonRightCol"/>
		      <input type="button" value="Desfazer" class="bottonRightCol" onclick="limparCampos();"/>
		  </td>
		  <td align="right">
			  <input type="button" onclick="javascript:validaCamposObrigatorios();" value="Gerar Relatório" class="bottonRightCol"/>		  
		  </td>
		</tr>
	</table>
</html:form>
</body>
</html>