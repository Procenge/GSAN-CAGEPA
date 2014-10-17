<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ taglib uri="/WEB-INF/gcomLib.tld" prefix="gcom" %>

<%@ page
	import="gcom.util.Pagina,gcom.util.ConstantesSistema,java.util.Collection, java.util.Map"%>
	
<%@ page
	import="gcom.arrecadacao.debitoautomatico.DebitoAutomaticoMovimento"%>

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
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="GerarMovimentoDebitoAutomaticoBancoActionForm"/>
<script>
<!--

var bCancel = false; 

   function validateGerarMovimentoDebitoAutomaticoBancoActionForm(form) {
	
	 var retorno = false;
	  
     if (bCancel) {
     	retorno =  true; 
     }else 
     {
    	retorno = validateCaracterEspecial(form);
    	 
    	if (form.habilitarCampoGrupoFaturamento){
    		retorno =  validateMesAno(form);
     	}
  	 }
     
     return retorno;
  }
  
  function caracteresespeciais () { 
    this.aa = new Array("mesAnoFaturamento", "Mês/ano de Faturamento possui caracteres especiais.", new Function ("varName", " return this[varName];"));
  }

   function MesAnoValidations () {
     this.aa = new Array("mesAnoFaturamento", "Mês/ano de Faturamento inválido.", new Function ("varName", " return this[varName];"));
   }
   


function facilitador(objeto){
	if (objeto.value == "0"){
		objeto.value = "1";
		marcarTodos();
	}
	else{
		objeto.value = "0";
		desmarcarTodos();			
	}
	verificaBancos();
}

function recuperarDadosSeisParametros(codigoRegistro, descricaoRegistro1, descricaoRegistro2, descricaoRegistro3, descricaoRegistro4, tipoConsulta) { 
	var form = document.GerarMovimentoDebitoAutomaticoBancoActionForm;
 	    form.idArrecadadorMovimento.value = codigoRegistro;
		form.codigoBancoMovimento.value = descricaoRegistro1;
		form.codigoRemessaMovimento.value = descricaoRegistro2;
		form.identificacaoServicoMovimento.value = descricaoRegistro3;
		form.numeroSequencialMovimento.value = descricaoRegistro4;
		form.opcaoEnvioParaBanco[0].focus();
		habilitaDesabilitaBotaoGerar();
			
}
function limparMovimento() {
	var form = document.GerarMovimentoDebitoAutomaticoBancoActionForm;
		form.idArrecadadorMovimento.value = "";
		form.codigoBancoMovimento.value = "";
		form.codigoRemessaMovimento.value = "";
		form.identificacaoServicoMovimento.value = "";
		form.numeroSequencialMovimento.value = "";
		habilitaDesabilitaBotaoGerar();
}

function getRadio(tipo) {
	var form = document.GerarMovimentoDebitoAutomaticoBancoActionForm;
	var radio;
	
	for(i = 0; i < 3; i++) {
		if(form.opcaoMovimentoDebitoAutomatico[i] != null){
			if(form.opcaoMovimentoDebitoAutomatico[i].value == tipo) {
				radio = form.opcaoMovimentoDebitoAutomatico[i];
				break;
			}
		}		
	}	
	
	return radio;
}

function isRadioChecked(tipo) {
	var radio = getRadio(tipo);
	
	if(radio == null) {
		return false;
	} else {
		return radio.checked;
	}
}

function chamaPopup(){
var form = document.GerarMovimentoDebitoAutomaticoBancoActionForm;
 if(isRadioChecked('regerar')){
  abrirPopup('exibirPesquisarMovimentoArrecadadorAction.do?menu=sim', 275, 665);
 }
}



