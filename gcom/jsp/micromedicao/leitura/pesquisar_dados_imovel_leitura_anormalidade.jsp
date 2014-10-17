<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<%@page import="gcom.util.ConstantesSistema"%>
<%@page import="gcom.micromedicao.MovimentoRoteiroEmpresa"%><html:html>

<head>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--

function fechar(){
		window.close();
		
}

function bloqueiaOutros(obj) {
	var form = document.forms[0];
	if (obj.value != '-1') {
		if (obj.name == 'hidrometro') {
			form.matriculaImovel.disabled = true;
			form.inscricaoImovel.disabled = true;
			form.sequecialImovelRota.disabled = true;
		} else if (obj.name == 'matriculaImovel') {
			form.hidrometro.disabled = true;
			form.inscricaoImovel.disabled = true;
			form.sequecialImovelRota.disabled = true;
		} else if (obj.name == 'inscricaoImovel') {
			form.matriculaImovel.disabled = true;
			form.hidrometro.disabled = true;
			form.sequecialImovelRota.disabled = true;
		} else if (obj.name == 'sequecialImovelRota') {
			form.matriculaImovel.disabled = true;
			form.hidrometro.disabled = true;
			form.inscricaoImovel.disabled = true;
		}
	} else {
		form.matriculaImovel.disabled = false;
		form.hidrometro.disabled = false;
		form.inscricaoImovel.disabled = false;
		form.sequecialImovelRota.disabled = false;
	}
}


function pesquisarImovelRota() {
	var form = document.forms[0];
	if (form.hidrometro.value == -1 && 
			form.matriculaImovel.value == -1 && 
			form.inscricaoImovel.value == -1 && 
			form.sequecialImovelRota.value == -1) {
		alert('É necessário informar algum dos critérios de busca dos dados de leitura.');
	} else {
		form.action = "exibirInformarDadosLeituraAnormalidadeAction.do";
		form.submit();
		enviarDados('', '', '');
	}
}

-->
</SCRIPT>
</head>
<body leftmargin="5" topmargin="5">
<html:form action="/informarLeituraDadosAnormalidadeAction.do"
	name="InformarDadosLeituraAnormalidadeActionForm"
	type="gcom.gui.micromedicao.leitura.InformarDadosLeituraAnormalidadeActionForm"
	method="post">
	<table width="570" border="0" cellpadding="0" cellspacing="5">
		<tr>
			<td width="560" valign="top" class="centercoltext">
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
					<td class="parabg">Pesquisar Dados Imovel para Informar Leitura e Anormalidade:</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
			<table border="0" width="100%">
				<tr>
					<td>
						<strong>Hidrômetro:</strong><span class="style2"> <strong>
						<font color="#FF0000">*</font> </strong> </span>
					</td>
					<td>
						<strong> <html:select property="hidrometro"
						style="width: 230px;" onchange="javascript:bloqueiaOutros(this);">
						<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
							</html:option>

						<logic:present name="colecaoImovelRota" scope="session">
							<logic:iterate id="imovelRota" name="colecaoImovelRota" indexId="index">
								<html:option value="<%=index.toString()%>"><bean:write name="imovelRota" property="numeroHidrometro"/></html:option>
							</logic:iterate>
						</logic:present>
						</html:select> </strong>
					</td>
				</tr>
				<tr>
					<td>
						<strong>Matrícula do Imóvel:</strong><span class="style2"> <strong>
						<font color="#FF0000">*</font> </strong> </span>
					</td>
					<td>
						<strong> <html:select property="matriculaImovel"
						style="width: 230px;" onchange="javascript:bloqueiaOutros(this);">
						<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
							</html:option>

						<logic:present name="colecaoImovelRota" scope="session">
							<logic:iterate id="imovelRota" name="colecaoImovelRota" indexId="index">
								<html:option value="<%=index.toString()%>"><bean:write name="imovelRota" property="imovel.id"/></html:option>
							</logic:iterate>
						</logic:present>
						</html:select> </strong>
					</td>
				</tr>
				<tr>
					<td>
						<strong>Inscrição do Imóvel:</strong><span class="style2"> <strong>
						<font color="#FF0000">*</font> </strong> </span>
					</td>
					<td>
						<strong> <html:select property="inscricaoImovel"
						style="width: 230px;" onchange="javascript:bloqueiaOutros(this);">
						<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
							</html:option>

						<logic:present name="colecaoImovelRota" scope="session">
							<logic:iterate id="imovelRota" name="colecaoImovelRota" indexId="index">
								<html:option value="<%=index.toString()%>"><bean:write name="imovelRota" property="numeroInscricao"/></html:option>
							</logic:iterate>
						</logic:present>
						</html:select> </strong>
					</td>
				</tr>
				<tr>
					<td>
						<strong>Sequêncial do Imóvel na Rota:</strong><span class="style2"> <strong>
						<font color="#FF0000">*</font> </strong> </span>
					</td>
					<td>
						<strong> <html:select property="sequecialImovelRota"
						style="width: 230px;" onchange="javascript:bloqueiaOutros(this);">
						<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
							</html:option>

						<logic:present name="colecaoImovelRota" scope="session">
							<logic:iterate id="imovelRota" name="colecaoImovelRota" indexId="index">
								<html:option value="<%=index.toString()%>"><bean:write name="imovelRota" property="imovel.numeroSequencialRota"/></html:option>
							</logic:iterate>
						</logic:present>
						</html:select> </strong>
					</td>
				</tr>
			</table>
			<table border="0" width="100%">
				<tr>
					<td colspan="3">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="3" align="left"><input name="Button" type="button"
						class="bottonRightCol" value="Fechar"
						onClick="javascript:fechar();"></td>
					<td colspan="3" align="right"><input name="Button" type="button"
						class="bottonRightCol" value="Pesquisar"
						onClick="javascript:pesquisarImovelRota();"></td>
				</tr>
				<p>&nbsp;</p>
			</table>
			</td>
		</tr>
	</table>
</html:form>
<body>
</html:html>
