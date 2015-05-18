<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@ page import="gcom.atendimentopublico.registroatendimento.EspecificacaoTramite"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page import="gcom.util.ConstantesSistema"%><html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false" 
	formName="InserirTramiteEspecificacaoActionForm" />
	
<script type='text/javascript' src='<c:out value='${pageContext.request.contextPath}'/>/dwr/engine.js'> </script>
<script type='text/javascript' src='<c:out value='${pageContext.request.contextPath}'/>/dwr/util.js'> </script>
<script type='text/javascript' src='<c:out value='${pageContext.request.contextPath}'/>/dwr/interface/AjaxService.js'> </script>

<script language="JavaScript">

function validarForm(form){

	document.forms[0].target='';
    form.action="inserirTramiteEspecificacaoAction.do";
    submeterFormPadrao(form);
}

function validarAddColecao(form){

	if(validateInserirTramiteEspecificacaoActionForm(form)){
	    document.forms[0].target='';
	    form.action="exibirInserirTramiteEspecificacaoAction.do?operacao=adicionar";
	    submeterFormPadrao(form);
	}
}

function desfazer(){
	form = document.forms[0];
	document.forms[0].target='';
	form.action='exibirInserirTramiteEspecificacaoAction.do?limpaSessao=1';
	form.submit();
}

function limparLocalidade() {
    var form = document.InserirTramiteEspecificacaoActionForm;

    form.localidade.value = "";
    form.nomeLocalidade.value = "";
}

function limparSetorComercial(){
	var form = document.InserirTramiteEspecificacaoActionForm;

    form.codigoSetorComercial.value = "";
    form.nomeSetorComercial.value = "";
}

function limparBairro(){
	var form = document.InserirTramiteEspecificacaoActionForm;

    form.codigoBairro.value = "";
    form.nomeBairro.value = "";
}

function limparUnidadeOrigem(){
	var form = document.InserirTramiteEspecificacaoActionForm;

    form.unidadeOrganizacionalOrigem.value = "";
    form.nomeUnidadeOrganizacionalOrigem.value = "";
}

function limparUnidadeDestino(){
	var form = document.InserirTramiteEspecificacaoActionForm;

	form.unidadeOrganizacionalDestino.value = "";
	form.nomeUnidadeOrganizacionalDestino.value = "";
}

function limparDescricaoMunicipio() {
	var form = document.forms[0];
	form.nomeMunicipio.value = "";
}

function limparDescricaoSetorComercial() {
	var form = document.forms[0];
	form.nomeSetorComercial.value = "";
}

function limparDescricaoLocalidade() {
	var form = document.forms[0];
	form.nomeLocalidade.value = "";
}

function limparDescricaoBairro() {
	var form = document.forms[0];
	form.nomeBairro.value = "";
}

function limparDescricaoUnidadeOrigem() {
	var form = document.forms[0];
	form.nomeUnidadeOrganizacionalOrigem.value = "";
}

function limparDescricaoUnidadeDestino() {
	var form = document.forms[0];
	form.nomeUnidadeOrganizacionalDestino.value = "";
}

function limparMunicipio() {
	var form = document.forms[0];
	form.municipio.value = "";
	form.nomeMunicipio.value = "";
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

function setaUnidade(unidade){

	document.forms[0].campoUnidade.value = unidade;
}

//Recebe os dados do(s) popup(s)
function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	
	var form = document.forms[0];
	
	if (tipoConsulta == 'localidade') {
		form.localidade.value = codigoRegistro;
		form.nomeLocalidade.value = descricaoRegistro;
		form.nomeLocalidade.style.color = "#000000";
		form.codigoSetorComercial.focus();
	}
	
	if (tipoConsulta == 'setorComercial') {
		form.codigoSetorComercial.value = codigoRegistro;
		form.nomeSetorComercial.value = descricaoRegistro;
		form.nomeSetorComercial.style.color = "#000000";
		form.codigoBairro.focus();
	}
	
	if (tipoConsulta == 'bairro') {
		form.codigoBairro.value = codigoRegistro;
		form.nomeBairro.value = descricaoRegistro;
		form.nomeBairro.style.color = "#000000";
		form.sistemaAbastecimento.focus();
	}

	if (tipoConsulta == 'municipio') {
		if(form.municipio.value != codigoRegistro) {
			limparBairro();
		}

		form.municipio.value = codigoRegistro;
		form.nomeMunicipio.value = descricaoRegistro;
		form.nomeMunicipio.style.color = "#000000";
		form.codigoBairro.focus();
	}
	
	if (tipoConsulta == 'unidadeOrganizacional') {
		if(form.campoUnidade.value == '1'){
			form.unidadeOrganizacionalOrigem.value = codigoRegistro;
			form.nomeUnidadeOrganizacionalOrigem.value = descricaoRegistro;
			form.nomeUnidadeOrganizacionalOrigem.style.color = "#000000";
			form.unidadeOrganizacionalDestino.focus();
		}else if(form.campoUnidade.value == '2'){
			form.unidadeOrganizacionalDestino.value = codigoRegistro;
			form.nomeUnidadeOrganizacionalDestino.value = descricaoRegistro;
			form.nomeUnidadeOrganizacionalDestino.style.color = "#000000";
		}
	}
	
}



