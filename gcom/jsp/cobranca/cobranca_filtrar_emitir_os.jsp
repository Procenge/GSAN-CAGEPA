<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>
<%@ page import="gcom.util.ConstantesSistema"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js"></script>
	
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>	
<script language="JavaScript">
<!-- Begin


	function caracteresespeciais () {
    	this.aa = new Array("idLocalidadeEventual", "Localidade Inicial possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     	this.ac = new Array("idSetorComercialEventual", "Setor Comercial Inicial possui caracteres especiais.", new Function ("varName", " return this[varName];"));
    }

    function IntegerValidations () {
    	this.am = new Array("idLocalidadeEventual", "Localidade Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
     	this.ao = new Array("idSetorComercialEventual", "Setor Comercial Inicial deve somente conter números positivos.", new Function ("varName", " return this[varName];"));
    }

    function required () {
//     	this.ac = new Array("descricaoSolicitacao", "Informe Descrição Solicitação.", new Function ("varName", " return this[varName];"));     	
//    	this.ah = new Array("cobrancaAtividade", "Informe Atividade de Cobrança.", new Function ("varName", " return this[varName];"));
    }


	function executarFiltrar(){
	var form = document.forms[0];
	form.action = '/gsan/filtrarEmitirOSCobrancaAction.do';
   	form.submit();
	}

function limparOrigem(tipo){
	var form = document.forms[0];

	switch(tipo){
		case 1: //De localidara pra baixo
			
			form.idLocalidadeEventual.value = "";
			form.nomeLocalidadeEventual.value = "";

		case 2: //De setor para baixo
		   form.idSetorComercialEventual.value = "";
		   form.nomeSetorComercialEventual.value = "";
	}
	verificarHabilitarGrupoCobrancaEventual();

}

function limparComando(tipo){
	var form = document.forms[0];

	if(tipo == 'cronograma'){
		form.idComandoCronograma.value = "";
		form.nomeComandoCronograma.value = "";
	} else if(tipo == 'eventual'){
		form.idComandoEventual.value = "";
		form.nomeComandoEventual.value = "";
	}
}
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

    	var form = document.forms[0];
    	if (tipoConsulta == 'cobrancaAcaoAtividadeComando') {
			form.idComandoEventual.value = codigoRegistro;
			form.nomeComandoEventual.value = descricaoRegistro;
    	}

		if (tipoConsulta == 'localidade') {
			form.idLocalidadeEventual.value = codigoRegistro;
			form.nomeLocalidadeEventual.value = descricaoRegistro;
			verificarHabilitarSetor();
		}
		
		if (tipoConsulta == 'setorComercial') {
			form.idSetorComercialEventual.value = codigoRegistro;
			form.nomeSetorComercialEventual.value = descricaoRegistro;
		}
		if (tipoConsulta == 'cobrancaAcaoAtividadeCronograma') {
			form.idComandoCronograma.value = codigoRegistro;
			form.nomeComandoCronograma.value = descricaoRegistro;
		}
		
		
     }

function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){

	
	if(objetoRelacionado.disabled != true){
		if (objeto == null || codigoObjeto == null){
			abrirPopup(url + "?" + "tipo=" + tipo, altura, largura);
		}
		else{
			if (codigoObjeto.length < 1 || isNaN(codigoObjeto)){
				alert(msg);
			}
			else{
				abrirPopup(url + "?" + "tipo=" + tipo + "&" + objeto + "=" + codigoObjeto + "&caminhoRetornoTelaPesquisa=" + tipo, altura, largura);
			}
		}
	}
}

function voltar(){
	var formulario = document.forms[0]; 
	formulario.action =  'exibirInserirComandoAcaoCobrancaAction.do?menu=sim'
	formulario.submit();
}

function mensagem(mensagem){
	if(mensagem.length > 0){
		alert(mensagem);
	}
}

/*
  	Seleciona por padrão o TipoFiltro Cronograma e 
  	desabilita os campos do TipoFiltro Eventual
 
 */
