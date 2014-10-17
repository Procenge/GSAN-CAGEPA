<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

<%@ page import="gcom.micromedicao.hidrometro.Hidrometro"%>
<%@ page import="gcom.util.ConstantesSistema" %>

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
<script type="text/javascript" language="Javascript">
<!-- Begin
     var bCancel = false;

    function validateHidrometroActionForm(form) {
        if (bCancel)
      return true;
        else
       return validateCaracterEspecial(form) && validateDate(form) && validateLong(form);
   }

    function caracteresespeciais () {
     this.aa = new Array("numeroHidrometro", "Número do Hidrômetro possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ab = new Array("dataAquisicao", "Data de Aquisição possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ac = new Array("anoFabricacao", "Ano de Fabricação possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ad = new Array("idLocalArmazenagem", "Local de Armazenagem possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ae = new Array("fixo", "Fixo da Numeração dos Hidrômetros possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.af = new Array("faixaInicial", "Faixa Inicial da Numeração dos Hidrômetros possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ag = new Array("faixaFinal", "Faixa Final da Numeração dos Hidrômetros possui caracteres especiais.", new Function ("varName", " return this[varName];"));
    }

	function DateValidations () {
    	this.aa = new Array("dataAquisicao", "Data de Aquisição inválida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
    }

    function IntegerValidations () {
     this.aa = new Array("anoFabricacao", "Ano de Fabricação deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.ab = new Array("idLocalArmazenagem", "Local de Armazenagem deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.ac = new Array("faixaInicial", "Faixa Inicial da Numeração dos Hidrômetros deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.ad = new Array("faixaFinal", "Faixa Final da Numeração dos Hidrômetros deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    }


function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

    var form = document.HidrometroActionForm;

    if (tipoConsulta == 'hidrometroLocalArmazenagem') {
      form.idLocalArmazenagem.value = codigoRegistro;
      form.localArmazenagemDescricao.value = descricaoRegistro;
      form.localArmazenagemDescricao.style.color = "#000000";

    }
  }

 function limparPesquisaLocalArmazenagem() {
    var form = document.HidrometroActionForm;

    form.idLocalArmazenagem.value = "";
    form.localArmazenagemDescricao.value = "";

  }



function validarForm(form){
    var intervalo = (form.faixaFinal.value - form.faixaInicial.value) + 1;
    
	if (validateHidrometroActionForm(form) && testarCampoValorZero(form.anoFabricacao, 'Data Fabricação') && testarCampoValorZero(form.idLocalArmazenagem, 'Local de Armazenamento'))
	{
    	if(form.numeroHidrometro.value == "" && form.dataAquisicao.value =="" && form.anoFabricacao.value =="" && document.getElementById("indicadorMacromedidorAUX").value == "" && form.idHidrometroClasseMetrologica.value =="-1" &&
             form.idHidrometroMarca.value =="-1" && form.idHidrometroDiametro.value =="-1" && form.idHidrometroCapacidade.value =="-1" && form.idHidrometroTipo.value =="-1" && form.idLocalArmazenagem.value =="")
        {
        	if(form.fixo.value != "" && form.faixaInicial.value != "" && form.faixaFinal.value != "" )
        	{  
            	if(form.faixaInicial.value > 0 && form.faixaFinal.value > 0)
            	{
                	if(intervalo > 0)
                	{
                  		submeterFormPadrao(form);
                 	}else{
                 		alert("Faixa Final da Numeração dos Hidrômetros deve ser maior ou igual a Faixa Inicial.");
                 		form.faixaFinal.focus();
                 	}
               	}else{
                	alert("Faixa Inicial e Faixa Final da Numeração dos Hidrômetros devem somente conter números positivos.");
                	form.faixaInicial.focus();
               	}
            }else{
            	if(form.faixaInicial.value != "" && form.faixaFinal.value != "" && form.fixo.value == "")
            	{
               		alert("O preenchimento do campo Fixo da Numeração dos Hidrômetros é obrigatório, caso Faixa Inicial e Faixa Final sejam informadas.");
               		form.fixo.focus();
               		return false;
               	}
               	if(form.faixaInicial.value != "" && form.faixaFinal.value == "" )
               	{
                	alert("O preenchimento do campo Faixa Final da Numeração dos Hidrômetros é obrigatório, caso Faixa Inicial seja informada.");
                	form.faixaFinal.focus();
                	return false;
               	}
               	if(form.faixaInicial.value == "" && form.faixaFinal.value != "" ){
                	alert("O preenchimento do campo Faixa Inicial da Numeração dos Hidrômetros é obrigatório, caso Faixa Final seja informada.");
                	form.faixaInicial.focus();
                	return false;
               	}
               	if(form.faixaInicial.value == "" && form.faixaFinal.value == "" && form.fixo.value != ""){
                	alert("O preenchimento dos campos Faixa Inicial e Final da Numeração dos Hidrômetros é obrigatório, caso Fixo seja informado.");
                	form.faixaInicial.focus();
                	return false;
               	}
               	submeterFormPadrao(form);
            }
         }else{
         	if(form.fixo.value != "" || form.faixaInicial.value != "" || form.faixaFinal.value != "")
         	{
            	alert('Preencher somente características ou numeração');
                return false;
            }else{
                submeterFormPadrao(form);
            }
        }
	}
}

//Verifica o valor do objeto radio em tempo de execução
function verificarMarcacao(valor){
  document.getElementById("indicadorMacromedidorAUX").value = valor;
}

function limparForm(){
	var form = document.HidrometroActionForm;
	    
	form.numeroHidrometro.value = "";
	form.dataAquisicao.value = "";
	form.anoFabricacao.value = "";
	form.indicadorMacromedidor[2].checked = true;
	form.idHidrometroClasseMetrologica.value = -1;
	form.idHidrometroMarca.value = -1;
	form.idHidrometroDiametro.value = -1;
	form.idHidrometroCapacidade.value = -1;
	form.idHidrometroTipo.value = -1;
	form.idLocalArmazenagem.value = "";
	form.localArmazenagemDescricao.value = "";
    form.idHidrometroSituacao.value = -1;
	form.fixo.value = "";
	form.faixaInicial.value = "";
	form.faixaFinal.value = "";
	form.numeroHidrometro.focus();
	form.idHidrometroTipoTurbina.value = -1;
}

	function atualizarSelecaoFormato(){
		var form = document.forms[0];
		var codigoFormatoNumeracao;
	
		for(var indice = 0; indice < form.elements.length; indice++){
			if (form.elements[indice].type == 'radio' && form.elements[indice].checked == true && form.elements[indice].name == "codigoFormatoNumeracao"){
				codigoFormatoNumeracao = form.elements[indice].value;
			}
		}

		limparForm();

		form.action = "/gsan/exibirFiltrarHidrometroAction.do?codigoFormatoNumeracao="+ codigoFormatoNumeracao;
		submeterFormPadrao(form);
	}
-->
</script>


</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('${requestScope.nomeCampo}');">
<html:form action="/filtrarHidrometroAction.do"
	name="HidrometroActionForm"
	type="gcom.gui.micromedicao.hidrometro.HidrometroActionForm"
	method="post" onsubmit="return validateHidrometroActionForm(this);">

	<input type="hidden" id="indicadorMacromedidorAUX" value="" />

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
					<td class="parabg">Filtrar Hidr&ocirc;metro</td>
					<td width="11" valign="top"><img
						src="<bean:message key="caminho.imagens"/>parahead_right.gif"
						border="0" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td>Para filtrar o(s) hidr&ocirc;metros(s), informe as
					caracter&iacute;sticas ou a numera&ccedil;&atilde;o dos
					hidr&ocirc;metros:</td>
					<td width="84"><input name="atualizar" type="checkbox"
						checked="checked" value="1"> <strong>Atualizar</strong></td>
					<td align="right"></td>
				</tr>
			</table>
			<table width="100%" border="0">

				<tr>
					<td colspan="2"><strong>Numeração dos Hidrômetros</strong></td>
				</tr>

				<tr>
					<td><strong>Formato da Numeração do Hidrômetro:</strong></td>
					<td>
				  		<html:radio property="codigoFormatoNumeracao" value="<%=Hidrometro.FORMATO_NUMERACAO_4_X_6.toString()%>" onclick="atualizarSelecaoFormato();"/><strong>4x6
						<html:radio property="codigoFormatoNumeracao" value="<%=Hidrometro.FORMATO_NUMERACAO_5_X_5.toString()%>" onclick="atualizarSelecaoFormato();"/>5x5</strong>
					</td>
				</tr>

				<tr>
					<td><strong>Fixo:</strong></td>
					<logic:equal name="HidrometroActionForm" property="codigoFormatoNumeracao" value="<%=Hidrometro.FORMATO_NUMERACAO_4_X_6.toString()%>">
						<td>
							<html:text maxlength="4" property="fixo" size="4" tabindex="12"/>
						</td>
					</logic:equal>
					<logic:equal name="HidrometroActionForm" property="codigoFormatoNumeracao" value="<%=Hidrometro.FORMATO_NUMERACAO_5_X_5.toString()%>">
						<td>
							<html:text maxlength="5" property="fixo" size="5" tabindex="12"/>
						</td>
					</logic:equal>
				</tr>

				<tr>
					<td><strong>Faixa:</strong></td>
					<logic:equal name="HidrometroActionForm" property="codigoFormatoNumeracao" value="<%=Hidrometro.FORMATO_NUMERACAO_4_X_6.toString()%>">
						<td>
							<html:text maxlength="6" property="faixaInicial" size="6" tabindex="13"/>
							<html:text maxlength="6" property="faixaFinal" size="6" tabindex="14"/>
						</td>
					</logic:equal>
					<logic:equal name="HidrometroActionForm" property="codigoFormatoNumeracao" value="<%=Hidrometro.FORMATO_NUMERACAO_5_X_5.toString()%>">
						<td>
							<html:text maxlength="5" property="faixaInicial" size="5" tabindex="13"/>
							<html:text maxlength="5" property="faixaFinal" size="5" tabindex="14"/>
						</td>
					</logic:equal>
				</tr>

				<tr>
					<td height="24" colspan="2" class="style1">
					<hr>
					</td>
				</tr>

				<tr>
					<td><strong>N&uacute;mero do Hidr&ocirc;metro:</strong></td>
					<td><html:text property="numeroHidrometro" size="10" maxlength="10" /></td>
				</tr>

				<tr>
					<td><strong>Capacidade:</strong></td>
					<td><html:select property="idHidrometroCapacidade" tabindex="9">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoHidrometroCapacidade"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>
				<tr>
					<td><strong>Ano de Fabrica&ccedil;&atilde;o:</strong></td>
					<td><html:text maxlength="4" property="anoFabricacao" size="4"
						tabindex="3"/>&nbsp;aaaa</td>
				</tr>
				<tr>
					<td><strong>Marca:</strong></td>
					<td><html:select property="idHidrometroMarca" tabindex="7">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoHidrometroMarca"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>

				<logic:equal name="HidrometroActionForm" property="codigoFormatoNumeracao" value="<%=Hidrometro.FORMATO_NUMERACAO_5_X_5.toString()%>">
				<tr>
					<td><strong>Tipo de Instalação da Turbina:</strong></td>
					<td>
						<html:select property="idHidrometroTipoTurbina">
							<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
							<html:options collection="colecaoHidrometroTipoTurbina" labelProperty="descricao" property="id" />
						</html:select>
					</td>
				</tr>
				</logic:equal>
				<logic:notEqual name="HidrometroActionForm" property="codigoFormatoNumeracao" value="<%=Hidrometro.FORMATO_NUMERACAO_5_X_5.toString()%>">
					<html:hidden property="idHidrometroTipoTurbina"/>
				</logic:notEqual>

				<tr>
					<td><strong>Data de Aquisi&ccedil;&atilde;o:</strong></td>
					<td><html:text property="dataAquisicao" size="10" maxlength="10"
						onkeyup="mascaraData(this,event)" tabindex="2" /> <a
						href="javascript:abrirCalendario('HidrometroActionForm', 'dataAquisicao')">
					<img border="0"
						src="<bean:message key="caminho.imagens"/>calendario.gif"
						width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
					dd/mm/aaaa</td>
				</tr>
				
				<tr>
					<td><strong>Finalidade:</strong></td>
					<td><html:radio tabindex="4" property="indicadorMacromedidor"
						value="<%=(Hidrometro.INDICADOR_COMERCIAL).toString()%>"
						onclick="verificarMarcacao(this.value)" /><strong>Comercial</strong>
					<html:radio tabindex="5" property="indicadorMacromedidor"
						value="<%=(Hidrometro.INDICADOR_OPERACIONAL).toString()%>"
						onclick="verificarMarcacao(this.value)" /><strong>Operacional</strong>

					<html:radio tabindex="6" property="indicadorMacromedidor"
						value="-1" /><strong>Todos</strong></td>
				</tr>
				<tr>
					<td><strong>Classe Metrológica:</strong></td>
					<td><html:select property="idHidrometroClasseMetrologica"
						tabindex="6">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoHidrometroClasseMetrologica"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>
				
				<tr>
					<td><strong>Diâmetro:</strong></td>
					<td><html:select property="idHidrometroDiametro" tabindex="8">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoHidrometroDiametro"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>
				
				<tr>
					<td><strong>Tipo:</strong></td>
					<td><html:select property="idHidrometroTipo" tabindex="10">
						<html:option value="-1">&nbsp;</html:option>
						<html:options collection="colecaoHidrometroTipo"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>
				<tr>
					<td><strong>Local de Armazenagem:</strong></td>
					<td><html:text property="idLocalArmazenagem" tabindex="11" size="4"
						maxlength="3"
						onkeypress="validaEnterComMensagem(event, 'exibirFiltrarHidrometroAction.do?objetoConsulta=1', 'idLocalArmazenagem', 'Local de Armazenagem');" />
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
					</logic:notPresent> <a
						href="javascript:limparPesquisaLocalArmazenagem();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
				<tr>
					<td><strong>Situação:</strong></td>
					<td>
						<html:select property="idHidrometroSituacao" tabindex="10">
						<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present name="colecaoHidrometroSituacao">
							<html:options collection="colecaoHidrometroSituacao" labelProperty="descricao" property="id" />
						</logic:present>
						</html:select>
					</td>
				</tr>

				<tr>
					<td><strong><font color="#ff0000"> <input name="Submit22"
						class="bottonRightCol" value="Limpar" type="button"
						onclick="limparForm();"> </font></strong></td>
					<td align="right">
						<gcom:controleAcessoBotao name="Button"
						value="Filtrar"
						onclick="javascript:validarForm(document.forms[0]);"
						url="filtrarHidrometroAction.do" /> <!-- <input type="button" class="bottonRightCol" tabindex="15" value="Filtrar" tabindex="13"
							onclick="validarForm(document.forms[0]);" /> --></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>
	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</body>
<!-- hidrometro_filtrar.jsp -->
</html:html>
