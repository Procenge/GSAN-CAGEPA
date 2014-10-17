<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gcom.util.ConstantesSistema"%>
<%@page import="gcom.cadastro.sistemaparametro.SistemaParametro"%>

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="ManterImovelActionForm"
	dynamicJavascript="false" staticJavascript="false" page="3" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
	
<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_ADA%>" scope="session">
<script>
<!-- Begin

     var bCancel = false;

    function validateManterImovelActionForm(form) {
        if (bCancel)
      return true;
        else
       return validateRequired(form) 
              && testarCampoDecimalValorZero(document.ManterImovelActionForm.areaConstruida, 'Área Construída')
              && testarCampoDecimalValorZero(document.ManterImovelActionForm.reservatorioInferior, 'Volume Reservatório Inferior')
              && testarCampoDecimalValorZero(document.ManterImovelActionForm.reservatorioSuperior, 'Volume Reservatório Superior')
              && testarCampoDecimalValorZero(document.ManterImovelActionForm.piscina, 'Volume Piscina Imóvel')
              && validateCaracterEspecial(form) && validateLong(form) && validateDecimal(form)
              && validarAreaConstruida() && validarPerfilImovel();
   }

    function caracteresespeciais () {
 //    this.ae = new Array("areaConstruida", "Área Construída possui caracteres especiais.", new Function ("varName", " return this[varName];"));
 //    this.af = new Array("reservatorioInferior", "Reservatório Inferior possui caracteres especiais.", new Function ("varName", " return this[varName];"));
  //   this.ag = new Array("reservatorioSuperior", "Reservatório Superior possui caracteres especiais.", new Function ("varName", " return this[varName];"));
  //   this.ah = new Array("piscina", "Piscina possui caracteres especiais.", new Function ("varName", " return this[varName];"));
    }

    function IntegerValidations () {
    }
    function FloatValidations () {
     this.ai = new Array("areaConstruida", "Área Construída 1deve somente conter números decimais positivos.", new Function ("varName", " return this[varName];"));
     this.aj = new Array("reservatorioInferior", "Volume Reservatório Inferior deve somente conter números decimais positivos.", new Function ("varName", " return this[varName];"));
     this.al = new Array("reservatorioSuperior", "Volume Reservatório Superior deve somente conter números decimais positivos.", new Function ("varName", " return this[varName];"));
     this.am = new Array("piscina", "Volume Piscina Imóvel deve somente conter números decimais positivos.", new Function ("varName", " return this[varName];"));
    }
//End -->
</script>

<script>

  function validarAreaConstruida(){
		var form = document.ManterImovelActionForm;
   /* if (form.areaConstruida.value == '' && form.faixaAreaConstruida.value == '-1'){
		alert("Informe Área Construída.");
			     return false;
    }*/
    return true;
			}

  function desabilitaCampoAreaConstruida(){
    var form = document.ManterImovelActionForm;
    if (form.areaConstruida.value != ''){
      form.faixaAreaConstruida.value = '-1';
      form.faixaAreaConstruida.disabled = true;
    }else{
      form.faixaAreaConstruida.disabled = false;
    }
		}


  function verificaCamposPreenchidos(){
    var form = document.ManterImovelActionForm;
    if(form.areaConstruida.value == '0' || form.areaConstruida.value == 'null'){form.areaConstruida.value = ''}
    if(form.reservatorioInferior.value == '0' || form.reservatorioInferior.value == 'null'){form.reservatorioInferior.value = ''}
    if(form.reservatorioSuperior.value == '0' || form.reservatorioSuperior.value == 'null'){form.reservatorioSuperior.value = ''}
    if(form.piscina.value == '0' || form.piscina.value == 'null'){form.piscina.value = ''}

    desabilitaCampoAreaConstruida();
    desabilitaCampoReservatorioInferior();
    desabilitaCampoReservatorioSuperior();
    desabilitaCampoPiscina();
  }

  function verificaCamposPreenchidosTarifa(){
    var form = document.ManterImovelActionForm;
    if(form.areaConstruida.value == '0' || form.areaConstruida.value == 'null'){form.areaConstruida.value = ''}
    if(form.reservatorioInferior.value == '0' || form.reservatorioInferior.value == 'null'){form.reservatorioInferior.value = ''}
    if(form.reservatorioSuperior.value == '0' || form.reservatorioSuperior.value == 'null'){form.reservatorioSuperior.value = ''}
    if(form.piscina.value == '0' || form.piscina.value == 'null'){form.piscina.value = ''}

    desabilitaCampoReservatorioInferior();
    desabilitaCampoReservatorioSuperior();
    desabilitaCampoPiscina();
  }  
