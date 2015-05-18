<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

<%@ page import="gcom.util.ConstantesSistema"%>
<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro"%>
<%@ page import="gcom.micromedicao.hidrometro.HidrometroSituacao"%>
<%@ page import="gcom.micromedicao.hidrometro.Hidrometro"
	isELIgnored="false"%>
<%@ page import="gcom.micromedicao.bean.OrdemServicoManutencaoHidrometroHelper"%>

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
	formName="AtualizarHidrometroActionForm" dynamicJavascript="false" />

<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>"
	property="parmId"
	value="<%="" + SistemaParametro.INDICADOR_EMPRESA_ADA%>"
	scope="session">
	<script language="JavaScript">
	function required () {
		this.aa = new Array("numeroHidrometro", "Informe Número do Hidrômetro.", new Function ("varName", " return this[varName];"));
		this.ab = new Array("dataAquisicao", "Informe Data de Aquisição.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
		this.ac = new Array("anoFabricacao", "Informe Ano de Fabricação.", new Function ("varName", " return this[varName];"));
		this.ad = new Array("idHidrometroClasseMetrologica", "Informe Classe Metrológica.", new Function ("varName", " return this[varName];"));
		this.ae = new Array("idHidrometroMarca", "Informe Marca.", new Function ("varName", " return this[varName];"));
		this.af = new Array("idHidrometroDiametro", "Informe Diâmetro.", new Function ("varName", " return this[varName];"));
		this.ag = new Array("idHidrometroCapacidade", "Informe Capacidade.", new Function ("varName", " return this[varName];"));
		this.ah = new Array("idNumeroDigitosLeitura", "Informe Número de Digitos.", new Function ("varName", " return this[varName];"));
		this.ai = new Array("idHidrometroTipo", "Informe Tipo.", new Function ("varName", " return this[varName];"));
		this.aj = new Array("codigoFormatoNumeracao", "Informe Formato da Numeração do Hidrômetro.", new Function ("varName", " return this[varName];"));
		this.ak = new Array("idHidrometroSituacao", "Informe Situação.", new Function ("varName", " return this[varName];"));
	}	

	function FloatValidations(){
	}
</script>
</logic:equal>

<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>"
	property="parmId"
	value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>"
	scope="session">
	<script language="JavaScript">
    function required(){
	    this.aa = new Array("numeroHidrometro", "Informe Número do Hidrômetro.", new Function ("varName", " return this[varName];"));
	    this.ab = new Array("dataAquisicao", "Informe Data de Aquisição.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
	    this.ac = new Array("anoFabricacao", "Informe Ano de Fabricação.", new Function ("varName", " return this[varName];"));
	    this.ad = new Array("idHidrometroClasseMetrologica", "Informe Classe Metrológica.", new Function ("varName", " return this[varName];"));
	    this.ae = new Array("idHidrometroMarca", "Informe Marca.", new Function ("varName", " return this[varName];"));
	    this.af = new Array("idHidrometroDiametro", "Informe Diâmetro.", new Function ("varName", " return this[varName];"));
	    this.ag = new Array("idHidrometroCapacidade", "Informe Capacidade.", new Function ("varName", " return this[varName];"));
	    this.ah = new Array("idNumeroDigitosLeitura", "Informe Número de Digitos.", new Function ("varName", " return this[varName];"));
	    this.ai = new Array("idHidrometroTipo", "Informe Tipo.", new Function ("varName", " return this[varName];"));
		this.aj = new Array("fatorConversao", "Informe Fator de Conversão.", new Function ("varName", " return this[varName];"));
		this.ak = new Array("codigoFormatoNumeracao", "Informe Formato da Numeração do Hidrômetro.", new Function ("varName", " return this[varName];"));
		this.al = new Array("idHidrometroSituacao", "Informe Situação.", new Function ("varName", " return this[varName];"));
	}

	function FloatValidations(){
		this.aa = new Array("fatorConversao", "Fator de Conversão deve somente conter números decimais positivos.", new Function ("varName", " return this[varName];"));
	}
</script>
</logic:equal>

<script language="JavaScript"><!-- Begin
 	var bCancel = false;

    function validateAtualizarHidrometroActionForm(form){
        if(bCancel){
      		return true;
      	}else{
       		return validateRequired(form)
       			&& validateCaracterEspecial(form)
       			&& validateDate(form)
       			&& validateLong(form)
       			&& validateDecimal(form)
       			&& validarHidrometroTipoTurbina(form);
       	}
    }

    function DateValidations(){
    	this.aa = new Array("dataAquisicao", "Data de Aquisição inválida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
    }

    function caracteresespeciais(){
    	this.aa = new Array("numeroHidrometro", "Número do Hidrômetro possui caracteres especiais.", new Function ("varName", " return this[varName];"));
    	this.ab = new Array("dataAquisicao", "Data de Aquisição possui caracteres especiais.", new Function ("varName", " return this[varName];"));
    	this.ac = new Array("anoFabricacao", "Ano de Fabricação possui caracteres especiais.", new Function ("varName", " return this[varName];"));
    	this.ad = new Array("numeroNotaFiscal", "Número da Nota Fiscal possui caracteres especiais.", new Function ("varName", " return this[varName];"));
    }

    function IntegerValidations(){
    	this.aa = new Array("anoFabricacao", "Ano de Fabricação deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    	this.ab = new Array("numeroNotaFiscal", "Número da Nota Fiscal deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    }

	function validarForm(form){

		if (validateAtualizarHidrometroActionForm(form) && testarCampoValorZero(form.numeroHidrometro, 'Número Hidrômetro') 
				&& testarCampoValorZero(form.anoFabricacao, 'Data Fabricação')){

			submeterFormPadrao(form);
		}
	}

	//Validacao Adicionada por Romulo Aurelio 24/05/2007 
	//[FS0007]-Montar ano de fabricacao
	function validarAnoFabricacao(){
		var form = document.forms[0];
		var codigoFormatoNumeracao;

		for(var indice = 0; indice < form.elements.length; indice++){
			if (form.elements[indice].type == 'radio' && form.elements[indice].checked == true && form.elements[indice].name == "codigoFormatoNumeracao"){
				codigoFormatoNumeracao = form.elements[indice].value;
			}
		}

		if (codigoFormatoNumeracao == <%=Hidrometro.FORMATO_NUMERACAO_4_X_6.toString()%> ||
			codigoFormatoNumeracao == <%=Hidrometro.FORMATO_NUMERACAO_5_X_5.toString()%>){

			var dataAtual = new Date();
			var anoDataAtual = dataAtual.getFullYear();
			var anoFabricacao = trim(form.anoFabricacao.value);
			var numeroHidrometro = trim(form.numeroHidrometro.value);
			var anoAtualCompleto = ''+ anoDataAtual;
			var anoDataAtualFinal = parseInt(anoAtualCompleto.substring(2,4));

			var ano = parseInt(numeroHidrometro.substring(1,3));
			if(numeroHidrometro.substring(1,2) == '0'){
				ano = parseInt(numeroHidrometro.substring(2,3));
			}

			if(!((numeroHidrometro.substring(1,2)== '0' || numeroHidrometro.substring(1,2)== '1' ||
				numeroHidrometro.substring(1,2)== '2' ||numeroHidrometro.substring(1,2)== '3' ||
				numeroHidrometro.substring(1,2)== '4' || numeroHidrometro.substring(1,2)== '5' ||
				numeroHidrometro.substring(1,2)== '6' ||numeroHidrometro.substring(1,2)== '7' ||
				numeroHidrometro.substring(1,2)== '8' ||numeroHidrometro.substring(1,2)== '9')&& 
				(numeroHidrometro.substring(2,3)=='0' || numeroHidrometro.substring(2,3)== '1' ||
				numeroHidrometro.substring(2,3)== '2' ||numeroHidrometro.substring(2,3)== '3' ||
				numeroHidrometro.substring(2,3)== '4' || numeroHidrometro.substring(2,3)== '5' ||
				numeroHidrometro.substring(2,3)== '6' ||numeroHidrometro.substring(2,3)== '7' ||
				numeroHidrometro.substring(2,3)== '8' ||numeroHidrometro.substring(2,3)== '9'))) {
			
				alert('Informe ano numérico');
				form.anoFabricacao.value = '';
			}else{
				if(ano<60){
					form.anoFabricacao.value = 2000 + ano; 
				}else{
					form.anoFabricacao.value = 1900 + ano; 
				}
	
				if(ano >= 85){
					form.anoFabricacao.value = 1900 + ano; 
				}
	
				if(ano >= 00 &&  ano <= anoDataAtualFinal){
					form.anoFabricacao.value = 2000 + ano; 
				}

				if(anoDataAtualFinal < ano && form.anoFabricacao.value > parseInt(anoAtualCompleto)){
					form.anoFabricacao.value = '';
					alert('Ano de fabricação inválido');
				}else if(form.anoFabricacao.value  < 1985){
					form.anoFabricacao.value = '';
					alert('Ano de fabicação de ser igual ou superior a 1985.');
				}
			}
		}
	}

	function limparAnoFabricacao(){
		var form = document.forms[0];
		var codigoFormatoNumeracao;

		for(var indice = 0; indice < form.elements.length; indice++){
			if (form.elements[indice].type == 'radio' && form.elements[indice].checked == true && form.elements[indice].name == "codigoFormatoNumeracao"){
				codigoFormatoNumeracao = form.elements[indice].value;
			}
		}

		if (codigoFormatoNumeracao == <%=Hidrometro.FORMATO_NUMERACAO_4_X_6.toString()%> ||
			codigoFormatoNumeracao == <%=Hidrometro.FORMATO_NUMERACAO_5_X_5.toString()%>){
			form.anoFabricacao.value = '';
		}
	} 

	function carregarCapacidadeHidrometroMarca(){
		var form = document.forms[0];
		if(form.numeroHidrometro != null && form.numeroHidrometro.value != ''){
			form.action = 'exibirAtualizarHidrometroAction.do';
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

		form.action = "/gsan/exibirAtualizarHidrometroAction.do?codigoFormatoNumeracao="+ codigoFormatoNumeracao + "&limpaSessao=1";
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
				&& form.idHidrometroTipoTurbina.value == <%="" + ConstantesSistema.NUMERO_NAO_INFORMADO%>){
			alert('Informe Tipo de Instalação da Turbina.');
			form.idHidrometroTipoTurbina.focus();
			retorno = false;
		}

		return retorno;
	}

	function voltarFiltrar(){

		var form = document.forms[0];
		location.href = '/gsan/exibirFiltrarHidrometroAction.do?menu=sim';
	}
	
	function consultarOs(idOs){
		var url = 'exibirConsultarDadosOrdemServicoPopupAction.do?numeroOS='+idOs;
		abrirPopup(url);
	}
//End -->
</script>


</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('${requestScope.nomeCampo}');">
<html:form action="/atualizarHidrometroAction.do"
	name="AtualizarHidrometroActionForm"
	type="gcom.gui.micromedicao.hidrometro.AtualizarHidrometroActionForm"
	method="post">

	<html:hidden property="idHidrometro" />
	

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellpadding="0" cellspacing="5">
		<tr>
			<td width="149" valign="top" class="leftcoltext">
			<div align="center"><%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> 
			<%@ include file="/jsp/util/informacoes_usuario.jsp"%> 
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> 
			<%@ include file="/jsp/util/mensagens.jsp"%> 
			<%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			</div>
			</td>
			<td valign="top" class="centercoltext">
			<table width="100%" height="100%">
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
					<td class="parabg">Atualizar Hidr&ocirc;metro</td>
					<td width="11" valign="top"><img
						src="<bean:message key="caminho.imagens"/>parahead_right.gif"
						border="0" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para atualizar o(s) hidr&ocirc;metros(s),
					informe os dados abaixo:</td>
					<td align="right"></td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td width="218"><strong>Matrícula do Imóvel:</strong></td>
					<td width="393"><html:text property="idImovel" size="10"
						maxlength="10" readonly="true"
						style="background-color:#EFEFEF; border:0;" /></td>
				</tr>
				<tr>
					<td><strong>Formato da Numeração do Hidrômetro:<font
						color="#FF0000">*</font></strong></td>
					<td><html:radio property="codigoFormatoNumeracao"
						value="<%=Hidrometro.FORMATO_NUMERACAO_4_X_6.toString()%>"
						onclick="atualizarSelecaoFormato();" /><strong>4x6 <html:radio
						property="codigoFormatoNumeracao"
						value="<%=Hidrometro.FORMATO_NUMERACAO_5_X_5.toString()%>"
						onclick="atualizarSelecaoFormato();" />5x5 <html:radio
						property="codigoFormatoNumeracao"
						value="<%=Hidrometro.FORMATO_NUMERACAO_LIVRE.toString()%>"
						onclick="atualizarSelecaoFormato();" />Livre</strong></td>
				</tr>
				<tr>
					<td width="30%"><strong>N&uacute;mero do
					Hidr&ocirc;metro:<font color="#FF0000">*</font></strong></td>
					<td width="70%"><html:text property="numeroHidrometro"
						size="10" maxlength="10" tabindex="1"
						onkeyup="javascript:limparAnoFabricacao();"
						onblur="javascript:validarAnoFabricacao();carregarCapacidadeHidrometroMarca();" /></td>

				</tr>
				<tr>
					<td><strong>Capacidade:<font color="#FF0000">*</font></strong></td>
					<td><html:select property="idHidrometroCapacidade"
						tabindex="2"
						onchange="redirecionarSubmit('exibirAtualizarHidrometroAction.do');">>
					  <html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="colecaoHidrometroCapacidade"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>

				<tr>
					<td><strong>Ano de Fabrica&ccedil;&atilde;o:<font
						color="#FF0000">*</font></strong></td>
					<td><html:text maxlength="4" property="anoFabricacao" size="4"
						readonly="true" style="background-color:#EFEFEF; border:0;" />&nbsp;aaaa</td>
				</tr>
				<tr>
					<td><strong>Marca:<font color="#FF0000">*</font></strong></td>
					<td><html:select property="idHidrometroMarca" tabindex="3">
						<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="colecaoHidrometroMarca"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>
				<tr>
					<td><strong>Lote de Entrega:</strong></td>
					<td><html:text maxlength="10" property="loteEntrega" size="6" tabindex="2"/></td>
				</tr>
				<logic:equal name="AtualizarHidrometroActionForm"
					property="codigoFormatoNumeracao"
					value="<%=Hidrometro.FORMATO_NUMERACAO_5_X_5.toString()%>">
					<tr>
						<td><strong>Tipo de Instalação da Turbina:<font
							color="#FF0000">*</font></strong></td>
						<td><html:select property="idHidrometroTipoTurbina">
							<html:option
								value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<html:options collection="colecaoHidrometroTipoTurbina"
								labelProperty="descricao" property="id" />
						</html:select></td>
					</tr>
				</logic:equal>

				<tr>
					<td height="24" colspan="2">
					<hr>
					</td>
				</tr>
				<tr>
					<td><strong>Data de Aquisi&ccedil;&atilde;o:<font
						color="#FF0000">*</font></strong></td>
					<td><html:text property="dataAquisicao" size="10"
						maxlength="10" onkeyup="mascaraData(this,event)" tabindex="4" />
					<a
						href="javascript:abrirCalendario('AtualizarHidrometroActionForm', 'dataAquisicao')">
					<img border="0"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>dd/mm/aaaa</td>
				</tr>
				
				<tr>
					<td><strong>Número da Nota Fiscal:</strong></td>
					<td><html:text property="numeroNotaFiscal" size="9" maxlength="9"/></td>
				</tr>

				<tr>
					<td><strong>Finalidade:</strong></td>
					<td><html:radio property="indicadorMacromedidor" tabindex="5"
						value="<%=(Hidrometro.INDICADOR_COMERCIAL).toString()%>" /><strong>Comercial</strong>
					<html:radio property="indicadorMacromedidor" tabindex="6"
						value="<%=(Hidrometro.INDICADOR_OPERACIONAL).toString()%>" /><strong>Operacional</strong>
					</td>
				</tr>
				<tr>
					<td><strong>Classe Metrológica:<font color="#FF0000">*</font></strong></td>
					<td><html:select property="idHidrometroClasseMetrologica"
						tabindex="7">
						<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="colecaoHidrometroClasseMetrologica"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>

				<tr>
					<td><strong>Diâmetro:<font color="#FF0000">*</font></strong></td>
					<td>
						<html:select property="idHidrometroDiametro" tabindex="8">
							<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<html:options collection="colecaoHidrometroDiametro" labelProperty="descricao" property="id" />
						</html:select>
					</td>
				</tr>


				<tr>
					<td><strong>Número de Digitos:<font color="#FF0000">*</font></strong></td>

					<td><logic:present name="colecaoIntervalo">
						<html:select property="idNumeroDigitosLeitura" tabindex="9"
							value="${hidrometro.numeroDigitosLeitura}">
							<html:option
								value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>

							<html:options collection="colecaoIntervalo"
								labelProperty="numeroDigitosLeitura"
								property="numeroDigitosLeitura" />

						</html:select>

						<%--<html:select property="idNumeroDigitosLeitura" tabindex="10">
              			<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option> 
			  			<html:options collection="colecaoIntervalo" labelProperty="idNumeroDigitosLeitura" property="idNumeroDigitosLeitura"/>
            		  </html:select>--%>
					</logic:present> <logic:notPresent name="colecaoIntervalo">
						<html:select property="idNumeroDigitosLeitura" tabindex="9">
							<html:option value="-1">&nbsp;</html:option>
						</html:select>
					</logic:notPresent></td>
				</tr>

				<tr>
					<td><strong>Tipo:<font color="#FF0000">*</font></strong></td>
					<td><html:select property="idHidrometroTipo" tabindex="10">
						<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="colecaoHidrometroTipo"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>

				<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>"
					property="parmId"
					value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>"
					scope="session">
					<tr>
						<td><strong>Hidrômetro Composto:</strong></td>
						<td><html:checkbox property="indicadorHidrometroComposto"
							value="true" tabindex="11"></html:checkbox></td>
					</tr>
					
					<input type="hidden" name="indicadorHidrometroComposto" value="false">

					<tr>
						<td><strong>Fator de Conversão:<font color="#FF0000">*</font></strong></td>
						<td>
							<html:text property="fatorConversao" size="7"
							maxlength="7"
							onkeyup="javascript:formataValorDecimalTresCasas(this,7)"
							tabindex="12" />
						</td>
					</tr>
					<tr>
						<td><strong>Leitura Acumulada do Hidrômetro:</strong></td>
						<td><html:text property="numeroLeituraAcumulada" size="4"
							tabindex="14" readonly="true"
							style="background-color:#EFEFEF; border:0;" /></td>
					</tr>
				</logic:equal>
				
				<tr>
					<td><strong>Situação:<font color="#FF0000">*</font></strong></td>
					<td>
						
							
							<logic:present scope="session" name="desabilitarSituacao">
								<html:select property="idHidrometroSituacao" tabindex="10" disabled="true">
									<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
									<logic:present name="colecaoHidrometroSituacao" scope="session">
									<html:options collection="colecaoHidrometroSituacao" labelProperty="descricao" property="id" />
									</logic:present>
								</html:select>
								
							</logic:present>
							
						    
						    <logic:notPresent scope="session" name="desabilitarSituacao">
						        <html:select property="idHidrometroSituacao" tabindex="10" >
									<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
									<logic:present name="colecaoHidrometroSituacao" scope="session">
									<html:options collection="colecaoHidrometroSituacao" labelProperty="descricao" property="id" />
									</logic:present>
								</html:select>
								
						    </logic:notPresent>
						
					
					</td>
				</tr>
				
				<tr>
					<td height="24" colspan="2">
					<hr>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<logic:present name="colecaoOrdemServicoManutencaoHidrometroHelper">
							<table width="100%" border="0">
								<tr>
									<td>
										<table width="100%" bgcolor="#90c7fc">
											<tr>
												<td bgcolor="#79bbfd" colspan="4">
												<div align="center" ><strong>Dados das Ordens de Serviço</strong></div>
												</td>
											</tr>
											<tr bordercolor="#FFFFFF" bgcolor="#90c7fc">
												<td>
													<div align="center" class="style9"><strong>Ordem de Serviço</strong></div>
												</td>
												<td>
													<div align="left" class="style9"><strong>Tipo de Serviço</strong></div>
												</td>
												<td>
													<div align="center" class="style9"><strong>Data da Geração</strong></div>
												</td>
												<td>
													<div align="left" class="style9"><strong>Situação</strong></div>
												</td>
											</tr>
											
											<%int cont3 = 0;%>
											<logic:iterate name="colecaoOrdemServicoManutencaoHidrometroHelper" id="ordemServicoManutencaoHidrometroHelper">
							
												<%cont3 = cont3 + 1;
													if (cont3 % 2 == 0) {%>
												<tr bgcolor="#cbe5fe">
													<%} else {
							
													%>
												<tr bgcolor="#FFFFFF">
													<%}%>
													
													<td>
														<div align="center">
															<a  title="Consultar Dados da Ordem de Serviço" href="javascript:consultarOs('<bean:write name="ordemServicoManutencaoHidrometroHelper" property="numeroOS"/>');">
																<bean:write name="ordemServicoManutencaoHidrometroHelper" property="numeroOS" />
															</a>
														</div>
													</td>
													
													<td>
														<div align="left">
															<bean:write name="ordemServicoManutencaoHidrometroHelper" property="descricaoTipoServico"/>
														</div>
													</td>
							
													<td>
														<div align="center"><bean:write name="ordemServicoManutencaoHidrometroHelper"
														property="dataGeracaoOS"/></div>
													</td>
													
													<td>
														<div align="left"><bean:write name="ordemServicoManutencaoHidrometroHelper"
														property="descricaoSituacaoOS" /></div>
													</td>
							
												</tr>
												
											</logic:iterate>
											
										</table>
									</td>
								</tr>
							</table>
						</logic:present>
					</td>
				</tr>

				<tr>
					<td>&nbsp;</td>
					<td align="left"><font color="#FF0000">*</font> Campo
					Obrigatório</td>
				</tr>
				
			</table>
			<table width="100%" border="0">
				<tr>
					<td><logic:present scope="session" name="manter">
						<input name="Submit222" class="bottonRightCol" value="Voltar"
							type="button"
							onclick="window.location.href='<html:rewrite page="/exibirManterHidrometroAction.do"/>'">
					</logic:present> <logic:notPresent scope="session" name="manter">
						<logic:present scope="session" name="filtrar_manter">
							<logic:present scope="session" name="veioDaTelaFiltrarHidrometro">
								<input name="Submit222" class="bottonRightCol" value="Voltar"
									type="button" onclick="javascript:voltarFiltrar();">
							</logic:present>
							<logic:notPresent scope="session"
								name="veioDaTelaFiltrarHidrometro">
								<input name="Submit222" class="bottonRightCol" value="Voltar"
									type="button" onclick="javascript:history.back();">
							</logic:notPresent>

						</logic:present>
						<logic:notPresent scope="session" name="filtrar_manter">
							<logic:present parameter="consultar_imovel">
								<input name="Submit222" class="bottonRightCol" value="Voltar"
									type="button" onclick="javascript:history.back();">
							</logic:present>
							<logic:notPresent parameter="consultar_imovel">
								<input name="Submit222" class="bottonRightCol" value="Voltar"
									type="button"
									onclick="window.location.href='/gsan/exibirFiltrarHidrometroAction.do?menu=sim';">
							</logic:notPresent>
						</logic:notPresent>
					</logic:notPresent> <input name="Submit22" class="bottonRightCol" value="Desfazer"
						type="button"
						onclick="window.location.href='/gsan/exibirAtualizarHidrometroAction.do?desfazer=S';">
					<input name="Submit23" class="bottonRightCol" value="Cancelar"
						type="button"
						onclick="window.location.href='/gsan/telaPrincipal.do'"> 
						<logic:equal name="AtualizarHidrometroActionForm" property="idHidrometroSituacao" value="<%="" + HidrometroSituacao.INSTALADO%>">
							<input name="Submit24" class="bottonRightCol" value="Análise Ligação Consumo" type="button"
							onclick="window.location.href='/gsan/consultarImovelWizardAction.do?destino=3&action=exibirConsultarImovelDadosAnaliseMedicaoConsumoAction&idImovelPrincipalAba=<bean:write name="AtualizarHidrometroActionForm" property="idImovel" />&flagTelaAtualizarHidrometro=sim';">
						</logic:equal>
					</td>
					<td align="right"><gcom:controleAcessoBotao name="Button"
						value="Atualizar"
						onclick="javascript:validarForm(document.AtualizarHidrometroActionForm);"
						url="atualizarHidrometroAction.do" /> <!--
					<input type="button" class="bottonRightCol" tabindex="12"
						value="Atualizar" tabindex="13"
						onclick="validarForm(document.AtualizarHidrometroActionForm);">--></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>
	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</body>
<!-- hidrometro_atualizar.jsp	 -->
</html:html>
