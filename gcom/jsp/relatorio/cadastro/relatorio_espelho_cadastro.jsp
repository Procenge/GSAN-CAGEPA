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

<html:javascript staticJavascript="false"  formName="GerarRelatorioEspelhoCadastroActionForm"/>

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
		
		if((form.p_parcial[0].checked == false) && (form.p_parcial[1].checked == false)) {
			msg = "Informe a Opção Parcial ou Total";
		}

		if((form.p_indicadorOrdenacao[0].checked == false) && (form.p_indicadorOrdenacao[1].checked == false)) {
			msg += "\nInforme a Opção Ordenar por Inscrição ou Rota de Leitura";
		} 
		
		msg = acrescentarMsg(msg, verificarAtributosInicialFinal(form.p_regionalInico,form.p_regionalFinal,"Gerência Regional"));
		msg = acrescentarMsg(msg, verificarAtributosInicialFinal(form.p_grupoFaturamentoInicio,form.p_grupoFaturamentoFinal,"Grupo de Faturamento"));	
		msg = acrescentarMsg(msg, verificarAtributosInicialFinal(form.p_localidadeInicio,form.p_localidadeFinal,"Localidade"));
		msg = acrescentarMsg(msg, verificarAtributosInicialFinal(form.p_setorFaturamentoInicio,form.p_setorFaturamentoFinal,"Setor Comercial"));
		msg = acrescentarMsg(msg, verificarAtributosInicialFinal(form.p_quadraInicio,form.p_quadraFinal,"Quadra"));
		msg = acrescentarMsg(msg, verificarAtributosInicialFinal(form.P_UNE_INI,form.P_UNE_FIN,"Unidade de Negocio"));

		if( msg != ""){
			alert(msg);
			return false;
		}
		
		return true;
	}
	
	function verificarAtributosInicialFinal(campoInicio, campoFim, nomeCampo){
		
		var msg = "";

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
  	
  		form.action='exibirGerarRelatorioEspelhoCadastroAction.do';
	    form.submit();
  	}

	// Função que centraliza toda a operação de limpeza do formulário
	// O parêmetro "nivel" segue a seguinte regra:
	// 0 - Limpa todo o formulário 
	// 1 - Limpa da Regional em diante
	// 2 - Limpa do Grupo de Faturamento em diante
	// 3 - Limpa de Localidade em diante
	// 4 - Limpa do Setor de Faturamento em diante
	// 5 - Limpa da Quadra em diante 
  	function limpar(nivel){

  		var form = document.forms[0];

  		if(nivel <= 0) {
	  		form.p_parcial[0].checked = false;
	  		form.p_parcial[1].checked = false;
  		}  		

  		if(nivel <= 0) {
	  		form.p_indicadorOrdenacao[0].checked = false;
	  		form.p_indicadorOrdenacao[1].checked = false;
  		}  		
  		
  		if(nivel <= 1) {
  		    form.p_regionalInico.value = "-1";
  		    form.p_regionalFinal.value = "-1";
  		}
  		
  		if(nivel <= 1) {
  		    form.P_UNE_INI.value = "-1";
  		    form.P_UNE_FIN.value = "-1";
  		}

  		if(nivel <= 2) {
  		    form.p_grupoFaturamentoInicio.value = "-1";
  		    form.p_grupoFaturamentoFinal.value = "-1";
  		}

  		if(nivel <= 3) {
  		    form.p_localidadeInicio.value = "";
		    form.p_localidadeFinal.value = "";
		    form.nomeLocalidadeInicial.value = "";
		    form.nomeLocalidadeFinal.value = "";
  		}  

  		if(nivel <= 4) {
			form.p_setorFaturamentoInicio.value = "";
			form.p_setorFaturamentoFinal.value = "";
			form.nomeSetorFaturamentoInicial.value = "";
			form.nomeSetorFaturamentoFinal.value = "";
  		}

  		if(nivel <= 5) {
			form.p_quadraInicio.value = "";
			form.p_quadraFinal.value = "";
  		}
  	}
  	
	function limparBorracha(tipo){
		var form = document.forms[0];
		
		switch(tipo){
			case 1: // Localidade Inicial
				limpar(3);
				break;
				
			case 2: // Localidade Final
				form.p_localidadeFinal.value = "";
				form.nomeLocalidadeFinal.value = "";
				break;
				
			case 3: // Setor Inicial		     	
		     	limpar(4);
		     	break;

			case 4: // Setor Final		     	
		     	form.p_setorFaturamentoFinal.value = "";
		     	form.nomeSetorFaturamentoFinal.value = "";
		     	break;
		}
	}
	
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

		var form = document.forms[0];

		var valorAnterior;

		if(form.campoOrigem.value == 'p_localidadeInicio') {

			valorAnterior = form.p_localidadeInicio.value;
					
      		form.p_localidadeInicio.value = codigoRegistro;
	  		form.nomeLocalidadeInicial.value = descricaoRegistro;
	  		form.nomeLocalidadeInicial.style.color = "#000000";

	  		if((form.p_localidadeFinal.value == '') 
	  		  		|| (form.p_localidadeFinal.value == valorAnterior)) {		  		

	      		form.p_localidadeFinal.value = codigoRegistro;
		  		form.nomeLocalidadeFinal.value = descricaoRegistro;	 
		  		form.nomeLocalidadeFinal.style.color = "#000000";
		  	  	
	  		}	  		
	  		
		} else if(form.campoOrigem.value == 'p_localidadeFinal') {		
      		form.p_localidadeFinal.value = codigoRegistro;
	  		form.nomeLocalidadeFinal.value = descricaoRegistro;	 
	  		form.nomeLocalidadeFinal.style.color = "#000000";
	  		 		
		} else if(form.campoOrigem.value == 'p_setorFaturamentoInicio') {		

			valorAnterior = form.p_setorFaturamentoInicio.value;
			
      		form.p_setorFaturamentoInicio.value = codigoRegistro;
	  		form.nomeSetorFaturamentoInicial.value = descricaoRegistro;	  		
	  		form.nomeSetorFaturamentoInicial.style.color = "#000000";

	  		if((form.p_setorFaturamentoFinal.value == '') 
	  		  		|| (form.p_setorFaturamentoFinal.value == valorAnterior)) {

	  			form.p_setorFaturamentoFinal.value = codigoRegistro;
		  		form.nomeSetorFaturamentoFinal.value = descricaoRegistro;	  		
		  		form.nomeSetorFaturamentoFinal.style.color = "#000000";
	  		}

		} else if(form.campoOrigem.value == 'p_setorFaturamentoFinal') {		

      		form.p_setorFaturamentoFinal.value = codigoRegistro;
	  		form.nomeSetorFaturamentoFinal.value = descricaoRegistro;	  		
	  		form.nomeSetorFaturamentoFinal.style.color = "#000000";
	  		
	  	}else if(form.campoOrigem.value == 'p_quadraInicio') {		

			valorAnterior = form.p_quadraInicio.value;
			
      		form.p_quadraInicio.value = codigoRegistro;
	  	
	  		if((form.p_quadraFinal.value == '') 
	  		  		|| (form.p_quadraFinal.value == valorAnterior)) {

	  			form.p_quadraFinal.value = codigoRegistro;  		
	  		}

		} else if(form.campoOrigem.value == 'p_quadraFinal') {		
      		
      		form.p_quadraFinal.value = codigoRegistro;
	  	}

		form.campoOrigem.value = "";
	}	
	
	function recuperarDadosQuatroParametros(idRegistro, descricaoRegistro, codigoRegistro, tipoConsulta) {
		recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta);
	}

	function replicarRegional() {
		var form = document.forms[0];
		form.p_regionalFinal.value = form.p_regionalInico.value;

		// Se regional inicial está vazio, limpa de regional em diante
		if(form.p_regionalInico.value == "-1") {
			limpar(1);
		}
	}
	
	function replicarUnidadeNegocio() {
		var form = document.forms[0];
		form.P_UNE_FIN.value = form.P_UNE_INI.value;

		// Se regional inicial está vazio, limpa de Unidade Negocio em diante
		if(form.P_UNE_INI.value == "-1") {
			limpar(1);
		}
	}

	function replicarGrupoFaturamento() {
		var form = document.forms[0];
		form.p_grupoFaturamentoFinal.value = form.p_grupoFaturamentoInicio.value;

		// Se grupo faturamento inicial está vazio, limpa de grupo faturamento em diante
		if(form.p_grupoFaturamentoInicio.value == "-1") {
			limpar(2);
		}
	}
  	
	function replicarQuadra() {
		var form = document.forms[0];
		form.p_quadraFinal.value = form.p_quadraInicio.value;

		// Se quadra inicial está vazio, limpa de quadra em diante
		if(form.p_quadraInicio.value == "-1") {
			limpar(5);
		}
	}

	 function replicarLocalidade() {
		 var form = document.forms[0];
		 form.p_localidadeFinal.value = form.p_localidadeInicio.value;
		 form.nomeLocalidadeFinal.value = form.nomeLocalidadeInicial.value;
	 } 

	 function replicarSetorFaturamento() {
		 var form = document.forms[0];
		 form.p_setorFaturamentoFinal.value = form.p_setorFaturamentoInicio.value;
		 form.nomeSetorFaturamentoFinal.value = form.nomeSetorFaturamentoInicial.value;
	 } 	 

	 function limparPesquisaQuadra(inicial) {
	    var form = document.forms[0];

	    if (inicial == 'true'){

	      	form.p_quadraInicio.value = "";

			var msgQuadraInicio = document.getElementById("msgQuadraInicio");
		
			if (msgQuadraInicio != null){

				msgQuadraInicio.innerHTML = "";
			}
	    }else{

	    	form.p_quadraFinal.value = "";

			var msgQuadraFinal = document.getElementById("msgQuadraFinal");
		
			if (msgQuadraFinal != null){

				msgQuadraFinal.innerHTML = "";
			} 
		}
	  }

	 function pesquisaLupaComDependencia(url, idDependencia, nomeMSG, altura, largura, campo){

		 document.forms[0].campoOrigem.value = campo;

		 abrirPopupDependencia(url, idDependencia, nomeMSG, altura, largura);
	 }
	 	 
