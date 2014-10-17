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

<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js">
	</script>
<html:javascript staticJavascript="false"
	formName="InserirQualidadeAguaActionForm" />




<script language="JavaScript">

function validarForm(){

		var form = document.forms[0];
		form.action = 'inserirQualidadeAguaAction.do';		
		if (form.referencia.readOnly == false) {
			
			var descricaoAnalise = form.descricaoConclusaoAnalisesRealizadas.value;
			if (descricaoAnalise.length > 100) {
			  alert('Descrição da Conclusão de Análise maior que 100 caracteres. ');
			  return false;
			}
			
			if(validateInserirQualidadeAguaActionForm(form)){
					if (!verificarAnoMesReferencia()){
						alert('Mês/Ano de Referência menor que o Corrente. ');
		   				return false;
					}
					
					if(!verificarValorAmostras()){
						alert('Nr. de Amostras Conformes informado possui valor maior que Nr. de Amostras Realizadas. ');
		   				return false;
					}
			
	    			submeterFormPadrao(form);
			}
		} else {
		
			submeterFormPadrao(form);
		}
}



function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

    var form = document.forms[0];

    if (tipoConsulta == 'localidade') {
      limparPesquisaLocalidade();
      form.idLocalidade.value = codigoRegistro;
      form.localidadeDescricao.value = descricaoRegistro;
      form.localidadeDescricao.style.color = "#000000";
      form.idSetorComercial.focus();
    }

    if (tipoConsulta == 'setorComercial') {
      limparPesquisaSetorComercial();
      form.idSetorComercial.value = codigoRegistro;
      form.setorComercialDescricao.value = descricaoRegistro;
      form.setorComercialDescricao.style.color = "#000000";
      form.fonteCaptacao.focus();
    }
  }

function limparPesquisaDescricaoLocalidade() {
    var form = document.forms[0];
      form.localidadeDescricao.value = "";
  }

function limparPesquisaLocalidade() {
    var form = document.forms[0];

      form.idLocalidade.value = "";
      form.localidadeDescricao.value = "";
  }

function limparDescricaoLocalidade(){
    var form = document.forms[0];
    form.localidadeDescricao.value = "";

}

function limparDescricaoSetorComercial(){
    var form = document.forms[0];
    form.setorComercialDescricao.value = "";


}


function limparPesquisaSetorComercial() {
    var form = document.forms[0];

      form.idSetorComercial.value = "";
      form.setorComercialDescricao.value = "";
}
function limparAmostras() {
	var form = document.forms[0];
	
	form.padraoTurbidez.value = "";
	form.numeroAmostrasExigidasTurbidez.value = "";
	form.padraoCor.value = "";
	form.numeroAmostrasExigidasCor.value = "";
	form.padraoCloro.value = "";
	form.numeroAmostrasExigidasCloro.value = "";
	form.padraoPH.value = "";
	form.numeroAmostrasExigidasPH.value = "";
	form.padraoFluor.value = "";
	form.numeroAmostrasExigidasFluor.value = "";
    form.padraoColiformesTotais.value = "";
	form.numeroAmostrasExigidasColiformesTotais.value = "";
	form.padraoColiformesTermotolerantes.value = "";
	form.numeroAmostrasExigidasColiformesTermotolerantes.value = "";
	
}

function verificarInserirTodos(){
		var form = InserirQualidadeAguaActionForm;
       	
       	if (form.indicadorInserirTodos.checked == true) {
       		form.indicadorInserirTodos.value = '1';
       	} else {
       		form.indicadorInserirTodos.value = '';
       	}
       	form.action = 'exibirInserirQualidadeAguaAction.do?indicadorInserirTodos='+form.indicadorInserirTodos.value;
       	form.submit();
       	
	}

function verificarAnoMesReferencia(){
    var form = document.InserirQualidadeAguaActionForm;
    var dataAtual = new Date();
    var dataReferencia = form.referencia.value;
    var mesAnoAtual = dataAtual.getMonth()+1;


        
    if (dataAtual.getMonth() < 10) {
    	mesAnoAtual = '0'+mesAnoAtual+'/'+dataAtual.getFullYear();
    }else{
        mesAnoAtual = mesAnoAtual+'/'+dataAtual.getFullYear();
    }

    if ((!comparaMesAno(mesAnoAtual, '<', dataReferencia)) && (!comparaMesAno(mesAnoAtual, '=', dataReferencia))){
		return false;
	}
	return true;
		     	
}	
	