</script>

</logic:equal>

<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
<script>
<!-- Begin

     var bCancel = false;

    function validateManterImovelActionForm(form) {
        if (bCancel)
		return true;
        else
       return validateRequired(form) 
              && testarCampoDecimalValorZero(document.ManterImovelActionForm.reservatorioInferior, 'Volume Reservatório Inferior')
              && testarCampoDecimalValorZero(document.ManterImovelActionForm.reservatorioSuperior, 'Volume Reservatório Superior')
              && testarCampoDecimalValorZero(document.ManterImovelActionForm.piscina, 'Volume Piscina Imóvel')
              && validateCaracterEspecial(form) && validateLong(form) && validateDecimal(form)
              && validarPerfilImovel();
	}

    function caracteresespeciais () {
 //    this.af = new Array("reservatorioInferior", "Reservatório Inferior possui caracteres especiais.", new Function ("varName", " return this[varName];"));
  //   this.ag = new Array("reservatorioSuperior", "Reservatório Superior possui caracteres especiais.", new Function ("varName", " return this[varName];"));
  //   this.ah = new Array("piscina", "Piscina possui caracteres especiais.", new Function ("varName", " return this[varName];"));
    }

    function IntegerValidations () {
    }
    function FloatValidations () {
     this.aj = new Array("reservatorioInferior", "Volume Reservatório Inferior deve somente conter números decimais positivos.", new Function ("varName", " return this[varName];"));
     this.al = new Array("reservatorioSuperior", "Volume Reservatório Superior deve somente conter números decimais positivos.", new Function ("varName", " return this[varName];"));
     this.am = new Array("piscina", "Volume Piscina Imóvel deve somente conter números decimais positivos.", new Function ("varName", " return this[varName];"));
    }
//End -->
</script>

<script>

  function verificaCamposPreenchidos(){
    var form = document.ManterImovelActionForm;
    if(form.reservatorioInferior.value == '0' || form.reservatorioInferior.value == 'null'){form.reservatorioInferior.value = ''}
    if(form.reservatorioSuperior.value == '0' || form.reservatorioSuperior.value == 'null'){form.reservatorioSuperior.value = ''}
    if(form.piscina.value == '0' || form.piscina.value == 'null'){form.piscina.value = ''}

    desabilitaCampoReservatorioInferior();
    desabilitaCampoReservatorioSuperior();
    desabilitaCampoPiscina();
  }

  function verificaCamposPreenchidosTarifa(){
    var form = document.ManterImovelActionForm;
    if(form.reservatorioInferior.value == '0' || form.reservatorioInferior.value == 'null'){form.reservatorioInferior.value = ''}
    if(form.reservatorioSuperior.value == '0' || form.reservatorioSuperior.value == 'null'){form.reservatorioSuperior.value = ''}
    if(form.piscina.value == '0' || form.piscina.value == 'null'){form.piscina.value = ''}

    desabilitaCampoReservatorioInferior();
    desabilitaCampoReservatorioSuperior();
    desabilitaCampoPiscina();
  }  
