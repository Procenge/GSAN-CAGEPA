<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@page import="gcom.cadastro.atendimento.DocumentoEletronico"%>
<%@page import="gcom.gui.GcomAction"%>
<%@ page import="gcom.util.ConstantesSistema" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css"  type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="AtualizarDocumentoEletronicoActionForm" />

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript">

</script>

</head>

<body leftmargin="5" topmargin="5" onload="javascript:setarFoco('${requestScope.nomeCampo}');">

<html:form action="/exibirConsultarDocumentoEletronicoAction" method="post"
	name="ConsultarDocumentoEletronicoActionForm"
	type="gcom.gui.cadastro.documentoeletronico.ConsultarDocumentoEletronicoActionForm"
	onsubmit="return validateConsultarDocumentoEletronicoActionForm(this);">

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
			
			<td width="600" valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>
			
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">Consultar Documento Eletrônico</td>
					<td width="11" valign="top">
						<img border="0" src="imagens/parahead_right.gif" />
					</td>
				</tr>

			</table>

			<!--Fim Tabela Reference a Páginação da Tela de Processo-->
			<p>&nbsp;</p>

			<table width="100%" border="0">

				<tr> 
					<td width="30%">
						<strong>Matr&iacute;cula do Im&oacute;vel:</strong>
					</td>
					<td>
						<html:text property="idImovel" maxlength="9" size="9" style="background-color:#EFEFEF; border:0; color: #000000" />

		                <html:text property="inscricaoImovel" size="30"
					                  maxlength="30" readonly="true"
					                  style="background-color:#EFEFEF; border:0; color: #000000" />

					</td>
				</tr>
			       
	       
				<tr>
					<td><strong>Cliente:</strong></td>
					<td>
						<html:text property="idCliente" size="9" maxlength="9" style="background-color:#EFEFEF; border:0; color: #000000" />
							<html:text property="nomeCliente" size="30" maxlength="30"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />

					</td>
				</tr>			

				<tr> 
					<td><strong>Tipo de Documento:</strong></td>
					<td>
							<html:text property="descricaoTipoDocumento" size="25" maxlength="25"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
					</td>
				</tr>
				
				<tr> 
					<td><strong>Nome do Arquivo:</strong></td>
					<td>
								<html:link
									href="/gsan/exibirConsultarDocumentoEletronicoAction.do?visualizarImagem=S"	>
									<bean:write name="ConsultarDocumentoEletronicoActionForm" property="nomeArquivo" />
								</html:link>					
					</td>
				</tr>

			</table>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">
					<hr>
					</td>
				</tr>

				<tr>
					<td colspan="1">
					<font color="#FF0000">
						<logic:present name="manter" scope="session">
						
							<input type="button" 
								name="ButtonReset" 
								class="bottonRightCol"
								value="Voltar"
								onClick="javascript:window.location.href='/gsan/exibirManterDocumentoEletronicoAction.do'">
							
						</logic:present>
						
						<logic:notPresent name="manter" scope="session">
							<input type="button" 
								name="ButtonReset" 
								class="bottonRightCol"
								value="Voltar"
								onClick="javascript:window.location.href='/gsan/exibirFiltrarDocumentoEletronicoAction.do'">
						</logic:notPresent> 
						
						<input type="button" 
							name="ButtonCancelar" 
							class="bottonRightCol"
							value="Cancelar"
							onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
					</font>
					</td>
				</tr>
			</table>
			<p>&nbsp;</p>
		</tr>
	</table>
<%@ include file="/jsp/util/rodape.jsp"%>	
</html:form>
</body>
</html:html>