function verificarChecado(valor){

	form = document.forms[0];
	//limparAmostras();
	if(valor == "1"){
	 	form.indicadorInserirTodos.checked = true;
	 }else{
	 	form.indicadorInserirTodos.checked = false;
	}

}
	
function verificarValorAmostras(){
	
	form = document.forms[0];
	
	if (!isNaN(form.numeroAmostrasRealizadasTurbidez.value) &&
		!isNaN(form.numeroAmostrasConformesTurbidez.value)){
		var intValorCampo1 = form.numeroAmostrasRealizadasTurbidez.value * 1;
		var intValorCampo2 = form.numeroAmostrasConformesTurbidez.value * 1;
		if (intValorCampo1 < intValorCampo2){
		   return false;
		}
	}
	
	if (!isNaN(form.numeroAmostrasRealizadasCor.value) &&
		!isNaN(form.numeroAmostrasConformesCor.value)){
		var intValorCampo1 = form.numeroAmostrasRealizadasCor.value * 1;
		var intValorCampo2 = form.numeroAmostrasConformesCor.value * 1;
		if (intValorCampo1 < intValorCampo2){
		   return false;
		}
	}
	
	if (!isNaN(form.numeroAmostrasRealizadasCloro.value) &&
		!isNaN(form.numeroAmostrasConformesCloro.value)){
		var intValorCampo1 = form.numeroAmostrasRealizadasCloro.value * 1;
		var intValorCampo2 = form.numeroAmostrasConformesCloro.value * 1;
		if (intValorCampo1 < intValorCampo2){
		   return false;
		}
	}

	if (!isNaN(form.numeroAmostrasRealizadasPH.value) &&
			!isNaN(form.numeroAmostrasConformesPH.value)){
			var intValorCampo1 = form.numeroAmostrasRealizadasPH.value * 1;
			var intValorCampo2 = form.numeroAmostrasConformesPH.value * 1;
			if (intValorCampo1 < intValorCampo2){
			   return false;
		}
	}
		
	if (!isNaN(form.numeroAmostrasRealizadasFluor.value) &&
		!isNaN(form.numeroAmostrasConformesFluor.value)){
		var intValorCampo1 = form.numeroAmostrasRealizadasFluor.value * 1;
		var intValorCampo2 = form.numeroAmostrasConformesFluor.value * 1;
		if (intValorCampo1 < intValorCampo2){
		   return false;
		}
	}
	
	if (!isNaN(form.numeroAmostrasRealizadasColiformesTotais.value) &&
		!isNaN(form.numeroAmostrasConformesColiformesTotais.value)){
		var intValorCampo1 = form.numeroAmostrasRealizadasColiformesTotais.value * 1;
		var intValorCampo2 = form.numeroAmostrasConformesColiformesTotais.value * 1;
		if (intValorCampo1 < intValorCampo2){
		   return false;
		}
	}
	
	if (!isNaN(form.numeroAmostrasRealizadasColiformesTermotolerantes.value) &&
		!isNaN(form.numeroAmostrasConformesColiformesTermotolerantes.value)){
		var intValorCampo1 = form.numeroAmostrasRealizadasColiformesTermotolerantes.value * 1;
		var intValorCampo2 = form.numeroAmostrasConformesColiformesTermotolerantes.value * 1;
		if (intValorCampo1 < intValorCampo2){
		   return false;
		}
	}
	return true;
}	
	


</script>


</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('${requestScope.nomeCampo}');
			verificarChecado('${requestScope.inserirTodosAtivado}');">

