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
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<%@ page import="gcom.util.ConstantesSistema"%>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false"  formName="GerarRelatorioGrandesConsumidoresActionForm"/>

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript">
	
	function validarForm(){
		
		var form = document.forms[0];
		if(validarCampos()){
			toggleBox('demodiv',1);
		}
	}
	
	
	function validarCampos(){
		
		var form = document.forms[0];
		msg = verificarLocalidadeInicialFinal(form.codigoLocalidadeInicial,form.codigoLocalidadeFinal);

		if (isNaN(form.codigoGerencia.value) || form.codigoGerencia.value == ""){
			alert('Informe Gerência Regional.');
			return false;
		}else if( msg != ""){
			alert(msg);
			return false;
		}
	
		return true;
	}
	
	function verificarLocalidadeInicialFinal(campoInicio, campoFim){
		
		var msg = "";     		
	   
		if (campoInicio.value.length == 0){
			msg = "Informe Localiade Inicial.";
		} else if (campoFim.value.length == 0) {
			msg = "Informe Localidade Final.";
		} else {
			if (campoInicio.value > campoFim.value){
				msg = "Localidade Final deve ser maior ou igual a Localidade Inicial.";
			}
		}
		
		return msg;
	}
	
	
	
	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,campo){

		if(!campo.disabled){
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
	}
	
  	function limpar(){

  		var form = document.forms[0];

  		form.action='exibirGerarRelatorioGrandesConsumidoresAction.do?menu=sim&limparCampos=true';
	    form.submit();
  	}
  	
  	function replicarLocalidade(valorCampo){

		var formulario = document.forms[0]; 

		if (validaCampoInteiroPositivo(valorCampo)){
			formulario.codigoLocalidadeFinal.value = formulario.codigoLocalidadeInicial.value;
		}else{
			alert('O campo Localidade Inicial aceita apenas números inteiros e positivos.');
			formulario.codigoLocalidadeInicial.value = "";
		}

		
	}

	function limparFinal(){

		var form = document.forms[0];

		form.nomeLocalidadeFinal.value = "";
		form.codigoLocalidadeFinal.value = "";
	}
	
	function limparInicial(borracha){

		var form = document.forms[0];

		if (borracha){
			form.codigoLocalidadeInicial.value = "";
		}
		
		form.nomeLocalidadeInicial.value = "";
		form.codigoLocalidadeFinal.value = "";
		form.nomeLocalidadeFinal.value = "";
	}
	
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

		var form = document.forms[0];

		if (tipoConsulta == 'localidadeOrigem') {
      		
      		form.codigoLocalidadeInicial.value = codigoRegistro;
	  		form.nomeLocalidadeInicial.value = descricaoRegistro;
	  		
	  		form.codigoLocalidadeFinal.value = codigoRegistro;
      		form.nomeLocalidadeFinal.value = descricaoRegistro;
      		
	  		form.nomeLocalidadeInicial.style.color = "#000000";
	  		form.nomeLocalidadeFinal.style.color = "#000000";
		}

		if (tipoConsulta == 'localidadeDestino') {
		
      		form.codigoLocalidadeFinal.value = codigoRegistro;
      		form.nomeLocalidadeFinal.value = descricaoRegistro;
	  		form.nomeLocalidadeFinal.style.color = "#000000";
		}
	}

	function validarInteiro(valorCampo){

		var formulario = document.forms[0];
		if (!validaCampoInteiroPositivo(valorCampo)){
			formulario.codigoLocalidadeFinal.value = "";
			alert('O campo Localidade Final aceita apenas números inteiros e positivos.');
		}
	}

	function chamarPopupComValidacao(nomeCampo){

		var formulario = document.forms[0];
		if (isNaN(formulario.codigoGerencia.value) || formulario.codigoGerencia.value == ""){
			alert('Informe Gerência Regional.');
		}else{
			if (nomeCampo == 'Localidade Inicial'){
				chamarPopup('exibirPesquisarLocalidadeAction.do', 'origem', null, null, 275, 480, '', formulario.codigoLocalidadeInicial);
				limparInicial(false);
			}else{
				chamarPopup('exibirPesquisarLocalidadeAction.do', 'destino', null, null, 275, 480, '', formulario.codigoLocalidadeFinal);
				limparFinal();
			}
		}
	}
	  	
