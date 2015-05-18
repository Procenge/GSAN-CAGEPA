<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<%@ page import="gcom.util.ConstantesSistema"%>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false"  formName="GerarRelatorioContasEmAtrasoPorIdadeDividaActionForm"/>

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>

<script language="JavaScript">
	
	function submeterForm(){
		
		var form = document.forms[0];
		if(validarCampos()) {
			if(form.ehRelatorioBatch.value == 2) {
				form.target = "_blank";				
			}
			form.submit();
			form.target = "";
		}
	}
	
	function validarCampos(){
		
		var form = document.forms[0];
		var msg = "";
		
		msg = acrescentarMsg(msg, verificarAtributosInicialFinal(form.P_GR_INI,form.P_GR_FIN,"Gerência Regional"));
		//msg = acrescentarMsg(msg, verificarAtributosInicialFinal(form.P_LOC_INI,form.P_LOC_FIN,"Localidade"));
		//msg = acrescentarMsg(msg, verificarAtributosInicialFinal(form.P_SET_INI,form.P_SET_FIN,"Setor de Faturamento"));
		msg = acrescentarMsg(msg, verificarAtributosInicialFinal(form.P_UNE_INI,form.P_UNE_FIN,"Unidade de Negocio"));

		if(form.P_AM_REF.value == '') {
			msg = acrescentarMsg(msg, "Informe a Referência do Débito");
		}
		
		if((form.P_TP_RELATORIO[0].checked == false) && (form.P_TP_RELATORIO[1].checked == false) 
				&& (form.P_TP_RELATORIO[2].checked == false)) {
			msg = acrescentarMsg(msg, "Informe a Tipo do Relatório");
		} 
		
		if( msg != ""){
			alert(msg);
			return false;
		}
		
		return true;
	}
	
	function verificarAtributosInicialFinal(campoInicio, campoFim, nomeCampo){
		
		var msg = "";

		if ((campoInicio.value == '') || (campoInicio.value == '-1')) {
			msg = acrescentarMsg(msg, "Informe "+nomeCampo+" Inicial");
		} 

		if ((campoFim.value == '') || (campoFim.value == '-1')) {
			msg = acrescentarMsg(msg, "Informe "+nomeCampo+" Final");
				
		}

		if((campoInicio.value != '') && (campoInicio.value != '-1') 
				&& (campoFim.value != '') && (campoFim.value != '-1')) {
							
		    if (Number(campoInicio.value) > Number(campoFim.value)){
		    	msg = acrescentarMsg(msg, nomeCampo + " Final deve ser maior ou igual a " + nomeCampo + " Inicial.");
			}
		}
		
		
		return msg;
	}

	function acrescentarMsg(msgCompleta, novaMsg) {
		var msg = msgCompleta;
		
		if(msg != "") {
			msg = msg + "\n" + novaMsg;
		} else {
			msg = novaMsg;
		}

		return msg;
	}
	
	
	
	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,campo){

		document.forms[0].campoOrigem.value = campo;

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
	
  	function reloadForm(){
  		var form = document.forms[0];
  	
  		form.action='exibirGerarRelatorioContasEmAtrasoPorIdadeDividaAction.do';
	    form.submit();
  	}

	// Função que centraliza toda a operação de limpeza do formulário
	// O parêmetro "nivel" segue a seguinte regra:
	// 0 - Limpa todo o formulário 
	// 1 - Limpa da Regional em diante
	// 2 - Limpa de Localidade em diante
	// 3 - Limpa do Setor de Faturamento em diante
	// 5 - Limpa da Rota em diante 
  	function limpar(nivel){

  		var form = document.forms[0];

  		if(nivel <= 0) {
	  		form.P_TP_RELATORIO[0].checked = false;
	  		form.P_TP_RELATORIO[1].checked = false;
	  		form.P_TP_RELATORIO[2].checked = false;
  		}  		

  		if(nivel <= 1) {
  		    form.P_GR_INI.value = "-1";
  		    form.P_GR_FIN.value = "-1";
  		}

  		if(nivel <= 2) {
  		    form.P_LOC_INI.value = "";
		    form.P_LOC_FIN.value = "";
		    form.nomeLocalidadeInicial.value = "";
		    form.nomeLocalidadeFinal.value = "";
  		}  

  		if(nivel <= 3) {
			form.P_SET_INI.value = "";
			form.P_SET_FIN.value = "";
			form.nomeSetorFaturamentoInicial.value = "";
			form.nomeSetorFaturamentoFinal.value = "";
  		}
  		
  		if(nivel <= 1) {
  		    form.P_UNE_INI.value = "-1";
  		    form.P_UNE_FIN.value = "-1";
  		}
  	}
  	
	function limparBorracha(tipo){
		var form = document.forms[0];
		
		switch(tipo){
			case 1: // Localidade Inicial
				limpar(3);
				break;
				
			case 2: // Localidade Final
				form.P_LOC_FIN.value = "";
				form.nomeLocalidadeFinal.value = "";
				break;
				
			case 3: // Setor Inicial		     	
		     	limpar(4);
		     	break;

			case 4: // Setor Final		     	
		     	form.P_SET_FIN.value = "";
		     	form.nomeSetorFaturamentoFinal.value = "";
		     	break;
		}
	}
	
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

		var form = document.forms[0];

		var valorAnterior;

		if(form.campoOrigem.value == 'P_LOC_INI') {

			valorAnterior = form.P_LOC_INI.value;
					
      		form.P_LOC_INI.value = codigoRegistro;
	  		form.nomeLocalidadeInicial.value = descricaoRegistro;
	  		form.nomeLocalidadeInicial.style.color = "#000000";

	  		if((form.P_LOC_FIN.value == '') 
	  		  		|| (form.P_LOC_FIN.value == valorAnterior)) {		  		

	      		form.P_LOC_FIN.value = codigoRegistro;
		  		form.nomeLocalidadeFinal.value = descricaoRegistro;	 
		  		form.nomeLocalidadeFinal.style.color = "#000000";
		  	  	
	  		}	  		
	  		
		} else if(form.campoOrigem.value == 'P_LOC_FIN') {		
      		form.P_LOC_FIN.value = codigoRegistro;
	  		form.nomeLocalidadeFinal.value = descricaoRegistro;	 
	  		form.nomeLocalidadeFinal.style.color = "#000000";
	  		 		
		} else if(form.campoOrigem.value == 'P_SET_INI') {		

			valorAnterior = form.P_SET_INI.value;
			
      		form.P_SET_INI.value = codigoRegistro;
	  		form.nomeSetorFaturamentoInicial.value = descricaoRegistro;	  		
	  		form.nomeSetorFaturamentoInicial.style.color = "#000000";

	  		if((form.P_SET_FIN.value == '') 
	  		  		|| (form.P_SET_FIN.value == valorAnterior)) {

	  			form.P_SET_FIN.value = codigoRegistro;
		  		form.nomeSetorFaturamentoFinal.value = descricaoRegistro;	  		
		  		form.nomeSetorFaturamentoFinal.style.color = "#000000";
	  		}

		} else if(form.campoOrigem.value == 'P_SET_FIN') {		
      		form.P_SET_FIN.value = codigoRegistro;
	  		form.nomeSetorFaturamentoFinal.value = descricaoRegistro;	  		
	  		form.nomeSetorFaturamentoFinal.style.color = "#000000";
	  	}

		form.campoOrigem.value = "";
	}	
	
	function recuperarDadosQuatroParametros(idRegistro, descricaoRegistro, codigoRegistro, tipoConsulta) {
		recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta);
	}

	function replicarRegional() {
		var form = document.forms[0];
		form.P_GR_FIN.value = form.P_GR_INI.value;

		// Se regional inicial está vazio, limpa de regional em diante
		if(form.P_GR_INI.value == "-1") {
			limpar(1);
		}
	}
	
	function replicarUnidadeNegocio() {
		var form = document.forms[0];
		form.P_UNE_FIN.value = form.P_UNE_INI.value;

		// Se regional inicial está vazio, limpa de regional em diante
		if(form.P_UNE_INI.value == "-1") {
			limpar(1);
		}
	}

	function replicarLocalidade() {
		var form = document.forms[0];
		form.P_LOC_FIN.value = form.P_LOC_INI.value;
		form.nomeLocalidadeFinal.value = form.nomeLocalidadeInicial.value;
	}

	function replicarSetorFaturamento() {
		var form = document.forms[0];
		form.P_SET_FIN.value = form.P_SET_INI.value;
		form.nomeSetorFaturamentoFinal.value = form.nomeSetorFaturamentoInicial.value;
	}
			

