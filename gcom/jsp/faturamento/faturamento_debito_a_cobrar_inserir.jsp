<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
<html:html>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false"
	formName="InserirDebitoACobrarActionForm" />

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>



<SCRIPT LANGUAGE="JavaScript">
<!--


function limparRegistroAtendimento(){
	var form = document.forms[0];
///	alert("form.registroAtendimento.disabled="+form.registroAtendimento.disabled);
	if(form.registroAtendimento.disabled == false){
		form.registroAtendimento.value = "";
		form.nomeRegistroAtendimento.value = "";
		form.ordemServico.disabled = false;
		form.ordemServico.value = "";
		form.nomeOrdemServico.value = "";
		if(form.idImovel != null){
		  form.idImovel.disabled = false;
		  form.idImovel.value = "";
		  form.inscricaoImovelInformada.value = ""; 
		}
		limparImovel();
	}
}

function pesquisarRegistroAtendimento(){
	var form = document.forms[0];
	if(form.registroAtendimento.disabled == false){
		abrirPopup('exibirPesquisarRegistroAtendimentoAction.do', 400, 800);
	}
}


function limparOrdemServico(){
	var form = document.forms[0];
	
	if(form.ordemServico.disabled == false){
		form.ordemServico.value = "";
		form.nomeOrdemServico.value = "";
		form.registroAtendimento.disabled = false;
		form.registroAtendimento.value = "";
		form.nomeRegistroAtendimento.value = "";
		form.idTipoDebito.disabled = false;
		form.idTipoDebito.value = "";
		form.descricaoTipoDebito.value = "";		
		form.valorTotalServico.value = "";	
		
	    if(form.idImovel != null){
		  form.idImovel.disabled = false;
		  form.idImovel.value = "";
		  form.inscricaoImovelInformada.value = ""; 
		}
		limparImovel();
	}
}
function pesquisarOrdemServico(){
	var form = document.forms[0];
	if(form.ordemServico.disabled == false){
		abrirPopup('exibirPesquisarOrdemServicoAction.do', 400, 800);
	}
}
	
  
function limparCalculoPrestacao(){
	var form = document.forms[0];
	form.valorPrestacao.value = "";
	form.valorTotalServicoAParcelar.value = "";
	form.valorJuros.value = "";
}

function pesquisarTipoDebito(){
	var form = document.forms[0];
	if(form.idTipoDebito.disabled == false){
		limparCalculoPrestacao();abrirPopup('exibirPesquisarTipoDebitoAction.do?limparForm=1&tipoFinanciamentoServico=SIM', 500, 600);	
	}
}

function limparTipoDebito(){
	var form = document.forms[0];
	if(form.idTipoDebito.disabled == false){
		form.idTipoDebito.value = "";
		form.descricaoTipoDebito.value = "";
		form.valorTotalServico.value = "";	
		limparCalculoPrestacao();
	}
}

function limparIdImovel(){
	var form = document.forms[0];
	
	if(form.idImovel.disabled == false){
		form.idImovel.value = "";
		form.inscricaoImovelInformada.value = "";
		form.ordemServico.value = "";
		form.nomeOrdemServico.value = "";
		form.ordemServico.disabled = false;
		form.registroAtendimento.disabled = false;
		form.registroAtendimento.value = "";
		form.nomeRegistroAtendimento.value = "";
		form.idTipoDebito.disabled = false;
		form.idTipoDebito.value = "";
		form.descricaoTipoDebito.value = "";
		limparImovel();
	}
}

	  	
-->    
</script>

<script language="JavaScript">
<!-- Begin
function DecimalValidations () {
     this.ae = new Array("taxaJurosFinanciamento", "Taxa de Juros do Financiamento possui caracteres especiais.", new Function ("varName", " return this[varName];"));
}