function habilitaDesabilitaBotaoGerar(){
var form = document.GerarMovimentoDebitoAutomaticoBancoActionForm;
 if(isRadioChecked('CNTA') || isRadioChecked('GPAG')){
	//verifica se tem algum banco da coleção checado
  	var achou = false;
    if(form.idsCodigosBancos != null){
      if(form.idsCodigosBancos.length != null){
        for (i=0; i < form.idsCodigosBancos.length; i++){
  		  if (form.idsCodigosBancos[i].checked == true){
  			achou = true;
  			break;
  		  }
  	    }
  	    if(achou){
  	      form.botaoGerar.disabled = false;
  	    }else{
  	      form.botaoGerar.disabled = true;
  	    }
      }else{
        if (form.idsCodigosBancos.checked){
          form.botaoGerar.disabled = false;
        }else{
          form.botaoGerar.disabled = true;
        }
      }
    } else {
       form.botaoGerar.disabled = true;
    }
 }else{
     if(isRadioChecked('regerar')){
      //verifica se foi escolhido o movimento e se é para enviar ou não para o banco
      if((form.codigoBancoMovimento.value != "") && (form.opcaoEnvioParaBanco[0].checked || form.opcaoEnvioParaBanco[1].checked)){
        form.botaoGerar.disabled = false;
      }else{
        form.botaoGerar.disabled = true;
      }
      //caso não for escolhido nenhum dos das opções de debito movimento
      //então desabilita o botão
     }else{
      form.botaoGerar.disabled = true;
  
     }
  }
}


function habilitaDesabilitaCampos(){
	var form = document.GerarMovimentoDebitoAutomaticoBancoActionForm;
	var grupoFaturamentoOk = false;
	var mesAnoFaturamentoOk = false;
	
 	if(isRadioChecked('CNTA')){
	  limparMovimento();
	  form.botaoListaBancos.disabled = true;
	  form.opcaoEnvioParaBanco[0].checked = false;
	  form.opcaoEnvioParaBanco[1].checked = false;
	  form.opcaoEnvioParaBanco[0].disabled = true;
	  form.opcaoEnvioParaBanco[1].disabled = true;
	  
	  if(form.idGrupoFaturamento != null){
		  
		  if(form.idGrupoFaturamentoSelecionados.length > 0) {
			  grupoFaturamentoOk = true;
		  }
		  
		  form.idGrupoFaturamento.disabled=false;
		  form.idGrupoFaturamentoSelecionados.disabled = false;
		  form.btMoverTodosDadosSelectMenu1PARAMenu2.disabled = false;
		  form.btMoverDadosSelectMenu1PARAMenu2.disabled = false;
		  form.btMoverDadosSelectMenu2PARAMenu1.disabled = false;
		  form.btMoverTodosDadosSelectMenu2PARAMenu1.disabled = false;
		  form.idGrupoFaturamento.focus();
	  } else {
		  grupoFaturamentoOk = true;
	  }
	  
	  if(form.mesAnoFaturamento != null){
		  form.mesAnoFaturamento.disabled=false;
		  
		  if(form.mesAnoFaturamento.value != "") {
			  mesAnoFaturamentoOk = true;  
		  }
	  } else {
		  mesAnoFaturamentoOk = true;  
	  }
	  
	  if(grupoFaturamentoOk && mesAnoFaturamentoOk) {
		  form.botaoListaBancos.disabled = false;
	  }
	  
	  habilitaDesabilitaBotaoGerar();
	  
 	}else if(isRadioChecked('GPAG')){
 		 limparMovimento();		 
		 if(form.idGrupoFaturamento != null){
			 form.idGrupoFaturamento.value="";
			 form.idGrupoFaturamento.disabled=true;
			 form.idGrupoFaturamentoSelecionados.value = "";
			 form.idGrupoFaturamentoSelecionados.disabled = true;
			 form.btMoverTodosDadosSelectMenu1PARAMenu2.disabled = true;
			 form.btMoverDadosSelectMenu1PARAMenu2.disabled = true;
			 form.btMoverDadosSelectMenu2PARAMenu1.disabled = true;
			 form.btMoverTodosDadosSelectMenu2PARAMenu1.disabled = true;
		 }
		 if(form.mesAnoFaturamento != null){
			 form.mesAnoFaturamento.value="";
			 form.mesAnoFaturamento.disabled=true;
		 }
		 
		 form.botaoListaBancos.disabled = false;
		 
		 habilitaDesabilitaBotaoGerar();
		 
 	}else{ 
 		if(isRadioChecked('regerar')){
		  form.opcaoEnvioParaBanco[0].disabled = false;
		  form.opcaoEnvioParaBanco[1].disabled = false;
  		}else{
		  form.opcaoEnvioParaBanco[0].disabled = true;
		  form.opcaoEnvioParaBanco[1].disabled = true;
 		}
 		
	    form.botaoListaBancos.disabled = true;
 		
 	    if(form.idGrupoFaturamento != null){
		  form.idGrupoFaturamento.disabled=true;
		  form.idGrupoFaturamentoSelecionados.disabled=true;
		  form.btMoverTodosDadosSelectMenu1PARAMenu2.disabled = true;
		  form.btMoverDadosSelectMenu1PARAMenu2.disabled = true;
		  form.btMoverDadosSelectMenu2PARAMenu1.disabled = true;
		  form.btMoverTodosDadosSelectMenu2PARAMenu1.disabled = true;
 	    }
 	    
 	    if(form.mesAnoFaturamento != null) {
		  form.mesAnoFaturamento.disabled=true;
		  form.mesAnoFaturamento.value="";
 	    }
 	}
  //desabilitaBotaoListaBanco();
}

