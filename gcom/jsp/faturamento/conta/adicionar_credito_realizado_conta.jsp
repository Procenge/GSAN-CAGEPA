<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@ page import="gcom.util.ConstantesSistema" %>
<%@ page import="gcom.arrecadacao.pagamento.bean.PagamentoHistoricoAdmiteDevolucaoHelper"%>


<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>

<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="AdicionarCreditoRealizadoContaActionForm"/>

<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/engine.js'> </script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/util.js'> </script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/interface/AjaxService.js'> </script>

<SCRIPT LANGUAGE="JavaScript">
<!--

function init(){
	//configurarCreditoOrigem(obterValorCreditoTipo()); 
}

function sugerirValorCredito(idCreditoOrigem){
	
	var idCreditoTipo = obterValorCreditoTipo();
	document.forms[0].vlCreditoOrigem.value = idCreditoOrigem;
	
	AjaxService.obterValorCreditoSugerido(idCreditoTipo, idCreditoOrigem, {callback: 
		function(valorSugerido) {

			var valorCredito = document.forms[0].valorCredito.value;
		
			if(valorSugerido != null && valorCredito == ''){
				
				document.forms[0].valorCredito.value = valorSugerido;			
								
			}
							
		}, async: false} 
	);
	
}

function obterValorCreditoTipo(){
	
	var indexCt = document.forms[0].creditoTipoID.selectedIndex;
	var valueCt = document.forms[0].creditoTipoID.options[indexCt].value;
	
	return valueCt;
	
}

function configurarCreditoOrigem(idCreditoTipo){
	
	var vlCreditoOrigem = document.forms[0].vlCreditoOrigem.value;
				
	var selectOrigemCredito = document.forms[0].creditoOrigemID;
	selectOrigemCredito.length = 0;
	
	var novaOpcao = new Option("", "-1");
	selectOrigemCredito.options[selectOrigemCredito.length] = novaOpcao;
	
	if(idCreditoTipo != "" && idCreditoTipo != '-1'){
					
		AjaxService.obterCreditoOrigem(idCreditoTipo, null, {callback: 
			function(dados) {

				for (key in dados){
					
					novaOpcao = new Option(dados[key], key);
					selectOrigemCredito.options[selectOrigemCredito.length] = novaOpcao;
										
					if(vlCreditoOrigem != '' && vlCreditoOrigem == key){
						
						novaOpcao.selected = true;
						
					}
					
		  		}
					
			}, async: false} 
		);
	
	}
	
	if(document.getElementById("trPagamentos") != null){
		document.getElementById("trPagamentos").style.display = "none";
	}
		
}


function configurarGridPagamentoHistoricoAdmiteDevolucao(idOrigemCredito){
	
	var form = document.forms[0];
	var creditoTipoID = form.creditoTipoID.value;
	
	var idImovel = form.imovelID.value;
	if(idOrigemCredito != ""){
		
		AjaxService.obterPagamentosHistoricoAdmiteDevolucao(idOrigemCredito, idImovel, creditoTipoID, false, {callback: 
			function(retorno) {
				if(retorno['msg'] != null){
					alert(retorno['msg']);
				}
				
				 form.action = 'exibirAdicionarCreditoRealizadoContaAction.do?imovel='+idImovel;
				 form.submit();
				 
			}, async: false} 
		);
	}
}

function configurarValorCredito(idPagamentoHistorico){
	
	var form = document.forms[0];
	var idImovel = form.matriculaImovel.value;
	if(idPagamentoHistorico != null && idPagamentoHistorico != ""){
		
		AjaxService.obgterInformacaoPagamentoHistorico(idPagamentoHistorico, idImovel, {callback: 
			function(retorno) {
				
				if(retorno!= null && retorno[0] != null){
					if(retorno[0] == "true" ){
						form.valorCredito.value = retorno[1];
						form.valorCredito.disabled = true;
						document.getElementById("valorCreditoHidden").value = retorno[1];
					} else {
						form.valorCredito.value = "";
						form.valorCredito.disabled = true;
						document.getElementById("valorCreditoHidden").value = "";
					}
				}
					
			}, async: false} 
		);
	}
}

