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

// function validaCampanha(){
// 	var form = document.forms[0];
// 	form.action='ExibirFiltrarCampanhaPremiacaoAction.do?menu=sim';
//     form.submit();
// }

// function validaMudancaDataInicialSorteio(){
// 	var form = document.forms[0];
// 	var textoDataInicial = form.dataInicialSorteio.value;
// 	form.dataFinalSorteio.value = form.dataInicialSorteio.value;
	
// // 	verifica_tamanho_data(form.dataInicialSorteio);
// // 	verifica_tamanho_data(form.dataFinalSorteio);
// 	mascara_data(form.dataInicialSorteio);
// 	mascara_data(form.dataFinalSorteio);

// }

// function validaMudancaPermiacao(){
// 	var form = document.forms[0];
// 	form.action='ExibirFiltrarCampanhaPremiacaoAction.do';
//     form.submit();
// } 

// function validaForm(){
	
// 	var form = document.forms[0];
// 	var peloMenosUm = false;
// 	if(form.idCampanha.value != -1){
// 		peloMenosUm = true;
// 	}
// 	if(form.dataInicialSorteio.value != ''){
// 		peloMenosUm = true;
		
// 	}
// 	if(form.dataFinalSorteio.value != ''){
// 		peloMenosUm = true;
		
// 	}
// 	if(form.idPremio.value != ''){
// 		peloMenosUm = true;
// 	}
	
// 	if(!peloMenosUm){
// 		alert('Informe pelo menos uma opção de seleção.');
		
// 		return false;
// 	}
	
// 	if(comparaData(form.dataInicialSorteio.value, '>', form.dataFinalSorteio.value)){
// 		alert('Data Final do Período é anterior à Data Inicial do Período');
		
// 		return false
// 	}
	
// 	form.submit();
	
// }

</script>
</head>
<body leftmargin="5" topmargin="5" onload="limpar();">
	<html:form action="/FiltrarCampanhaPremiacaoAction.do?indicadorAtualizar=1"
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
					<td class="parabg">Consultar Dados das Premiações da Unidade</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>

				</tr>
			</table>
			
			<p>&nbsp;</p>
	  <table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<strong>Premiação <bean:write name="unidadePremiacao"/> </</strong>
			</tr>
		</table>
		<br />
		<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<!--<td colspan="4" bgcolor="#3399FF"> -->
					<td colspan="8" bgcolor="#000000" height="2"></td>
				</tr>
				
				<tr bordercolor="#000000">

					<td width="15%" align="center" bgcolor="#90c7fc"><strong>Número inscrição</strong></td>
					<td width="10%" align="center" bgcolor="#90c7fc"><strong>Imóvel</strong></td>
					<td width="25%" align="center" bgcolor="#90c7fc"><strong>Nome do Cliente</strong></td>
					<td width="15%" align="center" bgcolor="#90c7fc"><strong>CPF/CNPJ</strong></td>
					<td width="10%" align="center" bgcolor="#90c7fc"><strong>Prêmio</strong></td>
					<td width="10%" align="center" bgcolor="#90c7fc"><strong>Ordem Premiação</strong></td>
					<td width="15%" align="center" bgcolor="#90c7fc"><strong>Data Sorteio</strong></td>
					
					
				</tr>
				<tr>
					<td colspan="8">
					<table width="100%" bgcolor="#99CCFF">
					
	<!-- 						Esquema de paginação -->
						<pg:pager isOffset="true" index="half-full" maxIndexPages="10" export="currentPageNumber=pageNumber;pageOffset" maxPageItems="10" items="${sessionScope.totalRegistros}">
							<pg:param name="q" />
								<logic:present name="colecaoCampanhaPremiacao">
									<%int cont = 0;%>
									<logic:iterate name="colecaoCampanhaPremiacao" id="item">
										<pg:item>
										  <%cont = cont + 1;
 											if (cont % 2 == 0) {%> 
											<tr bgcolor="#cbe5fe">
											<%} else { %>
											<tr bgcolor="#FFFFFF">
											<%}%>
											<td  width="15%">
												<div align="center">
<%-- 													<html:link href="/gsan/ConsultarDadosPremiacaoInscricaoAction.do" paramId="idRegistroConsultar"  --%>
<%-- 															 				paramProperty="id" paramName="item"> --%>
													<a href="javascript:abrirPopup('ConsultarDadosPremiacaoInscricaoAction.do?idRegistroConsultar=${item.id}', 500, 500);">
														<bean:write name="item" property="campanhaCadastro.numeroInscricao" />
													</a>
