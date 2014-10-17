<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ page import="gcom.util.ConstantesSistema" isELIgnored="false"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>

<script language="JavaScript">
function habilitaComboAnormalidade(tipoAnormalidade){
	var form = document.LeituraConsumoActionForm;
	if(tipoAnormalidade.value == "-1"){
		form.anormalidadeLeituraInformadaFiltro.disabled = true;
		form.anormalidadeLeituraFaturadaFiltro.disabled = true;
		form.anormalidadeConsumoFiltro.disabled = true;

		form.anormalidadeLeituraInformadaFiltro.value = "-1";
		form.anormalidadeLeituraFaturadaFiltro.value = "-1";
		form.anormalidadeConsumoFiltro.value = "-1";
	}else
	if(tipoAnormalidade.value == "<%=""+ConstantesSistema.ANORMALIDADE_LEITURA_INFORMADA%>"){
		form.anormalidadeLeituraInformadaFiltro.disabled = false;
		form.anormalidadeLeituraFaturadaFiltro.disabled = true;
		form.anormalidadeConsumoFiltro.disabled = true;	
		
		form.anormalidadeLeituraFaturadaFiltro.value = "-1";
		form.anormalidadeConsumoFiltro.value = "-1";
	}else
	if(tipoAnormalidade.value == "<%=""+ConstantesSistema.ANORMALIDADE_LEITURA_FATURADA%>"){
		form.anormalidadeLeituraInformadaFiltro.disabled = true;
		form.anormalidadeLeituraFaturadaFiltro.disabled = false;
		form.anormalidadeConsumoFiltro.disabled = true;	

		form.anormalidadeLeituraInformadaFiltro.value = "-1";
		form.anormalidadeConsumoFiltro.value = "-1";
	}else
	if(tipoAnormalidade.value == "<%=""+ConstantesSistema.ANORMALIDADE_CONSUMO%>"){
		form.anormalidadeLeituraInformadaFiltro.disabled = true;
		form.anormalidadeLeituraFaturadaFiltro.disabled = true;
		form.anormalidadeConsumoFiltro.disabled = false;	

		form.anormalidadeLeituraInformadaFiltro.value = "-1";
		form.anormalidadeLeituraFaturadaFiltro.value = "-1";
	}
}
</script>

