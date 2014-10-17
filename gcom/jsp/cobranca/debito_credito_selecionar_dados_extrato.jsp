<%@page import="gcom.faturamento.credito.CreditoTipo"%>
<%@page import="gcom.faturamento.debito.DebitoTipo"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<%@ page import="gcom.util.Util"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="gcom.cobranca.bean.ContaValoresHelper"%>
<%@ page import="gcom.faturamento.debito.DebitoACobrar"%>
<%@ page import="gcom.faturamento.credito.CreditoARealizar"%>
<%@ page import="gcom.cobranca.bean.GuiaPagamentoValoresHelper"%>
<%@ page import="gcom.cobranca.bean.DebitoCreditoParcelamentoHelper"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page import="gcom.util.ConstantesSistema"%><html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false"  formName="DebitoCreditoDadosSelecaoExtratoActionForm"/>

<SCRIPT LANGUAGE="JavaScript">

	function validarForm(form){
		if (validateDebitoCreditoDadosSelecaoExtratoActionForm(form)){
			validaIndicadores();
			submeterFormPadrao(form);
		}
	}
	
	function debitosCreditosSelecionados(form){
	
	    retorno = false;
		for(indice = 0; indice < form.elements.length; indice++){
			if (form.elements[indice].type == "checkbox" && form.elements[indice].checked == true) {
				retorno = true;
				break;
			}else {
				if(form.elements[indice].type == 'select-one' && form.elements[indice].selectedIndex != -1) { 
					retorno = true;
					break;
				}
			}
		}
		if (!retorno){
				alert('N�o existem d�bitos/cr�ditos selecionados para emiss�o do extrato de d�bitos.');
		}
		else{
		
			var idsConta = obterValorCheckboxMarcadoPorNome("conta");
			var idsDebito = obterValorComboboxMarcadoPorNome("idsDebito");
			var idsCredito = obterValorCheckboxMarcadoPorNome("credito");
			var idsGuiaPagamento = obterValorCheckboxMarcadoPorNome("guiaPagamento");
			var idsParcelamento = obterValorComboboxMarcadoPorNome("idsParcelamento");
			var urlTransferencia = "/gsan/emissaoExtratoDebitoAction.do?";
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
			if (idsParcelamento != null && idsParcelamento != ''){
				if (concatenador){
					urlTransferencia = urlTransferencia + "&parcelamento=" + idsParcelamento;
				}
				else{
					urlTransferencia = urlTransferencia + "parcelamento=" + idsParcelamento;
					concatenador = true;
				}
			}
			
			urlTransferencia = urlTransferencia + "&extratoDebito=1";
			form.action = urlTransferencia;
			submeterFormPadrao(form);
		}
	}
	
	function marcarCombosParcelas() {
		form = document.forms[0];
		for(indice = 0; indice < form.elements.length; indice++){
			if (form.elements[indice].type == "select-one") {
				form.elements[indice].selectedIndex = 1;			
			}
		}
	}
	
	
	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){
		if(objetoRelacionado.disabled != true){
			if (objeto == null || codigoObjeto == null){
				abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
			} else{
				if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
					alert(msg);
				} else{
					abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
				}
			}
		}
	}

	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	    var form = document.forms[0];
	    
	   	    
	    if (tipoConsulta == 'imovel') {

		    form.idImovel.value = codigoRegistro;
		    form.inscricaoImovel.value = descricaoRegistro;
		    form.inscricaoImovel.style.color = "#000000";
	    	reloadImovel();
	    }
	}
		
	function reloadImovel() {
		var form = document.forms[0];
		form.action = "/gsan/exibirDebitoCreditoDadosSelecaoExtratoAction.do?menu=sim";
		submeterFormPadrao(form);
	}
  	
  	function limparImovel() {

    	var form = document.forms[0];

    	form.idImovel.value = "";
		form.inscricaoImovel.value = "";
		form.inscricaoImovel.style.color = "#000000";
		form.nomeClienteUsuarioImovel.value = "";
		form.descricaoLigacaoAguaSituacaoImovel.value = "";
		form.descricaoLigacaoEsgotoSituacaoImovel.value = "";
		form.permitirSelecaoAcrescimoExtrato.value = "";
				
		//form.idImovel.focus();
		reloadImovel();
  	}
  
  	function limpar(){
  		limparImovel();
  	} 	
  	
  	
	String.prototype.trim = function() {
    	return this.replace(/^\s*/, "").replace(/\s*$/, "");
	}
  	
  	function validaCheck(){
     	validaCheckConta();
	    validaCheckDebito();
	    validaCheckCredito();
	    validaCheckGuia();
	    validaCheckParcelamento();
  	}
  	
  	
  	function validaCheckConta(){
    	var form = document.forms[0];  	
  	
  		var idContas = form.idsConta.value;
		myString = new String(idContas);
		splitString = myString.split(",");
		
		for (i=0; i< splitString.length; i++) {
			chave  = splitString[i];
			for(indice = 0; indice < form.elements.length; indice++){
				if (form.elements[indice].type == "checkbox" && 
					form.elements[indice].name == 'conta' && 
					form.elements[indice].value.trim() == chave.trim()) {

					form.elements[indice].checked = true;
				}
			}
		}
  	}
  	
  	function validaCheckDebito(){
    	var form = document.forms[0];  	
  	
  		var idDebitos = form.idsDebito.value;
		myString = new String(idDebitos);
		splitString = myString.split(",");
		
		for (i=0; i< splitString.length; i++) {
			chave  = splitString[i];
			for(indice = 0; indice < form.elements.length; indice++){
				if (form.elements[indice].type == "checkbox" && 
					form.elements[indice].name == 'debito' && 
					form.elements[indice].value.trim() == chave.trim()) {

					form.elements[indice].checked = true;
				}
			}
		}
  	}
  	
  	function validaCheckCredito(){
    	var form = document.forms[0];  	
  	
  		var idCreditos = form.idsCredito.value;
		myString = new String(idCreditos);
		splitString = myString.split(",");
		
		for (i=0; i< splitString.length; i++) {
			chave  = splitString[i];
			for(indice = 0; indice < form.elements.length; indice++){
				if (form.elements[indice].type == "checkbox" && 
					form.elements[indice].name == 'credito' && 
					form.elements[indice].value.trim() == chave.trim()) {

					form.elements[indice].checked = true;
				}
			}
		}
  	}
  	
  	function validaCheckGuia(){
    	var form = document.forms[0];  	
  	
  		var idGuias = form.idsGuia.value;
		myString = new String(idGuias);
		splitString = myString.split(",");
		
		for (i=0; i< splitString.length; i++) {
			chave  = splitString[i];
			for(indice = 0; indice < form.elements.length; indice++){
				if (form.elements[indice].type == "checkbox" && 
					form.elements[indice].name == 'guiaPagamento' && 
					form.elements[indice].value.trim() == chave.trim()) {

					form.elements[indice].checked = true;
				}
			}
		}
  	}
  	
  	function validaCheckParcelamento(){
    	var form = document.forms[0];  	
  	
  		var idParcelamentos = form.idsParcelamento.value;
		myString = new String(idParcelamentos);
		splitString = myString.split(",");
		
		for (i=0; i< splitString.length; i++) {
			chave  = splitString[i];
			for(indice = 0; indice < form.elements.length; indice++){
				if (form.elements[indice].type == "checkbox" && 
					form.elements[indice].name == 'parcelamento' && 
					form.elements[indice].value.trim() == chave.trim()) {

					form.elements[indice].checked = true;
				}
			}
		}
  	}
  	
	function facilitador(objeto,nome){

		if (objeto.value == "0" || objeto.id == undefined){

			objeto.value = "1";
			marcarTodosExtrato(nome);
			
		} else{
			
			objeto.value = "0";
			desmarcarTodosExtrato(nome);
		}
	}
	
	//Ativa todos os elementos do tipo checkbox do formul?rio existente
	function marcarTodosExtrato(nome){
	
		for (var i=0;i < document.forms[0].elements.length;i++){
			var elemento = document.forms[0].elements[i];
			if (elemento.type == "checkbox" && elemento.name == nome && elemento.value != '-1'){
				elemento.checked = true;
			}
		}
	}

	//Desativa todos os elementos do tipo checkbox do formul?rio existente
	function desmarcarTodosExtrato(nome) {
	
		for (var i=0;i < document.forms[0].elements.length;i++){
			var elemento = document.forms[0].elements[i];
			if (elemento.type == "checkbox" && elemento.name == nome){
				elemento.checked = false;
			}
		}
	}	
	
	function validaIndicadores(){
		var form = document.forms[0];
		form.indicadorMulta.value = document.getElementById("checkIndicadorMulta").value;
		form.indicadorJurosMora.value = document.getElementById("checkIndicadorJurosMora").value;
		form.indicadorAtualizacaoMonetaria.value = document.getElementById("checkIndicadorAtualizacaoMonetaria").value;		
	}
	
	function validaMulta(){
		if(document.getElementById("checkIndicadorMulta").value == <%=ConstantesSistema.SIM.toString()%>){
			document.getElementById("checkIndicadorMulta").value = <%=ConstantesSistema.NAO.toString()%>;
		}else{
			document.getElementById("checkIndicadorMulta").value = <%=ConstantesSistema.SIM.toString()%>;
		}
	}
	function validaJurosMora(){		
		if(document.getElementById("checkIndicadorJurosMora").value == <%=ConstantesSistema.SIM.toString()%>){
			document.getElementById("checkIndicadorJurosMora").value = <%=ConstantesSistema.NAO.toString()%>;
		}else{
			document.getElementById("checkIndicadorJurosMora").value = <%=ConstantesSistema.SIM.toString()%>;
		}		
	}
	function validaAtualizacaoMonetaria(){
		if(document.getElementById("checkIndicadorAtualizacaoMonetaria").value == <%=ConstantesSistema.SIM.toString()%>){
			document.getElementById("checkIndicadorAtualizacaoMonetaria").value = <%=ConstantesSistema.NAO.toString()%>;
		}else{
			document.getElementById("checkIndicadorAtualizacaoMonetaria").value = <%=ConstantesSistema.SIM.toString()%>;
		}
	}

