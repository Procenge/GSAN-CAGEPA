<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>

<%@ page import="gcom.util.Pagina,gcom.util.ConstantesSistema"%>
<%@ page import="gcom.arrecadacao.ContratoDemandaConsumo"%>
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

<html:form action="/removerContratoDemandaConsumoAction"
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
		<td class="parabg" >Pesquisa de Contratos de Demanda de Consumo</td>
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
					<td width="20%"><strong>Matricula do Imóvel</strong></td>
					<td width="15%"><strong>Número do contrato</strong></td>
					<td width="15%"><strong>Mês/Ano Fat. Inicial</strong></td>
					<td width="15%"><strong>Mês/Ano Fat. Final</strong></td>
					<td width="13%"><strong>Tipo de Contrato</strong></td>
				</tr>

				<%--Esquema de paginação--%>
				<pg:pager isOffset="true" index="half-full" maxIndexPages="10" export="currentPageNumber=pageNumber;pageOffset" maxPageItems="10" items="${sessionScope.totalRegistros}">

					<%
						int cont = 0;
					%>

					<pg:param name="pg" />
					<pg:param name="q" />

					<logic:iterate id="contratoDemandaConsumo" name="colecaoContratoDemandaConsumo" type="ContratoDemandaConsumo">
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
											value="<bean:write name="contratoDemandaConsumo" property="id"/>" />
									</div>
								</td>
								<td width="20%">
											<a href="exibirAtualizarContratoDemandaConsumoAction.do?manter=1&idRegistroAtualizacao=<bean:write name="contratoDemandaConsumo" property="id" />">
												<bean:write name="contratoDemandaConsumo" property="imovel.id" />
											</a>
								</td>
								
								<td width="15%">
									<bean:write name="contratoDemandaConsumo" property="numeroContrato" />
								</td>
								<td width="15%">
									<bean:write name="contratoDemandaConsumo" property="mesAnoFaturamentoInicio" />
								</td>
								<td width="15%">
									<bean:write name="contratoDemandaConsumo" property="mesAnoFaturamentoFim" />
								</td>
								<td width="13%">
									<logic:present name="contratoDemandaConsumo" property="consumoTarifa">
											<bean:write name="contratoDemandaConsumo" property="consumoTarifa.descricao" />
									</logic:present> 
									<logic:present name="contratoDemandaConsumo" property="numeroConsumoFixo">
											<bean:write name="contratoDemandaConsumo" property="numeroConsumoFixo" />
									</logic:present></td>
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
			url="removerContratoDemandaConsumoAction.do" /> <input name="button"
			type="button" class="bottonRightCol" value="Voltar Filtro"
			onclick="window.location.href='<html:rewrite page="/exibirFiltrarContratoDemandaConsumoAction.do"/>'"
			align="left" style="width: 80px;"></td>
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
