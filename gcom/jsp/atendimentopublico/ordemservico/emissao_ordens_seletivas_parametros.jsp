<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gcom.util.ConstantesSistema"%>
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="ImovelEmissaoOrdensSeletivasActionForm" dynamicJavascript="false" />

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
	
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/engine.js'> </script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/util.js'> </script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/interface/AjaxService.js'> </script>

<script>
var bCancel = false; 

    function validateImovelEmissaoOrdensSeletivasActionForm(form) {
      return true; 
   	}

	function validarLocalidade() {
		var campo = document.forms[0].localidade;
		var idLocalidade = campo.value;
		var escreveItem = true;
		
		document.forms[0].setor.disabled = false;
		document.forms[0].bairro.disabled = false;
		if (document.forms[0].setor.length > 0 && document.forms[0].bairro.length > 0) {
			if (document.forms[0].setor.value != "") {
				if (document.forms[0].rota.length == 0 || document.forms[0].rota.value != "") {
					document.forms[0].quadra.disabled = true;
				} else {
					document.forms[0].quadra.disabled = false;
				}
				if (document.forms[0].quadra.length == 0 || document.forms[0].quadra.value != "") {
					document.forms[0].rota.disabled = true;
				} else {
					document.forms[0].rota.disabled = false;
				}
				document.forms[0].lote.disabled = false;
				document.forms[0].logradouro.disabled = true;
			} else if (document.forms[0].bairro.value != "") {
				document.forms[0].quadra.disabled = true;
				document.forms[0].rota.disabled = true;
				document.forms[0].lote.disabled = true;
				document.forms[0].logradouro.disabled = false;
			}
		}	
		
		if (campo.value != "-1") {
			
			limparDadosGeograficos('3');
			
			AjaxService.carregaSetoresPorLocalidade(idLocalidade, {callback: 
					function(setores) {
						for (i = 0 ; i < setores.size(); i+=2){
				  			var novaOpcao = new Option(setores[i], setores[i+1]);
				  			document.forms[0].setor.options[document.forms[0].setor.length] = novaOpcao;
		  		   		} 
					}, async: false} );
			bairro = document.forms[0].bairro.value;
			document.forms[0].bairro.length = 0;
			AjaxService.carregaBairrosPorLocalidade(idLocalidade, {callback: 
					function(bairros) {
						for (key in bairros){
					  		var novaOpcao = new Option(key, bairros[key]);
					  		document.forms[0].bairro.options[document.forms[0].bairro.length] = novaOpcao;
		  		   		} 
					}, async: false} );	
			document.forms[0].bairro.value = bairro;
			
		} else {
			limparBorrachaElo('2')
		}
	}
	
	function carregaLocalidade() {
		var form = document.forms[0];
		var campo = form.gerenciaRegional;
		var idGerenciaRegional = campo.value;

		limparDadosGeograficos('2');

		localidade = form.localidade.value;
		form.localidade.length = 0;
		AjaxService.carregaLocalidadePorGerenciaRegional(idGerenciaRegional, {callback: 
			function(localidades) {
				var opcaoInicial = new Option("", "-1");
				form.localidade.options[form.localidade.length] = opcaoInicial;
				for (key in localidades){
			  		var novaOpcao = new Option(key, localidades[key]);
			  		form.localidade.options[form.localidade.length] = novaOpcao;
  		   		} 
			}, async: false} );
		form.localidade.value = localidade;
	}
	
   function validarSetor() {
		var idSetor;
		var count = 0;
		var campo = document.forms[0].setor;
		var temSelecionado = 0;
		var escreveItem = true;
		
		for(i = 1; i <= campo.length; i++){
			if(campo[i - 1].selected){
				count ++;
				idSetor = campo[i - 1].value;
				temSelecionado = 1;
			}
		}
		
		if (count == 1) {
			document.forms[0].bairro.disabled = true;
   			document.forms[0].logradouro.disabled = true;
   			document.forms[0].rota.disabled = false;
			document.forms[0].quadra.disabled = false;
			document.forms[0].lote.disabled = false;
			var quadra = document.forms[0].quadra.value;
			var rota = document.forms[0].rota.value;
			var lote = document.forms[0].lote.value;
			document.forms[0].quadra.length = 0;
			document.forms[0].lote.length = 0;
			document.forms[0].rota.length = 0;
			AjaxService.carregaQuadrasPorSetor(idSetor, {callback: 
					function(quadras) {
						for (i = 0 ; i < quadras.size(); i+=2){
					  		var novaOpcao = new Option(quadras[i], quadras[i+1]);
					  		document.forms[0].quadra.options[document.forms[0].quadra.length] = novaOpcao;
		  		   		} 
					}, async: false} );
			document.forms[0].quadra.value = quadra;		
			escreveItem = true; 
			document.forms[0].rota.length = 0;
			AjaxService.carregaRotasPorSetor(idSetor, {callback: 
					function(rotas) {
						for (i = 0 ; i < rotas.size(); i+=2){
					  		var novaOpcao = new Option(rotas[i], rotas[i+1]);
					  		document.forms[0].rota.options[document.forms[0].rota.length] = novaOpcao;
		  		   		} 
					}, async: false} );	
			document.forms[0].quadra.value = quadra;
			document.forms[0].rota.value = rota;
			validarQuadra();
			document.forms[0].lote.value = lote;
		} else if (temSelecionado == 1) {
			document.forms[0].quadra.length = 0;
			document.forms[0].lote.length = 0;
			document.forms[0].rota.length = 0;
			document.forms[0].bairro.disabled = true;
   			document.forms[0].logradouro.disabled = true;
   			document.forms[0].rota.disabled = true;
			document.forms[0].quadra.disabled = true;
			document.forms[0].lote.disabled = true;
			document.forms[0].bairro.value = "";
	   		document.forms[0].logradouro.value = "";
   		} else {
   			document.forms[0].quadra.length = 0;
   			document.forms[0].lote.length = 0;
			document.forms[0].rota.length = 0;
   			document.forms[0].bairro.disabled = false;
   			document.forms[0].logradouro.disabled = true;
   			document.forms[0].rota.disabled = true;
			document.forms[0].quadra.disabled = true;
			document.forms[0].lote.disabled = true;
   		}
	}
	
	function validarBairro() {
		var idBairro;
		var count = 0;
		var campo = document.forms[0].bairro;
		var temSelecionado = 0;
		
		for(i = 1; i <= campo.length; i++){
			if(campo[i - 1].selected){
				count ++;
				idBairro = campo[i - 1].value;
				temSelecionado = 1;
			}
		}
		
		if (count == 1) {
			
			document.forms[0].setor.disabled = true;
			document.forms[0].quadra.disabled = true;
			document.forms[0].lote.disabled = true;
			document.forms[0].rota.disabled = true;
			var logradouro = document.forms[0].logradouro.value; 
			document.forms[0].logradouro.length = 0;
			document.forms[0].logradouro.disabled = false;
			
			AjaxService.carregaLogradourosPorBairro(idBairro, {callback: 
					function(logradouros) {
						for (key in logradouros){
					  		var novaOpcao = new Option(key, logradouros[key]);
					  		document.forms[0].logradouro.options[document.forms[0].logradouro.length] = novaOpcao;
		  		   		} 
					}, async: false} );
			document.forms[0].logradouro.value = logradouro;
		} else if (temSelecionado == 1) {
			document.forms[0].logradouro.length = 0;
			document.forms[0].setor.disabled = true;
			document.forms[0].logradouro.disabled = true;
			document.forms[0].setor.value = "";
			document.forms[0].quadra.value = "";
			document.forms[0].lote.value = "";
			document.forms[0].rota.value = "";
		} else {
			document.forms[0].logradouro.length = 0; 
			document.forms[0].setor.disabled = false;
			document.forms[0].logradouro.disabled = true;
		}
		
	}
	
   function habilitaDesabilitaAbaHidrometro(obj) {
	if (obj != null) {
		var idSservicoTipo = obj.options[obj.selectedIndex].value;
			if (idSservicoTipo != "-1") { 
				AjaxService.comparaServicoTipoSubGrupoSubstituicaoHidrometro(idSservicoTipo, {callback: 
					function(verificar) {
						if (verificar) {
							document.getElementById('2').style.display = '';
						} else {
							document.getElementById('2').style.display = 'none';
						}
					}, async: false} );
			} else {
				document.getElementById('2').style.display = 'none';			
			}
		}		
	}
	
	function iniciar() {
		if (document.forms[0].localidade.value != "-1") {
			validarLocalidade();
		} else {
			
			if (document.forms[0].setor.length == 0) {
				document.forms[0].setor.disabled = true;
			}
			if (document.forms[0].bairro.length == 0) {
				document.forms[0].bairro.disabled = true;
			}
		}
		
		validarSetor();
		if (document.forms[0].quadra.length == 0 || document.forms[0].rota.value != "") {
			document.forms[0].quadra.disabled = true;
		}
		if (document.forms[0].rota.length == 0 || document.forms[0].quadra.value != "") {
			document.forms[0].rota.disabled = true;
		}
		if (document.forms[0].lote.length == 0) {
			document.forms[0].lote.disabled = true;
		}
		validarBairro();
		if (document.forms[0].logradouro.length == 0) {
			document.forms[0].logradouro.disabled = true;
		}
		if (document.forms[0].elo.value != "") {
			document.forms[0].localidade.disabled = true;
		}

		avaliarDependenciasCamposBloqueados();
	}
	
	function selecionouRota() {
		document.forms[0].quadra.disabled = true;
		document.forms[0].quadra.value = "";
		document.forms[0].lote.disabled = true;
		document.forms[0].lote.value = "";
		var campo = document.forms[0].rota;
		var temSelecionado;
		for(i = 1; i <= campo.length; i++){
			if(campo[i - 1].selected){
				temSelecionado = 1;
			}
		}
		if (temSelecionado == 1 ) {
			document.forms[0].quadra.value = "";
			document.forms[0].lote.value = "";	
		} else {
			document.forms[0].quadra.disabled = false;
			document.forms[0].lote.disabled = false;
		}
	}
	
	function selecionouQuadra() {
		document.forms[0].rota.disabled = true;
		document.forms[0].rota.value = "";
		var campo = document.forms[0].quadra;
		var temSelecionado;
		for(i = 1; i <= campo.length; i++){
			if(campo[i - 1].selected){
				temSelecionado = 1;
			}
		}
		if (temSelecionado == 1 ) {
			document.forms[0].rota.value = "";	
		} else {
			document.forms[0].rota.disabled = false;
		}
	}

	function validarQuadra() {
		var idQuadra;
		var count = 0;
		var campo = document.forms[0].quadra;
		var temSelecionado = 0;

		for(i = 1; i <= campo.length; i++){
			if(campo[i - 1].selected){
				count ++;
				idQuadra = campo[i - 1].value;
				temSelecionado = 1;
			}
		}

		if (count == 1) {
			document.forms[0].rota.disabled = true;
			var lote = document.forms[0].lote.value; 
			document.forms[0].lote.disabled = false;
			document.forms[0].lote.length = 0;
			

			AjaxService.carregaLotesPorQuadra(idQuadra, {callback:
					function(lotes) {
						for (key in lotes) {
					  		var novaOpcao = new Option(lotes[key], lotes[key]);
					  		document.forms[0].lote.options[document.forms[0].lote.length] = novaOpcao;
		  		   		}
					}, async: false} );
			document.forms[0].lote.value = lote;
		} else if (temSelecionado == 1) {
			document.forms[0].rota.disabled = true;
			document.forms[0].rota.length = 0;
			document.forms[0].lote.disabled = true;
			document.forms[0].lote.length = 0;
		} else {
			if (document.forms[0].quadra.length == 0) {
				document.forms[0].rota.disabled = true;
			} else {
				document.forms[0].rota.disabled = false;
			}
			document.forms[0].lote.disabled = true;
			document.forms[0].lote.length = 0;
		}
	}

	function addNomeFirma(){
		
		document.forms[0].nomeFirma.value = document.forms[0].firma[document.forms[0].firma.selectedIndex].text;
		
	}
	
	function limparBorrachaElo(valor) {
		
		document.forms[0].localidade.disabled = false;
		document.forms[0].setor.disabled = true;
		document.forms[0].bairro.disabled = true;
		document.forms[0].quadra.disabled = true;
		document.forms[0].rota.disabled = true;
		document.forms[0].lote.disabled = true;
		document.forms[0].setor.length = 0;
		document.forms[0].bairro.length = 0;
		document.forms[0].rota.length = 0;
		document.forms[0].quadra.length = 0;
		document.forms[0].lote.length = 0;
		document.forms[0].logradouro.length = 0;
		document.forms[0].setor.value = 0;
		document.forms[0].bairro.value = 0;
		document.forms[0].localidade.value = "-1";
		document.forms[0].setor.value = "";
		document.forms[0].bairro.value = "";
		document.forms[0].rota.value = "";
		document.forms[0].quadra.value = "";
		document.forms[0].lote.value = "";
		document.forms[0].logradouro.value = "";
		if (valor == '1') {
			document.forms[0].elo.value = "";
			document.forms[0].nomeElo.value = "";

			ativarDesativarDependenciasArquivo();
		    ativarDesativarDependenciasGrupoFaturamento();
		    ativarDesativarDependenciasDadosGeograficos();
		}
		document.forms[0].localidade.value = "-1";
		
	}

	function validaArquivo(){
		var form = document.forms[0];
		form.action = 'exibirFiltrarImovelEmissaoOrdensSeletivas.do?menu=sim';
		form.submit();
	}

	/* Recuperar Popup */
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	    var form = document.forms[0];
	    
	    if (tipoConsulta == 'elo') {

		      if(!form.elo.disabled){
			      form.elo.value = codigoRegistro;
			      form.nomeElo.value = descricaoRegistro;
			      form.nomeElo.style.color = "#000000";
			      ativarDesativarDependenciasArquivo();
			      ativarDesativarDependenciasGrupoFaturamento();
			      ativarDesativarDependenciasDadosGeograficos();
		      }
	    }
	}

	function avaliarDependenciasCamposBloqueados() {
		ativarDesativarDependenciasDadosGeograficos();
		ativarDesativarDependenciasGrupoFaturamento();
		ativarDesativarDependenciasElo();
		ativarDesativarDependenciasArquivo();
	}

	function ativarDesativarDependenciasArquivo() {
		var form = document.forms[0];
		
		var campo;
		
		var temSetor;
		var temQuadra;
		var temRota;
		var temLote;
		var temBairro;
		var temLogradouro;

		// Verifica para Setor
		campo = form.setor;
		for(i = 1; i <= campo.length; i++){
			if(campo[i - 1].selected){
				temSetor = 1;
				break;
			}
		}

		// Verifica para Quadra
		campo = form.quadra;
		for(i = 1; i <= campo.length; i++){
			if(campo[i - 1].selected){
				temQuadra = 1;
				break;
			}
		}

		// Verifica para Rota
		campo = form.rota;
		for(i = 1; i <= campo.length; i++){
			if(campo[i - 1].selected){
				temRota = 1;
				break;
			}
		}

		// Verifica para Lote
		campo = form.lote;
		for(i = 1; i <= campo.length; i++){
			if(campo[i - 1].selected){
				temLote = 1;
				break;
			}
		}

		// Verifica para Bairro
		campo = form.bairro;
		for(i = 1; i <= campo.length; i++){
			if(campo[i - 1].selected){
				temBairro = 1;
				break;
			}
		}

		// Verifica para Logradouro
		campo = form.logradouro;
		for(i = 1; i <= campo.length; i++){
			if(campo[i - 1].selected){
				temLogradouro = 1;
				break;
			}
		}

		var temArquivoCarregado;

		if (form.elo.value != "") {
			
			if (form.arquivoCarregado.value != "") {
				temArquivoCarregado = 1;
			}
			form.arquivoMatricula.value = "";
			form.arquivoMatricula.disabled = true;
			
		} else if (form.faturamentoGrupo.value != "-1") {
			
			if (form.arquivoCarregado.value != "") {
				temArquivoCarregado = 1;
			}
			form.arquivoMatricula.value = "";
			form.arquivoMatricula.disabled = true;
		} else if (form.gerenciaRegional.value != "-1") {
			if (form.arquivoCarregado.value != "") {
				temArquivoCarregado = 1;
			}
			form.arquivoMatricula.value = "";
			form.arquivoMatricula.disabled = true;
			
		} else if (form.localidade.value != "-1") {
			
			if (form.arquivoCarregado.value != "") {
				temArquivoCarregado = 1;
			}
			form.arquivoMatricula.value = "";
			form.arquivoMatricula.disabled = true;
			
		} else if (temSetor == 1 ) {
			if (form.arquivoCarregado.value != "") {
				
				temArquivoCarregado = 1;
			}
			form.arquivoMatricula.value = "";
			form.arquivoMatricula.disabled = true;
			
		} else if (temQuadra == 1 ) {
			
			if (form.arquivoCarregado.value != "") {
				temArquivoCarregado = 1;
			}
			form.arquivoMatricula.value = "";
			form.arquivoMatricula.disabled = true;
			
		} else if (temRota == 1 ) {
			
			if (form.arquivoCarregado.value != "") {
				temArquivoCarregado = 1;
			}
			form.arquivoMatricula.value = "";
			form.arquivoMatricula.disabled = true;
			
		} else if (temLote == 1 ) {
			
			if (form.arquivoCarregado.value != "") {
				temArquivoCarregado = 1;
			}
			form.arquivoMatricula.value = "";
			form.arquivoMatricula.disabled = true;
			
		} else if (temBairro == 1 ) {
			
			if (form.arquivoCarregado.value != "") {
				temArquivoCarregado = 1;
			}
			form.arquivoMatricula.value = "";
			form.arquivoMatricula.disabled = true;
			
		} else if (temLogradouro == 1 ) {
			
			if (form.arquivoCarregado.value != "") {
				temArquivoCarregado = 1;
			}
			form.arquivoMatricula.value = "";
			form.arquivoMatricula.disabled = true;
			
		} else {
			
			form.arquivoMatricula.disabled = false;
			
		}

		if (temArquivoCarregado == 1) {
			form.action = 'exibirFiltrarImovelEmissaoOrdensSeletivas.do?menu=sim';
			form.submit();
		}
	}

	function ativarDesativarDependenciasElo() {
		var form = document.forms[0];
		
		var campo;
		
		var temSetor;
		var temQuadra;
		var temRota;
		var temLote;
		var temBairro;
		var temLogradouro;

		// Verifica para Setor
		campo = form.setor;
		for(i = 1; i <= campo.length; i++){
			if(campo[i - 1].selected){
				temSetor = 1;
				break;
			}
		}

		// Verifica para Quadra
		campo = form.quadra;
		for(i = 1; i <= campo.length; i++){
			if(campo[i - 1].selected){
				temQuadra = 1;
				break;
			}
		}

		// Verifica para Rota
		campo = form.rota;
		for(i = 1; i <= campo.length; i++){
			if(campo[i - 1].selected){
				temRota = 1;
				break;
			}
		}

		// Verifica para Lote
		campo = form.lote;
		for(i = 1; i <= campo.length; i++){
			if(campo[i - 1].selected){
				temLote = 1;
				break;
			}
		}

		// Verifica para Bairro
		campo = form.bairro;
		for(i = 1; i <= campo.length; i++){
			if(campo[i - 1].selected){
				temBairro = 1;
				break;
			}
		}

		// Verifica para Logradouro
		campo = form.logradouro;
		for(i = 1; i <= campo.length; i++){
			if(campo[i - 1].selected){
				temLogradouro = 1;
				break;
			}
		}

		var temArquivoCarregado;

		if (form.arquivoCarregado.value != "") {
			form.elo.value = "";
			form.elo.disabled = true;
			
		} else if (form.faturamentoGrupo.value != "-1") {
			
			form.elo.value = "";
			form.elo.disabled = true;
			
		} else if (form.gerenciaRegional.value != "-1") {

			form.elo.value = "";
			form.elo.disabled = true;
			
		} else if (form.localidade.value != "-1") {
			
			form.elo.value = "";
			form.elo.disabled = true;
			
		} else if (temSetor == 1 ) {

			form.elo.value = "";
			form.elo.disabled = true;
			
		} else if (temQuadra == 1 ) {
			
			form.elo.value = "";
			form.elo.disabled = true;
			
		} else if (temRota == 1 ) {
			
			form.elo.value = "";
			form.elo.disabled = true;
			
		} else if (temLote == 1 ) {
			
			form.elo.value = "";
			form.elo.disabled = true;
			
		} else if (temBairro == 1 ) {
			
			form.elo.value = "";
			form.elo.disabled = true;
			
		} else if (temLogradouro == 1 ) {
			
			form.elo.value = "";
			form.elo.disabled = true;
			
		} else {
			
			form.elo.disabled = false;
			
		}
	}

	function ativarDesativarDependenciasGrupoFaturamento() {
		var form = document.forms[0];
		
		var campo;
		
		var temSetor;
		var temQuadra;
		var temRota;
		var temLote;
		var temBairro;
		var temLogradouro;

		// Verifica para Setor
		campo = form.setor;
		for(i = 1; i <= campo.length; i++){
			if(campo[i - 1].selected){
				temSetor = 1;
				break;
			}
		}

		// Verifica para Quadra
		campo = form.quadra;
		for(i = 1; i <= campo.length; i++){
			if(campo[i - 1].selected){
				temQuadra = 1;
				break;
			}
		}

		// Verifica para Rota
		campo = form.rota;
		for(i = 1; i <= campo.length; i++){
			if(campo[i - 1].selected){
				temRota = 1;
				break;
			}
		}

		// Verifica para Lote
		campo = form.lote;
		for(i = 1; i <= campo.length; i++){
			if(campo[i - 1].selected){
				temLote = 1;
				break;
			}
		}

		// Verifica para Bairro
		campo = form.bairro;
		for(i = 1; i <= campo.length; i++){
			if(campo[i - 1].selected){
				temBairro = 1;
				break;
			}
		}

		// Verifica para Logradouro
		campo = form.logradouro;
		for(i = 1; i <= campo.length; i++){
			if(campo[i - 1].selected){
				temLogradouro = 1;
				break;
			}
		}

		var temArquivoCarregado;

		if (form.arquivoCarregado.value != "") {
			
			form.faturamentoGrupo.value = (-1);
			form.faturamentoGrupo.disabled = true;
			
		} else if (form.elo.value != "") {
			
			form.faturamentoGrupo.value = (-1);
			form.faturamentoGrupo.disabled = true;
			
		} else if (form.gerenciaRegional.value != "-1") {

			form.faturamentoGrupo.value = (-1);
			form.faturamentoGrupo.disabled = true;
			
		} else if (form.localidade.value != "-1") {
			
			form.faturamentoGrupo.value = (-1);
			form.faturamentoGrupo.disabled = true;
			
		} else if (temSetor == 1 ) {

			form.faturamentoGrupo.value = (-1);
			form.faturamentoGrupo.disabled = true;
			
		} else if (temQuadra == 1 ) {
			
			form.faturamentoGrupo.value = (-1);
			form.faturamentoGrupo.disabled = true;
			
		} else if (temRota == 1 ) {
			
			form.faturamentoGrupo.value = (-1);
			form.faturamentoGrupo.disabled = true;
			
		} else if (temLote == 1 ) {
			
			form.faturamentoGrupo.value = (-1);
			form.faturamentoGrupo.disabled = true;
			
		} else if (temBairro == 1 ) {
			
			form.faturamentoGrupo.value = (-1);
			form.faturamentoGrupo.disabled = true;
			
		} else if (temLogradouro == 1 ) {
			
			form.faturamentoGrupo.value = (-1);
			form.faturamentoGrupo.disabled = true;
			
		} else {
			
			form.faturamentoGrupo.disabled = false;
			
		}
	}

	function ativarDesativarDependenciasDadosGeograficos() {
		var form = document.forms[0];
		
		var campo;
		
		var temSetor;
		var temQuadra;
		var temRota;
		var temLote;
		var temBairro;
		var temLogradouro;

		// Verifica para Setor
		campo = form.setor;
		for(i = 1; i <= campo.length; i++){
			if(campo[i - 1].selected){
				temSetor = 1;
				break;
			}
		}

		// Verifica para Quadra
		campo = form.quadra;
		for(i = 1; i <= campo.length; i++){
			if(campo[i - 1].selected){
				temQuadra = 1;
				break;
			}
		}

		// Verifica para Rota
		campo = form.rota;
		for(i = 1; i <= campo.length; i++){
			if(campo[i - 1].selected){
				temRota = 1;
				break;
			}
		}

		// Verifica para Lote
		campo = form.lote;
		for(i = 1; i <= campo.length; i++){
			if(campo[i - 1].selected){
				temLote = 1;
				break;
			}
		}

		// Verifica para Bairro
		campo = form.bairro;
		for(i = 1; i <= campo.length; i++){
			if(campo[i - 1].selected){
				temBairro = 1;
				break;
			}
		}

		// Verifica para Logradouro
		campo = form.logradouro;
		for(i = 1; i <= campo.length; i++){
			if(campo[i - 1].selected){
				temLogradouro = 1;
				break;
			}
		}

		var temArquivoCarregado;

		if (form.arquivoCarregado.value != "") {
			
			bloquearDadosGeograficos();
			
		} else if (form.elo.value != "") {
			
			bloquearDadosGeograficos();
			
		} else if (form.gerenciaRegional.value != "-1") {

			//bloquearDadosGeograficos();
			
		} else if (form.faturamentoGrupo.value != "-1") {
			
			bloquearDadosGeograficos();
			
		} else {
			
			desbloquearDadosGeograficos();
			
		}
	}

	function limparDadosGeograficos(valor) {
		var form = document.forms[0];
		
		form.setor.length = 0;
		form.bairro.length = 0;
		form.rota.length = 0;
		form.quadra.length = 0;
		form.lote.length = 0;
		form.logradouro.length = 0;
		if (valor == '1') {
			form.gerenciaRegional.value = (-1);
		}
		if (valor == '2') {
			form.localidade.value = (-1);
		}
		form.setor.value = "";
		form.bairro.value = "";
		form.rota.value = "";
		form.quadra.value = "";
		form.lote.value = "";
		form.logradouro.value = "";
		
	}

	function bloquearDadosGeograficos() {
		var form = document.forms[0];
		
		form.localidade.disabled = true;
		form.gerenciaRegional.disabled = true;
		form.setor.disabled = true;
		form.bairro.disabled = true;
		form.quadra.disabled = true;
		form.rota.disabled = true;
		form.lote.disabled = true;
		form.logradouro.disabled = true;
		limparDadosGeograficos('1');
		
	}

	function desbloquearDadosGeograficos() {
		var form = document.forms[0];
		
		form.localidade.disabled = false;
		form.gerenciaRegional.disabled = false;
		form.setor.disabled = false;
		form.bairro.disabled = false;
		form.quadra.disabled = false;
		form.rota.disabled = false;
		form.lote.disabled = false;
		form.logradouro.disabled = false;
		
	}
