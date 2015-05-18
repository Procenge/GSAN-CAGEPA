<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<%@ page import="gcom.util.Util"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="gcom.cobranca.bean.ContaValoresHelper"%>
<%@ page import="gcom.faturamento.debito.DebitoACobrar"%>
<%@ page import="gcom.faturamento.credito.CreditoARealizar"%>
<%@ page import="gcom.cobranca.bean.GuiaPagamentoValoresHelper"%>
<%@ page import="gcom.arrecadacao.pagamento.GuiaPagamento"%>
<%@ page import="gcom.cadastro.cliente.ClienteImovel" isELIgnored="false"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false"  formName="TransferenciaDebitoCreditoDadosImovelActionForm"/>

<SCRIPT LANGUAGE="JavaScript">
<!--

	function validarForm(form){
	
	    retorno = false;
	
		for(indice = 0; indice < form.elements.length; indice++){
			if (form.elements[indice].type == "checkbox" && form.elements[indice].checked == true) {
				retorno = true;
				break;
			}
		}
	
		if (!retorno){
			alert('Não existem débitos/créditos selecionados para transferência.'); 
		}
		else{
		
			if (confirm('Confirma transferência?')) {
				var idsConta = obterValorCheckboxMarcadoPorNome("conta");
				var idsDebito = obterValorCheckboxMarcadoPorNome("debito");
				var idsCredito = obterValorCheckboxMarcadoPorNome("credito");
				var idsGuiaPagamento = obterValorCheckboxMarcadoPorNome("guiaPagamento");
				
				var urlTransferencia = "/gsan/transferirDebitoCreditoAction.do?";
				var concatenador = false;
				
				if (idsConta != null && idsConta.length > 0){
					urlTransferencia = urlTransferencia + "conta=" + idsConta;
					concatenador = true;
				}
				
				if (idsDebito != null && idsDebito.length > 0){
					if (concatenador){
						urlTransferencia = urlTransferencia + "&debito=" + idsDebito;
					}
					else{
						urlTransferencia = urlTransferencia + "debito=" + idsDebito;
						concatenador = true;
					}
				}
				
				if (idsCredito != null && idsCredito.length > 0){
					if (concatenador){
						urlTransferencia = urlTransferencia + "&credito=" + idsCredito;
					}
					else{
						urlTransferencia = urlTransferencia + "credito=" + idsCredito;
						concatenador = true;
					}
				}
				
				if (idsGuiaPagamento != null && idsGuiaPagamento.length > 0){
					if (concatenador){
						urlTransferencia = urlTransferencia + "&guiaPagamento=" + idsGuiaPagamento;
					}
					else{
						urlTransferencia = urlTransferencia + "guiaPagamento=" + idsGuiaPagamento;
						concatenador = true;
					}
				}
				
				
				form.action = urlTransferencia;
				submeterFormPadrao(form);
			}
		}
	}
	
  	function marcarClienteOrigemId(objeto) {
  		var form = document.forms[0];
  		
		var i = 0;
		for (i = 0; i < document.forms[0].idClienteImovel.length; i++) { 
		    if (document.forms[0].idClienteImovel[i].checked == true) {
		    	form.idClienteImovelSelecionado.value = document.forms[0].valorClienteImovel[i].value;
		    	
				var concatenador = false;
				var urlTransferencia = "/gsan/exibirTransferenciaDebitoCreditoDadosSelecaoAction.do?";
				
				var idsConta = obterValorCheckboxMarcadoPorNome("conta");
				var idsDebito = obterValorCheckboxMarcadoPorNome("debito");
				var idsCredito = obterValorCheckboxMarcadoPorNome("credito");
				var idsGuiaPagamento = obterValorCheckboxMarcadoPorNome("guiaPagamento");				
				
				if (idsConta != null && idsConta.length > 0){
					urlTransferencia = urlTransferencia + "conta=" + idsConta;
					concatenador = true;
				}
				
				if (idsDebito != null && idsDebito.length > 0){
					if (concatenador){
						urlTransferencia = urlTransferencia + "&debito=" + idsDebito;
					}
					else{
						urlTransferencia = urlTransferencia + "debito=" + idsDebito;
						concatenador = true;
					}
				}
				
				if (idsCredito != null && idsCredito.length > 0){
					if (concatenador){
						urlTransferencia = urlTransferencia + "&credito=" + idsCredito;
					}
					else{
						urlTransferencia = urlTransferencia + "credito=" + idsCredito;
						concatenador = true;
					}
				}
				
				if (idsGuiaPagamento != null && idsGuiaPagamento.length > 0){
					if (concatenador){
						urlTransferencia = urlTransferencia + "&guiaPagamento=" + idsGuiaPagamento;
					}
					else{
						urlTransferencia = urlTransferencia + "guiaPagamento=" + idsGuiaPagamento;
						concatenador = true;
					}
				}		    	
		    	
      			form.action = urlTransferencia;
      			form.submit();		    	
		    }
		}	  		
  	}	
  	
  	function marcarItens() {
  		var form = document.forms[0];
  	
  		marcarItensSelecionadas(form.idsContasSelecionadas.value, 'conta');
  		marcarItensSelecionadas(form.idsDebitosSelecionadas.value, 'debito');
  		marcarItensSelecionadas(form.idsCreditosSelecionadas.value, 'credito');
  		marcarItensSelecionadas(form.idsGuiasSelecionadas.value, 'guiaPagamento');
  		
  	}
  	
	function marcarItensSelecionadas(chavesSelecionadas, nomeCheckBox) {
		var form = document.forms[0];
		var chaves = chavesSelecionadas;

		if (chaves != null && chaves != '' && chaves != 'undefined') {
			myString = new String(chavesSelecionadas);
			splitString = myString.split(",");
			
			for (i=0; i<splitString.length; i++) {
			
				var elementos = form.elements[nomeCheckBox];
				var docLength = elementos.length;
				
				if (typeof docLength == 'undefined') {
					if (splitString[i] == elementos.value) {
						elementos.checked = true;
					}
				} else {
					for (j=0; j<docLength; j++) {
						if (splitString[i] == elementos[j].value) {
							elementos[j].checked = true;
						}
					}
				}
			}
		}
	} 
	
	function selecionarCreditoARealizar() {
		var chaves = "";
		var form = document.forms[0];
		var selecionados = form.elements['credito'];
		
		// Se o 1º estiver marcado, desmarcar todos
		if (selecionados.length != null && selecionados.length != '' && selecionados.length != 'undefined') {
			if (selecionados[1] != null && selecionados[1].checked) {
				for (i=0; i < selecionados.length; i++) {
					selecionados[i].checked = false;
				}
			
			} else {
				// Se o 1º não estiver marcado, marcar todos
				
					for (i=0; i < selecionados.length; i++) {
						selecionados[i].checked = true;
					}
			}
		} else {
			if (selecionados != null && selecionados.checked) {
				selecionados.checked = false;
			} else {
				selecionados.checked = true;
			}
		}
	}	
	
	function selecionarDebitosACobrar() {
		var chaves = "";
		var form = document.forms[0];
		var selecionados = form.elements['debito'];
		
		// Se o 1º estiver marcado, desmarcar todos
		if (selecionados.length != null && selecionados.length != '' && selecionados.length != 'undefined') {
			if (selecionados[1] != null && selecionados[1].checked) {
				for (i=0; i < selecionados.length; i++) {
					selecionados[i].checked = false;
				}
			
			} else {
				// Se o 1º não estiver marcado, marcar todos
				
					for (i=0; i < selecionados.length; i++) {
						selecionados[i].checked = true;
					}
			}
		} else {
			if (selecionados != null && selecionados.checked) {
				selecionados.checked = false;
			} else {
				selecionados.checked = true;
			}
		}
	}	
	
	function selecionarGuiaPagamento() {
		var chaves = "";
		var form = document.forms[0];
		var selecionados = form.elements['guiaPagamento'];
		
		// Se o 1º estiver marcado, desmarcar todos
		if (selecionados.length != null && selecionados.length != '' && selecionados.length != 'undefined') {
			if (selecionados[1] != null && selecionados[1].checked) {
				for (i=0; i < selecionados.length; i++) {
					selecionados[i].checked = false;
				}
			
			} else {
				// Se o 1º não estiver marcado, marcar todos
				
					for (i=0; i < selecionados.length; i++) {
						selecionados[i].checked = true;
					}
			}
		} else {
			if (selecionados != null && selecionados.checked) {
				selecionados.checked = false;
			} else {
				selecionados.checked = true;
			}
		}
	}
	
	function selecionarContas() {
		var chaves = "";
		var form = document.forms[0];
		var selecionados = form.elements['conta'];
		
		// Se o 1º estiver marcado, desmarcar todos
		if (selecionados.length != null && selecionados.length != '' && selecionados.length != 'undefined') {
			if (selecionados[1] != null && selecionados[1].checked) {
				for (i=0; i < selecionados.length; i++) {
					selecionados[i].checked = false;
				}
			
			} else {
				// Se o 1º não estiver marcado, marcar todos
				
					for (i=0; i < selecionados.length; i++) {
						selecionados[i].checked = true;
					}
			}
		} else {
			if (selecionados != null && selecionados.checked) {
				selecionados.checked = false;
			} else {
				selecionados.checked = true;
			}
		}
	}	

