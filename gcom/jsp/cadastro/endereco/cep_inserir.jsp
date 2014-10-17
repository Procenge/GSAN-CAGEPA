<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

<%@ page import="gcom.util.ConstantesSistema"%>
<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro"%>


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
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="InserirCepActionForm" />

<script language="JavaScript">

/* Recuperar Popup */
function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

    var form = document.InserirCepActionForm;

    if (tipoConsulta == 'municipio') {
		form.codigoMunicipio.value = codigoRegistro;
		form.municipio.value = descricaoRegistro;
		form.municipio.style.color = "#000000";
    }

    if (tipoConsulta == 'bairro') {
	    form.codigoBairro.value = codigoRegistro;
	    form.bairro.value = descricaoRegistro;
		form.bairro.style.color = "#000000";
    
    }  
    
    if (tipoConsulta == 'logradouro') {
      	form.codigoLogradouro.value = codigoRegistro;
    	form.logradouro.value = descricaoRegistro;
		form.logradouro.style.color = "#000000";
    }
    
    if (tipoConsulta == 'localidade') {
      	form.codigoLocalidade.value = codigoRegistro;
    	form.localidade.value = descricaoRegistro;
		form.localidade.style.color = "#000000";
    }
  }

/* Funções de Limpar */

function limparMunicipio() {
    var form = document.forms[0];
    form.codigoMunicipio.value = "";
	form.municipio.value = "";
}

function limparBairro() {
    var form = document.forms[0];
    form.codigoBairro.value = "";
	form.bairro.value = "";
}

function limparLogradouro() {
    var form = document.forms[0];
    form.codigoLogradouro.value = "";
	form.logradouro.value = "";
}

function limparLocalidade() {
    var form = document.forms[0];
    form.codigoLocalidade.value = "";
	form.localidade.value = "";
}


function limpar(){
	var form = document.forms[0];	
	limparMunicipio();
	limparBairro();
	limparLogradouro();
	limparLocalidade();
	form.codigo.value = '';
	form.sigla.value = '-1';
	form.descricaoTipoLogradouro.value = '-1';
	
}

/* Validações */

function validarBairro(){
	var form = document.forms[0];
	if(form.municipio.value == '')   { 
		alert('Informe o Município');
	}else{
		abrirPopup('exibirPesquisarBairroAction.do?idMunicipio='+document.forms[0].codigoMunicipio.value+'&indicadorUsoTodos=1', 400, 800);
	}
}

function validarForm(){
	var form = document.forms[0];		
	if(validateInserirCepActionForm(form)){
		form.action = 'inserirCepAction.do';
  			form.submit();
   	}
}
</script>

</head>

<body leftmargin="5" topmargin="5"
	onload="document.forms[0].removerEndereco.value=''; setarFoco('${requestScope.nomeCampo}');">

