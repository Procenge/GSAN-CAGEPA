<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="InserirImovelActionForm" dynamicJavascript="false" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
	
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
    this.af = new Array("sequencialRota", "Sequência na Rota deve somente conter dígitos.", new Function ("varName", " return this[varName];"));
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
			if(form.idQuadra.value.length == 5){
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

function recuperarDadosQuatroParametros(idRegistro, descricaoRegistro, codigoRegistro, tipoConsulta) {
	var form = document.forms[0];

	if (tipoConsulta == 'rota') {
		//limparPesquisaRota();
		form.cdRota.value = codigoRegistro;
		form.descricaoRota.value = descricaoRegistro;
		form.descricaoRota.style.color = "#000000";
		form.nnSegmento.focus();
		 form.action='inserirImovelWizardAction.do?action=exibirInserirImovelLocalidadeAction&pesquisaRota=2';   
		 form.submit();
	}
}

//Recebe os dados do(s) popup(s)

function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta, rotaID, localidadeID, setorComercialCD, rotaCD) {

  var form = document.forms[0];

  if (tipoConsulta == 'localidade') {
    limparPesquisaLocalidade();
    form.idLocalidade.value = codigoRegistro;
    form.localidadeDescricao.value = descricaoRegistro;
    form.localidadeDescricao.style.color = "#000000";
    form.idSetorComercial.focus();
  }

  if (tipoConsulta == 'quadra') {
    limparPesquisaQuadra();
    form.idQuadra.value = codigoRegistro;
   // form.cdRota.value = rotaCD;
    form.lote.focus();
    //form.descricaoRota.value = 'Localidade:' + localidadeID + '; Setor Comercial:' + setorComercialCD + '; Cód. Rota:' + rotaCD;
    form.action='inserirImovelWizardAction.do?action=exibirInserirImovelLocalidadeAction&pesquisaQuadra=1';   
	form.submit();
   
  }

  if (tipoConsulta == 'setorComercial') {
    limparPesquisaSetorComercial();
    form.idSetorComercial.value = codigoRegistro;
    form.setorComercialDescricao.value = descricaoRegistro;
    form.setorComercialDescricao.style.color = "#000000";
    form.cdRota.focus();
  }

  if (tipoConsulta == 'rota') {	
	form.cdRota.value = codigoRegistro;
	form.cdRotaHelper.value = codigoRegistro;
	form.descricaoRota.value = descricaoRegistro;
	form.descricaoRota.style.color = "#000000";
	 form.action='inserirImovelWizardAction.do?action=exibirInserirImovelLocalidadeAction&pesquisaRota=2';   
	 form.submit();
	form.nnSegmento.focus();
  }
}
</script>

<script>
<!-- Begin

     var bCancel = false;

    function validateInserirImovelActionForm(form) {
        if (bCancel)
      return true;
        else
       return validateRequired(form)
              && testarCampoValorZero(document.InserirImovelActionForm.idLocalidade, 'Localidade') 
              && testarCampoValorZero(document.InserirImovelActionForm.idSetorComercial, 'Setor Comercial')
              && testarCampoValorZero(document.InserirImovelActionForm.idQuadra, 'Quadra')
              && testarCampoValorZero(document.InserirImovelActionForm.testadaLote, 'Testada do Lote') 
              && validateCaracterEspecial(form)
	          && validateLong(form) && validateInteiroZeroPositivo(form);
	          
   }

//End -->
</script>

<script>

function limparPesquisaDescricaoLocalidade() {
    var form = document.forms[0];
      form.localidadeDescricao.value = "";
  }


function limparPesquisaLocalidade() {
    var form = document.forms[0];

      form.idLocalidade.value = "";
      form.localidadeDescricao.value = "";


  }

function limparPesquisaDescricaoRota() {
    var form = document.forms[0];
      form.descricaoRota.value = "";
  }

function limparPesquisaRota() {
    var form = document.forms[0];
      form.cdRota.value = "";
      form.descricaoRota.value = "";
      form.action='inserirImovelWizardAction.do?action=exibirInserirImovelLocalidadeAction&pesquisaRota=1';   
      form.submit();

  }

function limparPesquisaQuadra() {
    var form = document.forms[0];

      form.idQuadra.value = "";

	var msgQuadra = document.getElementById("msgQuadra");
	
	if (msgQuadra != null){
		msgQuadra.innerHTML = "";
	}

  }