function consultarEspecificacao(obj) {

 	var idTipoSolicitacao = obj.options[obj.selectedIndex].value;
 
  	var selectSolicitacaoTipoEspecificacao = document.getElementsByName("solicitacaoTipoEspecificacao")[0];
  
  	var idSolicitacaoTipoEspecificacao = "${InserirTramiteEspecificacaoActionForm.solicitacaoTipoEspecificacao}";

  	selectSolicitacaoTipoEspecificacao.length=0;

  	if (idTipoSolicitacao != "-1") {
		  AjaxService.carregarEspecificacao( idTipoSolicitacao, 
		       function(especificacao) {
				  for (key in especificacao){
					  var novaOpcao = new Option(key, especificacao[key]);
					  if (key == idSolicitacaoTipoEspecificacao) {
					  		novaOpcao.selected = true;
					  }
					  selectSolicitacaoTipoEspecificacao.options[selectSolicitacaoTipoEspecificacao.length] = novaOpcao;
		  		   }
				  });
  	} else {

  		var novaOpcao = new Option(" ","-1");
  		selectSolicitacaoTipoEspecificacao.options[selectSolicitacaoTipoEspecificacao.length] = novaOpcao;
  	}	
}
</script>

</head>

<body leftmargin="5" topmargin="5"
	onload="setarFoco('${requestScope.nomeCampo}');">

