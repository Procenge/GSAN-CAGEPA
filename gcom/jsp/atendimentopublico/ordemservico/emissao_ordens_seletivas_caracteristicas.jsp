<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gcom.util.ConstantesSistema"%>
<%@ page import="gcom.gui.atendimentopublico.ordemservico.ConsumoMedioImovelHelper"%>
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/engine.js'> </script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/util.js'> </script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/interface/AjaxService.js'> </script>

<html:javascript staticJavascript="false" formName="ImovelEmissaoOrdensSeletivasActionForm" dynamicJavascript="false" />
<script language="JavaScript">

 var bCancel = false;

function validarForm(form){

  if(validatePesquisarActionForm(form)){
    form.submit();
  }
}

</script>

<script>
<!-- Begin 

     var bCancel = false; 

    function validateImovelEmissaoOrdensSeletivasActionForm(form) {                                                                   
		if (validateInteiroZeroPositivo(form)) {
			if (!VerificaIntervalosPreenchidos(form.intervaloQuantidadeEconomiasInicial,
					form.intervaloQuantidadeEconomiasFinal,
					'Verifique o preenchimento do Intervalo de Quantidade de Economias')) {
					
				return false;
			}
			
			if (!VerificaIntervalosPreenchidos(form.intervaloQuantidadeDocumentosInicial,
					form.intervaloQuantidadeDocumentosFinal,
					'Verifique o preenchimento do Intervalo de Quantidade de Documentos')) {
					
				return false;
			}
			
			if (!VerificaIntervalosPreenchidos(form.intervaloNumeroMoradoresInicial,
					form.intervaloNumeroMoradoresFinal,
					'Verifique o preenchimento do Intervalo de Número de Moradores')) {
					
				return false;
			}

			if (!VerificaIntervalosPreenchidos(form.intervaloNumeroConsumoMesInicial,
					form.intervaloNumeroConsumoMesFinal,
					'Verifique o preenchimento do Intervalo de Faixa de Consumo')) {

				return false;
			}

			if (!VerificaIntervalosPreenchidos(form.intervaloQuantidadeContasVencidasInicial,
					form.intervaloQuantidadeContasVencidasFinal,
					'Verifique o preenchimento do Intervalo de quantidade de Débito')) {

				return false;
			}

			if (!VerificaIntervalosPreenchidos(form.intervaloNumeroPontosUtilizacaoInicial,
					form.intervaloNumeroPontosUtilizacaoFinal,
					'Verifique o preenchimento do Intervalo de Pontos de Utilização')) {

				return false;
			}

			if (!VerificaIntervalosPreenchidos(form.intervaloDataCorteInicial,
					form.intervaloDataCorteFinal,
					'Verifique o preenchimento do Período de Corte')) {

				return false;
			}

			if (!verificaAnoMesMensagemPersonalizada(form.intervaloDataCorteInicial,
					"Mês/Ano Inicial do Período de Corte Inválido.")) {
				return false;
			}

			if (!verificaAnoMesMensagemPersonalizada(form.intervaloDataCorteFinal,
					"Mês/Ano Final do Período de Corte Inválido.")) {
				return false;
			}

			if ( parseInt(form.intervaloQuantidadeEconomiasFinal.value) <
				 parseInt(form.intervaloQuantidadeEconomiasInicial.value) ) {
	
				alert('O Intervalo de Quantidade de Economia Final deve ser Maior ou Igual a Inicial.');
				form.intervaloQuantidadeEconomiasFinal.focus();
				return false;
			}
			
			if ( parseInt(form.intervaloQuantidadeDocumentosFinal.value) <
				 parseInt(form.intervaloQuantidadeDocumentosInicial.value) ) {
	
				alert('O Intervalo de Quantidade de Documento Final deve ser Maior ou Igual a Inicial.');
				form.intervaloQuantidadeDocumentosFinal.focus();
				return false;
			}
			
			if ( parseInt(form.intervaloNumeroMoradoresFinal.value) <
				 parseInt(form.intervaloNumeroMoradoresInicial.value) ) {
	
				alert('O Intervalo de Número de Moradores Final deve ser Maior ou Igual a Inicial.');
				form.intervaloNumeroMoradoresFinal.focus();
				return false;
			}

			if ( parseInt(form.intervaloNumeroConsumoMesFinal.value) <
				parseInt(form.intervaloNumeroConsumoMesInicial.value) ) {

				alert('O Intervalo de Faixa de Consumo Final deve ser Maior ou Igual a Inicial.');
				form.intervaloNumeroConsumoMesFinal.focus();
				return false;
			}

			if ( parseInt(form.intervaloQuantidadeContasVencidasFinal.value) <
				parseInt(form.intervaloQuantidadeContasVencidasInicial.value) ) {

				alert('O Intervalo de quantidade de Débito Final deve ser Maior ou Igual a Inicial.');
				form.intervaloQuantidadeContasVencidasFinal.focus();
				return false;
			}

			if ( parseInt(form.intervaloNumeroPontosUtilizacaoFinal.value) <
				parseInt(form.intervaloNumeroPontosUtilizacaoInicial.value) ) {

				alert('O Intervalo de Pontos de Utilização Final deve ser Maior ou Igual a Inicial.');
				form.intervaloNumeroPontosUtilizacaoFinal.focus();
				return false;
			}

			if (comparaMesAno(form.intervaloDataCorteInicial.value, ">", form.intervaloDataCorteFinal.value)){

				alert("Mês/Ano Final do Período de Corte é anterior ao Mês/Ano Inicial do Período de Corte.");
				form.intervaloDataCorteFinal.focus();
				return false;
			}

			var intervaloAreaConstruidaInicial = form.intervaloAreaConstruidaInicial.value;
			var intervaloAreaConstruidaFinal = form.intervaloAreaConstruidaFinal.value;
			
			intervaloAreaConstruidaInicial = intervaloAreaConstruidaInicial.replace('.', '');
			intervaloAreaConstruidaInicial = intervaloAreaConstruidaInicial.replace(',', '');
			intervaloAreaConstruidaFinal = intervaloAreaConstruidaFinal.replace('.', '');
			intervaloAreaConstruidaFinal = intervaloAreaConstruidaFinal.replace(',', '');
			
			if ( parseInt(intervaloAreaConstruidaFinal) < parseInt(intervaloAreaConstruidaInicial) ) {
				alert('O Intervalo da Área Construida Final deve ser Maior ou Igual a Inicial.');
				form.intervaloAreaConstruidaFinal.focus();
				return false;
			}
	      	
			return true;
		}else {
			return false;
		}
	}

	function InteiroZeroPositivoValidations () { 
		this.aa = new Array("intervaloQuantidadeEconomiasInicial", "A Quantidade Inicial de Economias deve somente conter números positivos ou zero.", new Function ("varName", " return this[varName];"));
		this.ab = new Array("intervaloQuantidadeEconomiasFinal", "A Quantidade Final de Economias deve somente conter números positivos ou zero.", new Function ("varName", " return this[varName];"));
		this.ac = new Array("intervaloQuantidadeDocumentosInicial", "A Quantidade Inicial de Documentos deve somente conter números positivos ou zero.", new Function ("varName", " return this[varName];"));
		this.ad = new Array("intervaloQuantidadeDocumentosFinal", "A Quantidade Final de Documentos deve somente conter números positivos ou zero.", new Function ("varName", " return this[varName];"));
		this.ae = new Array("intervaloNumeroMoradoresInicial", "A Quantidade Inicial de Moradores deve somente conter números positivos ou zero.", new Function ("varName", " return this[varName];"));
		this.af = new Array("intervaloNumeroMoradoresFinal", "A Quantidade Final de Moradores deve somente conter números positivos ou zero.", new Function ("varName", " return this[varName];"));
		this.ag = new Array("intervaloAreaConstruidaInicial", "O Intervalo Inicial de Área Construida deve somente conter números positivos ou zero.", new Function ("varName", " return this[varName];"));
		this.ah = new Array("intervaloAreaConstruidaFinal", "O Intervalo Final de Área Construida deve somente conter números positivos ou zero.", new Function ("varName", " return this[varName];"));
		this.aj = new Array("consumoPorEconomia", "O Consumo Por Economia deve somente conter números positivos ou zero.", new Function ("varName", " return this[varName];"));
		this.ak = new Array("intervaloNumeroConsumoMesInicial", "O Intervalo Inicial de Faixa de Consumo deve somente conter números positivos ou zero.", new Function ("varName", " return this[varName];"));
		this.al = new Array("intervaloNumeroConsumoMesFinal", "O Intervalo Final de Faixa de Consumo deve somente conter números positivos ou zero.", new Function ("varName", " return this[varName];"));
		this.am = new Array("intervaloQuantidadeContasVencidasInicial", "O Intervalo Inicial de quantidade de Débito deve somente conter números positivos ou zero.", new Function ("varName", " return this[varName];"));
		this.an = new Array("intervaloQuantidadeContasVencidasFinal", "O Intervalo Final de quantidade de Débito deve somente conter números positivos ou zero.", new Function ("varName", " return this[varName];"));
		this.ao = new Array("intervaloNumeroPontosUtilizacaoInicial", "O Intervalo Inicial de Pontos de Utilização deve somente conter números positivos ou zero.", new Function ("varName", " return this[varName];"));
		this.ap = new Array("intervaloNumeroPontosUtilizacaoFinal", "O Intervalo Final de Pontos de Utilização deve somente conter números positivos ou zero.", new Function ("varName", " return this[varName];"));
	}
	
	function VerificaIntervalosPreenchidos(interInicial, interFinal, msg) {
		if(interInicial.value != '' && interFinal.value == '') {
			alert(msg);
			interFinal.focus();
			return false;
		}else if (interInicial.value == '' && interFinal.value != '') {
			alert(msg);
			interInicial.focus();
			return false;
		}
		
		return true;
	}
    
    function ControleCategoriaSubCategoria() {
    	var form = document.ImovelEmissaoOrdensSeletivasActionForm;
    	var obj = form.categoria;
    	
    	if (obj.selectedIndex == 0) {
    		form.subCategoria.disabled = true;
    		form.subCategoria[0].selected = true;
    	}else {
    		if (form.categoria.selectedIndex == 0 || form.categoria.selectedIndex == -1) {
    			form.subCategoria.disabled = true;
    			form.subCategoria.value = "-1";
    		}else {
    			form.subCategoria.disabled = false;
    		}
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
    
    function duplicarIntervaloQuantidadeEconomias(){
		var formulario = document.forms[0]; 
		formulario.intervaloQuantidadeEconomiasFinal.value = formulario.intervaloQuantidadeEconomiasInicial.value;
	}
	
	function duplicarIntervaloQuantidadeDocumentos(){
		var formulario = document.forms[0]; 
		formulario.intervaloQuantidadeDocumentosFinal.value = formulario.intervaloQuantidadeDocumentosInicial.value;
	}
	
	function duplicarIntervaloNumeroMoradores(){
		var formulario = document.forms[0]; 
		formulario.intervaloNumeroMoradoresFinal.value = formulario.intervaloNumeroMoradoresInicial.value;
	}

	function duplicarIntervaloDataCorte(){
		var formulario = document.forms[0];
		formulario.intervaloDataCorteFinal.value = formulario.intervaloDataCorteInicial.value;
	}
	
	function duplicarDataCorte(){
		var formulario = document.forms[0];
		formulario.dataCorteFinal.value = formulario.dataCorteInicial.value;
	}

	function duplicarIntervaloAreaConstruida(){
		var formulario = document.forms[0]; 
		formulario.intervaloAreaConstruidaFinal.value = formulario.intervaloAreaConstruidaInicial.value;
	}

	function duplicarIntervaloNumeroConsumoMes(){
		var formulario = document.forms[0]; 
		formulario.intervaloNumeroConsumoMesFinal.value = formulario.intervaloNumeroConsumoMesInicial.value;
	}

	function duplicarIntervaloQuantidadeContasVencidas(){
		var formulario = document.forms[0]; 
		formulario.intervaloQuantidadeContasVencidasFinal.value = formulario.intervaloQuantidadeContasVencidasInicial.value;
	}

    function duplicarIntervaloNumeroPontosUtilizacao(){
		var formulario = document.forms[0]; 
		formulario.intervaloNumeroPontosUtilizacaoFinal.value = formulario.intervaloNumeroPontosUtilizacaoInicial.value;
	}

	function limparIntervaloAreaConstruida() {
		var form = document.forms[0];
		
		form.intervaloAreaConstruidaInicial.value = '';
		form.intervaloAreaConstruidaFinal.value = '';
	}
	
	function controleIntervaloAreaConstruidaPreDefinida() {
		var form = document.forms[0];

		if ( (form.intervaloAreaConstruidaInicial.value != '') ||
			 (form.intervaloAreaConstruidaFinal.value != '') ) {
		
			form.intervaloAreaConstruidaPredefinida.selectedIndex = 0;
		}
	}

	function atualizarLigacaoAguaSituacao() {
		form = document.forms[0];
		form.action = "/gsan/filtrarImovelEmissaoOrdensSeletivasWizardAction.do?action=exibirFiltrarImovelEmissaoOrdensSeletivasCaracteristicas&objetoConsulta=1";
		form.submit();
	}

	function atualizarForm() {
		form = document.forms[0];
		form.action = "/gsan/filtrarImovelEmissaoOrdensSeletivasWizardAction.do?action=exibirFiltrarImovelEmissaoOrdensSeletivasCaracteristicas";
		form.submit();
	}

	function removerConsumoMedioImovel(url) {
		if (confirm('Confirma remoção ?')) {
			var form = document.forms[0];
			form.action = url;
			form.submit();
		}
	}

-->    
</script>

</head>

<body leftmargin="5" topmargin="5" onload="ControleCategoriaSubCategoria();">

<html:form action="/filtrarImovelEmissaoOrdensSeletivasWizardAction"
	name="ImovelEmissaoOrdensSeletivasActionForm"
	type="gcom.gui.atendimentopublico.ordemservico.ImovelEmissaoOrdensSeletivasActionForm"
	method="post"
	onsubmit="return validateImovelEmissaoOrdensSeletivasActionForm(this);">

	<jsp:include page="/jsp/util/wizard/navegacao_abas_wizard_valida_avancar_valida_voltar.jsp?numeroPagina=3" />

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="4" cellpadding="0">
		<input type="hidden" name="numeroPagina" value="4" />	
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
			<td width="617" valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><html:img src="imagens/parahead_left.gif" border="0" /></td>
					<td class="parabg">Filtrar Imóvel</td>
					<td width="11" valign="top"><html:img
						src="imagens/parahead_right.gif" border="0" /></td>
				</tr>
			</table>
			<!--Fim Tabela Reference a Páginação da Tela de Processo-->
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para filtrar o(s) im&oacute;vel(is) pelas
					caracter&iacute;sticas gerais, informe os dados abaixo:</td>
				</tr>
				
				<tr>
					<td>
					<strong>Perfil do Imóvel:</strong>
					</td>
				</tr>
				
				<tr>
					<td align="left">
								<html:select property="perfilImovel" multiple="true" style="width: 230px;">
									<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
									<logic:present name="collectionImovelPerfil">
										<html:options collection="collectionImovelPerfil"
											labelProperty="descricao" property="id" />
									</logic:present>
								</html:select>
						<html:hidden property="perfilImovelDescricao"/>
					</td>
				</tr>
				
			<td>
				<table cellpadding="0" cellspacing="2">
				<tr>
					<td>
					<strong>Categoria:</strong>
					</td>
					<td>
					<strong>Subcategoria:</strong>
					</td>
				</tr>
				<tr>
					<td align="left">
								<html:select property="categoria" multiple="true" onchange="ControleCategoria();" style="width: 230px;">
									<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
									<logic:present name="collectionImovelCategoria">
										<html:options collection="collectionImovelCategoria"
											labelProperty="descricao" property="id" />
									</logic:present>
								</html:select>
						<html:hidden property="categoriaDescricao"/>
					</td>
					<td align="left">
								<html:select property="subCategoria" styleId="subcategoria" multiple="true" style="width: 230px;">
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
			</td>				
				
				<tr><td colspan="2"><hr></td></tr>

				<tr>
					<td colspan="2">
						<table border="0">
							<tr>
								<td width="175"><strong>Situação da Ligação de Água:</strong></td>
								<td align="left">
									<html:select property="ligacaoAguaSituacao" multiple="true" onclick="atualizarLigacaoAguaSituacao();">
										<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
										<logic:present name="colecaoLigacaoAguaSituacao">
											<html:options collection="colecaoLigacaoAguaSituacao" property="id" labelProperty="descricao" />
										</logic:present>		
									</html:select>
								</td>

								<td>&nbsp;</td>

								<td width="175"><strong>Situação da Ligação de Esgoto:</strong></td>
								<td align="left">
									<html:select property="ligacaoEsgotoSituacao" multiple="true">
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

				<logic:present name="exibirIntervaloDataCorte" scope="session">
					<logic:equal name="exibirIntervaloDataCorte" scope="session" value="S">
						<tr>
							<td colspan="2">
								<table border="0">
									<tr>
										<td width="175"><strong>Cortado no Período de:</strong></td>
										<td align="left"><html:text property="intervaloDataCorteInicial"
										size="7" maxlength="7" onkeyup="duplicarIntervaloDataCorte(); mascaraAnoMes(this, event);"></html:text></td> 
										<td>&nbsp;a&nbsp;</td>
										<td align="left"><html:text property="intervaloDataCorteFinal" size="7" maxlength="7" onkeyup="mascaraAnoMes(this, event);"></html:text></td> 
										<td>(mm/aaaa)</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<table border="0">
									<tr>
										<td width="175"><strong>Data de Corte:</strong></td>
										<td align="left"><html:text property="dataCorteInicial"
											size="7" maxlength="10" onkeyup="duplicarDataCorte(); mascara_data(this, event);"></html:text></td>
										<td>&nbsp;a&nbsp;</td>
										<td align="left"><html:text property="dataCorteFinal" size="7" maxlength="10" onkeyup="mascara_data(this, event);"></html:text></td>
										<td>(dd/mm/aaaa)</td>
									</tr>
								</table>
							</td>
						</tr>
					</logic:equal>

					<logic:notEqual name="exibirIntervaloDataCorte" scope="session" value="S">
						<html:hidden property="intervaloDataCorteInicial" value=""/>
						<html:hidden property="intervaloDataCorteFinal" value=""/>
						
						<html:hidden property="dataCorteInicial" value=""/>
						<html:hidden property="dataCorteFinal" value=""/>
					</logic:notEqual>
				</logic:present>

				<logic:notPresent name="exibirIntervaloDataCorte" scope="session">
					<html:hidden property="intervaloDataCorteInicial" value=""/>
					<html:hidden property="intervaloDataCorteFinal" value=""/>
					
					<html:hidden property="dataCorteInicial" value=""/>
					<html:hidden property="dataCorteFinal" value=""/>
				</logic:notPresent>
				<tr><td colspan="2"><hr></td></tr>
				
				<tr>
					<td colspan="2">
						<table border="0">
							<tr>
								<td width="175"><strong>Intervalo de Quantidade de Economias:</strong></td>
								<td align="left"><html:text property="intervaloQuantidadeEconomiasInicial"
									size="5" maxlength="4" onkeyup="duplicarIntervaloQuantidadeEconomias();"></html:text></td>
								<td>&nbsp;a&nbsp;</td>
								<td align="left"><html:text property="intervaloQuantidadeEconomiasFinal" size="5" maxlength="4"></html:text></td>

								<td>&nbsp;</td>

								<td><strong>Intervalo de Pontos de Utilização:</strong></td>
								<td align="left"><html:text property="intervaloNumeroPontosUtilizacaoInicial"
									size="5" maxlength="4" onkeyup="duplicarIntervaloNumeroPontosUtilizacao();"></html:text></td>
								<td>&nbsp;a&nbsp;</td>
								<td align="left"><html:text property="intervaloNumeroPontosUtilizacaoFinal" size="5" maxlength="4"></html:text></td>
							</tr>
						</table>
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<table border="0">
							<tr>
								<td width="175"><strong>Intervalo de Quantidade de Documentos:</strong></td>
								<td align="left"><html:text property="intervaloQuantidadeDocumentosInicial"
									size="5" maxlength="4" onkeyup="duplicarIntervaloQuantidadeDocumentos();"></html:text></td>
								<td>&nbsp;a&nbsp;</td>
								<td align="left"><html:text property="intervaloQuantidadeDocumentosFinal" size="5" maxlength="4"></html:text></td>
							</tr>
						</table>
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<table border="0">
							<tr>
								<td width="175"><strong>Intervalo de N&uacute;mero de Moradores:</strong></td>
								<td align="left"><html:text property="intervaloNumeroMoradoresInicial"
									size="5" maxlength="4" onkeyup="duplicarIntervaloNumeroMoradores();"></html:text></td>
								<td>&nbsp;a&nbsp;</td>
								<td align="left"><html:text property="intervaloNumeroMoradoresFinal" size="5" maxlength="4"></html:text></td>
							</tr>
						</table>
					</td>
				</tr>
				
				<tr><td colspan="2"><hr></td></tr>
				
				<tr>
					<td colspan="2">
						<table border="0">
							<tr>
								<td width="175"><strong>Intervalo de &Aacute;rea Construida:</strong></td>
								<td align="left">
									<html:text property="intervaloAreaConstruidaInicial" size="7"
										maxlength="10" onkeyup="formataValorMonetario(this, 10); controleIntervaloAreaConstruidaPreDefinida(); duplicarIntervaloAreaConstruida();"
										style="text-align:right;">
									</html:text>
								</td>
								<td>&nbsp;a&nbsp;</td>
								<td align="left">
									<html:text property="intervaloAreaConstruidaFinal" size="7"
										maxlength="10" onkeyup="formataValorMonetario(this, 10); controleIntervaloAreaConstruidaPreDefinida();"
										style="text-align:right;">
									</html:text>
								</td>
								<td>&nbsp;&nbsp;</td>
								<td>
									<html:select property="intervaloAreaConstruidaPredefinida" style="width: 170px;"
										onchange="limparIntervaloAreaConstruida();">
										<html:option value="<%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
										<logic:present name="collectionIntervaloAreaConstruidaPredefinida">
											<html:options collection="collectionIntervaloAreaConstruidaPredefinida"
												labelProperty="faixaCompleta" property="id" />
										</logic:present>
									</html:select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				
				<tr>
					<td><strong>Im&oacute;vel Condom&iacute;nio:</strong></td>
					<td align="left">
						<table width="100%" border="0">
							<tr>
								<td width="175"><html:radio value="1" property="imovelCondominio"></html:radio>&nbsp;Sim</td>
								<td align="left"><html:radio value="2" property="imovelCondominio"></html:radio>&nbsp;N&atilde;o</td>
							</tr>
						</table>
					</td>
				</tr>
				
				<tr><td colspan="2"><hr></td></tr>

				<tr>
					<td colspan="2">
						<table border="0">
							<tr>
								<td width="175"><strong>Consumo por Economia:</strong></td>
								<td align="left">
									<logic:equal name="tipoOrdem" value="INSTALACAO_HIDROMETRO">
										<html:text property="consumoPorEconomia" size="8" maxlength="6" disabled="true"></html:text>
									</logic:equal>
									<logic:notEqual name="tipoOrdem" value="INSTALACAO_HIDROMETRO">
										<html:text property="consumoPorEconomia" size="8" maxlength="6"></html:text>
									</logic:notEqual>
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td colspan="2">
						<table border="0">
							<tr>
								<td width="175"><strong>Faixa de Consumo:</strong></td>
								<td align="left"><html:text property="intervaloNumeroConsumoMesInicial"
									size="5" maxlength="4" onkeyup="duplicarIntervaloNumeroConsumoMes();"></html:text></td>
								<td>&nbsp;a&nbsp;</td>
								<td align="left"><html:text property="intervaloNumeroConsumoMesFinal" size="5" maxlength="4"></html:text></td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td colspan="2">
						<table width="100%" border="0">
							<tr>
								<td colspan="2"><strong>Consumo Médio do Imóvel:</strong></td>
								<td align="right">
									<logic:equal name="tipoOrdem" value="INSTALACAO_HIDROMETRO">
										<html:button styleClass="bottonRightCol" value="Adicionar" property="botaoAdicionar" onclick="javascript:atualizarForm();abrirPopupComSubmit('exibirInserirConsumoMedioImovelPopupAction.do', 170, 575, 'ConsumoMedioImovelPopup');" disabled="true"/>
									</logic:equal>
									<logic:notEqual name="tipoOrdem" value="INSTALACAO_HIDROMETRO">
										<html:button styleClass="bottonRightCol" value="Adicionar" property="botaoAdicionar" onclick="javascript:atualizarForm();abrirPopupComSubmit('exibirInserirConsumoMedioImovelPopupAction.do', 170, 575, 'ConsumoMedioImovelPopup');"/>
									</logic:notEqual>
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td colspan="2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td colspan="3">
									<table width="100%" bgcolor="#90c7fc">
										<tr bordercolor="#FFFFFF" bgcolor="#90c7fc">
											<logic:notEqual name="tipoOrdem" value="INSTALACAO_HIDROMETRO">
												<td width="10%"><div align="center"><strong>Remover</strong></div></td>
											</logic:notEqual>
											<td width="45%" align="center"><strong>De</strong></td>
											<td width="45%" align="center"><strong>Até</strong></td>
										</tr>
									</table>
								</td>
							</tr>

							<tr>
								<td height="83px" colspan="3">
									<div style="width: 100%; height: 100%; overflow: auto;">
										<table width="100%" align="center" bgcolor="#99CCFF">
											<logic:present property="colecaoConsumoMedioImovel" name="ImovelEmissaoOrdensSeletivasActionForm" scope="session">
												<%int cont=0;%>
												<logic:iterate property="colecaoConsumoMedioImovel" name="ImovelEmissaoOrdensSeletivasActionForm" id="consumoMedioImovelHelper" type="ConsumoMedioImovelHelper" scope="session">
													<%cont = cont+1;
													if (cont%2==0) {%>
													<tr bgcolor="#cbe5fe">
													<%}else{%>
													<tr bgcolor="#FFFFFF">
													<%}%>
														<logic:notEqual name="tipoOrdem" value="INSTALACAO_HIDROMETRO">
															<td width="10%">
																<div align="center">
																	<a href="javascript:removerConsumoMedioImovel('removerConsumoMedioImovelAction.do?id=<%=""+consumoMedioImovelHelper.getId()%>');"><img border="0" src="/gsan/imagens/Error.gif"/></a>
																</div>
															</td>
														</logic:notEqual>
														<td width="45%">
															<div align="center">
																<bean:write name="consumoMedioImovelHelper" property="consumoMedioInicial"/>
															</div>
														</td>
														<td width="45%">
															<div align="center">
																<bean:write name="consumoMedioImovelHelper" property="consumoMedioFinal"/>
															</div>
														</td>
													</tr>
												</logic:iterate>
											</logic:present>
										</table>
									</div>
								</td>
							</tr>						
						</table>
					</td>
				</tr>

				<tr><td colspan="2"><hr></td></tr>

				<tr>
					<td colspan="2">
						<table border="0">
							<tr>
								<td width="175"><strong>Intervalo de quantidade de Débito:</strong></td>
								<td align="left"><html:text property="intervaloQuantidadeContasVencidasInicial"
									size="5" maxlength="4" onkeyup="duplicarIntervaloQuantidadeContasVencidas();"></html:text></td>
								<td>&nbsp;a&nbsp;</td>
								<td align="left"><html:text property="intervaloQuantidadeContasVencidasFinal" size="5" maxlength="4"></html:text></td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td colspan="2">
						<table border="0">
							<tr>
								<td width="175"><strong>Valor do Débito:</strong></td>
								<td align="left"><html:text property="valorTotalDebitoVencido"
									size="14" maxlength="14" onkeyup="javascript:formataValorDecimal(this, 2, event);" onblur="javascript:formataValorDecimal(this, 2, null);"></html:text></td>
							</tr>
						</table>
					</td>
				</tr>

				<tr id="Medicao">
					<td><strong>Tipo de Medi&ccedil;&atilde;o:</strong></td>
					<td align="right">
						<div align="left">
							<strong>
								<html:select property="tipoMedicao" style="width: 180px;">
									<logic:present name="collectionTipoMedicao">
										<html:options collection="collectionTipoMedicao"
											labelProperty="descricao" property="id" />
									</logic:present>
								</html:select>
							</strong>
						</div>
						<html:hidden property="tipoMedicaoDescricao"/>
					</td>
				</tr>
				
				<tr>
					<td>&nbsp;</td>
					<td align="right">&nbsp;</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<div align="right">
							<jsp:include page="/jsp/util/wizard/navegacao_botoes_wizard_valida_avancar_valida_voltar.jsp?numeroPagina=3" />
						</div>
					</td>
				</tr>
			</table>
		</tr>
	</table>
	<%@ include file="/jsp/util/rodape.jsp"%>
	
	<logic:present name="servicoTipo">
		<logic:equal name="servicoTipo" value="INSTALACAO">
			<script>document.getElementById('2').style.display = 'none';</script>
		</logic:equal>
		<logic:notEqual name="servicoTipo" value="INSTALACAO">
			<script>document.getElementById('2').style.display = '';</script>
		</logic:notEqual>
	</logic:present>
	<logic:notPresent name="servicoTipo">
		<script>document.getElementById('2').style.display = 'none';</script>
	</logic:notPresent>
	<script>document.getElementById('Medicao').style.display = 'none';</script>
</html:form>
</body>

</html:html>
