<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="gcom.util.ConstantesSistema"%>
<%@page import="gcom.gui.GcomAction"%>

<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key='caminho.css'/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>endereco.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>/validacao/ManutencaoRegistro.js"></script>

<script language="JavaScript">

  function janela(url, target, largura, altura) {
    if (url == "") {
      return;
    }
    
    //url= url+"ehPopup=t&openWindow=t";
    msgWin=window.open(url, target,"location=no,screenX=100,screenY=100,toolbar=no,directories=no,menubar=no,status=yes,scrollbars=yes,resizeable=yes,width=" + largura + ",height=" + altura + ",top=0,left=0");

    

  }

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

  function fecharPopup(){
    var form = document.forms[0];
    form.action = "/gsan/exibirManterContaConjuntoImovelAction.do?fecharPopup=ok";
    window.opener.location.reload();
	form.submit();
    window.close();
  }
  
  function validarForm(form){
	  
	  var idsContas = "";
		for(indice = 0; indice < window.opener.document.forms[0].elements.length; indice++){
			if (window.opener.document.forms[0].elements[indice].type == "checkbox" && window.opener.document.forms[0].elements[indice].checked == true) {
				idsContas = idsContas + window.opener.document.forms[0].elements[indice].value + ",";
			}
		}
		idsContas = idsContas.substring(0, idsContas.length - 1);
		form.contaSelected.value = idsContas;
		
	var mensagem = "";  
	if (form.motivoCancelamentoContaID.value == -1){
		mensagem += "Informe Motivo do Cancelamento.\n";
	}
	if(form.cdValorARetirar[0].checked == false
			&& form.cdValorARetirar[1].checked == false
			&& form.cdValorARetirar[2].checked == false){
		
		mensagem += 'Infome o Valor a Retirar.\n';
	}
	if (form.idMotivoRetificacao.value == -1){
		mensagem += "Informe Motivo de Retificação.";
	}
	// Exibir a mensagem de acordo com o radio selecionado
	var complementoMsg = "Água";
	if(form.cdValorARetirar[1].checked){
		complementoMsg = "Esgoto";
	} else if(form.cdValorARetirar[2].checked){
		complementoMsg = "Água e Esgoto"
	}
	
	if(mensagem != ""){
		alert(mensagem);
	}else if (confirm("Confirma a retirada do Valor de " +complementoMsg+ " ?")){
		submeterFormPadrao(form);
	}
	
}
  
function verificarExibirRA(){

   var form = document.forms[0];
   form.action='exibirRetirarValorAguaEsgotoConjuntoContaAction.do';
   form.submit();

}

//-->
</script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="CancelarContaActionForm" />

</head>
<logic:present name="fecharPopup">
	<body leftmargin="5" topmargin="5" onload="fecharPopup();">
</logic:present>

<logic:notPresent name="fecharPopup">
	<body leftmargin="5" topmargin="5"
		onload="window.focus();resizePageSemLink(600, 470); setarFoco('${requestScope.nomeCampo}');">
</logic:notPresent>

