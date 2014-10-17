<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gcom.util.ConstantesSistema"%>
<html:html>

<head>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false" formName="PesquisarComandoPrescricaoActionForm" />
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>

<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript">

function pesquisar(){
    
	var form = document.forms[0];
    
	if(validateCaracterEspecial(form) && validateLong(form) && validateDate(form)){
   		form.action = 'pesquisarComandoPrescricaoAction.do';
 		form.submit();		
	}
}

function caracteresespeciais () {
  
	this.aa = new Array("idUsuario", "Usuário que Gerou o Comando deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
}

function IntegerValidations () {
	 
	this.ab = new Array("idUsuario", "Usuário que Gerou o Comando deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
}	

function DateValidations () {
 
	this.ac = new Array("periodoGeracaoComandoInicial", "Data Inicial do Período de Geração do Comando inválida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
  	this.ad = new Array("periodoGeracaoComandoFinal", "Data Final do Período de Geração do Comando inválida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
  	this.ae = new Array("periodoRealizacaoComandoInicial", "Data Inicial do Período de Execução do Comando inválida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
  	this.af = new Array("periodoRealizacaoComandoFinal", "Data Final do Período de Execução do Comando inválida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
}    
    
function duplicaPeriodoGeracaoComando(form){
	
	form.periodoGeracaoComandoFinal.value = form.periodoGeracaoComandoInicial.value;
}

function duplicaPeriodoRealizacaoComando(form){
	
	form.periodoRealizacaoComandoFinal.value = form.periodoRealizacaoComandoInicial.value;
}
	
function limparUsuario() {
   
	var form = document.forms[0];
    form.nomeUsuario.value = "";
    form.idUsuario.value = "";
}

function verificarPreenchimentoDosCampos(){
	
	var preenchido = false;
    var formulario = document.forms[0];

	for(indice = 0; indice < formulario.elements.length; indice++){
		
		if (formulario.elements[indice].type == "text" && formulario.elements[indice].value.length > 0) {
			
			preenchido = true;
			break;
		}else if (formulario.elements[indice].type == "select-one" && formulario.elements[indice].value != "-1"){
			
			preenchido = true;
			break;
		}
	}

	if (preenchido){
		
		pesquisar();
	}else{
		
		formulario.elements[0].focus();
		alert("Informe pelo menos uma opção de seleção");
	}	
}

function limparForm(){
	
	var form = document.forms[0];
	form.tituloComando.value="";
    form.nomeUsuario.value = "";
    form.idUsuario.value = "";
	form.periodoGeracaoComandoInicial.value = "";
	form.periodoGeracaoComandoFinal.value = "";
	form.periodoRealizacaoComandoInicial.value = "";
	form.periodoRealizacaoComandoFinal.value = "";
}


</script>

</head>
<html:form action="/pesquisarComandoPrescricaoAction.do" name="PesquisarComandoPrescricaoActionForm" type="gcom.gui.cobranca.prescricao.PesquisarComandoPrescricaoActionForm"
	method="post">
	<body leftmargin="5" topmargin="5" onload="resizePageSemLink(750,370);setarFoco('${requestScope.nomeCampo}');">
	<table width="717" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="707" valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img src="imagens/parahead_left.gif"
						
						border="0" />
					</td>
					<td class="parabg">Pesquisar Comando de Prescrição</td>
					<td width="11"><img src="imagens/parahead_right.gif"
						border="0" />
					</td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				
				<tr>
					<td colspan="4">Preencha os campos para pesquisar comando de prescrição:</td>
				</tr>
		
				<tr>
					<td><strong>Título do Comando:</strong></td>
					<td colspan="2"><html:text maxlength="70" property="tituloComando" tabindex="3"
						size="70" /></td>
				</tr>
				
				<tr>
					<td>&nbsp;</td>
					<td><html:radio property="tipoPesquisa"
						value="<%=ConstantesSistema.TIPO_PESQUISA_INICIAL.toString()%>" />
						Iniciando pelo texto&nbsp;<html:radio property="tipoPesquisa"
							tabindex="4"
							value="<%=ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString()%>" />
						Contendo o texto</td>
				</tr>
				
				<tr>
					<td width="32%"><strong>Per&iacute;odo de Gera&ccedil;&atilde;o do
					Comando:</strong></td>
					<td width="68%" colspan="3">
					<strong> 
						<html:text tabindex="5" maxlength="10" property="periodoGeracaoComandoInicial" size="10" onkeyup="mascaraData(this, event);duplicaPeriodoGeracaoComando(document.forms[0]);" />
						<a href="javascript:abrirCalendario('PesquisarComandoPrescricaoActionForm', 'periodoGeracaoComandoInicial')">
						<img border="0"
						src="<bean:message key='caminho.imagens'/>calendario.gif"
						width="20" border="0" align="middle" alt="Exibir Calendário" /></a>						
						a</strong> 
						<html:text tabindex="6" maxlength="10" property="periodoGeracaoComandoFinal" size="10" onkeyup="mascaraData(this, event);" />
						<a href="javascript:abrirCalendario('PesquisarComandoPrescricaoActionForm', 'periodoGeracaoComandoFinal')">						
						<img border="0"
						src="<bean:message key='caminho.imagens'/>calendario.gif"
						width="20" border="0" align="middle" alt="Exibir Calendário" /></a>						
						dd/mm/aaaa
					</td>
				</tr>
				
				<tr>
					<td><strong>Per&iacute;odo de Realização do Comando:</strong></td>
					<td colspan="3">
					<strong> 
						<html:text tabindex="7" maxlength="10" property="periodoRealizacaoComandoInicial" size="10" onkeyup="mascaraData(this, event);duplicaPeriodoRealizacaoComando(document.forms[0]);" />
						<a href="javascript:abrirCalendario('PesquisarComandoPrescricaoActionForm', 'periodoRealizacaoComandoInicial')">
						<img border="0"
						src="<bean:message key='caminho.imagens'/>calendario.gif"
						width="20" border="0" align="middle" alt="Exibir Calendário" /></a>						
						a</strong> 
						<html:text tabindex="8" maxlength="10" property="periodoRealizacaoComandoFinal" size="10" onkeyup="mascaraData(this, event);" />
						<a href="javascript:abrirCalendario('PesquisarComandoPrescricaoActionForm', 'periodoRealizacaoComandoFinal')">
						<img border="0"
						src="<bean:message key='caminho.imagens'/>calendario.gif"
						width="20" border="0" align="middle" alt="Exibir Calendário" /></a>						
						dd/mm/aaaa
					</td>
				</tr>
				
				<tr>
					<td><strong>Usu&aacute;rio que Gerou o Comando:</strong></td>
					<td colspan="3"><strong>
						<html:text maxlength="7" tabindex="9" property="idUsuario" size="7" 
						onkeypress="javascript:validaEnterComMensagem(event, 'exibirPesquisarComandoPrescricaoAction.do', 'idUsuario', 'Usuário que Gerou o Comando');" />
						<img src="<bean:message key='caminho.imagens'/>pesquisa.gif"
						width="23" height="21" style="cursor:hand;"
						onclick="abrirPopup('exibirUsuarioPesquisar.do?caminhoRetornoTelaPesquisaUsuario=exibirPesquisarComandoPrescricaoAction', 250, 495);"
						alt="Pesquisar"> 
		   		        <logic:present name="usuarioNaoEncontrado" scope="request">
						    <html:text maxlength="40" property="nomeUsuario" size="40" style="background-color:#EFEFEF; border:0; color: #ff0000" readonly="true"/>
                        </logic:present>
                        <logic:notPresent name="usuarioNaoEncontrado" scope="request">
						    <html:text maxlength="40" property="nomeUsuario" size="40" style="background-color:#EFEFEF; border:0; color: #000000" readonly="true"/>
                        </logic:notPresent>
					<a	href="javascript:limparUsuario();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a>
					</strong></td>
				</tr>
				
				<tr>
					<td><strong> </strong></td>
					<td colspan="3">&nbsp;</td>
				</tr>
				
				<tr>
					<td colspan="4">
						<table width="100%" border="0">
							<tr>
								<td colspan="3"><INPUT TYPE="button" name="limpar" class="bottonRightCol"
									value="Limpar" onclick="document.forms[0].reset();limparForm();" />
								</td>
								<td align="right"><INPUT TYPE="button" name="pesquisar" class="bottonRightCol"
									value="Pesquisar" onclick="javascript:verificarPreenchimentoDosCampos();"/>
								</td>
							</tr>
						</table>
						<p>&nbsp;</p>
					</td>
				</tr>
				
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>
	</body>
</html:form>
</html:html>
