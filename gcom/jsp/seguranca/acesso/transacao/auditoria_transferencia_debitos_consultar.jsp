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
<script language="JavaScript">

	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
		var form = document.ConsultarAuditoriaTransferenciaDebitosActionForm;
	 	if (tipoConsulta == 'funcionario') {       	
			form.idFuncionario.value = codigoRegistro;
			form.nomeFuncionario.value = descricaoRegistro;
			form.nomeFuncionario.style.color = "#000000";
		}else if ('idUsuarioOrigem' == tipoConsulta) {
		 	form.idUsuarioOrigem.value = codigoRegistro;
		 	form.nomeUsuarioOrigem.value = descricaoRegistro;
		 	form.nomeUsuarioOrigem.style.color = "#000000";
	 	}else if ('idUsuarioDestino' == tipoConsulta) {
	 		form.idUsuarioDestino.value = codigoRegistro;
	 		form.nomeUsuarioDestino.value = descricaoRegistro;
	 		form.nomeUsuarioDestino.style.color = "#000000";
	 	}
	}
	


function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,campo){
		
		document.forms[0].tipoPesquisa.value = campo.name;
		
	  		if (objeto == null || codigoObjeto == null){
	     		if(tipo == "" ){
	      			abrirPopup(url,altura, largura);
	     		}else{
		  			abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
		 		}
	 		}else{
				if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
					alert(msg);
				}else{
					abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto, altura, largura);
				}
			}
		
	}
	
	
	
	
	
	function dataEstahLimpa(){
		 	var dataInicial = document.forms[0].dataInicial.value;
		 	var dataFinal = document.forms[0].dataFinal.value;
		 	if (dataInicial == '' || dataFinal == ''){
		 	    document.forms[0].dataLimpa.value = '1';
		 	} else {
		    	document.forms[0].dataLimpa.value = '0';
		 	}
	}
	 	
	function validaForm(){	
		var form = document.ConsultarAuditoriaTransferenciaDebitosActionForm;
		form.submit();
	}
	 
	function limparFuncionario() {		
		var form = document.ConsultarAuditoriaTransferenciaDebitosActionForm;	
		form.idFuncionario.value = "";
		form.nomeFuncionario.value = "";
		desabilitarIdUsuario();
	}
	
	function limparUsuario() {		
		var form = document.ConsultarAuditoriaTransferenciaDebitosActionForm;	
		form.idUsuarioOrigem.value = "";
		form.nomeUsuarioOrigem.value = "";
		form.idUsuarioDestino.value = "";
		form.nomeUsuarioDestino.value = "";
		desabilitarIdFuncionario();
	}
	
	function limparDataInicial() {		
		limparDataFinal();	
		var form = document.ConsultarAuditoriaTransferenciaDebitosActionForm;
		form.dataInicial.value = "";
	}
	
	function limparDataFinal() {	
		var form = document.ConsultarAuditoriaTransferenciaDebitosActionForm;
		form.dataFinal.value = "";
	}
	
	function desabilitarIdUsuario(){
		var form = document.ConsultarAuditoriaTransferenciaDebitosActionForm;
		if(form.idFuncionario.value != "" ){
			form.idUsuarioOrigem.disabled = true;
			form.idUsuarioDestino.disabled = true;
			limparUsuario();
		}else{
			form.idUsuarioOrigem.disabled = false;	
			form.idUsuarioDestino.disabled = false;
		}
	}

	function desabilitarIdFuncionario(){
		var form = document.ConsultarAuditoriaTransferenciaDebitosActionForm;
		if(form.idUsuarioOrigem.value != "" || form.idUsuarioDestino.value != "" ){
			form.idFuncionario.disabled = true;	
			limparFuncionario();
		}else{
			form.idFuncionario.disabled = false;					 
		}
	}
	 
</script>



