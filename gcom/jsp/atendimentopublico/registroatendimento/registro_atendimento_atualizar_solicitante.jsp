<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="AtualizarRegistroAtendimentoActionForm"
	dynamicJavascript="false" />


<script> 

    function validateAtualizarRegistroAtendimentoActionForm(form) {                                                                     
      return true;  
   	} 

 function remover(campo){
 var form = document.forms[0];
 form.action = "atualizarRegistroAtendimentoWizardAction.do?action=exibirAtualizarRegistroAtendimentoDadosSolicitanteAction&idRASolicitante="+campo;
 form.submit();
 }

function consultarDebitos() {
	var form = document.forms[0];
	var idCliente = form.idSelecionado.value; //$("input[name=idSelecionado]:checked").val();
	if (!isNaN(idCliente) && idCliente.length > 0 && idCliente.indexOf(',') == -1 &&
			idCliente.indexOf('.') == -1 && ((idCliente * 1) > 0)){
		abrirPopup('consultarDebitoAction.do?ehPopup=true&tipoPesquisa=cliente&codigoCliente='+idCliente, 550, 735);
	}
}

function countChecked(objeto) {

	$("input[name=idSelecionado]:checkbox").each(function(index) {
	    $(this).attr('checked', false);
	});

	objeto.attr('checked', true);
}

$(document).ready(function(){
	$("input[name=idSelecionado]:checkbox").click(function () {
		countChecked($(this));
	});
});
	
  
</script>


</head>

<body leftmargin="5" topmargin="5">


