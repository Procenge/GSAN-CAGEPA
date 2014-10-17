<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@ page import="gcom.util.Util"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="gcom.faturamento.conta.Conta" isELIgnored="false"%>
<%@ page import="gcom.util.ConstantesSistema"%>

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

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js">
</script><html:javascript staticJavascript="false"  formName="SelecionarContaRevisaoActionForm" />


<SCRIPT LANGUAGE="JavaScript">
<!--

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

// Verifica se há item selecionado
function verficarSelecao(objeto, msg){

	var form = document.forms[0];
	var contaChecked = objeto.value;
	var idContaMotivoRevisaoSelected = document.getElementById('idContaMotivoRevisao').value;

	if (trim(contaChecked) == "" && trim(idContaMotivoRevisaoSelected) == -1) {
		
		if (msg != null && msg != '' && msg == 'COM_MENSAGEM') {
			// Limpar a coleção conta em revisão
			document.forms[0].action = "/gsan/selecionarContaRevisaoAction.do?codigoImovel=" + form.codigoImovel.value + '&cancelar=S';
			document.forms[0].submit();
		} else {
			if(validateSelecionarContaRevisaoActionForm(form)) {
				var mensagem = "Nenhuma Conta foi selecionada para ser colocada em revisão.";
				if (CheckboxNaoVazioMensagemGenerico(mensagem, "")){
					window.close();
				}
			}
		}
		
	} else {

		if(validateSelecionarContaRevisaoActionForm(form)) {

			var mensagem = "Nenhuma Conta foi selecionada para ser colocada em revisão.";

			if (CheckboxNaoVazioMensagemGenerico(mensagem, "")){
				if (confirm ("Confirma revisão?")) {
					document.forms[0].action = "/gsan/selecionarContaRevisaoAction.do?codigoImovel=" + form.codigoImovel.value;
					document.forms[0].submit();
				}
			}
		}
	}
 }

 function fechar() {
	// Com Mensagem
	window.close();
 }

 function cancelarColocarContaRevisao() {
	 if (confirm ("Cancelar Colocar Conta em Revisão?")) {
		var form = document.forms[0];
		document.forms[0].action = "/gsan/selecionarContaRevisaoAction.do?cancelar=S&codigoImovel=" + form.codigoImovel.value;
		document.forms[0].submit();
	 }
 }

 function atualizarListaContas() {

	 	var arr = new Array();
	 	arr = document.getElementsByName('contaSelectID');
	 	
		for(i = 0; i < arr.length; i++){
		    var arrayCampo = document.forms[0].contaSelectAuxID.value.split(",");
	 		
	 		if (arrayCampo != null && arrayCampo != undefined && arrayCampo.length != 0) {

	 			for(j = 0; j < arrayCampo.length; j++) {
				 	if (arr[i].value == arrayCampo[j]) {
					 	arr[i].checked = true;
				 	}
		 		}
		 	}
		}
 }
 
//-->
</SCRIPT>

</head>

<logic:present name="closePage">
	<logic:equal name="closePage" value="S">
		<body leftmargin="5" topmargin="5" onload="window.close();">
	</logic:equal>
</logic:present>

<logic:notPresent name="closePage">
	<body leftmargin="5" topmargin="5" onload="atualizarListaContas();setarFoco('${requestScope.nomeCampoFoco}');">
</logic:notPresent>

