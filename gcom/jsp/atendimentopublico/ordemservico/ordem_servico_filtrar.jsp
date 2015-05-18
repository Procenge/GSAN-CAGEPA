<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<%@ page import="gcom.util.ConstantesSistema"%>
<%@ page import="gcom.atendimentopublico.ordemservico.OrdemServico"%>

<style type="text/css">

.desabilitar {
 background-color:#EFEFEF;
 border:0;
 color: #000000;
}

</style>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="FiltrarOrdemServicoActionForm"/>

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>

<script language="JavaScript">

	function validarForm(){
   		var form = document.forms[0];

   		if (form.desabilitaCampos.value == null || form.desabilitaCampos.value == ''
   	   		|| form.desabilitaCampos.value == 'false' || form.desabilitaCampos.value == false){

	   		if (form.diasAtraso.value != '') {
				if (form.situacaoOrdemServico.value == '-1') {
					alert("Informe a Situação da Ordem de Serviço");
					form.situacaoOrdemServico.focus();
					return;
				}
				if (form.unidadeAtual.value == '') {
					alert("Informe a Unidade Atual");
					form.unidadeAtual.focus();
					return;
				}
		
	   		}
	   		
	   		if (form.programada[2].checked && form.matriculaImovel.value == "") {
	   	   		// Todos
	   	   		if ((form.unidadeAtual.value == '') && (form.unidadeSuperior == null 
	   		   	   		|| form.unidadeSuperior.value == null || form.unidadeSuperior.value == '')) {
	   	   			alert("Informe a Unidade Atual ou a Unidade Superior/Chefia");
					return false;
	   	   	   	}
	
	   	   	}

	   		if(validateFiltrarOrdemServicoActionForm(form)){

				if(validaTodosPeriodos(form)){
					enviarSelectMultiplo('FiltrarOrdemServicoActionForm','tipoServicoSelecionados');
					form.action = 'filtrarOrdemServicoAction.do';
		   			form.submit();
				}
		  	}
   		}else{
   			
   			if(validateFiltrarOrdemServicoActionForm(form)){

				form.action = 'filtrarOrdemServicoAction.do';
	   			form.submit();
		  	}
   	   	}

    	
    }
    function validaTodosPeriodos(form) {

    	if (comparaData(form.periodoAtendimentoInicial.value, '>', form.periodoAtendimentoFinal.value)){

			alert('Data Final do Período de Atendimento é anterior à Data Inicial do Período de Atendimento');
			return false;

		} else if (comparaData(form.periodoGeracaoInicial.value, '>', form.periodoGeracaoFinal.value)){

			alert('Data Final do Período da Geração é anterior à Data Inicial do Período da Geração');
			return false;

		} else if (comparaData(form.periodoEncerramentoInicial.value, '>', form.periodoEncerramentoFinal.value)){

			alert('Data Final do Período de Encerramento é anterior à Data Inicial do Período de Encerramento');
			return false;

		} else if (comparaData(form.periodoExecucaoInicial.value, '>', form.periodoExecucaoFinal.value)){

			alert('Data Final do Período de Execução é anterior à Data Inicial do Período de Execução');
			return false;

		} else if (comparaData(form.periodoProgramacaoInicial.value, '>', form.periodoProgramacaoFinal.value)){

			alert('Data Final do Período de Programação é anterior à Data Inicial do Período de Programação');
			return false;

		} else if(form.codigoBairro.value != '' && form.municipio.value == '')   { 

			alert('Informe o Município');
			return false;
		}


		return true;
    }

	function limparForm(){

		var form = document.forms[0];

		form.numeroOS.value="";
		limparPesquisaRA();
		limparDocumentoCobranca();
		form.periodoAtendimentoInicial.value="";
		form.periodoAtendimentoFinal.value="";
		form.periodoGeracaoInicial.value="";
		form.periodoGeracaoFinal.value="";		
		limparImovel();
		limparCliente();
		limparUnidadeGeracao();
		limparUnidadeAtual();
		limparUnidadeSuperior();
		limparPeriodoEncerramento();
		limparPeriodoExecucao();
		limparMunicipio();
		limparBairro();
		limparLogradouro();
		limparProgramacao();
		form.diasAtraso.value = "";
		form.situacaoOrdemServico.selectedIndex = 0;
		form.areaBairro.selectedIndex = 0;
		MoverTodosDadosSelectMenu2PARAMenu1('FiltrarOrdemServicoActionForm', 'tipoServico', 'tipoServicoSelecionados');
		form.desabilitaCampos.value = null;

		form.action = "/gsan/exibirFiltrarOrdemServicoAction.do?menu=sim&limpar=sim";
		form.submit();
	}

	function limparProgramacao() {
		var form = document.forms[0];
		form.programada[2].checked = true;
		desabilitaProgramacaoEquipe();
	}

	function desabilitaProgramacaoEquipe() {
		var form = document.forms[0];
		
		limparEquipe();

		form.equipe.readOnly = true;
		form.equipe.className = 'desabilitar';

    	form.descricaoEquipe.readOnly = true;
    	form.descricaoEquipe.className = 'desabilitar';
		
		desabilitarPeriodoProgramacao();
	}

	function habilitarProgramacaoEquipe() {
		var form = document.forms[0];

		if (form.programada[0].checked != true) {
			limparEquipe();
		}

		form.equipe.removeAttribute('readOnly');
		form.equipe.className = '';
		
    	form.descricaoEquipe.removeAttribute('readOnly');
    	form.descricaoEquipe.className = '';
		
		limparHabilitarPeriodoProgramacao();
	}

	function limparPesquisaRA() {

    	var form = document.forms[0];

    	form.numeroRA.value = "";
    	form.descricaoRA.value = "";
    	
    	form.documentoCobranca.removeAttribute('readOnly');
    	form.documentoCobranca.className = '';
    	
    	form.descricaoDocumentoCobranca.removeAttribute('readOnly');
    	form.descricaoDocumentoCobranca.className = '';
    	
  	}

	function limparDocumentoCobranca() {

    	var form = document.forms[0];

    	form.documentoCobranca.value = "";
    	form.descricaoDocumentoCobranca.value = "";

    	form.numeroRA.removeAttribute('readOnly');
    	form.numeroRA.className = '';
    	
    	form.descricaoRA.removeAttribute('readOnly');
    	form.descricaoRA.className = '';

  	}

	function limparImovel() {

    	var form = document.forms[0];

    	form.matriculaImovel.value = "";
    	form.inscricaoImovel.value = "";
  	}

	function limparCliente() {

    	var form = document.forms[0];

    	form.codigoCliente.value = "";
    	form.nomeCliente.value = "";
  	}

    function limparUnidadeGeracao() {
        var form = document.forms[0];

        form.unidadeGeracao.value = "";
    	form.descricaoUnidadeGeracao.value = "";
    }

    function limparUnidadeAtual() {
        var form = document.forms[0];

        form.unidadeAtual.value = "";
    	form.descricaoUnidadeAtual.value = "";
    }

    function limparUnidadeSuperior() {
        var form = document.forms[0];

        form.unidadeSuperior.value = "";
    	form.descricaoUnidadeSuperior.value = "";
    }

    function limparEquipe() {
        var form = document.forms[0];
        
        form.equipe.value = "";
    	form.descricaoEquipe.value = "";
    }

    function limparMunicipio() {
        var form = document.forms[0];

        form.municipio.value = "";
    	form.descricaoMunicipio.value = "";
    	limparBairro();
    	
    }

    function limparBairro() {
        var form = document.forms[0];

        form.codigoBairro.value = "";
    	form.descricaoBairro.value = "";
    	limparAreaBairro();
    }
    
    function limparLogradouro() {
        var form = document.forms[0];

        form.logradouro.value = "";
    	form.descricaoLogradouro.value = "";
    }

    function limparPeriodoEncerramento() {

        var form = document.forms[0];
        
        form.periodoEncerramentoInicial.removeAttribute('readOnly');
        form.periodoEncerramentoInicial.className = '';
		
        form.periodoEncerramentoFinal.removeAttribute('readOnly');
        form.periodoEncerramentoFinal.className = '';
		
		form.periodoEncerramentoInicial.value="";
		form.periodoEncerramentoFinal.value="";
  	}
    
    function limparPeriodoExecucao() {

        var form = document.forms[0];
        
        form.periodoExecucaoInicial.removeAttribute('readOnly');
        form.periodoExecucaoInicial.className = '';
		
        form.periodoExecucaoFinal.removeAttribute('readOnly');
        form.periodoExecucaoFinal.className = '';
		
		form.periodoExecucaoInicial.value="";
		form.periodoExecucaoFinal.value="";
  	}
    
    function limparHabilitarPeriodoProgramacao() {
        var form = document.forms[0];
        
        form.periodoProgramacaoInicial.removeAttribute('readOnly');
        form.periodoProgramacaoInicial.className = '';
		
        form.periodoProgramacaoFinal.removeAttribute('readOnly');
        form.periodoProgramacaoFinal.className = '';
		
		form.periodoProgramacaoInicial.value="";
		form.periodoProgramacaoFinal.value="";
  	}

    function desabilitarPeriodoProgramacao() {
        var form = document.forms[0];
        
        form.periodoProgramacaoInicial.readOnly = true;
		form.periodoProgramacaoInicial.className = 'desabilitar';
		
		form.periodoProgramacaoFinal.readOnly = true;
		form.periodoProgramacaoFinal.className = 'desabilitar';
		
		form.periodoProgramacaoInicial.value="";
		form.periodoProgramacaoFinal.value="";
  	}
    
    function limparPeriodoGeracao() {
        var form = document.forms[0];
        
        if (form.periodoGeracaoInicial.value != ''){
			form.periodoGeracaoInicial.value="";
		}
		
		if (form.periodoGeracaoFinal.value != ''){
			form.periodoGeracaoFinal.value="";
		}			
  	}
    
    
	//Replica Data de Atendimento
	function replicaDataAtendimento() {
		var form = document.forms[0];
		form.periodoAtendimentoFinal.value = form.periodoAtendimentoInicial.value;
	}

	//Replica Data de Geracao
	function replicaDataGeracao() {
		var form = document.forms[0];
		form.periodoGeracaoFinal.value = form.periodoGeracaoInicial.value;
	}
    
	//Replica Data de Programação
	function replicaDataProgramacao() {
		var form = document.forms[0];
		form.periodoProgramacaoFinal.value = form.periodoProgramacaoInicial.value;
	}

	//Replica Data de Encerramento
	function replicaDataEncerramento() {
		var form = document.forms[0];
		form.periodoEncerramentoFinal.value = form.periodoEncerramentoInicial.value;
	}
	
	//Replica Data de Execucao
	function replicaDataExecucao() {
		var form = document.forms[0];
		form.periodoExecucaoFinal.value = form.periodoExecucaoInicial.value;
	}

	function validaForm(){
		var form = document.forms[0];
		
		if(form.numeroRA.value != null && form.numeroRA.value != ''){

			limparDocumentoCobranca();

   	    	form.documentoCobranca.readOnly = true;
   	    	form.documentoCobranca.className = 'desabilitar';

    		form.descricaoDocumentoCobranca.readOnly = true;
    		form.descricaoDocumentoCobranca.className = 'desabilitar';

		}else if(form.documentoCobranca.value != null && form.documentoCobranca.value != ''){

			limparPesquisaRA();
			
			form.numeroRA.readOnly = true;
			form.numeroRA.className = 'desabilitar';
   	    	
			form.descricaoRA.readOnly = true;
			form.descricaoRA.className = 'desabilitar';
   		
		}

		if (form.desabilitaCampos.value == null || form.desabilitaCampos.value == ''
   	   		|| form.desabilitaCampos.value == false){
   	   		
			if(	form.situacaoOrdemServico.value == '1' || 
				form.situacaoOrdemServico.value == '3' ||
				form.situacaoOrdemServico.value == '4' ){
	
				limparPeriodoEncerramento();
				limparPeriodoExecucao();
				
				form.periodoEncerramentoInicial.readOnly = true;
				form.periodoEncerramentoInicial.className = 'desabilitar';
				
				form.periodoEncerramentoFinal.readOnly = true;
				form.periodoEncerramentoFinal.className = 'desabilitar';
				
				form.periodoExecucaoInicial.readOnly = true;
				form.periodoExecucaoInicial.className = 'desabilitar';
				
				form.periodoExecucaoFinal.readOnly = true;
				form.periodoExecucaoFinal.className = 'desabilitar';
			
			}else{
	
				form.periodoEncerramentoInicial.removeAttribute('readOnly');
				form.periodoEncerramentoInicial.className = '';
				
				form.periodoEncerramentoFinal.removeAttribute('readOnly');
				form.periodoEncerramentoFinal.className = '';
				
				form.periodoExecucaoInicial.removeAttribute('readOnly');
				form.periodoExecucaoInicial.className = '';
				
				form.periodoExecucaoFinal.removeAttribute('readOnly');
				form.periodoExecucaoFinal.className = '';
			
			}
	
			if (form.programada[0].checked != true) {
				desabilitaProgramacaoEquipe();
			}
			/*if(form.situacaoProgramacao[2].checked){
				
				limparHabilitarPeriodoProgramacao();
				
				form.periodoProgramacaoInicial.disabled = true;
				form.periodoProgramacaoFinal.disabled = true;
			}else{
	
				form.periodoProgramacaoInicial.disabled = false;
				form.periodoProgramacaoFinal.disabled = false;
			}*/
		}
		
		
	}
	
	/* Chama Popup */ 
	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){
		if(objetoRelacionado.readOnly != true){
			if (objeto == null || codigoObjeto == null){
				abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
			} else{
				if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
					alert(msg);
				} else{
					abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
				}
			}
		}
	}
	
	var unidade = 0;
	
	function setUnidade(tipo) {
		unidade = tipo;
	}
	
	/* Recuperar Popup */
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	    var form = document.forms[0];
	    
	    if (tipoConsulta == 'registroAtendimento') {

	      if(!form.numeroRA.readOnly){
		      form.numeroRA.value = codigoRegistro;
		      form.descricaoRA.value = descricaoRegistro;
		      form.descricaoRA.style.color = "#000000";
		      reload();
	      }
	    
	    } else if (tipoConsulta == 'documentoCobranca') {

	      if(!form.documentoCobranca.readOnly){
		      form.documentoCobranca.value = codigoRegistro;
		      form.descricaoDocumentoCobranca.value = descricaoRegistro;
		      form.descricaoDocumentoCobranca.style.color = "#000000";
		      reload();
	      }
	    
	    } else if (tipoConsulta == 'imovel') {

	      form.matriculaImovel.value = codigoRegistro;
	      form.inscricaoImovel.value = descricaoRegistro;
	      form.inscricaoImovel.style.color = "#000000";
	    
	    } else if (tipoConsulta == 'equipe') {

	      form.equipe.value = codigoRegistro;
	      form.descricaoEquipe.value = descricaoRegistro;
	      form.descricaoEquipe.style.color = "#000000";
	    
	    } else if (tipoConsulta == 'cliente') {

		    form.codigoCliente.value = codigoRegistro;
		    form.nomeCliente.value = descricaoRegistro;
	      	form.nomeCliente.style.color = "#000000";
	    
   	    } else if (tipoConsulta == 'unidadeSuperior') {

	      	form.unidadeSuperior.value = codigoRegistro;
	      	form.descricaoUnidadeSuperior.value = descricaoRegistro;
			form.descricaoUnidadeSuperior.style.color = "#000000";

	    } else if (tipoConsulta == 'unidadeOrganizacional') {
	      
	   		if (unidade == 1) {

		    	form.unidadeGeracao.value = codigoRegistro;
		      	form.descricaoUnidadeGeracao.value = descricaoRegistro;
	      		form.descricaoUnidadeGeracao.style.color = "#000000";

	      	} else if (unidade == 2) {

		      	form.unidadeAtual.value = codigoRegistro;
		      	form.descricaoUnidadeAtual.value = descricaoRegistro;
      			form.descricaoUnidadeAtual.style.color = "#000000";

	      	}
	      	unidade = 0;
	    
	    } else if (tipoConsulta == 'municipio') {
		    form.municipio.value = codigoRegistro;
		    form.descricaoMunicipio.value = descricaoRegistro;
   			form.descricaoMunicipio.style.color = "#000000";
	    
	    } else if (tipoConsulta == 'bairro') {
		    form.codigoBairro.value = codigoRegistro;
		    form.descricaoBairro.value = descricaoRegistro;
   			form.descricaoBairro.style.color = "#000000";
	    
	    } else if (tipoConsulta == 'logradouro') {
  	      	form.logradouro.value = codigoRegistro;
	      	form.descricaoLogradouro.value = descricaoRegistro;
   			form.descricaoLogradouro.style.color = "#000000";
	    }
	}
	
	function reload() {
		var form = document.forms[0];
		form.action = "/gsan/exibirFiltrarOrdemServicoAction.do";
		form.submit();
	}
	
	//So chama a função abrirCalendario caso o campo esteja habilitado
	function chamarCalendario(fieldNameOrigem,objetoRelacionado,fieldNameDestino){
		
		if(objetoRelacionado.readOnly != true){
			if(fieldNameDestino != ''){
				abrirCalendarioReplicando('FiltrarOrdemServicoActionForm', fieldNameOrigem,fieldNameDestino);
			}else{
				abrirCalendario('FiltrarOrdemServicoActionForm', fieldNameOrigem);
			}
		}
	}
	
	function MoveSelectedDataFromMenu1TO2(form, object, selectedObject){
		var form = document.forms[0];
		
		if (form.tipoServico.value != '-1') {
			MoverDadosSelectMenu1PARAMenu2(form, object, selectedObject);
		}
	}
	
	function MoveSelectedDataFromMenu2TO1(form, object, selectedObject){
		var form = document.forms[0];
		
		if (form.tipoServico.value != '-1') {
			MoverDadosSelectMenu2PARAMenu1(form, object, selectedObject);
		}
	}

	/* Clear Área Bairro */
	function limparAreaBairro() {
		var form = document.forms[0];
		for(i=form.areaBairro.length-1; i>0; i--) {
			form.areaBairro.options[i] = null;
		}
	}
	
	function validarBairro(){
		var form = document.forms[0];
		if(form.municipio.value == '')   { 
			alert('Informe o Município');
		}else{
			abrirPopup('exibirPesquisarBairroAction.do?idMunicipio='+document.forms[0].municipio.value+'&indicadorUsoTodos=1', 400, 800);
		}
	}

	//Caso informe o(s) campos Número da OS, Número do RA ou Documento de Cobrança, sera desabilitados
	//e desconsideradas as demais regras de filtro para os outros campos da tela.
	function verificarCamposDesbloqueados(){

		var form = document.forms[0];

		if ((!(form.numeroRA.value == '' || form.numeroRA.value == null))
				|| (!(form.documentoCobranca.value == '' || form.documentoCobranca.value == null))
				|| (!(form.numeroOS.value == '' || form.numeroOS.value == null))){

			form.desabilitaCampos.value = true;
		}else{
		
			form.desabilitaCampos.value = false;
		}
	}	
	
	function limparTipoServicoSelecionados() {
		var form = document.forms[0];
		enviarSelectMultiplo('FiltrarOrdemServicoActionForm', 'tipoServicoSelecionados');
		form.action = 'exibirFiltrarOrdemServicoAction.do?indicadorTipoServico=2';
		form.submit();
	}
	
	function carregarTodosTipoServicoSelecionadosForm() {
		var form = document.forms[0];
		enviarSelectMultiplo('FiltrarOrdemServicoActionForm',
				'tipoServicoSelecionados');
		form.action = 'exibirFiltrarOrdemServicoAction.do?indicadorTipoServico=1';
		form.submit();

	}
	
	function carregarTipoServicoSelecionadosForm() {
		var form = document.forms[0];
		enviarSelectMultiplo('FiltrarOrdemServicoActionForm',
				'tipoServicoSelecionados');
		form.action = 'exibirFiltrarOrdemServicoAction.do?indicadorTipoServico=1';
		form.submit();
	}
	
	
	