</script>
	<logic:present name="indicadorFonteAbastecimentoObrigatorio">
		<script>
		<!-- Begin
			function required () {
			    this.ag = new Array("pavimentoCalcada", "Informe Pavimento Calçada.", new Function ("varName", " return this[varName];"));
			    this.ah = new Array("pavimentoRua", "Informe Pavimento Rua.", new Function ("varName", " return this[varName];"));
			    this.ai = new Array("fonteAbastecimento", "Informe Fonte de Abastecimento.", new Function ("varName", " return this[varName];"));
			    this.aj = new Array("situacaoLigacaoAgua", "Informe Situação Ligação Água.", new Function ("varName", " return this[varName];"));
			    this.al = new Array("situacaoLigacaoEsgoto", "Informe Situação Ligação Esgoto.", new Function ("varName", " return this[varName];"));
			    this.am = new Array("perfilImovel", "Informe Perfil do Imóvel.", new Function ("varName", " return this[varName];"));
			}
		<% String[] arrayJava = (String[])session.getAttribute("situacaoEsgotoNaoPermiteAlterarTipoDespejo");%>
		function verificaSituacaoEsgoto(selecionado){
				var array = new Array();
				<% for(int i=0;i<arrayJava.length;i++){
					%> array[<%=i%>] = <%=arrayJava[i] %>;
					<% } %>
			  var form = document.ManterImovelActionForm;
			  if(array[0]!='-1'){
				  for(var i=0;i<=array.length;i++){
					  if(selecionado.value == array[i]){
						  form.tipoDespejo.disabled = true;
						  form.tipoDespejo.value = -1;
						  break;
					  }else{
						  form.tipoDespejo.disabled = false;
					  }
				  }
			  }
			  
		  }
		-->
		</script>
	</logic:present>
	<logic:notPresent name="indicadorFonteAbastecimentoObrigatorio">
		<script>
		<!-- Begin
			function required () {
			    this.ag = new Array("pavimentoCalcada", "Informe Pavimento Calçada.", new Function ("varName", " return this[varName];"));
			    this.ah = new Array("pavimentoRua", "Informe Pavimento Rua.", new Function ("varName", " return this[varName];"));
			    //this.ai = new Array("fonteAbastecimento", "Informe Fonte de Abastecimento.", new Function ("varName", " return this[varName];"));
			    this.aj = new Array("situacaoLigacaoAgua", "Informe Situação Ligação Água.", new Function ("varName", " return this[varName];"));
			    this.al = new Array("situacaoLigacaoEsgoto", "Informe Situação Ligação Esgoto.", new Function ("varName", " return this[varName];"));
			    this.am = new Array("perfilImovel", "Informe Perfil do Imóvel.", new Function ("varName", " return this[varName];"));
			}
		<% String[] arrayJava = (String[])session.getAttribute("situacaoEsgotoNaoPermiteAlterarTipoDespejo");%>
		function verificaSituacaoEsgoto(selecionado){
				var array = new Array();
				<% for(int i=0;i<arrayJava.length;i++){
					%> array[<%=i%>] = <%=arrayJava[i] %>;
					<% } %>
			  var form = document.ManterImovelActionForm;
			  if(array[0]!='-1'){
				  for(var i=0;i<=array.length;i++){
					  if(selecionado.value == array[i]){
						  form.tipoDespejo.disabled = true;
						  form.tipoDespejo.value = -1;
						  break;
					  }else{
						  form.tipoDespejo.disabled = false;
					  }
				  }
			  }
			  
		  }
		-->
		</script>
	</logic:notPresent>
</logic:equal>

<script>

function validarPerfilImovel(){
	var form = document.ManterImovelActionForm;
	if(form.perfilImovel.enabled == false){
		if(form.perfilImovel.value == ""){
		     alert("Informe Perfil do Imóvel.");		
		     return false;
		}
	}
	return true;
}

function pesquisaEnter(tecla) {

  var form = document.ManterImovelActionForm;

  if (document.all) {
    var codigo = event.keyCode;
  } else {
    var codigo = tecla.which;
  }
  if(form.idCliente.value != ''){
    if (codigo == 13) {
      form.action = "exibirInserirImovelCaracteristicasAction.do";
      form.submit();
    } else {
      return true;
    }
  }
}

