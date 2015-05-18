<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ page import="gcom.util.ConstantesSistema" %>
<%@ page import="gcom.cadastro.endereco.LogradouroCep" %>
<%@ page import="java.util.Collection"%>

<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key='caminho.css'/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>endereco.js" ></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>

<script language="JavaScript">
function validarFormEndereco(formulario){

	var objCep = returnObject(formulario, "cep");
	var objCepSelecionado = returnObject(formulario, "cepSelecionado");
	var objLogradouro = returnObject(formulario, "logradouro");
	var objLogradouroDescricao = returnObject(formulario, "logradouroDescricao");
	var objCepUnico = returnObject(formulario, "cepUnico");
	var objNumero = returnObject(formulario, "numero");
	var objCepCarregado = document.getElementById("cepCarregado");
	var associacaoExistente = returnObject(formulario, "associacaoExistente");
	
	if (!verificarSelecaoCep(objCepSelecionado)){
		formulario.flagCepSelecionado.value = "";
	}
	else{
		formulario.flagCepSelecionado.value = "CARREGADO";
	}
	
	if (validateInserirEnderecoActionForm(formulario)){
		if (objCep.value.length > 0 && !testarCampoValorZero(objCep, "CEP")){
			objCep.focus();
		}
		else if (objCepCarregado == null){
			alert("A pesquisa do CEP não foi realizada. Confirme a realização da mesma");
			objCep.focus();
		}
		else if (objLogradouro.value.length > 0 && !testarCampoValorZero(objCep, "Logradouro")){
			objLogradouro.focus();
		}
		else if (objLogradouro.disabled == false 
			&& (objLogradouro.value.length < 1 || objLogradouroDescricao.value.length < 1)){
			alert("A pesquisa do Logradouro não foi realizada. Confirme a realização da mesma");
			objLogradouro.focus();
		}
		else if (associacaoExistente.value == "FALSO"){
			if (confirm("Este logradouro ainda não está associado a este CEP. Confirma a associação ?")){
				submeterFormPadrao(formulario);
			}
		}
		else {
			submeterFormPadrao(formulario);
		}
	}
}

function verificarSelecaoCep(objetoCepSelecionado){
	form = document.forms[0];
	retorno = false;

	for(indice = 0; indice < form.elements.length; indice++){
		if (form.elements[indice].type == "radio" && form.elements[indice].checked == true) {
			retorno = true;
			break;
		}
	}
	
	return retorno;
}


function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
    var form = document.forms[0];

    if (tipoConsulta == 'cep') {
	    form.cep.value = codigoRegistro;
      
    	//Código responsável pela atualização automática da coleção
	    form.action = "exibirInserirLogradouroAction.do";
		submeterFormPadrao(form);
    }if(tipoConsulta == 'imovel'){
		//Código responsável pela atualização automática da coleção
		 form.idMatriculaImovel.value = codigoRegistro;
		
		 form.action = "exibirInserirLogradouroAction.do";
	     form.submit();
	}
}
  
function limparPesquisaCep(){
  	var form = document.forms[0];
  	form.cep.value = "";
    
	if (form.logradouro.disabled){
    	form.logradouro.disabled = false;
    	form.logradouro.value = "";
    	form.logradouroDescricao.value = "";
    	from.idMatriculaImovel.value="";
    }
    
    form.action = "exibirInserirEnderecoAction.do?removerCep=OK";
	submeterFormPadrao(form);
}
function limparLogradouro(){
  	var form = document.forms[0];
  	form.logradouro.value = "";
    
	if (form.logradouro.disabled){
    	form.logradouro.disabled = false;
    	form.logradouro.cep = "";
    	form.logradouroDescricao.value = "";
    	from.idMatriculaImovel.value="";
    }
    
    form.action = "exibirInserirEnderecoAction.do?limparLogradouro=true";
    form.action = "exibirInserirEnderecoAction.do?removerCep=OK";
	submeterFormPadrao(form);
}

  
function selecionarCep(){
	var form = document.forms[0];
	var primeiroRadio;
	var qtdRadio = 0;
	
	for(indice = 0; indice < form.elements.length; indice++){
		if (form.elements[indice].type == "radio") {
			primeiroRadio = form.elements[indice];
			qtdRadio++;
		}
	}
	
	if (qtdRadio == 1){
		primeiroRadio.checked = true;
	}
}

function pesquisarCepAtravesLogradouro(tecla){

	var form = document.forms[0];
	
	if (form.cep.value.length > 0){
		
		validaEnterComMensagemAceitaZERO(tecla, 'exibirInserirEnderecoAction.do?pesquisarLogradouro=OK&pesquisarCep=OK', 'logradouro', 'Logradouro');
	}
	else{
		
		validaEnterComMensagemAceitaZERO(tecla, 'exibirInserirEnderecoAction.do?pesquisarLogradouro=OK', 'logradouro', 'Logradouro');
	}
}


function pesquisarCepAtravesLogradouroPopUp(){
	var form = document.forms[0];
	
	if (form.cep.value.length > 0){
		
		redirecionarSubmit('/gsan/exibirInserirEnderecoAction.do?pesquisarCep=OK');
		//abrirPopup('exibirPesquisarLogradouroAction.do?caminhoRetornoTelaPesquisaLogradouro=exibirInserirEnderecoAction');
		redirecionarSubmit('/gsan/exibirPesquisarLogradouroAction.do?caminhoRetornoTelaPesquisaLogradouro=exibirInserirEnderecoAction');
	}
	else{
		
		redirecionarSubmit('/gsan/exibirInserirEnderecoAction.do');
		//abrirPopup('exibirPesquisarLogradouroAction.do?caminhoRetornoTelaPesquisaLogradouro=exibirInserirEnderecoAction');
		redirecionarSubmit('/gsan/exibirPesquisarLogradouroAction.do?caminhoRetornoTelaPesquisaLogradouro=exibirInserirEnderecoAction');
	}
}

