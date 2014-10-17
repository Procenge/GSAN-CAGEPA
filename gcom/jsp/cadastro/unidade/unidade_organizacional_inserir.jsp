<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<%@ page import="gcom.util.ConstantesSistema"%>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="InserirUnidadeOrganizacionalActionForm" />

<script language="JavaScript">

	//Valida o form no validator.xml
	function validaForm(){
   		var form = document.forms[0];

    	if(validateInserirUnidadeOrganizacionalActionForm(form)){
		  	if(validaTodosRadioButton()) {
				submeterFormPadrao(form);
   	      	}
	  	}
    }
    
    function validaTodosRadioButton() {
		var form = document.forms[0];		

		if (!form.unidadeEsgoto[0].checked && !form.unidadeEsgoto[1].checked) {
			alert("Informe Indicador de Unidade Esgoto.");
			return false;
		}		
		if (!form.unidadeAbreRegistro[0].checked && !form.unidadeAbreRegistro[1].checked) {
			alert("Informe Indicador de Abre RA - Registro de Atendimento.");
			return false;
		}		

		if (!form.unidadeAceita[0].checked && !form.unidadeAceita[1].checked) {
			alert("Informe Indicador de Aceita Tramitação.");
			return false;
		}
		
		if (!form.unidadeCentralAtendimento[0].checked && !form.unidadeCentralAtendimento[1].checked) {
			alert("Informe Indicador de Central Atendimento.");
			return false;
		}

		if (!form.unidadeTarifaSocial[0].checked && !form.unidadeTarifaSocial[1].checked) {
			alert("Informe Indicador de Tarifa Social.");
			return false;
		}

		if(form.unidadeAbreRegistro[0].checked && form.meioSolicitacao.selectedIndex == 0){
			alert("Informe Meio de Solicitação.");		
			return false;
		}

		return true;
   	}
    

	function limparForm(limpaCodigo){

		var form = document.forms[0];

		form.idEmpresa.disabled=false;
		form.idLocalidade.disabled=false;
		form.sigla.disabled=false;
		form.gerenciaRegional.disabled=false;
		form.descricao.disabled=false;
		form.meioSolicitacao.disabled=false;

		if(limpaCodigo != 'false'){
			form.unidadeTipo.selectedIndex = 0;
		}

		form.codigoUnidadeTipo.value = "";
		form.descricao.value = "";
		form.sigla.value = "";
		
		form.gerenciaRegional.selectedIndex = 0;
		form.meioSolicitacao.selectedIndex = 0;

		form.unidadeEsgoto[0].checked = false;
		form.unidadeEsgoto[1].checked = false;
		
		form.unidadeAbreRegistro[0].checked = false;
		form.unidadeAbreRegistro[1].checked = false;

		form.unidadeAceita[0].checked = false;
		form.unidadeAceita[1].checked = false;
		
		form.unidadeCentralAtendimento[0].checked = false;
		form.unidadeCentralAtendimento[1].checked = false;

		form.unidadeTarifaSocial[0].checked = false;
		form.unidadeTarifaSocial[1].checked = false;
		
        form.idEmpresa.selectedIndex = "";
        
		limparPesquisaLocalidade();
		limparUnidadeSuperior();
		
	   	form.action='exibirInserirUnidadeOrganizacionalAction.do?menu=sim';
	   	form.submit();
		
	}

	function limparPesquisaLocalidade() {

    	var form = document.forms[0];
    	form.idLocalidade.value = "";
    	form.descricaoLocalidade.value = ""; 	
		if(	form.codigoUnidadeTipo.value == 'L' || 
			form.codigoUnidadeTipo.value == 'G'){
			form.descricao.value = "";
   		}
    	
  	}

    function limparUnidadeSuperior() {
        var form = document.forms[0];

        form.idUnidadeSuperior.value = "";
    	form.descricaoUnidadeSuperior.value = "";
    }

	//Chama Popup
	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){
		if(objetoRelacionado.disabled != true){
			if (objeto == null || codigoObjeto == null){
				abrirPopup(url + "?" + "menu=sim&"+"tipo=" + tipo, altura, largura);
			}
			else{
				if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
					alert(msg);
				}
				else{
					abrirPopup(url + "?" +"menu=sim&"+ "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
				}
			}
		}
	}

	//Recupera Dados Popup
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
		var form = document.forms[0];

	    if (tipoConsulta == 'localidade') {

	    	form.idLocalidade.value = codigoRegistro;
   	    	form.descricaoLocalidade.value = descricaoRegistro;
      		form.descricaoLocalidade.style.color = "#000000";

   			if(	form.codigoUnidadeTipo.value == 'L' || 
   				form.codigoUnidadeTipo.value == 'G'){
   				
   				form.descricao.value = descricaoRegistro;
				form.descricao.disabled=true;
   			}

   			form.action='exibirInserirUnidadeOrganizacionalAction.do?objetoConsulta=1';
   			form.submit();

	    }else if (tipoConsulta == 'unidadeSuperior') {
	    	form.idUnidadeSuperior.value = codigoRegistro;
   	    	form.descricaoUnidadeSuperior.value = descricaoRegistro;
      		form.descricaoUnidadeSuperior.style.color = "#000000";
	    }
	}

    function validarUnidadeTipo(ehGerencia) {
    	var form = document.forms[0];

		if(ehGerencia != 'true'){

    		limparForm('false');

    		form.action = 'exibirInserirUnidadeOrganizacionalAction.do'
    		form.submit();

		}else if(form.codigoUnidadeTipo.value == 'G'){

    		form.action = 'exibirInserirUnidadeOrganizacionalAction.do'
    		form.submit();
		}
    }
    
    function verificaIndicadorAbreRegistro(){
       	var form = document.forms[0];
    	
		if(form.unidadeAbreRegistro[1].checked){
			form.meioSolicitacao.selectedIndex = -1;
    		form.meioSolicitacao.disabled = true;
		}else{
			
			if(form.meioSolicitacao.disabled){
	   			form.meioSolicitacao.disabled=false;
			}
		}
    	
    }
	
	//verifica se existe alguma restrição 
	//para exibição alguma campo no formulario
    function verificaForm() {
       	var form = document.forms[0];

		//localidade
		if(form.codigoUnidadeTipo.value == 'L'){
			
			form.sigla.disabled=true;
			form.gerenciaRegional.disabled=true;
			form.descricao.disabled=true;
			form.idEmpresa.disabled=true;
			
		//gerencia regional
		}else if(form.codigoUnidadeTipo.value == 'G'){
			
			form.idLocalidade.disabled=true;
			form.descricao.disabled=true;
			form.idEmpresa.disabled=true;
			form.sigla.disabled=true;
			
		//centralizador
		}else if(form.codigoUnidadeTipo.value == 'C'){

			form.idLocalidade.disabled=true;
			form.gerenciaRegional.disabled=true;
			form.idEmpresa.disabled=true;

		//terceirizada
		}else if(form.codigoUnidadeTipo.value == 'T'){

			form.idLocalidade.disabled=true;
			form.gerenciaRegional.disabled=true;
		}

    }