</script>

</head>

<body leftmargin="5" topmargin="5">

<html:form action="/gerarRelatorioContasEmAtrasoPorIdadeDividaAction"
	name="GerarRelatorioContasEmAtrasoPorIdadeDividaActionForm"
	type="org.apache.struts.action.DynaActionForm"
	method="post">
	
<input type="hidden" name="acao" value="gerarRelatorio"/>
<input type="hidden" name="relatorio" value="RelatorioContasEmAtrasoPorIdadeDaDivida.rpt"/>	
<input type="hidden" name="campoOrigem" value=""/>	
<%--  ehRelatorioBatch indica se o Relatorio será processado como uma Rotina Batch  --%>
<%--  1 - Indica que SIM, é uma Rotina Batch                                       --%>
<%--  2 - Indica que NAO, não é uma Rotina Batch e o Relatório será gerado online  --%>
<input type="hidden" name="ehRelatorioBatch" value="1"/>

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
			<td width="600" valign="top" class="centercoltext">
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
					<td class="parabg">Gerar Relat&oacute;rio de Contas em Atraso Por Idade da D&iacute;vida</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				
				<tr>
					<td colspan="2">Para gerar o relat&oacute;rio, informe os dados abaixo:<br><br></td>
				</tr>

				<tr>
					<td>
						<strong>Ger&ecirc;ncia Regional Inicial:<font color="#FF0000">*</font></strong>
					</td>

					<td>
						<strong> 
						<html:select property="P_GR_INI" 
							style="width: 230px;"
							onchange="javascript:replicarRegional();">
							
							<html:option
								value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
							</html:option>
					
							<logic:present name="colecaoGerenciaRegional" scope="request">
								<html:options collection="colecaoGerenciaRegional"
									labelProperty="nome" 
									property="id" />
							</logic:present>
						</html:select> 														
						</strong>
					</td>
				</tr>
				
				<tr>
					<td>
						<strong>Ger&ecirc;ncia Regional Final:<font color="#FF0000">*</font></strong>
					</td>

					<td>
						<strong> 
						<html:select property="P_GR_FIN" 
							style="width: 230px;">
							
							<html:option
								value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
							</html:option>
					
							<logic:present name="colecaoGerenciaRegional" scope="request">
								<html:options collection="colecaoGerenciaRegional"
									labelProperty="nome" 
									property="id" />
							</logic:present>
						</html:select> 														
						</strong>
					</td>
				</tr>
				
				<tr>
					<td>
						<strong>Unidade Negocio Inicial:<font color="#FF0000">*</font></strong>
					</td>

					<td>
						<strong> 
						<html:select property="P_UNE_INI" 
							style="width: 230px;"
							onchange="javascript:replicarUnidadeNegocio();">
							
							<html:option
								value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
							</html:option>
					
							<logic:present name="colecaoUnidadeNegocio" scope="request">
								<html:options collection="colecaoUnidadeNegocio"
									labelProperty="nomeComId" 
									property="id" />
							</logic:present>
						</html:select> 														
						</strong>
					</td>
				</tr>
				
				<tr>
					<td>
						<strong>Unidade Negocio Final:<font color="#FF0000">*</font></strong>
					</td>

					<td>
						<strong> 
						<html:select property="P_UNE_FIN" 
							style="width: 230px;">
							
							<html:option
								value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
							</html:option>
					
							<logic:present name="colecaoUnidadeNegocio" scope="request">
								<html:options collection="colecaoUnidadeNegocio"
									labelProperty="nomeComId" 
									property="id" />
							</logic:present>
						</html:select> 														
						</strong>
					</td>
				</tr>				
			
				<tr>
					<td><strong>Localidade Inicial:</strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							tabindex="1"
							property="P_LOC_INI" 
							size="3"
							onblur="javascript:replicarLocalidade();"							
							onkeypress="javascript:validaEnterComMensagem(event, 'exibirGerarRelatorioContasEmAtrasoPorIdadeDividaAction.do?objetoConsulta=1','P_LOC_INI','Localidade Inicial');"/>
							
						<a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'localidadeInicial', null, null, 275, 480, '','P_LOC_INI');">
							<img width="23" 
								height="21" 
								border="0" 
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Localidade" /></a>
								
                        <logic:empty name="GerarRelatorioContasEmAtrasoPorIdadeDividaActionForm" property="P_LOC_INI">
							<html:text property="nomeLocalidadeInicial" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />                        
						</logic:empty>
						<logic:notEmpty name="GerarRelatorioContasEmAtrasoPorIdadeDividaActionForm" property="P_LOC_INI">
						    <html:text property="nomeLocalidadeInicial" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
						<a href="javascript:limparBorracha(1);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>
				
				<tr>
					<td><strong>Localidade Final:</strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							tabindex="1"
							property="P_LOC_FIN" 
							size="3"
							onkeypress="validaEnterComMensagem(event, 'exibirGerarRelatorioContasEmAtrasoPorIdadeDividaAction.do?objetoConsulta=2','P_LOC_FIN','Localidade Final');"/>
							
						<a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'destino', null, null, 275, 480, '','P_LOC_FIN');">
							<img width="23" 
								height="21" 
								border="0" 
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Localidade" /></a>
								 
                        <logic:empty name="GerarRelatorioContasEmAtrasoPorIdadeDividaActionForm" property="P_LOC_FIN">
							<html:text property="nomeLocalidadeFinal" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />                        
						</logic:empty>
						<logic:notEmpty name="GerarRelatorioContasEmAtrasoPorIdadeDividaActionForm" property="P_LOC_FIN">
						    <html:text property="nomeLocalidadeFinal" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>						
						<a href="javascript:limparBorracha(2);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>
				
				<tr>
					<td><strong>Setor de Faturamento Inicial:</strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							tabindex="1"
							property="P_SET_INI" 
							size="3"
							onblur="javascript:replicarSetorFaturamento();"
							onkeypress="javascript:validaEnterComMensagem(event, 'exibirGerarRelatorioContasEmAtrasoPorIdadeDividaAction.do?objetoConsulta=3','P_SET_INI','Setor Comercial Inicial');"/>
							
						<a href="javascript:chamarPopup('exibirPesquisarSetorComercialAction.do', 'setorComercialOrigem', null, null , 275, 480, 'Informe Setor Comercial Inicial','P_SET_INI');">
							<img width="23" 
								height="21" 
								border="0" 
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Setor Comercial" /></a>
								
                        <logic:empty name="GerarRelatorioContasEmAtrasoPorIdadeDividaActionForm" property="P_SET_INI">
							<html:text property="nomeSetorFaturamentoInicial" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />                        
						</logic:empty>
						<logic:notEmpty name="GerarRelatorioContasEmAtrasoPorIdadeDividaActionForm" property="P_SET_INI">
						    <html:text property="nomeSetorFaturamentoInicial" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>						
						
						<a href="javascript:limparBorracha(3);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>
				
				<tr>
					<td><strong>Setor de Faturamento Final:</strong></td>
					
					<td>
						
						<html:text maxlength="3" property="P_SET_FIN"
							size="3"
							onkeypress="validaEnterDependencia(event, 'exibirGerarRelatorioContasEmAtrasoPorIdadeDividaAction.do?objetoConsulta=4', document.forms[0].P_SET_FIN, document.forms[0].P_SET_FIN.value, 'Setor Comercial Final');"
							tabindex="8"/>
								
						<a href="javascript:chamarPopup('exibirPesquisarSetorComercialAction.do', 'setorComercialDestino', null, null, 275, 480, 'Informe Setor Comercial Final','P_SET_FIN');">
							<img width="23" 
								height="21" 
								border="0" 
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Setor Comercial" /></a>
								
                        <logic:empty name="GerarRelatorioContasEmAtrasoPorIdadeDividaActionForm" property="P_SET_FIN">
							<html:text property="nomeSetorFaturamentoFinal" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />                        
						</logic:empty>
						<logic:notEmpty name="GerarRelatorioContasEmAtrasoPorIdadeDividaActionForm" property="P_SET_FIN">
						    <html:text property="nomeSetorFaturamentoFinal" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>						
						
						<a href="javascript:limparBorracha(4);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>
				
				<tr>
		          <td width="30%"><strong>Refer&ecirc;ncia do D&eacute;bito:<font color="#FF0000">*</font></strong></td>
		          <td width="70%">
		          	<html:text property="P_AM_REF" size="7"  maxlength="7" 
		          		onkeyup="javascript:mascaraAnoMes(this, event);"
		          		onblur="javascript:verificaAnoMes(this);"
		          		/>
		          	&nbsp;mm/aaaa
		          </td>
		        </tr>
		        		      				
				<tr>
					<td><strong>Tipo do Relatório:<font color="#FF0000">*</font></strong></td>
					
					<td>
						<html:radio property="P_TP_RELATORIO" value="1" tabindex="5">
							<strong> Geral </strong> 
						</html:radio>
						<html:radio property="P_TP_RELATORIO" value="2" tabindex="5">
							<strong> Particular </strong> 
						</html:radio>
						<html:radio property="P_TP_RELATORIO" value="3" tabindex="5">
							<strong> P&uacute;blico </strong> 
						</html:radio>
					</td>
				</tr>				
				<tr>
					<td><strong>Formato do Relatório:<font color="#FF0000">*</font></strong></td>
					
					<td>
						<html:radio property="formatoRelatorio" value="1" tabindex="5">
							<strong> PDF </strong> 
						</html:radio>
						<html:radio property="formatoRelatorio" value="3" tabindex="5">
							<strong> XLS </strong> 
						</html:radio>
					</td>
				</tr>		
				<tr><td><br></td></tr>
				<tr>
					<td height="24" >
			          	<input type="button" 
			          		class="bottonRightCol" 
			          		value="Limpar" 
			          		onclick="javascript:limpar(0);"/>
					</td>
				
					<td align="right">
						<input type="button" 
							name="Button" 
							class="bottonRightCol" 
							value="Gerar" 
							onClick="javascript:submeterForm()" />
					</td>
					
				</tr>
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>
<%@ include file="/jsp/util/rodape.jsp"%>	
</body>
</html:form>
</html:html>