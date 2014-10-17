<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<%@page import="gcom.util.ConstantesSistema"%><html:html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="InformarDadosLeituraAnormalidadeActionForm" />

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">

	function pesquisar(){
		var form = document.forms[0];
		if(form.idEmpresa.value == '-1' || form.idLeiturista.value == '-1'){
			alert('Informe os dados necessários para a realização da pesquisa das rotas do leiturista.');
		}else{
			form.submit();
		}
	}

	function desabilitarEmpresaLeiturista(){
		var form = document.forms[0];
		if(form.editarEmpresa.value == 'false'){
			form.idEmpresa.disabled = true;
		}
		if(form.editarLeiturista.value == 'false'){
			form.idLeiturista.disabled = true;
		}
	}

	function limpar(){
		if(form.idEmpresa.disabled == false){
			form.idEmpresa.value = '-1';
		}
		if(form.idLeiturista.disabled == false){
			form.idLeiturista.value = '-1';
		}
	}

	function buscaLeiturista(){
		var form = document.forms[0];
		form.action = "exibirPesquisarRotaLeituristaAction.do?pesquisaEmpresa=true";
		form.submit();
	}

	
</script>

</head>

<body leftmargin="5" topmargin="5" onload="javascript:desabilitarEmpresaLeiturista();">


<html:form action="/pesquisarRotaLeituristaAction"
	name="PesquisarRotaLeituristaActionForm"
	type="gcom.gui.micromedicao.leitura.PesquisarRotaLeituristaActionForm"
	method="post">
	
	<html:hidden property="editarEmpresa" />
	<html:hidden property="editarLeiturista" />
	
	<table width="445" height="100%" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td valign="top" class="centercoltext">
			<table>
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Pesquisar Rotas Leiturista</td>
					<td width="11" valign="top"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>

			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
						<td colspan="2">Para pesquisar as rotas leiturista, informe os dados abaixo:</td>
				</tr>
				<tr>
					<td><strong>Empresa:</strong> <span class="style2"> <strong>
					<font color="#FF0000">*</font> </strong> </span></td>

					<td><strong> <html:select property="idEmpresa"
						style="width: 230px;" onchange="javascript:buscaLeiturista();">
						<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
							</html:option>

						<logic:present name="colecaoEmpresa" scope="session">
							<html:options collection="colecaoEmpresa"
								labelProperty="descricaoAbreviada" property="id" />
						</logic:present>
					</html:select> </strong></td>

				</tr>
				
				<tr>
					<td><strong>Agente Comercial:</strong> <span class="style2"> <strong>
					<font color="#FF0000">*</font> </strong> </span></td>

					<td><strong> <html:select property="idLeiturista"
						style="width: 230px;">
						<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
							</html:option>

						<logic:present name="colecaoLeiturista" scope="session">
							<html:options collection="colecaoLeiturista"
								labelProperty="idDescricao" property="id" />
						</logic:present>
					</html:select> </strong></td>
				
				</tr>
			</table>
			<table width="100%">
				<tr>
					<td height="19"><strong> <font color="#FF0000"></font></strong></td>

					<td align="right">
					<div align="left"><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios</div>
					</td>
				</tr>
				<tr>
				
					<td>
							<input type="button" align="left" name="ButtonReset"
							class="bottonRightCol" value="Limpar" onClick="javascript:limpar();'">
						</td>
						<td colspan="2" align="right">
							<input type="button" align="right" name="Button" class="bottonRightCol"
							value="Pesquisar" onclick="javascript:pesquisar();" tabindex="15" />
					</td>
				</tr>
			</table>
</html:form>
</body>
</html:html>

