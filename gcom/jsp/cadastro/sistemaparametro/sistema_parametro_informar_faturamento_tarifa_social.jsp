<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page import="gcom.util.ConstantesSistema"%>
<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<html:javascript formName="InformarSistemaParametrosActionForm"
	dynamicJavascript="false" staticJavascript="true" />

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
	
<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
<script>
function required () {
	this.aa = new Array("mesAnoReferencia", "Informe M�s e Ano de Refer�ncia.", new Function ("varName", " return this[varName];"));
	this.ab = new Array("menorConsumo", "Informe Menor Consumo para ser Grande Usu�rio.", new Function ("varName", " return this[varName];"));
	this.ac = new Array("menorValor", "Informe Menor Valor para Emiss�o de Contas.", new Function ("varName", " return this[varName];"));
	this.ad = new Array("qtdeEconomias", "Informe Qtde de Economias para ser Grande Usu�rio.", new Function ("varName", " return this[varName];"));
	this.ae = new Array("mesesCalculoMedio", "Informe Meses para C�lculo de M�dia de Consumo.", new Function ("varName", " return this[varName];"));
	this.af = new Array("titulosRelatorio", "Informe T�tulos de Relat�rio.", new Function ("varName", " return this[varName];"));
	this.ag = new Array("numeroDiasEsperaExtratoDebito", "Informe Dias em Espera para Extrato de D�bito.", new Function ("varName", " return this[varName];"));
	this.ah = new Array("numeroMinDebitosAguaParaTodos", "Informe N�mero M�nimo D�bitos �gua para Todos.", new Function ("varName", " return this[varName];"));
	this.ai = new Array("codMotivoExclusaoAguaParaTodos", "Informe C�digo Motivo Exclus�o �gua para Todos.", new Function ("varName", " return this[varName];"));
	this.aj = new Array("numeroConsumoMinAguaParaTodos", "Informe N�mero Consumo M�nimo �gua para Todos.", new Function ("varName", " return this[varName];"));
	this.ak = new Array("numeroConsumoExcedidoAguaParaTodos", "Informe N�mero Consumo Excedido �gua para Todos.", new Function ("varName", " return this[varName];"));
	this.al = new Array("codMotExclusaoConsumoAguaParaTodos", "Informe C�digo Motivo Exclus�o Consumo �gua para Todos.", new Function ("varName", " return this[varName];"));
	this.am = new Array("codTarifaAguaParaTodos", "Informe C�digo Tarifa �gua para Todos.", new Function ("varName", " return this[varName];"));
	this.an = new Array("numeroMaxDiasVigenciaTarifaAguaParaTodos", "Informe N�mero M�x. Dias Vig�ncia Tarifa �gua para Todos.", new Function ("varName", " return this[varName];"));
}
</script>
</logic:equal>

<logic:notEqual name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
<script>
function required () {
	this.aa = new Array("mesAnoReferencia", "Informe M�s e Ano de Refer�ncia.", new Function ("varName", " return this[varName];"));
	this.ab = new Array("menorConsumo", "Informe Menor Consumo para ser Grande Usu�rio.", new Function ("varName", " return this[varName];"));
	this.ac = new Array("menorValor", "Informe Menor Valor para Emiss�o de Contas.", new Function ("varName", " return this[varName];"));
	this.ad = new Array("qtdeEconomias", "Informe Qtde de Economias para ser Grande Usu�rio.", new Function ("varName", " return this[varName];"));
	this.ae = new Array("mesesCalculoMedio", "Informe Meses para C�lculo de M�dia de Consumo.", new Function ("varName", " return this[varName];"));
	this.af = new Array("titulosRelatorio", "Informe T�tulos de Relat�rio.", new Function ("varName", " return this[varName];"));
	this.ag = new Array("numeroDiasEsperaExtratoDebito", "Informe Dias em Espera para Extrato de D�bito.", new Function ("varName", " return this[varName];"));
}
</script>
</logic:notEqual>

