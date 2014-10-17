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
	<html:form action="/consultarAuditoriaCancelamentoRevisaoContasAction.do"
	   name="ConsultarAuditoriaCancelamentoRevisaoContasActionForm"
	   type="gcom.gui.seguranca.acesso.transacao.ConsultarAuditoriaCancelamentoRevisaoContasActionForm"
	   method="post" >
	   
	   <table>
				   	<tr>
				   		<td><strong>Matrícula Funcionário: <c:out value="${ConsultarAuditoriaCancelamentoRevisaoContasActionForm.idFuncionario}" /></strong></td>
				   		<td><strong>Data Inicial: <c:out value="${ConsultarAuditoriaCancelamentoRevisaoContasActionForm.dataInicial}" /></strong></td>
				   	</tr>
				   	<tr>
				   		<td><strong>Matrícula Usuário: <c:out value="${ConsultarAuditoriaCancelamentoRevisaoContasActionForm.idUsuario}" /></strong></td>
				   		<td><strong>Data Final: <c:out value="${ConsultarAuditoriaCancelamentoRevisaoContasActionForm.dataFinal}" /></strong></td>
				   	</tr>
				   	<tr>
				   		<td>&nbsp;</td>
						<td>&nbsp;</td>
				   	</tr>
	  				<tr bgcolor="#cbe5fe">
					<td align="center" colspan="2">
								<div style="width: 590px; height: 250; overflow: auto;">
								<table width="100%" border="0" bgcolor="#99CCFF">
															<tr bgcolor="#cbe5fe" bordercolor="#000000">
									<td width="35%" bgcolor="#90c7fc">
										<div align="center"><strong>Descrição da Operação</strong></div>
									</td>
		
									<td width="10%" bgcolor="#90c7fc">
										<div align="center"><strong>Referência</strong></div>
									</td>
									<td width="10%" bgcolor="#90c7fc">
										<div align="center"><strong>Motivo</strong></div>
									</td>
									<td width="15%" bgcolor="#90c7fc">
										<div align="center"><strong>Código</strong></div>
									</td>
									<td width="15%" bgcolor="#90c7fc">
										<div align="center"><strong>Data</strong></div>							
									</td>
									
									<td width="15%" bgcolor="#90c7fc">
										<div align="center"><strong>Hora</strong></div>							
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
											<td width="35%" align="center">${colecao.mctcdoper}</td>
											<td width="10%" align="center">${colecao.mctiamref}</td>
											<td width="10%" align="center">${colecao.mcticdmotivo}</td>
											<td width="15%" align="center">${colecao.mctcodigo}</td>	
											<td width="15%" align="center">${colecao.mctdata}</td>
											<td width="15%" align="center">${colecao.mcthora}</td>								
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
										onclick="window.location.href='/gsan/exibirConsultarAuditoriaCancelamentoRevisaoContasAction.do?reset=1';">
							</font>
						</strong>
					</td>
				</tr>
			</table>
	</html:form>
</body>
</html>