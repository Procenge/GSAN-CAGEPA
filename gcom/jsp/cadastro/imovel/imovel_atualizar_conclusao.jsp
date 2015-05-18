<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gcom.cadastro.imovel.Imovel" %>
<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro" %>

<html:html>

<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"  formName="ManterImovelActionForm" dynamicJavascript="false" page="3" />

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>

<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_ADA%>" scope="session">
<script>
	var bCancel = false;

    function validateManterImovelActionForm(form) {
    	
    	if (bCancel)
      		return true;
        else
        	return testarCampoValorZero(document.ManterImovelActionForm.numeroPontos, 'Número de Pontos') && 
        	    testarCampoValorZero(document.ManterImovelActionForm.numeroQuarto, 'Número de Quartos') && 
        	    testarCampoValorZero(document.ManterImovelActionForm.numeroBanheiro, 'Número de Baheiros') && 
        	    testarCampoValorZero(document.ManterImovelActionForm.numeroAdulto, 'Número de Adultos') && 
        	    testarCampoValorZero(document.ManterImovelActionForm.numeroCrianca, 'Número de Crianças') && 
        		testarCampoValorZero(document.ManterImovelActionForm.numeroMoradores, 'Número de Moradores') && 
        		testarCampoValorZero(document.ManterImovelActionForm.numeroMoradorTrabalhador, 'Número de Moradores que Trabalham') && 
        		testarCampoValorZero(document.ManterImovelActionForm.numeroIptu, 'Número de IPTU') && 
        		testarCampoValorZero(document.ManterImovelActionForm.numeroContratoCelpe, 'Contrato Companhia de Energia') && 
        		testarCampoValorZero(document.ManterImovelActionForm.idImovel, 'Imóvel Principal') && 
        		validateCaracterEspecial(form) && 
        		validateRequired(form) && 
        		validateLong(form) && 
        		validateDecimal(form) && 
        		validarUTM() && 
        		validateBigInteger(form) &&
        		validaNumeroMoradorTrabalhador(); 
	}

		function required() {
		 	this.aa = new Array("numeroQuarto", "Informe Número de Quartos.", new Function ("varName", " return this[varName];"));
		 	this.ab = new Array("numeroBanheiro", "Informe Número de Banheiros.", new Function ("varName", " return this[varName];"));
		}

   	function caracteresespeciais () {
	    	//this.ad = new Array("cordenadasUtmX", "Cordenadas Utm X deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     	//this.ae = new Array("cordenadasUtmY", "Cordenadas Utm Y deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     	this.af = new Array("numeroPontos", "Número de Pontos deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     	this.aq = new Array("numeroQuarto", "Número de Quartos deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     	this.ab = new Array("numeroBanheiro", "Número de Banheiro deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     	this.aa = new Array("numeroAdulto", "Número de Adultos deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     	this.ac = new Array("numeroCrianca", "Número de Crianças deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     	this.am = new Array("numeroMoradores", "Número de Moradores deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     	this.al = new Array("numeroMoradorTrabalhador", "Número de Moradores que Trabalham deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     	this.ah = new Array("numeroIptu", "Número de IPTU deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     	this.ai = new Array("numeroContratoCelpe", "Contrato Companhia de Energia deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     	this.at = new Array("idImovel", "Imóvel Principal possui caracteres especiais.", new Function ("varName", " return this[varName];"));
		this.aq = new Array("idFuncionario", "Funcionário deve somente conter números positivos.", new Function ("varName", " return this[varName];"));     	
    }

    function BigIntegerValidations () {
		this.az = new Array("numeroIptu", "Número de IPTU deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	}

    function IntegerValidations () {
     	this.al = new Array("numeroPontos", "Número de Pontos deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     	this.aq = new Array("numeroQuarto", "Número de Quatos deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     	this.ab = new Array("numeroBanheiro", "Número de Banheiros deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     	this.aa = new Array("numeroAdulto", "Número de Adultos deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     	this.ac = new Array("numeroCrianca", "Número de Crianças deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     	this.am = new Array("numeroMoradores", "Número de Moradores deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     	this.al = new Array("numeroMoradorTrabalhador", "Número de Moradores que Trabalham deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     	this.ao = new Array("numeroContratoCelpe", "Contrato Companhia de Energia deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     	this.au = new Array("idImovel", "Imóvel Principal deve somente conter dígitos.", new Function ("varName", " return this[varName];"));
		this.az = new Array("idFuncionario", "Funcionário deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    }

  	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

    	var form = document.forms[0];

	    if (tipoConsulta == 'imovel') {

	      limparPesquisaImovel();
	      form.idImovel.value = codigoRegistro;

	      form.action='atualizarImovelWizardAction.do?action=exibirAtualizarImovelConclusaoAction&pesquisar=SIM';
	      form.submit();
	    }else if ('funcionario' == tipoConsulta) {
			
		 	form.idFuncionario.value = codigoRegistro;
		 	form.action = 'atualizarImovelWizardAction.do?action=exibirAtualizarImovelConclusaoAction';
		 	form.submit();
    	}
  	}

 	function limparPesquisaImovel() {
    	var form = document.forms[0];
      	form.idImovel.value = "";
  	}

  	function limpaImovelPrincipal(){
    	var form = document.ManterImovelActionForm;
    	var id = form.idImovel.value;

		if(confirm('Confirma remoção ?')){
		    form.idImovel.value= "";
	      	form.action='atualizarImovelWizardAction.do?action=exibirAtualizarImovelConclusaoAction&remover=REMOVER';
    	  	form.submit();
   		}
  	}
  
  	function limparId(){
    	var form = document.ManterImovelActionForm;
      	if(form.quantidadeElemento.value == 0){
    		form.idImovel.value= "";
      	}
  	}

	function limparCamposFuncionario(){
		var form = document.forms[0];
 		form.nomeFuncionario.value = '';
	}

	function limparFuncionario() {
		document.forms[0].idFuncionario.value = '';
		document.forms[0].nomeFuncionario.value = '';
	}
	
	function somaMoradores(){
		var form = document.ManterImovelActionForm;
		form.numeroMoradores.value = Number(form.numeroCrianca.value) + Number(form.numeroAdulto.value);
	}
	
	function validaNumeroMoradorTrabalhador(){
		var form = document.ManterImovelActionForm;		
		if(Number(form.numeroMoradorTrabalhador.value) > Number(form.numeroMoradores.value)){
			alert("Número de moradores que trabalham é maior que o número de moradores.");
			form.numeroMoradorTrabalhador.focus();
			return false;
		}else{
		    return true;
		}    			
	}

		function limparZero() {
	  		var form = document.forms[0];
		
		  	if(form.numeroPontos.value == '0' || form.numeroPontos.value == 'null'){form.numeroPontos.value = ''}
		  	if(form.numeroQuarto.value == '0' || form.numeroQuarto.value == 'null'){form.numeroQuarto.value = ''}
		  	if(form.numeroBanheiro.value == '0' || form.numeroBanheiro.value == 'null'){form.numeroBanheiro.value = ''}
		  	if(form.numeroAdulto.value == '0' || form.numeroAdulto.value == 'null'){form.numeroAdulto.value = ''}
		  	if(form.numeroCrianca.value == '0' || form.numeroCrianca.value == 'null'){form.numeroCrianca.value = ''}
		  	if(form.numeroMoradores.value == '0' || form.numeroMoradores.value == 'null'){form.numeroMoradores.value = ''}
		  	if(form.numeroMoradorTrabalhador.value == '0' || form.numeroMoradorTrabalhador.value == 'null'){form.numeroMoradorTrabalhador.value = ''}
		  	if(form.numeroIptu.value == '0' || form.numeroIptu.value == 'null'){form.numeroIptu.value = ''}
		  	if(form.numeroContratoCelpe.value == '0' || form.numeroContratoCelpe.value == 'null'){form.numeroContratoCelpe.value = ''}
		  	if(form.cordenadasUtmX.value == 'null'){form.cordenadasUtmX.value = ''}
		  	if(form.cordenadasUtmY.value == 'null'){form.cordenadasUtmY.value = ''}
		}
	</script>
</logic:equal>

<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_DESO%>" scope="session">
	<script>
		var bCancel = false;

	    function validateManterImovelActionForm(form) {
	    	if (bCancel)
	      		return true;
	        else
	        	return testarCampoValorZero(document.ManterImovelActionForm.numeroPontos, 'Número de Pontos') && 
	        		testarCampoValorZero(document.ManterImovelActionForm.numeroMoradores, 'Número de Moradores') && 
	        		validateCaracterEspecial(form) && 
	        		validateRequired(form) && 
	        		validateLong(form) && 
	        		validateDecimal(form) && 
	        		validarUTM() && 
	        		validateBigInteger(form);
		}

		function required() {
		}

	   	function caracteresespeciais() {
//	    	this.ad = new Array("cordenadasUtmX", "Cordenadas Utm X deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
//	     	this.ae = new Array("cordenadasUtmY", "Cordenadas Utm Y deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     	this.af = new Array("numeroPontos", "Número de Pontos deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     	this.am = new Array("numeroMoradores", "Número de Moradores deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	    }

	    function BigIntegerValidations() {
		}

	    function IntegerValidations() {
	     	this.al = new Array("numeroPontos", "Número de Pontos deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	     	this.am = new Array("numeroMoradores", "Número de Moradores deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
	    }

		function limparZero() {
	  		var form = document.forms[0];

		  	if(form.numeroPontos.value == '0' || form.numeroPontos.value == 'null'){form.numeroPontos.value = ''}
		  	if(form.numeroMoradores.value == '0' || form.numeroMoradores.value == 'null'){form.numeroMoradores.value = ''}
		  	if(form.cordenadasUtmX.value == 'null'){form.cordenadasUtmX.value = ''}
		  	if(form.cordenadasUtmY.value == 'null'){form.cordenadasUtmY.value = ''}
//		  	if(form.cordenadasUtmX.value == '0' || form.cordenadasUtmX.value == 'null'){form.cordenadasUtmX.value = ''}
//		  	if(form.cordenadasUtmY.value == '0' || form.cordenadasUtmY.value == 'null'){form.cordenadasUtmY.value = ''}
		}
	</script>
</logic:equal>

<script>
	function validarUTM() {
		var form = document.forms[0];
		var retorno = true;

	    if (trim(form.cordenadasUtmX.value).length > 0 && trim(form.cordenadasUtmY.value).length == 0) {
			retorno = false;
		    alert("Informar ambas ou nenhuma das coordenadas UTM");
	    } else if (trim(form.cordenadasUtmY.value).length > 0  && trim(form.cordenadasUtmX.value).length == 0) {
			retorno = false;
	      	alert("Informar ambas ou nenhuma das coordenadas UTM");
	    }

	    return retorno;
	}

	function FloatValidations() {
	/*
		this.an = new Array("cordenadasUtmX", "Cordenadas UTM X deve ser um número decimal.", new Function ("varName", " return this[varName];"));
	 	this.ap = new Array("cordenadasUtmY", "Cordenadas UTM Y deve ser um número decimal.", new Function ("varName", " return this[varName];"));
	*/
	}
</script>
</head>

<body leftmargin="5" topmargin="5" onload="javascript:limparZero();">
<div id="formDiv">
<html:form action="/atualizarImovelWizardAction" method="post"
	onsubmit="return validateManterImovelActionForm(this);">

	<jsp:include
		page="/jsp/util/wizard/navegacao_abas_wizard_valida_avancar.jsp?numeroPagina=6" />



	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

	<input type="hidden" name="numeroPagina" value="7" />
	<table width="770" border="0" cellspacing="4" cellpadding="0">
		<html:hidden property="url" value="6" />
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
			<td width="640" valign="top" class="centercoltext">
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
					<td>Para concluir o cadastro, informe os dados abaixo:</td>
					<td align="right"></td>
				</tr>
			</table>
			<br>
			<table width="100%" border="0">
				<!-- <tr>
	      <tdcolspan="2">&nbsp;</td>
	    </tr> -->
				<tr>
					<td width="21%" height="24"><strong>N&uacute;mero de Pontos:</strong></td>
					<td width="79%">
						<logic:present name="permitirAtualizarConclusao" scope="session">
							<html:text maxlength="4" size="4"property="numeroPontos" />
						</logic:present>
						<logic:notPresent name="permitirAtualizarConclusao" scope="session">
							<html:text maxlength="4" size="4"property="numeroPontos" disabled="true" />
						</logic:notPresent>							
					</td>
				</tr>

				<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_ADA%>" scope="session">
					<tr>
						<td width="21%" height="24"><strong>N&uacute;mero de Quartos:<font
							color="#FF0000">*</font></strong></td>
						<td width="79%">
							<logic:present name="permitirAtualizarConclusao" scope="session">
								<html:text maxlength="4" size="4" property="numeroQuarto" />
							</logic:present>
							<logic:notPresent name="permitirAtualizarConclusao" scope="session">
								<html:text maxlength="4" size="4" property="numeroQuarto" disabled="true" />
							</logic:notPresent>								
						</td>
					</tr>
					<tr>
						<td width="21%" height="24"><strong>N&uacute;mero de Banheiros:<font
							color="#FF0000">*</font></strong></td>
						<td width="79%">
							<logic:present name="permitirAtualizarConclusao" scope="session">
								<html:text maxlength="4" size="4" property="numeroBanheiro" />
							</logic:present>
							<logic:notPresent name="permitirAtualizarConclusao" scope="session">	
								<html:text maxlength="4" size="4" property="numeroBanheiro" disabled="true" />
							</logic:notPresent>							
						</td>
					</tr>
				</logic:equal>

				<tr>
					<td height="24" colspan="2">
					<hr>
					</td>
				</tr>	

				<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_ADA%>" scope="session">
				<tr>
					<td width="21%" height="24"><strong>N&uacute;mero de Adultos:<font color="#FF0000"></font></strong></td>
					<td width="79%">
						<logic:present name="permitirAtualizarConclusao" scope="session">
							<html:text maxlength="4" size="4" property="numeroAdulto" onkeyup="somaMoradores();"/>
						</logic:present>
						<logic:notPresent name="permitirAtualizarConclusao" scope="session">
							<html:text maxlength="4" size="4" property="numeroAdulto" onkeyup="somaMoradores();" disabled="true" />
						</logic:notPresent>							
					</td>
				</tr>
				<tr>
					<td width="21%" height="24"><strong>N&uacute;mero de Crianças:<font	color="#FF0000"></font></strong></td>
					<td width="79%">
						<logic:present name="permitirAtualizarConclusao" scope="session">
							<html:text maxlength="4" size="4" property="numeroCrianca" onkeyup="somaMoradores();"/>
						</logic:present>
						<logic:notPresent name="permitirAtualizarConclusao" scope="session">
							<html:text maxlength="4" size="4" property="numeroCrianca" onkeyup="somaMoradores();" disabled="true" />
						</logic:notPresent>					
					</td>
				</tr>				
				</logic:equal>

				<logic:present name="indicadorCamposObrigatorios" scope="session">
				<tr>
					<td height="24"><strong>N&uacute;mero de Moradores:<font color="#FF0000">*</font></strong></td>
					<td>
						<logic:present name="permitirAtualizarConclusao" scope="session">
							<html:text maxlength="4" size="4" property="numeroMoradores" />
						</logic:present>
						<logic:notPresent name="permitirAtualizarConclusao" scope="session">
							<html:text maxlength="4" size="4" property="numeroMoradores" disabled="true" />
						</logic:notPresent>					
					</td>
				</tr>		
				</logic:present>
				<logic:notPresent name="indicadorCamposObrigatorios" scope="session">
				<tr>
					<td height="24"><strong>N&uacute;mero de Moradores:<font color="#FF0000"></font></strong></td>
					<td>
						<logic:present name="permitirAtualizarConclusao" scope="session">
							<html:text maxlength="4" size="4" property="numeroMoradores" />
						</logic:present>
						<logic:notPresent name="permitirAtualizarConclusao" scope="session">
							<html:text maxlength="4" size="4" property="numeroMoradores" disabled="true" />
						</logic:notPresent>												
					</td>
				</tr>		
				</logic:notPresent>
				
				<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_ADA%>" scope="session">
				<tr>
					<td height="24"><strong>N&uacute;mero de Moradores que Trabalham:<font color="#FF0000"></font></strong></td>
					<td>
						<logic:present name="permitirAtualizarConclusao" scope="session">					
							<html:text maxlength="4" size="4" property="numeroMoradorTrabalhador" onkeyup="validaNumeroMoradorTrabalhador();"/>
						</logic:present>
						<logic:notPresent name="permitirAtualizarConclusao" scope="session">
							<html:text maxlength="4" size="4" disabled="true" property="numeroMoradorTrabalhador" onkeyup="validaNumeroMoradorTrabalhador();"/>
						</logic:notPresent>							
					</td>
				</tr>
				<tr>
                   	<td>
                   		<p><strong>Renda Familiar:</strong></p>
                   	</td>
                   	<td>
                   		<logic:present name="permitirAtualizarConclusao" scope="session">
                   			<html:select property="faixaRendaFamiliar">
		                 		<html:option value="-1">&nbsp;</html:option>
                            	<html:options collection="rendaFamiliarFaixas" labelProperty="faixaCompletaComId" property="id"/>
                      		</html:select>
						</logic:present>
						<logic:notPresent name="permitirAtualizarConclusao" scope="session">
                   			<html:select property="faixaRendaFamiliar" disabled="true" >
		                 		<html:option value="-1">&nbsp;</html:option>
                            	<html:options collection="rendaFamiliarFaixas" labelProperty="faixaCompletaComId" property="id"/>
                      		</html:select>						
						</logic:notPresent>                      		
                   	</td>
                  </tr>				
				<tr>
					<td height="24" colspan="2">
					<hr>
					</td>
				</tr>
				<tr>
					<td height="24"><strong>N&uacute;mero de IPTU:</strong></td>
					<td>
						<logic:present name="permitirAtualizarConclusao" scope="session">
							<logic:equal name="tarifaSocial" value="1">
								<html:text maxlength="20" size="20" property="numeroIptu" disabled="true" />
							</logic:equal> 
							<logic:notEqual name="tarifaSocial" value="1">
								<html:text maxlength="20" size="20" property="numeroIptu" />
							</logic:notEqual>
						</logic:present>
						<logic:notPresent name="permitirAtualizarConclusao" scope="session">
							<html:text maxlength="20" size="20" property="numeroIptu" disabled="true" />
						</logic:notPresent>							
					</td>
				</tr>
				<tr>
					<td height="24"><strong>Contrato Companhia de Energia:</strong></td>
					<td>
						<logic:present name="permitirAtualizarConclusao" scope="session">
							<logic:equal name="tarifaSocial" value="1">
								<html:text maxlength="10" size="10" property="numeroContratoCelpe" disabled="true" />
							</logic:equal> 
							<logic:notEqual name="tarifaSocial" value="1">
								<html:text maxlength="10" size="10" property="numeroContratoCelpe" />
							</logic:notEqual>
						</logic:present>
						<logic:notPresent name="permitirAtualizarConclusao" scope="session">
							<html:text maxlength="10" size="10" property="numeroContratoCelpe" disabled="true" />
						</logic:notPresent>							
					</td>
				</tr>

				<tr>
					<td height="24" colspan="2">
					<hr>
					</td>
				</tr>
				
				</logic:equal>
				
				
				<tr>
					<td height="24" colspan="2">
					<hr>
					</td>
				</tr>
				<tr>
					<td height="24"><strong>Coordenadas UTM X:</strong></td>
					<td>
						<logic:present name="permitirAtualizarConclusao" scope="session">
							<html:text maxlength="25" property="cordenadasUtmX"	style="text-align: right;" />
						</logic:present>
						<logic:notPresent name="permitirAtualizarConclusao" scope="session">
							<html:text maxlength="25" property="cordenadasUtmX"	style="text-align: right;" disabled="true" />
						</logic:notPresent>					
					</td>
				</tr>
				<tr>
					<td height="24"><strong>Coordenadas UTM Y:</strong></td>
					<td>
						<logic:present name="permitirAtualizarConclusao" scope="session">
							<html:text maxlength="25" property="cordenadasUtmY"	style="text-align: right;" />
						</logic:present>
						<logic:notPresent name="permitirAtualizarConclusao" scope="session">
							<html:text maxlength="25" property="cordenadasUtmY"	style="text-align: right;" disabled="true" />
						</logic:notPresent>							
					</td>
				</tr>
				<tr>
					<td height="24" colspan="2">
					<hr>
					</td>
				</tr>

				<!-- ****************************************************************************************** -->
				<logic:equal name="<%=SistemaParametro.SISTEMA_PARAMETRO%>" property="parmId" value="<%="" + SistemaParametro.INDICADOR_EMPRESA_ADA%>" scope="session">
				<tr>
					<td width="10%"><strong>Imóvel Principal:</strong></td>
					<td width="90%">
						<logic:present name="permitirAtualizarConclusao" scope="session">
							<html:text property="idImovel" maxlength="9" size="9"
								onkeypress="javascript:return validaEnter(event, 'atualizarImovelWizardAction.do?action=exibirAtualizarImovelConclusaoAction&pesquisar=SIM', 'idImovel');" />
							<a href="javascript:abrirPopup('exibirPesquisarImovelAction.do', 400, 800);">
								<img width="23" height="21"	src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0" />
							</a>

		   		        	<logic:present name="idImovelPrincipalNaoEncontrado" scope="request">
                        		<input type="text" name="matriculaImovelPrincipal" size="50" readonly="readonly" style="background-color:#EFEFEF; border:0; color: #ff0000"
                        			value="${valorMatriculaImovelPrincipal}">
                        	</logic:present>
                        	<logic:notPresent name="idImovelPrincipalNaoEncontrado" scope="request">
                        		<logic:present name="valorMatriculaImovelPrincipal" scope="request">
                        			<input type="text" name="matriculaImovelPrincipal" size="50" readonly="readonly" style="background-color:#EFEFEF; border:0; color: #000000"
    	                    				value="<bean:write name="valorMatriculaImovelPrincipal" scope="request" />">
                        		</logic:present>
                        		<logic:notPresent name="valorMatriculaImovelPrincipal" scope="request">
                        			<input type="text" name="matriculaImovelPrincipal" size="50" readonly="readonly" style="background-color:#EFEFEF; border:0; color: #000000"
	                        				value="">
                        		</logic:notPresent>
                        	</logic:notPresent>
                        	
							<a href="javascript:limpaImovelPrincipal()"> 
								<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"	border="0" title="Apagar" />
							</a>                        	
						</logic:present>
						<logic:notPresent name="permitirAtualizarConclusao" scope="session">                        	
							<html:text property="idImovel" maxlength="9" size="9"  disabled="true"
								onkeypress="javascript:return validaEnter(event, 'atualizarImovelWizardAction.do?action=exibirAtualizarImovelConclusaoAction&pesquisar=SIM', 'idImovel');" />

		   		        	<logic:present name="idImovelPrincipalNaoEncontrado" scope="request">
                        		<input type="text" name="matriculaImovelPrincipal" disabled="disabled" size="50" readonly="readonly" style="background-color:#EFEFEF; border:0; color: #ff0000"
                        			value="${valorMatriculaImovelPrincipal}">
                        	</logic:present>
                        	<logic:notPresent name="idImovelPrincipalNaoEncontrado" scope="request">
                        		<logic:present name="valorMatriculaImovelPrincipal" scope="request">
                        			<input type="text" name="matriculaImovelPrincipal" disabled="disabled" size="50" readonly="readonly" style="background-color:#EFEFEF; border:0; color: #000000"
    	                    				value="<bean:write name="valorMatriculaImovelPrincipal" scope="request" />">
                        		</logic:present>
                        		<logic:notPresent name="valorMatriculaImovelPrincipal" scope="request">
                        			<input type="text" name="matriculaImovelPrincipal" disabled="disabled" size="50" readonly="readonly" style="background-color:#EFEFEF; border:0; color: #000000"
	                        				value="">
                        		</logic:notPresent>
                        	</logic:notPresent>
						</logic:notPresent>


					</td>
				</tr>
				<tr>
					<!-- *************** -->
					<td colspan="2">
					<table width="100%" cellpadding="0" cellspacing="0" >
						<tr>
							<td>
							<table width="100%" border="0" cellpadding="1" cellspacing="0"
								bgcolor="#99CCFF" bordercolor="#99CCFF">
								<!--header da tabela interna -->
								<tr>
									<td align="center"><strong>Endere&ccedil;o</strong></td>
								</tr>
							</table>
							</td>
						</tr>

						<logic:present name="imoveisPrincipal">

							<tr>
								<td height="40">
								<div style="width: 100%; height: 100%; overflow: auto;">
								<table width="100%" align="center" bgcolor="#99CCFF">
									<!--corpo da segunda tabela-->

									<%String cor = "#FFFFFF";%>
									<logic:present name="imoveisPrincipal">
										<logic:iterate name="imoveisPrincipal" id="imovel"
											type="Imovel">

											<%if (cor.equalsIgnoreCase("#FFFFFF")) {
				cor = "#FFFFFF";%>
											<tr bgcolor="#FFFFFF">
												<%} else {
				cor = "#cbe5fe";%>
											<tr bgcolor="#cbe5fe">
												<%}%>
												<td align="center"><bean:write name="imovel"
													property="enderecoFormatado" /></td>
											</tr>
										</logic:iterate>
									</logic:present>
								</table>
								</div>
								</td>
							</tr>

						</logic:present>
					</table>
					</td>
				</tr>
				
				<tr>
					<td width="26%"><strong>Funcionário:</strong></td>
					<td width="74%">
						<logic:present name="permitirAtualizarConclusao" scope="session">
							<html:text maxlength="9" 
								property="idFuncionario" 
								size="9"
								onkeypress="javascript:return validaEnter(event, 'atualizarImovelWizardAction.do?action=exibirAtualizarImovelConclusaoAction', 'idFuncionario');"
								onkeyup="limparCamposFuncionario();" /> 
						</logic:present>
						<logic:notPresent name="permitirAtualizarConclusao" scope="session">
							<html:text maxlength="9" disabled="true"
								property="idFuncionario" 
								size="9"
								onkeypress="javascript:return validaEnter(event, 'atualizarImovelWizardAction.do?action=exibirAtualizarImovelConclusaoAction', 'idFuncionario');"
								onkeyup="limparCamposFuncionario();" /> 
						</logic:notPresent>
							
						<logic:present name="permitirAtualizarConclusao" scope="session">											
							<a href="javascript:abrirPopup('exibirPesquisarFuncionarioAction.do?limpaForm=S', 495, 300);">
								<img src="<bean:message key='caminho.imagens'/>pesquisa.gif"
									width="23" 
									height="21" 
									alt="Pesquisar" 
									border="0"></a>
						</logic:present> 

		   		        <logic:present name="idImovelPrincipalEncontrado" scope="request">
		   		        	<logic:present name="permitirAtualizarConclusao" scope="session">
								<html:text property="nomeFuncionario" 
									size="40"
									maxlength="40" 
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</logic:present>
							<logic:notPresent name="permitirAtualizarConclusao" scope="session">
								<html:text property="nomeFuncionario" disabled="true"
									size="40"
									maxlength="40" 
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />							
							</logic:notPresent>
						</logic:present> 
						
						<logic:notPresent name="idImovelPrincipalEncontrado" scope="request">						
							<logic:present name="permitirAtualizarConclusao" scope="session">
								<html:text property="nomeFuncionario" 
									size="40"
									maxlength="40" 
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: red" />								
							</logic:present>
							<logic:notPresent name="permitirAtualizarConclusao" scope="session">
								<html:text property="nomeFuncionario" disabled="true" 
									size="40"
									maxlength="40" 
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: red" />								
							</logic:notPresent>								
						</logic:notPresent> 
						
						<logic:present name="permitirAtualizarConclusao" scope="session">
							<a href="javascript:limparFuncionario();"> 
								<img border="0" src="imagens/limparcampo.gif" height="21" width="23">
							</a>
						</logic:present>
					</td>
				</tr>
				</logic:equal>
				
				<!-- **** -->



				<tr>
					<td colspan="2">
					<div align="right"><jsp:include
						page="/jsp/util/wizard/navegacao_botoes_wizard_valida_avancar.jsp?numeroPagina=6" />
					</div>
					</td>
				</tr>

			</table>
	</table>
	<%@ include file="/jsp/util/rodape.jsp"%>
	<%@ include file="/jsp/util/tooltip.jsp"%>
</html:form>
</div>
</body>

<%@ include file="/jsp/util/telaespera.jsp"%>

<script>
document.getElementById('botaoConcluir').onclick = function() { botaoAvancarTelaEspera('/gsan/atualizarImovelWizardAction.do?concluir=true&action=atualizarImovelConclusaoAction'); }
</script>

</html:html>