</SCRIPT>

</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');validaCheck();validaIndicadores();">

<html:form action="/exibirDebitoCreditoDadosSelecaoExtratoAction" method="post">

<html:hidden property="idsConta"/>
<html:hidden property="idsDebito"/>
<html:hidden property="idsCredito"/>
<html:hidden property="idsGuia"/>
<html:hidden property="idsParcelamento"/>
<html:hidden property="permitirSelecaoAcrescimoExtrato"/>
<html:hidden property="indicadorMulta"/>
<html:hidden property="indicadorJurosMora"/>
<html:hidden property="indicadorAtualizacaoMonetaria"/>

<input type="hidden" name="checkConta" value="0">
<input type="hidden" name="checkCredito" value="0">
<input type="hidden" name="checkDebito" value="0">
<input type="hidden" name="checkGuia" value="0">
<input type="hidden" name="checkParcelamento" value="0">

<%@ include file="/jsp/util/cabecalho.jsp"%>

<% boolean tem = false; %>	
	<logic:present name="popup" scope="request" >
	<logic:equal name="popup" scope="request" value="sim">
		<% tem = true; %>
		<input type="hidden" name="popup" value="sim"/> 
	</logic:equal>
	</logic:present>
	
<% if (!tem) { %>
		<%@ include file="/jsp/util/menu.jsp" %>
<% } %>

<table width="770" border="0" cellspacing="4" cellpadding="0">

