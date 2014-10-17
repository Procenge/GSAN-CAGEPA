<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="ManterImovelActionForm" dynamicJavascript="false" />
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
	<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/engine.js'> </script>
	<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/util.js'> </script>
	<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/interface/AjaxService.js'> </script>	
<script>
function required () {
	this.aa = new Array("idLocalidade", "Informe Localidade.", new Function ("varName", " return this[varName];"));
    this.ab = new Array("idSetorComercial", "Informe Setor Comercial.", new Function ("varName", " return this[varName];"));
    this.ac = new Array("cdRota", "Informe Rota.", new Function ("varName", " return this[varName];"));
    this.ad = new Array("lote", "Informe Lote.", new Function ("varName", " return this[varName];"));
    this.ae = new Array("subLote", "Informe Sublote.", new Function ("varName", " return this[varName];"));
    this.af = new Array("idQuadra", "Informe Quadra.", new Function ("varName", " return this[varName];"));
}


function caracteresespeciais () {
    this.aa = new Array("idLocalidade", "Localidade deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    this.ab = new Array("idSetorComercial", "Setor Comercial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    this.ac = new Array("idQuadra", "Quadra deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    this.ad = new Array("lote", "Lote deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    this.ae = new Array("subLote", "Sublote deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    this.af = new Array("testadaLote", "Testada de Lote deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    this.ag = new Array("cdRota", "Rota deve somente conter números positivos.", new Function ("varName", " return this[varName];"));   
    this.ai = new Array("sequencialRota", "Sequência na Rota possui caracteres especiais.", new Function ("varName", " return this[varName];"));
}

function IntegerValidations () {
	this.aa = new Array("idLocalidade", "Localidade deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    this.ab = new Array("idSetorComercial", "Setor Comercial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    this.ac = new Array("idQuadra", "Quadra deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    this.ad = new Array("cdRota", "Rota deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     
}

function InteiroZeroPositivoValidations () {
	this.aa = new Array("subLote", "SubLote deve somente conter números positivos ou zero.", new Function ("varName", " return this[varName];"));
    this.ab = new Array("lote", "Lote deve somente conter números positivos ou zero.", new Function ("varName", " return this[varName];"));
}


function carregaRota() {
	 var idRegistro = document.forms[0].rotaHelper.value;          
	 var descricaoRota = obterRota(idRegistro);		
	 document.forms[0].descricaoRota.value = descricaoRota;
	 document.forms[0].idRota.value = idRegistro;	
	 if(document.forms[0].cdRota.value == ''){
   	    document.forms[0].cdRota.value = form.cdRotaHelper.value;
     }     
     form.action='atualizarImovelWizardAction.do?action=exibirAtualizarImovelLocalidadeAction&pesquisaRota=2';   
     form.submit();
}


function obterRota(idRegistro){

	var descricaoRota = "";
	
	AjaxService.obterRota(idRegistro, {callback: 
		function(id) {

		descricaoRota = id;	
		
		}, async: false} 
	);

	return descricaoRota;
	
}



function checa_proximo(campo){
	
    var form = document.forms[0];
	switch (campo){
		case "idLocalidade" : {
			if(form.idLocalidade.value.length == 3){
				form.idSetorComercial.focus();
			}	
			break;
		}
		case "idSetorComercial" : {
			if(form.idSetorComercial.value.length == 3){
				form.idQuadra.focus();
			}	
			break;
		}
		case "idQuadra" : {
			if(form.idQuadra.value.length == 4){
				form.lote.focus();
			}	
			break;
		}		
		case "lote" : {
			if(form.lote.value.length == 4){
				form.subLote.focus();
			}	
			break;
		}
		case "subLote" : {
			if(form.subLote.value.length == 3){
				form.cdRota.focus();
			}	
			break;
		}
		case "cdRota" : {
			if(form.cdRota.value.length == 5){
				form.nnSegmento.focus();
			}	
			break;
		}
		case "nnSegmento" : {
			if(form.nnSegmento.value.length == 2){
				form.sequencialRota.focus();
			}	
			break;
		}
	}	
}

</script>

<script>
<!-- Begin

     var bCancel = false;

    function validateManterImovelActionForm(form) {
        if (bCancel)
      return true;
        else
       return validateRequired(form)
              && testarCampoValorZero(document.ManterImovelActionForm.idLocalidade, 'Localidade') 
              && testarCampoValorZero(document.ManterImovelActionForm.idSetorComercial, 'Setor Comercial')
              && testarCampoValorZero(document.ManterImovelActionForm.idQuadra, 'Quadra')
		 	  && testarCampoValorZero(document.ManterImovelActionForm.testadaLote, 'Testada do Lote') 
  	          && validateCaracterEspecial(form) 
  	          && validateLong(form) && validateInteiroZeroPositivo(form);
   }
 
//End -->
</script>


<script>
/*function pesquisaEnter(tecla) {

  var form = document.forms[0];

  if (document.all) {
    var codigo = event.keyCode;
  } else {
    var codigo = tecla.which;
  }
  if(form.idLocalidade.value != '' || form.idSetorComercial.value != ''
     || form.idQuadra.value != ''){
    if (codigo == 13) {
      form.action = "exibirInserirImovelLocalidadeAction.do";
      form.submit();
    } else {
      return true;
    }
  }
}*/

function recuperarDadosQuatroParametros(idRegistro, descricaoRegistro, codigoRegistro, tipoConsulta) {

	var form = document.forms[0];
	if (tipoConsulta == 'rota') {
		limparPesquisaRota();
		form.cdRota.value = codigoRegistro;
		form.descricaoRota.value = descricaoRegistro;
		form.descricaoRota.style.color = "#000000";
		form.nnSegmento.focus();

	     form.action='atualizarImovelWizardAction.do?action=exibirAtualizarImovelLocalidadeAction&pesquisaRota=1';   
	     form.submit();
	
	}
}

//Recebe os dados do(s) popup(s)
function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta, rotaID, localidadeID, setorComercialCD, rotaCD) {

    var form = document.forms[0];
    if (tipoConsulta == 'localidade') {
      if(form.idLocalidade.value != codigoRegistro){
        limparSetorComercialQuadra()
      }
      limparPesquisaLocalidade();
      form.idLocalidade.value = codigoRegistro;
      form.localidadeDescricao.value = descricaoRegistro;
      form.localidadeDescricao.style.color = "#000000";
      
      form.idSetorComercial.focus();
    }

    if (tipoConsulta == 'quadra') {   	

      limparPesquisaQuadra();
      form.idQuadra.value = codigoRegistro;
      form.lote.focus();
      form.action='atualizarImovelWizardAction.do?action=exibirAtualizarImovelLocalidadeAction&pesquisaQuadra=1';   
      form.submit();
   
   
    }

    if (tipoConsulta == 'setorComercial') {
      if(form.idSetorComercial.value != codigoRegistro){
		limparPesquisaQuadra();
      }
      limparPesquisaSetorComercial();
      form.idSetorComercial.value = codigoRegistro;
      form.setorComercialDescricao.value = descricaoRegistro;
      form.setorComercialDescricao.style.color = "#000000";      
      form.idQuadra.focus();
    }
    if (tipoConsulta == 'rota') {      
    	
    	limparPesquisaRota();
    	form.cdRota.value = codigoRegistro;
    	form.descricaoRota.value = descricaoRegistro;
    	form.nnSegmento.focus();

        form.action='atualizarImovelWizardAction.do?action=exibirAtualizarImovelLocalidadeAction&pesquisaRota=2';   
        form.submit();
    
    }
  }

function limparPesquisaLocalidade() {
    var form = document.forms[0];

      form.idLocalidade.value = "";
      form.localidadeDescricao.value = "";


  }

function limparPesquisaQuadra() {
    var form = document.forms[0];

  form.idQuadra.value = "";
	var msgQuadra = document.getElementById("msgQuadra");
	
	if (msgQuadra != null){
		msgQuadra.innerHTML = "";
	}

  }

function limparPesquisaDescricaoRota() {
    var form = document.forms[0];
    form.idRota.value = "";   
    form.descricaoRota.value = "";
}



function limparPesquisaRota() {
    var form = document.forms[0];
   	  form.idRota.value = "";   
      form.descricaoRota.value = "";
      form.action='atualizarImovelWizardAction.do?action=exibirAtualizarImovelLocalidadeAction&pesquisaRota=2';   
      form.submit();

  }




function limparPesquisaSetorComercial() {
    var form = document.forms[0];

    form.idSetorComercial.value = "";
    form.setorComercialDescricao.value = "";
}

function limparSetorComercialQuadra(){
  var form = document.forms[0];

  form.idSetorComercial.value = "";
  form.setorComercialDescricao.value = "";

  form.idQuadra.value = "";
	var msgQuadra = document.getElementById("msgQuadra");
	
	if (msgQuadra != null){
		msgQuadra.innerHTML = "";
	}
}

function limparDescricaoQuadra(){
	var msgQuadra = document.getElementById("msgQuadra");
	
	if (msgQuadra != null){
		msgQuadra.innerHTML = "";
	}
}

function limparDescricaoLocalidade(){
    var form = document.forms[0];
    form.localidadeDescricao.value = "";

}
function limparDescricaoSetorComercial(){
    var form = document.forms[0];
    form.setorComercialDescricao.value = "";


}

function valorExistenciaLocalidade(){
	
   var form = document.forms[0];
   if(form.idLocalidade.value == ''){
   		form.idSetorComercial.value = "";
  		alert("Informe Localidade") ;
   }
}  
function valorExistenciaSetorComercial(){
   var form = document.forms[0];
   if(form.idSetorComercial.value == ''){
   		form.idQuadra.value = "";
  		alert("Informe Setor Comercial") ;
   }
}  


function limparPesquisaLocalidade() {
    var form = document.forms[0];

      form.idLocalidade.value = "";
      form.localidadeDescricao.value = "";


  }
function limparPesquisaSetorComercial() {
    var form = document.forms[0];

      form.idSetorComercial.value = "";
      form.setorComercialDescricao.value = "";


  }
function limparPesquisaQuadra() {
    var form = document.forms[0];

      form.idQuadra.value = "";

	var msgQuadra = document.getElementById("msgQuadra");
	
	if (msgQuadra != null){
		msgQuadra.innerHTML = "";
	}

  }

function limparPesquisaDescricaoLocalidade() {
    var form = document.forms[0];
      form.localidadeDescricao.value = "";
  }

function limparDescricaoRota(){
    var form = document.forms[0];
    form.idRota.value = "";
    form.cdRota.value = "";
    form.descricaoRota.value = "";
}


</script>
</head>

<body leftmargin="5" topmargin="5" onload="javascript:setarFoco('${requestScope.nomeCampo}');">
<div id="formDiv">
<html:form action="/atualizarImovelWizardAction" method="post" onsubmit="return validateManterImovelActionForm(this);setarFoco('${requestScope.nomeCampo}');">

<jsp:include page="/jsp/util/wizard/navegacao_abas_wizard_valida_avancar.jsp?numeroPagina=1"/>


<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

<table width="770" border="0" cellspacing="4" cellpadding="0">
<input type="hidden" name="numeroPagina" value="1"/>
<html:hidden property="url" value="1" />
<html:hidden property="idRota"/>
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
      </div></td>
    <td width="660" valign="top" class="centercoltext">
      <table height="100%">
        <tr>
          <td></td>
        </tr>
      </table>
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_left.gif"/></td>
          <td class="parabg">Atualizar Imóvel</td>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
        </tr>
      </table>
      <p>&nbsp;</p>
            <table width="100%" border="0">
              <tr>
                <td colspan="2">Para atualizar um im&oacute;vel, informe os dados abaixo:</td>
                <td align="right"></td>
              </tr>
              </table>
<!-- **************************************************** -->
<table width="100%" border="0">
				<tr>
				  <td><strong>Matrícula:</strong></td>	
				  <td>
					<html:hidden property="matricula"/>
					<bean:write name="ManterImovelActionForm" property="matricula"/>
					  &nbsp;&nbsp;<span style="color:#ff0000" id="msgAlteracaoInscrPendente"><bean:write name="ManterImovelActionForm" property="msgAlteracaoInscrPendente"/></span>					
		  		  </td>				
				</tr>
                 <tr>
		   <td width="19%"><strong>Localidade:<font color="#FF0000">*</font></strong></td>
                   <td width="81%" height="24"><html:text maxlength="3" property="idLocalidade" tabindex="1" size="3" 
					 onkeyup="checa_proximo(this.name);"
                   onkeypress="javascript:limparPesquisaDescricaoLocalidade();limparPesquisaQuadra();limparPesquisaSetorComercial(); limparDescricaoRota();return validaEnter(event, 'atualizarImovelWizardAction.do?action=exibirAtualizarImovelLocalidadeAction', 'idLocalidade');"/>
                      <a href="javascript:abrirPopup('exibirPesquisarLocalidadeAction.do?tipo=imovelLocalidade', 400, 800);limparPesquisaSetorComercialQuadra();">
                         <img border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/></a>

   				      <logic:present name="codigoLocalidadeNaoEncontrada" scope="request">
							<html:text property="localidadeDescricao" size="50" readonly="true" 
							style="background-color:#EFEFEF; border:0; color: #ff0000" 
							
							/>
                      </logic:present>


                      <logic:notPresent name="codigoLocalidadeNaoEncontrada" scope="request">
                        <html:text property="localidadeDescricao" size="50" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
                      </logic:notPresent>
						<a href="javascript:limparPesquisaLocalidade();limparPesquisaSetorComercial();limparPesquisaQuadra();limparDescricaoRota();document.forms[0].idLocalidade.focus();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a>                   
						</td>
                   </td>
                 </tr>
                 <tr>
                   <td><strong>Setor Comercial:<font color="#FF0000">*</font></strong></td>
                   <td height="24"><html:text maxlength="3" property="idSetorComercial" size="3" tabindex="2" 
                    onkeyup="checa_proximo(this.name);"
                   onkeypress="javascript:valorExistenciaLocalidade();limparDescricaoSetorComercial();limparPesquisaQuadra(); limparDescricaoRota(); return validaEnterDependencia(event, 'atualizarImovelWizardAction.do?action=exibirAtualizarImovelLocalidadeAction', this, document.forms[0].idLocalidade.value, 'Localidade');"/>
                      <a href="javascript:abrirPopupDependencia('exibirPesquisarSetorComercialAction.do?idLocalidade='+document.forms[0].idLocalidade.value+'&tipo=SetorComercial',document.forms[0].idLocalidade.value,'Localidade', 400, 800);">
			<img border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/></a>
   		      <logic:present name="codigoSetorComercialNaoEncontrada" scope="request">
			<html:text property="setorComercialDescricao" size="50" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000" />
                      </logic:present>

                      <logic:notPresent name="codigoSetorComercialNaoEncontrada" scope="request">
                        <html:text property="setorComercialDescricao" size="50" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
                      </logic:notPresent>
						<a href="javascript:limparPesquisaSetorComercial();limparPesquisaQuadra(); limparDescricaoRota();document.forms[0].idSetorComercial.focus();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a>
                   </td>
                 </tr>
                 <tr>
                   <td><strong>Quadra:<font color="#FF0000">*</font></strong></td>
                   <td height="24"><html:text maxlength="5" property="idQuadra" size="4" tabindex="3" 
                    onkeyup="checa_proximo(this.name);" 
                   onkeypress="javascript:valorExistenciaSetorComercial();limparDescricaoQuadra(); limparDescricaoRota(); return validaEnterDependencia(event, 'atualizarImovelWizardAction.do?action=exibirAtualizarImovelLocalidadeAction&pesquisaQuadra=1', this, document.forms[0].idSetorComercial.value, 'Setor Comercial');"/>
					 <a href="javascript:abrirPopupDependencia('exibirPesquisarQuadraAction.do?idLocalidade='+document.forms[0].idLocalidade.value+'&codigoSetorComercial='+document.forms[0].idSetorComercial.value+'&tipo=Quadra&retornarSeteParametros=S',document.forms[0].idSetorComercial.value,'Setor Comercial', 400, 800);">
				          	<img border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/>
				          </a>                 
						  
					 
					 <logic:present name="codigoQuadraNaoEncontrada" scope="request">
						<span style="color:#ff0000" id="msgQuadra"><bean:write scope="request" name="msgQuadra"/></span>
                      </logic:present>

                      <logic:notPresent name="codigoQuadraNaoEncontrada" scope="request">

                      </logic:notPresent>  
                      <a href="javascript:limparPesquisaQuadra();limparDescricaoRota();document.forms[0].idQuadra.focus();"> <img
							 src="<bean:message key="caminho.imagens"/>limparcampo.gif"
							 border="0" title="Apagar" />
						 </a>
                   </td>
                 </tr>		             
<!-- **************************************************** -->
              <tr>
                <td height="24"><strong>Lote:<font color="#FF0000">*</font></strong></td>
                <td><html:text maxlength="4" property="lote" tabindex="4" size="4"  onkeyup="checa_proximo(this.name);" /></td>
              </tr>
              <tr>
                <td height="24"><strong>Sublote:<font color="#FF0000">*</font></strong></td>
                <td><html:text maxlength="3" property="subLote" tabindex="5" size="3"  onkeyup="checa_proximo(this.name);" /></td>
              </tr>
              <tr>
                <td height="24"><strong>Testada do Lote:</strong></td>
                <td><html:text maxlength="4" property="testadaLote" tabindex="6" size="4"/></td>
              </tr>

				<tr>
					<td height="24"><strong>Rota:<font color="#FF0000">*</font></strong></td>
					<td width="81%" height="24" colspan="2">
					<html:text maxlength="5" property="cdRota" size="5" tabindex="7"
						onkeyup="checa_proximo(this.name);"
						onkeypress="javascript:limparPesquisaDescricaoRota(); return validaEnter(event, 'atualizarImovelWizardAction.do?action=exibirAtualizarImovelLocalidadeAction&pesquisaRota=1', 'cdRota');" />
					<a href="javascript:abrirPopupDependencia('exibirPesquisarRotaAction.do?idLocalidade='+document.forms[0].idLocalidade.value+'&codigoSetorComercial='+document.forms[0].idSetorComercial.value+'&restringirPesquisa=true',document.forms[0].idSetorComercial.value,'Setor Comercial', 400, 800);">
					<img border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/></a>

					<logic:present name="rotaNaoEncontrada" scope="request">
						<html:text property="descricaoRota" size="55" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
					</logic:present> 
					
					<logic:notPresent name="rotaNaoEncontrada" scope="request">
						<html:text property="descricaoRota" size="55" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
					</logic:notPresent>
					
					<a href="javascript:limparDescricaoRota(); document.forms[0].cdRota.focus();">
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif" border="0" title="Apagar"/></a>
					</td>
				</tr>

			
				
				<logic:present name="colecaoRotas" scope="session">
				<tr>
					<td width="16%"><strong>&nbsp;</td>
					<td colspan="3">
					
					<html:select property="rotaHelper" tabindex="4" size="6" style="width:230px height:230px" onclick="carregaRota();" >					
						<html:options collection="colecaoRotas" labelProperty="descricao" property="id" />
					</html:select>
					
						
					</td>
				</tr>
				</logic:present>


				<logic:notPresent name="colecaoRotas" scope="request">
						<tr>
					    <td width="16%">&nbsp; </td>
					
			        	</tr>
				</logic:notPresent>

				
				<tr>
					<td height="24"><strong>Segmento:</strong></td>
					<td colspan="2"><html:text maxlength="2"
						property="nnSegmento" size="2" tabindex="8"
						onkeyup="checa_proximo(this.name);" /></td>
				</tr>
				<tr>
					<td height="24"><strong>Sequência na Rota:</strong></td>
					<td><html:text maxlength="4" size="4" property="sequencialRota" tabindex="9" /></td>
				</tr>
				
				<!-- Distrito Operacional -->
				<tr>
			  		<td  width="40%"class="style3">
			  			<strong>
			  				Distrito Operacional:
			  			</strong>
			  		</td>
			  		<td  width="60%" colspan="2">
			  			<html:select property="idDistritoOperacional" tabindex="4" style="width:200px;">
							<html:option value="-1"> &nbsp; </html:option>
							<html:options collection="colecaoDistritoOperacional" property="id" labelProperty="descricaoComId"/>
						</html:select>
					</td>
				</tr>
				
				
				<tr>
					<td height="24">&nbsp;</td>
					<td><strong><font color="#FF0000">*</font></strong> Campo
					obrigat&oacute;rio</td>
				</tr>
				<tr>
                <td height="0" colspan="2">
                  <div align="right"> </div></td>
               </tr>
               <tr>
                 <td colspan="2">
					<div align="right">
						<jsp:include page="/jsp/util/wizard/navegacao_botoes_wizard_valida_avancar.jsp?numeroPagina=1"/>
					</div>
				</td>
               </tr>
            </table>
      <p>&nbsp;</p>
    </td>
  </tr>
</table>

<%@ include file="/jsp/util/rodape.jsp"%>
<%@ include file="/jsp/util/tooltip.jsp" %>

</html:form>
</div>
</body>

<%@ include file="/jsp/util/telaespera.jsp"%>

<script>
document.getElementById('botaoConcluir').onclick = function() { botaoAvancarTelaEspera('/gsan/atualizarImovelWizardAction.do?concluir=true&action=atualizarImovelLocalidadeAction'); }
</script>

</html:html>