<html:form action="/retirarValorAguaEsgotoConjuntoContaAction"
	name="CancelarContaActionForm"
	type="gcom.gui.faturamento.conta.CancelarContaActionForm"
	method="post">
	<table width="100%" border="0" cellspacing="5" cellpadding="0">

		<tr>

			<td width="100%" valign="top" class="centercoltext">

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
					<td class="parabg">Retirar Débitos Cobrados</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" />
					</td>
				</tr>
			</table>

			<p>&nbsp;</p>

			<table width="100%" border="0" align="center">	
        
        <tr>
               <td><strong>Retirar Valor de:<font color="#FF0000">*</font></strong></td>
               <td align="right">
      			    <div align="left">
                       <html:radio property="cdValorARetirar" value="<%=ConstantesSistema.CD_VALOR_A_RETIRAR_AGUA + ""%>"/><strong>Água
                       <html:radio property="cdValorARetirar" value="<%=ConstantesSistema.CD_VALOR_A_RETIRAR_ESGOTO + ""%>"/>Esgoto
                       <html:radio property="cdValorARetirar" value="<%=ConstantesSistema.CD_VALOR_A_RETIRAR_AMBOS + ""%>"/>Ambos</strong>
				</div>
			</td>
		</tr>
				<tr>
					<td width="150" height="20"><strong>Motivo de Retificação:<font
						color="#FF0000">*</font></strong></td>
					<td colspan="3"><html:select property="idMotivoRetificacao"
						style="width: 250px;" tabindex="1">
						<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present name="colecaoMotivoRetificacaoConta">
							<html:options collection="colecaoMotivoRetificacaoConta"
								labelProperty="descricaoMotivoRetificacaoConta" property="id" />
						</logic:present>
					</html:select></td>
				</tr>
		
		<tr>		
		</tr>

		<tr title="Após a retirada do valor de Água, Esgoto ou Ambos conforme selecionado acima, a conta poderá ficar sem valor de Água e Esgoto, nesse caso, a conta será cancelada pelo Motivo de Cancelamento selecionado.">
          <td width="150" height="20"><strong>Motivo do Cancelamento:<font
						color="#FF0000">*</font></strong></td>
          <td colspan="3">
	          <logic:present name="indicadorObrigatoriedadeRA" scope="session">
	          	<html:select property="motivoCancelamentoContaID" style="width: 250px;" tabindex="1" onchange="verificarExibirRA();">
		          	<logic:present name="colecaoContaMotivoCancelamentoSelecionada">
						<html:options collection="colecaoContaMotivoCancelamentoSelecionada" labelProperty="descricaoMotivoCancelamentoConta" property="id"/>
					</logic:present>
					<logic:present name="colecaoMotivoCancelamentoConta">
						<html:options collection="colecaoMotivoCancelamentoConta" labelProperty="descricaoMotivoCancelamentoConta" property="id"/>
					</logic:present>
				</html:select>
	          </logic:present>
	          <logic:notPresent name="indicadorObrigatoriedadeRA" scope="session">
	          	 <logic:present name="colecaoContaMotivoCancelamentoSelecionada" scope="session">
		          	<html:select property="motivoCancelamentoContaID" style="width: 250px;" tabindex="1" onchange="verificarExibirRA();">
			          <logic:present name="colecaoContaMotivoCancelamentoSelecionada">
						<html:options collection="colecaoContaMotivoCancelamentoSelecionada" labelProperty="descricaoMotivoCancelamentoConta" property="id"/>
					</logic:present>
						<logic:present name="colecaoMotivoCancelamentoConta">
							<html:options collection="colecaoMotivoCancelamentoConta" labelProperty="descricaoMotivoCancelamentoConta" property="id"/>
						</logic:present>
					</html:select>	          	 
	          	 </logic:present>
	          	 <logic:notPresent name="colecaoContaMotivoCancelamentoSelecionada" scope="session">
		          	<html:select property="motivoCancelamentoContaID" style="width: 250px;" tabindex="1" onchange="verificarExibirRA();">
			          	<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present name="colecaoMotivoCancelamentoConta">
							<html:options collection="colecaoMotivoCancelamentoConta" labelProperty="descricaoMotivoCancelamentoConta" property="id"/>
						</logic:present>
					</html:select>
	          	 </logic:notPresent>
	          </logic:notPresent>
          </td>
        </tr>
							
				<tr>
					<td colspan="2">
					<p>&nbsp;</p>
					</td>
				</tr>
				</table>
	
				<html:hidden property="contaSelected" value="" />
				
				<table width="100%" border="0" align="center">
				<tr>
					<td colspan="2">
					<p>&nbsp;</p>
					</td>
				</tr>
				<tr>
					<td colspan="2">
					<table width="100%">
						<tr>
							<td>
							<div align="left"><input name="ButtonFechar" type="button"
								class="bottonRightCol" value="Fechar"
								onclick="javascript:window.close();"></div>
							</td>

							<td align="right"><input type="button" name="Button"
								class="bottonRightCol" value="Confirmar"
								onclick="validarForm(document.forms[0]);" /></td>
						</tr>

					</table>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
</body>
</html:html>
