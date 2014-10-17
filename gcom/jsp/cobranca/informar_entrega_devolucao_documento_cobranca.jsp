<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

<%@ page import="gcom.util.ConstantesSistema"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="gcom.util.Util"%>
<%@page import="java.util.Date"%><html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
	dynamicJavascript="true" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<SCRIPT LANGUAGE="JavaScript">

function validarForm(form){
	var indicadorCampo;
	var documentoValidoinformado = false;

	if (form.cobrancaAcaoID == null || form.cobrancaAcaoID.value == '' || form.cobrancaAcaoID.value == '-1'){
		alert("Informe Ação de Cobrança.");
		return false;
	}

	if (form.matriculaImovel != null && form.matriculaImovel.value != ''){
		indicadorCampo = 1;
		if(!verificarDocumentoValido(indicadorCampo)){
			alert("Campo obrigatório para o 1º documento não informado");
			return false;
		}else{
			documentoValidoinformado = true;
		}
	}

	if (form.matriculaImovel2 != null && form.matriculaImovel2.value != ''){
		indicadorCampo = 2;
		if(!verificarDocumentoValido(indicadorCampo)){
			alert("Campo obrigatório para o 2º documento não informado");
			return false;
		}else{
			documentoValidoinformado = true;
		}
	}

	if (form.matriculaImovel3 != null && form.matriculaImovel3.value != ''){
		indicadorCampo = 3;
		if(!verificarDocumentoValido(indicadorCampo)){
			alert("Campo obrigatório para o 3º documento não informado");
			return false;
		}else{
			documentoValidoinformado = true;
		}
	}

	if (form.matriculaImovel4 != null && form.matriculaImovel4.value != ''){
		indicadorCampo = 4;
		if(!verificarDocumentoValido(indicadorCampo)){
			alert("Campo obrigatório para o 4º documento não informado");
			return false;
		}else{
			documentoValidoinformado = true;
		}
	}

	if (form.matriculaImovel5 != null && form.matriculaImovel5.value != ''){
		indicadorCampo = 5;
		if(!verificarDocumentoValido(indicadorCampo)){
			alert("Campo obrigatório para o 5º documento não informado");
			return false;
		}else{
			documentoValidoinformado = true;
		}
	}

	if (form.matriculaImovel6 != null && form.matriculaImovel6.value != ''){
		indicadorCampo = 6;
		if(!verificarDocumentoValido(indicadorCampo)){
			alert("Campo obrigatório para o 6º documento não informado");
			return false;
		}else{
			documentoValidoinformado = true;
		}
	}

	if (form.matriculaImovel7 != null && form.matriculaImovel7.value != ''){
		indicadorCampo = 7;
		if(!verificarDocumentoValido(indicadorCampo)){
			alert("Campo obrigatório para o 7º documento não informado");
			return false;
		}else{
			documentoValidoinformado = true;
		}
	}

	if (form.matriculaImovel8 != null && form.matriculaImovel8.value != ''){
		indicadorCampo = 8;
		if(!verificarDocumentoValido(indicadorCampo)){
			alert("Campo obrigatório para o 8º documento não informado");
			return false;
		}else{
			documentoValidoinformado = true;
		}
	}

	if (form.matriculaImovel9 != null && form.matriculaImovel9.value != ''){
		indicadorCampo = 9;
		if(!verificarDocumentoValido(indicadorCampo)){
			alert("Campo obrigatório para o 9º documento não informado");
			return false;
		}else{
			documentoValidoinformado = true;
		}
	}

	if (form.matriculaImovel10 != null && form.matriculaImovel10.value != ''){
		indicadorCampo = 10;
		if(!verificarDocumentoValido(indicadorCampo)){
			alert("Campo obrigatório para o 10º documento não informado");
			return false;
		}else{
			documentoValidoinformado = true;
		}
	}

	if(!validateInformarEntregaDevolucaoDocumentoCobrancaActionForm(form)){
		return false;
	}
	
	if(!documentoValidoinformado){
		alert("Nenhum documento válido foi informado.");
		return false;
	}else{
		form.submit();
	}
}

function submeterCampoMatriculaImovel(idCampoImovel){
	var form = document.forms[0];

	form.campoMatriculaImovel.value = idCampoImovel;

	if(form.campoMatriculaImovel.value != null){
		abrirPopup('exibirPesquisarImovelAction.do', 400, 800);
	}
}

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
    var form = document.forms[0];

    if(tipoConsulta == 'imovel'){
	  	switch (form.campoMatriculaImovel.value){
	      case 'matriculaImovel':
		        limparForm();      
		        form.matriculaImovel.value = codigoRegistro;
		        form.action = 'exibirInformarEntregaDevolucaoDocumentoCobrancaAction.do'
		        form.submit();
	            break;
			   
		   case 'matriculaImovel2':
		        limparForm();      
		        form.matriculaImovel2.value = codigoRegistro;
		        form.action = 'exibirInformarEntregaDevolucaoDocumentoCobrancaAction.do'
		        form.submit();
	            break;
	
		   case 'matriculaImovel3':
		        limparForm();      
		        form.matriculaImovel3.value = codigoRegistro;
		        form.action = 'exibirInformarEntregaDevolucaoDocumentoCobrancaAction.do'
		        form.submit();
	            break;
			   
		   case 'matriculaImovel4':
		        limparForm();      
		        form.matriculaImovel4.value = codigoRegistro;
		        form.action = 'exibirInformarEntregaDevolucaoDocumentoCobrancaAction.do'
		        form.submit();
	            break;
	
		   case 'matriculaImovel5':
		        limparForm();      
		        form.matriculaImovel5.value = codigoRegistro;
		        form.action = 'exibirInformarEntregaDevolucaoDocumentoCobrancaAction.do'
		        form.submit();
	            break;
			   
		   case 'matriculaImovel6':
		        limparForm();      
		        form.matriculaImovel6.value = codigoRegistro;
		        form.action = 'exibirInformarEntregaDevolucaoDocumentoCobrancaAction.do'
		        form.submit();
	            break;
	
		   case 'matriculaImovel7':
		        limparForm();      
		        form.matriculaImovel7.value = codigoRegistro;
		        form.action = 'exibirInformarEntregaDevolucaoDocumentoCobrancaAction.do'
		        form.submit();
	            break;
			   
		   case 'matriculaImovel8':
		        limparForm();      
		        form.matriculaImovel8.value = codigoRegistro;
		        form.action = 'exibirInformarEntregaDevolucaoDocumentoCobrancaAction.do'
		        form.submit();
	            break;
	
		   case 'matriculaImovel9':
		        limparForm();      
		        form.matriculaImovel9.value = codigoRegistro;
		        form.action = 'exibirInformarEntregaDevolucaoDocumentoCobrancaAction.do'
		        form.submit();
	            break;
			   
		   case 'matriculaImovel10':
		        limparForm();      
		        form.matriculaImovel10.value = codigoRegistro;
		        form.action = 'exibirInformarEntregaDevolucaoDocumentoCobrancaAction.do'
		        form.submit();
	            break;
	
	 	   default:
	       	break;
		}
    }
    
    if (tipoConsulta == 'parecer') {

    	switch (codigoRegistro){
	        case 'parecer':
	    	   form.parecerDescricao.value = descricaoRegistro;
	    	   form.parecer.value = descricaoRegistro.substring(0, 10);
	           form.action = 'exibirInformarEntregaDevolucaoDocumentoCobrancaAction.do'           
	    	   form.submit();
	 		   break;
	 		   
	 	   case 'parecer2':
	    	   form.parecerDescricao2.value = descricaoRegistro;
	    	   form.parecer2.value = descricaoRegistro.substring(0, 10);
	           form.action = 'exibirInformarEntregaDevolucaoDocumentoCobrancaAction.do'           
	    	   form.submit();
	 		   break;
	
	       case 'parecer3':
	    	   form.parecerDescricao3.value = descricaoRegistro;
	    	   form.parecer3.value = descricaoRegistro.substring(0, 10);
	           form.action = 'exibirInformarEntregaDevolucaoDocumentoCobrancaAction.do'           
	    	   form.submit();
	 		   break;
	 		   
	 	   case 'parecer4':
	    	   form.parecerDescricao4.value = descricaoRegistro;
	    	   form.parecer4.value = descricaoRegistro.substring(0, 10);
	           form.action = 'exibirInformarEntregaDevolucaoDocumentoCobrancaAction.do'           
	    	   form.submit();
	 		   break;
	
	       case 'parecer5':
	    	   form.parecerDescricao5.value = descricaoRegistro;
	    	   form.parecer5.value = descricaoRegistro.substring(0, 10);
	           form.action = 'exibirInformarEntregaDevolucaoDocumentoCobrancaAction.do'           
	    	   form.submit();
	 		   break;
	 		   
	 	   case 'parecer6':
	    	   form.parecerDescricao6.value = descricaoRegistro;
	    	   form.parecer6.value = descricaoRegistro.substring(0, 10);
	           form.action = 'exibirInformarEntregaDevolucaoDocumentoCobrancaAction.do'           
	    	   form.submit();
	 		   break;
	
	       case 'parecer7':
	    	   form.parecerDescricao7.value = descricaoRegistro;
	    	   form.parecer7.value = descricaoRegistro.substring(0, 10);
	           form.action = 'exibirInformarEntregaDevolucaoDocumentoCobrancaAction.do'           
	    	   form.submit();
	 		   break;
	 		   
	 	   case 'parecer8':
	    	   form.parecerDescricao8.value = descricaoRegistro;
	    	   form.parecer8.value = descricaoRegistro.substring(0, 10);
	           form.action = 'exibirInformarEntregaDevolucaoDocumentoCobrancaAction.do'           
	    	   form.submit();
	 		   break;
	
	       case 'parecer9':
	    	   form.parecerDescricao9.value = descricaoRegistro;
	    	   form.parecer9.value = descricaoRegistro.substring(0, 10);
	           form.action = 'exibirInformarEntregaDevolucaoDocumentoCobrancaAction.do'           
	    	   form.submit();
	 		   break;
	 		   
	 	   case 'parecer10':
	    	   form.parecerDescricao10.value = descricaoRegistro;
	    	   form.parecer10.value = descricaoRegistro.substring(0, 10);
	           form.action = 'exibirInformarEntregaDevolucaoDocumentoCobrancaAction.do'           
	    	   form.submit();
	 		   break;
	
		 	default:
	           break;
 		}
	}
}

