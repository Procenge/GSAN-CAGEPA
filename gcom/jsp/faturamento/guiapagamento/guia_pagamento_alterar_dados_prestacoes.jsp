<%@page import="gcom.util.Util"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ page import="gcom.cobranca.parcelamento.ParcelamentoItem"
	isELIgnored="false"%>
<%@ page import="gcom.gui.faturamento.bean.DadosPrestacaoGuiaHelper"%>

<html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="AlterarDadosDasPrestacoesForm" dynamicJavascript="false" />
	
<script language="JavaScript">

function confirmarAlterarDadosPrestacoes(){
	if(validarDataVencimento()){
		if (confirm ("Confirma alteração?")) {
			redirecionarSubmit('alterarDadosDasPrestacoesAction.do');
		}
	}
 }
 
function validarDataVencimento(){
	var dataVencimentoPrestacao = document.AlterarDadosDasPrestacoesForm.dataVencimentoPrestacao;
	var numeroPrestacao = document.AlterarDadosDasPrestacoesForm.numeroPrestacao;
	var msg = '';
	var retorno = true;
	
	if(dataVencimentoPrestacao != "" && dataVencimentoPrestacao.length > 0){

		for(indice = 0; indice < dataVencimentoPrestacao.length; indice++){
			
			msg ='Data de Vencimento da Prestação ' + numeroPrestacao[indice].value + ' inválida. Informe outra data.';
		
			if (!verificaDataMensagemPersonalizada(dataVencimentoPrestacao[indice], msg)) {
				dataVencimentoPrestacao[indice].focus();
				return false;
			}
			
			if(dataVencimentoPrestacao.length > 1 && numeroPrestacao[indice].value != 1){
				msg = 'Data de Vencimento da Prestação ' + numeroPrestacao[indice].value + ' não pode ser anterior a ' + dataVencimentoPrestacao[indice-1].value + '. Informe outra data.';
				
				if(comparaData(dataVencimentoPrestacao[indice].value,'<',dataVencimentoPrestacao[indice-1].value) &&
						numeroPrestacao[indice].value != numeroPrestacao[indice-1].value){
					alert(msg);
					dataVencimentoPrestacao[indice].focus();
					return false;
				}

				if(comparaData(dataVencimentoPrestacao[indice].value,'=',dataVencimentoPrestacao[indice-1].value) &&
						numeroPrestacao[indice].value != numeroPrestacao[indice-1].value){
					alert(msg);
					dataVencimentoPrestacao[indice].focus();
					return false;
				}
			}
			
			if(dataVencimentoPrestacao.length > indice && indice < (dataVencimentoPrestacao.length - 1) && numeroPrestacao[indice].value != 1){
				msg = 'Data de Vencimento da Prestação ' + numeroPrestacao[indice].value + ' não pode ser posterior a ' + dataVencimentoPrestacao[indice+1].value + '. Informe outra data.';
				
				if (comparaData(dataVencimentoPrestacao[indice].value,'>',dataVencimentoPrestacao[indice+1].value) &&
						numeroPrestacao[indice].value != numeroPrestacao[indice+1].value){
					alert(msg);
					dataVencimentoPrestacao[indice].focus();
					return false;
				}

				if (comparaData(dataVencimentoPrestacao[indice].value,'=',dataVencimentoPrestacao[indice+1].value) &&
						numeroPrestacao[indice].value != numeroPrestacao[indice+1].value){
					alert(msg);
					dataVencimentoPrestacao[indice].focus();
					return false;
				}
			}
		}
	}
	
	return retorno;
}
 
function carregarRetorno(){
	
	chamarReload('${sessionScope.retornarTela}');
	window.close();
	
}

</script>
	
</head>

<logic:equal name="fechaPopup" value="false" scope="request">
<body leftmargin="5" topmargin="5"
	onload="javascript:resizePageSemLink(650,700);">
</logic:equal>
<logic:equal name="fechaPopup" value="true" scope="request">
	<body leftmargin="5" topmargin="5"
		onload="javascript:carregarRetorno();">
</logic:equal>
	
<html:form action="/exibirAlterarDadosDasPrestacoesAction" method="post"
    name="AlterarDadosDasPrestacoesForm"
    type="gcom.gui.faturamento.AlterarDadosDasPrestacoesForm">

