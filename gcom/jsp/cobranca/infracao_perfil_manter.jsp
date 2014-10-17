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

function adicionarDebitoTipo(id){
	abrirPopup('exibirInserirInfracaoPerfilDebitoTipo.do?manter=S&idPerfil='+id,400,700);
}
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

<body leftmargin="5" topmargin="5">
	<html:form action="/removerInfracaoPerfilAction.do"
	   name="ManutencaoRegistroActionForm"
	   type="gcom.gui.ManutencaoRegistroActionForm"
	   method="post" >
	  
	  <%@ include file="/jsp/util/cabecalho.jsp"%>
	  <%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="115" valign="top" class="leftcoltext">
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


			<td valign="top" class="centercoltext">
			<table>
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Manter Infração Perfil</td>
					<td width="11" valign="top"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>

			<p>&nbsp;</p>
	  
	 	 <table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="5" height="23">
						<font color="#000000" style="font-size:10px" face="Verdana, Arial, Helvetica, sans-serif">
							<strong>registros Encontrados:</strong>
						</font>
					</td>
					<td align="right">
					</td>
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
					<td width="12%" align="center" bgcolor="#90c7fc"><strong>C&oacute;d.</strong></td>
					<td width="34%" align="center" bgcolor="#90c7fc"><strong>Tipo Infr.</strong></td>
					<td width="25%" align="center" bgcolor="#90c7fc"><strong>Categ.</strong></td>
					<td width="30%" align="center" bgcolor="#90c7fc"><strong>Subcategoria</strong></td>
					<td width="34%" align="center" bgcolor="#90c7fc"><strong>Perf.<br /> Imovel</strong></td>
					
				</tr>
				<tr>
					<td colspan="6">
					<table width="100%" bgcolor="#99CCFF">


						<%--Esquema de paginação--%>
						<pg:pager isOffset="true" index="half-full" maxIndexPages="10" export="currentPageNumber=pageNumber;pageOffset" maxPageItems="10" items="${sessionScope.totalRegistros}">
							<pg:param name="q" />
								<logic:present name="colecaoInfracaoPerfil">
									<%int cont = 0;%>
									<logic:iterate name="colecaoInfracaoPerfil" id="item">
										<pg:item>
										  <%cont = cont + 1;
											if (cont % 2 == 0) {%>
											<tr bgcolor="#cbe5fe">
											<%} else { %>
											<tr bgcolor="#FFFFFF">
											<%}%>
												<td width="9%">
													<div align="center">
														<input type="checkbox" name="idRegistrosRemocao" value="<bean:write name="item" property="id"/>" />
													</div>
												</td>
												<td width="10%" align="center">
													<div align="center">
														<bean:write name="item" property="id" /> </div></td>
												<td width="34%">
													<a href="javascript:adicionarDebitoTipo(<bean:write name="item" property="id" />);">
													 ${item.infracaoTipo.descricao} 
													</a></td>
												<td width="30%">
													 ${item.categoria.descricao}
													</td>
												<td width="34%">
													${item.subcategoria.descricao} 
													</td>
												<td width="34%">
													 ${item.imovelPerfil.descricao} 
													</td>
											</tr>
										</pg:item>
									</logic:iterate>
								</logic:present> 
					</table>
					<table width="100%" border="0">
						<tr>
							<td valign="top">
								<html:submit
								property="buttonRemover" styleClass="bottonRightCol"
								value="Remover" />
								<input  name="button"
										type="button" 
										class="bottonRightCol" 
										value="Voltar Filtro"
										onclick="window.location.href='/gsan/exibirFiltrarInfracaoPerfilAction.do'"
										align="left" 
										style="width: 80px;"></td>
							<td valign="top">
							
							<div align="right"><!-- 								<a href="javascript:toggleBox('demodiv',1);">
									<img border="0" src="<bean:message key="caminho.imagens"/>print.gif" title="Imprimir Orgaos Externos" /> 
								</a>--></div>
							</td>
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
			</tr>


	</table>
	<%@ include file="/jsp/util/rodape.jsp"%>
	</html:form>
</body>
</html>