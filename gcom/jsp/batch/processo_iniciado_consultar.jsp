<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
<html:html>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript">

function facilitador(objeto){

	if (objeto.id == "0" || objeto.id == undefined){
		objeto.id = "1";
		marcarTodos();
	}
	else{
		objeto.id = "0";
		desmarcarTodos();
	}
}

function mostraHint(e, hint)
{
   var div = document.getElementById('divHint');
   var html = hint;
   div.style.left= e.clientX + 15;
   div.style.top = e.clientY;
   div.style.display = 'inline';
   div.style.visibility = 'visible';
   div.innerHTML = html;
}

function escondeHint()
{
   var div = document.getElementById('divHint');
   div.style.display = 'none';
   div.style.visibility = 'hidden';
}

</script>
</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('${requestScope.nomeCampo}');">
<html:form action="/removerProcessoIniciadoAction"
	name="ManutencaoRegistroActionForm"
	type="gcom.gui.ManutencaoRegistroActionForm" method="post">

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

			<td valign="top" class="centercoltext">

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
					<td class="parabg">Consultar Processos Iniciados</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>

				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td><strong>M&ecirc;s/Ano de Refer&ecirc;ncia:
					${requestScope.mesAnoReferencia}</strong></td>
					<td>
					<div align="left"><strong> Data: <bean:write name="dataCorrente"
						formatKey="date.format" /> - Hora: <bean:write
						name="dataCorrente" formatKey="hour.format" /> </strong></div>
					</td>

				</tr>
			</table>

			<p>&nbsp;</p>

			<table width="100%" align="center" cellpadding="0" cellspacing="0">


				<tr bordercolor="#90c7fc">
					<td colspan="5"><font color="#000000" style="font-size:10px"
						face="Verdana, Arial, Helvetica, sans-serif"><strong>Processos
					Iniciados</strong></font></td>
				</tr>
				<tr>
					<td colspan="5">
					<table width="100%" align="center" bgcolor="#90c7fc">
						<tr bordercolor="#000000">							
							<td width="35%" bgcolor="#90c7fc">
							<div align="left"><strong>Processo</strong></div>
							</td>							
							<td width="12%" bgcolor="#90c7fc">
							<div align="center"><strong>Data/Hora</strong></div>
							</td>
							<td width="13%" bgcolor="#90c7fc">
							<div align="left">
							<p><strong>Usu&aacute;rio</strong></p>
							</div>
							</td>
							<td width="26%" bgcolor="#90c7fc">
							<div align="left"><strong>Situa&ccedil;&atilde;o</strong></div>
							</td>
							<% if(request.getAttribute("exibirIpProcesso").equals("1")) { %>
								<td bgcolor="#90c7fc">
								<div align="center">
								<p><strong>IP</strong></p>
								</div>
								</td>
							<% } %>
						</tr>
						<%int cont = 0;%>
						<pg:pager isOffset="true" index="half-full" maxIndexPages="10"
							export="currentPageNumber=pageNumber;pageOffset"
							maxPageItems="10" items="${sessionScope.totalRegistros}">
							<pg:param name="pg" />
							<pg:param name="q" />
							
							<logic:iterate name="colecaoProcessosIniciados"
								id="processoIniciado">
								
								<pg:item>
									
									<%cont = cont + 1;
								if (cont % 2 == 0) {%>
									<tr bgcolor="#cbe5fe">
										<%} else {

								%>
									<tr bgcolor="#FFFFFF">
										<%}%>										
										<td>
											<div align="left">											
												<div id="divHint" class="jsHint"></div>
													<a 
														href="/gsan/exibirConsultarDadosProcessoIniciadoAction.do?idRegistroAtualizacao=<bean:write name="processoIniciado"
																					property="id"/>" 
														onmouseover="mostraHint(event,'Detalhes do processo: <bean:write name="processoIniciado"
																					property="descricaoDadosComplementaresFormatado" />');" 
														onmouseout="escondeHint();">
														<bean:write name="processoIniciado"	property="processo.descricaoProcesso" /> 
													</a>
																											
											</div>										
										</td>
										<td>										
										<div align="center"><bean:write name="processoIniciado"
											property="dataHoraAgendamento" formatKey="date.format" /></div>
										<div align="center"><bean:write name="processoIniciado"
											property="dataHoraAgendamento" formatKey="hour.format" /></div>
										</td>
										<td>
										<div align="left"><bean:write name="processoIniciado"
											property="usuario.nomeUsuario" /></div>
										</td>
										<td>
										<div align="left"><bean:write name="processoIniciado"
											property="processoSituacao.descricao" /></div>
										</td>
										<% if(request.getAttribute("exibirIpProcesso").equals("1")) { %>
											<td>
											<div align="center">
											<logic:present name="processoIniciado" property="ip">
												<bean:define name="ipLocal" id="ipLocal" type="String" />
												<logic:present name="ipLocal">
													<logic:equal name="processoIniciado" property="ip" value="<%= ipLocal %>">
														<span style="color: red; font-weight: bold;"><bean:write name="processoIniciado" property="ip" /></span>
													</logic:equal>
													<logic:notEqual name="processoIniciado" property="ip" value="<%= ipLocal %>">
														<bean:write name="processoIniciado" property="ip" />
													</logic:notEqual>
												</logic:present>
												<logic:notPresent name="ipLocal"><bean:write name="processoIniciado" property="ip" /></logic:notPresent>
											</logic:present>
											<logic:notPresent name="processoIniciado" property="ip">&nbsp;-&nbsp;</logic:notPresent>
											</div>
											</td>
										<% } %>

									</tr>

								</pg:item>

							</logic:iterate>
					</table>
					</td>
				</tr>
			</table>

			<table width="100%" border="0">

				<tr>
					<td colspan="3" class="style1">
					<input name="voltar" type="button" class="bottonRightCol"
						value="Voltar Filtro"
						onclick="window.location.href='<html:rewrite page="/exibirFiltrarProcessoAction.do?limpar=true"/>'"></td>

					<td width="116" class="style1">
					<div align="right"><%--<input name="iniciar" type="button"
						class="bottonRightCol" value="Iniciar">--%>&nbsp;</div>
					</td>
				</tr>
				<tr>
					<td width="330">&nbsp;</td>
					<td width="157" align="right">&nbsp;</td>
					<td colspan="2" align="right">&nbsp;</td>
				</tr>


			</table>
			<table width="100%" border="0">
				<tr>
					<td align="center"><strong><%@ include
						file="/jsp/util/indice_pager_novo.jsp"%></strong></td>
				</tr>
			</table>




			</td>
		</tr>
		</pg:pager>
	</table>


	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</body>
</html:html>