</script>

</head>

<body leftmargin="5" topmargin="5">

<html:form action="/gerarRelatorioGrandesConsumidoresAction.do"
	name="GerarRelatorioGrandesConsumidoresActionForm"
	type="gcom.gui.relatorio.cadastro.imovel.GerarRelatorioGrandesConsumidoresActionForm"
	method="post">

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

<table width="770" border="0" cellspacing="5" cellpadding="0">
	  <tr>
	    <td width="130" valign="top" class="leftcoltext">
	      <div align="center">
	
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
					<td class="parabg">Gerar Relat&oacute;rio  Grandes Consumidores</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				
				<tr>
					<td colspan="2">Para gerar o relat&oacute;rio, informe os dados abaixo:</td>
				</tr>
				
				<tr>
		      		<td><strong>Gerência Regional:<font color="#FF0000">*</font></strong></td>
	                <td>
	                    <html:select property="codigoGerencia" tabindex="5">
								<html:option value="">&nbsp;</html:option>
								<html:options collection="colecaoGerenciaRegional" labelProperty="nome" property="id" />
						</html:select>
	                </td>
	      		</tr>		
              	
				<tr>
					<td><strong>Localidade Inicial:<font color="#FF0000">*</font></strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							tabindex="1"
							property="codigoLocalidadeInicial" 
							size="3"
							onblur="javascript:replicarLocalidade(this.value);" 
							onkeyup="javascript:limparInicial(false);"
							onkeypress="javascript:limparInicial(false);validaEnterComMensagem(event, 'exibirGerarRelatorioGrandesConsumidoresAction.do?objetoConsulta=1','codigoLocalidadeInicial','Localidade Inicial');"/>
							
						<a href="javascript:chamarPopupComValidacao('Localidade Inicial');">
							<img width="23" 
								height="21" 
								border="0" 
								style="cursor:hand;"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Localidade" /></a>
								

						<logic:present name="localidadeInicialEncontrada" scope="request">
							<html:text property="nomeLocalidadeInicial" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 

						<logic:notPresent name="localidadeInicialEncontrada" scope="request">
							<html:text property="nomeLocalidadeInicial" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>

						
						<a href="javascript:limparInicial(true);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>
				
				<tr>
					<td><strong>Localidade Final:<font color="#FF0000">*</font></strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							tabindex="1"
							property="codigoLocalidadeFinal" 
							size="3"
							onkeypress="validaEnterComMensagem(event, 'exibirGerarRelatorioGrandesConsumidoresAction.do?objetoConsulta=3','codigoLocalidadeFinal','Localidade Final');"
							onblur="validarInteiro(this.value);"/>
							
						<a href="javascript:chamarPopupComValidacao('Localidade Final');">
							<img width="23" 
								height="21" 
								border="0" 
								style="cursor:hand;"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Localidade" /></a>
								 

						<logic:present name="localidadeFinalEncontrada" scope="request">
							<html:text property="nomeLocalidadeFinal" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 

						<logic:notPresent name="localidadeFinalEncontrada" scope="request">
							<html:text property="nomeLocalidadeFinal" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>

						
						<a href="javascript:limparFinal();"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>
				
				<tr>
					<td>&nbsp;</td>
					<td align="right">
						<div align="left">
							<strong><font color="#FF0000">*</font></strong>
							Campos obrigat&oacute;rios</div>
					</td>
				</tr>
	
				<tr>
					<td height="24" >
			          	<input type="button" 
			          		class="bottonRightCol" 
			          		value="Limpar" 
			          		onclick="javascript:limpar();"/>
					</td>
				
					<td align="right">
						<input type="button" 
							name="Button" 
							class="bottonRightCol" 
							value="Gerar" 
							onClick="javascript:validarForm()" />
					</td>
					
				</tr>
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>
<jsp:include page="/jsp/relatorio/escolher_tipo_relatorio.jsp?relatorio=gerarRelatorioGrandesConsumidoresAction.do" />
<%@ include file="/jsp/util/rodape.jsp"%>	
</html:form>
</body>
</html:html>