function janela(url, target, largura, altura) {
    if (url == "") {
 return;
    }
    
    //url= url+"ehPopup=t&openWindow=t";
    msgWin=window.open(url, target,"location=no,screenX=100,screenY=100,toolbar=no,directories=no,menubar=no,status=yes,scrollbars=yes,resizeable=yes,width=" + largura + ",height=" + altura + ",top=0,left=0");

    

}

//-->
</script>

<logic:equal name="tipoPesquisaRetorno" value="cliente">
	<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="InserirEnderecoActionForm"/>
</logic:equal>

<logic:notEqual name="tipoPesquisaRetorno" value="cliente">
	<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="PesquisarActionForm" dynamicJavascript="false" />
	<script type="text/javascript" language="Javascript1.1"> 
	    var bCancel = false; 
	    function validateInserirEnderecoActionForm(form) {                                                                   
	        if (bCancel) 
	    		return true; 
	        else 
	       		return validateCaracterEspecial(form) && validateLong(form) && validateRequired(form); 
	   	} 
	
	    function caracteresespeciais () { 
		    this.aa = new Array("cep", "CEP possui caracteres especiais.", new Function ("varName", " return this[varName];"));
		    this.ab = new Array("logradouro", "Logradouro possui caracteres especiais.", new Function ("varName", " return this[varName];"));
		    this.ac = new Array("bairro", "Bairro possui caracteres especiais.", new Function ("varName", " return this[varName];"));
		    this.ad = new Array("numero", "Número possui caracteres especiais.", new Function ("varName", " return this[varName];"));
		    this.ae = new Array("complemento", "Complemento possui caracteres especiais.", new Function ("varName", " return this[varName];"));
	    } 
	
	    function IntegerValidations () { 
		    this.aa = new Array("cep", "CEP deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
		    this.ab = new Array("logradouro", "Logradouro deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
		    this.ac = new Array("bairro", "Bairro deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
		} 
	
	    function required () {
		    this.aa = new Array("flagCepSelecionado", "Informe CEP.", new Function ("varName", " return this[varName];")); 
		    this.ab = new Array("logradouro", "Informe Logradouro.", new Function ("varName", " return this[varName];")); 
		    this.ac = new Array("bairro", "Informe Bairro.", new Function ("varName", " return this[varName];"));
		    this.ad = new Array("numero", "Informe Número.", new Function ("varName", " return this[varName];"));
	    }
	 </script>
</logic:notEqual>
</head>

<logic:equal name="flagReload" value="false">
	<logic:present name="objetoCep">
		<body leftmargin="5" topmargin="5" onload="window.focus();resizePageSemLink(700, 570); setarFoco('${requestScope.nomeCampo}');">
	</logic:present>
	<logic:notPresent name="objetoCep">
		<body leftmargin="5" topmargin="5" onload="window.focus();resizePageSemLink(700, 570); setarFoco('${requestScope.nomeCampo}');">
	</logic:notPresent>
</logic:equal>

<logic:equal name="flagReload" value="true">
	<logic:equal name="flagRedirect" value="cliente">
		<logic:present name="fecharPopup">
			<logic:equal name="flagOperacao" value="1">
				<body leftmargin="5" topmargin="5" onload="chamarReload('inserirClienteWizardAction.do?action=exibirInserirClienteEnderecoAction');window.close();">
			</logic:equal>
			<logic:equal name="flagOperacao" value="2">
				<body leftmargin="5" topmargin="5" onload="chamarReload('atualizarClienteWizardAction.do?action=exibirAtualizarClienteEnderecoAction');window.close();">
			</logic:equal>
		</logic:present>
		<logic:notPresent name="fecharPopup">
			<logic:equal name="flagOperacao" value="1">
				<body leftmargin="5" topmargin="5" onload="chamarReload('inserirClienteWizardAction.do?action=exibirInserirClienteEnderecoAction');resizePageSemLink(700, 570); document.InserirEnderecoActionForm.reset();setarFoco('${requestScope.nomeCampo}');">
			</logic:equal>
			<logic:equal name="flagOperacao" value="2">
				<body leftmargin="5" topmargin="5" onload="chamarReload('atualizarClienteWizardAction.do?action=exibirAtualizarClienteEnderecoAction');resizePageSemLink(700, 570); document.InserirEnderecoActionForm.reset();setarFoco('${requestScope.nomeCampo}');">
			</logic:equal>
		</logic:notPresent>
	</logic:equal>
	<logic:equal name="flagRedirect" value="unidadeOperacional">
		<logic:present name="fecharPopup">
			<logic:equal name="flagOperacao" value="1">
				<body leftmargin="0" topmargin="0" onload="chamarReloadSubmetForm('exibirInserirUnidadeOperacionalAction.do');window.close();">
			</logic:equal>
			<logic:equal name="flagOperacao" value="2">
				<body leftmargin="0" topmargin="0" onload="chamarReloadSubmetForm('exibirAtualizarUnidadeOperacionalAction.do?recarregar=true');window.close();">
			</logic:equal>
		</logic:present>
	</logic:equal>
	<logic:equal name="flagRedirect" value="distritoOperacional">
		<logic:present name="fecharPopup">
			<logic:equal name="flagOperacao" value="1">
				<body leftmargin="0" topmargin="0" onload="chamarReloadSubmetForm('exibirInserirDistritoOperacionalAction.do');window.close();">
			</logic:equal>
			<logic:equal name="flagOperacao" value="2">
				<body leftmargin="0" topmargin="0" onload="chamarReloadSubmetForm('exibirAtualizarDistritoOperacionalAction.do?recarregar=true');window.close();">
			</logic:equal>
		</logic:present>
	</logic:equal>
	<logic:equal name="flagRedirect" value="imovel">
		<logic:present name="fecharPopup">
			<logic:equal name="flagOperacao" value="1">
				<body leftmargin="5" topmargin="5" onload="chamarReloadSubmetForm('inserirImovelWizardAction.do?action=exibirInserirImovelEnderecoAction');window.close();">
			</logic:equal>
			<logic:equal name="flagOperacao" value="2">
				<body leftmargin="5" topmargin="5" onload="chamarReloadSubmetForm('atualizarImovelWizardAction.do?action=exibirManterImovelAction&pagina=2');window.close();">
			</logic:equal>
		</logic:present>
		<logic:notPresent name="fecharPopup">
			<logic:equal name="flagOperacao" value="1">
				<body leftmargin="5" topmargin="5" onload="chamarReload('inserirImovelWizardAction.do?action=exibirInserirImovelEnderecoAction');resizePageSemLink(700, 570); document.InserirEnderecoActionForm.reset();setarFoco('${requestScope.nomeCampo}');">
			</logic:equal>
			<logic:equal name="flagOperacao" value="2">
				<body leftmargin="5" topmargin="5" onload="chamarReload('atualizarImovelWizardAction.do?action=exibirManterImovelAction&pagina=2');resizePageSemLink(700, 570); document.InserirEnderecoActionForm.reset();setarFoco('${requestScope.nomeCampo}');">
			</logic:equal>
		</logic:notPresent>
	</logic:equal>
	<logic:equal name="flagRedirect" value="localidade">
		<logic:present name="fecharPopup">
			<logic:equal name="flagOperacao" value="1">
				<body leftmargin="5" topmargin="5" onload="chamarSubmitEspecial('1');window.close();">
			</logic:equal>
			<logic:equal name="flagOperacao" value="2">
				<body leftmargin="5" topmargin="5" onload="chamarSubmitEspecial('2');window.close();">
			</logic:equal>
		</logic:present>
		<logic:notPresent name="fecharPopup">
			<logic:equal name="flagOperacao" value="1">
				<body leftmargin="5" topmargin="5" onload="chamarSubmitEspecial('1');resizePageSemLink(700, 570); document.InserirEnderecoActionForm.reset();setarFoco('${requestScope.nomeCampo}');">
			</logic:equal>
			<logic:equal name="flagOperacao" value="2">
				<body leftmargin="5" topmargin="5" onload="chamarSubmitEspecial('2');resizePageSemLink(700, 570); document.InserirEnderecoActionForm.reset();setarFoco('${requestScope.nomeCampo}');">
			</logic:equal>
		</logic:notPresent>
	</logic:equal>
	<logic:equal name="flagRedirect" value="gerenciaRegional">

		<logic:present name="fecharPopup">
		
			<logic:equal name="flagOperacao" value="1">
				<body leftmargin="5" topmargin="5" onload="chamarSubmitEspecial('1');window.close();">
			</logic:equal>
			<logic:equal name="flagOperacao" value="2">
				<body leftmargin="5" topmargin="5" onload="chamarSubmitEspecial('2');window.close();">
			</logic:equal>

		</logic:present>

		<logic:notPresent name="fecharPopup">
		
			<logic:equal name="flagOperacao" value="1">
				<body leftmargin="5" topmargin="5" onload="chamarSubmitEspecial('1');resizePageSemLink(510, 450); document.InserirEnderecoActionForm.reset();setarFoco('${requestScope.nomeCampo}');">
			</logic:equal>
			<logic:equal name="flagOperacao" value="2">
				<body leftmargin="5" topmargin="5" onload="chamarSubmitEspecial('2');resizePageSemLink(510, 450); document.InserirEnderecoActionForm.reset();setarFoco('${requestScope.nomeCampo}');">
			</logic:equal>

		</logic:notPresent>
	
	</logic:equal>
	<logic:equal name="flagRedirect" value="bacia">

		<logic:present name="fecharPopup">
		
			<logic:equal name="flagOperacao" value="1">
				<body leftmargin="0" topmargin="0" onload="chamarSubmitEspecial('1');window.close();">
			</logic:equal>
			<logic:equal name="flagOperacao" value="2">
				<body leftmargin="0" topmargin="0" onload="chamarSubmitEspecial('2');window.close();">
			</logic:equal>

		</logic:present>

		<logic:notPresent name="fecharPopup">
		
			<logic:equal name="flagOperacao" value="1">
				<body leftmargin="0" topmargin="0" onload="chamarSubmitEspecial('1');resizePageSemLink(510, 450); document.InserirEnderecoActionForm.reset();setarFoco('${requestScope.nomeCampo}');">
			</logic:equal>
			<logic:equal name="flagOperacao" value="2">
				<body leftmargin="0" topmargin="0" onload="chamarSubmitEspecial('2');resizePageSemLink(510, 450); document.InserirEnderecoActionForm.reset();setarFoco('${requestScope.nomeCampo}');">
			</logic:equal>

		</logic:notPresent>
	
	</logic:equal>
	<logic:equal name="flagRedirect" value="agencia">
		<logic:present name="fecharPopup">
			<logic:equal name="flagOperacao" value="1">
				<body leftmargin="5" topmargin="5" onload="chamarSubmitEspecial('1');window.close();">
			</logic:equal>
			<logic:equal name="flagOperacao" value="2">
				<body leftmargin="5" topmargin="5" onload="chamarSubmitEspecial('2');window.close();">
			</logic:equal>
		</logic:present>
		<logic:notPresent name="fecharPopup">
			<logic:equal name="flagOperacao" value="1">
				<body leftmargin="5" topmargin="5" onload="chamarSubmitEspecial('1');resizePageSemLink(700, 570); document.InserirEnderecoActionForm.reset();setarFoco('${requestScope.nomeCampo}');">
			</logic:equal>
			<logic:equal name="flagOperacao" value="2">
				<body leftmargin="5" topmargin="5" onload="chamarSubmitEspecial('2');resizePageSemLink(700, 570); document.InserirEnderecoActionForm.reset();setarFoco('${requestScope.nomeCampo}');">
			</logic:equal>
		</logic:notPresent>
	</logic:equal>
	<logic:equal name="flagRedirect" value="registroAtendimento">
		<logic:present name="fecharPopup">
			<logic:equal name="flagOperacao" value="1">
				<body leftmargin="5" topmargin="5" onload="chamarReload('inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosLocalOcorrenciaAction');window.close();">
			</logic:equal>
			<logic:equal name="flagOperacao" value="2">
				<body leftmargin="5" topmargin="5" onload="chamarReload('inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosSolicitanteAction&retornoEndereco=OK');window.close();">
			</logic:equal>
			<logic:equal name="flagOperacao" value="3">
				<body leftmargin="5" topmargin="5" onload="chamarReload('atualizarRegistroAtendimentoWizardAction.do?action=exibirAtualizarRegistroAtendimentoDadosLocalOcorrenciaAction&retornoEndereco=OK');window.close();">
			</logic:equal>
			<logic:equal name="flagOperacao" value="4">
				<body leftmargin="5" topmargin="5" onload="chamarReload('atualizarRegistroAtendimentoWizardAction.do?action=exibirAtualizarRegistroAtendimentoDadosSolicitanteAction&retornoEndereco=OK');window.close();">
			</logic:equal>
		</logic:present>
		<logic:notPresent name="fecharPopup">
			<logic:equal name="flagOperacao" value="4">
				<body leftmargin="5" topmargin="5" onload="redirecionarSubmit('exibirAdicionarSolicitanteRegistroAtendimentoAction.do');">
			</logic:equal>
		</logic:notPresent>
	</logic:equal>
</logic:equal>

<html:form action="/inserirEnderecoAction" method="post">
<table width="650" border="0" cellpadding="0" cellspacing="5">
	<tr>
		<html:hidden property="associacaoExistente"/>
		<html:hidden property="tipoAcao"/>
		<html:hidden property="objetoClienteEnderecoSelecionado"/>
		<html:hidden property="enderecoCorrespondencia"/>
		<html:hidden property="flagCepSelecionado"/>
	    <td width="640" valign="top" class="centercoltext">
	    	<table height="100%">
	        	<tr><td></td></tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		        <tr>
		          <td width="11"><img src="<bean:message key='caminho.imagens'/>parahead_left.gif" editor="Webstyle4" moduleid="Album Photos (Project)\toptab_page2_parahead_left.xws" border="0" /></td>
		          <td class="parabg">Informar Endere&ccedil;o</td>
		          <td width="11"><img src="<bean:message key='caminho.imagens'/>parahead_right.gif" editor="Webstyle4" moduleid="Album Photos (Project)\toptab_page2_parahead_right.xws" border="0" /></td>
		        </tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">Preencha os campos para inserir um endere&ccedil;o:</td>
					<td align="right"></td>
    			</tr>
    		</table>
    
    		<table width="100%" border="0">
				<logic:equal name="tipoPesquisaRetorno" value="cliente">
				<tr>
					<td width="21%" height="24"><strong>Tipo de Endere&ccedil;o:<font color="#FF0000">*</font></strong></td>
					<td>
						<html:select property="tipo" style="width: 200px;">
							<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<html:options collection="colecaoTipo" labelProperty="descricaoComId" property="id"/>
						</html:select>
					</td>
				</tr>
				</logic:equal>
				
				
				<tr>
				   <tr>
				     <td colspan="2"><strong>Informe ou pesquise preferencialmente o endereço pelo logradouro.</strong></td>
				   </tr>
				   
        			<td height="24"><strong>Logradouro:<font color="#FF0000">*</font></strong></td>
					<td>
						<logic:present name="logradouroBloqueado" scope="request">
							<html:text property="logradouro" size="9" maxlength="9" tabindex="1" disabled="true"/>
							<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23" height="21" border="0" alt="Pesquisar">
						</logic:present>
						<logic:notPresent name="logradouroBloqueado" scope="request">
							<html:text property="logradouro" size="9" maxlength="9" tabindex="1" 
								onkeypress="pesquisarCepAtravesLogradouro(event)"/>
							<a href="javascript:pesquisarCepAtravesLogradouroPopUp();">
								<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" 
									width="23" height="21" border="0" alt="Pesquisar"></a>
						</logic:notPresent>
						<logic:present name="corRetorno">
							<logic:equal name="corRetorno" value="exception">
								<html:text property="logradouroDescricao" size="50" readonly="true" 
									style="background-color:#EFEFEF; border:0; color: #ff0000"/>
							</logic:equal>
							<logic:notEqual name="corRetorno" value="exception">
								<html:text property="logradouroDescricao" size="50" readonly="true" 
									style="background-color:#EFEFEF; border:0; color: #000000"/>
							</logic:notEqual>
						</logic:present>
						<logic:notPresent name="corRetorno">
							<logic:empty name="InserirEnderecoActionForm" property="logradouro">
								<html:text property="logradouroDescricao" value="" size="50" readonly="true" 
									style="background-color:#EFEFEF; border:0; color: #ff0000"/>
							</logic:empty>
							<logic:notEmpty name="InserirEnderecoActionForm" property="logradouro">
								<html:text property="logradouroDescricao" size="50" readonly="true" 
									style="background-color:#EFEFEF; border:0; color: #000000"/>
							</logic:notEmpty>
						</logic:notPresent>
						<logic:present name="logradouroBloqueado" scope="request">
							<img src="<bean:message key='caminho.imagens'/>limparcampo.gif" alt="Apagar">
						</logic:present>
						<logic:notPresent name="logradouroBloqueado" scope="request">
						<a href="javascript:limparLogradouro();">
								<img src="<bean:message key='caminho.imagens'/>limparcampo.gif" 
									alt="Apagar" border="0"></a>
						</logic:notPresent>
					</td>
        		</tr>
				<tr>
					<td width="21%" height="24"><strong>N&uacute;mero:<font color="#FF0000"></font></strong></td>
					<td><html:text maxlength="5" property="numero" size="9" tabindex="5"/></td>
				</tr>
				<tr>
					<td width="21%" height="24"><strong>CEP:<font color="#FF0000">*</font></strong></td>
					<td>
						<table cellspacing="0" cellpadding="0" border="0" width="100%">
							<tr>
								<td width="80">
									<logic:present name="logradouroMunicipioCepUnico" scope="request">
										<logic:equal name="logradouroMunicipioCepUnico" value="OK">
											<html:text maxlength="8" property="cep" tabindex="2" size="8" disabled="true"/>
										</logic:equal>
										<logic:notEqual name="logradouroMunicipioCepUnico" value="OK">
											<html:text maxlength="8" property="cep" tabindex="2"
												onkeypress="validaEnterComMensagemAceitaZERO(event, 'exibirInserirEnderecoAction.do?pesquisarCep=OK', 'cep', 'CEP');" size="8"/>
										</logic:notEqual>
									</logic:present>
									<logic:notPresent name="logradouroMunicipioCepUnico" scope="request">
										<html:text maxlength="8" property="cep" tabindex="2"
											onkeypress="validaEnterComMensagemAceitaZERO(event, 'exibirInserirEnderecoAction.do?pesquisarCep=OK', 'cep', 'CEP');" size="8"/>
									</logic:notPresent>
								</td>
								<td>
									<logic:present name="logradouroMunicipioCepUnico" scope="request">
										<logic:equal name="logradouroMunicipioCepUnico" value="OK">
											<img width="23" height="21" border="0" title="Pesquisar CEP"
												src="<bean:message key='caminho.imagens'/>pesquisa.gif"/>
										</logic:equal>
										<logic:notEqual name="logradouroMunicipioCepUnico" value="OK">
											<a href="javascript:redirecionarSubmit('/gsan/exibirInserirEnderecoAction.do'), redirecionarSubmit('/gsan/exibirPesquisarCepAction.do?caminhoRetornoTelaInformarEndereco=exibirInserirEnderecoAction&numeroJsp='+document.forms[0].numero.value);">
												<img width="23" height="21" border="0" title="Pesquisar CEP"
													src="<bean:message key='caminho.imagens'/>pesquisa.gif"/></a>
										</logic:notEqual>
									</logic:present>
									<logic:notPresent name="logradouroMunicipioCepUnico" scope="request">
										<a href="javascript:redirecionarSubmit('/gsan/exibirInserirEnderecoAction.do'), redirecionarSubmit('/gsan/exibirPesquisarCepAction.do?caminhoRetornoTelaInformarEndereco=exibirInserirEnderecoAction&numeroJsp='+document.forms[0].numero.value);">
											<img width="23" height="21" border="0" title="Pesquisar CEP" 
												src="<bean:message key='caminho.imagens'/>pesquisa.gif"/></a>
									</logic:notPresent>
									<logic:present name="corRetornoCep">
										<logic:equal name="corRetornoCep" value="exception">
											<html:text property="cepDescricao" size="50" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
										</logic:equal>
										<logic:notEqual name="corRetornoCep" value="exception">
											<html:text property="cepDescricao" size="50" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
										</logic:notEqual>
									</logic:present>
									<logic:notPresent name="corRetornoCep">
										<logic:empty name="InserirEnderecoActionForm" property="cep">
											<html:text property="cepDescricao" value="" size="50" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
										</logic:empty>
										<logic:notEmpty name="InserirEnderecoActionForm" property="cep">
											<html:text property="cepDescricao" size="50" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
										</logic:notEmpty>
									</logic:notPresent>
									<logic:present name="logradouroMunicipioCepUnico" scope="request">	
										<img src="<bean:message key='caminho.imagens'/>limparcampo.gif"
											border="0" title="Apagar" />
									</logic:present>
										<logic:notPresent name="logradouroMunicipioCepUnico" scope="request">	
											<a href="javascript:limparPesquisaCep();">
												<img src="<bean:message key='caminho.imagens'/>limparcampo.gif"
													border="0" title="Apagar" /></a>
										</logic:notPresent>
								</td>
							</tr>
							
							
							
							
						</table>
						<html:hidden property="cepUnico"/>
						<html:hidden property="codigoCepUnico"/>
					</td>
				</tr>
				<logic:equal name="exibirMatriculaImovel" value="1">
									<tr>
										<td bordercolor="#000000" width="25%"><strong>Im&oacute;vel:</strong></td>
												<td width="75%" colspan="3"><html:text
													property="idMatriculaImovel" maxlength="9" size="9" 
													onkeypress="validaEnterComMensagemAceitaZERO(event, 'exibirInserirEnderecoAction.do?pesquisarImovel=OK', 'idMatriculaImovel', 'IMOVEL');"
													 />
												<a 	href="javascript:redirecionarSubmit('exibirPesquisarImovelAction.do?caminhoRetornoTelaPesquisaImovel=exibirInserirEnderecoAction')">
			
												<img width="23" height="21"
												src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" /></a>
												<html:text property="imovelDescricao" size="30" readonly="true"  style="background-color:#EFEFEF; border:0; color: #000000" />
										</td>
									</tr>
							</logic:equal>
							
				<tr>
					<td colspan="2">
						<table width="100%" cellpadding="0" cellspacing="0">
							<tr> 
			                	<td> 
									<table width="100%" bgcolor="#99CCFF">
					                    <tr bgcolor="#99CCFF"> 
											<td align="center" width="10%"></td>
											<td width="18%"><div align="center"><strong>Logradouro</strong></div></td>
		                        			<td width="15%"><div align="center"><strong>Bairro</strong></div></td>
		                        			<td width="13%"><div align="center"><strong>Município</strong></div></td>
											<td width="5%"><div align="center"><strong>UF</strong></div></td>
											<td width="15%"><div align="center"><strong>CEP</strong></div></td>
											<td width="10%"><div align="center"><strong>Faixa</strong></div></td>
											<td width="15%"><div align="center"><strong>Lado</strong></div></td>
			        		            </tr>
			                    	</table>
								</td>
				            </tr>
				        </table>
						<logic:present name="colecaoCepSelecionadosUsuario" scope="session">
						<% String cor = "#FFFFFF";%>
						<div style="width: 100%; height: 100; overflow: auto;">
						<table width="100%" cellpadding="0" cellspacing="0" id="cepCarregado">
				            <tr> 
								<td> 
									<% cor = "#cbe5fe";%>
									<table width="100%" align="center" bgcolor="#99CCFF">
									<logic:iterate name="colecaoCepSelecionadosUsuario" id="logradouroCep" type="LogradouroCep">
									<% if (((Collection) session.getAttribute("colecaoCepSelecionadosUsuario")).size() > 1) {%>
										<% if (logradouroCep.getLogradouro() != null){ %>
											<%	if (cor.equalsIgnoreCase("#FFFFFF")){
											cor = "#cbe5fe";%>
											<tr bgcolor="#cbe5fe">
											<%} else{ cor = "#FFFFFF";%>
											<tr bgcolor="#FFFFFF">
											<%}%>
												<td align="center" width="10%">
													<html:radio property="cepSelecionado" value="<%="" + logradouroCep.getCep().getCepId().intValue()%>" onclick="document.forms[0].associacaoExistente.value='OK'"/>
												</td>	
												<td width="18%">
													<div align="left">
														<logic:present name="logradouroCep" property="cep.descricaoLogradouroFormatada">
															<bean:write name="logradouroCep" property="cep.descricaoLogradouroFormatada"/>
														</logic:present>
														<logic:notPresent name="logradouroCep" property="cep.descricaoLogradouroFormatada">
															&nbsp;
														</logic:notPresent>
													</div>
												</td>
												<td width="15%">
													<div align="left">
														<logic:present name="logradouroCep" property="cep.bairro">
															<bean:write name="logradouroCep" property="cep.bairro"/>
														</logic:present>
														<logic:notPresent name="logradouroCep" property="cep.bairro">
															&nbsp;
														</logic:notPresent>
													</div>
												</td>
												<td width="15%">
													<div align="left">
														<logic:present name="logradouroCep" property="cep.municipio">
															<bean:write name="logradouroCep" property="cep.municipio"/>
														</logic:present>
														<logic:notPresent name="logradouroCep" property="cep.municipio">
															&nbsp;
														</logic:notPresent>	
													</div>
												</td>
												<td width="5%">
													<div align="center">
														<logic:present name="logradouroCep" property="cep.sigla">
															<bean:write name="logradouroCep" property="cep.sigla"/>
														</logic:present>
														<logic:notPresent name="logradouroCep" property="cep.sigla">
															&nbsp;
														</logic:notPresent>	
													</div>
												</td>
												<td width="15%">
													<div align="center">
														<logic:present name="logradouroCep" property="cep.codigo">
															<bean:write name="logradouroCep" property="cep.cepFormatado"/>
														</logic:present>
														<logic:notPresent name="logradouroCep" property="cep.codigo">
															&nbsp;
														</logic:notPresent>	
													</div>
												</td>
												<td width="15%">
													<div align="center">
														<logic:present name="logradouroCep" property="cep.descricaoFaixaFormatada">
															<bean:write name="logradouroCep" property="cep.descricaoFaixaFormatada"/>
														</logic:present>
														<logic:notPresent name="logradouroCep" property="cep.descricaoFaixaFormatada">
															&nbsp;
														</logic:notPresent>	
													</div>
												</td>
													<td width="10%">
													<div align="center">
														<logic:present name="logradouroCep" property="cep.ladoFormatado">
															<bean:write name="logradouroCep" property="cep.ladoFormatado"/>
														</logic:present>
														<logic:notPresent name="logradouroCep" property="cep.ladoFormatado">
															&nbsp;
														</logic:notPresent>	
													</div>
												</td>
											</tr>
										<%}else{ // ELSE DE logradouroCep.getLogradouro() %>
											<% if (cor.equalsIgnoreCase("#FFFFFF")){
											cor = "#cbe5fe";%>
											<tr bgcolor="#cbe5fe">
											<%} else{ cor = "#FFFFFF";%>
											<tr bgcolor="#FFFFFF">
											<%}%>
												<td align="center" width="10%">
													<html:radio property="cepSelecionado" value="<%="" + logradouroCep.getCep().getCepId().intValue()%>" onclick="document.forms[0].associacaoExistente.value='FALSO'"/>
												</td>	
												<td width="18%">
													<div align="left">
														<logic:present name="logradouroCep" property="cep.descricaoLogradouroFormatada">
															<strong><bean:write name="logradouroCep" property="cep.descricaoLogradouroFormatada"/></strong>
														</logic:present>
														<logic:notPresent name="logradouroCep" property="cep.descricaoLogradouroFormatada">
															&nbsp;
														</logic:notPresent>
													</div>
												</td>
												<td width="15%">
													<div align="left">
														<logic:present name="logradouroCep" property="cep.bairro">
															<strong><bean:write name="logradouroCep" property="cep.bairro"/></strong>
														</logic:present>
														<logic:notPresent name="logradouroCep" property="cep.bairro">&nbsp;</logic:notPresent>
													</div>
												</td>
												<td width="15%">
													<div align="left">
														<logic:present name="logradouroCep" property="cep.municipio">
															<strong><bean:write name="logradouroCep" property="cep.municipio"/></strong>
														</logic:present>
														<logic:notPresent name="logradouroCep" property="cep.municipio">
															&nbsp;
														</logic:notPresent>	
													</div>
												</td>
												<td width="5%">
													<div align="left">
														<logic:present name="logradouroCep" property="cep.sigla">
															<strong><bean:write name="logradouroCep" property="cep.sigla"/></strong>
														</logic:present>
														<logic:notPresent name="logradouroCep" property="cep.sigla">
															&nbsp;
														</logic:notPresent>	
													</div>
												</td>
												<td width="15%">
													<div align="center">
														<logic:present name="logradouroCep" property="cep.codigo">
															<strong><bean:write name="logradouroCep" property="cep.cepFormatado"/></strong>
														</logic:present>
														<logic:notPresent name="logradouroCep" property="cep.codigo">
															&nbsp;
														</logic:notPresent>	
													</div>
												</td>
												<td width="15%">
													<div align="center">
														<logic:present name="logradouroCep" property="cep.descricaoFaixaFormatada">
															<bean:write name="logradouroCep" property="cep.descricaoFaixaFormatada"/>
														</logic:present>
														<logic:notPresent name="logradouroCep" property="cep.descricaoFaixaFormatada">
															&nbsp;
														</logic:notPresent>	
													</div>
												</td>
													<td width="10%">
													<div align="center">
														<logic:present name="logradouroCep" property="cep.ladoFormatado">
															<bean:write name="logradouroCep" property="cep.ladoFormatado"/>
														</logic:present>
														<logic:notPresent name="logradouroCep" property="cep.ladoFormatado">
															&nbsp;
														</logic:notPresent>	
													</div>
												</td>
											</tr>
										<%}%>
									<%}else{ // ELSE DE (Collection) session %>
										<% if (cor.equalsIgnoreCase("#FFFFFF")){
										cor = "#cbe5fe";%>
										<tr bgcolor="#cbe5fe">
										<%} else{ cor = "#FFFFFF";%>
										<tr bgcolor="#FFFFFF">
										<%}%> 
											<td align="center" width="10%">
												<html:radio property="cepSelecionado" value="<%="" + logradouroCep.getCep().getCepId().intValue()%>"/>
											</td>	
											<td width="18%">
												<div align="left">
													<logic:present name="logradouroCep" property="cep.descricaoLogradouroFormatada">
														<bean:write name="logradouroCep" property="cep.descricaoLogradouroFormatada"/>
													</logic:present>
													<logic:notPresent name="logradouroCep" property="cep.descricaoLogradouroFormatada">
														&nbsp;
													</logic:notPresent>
												</div>
											</td>
											<td width="15%">
												<div align="left">
													<logic:present name="logradouroCep" property="cep.bairro">
														<bean:write name="logradouroCep" property="cep.bairro"/>
													</logic:present>
													<logic:notPresent name="logradouroCep" property="cep.bairro">
														&nbsp;
													</logic:notPresent>
												</div>
											</td>
											<td width="13%">
												<div align="left">
													<logic:present name="logradouroCep" property="cep.municipio">
														<bean:write name="logradouroCep" property="cep.municipio"/>
													</logic:present>
													<logic:notPresent name="logradouroCep" property="cep.municipio">
														&nbsp;
													</logic:notPresent>	
												</div>
											</td>
											<td width="5%">
												<div align="left">
													<logic:present name="logradouroCep" property="cep.sigla">
														<bean:write name="logradouroCep" property="cep.sigla"/>
													</logic:present>
													<logic:notPresent name="logradouroCep" property="cep.sigla">
														&nbsp;
													</logic:notPresent>	
												</div>
											</td>
											<td width="15%">
												<div align="center">
													<logic:present name="logradouroCep" property="cep.codigo">
														<bean:write name="logradouroCep" property="cep.cepFormatado"/>
													</logic:present>
													<logic:notPresent name="logradouroCep" property="cep.codigo">
														&nbsp;
													</logic:notPresent>	
												</div>
											</td>
												<td width="15%">
													<div align="center">
														<logic:present name="logradouroCep" property="cep.descricaoFaixaFormatada">
															<bean:write name="logradouroCep" property="cep.descricaoFaixaFormatada"/>
														</logic:present>
														<logic:notPresent name="logradouroCep" property="cep.descricaoFaixaFormatada">
															&nbsp;
														</logic:notPresent>	
													</div>
												</td>
													<td width="15%">
													<div align="center">
														<logic:present name="logradouroCep" property="cep.ladoFormatado">
															<bean:write name="logradouroCep" property="cep.ladoFormatado"/>
														</logic:present>
														<logic:notPresent name="logradouroCep" property="cep.ladoFormatado">
															&nbsp;
														</logic:notPresent>	
													</div>
												</td>
										</tr>
									<%}%>
									</logic:iterate>
									</table>
								</td>
				            </tr>
						</table>
						</div>
						</logic:present>
					
						<logic:present name="objetoCep" scope="session">
						<table width="100%" cellpadding="0" cellspacing="0" id="cepCarregado">
				            <tr> 
								<td> 
									<table width="100%" align="center" bgcolor="#99CCFF">
										<tr bgcolor="#FFFFFF">
											<td align="center" width="10%">
												<html:radio property="cepSelecionado" value="${sessionScope.objetoCep.cepId}" />
											</td>	
											<td width="18%">
												<div align="left">
													<logic:present name="objetoCep" property="descricaoLogradouroFormatada">
														<bean:write name="objetoCep" property="descricaoLogradouroFormatada"/>
													</logic:present>
													<logic:notPresent name="objetoCep" property="descricaoLogradouroFormatada">
														&nbsp;
													</logic:notPresent>
												</div>
											</td>
											<td width="15%">
												<div align="left">
													<logic:present name="objetoCep" property="bairro">
														<bean:write name="objetoCep" property="bairro"/>
													</logic:present>
													<logic:notPresent name="objetoCep" property="bairro">
														&nbsp;
													</logic:notPresent>
												</div>
											</td>
											<td width="13%">
												<div align="left">
													<logic:present name="objetoCep" property="municipio">
														<bean:write name="objetoCep" property="municipio"/>
													</logic:present>
													<logic:notPresent name="objetoCep" property="municipio">
														&nbsp;
													</logic:notPresent>	
												</div>
											</td>
											<td width="5%">
												<div align="left">
													<logic:present name="objetoCep" property="sigla">
														<bean:write name="objetoCep" property="sigla"/>
													</logic:present>
													<logic:notPresent name="objetoCep" property="sigla">
														&nbsp;
													</logic:notPresent>	
												</div>
											</td>
											<td width="15%">
												<div align="center">
													<logic:present name="objetoCep" property="codigo">
														<bean:write name="objetoCep" property="cepFormatado"/>
													</logic:present>
													<logic:notPresent name="objetoCep" property="codigo">
														&nbsp;
													</logic:notPresent>	
												</div>
											</td>
											<td width="15%">
													<div align="center">
														<logic:present name="objetoCep" property="descricaoFaixaFormatada">
															<bean:write name="objetoCep" property="descricaoFaixaFormatada"/>
														</logic:present>
														<logic:notPresent name="objetoCep" property="descricaoFaixaFormatada">
															&nbsp;
														</logic:notPresent>	
													</div>
												</td>
												<td width="15%">
												<div align="center">
													<logic:present name="objetoCep" property="ladoFormatado">
														<bean:write name="objetoCep" property="ladoFormatado"/>
													</logic:present>
													<logic:notPresent name="objetoCep" property="ladoFormatado">
															&nbsp;
													</logic:notPresent>	
												</div>
											</td>
										</tr>
									</table>
								</td>
			            	</tr>
						</table>
						</logic:present>
					</td>
				</tr>
				
		        <tr>
		        	<td height="24" colspan="2"></td>
		        </tr>
		        <tr>
        			<td width="21%" height="24"><strong>Bairro:<font color="#FF0000">*</font></strong></td>
					<td>
						<logic:present name="logradouroBairros" scope="request">
							<html:select property="bairro" style="width: 200px;" tabindex="3">
								<html:option value="">&nbsp;</html:option>
								<html:options collection="logradouroBairros" labelProperty="nome" property="id"/>
							</html:select>
						</logic:present>
						<logic:notPresent name="logradouroBairros" scope="request">
							<html:select property="bairro" style="width: 200px;" tabindex="3">
								<html:option value="">&nbsp;</html:option>
							</html:select>
						</logic:notPresent>
          
					</td>
				</tr>

		        <logic:present name="indicadorReferenciaObrigatoria" scope="session">
			        <tr>
					<td width="21%" height="24"><strong>Refer&ecirc;ncia:<font color="#FF0000">*</font></strong></td>
						<td>
							<html:select property="enderecoReferencia" style="width: 200px;" tabindex="4">
								<html:options collection="colecaoEnderecoReferencia" labelProperty="descricaoComId" property="id"/>
							</html:select>
						</td>
			        </tr>
		        </logic:present>

		        <logic:notPresent name="indicadorReferenciaObrigatoria" scope="session">
			        <tr>
						<td width="21%" height="24"><strong>Refer&ecirc;ncia:</strong></td>
						<td>
							<html:select property="enderecoReferencia" style="width: 200px;" tabindex="4">
								<html:option value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>
								<html:options collection="colecaoEnderecoReferencia" labelProperty="descricaoComId" property="id"/>
							</html:select>
						</td>
			        </tr>
		        </logic:notPresent>
				<tr>
					<td width="21%" height="24"><strong>Complemento:</strong></td>
					<td><html:text maxlength="25" property="complemento" size="25" tabindex="6" /></td>
				</tr>
				<tr>
					<td height="24">&nbsp;</td>
					<td><font color="#FF0000">*</font> Campo Obrigat&oacute;rio</td>
				</tr>
				<tr>
					<logic:present name="caminhoRetornoTelaAdicionarSolicitante">
						<td>
							<input type="button" class="bottonRightCol" value="Voltar" onclick="redirecionarSubmit('${caminhoRetornoTelaAdicionarSolicitante}.do');" style="width: 70px;" tabindex="7">
						</td>
						<td height="27">
						
						<div align="right">
							<input type="hidden" name="flagReload">
							<logic:empty name="InserirEnderecoActionForm" property="tipoAcao">
								<input type="button" class="bottonRightCol" value="Inserir" onclick="validarFormEndereco(document.forms[0]);" style="width: 70px;" tabindex="8">
							</logic:empty>
							<logic:notEmpty name="InserirEnderecoActionForm" property="tipoAcao">
								<input type="button" class="bottonRightCol" value="Atualizar" onclick="validarFormEndereco(document.forms[0]);" style="width: 70px;" tabindex="8">
							</logic:notEmpty>
							<input type="button" class="bottonRightCol" value="Fechar" onClick="window.close();" style="width: 70px;" tabindex="9">
    			        </div>
    			        
    			        </td>
					</logic:present>
					
					<logic:notPresent name="caminhoRetornoTelaAdicionarSolicitante">
						<td height="27" colspan="2">
						
						<div align="right">
							<input type="hidden" name="flagReload">
							<logic:empty name="InserirEnderecoActionForm" property="tipoAcao">
								<input type="button" class="bottonRightCol" value="Inserir" onclick="validarFormEndereco(document.forms[0]);" style="width: 70px;" tabindex="8">
							</logic:empty>
							<logic:notEmpty name="InserirEnderecoActionForm" property="tipoAcao">
								<input type="button" class="bottonRightCol" value="Atualizar" onclick="validarFormEndereco(document.forms[0]);" style="width: 70px;" tabindex="8">
							</logic:notEmpty>
							<input type="button" class="bottonRightCol" value="Fechar" onClick="window.close();" style="width: 70px;" tabindex="9">
    			        </div>
    			        
    			        </td>
    			        
					</logic:notPresent>
					
				</tr>
			</table>
		    <p>&nbsp;</p>
		</td>
	</tr>
</table>
</html:form>
<script>
	selecionarCep();
</script>
</body>
</html:html>