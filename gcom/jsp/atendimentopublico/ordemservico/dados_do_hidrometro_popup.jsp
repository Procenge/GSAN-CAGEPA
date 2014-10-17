<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gcom.util.ConstantesSistema"%>
<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<html:javascript staticJavascript="false" formName="DadosDoHidrometroPopupActionForm" />

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript">
	function validarForm(form) {
		if (!VerificaIntervalosPreenchidos(form.intervaloInstalacaoInicial,
				form.intervaloInstalacaoFinal,
				'Verifique o preenchimento do Período de Instalação')) {

			return false;
		}
		if (!verificaAnoMesMensagemPersonalizada(form.intervaloInstalacaoInicial,
				"Mês/Ano Inicial do Período de Instalação Inválido.",false)) {
			return false;
		}

		if (!verificaAnoMesMensagemPersonalizada(form.intervaloInstalacaoFinal,
				"Mês/Ano Final do Período de Instalação Inválido.",false)) {
			return false;
		}

		if (comparaMesAno(form.intervaloInstalacaoInicial.value, ">", form.intervaloInstalacaoFinal.value)){
			alert("Mês/Ano Final do Período de Instalação é anterior ao Mês/Ano Inicial do Período de Instalação.");
			form.intervaloInstalacaoFinal.focus();
			return false;
		}

		form.descricaoHidrometroDiametro.value = form.idHidrometroDiametro.options[form.idHidrometroDiametro.selectedIndex].text;
		form.descricaoHidrometroCapacidade.value = form.idHidrometroCapacidade.options[form.idHidrometroCapacidade.selectedIndex].text;

		document.forms[0].target = '';
    	form.submit();
	}

	function duplicarIntervaloInstalacao() {
		var formulario = document.forms[0]; 
		formulario.intervaloInstalacaoFinal.value = formulario.intervaloInstalacaoInicial.value;
	}

	function VerificaIntervalosPreenchidos(interInicial, interFinal, msg) {
		if(interInicial.value != '' && interFinal.value == '') {
			alert(msg);
			interFinal.focus();
			return false;
		}else if (interInicial.value == '' && interFinal.value != '') {
			alert(msg);
			interInicial.focus();
			return false;
		}
		
		return true;
	}
</script>

</head>

<logic:present name="closePage" scope="session">
	<logic:equal name="closePage" value="S" scope="session">
		<body leftmargin="5" topmargin="5" onload="chamarReload('filtrarImovelEmissaoOrdensSeletivasWizardAction.do?action=exibirFiltrarImovelEmissaoOrdensSeletivasHidrometro');window.close();">
	</logic:equal>
</logic:present>

<logic:notPresent name="closePage" scope="session">
	<body leftmargin="5" topmargin="5">
</logic:notPresent>

<html:form action="/inserirDadosDoHidrometroPopupAction" method="post"
	onsubmit="return validateDadosDoHidrometroPopupActionForm(this);">
	<table width="600" border="0" cellpadding="0" cellspacing="5">
		<tr>
			<td width="600" valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Adicionar Dados do Hidrômetro para filtro do imóvel</td>
					<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>Preencha os campos de dados do hidrômetro para filtro do imóvel</td>
					<td align="right"></td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td width="120"><strong>Diâmetro:</strong></td>
					<td align="left">
						<table width="100%" border="0">
							<tr>
								<td>
									<strong>
										<html:select property="idHidrometroDiametro">
											<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
											<html:options collection="colecaoHidrometroDiametro"
												labelProperty="descricao" property="id" />
										</html:select>
									</strong>
									<html:hidden property="descricaoHidrometroDiametro"/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td width="160"><strong>Capacidade:</strong></td>
					<td align="left">
						<table width="100%" border="0">
							<tr>
								<td>
									<strong>
										<html:select property="idHidrometroCapacidade" style="width: 230px;">
											<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
											<html:options collection="colecaoHidrometroCapacidade"
												labelProperty="descricao" property="id" />
										</html:select>
									</strong>
									<html:hidden property="descricaoHidrometroCapacidade"/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table border="0">
							<tr>
								<td width="160"><strong>Período de instalação:</strong></td>
								<td align="left"><html:text property="intervaloInstalacaoInicial"
									size="7" maxlength="7" onkeyup="duplicarIntervaloInstalacao(); mascaraAnoMes(this, event);"></html:text></td>
								<td>&nbsp;a&nbsp;</td>
								<td align="left"><html:text property="intervaloInstalacaoFinal" size="7" maxlength="7" onkeyup="mascaraAnoMes(this, event);"></html:text></td>
								<td>(mm/aaaa)</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table border="0">
							<tr>
								<td width="160"><strong>Leitura Acumulada:</strong></td>
								<td align="left"><html:text property="numeroLeituraAcumulada" size="6" maxlength="6" onkeypress="return isCampoNumerico(event);"></html:text></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td height="24" colspan="2">
					<div align="right"> 
						<input name="Button1" type="button" class="bottonRightCol" value="Inserir" onClick="validarForm(document.forms[0]);">
						<input name="Button2" type="button" class="bottonRightCol" value="Fechar" onClick="window.close();">
					</div>
					</td>
				</tr>
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>
</html:form>
</body>
</html:html>
