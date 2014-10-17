<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="gcom.seguranca.acesso.usuario.UsuarioTipo,gcom.cadastro.empresa.Empresa"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="InserirUsuarioDadosGeraisActionForm"	
	dynamicJavascript="false" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript">
window.onmousemove = verificarAcesso;
	function limparLotacao() {
		 	document.forms[0].idLotacao.value = '';
		 	document.forms[0].nomeLotacao.value = '';
	}

	function limparFuncionario() {
		 	document.forms[0].idFuncionario.value = '';
		 	document.forms[0].nomeFuncionario.value = '';
		 	document.forms[0].idLotacao.value = '';
		 	document.forms[0].nomeLotacao.value = '';
		 	document.forms[0].nome.value = '';
	}

	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
		if ('funcionario' == tipoConsulta) {
			document.forms[0].idLotacao.value = '';
		 	document.forms[0].nomeLotacao.value = '';
		 	document.forms[0].nome.value = '';
		 	document.forms[0].nomeFuncionario.value = '';
		 	document.forms[0].idFuncionario.value = codigoRegistro;
		 	document.forms[0].action = 'exibirInserirUsuarioDadosGeraisAction.do';
		 	submeterFormPadrao(document.forms[0]);
	 	} else if ('unidadeOrganizacional' == tipoConsulta) { 
		 	document.forms[0].idLotacao.value = codigoRegistro;
		 	document.forms[0].nomeLotacao.value = descricaoRegistro;
		 	document.forms[0].action = 'exibirInserirUsuarioDadosGeraisAction.do';
		 	submeterFormPadrao(document.forms[0]);
	 	}
	}
	

     var bCancel = false; 

    function validateInserirUsuarioDadosGeraisActionForm(form) {                                                                   
        if (bCancel) 
      return true; 
        else 
       return validateRequired(form) && validateLong(form) && validateCaracterEspecial(form) && validateDate(form) && validateEmail(form) && enviar(form.usuarioTipo.value) && testarCampoValorZero(form.idFuncionario, 'Matrícula do Funcionário') && testarCampoValorZero(form.idLotacao, 'Unidade de Lotação')&& validarEmail(); 
   } 

    function required () { 
     this.aa = new Array("usuarioTipo", "Informe Tipo de Usuário.", new Function ("varName", " return this[varName];"));
     this.ab = new Array("empresa", "Informe Empresa.", new Function ("varName", " return this[varName];"));
     if(document.forms[0].indicadorFuncionario.value != document.forms[0].indicadorFuncionarioSIM.value){
     	this.ac = new Array("nome", "Informe Nome Usuário.", new Function ("varName", " return this[varName];"));
     }
     if(document.forms[0].indicadorFuncionario.value == document.forms[0].indicadorFuncionarioSIM.value){
      	this.ad = new Array("idFuncionario", "Informe Matrícula do Funcionário.", new Function ("varName", " return this[varName];"));
      }
     this.ae = new Array("login", "Informe Login.", new Function ("varName", " return this[varName];"));
     this.af = new Array("email", "Informe E-mail.", new Function ("varName", " return this[varName];"));
     this.ag = new Array("confirmacaoEmail","Informe Confirmação E-Mail.", new Function ("varName", " return this[varName];"));
    } 

    function IntegerValidations () { 
     this.aa = new Array("usuarioTipo", "Tipo de Usuário deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.ab = new Array("empresa", "Empresa deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.ac = new Array("idLotacao", "Lotação deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    } 

    function caracteresespeciais () { 
     this.aa = new Array("nome", "Nome possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ab = new Array("login", "Login possui caracteres especiais.", new Function ("varName", " return this[varName];"));
    } 

    function email () { 
     this.aa = new Array("email", "E-mail é um endereço inválido.", new Function ("varName", " return this[varName];"));
     this.ab = new Array("confirmacaoEmail", "Confirmação E-mail é um endereço inválido.", new Function ("varName", " return this[varName];"));
    } 
    
    function DateValidations () { 
     this.aa = new Array("dataInicial", "Data de Cadastramento Inicial não é uma data válida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
     this.aa = new Array("dataFinal", "Data de Cadastramento Inicial não é uma data válida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
    } 

function desfazer(){
    document.forms[0].reset();
    verificarAcesso();
}

function enviar(idTipoUsuario) {
   var form = document.forms[0];
   var achou = true;
   if(trim(idTipoUsuario) == ''){
   alert('Informe Tipo de Usuário.');
   achou = false;
   }else{
    if(idTipoUsuario != form.administrador.value){
	    //caso o o indicador de funcionário seja 0(não é funcionário)
		if(form.indicadorFuncionario.value != form.indicadorFuncionarioSIM.value){
		  if(trim(form.empresa.value) == ''){
		       alert('Informe Empresa.');
			   achou = false;
		  }else{
		        if (trim(form.idLotacao.value) == '') {
			       alert('Informe Unidade Lotação.');
			       achou = false;
		        }else{
		          if(trim(form.dataInicial.value) == '' || trim(form.dataFinal.value) == '') {
			       alert('Informe Período de Cadastramento.');
			       achou = false;
		          }
		        }
		      
		  }
		 
		 //caso o tipo de usuario seja funcionário
		}else{
		 if(form.indicadorFuncionario.value == form.indicadorFuncionarioSIM.value){
		   if (trim(form.idFuncionario.value) == '') {
			 alert('Informe Matrícula Funcionário.');
			 achou = false;
		   }
		 }
	   }
	}
  }
	
	return achou; 
}

function trocaTipUsuario() {
var form = document.forms[0];
//seta o idUsuarioTipo e o indicadorFuncionário 
var parmsUsuarioTipo = form.parmsUsuarioTipo.value.split(';');

form.usuarioTipo.value = parmsUsuarioTipo[0];
form.indicadorFuncionario.value = parmsUsuarioTipo[1];
if(form.usuarioTipo.value != ''){
 form.nome.value = '';
 form.idFuncionario.value = '';
 form.nomeFuncionario.value = '';
 form.idLotacao.value = '';
 form.dataInicial.value = '';
 form.dataFinal.value = '';
 form.empresa.value = '';
 form.nomeLotacao.value = '';
 form.login.value = '';
 form.email.value = '';
 form.confirmacaoEmail.value = '';
 }
 verificarAcesso();
}

function verificarAcesso(){
var form = document.forms[0];
//caso a abrangência não tenha valor
if(form.usuarioTipo.value == ''){
 form.nome.disabled = false;
 form.idFuncionario.disabled = false;
 form.idLotacao.disabled = false;
 form.empresa.disabled = false;
 
}else{
if(form.usuarioTipo.value == form.administrador.value){
    form.nome.disabled = false;
    form.idFuncionario.disabled = true;
    form.idLotacao.disabled = false;
    form.empresa.disabled = false;
    form.idFuncionario.value = '';
    form.nomeFuncionario.value = '';
 }else{
	 if(form.indicadorFuncionario.value == form.indicadorFuncionarioSIM.value){
		 form.nome.disabled = true;
		 form.idFuncionario.disabled = false;
		 form.idLotacao.disabled = false;
		 form.empresa.disabled = true;
		 form.empresa.value = form.idEmpresaFuncionario.value;
	 }else{
	   if(form.indicadorFuncionario.value != form.indicadorFuncionarioSIM.value){
		    form.nome.disabled = false;
		    form.idFuncionario.disabled = true;
		    form.idLotacao.disabled = false;
		    form.empresa.disabled = false;
		    form.idFuncionario.value = '';
		    form.nomeFuncionario.value = '';
	  }
 	}
 }
}
}

function chamarPopup(url,altura, largura,objetoRelacionado){
		if(objetoRelacionado.disabled != true){
			abrirPopup(url, altura, largura);
		}
			
   }
function limparCamposFuncionario(){
var form = document.forms[0];
 
  form.nomeFuncionario.value = '';
  form.nome.value = '';
  form.idLotacao.value = '';
  form.nomeLotacao.value = '';
  form.dataInicial.value = '';
  form.dataFinal.value = '';
  form.login.value = '';
 form.email.value = '';
 form.confirmacaoEmail.value = '';
}
function limparNomeLotacao(){
var form = document.forms[0];
 if(form.idLotacao == ''){
  form.nomeLotacao.value = '';
 }
}

function limpaDataFinal(){
var form = document.forms[0];
  if(form.dataInicial.value == ''){
   form.dataFinal.value = '';
  }

}

function validarEmail(){
var form = document.forms[0];

	if(form.confirmacaoEmail.value == ''){
		alert('Informe Confirmação Email');
	}else if(form.email.value != form.confirmacaoEmail.value){
		alert('Informações de email incompatíveis');
	}else{
		return true;
	}
}

function validaEnterSemEvent() {

	var form = document.forms[0];	
	var valorCampo = form.idLotacao.value;
	var caminho = '/gsan/exibirInserirUsuarioDadosGeraisAction.do' ;
		
	if (!isNaN(valorCampo) && valorCampo.length > 0 && valorCampo.indexOf(',') == -1 &&
		valorCampo.indexOf('.') == -1 && ((valorCampo * 1) > 0)){
				
			return pesquisaEnterSemEvento(caminho);		
	}else{
		return true;
	}
}
</script>
</head>

<body leftmargin="5" topmargin="5" onload="verificarAcesso();">
<html:form action="/inserirUsuarioWizardAction" method="post"
	onsubmit="return validateInserirUsuarioDadosGeraisActionForm(this);">

	<jsp:include
		page="/jsp/util/wizard/navegacao_abas_wizard_valida_avancar_valida_voltar.jsp?numeroPagina=1" />

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>
	<input type="hidden" name="numeroPagina" value="1" />
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
					<td class="parabg">Inserir Usuário - Dados Gerais</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table border="0" width="100%">
					<tr>
						<td colspan="2">Para adicionar o usuário, informe os dados abaixo:
						<input type="hidden" name="indicadorFuncionarioSIM"
							value="<%=UsuarioTipo.INDICADOR_FUNCIONARIO%>" /> 
							<input
							type="hidden" name="administrador"
							value="<%=UsuarioTipo.USUARIO_TIPO_ADMINISTRADOR%>" /> 
							<input type="hidden"
							name="idEmpresaFuncionario" value="<%=Empresa.INDICADOR_EMPRESA_PRINCIPAL%>" />
							<html:hidden
							property="indicadorFuncionario" />
							<html:hidden
							property="usuarioTipo" /></td>
					</tr>
					<tr>
						<td width="26%"><strong>Tipo de Usuário:<font color="#ff0000">*</font></strong></td>
						<td width="74%"><html:select property="parmsUsuarioTipo"
							onchange="javascript:trocaTipUsuario()">
							<bean:define name="InserirUsuarioDadosGeraisActionForm" property="parmsUsuarioTipo" id="parmsUsu"/>
							<option value=""></option>
							<logic:notEmpty name="collUsuarioTipo">
								<logic:iterate name="collUsuarioTipo" id="usuarioTipo">
								<option <%=(parmsUsu.toString().equalsIgnoreCase(((UsuarioTipo)usuarioTipo).getId() + ";" + ((UsuarioTipo)usuarioTipo).getIndicadorFuncionario()))?" selected ": "" %> 
								value="<bean:write name="usuarioTipo" property="id"/>;<bean:write name="usuarioTipo" property="indicadorFuncionario"/>">
								<bean:write name="usuarioTipo" property="descricao"/>
								</option>
								</logic:iterate>
							</logic:notEmpty>
						</html:select></td>
					</tr>
					<tr>
						<td width="26%"><strong>Empresa:<font color="#ff0000">*</font></strong></td>
						<td width="74%"><html:select property="empresa">
							<option value=""></option>
							<html:options name="request" collection="collEmpresa"
								labelProperty="descricao" property="id" />
						</html:select></td>
					</tr>
					<tr>
						<td width="26%"><strong>Matrícula do Funcionário:</strong></td>
						<td width="74%"><html:text maxlength="9" property="idFuncionario"
							size="9"
							onkeypress="javascript:document.forms[0].idLotacao.value='';validaEnter(event, 'exibirInserirUsuarioDadosGeraisAction.do', 'idFuncionario');return isCampoNumerico(event);"
							onkeyup="limparCamposFuncionario();" /> <a
							href="javascript:chamarPopup('exibirPesquisarFuncionarioAction.do?limpaForm=S', 495, 300,document.forms[0].idFuncionario);"><img
							src="<bean:message key='caminho.imagens'/>pesquisa.gif"
							width="23" height="21" alt="Pesquisar" border="0"></a> <logic:equal
							property="funcionarioNaoEncontrada"
							name="InserirUsuarioDadosGeraisActionForm" value="true">
							<html:text property="nomeFuncionario" size="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal> <logic:equal property="funcionarioNaoEncontrada"
							name="InserirUsuarioDadosGeraisActionForm" value="false">
							<html:text property="nomeFuncionario" size="40" maxlength="40"
								readonly="true"
								style="border: 0pt none ; background-color: rgb(239, 239, 239);" />
						</logic:equal> <a href="javascript:limparFuncionario();"> <img
							border="0" src="imagens/limparcampo.gif" height="21" width="23">
						</a></td>
					</tr>
					<tr>
						<td width="26%"><strong>Nome do Usuário:<font color="#ff0000">*</font></strong></td>
						<td width="74%"><html:text property="nome" size="50"
							maxlength="50" /></td>
					</tr>
					<tr>
						<td width="26%"><strong>Unidade Organizacional:<font color="#ff0000">*</font></strong></td>
						<td width="74%"><html:text maxlength="8" property="idLotacao"
							size="9"
							onkeypress="javascript:return validaEnterSemUpperCase(event, 'exibirInserirUsuarioDadosGeraisAction.do', 'idLotacao');"
							onkeyup="limparNomeLotacao();" onblur="javascript:validaEnterSemEvent();"/> <a
							href="javascript:chamarPopup('exibirPesquisarUnidadeOrganizacionalAction.do?limpaForm=S', 495, 300,document.forms[0].idLotacao);"><img
							src="<bean:message key='caminho.imagens'/>pesquisa.gif"
							width="23" height="21" alt="Pesquisar" border="0"></a> <logic:equal
							property="lotacaoNaoEncontrada"
							name="InserirUsuarioDadosGeraisActionForm" value="false">
							<html:text name="InserirUsuarioDadosGeraisActionForm"
								property="nomeLotacao" size="40" maxlength="40" readonly="true"
								style="border: 0pt none ; background-color: rgb(239, 239, 239);" />
						</logic:equal> <logic:equal property="lotacaoNaoEncontrada"
							name="InserirUsuarioDadosGeraisActionForm" value="true">
							<html:text name="InserirUsuarioDadosGeraisActionForm"
								property="nomeLotacao" size="40" maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal> <a href="javascript:limparLotacao();"> <img
							border="0" src="imagens/limparcampo.gif" height="21" width="23">
						</a></td>
					</tr>

					<tr>
						<td width="26%"><strong>Período de Cadastramento:<font color="#ff0000">*</font></strong></td>
						<td width="74%"><html:text
						    onkeypress="javascript:return isCampoNumerico(event); " 
							onkeyup="mascaraData(this, event);limpaDataFinal();"
							property="dataInicial" size="10" maxlength="10" /> <a
							href="javascript:abrirCalendario('InserirUsuarioDadosGeraisActionForm', 'dataInicial')">
						<img border="0"
							src="<bean:message key='caminho.imagens'/>calendario.gif"
							width="20" border="0" align="middle" alt="Exibir Calendário" /></a>

						&nbsp; <html:text onkeypress="javascript:return isCampoNumerico(event); " 
						    onkeyup="mascaraData(this, event);"
							property="dataFinal" size="10" maxlength="10" /> <a
							href="javascript:abrirCalendario('InserirUsuarioDadosGeraisActionForm', 'dataFinal')"><img
							border="0"
							src="<bean:message key='caminho.imagens'/>calendario.gif"
							width="20" border="0" align="middle" alt="Exibir Calendário" /></a>
						dd/mm/aaaa</td>
					</tr>

					<tr>
						<td colspan="2">
						<hr>
						</td>
					</tr>
					<tr>
						<td width="26%"><strong>Login:<font color="#ff0000">*</font></strong></td>
						<td width="74%"><html:text property="login" size="11"
							maxlength="11" style="text-transform: none;"/>(matrícula do funcionário ou cpf do prestador de serviço)</td>
					</tr>
					<tr>
						<td width="26%"><strong>E-Mail:<font color="#ff0000">*</font></strong></td>
						<td width="74%"><html:text property="email" size="40"
							maxlength="40" style="text-transform: none;"/></td>
					</tr>
					<tr>
						<td width="26%"><strong>Confirmação E-Mail:<font color="#ff0000">*</font></strong></td>
						<td width="74%">
						<html:text property="confirmacaoEmail" size="40"
							maxlength="40" style="text-transform: none;"/>
						</td>
					</tr>

					<tr>
						<td colspan="2">
						<table border="0" width="100%">
							<tr>
								<td>
								<div align="right"><jsp:include
									page="/jsp/util/wizard/navegacao_botoes_wizard_valida_avancar_valida_voltar.jsp?numeroPagina=1" /></div>
								</td>
							</tr>
						</table>
						</td>
					</tr>

			</table>
			</td>
		</tr>
	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>

</body>
</html:form>
</html:html>
