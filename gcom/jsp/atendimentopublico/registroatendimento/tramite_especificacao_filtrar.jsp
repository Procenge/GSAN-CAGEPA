<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@page import="gcom.util.ConstantesSistema"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css"	type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<script type='text/javascript' src='<c:out value='${pageContext.request.contextPath}'/>/dwr/engine.js'> </script>
<script type='text/javascript' src='<c:out value='${pageContext.request.contextPath}'/>/dwr/util.js'> </script>
<script type='text/javascript' src='<c:out value='${pageContext.request.contextPath}'/>/dwr/interface/AjaxService.js'> </script>

<html:javascript staticJavascript="false" formName="FiltrarTramiteEspecificacaoActionForm" />

<script language="JavaScript">
	function validarForm() {
		var form = document.forms[0];
		form.action = 'filtrarTramiteEspecificacaoAction.do';
		submeterFormPadrao(form);
	}

	function carregarCombo(obj, tipoObj) {
		var id = obj.options[obj.selectedIndex].value;

		if (id != "-1") {
			if (tipoObj == 'solicitacaoTipo') {

			    carregarComboGenerico(obj, 'gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao', 'solicitacaoTipo.id', document.forms[0].idSolicitacaoTipoEspecificacao, 'descricaoComId', 'colecaoSolicitacaoTipoEspecificacaoFiltro');

			} 
			// recarrega o formulário para atualizar os comboBoxes
			document.forms[0].action = 'exibirFiltrarTramiteEspecificacaoAction.do';
			submeterFormPadrao(document.forms[0]);
		} else {
			if (tipoObj == 'solicitacaoTipo') {

			    carregarComboGenerico(obj, 'gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao', null, document.forms[0].idSolicitacaoTipoEspecificacao, 'descricaoComId', 'colecaoSolicitacaoTipoEspecificacaoFiltro');

			}
			// recarrega o formulário para atualizar os comboBoxes
			document.forms[0].action = 'exibirFiltrarTramiteEspecificacaoAction.do';
			submeterFormPadrao(document.forms[0]);
		}	
	}

	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
		var form = document.forms[0];

		if (tipoConsulta == 'localidade') {
			if(form.idLocalidade.value != codigoRegistro) {
				limparPesquisaSetorComercial();
			}

			form.idLocalidade.value = codigoRegistro;
			form.descricaoLocalidade.value = descricaoRegistro;
			form.descricaoLocalidade.style.color = "#000000";
		} else if (tipoConsulta == 'setorComercial') {
			form.codigoSetorComercial.value = codigoRegistro;
			form.descricaoSetorComercial.value = descricaoRegistro;
			form.descricaoSetorComercial.style.color = "#000000";
		} else if (tipoConsulta == 'municipio') {
			if(form.idMunicipio.value != codigoRegistro) {
				limparPesquisaBairro();
			}

			form.idMunicipio.value = codigoRegistro;
			form.nomeMunicipio.value = descricaoRegistro;
			form.nomeMunicipio.style.color = "#000000";

			form.codigoBairro.focus();
		} else if (tipoConsulta == 'bairro') {
			form.codigoBairro.value = codigoRegistro;
			form.nomeBairro.value = descricaoRegistro;
			form.nomeBairro.style.color = "#000000";
		} else if (tipoConsulta == 'unidadeOrganizacional') {
			if (form.campoUnidade.value == '1') {
				form.idUnidadeOrganizacionalOrigem.value = codigoRegistro;
				form.descricaoUnidadeOrganizacionalOrigem.value = descricaoRegistro;
				form.descricaoUnidadeOrganizacionalOrigem.style.color = "#000000";
			} else if (form.campoUnidade.value == '2') {
				form.idUnidadeOrganizacionalDestino.value = codigoRegistro;
				form.descricaoUnidadeOrganizacionalDestino.value = descricaoRegistro;
				form.descricaoUnidadeOrganizacionalDestino.style.color = "#000000";
			}
		}
	}

	function limparPesquisaLocalidade() {
		var form = document.forms[0];
		form.idLocalidade.value = "";
		form.descricaoLocalidade.value = "";
	}

	function limparPesquisaDescricaoLocalidade() {
		var form = document.forms[0];
		form.descricaoLocalidade.value = "";
	}

	function limparPesquisaSetorComercial() {
		var form = document.forms[0];
		form.codigoSetorComercial.value = "";
		form.descricaoSetorComercial.value = "";
	}

	function limparDescricaoSetorComercial() {
		var form = document.forms[0];
		form.descricaoSetorComercial.value = "";
	}

	function limparPesquisaMunicipio() {
		var form = document.forms[0];
		form.idMunicipio.value = "";
		form.nomeMunicipio.value = "";
	}

	function limparPesquisaDescricaoMunicipio() {
		var form = document.forms[0];
		form.nomeMunicipio.value = "";
	}

	function limparPesquisaBairro() {
		var form = document.forms[0];
		form.codigoBairro.value = "";
		form.nomeBairro.value = "";
	}

	function limparNomeBairro() {
		var form = document.forms[0];
		form.nomeBairro.value = "";
	}

	function limparPesquisaUnidadeOrganizacionalOrigem() {
		var form = document.forms[0];
		form.idUnidadeOrganizacionalOrigem.value = "";
		form.descricaoUnidadeOrganizacionalOrigem.value = "";
	}

	function limparPesquisaDescricaoUnidadeOrganizacionalOrigem() {
		var form = document.forms[0];
		form.descricaoUnidadeOrganizacionalOrigem.value = "";
	}

	function limparPesquisaUnidadeOrganizacionalDestino() {
		var form = document.forms[0];
		form.idUnidadeOrganizacionalDestino.value = "";
		form.descricaoUnidadeOrganizacionalDestino.value = "";
	}

	function limparPesquisaDescricaoUnidadeOrganizacionalDestino() {
		var form = document.forms[0];
		form.descricaoUnidadeOrganizacionalDestino.value = "";
	}

	function setaUnidade(unidade) {
		var form = document.forms[0];
		form.campoUnidade.value = unidade;
	}
