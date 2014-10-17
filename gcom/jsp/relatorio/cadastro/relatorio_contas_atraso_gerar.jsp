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

<html:javascript staticJavascript="false"  formName="GerarRelatorioContasAtrasoDynaActionForm"/>

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>

<script language="JavaScript">

	function validarForm(){
		
		var form = document.forms[0];
		if(campoObrigatorio()){
			if(document.forms[0].ehRelatorioBatch.value == 2) {
			    document.forms[0].target = "_blank";
			}
			document.forms[0].submit();
			document.forms[0].target = "";
		}
	}

	function campoObrigatorio(){       
		var form = document.forms[0];
		var msg = "";

		if(!form.TIPO_RELATORIO[0].checked && !form.TIPO_RELATORIO[1].checked){
			msg = "Informe o Tipo de Relatório";
			//form.TIPO_RELATORIO.focus();
			alert(msg);
			return false;
		}
		
		if((form.p_indicadorOrdenacao[0].checked == false) && (form.p_indicadorOrdenacao[1].checked == false)) {
			msg += "Informe a Opção Ordenar por Inscrição ou Lote";
			alert(msg);
			return false;
		} 
				
		if(form.REF_I.value.length < 1 || form.REF_F.value.length < 1){
			msg = "Informe a Referência Débito";
			form.REF_I.focus();
			alert(msg);
			return false;
		}else{
			if (comparaMesAno(form.REF_F.value,">=",form.REF_I.value) == false){
				alert("Referência final maior que referência inicial.");
				return false;
			}
		}
		
		return true;
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
      		
      		form.LOCA_I.value = codigoRegistro;
	  		form.nomeLocalidadeInicial.value = descricaoRegistro;
	  		
	  		form.LOCA_F.value = codigoRegistro;
      		form.nomeLocalidadeFinal.value = descricaoRegistro;
      		
	  		form.nomeLocalidadeInicial.style.color = "#000000";
	  		form.nomeLocalidadeFinal.style.color = "#000000";
	  		
	  		form.SETOR_I.focus();
		}

		if (tipoConsulta == 'localidadeDestino') {
		
      		form.LOCA_F.value = codigoRegistro;
      		form.nomeLocalidadeFinal.value = descricaoRegistro;
	  		form.nomeLocalidadeFinal.style.color = "#000000";

	  		form.SETOR_F.focus();
		}
		
		if (tipoConsulta == 'cliente') {
			
			var valorAnterior;
			
			if(form.campoOrigem.value == 'RESP_I') {
			
				valorAnterior = form.RESP_I.value;
				
				form.RESP_I.value = codigoRegistro;
		  		form.nomeResponsavelInicial.value = descricaoRegistro;
		  		form.nomeResponsavelInicial.style.color = "#000000";
				
		  		if((form.RESP_F.value == '') 
		  		  		|| (form.RESP_F.value == valorAnterior)) {
		      		form.RESP_F.value = codigoRegistro;
			  		form.nomeResponsavelFinal.value = descricaoRegistro;	 
			  		form.nomeResponsavelFinal.style.color = "#000000";
			  	  	
		  		}
		  		
			}  else if(form.campoOrigem.value == 'RESP_F') {
				form.RESP_F.value = codigoRegistro;
		  		form.nomeResponsavelFinal.value = descricaoRegistro;
		  		form.nomeResponsavelFinal.style.color = "#000000";	  		 		
			}

			form.campoOrigem.value = "";			
		}
	}

	function recuperarDadosQuatroParametros(idRegistro, descricaoRegistro, codigoRegistro, tipoConsulta) {

		var form = document.forms[0];

		if (tipoConsulta == 'setorComercialOrigem') {
		  	form.SETOR_I.value = codigoRegistro;
		  	form.nomeSetorComercialInicial.value = descricaoRegistro;
		  	form.nomeSetorComercialInicial.style.color = "#000000"; 
		  	
		  	form.SETOR_F.value = codigoRegistro;
		  	form.nomeSetorComercialFinal.value = descricaoRegistro;
		  	form.nomeSetorComercialFinal.style.color = "#000000";
		  	
		}

		if (tipoConsulta == 'setorComercialDestino') {
		  	form.SETOR_F.value = codigoRegistro;
		  	form.nomeSetorComercialFinal.value = descricaoRegistro;
		  	form.nomeSetorComercialFinal.style.color = "#000000";
		}
	}

	function limparClienteInicialForm() {
		var form = document.forms[0];
		form.RESP_I.value = "";
		form.nomeResponsavelInicial.value = "";	
	}
	
	function limparClienteFinalForm() {
		var form = document.forms[0];
		form.RESP_F.value = "";
		form.nomeResponsavelFinal.value = "";	
	}

	function limparClienteInicialTecla() {
		var form = document.forms[0];
	
		form.RESP_I.value = "";
	}
	
	function limparClienteFinalTecla() {
		var form = document.forms[0];
	
		form.RESP_F.value = "";
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
		
		formulario.LOCA_F.value = formulario.LOCA_I.value;
		formulario.SETOR_I.focus;
	}
	
	function replicarResponsavel(){
		var formulario = document.forms[0]; 
		
		formulario.RESP_F.value = formulario.RESP_I.value;
		formulario.RESP_I.focus;
	}

	function replicarSetorComercial(){
		var formulario = document.forms[0]; 
		formulario.SETOR_F.value = formulario.SETOR_I.value;
		formulario.QUAD_I.focus;
	}
	
	function replicarQuadra(){
		var formulario = document.forms[0]; 
		formulario.QUAD_F.value = formulario.QUAD_I.value;
		formulario.ROTA_I.focus;
	}

	function replicarRota(){
		var formulario = document.forms[0]; 
		formulario.ROTA_F.value = formulario.ROTA_I.value;
		formulario.LOTE_I.focus;
	} 

	function replicarLote(){
		var formulario = document.forms[0]; 
		formulario.LOTE_F.value = formulario.LOTE_I.value;
		formulario.SLOTE_I.focus;
	} 

	function replicarSubLote(){
		var formulario = document.forms[0]; 
		formulario.SLOTE_F.value = formulario.SLOTE_I.value;
		formulario.CAT_I.focus;
	}

	function replicarReferencia(){
		var formulario = document.forms[0]; 
		formulario.REF_F.value = formulario.REF_I.value;
		formulario.REF_I.focus;
	}

	function limparOrigem(tipo){
		var form = document.forms[0];
		
		switch(tipo){
		
		case 1: //De localidade pra baixo
			
			form.nomeLocalidadeInicial.value = "";
			form.LOCA_F.value = "";
			form.nomeLocalidadeFinal.value = "";
			form.SETOR_I.value = "";
			form.nomeSetorComercialInicial.value = "";
		    form.SETOR_F.value = "";
		    form.nomeSetorComercialFinal.value = "";
		    
		case 2: //De setor para baixo

		   form.nomeSetorComercialInicial.value = "";
		   form.SETOR_F.value = "";
		   form.nomeSetorComercialFinal.value = "";
		}
	}

	function limparBorrachaOrigem(tipo){
		var form = document.forms[0];
		
		switch(tipo){
			case 1: //De localidara pra baixo

				form.LOCA_I.value = "";
				form.nomeLocalidadeInicial.value = "";
				form.LOCA_F.value = "";
				form.nomeLocalidadeFinal.value = "";
				break;
			case 2: //De setor para baixo
		     	
		     	form.SETOR_I.value = "";
		     	form.nomeSetorComercialInicial.value = "";
		     	form.SETOR_F.value = "";
		     	form.nomeSetorComercialFinal.value = "";
		}
	}

	function limpar(){

  		var form = document.forms[0];
  		
  		form.RESP_I.value = '';
  		form.RESP_F.value = '';
  		form.GRAND_USU.value = '';
  		form.IND_PARCEL_ATRASO.value = '';
  		form.QTD_CONTAS.value = '';
  		form.VAL_DEBITO.value = '';
  		form.SIT_AGUA.value = "-1";
  		form.SIT_ESGOTO.value = "-1";
  		form.TIPO_RELATORIO.value = '';
  		form.REG_I.value = "-1";
  		form.REG_F.value = "-1";
  		form.UNE_I.value = "-1";
  		form.UNE_F.value = "-1";
  		form.GRUPO_I.value = "-1";
  		form.GRUPO_F.value = "-1";
  		form.LOCA_I.value = '';
  		form.LOCA_F.value = '';
  		form.QUAD_I.value = '';
  		form.QUAD_F.value = '';
  		form.SETOR_I.value = '';
  		form.SETOR_F.value = '';
  		form.ROTA_I.value = '';
  		form.ROTA_F.value = '';
  		form.LOTE_I.value = '';
  		form.LOTE_F.value = '';
  		form.SLOTE_I.value = '';
  		form.SLOTE_F.value = '';
  		form.CAT_I.value = "-1";
  		form.CAT_F.value = "-1";
  		form.SCAT_I.value = "-1";
  		form.SCAT_F.value = "-1";
  		form.REF_I.value = '';
  		form.REF_F.value = '';
  		
  		form.nomeLocalidadeInicial.value = '';
  		form.nomeLocalidadeFinal.value = '';
  		form.nomeSetorComercialInicial.value = '';
  		form.nomeSetorComercialFinal.value = '';
  		form.nomeResponsavelInicial.value = '';
  		form.nomeResponsavelFinal.value = '';
  		
  	}
	
	function disabilitarHabilitarUnidadeNegocio() {
		var form = document.forms[0];
		if((form.REG_I.value == "-1")) {
			form.UNE_I.disabled = false;
			form.UNE_F.disabled = false;
		} else {
			form.UNE_I.value = "-1"
			form.UNE_F.value = "-1"

			form.UNE_I.disabled = true;
			form.UNE_F.disabled = true;
		}
	}

	function disabilitarHabilitarGerenciaRegional() {
		var form = document.forms[0];
		if((form.UNE_I.value == "-1")) {
			form.REG_I.disabled = false;
			form.REG_F.disabled = false;
		} else {
			form.REG_I.value = "-1"
			form.REG_F.value = "-1"

			form.REG_I.disabled = true;
			form.REG_F.disabled = true;
		}
	}	
	
	function campoGrupoFaturamentoDependentePreenchido(evento){
		var form = document.forms[0];
		if(evento.value != "" && form.GRUPO_I.value == "-1") {
			form.ROTA_I.value = "";
			form.ROTA_F.value = "";
			alert("Informe o Grupo Faturamento.");
		}
	}
	  	
	function campoSubCategoriaDependentePreenchido(evento){
		var form = document.forms[0];
		if(evento.value != "-1" && form.CAT_I.value == "-1") {
			form.SCAT_I.value = "-1";
			form.SCAT_F.value = "-1";
			alert("Informe a Categoria.");
		}
	}
	
	function submitDynaReset(){
		var form = document.forms[0];
		
		if(form.RESP_I.value == 0){
			form.RESP_I.value = '';
		}
		if(form.RESP_F.value == 0){
	  		form.RESP_F.value = '';
		}
		if(form.QTD_CONTAS.value == 0){
			form.QTD_CONTAS.value = '';
		}
		if(form.LOCA_I.value == 0){
			form.LOCA_I.value = '';	
		}
		if(form.LOCA_F.value == 0){
	  		form.LOCA_F.value = '';
		}
		if(form.QUAD_I.value == 0){
	  		form.QUAD_I.value = '';
		}
		if(form.QUAD_F.value == 0){
			form.QUAD_F.value = '';			
		}
		if(form.SETOR_I.value == 0){
			form.SETOR_I.value = '';	
		}
		if(form.SETOR_F.value == 0){
			form.SETOR_F.value = '';	
		}
		if(form.ROTA_I.value == 0){
			form.ROTA_I.value = '';			
		}
		if(form.ROTA_F.value == 0){
			form.ROTA_F.value = '';	
		}
		if(form.LOTE_I.value == 0){
			form.LOTE_I.value = '';	
		}
		if(form.LOTE_F.value == 0){
			form.LOTE_F.value = '';			
		}
		if(form.SLOTE_I.value == 0){
	  		form.SLOTE_I.value = '';
		}
		if(form.SLOTE_F.value == 0){
			form.SLOTE_F.value = '';			
		}
	}
	  	
