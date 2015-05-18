<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page import="gcom.util.ConstantesSistema" %>
<%@ page import="gcom.cadastro.endereco.Cep"%>


<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key='caminho.css'/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="SelecionarCepActionForm" />

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<SCRIPT LANGUAGE="JavaScript">
<!--


function limpar(tipo){

	var form = document.forms[0];
}



function validarForm(form){

	var objMunicipio = returnObject(form, "nomeMunicipio");
	var objLogradouro = returnObject(form, "nomeLogradouro");
	
	if (objMunicipio.disabled){
		objMunicipio.disabled = false;
		voltarSituacao = true;
	}
	
	if (validateSelecionarCepActionForm(form) && objLogradouro.value != ''){
	
		redirecionarSubmit('pesquisarSelecionarCepAction.do');
		 
	}
	else if (voltarSituacao){
		objMunicipio.disabled = true;
		
		if (objLogradouro.value == '') {
			alert('Por favor informar o Logradouro');
		}
		
	}
}


function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg){

	if (objeto == null || codigoObjeto == null){
		abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
	}
	else{
		if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
			alert(msg);
		}
		else{
			abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
		}
	}
}


function facilitador(objeto){
	if (objeto.id == "0" || objeto.id == undefined){
		objeto.id = "1";
		marcarTodos();
	}
	else{
		objeto.id = "0";
		desmarcarTodos();
	}
}

// Verifica se há item selecionado
function verificarSelecao(objeto){

	if (CheckboxNaoVazioMensagemGenerico("Nenhum registro foi selecionado.", objeto)){
		redirecionarSubmit('inserirSelecaoCepAction.do');
	}
}

// Verifica se há item selecionado
function verificarSelecaoParaRemocao(objeto){

	if (CheckboxNaoVazio(objeto)){
		if (confirm ("Confirma exclusão ?")) {
			redirecionarSubmit('removerSelecaoCepAction.do');
		 }
	}
}

function cepPadrao(){
	
	var form = document.forms[0];
	var idMunicipio = form.idMunicipio;
	
	form.action = "pesquisarSelecionarCepPadraoAction.do?idMunicipio=" + idMunicipio.value;
	submeterFormPadrao(form); 
}

//-->
</SCRIPT>

</head>

<logic:present name="retornarUseCase">

	<logic:equal name="flagRedirect" value="endereco">
		<BODY TOPMARGIN="5" LEFTMARGIN="5"
			onload="redirecionarSubmit('exibirInserirEnderecoAction.do')">
	</logic:equal>

	<logic:notEqual name="flagRedirect" value="endereco">

		<logic:equal name="operacao" value="1">
			<BODY TOPMARGIN="5" LEFTMARGIN="5"
				onload="chamarSubmitComUrl('exibirInserirLogradouroAction.do'),window.close();">
		</logic:equal>

		<logic:notEqual name="operacao" value="1">
			<BODY TOPMARGIN="5" LEFTMARGIN="5"
				onload="chamarSubmitComUrl('exibirAtualizarLogradouroAction.do'),window.close();">
		</logic:notEqual>

	</logic:notEqual>

</logic:present>

<logic:notPresent name="retornarUseCase">
	<BODY TOPMARGIN="5" LEFTMARGIN="5"
		onload="resizePageSemLink(700, 650); setarFoco('${requestScope.nomeCampo}');">
</logic:notPresent>

