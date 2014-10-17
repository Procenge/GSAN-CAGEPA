<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>

<%@ page import="java.util.Collection,gcom.util.ConstantesSistema"%>
<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
</head>

<body leftmargin="5" topmargin="5" onload="javascript:resizePageSemLink(810, 405)">

<table width="770" border="0" cellspacing="4" cellpadding="0">
	<tr>
		<td width="770" valign="top" class="centercoltext">
		<table height="100%">
			<tr>
				<td></td>
			</tr>
		</table>
		
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
				<td class="parabg">Pesquisa de Boleto Banc&oacute;rio</td>
				<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
			</tr>
		</table>
		<p>&nbsp;</p>
		<table width="100%" border="0" bgcolor="#90c7fc">
			<tbody>
			<tr align="left" bgcolor="#90c7fc">
				<td width="5%" align="center"><strong>Arrecadador</strong></td>
				<td width="20%" align="center"><strong>Nosso Número</strong></td>
				<td width="15%"  align="center"><strong>Data de Entrada</strong></td>
				<td width="15%" align="center"><strong>Data de Vencimento </strong></td>
				<td width="15%" align="center"><strong>Valor</strong></td>
				<td width="30%" align="center"><strong>Situação Atual</strong></td>
			</tr>
			<%String cor = "#cbe5fe";%>
			<%--Esquema de paginação--%>
			<pg:pager isOffset="true" index="half-full" maxIndexPages="10"
				export="currentPageNumber=pageNumber;pageOffset"
				maxPageItems="10" items="${sessionScope.totalRegistros}">
				<pg:param name="pg" />
				<pg:param name="q" />

				<logic:iterate name="colecaoBoletoSituacaoHistorico" id="boletoBancarioSituacaoHistorico">
					<pg:item>
						<%if (cor.equalsIgnoreCase("#cbe5fe")) {
					cor = "#FFFFFF";%>
									<tr bgcolor="#FFFFFF">
									<%} else {
					cor = "#cbe5fe";%>
									<tr bgcolor="#cbe5fe">
										<%}%>
							<td>
							<logic:notEmpty	name="boletoBancarioSituacaoHistorico" property="boletoBancario.arrecadador">
								<bean:write name="boletoBancarioSituacaoHistorico" property="boletoBancario.arrecadador.codigoAgente" /> 
							</logic:notEmpty>	
							</td>
							
							<td>
							  <logic:notEmpty name="boletoBancarioSituacaoHistorico" property="boletoBancario.convenio">
							    <logic:notEmpty name="boletoBancarioSituacaoHistorico" property="boletoBancario.numeroSequencial">

									<logic:notEmpty 
										name="caminhoRetornoTelaPesquisaboletoBancario">
										<a
											href="javascript:enviarDadosParametros('<bean:write name="caminhoRetornoTelaPesquisaBoletoBancario"/>','<bean:write name="boletoBancarioSituacaoHistorico" property="boletoBancario.numeroSequencial"/>', '<bean:write name="boletoBancarioSituacaoHistorico" property="boletoBancario.numeroSequencial"/>', 'numeroBoleto');">
										<bean:write name="boletoBancarioSituacaoHistorico" property="boletoBancario.convenio" /><bean:write
											name="boletoBancarioSituacaoHistorico" property="boletoBancario.numeroSequencial" /> </a>
									</logic:notEmpty>
								
									<logic:empty name="caminhoRetornoTelaPesquisaboletoBancario">
									<logic:notEmpty  name="boletoBancarioSituacaoHistorico" property="boletoBancario.arrecadador">
										<a
											href="javascript:enviarDados('<bean:write name="boletoBancarioSituacaoHistorico" property="boletoBancario.numeroSequencial"/>', '<bean:write name="boletoBancarioSituacaoHistorico" property="boletoBancario.numeroSequencial"/>', 'numeroBoleto');enviarDados('<bean:write name="boletoBancarioSituacaoHistorico" property="boletoBancario.arrecadador.codigoAgente"/>', '${boletoBancarioSituacaoHistorico.boletoBancario.arrecadador.cliente.nome}', 'arrecadador');">
										<bean:write name="boletoBancarioSituacaoHistorico" property="boletoBancario.convenio" /><bean:write
											name="boletoBancarioSituacaoHistorico" property="boletoBancario.numeroSequencial" /> </a>
									</logic:notEmpty>
									</logic:empty>

								</logic:notEmpty> 
							 </logic:notEmpty> 
							  
							  <logic:empty name="boletoBancarioSituacaoHistorico" property="boletoBancario.convenio">
	    	 					  &nbsp;
	    	 					<logic:empty name="boletoBancarioSituacaoHistorico" property="boletoBancario.numeroSequencial">
	    	 					  &nbsp;
	    					  	</logic:empty>
	    					  </logic:empty>
	    					</td>
	    					
	    					<td align="center">
							  <bean:write name="boletoBancarioSituacaoHistorico" property="dataEntrada" formatKey="date.format"/>
							</td>
							
							<td align="center">
							  <bean:write name="boletoBancarioSituacaoHistorico" property="boletoBancario.dataVencimento" formatKey="date.format"/>
							</td>
							
							<td>
							  <bean:write name="boletoBancarioSituacaoHistorico" property="boletoBancario.valorDebito" />
							</td>
	    					
							<td align="center">
							  <logic:notEmpty name="boletoBancarioSituacaoHistorico" property="boletoBancario.boletoBancarioSituacao">
								<bean:write name="boletoBancarioSituacaoHistorico" property="boletoBancario.boletoBancarioSituacao.descricao" />
							  </logic:notEmpty> 
							  <logic:empty name="boletoBancarioSituacaoHistorico" property="boletoBancario.boletoBancarioSituacao">
	    	 					&nbsp;
	    					  </logic:empty>
	    					</td>
	    					
						</tr>
					</pg:item>
				</logic:iterate>
				</tbody>
		</table>
		<table width="100%" border="0">
			<tr>
				<td>
				<div align="center"><strong><%@ include
				file="/jsp/util/indice_pager_novo.jsp"%></strong></div>
				</td>
			</tr>
			</pg:pager><%-- Fim do esquema de paginação --%>
			
			<tr>
				<td height="24"><input type="button" class="bottonRightCol"	value="Voltar"	onclick="window.location.href='<html:rewrite page="/exibirPesquisarBoletoBancarioAction.do"/>'" />
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html:html>
