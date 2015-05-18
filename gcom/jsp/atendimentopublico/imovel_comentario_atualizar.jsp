<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="gcom.atendimentopublico.bean.ImovelComentarioHelper"%>
<%@ page import="gcom.util.ConstantesSistema"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js" ></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript formName="InformarImovelComentarioActionForm"/>

<script language="JavaScript">
function validarFormulario(){
	var form = document.forms[0];
	
	if (validateInformarImovelComentarioActionForm(form)){
		
	if (form.comentario.value == ''){
			
			alert("Informe Comentário");
			return;
		}else{
			
			form.action = '/gsan/atualizarImovelComentarioAction.do';
	  		form.submit();
		}
	}
}

function desfazer(){
	var form = document.forms[0];
	
	form.action = '/gsan/exibirAtualizarImovelComentarioAction.do?desfazer=1';
  	form.submit();
}
	
</script>

</head>

<body leftmargin="5" topmargin="5">


<html:form action="/exibirAtualizarImovelComentarioAction" 
	name="InformarImovelComentarioActionForm"
	type="gcom.gui.atendimentopublico.InformarImovelComentarioActionForm" 
	method="post">


<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

<table width="770" border="0" cellspacing="4" cellpadding="0">
	<tr>
		<td width="149" valign="top" class="leftcoltext">
		    <div align="center">
		
				<%@ include file="/jsp/util/informacoes_usuario.jsp"%>
		
				<%@ include file="/jsp/util/mensagens.jsp"%>
		    </div>
	    </td>
	
		<td width="600" valign="top" class="centercoltext">
			<table height="100%">
		        <tr>
		          <td></td>
		        </tr>
		    </table>
	
		    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		      <tr>
		        <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
		        <td class="parabg">Comentário do Imóvel</td>
		        <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
		      </tr>
		    </table>
	      	<p>&nbsp;</p>
	
		    <table width="100%" border="0">
			     <tr>
			    	 <td colspan="2">Nesta tela, o usuário pode atualizar um comentário do imóvel:</td>
			     </tr>
			
			  	 <tr>
			     	<td colspan="2">
						<table width="100%" align="center" bgcolor="#90c7fc" border="0">
							<tr bgcolor="#cbe5fe">
								<td width="100%" align="center">
									<table width="100%" border="0">
								 		<tr>
											<td><strong>Im&oacute;vel:</strong></td>
																
											<td>
						           		
							  					<html:text maxlength="9" 
													property="idImovel" 
													size="9"
													disabled="true"
													/>	
												
												<html:text property="inscricaoImovel" 
													size="30"
													maxlength="30" 
													readonly="true"
													style="background-color:#EFEFEF; border:0; color: #000000" />
											</td>
										</tr>
										<tr>
											<td>
												<strong>Comentário:</strong>
												<font color="#FF0000">*</font>
											</td>
						
											<td>
												<html:textarea property="comentario" style="width: 480" rows="10" onkeyup=" validarTamanhoMaximoTextArea(this,1000);"/>
											</td>
										</tr>
										<tr>
									        <td><strong>Indicador de uso:</strong><font color="#FF0000">*</font></td>
									        <td>
												<html:radio property="indicadorUso" value="<%=""+ConstantesSistema.SIM%>"/><strong>Ativo
												<html:radio property="indicadorUso" value="<%=""+ConstantesSistema.SIM%>"/>Inativo</strong>
											</td>
									      </tr>
									   
									   <tr>
				                	</table>
								</td>
					    	</tr>
						</table>		
				 	</td>
				 </tr>             
			     <tr>
			    	<td colspan="2" height="10"></td>
			     </tr>
			     <tr>
					 <td colspan="2">
						<table width="100%">
						    <tr>
								<td colspan="2"><div align="left"><strong><font color="#FF0000">*</font></strong>Campos obrigat&oacute;rios</div></td>
							</tr>
							<tr>
			    	      		<td colspan="2" height="10"></td>
						    </tr>
							<tr>
								<td>				
									<input name="Submit22"
									 class="bottonRightCol" value="Desfazer" type="button"
									 onclick="javascript:desfazer();">	
									<input name="Submit23" class="bottonRightCol" value="Cancelar"
											type="button"
											onclick="window.location.href='/gsan/telaPrincipal.do'"> 
								</td>
								<td align="right">	
										<input name="Submit24"
											 class="bottonRightCol" value="Atualizar Comentário" type="button"
											 onclick="javascript:validarFormulario();">	
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