<html:form action="/inserirTramiteEspecificacaoAction"
	name="InserirTramiteEspecificacaoActionForm"
	type="gcom.gui.atendimentopublico.registroatendimento.InserirTramiteEspecificacaoActionForm"
	method="post">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>
	
	<INPUT TYPE="hidden" name="campoUnidade">
	
	<table width="770" border="0" cellspacing="5" cellpadding="0">
		<tr>

			<td width="115" valign="top" class="leftcoltext">
			<div align="center">

			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>

			<%@ include file="/jsp/util/informacoes_usuario.jsp"%>

			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>

			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>

			<%@ include file="/jsp/util/mensagens.jsp"%>

			<p align="left">&nbsp;</p>

			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			</div>
			</td>
			<td width="600" valign="top" bgcolor="#003399" class="centercoltext">
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
					<td class="parabg">Inserir Tramite por Especifica��o</td>

					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
				<tr>
					<td height="5" colspan="3"></td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">
					<p>Para adicionar um Tramite por Especifica��o, informe os dados abaixo:</p>
					<p>&nbsp;</p>
					</td>
				</tr>
				<tr>
					<td width="28%"><strong>Tipo de Solicita��o:<font color="#FF0000">*</font></strong></td>
					<td><html:select property="tipoSolicitacao" style="width: 350px;" tabindex="1"
						onchange="consultarEspecificacao(this);">
						<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="colecaoSolicitacaoTipo"
							labelProperty="descricaoComId" property="id" />
					</html:select></td>
				</tr>
				<tr>
					<td width="28%"><strong>Especifica��o:<font color="#FF0000">*</font></strong></td>
					<td><html:select property="solicitacaoTipoEspecificacao" style="width: 350px;" tabindex="2">
						<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present name="colecaoSolicitacaoTipoEspecificacao">
						<html:options collection="colecaoSolicitacaoTipoEspecificacao"
							labelProperty="descricaoComId" property="id" />
						</logic:present>
					</html:select></td>
				</tr>
				<tr>
		            <td width="28%"><strong>Localidade:</strong></td>
		            <td>
						<html:text maxlength="3" property="localidade" size="3" onkeypress="javascript:limparDescricaoLocalidade();limparSetorComercial();validaEnterComMensagem(event, 'exibirInserirTramiteEspecificacaoAction.do?objetoConsulta=1','localidade','C�digo da localidade');" 
							tabindex="3" />
		                <a	href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do?indicadorUsoTodos=1', 'localidade', null, null, 275, 480, '');limparSetorComercial();"><img
								src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"
								width="23" height="21" title="Pesquisar"></a> 
						<logic:present name="localidadeNaoEncontrada" scope="request">
							<html:text property="nomeLocalidade" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:present>
		
						<logic:notPresent name="localidadeNaoEncontrada" scope="request">
							<html:text property="nomeLocalidade" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color:#000000"/>
						</logic:notPresent>
						<a href="javascript:limparLocalidade();limparSetorComercial();document.forms[0].localidade.focus();"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
						</a>
					</td>
		        </tr>
		        <tr>
		           <td width="28%"><strong>Setor Comercial:</strong></td>
		           <td>
				   
					   <html:text maxlength="3" property="codigoSetorComercial" size="3" 
					   		onkeypress="javascript:limparDescricaoSetorComercial();validaEnterDependencia(event, 'exibirInserirTramiteEspecificacaoAction.do?objetoConsulta=2', this, document.forms[0].localidade.value, 'Localidade');"
					   		tabindex="4" />
					   <a	href="javascript:abrirPopupDependencia('exibirPesquisarSetorComercialAction.do?idLocalidade='+document.forms[0].localidade.value+'&tipo=tramiteEspecificacao',document.forms[0].localidade.value,'Localidade', 400, 800);">
					   			<img src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"
								width="23" height="21" title="Pesquisar"></a> 
						<logic:present name="setorComercialNaoEncontrado" scope="request">
							<html:text property="nomeSetorComercial" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
						</logic:present>
		
						<logic:notPresent name="setorComercialNaoEncontrado" scope="request">
							<html:text property="nomeSetorComercial" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
						</logic:notPresent>
					   
						<a href="javascript:limparSetorComercial();document.forms[0].codigoSetorComercial.focus();"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
						</a>
						<html:hidden property="idSetorComercial"/>
		
					</td>
		        </tr>
		        <tr> 
					<td width="28%"><strong>Munic�pio:</strong></td>
					<td height="24" colspan="2">
						<html:text property="municipio" size="3" maxlength="4"  tabindex="4"
							onkeypress="javascript:limparDescricaoMunicipio();limparBairro();validaEnterComMensagem(event, 'exibirInserirTramiteEspecificacaoAction.do?objetoConsulta=3', 'municipio', 'Munic�pio');"/>
						<a
							href="javascript:abrirPopup('exibirPesquisarMunicipioAction.do?indicadorUsoTodos=1', 400, 800);limparBairro();">
						<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23"	height="21" name="imagem" alt="Pesquisar" border="0"></a>
						
						<logic:present name="municipioNaoEncontrado" scope="request">
							<html:text property="nomeMunicipio" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
						</logic:present> 

						<logic:notPresent name="municipioNaoEncontrado" scope="request">
							<html:text property="nomeMunicipio" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
						</logic:notPresent>
						
						<a href="javascript:limparMunicipio();limparBairro();document.forms[0].municipio.focus();">
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar"/></a>
					</td>
				</tr>
		        <tr>
					<td width="28%"><strong>Bairro:</strong></td>
					<td height="24" colspan="2"><html:text maxlength="4"
						property="codigoBairro" size="3" tabindex="5"
						onkeypress="javascript:limparDescricaoBairro();validaEnterDependencia(event, 'exibirInserirTramiteEspecificacaoAction.do?objetoConsulta=4', this, document.forms[0].municipio.value, 'Munic�pio');"/>
					<a
						href="javascript:abrirPopupDependencia('exibirPesquisarBairroAction.do?idMunicipio='+document.forms[0].municipio.value+'&indicadorUsoTodos=1', document.forms[0].municipio.value, 'Munic�pio', 400, 800);">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Bairro" /></a> 
						<logic:present name="bairroNaoEncontrado" scope="request">
							<html:text property="nomeBairro" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
						</logic:present> 

						<logic:notPresent name="bairroNaoEncontrado" scope="request">
							<html:text property="nomeBairro" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
						</logic:notPresent>
						
					<a href="javascript:limparBairro();document.forms[0].codigoBairro.focus();">
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a>
						<html:hidden property="idBairro"/>
					</td>
				</tr>
			
				
				<tr>
		            <td width="28%"><strong>Unidade Origem:<font color="#FF0000"></font></strong></td>
		            <td>
						<html:text maxlength="8" property="unidadeOrganizacionalOrigem" size="6" onkeypress="javascript:limparDescricaoUnidadeOrigem();validaEnterComMensagem(event, 'exibirInserirTramiteEspecificacaoAction.do?objetoConsulta=5','unidadeOrganizacionalOrigem','Unidade Origem');" 
							tabindex="14" />
		                <a	href="javascript:chamarPopup('exibirPesquisarUnidadeOrganizacionalAction.do?indicadorUsoTodos=1&limpaForm=sim', 'unidadeOrganizacional', null, null, 275, 480, '');setaUnidade(1);"><img
								src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"
								width="23" height="21" title="Pesquisar"></a> 
						<logic:present name="unidadeOrigemNaoEncontrado" scope="request">
							<html:text property="nomeUnidadeOrganizacionalOrigem" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
						</logic:present> 

						<logic:notPresent name="unidadeOrigemNaoEncontrado" scope="request">
							<html:text property="nomeUnidadeOrganizacionalOrigem" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
						</logic:notPresent>
						
						<a href="javascript:limparUnidadeOrigem();document.forms[0].unidadeOrganizacionalOrigem.focus();"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
						</a>
					</td>
		        </tr>
		        <tr>
		            <td width="28%"><strong>Unidade Destino:<font color="#FF0000">*</font></strong></td>
		            <td>
						<html:text maxlength="8" property="unidadeOrganizacionalDestino" size="6" onkeypress="javascript:limparDescricaoUnidadeDestino();validaEnterComMensagem(event, 'exibirInserirTramiteEspecificacaoAction.do?objetoConsulta=6','unidadeOrganizacionalDestino','Unidade Destino');" 
							tabindex="15" />
		                <a	href="javascript:chamarPopup('exibirPesquisarUnidadeOrganizacionalAction.do?indicadorUsoTodos=1&limpaForm=sim', 'unidadeOrganizacional', null, null, 275, 480, '');setaUnidade(2);"><img
								src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"
								width="23" height="21" title="Pesquisar"></a> 
						<logic:present name="unidadeDestinoNaoEncontrado" scope="request">
							<html:text property="nomeUnidadeOrganizacionalDestino" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
						</logic:present> 

						<logic:notPresent name="unidadeDestinoNaoEncontrado" scope="request">
							<html:text property="nomeUnidadeOrganizacionalDestino" size="40" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
						</logic:notPresent>
						
						<a href="javascript:limparUnidadeDestino();document.forms[0].unidadeOrganizacionalDestino.focus();"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
						</a>
					</td>
		        </tr>
		        
		        
		        <tr>
			       	<td width="25%" height="24">
			       		<strong>Unidade do Primeiro Tr�mite?<span class="style3"><font color="#FF0000">*</font></span></strong>
			       	</td>
			       	<td align="left"><label> <html:radio property="indicadorPrimeiroTramite" value="1" /> <strong>Sim</strong></label>
					<label> <html:radio property="indicadorPrimeiroTramite" value="2" /> <strong>N�o</strong></label></td>
			     </tr>
				<tr>
					<td height="24" colspan="2">
					<hr>
					</td>
				</tr>
				<tr>
					<td><strong>Tr�mites Inclu�dos<font
						color="#FF0000"></font></strong></td>
					<td align="right"><input type="button" name="Submit24"
						class="bottonRightCol" value="Adicionar"
						onclick="validarAddColecao(document.forms[0]);"
						tabindex="16"></td>
				</tr>

				<tr>
					<td style="position: relative">
						<div style="position: absolute; width: 595px; height: 250; overflow: auto;" >
						<table bgcolor="#90c7fc" border="0" style="">
							<tr bordercolor="#FFFFFF" bgcolor="#79BBFD">
								<td>
									<div align="center">
										<strong>
											Remover
										</strong>
									</div>
								</td>
								<td align="center"><font color="#000000"><strong>Especifica��o</strong></font></td>
								<td align="center"><font color="#000000"><strong>Localidade</strong></font></td>
								<td align="center"><font color="#000000"><strong>Setor Comercial</strong></font></td>
								<td align="center"><font color="#000000"><strong>Bairro</strong></font></td>							
								<td align="center"><font color="#000000"><strong>Unidade Origem</strong></font></td>
								<td align="center"><font color="#000000"><strong>Unidade Destino</strong></font></td>
								<td align="center"><font color="#000000"><strong>Unidade do Primeiro Tr�mite</strong></font></td>
							</tr>
							<logic:present name="colecaoTramiteEspecificacao">
							<%int cont = 0;%>
								<logic:iterate name="colecaoTramiteEspecificacao" id="especificacaoTramite" type="EspecificacaoTramite">
										<%cont = cont + 1;
										if (cont % 2 == 0) {%>
										<tr bgcolor="#cbe5fe">
										<%} else {%>
										<tr bgcolor="#FFFFFF">
										<%}%>
											
											<td width="15%">
												<div align="center"><font color="#333333"> <img width="14"
													height="14" border="0"
													src="<bean:message key="caminho.imagens"/>Error.gif"
													onclick="javascript:document.forms[0].target='';if(confirm('Confirma remo��o?')){redirecionarSubmit('exibirInserirTramiteEspecificacaoAction.do?codigoTramiteEspecificacao=<bean:write name="especificacaoTramite" property="ultimaAlteracao.time"/>&operacao=remover');}" />
											</font></div>
											</td>
											<td>
												<div align="center">
												<logic:notEmpty name="especificacaoTramite" property="solicitacaoTipoEspecificacao">
													<bean:write name="especificacaoTramite" property="solicitacaoTipoEspecificacao.descricao" />
												</logic:notEmpty>
												</div>
											</td>
											<td>
												<div align="center">
												<logic:notEmpty name="especificacaoTramite" property="localidade">
													<bean:write name="especificacaoTramite" property="localidade.descricaoComId" />
												</logic:notEmpty>
												</div>
											</td>
											<td>
												<div align="center">
												<logic:notEmpty name="especificacaoTramite" property="setorComercial">
													<bean:write name="especificacaoTramite" property="setorComercial.descricaoComCodigo" />
												</logic:notEmpty>
												</div>
											</td>
											<td>
												<div align="center">
												<logic:notEmpty name="especificacaoTramite" property="bairro">
													<bean:write name="especificacaoTramite" property="bairro.nomeComCodigo" />
												</logic:notEmpty>
												</div>
											</td>
										
											<td>
												<div align="center">
												<logic:notEmpty name="especificacaoTramite" property="unidadeOrganizacionalOrigem">
													<bean:write name="especificacaoTramite" property="unidadeOrganizacionalOrigem.descricaoComId" />
												</logic:notEmpty>
												</div>
											</td>
											<td>
												<div align="center">
												<logic:notEmpty name="especificacaoTramite" property="unidadeOrganizacionalDestino">
													<bean:write name="especificacaoTramite" property="unidadeOrganizacionalDestino.descricaoComId" />
												</logic:notEmpty>
												</div>
											</td>
											<td>
												<div align="center">
												<logic:notEmpty name="especificacaoTramite" property="indicadorPrimeiroTramite">
													
													
												   <logic:equal name="especificacaoTramite" property="indicadorPrimeiroTramite" value="1">
													  SIM
												  </logic:equal>
												  <logic:equal name="especificacaoTramite" property="indicadorPrimeiroTramite" value="2">	
												    N�O
												  </logic:equal>
													
													
													
												</logic:notEmpty>
												</div>
											</td>
										</tr>
								</logic:iterate>
							</logic:present>
						</table>
						</div>
						<p style="height: 255">&nbsp;</p>
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
					<hr>
					</td>
				</tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td valign="top"><input name="button" type="button"
						class="bottonRightCol" value="Desfazer" onclick="desfazer();">&nbsp;<input
						type="button" name="ButtonCancelar" class="bottonRightCol"
						value="Cancelar"
						onClick="javascript:document.forms[0].target='';window.location.href='/gsan/telaPrincipal.do'"></td>
					<td valign="top">
					<div align="right"><input name="botaoInserir" type="button"
						class="bottonRightCol" value="Inserir"
						onclick="validarForm(document.forms[0]);" tabindex="17"></div>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>
</body>
</html:form>
</html:html>