function limparColecaoGrupoFaturamento(){
	var form = document.GerarMovimentoDebitoAutomaticoBancoActionForm;
	form.action = 'exibirGerarMovimentoDebitoAutomaticoBancoAction.do?limpaColecao=1';
	form.submit();
}

function desabilitaBotaoListaBanco(){
	var form = document.GerarMovimentoDebitoAutomaticoBancoActionForm;
	var grupoFaturamentoOk = false;
	var mesAnoFaturamentoOk = false;
 	
	if(form.idGrupoFaturamento != null){
	 	if(form.idGrupoFaturamentoSelecionados.length > 0){
	 		grupoFaturamentoOk = true;
	 	} 
	} else {
 		grupoFaturamentoOk = true;		
	}
	
	if(form.mesAnoFaturamento != null) {
		if(form.mesAnoFaturamento.value != "") {
			mesAnoFaturamentoOk = true;
		}
	} else {
		mesAnoFaturamentoOk = true;
	}
	
	if(grupoFaturamentoOk && mesAnoFaturamentoOk) {
	    form.botaoListaBancos.disabled = false;
	} else {
	    form.botaoListaBancos.disabled = true;		
	}
}

function exibirListaBancos(){
var form = document.GerarMovimentoDebitoAutomaticoBancoActionForm;
var mensagem = '';
	
	if(isRadioChecked('CNTA')){
		if(form.idGrupoFaturamento != null){
			if(form.idGrupoFaturamentoSelecionados.length == 0){
				mensagem = 'Informe Grupo de Faturamento.';
			}
		}
		
		if(form.mesAnoFaturamento != null){
			if(form.mesAnoFaturamento.value == ''){
				if(mensagem != ''){
					mensagem = mensagem + '\n';
				}
				mensagem = mensagem + 'Informe Mês/ano de Faturamento.';
			}
		}
	}
	
	if(mensagem != ''){
		alert(mensagem);
		
	} else if (validateGerarMovimentoDebitoAutomaticoBancoActionForm(form)){

		form.action = 'exibirGerarMovimentoDebitoAutomaticoBancoAction.do?listarBancos=1';
		
		if((form.idGrupoFaturamento != null) && (form.idGrupoFaturamentoSelecionados.length > 0)){
			enviarSelectMultiplo('GerarMovimentoDebitoAutomaticoBancoActionForm','idGrupoFaturamentoSelecionados');
			montarStringDados('GerarMovimentoDebitoAutomaticoBancoActionForm','idGrupoFaturamentoSelecionados');
			stringBuffer = montarStringDados('GerarMovimentoDebitoAutomaticoBancoActionForm','idGrupoFaturamentoSelecionados');
			form.criaColecaoBanco.value = stringBuffer;
		}
		
		form.submit();
	}
}
function desfazer(){
var form = document.GerarMovimentoDebitoAutomaticoBancoActionForm;
//form.reset();
form.action = 'exibirGerarMovimentoDebitoAutomaticoBancoAction.do?menu=sim';
form.submit();

}