function validarForm(form){
		
	var msg = "Mês e Ano de referência inválido.";
	
	if (validateAdicionarCreditoRealizadoContaActionForm(form)){
			
		<% if(session.getAttribute("pagamentosHistoricoAdmiteDevolucao") != null){ %>
			if(checkRadioPeloMenosUmRadioChecked('idPagamentoHistorico')){
				alert("Selecione um pagamento.");
				return false;
			}  
		<% } %>
	
	
		var ANO_LIMITE = document.getElementById("ANO_LIMITE").value;
		
		if (form.mesAnoCredito.value.length > 0 &&
				((form.mesAnoCredito.value.substring(3, 7) * 1) < (ANO_LIMITE * 1))){
			alert("Ano do Crédito não deve ser menor que " + ANO_LIMITE);
			form.mesAnoCredito.focus();
			return false;
		}
		
		if (form.mesAnoCobranca.value.length > 0 && 
				((form.mesAnoCobranca.value.substring(3, 7) * 1) < (ANO_LIMITE * 1))){
			alert("Ano da cobrança não deve ser menor que " + ANO_LIMITE);
			form.mesAnoCobranca.focus();
			return false;
		}
		
		if (testarCampoValorZero(form.valorCredito, "Valor do Crédito")){
			
			submeterFormPadrao(form);
		
		}
	}
}

//-->
</SCRIPT>

</head>

<logic:present name="reloadPage">
		<body leftmargin="5" topmargin="5" onload="window.focus(); resizePageSemLink(520, 350); chamarSubmitComUrl('exibirRetificarContaAction.do?reloadPage=1'); window.close();init();">
</logic:present>

<logic:notPresent name="reloadPage">
	<body leftmargin="5" topmargin="5" onload="window.focus(); resizePageSemLink(520, 350);init();">
</logic:notPresent>

<INPUT TYPE="hidden" ID="ANO_LIMITE" value="${requestScope.anoLimite}"/>

<html:form action="/adicionarCreditoRealizadoContaAction" method="post">

<html:hidden property="imovelID"/>
<input type="hidden" name="vlCreditoOrigem"/>

<table width="492" border="0" cellpadding="0" cellspacing="5">
  <tr>
    <td width="482" valign="top" class="centercoltext"> 
      <table height="100%">
        <tr>
          <td></td>
        </tr>
      </table>
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11"><img src="<bean:message key="caminho.imagens"/>parahead_left.gif" editor="Webstyle4" moduleid="Album Photos (Project)\toptab_page2_parahead_left.xws" border="0" /></td>
          <td class="parabg">Adicionar Crédito na Conta</td>
          <td width="11"><img src="<bean:message key="caminho.imagens"/>parahead_right.gif" editor="Webstyle4" moduleid="Album Photos (Project)\toptab_page2_parahead_right.xws" border="0" /></td>
        </tr>
      </table>
      <p>&nbsp;</p>

      <table width="100%" border="0">
        <tr>
          <td colspan="4">Preencha os campos abaixo para inserir um crédito na conta:</td>
          <td align="right"></td>
        </tr>
        </table>
        <table width="100%" border="0">
        <tr>
          <td width="100" height="20"><strong>Tipo de Crédito:<font color="#FF0000">*</font></strong></td>
          <td colspan="3">
          
          	<html:select property="creditoTipoID" style="width: 230px;" tabindex="1" onchange="configurarCreditoOrigem(this.value)">
          		<html:option value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>
				<logic:present name="colecaoAdicionarCreditoTipo">
					<html:options collection="colecaoAdicionarCreditoTipo" labelProperty="descricao" property="id"/>
				</logic:present>
			</html:select>
          
          </td>
        </tr>
        <tr>
          <td><strong>Mês e Ano do Crédito:</strong></td>
          <td colspan="3"><html:text property="mesAnoCredito" size="8" maxlength="7" tabindex="2" onkeyup="mascaraAnoMes(this, event);"/>
          &nbsp;mm/aaaa
          </td>
        </tr>
        <tr>
          <td><strong>Mês e Ano da Cobrança:</strong></td>
          <td colspan="3"><html:text property="mesAnoCobranca" size="8" maxlength="7" tabindex="3" onkeyup="mascaraAnoMes(this, event);"/>
          &nbsp;mm/aaaa
          </td>
        </tr>
        <tr>
          <td>
          	<strong>Valor do Crédito:<font color="#FF0000">*</font></strong></td>
          	<td colspan="3">
          		<html:text property="valorCredito" size="15" maxlength="14" style="text-align: right;" tabindex="4" onkeyup="formataValorMonetario(this, 11)"/>
          		<input type="hidden" id="valorCreditoHidden" name="valorCreditoHidden" >
          	</td>
        </tr>
        <tr>
          <td width="100" height="20"><strong>Origem do Crédito:<font color="#FF0000">*</font></strong></td>
          <td colspan="3">

																		<!-- -->
          	<strong> 
				<html:select property="creditoOrigemID" tabindex="5" onchange="javascript:sugerirValorCredito(this.value), configurarGridPagamentoHistoricoAdmiteDevolucao(this.value); " >
						<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="colecaoAdicionarCreditoOrigem" labelProperty="descricao" property="id" />
				</html:select>
			</strong>
          
          </td>
        </tr>