function IntegerValidations  () {
///    this.am = new Array("codigoImovel", "Matr�cula do Im�vel deve somente conter n�meros positivos.", new Function ("varName", " return this[varName];"));

    this.am = new Array("ordemServico", "Ordem de Servi�o deve somente conter n�meros positivos.", new Function ("varName", " return this[varName];"));        
    this.an = new Array("registroAtendimento", "RA - Registro de Atendimento deve somente conter n�meros positivos.", new Function ("varName", " return this[varName];"));        
    this.aq = new Array("idTipoDebito", "Tipo de D�bito deve somente conter n�meros positivos.", new Function ("varName", " return this[varName];"));        
    this.an = new Array("numeroPrestacoes", "N�mero de Presta��es deve somente conter n�meros positivos.", new Function ("varName", " return this[varName];"));
}
	
	
function recuperarDadosPopup(codigoRegistro, descricaoRegistro, tipoConsulta) {

    var form = document.forms[0];

    if (tipoConsulta == 'imovel') {
      limparForm();
      form.idImovel.value = codigoRegistro;
      form.action = 'exibirInserirDebitoACobrarAction.do?objetoConsulta=4'
      form.submit();
    }
    
    if (tipoConsulta == 'tipoDebito') {
      form.idTipoDebito.value = codigoRegistro;
      form.descricaoTipoDebito.value = descricaoRegistro;
      form.descricaoTipoDebito.style.color = "#000000";
      form.action = 'exibirInserirDebitoACobrarAction.do?objetoConsulta=1'
	  form.submit();
    }

    if (tipoConsulta == 'ordemServico') {
      form.ordemServico.value = codigoRegistro;
      form.nomeOrdemServico.value = descricaoRegistro;
      form.nomeOrdemServico.style.color = "#000000";
      form.action = 'exibirInserirDebitoACobrarAction.do?objetoConsulta=3'
      form.submit();

    }
    if (tipoConsulta == 'registroAtendimento') {
      form.registroAtendimento.value = codigoRegistro;
      form.nomeRegistroAtendimento.value = descricaoRegistro;
      form.nomeRegistroAtendimento.style.color = "#000000";
      form.action = 'exibirInserirDebitoACobrarAction.do?objetoConsulta=2'
      form.submit();
    }

   
}


function limparForm(){
 	var form = document.forms[0];
 	
 	for (var i=0;i < form.elements.length;i++){
		var elemento = form.elements[i];
		if (elemento.type == "text"){
			elemento.value = "";
		}
	}
	
	redirecionarSubmit("exibirInserirDebitoACobrarAction.do?menu=sim");
}
 
 
 function limparImovel(){
 	var form = document.forms[0];
	form.codigoImovel.value = '';
	form.inscricaoImovel.value = '';
	form.nomeCliente.value = '';
	form.situacaoAgua.value = '';
	form.situacaoEsgoto.value = '';
 }
 
 