</script>

</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');">

<html:form action="/filtrarTramiteEspecificacaoAction"
	name="FiltrarTramiteEspecificacaoActionForm"
	type="gcom.gui.atendimentopublico.registroatendimento.FiltrarTramiteEspecificacaoActionForm"
	method="post">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<input type="hidden" name="campoUnidade">

	<table width="770" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="115" valign="top" class="leftcoltext">
			<div align="center">

			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>

			<%@ include file="/jsp/util/informacoes_usuario.jsp"%>

			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>

			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>

			<%@ include file="/jsp/util/mensagens.jsp"%>

			<p align="left">&nbsp;</p>

			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>

			</div>
			</td>
			<td width="600" valign="top" bgcolor="#003399" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
					<td class="parabg">Filtrar Trâmite por Especificação</td>
					<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
				</tr>
				<tr>
					<td height="5" colspan="3"></td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td width="80%" colspan="2">Para filtrar um trâmite por especificação, informe os dados abaixo:</td>
					<td width="20%" align="right"><html:checkbox property="atualizar" value="true"/><strong>Atualizar</strong></td>
				</tr>
				<input type="hidden" name="atualizar" value="false">

				<tr>
					<td></td>
				</tr>
				<tr>
					<td width="28%"><strong>Tipo de Solicitação:</strong></td>
					<td colspan="2">
						<html:select property="idSolicitacaoTipo" tabindex="1" style="width: 350px;" onchange="carregarCombo(this, 'solicitacaoTipo');">
							<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<logic:present name="colecaoSolicitacaoTipoFiltro">
								<html:options collection="colecaoSolicitacaoTipoFiltro" labelProperty="descricaoComId" property="id"/>
							</logic:present>
						</html:select>
					</td>
				</tr>
				<tr>
					<td width="28%"><strong>Especificação:</strong></td>
					<td colspan="2">
						<html:select property="idSolicitacaoTipoEspecificacao" style="width: 350px;" tabindex="2">
							<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<logic:present name="colecaoSolicitacaoTipoEspecificacaoFiltro">
								<html:options collection="colecaoSolicitacaoTipoEspecificacaoFiltro" labelProperty="descricaoComId" property="id"/>
							</logic:present>
						</html:select>
					</td>
				</tr>
				<tr>
					<td width="28%"><strong>Localidade:</strong></td>
					<td height="24" colspan="2">
						<html:text maxlength="3" property="idLocalidade" size="3" tabindex="3"
							onkeypress="javascript:limparPesquisaDescricaoLocalidade();limparPesquisaSetorComercial();validaEnter(event, 'exibirFiltrarTramiteEspecificacaoAction.do?objetoConsulta=1', 'idLocalidade');return isCampoNumerico(event);"/>
						<a href="javascript:abrirPopup('exibirPesquisarLocalidadeAction.do?tipo=tramiteEspecificacao', 400, 800);limparPesquisaSetorComercial();">
						<img border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/></a>

						<logic:present name="localidadeNaoEncontrada" scope="request">
							<input type="text" name="descricaoLocalidade" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" value="<bean:message key="atencao.localidade.inexistente"/>"/>
						</logic:present>

						<logic:notPresent name="localidadeNaoEncontrada" scope="request">
							<html:text property="descricaoLocalidade" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
						</logic:notPresent>

						<a href="javascript:limparPesquisaLocalidade();limparPesquisaSetorComercial();document.forms[0].idLocalidade.focus();">
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar"/></a>
					</td>
				</tr>
				<tr>
					<td width="28%"><strong>Setor Comercial:</strong></td>
					<td height="24" colspan="2">
						<html:text maxlength="3" property="codigoSetorComercial" size="3" tabindex="4"
							onkeypress="javascript:limparDescricaoSetorComercial();validaEnterDependencia(event, 'exibirFiltrarTramiteEspecificacaoAction.do?objetoConsulta=2', this, document.forms[0].idLocalidade.value, 'Localidade');return isCampoNumerico(event);"/>
						<a href="javascript:abrirPopupDependencia('exibirPesquisarSetorComercialAction.do?idLocalidade='+document.forms[0].idLocalidade.value+'&tipo=tramiteEspecificacao',document.forms[0].idLocalidade.value,'Localidade', 400, 800);">
						<img border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/></a>

						<logic:present name="setorComercialNaoEncontrado" scope="request">
							<input type="text" name="descricaoSetorComercial" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" value="<bean:message key="atencao.setor_comercial.inexistente"/>"/>
						</logic:present>

						<logic:notPresent name="setorComercialNaoEncontrado" scope="request">
							<html:text property="descricaoSetorComercial" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
						</logic:notPresent>
					
						<a href="javascript:limparPesquisaSetorComercial();document.forms[0].codigoSetorComercial.focus();">
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar"/></a>
					</td>
				</tr>
				<tr>
					<td width="28%"><strong>Município:</strong></td>
					<td height="24" colspan="2">
						<html:text maxlength="4" property="idMunicipio" size="3" tabindex="5"
							onkeypress="javascript:limparPesquisaDescricaoMunicipio();limparPesquisaBairro();validaEnter(event, 'exibirFiltrarTramiteEspecificacaoAction.do?objetoConsulta=3', 'idMunicipio');return isCampoNumerico(event);"/>
						<a href="javascript:abrirPopup('exibirPesquisarMunicipioAction.do', 275, 480);limparPesquisaBairro();">
						<img border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/></a>

						<logic:present name="municipioNaoEncontrado" scope="request">
							<input type="text" name="nomeMunicipio" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" value="<bean:message key="atencao.municipio.inexistente"/>"/>
						</logic:present>

						<logic:notPresent name="municipioNaoEncontrado" scope="request">
							<html:text property="nomeMunicipio" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
						</logic:notPresent>

						<a href="javascript:limparPesquisaMunicipio();limparPesquisaBairro();document.forms[0].idMunicipio.focus();">
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar"/></a>
					</td>
				</tr>
				<tr>
					<td width="28%"><strong>Bairro:</strong></td>
					<td height="24" colspan="2">
						<html:text maxlength="4" property="codigoBairro" size="3" tabindex="6"
							onkeypress="javascript:limparNomeBairro();validaEnterDependencia(event, 'exibirFiltrarTramiteEspecificacaoAction.do?objetoConsulta=4', this, document.forms[0].idMunicipio.value, 'Município');return isCampoNumerico(event);"/>
						<a href="javascript:abrirPopupDependencia('exibirPesquisarBairroAction.do?idMunicipio='+document.forms[0].idMunicipio.value+'&indicadorUsoTodos=1', document.forms[0].idMunicipio.value, 'Município', 275, 480);">
						<img border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/></a>

						<logic:present name="bairroNaoEncontrado" scope="request">
							<input type="text" name="nomeBairro" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" value="<bean:message key="atencao.bairro.inexistente"/>"/>
						</logic:present>

						<logic:notPresent name="bairroNaoEncontrado" scope="request">
							<html:text property="nomeBairro" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
						</logic:notPresent>

						<a href="javascript:limparPesquisaBairro();document.forms[0].codigoBairro.focus();">
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar"/></a>
					</td>
				</tr>
			
				<tr>
					<td width="28%"><strong>Unidade Origem:</strong></td>
					<td height="24" colspan="2">
						<html:text maxlength="8" property="idUnidadeOrganizacionalOrigem" size="6" tabindex="15"
							onkeypress="javascript:limparPesquisaDescricaoUnidadeOrganizacionalOrigem();validaEnter(event, 'exibirFiltrarTramiteEspecificacaoAction.do?objetoConsulta=5', 'idUnidadeOrganizacionalOrigem');return isCampoNumerico(event);"/>
						<a href="javascript:abrirPopup('exibirPesquisarUnidadeOrganizacionalAction.do?indicadorUsoTodos=1&limpaForm=sim', 275, 480);setaUnidade(1);">
						<img border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/></a>

						<logic:present name="unidadeOrganizacionalOrigemNaoEncontrado" scope="request">
							<input type="text" name="descricaoUnidadeOrganizacionalOrigem" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" value="<bean:message key="atencao.unidadeOrganizacional.inexistente"/>"/>
						</logic:present>

						<logic:notPresent name="unidadeOrganizacionalOrigemNaoEncontrado" scope="request">
							<html:text property="descricaoUnidadeOrganizacionalOrigem" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
						</logic:notPresent>

						<a href="javascript:limparPesquisaUnidadeOrganizacionalOrigem();document.forms[0].idUnidadeOrganizacionalOrigem.focus();">
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar"/></a>
					</td>
				</tr>
				<tr>
					<td width="28%"><strong>Unidade Destino:</strong></td>
					<td height="24" colspan="2">
						<html:text maxlength="8" property="idUnidadeOrganizacionalDestino" size="6" tabindex="16"
							onkeypress="javascript:limparPesquisaDescricaoUnidadeOrganizacionalDestino();validaEnter(event, 'exibirFiltrarTramiteEspecificacaoAction.do?objetoConsulta=6', 'idUnidadeOrganizacionalDestino');return isCampoNumerico(event);"/>
						<a href="javascript:abrirPopup('exibirPesquisarUnidadeOrganizacionalAction.do?indicadorUsoTodos=1&limpaForm=sim', 275, 480);setaUnidade(2);">
						<img border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/></a>

						<logic:present name="unidadeOrganizacionalDestinoNaoEncontrado" scope="request">
							<input type="text" name="descricaoUnidadeOrganizacionalDestino" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" value="<bean:message key="atencao.unidadeOrganizacional.inexistente"/>"/>
						</logic:present>

						<logic:notPresent name="unidadeOrganizacionalDestinoNaoEncontrado" scope="request">
							<html:text property="descricaoUnidadeOrganizacionalDestino" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
						</logic:notPresent>

						<a href="javascript:limparPesquisaUnidadeOrganizacionalDestino();document.forms[0].idUnidadeOrganizacionalDestino.focus();">
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar"/></a>
					</td>
				</tr>
				
				
				
				<tr>
					<td height="30"><strong>Unidade do Primeiro Trâmite? :<font color="#FF0000">*</font></strong></td>
					<td>
						<html:radio property="indicadorPrimeiroTramite" value="<%="" + ConstantesSistema.SIM.toString()%>"/><strong>SIM&nbsp;
						<html:radio property="indicadorPrimeiroTramite" value="<%="" + ConstantesSistema.NAO.toString()%>"/>NÃO</strong>
					</td>
				</tr>
				
				<tr>
					<td colspan="3">&nbsp;</td>
				</tr>
				
				
				
			</table>
			<table width="100%" border="0">
				<tr>
					<td valign="top"><input name="button" type="button"
						class="bottonRightCol" value="Limpar"
						onclick="window.location.href='/gsan/exibirFiltrarTramiteEspecificacaoAction.do?limpar=S'">&nbsp;</td>
					<td valign="top">
					<div align="right"><input name="button" type="button"
						class="bottonRightCol" value="Filtrar"
						onclick="javascript:validarForm();" tabindex="17"></div>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>

</body>
</html:html>
