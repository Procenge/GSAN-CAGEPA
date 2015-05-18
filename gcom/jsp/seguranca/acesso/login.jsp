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
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript">
	function lembrarSenha() {
	  var form = document.forms[0];
	  if(form.login.value == null || trim(form.login.value) == ""){
	    alert('Informe o login');
	    form.login.focus();
	  }else{
	    abrirPopup('exibirLembrarSenhaAction.do?login='+form.login.value, 270, 580);
	  }  
	}
</script>

</head>


<body leftmargin="5" topmargin="5" onLoad="document.EfetuarLoginActionForm.login.focus();">
<%@ include file="/jsp/util/cabecalho.jsp" %>

<table width="770" border="0" cellspacing="4" cellpadding="0">
  <tr>
    <td valign="top" class="leftcoltext">
      <div align="center">
        <html:form action="/efetuarLoginAction" 
        		   name="EfetuarLoginActionForm" 
        		   type="gcom.gui.seguranca.acesso.EfetuarLoginActionForm" 
        		   method="post">
        	
	        <table cellpadding="3" border="0" class="tableinlayer" style="position: relative; top: 5px; left: 0px;">
	          <tr>
	            <td>
		          <table width="100" border="0" align="center" >
                    <tr>
                      <td width="94">Login</td>
                    </tr>
                    <tr>
                      <td>
                        <html:text maxlength="11" property="login" size="14"  style="text-transform: none;"/>
                      </td>
                    </tr>
                    <tr>
                      <td>Senha</td>
                    </tr>
                    <tr>
                      <td>
                        <html:password maxlength="12" property="senha" size="14" redisplay="false"  style="text-transform: none;"/>
                      </td>
                    </tr>
                    <tr>
                      <td align="right">
                        <html:submit styleClass="buttonLeftCol" value="Login" property="buttonLogin"/>
                      </td>
                    </tr>
                  </table>
		        </td>
		      </tr>
		    </table>
	     </html:form>
	    
       <%@ include file="/jsp/util/mensagens.jsp" %>
       
       <p align="left">&nbsp;</p>
       
	   <p align="center">
         <a href="/gsan/exibirLembrarSenhaAction.do">Esqueceu a senha ?</a>
	   </p>	  

     </div>
   </td>
   <td width="600" class="centercoltext" valign="top">
     
     <table height="100%" border="0">
       <tr>
         <td></td>
       </tr>
     </table>
     <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
       <tr>
         <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
         <td class="parabg">GSAN - Sistema Integrado de Gest&atilde;o de Servi&ccedil;os de Saneamento</td>
         <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
       </tr>
      </table>
      

      <table width="95%" border ="0">
	      <tr>
      
    		 <td align="center">
    			<br><br><br> 
    		 	<!-- <img src="<bean:message key="caminho.imagens"/>logo_aguas_f_azul.gif" border="0" /> -->
    		 	<img src="<bean:message key="caminho.imagens"/>${applicationScope.imagemPrincipal}" border="0" >
      			<br><br><br>
      			<a href="http://www.procenge.com.br/" target="_blank">
      				<!-- <img src="<bean:message key="caminho.imagens"/>procenge_inicial.gif" border="0" /> -->
      				<img src="<bean:message key="caminho.imagens"/>${applicationScope.imagemSecundaria}" border="0" >
      			</a>
      
      			<br>     
      			<span style="font-size: 11px; font-family: verdana">			
					<br><p align="center"><a href="http://www.procenge.com.br/" target="_blank">Tecnologia para Decisão</a></p>
				<br>				 
				</span>
      	
      		
      		  </td>
      		</tr>
      </table>
      

      
	  </td>
  </tr>
</table>
 <%@ include file="/jsp/util/rodape.jsp" %>
</body>
</html:html>
