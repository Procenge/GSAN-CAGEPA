<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@page import="gcom.util.ConstantesSistema"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<script type='text/javascript' src='<c:out value='${pageContext.request.contextPath}'/>/dwr/engine.js'> </script>
<script type='text/javascript' src='<c:out value='${pageContext.request.contextPath}'/>/dwr/util.js'> </script>
<script type='text/javascript' src='<c:out value='${pageContext.request.contextPath}'/>/dwr/interface/AjaxService.js'> </script>

<html:javascript staticJavascript="false" formName="AtualizarTramiteEspecificacaoActionForm" />

<script language="JavaScript">
	function validarForm(formulario) {
		if (validateAtualizarTramiteEspecificacaoActionForm(formulario)) {
			formulario.action = "/gsan/atualizarTramiteEspecificacaoAction.do";
			submeterFormPadrao(formulario);
		}
	}

	function carregarCombo(obj, tipoObj) {
		var id = obj.options[obj.selectedIndex].value;

		if (id != "-1") {
			if (tipoObj == 'solicitacaoTipo') {

			    carregarComboGenerico(obj, 'gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao', 'solicitacaoTipo.id', document.forms[0].idSolicitacaoTipoEspecificacao, 'descricaoComId', 'colecaoSolicitacaoTipoEspecificacao');

			} else if (tipoObj == 'sistemaAbastecimento') {

			    carregarComboGenerico(obj, 'gcom.operacional.DistritoOperacional', 'sistemaAbastecimento.id', document.forms[0].idDistritoOperacional, 'descricaoComId', 'colecaoDistritoOperacional');
				limparComboGenerico(document.forms[0].idZonaAbastecimento);
			    limparComboGenerico(document.forms[0].idSetorAbastecimento);

			} else if (tipoObj == 'distritoOperacional') {

				carregarComboGenerico(obj, 'gcom.operacional.ZonaAbastecimento', 'distritoOperacional.id', document.forms[0].idZonaAbastecimento, 'descricaoComCodigo', 'colecaoZonaAbastecimento');
				limparComboGenerico(document.forms[0].idSetorAbastecimento);

			} else if (tipoObj == 'zonaAbastecimento') {

				carregarComboGenerico(obj, 'gcom.operacional.SetorAbastecimento', 'zonaAbastecimento.id', document.forms[0].idSetorAbastecimento, 'descricaoComCodigo', 'colecaoSetorAbastecimento');

			} else if (tipoObj == 'sistemaEsgoto') {

			    carregarComboGenerico(obj, 'gcom.operacional.SubsistemaEsgoto', 'sistemaEsgoto.id', document.forms[0].idSubsistemaEsgoto, 'descricaoComCodigo', 'colecaoSubsistemaEsgoto');
				limparComboGenerico(document.forms[0].idBacia);
			    limparComboGenerico(document.forms[0].idSubBacia);

			} else if (tipoObj == 'subsistemaEsgoto') {

				carregarComboGenerico(obj, 'gcom.operacional.Bacia', 'subsistemaEsgoto.id', document.forms[0].idBacia, 'descricaoComCodigoEId', 'colecaoBacia');
				limparComboGenerico(document.forms[0].idSubBacia);

			} else if (tipoObj == 'bacia') {

				carregarComboGenerico(obj, 'gcom.operacional.SubBacia', 'bacia.id', document.forms[0].idSubBacia, 'descricaoComCodigo', 'colecaoSubBacia');

			}
		} else {
			if (tipoObj == 'solicitacaoTipo') {

			    carregarComboGenerico(obj, 'gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao', null, document.forms[0].idSolicitacaoTipoEspecificacao, 'descricaoComId', 'colecaoSolicitacaoTipoEspecificacao');

			} else if (tipoObj == 'sistemaAbastecimento') {

				carregarComboGenerico(obj, 'gcom.operacional.DistritoOperacional', null, document.forms[0].idDistritoOperacional, 'descricaoComId', 'colecaoDistritoOperacional');
				carregarComboGenerico(obj, 'gcom.operacional.ZonaAbastecimento', null, document.forms[0].idZonaAbastecimento, 'descricaoComCodigo', 'colecaoZonaAbastecimento');
				carregarComboGenerico(obj, 'gcom.operacional.SetorAbastecimento', null, document.forms[0].idSetorAbastecimento, 'descricaoComCodigo', 'colecaoSetorAbastecimento');

			} else if (tipoObj == 'distritoOperacional') {

				if (document.forms[0].idSistemaAbastecimento.value == '-1') {
					carregarComboGenerico(obj, 'gcom.operacional.ZonaAbastecimento', null, document.forms[0].idZonaAbastecimento, 'descricaoComCodigo', 'colecaoZonaAbastecimento');
					carregarComboGenerico(obj, 'gcom.operacional.SetorAbastecimento', null, document.forms[0].idSetorAbastecimento, 'descricaoComCodigo', 'colecaoSetorAbastecimento');
				} else {
					limparComboGenerico(document.forms[0].idZonaAbastecimento);
					limparComboGenerico(document.forms[0].idSetorAbastecimento);
				}

			} else if (tipoObj == 'zonaAbastecimento') {

				if (document.forms[0].idSistemaAbastecimento.value == '-1' && document.forms[0].idDistritoOperacional.value == '-1') {
					carregarComboGenerico(obj, 'gcom.operacional.SetorAbastecimento', null, document.forms[0].idSetorAbastecimento, 'descricaoComCodigo', 'colecaoSetorAbastecimento');
				} else {
					limparComboGenerico(document.forms[0].idSetorAbastecimento);
				}

			} else if (tipoObj == 'sistemaEsgoto') {

				carregarComboGenerico(obj, 'gcom.operacional.SubsistemaEsgoto', null, document.forms[0].idSubsistemaEsgoto, 'descricaoComCodigo', 'colecaoSubsistemaEsgoto');
				carregarComboGenerico(obj, 'gcom.operacional.Bacia', null, document.forms[0].idBacia, 'descricaoComCodigoEId', 'colecaoBacia');
				carregarComboGenerico(obj, 'gcom.operacional.SubBacia', null, document.forms[0].idSubBacia, 'descricaoComCodigo', 'colecaoSubBacia');

			} else if (tipoObj == 'subsistemaEsgoto') {

				if (document.forms[0].idSistemaEsgoto.value == '-1') {
					carregarComboGenerico(obj, 'gcom.operacional.Bacia', null, document.forms[0].idBacia, 'descricaoComCodigoEId', 'colecaoBacia');
					carregarComboGenerico(obj, 'gcom.operacional.SubBacia', null, document.forms[0].idSubBacia, 'descricaoComCodigo', 'colecaoSubBacia');
				} else {
					limparComboGenerico(document.forms[0].idBacia);
					limparComboGenerico(document.forms[0].idSubBacia);
				}

			} else if (tipoObj == 'bacia') {

				if (document.forms[0].idSistemaEsgoto.value == '-1' && document.forms[0].idSubsistemaEsgoto.value == '-1') {
					carregarComboGenerico(obj, 'gcom.operacional.SubBacia', null, document.forms[0].idSubBacia, 'descricaoComCodigo', 'colecaoSubBacia');
				} else {
					limparComboGenerico(document.forms[0].idSubBacia);
				}

			}
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
</SCRIPT>

</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');">

<html:form action="/exibirAtualizarTramiteEspecificacaoAction"
	name="AtualizarTramiteEspecificacaoActionForm"
	type="gcom.gui.operacional.bacia.AtualizarTramiteEspecificacaoActionForm"
	method="post">

	<html:hidden property="id"/>

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp" %>

	<input type="hidden" name="campoUnidade">

	<table width="770" border="0" cellspacing="5" cellpadding="0">
		<tr>
	    	<td width="130" valign="top" class="leftcoltext">
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
			<td width="625" valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
					<td class="parabg">Atualizar Trâmite por Especificação</td>
					<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para alterar o trâmite por especificação, informe os dados abaixo:</td>
					<td align="right"></td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td width="28%"><strong>Tipo de Solicitação:<font color="#FF0000">*</font></strong></td>
					<td colspan="2">
						<html:text size="3" tabindex="1" property="idSolicitacaoTipo" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>&nbsp;&nbsp;
						<html:text property="descricaoSolicitacaoTipo" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
					</td>
				</tr>
				<tr>
					<td width="28%"><strong>Especificação:<font color="#FF0000">*</font></strong></td>
					<td colspan="2">
						<html:text size="3" tabindex="2" property="idSolicitacaoTipoEspecificacao" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>&nbsp;&nbsp;
						<html:text property="descricaoSolicitacaoTipoEspecificacao" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
					</td>
				</tr>
				<tr>
					<td width="28%"><strong>Localidade:</strong></td>
					<td height="24" colspan="2">
						<html:text maxlength="3" property="idLocalidade" size="3" tabindex="3"
							onkeypress="javascript:limparPesquisaDescricaoLocalidade();limparPesquisaSetorComercial();validaEnter(event, 'exibirAtualizarTramiteEspecificacaoAction.do?objetoConsulta=1', 'idLocalidade');return isCampoNumerico(event);"/>
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
							onkeypress="javascript:limparDescricaoSetorComercial();validaEnterDependencia(event, 'exibirAtualizarTramiteEspecificacaoAction.do?objetoConsulta=2', this, document.forms[0].idLocalidade.value, 'Localidade');return isCampoNumerico(event);"/>
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
							onkeypress="javascript:limparPesquisaDescricaoMunicipio();limparPesquisaBairro();validaEnter(event, 'exibirAtualizarTramiteEspecificacaoAction.do?objetoConsulta=3', 'idMunicipio');return isCampoNumerico(event);"/>
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
							onkeypress="javascript:limparNomeBairro();validaEnterDependencia(event, 'exibirAtualizarTramiteEspecificacaoAction.do?objetoConsulta=4', this, document.forms[0].idMunicipio.value, 'Município');return isCampoNumerico(event);"/>
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
					<td width="28%"><strong>Sistema de Abastecimento:</strong></td>
					<td colspan="2">
						<html:select property="idSistemaAbastecimento" tabindex="7" style="width: 220px;" onchange="carregarCombo(this, 'sistemaAbastecimento');">
							<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<logic:present name="colecaoSistemaAbastecimento">
								<html:options collection="colecaoSistemaAbastecimento" labelProperty="descricaoComCodigo" property="id"/>
							</logic:present>
						</html:select>
					</td>
				</tr>
				<tr>
					<td width="28%"><strong>Distrito Operacional:</strong></td>
					<td colspan="2">
						<html:select property="idDistritoOperacional" tabindex="8" style="width: 220px;" onchange="carregarCombo(this, 'distritoOperacional');">
							<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<logic:present name="colecaoDistritoOperacional">
								<html:options collection="colecaoDistritoOperacional" labelProperty="descricaoComId" property="id"/>
							</logic:present>
						</html:select>
					</td>
				</tr>
				<tr>
					<td width="28%"><strong>Zona de Abastecimento:</strong></td>
					<td colspan="2">
						<html:select property="idZonaAbastecimento" tabindex="9" style="width: 220px;" onchange="carregarCombo(this, 'zonaAbastecimento');">
							<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<logic:present name="colecaoZonaAbastecimento">
								<html:options collection="colecaoZonaAbastecimento" labelProperty="descricaoComCodigo" property="id"/>
							</logic:present>
						</html:select>
					</td>
				</tr>
				<tr>
					<td width="28%"><strong>Setor de Abastecimento:</strong></td>
					<td colspan="2">
						<html:select property="idSetorAbastecimento" tabindex="10" style="width: 220px;">
							<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<logic:present name="colecaoSetorAbastecimento">
								<html:options collection="colecaoSetorAbastecimento" labelProperty="descricaoComCodigo" property="id"/>
							</logic:present>
						</html:select>
					</td>
				</tr>
				<tr>
					<td width="28%"><strong>Sistema de Esgoto:</strong></td>
					<td colspan="2">
						<html:select property="idSistemaEsgoto" tabindex="11" style="width: 220px;" onchange="carregarCombo(this, 'sistemaEsgoto');">
							<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<logic:present name="colecaoSistemaEsgoto">
								<html:options collection="colecaoSistemaEsgoto" labelProperty="descricaoComId" property="id"/>
							</logic:present>
						</html:select>
					</td>
				</tr>
				<tr>
					<td width="28%"><strong>Subsistema de Esgoto:</strong></td>
					<td colspan="2">
						<html:select property="idSubsistemaEsgoto" tabindex="12" style="width: 220px;" onchange="carregarCombo(this, 'subsistemaEsgoto');">
							<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<logic:present name="colecaoSubsistemaEsgoto">
								<html:options collection="colecaoSubsistemaEsgoto" labelProperty="descricaoComCodigo" property="id"/>
							</logic:present>
						</html:select>
					</td>
				</tr>
				<tr>
					<td width="28%"><strong>Bacia:</strong></td>
					<td colspan="2">
						<html:select property="idBacia" tabindex="13" style="width: 220px;" onchange="carregarCombo(this, 'bacia');">
							<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<logic:present name="colecaoBacia">
								<html:options collection="colecaoBacia"	labelProperty="descricaoComCodigoEId" property="id"/>
							</logic:present>
						</html:select>
					</td>
				</tr>
				<tr>
					<td width="28%"><strong>Subbacia:</strong></td>
					<td colspan="2">
						<html:select property="idSubBacia" tabindex="14" style="width: 220px;">
							<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<logic:present name="colecaoSubBacia">
								<html:options collection="colecaoSubBacia" labelProperty="descricaoComCodigo" property="id"/>
							</logic:present>
						</html:select>
					</td>
				</tr>
				<tr>
					<td width="28%"><strong>Unidade Origem:</strong></td>
					<td height="24" colspan="2">
						<html:text maxlength="8" property="idUnidadeOrganizacionalOrigem" size="6" tabindex="15"
							onkeypress="javascript:limparPesquisaDescricaoUnidadeOrganizacionalOrigem();validaEnter(event, 'exibirAtualizarTramiteEspecificacaoAction.do?objetoConsulta=5', 'idUnidadeOrganizacionalOrigem');return isCampoNumerico(event);"/>
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
					<td width="28%"><strong>Unidade Destino:<font color="#FF0000">*</font></strong></td>
					<td height="24" colspan="2">
						<html:text maxlength="8" property="idUnidadeOrganizacionalDestino" size="6" tabindex="16"
							onkeypress="javascript:limparPesquisaDescricaoUnidadeOrganizacionalDestino();validaEnter(event, 'exibirAtualizarTramiteEspecificacaoAction.do?objetoConsulta=6', 'idUnidadeOrganizacionalDestino');return isCampoNumerico(event);"/>
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
					<td height="30"><strong>Indicador de Uso:<font color="#FF0000">*</font></strong></td>
					<td>
						<html:radio property="indicadorUso" value="<%="" + ConstantesSistema.INDICADOR_USO_ATIVO.toString()%>"/><strong>Ativo&nbsp;
						<html:radio property="indicadorUso" value="<%="" + ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>"/>Inativo</strong>
					</td>
				</tr>
				<tr>
					<td><strong><font color="#FF0000"></font></strong></td>
					<td align="right">
						<div align="left"><strong><font color="#FF0000">*</font></strong>Campos obrigat&oacute;rios</div>
					</td>
				</tr>
				<tr>
					<td colspan="2" widt="100%">
					<table width="100%">
						<tr>
							<td>
				      			<logic:present name="voltar">
									<input name="Button" type="button" class="bottonRightCol" value="Voltar" align="left"
										onclick="window.location.href='<html:rewrite page="/exibirFiltrarTramiteEspecificacaoAction.do"/>'">
								</logic:present>
								<logic:notPresent name="voltar">
									<input name="Button" type="button" class="bottonRightCol" value="Voltar" align="left"
										onclick="window.location.href='<html:rewrite page="/exibirManterTramiteEspecificacaoAction.do"/>'">
								</logic:notPresent>
								<input name="Submit22" class="bottonRightCol" value="Desfazer" type="button"
									onclick="window.location.href='/gsan/exibirAtualizarTramiteEspecificacaoAction.do?desfazer=S';">
								<input name="Submit23" class="bottonRightCol" value="Cancelar" type="button"
									onclick="window.location.href='/gsan/telaPrincipal.do'">
		      				</td>
		      				<td align="right">
		      					<gcom:controleAcessoBotao name="Button" value="Atualizar" onclick="javascript:validarForm(document.forms[0]);" url="atualizarTramiteEspecificacaoAction.do"/>
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
