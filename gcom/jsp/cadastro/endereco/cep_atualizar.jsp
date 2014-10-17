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
<html:javascript staticJavascript="false" formName="AtualizarCepActionForm" />

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


<body leftmargin="5" topmargin="5">
	<html:form action="/atualizarCepAction.do"
		name="AtualizarCepActionForm"
		type="gcom.gui.cadastro.endereco.AtualizarCepActionForm" method="post">	
		
		
		
		<table width="100%" border="0">
			<tr>
				<td height="10" colspan="3">Para atualizar o Cep, informe os dados abaixo:</td>
			</tr>
			
			<tr>
				<td><strong>Código:<font color="#FF0000">*</font></strong></td>
				<td colspan="2"><html:text property="codigo" size="9"
					tabindex="1" maxlength="8" onkeypress="return isCampoNumerico(event);"/></td>
			</tr>
			<tr>
				<td height="0"><strong>Unidade Federativa:<font color="#FF0000">*</font></strong></td>
				<td colspan="2">
					<html:select property="sigla" tabindex="2" style="width: 150px;">
						<html:option value="">&nbsp;</html:option>
						<html:options collection="colecaoUnidadeFederacao" labelProperty="descricao" property="sigla" />
					</html:select>
				</td>
			</tr>
			<tr>
				<td>
					<strong>Munic&iacute;pio:<font color="#FF0000">*</font></strong>
				</td>
				<td colspan="2"><html:text maxlength="4" property="codigoMunicipio"
					onkeyup="limpaNomeMunicipio();" size="4" tabindex="3"
					onkeypress="return validaEnterComMensagem(event, 'exibirAtualizarCepAction.do?objetoConsulta=1','codigoMunicipio','Municipio'),isCampoNumerico(event);" />
					
					<a href="javascript:abrirPopup('exibirPesquisarMunicipioAction.do');">
						<img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" title="Pesquisar Municipio" />
					</a> 
					
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
					
						<logic:empty name="AtualizarCepActionForm" property="codigoMunicipio">
							<html:text property="municipio" value="" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						
						<logic:notEmpty name="AtualizarCepActionForm" property="codigoMunicipio">
							<html:text property="municipio" size="40" maxlength="30"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>

					</logic:notPresent> 
					
					<a href="javascript:limparMunicipio();">
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
					</a>
				</td>
			</tr>
				<tr>
					<td><strong>Localidade:</strong></td>
					<td>
						<html:text maxlength="4" tabindex="4"
							property="codigoLocalidade" size="5"
							onkeypress="return validaEnterComMensagem(event, 'exibirAtualizarCepAction.do?objetoConsulta=4','codigoLocalidade','Localidade'), isCampoNumerico(event);" />
	
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
						onkeypress="return validaEnterComMensagem(event, 'exibirAtualizarCepAction.do?objetoConsulta=2','codigoBairro','Bairro'), isCampoNumerico(event);" />

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
						<logic:notPresent name="bairroEncontrado">
							<logic:empty name="AtualizarCepActionForm" property="codigoBairro">
								<html:text property="bairro" size="30" maxlength="30"
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000" />
							</logic:empty>
							<logic:notEmpty name="AtualizarCepActionForm" property="codigoBairro">
								<html:text property="bairro" size="30" maxlength="30"
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</logic:notEmpty>
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
							onkeypress="return validaEnterComMensagem(event, 'exibirAtualizarCepAction.do?objetoConsulta=3','codigoLogradouro','Logradouro'), isCampoNumerico(event);" />
	
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
							<logic:empty name="AtualizarCepActionForm" property="codigoLogradouro">
								<html:text property="logradouro" size="30" maxlength="30"
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000" />
							</logic:empty>
							<logic:notEmpty name="AtualizarCepActionForm" property="codigoLogradouro">
								<html:text property="logradouro" size="30" maxlength="30"
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</logic:notEmpty>
						</logic:notPresent> 
						<a href="javascript:limparLogradouro();"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
						</a>
					</td>
				</tr>
			<tr>
				<td><strong>Indicador de Uso:</strong></td>
				<td align="right">
					<div align="left"><html:radio property="indicadorUso" tabindex="7"
						value="<%=ConstantesSistema.INDICADOR_USO_ATIVO.toString()%>" />
					<strong>Ativo</strong> <html:radio property="indicadorUso" tabindex="5"
						value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>" />
					<strong>Inativo</strong> 					
				</td>
			</tr>
			<tr>
				<td height="0"><strong>Cep Tipo:</strong></td>
				<td colspan="2"><html:select property="cepTipo" tabindex="8" style="width: 125px;">
					<html:option value="">&nbsp;</html:option>
					<html:option value="1">PROMOCIONAL</html:option>
					<html:option value="2">EMPRESARIAL</html:option>
					<html:option value="3">LOGRADOURO</html:option>
					<html:option value="4">ÚNICO</html:option>
					<html:option value="5">INICIAL</html:option>
				</html:select></td>
			</tr>
			<tr>
				<td><strong>Intervalo Numeração:</strong></td>
				<td colspan="2"><html:text property="intervaloNumeracao" size="32"
					tabindex="9" maxlength="30" onkeypress="return isCampoNumerico(event);"/></td>
			</tr>
			<tr>
				<td><strong>Faixa Inicial:<font color="#FF0000">*</font></strong></td>
				<td colspan="2"><html:text property="faixaInicial" size="15"
					tabindex="10" maxlength="6" onkeypress="return isCampoNumerico(event);"/></td>
			</tr>
			<tr>
				<td><strong>Faixa Final:<font color="#FF0000">*</font></strong></td>
				<td colspan="2"><html:text property="faixaFinal" size="15"
					tabindex="11" maxlength="6" onkeypress="return isCampoNumerico(event);"/></td>
			</tr>
			<tr>
				<td height="0"><strong>Lado Cep:</strong></td>
				<td colspan="2"><html:select property="codigoLadoCep" tabindex="12" style="width: 100px;">
					<html:option value="">&nbsp;</html:option>
					<html:option value="P">P - PAR</html:option>
					<html:option value="I">I - IMPAR</html:option>
					<html:option value="A">A - AMBOS</html:option>
				</html:select></td>
			</tr>
			<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
			</tr>

			<tr>
				<td></td>
				<td align="right">
					<div align="left">
						<strong><font color="#FF0000">*</font></strong>Campos obrigatórios
					</div>
				</td>
			</tr>
			
			<tr>
				<td colspan="2">
					<font color="#FF0000"> 
						<logic:present name="atualizar" scope="request">
							<input type="button" 
								   name="ButtonReset" 
								   class="bottonRightCol"
								   value="Voltar"
								   tabindex="13"
							       onClick="javascript:window.location.href='/gsan/exibirFiltrarClienteTipo.do?menu=sim'">
						</logic:present>
									
						<logic:notPresent name="atualizar" scope="request">
							<input type="button" 
								   name="ButtonReset" 
								   class="bottonRightCol"
								   value="Voltar" 
								   tabindex="14"
								   onclick="window.location.href='<html:rewrite page="/filtrarCepAction.do"/>'">
						</logic:notPresent>
						<input 	type="button"
								name="ButtonCancelar" 
								class="bottonRightCol" 
								value="Cancelar"
								tabindex="15"
								onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
					</font>
				</td>
				<td align="right">
					<gcom:controleAcessoBotao name="Button" value="Atualizar" tabindex="16" onclick="javascript:validaForm(document.forms[0]);" url="atualizarCepAction.do"/>
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html>