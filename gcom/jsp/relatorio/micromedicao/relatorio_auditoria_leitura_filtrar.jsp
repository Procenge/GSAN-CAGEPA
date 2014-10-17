<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
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
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="GerarRelatorioContasEmitidasDynaForm" />
<script>
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro,
			tipoConsulta) {

		var form = document.forms[0];

		if (tipoConsulta == 'localidade') {
			form.localidadeID.value = codigoRegistro;
			form.descricaoLocalidade.value = descricaoRegistro;
			form.descricaoLocalidade.style.color = "#000000";

			form.setorComercialCD.focus();
		}

		if (tipoConsulta == 'setorComercial') {

			form.setorComercialCD.value = codigoRegistro;
			form.descricaoSetorComercial.value = descricaoRegistro;
			form.descricaoSetorComercial.style.color = "#000000";
			form.cdRota.focus();

		}
		if (tipoConsulta == 'rota') {

			form.cdRota.value = codigoRegistro;
			form.descricaoRota.value = descricaoRegistro;
			form.descricaoRota.style.color = "#000000";

		}
	}

	function validarForm() {

		var form = document.forms[0];

		if (form.P_GP_FATURAMENTO.value != null
				&& form.P_GP_FATURAMENTO.value != -1) {

			form.submit();
		}

	}

	function limparPesquisaDescricaoRota() {
		var form = document.forms[0];
		//     form.idRota.value = "";   
		form.descricaoRota.value = "";
	}

	function validarCampos() {

	}

	function recuperarDadosQuatroParametros(idRegistro, descricaoRegistro,
			codigoRegistro, tipoConsulta) {

		var form = document.forms[0];
		if (tipoConsulta == 'rota') {
			form.cdRota.value = codigoRegistro;
			form.descricaoRota.value = descricaoRegistro;
			form.descricaoRota.style.color = "#000000";

		}
	}

	function limpar() {

		var formulario = document.forms[0];
		limparLocalidade();
		formulario.P_GP_FATURAMENTO.value = '-1';
		
	}
	
	function limparLocalidade(){
		var formulario = document.forms[0];
		formulario.descricaoLocalidade.value = '';
		formulario.localidadeID.value = '';
		limparSetor();
	}
	
	function limparSetor(){
		var formulario = document.forms[0];
		formulario.descricaoSetorComercial.value = '';
		formulario.setorComercialCD.value = '';
		formulario.setorComercialID.value = '';
		limparRota();
	}
	function limparRota(){
		var formulario = document.forms[0];
		formulario.cdRota.value = '';
		formulario.descricaoRota.value = '';
	}
	

	function chamaPopupSetor() {
		var form = document.FiltrarMensagemContaActionForm;

			abrirPopupDependencia(
				'exibirPesquisarSetorComercialAction.do?idLocalidade='
						+ document.forms[0].localidadeID.value
						+ '&tipo=SetorComercial',
				document.forms[0].localidadeID.value, 'Localidade', 400, 800);
	}
</script>


</head>

