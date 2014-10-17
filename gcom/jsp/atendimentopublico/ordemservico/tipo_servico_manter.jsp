<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>
<%@ page
	import="gcom.util.Pagina,gcom.util.ConstantesSistema,java.util.Collection"%>

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
<script>
<!--
function facilitador(objeto){
	if (objeto.value == "0"){
		objeto.value = "1";
		marcarTodos();
	}
	else{
		objeto.value = "0";
		desmarcarTodos();
	}
}

function validarForm(form){
	form.submit();
}
-->
</script>

</head>

<body leftmargin="5" topmargin="5">

<html:form action="/removerTipoServicoAction"
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
					<td class="parabg">Manter Tipo de Serviço</td>

					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
				<tr>
					<td height="5" colspan="3"></td>
				</tr>
			</table>


			<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="6" height="23"><font color="#000000"
						style="font-size:10px"
						face="Verdana, Arial, Helvetica, sans-serif"><strong>Tipos de Serviço
					Encontrados:</strong></font></td>
					<td align="right"></td>
				</tr>
				</table>
			
	  			<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<!--<td colspan="4" bgcolor="#3399FF"> -->
					<td colspan="6" bgcolor="#000000" height="2"></td>
				</tr>

				<tr bordercolor="#000000">

					<td width="7%" bgcolor="#90c7fc">
					<div align="center"><strong><a href="javascript:facilitador(this);">Todos</a></strong></div>
					</td>
					<td width="12%" align="center" bgcolor="#90c7fc"><strong>C&oacute;digo</strong></td>
					<td width="34%" align="center" bgcolor="#90c7fc"><strong>Descrição</strong></td>
					<td width="12%" align="center" bgcolor="#90c7fc"><strong>Cód.Serviço Tipo</strong></td>
					<td width="12%" align="center" bgcolor="#90c7fc"><strong>Tempo Médio</strong></td>
					<td width="23%" align="center" bgcolor="#90c7fc"><strong>Ind.Atualização Comercial</strong></td>
				</tr>
				<tr>
					<td colspan="6">
					<table width="100%" bgcolor="#99CCFF">


						<%--Esquema de paginação--%>
						<pg:pager isOffset="true" index="half-full" maxIndexPages="10"
							export="currentPageNumber=pageNumber;pageOffset"
							maxPageItems="10" items="${sessionScope.totalRegistros}">
							<pg:param name="q" />
							<logic:present name="colecaoServicoTipo">
								<%int cont = 0;%>
								<logic:iterate name="colecaoServicoTipo" id="servicoTipo">
									<pg:item>
										<%cont = cont + 1;
									if (cont % 2 == 0) {%>
										<tr bgcolor="#cbe5fe">
											<%} else {

									%>
										<tr bgcolor="#FFFFFF">
											<%}%>
											<logic:equal name="servicoTipo" property="indicadorUso"
												value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>">
												<td width="7%">
												
												<div align="center"><input type="checkbox"
													name="idRegistrosRemocao"
													value="<bean:write name="servicoTipo" property="id"/>" /></div>
												</td>
												
												<td width="12%" align="center">

												<div align="center"><bean:write name="servicoTipo"	property="id" /></div></td>
												
												<td width="34%"><a href="exibirAtualizarTipoServicoAction.do?pesquisa=S&manter=sim&idRegistroAtualizacao=
													<bean:write	name="servicoTipo" property="id" />">
													
												<bean:write name="servicoTipo" property="descricao" /></a></td>

												<td width="12%">
												<logic:equal name="servicoTipo" property="codigoServicoTipo" value="C">
													 Comercial 
												 </logic:equal>
					 							 <logic:equal name="servicoTipo" property="codigoServicoTipo" value="O">
													 Operacional
												 </logic:equal>
												 </td>

												<td width="12%">
													<div align="center"><bean:write name="servicoTipo" property="tempoMedioExecucao" /></div>
												</td>
												
												<td width="23%">
												  <logic:equal name="servicoTipo" property="indicadorAtualizaComercial" value="1">
												     Sim, no momento da execução
												  </logic:equal>
												  <logic:equal name="servicoTipo" property="indicadorAtualizaComercial" value="2">
												     Não Atualiza
												  </logic:equal>
												  <logic:equal name="servicoTipo" property="indicadorAtualizaComercial" value="3">
												     Sim, no momento posterior
												  </logic:equal>												  												  
													<!--<div align="center"><bean:write name="servicoTipo" property="indicadorAtualizaComercial" /></div>-->												 
												</td>
										</tr>
										</logic:equal>
										<logic:notEqual name="servicoTipo" property="indicadorUso"
											value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>">
											<td width="7%">
											
											<div align="center"><input type="checkbox"
												name="idRegistrosRemocao"
												value="<bean:write name="servicoTipo" property="id"/>" /></div>
												
											</td>
											
											<td width="12%">
											
											<div align="center"><bean:write name="servicoTipo"
												property="id" /></div>
											</td>
											
											<td width="34%"><a
												href="exibirAtualizarTipoServicoAction.do?pesquisa=S&manter=sim&idRegistroAtualizacao=<bean:write
													name="servicoTipo" property="id" />">
											<bean:write name="servicoTipo" property="descricao" /> </a></td>
											
											<td width="12%">
												<logic:equal name="servicoTipo" property="codigoServicoTipo" value="C">
													 Comercial 
												 </logic:equal>
					 							 <logic:equal name="servicoTipo" property="codigoServicoTipo" value="O">
													 Operacional
												 </logic:equal>
											</td>
											<td width="12%">
											<div align="center"><bean:write name="servicoTipo"
												property="tempoMedioExecucao" /></div>
											</td>
											
											<td width="23%">
												  <logic:equal name="servicoTipo" property="indicadorAtualizaComercial" value="1">
												     Sim, no momento da execução
												  </logic:equal>
												  <logic:equal name="servicoTipo" property="indicadorAtualizaComercial" value="2">
												     Não Atualiza
												  </logic:equal>
												  <logic:equal name="servicoTipo" property="indicadorAtualizaComercial" value="3">
												     Sim, no momento posterior
												  </logic:equal>	
													<!--<div align="center"><bean:write name="servicoTipo" property="indicadorAtualizaComercial" /></div>-->
											</td>

										</logic:notEqual>
									</pg:item>
								</logic:iterate>
							</logic:present>
					</table>
					<table width="100%" border="0">
						<tr>
							<td valign="top">
							<gcom:controleAcessoBotao name="Button" value="Remover"
							  onclick="javascript:validarForm(document.forms[0]);" url="removerTipoServicoAction.do"/>
					<!--
							<html:submit styleClass="bottonRightCol"
								value="Remover" property="Button" /> --> <input name="button"
								type="button" class="bottonRightCol" value="Voltar Filtro"
								onclick="window.location.href='/gsan/exibirFiltrarTipoServicoAction.do'"
								align="left" style="width: 80px;"></td>

						</tr>
					</table>

					<table width="100%" border="0">
						<tr>
							<td>
							<div align="center"><strong><%@ include
								file="/jsp/util/indice_pager_novo.jsp"%></strong></div>
							</td>
							</pg:pager>
							<%-- Fim do esquema de paginação --%>
						</tr>
					</table>
					</td>
				</tr>

			</table>
	</table>
	<jsp:include
		page="/jsp/relatorio/escolher_tipo_relatorio.jsp?relatorio=gerarRelatorioBairroManterAction.do" />
	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</body>
</html:html>
