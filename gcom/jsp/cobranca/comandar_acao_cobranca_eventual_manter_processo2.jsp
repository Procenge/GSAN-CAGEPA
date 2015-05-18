<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>
<%@ page import="gcom.gui.cobranca.ManterComandoAcaoCobrancaDetalhesActionForm"%>
<%@ page import="gcom.cobranca.CobrancaAtividade" %>
<%@ page import="gcom.tarefa.TarefaRelatorio" %>
<%@ page import="gcom.cobranca.CobrancaAcao" %>

<%@ page import="gcom.util.ConstantesSistema"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
<html:html>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<html:javascript staticJavascript="false"  formName="ManterComandoAcaoCobrancaDetalhesActionForm" />
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="ManterComandoAcaoCobrancaDetalhesActionForm" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>


<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/engine.js'> </script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/util.js'> </script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/interface/AjaxService.js'> </script>

<script language="JavaScript">
<!-- Begin

function validaArrecadador() {
	var form = document.forms[0];
	form.idClienteCombo.disabled = true;

	var i;
	for (i = 0; i < form.cobrancaAcao.length; i++) {
		if (form.cobrancaAcao.options[i].selected==true&&form.cobrancaAcao.options[i].value == '36') {
			form.idClienteCombo.disabled = false;
		}
	}

	if (form.idClienteCombo.disabled) {
		form.idClienteCombo.value = '-1';
	}
}

    function caracteresespeciais () {
     this.ag = new Array("titulo", "Título possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.aa = new Array("localidadeOrigemID", "Localidade Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.ab = new Array("localidadeDestinoID", "Localidade Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.ac = new Array("setorComercialOrigemCD", "Setor Comercial Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.ad = new Array("setorComercialDestinoCD", "Setor Comercial Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.ae = new Array("idCliente", "Cliente deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    }

    function IntegerValidations () {
     this.ac = new Array("prazoExecucao", "Cliente deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.ab = new Array("quantidadeMaximaDocumentos", "Quantidade máxima de documentos deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.af = new Array("localidadeOrigemID", "Localidade Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.ag = new Array("localidadeDestinoID", "Localidade Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.ah = new Array("setorComercialOrigemCD", "Setor Comercial Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.ai = new Array("setorComercialDestinoCD", "Setor Comercial Final deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.aj = new Array("idCliente", "Cliente deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.aq = new Array("cobrancaCriterio", "Critério de Cobrança deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    }

    function required () {
     this.ab = new Array("titulo", "Informe Título.", new Function ("varName", " return this[varName];"));
     this.ac = new Array("descricaoSolicitacao", "Informe Descrição Solicitação.", new Function ("varName", " return this[varName];"));     	
     this.al = new Array("cobrancaAcao", "Informe Ação de Cobrança.", new Function ("varName", " return this[varName];"));
     this.am = new Array("cobrancaAtividade", "Informa Atividade de Cobrança.", new Function ("varName", " return this[varName];"));
    } 

function validar(){

   var form = document.forms[0];
   var msg = '';
   if(form.cobrancaAcao.value == "-1"){
	   msg = 'Informe Ação de Cobrança.\n';
   }
   if(form.cobrancaAtividade.value == "-1"){
	   msg = msg + 'Informe Atividade de Cobrança.\n';
   }
	if( msg != '' ){
		alert(msg);
		return false;
	}else{
		return true;
	}
}

function validarCliente(apagar){

	var form = document.forms[0];
	if (form.idCliente.value != ''){

  		if(apagar == 1) form.clienteRelacaoTipo.value = "";	
  		form.clienteRelacaoTipo.disabled = false;
  		form.codigoClienteSuperior.value = "";
  		form.codigoClienteSuperior.disabled = true;
	}else{
		form.clienteRelacaoTipo.value = "";
  		form.clienteRelacaoTipo.disabled = true;
  		form.codigoClienteSuperior.value = "";
  		form.codigoClienteSuperior.disabled = false;
	 	}
	}



	function desabilitarCobrancaAcao(){
		var form = document.forms[0];

		if (form.localidadeOrigemID.value != ''  ){

	      	form.cobrancaGrupo.value = "";	
	      	form.cobrancaGrupo.disabled = true;
	      	form.gerenciaRegional.disabled = true;
	      	form.unidadeNegocio.disabled = true;
     		form.nomeCliente.value = ""
     		form.idCliente.value = ""
	        form.idCliente.disabled = true;
	        form.clienteRelacaoTipo.value = "";
	        form.clienteRelacaoTipo.disabled = true;  
	        form.arquivoImoveis.disabled = true;
	        form.arquivoImoveis.value = '';    
	        form.identificadorInformacaoArquivo[0].disabled = true;
	        form.identificadorInformacaoArquivo[1].disabled = true;
	        form.codigoClienteSuperior.value = "";
   			form.codigoClienteSuperior.disabled = true;	

   			form.setorComercialOrigemCD.disabled = false;
	     	form.setorComercialDestinoCD.disabled = false;
	      	
		}else{
	     	form.cobrancaGrupo.disabled = false;
		    form.gerenciaRegional.disabled = false;
		    form.unidadeNegocio.disabled = false;      
		    form.idCliente.disabled = false;
		    form.codigoClienteSuperior.disabled = false;  
		    form.arquivoImoveis.disabled = false;
	        form.arquivoImoveis.value = '';  
	        form.identificadorInformacaoArquivo[0].disabled = false;
	        form.identificadorInformacaoArquivo[1].disabled = false;

	        form.setorComercialOrigemCD.disabled = true;
	      	form.setorComercialDestinoCD.disabled = true;
	      	
	      	form.quadraInicial.disabled = true;
	      	form.quadraFinal.disabled = true; 	

	    	form.rotaInicial.disabled = true;
	      	form.rotaFinal.disabled = true; 
		}
	}

function validarArquivoSelecionado() {

	var form = document.forms[0];

	if (form.arquivoImoveis.value != "") {

		form.cobrancaGrupo.disabled = true;
		form.cobrancaGrupo.value = '-1';

		form.gerenciaRegional.disabled = true;   
	    form.gerenciaRegional.value = '-1'; 

	    form.unidadeNegocio.disabled = true;   
        form.unidadeNegocio.value = '-1'; 
            
		form.localidadeOrigemID.disabled = true;
		form.localidadeDestinoID.disabled = true;

        form.setorComercialOrigemCD.disabled = true;
        form.setorComercialDestinoCD.disabled = true;
        
        form.quadraInicial.disabled = true;
    	form.quadraFinal.disabled = true;
       
    	form.rotaInicial.disabled = true;
    	form.rotaFinal.disabled = true;
    	   		
	    form.idCliente.disabled = true;
	    form.idCliente.value = '';
	    
        form.clienteRelacaoTipo.disabled = true;
        form.codigoClienteSuperior.value = '';
        form.codigoClienteSuperior.disabled = true;

        form.identificadorInformacaoArquivo[0].disabled = false;
        form.identificadorInformacaoArquivo[1].disabled = false;
        
	} else {
		form.cobrancaGrupo.disabled = false;
		form.localidadeOrigemID.disabled = false;
		form.localidadeDestinoID.disabled = false;
        form.gerenciaRegional.disabled = false;   
        form.gerenciaRegional.value = '-1'; 
        form.unidadeNegocio.disabled = false;   
        form.unidadeNegocio.value = '-1';      
        form.idCliente.disabled = false;
        form.codigoClienteSuperior.disabled = false;

        form.identificadorInformacaoArquivo[0].disabled = false;
        form.identificadorInformacaoArquivo[1].disabled = false;
	}
}



function verificarCobrancaAtividade(){
///document.forms[0].cobrancaAtividade.options[document.forms[0].cobrancaAtividade.selectedIndex].id
    var form = document.forms[0];
	if(form.cobrancaAtividade.value == 1){
///		form.executarComando.disabled = true;	
	}
}


function validarHabilitarCampo(){

	var form = document.forms[0];
	
	if(form.dataRealizacao==undefined || form.dataRealizacao.value == ''){

		//habilitar(document.forms[0].cobrancaAtividadeIndicadorExecucao.value);
		//validarGrupoCobranca(0);
		validarCliente(0);
		
		validarClienteSuperior(1);
		desabilitarLocalidadeCobrancaAcao();

		validarGrupoCobrancaParaBloquear();

		validarGerenciaRegionalParaBloquear();

		validarUnidadeNegocioParaBloquear();
		
		verificarCampoPrazoExecucao();

		//habilitacaoCampoRota();
		validarClienteParaBloquear();

		validarLocalidadeSetorRotaParaBloquear();
	
		var ok = 1;
		if(form.localidadeOrigemID.value != ""){
			ok = 2;
		}
		if(form.localidadeDestinoID.value != ""){
			ok = 2;
		}
		if(form.idCliente.value != ""){
			ok = 2;
		}
		if(form.codigoClienteSuperior.value != ""){
		  ok = 2;
		}
		if(form.gerenciaRegional.value != "-1"){
			ok = 2;
		}
		if(form.cobrancaGrupo.value != "-1"){
			ok = 2;
		}
		if(form.unidadeNegocio.value != "-1"){
			ok = 2;
		}
		
		if(ok == 1){
			form.cobrancaGrupo.disabled = false;
		    form.gerenciaRegional.disabled = false;		    
		    form.unidadeNegocio.disabled = false;		    
			form.localidadeOrigemID.readOnly = false;
			form.localidadeDestinoID.readOnly = false;
			form.setorComercialOrigemCD.readOnly = false;
		    form.setorComercialDestinoCD.readOnly = false;
		    form.quadraInicial.disabled = false;
    	    form.quadraFinal.disabled = false;
		    form.rotaInicial.readOnly = false;
    	    form.rotaFinal.readOnly = false;
        	form.nomeCliente.value = ""
        	form.idCliente.value = ""
        	form.idCliente.readOnly = false;
        	form.clienteRelacaoTipo.value = "";
        	form.clienteRelacaoTipo.disabled = false;
        	validaArrecadador(); 
		}
	}
}



function executarcomando(){
	
	var form = document.forms[0];
	
	if(validateRequired(form) && testarCampoValorZero(document.ManterComandoAcaoCobrancaDetalhesActionForm.localidadeOrigemID, 'Localidade Inicial') 
    && testarCampoValorZero(document.ManterComandoAcaoCobrancaDetalhesActionForm.localidadeDestinoID, 'Localidade Final') 
	&& testarCampoValorZero(document.ManterComandoAcaoCobrancaDetalhesActionForm.setorComercialOrigemCD, 'Setor Comercial Inicial') 
	&& testarCampoValorZero(document.ManterComandoAcaoCobrancaDetalhesActionForm.setorComercialDestinoCD, 'Setor Comercial Final') 
 	&& testarCampoValorZero(document.ManterComandoAcaoCobrancaDetalhesActionForm.idCliente, 'Cliente') 
    && validateCaracterEspecial(form) 
	&& validateLong(form) 
	&& validarLocalidadeDiferentes()
	&& validarSetoresComerciaisDiferentes()
	&& validarRotasDiferentes()
	&& verificarSetoresComerciaisMenores()
	&& verificarLocalidadeMenores()
	&& tratarExecutarComando()){	
	    form.action =  '/gsan/manterComandoAcaoCobrancaEventualExecutarComandoAction.do'
		form.submit();
	}
}

function concluircomando(){
	var form = document.forms[0];
	

	if(validar() && validateRequired(form) && testarCampoValorZero(document.ManterComandoAcaoCobrancaDetalhesActionForm.localidadeOrigemID, 'Localidade Inicial') 
    && testarCampoValorZero(document.ManterComandoAcaoCobrancaDetalhesActionForm.localidadeDestinoID, 'Localidade Final') 
	&& testarCampoValorZero(document.ManterComandoAcaoCobrancaDetalhesActionForm.setorComercialOrigemCD, 'Setor Comercial Inicial') 
	&& testarCampoValorZero(document.ManterComandoAcaoCobrancaDetalhesActionForm.setorComercialDestinoCD, 'Setor Comercial Final') 
 	&& testarCampoValorZero(document.ManterComandoAcaoCobrancaDetalhesActionForm.idCliente, 'Cliente') 
    && validateCaracterEspecial(form)
	&& validateLong(form) 
	&& validarLocalidadeDiferentes()
	&& validarSetoresComerciaisDiferentes()
	&& validarQuadras()
	&& validarEmpresa()
	&& validarRotasDiferentes()
	&& verificarSetoresComerciaisMenores()
	&& verificarLocalidadeMenores() 
	&& validarInscricoes() && verificarAtividadeEmitir()
	&& validarCriterioCobranca()){
		
	form.action =  '/gsan/manterComandoAcaoCobrancaEventualConcluirAction.do'
	    form.submit(); 
	    
	} 
}

function tratarExecutarComando(){
	var form = document.forms[0];
	if(form.cobrancaAtividade.value == 3){
		alert("O Caso de [UC0000] Encerrar Atividade de Ação de Cobrança não foi implementado para esta iteração.")
		form.cobrancaAtividade.focus();
		return false;
	}
	return true;
}

function validarLocalidadeDiferentes(){
	var form = document.forms[0];
	
	if(form.localidadeOrigemID.value != '' && form.localidadeDestinoID.value == ''){
		alert("Informe Localidade Final.");
		form.localidadeDestinoID.focus();
		return false;
	}
	if(form.localidadeOrigemID.value == '' && form.localidadeDestinoID.value != ''){
		alert("Informe Localidade Inicial.");
		form.localidadeOrigemID.focus();
		return false;
	}
	return true;
	
}

function desabilitarLocalidadeCobrancaAcao(){
  	var form = document.forms[0];
  	if (form.idCliente.value != '' || form.codigoClienteSuperior.value != '' ){

      	form.cobrancaGrupo.value = "";	
      	form.cobrancaGrupo.disabled = true;
      	form.localidadeOrigemID.disabled = true;
      	form.localidadeDestinoID.disabled = true;
	  	form.setorComercialOrigemCD.disabled = true;
      	form.setorComercialDestinoCD.disabled = true;
      	form.gerenciaRegional.disabled = true;
      	form.unidadeNegocio.disabled = true;
      	form.quadraInicial.disabled = true;
      	form.quadraFinal.disabled = true; 
      	form.rotaInicial.disabled = true;
      	form.rotaFinal.disabled = true;  
      	form.arquivoImoveis.disabled = true;
      	form.arquivoImoveis.value = '';  
      	document.forms[0].identificadorInformacaoArquivo[0].disabled = true;
		document.forms[0].identificadorInformacaoArquivo[1].disabled = true;  
  	}else{
      	form.cobrancaGrupo.disabled = false;
      	form.localidadeOrigemID.disabled = false;
      	form.localidadeDestinoID.disabled = false;
      	form.gerenciaRegional.disabled = false;
      	form.unidadeNegocio.disabled = false;  
      	form.arquivoImoveis.disabled = false; 
      	document.forms[0].identificadorInformacaoArquivo[0].disabled = false;
		document.forms[0].identificadorInformacaoArquivo[1].disabled = false;
  	}
}


function validarSetoresComerciaisDiferentes(){
	var form = document.forms[0];
	
	if(form.setorComercialOrigemCD.value!= '' && form.setorComercialDestinoCD.value == ''){
		alert("Informe Setor Comercial Final.");
		form.setorComercialDestinoCD.focus();
		return false;
	}
	if(form.setorComercialOrigemCD.value == '' && form.setorComercialDestinoCD.value != ''){
		alert("Informe Setor Comercial Inicial.");
		form.setorComercialDestinoCD.focus();
		return false;
	}
	return true;
}

function validarClienteParaBloquear(){

	var form = document.forms[0];
	if(form.dataRealizacao==undefined || form.dataRealizacao.value == ''){
		if(form.idCliente.value != ""){
			form.cobrancaGrupo.disabled = true;
		    form.gerenciaRegional.disabled = true;
		    form.unidadeNegocio.disabled = true;		    
	    	form.localidadeOrigemID.readOnly = true;
			form.localidadeDestinoID.readOnly = true;
	        form.setorComercialOrigemCD.readOnly = true;
	        form.setorComercialDestinoCD.readOnly = true;
	        form.imagem.enabled = true;
	   		form.rotaInicial.readOnly = true;
	   		form.rotaFinal.readOnly = true;
		}
	}
}


function validarGrupoCobrancaSelecionado(){

	var form = document.forms[0];
		
	if(form.cobrancaGrupo.value != "-1"){
		form.gerenciaRegional.disabled = true;
		form.unidadeNegocio.disabled = true;		
    	form.localidadeOrigemID.readOnly = true;
		form.localidadeDestinoID.readOnly = true;
        form.setorComercialOrigemCD.readOnly = true;
        form.setorComercialDestinoCD.readOnly = true;
        form.imagem.enabled = true;
        form.quadraInicial.disabled = true;
  		form.quadraFinal.disabled = true;
   		form.rotaInicial.readOnly = true;
  		form.rotaFinal.readOnly = true;
        form.nomeCliente.value = ""
        form.idCliente.value = ""
        form.idCliente.readOnly = true;
        form.arquivoImoveis.value = "";
        form.arquivoImoveis.disabled = true;
        form.identificadorInformacaoArquivo[0].disabled = true;
        form.identificadorInformacaoArquivo[1].disabled = true;
        form.codigoClienteSuperior.value = '';
        form.codigoClienteSuperior.disabled = true;
        form.clienteRelacaoTipo.value = "";
        form.clienteRelacaoTipo.disabled = true;
        
	}else{
		form.gerenciaRegional.disabled = false;
		form.unidadeNegocio.disabled = false;		
    	form.localidadeOrigemID.disabled = false;
		form.localidadeDestinoID.disabled = false;
        form.imagem.enabled = false;
        form.nomeCliente.value = ""
        form.idCliente.value = ""
        form.idCliente.disabled = false;
        form.clienteRelacaoTipo.value = "";
        form.arquivoImoveis.disabled = false;
        form.identificadorInformacaoArquivo[0].disabled = false;
        form.identificadorInformacaoArquivo[1].disabled = false;
        form.codigoClienteSuperior.disabled = false;

        habilitaDesabilitaSetorComercialRota();
	}
}

function validarGerenciaRegionalSelecionado(){

	var form = document.forms[0];
	
	if(form.gerenciaRegional.value != "-1"){
		form.cobrancaGrupo.disabled = true;
    	form.localidadeOrigemID.disabled = true;
		form.localidadeDestinoID.disabled = true;
        form.setorComercialOrigemCD.disabled = true;
        form.setorComercialDestinoCD.disabled = true;
        form.unidadeNegocio.disabled = true;
        form.imagem.disabled = true;
        form.quadraInicial.disabled = true;
   		form.quadraFinal.disabled = true;
   		form.rotaInicial.disabled = true;
   		form.rotaFinal.disabled = true;
        form.nomeCliente.value = ""
        form.idCliente.value = ""
        form.idCliente.disabled = true;
        form.codigoClienteSuperior.value = '';
        form.codigoClienteSuperior.disabled = true;
        form.clienteRelacaoTipo.value = "";
        form.clienteRelacaoTipo.disabled = true;

        form.arquivoImoveis.value = "";    
        form.arquivoImoveis.disabled = true;

        form.identificadorInformacaoArquivo[0].disabled = true;
        form.identificadorInformacaoArquivo[1].disabled = true;
        
	}else{
		form.cobrancaGrupo.disabled = false;
    	form.localidadeOrigemID.disabled = false;
		form.localidadeDestinoID.disabled = false;
        form.setorComercialOrigemCD.disabled = false;
        form.setorComercialDestinoCD.disabled = false;
        form.unidadeNegocio.disabled = false;        
        form.imagem.enabled = false;
        
        habilitaDesabilitaSetorComercialRota();
        
        form.nomeCliente.value = "";
        form.idCliente.value = "";
        form.idCliente.disabled = false;
        form.clienteRelacaoTipo.value = "";
        form.codigoClienteSuperior.disabled = false;

        form.arquivoImoveis.value = "";    
        form.arquivoImoveis.disabled = false;

        form.identificadorInformacaoArquivo[0].disabled = false;
        form.identificadorInformacaoArquivo[1].disabled = false;
	}
}


function validarUnidadeNegocioSelecionado(){

	var form = document.forms[0];
	
	if(form.unidadeNegocio.value != "-1"){
		form.cobrancaGrupo.disabled = true;
    	form.localidadeOrigemID.disabled = true;
		form.localidadeDestinoID.disabled = true;
        form.setorComercialOrigemCD.disabled = true;
        form.setorComercialDestinoCD.disabled = true;
        form.gerenciaRegional.disabled = true;        
        form.imagem.disabled = true;
        form.quadraInicial.disabled = true;
   		form.quadraFinal.disabled = true;
   		form.rotaInicial.disabled = true;
   		form.rotaFinal.disabled = true;
        form.nomeCliente.value = ""
        form.idCliente.value = ""
        form.idCliente.disabled = true;
        form.clienteRelacaoTipo.value = "";
        form.clienteRelacaoTipo.disabled = true;
        form.codigoClienteSuperior.value = '';
        form.codigoClienteSuperior.disabled = true;

        form.arquivoImoveis.value = "";    
        form.arquivoImoveis.disabled = true;
        form.identificadorInformacaoArquivo[0].disabled = true;
        form.identificadorInformacaoArquivo[1].disabled = true;
	}else{
		form.cobrancaGrupo.disabled = false;
    	form.localidadeOrigemID.disabled = false;
		form.localidadeDestinoID.disabled = false;
   		form.gerenciaRegional.disabled = false;
        form.imagem.enabled = false;
        form.nomeCliente.value = ""
        form.idCliente.value = ""
        form.idCliente.disabled = false;
        form.clienteRelacaoTipo.value = "";
        form.codigoClienteSuperior.disabled = false;

        form.arquivoImoveis.value = "";    
        form.arquivoImoveis.disabled = false;
        form.identificadorInformacaoArquivo[0].disabled = false;
        form.identificadorInformacaoArquivo[1].disabled = false;

        habilitaDesabilitaSetorComercialRota();
	}
}

function habilitaDesabilitaSetorComercialRota(){

	if(document.forms[0].localidadeOrigemID.value != ''){
		
		document.forms[0].setorComercialOrigemCD.disabled = false;
		document.forms[0].setorComercialDestinoCD.disabled = false;
		
	}else{
		document.forms[0].setorComercialOrigemCD.disabled = true;
		document.forms[0].setorComercialOrigemCD.value = '';
		
		document.forms[0].setorComercialDestinoCD.disabled = true;
		document.forms[0].setorComercialDestinoCD.value = '';
	}

	if(document.forms[0].setorComercialOrigemCD.value != ''){
		
		document.forms[0].quadraInicial.disabled = false;
		document.forms[0].quadraFinal.disabled = false;
		document.forms[0].rotaInicial.disabled = false;
		document.forms[0].rotaFinal.disabled = false;
		
	}else{
		document.forms[0].quadraInicial.disabled = true;
		document.forms[0].quadraInicial.value = '';		
		document.forms[0].quadraFinal.disabled = true;
		document.forms[0].quadraFinal.value = '';
		
		document.forms[0].rotaInicial.disabled = true;
		document.forms[0].rotaInicial.value = '';		
		document.forms[0].rotaFinal.disabled = true;
		document.forms[0].rotaFinal.value = '';
	}
	
}

function validarQuadras(){
	var form = document.forms[0];
	
    if(form.quadraInicial != undefined){
		if(form.quadraInicial.value!= '' && form.quadraFinal.value == ''){
			alert("Informe Quadra Final.");
			form.quadraFinal.focus();
			return false;
		}
		if(form.quadraInicial.value == '' && form.quadraFinal.value != ''){
			alert("Informe Quadra Inicial.");
			form.quadraInicial.focus();
			return false;
		}
		if(form.quadraInicial.value != '' && form.quadraFinal.value != ''){
			var quadraInicialInt = parseInt(form.quadraInicial.value);
			var quadraFinalInt = parseInt(form.quadraFinal.value);

			if(quadraFinalInt < quadraInicialInt){
				alert("A Quadra Final não pode ser menor que a Quadra Inicial.");
				form.quadraFinal.focus();
				return false;
			}
		}
		
	}
	return true;
}

function validarEmpresa(){
	var form = document.forms[0];
	var isCobrancaAdministrativa = '<%=request.getAttribute("isCobrancaAdministrativa")%>';

	if(isCobrancaAdministrativa == "true" && form.idEmpresa.value == ""){
		alert("Informe a empresa.");
		return false;
	} else{
		return true;
	}	
}

function validarRotasDiferentes(){
	var form = document.forms[0];
	
    if(form.rotaInicial != undefined){
		if(form.rotaInicial.value!= '' && form.rotaFinal.value == ''){
			alert("Informe Rota Final.");
			form.rotaFinal.focus();
			return false;
		}
		if(form.rotaInicial.value == '' && form.rotaFinal.value != ''){
			alert("Informe Rota Inicial.");
			form.rotaInicial.focus();
			return false;
		}
	}
	return true;
}

function verificarSetoresComerciaisMenores(){
	
	var form = document.forms[0];
	if(form.setorComercialOrigemCD.value != '' && form.setorComercialDestinoCD.value != ''){
		if(form.setorComercialDestinoCD.value < form.setorComercialOrigemCD.value){
			alert("Setor Comercial Final menor que Setor Comercial Inicial");	
			form.setorComercialDestinoCD.focus();
			return false;
		}else{
			return true;
		}
	
	}else{
		return true;
	}
}

function verificarLocalidadeMenores(){
	var form = document.forms[0];
	
	if(form.localidadeOrigemID.value != '' && form.localidadeDestinoID.value != ''){
		if(form.localidadeDestinoID.value < form.localidadeOrigemID.value){
			alert("Localidade Final menor que Localidade Inicial");	
			form.localidadeDestinoID.focus();
			return false;
		}else{
			return true;
		}
	
	}else{
		return true;
	}
}



function validarCriterios(valor){

	var form = document.forms[0];

	if(valor == 1){ //validar ação cobrança
		form.action =  '/gsan/exibirManterComandoAcaoCobrancaDetalhesAction.do?validarCriterio=SIM&validar=Acao'
    	form.submit();
    }else if(valor == 2){//validar cobrança atividade
		form.action =  '/gsan/exibirManterComandoAcaoCobrancaDetalhesAction.do?validarCriterio=SIM&validar=Atividade'
    	form.submit();
    	
    }
}

function verificar(){

	var form = document.forms[0];
	
	//if(form.cobrancaAtividadeIndicadorExecucao.value != 1){
	///	form.executarComando.disabled = true;	
	//}else if(form.cobrancaAtividadeIndicadorExecucao.value != 1){
		///form.executarComando.disabled = false;	
	//}
}

function habilitar(valor){

	var form = document.forms[0];
	//form.cobrancaAtividadeIndicadorExecucao.value = valor;	
	if(valor == 1){//indicador de rota
		form.concluir.disabled = false;
		form.Avancar.disabled = true;
		form.indicador[0].checked = true;
	}else if(valor == 2){//indicador de comando
		form.concluir.disabled  = true;
		form.Avancar.disabled = false;	
		form.indicador[1].checked = true;	
	}else if(valor == ""){
		form.concluir.disabled = false;
		form.Avancar.disabled = true;
		form.indicador[0].checked = true;
	}
}

function desabilitarCobrancaGrupo(){
	
	var form = document.forms[0];
	form.cobrancaGrupo.disabled = true;
	form.cobrancaGrupo.value = "";
}

function validarGrupoCobranca(visulizar){

	var form = document.forms[0];
	
///	if(form.cobrancaGrupo.value != ""){
		var ok = true;
		if(form.gerenciaRegional.value != "-1"){
			ok = false;
		}
		if(form.unidadeNegocio.value != "-1"){
			ok = false;
		}
		if(form.localidadeOrigemID.value != ""){
			ok = false;
		}
		if(form.localidadeDestinoID.value != ""){
			ok = false;
		}
		if(ok == true){
			if(form.cobrancaGrupo.value != ""){
				form.gerenciaRegional.disabled = true;
				form.unidadeNegocio.disabled = true;				
		    	form.localidadeOrigemID.readOnly = true;
				form.localidadeDestinoID.readOnly = true;
		        form.setorComercialOrigemCD.readOnly = true;
		        form.setorComercialDestinoCD.readOnly = true;
		        form.imagem.enabled = true;
			    if(form.rotaInicial != undefined){
		    	   		form.rotaInicial.readOnly = true;
		    	   		form.rotaFinal.readOnly = true;
			    }
			}else{
				form.gerenciaRegional.disabled = false;
				form.unidadeNegocio.disabled = false;				
		    	form.localidadeOrigemID.readOnly = false;
				form.localidadeDestinoID.readOnly = false;
		        form.setorComercialOrigemCD.readOnly = false;
		        form.setorComercialDestinoCD.readOnly = false;
		        form.imagem.enabled = false;
			    if(form.rotaInicial != undefined){
		    	   		form.rotaInicial.readOnly = false;
		    	   		form.rotaFinal.readOnly = false;
			    }	
			}
		}else{
			if(visulizar == 1){
				alert("Gerência Regional/Localidade/Setor Comercial/Rota informados");
				form.cobrancaGrupo.value = "";
			}
		}
	///}	
}

function validarGrupoCobrancaParaBloquear(){

	var form = document.forms[0];
	
	if(form.dataRealizacao==undefined || form.dataRealizacao.value == ''){
	
		var ok = 1;
		if(form.localidadeOrigemID.value != ""){
			ok = 2;
		}
		if(form.localidadeDestinoID.value != ""){
			ok = 2;
		}
		if(form.idCliente.value != ""){
			ok = 2;
		}
		if(form.codigoClienteSuperior.value != ""){
		  ok = 2;
		}
		if(form.unidadeNegocio.value != "-1"){
			ok = 2;
		}
		if(form.gerenciaRegional.value != "-1"){
			ok = 2;
		}
		if(ok == 1){
///			form.cobrancaGrupo.disabled = true;
		    form.gerenciaRegional.disabled = true;
		    form.unidadeNegocio.disabled = true;		    
			form.localidadeOrigemID.readOnly = true;
			form.localidadeDestinoID.readOnly = true;
			form.setorComercialOrigemCD.readOnly = true;
		    form.setorComercialDestinoCD.readOnly = true;
		    form.rotaInicial.readOnly = true;
    	    form.rotaFinal.readOnly = true;
        	form.nomeCliente.value = ""
        	form.idCliente.value = ""
        	form.idCliente.readOnly = true;
        	form.clienteRelacaoTipo.value = "";
        	form.clienteRelacaoTipo.disabled = true;
        	form.codigoClienteSuperior.value = '';
            form.codigoClienteSuperior.disabled = true;
		}
	}
}

function validarGerenciaRegionalParaBloquear(){

	var form = document.forms[0];
	
if(form.dataRealizacao==undefined || form.dataRealizacao.value == ''){
	
		var ok = 1;
		if(form.localidadeOrigemID.value != ""){
			ok = 2;
		}
		if(form.localidadeDestinoID.value != ""){
			ok = 2;
		}
		if(form.idCliente.value != ""){
			ok = 2;
		}
		
		if(form.codigoClienteSuperior.value != ""){
		  ok = 2;
		}

		if(form.unidadeNegocio.value != "-1"){
			ok = 2;
		}

		if(form.cobrancaGrupo.value != "-1"){
			ok = 2;
		}
		
		if(ok == 1){
			form.cobrancaGrupo.disabled = true;
		    form.unidadeNegocio.disabled = true;		    
			form.localidadeOrigemID.readOnly = true;
			form.localidadeDestinoID.readOnly = true;
			form.setorComercialOrigemCD.readOnly = true;
		    form.setorComercialDestinoCD.readOnly = true;
		    form.quadraInicial.disabled = true;
    	    form.quadraFinal.disabled = true;
		    form.rotaInicial.readOnly = true;
    	    form.rotaFinal.readOnly = true;
        	form.nomeCliente.value = ""
        	form.idCliente.value = ""
        	form.idCliente.readOnly = true;
        	form.clienteRelacaoTipo.value = "";
        	form.clienteRelacaoTipo.disabled = true;
        	form.clienteRelacaoTipo.value = "";
        	form.clienteRelacaoTipo.disabled = true;
		}
		
	}
}

function validarUnidadeNegocioParaBloquear(){

	var form = document.forms[0];
	
	if(form.dataRealizacao==undefined || form.dataRealizacao.value == ''){
	
		var ok = 1;
		if(form.localidadeOrigemID.value != ""){
			ok = 2;
		}
		if(form.localidadeDestinoID.value != ""){
			ok = 2;
		}
		if(form.idCliente.value != ""){
			ok = 2;
		}
		
		if(form.codigoClienteSuperior.value != ""){
		  ok = 2;
		}
		
		if(form.gerenciaRegional.value != "-1"){
			ok = 2;
		}
		if(form.cobrancaGrupo.value != "-1"){
			ok = 2;
		}
		
		if(ok == 1){
			form.cobrancaGrupo.disabled = true;
		    form.gerenciaRegional.disabled = true;		    
			form.localidadeOrigemID.readOnly = true;
			form.localidadeDestinoID.readOnly = true;
			form.setorComercialOrigemCD.readOnly = true;
		    form.setorComercialDestinoCD.readOnly = true;
		    form.quadraInicial.disabled = true;
    	    form.quadraFinal.disabled = true;
		    form.rotaInicial.readOnly = true;
    	    form.rotaFinal.readOnly = true;
        	form.nomeCliente.value = ""
        	form.idCliente.value = ""
        	form.idCliente.readOnly = true;
        	form.clienteRelacaoTipo.value = "";
        	form.clienteRelacaoTipo.disabled = true;
		}
	}
}

function validarLocalidadeSetorRotaParaBloquear(){

	var form = document.forms[0];
	
	if(form.dataRealizacao==undefined || form.dataRealizacao.value == ''){
	 var ok = 1;
		if(form.localidadeOrigemID.value != ""){
			ok = 2;
		}
		if(form.localidadeDestinoID.value != ""){
			ok = 2;
		}
		
		if(ok == 2){
			form.cobrancaGrupo.disabled = true;
		    form.gerenciaRegional.disabled = true;
		    form.unidadeNegocio.disabled = true;		    
        	form.nomeCliente.value = ""
        	form.idCliente.value = ""
        	form.idCliente.readOnly = true;
        	form.clienteRelacaoTipo.value = "";
        	form.clienteRelacaoTipo.disabled = true;
        	form.clienteRelacaoTipo.value = "";
        	form.clienteRelacaoTipo.disabled = true;
		}
	}
}




function validarLocalidade(){

	var form = document.forms[0];

	if(form.localidadeOrigemID.value != form.localidadeDestinoID.value){
		
	    form.setorComercialDestinoCD.value = "";
	    form.setorComercialDestinoID.value = "";		   
 	    form.nomeSetorComercialDestino.value = "";
		form.setorComercialOrigemCD.value = "";
		form.setorComercialOrigemID.value = "";
		form.nomeSetorComercialOrigem.value = "";
		alert("Para informar o Setor Comercial, a Localidade Inicial e Final precisam ser iguais");
	}

}

function receberRota(codigoRota,destino) {
 	  var form = document.forms[0];
	if(destino == "inicial"){
		form.rotaInicial.value = codigoRota;		
		form.rotaFinal.value = codigoRota;				
	}else if(destino == "final"){
		form.rotaFinal.value = codigoRota;		
	}

}



function limparDestino(tipo){

	var form = document.forms[0];

	switch(tipo){
		case 1: //De localidade pra baixo
			form.localidadeDestinoID.value = "";
			form.nomeLocalidadeDestino.value = "";		
			form.setorComercialOrigemCD.value = "";
			form.setorComercialOrigemID.value = "";
			form.nomeSetorComercialOrigem.value = "";
		    form.setorComercialDestinoCD.value = "";
		    form.nomeSetorComercialDestino.value = "";
		    form.nomeLocalidadeOrigem.value = "";
		    desabilitarCobrancaAcao();
		case 2: //De setor para baixo
   	       form.setorComercialDestinoCD.value = "";
		   form.setorComercialDestinoID.value = ""; 
		   form.nomeSetorComercialOrigem.value = "";	
		   form.nomeSetorComercialDestino.value = "";	
		   form.quadraInicial.value = "";
   	   	   form.quadraFinal.value = "";
	   	   form.rotaInicial.value = "";
   	   	   form.rotaFinal.value = "";	
		case 3:
	   	   	form.rotaFinal.value = "";
		case 4:
	   	   	form.quadraFinal.value = "";	
	}
}

function limparOrigem(tipo,objetoReferencia){
	var form = document.forms[0];
	
	if(objetoReferencia.disabled == false){

	switch(tipo){
		case 1: //De localidara pra baixo

			form.cobrancaGrupo.disabled = false;
			form.gerenciaRegional.disabled = false;
			form.unidadeNegocio.disabled = false;	
			
			form.codigoClienteSuperior.disabled = false;			
	        form.idCliente.disabled = false;
	        form.clienteRelacaoTipo.disabled = true;

	        form.arquivoImoveis.value = "";    
	        form.arquivoImoveis.disabled = false;
	        document.forms[0].identificadorInformacaoArquivo[0].disabled = false;
			document.forms[0].identificadorInformacaoArquivo[1].disabled = false;
			
			form.localidadeOrigemID.value = "";
			form.nomeLocalidadeOrigem.value = "";
			
			form.localidadeDestinoID.value = "";
			form.nomeLocalidadeDestino.value = "";

			form.setorComercialOrigemCD.disabled = true;
			form.setorComercialDestinoCD.disabled = true;

			form.localidadeDestinoID.disabled = false;
			
		case 2: //De setor para baixo
		   form.setorComercialOrigemCD.value = "";
		   form.setorComercialOrigemID.value = "";
		   form.nomeSetorComercialOrigem.value = "";
	   
		   form.setorComercialDestinoCD.value = "";
		   form.setorComercialDestinoID.value = "";		   
		   form.nomeSetorComercialDestino.value = "";
		   
		   form.quadraInicial.value = "";
		   form.quadraInicial.disabled = true;
   		   form.quadraFinal.value = "";		 
		   form.quadraFinal.disabled = true; 
		   
		   form.rotaInicial.value = "";
		   form.rotaInicial.disabled = true;
		   
   		   form.rotaFinal.value = "";		 
		   form.rotaFinal.disabled = true;  
			   
		case 3:				
			form.rotaInicial.value = "";
	   	   	form.rotaFinal.value = "";	

		case 4:
			form.quadraInicial.value = "";
	   	   	form.quadraFinal.value = "";   		      
	}
	}
}

function limparBorrachaDestino(tipo,objetoReferencia){
	var form = document.forms[0];

   if(objetoReferencia.disabled == false){

	switch(tipo){
		case 1: //De localidade pra baixo
			form.localidadeDestinoID.value = "";
			form.nomeLocalidadeDestino.value = "";					
			form.setorComercialOrigemCD.value = "";
			form.setorComercialOrigemID.value = "";
			form.nomeSetorComercialOrigem.value = "";
    		form.setorComercialDestinoCD.value = "";

		case 2: //De setor para baixo		   
		   form.setorComercialDestinoID.value = ""; 
		   form.nomeSetorComercialDestino.value = "";		   
   		   form.setorComercialDestinoCD.value = "";
   		   form.quadraInicial.value = "";
		   form.quadraFinal.value = "";
		   form.rotaInicial.value = "";
    	   form.rotaFinal.value = "";
	}
	}
}

function habilitarQuadraRota(){
	var form = document.forms[0];

	form.quadraInicial.value = "";
	form.quadraFinal.value = "";
	    
	form.quadraInicial.value = "";
	form.quadraFinal.value = "";
	
	if(form.setorComercialDestinoCD.value == form.setorComercialOrigemCD.value){

	   form.quadraInicial.readOnly = false;
 	   form.quadraFinal.readOnly = false;	
		
	   form.rotaInicial.readOnly = false;
 	   form.rotaFinal.readOnly = false;	
	}else{

	   form.quadraInicial.readOnly = true;
 	   form.quadraFinal.readOnly = true;
	   form.rotaInicial.readOnly = true;
   	   form.rotaFinal.readOnly = true;	

	}
}


function limparCriterioCobranca(){
	   var form = document.forms[0];
	   form.cobrancaCriterio.value = "";
	   form.nomeCobrancaCriterio.value = "";
	}

function recuperarDadosQuatroParametros(idRegistro, descricaoRegistro, codigoRegistro, tipoConsulta) {

	var form = document.forms[0];

	if (tipoConsulta == 'setorComercialOrigem') {
      form.setorComercialOrigemCD.value = codigoRegistro;
      form.setorComercialOrigemID.value = idRegistro;
      form.action = 'exibirManterComandoAcaoCobrancaDetalhesAction.do?objetoConsulta=2&inscricaoTipo=origem'
      form.submit();
      
	  form.nomeSetorComercialOrigem.value = descricaoRegistro;
	  form.nomeSetorComercialOrigem.style.color = "#000000";
	  if(form.setorComercialDestinoCD.value == ""){
	      form.setorComercialDestinoCD.value = codigoRegistro;
    	  form.setorComercialDestinoID.value = idRegistro;
		  form.nomeSetorComercialDestino.value = descricaoRegistro;
		  form.nomeSetorComercialDestino.style.color = "#000000"; 
	  }
	  
	  form.quadraInicial.disabled = false;
  	  form.quadraFinal.disabled = false;	  
	}

	if (tipoConsulta == 'setorComercialDestino') {
      form.setorComercialDestinoCD.value = codigoRegistro;
   	  form.setorComercialDestinoID.value = idRegistro;
      form.action = 'exibirManterComandoAcaoCobrancaDetalhesAction.do?objetoConsulta=2&inscricaoTipo=destino'
   	  form.submit();

	  form.nomeSetorComercialDestino.value = descricaoRegistro;
	  form.nomeSetorComercialDestino.style.color = "#000000"; 
	}else if (tipoConsulta == 'quadraOrigem') {
		
  		form.quadraInicial.value = codigoRegistro;
  		form.rotaInicial.value = "";
  		form.rotaFinal.value = "";
  		
  		if(form.quadraFinal.value == ""){
      		form.quadraFinal.value = codigoRegistro;
  		}
  		
	}else if (tipoConsulta == 'quadraDestino') {

  		form.quadraFinal.value = codigoRegistro;
  		form.rotaInicial.value = "";
  		form.rotaFinal.value = "";
  		
	}else if((tipoConsulta == 'rota') && (codigoRegistro == 'inicial')){

		var codigo = obterCodigoRota(idRegistro);

		form.rotaInicial.value = codigo;
		form.quadraInicial.value = "";
  		form.quadraFinal.value = "";
		
		replicarRota();			

	}else if((tipoConsulta == 'rota') && (codigoRegistro == 'final')){

		var codigo = obterCodigoRota(idRegistro);
		form.quadraInicial.value = "";
  		form.quadraFinal.value = "";
		
		form.rotaFinal.value = codigo;

	}
}

function obterCodigoRota(idRegistro){

	var codigoRota = "";
	
	AjaxService.obterCodigoRota(idRegistro, {callback: 
		function(id) {

			codigoRota = id;	
		
		}, async: false} 
	);

	return codigoRota;
	
}

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

    var form = document.forms[0];
    if (tipoConsulta == 'cobrancaAcaoAtividadeComando') {
      form.action = 'exibirManterComandoAcaoCobrancaDetalhesAction.do?idCobrancaAcaoAtividadeComando='+codigoRegistro+'&cobrancaAcaoAtividadeComandoPesquisado=true'
      form.submit();
    }

	if (tipoConsulta == 'localidadeOrigem') {
      form.localidadeOrigemID.value = codigoRegistro;
	  form.nomeLocalidadeOrigem.value = descricaoRegistro;
      form.action = 'exibirManterComandoAcaoCobrancaDetalhesAction.do?objetoConsulta=1&inscricaoTipo=origem'
      form.submit();
	  form.nomeLocalidadeOrigem.style.color = "#000000";
	  if(form.localidadeDestinoID.value == ""){
	      form.localidadeDestinoID.value = codigoRegistro;
		  form.nomeLocalidadeDestino.value = descricaoRegistro;
		  form.nomeLocalidadeDestino.style.color = "#000000";
	  }
	}

	if (tipoConsulta == 'localidade') {
    	form.localidadeDestinoID.value = codigoRegistro;
        form.action = 'exibirManterComandoAcaoCobrancaDetalhesAction.do?objetoConsulta=1&inscricaoTipo=destino'
        form.submit();
    	form.nomeLocalidadeDestino.value = descricaoRegistro;
 		form.nomeLocalidadeDestino.style.color = "#000000";    		
	}
	
    if (tipoConsulta == 'cliente') {
	 	if (form.tipoPesquisa.value != null && form.tipoPesquisa.value == 'clienteSuperior') {
	 		form.codigoClienteSuperior.value = codigoRegistro;
      		form.nomeClienteSuperior.value = descricaoRegistro;
      		form.nomeClienteSuperior.style.color = "#000000";
      		validarClienteSuperior(1);	
      		desabilitarLocalidadeCobrancaAcao();
	 	} else {
	        form.idCliente.value = codigoRegistro;
      		form.nomeCliente.value = descricaoRegistro;
      		form.nomeCliente.style.color = "#000000";
     	 	form.clienteRelacaoTipo.disabled = false;
     	 	validarCliente(1);	
     	 	desabilitarLocalidadeCobrancaAcao()
        }
        
     }

	if (tipoConsulta == 'criterioCobranca') {
		
  		form.cobrancaCriterio.value = codigoRegistro;
  		form.nomeCobrancaCriterio.value = descricaoRegistro;
	  	form.nomeCobrancaCriterio.style.color = "#000000";
	}

}

function limparCliente(){
  	  var form = document.forms[0];
       form.nomeCliente.value = ""
       form.idCliente.value = ""
       form.clienteRelacaoTipo.value = "";
       form.clienteRelacaoTipo.disabled = true;
       form.nomeClienteSuperior.value = "";
       form.codigoClienteSuperior.disabled = false;
}

function avancar(){
	var form = document.forms[0]; 
		
	if(validateRequired(form) && testarCampoValorZero(document.ManterComandoAcaoCobrancaDetalhesActionForm.localidadeOrigemID, 'Localidade Inicial') 
	    && testarCampoValorZero(document.ManterComandoAcaoCobrancaDetalhesActionForm.localidadeDestinoID, 'Localidade Final') 
		&& testarCampoValorZero(document.ManterComandoAcaoCobrancaDetalhesActionForm.setorComercialOrigemCD, 'Setor Comercial Inicial') 
		&& testarCampoValorZero(document.ManterComandoAcaoCobrancaDetalhesActionForm.setorComercialDestinoCD, 'Setor Comercial Final') 
	 	&& testarCampoValorZero(document.ManterComandoAcaoCobrancaDetalhesActionForm.idCliente, 'Cliente') 
	    && validateCaracterEspecial(form) 
		&& validateLong(form) 
		&& validarLocalidadeDiferentes()
		&& validarSetoresComerciaisDiferentes()
		&& validarRotasDiferentes()
		&& verificarSetoresComerciaisMenores()
		&& verificarLocalidadeMenores()
		&& validarInscricoes()){	
			form.action =  '/gsan/exibirManterComandoAcaoCobrancaEventualCriterioComandoAction.do?idCobrancaAtividade='+form.cobrancaAtividade.value+'&idCobrancaAcao='+form.cobrancaAcao.value
   			form.submit();
   	}
}

function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){


	if(objetoRelacionado.disabled != true && objetoRelacionado.readOnly != true){
		if (objeto == null || codigoObjeto == null){
			abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
		}
		else{
			if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
				alert(msg);
			}
			else{
				abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
			}
		}
	}
}

function voltar(){
	var formulario = document.forms[0]; 
	formulario.action =  'exibirManterComandoAcaoCobrancaAction.do?menu=sim'
	formulario.submit();
}

function mensagem(mensagem){
	if(mensagem.length > 0){
		alert(mensagem);
	}
}

function validarExistenciaLocalidadeInicial(){
	var form = document.forms[0]; 
	if(form.localidadeOrigemID.value == ""){
		form.localidadeDestinoID.value = "";
		alert("Informe Localidade Inicial.");
		form.localidadeOrigemID.focus();
	}
}

function validarExistenciaSetorComercialInicial(){
	var form = document.forms[0]; 
	if(form.setorComercialOrigemCD.value == ""){
		form.setorComercialDestinoCD.value = "";
		alert("Informe Setor Comercial Inicial.");
		form.setorComercialOrigemCD.focus();
	}
}


function habilitacaoCampoRota(){

	var form = document.forms[0];
	
	var setorComercialOrigem = trim(form.setorComercialOrigemCD.value);
	var setorComercialDestino = trim(form.setorComercialDestinoCD.value);
	
	if (setorComercialOrigem.length > 0 && setorComercialDestino.length > 0){
		
		if (setorComercialOrigem != setorComercialDestino){
			form.rotaInicial.readOnly = true;
			form.rotaFinal.readOnly = true;
		}
		else{
			form.rotaInicial.readOnly = false;
			form.rotaFinal.readOnly = false;
		}
		
	}
}

function desabilitaIntervaloDiferente(tipo){
	var form = document.forms[0];
	
	switch(tipo){
		case 1: //De localidade 
		    if(form.localidadeOrigemID.value != form.localidadeDestinoID.value){
		        form.setorComercialOrigemCD.value = "";
			 	form.setorComercialDestinoCD.value = "";
		        form.setorComercialOrigemCD.readOnly = true;
			 	form.setorComercialDestinoCD.readOnly = true;
			 	if(form.quadraInicial != undefined){
			 		form.quadraInicial.value = "";
				 	form.quadraFinal.value = "";
				 	form.quadraInicial.readOnly = true;
				 	form.quadraFinal.readOnly = true;
			 	}
			    if(form.rotaInicial != undefined){
			    	form.rotaInicial.value = "";
				 	form.rotaFinal.value = "";
				  	form.rotaInicial.readOnly = true;
				 	form.rotaFinal.readOnly = true;
             	}
			 
			  }else{
		        form.setorComercialOrigemCD.readOnly = false;
			 	form.setorComercialDestinoCD.readOnly = false;
			 	if(form.quadraInicial != undefined){
				 	form.quadraInicial.readOnly = false;
				 	form.quadraFinal.readOnly = false;
			 	}
			    if(form.rotaInicial != undefined){
				  	form.rotaInicial.readOnly = false;
				 	form.rotaFinal.readOnly = false;
             	}
			  }
			break;					
		case 2: //De setor Comercial		   
		    if(form.setorComercialOrigemCD.value != form.setorComercialDestinoCD.value){
		    	if(form.quadraInicial != undefined){
		    		form.quadraInicial.value = "";
				 	form.quadraFinal.value = "";
				  	form.quadraInicial.readOnly = true;
				 	form.quadraFinal.readOnly = true;
             	}
			    if(form.rotaInicial != undefined){
			    	form.rotaInicial.value = "";
				 	form.rotaFinal.value = "";
				  	form.rotaInicial.readOnly = true;
				 	form.rotaFinal.readOnly = true;
             	}
  			  }else{
  				if(form.quadraInicial != undefined){
				  	form.quadraInicial.readOnly = false;
		 			form.quadraFinal.readOnly = false;
		 		}
				   if(form.rotaInicial != undefined){
					  	form.rotaInicial.readOnly = false;
			 			form.rotaFinal.readOnly = false;
			 		}
			  }
			break;
		case 3://De Rota 

		   if(form.rotaInicial != undefined){
		   
		     if(form.rotaInicial.value != form.rotaFinal.value){
			  	form.rotaInicial.readOnly = true;
			 	form.rotaFinal.readOnly = true;
             	form.rotaInicial.value = "";
             	form.rotaFinal.value = "";
			  }else{
			  	form.rotaInicial.readOnly = false;
			 	form.rotaFinal.readOnly = false;
			  }

			}
			break;
		}
}

function replicarSetorComercial(){
	var form = document.forms[0];
	if(form.setorComercialDestinoCD.value == ""){
		form.setorComercialDestinoCD.value = form.setorComercialOrigemCD.value;
	}	
	if (form.setorComercialDestinoCD.value == form.setorComercialOrigemCD.value){
		habilitarQuadraRota();
	}
}

function replicarLocalidade(){
	var form = document.forms[0];
	if(form.localidadeDestinoID.value == ""){
		form.localidadeDestinoID.value = form.localidadeOrigemID.value;	
	}
	if(form.localidadeDestinoID.value == form.localidadeOrigemID.value){
		form.setorComercialOrigemCD.readOnly = false;
		form.setorComercialDestinoCD.readOnly = false;
	}
	
}

function replicarQuadra(){
	var form = document.forms[0];
	if(form.quadraFinal.value == ""){
		form.quadraFinal.value = form.quadraInicial.value;
	}	
}

function replicarRota(){
	var form = document.forms[0];
	if(form.rotaFinal.value == ""){
		form.rotaFinal.value = form.rotaInicial.value;	
	}
	
}

function pesquisarSetorComercial(destino){
	var form = document.forms[0];
	var msg = '';
	
	if(form.localidadeOrigemID.value == ""){
		   msg += 'Informe Localidade Inicial.\n';
	}
	if(form.localidadeDestinoID.value == ""){
		   msg += 'Informe Localidade Final.\n';
	}
	
	if( msg != '' ){
		   alert(msg);
   	}else{
		var msgDois = '';
		
	   if(form.localidadeOrigemID.value != form.localidadeDestinoID.value){
		   msgDois = 'Localidades diferentes.\n';
	   }

	   if( msgDois != '' ){
		   alert(msgDois);
		}else{
			abrirPopup('exibirPesquisarSetorComercialAction.do?idLocalidade='+form.localidadeOrigemID.value+'&tipo=setorComercialOrigem&indicadorUsoTodos=1',form.localidadeOrigemID.value,'Localidade da inscrição de origem', 400, 800);
		}
	}

}

function pesquisarRota(destino){
   var form = document.forms[0];
   var msg = '';
   if(form.localidadeOrigemID.value == ""){
	   msg = 'Informe Localidade Inicial.\n';
   }
   if(form.localidadeDestinoID.value == ""){
	   msg = msg+ 'Informe Localidade Final.\n';
   }
   if(form.setorComercialOrigemCD.value == ""){
	   msg = msg +'Informe Setor Comercial Inicial.\n';
   }
   if(form.setorComercialDestinoCD.value == ""){
	   msg = msg+ 'Informe Setor Comercial Final.\n';
   }

   if( msg != '' ){
	   alert(msg);
   }else{
		var msgDois = '';
		
	   if(form.localidadeOrigemID.value != form.localidadeDestinoID.value){
		   msgDois = 'Localidades diferentes.\n';
	   }
	   if(form.setorComercialOrigemCD.value != form.setorComercialDestinoCD.value){
		   msgDois = msgDois + 'Setores Comeriais diferentes.\n';
	   }
	   if( msgDois != '' ){
		   alert(msgDois);
		}else{
			abrirPopup('exibirPesquisarRotaAction.do?idLocalidade='+form.localidadeOrigemID.value+'&codigoSetorComercial='+form.setorComercialOrigemCD.value+'&destinoCampo='+destino+'&restringirPesquisa=true', 250, 495);
		}
	}
}

function validarInscricoes(){
   var form = document.forms[0];
   var msg = '';
   if(form.setorComercialOrigemCD.value != "" && form.localidadeOrigemID.value == ""){
	   msg = 'Informe Localidade Inicial.\n';
   }
   if(form.setorComercialDestinoCD.value != "" && form.localidadeDestinoID.value == ""){
	   msg = msg+ 'Informe Localidade Final.\n';
   }
   if(form.rotaInicial.value != "" && form.setorComercialOrigemCD.value == ""){
	   msg = msg +'Informe Setor Comercial Inicial.\n';
   }
   if(form.rotaFinal.value != "" &&form.setorComercialDestinoCD.value == ""){
	   msg = msg+ 'Informe Setor Comercial Final.\n';
   }

   if( msg != '' ){
   		alert(msg);
		return false;
   }else{
		return true;
   }
}

function verificarAtividadeEmitir(){
var form = document.forms[0];
 if(form.cobrancaAtividade.value == form.idAtividadeCobrancaEmitir.value){
  if(form.prazoExecucao.value == ''){
   alert("Informe prazo de execução");
   return false;
  }else{
   return true;
  }
 }else{
  return true;
 }
}

function verificarCampoPrazoExecucao(){
var form = document.forms[0];
if(form.dataRealizacao==undefined || form.dataRealizacao.value == ''){
 if(form.cobrancaAtividade != null && form.cobrancaAtividade.value != form.idAtividadeCobrancaEmitir.value){
  form.prazoExecucao.value = '';
  form.prazoExecucao.disabled = true; 
 }else{
  form.prazoExecucao.disabled = false; 
 }
}
}

function  validarAbrirPoupPesquisarClienteSuperior() {
	var form = document.forms[0];
	if (form.codigoClienteSuperior.disabled == false) {
		form.tipoPesquisa.value = 'clienteSuperior';
		chamarPopup('exibirPesquisarClienteAction.do?indicadorUsoTodos=1&limparForm=OK', 'clienteSuperior', null, null, 275, 480, '',form.codigoClienteSuperior.value);
	}	
}

function validarAbrirPoupPesquisarCliente(){
	var form = document.forms[0];	
	if(form.idCliente.disabled == false){
	    form.tipoPesquisa.value = 'cliente';
	    chamarPopup('exibirPesquisarClienteAction.do?indicadorUsoTodos=1&limparForm=OK', 'cliente', null, null, 275, 480, '',form.idCliente.value);
	}
	
}


function validarLimparClienteSuperior(){
	var form = document.forms[0];						
	if(form.codigoClienteSuperior.disabled == false){
		limparClienteSuperior();
	}
}

function limparClienteSuperior(){
  	  var form = document.forms[0];
      form.nomeClienteSuperior.value = ""
      form.codigoClienteSuperior.value = ""
      form.nomeCliente.value = "";
      form.idCliente.disabled = false;
}


function limparCliente(){
  	  var form = document.forms[0];
       form.nomeCliente.value = ""
       form.idCliente.value = ""
       form.clienteRelacaoTipo.value = "";
       form.clienteRelacaoTipo.disabled = true;
       form.nomeClienteSuperior.value = "";
       form.codigoClienteSuperior.disabled = false;
}

function validarClienteSuperior(apagar){
    	var form = document.forms[0];
    	if (form.codigoClienteSuperior.value != ''){
      		if(apagar == 1) form.clienteRelacaoTipo.value = "";	
      		form.clienteRelacaoTipo.disabled = true;
      		form.idCliente.disabled = true;
      		form.idCliente.value = "";
      		form.nomeCliente.value = "";
    	}else{
    		form.clienteRelacaoTipo.value = "";	
      		form.idCliente.disabled = false;
   	 	}
  	}
  	
function validarLimparCliente(){
	var form = document.forms[0];						
	if(form.idCliente.disabled == false){
		limparCliente();
	}
}

function verificaArquivo() {
	var form = document.forms[0];
	if (form.identificadorInformacaoArquivo[0].checked) {
		form.arquivoImoveis.disabled = false;
	} else {
		form.arquivoImoveis.disabled = true;
	}	
}

function extendeTabela(tabela,display){
	var form = document.forms[0];

	if(display){
			eval('layerHide'+tabela).style.display = 'none';
			eval('layerShow'+tabela).style.display = 'block';
	}else{
		eval('layerHide'+tabela).style.display = 'block';
			eval('layerShow'+tabela).style.display = 'none';
	}
}

function habilitaDesabilitaPrecedente() {

	var habilitaAcaoPrecedente = true;
	
	var form = document.forms[0];
	var obj = form.cobrancaAcao; 
	
	if (obj != null) {
		var idAcaoCobranca = obj.options[obj.selectedIndex].value;
			if (idAcaoCobranca != "-1") { 
				AjaxService.verificaAcaoPrecedente(idAcaoCobranca, {callback: 
					function(verificar) {
						if (verificar) {
							habilitaAcaoPrecedente = false;
						}
					}, async: false} );
			} 
		}	
		document.forms[0].acaoPrecedente.disabled = habilitaAcaoPrecedente;
	}

function habilitaDesabilitaParametrosAdicionais(){

	if(document.forms[0].dataRealizacao.value != ''){

		document.forms[0].cobrancaAcao.disabled = true;
		document.forms[0].acaoPrecedente.disabled = true;
		document.forms[0].tituloComandoPrecedente.disabled = true;
		document.forms[0].titulo.disabled = true;
		document.forms[0].descricaoSolicitacao.disabled = true;
		document.forms[0].cobrancaAtividade.disabled = true;
		document.forms[0].idEmpresa.disabled = true;
		document.forms[0].idClienteCombo.disabled = true;
		document.forms[0].prazoExecucao.disabled = false;
		document.forms[0].quantidadeMaximaDocumentos.disabled = true;
		document.forms[0].indicadorImoveisDebito[0].disabled = true;
		document.forms[0].indicadorImoveisDebito[1].disabled = true;
		document.forms[0].cobrancaCriterio.disabled = true;
		document.forms[0].indicadorCriterioComando[0].disabled = true;
		document.forms[0].indicadorCriterioComando[1].disabled = true;

		document.forms[0].cobrancaGrupo.disabled = true;
		document.forms[0].indicadorCriterioComando.disabled = true;
		document.forms[0].gerenciaRegional.disabled = true;
		document.forms[0].unidadeNegocio.disabled = true;
		document.forms[0].localidadeOrigemID.disabled = true;
		document.forms[0].setorComercialOrigemCD.disabled = true;
		document.forms[0].quadraInicial.disabled = true;
		document.forms[0].quadraFinal.disabled = true;
		document.forms[0].rotaInicial.disabled = true;
		document.forms[0].localidadeDestinoID.disabled = true;
		document.forms[0].setorComercialDestinoCD.disabled = true;
		document.forms[0].rotaFinal.disabled = true;
		document.forms[0].codigoClienteSuperior.disabled = true;
		document.forms[0].idCliente.disabled = true;
		document.forms[0].arquivoImoveis.disabled = true;
		document.forms[0].clienteRelacaoTipo.disabled = true;

		document.forms[0].identificadorInformacaoArquivo[0].disabled = true;
		document.forms[0].identificadorInformacaoArquivo[1].disabled = true;

		document.forms[0].indicadorGerarRelacaoDocumento[0].disabled = true;
		document.forms[0].indicadorGerarRelacaoDocumento[1].disabled = true;
		
	}else if(document.forms[0].desabilitarParametrosAdicionais.value == "true"){

		document.forms[0].cobrancaGrupo.disabled = true;
		document.forms[0].gerenciaRegional.disabled = true;
		document.forms[0].unidadeNegocio.disabled = true;
		document.forms[0].localidadeOrigemID.disabled = true;
		document.forms[0].setorComercialOrigemCD.disabled = true;
		document.forms[0].quadraInicial.disabled = true;
		document.forms[0].quadraFinal.disabled = true;
		document.forms[0].rotaInicial.disabled = true;
		document.forms[0].localidadeDestinoID.disabled = true;
		document.forms[0].setorComercialDestinoCD.disabled = true;
		document.forms[0].rotaFinal.disabled = true;
		document.forms[0].codigoClienteSuperior.disabled = true;
		document.forms[0].idCliente.disabled = true;
		document.forms[0].arquivoImoveis.disabled = true;
		document.forms[0].identificadorInformacaoArquivo[0].disabled = true;
		document.forms[0].identificadorInformacaoArquivo[1].disabled = true;
	}else{

		if(document.forms[0].cobrancaGrupo.value != '-1'){
			
			document.forms[0].cobrancaGrupo.disabled = false;
			
			document.forms[0].gerenciaRegional.disabled = true;
			document.forms[0].unidadeNegocio.disabled = true;
			document.forms[0].localidadeOrigemID.disabled = true;
			document.forms[0].localidadeDestinoID.disabled = true;
			document.forms[0].codigoClienteSuperior.disabled = true;
			document.forms[0].idCliente.disabled = true;
			document.forms[0].arquivoImoveis.disabled = true;
			document.forms[0].identificadorInformacaoArquivo[0].disabled = true;
			document.forms[0].identificadorInformacaoArquivo[1].disabled = true;
			habilitaDesabilitaSetorComercialRota();
			document.forms[0].clienteRelacaoTipo.disabled = true;

		}else if (document.forms[0].gerenciaRegional.value != '-1'){
			
			document.forms[0].gerenciaRegional.disabled = false;
			
			document.forms[0].cobrancaGrupo.disabled = true;
			document.forms[0].unidadeNegocio.disabled = true;
			document.forms[0].localidadeOrigemID.disabled = true;
			document.forms[0].localidadeDestinoID.disabled = true;
			document.forms[0].codigoClienteSuperior.disabled = true;
			document.forms[0].idCliente.disabled = true;
			document.forms[0].arquivoImoveis.disabled = true;
			document.forms[0].identificadorInformacaoArquivo[0].disabled = true;
			document.forms[0].identificadorInformacaoArquivo[1].disabled = true;
			habilitaDesabilitaSetorComercialRota();
			document.forms[0].clienteRelacaoTipo.disabled = true;
			
		}else if (document.forms[0].unidadeNegocio.value != '-1'){

			document.forms[0].unidadeNegocio.disabled = false;
			
			document.forms[0].cobrancaGrupo.disabled = true;
			document.forms[0].gerenciaRegional.disabled = true;
			document.forms[0].localidadeOrigemID.disabled = true;
			document.forms[0].localidadeDestinoID.disabled = true;
			document.forms[0].codigoClienteSuperior.disabled = true;
			document.forms[0].idCliente.disabled = true;
			document.forms[0].arquivoImoveis.disabled = true;
			document.forms[0].identificadorInformacaoArquivo[0].disabled = true;
			document.forms[0].identificadorInformacaoArquivo[1].disabled = true;
			habilitaDesabilitaSetorComercialRota();
			document.forms[0].clienteRelacaoTipo.disabled = true;
			
		}else if (document.forms[0].codigoClienteSuperior.value != ''){

			document.forms[0].codigoClienteSuperior.disabled = false;
			
			document.forms[0].unidadeNegocio.disabled = true;
			document.forms[0].cobrancaGrupo.disabled = true;
			document.forms[0].gerenciaRegional.disabled = true;
			document.forms[0].localidadeOrigemID.disabled = true;
			document.forms[0].localidadeDestinoID.disabled = true;
			document.forms[0].idCliente.disabled = true;
			document.forms[0].arquivoImoveis.disabled = true;
			document.forms[0].identificadorInformacaoArquivo[0].disabled = true;
			document.forms[0].identificadorInformacaoArquivo[1].disabled = true;
			habilitaDesabilitaSetorComercialRota();
			document.forms[0].clienteRelacaoTipo.disabled = true;
			
		}else if (document.forms[0].idCliente.value != ''){

			document.forms[0].idCliente.disabled = false;
			
			document.forms[0].unidadeNegocio.disabled = true;
			document.forms[0].cobrancaGrupo.disabled = true;
			document.forms[0].gerenciaRegional.disabled = true;
			document.forms[0].localidadeOrigemID.disabled = true;
			document.forms[0].localidadeDestinoID.disabled = true;
			document.forms[0].codigoClienteSuperior.disabled = true;
			document.forms[0].arquivoImoveis.disabled = true;
			document.forms[0].identificadorInformacaoArquivo[0].disabled = true;
			document.forms[0].identificadorInformacaoArquivo[1].disabled = true;
			habilitaDesabilitaSetorComercialRota();
			document.forms[0].clienteRelacaoTipo.disabled = true;
			
		}else if (document.forms[0].localidadeOrigemID.value != ''){

			document.forms[0].localidadeOrigemID.disabled = false;
			document.forms[0].localidadeDestinoID.disabled = false;
			document.forms[0].setorComercialOrigemCD.disabled = false;
			document.forms[0].setorComercialDestinoCD.disabled = false;

			if (document.forms[0].setorComercialOrigemCD.value != '' 
					&& document.forms[0].setorComercialOrigemCD.value == document.forms[0].setorComercialDestinoCD.value){
				
				document.forms[0].quadraInicial.disabled = false;
				document.forms[0].quadraFinal.disabled = false;				
				document.forms[0].rotaInicial.disabled = false;
				document.forms[0].rotaFinal.disabled = false;
			}else{

				document.forms[0].quadraInicial.valeu = "";
				document.forms[0].quadraFinal.value = "";
				document.forms[0].rotaInicial.value = "";
				document.forms[0].rotaFinal.value = "";
				document.forms[0].quadraInicial.disabled = true;
				document.forms[0].quadraFinal.disabled = true;
				document.forms[0].rotaInicial.disabled = true;
				document.forms[0].rotaFinal.disabled = true;
			}
			
			document.forms[0].unidadeNegocio.disabled = true;
			document.forms[0].cobrancaGrupo.disabled = true;
			document.forms[0].gerenciaRegional.disabled = true;
			document.forms[0].idCliente.disabled = true;
			document.forms[0].codigoClienteSuperior.disabled = true;
			document.forms[0].arquivoImoveis.disabled = true;
			document.forms[0].identificadorInformacaoArquivo[0].disabled = true;
			document.forms[0].identificadorInformacaoArquivo[1].disabled = true;
			document.forms[0].clienteRelacaoTipo.disabled = true;

			
		}else if (document.forms[0].arquivoImoveis.value != ''){

			document.forms[0].arquivoImoveis.disabled = false;
			document.forms[0].identificadorInformacaoArquivo[0].disabled = false;
			document.forms[0].identificadorInformacaoArquivo[1].disabled = false;
			

			document.forms[0].localidadeOrigemID.disabled = true;
			document.forms[0].setorComercialOrigemCD.disabled = true;
			document.forms[0].setorComercialDestinoCD.disabled = true;
			document.forms[0].unidadeNegocio.disabled = true;
			document.forms[0].cobrancaGrupo.disabled = true;
			document.forms[0].gerenciaRegional.disabled = true;
			document.forms[0].localidadeDestinoID.disabled = true;
			document.forms[0].idCliente.disabled = true;
			document.forms[0].codigoClienteSuperior.disabled = true;
			document.forms[0].quadraInicial.disabled = true;
			document.forms[0].quadraFinal.disabled = true;
			document.forms[0].rotaInicial.disabled = true;
			document.forms[0].rotaFinal.disabled = true;
			document.forms[0].clienteRelacaoTipo.disabled = true;
			
		}else{
			
			document.forms[0].cobrancaGrupo.disabled = false;
			document.forms[0].gerenciaRegional.disabled = false;
			document.forms[0].unidadeNegocio.disabled = false;
			document.forms[0].localidadeOrigemID.disabled = false;
			document.forms[0].localidadeDestinoID.disabled = false;
			habilitaDesabilitaSetorComercialRota();
			document.forms[0].idCliente.disabled = false;
			document.forms[0].codigoClienteSuperior.disabled = false;
			document.forms[0].clienteRelacaoTipo.disabled = true;
			document.forms[0].arquivoImoveis.disabled = false;
			document.forms[0].identificadorInformacaoArquivo[0].disabled = false;
			document.forms[0].identificadorInformacaoArquivo[1].disabled = false;
			
		}
		
	}
}

function verificarIndicadorCriterio(){

	if(document.forms[0].indicadorCriterioComando[0].checked == true){
		document.forms[0].cobrancaCriterio.disabled = true;
		document.forms[0].cobrancaCriterio.value = '';
		document.forms[0].nomeCobrancaCriterio.value = '';
	}else if(document.forms[0].indicadorCriterioComando[1].checked == true){
		document.forms[0].cobrancaCriterio.disabled = false;
	}
	
}

function validarCriterioCobranca(){

	if(document.forms[0].indicadorCriterioComando[1].checked == true &&
			document.forms[0].cobrancaCriterio.value == ''){

		alert("Informe o Critério de Cobrança");
		document.forms[0].cobrancaCriterio.focus();
		return false;
	}

	return true;
}

function preencherCampoPrazoExecucao(){
	var form = document.forms[0];
		form.submit();
	
	}

function habilitaDesabilitaFormato(){
	var desabilitaFormato = true;

	var form = document.forms[0];
	var obj = form.cobrancaAcao;

	if (obj != null){
		var idAcaoCobranca = obj.options[obj.selectedIndex].value;

		if (idAcaoCobranca != "-1" && idAcaoCobranca == <%=""+CobrancaAcao.CARTA_COM_OPCOES_DE_PAGAMENTO%>){
			desabilitaFormato = false;
		}
	}

	for (var i=0;i < document.forms[0].elements.length;i++){
		var elemento = document.forms[0].elements[i];

		if (elemento.type == "radio" && elemento.name == 'formatoArquivo'){
			elemento.disabled = desabilitaFormato;
			document.forms[0].formatoArquivo[0].checked == true;
		}
	}
}

function isCamposPesquisaQuadraRotaValidos(){
	var form = document.forms[0];
	   var msg = '';
	   if(form.localidadeOrigemID.value == ""){
		   msg = 'Informe Localidade Inicial.\n';
	   }
	   if(form.localidadeDestinoID.value == ""){
		   msg = msg+ 'Informe Localidade Final.\n';
	   }
	   if(form.setorComercialOrigemCD.value == ""){
		   msg = msg +'Informe Setor Comercial Inicial.\n';
	   }
	   if(form.setorComercialDestinoCD.value == ""){
		   msg = msg+ 'Informe Setor Comercial Final.\n';
	   }

	   if( msg != '' ){
		   alert(msg);
		   return false;
	   }else{
			var msgDois = '';
			
		   if(form.localidadeOrigemID.value != form.localidadeDestinoID.value){
			   msgDois = 'Localidades diferentes.\n';
		   }
		   if(form.setorComercialOrigemCD.value != form.setorComercialDestinoCD.value){
			   msgDois = msgDois + 'Setores Comeriais diferentes.\n';
		   }
		   if( msgDois != '' ){
			   alert(msgDois);
			   return false;
			}else{
				return true;
			}
		}
	
}

function pesquisarQuadra(destino){
	var form = document.forms[0];
	
	if (isCamposPesquisaQuadraRotaValidos()){
		abrirPopup('exibirPesquisarQuadraAction.do?idLocalidade='+form.localidadeOrigemID.value+'&codigoSetorComercial='+form.setorComercialOrigemCD.value+'&tipo='+destino, 250, 495);
	}
}

function limparRotaQuadra(campo){
	var form = document.forms[0];
	
	if(campo.name == "quadraInicial" || campo.name == "quadraFinal"){
		
		if(form.quadraInicial.value != "" || form.quadraFinal.value != ""){
			form.rotaInicial.value = "";
			form.rotaFinal.value = "";
		}
		
		if(campo.name == "quadraInicial"){
			replicarQuadra();
		}
	} else if (campo.name == "rotaInicial" || campo.name == "rotaFinal"){
		
		if(form.rotaInicial.value != "" || form.rotaFinal.value != ""){
			form.quadraInicial.value = "";
			form.quadraFinal.value = "";
		}
		
		if(campo.name == "rotaInicial"){
			replicarRota();
		}
	}
}

function limparCampo(nomeCampo){
	var form = document.forms[0];

	if(nomeCampo == "quadraInicial"){
		form.quadraInicial.value = "";
		form.quadraFinal.value = "";
	}
	if(nomeCampo == "quadraFinal"){
		form.quadraFinal.value = "";
	}
	if(nomeCampo == "rotaInicial"){
		form.rotaInicial.value = "";
		form.rotaFinal.value = "";
	}
	if(nomeCampo == "rotaFinal"){
		form.rotaFinal.value = "";
	}
}
-->
</script>
</head>

<body leftmargin="5" topmargin="5" onload="javascript:validaArrecadador();verificaArquivo();habilitaDesabilitaPrecedente();verificarIndicadorCriterio();habilitaDesabilitaParametrosAdicionais();habilitaDesabilitaFormato();">
<html:form action="/exibirManterComandoAcaoCobrancaDetalhesAction.do"
	name="ManterComandoAcaoCobrancaDetalhesActionForm"
	type="gcom.gui.cobranca.ManterComandoAcaoCobrancaDetalhesActionForm"
	method="post"
	onsubmit="return validateExibirManterComandoAcaoCobrancaDetalhesActionForm(this);" enctype="multipart/form-data">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>
	
	<input type="hidden" name="idAtividadeCobrancaEmitir" value= "<%=CobrancaAtividade.EMITIR %>"/>
	<input type="hidden" name="tipoPesquisa" />
	<html:hidden property="dataRealizacao"/>
	<input type="hidden" name="desabilitarParametrosAdicionais" value="<%=ManterComandoAcaoCobrancaDetalhesActionForm.DESABILITARPARAMETROSADICIONAIS%>" />

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


			<td valign="top" class="centercoltext"><!--Início Tabela Reference a Páginação da Tela de Processo-->
			<!--Início Tabela Reference a Páginação da Tela de Processo-->
			<table>
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">Manter Comando de A&ccedil;&atilde;o de
					Cobran&ccedil;a</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>
				</tr>
			</table>
			<!--Fim Tabela Reference a Páginação da Tela de Processo-->
			<p>&nbsp;</p>
			<table width="100%" border="0" dwcopytype="CopyTableRow">
				<tr>
					<td colspan="4">Para determinar a a&ccedil;&atilde;o de
					cobran&ccedil;a a ser comandada, informe os dados abaixo:</td>
				</tr>
				
				<tr>
					<td width="16%"><strong>A&ccedil;&atilde;o de Cobran&ccedil;a:<font
						color="#FF0000">*</font></strong></td>
					<td colspan="3">
					 <html:select tabindex="1" 
						name="ManterComandoAcaoCobrancaDetalhesActionForm"
						property="cobrancaAcao" onchange="validarCriterios(1);habilitaDesabilitaPrecedente();validaArrecadador();preencherCampoPrazoExecucao();habilitaDesabilitaFormato();">
						<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present scope="session" name="colecaoCobrancaAcao">
							<html:options name="session" collection="colecaoCobrancaAcao"
								labelProperty="descricaoCobrancaAcao" property="id" />
						</logic:present>
					  </html:select>
				</tr>
				
			
				
				<tr>
					<td width="16%"><strong>Comando Precedente:</strong></td>
					<td colspan="3">
					
					<logic:present name="exibirTituloComandoPrecedente">
						<html:text property="tituloComandoPrecedente" size="20" maxlength="100" tabindex="4"  readonly="true"  
					onmouseover="this.T_BGCOLOR='whitesmoke';this.T_LEFT=true;return escape('Comando Precedente: ${ManterComandoAcaoCobrancaDetalhesActionForm.tituloCompletoComandoPrecedente}')" />
					</logic:present>
					
					<logic:notPresent name="exibirTituloComandoPrecedente"> 
						<html:text property="tituloComandoPrecedente" tabindex="3" size="20" maxlength="100"  readonly="true"  />
					</logic:notPresent>	
					
					<input name="Button32232" type="button" id="acaoPrecedente" tabindex="3" 
						class="bottonRightCol" value="Selecionar"
						onClick="javascript:abrirPopup('exibirPesquisarComandoAcaoCobrancaAction.do?acaoCobrancaSelecionada='+document.forms[0].cobrancaAcao.value+'&caminhoRetornoTelaPesquisaAcaoCobranca=exibirManterComandoAcaoCobrancaDetalhesAction&limparForm=OK', 400, 750);" />
					
					</td>
				</tr>
				
				<tr>
					<td width="16%"><strong>Título:<font
						color="#FF0000">*</font></strong></td>
					<td colspan="3">
					  <html:text property="titulo" size="60" maxlength="100" tabindex="5"/>
					</td>
				</tr>
				<tr>
					<td width="16%"><strong>Descrição da Solicitação:<font
						color="#FF0000">*</font></strong></td>
					<td colspan="3">
					  <html:textarea property="descricaoSolicitacao" cols="50" rows="3" tabindex="6"/>
					</td>
				</tr>
				
				<tr>
					<td><strong>Atividade de Cobran&ccedil;a:<font color="#FF0000">*</font></strong></td>
					<td colspan="3">
					  <html:select name="ManterComandoAcaoCobrancaDetalhesActionForm"
						tabindex="7"
						property="cobrancaAtividade" onchange="validarCriterios(2);">
						<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present scope="session" name="colecaoCobrancaAtividade">
							<html:options name="session"
								collection="colecaoCobrancaAtividade"
								labelProperty="descricaoCobrancaAtividade" property="id" />
						</logic:present>
					  </html:select>
					</td>
				</tr>
				
				<logic:present name="isCobrancaAdministrativa" scope="request">	
					<tr>
						<td width="25%"><strong>Empresa:<font color="#FF0000">*</font></strong></td>
						<td colspan="2">
							<html:select name="ManterComandoAcaoCobrancaDetalhesActionForm" 
										 property="idEmpresa" tabindex="8">
							  <html:option value="">&nbsp;</html:option>
							  <logic:present scope="session" name="colecaoEmpresa">
								<html:options name="session" collection="colecaoEmpresa"
											  labelProperty="descricao" property="id" />
							  </logic:present>			
							</html:select>
						</td>
					</tr>
				</logic:present>
				<logic:notPresent name="isCobrancaAdministrativa" scope="request">
					<tr>
						<td width="25%"><strong>Empresa:</strong></td>
						<td colspan="2">
							<html:select name="ManterComandoAcaoCobrancaDetalhesActionForm" 
										 property="idEmpresa" tabindex="8">
							  <html:option value="">&nbsp;</html:option>
							  <logic:present scope="session" name="colecaoEmpresa">
								<html:options name="session" collection="colecaoEmpresa"
											  labelProperty="descricao" property="id" />
							  </logic:present>			
							</html:select>
						</td>
					</tr>
				</logic:notPresent>
				
				
				 
				 <tr>
					<td width="32%" class="style3"><strong>Arrecadador:</strong></td>
					<td width="68%" colspan="2">
					<html:select property="idClienteCombo" tabindex="9" style="width:200px;">
						<html:option value="-1"> &nbsp; </html:option>
						<logic:notEmpty name="colecaoClienteArrecadador"> 
						<html:options collection="colecaoClienteArrecadador" property="id" labelProperty="nome" />
						</logic:notEmpty>
				  </html:select>
				  </td>
				</tr>
				

				
				<tr>
					<td width="16%"><strong>Prazo de Execução:</strong></td>
					<td colspan="3"><html:text property="prazoExecucao" tabindex="10" size="3" maxlength="3" onkeyup="javascript:verificaNumeroInteiro(this);"/></td>
				</tr>
				
				<tr>
					<td width="16%"><strong>Quantidade Máxima de Documentos:</strong></td>
					<td colspan="3">
					  <html:text property="quantidadeMaximaDocumentos" tabindex="11" size="5" maxlength="5" onkeyup="javascript:verificaNumeroInteiro(this);"/>
				   </td>
				</tr>
				
				<tr>
					<td width="16%"><strong>Apenas para Imóveis com Débito:</strong></td>
					<td colspan="3">
					  <html:radio property="indicadorImoveisDebito" value="1" tabindex="13" /> 
					                  <strong>Sim 
					                <html:radio	property="indicadorImoveisDebito" value="2" tabindex="14"/> 
					                N&atilde;o</strong>
					  
					</td>
				</tr>
				
				<tr>
					<td width="16%"><strong>Gerar Relação dos Documentos:</strong></td>
					<td><html:radio property="indicadorGerarRelacaoDocumento" value="1" /> 
					                  <strong>Sim 
					                <html:radio	property="indicadorGerarRelacaoDocumento" value="2" /> 
					                N&atilde;o</strong>
					</td>
				</tr>

		
				<tr>
					<td width="16%"><strong>Indicador de Critério:</strong></td>
					<td colspan="3"><html:radio property="indicadorCriterioComando" value="1" tabindex="12" onclick="verificarIndicadorCriterio();" /> 
					                  <strong>Critério da Ação
					                <html:radio	property="indicadorCriterioComando" value="2" tabindex="13" onclick="verificarIndicadorCriterio();"/> 
					            	Critério do Comando</strong>
					</td>
				</tr>

				<tr>
					<td width="16%"><strong>Formato:</strong></td>
					<td>
						<html:radio property="formatoArquivo" disabled="true" value="<%=""+TarefaRelatorio.TIPO_PDF%>"/><strong>PDF 
					    <html:radio	property="formatoArquivo" disabled="true" value="<%=""+TarefaRelatorio.TIPO_CSV%>"/>CSV</strong>
					</td>
				</tr>
		<tr>
		 <td><strong>Crit&eacute;rio de Cobran&ccedil;a:</strong></td>
					<td width="81%" align="right" colspan="2">
					<div align="left">
					
						<html:text maxlength="6" tabindex="15"
					property="cobrancaCriterio" size="8" 
					name="ManterComandoAcaoCobrancaDetalhesActionForm"
					onkeypress="validaEnterComMensagem(event, 'exibirManterComandoAcaoCobrancaDetalhesAction.do?objetoConsulta=1&inscricaoTipo=origem&validarCriterio=naoAcao', 'cobrancaCriterio', 'Critério Cobrança');"
					onkeyup="javascript:verificaNumeroInteiro(this);"/>
							
					<img src="<bean:message key='caminho.imagens'/>pesquisa.gif"
					width="23" height="21" style="cursor:hand;" name="imagem"
					onclick="chamarPopup('exibirPesquisarCriterioCobrancaAction.do?limpaForm=1', 'origem', null, null, 275, 480, '',document.forms[0].cobrancaCriterio);"
					alt="Pesquisar">

						<strong> 
						<logic:present name="corCobrancaCriterio">
							<logic:equal name="corCobrancaCriterio" value="exception">
								<html:text property="nomeCobrancaCriterio" 
									size="40"
									maxlength="40" 
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000" />
							</logic:equal>

							<logic:notEqual name="corCobrancaCriterio" value="exception">
								<html:text property="nomeCobrancaCriterio" 
									size="40"
									maxlength="40" 
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</logic:notEqual>

						</logic:present> 
						
						<logic:notPresent name="corCobrancaCriterio">
							<logic:empty name="ManterComandoAcaoCobrancaDetalhesActionForm"
								property="cobrancaCriterio">
							
							<html:text property="nomeCobrancaCriterio" 
								value="" 
								size="40"
								maxlength="40" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
							</logic:empty>
							
							<logic:notEmpty name="ManterComandoAcaoCobrancaDetalhesActionForm"
								property="cobrancaCriterio">
								
								<html:text property="nomeCobrancaCriterio" 
									size="40"
									maxlength="40" 
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: 	#000000" />
							</logic:notEmpty>
						</logic:notPresent> 
						
						<a href="javascript:limparCriterioCobranca();"> <img name="imagem"
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a>  					
						</strong>
						</div>
					</td>

				</tr>
				
		
				<tr>
					<td colspan="3">
					<div id="layerHideLigacaoAgua" style="display:block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('LigacaoAgua',true);" /> <b>Parâmetros Adicionais</b> </a> </span></td>
						</tr>
					</table>
					</div>
					

					<div id="layerShowLigacaoAgua" style="display:none">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('LigacaoAgua',false);" /> <b>Parâmetros Adicionais</b> </a> </span></td>
						</tr>

						<tr>
							<td colspan="9">
							<table width="100%" bgcolor="#99CCFF">
		
						<tr>
					<td><strong>Grupo de Cobran&ccedil;a:</strong></td>
					<td colspan="3">
					  <html:select tabindex="16"
						name="ManterComandoAcaoCobrancaDetalhesActionForm"
						onchange="javascript:validarGrupoCobrancaSelecionado();"
						property="cobrancaGrupo">
						<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present scope="session" name="colecaoCobrancaGrupo">
							<html:options name="session" collection="colecaoCobrancaGrupo"
								labelProperty="descricao" property="id" />
						</logic:present>
					  </html:select>	
					</td>
				</tr>
				<tr>
					<td colspan="4">
					<hr>
					</td>
				</tr>
				<tr>
					<td height="24"><strong>Ger&ecirc;ncia Regional:<strong></strong></strong></td>
					<td width="60%" colspan="3">
					<div align="left"><strong>
					
					  <html:select tabindex="17"
						name="ManterComandoAcaoCobrancaDetalhesActionForm"
						onchange="javaScript:validarGerenciaRegionalSelecionado();"
						property="gerenciaRegional">
						<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present scope="session" name="colecaoGerenciaRegional">
				         <logic:iterate name="colecaoGerenciaRegional" id="colecaoGerenciasRegionais">
  							  <html:option value="${colecaoGerenciasRegionais.id}">
					           <bean:write name="colecaoGerenciasRegionais" property="nomeAbreviado"/> 
					           - <bean:write name="colecaoGerenciasRegionais" property="nome"/>
					          </html:option>
				         </logic:iterate>
						</logic:present>
					  </html:select>
					  
					</strong></div>
					</td>

				</tr>
				
				<tr>
					<td height="24"><strong>Unidade Negócio:<strong></strong></strong></td>
					<td width="60%" colspan="3">
					 <div align="left"><strong> 
						  <html:select tabindex="18"
							name="ManterComandoAcaoCobrancaDetalhesActionForm"
							onchange="javaScript:validarUnidadeNegocioSelecionado();"
							property="unidadeNegocio">
							<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<logic:present scope="session" name="colecaoUnidadeNegocio">
					         <logic:iterate name="colecaoUnidadeNegocio" id="colecaoUnidadeNegocios">
									<html:option value="${colecaoUnidadeNegocios.id}">
						           <bean:write name="colecaoUnidadeNegocios" property="nomeAbreviado"/> 
						           - <bean:write name="colecaoUnidadeNegocios" property="nome"/>
						          </html:option>
					         </logic:iterate>
							</logic:present>

						  </html:select>
					 </strong></div>
					</td>

				</tr>
				
				
				
				<html:hidden property="inscricaoTipo" />
				<tr>
					<td colspan="4">
					<hr>
					</td>
				</tr>

				<tr>

					<td height="51"><strong>Localidade Inicial:<font color="#FF0000"> </font></strong></td>
					<td width="31%" align="right" colspan="2">
					<div align="left">
					  <html:text maxlength="3" property="localidadeOrigemID" size="3"
						onkeypress="validaEnterComMensagem(event, 'exibirManterComandoAcaoCobrancaDetalhesAction.do?objetoConsulta=1&inscricaoTipo=origem', 'localidadeOrigemID','Localidade Inicial');"
						tabindex="19" onkeyup="javaScript:desabilitarCobrancaAcao();verificaNumeroInteiro(this);limparDestino(1);"
						onblur="javascript:replicarLocalidade();" onchange="enviarDadosLocalidade()" />
					 <img
						src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23"
						height="21" style="cursor:hand;" name="imagem"
						onclick="chamarPopup('exibirPesquisarLocalidadeAction.do', 'origem', null, null, 275, 480, '',document.forms[0].localidadeOrigemID);"
						alt="Pesquisar"> <strong> <logic:present name="corLocalidadeOrigem">
						<logic:equal name="corLocalidadeOrigem" value="exception">
							<html:text property="nomeLocalidadeOrigem" size="30"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>

						<logic:notEqual name="corLocalidadeOrigem" value="exception">
							<html:text property="nomeLocalidadeOrigem" size="30"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>

					</logic:present> <logic:notPresent name="corLocalidadeOrigem">

						<logic:empty name="ManterComandoAcaoCobrancaDetalhesActionForm"
							property="localidadeOrigemID">
							<html:text property="nomeLocalidadeOrigem" value="" size="30"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="ManterComandoAcaoCobrancaDetalhesActionForm"
							property="localidadeOrigemID">
							<html:text property="nomeLocalidadeOrigem" size="30"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: 	#000000" />
						</logic:notEmpty>
					</logic:notPresent> 
						<a href="javascript:limparOrigem(1,document.forms[0].localidadeOrigemID);"> <img name="imagem"
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a>  							
					</strong></div>
					</td>
				</tr>
				<tr>
					<td><strong>Setor Comercial Inicial: </strong></td>
					<td align="right" colspan="2">
					<div align="left">
					  <html:text maxlength="3" tabindex="20"
						property="setorComercialOrigemCD" size="3"
						onkeyup="javascript:verificaNumeroInteiro(this);limparDestino(2);habilitaDesabilitaSetorComercialRota();"
						onkeypress="validaEnterDependenciaComMensagem(event, 'exibirManterComandoAcaoCobrancaDetalhesAction.do?objetoConsulta=2&inscricaoTipo=origem&validarCriterio=naoAcao', document.forms[0].setorComercialOrigemCD, document.forms[0].localidadeOrigemID.value, 'Localidade Inicial', 'Setor Comercial Inicial');"
						onblur="replicarSetorComercial();"
						onfocus="validarLocalidade();" />
					 <img
						src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23"
						height="21" style="cursor:hand;" name="imagem"
						onclick="javascript:pesquisarSetorComercial('setorComercialOrigemCD');">
						<html:hidden property="setorComercialOrigemID" />

					<logic:present name="corSetorComercialOrigem">
						<logic:equal name="corSetorComercialOrigem" value="exception">
							<html:text property="nomeSetorComercialOrigem" size="30"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>

						<logic:notEqual name="corSetorComercialOrigem" value="exception">
							<html:text property="nomeSetorComercialOrigem" size="30"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> <logic:notPresent name="corSetorComercialOrigem">
						<logic:empty name="ManterComandoAcaoCobrancaDetalhesActionForm"
							property="setorComercialOrigemCD">
							<html:text property="nomeSetorComercialOrigem" value="" size="30"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="ManterComandoAcaoCobrancaDetalhesActionForm"
							property="setorComercialOrigemCD">
							<html:text property="nomeSetorComercialOrigem" size="30"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>

					</logic:notPresent>
						<a href="javascript:limparOrigem(2,document.forms[0].setorComercialOrigemCD);"> <img name="imagem"
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a>  							
					</div>
					</td>
				</tr>				
				<tr>
					<td><strong>Quadra Inicial:</strong></td>
					<td><html:text 
							
							maxlength="4"
							property="quadraInicial" 
							size="4"
							name="ManterComandoAcaoCobrancaDetalhesActionForm"
							onblur="limparRotaQuadra(this)"
							onkeyup="javaScript:verificaNumeroInteiro(this);" />
							
							<a href="javascript:pesquisarQuadra('quadraOrigem');">
							<img width="23" height="21" border="0"
							src="<bean:message key="caminho.imagens"/>pesquisa.gif"
							title="Pesquisar Quadra" /></a>	
							
							<a href="javascript:limparCampo('quadraInicial');"> <img name="imagem"
							src="<bean:message key="caminho.imagens"/>limparcampo.gif"
							border="0" title="Apagar" /></a>
					</td>
				</tr>
				<tr>
					<td><strong>Rota Inicial:<font color="#FF0000"> </font></strong></td>
					<td align="right" colspan="2">
					<div align="left">
					  <html:text maxlength="4" tabindex="21"
						property="rotaInicial" size="4"
						onblur="javascript:limparRotaQuadra(this);" 
						onkeyup="javaScript:verificaNumeroInteiro(this);"
						name="ManterComandoAcaoCobrancaDetalhesActionForm"/>
						
					 <a href="javascript:pesquisarRota('inicial');">
					 <img width="23" height="21" border="0"
					 src="<bean:message key="caminho.imagens"/>pesquisa.gif"
					 title="Pesquisar Rota" /></a>	
					 
				 	<a href="javascript:limparCampo('rotaInicial');"> <img name="imagem"
					src="<bean:message key="caminho.imagens"/>limparcampo.gif"
					border="0" title="Apagar" /></a> 					
					</div>
					</td>
				</tr>
				<tr>
					<td colspan="4">
					<hr>
					</td>
				</tr>

				<tr>
					<td><strong>Localidade Final:<font color="#FF0000"> </font></strong></td>
					<td width="37%" align="left" colspan="2">
					
					  <html:text maxlength="3"
						property="localidadeDestinoID" size="3" tabindex="22"
						onkeyup="desabilitaIntervaloDiferente(1);verificaNumeroInteiro(this);"
						onkeypress="javascript:validaEnterComMensagem(event, 'exibirManterComandoAcaoCobrancaDetalhesAction.do?objetoConsulta=1&inscricaoTipo=destino', 'localidadeDestinoID','Localidade Final');" 
						/>
					
					<img src="<bean:message key='caminho.imagens'/>pesquisa.gif"
						width="23" height="21" border="0" style="cursor:hand;"
						name="imagem"
						onclick="chamarPopup('exibirPesquisarLocalidadeAction.do', 'destino', null, null, 275, 480, '',document.forms[0].localidadeDestinoID);"
						alt="Pesquisar"><logic:present
						name="corLocalidadeDestino">

						<logic:equal name="corLocalidadeDestino" value="exception">
							<html:text property="nomeLocalidadeDestino" size="30"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>

						<logic:notEqual name="corLocalidadeDestino" value="exception">
							<html:text property="nomeLocalidadeDestino" size="30"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>

					</logic:present> <logic:notPresent name="corLocalidadeDestino">

						<logic:empty name="ManterComandoAcaoCobrancaDetalhesActionForm"
							property="localidadeDestinoID">
							<html:text property="nomeLocalidadeDestino" value="" size="30"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="ManterComandoAcaoCobrancaDetalhesActionForm"
							property="localidadeDestinoID">
							<html:text property="nomeLocalidadeDestino" size="30"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: 	#000000" />
						</logic:notEmpty>
					</logic:notPresent>
						<a href="javascript:limparBorrachaDestino(1,document.forms[0].localidadeDestinoID);"> <img name="imagem"
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a>  		
					</td>
				</tr>

				<tr>
					<td><strong>Setor Comercial Final: </strong></td>
					<td align="left" colspan="2">
					  <html:text maxlength="3"
						property="setorComercialDestinoCD" size="3" tabindex="23"
						onkeyup="desabilitaIntervaloDiferente(2);habilitarQuadraRota();verificaNumeroInteiro(this);"
						onkeypress="validaEnterDependenciaComMensagem(event, 'exibirManterComandoAcaoCobrancaDetalhesAction.do?objetoConsulta=2&inscricaoTipo=destino&validarCriterio=naoAcao', document.forms[0].setorComercialDestinoCD, document.forms[0].localidadeDestinoID.value, 'Localidade Final', 'Setor Comercial Final');"
						onfocus="validarLocalidade();" />  
					
					<img
						src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23"
						height="21" style="cursor:hand;" name="imagem"
						onclick="javascript:pesquisarSetorComercial('setorComercialOrigemCD');"
						alt="Pesquisar"><logic:present
						name="corSetorComercialDestino">

						<logic:equal name="corSetorComercialDestino" value="exception">
							<html:text property="nomeSetorComercialDestino" size="30"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>

						<logic:notEqual name="corSetorComercialDestino" value="exception">
							<html:text property="nomeSetorComercialDestino" size="30"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>

					</logic:present> <logic:notPresent name="corSetorComercialDestino">

						<logic:empty name="ManterComandoAcaoCobrancaDetalhesActionForm"
							property="setorComercialDestinoCD">
							<html:text property="nomeSetorComercialDestino" size="30"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="ManterComandoAcaoCobrancaDetalhesActionForm"
							property="setorComercialDestinoCD">
							<html:text property="nomeSetorComercialDestino" size="30"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>

					</logic:notPresent> <html:hidden property="setorComercialDestinoID" />
						<a href="javascript:limparBorrachaDestino(2,document.forms[0].setorComercialDestinoCD);"> <img name="imagem"
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a>  		
					</td>
				</tr>
				
				<tr>
					<td><strong>Quadra Final:</strong></td>
					<td><html:text 
							
							maxlength="4"
							property="quadraFinal" 
							size="4"
							name="ManterComandoAcaoCobrancaDetalhesActionForm"
							onkeyup="javaScript:verificaNumeroInteiro(this);"
							onblur="limparRotaQuadra(this)" />
							
							<a href="javascript:pesquisarQuadra('quadraDestino');">
							<img width="23" height="21" border="0"         
							src="<bean:message key="caminho.imagens"/>pesquisa.gif"
							title="Pesquisar Quadra" /></a>	
							
							<a href="javascript:limparCampo('quadraFinal');"> <img name="imagem"
							src="<bean:message key="caminho.imagens"/>limparcampo.gif"
							border="0" title="Apagar" /></a>
					</td>
				</tr>
				
				<tr>
					<td><strong>Rota Final:<font color="#FF0000"> </font></strong></td>
					<td align="right" colspan="2">
						<div align="left">
						  <html:text maxlength="4" tabindex="24"
						     property="rotaFinal" size="4"
						     onblur="limparRotaQuadra(this);"
						     onkeyup="javascript:verificaNumeroInteiro(this);"
						     name="ManterComandoAcaoCobrancaDetalhesActionForm"/> 
						     
						<a href="javascript:pesquisarRota('final');">
						<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Rota" /></a>	
						
						<a href="javascript:limparCampo('rotaFinal');"> <img name="imagem"
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a> 					
					</td>
				</tr>
				<tr>
					<td colspan="4">
					<hr>
					</td>
				</tr>
				<tr>
					<td><strong>Cliente Superior:</strong></td>
					<td colspan="3"><strong>
					  <html:text maxlength="9"
						property="codigoClienteSuperior" size="9" tabindex="25"
						onkeyup="javaScript:validarClienteSuperior(1);verificaNumeroInteiro(this);desabilitarLocalidadeCobrancaAcao();"
						onkeypress="javascript:validaEnter(event, 'exibirManterComandoAcaoCobrancaDetalhesAction.do?objetoConsulta=3&inscricaoTipo=destino', 'codigoClienteSuperior');" />
					<a href="javascript:validarAbrirPoupPesquisarClienteSuperior();">
					<img border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Cliente" /></a> <logic:present
						name="codigoClienteSuperiorNaoEncontrado" scope="request">
						<input type="text" name="nomeClienteSuperior" size="40" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000"
							value="CLIENTE SUPERIOR INEXISTENTE" />
					</logic:present> <logic:notPresent
						name="codigoClienteSuperiorNaoEncontrado" scope="request">
						<html:text property="nomeClienteSuperior" size="40" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000" />
					</logic:notPresent> <a href="javascript:validarLimparClienteSuperior();desabilitarLocalidadeCobrancaAcao()"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a> </strong></td>
				</tr>
				<tr>
					<td><strong>Cliente:</strong></td>
					<td colspan="3"><strong>
					  <html:text maxlength="9"
						property="idCliente" size="9" tabindex="25"
						onkeyup="javaScript:validarCliente();desabilitarLocalidadeCobrancaAcao();verificaNumeroInteiro(this);"
						onkeypress="javascript:validaEnter(event, 'exibirManterComandoAcaoCobrancaDetalhesAction.do?objetoConsulta=3&inscricaoTipo=destino', 'idCliente');" />
					<a href="javascript:validarAbrirPoupPesquisarCliente();">
					<img border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Cliente" /></a> <logic:present
						name="codigoClienteNaoEncontrado" scope="request">
						<input type="text" name="nomeCliente" size="50" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000"
							value="CLIENTE INEXISTENTE" />
					</logic:present> <logic:notPresent
						name="codigoClienteNaoEncontrado" scope="request">
						<html:text property="nomeCliente" size="50" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000" />
					</logic:notPresent> <a href="javascript:validarLimparCliente();desabilitarLocalidadeCobrancaAcao();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a> </strong></td>
				</tr>
				<tr>
					<td><strong>Tipo de Rela&ccedil;&atilde;o:</strong></td>
					<td colspan="3"><strong> 
					  <html:select tabindex="26"
						name="ManterComandoAcaoCobrancaDetalhesActionForm"
						property="clienteRelacaoTipo">
						<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options name="request"
							collection="colecaoClienteRelacaoTipo" labelProperty="descricao"
							property="id" />
					</html:select>
					 </strong></td>
				</tr>
				
				<tr>
					<td colspan="4">
					<hr>
					</td>
				</tr>
				<tr>
					<td><strong>Arquivo de Imóveis:</strong></td>
					<td colspan="2">
					<html:file property="arquivoImoveis" tabindex="27"
						size="40" onchange="javascript:validarArquivoSelecionado();" />
					</td>
				</tr>
				<tr>
					<td>
					<html:radio property="identificadorInformacaoArquivo" tabindex="28" value="1" onchange="javascript:verificaArquivo();"/>Arquivo Informado
					<html:radio property="identificadorInformacaoArquivo" value="2" tabindex="29" onchange="javascript:verificaArquivo();"/>Arquivo Não Informado
					<td>
				</tr>
			
					</table>
							</td>
						</tr>

					</table>
					</div>
					</td>
				</tr>
		
				<tr>
					<td height="17"><strong></strong></td>
					<td colspan="3"><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios</td>
				</tr>
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>
				<tr>
					<td height="17" colspan="4">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" align="left"><!--  <input name="executarComando"
						type="button" class="bottonRightCol" onClick="executarcomando();"
						value="Executar Comando" />
						-->
						</td>
					<td colspan="1" align="right">
					<table border="0">
						<tr>
					<!--
							<td align="right"><img src="imagens/voltar.gif" width="15"
								height="24" border="0" /></td>
							<td align="right"><input name="Button32222" type="button"
								class="bottonRightCol" value="Voltar"
								onClick="javascript:voltar()" /></td>
							<td align="right"><input name="Avancar" type="button"
								class="bottonRightCol" disabled value="Avançar"
								onClick="avancar();" /></td>
							<td align="right"><img src="imagens/avancar.gif" width="15"
								height="24" border="0" /></td>
							<td align="right"></td>
					  -->		
						<td align="right"><input name="Button32232" type="button"
								class="bottonRightCol" value="Recuperar Dados de Outro Comando"
								onClick="javascript:abrirPopup('exibirPesquisarComandoAcaoCobrancaAction.do?limparForm=OK', 400, 750);" /></td>
						
						</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td colspan="3" width="100%" height="1px" bgcolor="#000000"></td>
				</tr>				
				<tr>
					<td colspan="2"><strong> <font color="#ff0000"> <input name="Submit22"
						class="bottonRightCol" value="Desfazer" type="button"
						onClick="javascript:voltar();" >

					<input name="Submit23" class="bottonRightCol" value="Cancelar"
						type="button"
						onclick="window.location.href='/gsan/telaPrincipal.do'"> </font></strong></td>
					<td align="right">
					  <gcom:controleAcessoBotao name="concluir" value="Concluir" onclick="javascript:concluircomando();" url="manterComandoAcaoCobrancaEventualConcluirAction.do"/>
					  <%-- <input type="button" name="concluir" class="bottonRightCol" value="Concluir" onclick="javascript:concluircomando();" /> --%>
					</td>
				</tr>				
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>
	
	<%@ include file="/jsp/util/tooltip.jsp"%>
	<%@ include file="/jsp/util/rodape.jsp"%>
	
</html:form>
</body>
<script language="JavaScript">
<!-- Begin
	validarHabilitarCampo();
-->
</script>
</html:html>
