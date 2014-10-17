<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>
<%@page import="gcom.cobranca.programacobranca.ProgramaCobranca"%>
<%@page import="gcom.util.ConstantesSistema"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>/validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script>
<!--
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

function remover(objeto){

	if (CheckboxNaoVazio(objeto)){
		if (confirm ("Confirma remoção?")) {
			document.forms[0].action = "/gsan/removerProgramaCobrancaAction.do"
			document.forms[0].submit();
		 }
	}
}
-->
</script>
</head>

<body leftmargin="5" topmargin="5">

<html:form action="/removerProgramaCobrancaAction" 
	name="ManutencaoRegistroActionForm"
	type="gcom.gui.ManutencaoRegistroActionForm" method="post"
	onsubmit="return CheckboxNaoVazio(document.ManutencaoRegistroActionForm.idRegistrosRemocao) && confirm('Confirma remoção?')">

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
		<td width="625" valign="top" class="centercoltext">
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
					<td class="parabg">Manter Programa de Cobranca</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
				<tr>
					<td height="5" colspan="3"></td>
				</tr>
			</table>
	
			<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="4" height="23">
						<font color="#000000" style="font-size:10px" face="Verdana, Arial, Helvetica, sans-serif">
						<strong>Programas de Cobrança Encontrados:</strong>
						</font>
					</td>
					<td align="right"></td>
				</tr>
	  		</table>
			
	  		<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="4" bgcolor="#000000" height="2"></td>
				</tr>
				<tr>
					<td>
						<table width="100%" bgcolor="#99CCFF">
							<!--header da tabela interna -->
							<tr bgcolor="#99CCFF">
								<td width="7%" align="center">
									<strong>
									<a onclick="this.focus();" id="0" href="javascript:facilitador(this);">Todos</a>
									</strong>
								</td>
								<td width="31%" align="center">
									<strong>Nome</strong>
								</td>
								<td width="31%" align="center">
									<strong>Descrição</strong>
								</td>
								<td width="31%" align="center">
									<strong>Critério</strong>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" bgcolor="#99CCFF">
						<%--Esquema de paginação--%>
						<pg:pager isOffset="true" index="half-full" maxIndexPages="10"
							export="currentPageNumber=pageNumber;pageOffset"
							maxPageItems="10" items="${sessionScope.totalRegistros}">
							<pg:param name="pg" />
							<pg:param name="q" />
							<logic:present name="programas">
								<%int cont = 1;%>
								<logic:iterate name="programas" id="programa">
									<pg:item>
									 <%cont = cont + 1;
										if (cont % 2 == 0) {%>
									 	   <tr bgcolor="#FFFFFF">
									<%} else { %>
										   <tr bgcolor="#cbe5fe"> <%}%>
											<logic:equal name="programa" property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>">

												<td width="7%">
												 <div align="center">
													<input type="checkbox" name="idRegistrosRemocao" value="<bean:write name="programa" property="id"/>" />
												 </div>
												</td>
												<td width="31%"><font color="#CC0000"> 
													<a href="exibirAtualizarProgramaCobrancaAction.do?manter=1&codigoPrograma=<bean:write name="programa" property="id" />">
														<bean:write name="programa" property="nome" />
													</a></font>
												</td>
												<td width="31%"><font color="#CC0000"> 
												  <logic:empty name="programa" property="descricao">
												  	&nbsp;
                     							  </logic:empty> 
	                     						  <logic:notEmpty name="programa" property="descricao">
													 <bean:write name="programa" property="descricao" />
												  </logic:notEmpty> </font>
												</td>

												<td width="31%"><font color="#CC0000"> 
													<bean:write name="programa" property="criterio.descricaoCobrancaCriterio" /> </font>
												</td>
											</logic:equal>

											<logic:notEqual name="programa" property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>">
												<td width="7%">
												  <div align="center">
												    <input type="checkbox" name="idRegistrosRemocao" value="<bean:write name="programa" property="id"/>" />
												  </div>
												</td>
												<td width="31%">
												  <a href="exibirAtualizarProgramaCobrancaAction.do?manter=1&codigoPrograma=<bean:write name="programa" property="id" />">
													<bean:write name="programa" property="nome" />
												  </a>
												</td>
												<td width="31%">
												  <logic:empty name="programa" property="descricao">
                      								&nbsp;
                     							  </logic:empty>
                     							  <logic:notEmpty name="programa" property="descricao">
													<bean:write name="programa" property="descricao" />
												  </logic:notEmpty></td>

												<td width="31%">
													<bean:write name="programa" property="criterio.descricaoCobrancaCriterio" />
												</td>

											</logic:notEqual>
										</tr>
									</pg:item>
								</logic:iterate>
							</logic:present>
						<%-- Fim do esquema de paginação --%>
						</table>
						<table width="100%">
						  <tr>
							<td>
							  <gcom:controleAcessoBotao name="Button" value="Remover" onclick="remover(idRegistrosRemocao);" url="removerClienteAction.do"/>
								<input name="button" type="button" class="bottonRightCol" value="Voltar Filtro"
									   onclick="window.location.href='<html:rewrite page="/exibirFiltrarProgramaCobrancaAction.do?botao=VoltarFiltro"/>'">
							</td>
						  </tr>
						</table>
						<table width="100%" border="0">
							<tr>
								<td align="center">
									<strong><%@ include file="/jsp/util/indice_pager_novo.jsp"%></strong>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>		
		</pg:pager>
		<p>&nbsp;</p>
		</td>
	</tr>
</table>
<%@ include file="/jsp/util/rodape.jsp"%>

</body>
</html:form>
</html:html>