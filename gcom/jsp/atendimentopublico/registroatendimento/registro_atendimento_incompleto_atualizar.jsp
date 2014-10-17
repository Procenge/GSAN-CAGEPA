<%@page import="gcom.atendimentopublico.registroatendimento.AtendimentoIncompleto"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@ page import="gcom.atendimentopublico.registroatendimento.RegistroAtendimento" %>
<%@ page import="gcom.atendimentopublico.registroatendimento.AtendimentoIncompleto" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false"  formName="ConsultarRegistroAtendimentoIncompletoActionForm" dynamicJavascript="false" />

<script language="JavaScript">
<!--

	var bCancel = false; 

	//  Função que faz a validação normal dos forms e faz a validação especial
	//pra o campo senhaAtendimento, que é dependente do meioSolicitacao.
    function validateConsultarRegistroAtendimentoActionForm(form) {                                                                   
    	var retorno = false;                                                                    
        if (bCancel){
      		retorno = true;
      	} else {
      		var form = document.forms[0];
      		if (validateCaracterEspecial(form) && validateRequired(form) && validateDate(form) && validateInteger(form)){
	  			if (verificarObrigatoriedadeSenhaAtendimento(form.meioSolicitacao.value) && (form.senhaAtendimento.value == '')){
       				alert('Para o Meio de Solicitação informado a Senha de Atendimento é obrigatória.');
       				form.senhaAtendimento.focus();
       			} else {
       				if (testarCampoValorInteiroComZero(form.senhaAtendimento, 'Senha de Atendimento')){
       					retorno = true;
       				}
       			}
   			}
   		}
   		return retorno;
   	} 

	function validarForm() {
	  	var form = document.forms[0];
	  	form.action = "/gsan/atualizarRegistroAtendimentoIncompletoAction.do";
		submeterFormPadrao(form);
	}
	

    function caracteresespeciais () { 
     this.aa = new Array("dataAtendimento", "Data do Atendimento possui caracteres especiais.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
     this.ab = new Array("hora", "Hora possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ac = new Array("tempoEsperaInicial", "Tempo de Espera Inicial possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ad = new Array("tempoEsperaFinal", "Tempo de Espera Final possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ae = new Array("unidade", "Unidade de Atendimento possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     //this.af = new Array("observacao", "Observação possui caracteres especiais.", new Function ("varName", " return this[varName];"));
    } 

    function required () { 
     this.aa = new Array("dataAtendimento", "Informe Data do Atendimento.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
     this.ab = new Array("hora", "Informe Hora.", new Function ("varName", " return this[varName];"));
     this.ac = new Array("unidade", "Informe Unidade de Atendimento.", new Function ("varName", " return this[varName];"));
     this.ad = new Array("meioSolicitacao", "Informe Meio de Solicitação.", new Function ("varName", " return this[varName];"));
     this.ae = new Array("tipoSolicitacao", "Informe Tipo de Solicitação.", new Function ("varName", " return this[varName];"));
     this.af = new Array("especificacao", "Informe Especificação.", new Function ("varName", " return this[varName];"));
    } 

    function DateValidations () { 
     this.aa = new Array("dataAtendimento", "Data do Atendimento inválida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
    } 

    function IntegerValidations () { 
     this.aa = new Array("unidade", "Unidade de Atendimento deve ser numérico(a).", new Function ("varName", " return this[varName];"));
    }


	function verificarDataAtendimento(){
		
		var form = document.forms[0];
		
		if (form.especificacao.value > 0){
			if (form.dataAtendimento.value.length < 1){
				alert("Informe Data do Atendimento");
				form.dataAtendimento.focus();
			}
			else if (validateDate(form)){
				redirecionarSubmit('atualizarRegistroAtendimentoWizardAction.do?action=exibirAtualizarRegistroAtendimentoDadosGeraisAction&definirDataPrevista=OK');
			}
		}
	}
	
	function limpar(situacao){
	
		var form = document.forms[0];
	
		switch (situacao){
	       case 1:
			   form.unidade.value = "";
			   form.descricaoUnidade.value = "";
			   
			   //Coloca o foco no objeto selecionado
			   form.unidade.focus();
			   break;
			   
		   default:
	          break;
		}
	}
	
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	    var form = document.forms[0];
	    if (tipoConsulta == 'arrecadador') {
	      form.banco.value = codigoRegistro;
	      form.descricaoBanco.value = descricaoRegistro;
	      form.descricaoBanco.style.color = "#000000";
	    }
	}
	
	function carregarTempoEsperaFinal(){
		var form = document.forms[0];
		if (form.tempoEsperaInicial.value.length > 0 && form.tempoEsperaFinal.readOnly == true){
			redirecionarSubmit('atualizarRegistroAtendimentoWizardAction.do?action=exibirAtualizarRegistroAtendimentoDadosGeraisAction&tempoEsperaFinalDesabilitado=OK');
		}
	}
	function limparPesquisaRA() {
    	var form = document.forms[0];
    	form.RAEfetiva.value = "";
  	}
	function carregarEspecificacao(){
		var form = document.forms[0];
		if (form.tipoSolicitacao.value > 0){
			redirecionarSubmit('atualizarRegistroAtendimentoWizardAction.do?action=exibirAtualizarRegistroAtendimentoDadosGeraisAction&pesquisarEspecificacao=OK');
		}
	}
	
	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){
		if(objetoRelacionado.disabled != true){
			if (objeto == null || codigoObjeto == null){
				abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
			} else{
				if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
					alert(msg);
				} else{
					abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
				}
			}
		}
	}
	
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	    var form = document.forms[0];
		
	    if (tipoConsulta == 'registroAtendimento') {
	      form.RAEfetiva.value = codigoRegistro;	      
	    }
	}


//-->
</SCRIPT>


</head>

<body leftmargin="5" topmargin="5"
	onload="setarFoco('${requestScope.nomeCampo}'); limitTextArea(document.forms[0].observacao, 400, document.getElementById('utilizado'), document.getElementById('limite'));">


<html:form action="/atualizarRegistroAtendimentoWizardAction" method="post" >

	<html:hidden property="idImovel"/>

	<html:hidden property="idMunicipio"/>
	<html:hidden property="cdBairro"/>
	<html:hidden property="idBairroArea"/>


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

			<td width="600" valign="top" class="centercoltext">

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
					<td class="parabg">Atualizar RA - Registro de Atendimento Incompleto</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para atualizar o RA - Registro de Atendimento incompleto, informe
					os dados gerais abaixo:</td>
				</tr>
				<tr>
					<td><strong>N&uacute;mero do RA:</strong></td>
					
					<td>
						<html:text name="ConsultarRegistroAtendimentoIncompletoActionForm" property="id" readonly="true" size="11" maxlength="10"/>
					</td>
				</tr>
				<tr>
					<td><strong>Número Fone:</strong></td>
					<td><html:text name="ConsultarRegistroAtendimentoIncompletoActionForm" property="numeroFoneChamada" size="11" maxlength="10" readonly="true" /></td>
				</tr>
				<tr>
					<td><strong>Nome Contato:</strong></td>
					<td><html:text name="ConsultarRegistroAtendimentoIncompletoActionForm" property="nomeContato" size="55" maxlength="55" readonly="true" /></td>
				</tr>
				<c:if test="${(not empty ConsultarRegistroAtendimentoIncompletoActionForm.imovel)}">
					<tr>
						<td><strong>Matrícula do Imóvel:</strong></td>
						<td><html:text name="ConsultarRegistroAtendimentoIncompletoActionForm"
									property="imovel" size="55" maxlength="55" readonly="true" />
						</td>
					</tr>
				</c:if>
						<tr>
					<td><strong>Motivo do Atendimento Incompleto:</strong></td>
					<td><html:text name="ConsultarRegistroAtendimentoIncompletoActionForm" property="atendimentoIncompletoMotivo" size="55" maxlength="55" readonly="true" /></td>
				</tr>	
				
				<tr>
					<td><strong>Tipo de Solicitação:</strong></td>
					<td><html:text name="ConsultarRegistroAtendimentoIncompletoActionForm" property="solicitacaoTipo" size="55" maxlength="55" readonly="true" /></td>
				</tr>
				<tr>
					<td><strong>Tipo de Especificação:</strong></td>
					<td><html:text name="ConsultarRegistroAtendimentoIncompletoActionForm" property="solicitacaoTipoEspecificacao" size="55" maxlength="55" readonly="true" /></td>
				</tr>				
				
				<tr>
					<td><strong>Chamada Retornada:<font color="#FF0000">*</font></strong></td>
					<td>
						<html:radio name="ConsultarRegistroAtendimentoIncompletoActionForm" property="indicadorRetornoChamada" value="1"/>Sim
						<html:radio name="ConsultarRegistroAtendimentoIncompletoActionForm" property="indicadorRetornoChamada" value="2" />Não
					</td>
				</tr>
				<tr>
					<td><strong>RA Efetiva:<font color="#FF0000">*</font></strong></td>
					<td>
						
						<html:text maxlength="9" name="ConsultarRegistroAtendimentoIncompletoActionForm"
							tabindex="1"
							property="RAEfetiva" 
							size="9"
							/>
							
							<a href="javascript:chamarPopup('exibirPesquisarRegistroAtendimentoAction.do', 'registroAtendimento', null, null, 600, 730, '', document.forms[0].RAEfetiva);">
								
								<img width="23" 
									height="21" 
									border="0"
									src="<bean:message key="caminho.imagens"/>pesquisa.gif"
									title="Pesquisar RA" /></a> 

							<a href="javascript:limparPesquisaRA();"> 
								<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
									border="0" 
									title="Apagar" /></a>
						</td>
				</tr>
				<tr>
					<td><strong>Observacao:</strong></td>
					<td><html:textarea name="ConsultarRegistroAtendimentoIncompletoActionForm" readonly="true" property="observacao" cols="40" rows="5" onkeyup="limitTextArea(document.forms[0].observacao, 400, document.getElementById('utilizado'), document.getElementById('limite'));"/><br>
					<strong><span id="utilizado">0</span>/<span id="limite">400</span></strong></td>
				</tr>
				<tr>
					<td colspan="2" height="10"></td>
				</tr> 
				<tr>
					<td colspan="2"><font color="#FF0000"> <logic:present
						name="inserir" scope="request">
						<input type="button" name="ButtonReset" class="bottonRightCol"
							value="Voltar"
							onClick="javascript:window.location.href='/gsan/exibirFiltrarRegistroAtendimentoIncompletoAction.do?menu=sim'">
					</logic:present><logic:notPresent name="inserir" scope="request">
						<input type="button" name="ButtonReset" class="bottonRightCol"
							value="Voltar" onClick="javascript:history.back();">
					</logic:notPresent><input type="button" name="ButtonReset"
						class="bottonRightCol" value="Desfazer"
						onClick="(document.forms[0]).reset()"> <input type="button"
						name="ButtonCancelar" class="bottonRightCol" value="Cancelar"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
					</font></td>
					<td align="right">
					   <input type="button" onclick="javascript:validarForm();" name="botaoAtualizar" class="bottonRightCol" value="Atualizar">
					</td>
				</tr>
				<tr>
					<td colspan="2">
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
<!-- registro_atendimento_incompleto_atualizar.jsp -->
</html:html>