<script>
<!--
var bCancel = false;

function validateInformarSistemaParametrosActionForm(form) {
	if (bCancel)
		return true;
	else
		return  validateRequired(form)
		&& validateInteger(form);
		//validateCaracterEspecial(form) 
		//&& validateRequired(form)
		//&& validateInteger(form);
}
function IntegerValidations () {
	this.aa = new Array("menorConsumo", "Menor Consumo para ser Grande Usu�rio deve somente conter n�meros positivos.", new Function ("varName", " return this[varName];"));
	this.ab = new Array("menorValor", "Menor Valor para Emiss�o de Contas deve somente conter n�meros positivos.", new Function ("varName", " return this[varName];"));
	this.ac = new Array("qtdeEconomias", "Qtde de Economias para ser Grande Usu�rio deve somente conter n�meros positivos.", new Function ("varName", " return this[varName];"));
	this.ad = new Array("mesesCalculoMedio", "Meses para C�lculo de M�dia de Consumo deve somente conter n�meros positivos.", new Function ("varName", " return this[varName];"));
	this.ae = new Array("diasMinimoVencimento", "Dias M�nimo para Calcular Vencimento deve somente conter n�meros positivos.", new Function ("varName", " return this[varName];"));
	this.af = new Array("diasMinimoVencimentoCorreio", "Dias M�nimo para Calcular Vencimento se entrega pelos Correios deve somente conter n�meros positivos.", new Function ("varName", " return this[varName];"));
	this.ag = new Array("numeroMesesValidadeConta", "N�mero de Meses para validade da Conta deve somente conter n�meros positivos.", new Function ("varName", " return this[varName];"));
	this.ah = new Array("numeroMesesAlteracaoVencimento", "N�mero de Meses para altera��o de um vencimento para outro deve somente conter n�meros positivos.", new Function ("varName", " return this[varName];"));
	this.ai = new Array("consumoMaximo", "Consumo de Energia M�xima para a Tarifa deve somente conter n�meros positivos.", new Function ("varName", " return this[varName];"));
	this.aj = new Array("areaMaxima", "�rea M�xima do Im�vel para a Tarifa deve somente conter n�meros positivos.", new Function ("varName", " return this[varName];"));
	this.ak = new Array("numeroMaximoTiposDebitoEmissaoDocumento", "N�mero M�ximo de Tipos de D�bito por Documento deve somente conter n�meros positivos.", new Function ("varName", " return this[varName];"));
	}



/*function caracteresespeciais () {
	this.aa = new Array("nomeEstado", "Nome do Estado possui caracteres especiais.", new Function ("varName", " return this[varName];"));
	this.ab = new Array("nomeEmpresa", "Nome da Empresa possui caracteres especiais.", new Function ("varName", " return this[varName];"));
	this.ac = new Array("abreviaturaEmpresa", "Abreviatura da Empresa possui caracteres especiais.", new Function ("varName", " return this[varName];"));
	this.ad = new Array("cnpj", "CNPJ possui caracteres especiais.", new Function ("varName", " return this[varName];"));
}*/

function validarDias(valor){
	var form = document.forms[0];
	//if(form.diasMinimoVencimento.value > 15){
		//form.diasMinimoVencimento.value = '';
		//}
		//if(form.diasMinimoVencimento.value > 15){
		//form.diasMinimoVencimento.value = '';
		//}
	if(valor > 15){
	alert('O n�mero m�nimo de dias n�o pode ser superior a 15');
	limparDiasMinimoVencimento();
	limparDiasMinimoVencimentoCorreio();
			
	}

}

function validarMes(valor){
	if(valor > 12){
		alert('O n�mero m�nimo de meses n�o pode ser superior a 12');
		limparNumeroMesesValidadeConta();
		limparNumeroMesesAlteracaoVencimento();
	}
}