function desabilitaCampoReservatorioInferior(){
    var form = document.InserirImovelActionForm;

    if (form.reservatorioInferior != null){
       if (form.reservatorioInferior.value != ''){
           form.faixaReservatorioInferior.value = '-1';
           form.faixaReservatorioInferior.disabled = true;
       }else{
           form.faixaReservatorioInferior.disabled = false;
       }
    }
  }

  function desabilitaCampoReservatorioSuperior(){
    var form = document.InserirImovelActionForm;

    if(form.reservatorioSuperior != null){
      if (form.reservatorioSuperior.value != ''){
          form.faixaResevatorioSuperior.value = '-1';
          form.faixaResevatorioSuperior.disabled = true;
       }else{
          form.faixaResevatorioSuperior.disabled = false;
       }
    }
  }

  function desabilitaCampoPiscina(){
    var form = document.InserirImovelActionForm;

	if(form.piscina != null){
	   if (form.piscina.value != ''){
	      form.faixaPiscina.value = '-1';
	      form.faixaPiscina.disabled = true;
	    }else{
	      form.faixaPiscina.disabled = false;
	    }
	}
  }

</script>

</head>

<logic:equal name="tarifaSocial" value="1">
	<body leftmargin="5" topmargin="5"
		onload="javascript:verificaCamposPreenchidosTarifa();verificaSituacaoEsgoto(document.ManterImovelActionForm.situacaoLigacaoEsgoto);">
</logic:equal>
<logic:notEqual name="tarifaSocial" value="1">
	<body leftmargin="5" topmargin="5" onload="javascript:verificaSituacaoEsgoto(document.ManterImovelActionForm.situacaoLigacaoEsgoto);">
</logic:notEqual>

