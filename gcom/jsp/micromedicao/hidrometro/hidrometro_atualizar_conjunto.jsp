<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="gcom.util.ConstantesSistema"%>
<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro" %>
<%@ page import="gcom.micromedicao.hidrometro.Hidrometro" isELIgnored="false"%>

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
		this.ab = new Array("dataAquisicao", "Informe Data de Aquisi��o.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
		this.ac = new Array("anoFabricacao", "Informe Ano de Fabrica��o.", new Function ("varName", " return this[varName];"));
		this.ad = new Array("idHidrometroClasseMetrologica", "Informe Classe Metrol�gica.", new Function ("varName", " return this[varName];"));
		this.ae = new Array("idHidrometroMarca", "Informe Marca.", new Function ("varName", " return this[varName];"));
		this.af = new Array("idHidrometroDiametro", "Informe Di�metro.", new Function ("varName", " return this[varName];"));
		this.ag = new Array("idHidrometroCapacidade", "Informe Capacidade.", new Function ("varName", " return this[varName];"));
		this.ah = new Array("idNumeroDigitosLeitura", "Informe N�mero de Digitos.", new Function ("varName", " return this[varName];"));
		this.ai = new Array("idHidrometroTipo", "Informe Tipo.", new Function ("varName", " return this[varName];"));
	}
</script>
</logic:equal>

