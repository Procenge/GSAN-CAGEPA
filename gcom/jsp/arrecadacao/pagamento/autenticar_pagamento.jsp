<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

<%@ page import="gcom.util.ConstantesSistema,java.util.Collection"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>/validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="ManterPagamentoActionForm"/>

<script language="JavaScript">
<!--
	function validarForm(form){
		verificarComboBoxChecado(form);
		
		submeterFormPadrao(form);
	}
	
	function verificarComboBoxChecado(form){
		
		var elements = document.getElementsByName("idRegistroAutenticar");
		var radioChecado = "";
		var dataPagamentoElements = document.getElementsByName("dataPagamentoAutenticar");
		var dataPagamento = "";
	
		for (var i=0, len=elements.length; i<len; ++i){
		    if (elements[i].checked) {
		    	radioChecado = elements[i].value;
		    	dataPagamento = dataPagamentoElements[i].value;
		    }
		}
		
		form.idPagamento.value = radioChecado;
		form.dataPagamento.value = dataPagamento;
	}
 -->
</script>

</head>

<body leftmargin="5" topmargin="5" onload="resizePageSemLink(790, 530);">
<html:form action="/autenticarPagamentosAction"
			name="AutenticarPagamentosActionForm"
			type="gcom.gui.arrecadacao.pagamento.AutenticarPagamentosActionForm" 
			method="post"
			onsubmit="return validateManterPagamentoActionForm(this);">
			
	<html:hidden property="idPagamento"/>
	<html:hidden property="dataPagamento"/>

<table width="750" border="0" cellspacing="5" cellpadding="0">
  <tr>
	<td width="750" valign="top" class="centercoltext">
	  <table height="100%">
		<tr>
		  <td></td>
		</tr>
	  </table>
	  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	    <tr>
	  	  <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
		  <td class="parabg">Autenticar Pagamentos</td>
	 	  <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
	    </tr>
	  </table>
	  <p>&nbsp;</p>
	  <table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="11" height="23"><font color="#000000"
						style="font-size:10px"
						face="Verdana, Arial, Helvetica, sans-serif"> 
						<strong> Selecione apenas um pagamento por vez para ser autenticado. </strong> </font>
					</td>
				</tr>

				<tr>
					<td colspan="11" bgcolor="#000000" height="2"></td>
				</tr>

				<tr>
					<td colspan="11">
					<table width="100%" bgcolor="#99CCFF">
						<tr bordercolor="#FFFFFF" bgcolor="#99CCFF">
							<td class="style1" align="center" rowspan="1"><strong> <font
								color="#000000" style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <a
								onclick="this.focus();" id="0"
								href="javascript:facilitador(this);">Selecione</a> </font> </strong>
							</td>

							<td rowspan="1" bordercolor="#000000" bgcolor="#90c7fc">
							<div align="center"><font color="#000000" style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Im&oacute;vel</strong>
							</font></div>
							</td>

							<td rowspan="1" bordercolor="#000000" bgcolor="#90c7fc">
							<div align="center"><font color="#000000" style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Cliente</strong>
							</font></div>
							</td>

							<td rowspan="1" bordercolor="#000000" bgcolor="#90c7fc">
							<div align="center"><font color="#000000" style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Tipo do
							Documento</strong> </font></div>
							</td>

							<td rowspan="1" bordercolor="#000000" bgcolor="#90c7fc">
							<div align="center"><font color="#000000" style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>M&ecirc;s/Ano
							Refer&ecirc;ncia do Pagamento</strong> </font></div>
							</td>

							<td rowspan="1" bordercolor="#000000" bgcolor="#90c7fc">
							<div align="center"><font color="#000000" style="font-size:9px"
								face="Verdana, Arial, Helvetica, sans-serif"> <strong>Valor do
							Pagamento</strong> </font></div>
							</td>
						</tr>
 
						<%--Esquema de paginação--%>
						<pg:pager isOffset="true" index="half-full" maxIndexPages="10"
							export="currentPageNumber=pageNumber;pageOffset"
							maxPageItems="10" items="${sessionScope.totalRegistros}">
							<pg:param name="pg" />
							<pg:param name="q" />

							<logic:present name="colecaoPagamentos">
								<%int cont = 1;%>
								<logic:iterate name="colecaoPagamentos" id="autenticarPagamentosHelper">
									<pg:item>
										<%cont = cont + 1;
										if (cont % 2 == 0) {%>
										<tr bgcolor="#FFFFFF">
											<%} else { %>
										<tr bgcolor="#cbe5fe">
											<%}%>

											<td>
												<div align="center">
													<input type="radio" name=idRegistroAutenticar id="idRegistroAutenticar" 
													value="<bean:write name="autenticarPagamentosHelper" property="idPagamento"/>" />
													
													<input type="hidden" name="dataPagamentoAutenticar" id="dataPagamentoAutenticar" 
														value="<bean:write name="autenticarPagamentosHelper" property="dataPagamento"/>"/>
												</div>
											</td>

											<td>
												<div align="center">
													<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
														<bean:write name="autenticarPagamentosHelper" property="idImovel" /> 
													</font>
												</div>
											</td>

											<td align="center">
												<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
													<bean:write name="autenticarPagamentosHelper" property="idCliente" /> 
												</font>
											</td>

											<td>
												<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
													<bean:write name="autenticarPagamentosHelper" property="descricaoTipoDocimento" />
												</font>
											</td>

											<td align="center">
												<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
													<bean:write name="autenticarPagamentosHelper" property="referenciaPagamento" />
												</font>
											</td>

											<td>
												<div align="right">
													<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
														<bean:write name="autenticarPagamentosHelper" property="valorPagamento" formatKey="money.format" /> 
													</font>
												</div>
											</td>

										</tr>

									</pg:item>
								</logic:iterate>
							</logic:present>
					</table>
				<table width="100%">
						<tr>
							<td colspan="2">
					          	<INPUT TYPE="button" class="bottonRightCol" value="Limpar" onclick="document.forms[0].reset();"/>
									&nbsp;&nbsp;
					          	<INPUT TYPE="button" class="bottonRightCol" value="Voltar" onclick="window.close();"/>
					         </td>
					         <td align="right">
						         <input type="button" name="Button" class="bottonRightCol" value="Gerar Autenticação" onclick="javascript:validarForm(document.forms[0]);" />
							 </td>
						</tr>
					</table>

					<table width="100%" border="0">
						<tr>
							<td>
							<div align="center"><strong><%@ include
								file="/jsp/util/indice_pager_novo.jsp"%></strong></div>
							</td>
						</tr>
					</table>
					</pg:pager>
					<%-- Fim do esquema de paginação --%>
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
