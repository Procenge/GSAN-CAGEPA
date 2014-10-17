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

function iniciarPesquisar(){
	redirecionarSubmit("/gsan/exibirUnificarCadastroClientesRedundantesAction.do?pesquisa=101");
}
function iniciarCorrecao(){
	redirecionarSubmit("/gsan/exibirUnificarCadastroClientesRedundantesAction.do?correcao=101");
}
</script>

</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');" >
<html:form action="/exibirUnificarCadastroClientesRedundantesAction" method="post" name="UnificarCadastroClientesRedundantesActionForm"
	type="gcom.gui.cadastro.cliente.UnificarCadastroClientesRedundantesActionForm">


<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

<table width="770" border="0" cellspacing="4" cellpadding="0">
	<tr>
		<td width="149" valign="top" class="leftcoltext">
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

		<td width="630" valign="top" class="centercoltext">
			<table height="100%">
				<tr><td></td></tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
					<td class="parabg">Unificação de Cadastro de Clientes Redundantes</td>
					<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
				</tr>
			</table>
			<p>&nbsp;</p>

<!-- ============================================================================================================================== -->

			<table width="100%" border="0" >
		    	<tr>
		        	<td width="100%">
						<table width="100%" border="0" >
				            <tr>
					    		<td colspan="4">
									<table width="100%" align="center" bgcolor="#99CCFF">
		   				      			<tr>
		    								<td align="center"><strong>Levantamento de Dados de Clientes Redundantes</strong></td>
			    			  			</tr>
						      			<tr bgcolor="#cbe5fe">
							     			<td width="100%" align="center">
					                			<table width="100%" border="0">
					                				<tr>
					                					<td>
															<strong>Buscar números de CPF repetidos na base</strong>
					                					</td>
					                					<td>
					                						<input type="button" class="bottonRightCol" value="Iniciar Busca " tabindex="9"  
															onclick="iniciarPesquisar();">
					                					</td>
					                				</tr>
					                				<tr>
						                				<td>
						                					<strong>Número de CPFs e CNPJs com redundancias na base:</strong>
						                				</td>
						                				<td>
						                					<html:text property="numeroRegistrosComRedundacia" readonly="true"/><br/>
						                					
						                				</td>
					                				</tr>
					                				<tr>
					                					<td>
					                					</td>
					                					<td>
					                					<logic:notPresent name="UnificarCadastroClientesRedundantesActionForm" property="numeroRegistrosComRedundacia">
						                					<input type="button" class="bottonRightCol" value="Iniciar Correção" tabindex="9" readonly="readonly">
					                					</logic:notPresent>
					                					<logic:present name="UnificarCadastroClientesRedundantesActionForm" property="numeroRegistrosComRedundacia">
						                					<input type="button" class="bottonRightCol" value="Iniciar Correção" tabindex="9" 
															onclick="iniciarCorrecao();">
					                					</logic:present>
					                					</td>
					                				</tr>
												</table>
											</td>
										</tr>
									</table> 
								</td>
							</tr>						
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="button" name="Button" class="bottonRightCol" value="Desfazer"
							onClick="javascript:window.location.href='/gsan/exibirUnificarCadastroClientesRedundantesAction.do?menu=sim'">
						<input type="button" name="Button" class="bottonRightCol" value="Cancelar"
							onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"/>
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