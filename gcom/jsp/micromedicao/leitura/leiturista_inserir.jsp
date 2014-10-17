<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

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
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="InserirLeituristaActionForm" />

<SCRIPT LANGUAGE="JavaScript">

 
	function limparCliente(){
		var form = document.forms[0];
		
		form.idCliente.value = "";
		form.descricaoCliente.value = "";
	}
	
	function limpar(){

	var form = document.forms[0];
	
	form.idFuncionario.value = "";
	form.descricaoFuncionario.value = "";
			   
	//Coloca o foco no objeto selecionado
	form.idFuncionario.focus();
}

function limparDescricao(){
	
	var form = document.forms[0];
	form.descricaoFuncionario.value = "";
}
	
    function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
    	
    	var form= document.forms[0];
    	
  		if (tipoConsulta=='funcionario'){
  			form.idFuncionario.value = codigoRegistro;
  			form.descricaoFuncionario.value = descricaoRegistro;
  			form.descricaoFuncionario.style.color = "#000000";
      	}else{
      		form.idCliente.value = codigoRegistro;  
      		form.descricaoCliente.value = descricaoRegistro;
      		form.descricaoCliente.style.color = "#000000";
      	}	  
    }	

	function validarForm(){
		var form = document.InserirLeituristaActionForm;
		
		if (form.idCliente.value != '' && form.idFuncionario.value != ''){
		   alert('Informe somente Funcionário ou Cliente do Agente Comercial.');	
		   return false;	   
		}

		if (form.idCliente.value == '' && form.idFuncionario.value == ''){
		   alert('Informe Funcionário ou Cliente do Agente Comercial.');
		   return false;		   
		}

		if (form.idCliente.value != '' && form.empresaID[form.empresaID.selectedIndex].value == '-1'){
		   alert('Informe Empresa para o Cliente selecionado. ');
		   return false;		   
		}
		
		if(validateInserirLeituristaActionForm(form)){
	       	submeterFormPadrao(form);
	    }
	}
	
		function limparForm() {
		var form = document.InserirLeituristaActionForm;
		form.idFuncionario.value = "";
		form.descricaoFuncionario.value = "";
	    form.empresaID.value = "";
	    form.idCliente.value = "";
		form.descricaoCliente.value = "";
	    form.ddd.value = "";
	    form.telefone.value = "";
				
	}
	
	
function isIMEI (s) {
var etal = /^[0-9]{15}$/;
  if (!etal.test(s))
    return false;
  sum = 0; mul = 2; l = 14;
  for (i = 0; i < l; i++) {
    digit = s.substring(l-i-1,l-i);
    tp = parseInt(digit,10)*mul;
    if (tp >= 10)
         sum += (tp % 10) +1;
    else
         sum += tp;
    if (mul == 1)
         mul++;
    else
         mul--;
    }
  chk = ((10 - (sum % 10)) % 10);
  if (chk != parseInt(s.substring(14,15),10))
    return false;
  return true;
}

	
	  	function validaNumeroDocumento() {
		var form = document.forms[0];		
		if (!form.numeroDocumento1[""].checked
				&& !form.deferimento[1].checked) {
			alert("Informe Numero do Documento.");		
			return false;
		}		
		if (!form.idCodigo1[0].checked
				&& !form.idCodigo1[""].checked) {
			alert("Informe Motivo.");		
			return false;
		}		
		return true;
   	}
   	
   	function remover(objetoRemocao){
  redirecionarSubmit("/gsan/exibirInserirLeituristaAction.do?removerComponente=" + objetoRemocao);
}

   	function protegerCampos(){
   		var form = document.forms[0];
   		var funcionario = form.idFuncionario.value;
   		var campoCliente = form.idCliente;
   		var campoEmpresa = form.empresaID;

   		if(funcionario != null && trim(funcionario) != ""){
			campoCliente.value = "";
			campoEmpresa.value = "";
			campoCliente.disabled = true;
			campoEmpresa.disabled = true;
   	    }else{
   	    	campoCliente.disabled = false;
			campoEmpresa.disabled = false;
   	   	}
   	}

   	function habilitarPesquisaCliente() {
   		var form = document.forms[0];
   		if (form.idCliente.disabled == false) {
   			abrirPopup('exibirPesquisarClienteAction.do?menu=sim');
   		}	
   	}
	
</SCRIPT>


</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('${requestScope.nomeCampo}');protegerCampos();">


<html:form action="/inserirLeituristaAction"
	name="InserirLeituristaActionForm"
	type="gcom.gui.cadastro.InserirLeituristaActionForm" method="post">

	<INPUT TYPE="hidden" name="removerEndereco">
	<INPUT TYPE="hidden" name="limparCampos" id="limparCampos">


	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

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

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Inserir Agente Comercial</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para adicionar um Agente Comercial, informe os dados
					abaixo:</td>

					<%-- 					
				<tr>
					<td><strong>Código do Leiturista Responsável:<font color="#FF0000">*</font></strong></td>
					<td><html:text property="codigo" size="6" maxlength="6" tabindex="4" /></td>
				</tr>