<table width="570" border="0" cellspacing="5" cellpadding="0"  align="center">
	<tr>
		<td width="570" valign="top" class="centercoltext">
		<table height="100%">
			<tr>
				<td></td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td width="11"><img	src="<bean:message key="caminho.imagens"/>parahead_left.gif" border="0" /></td>
				<td class="parabg">Alterar Dados das Prestações da Guia</td>
				<td width="11"><img	src="<bean:message key="caminho.imagens"/>parahead_right.gif" border="0" /></td>
			</tr>
		</table>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="4">
						<table width="100%" align="center" bgcolor="#99CCFF" border="0"
								cellpadding="0" cellspacing="0">
							<tr>
								<td><strong>Dados das Prestações</strong></td>
							</tr>
							<tr bgcolor="#cbe5fe">
								<td width="100%" align="center">
									<table width="100%" cellpadding="0" cellspacing="0" border="0">
										<tr>
											<td></td>
										</tr>
										<tr>
											<td colspan="10" bgcolor="#000000" height="2"></td>
										</tr>
		
										<tr bordercolor="#000000">
											<td width="25%" bgcolor="#90c7fc" align="center"><strong>Número da Prestação</strong></td>										
											<td width="25%" bgcolor="#90c7fc" align="center"><strong>Data de Vencimento</strong></td>
											<td width="25%" bgcolor="#90c7fc" align="center"><strong>Tipo de Débito</strong></td>
											<td width="25%" bgcolor="#90c7fc" align="center"><strong>Valor da Prestação</strong></td>
										</tr>
										<tr>
											<td colspan="10"> 
											<div style="width: 100%; height: 100;">
											<table width="100%" bgcolor="#99CCFF">
												<logic:present name="colecaoDadosPrestacaoGuiaHelper">
													<%int cont = 0;%>
													<logic:iterate name="colecaoDadosPrestacaoGuiaHelper" id="dadosPrestacaoGuiaHelper" type="DadosPrestacaoGuiaHelper">
														<%cont = cont + 1;
	
														if (cont % 2 == 0) {%>
																<tr bgcolor="#cbe5fe">
														<%} else { %>
																<tr bgcolor="#FFFFFF">
														<%}%>
														<td width="25%" align="center">
																<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
																	<html:text property="numeroPrestacao" value="<%="" + dadosPrestacaoGuiaHelper.getNumeroPrestacao()%>" readonly="true" style="background-color:#EFEFEF; border:0; text-align:center;"/>																		
																</font>
														</td>
														<logic:equal name="dadosPrestacaoGuiaHelper" property="numeroPrestacao" value="1">
															<td width="25%" align="center">
																<html:text name="dadosPrestacaoGuiaHelper" property="dataVencimentoPrestacao" size="10" maxlength="10"
																	onkeyup="mascaraData(this,event)"  tabindex="2"  readonly="true" 
																	value="<%="" + Util.formatarData(dadosPrestacaoGuiaHelper.getDataVencimentoPrestacao())%>" style="background-color:#EFEFEF; border:0;"/>
															</td>
														</logic:equal>
														<logic:notEqual name="dadosPrestacaoGuiaHelper" property="numeroPrestacao" value="1">
															<td width="25%" align="center">
																<html:text name="dadosPrestacaoGuiaHelper" property="dataVencimentoPrestacao" size="10" maxlength="10"
																	onkeyup="mascaraData(this,event)"  tabindex="2"  readonly="false" 
																	value="<%=Util.formatarData(dadosPrestacaoGuiaHelper.getDataVencimentoPrestacao())%>"/>
															</td>
														</logic:notEqual>
														<td width="25%" align="center">
															<% for(int indiceDebito = 0; indiceDebito < dadosPrestacaoGuiaHelper.getIdDebitoTipo().length; indiceDebito++) {%>
																<html:hidden property="numeroPrestacaoArray" value="<%=dadosPrestacaoGuiaHelper.getNumeroPrestacaoArray()[indiceDebito] + ""%>" />																		
																<html:hidden property="dataVencimentoPrestacaoArray" value="<%=Util.formatarData(dadosPrestacaoGuiaHelper.getDataVencimentoPrestacaoArray()[indiceDebito])%>" />																		
																<html:hidden property="debitoTipo"  value="<%=dadosPrestacaoGuiaHelper.getIdDebitoTipo()[indiceDebito]%>" />																																		

															 	<html:text property="descricaoDebitoTipo" value="<%="" + dadosPrestacaoGuiaHelper.getDescricaoDebitoTipo()[indiceDebito]%>" 
																	readonly="true" size="25" maxlength="25" style="background-color:#EFEFEF;text-align:center;border:0;"/>	
															<%}%>
														</td>
														<td width="25%" align="center">
															<% for(int indiceDebito = 0; indiceDebito < dadosPrestacaoGuiaHelper.getValorDebitoNaPrestacao().length; indiceDebito++) {%>
															<html:text property="valorDebitoNaPrestacao" size="10" maxlength="10" 
																onkeypress="return isCampoNumerico(event);" tabindex="2"  readonly="false" onkeyup="formataValorMonetario(this, 13);"
																value="<%="" + Util.formatarMoedaReal(Util.formatarStringParaBigDecimal(dadosPrestacaoGuiaHelper.getValorDebitoNaPrestacao()[indiceDebito]))%>"/>
															<%}%>
														</td>
													</logic:iterate>
												</logic:present>
											</table>
											</div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>	
				<tr>
					<td colspan="10">
						<hr>
					</td>
				</tr>
			<table width="100%" border="0">
				<tr>
					<td height="24"><logic:present
						name="caminhoRetornoTelaConsultaGuiaPagamento">
						<input type="button" class="bottonRightCol" value="Voltar"
							style="width: 70px;" onclick="javascript:history.back();" />
					</logic:present>
					<td align="right"><input name="Button" type="button"
						class="bottonRightCol" value="Fechar"
						onClick="javascript:window.close();"></td>
						
					<td align="center">					
						<input type="button" 
									 	name="" 
									 	value="Confirmar" 
									 	class="bottonRightCol" 
										onclick="confirmarAlterarDadosPrestacoes()"
										style="width:100px"/>
					</td>
				</tr>
			</table>
		</table>
		</td>
		</tr>
	</table>

</html:form>
</body>
</html>
