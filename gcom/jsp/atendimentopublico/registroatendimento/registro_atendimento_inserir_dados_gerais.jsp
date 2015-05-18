<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@ page import="gcom.util.ConstantesSistema" %>
<%@ page import="gcom.atendimentopublico.registroatendimento.RegistroAtendimento" %>
<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<bean:message key="caminho.css"/>EstilosCompesa.css" type="text/css">

<script language="JavaScript" src="<bean:message key="caminho.js"/>util.js" ></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>Calendario.js" ></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>


<SCRIPT LANGUAGE="JavaScript">

	var bCancel = false; 
	var idSolitTipo;
	
	//  Função que faz a validação normal dos forms e faz a validação especial
	//pra o campo senhaAtendimento, que é dependente do meioSolicitacao.
    function validateInserirRegistroAtendimentoActionForm(form) {
    	var retorno = false;                                                                    
        if (bCancel){
      		retorno = true;
      	} else {
      		var form = document.forms[0];
	  		if (validateCaracterEspecial(form) && validateRequired(form) && validateDate(form) && validateHoraMinuto(form) && validateInteger(form)){
				if(form.parmId.value == '1'){
	  			if (verificarObrigatoriedadeSenhaAtendimento(form.meioSolicitacao.value) && (form.senhaAtendimento.value == '')){
       				alert('Para o Meio de Solicitação informado a Senha de Atendimento é obrigatória.');
       				form.senhaAtendimento.focus();
       			} else {
       				if (testarCampoValorInteiroComZero(form.senhaAtendimento, 'Senha de Atendimento')){
       					retorno = true;
       				}
       			}
				}else{
					retorno = true;
   				}
   			}
   		}
   		return retorno;
   	} 
   
   function caracteresespeciais () { 
     this.aa = new Array("numeroAtendimentoManual", "Número de Atendimento Manual possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ab = new Array("dataAtendimento", "Data do Atendimento possui caracteres especiais.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
     this.ac = new Array("hora", "Hora possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ad = new Array("tempoEsperaInicial", "Tempo de Espera Inicial possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ae = new Array("tempoEsperaFinal", "Tempo de Espera Final possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.af = new Array("unidade", "Unidade de Atendimento possui caracteres especiais.", new Function ("varName", " return this[varName];"));
    }

    function required () { 
     this.aa = new Array("dataAtendimento", "Informe Data do Atendimento.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
     this.ab = new Array("hora", "Informe Hora.", new Function ("varName", " return this[varName];"));
     this.ac = new Array("unidade", "Informe Unidade de Atendimento.", new Function ("varName", " return this[varName];"));
     this.ad = new Array("meioSolicitacao", "Informe Meio de Solicitação.", new Function ("varName", " return this[varName];"));
     this.ae = new Array("tipoSolicitacao", "Informe Tipo de Solicitação.", new Function ("varName", " return this[varName];"));
     this.af = new Array("especificacao", "Informe Especificação.", new Function ("varName", " return this[varName];"));
    } 

    function DateValidations () { 
     this.aa = new Array("dataAtendimento", "Data do Atendimento inválida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
    } 

    function HoraMinutoValidations () { 
     this.aa = new Array("hora", "Hora inválida. ", new Function ("varName", " return this[varName];"));
     this.ab = new Array("tempoEsperaInicial", "Tempo de Espera Inicial inválida. ", new Function ("varName", " return this[varName];"));
     this.ac = new Array("tempoEsperaFinal", "Tempo de Espera Final inválida. ", new Function ("varName", " return this[varName];"));
    } 

    function IntegerValidations () { 
     this.aa = new Array("numeroAtendimentoManual", "Número de Atendimento Manual deve ser numérico(a).", new Function ("varName", " return this[varName];"));
     this.ab = new Array("unidade", "Unidade de Atendimento deve ser numérico(a).", new Function ("varName", " return this[varName];"));
    }

	function verificarDataAtendimento(){
		
		var form = document.forms[0];
		
		if (form.especificacao.value > 0){
			if (form.dataAtendimento.value.length < 1){
				alert("Informe Data do Atendimento");
				form.dataAtendimento.focus();
			}
			else if (validateDate(form)){
				redirecionarSubmit('inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosGeraisAction&definirDataPrevista=OK');
			}
		}
	}
	
	function limpar(situacao){
	
		var form = document.forms[0];
	
		switch (situacao){
	       case 1:
	    	   form.sugerirUnidadeDestinoRA.value = 'S';
			   form.unidade.value = "";
			   form.descricaoUnidade.value = "";
			   
			   //Coloca o foco no objeto selecionado
			   form.unidade.focus();
			   break;
			   
		   default:
	          break;
		}
	}
	
	function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {
	    var form = document.forms[0];
	
	    if (tipoConsulta == 'arrecadador') {
	      form.banco.value = codigoRegistro;
	      form.descricaoBanco.value = descricaoRegistro;
	      form.descricaoBanco.style.color = "#000000";
	    }
	    if (tipoConsulta == 'registroAtendimento') {
	      document.getElementById("campoRAReiterada").value = codigoRegistro;
	      document.getElementById("campoDescricaoRA").value = descricaoRegistro;
	    }
	    if (tipoConsulta == 'unidadeOrganizacional') {
	    	form.sugerirUnidadeDestinoRA.value = 'S';
      		form.unidade.value = codigoRegistro;
      		form.descricaoUnidade.value = descricaoRegistro;
      		form.descricaoUnidade.style.color = "#000000";
      
      		redirecionarSubmit("inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosGeraisAction&pesquisarUnidade=OK");
    	}
	}
	
	function carregarTempoEsperaFinal(){
		
		var form = document.forms[0];
		
		if (form.tempoEsperaInicial.value.length > 0 && form.tempoEsperaFinal.readOnly == true){
			redirecionarSubmit('inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosGeraisAction&tempoEsperaFinalDesabilitado=OK');
		}
	}

	function carregarEspecificacao(obj) {
	 	var idSolicitacaoTipo = obj.options[obj.selectedIndex].value;
	  	var campoEspecificacao = document.getElementById("especificacao");
	    
	  	campoEspecificacao.length=0;
	  	if (idSolicitacaoTipo != "-1") {
			  AjaxService.carregarEspecificacao( idSolicitacaoTipo, function(especificacao) {
					  for (key in especificacao){
						  var novaOpcao = new Option(key, especificacao[key]);
						  campoEspecificacao.options[campoEspecificacao.length] = novaOpcao;
					
			  		   }
					  comboDataPrevista(campoEspecificacao);
					  });
	  	} else {
	  		var novaOpcao = new Option(" ","-1");
			campoEspecificacao.options[campoEspecificacao.length] = novaOpcao;
			comboDataPrevista(campoEspecificacao);
	  	}	
	}

	function comboDataPrevista(obj) {
		var form = document.forms[0];
		form.sugerirUnidadeDestinoRA.value = 'S';
	
		var idEspecificacao = obj.options[obj.selectedIndex].value;
		document.forms[0].especificacao.value = obj.options[obj.selectedIndex].value;
		
		redirecionarSubmit('/gsan/inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosGeraisAction&definirDataPrevista=OK&idEspecificacao=' + idEspecificacao + '&idSolitTipo=' + idSolitTipo);
	}

	function limparDataPrevista(dataAtendimento){
		
		var form = document.forms[0];
		
		if (dataAtendimento.value.length < 1){
			form.dataPrevista.value = "";
		}
	}

	//Função criada especialmente para o firefox
	function validarDataAtendimento(form){
	
		if (form.dataAtendimento.value.length > 0){
			if (verificaDataMensagemPersonalizada(form.dataAtendimento, "Data do Atendimento inválida.")){
				if (form.hora.value.length > 0){
					if (validaHoraMinutoMensagem(form.hora.value, "Hora do Atendiemto")){
						redirecionarSubmit('inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosGeraisAction&definirDataPrevista=OK');
					} else {
						form.hora.value = "";
						form.hora.focus();
					}
				}
				
			}
		}
	}
	
	//  Função pra habilidar ou desabilitar o campo SenhaAtendimento
	//de acordo com o Meio de Solicitação escolhido.
	function habilitarDesabilitarSenhaAtendimento(){
		var form = document.forms[0];
		if(form.parmId.value == '1'){
		if (verificarObrigatoriedadeSenhaAtendimento(form.meioSolicitacao.value)){
			form.senhaAtendimento.readOnly = false;
		} else {
			form.senhaAtendimento.readOnly = true;
			form.senhaAtendimento.value = '';
		}
	}
	}
		
	//  Função que verifica na Classe Java (RegistroAtendimento) as 
	//constantes que exigem SenhaAtendimento.
	// Retorna true ou false.
	function verificarObrigatoriedadeSenhaAtendimento(numeroMeioSolicitacao){
		// colocar aqui, se necessário, outras constantes
		var balcao = <%=""+ RegistroAtendimento.MEIO_SOLICITACAO_BALCAO %>;
		
		var retorno = false;
		// validar aqui todas as constantes.
		if(numeroMeioSolicitacao == balcao){
			retorno = true;
		}
		return retorno;
	}

	function limparPesquisaRA() {
		document.getElementById("campoRAReiterada").value = "";
		document.getElementById("campoDescricaoRA").value = "";
	}
	
	/* Chama Popup */ 
	function chamarPopup(url, tipo, objeto, codigoObjeto, altura, largura, msg,objetoRelacionado){
		if(objetoRelacionado.disabled != true){
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
	
	function verificarIndicadorProcessoAdmJud(indicadorProcessoAdmJud, permissaoAlterarProcessoAdmJud){
		form = document.forms[0];
		
		if(permissaoAlterarProcessoAdmJud == <%=ConstantesSistema.NAO.toString()%>){
			form.indicadorProcessoAdmJud[1].checked = true;
			form.indicadorProcessoAdmJud[0].disabled = true;
			form.indicadorProcessoAdmJud[1].disabled = true;
		}else if(indicadorProcessoAdmJud == <%=ConstantesSistema.SIM.toString()%>){
			form.indicadorProcessoAdmJud[0].checked = true;
		}else{
			form.indicadorProcessoAdmJud[1].checked = true;
		}
	}
	
	function habilitaCampoNumeroProcessoAgencia(){

		var form = document.forms[0];

		for(var i=0;i<form.indicadorProcessoAdmJud.length;i++) {

			if(form.indicadorProcessoAdmJud[i].checked) {

				if(form.indicadorProcessoAdmJud[i].value == 1){
					form.numeroProcessoAgencia.disabled = false;
				} else {
					form.numeroProcessoAgencia.value = "";
					form.numeroProcessoAgencia.disabled = true;
				}
			}
		}
	}
	
   function consultarServicosAssociados(){
		
		var form = document.forms[0];
		var especificacao = form.especificacao.value;
		
		if (!isNaN(especificacao) && especificacao.length > 0 && especificacao.indexOf(',') == -1 &&
				especificacao.indexOf('.') == -1 && ((especificacao * 1) > 0)){
			
			abrirPopup("/gsan/exibirConsultarServicoAssociadoAction.do?idSolicitacaoTipoEspecificacao=" + especificacao, 400, 690);
		}
	}
	
	
</SCRIPT>
<script type='text/javascript' src='<c:out value='${pageContext.request.contextPath}'/>/dwr/engine.js'> </script>
<script type='text/javascript' src='<c:out value='${pageContext.request.contextPath}'/>/dwr/util.js'> </script>
<script type='text/javascript' src='<c:out value='${pageContext.request.contextPath}'/>/dwr/interface/AjaxService.js'> </script>

<html:javascript staticJavascript="false"  formName="InserirRegistroAtendimentoActionForm" dynamicJavascript="false" />
</head>

<body leftmargin="5" topmargin="5" onload="setarFoco('${requestScope.nomeCampo}'); limitTextArea(document.forms[0].observacao, 400, document.getElementById('utilizado'), document.getElementById('limite')); habilitarDesabilitarSenhaAtendimento();verificarIndicadorProcessoAdmJud('${sessionScope.indicadorProcessoAdmJud}','${sessionScope.permissaoAlterarProcessoAdmJud}');habilitaCampoNumeroProcessoAgencia();">

<div id="formDiv">
<html:form action="/inserirRegistroAtendimentoWizardAction" method="post">

<html:hidden property="idImovel"/>
<html:hidden property="sugerirUnidadeDestinoRA" />
<html:hidden property="idMunicipio"/>
<html:hidden property="cdBairro"/>
<html:hidden property="idBairroArea"/>
<html:hidden name="sistemaParametro" property="parmId"/>

<jsp:include page="/jsp/util/wizard/navegacao_abas_wizard_valida_avancar.jsp?numeroPagina=1"/>


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
          <td class="parabg">Inserir RA - Registro de Atendimento</td>
          <td width="11"><img border="0" src="<bean:message key="caminho.imagens"/>parahead_right.gif"/></td>
        </tr>
      </table>
      <p>&nbsp;</p>

      <table width="100%" border="0">
      <tr>
      	<td colspan="2">Para inserir o RA - Registro de Atendimento, informe os dados gerais abaixo:</td>
      </tr>
      <tr>
      	<td HEIGHT="30"><strong>Tipo do Atendimento:<font color="#FF0000">*</font></strong></td>
        <td>
        	<strong>
				<html:radio property="tipo" value="1" tabindex="1" onclick="redirecionarSubmit('inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosGeraisAction&mudarTipo=OK');"/>on-line  
				<html:radio property="tipo" value="2" tabindex="2" onclick="redirecionarSubmit('inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosGeraisAction&mudarTipo=OK');"/>manual 
			</strong>
		</td>
      </tr>
      
      <tr>
      	<td HEIGHT="30"><strong>Protocolo: </strong></td>
      	<td><html:text property="sequenceRA" readonly="true" style="background-color:#EFEFEF; border:0; color:#FF0000; font-weight: bold;" size="10"/>
      	 <div align="right">
			<a href="javascript:abrirPopupComSubmit('exibirConsultarImovelAction.do?idImovel=${idImovel}',400,800);">
				<strong>Consultar Imóvel</strong>
			</a>
			<br />
			<!-- Consultar RA - Registro de Atendimento  -->
			<a href="javascript:abrirPopupComSubmit('exibirPesquisarRegistroAtendimentoAction.do?origemInserirRA=true',400,800);">
				<strong>Pesquisar RA - Registro de Atendimento</strong>
			</a>
		 </div>  
      	</td>
      </tr>
      
      <tr>	
      	<td HEIGHT="30"><strong>Número Manual:</strong></td>
        <td>
			<logic:equal name="InserirRegistroAtendimentoActionForm" property="tipo" value="1">
				<html:text 
					property="numeroAtendimentoManual" 
					size="12" 
					maxlength="11" 
					tabindex="3" 
					onkeypress="javascript:return isCampoNumerico(event);"/>
			</logic:equal>
			
			<logic:notEqual name="InserirRegistroAtendimentoActionForm" property="tipo" value="1">
				<html:text 
					property="numeroAtendimentoManual" 
					size="12" 
					maxlength="11"
					tabindex="3"
					onkeypress="javascript:return isCampoNumerico(event);"/>
			</logic:notEqual>
		</td>
      </tr>
      <tr>
      	<td HEIGHT="30"><strong>Data do Atendimento:<font color="#FF0000">*</font></strong></td>
        <td>
			<logic:equal name="InserirRegistroAtendimentoActionForm" property="tipo" value="1">
				<html:text property="dataAtendimento" size="11" maxlength="10" tabindex="4" onkeyup="mascaraData(this, event)" readonly="true"/>
				<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="20" border="0" alt="Exibir Calendário" tabindex="4"/>
			</logic:equal>
			
			<logic:notEqual name="InserirRegistroAtendimentoActionForm" property="tipo" value="1">
				<html:text property="dataAtendimento" size="11" maxlength="10" tabindex="4" onkeyup="mascaraData(this, event);limparDataPrevista(this);"/>
				<a href="javascript:abrirCalendario('InserirRegistroAtendimentoActionForm', 'dataAtendimento');">
				<img border="0" src="<bean:message key='caminho.imagens'/>calendario.gif" width="20" border="0" alt="Exibir Calendário" tabindex="4"/></a>
			</logic:notEqual>
			
			<strong>&nbsp;(dd/mm/aaaa)</strong>
		</td>
      </tr>
      <tr>
      	<td HEIGHT="30"><strong>Hora do Atendimento:<font color="#FF0000">*</font></strong></td>
        <td>
        	<logic:equal name="InserirRegistroAtendimentoActionForm" property="tipo" value="1">
				<html:text property="hora" size="10" maxlength="5" tabindex="5" onkeyup="mascaraHoraSemMensagem(this, event)" readonly="true"/>
			</logic:equal>
			
			<logic:notEqual name="InserirRegistroAtendimentoActionForm" property="tipo" value="1">
				<html:text property="hora" size="10" maxlength="5" tabindex="5" onkeyup="mascaraHoraSemMensagem(this, event)"  onblur="validarDataAtendimento(document.forms[0]);"/>
			</logic:notEqual>
			
			<strong>&nbsp;(hh:mm)</strong>
		</td>
      </tr>
      <tr>
      	<td HEIGHT="30"><strong>Tempo de Espera:</strong></td>
        <td>
			<html:text property="tempoEsperaInicial" size="10" maxlength="5" tabindex="6" onkeyup="if (mascaraHoraSemMensagem(this, event)){carregarTempoEsperaFinal();}"/>
			<strong>&nbsp;(hh:mm)</strong>
			
			&nbsp;&nbsp;&nbsp;
			
			<logic:equal name="InserirRegistroAtendimentoActionForm" property="tipo" value="1">
				<html:text property="tempoEsperaFinal" size="10" maxlength="5" tabindex="7" onkeyup="mascaraHoraSemMensagem(this, event)" readonly="true"/>
			</logic:equal>
			
			<logic:notEqual name="InserirRegistroAtendimentoActionForm" property="tipo" value="1">
				<html:text property="tempoEsperaFinal" size="10" maxlength="5" tabindex="7" onkeyup="mascaraHoraSemMensagem(this, event)"/>
			</logic:notEqual>
			
			<strong>&nbsp;(hh:mm)</strong>
			
		</td>
      </tr>
      <tr>
      	<td HEIGHT="30"><strong>Unidade de Atendimento:<font color="#FF0000">*</font></strong></td>
        <td>
        	
        	<html:text property="unidade" size="8" maxlength="8" tabindex="8" onkeypress="document.forms[0].sugerirUnidadeDestinoRA.value = 'S';validaEnterComMensagem(event, 'inserirRegistroAtendimentoWizardAction.do?action=exibirInserirRegistroAtendimentoDadosGeraisAction&pesquisarUnidade=OK', 'unidade', 'Unidade');" onchange="document.forms[0].sugerirUnidadeDestinoRA.value = 'S';" />
			<a href="javascript:abrirPopup('exibirPesquisarUnidadeOrganizacionalAction.do', 410, 790);">
			<img src="<bean:message key='caminho.imagens'/>pesquisa.gif" width="23" height="21" alt="Pesquisar" border="0"></a>

			<logic:present name="corUnidade">

				<logic:equal name="corUnidade" value="exception">				
					<html:text property="descricaoUnidade" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
				</logic:equal>

				<logic:notEqual name="corUnidade" value="exception">				
					<html:text property="descricaoUnidade" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
				</logic:notEqual>

			</logic:present>

			<logic:notPresent name="corUnidade">

				<logic:empty name="InserirRegistroAtendimentoActionForm" property="unidade">				
					<html:text property="descricaoUnidade" value="" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #ff0000"/>
				</logic:empty>
				<logic:notEmpty name="InserirRegistroAtendimentoActionForm" property="unidade">				
					<html:text property="descricaoUnidade" size="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
				</logic:notEmpty>
				

			</logic:notPresent>
        	
        	<a href="javascript:limpar(1);">
        	<img src="<bean:message key='caminho.imagens'/>limparcampo.gif" alt="Apagar" border="0"></a>
		</td>
      </tr>
      <tr>
      	<td HEIGHT="30"><strong>Meio de Solicitação:<font color="#FF0000">*</font></strong></td>
        <td>
			<html:select property="meioSolicitacao" style="width: 200px;font-size:11px;" 
					onchange="javascript:habilitarDesabilitarSenhaAtendimento();" tabindex="9">
				<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
				<html:options collection="colecaoMeioSolicitacao" labelProperty="descricao" property="id"/>
			</html:select>
		</td>
      </tr>
      <logic:equal name="sistemaParametro" property="parmId" scope="session" value="<%=SistemaParametro.INDICADOR_EMPRESA_ADA.toString()%>">
	  <tr>
	  	<td HEIGHT="30"><strong>Senha de Atendimento:</strong></td>
				<td>
					<html:text property="senhaAtendimento" size="5" maxlength="5" tabindex="10" readonly="true"/>
	  </td>
		</tr>
	  </logic:equal>
	  	
      <tr>
        <td HEIGHT="30"><strong>Tipo de Solicitação:<font color="#FF0000">*</font></strong></td>
        <td>
			<html:select property="tipoSolicitacao" style="width: 350px;font-size:11px;" tabindex="11" onchange="carregarEspecificacao(this);">
				<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
				<html:options collection="colecaoSolicitacaoTipo" labelProperty="descricao" property="id" />
			</html:select>
		</td>
      </tr>
      <tr>
        <td HEIGHT="30"><strong>Especificação:<font color="#FF0000">*</font></strong></td>
        <td>
			<html:select property="especificacao" style="width: 350px;font-size:11px;" tabindex="12" styleId="especificacao" onchange="comboDataPrevista(this);"> 
				<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
				<html:options collection="colecaoSolicitacaoTipoEspecificacao" labelProperty="descricao" property="id"/>
			</html:select>
		</td>
		<td>
		
		  <logic:present name="exibirBotaoServicoAssociado">
	        <tr>
	           <td colspan="3" HEIGHT="10" align="right">
	         	<input type="button" class="bottonRightCol" value="Consultar Serviços Associados à Especificação" id="botaoRecuperarDados" align="right" onclick="consultarServicosAssociados();">	
	         </td>
	       </tr>
           </logic:present>
		
		</td>
		
      </tr>
      
      <tr>
        <td><strong>Quantidade de Prestações da Guia de Pagamento :</strong></td>
		  <td colspan="3">
		  
		  	<logic:present name="habilitarPrestacaoGuia" scope="session">
		  		<html:text
						property="quantidadePrestacoesGuiaPagamento" size="10" maxlength="3" 
						onkeyup="javascript:verificaNumeroInteiro(this);"/>
			</logic:present>
			
		  	<logic:notPresent name="habilitarPrestacaoGuia" scope="session">
		  		<html:text
						property="quantidadePrestacoesGuiaPagamento" size="10" maxlength="3" readonly="true"
						onkeyup="javascript:verificaNumeroInteiro(this);"/>
			</logic:notPresent>									
	
        </td>
      </tr>      
      
      <tr>
		<td><strong>RA a reiterar:</strong></td>
		<td>
		<logic:present name="raAReiterar" scope="session">
			<html:text maxlength="9" tabindex="1" property="idRaReiteracao"	size="9" styleId="campoRAReiterada" disabled="false"
				onkeypress="validaEnterComMensagem(event, 'exibirInserirRegistroAtendimentoAction.do?objetoConsulta=1&veioDeInserirRA=true','idRaReiteracao','Numero RA');"/>
				<a href="javascript:chamarPopup('exibirPesquisarRegistroAtendimentoAction.do', 'registroAtendimento', null, null, 600, 730, '', document.forms[0].idRaReiteracao);">
					<img width="23" height="21"	border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" title="Pesquisar RA" /></a> 
		</logic:present> 
		<logic:notPresent name="raAReiterar" scope="session">
			<html:text maxlength="9" tabindex="1" property="idRaReiteracao"	size="9" styleId="campoRAReiterada" disabled="true"
				onkeypress="validaEnterComMensagem(event, 'exibirInserirRegistroAtendimentoAction.do?objetoConsulta=1&veioDeInserirRA=true','idRaReiteracao','Numero RA');"/>
				<a href="javascript:chamarPopup('exibirPesquisarRegistroAtendimentoAction.do', 'registroAtendimento', null, null, 600, 730, '', document.forms[0].idRaReiteracao);">
					<img width="23" height="21"	border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif" title="Pesquisar RA" /></a> 
		</logic:notPresent> 	
				<logic:present name="numeroRAEncontrada" scope="request">
					<html:text property="descricaoRA" styleId="campoDescricaoRA" size="45"	maxlength="45" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000" />
				</logic:present> 
				<logic:notPresent name="numeroRAEncontrada" scope="request">
					<html:text property="descricaoRA" styleId="campoDescricaoRA" size="45"	maxlength="45" readonly="true" style="background-color:#EFEFEF; border:0; color: red" />
				</logic:notPresent>
				<a href="javascript:limparPesquisaRA();"> 
					<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"	border="0" title="Apagar" /></a>
		</td>
	</tr>
      <tr>
      	<td HEIGHT="30"><strong>Data Prevista:</strong></td>
        <td>
			<html:text property="dataPrevista" styleId="dataPrevista" size="16" maxlength="16" tabindex="13" onkeyup="mascaraData(this, event)" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
		</td>
      </tr>
      <tr>
      	<td HEIGHT="30"><strong>Nome Contato:</strong></td>
        <td>
			<html:text property="nomeContato" size="60" maxlength="60" tabindex="13" />
		</td>
      </tr>
      <tr>
      	<td HEIGHT="30"><strong>Observação do R.A:</strong></td>
        <td>
			<html:textarea property="observacao" cols="40" rows="5" onkeyup="limitTextArea(document.forms[0].observacao, 400, document.getElementById('utilizado'), document.getElementById('limite'));"/><br>
			<strong><span id="utilizado">0</span>/<span id="limite">400</span></strong>
		</td>
      </tr>
      <tr>
      	<td HEIGHT="30"><strong>Motivo Atendimento Incompleto:</strong></td>
        <td>
			<html:select property="motivoAtendimentoIncompleto" style="width: 350px;font-size:11px;" tabindex="14" > 
				<html:option value="<%=""+ConstantesSistema.NUMERO_NAO_INFORMADO%>">&nbsp;</html:option>
				<html:options collection="colecaoMotivoAtendimentoIncompleto" labelProperty="descricao" property="id"/>
			</html:select>
		</td>
      </tr>
   	  <tr> 
        <td><strong>Processo Adm./Jud. ?</strong></td>
           <td HEIGHT="30">
            <span class="style2"><strong> 
             <label>
             <html:radio property="indicadorProcessoAdmJud" value="<%=ConstantesSistema.SIM.toString()%>" onclick="javascript:habilitaCampoNumeroProcessoAgencia()"/> Sim
             </label>
		  	 <label> 
		 		 &nbsp;&nbsp; 
		 		 <html:radio property="indicadorProcessoAdmJud" value="<%=ConstantesSistema.NAO.toString()%>" onclick="javascript:habilitaCampoNumeroProcessoAgencia()"/> Não
		  	 </label>
            </strong></span>
           </td>
      </tr>
      <tr>
		<td HEIGHT="30">
			<strong> Número do Processo na Agência:</strong></td>
		<td>
			<html:text property="numeroProcessoAgencia" 
						size="25" 
						maxlength="20" 
						tabindex="3"/>
		</td>
	  </tr>
      <tr>
      	<td colspan="2" height="10"></td>
      </tr>
      <tr>
        <td colspan="2">
			<div align="right">
				<jsp:include page="/jsp/util/wizard/navegacao_botoes_wizard_valida_avancar_ra.jsp?numeroPagina=1"/>
			</div>
		</td>
      </tr>
      </table>
      <p>&nbsp;</p>
	</td>
  </tr>
</table>



<%@ include file="/jsp/util/rodape.jsp"%>
</html:form>
</div>

<%@ include file="/jsp/util/telaespera.jsp"%>

<script>
document.getElementById('botaoConcluir').onclick = function() { botaoAvancarTelaEspera('/gsan/inserirRegistroAtendimentoWizardAction.do?concluir=true&action=inserirRegistroAtendimentoDadosGeraisAction'); }
</script>

</body>
</html:html>