<%-- 													</html:link>  --%>
												</div>
											</td>
											<td  width="10%">
												<div align="center">
													<bean:write name="item" property="campanhaCadastro.imovel.id" /> 
												</div>
											</td>
											<td  width="25%">
												<div align="center">
													<a title="${item.campanhaCadastro.nomeCliente}"><bean:write name="item" property="campanhaCadastro.nomeClienteAbreviado" /></a> 
												</div>
											</td>
											<td  width="15%">
												<div align="center">
													<bean:write name="item" property="campanhaCadastro.numeroCPFCNPJ" /> 
												</div>
											</td>
											<td  width="10%">
												<div align="center">
													
													<bean:write name="item" property="campanhaPremio.descricaoAbreviada" /> 
												</div>
											</td>
											<td  width="10%">
												<div align="center">
													<bean:write name="item" property="campanhaPremio.numeroOrdemPremiacao" /> 
												</div>
											</td>
											<td  width="15%">
												<div align="center">
													<bean:write name="item" property="campanhaSorteio.dataSorteioFormatada" /> 
												</div>
											</td>
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
							
	
<!-- 		<br/> -->
<!-- 		<table width="100%" border="0"> -->
				
<!-- 				<tr> -->
<!-- 					<td><strong>Campanha:<font color="#FF0000">*</font></strong></td> -->
<!-- 					<td> -->
<%-- 						<html:select onchange="validaCampanha();" property="idCampanha" tabindex="1"> --%>
<%-- 							<html:option value="<%=""+ConstantesSistema.VALOR_NAO_INFORMADO%>">&nbsp;</html:option> --%>
<%-- 							<html:options collection="colecaoCampanhas" labelProperty="dsTituloCampanha" property="id" /> --%>
<%-- 						</html:select> --%>
<!-- 					</td> -->
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 					<td><strong>Periodo do Sorteio:</strong></td> -->
<!-- 					<td > -->
						
<%-- 						<html:text  --%>
<%-- 							onkeyup="validaMudancaDataInicialSorteio();" --%>
<%-- 							onblur="validaMudancaDataInicialSorteio();" --%>
<%-- 							property="dataInicialSorteio" size="10" maxlength="10" />  --%>
<!-- 							<a href="javascript:abrirCalendarioReplicando('FiltrarCampanhaPremiacaoActionForm', 'dataInicialSorteio', 'dataFinalSorteio')"> -->
<!-- 							<img border="0" width="16" height="15" -->
<%-- 							src="<bean:message key="caminho.imagens"/>calendario.gif" --%>
<!-- 							width="20" border="0" align="absmiddle" alt="Exibir Calendário" /> -->
<!-- 							</a> -->
					
					
<!-- 						&nbsp;<strong> a </strong>&nbsp; -->
<%-- 						<html:text  --%>
<%-- 						onkeyup="mascara_data(form.dataFinalSorteio);" --%>
<%-- 						onblur="javascript:verifica_tamanho_data(this);" --%>
<%-- 						property="dataFinalSorteio" size="10" maxlength="10" /> --%>
<!-- 						<a href="javascript:abrirCalendario('FiltrarCampanhaPremiacaoActionForm', 'dataFinalSorteio')"> -->
<!-- 							<img border="0" width="16" height="15" -->
<%-- 							src="<bean:message key="caminho.imagens"/>calendario.gif" --%>
<!-- 							width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a> -->
<!-- 					</td> -->
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 					<td valign="top"><strong>Prêmio:</strong></td> -->
<!-- 					<td> -->
<%-- 					<html:select property="idPremio" style="width: 350px;" --%>
<%-- 						multiple="true" tabindex="13"> --%>
<%-- 						<logic:present name="colecaoPremios" scope="session"> --%>
<%-- 						<html:option  --%>
<%-- 							value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>  --%>
						
<%-- 							<html:options collection="colecaoPremios" --%>
<%-- 								labelProperty="descricao" property="id" /> --%>
<%-- 						</logic:present> --%>
<%-- 					</html:select> --%>
<!-- 				</td> -->
<!-- 				</tr> -->
<%-- 				<logic:present name="indicadorTemPremiacaoPorUnidade"> --%>
<%-- 					<logic:equal name="indicadorTemPremiacaoPorUnidade" value="<%=ConstantesSistema.ATIVO%>"> --%>
<!-- 						<tr> -->
<!-- 							<td><strong>Premiação:<font color="#FF0000">*</font></strong></td> -->
<!-- 							<td > -->
<%-- 		 						 <html:radio onclick="validaMudancaPermiacao();" property="premiacaoTipo"  value="<%=ConstantesSistema.PREMIACAO_DA_UNIDADE%>" /><strong>Da Unidade --%>
<%-- 								 <html:radio onclick="validaMudancaPermiacao();" property="premiacaoTipo"  value="<%=ConstantesSistema.PREMIACAO_GLOBAL%>" />Global --%>
<%-- 								 <html:radio onclick="validaMudancaPermiacao();" property="premiacaoTipo"  value="<%=ConstantesSistema.PREMIACAO_AMBAS%>" />Ambas</strong> --%>
		 								
<!-- 							</td> -->
<!-- 						</tr> -->
<%-- 					</logic:equal> --%>
<%-- 				</logic:present> --%>
				
