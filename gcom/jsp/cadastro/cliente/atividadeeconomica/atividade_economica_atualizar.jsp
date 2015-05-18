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
	formName="AtualizarAtividadeEconomicaActionForm" 
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
		
		if(form.descricao.value == ''){
			
			msg = 'Informe o campo Descrição. \n';
		}
		
		if(msg != ''){
			
			alert(msg);
		}
		
		return retorno;
	}
	
</SCRIPT>
</head>

<body leftmargin="5" topmargin="5">

<html:form action="/atualizarAtividadeEconomicaAction.do" method="post">
	
	<html:hidden name="AtualizarAtividadeEconomicaActionForm" property="id" />

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
	          		<td class="parabg">Atualizar Atividade Ecônomica</td>
	          		<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
	        	</tr>
	      	</table>
	      	
      		<p>&nbsp;</p>

	      	<table width="100%" border="0">
		      	<tr>
		      		<td colspan="2">Para alterar a atividade ecônomica, informe os dados abaixo:</td>
		      	</tr>
	      	</table>
    
      		<table width="100%" border="0">
      		
      		<tr>
				<td><strong>Código:
					<font color="#FF0000">*</font></strong>
				</td>
				
				<td align="right">
				<div align="left">
					<html:text maxlength="10"
						property="codigo" 
						size="10" 
						tabindex="1"
						readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000"/>
					(X9999-9/99)
				</div>
				</td>
		  	  </tr>
		      
		      <tr>
		      	<td height="20"><strong>Descrição:
		      		<font color="#FF0000">*</font></strong>
		      	</td>
		        
		        <td align="right">
					<div align="left">
					<html:text maxlength="200" 
						property="descricao" 
						size="60" 
						tabindex="1"/>

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
					<td><strong>Perfil da Ligação de Esgoto Aplicado:</strong>
					</td>
					
					<td align="right">
						<div align="left">
							<logic:present name="colecaoLigacaoEsgotoPerfil">
								<html:select property="idLigacaoEsgotoPerfil" tabindex="3">
									<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
									<html:options collection="colecaoLigacaoEsgotoPerfil" property="id" labelProperty="descricao"/> 
			                   	</html:select>
		                   	</logic:present>
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
									onClick="window.location.href='/gsan/exibirAtualizarAtividadeEconomicaAction.do?idRegistroAtualizacao=<bean:write name="AtualizarAtividadeEconomicaActionForm" property="id" />'"> 
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
