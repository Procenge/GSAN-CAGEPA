<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false" formName="EfetuarInstalacaoHidrometroActionForm" />

<script language="JavaScript"><!--
  	function validaForm(){
    	var form = document.forms[0];
		submeterFormPadrao(form);  	  	
  	}
  	
  	
	 
	function limparForm() {
	
		var form = document.forms[0];
		window.location.href='/gsan/exibirEfetuarInstalacaoHidrometroLoteAction.do?menu=sim'
		
		
		
	 }
--></script>

</head>

<body 
	leftmargin="5" 
	topmargin="5">

<html:form action="/efetuarInstalacaoHidrometroLoteAction.do"
	name="EfetuarInstalacaoHidrometroActionForm"
	type="gcom.gui.atendimentopublico.hidrometro.EfetuarInstalacaoHidrometroActionForm"
	enctype="multipart/form-data" method="post">

	<html:hidden property="veioEncerrarOS" />
	<html:hidden property="qtdeMaxParcelas" />

	<logic:notPresent name="semMenu">

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
				<td width="615" valign="top" class="centercoltext">
				<table height="100%">
					<tr>
						<td></td>
					</tr>
				</table>

				</logic:notPresent> <logic:present name="semMenu">
					<table width="550" border="0" cellspacing="5" cellpadding="0">
						<tr>
							<td width="615" valign="top" class="centercoltext">
							<table height="100%">
								<tr>
									<td></td>
								</tr>
							</table>
							</logic:present>

							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td width="11"><img border="0"
										src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
									<td class="parabg">Efetuar Instalação de Hidrômetro em
									Lote</td>
									<td width="11"><img border="0"
										src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
								</tr>
							</table>
							<p>&nbsp;</p>
							<table width="100%" border="0">
								<tr>
									<td height="31">
									<table width="100%" border="0" align="center">
										<tr>
											<td height="10" colspan="2">Para efetuar a instalação
											dos hidrômetros, informe o caminho do arquivo com os dados
											das instalações:</td>
										</tr>
										<tr bgcolor="#cbe5fe">
											<td colspan="2">
												<table width="100%" border="0" bgcolor="#99CCFF">
													<tr bgcolor="#99CCFF"><td height="18" colspan="2"> <div align="center">Dados do Arquivo </div></td></tr>
													<tr bgcolor="#cbe5fe"><td height="10"><strong>Arquivo (.csv):<strong><font
															color="#FF0000">*</font></strong></strong></td>
														<td>
															<table width="100%" border="0">
																<tr><td colspan="3" align="left">
																<html:file property="arquivo" size="40"></html:file></td>
																</tr>
															</table>
														</td>
													</tr>
												</table>
											</td>
										</tr>
											</table>
										</tr>
		
									</td>
</tr><tr>
									<td colspan="2">
									<table width="100%">
										<tr>
											<td width="40%" align="left"><logic:present
												name="caminhoRetornoIntegracaoComercial">
												<INPUT TYPE="button" class="bottonRightCol" value="Voltar"
													onclick="redirecionarSubmit('${caminhoRetornoIntegracaoComercial}')" />
											</logic:present> <input type="button" name="ButtonReset"
												class="bottonRightCol" value="Desfazer"
												onClick="limparForm();"> <input type="button"
												name="ButtonCancelar" class="bottonRightCol"
												value="Cancelar"
												onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
											</td>
											<td align="right"><input name="Button" type="button"
												class="bottonRightCol" value="Criticar Arquivo e Efetuar Instalações"
												onclick="validaForm();"></td>
										</tr>
										
									</table>
									</td>
								</tr>
								<!--</tr></table></td>-->
							</table>
					</table>
					<!-- Fim do Corpo-->

					<logic:notPresent name="semMenu">
						<%@ include file="/jsp/util/rodape.jsp"%>
					</logic:notPresent>
					</html:form>
					</html:html>