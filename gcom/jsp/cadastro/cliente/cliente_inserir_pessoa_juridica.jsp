<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page import="gcom.util.ConstantesSistema"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>inscricaoEstadual.js"></script>
<script language="Javascript">
<!--
var bCancel = false;

function validateClienteActionForm(form) {
	if (bCancel)
		return true;
	else
		return testarCampoValorZero(document.ClienteActionForm.cnpj, 'CNPJ') 
			&& testarCampoValorZero(document.ClienteActionForm.codigoClienteResponsavel, 'Código do Cliente Responsável Superior')			 
			&& validateCaracterEspecial(form) 
			&& validateLong(form) 
			&& validarCnpj()								
			&& validateInscricao()
			&& validateRequired(form); 
}

function required () {
	this.ab = new Array("cnpj", "Informe CNPJ.", new Function ("varName", " return this[varName];"));
}

function IntegerValidations () {
	this.ac = new Array("cnpj", "CNPJ deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	this.ad = new Array("codigoClienteResponsavel", "Código do Cliente Responsável Superior deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	this.ae = new Array("inscricaoEstadual", "Inscrição Estadual deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
}

function cnpj () {
	this.aa = new Array("cnpj", "CNPJ inválido.", new Function ("varName", " return this[varName];"));	
}


function validarCnpj() {
	var form = document.forms[0];
	if (form.cnpj != null &&
	form.cnpj.value == '') {
		alert('Informe CNPJ.');
		return false;
	} else {
		if ( !isCnpj(form.cnpj.value) ) {
			alert('CNPJ Inválido.');
			return false;
		} else {
			return true;
		}
	}
} 


function isCnpj(cnpj){
	
	var i;
	var c = cnpj.substr(0,12);
	var dv = cnpj.substr(12,2);
	var d1 = 0;
	 
	for (i = 0; i < 12; i++){
		d1 += c.charAt(11-i)*(2+(i % 8));
	}
	 
	if (d1 == 0) return false;
			
	d1 = 11 - (d1 % 11);
	 
	if (d1 > 9) d1 = 0;
	
	if (dv.charAt(0) != d1){
		return false;
	 
	}
	 
	d1 *= 2;
			
	for (i = 0; i < 12; i++){
	 
		d1 += c.charAt(11-i)*(2+((i+1) % 8));
	
	}
			 
	d1 = 11 - (d1 % 11);
			
	if (d1 > 9) d1 = 0;
	
	if (dv.charAt(1) != d1){
	
		return false;
	 
	}
	 
	 return true;
	 
}


function caracteresespeciais () {
	this.aa = new Array("cnpj", "CNPJ possui caracteres especiais.", new Function ("varName", " return this[varName];"));
	this.ab = new Array("codigoClienteResponsavel", "Código do Cliente Responsável Superior possui caracteres especiais.", new Function ("varName", " return this[varName];"));
	this.ac = new Array("inscricaoEstadual", "Inscrição Estadual possui caracteres especiais.", new Function ("varName", " return this[varName];"));
}


//Recebe os dados do(s) popup(s)
function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	var form = document.ClienteActionForm;
	if (tipoConsulta == 'responsavelSuperior') {
		form.codigoClienteResponsavel.value = codigoRegistro;
		form.nomeClienteResponsavel.value = descricaoRegistro;
		form.nomeClienteResponsavel.style.color = "#000000";
	    setarFoco('codigoClienteResponsavel');
  	}
}

function limparClienteResponsavel() {
	var form = document.ClienteActionForm;
	form.codigoClienteResponsavel.value = "";
	form.nomeClienteResponsavel.value = "";
}

function validateInscricao(){
	var form = document.ClienteActionForm;
	var inscricao = form.inscricaoEstadual.value;
	var uf = form.idUnidadeFederacao[form.idUnidadeFederacao.selectedIndex].value;
	var sigla = form.idUnidadeFederacao[form.idUnidadeFederacao.selectedIndex].text;
		
 	if (inscricao == ''){
 	   //form.idUnidadeFederacao[form.idUnidadeFederacao.selectedIndex].value = '-1';
 	   return true;
 	}
 		
	if (inscricao != '' && uf == '-1'){	  
	   alert('Informe o Estado da Inscrição Estadual. ');
	   return false;
	}

   if (CheckIE(inscricao , sigla)){
	  return true;
    }else{
        alert('Inscrição Estadual inválida para o Estado.');
        return false;        
	}

}

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

	  var form = document.forms[0];

	  if (tipoConsulta == 'atividadeEconomica') {
	    
	    	form.codigoAtividadeEconomica.value = codigoRegistro;
	    	form.action='inserirClienteWizardAction.do?action=exibirInserirClientePessoaAction&pesquisaAtividadeEconomica=' + codigoRegistro;   
			form.submit();
	  }
}

