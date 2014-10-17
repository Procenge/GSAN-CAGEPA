<%@page import="gcom.util.ConstantesSistema"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>

<%@ page import="gcom.contabil.UnidadeContabilAgrupamento" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<html:javascript staticJavascript="false"  formName="ConsultarLancamentoSinteticoActionForm" dynamicJavascript="false"/>
<script language="JavaScript">

function reexibir(){			
	var form = document.forms[0];
    form.action = "/gsan/exibirConsultarLancamentoSinteticoAction.do?menu=sim";
    form.submit();
}

function pesquisar(){
	var form = document.forms[0];
    form.action = "/gsan/consultarLancamentoSinteticoAction.do?menu=sim";
    form.submit();
}

function consultarLancamentosAnaliticos(id,dataContabil,contaDebito,contaCredito,
		descricaoModulo,descricaoEventoComercial,descricaoCategoria,
		lancamentoItemContabil,impostoTipo,valorString){

	document.getElementById("idSintetico").value = id;
	document.getElementById("dataContabilFormatada").value = dataContabil;
	document.getElementById("contaDebito").value = contaDebito;
	document.getElementById("contaCredito").value = contaCredito;
	document.getElementById("descricaoModulo").value = descricaoModulo;
	document.getElementById("descricaoEventoComercial").value = descricaoEventoComercial;
	document.getElementById("descricaoCategoria").value = descricaoCategoria;
	document.getElementById("lancamentoItemContabil").value = lancamentoItemContabil;
	document.getElementById("impostoTipo").value = impostoTipo;
	document.getElementById("valorString").value = valorString;   
									
		
	var form = document.forms[0];
    form.action = "/gsan/consultarLancamentoAnaliticoAction.do?menu=sim";
    form.submit();
}

	function limparOrigem(tipo){
		var form = document.forms[0];
		
		switch(tipo){
		
		case 1:
			form.localidadeInicial.value = "";
			form.nomeLocalidadeInicial.value = "";
			form.localidadeFinal.value = "";
			form.nomeLocalidadeFinal.value = "";
			break;
		case 2:
			form.contaAuxiliarInicial.value = "";
			form.nomeContaAuxiliarInicial.value = "";
			form.contaAuxiliarFinal.value = "";
			form.nomeContaAuxiliarFinal.value = "";
			break;
		}
	}
	
	function limparDestino(tipo){
		var form = document.forms[0];
		
		switch(tipo){
		
		case 1:
			form.localidadeFinal.value = "";
			form.nomeLocalidadeFinal.value = "";
		    break;
		case 2:
			form.contaAuxiliarFinal.value = "";
			form.nomeContaAuxiliarFinal.value = "";
			break;
		}
	}

	function limparBorrachaOrigem(tipo){
		var form = document.forms[0];
		
		switch(tipo){
			case 1:
				form.localidadeInicial.value = "";
				form.nomeLocalidadeInicial.value = "";
				form.localidadeFinal.value = "";
				form.nomeLocalidadeFinal.value = "";
				break;
			case 2:
				form.contaAuxiliarInicial.value = "";
				form.nomeContaAuxiliarInicial.value = "";
				form.contaAuxiliarFinal.value = "";
				form.nomeContaAuxiliarFinal.value = "";
				break;
		}
	}
	
	function limparBorrachaDestino(tipo){
		var form = document.forms[0];
		
		switch(tipo){
			case 1:
				form.localidadeFinal.value = "";
				form.nomeLocalidadeFinal.value = "";
				break;
			case 2:
				form.contaAuxiliarFinal.value = "";
				form.nomeContaAuxiliarFinal.value = "";
				break;
		}
	}
	
	function replicarLocalidade(){
		var formulario = document.forms[0]; 
		
		formulario.localidadeFinal.value = formulario.localidadeInicial.value;
	}
	
	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,campo){
		
		document.forms[0].campoOrigem.value = campo.name;
		if(!campo.disabled){
	  		if (objeto == null || codigoObjeto == null){
	     		if(tipo == "" ){
	      			abrirPopup(url,altura, largura);
	     		}else{
		  			abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
		 		}
	 		}else{
				if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
					alert(msg);
				}else{
					abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto, altura, largura);
				}
			}
		}
	}
	
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

		var form = document.forms[0];
		
		if (tipoConsulta == 'localidadeOrigem') {
      		form.localidadeInicial.value = codigoRegistro;
	  		form.nomeLocalidadeInicial.value = descricaoRegistro;
	  		form.localidadeFinal.value = codigoRegistro;
      		form.nomeLocalidadeFinal.value = descricaoRegistro;
	  		form.nomeLocalidadeInicial.style.color = "#000000";
	  		form.nomeLocalidadeFinal.style.color = "#000000";
		}

		if (tipoConsulta == 'localidadeDestino') {
      		form.localidadeFinal.value = codigoRegistro;
      		form.nomeLocalidadeFinal.value = descricaoRegistro;
	  		form.nomeLocalidadeFinal.style.color = "#000000";
		}
		
		if (tipoConsulta == 'arrecadadorOrigem') {
      		form.contaAuxiliarInicial.value = codigoRegistro;
	  		form.nomeContaAuxiliarInicial.value = descricaoRegistro;
	  		form.contaAuxiliarFinal.value = codigoRegistro;
      		form.nomeContaAuxiliarFinal.value = descricaoRegistro;
	  		form.nomeContaAuxiliarInicial.style.color = "#000000";
	  		form.nomeContaAuxiliarFinal.style.color = "#000000";
		}
		
		if (tipoConsulta == 'arrecadadorDestino') {
      		form.contaAuxiliarFinal.value = codigoRegistro;
      		form.nomeContaAuxiliarFinal.value = descricaoRegistro;
	  		form.nomeContaAuxiliarFinal.style.color = "#000000";
		}
	}
	
	function replicarContaContabil(){
		var formulario = document.forms[0]; 
		formulario.contaContabilFinal.value = formulario.contaContabilInicial.value;
	}
	
	function replicarContaAuxiliar(){
		var formulario = document.forms[0]; 
		
		formulario.contaAuxiliarFinal.value = formulario.contaAuxiliarInicial.value;
	}
	
	function replicarRegional(){
		var formulario = document.forms[0]; 
		
		formulario.regionalFinal.value = formulario.regionalInicial.value;
	}
	
	function validarDatas(){
		var formulario = document.forms[0];
		
		var dtInicio = formulario.dtInicio.value;
		var dtFim = formulario.dtFim.value;
		var retorno = comparaData(dtInicio, '>', dtFim );
		
		if(retorno){
			alert('Data final não pode ser menor que data inicial.');
			formulario.dtFim.value = '';
			formulario.dtFim.focus();
			return false;
		}
	}

