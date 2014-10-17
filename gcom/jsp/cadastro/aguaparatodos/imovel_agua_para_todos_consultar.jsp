<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@page isELIgnored="false"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page import="gcom.cadastro.aguaparatodos.ImovelAguaParaTodos"%><html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="ConsultarImovelAguaParaTodosActionForm" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>


<script language="JavaScript">
function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

    var form = document.forms[0];

    if (tipoConsulta == 'imovel') {
      form.matricula.value = codigoRegistro;
      pesquisarImovelAguaParaTodos();
    }
 }

function pesquisarImovelAguaParaTodos() {
	var form = document.forms[0];
	redirecionarSubmit("exibirConsultarImovelAguaParaTodosAction.do?matriculaImovel="+form.matricula.value);	
}

function pesquisarImovelAguaParaTodosEnter() {

	var form = document.forms[0];
	validaEnter('event', 'exibirConsultarImovelAguaParaTodosAction.do?matriculaImovel='+form.matricula.value,'matricula');
}

</script>
</head>


<body leftmargin="5" topmargin="5" onload="">
<html:form action="/exibirConsultarImovelAguaParaTodosAction.do"
	name="ConsultarImovelAguaParaTodosActionForm"
	type="gcom.gui.cadastro.aguaparatodos.ConsultarImovelAguaParaTodosActionForm"
	method="post">

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
					<td class="parabg">Consultar Programa Água para Todos</td>
					<td width="11" valign="top"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>

			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para Consultar o Programa Água para todos, informe os dados abaixo:</td>
				</tr>
				
				<tr>
					<td width="20%">
						<strong>Matrícula do Imóvel:</strong>
					</td>
					<td width="100%">
							<html:text property="matricula" maxlength="9" size="9" tabindex="0" onkeypress="javascript:pesquisarImovelAguaParaTodosEnter();"/>
							<a href="javascript:abrirPopup('exibirPesquisarImovelAction.do', 400, 800);">
 							<img width="23" height="21" border="0" title="Pesquisar Imóvel."
 								src="<bean:message key="caminho.imagens"/>pesquisa.gif"/></a>
					</td>
				</tr>
				<table width="100%" border="0">
					<tr>
						<td>
							<logic:notEqual name="ConsultarImovelAguaParaTodosActionForm" property="flagOperacao" value="0">
							<hr>
							<table width="100%" border="0">
								<tr>
								  <td><strong>Localização:</strong></td>						
								  <td colspan="3">
								  	<html:hidden property="localidade"/>
									<bean:write name="ConsultarImovelAguaParaTodosActionForm" property="localidade"/>
								  </td>
								  <td><strong>Sit. Água:</strong></td>	
								  <td colspan="3">
									<html:hidden property="situacaoAgua"/>
									<bean:write name="ConsultarImovelAguaParaTodosActionForm" property="situacaoAgua"/>
								  </td>
								</tr>
								<tr>
								  <td><strong>Inscrição:</strong></td>						
								  <td colspan="3">
									<html:hidden property="inscricao"/>
									<bean:write name="ConsultarImovelAguaParaTodosActionForm" property="inscricao"/>
								  </td>
								  <td><strong>Sit. Esgoto:</strong></td>						
								  <td colspan="3">
									<html:hidden property="situacaoEsgoto"/>
									<bean:write name="ConsultarImovelAguaParaTodosActionForm" property="situacaoEsgoto"/>
								  </td>
								</tr>
								<tr>
								  <td><strong>Nome:</strong></td>						
								  <td colspan="3">
									<html:hidden property="nomeCliente"/>
									<bean:write name="ConsultarImovelAguaParaTodosActionForm" property="nomeCliente"/>
								  </td>
								  <td><strong>Telefone:</strong></td>						
								  <td colspan="3">
									<html:hidden property="telefone"/>
									<bean:write name="ConsultarImovelAguaParaTodosActionForm" property="telefone"/>
								  </td>
								</tr>
								<tr>
								  <td><strong>Endereço:</strong></td>						
								  <td colspan="3" width="350">
									<html:hidden property="endereco"/>
									<bean:write name="ConsultarImovelAguaParaTodosActionForm" property="endereco"/>
								  </td>
								</tr>
								<tr>
									<td colspan="1"><strong>Economias:</strong></td>						
									<td>
										<html:hidden property="categoriasEconomia"/>
										<bean:write name="ConsultarImovelAguaParaTodosActionForm" property="categoriasEconomia"/>
									</td>
								</tr>
							</table>
							<hr>
							<table width="100%" cellpadding="0" cellspacing="0">
								<tr>
									<td height="0">
									<table width="100%" bgcolor="#90c7fc">
										<!--header da tabela interna -->
										<tr bordercolor="#FFFFFF" bgcolor="#90c7fc">
											<td width="20%" align="center"><strong>NIC</strong></td>
											<td width="50%" align="center"><strong>Nome Contribuinte</strong></td>
											<td width="30%" align="center"><strong>Situação</strong></td>
										</tr>
									</table>
									</td>
								</tr>
								<tr>
									<td height="130">
									<div style="width: 100%; height: 100%; overflow: auto;">
									<table width="100%" align="left" bgcolor="#99CCFF">
										<!--corpo da segunda tabela-->
										<%int cont = 0;%>
										<logic:notEmpty name="ConsultarImovelAguaParaTodosActionForm" property="colecaoImovelAguaParaTodos">
											<logic:iterate name="ConsultarImovelAguaParaTodosActionForm" property="colecaoImovelAguaParaTodos" id="imovelAguaParaTodos" type="ImovelAguaParaTodos">
												<%cont = cont + 1;
												if (cont % 2 == 0) {%>
													<tr bgcolor="#cbe5fe">
												<%} else {%>
													<tr bgcolor="#FFFFFF">
												<%}%>
													<td width="20%" align="center">
														<div><a href="javascript:abrirPopupComSubmit('consultarImovelAguaParaTodosAction.do?id=
																	<bean:write name="imovelAguaParaTodos" property="id"/>', 350, 750, 'Teste');">
																			${imovelAguaParaTodos.idContribuinte}
															 </a>
														</div>
													</td>
													<td width="50%" align="center">
														<div>${imovelAguaParaTodos.nomeParticipante}</div>
													</td>
													<td width="30%" align="center">
														<logic:equal name="imovelAguaParaTodos" property="codigoSituacao" value="9">
															<div>Imóvel ainda não habilitado</div>
														</logic:equal>
														<logic:equal name="imovelAguaParaTodos" property="codigoSituacao" value="0">
															<div>Imóvel habilitado</div>
														</logic:equal>
														<logic:equal name="imovelAguaParaTodos" property="codigoSituacao" value="1">
															<div>Excluído</div>
														</logic:equal>
													</td>										
												</tr>
											</logic:iterate>
										</logic:notEmpty>
									</table>
									</div>
									</td>
								</tr>
							</table>
							</logic:notEqual>
						</td>
					</tr>
					<tr>
					</tr>
					<tr>
					<td align="left">
						<input name="Button" type="button" class="bottonRightCol" value="Limpar" align="left"
						onclick="window.location.href='/gsan/exibirConsultarImovelAguaParaTodosAction.do?menu=sim'">
						<input type="button"name="ButtonCancelar" class="bottonRightCol" value="Cancelar" 
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
					</td>
					</tr>
				</table>
				
			</table>
			</td>
		</tr>
	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>
	
</html:form>



</body>
</html:html>