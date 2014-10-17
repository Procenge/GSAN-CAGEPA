 <%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>
<%@ page import="gcom.util.ConstantesSistema" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>

<SCRIPT LANGUAGE="JavaScript">

	function validarForm() {
      var form = document.forms[0];
      form.action = 'pesquisarComandoNegativacaoAction.do';
      
	  if(validatePesquisarComandoNegativacaoActionForm(form)){	
		  if ((comparaData(form.periodoGeracaoComandoInicial.value,'>',form.periodoGeracaoComandoFinal.value)) ||
		  	(comparaData(form.periodoExecucaoComandoInicial.value,'>',form.periodoExecucaoComandoFinal.value))){
		  	alert ('Data Final do Período é anterior à Data Inicial do Período');
		  } else{
		  	submeterFormPadrao(form); 
		  }
		}
   	  } 
   	   	
	function limparUsuarioResponsavel() {
		var form = document.forms[0];
		
      	form.usuarioResponsavelId.value = '';
	    form.usuarioResponsavelNome.value = '';
	}
	
	/* Chama Popup */ 
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
	
	/* Recuperar Popup */
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	    var form = document.forms[0];
	    
	      form.usuarioResponsavelId.value = codigoRegistro;
	      form.usuarioResponsavelNome.value = descricaoRegistro;
	      form.usuarioResponsavelNome.style.color = '#000000';	 
	    
	}
	
	function setaFocus(){
		var form = document.PesquisarComandoNegativacaoActionForm;
		form.tituloComando.focus();
	}

</SCRIPT>

</head>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="PesquisarComandoNegativacaoActionForm" />

<body leftmargin="5" topmargin="5" onload="setaFocus();">

