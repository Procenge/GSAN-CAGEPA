<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>
<%@ page import="gcom.util.ConstantesSistema"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
	
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>	
<script language="JavaScript">

/* Recuperar Popup */
function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
    var form = document.forms[0];
    
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

/* Validações */

function validarBairro(){
	var form = document.forms[0];
	abrirPopup('exibirPesquisarBairroAction.do?idMunicipio='+document.forms[0].codigoMunicipio.value+'&indicadorUsoTodos=1', 400, 800);
}

function validaForm(){	
	var form = document.forms[0]; 
	form.submit();
}

</script>
</head>
<body leftmargin="5" topmargin="5" onload="limpar();">
	<html:form action="/filtrarCepAction.do"
	   name="FiltrarCepActionForm"
	   type="gcom.gui.cadastro.endereco.FiltrarCepActionForm"
	   method="post" >
	  
		<table width="100%" border="0">
			<tr>
				<td>Para filtrar um Cep, informe os dados abaixo:</td>
				<td align="right">
					<input type="checkbox"name="indicadorAtualizar" value="1" checked />
					<strong>Atualizar</strong></td>
			</tr>
		</table>
		<br/>
		<table width="100%" border="0">
				
				<tr>
					<td><strong>C&oacute;digo:</strong></td>
					<td><html:text property="codigo" size="10" tabindex="1" maxlength="8" onkeypress="return isCampoNumerico(event);"/></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td><strong>Munic&iacute;pio:</strong></td>
					<td colspan="2"><html:text maxlength="4" property="codigoMunicipio"
						onkeyup="limpaNomeMunicipio();" size="4" tabindex="2"
						onkeypress="return validaEnterComMensagem(event, 'exibirFiltrarCepAction.do?objetoConsulta=1','codigoMunicipio','Municipio'), isCampoNumerico(event);" />
					<a
						href="javascript:abrirPopup('exibirPesquisarMunicipioAction.do');">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Municipio" /></a> 
					<logic:present name="municipioEncontrado">
						<logic:equal name="municipioEncontrado" value="exception">
							<html:text property="municipio" size="40" maxlength="30"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>
						<logic:notEqual name="municipioEncontrado" value="exception">
							<html:text property="municipio" size="40" maxlength="30"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> 
					<logic:notPresent name="municipioEncontrado">
						<logic:empty name="FiltrarCepActionForm" property="codigoMunicipio">
							<html:text property="municipio" value="" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="FiltrarCepActionForm" property="codigoMunicipio">
							<html:text property="municipio" size="40" maxlength="30"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>

					</logic:notPresent> <a href="javascript:limparMunicipio();">
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>				

				<tr>
					<td><strong>Bairro:</strong></td>
					<td>
						<html:text maxlength="4" tabindex="3"
						property="codigoBairro" size="4"
						onkeypress="return validaEnterComMensagem(event, 'exibirFiltrarCepAction.do?objetoConsulta=2','codigoBairro','Bairro'), isCampoNumerico(event);" />

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
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</logic:equal>
							<logic:notEqual name="bairroEncontrado" value="exception">
								<html:text property="bairro" size="30" maxlength="30"
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000" />
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
						<html:text maxlength="5" tabindex="4"
							property="codigoLogradouro" size="6"
							onkeypress="return validaEnterComMensagem(event, 'exibirFiltrarCepAction.do?objetoConsulta=3','codigoLogradouro','Logradouro'), isCampoNumerico(event);" />
	
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
									<html:text property="logradouro" size="35" maxlength="35" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:equal>
								<logic:notEqual name="logradouroEncontrado" value="exception">
									<html:text property="logradouro" size="35" maxlength="35" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:notEqual>
						</logic:notPresent> 
						<a href="javascript:limparLogradouro();"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
						</a>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				
				<tr>
					<td><strong>Indicador de Uso:</strong></td>
					<td align="right">
						<div align="left"><html:radio property="indicadorUso" tabindex="5"
							value="<%=ConstantesSistema.INDICADOR_USO_ATIVO.toString()%>" />
						<strong>Ativo</strong> <html:radio property="indicadorUso" tabindex="5"
							value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>" />
						<strong>Inativo</strong> <html:radio property="indicadorUso"
							tabindex="5" value="3" /> <strong>Todos</strong></div>					
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>
						<strong> 
							<font color="#ff0000"> 
								<input	name="Submit22" 
										class="bottonRightCol"
										value="Limpar"
										tabindex="6" 
										type="button"
										onclick="window.location.href='/gsan/exibirFiltrarCepAction.do?menu=sim';">
								<input type="button"
										name="ButtonCancelar" 
										class="bottonRightCol" 
										value="Cancelar"
										tabindex="7"
										onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
							</font>
						</strong>
					</td>
					<td align="right"></td>
					<td align="right">
						<gcom:controleAcessoBotao name="Button" value="Filtrar" tabindex="8" onclick="javascript:validaForm();" url="filtrarCepAction.do"/>
					</td>
				</tr>
			</table>
	</html:form>
</body>
</html>