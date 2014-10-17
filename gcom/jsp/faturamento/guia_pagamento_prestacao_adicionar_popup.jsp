<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">


<%-- Carrega javascripts da biblioteca --%>
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
<%-- Novos Javascripts --%>
<script language="JavaScript">
	/* Valida Form */
	function validaForm() {
		var form = document.InserirGuiaPagamentoActionForm;
		
		if(validarValorDebito() && validaTipoDebito()) {
			 
			submeterFormPadrao(form);
		}
   	    
	}
	
	function validarValorDebito() {
		
		var form = document.InserirGuiaPagamentoActionForm;
		var conteudo = form.valorDebito.value.replace(",","");
	   	var conteudo = conteudo.replace(".","");
	   
	   	if(!isCampoNumericoDecimal(conteudo)) {
			alert('Valor do Débito Inválido.');
			form.valorDebito.value = "";
			form.valorDebito.focus();
			return false;
		}
	   	
	   	return true;
   	    
	}
			
	/* Limpar Tipo Débito  */
	function limparTipoDebito() {
		var form = document.InserirGuiaPagamentoActionForm;
		form.descricaoTipoDebito.value = '';
		form.idTipoDebito.value = '';
		form.valorDebito.value = '';
		form.idTipoDebito.focus();
	}
	/* Valida Tipo Débito */
	function validaTipoDebito() {
		var form = document.forms[0];

		if (form.idTipoDebito.value == '' || 
				form.valorDebito.value == '') {
			alert("Informe o Tipo de Débito e seu Valor.");		
			return false;
		}
			
		return true;		
	}
		
	/* Recupera Dados Popup */	
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	    var form = document.forms[0];
	    if (tipoConsulta == 'tipoDebito') {
	      form.idTipoDebito.value = codigoRegistro;
	      form.descricaoTipoDebito.value = descricaoRegistro;
	    }
	}
	
	function carregarRetorno(){
		chamarReload('${sessionScope.retornarTela}');
		window.close();
	}
	
</script>
</head>

<logic:equal name="fechaPopup" value="false" scope="request">
<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('${requestScope.nomeCampo}');">
</logic:equal>
<logic:equal name="fechaPopup" value="true" scope="request">
	<body leftmargin="5" topmargin="5"
		onload="javascript:carregarRetorno();">
</logic:equal>
	
<html:form action="/inserirGuiaPagamentoPrestacaoAction.do"
	name="InserirGuiaPagamentoActionForm"
	type="gcom.gui.faturamento.InserirGuiaPagamentoActionForm"
	method="post" >
	
	<table width="570" border="0" cellpadding="0" cellspacing="5">
		<tr> 
	    	<td width="560" valign="top" class="centercoltext">
	    		<table height="100%">
	        		<tr> 
	          			<td></td>
	        		</tr>
	      		</table>
	      		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	        		<tr> 
	          			<td width="11">
	          				<img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" />
	          			</td>
	          			<td class="parabg">Adicionar Prestações de Guia de Pagamento</td>
	          			<td width="11">
	          				<img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" />
	          			</td>
	        		</tr>
	      		</table>
	      		<p>&nbsp;</p>
	      		<table width="100%" border="0">
	        		<tr> 
	          			<td colspan="5">Preencha os campos para inserir uma Prestação de Guia de Pagamento:</td>
	        		</tr>	        		
	        		<tr>
	          			<td height="24">
	          				<strong>Tipo de Débito:</strong>
	          				<font color="#FF0000">*</font>
	          			</td>
	          			<td colspan="4">
	          				<strong>
								<html:text property="idTipoDebito" size="10" maxlength="9" tabindex="1"
										   onkeypress="javascript:validaEnterComMensagem(event, 'exibirInserirGuiaPagamentoPrestacaoAction.do?validaDebitoTipo=true', 'idTipoDebito','Tipo de Débito');if(event.keyCode == 13)document.forms[0].valorDebito.focus();return isCampoNumerico(event);" />
								<a href="javascript:abrirPopup('exibirPesquisarTipoDebitoAction.do?caminhoRetornoTelaPesquisaTipoDebito=exibirInserirGuiaPagamentoPrestacaoAction');">
									<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23" height="21" style="cursor:hand;" name="imagem" border="0" alt="Pesquisar">
								</a>
								<logic:present name="debitoTipoNaoEncontrado" scope="request">
									<html:text property="descricaoTipoDebito" readonly="true"
											   style="background-color:#EFEFEF; border:0; color: #000000" size="40" maxlength="40" />
								</logic:present> 

								<logic:notPresent name="debitoTipoNaoEncontrado" scope="request">
									<html:text property="descricaoTipoDebito" readonly="true"
											   style="background-color:#EFEFEF; border:0; color: #ff0000" size="40" maxlength="40"/>
								</logic:notPresent>                 						
																				 
								<a href="javascript:limparTipoDebito();">
									<img border="0" title="Apagar" src="<bean:message key='caminho.imagens'/>limparcampo.gif" /></a>
		            		</strong>
		            	</td>
	        		</tr>
	        		<tr>
	          			<td height="24">
	          				<strong>Valor do Débito:</strong>
	          				<font color="#FF0000">*</font>
	          			</td>
	          			<td colspan="4">
	          				<strong>
	          					<html:text property="valorDebito" size="10" maxlength="13" tabindex="2" onkeyup="formataValorMonetario(this, 13);"/>								
	          				</strong>
	          			</td>
	        		</tr>
	        		<tr> 
	          			<td height="24">
	          				<strong> </strong>
	          			</td>
	          			<td colspan="4">
	          				<strong>
	          					<font color="#FF0000">*</font>
	          				</strong> 
	          				Campos obrigat&oacute;rios
	          			</td>
	        		</tr>
	        		<tr> 
	          			<td height="27" colspan="5"> 
	          				<div align="right"> 
	              				<input name="Button" type="button" class="bottonRightCol" value="Inserir" tabindex="3" onclick="javascript:validaForm();">
	              				<input name="Button2" type="button" class="bottonRightCol" value="Fechar" tabindex="4" onClick="javascript:window.close();">
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