<html:form action="/exibirInserirCepAction" method="post">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="4" cellpadding="0">
		<tr>
			<td width="149" valign="top" class="leftcoltext">
			<div align="center"><%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%@ include
				file="/jsp/util/informacoes_usuario.jsp"%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%@ include
				file="/jsp/util/mensagens.jsp"%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			</div>
			</td>

			<td width="625" valign="top" class="centercoltext">

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
					<td class="parabg">Inserir CEP</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td>Para adicionar o(s) cep(s), informe os dados abaixo:</td>
					<td align="right"></td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td><strong>Código:<font color="#FF0000">*</font></strong></td>
					<td colspan="2"><html:text property="codigo" size="9"
						tabindex="1" maxlength="8"
						onkeypress="return isCampoNumerico(event);" /></td>
				</tr>
				<tr>
					<td><strong>Unidade Federativa:<font color="#FF0000">*</font></strong></td>
					<td colspan="2"><html:select property="sigla" tabindex="2">
						<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="colecaoUnidadeFederacao" labelProperty="descricao" property="sigla" />
					</html:select></td>
				</tr>
				<tr>
					<td><strong>Munic&iacute;pio:<font color="#FF0000">*</font></strong></td>
					<td colspan="2">
						<html:text maxlength="4" property="codigoMunicipio"
						onkeyup="limpaNomeMunicipio();" size="4" tabindex="3"
						onkeypress="return validaEnterComMensagem(event, 'exibirInserirCepAction.do?objetoConsulta=1','codigoMunicipio','Municipio'), isCampoNumerico(event);" />
					<a href="javascript:abrirPopup('exibirPesquisarMunicipioAction.do');">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Municipio" /></a> 
						<logic:present name="municipioEncontrado">
						<logic:equal name="municipioEncontrado" value="exception">
							<html:text property="municipio" size="30" maxlength="30"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>
						<logic:notEqual name="municipioEncontrado" value="exception">
							<html:text property="municipio" size="30" maxlength="30"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> <logic:notPresent name="municipioEncontrado">
						<logic:empty name="InserirCepActionForm" property="codigoMunicipio">
							<html:text property="municipio" value="" size="30"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="InserirCepActionForm" property="codigoMunicipio">
							<html:text property="municipio" size="30" maxlength="30"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>

					</logic:notPresent> <a href="javascript:limparMunicipio();">
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
				<tr>
					<td><strong>Localidade:</strong></td>
					<td>
						<html:text maxlength="4" tabindex="4"
							property="codigoLocalidade" size="5"
							onkeypress="return validaEnterComMensagem(event, 'exibirInserirCepAction.do?objetoConsulta=4','codigoLocalidade','Localidade'), isCampoNumerico(event);" />
	
						<a href="javascript:abrirPopup('exibirPesquisarLocalidadeAction.do?tipo=imovelLocalidade', 400, 800);">
							<img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" title="Pesquisar Localidade" />
						</a> 
							<logic:present name="localidadeEncontrado" scope="request">
								<logic:equal name="localidadeEncontrado" value="exception">
									<html:text property="localidade" size="35" maxlength="35" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:equal>
								<logic:notEqual name="localidadeEncontrado" value="exception">
									<html:text property="localidade" size="35" maxlength="35" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEqual>
							</logic:present> 
						<logic:notPresent name="localidadeEncontrado" scope="request">
								<logic:equal name="localidadeEncontrado" value="exception">
									<html:text property="localidade" size="35" maxlength="35" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:equal>
								<logic:notEqual name="localidadeEncontrado" value="exception">
									<html:text property="localidade" size="35" maxlength="35" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEqual>
						</logic:notPresent> 
						<a href="javascript:limparLocalidade();"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
						</a>
					</td>
				</tr>
				
				<tr>
					<td><strong>Bairro:</strong></td>
					<td>
						<html:text maxlength="4" tabindex="5"
						property="codigoBairro" size="4"
						onkeypress="return validaEnterComMensagem(event, 'exibirInserirCepAction.do?objetoConsulta=2','codigoBairro','Bairro'), isCampoNumerico(event);" />

						<a href="javascript:validarBairro();"> <img width="23"
							height="21" border="0"
							src="<bean:message key="caminho.imagens"/>pesquisa.gif"
							title="Pesquisar Bairro" /></a> 
						<logic:present name="bairroEncontrado" scope="request">
							<logic:equal name="bairroEncontrado" value="exception">
								<html:text property="bairro" size="30" maxlength="30"
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000" />
							</logic:equal>
							<logic:notEqual name="bairroEncontrado" value="exception">
								<html:text property="bairro" size="30" maxlength="30"
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</logic:notEqual>
						</logic:present> 
						<logic:notPresent name="bairroEncontrado" scope="request">
							<logic:equal name="bairroEncontrado" value="exception">
								<html:text property="bairro" size="30" maxlength="30"
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000" />
							</logic:equal>
							<logic:notEqual name="bairroEncontrado" value="exception">
								<html:text property="bairro" size="30" maxlength="30"
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</logic:notEqual>
						</logic:notPresent> 
						<a href="javascript:limparBairro();"> <img
							src="<bean:message key="caminho.imagens"/>limparcampo.gif"
							border="0" title="Apagar" />
						</a>
					</td>
				</tr>
				
				<tr>
					<td><strong>Logradouro:</strong></td>
					<td>
						<html:text maxlength="5" tabindex="6"
							property="codigoLogradouro" size="6"
							onkeypress="return validaEnterComMensagem(event, 'exibirInserirCepAction.do?objetoConsulta=3','codigoLogradouro','Logradouro'), isCampoNumerico(event);" />
	
						<a href="javascript:abrirPopup('exibirPesquisarLogradouroAction.do?codigoMunicipio='+document.forms[0].codigoMunicipio.value+'&codigoBairro='+document.forms[0].codigoBairro.value+'&indicadorUsoTodos=1&primeriaVez=1', 400, 800);">
							<img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" title="Pesquisar Logradouro" />
						</a> 
							<logic:present name="logradouroEncontrado" scope="request">
								<logic:equal name="logradouroEncontrado" value="exception">
									<html:text property="logradouro" size="35" maxlength="35" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:equal>
								<logic:notEqual name="logradouroEncontrado" value="exception">
									<html:text property="logradouro" size="35" maxlength="35" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEqual>
							</logic:present> 
						<logic:notPresent name="logradouroEncontrado" scope="request">
								<logic:equal name="logradouroEncontrado" value="exception">
									<html:text property="logradouro" size="35" maxlength="35" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:equal>
								<logic:notEqual name="logradouroEncontrado" value="exception">
									<html:text property="logradouro" size="35" maxlength="35" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEqual>
						</logic:notPresent> 
						<a href="javascript:limparLogradouro();"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
						</a>
					</td>
				</tr>
				
				<tr>
					<td></td>
					<td><font color="#FF0000">*</font>&nbsp;Campos
					obrigat&oacute;rios</td>
				</tr>

				<tr>
					<td colspan="2">
						<input type="button" name="Button"
							class="bottonRightCol" value="Desfazer" tabindex="7"
							onClick="javascript:limpar()"
							style="width: 80px" />&nbsp; 
						<input type="button" name="Button"
							class="bottonRightCol" value="Cancelar" tabindex="8"
							onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
							style="width: 80px" />
					</td>
					<td align="right">
						<gcom:controleAcessoBotao name="Button"
							value="Inserir" tabindex="9"
							onclick="javascript:validarForm(document.forms[0]);"
							url="inserirCepAction.do" />
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