<html:form action="/pesquisarComandoNegativacaoAction"
	name="PesquisarComandoNegativacaoActionForm"
	type="gcom.gui.cabranca.PesquisarComandoNegativacaoActionForm"
	method="post"
	onsubmit="return validatePesquisarComandoNegativacaoActionForm(this);">

	<table width="600" border="0" cellspacing="5" cellpadding="0">
		<tr>
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
					<td class="parabg">Pesquisar Comando de Negativação</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>

			<table width="100%" border="0">

				<tr>
					<td width="100%" colspan="3">
					<table width="100%">
						<tr>
							<td width="80%">Preencha os campos para pesquisar Comandos de Negativação:</td>
						</tr>
					</table>
					</td>
				</tr>
			</table>

			<table width="100%" border="0">
				
				<tr>
					<td width="30%">
						<strong>Título do Comando:</strong>
					</td>
					<td width="70%">
						<strong> 
						<html:textarea property="tituloComando" cols="40" rows="5" tabindex="1" />
						</strong>
					</td>
				</tr>

				 <tr>
					<td width="30%">&nbsp;</td>
					<td width="70%">
						<html:radio property="tipoPesquisa"	tabindex="2"
						value="<%=ConstantesSistema.TIPO_PESQUISA_INICIAL.toString()%>" />Iniciando pelo texto
						<html:radio	property="tipoPesquisa" tabindex="3" 
						value="<%=ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString()%>" />Contendo o texto
					</td>
				</tr>
				<tr>
					<td width="30%"><strong>Comando Simulado:<font color="#FF0000">*</font></strong></td>
					<td width="70%">
						<html:radio property="indicadorComandoSimulado" 
							value="<%=ConstantesSistema.TODOS.toString()%>" tabindex="4" /><strong>Todos
						<html:radio property="indicadorComandoSimulado" 
							value="<%=ConstantesSistema.SIM.toString()%>" tabindex="5" />Sim
						<html:radio property="indicadorComandoSimulado" 
							value="<%=ConstantesSistema.NAO.toString()%>" tabindex="6" />Não</strong>
					</td>
				</tr>
				
				
				<tr> 
	           		<td width="30%" class="style3"><strong>Período de Geração do Comando:</strong></td>
					<td width="70%" colspan="2">

						<html:text	property="periodoGeracaoComandoInicial" size="10" maxlength="10" tabindex="7" 
						onkeyup="mascaraData(this, event);replicarCampo(document.forms[0].periodoGeracaoComandoFinal,this);" 
						onfocus="replicarCampo(document.forms[0].periodoGeracaoComandoFinal,this);" /> 
						
						<a href="javascript:abrirCalendarioReplicando('PesquisarComandoNegativacaoActionForm', 'periodoGeracaoComandoInicial', 'periodoGeracaoComandoFinal');">
						<img border="0"	src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle"
						alt="Exibir Calendário" /></a>
						a
						<html:text property="periodoGeracaoComandoFinal" size="10" maxlength="10" tabindex="8" onkeyup="mascaraData(this, event);" /> 
		            	<a href="javascript:abrirCalendario('PesquisarComandoNegativacaoActionForm', 'periodoGeracaoComandoFinal')">
						<img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle"
						alt="Exibir Calendário" /></a> dd/mm/aaaa
					</td>
	           	</tr>
	           	
	           	<tr> 
	           		<td width="30%" class="style3"><strong>Período de Execução do Comando:</strong></td>
					<td width="70%" colspan="2">

						<html:text	property="periodoExecucaoComandoInicial" size="10" maxlength="10" tabindex="9" 
						onkeyup="mascaraData(this, event);replicarCampo(document.forms[0].periodoExecucaoComandoFinal,this);" 
						onfocus="replicarCampo(document.forms[0].periodoExecucaoComandoFinal,this);" /> 
						
						<a href="javascript:abrirCalendarioReplicando('PesquisarComandoNegativacaoActionForm', 'periodoExecucaoComandoInicial', 'periodoExecucaoComandoFinal');">
						<img border="0"	src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle"
						alt="Exibir Calendário" /></a>
						a
						<html:text property="periodoExecucaoComandoFinal" size="10" maxlength="10" tabindex="10" onkeyup="mascaraData(this, event);" /> 
		            	<a href="javascript:abrirCalendario('PesquisarComandoNegativacaoActionForm', 'periodoExecucaoComandoFinal')">
						<img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle"
						alt="Exibir Calendário" /></a> dd/mm/aaaa
					</td>
	           	</tr>
	           	
	           	
	           	<tr> 	
                     <td width="200"> <strong>Usuário Responsável:</strong> </td>
                     <td colspan="3">
						<html:text property="usuarioResponsavelId" size="9" maxlength="11" tabindex="10" 
  								   onkeypress="javascript:validaEnterComMensagem(event, 'exibirPesquisarComandoNegativacaoAction.do?validaUsuarioResponsavel=true', 'usuarioResponsavelId','Usuário Responsável');"/>
						
						<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23"	height="21" 
						     style="cursor:hand;" name="imagem"	onclick="chamarPopup('exibirUsuarioPesquisar.do', 'usuario', null, null, 275, 480, '',document.forms[0].usuarioResponsavelId);"
							 alt="Pesquisar">

						<logic:present name="usuarioResponsavelEncontrada" scope="session">
							<html:text property="usuarioResponsavelNome" readonly="true"
   									   style="background-color:#EFEFEF; border:0; color: #000000" size="45"/>
						</logic:present> 
						<logic:notPresent name="usuarioResponsavelEncontrada" scope="session">
							<html:text property="usuarioResponsavelNome" readonly="true"
   									   style="background-color:#EFEFEF; border:0; color: #ff0000" size="45"/>
						</logic:notPresent>

						<a href="javascript:limparUsuarioResponsavel();">
							<img border="0" title="Apagar" src="<bean:message key='caminho.imagens'/>limparcampo.gif" /></a>
					</td>
                </tr>
				
				<tr>
					<td>
						<input name="Button" type="button" class="bottonRightCol" value="Limpar" align="left"
						onclick="window.location.href='/gsan/exibirPesquisarComandoNegativacaoAction.do?menu=sim'" tabindex="11">
					</td>
					
					<td align="right">
						<td align="right" colspan="2">
						<input name="Button" type="button" class="bottonRightCol" value="Pesquisar" align="left"
						onclick="javascript:validarForm();" tabindex="12">
					</td>
				</tr>

			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>
</html:form>
</body>
</html:html>

