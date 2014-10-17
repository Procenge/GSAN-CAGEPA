<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>
<%@ page
	import="gcom.util.Pagina,gcom.util.ConstantesSistema,java.util.Collection"%>
<%@ page import="gcom.cobranca.bean.ImovelCobrancaSituacaoAdministrativaRemuneracaoHelper"%>
<%@ page import="gcom.util.Util"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<html:javascript staticJavascript="false" formName="ConsultarDadosCobrancaAdministrativaActionForm" />

</head>

<body leftmargin="5" topmargin="5">

<html:form action="/exibirDocumentosRemuneradosAction"  method="post">

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
			<td valign="top" bgcolor="#003399" class="centercoltext">
				<table height="100%">

					<tr>
						<td></td>
					</tr>
				</table>
				<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0">
					<tr>
						<td width="11"><img border="0"
							src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
						<td class="parabg">Dados Cobrança Administrativa do Imovel</td>

						<td width="11"><img border="0"
							src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
					</tr>
					<tr>
						<td height="5" colspan="3"></td>
					</tr>
				</table>
				<table width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td colspan="5" height="23"><font color="#000000"
							style="font-size: 10px"
							face="Verdana, Arial, Helvetica, sans-serif"><strong>Dados
									do Imóvel:</strong></font></td>
						<td align="right"></td>
					</tr>
				</table>
				<table width="100%" border="0">
					<tr>
						<td width="20%"><strong>Matrícula:</strong></td>
						<td><html:text property="matricula" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"></html:text></td>
					</tr>
					<tr>
						<td width="20%"><strong>Inscrição:</strong></td>
						<td><html:text property="inscricaoImovel" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/></td>
					</tr>
					<tr>
						<td width="20%"><strong>Nome:</strong></td>
						<td><html:text property="nomeCliente" size="55" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/></td>
					</tr>
					<tr>
						<td width="20%"><strong>Endereço:</strong></td>
						<td><html:text property="endereco" size="80" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/></td>
					</tr>
				</table>
				<table width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td colspan="5" height="23"><font color="#000000"
							style="font-size: 10px"
							face="Verdana, Arial, Helvetica, sans-serif"><strong>Documentos Remunerados:</strong></font></td>
						<td align="right"></td>
					</tr>
				</table>
					<table width="100%" cellpadding="0" cellspacing="0" >
						<tr>
							<!--<td colspan="4" bgcolor="#3399FF"> -->
							<td colspan="6" bgcolor="#000000" height="2"></td>
						</tr>
						<tr bordercolor="#000000">
							<td width="10%" bgcolor="#90c7fc" height="20">
								<div align="center">
									<strong>Tipo</strong>
								</div>
							</td>
							<td width="30%" align="center" bgcolor="#90c7fc"><strong>Indentificação</strong></td>
							<td width="16%" align="center" bgcolor="#90c7fc"><strong>Tipo</strong></td>
							<td width="12%" align="center" bgcolor="#90c7fc"><strong>Percentual</strong></td>
							<td width="20%" align="center" bgcolor="#90c7fc"><strong>Valor</strong></td>
							<td width="12%" align="center" bgcolor="#90c7fc"><strong>Total</strong></td>
						</tr>

						<tr>
							<td colspan="6">
								
								<table width="100%" cellpadding="0" cellspacing="0">
									<logic:present name="listaRemuneracoes">
										<%int cont = 1;%>
										<logic:iterate name="listaRemuneracoes" id="DocumentoRemunerado" type="ImovelCobrancaSituacaoAdministrativaRemuneracaoHelper">
											<%cont = cont + 1;
											if (cont % 2 == 0) {%>
											<tr bgcolor="#FFFFFF">
											<%} else { %>
											<tr bgcolor="#cbe5fe">
											<%}%>
												<td style="padding: 0px;" width="10%">
													<table width="100%"  border="0" cellpadding="0" cellspacing="0">
														<%if (cont % 2 == 0) {%>
														<tr bgcolor="#FFFFFF" style="padding: 0px;">
														<%} else { %>
														<tr bgcolor="#cbe5fe" style="padding: 0px;">
														<%}%>
															<td align="center" style=" padding: 0px; border-right: 2px solid #99CCFF;"><bean:write name="DocumentoRemunerado" property="tipo" /></td>
														</tr>
													</table>
												</td>
												<td style="padding: 0px;" width="30%">
													<table width="100%" border="0" cellpadding="0" cellspacing="0">
														<%if (cont % 2 == 0) {%>
														<tr bgcolor="#FFFFFF" style="padding: 0px;">
														<%} else { %>
														<tr bgcolor="#cbe5fe" style="padding: 0px;">
														<%}%>
															<td align="center" style=" padding: 0px; border-right: 2px solid #99CCFF;"><bean:write name="DocumentoRemunerado" property="identificacao" /></td>
														</tr>
													</table>
												</td>
												<td>
													<table width="100%"  cellspacing="0" border="0">
														<% if (DocumentoRemunerado.getValorRemuneracao() != null) {%>
															<%if (cont % 2 == 0) {%>
															<tr bgcolor="#FFFFFF" style="padding: 0px;">
															<%} else { %>
															<tr bgcolor="#cbe5fe" style="padding: 0px;">
															<%}%>
																<td width="16%" align="center" style=" padding: 0px; border-right: 2px solid #99CCFF;"><bean:write name="DocumentoRemunerado" property="descricao" /></td>
																<td width="12%" align="center" style=" padding: 0px; border-right: 2px solid #99CCFF;"><bean:write name="DocumentoRemunerado" property="percentualRemuneracao" /></td>
																<td width="20%" align="center" style=" padding: 0px; border-right: 2px solid #99CCFF;">
																		<%="R$ " + Util.formatarMoedaReal(DocumentoRemunerado.getValorRemuneracao())%>
																</td>
															</tr>
														<%}%>
														<% if (DocumentoRemunerado.getValorReincidencia() != null) {%>
															<%if (cont % 2 == 0) {%>
															<tr bgcolor="#FFFFFF">
															<%} else { %>
															<tr bgcolor="#cbe5fe">
															<%}%>
																<td width="16%" align="center" style=" padding: 0px; border-right: 2px solid #99CCFF;"><bean:write name="DocumentoRemunerado" property="descricao" /></td>
																<td width="12%" align="center" style=" padding: 0px; border-right: 2px solid #99CCFF;"><bean:write name="DocumentoRemunerado" property="percentualReincidencia" /></td>
																<td width="20%" align="center" style=" padding: 0px; border-right: 2px solid #99CCFF;">
																	<%="R$ " + Util.formatarMoedaReal(DocumentoRemunerado.getValorReincidencia())%>
																</td>
															</tr>
														<%}%>
														<% if (DocumentoRemunerado.getValorEspecial() != null) {%>
															<%if (cont % 2 == 0) {%>
															<tr bgcolor="#FFFFFF">
															<%} else { %>
															<tr bgcolor="#cbe5fe">
															<%}%>
																<td width="16%" align="center" style=" padding: 0px; border-right: 2px solid #99CCFF;"><bean:write name="DocumentoRemunerado" property="descricao" /></td>
																<td width="12%" align="center" style=" padding: 0px; border-right: 2px solid #99CCFF;"><bean:write name="DocumentoRemunerado" property="percentualEspecial" /></td>
																<td width="20%" align="center" style=" padding: 0px; border-right: 2px solid #99CCFF;">
																	<%="R$ " + Util.formatarMoedaReal(DocumentoRemunerado.getValorEspecial())%>
																</td>
															</tr>
														<%}%>
														<% if (DocumentoRemunerado.getValorParcelamento() != null) {%>
															<%if (cont % 2 == 0) {%>
															<tr bgcolor="#FFFFFF">
															<%} else { %>
															<tr bgcolor="#cbe5fe">
															<%}%>
																<td width="16%" align="center" style=" padding: 0px; border-right: 2px solid #99CCFF;"><bean:write name="DocumentoRemunerado" property="descricao" /></td>
																<td width="12%" align="center" style=" padding: 0px; border-right: 2px solid #99CCFF;"><bean:write name="DocumentoRemunerado" property="percentualParcelamento" /></td>
																<td width="20%" align="center" style=" padding: 0px; border-right: 2px solid #99CCFF;">
																	<%="R$ " + Util.formatarMoedaReal(DocumentoRemunerado.getValorParcelamento())%>
																</td>
															</tr>
														<%}%>
													</table>
												</td>
												<td width="12%" align="center">
													<%="R$ " + Util.formatarMoedaReal(DocumentoRemunerado.getTotal())%>
												</td>
											</tr>
										</logic:iterate>
									</logic:present>
								</table>
								
								<br>
								<br>
								<br>
							
								<table width="100%" border="0">
									<tr>
										<td valign="top"><input name="button" type="button"
											class="bottonRightCol" value="Voltar"
											onclick="window.location.href='<html:rewrite page="/exibirConsultarDadosCobrancaAdminstrativaPorImovelAction.do"/>'"
											align="left" style="width: 80px;"></td>
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
