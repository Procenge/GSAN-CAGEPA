<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
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
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="GerarRelatorioSituacaoEspecialFaturamentoActionForm"
	dynamicJavascript="true" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript">
	function validarForm(form) {
		if (validateGerarRelatorioSituacaoEspecialFaturamentoActionForm(form)) {
			submeterFormPadrao(form);
		}
	}

	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	    var form = document.GerarRelatorioSituacaoEspecialFaturamentoActionForm;

	    if (tipoConsulta == 'setorComercial') {
	      	form.codigoSetorComercial.value = codigoRegistro;
	    	form.descricaoSetorComercial.value = descricaoRegistro;
			form.descricaoSetorComercial.style.color = "#000000";
	    }
	    
	    if (tipoConsulta == 'localidade') {
	      	form.idLocalidade.value = codigoRegistro;
	    	form.descricaoLocalidade.value = descricaoRegistro;
			form.descricaoLocalidade.style.color = "#000000";
	    }

	    if (tipoConsulta == 'quadra') {
	        limparQuadra();
	        form.numeroQuadra.value = codigoRegistro;
	    }
	}

	function recuperarDadosQuatroParametros(idRegistro, descricaoRegistro, codigoRegistro, tipoConsulta) {
		var form = document.forms[0];

		if (tipoConsulta == 'rota') {
			form.codigoRota.value = codigoRegistro;
			form.descricaoRota.value = descricaoRegistro;
			form.descricaoRota.style.color = "#000000";
		}
	}

	function limparLocalidade(){
		var form = document.forms[0];

		form.idLocalidade.value = '';
		form.descricaoLocalidade.value = '';
		
		limparSetor();
	}

	function limparSetor(){
		var form = document.forms[0];

		form.codigoSetorComercial.value = '';
		form.descricaoSetorComercial.value = '';

		limparQuadra();
		limparRota();
	}

	function limparDescricaoQuadra(){
		var msgQuadra = document.getElementById("msgQuadra");
		
		if (msgQuadra != null){
			msgQuadra.innerHTML = "";
		}
	}

	function limparQuadra() {
	    var form = document.forms[0];

        form.numeroQuadra.value = "";

        limparDescricaoQuadra();
	}

	function limparRota() {
		var form = document.forms[0];

		form.codigoRota.value = "";   
		form.descricaoRota.value = "";
	}

</script>