function validarNumeroDiasEsperaExtratoDebito(valor) {
	
	var form = document.forms[0];
	var mensagem = '';
	var indicadorInvalidacao = false;
	var numeroDiasMenor = parseInt(form.numeroDiasEsperaMenor.value);
	var numeroDiasMaior = parseInt(form.numeroDiasEsperaMaior.value);
	
	if (valor != '') {
		if (valor < numeroDiasMenor) {
			indicadorInvalidacao = true;
			mensagem = 'O n�mero de dias de espera n�o pode ser menor que ' + form.numeroDiasEsperaMenor.value + '.';
		} else if (valor > numeroDiasMaior) {
			indicadorInvalidacao = true;
			mensagem = 'O n�mero de dias de espera n�o pode ser maior que ' + form.numeroDiasEsperaMaior.value + '.';
		}
	}
	
	if (indicadorInvalidacao) {
		alert(mensagem);
		form.numeroDiasEsperaExtratoDebito.value = '';
		form.numeroDiasEsperaExtratoDebito.focus();
	}
}

function limparDiasMinimoVencimento(){

	var form = document.forms[0];
		if(form.diasMinimoVencimento.value > 15){
		form.diasMinimoVencimento.value = '';
		form.diasMinimoVencimento.focus();
		}
}

function limparDiasMinimoVencimentoCorreio(){
	var form = document.forms[0];
		if(form.diasMinimoVencimentoCorreio.value > 15){
		form.diasMinimoVencimentoCorreio.value = '';
		form.diasMinimoVencimentoCorreio.focus();
		}
}

function limparNumeroMesesValidadeConta()	{
	var form = document.forms[0];
	
	if(form.numeroMesesValidadeConta.value > 12){
	form.numeroMesesValidadeConta.value = '';
	form.numeroMesesValidadeConta.focus();
	}
}
	
function limparNumeroMesesAlteracaoVencimento(){
	var form = document.forms[0];	
	if(form.numeroMesesAlteracaoVencimento.value > 12){
	form.numeroMesesAlteracaoVencimento.value = '';
	form.numeroMesesAlteracaoVencimento.focus();
	}
}
-->
</script>
</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('${requestScope.nomeCampo}');">