--%>
				<tr>
					<td WIDTH="20%"><strong>Funcionário:</strong></td>
					<td width="80%" colspan="3"><html:text property="idFuncionario"
						size="8" maxlength="5" tabindex="1"
						onkeypress="limparDescricao();validaEnterComMensagem(event, 'exibirInserirLeituristaAction.do', 'idFuncionario', 'Funcionário');" 
						onkeyup="protegerCampos();" onblur="protegerCampos();"/>
					<a 
						href="javascript:document.forms[0].idFuncionario.focus();abrirPopup('exibirInserirLeituristaAction.do?pesquisarFuncionario=OK',400,400);">
					<img src="/gsan/imagens/pesquisa.gif" alt="Pesquisar Funcionario"
						border="0" height="21" width="23"></a> <logic:present
						name="corFuncionario">

						<logic:equal name="corFuncionario" value="exception">
							<html:text property="descricaoFuncionario" size="40"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000"/>
						</logic:equal>

						<logic:notEqual name="corFuncionario" value="exception">
							<html:text property="descricaoFuncionario" size="40"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>

					</logic:present> <logic:notPresent name="corFuncionario">

						<logic:empty name="InserirLeituristaActionForm"
							property="idFuncionario">
							<html:text property="descricaoFuncionario" size="40"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="InserirLeituristaActionForm"
							property="idFuncionario">
							<html:text property="descricaoFuncionario" size="40"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>


					</logic:notPresent> <a href="javascript:limpar();protegerCampos();"> <img
						src="<bean:message key='caminho.imagens'/>limparcampo.gif"
						alt="Apagar" border="0"></a></td>
				</tr>

				<tr>
					<td><strong>Cliente :</strong></td>
					<td colspan="2"><html:text property="idCliente" size="8"
						maxlength="8" tabindex="2"
						onkeypress="validaEnterComMensagem(event, 'exibirInserirLeituristaAction.do', 'idCliente','Cliente');" />

					<a
						href="javascript:habilitarPesquisaCliente();">
					<img src="/gsan/imagens/pesquisa.gif" alt="Pesquisar Cliente"
						border="0" height="21" width="23"></a> <logic:present
						name="funcionalidadeEncontrada">

						<logic:equal name="funcionalidadeEncontrada" value="exception">
							<html:text property="descricaoCliente" size="40" maxlength="50"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>

						<logic:notEqual name="funcionalidadeEncontrada" value="exception">
							<html:text property="descricaoCliente" size="40" maxlength="40"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> <logic:notPresent name="funcionalidadeEncontrada">
						<logic:empty name="InserirLeituristaActionForm"
							property="idCliente">
							<html:text property="descricaoCliente" size="40" maxlength="40"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>

						<logic:notEmpty name="InserirLeituristaActionForm"
							property="idCliente">
							<html:text property="descricaoCliente" size="40" maxlength="40"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
					</logic:notPresent> <a href="javascript:limparCliente()">
					<img border="0" title="Apagar"
						src="<bean:message key='caminho.imagens'/>limparcampo.gif" /> </a>
					</td>
				</tr>
				<tr>
					<td><strong>Empresa:</strong></td>
					<td align="left"><html:select property="empresaID" tabindex="3">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoEmpresa"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>
				<tr>
					<td><strong>Código do DDD do Município:<font color="#FF0000">*</font></strong></td>
					<td><html:text property="ddd" size="2" maxlength="2" tabindex="4" /></td>
				</tr>

				<tr>
					<td><strong>Número Telefone:<font color="#FF0000">*</font></strong></td>
					<td><html:text property="telefone" size="8" maxlength="8"
						tabindex="5" /></td>
				</tr>

				<tr>
					<td><strong>Número do IMEI:<font color="#FF0000"></font></strong></td>
					<td><html:text property="numeroImei" size="15" maxlength="15"
						tabindex="6" onkeyup="isIMEI()" /></td>
				</tr>

				<tr>
					<td></td>
					<td><font color="#FF0000">*</font>&nbsp;Campos obrigat&oacute;rios</td>
				</tr>

				<tr>
					<td width="29%"><input name="Button" type="button" class="bottonRightCol"
						value="Desfazer" align="left"
						onclick="window.location.href='<html:rewrite page="/exibirInserirLeituristaAction.do?desfazer=S"/>'">
					<input type="button" name="ButtonCancelar" class="bottonRightCol"
						value="Cancelar" align="left"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"></td>
					<td align="right" width="71%"><%--<INPUT type="button" class="bottonRightCol" value="Inserir" tabindex="13" style="width: 70px;" onclick="validarForm(document.forms[0]);">--%>
					<%-- 
		  	A taglib vai substituir o botão, as propriedades requeridas da tag são :
		  	1-name -> O nome do botão.
		  	2-value -> A descrição do botão. 
		  	3-onclick -> a função javascript que vai ser chamada no click do botão.
		  	4-url -> a url doaction da operação para verificar se o usário logado tem acesso a operação.
		  	
		  	As propriedades NÃO requeridas da tag são:
		  	1-tabindex -> mesma função do input
		  	2-align -> mesma função do input
		  --%> <gcom:controleAcessoBotao name="Botao" value="Inserir"
						onclick="validarForm(document.forms[0]);"
						url="inserirLeituristaAction.do" tabindex="13" /></td>
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