<% if (!tem) { %>
			<td width="149" valign="top" class="leftcoltext">
			<input type="hidden" name="numeroPagina" value="1"/>
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
 <% } %>
	<td width="625" valign="top" class="centercoltext">

        <table height="100%">
        <tr>
          <td></td>
        </tr>
      	</table>

      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
          <td class="parabg">Extrato de D�bitos</td>
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
			<td><strong>Im�vel:</strong></td>
			<td>
				<html:text property="idImovel" size="10" maxlength="10" tabindex="2"
				onkeypress="validaEnterComMensagem(event, 'exibirDebitoCreditoDadosSelecaoExtratoAction.do?pesquisarImovel=SIM', 'idImovel', 'Matr�cula');" /> 
				<a 
				href="javascript:chamarPopup('exibirPesquisarImovelAction.do', 'imovel', null, null, 490, 800, '', document.forms[0].idImovel);">
				<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23" height="21" alt="Pesquisar" border="0">
				</a> 

				<logic:present name="corImovel">
					<logic:equal name="corImovel" value="exception">
						<html:text property="inscricaoImovel" size="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" />
					</logic:equal>

					<logic:notEqual name="corImovel" value="exception">
						<html:text property="inscricaoImovel" size="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
					</logic:notEqual>
				</logic:present> 

				<logic:notPresent name="corImovel">
					<logic:empty name="DebitoCreditoDadosSelecaoExtratoActionForm" property="idImovel">
						<html:text property="inscricaoImovel" value="" size="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" />
					</logic:empty>
					
					<logic:notEmpty name="DebitoCreditoDadosSelecaoExtratoActionForm" property="idImovel">
						<html:text property="inscricaoImovel" size="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
					</logic:notEmpty>
				</logic:notPresent> 

				<a href="javascript:limparImovel();"> <img src="<bean:message key='caminho.imagens'/>limparcampo.gif"
				alt="Apagar" border="0"></a>
			</td>
		</tr>
		
		<tr>
			<td><strong>Cliente:</strong></td>
			<td>
	   			<html:text property="nomeClienteUsuarioImovel" size="50" maxlength="50" readonly="true"
				style="background-color:#EFEFEF; border:0; color: #000000"/>
			</td>
		</tr>
		
		<tr>
			<td><strong>Sit. da Lig. de �gua:</strong></td>
			<td>
	   			<html:text property="descricaoLigacaoAguaSituacaoImovel" size="20" maxlength="20" readonly="true"
				style="background-color:#EFEFEF; border:0; color: #000000"/>
				
				<strong>&nbsp;&nbsp;Sit. da Lig. de Esgoto:&nbsp;&nbsp;</strong>
				
				<html:text property="descricaoLigacaoEsgotoSituacaoImovel" size="20" maxlength="20" readonly="true"
				style="background-color:#EFEFEF; border:0; color: #000000"/>
			</td>
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
		
			<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td>
				
					<table width="100%" cellpadding="0" cellspacing="0">
					<tr bgcolor="#79bbfd">
						<td colspan="6" height="20"><strong>Contas</strong></td>
					</tr>
					<tr bgcolor="#90c7fc">

						<td width="5%" height="20">
							<strong><a href="javascript:facilitador(document.forms[0].checkConta,'conta');" id="0">Todos</a></strong>
						</td>
						<td width="15%">
							<div align="center"><strong>M�s/Ano</strong></div>
						</td>
						<td width="20%">
							<div align="center"><strong>Vencimento</strong></div>
						</td>
						<td width="20%">
							<div align="center"><strong>Valor</strong></div>
						</td>
						<td width="20%">
							<div align="center"><strong>Acr�s. Impont.</strong></div>
						</td>
						<td width="20%">
							<div align="center"><strong>Situa��o</strong></div>
						</td>

					</tr>
					</table>

				</td>
			</tr>
			
			
			<%BigDecimal valorTotalConta = new BigDecimal("0.00");%>
			<%BigDecimal valorTotalAcrescimo = new BigDecimal("0.00");%>

			<logic:present name="colecaoContaExtrato">
			
			<logic:notEmpty name="colecaoContaExtrato">

			<tr>
				<td>
										
					<% String cor = "#cbe5fe";%>

					<div style="width: 100%; height: 100; overflow: auto;">
										
					<table width="100%" align="center" bgcolor="#90c7fc">
										
					<logic:iterate name="colecaoContaExtrato" id="conta" type="ContaValoresHelper">

						
						<%valorTotalAcrescimo = valorTotalAcrescimo.add(conta.getValorTotalContaValoresParcelamento()); %>
						
						<%	if (cor.equalsIgnoreCase("#cbe5fe")){
							cor = "#FFFFFF";%>
							<tr bgcolor="#FFFFFF">
						<%} else{
							cor = "#cbe5fe";%>
							<tr bgcolor="#cbe5fe">
						<%}%>
												
						
						<logic:notEmpty name="conta" property="contaPermitida">						
							<logic:equal name="conta" property="contaPermitida" value="<%= ConstantesSistema.SIM.toString()%>">
								<td align="center" width="5%">
									<INPUT TYPE="checkbox" NAME="conta" value="<%="" + conta.getConta().getId().intValue() %>">
								</td>
							</logic:equal>
							<logic:equal name="conta" property="contaPermitida" value="<%= ConstantesSistema.NAO.toString()%>">
								<td align="center" width="5%">
									<INPUT TYPE="checkbox" disabled="disabled" NAME="conta" value="<%="" + conta.getConta().getId().intValue() %>">
								</td>
							</logic:equal>
						</logic:notEmpty>
						
						<logic:empty name="conta" property="contaPermitida">						
							<td align="center" width="5%">
								<INPUT TYPE="checkbox" NAME="conta" value="<%="" + conta.getConta().getId().intValue() %>">
							</td>
						</logic:empty>
						
						<logic:empty name="conta" property="conta.contaMotivoRevisao">
						
							<td width="15%" align="center">
							<font color="#000000"> 
								<a href="javascript:abrirPopup('exibirConsultarContaAction.do?contaID=<%="" + conta.getConta().getId() %>&tipoConsulta=conta', 600, 800);">
								<%=""+ Util.formatarMesAnoReferencia(conta.getConta().getReferencia())%> </a>
							</font>	
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
										<%valorTotalConta = valorTotalConta.add(new BigDecimal(conta.getConta().getValorTotalConta())); %>
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
									" Atualiza��o Monet�ria: " + Util.formatarMoedaReal(conta.getValorAtualizacaoMonetaria()).trim()%>">
										
										<%="" + Util.formatarMoedaReal(conta.getValorTotalContaValoresParcelamento()).trim()%>
									</a>
									</logic:notEqual>
									
								</div>
							</td>
							<td width="20%">
								<div align="center">
														
									<logic:present name="conta" property="conta.debitoCreditoSituacaoAtual">
										<font color="#000000"> 
											<bean:write name="conta" property="conta.debitoCreditoSituacaoAtual.descricaoDebitoCreditoSituacao" />
										</font>
									</logic:present> 
														
									<logic:notPresent name="conta" property="conta.debitoCreditoSituacaoAtual">
										&nbsp;
									</logic:notPresent>
														
								</div>
							</td>
							
						</logic:empty>
						
						
						
						
						
						
						<logic:notEmpty name="conta" property="conta.contaMotivoRevisao">
							<td width="15%" align="center">
								<font color="#ff0000"> 
									<a href="javascript:abrirPopup('exibirConsultarContaAction.do?contaID=<%="" + conta.getConta().getId() %>&tipoConsulta=conta', 600, 800);">
									<font color="#ff0000"><%=""+ Util.formatarMesAnoReferencia(conta.getConta().getReferencia())%> </font></a>
								</font>
							</td>
							<td width="20%">
								<div align="center">
									<logic:present name="conta" property="conta.dataVencimentoConta">
										<span style="color: #ff0000;">
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
									<span style="color: #ff0000;">
										<%="" + Util.formatarMoedaReal(new BigDecimal(conta.getConta().getValorTotalConta())).trim()%>
										<%valorTotalConta = valorTotalConta.add(new BigDecimal(conta.getConta().getValorTotalConta())); %>
									</span>
								</div>
							</td>
							<td width="20%">
								<div align="right">
									
									<logic:equal name="conta" property="valorTotalContaValoresParcelamento" value="0.00">
									<span style="color: #ff0000;">
										<%="" + Util.formatarMoedaReal(conta.getValorTotalContaValoresParcelamento()).trim()%>
									</span>
									</logic:equal>
									
									<logic:notEqual name="conta" property="valorTotalContaValoresParcelamento" value="0.00">
									<a title="<%="Multa: " + Util.formatarMoedaReal(conta.getValorMulta()).trim() + 
									" Juros de Mora: " + Util.formatarMoedaReal(conta.getValorJurosMora()).trim() +
									" Atualiza��o Monet�ria: " + Util.formatarMoedaReal(conta.getValorAtualizacaoMonetaria()).trim()%>">
										<span style="color: #ff0000;">
											<%="" + Util.formatarMoedaReal(conta.getValorTotalContaValoresParcelamento()).trim()%>
										</span>
									</a>
									</logic:notEqual>
									
								</div>
							</td>
							<td width="20%">
								<div align="center">
														
									<logic:present name="conta" property="conta.debitoCreditoSituacaoAtual">
										<font color="#ff0000"> 
											<bean:write name="conta" property="conta.debitoCreditoSituacaoAtual.descricaoDebitoCreditoSituacao" />
										</font>
									</logic:present> 
														
									<logic:notPresent name="conta" property="conta.debitoCreditoSituacaoAtual">
										&nbsp;
									</logic:notPresent>
														
								</div>
							</td>
							
						</logic:notEmpty>
						
						
						
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
						<td colspan="6" height="20"><strong>D�bitos</strong></td>
					</tr>
					<tr bgcolor="#90c7fc">

						<td width="15%" height="20">
							<strong><a href="javascript:facilitador(document.forms[0].checkDebito,'debito');"id="0">Todos</a></strong>
						</td>
						<td width="30%">
							<div align="center"><strong>Tipo do D�bito</strong></div>
						</td>
						<td width="15%">
							<div align="center"><strong>M�s/Ano Ref.</strong></div>
						</td>
						<td width="15%">
							<div align="center"><strong>M�s/Ano Cobr.</strong></div>
						</td>
						<td width="10%">
							<div align="center"><strong>Parc.</strong></div>
						</td>
						<td width="10%">
							<div align="center"><strong>Vl. Restante</strong></div>
						</td>

					</tr>
					</table>

				</td>
			</tr>
			
			
			<%BigDecimal valorTotalDebito = new BigDecimal("0.00");%>
			
			<logic:present name="colecaoDebitoACobrarExtrato">
			
			<logic:notEmpty name="colecaoDebitoACobrarExtrato">

			<tr>
				<td>
										
					<% String cor1 = "#cbe5fe";%>

					<div style="width: 100%; height: 100; overflow: auto;">
										
					<table width="100%" align="center" bgcolor="#90c7fc">
										
					<logic:iterate name="colecaoDebitoACobrarExtrato" id="debitoACobrar" type="DebitoACobrar">

						<%valorTotalDebito = valorTotalDebito.add(debitoACobrar.getValorRestanteCobrado()); %>
						
						<%	if (cor1.equalsIgnoreCase("#cbe5fe")){
							cor1 = "#FFFFFF";%>
							<tr bgcolor="#FFFFFF">
						<%} else{
							cor1 = "#cbe5fe";%>
							<tr bgcolor="#cbe5fe">
						<%}%>
												
						<td align="center" width="15%">
							<html:select property="idsDebito" >
								<option value="-1">Nenhum</option>
								<option value="${debitoACobrar.id}_todas" >Todos</option>
								<%for(int i = (debitoACobrar.getNumeroPrestacaoDebito() - debitoACobrar.getNumeroPrestacaoCobradas()) - 1; i > 0; i--){%>
									<option value="<%=debitoACobrar.getId() + "_" + i%>" ><%=i%></option>
									<%} %>
							</html:select>
						</td>				
						<!-- <td align="center" width="5%">
							<INPUT TYPE="checkbox" NAME="debito" value="<%="" + debitoACobrar.getId().intValue() %>">
						</td> -->
						<td width="30%" align="left">
							<a href="javascript:abrirPopup('exibirConsultarDebitoACobrarAction.do?imovelID=<%="" + debitoACobrar.getImovel().getId().intValue() %>&debitoID=<%="" + debitoACobrar.getId().intValue() %>', 570, 720);">
							<bean:write name="debitoACobrar" property="debitoTipo.descricao" /></a>
						</td>
						<td width="10%">
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
						<td width="10%">
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
						<td width="7%">
							<div align="center">
								
								<bean:write name="debitoACobrar" property="numeroPrestacaoCobradas" /> /
								<bean:write name="debitoACobrar" property="numeroPrestacaoDebito" />
								
							</div>
						</td>
						<td width="20%">
							<div align="right">
								<!--  					
								<%="" + Util.formatarMoedaReal(debitoACobrar.getValorRestanteCobrado()).trim()%>
										-->
								<bean:write name="debitoACobrar" property="valorRestanteCobrado" formatKey="money.format"/>			
							</div>
						</td>
					</tr>
			
					</logic:iterate>
					
						<%if (cor1.equalsIgnoreCase("#cbe5fe")){
							cor1 = "#FFFFFF";%>
							<tr bgcolor="#FFFFFF">
						<%} else{
							cor1 = "#cbe5fe";%>
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
						<td colspan="6" height="20"><strong>Cr�ditos</strong></td>
					</tr>
					<tr bgcolor="#90c7fc">

						<td width="5%" height="20">
							<strong><a href="javascript:facilitador(document.forms[0].checkCredito,'credito');"id="0">Todos</a></strong>
						</td>
						<td width="30%">
							<div align="center"><strong>Tipo do Cr�dito</strong></div>
						</td>
						<td width="20%">
							<div align="center"><strong>M�s/Ano Ref.</strong></div>
						</td>
						<td width="20%">
							<div align="center"><strong>M�s/Ano Cobr.</strong></div>
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
			
			
			<%BigDecimal valorTotalCredito = new BigDecimal("0.00");%>
			
			<logic:present name="colecaoCreditoARealizarExtrato">
			
			<logic:notEmpty name="colecaoCreditoARealizarExtrato">

			<tr>
				<td>
										
					<% String cor2 = "#cbe5fe";%>

					<div style="width: 100%; height: 100; overflow: auto;">
										
					<table width="100%" align="center" bgcolor="#90c7fc">
										
					<logic:iterate name="colecaoCreditoARealizarExtrato" id="creditoARealizar" type="CreditoARealizar">

						<%valorTotalCredito = valorTotalCredito.add(creditoARealizar.getValorTotal()); %>
						
						<%	if (cor2.equalsIgnoreCase("#cbe5fe")){
							cor2 = "#FFFFFF";%>
							<tr bgcolor="#FFFFFF">
						<%} else{
							cor2 = "#cbe5fe";%>
							<tr bgcolor="#cbe5fe">
						<%}%>																				
												
						<td align="center" width="5%">
							<logic:notEqual name="creditoARealizar" property="creditoTipo.id" value="<%="" + CreditoTipo.DESCONTO_ACRESCIMOS_IMPONTUALIDADE %>">
								<INPUT TYPE="checkbox" NAME="credito" value="<%="" + creditoARealizar.getId().intValue() %>"/> 
							</logic:notEqual>
							<logic:equal name="creditoARealizar" property="creditoTipo.id" value="<%="" + CreditoTipo.DESCONTO_ACRESCIMOS_IMPONTUALIDADE %>">
								<INPUT TYPE="checkbox" NAME="credito" disabled="disabled"/> 
							</logic:equal>
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
									<!--  				
								<%="" + Util.formatarMoedaReal(creditoARealizar.getValorTotal()).trim()%>
									-->				
									<bean:write name="creditoARealizar" property="valorTotal" formatKey="money.format" />
							</div>
						</td>
					</tr>
			
					</logic:iterate>
					
						<%if (cor2.equalsIgnoreCase("#cbe5fe")){
							cor2 = "#FFFFFF";%>
							<tr bgcolor="#FFFFFF">
						<%} else{
							cor2 = "#cbe5fe";%>
							<tr bgcolor="#cbe5fe">
						<%}%>

						<td width="5%" height="20"></td>
						<td width="20%">
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
						<td colspan="6" height="20"><strong>Guias de Pagamento</strong></td>
					</tr>
					<tr bgcolor="#90c7fc">

						<td width="5%" height="20">
							<strong><a href="javascript:facilitador(document.forms[0].checkGuia,'guiaPagamento');"id="0">Todos</a></strong>
						</td>
						<td width="23%">
							<div align="center"><strong>N�mero da Guia</strong></div>
						</td>
						<td width="10%">
							<div align="center"><strong>Presta��o</strong></div>
						</td>
						<td width="20%">
							<div align="center"><strong>Emiss�o</strong></div>
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
			
			<%BigDecimal valorTotalGuiaPagamento = new BigDecimal("0.00");%>
			
			<logic:present name="colecaoGuiaPagamentoExtrato">
			
			<logic:notEmpty name="colecaoGuiaPagamentoExtrato">

			<tr>
				<td>
										
					<% String cor3 = "#cbe5fe";%>

					<div style="width: 100%; height: 100; overflow: auto;">
										
					<table width="100%" align="center" bgcolor="#90c7fc">
										
					<logic:iterate name="colecaoGuiaPagamentoExtrato" id="guiaPagamentoValoresHelper" type="GuiaPagamentoValoresHelper">

						<%valorTotalGuiaPagamento = valorTotalGuiaPagamento.add(guiaPagamentoValoresHelper.getValorTotalPrestacao()); %>
						
						<%	if (cor3.equalsIgnoreCase("#cbe5fe")){
							cor3 = "#FFFFFF";%>
							<tr bgcolor="#FFFFFF">
						<%} else{
							cor3 = "#cbe5fe";%>
							<tr bgcolor="#cbe5fe">
						<%}%>
						
						<logic:equal name="guiaPagamentoValoresHelper" property="desativarSelecao" value="<%= ConstantesSistema.SIM.toString()%>">
							<td align="center" width="5%">
								<INPUT TYPE="checkbox" NAME="guiaPagamento" disabled="disabled" value="-1">
							</td>
						</logic:equal>

						<logic:notEqual name="guiaPagamentoValoresHelper" property="desativarSelecao" value="<%= ConstantesSistema.SIM.toString()%>">
							<td align="center" width="5%">
								<INPUT TYPE="checkbox" NAME="guiaPagamento"
								value="<%="" + guiaPagamentoValoresHelper.getIdGuiaPagamento() + "_"%><bean:write name="guiaPagamentoValoresHelper" property="numeroPrestacao" />">
							</td>
						</logic:notEqual>

						<td width="23%" align="left">
							<a href="javascript:abrirPopup('exibirConsultarGuiaPagamentoAction.do?guiaPagamentoId=<%="" + guiaPagamentoValoresHelper.getIdGuiaPagamento() %>', 600, 800);">
								<%="" + guiaPagamentoValoresHelper.getIdGuiaPagamento() %>
							</a>
						</td>
						<td width="10%">
							<div align="center">
								<bean:write name="guiaPagamentoValoresHelper" property="numeroPrestacao" />
							</div>
						</td>
						
						<td width="20%">
							<div align="center">
								<logic:notEmpty name="guiaPagamentoValoresHelper" property="dataEmissao">
									<span style="color: #000000;">
										<bean:write name="guiaPagamentoValoresHelper" property="dataEmissao" formatKey="date.format"/>
									</span>
								</logic:notEmpty>
								<logic:empty name="guiaPagamentoValoresHelper" property="dataEmissao">
									&nbsp;
								</logic:empty>
							</div>
						</td>
						<td width="20%">
							<div align="center">
									<logic:notEmpty name="guiaPagamentoValoresHelper" property="dataVencimento">
										<span style="color: #000000;">
											<bean:write name="guiaPagamentoValoresHelper" property="dataVencimento" formatKey="date.format"/>
										</span>
									</logic:notEmpty>
									<logic:empty name="guiaPagamentoValoresHelper" property="dataVencimento">
										&nbsp;
									</logic:empty>
							</div>
						</td>
						<td width="20%">
							<div align="right">
								<bean:write name="guiaPagamentoValoresHelper" property="valorTotalPrestacao" formatKey="money.format" />
								<!--  <%="" + Util.formatarMoedaReal(guiaPagamentoValoresHelper.getValorTotalPrestacao())%> -->
							</div>
						</td>
					</tr>
			
					</logic:iterate>
					
						<%if (cor3.equalsIgnoreCase("#cbe5fe")){
							cor3 = "#FFFFFF";%>
							<tr bgcolor="#FFFFFF">
						<%} else{
							cor3 = "#cbe5fe";%>
							<tr bgcolor="#cbe5fe">
						<%}%>

						<td width="5%" height="20"></td>
						<td width="25%">
							<div align="center"><strong>Total:</strong></div>
						</td>
						<td width="10%"></td>
						<td width="15%"></td>
						<td width="15%"></td>
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
						<td colspan="6" height="20"><strong>Parcelamento</strong></td>
					</tr>
					<tr bgcolor="#90c7fc">
						<td width="16%" height="20">
							<strong><a href="javascript:marcarCombosParcelas();" id="0">Todos</a></strong>
						</td>
						<td width="16%">
							<div align="center"><strong>Data</strong></div>
						</td>
						<td width="16%">
							<div align="center"><strong>Mes/Ano Ref.</strong></div>
						</td>
						<td width="16%">
							<div align="center"><strong>Mes/Ano Cobr.</strong></div>
						</td>
						
						<td width="16%">
							<div align="center"><strong>Parc.</strong></div>
						</td>
						<td width="16%">
							<div align="center"><strong>Valor</strong></div>
						</td>
					</table>

				</td>
			</tr>
			
			
			<%BigDecimal valorTotalParcelamento = new BigDecimal("0.00");%>

			<logic:present name="colecaoDebitoCreditoParcelamento">
			
			<logic:notEmpty name="colecaoDebitoCreditoParcelamento">

			<tr>
				<td>
										
					<% String cor0 = "#cbe5fe";%>

					<div style="width: 100%; height: 100; overflow: auto;">
										
					<table width="100%" align="center" bgcolor="#90c7fc">
										
					<logic:iterate name="colecaoDebitoCreditoParcelamento" id="debitoCredito" type="DebitoCreditoParcelamentoHelper">
																		
						<%valorTotalParcelamento = valorTotalParcelamento.add(debitoCredito.getValorTotalDebito()); %>
						
						<%	if (cor0.equalsIgnoreCase("#cbe5fe")){
							cor0 = "#FFFFFF";%>
							<tr bgcolor="#FFFFFF">
						<%} else{
							cor0 = "#cbe5fe";%>
							<tr bgcolor="#cbe5fe">
						<%}%>						
						<td align="center" width="16%">
							<html:select property="idsParcelamento" >
								<option value="-1">Nenhum</option>
								<option value="${debitoCredito.parcelamento.id}_todas" <c:if test="${debitoCredito.selecaoPermitida eq false}">disabled="disabled"</c:if>>Todos</option>
								<c:forEach items="${debitoCredito.quantidadeParcelasAberto}" var="parcelas">
									<option value="${debitoCredito.parcelamento.id}_${parcelas}" <c:if test="${debitoCredito.selecaoPermitida eq false}">disabled="disabled"</c:if>  >${parcelas}</option>
								</c:forEach>
							</html:select>
						</td>
						
						<td width="16%" align="center">
						
							<div align="left">
								<a href="javascript:abrirPopup('exibirConsultarParcelamentoDebitoPopupAction.do?
								codigoImovel=<bean:define name="debitoCredito" property="parcelamento.imovel" id="imovel" />
								<bean:write name="imovel" property="id" />
								&codigoParcelamento=<bean:write name="debitoCredito" property="parcelamento.id" />', 400, 800);">
								<bean:write name="debitoCredito" property="parcelamento.ultimaAlteracao" formatKey="date.format" /></a>
							</div>
						
						</td>
						<td width="16%" align="center"><%=Util.formatarMesAnoReferencia(debitoCredito.getParcelamento().getAnoMesReferenciaFaturamento())%></td>
						<td width="18%" align="center"><%=Util.formatarMesAnoReferencia(debitoCredito.getParcelamento().getAnoMesReferenciaFaturamento())%></td>
						<td width="10%" align="center"><%=debitoCredito.getParcelamento().getNumeroPrestacoes() - ( debitoCredito.getQuantidadeParcelasAberto() != null ? (debitoCredito.getQuantidadeParcelasAberto().size() +1) : 0) %> / <bean:write name="debitoCredito" property="parcelamento.numeroPrestacoes" /></td>
						<td width="16%">
							<div align="right">
								<span style="color: #000000;">
									<!-- <%="" + Util.formatarMoedaReal(debitoCredito.getValorTotalDebito()).trim()%> -->
									<bean:write name="debitoCredito" property="valorTotalDebito" formatKey="money.format" />
								</span>
							</div>
						</td>
					</tr>
			
					</logic:iterate>
					
						<%if (cor0.equalsIgnoreCase("#cbe5fe")){
							cor0 = "#FFFFFF";%>
							<tr bgcolor="#FFFFFF">
						<%} else{
							cor0 = "#cbe5fe";%>
							<tr bgcolor="#cbe5fe">
						<%}%>

						<td height="20"></td>
						<td >
							<div align="center"><strong>Total:</strong></div>
						</td>
						<td width="50%"colspan="4">
							<div align="right"><strong>
								<%="" + Util.formatarMoedaReal(valorTotalParcelamento).trim()%>
							</strong></div>
						</td>

					</tr>
										
					</table>
										
					</div>
					
				</td>
			</tr>

			</logic:notEmpty>
			
			</logic:present>
			
			</table>
			
		</td>
	</tr>
	</table>
	
	<table width="100%" border="0">
	<tr>
		<td HEIGHT="10">
			
		</td>
	</tr>
	</table>
	
	<%BigDecimal valorTotalDebitosFinal = valorTotalConta.add(valorTotalDebito); %>
	<%valorTotalDebitosFinal = valorTotalDebitosFinal.add(valorTotalGuiaPagamento); %>
	<%valorTotalDebitosFinal = valorTotalDebitosFinal.subtract(valorTotalCredito); %>
	<%valorTotalDebitosFinal = valorTotalDebitosFinal.add(valorTotalParcelamento); %>
	
	
	<%BigDecimal valorTotalDebitosAtualizado = valorTotalDebitosFinal.add(valorTotalAcrescimo); %>
	
	<table width="100%" border="0">
	<tr>
		<td HEIGHT="20"><strong>Total dos D�bitos:</strong></td>
		<td><div align="right"><strong><%="" + Util.formatarMoedaReal(valorTotalDebitosFinal).trim()%></strong></div></td>
	</tr>
	<tr>
		<td HEIGHT="20"><strong>Total dos D�bitos Atualizados:</strong></td>
		<td><div align="right"><strong><%="" + Util.formatarMoedaReal(valorTotalDebitosAtualizado).trim()%></strong></div></td>
	</tr>
	</table>
	
	<table width="100%" border="0">
	<tr>
		<td HEIGHT="10"></td>
	</tr>
	</table>
	 
	 <table>
		<tr>
			<td>
			
			<logic:present name="habilitaIncluirDebito" >
				<a href="javascript:abrirPopup('exibirInserirDebitoACobrarPopupAction.do?limparForm=1', 350, 610);">
				<strong><%="Incluir D�bito"%> </strong></a>
			</logic:present>
			
			<logic:notPresent name="habilitaIncluirDebito" >
				<a>
				<strong><%="Incluir D�bito"%> </strong></a>
			</logic:notPresent>
				
			</td>
		
		</tr>
	</table>
	<br>
	<table width="100%" border="0">
		<tr>
			<td colspan="7"><strong>Acr�scimos Cobrados:</strong></td>
		</tr>
		<tr>
			<td>
				<strong></strong>
			</td>
			<td>
				<logic:equal name="DebitoCreditoDadosSelecaoExtratoActionForm" property="permitirSelecaoAcrescimoExtrato"  value="<%=ConstantesSistema.NAO.toString()%>">
					<input type="checkbox" name="checkIndicadorMulta" id="checkIndicadorMulta" disabled="disabled" checked="checked" onclick="javascript:validaMulta();validaIndicadores();reloadImovel();" value="<%= ConstantesSistema.SIM.toString()%>"/> 
				</logic:equal>
				<logic:equal name="DebitoCreditoDadosSelecaoExtratoActionForm" property="permitirSelecaoAcrescimoExtrato" value="<%=ConstantesSistema.SIM.toString()%>">
					<logic:present name="indicadorMulta" scope="request">
						<logic:equal name="indicadorMulta" scope="request" value="<%=ConstantesSistema.SIM.toString()%>">
							<input type="checkbox" name="checkIndicadorMulta" id="checkIndicadorMulta" checked="checked" onclick="javascript:validaMulta();validaIndicadores();reloadImovel();" value="<%= ConstantesSistema.SIM.toString()%>"/> 
						</logic:equal>
						<logic:equal name="indicadorMulta" scope="request" value="<%=ConstantesSistema.NAO.toString()%>">
							<input type="checkbox" name="checkIndicadorMulta" id="checkIndicadorMulta" onclick="javascript:validaMulta();validaIndicadores();reloadImovel();" value="<%= ConstantesSistema.NAO.toString()%>"/> 
						</logic:equal>
					</logic:present>
					<logic:notPresent name="indicadorMulta" scope="request">
						<input type="checkbox" name="checkIndicadorMulta" id="checkIndicadorMulta" disabled="disabled" checked="checked" onclick="javascript:validaMulta();validaIndicadores();reloadImovel();" value="<%= ConstantesSistema.SIM.toString()%>"/>
					</logic:notPresent>
				</logic:equal>
				<logic:equal name="DebitoCreditoDadosSelecaoExtratoActionForm" property="permitirSelecaoAcrescimoExtrato"  value="">
					<input type="checkbox" name="checkIndicadorMulta" id="checkIndicadorMulta" disabled="disabled" checked="checked" onclick="javascript:validaMulta();validaIndicadores();reloadImovel();" value="<%= ConstantesSistema.SIM.toString()%>"/> 
				</logic:equal>
			</td>
			<td>
				<strong>Cobran�a de Multa</strong>
			</td>
			<td>
				<logic:equal name="DebitoCreditoDadosSelecaoExtratoActionForm" property="permitirSelecaoAcrescimoExtrato" value="<%=ConstantesSistema.NAO.toString()%>">
					<input type="checkbox" name="checkIndicadorJurosMora" id="checkIndicadorJurosMora" disabled="disabled" checked="checked" onclick="javascript:validaJurosMora();validaIndicadores();reloadImovel();" value="<%= ConstantesSistema.SIM.toString()%>"/>
				</logic:equal>
				<logic:equal name="DebitoCreditoDadosSelecaoExtratoActionForm" property="permitirSelecaoAcrescimoExtrato" value="<%=ConstantesSistema.SIM.toString()%>">
					<logic:present name="indicadorJurosMora" scope="request">
						<logic:equal name="indicadorJurosMora" scope="request" value="<%=ConstantesSistema.SIM.toString()%>">
							<input type="checkbox" name="checkIndicadorJurosMora" id="checkIndicadorJurosMora" checked="checked" onclick="javascript:validaJurosMora();validaIndicadores();reloadImovel();" value="<%= ConstantesSistema.SIM.toString()%>"/> 
						</logic:equal>
						<logic:equal name="indicadorJurosMora" scope="request" value="<%=ConstantesSistema.NAO.toString()%>">
							<input type="checkbox" name="checkIndicadorJurosMora" id="checkIndicadorJurosMora" onclick="javascript:validaJurosMora();validaIndicadores();reloadImovel();" value="<%= ConstantesSistema.NAO.toString()%>"/> 
						</logic:equal>
					</logic:present>
					<logic:notPresent name="indicadorMulta" scope="request">
						<input type="checkbox" name="checkIndicadorJurosMora" id="checkIndicadorJurosMora" disabled="disabled" checked="checked" onclick="javascript:validaJurosMora();validaIndicadores();reloadImovel();" value="<%= ConstantesSistema.SIM.toString()%>"/>
					</logic:notPresent>
				</logic:equal>
				<logic:equal name="DebitoCreditoDadosSelecaoExtratoActionForm" property="permitirSelecaoAcrescimoExtrato" value="">
					<input type="checkbox" name="checkIndicadorJurosMora" id="checkIndicadorJurosMora" disabled="disabled" checked="checked" onclick="javascript:validaJurosMora();validaIndicadores();reloadImovel();" value="<%= ConstantesSistema.SIM.toString()%>"/>
				</logic:equal>
			</td>
			<td>
				<strong>Cobran�a de Juros</strong>
			</td>
			<td>
				<logic:equal name="DebitoCreditoDadosSelecaoExtratoActionForm" property="permitirSelecaoAcrescimoExtrato" value="<%=ConstantesSistema.NAO.toString()%>">
					<input type="checkbox" name="checkIndicadorAtualizacaoMonetaria" id="checkIndicadorAtualizacaoMonetaria" disabled="disabled" checked="checked" onclick="javascript:validaAtualizacaoMonetaria();validaIndicadores();reloadImovel();" value="<%= ConstantesSistema.SIM.toString()%>"/>
				</logic:equal>
				<logic:equal name="DebitoCreditoDadosSelecaoExtratoActionForm" property="permitirSelecaoAcrescimoExtrato" value="<%=ConstantesSistema.SIM.toString()%>">
					<logic:present name="indicadorAtualizacaoMonetaria" scope="request">
						<logic:equal name="indicadorAtualizacaoMonetaria" scope="request" value="<%=ConstantesSistema.SIM.toString()%>">
							<input type="checkbox" name="checkIndicadorAtualizacaoMonetaria" id="checkIndicadorAtualizacaoMonetaria" checked="checked" onclick="javascript:validaAtualizacaoMonetaria();validaIndicadores();reloadImovel();" value="<%= ConstantesSistema.SIM.toString()%>"/>
						</logic:equal>
						<logic:equal name="indicadorAtualizacaoMonetaria" scope="request" value="<%=ConstantesSistema.NAO.toString()%>">
							<input type="checkbox" name="checkIndicadorAtualizacaoMonetaria" id="checkIndicadorAtualizacaoMonetaria" onclick="javascript:validaAtualizacaoMonetaria();validaIndicadores();reloadImovel();" value="<%= ConstantesSistema.NAO.toString()%>"/>
						</logic:equal>
					</logic:present>
					<logic:notPresent name="indicadorAtualizacaoMonetaria" scope="request">
						<input type="checkbox" name="checkIndicadorAtualizacaoMonetaria" id="checkIndicadorAtualizacaoMonetaria" disabled="disabled" checked="checked" onclick="javascript:validaAtualizacaoMonetaria();validaIndicadores();reloadImovel();" value="<%= ConstantesSistema.SIM.toString()%>"/>
					</logic:notPresent>
				</logic:equal>
				<logic:equal name="DebitoCreditoDadosSelecaoExtratoActionForm" property="permitirSelecaoAcrescimoExtrato" value="">
					<input type="checkbox" name="checkIndicadorAtualizacaoMonetaria" id="checkIndicadorAtualizacaoMonetaria" disabled="disabled" checked="checked" onclick="javascript:validaAtualizacaoMonetaria();validaIndicadores();reloadImovel();" value="<%= ConstantesSistema.SIM.toString()%>"/>
				</logic:equal>
				<!--<html:checkbox property="indicadorAtualizacaoMonetaria" disabled="disabled" onclick="javascript:validaIndicadores();reloadImovel();" value=""></html:checkbox>-->
			</td>
			<td>
				<strong>Cobran�a de Corre��o</strong>
			</td>
		</tr>
	</table>
	 
	<table width="100%" border="0">
	<tr>
		<td HEIGHT="10"></td>
	</tr>
	</table>
	 
	<table width="100%" border="0">
		<tr>
			<td width="30%"><strong>Acr�scimos Impontualidade:</strong></td>
			<td><strong> <span class="style1"><strong> 
			<logic:equal name="indicadorEmissaoExtratoDebitoSemAcrescimo" value="2">
				<html:radio	property="indicadorIncluirAcrescimosImpontualidade" value="1"/> <strong>Incluir
				<html:radio property="indicadorIncluirAcrescimosImpontualidade" value="2" disabled="true"/> N�o Incluir
			</logic:equal>
			<logic:notEqual name="indicadorEmissaoExtratoDebitoSemAcrescimo" value="2">
				<html:radio	property="indicadorIncluirAcrescimosImpontualidade" value="1"/> <strong>Incluir
				<html:radio property="indicadorIncluirAcrescimosImpontualidade" value="2"/> N�o Incluir
			</logic:notEqual>
			
			
			<logic:equal name="temPermissaoIncluirAcrescExtratoComDesconto" value="true">
				<html:radio property="indicadorIncluirAcrescimosImpontualidade" value="3" /> Incluir com Desconto
			</logic:equal>
			
			</strong></strong></span></strong></td>
		</tr>
	</table>
	
	<logic:equal name="temPermissaoRetirarTaxaCobrancaDoExtratoDeDebitos" value="true">
		<table width="100%" border="0">
			<tr>
				<td width="30%"><strong>Cobrar Taxa de Cobran�a:</strong></td>
				<td><strong> <span class="style1"><strong> 
				<html:radio	property="indicadorTaxaCobranca" value="1" /> <strong>Sim
				<html:radio property="indicadorTaxaCobranca" value="2" /> N�o
				</strong></strong></span></strong></td>
			</tr>
		</table>
	</logic:equal>
			
	<table width="100%" border="0">
	<tr>
		<td HEIGHT="10"></td>
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
			<input name="Button" type="button" class="bottonRightCol"
			value="Desfazer" align="left"
			onclick="javascript:limparImovel();">
			<input name="Button" type="button" class="bottonRightCol"
			value="Cancelar" align="left" onclick="javascript:window.location.href='/gsan/telaPrincipal.do'">
		</td>
			
		<td align="right">
			<input type="button" name="" value="Imprimir" class="bottonRightCol" 
			onclick="javascript:debitosCreditosSelecionados(document.forms[0]);"
			url="emissaoRelatorioExtratoDebitoAction.do?extratoDebito=1" <c:if test="${impressaoPermitida eq '0'}">disabled="disabled"</c:if>/>
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
</html:html>