function ativarTipoFiltroPadrao(){
  	form = document.forms[0];
	
  	if(form.tipoFiltro[1].checked == true){
		ativarCamposCronogramaOuEventual(form.tipoFiltro[1]);
	  	verificarHabilitarGrupoCobrancaEventual();
	  	verificarHabilitarLocalSetorEventual();
  	} else {
		form.tipoFiltro[0].checked = true;
		ativarCamposCronogramaOuEventual(form.tipoFiltro[0]);
  	}
}


/*
 	Esta função é chamada quando um dos botões de rádio é selecionado, 
 	ela habilita e desabilita os campos de acordo com o tipo de filtro.
 */
function ativarCamposCronogramaOuEventual(campo){
	form = document.forms[0];
	if(campo.value == 'cronograma'){
		//desabilitar os controles do eventual
		form.idCobrancaGrupoEventual.disabled = true;
		form.idLocalidadeEventual.disabled = true;
		form.nomeLocalidadeEventual.disabled = true;
		form.idSetorComercialEventual.disabled = true;
		form.nomeSetorComercialEventual.disabled = true;
		form.idCobrancaAcaoEventual.disabled = true;
		form.dataInicialEventual.disabled = true;
		form.dataFinalEventual.disabled = true;
		form.idComandoEventual.disabled = true;
		form.nomeComandoEventual.disabled = true;
		
		
		//habilitar os comandos do cronograma
		form.idCobrancaAcaoCronograma.disabled = false;
		form.idCobrancaGrupoCronograma.disabled = false;
		form.dataFinalCronograma.disabled = false;
		form.dataInicialCronograma.disabled = false;
		form.idComandoCronograma.disabled = false;
		form.nomeComandoCronograma.disabled = false;

		form.idCobrancaGrupoCronograma.focus();
	} else {
		//desabilitar os comandos do cronograma
		form.idCobrancaAcaoCronograma.disabled = true;
		form.idCobrancaGrupoCronograma.disabled = true;
		form.dataFinalCronograma.disabled = true;
		form.dataInicialCronograma.disabled = true;
		form.idComandoCronograma.disabled = true;
		form.nomeComandoCronograma.disabled = true;
		
		//habilitar os controles do eventual
		form.ativoEventual.value = false;
		form.ativoCobrancaGrupoEventual.value = false;
		form.ativoLocalidadeSetorEventual.value = false;
		
		form.idCobrancaGrupoEventual.disabled = false;
		form.idLocalidadeEventual.disabled = false;
		form.nomeLocalidadeEventual.disabled = false;
		form.idSetorComercialEventual.disabled = false;
		form.nomeSetorComercialEventual.disabled = false;
		form.idCobrancaAcaoEventual.disabled = false;
		form.dataInicialEventual.disabled = false;
		form.dataFinalEventual.disabled = false;
		form.idComandoEventual.disabled = false;
		form.nomeComandoEventual.disabled = false;
		verificarHabilitarSetor();
		
		form.idCobrancaGrupoEventual.focus();
	}
}

function verificarHabilitarGrupoCobrancaEventual(){
	//verificarHabilitarSetor();
	form = document.forms[0];
	if(form.idLocalidadeEventual.value == "" && form.idSetorComercialEventual.value == ""){
		form.idCobrancaGrupoEventual.disabled = false;
	} else {
		form.idCobrancaGrupoEventual.selectedIndex = 0;
		form.idCobrancaGrupoEventual.disabled = true;
	}

}

function verificarHabilitarLocalSetorEventual(){
	form = document.forms[0];
	if(form.idCobrancaGrupoEventual.selectedIndex == 0){
		desabilitarLocalSetorEventual(false);
		
	} else {
		desabilitarLocalSetorEventual(true);
	} 
}

function desabilitarLocalSetorEventual(habilitar){
	form = document.forms[0];
	form.idLocalidadeEventual.disabled = habilitar;
	form.nomeLocalidadeEventual.disabled = habilitar;
	form.idSetorComercialEventual.disabled = habilitar;
	form.nomeSetorComercialEventual.disabled = habilitar;
	form.ativoLocalidadeSetorEventual.value = habilitar;
}