<div id="formDiv">
<html:form action="/atualizarImovelWizardAction" method="post" enctype="multipart/form-data" onsubmit="return validateManterImovelActionForm(this);">

	<jsp:include
		page="/jsp/util/wizard/navegacao_abas_wizard_valida_avancar.jsp?numeroPagina=5" />



	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<table width="770" border="0" cellspacing="4" cellpadding="0">
		<input type="hidden" name="numeroPagina" value="6" />
		<html:hidden property="url" value="5" />
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
			<td width="650" valign="top" class="centercoltext">
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
					<td class="parabg">Atualizar Imóvel</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">		
					
				<tr>
					
					<td align="left"><strong> Foto do imóvel: </strong></td>
					<td align="center">											
							
							<td colspan="3" align="left"><input type="file" style="textbox"	name="fotoFachada" size="50" />	</td>
														
							<td>
								<logic:notEmpty name="fotoFachada">
								<a href="javascript:abrirPopup('exibirFotoFachadaImovelAction.do?id=<bean:write name="idImovel"/>', 600, 800)">
									<input name="imageField" type="image" src="imagens/imgfolder.gif" width="18" height="18" border="0" disabled="disabled">
								</a>
								
								</logic:notEmpty>
							</td>
							
					</td>
			    	<td align="right"></td>
				</tr>						
				<tr>
					<td>Para identificar &agrave; faixa da &aacute;rea contru&iacute;da do im&oacute;vel, informe os dados abaixo:</td>
					
				</tr>
				</table>
			<table width="100%" border="0">
				<tr>
					<td height="23">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
							<table width="100%" border="0">							
								<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_ADA%>" scope="session">							
								<tr>
									<td width="23%"><strong>&Aacute;rea Constru&iacute;da:</strong></td>
									<td height="10"><logic:equal name="tarifaSocial" value="1">
										<html:text maxlength="10" size="10" property="areaConstruida" style="text-align: right;"
											onkeyup="javaScript:desabilitaCampoAreaConstruida();formataValorMonetario(this, 8);"
											onkeypress="javaScript:desabilitaCampoAreaConstruida();"
											disabled="true" />&nbsp;m<sup>2</sup>
									</logic:equal> <logic:notEqual name="tarifaSocial" value="1">
										<html:text maxlength="10" size="10" property="areaConstruida" style="text-align: right;"
											onkeyup="javaScript:desabilitaCampoAreaConstruida();formataValorMonetario(this, 8);" 
											onkeypress="javaScript:desabilitaCampoAreaConstruida();"
											/>&nbsp;m<sup>2</sup>
									</logic:notEqual>
									</td>
									
									
									<td colspan="2">
									  <logic:equal name="tarifaSocial" value="1">
										<html:select property="faixaAreaConstruida" disabled="true">
										  <html:option value="-1">&nbsp;</html:option>
										  <html:options collection="areaContruidaFaixas" labelProperty="faixaCompletaComId" property="id" />
										</html:select>
									  </logic:equal> 
									  
									  <logic:notEqual name="tarifaSocial" value="1">
										<html:select property="faixaAreaConstruida">
											<html:option value="-1">&nbsp;</html:option>
											<html:options collection="areaContruidaFaixas"
												labelProperty="faixaCompleta" property="id" />
										</html:select>
									  </logic:notEqual>
								    </td>
								</tr>
								</logic:equal>
								 <tr>
				                    	<td>
				                    		<p><strong>Padrão de Construção:</strong></p>
				                    	</td>
				                    	<td>
				                    		<html:select property="padraoConstrucao">
							                 <html:option value="-1">&nbsp;</html:option>
				                             <html:options collection="padroesConstrucao" labelProperty="descricaoComId" property="id"/>
				                       </html:select>
				                    	</td>
                 			   </tr>
								
								<tr>
									<td>
									<p><strong>Volume Reservat&oacute;rio Inferior:</strong></p>
									</td>
									<td height="10"><html:text maxlength="9" size="9"
										property="reservatorioInferior" style="text-align: right;"
										onkeyup="javaScript:formataValorMonetario(this, 7);desabilitaCampoReservatorioInferior();"
										onkeypress="javaScript:desabilitaCampoReservatorioInferior();"
										 />&nbsp;m<sup>3</sup>
									</td>
										 
									<td width="29%"><html:select
										property="faixaReservatorioInferior">
										<html:option value="-1">&nbsp;</html:option>
										<html:options collection="reservatorioVolumeFaixas"
											labelProperty="faixaCompletaComId" property="id" />
									</html:select>
									</td>
									<td width="24%">&nbsp;</td>
								</tr>
								<tr>
									<td height="10">
									<p><strong>Volume Reservat&oacute;rio Superior:</strong></p>
									</td>
									<td><html:text maxlength="9" size="9"
										property="reservatorioSuperior" style="text-align: right;"
										onkeyup="javaScript:formataValorMonetario(this, 7);desabilitaCampoReservatorioSuperior()"
										onkeypress="javaScript:desabilitaCampoReservatorioSuperior();"
										 />&nbsp;m<sup>3</sup>
									</td>
									
									<td>
									  <html:select property="faixaResevatorioSuperior">
										<html:option value="-1">&nbsp;</html:option>
										<html:options collection="reservatorioVolumeFaixas" labelProperty="faixaCompletaComId" property="id" />
									  </html:select>
									</td>
									
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td width="23%" height="10">
									<p><strong>Volume Piscina Im&oacute;vel:</strong></p>
									</td>
									<td width="24%"><html:text maxlength="9" size="9"
										property="piscina" style="text-align: right;"
										onkeyup="javaScript:formataValorMonetario(this, 7);desabilitaCampoPiscina();"
										onkeypress="javaScript:desabilitaCampoPiscina();" />&nbsp;m<sup>3</sup>
									</td>
									
									<td>
									  <html:select property="faixaPiscina">
										<html:option value="-1">&nbsp;</html:option>
										<html:options collection="piscinaVolumeFaixas" labelProperty="faixaCompletaComId" property="id" />
									  </html:select>
									</td>
									
									<td>
									<p>&nbsp;</p>
									<p>&nbsp;</p>
									</td>
								</tr>
								
								<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_ADA%>" scope="session">
				                    <tr>
				                      <td width="23%" height="10"><p><strong>Jardim:</strong></p></td>
				                      <td colspan="3">
										<strong><html:radio property="jardim" value="1" />Sim</strong> 
				    					<strong><html:radio property="jardim" value="2" />Não</strong>
				                      </td>
				                      <td><p>&nbsp;</p>
				                          <p>&nbsp;</p></td>
				                    </tr>
			                    </logic:equal>
								<logic:notEqual name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_ADA%>" scope="session">
									<html:hidden property="jardim" value="1"/>
								</logic:notEqual>
							</table>
							</td>
						</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td height="24">
					<hr>
					</td>
				</tr>
				<tr>
					<td height="24">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
							<table width="100%" border="0">
								<tr>
									<td width="20%" height="27"><strong>Pavimento Cal&ccedil;ada:<font	color="#FF0000">*</font></strong></td>
									<td width="36%"><html:select property="pavimentoCalcada">
				         <html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
				         </html:option>

										<html:options collection="pavimetoCalcadas"	labelProperty="descricaoComId" property="id" />
									</html:select></td>
									<td width="23%">&nbsp;</td>
									<td width="21%">&nbsp;</td>
								</tr>
								<tr>
									<td><strong>Pavimento Rua:</strong><font	color="#FF0000">*</font></td>
									<td height="24"><html:select property="pavimentoRua">
				         <html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
				         </html:option>

										<html:options collection="pavimentoRuas"
											labelProperty="descricaoComId" property="id" />
									</html:select></td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td><strong>Fonte de Abastecimento:
									<font	color="#FF0000">
									 <logic:present name="indicadorFonteAbastecimentoObrigatorio">
				                     *
				                      </logic:present>
									</font>
									</strong></td>
									<td height="24"><html:select property="fonteAbastecimento">
				         <html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
				         </html:option>

										<html:options collection="fonteAbastecimentos"
											labelProperty="descricaoComId" property="id" />
									</html:select></td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td><strong>Situa&ccedil;&atilde;o Liga&ccedil;&atilde;o
									&Aacute;gua:<font	color="#FF0000">*</font></strong></td>
									<td height="23">
									<logic:equal name="semSituacaoAgua" value="true">
										<html:select property="situacaoLigacaoAgua" disabled="true">
				         <html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
				         </html:option>

											<html:options collection="ligacaoAguaSituacaos"
												labelProperty="descricaoComId" property="id" />
										</html:select>
									</logic:equal>
									<logic:notEqual name="semSituacaoAgua" value="true">
										<html:select property="situacaoLigacaoAgua">
				         <html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
				         </html:option>

												<html:options collection="ligacaoAguaSituacaos"
													labelProperty="descricaoComId" property="id" />
											</html:select>
									</logic:notEqual>										
										</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td><strong>Situa&ccedil;&atilde;o Liga&ccedil;&atilde;o
									Esgoto:<font	color="#FF0000">*</font></strong></td>
									<td height="24">
									<logic:equal name="semSituacaoEsgoto" value="true">
										<html:select property="situacaoLigacaoEsgoto" disabled="true" onchange="javaScript:verificaSituacaoEsgoto(this);">
				         <html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
				         </html:option>

											<html:options collection="ligacaoEsgotoSituacaos"
												labelProperty="descricaoComId" property="id" />
										</html:select>
									</logic:equal>
									<logic:notEqual name="semSituacaoEsgoto" value="true">
										
										<logic:equal name="alterarEsgoto" value="<%=""+ConstantesSistema.SIM%>" scope="session">
											<html:select property="situacaoLigacaoEsgoto" onchange="javaScript:verificaSituacaoEsgoto(this);"  disabled="false">
				         						<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
				         							</html:option>
												<html:options collection="ligacaoEsgotoSituacaos"
													labelProperty="descricaoComId" property="id" />
											</html:select>
										</logic:equal>
										<logic:equal name="alterarEsgoto" value="<%=""+ConstantesSistema.NAO%>" scope="session">
											<html:select property="situacaoLigacaoEsgoto" onchange="javaScript:verificaSituacaoEsgoto(this);"  disabled="true">
				         						<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
				         							</html:option>
											<html:options collection="ligacaoEsgotoSituacaos"
												labelProperty="descricaoComId" property="id" />
										</html:select>
										</logic:equal>
									</logic:notEqual>										
										</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td><strong>Perfil do Im&oacute;vel:<font	color="#FF0000">*</font></strong></td>
									<td height="24">
										<logic:equal name="factivelEmEspera" value="1">
											<html:select property="perfilImovel" disabled="true">
					        					 <html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
												 <html:options collection="perfilImoveis" labelProperty="descricaoComId" property="id" />
											</html:select>
										</logic:equal> 
										<logic:notEqual name="factivelEmEspera" value="1">
											<html:select property="perfilImovel">
				        						<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
												<html:options collection="perfilImoveis" labelProperty="descricaoComId" property="id" />
											</html:select>
										</logic:notEqual>
										</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td><strong>Po&ccedil;o:<font	color="#FF0000"></font></strong></td>
									<td height="27"><html:select property="poco">
				         <html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
				         </html:option>

										<html:options collection="pocoTipos" labelProperty="descricaoComId"
											property="id" />
									</html:select></td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td><strong>Tipo de Despejo:</strong></td>
									<td height="27"><html:select property="tipoDespejo">
				         <html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
				         </html:option>
										<html:options collection="tipoDespejos"
											labelProperty="descricaoComId" property="id" />
									</html:select></td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
								
								<tr>
				                    	<td>
				                    		<p><strong>Tipo de Esgotamento:</strong></p>
				                    	</td>
				                    	<td>
				                    		<html:select property="esgotamento">
							                 <html:option value="-1">&nbsp;</html:option>
				                             <html:options collection="esgotamentos" labelProperty="descricaoComId" property="id"/>
				                       </html:select>
				                    	</td>
                    			</tr>

								<tr>
									<td width="20%" height="27"><strong>Setor de Abastecimento:</strong></td>
									<td width="36%">
										<html:select property="setorAbastecimento">
											<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
											<logic:present name="colecaoSetorAbastecimento">
												<html:options collection="colecaoSetorAbastecimento" labelProperty="descricaoComCodigo" property="id"/>
											</logic:present>
										</html:select>
									</td>
									<td width="23%">&nbsp;</td>
									<td width="21%">&nbsp;</td>
								</tr>

								<tr>
									<td width="20%" height="27"><strong>Sub-bacia:</strong></td>
									<td width="36%">
										<html:select property="subBacia">
											<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
											<logic:present name="colecaoSubBacia">
												<html:options collection="colecaoSubBacia" labelProperty="descricaoComCodigo" property="id"/>
											</logic:present>
										</html:select>
									</td>
									<td width="23%">&nbsp;</td>
									<td width="21%">&nbsp;</td>
								</tr>

							</table>
							</td>
						</tr>
						<tr>
							<td>
							<div align="right"><jsp:include
								page="/jsp/util/wizard/navegacao_botoes_wizard_valida_avancar.jsp?numeroPagina=5" />
							</div>
							</td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
	</table>
	<%@ include file="/jsp/util/rodape.jsp"%>
<%@ include file="/jsp/util/tooltip.jsp" %>

	
</html:form>

</div>
</body>


<%@ include file="/jsp/util/telaespera.jsp"%>

<script>
document.getElementById('botaoConcluir').onclick = function() { botaoAvancarTelaEspera('/gsan/atualizarImovelWizardAction.do?concluir=true&action=atualizarImovelCaracteristicasAction'); }
</script>


</html:html>
