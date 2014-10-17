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

<html:javascript staticJavascript="false"  formName="FiltrarHistoricoManutencaoLigacaoAguaActionForm"/>

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>

<script language="JavaScript">

	function validarForm(){
		
		var form = document.forms[0];
		var msg = "";
		var valido = true;
		
		if(!existeAlgumCampoPreenchido()) {
			msg = "Informe pelo menos uma opção de seleção";
		
		} else if(!validarImovelLocalidadeSetor()){
			msg = "Informe o Imóvel ou a Localidade e o Setor Comercial";
			
		} else if((form.localidadeFinal.value != "") && (form.localidadeInicial.value == "")) {
			msg = "Informe a Localidade Inicial";
			
		} else if((form.localidadeFinal.value == "") && (form.localidadeInicial.value != "")) {
			msg = "Informe a Localidade Final";
			
		} else if(form.localidadeFinal.value < form.localidadeInicial.value) {
			msg = "Localidade Final é anterior à Localidade Inicial";
			
		} else if((form.localidadeInicial.value != form.localidadeFinal.value) 
				&& ((form.setorComercialInicial.value != "") || (form.setorComercialFinal.value != ""))) {
			msg = "Informe o Setor Comercial apenas se a Localidade Final for igual à Localidade Inicial";
			
		} else if((form.setorComercialFinal.value != "") && (form.setorComercialInicial.value == "")) {
			msg = "Informe o Setor Comercial Inicial";
			
		} else if((form.setorComercialFinal.value == "") && (form.setorComercialInicial.value != "")) {
			msg = "Informe o Setor Comercial Final";
			
		} else if(form.setorComercialFinal.value < form.setorComercialInicial.value) {
			msg = "Setor Comercial Final é anterior ao Setor Comercial Inicial";
			
		} else if((form.valorDebitoFinal.value != "") && (form.valorDebitoInicial.value == "")) {
			msg = "Informe o Valor de Débito Inicial";
			
		} else if((form.valorDebitoFinal.value == "") && (form.valorDebitoInicial.value != "")) {
			msg = "Informe o Valor de Débito Final";
			
		} else if(!validarIntervaloValorEmissao()){			
			msg = "Valor do Débito Final é menor que o Valor do Débito Inicial";
			
		} else if((form.periodoEmissaoInicial.value != "") && !verificaData(form.periodoEmissaoInicial)) {
			valido = false;
			
		} else if((form.periodoEmissaoFinal.value != "") && !verificaData(form.periodoEmissaoFinal)) {
			valido = false;
			
		} else if((form.periodoEmissaoFinal.value != "") && (form.periodoEmissaoInicial.value == "")) {
			msg = "Informe a Data de Emissão Inicial";
			
		} else if((form.periodoEmissaoFinal.value == "") && (form.periodoEmissaoInicial.value != "")) {
			msg = "Informe a Data de Emissão Final";
			
		} else if(!validarPeriodoEmissao()) {
			msg = "Data Final do Período é anterior à Data Inicial do Período";
		}
				
		if(msg != "") {
			valido = false;
			alert(msg);
		}
		
		if(valido) {	
			setDisabledLocalidadeSetor(false);
			setDisabledImovel(false);
					
			form.submit();
		}
	}
	
	function validarIntervaloValorEmissao() {
		var form = document.forms[0];		
		
		if((form.valorDebitoFinal.value != "") && (form.valorDebitoInicial.value != "")) {
			var valorFinal = obterNumerosSemVirgulaEPonto(form.valorDebitoFinal.value); 
			var valorInicial = obterNumerosSemVirgulaEPonto(form.valorDebitoInicial.value);

			if(valorFinal.length == valorInicial.length) {
				return valorFinal >= valorInicial;
			} else {
				return valorFinal.length > valorInicial.length;
			}							
		} else {
			return true;
		}
	}

	function validarImovelLocalidadeSetor() {
		var form = document.forms[0];	
		return !((form.imovel.value != "") && ((form.localidadeInicial.value != "") || (form.setorComercialInicial.value != "")));
	}
	
	function existeAlgumCampoPreenchido() {
		var form = document.forms[0];	
		
		return ((form.imovel.value != "") || 
				(form.localidadeInicial.value != "") ||
				possuiItemSelecionado(form.perfisImovel) ||
				possuiItemSelecionado(form.formasEmissao) ||
				possuiItemSelecionado(form.tiposDocumento) ||
				possuiItemSelecionado(form.tiposServico) ||
				(form.valorDebitoInicial.value != "") ||
				(form.periodoEmissaoInicial.value != ""));
	}
	
	function possuiItemSelecionado(campoSelect) {
		for(var i = 0; i < campoSelect.length; i++) {
			if((i > 0) && campoSelect.options[i].selected) {
				return true;
			}
		}
		return false;
	}
	
	function validarPeriodoEmissao() {
		var form = document.forms[0];	
		return comparaDatas(form.periodoEmissaoFinal.value,">",form.periodoEmissaoInicial.value) || 
		       comparaDatas(form.periodoEmissaoFinal.value,"=",form.periodoEmissaoInicial.value);
	}

	
	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,campo){
		
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
	  		
	  		form.setorComercialInicial.focus();
	  		
		} else if (tipoConsulta == 'localidadeDestino') {
		
      		form.localidadeFinal.value = codigoRegistro;
      		form.nomeLocalidadeFinal.value = descricaoRegistro;
	  		form.nomeLocalidadeFinal.style.color = "#000000";

	  		form.setorComercialInicial.focus();
	  		
		} else if (tipoConsulta == 'imovel') {
			
      		form.imovel.value = codigoRegistro;
      		form.inscricaoImovel.value = descricaoRegistro;
	  		form.inscricaoImovel.style.color = "#000000";

	  		form.perfisImovel.focus();
		}		
		
		verificarCamposExcludentes();
	}

	function recuperarDadosQuatroParametros(idRegistro, descricaoRegistro, codigoRegistro, tipoConsulta) {

		var form = document.forms[0];

		if (tipoConsulta == 'setorComercialOrigem') {
		  	form.setorComercialInicial.value = codigoRegistro;
		  	form.nomeSetorComercialInicial.value = descricaoRegistro;
		  	form.nomeSetorComercialInicial.style.color = "#000000"; 
		  	
		  	form.setorComercialFinal.value = codigoRegistro;
		  	form.nomeSetorComercialFinal.value = descricaoRegistro;
		  	form.nomeSetorComercialFinal.style.color = "#000000";
		  	
		}

		if (tipoConsulta == 'setorComercialDestino') {
		  	form.setorComercialFinal.value = codigoRegistro;
		  	form.nomeSetorComercialFinal.value = descricaoRegistro;
		  	form.nomeSetorComercialFinal.style.color = "#000000";
		  	
		}
		
  		form.perfisImovel.focus();
  		
  		verificarCamposExcludentes();
	}

	function troca(str,strsai,strentra) 
    { 
            while(str.indexOf(strsai)>-1) 
            { 
                    str = str.replace(strsai,strentra); 
            } 
            return str; 
    }

	function FormataMoeda(campo,tammax,teclapres,caracter) 
    { 
            if(teclapres == null || teclapres == "undefined") 
            { 
                    var tecla = -1; 
            } 
            else 
            { 
                    var tecla = teclapres.keyCode; 
            } 

            if(caracter == null || caracter == "undefined") 
            { 
                    caracter = "."; 
            } 

            vr = campo.value; 
            if(caracter != "") 
            { 
                    vr = troca(vr,caracter,""); 
            } 
            vr = troca(vr,"/",""); 
            vr = troca(vr,",",""); 
            vr = troca(vr,".",""); 

            tam = vr.length; 
            if(tecla > 0) 
            { 
                    if(tam < tammax && tecla != 8) 
                    { 
                            tam = vr.length + 1; 
                    } 

                    if(tecla == 8) 
                    { 
                            tam = tam - 1; 
                    } 
            } 
            if(tecla == -1 || tecla == 8 || tecla >= 48 && tecla <= 57 || tecla >= 96 && tecla <= 105) 
            { 
                    if(tam <= 2) 
                    { 
                            campo.value = vr; 
                    } 
                    if((tam > 2) && (tam <= 5)) 
                    { 
                            campo.value = vr.substr(0, tam - 2) + ',' + vr.substr(tam - 2, tam); 
                    } 
                    if((tam >= 6) && (tam <= 8)) 
                    { 
                            campo.value = vr.substr(0, tam - 5) + caracter + vr.substr(tam - 5, 3) + ',' + vr.substr(tam - 2, tam); 
                    } 
                    if((tam >= 9) && (tam <= 11)) 
                    { 
                            campo.value = vr.substr(0, tam - 8) + caracter + vr.substr(tam - 8, 3) + caracter + vr.substr(tam - 5, 3) + ',' + vr.substr(tam - 2, tam); 
                    } 
                    if((tam >= 12) && (tam <= 14)) 
                    { 
                            campo.value = vr.substr(0, tam - 11) + caracter + vr.substr(tam - 11, 3) + caracter + vr.substr(tam - 8, 3) + caracter + vr.substr(tam - 5, 3) + ',' + vr.substr(tam - 2, tam); 
                    } 
                    if((tam >= 15) && (tam <= 17)) 
                    { 
                            campo.value = vr.substr(0, tam - 14) + caracter + vr.substr(tam - 14, 3) + caracter + vr.substr(tam - 11, 3) + caracter + vr.substr(tam - 8, 3) + caracter + vr.substr(tam - 5, 3) + ',' + vr.substr(tam - 2, tam); 
                    } 
            } 
    }

	function maskKeyPress(objEvent) 
    { 
            var iKeyCode; 
                                     
            if(window.event) // IE 
            { 
                    iKeyCode = objEvent.keyCode; 
                    if(iKeyCode>=48 && iKeyCode<=57) return true; 
                    return false; 
            } 
            else if(e.which) // Netscape/Firefox/Opera 
            { 
                    iKeyCode = objEvent.which; 
                    if(iKeyCode>=48 && iKeyCode<=57) return true; 
                    return false; 
            } 
             
             
    }

	function replicarLocalidade(){
		var formulario = document.forms[0]; 
		
		formulario.localidadeFinal.value = formulario.localidadeInicial.value;
		formulario.setorComercialInicial.focus;
		verificarCamposExcludentes();
	}
	
	function replicarSetorComercial(){
		var formulario = document.forms[0]; 
		formulario.setorComercialFinal.value = formulario.setorComercialInicial.value;
	}
	
	function replicarValorDebito(){
		var formulario = document.forms[0]; 
		formulario.valorDebitoFinal.value = formulario.valorDebitoInicial.value;
	}

	function limparInicial(tipo){
		var form = document.forms[0];
		
		switch(tipo){
		
		case 1: //De localidade pra baixo
			
			form.nomeLocalidadeInicial.value = "";
			form.localidadeFinal.value = "";
			form.nomeLocalidadeFinal.value = "";
			form.setorComercialInicial.value = "";
			form.nomeSetorComercialInicial.value = "";
		    form.setorComercialFinal.value = "";
		    form.nomeSetorComercialFinal.value = "";
		    
		case 2: //De setor para baixo

		   form.nomeSetorComercialInicial.value = "";
		   form.setorComercialFinal.value = "";
		   form.nomeSetorComercialFinal.value = "";
		}
		
	}

	function limparBorrachaInicial(tipo){
		var form = document.forms[0];
		
		switch(tipo){
			case 1: //De localidade pra baixo

				form.localidadeInicial.value = "";
				form.nomeLocalidadeInicial.value = "";
				form.localidadeFinal.value = "";
				form.nomeLocalidadeFinal.value = "";

				form.setorComercialInicial.value = "";
		     	form.nomeSetorComercialInicial.value = "";
		     	form.setorComercialFinal.value = "";
		     	form.nomeSetorComercialFinal.value = "";
				break;
			case 2: //De setor para baixo
		     	
		     	form.setorComercialInicial.value = "";
		     	form.nomeSetorComercialInicial.value = "";
		     	form.setorComercialFinal.value = "";
		     	form.nomeSetorComercialFinal.value = "";
		}
		
		verificarCamposExcludentes();
	}

	function limparBorrachaFinal(tipo){
		var form = document.forms[0];
		
		switch(tipo){
			case 1:
				
				form.localidadeFinal.value = "";
				form.nomeLocalidadeFinal.value = "";
				break;
			case 2:
		     	
		     	form.setorComercialFinal.value = "";
		     	form.nomeSetorComercialFinal.value = "";
		}
		
		verificarCamposExcludentes();
	}

	function limpar(){

  		var form = document.forms[0];  		

  		form.localidadeInicial.value = '';
  		form.localidadeFinal.value = '';
  		form.setorComercialInicial.value = '';
  		form.setorComercialFinal.value = '';
  		form.nomeLocalidadeInicial.value = '';
  		form.nomeLocalidadeFinal.value = '';
  		form.nomeSetorComercialInicial.value = '';
  		form.nomeSetorComercialFinal.value = '';
  		form.periodoEmissaoInicial.value = '';
  		form.periodoEmissaoFinal.value = '';  		
  	}
	
	function limparImovel() {
		var form = document.forms[0];
		form.imovel.value = "";
		form.inscricaoImovel.value = "";
		verificarCamposExcludentes();
	}
	
	function verificarCamposExcludentes() {
		var form = document.forms[0];
		if(form.imovel.value == "") {
			setDisabledLocalidadeSetor(false);
		} else {
			form.localidadeInicial.value = "";
			form.localidadeFinal.value = "";
			form.nomeLocalidadeInicial.value = "";
			form.nomeLocalidadeFinal.value = "";
	
			form.setorComercialInicial.value = "";
			form.setorComercialFinal.value = "";
			form.nomeSetorComercialInicial.value = "";
			form.nomeSetorComercialFinal.value = "";

			setDisabledLocalidadeSetor(true);
		}
		
		if((form.localidadeInicial.value != "") || (form.setorComercialInicial.value != "")) {
			setDisabledImovel(true);
			form.imovel.value = "";
			form.inscricaoImovel.value = "";		

			if(form.localidadeInicial.value != form.localidadeFinal.value) {
				form.setorComercialInicial.value = "";
				form.setorComercialFinal.value = "";
				form.nomeSetorComercialInicial.value = "";
				form.nomeSetorComercialFinal.value = "";

				setDisabledSetorComercial(true);								
			} else {
				setDisabledSetorComercial(false);								
			}
		} else {
			if(form.localidadeInicial.value == "") {
				form.setorComercialInicial.value = "";
				form.setorComercialFinal.value = "";
				form.nomeSetorComercialInicial.value = "";
				form.nomeSetorComercialFinal.value = "";

				setDisabledSetorComercial(true);				
			} else {
				setDisabledSetorComercial(false);	
			}
			setDisabledImovel(false);
		}
	}
	

	function validaEnterComCamposExcludentes(tecla, caminhoActionReload, nomeCampo, descricao) {
		verificarCamposExcludentes();
		validaEnterComMensagem(tecla, caminhoActionReload, nomeCampo, descricao);				
	}		
	
	function setDisabledLocalidadeSetor(valor) {
		var form = document.forms[0];
		form.localidadeInicial.disabled = valor;
		form.localidadeFinal.disabled = valor;
		form.setorComercialInicial.disabled = valor;
		form.setorComercialFinal.disabled = valor;
	}

	function setDisabledSetorComercial(valor) {
		var form = document.forms[0];
		form.setorComercialInicial.disabled = valor;
		form.setorComercialFinal.disabled = valor;
	}

	function setDisabledImovel(valor) {
		var form = document.forms[0];
		form.imovel.disabled = valor;
	}
	
	/* Replica Data de Emissão */
	function replicaDataEmissao() {
		var form = document.forms[0];
		form.periodoEmissaoFinal.value = form.periodoEmissaoInicial.value
	}
	
