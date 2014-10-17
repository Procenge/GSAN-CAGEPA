<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

</head>


<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<script language="JavaScript">

function validarForm(){
	var form = document.forms[0];
	form.submit();
}
	
</script>


<body leftmargin="5" topmargin="5">

<html:form action="/TestarEnvioEmailAction.do"
	name="TestarEnvioEmailActionForm"
	type="gcom.gui.seguranca.sistema.TestarEnvioEmailActionForm"
	enctype="multipart/form-data"
	method="post">

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="4" cellpadding="0">
  		<tr>
    		<td width="149" valign="top" class="leftcoltext">
			<div align="center"><%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%@ include
				file="/jsp/util/informacoes_usuario.jsp"%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%@ include
				file="/jsp/util/mensagens.jsp"%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
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
		          			<img border="0" 
		          				src="<bean:message key="caminho.imagens"/>parahead_left.gif"/>
		          		</td>
		          		<td class="parabg">Testar Envio de E-mail</td>
		          		<td width="11">
		          			<img border="0" 
		          				src="<bean:message key="caminho.imagens"/>parahead_right.gif"/>
		          		</td>
		        	</tr>
		      	</table>
			   	
			   	<table width="100%" border="0">
			        
			        <tr>
							<td colspan="4">
								<div align="left"><strong>Digite as informações para enviar o e-mail:</strong></div>
							</td>
			        </tr>
			        <tr><td><br/></td></tr>
			        <tr>
			        	<td width="20%">
			        		<div align="left"><strong>Servidor SMTP:</strong></div>
			        	</td>
			        	<td width="30%">
			        		<html:text property="servidorSMTP"></html:text>
			        	</td>
			        	<td width="20%">
			        		<div align="left"><strong>Porta:</strong></div>
			        	</td>
			        	<td width="30%">
			        		<html:text property="porta"></html:text>
			        	</td>
			        </tr>
			        <tr>
			        	<td>
			        		<div align="left"><strong>Usuario:</strong></div>
			        	</td>
			        	<td>
			        		<html:text property="usuario"></html:text>
			        	</td>
			        	<td>
			        		<div align="left"><strong>Senha:</strong></div>
			        	</td>
			        	<td>
			        		<html:text property="senha"></html:text>
			        		
			        	</td>
			        </tr>
			        
			    </table>
			    <hr>
			    <table width="100%" border="0">
			    	<tr>
			        	<td width="20%">
			        		<div align="left"><strong>Remetente:</strong></div>
			        	</td>
			        	<td width="30%">
			        		<html:text size="50" maxlength="50" property="remetente"></html:text>
			        	</td>
			        	
			        </tr>
			        <tr>
			        	<td width="20%">
			        		<div align="left"><strong>Destinatario:</strong></div>
			        	</td>
			        	<td width="30%">
			        		<html:text size="50" maxlength="50" property="destinatario"></html:text>
			        		
			        	</td>
			        </tr>
			        <tr><td><br/></td></tr>
			        <tr>
			        	<td >
			        		<div align="left"><strong>Titulo:</strong></div>
			        	</td>
			        	<td >
			        		<html:text size="50" maxlength="50" property="titulo"></html:text>
			        	</td>
			        </tr>
			        <tr>
			        	<td width="20%" valign="top">
			        		<div align="left"><strong>Mensagem:</strong></div>
			        	</td>
			        	<td >
			        		<html:textarea cols="54" rows="10" property="mensagem"></</html:textarea>
			        	</td>
			        </tr>
			        <tr>
			        	<td >
			        		<div align="left"><strong>Anexo:</strong></div>
			        	</td>
			        	<td >
			        		<html:file property="anexo" ></html:file>
			        	</td>
			        </tr>
			        
		       	</table>
		       	<p>&nbsp;</p>
		        <table width="100%" border="0">
<!-- 			        <tr> -->
<!-- 			          	<td width="60" align="left"> -->
<!-- 			            	<strong> -->
<!-- 			            		<a href="/gsan/telaPrincipal.do">Voltar</a> -->
<!-- 			            	</strong> -->
<!-- 			          	</td> -->
			          
<!-- 			          	<td align="right"> -->
<!-- 	  		      			<input name="Button"  -->
<!-- 								type="button" -->
<!-- 								class="bottonRightCol"  -->
<!-- 								value="Exibir" > -->
<!-- 			  	      	</td> -->
<!-- 			        </tr> -->
					<tr>
						<td >
							<input type="button" name="Button"
								class="bottonRightCol" value="Desfazer" tabindex="7"
								onClick="javascript:window.location.href='/gsan/exibirTestarEnvioEmailAction.do?menu=sim'"
								style="width: 80px" />&nbsp; 
							<input type="button" name="Button"
								class="bottonRightCol" value="Cancelar" tabindex="8"
								onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
								style="width: 80px" />
						</td>
						<td align="right">
							
							<gcom:controleAcessoBotao name="Button"
								value="Enviar Email" tabindex="9"
								onclick="javascript:validarForm();"
								url="TestarEnvioEmailAction.do" />
						</td>
					</tr>	
				</table>
			   	
			   	<p>&nbsp;</p>
			    <p>&nbsp;</p>
			    
    		</td>
  		</tr>
	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</body>
</html:html>