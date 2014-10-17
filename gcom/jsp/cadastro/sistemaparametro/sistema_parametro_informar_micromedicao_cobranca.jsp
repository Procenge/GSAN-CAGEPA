<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page import="gcom.util.ConstantesSistema"%>

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
<script>
<!--
var bCancel = false;

function validateInformarSistemaParametrosActionForm(form) {
	var retorno;
	if (bCancel)
		return true;
	else
		if(validateRequired(form) && validateInteger(form)){
			var radioCodigoLimiteAceitavelAnormalidades = form.codigoLimiteAceitavelAnormalidades;
			 var codigoLimiteAceitavelAnormalidades;
			 for(var i = 0; i < radioCodigoLimiteAceitavelAnormalidades.length; i++){
				 if(radioCodigoLimiteAceitavelAnormalidades[i].checked){
					 codigoLimiteAceitavelAnormalidades = radioCodigoLimiteAceitavelAnormalidades[i].value;
					 break;
				}
			 }
			if(codigoLimiteAceitavelAnormalidades == ''){
				retorno = true;
			}else{
				if(form.percentualAnormalidadeAceitavel.value == ''){
					alert('Informe Percentual para Controle do Limite de Anormalidade Aceitável.');
				}else{
					retorno = true;
				}
			}
			return retorno;
		}
		//validateCaracterEspecial(form) 
		//&& validateRequired(form)
		//&& validateInteger(form);
}
function IntegerValidations () {
	this.aa = new Array("incrementoMaximoConsumo", "Incremento Máximo de Consumo por economia em Rateio deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	this.ab = new Array("decrementoMaximoConsumo", "Decremento Máximo de Consumo por economia em Rateio deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	this.ac = new Array("diasVencimentoCobranca", "Número de Dias entre o Vencimento e o Início da Cobrança deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	
}



/*function caracteresespeciais () {
	this.aa = new Array("nomeEstado", "Nome do Estado possui caracteres especiais.", new Function ("varName", " return this[varName];"));
	this.ab = new Array("nomeEmpresa", "Nome da Empresa possui caracteres especiais.", new Function ("varName", " return this[varName];"));
	this.ac = new Array("abreviaturaEmpresa", "Abreviatura da Empresa possui caracteres especiais.", new Function ("varName", " return this[varName];"));
	this.ad = new Array("cnpj", "CNPJ possui caracteres especiais.", new Function ("varName", " return this[varName];"));
}*/

function required () {
	this.aa = new Array("incrementoMaximoConsumo", "Informe Incremento Máximo de Consumo por economia em Rateio.", new Function ("varName", " return this[varName];"));
	this.ab = new Array("decrementoMaximoConsumo", "Informe Decremento Máximo de Consumo por economia em Rateio.", new Function ("varName", " return this[varName];"));
	this.ac = new Array("diasVencimentoCobranca", "Informe Número de Dias entre o Vencimento e o Início da Cobrança.", new Function ("varName", " return this[varName];"));
	this.ad = new Array("indicadorLayoutArquivoLeituraPadrao", "Informe Formato do Arquivo de Leituras Segue o Formato Padrão do Gsan.", new Function ("varName", " return this[varName];"));
	this.ae = new Array("codigoLimiteAceitavelAnormalidades", "Informe Entidade de Referência para Controle do Limite Aceitável de Anormalidades.", new Function ("varName", " return this[varName];"));
}


function verificarHabilitarPercentualControleAnormalidade(){
	 var form = document.forms[0];
	 var percentualAnormalidadeAceitavel = form.percentualAnormalidadeAceitavel;
	 var radioCodigoLimiteAceitavelAnormalidades = form.codigoLimiteAceitavelAnormalidades;
	 var codigoLimiteAceitavelAnormalidades;
	 for(var i = 0; i < radioCodigoLimiteAceitavelAnormalidades.length; i++){
		 if(radioCodigoLimiteAceitavelAnormalidades[i].checked){
			 codigoLimiteAceitavelAnormalidades = radioCodigoLimiteAceitavelAnormalidades[i].value;
			 break;
		}
	 }
	 if(codigoLimiteAceitavelAnormalidades != ''){
		 if(percentualAnormalidadeAceitavel.disabled == true){
			 percentualAnormalidadeAceitavel.disabled = false;
		 }
	 }else{
		 percentualAnormalidadeAceitavel.disabled = true;
		 percentualAnormalidadeAceitavel.value = '';
	 }
}



-->
</script>
</head>

<body leftmargin="5" topmargin="5"
	onload="setarFoco('${requestScope.nomeCampo}'); verificarHabilitarPercentualControleAnormalidade();">

<html:form action="/informarParametrosSistemaWizardAction" method="post"
	onsubmit="return validateInformarSistemaParametrosActionForm(this);">

	<jsp:include
		page="/jsp/util/wizard/navegacao_abas_wizard_valida_avancar.jsp?numeroPagina=4" />

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<input type="hidden" name="numeroPagina" value="4" />
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
					<td class="parabg">Informar Parâmetros do Sistema</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0" dwcopytype="CopyTableRow">
				<tr>
					<td>Para informar parâmetros do sistema, informe os dados abaixo:
					<td align="right"></td>
				</tr>
			</table>

			<table width="100%" border="0">


				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" align="center"><strong>Parâmetros para
					Micromedição:</strong></td>
				</tr>

				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td width="40%" align="left"><strong>Código da Menor Capacidade de
					Hidrômetro para ser Grande Usuário:</strong></td>
					<td><html:select property="codigoMenorCapacidade">
						<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="colecaoHidrometroCapacidade"
							labelProperty="id" property="id" />
					</html:select></td>
				</tr>
				<tr>
					<td width="40%"><strong>Indicador de Geração de Faixa Falsa:</strong></td>
					<td><strong> <html:radio property="indicadorGeracaoFaixaFalsa"
						value="1" /> Sim <html:radio
						property="indicadorGeracaoFaixaFalsa" value="2" /> N&atilde;o <html:radio
						property="indicadorGeracaoFaixaFalsa" value="3" /> De acordo com
					a Rota</strong></td>

				</tr>
				
				<tr>
					<td width="40%"><strong>Indicador do Percentual para Geração de
					Faixa Falsa:</strong></td>
					<td><strong> <html:radio
						property="indicadorPercentualGeracaoFaixaFalsa" value="1" />
					Percentual Parâmetro <html:radio
						property="indicadorPercentualGeracaoFaixaFalsa" value="2" />
					Percentual da Rota </strong></td>

				</tr>

				<tr>
					<td width="40%" align="left"><strong> Percentual de Geração de
					Faixa Falsa:</strong></td>
					<td width="87%"><html:text property="percentualGeracaoFaixaFalsa"
						size="5" maxlength="6"
						onkeyup="javascript:formataValorMonetario(this, 5);" /> <font
						size="1"> &nbsp; </font></td>
				</tr>

				<tr>
					<td width="40%"><strong>Indicador de Geração de Fiscalização de
					Leitura :</strong></td>
					<td><strong> <html:radio
						property="indicadorGeracaoFiscalizacaoLeitura" value="1" /> Sim <html:radio
						property="indicadorGeracaoFiscalizacaoLeitura" value="2" /> Não <html:radio
						property="indicadorGeracaoFiscalizacaoLeitura" value="3" /> De
					acordo com a Rota </strong></td>

				</tr>

				<tr>
					<td width="40%"><strong>Indicador do Percentual Geração de
					Fiscalização de Leitura :</strong></td>
					<td><strong> <html:radio
						property="indicadorPercentualGeracaoFiscalizacaoLeitura" value="1" />
					Percentual Parâmetro <html:radio
						property="indicadorPercentualGeracaoFiscalizacaoLeitura" value="2" />
					Percentual da Rota </strong></td>

				</tr>

				<tr>
					<td width="40%" align="left"><strong>Percentual de Geração de
					Fiscalização de Leitura:</strong></td>
					<td width="87%"><html:text
						property="percentualGeracaoFiscalizacaoLeitura" size="5"
						maxlength="6" onkeyup="javascript:formataValorMonetario(this, 5);" />
					<font size="1"> &nbsp; </font></td>
				</tr>

				<tr>
					<td width="40%" align="left"><strong>Incremento Máximo de Consumo
					por economia em Rateio: <font color="#FF0000">*</font></strong></td>
					<td width="87%"><html:text property="incrementoMaximoConsumo"
						size="9" maxlength="9"
						onkeyup="javascript:verificaNumeroInteiro(this);" /> <font
						size="1"> &nbsp; </font></td>
				</tr>

				<tr>
					<td width="40%" align="left"><strong>Decremento Máximo de Consumo
					por economia em Rateio: <font color="#FF0000">*</font></strong></td>
					<td width="87%"><html:text property="decrementoMaximoConsumo"
						size="9" maxlength="9"
						onkeyup="javascript:verificaNumeroInteiro(this);" /> <font
						size="1"> &nbsp; </font></td>
				</tr>

				<tr>
					<td width="40%" align="left"><strong>Percentual de Tolerância para
					o Rateio do Consumo: </strong></td>
					<td width="87%"><html:text
						property="percentualToleranciaRateioConsumo" size="5"
						maxlength="5" onkeyup="javascript:formataValorDecimalUmaCasa(this, 3);" />
					<font size="1"> &nbsp; </font></td>
				</tr>
				
				<tr>
					<td width="40%"><strong> Formato do Arquivo de Leituras Segue o Formato Padrão do Gsan?</strong></td>
					<td>
						<strong> 
							<html:radio property="indicadorLayoutArquivoLeituraPadrao"value="1" /> Sim 
							<html:radio	property="indicadorLayoutArquivoLeituraPadrao" value="2" /> N&atilde;o
						</strong>
					</td>
				</tr>
				
				<tr>
					<td width="40%"><strong> Entidade de Referência para Controle do Limite Aceitável de Anormalidades:</strong></td>
					<td>
						<strong> 
							<html:radio property="codigoLimiteAceitavelAnormalidades" value=""  onclick="verificarHabilitarPercentualControleAnormalidade()" /> Sem Controle 
							<html:radio	property="codigoLimiteAceitavelAnormalidades" value="1" onclick="verificarHabilitarPercentualControleAnormalidade()"/> Controle por Grupo de Faturamento
							<html:radio	property="codigoLimiteAceitavelAnormalidades" value="2" onclick="verificarHabilitarPercentualControleAnormalidade()"/> Controle por Setor Comercial
							<html:radio	property="codigoLimiteAceitavelAnormalidades" value="3" onclick="verificarHabilitarPercentualControleAnormalidade()"/> Controle por Rota
						</strong>
					</td>
				</tr>
				
				<tr>
					<td width="40%" align="left"><strong> Percentual para Controle do Limite de Anormalidade Aceitável:</strong></td>
					<td width="87%"><html:text property="percentualAnormalidadeAceitavel"
						size="5" maxlength="5"
						onkeyup="javascript:formataValorMonetario(this, 5);" /> <font
						size="1"> &nbsp; </font></td>
				</tr>

				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" align="center"><strong>Parâmetros para Cobrança:</strong></td>

				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>




				<tr>
					<td width="40%" align="left"><strong>Número de Dias entre o
					Vencimento e o Início da Cobrança:<font color="#FF0000">*</font></strong></td>
					<td><html:text maxlength="2" property="diasVencimentoCobranca"
						size="2" onkeyup="javascript:verificaNumeroInteiro(this);" /></td>
				</tr>


				<tr>
					<td><strong></strong></td>
					<td><strong><font color="#FF0000">*</font></strong>Campo
					obrigat&oacute;rio</td>
				</tr>
				<tr>
					<td colspan="2">
					<div align="right"><jsp:include
						page="/jsp/util/wizard/navegacao_botoes_wizard_valida_avancar.jsp?numeroPagina=4" /></div>
					</td>
				</tr>
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>
</body>
</html:form>
</html:html>
