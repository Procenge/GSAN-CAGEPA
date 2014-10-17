<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

<%@ page import="gcom.util.ConstantesSistema" %>
<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro" %>
<%@ page import="gcom.micromedicao.hidrometro.Hidrometro"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="HidrometroActionForm" dynamicJavascript="false" />

<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_ADA%>" scope="session">
<script language="JavaScript">
	function FloatValidations () {
	}

	function required () {
		this.aa = new Array("dataAquisicao", "Informe Data de Aquisição.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
		this.ab = new Array("anoFabricacao", "Informe Ano de Fabricação.", new Function ("varName", " return this[varName];"));
		this.ac = new Array("idHidrometroClasseMetrologica", "Informe Classe Metrológica.", new Function ("varName", " return this[varName];"));
		this.ad = new Array("idHidrometroMarca", "Informe Marca.", new Function ("varName", " return this[varName];"));
		this.ae = new Array("idHidrometroDiametro", "Informe Diâmetro.", new Function ("varName", " return this[varName];"));
		this.af = new Array("idHidrometroCapacidade", "Informe Capacidade.", new Function ("varName", " return this[varName];"));
		this.ag = new Array("idNumeroDigitosLeitura", "Informe Número de Digitos.", new Function ("varName", " return this[varName];"));
		this.ah = new Array("idHidrometroTipo", "Informe Tipo.", new Function ("varName", " return this[varName];"));
		this.ai = new Array("idLocalArmazenagem", "Informe Local de Armazenagem.", new Function ("varName", " return this[varName];"));
		this.aj = new Array("fixo", "Informe Fixo da Numeração dos Hidrômetros.", new Function ("varName", " return this[varName];"));
		this.ak = new Array("faixaInicial", "Informe Faixa Inicial da Numeração dos Hidrômetros.", new Function ("varName", " return this[varName];"));
		this.al = new Array("faixaFinal", "Informe Faixa Final da Numeração dos Hidrômetros.", new Function ("varName", " return this[varName];"));
		this.am = new Array("codigoFormatoNumeracao", "Informe Formato da Numeração do Hidrômetro.", new Function ("varName", " return this[varName];"));
	}
</script>
</logic:equal>

<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
<script language="JavaScript">
	function FloatValidations () {
		this.aa = new Array("fatorConversao", "Fator de Conversão deve somente conter números decimais positivos.", new Function ("varName", " return this[varName];"));
    }

    function required () {
     this.aa = new Array("dataAquisicao", "Informe Data de Aquisição.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
     this.ab = new Array("anoFabricacao", "Informe Ano de Fabricação.", new Function ("varName", " return this[varName];"));
     this.ac = new Array("idHidrometroClasseMetrologica", "Informe Classe Metrológica.", new Function ("varName", " return this[varName];"));
     this.ad = new Array("idHidrometroMarca", "Informe Marca.", new Function ("varName", " return this[varName];"));
     this.ae = new Array("idHidrometroDiametro", "Informe Diâmetro.", new Function ("varName", " return this[varName];"));
     this.af = new Array("idHidrometroCapacidade", "Informe Capacidade.", new Function ("varName", " return this[varName];"));
     this.ag = new Array("idNumeroDigitosLeitura", "Informe Número de Digitos.", new Function ("varName", " return this[varName];"));
     this.ah = new Array("idHidrometroTipo", "Informe Tipo.", new Function ("varName", " return this[varName];"));
     this.ai = new Array("idLocalArmazenagem", "Informe Local de Armazenagem.", new Function ("varName", " return this[varName];"));
     this.aj = new Array("fixo", "Informe Fixo da Numeração dos Hidrômetros.", new Function ("varName", " return this[varName];"));
     this.ak = new Array("faixaInicial", "Informe Faixa Inicial da Numeração dos Hidrômetros.", new Function ("varName", " return this[varName];"));
     this.al = new Array("faixaFinal", "Informe Faixa Final da Numeração dos Hidrômetros.", new Function ("varName", " return this[varName];"));
     this.am = new Array("fatorConversao", "Informe Fator de Conversão.", new Function ("varName", " return this[varName];"));
     this.an = new Array("codigoFormatoNumeracao", "Informe Formato da Numeração do Hidrômetro.", new Function ("varName", " return this[varName];"));
	}
</script>
</logic:equal>

<script language="JavaScript">
<!-- Begin
	var bCancel = false;

	function validateHidrometroActionForm(form){
	    if (bCancel)
	  		return true;
	    else
	       	return validateRequired(form)
	       			&& validarIndicadorMacromedidor()
	       			&& validateCaracterEspecial(form)
	       			&& validateDate(form)
	       			&& validateLong(form)
	       			&& validateDecimal(form)
	       			&& validateDecimalZeroPositivo(form)
	       			&& testarCampoValorZero(form.fixo, 'Fixo da Numeração dos Hidrômetros')
	       			&& testarCampoValorZero(form.anoFabricacao, 'Data Fabricação')
	       			&& validarHidrometroTipoTurbina(form);
    }

    function DateValidations(){
		this.aa = new Array("dataAquisicao", "Data de Aquisição inválida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
    }

    function caracteresespeciais(){
		this.aa = new Array("dataAquisicao", "Data de Aquisição possui caracteres especiais.", new Function ("varName", " return this[varName];"));
		this.ab = new Array("anoFabricacao", "Ano de Fabricação possui caracteres especiais.", new Function ("varName", " return this[varName];"));
		this.ac = new Array("idLocalArmazenagem", "Local de Armazenagem possui caracteres especiais.", new Function ("varName", " return this[varName];"));
		this.ad = new Array("fixo", "Fixo da Numeração dos Hidrômetros possui caracteres especiais.", new Function ("varName", " return this[varName];"));
		this.ae = new Array("faixaInicial", "Faixa Inicial da Numeração dos Hidrômetros possui caracteres especiais.", new Function ("varName", " return this[varName];"));
		this.af = new Array("faixaFinal", "Faixa Final da Numeração dos Hidrômetros possui caracteres especiais.", new Function ("varName", " return this[varName];"));
    }

    function IntegerValidations(){
		this.aa = new Array("anoFabricacao", "Ano de Fabricação deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
		this.ab = new Array("faixaInicial", "Faixa Inicial da Numeração dos Hidrômetros deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
		this.ac = new Array("faixaFinal", "Faixa Final da Numeração dos Hidrômetros deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    }

    function DecimalZeroPositivoValidations(){
		this.aa = new Array("idLocalArmazenagem", "Local de Armazenagem deve somente conter números.", new Function ("varName", " return this[varName];"));
    }

    function limparLocalArmazenagem(){
		var form = document.HidrometroActionForm;
		form.idLocalArmazenagem.value = '';
		form.localArmazenagemDescricao.value = '';
    }

	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta){
	    var form = document.HidrometroActionForm;

	    if(tipoConsulta == 'hidrometroLocalArmazenagem'){
    		form.idLocalArmazenagem.value = codigoRegistro;
      		form.localArmazenagemDescricao.value = descricaoRegistro;
      		form.localArmazenagemDescricao.style.color = "#000000";
		}
	}

	// Validacao Adicionada por Romulo Aurelio 24/05/2007 
	// [FS0007]-Montar ano de fabricacao
	function validarAnoFabricacao(){
		var form = document.forms[0];
		
		var dataAtual = new Date();
		var anoDataAtual = dataAtual.getFullYear();
		var anoFabricacao = trim(form.anoFabricacao.value);
		var fixo = trim(form.fixo.value);
		var anoAtualCompleto = ''+ anoDataAtual;
		var anoDataAtual = parseInt(anoAtualCompleto.substring(2,4));
		var codigoFormatoNumeracao;

		for(var indice = 0; indice < form.elements.length; indice++){
			if(form.elements[indice].type == 'radio' && form.elements[indice].checked == true && form.elements[indice].name == "codigoFormatoNumeracao"){
				codigoFormatoNumeracao = form.elements[indice].value;
			}
		}

		if(fixo != ''){
			if(((codigoFormatoNumeracao == <%=Hidrometro.FORMATO_NUMERACAO_4_X_6.toString()%> && fixo.length == 4) || 
				(codigoFormatoNumeracao == <%=Hidrometro.FORMATO_NUMERACAO_5_X_5.toString()%> && fixo.length == 5))
			   &&!isNaN(fixo.substring(1,3))){

				var ano = parseInt(fixo.substring(1,3));

				if(fixo.substring(1,2) == '0'){
					ano = parseInt(fixo.substring(2,3));
				}

				if(!((fixo.substring(1,2)== '0' || fixo.substring(1,2)== '1' ||	fixo.substring(1,2)== '2' ||
					  fixo.substring(1,2)== '3' || fixo.substring(1,2)== '4' || fixo.substring(1,2)== '5' ||
					  fixo.substring(1,2)== '6' || fixo.substring(1,2)== '7' || fixo.substring(1,2)== '8' ||
					  fixo.substring(1,2)== '9')&& 
					 (fixo.substring(2,3)== '0' || fixo.substring(2,3)== '1' || fixo.substring(2,3)== '2' ||
					  fixo.substring(2,3)== '3' || fixo.substring(2,3)== '4' || fixo.substring(2,3)== '5' ||
					   fixo.substring(2,3)== '6' ||fixo.substring(2,3)== '7' ||	fixo.substring(2,3)== '8' ||
					   fixo.substring(2,3)== '9'))){

					form.anoFabricacao.value = ' ';
					alert('Informe ano numérico.');
					form.anoFabricacao.value = ' ';
				}else{
					if(ano<60){
						form.anoFabricacao.value = 2000 + ano; 
					}else{
						form.anoFabricacao.value = 1900 + ano; 
					}

					if(ano >= 85){
						form.anoFabricacao.value = 1900 + ano; 
					}

					if(ano >= 00 &&  ano <= anoDataAtual){
						form.anoFabricacao.value = 2000 + ano; 
					}

					if(anoDataAtual < ano && form.anoFabricacao.value > parseInt(anoAtualCompleto)){
						form.anoFabricacao.value = '';
						alert('Ano de fabricação inválido.');
					}else if(form.anoFabricacao.value  < 1985) {
						form.anoFabricacao.value = '';
						alert('Ano de fabicação deve ser igual ou superior a 1985.');
					}
				}
			}else{
				alert("Número do hidrômetro inválido.");
				form.fixo.value = "";			
				form.fixo.focus();
			} 
		}
	}

	function limparAnoFabricacao(){
		var form = document.forms[0];
		form.anoFabricacao.value = '';
	} 

	function validarForm(form){
    	if(validateHidrometroActionForm(form)){
			var fixo = form.fixo.value;
			var faixaFinal = form.faixaFinal.value;
			var faixaInicial =  form.faixaInicial.value;
			var intervalo = (faixaFinal - faixaInicial) + 1;
			var fixo =  trim(form.fixo.value);
			var anoFabricacao = trim(form.anoFabricacao.value);
			var mensagem = 'deve ser diferente de zero.'
			var flag = true;

			var codigoFormatoNumeracao;

			for(var indice = 0; indice < form.elements.length; indice++){
				if (form.elements[indice].type == 'radio' && form.elements[indice].checked == true && form.elements[indice].name == "codigoFormatoNumeracao"){
					codigoFormatoNumeracao = form.elements[indice].value;
				}
			}

			if((faixaInicial > 0) && (faixaFinal > 0)){
				if(intervalo > 0){
					if(codigoFormatoNumeracao == <%=Hidrometro.FORMATO_NUMERACAO_4_X_6.toString()%> && fixo.length < 4){
						alert("Fixo da Numeração dos Hidrômetros deve conter no mínimo 4 caracteres.");
					}if(codigoFormatoNumeracao == <%=Hidrometro.FORMATO_NUMERACAO_5_X_5.toString()%> && fixo.length < 5){
						alert("Fixo da Numeração dos Hidrômetros deve conter no mínimo 5 caracteres.");
					}else{
						if(anoFabricacao == 0){
							alert('Ano de Fabricação '+ mensagem);
							flag = false;
						} 

						if(fixo == 0){
							alert('Fixo da Numeração dos Hidrômetros ' + mensagem);
							flag = false;
						}
						
						if(trim(form.fixo.value)== ''){
							alert("Número do hidrômetro não pode ter espaços em branco.");
							form.fixo.value = "";
							flag = false;
						}
							
						if(flag == true){
							submeterFormPadrao(form);
						}
					}
				}else{
					alert("Faixa Final da Numeração dos Hidrômetros deve ser maior ou igual a Faixa Inicial.");
				}
			}else{
				alert("Faixa Inicial e Faixa Final da Numeração dos Hidrômetros devem somente conter números positivos.");
			}
		}
	}

	function validarIndicadorMacromedidor(){
	    var form = document.forms[0];
	    var retorno = true;
	    
	    var indice;
	    var array = new Array(form.indicadorMacromedidor.length);
	    var selecionado = "";
	    var formulario = document.forms[0]; 
	    for(indice = 0; indice < form.elements.length; indice++){
		   if (form.elements[indice].type == 'radio' && form.elements[indice].checked == true && formulario.elements[indice].name == "indicadorMacromedidor") {
		      selecionado = form.elements[indice].value;
		      indice = form.elements.length;
		   }
	    }    
		if(selecionado == ''){
			alert('Informe Finalidade.');
			indicadorMacromedidor.focus();
			retorno = false;
		}	

		return retorno;
	}	

	function carregarCapacidadeHidrometroMarca(){
		var form = document.forms[0];
		var fixo = trim(form.fixo.value);
		if (fixo != null && fixo != ''){
			form.action = 'exibirInserirHidrometroAction.do?objetoConsulta=2';
			form.submit();
		}	
	}

	function atualizarSelecaoFormato(){
		var form = document.forms[0];
		var codigoFormatoNumeracao;

		for(var indice = 0; indice < form.elements.length; indice++){
			if (form.elements[indice].type == 'radio' && form.elements[indice].checked == true && form.elements[indice].name == "codigoFormatoNumeracao"){
				codigoFormatoNumeracao = form.elements[indice].value;
			}
		}

		form.action = "/gsan/exibirInserirHidrometroAction.do?codigoFormatoNumeracao="+ codigoFormatoNumeracao + "&limpaSessao=1";
		submeterFormPadrao(form);
	}

	function validarHidrometroTipoTurbina(){
		var retorno = true;
		var form = document.forms[0];
		var codigoFormatoNumeracao;

		for(var indice = 0; indice < form.elements.length; indice++){
			if (form.elements[indice].type == 'radio' && form.elements[indice].checked == true && form.elements[indice].name == "codigoFormatoNumeracao"){
				codigoFormatoNumeracao = form.elements[indice].value;
			}
		}

		if (codigoFormatoNumeracao == <%=Hidrometro.FORMATO_NUMERACAO_5_X_5.toString()%>
				&& form.idHidrometroTipoTurbina.value == <%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>){
			alert('Informe Tipo de Instalação da Turbina.');
			form.idHidrometroTipoTurbina.focus();
			retorno = false;
		}

		return retorno;
	}
//End -->

</script>


</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('${requestScope.nomeCampo}');">
<html:form action="/inserirHidrometroAction.do"
	name="HidrometroActionForm"
	type="gcom.gui.micromedicao.hidrometro.HidrometroActionForm"
	method="post" onsubmit="return validateHidrometroActionForm(this);">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellpadding="0" cellspacing="5">
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
			<td valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img
						src="<bean:message key="caminho.imagens"/>parahead_left.gif"
						border="0" /></td>
					<td class="parabg">Inserir Hidr&ocirc;metro</td>
					<td width="11" valign="top"><img
						src="<bean:message key="caminho.imagens"/>parahead_right.gif"
						border="0" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td>Para adicionar o(s) hidr&ocirc;metros(s), informe os dados
					abaixo:</td>
					<td align="right"></td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td><strong>Formato da Numeração do Hidrômetro:<font color="#FF0000">*</font></strong></td>
					<td>
				  		<html:radio property="codigoFormatoNumeracao" value="<%=Hidrometro.FORMATO_NUMERACAO_4_X_6.toString()%>" onclick="atualizarSelecaoFormato();"/><strong>4x6
						<html:radio property="codigoFormatoNumeracao" value="<%=Hidrometro.FORMATO_NUMERACAO_5_X_5.toString()%>" onclick="atualizarSelecaoFormato();"/>5x5</strong>
					</td>
				</tr>

				<tr>
					<td colspan="2"><strong>Numeração dos Hidrômetros</strong></td>
				</tr>
				<tr>
					<td><strong>Fixo:<font color="#FF0000">*</font></strong></td>
					<logic:equal name="HidrometroActionForm" property="codigoFormatoNumeracao" value="<%=Hidrometro.FORMATO_NUMERACAO_4_X_6.toString()%>">
						<td>
							<html:text maxlength="4" property="fixo" size="4" tabindex="1" onkeyup= "javascript:limparAnoFabricacao();"
								onblur="javascript:validarAnoFabricacao();carregarCapacidadeHidrometroMarca();"/>
						</td>
					</logic:equal>
					<logic:equal name="HidrometroActionForm" property="codigoFormatoNumeracao" value="<%=Hidrometro.FORMATO_NUMERACAO_5_X_5.toString()%>">
						<td>
							<html:text maxlength="5" property="fixo" size="5" tabindex="1" onkeyup= "javascript:limparAnoFabricacao();"
								onblur="javascript:validarAnoFabricacao();carregarCapacidadeHidrometroMarca();"/>
						</td>
					</logic:equal>
				</tr>
				<tr>
					<td><strong>Faixa:<font color="#FF0000">*</font></strong></td>
					<logic:equal name="HidrometroActionForm" property="codigoFormatoNumeracao" value="<%=Hidrometro.FORMATO_NUMERACAO_4_X_6.toString()%>">
						<td>
							<html:text maxlength="6" property="faixaInicial" size="6" tabindex="2"/>
							<html:text maxlength="6" property="faixaFinal" size="6" tabindex="3"/>
						</td>
					</logic:equal>
					<logic:equal name="HidrometroActionForm" property="codigoFormatoNumeracao" value="<%=Hidrometro.FORMATO_NUMERACAO_5_X_5.toString()%>">
						<td>
							<html:text maxlength="5" property="faixaInicial" size="5" tabindex="2"/>
							<html:text maxlength="5" property="faixaFinal" size="5" tabindex="3"/>
						</td>
					</logic:equal>
				</tr>

				<tr>
					<td><strong>Capacidade:<font color="#FF0000">*</font></strong></td>
					<td><html:select property="idHidrometroCapacidade" tabindex="4"> <!-- onchange="redirecionarSubmit('exibirInserirHidrometroAction.do');" -->
						<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="colecaoHidrometroCapacidade"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>

				<tr>
					<td><strong>Ano de Fabrica&ccedil;&atilde;o:<font color="#FF0000">*</font></strong>
					</td>
					<td><html:text maxlength="4" property="anoFabricacao" size="4" readonly="true"
						style="background-color:#EFEFEF; border:0;"/>&nbsp;aaaa</td>
				</tr>

				<tr>
					<td><strong>Marca:<font color="#FF0000">*</font></strong></td>
					<td><html:select property="idHidrometroMarca" tabindex="5">
						<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="colecaoHidrometroMarca"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>

				<logic:equal name="HidrometroActionForm" property="codigoFormatoNumeracao" value="<%=Hidrometro.FORMATO_NUMERACAO_5_X_5.toString()%>">
				<tr>
					<td><strong>Tipo de Instalação da Turbina:<font color="#FF0000">*</font></strong></td>
					<td>
						<html:select property="idHidrometroTipoTurbina">
							<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<html:options collection="colecaoHidrometroTipoTurbina" labelProperty="descricao" property="id" />
						</html:select>
					</td>
				</tr>
				</logic:equal>

				<tr>
					<td height="24" colspan="2" class="style1">
					<hr>
					</td>
				</tr>

				<tr>
					<td><strong>Data de Aquisi&ccedil;&atilde;o:<font color="#FF0000">*</font></strong>
					</td>
					<td><html:text property="dataAquisicao" size="10" maxlength="10"
						tabindex="6" onkeyup="mascaraData(this,event)" /> <a
						href="javascript:abrirCalendario('HidrometroActionForm', 'dataAquisicao')">
					<img border="0"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
					dd/mm/aaaa</td>
				</tr>

				<tr>
					<td><strong>Finalidade:<font color="#FF0000">*</font></strong></td>
					<td><strong> <span class="style1"><strong> <html:radio
						property="indicadorMacromedidor"
						value="<%=(Hidrometro.INDICADOR_COMERCIAL).toString()%>"
						tabindex="7" /> <strong>Comercial <html:radio
						property="indicadorMacromedidor"
						value="<%=(Hidrometro.INDICADOR_OPERACIONAL).toString()%>" />
					Operacional </strong></strong></span></strong></td>
				</tr>
				<tr>
					<td><strong>Classe Metrológica:<font color="#FF0000">*</font></strong></td>
					<td><html:select property="idHidrometroClasseMetrologica"
						tabindex="8">
						<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="colecaoHidrometroClasseMetrologica"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>

				<tr>
					<td><strong>Diâmetro:<font color="#FF0000">*</font></strong></td>
					<td><html:select property="idHidrometroDiametro" tabindex="9">
						<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="colecaoHidrometroDiametro"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>


				<tr>
					<td><strong>Número de Digitos:<font color="#FF0000">*</font></strong></td>
					<td><html:select name="HidrometroActionForm"
						property="idNumeroDigitosLeitura" tabindex="10">
						<html:option value="">&nbsp;</html:option>
						<logic:present name="colecaoIntervalo">
							<c:forEach items="${sessionScope.colecaoIntervalo}"
								var="numeroDigitosLeitura">
								<html:option value="${numeroDigitosLeitura}">${numeroDigitosLeitura}</html:option>
							</c:forEach>
						</logic:present>
					</html:select></td>
				</tr>
				<%--
				<tr>
					<td><strong>Número de Digitos:<font color="#FF0000">*</font></strong></td>
					<td>
						<logic:present name="colecaoIntervalo">
							<html:select name="idNumeroDigitosLeitura" tabindex="9">
								<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
								<html:options collection="colecaoIntervalo" labelProperty="idNumeroDigitosLeitura" property="id" />
							</html:select>
						</logic:present> 
						<logic:notPresent name="colecaoIntervalo">
							<select name=idNumeroDigitosLeitura tabindex="9">
								<option>&nbsp;</option>
							</select>
						</logic:notPresent>
					</td>
				</tr>
				--%>
				<tr>
					<td><strong>Tipo:<font color="#FF0000">*</font></strong></td>
					<td><html:select property="idHidrometroTipo" tabindex="11">
						<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="colecaoHidrometroTipo"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>

				<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
					<tr>
						<td><strong>Hidrômetro Composto:</strong></td>
						<td><html:checkbox property="indicadorHidrometroComposto" tabindex="12"></html:checkbox></td>
					</tr>
					<tr>
					    <td><strong>Fator de Conversão:<font color="#FF0000">*</font></strong></td>
					    <td><html:text property="fatorConversao" size="7" maxlength="7" onkeyup="javascript:formataValorDecimalTresCasas(this,7)" tabindex="13"/></td>
					</tr>
				</logic:equal>

				<tr>
					<td><strong>Local de Armazenagem:<font color="#FF0000">*</font></strong></td>
					<td><html:text property="idLocalArmazenagem" size="5" maxlength="3"
						tabindex="14"
						onkeypress="validaEnterComMensagemAceitaZERO(event, 'exibirInserirHidrometroAction.do?objetoConsulta=1', 'idLocalArmazenagem', 'Local de Armazenagem');" />
					<a
						href="javascript:abrirPopup('exibirPesquisarTabelaAuxiliarAbreviadaAction.do?tela=hidrometroLocalArmazenagem', 250, 495);">
					<img src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						border="0" width="23" height="21" title="Pesquisar"></a> <logic:present
						name="corLocalArmazenagem">
						<logic:equal name="corLocalArmazenagem" value="exception">
							<html:text property="localArmazenagemDescricao" size="35"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>
						<logic:notEqual name="corLocalArmazenagem" value="exception">
							<html:text property="localArmazenagemDescricao" size="35"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> <logic:notPresent name="corLocalArmazenagem">
						<logic:empty name="HidrometroActionForm"
							property="idLocalArmazenagem">
							<html:text property="localArmazenagemDescricao" size="35"
								value="" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="HidrometroActionForm"
							property="idLocalArmazenagem">
							<html:text property="localArmazenagemDescricao" size="35"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
					</logic:notPresent> <a href="javascript:limparLocalArmazenagem();">
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /> </a></td>
				</tr>

				<tr>
					<td>&nbsp;</td>
					<td align="left"><font color="#FF0000">*</font> Campo Obrigatório</td>
				</tr>
				<tr>
					<td><input type="button" name="Button" class="bottonRightCol"
						value="Desfazer" tabindex="15"
						onClick="javascript:window.location.href='/gsan/exibirInserirHidrometroAction.do?limpaSessao=1'"
						style="width: 80px" /> &nbsp; <input type="button" name="Button"
						class="bottonRightCol" value="Cancelar" tabindex="16"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
						style="width: 80px" /></td>
					<td align="right"><gcom:controleAcessoBotao name="Button"
						value="Inserir"
						onclick="javascript:validarForm(document.HidrometroActionForm);"
						url="inserirHidrometroAction.do" /> <!-- 					<input type="button" class="bottonRightCol" value="Inserir" tabindex="17" onclick="validarForm(document.HidrometroActionForm);"> -->
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