</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('${requestScope.nomeCampo}');">

	<html:form action="/gerarRelatorioSituacaoEspecialFaturamentoAction"
		name="GerarRelatorioSituacaoEspecialFaturamentoActionForm"
		type="gcom.gui.relatorio.faturamento.GerarRelatorioSituacaoEspecialFaturamentoActionForm"
		method="post">

		<%@ include file="/jsp/util/cabecalho.jsp"%>
		<%@ include file="/jsp/util/menu.jsp"%>

		<table width="770" border="0" cellspacing="4" cellpadding="0">
			<tr>
				<td width="149" valign="top" class="leftcoltext">
					<div align="center">
						<%@ include file="/jsp/util/informacoes_usuario.jsp"%>

						<%@ include file="/jsp/util/mensagens.jsp"%>
					</div>
				</td>
				<td width="625" valign="top" bgcolor="#003399" class="centercoltext">
					<table height="100%">

						<tr>
							<td></td>
						</tr>
					</table>
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td width="11"><img border="0"
								src="imagens/parahead_left.gif" /></td>
							<td class="parabg">Relatório Situação Especial de
								Faturamento</td>
							<td width="11" valign="top"><img border="0"
								src="imagens/parahead_right.gif" /></td>
						</tr>
					</table>
					<p>&nbsp;</p>
					<table width="100%" border="0">
						<tr>
							<td colspan="2">Para gerar o relatório, informe os dados abaixo:</td>
						</tr>
						<tr>
							<td width="30%"><strong>Referência do Faturamento:<font	color="#FF0000">*</font></strong></td>
							<td><html:text property="mesAnoFaturamento" size="7" maxlength="7" onkeyup="mascaraAnoMes(this, event);"/>&nbsp;mm/aaaa</td>
						</tr>

						<tr>
							<td><strong>Localidade:</strong></td>
							<td>
								<html:text maxlength="3" 
									tabindex="1"
									property="idLocalidade" 
									size="3"
									onkeypress="validaEnterComMensagem(event, 'exibirGerarRelatorioSituacaoEspecialFaturamentoAction.do?objetoConsulta=1','idLocalidade','Localidade');"/>
									
								<a href="javascript:abrirPopup('exibirPesquisarLocalidadeAction.do?tipo=localidade&indicadorUsoTodos=1', 400, 800);limparLocalidade();">
									<img width="23" 
										height="21" 
										border="0" 
										src="<bean:message key="caminho.imagens"/>pesquisa.gif"
										title="Pesquisar Localidade" /></a>
		
								<logic:present name="localidadeEncontrada" scope="request">
									<html:text property="descricaoLocalidade" 
										size="30"
										maxlength="30" 
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:present> 
		
								<logic:notPresent name="localidadeEncontrada" scope="request">
									<html:text property="descricaoLocalidade" 
										size="30"
										maxlength="30" 
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: red" />
								</logic:notPresent>
								
								<a href="javascript:limparLocalidade();"> 
									<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
										border="0" 
										title="Apagar" /></a>
							</td>
						</tr>

						<tr>
							<td><strong>Setor Comercial:</strong></td>
							<td>
								<html:text maxlength="3" 
									tabindex="1"
									property="codigoSetorComercial" 
									size="3"
									onkeypress="javascript:validaEnterComMensagem(event, 'exibirGerarRelatorioSituacaoEspecialFaturamentoAction.do?objetoConsulta=2','codigoSetorComercial','Setor Comercial');"/>
									
								<a href="javascript:limparSetor();abrirPopup('exibirPesquisarSetorComercialAction.do?idLocalidade='+document.forms[0].idLocalidade.value+'&tipo=setorComercial', 400, 800);">
									<img width="23" 
										height="21" 
										border="0" 
										src="<bean:message key="caminho.imagens"/>pesquisa.gif"
										title="Pesquisar Setor Comercial" /></a>
										
		
								<logic:present name="setorComercialEncontrado" scope="request">
									<html:text property="descricaoSetorComercial" 
										size="30"
										maxlength="30" 
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:present> 
		
								<logic:notPresent name="setorComercialEncontrado" scope="request">
									<html:text property="descricaoSetorComercial" 
										size="30"
										maxlength="30" 
										readonly="true"
										style="background-color:#EFEFEF; border:0; color: red" />
								</logic:notPresent>
								
								<a href="javascript:limparSetor();"> 
									<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
										border="0" 
										title="Apagar" /></a>
							</td>
						</tr>

						<tr>
							<td><strong>Quadra:</strong></td>
							<td colspan="2" height="24"><html:text maxlength="5"
								property="numeroQuadra" tabindex="3" size="4"
								onkeypress="javascript:limparDescricaoQuadra();return validaEnterDependencia(event, 'exibirGerarRelatorioSituacaoEspecialFaturamentoAction.do?objetoConsulta=3', this, document.forms[0].codigoSetorComercial.value, 'Setor Comercial');" />
							      <a href="javascript:abrirPopupDependencia('exibirPesquisarQuadraAction.do?idLocalidade='+document.forms[0].idLocalidade.value+'&codigoSetorComercial='+document.forms[0].codigoSetorComercial.value+'&tipo=Quadra',document.forms[0].codigoSetorComercial.value,'Setor Comercial', 400, 800);">
						          	<img border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/>
						          </a>                 

							<logic:present name="codigoQuadraNaoEncontrada" scope="request">
								<span style="color:#ff0000" id="msgQuadra"><bean:write
									scope="request" name="msgQuadra" /></span>
							</logic:present> <logic:notPresent name="codigoQuadraNaoEncontrada"
								scope="request">
							</logic:notPresent>
							<a href="javascript:limparQuadra();document.forms[0].numeroQuadra.focus();"> <img
									 src="<bean:message key="caminho.imagens"/>limparcampo.gif"
									 border="0" title="Apagar" />
								 </a>
							</td>
						</tr>

						<tr>
							<td><strong>Rota:</strong></td>
							<td colspan="2" height="24"><html:text
									maxlength="5" property="codigoRota" size="4"
									onkeypress="javascript:return validaEnterDependencia(event, 'exibirGerarRelatorioSituacaoEspecialFaturamentoAction.do?objetoConsulta=4', this, document.forms[0].codigoSetorComercial.value, 'Setor Comercial');" />
								<a
								href="javascript:abrirPopupDependencia('exibirPesquisarRotaAction.do?&tipo=rota&idLocalidade='+document.forms[0].idLocalidade.value+'&codigoSetorComercial='+document.forms[0].codigoSetorComercial.value+'&restringirPesquisa=true',document.forms[0].codigoSetorComercial.value,'Setor Comercial', 400, 800);">
									<img border="0"
									src="<bean:message key="caminho.imagens"/>pesquisa.gif"
									border="0" />
							</a> <logic:notPresent name="rotaEncontrada" scope="request">
									<html:text property="descricaoRota" size="40" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:notPresent> <logic:present name="rotaEncontrada" scope="request">
									<html:text property="descricaoRota" size="40" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:present> <a
								href="javascript:limparRota(); document.forms[0].codigoRota.focus();">
									<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
									border="0" title="Apagar" />
							</a></td>
						</tr>


						<tr>
							<td width="30%"><strong>Tipo da Situação Especial de Faturamento:</strong></td>
							<td>
								<html:select property="idFaturamentoSituacaoTipo">
									<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
									<html:options collection="colecaoFaturamentoSituacaoTipo" labelProperty="descricao" property="id" />
								</html:select>
							</td>
						</tr>

						<tr>
							<td width="30%"><strong>Motivo da Situação Especial de Faturamento:</strong></td>
							<td>
								<html:select property="idFaturamentoSituacaoMotivo">
									<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
									<html:options collection="colecaoFaturamentoSituacaoMotivo" labelProperty="descricao" property="id" />
								</html:select>
							</td>
						</tr>

						<tr>
							<td>
								<p>&nbsp;</p>
							</td>
						</tr>
						<tr>
							<td><strong> <font color="#FF0000"></font>
							</strong></td>
							<td colspan="3" align="right">
								<div align="left">
									<strong> <font color="#FF0000">*</font>
									</strong> Campos obrigat&oacute;rios
								</div>
							</td>
						</tr>
						<tr>
							<td><input name="Submit22" class="bottonRightCol"
								value="Limpar" type="button"
								onclick="window.location.href='/gsan/exibirGerarRelatorioSituacaoEspecialFaturamentoAction.do?menu=sim';">
							<td colspan="2" align="right"><gcom:controleAcessoBotao
									name="Button" value="Gerar"
									onclick="javascript:validarForm(document.forms[0]);"
									url="gerarRelatorioSituacaoEspecialFaturamentoAction.do" /></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

		<%@ include file="/jsp/util/rodape.jsp"%>

	</html:form>
</body>
</html:html>