function verificarCheckDocumentoValidado(){
    var form = document.forms[0];
    
    if(form.documentoValidado.value == "1"){
    	
    	form.documentoValidado.checked = true;
    	form.documentoValidado.value = "1";
    }else{
    	
    	form.documentoValidado.checked = false;
    	form.documentoValidado.value = "2";
    }
}
-->
</script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="ClienteActionForm" dynamicJavascript="false"
	/>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
</head>
<body leftmargin="5" topmargin="5"
	onload="setarFoco('${requestScope.nomeCampo}');verificarCheckDocumentoValidado();">
	
<div id="formDiv">
<html:form action="/inserirClienteWizardAction" method="post">
	<jsp:include page="/jsp/util/wizard/navegacao_abas_wizard_valida_avancar.jsp?numeroPagina=2" />
	
	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>
	
	<input type="hidden" name="numeroPagina" value="2" />
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
					<td class="parabg">Inserir Cliente</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0" dwcopytype="CopyTableRow">
				<tr>
					<td>Para adicionar um cliente pessoa jur&iacute;dica,
					informe os dados abaixo:</td>
				<td align="right"></td>
    			</tr>
    		</table>
    
    		<table width="100%" border="0">
    						<tr>
					<td colspan="2">
						<strong> Cliente Respons&aacute;vel Superior </strong>
					</td>
				</tr>
				<tr>
					<td><strong> C&oacute;digo : </strong></td>
					<td>
						<html:text maxlength="9" property="codigoClienteResponsavel" size="9" tabindex="5"
							onkeypress="javascript:return validaEnter(event, '/gsan/inserirClienteWizardAction.do?destino=2&action=exibirInserirClientePessoaAction', 'codigoClienteResponsavel');" />
						<a href="javascript:abrirPopup('exibirPesquisarResponsavelSuperiorAction.do', 400, 800);">
							<img src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"
								width="23" height="21" title="Pesquisar Cliente"></a>
						<logic:present name="codigoClienteNaoEncontrado">
							<logic:equal name="codigoClienteNaoEncontrado" value="exception">
								<html:text property="nomeClienteResponsavel" size="50" maxlength="50" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000" />
							</logic:equal>
							<logic:notEqual name="codigoClienteNaoEncontrado" value="exception">
								<html:text property="nomeClienteResponsavel" size="50" maxlength="50" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</logic:notEqual>
						</logic:present>
						<logic:notPresent name="codigoClienteNaoEncontrado">
							<logic:empty name="ClienteActionForm" property="codigoClienteResponsavel">
								<html:text property="nomeClienteResponsavel" value="" size="50"	maxlength="50" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000" />
							</logic:empty>
							<logic:notEmpty name="ClienteActionForm" property="codigoClienteResponsavel">
								<html:text property="nomeClienteResponsavel" size="50" maxlength="50" readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</logic:notEmpty>
						</logic:notPresent>
						<a href="javascript:limparClienteResponsavel();">
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" title="Apagar" /></a>
					</td>
				<tr>
					<td colspan="2">
					<hr>
					</td>
				</tr>
				<tr>
					<td width="13%"><strong> CNPJ:<font color="#FF0000">*</font></strong>
					</td>
					<td width="87%"><html:text property="cnpj" size="14" maxlength="14" tabindex="1" onkeypress="return isCampoNumerico(event);"/>
					<font size="1"> &nbsp; </font>
						<strong> Documento Validado:<font color="#FF0000">*</font></strong>
						<html:radio property="documentoValidado" value="<%=ConstantesSistema.SIM.toString()%>" disabled="false" /><strong>Sim</strong>
						<html:radio property="documentoValidado" value="<%=ConstantesSistema.NAO.toString()%>" disabled="false" /><strong>Não</strong>
					</td>
				</tr>
				<tr>
					<td><strong> Ramo de Atividade: </strong></td>
					<td>
						<html:select property="idRamoAtividade" tabindex="2">
							<html:option value="<%= "" + ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>
							<html:options collection="ramoAtividades" labelProperty="descricaoComId" property="id" />
						</html:select>
					</td>
				</tr>
				
				<logic:present name="obrigatorioAtividadeEconomica" scope="request">
						
					<tr>
						<td height="24"><strong>Atividade Ecônomica:<font color="#FF0000">*</font></strong></td>
						<td> 
							<html:text maxlength="10" 
									property="codigoAtividadeEconomica" 
									size="10" 
									onkeypress="return validaEnterString(event, 'inserirClienteWizardAction.do?action=exibirInserirClientePessoaAction', 'codigoAtividadeEconomica');" />
							<a href="javascript:abrirPopup('exibirPesquisarAtividadeEconomicaAction.do', 400, 800);">
							<img border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/></a>
							(X9999-9/99)
						</td>

					</tr>
				</logic:present>
				
				<logic:notPresent name="obrigatorioAtividadeEconomica" scope="request">
					
					<tr>
						<td height="24"><strong>Atividade Ecônomica:</strong></td>
						<td> 
							<html:text maxlength="10" 
								property="codigoAtividadeEconomica" 
								size="10" 
								onkeypress="return validaEnterString(event, 'inserirClienteWizardAction.do?action=exibirInserirClientePessoaAction', 'codigoAtividadeEconomica');" />
							<a href="javascript:abrirPopup('exibirPesquisarAtividadeEconomicaAction.do', 400, 800);">
							<img border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/></a>
							(X9999-9/99)
						</td>

					</tr>
				</logic:notPresent>
				
				<tr>
					<td width="13%"><strong> Inscrição Estadual: </strong>
					</td>
					<td width="27%"><html:text property="inscricaoEstadual" size="14" maxlength="14" tabindex="3"/>
					<font size="1"> &nbsp; </font></td>					
				</tr>
				<tr>
					<td height="24%" align="left"><strong>Estado:</strong></td>
					<td><html:select property="idUnidadeFederacao" tabindex="4">
						<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="unidadesFederacao" labelProperty="sigla"
							property="id" />
					</html:select></td>					
				</tr>
				<tr>
					<td colspan="2"><hr></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td align="left"><font color="red"> * </font> Campo Obrigatório</td>
				</tr>
				<tr>
					<td colspan="2">
					<div align="right"><jsp:include
						page="/jsp/util/wizard/navegacao_botoes_wizard_valida_avancar.jsp?numeroPagina=2" />
					</div>
					</td>
				</tr>
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>
	<%@ include file="/jsp/util/rodape.jsp"%>
	<%@ include file="/jsp/util/tooltip.jsp"%>
</html:form>
</div>
</body>

<%@ include file="/jsp/util/telaespera.jsp"%>

<script>
document.getElementById('botaoConcluir').onclick = function() { botaoAvancarTelaEspera('/gsan/inserirClienteWizardAction.do?concluir=true&action=inserirClientePessoaAction'); }
</script>

</html:html>