<html:form action="/selecionarContaRevisaoAction" 
	name="SelecionarContaRevisaoActionForm" 
	type="gcom.gui.atendimentopublico.registroatendimento.SelecionarContaRevisaoActionForm" method="post" >
	<html:hidden property="abrirPopUpEnviarContaRevisao"/>
	<html:hidden property="contaSelectAuxID"/>
	<html:hidden property="especificacao"/>
	
	<table width="500" border="0" cellpadding="0" cellspacing="5">
		<tr>
			<td width="720" valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Selecionar Conta Para Revis&atilde;o</td>
					<td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td>Para colocar a(s) conta(s) em revis&atilde;o, informe os dados abaixo:</td>
					<td align="right"></td>					
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td height="10" width="150px;"><strong>Matrícula do Imóvel:</strong></td>
					<td align="left">
						<html:text property="codigoImovel" maxlength="9" size="9" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
					</td>
				</tr>
				<tr>
					<td><strong>Motivo da Revis&atilde;o:<font color="#FF0000">*</font></strong></td>
					<td>
						<html:select property="idContaMotivoRevisao" tabindex="5" styleId="idContaMotivoRevisao">
							<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<logic:present name="colecaoContaMotivoRevisao">
								<logic:notEmpty name="colecaoContaMotivoRevisao">
									<html:options collection="colecaoContaMotivoRevisao" labelProperty="descricaoMotivoRevisaoConta" property="id" />
								</logic:notEmpty>
							</logic:present>
						</html:select>
					</td>
				</tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
			</table>
			<table width="100%" border="0">
			<tr>
				<td colspan="2">
				<table width="100%" border="0">
						<tr>
							<td colspan="4">
							<table width="100%" cellpadding="0" cellspacing="0">
								<tr>
									<td>
									<table width="100%" border="0" bgcolor="#90c7fc">
										<tr>
											<td bgcolor="#79bbfd" colspan="9" height="20">
												<strong>Contas do Imóvel</strong>
											</td>
										</tr>
										<tr bgcolor="#90c7fc">
											<td align="center" width="5%"><a href="javascript:facilitador(this);" id="0"><strong>Todos</strong></a></td>
											<td width="13%">
												<div align="center"><strong>Refer&ecirc;ncia</strong></div>
											</td>
											<td width="13%">
												<div align="center"><strong>Vencimento</strong></div>
											</td>
											<td width="10%">
												<div align="center"><strong>Valor Total</strong></div>
											</td>
										</tr>
									</table>

									</td>
								</tr>

								<logic:present name="colecaoContaImovel">

									<tr>
										<td>
										
										<% String cor = "#cbe5fe";%>

										<div style="width: 100%; height: 100; overflow: auto;">
										
										<table width="100%" align="center" bgcolor="#90c7fc">
										
										<logic:iterate name="colecaoContaImovel" id="conta" type="Conta">

								
												<%	if (cor.equalsIgnoreCase("#cbe5fe")){
													cor = "#FFFFFF";%>
													<tr bgcolor="#FFFFFF">
												<%} else{
													cor = "#cbe5fe";%>
													<tr bgcolor="#cbe5fe">
												<%}%>
												
												<%
	  											 String data = "";
													 if(((Conta)conta).getUltimaAlteracao() != null){
														data = new Long(((Conta)conta).getUltimaAlteracao().getTime()).toString();	 
													 }	
												%>
												
													<td align="center" width="5%" valign="middle">
														<input type="checkbox" name="contaSelectID" id="contaSelectID" value='<%="" + conta.getId().intValue() + "-" + data%>' />
													</td>
														
													<td width="10%" align="center">
														<%=""+ Util.formatarMesAnoReferencia(conta.getReferencia())%>
													</td>
													
													<td width="13%">
														<div align="center">
															<logic:present name="conta" property="dataVencimentoConta">
																<span style="color: #000000;"><%="" + Util.formatarData(conta.getDataVencimentoConta())%></span>
															</logic:present>
															<logic:notPresent name="conta" property="dataVencimentoConta">
																&nbsp;
															</logic:notPresent>
														</div>
													</td>
													
													<td width="10%"><div align="right"><span style="color: #000000;"><%="" + Util.formatarMoedaReal(new BigDecimal(conta.getValorTotalConta())).trim()%></span></div></td>
												</tr>

										</logic:iterate>
										
										</table>
										
										</div>
										</td>
									</tr>

								</logic:present>

							</table>
							</td>
						</tr>

					</table>
				</td>
				</tr>
				<tr>
					<td align="right" colspan="2">
						<div><strong><font color="#FF0000">*</font></strong>Campos obrigat&oacute;rios</div>
					</td>
				</tr>
				<tr>
					<td height="5" align="left">
						<c:if test="${sessionScope.SelecionarContaRevisaoActionForm.abrirPopUpEnviarContaRevisao == 'COM_MENSAGEM'}">
							<input name="Button2" type="button" class="bottonRightCol" value="Fechar" onClick="fechar();">&nbsp;&nbsp;
						</c:if>
					</td>
					<td valign="top" align="right">
						<gcom:controleAcessoBotao name="Button" value="Colocar Em Revisão" onclick="javascript:verficarSelecao(contaSelectID, '${sessionScope.SelecionarContaRevisaoActionForm.abrirPopUpEnviarContaRevisao}');" url="colocarRevisaoContaAction.do" align="left"/>
					</td>
				</tr>
				
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>
</html:form>
</body>
</html:html>