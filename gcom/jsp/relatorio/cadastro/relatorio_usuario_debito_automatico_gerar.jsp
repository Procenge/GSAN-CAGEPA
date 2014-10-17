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

<html:javascript staticJavascript="false"  formName="GerarRelatorioUsuarioDebitoAutomaticoActionForm"/>

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
		
		msg = verificarBancoInicialFinal(form.codigoBancoInicial,form.codigoBancoFinal);
		if( msg != ""){
			alert(msg);
			return false;
		}
	
		return true;
	}
	
	function verificarBancoInicialFinal(campoInicio, campoFim){
		
		var msg = "";     		
	   
		if (campoInicio.value.length == 0){
			msg = "Informe Banco Inicial.";
		} else if (campoFim.value.length == 0) {
			msg = "Informe Banco Final.";
		} else {
			if (campoInicio.value > campoFim.value){
				msg = "Cód. Banco Final menor que o Cód. Banco Inicial.";
			}
		}
		
		return msg;
	}
	
  	function limpar(){

  		var form = document.forms[0];

		form.codigoBancoInicial.value = "";
		form.codigoBancoFinal.value = "";
  	}
  	
  	function replicarBanco(valorCampo){

		var formulario = document.forms[0]; 

		if (validaCampoInteiroPositivo(valorCampo)){
			formulario.codigoBancoFinal.value = formulario.codigoBancoInicial.value;
		}else{
			alert('O campo Código do Banco Inicial aceita apenas números inteiros e positivos.');
			formulario.codigoBancoInicial.value = "";
		}
		
	}
	
	function validarInteiro(valorCampo){

		var formulario = document.forms[0];
		if (!validaCampoInteiroPositivo(valorCampo)){
			formulario.codigoBancoFinal.value = "";
			alert('O campo Código do Banco Final aceita apenas números inteiros e positivos.');
		}
	}
	  	
</script>

</head>

<body leftmargin="5" topmargin="5">

<html:form action="/gerarRelatorioUsuarioDebitoAutomaticoAction.do"
	name="GerarRelatorioUsuarioDebitoAutomaticoActionForm"
	type="gcom.gui.relatorio.cadastro.cliente.GerarRelatorioUsuarioDebitoAutomaticoActionForm"
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
					<td class="parabg">Gerar Relat&oacute;rio  Usu&aacute;rios em D&eacute;bito Autom&aacute;tico</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				
				<tr>
					<td>Para gerar o relat&oacute;rio, informe os dados abaixo:</td>
				</tr>		
              	
				<tr>
					<td><strong>C&oacute;digo do Banco Inicial:<font color="#FF0000">*</font></strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							tabindex="1"
							property="codigoBancoInicial" 
							size="3"
							onblur="javascript:replicarBanco(this.value);"/>
							
					</td>
				</tr>
				
				<tr>
					<td><strong>C&oacute;digo do Banco Final:<font color="#FF0000">*</font></strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							tabindex="2"
							property="codigoBancoFinal" 
							size="3"
							onblur="validarInteiro(this.value);"/>
							
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
			          		tabindex="3" 
			          		class="bottonRightCol" 
			          		value="Limpar" 
			          		onclick="javascript:limpar();"/>
					</td>
				
					<td align="right">
						<input type="button" 
							name="Button" 
							tabindex="4"
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
<jsp:include page="/jsp/relatorio/escolher_tipo_relatorio.jsp?relatorio=gerarRelatorioUsuarioDebitoAutomaticoAction.do" />
<%@ include file="/jsp/util/rodape.jsp"%>	
</html:form>
</body>
</html:html>
