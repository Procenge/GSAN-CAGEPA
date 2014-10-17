<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="PesquisarActionForm"
	dynamicJavascript="false" />
<script language="JavaScript">

 var bCancel = false;

    function validatePesquisarActionForm(form) {
        if (bCancel)
      return true;
        else
       return testarCampoValorZero(document.PesquisarActionForm.idLocalidade, 'Localidade') && testarCampoValorZero(document.PesquisarActionForm.codigoSetorComercial, 'Setor Comerical') &&
              testarCampoValorZero(document.PesquisarActionForm.idQuadra, 'Quadra') && testarCampoValorZero(document.PesquisarActionForm.idCliente, 'Cliente')&&
              testarCampoValorZero(document.PesquisarActionForm.idHidrometro, 'Hidrometro') &&
              testarCampoValorZero(document.PesquisarActionForm.cep, 'CEP') && testarCampoValorZero(document.PesquisarActionForm.idMunicipioImovel, 'Município')&&
              testarCampoValorZero(document.PesquisarActionForm.codigoBairroImovel, 'Bairro') && testarCampoValorZero(document.PesquisarActionForm.idLogradouro, 'Logradouro') 
              && validateCaracterEspecial(form) && validateLong(form) && validateInteiroZeroPositivo(form);

   }

    function caracteresespeciais () {
     this.aa = new Array("idLocalidade", "Localidade possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ab = new Array("codigoSetorComercial", "Setor Comercial possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ac = new Array("idQuadra", "Quadra possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ad = new Array("lote", "Lote possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ae = new Array("subLote", "SubLote possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.aj = new Array("idCliente", "Cliente possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ai = new Array("cep", "CEP possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ag = new Array("idMunicipioImovel", "Município possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ah = new Array("codigoBairroImovel", "Bairro possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.af = new Array("idLogradouro", "Logradouro possui caracteres especiais.", new Function ("varName", " return this[varName];"));

  }

    function IntegerValidations () {
     this.aa = new Array("idLocalidade", "Localidade deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.ab = new Array("codigoSetorComercial", "Setor Comercial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.ac = new Array("idQuadra", "Quadra deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.ag = new Array("idCliente", "Cliente deve somente conter números positivos.", new Function ("varName", " return this[varName];"));     
     this.ai = new Array("cep", "CEP deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.ah = new Array("idMunicipioImovel", "Município deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.af = new Array("codigoBairroImovel", "Bairro deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     this.aj = new Array("idLogradouro", "Logradouro deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    }
    
	function InteiroZeroPositivoValidations(){
     this.aa = new Array("lote", "Lote deve somente conter números positivos ou zero.", new Function ("varName", " return this[varName];"));
	 this.ab = new Array("subLote", "SubLote deve somente conter números positivos ou zero.", new Function ("varName", " return this[varName];"));
    }


 function limparPesquisaLocalidade() {
    var form = document.PesquisarActionForm;

    form.idLocalidade.value = "";
    form.descricaoLocalidade.value = "";
    limparPesquisaSetorComercial();
    limparPesquisaQuadra();
    limparPesquisaMunicipio();


  }
function limparPesquisaSetorComercial() {
    var form = document.PesquisarActionForm;

    form.codigoSetorComercial.value = "";
    form.descricaoSetorComercial.value = "";
     form.idMunicipioImovel.disabled = false;


  }
function limparPesquisaQuadra() {
    var form = document.PesquisarActionForm;

    form.idQuadra.value = "";
    if (form.descricaoQuadra != null){
    	form.descricaoQuadra.value = "";
    }	


  }
function limparPesquisaCliente() {
    var form = document.PesquisarActionForm;

    form.idCliente.value = "";
    form.nomeCliente.value = "";

  }
  
function limparPesquisaHidrometro() {
    var form = document.PesquisarActionForm;

    form.idHidrometro.value = "";
    form.descricaoHidrometro.value = "";

  }
function limparPesquisaMunicipio() {
    var form = document.PesquisarActionForm;

    form.idMunicipioImovel.value = "";
    form.descricaoMunicipioImovel.value = "";

  }

function limparPesquisaBairro() {
    var form = document.PesquisarActionForm;

    form.codigoBairroImovel.value = "";
    form.descricaoBairroImovel.value = "";
}
function limparPesquisaLogradouro(){
    var form = document.PesquisarActionForm;

    form.idLogradouro.value = "";
    form.nomeLogradouro.value = "";

}

function limparPesquisaCep() {
    var form = document.forms[0];

      form.cep.value = "";
      form.descricaoCep.value = "";


  }


function validarForm(form){

if(validatePesquisarActionForm(form)){
    form.submit();
}
}

function verificarSituacaoMunicipioPesquisarMunicipio(){
	var form = document.forms[0];
	if(form.idMunicipioImovel.disabled == false){
	    limparPesquisaBairro();
		redirecionarSubmit('exibirPesquisarMunicipioAction.do?caminhoRetornoTelaPesquisaMunicipio=exibirPesquisarImovelAction');
	}

}

function verificarSituacaoMunicipioLimparMunicipio(){

	var form = document.forms[0];
	if(form.idMunicipioImovel.disabled == false){
		limparPesquisaMunicipio();limparPesquisaBairro();
	}

}

function verificarDigitosSetorComercial(){
	var form = document.forms[0];
	
    if (form.codigoSetorComercial.value != ''){
      form.idMunicipioImovel.disabled = false;
    }else{
      form.idMunicipioImovel.disabled = false;
    }
}

function limparForm(){

	var form = document.forms[0];
	
	limparPesquisaLogradouro();
	limparPesquisaBairro();
	limparPesquisaCliente();
	limparPesquisaHidrometro();
	limparPesquisaMunicipio();	
	limparPesquisaQuadra();
	limparPesquisaSetorComercial();
	limparPesquisaLocalidade();
	limparCep();
	
	form.lote.value = "";
	form.subLote.value = "";
	form.cep.value = "";	
}

function limparCep(){
	var form = document.forms[0];
	
	form.cep.value = "";
	form.descricaoCep.value = "";
}
</script>
</head>

<body leftmargin="5" topmargin="5"
	onload="resizePageSemLink(800, 490);javascript:setarFoco('${requestScope.nomeCampo}');javascript:verificarDigitosSetorComercial()">

<html:form action="/pesquisarImovelAction" method="post"
	onsubmit="return validatePesquisarActionForm(this);">
	<table width="760" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="760" valign="top" class="centercoltext">
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
					<td class="parabg">Pesquisar Im&oacute;vel</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td colspan="4">Preencha os campos para pesquisar um im&oacute;vel:</td>
					<td align="right"></td>
				</tr>
				
			</table>
				
			<table width="100%" border="0">
				<tr>
					<td width="18%"><strong>Localidade:</strong></td>
					<td width="82%" height="24"><html:text maxlength="3" tabindex="1"
						property="idLocalidade" size="3"
						onkeypress="limparPesquisaQuadra();limparPesquisaSetorComercial();limparPesquisaMunicipio();validaEnter(event, 'exibirPesquisarImovelAction.do?objetoConsulta=1','idLocalidade');"
						onkeyup="javascript:verificarDigitosSetorComercial()" /> <a
						href="javascript:redirecionarSubmit('exibirPesquisarLocalidadeAction.do?caminhoRetornoTelaPesquisa=exibirPesquisarImovelAction');limparPesquisaQuadra();limparPesquisaSetorComercial();">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Localidade" /></a> <logic:present
						name="idLocalidadeNaoEncontrada">
						<logic:equal name="idLocalidadeNaoEncontrada" value="exception">
							<html:text property="descricaoLocalidade" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>
						<logic:notEqual name="idLocalidadeNaoEncontrada" value="exception">
							<html:text property="descricaoLocalidade" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> <logic:notPresent name="idLocalidadeNaoEncontrada">
						<logic:empty name="PesquisarActionForm" property="idLocalidade">
							<html:text property="descricaoLocalidade" value="" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="PesquisarActionForm" property="idLocalidade">
							<html:text property="descricaoLocalidade" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>

					</logic:notPresent> <a
						href="javascript:limparPesquisaLocalidade();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
				<tr>
					<td><strong>Setor Comercial:</strong></td>
					<td><html:text maxlength="3" property="codigoSetorComercial"
						tabindex="2" size="3"
						onkeypress="limparPesquisaQuadra();limparPesquisaBairro();validaEnterDependencia(event, 'exibirPesquisarImovelAction.do?objetoConsulta=1',this,document.forms[0].idLocalidade.value,'Localidade');"
						onkeyup="javascript:verificarDigitosSetorComercial()" /> <a
						href="javascript:limparPesquisaBairro();redirecionarSubmitDependencia('exibirPesquisarSetorComercialAction.do?caminhoRetornoTelaPesquisa=exibirPesquisarImovelAction&idLocalidade='+document.forms[0].idLocalidade.value,document.forms[0].idLocalidade.value,'Localidade');limparPesquisaQuadra();">
						
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Setor Comercial" /></a> 
						<logic:present	name="idSetorComercialNaoEncontrada">
						<logic:equal name="idSetorComercialNaoEncontrada"	value="exception">
							<html:text property="descricaoSetorComercial" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>
						<logic:notEqual name="idSetorComercialNaoEncontrada"
							value="exception">
							<html:text property="descricaoSetorComercial" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> 
					<logic:notPresent name="idSetorComercialNaoEncontrada">
						<logic:empty name="PesquisarActionForm"
							property="codigoSetorComercial">
							<html:text property="descricaoSetorComercial" value="" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="PesquisarActionForm"
							property="codigoSetorComercial">
							<html:text property="descricaoSetorComercial" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>

					</logic:notPresent> <a
						href="javascript:limparPesquisaSetorComercial();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
				
				<tr>
					<td><strong>Quadra:</strong></td>
		
					<td height="24"><html:text maxlength="5" property="idQuadra"
						tabindex="3" size="4"
						onkeypress="validaEnterDependencia(event, 'exibirPesquisarImovelAction.do?objetoConsulta=1',this,document.forms[0].codigoSetorComercial.value,'Setor Comercial');" 
						onkeyup="javascript:verificarDigitosSetorComercial()"/>
						<ahref="javascript:limparPesquisaBairro();redirecionarSubmitDependencia('exibirPesquisarQuadraAction.do?caminhoRetornoTelaPesquisa=exibirPesquisarImovelAction&idLocalidade='+document.forms[0].idLocalidade.value,document.forms[0].idLocalidade.value,'Localidade');limparPesquisaQuadra();">
					
					<a href="javascript:abrirPopupDependencia('exibirPesquisarQuadraAction.do?idSetorComercial='+document.forms[0].codigoSetorComercial.value+'&tipo=Quadra',document.forms[0].codigoSetorComercial.value,'Setor Comercial', 400, 800);">
				          	<img border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/>
				    </a>  			
					<logic:present name="idQuadraNaoEncontrada">
						<logic:equal name="idQuadraNaoEncontrada" value="exception">
							<html:text property="descricaoQuadra" size="40" maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>
					</logic:present>
					 <a href="javascript:limparPesquisaQuadra();document.forms[0].idQuadra.focus();"> <img
							 src="<bean:message key="caminho.imagens"/>limparcampo.gif"
							 border="0" title="Apagar" />
						 </a>
					</td>
				</tr>
				<tr>
					<td width="18%"><strong>Lote:</strong></td>
					<td width="82%"><html:text maxlength="4" property="lote"
						tabindex="4" size="5" /></td>
				<tr>
					<td width="18%"><strong>SubLote:</strong></td>
					<td width="82%"><html:text maxlength="3" property="subLote"
						tabindex="5" size="5" /></td>
				<tr>
					<td width="18%"><strong>Cliente:</strong></td>

					<td width="82%"><html:text maxlength="9" property="idCliente"
						tabindex="6" size="10"
						onkeypress="return validaEnter(event, 'exibirPesquisarImovelAction.do?objetoConsulta=1&limparForm=SIM', 'idCliente');" />
					<a
						href="javascript:redirecionarSubmit('exibirPesquisarClienteAction.do?caminhoRetornoTelaPesquisaCliente=exibirPesquisarImovelAction');">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Cliente" /></a> <logic:present
						name="idClienteNaoEncontrado">
						<logic:equal name="idClienteNaoEncontrado" value="exception">
							<html:text property="nomeCliente" size="55" maxlength="50"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>
						<logic:notEqual name="idClienteNaoEncontrado" value="exception">
							<html:text property="nomeCliente" size="55" maxlength="50"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> <logic:notPresent name="idClienteNaoEncontrado">
						<logic:empty name="PesquisarActionForm" property="idCliente">
							<html:text property="nomeCliente" value="" size="55"
								maxlength="50" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="PesquisarActionForm" property="idCliente">
							<html:text property="nomeCliente" size="55" maxlength="50"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>

					</logic:notPresent> <a href="javascript:limparPesquisaCliente();">
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
				
				<tr>
					<td width="18%"><strong>Número do Hidrômetro:</strong></td>

					<td width="82%"><html:text maxlength="10" property="idHidrometro" tabindex="6" size="10"	onkeypress="return pesquisaEnter(event, 'exibirPesquisarImovelAction.do?objetoConsulta=1&limparForm=SIM', 'idHidrometro');" />
					<a
						href="javascript:redirecionarSubmit('exibirPesquisarHidrometroAction.do?caminhoRetornoTelaPesquisaHidrometro=exibirPesquisarImovelAction');">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"	title="Pesquisar Hidrômetro" /></a> 
						<logic:present	name="idHidrometroNaoEncontrado">
						<logic:equal name="idHidrometroNaoEncontrado" value="exception">
							<html:text property="descricaoHidrometro" size="55" maxlength="50"	readonly="true"	style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>
						<logic:notEqual name="idHidrometroNaoEncontrado" value="exception">
							<html:text property="descricaoHidrometro" size="55" maxlength="50"	readonly="true"	style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> 
					<logic:notPresent name="idHidrometroNaoEncontrado">
						<logic:empty name="PesquisarActionForm" property="idHidrometro">
							<html:text property="descricaoHidrometro" value="" size="55" maxlength="50" readonly="true"	style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="PesquisarActionForm" property="idHidrometro">
							<html:text property="descricaoHidrometro" size="55" maxlength="50"	readonly="true"	style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
					</logic:notPresent> <a href="javascript:limparPesquisaHidrometro();">
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
				
				<tr>
					<td width="18%"><strong>Munic&iacute;pio:</strong></td>
					<td width="82%"><html:text maxlength="4" tabindex="7"
						property="idMunicipioImovel" size="4"
						onkeypress="limparPesquisaBairro();validaEnter(event, 'exibirPesquisarImovelAction.do?objetoConsulta=1', 'idMunicipioImovel');limparPesquisaSetorComercial();limparPesquisaBairro();limparPesquisaLogradouro();limparPesquisaCep();" />
					<a href="javascript:verificarSituacaoMunicipioPesquisarMunicipio()">
					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Municipio" /></a> <logic:present
						name="idMunicipioImovelNaoEncontrado">
						<logic:equal name="idMunicipioImovelNaoEncontrado"
							value="exception">
							<html:text property="descricaoMunicipioImovel" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>
						<logic:notEqual name="idMunicipioImovelNaoEncontrado"
							value="exception">
							<html:text property="descricaoMunicipioImovel" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> <logic:notPresent
						name="idMunicipioImovelNaoEncontrado">
						<logic:empty name="PesquisarActionForm"
							property="idMunicipioImovel">
							<html:text property="descricaoMunicipioImovel" value="" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="PesquisarActionForm"
							property="idMunicipioImovel">
							<html:text property="descricaoMunicipioImovel" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
					</logic:notPresent> <a
						href="javascript:verificarSituacaoMunicipioLimparMunicipio();limparPesquisaBairro();limparPesquisaLogradouro();limparPesquisaCep();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
				<tr>
					<td width="18%"><strong>Bairro:</strong></td>
					<td width="82%"><html:text maxlength="4"
						property="codigoBairroImovel" size="4" tabindex="8"
						onkeypress="validaEnter(event, 'exibirPesquisarImovelAction.do?objetoConsulta=1', 'codigoBairroImovel');limparPesquisaLogradouro();limparPesquisaCep();" />
					<a
						href="javascript:redirecionarSubmit('exibirPesquisarBairroAction.do?caminhoRetornoTelaPesquisaBairro=exibirPesquisarImovelAction&tipo=imovel&idMunicipio='+document.forms[0].idMunicipioImovel.value);">

					<img width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Bairro" /></a> <logic:present
						name="codigoBairroImovelNaoEncontrado">
						<logic:equal name="codigoBairroImovelNaoEncontrado"
							value="exception">
							<html:text property="descricaoBairroImovel" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>
						<logic:notEqual name="codigoBairroImovelNaoEncontrado"
							value="exception">
							<html:text property="descricaoBairroImovel" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> <logic:notPresent
						name="codigoBairroImovelNaoEncontrado">
						<logic:empty name="PesquisarActionForm"
							property="codigoBairroImovel">
							<html:text property="descricaoBairroImovel" value="" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="PesquisarActionForm"
							property="codigoBairroImovel">
							<html:text property="descricaoBairroImovel" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
					</logic:notPresent> <a href="javascript:limparPesquisaBairro();limparPesquisaLogradouro();limparPesquisaCep();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
				<tr>
					<td width="18%"><strong>Logradouro:</strong></td>
					<td width="18%"><html:text property="idLogradouro" size="9"
						maxlength="9" tabindex="9"
						onkeypress="validaEnter(event, 'exibirPesquisarImovelAction.do?objetoConsulta=1', 'idLogradouro');limparPesquisaCep();" />
					<a
						href="javascript:redirecionarSubmit('exibirPesquisarImovelAction.do?chamarPesquisaImovel=pesquisarLogradouro');"><img
						src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0"
						alt="Pesquisar"></a> <logic:present
						name="idLogradouroNaoEncontrado">
						<logic:equal name="idLogradouroNaoEncontrado" value="exception">
							<html:text property="nomeLogradouro" size="40" maxlength="30"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>
						<logic:notEqual name="idLogradouroNaoEncontrado" value="exception">
							<html:text property="nomeLogradouro" size="40" maxlength="30"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> <logic:notPresent name="idLogradouroNaoEncontrado">
						<logic:empty name="PesquisarActionForm" property="idLogradouro">
							<html:text property="nomeLogradouro" value="" size="40"
								maxlength="30" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="PesquisarActionForm" property="idLogradouro">
							<html:text property="nomeLogradouro" size="40" maxlength="30"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
					</logic:notPresent> <a
						href="javascript:limparPesquisaLogradouro();limparPesquisaCep();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>
				<tr>
					<td width="18%"><strong>Número:</strong></td>
					<td width="82%"><html:text maxlength="8" property="numeroImovel" tabindex="10" size="9" /></td>
				<tr>
				<tr>
					<td width="18%"><strong>CEP:</strong></td>
					<td width="82%"><html:text property="cep" size="8" maxlength="8" tabindex="11"
						onkeypress="validaEnter(event, 'exibirPesquisarImovelAction.do?objetoConsulta=1','cep');" />
					<a
						href="javascript:redirecionarSubmit('exibirPesquisarImovelAction.do?chamarPesquisaImovel=pesquisarCep');">
					<img border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0" /></a>

					<logic:present name="cepNaoEncontrada" scope="request">
						<input type="text" name="descricaoCep" size="30" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #ff0000"
							value="<bean:message key="atencao.cep.inexistente"/>" />
					</logic:present> <logic:notPresent name="cepNaoEncontrada"
						scope="request">
						<html:text property="descricaoCep" size="30" readonly="true"
							style="background-color:#EFEFEF; border:0; color: #000000" />
					</logic:notPresent> <a href="javascript:limparCep();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a></td>
				</tr>

				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td height="24" colspan="3" width="80%"><INPUT TYPE="button"
						class="bottonRightCol" value="Limpar"
						onclick="javascript:limparForm();" /> &nbsp;&nbsp; <logic:present
						name="caminhoRetornoTelaPesquisaImovel">
						<INPUT TYPE="button" class="bottonRightCol" value="Voltar"
							onclick="redirecionarSubmit('${caminhoRetornoTelaPesquisaImovel}.do')" />
					</logic:present></td>
					<td align="right"><input type="button" name="Button"
						class="bottonRightCol" tabindex="12" value="Pesquisar"
						onClick="javascript:validarForm(document.forms[0])" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>
</html:form>
</body>
</html:html>
