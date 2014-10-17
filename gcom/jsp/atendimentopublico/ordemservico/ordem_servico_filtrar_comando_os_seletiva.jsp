<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page import="gcom.util.ConstantesSistema" isELIgnored="false"%>

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"	href="<bean:message key="caminho.css"/>EstilosCompesa.css"	type="text/css">

<script language="JavaScript" 	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
	
<html:javascript staticJavascript="false"  formName="ConsultarComandoOsSeletivaActionForm"/>
	
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/engine.js'> </script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/util.js'> </script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/interface/AjaxService.js'> </script>

<script language="JavaScript">

function replicaDataComando() {
	var form = document.forms[0];
	form.dataComandoFinal.value = form.dataComandoInicial.value;
}

function replicaDataRealizacao() {
	var form = document.forms[0];
	form.dataRealizacaoComandoFinal.value = form.dataRealizacaoComandoInicial.value;
	
}

function extendeTabela(tabela,display){

	var form = document.forms[0];
	var temElo;
	var count=0;
	
	campo = form.elo;
	for(i = 1; i <= campo.length; i++){
		if(campo[i - 1].selected){
			temElo = 1;
			count ++;
			identificador= campo[i - 1].value;
			break;
		}
	}
	if(count!=1){
	
		if(display){
			eval('layerHide'+tabela).style.display = 'none';
			eval('layerShow'+tabela).style.display = 'block';
		}else{
		eval('layerHide'+tabela).style.display = 'block';
			eval('layerShow'+tabela).style.display = 'none';
		}
	}
}

function retrairTela(){
	
	var form = document.forms[0];
	var temElo;
	var count=0;
	
	campo = form.elo;
	for(i = 1; i <= campo.length; i++){
		if(campo[i - 1].selected){
			temElo = 1;
			count ++;
			identificador= campo[i - 1].value;
			break;
		}
	}
	
	if(count==1){

		var tabela='Localizacao';
		var display=false;
		
		eval('layerHide'+tabela).style.display = 'block';
		eval('layerShow'+tabela).style.display = 'none';
		
		
	}
}


function ativarDesativarDependenciasElo() {
	var form = document.forms[0];

	var rescreve=false;
	var temFaturamentoGrupo;
	var temGerenciaRegional;
	var temLocalidade;
	var temSetor;
	var temQuadra;
	var temRota;
	var temLote;
	var temBairro;
	var temLogradouro;
	
	// Verifica para Grupo Faturamento
	campo = form.faturamentoGrupo;
	for(i = 1; i <= campo.length; i++){
		if(campo[i - 1].selected){
			temFaturamentoGrupo = 1;
			identificador= campo[i - 1].value;

			if(identificador==-1 && rescreve==false){
				rescreve=true;
			}
			
			break;
		}
	}
	
	// Verifica Gerencia Regional
	campo = form.gerenciaRegional;
	for(i = 1; i <= campo.length; i++){
		if(campo[i - 1].selected){
			temGerenciaRegional = 1;
			identificador= campo[i - 1].value;
			
			if(identificador==-1 && rescreve==false){
				rescreve=true;
			}
			
			break;
		}
	}
	
	// Verifica Localidade
	campo = form.localidade;
	for(i = 1; i <= campo.length; i++){
		if(campo[i - 1].selected){
			temLocalidade = 1;
			identificador= campo[i - 1].value;
			if(identificador==-1 && rescreve==false){
				rescreve=true;
			}
			break;
		}
	}
	
	// Verifica para Setor
	campo = form.setor;
	for(i = 1; i <= campo.length; i++){
		if(campo[i - 1].selected){
			temSetor = 1;
			identificador= campo[i - 1].value;
			if(identificador==-1 && rescreve==false){
				rescreve=true;
			}
			break;
		}
	}

	// Verifica para Quadra
	campo = form.quadra;
	for(i = 1; i <= campo.length; i++){
		if(campo[i - 1].selected){
			temQuadra = 1;
			identificador= campo[i - 1].value;
			if(identificador==-1 && rescreve==false){
				rescreve=true;
			}
			break;
		}
	}

	// Verifica para Rota
	campo = form.rota;
	for(i = 1; i <= campo.length; i++){
		if(campo[i - 1].selected){
			temRota = 1;
			identificador= campo[i - 1].value;
			if(identificador==-1 && rescreve==false){
				rescreve=true;
			}
			break;
		}
	}

	// Verifica para Lote
	campo = form.lote;
	for(i = 1; i <= campo.length; i++){
		if(campo[i - 1].selected){
			temLote = 1;
			identificador= campo[i - 1].value;
			if(identificador==-1 && rescreve==false){
				rescreve=true;
			}
			break;
		}
	}

	// Verifica para Bairro
	campo = form.bairro;
	for(i = 1; i <= campo.length; i++){
		if(campo[i - 1].selected){
			temBairro = 1;
			identificador= campo[i - 1].value;
			if(identificador==-1 && rescreve==false){
				reescreve=true;
			}
			break;
		}
	}

	// Verifica para Logradouro
	campo = form.logradouro;
	for(i = 1; i <= campo.length; i++){
		if(campo[i - 1].selected){
			temLogradouro = 1;
			identificador= campo[i - 1].value;
			if(identificador==-1 && rescreve==false){
				reescreve=true;
			}
			break;
		}
	}
	
	if (temFaturamentoGrupo == 1) {

		form.elo.length = 0;
		form.elo.disabled = true;
		
	}else if (temGerenciaRegional == 1) {
		
		form.elo.length = 0;
		form.elo.disabled = true;
		
	}else if (temLocalidade == 1) {
		
		form.elo.length = 0;
		form.elo.disabled = true;
		
	} else if (temSetor == 1 ) {

		form.elo.length = 0;
		form.elo.disabled = true;
		
	} else if (temQuadra == 1 ) {
		
		form.elo.length = 0;
		form.elo.disabled = true;
		
	} else if (temRota == 1 ) {
		
		form.elo.length = 0;
		form.elo.disabled = true;
		
	} else if (temLote == 1 ) {
		
		form.elo.length = 0;
		form.elo.disabled = true;
		
	} else if (temBairro == 1 ) {
		
		form.elo.length = 0;
		form.elo.disabled = true;
		
	} else if (temLogradouro == 1 ) {
		
		form.elo.length = 0;
		form.elo.disabled = true;
		
	}else{
		
		elo = form.elo.value;
		form.elo.length = 0;
		AjaxService.carregarElo({callback: 
			function(elos) {
				for (i = 0 ; i < elos.size(); i+=2){
			  		var novaOpcao = new Option(elos[i], elos[i+1]);
			  		document.forms[0].elo.options[document.forms[0].elo.length] = novaOpcao;
			   		} 
			}, async: false} );
	document.forms[0].elo.value = elo;
	form.elo.disabled = false;
	}
}

