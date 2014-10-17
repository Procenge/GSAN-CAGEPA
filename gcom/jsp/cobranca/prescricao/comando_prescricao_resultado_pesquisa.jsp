<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript">
	
function voltar(){
      
	var form = document.forms[0];
    form.action = '/gsan/exibirPesquisarComandoPrescricaoAction.do';
   	form.submit();		
}

function visualizarParametros(idPrescricaoComando){
       
	var form = document.forms[0];
    form.action = 'exibirResultadoPesquisaComandoPrescricaoParametrosAction.do?idPrescricaoComando='+idPrescricaoComando;
    form.submit();		
}

	
</script>

</head>
<body leftmargin="5" topmargin="5" onload="resizePageSemLink(800,500);">
<form method="post">
<table width="770" border="0" cellspacing="4" cellpadding="0">
	<tr>
		<td width="770" valign="top" class="centercoltext">
		<table height="100%">
			<tr>
				<td></td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="11"><img src="imagens/parahead_left.gif"
					border="0" /></td>
				<td class="parabg">Comandos de Prescrição</td>
				<td width="11"><img src="imagens/parahead_right.gif"
					border="0" /></td>
			</tr>
		</table>
		<p>&nbsp;</p>

		<table width="100%" border="0" bgcolor="#79bbfd" align="center"
			cellpadding="0" cellspacing="0">
			<!--header da tabela interna -->
			<tr bgcolor="#79bbfd" height="18">
				<td align="center" class="style11"><strong>Comandos de Prescrição</strong></td>
			</tr>
		</table>

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td height="22" class="style1">
				<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0">
					<tr>
						<td height="0">


						<table width="100%" bgcolor="#90c7fc">
							<tr bgcolor="#90c7fc">
								
								<td width="15%" bgcolor="#90c7fc">
									<div align="center"><strong>Título</strong></div>
								</td>
								
								<td width="16%" bgcolor="#90c7fc">
									<div align="center"><strong>Data e Hora de Gera&ccedil;&atilde;o
										do Comando</strong></div>
								</td>
								
								<td width="16%" bgcolor="#90c7fc">
									<div align="center"><strong>Data e Hora de
										Realização do Comando</strong></div>
								</td>
								
								<td width="20%" bgcolor="#90c7fc">
									<div align="center"><strong>Usu&aacute;rio que Gerou o Comando</strong></div>
								</td>
								
								<td width="11%" bgcolor="#90c7fc">
									<div align="center"><strong>Qtde. Imóveis</strong></div>
								</td>
								
								<td width="11%" bgcolor="#90c7fc">
									<div align="center"><strong>Qtde. Contas</strong></div>
								</td>
								
								<td width="11%" bgcolor="#90c7fc">
									<div align="center"><strong>Qtde. Guias</strong></div>
								</td>
								
							</tr>

							<%String cor = "#cbe5fe";%>

							<!--Fim Tabela Reference a Páginação da Tela-->
							<%--Esquema de paginação--%>
							<pg:pager isOffset="true" index="half-full" maxIndexPages="10"
								export="currentPageNumber=pageNumber;pageOffset"				
								maxPageItems="10" items="${sessionScope.totalRegistros}">

								<pg:param name="pg" />
								<pg:param name="q" />

								<logic:iterate name="colecaoPrescricaoComando"
									id="prescricaoComando">
									<pg:item>
										<%if (cor.equalsIgnoreCase("#cbe5fe")) {
												cor = "#FFFFFF";%>
										<tr bgcolor="#FFFFFF">
											<%} else {
												
												cor = "#cbe5fe";%>
										<tr bgcolor="#cbe5fe">
											<%}%>
											
											<td width="15%">
												<div align="center">
													<a title="'<bean:write name="prescricaoComando" property="titulo"/>'" href="javascript:enviarDados('<bean:write name="prescricaoComando" property="id"/>', '<bean:write name="prescricaoComando" property="titulo"/>', 'prescricaoComando');">
														<bean:write name="prescricaoComando" property="descricaoTituloAbreviado"/>
													</a>
												</div>
											</td>
											
											<td width="16%">
												<div align="center"><bean:write
													name="prescricaoComando"
													format="dd/MM/yyyy HH:mm:ss" property="dataComando" /></div>
											</td>
											
											<td width="16%">
												<div align="center"><bean:write
													name="prescricaoComando"
													format="dd/MM/yyyy HH:mm:ss" property="dataRealizacao" /></div>
											</td>
											
											<td width="20%">
												<div align="center">
													<logic:present
														name="prescricaoComando" property="usuario">
														<bean:define name="prescricaoComando"
														property="usuario" id="usuario" />
														<bean:write name="usuario" property="nomeUsuario" />
													</logic:present>
												</div>
											</td>
											
											<td width="11%">
												<div align="center">
													<a href="javascript:visualizarParametros(<bean:write name="prescricaoComando"
													property="id" />)"><bean:write
													name="prescricaoComando"
													property="quantidadeImoveis" /></a>
												</div>
											</td>
											
											<td width="11%">
												<div align="center">
													<bean:write
													name="prescricaoComando"
													property="quantidadeContasPrescritas" />
												</div>
											</td>
											
											<td width="11%">
												<div align="center">
													<bean:write
													name="prescricaoComando"
													property="quantidadeGuiasPrescritas" />
												</div>
											</td>

										</tr>
									</pg:item>

								</logic:iterate>
						</table>
						<table width="100%" border="0">
							<tr >
								<td align="center"><strong><%@ include file="/jsp/util/indice_pager_novo.jsp"%></strong>
								</td>
							</tr>
							
							</pg:pager><%-- Fim do esquema de paginação --%>
							
							<tr>
							<td height="24"><input type="button" name="Submit222"
								class="bottonRightCol" value="Voltar Pesquisa"
								onClick="javascript:voltar();">
							</tr>
						</table>

						</td>
					</tr>
				</table>
			
			
		</table>
		<p>&nbsp;</p>
		</td>
	</tr>
</table>
</form>
</body>
</html:html>

