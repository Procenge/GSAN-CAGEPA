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
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="PesquisarArrecadadorActionForm" />


<script language="Javascript">
window.onmousemove = resizePageSemLink(810, 320);

function limparPesquisaArrecadador() {

	 var form = document.PesquisarBoletoBancarioActionForm;
	 
	form.bancoBoletoBancarioPesquisa.value = "";
    form.descricaoBancoBoletoBancarioPesquisa.value = "";
}

function limparPesquisaImovel() {
	 var form = document.PesquisarBoletoBancarioActionForm;
	
    form.imovelBoletoBancarioPesquisa.value = "";
    form.inscricaoImovelBoletoBancarioPesquisa.value = "";
}

function limparPesquisaCliente() {
	 var form = document.PesquisarBoletoBancarioActionForm;
	 
    form.clienteBoletoBancarioPesquisa.value = "";
    form.nomeClienteBoletoBancarioPesquisa.value = "";
    
}

function chamarPesquisar(){
	var form = document.forms[0];
	
	if (verificarDatas() && validarIntervalos()){
   		submeterFormPadrao(form);
	}
}

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
    var form = document.forms[0];

    if (tipoConsulta == 'arrecadador') {
      form.bancoBoletoBancarioPesquisa.value = codigoRegistro;
      form.descricaoBancoBoletoBancarioPesquisa.value = descricaoRegistro;
      form.descricaoBancoBoletoBancarioPesquisa.style.color = "#000000";
    }
    if (tipoConsulta == 'imovel') {
        form.imovelBoletoBancarioPesquisa.value = codigoRegistro;
        form.inscricaoImovelBoletoBancarioPesquisa.value = descricaoRegistro;
        form.inscricaoImovelBoletoBancarioPesquisa.style.color = "#000000";
    }
    if (tipoConsulta == 'cliente') {
        form.clienteBoletoBancarioPesquisa.value = codigoRegistro;
        form.nomeClienteBoletoBancarioPesquisa.value = descricaoRegistro;
        form.nomeClienteBoletoBancarioPesquisa.style.color = "#000000";
    }
    
}

function replicaData(dataInicial, dataFinal) {
	if(dataFinal.value == ""){
		dataFinal.value = dataInicial.value;
    }
}

function limparDataInicialFinal(dataInicial, dataFinal){
	if(dataInicial.value == ""){
		dataFinal.value = "";
    }
}

function limparForm(){

	limparPesquisaArrecadador();
	limparPesquisaImovel();
	limparPesquisaCliente();
	
	var form = document.forms[0];

	form.dataInicialEntradaBoletoBancarioPesquisa.value = "";
	form.dataFinalEntradaBoletoBancarioPesquisa.value = "";
	form.dataInicialVencimentoBoletoBancarioPesquisa.value = "";
	form.dataFinalVencimentoBoletoBancarioPesquisa.value = "";
}

function verificarDatas(){

	var form = document.forms[0];
	
	if (!verificaDataMensagemPersonalizada(form.dataInicialEntradaBoletoBancarioPesquisa, 'Data Inicial de Entrada é inválida')) { 
		return false; 
	}

	if (!verificaDataMensagemPersonalizada(form.dataFinalEntradaBoletoBancarioPesquisa, 'Data Final de Entrada é inválida')) { 
		return false; 
	}

	if (!verificaDataMensagemPersonalizada(form.dataInicialVencimentoBoletoBancarioPesquisa, 'Data Inicial de Vencimento é inválida')) { 
		return false; 
	}

	if (!verificaDataMensagemPersonalizada(form.dataFinalVencimentoBoletoBancarioPesquisa, 'Data Final de Vencimento é inválida')) { 
		return false; 
	}

	return true;
}

function validarIntervalos(){
	
	var form = document.forms[0];
	
    if (comparaData(form.dataInicialEntradaBoletoBancarioPesquisa.value, '>', form.dataFinalEntradaBoletoBancarioPesquisa.value)){
		  alert("A data final de entrada deve ser maior que a data inicial");
		  return false;
    }

    if (comparaData(form.dataInicialVencimentoBoletoBancarioPesquisa.value, '>', form.dataFinalVencimentoBoletoBancarioPesquisa.value)){
		  alert("A data final de vencimento deve ser maior que a data inicial");
		  return false;
  	}

    return true;
}