</script>

</head>

<body leftmargin="5" topmargin="5" onload="javascript:disabilitarHabilitarUnidadeNegocio(); disabilitarHabilitarGerenciaRegional(); submitDynaReset();">

<html:form action="/gerarRelatorioContasAtrasoAction.do"
	name="GerarRelatorioContasAtrasoDynaActionForm"
	type="org.apache.struts.action.DynaActionForm"
	method="post">

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

	<input type="hidden" name="campoOrigem" value=""/>
	<input type="hidden" name="acao" value="gerarRelatorio"/>
	<input type="hidden" name="relatorio" value="RelatorioContasEmAtraso.rpt"/>
	
	
	<%--  ehRelatorioBatch indica se o Relatorio será processado como uma Rotina Batch  --%>
	<%--  1 - Indica que SIM, é uma Rotina Batch                                       --%>
	<%--  2 - Indica que NAO, não é uma Rotina Batch e o Relatório será gerado online  --%>
	<input type="hidden" name="ehRelatorioBatch" value="1"/>

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
					<td class="parabg">Gerar Relat&oacute;rio de Contas em Atraso</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				
				<tr>
					<td colspan="2">Para gerar o relat&oacute;rio, informe os dados abaixo:</td>
				</tr>
				
				
				<!-- Mudança de layout de página. -->
				<tr>
					<td><strong>Tipo do relatório: <font color="#FF0000">*</font></strong></td>
					<td>
						<span class="style2">
							<html:radio property="TIPO_RELATORIO" value="A" /><strong>Analítico</strong>
							<html:radio property="TIPO_RELATORIO" value="S" /><strong>Sintético</strong>
	 					</span>
	 				</td>
				</tr>
				
				<tr>
					<td><strong>Ordenar Por:<font color="#FF0000">*</font></strong></td>
					<td>
						<html:radio property="p_indicadorOrdenacao" value="40,3,4,1,2" tabindex="5">
							<strong> Inscrição </strong> 
						</html:radio>
						<html:radio property="p_indicadorOrdenacao" value="1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,24,25,27,28,29,30,31,32,33,34" tabindex="5">
							<strong> Lote </strong> 
						</html:radio>
					</td>
				</tr>
				
				
				<tr>
					<td><strong>Refer&ecirc;ncia do débito Inicial: <font color="#FF0000">*</font></strong></td>					
					<td >						
						<html:text maxlength="7"
							
							property="REF_I" 
							size="7" 
							onkeypress="javascript:return isCampoNumerico(event); "
							onkeyup="javascript:mascaraAnoMes(this, event);"
							onblur="javascript:replicarReferencia();verificaAnoMes(this);"
							/>
						<strong>a</strong>
						<html:text maxlength="7"
							
							property="REF_F" 
							size="7"
							onkeypress="javascript:return isCampoNumerico(event); "
							onkeyup="javascript:mascaraAnoMes(this, event);"
							onblur="javascript:verificaAnoMes(this);"
							/>(mm/aaaa)
					</td>
				</tr>
				
				<tr>
				    <td><strong>Cliente Responsável Inicial:</strong></td>
				    <td>
						<html:text property="RESP_I" size="9" maxlength="9"
							onkeypress="javascript:validaEnterComMensagem(event,'exibirGerarRelatorioContasAtrasoAction.do?objetoConsulta=5','RESP_I','Cliente Responsável');
										return isCampoNumerico(event);" />
						<a href="javascript:chamarPopup('exibirPesquisarClienteAction.do', 'cliente', null, null, 275, 480, '',document.forms[0].RESP_I);">	
							<img width="23" height="21" src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								style="cursor:hand;" alt="Pesquisar" border="0"/></a> 
						<logic:present name="clienteInexistente" scope="request">
							<html:text property="nomeResponsavelInicial" size="35" maxlength="35"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present>
						<logic:notPresent name="clienteInexistente" scope="request">
							<html:text property="nomeResponsavelInicial" size="35" maxlength="35"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent> 
						<a href="javascript:limparClienteInicialForm();">
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"border="0" title="Apagar" />
						</a>
					</td>
			  	</tr>
			  	
			  	<tr>
				    <td><strong>Cliente Responsável Final:</strong></td>
				    <td>
						<html:text property="RESP_F" size="9" maxlength="9"
							onkeypress="javascript:validaEnterComMensagem(event,'exibirGerarRelatorioContasAtrasoAction.do?objetoConsulta=6','RESP_F','Cliente Responsável');
										return isCampoNumerico(event);" />
						<a href="javascript:chamarPopup('exibirPesquisarClienteAction.do', 'cliente', null, null, 275, 480, '',document.forms[0].RESP_F);">	
							<img width="23" height="21" src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								style="cursor:hand;" alt="Pesquisar" border="0"/></a> 
						<logic:present name="clienteInexistente" scope="request">
							<html:text property="nomeResponsavelFinal" size="35" maxlength="35"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present>
						<logic:notPresent name="clienteInexistente" scope="request">
							<html:text property="nomeResponsavelFinal" size="35" maxlength="35"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent> 
						<a href="javascript:limparClienteFinalForm();">
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"border="0" title="Apagar" />
						</a>
					</td>
			  	</tr>
				
				<tr>
					<td><strong>Grande Usuário:</strong></td>
					<td>
						<span class="style2">
							<html:radio property="GRAND_USU" value="S" /><strong>Sim</strong>
							<html:radio property="GRAND_USU" value="N" /><strong>Não</strong>
	 					</span>
	 				</td>
				</tr>
				
				<tr>
					<td ><strong>Indicador de Parcelamento:</strong></td>
					<td>
						<span class="style2">
							<html:radio property="IND_PARCEL_ATRASO" value="S" /><strong>Sim</strong>
							<html:radio property="IND_PARCEL_ATRASO" value="N" /><strong>Não</strong>
	 					</span>
	 				</td>
				</tr>
				
				<tr>
					<td><strong>Qtd. de Contas : </strong></td>					
					<td>						
						<html:text maxlength="3" property="QTD_CONTAS" size="3" onkeypress="return isCampoNumerico(event); "/>
					</td>
				</tr>
				
				<tr>
					<td><strong>Valor do débito: </strong></td>						
					<td>						
						<html:text maxlength="13" property="VAL_DEBITO" size="13" onkeydown="FormataMoeda(this,10,event)" onkeypress="return maskKeyPress(event)" />
						
					</td>
				</tr>
				
				<tr> 
                	<td>
                		<strong>Situa&ccedil;&atilde;o  Liga&ccedil;&atilde;o de &Aacute;gua</strong>
                    </td>
                    
                    <td><strong> 
						<html:select property="SIT_AGUA" style="width:180px">
							<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<logic:present name="colecaoSituacaoLigacaoAgua" scope="request">
							<html:options collection="colecaoSituacaoLigacaoAgua" 
									labelProperty="descricao" 
									property="id"/>
							</logic:present>
						</html:select> </strong>
					</td>
                	
              	</tr>
              	
              	<tr> 
                	<td>
                		<strong>Situa&ccedil;&atilde;o  Liga&ccedil;&atilde;o de Esgoto</strong>
                    </td>
                    
                    <td><strong> 
						<html:select property="SIT_ESGOTO" style="width:180px">
							<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<logic:present name="colecaoSituacaoLigacaoEsgoto" scope="request">
							<html:options collection="colecaoSituacaoLigacaoEsgoto" 
									labelProperty="descricao" 
									property="id"/>
							</logic:present>
						</html:select> </strong>
					</td>
                	
              	</tr>				
              	   				
				<tr>
					<td>
						<strong>Ger&ecirc;ncia Regional Inicial:</strong>
					</td>

					<td>
						<strong> 
						<html:select property="REG_I" 
							style="width: 230px;" 
							onchange="javascript:disabilitarHabilitarUnidadeNegocio();"
							onblur="replicarCampo(REG_F,REG_I);">
							
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
						<strong>Ger&ecirc;ncia Regional Final:</strong>
					</td>

					<td>
						<strong> 
						<html:select property="REG_F" 
							style="width: 230px;" 
							onchange="javascript:disabilitarHabilitarUnidadeNegocio(); ">
							
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
						<strong>Unidade Negócio Inicial:</strong>
					</td>

					<td>
						<strong> 
						<html:select property="UNE_I" 
							style="width: 230px;" 
							onchange="javascript:disabilitarHabilitarGerenciaRegional();"
							onblur="replicarCampo(UNE_F,UNE_I);">
							
							<html:option
								value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
							</html:option>
					
							<logic:present name="colecaoUnidadeNegocio" scope="request">
								<html:options collection="colecaoUnidadeNegocio"
									labelProperty="nome" 
									property="id" />
							</logic:present>
						</html:select> 														
						</strong>
					</td>
				</tr>
				
				<tr>
					<td>
						<strong>Unidade Negócio Final:</strong>
					</td>

					<td>
						<strong> 
						<html:select property="UNE_F" 
							style="width: 230px;" 
							onchange="javascript:disabilitarHabilitarGerenciaRegional();">
							
							<html:option
								value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
							</html:option>
					
							<logic:present name="colecaoUnidadeNegocio" scope="request">
								<html:options collection="colecaoUnidadeNegocio"
									labelProperty="nome" 
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
							
							property="LOCA_I" 
							size="3"
							onblur="javascript:replicarLocalidade();" 
							onkeyup="javascript:limparOrigem(1);"
							onkeypress="javascript:validaEnterComMensagem(event,'exibirGerarRelatorioContasAtrasoAction.do?objetoConsulta=1','LOCA_I','Localidade Inicial'); return isCampoNumerico(event);"/>
							
						<a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'origem', null, null, 275, 480, '',document.forms[0].LOCA_I);limparOrigem(1);">
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

						
						<a href="javascript:limparBorrachaOrigem(1);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>
				
				<tr>
					<td><strong>Localidade Final:</strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							
							property="LOCA_F" 
							size="3"
							onkeypress="validaEnterComMensagem(event, 'exibirGerarRelatorioContasAtrasoAction.do?objetoConsulta=3','LOCA_F','Localidade Final'); return isCampoNumerico(event);"/>
							
						<a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'destino', null, null, 275, 480, '',document.forms[0].LOCA_F);limparDestino(1);">
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

						
						<a href="javascript:limparBorrachaDestino(1);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>
				
				<tr>
					<td><strong>Setor Comercial Inicial:</strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							
							property="SETOR_I" 
							size="3"
							onblur="javascript:replicarSetorComercial();"
							onkeyup="limparOrigem(2);"
							onkeypress="javascript:validaEnterComMensagem(event, 'exibirGerarRelatorioContasAtrasoAction.do?objetoConsulta=2','SETOR_I','Setor Comercial Inicial'); return isCampoNumerico(event);"/>
							
						<a href="javascript:chamarPopup('exibirPesquisarSetorComercialAction.do', 'setorComercialOrigem', 'idLocalidade', document.forms[0].LOCA_I.value , 275, 480, 'Informe Localidade Inicial',document.forms[0].SETOR_I);
						         limparOrigem(2);">
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
						
						<a href="javascript:limparBorrachaOrigem(2);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>
				
				<tr>
					<td><strong>Setor Comercial Final:</strong></td>
					
					<td>
						
						<html:text maxlength="3" property="SETOR_F"
							size="3"
							
							onkeypress="validaEnterComMensagem(event, 'exibirGerarRelatorioContasAtrasoAction.do?objetoConsulta=4','SETOR_F','Setor Comercial Final'); return isCampoNumerico(event);"/>
								
						<a href="javascript:chamarPopup('exibirPesquisarSetorComercialAction.do', 'setorComercialDestino', 'idLocalidade', document.forms[0].LOCA_F.value, 275, 480, 'Informe Localidade Final',document.forms[0].SETOR_F);
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
						
						<a href="javascript:limparBorrachaDestino(2);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>
								
				<tr>
					<td><strong>Quadra Inicial:</strong></td>
					<td><html:text 
							
							maxlength="5"
							property="QUAD_I" 
							size="5"
							onkeypress="return isCampoNumerico(event);"
							onblur="replicarQuadra()" />
							 
							<logic:present name="corQuadraOrigem" scope="request">
								<span style="color:#ff0000" id="msgQuadraInicial">
									<bean:write scope="request" name="msgQuadraInicial" />
								</span>
							</logic:present> 
					</td>
				</tr>
				
				<tr>
					<td><strong>Quadra Final:</strong></td>
					<td><html:text 
							
							maxlength="5"
							property="QUAD_F" 
							size="5"
							onkeypress="return isCampoNumerico(event);"
							/> 
							<logic:present name="corQuadraOrigem" scope="request">
								<span style="color:#ff0000" id="msgQuadraInicial">
									<bean:write scope="request" name="msgQuadraInicial" />
								</span>
							</logic:present> 
					</td>
				</tr>
				
				<tr>
					<td><strong>Lote Inicial:</strong></td>
					<td>
					<html:text 
						maxlength="4" 
						property="LOTE_I" 
						size="4"
						onblur="javascript:replicarLote();" 
						 />
					</td>
				</tr>
				
				<tr>
					<td><strong>Lote Final:</strong></td>
					<td>
					<html:text 
						maxlength="4" 
						property="LOTE_F" 
						size="4"
						 
						 />
					</td>
				</tr>
				
				<tr>
					<td><strong>SubLote Inicial:</strong></td>
					<td>
					<html:text 
						maxlength="3" 
						property="SLOTE_I" 
						size="4"
						onblur="javascript:replicarSubLote();" 
						 />
					</td>
				</tr>
				
				<tr>
					<td><strong>SubLote Final:</strong></td>
					<td>
					<html:text 
						maxlength="3" 
						property="SLOTE_F" 
						size="4"
						 
						 />
					</td>
				</tr>
				
				<tr>
					<td>
						<strong>Grupo de Faturamento Inicial:</strong>
					</td>
					
					<td><strong> 
						<html:select property="GRUPO_I"
								onblur="replicarCampo(GRUPO_F,GRUPO_I);">
							<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<logic:present name="colecaoFaturamentoGrupo" scope="request">
							<html:options collection="colecaoFaturamentoGrupo" 
									labelProperty="descricao" 
									property="id"/>
							</logic:present>
						</html:select> </strong>
					</td>
				</tr>
				
				<tr>
					<td>
						<strong>Grupo de Faturamento Final:</strong>
					</td>
					
					<td><strong> 
						<html:select property="GRUPO_F">
							<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<logic:present name="colecaoFaturamentoGrupo" scope="request">
							<html:options collection="colecaoFaturamentoGrupo" 
									labelProperty="descricao" 
									property="id"/>
							</logic:present>
						</html:select> </strong>
					</td>
				</tr>
				
				<tr>
					<td>
						<strong>Rota Inicial:</strong>
					</td>
					
					<td><html:text 
							
							maxlength="3"
							property="ROTA_I" 
							size="4"
							onkeypress="return isCampoNumerico(event);"
							onblur="replicarRota(); campoGrupoFaturamentoDependentePreenchido(event);" />
					</td>
				</tr>
				
				<tr>
					<td>
						<strong>Rota Final:</strong>
					</td>
					
					<td><html:text 
							
							maxlength="3"
							property="ROTA_F" 
							size="4"
							onkeypress="return isCampoNumerico(event);"
							onblur="campoGrupoFaturamentoDependentePreenchido(event);" />
					</td>
				</tr>
				
				
				<tr> 
                	<td>
                		<strong>Categoria Inicial:</strong>                		
                    </td>
                    
                    <td><strong> 
						<html:select property="CAT_I"
									onblur="replicarCampo(CAT_F,CAT_I);">
							<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<logic:present name="colecaoCategoria" scope="request">
							<html:options collection="colecaoCategoria" 
									labelProperty="descricao" 
									property="id"/>
							</logic:present>
						</html:select> </strong>
					</td>
                	
              	</tr>
              	
              	<tr> 
                	<td>
                		<strong>Categoria Final:</strong>                		
                    </td>
                    
                    <td><strong> 
						<html:select property="CAT_F">
							<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<logic:present name="colecaoCategoria" scope="request">
							<html:options collection="colecaoCategoria" 
									labelProperty="descricao" 
									property="id"/>
							</logic:present>
						</html:select> </strong>
					</td>
                	
              	</tr> 
              	
              	<tr> 
                	<td>
                		<strong>Subcategoria Inicial:</strong>                		
                    </td>
                    
                    <td><strong> 
						<html:select property="SCAT_I"
									onblur="replicarCampo(SCAT_F,SCAT_I); campoSubCategoriaDependentePreenchido(event);" style="width:350px">
							<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<logic:present name="colecaoSubcategoria" scope="request">
							<html:options collection="colecaoSubcategoria" 
									labelProperty="descricao" 
									property="id"/>
							</logic:present>
						</html:select> </strong>
					</td>
                	
              	</tr>
              	
              	<tr> 
                	<td>
                		<strong>Subcategoria Final:</strong>                		
                    </td>
                    
                    <td><strong> 
						<html:select property="SCAT_F" style="width:350px" onblur="campoSubCategoriaDependentePreenchido(event);">
							<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<logic:present name="colecaoSubcategoria" scope="request">
							<html:options collection="colecaoSubcategoria" 
									labelProperty="descricao" 
									property="id"/>
							</logic:present>
						</html:select> </strong>
					</td>
                	
              	</tr>
              	
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
							value="Gerar" 
							onClick="javascript:validarForm();" />
							
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

<!-- relatorio_contas_atraso_gerar.jsp -->
</html:html>