<logic:notEmpty name="pagamentosHistoricoAdmiteDevolucao" scope="session">

			<tr id="trPagamentos" style="display: block, width: 100%; "> 
					<td colspan="3">
					
					<table width="100%" align="center" bgcolor="#90c7fc" border="0">
						<%String cor = "#cbe5fe";%>
						<%cor = "#cbe5fe";%>
						<tr bordercolor="#79bbfd">
							<td colspan="7" align="center" bgcolor="#79bbfd">
							<strong>Pagamentos</strong>
							</td>
						</tr>
					
						<!-- INICIO CABEÇALHO CONTAS -->						
						<tr bordercolor="#000000">
							<td width="5%" bgcolor="#90c7fc">
								<div align="center" class="style9">
									<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
									<strong></strong>
								</font>
							</div>

							<td width="10%" bgcolor="#90c7fc">
								<div align="center" class="style9">
									<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
										<strong>Data Pagamento</strong>
									</font>
								</div>
							</td>

						
							<td width="20%" bgcolor="#90c7fc">
								<div align="center" class="style9">
									<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
										<strong>Valor</strong>
									</font>
								</div>
							</td>				

							<td width="10%" bgcolor="#90c7fc">
								<div align="center" class="style9">
									<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
										<strong>Situação</strong>
									</font>
								</div>
							</td>				

							<td width="20%" bgcolor="#90c7fc">
								<div align="center" class="style9">
									<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
										<strong>Identificação do documento</strong>
									</font>
								</div>
							</td>				

							<td width="20%" bgcolor="#90c7fc">
								<div align="center" class="style9">
									<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif">
										<strong>Valor do documento</strong>
									</font>
								</div>
							</td>				
						</tr>
					
<%int cont = 0;%>
<logic:iterate name="pagamentosHistoricoAdmiteDevolucao" id="pagamentoHistorico" type="PagamentoHistoricoAdmiteDevolucaoHelper">
<%cont = cont + 1;
if (cont % 2 == 0) {%>
						<tr bgcolor="#cbe5fe">
<%} else {%>
						<tr bgcolor="#FFFFFF">
<%}%>
			
							<td width="5%" align="center">
								<div align="center" >
										<input type="radio" id="idPagamentoHistorico"  name="idPagamentoHistorico" value="<%=pagamentoHistorico.getIdPagamentoHistorico() %>" 
											onclick="javascript:configurarValorCredito(this.value);" unchecked />
								</div>
							</td>		
									
							<td width="10%" align="center">
								<div align="center">
									<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
										<bean:write name="pagamentoHistorico" property="dataPagamento" formatKey="date.format" /> 
									</font>
								</div>		
							</td>		
							
							<td width="20%" align="center">
								<div align="right">
									<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
										<bean:write name="pagamentoHistorico" property="valorPagamento" formatKey="money.format" />
									</font>
								</div>
							</td>		

							<td width="10%" align="center">
								<div align="center">
									<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
										<bean:write name="pagamentoHistorico" property="situacaoAtualDescricao" />
									</font>
								</div>
							</td>		

							<td width="20%" align="center">
								<div align="center">
									<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
										<bean:write name="pagamentoHistorico" property="indentificacaoDocumento" />
									</font>
								</div>
							</td>		
																
							<td width="15%" align="center">
								<div align="right">
									<font color="#000000" style="font-size:9px" face="Verdana, Arial, Helvetica, sans-serif"> 
										<bean:write name="pagamentoHistorico" property="valorDocumento" formatKey="money.format" />
									</font>
								</div>
							</td>		

						</tr>				
</logic:iterate>							

				
					</table> <!-- FIM TABLE PAGAMENTOS -->
				  </td> <!-- FIM TD PAGAMENTOS -->
				</tr>	<!-- FIM TR PAGAMENTOS não confundir com a de dentro do IF-->
		
</logic:notEmpty>	        
        
        <tr>
          <td height="30" colspan="4"> 
          	<div align="right">
              <input type="button" onclick="validarForm(document.forms[0]);" class="bottonRightCol" value="Inserir" style="width: 70px;">&nbsp;
              <input type="button" onclick="window.close();" class="bottonRightCol" value="Fechar" style="width: 70px;">
			</div>
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



