<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>




<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="gcom.util.ConstantesSistema" isELIgnored="false"%>
<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="FiltrarLeituristaActionForm" dynamicJavascript="true" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<SCRIPT LANGUAGE="JavaScript">

 
	function limparCliente(){
		var form = document.forms[0];
		
		form.idCliente.value = "";
		form.descricaoCliente.value = "";
	}

	function limparFuncionario(){
		var form = document.forms[0];
		
		form.idFuncionario.value = "";
		form.descricaoFuncionario.value = "";
	}
	
    function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
    	
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

	function validaForm(){
		var form = document.FiltrarLeituristaActionForm;
		
		if(validateFiltrarLeituristaActionForm(form)){
	       	submeterFormPadrao(form);
	    }
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
   	
   	function verificarChecado(valor){
	form = document.forms[0];
	if(valor == "1"){
	 	form.indicadorAtualizar.checked = true;
	 }else{
	 	form.indicadorAtualizar.checked = false;
	}
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
	
</SCRIPT>


</head>

<body leftmargin="5" topmargin="5"
	onload="verificarChecado('${sessionScope.indicadorAtualizar}'); protegerCampos();">
<html:form action="/filtrarLeituristaAction"
	name="FiltrarLeituristaActionForm"
	type="gcom.gui.micromedicao.leitura.FiltrarLeituristaActionForm"
	method="post"
	onsubmit="return validateFiltrarLeituristaActionForm(this);">

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


			<!--Início Tabela Reference a Páginação da Tela de Processo-->
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">Filtrar Agente Comercial</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>
				</tr>

			</table>
			<!--Fim Tabela Reference a Páginação da Tela de Processo-->
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td width="100%" colspan=2>
					<table width="100%" border="0">
						<tr>
							<td>Para filtrar um Agente Comercial, informe os dados abaixo:</td>
							<td align="right"><input type="checkbox"
								name="indicadorAtualizar" value="1" /><strong>Atualizar</strong>

							</td>
							<td align="right"></td>
						</tr>
					</table>
					</td>
				</tr>

				<tr>
					<td><strong>Funcionário :</strong></td>
					<td colspan="2"><html:text property="idFuncionario" size="8"
						maxlength="8" tabindex="1"
						onkeypress="validaEnterComMensagem(event, 'exibirFiltrarLeituristaAction.do', 'idFuncionario','Funcionario');"
						onkeyup="protegerCampos();" onblur="protegerCampos();" />

					<a
						href="javascript:abrirPopup('exibirPesquisarFuncionarioAction.do?menu=sim');">
					<img src="/gsan/imagens/pesquisa.gif" alt="Pesquisar Funcionario"
						border="0" height="21" width="23"></a> <logic:present
						name="funcionalidadeEncontrada">

						<logic:equal name="funcionalidadeEncontrada" value="exception">
							<html:text property="descricaoFuncionario" size="30"
								maxlength="50" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>

						<logic:notEqual name="funcionalidadeEncontrada" value="exception">
							<html:text property="descricaoFuncionario" size="30"
								maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> <logic:notPresent name="funcionalidadeEncontrada">
						<logic:empty name="FiltrarLeituristaActionForm"
							property="idFuncionario">
							<html:text property="descricaoFuncionario" size="30"
								maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>

						<logic:notEmpty name="FiltrarLeituristaActionForm"
							property="idFuncionario">
							<html:text property="descricaoFuncionario" size="30"
								maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
					</logic:notPresent> <a
						href="javascript:limparFuncionario()"> <img border="0"
						title="Apagar"
						src="<bean:message key='caminho.imagens'/>limparcampo.gif" /> </a>
					</td>
				</tr>

				
				<tr>
					<td><strong>Cliente :</strong></td>
					<td colspan="2"><html:text property="idCliente" size="8"
						maxlength="8" tabindex="2"
						onkeypress="validaEnterComMensagem(event, 'exibirFiltrarLeituristaAction.do', 'idCliente','Cliente');" />

					<a
						href="javascript:abrirPopup('exibirPesquisarClienteAction.do?menu=sim');">
					<img src="/gsan/imagens/pesquisa.gif" alt="Pesquisar Cliente"
						border="0" height="21" width="23"></a> <logic:present
						name="funcionalidadeEncontrada">

						<logic:equal name="funcionalidadeEncontrada" value="exception">
							<html:text property="descricaoCliente" size="30" maxlength="50"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>

						<logic:notEqual name="funcionalidadeEncontrada" value="exception">
							<html:text property="descricaoCliente" size="30" maxlength="40"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> <logic:notPresent name="funcionalidadeEncontrada">
						<logic:empty name="FiltrarLeituristaActionForm"
							property="idCliente">
							<html:text property="descricaoCliente" size="30" maxlength="40"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>

						<logic:notEmpty name="FiltrarLeituristaActionForm"
							property="idCliente">
							<html:text property="descricaoCliente" size="30" maxlength="40"
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
					<td colspan="2" align="left"><html:select property="empresaID" tabindex="3">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoEmpresa"
							labelProperty="descricao" property="id" />						
					</html:select></td>
				</tr>
				
				<tr>
					<td><strong>Código do DDD do Município:</strong></td>
					<td><html:text property="ddd" size="2" maxlength="2" tabindex="4" /></td>
				</tr>

				<tr>
					<td><strong>Numero Telefone:</strong></td>
					<td><html:text property="telefone" size="10" maxlength="9"
						tabindex="5" /></td>
				</tr>
				<tr>
					<td><strong>Indicador de Uso:</strong></td>
					<td><strong> <html:radio property="indicadorUso" value="1" tabindex="6" /> <strong>Ativos&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<html:radio property="indicadorUso" value="2" tabindex="7"/>
					Inativos&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<html:radio
						property="indicadorUso" value="3" /> Todos</strong> </strong></td>
				</tr>
				<tr>
					<td><input type="button" name="Submit22" class="bottonRightCol"
						value="Limpar"
						onClick="javascript:window.location.href='/gsan/exibirFiltrarLeituristaAction.do?menu=sim'">
					<!-- <input type="button" name="Button" class="bottonRightCol" value="Cancelar"
								onClick="window.location.href='/gsan/telaPrincipal.do'" /> --></td>
					<td align="right"><input type="button" name="Submit2"
						class="bottonRightCol" value="Filtrar"
						onclick="validaForm(document.forms[0]);"></td>
				</tr>
			</table>
			<p>&nbsp;</p>
		</tr>
		<!-- Rodapé -->
		<%@ include file="/jsp/util/rodape.jsp"%>
	</table>
	<p>&nbsp;</p>

	<tr>

	</tr>

</html:form>
</body>
</html:html>

