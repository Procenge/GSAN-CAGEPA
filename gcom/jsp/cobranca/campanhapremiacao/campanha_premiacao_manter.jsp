<%@page import="gcom.cobranca.campanhapremiacao.CampanhaPremio"%>
<%@page import="gcom.cobranca.campanhapremiacao.CampanhaPremiacao"%>
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
	<html:form action="/ConsultarDadosPremiacoesUnidadeAction.do"
	   name="ManutencaoRegistroActionForm"
	   type="gcom.gui.ManutencaoRegistroActionForm"
	   method="post" >
	
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
					<td class="parabg">Manter Premiações da Campanha</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>

				</tr>
			</table>
			
			<p>&nbsp;</p>
	  <table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="5" height="23">
						<font color="#000000" style="font-size:10px" face="Verdana, Arial, Helvetica, sans-serif">
							<strong>Premiações Encontradas:</strong>
						</font>
					</td>
					<td align="right">
						</td>
				</tr>
			</table>
			
	  		<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<!--<td colspan="4" bgcolor="#3399FF"> -->
					<td colspan="5" bgcolor="#000000" height="2"></td>
				</tr>

				<tr bordercolor="#000000">

					<td width="25%" align="center" bgcolor="#90c7fc"><strong>Unidade Premiação</strong></td>
					<td width="45%" align="center" bgcolor="#90c7fc"><strong>Prêmio</strong></td>
					<td width="10%" align="center" bgcolor="#90c7fc"><strong>Ordem Premiação</strong></td>
					<td width="10%" align="center" bgcolor="#90c7fc"><strong>Qtde. Prêmio</strong></td>
					<td width="10%" align="center" bgcolor="#90c7fc"><strong>Qtde. Sorteada</strong></td>
					
					
				</tr>
				<tr>
					<td colspan="5">
					<table width="100%" bgcolor="#99CCFF">


<!-- 						Esquema de paginação -->
						<pg:pager isOffset="true" index="half-full" maxIndexPages="10" export="currentPageNumber=pageNumber;pageOffset" maxPageItems="10" items="${sessionScope.totalRegistros}">
							<pg:param name="q" />
								<logic:present name="listaCampanhaPremio">
									<%int cont = 0;%>
									<logic:iterate name="listaCampanhaPremio" id="item">
										<pg:item>
										  <%cont = cont + 1;
 											if (cont % 2 == 0) {%> 
											<tr bgcolor="#cbe5fe">
											<%} else { %>
											<tr bgcolor="#FFFFFF">
											<%}%>

													
													<td width="25%" align="center">
													
														<div align="center">
															 <logic:present name="item" property="gerenciaRegional">
															 	<html:link href="/gsan/ConsultarDadosPremiacoesUnidadeAction.do" paramId="idRegistroConsultar" 
															 				paramProperty="id" paramName="item" 
															 				title="<%="" + ((CampanhaPremio)item).getGerenciaRegional().getId() +" - "+ 
															 				((CampanhaPremio)item).getGerenciaRegional().getNome()%>">
															 		<bean:write name="item" property="gerenciaRegional.nomeAbreviado" /> 
															 	</html:link>
															 </logic:present>
															 
															 <logic:present name="item" property="unidadeNegocio">
															 	<html:link href="/gsan/ConsultarDadosPremiacoesUnidadeAction.do" paramId="idRegistroConsultar" 
															 				paramProperty="id" paramName="item" 
															 				title="<%="" + ((CampanhaPremio)item).getUnidadeNegocio().getId() +" - "+ 
															 				((CampanhaPremio)item).getUnidadeNegocio().getNome()%>">
															 		<bean:write name="item" property="unidadeNegocio.nomeAbreviado" />
															 	</html:link> 
															 </logic:present>
															 
															 <logic:present name="item" property="eloPremio">
															 <html:link href="/gsan/ConsultarDadosPremiacoesUnidadeAction.do" paramId="idRegistroConsultar" 
															 				paramProperty="id" paramName="item" 
															 				title="<%="" + ((CampanhaPremio)item).getEloPremio().getDescricaoComId()%>">
															 		<bean:write name="item" property="eloPremio.id" /> 
															 	</html:link>
															 </logic:present>
															 
															 <logic:present name="item" property="localidade">
															 	<html:link href="/gsan/ConsultarDadosPremiacoesUnidadeAction.do" paramId="idRegistroConsultar" 
															 				paramProperty="id" paramName="item" 
															 				title="<%="" + ((CampanhaPremio)item).getLocalidade().getDescricaoComId()%>">
															 		<bean:write name="item" property="localidade.id" /> 
															 	</html:link>
															 </logic:present>
															 
															 <logic:notPresent name="item" property="gerenciaRegional">
															 	<logic:notPresent name="item" property="unidadeNegocio">
															 		<logic:notPresent name="item" property="eloPremio">
															 			<logic:notPresent name="item" property="localidade">
															 				<html:link href="/gsan/ConsultarDadosPremiacoesUnidadeAction.do" paramId="idRegistroConsultar" 
															 				paramProperty="id" paramName="item">
															 					<strong>Global</strong>
															 				</html:link>
															 			</logic:notPresent>
															 		</logic:notPresent>
															 	</logic:notPresent> 
															 </logic:notPresent>
														</div>
														
														</td>
														
													<td width="45%" align="center">
														<div align="center">
															 <bean:write name="item" property="descricao" /> </div></td>
													<td width="10%" align="center">
														<div align="center">
															<bean:write name="item" property="numeroOrdemPremiacao" /> </div></td>
													<td width="10%" align="center">
														<div align="center">
															<bean:write name="item" property="quantidadePremio" /> </div></td>
													<td width="10%" align="center">
														<div align="center">
															<bean:write name="item" property="quantidadePremioSorteada" /> </div></td>
												

										</tr>
									</pg:item>
								</logic:iterate>
							</logic:present> 
						
					</table>
					<table width="100%" border="0">
						<tr>
							<td>
							<div align="center"><strong>
								<%@ include file="/jsp/util/indice_pager_novo.jsp"%></strong></div>
							</td>
							</pg:pager>
<!-- 							Fim do esquema de paginação -->
						</tr>
					</table>
					<table width="100%" border="0">
						<tr>
							<td valign="top">
								
								<input  name="button"
 										type="button"  
 										class="bottonRightCol"  
 										value="Voltar Filtro" 
 										onclick="window.location.href='/gsan/ExibirFiltrarCampanhaPremiacaoAction.do?menu=sim'" 
 										align="left"  
										style="width: 80px;"></td> 
							<td valign="top">
							
 							<div align="right">								<a href="emitirRelatorioPremiacoesAction.do">
									<img border="0" src="<bean:message key="caminho.imagens"/>print.gif" title="Imprimir Relatório Premiações" /> 
								</a></div> 
							</td>
						</tr>
					</table>
					</td>
				</tr>
				</table>
			
			</td>
			
			</tr>
				<%@ include file="/jsp/util/rodape.jsp"%>
			</table>
			
			
		
	</html:form>
</body>
</html>