<body leftmargin="5" topmargin="5">
	<html:form action="/gerarRelatorioAuditoriaLeituraAction.do"
		name="GerarRelatorioAuditoriaLeituraDynaForm"
		type="org.apache.struts.action.DynaActionForm" method="post">

		<%@ include file="/jsp/util/cabecalho.jsp"%>
		<%@ include file="/jsp/util/menu.jsp"%>

		<table width="770" border="0" cellpadding="0" cellspacing="5">
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
				<td valign="top" class="centercoltext">
					<table height="100%">
						<tr>
							<td></td>
						</tr>
					</table>
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td width="11"><img
								src="<bean:message key="caminho.imagens"/>parahead_left.gif"
								border="0" /></td>
							<td class="parabg">Relatório de Auditoria de leitura</td>
							<td width="11" valign="top"><img
								src="<bean:message key="caminho.imagens"/>parahead_right.gif"
								border="0" /></td>
						</tr>
					</table>
					<p>&nbsp;</p>
					<table width="100%" border="0">
						<tr>
							<td colspan="2">Para gerar o relatório de Auditoria de
								Leitura, informe os dados abaixo:</td>
						</tr>

						<tr>
							<td width="35%"><strong>Grupo de Faturamento:<font
									color="#FF0000">*</font></strong></td>
							<td><strong><strong> <html:select
											property="P_GP_FATURAMENTO">
											<html:option
												value="<%=""
									+ ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
											<html:options collection="colecaoGrupoFaturamento"
												labelProperty="descricao" property="id" />
										</html:select>
								</strong></strong></td>
						</tr>
						<tr>
							<td><strong>Localidade:</strong></td>
							<td valign="top"><html:text property="localidadeID"
									size="4" maxlength="3"
									onfocus="limparLocalidade();"
									onkeypress="validaEnterComMensagem(event, 'exibirGerarRelatorioAuditoriaLeituraAction.do?consultaLocalidade=1', 'localidadeID', 'Localidade');" />
								<a
								href="javascript:abrirPopup('exibirPesquisarLocalidadeAction.do', 250, 495);">
									<img src="<bean:message key="caminho.imagens"/>pesquisa.gif"
									border="0" width="23" height="21" title="Pesquisar">
							</a> <logic:present name="corLocalidade">
									<logic:equal name="corLocalidade" value="exception">
										<html:text property="descricaoLocalidade" size="45"
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: #ff0000" />
									</logic:equal>
									<logic:notEqual name="corLocalidade" value="exception">
										<html:text property="descricaoLocalidade" size="45"
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: #000000" />
									</logic:notEqual>
								</logic:present> <logic:notPresent name="corLocalidade">
									<logic:empty name="GerarRelatorioAuditoriaLeituraDynaForm"
										property="localidadeID">
										<html:text property="descricaoLocalidade" size="45" value=""
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: #ff0000" />
									</logic:empty>
									<logic:notEmpty name="GerarRelatorioAuditoriaLeituraDynaForm"
										property="localidadeID">
										<html:text property="descricaoLocalidade" size="45"
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: #000000" />
									</logic:notEmpty>
								</logic:notPresent> <a href="javascript:limparLocalidade();"> <img
									src="<bean:message key="caminho.imagens"/>limparcampo.gif"
									border="0" title="Apagar" /></a></td>
						</tr>
						<tr>
							<td><strong>Setor Comercial:</strong></td>
							<td><html:text property="setorComercialCD" size="5"
									onfocus="limparSetor();"
									maxlength="3" tabindex="2"
									onkeypress="validaEnterDependenciaComMensagem(event, 'exibirGerarRelatorioAuditoriaLeituraAction.do?consultaSetorComercial=1', document.forms[0].setorComercialCD, document.forms[0].localidadeID.value, 'Localidade','Setor Comercial');" />

								<a href="javascript:chamaPopupSetor();"> <img width="23"
									height="21" border="0"
									src="<bean:message key="caminho.imagens"/>pesquisa.gif"
									title="Pesquisar Setor Comercial" /></a> <logic:present
									name="corSetorComercial">

									<logic:equal name="corSetorComercial" value="exception">
										<html:text property="descricaoSetorComercial" size="45"
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: #ff0000" />
									</logic:equal>

									<logic:notEqual name="corSetorComercial" value="exception">
										<html:text property="descricaoSetorComercial" size="45"
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: #000000" />
									</logic:notEqual>

								</logic:present> <logic:notPresent name="corSetorComercial">

									<logic:empty name="GerarRelatorioAuditoriaLeituraDynaForm"
										property="setorComercialCD">
										<html:text property="descricaoSetorComercial" value=""
											size="45" readonly="true"
											style="background-color:#EFEFEF; border:0; color: #ff0000" />
									</logic:empty>
									<logic:notEmpty name="GerarRelatorioAuditoriaLeituraDynaForm"
										property="setorComercialCD">
										<html:text property="descricaoSetorComercial" size="45"
											readonly="true"
											style="background-color:#EFEFEF; border:0; color: #000000" />
									</logic:notEmpty>

								</logic:notPresent> <a href="javascript:limparSetor();"> <img
									src="<bean:message key="caminho.imagens"/>limparcampo.gif"
									border="0" title="Apagar" />
							</a> <html:hidden property="setorComercialID" /></td>
						</tr>
						<tr>
							<td height="24"><strong>Rota:</strong></td>
							<td width="81%" height="24" colspan="2"><html:text
									onfocus="limparRota();"
									maxlength="5" property="cdRota" size="5" tabindex="7"
									onkeypress="javascript: limparPesquisaDescricaoRota(); return validaEnter(event, 'exibirGerarRelatorioAuditoriaLeituraAction.do?consultaRota=1', 'cdRota');" />
								<a
								href="javascript:abrirPopupDependencia('exibirPesquisarRotaAction.do?&tipo=rota&idLocalidade='+document.forms[0].localidadeID.value+'&codigoSetorComercial='+document.forms[0].setorComercialCD.value+'&restringirPesquisa=true',document.forms[0].setorComercialCD.value,'Setor Comercial', 400, 800);">
									<img border="0"
									src="<bean:message key="caminho.imagens"/>pesquisa.gif"
									border="0" />
							</a> <logic:present name="rotaNaoEncontrada" scope="request">
									<html:text property="descricaoRota" size="55" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:present> <logic:notPresent name="rotaNaoEncontrada" scope="request">
									<html:text property="descricaoRota" size="55" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notPresent> <a
								href="javascript:limparRota(); document.forms[0].cdRota.focus();">
									<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
									border="0" title="Apagar" />
							</a></td>
						</tr>
					</table>
					<table width="100%">
						<tr>
							<td height="24"><input type="button" class="bottonRightCol"
								value="Limpar" onclick="javascript:limpar()" /></td>

							<td align="right"><input type="button" name="Button"
								class="bottonRightCol" value="Gerar"
								onClick="javascript:validarForm();"
								url="gerarRelatorioAuditoriaLeituraAction.do" /></td>

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