function carregaLocalidade() {

	var count=0;
	var form = document.forms[0];
	var campo = form.gerenciaRegional;
	var idGerenciaRegional = campo.value;
	var identificador;
	
	campo = form.gerenciaRegional;
	for(i = 1; i <= campo.length; i++){
		if(campo[i-1].selected){
			count++;
			identificador=campo[i-1].value;
		}
	}
	if(identificador==-1 || count>1 ){
	
		form.localidade.length = 0;
		form.localidade.disabled = true;
		
	}else{
		
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
		form.localidade.disabled = false;
		form.setor.length = 0;
		form.quadra.length = 0;
		form.rota.length = 0;
		form.lote.length = 0;
		form.bairro.length = 0;
		form.logradouro.length = 0;
	}
}

function carregarFaturamentoGrupo(){
	
	var form = document.forms[0];
	var temElo;
	var count=0;
	// Verifica para Grupo Faturamento
	campo = form.elo;
	for(i = 1; i <= campo.length; i++){
		if(campo[i - 1].selected){
			temElo = 1;
			count ++;
			identificador= campo[i - 1].value;
			break;
		}
	}
	if(count==1){
		
		form.faturamentoGrupo.length = 0;
		form.faturamentoGrupo.disabled = true;
		form.gerenciaRegional.length = 0;
		form.localidade.length = 0;
		form.setor.length = 0;
		form.quadra.length = 0;
		form.rota.length = 0;
		form.lote.length = 0;
		form.bairro.length = 0;
		form.logradouro.length = 0;
		
	}else{
		faturamentoGrupo = form.faturamentoGrupo.value;
		form.faturamentoGrupo.length = 0;
		AjaxService.carregarFaturamentoGrupo({callback: 
			function(grupos) {
				for (i = 0 ; i < grupos.size(); i+=2){
			  		var novaOpcao = new Option(grupos[i], grupos[i+1]);
			  		document.forms[0].faturamentoGrupo.options[document.forms[0].faturamentoGrupo.length] = novaOpcao;
			   		} 
			}, async: false} );
	document.forms[0].faturamentoGrupo.value = faturamentoGrupo;
	form.faturamentoGrupo.disabled = false;
	}
}

