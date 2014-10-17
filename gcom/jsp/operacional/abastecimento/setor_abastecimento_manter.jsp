<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>

<%@ page import="gcom.operacional.SetorAbastecimento"%>
<%@ page import="gcom.util.ConstantesSistema"%>
<%@ page import="java.util.Collection"%>

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
-->
</script>
</head>

<body leftmargin="5" topmargin="5">

<html:form action="/removerSetorAbastecimentoAction"
	name="ManutencaoRegistroActionForm"
	type="gcom.gui.ManutencaoRegistroActionForm" method="post"
	onsubmit="return CheckboxNaoVazio(document.ManutencaoRegistroActionForm.idRegistrosRemocao) && confirm('Confirmar exclusao?')">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="130" valign="top" class="leftcoltext">
			<div align="center">
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>

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
					<td class="parabg">Manter Setor de Abastecimento</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>

				</tr>
			</table>

			<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="7" height="23"><font style="font-size: 10px;"
						color="#000000" face="Verdana, Arial, Helvetica, sans-serif"> <strong>Setor(s) de Abastecimento
					cadastrado(s):</strong> </font></td>
				</tr>
				<tr>
					<!--<td colspan="4" bgcolor="#3399FF"> -->
					<td colspan="7" bgcolor="#000000" height="2" valign="baseline"></td>
				</tr>
				<tr>
					<td>
					<table width="100%" align="center" bgcolor="#90c7fc" border="0"
						cellpadding="0" cellspacing="0">
						<tr bgcolor="#cbe5fe">
							<td width="100%" align="center">
							<table width="100%" bgcolor="#90c7fc">
								<tr bordercolor="#FFFFFF" bgcolor="#79BBFD">
									<td width="7%">
										<div align="center"><strong><a
										href="javascript:facilitador(this);" id="0">Todos</a></strong>
										</div>
									</td>
									
									<td width="5%">
										<div align="center"><strong>Código do Setor de Abastecimento</strong></div>
									</td>
									<td width="30%">
										<div align="center"><strong>Descrição do Setor de Abastecimento</strong></div>
									</td>
									<td width="15%">
										<div align="center"><strong>Localidade</strong></div>
									</td>
									<td width="15%">
										<div align="center"><strong>Distrito Operacional</strong></div>
									</td>
									<td width="15%">
										<div align="center"><strong>Zona Abastecimento</strong></div>
									</td>
									<td width="20%">
										<div align="center"><strong>Indicador de Uso</strong></div>
									</td>
									
								</tr>

								<pg:pager isOffset="true" index="half-full" maxIndexPages="10"
									export="currentPageNumber=pageNumber;pageOffset"
									maxPageItems="10" items="${sessionScope.totalRegistros}">

									<pg:param name="pg" />
									<pg:param name="q" />

									<%int cont = 0;%>
									<logic:iterate name="collectionSetorAbastecimento" id="setorAbastecimento"
										type="SetorAbastecimento">

									<pg:item>
										<%cont = cont + 1;
										if (cont % 2 == 0) {%>
											<tr bgcolor="#cbe5fe">
										<%} else {%>
											<tr bgcolor="#FFFFFF">
										<%}%>

											<td width="7%">
												<div align="center"><input type="checkbox"
												name="idRegistrosRemocao"
												value="<bean:write name="setorAbastecimento" property="id"/>">
												</div>
											</td>

											<td width="5%" align="center">
											<html:link
												href="/gsan/exibirAtualizarSetorAbastecimentoAction.do?manter=sim"
												paramId="idRegistroAtualizacao" paramProperty="id"
												paramName="setorAbastecimento"
												title="<%="" + setorAbastecimento.getId()%>">
												<bean:write name="setorAbastecimento" property="codigoSetorAbastecimento"/>
											</html:link>
											</td>

											<td width="30%">
												<div align="center">${setorAbastecimento.descricao}</div>
											</td>
											
											<td width="15%">
												<div align="center">
												${setorAbastecimento.zonaAbastecimento.localidade.descricaoComId}
												</div>
											</td>
											
											<td width="15%">
												<div align="center">
												${setorAbastecimento.distritoOperacional.descricaoComId}
												</div>
											</td>
											
											<td width="15%">
												<div align="center">
												${setorAbastecimento.zonaAbastecimento.descricaoComCodigo}
												</div>
											</td>
											
											<td width="20%">
												<div align="center">
												${setorAbastecimento.indicadorUsoFormatado}
												</div>
											</td>
											
										</pg:item>
									</logic:iterate>
							</table>
							<table width="100%" border="0">
								<tr>
									<td align="center"><strong><%@ include
										file="/jsp/util/indice_pager_novo.jsp"%></strong></td>
								</tr>
								</pg:pager>
								<tr>
									<td>
										<font color="#FF0000">
										<html:submit
											property="buttonRemover" 
											styleClass="bottonRightCol"
											value="Remover" /> 
										</font> 
										<input name="button" 
											type="button"
											class="bottonRightCol" 
											value="Voltar Filtro"
											onclick="window.location.href='<html:rewrite page="/exibirFiltrarSetorAbastecimentoAction.do?limpar=S"/>'"
											align="left" 
											style="width: 80px;">
									</td>
									<td valign="top">
									<div align="right"><a href="javascript:toggleBox('demodiv',1);">
									<img border="0"
										src="<bean:message key="caminho.imagens"/>print.gif"
										title="Imprimir Setor(s) de Abastecimento" /> </a></div>
									</td>
								</tr>
							</table>
							</td>
						</tr>

					</table>
					<p>&nbsp;</p>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<jsp:include
		page="/jsp/relatorio/escolher_tipo_relatorio.jsp?relatorio=gerarRelatorioSetorAbastecimentoManterAction.do" />
	<%@ include file="/jsp/util/rodape.jsp"%>
	
</body>
</html:form>
</html:html>
