<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page import="gcom.util.ConstantesSistema"%>

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="FiltrarClienteActionForm" dynamicJavascript="false"
	/>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript">
<!--
    function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
        var form = document.forms[0];

        if (tipoConsulta == 'arrecadador') {
          form.bancoBoletoBancarioFiltro.value = codigoRegistro;
          form.descricaoBancoBoletoBancarioFiltro.value = descricaoRegistro;
          form.descricaoBancoBoletoBancarioFiltro.style.color = "#000000";
        }
        if (tipoConsulta == 'imovel') {
            form.imovelBoletoBancarioFiltro.value = codigoRegistro;
            form.inscricaoImovelBoletoBancarioFiltro.value = descricaoRegistro;
            form.inscricaoImovelBoletoBancarioFiltro.style.color = "#000000";
        }
        if (tipoConsulta == 'cliente') {
            form.clienteBoletoBancarioFiltro.value = codigoRegistro;
            form.nomeClienteBoletoBancarioFiltro.value = descricaoRegistro;
            form.nomeClienteBoletoBancarioFiltro.style.color = "#000000";
        }
        if (tipoConsulta == 'numeroBoleto') {
            form.numeroBoletoCartaCobrancaBoletoBancarioFiltro.value = codigoRegistro;
            form.numeroBoletoCartaCobrancaBoletoBancarioFiltro.value = descricaoRegistro;
            form.numeroBoletoCartaCobrancaBoletoBancarioFiltro.style.color = "#000000";
        }
        
    }
    
    function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){
    	if(objetoRelacionado.readOnly != true){
    		if (objeto == null || codigoObjeto == null){
    			abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
    		}else{
    			if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
    				alert(msg);
    			}else{
    				abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
    			}
    		}
    	}
    }

    function limparDataInicialFinal(dataInicial, dataFinal){
    	if(dataInicial.value == ""){
    		dataFinal.value = "";
        }
    }

    function chamarFiltrar(){
    	var form = document.forms[0];

		if (verificarDatas() && validarIntervalos() && verificarArrecadador()){
	   		submeterFormPadrao(form);
		}
	}

	function verificarDatas(){

		var form = document.forms[0];
		
		if (!verificaDataMensagemPersonalizada(form.dataInicialEntradaBoletoBancarioFiltro, 'Data Inicial de Entrada é inválida')) { 
			return false; 
		}

		if (!verificaDataMensagemPersonalizada(form.dataFinalEntradaBoletoBancarioFiltro, 'Data Final de Entrada é inválida')) { 
			return false; 
		}

		if (!verificaDataMensagemPersonalizada(form.dataInicialVencimentoBoletoBancarioFiltro, 'Data Inicial de Vencimento é inválida')) { 
			return false; 
		}

		if (!verificaDataMensagemPersonalizada(form.dataFinalVencimentoBoletoBancarioFiltro, 'Data Final de Vencimento é inválida')) { 
			return false; 
		}

		if (!verificaDataMensagemPersonalizada(form.dataInicialCreditoBoletoBancarioFiltro, 'Data Inicial de Credito é inválida')) { 
			return false; 
		}

		if (!verificaDataMensagemPersonalizada(form.dataFinalCreditoBoletoBancarioFiltro, 'Data Final de Credito é inválida')) { 
			return false; 
		}

		if (!verificaDataMensagemPersonalizada(form.dataInicialCancelamentoBoletoBancarioFiltro, 'Data Inicial de Cancelamento é inválida')) { 
			return false; 
		}

		if (!verificaDataMensagemPersonalizada(form.dataFinalCancelamentoBoletoBancarioFiltro, 'Data Final de Cancelamento é inválida')) { 
			return false; 
		}

		if (!verificaDataMensagemPersonalizada(form.dataInicialPagamentoBoletoBancarioFiltro, 'Data Inicial de Pagamento é inválida')) { 
			return false; 
		}

		if (!verificaDataMensagemPersonalizada(form.dataFinalPagamentoBoletoBancarioFiltro, 'Data Final de Pagamento é inválida')) { 
			return false; 
		}
		
		return true;
	}
    
	function validarIntervalos(){
		
		var form = document.forms[0];
		
	    if (comparaData(form.dataInicialEntradaBoletoBancarioFiltro.value, '>', form.dataFinalEntradaBoletoBancarioFiltro.value)){
			  alert("A data final de entrada deve ser maior que a data inicial");
			  return false;
	    }

	    if (comparaData(form.dataInicialVencimentoBoletoBancarioFiltro.value, '>', form.dataFinalVencimentoBoletoBancarioFiltro.value)){
			  alert("A data final de vencimento deve ser maior que a data inicial");
			  return false;
	    }

	    if (comparaData(form.dataInicialCreditoBoletoBancarioFiltro.value, '>', form.dataFinalCreditoBoletoBancarioFiltro.value)){
			  alert("A data final de credito deve ser maior que a data inicial");
			  return false;
	    }

	    if (comparaData(form.dataInicialCancelamentoBoletoBancarioFiltro.value, '>', form.dataFinalCancelamentoBoletoBancarioFiltro.value)){
			  alert("A data final de cancelamento deve ser maior que a data inicial");
			  return false;
	    }
	    
	    if (comparaData(form.dataInicialPagamentoBoletoBancarioFiltro.value, '>', form.dataFinalPagamentoBoletoBancarioFiltro.value)){
			  alert("A data final de pagamento deve ser maior que a data inicial");
			  return false;
	    }

	    return true;
	}

	function verificarArrecadador() {
		var form = document.forms[0];
		
		if((form.numeroBoletoBancarioFiltro.value != null && form.numeroBoletoBancarioFiltro.value != "") ||
				(form.numeroBoletoCartaCobrancaBoletoBancarioFiltro.value != null && form.numeroBoletoCartaCobrancaBoletoBancarioFiltro.value != "")	) {
					if(form.bancoBoletoBancarioFiltro.value == null || form.bancoBoletoBancarioFiltro.value == ""){
						alert("Informe o Arrecadador.");
						  return false;
					}
		}
		return true;
	}
	
	function limparArrecadador() {
		var form = document.forms[0];
		
	    form.bancoBoletoBancarioFiltro.value = "";
	    form.descricaoBancoBoletoBancarioFiltro.value = "";
	}

	function limparNumeroBoletoCartaCobranca() {
		var form = document.forms[0];
		
	    form.numeroBoletoCartaCobrancaBoletoBancarioFiltro.value = "";
	}

	function limparImovel() {
		var form = document.forms[0];
		
	    form.imovelBoletoBancarioFiltro.value = "";
	    form.inscricaoImovelBoletoBancarioFiltro.value = "";
	}

	function limparCliente() {
		var form = document.forms[0];
		
	    form.clienteBoletoBancarioFiltro.value = "";
	    form.nomeClienteBoletoBancarioFiltro.value = "";
	}
	
	function desfazer(){
		 form = document.forms[0];

		 limparArrecadador();
		 limparNumeroBoletoCartaCobranca();
		 limparImovel();
		 limparCliente();

		 form.numeroBoletoBancarioFiltro.value = "";
		 form.idsSituacaoBoletoBancarioFiltro.value = "-1";
		 form.idsTipoDocumentoBoletoBancarioFiltro.value = "-1";
		 
		 form.dataInicialEntradaBoletoBancarioFiltro.value = "";

		 form.dataFinalEntradaBoletoBancarioFiltro.value = "";
		 form.dataInicialVencimentoBoletoBancarioFiltro.value = "";
		 
		 form.dataFinalVencimentoBoletoBancarioFiltro.value = "";
		 form.dataInicialCreditoBoletoBancarioFiltro.value = "";
		 form.dataFinalCreditoBoletoBancarioFiltro.value = "";
		 
		 form.dataInicialCancelamentoBoletoBancarioFiltro.value = "";
		 form.dataFinalCancelamentoBoletoBancarioFiltro.value = "";
		 form.idsMotivoCancelamentoBoletoBancarioFiltro.value = "-1";

		 form.dataInicialPagamentoBoletoBancarioFiltro.value = "";
		 form.dataFinalPagamentoBoletoBancarioFiltro.value = "";

		 form.indicadorTotalizarImovel.value = "2";
		 form.indicadorTotalizarImovel[1].checked = true;
	}

	function valorCheckAtualizar(){
	    var form = document.forms[0];

	    if(form.atualizarFiltro.checked == true){
		    form.atualizarFiltro.value = "1";
	    }else{
		    form.atualizarFiltro.value = "";    
	    }
	}