</script>

</head>
<body leftmargin="5" topmargin="5" onload="iniciar();">


<html:form action="/filtrarImovelEmissaoOrdensSeletivasWizardAction"
	type="gcom.gui.atendimentopublico.ordemservico.ImovelEmissaoOrdensSeletivasActionForm"
	onsubmit="return validateImovelEmissaoOrdensSeletivasActionForm(this);"
	name="ImovelEmissaoOrdensSeletivasActionForm"  enctype="multipart/form-data" method="post">

	<jsp:include page="/jsp/util/wizard/navegacao_abas_wizard_valida_avancar_valida_voltar.jsp?numeroPagina=1" />

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="4" cellpadding="0">

		<input type="hidden" name="numeroPagina" value="1" />
		<html:hidden property="nomeFirma"/>
		<html:hidden property="arquivoCarregado"/>

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
						<td class="parabg">Filtrar Imóvel</td>
						<td width="11"><img border="0"
							src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
					</tr>
				</table>
				<p>&nbsp;</p>
			
				<table width="100%" border="0">
					<tr>
						<td colspan="2">Para filtrar o(s) im&oacute;vel(is), informe os dados abaixo:</td>
					</tr>
					
					<tr>
						<td width="165"><strong>Tipo do Serviço:<font color="#FF0000">*</font></strong></td>
						<td align="left">
							<table width="100%" border="0">
								<tr>
									<html:select property="servicoTipo" styleId="servicoTipo" onchange="habilitaDesabilitaAbaHidrometro(this);">
												<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
												<logic:present name="colecaoServicoTipo">
													<html:options collection="colecaoServicoTipo"
														property="id"
														labelProperty="descricao" />
												</logic:present>
									</html:select>
								</tr>
							</table>
						</td>
					</tr>
					
					<tr>
						<td width="165"><strong>Simulação:<font color="#FF0000">*</font></strong></td>
						<td align="left">
							<table width="100%" border="0">
								<tr>
									<logic:present name="simulacao">
										<td width="100"><html:radio value="1" property="simulacao"></html:radio>&nbsp;Sim</td>
										<logic:equal name="usuarioPermissaoGerar" value="true">
											<td align="left"><html:radio value="2" property="simulacao"></html:radio>&nbsp;N&atilde;o</td>
										</logic:equal>
										<logic:equal name="usuarioPermissaoGerar" value="false">
											<td align="left"><html:radio value="2" property="simulacao" disabled="true"></html:radio>&nbsp;N&atilde;o</td>
										</logic:equal>
									</logic:present>
									<logic:notPresent name="simulacao">
										<td width="100"><html:radio value="1" property="simulacao"></html:radio>&nbsp;Sim</td>
										<logic:equal name="usuarioPermissaoGerar" value="true">
											<td align="left"><html:radio value="2" property="simulacao"></html:radio>&nbsp;N&atilde;o</td>
										</logic:equal>
										<logic:equal name="usuarioPermissaoGerar" value="false">
											<td align="left"><html:radio value="2" property="simulacao" disabled="true"></html:radio>&nbsp;N&atilde;o</td>
										</logic:equal>
									</logic:notPresent>
								</tr>
							</table>
						</td>
					</tr>
					
					<tr>
						<td><strong>Empresa:&nbsp;<font color="#FF0000">*</font></strong></td>
						<td align="right">
							<div align="left">
								<strong>
									<logic:present name="sugestao">
										<logic:equal name="sugestao" value="1">
											<html:select property="firma" disabled="true" onchange="addNomeFirma();">
												<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
												<logic:present name="colecaoFirma">
													<html:options collection="colecaoFirma"
														property="id"
														labelProperty="descricao" />
												</logic:present>
											</html:select>
										</logic:equal>
										<logic:equal name="sugestao" value="2">
											<html:select property="firma" onchange="addNomeFirma();">
												<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
												<logic:present name="colecaoFirma">
													<html:options collection="colecaoFirma"
														property="id"
														labelProperty="descricao" />
												</logic:present>
											</html:select>
										</logic:equal>
									</logic:present>
									
									<logic:notPresent name="sugestao">
										<html:select property="firma" onchange="addNomeFirma();">
											<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
											<logic:present name="colecaoFirma">
												<html:options collection="colecaoFirma"
													property="id"
													labelProperty="descricao" />
											</logic:present>
										</html:select>
									</logic:notPresent>
								</strong>
							</div>
						</td>
					</tr>
					<tr>
					<td><strong>Título:&nbsp;<font color="#FF0000">*</font></strong></td>
						<td align="left">
								<html:textarea property="titulo" cols="53" rows="1"
								 onkeyup="limitTextArea(document.forms[0].titulo, 100, document.getElementById('utilizado'), document.getElementById('limite'));"/>
								 <br><strong><span id="utilizado">0</span>/<span id="limite">100</span></strong>
						</td>					
					</tr>
					<tr>
						<td width="165"><strong>Quantidade M&aacute;xima:</strong></td>
						<td align="left"><html:text property="quantidadeMaxima" size="5" maxlength="4"></html:text></td>
					</tr>
					
					<tr><td colspan="2"><hr></td></tr>
					
					<tr>
						<td width="20%"><strong> Arquivo:</strong>
						</td>
					<td>
						<html:file property="arquivoMatricula" accept="text" onchange="validaArquivo();ativarDesativarDependenciasElo();ativarDesativarDependenciasGrupoFaturamento();ativarDesativarDependenciasDadosGeograficos();"></html:file>
						<logic:notEmpty  name="arquivo" scope="session">
							<bean:write name="arquivo" property="fileName" scope="session"/>
						</logic:notEmpty>
						<logic:notEmpty  name="ImovelEmissaoOrdensSeletivasActionForm" property="enderecoArquivoDownload">
							<br/><a href="exibirFiltrarImovelEmissaoOrdensSeletivas.do?download=true"><bean:write name="ImovelEmissaoOrdensSeletivasActionForm" property="arquivoDownload"></bean:write> </a>
						</logic:notEmpty>
						<logic:empty  name="ImovelEmissaoOrdensSeletivasActionForm" property="enderecoArquivoDownload">
							<br/><bean:write name="ImovelEmissaoOrdensSeletivasActionForm" property="arquivoDownload"></bean:write>
						</logic:empty>
					</td>
					
				</tr>
					<tr><td colspan="2"><hr></td></tr>
					<tr>
						<td><strong>Elo:</strong></td>
						<td>
							<html:text tabindex="7" maxlength="3" property="elo" size="5"
								onkeypress="form.target=''; validaEnter(event, 'filtrarImovelEmissaoOrdensSeletivasWizardAction.do?action=exibirFiltrarImovelEmissaoOrdensSeletivasParametros&objetoConsulta=4&inscricaoTipo=origem', 'elo');" onblur="ativarDesativarDependenciasArquivo();ativarDesativarDependenciasGrupoFaturamento();ativarDesativarDependenciasDadosGeograficos();"/>
							<a href="javascript:abrirPopup('exibirPesquisarEloAction.do',295, 478);" id="btPesqElo">
								<img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar" /></a>
								
							<logic:present name="corElo">
								<logic:equal name="corElo" value="exception">
									<html:text property="nomeElo" size="35" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:equal>
		
								<logic:notEqual name="corElo" value="exception">
									<html:text property="nomeElo" size="35" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEqual>
							</logic:present>
							
							<logic:notPresent name="corElo">
								<logic:empty name="ImovelEmissaoOrdensSeletivasActionForm" property="elo">
									<html:text property="nomeElo" value="" size="35" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:empty>

								<logic:notEmpty name="ImovelEmissaoOrdensSeletivasActionForm" property="elo">
									<html:text property="nomeElo" size="35" readonly="true"
										style="background-color:#EFEFEF; border:0; color: 	#000000" />
								</logic:notEmpty>
							</logic:notPresent>
							
							<a href="javascript:limparBorrachaElo(1);">
								<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
									border="0" title="Apagar" />
							</a>
						</td>
					</tr>
					
					<tr>
						<td colspan="2">
							<hr>
						</td>
					</tr>
					
					<tr>
						<td><strong>Grupo de Faturamento:</strong></td>
						<td align="left">
							<html:select property="faturamentoGrupo" onchange="ativarDesativarDependenciasArquivo();ativarDesativarDependenciasElo();ativarDesativarDependenciasDadosGeograficos();">
								<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
								<logic:present name="colecaoFaturamentoGrupo">
									<html:options collection="colecaoFaturamentoGrupo" property="id" labelProperty="descricao" />
								</logic:present>		
							</html:select>
						</td>
					</tr>
					
					<tr>
						<td colspan="2">
							<hr>
						</td>
					</tr>
					
					<tr>
						<td colspan="2">
							<table border="0">
								<tr>
									<td width="300">
										<strong>Dados Localização Geográfica:</strong>
									</td>									
								</tr>
							</table>
						</td>
					</tr>
					
					<tr>
						<td><strong>Regional:</strong></td>
						<td align="left">
							<html:select property="gerenciaRegional" onchange="carregaLocalidade();ativarDesativarDependenciasArquivo();ativarDesativarDependenciasElo();ativarDesativarDependenciasGrupoFaturamento();">
								<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
								<logic:present name="colecaoGerenciaRegional">
									<html:options collection="colecaoGerenciaRegional" property="id" labelProperty="nome" />
								</logic:present>		
							</html:select>
						</td>
					</tr>
					
					<tr>
						<td><strong>Localidade:</strong></td>
						<td align="left">
							<html:select property="localidade" onchange="validarLocalidade();ativarDesativarDependenciasArquivo();ativarDesativarDependenciasElo();ativarDesativarDependenciasGrupoFaturamento();">
								<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
								<logic:present name="colecaoLocalidade">
									<html:options collection="colecaoLocalidade" property="id" labelProperty="descricaoComId" />
								</logic:present>		
							</html:select>
						</td>
					</tr>

					<tr>
						<td colspan="3">
							<table cellpadding="0" cellspacing="0" border="0">
								<tr valign="top">
									<td>
										<table cellpadding="0" cellspacing="2">
											<tr>
												<td>
													<strong>Setor:</strong>
												</td>
											</tr>
											<tr>
												<td>
													<html:select property="setor" multiple="true" style="width: 280px; height: 100px" 
													onchange="validarSetor();ativarDesativarDependenciasArquivo();ativarDesativarDependenciasElo();ativarDesativarDependenciasGrupoFaturamento();">
														<logic:present name="colecaoSetores">
															<html:options collection="colecaoSetores" property="id" labelProperty="codigo" />
														</logic:present>
													</html:select>
												</td>
											</tr>
											
											<tr>
												<td colspan="2"> 
													<table cellpadding="0" cellspacing="2">
														<tr>
															<td>
																<strong>Quadra:</strong>						
															</td>
															<td>
																<strong>Rota:</strong>																
															</td>
														</tr>
														<tr>
															<td>
																<html:select property="quadra" multiple="true" style="width: 138px; height: 100px" 
																onchange="validarQuadra();ativarDesativarDependenciasArquivo();ativarDesativarDependenciasElo();ativarDesativarDependenciasGrupoFaturamento();">
																<logic:present name="colecaoQuadras">
																	<html:options collection="colecaoQuadras" property="id" labelProperty="numeroQuadra" />
																</logic:present>
																</html:select>						
															</td>
															<td>
																<html:select property="rota" multiple="true" style="width: 138px; height: 100px" 
																onchange="selecionouRota();ativarDesativarDependenciasArquivo();ativarDesativarDependenciasElo();ativarDesativarDependenciasGrupoFaturamento();">
																<logic:present name="colecaoRotas">
																	<html:options collection="colecaoRotas" property="id" labelProperty="codigo" />
																</logic:present>
																</html:select>																
															</td>														
														</tr>
													</table>
												</td>											
											</tr>

											<tr>
												<td>
													<strong>Lote:</strong>
												</td>
											</tr>
											<tr>
												<td>
													<html:select property="lote" multiple="true" style="width: 138px; height: 100px" 
													onchange="ativarDesativarDependenciasArquivo();ativarDesativarDependenciasElo();ativarDesativarDependenciasGrupoFaturamento();">
														<logic:present name="colecaoLotes">
															<html:options collection="colecaoLotes" property="lote" labelProperty="lote"/>
														</logic:present>
													</html:select>				
												</td>														
											</tr>

										</table>
									</td>

									<td>
										<table cellpadding="0" cellspacing="2">
											<tr>
												<td>
													<strong>Bairro:</strong>						
												</td>
											</tr>
											<tr>
												<td colspan="2">
													<html:select property="bairro" multiple="true" style="width: 280px; height: 100px" 
													onchange="validarBairro();ativarDesativarDependenciasArquivo();ativarDesativarDependenciasElo();ativarDesativarDependenciasGrupoFaturamento();">
														<logic:present name="colecaoBairros">
															<html:options collection="colecaoBairros" property="id" labelProperty="nome" />
														</logic:present>
													</html:select>
												</td>
											</tr>
											<tr>
												<td>
													<strong>Logradouro:</strong>						
												</td>
											</tr>
											<tr>
												<td colspan="2">
													<html:select property="logradouro" multiple="true" style="width: 280px; height: 100px"
													onchange="ativarDesativarDependenciasArquivo();ativarDesativarDependenciasElo();ativarDesativarDependenciasGrupoFaturamento();">
														<logic:present name="colecaoLogradouros">
															<html:options collection="colecaoLogradouros" property="id" labelProperty="nome" />
														</logic:present>
													</html:select>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
															 
					<tr>
						<td colspan="2">
							<hr>
						</td>
					</tr>
					
					
					<tr>
						<td>&nbsp;</td>
						<td colspan="3" align="right">&nbsp;</td>
					</tr>
					
					<tr>
						<td colspan="4">
							<div align="right">
								<jsp:include page="/jsp/util/wizard/navegacao_botoes_wizard_valida_avancar_valida_voltar.jsp?numeroPagina=1" />
							</div>
						</td>
					</tr>
				</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>

	<logic:present name="servicoTipo">
		<logic:equal name="servicoTipo" value="INSTALACAO">
			<script>document.getElementById('2').style.display = 'none';--</script>
		</logic:equal>
		<logic:notEqual name="servicoTipo" value="INSTALACAO">
			<script>document.getElementById('2').style.display = '';</script>
		</logic:notEqual>
	</logic:present>
	<logic:notPresent name="servicoTipo">
		<script>document.getElementById('2').style.display = 'none';</script>
	</logic:notPresent>
	
</html:form>

</body>
</html:html>