function gerar(){
var form = document.GerarMovimentoDebitoAutomaticoBancoActionForm;
	if (validateGerarMovimentoDebitoAutomaticoBancoActionForm(form)){
		form.submit();
	}
}

function montarStringDados(nomeForm, nomeCampoMenu2) {
    
    stringBuffer = "";
    
    var m2 = document.forms[nomeForm].elements[nomeCampoMenu2];

	if (m2.length > 0) {
	
		for (i=0; i<m2.length; i++) {
			
			if (stringBuffer.length > 0){
				stringBuffer = stringBuffer + ":" + m2.options[i].value;
			}
			else{
				stringBuffer = m2.options[i].value;
			}
		}
	}
	
	return  stringBuffer;
}

function verificaBancos(){
	var form = document.GerarMovimentoDebitoAutomaticoBancoActionForm;
	var pCampo = form.idsCodigosBancos;
	
	var numBancos = pCampo.length;
	var bancosSelecionados = false;
	
	if(numBancos != "" && numBancos >0){
		for(i=0; i<numBancos; i++){
			if(pCampo[i].checked == true){
				bancosSelecionados = true;			
			}
		}
	}else{
		if(pCampo.checked == false){
			bancosSelecionados = false;	
		}else{
			bancosSelecionados = true;
		}		
	}	
	if(bancosSelecionados == false){		
		form.botaoGerar.disabled = true;
	}else{
		form.botaoGerar.disabled = false;
	}	
}

-->
</script>

</head>

<body leftmargin="5" topmargin="5" onload="habilitaDesabilitaCampos();habilitaDesabilitaBotaoGerar();">

