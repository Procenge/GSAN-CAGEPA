<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>

<%@ page import="gcom.util.Pagina,gcom.util.ConstantesSistema"%>
<%@ page import="gcom.cadastro.endereco.Cep"%>
<%@ page import="java.util.Collection"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta name="generator"
	content="Web page layout created by Xara Webstyle 4 - http://www.xara.com/webstyle/" />
<link rel="stylesheet"
	href="<bean:message key='caminho.css'/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript"
	src="<bean:message key='caminho.js'/>util.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<script>

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


</script>

</head>

<body leftmargin="5" topmargin="5" >

<html:form action="/removerCepAction"
	name="ManutencaoRegistroActionForm"
	type="gcom.gui.ManutencaoRegistroActionForm" method="post"
	onsubmit="return CheckboxNaoVazio(document.ManutencaoRegistroActionForm.idRegistrosRemocao) && confirm('Confirma remoção?')">

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp"%>


<table width="710" border="0" cellspacing="4" cellpadding="0" >
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
<td valign="top" bgcolor="#003399" class="centercoltext" >
	<table height="100%" >

	</table>
	<table  border="0" cellpadding="0" cellspacing="5" style=" max-width: 597px;">
<tr>
	<td style=" max-width: 597px; border: none;" valign="top" class="centercoltext">
<table height="100%">
	<tr>
		<td></td>
	</tr>
</table>
<table width="580px" border="0" align="center" cellpadding="0"
	cellspacing="0">
	<tr>
		<td width="11" ><img
			src="<bean:message key='caminho.imagens'/>parahead_left.gif"
			editor="Webstyle4"
			moduleid="Album Photos (Project)\toptab_page2_parahead_left.xws"
			border="0" /></td>
		<td class="parabg" >Pesquisa de CEPs</td>
		<td width="11"><img
			src="<bean:message key='caminho.imagens'/>parahead_right.gif"
			editor="Webstyle4"
			moduleid="Album Photos (Project)\toptab_page2_parahead_right.xws"
			border="0" /></td>
	</tr>
</table>
<p>&nbsp;</p>

<table width="580px" cellpadding="0" cellspacing="0" style=" max-width: 700px;">
	<tr>
		<td>
			<table  bgcolor="#99CCFF" >
				<tr bgcolor="#99CCFF" >
					<td width="7%" bgcolor="#90c7fc" height="20">
						<div align="center">
							<strong><a href=" javascript:facilitador(this);" id="0">Todos</a></strong>
						</div>
					</td>
					<td width="15%"><strong>CEP</strong></td>
					<td width="20%"><strong>Logradouro</strong></td>
					<td width="15%"><strong>Bairro</strong></td>
					<td width="13%"><strong>Município</strong></td>
					<td width="5%"><strong>UF</strong></td>
					<td width="10%">
					<div align="center">
							<strong>Faixa</strong>
						</div></td>
				</tr>

				<%--Esquema de paginação--%>
				<pg:pager isOffset="true" index="half-full" maxIndexPages="10" export="currentPageNumber=pageNumber;pageOffset" maxPageItems="10" items="${sessionScope.totalRegistros}">

					<%
						int cont = 0;
					%>

					<pg:param name="pg" />
					<pg:param name="q" />

					<logic:iterate id="cep" name="colecaoCep" type="Cep">
						<pg:item>
							<!--	<tr class="linhaBaixoTD"> -->
							<%
								cont = cont + 1;
								if (cont % 2 == 0) {
							%>
							<tr bgcolor="#cbe5fe">
								<%
									} else {
								%>
							<tr bgcolor="#FFFFFF">
								<%
									}
								%>
								<td width="7%">
									<div align="center">
										<input type="checkbox" name="idRegistrosRemocao"
											value="<bean:write name="cep" property="cepId"/>" />
									</div>
								</td>
								<td width="15%">
								<logic:present name="cep" property="codigo">
										<logic:notEqual name="cep" property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>">
											<a href="exibirAtualizarCepAction.do?manter=1&idRegistroAtualizacao=<bean:write name="cep" property="cepId" />">
												<bean:write name="cep" property="cepFormatado" />
											</a>
										</logic:notEqual>
										<logic:equal name="cep" property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>">
											<font color="#CC0000"> 
												<a href="exibirAtualizarCepAction.do?manter=1&idRegistroAtualizacao=<bean:write name="cep" property="cepId" />">
													<bean:write name="cep" property="cepFormatado" />
												</a>
											</font>
										</logic:equal>
									</logic:present> 
									<logic:notPresent name="cep" property="codigo">
										&nbsp;
									</logic:notPresent>
								
								</td>
								
								<td width="18%"><logic:present name="cep" property="descricaoLogradouroFormatada">
										<logic:present name="caminhoRetornoTelaInformarEndereco" scope="session">
											<logic:notEqual name="cep" property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>">
												<a href="javascript:enviarDadosParametros('<bean:write name="caminhoRetornoTelaInformarEndereco"/>', '<bean:write name="cep" property="codigo"/>', '<bean:write name="cep" property="descricaoLogradouroFormatada"/>', 'cep');">
													<bean:write name="cep" property="descricaoLogradouroFormatada" />
												</a>
											</logic:notEqual>
											<logic:equal name="cep" property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>">
												<a href="javascript:enviarDadosParametros('<bean:write name="caminhoRetornoTelaInformarEndereco"/>', '<bean:write name="cep" property="codigo"/>', '<bean:write name="cep" property="descricaoLogradouroFormatada"/>', 'cep');">
													<font color="#CC0000"> <bean:write name="cep" property="descricaoLogradouroFormatada" />
												</font>
												</a>
											</logic:equal>
										</logic:present>

										<logic:notPresent name="caminhoRetornoTelaInformarEndereco" scope="session">
											<logic:notEqual name="cep" property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>">
