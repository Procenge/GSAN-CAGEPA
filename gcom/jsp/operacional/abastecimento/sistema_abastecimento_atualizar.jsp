<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gsan" %>

<%@ page import="gcom.util.ConstantesSistema" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false"  
	formName="AtualizarSistemaAbastecimentoActionForm" 
	dynamicJavascript="false" />

<SCRIPT LANGUAGE="JavaScript">
	function validarForm(formulario) {
	
	 	if (validarCampos()){
			submeterFormPadrao(formulario);
		}
	}

	function validarCampos(){
		var retorno = true;
		var form = document.forms[0];
		var msg = '';
		if(form.codigo.value == ''){
			msg = 'Informe o campo Código. \n';
		}
		if (form.codigo.value != null && form.codigo.value != ""){
			if (!validaCampoInteiroPositivo(form.codigo.value)){
				msg = 'O campo código aceita apenas números inteiros e positivos. \n';
				retorno = false;
			}
		}

		if (form.gerenciaRegional.value != null && form.gerenciaRegional.value == "-1"){
			if (!validaCampoInteiroPositivo(form.gerenciaRegional.value)){
				msg = msg +  'Informe o campo Gerência Regional.';
				retorno = false;
			}
		}
		
		if(msg != ''){
			alert(msg);
		}
		
		return retorno;
	}

	function validarCampoInteiro(valorCampo, nomeCampo, descricaoCampo){
	
		var form = document.forms[0];
	
		if (valorCampo != null && !valorCampo == ""){
			if (!validaCampoInteiroPositivo(valorCampo)){
				if (nomeCampo == 'codigo'){
					form.codigo.value = "";
					alert('O campo '+ descricaoCampo + ' aceita apenas números inteiros e positivos.');
				}
			}
		}
	}
	
</SCRIPT>
</head>

<body leftmargin="5" topmargin="5">

<html:form action="/atualizarSistemaAbastecimentoAction.do" method="post">
	
	<html:hidden name="AtualizarSistemaAbastecimentoActionForm" property="id" />

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp" %>

	<table width="770" border="0" cellspacing="5" cellpadding="0">
  		<tr>
    		<td width="130" valign="top" class="leftcoltext">
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

			<td width="625" valign="top" class="centercoltext">

	        <table height="100%">
	        	<tr>
	          		<td></td>
	        	</tr>
	      	</table>

	      	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	        	<tr>
	          		<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
	          		<td class="parabg">Atualizar Sistema de Abastecimento</td>
	          		<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
	        	</tr>
	      	</table>
	      	
      		<p>&nbsp;</p>

	      	<table width="100%" border="0">
		      	<tr>
		      		<td colspan="2">Para alterar o Sistema de Abastecimento, informe os dados abaixo:</td>
		      	</tr>
	      	</table>
    
      		<table width="100%" border="0">
      		
      		<tr>
				<td><strong>Código:
					<font color="#FF0000">*</font></strong>
				</td>
				
				<td align="right">
				<div align="left">
					<html:text maxlength="4"
						property="codigo" 
						size="4" 
						tabindex="1"
						readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000"
						onkeypress="return isCampoNumerico(event);"
						onblur="javascript:validarCampoInteiro(this.value, 'codigo', 'Código');"/></div>
				</td>
		  	  </tr>
		      
		      <tr>
		      	<td height="20"><strong>Descrição:
		      		<font color="#FF0000">*</font></strong>
		      	</td>
		        
		        <td align="right">
					<div align="left">
					<html:text maxlength="20" 
						property="descricao" 
						size="55" 
						tabindex="1"/>

					</div>
				</td>
      		  </tr>
      		  
   		  <tr>
		      	<td height="20"><strong>Descrição Abreviada:</strong>
		      	</td>
		        
		        <td align="right">
					<div align="left">
					<html:text maxlength="6" 
						property="descricaoAbreviada" 
						size="7" 
						tabindex="2"/>
	
					</div>
				</td>
   		 	</tr>
      			  		
	  		<tr>
        		<td><strong>Indicador de uso:</strong></td>
        		<td>
					<html:radio property="indicadorUso" value="1"/><strong>Ativo
					<html:radio property="indicadorUso" value="2"/>Inativo</strong>
				</td>
      		</tr>

			<tr>
					<td><strong>Gerência Regional:</strong>
					<font color="#FF0000">*</font>
					</td>
					
					<td align="right">
					<div align="left">
						<html:select property="gerenciaRegional" tabindex="4">
							<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<html:options collection="gerenciasRegionais" property="id" labelProperty="nomeComId"/> 
	                   	</html:select>
						</div>
					</td>
				</tr>

	      	<tr>
	      		<td><strong> <font color="#FF0000"></font></strong></td>
	        	<td align="right">
	        		<div align="left"><strong><font color="#FF0000">*</font></strong>
	                    Campos obrigat&oacute;rios</div>
	            </td>
	      	</tr>					
	      	      
      		<tr>
      			<td colspan="2" widt="100%">
      			<table width="100%">
					<tr>
						<tr>
							<td width="40%" align="left">
								<input type="button"
									name="ButtonCancelar" 
									class="bottonRightCol" 
									value="Voltar"
									onClick="window.history.go(-1)"> 
								<input type="button"
									name="ButtonReset" 
									class="bottonRightCol" 
									value="Desfazer"
									onClick="window.location.href='/gsan/exibirAtualizarSistemaAbastecimentoAction.do?idRegistroAtualizacao=<bean:write name="AtualizarSistemaAbastecimentoActionForm" property="id" />'"> 
								<input type="button"
									name="ButtonCancelar" 
									class="bottonRightCol" 
									value="Cancelar"
									onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
							</td>
							<td align="right">
								<input type="button"
									onClick="javascript:validarForm(document.forms[0]);"
									name="botaoAtualizar" 
									class="bottonRightCol" 
									value="Atualizar">
							</td>
						</tr>
	      			
      			</table>
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
</html:html>