function carregarRegional(){
	
	var form = document.forms[0];
	var temgerenciaRegional;
	
	// Verifica para Gerencia Regional 
	campo = form.gerenciaRegional;
	
	gerenciaRegional = form.gerenciaRegional.value;
	form.gerenciaRegional.length = 0;
	AjaxService.carregarGerenciaRegioanl({callback: 
		function(gerencias) {
			for (i = 0 ; i < gerencias.size(); i+=2){
		  		var novaOpcao = new Option(gerencias[i], gerencias[i+1]);
		  		document.forms[0].gerenciaRegional.options[document.forms[0].gerenciaRegional.length] = novaOpcao;
		   		} 
		}, async: false} );
document.forms[0].gerenciaRegional.value = gerenciaRegional;
form.gerenciaRegional.disabled = false;
	
}


function validarLocalidade(){
	
	var count=0;
	var form = document.forms[0];
	var campo = form.localidade;
	var idLocalidade = campo.value;
	var identificador;
	
	for(i = 1; i <= campo.length; i++){
		if(campo[i-1].selected){
			count++;
			identificador=campo[i-1].value;
		}
	}
	if(identificador==-1 || count>1 ){
	
		form.setor.length = 0;
		form.setor.disabled = true;
		form.bairro.disabled = true;
		form.bairro.length = 0;
		
	}else{
		
		setor = document.forms[0].bairro.value;
		document.forms[0].setor.length = 0;
		AjaxService.carregaSetoresPorLocalidade(idLocalidade, {callback: 
				function(setores) {
					for (i = 0 ; i < setores.size(); i+=2){
			  			var novaOpcao = new Option(setores[i], setores[i+1]);
			  			document.forms[0].setor.options[document.forms[0].setor.length] = novaOpcao;
	  		   		} 
				}, async: false} );
		document.forms[0].setor.value = setor;
		form.setor.disabled = false;
		
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
		form.bairro.disabled = false;
		form.logradouro.disabled=true;
		form.logradouro.length = 0;
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
function validarSetor() {
	var idSetor;
	var count = 0;
	var campo = document.forms[0].setor;
	var temSelecionado = 0;
	var escreveItem = true;
	var form = document.forms[0];
	
	for(i = 1; i <= campo.length; i++){
		if(campo[i - 1].selected){
			count ++;
			idSetor = campo[i - 1].value;
			temSelecionado = 1;
		}
	}
	if (count == 1) {
		var quadra = document.forms[0].quadra.value;
		var rota = document.forms[0].rota.value;
		var lote = document.forms[0].lote.value;
		
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
		form.quadra.disabled = false;
		form.rota.disabled = false;
		
	}else{
			document.forms[0].quadra.length = 0;
			document.forms[0].lote.length = 0;
			document.forms[0].rota.length = 0;
			document.forms[0].bairro.disabled = false;
			document.forms[0].logradouro.disabled = true;
			document.forms[0].lote.disabled = true;
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
		document.forms[0].lote.disabled = false;
	} else {
		if (document.forms[0].quadra.length == 0) {
			document.forms[0].rota.disabled = true;
			document.forms[0].rota.value = "";
		} else {
			document.forms[0].rota.disabled = false;
		}
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
	} else {
		document.forms[0].logradouro.length = 0; 
		document.forms[0].setor.disabled = false;
		document.forms[0].logradouro.disabled = true;
	}
	
}

function ControleCategoria() {
	var form = document.forms[0];
	var campo = form.categoria;
	var count = 0;
	var temSelecionado = 0;
	var idCategoria;
	
	for(i = 1; i <= campo.length; i++){
		if(campo[i - 1].selected){
			count ++;
			idCategoria = campo[i - 1].value;
			temSelecionado = 1;
		}
	}
	
	if (count == 1 && idCategoria != "-1") {
		form.subCategoria.disabled = false;
		AjaxService.carregaSubcategorias(idCategoria, {callback: 
			function(list) {
    			//Função que remove caso exista os valores da combo.  
                DWRUtil.removeAllOptions("subcategoria");  
                //Adicionando valores na combo.  
                DWRUtil.addOptions("subcategoria", {'-1':' '});
                DWRUtil.addOptions("subcategoria", list);
			}
		});
	} else {
		form.subCategoria.length = 0;
		form.subCategoria.value = "-1";
		form.subCategoria.disabled = true;
	}
}

function limparForm(){

	var form = document.forms[0];

	  form.titulo="";
	  form.tipoPesquisa="";
	  form.firma.length = 0;;
	  form.servicoTipo.length = 0;
	  form.dataComandoInicial="";
	  form.dataComandoFinal="";
	  form.dataRealizacaoComandoInicial="";
	  form.dataRealizacaoComandoFinal="";
	  form.situacaoComando.selected=0;
	  form.elo.length = 0;
	  form.faturamentoGrupo.length = 0;
	  form.gerenciaRegional.length = 0;
	  form.localidade.length = 0;
	  form.setor.length = 0;
	  form.quadra.length = 0;
	  form.rota.length = 0;
	  form.lote.length = 0;
	  form.bairro.length = 0;
	  form.logradouro.length = 0;
	  form.perfilImovel.length = 0;
	  form.perfilImovelDescricao.length = 0;
	  form.categoria.length = 0;
	  form.categoriaDescricao.length = 0;
	  form.subCategoria.length = 0;
	  form.subCategoriaDescricao.length = 0;
	  form.ligacaoAguaSituacao.length = 0;
	  form.ligacaoEsgotoSituacao.length = 0;
	
	
	form.action = "/gsan/exibirFiltrarComandoOsSeletivaoAction.do";
	form.submit();
}

function validarForm(){
		var form = document.forms[0];
		
		form.action = "/gsan/filtrarComandoOsSeletivaAction.do";
		form.submit();
}

function validarRota(){
	
	var form = document.forms[0];
	var campo = form.rota;
	var count = 0;
	var temSelecionado = 0;
	var idrota;
	
	for(i = 1; i <= campo.length; i++){
		if(campo[i - 1].selected){
			count ++;
			idrota = campo[i - 1].value;
			temSelecionado = 1;
		}
	}
	if(temSelecionado==1){
		document.forms[0].quadra.value = "";
   		document.forms[0].lote.value = "";
		document.forms[0].quadra.disabled = true;
		document.forms[0].lote.disabled = true;
		
	}else{
		
		document.forms[0].quadra.disabled = false;
		document.forms[0].lote.disabled = false;
		
	}
}

function validarBairro(){
	
	var form = document.forms[0];
	var campo = form.bairro;
	var count = 0;
	var temSelecionado = 0;
	var idBairro;
	
	for(i = 1; i <= campo.length; i++){
		if(campo[i - 1].selected){
			count ++;
			idBairro = campo[i - 1].value;
			temSelecionado = 1;
		}
	}
	

	if(temSelecionado==1){

		document.forms[0].setor.value = "";
		document.forms[0].rota.value = "";
   		document.forms[0].quadra.value = "";
   		document.forms[0].lote.value = "";
		document.forms[0].setor.disabled = true;
		document.forms[0].rota.disabled = true;
		document.forms[0].quadra.disabled = true;
		document.forms[0].lote.disabled = true;
		
	}else{
		
		document.forms[0].setor.disabled = false;
		document.forms[0].rota.disabled = false;
		document.forms[0].quadra.disabled = false;
		document.forms[0].lote.disabled = false;
		
	}
}

function ativarDesativarDependenciasfaturamentoGrupo() {
	var form = document.forms[0];

	var rescreve=false;
	var temGerenciaRegional;
	var temLocalidade;
	var temSetor;
	var temQuadra;
	var temRota;
	var temLote;
	var temBairro;
	var temLogradouro;
	
		
	// Verifica Gerencia Regional
	campo = form.gerenciaRegional;
	for(i = 1; i <= campo.length; i++){
		if(campo[i - 1].selected){
			temGerenciaRegional = 1;
			identificador= campo[i - 1].value;
			
			if(identificador==-1 && rescreve==false){
				rescreve=true;
			}
			
			break;
		}
	}
	
	// Verifica Localidade
	campo = form.localidade;
	for(i = 1; i <= campo.length; i++){
		if(campo[i - 1].selected){
			temLocalidade = 1;
			identificador= campo[i - 1].value;
			if(identificador==-1 && rescreve==false){
				rescreve=true;
			}
			break;
		}
	}
	
	// Verifica para Setor
	campo = form.setor;
	for(i = 1; i <= campo.length; i++){
		if(campo[i - 1].selected){
			temSetor = 1;
			identificador= campo[i - 1].value;
			if(identificador==-1 && rescreve==false){
				rescreve=true;
			}
			break;
		}
	}

	// Verifica para Quadra
	campo = form.quadra;
	for(i = 1; i <= campo.length; i++){
		if(campo[i - 1].selected){
			temQuadra = 1;
			identificador= campo[i - 1].value;
			if(identificador==-1 && rescreve==false){
				rescreve=true;
			}
			break;
		}
	}

	// Verifica para Rota
	campo = form.rota;
	for(i = 1; i <= campo.length; i++){
		if(campo[i - 1].selected){
			temRota = 1;
			identificador= campo[i - 1].value;
			if(identificador==-1 && rescreve==false){
				rescreve=true;
			}
			break;
		}
	}

	// Verifica para Lote
	campo = form.lote;
	for(i = 1; i <= campo.length; i++){
		if(campo[i - 1].selected){
			temLote = 1;
			identificador= campo[i - 1].value;
			if(identificador==-1 && rescreve==false){
				rescreve=true;
			}
			break;
		}
	}

	// Verifica para Bairro
	campo = form.bairro;
	for(i = 1; i <= campo.length; i++){
		if(campo[i - 1].selected){
			temBairro = 1;
			identificador= campo[i - 1].value;
			if(identificador==-1 && rescreve==false){
				reescreve=true;
			}
			break;
		}
	}

	// Verifica para Logradouro
	campo = form.logradouro;
	for(i = 1; i <= campo.length; i++){
		if(campo[i - 1].selected){
			temLogradouro = 1;
			identificador= campo[i - 1].value;
			if(identificador==-1 && rescreve==false){
				reescreve=true;
			}
			break;
		}
	}
	
	if (temGerenciaRegional == 1) {
		
		form.faturamentoGrupo.length = 0;
		form.faturamentoGrupo.disabled = true;
		
	}else if (temLocalidade == 1) {
		
		form.faturamentoGrupo.length = 0;
		form.faturamentoGrupo.disabled = true;
		
	} else if (temSetor == 1 ) {

		form.faturamentoGrupo.length = 0;
		form.faturamentoGrupo.disabled = true;
		
	} else if (temQuadra == 1 ) {
		
		form.faturamentoGrupo.length = 0;
		form.faturamentoGrupo.disabled = true;
		
	} else if (temRota == 1 ) {
		
		form.faturamentoGrupo.length = 0;
		form.faturamentoGrupo.disabled = true;
		
	} else if (temLote == 1 ) {
		
		form.faturamentoGrupo.length = 0;
		form.faturamentoGrupo.disabled = true;
		
	} else if (temBairro == 1 ) {
		
		form.faturamentoGrupo.length = 0;
		form.faturamentoGrupo.disabled = true;
		
	} else if (temLogradouro == 1 ) {
		
		form.faturamentoGrupo.length = 0;
		form.faturamentoGrupo.disabled = true;
		
	}else{
		
		faturamentoGrupo = form.faturamentoGrupo.value;
		form.faturamentoGrupo.length = 0;
		AjaxService.carregarFaturamentoGrupo({callback: 
			function(grupos) {
				for (i = 0 ; i < grupos.size(); i+=2){
			  		var novaOpcao = new Option(grupos[i], grupos[i+1]);
			  		document.forms[0].faturamentoGrupo.options[document.forms[0].faturamentoGrupo.length] = novaOpcao;
			   		} 
			}, async: false} );
	document.forms[0].faturamentoGrupo.value = faturamentoGrupo;
	form.faturamentoGrupo.disabled = false;
	}
}


</script>

</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('${requestScope.nomeCampo}');">
<html:form action="/filtrarComandoOsSeletivaAction" method="post" name="ConsultarComandoOsSeletivaActionForm"
	type="gcom.gui.atendimentopublico.ordemservico.ConsultarComandoOsSeletivaActionForm">
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
			
			<td valign="top" class="centercoltext"><!--Início Tabela Reference a Páginação da Tela de Processo-->
			
			<p>&nbsp;</p>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">Filtrar Comando OS Seletiva</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>						
				</tr>				
			</table>
			
		<p>&nbsp;</p>
			<table width="100%" height="100%" border="0">
				<%String cor = "#FFFFFF";%>
				<tr>
				<td colspan="1">
					<td align="left">Para Filtrar um comando, informe os dados abaixo:</td>
					<table width="100%" align="center"  border="0">
						<tr>
						<tr>
							<td><strong>Título:&nbsp;</font></strong></td>
								<td align="left">
								<html:textarea property="titulo" cols="53" rows="1"
								 onkeyup="limitTextArea(document.forms[0].titulo, 100, document.getElementById('utilizado'), document.getElementById('limite'));"/>
								 <br><strong><span id="utilizado">0</span>/<span id="limite">100</span></strong>
								</td>					
						</tr>
						<tr>
					<td>&nbsp;</td>
					<td><html:radio property="tipoPesquisa"
						value="<%=ConstantesSistema.TIPO_PESQUISA_INICIAL.toString()%>" />
							Iniciando pelo texto<html:radio property="tipoPesquisa"
							tabindex="5"
							value="<%=ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString()%>" />
							Contendo o texto</td>
					</tr>
						  <tr>
							<td><strong>Empresa:</strong></td>
								<td align="left">
									<html:select property="firma" multiple="true"
											style="width: 450px; height: 100px" >
									<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
										<logic:present name="colecaoFirma">
												<html:options collection="colecaoFirma" property="id"
												labelProperty="descricao" />
										</logic:present>
									</html:select>
						    	</td>
							</tr>
							<tr>
							<td><strong>Serviço Tipo:</strong></td>
								<td align="left">
									<html:select property="servicoTipo" multiple="true"
											style="width: 450px; height: 100px" >
										<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
										<logic:present name="colecaoServicoTipo">
												<html:options collection="colecaoServicoTipo" property="id"
												labelProperty="descricao" />
										</logic:present>
									</html:select>
						    	</td>
							</tr>
				<tr>
	                <td>
	                	<strong>Período do Comando:</strong>
	                </td>
                
	                <td colspan="6">
	                	<span class="style2">
	                	
	                	<strong> 
							
							<html:text property="dataComandoInicial" 
								size="11" 
								maxlength="10" 
								tabindex="11" 
								onkeypress="javascript:return isCampoNumerico(event); " 
								onkeyup="mascaraData(this, event);replicaDataComando();"/>
							
							<a href="javascript:abrirCalendarioReplicando('ConsultarComandoOsSeletivaActionForm', 'dataComandoInicial','dataComandoFinal');">
								<img border="0" 
									src="<bean:message key='caminho.imagens'/>calendario.gif" 
									width="16" 
									height="15" 
									border="0" 
									alt="Exibir Calendário"/></a>
							à 
							
							<html:text property="dataComandoFinal" 
								size="11" 
								maxlength="10" 
								tabindex="12" 
								onkeypress="javascript:return isCampoNumerico(event); " 
								onkeyup="javascript:mascaraData(this, event);"/>
								
							<a href="javascript:abrirCalendario('ConsultarComandoOsSeletivaActionForm', 'dataComandoFinal');">
								<img border="0" 
									src="<bean:message key='caminho.imagens'/>calendario.gif" 
									width="16" 
									height="15" 
									border="0" 
									alt="Exibir Calendário"/></a>
							
							</strong>(dd/mm/aaaa)<strong> 
	                  	</strong>
	                  	</span>
	              	</td>
              	</tr>
              	<tr>
	                <td>
	                	<strong>Período de Realização do Comando:</strong>
	                </td>
					
					<td colspan="6">
	                	<span class="style3">
	                	<strong> 
							
							<html:text property="dataRealizacaoComandoInicial" 
								size="11" 
								maxlength="10" 
								tabindex="11" 
								onkeypress="javascript:return isCampoNumerico(event); " 
								onkeyup="mascaraData(this, event);replicaDataRealizacao();"/>
							
							<a href="javascript:abrirCalendarioReplicando('ConsultarComandoOsSeletivaActionForm', 'dataRealizacaoComandoInicial','dataRealizacaoComandoFinal');">
								<img border="0" 
									src="<bean:message key='caminho.imagens'/>calendario.gif" 
									width="16" 
									height="15" 
									border="0" 
									alt="Exibir Calendário"/></a>
							à 
							
							<html:text property="dataRealizacaoComandoFinal" 
								size="11" 
								maxlength="10" 
								tabindex="12" 
								onkeypress="javascript:return isCampoNumerico(event); " 
								onkeyup="javascript:mascaraData(this, event);"/>
								
							<a href="javascript:abrirCalendario('ConsultarComandoOsSeletivaActionForm', 'dataRealizacaoComandoFinal');">
								<img border="0" 
									src="<bean:message key='caminho.imagens'/>calendario.gif" 
									width="16" 
									height="15" 
									border="0" 
									alt="Exibir Calendário"/></a>
							
							</strong>(dd/mm/aaaa)<strong> 
	                  	</strong>
	                  	</span>
	              	</td>
              	</tr>
              	<tr>
	                <td>
	                	<strong>Situação do Comando:</strong>
	                </td>
	                <td colspan="6">
						<html:select property="situacaoComando">
												<html:option value="<%="0"%>">Todos</html:option>
												<html:option value="<%="1"%>">Realizado</html:option>
												<html:option value="<%="2"%>">Não Realizado</html:option>
						</html:select>
	              	</td>
              	</tr>
					<tr>
						<td><strong>Elo:</strong></td>
								<td align="left">
									<html:select property="elo" multiple="true"
											style="width: 450px; height: 100px" onchange="javaScript:carregarFaturamentoGrupo();carregarRegional();retrairTela();" >
										<logic:present name="colecaoElo">
												<html:options collection="colecaoElo" property="id"
												labelProperty="descricao" />
										</logic:present>
									</html:select>
						    	</td>
						</tr>
						<tr>
						<td><strong>Grupo Faturamento:</strong></td>
								<td align="left">
									<html:select property="faturamentoGrupo" multiple="true"
											style="width: 450px; height: 100px" onchange="javaScript:ativarDesativarDependenciasElo();carregarRegional();" >
										<logic:present name="colecaoGrupoFaturamento">
												<html:options collection="colecaoGrupoFaturamento" property="id"
												labelProperty="descricao" />
										</logic:present>
									</html:select>
						    	</td>
						</tr>
			<tr>
				<td colspan="4">
					<div id="layerHideLocalizacao" style="display: block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('Localizacao',true);" /><b>Dados de Localização Geográfica
							</b></a></span></td>
						</tr>
					</table>
					</div>

					<div id="layerShowLocalizacao" style="display: none">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('Localizacao',false);" /><b>Dados de Localização Geográfica
							</b></a></span></td>
						</tr>
							<tr bgcolor="#cbe5fe">
								<td>
							  	   <table width="100%" align="center">
							  	<tr>
								<td><strong>Regional:</strong></td>
									<td align="left">
										<html:select property="gerenciaRegional" multiple="true"
												style="width: 220px; height: 100px" onchange="javaScript:ativarDesativarDependenciasElo();carregaLocalidade();ativarDesativarDependenciasfaturamentoGrupo();" >
											<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>	
											<logic:present name="colecaoGerenciaRegional">
													<html:options collection="colecaoGerenciaRegional" property="id"
													labelProperty="nome" />
											</logic:present>
										</html:select>
						    		</td>
						    		<td><strong>Localidade:</strong></td>
									<td align="left">
										<html:select property="localidade" multiple="true"
												style="width: 220px; height: 100px" onchange="javaScript:ativarDesativarDependenciasElo();validarLocalidade();ativarDesativarDependenciasfaturamentoGrupo();" >
											<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>	
											<logic:present name="colecaoLocalidade">
													<html:options collection="colecaoLocalidade" property="id"
													labelProperty="descricao" />
											</logic:present>
										</html:select>
						    		</td>
								</tr>
								<tr>
								<td><strong>Setor:</strong></td>
									<td align="left">
										<html:select property="setor" multiple="true"
												style="width: 220px; height: 100px" onchange="javaScript:ativarDesativarDependenciasElo();validarSetor();ativarDesativarDependenciasfaturamentoGrupo();" >
											<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>	
											<logic:present name="colecaoSetor">
													<html:options collection="colecaoSetor" property="id"
													labelProperty="codigo" />
											</logic:present>
										</html:select>
						    		</td>
						    		<td><strong>Quadra:</strong></td>
									<td align="left">
										<html:select property="quadra" multiple="true"
												style="width: 220px; height: 100px" onchange="javaScript:ativarDesativarDependenciasElo();validarQuadra();ativarDesativarDependenciasfaturamentoGrupo();" >
											<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>	
											<logic:present name="colecaoQuadra">
													<html:options collection="colecaoQuadra" property="id"
													labelProperty="numeroQuadra" />
											</logic:present>
										</html:select>
						    		</td>
								</tr>
								<tr>
								<td><strong>Rota:</strong></td>
									<td align="left">
										<html:select property="rota" multiple="true"
												style="width: 220px; height: 100px" onchange="javaScript:validarRota();ativarDesativarDependenciasElo();ativarDesativarDependenciasfaturamentoGrupo();" >
											<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>	
											<logic:present name="colecaoRota">
													<html:options collection="colecaoRota" property="id"
													labelProperty="codigo" />
											</logic:present>
										</html:select>
						    		</td>
						    		<td><strong>Lote:</strong></td>
									<td align="left">
										<html:select property="lote" multiple="true"
												style="width: 220px; height: 100px" onchange="javaScript:ativarDesativarDependenciasElo();ativarDesativarDependenciasfaturamentoGrupo();" >
											<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>	
											<logic:present name="colecaoLote">
													<html:options collection="colecaoLote" property="lote"
													labelProperty="lote" />
											</logic:present>
										</html:select>
						    		</td>
								</tr>
								<tr>
								<td><strong>Bairro:</strong></td>
									<td align="left">
										<html:select property="bairro" multiple="true"
												style="width: 220px; height: 100px" onchange="javaScript: validarBairro();ativarDesativarDependenciasElo();ativarDesativarDependenciasfaturamentoGrupo();" >
											<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>	
											<logic:present name="colecaoBairro">
													<html:options collection="colecaoBairro" property="id"
													labelProperty="nome" />
											</logic:present>
										</html:select>
						    		</td>
						    		<td><strong>Logradouro:</strong></td>
									<td align="left">
										<html:select property="logradouro" multiple="true"
												style="width: 220px; height: 100px" onchange="javaScript:ativarDesativarDependenciasElo();ativarDesativarDependenciasfaturamentoGrupo();" >
											<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>	
											<logic:present name="colecaoLogradouro">
													<html:options collection="colecaoLogradouro" property="id"
													labelProperty="nome" />
											</logic:present>
										</html:select>
						    		</td>
								</tr>
						     </table>
							</td>
						</tr>	
						</tr>
					</table>
					</div>	
				</td>
			</tr>
		    <tr>
			<td colspan="4">
					<div id="layerHideImovel" style="display: block">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('Imovel',true);" /><b>Características do Imóvel
							</b></a></span></td>
						</tr>
					</table>
					</div>

					<div id="layerShowImovel" style="display: none">
					<table width="100%" border="0" bgcolor="#99CCFF">
						<tr bgcolor="#99CCFF">
							<td align="center"><span class="style2"> <a
								href="javascript:extendeTabela('Imovel',false);" /><b>Características do Imóvel
							</b></a></span></td>
						</tr>
							</tr>
							<tr bgcolor="#cbe5fe">
								<td>
							  	   <table width="100%" align="center">
							  	<tr>
				<td>
				<table cellpadding="0" cellspacing="2">
				<tr>
					<td>
					<strong>Pefil Imóvel:</strong>
					</td>
					<td>
					<strong>Categoria:</strong>
					</td>
					<td>
					<strong>Subcategoria:</strong>
					</td>
				</tr>
				<tr>
					<td align="left">
								<html:select property="perfilImovel" multiple="true" style="width: 190px;">
									<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
									<logic:present name="collectionImovelPerfil">
										<html:options collection="collectionImovelPerfil"
											labelProperty="descricao" property="id" />
									</logic:present>
								</html:select>
						<html:hidden property="perfilImovelDescricao"/>
					</td>
					<td align="left">
								<html:select property="categoria" multiple="true" onchange="ControleCategoria();" style="width: 190px;">
									<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
									<logic:present name="collectionImovelCategoria">
										<html:options collection="collectionImovelCategoria"
											labelProperty="descricao" property="id" />
									</logic:present>
								</html:select>
						<html:hidden property="categoriaDescricao"/>
					</td>
					<td align="left">
								<html:select property="subCategoria" styleId="subcategoria" multiple="true" style="width: 190px;">
									<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
									<logic:present name="collectionImovelSubcategoria">
										<html:options collection="collectionImovelSubcategoria"
											labelProperty="descricao" property="id" />
									</logic:present>
								</html:select>
						<html:hidden property="subCategoriaDescricao"/>
					</td>
				</tr>
				</table>
			</td>	<tr><td colspan="2"><hr></td></tr>

				<tr>
					<td colspan="2">
						<table border="0">
							<tr>
								<td width="175"><strong>Situação da Ligação de Água:</strong></td>
								<td align="left">
									<html:select property="ligacaoAguaSituacao" multiple="true" style="width: 190px;">
										<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
										<logic:present name="colecaoLigacaoAguaSituacao">
											<html:options collection="colecaoLigacaoAguaSituacao" property="id" labelProperty="descricao" />
										</logic:present>		
									</html:select>
								</td>

								<td>&nbsp;</td>

								<td width="175"><strong>Situação da Ligação de Esgoto:</strong></td>
								<td align="left">
									<html:select property="ligacaoEsgotoSituacao" multiple="true" style="width: 190px;">
										<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
										<logic:present name="colecaoLigacaoEsgotoSituacao">
											<html:options collection="colecaoLigacaoEsgotoSituacao" property="id" labelProperty="descricao" />
										</logic:present>		
									</html:select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
					  </tr>
					</table>
					</div>
				</tr>
				</table>
			</tr>
			</table>
						
			<!--Fim Tabela Reference a Páginação da Tela de Processo-->
			<p>&nbsp;</p>
			<table width="100%" border="0">
					<tr>
					<td>
						<div id="botoes">
							
							<table>
								<tr>
									<td height="24" align="left" width="100%">
							          	<input type="button" 
							          		class="bottonRightCol" 
							          		value="Limpar" 
							          		onclick="javascript:limparForm();"/>
									</td>
									
									<td align="right">
										<input type="button" 
											name="Button" 
											id="botaoFiltrar"
											class="bottonRightCol" 
											value="Filtrar" 
											onClick="javascript:validarForm()" />
									</td>
									
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
			<p>&nbsp;</p>
		</tr>
	</table>
	<tr>
		<td colspan="3"><%@ include file="/jsp/util/rodape.jsp"%>
	</tr>
</html:form>
</body>
</html:html>

