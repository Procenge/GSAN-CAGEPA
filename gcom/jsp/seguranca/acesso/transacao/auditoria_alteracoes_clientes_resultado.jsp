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
	<html:form action="/consultarAuditoriaAlteracoesClientesAction.do"
	   name="ConsultarAuditoriaAlteracoesClientesActionForm"
	   type="gcom.gui.seguranca.acesso.transacao.ConsultarAuditoriaAlteracoesClientesActionForm"
	   method="post" >
	   
	   <table>
				   	<tr>
				   		<td><strong>Matrícula Funcionário: <c:out value="${ConsultarAuditoriaAlteracoesClientesActionForm.idFuncionario}" /></strong></td>
				   		<td><strong>Data Inicial: <c:out value="${ConsultarAuditoriaAlteracoesClientesActionForm.dataInicial}" /></strong></td>
				   	</tr>
				   	<tr>
				   		<td><strong>Matrícula Usuário: <c:out value="${ConsultarAuditoriaAlteracoesClientesActionForm.idUsuario}" /></strong></td>
				   		<td><strong>Data Final: <c:out value="${ConsultarAuditoriaAlteracoesClientesActionForm.dataFinal}" /></strong></td>
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
								<td width="120px" bgcolor="#90c7fc">
									<div align="center">
										<strong>Descrição do Campo</strong>
									</div>
								</td>

								<td width="80px" bgcolor="#90c7fc">
									<div align="center">
										<strong>Conteúdo Atual</strong>
									</div>
								</td>
								<td width="80px" bgcolor="#90c7fc">
									<div align="center">
										<strong>Conteúdo Anterior</strong>
									</div>
								</td>
								<td width="80px" bgcolor="#90c7fc">
									<div align="center">
										<strong>Código</strong>
									</div>
								</td>
								<td width="80px" bgcolor="#90c7fc">
									<div align="center">
										<strong>Data</strong>
									</div>
								</td>

								<td width="80px" bgcolor="#90c7fc">
									<div align="center">
										<strong>Hora</strong>
									</div>
								</td>

							</tr>


							<logic:present name="colecao">
								<%
									int cont = 0;
								%>
								<logic:iterate name="colecao" id="colecao">
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
										<td width="120px" align="center">${colecao.descricaoCampo}</td>
										<td width="80px" align="center">${colecao.conteudoAtual}</td>
										<td width="80px" align="center">${colecao.conteudoAnterior}</td>
										<td width="80px" align="center">${colecao.codigo}</td>
										<td width="80px" align="center">${colecao.data}</td>
										<td width="80px" align="center">${colecao.hora}</td>
									</tr>
								</logic:iterate>
							</logic:present>
						</table>
					</div></tr>
	  
				<tr>
					<td>
						<strong> 
							<font color="#ff0000"> 
								<input	name="Submit22" 
										class="bottonRightCol"
										value="Voltar"
										tabindex="6" 
										type="button"
										onclick="window.location.href='/gsan/exibirConsultarAuditoriaAlteracoesClientesAction.do?reset=1';">
							</font>
						</strong>
					</td>
				</tr>
			</table>
	</html:form>
</body>
</html>