//-->
</SCRIPT>

</head>

<body leftmargin="5" topmargin="5" onload="javascript:marcarItens();setarFoco('${requestScope.nomeCampo}');">

<html:form action="/transferirDebitoCreditoAction" method="post">


<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

<html:hidden property="idsContasSelecionadas"/>
<html:hidden property="idsDebitosSelecionadas"/>
<html:hidden property="idsCreditosSelecionadas"/>
<html:hidden property="idsGuiasSelecionadas"/>
	
<table width="770" border="0" cellspacing="4" cellpadding="0">
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

	<td width="625" valign="top" class="centercoltext">

        <table height="100%">
        <tr>
          <td></td>
        </tr>
      	</table>

      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
          <td class="parabg">Transferencia de Débitos</td>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
        </tr>
      </table>
      <p>&nbsp;</p>

      <table width="100%" border="0">
      <tr>
      	<td></td>
      </tr>
      </table>
      
          
    <table width="100%" border="0">
	<tr>
		<td><strong>Imóvel Origem:</strong></td>
		<td>
			<html:text property="idImovelOrigem" size="10" maxlength="10" readonly="true"
			style="background-color:#EFEFEF; border:0; color: #000000"/>
		</td>

		<logic:present name="transferenciaEntreImovel" scope="session">	
			<td><strong>Imóvel Destino:</strong></td>
			<td>
				<html:text property="idImovelDestino" size="10" maxlength="10" readonly="true"
				style="background-color:#EFEFEF; border:0; color: #000000"/>
			</td>
		</logic:present>
	</tr>

	</table>
	
	<logic:notPresent name="transferenciaEntreImovel" scope="session">
	    <logic:present name="indicadorFaturamentoTitularDebito" scope="request">
			<table width="100%" align="center" bgcolor="#90c7fc" border="0">
			<tr>
				<td><strong>Cliente/Tipo de Relação Origem</strong></td>
			</tr>
			
			<html:hidden property="idClienteImovelSelecionado"/>
			<tr bgcolor="#cbe5fe">
		    	<td width="100%" align="center">
					<table width="100%" border="0">
					<tr>
						<td WIDTH="100%">
								<%int cont = 0;%>
								<table width="100%" bgcolor="#99CCFF">
									<tr bgcolor="#90c7fc">
										<td align="center" width="7%"><strong></strong></td>
										<td align="center" width="18%"><strong>Tipo de Relação</strong></td>
										<td align="center" width="75%"><strong>Nome do Cliente Origem</strong></td>
									</tr>
									
									<logic:notEmpty name="colecaoRelacaoImovel" scope="session">
									<tr>
										<td height="100" colspan="9">
										<div style="width: 100%; height: 100%; overflow: auto;">
										<table width="100%">
											<logic:iterate name="colecaoRelacaoImovel" type="ClienteImovel" id="clienteImovel">
												<%cont = cont + 1;
												if (cont % 2 == 0) {%>
												<tr bgcolor="#cbe5fe" width="100%">
												<%} else {%>
												<tr bgcolor="#FFFFFF" width="100%">
												<%}%>
								
												<td align="center" height="20" width="7%">
													<input type="hidden" name="valorClienteImovel"
														value="<bean:write name="clienteImovel" property="clienteRelacaoTipo.id"/>.<bean:write name="clienteImovel" property="cliente.id"/>" 
														 >
													
													<input type="radio" id="idClienteImovel" 
														name="idClienteImovel" 
														 onclick="javascript:marcarClienteOrigemId(this);">
														
														
														
												</td>
												<td align="center" height="20" width="18%">
													<bean:write name="clienteImovel" property="clienteRelacaoTipo.descricao"/>
												</td>
												<td align="center" height="20" width="75%">
													<bean:write name="clienteImovel" property="cliente.descricao"/>
												</td>																				
												</tr>
											</logic:iterate>							
										</table>
										</div>
										</td>
									</tr>
									</logic:notEmpty>							
								</table>			
						</td>
					</tr>
					</table>
				
				</td>
		    </tr>
	
		    </table>  
		   	</br>  
	   </logic:present>
	</logic:notPresent>	   		
	
	<logic:notPresent name="transferenciaEntreImovel" scope="session">
		<table width="100%" border="0">
			<tr>
				<td><strong>Cliente Destino:</strong></td>
				<td>	
					<html:text property="idClienteDestino" size="10" maxlength="10" readonly="true"
					style="background-color:#EFEFEF; border:0; color: #000000"/>
					<html:text property="nomeClienteDestino" size="50" maxlength="50" readonly="true"
					style="background-color:#EFEFEF; border:0; color: #000000"/>					
				</td>
			</tr>
		</table>	
	</logic:notPresent>			

	<table width="100%" border="0">
	<tr>
		<td HEIGHT="10"></td>
	</tr>
	</table>
		
	<table width="100%" border="0">
	<tr>
		<td>
		
			<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td>
				
					<table width="100%" cellpadding="0" cellspacing="0">
					<tr bgcolor="#79bbfd">
						<td colspan="6" height="20"><strong>Contas</strong></td>
					</tr>
					<tr bgcolor="#90c7fc">

						<td align="center" width="30"><a href="javascript:selecionarContas();"><strong>Todos</strong></a></td>
						<td width="15%">
							<div align="center"><strong>Mês/Ano</strong></div>
						</td>
						<td width="20%">
							<div align="center"><strong>Vencimento</strong></div>
						</td>
						<td width="20%">
							<div align="center"><strong>Valor</strong></div>
						</td>
						<td width="20%">
							<div align="center"><strong>Acrés. Impont.</strong></div>
						</td>
						<td width="20%">
							<div align="center"><strong>Situação</strong></div>
						</td>

					</tr>
					</table>

				</td>
			</tr>
			
			
			<%BigDecimal valorTotalConta = new BigDecimal("0.00");%>
			<%BigDecimal valorTotalAcrescimo = new BigDecimal("0.00");%>

			<logic:present name="colecaoConta">
			
			<logic:notEmpty name="colecaoConta">

			<tr>
				<td>
										
					<% String cor = "#cbe5fe";%>

					<div style="width: 100%; height: 100; overflow: auto;">
										
					<table width="100%" align="center" bgcolor="#90c7fc">
										
					<logic:iterate name="colecaoConta" id="conta" type="ContaValoresHelper">

						<%valorTotalConta = valorTotalConta.add(conta.getValorTotalConta()); %>
						<%valorTotalAcrescimo = valorTotalAcrescimo.add(conta.getValorTotalContaValoresParcelamento()); %>
						
						<%	if (cor.equalsIgnoreCase("#cbe5fe")){
							cor = "#FFFFFF";%>
							<tr bgcolor="#FFFFFF">
						<%} else{
							cor = "#cbe5fe";%>
							<tr bgcolor="#cbe5fe">
						<%}%>
												
												
						<td align="center" width="5%">
							<INPUT TYPE="checkbox" name="conta" id="conta"
							value="<%="" + conta.getConta().getId().intValue() %>">
						</td>
						<td width="15%" align="center">
							<a href="javascript:abrirPopup('exibirConsultarContaAction.do?contaID=<%="" + conta.getConta().getId() %>&tipoConsulta=conta', 600, 800);">
							<%=""+ Util.formatarMesAnoReferencia(conta.getConta().getReferencia())%> </a>
						</td>
						<td width="20%">
							<div align="center">
							
								<logic:present name="conta" property="conta.dataVencimentoConta">
									<span style="color: #000000;">
										<%="" + Util.formatarData(conta.getConta().getDataVencimentoConta())%>
									</span>
								</logic:present> 
								
								<logic:notPresent name="conta" property="conta.dataVencimentoConta">
									&nbsp;
								</logic:notPresent>	
							
							</div>
						</td>
						<td width="20%">
							<div align="right">
								<span style="color: #000000;">
									<%="" + Util.formatarMoedaReal(new BigDecimal(conta.getConta().getValorTotalConta())).trim()%>
								</span>
							</div>
						</td>
						<td width="20%">
							<div align="right">
								
								<logic:equal name="conta" property="valorTotalContaValoresParcelamento" value="0.00">
								<span style="color: #000000;">
									<%="" + Util.formatarMoedaReal(conta.getValorTotalContaValoresParcelamento()).trim()%>
								</span>
								</logic:equal>
								
								<logic:notEqual name="conta" property="valorTotalContaValoresParcelamento" value="0.00">
								<a title="<%="Multa: " + Util.formatarMoedaReal(conta.getValorMulta()).trim() + 
								" Juros de Mora: " + Util.formatarMoedaReal(conta.getValorJurosMora()).trim() +
								" Atualização Monetária: " + Util.formatarMoedaReal(conta.getValorAtualizacaoMonetaria()).trim()%>">
									
									<%="" + Util.formatarMoedaReal(conta.getValorTotalContaValoresParcelamento()).trim()%>
								</a>
								</logic:notEqual>
								
							</div>
						</td>
						<td width="20%">
							<div align="center">
													
								<logic:present name="conta" property="conta.debitoCreditoSituacaoAtual">
									<bean:write name="conta" property="conta.debitoCreditoSituacaoAtual.descricaoDebitoCreditoSituacao" />
								</logic:present> 
													
								<logic:notPresent name="conta" property="conta.debitoCreditoSituacaoAtual">
									&nbsp;
								</logic:notPresent>
													
							</div>
						</td>
					</tr>
			
					</logic:iterate>
					
						<%if (cor.equalsIgnoreCase("#cbe5fe")){
							cor = "#FFFFFF";%>
							<tr bgcolor="#FFFFFF">
						<%} else{
							cor = "#cbe5fe";%>
							<tr bgcolor="#cbe5fe">
						<%}%>

						<td width="5%" height="20"></td>
						<td width="15%">
							<div align="center"><strong>Total:</strong></div>
						</td>
						<td width="20%"></td>
						<td width="20%">
							<div align="right"><strong>
								<%="" + Util.formatarMoedaReal(valorTotalConta).trim()%>
							</strong></div>
						</td>
						<td width="20%">
							<div align="right"><strong>
								<%="" + Util.formatarMoedaReal(valorTotalAcrescimo).trim()%>
							</strong></div>
						</td>
						<td width="20%"></td>

					</tr>
										
					</table>
										
					</div>
					
				</td>
			</tr>

			</logic:notEmpty>
			
			</logic:present>
			
			</table>
			
			<%BigDecimal valorTotalDebito = new BigDecimal("0.00");%>	
			<%BigDecimal valorTotalGuiaPagamento = new BigDecimal("0.00");%>
			<%BigDecimal valorTotalCredito = new BigDecimal("0.00");%>				
				
			<logic:present name="indicadorFaturamentoTitularDebito" scope="request">
				</br> 
				<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td HEIGHT="5"></td>
				</tr>
				</table>	
	
				<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td>
					
						<table width="100%" cellpadding="0" cellspacing="0">
						<tr bgcolor="#79bbfd">
							<td colspan="6" height="20"><strong>Débitos</strong></td>
						</tr>
						<tr bgcolor="#90c7fc">
	
							<td align="center" width="30"><a href="javascript:selecionarDebitosACobrar();"><strong>Todos</strong></a></td>
							<td width="30%">
								<div align="center"><strong>Tipo do Débito</strong></div>
							</td>
							<td width="20%">
								<div align="center"><strong>Mês/Ano Ref.</strong></div>
							</td>
							<td width="20%">
								<div align="center"><strong>Mês/Ano Cobr.</strong></div>
							</td>
							<td width="5%">
								<div align="center"><strong>Parc.</strong></div>
							</td>
							<td width="20%">
								<div align="center"><strong>Vl. Restante</strong></div>
							</td>
	
						</tr>
						</table>
	
					</td>
				</tr>
				
				<logic:present name="colecaoDebitoACobrar">
				
				<logic:notEmpty name="colecaoDebitoACobrar">
	
				<tr>
					<td>
											
						<% String cor = "#cbe5fe";%>
	
						<div style="width: 100%; height: 100; overflow: auto;">
											
						<table width="100%" align="center" bgcolor="#90c7fc">
											
						<logic:iterate name="colecaoDebitoACobrar" id="debitoACobrar" type="DebitoACobrar">
	
							<%valorTotalDebito = valorTotalDebito.add(debitoACobrar.getValorRestanteCobrado()); %>
							
							<%	if (cor.equalsIgnoreCase("#cbe5fe")){
								cor = "#FFFFFF";%>
								<tr bgcolor="#FFFFFF">
							<%} else{
								cor = "#cbe5fe";%>
								<tr bgcolor="#cbe5fe">
							<%}%>
													
													
							<td align="center" width="5%">
								<INPUT TYPE="checkbox" name="debito" id="debito"
								value="<%="" + debitoACobrar.getId().intValue() %>">
							</td>
							<td width="30%" align="left">
								<a href="javascript:abrirPopup('exibirConsultarDebitoACobrarAction.do?imovelID=<%="" + debitoACobrar.getImovel().getId().intValue() %>&debitoID=<%="" + debitoACobrar.getId().intValue() %>', 570, 720);">
								<bean:write name="debitoACobrar" property="debitoTipo.descricao" /></a>
							</td>
							<td width="20%">
								<div align="center">
								
									<logic:present name="debitoACobrar" property="anoMesReferenciaDebito">
										<span style="color: #000000;">
											<%=""+ Util.formatarMesAnoReferencia(debitoACobrar.getAnoMesReferenciaDebito())%>
										</span>
									</logic:present> 
									
									<logic:notPresent name="debitoACobrar" property="anoMesReferenciaDebito">
										&nbsp;
									</logic:notPresent>	
								
								</div>
							</td>
							<td width="20%">
								<div align="center">
									
									<logic:present name="debitoACobrar" property="anoMesCobrancaDebito">
										<span style="color: #000000;">
											<%=""+ Util.formatarMesAnoReferencia(debitoACobrar.getAnoMesCobrancaDebito())%>
										</span>
									</logic:present> 
									
									<logic:notPresent name="debitoACobrar" property="anoMesCobrancaDebito">
										&nbsp;
									</logic:notPresent>	
								
								</div>
							</td>
							<td width="5%">
								<div align="center">
									
									<bean:write name="debitoACobrar" property="numeroPrestacaoCobradas" /> /
									<bean:write name="debitoACobrar" property="numeroPrestacaoDebito" />
									
								</div>
							</td>
							<td width="20%">
								<div align="right">
														
									<%="" + Util.formatarMoedaReal(debitoACobrar.getValorRestanteCobrado()).trim()%>
														
								</div>
							</td>
						</tr>
				
						</logic:iterate>
						
							<%if (cor.equalsIgnoreCase("#cbe5fe")){
								cor = "#FFFFFF";%>
								<tr bgcolor="#FFFFFF">
							<%} else{
								cor = "#cbe5fe";%>
								<tr bgcolor="#cbe5fe">
							<%}%>
	
							<td width="5%" height="20"></td>
							<td width="30%">
								<div align="center"><strong>Total:</strong></div>
							</td>
							<td width="20%"></td>
							<td width="20%"></td>
							<td width="5%"></td>
							<td width="20%">
								<div align="right">
									<strong>				
									<%="" + Util.formatarMoedaReal(valorTotalDebito).trim()%>
									</strong>				
								</div>
							</td>
	
						</tr>
											
						</table>
											
						</div>
						
					</td>
				</tr>
	
				</logic:notEmpty>
				
				</logic:present>
	
				</table>
			
				<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td HEIGHT="5"></td>
				</tr>
				</table>
				
				<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td>
					
						<table width="100%" cellpadding="0" cellspacing="0">
						<tr bgcolor="#79bbfd">
							<td colspan="6" height="20"><strong>Créditos</strong></td>
						</tr>
						<tr bgcolor="#90c7fc">
	
							<td align="center" width="30"><a href="javascript:selecionarCreditoARealizar();"><strong>Todos</strong></a></td>
							<td width="30%">
								<div align="center"><strong>Tipo do Crédito</strong></div>
							</td>
							<td width="20%">
								<div align="center"><strong>Mês/Ano Ref.</strong></div>
							</td>
							<td width="20%">
								<div align="center"><strong>Mês/Ano Cobr.</strong></div>
							</td>
							<td width="5%">
								<div align="center"><strong>Parc.</strong></div>
							</td>
							<td width="20%">
								<div align="center"><strong>Vl. Restante</strong></div>
							</td>
	
						</tr>
						</table>
	
					</td>
				</tr>
				
				<logic:present name="colecaoCreditoARealizar">
				
				<logic:notEmpty name="colecaoCreditoARealizar">
	
				<tr>
					<td>
											
						<% String cor = "#cbe5fe";%>
	
						<div style="width: 100%; height: 100; overflow: auto;">
											
						<table width="100%" align="center" bgcolor="#90c7fc">
											
						<logic:iterate name="colecaoCreditoARealizar" id="creditoARealizar" type="CreditoARealizar">
	
							<%valorTotalCredito = valorTotalCredito.add(creditoARealizar.getValorTotal()); %>
							
							<%	if (cor.equalsIgnoreCase("#cbe5fe")){
								cor = "#FFFFFF";%>
								<tr bgcolor="#FFFFFF">
							<%} else{
								cor = "#cbe5fe";%>
								<tr bgcolor="#cbe5fe">
							<%}%>
													
													
							<td align="center" width="5%">
								<input type="checkbox" name="credito" id="credito"
								value="<%="" + creditoARealizar.getId().intValue() %>">
							</td>
							<td width="30%" align="left">
								<a href="javascript:abrirPopup('exibirConsultarCreditoARealizarAction.do?imovelID=<%="" + creditoARealizar.getImovel().getId().intValue() %>&creditoID=<%="" + creditoARealizar.getId().intValue() %>', 570, 720);">
								<bean:write name="creditoARealizar" property="creditoTipo.descricao" /></a>
							</td>
							<td width="20%">
								<div align="center">
								
									<logic:present name="creditoARealizar" property="anoMesReferenciaCredito">
										<span style="color: #000000;">
											<%=""+ Util.formatarMesAnoReferencia(creditoARealizar.getAnoMesReferenciaCredito())%>
										</span>
									</logic:present> 
									
									<logic:notPresent name="creditoARealizar" property="anoMesReferenciaCredito">
										&nbsp;
									</logic:notPresent>	
								
								</div>
							</td>
							<td width="20%">
								<div align="center">
									
									<logic:present name="creditoARealizar" property="anoMesCobrancaCredito">
										<span style="color: #000000;">
											<%=""+ Util.formatarMesAnoReferencia(creditoARealizar.getAnoMesCobrancaCredito())%>
										</span>
									</logic:present> 
									
									<logic:notPresent name="creditoARealizar" property="anoMesCobrancaCredito">
										&nbsp;
									</logic:notPresent>	
								
								</div>
							</td>
							<td width="5%">
								<div align="center">
									
									<bean:write name="creditoARealizar" property="numeroPrestacaoRealizada" /> /
									<bean:write name="creditoARealizar" property="numeroPrestacaoCredito" />
									
								</div>
							</td>
							<td width="20%">
								<div align="right">
														
									<%="" + Util.formatarMoedaReal(creditoARealizar.getValorTotal()).trim()%>
														
								</div>
							</td>
						</tr>
				
						</logic:iterate>
						
							<%if (cor.equalsIgnoreCase("#cbe5fe")){
								cor = "#FFFFFF";%>
								<tr bgcolor="#FFFFFF">
							<%} else{
								cor = "#cbe5fe";%>
								<tr bgcolor="#cbe5fe">
							<%}%>
	
							<td width="5%" height="20"></td>
							<td width="30%">
								<div align="center"><strong>Total:</strong></div>
							</td>
							<td width="20%"></td>
							<td width="20%"></td>
							<td width="5%"></td>
							<td width="20%">
								<div align="right">
									<strong>				
									<%="" + Util.formatarMoedaReal(valorTotalCredito).trim()%>
									</strong>				
								</div>
							</td>
	
						</tr>
											
						</table>
											
						</div>
						
					</td>
				</tr>
	
				</logic:notEmpty>
				
				</logic:present>
	
				</table>
			
				
				
				<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td HEIGHT="5"></td>
				</tr>
				</table>

				</br>
				<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td>
					
						<table width="100%" cellpadding="0" cellspacing="0">
						<tr bgcolor="#79bbfd">
							<td colspan="5" height="20"><strong>Guias de Pagamento</strong></td>
						</tr>
						<tr bgcolor="#90c7fc">
	
							<td align="center" width="30"><a href="javascript:selecionarGuiaPagamento();"><strong>Todos</strong></a></td>
							<td width="35%">
								<div align="center"><strong>Tipo do Débito</strong></div>
							</td>
							<td width="20%">
								<div align="center"><strong>Emissão</strong></div>
							</td>
							<td width="20%">
								<div align="center"><strong>Vencimento</strong></div>
							</td>
							<td width="20%">
								<div align="center"><strong>Valor</strong></div>
							</td>
	
						</tr>
						</table>
	
					</td>
				</tr>
				
				<logic:present name="colecaoGuiaPagamento">
				
				<logic:notEmpty name="colecaoGuiaPagamento">
	
				<tr>
					<td>
											
						<% String cor = "#cbe5fe";%>
	
						<div style="width: 100%; height: 100; overflow: auto;">
											
						<table width="100%" align="center" bgcolor="#90c7fc">
											
						<logic:iterate name="colecaoGuiaPagamento" id="guiaPagamentoAtual" type="GuiaPagamento">
	
							<%valorTotalGuiaPagamento = valorTotalGuiaPagamento.add(guiaPagamentoAtual.getValorDebito()); %>
							
							<%	if (cor.equalsIgnoreCase("#cbe5fe")){
								cor = "#FFFFFF";%>
								<tr bgcolor="#FFFFFF">
							<%} else{
								cor = "#cbe5fe";%>
								<tr bgcolor="#cbe5fe">
							<%}%>
													
													
							<td align="center" width="5%">
								<INPUT TYPE="checkbox" name="guiaPagamento" id="guiaPagamento"
								value="<%="" + guiaPagamentoAtual.getId().intValue() %>">
							</td>
							<td width="35%" align="left">
								<a href="javascript:abrirPopup('exibirConsultarGuiaPagamentoAction.do?guiaPagamentoId=<%="" + guiaPagamentoAtual.getId().intValue() %>', 600, 800);">
									<logic:iterate name="guiaPagamentoAtual" property="guiasPagamentoPrestacao" id="prestacao">
										<span style="color: #000000;">
											<bean:write name="prestacao" property="debitoTipo.descricao" />
											<br>
										</span>
									</logic:iterate>
								</a>
							</td>
							<td width="20%">
								<div align="center">
									<logic:iterate name="guiaPagamentoAtual" property="guiasPagamentoPrestacao" id="prestacao">
										<logic:notEmpty name="prestacao" property="dataEmissao">
											<span style="color: #000000;">
												<bean:write name="prestacao" property="dataEmissao" formatKey="date.format"/>
											</span>
										</logic:notEmpty>
										<logic:empty name="prestacao" property="dataEmissao">
											&nbsp;
										</logic:empty>
									</logic:iterate>							
								</div>
							</td>
							<td width="20%">
								<div align="center">
									<logic:iterate name="guiaPagamentoAtual" property="guiasPagamentoPrestacao" id="prestacao">
										<logic:notEmpty name="prestacao" property="dataVencimento">
											<span style="color: #000000;">
												<bean:write name="prestacao" property="dataVencimento" formatKey="date.format"/>
											</span>
										</logic:notEmpty>
										<logic:empty name="prestacao" property="dataVencimento">
											&nbsp;
										</logic:empty>
									</logic:iterate>													
								</div>
							</td>
							<td width="20%">
								<div align="right">
									
									<%="" + Util.formatarMoedaReal(guiaPagamentoAtual.getValorDebito()).trim()%>
									
								</div>
							</td>
						</tr>
				
						</logic:iterate>
						
							<%if (cor.equalsIgnoreCase("#cbe5fe")){
								cor = "#FFFFFF";%>
								<tr bgcolor="#FFFFFF">
							<%} else{
								cor = "#cbe5fe";%>
								<tr bgcolor="#cbe5fe">
							<%}%>
	
							<td width="5%" height="20"></td>
							<td width="35%">
								<div align="center"><strong>Total:</strong></div>
							</td>
							<td width="20%"></td>
							<td width="20%"></td>
							<td width="20%">
								<div align="right">
									<strong>				
									<%="" + Util.formatarMoedaReal(valorTotalGuiaPagamento).trim()%>
									</strong>				
								</div>
							</td>
	
						</tr>
											
						</table>
											
						</div>
						
					</td>
				</tr>
	
				</logic:notEmpty>
				
				</logic:present>
	
				</table>
			 
	 		</logic:present>
		</td>
	</tr>
	</table>
	
	<table width="100%" border="0">
	<tr>
		<td HEIGHT="10"></td>
	</tr>
	</table>
	
	<%BigDecimal valorTotalDebitosFinal = valorTotalConta.add(valorTotalDebito); %>
	<%valorTotalDebitosFinal = valorTotalDebitosFinal.add(valorTotalGuiaPagamento); %>
	<%valorTotalDebitosFinal = valorTotalDebitosFinal.subtract(valorTotalCredito); %>
	
	<%BigDecimal valorTotalDebitosAtualizado = valorTotalDebitosFinal.add(valorTotalAcrescimo); %>
	
	<table width="100%" border="0">
	<tr>
		<td HEIGHT="20"><strong>Total dos Débitos:</strong></td>
		<td><div align="right"><strong><%="" + Util.formatarMoedaReal(valorTotalDebitosFinal).trim()%></strong></div></td>
	</tr>
	<tr>
		<td HEIGHT="20"><strong>Total dos Débitos Atualizados:</strong></td>
		<td><div align="right"><strong><%="" + Util.formatarMoedaReal(valorTotalDebitosAtualizado).trim()%></strong></div></td>
	</tr>
	</table>
	
	<table width="100%" border="0">
	<tr>
		<td HEIGHT="10"></td>
	</tr>
	</table>
	
	<table width="100%" border="0">
	<tr>
		<td>
			<input type="button" name="Button" class="bottonRightCol" value="Voltar" tabindex="1"
			onClick="window.location.href='/gsan/exibirTransferenciaDebitoCreditoDadosImovelAction.do'" style="width: 80px" />&nbsp; 
		</td>
		<td align="right">
			<gcom:controleAcessoBotao name="Button" value="Transferir" tabindex="2"
			onclick="javascript:validarForm(document.forms[0]);" url="transferirDebitoCreditoAction.do" /> 
		</td>
	</tr>
   
   </table>
      
	<p>&nbsp;</p>
	
	</td>
  </tr>
</table>

<%@ include file="/jsp/util/rodape.jsp"%>

<logic:present name="indicadorFaturamentoTitularDebito" scope="request">
	<SCRIPT LANGUAGE="JavaScript">
	<!--
	
		if (document.forms[0].idClienteImovel != undefined) {
			if (document.forms[0].idClienteImovel.length != undefined) {
				var i = 0;
				for (i = 0; i < document.forms[0].valorClienteImovel.length; i++) { 
				    if (document.forms[0].valorClienteImovel[i].value == document.forms[0].idClienteImovelSelecionado.value) {
				    	document.forms[0].idClienteImovel[i].checked = true;
				    } else {
				    	document.forms[0].idClienteImovel[i].checked = false;
				    }
				}				
			} else {
			    if (document.forms[0].valorClienteImovel.value == document.forms[0].idClienteImovelSelecionado.value) {
			    	document.forms[0].idClienteImovel.checked = true;			    	
			    }				
			}
		}

	//-->
	</SCRIPT>
</logic:present>	

</html:form>


</body>
</html:html>