function validarForm(form, inserir){

	
		var condicao = true;
	 	var form = document.forms[0];
		
		if(form.idImovel != null){
		  if(form.ordemServico.value == "" && form.registroAtendimento.value == '' && form.idImovel.value == ""){
			  alert("Informe RA - Registro de Atendimento, Ordem de Servi�o ou Im�vel");
			  form.registroAtendimento.focus();
			  condicao = false;
		  }
		}else{
		  if(form.ordemServico.value == "" && form.registroAtendimento.value == ''){
			  alert("Informe RA - Registro de Atendimento ou Ordem de Servi�o");
			  form.registroAtendimento.focus();
			  condicao = false;
		  }
		}
		
		if(form.idImovel != null){
		  if(condicao){		
			if(form.ordemServico.value == "" && form.registroAtendimento.value != '' && form.idTipoDebito.value == ""){
				alert("Informe D�bito Tipo");
				form.idTipoDebito.focus();
				condicao = false;
			}
			if(form.ordemServico.value == "" && form.registroAtendimento.value == "" && form.idImovel.value != "" && form.idTipoDebito.value == ""){
				alert("Informe D�bito Tipo");
				form.idTipoDebito.focus();
				condicao = false;
			}
		  }
		}else{
		  if(condicao){		
			if(form.ordemServico.value == "" && form.registroAtendimento.value != '' && form.idTipoDebito.value == ""){
				alert("Informe D�bito Tipo");
				form.idTipoDebito.focus();
				condicao = false;
			}
		  }		
		}
		

	if(condicao){
		if (validateInserirDebitoACobrarActionForm(form)){
			if (inserir){
				urlRedirect = "/gsan/inserirDebitoACobrarAction.do";
			}else{
				urlRedirect = "/gsan/calcularPrestacaoDebitoACobrarAction.do";
			}
			var vlEntrada = returnObject(form,"valorEntrada");
			var vlTotalServico = returnObject(form,"valorTotalServico");
	
			var vlEntr = vlEntrada.value;
			vlEntr = vlEntr.replace('.','');
			vlEntr = vlEntr.replace(',','.');
			
			var vlTotServ = vlTotalServico.value;
			vlTotServ = vlTotServ.replace('.','');
			vlTotServ = vlTotServ.replace(',','.');
			
			
			if( (vlEntr*1) > (vlTotServ*1) ){
				alert("Valor de Entrada deve ser inferior a Valor Total do Servi�o.");
				form.vlEntrada.focus();
			}else if( (vlEntr*1) == (vlTotServ*1) ){
				alert("Valor de Entrada deve ser inferior a Valor Total do Servi�o.");
				form.vlEntrada.focus();
			}else if(form.codigoImovel.value.length < 1){
				if(form.ordemServico.value == "" && form.registroAtendimento.value != ''){
					alert("Dados do RA - Registro de Atendimento n�o pesquisados. Deseja pesquisar agora?")
					validaEnterSemEvento('exibirInserirDebitoACobrarAction.do?objetoConsulta=2', 'registroAtendimento');
				}
				if(form.ordemServico.value != "" && form.registroAtendimento.value == ''){
					alert("Dados da Ordem de Servico n�o pesquisados. Deseja pesquisar agora?")
					validaEnterSemEvento('exibirInserirDebitoACobrarAction.do?objetoConsulta=3', 'ordemServico');
				}
			}else if(form.inscricaoImovel.value.length < 1){
				exibirDadosImovel(form);
			}else if(form.valorTotalServico.value.length < 1){
				alert("Informe Valor Total Servico.");
				form.codigoImovel.focus();
			}else if(form.idTipoDebito.value.length < 1){
				alert("Informe Tipo de D�bito.");
				form.idTipoDebito.focus();
			}else if(form.idTipoDebito.value.length < 1){
				alert("Realize Calculo da Presta��o.");
				form.calcularPrestacao.focus();
			}else if((inserir) && (form.valorPrestacao.value.length < 1)){	
				alert("Realize Calculo da Presta��o.");
				form.calcularPrestacao.focus();
			}else{
				redirecionarSubmit(urlRedirect);
			}
		}
	}
}
 

function exibirDadosImovel(form){

	codigoImovel = form.codigoImovel.value;
	inscricaoImovel = form.inscricaoImovel.value;

	if (codigoImovel.length > 0 && inscricaoImovel.length < 1){
		
		alert("Dados do Im�vel n�o pesquisados. Deseja pesquisar agora?")
		validaEnterSemEvento('exibirInserirDebitoACobrarAction.do', 'codigoImovel');	
		
	}
	
}
 
function bloquearOrdemServico(){
 	var form = document.forms[0];
	if(form.registroAtendimento.value != ""){
		form.ordemServico.disabled = true;
		if(form.idImovel != null){
		  form.idImovel.disabled = true;
		}
	} else{
		form.ordemServico.disabled = false;		
    	limparOrdemServico();
	}
}

function bloquearRegistroAtendimento(){
	 	var form = document.forms[0];
	if(form.ordemServico.value != ""){
		form.registroAtendimento.disabled = true;
		if(form.idImovel != null){
		  form.idImovel.disabled = true;
		}
	}else{
		form.registroAtendimento.disabled = false;		
		form.idTipoDebito.disabled = false;	
		limparRegistroAtendimento();
		limparTipoDebito();		
	}
	 
}