function limparForm(limpar){
	var form = document.forms[0];
	if (limpar != null) {
	    if(limpar == 'OK'){
			form.cobrancaAcaoID.value = '';
	    	form.matriculaImovel.value = '';
	    	form.matriculaImovel2.value = '';
	    	form.matriculaImovel3.value = '';
	    	form.matriculaImovel4.value = '';
	    	form.matriculaImovel5.value = '';
	    	form.matriculaImovel6.value = '';
	    	form.matriculaImovel7.value = '';
	    	form.matriculaImovel8.value = '';
	    	form.matriculaImovel9.value = '';
	    	form.matriculaImovel10.value = '';
	    	form.indicadorEntregaDevolucao[1].checked = true;
	    	form.indicadorEntregaDevolucao2[1].checked = true;
	    	form.indicadorEntregaDevolucao3[1].checked = true;
	    	form.indicadorEntregaDevolucao4[1].checked = true;
	    	form.indicadorEntregaDevolucao5[1].checked = true;
	    	form.indicadorEntregaDevolucao6[1].checked = true;
	    	form.indicadorEntregaDevolucao7[1].checked = true;
	    	form.indicadorEntregaDevolucao8[1].checked = true;
	    	form.indicadorEntregaDevolucao9[1].checked = true;
	    	form.indicadorEntregaDevolucao10[1].checked = true;
	    	form.motivoNaoEntrega[form.motivoNaoEntrega.selectedIndex].value = '-1';
	    	form.motivoNaoEntrega2[form.motivoNaoEntrega2.selectedIndex].value = '-1';
	    	form.motivoNaoEntrega3[form.motivoNaoEntrega3.selectedIndex].value = '-1';
	    	form.motivoNaoEntrega4[form.motivoNaoEntrega4.selectedIndex].value = '-1';
	    	form.motivoNaoEntrega5[form.motivoNaoEntrega5.selectedIndex].value = '-1';
	    	form.motivoNaoEntrega6[form.motivoNaoEntrega6.selectedIndex].value = '-1';
	    	form.motivoNaoEntrega7[form.motivoNaoEntrega7.selectedIndex].value = '-1';
	    	form.motivoNaoEntrega8[form.motivoNaoEntrega8.selectedIndex].value = '-1';
	    	form.motivoNaoEntrega9[form.motivoNaoEntrega9.selectedIndex].value = '-1';
	    	form.motivoNaoEntrega10[form.motivoNaoEntrega10.selectedIndex].value = '-1';
	    	form.parecer.value = '';
	    	form.parecer2.value = '';
	    	form.parecer3.value = '';
	    	form.parecer4.value = '';
	    	form.parecer5.value = '';
	    	form.parecer6.value = '';
	    	form.parecer7.value = '';
	    	form.parecer8.value = '';
	    	form.parecer9.value = '';
	    	form.parecer10.value = '';
	    	form.action = 'exibirInformarEntregaDevolucaoDocumentoCobrancaAction.do'           
    	    form.submit();
	    }
	}
}

function verificarDocumentoValido(indicadorCampo) {
	var form = document.forms[0];		

	var documentoPreechido = false;
	
	if (indicadorCampo == 1 && (form.matriculaImovel != null && form.matriculaImovel.value != '') && 
			(form.dataEntregaDevolucao != null && form.dataEntregaDevolucao != '')){
			if(form.indicadorEntregaDevolucao[0].checked == false && 
					(form.motivoNaoEntrega[form.motivoNaoEntrega.selectedIndex] != null &&
							 form.motivoNaoEntrega[form.motivoNaoEntrega.selectedIndex].value != '-1')){
				documentoPreechido = true;
			}else if(form.indicadorEntregaDevolucao[0].checked == true){
				documentoPreechido = true;
			}
	}

	if (indicadorCampo == 2 && (form.matriculaImovel2 != null && form.matriculaImovel2.value != '') && 
			(form.dataEntregaDevolucao2 != null && form.dataEntregaDevolucao2 != '')){
			if(form.indicadorEntregaDevolucao2[0].checked == false && 
					(form.motivoNaoEntrega2[form.motivoNaoEntrega2.selectedIndex] != null &&
							 form.motivoNaoEntrega2[form.motivoNaoEntrega2.selectedIndex].value != '-1')){
				documentoPreechido = true;
			}else if(form.indicadorEntregaDevolucao2[0].checked == true){
				documentoPreechido = true;
			}
	}

	if (indicadorCampo == 3 && (form.matriculaImovel3 != null && form.matriculaImovel3.value != '') && 
			(form.dataEntregaDevolucao3 != null && form.dataEntregaDevolucao3 != '')){
			if(form.indicadorEntregaDevolucao3[0].checked == false && 
					(form.motivoNaoEntrega3[form.motivoNaoEntrega3.selectedIndex] != null &&
							 form.motivoNaoEntrega3[form.motivoNaoEntrega3.selectedIndex].value != '-1')){
				documentoPreechido = true;
			}else if(form.indicadorEntregaDevolucao3[0].checked == true){
				documentoPreechido = true;
			}
	}

	if (indicadorCampo == 4 && (form.matriculaImovel4 != null && form.matriculaImovel4.value != '') && 
			(form.dataEntregaDevolucao4 != null && form.dataEntregaDevolucao4 != '')){
			if(form.indicadorEntregaDevolucao4[0].checked == false && 
					(form.motivoNaoEntrega4[form.motivoNaoEntrega4.selectedIndex] != null &&
							 form.motivoNaoEntrega4[form.motivoNaoEntrega4.selectedIndex].value != '-1')){
				documentoPreechido = true;
			}else if(form.indicadorEntregaDevolucao4[0].checked == true){
				documentoPreechido = true;
			}
	}

	if (indicadorCampo == 5 && (form.matriculaImovel5 != null && form.matriculaImovel5.value != '') && 
			(form.dataEntregaDevolucao5 != null && form.dataEntregaDevolucao5 != '')){
			if(form.indicadorEntregaDevolucao5[0].checked == false && 
					(form.motivoNaoEntrega5[form.motivoNaoEntrega5.selectedIndex] != null &&
							 form.motivoNaoEntrega5[form.motivoNaoEntrega5.selectedIndex].value != '-1')){
				documentoPreechido = true;
			}else if(form.indicadorEntregaDevolucao5[0].checked == true){
				documentoPreechido = true;
			}
	}

	if (indicadorCampo == 6 && (form.matriculaImovel6 != null && form.matriculaImovel6.value != '') && 
			(form.dataEntregaDevolucao6 != null && form.dataEntregaDevolucao6 != '')){
			if(form.indicadorEntregaDevolucao6[0].checked == false && 
					(form.motivoNaoEntrega6[form.motivoNaoEntrega6.selectedIndex] != null &&
							 form.motivoNaoEntrega6[form.motivoNaoEntrega6.selectedIndex].value != '-1')){
				documentoPreechido = true;
			}else if(form.indicadorEntregaDevolucao6[0].checked == true){
				documentoPreechido = true;
			}
	}

	if (indicadorCampo == 7 && (form.matriculaImovel7 != null && form.matriculaImovel7.value != '') && 
			(form.dataEntregaDevolucao7 != null && form.dataEntregaDevolucao7 != '')){
			if(form.indicadorEntregaDevolucao7[0].checked == false && 
					(form.motivoNaoEntrega7[form.motivoNaoEntrega7.selectedIndex] != null &&
							 form.motivoNaoEntrega7[form.motivoNaoEntrega7.selectedIndex].value != '-1')){
				documentoPreechido = true;
			}else if(form.indicadorEntregaDevolucao7[0].checked == true){
				documentoPreechido = true;
			}
	}

	if (indicadorCampo == 8 && (form.matriculaImovel8 != null && form.matriculaImovel8.value != '') && 
			(form.dataEntregaDevolucao8 != null && form.dataEntregaDevolucao8 != '')){
			if(form.indicadorEntregaDevolucao8[0].checked == false && 
					(form.motivoNaoEntrega8[form.motivoNaoEntrega8.selectedIndex] != null &&
							 form.motivoNaoEntrega8[form.motivoNaoEntrega8.selectedIndex].value != '-1')){
				documentoPreechido = true;
			}else if(form.indicadorEntregaDevolucao8[0].checked == true){
				documentoPreechido = true;
			}
	}

	if (indicadorCampo == 9 && (form.matriculaImovel9 != null && form.matriculaImovel9.value != '') && 
			(form.dataEntregaDevolucao9 != null && form.dataEntregaDevolucao9 != '')){
			if(form.indicadorEntregaDevolucao9[0].checked == false && 
					(form.motivoNaoEntrega9[form.motivoNaoEntrega9.selectedIndex] != null &&
							 form.motivoNaoEntrega9[form.motivoNaoEntrega9.selectedIndex].value != '-1')){
				documentoPreechido = true;
			}else if(form.indicadorEntregaDevolucao9[0].checked == true){
				documentoPreechido = true;
			}
	}

	if (indicadorCampo == 10 && (form.matriculaImovel10 != null && form.matriculaImovel10.value != '') && 
			(form.dataEntregaDevolucao10 != null && form.dataEntregaDevolucao10 != '')){
			if(form.indicadorEntregaDevolucao10[0].checked == false && 
					(form.motivoNaoEntrega10[form.motivoNaoEntrega10.selectedIndex] != null &&
							 form.motivoNaoEntrega10[form.motivoNaoEntrega10.selectedIndex].value != '-1')){
				documentoPreechido = true;
			}else if(form.indicadorEntregaDevolucao10[0].checked == true){
				documentoPreechido = true;
			}
	}
	
	return documentoPreechido;
}  