<html:form action="/informarParametrosSistemaWizardAction" method="post"
	onsubmit="return validateInformarSistemaParametrosActionForm(this);">

	<jsp:include
		page="/jsp/util/wizard/navegacao_abas_wizard_valida_avancar.jsp?numeroPagina=2" />

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<input type="hidden" name="numeroPagina" value="2" />
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
			<td width="655" valign="top" class="centercoltext">
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
					<td class="parabg">Informar Par�metros do Sistema</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0" dwcopytype="CopyTableRow">
				<tr>
					<td>Para informar par�metros do sistema, informe os dados abaixo:
					<td align="right"></td>
				</tr>
			</table>

			<table width="100%" border="0">


				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" align="center"><strong>Par�metros para Faturamento:</strong></td>
				</tr>

				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td width="40%" align="left"><strong>M�s e Ano de Refer�ncia:</strong><font
						color="#FF0000">*</font></td>
					<td width="82%"><html:text property="mesAnoReferencia" size="7"
						maxlength="7" onkeyup="javascript:mascaraAnoMes(this,event);" />
					<strong>mm/aaaa</strong></td>

				</tr>
				<tr>
					<td width="40%" align="left"><strong>Menor Consumo para ser Grande
					Usu�rio:<font color="#FF0000">*</font></strong></td>
					<td><html:text maxlength="9" property="menorConsumo" size="9"
						onkeyup="javascript:verificaNumeroInteiro(this);" /></td>
				</tr>
				<tr>
					<td width="40%" align="left"><strong>Menor Valor para Emiss�o de
					Contas:<font color="#FF0000">*</font></strong></td>
					<td><html:text maxlength="13" property="menorValor" size="13"
						onkeyup="javascript:formataValorMonetario(this,13);" /></td>
				</tr>
				<tr>
					<td width="40%" align="left"><strong> Qtde de Economias para ser
					Grande Usu�rio:<font color="#FF0000">*</font></strong></td>
					<td width="87%"><html:text property="qtdeEconomias" size="9"
						maxlength="9" onkeyup="javascript:verificaNumeroInteiro(this);" />
					<font size="1"> &nbsp; </font></td>
				</tr>

				<tr>
					<td width="40%" align="left"><strong> Meses para C�lculo de M�dia
					de Consumo:<font color="#FF0000">*</font></strong></td>
					<td width="87%"><html:text property="mesesCalculoMedio" size="2"
						maxlength="2" onkeyup="javascript:verificaNumeroInteiro(this);" />
					<font size="1"> &nbsp; </font></td>
				</tr>


				<tr>
					<td width="40%" align="left"><strong>Dias M�nimo para Calcular
					Vencimento:</strong></td>
					<td><html:text maxlength="5" property="diasMinimoVencimento"
						size="2" onkeyup="javascript:verificaNumeroInteiro(this);"
						onblur="javascript:validarDias(document.forms[0].diasMinimoVencimento.value);" /></td>
				</tr>

				<tr>
					<td width="40%" align="left"><strong>Dias M�nimo para Calcular
					Vencimento se entrega pelos Correios:</strong></td>
					<td><html:text maxlength="2" property="diasMinimoVencimentoCorreio"
						size="2" onkeyup="javascript:verificaNumeroInteiro(this);"
						onblur="javascript:validarDias(document.forms[0].diasMinimoVencimentoCorreio.value);" /></td>
				</tr>

				<tr>
					<td width="40%" align="left"><strong>N�mero de Meses para validade
					da Conta:</strong></td>
					<td><html:text maxlength="2" property="numeroMesesValidadeConta"
						size="2" onkeyup="javascript:verificaNumeroInteiro(this);"
						onblur="validarMes(document.forms[0].numeroMesesValidadeConta.value);" /></td>
				</tr>
				<tr>
					<td width="40%" align="left"><strong>N�mero de Meses para altera��o
					de um vencimento para outro:</strong></td>
					<td><html:text maxlength="2"
						property="numeroMesesAlteracaoVencimento" size="2"
						onkeyup="javascript:verificaNumeroInteiro(this);"
						onblur="validarMes(document.forms[0].numeroMesesAlteracaoVencimento.value);" /></td>
				</tr>				
				<tr>
					<td width="40%" align="left"><strong>Dias em Espera para Extrato de D�bito:<font color="#FF0000">*</font></strong></td>
					<td>
						<html:text maxlength="2"
							property="numeroDiasEsperaExtratoDebito" size="2"
							onkeyup="javascript:verificaNumeroInteiro(this);" 
							onblur="validarNumeroDiasEsperaExtratoDebito(document.forms[0].numeroDiasEsperaExtratoDebito.value);" />
					</td>
				</tr>
				<tr>
					<td>
						<html:hidden property="numeroDiasEsperaMenor"/>
						<html:hidden property="numeroDiasEsperaMaior"/>
					</td>					
				</tr>
				
				<tr>
					<td width="40%"><strong>Indicador de Fatura do Cliente Atual:</strong></td>
					<td><strong> 				
					<html:radio
						property="indicadorClienteAtualFatura" value="1" /> Sim 
						<html:radio
						property="indicadorClienteAtualFatura" value="2" /> N�o </strong></td>

				</tr>
				
				<!-- Campo que estou adicionando - Rodrigo Oliveira -->
				<tr>
					<td width="40%"><strong>Ano refer�ncia d�bito:</strong></td>
					<td>
						<html:text maxlength="4" 
							property="anoReferenciaDebitoConta" size="4"
							onkeyup="javascript:verificaNumeroInteiro(this);"  />
					</td>
				</tr>
				<!-- Campo que estou adicionando - Rodrigo Oliveira -->
				
				<!-- Campos adicionandos no desenvolvimento do faturamento DESO - Ailton Sousa - INICIO -->
				<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
				
				<tr>
					<td width="40%"><strong>N&uacute;mero M&iacute;nimo D&eacute;bitos &Aacute;gua para Todos:</strong><font
						color="#FF0000">*</font></td>
					<td>
						<html:text maxlength="4" 
							property="numeroMinDebitosAguaParaTodos" size="4"
							onkeyup="javascript:verificaNumeroInteiro(this);"  />
					</td>
				</tr>
				<tr>
					<td width="40%"><strong>C&oacute;digo Motivo Exclus&atilde;o &Aacute;gua para Todos:</strong><font
						color="#FF0000">*</font></td>
					<td>
						<html:text maxlength="4" 
							property="codMotivoExclusaoAguaParaTodos" size="4"
							onkeyup="javascript:verificaNumeroInteiro(this);"  />
					</td>
				</tr>
				<tr>
					<td width="40%"><strong>N&uacute;mero Consumo M&iacute;nimo &Aacute;gua para Todos:</strong><font
						color="#FF0000">*</font></td>
					<td>
						<html:text maxlength="7" 
							property="numeroConsumoMinAguaParaTodos" size="7"
							onkeyup="javascript:verificaNumeroInteiro(this);"  />
					</td>
				</tr>
				<tr>
					<td width="40%"><strong>N&uacute;mero Consumo Excedido &Aacute;gua para Todos:</strong><font
						color="#FF0000">*</font></td>
					<td>
						<html:text maxlength="7" 
							property="numeroConsumoExcedidoAguaParaTodos" size="7"
							onkeyup="javascript:verificaNumeroInteiro(this);"  />
					</td>
				</tr>
				<tr>
					<td width="40%"><strong>C&oacute;digo Motivo Exclus&atilde;o Consumo &Aacute;gua para Todos:</strong><font
						color="#FF0000">*</font></td>
					<td>
						<html:text maxlength="4" 
							property="codMotExclusaoConsumoAguaParaTodos" size="4"
							onkeyup="javascript:verificaNumeroInteiro(this);"  />
					</td>
				</tr>
				<tr>
					<td width="40%"><strong>C&oacute;digo Tarifa &Aacute;gua para Todos:</strong><font
						color="#FF0000">*</font></td>
					<td>
						<html:text maxlength="4" 
							property="codTarifaAguaParaTodos" size="4"
							onkeyup="javascript:verificaNumeroInteiro(this);"  />
					</td>
				</tr>
				<tr>
					<td width="40%"><strong>N&uacute;mero M&aacute;x. Dias Vig&ecirc;ncia Tarifa &Aacute;gua para Todos:</strong><font
						color="#FF0000">*</font></td>
					<td>
						<html:text maxlength="4" 
							property="numeroMaxDiasVigenciaTarifaAguaParaTodos" size="4"
							onkeyup="javascript:verificaNumeroInteiro(this);"  />
					</td>
				</tr>
				</logic:equal>
				
				<logic:notEqual name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
				
				<tr>
					<td width="40%"><strong>N&uacute;mero M&iacute;nimo D&eacute;bitos &Aacute;gua para Todos:</strong><font
						color="#FF0000"></font></td>
					<td>
						<html:text maxlength="4" 
							property="numeroMinDebitosAguaParaTodos" size="4"
							onkeyup="javascript:verificaNumeroInteiro(this);"  />
					</td>
				</tr>
				<tr>
					<td width="40%"><strong>C&oacute;digo Motivo Exclus&atilde;o &Aacute;gua para Todos:</strong><font
						color="#FF0000"></font></td>
					<td>
						<html:text maxlength="4" 
							property="codMotivoExclusaoAguaParaTodos" size="4"
							onkeyup="javascript:verificaNumeroInteiro(this);"  />
					</td>
				</tr>
				<tr>
					<td width="40%"><strong>N&uacute;mero Consumo M&iacute;nimo &Aacute;gua para Todos:</strong><font
						color="#FF0000"></font></td>
					<td>
						<html:text maxlength="7" 
							property="numeroConsumoMinAguaParaTodos" size="7"
							onkeyup="javascript:verificaNumeroInteiro(this);"  />
					</td>
				</tr>
				<tr>
					<td width="40%"><strong>N&uacute;mero Consumo Excedido &Aacute;gua para Todos:</strong><font
						color="#FF0000"></font></td>
					<td>
						<html:text maxlength="7" 
							property="numeroConsumoExcedidoAguaParaTodos" size="7"
							onkeyup="javascript:verificaNumeroInteiro(this);"  />
					</td>
				</tr>
				<tr>
					<td width="40%"><strong>C&oacute;digo Motivo Exclus&atilde;o Consumo &Aacute;gua para Todos:</strong><font
						color="#FF0000"></font></td>
					<td>
						<html:text maxlength="4" 
							property="codMotExclusaoConsumoAguaParaTodos" size="4"
							onkeyup="javascript:verificaNumeroInteiro(this);"  />
					</td>
				</tr>
				<tr>
					<td width="40%"><strong>C&oacute;digo Tarifa &Aacute;gua para Todos:</strong><font
						color="#FF0000"></font></td>
					<td>
						<html:text maxlength="4" 
							property="codTarifaAguaParaTodos" size="4"
							onkeyup="javascript:verificaNumeroInteiro(this);"  />
					</td>
				</tr>
				<tr>
					<td width="40%"><strong>N&uacute;mero M&aacute;x. Dias Vig&ecirc;ncia Tarifa &Aacute;gua para Todos:</strong><font
						color="#FF0000"></font></td>
					<td>
						<html:text maxlength="4" 
							property="numeroMaxDiasVigenciaTarifaAguaParaTodos" size="4"
							onkeyup="javascript:verificaNumeroInteiro(this);"  />
					</td>
				</tr>
				</logic:notEqual>
				
				<!-- Campos adicionandos no desenvolvimento do faturamento DESO - Ailton Sousa - FIM -->
				
				<tr>					
					<td>&nbsp;</td>											
				</tr>
				
				<tr>
					<td colspan="2" align="center"><strong>Par�metros de Impress�o:</strong></td>

				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td width="40%" align="left"><strong>T�tulo P�gina Emiss�o Relat�rios:</strong></td>
					<td><html:text maxlength="40" property="titulosRelatorio" size="40" /></td>
				</tr>
				<tr>
					<td width="40%" align="left"><strong>M�ximo de Tipos de D�bito por Documento:</strong></td>
					<td><html:text maxlength="2" property="numeroMaximoTiposDebitoEmissaoDocumento" size="4" onkeyup="javascript:verificaNumeroInteiro(this);" /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" align="center"><strong>Par�metros para Tarifa
					Social:</strong></td>

				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td width="40%" align="left"><strong>Sal�rio M�nimo:</strong></td>
					<td><html:text maxlength="9" property="salarioMinimo" size="9"
						onkeyup="javascript:formataValorMonetario(this, 9);" /></td>
				</tr>
				<tr>
					<td width="40%" align="left"><strong>�rea M�xima do Im�vel para a
					Tarifa:</strong></td>
					<td><html:text maxlength="7" property="areaMaxima" size="7"
						onkeyup="javascript:verificaNumeroInteiro(this);" /></td>
				</tr>
				<tr>
					<td width="40%" align="left"><strong>Consumo de Energia M�xima para
					a Tarifa:</strong></td>
					<td><html:text maxlength="4" property="consumoMaximo" size="4"
						onkeyup="javascript:verificaNumeroInteiro(this);" /></td>
				</tr>
				<tr>
					<td><strong></strong></td>
					<td><strong><font color="#FF0000">*</font></strong>Campo
					obrigat&oacute;rio</td>
				</tr>
				<tr>
					<td colspan="2">
					<div align="right"><jsp:include
						page="/jsp/util/wizard/navegacao_botoes_wizard_valida_avancar.jsp?numeroPagina=2" /></div>
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