<%-- 				<logic:present name="indicadorUnidadePremiacao"> --%>
<%-- 					<logic:equal name="indicadorUnidadePremiacao" value="<%=ConstantesSistema.INDICADOR_UNIDADE_PREMIACAO_POR_GERENCIA_REGIONAL%>"> --%>
<!-- 						<tr> -->
<!-- 							<td><strong>Gerência Regional:<font color="#FF0000">*</font></strong></td> -->
<!-- 							<td> -->
<%-- 								<html:select property="idGerenciaRegional" style="width: 350px;" --%>
<%-- 									multiple="true" tabindex="13"> --%>
<%-- 									<html:option  --%>
<%-- 										value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option> --%>
<%-- 									<logic:present name="colecaoGerenciaRegional" scope="session"> --%>
<%-- 										<html:options collection="colecaoGerenciaRegional" --%>
<%-- 											labelProperty="nome" property="id" /> --%>
<%-- 									</logic:present> --%>
<%-- 								</html:select> --%>
<!-- 							</td> -->
<!-- 						</tr> -->
<%-- 					</logic:equal> --%>
				
<%-- 					<logic:equal name="indicadorUnidadePremiacao" value="<%=ConstantesSistema.INDICADOR_UNIDADE_PREMIACAO_POR_UNIDADE_NEGOCIO%>"> --%>
<!-- 						<tr> -->
<!-- 							<td><strong>Unidade de Negócio:<font color="#FF0000">*</font></strong></td> -->
<!-- 							<td> -->
<%-- 								<html:select property="idUnidadeNegocio" style="width: 350px;" --%>
<%-- 									multiple="true" tabindex="13"> --%>
<%-- 									<html:option  --%>
<%-- 										value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option> --%>
<%-- 									<logic:present name="colecaoUnidadeNegocio" scope="session"> --%>
<%-- 										<html:options collection="colecaoUnidadeNegocio" --%>
<%-- 											labelProperty="nome" property="id" /> --%>
<%-- 									</logic:present> --%>
<%-- 								</html:select> --%>
<!-- 							</td> -->
<!-- 						</tr> -->
<%-- 					</logic:equal> --%>
				
<%-- 					<logic:equal name="indicadorUnidadePremiacao" value="<%=ConstantesSistema.INDICADOR_UNIDADE_PREMIACAO_POR_ELO%>"> --%>
<!-- 						<tr> -->
<!-- 							<td><strong>Elo:<font color="#FF0000">*</font></strong></td> -->
<!-- 							<td> -->
<%-- 								<html:select property="idElo" style="width: 350px;" --%>
<%-- 									multiple="true" tabindex="13"> --%>
<%-- 									<html:option  --%>
<%-- 										value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option> --%>
<%-- 									<logic:present name="colecaoElo" scope="session"> --%>
<%-- 										<html:options collection="colecaoElo" --%>
<%-- 											labelProperty="descricao" property="id" /> --%>
<%-- 									</logic:present> --%>
<%-- 								</html:select> --%>
<!-- 							</td> -->
<!-- 						</tr> -->
<%-- 					</logic:equal> --%>
				
<%-- 					<logic:equal name="indicadorUnidadePremiacao" value="<%=ConstantesSistema.INDICADOR_UNIDADE_PREMIACAO_POR_LOCALIDADE%>"> --%>
<!-- 							<tr> -->
<!-- 								<td><strong>Localidade:<font color="#FF0000">*</font></strong></td> -->
<!-- 								<td> -->
<%-- 									<html:select property="idLocalidade" style="width: 350px;" --%>
<%-- 										multiple="true" tabindex="13"> --%>
<%-- 										<html:option  --%>
<%-- 											value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option> --%>
<%-- 										<logic:present name="colecaoLocalidade" scope="session"> --%>
<%-- 											<html:options collection="colecaoLocalidade" --%>
<%-- 												labelProperty="descricao" property="id" /> --%>
<%-- 										</logic:present> --%>
<%-- 									</html:select> --%>
<!-- 								</td> -->
<!-- 							</tr> -->
<%-- 						</logic:equal> --%>
<%-- 					</logic:present> --%>
<table width="100%" border="0">
				<tr>
					<td>
						<strong> 
							<font color="#ff0000"> 
								<input	name="Submit22" 
										class="bottonRightCol"
										value="Voltar" 
										type="button"
										onclick="window.location.href='/gsan/ExibirManterCampanhaPremiacaoAction.do';">
								<input type="button"
										name="ButtonCancelar" 
										class="bottonRightCol" 
										value="Cancelar"
										onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
									<input type="button"
										name="ButtonDesfazer" 
										class="bottonRightCol" 
										value="Desfazer"
										onClick="javascript:window.location.href='/gsan/ExibirFiltrarCampanhaPremiacaoAction.do?menu=sim'">
							</font>
						</strong>
					</td>
					
				</tr>
			</table>
			
			</td>
			
			</tr>
			</table>
			
			</td>
				<%@ include file="/jsp/util/rodape.jsp"%>
			</table>
	</html:form>
</body>
</html>