<html:form action="/inserirQualidadeAguaAction" method="post"
	name="InserirQualidadeAguaActionForm"
	type="gcom.gui.faturamento.InserirQualidadeAguaActionForm"
	onsubmit="return validateInserirQualidadeAguaActionForm(this);">

	<%@ include file="/jsp/util/cabecalho.jsp"%>
	<%@ include file="/jsp/util/menu.jsp"%>

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

			<!--Início Tabela Reference a Páginação da Tela de Processo-->
			<table>
				<tr>
					<td></td>

				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">Inserir Qualidade da Água</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>
				</tr>

			</table>
			<!--Fim Tabela Reference a Páginação da Tela de Processo-->
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>

					<td colspan="4">Para adicionar a qualidade da água, informe os
					dados abaixo:</td>
				</tr>




				<logic:present name="inserirTodosAtivado" scope="request">
					<tr>
						<td width="20%"><strong>Referência:<font color="#FF0000">*</font></strong></td>

						<td width="16%"><html:text property="referencia" size="7" maxlength="7"
							tabindex="1" onkeyup="mascaraAnoMes(this, event);" readonly="true" /> </td>
						<td width="16%">(mm/aaaa)</td>	

						<td width="48%" align="right" colspan="3"><input type="checkbox"
							name="indicadorInserirTodos" value="1"
							onclick="javascript:verificarInserirTodos();" align="right" /> <strong>Inserir
						Todos</strong></td>
					</tr>

				
						<tr>
							<td width="20%"><strong>Localidade:<font color="#FF0000">*</font></strong></td>
							<td width="80%" height="24" colspan="5"><html:text
								property="idLocalidade" maxlength="3" size="3" tabindex="3" readonly="true"
								onkeypress="javascript:limparPesquisaDescricaoLocalidade();limparPesquisaSetorComercial();
	                   		validaEnterComMensagem(event,'exibirInserirQualidadeAguaAction.do','idLocalidade','Localidade');"
								/> <a
								href="javascript:abrirPopup('exibirPesquisarLocalidadeAction.do?tipo=imovelLocalidade', 298, 480);">
							<img border="0"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								border="0" width="23" height="21" title="Pesquisar" /></a> <logic:present
								name="localidadeEncontrada">
								<logic:equal name="localidadeEncontrada" value="exception">
									<html:text property="localidadeDescricao" size="50"
										maxlength="50" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:equal>
								<logic:notEqual name="localidadeEncontrada" value="exception">
									<html:text property="localidadeDescricao" size="50"
										maxlength="50" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEqual>
							</logic:present> <logic:notPresent name="localidadeEncontrada">
								<logic:empty name="InserirQualidadeAguaActionForm"
									property="idLocalidade">
									<html:text property="localidadeDescricao" size="50"
										maxlength="50" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:empty>
								<logic:notEmpty name="InserirQualidadeAguaActionForm"
									property="idLocalidade">
									<html:text property="localidadeDescricao" size="50"
										maxlength="50" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEmpty>
							</logic:notPresent> <a
								href="javascript:limparPesquisaLocalidade();limparPesquisaSetorComercial();document.forms[0].idLocalidade.focus();">
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" title="Apagar" /></a></td>
						</tr>

						<tr>
							<td width="20%"><strong>Setor Comercial:</strong></td>
							<td width="80%" height="24" colspan="5"><html:text maxlength="3"
								property="idSetorComercial" size="3" tabindex="4" readonly="true"
								onkeypress="javascript:limparDescricaoSetorComercial();
                   				validaEnterDependenciaComMensagem(event,'exibirInserirQualidadeAguaAction.do', document.forms[0].idSetorComercial, document.forms[0].idLocalidade.value, 'Localidade', 'Setor Comercial');" /> <a
								href="javascript:abrirPopupDependencia('exibirPesquisarSetorComercialAction.do?idLocalidade='+document.forms[0].idLocalidade.value+'&tipo=SetorComercial',document.forms[0].idLocalidade.value,'Localidade',298, 480);" >
							<img border="0"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								border="0" /></a> <logic:present
								name="codigoSetorComercialNaoEncontrada" scope="request">
								<input type="text" name="setorComercialDescricao" size="50"
									readonly="true" maxlength="50"
									style="background-color:#EFEFEF; border:0; color: #ff0000" />
							</logic:present> <logic:present
								name="codigoSetorComercialEncontrado">
								<logic:equal name="codigoSetorComercialEncontrado"
									value="exception">
									<html:text property="setorComercialDescricao" size="50"
										maxlength="50" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:equal>
								<logic:notEqual name="codigoSetorComercialEncontrado"
									value="exception">
									<html:text property="setorComercialDescricao" size="50"
										maxlength="50" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEqual>
							</logic:present> <logic:notPresent
								name="codigoSetorComercialEncontrado">
								<logic:empty name="InserirQualidadeAguaActionForm"
									property="idSetorComercial">
									<html:text property="setorComercialDescricao" size="50"
										maxlength="50" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:empty>
								<logic:notEmpty name="InserirQualidadeAguaActionForm"
									property="idSetorComercial">
									<html:text property="setorComercialDescricao" size="50"
										maxlength="50" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEmpty>
							</logic:notPresent> <a
								href="javascript:limparPesquisaSetorComercial();document.forms[0].idSetorComercial.focus();">
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" title="Apagar" /></a></td>
					</tr>
					
					<tr>
						<td width="20%"><strong>Fonte de Captação:</strong></td>
						<td colspan="5" width="80%"><html:text property="fonteCaptacao" size="40"
							maxlength="30" tabindex="5" style=" width : 361px;" readonly="true" /></td>
					</tr>
					
					<tr>
						<td width="100%" colspan="6">
					    	&nbsp;
					    </td>
					</tr>
					
					<tr>
						<td width="20%"><strong>&nbsp;</strong></td>
						<td width="16%"><strong>Número Amostras Exigidas:</strong></td>
						<td width="16%"><strong>Amostras Realizadas:</strong></td>
						<td width="16%"><strong>Amostras Conformes:</strong></td>
						<td width="16%"><strong>Média:</strong></td>
						<td width="16%"><strong>Padrão Amostras Permitido:</strong></td>
						
					</tr>				
					
					<tr>
						
						<td width="20%"><strong>Turbidez:</strong></td>
						<td width="16%"><html:text property="numeroAmostrasExigidasTurbidez" size="6" maxlength="4" tabindex="6" readonly="true" onkeyup="verificaNumeroInteiro(this)"/></td>
						<td width="16%"><html:text property="numeroAmostrasRealizadasTurbidez" size="6" maxlength="4" tabindex="7" readonly="true" onkeyup="verificaNumeroInteiro(this)"  /></td>
						<td width="16%"><html:text property="numeroAmostrasConformesTurbidez" size="6" maxlength="4" tabindex="8" readonly="true" onkeyup="verificaNumeroInteiro(this)" /></td>
						<td width="16%"><html:text property="numeroAmostrasMediaTurbidez" size="6" maxlength="3" readonly="true" onkeyup="verificaNumeroInteiro(this)" tabindex="9" /></td>
						<td width="16%"><html:text property="padraoTurbidez" size="20" maxlength="20" tabindex="10" readonly="true" /></td>
						
					</tr>
					<tr>
						<td><strong>Cor:</strong></td>
						<td><html:text property="numeroAmostrasExigidasCor" size="6" maxlength="4" tabindex="11" readonly="true" onkeyup="verificaNumeroInteiro(this)"/></td>
						<td><html:text property="numeroAmostrasRealizadasCor" size="6" maxlength="4" tabindex="12" readonly="true" onkeyup="verificaNumeroInteiro(this)"/></td>
						<td><html:text property="numeroAmostrasConformesCor" size="6" maxlength="4" tabindex="13" readonly="true" onkeyup="verificaNumeroInteiro(this)"/></td>
						<td><html:text property="numeroAmostrasMediaCor" size="6" maxlength="3"  onkeyup="formataValorMonetario(this, 5)" tabindex="14" readonly="true" /></td>
						<td><html:text property="padraoCor" size="20" maxlength="20" tabindex="15" readonly="true" /></td>
						
					</tr>
					<tr>
						<td><strong>Cloro:</strong></td>
						<td><html:text property="numeroAmostrasExigidasCloro" size="6" maxlength="4" tabindex="16" readonly="true" onkeyup="verificaNumeroInteiro(this)"/></td>
						<td><html:text property="numeroAmostrasRealizadasCloro" size="6" maxlength="4" tabindex="17" readonly="true" onkeyup="verificaNumeroInteiro(this)" /></td>
						<td><html:text property="numeroAmostrasConformesCloro" size="6" maxlength="4"	tabindex="18" readonly="true" onkeyup="verificaNumeroInteiro(this)"/></td>
						<td><html:text property="numeroAmostrasMediaCloro" size="6" maxlength="3" readonly="true" onkeyup="verificaNumeroInteiro(this)" tabindex="19" /></td>
						<td><html:text property="padraoCloro" size="20" maxlength="20" tabindex="20" readonly="true" /></td>
						
					</tr>				
					<tr>
						<td><strong>PH:</strong></td>
						<td><html:text property="numeroAmostrasExigidasPH" size="6" maxlength="4" tabindex="21" readonly="true" onkeyup="verificaNumeroInteiro(this)"/></td>
						<td><html:text property="numeroAmostrasRealizadasPH" size="6" maxlength="4" tabindex="22" readonly="true" onkeyup="verificaNumeroInteiro(this, 7)" /></td>
						<td><html:text property="numeroAmostrasConformesPH" size="6" maxlength="4"	tabindex="23" readonly="true" onkeyup="verificaNumeroInteiro(this, 7)" /></td>
						<td><html:text property="numeroAmostrasMediaPH" size="6" maxlength="3" readonly="true" onkeyup="verificaNumeroInteiro(this)" tabindex="24" /></td>
						<td><html:text property="padraoPH" size="20" maxlength="20" tabindex="25" readonly="true"  /></td>
						
					</tr>				
					<tr>
						<td><strong>Flúor:</strong></td>
						<td><html:text property="numeroAmostrasExigidasFluor" size="6" maxlength="4" tabindex="26" readonly="true" onkeyup="verificaNumeroInteiro(this)"/></td>
						<td><html:text property="numeroAmostrasRealizadasFluor" size="6" maxlength="4" tabindex="27" readonly="true" onkeyup="verificaNumeroInteiro(this)" /></td>
						<td><html:text property="numeroAmostrasConformesFluor" size="6" maxlength="4" tabindex="28" readonly="true" onkeyup="verificaNumeroInteiro(this)" /></td>
						<td><html:text property="numeroAmostrasMediaFluor" size="6" maxlength="3" readonly="true" onkeyup="verificaNumeroInteiro(this)" tabindex="29" /></td>
						<td><html:text property="padraoFluor" size="20" maxlength="20" tabindex="30" readonly="true" /></td>
						
					</tr>

					<tr>
						<td><strong>Coliformes Totais:</strong></td>
						<td><html:text property="numeroAmostrasExigidasColiformesTotais" size="6" maxlength="4" tabindex="31" readonly="true" onkeyup="verificaNumeroInteiro(this)" /></td>
						<td><html:text property="numeroAmostrasRealizadasColiformesTotais" size="6" maxlength="4" tabindex="32" readonly="true" onkeyup="verificaNumeroInteiro(this)" /></td>
						<td><html:text property="numeroAmostrasConformesColiformesTotais" size="6" maxlength="4" tabindex="33" readonly="true" onkeyup="verificaNumeroInteiro(this)" /></td>
						<td><html:text property="numeroAmostrasMediaColiformesTotais" size="6" maxlength="3" onkeyup="verificaNumeroInteiro(this)" tabindex="34" readonly="true" /></td>
						<td><html:text property="padraoColiformesTotais" size="20" maxlength="20" tabindex="35" readonly="true" /></td>
						
					</tr>

					<tr>
						<td><strong>Coliformes Termotolerantes:</strong></td>
						<td><html:text property="numeroAmostrasExigidasColiformesTermotolerantes" size="6" maxlength="4" tabindex="36" readonly="true" onkeyup="verificaNumeroInteiro(this)" /></td>
						<td><html:text property="numeroAmostrasRealizadasColiformesTermotolerantes" size="6" maxlength="4" tabindex="37" readonly="true" onkeyup="verificaNumeroInteiro(this)" /></td>
						<td><html:text property="numeroAmostrasConformesColiformesTermotolerantes" size="6" maxlength="4" tabindex="38" readonly="true" onkeyup="verificaNumeroInteiro(this)"/></td>
						<td><html:text property="numeroAmostrasMediaColiformesTermotolerantes" size="6" maxlength="3" onkeyup="verificaNumeroInteiro(this)" tabindex="39" readonly="true" /></td>
						<td><html:text property="padraoColiformesTermotolerantes" size="20" maxlength="20" tabindex="40" readonly="true" /></td>
						
					</tr>	
					
					
					<tr>
						<td width="20%"><strong>Conclusão Análise:</strong></td>
						<td colspan="5" width="80%"><html:textarea property="descricaoConclusaoAnalisesRealizadas" rows="3" tabindex="41" readonly="true" cols="45" /></td>
					</tr>

				</logic:present>

				<logic:notPresent name="inserirTodosAtivado" scope="request">

					<tr>
						<td width="20%"><strong>Referência:<font color="#FF0000">*</font></strong></td>

						<td width="16%"><html:text property="referencia" size="7" maxlength="7"
							tabindex="1" onkeyup="mascaraAnoMes(this, event);" onkeypress="return isCampoNumerico(event);" /> </td>
						<td width="16%">(mm/aaaa)</td>	

						<td width="48%" align="right" colspan="3"><input type="checkbox"
							name="indicadorInserirTodos" value="1"
							onclick="javascript:verificarInserirTodos();" align="right" /> <strong>Inserir
						Todos</strong></td>
					</tr>

				
						<tr>
							<td width="20%"><strong>Localidade:<font color="#FF0000">*</font></strong></td>
							<td width="80%" height="24" colspan="5"><html:text
								property="idLocalidade" maxlength="3" size="3" tabindex="3"
								onkeypress="javascript:limparPesquisaDescricaoLocalidade();limparPesquisaSetorComercial();
	                   		validaEnterComMensagem(event,'exibirInserirQualidadeAguaAction.do','idLocalidade','Localidade');"
								/> <a
								href="javascript:abrirPopup('exibirPesquisarLocalidadeAction.do?tipo=imovelLocalidade', 298, 480);">
							<img border="0"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								border="0" width="23" height="21" title="Pesquisar" /></a> <logic:present
								name="localidadeEncontrada">
								<logic:equal name="localidadeEncontrada" value="exception">
									<html:text property="localidadeDescricao" size="50"
										maxlength="50" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:equal>
								<logic:notEqual name="localidadeEncontrada" value="exception">
									<html:text property="localidadeDescricao" size="50"
										maxlength="50" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEqual>
							</logic:present> <logic:notPresent name="localidadeEncontrada">
								<logic:empty name="InserirQualidadeAguaActionForm"
									property="idLocalidade">
									<html:text property="localidadeDescricao" size="50"
										maxlength="50" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:empty>
								<logic:notEmpty name="InserirQualidadeAguaActionForm"
									property="idLocalidade">
									<html:text property="localidadeDescricao" size="50"
										maxlength="50" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEmpty>
							</logic:notPresent> <a
								href="javascript:limparPesquisaLocalidade();limparPesquisaSetorComercial();document.forms[0].idLocalidade.focus();">
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" title="Apagar" /></a></td>
						</tr>

						<tr>
							<td width="20%"><strong>Setor Comercial:</strong></td>
							<td width="80%" height="24" colspan="5"><html:text maxlength="3"
								property="idSetorComercial" size="3" tabindex="4" 
								onkeypress="javascript:limparDescricaoSetorComercial();
                   				validaEnterDependenciaComMensagem(event,'exibirInserirQualidadeAguaAction.do', document.forms[0].idSetorComercial, document.forms[0].idLocalidade.value, 'Localidade', 'Setor Comercial');" /> <a
								href="javascript:abrirPopupDependencia('exibirPesquisarSetorComercialAction.do?idLocalidade='+document.forms[0].idLocalidade.value+'&tipo=SetorComercial',document.forms[0].idLocalidade.value,'Localidade',298, 480);" >
							<img border="0"
								src="<bean:message key="caminho.imagens"/>pesquisa.gif"
								border="0" /></a> <logic:present
								name="codigoSetorComercialNaoEncontrada" scope="request">
								<input type="text" name="setorComercialDescricao" size="50"
									readonly="true" maxlength="50"
									style="background-color:#EFEFEF; border:0; color: #ff0000" />
							</logic:present> <logic:present
								name="codigoSetorComercialEncontrado">
								<logic:equal name="codigoSetorComercialEncontrado"
									value="exception">
									<html:text property="setorComercialDescricao" size="50"
										maxlength="50" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:equal>
								<logic:notEqual name="codigoSetorComercialEncontrado"
									value="exception">
									<html:text property="setorComercialDescricao" size="50"
										maxlength="50" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEqual>
							</logic:present> <logic:notPresent
								name="codigoSetorComercialEncontrado">
								<logic:empty name="InserirQualidadeAguaActionForm"
									property="idSetorComercial">
									<html:text property="setorComercialDescricao" size="50"
										maxlength="50" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #ff0000" />
								</logic:empty>
								<logic:notEmpty name="InserirQualidadeAguaActionForm"
									property="idSetorComercial">
									<html:text property="setorComercialDescricao" size="50"
										maxlength="50" readonly="true"
										style="background-color:#EFEFEF; border:0; color: #000000" />
								</logic:notEmpty>
							</logic:notPresent> <a
								href="javascript:limparPesquisaSetorComercial();document.forms[0].idSetorComercial.focus();">
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
								border="0" title="Apagar" /></a></td>
					</tr>
					
					<tr>
						<td width="20%"><strong>Fonte de Captação:</strong></td>
						<td colspan="5" width="80%"><html:text property="fonteCaptacao" size="40"
							maxlength="30" tabindex="5" style=" width : 361px;"/></td>
					</tr>
					
					<tr>
						<td width="100%" colspan="6">
					    	&nbsp;
					    </td>
					</tr>
					
					<tr>
						
						<td width="20%"><strong>&nbsp;</strong></td>
						<td width="16%"><strong>Número Amostras Exigidas:</strong></td>
						<td width="16%"><strong>Amostras Realizadas:</strong></td>
						<td width="16%"><strong>Amostras Conformes:</strong></td>
						<td width="16%"><strong>Média:</strong></td>
						<td width="16%"><strong>Padrão Amostras Permitido:</strong></td>
						
					</tr>

					<tr>
						<td width="20%"><strong>Turbidez:</strong></td>
						<td width="16%"><html:text property="numeroAmostrasExigidasTurbidez" size="6" maxlength="4" tabindex="6" onkeyup="verificaNumeroInteiro(this)"/></td>
						<td width="16%"><html:text property="numeroAmostrasRealizadasTurbidez" size="6" maxlength="4" tabindex="7" onkeyup="verificaNumeroInteiro(this)" /></td>
						<td width="16%"><html:text property="numeroAmostrasConformesTurbidez" size="6" maxlength="4" tabindex="8" onkeyup="verificaNumeroInteiro(this)"/></td>
						<td width="16%"><html:text property="numeroAmostrasMediaTurbidez" size="6" maxlength="3" onkeyup="verificaNumeroInteiro(this)" tabindex="9" /></td>
						<td width="16%"><html:text property="padraoTurbidez" size="20" maxlength="20" tabindex="10" /></td>
						
					</tr>
					<tr>
						<td><strong>Cor:</strong></td>
						<td><html:text property="numeroAmostrasExigidasCor" size="6" maxlength="4" tabindex="11" onkeyup="verificaNumeroInteiro(this)"/></td>
						<td><html:text property="numeroAmostrasRealizadasCor" size="6" maxlength="4" tabindex="12" onkeyup="verificaNumeroInteiro(this)"/></td>
						<td><html:text property="numeroAmostrasConformesCor" size="6" maxlength="4" tabindex="13" onkeyup="verificaNumeroInteiro(this)"/></td>
						<td><html:text property="numeroAmostrasMediaCor" size="6" maxlength="3"  onkeyup="verificaNumeroInteiro(this)" tabindex="14" /></td>
						<td><html:text property="padraoCor" size="20" maxlength="20" tabindex="15" /></td>
				
					</tr>
					<tr>
						<td><strong>Cloro:</strong></td>
						<td><html:text property="numeroAmostrasExigidasCloro" size="6" maxlength="4" tabindex="16" onkeyup="verificaNumeroInteiro(this)"/></td>
						<td><html:text property="numeroAmostrasRealizadasCloro" size="6" maxlength="4" tabindex="17"  onkeyup="verificaNumeroInteiro(this)"/></td>
						<td><html:text property="numeroAmostrasConformesCloro" size="6" maxlength="4"	tabindex="18" onkeyup="verificaNumeroInteiro(this)" /></td>
						<td><html:text property="numeroAmostrasMediaCloro" size="6" maxlength="3" onkeyup="verificaNumeroInteiro(this)" tabindex="19" /></td>
						<td><html:text property="padraoCloro" size="20" maxlength="20" tabindex="20" /></td>
						
					</tr>				
					<tr>
						<td><strong>PH:</strong></td>
						<td><html:text property="numeroAmostrasExigidasPH" size="6" maxlength="4" tabindex="21" onkeyup="verificaNumeroInteiro(this)"/></td>
						<td><html:text property="numeroAmostrasRealizadasPH" size="6" maxlength="4" tabindex="22" onkeyup="verificaNumeroInteiro(this)"/></td>
						<td><html:text property="numeroAmostrasConformesPH" size="6" maxlength="4"	tabindex="23" onkeyup="verificaNumeroInteiro(this)"/></td>
						<td><html:text property="numeroAmostrasMediaPH" size="6" maxlength="3" onkeyup="verificaNumeroInteiro(this)" tabindex="24" /></td>
						<td><html:text property="padraoPH" size="20" maxlength="20" tabindex="25" /></td>
						
					</tr>				
					<tr>
						<td><strong>Flúor:</strong></td>
						<td><html:text property="numeroAmostrasExigidasFluor" size="6" maxlength="4" tabindex="26" onkeyup="verificaNumeroInteiro(this)"/></td>
						<td><html:text property="numeroAmostrasRealizadasFluor" size="6" maxlength="4" tabindex="27" onkeyup="verificaNumeroInteiro(this)" /></td>
						<td><html:text property="numeroAmostrasConformesFluor" size="6" maxlength="4" tabindex="28"  onkeyup="verificaNumeroInteiro(this)"/></td>
						<td><html:text property="numeroAmostrasMediaFluor" size="6" maxlength="3" onkeyup="verificaNumeroInteiro(this)" tabindex="29" /></td>
						<td><html:text property="padraoFluor" size="20" maxlength="20" tabindex="30" /></td>
					
					</tr>

					<tr>
						<td><strong>Coliformes Totais:</strong></td>
						<td><html:text property="numeroAmostrasExigidasColiformesTotais" size="6" maxlength="4" tabindex="31" onkeyup="verificaNumeroInteiro(this)"/></td>
						<td><html:text property="numeroAmostrasRealizadasColiformesTotais" size="6" maxlength="4" tabindex="32" onkeyup="verificaNumeroInteiro(this)" /></td>
						<td><html:text property="numeroAmostrasConformesColiformesTotais" size="6" maxlength="4" tabindex="33" onkeyup="verificaNumeroInteiro(this)" /></td>
						<td><html:text property="numeroAmostrasMediaColiformesTotais" size="6" maxlength="3" onkeyup="verificaNumeroInteiro(this)" tabindex="34" /></td>
						<td><html:text property="padraoColiformesTotais" size="20" maxlength="20" tabindex="35" /></td>
						
					</tr>

					<tr>
						<td><strong>Coliformes Termotolerantes:</strong></td>
						<td><html:text property="numeroAmostrasExigidasColiformesTermotolerantes" size="6" maxlength="4" tabindex="36" onkeyup="verificaNumeroInteiro(this)"/></td>
						<td><html:text property="numeroAmostrasRealizadasColiformesTermotolerantes" size="6" maxlength="4" tabindex="37"  onkeyup="verificaNumeroInteiro(this)"/></td>
						<td><html:text property="numeroAmostrasConformesColiformesTermotolerantes" size="6" maxlength="4" tabindex="38" onkeyup="verificaNumeroInteiro(this)" /></td>
						<td><html:text property="numeroAmostrasMediaColiformesTermotolerantes" size="6" maxlength="3" onkeyup="verificaNumeroInteiro(this)" tabindex="39" /></td>
						<td><html:text property="padraoColiformesTermotolerantes" size="20" maxlength="20" tabindex="40" /></td>
						
					</tr>	
					
					
					<tr>
						<td width="20%"><strong>Conclusão Análise:</strong></td>
						<td colspan="5" width="80%"><html:textarea property="descricaoConclusaoAnalisesRealizadas" rows="3" tabindex="41" cols="45" /></td>
					</tr>			
				</logic:notPresent>


				<tr>
					<td height="19"><strong> <font color="#FF0000"></font></strong></td>
					<td align="right">
					<div align="center"><strong><font color="#FF0000">*</font></strong>
					Campos obrigat&oacute;rios</div>
					</td>
				</tr>
				<tr>
					<td width="40%"><input name="Button" type="button"
						class="bottonRightCol" value="Desfazer" align="left"
						onclick="window.location.href='/gsan/exibirInserirQualidadeAguaAction.do?menu=sim'">
					<input name="Button" type="button" class="bottonRightCol"
						value="Cancelar" align="left"
						onclick="window.location.href='/gsan/telaPrincipal.do'"></td>
					<td align="right" height="24" colspan="5" width="60%"><input type="button" name="Button"
						class="bottonRightCol" value="Inserir"
						onClick="javascript:validarForm();" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>
		</tr>
	</table>
	<tr>
		<td colspan="3">
	</tr>
	<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</body>
</html:html>