<%-- 												<a href="exibirAtualizarCepAction.do?manter=1&idRegistroAtualizacao=<bean:write name="cep" property="cepId" />"> --%>
													<bean:write name="cep" property="descricaoLogradouroFormatada" />
<!-- 												</a> -->
											</logic:notEqual>
											<logic:equal name="cep" property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>">
<%-- 												<a href="exibirAtualizarCepAction.do?manter=1&idRegistroAtualizacao=<bean:write name="cep" property="cepId" />"> --%>
													<font color="#CC0000"> <bean:write name="cep" property="descricaoLogradouroFormatada" />
												</font>
<!-- 												</a> -->
											</logic:equal>
										</logic:notPresent>
									</logic:present> 
									<logic:notPresent name="cep" property="descricaoLogradouroFormatada">
										&nbsp;
									</logic:notPresent>
								</td>
								<td width="15%"><logic:present name="cep" property="bairro">
										<logic:notEqual name="cep" property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>">
											<bean:write name="cep" property="bairro" />
										</logic:notEqual>
										<logic:equal name="cep" property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>">
											<font color="#CC0000"> <bean:write name="cep"
													property="bairro" />
											</font>
										</logic:equal>
									</logic:present> 
									<logic:notPresent name="cep" property="bairro">
										&nbsp;
									</logic:notPresent></td>
								<td width="15%"><logic:present name="cep" property="municipio">
										<logic:notEqual name="cep" property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>">
											<bean:write name="cep" property="municipio" />
										</logic:notEqual>
										<logic:equal name="cep" property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>">
											<font color="#CC0000"> <bean:write name="cep" property="municipio" />
											</font>
										</logic:equal>
									</logic:present> 
									<logic:notPresent name="cep" property="municipio">
										&nbsp;
									</logic:notPresent></td>
								<td width="5%"><logic:present name="cep" property="sigla">
										<logic:notEqual name="cep" property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>">
											<bean:write name="cep" property="sigla" />
										</logic:notEqual>
										<logic:equal name="cep" property="indicadorUso" value="<%=ConstantesSistema.INDICADOR_USO_DESATIVO.toString()%>">
											<font color="#CC0000"> <bean:write name="cep"
													property="sigla" />
											</font>
										</logic:equal>
									</logic:present> 
									<logic:notPresent name="cep" property="sigla">
										&nbsp;
									</logic:notPresent></td>
								<td width="15%">
									<div align="center">
										<logic:present name="cep"
											property="descricaoFaixaFormatada">
											<bean:write name="cep"
												property="descricaoFaixaFormatada" />
										</logic:present>
										<logic:notPresent name="cep"
											property="descricaoFaixaFormatada">
						&nbsp;
					</logic:notPresent>
									</div>
								</td>
							</tr>
						</pg:item>
					</logic:iterate>
					
					
			</table>
		</td>
	</tr>
	<tr>
		<td align="center"><strong><%@ include
					file="/jsp/util/indice_pager_novo.jsp"%></strong>
		</td>
	</tr>
</table>
</pg:pager>
<table width="100%" border="0">
	<tr>
		<td valign="top"><input type="submit"
			class="bottonRightCol" name="Button" value="Remover"
			url="removerCepAction.do" /> <input name="button"
			type="button" class="bottonRightCol" value="Voltar Filtro"
			onclick="window.location.href='<html:rewrite page="/exibirFiltrarCepAction.do"/>'"
			align="left" style="width: 80px;"></td>
		<td align="right" valign="top">
			<a href="javascript:toggleBox('demodiv',1);"> 
				<img align="right" border="0" src="<bean:message key='caminho.imagens'/>print.gif" title="Imprimir Ceps" />
			</a>
		</td>
	</tr>
					</table>
				</td>
			</tr>
		</table>

	</td>
</tr>
</table>
<!-- Relatório -->
<jsp:include
	page="/jsp/relatorio/escolher_tipo_relatorio.jsp?relatorio=gerarRelatorioCepManterAction.do" />
<%@ include file="/jsp/util/rodape.jsp"%>

</html:form>
</body>
</html:html>