</head>
<body leftmargin="5" topmargin="5" onload="desabilitarIdUsuario();desabilitarIdFuncionario();">
	<html:form action="/consultarAuditoriaTransferenciaDebitosAction.do"
	   name="ConsultarAuditoriaTransferenciaDebitosActionForm"
	   type="gcom.gui.seguranca.acesso.transacao.ConsultarAuditoriaTransferenciaDebitosActionForm"
	   method="post" >
	  
		<table width="100%" border="0">
			<tr>
				<td colspan="3">Para Consultar as Transferências de Debitos, informe os dados abaixo:</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
				
				<tr>
					<td><strong>Funcionário:</strong></td>
					<td colspan="2">
						<html:text maxlength="9" property="idFuncionario"
							tabindex="1" size="9"
							onkeypress="javascript:validaEnterComMensagem(event, 'exibirConsultarAuditoriaTransferenciaDebitosAction.do?objetoConsulta=1', 'idFuncionario', 'Funcionário');" 
							onkeyup="desabilitarIdUsuario();"
							onblur="desabilitarIdUsuario();"/>
						<a
							href="javascript:abrirPopup('exibirPesquisarFuncionarioAction.do');">
						<img width="23" height="21" border="0"
							src="<bean:message key="caminho.imagens"/>pesquisa.gif"
							title="Pesquisar Funcionário" /></a> <logic:present
							name="idFuncionarioNaoEncontrado" scope="request">
							<html:text maxlength="30" property="nomeFuncionario"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000"
								size="40" />
						</logic:present> <logic:notPresent
							name="idFuncionarioNaoEncontrado" scope="request">
							<html:text maxlength="30" property="nomeFuncionario"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000"
								size="40" />
						</logic:notPresent> <a href="javascript:limparFuncionario();"> <img
							src="<bean:message key="caminho.imagens"/>limparcampo.gif"
							border="0" title="Apagar" /></a>
					</td>
				</tr>
				<tr>
					<td><input type="hidden" name="tipoPesquisa" value=""/></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
                  <td>
	                  <strong>Usuário Origem:</strong>
                  </td>
                 <td>
					<html:text property="idUsuarioOrigem" maxlength="9" size="9"
						onkeypress="validaEnterComMensagem(event, 'exibirConsultarAuditoriaServicosACobrarAction.do', 'idUsuarioOrigem', 'Matrícula do Usuário Origem')" />
                  	<a href="javascript:chamarPopup('exibirUsuarioPesquisar.do', 'origem', 'null', null, 275, 480, '',document.forms[0].idUsuarioOrigem);">
									<img width="23" 
										height="21" 
										border="0" 
										style="cursor:hand;"
										src="<bean:message key="caminho.imagens"/>pesquisa.gif"
										title="Pesquisar Usuário Origem" /></a>
                  
                    
                    <logic:present name="usuarioNaoEncontradoOrigem">
						<html:text property="nomeUsuarioOrigem" size="25" maxlength="40"
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000" />
					</logic:present> <logic:notPresent name="usuarioNaoEncontradoOrigem">
						<html:text property="nomeUsuarioOrigem" size="25" maxlength="40"
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000" />
					</logic:notPresent>
                  	<a href="javascript:limparUsuario();"> <img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" /></a>	
                  	
                  </td>
                  <td>&nbsp;</td>
				</tr>
				<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				<tr>
                  <td>
	                  <strong>Usuário Destino:</strong>
                  </td>
                 <td>
					<html:text property="idUsuarioDestino" maxlength="9" size="9"
						onkeypress="validaEnterComMensagem(event, 'exibirConsultarAuditoriaServicosACobrarAction.do', 'idUsuarioDestino', 'Matrícula do Usuário Destino')" />
						<a href="javascript:chamarPopup('exibirUsuarioPesquisar.do', 'destino', 'null', null, 275, 480, '',document.forms[0].idUsuarioDestino);">
															<img width="23" 
																height="21" 
																border="0" 
																style="cursor:hand;"
																src="<bean:message key="caminho.imagens"/>pesquisa.gif"
																title="Pesquisar Usuário Destino" /></a>               		


                    <logic:present name="usuarioNaoEncontradoDestino">
						<html:text property="nomeUsuarioDestino" size="25" maxlength="40"
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000" />
					</logic:present> <logic:notPresent name="usuarioNaoEncontradoDestino">
						<html:text property="nomeUsuarioDestino" size="25" maxlength="40"
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000" />
					</logic:notPresent>
                  	<a href="javascript:limparUsuario();"> <img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" /></a>	
                  	
                  </td>
                  <td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td><strong>Data Inicial:</strong></td>
					<td>
					<input type="hidden" name="dataLimpa" value="0">
                  		<html:text name="ConsultarAuditoriaTransferenciaDebitosActionForm" onkeyup="mascaraData(this, event);"
                  	 		onblur="javascript:dataEstahLimpa()"  property="dataInicial" size="10" maxlength="10" tabindex="3"/> 
						<a href="javascript:abrirCalendario('ConsultarAuditoriaTransferenciaDebitosActionForm', 'dataInicial')">
						<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="20" border="0" align="middle" alt="Exibir Calendário" />
						</a>&nbsp; dd/mm/aaaa
						<a href="javascript:limparDataInicial();"> <img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" /></a>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td><strong>Data Final:</strong></td>	
					<td>			
					<html:text name="ConsultarAuditoriaTransferenciaDebitosActionForm" onkeyup="mascaraData(this, event);" property="dataFinal" size="10" maxlength="10" tabindex="4"/> 
						<a href="javascript:abrirCalendario('ConsultarAuditoriaTransferenciaDebitosActionForm', 'dataFinal')"><img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif"
							width="20" border="0" align="middle" alt="Exibir Calendário" /></a> dd/mm/aaaa
						<a href="javascript:limparDataFinal();"> <img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" /></a>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>
						<strong> 
							<font color="#ff0000"> 
								<input	name="Submit22" 
										class="bottonRightCol"
										value="Limpar"
										tabindex="5" 
										type="button"
										onclick="window.location.href='/gsan/exibirConsultarAuditoriaTransferenciaDebitosAction.do?menu=sim';">
								<input type="button"
										name="ButtonCancelar" 
										class="bottonRightCol" 
										value="Cancelar"
										tabindex="6"
										onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
							</font>
						</strong>
					</td>
					<td align="right"></td>
					<td align="right">
						<gcom:controleAcessoBotao name="Button" value="Consultar" tabindex="7" onclick="javascript:validaForm();" url="consultarAuditoriaTransferenciaDebitosAction.do"/>
					</td>
				</tr>
			</table>
	</html:form>
</body>
</html>