function verificarHabilitarSetor(){
	form = document.forms[0];
	if(form.idLocalidadeEventual.value == ""){
		form.idSetorComercialEventual.disabled = true;
	} else {
		form.idSetorComercialEventual.selectedIndex = 0;
		form.idSetorComercialEventual.disabled = false;
	}

}


-->
</script>
</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:ativarTipoFiltroPadrao();mensagem('${requestScope.mensagem}'); verificarHabilitarSetor();"
	onreset="alet('O form foi limpo')">
<html:form action="/filtrarEmitirOSCobrancaAction.do"
   name="FiltrarEmitirOSCobrancaActionForm"
   type="gcom.gui.cobranca.FiltrarEmitirOSCobrancaActionForm"
   method="post" >
	<input type="hidden" name="ativoCronograma" />
	<input type="hidden" name="ativoEventual" />
	<input type="hidden" name="ativoCobrancaGrupoEventual" />
	<input type="hidden" name="ativoLocalidadeSetorEventual" />
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td valign="top">
			<table width="100%" border="0"  cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<html:radio property="tipoFiltro" styleId="tipoFiltro" value="cronograma" tabindex="40" onclick="javaScript: ativarCamposCronogramaOuEventual(this);" />
					</td>
					<td>
						<strong>
							A&ccedil;&atilde;o do Cronograma: 
						</strong>
					</td>
				</tr>
				<!-- INICIO - Grupo de Cobrança - Cronograma *********************************** -->
				<tr>
					<td><strong>Grupo de Cobran&ccedil;a:</strong></td>
					<td colspan="3">
						<html:select property="idCobrancaGrupoCronograma" tabindex="1">
						<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present scope="session" name="cobrancaGrupos">
							<html:options name="form" collection="cobrancaGrupos"
								labelProperty="descricao" property="id" />
						</logic:present>
						</html:select>
					</td>
				</tr>
				<!-- FIM - Grupo de Cobrança - Cronograma *********************************** -->
				
				<!-- INICIO - Comando para a acao Cronograma *********************************** -->
				<tr>
					<td><strong>Comando(Cronograma):</strong></td>
					<td width="81%" align="right" colspan="2">
					<div align="left">
						<html:text maxlength="3"
							property="idComandoCronograma" 
							size="3" 
							tabindex="2"
							onkeypress="validaEnterComMensagem(event, 'exibirFiltrarEmitirOSCobrancaAction.do', 'idComandoCronograma', 'Comando Cronograma');"
							/> 
							
						<a href="javascript:if(!document.forms[0].idComandoCronograma.disabled)chamarPopup('exibirPesquisarCronogramaAcaoCobrancaAction.do?indicadorUsoTodos=1', 'origem', null, null, 360, 735, '',document.forms[0].idComandoCronograma);">
                       		<img border="0" 
                       			src="<bean:message key="caminho.imagens"/>pesquisa.gif" 
                       			border="0"/></a>

						<strong>
						<logic:present name="corNomeComandoCronograma">
							<logic:equal name="corNomeComandoCronograma" value="exception">
								<html:text property="nomeComandoCronograma" 
									size="40"
									maxlength="40" 
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000" />
							</logic:equal>

							<logic:notEqual name="corNomeComandoCronograma" value="exception">
								<html:text property="nomeComandoCronograma" 
									size="40"
									maxlength="40" 
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</logic:notEqual>
						</logic:present>
						
						<logic:notPresent name="corNomeComandoCronograma">
							<logic:empty name="FiltrarEmitirOSCobrancaActionForm"
								property="nomeComandoCronograma">
								<html:text property="nomeComandoCronograma" 
									value=""
									size="40"
									maxlength="40" 
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000" />
							</logic:empty>
							
							<logic:notEmpty name="FiltrarEmitirOSCobrancaActionForm"
								property="nomeComandoCronograma">
								<html:text property="nomeComandoCronograma" 
									size="40"
									maxlength="40" 
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: 	#000000" />
							</logic:notEmpty>
						</logic:notPresent>
						
						<a href="javascript:if(!document.forms[0].idComandoCronograma.disabled)limparComando('cronograma');"> <img name="imagem"
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a>  					
						</strong></div>
					</td>
				</tr>
				<!-- FIM - Comando para a acao Cronograma *********************************** -->

				<!-- INICIO - Ação de Cobrança - Cronograma *********************************** -->
				<tr>
					<td width="16%"><strong>A&ccedil;&atilde;o de Cobran&ccedil;a:<font
						color="#FF0000"></font></strong></td>
					<td colspan="3">
						<html:select property="idCobrancaAcaoCronograma" tabindex="3">
						<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present scope="session" name="cobrancaAcoes">
							<html:options name="form" collection="cobrancaAcoes"
								labelProperty="descricaoCobrancaAcao" property="id" />
						</logic:present>
						</html:select>
					</td>
				</tr>
				<!-- FIM - Ação de Cobrança - Cronograma *********************************** -->
				<!-- INICIO - Período Cronograma *********************************** -->
				<tr>
	                <td>
	                	<strong>Per&iacute;odo:</strong>
	                </td>
                
	                <td colspan="6">
	                	<span class="style2">
	                	
	                	<strong> 
							
							<html:text property="dataInicialCronograma" 
								size="11" 
								maxlength="10" 
								tabindex="4" 
								onkeyup="mascaraData(this, event);"/>
							
							<a href="javascript:if(!document.forms[0].dataInicialCronograma.disabled)abrirCalendarioReplicando('FiltrarEmitirOSCobrancaActionForm', 'dataInicialCronograma');">
								<img border="0" 
									src="<bean:message key='caminho.imagens'/>calendario.gif" 
									width="16" 
									height="15" 
									border="0" alt="Exibir Calendário" /></a>
							a 
							
							<html:text property="dataFinalCronograma" 
								size="11" 
								maxlength="10" 
								tabindex="5" 
								onkeyup="mascaraData(this, event)"/>
								
							<a href="javascript:if(!document.forms[0].dataFinalCronograma.disabled)abrirCalendario('FiltrarEmitirOSCobrancaActionForm', 'dataFinalCronograma');">
								<img border="0" 
									src="<bean:message key='caminho.imagens'/>calendario.gif" 
									width="16" 
									height="15" 
									border="0" 
									alt="Exibir Calendário" /></a>
							</strong>(dd/mm/aaaa)<strong> 
	                  	</strong>
	                  	</span>
	              	</td>
              	</tr>
				<!-- FIM - Período Cronograma *********************************** -->
				<tr>
					<td colspan="4">
					<hr>
					</td>
				</tr>
				
				<tr>
					<td>
						<html:radio property="tipoFiltro" styleId="tipoFiltro" value="eventual" tabindex="41" onclick="javaScript: ativarCamposCronogramaOuEventual(this);"/> 
					</td>
					<td>
						<strong>
							A&ccedil;&atilde;o do Eventual:
						</strong>
					</td>
				</tr>
				<!-- INICIO - Grupo de cobrança do eventual *********************************** -->
				<tr>
					<td><strong>Grupo de Cobran&ccedil;a:</strong></td>
					<td colspan="3">
						<html:select property="idCobrancaGrupoEventual" tabindex="6"
							onchange="verificarHabilitarLocalSetorEventual();" >
						<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present scope="session" name="cobrancaGrupos">
							<html:options name="form" collection="cobrancaGrupos"
								labelProperty="descricao" property="id" />
						</logic:present>
						</html:select>
					</td>
				</tr>
				<!-- FIM - Grupo de cobrança do eventual *********************************** -->
				<!-- INICIO - Localidade para a acao eventual *********************************** -->
				<tr>
					<td><strong>Localidade:</strong></td>
					<td width="81%" align="right" colspan="2">
					<div align="left">
						<html:text maxlength="7"
							property="idLocalidadeEventual" 
							size="3" 
							tabindex="7"
							onkeypress="validaEnterComMensagem(event, 'exibirFiltrarEmitirOSCobrancaAction.do', 'idLocalidadeEventual', 'Localidade');"
							onkeyup="verificarHabilitarGrupoCobrancaEventual();"
							/> 
							
						<a href="javascript:if(!document.forms[0].idLocalidadeEventual.disabled)chamarPopup('exibirPesquisarLocalidadeAction.do?indicadorUsoTodos=1', 'origem', null, null, 275, 480, '',document.forms[0].idLocalidadeEventual);">
                       		<img border="0" 
                       			src="<bean:message key="caminho.imagens"/>pesquisa.gif" 
                       			border="0"/></a>

						<strong> 
						<logic:present name="corLocalidadeEventual">
							<logic:equal name="corLocalidadeEventual" value="exception">
								<html:text property="nomeLocalidadeEventual" 
									size="40"
									maxlength="40" 
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #ff0000" />
							</logic:equal>

							<logic:notEqual name="corLocalidadeEventual" value="exception">
								<html:text property="nomeLocalidadeEventual" 
									size="40"
									maxlength="40" 
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: #000000" />
							</logic:notEqual>

						</logic:present> 
						
						<logic:notPresent name="corLocalidadeEventual">
							<logic:empty name="FiltrarEmitirOSCobrancaActionForm"
								property="idLocalidadeEventual">
							
							<html:text property="nomeLocalidadeEventual" 
								value="" 
								size="40"
								maxlength="40" 
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
							</logic:empty>
							
							<logic:notEmpty name="FiltrarEmitirOSCobrancaActionForm"
								property="idLocalidadeEventual">
								
								<html:text property="nomeLocalidadeEventual" 
									size="40"
									maxlength="40" 
									readonly="true"
									style="background-color:#EFEFEF; border:0; color: 	#000000" />
							</logic:notEmpty>
						</logic:notPresent> 
						
						<a href="javascript:if(!document.forms[0].idLocalidadeEventual.disabled)limparOrigem(1);"> <img name="imagem"
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a>  					
						</strong></div>
					</td>
				</tr>
				<!-- FIM - Localidade para a acao eventual *********************************** -->
				<!-- INICIO - Setor Comercial para a acao eventual *********************************** -->
				<tr>
					<td><strong>Setor:</strong></td>
					<td align="right" colspan="2">
					<div align="left">
						<html:text maxlength="3" property="idSetorComercialEventual" size="3" tabindex="8" 
						styleId="idSetorComercialEventual" 
						onkeyup="verificarHabilitarGrupoCobrancaEventual();"
						onkeypress="validaEnterComMensagem(event, 'exibirFiltrarEmitirOSCobrancaAction.do', 'idSetorComercialEventual', 'Setor Comercial');"
						 />
							 
						<a href="javascript:if(!document.forms[0].idSetorComercialEventual.disabled)abrirPopupDependencia('exibirPesquisarSetorComercialAction.do?idLocalidade='+document.forms[0].idLocalidadeEventual.value+'&tipo=setorComercialEventual&indicadorUsoTodos=1',document.forms[0].idLocalidadeEventual.value,'Localidade da inscrição de origem', 400, 800);">
                         <img border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" border="0"/></a>
					<logic:present name="corNomeSetorComercial">
						<logic:equal name="corNomeSetorComercial" value="exception">
							<html:text property="nomeSetorComercialEventual" size="40"
								maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>

						<logic:notEqual name="corNomeSetorComercial" value="exception">
							<html:text property="corNomeSetorComercial" size="40"
								maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> 
					<logic:notPresent name="corNomeSetorComercial">
						<logic:empty
							name="FiltrarEmitirOSCobrancaActionForm"
							property="idSetorComercialEventual">
							<html:text property="nomeSetorComercialEventual" value="" size="40"
								maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="FiltrarEmitirOSCobrancaActionForm" property="idSetorComercialEventual">
							<html:text property="nomeSetorComercialEventual" size="40"
								maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
						
					</logic:notPresent> 
						<a href="javascript:if(!document.forms[0].idSetorComercialEventual.disabled)limparOrigem(2);"> <img name="imagem"
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar" /></a>  										
					</div>
					</td>
				</tr>
				<!-- FIM - Setor Comercial para a acao eventual *********************************** -->
				<!-- INICIO - Comando para a acao eventual *********************************** -->
				<tr>
					<td HEIGHT="30"><strong>Comando Eventual:</strong></td>
					<td>
						<html:hidden property="idComandoEventual"/>
						<html:text property="nomeComandoEventual" size="55" maxlength="55" readonly="true" 
						style="background-color:#EFEFEF; border:0; color: #000000"/>
						<a href="javascript:if(!document.forms[0].nomeComandoEventual.disabled)abrirPopup('exibirPesquisarComandoAcaoCobrancaAction.do?limparForm=OK', 400, 750);" tabindex="9" title="Pesquisar">
							<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23" height="21" border="0">
						</a>
						<a href="javascript:if(!document.forms[0].nomeComandoEventual.disabled)limparComando('eventual');">
							<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
							border="0" title="Apagar" />
						</a>
					</td>
				</tr>				
				<!-- FIM - Comando para a acao eventual *********************************** -->
				<!-- INICIO - Ação de Cobrança eventual *********************************** -->
				<tr>
					<td width="16%"><strong>A&ccedil;&atilde;o de Cobran&ccedil;a:<font
						color="#FF0000"></font></strong></td>
					<td colspan="3">
						<html:select property="idCobrancaAcaoEventual" tabindex="10">
						<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
						<logic:present scope="session" name="cobrancaAcoes">
							<html:options name="form" collection="cobrancaAcoes"
								labelProperty="descricaoCobrancaAcao" property="id" />
						</logic:present>
						</html:select>
					</td>
				</tr>
				<!-- FIM - Ação de Cobrança eventual *********************************** -->
				<!-- INICIO - Período Eventual *********************************** -->
				<tr>
	                <td>
	                	<strong>Per&iacute;odo:</strong>
	                </td>
                
	                <td colspan="6">
	                	<span class="style2">
	                	
	                	<strong> 
							
							<html:text property="dataInicialEventual" 
								size="11" 
								maxlength="10" 
								tabindex="11" 
								onkeyup="mascaraData(this, event);"/>
							
							<a href="javascript:if(!document.forms[0].dataInicialEventual.disabled)abrirCalendario('FiltrarEmitirOSCobrancaActionForm', 'dataInicialEventual');">
								<img border="0" 
									src="<bean:message key='caminho.imagens'/>calendario.gif" 
									width="16" 
									height="15" 
									border="0" alt="Exibir Calendário" /></a>
							a 
							
							<html:text property="dataFinalEventual" 
								size="11" 
								tabindex="12"
								maxlength="10" 
								onkeyup="mascaraData(this, event)" />
								
							<a href="javascript:if(!document.forms[0].dataFinalEventual.disabled)abrirCalendario('FiltrarOrdemServicoActionForm', 'dataFinalEventual');">
								<img border="0" 
									src="<bean:message key='caminho.imagens'/>calendario.gif" 
									width="16" 
									height="15" 
									border="0" 
									alt="Exibir Calendário" /></a>
							</strong>(dd/mm/aaaa)<strong> 
	                  	</strong>
	                  	</span>
	              	</td>
              	</tr>
				<!-- FIM - Período Eventual *********************************** -->
				<tr>
					<td height="17"></td>
					<td colspan="3"></td>
				</tr>
				<tr>
					<td colspan="3" width="100%" height="1px" bgcolor="#000000"></td>
				</tr>				
				<tr>
					<td colspan="2"><strong> <font color="#ff0000"> 
					<input name="reset" class="bottonRightCol" value="Desfazer" type="reset" />
					<input type="button" name="ButtonCancelar" class="bottonRightCol" value="Cancelar"
							onClick="javascript:document.forms[0].target='';window.location.href='/gsan/telaPrincipal.do'">
					 </font></strong>
					</td>
					<td align="right">
						<input type="button" name="filtrar" class="bottonRightCol" value="Filtrar" tabindex="12" onclick="javaScript: executarFiltrar();" />
					</td>
				</tr>				
			</table>
			</td>
		</tr>
	</table>
</html:form>
</body>
</html>