</script>

</head>

<body leftmargin="5" topmargin="5">

<html:form action="/gerarRelatorioEspelhoCadastroAction"
	name="GerarRelatorioEspelhoCadastroActionForm"
	type="org.apache.struts.action.DynaActionForm"
	method="post">
	
<input type="hidden" name="acao" value="gerarRelatorio"/>
<input type="hidden" name="relatorio" value="RelatorioEspelhoCadastro.rpt"/>	
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
					<td class="parabg">Gerar Relat&oacute;rio  de Espelho de Cadastro</td>
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
					<td><strong>Opção:<font color="#FF0000">*</font></strong></td>
					
					<td>
						<html:radio property="p_parcial" value="p" tabindex="5">
							<strong> Parcial </strong> 
						</html:radio>
						<html:radio property="p_parcial" value="t" tabindex="5">
							<strong> Total </strong> 
						</html:radio>
					</td>
				</tr>
				
				<tr>
					<td><strong>Ordenar Por:<font color="#FF0000">*</font></strong></td>
					
					<td>
						<html:radio property="p_indicadorOrdenacao" value="1,2,4,6,7,10,11" tabindex="5">
							<strong> Inscrição </strong> 
						</html:radio>
						<html:radio property="p_indicadorOrdenacao" value="1,2,8" tabindex="5">
							<strong> Rota de Leitura </strong> 
						</html:radio>
					</td>
				</tr>
				
				<tr>
					<td>
						<strong>Ger&ecirc;ncia Regional Inicial:<font color="#FF0000"></font></strong>
					</td>

					<td>
						<strong> 
						<html:select property="p_regionalInico" 
							style="width: 230px;"
							onchange="javascript:replicarRegional();">
							
							<html:option
								value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
							</html:option>
					
							<logic:present name="colecaoGerenciaRegional" scope="request">
								<html:options collection="colecaoGerenciaRegional"
									labelProperty="nomeComId" 
									property="id" />
							</logic:present>
						</html:select> 														
						</strong>
					</td>
				</tr>
				
				<tr>
					<td>
						<strong>Ger&ecirc;ncia Regional Final:<font color="#FF0000"></font></strong>
					</td>

					<td>
						<strong> 
						<html:select property="p_regionalFinal" 
							style="width: 230px;">
							
							<html:option
								value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
							</html:option>
					
							<logic:present name="colecaoGerenciaRegional" scope="request">
								<html:options collection="colecaoGerenciaRegional"
									labelProperty="nomeComId" 
									property="id" />
							</logic:present>
						</html:select> 														
						</strong>
					</td>
				</tr>				
			
				<tr>
					<td>
						<strong>Unidade Negocio Inicial:<font color="#FF0000"></font></strong>
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
						<strong>Unidade Negocio Final:<font color="#FF0000"></font></strong>
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
					<td>
						<strong>Grupo de Faturamento Inicial:<font color="#FF0000"></font></strong>
					</td>

					<td>
						<strong> 
						<html:select property="p_grupoFaturamentoInicio" 
							style="width: 230px;"
							onchange="javascript:replicarGrupoFaturamento();">
							
							<html:option
								value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
							</html:option>
					
							<logic:present name="colecaoFaturamentoGrupo" scope="request">
								<html:options collection="colecaoFaturamentoGrupo"
									labelProperty="descricao" 
									property="id" />
							</logic:present>
						</html:select> 														
						</strong>
					</td>
				</tr>

				<tr>
					<td>
						<strong>Grupo de Faturamento Final:<font color="#FF0000"></font></strong>
					</td>

					<td>
						<strong> 
						<html:select property="p_grupoFaturamentoFinal" 
							style="width: 230px;">
							
							<html:option
								value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
							</html:option>
					
							<logic:present name="colecaoFaturamentoGrupo" scope="request">
								<html:options collection="colecaoFaturamentoGrupo"
									labelProperty="descricao" 
									property="id" />
							</logic:present>
						</html:select> 														
						</strong>
					</td>
				</tr>
			    				
				<tr>
					<td><strong>Localidade Inicial:<font color="#FF0000"></font></strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							tabindex="1"
							property="p_localidadeInicio" 
							size="3"		
							onblur="javascript:replicarLocalidade();"					
							onkeypress="javascript:return isCampoNumerico(event);"
							onkeyup="return validaEnterComMensagem(event, 'exibirGerarRelatorioEspelhoCadastroAction.do?objetoConsulta=1','p_localidadeInicio','Localidade Inicial');"/>
							
						<a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'localidadeInicial', null, null, 275, 480, '','p_localidadeInicio');">
							<img width="23" 
								height="21" 
								border="0" 
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Localidade" /></a>
								
                        <logic:empty name="GerarRelatorioEspelhoCadastroActionForm" property="p_localidadeInicio">
							<html:text property="nomeLocalidadeInicial" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />                        
						</logic:empty>
						<logic:notEmpty name="GerarRelatorioEspelhoCadastroActionForm" property="p_localidadeInicio">
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
					<td><strong>Localidade Final:<font color="#FF0000"></font></strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							tabindex="1"
							property="p_localidadeFinal" 
							size="3"
							onkeypress="return isCampoNumerico(event);"
							onkeyup="return validaEnterComMensagem(event, 'exibirGerarRelatorioEspelhoCadastroAction.do?objetoConsulta=2','p_localidadeFinal','Localidade Final');"/>
							
						<a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'destino', null, null, 275, 480, '','p_localidadeFinal');">
							<img width="23" 
								height="21" 
								border="0" 
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Localidade" /></a>
								 
                        <logic:empty name="GerarRelatorioEspelhoCadastroActionForm" property="p_localidadeFinal">
							<html:text property="nomeLocalidadeFinal" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />                        
						</logic:empty>
						<logic:notEmpty name="GerarRelatorioEspelhoCadastroActionForm" property="p_localidadeFinal">
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
					<td><strong>Setor Comercial Inicial:<font color="#FF0000"></font></strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							tabindex="1"
							property="p_setorFaturamentoInicio" 
							size="3"
							onblur="javascript:replicarSetorFaturamento();"
							onkeypress="return isCampoNumerico(event);"
							onkeyup="return validaEnterDependencia(event, 'exibirGerarRelatorioEspelhoCadastroAction.do?objetoConsulta=3', document.forms[0].p_setorFaturamentoInicio, document.forms[0].p_localidadeInicio.value, 'Setor Faturamento Inicial');"/>
							
						<a href="javascript:pesquisaLupaComDependencia('exibirPesquisarSetorComercialAction.do?idLocalidade='+document.forms[0].p_localidadeInicio.value+'&tipo=SetorComercial',document.forms[0].p_localidadeInicio.value,'Localidade Inicial', 400, 800,'p_setorFaturamentoInicio');limparPesquisaQuadra('true');">
							<img width="23" 
								height="21" 
								border="0" 
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Setor Comercial" /></a>
								
                        <logic:empty name="GerarRelatorioEspelhoCadastroActionForm" property="p_setorFaturamentoInicio">
							<html:text property="nomeSetorFaturamentoInicial" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />                        
						</logic:empty>
						<logic:notEmpty name="GerarRelatorioEspelhoCadastroActionForm" property="p_setorFaturamentoInicio">
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
					<td><strong>Setor Comercial Final:<font color="#FF0000"></font></strong></td>
					
					<td>
						
						<html:text maxlength="3" property="p_setorFaturamentoFinal"
							size="3"
							onkeypress="return isCampoNumerico(event);"
							onkeyup="return validaEnterDependencia(event, 'exibirGerarRelatorioEspelhoCadastroAction.do?objetoConsulta=4', this, document.forms[0].p_localidadeFinal.value, 'Localidade Final');"
							tabindex="8"/>
								
						<a href="javascript:pesquisaLupaComDependencia('exibirPesquisarSetorComercialAction.do?idLocalidade='+document.forms[0].p_localidadeFinal.value+'&tipo=SetorComercial',document.forms[0].p_localidadeFinal.value,'Localidade Final', 400, 800,'p_setorFaturamentoFinal');limparPesquisaQuadra('false');">
							<img width="23" 
								height="21" 
								border="0" 
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Setor Comercial" /></a>
								
                        <logic:empty name="GerarRelatorioEspelhoCadastroActionForm" property="p_setorFaturamentoFinal">
							<html:text property="nomeSetorFaturamentoFinal" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />                        
						</logic:empty>
						<logic:notEmpty name="GerarRelatorioEspelhoCadastroActionForm" property="p_setorFaturamentoFinal">
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
					<td><strong>Quadra Inicial:<font color="#FF0000"></font></strong></td>
					
					<td>
						
						<html:text maxlength="5" 
							tabindex="1"
							property="p_quadraInicio"							
							size="5"
							onchange="javascript:replicarQuadra();" onkeypress="return isCampoNumerico(event);"/>
							<a
								href="javascript:pesquisaLupaComDependencia('exibirPesquisarQuadraAction.do?idLocalidade='+document.forms[0].p_localidadeInicio.value+'&codigoSetorComercial='+document.forms[0].p_setorFaturamentoInicio.value+'&tipo=Quadra&retornarSeteParametros=S',document.forms[0].p_setorFaturamentoInicio.value,'Setor Comercial Inicial', 400, 800,'p_quadraInicio');"
								title="Pesquisar Quadra">
								<img border="0"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0" />
							</a> 
							<logic:present name="codigoQuadraInicioNaoEncontrada" scope="request">
								<span style="color: #ff0000" id="msgQuadraInicio"><bean:write
									scope="request" name="msgQuadraInicio" /></span>
							</logic:present> 
							
							<a href="javascript:limparPesquisaQuadra('true');document.forms[0].p_quadraInicio.focus();">
								<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" title="Apagar" /> 
							</a>
					</td>
				</tr>
				
				<tr>
					<td><strong>Quadra Final:<font color="#FF0000"></font></strong></td>
					
					<td>
						
						<html:text maxlength="5" 
							tabindex="1"
							property="p_quadraFinal" 
							size="5" onkeypress="return isCampoNumerico(event);"/>
						<a
							href="javascript:pesquisaLupaComDependencia('exibirPesquisarQuadraAction.do?idLocalidade='+document.forms[0].p_localidadeFinal.value+'&codigoSetorComercial='+document.forms[0].p_setorFaturamentoFinal.value+'&tipo=Quadra&retornarSeteParametros=S',document.forms[0].p_setorFaturamentoFinal.value,'Setor Comercial Final', 400, 800,'p_quadraFinal');"
							title="Pesquisar Quadra">
							<img border="0"
							src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0" />
						</a> 
						<logic:present name="codigoQuadraFinalNaoEncontrada" scope="request">
							<span style="color: #ff0000" id="msgQuadraFinal"><bean:write
								scope="request" name="msgQuadraFinal" /></span>
						</logic:present> 
						
						<a href="javascript:limparPesquisaQuadra('false');document.forms[0].p_quadraFinal.focus();">
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
							border="0" title="Apagar" /> 
						</a>
					</td>
				</tr>
				<tr>
			       <td></td>
			       <td><font color="#FF0000">*</font>&nbsp;Campos obrigat&oacute;rios</td>
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