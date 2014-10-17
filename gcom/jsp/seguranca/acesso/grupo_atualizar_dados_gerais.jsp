<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="AtualizarGrupoActionForm" dynamicJavascript="false" />

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>

<script>

	var bCancel = false;

    function validateAtualizarGrupoActionForm(form) {
    	if (bCancel)
      		return true;
        else
       		return  validateRequired(form) && validateCaracterEspecial(form) ;
   	}

    function caracteresespeciais () {
    	this.aa = new Array("descricao", "Descrição possui caracteres especiais.", new Function ("varName", " return this[varName];"));
      	this.ab = new Array("descricaoAbreviada", "Descrição Abreviada possui caracteres especiais.", new Function ("varName", " return this[varName];"));
    }
    
    function required () {
    	this.aa = new Array("descricao", "Informe Descrição.", new Function ("varName", " return this[varName];"));
      	this.ab = new Array("descricaoAbreviada", "Informe a Descrição Abreviada.", new Function ("varName", " return this[varName];"));
    }

</script>

</head>

<body>

<html:form
    action="/atualizarGrupoWizardAction"
    method="post"
    onsubmit="return validateAtualizarGrupoActionForm(this);">

<jsp:include page="/jsp/util/wizard/navegacao_abas_wizard_valida_avancar.jsp?numeroPagina=1"/>

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

<table width="770" border="0" cellspacing="4" cellpadding="0">
	
	<tr>
    	<td width="149" valign="top" class="leftcoltext">
    		<input type="hidden" name="numeroPagina" value="1"/>
      
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
      		
      		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		        <tr>
		          	<td width="11">
		          		<img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/>
		          	</td>
		          	<td class="parabg">Atualizar Grupo - Dados Gerais</td>
		          	<td width="11">
		          		<img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/>
		          	</td>
		        </tr>
      		</table>
      		
      		<p>&nbsp;</p>
       		
       		<table border="0" width="100%">
	        	<tr>
	           		<td colspan="2">Para adicionar o grupo, informe os dados abaixo:</td>
	           	</tr>
           
           		<tr>
             		<td><strong>Descrição:<font color="#ff0000">*</font></strong></td>
             		<td>
						<html:text maxlength="40" name="AtualizarGrupoActionForm" property="descricao" size="40" />
             		</td>
           		</tr>
           
	           	<tr> 
	           		<td><strong>Descrição Abreviada:<font color="#ff0000">*</font></strong></td>
	             	<td>
						<html:text maxlength="10" name="AtualizarGrupoActionForm" property="descricaoAbreviada" size="10" />
	             	</td>
	           </tr>
           
           		<tr> 
	             	<td><strong>Indicador de Uso:<font color="#ff0000">*</font></strong></td>
	             	<td>
						<strong> 
							<html:radio name="AtualizarGrupoActionForm" property="indicadorUso" value="1" />
						<strong>Ativo 
							<html:radio name="AtualizarGrupoActionForm" property="indicadorUso" value="2" />
						Inativo</strong> </strong>
	             	</td>
           		</tr>
                             
	           	<tr> 
	             	<td>&nbsp;</td>
	             	<td><strong><font color="#ff0000">*</font></strong>Campos obrigatórios</td>
	           	</tr>

			  	<tr>
	            	<td colspan="2"><div align="right">
	            	<jsp:include page="/jsp/util/wizard/navegacao_botoes_wizard_valida_avancar.jsp?numeroPagina=1"/></div></td>
	          	</tr>
       		</table>
    	</td>
  	</tr>
</table>

<%@ include file="/jsp/util/rodape.jsp"%>

</body>
</html:form>
</html:html>