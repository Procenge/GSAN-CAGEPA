<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page import="gcom.util.Util" %>
<%@ page import="gcom.util.ConstantesSistema" isELIgnored="false"%>
<%@ page import="java.util.Collection" isELIgnored="false"%>
<%@ page import="gcom.cadastro.imovel.ImovelConsumoFaixaAreaCategoria"%>

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
	formName="EconomiaPopupActionForm" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">
	function validarForm(form) {
		document.forms[0].target = '';
    	form.submit();
	}

	function preencheComZero(campo) {
		if (campo.value == '') {
			campo.value = '0';
		}
	}

	function submitAtualizarArea(form, campo) {
        form.action='exibirImovelConsumoFaixaAreaCategoriaPopupAction.do?atualizarArea=S&nomeCampoFoco=' + campo;
    	form.submit();
	}
</script>

</head>

<logic:present name="closePage">
	<logic:equal name="closePage" value="S">
		<body leftmargin="5" topmargin="5" onload="window.close();">
	</logic:equal>
</logic:present>

<logic:notPresent name="closePage">
	<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampoFoco}');">
</logic:notPresent>

<html:form action="/inserirImovelConsumoFaixaAreaCategoriaPopupAction" method="post"
	onsubmit="return validateImovelConsumoFaixaAreaCategoriaPopupActionForm(this);">
	<table width="720" border="0" cellpadding="0" cellspacing="5">
		<tr>
			<td width="720" valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Consumo por Faixa de Área e Categoria do Imóvel</td>
					<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>Preencha os campos para inserir os Consumos por Faixa de Área e Categoria do Imóvel:</td>
					<td align="right"></td>											
				</tr>
			</table>
			<table width="100%" border="0">
			<tr>
				<td>
				<table width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td height="0">
						<table width="100%" bgcolor="#90c7fc">
							<!--header da tabela interna -->
							<tr bordercolor="#FFFFFF" bgcolor="#90c7fc">
								<td width="15%" align="center"><strong>Categoria</strong></td>
								<td width="10%" align="center"><strong>Faixa</strong></td>
								<td width="13%" align="center"><strong>Comprimento da Frente</strong></td>
								<td width="13%" align="center"><strong>Comprimento do Lado</strong></td>
								<td width="13%" align="center"><strong>Comprimento da Testada</strong></td>
								<td width="12%" align="center"><strong>Número de Andares</strong></td>
								<td width="12%" align="center"><strong>Comprimento do Andar</strong></td>
								<td width="12%" align="center"><strong>Área Construída</strong></td>
							</tr>
						</table>
						</td>
					</tr>
					<tr>
						<td height="130">
						<div style="width: 100%; height: 100%; overflow: auto;">
						<table width="100%" align="left" bgcolor="#99CCFF">
							<!--corpo da segunda tabela-->
							<%int cont = 0;%>
							<logic:notEmpty name="colecaoImovelConsumoFaixaAreaCategoriaAux">
								<logic:iterate name="colecaoImovelConsumoFaixaAreaCategoriaAux" id="imovelConsumoFaixaAreaCategoria" type="ImovelConsumoFaixaAreaCategoria">
									<%cont = cont + 1;
									if (cont % 2 == 0) {%>
									<tr bgcolor="#cbe5fe">
									<%} else {%>
									<tr bgcolor="#FFFFFF">
									<%}%>
										<td width="15%" align="center">
											<div>${imovelConsumoFaixaAreaCategoria.categoria.descricao} &nbsp;</div>
										</td>
										<td width="10%" align="center">
											<div>${imovelConsumoFaixaAreaCategoria.comp_id.consumoFaixaAreaCategoria.id} &nbsp;</div>
										</td>
										<td width="13%" align="center">
											<input type="text" style="text-align: right;font-size: xx-small;" 
												align="center" name=<%="comprimentoFrente" + imovelConsumoFaixaAreaCategoria.getCategoria().getId().intValue()%>
												value="<%="" + (imovelConsumoFaixaAreaCategoria.getComprimentoFrente())%>"
												size="4" maxlength="4" onkeypress="return isCampoNumerico(event);" onblur="preencheComZero(this);" onchange="javascript:submitAtualizarArea(document.forms[0], '<%="comprimentoLado" + imovelConsumoFaixaAreaCategoria.getCategoria().getId().intValue()%>');" />
										</td>
										<td width="13%" align="center">
											<input type="text" style="text-align: right;font-size: xx-small;" 
												align="center" name=<%="comprimentoLado" + imovelConsumoFaixaAreaCategoria.getCategoria().getId().intValue()%>
												value="<%="" + (imovelConsumoFaixaAreaCategoria.getComprimentoLado())%>"
												size="4" maxlength="4" onkeypress="return isCampoNumerico(event);" onblur="preencheComZero(this);" onchange="javascript:submitAtualizarArea(document.forms[0], '<%="comprimentoTestada" + imovelConsumoFaixaAreaCategoria.getCategoria().getId().intValue()%>');" />
										</td>
										<td width="13%" align="center">
											<input type="text" style="text-align: right;font-size: xx-small;" 
												align="center" name=<%="comprimentoTestada" + imovelConsumoFaixaAreaCategoria.getCategoria().getId().intValue()%>
												value="<%="" + (imovelConsumoFaixaAreaCategoria.getComprimentoTestada() != null ? imovelConsumoFaixaAreaCategoria.getComprimentoTestada() : "")%>"
												size="4" maxlength="4" onkeypress="return isCampoNumerico(event);"/>
										</td>
										<td width="12%" align="center">
											<input type="text" style="text-align: right;font-size: xx-small;" 
												align="center" name=<%="numeroAndares" + imovelConsumoFaixaAreaCategoria.getCategoria().getId().intValue()%>
												value="<%="" + (imovelConsumoFaixaAreaCategoria.getNumeroAndares() != null ? imovelConsumoFaixaAreaCategoria.getNumeroAndares() : "")%>"
												size="4" maxlength="4" onkeypress="return isCampoNumerico(event);"/>
										</td>
										<td width="12%" align="center">
											<input type="text" style="text-align: right;font-size: xx-small;" 
												align="center" name=<%="comprimentoAndar" + imovelConsumoFaixaAreaCategoria.getCategoria().getId().intValue()%>
												value="<%="" + (imovelConsumoFaixaAreaCategoria.getComprimentoAndar() != null ? imovelConsumoFaixaAreaCategoria.getComprimentoAndar() : "")%>"
												size="4" maxlength="4" onkeypress="return isCampoNumerico(event);"/>
										</td>
										<td width="12%" align="center">
											<div>${imovelConsumoFaixaAreaCategoria.areaConstruida} &nbsp;</div>
										</td>
									</tr>
								</logic:iterate>
							</logic:notEmpty>
						</table>
						</div>
						</td>
					</tr>
				</table>
				</td>
				<tr>
					<td height="24">
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