<html:form action="/gerarMovimentoDebitoAutomaticoBancoAction"
	name="GerarMovimentoDebitoAutomaticoBancoActionForm"
	type="gcom.gui.arrecadacao.GerarMovimentoDebitoAutomaticoBancoActionForm"
	method="post">
	
	<input type="hidden" name="criaColecaoBanco" value=""/>

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
			<td valign="top" bgcolor="#003399" class="centercoltext">
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
					<td class="parabg">Gerar Movimento de Débito Automático</td>

					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
				<tr>
					<td height="5" colspan="3"></td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td colspan="2">
					<p>Para gerar o movimento de débito automático, informe os dados
					abaixo:</p>
					<p>&nbsp;</p>
					</td>
				</tr>
				
				<logic:equal name="GerarMovimentoDebitoAutomaticoBancoActionForm" property="habilitarCampoDebitoAutomaticoPrefeitura" value="true"> 
					<tr>
						<td colspan="2">
							<strong> 
								<html:radio property="opcaoMovimentoDebitoAutomatico" value="<%=DebitoAutomaticoMovimento.GERAR_MOVIMENTO_DE_DEBITO_AUTOMATICO_PREFEITURA %>" onclick="habilitaDesabilitaCampos();limparColecaoGrupoFaturamento();" />
								&nbsp; Gerar Movimento de Débito Automático para Prefeitura
							</strong>
						</td>
					</tr>
				
				</logic:equal> 
				<tr>
					<td colspan="2">
						<strong> 
							<html:radio property="opcaoMovimentoDebitoAutomatico" value="<%=DebitoAutomaticoMovimento.GERAR_MOVIMENTO_DE_DEBITO_AUTOMATICO %>" onclick="habilitaDesabilitaCampos();limparColecaoGrupoFaturamento();" />
							&nbsp; Gerar Movimento de Débito Automático 
						</strong>
					</td>
				</tr>
				
				<logic:equal name="GerarMovimentoDebitoAutomaticoBancoActionForm" property="habilitarCampoGrupoFaturamento" value="true">
					<tr>
						<td width="25%">
							<strong>Grupo de Faturamento:<font color="#FF0000">*</font></strong>
						</td>
						<td width="75%" colspan="2">
						
						<table width="100%" border=0 cellpadding=0 cellspacing=0 align="left">
							<tr>
								<td width="175">
								
									<div align="left"><strong>Disponíveis</strong></div>
	
									<html:select property="idGrupoFaturamento" 
										size="6" 
										multiple="true" 
										style="width:190px">
										
										<logic:present name="colecaoFaturamentoGrupo">
										
											<logic:iterate name="colecaoFaturamentoGrupo" id="faturamentoGrupo" >
												<bean:define name="GerarMovimentoDebitoAutomaticoBancoActionForm" property="idGrupoFaturamento" id="idGF"/>
												<option <%= (idGF.toString().equalsIgnoreCase(((gcom.faturamento.FaturamentoGrupo)faturamentoGrupo).getId() + ";" + ((gcom.faturamento.FaturamentoGrupo)faturamentoGrupo).getAnoMesReferencia()))?" selected ": "" %> value="<bean:write name="faturamentoGrupo" property="id" />;<bean:write name="faturamentoGrupo" property="anoMesReferencia" />" >
													<bean:write name="faturamentoGrupo" property="descricao" />
												</option>
	                        				</logic:iterate>
	                        			</logic:present>
									
									</html:select>
								</td>
	
								<td width="5" valign="center"><br>
									<table width="50" align="center">
										<tr>
											<td align="center">
												<input type="button" id="btMoverTodosDadosSelectMenu1PARAMenu2"
													class="bottonRightCol"
													onclick="javascript:MoverTodosDadosSelectMenu1PARAMenu2('GerarMovimentoDebitoAutomaticoBancoActionForm', 'idGrupoFaturamento', 'idGrupoFaturamentoSelecionados');desabilitaBotaoListaBanco();"
													value=" &gt;&gt; ">
											</td>
										</tr>
		
										<tr>
											<td align="center">
												<input type="button" 
													class="bottonRightCol" id="btMoverDadosSelectMenu1PARAMenu2"
													onclick="javascript:MoverDadosSelectMenu1PARAMenu2('GerarMovimentoDebitoAutomaticoBancoActionForm', 'idGrupoFaturamento', 'idGrupoFaturamentoSelecionados');desabilitaBotaoListaBanco();"
													value=" &nbsp;&gt;  ">
											</td>
										</tr>
		
										<tr>
											<td align="center">
												<input type="button" 
													class="bottonRightCol" id="btMoverDadosSelectMenu2PARAMenu1"
													onclick="javascript:MoverDadosSelectMenu2PARAMenu1('GerarMovimentoDebitoAutomaticoBancoActionForm', 'idGrupoFaturamento', 'idGrupoFaturamentoSelecionados');desabilitaBotaoListaBanco();"
													value=" &nbsp;&lt;  ">
											</td>
										</tr>
		
										<tr>
											<td align="center">
												<input type="button" id="btMoverTodosDadosSelectMenu2PARAMenu1"
													class="bottonRightCol"
													onclick="javascript:MoverTodosDadosSelectMenu2PARAMenu1('GerarMovimentoDebitoAutomaticoBancoActionForm', 'idGrupoFaturamento', 'idGrupoFaturamentoSelecionados');desabilitaBotaoListaBanco();"
													value=" &lt;&lt; ">
											</td>
										</tr>
									</table>
								</td>
	
								<td>
									<div align="left">
										<strong>Selecionados</strong>
									</div>
									
									<html:select property="idGrupoFaturamentoSelecionados" size="6" multiple="true" style="width:190px">
	
										<logic:present name="colecaoFaturamentoGrupoSelecionado">
									
											<logic:iterate name="colecaoFaturamentoGrupoSelecionado" id="faturamentoGrupo" >
												<bean:define name="GerarMovimentoDebitoAutomaticoBancoActionForm" property="idGrupoFaturamentoSelecionados" id="idGFS"/>
												<option <%= (idGFS.toString().equalsIgnoreCase(((gcom.faturamento.FaturamentoGrupo)faturamentoGrupo).getId() + ";" + ((gcom.faturamento.FaturamentoGrupo)faturamentoGrupo).getAnoMesReferencia()))?" selected ": "" %> value="<bean:write name="faturamentoGrupo" property="id" />;<bean:write name="faturamentoGrupo" property="anoMesReferencia" />" >
													<bean:write name="faturamentoGrupo" property="descricao" />
												</option>
											</logic:iterate>
										</logic:present>
									
									</html:select>						
									
								</td>
							</tr>
						</table>
						</td>
					</tr>
				</logic:equal>
				
				<logic:equal name="GerarMovimentoDebitoAutomaticoBancoActionForm" property="habilitarCampoReferencia" value="true">
					<tr>
						<td width="25%"><strong>Mês/ano de Faturamento:<font
							color="#FF0000">*</font></strong></td>
						<td width="74%"><html:text property="mesAnoFaturamento"
							maxlength="7" size="7" tabindex="2"
							onkeyup="mascaraAnoMes(this, event);desabilitaBotaoListaBanco();" />
						mm/aaaa</td>
					</tr>
				</logic:equal>
				
				<logic:equal name="GerarMovimentoDebitoAutomaticoBancoActionForm" property="habilitarCampoGrupoFaturamento" value="true">
				<tr>
					<td colspan="2">
					<div align="right"><input name="botaoListaBancos" type="button"
						class="bottonRightCol" value="Lista de Bancos" tabindex="3"
						onclick="exibirListaBancos();"></div>
					</td>
				</tr>
				</logic:equal>
				
				<logic:notEqual name="GerarMovimentoDebitoAutomaticoBancoActionForm" property="habilitarCampoGrupoFaturamento" value="true">
				<tr>
					<td colspan="2">
					<div align="right"><input name="botaoListaBancos" type="button"
						class="bottonRightCol" value="Lista de Bancos" tabindex="3"
						onclick="exibirListaBancos();" disabled="disabled"></div>
					</td>
				</tr>
				</logic:notEqual>
				<tr>
					<td>&nbsp;</td>
				</tr>

				<tr>
					<td colspan="2">


					<table width="100%" cellpadding="0" cellspacing="0">
						<tr bordercolor="#000000">

							<td width="7%" bgcolor="#90c7fc">
							<div align="center"><strong><a
								href="javascript:facilitador(this);">Todos</a></strong></div>
							</td>
							<td width="12%" align="center" bgcolor="#90c7fc"><strong>C&oacute;d.Banco</strong></td>
							<td width="50%" align="center" bgcolor="#90c7fc"><strong>Nome do Banco</strong></td>
							<td width="30%" align="center" bgcolor="#90c7fc"><strong>Quantidade de Registros</strong></td>
						</tr>
						<logic:present name="debitosAutomaticoBancosMap">
							<tr>
								<td colspan="5" height="100">

								<div style="width: 100%; height: 100%; overflow: auto;">
								<table width="100%" bgcolor="#99CCFF">

									<%int cont = 0;%>
									<logic:iterate name="debitosAutomaticoBancosMap"
										id="debitoAutomaticoBanco">
										<%cont = cont + 1;
							if (cont % 2 == 0) {%>
										<tr bgcolor="#cbe5fe">
											<%} else {
											%>
										<tr bgcolor="#FFFFFF">
											<%}%>
											<td width="7%">
											<div align="center"><input type="checkbox" checked
												name="idsCodigosBancos" onclick="habilitaDesabilitaBotaoGerar();verificaBancos();"
												value="<bean:write name="debitoAutomaticoBanco" property="key.id" />" /></div>
											</td>
											<td width="12%" align="center"><bean:write name="debitoAutomaticoBanco"
												property="key.id" /></td>
											<td width="50%"><bean:write name="debitoAutomaticoBanco"
												property="key.descricao" /></td>
											<td align="center" width="30%"><%=((Collection)((Map.Entry)debitoAutomaticoBanco).getValue()).size()%>
											</td>
											</tr>
									</logic:iterate>
								</table>
								</div>
								</td>
							</tr>
						</logic:present>
					</table>
					</td>
				</tr>
				<tr>
					<td colspan="2">
					<hr>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<strong> 
							<html:radio property="opcaoMovimentoDebitoAutomatico" value="regerar"
								onclick="limparColecaoGrupoFaturamento();" /> 
								&nbsp;Regerar Arquivo TXT do Movimento de Débito Automático
						</strong>
					</td>
				</tr>
				<tr>
					<td width="15%"><strong>Movimento:<font color="#FF0000">*</font></strong></td>
					<td><html:text property="codigoBancoMovimento" size="3"
						maxlength="3" readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000" /> <html:text
						property="codigoRemessaMovimento" size="3" maxlength="3"
						readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000" /> <html:text
						property="identificacaoServicoMovimento" size="30" maxlength="30"
						readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000" /> <html:text
						property="numeroSequencialMovimento" size="9" maxlength="9"
						readonly="true"
						style="background-color:#EFEFEF; border:0; color: #000000" /><strong>
						<html:hidden property = "idArrecadadorMovimento"/>
						<a href="javascript:chamaPopup();"> <img
						src="<bean:message key="caminho.imagens"/>pesquisa.gif"
						border="0" title="Pesquisar Movimento" tabindex="4"/></a>
					    <a
						href="javascript:limparMovimento();"> <img
						src="<bean:message key="caminho.imagens"/>limparcampo.gif"
						border="0" title="Apagar Movimento" /></a> </strong></td>
				</tr>
				<tr>
					<td width="15%"><strong>Enviar para o Banco:<font color="#FF0000">*</font></strong></td>
					<td><strong> <html:radio property="opcaoEnvioParaBanco" value="1"
						onclick="habilitaDesabilitaBotaoGerar();" tabindex="5"/>Sim <html:radio
						property="opcaoEnvioParaBanco" value="2"
						onclick="habilitaDesabilitaBotaoGerar();" tabindex="6"/>N&atilde;o </strong>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td><strong><font color="#FF0000"></font></strong></td>
					<td align="right">
						<div align="left"><strong> <font color="#FF0000">*</font></strong>Campos obrigat&oacute;rios</div>

					</td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td valign="top"><input name="button" type="button"
						class="bottonRightCol" value="Desfazer" onclick="desfazer();">&nbsp;<input type="button"
						name="ButtonCancelar" class="bottonRightCol" value="Cancelar"
						onClick="javascript:window.location.href='/gsan/telaPrincipal.do'"></td>
					<td valign="top">
					  <div align="right">
					     <%--<input name="botaoGerar" type="button" class="bottonRightCol" value="Gerar" onclick="gerar();" tabindex="7">   --%>
					    <gcom:controleAcessoBotao name="botaoGerar" value="Gerar" onclick="javascript:gerar();" url="gerarMovimentoDebitoAutomaticoBancoAction.do" tabindex="7"/>
					  </div>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
	</table>

	<%@ include file="/jsp/util/rodape.jsp"%>
</body>
</html:form>
<!-- gerar_debito_automatico_movimento.jsp -->
</html:html>
