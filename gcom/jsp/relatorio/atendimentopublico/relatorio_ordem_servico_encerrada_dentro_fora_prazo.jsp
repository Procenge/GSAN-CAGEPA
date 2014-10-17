<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<%@ page import="gcom.util.ConstantesSistema"%>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false" 
	formName="GerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm" staticJavascript="false" dynamicJavascript="true"/>

<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/engine.js'> </script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/util.js'> </script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/interface/AjaxService.js'> </script>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript">

	function validarForm(){
   		var form = document.forms[0];

    	if(validateGerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm(form)){
			if(validaTodosPeriodos()){
				enviarSelectMultiplo('GerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm','tipoServicoSelecionados');
				toggleBox('demodiv', 1);

			}
	  	}
    }
    
	function limparDescricaoLocalidade() {
		var form = document.forms[0];
		form.localidadeDescricaoFiltro.value = '';
	}
    
    function validaTodosPeriodos() {

		var form = document.forms[0];
		
		if (validaPeriodoAtendimento(form) && validaPeriodoEncerramento(form)) {

    		if (comparaData(form.periodoAtendimentoInicial.value, '>', form.periodoAtendimentoFinal.value)){

				alert('Data Final do Período de Atendimento é anterior à Data Inicial do Período de Atendimento');
				return false;

			} else if (comparaData(form.periodoEncerramentoInicial.value, '>', form.periodoEncerramentoFinal.value)){

				alert('Data Final do Período de Encerramento é anterior à Data Inicial do Período de Encerramento');
				return false;

			}
			
		} else {
			return false;
		} 

		return true;
    }
    
    function validaPeriodoAtendimento(form) {
    	
    	var periodoAtendimentoInicial = trim(form.periodoAtendimentoInicial.value);
 	   	var periodoAtendimentoFinal = trim(form.periodoAtendimentoFinal.value);
    	
    	if ((periodoAtendimentoInicial != null && periodoAtendimentoInicial != '') &&
    	((periodoAtendimentoFinal == null || periodoAtendimentoFinal == ''))) {
    		alert('Informe Data Final Período de Atendimento');
       		return false;
    	} else if ((periodoAtendimentoFinal != null && periodoAtendimentoFinal != '') &&
    	((periodoAtendimentoInicial == null || periodoAtendimentoInicial == ''))) {
    		alert('Informe Data Inicial Período de Atendimento');
    		return false;
    	}
    	return true;
    }
    
    function validaPeriodoEncerramento(form) {
    	var form = document.forms[0];
    	
    	var periodoEncerramentoInicial = trim(form.periodoEncerramentoInicial.value);
 	   	var periodoEncerramentoFinal = trim(form.periodoEncerramentoFinal.value);
    	
    	if ((periodoEncerramentoInicial != null && periodoEncerramentoInicial != '') &&
    	((periodoEncerramentoFinal == null || periodoEncerramentoFinal == ''))) {
    		alert('Informe Data Final Período de Encerramento');
       		return false;
    	} else if ((periodoEncerramentoFinal != null && periodoEncerramentoFinal != '') &&
    	((periodoEncerramentoInicial == null || periodoEncerramentoInicial == ''))) {
    		alert('Informe Data Inicial Período de Encerramento');
    		return false;
    	}
    	
    	return true;
    }

    function limparUnidadeAtendimento() {
        var form = document.forms[0];

        form.idUnidadeAtendimento.value = "";
    	form.descricaoUnidadeAtendimento.value = "";
    }

    function limparUnidadeAtual() {
        var form = document.forms[0];

        form.idUnidadeAtual.value = "";
    	form.descricaoUnidadeAtual.value = "";
    }

    function limparUnidadeEncerramento() {
        var form = document.forms[0];

        form.idUnidadeEncerramento.value = "";
    	form.descricaoUnidadeEncerramento.value = "";
    }
    
    function limparUnidadeAtendimentoTecla() {
        var form = document.forms[0];

    	form.descricaoUnidadeAtendimento.value = "";
    }

    function limparUnidadeAtualTecla() {
        var form = document.forms[0];

    	form.descricaoUnidadeAtual.value = "";
    }

    function limparUnidadeEncerramentoTecla() {
        var form = document.forms[0];

    	form.descricaoUnidadeEncerramento.value = "";
    }
    function limparPeriodoEncerramento() {

        var form = document.forms[0];
        
		form.periodoEncerramentoInicial.disabled = false;
		form.periodoEncerramentoFinal.disabled = false;
		
		form.periodoEncerramentoInicial.value="";
		form.periodoEncerramentoFinal.value="";
  	}

    function limparPeriodoAtendimento() {

        var form = document.forms[0];
        
		form.periodoAtendimentoInicial.disabled = false;
		form.periodoAtendimentoFinal.disabled = false;
		
		form.periodoAtendimentoInicial.value="";
		form.periodoAtendimentoFinal.value="";
  	}
    
	//Replica Data de Atendimento
	function replicaDataAtendimento() {
		var form = document.forms[0];
		form.periodoAtendimentoFinal.value = form.periodoAtendimentoInicial.value;
	}

	//Replica Data de Encerramento
	function replicaDataEncerramento() {
		var form = document.forms[0];
		form.periodoEncerramentoFinal.value = form.periodoEncerramentoInicial.value;
	}

	function validaForm(){
		var form = document.forms[0];
		
		if(	form.situacaoOrdemServico.value == '1' || 
			form.situacaoOrdemServico.value == '3' ||
			form.situacaoOrdemServico.value == '4' ){

			limparPeriodoEncerramento();
			
			form.periodoEncerramentoInicial.disabled = true;
			form.periodoEncerramentoFinal.disabled = true;
		
		}else{

			form.periodoEncerramentoInicial.disabled = false;
			form.periodoEncerramentoFinal.disabled = false;
		
		}
		
	}
	
	function validaOrigemServico(origem) {
		var form = document.forms[0];
		
		if (origem == '2') {
			form.idUnidadeAtendimento.value = "";
			form.descricaoUnidadeAtendimento.value = "";
			form.idUnidadeAtendimento.disabled = true;
			form.periodoAtendimentoInicial.value = "";
			form.periodoAtendimentoFinal.value = "";
			form.periodoAtendimentoInicial.disabled = true;
			form.periodoAtendimentoFinal.disabled = true;
		} else {
			form.idUnidadeAtendimento.disabled = false;	
			form.periodoAtendimentoInicial.disabled = false;
			form.periodoAtendimentoFinal.disabled = false;
		}
	}
	
	
	/* Chama Popup */ 
	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){
		if(objetoRelacionado.disabled != true){
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
	    
	    if (tipoConsulta == 'unidadeOrganizacional') {

	   		if (unidade == 1) {

		    	form.idUnidadeAtendimento.value = codigoRegistro;
		      	form.descricaoUnidadeAtendimento.value = descricaoRegistro;
	      		form.descricaoUnidadeAtendimento.style.color = "#000000";

	      	} else if (unidade == 2) {

		      	form.idUnidadeAtual.value = codigoRegistro;
		      	form.descricaoUnidadeAtual.value = descricaoRegistro;
      			form.descricaoUnidadeAtual.style.color = "#000000";

	      	} else if (unidade == 3) {
	      	
		      	form.idUnidadeEncerramento.value = codigoRegistro;
		      	form.descricaoUnidadeEncerramento.value = descricaoRegistro;
      			form.descricaoUnidadeEncerramento.style.color = "#000000";

	      	}
	      	
	      	unidade = 0;
	    
	    
	    } else if (tipoConsulta == 'localidade') {
	    	form.idLocalidadeFiltro.value = codigoRegistro;
			form.localidadeDescricaoFiltro.value = descricaoRegistro;
			form.localidadeDescricaoFiltro.style.color = "#000000";
	    } 
	}
	
	//So chama a função abrirCalendario caso o campo esteja habilitado
	function chamarCalendario(fieldNameOrigem,objetoRelacionado,fieldNameDestino){
		
		if(objetoRelacionado.disabled != true){
			if(fieldNameDestino != ''){
				abrirCalendarioReplicando('GerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm', fieldNameOrigem,fieldNameDestino);
			}else{
				abrirCalendario('GerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm', fieldNameOrigem);
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

	function limparPesquisaLocalidade(){
		var form = document.forms[0];

		form.idLocalidadeFiltro.value = "";
		form.localidadeDescricaoFiltro.value = "";
	}

	function pesquisaLocalidade(id){
		var form = document.forms[0];
		if (event.keyCode == 13 && id != ''){
			AjaxService.pesquisaLocalidade(id,{  
		        callback:function(retorno){  
		        	DWRUtil.setValue("localidadeDescricaoFiltro","");
		        	if (retorno == '-1'){
		        		DWRUtil.setValue("localidadeDescricaoFiltro", "LOCALIDADE INEXISTENTE");  
		            	form.localidadeDescricaoFiltro.style.color = "#ff0000";
				    } else {
			            DWRUtil.setValue("localidadeDescricaoFiltro", retorno);  
			            form.localidadeDescricaoFiltro.style.color = "#000000";
					}
		    	}
			});
		}
	}

	function limparFormulario(){
		var form = document.forms[0];
		limparUnidadeAtendimento();
		limparPesquisaLocalidade();
		limparPeriodoAtendimento();
		limparPeriodoEncerramento();
		limparUnidadeEncerramento();
		form.action = '/gsan/exibirGerarRelatorioOrdemServicoEncerradaDentroForaPrazoAction.do?menu=sim';
		form.submit();
	}
</script>

</head>

<body leftmargin="5" topmargin="5"
	onload="window.focus();javascript:setarFoco('${requestScope.nomeCampo}');javascript:validaOrigemServico('${requestScope.origemServico}');" >

<html:form action="/gerarRelatorioOrdemServicoEncerradaDentroForaPrazoAction" method="post"
	name="GerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm"
	type="gcom.gui.atendimentopublico.ordemservico.GerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm">

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
					<td class="parabg"> Gerar Relatório de Ordem de Serviço Encerrada Dentro e Fora do Prazo </td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">

				<tr>
					<td colspan="2">Para selecionar ordens de serviço para geração do
					relatório, informe os dados abaixo:</td>
				</tr>
				<tr>

					<td><strong>Origem dos Serviços:</strong></td>
					<td><html:radio property="origemServico"
						value="<%=""+ConstantesSistema.ORIGEM_SERVICO_SOLICITADO%>"
						onclick="javascript:validaOrigemServico('1');" /> <strong>Solicitados</strong>
					<html:radio property="origemServico"
						value="<%=""+ConstantesSistema.ORIGEM_SERVICO_SELETIVO%>"
						onclick="javascript:validaOrigemServico('2');" />
						<strong>Seletivos</strong></td>
				</tr>
				<tr>
					<td height="10" colspan="3">
					<div align="right">
					<hr>
					</div>
					<div align="right"></div>
					</td>
				</tr>
				<tr>
					<td width="120"><strong>Tipo de Servi&ccedil;o:</strong></td>
					<td colspan="2">
					<table width="100%" border=0 cellpadding=0 cellspacing=0
						align="left">
						<tr>
							<td width="175">

							<div align="left"><strong>Disponíveis</strong></div>

							<html:select property="tipoServico" size="6" multiple="true"
								style="width:190px">

								<html:options collection="colecaoTipoServico"
									labelProperty="descricao" property="id" />
							</html:select></td>

							<td width="5" valign="center"><br>
							<table width="50" align="center">
								<tr>
									<td align="center"><input type="button" class="bottonRightCol"
										onclick="javascript:MoverTodosDadosSelectMenu1PARAMenu2('GerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm', 'tipoServico', 'tipoServicoSelecionados');"
										value=" &gt;&gt; "></td>
								</tr>

								<tr>
									<td align="center"><input type="button" class="bottonRightCol"
										onclick="javascript:MoverDadosSelectMenu1PARAMenu2('GerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm', 'tipoServico', 'tipoServicoSelecionados');"
										value=" &nbsp;&gt;  "></td>
								</tr>

								<tr>
									<td align="center"><input type="button" class="bottonRightCol"
										onclick="javascript:MoverDadosSelectMenu2PARAMenu1('GerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm', 'tipoServico', 'tipoServicoSelecionados');"
										value=" &nbsp;&lt;  "></td>
								</tr>

								<tr>
									<td align="center"><input type="button" class="bottonRightCol"
										onclick="javascript:MoverTodosDadosSelectMenu2PARAMenu1('GerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm', 'tipoServico', 'tipoServicoSelecionados');"
										value=" &lt;&lt; "></td>
								</tr>
							</table>
							</td>

							<td>
							<div align="left"><strong>Selecionados</strong></div>

							<html:select property="tipoServicoSelecionados" size="6"
								multiple="true" style="width:190px">

							</html:select></td>
						</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td height="10" colspan="3">
					<div align="right">
					<hr>
					</div>
					<div align="right"></div>
					</td>
				</tr>
				
				<tr>
					<td width="19%"><strong>Localidade:</strong></td>
					<td colspan="2" width="81%" height="24"><html:text maxlength="3"
						tabindex="1" property="idLocalidadeFiltro" size="3"
						onkeypress=" validaEnterComMensagem(event, 'exibirGerarRelatorioOrdemServicoEncerradaDentroForaPrazoAction.do?objetoConsulta=6','idLocalidadeFiltro','Localidade'); javascript:limparDescricaoLocalidade();pesquisaLocalidade(this.value);return isCampoNumerico(event);" />
					<a
						href="javascript:abrirPopup('exibirPesquisarLocalidadeAction.do?tipo=imovelLocalidade&indicadorUsoTodos=1', 400, 800);limparPesquisaQuadra();limparPesquisaSetorComercial();">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Localidade" /></a> <logic:present
						name="idLocalidadeNaoEncontrada">
						<logic:equal name="idLocalidadeNaoEncontrada" value="exception">
							<html:text property="localidadeDescricaoFiltro" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>
						<logic:notEqual name="idLocalidadeNaoEncontrada" value="exception">
							<html:text property="localidadeDescricaoFiltro" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> <logic:notPresent name="idLocalidadeNaoEncontrada">
						<logic:empty name="GerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm"
							property="idLocalidadeFiltro">
							<html:text property="localidadeDescricaoFiltro" value=""
								size="40" maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="GerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm"
							property="idLocalidadeFiltro">
							<html:text property="localidadeDescricaoFiltro" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>

					</logic:notPresent> <a
						href="javascript:limparPesquisaLocalidade();">
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
				

				<tr>
					<td><strong>Unidade de Atendimento:</strong></td>
					<td><html:text maxlength="8" tabindex="2"
						property="idUnidadeAtendimento" size="6"
						onkeyup="javascript:limparUnidadeAtendimentoTecla();"
						onkeypress="validaEnterComMensagem(event, 'exibirGerarRelatorioOrdemServicoEncerradaDentroForaPrazoAction.do?objetoConsulta=1','idUnidadeAtendimento','Unidade Atendimento');" />
					<a
						href="javascript:setUnidade(1); chamarPopup('exibirPesquisarUnidadeOrganizacionalAction.do?tipoUnidade=unidadeAtendimento', 'idUnidadeAtendimento', null, null, 275, 480, '', document.forms[0].idUnidadeAtendimento);">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Unidade Atendimento" /></a> <logic:present
						name="unidadeAtendimentoEncontrada" scope="request">

						<html:text property="descricaoUnidadeAtendimento" size="40"
							maxlength="40" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000" />

					</logic:present> <logic:notPresent
						name="unidadeAtendimentoEncontrada" scope="request">

						<html:text property="descricaoUnidadeAtendimento" size="40"
							maxlength="40" readonly="true"
							style="background-color:#EFEFEF; border:0; color: red" />

					</logic:notPresent> <a
						href="javascript:limparUnidadeAtendimento();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /> </a></td>
				</tr>

				<tr>
					<td><strong>Unidade Atual:<font color="#FF0000">*</font></strong></td>
					<td><html:text maxlength="8" tabindex="3" property="idUnidadeAtual"
						size="6" onkeyup="javascript:limparUnidadeAtualTecla();"
						onkeypress="validaEnterComMensagem(event, 'exibirGerarRelatorioOrdemServicoEncerradaDentroForaPrazoAction.do?objetoConsulta=2','idUnidadeAtual','Unidade Atual');" />

					<a
						href="javascript:setUnidade(2); chamarPopup('exibirPesquisarUnidadeOrganizacionalAction.do?tipoUnidade=unidadeAtual', 'idUnidadeAtual', null, null, 275, 480, '', document.forms[0].idUnidadeAtual);">

					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Unidade Atual" /></a> <logic:present
						name="unidadeAtualEncontrada" scope="request">

						<html:text property="descricaoUnidadeAtual" size="40"
							maxlength="40" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000" />

					</logic:present> <logic:notPresent name="unidadeAtualEncontrada"
						scope="request">

						<html:text property="descricaoUnidadeAtual" size="40"
							maxlength="40" readonly="true"
							style="background-color:#EFEFEF; border:0; color: red" />

					</logic:notPresent> <a href="javascript:limparUnidadeAtual();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
				<tr>
					<td><strong>Unidade Encerramento:</strong></td>
					<td><html:text maxlength="8" tabindex="4"
						property="idUnidadeEncerramento" size="6"
						onkeyup="javascript:limparUnidadeEncerramentoTecla();"
						onkeypress="validaEnterComMensagem(event, 'exibirGerarRelatorioOrdemServicoEncerradaDentroForaPrazoAction.do?objetoConsulta=3','idUnidadeEncerramento','Unidade Encerramento');" />

					<a
						href="javascript:setUnidade(3); chamarPopup('exibirPesquisarUnidadeOrganizacionalAction.do?tipoUnidade=unidadeEncerramento', 'idUnidadeEnceramento', null, null, 275, 480, '', document.forms[0].idUnidadeEncerramento);">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Unidade Superior" /></a> <logic:present
						name="unidadeEncerramentoEncontrada" scope="request">
						<html:text property="descricaoUnidadeEncerramento" size="40"
							maxlength="40" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000" />
					</logic:present> <logic:notPresent
						name="unidadeEncerramentoEncontrada" scope="request">

						<html:text property="descricaoUnidadeEncerramento" size="40"
							maxlength="40" readonly="true"
							style="background-color:#EFEFEF; border:0; color: red" />

					</logic:notPresent> <a
						href="javascript:limparUnidadeEncerramento();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /> </a></td>
				</tr>

				<tr>
					<td height="10" colspan="3">
					<div align="right">
					<hr>
					</div>
					<div align="right"></div>
					</td>
				</tr>

				<tr>
					<td><strong>Per&iacute;odo de Atendimento:</strong></td>

					<td colspan="6"><span class="style2"> <strong> <html:text
						property="periodoAtendimentoInicial" size="11" maxlength="10"
						tabindex="5"
						onkeyup="mascaraData(this, event);replicaDataAtendimento();" /> <a
						href="javascript:abrirCalendarioReplicando('GerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm', 'periodoAtendimentoInicial','periodoAtendimentoFinal');">
					<img border="0"
						src="<bean:message key='caminho.imagens'/>calendario.gif"
						width="16" height="15" border="0" alt="Exibir Calendário"
						/></a> a <html:text
						property="periodoAtendimentoFinal" size="11" maxlength="10"
						tabindex="6" onkeyup="mascaraData(this, event)" /> <a
						href="javascript:abrirCalendario('GerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm', 'periodoAtendimentoFinal');">
					<img border="0"
						src="<bean:message key='caminho.imagens'/>calendario.gif"
						width="16" height="15" border="0" alt="Exibir Calendário"
						/></a> </strong>(dd/mm/aaaa)<strong> </strong> </span>
					</td>
				</tr>
				<tr>
					<td><strong>Per&iacute;odo de Encerramento:</strong></td>

					<td colspan="6"><span class="style2"> <strong> <html:text
						property="periodoEncerramentoInicial" size="11" maxlength="10"
						tabindex="7"
						onkeyup="mascaraData(this, event);replicaDataEncerramento();" />
					<a
						href="javascript:chamarCalendario('periodoEncerramentoInicial',document.forms[0].periodoEncerramentoInicial,'periodoEncerramentoFinal');">
					<img border="0"
						src="<bean:message key='caminho.imagens'/>calendario.gif"
						width="16" height="15" border="0" alt="Exibir Calendário" /></a> a <html:text
						property="periodoEncerramentoFinal" size="11" maxlength="10"
						tabindex="8" onkeyup="mascaraData(this, event)" /> <a
						href="javascript:chamarCalendario('periodoEncerramentoFinal',document.forms[0].periodoEncerramentoFinal,'');">
					<img border="0"
						src="<bean:message key='caminho.imagens'/>calendario.gif"
						width="16" height="15" border="0" alt="Exibir Calendário"/></a> </strong>(dd/mm/aaaa)<strong> </strong> </span>
					</td>
				</tr>

				<tr>
					<td height="10" colspan="3">
					<div align="right">
					<hr>
					</div>
					<div align="right"></div>
					</td>
				</tr>
				<tr>
				
					<td><strong>Tipo de Ordenação</strong></td>
					<td><html:radio property="tipoOrdenacao" value="1">
						<strong> Número RA</strong>
					</html:radio> <html:radio property="tipoOrdenacao" value="2">
						<strong> Endereço</strong>
					</html:radio></td>
				
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td align="right">
					<div align="left"><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios</div>
					</td>
				</tr>
				<tr>
					<td height="24">
						<input type="button" class="bottonRightCol"
									value="Limpar"
									onclick="javascrip:limparFormulario();" 
									tabindex="12"/>
					</td>

					<td align="right"><input type="button" name="Button"
						class="bottonRightCol" value="Gerar" tabindex="11"
						onClick="javascript:validarForm()" /></td>

				</tr>
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>
	<jsp:include
		page="/jsp/relatorio/escolher_tipo_relatorio.jsp?relatorio=gerarRelatorioOrdemServicoEncerradaDentroForaPrazoAction.do&left=400&top=510" />
	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</body>
</html:html>
