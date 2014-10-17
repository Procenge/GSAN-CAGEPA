<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gsan" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false"  
	formName="AtualizarSetorAbastecimentoActionForm" 
	dynamicJavascript="true" />

<SCRIPT LANGUAGE="JavaScript">
	function validarForm(form) {
 		if (validateAtualizarSetorAbastecimentoActionForm(form)){
			submeterFormPadrao(form);
		}
	}

</SCRIPT>
</head>

<body leftmargin="5" topmargin="5">

<html:form action="/atualizarSetorAbastecimentoAction.do" method="post">
	
	<html:hidden name="AtualizarSetorAbastecimentoActionForm" property="id" />

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
	          		<td class="parabg">Atualizar Setor de Abastecimento</td>
	          		<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
	        	</tr>
	      	</table>
	      	
      		<p>&nbsp;</p>

	      	<table width="100%" border="0">
		      	<tr>
		      		<td colspan="2">Para alterar o Setor de Abastecimento, informe os dados abaixo:</td>
		      	</tr>
	      	</table>
    
      		<table width="100%" border="0">
      		
      		<tr>
					<td><strong>Código do Setor de Abastecimento:
						<font color="#FF0000">*</font></strong>
					</td>
					
					<td align="right">
					<div align="left">
						<html:text maxlength="4"
							property="codigo" 
							size="4" 
							tabindex="1" 
							readonly="true"
							onblur="javascript:validarCampoInteiro(this.value, 'codigo', 'Código');"/></div>
					</td>
				</tr>
		      <tr>
			  		<td  width="40%"class="style3">
			  			<strong>
			  				Sistema de Abastecimento: <font color="#FF0000">*</font>
			  			</strong>
			  		</td>
			  		<td  width="60%" colspan="2">
			  			<html:select property="sistemaAbastecimento" tabindex="4" style="width:200px;" disabled="true">
							<html:option value="-1"> &nbsp; </html:option>
							<html:options collection="colecaoSistemaAbastecimento" property="id" labelProperty="descricaoComCodigo"/>
						</html:select>
					</td>
				</tr>
				
				<tr>
			  		<td  width="40%"class="style3">
			  			<strong>
			  				Distrito Operacional: <font color="#FF0000">*</font>
			  			</strong>
			  		</td>
			  		<td  width="60%" colspan="2">
			  			<html:select property="distritoOperacional" tabindex="4" style="width:200px;" disabled="true">
							<html:option value="-1"> &nbsp; </html:option>
							<html:options collection="colecaoDistritoOperacional" property="id" labelProperty="descricaoComId"/>
						</html:select>
					</td>
				</tr>
				
				<tr>
			  		<td  width="40%"class="style3">
			  			<strong>
			  				Zona de Abastecimento: <font color="#FF0000">*</font>
			  			</strong>
			  		</td>
			  		<td  width="60%" colspan="2">
			  			<html:select property="zonaAbastecimento" tabindex="4" style="width:200px;" disabled="true">
							<html:option value="-1"> &nbsp; </html:option>
							<html:options collection="colecaoZonaAbastecimento" property="id" labelProperty="descricaoComCodigo"/>
						</html:select>
					</td>
				</tr>
		      <tr>
					<td><strong>Descrição do Setor de Abastecimento:
						<font color="#FF0000">*</font></strong>
					</td>
					
					<td align="right">
					<div align="left">
						<html:text maxlength="30"
							property="descricao" 
							size="52"
							tabindex="2" /></div>
					</td>
				</tr>
      		  
   		    <tr>
					<td><strong>Descrição Abreviada:</strong>
					</td>
					
					<td align="right">
					<div align="left">
						<html:text maxlength="6"
							property="descricaoAbreviada"
							size="6" 
							tabindex="3" /></div>
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
									onClick="window.location.href='/gsan/exibirAtualizarSetorAbastecimentoAction.do?idRegistroAtualizacao=<bean:write name="AtualizarSetorAbastecimentoActionForm" property="id" />'"> 
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
