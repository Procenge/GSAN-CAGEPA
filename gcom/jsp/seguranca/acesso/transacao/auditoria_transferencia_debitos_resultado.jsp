<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>
<%@ page import="gcom.util.ConstantesSistema"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
	
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>	
</head>
<body leftmargin="5" topmargin="5">
	<html:form action="/consultarAuditoriaTransferenciaDebitosAction.do"
	   name="ConsultarAuditoriaTransferenciaDebitosActionForm"
	   type="gcom.gui.seguranca.acesso.transacao.ConsultarAuditoriaTransferenciaDebitosActionForm"
	   method="post" >
	   
	   <table>
				   	<tr>
				   		<td><strong>Matrícula Funcionário: <c:out value="${ConsultarAuditoriaTransferenciaDebitosActionForm.idFuncionario}" /></strong></td>
				   		<td><strong>Data Inicial: <c:out value="${ConsultarAuditoriaTransferenciaDebitosActionForm.dataInicial}" /></strong></td>
				   	</tr>
				   	<tr>
				   		<td><strong>Matrícula Usuário Origem: <c:out value="${ConsultarAuditoriaTransferenciaDebitosActionForm.idUsuarioOrigem}" /></strong></td>
				   		
				   		<td><strong>Data Final: <c:out value="${ConsultarAuditoriaTransferenciaDebitosActionForm.dataFinal}" /></strong></td>
				   	</tr>
				   	<tr>
				   	<td><strong>Matrícula Usuário Destino: <c:out value="${ConsultarAuditoriaTransferenciaDebitosActionForm.idUsuarioDestino}" /></strong></td>
				   	</tr>
				   	<tr>
				   		<td>&nbsp;</td>
						<td>&nbsp;</td>
				   	</tr>
	  				<tr bgcolor="#cbe5fe">
					<td align="center" colspan="2">
								<div style="width: 590px; height: 250; overflow: auto;">
								<table width="100%" border="0" bgcolor="#99CCFF">
<!-- 								Linha 1 -->
								<tr bgcolor="#cbe5fe" bordercolor="#000000">
									<td width="10%" bgcolor="#90c7fc">
										<div align="center"><strong>Código</strong></div>
									</td>
		
									<td width="10%" bgcolor="#90c7fc">
										<div align="center"><strong>Data</strong></div>
									</td>
									<td width="10%" bgcolor="#90c7fc">
										<div align="center"><strong>Hora</strong></div>
									</td>
									<td width="10%" bgcolor="#90c7fc">
										<div align="center"><strong>Referência Inicial</strong></div>
									</td>
									<td width="10%" bgcolor="#90c7fc">
										<div align="center"><strong>Referência Final</strong></div>							
									</td>
									<td width="10%" bgcolor="#90c7fc">
										<div align="center"><strong>Usuário Origem</strong></div>
									</td>
									<td width="10%" bgcolor="#90c7fc">
										<div align="center"><strong>Usuário Destino</strong></div>							
									</td>
									
									<td width="5%" bgcolor="#90c7fc">
										<div align="center"><strong>Total Prestação</strong></div>							
									</td>
																	
								</tr>
<!-- 								Linha 2 -->
								<tr>
								<td width="10%" bgcolor="#90c7fc">
										<div align="center"><strong>Valor Entrada</strong></div>							
									</td>	
									<td width="10%" bgcolor="#90c7fc">
										<div align="center"><strong>Valor Prestação</strong></div>
									</td>
		
									<td width="10%" bgcolor="#90c7fc">
										<div align="center"><strong>Valor Débito Histórico</strong></div>
									</td>
									<td colspan="2" width="10%" bgcolor="#90c7fc">
										<div align="center"><strong>Valor Débito Atual</strong></div>
									</td>
									<td colspan="2" width="15%" bgcolor="#90c7fc">
										<div align="center"><strong>Valor Sacado Incluso</strong></div>							
									</td>		
									<td colspan="2" width="15%" bgcolor="#90c7fc">
										<div align="center"><strong>Valor Parcelado</strong></div>							
									</td>						
									
								</tr>
								<logic:present name="colecao">
									<%int cont = 0;%>
									<logic:iterate name="colecao" id="colecao">
										<%cont = cont + 1;
										if (cont % 2 == 0) {%>
										<tr bgcolor="#cbe5fe">
											<%} else { %>
										<tr bgcolor="#FFFFFF">
											<%}%>
											<td width="10%" align="center">${colecao.codigo}</td>
											<td width="10%" align="center">${colecao.dataT}</td>
											<td width="10%" align="center">${colecao.hora}</td>
											<td width="10%" align="center">${colecao.referenciaInicial}</td>	
											<td width="10%" align="center">${colecao.referenciaFinal}</td>
											<td width="15%" align="center">${colecao.usuarioOrigem}</td>
											<td width="15%" align="center">${colecao.usuarioDestino}</td>
											<td width="5%" align="center">${colecao.totalPrestacao}</td>
																		
										</tr>
										<%if (cont % 2 == 0) {%>
										<tr bgcolor="#cbe5fe"> 
											<%} else { %>
										<tr bgcolor="#FFFFFF">
											<%}%>
											<td width="10%" align="center">${colecao.valorEntrada}</td>
											<td width="10%" align="center">${colecao.valorPrestacao}</td>
											<td width="10%" align="center" >${colecao.valorDebitoHistorico}</td>
											<td width="30%" align="center" colspan="2">${colecao.valorDebitoAtual}</td>
											<td width="10%" align="center" colspan="2">${colecao.valorSacadoIncluso}</td>	
											<td width="10%" align="center">${colecao.valorParcelado}</td>							
										</tr>
										
									</logic:iterate>
								</logic:present>
							</table>
						</div>
					</tr>
				<tr>
					<td>
						<strong> 
							<font color="#ff0000"> 
								<input	name="Submit22" 
										class="bottonRightCol"
										value="Voltar"
										tabindex="6" 
										type="button"
										onclick="window.location.href='/gsan/exibirConsultarAuditoriaTransferenciaDebitosAction.do?reset=1';">
							</font>
						</strong>
					</td>
				</tr>
			</table>
	</html:form>
</body>
</html>