<html:form action="/exibirSelecionarCepAction" method="post">

	<table width="650" border="0" cellpadding="0" cellspacing="5">
		<tr>
			<td width="640" valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img
						src="<bean:message key='caminho.imagens'/>parahead_left.gif"
						editor="Webstyle4"
						moduleid="Album Photos (Project)\toptab_page2_parahead_left.xws"
						border="0" /></td>
					<td class="parabg">Selecionar CEPs</td>
					<td width="11"><img
						src="<bean:message key='caminho.imagens'/>parahead_right.gif"
						editor="Webstyle4"
						moduleid="Album Photos (Project)\toptab_page2_parahead_right.xws"
						border="0" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td colspan="2">Preencha os campos para selecionar CEPs:</td>
					<td align="right"></td>
    			</tr>
      		</table>
				<!-- FORMULARIO --------------------------------------------------------------------------------------------------------------- -->
			<table width="100%" border="0">
				<tr>
					<td><strong>Munic&iacute;pio:<font color="#FF0000">*</font></strong></td>
					<td colspan="2"><html:hidden property="idMunicipio" /> <logic:empty
						name="SelecionarCepActionForm" property="nomeMunicipio">
						<html:text maxlength="30" property="nomeMunicipio" size="30"
							tabindex="1" />
					</logic:empty> <logic:notEmpty name="SelecionarCepActionForm"
						property="nomeMunicipio">

						<logic:present name="municipioInformado" scope="session">
							<html:text maxlength="30" property="nomeMunicipio" size="30"
								tabindex="1" disabled="true" />
						</logic:present>

						<logic:notPresent name="municipioInformado" scope="session">
							<html:text maxlength="30" property="nomeMunicipio" size="30"
								tabindex="1" />
						</logic:notPresent>

					</logic:notEmpty></td>
				</tr>
				<tr>
					<td width="15%"><strong>Logradouro:<font color="#FF0000">*</font></strong></td>
					<td width="85%" colspan="2"><html:text maxlength="30"
						property="nomeLogradouro" size="30" tabindex="2" /></td>
				</tr>
				<tr>
          			<td width="10%" height="5"><strong>Faixa:</strong></td>
          					<td colspan="2">
            					<logic:notPresent name="travarFaixa" scope="request">
        		 					<html:text property="faixaInicial" style="width: 50;" maxlength="6" />
        						</logic:notPresent>
        						 <logic:present name="travarFaixa" scope="request">
        	 						<html:text property="faixaInicial" style="width: 50;" maxlength="6" disabled="true"/>
        					</logic:present>
    	    		</tr>
    	    		 <tr>
          		<td height="24"><strong>Lado Cep:</strong></td>
          				<td colspan="3">
          				<html:select property="codigoLado" style="width: 80;">
							<html:option value="-1">&nbsp;</html:option>
							<html:option value="<%=ConstantesSistema.PAR.toString()%>">PAR</html:option>
							<html:option value="<%=ConstantesSistema.IMPAR.toString() %>">ÍMPAR</html:option>
							<html:option value="<%=ConstantesSistema.AMBOS.toString()%>">AMBOS</html:option>
         				 </html:select></td>
      			  </tr>
			<tr>
			<td colspan="2"><input type="button" class="bottonRightCol"
						value="Selecionar" onclick="validarForm(document.forms[0]);"
						style="width: 80px;" name="botaoSelecionar" tabindex="3"></td>
				</tr>
				<tr>
					<td height="10" colspan="2"></td>
				</tr>
				<tr>
					<td colspan="2"><strong>CEPs Selecionados:</strong></td>
				</tr>
				<tr>
					<td colspan="2">

					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td></td>
						</tr>
					</table>

					<div style="width: 100%; height: 300; overflow: auto;">

					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td>

							<table width="100%" align="center" bgcolor="#99CCFF">
								<tr bgcolor="#99CCFF">
									<td align="center" width="10%"><A
										HREF="javascript:facilitador(this);" id="0"><strong>Todos</strong></A></td>
											<td width="18%"><div align="center"><strong>Logradouro</strong></div></td>
		                        			<td width="15%"><div align="center"><strong>Bairro</strong></div></td>
		                        			<td width="13%"><div align="center"><strong>Município</strong></div></td>
											<td width="5%"><div align="center"><strong>UF</strong></div></td>
											<td width="15%"><div align="center"><strong>CEP</strong></div></td>
											<td width="10%"><div align="center"><strong>Faixa</strong></div></td>
											<td width="15%"><div align="center"><strong>Lado</strong></div></td>
								</tr>
								<logic:present name="colecaoCepSelecionados" scope="session">
									<%String cor = "#FFFFFF";%>
									<logic:iterate name="colecaoCepSelecionados" id="cep"
										type="Cep">


										<%if (cor.equalsIgnoreCase("#FFFFFF")) {
				cor = "#cbe5fe";%>
										<tr bgcolor="#cbe5fe">
											<%} else {
				cor = "#FFFFFF";%>
										<tr bgcolor="#FFFFFF">
											<%}%>

											<td align="center" width="10%"><html:checkbox
												property="idCepSelecao"
												value="<%="" + cep.getCepId().intValue() %>" /></td>
											<td width="18%">
											<div align="left"><logic:present name="cep"
												property="descricaoLogradouroFormatada">
												<bean:write name="cep"
													property="descricaoLogradouroFormatada" />
											</logic:present> <logic:notPresent name="cep"
												property="descricaoLogradouroFormatada">&nbsp;</logic:notPresent>

											</div>
											</td>
											<td width="15%">
											<div align="left"><logic:present name="cep" property="bairro">
												<bean:write name="cep" property="bairro" />
											</logic:present> <logic:notPresent name="cep"
												property="bairro">&nbsp;</logic:notPresent></div>
											</td>

											<td width="15%">
											<div align="left"><logic:present name="cep"
												property="municipio">
												<bean:write name="cep" property="municipio" />
											</logic:present> <logic:notPresent name="cep"
												property="municipio">&nbsp;</logic:notPresent></div>
											</td>

											<td width="5%">
											<div align="left"><logic:present name="cep" property="sigla">
												<bean:write name="cep" property="sigla" />
											</logic:present> <logic:notPresent name="cep"
												property="sigla">&nbsp;</logic:notPresent></div>
											</td>

											<td width="15%">
											<div align="center"><logic:present name="cep"
												property="codigo">
												<bean:write name="cep" property="cepFormatado" />
											</logic:present> <logic:notPresent name="cep"
												property="codigo">&nbsp;</logic:notPresent></div>
											</td>
												<td width="15%">
													<div align="center">
														<logic:present name="cep" property="descricaoFaixaFormatada">
															<bean:write name="cep" property="descricaoFaixaFormatada"/>
														</logic:present>
														<logic:notPresent name="cep" property="descricaoFaixaFormatada">
															&nbsp;
														</logic:notPresent>	
													</div>
												</td>
													<td width="10%">
													<div align="center">
														<logic:present name="cep" property="ladoFormatado">
															<bean:write name="cep" property="ladoFormatado"/>
														</logic:present>
														<logic:notPresent name="cep" property="ladoFormatado">
															&nbsp;
														</logic:notPresent>	
													</div>
												</td>
										</tr>
									</logic:iterate>
								</logic:present>
							</table>

							</td>
						</tr>

					</table>

					</div>
					</td>
				</tr>

				<!-- FIM DO FORMULÁRIO -------------------------------------------------------------------------------------------------------- -->

				<tr>
					<td></td>
					<td align="right"><INPUT type="button" class="bottonRightCol"
						value="CEP Inicial de Município" tabindex="5"
						onclick="cepPadrao()" name="botaoCepPadrao"> <INPUT type="button"
						class="bottonRightCol" value="Inserir" tabindex="6"
						onclick="verificarSelecao(idCepSelecao);" style="width: 70px;"
						name="botaoInserir"></td>
				</tr>
			</table>

			<p>&nbsp;</p>
			</td>
		</tr>
	</table>

</html:form>

</body>

</html:html>