function limparPesquisaSetorComercial() {
    var form = document.forms[0];

      form.idSetorComercial.value = "";
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

function limparDescricaoRota(){
    var form = document.forms[0];
    form.descricaoRota.value = "";

}

function limparDescricaoSetorComercial(){
    var form = document.forms[0];
    form.setorComercialDescricao.value = "";


}

function validarDigitoZero(tecla){
    var form = document.forms[0];
    
    if (document.all) {
		var codigo = event.keyCode;
    }
	else {
       var codigo = tecla.which;
    }
    
    if(codigo == 13){
	    if(form.cdRota.value <= 0){
	    	alert('Favor preencher a rota só com números positivos!');
	    	form.cdRota.value = "";
	    }
    }
}






</script>
</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('${requestScope.nomeCampo}');">
<div id="formDiv"><html:form action="/inserirImovelWizardAction"
	method="post" onsubmit="return validateInserirImovelActionForm(this);">

	<jsp:include
		page="/jsp/util/wizard/navegacao_abas_wizard_valida_avancar.jsp?numeroPagina=1" />


	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="4" cellpadding="0">
		<input type="hidden" name="numeroPagina" value="1" />
		<html:hidden property="url" value="1" />
		<html:hidden property="idRota"/>
		<tr>
			<td width="149" valign="top" class="leftcoltext">
			<div align="center"><%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%@ include
				file="/jsp/util/informacoes_usuario.jsp"%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%@ include
				file="/jsp/util/mensagens.jsp"%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			<%--<p align="left">&nbsp;</p>--%> <%--<p align="left">&nbsp;</p>--%>
			</div>
			</td>
			<td width="660" valign="top" class="centercoltext">
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
					<td class="parabg">Inserir Imóvel</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td>Para inserir um im&oacute;vel, informe os dados abaixo:</td>
					<td align="right"></td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td width="19%"><strong>Localidade:<font
						color="#FF0000">*</font></strong></td>
					<td width="81%" height="24" colspan="2"><html:text
						maxlength="3" property="idLocalidade" size="3" tabindex="1"
						onkeyup="checa_proximo(this.name);"
						onkeypress="javascript:limparPesquisaDescricaoLocalidade();limparPesquisaQuadra();limparPesquisaSetorComercial(); return validaEnter(event, 'inserirImovelWizardAction.do?action=exibirInserirImovelLocalidadeAction', 'idLocalidade');" />
					<a
						href="javascript:abrirPopup('exibirPesquisarLocalidadeAction.do?tipo=imovelLocalidade', 400, 800);limparPesquisaQuadra();limparPesquisaSetorComercial();">
					<img border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0" /></a>

					<logic:present name="codigoLocalidadeNaoEncontrada" scope="request">
						<input type="text" name="localidadeDescricao" size="37"
							readonly="true"
							style="background-color: #EFEFEF; border: 0; color: #ff0000"
							value="<bean:message key="atencao.localidade.inexistente"/>" />
					</logic:present> <logic:notPresent name="codigoLocalidadeNaoEncontrada"
						scope="request">
						<html:text property="localidadeDescricao" size="37"
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000" />
					</logic:notPresent> <a
						href="javascript:limparPesquisaLocalidade();limparPesquisaSetorComercial();limparPesquisaQuadra();document.forms[0].idLocalidade.focus();">
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
				<tr>
					<td><strong>Setor Comercial:<font color="#FF0000">*</font></strong></td>
					<td height="24" colspan="2"><html:text maxlength="3"
						property="idSetorComercial" size="3"
						onkeyup="checa_proximo(this.name);" tabindex="2"
						onkeypress="javascript:limparDescricaoSetorComercial();limparPesquisaQuadra(); return validaEnterDependencia(event, 'inserirImovelWizardAction.do?action=exibirInserirImovelLocalidadeAction', idSetorComercial, document.forms[0].idLocalidade.value, 'Localidade');" />
					<a
						href="javascript:abrirPopupDependencia('exibirPesquisarSetorComercialAction.do?idLocalidade='+document.forms[0].idLocalidade.value+'&tipo=SetorComercial',document.forms[0].idLocalidade.value,'Localidade', 400, 800);limparPesquisaQuadra();">
					<img border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0" /></a>
					<logic:present name="codigoSetorComercialNaoEncontrada"
						scope="request">
						<input type="text" name="setorComercialDescricao" size="37"
							readonly="true"
							style="background-color: #EFEFEF; border: 0; color: #ff0000"
							value="<bean:message key="atencao.setor_comercial.inexistente"/>" />
					</logic:present> <logic:notPresent name="codigoSetorComercialNaoEncontrada"
						scope="request">
						<html:text property="setorComercialDescricao" size="37"
							readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000" />
					</logic:notPresent> <a
						href="javascript:limparPesquisaSetorComercial();limparPesquisaQuadra();document.forms[0].idSetorComercial.focus();">
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
				<tr>

					<td><strong>Quadra:<font color="#FF0000">*</font></strong></td>
					<td height="24" colspan="2"><html:text maxlength="5"
						property="idQuadra" size="4" onkeyup="checa_proximo(this.name);"
						tabindex="4"
						onkeypress="return validaEnterDependencia(event, 'inserirImovelWizardAction.do?action=exibirInserirImovelLocalidadeAction&pesquisaQuadra=1', idQuadra, document.forms[0].idSetorComercial.value, 'Setor Comercial');" />

					<a
						href="javascript:abrirPopupDependencia('exibirPesquisarQuadraAction.do?idLocalidade='+document.forms[0].idLocalidade.value+'&codigoSetorComercial='+document.forms[0].idSetorComercial.value+'&tipo=Quadra&retornarSeteParametros=S',document.forms[0].idSetorComercial.value,'Setor Comercial', 400, 800);">
					<img border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0" />
					</a> <logic:present name="codigoQuadraNaoEncontrada" scope="request">
						<span style="color: #ff0000" id="msgQuadra"><bean:write
							scope="request" name="msgQuadra" /></span>
					</logic:present> <logic:notPresent name="codigoQuadraNaoEncontrada" scope="request">

					</logic:notPresent> <a
						href="javascript:limparPesquisaQuadra();document.forms[0].idQuadra.focus();">
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /> </a></td>

				</tr>
				<tr>
					<td height="24"><strong>Lote:<font color="#FF0000">*</font></strong></td>
					<td colspan="2"><html:text maxlength="4" property="lote"
						size="4" tabindex="5" onkeyup="checa_proximo(this.name);" /></td>
				</tr>
				<tr>
					<td height="24"><strong>Sublote:<font color="#FF0000">*</font></strong></td>
					<td colspan="2"><html:text maxlength="3" property="subLote"
						size="3" tabindex="6" onkeyup="checa_proximo(this.name);" /></td>
				</tr>
				<tr>
					<td height="24"><strong>Testada do Lote:</strong></td>
					<td colspan="2"><html:text maxlength="4"
						property="testadaLote" size="4" tabindex="7" /></td>
				</tr>

				<tr>
					<td height="24"><strong>Rota:<font color="#FF0000">*</font></strong></td>
					<td width="81%" height="24" colspan="2">
					<html:text maxlength="5" property="cdRota" size="5" tabindex="8"
						onkeyup="checa_proximo(this.name);"
						onkeypress="javascript:validarDigitoZero(event); javascript:limparPesquisaDescricaoRota(); return validaEnterDependencia(event, 'inserirImovelWizardAction.do?action=exibirInserirImovelLocalidadeAction&pesquisaRota=2', cdRota, document.forms[0].idSetorComercial.value, 'Setor Comercial'); " />
					<a href="javascript:abrirPopupDependencia('exibirPesquisarRotaAction.do?idLocalidade='+document.forms[0].idLocalidade.value+'&codigoSetorComercial='+document.forms[0].idSetorComercial.value+'&restringirPesquisa=true',document.forms[0].idSetorComercial.value,'Setor Comercial', 400, 800);">
					<img border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/></a>

					<logic:present name="rotaNaoEncontrada" scope="request">
						<html:text property="descricaoRota" size="35" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
					</logic:present> 
					
					<logic:notPresent name="rotaNaoEncontrada" scope="request">
						<html:text property="descricaoRota" size="35" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
					</logic:notPresent>
					
					<a href="javascript:limparPesquisaRota(); document.forms[0].cdRota.focus();">
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
						property="nnSegmento" size="2" tabindex="9"
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
			  			<html:select property="idDistritoOperacional" tabindex="10" style="width:200px;">
							<html:option value="-1"> &nbsp; </html:option>
							<html:options collection="colecaoDistritoOperacional" property="id" labelProperty="descricaoComId"/>
						</html:select>
					</td>
				</tr>
				
				<tr>
					<td height="24">&nbsp;</td>
					<td colspan="2"><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios</td>
				</tr>
				<tr>
					<td height="0" colspan="3">
					<div align="right"></div>
					</td>
				</tr>
				<tr>
					<td colspan="3">
					<div align="right"><jsp:include
						page="/jsp/util/wizard/navegacao_botoes_wizard_valida_avancar.jsp?numeroPagina=1" />
					</div>
					</td>
				</tr>
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>

</html:form></div>
</body>

<%@ include file="/jsp/util/telaespera.jsp"%>

<script>
document.getElementById('botaoConcluir').onclick = function() { botaoAvancarTelaEspera('/gsan/inserirImovelWizardAction.do?concluir=true&action=inserirImovelLocalidadeAction'); }
</script>


</html:html>
