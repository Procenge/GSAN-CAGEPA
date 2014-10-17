<%@page import="java.util.Date"%>
<%@page import="gcom.util.Util"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ page import="gcom.util.ConstantesSistema"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
	
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>	
<script language="JavaScript">
function validaForm(){	
	var form = document.forms[0]; 
	form.submit();
}
</script>

<body leftmargin="5" topmargin="5">
<html:form action="/exibirManterAtualizacaoCadastralColetorDadosAction.do"
	name="FiltrarAtualizacaoCadastralColetorDadosActionForm"
	type="gcom.gui.cadastro.imovel.FiltrarAtualizacaoCadastralColetorDadosActionForm" method="post"
	onsubmit="">
	    <%@ include file="/jsp/util/cabecalho.jsp"%>
		<%@ include file="/jsp/util/menu.jsp"%>
		<table width="770" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="130" valign="top" class="leftcoltext">
			<div align="center">
			<%@ include file="/jsp/util/informacoes_usuario.jsp"%>

			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>

			<%@ include file="/jsp/util/mensagens.jsp"%>

			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			</div>
			</td>
			<td width="625" valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>

			<table>
				<tr>
					<td></td>

				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">Manter Atualização Cadastral via Coletor de Dados</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>

				</tr>
			</table>
			<p>&nbsp;</p>
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td colspan="6" height="23">
					<font color="#000000" style="font-size:10px" face="Verdana, Arial, Helvetica, sans-serif">
						<strong>Registros Encontrados:</strong>
					</font>
				</td>
			</tr>
		</table>
			
	  	<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td colspan="5" bgcolor="#000000" height="2"></td>
			</tr>

			<tr bordercolor="#000000">
							
							<td width="12%" bordercolor="#000000" bgcolor="#90c7fc"
								align="center">
							<div align="center"><strong>Matrícula</strong></div>
							</td>
							<td width="22%" bordercolor="#90c7fc" bgcolor="#90c7fc"
								align="center">
							<div align="center"><strong>Inscrição</strong></div>
							</td>
							<td width="12%" bordercolor="#90c7fc" bgcolor="#90c7fc"
								align="center">
							<div align="center"><strong>Referência</strong></div>
							</td>
							<td width="34%" bordercolor="#000000" bgcolor="#90c7fc"
								align="center">
							<div align="center"><strong>Leiturista</strong></div>
							</td>
							<td width="12%" bordercolor="#90c7fc" bgcolor="#90c7fc"
								align="center">
							<div align="center"><strong>Data/Hora</strong></div>
							</td>
			</tr>
			<tr>
				<td colspan="5">
					<table width="100%" bgcolor="#99CCFF">
						<%--Esquema de paginação--%>
						<pg:pager isOffset="true" index="half-full" maxIndexPages="10" export="currentPageNumber=pageNumber;pageOffset" maxPageItems="10" items="${sessionScope.totalRegistros}">
							<pg:param name="pg" />
							<pg:param name="q" />
								<logic:present name="colecaoMovimentoRoteiroEmpresa">
									<%int cont = 0;%>
									<logic:iterate name="colecaoMovimentoRoteiroEmpresa" id="item">
										<pg:item>
											<%cont = cont + 1;
											  if (cont % 2 == 0) {%>
											<tr bgcolor="#cbe5fe">
											<%} else { %>
											<tr bgcolor="#FFFFFF">
											<%}
											  Object[] arrayItem = (Object[])item;
											  String id = arrayItem[0].toString();
		  										 String referencia = Util.formatarAnoMesParaMesAno(arrayItem[3].toString()).toString() ;
		  										String matricula = arrayItem[1].toString();
		 										 String inscricao = arrayItem[2].toString();
		 										 String leiturista = arrayItem[4].toString();
		 										 String data = Util.formatarDataComHora((Date) arrayItem[5]);
												 
											
											%>
													<td width="12%" align="center">
														<div align="center">
															<font color="#000000">
															<%=matricula%></font></div></td>
													<td width="22%" align="center">
														<div align="center">
															<font color="#000000"> <%=inscricao%> </font></div></td>
													<td width="12%" align="center">
														<a href="exibirConsultarAtualizacaoCadastralColetorDadosAction.do?idRegistro=<%=id%>">
															<font color="#000000"> <%=referencia%> </font> 
														</a></td>
													<td width="34%" align="center">
														<div align="center">
															<font color="#000000"> <%=leiturista%> </font></div></td>
													<td width="12%" align="center">
														<div align="center">
															<font color="#000000"> <%=data%> </font></div></td>
											</tr>
										</pg:item>
									</logic:iterate>
								</logic:present> 
						</table>
					<table width="100%" border="0">
						<tr>
							<td valign="top">
								<input  name="button"
										type="button" 
										class="bottonRightCol" 
										value="Voltar Filtro"
										onclick="window.location.href='/gsan/exibirFiltrarAtualizacaoCadastralColetorDadosAction.do'"
										align="left" 
										style="width: 80px;"></td>
							<td valign="top">
								<div align="right"></div>
							</td>
							<td align="right" valign="top"><a
								href="javascript:toggleBox('demodiv',1);"> <img align="right"
								border="0" src="<bean:message key='caminho.imagens'/>print.gif"
								title="Imprimir Imóveis" /> </a></td>
						</tr>
					</table>

					<table width="100%" border="0">
						<tr>
							<td>
							<div align="center"><strong>
								<%@ include file="/jsp/util/indice_pager_novo.jsp"%></strong></div>
							</td>
							</pg:pager>
							<%-- Fim do esquema de paginação --%>
						</tr>
					</table>
					</td>
				</tr>
			</table>
			<jsp:include
		page="/jsp/relatorio/escolher_tipo_relatorio.jsp?relatorio=gerarRelatorioAtualizacaoCadastralColetorDadosAction.do" />
		</td>
		</tr>
		<%@ include file="/jsp/util/rodape.jsp"%>
		</table>
		</html:form>
</body>
</html>
