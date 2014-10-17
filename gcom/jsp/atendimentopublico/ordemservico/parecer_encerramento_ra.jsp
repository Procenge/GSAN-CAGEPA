<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ page import="gcom.util.Util" %>
<%@ page import="gcom.util.ConstantesSistema" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
 <%@ include file="/jsp/util/titulo.jsp"%>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

 <html:javascript staticJavascript="false"  formName="EncerrarOrdemServicoActionForm" />

 <script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
 <script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
 <script>

	function validaForm() {
	  	var form = document.forms[0];
   			
	  	submeterFormPadrao(form);
	}
	
	function validarSubmitAutomatico(form) {
   		if (form.submitAutomatico3.value == 'ok') {
   			submeterFormPadrao(form);
      	}
   }
	
 </script>
</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');">
<div id="formDiv">
<html:form 	action="/encerrarOrdemServicoAction?confirmado=ok&valorEncerramentoRA=ok" enctype="multipart/form-data"
			method="post" focus="txParecerEncerramento">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

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
			<td width="655" valign="top" class="centercoltext"><!--Início Tabela Reference a Páginação da Tela de Processo-->
			<table>
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key='caminho.imagens'/>parahead_left.gif" /></td>
					<td class="parabg">Parecer do Encerramento do RA</td>
					<td width="11" valign="top"><img border="0"
						src="<bean:message key='caminho.imagens'/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td><strong>Número do RA: <font color="#FF0000"></font></strong></td>
					<td align="right">
					<div align="left"><strong><bean:write
						property="numeroRA" name="EncerrarOrdemServicoActionForm"/></strong></div>
					</td>
				</tr>
				<tr> 	
	           		<td class="style3">
	           			<strong>Parecer do Encerramento do RA:<font color="#FF0000"></font></strong>
	           		</td>
               			<td colspan="3">
               				<span class="style2">
		                       <strong>
									<html:textarea property="txParecerEncerramento" cols="50" rows="5" onkeyup="javascript:limitTextArea(this, 400, document.getElementById('utilizadoParecerEncerramento'), document.getElementById('limiteParecerEncerramento'));"/>	
									<strong><span id="utilizadoParecerEncerramento">0</span>/<span id="limiteParecerEncerramento">400</span></strong>		                                   						
								</strong>
							</span>
						</td>
                </tr>				
				<tr>
					<td><strong> <font color="#FF0000">
					<input type="button" name="Button" class="bottonRightCol"
						value="Cancelar" tabindex="6"
						onClick="javascript:window.location.href='/gsan/exibirEncerrarOrdemServicoAction.do'"
						style="width: 80px" /> </font></strong></td>
					<td align="right"><input type="button" onclick="javascript:validaForm();" name="botaoInserir"
						class="bottonRightCol" value="Confirmar"></td>
						
				<!-- Utilizado pelo setor de Mobilidade para encerramento automático de OS através de dispositivo móvel -->
			   <input type="hidden" name="submitAutomatico3" value="${requestScope.submitAutomatico3}" />
			   <script language="JavaScript">
			   		validarSubmitAutomatico(document.forms[0]);
			   </script>
				</tr>
			</table>
			<p>&nbsp;</p>
		</tr>
		<tr>
			<td colspan="3"></td>
		</tr>
	</table>
	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</div>
</body>

<%@ include file="/jsp/util/telaespera.jsp"%>

</html:html>