</script>

</head>

<body leftmargin="5" topmargin="5" onLoad="verificarCamposDesbloqueados();validaForm();">

<html:form action="/filtrarOrdemServicoAction" name="FiltrarOrdemServicoActionForm" 
	type="gcom.gui.atendimentopublico.ordemservico.FiltrarOrdemServicoActionForm" method="post">

<html:hidden property="desabilitaCampos"/>

<%@ include file="/jsp/util/cabecalho.jsp"%>

<table width="770" border="0" cellspacing="4" cellpadding="0">
	  <tr>
	    <td valign="top" class="leftcoltext">
	      <div align="left">
	
			<%@ include file="/jsp/util/informacoes_usuario.jsp"%>
	
			<%@ include file="/jsp/util/mensagens.jsp"%>
	
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
					<td class="parabg">Filtrar Ordem de Servi&ccedil;o</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">Preencha os campos para filtrar ordens de servi&ccedil;o:</td>
				</tr>
				<tr>
					<td>
						<div id="camposChave">
							
							<table>
								<tr> 
				                	<td width="20%"><strong>N&uacute;mero da OS:</strong></td>
				                	<td colspan="6">
				                		<strong>
				                  			<html:text property="numeroOS" size="9" maxlength="9" 
				                  			onkeyup="limparPeriodoGeracao();" 
				                  			onblur="verificarCamposDesbloqueados();"/>
				                  		</strong>
				                  	</td>
				              	</tr>
				
								<tr>
									<td><strong>N&uacute;mero do RA:</strong></td>
									
									<td width="1453">
										
										<html:text maxlength="9" 
											tabindex="1"
											property="numeroRA" 
											size="9"
											onkeypress="validaEnterComMensagem(event, 'exibirFiltrarOrdemServicoAction.do?objetoConsulta=1','numeroRA','Numero RA');"
											onkeyup="limparPeriodoGeracao();"
											onblur="verificarCamposDesbloqueados();"/>
											
											<a href="javascript:chamarPopup('exibirPesquisarRegistroAtendimentoAction.do', 'registroAtendimento', null, null, 600, 730, '', document.forms[0].numeroRA);">
												
												<img width="23" 
													height="21" 
													border="0"
													src="<bean:message key="caminho.imagens"/>pesquisa.gif"
													title="Pesquisar RA" /></a> 
				
											<logic:present name="numeroRAEncontrada" scope="request">
												
												<html:text property="descricaoRA" 
													size="45"
													maxlength="45" 
													readonly="true"
													style="background-color:#EFEFEF; border:0; color: #000000" />
											</logic:present> 
				
											<logic:notPresent name="numeroRAEncontrada" scope="request">
												
												<html:text property="descricaoRA" 
													size="45"
													maxlength="45" 
													readonly="true"
													style="background-color:#EFEFEF; border:0; color: red" />
													
											</logic:notPresent>
				
											<a href="javascript:limparPesquisaRA();verificarCamposDesbloqueados();"> 
												<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
													border="0" 
													title="Apagar" /></a>
										</td>
								</tr>
				
								<tr>
									<td><strong>Documento de Cobran&ccedil;a:</strong></td>
									
									<td>
										
										<html:text maxlength="9" 
											tabindex="1"
											property="documentoCobranca" 
											size="9"
											onkeypress="validaEnterComMensagem(event, 'exibirFiltrarOrdemServicoAction.do?objetoConsulta=2','documentoCobranca','Documento de Cobrança');"
											onkeyup="limparPeriodoGeracao();"
											onblur="verificarCamposDesbloqueados();"/>
											
											<a href="javascript:abrirPopup('exibirFiltrarDocumentosCobrancaAction.do?ehPopup=true',500,800);">
				
												<img width="23" 
													height="21" 
													border="0"
													src="<bean:message key="caminho.imagens"/>pesquisa.gif"
													title="Pesquisar Documento Cobrança" /></a> 
				
											<logic:present name="documentoCobrancaEncontrada" scope="request">
												<html:text property="descricaoDocumentoCobranca" 
													size="45"
													maxlength="45" 
													readonly="true"
													style="background-color:#EFEFEF; border:0; color: #000000" />
											</logic:present> 
				
											<logic:notPresent name="documentoCobrancaEncontrada" scope="request">
												<html:text property="descricaoDocumentoCobranca" 
													size="45"
													maxlength="45" 
													readonly="true"
													style="background-color:#EFEFEF; border:0; color: red" />
											</logic:notPresent>
				
											<a href="javascript:limparDocumentoCobranca();verificarCamposDesbloqueados();"> 
												<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
													border="0" 
													title="Apagar" /></a>
				
										</td>
								</tr>
										<tr>
									<td>
										<strong>Situa&ccedil;&atilde;o de Débito do Documento de Cobrança:</strong>
									</td>
				
									<td>
										<strong>
										<html:select property="situacaoDocumentoCobranca" style="width: 300px;" onchange="javascript:validaForm();">
				
											<html:option
												value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
											</html:option>
				
											<html:option
												value="1">PENDENTE
											</html:option>
				
											<html:option
												value="2">NÃO PENDENTE
											</html:option>
											<html:option
												value="0">AMBOS
											</html:option>
										</html:select> 													
										</strong>
									</td>
								</tr>
				
				           		<tr>
				             		<td height="10" colspan="3"> 
					             		<div align="right"> 
					                 		<hr>
					               		</div>
					               		<div align="right"> </div>
				               		</td>
				           		</tr>
							</table>
						</div>
					</td>
				</tr>
				
				<tr>
					<td>
					
						<div id="camposAdicionais">			
							<table>
								<tr>
									<td>
										<strong>Situa&ccedil;&atilde;o da Ordem de Servi&ccedil;o:</strong>
									</td>
				
									<td>
										<strong>
										<html:select property="situacaoOrdemServico" style="width: 300px;" onchange="javascript:validaForm();">
				
											<html:option
												value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
											</html:option>
				
											<html:option
												value="<%=""+OrdemServico.SITUACAO_PENDENTE%>">PENDENTES
											</html:option>
				
											<html:option
												value="<%=""+OrdemServico.SITUACAO_ENCERRADO%>">ENCERRADOS
											</html:option>
				
											<html:option
												value="<%=""+OrdemServico.SITUACAO_EXECUCAO_EM_ANDAMENTO%>">EXECU&Ccedil;&Atilde;O EM ANDAMENTO
											</html:option>
				
											<html:option
												value="<%=""+OrdemServico.SITUACAO_AGUARDANDO_LIBERACAO%>">AGUARDANDO LIBERA&Ccedil;&Atilde;O PARA EXECU&Ccedil;&Atilde;O
											</html:option>
										
										</html:select> 													
										</strong>
									</td>
								</tr>
							  <tr>
							  		<td>
				                     	<html:radio property="programada" value="1" onclick="javascript:habilitarProgramacaoEquipe();">Programado</html:radio>
				                     </td>
				                     <td>
				                    	<html:radio property="programada" value="2" onclick="javascript:desabilitaProgramacaoEquipe();">Não Programado</html:radio>
				                    	<html:radio property="programada" value="3" onclick="javascript:desabilitaProgramacaoEquipe();">Todos</html:radio>
				 			  		</td>
							  </tr>
				
								<tr>
									<td width="110">
										<strong>Tipo de Servi&ccedil;o:</strong>					</td>
									<td colspan="2">
									<table width="100%" border=0 cellpadding=0 cellspacing=0 align="left">
										<tr>
											<td width="175">
											
												<div align="left"><strong>Disponíveis</strong></div>
				
												<html:select property="tipoServico" 
													size="6" 
													multiple="true" 
													style="width:190px">
												<logic:notEmpty name="colecaoTipoServico">
													
													<html:options collection="colecaoTipoServico" 
														labelProperty="descricao" 
														property="id" />
													
												</logic:notEmpty>
												<logic:empty name="colecaoTipoServico">	
													<html:options collection="colecaoServicoTipoDisponivel" 
														labelProperty="descricao" 
														property="id" />
												</logic:empty>
												</html:select>
											</td>
				
											<td width="5" align="center"><br>
												<table width="50" align="center">
													<tr>
														<td align="center">
															<input type="button" 
																class="bottonRightCol"
																onclick="javascript:MoverTodosDadosSelectMenu1PARAMenu2('FiltrarOrdemServicoActionForm', 'tipoServico', 'tipoServicoSelecionados');carregarTodosTipoServicoSelecionadosForm();"
																value=" &gt;&gt; ">
														</td>
													</tr>
					
													<tr>
														<td align="center">
															<input type="button" 
																class="bottonRightCol"
																onclick="javascript:MoverDadosSelectMenu1PARAMenu2('FiltrarOrdemServicoActionForm', 'tipoServico', 'tipoServicoSelecionados');carregarTipoServicoSelecionadosForm();"
																value=" &nbsp;&gt;  ">
														</td>
													</tr>
					
													<tr>
														<td align="center">
															<input type="button" 
																class="bottonRightCol"
																onclick="javascript:MoverDadosSelectMenu2PARAMenu1('FiltrarOrdemServicoActionForm', 'tipoServico', 'tipoServicoSelecionados');"
																value=" &nbsp;&lt;  ">
														</td>
													</tr>
					
													<tr>
														<td align="center">
															<input type="button" 
																class="bottonRightCol"
																onclick="javascript:MoverTodosDadosSelectMenu2PARAMenu1('FiltrarOrdemServicoActionForm', 'tipoServico', 'tipoServicoSelecionados');limparTipoServicoSelecionados();;"
																value=" &lt;&lt; ">
														</td>
													</tr>
												</table>
											</td>
			
											<td>
												<div align="left">
													<strong>Selecionados</strong>
												</div>
												
												<html:select property="tipoServicoSelecionados" 
													size="6" 
													multiple="true" 
													style="width:190px">
													
													<logic:notEmpty name="colecaoTipoServicoSelecionados">	
													<html:options collection="colecaoTipoServicoSelecionados" 
														labelProperty="descricao" 
														property="id" />
												</logic:notEmpty>
												
												</html:select>
											</td>
										</tr>
									</table>
									</td>
								</tr>
								
								<tr>
									<td width="110">
										<strong>OSs com serviço de reparo:</strong>					
									</td>																  		
				                     <td>
				                     	<html:radio property="indicadorReparo" value="1" onclick="javascript:reload();" >Gerado</html:radio>
				                    	<html:radio property="indicadorReparo" value="2" onclick="javascript:reload();" >Não Gerado</html:radio>
				                    	<html:radio property="indicadorReparo" value="3" onclick="javascript:reload();" >Ambos</html:radio>
				 			  		</td>
							    </tr>
				
				           		<tr>
				             		<td height="10" colspan="3"> 
					             		<div align="right"> 
					                 		<hr>
					               		</div>
					               		<div align="right"> </div>
				               		</td>
				           		</tr>
				
								<tr>
									<td><strong>Matr&iacute;cula do Im&oacute;vel:</strong></td>
									
									<td>
										
										<html:text maxlength="9" 
											tabindex="1"
											property="matriculaImovel" 
											size="9"
											onkeypress="validaEnterComMensagem(event, 'exibirFiltrarOrdemServicoAction.do?objetoConsulta=3','matriculaImovel','Matricula Imóvel');"
											onkeyup="limparPeriodoGeracao();"/>
													
											
											<a href="javascript:chamarPopup('exibirPesquisarImovelAction.do', 'imovel', null, null, 275, 480, '', document.forms[0].matriculaImovel);">
											<img width="23" 
												height="21" 
												border="0"
												src="<bean:message key="caminho.imagens"/>pesquisa.gif"
												title="Pesquisar Imóvel" /></a> 
				
											<logic:present name="matriculaImovelEncontrada" scope="request">
												<html:text property="inscricaoImovel" 
													size="30"
													maxlength="30" 
													readonly="true"
													style="background-color:#EFEFEF; border:0; color: #000000" />
											</logic:present> 
				
											<logic:notPresent name="matriculaImovelEncontrada" scope="request">
												<html:text property="inscricaoImovel" 
													size="30"
													maxlength="30" 
													readonly="true"
													style="background-color:#EFEFEF; border:0; color: red" />
											</logic:notPresent>
											
											<a href="javascript:limparImovel();"> 
											<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
												border="0" 
												title="Apagar" />
											</a>
										</td>
								</tr>
								
								<tr>
									<td><strong>C&oacute;digo do Cliente:</strong></td>
									
									<td>
										
										<html:text maxlength="9" 
											tabindex="1"
											property="codigoCliente" 
											size="9"
											onkeypress="validaEnterComMensagem(event, 'exibirFiltrarOrdemServicoAction.do?objetoConsulta=4','codigoCliente','Código Cliente');"
											onkeyup="limparPeriodoGeracao();"/>
											
											<a href="javascript:chamarPopup('exibirPesquisarClienteAction.do', 'cliente', null, null, 275, 480, '', document.forms[0].codigoCliente);">
											<img width="23" 
												height="21" 
												border="0"
												src="<bean:message key="caminho.imagens"/>pesquisa.gif"
												title="Pesquisar Cliente" /></a> 			
				
											<logic:present name="codigoClienteEncontrada" scope="request">
												<html:text property="nomeCliente" 
													size="45"
													maxlength="45" 
													readonly="true"
													style="background-color:#EFEFEF; border:0; color: #000000" />
											</logic:present> 
				
											<logic:notPresent name="codigoClienteEncontrada" scope="request">
												<html:text property="nomeCliente" 
													size="45"
													maxlength="45" 
													readonly="true"
													style="background-color:#EFEFEF; border:0; color: red" />
											</logic:notPresent>
											
											<a href="javascript:limparCliente();"> 
												<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
													border="0" 
													title="Apagar" />
											</a>			
										</td>
								</tr>
				
				           		<tr>
				             		<td height="10" colspan="3"> 
					             		<div align="right"> 
					                 		<hr>
					               		</div>
					               		<div align="right"> </div>
				               		</td>
				           		</tr>
				
								<tr>
									<td><strong>Unidade de Gera&ccedil;&atilde;o:</strong></td>
									<td>
										<html:text maxlength="8" 
											tabindex="1"
											property="unidadeGeracao" 
											size="6"
											onkeypress="validaEnterComMensagem(event, 'exibirFiltrarOrdemServicoAction.do?objetoConsulta=5','unidadeGeracao','Unidade Geração');"/> 
							
											
											<a href="javascript:setUnidade(1); chamarPopup('exibirPesquisarUnidadeOrganizacionalAction.do?tipoUnidade=unidadeGeracao', 'unidadeGeracao', null, null, 275, 480, '', document.forms[0].unidadeGeracao);">
											<img width="23" 
												height="21" 
												border="0"
												src="<bean:message key="caminho.imagens"/>pesquisa.gif"
												title="Pesquisar Unidade Geração" /></a> 
				
										<logic:present name="unidadeGeracaoEncontrada" scope="request">
											
											<html:text property="descricaoUnidadeGeracao" 
												size="40"
												maxlength="40" 
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000" />
											
										</logic:present> 
				
										<logic:notPresent name="unidadeGeracaoEncontrada" scope="request">
											
											<html:text property="descricaoUnidadeGeracao" 
												size="40"
												maxlength="40" 
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: red" />
				
										</logic:notPresent>
				
										<a href="javascript:limparUnidadeGeracao();">
										<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
											border="0" 
											title="Apagar" />
										</a>
									</td>
								</tr>
				
								<tr>
									<td><strong>Unidade Atual:</strong></td>
									<td>
										<html:text maxlength="8" 
											tabindex="1"
											property="unidadeAtual" 
											size="6"
											onkeypress="validaEnterComMensagem(event, 'exibirFiltrarOrdemServicoAction.do?objetoConsulta=6','unidadeAtual','Unidade Atual');"/> 
											
											
										<a href="javascript:setUnidade(2); chamarPopup('exibirPesquisarUnidadeOrganizacionalAction.do?tipoUnidade=unidadeAtual', 'unidadeAtual', null, null, 275, 480, '', document.forms[0].unidadeAtual);">
		
										<img width="23" 
											height="21" 
											border="0"
											src="<bean:message key="caminho.imagens"/>pesquisa.gif"
											title="Pesquisar Unidade Atual" /></a>
				
										<logic:present name="unidadeAtualEncontrada" scope="request">
											
											<html:text property="descricaoUnidadeAtual" 
												size="40"
												maxlength="40" 
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000" />
											
										</logic:present> 
				
										<logic:notPresent name="unidadeAtualEncontrada" scope="request">
											
											<html:text property="descricaoUnidadeAtual" 
												size="40"
												maxlength="40" 
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: red" />
				
										</logic:notPresent>
										
										<a href="javascript:limparUnidadeAtual();">
										<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
											border="0" 
											title="Apagar" />
										</a>
									</td>
								</tr>
				
								<tr>
									<td><strong>Unidade Superior/Chefia:</strong></td>
									<td>
										<html:text maxlength="8" 
											tabindex="1"
											property="unidadeSuperior" 
											size="6"
											onkeypress="validaEnterComMensagem(event, 'exibirFiltrarOrdemServicoAction.do?objetoConsulta=7','unidadeSuperior','Unidade Superior');"/> 
											
											<a href="javascript:setUnidade(3); chamarPopup('exibirPesquisarUnidadeSuperiorAction.do?tipoUnidade=unidadeSuperior', 'unidadeSuperior', null, null, 275, 480, '', document.forms[0].unidadeSuperior);">
											<img width="23" 
												height="21" 
												border="0"
												src="<bean:message key="caminho.imagens"/>pesquisa.gif"
												title="Pesquisar Unidade Superior" /></a> 
				
										<logic:present name="unidadeSuperiorEncontrada" scope="request">
											<html:text property="descricaoUnidadeSuperior" 
												size="40"
												maxlength="40" 
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000" />
										</logic:present> 
				
										<logic:notPresent name="unidadeSuperiorEncontrada" scope="request">
											
											<html:text property="descricaoUnidadeSuperior" 
												size="40"
												maxlength="40" 
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: red" />
				
										</logic:notPresent>
				
										<a href="javascript:limparUnidadeSuperior();">
										<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
											border="0" 
											title="Apagar" />
										</a>									
									</td>
								</tr>
								
								<tr>
									<td><strong>Equipe:</strong></td>
									<td>
										<html:text maxlength="4" 
											tabindex="1"
											property="equipe" 
											size="4"
											onkeypress="validaEnterComMensagem(event, 'exibirFiltrarOrdemServicoAction.do?objetoConsulta=11','equipe','Equipe');"/> 
											
										<a href="javascript: if (document.forms[0].equipe.readOnly == false) { chamarPopup('exibirPesquisarEquipeAction.do?tipoEquipe=equipe', 'equipe', null, null, 275, 480, '', document.forms[0].equipe); }">
										<img width="23" 
											height="21" 
											border="0"
											src="<bean:message key="caminho.imagens"/>pesquisa.gif"
											title="Pesquisar Equipe" /></a> 
											
										<logic:present name="equipeEncontrada" scope="request">
											<html:text property="descricaoEquipe" 
												size="40"
												maxlength="40" 
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000" />
										</logic:present> 
				
										<logic:notPresent name="equipeEncontrada" scope="request">
											
											<html:text property="descricaoEquipe" 
												size="40"
												maxlength="40" 
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: red" />
				
										</logic:notPresent>
				
										<a href="javascript:limparEquipe();">
										<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
											border="0" 
											title="Apagar" />
										</a>	
									</td>
								</tr>
				
				           		<tr>
				             		<td height="10" colspan="3"> 
					             		<div align="right"> 
					                 		<hr>
					               		</div>
					               		<div align="right"> </div>
				               		</td>
				           		</tr>
				
				              	<tr>
					                <td>
					                	<strong>Per&iacute;odo de Atendimento:</strong>
					                </td>
				                
					                <td colspan="6">
					                	<span class="style2">
					                	
					                	<strong> 
											
											<html:text property="periodoAtendimentoInicial" 
												size="11" 
												maxlength="10" 
												tabindex="3" 
												onkeyup="mascaraData(this, event);replicaDataAtendimento();"/>
											
											<a href="javascript:abrirCalendarioReplicando('FiltrarOrdemServicoActionForm', 'periodoAtendimentoInicial','periodoAtendimentoFinal');">
											<img border="0" 
												src="<bean:message key='caminho.imagens'/>calendario.gif" 
												width="16" 
												height="15" 
												border="0" alt="Exibir Calendário"/>
											</a>
											
											a 
											
											<html:text property="periodoAtendimentoFinal" 
												size="11" 
												maxlength="10" 
												tabindex="3" 
												onkeyup="mascaraData(this, event)"/>
												
											<a href="javascript:abrirCalendario('FiltrarOrdemServicoActionForm', 'periodoAtendimentoFinal');">
											<img border="0" 
												src="<bean:message key='caminho.imagens'/>calendario.gif" 
												width="16" 
												height="15" 
												border="0" 
												alt="Exibir Calendário" />
											</a>
											
											</strong>(dd/mm/aaaa)<strong> 
					                  	</strong>
					                  	</span>
					              	</td>
				              	</tr>
				
				              	<tr>
					                <td>
					                	<strong>Per&iacute;odo de Gera&ccedil;&atilde;o:</strong>
					                </td>
				                
					                <td colspan="6">
					                	<span class="style2">
					                	
					                	<strong> 
											
											<html:text property="periodoGeracaoInicial" 
												size="11" 
												maxlength="10" 
												tabindex="3" 
												onkeyup="mascaraData(this, event);replicaDataGeracao();"/>
											
											<a href="javascript:abrirCalendarioReplicando('FiltrarOrdemServicoActionForm', 'periodoGeracaoInicial','periodoGeracaoFinal');">
											<img border="0" 
												src="<bean:message key='caminho.imagens'/>calendario.gif" 
												width="16" 
												height="15" 
												border="0" 
												alt="Exibir Calendário"/>
											</a>
							
											a 
										
											<html:text property="periodoGeracaoFinal" 
												size="11" 
												maxlength="10" 
												tabindex="3" 
												onkeyup="mascaraData(this, event)"/>
											
											<a href="javascript:abrirCalendario('FiltrarOrdemServicoActionForm', 'periodoGeracaoFinal');">
											<img border="0" 
												src="<bean:message key='caminho.imagens'/>calendario.gif" 
												width="16" 
												height="15" 
												border="0" 
												alt="Exibir Calendário"/>
											</a>
											
											</strong>(dd/mm/aaaa)<strong> 
					                  	</strong>
					                  	</span>
					              	</td>
				              	</tr>
				
				              	<tr>
					                <td>
					                	<strong>Per&iacute;odo de Programa&ccedil;&atilde;o:</strong>
					                </td>
				                
					                <td colspan="6">
					                	<span class="style2">
					                	
					                	<strong> 
											
											<html:text property="periodoProgramacaoInicial" 
												size="11" 
												maxlength="10" 
												tabindex="3" 
												onkeyup="mascaraData(this, event);replicaDataProgramacao();"/>
											
											
											<a href="javascript: if (document.forms[0].equipe.readOnly == false) { chamarCalendario('periodoProgramacaoInicial',document.forms[0].periodoProgramacaoInicial,'periodoProgramacaoFinal'); }">
											<img border="0" 
												src="<bean:message 
												key='caminho.imagens'/>calendario.gif" 
												width="16" 
												height="15" 
												border="0" 
												alt="Exibir Calendário"/>
											</a>
											
											a 
											
											<html:text property="periodoProgramacaoFinal" 
												size="11" 
												maxlength="10" 
												tabindex="3" 
												onkeyup="mascaraData(this, event)"/>
				
											<a href="javascript: if (document.forms[0].equipe.readOnly == false) { chamarCalendario('periodoProgramacaoFinal',document.forms[0].periodoProgramacaoFinal,''); }">
											<img border="0" 
												src="<bean:message key='caminho.imagens'/>calendario.gif" 
												width="16" 
												height="15" 
												border="0" 
												alt="Exibir Calendário"/>
											</a>
											
											</strong>(dd/mm/aaaa)<strong> 
					                  	</strong>
					                  	</span>
					              	</td>
				              	</tr>
				
				              	<tr>
					                <td>
					                	<strong>Per&iacute;odo de Encerramento:</strong>
					                </td>
				                
					                <td colspan="6">
					                	<span class="style2">
					                	
					                	<strong> 
											
											<html:text property="periodoEncerramentoInicial" 
												size="11" 
												maxlength="10" 
												tabindex="3" 
												onkeyup="mascaraData(this, event);replicaDataEncerramento();"/>
											
											<a href="javascript:chamarCalendario('periodoEncerramentoInicial',document.forms[0].periodoEncerramentoInicial,'periodoEncerramentoFinal');">
												<img border="0" 
													src="<bean:message key='caminho.imagens'/>calendario.gif" 
													width="16" 
													height="15" 
													border="0" 
													alt="Exibir Calendário" />
											</a>

											a 
											
											<html:text property="periodoEncerramentoFinal" 
												size="11" 
												maxlength="10" 
												tabindex="3" 
												onkeyup="mascaraData(this, event)"/>
											
											<a href="javascript:chamarCalendario('periodoEncerramentoFinal',document.forms[0].periodoEncerramentoFinal,'');">
											<img border="0" 
												src="<bean:message key='caminho.imagens'/>calendario.gif" 
												width="16" 
												height="15" 
												border="0" 
												alt="Exibir Calendário"/>
											</a>
											
											</strong>(dd/mm/aaaa)<strong> 
					                  	</strong>
					                  	</span>
					              	</td>
				              	</tr>
				              	
				              	<tr>
					                <td>
					                	<strong>Per&iacute;odo de Execução:</strong>
					                </td>
				                
					                <td colspan="6">
					                	<span class="style2">
					                	
					                	<strong> 
											
											<html:text property="periodoExecucaoInicial" 
												size="11" 
												maxlength="10" 
												tabindex="3" 
												onkeyup="mascaraData(this, event);replicaDataExecucao();"/>
											
											<a href="javascript:chamarCalendario('periodoExecucaoInicial',document.forms[0].periodoExecucaoInicial,'periodoExecucaoFinal');">
												<img border="0" 
													src="<bean:message key='caminho.imagens'/>calendario.gif" 
													width="16" 
													height="15" 
													border="0" 
													alt="Exibir Calendário" />
											</a>

											a 
											
											<html:text property="periodoExecucaoFinal" 
												size="11" 
												maxlength="10" 
												tabindex="3" 
												onkeyup="mascaraData(this, event)"/>
											
											<a href="javascript:chamarCalendario('periodoExecucaoFinal',document.forms[0].periodoExecucaoFinal,'');">
											<img border="0" 
												src="<bean:message key='caminho.imagens'/>calendario.gif" 
												width="16" 
												height="15" 
												border="0" 
												alt="Exibir Calendário"/>
											</a>
											
											</strong>(dd/mm/aaaa)<strong> 
					                  	</strong>
					                  	</span>
					              	</td>
				              	</tr>
				              	<tr>
					                <td>
					                	<strong>Per&iacute;odo de Previsão para Cliente:</strong>
					                </td>
				                
					                <td colspan="6">
					                	<span class="style2">
					                	
					                	<strong> 
											
											<html:text property="periodoPrevisaoInicial" 
												size="11" 
												maxlength="10" 
												tabindex="3" 
												onkeyup="mascaraData(this, event);replicaDataExecucao();"/>
											
											<a href="javascript:chamarCalendario('periodoPrevisaoInicial',document.forms[0].periodoPrevisaoInicial,'periodoPrevisaoFinal');">
												<img border="0" 
													src="<bean:message key='caminho.imagens'/>calendario.gif" 
													width="16" 
													height="15" 
													border="0" 
													alt="Exibir Calendário" />
											</a>

											a 
											
											<html:text property="periodoPrevisaoFinal" 
												size="11" 
												maxlength="10" 
												tabindex="3" 
												onkeyup="mascaraData(this, event)"/>
											
											<a href="javascript:chamarCalendario('periodoPrevisaoFinal',document.forms[0].periodoPrevisaooFinal,'');">
											<img border="0" 
												src="<bean:message key='caminho.imagens'/>calendario.gif" 
												width="16" 
												height="15" 
												border="0" 
												alt="Exibir Calendário"/>
											</a>
											
											</strong>(dd/mm/aaaa)<strong> 
					                  	</strong>
					                  	</span>
					              	</td>
				              	</tr>
				              	
				              <tr>
				              <td width="110"><strong>Dias de Atraso:</strong></td>
				                <td colspan="6">
				                <strong>
				                <html:text property="diasAtraso" size="9" maxlength="9" onkeypress="return isCampoNumerico(event);" />
				                </strong>
				              </td>
				              </tr>
				              
				              <tr>
				              <td width="110"><strong>Dias na Unidade:</strong></td>
				                <td colspan="6">
				                <strong>
				                <html:text property="quantidadeDiasUnidade" size="9" maxlength="9" onkeypress="return isCampoNumerico(event);" />
				                </strong>
				              </td>
				              </tr>
				
				           		<tr>
				             		<td height="10" colspan="3"> 
					             		<div align="right"> 
					                 		<hr>
					               		</div>
					               		<div align="right"> </div>
				               		</td>
				           		</tr>
				
								<tr>
									<td><strong>Munic&iacute;pio:</strong></td>
									<td>
										<html:text maxlength="4" 
											tabindex="1"
											property="municipio" 
											size="4"
											onkeypress="validaEnterComMensagem(event, 'exibirFiltrarOrdemServicoAction.do?objetoConsulta=8','municipio','Município');"/> 
											
										<a href="javascript:chamarPopup('exibirPesquisarMunicipioAction.do', 'municipio', null, null, 275, 480, '', document.forms[0].municipio);">
			
											<img width="23" 
												height="21" 
												border="0"
												src="<bean:message key="caminho.imagens"/>pesquisa.gif"
												title="Pesquisar Município" /></a>
													
				
										<logic:present name="municipioEncontrada" scope="request">
											<html:text property="descricaoMunicipio" 
												size="30"
												maxlength="30" 
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000" />
										</logic:present> 
				
										<logic:notPresent name="municipioEncontrada" scope="request">
											<html:text property="descricaoMunicipio" 
												size="30"
												maxlength="30" 
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: red" />
										</logic:notPresent>
				
										<a href="javascript:limparMunicipio();">
										<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
											border="0" 
											title="Apagar" /></a>
									
									</td>
								</tr>
				
								<tr>
									<td><strong>Bairro:</strong></td>
									<td>
										<html:text maxlength="4" 
											tabindex="1"
											property="codigoBairro" 
											size="4"
											onkeypress="validaEnterComMensagem(event, 'exibirFiltrarOrdemServicoAction.do?objetoConsulta=9','codigoBairro','Bairro');"/> 
										
										<a href="javascript:validarBairro();">
											<img width="23" 
												height="21" 
												border="0"
												src="<bean:message key="caminho.imagens"/>pesquisa.gif"
												title="Pesquisar Bairro" /></a> 
				
										<logic:present name="bairroEncontrada" scope="request">
											<html:text property="descricaoBairro" 
												size="30"
												maxlength="30" 
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000" />
										</logic:present> 
				
										<logic:notPresent name="bairroEncontrada" scope="request">
											<html:text property="descricaoBairro" 
												size="30"
												maxlength="30" 
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: red" />
										</logic:notPresent>
				
										<a href="javascript:limparBairro();">
										<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
											border="0" 
											title="Apagar" /></a>
									
									</td>
								</tr>
						        
								<tr>
									<td>
										<strong>&Aacute;rea do Bairro:</strong>
									</td>
				
									<td>
										<strong> 
										<html:select property="areaBairro" style="width: 230px;">
											<html:option
												value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
											</html:option>
									
											<logic:present name="colecaoAreaBairro" scope="request">
												<html:options collection="colecaoAreaBairro"
													labelProperty="nome" 
													property="id" />
											</logic:present>
										</html:select> 														
										</strong>
									</td>
								</tr>
				
								<tr>
									<td><strong>Logradouro:</strong></td>
									<td>
										<html:text maxlength="9" 
											tabindex="1"
											property="logradouro" 
											size="9"
											onkeypress="validaEnterComMensagem(event, 'exibirFiltrarOrdemServicoAction.do?objetoConsulta=10','logradouro','Logradouro');"/> 
											
										<a href="javascript:abrirPopup('exibirPesquisarLogradouroAction.do?codigoMunicipio='+document.forms[0].municipio.value+'&codigoBairro='+document.forms[0].codigoBairro.value+'&indicadorUsoTodos=1&primeriaVez=1', 400, 800);">
											<img width="23" 
												height="21" 
												border="0"
												src="<bean:message key="caminho.imagens"/>pesquisa.gif"
												title="Pesquisar Logradouro" /></a> 
				
										<logic:present name="logradouroEncontrado" scope="request">
											<html:text property="descricaoLogradouro" 
												size="40"
												maxlength="40" 
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: #000000" />
										</logic:present> 
				
										<logic:notPresent name="logradouroEncontrado" scope="request">
											<html:text property="descricaoLogradouro" 
												size="40"
												maxlength="40" 
												readonly="true"
												style="background-color:#EFEFEF; border:0; color: red" />
										</logic:notPresent>
				
										<a href="javascript:limparLogradouro();">
											<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
												border="0" 
												title="Apagar" /></a>
									</td>
								</tr>
								<tr>
				             		<td height="10" colspan="3"> 
					             		<div align="right"> 
					                 		<hr>
					               		</div>
					               		<div align="right"> </div>
				               		</td>
				           		</tr>
								
								<tr>
									<td width="100%" ><strong>Ordenação do Relatório: </strong></td>
									<td>
									    <input type="radio" name="ordenador" value="1"><strong>Imóvel</strong> 
										<input type="radio" name="ordenador" value="2"><strong>Logradouro</strong> 
										<input type="radio" name="ordenador" value="3"><strong>Solicitante</strong>
										<input type="radio" name="ordenador" value="4"><strong>Data OS</strong>  
									</td>
								</tr>
													
								
							</table>			
						</div>
					</td>
				</tr>
								
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
			</td>
		</tr>
	</table>
<%@ include file="/jsp/util/rodape.jsp"%>	
</html:form>
</body>
<!-- ordem_servico_filtrar.jsp -->
</html:html>