function desabilitaMotivoNaoEntrega(){
	var form = document.forms[0];

	if(form.indicadorEntregaDevolucao[0].checked == true){
		form.motivoNaoEntrega.selectedIndex = '-1';
		form.motivoNaoEntrega.disabled = true;
	}else{
		form.motivoNaoEntrega.disabled = false;
	}
	
	if(form.indicadorEntregaDevolucao2[0].checked == true){
		form.motivoNaoEntrega2.selectedIndex = '-1';
		form.motivoNaoEntrega2.disabled = true;
	}else{
		form.motivoNaoEntrega2.disabled = false;
	}

	if(form.indicadorEntregaDevolucao3[0].checked == true){
		form.motivoNaoEntrega3.selectedIndex = '-1';
		form.motivoNaoEntrega3.disabled = true;
	}else{
		form.motivoNaoEntrega3.disabled = false;
	}

	if(form.indicadorEntregaDevolucao4[0].checked == true){
		form.motivoNaoEntrega4.selectedIndex = '-1';
		form.motivoNaoEntrega4.disabled = true;
	}else{
		form.motivoNaoEntrega4.disabled = false;
	}

	if(form.indicadorEntregaDevolucao5[0].checked == true){
		form.motivoNaoEntrega5.selectedIndex = '-1';
		form.motivoNaoEntrega5.disabled = true;
	}else{
		form.motivoNaoEntrega5.disabled = false;
	}

	if(form.indicadorEntregaDevolucao6[0].checked == true){
		form.motivoNaoEntrega6.selectedIndex = '-1';
		form.motivoNaoEntrega6.disabled = true;
	}else{
		form.motivoNaoEntrega6.disabled = false;
	}

	if(form.indicadorEntregaDevolucao7[0].checked == true){
		form.motivoNaoEntrega7.selectedIndex = '-1';
		form.motivoNaoEntrega7.disabled = true;
	}else{
		form.motivoNaoEntrega7.disabled = false;
	}

	if(form.indicadorEntregaDevolucao8[0].checked == true){
		form.motivoNaoEntrega8.selectedIndex = '-1';
		form.motivoNaoEntrega8.disabled = true;
	}else{
		form.motivoNaoEntrega8.disabled = false;
	}

	if(form.indicadorEntregaDevolucao9[0].checked == true){
		form.motivoNaoEntrega9.selectedIndex = '-1';
		form.motivoNaoEntrega9.disabled = true;
	}else{
		form.motivoNaoEntrega9.disabled = false;
	}

	if(form.indicadorEntregaDevolucao10[0].checked == true){
		form.motivoNaoEntrega10.selectedIndex = '-1';
		form.motivoNaoEntrega10.disabled = true;
	}else{
		form.motivoNaoEntrega10.disabled = false;
	}
}

</SCRIPT>


</head>

<body leftmargin="5" topmargin="5"
	onload="limparForm('${requestScope.limparForm}');desabilitaMotivoNaoEntrega();">

