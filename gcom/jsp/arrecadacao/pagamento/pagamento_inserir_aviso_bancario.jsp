<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="PagamentoActionForm" dynamicJavascript="false" />
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>
<script>
<!--

    var bCancel = false;

    function validatePagamentoActionForm(form) {
        if (bCancel)
      return true;
        else
       return  validaRequiredAvisoBancario() && validateDate(form) && verificaDataPagamento(form);
   }

    
    function required () {
     this.aa = new Array("idAvisoBancario", "Informe Aviso Bancário.", new Function ("varName", " return this[varName];"));
     this.ab = new Array("dataPagamento", "Informe Data do Pagamento.", new Function ("varName", " return this[varName];"));
     this.ac = new Array("idFormaArrecadacao", "Informe Forma de Arrecadação.", new Function ("varName", " return this[varName];"));
     this.ad = new Array("tipoInclusao", "Informe Tipo de Inclusão.", new Function ("varName", " return this[varName];"));
    
    }

	function DateValidations () { 
     this.aa = new Array("dataPagamento", "Data do Pagamento inválida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
    } 
-->
</script>
<script>
<!--

	function validaRequiredAvisoBancario () {
		var form = document.forms[0];
		var msg = '';

		 if(form.idAvisoBancario.value == null || form.idAvisoBancario.value == "") {
			msg = 'Informe Aviso Bancário.\n';
		}
		
		if(form.dataPagamento.value == null || form.dataPagamento.value == "") {
			msg = msg + 'Informe Data do Pagamento.\n';
		}

		if(form.idFormaArrecadacao.value == null || form.idFormaArrecadacao.value == ""
		|| form.idFormaArrecadacao.value == "-1") {
			msg = msg + 'Informe Forma de Arrecadação.\n';
		}
		
		if(!form.tipoInclusao[0].checked && !form.tipoInclusao[1].checked){
			msg = msg + 'Informe Tipo de Inclusão.';
		}
		
		
		if( msg != '' ){
			alert(msg);
			return false;
		}else{
			return true;
		}
	}





 function recuperarDadosCincoParametros(codigoRegistro, descricaoRegistro1, descricaoRegistro2, descricaoRegistro3, tipoConsulta) { 
	var form = document.forms[0];
	
 	if (tipoConsulta == 'avisoBancario') {
		form.codigoAgenteArrecadador.value = descricaoRegistro1;
		form.dataLancamentoAviso.value = descricaoRegistro2;
		form.numeroSequencialAviso.value = descricaoRegistro3;
		form.idAvisoBancario.value = codigoRegistro;
	}
}
 
function limparAvisoBancario() {
	var form = document.PagamentoActionForm;
		form.codigoAgenteArrecadador.value = "";
		form.dataLancamentoAviso.value = "";
		form.numeroSequencialAviso.value = "";
		form.idAvisoBancario.value = "";
}

function verificaDataPagamento(form){
  var DATA_ATUAL = document.getElementById("DATA_ATUAL").value;
  
  if (comparaData(form.dataPagamento.value, ">", DATA_ATUAL)){
	alert('Data do Pagamento posterior à data corrente ' + DATA_ATUAL);
  } else{
    return true;
  }
}
-->
</script>
</head>

<body leftmargin="5" topmargin="5">

<html:form
    action="/inserirPagamentosWizardAction"
    method="post"
    onsubmit="return validatePagamentoActionForm(this);"
>

<jsp:include page="/jsp/util/wizard/navegacao_abas_wizard_valida_avancar.jsp?numeroPagina=1"/>

<input type="hidden" name="numeroPagina" value="1"/>
<input type="hidden" id="DATA_ATUAL" value="${requestScope.dataAtual}"/>
	
	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp" %>

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
	    <td width="625" valign="top" class="centercoltext">
	      <table height="100%">
	        <tr>
	          <td></td>
	        </tr>
	      </table>

      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
          <td class="parabg">Inserir Pagamentos</td>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
        </tr>
      </table>
      <p>&nbsp;</p>
      <table width="100%" border="0" dwcopytype="CopyTableRow">
        <tr>
          <td colspan="4">
            Para inserir o pagamento, informe os dados abaixo:
          </td>
        </tr>
        <tr>
			<td width="18%">
			  <strong>Aviso Bancário:<font color="#FF0000">*</font></strong>
			</td>
                         
			<td colspan="1">
				<strong> 
				 <html:text property="codigoAgenteArrecadador" size="3" maxlength="3" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
				 <html:text property="dataLancamentoAviso" size="10" maxlength="10" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
				 <html:text property="numeroSequencialAviso" size="3" maxlength="3" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
				 <a href="javascript:abrirPopup('exibirPesquisarAvisoBancarioAction.do?limparForm=1', 475, 765);">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Aviso Bancário" /></a>
				<a href="javascript:limparAvisoBancario();">
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
							border="0" title="Apagar" /> </a>
				</strong>
				<html:hidden property="idAvisoBancario"/>
			</td>
		  	<td colspan="2" style="text-align:right;">
				<a href="javascript:abrirPopup('exibirInserirAvisoBancarioAction.do?montarPopUp=true',400,800);">
					<strong>Inserir Aviso Bancário</strong>
				</a>
			</td>
        </tr>
        
        <tr>
		  <td width="170"><strong>Data do Pagamento:<font color="#FF0000">*</font></strong></td>
		  <td colspan="3">
		  
		    <strong> 
			  <html:text maxlength="10" property="dataPagamento" size="10" onkeypress="javascript:mascaraData(this,event);"/>

			  <a href="javascript:abrirCalendario('PagamentoActionForm', 'dataPagamento')">
				<img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif"
				width="20" border="0" align="absmiddle" alt="Exibir Calendário" /></a>
			</strong>
 		  </td>
		</tr>
        
        <tr>
          <td>
            <strong>Forma de Arrecadação:<font color="#FF0000">*</font></strong>
          </td>
          <td colspan="3">
            <html:select property="idFormaArrecadacao" style="width: 450px">
              <html:option value="-1">&nbsp;</html:option> 
			  <html:options collection="colecaoFormaArrecadacao" labelProperty="descricao" property="id"/>
            </html:select>
          </td>
        </tr>
        <tr>
          <td>
            <strong>Tipo de Inclusão:<font color="#FF0000">*</font></strong>
          </td>
          <td  colspan="3">
            <html:radio property="tipoInclusao" value="manual"><strong>Manual</strong></html:radio>
            <html:radio property="tipoInclusao" value="caneta"><strong>Código de Barras</strong></html:radio>
          </td>
        </tr>
        <tr>
          <td>
            <strong>  </strong>
          </td>
          <td  colspan="3">
            <strong> <font color="#FF0000">*</font> </strong> Campo obrigat&oacute;rio
          </td>
        </tr>
        <tr>
          <td colspan="3"><div align="right"><jsp:include page="/jsp/util/wizard/navegacao_botoes_wizard_valida_avancar.jsp?numeroPagina=1"/></div></td>
        </tr>
      </table>
      <p>&nbsp;</p>
    </td>
  </tr>

</table>

<%@ include file="/jsp/util/rodape.jsp"%>

</body>
</html:form>
<!-- pagamento_inserir_aviso_bancario.jsp -->
</html:html>
