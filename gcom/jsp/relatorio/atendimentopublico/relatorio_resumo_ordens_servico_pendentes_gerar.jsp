<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<%@ page import="gcom.util.ConstantesSistema"%>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>

<html:javascript staticJavascript="false"  formName="GerarRelatorioResumoOrdensServicoPendentesActionForm"/>

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>

<script language="JavaScript">
	
	function validarForm(){
		
		var form = document.forms[0];
		if(validateGerarRelatorioResumoOrdensServicoPendentesActionForm(form)
			&& validarCampos()){

			enviarSelectMultiplo('GerarRelatorioResumoOrdensServicoPendentesActionForm','tipoServicoSelecionados');
			toggleBox('demodiv',1);
		}
	}
	
	function validarCampos(){
		
		var form = document.forms[0];
		
		
		msg = verificarAtributosInicialFinal(form.idLocalidadeInicial,form.idLocalidadeFinal,"Código da Localidade Inicial", "Código da Localidade Final");
		if( msg != ""){
			alert(msg);
			return false;
		}else{
			msg = verificarAtributosInicialFinal(form.codigoSetorComercialInicial,form.codigoSetorComercialFinal,"Código do Setor Comercial Inicial", "Código do Setor Comercial Final");
			if( msg != ""){
				alert(msg);
				return false;
			}
		}
	
		return true;
	}

	function validarDatas(){

		var form = document.forms[0];
		
		var periodoGeracaoInicial = form.periodoGeracaoInicial.value;
		var periodoGeracaoFinal = form.periodoGeracaoFinal.value;

		if (isBrancoOuNulo(periodoGeracaoInicial) && isBrancoOuNulo(periodoGeracaoInicial)){
			alert("Informe:\no Período");
			return false;
		} else if (isBrancoOuNulo(periodoGeracaoInicial)){
			alert("Informe:\nPeríodo Inicial");
			return false;
		} else if (isBrancoOuNulo(periodoGeracaoFinal)){
			alert("Informe:\nPeríodo Final");
			return false;
		}

		return true;

	}

	function validarIntervalos(){

		var form = document.forms[0];
		
		var periodoGeracaoInicial = form.periodoGeracaoInicial.value;
		var periodoGeracaoFinal = form.periodoGeracaoFinal.value;

		// Verificamos se a data final informada é menor que a data inicial
	    if (comparaData(periodoGeracaoFinal, '<', periodoGeracaoInicial)){
			  alert("A data final deve ser maior que a data inicial");
			  return false;
	    }
	    return true;
	}	
	
	function verificarAtributosInicialFinal(campoInicio, campoFim, nomeCampoInicio, nomeCampoFim){
		
		var msg = ""; 
        
		if (campoInicio.value.length > 0 && (campoFim.value == null || campoFim.value == '')) {
			campoFim.value = campoInicio.value;
		}else if (campoInicio.value.length > 0 && campoFim.value.length < 1){
			msg = "Informe "+nomeCampoFim+".";
		} else if (campoInicio.value.length < 1 && campoFim.value.length > 0) {
			msg = "Informe "+nomeCampoInicio+".";
		}else {
			if (campoInicio.value > campoFim.value){
				msg = nomeCampoFim+" deve ser maior ou igual a "+nomeCampoInicio+".";
			}
		}
		
		return msg;
	}
	
	
	
	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,campo){
		if(!campo.disabled){
	  		if (objeto == null || codigoObjeto == null){
	     		if(tipo == "" ){
	      			abrirPopup(url,altura, largura);
	     		}else{
		  			abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
		 		}
	 		}else{
				if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
					alert(msg);
				}else{
					abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto, altura, largura);
				}
			}
  		}
	}
	
  	function limpar(){

  		var form = document.forms[0];
  		
		form.idLocalidadeInicial.value = '';
		form.descricaoLocalidadeInicial.value = '';
		form.idLocalidadeFinal.value = '';
		form.descricaoLocalidadeFinal.value = '';

		form.codigoSetorComercialInicial.value = '';
		form.descricaoSetorComercialInicial.value = '';
		form.codigoSetorComercialFinal.value = '';
		form.descricaoSetorComercialFinal.value = '';
  		form.idGerenciaRegional.value = '';
		form.idGerenciaRegional.selectedIndex = 0;
  		form.idUnidadeNegocio.value = '';
		form.idUnidadeNegocio.selectedIndex = 0;		
		form.periodoGeracaoInicial.value = "";
		form.periodoGeracaoFinal.value = "";
		
		for(i=form.tipoServicoSelecionados.length-1; i>0; i--) {
			form.tipoServicoSelecionados.options[i] = null;
		}

  		form.action='exibirGerarRelatorioResumoOrdensServicoPendentesAction.do?menu=sim';
	    form.submit();
  	}
  	
  	function replicarLocalidade(){
		var formulario = document.forms[0]; 
		
		formulario.idLocalidadeFinal.value = formulario.idLocalidadeInicial.value;
		formulario.codigoSetorComercialInicial.focus;
	}
	
	function replicarSetorComercial(){
		var formulario = document.forms[0]; 
		formulario.codigoSetorComercialFinal.value = formulario.codigoSetorComercialInicial.value;
	} 
	
	function limparOrigem(tipo){
		var form = document.forms[0];
		
		switch(tipo){
		
		case 1: //De localidade pra baixo

			form.descricaoLocalidadeInicial.value = "";
			form.idLocalidadeFinal.value = "";
			form.descricaoLocalidadeFinal.value = "";
			form.codigoSetorComercialInicial.value = "";
		    form.codigoSetorComercialFinal.value = "";
		    form.descricaoSetorComercialInicial.value = "";
			form.descricaoSetorComercialFinal.value = "";
		    
		case 2: //De setor para baixo

		   form.descricaoSetorComercialInicial.value = "";
		   form.codigoSetorComercialFinal.value = "";
		   form.descricaoSetorComercialFinal.value = "";
		}
	}
	
	function limparBorrachaOrigem(tipo){
		var form = document.forms[0];
		
		switch(tipo){
			case 1: //De localidara pra baixo

				form.idLocalidadeInicial.value = "";
				form.descricaoLocalidadeInicial.value = "";
				form.idLocalidadeFinal.value = "";
				form.descricaoLocalidadeFinal.value = "";
				form.codigoSetorComercialInicial.value = "";
		     	form.descricaoSetorComercialInicial.value = "";
		     	form.codigoSetorComercialFinal.value = "";
		     	form.descricaoSetorComercialFinal.value = "";
				break;
			case 2: //De setor para baixo
		     	
		     	form.codigoSetorComercialInicial.value = "";
		     	form.descricaoSetorComercialInicial.value = "";
		     	form.codigoSetorComercialFinal.value = "";
		     	form.descricaoSetorComercialFinal.value = "";
		}
	}
	
	function limparBorrachaDestino(tipo){
		var form = document.forms[0];

		switch(tipo){
			case 1: //De localidade pra baixo
				form.idLocalidadeFinal.value = "";
				form.descricaoLocalidadeFinal.value = "";
				form.codigoSetorComercialFinal.value = "";
		   		form.descricaoSetorComercialFinal.value = "";
				
			case 2: //De setor para baixo		   
		   		form.codigoSetorComercialFinal.value = ""; 
		   		form.descricaoSetorComercialFinal.value = "";		   
		}
	}
	
	
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

		var form = document.forms[0];

		if (tipoConsulta == 'localidadeOrigem') {
      		
      		form.idLocalidadeInicial.value = codigoRegistro;
	  		form.descricaoLocalidadeInicial.value = descricaoRegistro;
	  		
	  		form.idLocalidadeFinal.value = codigoRegistro;
      		form.descricaoLocalidadeFinal.value = descricaoRegistro;
      		
	  		form.descricaoLocalidadeInicial.style.color = "#000000";
	  		form.descricaoLocalidadeFinal.style.color = "#000000";
	  		
	  		form.codigoSetorComercialInicial.focus();
		}

		if (tipoConsulta == 'localidadeDestino') {
		
      		form.idLocalidadeFinal.value = codigoRegistro;
      		form.descricaoLocalidadeFinal.value = descricaoRegistro;
	  		form.descricaoLocalidadeFinal.style.color = "#000000";

	  		form.codigoSetorComercialFinal.focus();
		}
	}
	
	
	function recuperarDadosQuatroParametros(idRegistro, descricaoRegistro, codigoRegistro, tipoConsulta) {

		var form = document.forms[0];

		if (tipoConsulta == 'setorComercialOrigem') {
		  	form.codigoSetorComercialInicial.value = codigoRegistro;
		  	form.descricaoSetorComercialInicial.value = descricaoRegistro;
		  	form.descricaoSetorComercialInicial.style.color = "#000000"; 
		  	
		  	form.codigoSetorComercialFinal.value = codigoRegistro;
		  	form.descricaoSetorComercialFinal.value = descricaoRegistro;
		  	form.descricaoSetorComercialFinal.style.color = "#000000";
		  	
		}

		if (tipoConsulta == 'setorComercialDestino') {
		  	form.codigoSetorComercialFinal.value = codigoRegistro;
		  	form.descricaoSetorComercialFinal.value = descricaoRegistro;
		  	form.descricaoSetorComercialFinal.style.color = "#000000";
		}
	}

	function replicaDataGeracao() {
		var form = document.forms[0];
		form.periodoGeracaoFinal.value = form.periodoGeracaoInicial.value;
	}
	  	
