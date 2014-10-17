<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page isELIgnored="false" import="gcom.gui.ActionServletException,gcom.util.ControladorException"%>

<html:html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script type="text/javascript">
    function imprimir(){
        novaJanela = window.open('jsp/util/imprimir_popup.jsp?idPagamento=${requestScope.codigoAutenticacaoPagamento}','','maximized=no,minimized=no,copyhistory=no,top=-1000000000,left=1000000000,status=no,toolbar=no,scrollbars=no,titlebar=no,menubar=no,resizable=no,width=10,height=1,directories=no,location=no');
        
        javascript:window.location.href="${requestScope.caminhoConfirmacao}";
    }
    
</script>
</head>

<body leftmargin="5" topmargin="5">
	<table width="100%" border="0" cellspacing="5" cellpadding="0">
 		<tr>
	   		<td width="100%" valign="top" class="centercoltext">
		 	
		 	<table height="100%">
		   		<tr>
			 		<td></td>
		   		</tr>
		 	</table>
		 	
		 	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td width="17">
						<img border="0"	src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
				 	<td width="100%" class="parabg">Confirmação</td>
				 	<td width="3%" class="parabg">
				   		<html:link href="javascript:window.close();">
					 		<img border="0" src="<bean:message key="caminho.imagens"/>close.gif" alt="Fechar" />
				   		</html:link>
				 	</td>
				 	<td width="4%">
				   		<img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" />
				 	</td>
			   	</tr>
			</table>
			
			<table width="100%" border="0">
				<tr>
					<td width="6%" align="left">
				   		<img src="<bean:message key="caminho.imagens"/>alerta.gif" />
				 	</td>
				 	<td>
				   		<span style="font-size:12px;font-weight: bold;">

				   		<%	
				   		
				   			String tipoRelatorio = (String) request.getAttribute("tipoRelatorio");
				   			String idNomeRelatorio = (String)request.getAttribute("idNomeRelatorio");
						%> 
					    ${requestScope.mensagemConfirmacao}<br>

				   		</span>
				 	</td>
			   	</tr>
			   	
			   	<c:if test="${!nomeLink}">
			   	<tr height="50">
				 	<td colspan="3">
						<div align="center">
							<a href="javascript:abrirPopup('${requestScope.caminhoLink}', 400, 800);">
									<strong>${requestScope.nomeLink}</strong>
						 	</a>
						</div> 	
					</td>
			   	</tr>
	   			</c:if>
	   			<tr>
		 			<td colspan="2" align="center">
		 			
		 				<c:if test="${confirmacaoImpressao}">
							<input type="button"
						          	class="bottonRightCol" 
						          	name="confirmar" 
						          	value="Confirmar Imprimir"
								  	onclick="imprimir();" />
						</c:if>
						<c:if test="${!confirmacaoImpressao}">
						
							<c:if test="${empty requestScope.confirmacaoNormal}" var="confirmacaoNormal">
						   		
						   		<c:choose>
						   			<c:when test="${!empty sessionScope.statusWizard.caminhoActionPrincipalWizard}">
		                        		<logic:present name="nomeBotao1">
								     		<input type="button"
								          		class="bottonRightCol" 
								          		name="confirmar" 
								          		value="${requestScope.nomeBotao1}"
										  		onclick="javascript:window.location.href='${sessionScope.statusWizard.caminhoActionPrincipalWizard}.do?action=${requestScope.caminhoConfirmacao}&destino=${param.destino}&confirmado=ok&campoHidden=${requestScope.valorHiddenConfirmacao}'" />
										</logic:present>
										
										<logic:notPresent name="nomeBotao1">	  
								     		<input type="button"
								          		class="bottonRightCol" 
								          		name="confirmar" 
								          		value="Confirmar"
										 	 	onclick="javascript:window.location.href='${sessionScope.statusWizard.caminhoActionPrincipalWizard}.do?action=${requestScope.caminhoConfirmacao}&destino=${param.destino}&confirmado=ok&campoHidden=${requestScope.valorHiddenConfirmacao}'" />
										</logic:notPresent>
		                        	</c:when>
		                        	<c:otherwise>
				                        <logic:present name="nomeBotao1">
								     		<input type="button"
								          		class="bottonRightCol" 
								          		name="confirmar" 
								          		value="${requestScope.nomeBotao1}"
										  		onclick="javascript:window.location.href='${sessionScope.statusWizardModificado.caminhoActionPrincipalWizard}.do?action=${requestScope.caminhoConfirmacao}&destino=${param.destino}&confirmado=ok&campoHidden=${requestScope.valorHiddenConfirmacao}'" />
										</logic:present>
										
										<logic:notPresent name="nomeBotao1">	  
								     		<input type="button"
								          		class="bottonRightCol" 
								          		name="confirmar" 
								          		value="Confirmar"
										 	 	onclick="javascript:window.location.href='${sessionScope.statusWizardModificado.caminhoActionPrincipalWizard}.do?action=${requestScope.caminhoConfirmacao}&destino=${param.destino}&confirmado=ok&campoHidden=${requestScope.valorHiddenConfirmacao}'" />
										</logic:notPresent>
		                        	</c:otherwise>	
						   		</c:choose>
						   		
							</c:if>
							
							<c:if test="${!confirmacaoNormal}">
								<logic:present name="nomeBotao1">
							   		<input type="button"
							          	class="bottonRightCol" 
							          	name="confirmar" 
							          	value="${requestScope.nomeBotao1}"
									  	onclick="javascript:window.location.href='${requestScope.caminhoConfirmacao}?confirmado=ok&tipoRelatorio=<%=tipoRelatorio%>&idNomeRelatorio=<%=idNomeRelatorio%>&campoHidden=${requestScope.valorHiddenConfirmacao}'" />
								</logic:present>		  
								
								<logic:notPresent name="nomeBotao1">
							   		<input type="button"
							          	class="bottonRightCol" 
							          	name="confirmar" 
							          	value="Confirmar"
									  	onclick="javascript:window.location.href='${requestScope.caminhoConfirmacao}?confirmado=ok&tipoRelatorio=<%=tipoRelatorio%>&idNomeRelatorio=<%=idNomeRelatorio%>&campoHidden=${requestScope.valorHiddenConfirmacao}'" />
								</logic:notPresent>		  
							</c:if>
						</c:if>		  
				  		&nbsp;&nbsp;&nbsp;&nbsp;
						
						<logic:present name="cancelamento">
						
							<logic:present name="disableBotao2"> <!-- Caso seja necessário deixar desabilitado por alguma regra de permissão, caso informe. -->
							
								<logic:present name="nomeBotao2">
									<input type="button"
							          	class="bottonRightCol" 
							          	name="cancelar" 
							          	value="${requestScope.nomeBotao2}"
							          	title="${requestScope.titleBotao2}"
							          	disabled="disabled"
									  	onclick="javascript:window.location.href='${requestScope.caminhoConfirmacao}?confirmado=cancelar&idNomeRelatorio=<%=idNomeRelatorio%>&campoHidden=${requestScope.valorHiddenConfirmacao}'" />
								</logic:present>
				 				
				 				<logic:notPresent name="nomeBotao2">
									<input type="button"
							          	class="bottonRightCol" 
							          	name="cancelar" 
							          	value="Cancelar"
							          	disabled="disabled"
								  		onclick="javascript:window.location.href='${requestScope.caminhoConfirmacao}?confirmado=cancelar&idNomeRelatorio=<%=idNomeRelatorio%>&campoHidden=${requestScope.valorHiddenConfirmacao}'" />
					 			</logic:notPresent>
					 			
							</logic:present>
							
							<logic:notPresent name="disableBotao2">
								<logic:present name="nomeBotao2">
									<input type="button"
							          	class="bottonRightCol" 
							          	name="cancelar" 
							          	value="${requestScope.nomeBotao2}"
									  	onclick="javascript:window.location.href='${requestScope.caminhoConfirmacao}?confirmado=cancelar&idNomeRelatorio=<%=idNomeRelatorio%>&campoHidden=${requestScope.valorHiddenConfirmacao}'" />
								</logic:present>
				 				
				 				<logic:notPresent name="nomeBotao2">
									<input type="button"
							          	class="bottonRightCol" 
							          	name="cancelar" 
							          	value="Cancelar"
								  		onclick="javascript:window.location.href='${requestScope.caminhoConfirmacao}?confirmado=cancelar&idNomeRelatorio=<%=idNomeRelatorio%>&campoHidden=${requestScope.valorHiddenConfirmacao}'" />
					 			</logic:notPresent>
								
							</logic:notPresent>
							
						</logic:present>						
						
						<logic:present name="urlBotaoVoltar">
					     	<input type="button" 
					     		class="bottonRightCol"
							  	name="voltar"
							  	value="Voltar" 
							  	onclick="javascript:window.location.href='${requestScope.urlBotaoVoltar}'" />
						</logic:present>
						<logic:notPresent name="urlBotaoVoltar">
							<logic:present name="voltar">		  
						     	<input type="button" 
						     		class="bottonRightCol"
								  	name="voltar"
								  	value="Voltar" 
								  	onclick="javascript:history.back()" />
					   		</logic:present>
							
							<logic:notPresent name="voltar">		  
						     	<input type="button" 
						     		class="bottonRightCol"
								  	name="voltar"
								  	value="Voltar" 
								  	onclick="javascript:history.back()" />
					   		</logic:notPresent>
				   		&nbsp;&nbsp;&nbsp;&nbsp;
				   		</logic:notPresent>
				   		
		 			</td>
	   			</tr>
	   			
	   			<tr>
		 			<td colspan="2">&nbsp;</td>
	   			</tr>
	 		</table>
		 	
		 	<p>&nbsp;</p>
			<p>&nbsp;</p>
   			
   			</td>
  		</tr>
	</table>
	
</body>
</html:html>