</script>

</head>

<body leftmargin="5" topmargin="5" onload="javascript:verificarCamposExcludentes();">

<html:form action="/consultarHistoricoManutencaoLigacaoAguaAction.do"
	name="FiltrarHistoricoManutencaoLigacaoAguaActionForm"
	type="gcom.gui.atendimentopublico.ligacaoagua.FiltrarHistoricoManutencaoLigacaoAguaActionForm"
	method="post">
	
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
					<td class="parabg">Consultar Hist&oacute;rico da Manuten&ccedil;&atilde;o da Liga&ccedil;&atilde;o de &Aacute;gua</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				
				<tr>
					<td colspan="2">Para consultar o hist&oacute;rico de manuten&ccedil;&atilde;o de liga&ccedil;&atilde;o de &aacute;gua, informe os dados abaixo:</td>
				</tr>
					
					
				<tr>
					<td width="30%"><strong>Imóvel:</strong></td>
					<td>
						<html:text property="imovel" size="9" maxlength="9" 
							onkeyup="validaEnterComCamposExcludentes(event, 'exibirFiltrarHistoricoManutencaoLigacaoAguaAction.do', 'imovel', 'Imóvel');verificaNumeroInteiro(this);"
							onkeypress="document.forms[0].inscricaoImovel.value='';" />
						<a href="javascript:chamarPopup('exibirPesquisarImovelAction.do', 'imovel', null, null, 275, 480, '',document.forms[0].imovel);">	
							<img width="23" height="21" src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								style="cursor:hand;" alt="Pesquisar" border="0"/></a> 
						<logic:present name="matriculaEncontrada" scope="request">
							<html:text property="inscricaoImovel" size="35" maxlength="35"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present>
						<logic:notPresent name="matriculaEncontrada" scope="request">
							<html:text property="inscricaoImovel" size="35" maxlength="35"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:notPresent> 
						<a href="javascript:limparImovel();">
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"border="0" title="Apagar" />
						</a>
					</td>
				</tr>					
					
              <!-- Localidade Inicial e Final -->
					
				<tr>
					<td><strong>Localidade Inicial:</strong></td>
					
					<td>
						
						<html:text maxlength="3" 							
							property="localidadeInicial" 
							size="3"
							onblur="javascript:replicarLocalidade();" 
							onkeypress="javascript:limparInicial(1);"
							onkeyup="javascript:validaEnterComCamposExcludentes(event,'exibirFiltrarHistoricoManutencaoLigacaoAguaAction.do?objetoConsulta=1','localidadeInicial','Localidade Inicial'); return isCampoNumerico(event);verificaNumeroInteiro(this);"/>
							
						<a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'origem', null, null, 275, 480, '',document.forms[0].localidadeInicial);limparInicial(1);">
							<img width="23" 
								height="21" 
								border="0" 
								style="cursor:hand;"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Localidade" /></a>
								

						<logic:present name="localidadeInicialEncontrada" scope="request">
							<html:text property="nomeLocalidadeInicial" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 

						<logic:notPresent name="localidadeInicialEncontrada" scope="request">
							<html:text property="nomeLocalidadeInicial" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>

						
						<a href="javascript:limparBorrachaInicial(1);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>
				
				<tr>
					<td><strong>Localidade Final:</strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							
							property="localidadeFinal" 
							size="3"
							onkeypress="javascript:verificarCamposExcludentes();"
							onkeyup="validaEnterComCamposExcludentes(event, 'exibirFiltrarHistoricoManutencaoLigacaoAguaAction.do?objetoConsulta=3','localidadeFinal','Localidade Final'); return isCampoNumerico(event);verificaNumeroInteiro(this);"/>
							
						<a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'destino', null, null, 275, 480, '',document.forms[0].localidadeFinal);limparDestino(1);">
							<img width="23" 
								height="21" 
								border="0" 
								style="cursor:hand;"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Localidade" /></a>
								 

						<logic:present name="localidadeFinalEncontrada" scope="request">
							<html:text property="nomeLocalidadeFinal" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 

						<logic:notPresent name="localidadeFinalEncontrada" scope="request">
							<html:text property="nomeLocalidadeFinal" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>

						
						<a href="javascript:limparBorrachaFinal(1);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>

              <!-- Setor Comercial Inicial e Final -->
				
				<tr>
					<td><strong>Setor Comercial Inicial:</strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							
							property="setorComercialInicial" 
							size="3"
							onblur="javascript:replicarSetorComercial();"
							onkeypress="limparInicial(2);"
							onkeyup="javascript:validaEnterComCamposExcludentes(event, 'exibirFiltrarHistoricoManutencaoLigacaoAguaAction.do?objetoConsulta=2','setorComercialInicial','Setor Comercial Inicial'); return isCampoNumerico(event);verificaNumeroInteiro(this);"/>
							
						<a href="javascript:chamarPopup('exibirPesquisarSetorComercialAction.do', 'setorComercialOrigem', 'localidadeInicial', document.forms[0].localidadeInicial.value , 275, 480, 'Informe Localidade Inicial',document.forms[0].setorComercialInicial);
						         limparInicial(2);">
							<img width="23" 
								height="21" 
								border="0" 
								style="cursor:hand;"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Setor Comercial" /></a>
								

						<logic:present name="setorComercialInicialEncontrado" scope="request">
							<html:text property="nomeSetorComercialInicial" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 

						<logic:notPresent name="setorComercialInicialEncontrado" scope="request">
							<html:text property="nomeSetorComercialInicial" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>
						
						<a href="javascript:limparBorrachaInicial(2);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>
				
				<tr>
					<td><strong>Setor Comercial Final:</strong></td>
					
					<td>
						
						<html:text maxlength="3" property="setorComercialFinal"
							size="3"							
							onkeyup="validaEnterComCamposExcludentes(event, 'exibirFiltrarHistoricoManutencaoLigacaoAguaAction.do?objetoConsulta=4','setorComercialFinal','Setor Comercial Final'); return isCampoNumerico(event);verificaNumeroInteiro(this);"/>
								
						<a href="javascript:chamarPopup('exibirPesquisarSetorComercialAction.do', 'setorComercialDestino', 'idLocalidade', document.forms[0].localidadeInicial.value, 275, 480, 'Informe Localidade Final',document.forms[0].setorComercialFinal);
							        limparDestino(2);">
							<img width="23" 
								height="21" 
								border="0" 
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Setor Comercial" /></a>
								

						<logic:present name="setorComercialFinalEncontrado" scope="request">
							<html:text property="nomeSetorComercialFinal" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 

						<logic:notPresent name="setorComercialFinalEncontrado" scope="request">
							<html:text property="nomeSetorComercialFinal" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>
						
						<a href="javascript:limparBorrachaFinal(2);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>
				
              <!-- Perfis do Imóvel -->

              <tr> 
				<td width="30%"><strong>Perfil do Im&oacute;vel:</strong></td>
                <td colspan="2"><span class="style2"><strong>
					<html:select property="perfisImovel" style="width: 260px; height:100px;" multiple="true" tabindex="12">
						<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present name="colecaoPerfisImovel" scope="session">
							<html:options collection="colecaoPerfisImovel" labelProperty="descricao" property="id" />
						</logic:present>
					</html:select>                
                  </strong></span>
                </td>
              </tr>              
              
              <!-- Formas de emissão -->

              <tr> 
				<td width="30%"><strong>Forma de Emiss&atilde;o:</strong></td>
                <td colspan="2"><span class="style2"><strong>
					<html:select property="formasEmissao" style="width: 260px; height:100px;" multiple="true" tabindex="12">
						<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present name="colecaoFormasEmissao" scope="session">
							<html:options collection="colecaoFormasEmissao" labelProperty="descricaoDocumentoEmissaoForma" property="id" />
						</logic:present>
					</html:select>                
                  </strong></span>
                </td>
              </tr>              
              				
              <!-- Tipos de documento -->

              <tr> 
				<td width="30%"><strong>Tipo de Documento:</strong></td>
                <td colspan="2"><span class="style2"><strong>
					<html:select property="tiposDocumento" style="width: 260px; height:100px;" multiple="true" tabindex="12">
						<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present name="colecaoTiposDocumento" scope="session">
							<html:options collection="colecaoTiposDocumento" labelProperty="descricaoDocumentoTipo" property="id" />
						</logic:present>
					</html:select>                
                  </strong></span>
                </td>
              </tr>              
              				
              <!-- Tipos de serviço -->

              <tr> 
				<td width="30%"><strong>Tipo de Serviço:</strong></td>
                <td colspan="2"><span class="style2"><strong>
					<html:select property="tiposServico" style="width: 260px; height:100px;" multiple="true" tabindex="12">
						<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present name="colecaoTiposServico" scope="session">
							<html:options collection="colecaoTiposServico" labelProperty="descricao" property="id" />
						</logic:present>
					</html:select>                
                  </strong></span>
                </td>
              </tr>              
              				
			  <tr>
				<td><strong>Valor do D&eacute;bito:</strong></td>						
				<td><span class="style2"><strong>					
					<html:text maxlength="13"
						property="valorDebitoInicial" 
						size="13" 
						onkeydown="FormataMoeda(this,10,event)" 
						onkeypress="return maskKeyPress(event)" 
						onkeyup="replicarValorDebito();"
					/>
					a
					<html:text maxlength="13"
						property="valorDebitoFinal" 
						size="13" 
						onkeydown="FormataMoeda(this,10,event)" 
						onkeypress="return maskKeyPress(event)" 
					/></strong></span>					
				</td>
			  </tr>
              				
              <!-- Período de Emissão -->

              <tr> 
				<td width="30%"><strong>Per&iacute;odo de Emissão:</strong></td>
              
                <td colspan="4"><span class="style2"><strong>                 	    
						<html:text property="periodoEmissaoInicial" size="11" maxlength="10" tabindex="22" onkeyup="mascaraData(this, event);replicaDataEmissao();"/>
						<a href="javascript:abrirCalendario('FiltrarHistoricoManutencaoLigacaoAguaActionForm', 'periodoEmissaoInicial');">
						<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="16" height="15" border="0" alt="Exibir Calendário" /></a>
						a 
						<html:text property="periodoEmissaoFinal" size="11" maxlength="10" tabindex="23" onkeyup="mascaraData(this, event)"/>
						<a href="javascript:abrirCalendario('FiltrarHistoricoManutencaoLigacaoAguaActionForm', 'periodoEmissaoFinal');">
						<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="16" height="15" border="0" alt="Exibir Calendário" /></a>
					</strong>(dd/mm/aaaa)</span>
				</td>
              </tr>              
              
              <!-- Ações -->
				
				<tr>
					<td height="24" >
			          	<input type="button" 
			          		class="bottonRightCol" 
			          		value="Limpar" 
			          		onclick="javascript:limpar()"/>
					</td>
				
					<td align="right">
						<input type="button" 
							name="Button" 
							class="bottonRightCol" 
							value="Consultar" 
							onClick="javascript:validarForm()" />
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