</script>

</head>

<body leftmargin="5" topmargin="5">

<html:form action="/gerarRelatorioResumoOrdensServicoPendentesAction.do"
	name="GerarRelatorioResumoOrdensServicoPendentesActionForm"
	type="gcom.gui.relatorio.atendimentopublico.ordemservico.GerarRelatorioResumoOrdensServicoPendentesActionForm"
	method="post">

<%@ include file="/jsp/util/cabecalho.jsp"%>
<%@ include file="/jsp/util/menu.jsp" %>

<table width="770" border="0" cellspacing="4" cellpadding="0">
	  <tr>
	    <td width="149" valign="top" class="leftcoltext">
	      <div align="center">
	
			<%@ include file="/jsp/util/informacoes_usuario.jsp"%>
	
			<%@ include file="/jsp/util/mensagens.jsp"%>

	      	</div>
	      	</td>
			<td width="600" valign="top" class="centercoltext">
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
					<td class="parabg">Gerar Relat&oacute;rio  Resumo de Ordens de Serviço Pendentes</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
			<table width="100%" border="0">
				
				<tr>
					<td colspan="2">Para gerar o relat&oacute;rio, informe os dados abaixo:</td>
				</tr>

				<tr>
					<td>
						<strong>Ger&ecirc;ncia Regional:</strong>
					</td>

					<td>
						<strong> 
						<html:select property="idGerenciaRegional" 
							style="width: 230px;">
							
							<html:option
								value="">&nbsp;
							</html:option>
					
							<logic:present name="colecaoGerenciaRegional" scope="request">
								<html:options collection="colecaoGerenciaRegional"
									labelProperty="nome" 
									property="id" />
							</logic:present>
						</html:select> 														
						</strong>
					</td>
				</tr>		

				<tr>
					<td>
						<strong>Unidade de Neg&oacute;cio:</strong>
					</td>

					<td>
						<strong> 
						<html:select property="idUnidadeNegocio" 
							style="width: 230px;">
							
							<html:option
								value="">&nbsp;
							</html:option>
					
							<logic:present name="colecaoUnidadeNegocio" scope="request">
								<html:options collection="colecaoUnidadeNegocio"
									labelProperty="nome" 
									property="id" />
							</logic:present>
						</html:select> 														
						</strong>
					</td>
				</tr>		
              	
				<tr>
					<td><strong>Localidade Inicial:<font color="#FF0000">*</font></strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							tabindex="1"
							property="idLocalidadeInicial" 
							size="3"
							onblur="javascript:replicarLocalidade();" 
							onkeyup="javascript:limparOrigem(1);"
							onkeypress="javascript:limparOrigem(1);validaEnter(event, 'exibirGerarRelatorioResumoOrdensServicoPendentesAction.do?objetoConsulta=1', 'idLocalidadeInicial');"/>
							
						<a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'origem', null , null, 275, 480, null,document.forms[0].idLocalidadeInicial);limparOrigem(1);">
							<img width="23" 
								height="21" 
								border="0" 
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Localidade" /></a>
								

						<logic:present name="localidadeInicialEncontrada" scope="request">
							<html:text property="descricaoLocalidadeInicial" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 

						<logic:notPresent name="localidadeInicialEncontrada" scope="request">
							<html:text property="descricaoLocalidadeInicial" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>

						
						<a href="javascript:limparBorrachaOrigem(1);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>
				
				<tr>
					<td><strong>Setor Comercial Inicial:</strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							tabindex="2"
							property="codigoSetorComercialInicial" 
							size="3"
							onblur="javascript:replicarSetorComercial();"
							onkeyup="limparOrigem(2);"
							onkeypress="javascript:limparOrigem(2);validaEnterDependencia(event, 'exibirGerarRelatorioResumoOrdensServicoPendentesAction.do?objetoConsulta=2', document.forms[0].codigoSetorComercialInicial, document.forms[0].idLocalidadeInicial.value, 'Localidade Inicial');"/>
							
						<a href="javascript:chamarPopup('exibirPesquisarSetorComercialAction.do', 'setorComercialOrigem', 'idLocalidade', document.forms[0].idLocalidadeInicial.value , 275, 480, 'Informe Localidade Inicial',document.forms[0].codigoSetorComercialInicial);
						         limparOrigem(2);">
							<img width="23" 
								height="21" 
								border="0" 
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Setor Comercial" /></a>
								

						<logic:present name="setorComercialInicialEncontrado" scope="request">
							<html:text property="descricaoSetorComercialInicial" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 

						<logic:notPresent name="setorComercialInicialEncontrado" scope="request">
							<html:text property="descricaoSetorComercialInicial" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>
						
						<a href="javascript:limparBorrachaOrigem(2);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>
				
				<tr>
					<td><strong>Localidade Final:</strong></td>
					
					<td>
						
						<html:text maxlength="3" 
							tabindex="3"
							property="idLocalidadeFinal" 
							size="3"
							onkeypress="validaEnter(event, 'exibirGerarRelatorioResumoOrdensServicoPendentesAction.do?objetoConsulta=3', 'idLocalidadeFinal');"/>
							
						<a href="javascript:chamarPopup('exibirPesquisarLocalidadeAction.do', 'destino', null, null, 275, 480, null,document.forms[0].idLocalidadeFinal);limparBorrachaDestino(1);">
							<img width="23" 
								height="21" 
								border="0" 
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Localidade" /></a>
								 

						<logic:present name="localidadeFinalEncontrada" scope="request">
							<html:text property="descricaoLocalidadeFinal" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 

						<logic:notPresent name="localidadeFinalEncontrada" scope="request">
							<html:text property="descricaoLocalidadeFinal" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>

						
						<a href="javascript:limparBorrachaDestino(1);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>
				
				<tr>
					<td><strong>Setor Comercial Final:</strong></td>
					
					<td>
						
						<html:text maxlength="3" property="codigoSetorComercialFinal"
							size="3"
							onkeypress="validaEnterDependencia(event, 'exibirGerarRelatorioResumoOrdensServicoPendentesAction.do?objetoConsulta=4', document.forms[0].codigoSetorComercialFinal, document.forms[0].idLocalidadeFinal.value, 'Localidade Final');"
							tabindex="4"/>
								
						<a href="javascript:chamarPopup('exibirPesquisarSetorComercialAction.do', 'setorComercialDestino', 'idLocalidade', document.forms[0].idLocalidadeFinal.value, 275, 480, 'Informe Localidade Final',document.forms[0].codigoSetorComercialFinal);
							        limparBorrachaDestino(2);">
							<img width="23" 
								height="21" 
								border="0" 
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								title="Pesquisar Setor Comercial" /></a>
								

						<logic:present name="setorComercialFinalEncontrado" scope="request">
							<html:text property="descricaoSetorComercialFinal" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:present> 

						<logic:notPresent name="setorComercialFinalEncontrado" scope="request">
							<html:text property="descricaoSetorComercialFinal" 
								size="30"
								maxlength="30" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: red" />
						</logic:notPresent>
						
						<a href="javascript:limparBorrachaDestino(2);"> 
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" 
								title="Apagar" /></a>
					</td>
				</tr>
				
				<tr>
             		<td height="10" colspan="2"> 
	             		<div align="right"> 
	                 		<hr>
	               		</div>
	               		<div align="right"> </div>
               		</td>
           		</tr>
				
				<tr>
					<td width="120"><strong>Tipo de Servi&ccedil;o:</strong></td>
					<td colspan="2">
					<table width="100%" border=0 cellpadding=0 cellspacing=0
						align="left">
						<tr>
							<td width="175">

							<div align="left"><strong>Disponíveis</strong></div>

							<html:select property="tipoServico" size="6" multiple="true"
								style="width:190px" tabindex="5">

								<html:options collection="colecaoTipoServico"
									labelProperty="descricao" property="id" />
							</html:select></td>

							<td width="5"><br>
							<table width="50" align="center">
								<tr>
									<td align="center"><input type="button" class="bottonRightCol"
										onclick="javascript:MoverTodosDadosSelectMenu1PARAMenu2('GerarRelatorioResumoOrdensServicoPendentesActionForm', 'tipoServico', 'tipoServicoSelecionados');"
										value=" &gt;&gt; " tabindex="6"></td>
								</tr>

								<tr>
									<td align="center"><input type="button" class="bottonRightCol"
										onclick="javascript:MoverDadosSelectMenu1PARAMenu2('GerarRelatorioResumoOrdensServicoPendentesActionForm', 'tipoServico', 'tipoServicoSelecionados');"
										value=" &nbsp;&gt;  " tabindex="7"></td>
								</tr>

								<tr>
									<td align="center"><input type="button" class="bottonRightCol"
										onclick="javascript:MoverDadosSelectMenu2PARAMenu1('GerarRelatorioResumoOrdensServicoPendentesActionForm', 'tipoServico', 'tipoServicoSelecionados');"
										value=" &nbsp;&lt;  " tabindex="8"></td>
								</tr>

								<tr>
									<td align="center"><input type="button" class="bottonRightCol"
										onclick="javascript:MoverTodosDadosSelectMenu2PARAMenu1('GerarRelatorioResumoOrdensServicoPendentesActionForm', 'tipoServico', 'tipoServicoSelecionados');"
										value=" &lt;&lt; " tabindex="9"></td>
								</tr>
							</table>
							</td>

							<td>
							<div align="left"><strong>Selecionados</strong></div>

							<html:select property="tipoServicoSelecionados" size="6"
								multiple="true" style="width:190px" tabindex="10">

							</html:select></td>
						</tr>
					</table>
					</td>
				</tr>
				
				<tr>
             		<td height="10" colspan="2"> 
	             		<div align="right"> 
	                 		<hr>
	               		</div>
	               		<div align="right"> </div>
               		</td>
           		</tr>
				
				<tr>
	                <td>
	                	<strong>Per&iacute;odo de Gera&ccedil;&atilde;o:</strong>
	                </td>
                
	                <td colspan="6">
	                	<span class="style2">
	                	
	                	<strong> 
							
							<html:text property="periodoGeracaoInicial" 
								size="11" 
								maxlength="10" 
								tabindex="11" 
								onkeypress="javascript:return isCampoNumerico(event); " 
								onkeyup="mascaraData(this, event);replicaDataGeracao();"/>
							
							<a href="javascript:abrirCalendarioReplicando('GerarRelatorioResumoOrdensServicoPendentesActionForm', 'periodoGeracaoInicial','periodoGeracaoFinal');">
								<img border="0" 
									src="<bean:message key='caminho.imagens'/>calendario.gif" 
									width="16" 
									height="15" 
									border="0" 
									alt="Exibir Calendário"/></a>
							à 
							
							<html:text property="periodoGeracaoFinal" 
								size="11" 
								maxlength="10" 
								tabindex="12" 
								onkeypress="javascript:return isCampoNumerico(event); " 
								onkeyup="mascaraData(this, event)"/>
								
							<a href="javascript:abrirCalendario('GerarRelatorioResumoOrdensServicoPendentesActionForm', 'periodoGeracaoFinal');">
								<img border="0" 
									src="<bean:message key='caminho.imagens'/>calendario.gif" 
									width="16" 
									height="15" 
									border="0" 
									alt="Exibir Calendário"/></a>
							
							</strong>(dd/mm/aaaa)<strong> 
	                  	</strong>
	                  	</span>
	              	</td>
              	</tr>

				<tr>
					<td height="24" >
			          	<input type="button" 
			          		class="bottonRightCol" 
			          		value="Limpar" 
			          		onclick="javascript:limpar();"
			          		tabindex="14"/>
					</td>
				
					<td align="right">
						<input type="button" 
							name="Button" 
							class="bottonRightCol" 
							value="Gerar"
							tabindex="13" 
							onClick="javascript:validarForm()" />
					</td>
					
				</tr>
			</table>
			<p>&nbsp;</p>
			</td>
		</tr>
	</table>

<jsp:include page="/jsp/relatorio/escolher_tipo_relatorio.jsp?relatorio=gerarRelatorioResumoOrdensServicoPendentesAction.do&left=530&top=150" />

<%@ include file="/jsp/util/rodape.jsp"%>	
</html:form>
</body>
</html:html>