</script>

</head>

<body leftmargin="5" topmargin="5" 
		onload="javascript:resizePageSemLink(810, 355);setarFoco('${requestScope.nomeCampo}');">

<html:form action="/pesquisarBoletoBancarioAction" method="post"
	name="PesquisarBoletoBancarioActionForm"
	type="gcom.gui.arrecadacao.PesquisarBoletoBancarioActionForm"
	onsubmit="return validatePesquisarBoletoBancarioActionForm(this);">
	<table width="770" border="0" cellspacing="4" cellpadding="0">
		<tr>
			<td width="770" valign="top" class="centercoltext">
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
					<td class="parabg">Pesquisar Boleto Bancário</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">Preencha os campos para pesquisar um boleto bancário:</td>
				</tr>

				<tr>
					<td width="20%" height="30">
						<strong>Arrecadador:</strong>
					</td>
				<td width="432">
						    <html:text property="bancoBoletoBancarioPesquisa" size="4" maxlength="3" tabindex="1" onkeypress="validaEnterComMensagem(event, 'exibirPesquisarBoletoBancarioAction.do', 'bancoBoletoBancarioPesquisa', 'Arrecadador');" />
							<a href="javascript:abrirPopup('exibirPesquisarArrecadadorAction.do?caminhoRetornoTelaPesquisaArrecadador=exibirPesquisarBoletoBancarioAction');">
								<img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" title="Pesquisar Arrecadador" />
							</a> 
							
							<logic:present name="corBanco">
								<logic:equal name="corBanco" value="exception">
									<html:text property="descricaoBancoBoletoBancarioPesquisa" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:equal>
	
								<logic:notEqual name="corBanco" value="exception">
									<html:text property="descricaoBancoBoletoBancarioPesquisa" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEqual>
							</logic:present> 
							
							<logic:notPresent name="corBanco">
								<logic:empty name="PesquisarBoletoBancarioActionForm" property="bancoBoletoBancarioPesquisa">
									<html:text property="descricaoBancoBoletoBancarioPesquisa" value="" size="45"	readonly="true"	style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:empty>
								
								<logic:notEmpty name="PesquisarBoletoBancarioActionForm" property="bancoBoletoBancarioPesquisa">
									<html:text property="descricaoBancoBoletoBancarioPesquisa" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEmpty>
							</logic:notPresent> 
							
							<a href="javascript:limparPesquisaArrecadador();"> 
							  <img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
							</a>
						</td>
					</tr>
					
					<tr>
	
					<td height="10" width="160"><strong>Matrícula do Imóvel:</strong></td>
					<td width="403">
				    <html:text property="imovelBoletoBancarioPesquisa" size="9" maxlength="9" tabindex="2" onkeypress="validaEnterComMensagem(event, 'exibirPesquisarBoletoBancarioAction.do', 'imovelBoletoBancarioPesquisa', 'Imovel');" />
					<a href="javascript:abrirPopup('exibirPesquisarImovelAction.do?caminhoRetornoTelaPesquisaImovel=exibirPesquisarBoletoBancarioAction');">
						<img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" title="Pesquisar Imóvel" />
					</a> 
					
					<logic:present name="corImovel">
						<logic:equal name="corImovel" value="exception">
							<html:text property="inscricaoImovelBoletoBancarioPesquisa" size="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>

						<logic:notEqual name="corImovel" value="exception">
							<html:text property="inscricaoImovelBoletoBancarioPesquisa" size="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> 
					
					<logic:notPresent name="corImovel">
						<logic:empty name="PesquisarBoletoBancarioActionForm" property="imovelBoletoBancarioPesquisa">
							<html:text property="inscricaoImovelBoletoBancarioPesquisa" value="" size="30"	readonly="true"	style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						
						<logic:notEmpty name="PesquisarBoletoBancarioActionForm" property="imovelBoletoBancarioPesquisa">
							<html:text property="inscricaoImovelBoletoBancarioPesquisa" size="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
					</logic:notPresent> 
					
					<a href="javascript:limparPesquisaImovel();"> 
					  <img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
					</a>
				</td>
				</tr> 
				
				<tr>
					<td><strong>Cliente:</strong></td>
				<td width="403">
			    <html:text property="clienteBoletoBancarioPesquisa" size="9" maxlength="9" tabindex="3" onkeypress="validaEnterComMensagem(event, 'exibirPesquisarBoletoBancarioAction.do', 'clienteBoletoBancarioPesquisa', 'Cliente');" />
					<a href="javascript:abrirPopup('exibirPesquisarClienteAction.do?caminhoRetornoTelaPesquisaCliente=exibirPesquisarBoletoBancarioAction');">
						<img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" title="Pesquisar Cliente" />
					</a> 
					
					<logic:present name="corCliente">
						<logic:equal name="corCliente" value="exception">
							<html:text property="nomeClienteBoletoBancarioPesquisa" size="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>

						<logic:notEqual name="corCliente" value="exception">
							<html:text property="nomeClienteBoletoBancarioPesquisa" size="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> 
					
					<logic:notPresent name="corCliente">
						<logic:empty name="PesquisarBoletoBancarioActionForm" property="clienteBoletoBancarioPesquisa">
							<html:text property="nomeClienteBoletoBancarioPesquisa" value="" size="30"	readonly="true"	style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						
						<logic:notEmpty name="PesquisarBoletoBancarioActionForm" property="clienteBoletoBancarioPesquisa">
							<html:text property="nomeClienteBoletoBancarioPesquisa" size="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
					</logic:notPresent> 
					
					<a href="javascript:limparPesquisaCliente();"> 
					  <img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
					</a>		
				</td>
				</tr>
				
				<tr>
			    <td><strong>Período de Entrada:</strong></td>
			    <td>
			      <strong> 
			        <html:text tabindex="4" property="dataInicialEntradaBoletoBancarioPesquisa" size="10" maxlength="10" onkeyup="mascaraData(this,event)" onchange="replicaData(this, document.forms[0].dataFinalEntradaBoletoBancarioPesquisa);limparDataInicialFinal(this, document.forms[0].dataFinalEntradaBoletoBancarioPesquisa);"/> 
			        <a href="javascript:abrirCalendario('PesquisarBoletoBancarioActionForm', 'dataInicialEntradaBoletoBancarioPesquisa')">
				      <img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a> a
			      </strong> 
			      
			      <html:text tabindex="5" property="dataFinalEntradaBoletoBancarioPesquisa" size="10" maxlength="10" onkeyup="mascaraData(this,event)"/> 
			      <a href="javascript:abrirCalendario('PesquisarBoletoBancarioActionForm', 'dataFinalEntradaBoletoBancarioPesquisa')">
					<img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a> (dd/mm/aaaa)
			    </td>
			  </tr>	
			  
			  <tr>
			    <td><strong>Período Vencimento:</strong></td>
			    <td>
			      <strong> 
			        <html:text tabindex="6" property="dataInicialVencimentoBoletoBancarioPesquisa" size="10" maxlength="10" onkeyup="mascaraData(this,event)" onchange="replicaData(this, document.forms[0].dataFinalVencimentoBoletoBancarioPesquisa);limparDataInicialFinal(this, document.forms[0].dataFinalVencimentoBoletoBancarioPesquisa);"/> 
			        <a href="javascript:abrirCalendario('PesquisarBoletoBancarioActionForm', 'dataInicialVencimentoBoletoBancarioPesquisa')">
				      <img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a> a
			      </strong> 
			      
			      <html:text tabindex="7" property="dataFinalVencimentoBoletoBancarioPesquisa" size="10" maxlength="10" onkeyup="mascaraData(this,event)"/> 
			      <a href="javascript:abrirCalendario('PesquisarBoletoBancarioActionForm', 'dataFinalVencimentoBoletoBancarioPesquisa')">
					<img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a> (dd/mm/aaaa)
			    </td>
			  </tr>	
			  
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>
			          	<INPUT TYPE="button" class="bottonRightCol" value="Limpar" onclick="document.forms[0].reset();limparForm();"/>
							&nbsp;&nbsp;
			          	<logic:present name="caminhoRetornoTelaPesquisaArrecadador">
			          		<INPUT TYPE="button" class="bottonRightCol" value="Voltar" onclick="redirecionarSubmit('${caminhoRetornoTelaPesquisaArrecadador}.do')"/>
			          	</logic:present>
			         </td>
			         <td align="right">
				        <input type="button" name="Button" class="bottonRightCol"
						 value="Pesquisar"
						onClick="javascript:chamarPesquisar();" />
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