-->
</script>

</head>
<body leftmargin="5" topmargin="5"
	onload="setarFoco('${requestScope.nomeCampo}');">

<html:form action="/filtrarBoletoBancarioAction" method="post"
	onsubmit="return validateFiltrarBoletoBancarioActionForm(this);">

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
			<td width="620" valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11">
						<img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif" />
					</td>
					<td class="parabg">Filtrar Boleto Bancário</td>
					<td width="11">
						<img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" />
					</td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table bordercolor="#000000" width="100%" cellspacing="0">
				<tr>
					<td colspan="2">
						<p>Para filtrar um boleto bancário no sistema, informe os dados abaixo:</p>
					</td>
					<td align="right">
						<html:checkbox name="FiltrarBoletoBancarioActionForm" property="atualizarFiltro" value="1" onclick="javacript:valorCheckAtualizar();"/><strong>Atualizar</strong>
					</td>
					<td align="right"></td>
	          	</tr>
	        </table>
	        
	        <table bordercolor="#000000" width="100%" cellspacing="0">
				<tr>
					<td width="20%" height="30">
						<strong>Arrecadador:</strong>
					</td>
					<td width="432">
						    <html:text onkeyup="javascript:verificaNumeroInteiro(this);"  property="bancoBoletoBancarioFiltro" size="4" maxlength="3" tabindex="1" onkeypress="validaEnterComMensagem(event, 'exibirFiltrarBoletoBancarioAction.do', 'bancoBoletoBancarioFiltro', 'Arrecadador');" />
							<a href="javascript:abrirPopup('exibirPesquisarArrecadadorAction.do');" >
								<img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" title="Pesquisar Arrecadador" />
							</a> 
							
							<logic:present name="corBanco">
								<logic:equal name="corBanco" value="exception">
									<html:text property="descricaoBancoBoletoBancarioFiltro" onblur="document.forms[0].numeroBoletoBancarioFiltro.focus();" onkeyup="javascript:verificaNumeroInteiro(this);" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:equal>
	
								<logic:notEqual name="corBanco" value="exception">
									<html:text property="descricaoBancoBoletoBancarioFiltro" onblur="document.forms[0].numeroBoletoBancarioFiltro.focus();" onkeyup="javascript:verificaNumeroInteiro(this);" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEqual>
							</logic:present> 
							
							<logic:notPresent name="corBanco">
								<logic:empty name="FiltrarBoletoBancarioActionForm" property="bancoBoletoBancarioFiltro">
									<html:text property="descricaoBancoBoletoBancarioFiltro" onblur="document.forms[0].numeroBoletoBancarioFiltro.focus();" onkeyup="javascript:verificaNumeroInteiro(this);" value="" size="45"	readonly="true"	style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:empty>
								
								<logic:notEmpty name="FiltrarBoletoBancarioActionForm" property="bancoBoletoBancarioFiltro">
									<html:text property="descricaoBancoBoletoBancarioFiltro" onblur="document.forms[0].numeroBoletoBancarioFiltro.focus();" onkeyup="javascript:verificaNumeroInteiro(this);" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEmpty>
							</logic:notPresent> 
							
							<a href="javascript:limparArrecadador();document.forms[0].bancoBoletoBancarioFiltro.focus();"> 
							  <img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar" />
							</a>
						</td>
				</tr>
				
				<tr>
					<td height="24"><strong>Número do Boleto:</strong></td>
					<td><html:text tabindex="2"  property="numeroBoletoBancarioFiltro" size="9" maxlength="9"   onkeyup="javascript:verificaNumeroInteiro(this);"  /></td>
				</tr>
				
				<tr>
					<td height="10" width="160"><strong>Número do Boleto da Carta de Cobrança:</strong></td>
					<td width="403"><html:text tabindex="3" property="numeroBoletoCartaCobrancaBoletoBancarioFiltro" maxlength="9" onkeyup="javascript:verificaNumeroInteiro(this);" onblur="document.forms[0].imovelBoletoBancarioFiltro.focus();"
						size="9"/>
					<a
						href="javascript:abrirPopup('exibirPesquisarBoletoBancarioAction.do?indicador=1&reset=1', 400, 800);">
					<img width="23" height="21"
						src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" /></a>
					
					<a href="javascript:limparNumeroBoletoCartaCobranca();document.forms[0].numeroBoletoCartaCobrancaBoletoBancarioFiltro.focus();" > 
						<img border="0" src="<bean:message key='caminho.imagens'/>limparcampo.gif" style="cursor: hand;" /> 
					</a>	
					</td>									
				</tr>
				
				<tr>
					<td height="10" width="160"><strong>Matrícula do Imóvel:</strong></td>
					<td width="403"><html:text property="imovelBoletoBancarioFiltro" maxlength="9" tabindex="4"
						size="9" onkeyup="javascript:verificaNumeroInteiro(this);" 
						onkeypress="validaEnterComMensagem(event, 'exibirFiltrarBoletoBancarioAction.do', 'imovelBoletoBancarioFiltro', 'Matrícula do Imóvel')" />
					<a
						href="javascript:abrirPopup('exibirPesquisarImovelAction.do', 400, 800);">
					<img width="23" height="21"
						src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" /></a>
					
					<logic:present name="corInscricao">
	
						<logic:equal name="corInscricao" value="exception">
							<html:text property="inscricaoImovelBoletoBancarioFiltro"  onkeyup="javascript:verificaNumeroInteiro(this);" size="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
						</logic:equal>
		
						<logic:notEqual name="corInscricao" value="exception">
							<html:text property="inscricaoImovelBoletoBancarioFiltro"  onkeyup="javascript:verificaNumeroInteiro(this);" size="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
						</logic:notEqual>
		
					</logic:present>
		
					<logic:notPresent name="corInscricao">
		
						<logic:empty name="FiltrarBoletoBancarioActionForm" property="imovelBoletoBancarioFiltro">
							<html:text  onkeyup="javascript:verificaNumeroInteiro(this);" property="inscricaoImovelBoletoBancarioFiltro" size="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
						</logic:empty>
						<logic:notEmpty name="FiltrarBoletoBancarioActionForm" property="imovelBoletoBancarioFiltro">
							<html:text onkeyup="javascript:verificaNumeroInteiro(this);" property="inscricaoImovelBoletoBancarioFiltro" size="30" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
						</logic:notEmpty>
		
					</logic:notPresent>
					
					<a href="javascript:limparImovel();" > 
						<img border="0" src="<bean:message key='caminho.imagens'/>limparcampo.gif" style="cursor: hand;" /> 
					</a>	
					</td>									
				</tr>
								
				<tr>
					<td><strong>Cliente Responsável:</strong></td>
					<td colspan="2" height="24"><html:text maxlength="9"
						property="clienteBoletoBancarioFiltro" tabindex="5" size="10" onkeyup="javascript:verificaNumeroInteiro(this);" 
						onkeypress="javascript:validaEnter(event, 'exibirFiltrarBoletoBancarioAction.do', 'clienteBoletoBancarioFiltro');" />
					<a
						href="javascript:abrirPopup('exibirPesquisarClienteAction.do', 400, 800);">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Cliente" /></a> <logic:present
						name="idClienteNaoEncontrado">
						<logic:equal name="idClienteNaoEncontrado" value="exception">
							<html:text property="nomeClienteBoletoBancarioFiltro" size="50" maxlength="50" onkeyup="javascript:verificaNumeroInteiro(this);"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>
						<logic:notEqual name="idClienteNaoEncontrado" value="exception">
							<html:text property="nomeClienteBoletoBancarioFiltro" size="50" maxlength="50" onkeyup="javascript:verificaNumeroInteiro(this);"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> <logic:notPresent name="idClienteNaoEncontrado">
						<logic:empty name="FiltrarBoletoBancarioActionForm"
							property="clienteBoletoBancarioFiltro">
							<html:text property="nomeClienteBoletoBancarioFiltro" value="" size="50" onkeyup="javascript:verificaNumeroInteiro(this);"
								maxlength="50" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="FiltrarBoletoBancarioActionForm"
							property="clienteBoletoBancarioFiltro">
							<html:text property="nomeClienteBoletoBancarioFiltro" size="50" maxlength="50" onkeyup="javascript:verificaNumeroInteiro(this);"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>

					</logic:notPresent> <a href="javascript:limparCliente();">
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a>
				 </td>
				</tr>
				
				 <tr>
					<td>
						<strong>Situa&ccedil;&atilde;o do Boleto Bancário:</strong>
					</td>                 
                 	<td colspan="3">
                 		
                 			<html:select   property="idsSituacaoBoletoBancarioFiltro" multiple="multiple" style="width: 200px;" tabindex="6" >
                 			<html:option  disabled="true" value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>"> &nbsp; </html:option>
              					<html:options collection="colecaoBoletoBancarioSituacao" labelProperty="descricao" property="id"/>
             				</html:select>
             				
                 	</td>
                 </tr>    
                 
                 <tr>
					<td>
						<strong>Tipo de Documento do Boleto:</strong>
					</td>                 
                 	<td colspan="3">
                 			<html:select property="idsTipoDocumentoBoletoBancarioFiltro" multiple="multiple" style="width: 200px;" tabindex="7" >
                 			<html:option disabled="true" value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>"> &nbsp; </html:option>
              					<html:options collection="colecaoDocumentoTipo" labelProperty="descricaoDocumentoTipo" property="id"/>
             				</html:select>
                 	</td>
                 </tr>    
                 
				<tr>
			    <td><strong>Período de Entrada:</strong></td>
			    <td>
			      <strong> 
			        <html:text tabindex="8" property="dataInicialEntradaBoletoBancarioFiltro" size="10" maxlength="10" onkeyup="mascaraData(this,event);replicarCampo(document.forms[0].dataFinalEntradaBoletoBancarioFiltro,this);"/> 
			        <a href="javascript:abrirCalendario('FiltrarBoletoBancarioActionForm', 'dataInicialEntradaBoletoBancarioFiltro')">
				      <img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a> a
			      </strong> 
			      
			      <html:text tabindex="9" property="dataFinalEntradaBoletoBancarioFiltro" size="10" maxlength="10" onkeyup="mascaraData(this,event)"/> 
			      <a href="javascript:abrirCalendario('FiltrarBoletoBancarioActionForm', 'dataFinalEntradaBoletoBancarioFiltro')">
					<img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a> (dd/mm/aaaa)
			    </td>
			  </tr>	
			  
			  <tr>
			    <td><strong>Período de Vencimento:</strong></td>
			    <td>
			      <strong> 
			        <html:text tabindex="10" property="dataInicialVencimentoBoletoBancarioFiltro" size="10" maxlength="10" onkeyup="mascaraData(this,event);replicarCampo(document.forms[0].dataFinalVencimentoBoletoBancarioFiltro,this);" /> 
			        <a href="javascript:abrirCalendario('FiltrarBoletoBancarioActionForm', 'dataInicialVencimentoBoletoBancarioFiltro')">
				      <img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a> a
			      </strong> 
			      
			      <html:text tabindex="11" property="dataFinalVencimentoBoletoBancarioFiltro" size="10" maxlength="10" onkeyup="mascaraData(this,event);" /> 
			      <a href="javascript:abrirCalendario('FiltrarBoletoBancarioActionForm', 'dataFinalVencimentoBoletoBancarioFiltro')">
					<img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a> (dd/mm/aaaa)
			    </td>
			  </tr>	
			  
			  <tr>
			    <td><strong>Período de Pagamento:</strong></td>
			    <td>
			      <strong> 
			        <html:text property="dataInicialPagamentoBoletoBancarioFiltro" size="10" maxlength="10" onkeyup="mascaraData(this,event);replicarCampo(document.forms[0].dataFinalPagamentoBoletoBancarioFiltro,this);"/> 
			        <a href="javascript:abrirCalendario('FiltrarBoletoBancarioActionForm', 'dataInicialPagamentoBoletoBancarioFiltro')">
				      <img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a> a
			      </strong> 
			      
			      <html:text property="dataFinalPagamentoBoletoBancarioFiltro" size="10" maxlength="10" onkeyup="mascaraData(this,event)"/> 
			      <a href="javascript:abrirCalendario('FiltrarBoletoBancarioActionForm', 'dataFinalPagamentoBoletoBancarioFiltro')">
					<img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a> (dd/mm/aaaa)
			    </td>
			  </tr>	

			  <tr>
			    <td><strong>Período de Crédito:</strong></td>
			    <td>
			      <strong> 
			        <html:text tabindex="12" property="dataInicialCreditoBoletoBancarioFiltro" size="10" maxlength="10" onkeyup="mascaraData(this,event);replicarCampo(document.forms[0].dataFinalCreditoBoletoBancarioFiltro,this);"/> 
			        <a href="javascript:abrirCalendario('FiltrarBoletoBancarioActionForm', 'dataInicialCreditoBoletoBancarioFiltro')">
				      <img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a> a
			      </strong> 
			      
			      <html:text tabindex="13" property="dataFinalCreditoBoletoBancarioFiltro" size="10" maxlength="10" onkeyup="mascaraData(this,event)"/> 
			      <a href="javascript:abrirCalendario('FiltrarBoletoBancarioActionForm', 'dataFinalCreditoBoletoBancarioFiltro')">
					<img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a> (dd/mm/aaaa)
			    </td>
			  </tr>	
			  
			  <tr>
			    <td><strong>Período de Cancelamento:</strong></td>
			    <td>
			      <strong> 
			        <html:text tabindex="14" property="dataInicialCancelamentoBoletoBancarioFiltro" size="10" maxlength="10" onkeyup="mascaraData(this,event);replicarCampo(document.forms[0].dataFinalCancelamentoBoletoBancarioFiltro,this);"/> 
			        <a href="javascript:abrirCalendario('FiltrarBoletoBancarioActionForm', 'dataInicialCancelamentoBoletoBancarioFiltro')">
				      <img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a> a
			      </strong> 
			      
			      <html:text tabindex="15" property="dataFinalCancelamentoBoletoBancarioFiltro" size="10" maxlength="10" onkeyup="mascaraData(this,event)"/> 
			      <a href="javascript:abrirCalendario('FiltrarBoletoBancarioActionForm', 'dataFinalCancelamentoBoletoBancarioFiltro')">
					<img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif" width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a> (dd/mm/aaaa)
			    </td>
			  </tr>	
			  
			   <tr>
					<td>
						<strong>Motivo Cancelamento do Boleto:</strong>
					</td>                 
                 	
                 	<td colspan="3" align="left">
           			<html:select property="idsMotivoCancelamentoBoletoBancarioFiltro" multiple="multiple"  style="width: 200px;" tabindex="16" >
           				
           				<html:option disabled="true" value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>"> &nbsp; </html:option>
        					<html:options 
        					collection="colecaoBoletoBancarioMotivoCancelamento" labelProperty="descricao" property="id"/>
       				</html:select>
       				</td>
             		
                </tr> 
			  
			  <tr>
					<td><strong>Totalização por imóvel?</strong></td>
					<td><strong> 
						<html:radio property="indicadorTotalizarImovel" value="1"/><strong>Sim 
						<html:radio	property="indicadorTotalizarImovel" value="2"/>Nao</strong>
					</strong></td>
				</tr>

			  <tr>
				<td align="left"><input type="button" name="ButtonReset"
					class="bottonRightCol" value="Limpar"
					onclick="desfazer();">&nbsp;<input type="button"
						name="ButtonCancelar" class="bottonRightCol" value="Cancelar"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"></td>
				<td colspan="2" height="24" align="right">
				<gcom:controleAcessoBotao
					name="Button" value="Filtrar"
					onclick="javascript:chamarFiltrar();" url="filtrarImovelAction.do" /></td>
				<td></td>
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