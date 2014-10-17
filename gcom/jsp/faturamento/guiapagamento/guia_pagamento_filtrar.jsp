<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="gcom.util.Pagina, gcom.util.ConstantesSistema"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gsan"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
	
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>

<html:javascript staticJavascript="false"  formName="FiltrarGuiaPagamentoActionForm"/>

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
	
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
	
<script language="Javascript">
	
	function validarForm(formulario){

		if(validateFiltrarGuiaPagamentoActionForm(formulario) && validaTodosPeriodos(formulario)){    		 
			enviarSelectMultiplo('FiltrarGuiaPagamentoActionForm','idTipoDebitoSelecionados');
			formulario.action = 'filtrarGuiaPagamentoAction.do';
			formulario.submit();
		}
	}

	function validarCampoInteiro(valorCampo, nomeCampo, descricaoCampo){
		
		var form = document.forms[0];

		if (valorCampo != null && !valorCampo == ""){

			if (!validaCampoInteiroPositivo(valorCampo)){

				if (nomeCampo == 'numeroGuia'){

					form.numeroGuia.value = "";

					alert(descricaoCampo + ' deve somente conter números positivos.');
				}
			}
		}
	}

	function setarAtualizar(){

		var formulario = document.forms[0];
		
		if (formulario.indicadorAtualizar.value == "1"){

			formulario.indicadorAtualizar.value = "2"; 
		}else{

			formulario.indicadorAtualizar.value = "1";
		}
	}


	function limparImovel() {

        var form = document.FiltrarGuiaPagamentoActionForm;
		
		form.codigoClienteGuia.disabled = false;
        form.matriculaImovel.value = "";
        form.inscricaoImovel.value = "";
        form.inscricaoImovel.style.color = "#000000";
    }
    
    function limparClienteResponsavel() {

        var form = document.FiltrarGuiaPagamentoActionForm;
	
        form.codigoClienteResponsavel.value = "";
        form.nomeClienteResponsavel.value = "";
        form.idTipoRelacao.disabled = false;
        form.nomeClienteResponsavel.style.color = "#000000";
    }

    function limparClienteGuia() {

        var form = document.FiltrarGuiaPagamentoActionForm;
	
		form.matriculaImovel.disabled = false;
		form.codigoClienteResponsavel.disabled = false;
        form.codigoClienteGuia.value = "";
        form.idTipoRelacao.disabled = false;
        form.nomeClienteGuia.value = "";
        form.nomeClienteGuia.style.color = "#000000";
    }

    function limparLocalidade() {

        var form = document.FiltrarGuiaPagamentoActionForm;
	
        form.idLocalidade.value = "";
        form.nomeLocalidade.value = "";
        form.nomeLocalidade.style.color = "#000000";
    }

    function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){

		if(objetoRelacionado.readOnly != true){

			if (objeto == null || codigoObjeto == null){

				abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
			} else{

				if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){

					alert(msg);
				} else{

					abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
				}
			}
		}
	}

	function desabilitarCampos(campo){

		 var form = document.FiltrarGuiaPagamentoActionForm;		 

		 if (campo == 'numeroGuia' && form.numeroGuia.value.length > 0){
			 /*
			 form.matriculaImovel.value = '';
			 form.inscricaoImovel.value = '';
			 form.matriculaImovel.disabled = true;
			 form.codigoClienteResponsavel.value = '';
			 form.nomeClienteResponsavel.value = '';
			 form.codigoClienteResponsavel.disabled = true;
			 form.idTipoRelacao.selectedIndex = -1;
			 form.idTipoRelacao.disabled = true;
			 form.codigoClienteGuia.value = '';
			 form.nomeClienteGuia.value = '';
			 form.codigoClienteGuia.disabled = true;
			 form.idLocalidade.value = '';
			 form.nomeLocalidade.value = '';
			 form.idLocalidade.disabled = true;
			 form.periodoReferenciaFaturamentoInicial.value = '';
			 form.periodoReferenciaFaturamentoFinal.value = '';
			 form.periodoReferenciaFaturamentoInicial.disabled = true;
			 form.periodoReferenciaFaturamentoFinal.disabled = true;
			 form.periodoEmissaoInicial.value = '';
			 form.periodoEmissaoFinal.value = '';
			 form.periodoEmissaoInicial.disabled = true;
			 form.periodoEmissaoFinal.disabled = true;
			 form.periodoVencimentoInicial.value = '';
			 form.periodoVencimentoFinal.value = '';
			 form.periodoVencimentoInicial.disabled = true;
			 form.periodoVencimentoFinal.disabled = true;
			 form.idTipoDocumento.selectedIndex = -1;
			 //form.idTipoDocumento.disabled = true;
			 form.idTipoDebitoDisponiveis.disabled = true;
			 form.idTipoDebitoSelecionados.value = '';
			 */
		 }

		if (form.matriculaImovel.value.length == 0){
			form.codigoClienteGuia.disabled = false;
		}

		if (form.codigoClienteGuia.value.length == 0){
			form.matriculaImovel.disabled = false;
			form.codigoClienteResponsavel.disabled = false;
			form.idTipoRelacao.disabled = false;
		}
		
		if (form.codigoClienteResponsavel.value.length == 0){
			form.idTipoRelacao.disabled = false;
		}

		if (form.matriculaImovel.value.length > 0){
			form.codigoClienteGuia.value = '';
			form.nomeClienteGuia.value = '';
			form.codigoClienteGuia.disabled = true;
		}
		
		if (form.codigoClienteGuia.value.length > 0){
			form.matriculaImovel.value = '';
			form.inscricaoImovel.value = '';
			form.matriculaImovel.disabled = true;
			form.codigoClienteResponsavel.value = '';
			form.nomeClienteResponsavel.value = '';
			form.codigoClienteResponsavel.disabled = true;
			form.idTipoRelacao.selectedIndex = -1;
			form.idTipoRelacao.disabled = true;
		}
		
		if (form.codigoClienteResponsavel.value.length > 0){
			form.idTipoRelacao.selectedIndex = -1;
			form.idTipoRelacao.disabled = true;
		}
	}

	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

	    var form = document.forms[0];
	    
	    if (tipoConsulta == 'imovel') {

	      form.matriculaImovel.value = codigoRegistro;
	      form.inscricaoImovel.value = descricaoRegistro;
	      form.inscricaoImovel.style.color = "#000000";
	      form.matriculaImovel.disabled = false;
		  form.codigoClienteGuia.value = '';
		  form.nomeClienteGuia.value = '';
		  form.codigoClienteGuia.disabled = true;
		  form.codigoClienteResponsavel.disabled = false;
		  form.idTipoRelacao.disabled = false;
	    
	    } else if (tipoConsulta == 'cliente' && form.indicadorPesquisaClienteResponsavel.value == 'true') {

		    form.codigoClienteResponsavel.value = codigoRegistro;
		    form.nomeClienteResponsavel.value = descricaoRegistro;
	      	form.nomeClienteResponsavel.style.color = "#000000";
	      	form.indicadorPesquisaClienteResponsavel.value = 'false';
	      	form.idTipoRelacao.selectedIndex = -1;
			form.idTipoRelacao.disabled = true;
	     
   	    }else if (tipoConsulta == 'cliente' && form.indicadorPesquisaClienteGuia.value == 'true'){

   	   	    form.codigoClienteGuia.value = codigoRegistro;
	    	form.nomeClienteGuia.value = descricaoRegistro;
      		form.nomeClienteGuia.style.color = "#000000";
      		form.indicadorPesquisaClienteGuia.value = 'false';
      		form.matriculaImovel.value = '';
			form.inscricaoImovel.value = '';
			form.matriculaImovel.disabled = true;
			form.codigoClienteGuia.disabled = false;
			form.codigoClienteResponsavel.value = '';
			form.nomeClienteResponsavel.value = '';	
			form.codigoClienteResponsavel.disabled = true;
			form.idTipoRelacao.selectedIndex = -1;
			form.idTipoRelacao.disabled = true;
   	    }else if (tipoConsulta == 'localidade'){

   	    	form.idLocalidade.value = codigoRegistro;
   	        form.nomeLocalidade.value = descricaoRegistro;
   	        form.nomeLocalidade.style.color = "#000000";
   	    }
	}

	function habilitarPesquisaClienteImovel(campo){

		 var form = document.FiltrarGuiaPagamentoActionForm;

		 if (campo == 'matriculaImovel' && form.codigoClienteGuia.value.length == 0){

			 chamarPopup('exibirPesquisarImovelAction.do', 'imovel', null, null, 600, 730, '', form.matriculaImovel);
			 	 
		 }else if (campo == 'clienteGuia' && form.matriculaImovel.value.length == 0){

			 form.indicadorPesquisaClienteGuia.value = 'true';
			 chamarPopup('exibirPesquisarClienteAction.do', 'cliente', null, null, 600, 730, '', form.codigoClienteResponsavel);		 
		 }else if (campo == 'clienteResponsavel' && form.codigoClienteGuia.value.length == 0){

			 form.indicadorPesquisaClienteResponsavel.value = 'true';
			 chamarPopup('exibirPesquisarClienteAction.do', 'cliente', null, null, 600, 730, '', form.codigoClienteGuia);	 
		 }
	}

	function validarEnterClienteGuia(event){

		var form = document.FiltrarGuiaPagamentoActionForm;
		
		validaEnterComMensagem(event, 'exibirFiltrarGuiaPagamentoAction.do?objetoConsulta=3','codigoClienteGuia','Cliente da Guia');
	}

	function validarEnterClienteResponsavel(event){

		var form = document.FiltrarGuiaPagamentoActionForm;
		
		validaEnterComMensagem(event, 'exibirFiltrarGuiaPagamentoAction.do?objetoConsulta=2','codigoClienteResponsavel','Cliente Responsável');
	}

	function validarEnterMatriculaImovel(event){

		var form = document.FiltrarGuiaPagamentoActionForm;

		validaEnterComMensagem(event, 'exibirFiltrarGuiaPagamentoAction.do?objetoConsulta=1','matriculaImovel','Imóvel da Guia');
	}

	function desabilitarCamposDesnecessarios(){

		var form = document.FiltrarGuiaPagamentoActionForm;
		
		if (form.codigoClienteGuia.value.length > 0){

			 form.matriculaImovel.value = '';
			 form.inscricaoImovel.value = '';
			 form.matriculaImovel.disabled = true;
			 form.codigoClienteGuia.disabled = false;
			 form.codigoClienteResponsavel.value = '';
			 form.nomeClienteResponsavel.value = '';	
			 form.codigoClienteResponsavel.disabled = true;
			 form.idTipoRelacao.selectedIndex = -1;
			 form.idTipoRelacao.disabled = true;
		}

		if (form.matriculaImovel.value.length > 0){

			 form.matriculaImovel.disabled = false;
			 form.codigoClienteGuia.value = '';
			 form.nomeClienteGuia.value = '';
			 form.codigoClienteGuia.disabled = true;
			 form.codigoClienteResponsavel.disabled = false;
			 form.idTipoRelacao.disabled = false;
		}

		if (form.codigoClienteResponsavel.value.length > 0){

			form.idTipoRelacao.selectedIndex = -1;
			form.idTipoRelacao.disabled = true;
		}
	}

	function validaTodosPeriodos(form) {

		if (comparaMesAno(form.periodoReferenciaFaturamentoInicial.value, '>', form.periodoReferenciaFaturamentoFinal.value)){

			alert('Mês/Ano Final do Período de Referência do Faturamento é anterior ao Mês/Ano Inicial');
			return false;
		} else if (comparaData(form.periodoEmissaoInicial.value, '>', form.periodoEmissaoFinal.value)){

			alert('Data Final do Período de Emissão é anterior à Data Inicial');
			return false;

		} else if (comparaData(form.periodoVencimentoInicial.value, '>', form.periodoVencimentoFinal.value)){

			alert('Data Final do Período de Vencimento é anterior à Data Inicial');
			return false;

		}
		
		return true;
    }