</script>
</head>
<body leftmargin="5" topmargin="5" onload="javascript:verificaForm();">
<html:form action="/inserirUnidadeOrganizacionalAction.do"
	name="InserirUnidadeOrganizacionalActionForm"
	type="gcom.gui.cadastro.unidade.InserirUnidadeOrganizacionalActionForm"
	method="post">

	<html:hidden property="codigoUnidadeTipo" />

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="4" cellpadding="0">

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

			<td width="615" valign="top" class="centercoltext">
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
					<td class="parabg">Inserir Unidade Organizacional</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" />
					</td>
				</tr>
			</table>

			<p>&nbsp;</p>


			<!--Inicio da Tabela Unidade Organizacional -->
			<table width="100%" border="0">
				<tr>
					<td>
					<table width="100%" border="0" align="center">
						<tr>
							<td width="100%" height="10">Para inserir uma unidade
							organizacional, informe os dados gerais abaixo:</td>
						</tr>

						<tr bgcolor="#cbe5fe">
							<td align="center">
							<table width="100%" border="0" bgcolor="#99CCFF">
								<tr bgcolor="#99CCFF">
									<td colspan="2">
									<div align="center"><span class="style2"> Dados da Unidade
									Organizacional </span></div>
									</td>
								</tr>

								<tr bgcolor="#cbe5fe">
									<td>
									<table border="0" width="100%">

										<tr>
											<td><strong> Tipo da Unidade:</strong> <span class="style2">
											<strong> <font color="#FF0000">*</font> </strong> </span></td>

											<td><strong> <html:select property="unidadeTipo"
												style="width: 230px;"
												onchange="javascript:validarUnidadeTipo();">
												<html:option
													value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
													</html:option>

												<logic:present name="colecaoUnidadeTipo" scope="session">
													<html:options collection="colecaoUnidadeTipo"
														labelProperty="descricao" property="id" />

												</logic:present>
											</html:select> </strong></td>
										</tr>
										<tr>
											<td><strong>Localidade:</strong></td>
											<td width="63%"><html:text property="idLocalidade"
												size="4" maxlength="4"
												onkeypress="validaEnterComMensagem(event, 'exibirInserirUnidadeOrganizacionalAction.do?objetoConsulta=1', 'idLocalidade', 'Localidade');" />
											<a
												href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'localidade', null, null, 275, 480, '',document.forms[0].idLocalidade);">
											<img src="<bean:message key="caminho.imagens"/>pesquisa.gif"
												border="0" width="23" height="21" title="Pesquisar"></a> <logic:present
												name="corLocalidade">
												<logic:equal name="corLocalidade" value="exception">
													<html:text property="descricaoLocalidade" size="40"
														readonly="true"
														style="background-color:#EFEFEF; border:0; color: #ff0000" />
												</logic:equal>
												<logic:notEqual name="corLocalidade" value="exception">
													<html:text property="descricaoLocalidade" size="40"
														readonly="true"
														style="background-color:#EFEFEF; border:0; color: #000000" />
												</logic:notEqual>
											</logic:present> <logic:notPresent name="corLocalidade">
												<logic:empty name="InserirUnidadeOrganizacionalActionForm"
													property="idLocalidade">
													<html:text property="descricaoLocalidade" size="40"
														value="" readonly="true"
														style="background-color:#EFEFEF; border:0; color: #ff0000" />
												</logic:empty>
												<logic:notEmpty
													name="InserirUnidadeOrganizacionalActionForm"
													property="idLocalidade">
													<html:text property="descricaoLocalidade" size="40"
														readonly="true"
														style="background-color:#EFEFEF; border:0; color: #000000" />
												</logic:notEmpty>
											</logic:notPresent> <a
												href="javascript:limparPesquisaLocalidade();"> <img
												src="<bean:message key="caminho.imagens"/>limparcampo.gif"
												border="0" title="Apagar" /></a></td>
										</tr>
										<tr>
											<td><strong> Ger&ecirc;ncia Regional:</strong></td>

											<td><strong> <html:select property="gerenciaRegional"
												style="width: 230px;"
												onchange="javascript:validarUnidadeTipo('true');">
												<html:option
													value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
														</html:option>


												<logic:present name="colecaoGerenciaRegional"
													scope="session">
													<html:options collection="colecaoGerenciaRegional"
														labelProperty="nome" property="id" />
												</logic:present>

											</html:select> </strong></td>
										</tr>

										<tr>
											<td><strong>Descri&ccedil;&atilde;o</strong> <span
												class="style2"> <strong> <font color="#FF0000">*</font> </strong>
											</span></td>

											<td><html:text maxlength="40" property="descricao"
												size="40" /></td>
										</tr>

										<tr>
											<td><strong>Sigla:</strong></td>
											<td><html:text maxlength="20" property="sigla" size="20" /></td>
										</tr>

										<tr>
											<td><strong>Empresa:</strong> <span class="style2"> <strong>
											<font color="#FF0000">*</font> </strong> </span></td>

											<td><strong> <html:select property="idEmpresa"
												style="width: 230px;">
												<html:option
													value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
													</html:option>

												<logic:present name="colecaoEmpresa" scope="session">
													<html:options collection="colecaoEmpresa"
														labelProperty="descricao" property="id" />
												</logic:present>
											</html:select> </strong></td>

										</tr>
										<tr>
											<td><strong>Unidade Superior:</strong></td>
											<td width="63%"><html:text property="idUnidadeSuperior"
												size="6" maxlength="8"
												onkeypress="validaEnterComMensagem(event, 'exibirInserirUnidadeOrganizacionalAction.do?objetoConsulta=2', 'idUnidadeSuperior', 'Unidade Superior');" />
											<a
												href="javascript:chamarPopup('exibirPesquisarUnidadeSuperiorAction.do', 'unidadeSuperior', null, null, 275, 480, '',document.forms[0].idUnidadeSuperior);">
											<img src="<bean:message key="caminho.imagens"/>pesquisa.gif"
												border="0" width="23" height="21" title="Pesquisar"></a> <logic:present
												name="corUnidadeSuperior">
												<logic:equal name="corUnidadeSuperior" value="exception">
													<html:text property="descricaoUnidadeSuperior" size="40"
														readonly="true"
														style="background-color:#EFEFEF; border:0; color: #ff0000" />
												</logic:equal>
												<logic:notEqual name="corUnidadeSuperior" value="exception">
													<html:text property="descricaoUnidadeSuperior" size="40"
														readonly="true"
														style="background-color:#EFEFEF; border:0; color: #000000" />
												</logic:notEqual>
											</logic:present> <logic:notPresent name="corUnidadeSuperior">
												<logic:empty name="InserirUnidadeOrganizacionalActionForm"
													property="idUnidadeSuperior">
													<html:text property="descricaoUnidadeSuperior" size="40"
														value="" readonly="true"
														style="background-color:#EFEFEF; border:0; color: #ff0000" />
												</logic:empty>
												<logic:notEmpty
													name="InserirUnidadeOrganizacionalActionForm"
													property="idUnidadeSuperior">
													<html:text property="descricaoUnidadeSuperior" size="40"
														readonly="true"
														style="background-color:#EFEFEF; border:0; color: #000000" />
												</logic:notEmpty>
											</logic:notPresent> <a
												href="javascript:limparUnidadeSuperior();"> <img
												src="<bean:message key="caminho.imagens"/>limparcampo.gif"
												border="0" title="Apagar" /></a></td>
										</tr>

										<tr>
											<td><strong>Unidade de Esgoto?</strong> <span class="style2">
											<strong> <font color="#FF0000">*</font> </strong> </span></td>
											<td><html:radio property="unidadeEsgoto"
												value="<%=""+ConstantesSistema.SIM%>" /> <strong>Sim</strong>
											<html:radio property="unidadeEsgoto"
												value="<%=""+ConstantesSistema.NAO%>" /> <strong>N&atilde;o</strong>
											</td>
										</tr>

										<tr>
											<td><strong>Unidade Abre RA - Registro de Atendimento?</strong> <span
												class="style2"> <strong> <font color="#FF0000">*</font> </strong>
											</span></td>
											<td><html:radio property="unidadeAbreRegistro"
												value="<%=""+ConstantesSistema.SIM%>"
												onclick="javascript:verificaIndicadorAbreRegistro();" /> <strong>Sim</strong>
											<html:radio property="unidadeAbreRegistro"
												value="<%=""+ConstantesSistema.NAO%>"
												onclick="javascript:verificaIndicadorAbreRegistro();" /> <strong>N&atilde;o</strong>
											</td>
										</tr>

										<tr>
											<td><strong>Unidade Aceita Tramita&ccedil;&atilde;o?</strong>
											<span class="style2"> <strong> <font color="#FF0000">*</font>
											</strong> </span></td>
											<td><html:radio property="unidadeAceita"
												value="<%=""+ConstantesSistema.SIM%>" /> <strong>Sim</strong>
											<html:radio property="unidadeAceita"
												value="<%=""+ConstantesSistema.NAO%>" /> <strong>N&atilde;o</strong>
											</td>
										</tr>

										<tr>
											<td><strong>Unidade Central Atendimento</strong> <span
												class="style2"> <strong> <font color="#FF0000">*</font> </strong>
											</span></td>
											<td><html:radio property="unidadeCentralAtendimento"
												value="<%=""+ConstantesSistema.SIM%>" /> <strong>Sim</strong>
											<html:radio property="unidadeCentralAtendimento"
												value="<%=""+ConstantesSistema.NAO%>" /> <strong>N&atilde;o</strong>
											</td>
										</tr>

										<tr>
											<td><strong>Unidade Tarifa Social</strong> <span
												class="style2"> <strong> <font color="#FF0000">*</font> </strong>
											</span></td>
											<td><html:radio property="unidadeTarifaSocial"
												value="<%=""+ConstantesSistema.SIM%>" /> <strong>Sim</strong>
											<html:radio property="unidadeTarifaSocial"
												value="<%=""+ConstantesSistema.NAO%>" /> <strong>N&atilde;o</strong>
											</td>
										</tr>


										<tr>
											<td><strong> Meio de Solicita&ccedil;&atilde;o Padr&atilde;o:
											<span class="style2"> <strong> <font color="#FF0000">*</font>
											</strong> </span> </strong></td>

											<td><strong> <html:select property="meioSolicitacao"
												style="width: 230px;">
												<html:option
													value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
													</html:option>

												<logic:present name="colecaoMeioSolicitacao" scope="session">
													<html:options collection="colecaoMeioSolicitacao"
														labelProperty="descricao" property="id" />
												</logic:present>
											</html:select> </strong></td>
										</tr>

									</table>
									</td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td>
							<table width="100%">

								<tr>
									<td width="40%" align="left"><input type="button"
										name="ButtonReset" class="bottonRightCol" value="Desfazer"
										onClick="limparForm();"> <input type="button"
										name="ButtonCancelar" class="bottonRightCol" value="Cancelar"
										onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
									</td>

									<td align="right"><input name="Button" type="button"
										class="bottonRightCol" value="Inserir" onclick="validaForm();">
									</td>

								</tr>
							</table>
							</td>

						</tr>

					</table>
					</td>
				</tr>

			</table>

			</td>
		</tr>

	</table>
	<!-- Fim do Corpo - Rafael Pinto -->
	<%@ include file="/jsp/util/rodape.jsp"%>

</html:form>
</body>
</html:html>