<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
<script language="JavaScript">
	function FloatValidations () {
		this.aa = new Array("fatorConversao", "Fator de Convers�o deve somente conter n�meros decimais positivos.", new Function ("varName", " return this[varName];"));
   }

    function required () {
     this.ab = new Array("dataAquisicao", "Informe Data de Aquisi��o.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
     this.ac = new Array("anoFabricacao", "Informe Ano de Fabrica��o.", new Function ("varName", " return this[varName];"));
     this.ad = new Array("idHidrometroClasseMetrologica", "Informe Classe Metrol�gica.", new Function ("varName", " return this[varName];"));
     this.ae = new Array("idHidrometroMarca", "Informe Marca.", new Function ("varName", " return this[varName];"));
     this.af = new Array("idHidrometroDiametro", "Informe Di�metro.", new Function ("varName", " return this[varName];"));
     this.ag = new Array("idHidrometroCapacidade", "Informe Capacidade.", new Function ("varName", " return this[varName];"));
     this.ah = new Array("idNumeroDigitosLeitura", "Informe N�mero de Digitos.", new Function ("varName", " return this[varName];"));
     this.ai = new Array("idHidrometroTipo", "Informe Tipo.", new Function ("varName", " return this[varName];"));
		this.aj = new Array("fatorConversao", "Informe Fator de Convers�o.", new Function ("varName", " return this[varName];"));
	}
</script>
</logic:equal>

<script language="JavaScript">
<!-- Begin
	var bCancel = false;

	function validateHidrometroActionForm(form) {
		if (bCancel)
			return true;
		else
			return validateRequired(form)
				&& validateCaracterEspecial(form)
				&& validateDate(form)
				&& validateLong(form)
				&& validateDecimal(form)
				&& validaDataRevisao();
    }

    function DateValidations () {
     this.aa = new Array("dataAquisicao", "Data de Aquisi��o inv�lida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
 	 this.bb = new Array("dataUltimaRevisao", "Data da revis�o inv�lida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));		
       }

    function caracteresespeciais () {
    	this.ab = new Array("dataAquisicao", "Data de Aquisi��o possui caracteres especiais.", new Function ("varName", " return this[varName];"));
    	this.ac = new Array("anoFabricacao", "Ano de Fabrica��o possui caracteres especiais.", new Function ("varName", " return this[varName];"));
    }

    function IntegerValidations () {
     this.aa = new Array("anoFabricacao", "Ano de Fabrica��o deve somente conter n�meros positivos.", new Function ("varName", " return this[varName];"));

    }

function validarForm(form){

	    if (validateHidrometroActionForm(form)){
             submeterFormPadrao(form);
	  }
}

function validaDataRevisao(){

	var resposta=true;

	if(document.forms[0].dataUltimaRevisao!=null && document.forms[0].dataUltimaRevisao.value!=""){
	
	resposta= comparaDatas(document.forms[0].dataUltimaRevisao.value,'>',document.forms[0].dataAquisicao.value);
	
		if(resposta==false){
					
			alert("A Data da �ltima Revis�o n�o pode ser anterior a Data de Aquisi��o do Hidr�metro.");
						
		}
	}
	return resposta;
}

	



//End -->

</script>


</head>

<body leftmargin="5" topmargin="5">
<html:form action="/atualizarConjuntoHidrometroAction.do"
	name="HidrometroActionForm"
	type="gcom.gui.micromedicao.hidrometro.HidrometroActionForm"
	method="post">

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
					<td class="parabg">Atualizar Conjunto Hidr&ocirc;metros</td>
					<td width="11" valign="top"><img
						src="<bean:message key="caminho.imagens"/>parahead_right.gif"
						border="0" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para atualizar o(s) hidr&ocirc;metros(s), informe
					os dados abaixo:</td>
					<td align="right"></td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td><strong>Formato da Numera��o do Hidr�metro:</strong></td>
					<td>
				  		<html:radio property="codigoFormatoNumeracao" value="<%=Hidrometro.FORMATO_NUMERACAO_4_X_6.toString()%>" disabled="true"/><strong>4x6
						<html:radio property="codigoFormatoNumeracao" value="<%=Hidrometro.FORMATO_NUMERACAO_5_X_5.toString()%>" disabled="true"/>5x5
						<html:radio property="codigoFormatoNumeracao" value="<%=Hidrometro.FORMATO_NUMERACAO_LIVRE.toString()%>" disabled="true"/>Livre</strong>
					</td>
				</tr>

				<tr>
					<logic:notEqual name="HidrometroActionForm" property="codigoFormatoNumeracao" value="<%=Hidrometro.FORMATO_NUMERACAO_LIVRE.toString()%>">
						<td colspan="2"><strong>Numera��o dos Hidr�metros</strong></td>
					</logic:notEqual>

					<logic:equal name="HidrometroActionForm" property="codigoFormatoNumeracao" value="<%=Hidrometro.FORMATO_NUMERACAO_LIVRE.toString()%>">
						<td><strong>Numera��o dos Hidr�metros</strong></td>
						<td>
							<html:select styleId="colecaoHidrometroNumero" property="numeroHidrometro">  
								<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
								<html:options name="colecaoHidrometroNumero" />  
							</html:select>
						</td>
					</logic:equal>
				</tr>

				<tr>
					<logic:equal name="HidrometroActionForm" property="codigoFormatoNumeracao" value="<%=Hidrometro.FORMATO_NUMERACAO_4_X_6.toString()%>">
						<td><strong>Fixo:</strong></td>
						<td>
							<html:text maxlength="4" property="fixo" size="4" disabled="true"/>
						</td>
					</logic:equal>
					<logic:equal name="HidrometroActionForm" property="codigoFormatoNumeracao" value="<%=Hidrometro.FORMATO_NUMERACAO_5_X_5.toString()%>">
						<td><strong>Fixo:</strong></td>
						<td>
							<html:text maxlength="5" property="fixo" size="5" disabled="true"/>
						</td>
					</logic:equal>
				</tr>

				<tr>
					<logic:equal name="HidrometroActionForm" property="codigoFormatoNumeracao" value="<%=Hidrometro.FORMATO_NUMERACAO_4_X_6.toString()%>">
						<td><strong>Faixa:</strong></td>
						<td>
							<html:text maxlength="6" property="faixaInicial" size="6" disabled="true"/>
							<html:text maxlength="6" property="faixaFinal" size="6" disabled="true"/>
						</td>
					</logic:equal>

					<logic:equal name="HidrometroActionForm" property="codigoFormatoNumeracao" value="<%=Hidrometro.FORMATO_NUMERACAO_5_X_5.toString()%>">
						<td><strong>Faixa:</strong></td>
						<td>
							<html:text maxlength="5" property="faixaInicial" size="5" disabled="true"/>
							<html:text maxlength="5" property="faixaFinal" size="5" disabled="true"/>
						</td>
					</logic:equal>
				</tr>

				<tr>
					<td><strong>Capacidade:</strong></td>
					<td><html:select property="idHidrometroCapacidade" disabled="true"
						onchange="redirecionarSubmit('exibirAtualizarConjuntoHidrometroAction.do?pesquisaIntervalo=1');">>
	                  <html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="colecaoHidrometroCapacidade"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>
				<tr>
					<td><strong>Ano de Fabrica&ccedil;&atilde;o:</strong>
					</td>
					<td><html:text maxlength="4" property="anoFabricacao" size="4" disabled="true"/>&nbsp;aaaa
					</td>
				</tr>
				<tr>
					<td><strong>Marca:</strong></td>
					<td><html:select property="idHidrometroMarca" disabled="true">
						<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="colecaoHidrometroMarca"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>

				<logic:equal name="HidrometroActionForm" property="codigoFormatoNumeracao" value="<%=Hidrometro.FORMATO_NUMERACAO_5_X_5.toString()%>">
				<tr>
					<td><strong>Tipo de Instala��o da Turbina:</strong></td>
					<td>
						<html:select property="idHidrometroTipoTurbina" disabled="true">
							<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<html:options collection="colecaoHidrometroTipoTurbina" labelProperty="descricao" property="id" />
						</html:select>
					</td>
				</tr>
				</logic:equal>

				<tr>
					<td height="24" colspan="2">
					<hr>
					</td>
				</tr>
				<tr>
					<td><strong>Data de Aquisi&ccedil;&atilde;o:<font color="#FF0000">*</font></strong>
					</td>
					<td><html:text property="dataAquisicao" size="10" maxlength="10"
						onkeyup="mascaraData(this,event)" /> <a
						href="javascript:abrirCalendario('HidrometroActionForm', 'dataAquisicao')">
					<img border="0"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calend�rio" /></a>
					dd/mm/aaaa</td>
				</tr>

				<tr>
					<td><strong>Finalidade:</strong></td>
					<td><html:radio property="indicadorMacromedidor"
						value="<%=(Hidrometro.INDICADOR_COMERCIAL).toString()%>" /><strong>Comercial</strong>
					<html:radio property="indicadorMacromedidor"
						value="<%=(Hidrometro.INDICADOR_OPERACIONAL).toString()%>" /><strong>Operacional</strong>
					</td>
				</tr>
				<tr>
					<td><strong>Classe Metrol�gica:<font color="#FF0000">*</font></strong>
					</td>
					<td><html:select property="idHidrometroClasseMetrologica">
						<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="colecaoHidrometroClasseMetrologica"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>

				<tr>
					<td><strong>Di�metro:<font color="#FF0000">*</font></strong></td>
					<td><html:select property="idHidrometroDiametro">
						<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="colecaoHidrometroDiametro"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>

				<tr>
					<td><strong>N�mero de Digitos:<font color="#ff0000">*</font></strong></td>
					<td><logic:present name="colecaoIntervalo">
						<html:select property="idNumeroDigitosLeitura">
							<html:option
								value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<logic:iterate name="colecaoIntervalo"
								id="idNumeroDigitosLeitura">
								<html:option value="${idNumeroDigitosLeitura}">
									<bean:write name="idNumeroDigitosLeitura" />
								</html:option>
							</logic:iterate>
						</html:select>

					</logic:present> <logic:notPresent name="colecaoIntervalo">
						<html:select property="idNumeroDigitosLeitura">
							<html:option value="-1">&nbsp;</html:option>
						</html:select>
					</logic:notPresent></td>
				</tr>
				<tr>
					<td><strong>Tipo:<font color="#FF0000">*</font></strong></td>
					<td><html:select property="idHidrometroTipo">
						<html:option
							value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<html:options collection="colecaoHidrometroTipo"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>

				<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
					<tr>
						<td><strong>Hidr�metro Composto:</strong></td>
						<td><html:checkbox property="indicadorHidrometroComposto" value="true" tabindex="11"></html:checkbox></td>
					</tr>
					<input type="hidden" name="indicadorHidrometroComposto" value="false">

					<tr>
					    <td><strong>Fator de Convers�o:<font color="#FF0000">*</font></strong></td>
					    <td><html:text property="fatorConversao" size="7" maxlength="7" onkeyup="javascript:formataValorDecimalTresCasas(this,7)" tabindex="12"/></td>
					</tr>
				</logic:equal>
				<tr>
						<td><strong>Data da �ltima Revis�o:</strong></td>
						<td><html:text property="dataUltimaRevisao" size="10"
							maxlength="10" onkeyup="mascaraData(this,event)" tabindex="13" />
						<a
							href="javascript:abrirCalendario('AtualizarHidrometroActionForm', 'dataUltimaRevisao')">
						<img border="0"
							src="<bean:message key="caminho.imagens"/>calendario.gif"
							width="20" border="0" align="absmiddle" alt="Exibir Calend�rio" /></a>dd/mm/aaaa</td>
					</tr>
				<tr>
					<td>&nbsp;</td>
					<td align="left"><font color="#FF0000">*</font> Campo Obrigat�rio</td>
				</tr>
				<tr>
					<td><logic:present scope="session" name="manter">
						<input name="Submit222" class="bottonRightCol" value="Voltar"
							type="button"
							onclick="window.location.href='<html:rewrite page="/exibirManterHidrometroAction.do"/>'">
					</logic:present> <logic:notPresent scope="session" name="manter">
						<logic:present scope="session" name="filtrar_manter">
							<input name="Submit222" class="bottonRightCol" value="Voltar"
								type="button" onclick="javascript:history.back();">
						</logic:present>
						<logic:notPresent scope="session" name="filtrar_manter">
							<input name="Submit222" class="bottonRightCol" value="Voltar"
								type="button"
								onclick="window.location.href='/gsan/exibirFiltrarHidrometroAction.do?menu=sim';">
						</logic:notPresent>
					</logic:notPresent> <input name="Submit22" class="bottonRightCol"
						value="Desfazer" type="button"
						onclick="window.location.href='/gsan/exibirAtualizarConjuntoHidrometroAction.do';">
					<input name="Submit23" class="bottonRightCol" value="Cancelar"
						type="button"
						onclick="window.location.href='/gsan/telaPrincipal.do'"></td>
					<td align="right"><input type="button" class="bottonRightCol"
						value="Atualizar" tabindex="13"
						onclick="validarForm(document.forms[0]);"></td>
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>
	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</body>
</html:html>