function bloquearOrdemServicoRa(){
	var form = document.forms[0];
	if(form.idImovel.value != ""){
		form.registroAtendimento.disabled = true;
		form.ordemServico.disabled = true;
	}else{
		form.registroAtendimento.disabled = false;
		form.ordemServico.disabled = false;	
		limparRegistroAtendimento();
    	limparOrdemServico();
	}	 
}


 
-->
</script>
</head>

<body leftmargin="5" topmargin="5"
	onload="javascript:setarFoco('${requestScope.nomeCampo}');">
<html:form action="/exibirInserirDebitoACobrarAction"
	name="InserirDebitoACobrarActionForm" method="post"
	type="gcom.gui.faturamento.InserirDebitoACobrarActionForm"
	onsubmit="return validateInserirDebitoACobrarActionForm(this);">

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

			<td valign="top" class="centercoltext"><!--In�cio Tabela Reference a P�gina��o da Tela de Processo-->
			<p>&nbsp;</p>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0" src="imagens/parahead_left.gif" /></td>
					<td class="parabg">Inserir D&eacute;bito a Cobrar</td>
					<td width="11" valign="top"><img border="0"
						src="imagens/parahead_right.gif" /></td>
				</tr>
			</table>
			<!--Fim Tabela Reference a P�gina��o da Tela de Processo-->
			<p>&nbsp;</p>
			<table width="100%" border="0">
				<tr>
					<td>Para inserir o d&eacute;bito a cobrar, informe os dados abaixo:</td>
					<td align="right"></td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td width="180"><strong>RA - Registro de Atendimento:<font
						color="#FF0000"></font></strong></td>
					<td colspan="3" align="right">
					<div align="left"><logic:present name="travarRegistroAtendimento">
						<html:text property="registroAtendimento" maxlength="9" size="9"
							tabindex="1" onfocus="javascript:bloquearOrdemServico();"
							onkeyup="javascript:bloquearOrdemServico();"
							onkeypress="validaEnterComMensagem(event, 'exibirInserirDebitoACobrarAction.do?objetoConsulta=2', 
							'registroAtendimento','RA - Registro de Atendimento');" />
					</logic:present> <logic:notPresent name="travarRegistroAtendimento">
						<html:text property="registroAtendimento" disabled="true"
							maxlength="9" size="9" tabindex="1"
							onfocus="javascript:bloquearOrdemServico();"
							onkeyup="javascript:bloquearOrdemServico();"
							onkeypress="validaEnterComMensagem(event, 'exibirInserirDebitoACobrarAction.do?objetoConsulta=2', 
							'registroAtendimento','RA - Registro de Atendimento');" />
					</logic:notPresent> <a
						href="javascript:pesquisarRegistroAtendimento();"> <img width="23"
						height="21"
						src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" /></a>
					<logic:present name="corRegistroAtendimento">
						<logic:equal name="corRegistroAtendimento" value="exception">
							<html:text property="nomeRegistroAtendimento" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000"
								size="40" maxlength="40" />
						</logic:equal>
						<logic:notEqual name="corRegistroAtendimento" value="exception">
							<html:text property="nomeRegistroAtendimento" size="40"
								maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> <logic:notPresent name="corRegistroAtendimento">
						<logic:empty name="InserirDebitoACobrarActionForm"
							property="registroAtendimento">
							<html:text property="nomeRegistroAtendimento" size="40"
								maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="InserirDebitoACobrarActionForm"
							property="registroAtendimento">
							<html:text property="nomeRegistroAtendimento" size="40"
								maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
					</logic:notPresent> <a
						href="javascript:limparRegistroAtendimento();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar RA - Registro de Atendimento" /> </a></div>
					</td>
				</tr>
				<tr>
					<td width="180"><strong>Ordem de Servi&ccedil;o:</strong></td>
					<td colspan="3" align="left">
					<div align="left"><logic:present name="travarOrdemServico">
						<html:text property="ordemServico" maxlength="9" size="9"
							tabindex="1" onfocus="javascript:bloquearRegistroAtendimento();"
							onkeyup="javascript:bloquearRegistroAtendimento();"
							onkeypress="validaEnterComMensagem(event, 'exibirInserirDebitoACobrarAction.do?objetoConsulta=3', 
							'ordemServico','Ordem de Servi�o');" />
					</logic:present> <logic:notPresent name="travarOrdemServico">
						<html:text property="ordemServico" maxlength="9" disabled="true"
							size="9" tabindex="1"
							onfocus="javascript:bloquearRegistroAtendimento();"
							onkeyup="javascript:bloquearRegistroAtendimento();"
							onkeypress="validaEnterComMensagem(event, 'exibirInserirDebitoACobrarAction.do?objetoConsulta=3', 
							'ordemServico','Ordem de Servi�o');" />
					</logic:notPresent> <a href="javascript:pesquisarOrdemServico();">
					<img width="23" height="21"
						src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0" /></a>
					<logic:present name="corNomeOrdemServico">
						<logic:equal name="corNomeOrdemServico" value="exception">
							<html:text property="nomeOrdemServico" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000"
								size="40" maxlength="40" />
						</logic:equal>
						<logic:notEqual name="corNomeOrdemServico" value="exception">
							<html:text property="nomeOrdemServico" size="40" maxlength="40"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>
					</logic:present> <logic:notPresent name="corNomeOrdemServico">
						<logic:empty name="InserirDebitoACobrarActionForm"
							property="ordemServico">
							<html:text property="nomeOrdemServico" size="40" maxlength="40"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="InserirDebitoACobrarActionForm"
							property="ordemServico">
							<html:text property="nomeOrdemServico" size="40" maxlength="40"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
					</logic:notPresent> <a href="javascript:limparOrdemServico();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar Ordem de Servi�o" /> </a></div>
					</td>
				</tr>
				<logic:present name="informarImovel">
				<tr>
					<td width="180"><strong>Matr�cula do Im�vel:</strong></td>
					<td colspan="3" align="left"><logic:present name="travarImovel">
				
					
						<html:text property="idImovel" maxlength="9" size="9"
							tabindex="1" onfocus="javascript:bloquearOrdemServicoRa();"
							onkeyup="javascript:bloquearOrdemServicoRa();"
							onkeypress="validaEnterComMensagem(event, 'exibirInserirDebitoACobrarAction.do?objetoConsulta=4', 
							'idImovel','Im�vel');" />
							
						<a
						href="javascript:abrirPopup('exibirPesquisarImovelAction.do', 400, 800);"
						tabindex="1"> <img width="23" height="21"
						src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0"
						title="Pesquisar Im�vel" /></a> 
					<logic:present name="corInscricao">

						<logic:equal name="corInscricao" value="exception">
							<html:text property="inscricaoImovelInformada" size="40" maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>

						<logic:notEqual name="corInscricao" value="exception">
							<html:text property="inscricaoImovelInformada" size="40" maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>

					</logic:present> 
					<logic:notPresent name="corInscricao">

						<logic:empty name="InserirDebitoACobrarActionForm" property="idImovel">
							<html:text property="inscricaoImovelInformada" size="40" maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="InserirDebitoACobrarActionForm" property="idImovel">
							<html:text property="inscricaoImovelInformada" size="40" maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
					</logic:notPresent> <a href="javascript:limparIdImovel();" tabindex="1">
					<img border="0"
						src="<bean:message key='caminho.imagens'/>limparcampo.gif"
						style="cursor: hand;" title="Apagar Im�vel" /> </a></td>	
							
							
							
							
					</logic:present> 
					<logic:notPresent name="travarImovel">
					
					<html:text property="idImovel" maxlength="9" size="9" disabled="true"
						onfocus="javascript:bloquearOrdemServicoRa();"
						onkeyup="javascript:bloquearOrdemServicoRa();"
						onkeypress="validaEnterComMensagem(event, 'exibirInserirDebitoACobrarAction.do?objetoConsulta=4', 
							'idImovel','Im�vel');" />
							
						 <img width="23" height="21"
						src="<bean:message key='caminho.imagens'/>pesquisa.gif" border="0"
						title="Pesquisar Im�vel" />
						
					<logic:present name="corInscricao">

						<logic:equal name="corInscricao" value="exception">
							<html:text property="inscricaoImovelInformada" size="40" maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>

						<logic:notEqual name="corInscricao" value="exception">
							<html:text property="inscricaoImovelInformada" size="40" maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>

					</logic:present> 
					<logic:notPresent name="corInscricao">

						<logic:empty name="InserirDebitoACobrarActionForm" property="idImovel">
							<html:text property="inscricaoImovelInformada" size="40" maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="InserirDebitoACobrarActionForm" property="idImovel">
							<html:text property="inscricaoImovelInformada" size="40" maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>
					</logic:notPresent> <a href="javascript:limparIdImovel();" tabindex="1">
					<img border="0"
						src="<bean:message key='caminho.imagens'/>limparcampo.gif"
						style="cursor: hand;" title="Apagar Im�vel" /> </a></td>	
							
							
							
							
							
							
							
							
							
							
					</logic:notPresent>
					
				</tr>
				</logic:present>
				<tr>
					<td colspan="4">
					<table width="100%" align="center" bgcolor="#99CCFF" border="0">
						<tr>
							<td><strong>Dados do Im�vel:</strong></td>
						</tr>
						<tr bgcolor="#cbe5fe">
							<td width="100%" align="center">
							<table width="100%" border="0">
								<tr>
									<td height="10"><strong>Matr�cula:</strong></td>
									<td><html:text property="codigoImovel" size="45"
										readonly="true" style="background-color:#EFEFEF; border:0" />
									</td>
								</tr>
								<tr>
									<td height="10"><strong>Inscri��o:</strong></td>
									<td><html:text property="inscricaoImovel" size="45"
										readonly="true" style="background-color:#EFEFEF; border:0" />
									</td>
								</tr>

								<tr>
									<td height="10"><strong>Nome do Cliente Usu�rio:</strong></td>
									<td><html:text property="nomeCliente" size="45" readonly="true"
										style="background-color:#EFEFEF; border:0" /></td>
								</tr>
								<tr>
									<td height="10"><strong>Situa��o de �gua:</strong></td>
									<td><html:text property="situacaoAgua" size="45"
										readonly="true" style="background-color:#EFEFEF; border:0" />
									</td>
								</tr>
								<tr>
									<td height="10"><strong>Situa��o de Esgoto:</strong></td>
									<td><html:text property="situacaoEsgoto" size="45"
										readonly="true" style="background-color:#EFEFEF; border:0" />
									</td>
								</tr>
							</table>
							</td>
						</tr>

					</table>
					</td>
				</tr>
				<tr>
					<td colspan="4">
					<hr>
					</td>
				</tr>

				<tr>
					<td width="180"><strong>Tipo de D�bito:</strong><font
						color="#FF0000">*</font></td>
					<td colspan="3" align="right">
					<div align="left"><logic:present name="travarDebitoTipo">
						<html:text property="idTipoDebito" size="9"
							onkeypress="validaEnter(event, 'exibirInserirDebitoACobrarAction.do?objetoConsulta=1', 'idTipoDebito');"
							maxlength="9" tabindex="4" onkeyup="limparCalculoPrestacao();" />
					</logic:present> <logic:notPresent name="travarDebitoTipo">
						<html:text property="idTipoDebito" size="9" disabled="true"
							onkeypress="validaEnter(event, 'exibirInserirDebitoACobrarAction.do?objetoConsulta=1', 'idTipoDebito');"
							maxlength="9" tabindex="4" onkeyup="limparCalculoPrestacao();" />
					</logic:notPresent> <a href="javascript:pesquisarTipoDebito();"> <img
						width="23" height="21" border="0"
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						title="Pesquisar Tipo de D�bito" /></a> <logic:present
						name="corDebitoTipo">
						<logic:equal name="corDebitoTipo" value="exception">
							<html:text property="descricaoTipoDebito" value=""
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000"
								size="40" maxlength="45" />

						</logic:equal>
						<logic:notEqual name="corDebitoTipo" value="exception">
							<html:text property="descricaoTipoDebito" size="40"
								maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />

						</logic:notEqual>
					</logic:present> <logic:notPresent name="corDebitoTipo">
						<logic:empty name="InserirDebitoACobrarActionForm"
							property="idTipoDebito">
							<html:text property="descricaoTipoDebito" size="40"
								maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />

						</logic:empty>
						<logic:notEmpty name="InserirDebitoACobrarActionForm"
							property="idTipoDebito">
							<html:text property="descricaoTipoDebito" size="40"
								maxlength="40" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />

						</logic:notEmpty>
					</logic:notPresent> <a href="javascript:limparTipoDebito();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar Tipo de D�bito" /> </a></div>
					</td>
				</tr>
				<tr>
					<td><strong>Valor Total do Servi&ccedil;o:<strong><font
						color="#FF0000">*</font></strong></strong></td>
					<td colspan="3" align="right">
					<div align="left">
					<logic:present name="travarValorServico">					
					<html:text property="valorTotalServico"
						 maxlength="14" size="14" tabindex="5" 	style="text-align: right;background-color:#EFEFEF; border:0"
						  readonly="true"/>					
					</logic:present>
					<logic:notPresent name="travarValorServico">
					<html:text property="valorTotalServico"
						style="text-align: right;"
						onkeyup="formataValorMonetario(this, 11);limparCalculoPrestacao();"
						maxlength="14" size="14" tabindex="5" />						
					</logic:notPresent>
					
					</div>
					<div align="left"><strong></strong></div>
					<div align="left"></div>
					</td>
				</tr>
				<tr>
					<td><strong>Percentual de Abatimento:</strong></td>
					<td align="right" colspan="3">
					<div align="left"></div>
					<div align="left"><strong></strong></div>
					<div align="left"><html:text property="percentualAbatimento"
						style="text-align: right;" maxlength="6" size="6" tabindex="6"
						onkeyup="formataValorMonetario(this, 6);limparCalculoPrestacao();" /></div>
					</td>
				</tr>
				<tr>
					<td><strong>Valor da Entrada:</strong></td>
					<td colspan="3" align="right">
					<div align="left"><html:text property="valorEntrada" maxlength="14"
						style="text-align: right;"
						onkeyup="formataValorMonetario(this, 11);limparCalculoPrestacao();"
						size="14" tabindex="7" /></div>
					<div align="left"><strong></strong></div>
					<div align="left"></div>
					</td>
				</tr>
				<tr>
					<td><strong>N&uacute;mero de Presta&ccedil;&otilde;es:<strong><font
						color="#FF0000">*</font></strong></strong></td>
					<td colspan="3" align="right">
					<div align="left"><html:text property="numeroPrestacoes"
						maxlength="6" size="10" onkeyup="limparCalculoPrestacao();"
						tabindex="8" /></div>
					<div align="left"><strong></strong></div>
					<div align="left"></div>
					</td>
				</tr>
				<tr>
					<td><strong>In&iacute;cio da Cobran&ccedil;a:<strong><font
						color="#FF0000">*</font></strong></strong></td>
					<td colspan="3" align="right">
					<div align="left"><html:text property="anoMesCobrancaDebito"
						maxlength="7" size="10" tabindex="8"
						onkeyup="javascript:mascaraAnoMes(this,event);"/>&nbsp;<strong>mm/aaaa</strong></div>
					<div align="left"><strong></strong></div>
					<div align="left"></div>
					</td>
				</tr>
				<tr>
					<td><strong>Taxa de Juros do Financiamento:</strong></td>
					<td colspan="2" align="right">
					<div align="left"><html:text property="taxaJurosFinanciamento"
						style="text-align: right;" maxlength="6" tabindex="9"
						onkeyup="formataValorMonetario(this, 6);limparCalculoPrestacao();"
						size="6" /></div>
					<td align="right"><input type="button" name="calcularPrestacao"
						class="bottonRightCol" value="Calcular Presta&ccedil;&atilde;o"
						onclick="validarForm(document.forms[0], false);" tabindex="10"></td>
				</tr>
				<tr>
					<td><strong>Prazo de Suspens�o de Lan�amento:<strong></strong></strong></td>
					<td colspan="3" align="right">
					<div align="left"><html:text property="numerosDiasSuspensao"
						onkeypress="return isCampoNumerico(event);" maxlength="4"
						size="5"
						/></div>
					<div align="left"><strong></strong></div>
					<div align="left"></div>
					</td>
				</tr>
				<tr>
					<td colspan="4">
					<hr>
					</td>
				</tr>
				<tr>
					<td><strong>Valor dos Juros:</strong></td>
					<td colspan="3" align="right">
					<div align="left"></div>
					<div align="left"><strong></strong></div>
					<div align="left"><html:text property="valorJuros" maxlength="20"
						style="text-align: right;background-color:#EFEFEF; border:0"
						onkeyup="formataValorMonetario(this, 11);" size="20"
						readonly="true" /></div>
					</td>
				</tr>
				<tr>
					<td><strong>Valor Total do Servi&ccedil;o a Parcelar:</strong></td>
					<td colspan="3" align="right">
					<div align="left"><html:text property="valorTotalServicoAParcelar"
						onkeyup="formataValorMonetario(this, 11);" maxlength="20"
						style="text-align: right;background-color:#EFEFEF; border:0"
						size="20" readonly="true" /></div>
					<div align="left"><strong></strong></div>
					<div align="left"></div>
					</td>
				</tr>
				<tr>
					<td><strong>Valor da Presta&ccedil;&atilde;o:<strong></strong></strong></td>
					<td colspan="3" align="right">
					<div align="left"><html:text property="valorPrestacao"
						onkeyup="formataValorMonetario(this, 11);" maxlength="20"
						size="20"
						style="text-align: right;background-color:#EFEFEF; border:0"
						readonly="true" /></div>
					<div align="left"><strong></strong></div>
					<div align="left"></div>
					</td>
				</tr>
				
				<tr>
					<td>&nbsp;</td>
					<td colspan="3" align="right">
					<div align="left"></div>
					<div align="left"><strong></strong></div>
					<div align="left"><font color="#FF0000">*</font> Campo
					Obrigat&oacute;rio</div>
					</td>
				</tr>
				<tr>
					<td colspan="4">
					<hr>
					</td>
				</tr>


				<tr>
					<td colspan="3"><input name="Button" type="button"
						class="bottonRightCol" value="Desfazer" align="left"
						onclick="limparForm();"> <input name="Button" type="button"
						class="bottonRightCol" value="Cancelar" align="left"
						onclick="javascript:window.location.href='/gsan/telaPrincipal.do'">
					</td>
					<td align="right"><gcom:controleAcessoBotao name="Button"
						value="Inserir"
						onclick="javascript:validarForm(document.forms[0], true);"
						url="inserirDebitoACobrarAction.do" /> <!--
						<input name="inserir" type="button"
							class="bottonRightCol" value="Inserir" align="right"
							onClick="validarForm(document.forms[0], true);" tabindex="11"> -->
					</td>
				</tr>

				<tr>
					<td>&nbsp;</td>
					<td colspan="3" align="right">&nbsp;</td>
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