<html:form action="/atualizarRegistroAtendimentoWizardAction"
	method="post">
	
	<html:hidden property="idImovel"/>

	<html:hidden property="idMunicipio"/>
	<html:hidden property="cdBairro"/>
	<html:hidden property="idBairroArea"/>
	<html:hidden property="especificacao"/>


	<jsp:include
		page="/jsp/util/wizard/navegacao_abas_wizard_valida_avancar.jsp?numeroPagina=3" />

	<html:hidden property="numeroRA" />

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="5" cellpadding="5">
		<tr>
			<td width="149" valign="top" class="leftcoltext">
			<div align="center">
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>

			<%@ include file="/jsp/util/informacoes_usuario.jsp"%>

			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>

			<%@ include file="/jsp/util/mensagens.jsp"%>

			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%>
			</div>
			</td>

			<td width="600" valign="top" class="centercoltext">

			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Atualizar RA - Registro de Atendimento</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%">
				<tr>
					<td colspan="3">

					<table width="100%" border="0">
					
				      <tr>
				      	<td colspan="2" HEIGHT="10" align="right">
				         	<input type="button" class="bottonRightCol" value="Consultar D&eacute;bitos" id="botaoConsultarDebitos" align="right" onclick="consultarDebitos();">	
				         </td>
				      </tr>
					
						<tr>
							<td><strong>Solicitantes encontrados:</strong></td>
							<td align="right"><input type="button" class="bottonRightCol"
								value="Adicionar Solicitante RA"
								onclick="abrirPopup('exibirAdicionarSolicitanteRegistroAtendimentoAction.do?idRegistroAtendimento='+ document.forms[0].numeroRA.value+'&objetoColecao=SIM', 570, 700);"></td>
						</tr>


						<tr>
							<td colspan="2"></td>
						</tr>
						<tr>
							<td colspan="2">
							<table width="100%" cellpadding="0" cellspacing="0">
								<tr>
									<td>
									<table width="100%" border="0" bgcolor="#99CCFF">
										<tr bgcolor="#99CCFF">
											<td width="5%" align="center"></td>
											<td width="10%" align="center"><strong>Remover</strong></td>
											<td width="10%" align="center"><strong>Principal</strong></td>
											<td width="29%" align="center"><strong>Nome Solicitante</strong></td>
											<td width="14%" align="center"><strong>Cód. Cliente</strong></td>
											<td width="32%" align="center"><strong>Unidade</strong></td>
										</tr>
									</table>
									</td>
								</tr>
								<tr>
									<td>
									<table width="100%" align="center" bgcolor="#99CCFF">
										<!--corpo da segunda tabela-->

										<%String cor = "#cbe5fe";%>

										<logic:iterate name="colecaoRASolicitante"
											id="registroAtendimentoSolicitante">

											<%if (cor.equalsIgnoreCase("#cbe5fe")) {
							cor = "#FFFFFF";%>
											<tr bgcolor="#FFFFFF" >
												<%} else {
							cor = "#cbe5fe";%>
											<tr bgcolor="#cbe5fe" >
												<%}%>

												<td width="5%" align="center">
													<logic:notEmpty name="registroAtendimentoSolicitante" property="cliente">
														<input type="checkbox" name="idSelecionado" value="<bean:write name="registroAtendimentoSolicitante" property="cliente.id" />" >
													</logic:notEmpty>
												</td>

												<td width="10%" align="center"><a
													href="javascript:if(confirm('Confirma remoção?')){remover(${registroAtendimentoSolicitante.ultimaAlteracao.time});}"
													alt="Remover"><img
													src="<bean:message key='caminho.imagens'/>Error.gif"
													width="14" height="14" border="0"></a></td>
												<td width="10%" align="center">
												<logic:empty name="AtualizarRegistroAtendimentoActionForm" property="idSolicitantePrincipal">
												 <logic:empty
													name="registroAtendimentoSolicitante"
													property="indicadorSolicitantePrincipal">
													<input type="radio" name="idSolicitantePrincipal"
														value="${registroAtendimentoSolicitante.ultimaAlteracao.time}" />
												 </logic:empty> 
												 <logic:notEmpty
													name="registroAtendimentoSolicitante"
													property="indicadorSolicitantePrincipal">
													<logic:equal name="registroAtendimentoSolicitante"
														property="indicadorSolicitantePrincipal" value="1">
														<input type="radio" name="idSolicitantePrincipal"
															value="${registroAtendimentoSolicitante.ultimaAlteracao.time}"
															checked="checked" />
													</logic:equal>
													<logic:notEqual name="registroAtendimentoSolicitante"
														property="indicadorSolicitantePrincipal" value="1">
														<input type="radio" name="idSolicitantePrincipal"
															value="${registroAtendimentoSolicitante.ultimaAlteracao.time}" />
													</logic:notEqual>
												 </logic:notEmpty>
												</logic:empty>
												<logic:notEmpty name="AtualizarRegistroAtendimentoActionForm" property="idSolicitantePrincipal">
												<logic:equal name="AtualizarRegistroAtendimentoActionForm" property="idSolicitantePrincipal" value="${registroAtendimentoSolicitante.ultimaAlteracao.time}" >
													<input type="radio" name="idSolicitantePrincipal"
														value="${registroAtendimentoSolicitante.ultimaAlteracao.time}" checked="checked" />
												 </logic:equal>
												 <logic:notEqual name="AtualizarRegistroAtendimentoActionForm" property="idSolicitantePrincipal" value="${registroAtendimentoSolicitante.ultimaAlteracao.time}" >
													<input type="radio" name="idSolicitantePrincipal"
														value="${registroAtendimentoSolicitante.ID}"/>
												 </logic:notEqual>
												</logic:notEmpty>
												</td>
												<td width="29%" align="center"><a
													href="javascript:abrirPopup('exibirAdicionarSolicitanteRegistroAtendimentoAction.do?idRegistroAtendimento='+document.forms[0].numeroRA.value+'&objetoColecao=<bean:write name="registroAtendimentoSolicitante" property="ultimaAlteracao.time"/>&idRASolicitante=<bean:write name="registroAtendimentoSolicitante" property="ID"/>&primeiraVez=SIM', 570, 700);" />
												<bean:write name="registroAtendimentoSolicitante"
													property="solicitante" /> </a></td>
												<td width="14%" align="center">
												<logic:notEmpty name="registroAtendimentoSolicitante" property="cliente">
												<bean:write
													name="registroAtendimentoSolicitante" property="cliente.id" />
												</logic:notEmpty>
												<logic:empty name="registroAtendimentoSolicitante" property="cliente">
												&nbsp;
												</logic:empty>	
												</td>
												<td width="32%" align="center"><logic:notEmpty
													name="registroAtendimentoSolicitante"
													property="unidadeOrganizacional">
													<bean:write name="registroAtendimentoSolicitante"
														property="unidadeOrganizacional.descricao" />
												</logic:notEmpty> <logic:empty
													name="registroAtendimentoSolicitante"
													property="unidadeOrganizacional">
											&nbsp;
											</logic:empty></td>


											</tr>
										</logic:iterate>

									</table>
									</td>
								</tr>


							</table>
							</td>
						</tr>

					</table>
					</td>
				</tr>




				<tr>
					<td colspan="3" height="10"></td>
				</tr>
				<tr>
					<td colspan="3">
					<div align="right"><jsp:include page="/jsp/util/wizard/navegacao_botoes_wizard_valida_avancar_ra.jsp?numeroPagina=3"/>
					</div>
					</td>
				</tr>
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>

</html:form>


</body>
<!-- registro_atendimento_atualizar_solicitante.jsp -->
</html:html>

