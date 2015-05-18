<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>
<%@ page import="gcom.util.ConstantesSistema"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false"  formName="TransferenciaDebitoCreditoDadosImovelActionForm"/>

<SCRIPT LANGUAGE="JavaScript">
<!--
	
	function validarForm(form){
		if (validateTransferenciaDebitoCreditoDadosImovelActionForm(form)){
			if (validarDestino()) {
				submeterFormPadrao(form);
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
	    
	    if (tipoConsulta == 'registroAtendimento') {

		    form.idRegistroAtendimento.value = codigoRegistro;
		    form.descricaoEspecificacaoRA.value = descricaoRegistro;
		    form.descricaoEspecificacaoRA.style.color = "#000000";
	    	reloadRA();
	    }
	    
	    if (tipoConsulta == 'imovel') {

		    form.idImovelDestino.value = codigoRegistro;
		    form.inscricaoImovelDestino.value = descricaoRegistro;
		    form.inscricaoImovelDestino.style.color = "#000000";
	    	reloadImovelDestino();
	    }
	    
	    if (tipoConsulta == 'cliente') {
			form.idClienteDestino.value = codigoRegistro;
			form.nomeClienteDestino.value = descricaoRegistro;
			reloadClienteDestino();
		}	    
	}
	
	
	function reloadRA() {
		var form = document.forms[0];
		form.action = "/gsan/exibirTransferenciaDebitoCreditoDadosImovelAction.do?pesquisarRA=SIM";
		submeterFormPadrao(form);
	}
	
	function reloadImovelDestino() {
		var form = document.forms[0];
		form.action = "/gsan/exibirTransferenciaDebitoCreditoDadosImovelAction.do?pesquisarImovelDestino=SIM";
		submeterFormPadrao(form);
	}
	
	function reloadClienteDestino() {
		var form = document.forms[0];
		form.action = "/gsan/exibirTransferenciaDebitoCreditoDadosImovelAction.do?pesquisarClienteDestino=SIM";
		submeterFormPadrao(form);
	}	
  	
  	function limparRADigitacao() {

    	var form = document.forms[0];

    	form.descricaoEspecificacaoRA.value = "";
		form.descricaoEspecificacaoRA.style.color = "#000000";
    	
    	form.idImovelOrigem.value = "";
		form.inscricaoImovelOrigem.value = "";
		form.nomeClienteUsuarioImovelOrigem.value = "";
		form.descricaoLigacaoAguaSituacaoImovelOrigem.value = "";
		form.descricaoLigacaoEsgotoSituacaoImovelOrigem.value = "";
  	}
  	
  	function limparImovelDestino() {

    	var form = document.forms[0];

    	form.idImovelDestino.value = "";
		form.inscricaoImovelDestino.value = "";
		form.inscricaoImovelDestino.style.color = "#000000";
		
		form.nomeClienteUsuarioImovelDestino.value = "";
		form.descricaoLigacaoAguaSituacaoImovelDestino.value = "";
		form.descricaoLigacaoEsgotoSituacaoImovelDestino.value = "";
		
		form.idImovelDestino.focus();
  	}
  	
  	function limparImovelDestinoDigitacao() {

    	var form = document.forms[0];

    	form.inscricaoImovelDestino.value = "";
		form.inscricaoImovelDestino.style.color = "#000000";
		
		form.nomeClienteUsuarioImovelDestino.value = "";
		form.descricaoLigacaoAguaSituacaoImovelDestino.value = "";
		form.descricaoLigacaoEsgotoSituacaoImovelDestino.value = "";
  	}
  	
  	function limpar(){
  		limparImovelDestino();
  		limparRA();
  	}
  	
//-->
</SCRIPT>

<logic:present name="indicadorFaturamentoTitularDebito" scope="request">
	<SCRIPT LANGUAGE="JavaScript">
	<!--
	
		function modificarTipoTransferencia() {
			var form = document.forms[0];
		
			if (form.tipoTransferencia[0].checked) {
				// Entres Imoveis

				form.idImovelDestino.disabled = false;
				
		  		form.idClienteDestino.value = "";
		  		form.nomeClienteDestino.value = "";
		  		form.cpfCnpjClienteDestino.value = "";
		  		form.idClienteDestino.disabled = true;	

		  		
			} else if (form.tipoTransferencia[1].checked) {
				// Entres Clientes
				
				form.nomeClienteUsuarioImovelDestino.value = "";
				form.descricaoLigacaoAguaSituacaoImovelDestino.value = "";
				form.descricaoLigacaoEsgotoSituacaoImovelDestino.value = "";
				form.inscricaoImovelDestino.value = "";
											
				form.idImovelDestino.value = "";
				form.idImovelDestino.disabled = true;
				
		  		form.idClienteDestino.disabled = false;	
		  		
			} else {
				form.nomeClienteUsuarioImovelDestino.value = "";
				form.descricaoLigacaoAguaSituacaoImovelDestino.value = "";
				form.descricaoLigacaoEsgotoSituacaoImovelDestino.value = "";
				form.inscricaoImovelDestino.value = "";				
				
		  		form.idClienteDestino.value = "";
		  		form.nomeClienteDestino.value = "";
		  		form.cpfCnpjClienteDestino.value = "";
		  		form.idClienteDestino.disabled = true;	
		  		
				form.idImovelDestino.value = "";
				form.idImovelDestino.disabled = true;
			}
		}
	
	  	function habilitarPesquisaClienteDestino(form) {
	  		if (form.tipoTransferencia[1].checked) {	  		
	  			abrirPopup('exibirPesquisarClienteAction.do?indicadorUsoTodos=1', 520, 800);
	  		}
	  	}
	  	
	  	function habilitarPesquisaImovelDestino() {
	  		if (document.forms[0].tipoTransferencia[0].checked) {	  		
	  			chamarPopup('exibirPesquisarImovelAction.do', 'imovel', null, null, 490, 800, '', document.forms[0].idImovelDestino)
	  		}
	  	}	  	
	
	  	function limparCliente() {
	  		var form = document.forms[0];
	  		
	  		form.idClienteDestino.value = "";
	  		form.nomeClienteDestino.value = "";
	  		form.cpfCnpjClienteDestino.value = "";
	  	}  	
	  	
		function validarDestino() {
			var form = document.forms[0];
			var retorno=true;
			var msg="";
			
			if (form.tipoTransferencia[0].checked) {
				if (form.idImovelDestino.value == "") {
					retorno=false;
					msg = msg + "Informar o Imóvel de Destino.\n";
				}
			} else if (form.tipoTransferencia[1].checked) {
				if (form.idClienteDestino.value == "") {
					retorno=false;
					msg = msg + "Informar o Cliente de Destino.\n";
				}				
			} else {
				retorno=false;
				msg = msg + "Informar o Tipo de Transferência.\n";
			}
			
			if (msg != "") {
				alert(msg);
			}
			
			return retorno;
		}	
		
	  	function limparRA() {

	    	var form = document.forms[0];

	    	form.tipoTransferencia[0].checked = false;
	    	form.tipoTransferencia[1].checked = false;
	    	
	    	form.idRegistroAtendimento.value = "";
			form.descricaoEspecificacaoRA.value = "";
			form.descricaoEspecificacaoRA.style.color = "#000000";
	    	
	    	form.idImovelOrigem.value = "";
			form.inscricaoImovelOrigem.value = "";
			form.nomeClienteUsuarioImovelOrigem.value = "";
			form.descricaoLigacaoAguaSituacaoImovelOrigem.value = "";
			form.descricaoLigacaoEsgotoSituacaoImovelOrigem.value = "";
			
	  		form.idClienteDestino.value = "";
	  		form.nomeClienteDestino.value = "";
	  		form.cpfCnpjClienteDestino.value = "";
	  		form.idClienteDestino.disabled = true;	
	  		
			form.idImovelDestino.value = "";
			form.idImovelDestino.disabled = true;
			
			form.idRegistroAtendimento.focus();
			
	  	}	
	  	
	//-->
	</SCRIPT>
</logic:present>

<logic:notPresent name="indicadorFaturamentoTitularDebito" scope="request">
	<SCRIPT LANGUAGE="JavaScript">
	<!--
		function modificarTipoTransferencia() {
		}
	
		function validarDestino() {
			var form = document.forms[0];
			var retorno=true;
			
			if (form.idImovelDestino.value == "") {
				retorno=false;
				alert("Informar o Imóvel de Destino.");
			}
			
			return retorno;
		}	

	  	function habilitarPesquisaImovelDestino() {
	  		chamarPopup('exibirPesquisarImovelAction.do', 'imovel', null, null, 490, 800, '', document.forms[0].idImovelDestino)
	  	}	
	  	
	  	function limparRA() {

	    	var form = document.forms[0];

	    	form.idRegistroAtendimento.value = "";
			form.descricaoEspecificacaoRA.value = "";
			form.descricaoEspecificacaoRA.style.color = "#000000";
	    	
	    	form.idImovelOrigem.value = "";
			form.inscricaoImovelOrigem.value = "";
			form.nomeClienteUsuarioImovelOrigem.value = "";
			form.descricaoLigacaoAguaSituacaoImovelOrigem.value = "";
			form.descricaoLigacaoEsgotoSituacaoImovelOrigem.value = "";
			
			form.idRegistroAtendimento.focus();
	  	}	  	
		
	//-->
	</SCRIPT>
</logic:notPresent>

</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}');modificarTipoTransferencia();">

<html:form action="/exibirTransferenciaDebitoCreditoDadosSelecaoAction" method="post">


<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

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
      	<td>Para transferir débito(s) informe RA - Registro de Atendimento:</td>
      </tr>
      </table>
      
    <table width="100%" border="0">
	<tr>
		<td WIDTH="120"><strong>Número do RA:<font color="#FF0000">*</font></strong></td>
		<td>
			<html:text property="idRegistroAtendimento" size="9" maxlength="9" tabindex="1"
			onkeypress="validaEnterComMensagem(event, 'exibirTransferenciaDebitoCreditoDadosImovelAction.do?pesquisarRA=SIM', 'idRegistroAtendimento', 'RA - Registro de Atendimento');"
			onkeyup="limparRADigitacao();" /> <a
			href="javascript:chamarPopup('exibirPesquisarRegistroAtendimentoAction.do', 'registroAtendimento', null, null, 600, 730, '', document.forms[0].idRegistroAtendimento);">
			<img src="<bean:message key='caminho.imagens'/>pesquisa.gif"
			width="23" height="21" alt="Pesquisar" border="0"></a> 
			
			<logic:present name="corRA">

				<logic:equal name="corRA" value="exception">
					<html:text property="descricaoEspecificacaoRA" size="45" readonly="true"
					style="background-color:#EFEFEF; border:0; color: #ff0000" />
				</logic:equal>

				<logic:notEqual name="corRA" value="exception">
					<html:text property="descricaoEspecificacaoRA" size="45" readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000" />
				</logic:notEqual>

			</logic:present> 
			
			<logic:notPresent name="corRA">

				<logic:empty name="TransferenciaDebitoCreditoDadosImovelActionForm" property="idRegistroAtendimento">
					<html:text property="descricaoEspecificacaoRA" value="" size="45" readonly="true"
					style="background-color:#EFEFEF; border:0; color: #ff0000" />
				</logic:empty>
				
				<logic:notEmpty name="TransferenciaDebitoCreditoDadosImovelActionForm" property="idRegistroAtendimento">
					<html:text property="descricaoEspecificacaoRA" size="45" readonly="true"
					style="background-color:#EFEFEF; border:0; color: #000000" />
				</logic:notEmpty>


			</logic:notPresent> 
			
			<a href="javascript:limparRA();"> <img src="<bean:message key='caminho.imagens'/>limparcampo.gif"
			alt="Apagar" border="0"></a>
		</td>
	</tr>
	</table>
      
    <table width="100%" align="center" bgcolor="#90c7fc" border="0">
	<tr>
		<td><strong>Imóvel de Origem</strong></td>
	</tr>
	
	<tr bgcolor="#cbe5fe">
    	<td width="100%" align="center">
    
	    <table width="100%" border="0">
	    <tr>
			<td WIDTH="120"><strong>Matrícula:</strong></td>
			<td>
	   			<html:text property="idImovelOrigem" size="10" maxlength="10" readonly="true"
				style="background-color:#EFEFEF; border:0; color: #000000"/>
			</td>
		</tr>
		
		<tr>
			<td><strong>Inscrição:</strong></td>
			<td>
	   			<html:text property="inscricaoImovelOrigem" size="25" maxlength="25" readonly="true"
				style="background-color:#EFEFEF; border:0; color: #000000"/>
			</td>
		</tr>
		
		<tr>
			<td><strong>Cliente:</strong></td>
			<td>
	   			<html:text property="nomeClienteUsuarioImovelOrigem" size="45" maxlength="45" readonly="true"
				style="background-color:#EFEFEF; border:0; color: #000000"/>
			</td>
		</tr>
		
		<tr>
			<td><strong>Sit. da Lig. de Água:</strong></td>
			<td>
	   			<html:text property="descricaoLigacaoAguaSituacaoImovelOrigem" size="20" maxlength="20" readonly="true"
				style="background-color:#EFEFEF; border:0; color: #000000"/>
				
				<strong>&nbsp;&nbsp;Sit. da Lig. de Esgoto:&nbsp;&nbsp;</strong>
				
				<html:text property="descricaoLigacaoEsgotoSituacaoImovelOrigem" size="20" maxlength="20" readonly="true"
				style="background-color:#EFEFEF; border:0; color: #000000"/>
			</td>
		</tr>
		</table>
	
		</td>
    </tr>
    </table>
	
	
	<br>
	
	<logic:present name="indicadorFaturamentoTitularDebito" scope="request">
		<table width="100%" align="center" bgcolor="#90c7fc" border="0">
		<tr>
			<td><strong>Tipo de Transferência</strong></td>
		</tr>
		
		<tr bgcolor="#cbe5fe">
	    	<td width="100%" align="center">
		
			<table width="100%" border="0">
			<tr>
				<td WIDTH="50%" align="center" >
						<html:radio property="tipoTransferencia" onclick="javascript:modificarTipoTransferencia();"
								value="<%=ConstantesSistema.TIPO_PESQUISA_INICIAL.toString()%>" />
						<strong>Entre Imóveis</strong>		
				</td>
				<td WIDTH="50%" align="center">
						<html:radio property="tipoTransferencia" onclick="javascript:modificarTipoTransferencia();"
							tabindex="5"
							value="<%=ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString()%>" />
						<strong>Entre Clientes</strong>
				</td>
			</tr>
			</table>
			
			</td>
	    </tr>
	    </table>	
		
		<br>	
	</logic:present>
	
	<table width="100%" align="center" bgcolor="#90c7fc" border="0">
	<tr>
		<td><strong>Imóvel de Destino</strong></td>
	</tr>
	
	<tr bgcolor="#cbe5fe">
    	<td width="100%" align="center">
	
		<table width="100%" border="0">
		<tr>
			<td WIDTH="120"><strong>Matrícula:<font color="#FF0000">*</font></strong></td>
			<td>
				<html:text property="idImovelDestino" size="10" maxlength="10" tabindex="2"
				onkeypress="validaEnterComMensagem(event, 'exibirTransferenciaDebitoCreditoDadosImovelAction.do?pesquisarImovelDestino=SIM', 'idImovelDestino', 'Matrícula');"
				onkeyup="limparImovelDestinoDigitacao();" /> 
				
				<a
				href="javascript:habilitarPesquisaImovelDestino();">
				<img src="<bean:message key='caminho.imagens'/>pesquisa.gif"
				width="23" height="21" alt="Pesquisar" border="0"></a> 
				
				<logic:present name="corImovelDestino">
	
					<logic:equal name="corImovelDestino" value="exception">
						<html:text property="inscricaoImovelDestino" size="45" readonly="true"
						style="background-color:#EFEFEF; border:0; color: #ff0000" />
					</logic:equal>
	
					<logic:notEqual name="corImovelDestino" value="exception">
						<html:text property="inscricaoImovelDestino" size="45" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000" />
					</logic:notEqual>
	
				</logic:present> 
				
				<logic:notPresent name="corImovelDestino">
	
					<logic:empty name="TransferenciaDebitoCreditoDadosImovelActionForm" property="idImovelDestino">
						<html:text property="inscricaoImovelDestino" value="" size="45" readonly="true"
						style="background-color:#EFEFEF; border:0; color: #ff0000" />
					</logic:empty>
					
					<logic:notEmpty name="TransferenciaDebitoCreditoDadosImovelActionForm" property="idImovelDestino">
						<html:text property="inscricaoImovelDestino" size="45" readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000" />
					</logic:notEmpty>
	
	
				</logic:notPresent> 
				
				<a href="javascript:limparImovelDestino();"> <img src="<bean:message key='caminho.imagens'/>limparcampo.gif"
				alt="Apagar" border="0"></a>
			</td>
		</tr>
		
		<tr>
			<td><strong>Cliente:</strong></td>
			<td>
	   			<html:text property="nomeClienteUsuarioImovelDestino" size="45" maxlength="45" readonly="true"
				style="background-color:#EFEFEF; border:0; color: #000000"/>
			</td>
		</tr>
		
		<tr>
			<td><strong>Sit. da Lig. de Água:</strong></td>
			<td>
	   			<html:text property="descricaoLigacaoAguaSituacaoImovelDestino" size="20" maxlength="20" readonly="true"
				style="background-color:#EFEFEF; border:0; color: #000000"/>
				
				<strong>&nbsp;&nbsp;Sit. da Lig. de Esgoto:&nbsp;&nbsp;</strong>
				
				<html:text property="descricaoLigacaoEsgotoSituacaoImovelDestino" size="20" maxlength="20" readonly="true"
				style="background-color:#EFEFEF; border:0; color: #000000"/>
			</td>
		</tr>
		</table>
		
		</td>
    </tr>
    </table>
    
    <br>
    
    <logic:present name="indicadorFaturamentoTitularDebito" scope="request">
		<table width="100%" align="center" bgcolor="#90c7fc" border="0">
		<tr>
			<td><strong>Cliente de Destino</strong></td>
		</tr>
		
		<tr bgcolor="#cbe5fe">
	    	<td width="100%" align="center">   
	    		<table width="100%" border="0"> 
				<tr >
					<td width="22%"><strong>Cliente :<font color="#FF0000">*</font></strong></td>
	
					<td>
						<html:text property="idClienteDestino" size="9" maxlength="9"
							onkeyup="validaEnterComMensagem(event, 
							'exibirTransferenciaDebitoCreditoDadosImovelAction.do?pesquisarClienteDestino=SIM', 'idClienteDestino','Cliente Destino');"
							onkeypress="document.forms[0].nomeClienteDestino.value='';
							document.forms[0].cpfCnpjClienteDestino.value='';" />
						
						<a href="javascript:habilitarPesquisaClienteDestino(document.forms[0]);" >
							<img width="23" height="21"	src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" />
						</a>
						
						<logic:present name="clienteInexistente" scope="request">
							<html:text property="nomeClienteDestino" size="30" maxlength="30"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:present> 
						<logic:notPresent name="clienteInexistente"
							scope="request">
							<html:text property="nomeClienteDestino" size="30" maxlength="30"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notPresent>
						
						<a href="javascript:limparCliente();">
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" />
						</a>
					</td>
					</tr>    				
					<tr >
						<td width="28%"><strong>CPF/CNPJ :</strong></td>
		
						<td>
							<html:text property="cpfCnpjClienteDestino" size="18" maxlength="18" 
							readonly="true" style="background-color:#EFEFEF; border:0;" />
						</td>
					</tr>
		
	    		</table>
			</td>
		</tr>       
	    </table>    
   </logic:present>	
   
   <br>
	
   <table width="100%" border="0">
   <tr>
       <td WIDTH="120"></td>
       <td><font color="#FF0000">*</font>&nbsp;Campos obrigat&oacute;rios</td>
   </tr>
	
	<tr>
		<td>
			<input type="button" name="Button" class="bottonRightCol" value="Limpar" tabindex="3"
			onClick="limpar();" style="width: 80px" />&nbsp; 
		</td>
		<td align="right">
			<gcom:controleAcessoBotao name="Button" value="Selecionar" tabindex="4"
			onclick="javascript:validarForm(document.forms[0]);" url="exibirTransferenciaDebitoCreditoDadosSelecaoAction.do" /> 
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