</script>
</head>
<body leftmargin="5" topmargin="5"
	onload="setarFoco('${requestScope.nomeCampo}');">

<html:form action="/exibirConsultarLancamentoSinteticoAction" method="post"
	onsubmit="return validateConsultarLancamentoSinteticoActionForm(this);">
	
	<input type="hidden" id="idSintetico" name="idSintetico">
	<input type="hidden" id="dataContabilFormatada" name="dataContabilFormatada">
	<input type="hidden" id="contaDebito" name="contaDebito">
	<input type="hidden" id="contaCredito" name="contaCredito">
	<input type="hidden" id="descricaoModulo" name="descricaoModulo">
	<input type="hidden" id="descricaoEventoComercial" name="descricaoEventoComercial">
	<input type="hidden" id="descricaoCategoria" name="descricaoCategoria">
	<input type="hidden" id="lancamentoItemContabil" name="lancamentoItemContabil">
	<input type="hidden" id="impostoTipo" name="impostoTipo">
	<input type="hidden" id="valorString" name="valorString">
	<input type="hidden" name="campoOrigem" value=""/>

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
					<td class="parabg">Filtrar Lançamentos</td>
					<td width="11">
						<img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif" />
					</td>
				</tr>
			</table>
			<p>&nbsp;</p>			
	        
	        <table bordercolor="#000000" width="100%" cellspacing="0">
	        	<tr>
					<td><strong>Data Contábil Início:<font color="#FF0000">*</font></strong></td>
					<td><html:text property="dtInicio" size="10"
						maxlength="10" 
						onkeyup="mascaraData(this, event);replicarCampo(document.forms[0].dtFim,this);"
						onfocus="replicarCampo(document.forms[0].dtFim,this);" />
					<a href="javascript:abrirCalendarioReplicando('ConsultarLancamentoSinteticoActionForm', 'dtInicio', 'dtFim');">
					<img border="0"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
					dd/mm/aaaa</td>
				</tr>

				<tr>
					<td><strong>Data Contábil Fim:<font color="#FF0000">*</font></strong></td>
					<td><html:text property="dtFim" size="10" maxlength="10"
						onkeyup="mascaraData(this, event);"
						onblur="validarDatas();" /> <a
						href="javascript:abrirCalendario('ConsultarLancamentoSinteticoActionForm', 'dtFim')">
					<img border="0"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
					dd/mm/aaaa</td>
				</tr>
				
				<tr>
					<td>
						<strong>Localidade Inicial:</strong>
					</td>
					<td>
						<html:text maxlength="3" 
							property="localidadeInicial" 
							size="3"
							onblur="verificaNumero(this); replicarLocalidade();" 
							onkeypress="javascript:validaEnterComMensagem(event,'exibirConsultarLancamentoSinteticoAction.do?objetoConsulta=1','localidadeInicial','Localidade Inicial');return isCampoNumerico(event);"
							/>
							
						<a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'origem', null, null, 275, 480, '', document.forms[0].localidadeInicial.value);limparOrigem(1);">
							<img width="23" 
								height="21" 
								border="0" 
								style="cursor:hand;"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Localidade" /></a>
								
						<logic:present name="localidadeInicialEncontrada">
							<html:text property="nomeLocalidadeInicial" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 

						<logic:notPresent name="localidadeInicialEncontrada">
							<html:text property="nomeLocalidadeInicial" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>
						
						<a href="javascript:limparBorrachaOrigem(1);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>
				
				<tr>
					<td>
						<strong>Localidade Final:</strong>
					</td>
					<td>
						<html:text maxlength="3" 
							property="localidadeFinal" 
							size="3"
							onkeypress="validaEnterComMensagem(event,'exibirConsultarLancamentoSinteticoAction.do?objetoConsulta=2','localidadeFinal','Localidade Final'); return isCampoNumerico(event);"
							onblur="verificaNumero(this);"/>
							
						<a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'destino', null, null, 275, 480, '',document.forms[0].localidadeFinal);limparDestino(1);">
							<img width="23" 
								height="21" 
								border="0" 
								style="cursor:hand;"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Localidade" />
						</a>

						<logic:present name="localidadeFinalEncontrada">
							<html:text property="nomeLocalidadeFinal" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 

						<logic:notPresent name="localidadeFinalEncontrada">
							<html:text property="nomeLocalidadeFinal" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>
						
						<a href="javascript:limparBorrachaDestino(1);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>
				
				<tr>
					<td><strong>Conta Contábil Inicial:</strong></td>
					<td colspan="2">
						<html:select name="ConsultarLancamentoSinteticoActionForm" property="contaContabilInicial" onchange="javascript:replicarContaContabil();">
						  <html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						  <logic:present name="colecaoContaContabil">
							<html:options collection="colecaoContaContabil" labelProperty="numeroConta" property="id" />
						  </logic:present>			
						</html:select>
					</td>
				</tr>
				
				<tr>
					<td><strong>Conta Contábil Final:</strong></td>
					<td colspan="2">
						<html:select name="ConsultarLancamentoSinteticoActionForm" property="contaContabilFinal">
						  <html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						  <logic:present name="colecaoContaContabil">
							<html:options collection="colecaoContaContabil" labelProperty="numeroConta" property="id" />
						  </logic:present>			
						</html:select>
					</td>
				</tr>
				
				<tr>
					<td>
						<strong>Conta Auxiliar Inicial:</strong>
					</td>
					<td>
						<html:text maxlength="3" 
							property="contaAuxiliarInicial" 
							size="3"
							onblur="verificaNumero(this); replicarContaAuxiliar();" 
							onkeypress="javascript:validaEnterComMensagem(event,'exibirConsultarLancamentoSinteticoAction.do?objetoConsulta=3','contaAuxiliarInicial','Conta Auxiliar Inicial');return isCampoNumerico(event);"
							/>
							
						<a href="javascript:chamarPopup('exibirPesquisarArrecadadorAction.do', 'origem', null, null, 275, 480, '', document.forms[0].contaAuxiliarInicial.value);limparOrigem(2);">
							<img width="23" 
								height="21" 
								border="0" 
								style="cursor:hand;"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Arrecadador" /></a>
								
						<logic:present name="contaAuxiliarInicialEncontrada">
							<html:text property="nomeContaAuxiliarInicial" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 

						<logic:notPresent name="contaAuxiliarInicialEncontrada">
							<html:text property="nomeContaAuxiliarInicial" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>
						
						<a href="javascript:limparBorrachaOrigem(2);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>
				
				<tr>
					<td>
						<strong>Conta Auxiliar Final:</strong>
					</td>
					<td>
						<html:text maxlength="3" 
							property="contaAuxiliarFinal" 
							size="3"
							onkeypress="validaEnterComMensagem(event,'exibirConsultarLancamentoSinteticoAction.do?objetoConsulta=4','contaAuxiliarFinal','Conta Auxiliar Final'); return isCampoNumerico(event);"
							onblur="verificaNumero(this);"/>
							
						<a href="javascript:chamarPopup('exibirPesquisarArrecadadorAction.do', 'destino', null, null, 275, 480, '',document.forms[0].contaAuxiliarFinal);limparDestino(2);">
							<img width="23" 
								height="21" 
								border="0" 
								style="cursor:hand;"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Arrecadador" />
						</a>

						<logic:present name="contaAuxiliarFinalEncontrada">
							<html:text property="nomeContaAuxiliarFinal" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 

						<logic:notPresent name="contaAuxiliarFinalEncontrada">
							<html:text property="nomeContaAuxiliarFinal" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>
						
						<a href="javascript:limparBorrachaDestino(2);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>
				
				<tr>
					<td><strong>Regional Inicial:</strong></td>
					<td colspan="2">
						<html:select name="ConsultarLancamentoSinteticoActionForm" property="regionalInicial" onchange="javascript:replicarRegional();">
						  <html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						  <logic:present name="colecaoRegional">
							<html:options collection="colecaoRegional" labelProperty="nome" property="id" />
						  </logic:present>			
						</html:select>
					</td>
				</tr>
				
				<tr>
					<td><strong>Regional Final:</strong></td>
					<td colspan="2">
						<html:select name="ConsultarLancamentoSinteticoActionForm" property="regionalFinal">
						  <html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						  <logic:present name="colecaoRegional">
							<html:options collection="colecaoRegional" labelProperty="nome" property="id" />
						  </logic:present>			
						</html:select>
					</td>
				</tr>
				
				<logic:equal name="unidadeContabilAgrupamento" value="<%= "" + UnidadeContabilAgrupamento.LOCALIDADE %>">
					<tr>
						<td><strong>Localidade:</strong></td>
						<td colspan="2">
							<html:select name="ConsultarLancamentoSinteticoActionForm" property="idUnidadeAgrupamento">
							  <html:option value="">&nbsp;</html:option>
							  <logic:present name="colecaoUnidadeAgrupamentoContabil">
								<html:options collection="colecaoUnidadeAgrupamentoContabil" labelProperty="descricao" property="id" />
							  </logic:present>			
							</html:select>
						</td>
					</tr>
				</logic:equal>
				<logic:equal name="unidadeContabilAgrupamento" value="<%= "" + UnidadeContabilAgrupamento.SETOR_COMERCIAL %>">				
					<tr>
						<td><strong>Setor Comercial:</strong></td>
						<td colspan="2">
							<html:select name="ConsultarLancamentoSinteticoActionForm" property="idUnidadeAgrupamento">
							  <html:option value="">&nbsp;</html:option>
							  <logic:present name="colecaoUnidadeAgrupamentoContabil">
								<html:options collection="colecaoUnidadeAgrupamentoContabil" labelProperty="descricao" property="id" />
							  </logic:present>			
							</html:select>
						</td>
					</tr>
				</logic:equal>
				<logic:equal name="unidadeContabilAgrupamento" value="<%= "" + UnidadeContabilAgrupamento.GERENCIA_REGIONAL %>">
					<tr>
						<td><strong>Gerência Regional:</strong></td>
						<td colspan="2">
							<html:select name="ConsultarLancamentoSinteticoActionForm" property="idUnidadeAgrupamento">
							  <html:option value="">&nbsp;</html:option>
							  <logic:present name="colecaoUnidadeAgrupamentoContabil">
								<html:options collection="colecaoUnidadeAgrupamentoContabil" labelProperty="nome" property="id" />
							  </logic:present>			
							</html:select>
						</td>
					</tr>
				</logic:equal>
				<logic:equal name="unidadeContabilAgrupamento" value="<%= "" + UnidadeContabilAgrupamento.UNIDADE_NEGOCIO %>">
					<tr>
						<td><strong>Unidade Negocio:</strong></td>
						<td colspan="2">
							<html:select name="ConsultarLancamentoSinteticoActionForm" property="idUnidadeAgrupamento">
							  <html:option value="">&nbsp;</html:option>
							  <logic:present name="colecaoUnidadeAgrupamentoContabil">
								<html:options collection="colecaoUnidadeAgrupamentoContabil" labelProperty="nome" property="id" />
							  </logic:present>			
							</html:select>
						</td>
					</tr>				
				</logic:equal>
				
				<tr>
					<td><strong>Modulo:</strong></td>
					<td colspan="2">
						<html:select name="ConsultarLancamentoSinteticoActionForm" property="idModulo" onchange="javascript:reexibir();">
						  <html:option value="">&nbsp;</html:option>
						  <logic:present name="colecaoModulo">
							<html:options collection="colecaoModulo" labelProperty="descricaoModulo" property="id" />
						  </logic:present>			
						</html:select>
					</td>
				</tr>
				
				<tr>
					<td><strong>Evento Comercial:</strong></td>
					<td colspan="2">
						<html:select name="ConsultarLancamentoSinteticoActionForm" property="idEventoComercial" onchange="javascript:reexibir();">
						  <html:option value="">&nbsp;</html:option>
						  <logic:present name="colecaoEventoComercial">
							<html:options collection="colecaoEventoComercial" labelProperty="descricao" property="id" />
						  </logic:present>			
						</html:select>
					</td>
				</tr>
				
				<tr>
					<logic:notEmpty name="colecaoComplementoItemContabil">
						<td><strong>Lançamento Item Contbil:</strong></td>
						
						<td colspan="2">								
							<html:select name="ConsultarLancamentoSinteticoActionForm" property="idComplementoLancamentoItemContabil">
							  <html:option value="">&nbsp;</html:option>
							  <logic:present name="colecaoComplementoItemContabil">
								<html:options collection="colecaoComplementoItemContabil" labelProperty="descricao" property="id" />
							  </logic:present>			
							</html:select>																
						</td>
					</logic:notEmpty>				
					
					
					<logic:notEmpty name="colecaoComplementoImposto">
						<td><strong>Imposto:</strong></td>
						
						<td colspan="2">								
							<html:select name="ConsultarLancamentoSinteticoActionForm" property="idComplementoImposto">
						  		<html:option value="">&nbsp;</html:option>
						  		<logic:present name="colecaoComplementoImposto">
									<html:options collection="colecaoComplementoImposto" labelProperty="descricao" property="id" />
						  		</logic:present>			
							</html:select>																
						</td>													
					</logic:notEmpty>	
						
				</tr>
				
				<tr>
					<td><strong>Categoria:</strong></td>
					<td colspan="2">
						<html:select name="ConsultarLancamentoSinteticoActionForm" property="idCategoria">
						  <html:option value="">&nbsp;</html:option>
						  <logic:present name="colecaoCategoria">
							<html:options collection="colecaoCategoria" labelProperty="descricao" property="id" />
						  </logic:present>			
						</html:select>
					</td>
				</tr>				
				<tr>
					<td colspan="3">
						<table width="100%" border="0">
							<tr>								
								<td align="right">
									<input type="button" name="ButtonPesquisar" class="bottonRightCol"
										value="Pesquisar" onclick="javascript:pesquisar();">
								</td>
							</tr>	
						</table>
					</td>
				</tr>
				<tr>
				<td colspan="3">

				<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="4" bgcolor="#000000" height="2"></td>
				</tr>
				<tr>
					<td>
						<table width="100%" bgcolor="#99CCFF">
							<!--header da tabela interna -->
							<tr bgcolor="#99CCFF">
								<td width="62" align="center">
									<strong>Data Cont&aacute;bil</strong>
								</td>								
								<td width="62" align="center">
									<strong>Conta D&eacute;bito</strong>
								</td>
								<td width="62" align="center">
									<strong>Conta Credito</strong>
								</td>
								<td width="62" align="center">
									<strong>Modulo</strong>
								</td>
								<td width="165" align="center">
									<strong>Evento Comercial</strong>
								</td>
								<td width="62" align="center">
									<strong>Categoria</strong>
								</td>
								<td width="62" align="center">
									<strong>Complemento</strong>
								</td>
								<td width="62" align="center">
									<strong>Valor</strong>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<!-- --------------------------------------------------------------------------------- -->											
				
				
				<tr>
					<td>
					
						<table width="100%" bgcolor="#99CCFF">										
							<logic:present name="lancamentos">
								<%int cont = 0;%>
								<logic:iterate name="lancamentos" id="lancamento">									
									<%cont = cont + 1;
									if (cont % 2 == 0) {%>
									<tr bgcolor="#cbe5fe">
									<%} else {%>
									<tr bgcolor="#FFFFFF">
									<%}%>										
										<td width="62" align="center">
											<a href="javascript:consultarLancamentosAnaliticos('${lancamento.id}','${lancamento.dataContabilFormatada}',
											'${lancamento.contaDebito}','${lancamento.contaCredito}','${lancamento.descricaoModulo}',
											'${lancamento.descricaoEventoComercial}','${lancamento.descricaoCategoria}',
											'${lancamento.lancamentoItemContabil}','${lancamento.impostoTipo}',
											'${lancamento.valorString}')">																								
											<bean:write name="lancamento" property="dataContabilFormatada" /></a>
										</td>
										<td width="62" align="center">
											<bean:write name="lancamento" property="contaDebito" />
										</td>
										<td width="62" align="center">
											<bean:write name="lancamento" property="contaCredito" />
										</td>
										<td width="62" align="center">
											<bean:write name="lancamento" property="descricaoAbrevModulo" />																					
										</td>
										<td width="165" align="center">
											<bean:write name="lancamento" property="descricaoEventoComercial" />											
										</td>
										<td width="62" align="center">
											<bean:write name="lancamento" property="descricaoAbrevCategoria" />																						
										</td>
										<td width="62" align="center">
											<logic:present name="lancamento" property="abrevLancamentoItemContabil">											
												<bean:write name="lancamento" property="abrevLancamentoItemContabil" />											
											</logic:present>
											<logic:present name="lancamento" property="abrevImpostoTipo">											
												<bean:write name="lancamento" property="abrevImpostoTipo" />											
											</logic:present>
										</td>										
										<td width="62" align="center">
											<bean:write name="lancamento" property="valorString" />
										</td>	
																																
									</tr>									
								</logic:iterate>
							</logic:present>						
						</table>												
					</td>
				</tr>
				
				<!-- ---------------------------------------------------------------------------------- -->
				  
				
			</table>

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