</script>

</head>


<body leftmargin="5" topmargin="5"
	onload="document.forms[0].indicadorAtualizar.checked=true; desabilitarCamposDesnecessarios()">
<html:form action="/filtrarGuiaPagamentoAction" method="post"
	onsubmit="return validateFiltrarGuiaPagamentoActionForm(this);">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>
	<html:hidden property="indicadorAtualizar" value="1"/>
	<html:hidden property="indicadorPesquisaClienteGuia" value="false"/>
	<html:hidden property="indicadorPesquisaClienteResponsavel" value="false"/>
	<table width="770" border="0" cellspacing="5" cellpadding="0">
		<tr>
			<td width="115" valign="top" class="leftcoltext">

			<div align="center">
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>

			<%@ include file="/jsp/util/informacoes_usuario.jsp"%>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>

			<%@ include file="/jsp/util/mensagens.jsp"%>

			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>
			<p align="left">&nbsp;</p>


			</div>
			</td>
			<td valign="top" class="centercoltext">

			<table>
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Filtrar Guia de Pagamento</td>
					<td width="11" valign="top"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>

			<p>&nbsp;</p>
			<table bordercolor="#000000" width="100%" cellspacing="0" >
				<tr>
					<td>
					Para manter a(s) Guia(s) de Pagamento, informe os dados abaixo:
					</td>
					<td width="84">
					<input name="atualizar" type="checkbox"
						checked="checked" onclick="javascript:setarAtualizar();" value="1"> <strong>Atualizar</strong>
					</td>
    			</tr>
   			</table>
   			
   			<table width="100%" border="0">
   			
   				<tr>
					<td width="15%"><strong>Número da Guia:</strong></td>
					<td><html:text property="numeroGuia" 
						size="9" 
						tabindex="1"
						maxlength="9" 
						onkeypress="return isCampoNumerico(event);" 
						onblur="javascript:validarCampoInteiro(this.value, 'numeroGuia', 'Número da Guia');desabilitarCampos('numeroGuia');"/>
					</td>
				</tr>
				
				<logic:present name="exibirNuContratoParcOrgaoPublico" scope="session">
				<tr>
					<td width="15%"><strong>No. Contrato Parcelamento Órgão Público:</strong></td>
					<td><html:text property="numeroContratoParcelOrgaoPublico" 
						size="9" 
						tabindex="1"
						maxlength="9" 
						onkeypress="return isCampoNumerico(event);"/>
					</td>
				</tr>
				</logic:present>
				
				<tr>
					<td><strong>Im&oacute;vel da Guia:</strong></td>
					
					<td>
						
						<html:text maxlength="9" 
							tabindex="2"
							property="matriculaImovel" 
							size="9"
							onkeypress="javascript:validarEnterMatriculaImovel(event);"
							onblur="javascript:desabilitarCampos('matriculaImovel');"/>
									
							
						<a href="javascript:habilitarPesquisaClienteImovel('matriculaImovel');">
						<img width="23" 
							height="21" 
							border="0"
							src="<bean:message key="caminho.imagens"/>pesquisa.gif"
							title="Pesquisar Imóvel" /></a> 
	
						<logic:present name="matriculaImovelEncontrada" scope="request">
							<html:text property="inscricaoImovel" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 
	
						<logic:notPresent name="matriculaImovelEncontrada" scope="request">
							<html:text property="inscricaoImovel" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>
						
						<a href="javascript:limparImovel();"> 
						<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
							border="0" 
							title="Apagar" />
						</a>
					</td>
				</tr>
				
				<tr>
					<td><strong>Cliente Respons&aacute;vel:</strong></td>
					
					<td>
						
						<html:text maxlength="9" 
							tabindex="3"
							property="codigoClienteResponsavel" 
							size="9"
							onkeypress="javascript:validarEnterClienteResponsavel(event);"
							onblur="javascript:desabilitarCampos('codigoClienteResponsavel');"/>
							
						<a href="javascript:habilitarPesquisaClienteImovel('clienteResponsavel');">
						<img width="23" 
							height="21" 
							border="0"
							src="<bean:message key="caminho.imagens"/>pesquisa.gif"
							title="Pesquisar Cliente Responsável" /></a> 			
	
						<logic:present name="codigoClienteResponsavelEncontrado" scope="request">
							<html:text property="nomeClienteResponsavel" 
								size="45"
								maxlength="45" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 
	
						<logic:notPresent name="codigoClienteResponsavelEncontrado" scope="request">
							<html:text property="nomeClienteResponsavel" 
								size="45"
								maxlength="45" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>
						
						<a href="javascript:limparClienteResponsavel();"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" />
						</a>			
					</td>
				</tr>
				
				<tr>
					<td>
						<strong>Tipo da Relação do Cliente com o Imóvel:</strong>
					</td>

					<td>
						<strong> 
						<html:select property="idTipoRelacao" multiple="true" style="width: 230px;" tabindex="4">
							<html:option
								value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
							</html:option>
					
							<logic:present name="colecaoClienteRelacaoTipo" scope="request">
								<html:options collection="colecaoClienteRelacaoTipo"
									labelProperty="descricao" 
									property="id" />
							</logic:present>
						</html:select> 														
						</strong>
					</td>
				</tr>
				
				<tr>
					<td><strong>Cliente da Guia:</strong></td>
					
					<td>
						
						<html:text maxlength="9" 
							tabindex="5"
							property="codigoClienteGuia" 
							size="9"
							onkeypress="javascript:validarEnterClienteGuia(event);"
							onblur="javascript:desabilitarCampos('codigoClienteGuia');"/>
							
						<a href="javascript:habilitarPesquisaClienteImovel('clienteGuia');">
						<img width="23" 
							height="21" 
							border="0"
							src="<bean:message key="caminho.imagens"/>pesquisa.gif"
							title="Pesquisar Cliente da Guia" /></a> 			

						<logic:present name="codigoClienteGuiaEncontrado" scope="request">
							<html:text property="nomeClienteGuia" 
								size="45"
								maxlength="45" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 

						<logic:notPresent name="codigoClienteGuiaEncontrado" scope="request">
							<html:text property="nomeClienteGuia" 
								size="45"
								maxlength="45" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>
						
						<a href="javascript:limparClienteGuia();"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" />
						</a>			
					</td>
				</tr>
				<tr>
					<td><strong>Localidade:</strong></td>
					
					<td>
						
						<html:text maxlength="9" 
							tabindex="6"
							property="idLocalidade" 
							size="9"
							onkeypress="javascript:validaEnterComMensagem(event, 'exibirFiltrarGuiaPagamentoAction.do?objetoConsulta=4','idLocalidade','Localidade');"/>
							
						<a href="javascript: chamarPopup('exibirPesquisarLocalidadeAction.do', 'localidade', null, null, 250, 495, '', document.forms[0].idLocalidade);">
						<img width="23" 
							height="21" 
							border="0"
							src="<bean:message key="caminho.imagens"/>pesquisa.gif"
							title="Pesquisar Localidade" /></a> 			
	
						<logic:present name="idLocalidadeEncontrado" scope="request">
							<html:text property="nomeLocalidade" 
								size="45"
								maxlength="45" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 
	
						<logic:notPresent name="idLocalidadeEncontrado" scope="request">
							<html:text property="nomeLocalidade" 
								size="45"
								maxlength="45" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>
						
						<a href="javascript:limparLocalidade();"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" />
						</a>			
					</td>
				</tr>
				
				<tr>
					<td>
						<strong>Per&iacute;odo de Referência do Faturamento:</strong>
					</td>
					<td>
						<span class="style2">
							<html:text maxlength="7"
								property="periodoReferenciaFaturamentoInicial" 
								size="7"
								onkeyup="mascaraAnoMes(this, event);replicarCampo(document.forms[0].periodoReferenciaFaturamentoFinal, document.forms[0].periodoReferenciaFaturamentoInicial);"
								tabindex="7" /> &nbsp; 
							<strong>a</strong> &nbsp; 
							<html:text maxlength="7"
								property="periodoReferenciaFaturamentoFinal" 
								size="7"
								onkeyup="mascaraAnoMes(this, event);" 
								tabindex="8" />
							</strong>(mm/aaaa)<strong>
						</span>
					</td>
				</tr>

              	<tr>
	                <td>
	                	<strong>Per&iacute;odo de Emiss&atilde;o:</strong>
	                </td>
                
	                <td>
	                	<span class="style2">
	                	
	                	<strong> 
							
							<html:text property="periodoEmissaoInicial" 
								size="11" 
								maxlength="10" 
								tabindex="9" 
								onkeyup="mascaraData(this, event);replicarCampo(document.forms[0].periodoEmissaoFinal, document.forms[0].periodoEmissaoInicial);"/>
							
							<a href="javascript:abrirCalendarioReplicando('FiltrarGuiaPagamentoActionForm', 'periodoEmissaoInicial','periodoEmissaoFinal');">
							<img border="0" 
								src="<bean:message key='caminho.imagens'/>calendario.gif" 
								width="16" 
								height="15" 
								border="0" 
								alt="Exibir Calendário"/>
							</a>
			
							a 
						
							<html:text property="periodoEmissaoFinal" 
								size="11" 
								maxlength="10" 
								tabindex="10" 
								onkeyup="mascaraData(this, event)"/>
							
							<a href="javascript:abrirCalendario('FiltrarGuiaPagamentoActionForm', 'periodoEmissaoFinal');">
							<img border="0" 
								src="<bean:message key='caminho.imagens'/>calendario.gif" 
								width="16" 
								height="15" 
								border="0" 
								alt="Exibir Calendário"/>
							</a>
							
						</strong>(dd/mm/aaaa)
	                  	</span>
	              	</td>
              	</tr>
              	
              	<tr>
	                <td>
	                	<strong>Per&iacute;odo de Vencimento:</strong>
	                </td>
                
	                <td>
	                	<span class="style2">
	                	
	                	<strong> 
							
							<html:text property="periodoVencimentoInicial" 
								size="11" 
								maxlength="10" 
								tabindex="11" 
								onkeyup="mascaraData(this, event);replicarCampo(document.forms[0].periodoVencimentoFinal, document.forms[0].periodoVencimentoInicial);"/>
							
							<a href="javascript:abrirCalendarioReplicando('FiltrarGuiaPagamentoActionForm', 'periodoVencimentoInicial','periodoVencimentoFinal');">
							<img border="0" 
								src="<bean:message key='caminho.imagens'/>calendario.gif" 
								width="16" 
								height="15" 
								border="0" 
								alt="Exibir Calendário"/>
							</a>
			
							a 
						
							<html:text property="periodoVencimentoFinal" 
								size="11" 
								maxlength="10" 
								tabindex="12" 
								onkeyup="mascaraData(this, event)"/>
							
							<a href="javascript:abrirCalendario('FiltrarGuiaPagamentoActionForm', 'periodoVencimentoFinal');">
							<img border="0" 
								src="<bean:message key='caminho.imagens'/>calendario.gif" 
								width="16" 
								height="15" 
								border="0" 
								alt="Exibir Calendário"/>
							</a>
							
						</strong>(dd/mm/aaaa)
	                  	</span>
	              	</td>
              	</tr>
              	
              	<tr>
					<td>
						<strong>Tipo do Documento:</strong>
					</td>

					<td>
						<strong> 
						<html:select property="idTipoDocumento" multiple="true" style="width: 230px;" tabindex="13">
							<html:option
								value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;
							</html:option>
					
							<logic:present name="colecaoDocumentoTipo" scope="request">
								<html:options collection="colecaoDocumentoTipo"
									labelProperty="descricaoDocumentoTipo" 
									property="id" />
							</logic:present>
						</html:select> 														
						</strong>
					</td>
				</tr>
			
				<tr>
					<td>
						<strong>Tipo do Débito:</strong>					
					</td>
					<td>
					<table width="100%" border=0 cellpadding=0 cellspacing=0 align="left">
						<tr>
							<td width="175">
							
								<div align="left"><strong>Disponíveis</strong></div>

								<html:select property="idTipoDebitoDisponiveis" 
									size="6"
									tabindex="14" 
									multiple="true" 
									style="width:190px">
								<logic:notEmpty name="colecaoDebitoTipo">	
									<html:options collection="colecaoDebitoTipo" 
										labelProperty="descricao" 
										property="id" />
								</logic:notEmpty>
								</html:select>
							</td>

							<td width="5"><br>
								<table width="50" align="center">
									<tr>
										<td align="center">
											<input type="button" 
												class="bottonRightCol"
												onclick="javascript:MoverTodosDadosSelectMenu1PARAMenu2('FiltrarGuiaPagamentoActionForm', 'idTipoDebitoDisponiveis', 'idTipoDebitoSelecionados');"
												value=" &gt;&gt; ">
										</td>
									</tr>
	
									<tr>
										<td align="center">
											<input type="button" 
												class="bottonRightCol"
												onclick="javascript:MoverDadosSelectMenu1PARAMenu2('FiltrarGuiaPagamentoActionForm', 'idTipoDebitoDisponiveis', 'idTipoDebitoSelecionados');"
												value=" &nbsp;&gt;  ">
										</td>
									</tr>
	
									<tr>
										<td align="center">
											<input type="button" 
												class="bottonRightCol"
												onclick="javascript:MoverDadosSelectMenu2PARAMenu1('FiltrarGuiaPagamentoActionForm', 'idTipoDebitoDisponiveis', 'idTipoDebitoSelecionados');"
												value=" &nbsp;&lt;  ">
										</td>
									</tr>
	
									<tr>
										<td align="center">
											<input type="button" 
												class="bottonRightCol"
												onclick="javascript:MoverTodosDadosSelectMenu2PARAMenu1('FiltrarGuiaPagamentoActionForm', 'idTipoDebitoDisponiveis', 'idTipoDebitoSelecionados');"
												value=" &lt;&lt; ">
										</td>
									</tr>
								</table>
							</td>

							<td>
								<div align="left">
									<strong>Selecionados</strong>
								</div>
								
								<html:select property="idTipoDebitoSelecionados" 
									size="6"
									tabindex="15" 
									multiple="true" 
									style="width:190px">
								
								</html:select>
							</td>
						</tr>
					</table>
					</td>
				</tr>
				
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>
						<input name="Submit22"
							class="bottonRightCol" 
							value="Limpar" 
							type="button"
							onclick="window.location.href='/gsan/exibirFiltrarGuiaPagamentoAction.do?menu=sim';"> 			
					</td>
					<td>
						<input type="button" 
								name="Button"
								class="bottonRightCol" 
								value="Cancelar" 
								tabindex="6"
								onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"
								style="width: 80px" />
					</td>
					
					<td width="65" align="right">
						<input name="Button2" 
							   type="button"
							class="bottonRightCol" 
							value="Filtrar" 
							align="right"
							onClick="javascript:validarForm(document.forms[0]);" 
							tabindex="7" />
						
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