<script>
<!-- Begin

     var bCancel = false;

    function validateLeituraConsumoActionForm(form) {
        if (bCancel)
      return true;
        else
       return testarCampoValorZero(document.LeituraConsumoActionForm.consumoFaturamdoMinimoFiltro, 'Consumo Faturado Mínimo') 
       && testarCampoValorZero(document.LeituraConsumoActionForm.consumoMedidoMinimoFiltro, 'Consumo Medido Mínimo')
       && testarCampoValorZero(document.LeituraConsumoActionForm.consumoMedioMinimoFiltro, 'Consumo Médio') 
       && validateCaracterEspecial(form)
       && validateLong(form)
       && validateRequired(form);
   }

    function caracteresespeciais () {
     this.aa = new Array("consumoFaturamdoMinimoFiltro", "Consumo Faturado Mínimo possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ab = new Array("consumoMedidoMinimoFiltro", "Consumo Medido Mínimo possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ac = new Array("consumoMedioMinimoFiltro", "Consumo Médio possui caracteres especiais.", new Function ("varName", " return this[varName];"));
    }

    function IntegerValidations () {
     this.am = new Array("consumoFaturamdoMinimoFiltro", "Consumo Faturado Mínimo deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.an = new Array("consumoMedidoMinimoFiltro", "Consumo Medido Mínimo deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.ao = new Array("consumoMedioMinimoFiltro", "Consumo Médio deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    }
    
    function required () {
	 this.aj = new Array("idGrupoFaturamentoFiltro", "Informe Grupo de Faturamento.", new Function ("varName", " return this[varName];"));
	}
    
    function limparForm(){
    	var form = document.forms[0];
    	
    	form.consumoFaturamdoMinimoFiltro.value = "";
    	form.consumoMedidoMinimoFiltro.value = "";
    	form.consumoMedioMinimoFiltro.value = "";
    	form.tipoAnormalidadeFiltro.value = "-1";
    	form.anormalidadeLeituraInformadaFiltro.value = "-1";
    	form.anormalidadeLeituraFaturadaFiltro.value = "-1";
    	form.anormalidadeConsumoFiltro.value = "-1";
    	form.leituraSituacaoAtualFiltro.value = "";
    	habilitaComboAnormalidade(document.LeituraConsumoActionForm.tipoAnormalidadeFiltro);
    }

//End -->
</script>


<SCRIPT LANGUAGE="JavaScript">
<!--
function validarForm(form){
	// Confirma a validação do formulário
	if (validateLeituraConsumoActionForm(form)){
		redirecionarSubmit("/gsan/filtrarExcecoesLeiturasConsumosAction.do?nomeCaminhoMapping=efetuarAnaliseExcecoesLeiturasConsumos");
	}

}
//-->
</SCRIPT>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="LeituraConsumoActionForm" dynamicJavascript="false" />

</head>

<body leftmargin="5" topmargin="5" onload="javascript:habilitaComboAnormalidade(document.LeituraConsumoActionForm.tipoAnormalidadeFiltro);setarFoco('${requestScope.nomeCampo}');">


<html:form 
  name="LeituraConsumoActionForm"
  type="gcom.gui.micromedicao.LeituraConsumoActionForm"
  action="/filtrarExcecoesLeiturasConsumosWizardAction"
  method="post">
  
<jsp:include page="/jsp/util/wizard/navegacao_abas_wizard_filtro.jsp?numeroPagina=3"/>


<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

<table width="770" border="0" cellspacing="4" cellpadding="0">
	
  <tr>
    <td width="149" valign="top" class="leftcoltext">
    
    	<input type="hidden" name="numeroPagina" value="3"/>
      
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

	<td width="625" valign="top" class="centercoltext">

	  <table height="100%">
        <tr>
          <td></td>
        </tr>
      </table>

      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
          <td class="parabg">Filtrar Exceções de Leituras e Consumos</td>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
        </tr>
      </table>
      <p>&nbsp;</p>
      <html:hidden property="idGrupoFaturamentoFiltro"/>
<table width="100%" border="0" >
<tr>
<td align="right"></td>
</tr>
</table>
      <table width="100%" border="0" >
        <tr>
            <td><strong>Tipo de Anormalidade:</strong></td>
            <td><html:select property="tipoAnormalidadeFiltro" onchange="javascript:habilitaComboAnormalidade(this);">
			 		<html:option value="-1">&nbsp;</html:option>
                	<html:option value="<%=""+ConstantesSistema.ANORMALIDADE_LEITURA_INFORMADA%>" >ANORMALIDADE DE LEITURA INFORMADA</html:option>
                	<html:option value="<%=""+ConstantesSistema.ANORMALIDADE_LEITURA_FATURADA%>" >ANORMALIDADE DE LEITURA FATURADA</html:option>
                	<html:option value="<%=""+ConstantesSistema.ANORMALIDADE_CONSUMO%>" >ANORMALIDADE DE CONSUMO</html:option>
                </html:select>
            </td>
        </tr>
        <tr>
            <td><strong>Anormalidade de Leitura Informada:</strong></td>
            <td><html:select property="anormalidadeLeituraInformadaFiltro">
			 		<html:option value="-1">&nbsp;</html:option>
                	<html:options collection="leituraAnormalidades" labelProperty="descricao" property="id"/>
                </html:select>
            </td>
        </tr>
        <tr>
            <td><strong>Anormalidade de Leitura Faturada:</strong></td>
            <td><html:select property="anormalidadeLeituraFaturadaFiltro">
			 		<html:option value="-1">&nbsp;</html:option>
                	<html:options collection="leituraAnormalidades" labelProperty="descricao" property="id"/>
                </html:select>
            </td>
        </tr>
        <tr>
            <td><strong>Anormalidade de Consumo:</strong></td>
            <td><html:select property="anormalidadeConsumoFiltro">
			 		<html:option value="-1">&nbsp;</html:option>
                	<html:options collection="consumoAnormalidades" labelProperty="descricao" property="id"/>
                </html:select>
            </td>
        </tr>
        <tr>
            <td><strong>Consumo Faturado Mínimo:</strong></td>
            <td><html:text maxlength="6" property="consumoFaturamdoMinimoFiltro" size="6" tabindex="8"/></td>
        </tr>
        <tr>
            <td><strong>Consumo Medido Mínimo:</strong></td>
            <td><html:text maxlength="6" property="consumoMedidoMinimoFiltro" size="6" tabindex="8"/></td>
        </tr>
        <tr>
            <td><strong>Consumo Médio:</strong></td>
            <td><html:text maxlength="6" property="consumoMedioMinimoFiltro" size="6" tabindex="8"/></td>
        </tr>
        
        <tr>
            <td><strong>Situação da leitura atual:</strong></td>
            <td><html:select property="leituraSituacaoAtualFiltro">
			 		<html:option value="-1">&nbsp;</html:option>
                	<html:options collection="colecaoLeituraSituacaoAtual" labelProperty="descricao" property="id"/>
                </html:select>
            </td>
        </tr>        
	        <tr>
                <td colspan="2">
                	<div align="right">
                		<jsp:include page="/jsp/util/wizard/navegacao_botoes_wizard_filtro.jsp?numeroPagina=3"/>
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
</html:html>