<html:form action="/informarEntregaDevolucaoDocumentoCobrancaAction"
	name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
	type="gcom.gui.cobranca.InformarEntregaDevolucaoDocumentoCobrancaActionForm"
	method="post">

	<html:hidden property="parecer" />
	<html:hidden property="parecer2" />
	<html:hidden property="parecer3" />
	<html:hidden property="parecer4" />
	<html:hidden property="parecer5" />
	<html:hidden property="parecer6" />
	<html:hidden property="parecer7" />
	<html:hidden property="parecer8" />
	<html:hidden property="parecer9" />
	<html:hidden property="parecer10" />
	<html:hidden property="parecerDescricao" />
	<html:hidden property="parecerDescricao2" />
	<html:hidden property="parecerDescricao3" />
	<html:hidden property="parecerDescricao4" />
	<html:hidden property="parecerDescricao5" />
	<html:hidden property="parecerDescricao6" />
	<html:hidden property="parecerDescricao7" />
	<html:hidden property="parecerDescricao8" />
	<html:hidden property="parecerDescricao9" />
	<html:hidden property="parecerDescricao10" />
	<html:hidden property="campoMatriculaImovel" />

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="4" cellpadding="0">
		<tr>
			<td width="149" valign="top" class="leftcoltext">
			<div align="center"><%@ include
				file="/jsp/util/informacoes_usuario.jsp"%> <%@ include
				file="/jsp/util/mensagens.jsp"%></div>
			</td>
			<td width="625" valign="top" bgcolor="#003399" class="centercoltext">
			<table height="100%">

				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">Informar Entrega/Devolução de Documentos de
					Cobrança</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>
				</tr>
			</table>
			<!--Fim Tabela Reference a Páginação da Tela de Processo-->
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para informar a entrega/devolução dos
					documentos de cobrança, informe os dados abaixo:</td>
				</tr>

				<tr>
					<td><strong>A&ccedil;&atilde;o de Cobran&ccedil;a:<font
						color="#FF0000">*</font></strong></td>
					<td colspan="4"><html:select property="cobrancaAcaoID"
						tabindex="1"
						name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
						style="width: 230px;" size="2">
						<logic:present name="colecaoCobrancaAcao">
							<html:option value="" />
							<html:options collection="colecaoCobrancaAcao"
								labelProperty="descricaoCobrancaAcao" property="id" />
						</logic:present>
					</html:select></td>
				</tr>

				<tr>

					<td><strong></strong></td>


					<td colspan="3" align="left"><label><span
						class="style2"> </span> </label></td>
				</tr>

				<tr>

					<td><strong></strong></td>


					<td colspan="3" align="left"><label><span
						class="style2"> </span> </label></td>
				</tr>

				<tr>
					<td colspan="4">
					<table bgcolor="#99ccff" width="100%">

						<tbody>
							<tr bordercolor="#FFFFFF" bgcolor="#79bbfd">
								<td width="20%">
								<div align="center">Matrícula<font color="#FF0000">*</font></div>
								</td>
								<td width="18%">
								<div align="center">Entrega / Devolução<font
									color="#FF0000">*</font></div>
								</td>
								<td width="20%">
								<div align="center">Data da Entrega / Devolução<font
									color="#FF0000">*</font></div>
								</td>
								<td>
								<div align="center">Motivo Não Entrega<font
									color="#FF0000">*</font></div>
								</td>
								<td>
								<div align="center">Parecer</div>
								</td>
							</tr>

							<tr>
								<%-- PRIMEIRA LINHA DA TABELA --%>
								<td align="center"><html:text property="matriculaImovel"
									size="9" maxlength="9"
									onkeypress="validaEnterComMensagem(event, 'exibirInformarEntregaDevolucaoDocumentoCobrancaAction.do', 'matriculaImovel','Matrícula do Imóvel')"
									tabindex="1" /> <a
									href="javascript:submeterCampoMatriculaImovel('matriculaImovel');">
								<img width="23" height="21"
									src="<bean:message key='caminho.imagens'/>pesquisa.gif"
									border="0" /></a></td>
								<td align="left">
									<logic:empty name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"	property="indicadorEntregaDevolucao">
										<input type="radio"	name="indicadorEntregaDevolucao" onclick="javascript:desabilitaMotivoNaoEntrega();"  
											value="<%=ConstantesSistema.INDICADOR_ENTREGA_DOCUMENTO%>"> <strong>Entrega
										<br>
										<input type="radio" name="indicadorEntregaDevolucao" onclick="javascript:desabilitaMotivoNaoEntrega();"
											value="<%=ConstantesSistema.INDICADOR_DEVOLUCAO_DOCUMENTO%>" checked> Devolução</strong>
									</logic:empty>
									<logic:notEmpty name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"	property="indicadorEntregaDevolucao">
										<logic:equal name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
											property="indicadorEntregaDevolucao" value="1">
											<input type="radio"	name="indicadorEntregaDevolucao" onclick="javascript:desabilitaMotivoNaoEntrega();"  
												value="<%=ConstantesSistema.INDICADOR_ENTREGA_DOCUMENTO%>" checked> <strong>Entrega
											<br>
											<input type="radio" name="indicadorEntregaDevolucao" onclick="javascript:desabilitaMotivoNaoEntrega();"
												value="<%=ConstantesSistema.INDICADOR_DEVOLUCAO_DOCUMENTO%>" > Devolução</strong>
										</logic:equal>
										<logic:notEqual name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
											property="indicadorEntregaDevolucao" value="1">
											<input type="radio"	name="indicadorEntregaDevolucao" onclick="javascript:desabilitaMotivoNaoEntrega();"  
												value="<%=ConstantesSistema.INDICADOR_ENTREGA_DOCUMENTO%>" > <strong>Entrega
											<br>
											<input type="radio" name="indicadorEntregaDevolucao" onclick="javascript:desabilitaMotivoNaoEntrega();"
												value="<%=ConstantesSistema.INDICADOR_DEVOLUCAO_DOCUMENTO%>" checked> Devolução</strong>
										</logic:notEqual>
									</logic:notEmpty>
								</td>
								<td align="center"><html:text tabindex="8"
									property="dataEntregaDevolucao"
									value="<%=Util.formatarData(new Date())%>" maxlength="10"
									size="10" onkeyup="mascaraData(this, event);"/> <a
									href="javascript:abrirCalendario('InformarEntregaDevolucaoDocumentoCobrancaActionForm', 'dataEntregaDevolucao')">
								<img border="0"
									src="<bean:message key="caminho.imagens"/>calendario.gif"
									width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
								</td>
								<td align="center"><html:select property="motivoNaoEntrega"
									tabindex="6">
									<html:option
										value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>
									<html:options collection="colecaoMotivosNaoEntrega"
										labelProperty="descricao" property="id" />
								</html:select></td>
								<td align="center">
									<a href="javascript:abrirPopup('exibirInformarDescricaoParecerPopupAction.do?idCampo=parecer&dsCampo='+'<bean:write name="InformarEntregaDevolucaoDocumentoCobrancaActionForm" property="parecerDescricao"/>' , 520, 550);"
									title="<bean:write name="InformarEntregaDevolucaoDocumentoCobrancaActionForm" property="parecerDescricao"/>">
								<logic:empty
									name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
									property="parecer">
										Informe Parecer						  	
								  	</logic:empty> <logic:notEmpty
									name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
									property="parecer">
									<bean:write
										name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
										property="parecer" />
								</logic:notEmpty> </a></td>
								<%-- FIM DA PRIMEIRA LINHA DA TABELA --%>
							</tr>

							<tr>
								<%-- INICIO DA SEGUNDA LINHA DA TABELA--%>
								<td align="center"><html:text property="matriculaImovel2"
									size="9" maxlength="9"
									onkeypress="validaEnterComMensagem(event, 'exibirInformarEntregaDevolucaoDocumentoCobrancaAction.do', 'matriculaImovel2','Matrícula do Imóvel')"
									tabindex="1" /> <a
									href="javascript:submeterCampoMatriculaImovel('matriculaImovel2');">
								<img width="23" height="21"
									src="<bean:message key='caminho.imagens'/>pesquisa.gif"
									border="0" /></a></td>
								<td align="left">
									<logic:empty name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"	property="indicadorEntregaDevolucao2">
										<input type="radio"	name="indicadorEntregaDevolucao2" onclick="javascript:desabilitaMotivoNaoEntrega();"  
											value="<%=ConstantesSistema.INDICADOR_ENTREGA_DOCUMENTO%>"> <strong>Entrega
										<br>
										<input type="radio" name="indicadorEntregaDevolucao2" onclick="javascript:desabilitaMotivoNaoEntrega();"
											value="<%=ConstantesSistema.INDICADOR_DEVOLUCAO_DOCUMENTO%>" checked> Devolução</strong>
									</logic:empty>
									<logic:notEmpty name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"	property="indicadorEntregaDevolucao2">
										<logic:equal name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
											property="indicadorEntregaDevolucao2" value="1">
											<input type="radio"	name="indicadorEntregaDevolucao2" onclick="javascript:desabilitaMotivoNaoEntrega();"  
												value="<%=ConstantesSistema.INDICADOR_ENTREGA_DOCUMENTO%>" checked> <strong>Entrega
											<br>
											<input type="radio" name="indicadorEntregaDevolucao2" onclick="javascript:desabilitaMotivoNaoEntrega();"
												value="<%=ConstantesSistema.INDICADOR_DEVOLUCAO_DOCUMENTO%>" > Devolução</strong>
										</logic:equal>
										<logic:notEqual name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
											property="indicadorEntregaDevolucao2" value="1">
											<input type="radio"	name="indicadorEntregaDevolucao2" onclick="javascript:desabilitaMotivoNaoEntrega();"  
												value="<%=ConstantesSistema.INDICADOR_ENTREGA_DOCUMENTO%>" > <strong>Entrega
											<br>
											<input type="radio" name="indicadorEntregaDevolucao2" onclick="javascript:desabilitaMotivoNaoEntrega();"
												value="<%=ConstantesSistema.INDICADOR_DEVOLUCAO_DOCUMENTO%>" checked> Devolução</strong>
										</logic:notEqual>
									</logic:notEmpty>
								</td>
								<td align="center"><html:text tabindex="8"
									property="dataEntregaDevolucao2"
									value="<%=Util.formatarData(new Date())%>" maxlength="10"
									size="10" onkeyup="mascaraData(this, event);" /> <a
									href="javascript:abrirCalendario('InformarEntregaDevolucaoDocumentoCobrancaActionForm', 'dataEntregaDevolucao2')">
								<img border="0"
									src="<bean:message key="caminho.imagens"/>calendario.gif"
									width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
								</td>
								<td align="center"><html:select
									property="motivoNaoEntrega2" tabindex="6">
									<html:option
										value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>
									<html:options collection="colecaoMotivosNaoEntrega"
										labelProperty="descricao" property="id" />
								</html:select></td>
								<td align="center">
									<a href="javascript:abrirPopup('exibirInformarDescricaoParecerPopupAction.do?idCampo=parecer2&dsCampo='+'<bean:write name="InformarEntregaDevolucaoDocumentoCobrancaActionForm" property="parecerDescricao2"/>' , 520, 550);"
									title="<bean:write name="InformarEntregaDevolucaoDocumentoCobrancaActionForm" property="parecerDescricao2"/>">
								<logic:empty
									name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
									property="parecer2">
										Informe Parecer						  	
								  	</logic:empty> <logic:notEmpty
									name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
									property="parecer2">
									<bean:write
										name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
										property="parecer2" />
								</logic:notEmpty> </a></td>
								<%-- FIM DA SEGUNDA LINHA DA TABELA --%>
							</tr>

							<tr>
								<%-- INICIO DA TERCEIRA LINHA DA TABELA--%>
								<td align="center"><html:text property="matriculaImovel3"
									size="9" maxlength="9"
									onkeypress="validaEnterComMensagem(event, 'exibirInformarEntregaDevolucaoDocumentoCobrancaAction.do', 'matriculaImovel3','Matrícula do Imóvel')"
									tabindex="1" /> <a
									href="javascript:submeterCampoMatriculaImovel('matriculaImovel3');">
								<img width="23" height="21"
									src="<bean:message key='caminho.imagens'/>pesquisa.gif"
									border="0" /></a></td>
								<td align="left">
									<logic:empty name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"	property="indicadorEntregaDevolucao3">
										<input type="radio"	name="indicadorEntregaDevolucao3" onclick="javascript:desabilitaMotivoNaoEntrega();"  
											value="<%=ConstantesSistema.INDICADOR_ENTREGA_DOCUMENTO%>"> <strong>Entrega
										<br>
										<input type="radio" name="indicadorEntregaDevolucao3" onclick="javascript:desabilitaMotivoNaoEntrega();"
											value="<%=ConstantesSistema.INDICADOR_DEVOLUCAO_DOCUMENTO%>" checked> Devolução</strong>
									</logic:empty>
									<logic:notEmpty name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"	property="indicadorEntregaDevolucao3">
										<logic:equal name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
											property="indicadorEntregaDevolucao3" value="1">
											<input type="radio"	name="indicadorEntregaDevolucao3" onclick="javascript:desabilitaMotivoNaoEntrega();"  
												value="<%=ConstantesSistema.INDICADOR_ENTREGA_DOCUMENTO%>" checked> <strong>Entrega
											<br>
											<input type="radio" name="indicadorEntregaDevolucao3" onclick="javascript:desabilitaMotivoNaoEntrega();"
												value="<%=ConstantesSistema.INDICADOR_DEVOLUCAO_DOCUMENTO%>" > Devolução</strong>
										</logic:equal>
										<logic:notEqual name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
											property="indicadorEntregaDevolucao3" value="1">
											<input type="radio"	name="indicadorEntregaDevolucao3" onclick="javascript:desabilitaMotivoNaoEntrega();"  
												value="<%=ConstantesSistema.INDICADOR_ENTREGA_DOCUMENTO%>" > <strong>Entrega
											<br>
											<input type="radio" name="indicadorEntregaDevolucao3" onclick="javascript:desabilitaMotivoNaoEntrega();"
												value="<%=ConstantesSistema.INDICADOR_DEVOLUCAO_DOCUMENTO%>" checked> Devolução</strong>
										</logic:notEqual>
									</logic:notEmpty>
								</td>
								<td align="center"><html:text tabindex="8"
									property="dataEntregaDevolucao3"
									value="<%=Util.formatarData(new Date())%>" maxlength="10"
									size="10" onkeyup="mascaraData(this, event);" /> <a
									href="javascript:abrirCalendario('InformarEntregaDevolucaoDocumentoCobrancaActionForm', 'dataEntregaDevolucao3')">
								<img border="0"
									src="<bean:message key="caminho.imagens"/>calendario.gif"
									width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
								</td>
								<td align="center"><html:select
									property="motivoNaoEntrega3" tabindex="6">
									<html:option
										value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>
									<html:options collection="colecaoMotivosNaoEntrega"
										labelProperty="descricao" property="id" />
								</html:select></td>
								<td align="center">
									<a href="javascript:abrirPopup('exibirInformarDescricaoParecerPopupAction.do?idCampo=parecer3&dsCampo='+'<bean:write name="InformarEntregaDevolucaoDocumentoCobrancaActionForm" property="parecerDescricao3"/>' , 520, 550);"
									title="<bean:write name="InformarEntregaDevolucaoDocumentoCobrancaActionForm" property="parecerDescricao3"/>">
								<logic:empty
									name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
									property="parecer3">
										Informe Parecer						  	
								  	</logic:empty> <logic:notEmpty
									name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
									property="parecer3">
									<bean:write
										name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
										property="parecer3" />
								</logic:notEmpty> </a></td>
								<%-- FIM TERCEIRA LINHA DA TABELA --%>
							</tr>

							<tr>
								<%-- INICIO QUARTA LINHA DA TABELA --%>
								<td align="center"><html:text property="matriculaImovel4"
									size="9" maxlength="9"
									onkeypress="validaEnterComMensagem(event, 'exibirInformarEntregaDevolucaoDocumentoCobrancaAction.do', 'matriculaImovel4','Matrícula do Imóvel')"
									tabindex="1" /> <a
									href="javascript:submeterCampoMatriculaImovel('matriculaImovel4');">
								<img width="23" height="21"
									src="<bean:message key='caminho.imagens'/>pesquisa.gif"
									border="0" /></a></td>
								<td align="left">
									<logic:empty name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"	property="indicadorEntregaDevolucao4">
										<input type="radio"	name="indicadorEntregaDevolucao4" onclick="javascript:desabilitaMotivoNaoEntrega();"  
											value="<%=ConstantesSistema.INDICADOR_ENTREGA_DOCUMENTO%>"> <strong>Entrega
										<br>
										<input type="radio" name="indicadorEntregaDevolucao4" onclick="javascript:desabilitaMotivoNaoEntrega();"
											value="<%=ConstantesSistema.INDICADOR_DEVOLUCAO_DOCUMENTO%>" checked> Devolução</strong>
									</logic:empty>
									<logic:notEmpty name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"	property="indicadorEntregaDevolucao4">
										<logic:equal name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
											property="indicadorEntregaDevolucao4" value="1">
											<input type="radio"	name="indicadorEntregaDevolucao4" onclick="javascript:desabilitaMotivoNaoEntrega();"  
												value="<%=ConstantesSistema.INDICADOR_ENTREGA_DOCUMENTO%>" checked> <strong>Entrega
											<br>
											<input type="radio" name="indicadorEntregaDevolucao4" onclick="javascript:desabilitaMotivoNaoEntrega();"
												value="<%=ConstantesSistema.INDICADOR_DEVOLUCAO_DOCUMENTO%>" > Devolução</strong>
										</logic:equal>
										<logic:notEqual name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
											property="indicadorEntregaDevolucao4" value="1">
											<input type="radio"	name="indicadorEntregaDevolucao4" onclick="javascript:desabilitaMotivoNaoEntrega();"  
												value="<%=ConstantesSistema.INDICADOR_ENTREGA_DOCUMENTO%>" > <strong>Entrega
											<br>
											<input type="radio" name="indicadorEntregaDevolucao4" onclick="javascript:desabilitaMotivoNaoEntrega();"
												value="<%=ConstantesSistema.INDICADOR_DEVOLUCAO_DOCUMENTO%>" checked> Devolução</strong>
										</logic:notEqual>
									</logic:notEmpty>
								</td>
								<td align="center"><html:text tabindex="8"
									property="dataEntregaDevolucao4"
									value="<%=Util.formatarData(new Date())%>" maxlength="10"
									size="10" onkeyup="mascaraData(this, event);" /> <a
									href="javascript:abrirCalendario('InformarEntregaDevolucaoDocumentoCobrancaActionForm', 'dataEntregaDevolucao4')">
								<img border="0"
									src="<bean:message key="caminho.imagens"/>calendario.gif"
									width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
								</td>
								<td align="center"><html:select
									property="motivoNaoEntrega4" tabindex="6">
									<html:option
										value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>
									<html:options collection="colecaoMotivosNaoEntrega"
										labelProperty="descricao" property="id" />
								</html:select></td>
								<td align="center">
									<a href="javascript:abrirPopup('exibirInformarDescricaoParecerPopupAction.do?idCampo=parecer4&dsCampo='+'<bean:write name="InformarEntregaDevolucaoDocumentoCobrancaActionForm" property="parecerDescricao4"/>' , 520, 550);"
									title="<bean:write name="InformarEntregaDevolucaoDocumentoCobrancaActionForm" property="parecerDescricao4"/>">
								<logic:empty
									name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
									property="parecer4">
										Informe Parecer						  	
								  	</logic:empty> <logic:notEmpty
									name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
									property="parecer4">
									<bean:write
										name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
										property="parecer4" />
								</logic:notEmpty> </a></td>
								<%--FIM QUARTA LINHA --%>
							</tr>

							<tr>
								<%--INICIO QUINTA LINHA --%>
								<td align="center"><html:text property="matriculaImovel5"
									size="9" maxlength="9"
									onkeypress="validaEnterComMensagem(event, 'exibirInformarEntregaDevolucaoDocumentoCobrancaAction.do', 'matriculaImovel5','Matrícula do Imóvel')"
									tabindex="1" /> <a
									href="javascript:submeterCampoMatriculaImovel('matriculaImovel5');">
								<img width="23" height="21"
									src="<bean:message key='caminho.imagens'/>pesquisa.gif"
									border="0" /></a></td>
								<td align="left">
									<logic:empty name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"	property="indicadorEntregaDevolucao5">
										<input type="radio"	name="indicadorEntregaDevolucao5" onclick="javascript:desabilitaMotivoNaoEntrega();"  
											value="<%=ConstantesSistema.INDICADOR_ENTREGA_DOCUMENTO%>"> <strong>Entrega
										<br>
										<input type="radio" name="indicadorEntregaDevolucao5" onclick="javascript:desabilitaMotivoNaoEntrega();"
											value="<%=ConstantesSistema.INDICADOR_DEVOLUCAO_DOCUMENTO%>" checked> Devolução</strong>
									</logic:empty>
									<logic:notEmpty name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"	property="indicadorEntregaDevolucao5">
										<logic:equal name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
											property="indicadorEntregaDevolucao5" value="1">
											<input type="radio"	name="indicadorEntregaDevolucao5" onclick="javascript:desabilitaMotivoNaoEntrega();"  
												value="<%=ConstantesSistema.INDICADOR_ENTREGA_DOCUMENTO%>" checked> <strong>Entrega
											<br>
											<input type="radio" name="indicadorEntregaDevolucao5" onclick="javascript:desabilitaMotivoNaoEntrega();"
												value="<%=ConstantesSistema.INDICADOR_DEVOLUCAO_DOCUMENTO%>" > Devolução</strong>
										</logic:equal>
										<logic:notEqual name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
											property="indicadorEntregaDevolucao5" value="1">
											<input type="radio"	name="indicadorEntregaDevolucao5" onclick="javascript:desabilitaMotivoNaoEntrega();"  
												value="<%=ConstantesSistema.INDICADOR_ENTREGA_DOCUMENTO%>" > <strong>Entrega
											<br>
											<input type="radio" name="indicadorEntregaDevolucao5" onclick="javascript:desabilitaMotivoNaoEntrega();"
												value="<%=ConstantesSistema.INDICADOR_DEVOLUCAO_DOCUMENTO%>" checked> Devolução</strong>
										</logic:notEqual>
									</logic:notEmpty>
								</td>
								<td align="center"><html:text tabindex="8"
									property="dataEntregaDevolucao5"
									value="<%=Util.formatarData(new Date())%>" maxlength="10"
									size="10" onkeyup="mascaraData(this, event);" /> <a
									href="javascript:abrirCalendario('InformarEntregaDevolucaoDocumentoCobrancaActionForm', 'dataEntregaDevolucao5')">
								<img border="0"
									src="<bean:message key="caminho.imagens"/>calendario.gif"
									width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
								</td>
								<td align="center"><html:select
									property="motivoNaoEntrega5" tabindex="6">
									<html:option
										value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>
									<html:options collection="colecaoMotivosNaoEntrega"
										labelProperty="descricao" property="id" />
								</html:select></td>
								<td align="center">
									<a href="javascript:abrirPopup('exibirInformarDescricaoParecerPopupAction.do?idCampo=parecer5&dsCampo='+'<bean:write name="InformarEntregaDevolucaoDocumentoCobrancaActionForm" property="parecerDescricao5"/>' , 520, 550);"
									title="<bean:write name="InformarEntregaDevolucaoDocumentoCobrancaActionForm" property="parecerDescricao5"/>">
								<logic:empty
									name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
									property="parecer5">
										Informe Parecer						  	
								  	</logic:empty> <logic:notEmpty
									name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
									property="parecer5">
									<bean:write
										name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
										property="parecer5" />
								</logic:notEmpty> </a></td>
								<%-- FIM QUINTA LINHA --%>
							</tr>

							<tr>
								<%-- INICIO SEXTA LINHA --%>
								<td align="center"><html:text property="matriculaImovel6"
									size="9" maxlength="9"
									onkeypress="validaEnterComMensagem(event, 'exibirInformarEntregaDevolucaoDocumentoCobrancaAction.do', 'matriculaImovel6','Matrícula do Imóvel')"
									tabindex="1" /> <a
									href="javascript:submeterCampoMatriculaImovel('matriculaImovel6');">
								<img width="23" height="21"
									src="<bean:message key='caminho.imagens'/>pesquisa.gif"
									border="0" /></a></td>
								<td align="left">
									<logic:empty name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"	property="indicadorEntregaDevolucao6">
										<input type="radio"	name="indicadorEntregaDevolucao6" onclick="javascript:desabilitaMotivoNaoEntrega();"  
											value="<%=ConstantesSistema.INDICADOR_ENTREGA_DOCUMENTO%>"> <strong>Entrega
										<br>
										<input type="radio" name="indicadorEntregaDevolucao6" onclick="javascript:desabilitaMotivoNaoEntrega();"
											value="<%=ConstantesSistema.INDICADOR_DEVOLUCAO_DOCUMENTO%>" checked> Devolução</strong>
									</logic:empty>
									<logic:notEmpty name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"	property="indicadorEntregaDevolucao6">
										<logic:equal name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
											property="indicadorEntregaDevolucao6" value="1">
											<input type="radio"	name="indicadorEntregaDevolucao6" onclick="javascript:desabilitaMotivoNaoEntrega();"  
												value="<%=ConstantesSistema.INDICADOR_ENTREGA_DOCUMENTO%>" checked> <strong>Entrega
											<br>
											<input type="radio" name="indicadorEntregaDevolucao6" onclick="javascript:desabilitaMotivoNaoEntrega();"
												value="<%=ConstantesSistema.INDICADOR_DEVOLUCAO_DOCUMENTO%>" > Devolução</strong>
										</logic:equal>
										<logic:notEqual name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
											property="indicadorEntregaDevolucao6" value="1">
											<input type="radio"	name="indicadorEntregaDevolucao6" onclick="javascript:desabilitaMotivoNaoEntrega();"  
												value="<%=ConstantesSistema.INDICADOR_ENTREGA_DOCUMENTO%>" > <strong>Entrega
											<br>
											<input type="radio" name="indicadorEntregaDevolucao6" onclick="javascript:desabilitaMotivoNaoEntrega();"
												value="<%=ConstantesSistema.INDICADOR_DEVOLUCAO_DOCUMENTO%>" checked> Devolução</strong>
										</logic:notEqual>
									</logic:notEmpty>
								</td>
								<td align="center"><html:text tabindex="8"
									property="dataEntregaDevolucao6"
									value="<%=Util.formatarData(new Date())%>" maxlength="10"
									size="10" onkeyup="mascaraData(this, event);" /> <a
									href="javascript:abrirCalendario('InformarEntregaDevolucaoDocumentoCobrancaActionForm', 'dataEntregaDevolucao6')">
								<img border="0"
									src="<bean:message key="caminho.imagens"/>calendario.gif"
									width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
								</td>
								<td align="center"><html:select
									property="motivoNaoEntrega6" tabindex="6">
									<html:option
										value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>
									<html:options collection="colecaoMotivosNaoEntrega"
										labelProperty="descricao" property="id" />
								</html:select></td>
								<td align="center">
									<a href="javascript:abrirPopup('exibirInformarDescricaoParecerPopupAction.do?idCampo=parecer6&dsCampo='+'<bean:write name="InformarEntregaDevolucaoDocumentoCobrancaActionForm" property="parecerDescricao6"/>' , 520, 550);"
									title="<bean:write name="InformarEntregaDevolucaoDocumentoCobrancaActionForm" property="parecerDescricao6"/>">
								<logic:empty
									name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
									property="parecer6">
										Informe Parecer						  	
								  	</logic:empty> <logic:notEmpty
									name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
									property="parecer6">
									<bean:write
										name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
										property="parecer6" />
								</logic:notEmpty> </a></td>
								<%-- FIM SEXTA LINHA --%>
							</tr>

							<tr>
								<%-- INICIO SETIMA LINHA --%>
								<td align="center"><html:text property="matriculaImovel7"
									size="9" maxlength="9"
									onkeypress="validaEnterComMensagem(event, 'exibirInformarEntregaDevolucaoDocumentoCobrancaAction.do', 'matriculaImovel7','Matrícula do Imóvel')"
									tabindex="1" /> <a
									href="javascript:submeterCampoMatriculaImovel('matriculaImovel7');">
								<img width="23" height="21"
									src="<bean:message key='caminho.imagens'/>pesquisa.gif"
									border="0" /></a></td>
								<td align="left">
									<logic:empty name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"	property="indicadorEntregaDevolucao7">
										<input type="radio"	name="indicadorEntregaDevolucao7" onclick="javascript:desabilitaMotivoNaoEntrega();"  
											value="<%=ConstantesSistema.INDICADOR_ENTREGA_DOCUMENTO%>"> <strong>Entrega
										<br>
										<input type="radio" name="indicadorEntregaDevolucao7" onclick="javascript:desabilitaMotivoNaoEntrega();"
											value="<%=ConstantesSistema.INDICADOR_DEVOLUCAO_DOCUMENTO%>" checked> Devolução</strong>
									</logic:empty>
									<logic:notEmpty name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"	property="indicadorEntregaDevolucao7">
										<logic:equal name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
											property="indicadorEntregaDevolucao7" value="1">
											<input type="radio"	name="indicadorEntregaDevolucao7" onclick="javascript:desabilitaMotivoNaoEntrega();"  
												value="<%=ConstantesSistema.INDICADOR_ENTREGA_DOCUMENTO%>" checked> <strong>Entrega
											<br>
											<input type="radio" name="indicadorEntregaDevolucao7" onclick="javascript:desabilitaMotivoNaoEntrega();"
												value="<%=ConstantesSistema.INDICADOR_DEVOLUCAO_DOCUMENTO%>" > Devolução</strong>
										</logic:equal>
										<logic:notEqual name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
											property="indicadorEntregaDevolucao7" value="1">
											<input type="radio"	name="indicadorEntregaDevolucao7" onclick="javascript:desabilitaMotivoNaoEntrega();"  
												value="<%=ConstantesSistema.INDICADOR_ENTREGA_DOCUMENTO%>" > <strong>Entrega
											<br>
											<input type="radio" name="indicadorEntregaDevolucao7" onclick="javascript:desabilitaMotivoNaoEntrega();"
												value="<%=ConstantesSistema.INDICADOR_DEVOLUCAO_DOCUMENTO%>" checked> Devolução</strong>
										</logic:notEqual>
									</logic:notEmpty>
								</td>
								<td align="center"><html:text tabindex="8"
									property="dataEntregaDevolucao7"
									value="<%=Util.formatarData(new Date())%>" maxlength="10"
									size="10" onkeyup="mascaraData(this, event);" /> <a
									href="javascript:abrirCalendario('InformarEntregaDevolucaoDocumentoCobrancaActionForm', 'dataEntregaDevolucao7')">
								<img border="0"
									src="<bean:message key="caminho.imagens"/>calendario.gif"
									width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
								</td>
								<td align="center"><html:select
									property="motivoNaoEntrega7" tabindex="6">
									<html:option
										value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>
									<html:options collection="colecaoMotivosNaoEntrega"
										labelProperty="descricao" property="id" />
								</html:select></td>
								<td align="center">
									<a href="javascript:abrirPopup('exibirInformarDescricaoParecerPopupAction.do?idCampo=parecer7&dsCampo='+'<bean:write name="InformarEntregaDevolucaoDocumentoCobrancaActionForm" property="parecerDescricao7"/>' , 520, 550);"
									title="<bean:write name="InformarEntregaDevolucaoDocumentoCobrancaActionForm" property="parecerDescricao7"/>">
								<logic:empty
									name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
									property="parecer7">
										Informe Parecer						  	
								  	</logic:empty> <logic:notEmpty
									name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
									property="parecer7">
									<bean:write
										name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
										property="parecer7" />
								</logic:notEmpty> </a></td>
								<%-- FIM SETIMA LINHA--%>
							</tr>

							<tr>
								<%--INICIO OITAVA LINHA --%>
								<td align="center"><html:text property="matriculaImovel8"
									size="9" maxlength="9"
									onkeypress="validaEnterComMensagem(event, 'exibirInformarEntregaDevolucaoDocumentoCobrancaAction.do', 'matriculaImovel8','Matrícula do Imóvel')"
									tabindex="1" /> <a
									href="javascript:submeterCampoMatriculaImovel('matriculaImovel8');">
								<img width="23" height="21"
									src="<bean:message key='caminho.imagens'/>pesquisa.gif"
									border="0" /></a></td>
								<td align="left">
									<logic:empty name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"	property="indicadorEntregaDevolucao8">
										<input type="radio"	name="indicadorEntregaDevolucao8" onclick="javascript:desabilitaMotivoNaoEntrega();"  
											value="<%=ConstantesSistema.INDICADOR_ENTREGA_DOCUMENTO%>"> <strong>Entrega
										<br>
										<input type="radio" name="indicadorEntregaDevolucao8" onclick="javascript:desabilitaMotivoNaoEntrega();"
											value="<%=ConstantesSistema.INDICADOR_DEVOLUCAO_DOCUMENTO%>" checked> Devolução</strong>
									</logic:empty>
									<logic:notEmpty name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"	property="indicadorEntregaDevolucao8">
										<logic:equal name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
											property="indicadorEntregaDevolucao8" value="1">
											<input type="radio"	name="indicadorEntregaDevolucao8" onclick="javascript:desabilitaMotivoNaoEntrega();"  
												value="<%=ConstantesSistema.INDICADOR_ENTREGA_DOCUMENTO%>" checked> <strong>Entrega
											<br>
											<input type="radio" name="indicadorEntregaDevolucao8" onclick="javascript:desabilitaMotivoNaoEntrega();"
												value="<%=ConstantesSistema.INDICADOR_DEVOLUCAO_DOCUMENTO%>" > Devolução</strong>
										</logic:equal>
										<logic:notEqual name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
											property="indicadorEntregaDevolucao8" value="1">
											<input type="radio"	name="indicadorEntregaDevolucao8" onclick="javascript:desabilitaMotivoNaoEntrega();"  
												value="<%=ConstantesSistema.INDICADOR_ENTREGA_DOCUMENTO%>" > <strong>Entrega
											<br>
											<input type="radio" name="indicadorEntregaDevolucao8" onclick="javascript:desabilitaMotivoNaoEntrega();"
												value="<%=ConstantesSistema.INDICADOR_DEVOLUCAO_DOCUMENTO%>" checked> Devolução</strong>
										</logic:notEqual>
									</logic:notEmpty>
								</td>
								<td align="center"><html:text tabindex="8"
									property="dataEntregaDevolucao8"
									value="<%=Util.formatarData(new Date())%>" maxlength="10"
									size="10" onkeyup="mascaraData(this, event);" /> <a
									href="javascript:abrirCalendario('InformarEntregaDevolucaoDocumentoCobrancaActionForm', 'dataEntregaDevolucao8')">
								<img border="0"
									src="<bean:message key="caminho.imagens"/>calendario.gif"
									width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
								</td>
								<td align="center"><html:select
									property="motivoNaoEntrega8" tabindex="6">
									<html:option
										value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>
									<html:options collection="colecaoMotivosNaoEntrega"
										labelProperty="descricao" property="id" />
								</html:select></td>
								<td align="center">
									<a href="javascript:abrirPopup('exibirInformarDescricaoParecerPopupAction.do?idCampo=parecer8&dsCampo='+'<bean:write name="InformarEntregaDevolucaoDocumentoCobrancaActionForm" property="parecerDescricao8"/>' , 520, 550);"
									title="<bean:write name="InformarEntregaDevolucaoDocumentoCobrancaActionForm" property="parecerDescricao8"/>">
								<logic:empty
									name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
									property="parecer8">
										Informe Parecer						  	
								  	</logic:empty> <logic:notEmpty
									name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
									property="parecer8">
									<bean:write
										name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
										property="parecer8" />
								</logic:notEmpty> </a></td>
								<%-- FIM OITAVA LINHA--%>
							</tr>

							<tr>
								<%-- INICIO NONA LINHA--%>
								<td align="center"><html:text property="matriculaImovel9"
									size="9" maxlength="9"
									onkeypress="validaEnterComMensagem(event, 'exibirInformarEntregaDevolucaoDocumentoCobrancaAction.do', 'matriculaImovel9','Matrícula do Imóvel')"
									tabindex="1" /> <a
									href="javascript:submeterCampoMatriculaImovel('matriculaImovel9');">
								<img width="23" height="21"
									src="<bean:message key='caminho.imagens'/>pesquisa.gif"
									border="0" /></a></td>
								<td align="left">
									<logic:empty name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"	property="indicadorEntregaDevolucao9">
										<input type="radio"	name="indicadorEntregaDevolucao9" onclick="javascript:desabilitaMotivoNaoEntrega();"  
											value="<%=ConstantesSistema.INDICADOR_ENTREGA_DOCUMENTO%>"> <strong>Entrega
										<br>
										<input type="radio" name="indicadorEntregaDevolucao9" onclick="javascript:desabilitaMotivoNaoEntrega();"
											value="<%=ConstantesSistema.INDICADOR_DEVOLUCAO_DOCUMENTO%>" checked> Devolução</strong>
									</logic:empty>
									<logic:notEmpty name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"	property="indicadorEntregaDevolucao9">
										<logic:equal name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
											property="indicadorEntregaDevolucao9" value="1">
											<input type="radio"	name="indicadorEntregaDevolucao9" onclick="javascript:desabilitaMotivoNaoEntrega();"  
												value="<%=ConstantesSistema.INDICADOR_ENTREGA_DOCUMENTO%>" checked> <strong>Entrega
											<br>
											<input type="radio" name="indicadorEntregaDevolucao9" onclick="javascript:desabilitaMotivoNaoEntrega();"
												value="<%=ConstantesSistema.INDICADOR_DEVOLUCAO_DOCUMENTO%>" > Devolução</strong>
										</logic:equal>
										<logic:notEqual name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
											property="indicadorEntregaDevolucao9" value="1">
											<input type="radio"	name="indicadorEntregaDevolucao9" onclick="javascript:desabilitaMotivoNaoEntrega();"  
												value="<%=ConstantesSistema.INDICADOR_ENTREGA_DOCUMENTO%>" > <strong>Entrega
											<br>
											<input type="radio" name="indicadorEntregaDevolucao9" onclick="javascript:desabilitaMotivoNaoEntrega();"
												value="<%=ConstantesSistema.INDICADOR_DEVOLUCAO_DOCUMENTO%>" checked> Devolução</strong>
										</logic:notEqual>
									</logic:notEmpty>
								</td>
								<td align="center"><html:text tabindex="8"
									property="dataEntregaDevolucao9"
									value="<%=Util.formatarData(new Date())%>" maxlength="10"
									size="10" onkeyup="mascaraData(this, event);" /> <a
									href="javascript:abrirCalendario('InformarEntregaDevolucaoDocumentoCobrancaActionForm', 'dataEntregaDevolucao9')">
								<img border="0"
									src="<bean:message key="caminho.imagens"/>calendario.gif"
									width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
								</td>
								<td align="center"><html:select
									property="motivoNaoEntrega9" tabindex="6">
									<html:option
										value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>
									<html:options collection="colecaoMotivosNaoEntrega"
										labelProperty="descricao" property="id" />
								</html:select></td>
								<td align="center">
									<a href="javascript:abrirPopup('exibirInformarDescricaoParecerPopupAction.do?idCampo=parecer9&dsCampo='+'<bean:write name="InformarEntregaDevolucaoDocumentoCobrancaActionForm" property="parecerDescricao9"/>' , 520, 550);"
									title="<bean:write name="InformarEntregaDevolucaoDocumentoCobrancaActionForm" property="parecerDescricao9"/>">
								<logic:empty
									name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
									property="parecer9">
										Informe Parecer						  	
								  	</logic:empty> <logic:notEmpty
									name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
									property="parecer9">
									<bean:write
										name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
										property="parecer9" />
								</logic:notEmpty> </a></td>
								<%-- FIM NONA LINHA--%>
							</tr>

							<tr>
								<%-- INICIO DECIMA LINHA--%>
								<td align="center"><html:text property="matriculaImovel10"
									size="9" maxlength="9"
									onkeypress="validaEnterComMensagem(event, 'exibirInformarEntregaDevolucaoDocumentoCobrancaAction.do', 'matriculaImovel10','Matrícula do Imóvel')"
									tabindex="1" />	<a
									href="javascript:submeterCampoMatriculaImovel('matriculaImovel10');">
								<img width="23" height="21"
									src="<bean:message key='caminho.imagens'/>pesquisa.gif"
									border="0" /></a></td>
								<td align="left">
									<logic:empty name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"	property="indicadorEntregaDevolucao10">
										<input type="radio"	name="indicadorEntregaDevolucao10" onclick="javascript:desabilitaMotivoNaoEntrega();"  
											value="<%=ConstantesSistema.INDICADOR_ENTREGA_DOCUMENTO%>"> <strong>Entrega
										<br>
										<input type="radio" name="indicadorEntregaDevolucao10" onclick="javascript:desabilitaMotivoNaoEntrega();"
											value="<%=ConstantesSistema.INDICADOR_DEVOLUCAO_DOCUMENTO%>" checked> Devolução</strong>
									</logic:empty>
									<logic:notEmpty name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"	property="indicadorEntregaDevolucao10">
										<logic:equal name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
											property="indicadorEntregaDevolucao10" value="1">
											<input type="radio"	name="indicadorEntregaDevolucao10" onclick="javascript:desabilitaMotivoNaoEntrega();"  
												value="<%=ConstantesSistema.INDICADOR_ENTREGA_DOCUMENTO%>" checked> <strong>Entrega
											<br>
											<input type="radio" name="indicadorEntregaDevolucao10" onclick="javascript:desabilitaMotivoNaoEntrega();"
												value="<%=ConstantesSistema.INDICADOR_DEVOLUCAO_DOCUMENTO%>" > Devolução</strong>
										</logic:equal>
										<logic:notEqual name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
											property="indicadorEntregaDevolucao10" value="1">
											<input type="radio"	name="indicadorEntregaDevolucao10" onclick="javascript:desabilitaMotivoNaoEntrega();"  
												value="<%=ConstantesSistema.INDICADOR_ENTREGA_DOCUMENTO%>" > <strong>Entrega
											<br>
											<input type="radio" name="indicadorEntregaDevolucao10" onclick="javascript:desabilitaMotivoNaoEntrega();"
												value="<%=ConstantesSistema.INDICADOR_DEVOLUCAO_DOCUMENTO%>" checked> Devolução</strong>
										</logic:notEqual>
									</logic:notEmpty>
								</td>
								<td align="center"><html:text tabindex="8"
									property="dataEntregaDevolucao10"
									value="<%=Util.formatarData(new Date())%>" maxlength="10"
									size="10" onkeyup="mascaraData(this, event);" /> <a
									href="javascript:abrirCalendario('InformarEntregaDevolucaoDocumentoCobrancaActionForm', 'dataEntregaDevolucao10')">
								<img border="0"
									src="<bean:message key="caminho.imagens"/>calendario.gif"
									width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
								</td>
								<td align="center"><html:select
									property="motivoNaoEntrega10" tabindex="6">
									<html:option
										value="<%= ""+ConstantesSistema.NUMERO_NAO_INFORMADO %>">&nbsp;</html:option>
									<html:options collection="colecaoMotivosNaoEntrega"
										labelProperty="descricao" property="id" />
								</html:select></td>
								<td align="center">
									<a href="javascript:abrirPopup('exibirInformarDescricaoParecerPopupAction.do?idCampo=parecer10&dsCampo='+'<bean:write name="InformarEntregaDevolucaoDocumentoCobrancaActionForm" property="parecerDescricao10"/>' , 520, 550);"
									title="<bean:write name="InformarEntregaDevolucaoDocumentoCobrancaActionForm" property="parecerDescricao10"/>">
								<logic:empty
									name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
									property="parecer10">
										Informe Parecer						  	
								  	</logic:empty> <logic:notEmpty
									name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
									property="parecer10">
									<bean:write
										name="InformarEntregaDevolucaoDocumentoCobrancaActionForm"
										property="parecer10" />
								</logic:notEmpty> </a></td>
								<%-- FIM DECIMA LINHA--%>
							</tr>
						</tbody>
					</table>
					</td>
				</tr>

				<tr>
					<td><strong> <font color="#FF0000"></font></strong></td>
					<td align="right">
					<div align="left"><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios</div>
					</td>
				</tr>
				<tr>
					<td align="left"><input name="Button" type="button"
						class="bottonRightCol" value="Desfazer" align="left"
						onclick="window.location.href='<html:rewrite page="/exibirInformarEntregaDevolucaoDocumentoCobrancaAction.do?menu=sim"/>'">
					<input type="button" name="ButtonCancelar" class="bottonRightCol"
						value="Cancelar"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'">
					</td>
					<td align="right"><input name="Button" type="button"
						class="bottonRightCol" value="Efetuar"
						onclick="javascript:validarForm(document.forms[0]);" /></td>
				</tr>
			</table>
			<script language="JavaScript">
					desabilitaMotivoNaoEntrega();
			</script></td>
		</tr>
	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</body>
</html:html>
