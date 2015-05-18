
<%@page import="gcom.util.ConstantesSistema"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page import="gcom.atendimentopublico.registroatendimento.RegistroAtendimento" %>
<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro"%>

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
	src="<bean:message key="caminho.js"/>Calendario.js"></script>

<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="AtualizarRegistroAtendimentoActionForm"
	dynamicJavascript="false" />

<SCRIPT LANGUAGE="JavaScript">
<!--

	var bCancel = false; 

	//  Função que faz a validação normal dos forms e faz a validação especial
	//pra o campo senhaAtendimento, que é dependente do meioSolicitacao.
    function validateAtualizarRegistroAtendimentoActionForm(form) {                                                                   
    	var retorno = false;                                                                    
        if (bCancel){
      		retorno = true;
      	} else {
      		var form = document.forms[0];
      		if (validateCaracterEspecial(form) && validateRequired(form) && validateDate(form) && validateInteger(form)){
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
     this.aa = new Array("dataAtendimento", "Data do Atendimento possui caracteres especiais.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
     this.ab = new Array("hora", "Hora possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ac = new Array("tempoEsperaInicial", "Tempo de Espera Inicial possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ad = new Array("tempoEsperaFinal", "Tempo de Espera Final possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     this.ae = new Array("unidade", "Unidade de Atendimento possui caracteres especiais.", new Function ("varName", " return this[varName];"));
     //this.af = new Array("observacao", "Observação possui caracteres especiais.", new Function ("varName", " return this[varName];"));
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

    function IntegerValidations () { 
     this.aa = new Array("unidade", "Unidade de Atendimento deve ser numérico(a).", new Function ("varName", " return this[varName];"));
    }


	function verificarDataAtendimento(){
		
		var form = document.forms[0];
		
		if (form.especificacao.value > 0){
			if (form.dataAtendimento.value.length < 1){
				alert("Informe Data do Atendimento");
				form.dataAtendimento.focus();
			}
			else if (validateDate(form)){
				redirecionarSubmit('atualizarRegistroAtendimentoWizardAction.do?action=exibirAtualizarRegistroAtendimentoDadosGeraisAction&pesquisarEspecificacao=OK&definirDataPrevista=OK');
			}
		}
	}
	
	function limpar(situacao){
	
		var form = document.forms[0];
	
		switch (situacao){
	       case 1:
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
	    }else if (tipoConsulta == 'unidadeOrganizacional') {
		      
	      form.unidade.value = codigoRegistro;
	      form.descricaoUnidade.value = descricaoRegistro;
		  form.descricaoUnidade.style.color = '#000000';
	    }
	}
	
	function carregarTempoEsperaFinal(){
		
		var form = document.forms[0];
		
		if (form.tempoEsperaInicial.value.length > 0 && form.tempoEsperaFinal.readOnly == true){
			redirecionarSubmit('atualizarRegistroAtendimentoWizardAction.do?action=exibirAtualizarRegistroAtendimentoDadosGeraisAction&tempoEsperaFinalDesabilitado=OK');
		}
	}
	
	function carregarEspecificacao(){
		
		var form = document.forms[0];
		
		if (form.tipoSolicitacao.value > 0){
			redirecionarSubmit('atualizarRegistroAtendimentoWizardAction.do?action=exibirAtualizarRegistroAtendimentoDadosGeraisAction&pesquisarEspecificacao=OK');
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
			form.indicadorProcessoAdmJud[0].disabled = true;
			form.indicadorProcessoAdmJud[1].disabled = true;
			form.numeroProcessoAgencia.disabled = true;
		}else if(indicadorProcessoAdmJud == <%=ConstantesSistema.SIM.toString()%>){
			form.indicadorProcessoAdmJud[0].checked = true;
		}else{
			form.indicadorProcessoAdmJud[1].checked = true;
		}
	}
	
	function habilitaCampoNumeroProcessoAgencia(){

		var form = document.forms[0];
		var permissaoAlterarProcessoAdmJud = "${sessionScope.permissaoAlterarProcessoAdmJud}";
		
		if(permissaoAlterarProcessoAdmJud == <%=ConstantesSistema.NAO.toString()%>){
			form.numeroProcessoAgencia.disabled = true;
		}else{
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
	}

	   
	function consultarServicosAssociados(){
			
			var form = document.forms[0];
			var especificacao = form.especificacao.value;
			
			if (!isNaN(especificacao) && especificacao.length > 0 && especificacao.indexOf(',') == -1 &&
					especificacao.indexOf('.') == -1 && ((especificacao * 1) > 0)){
				
				abrirPopup("/gsan/exibirConsultarServicoAssociadoAction.do?idSolicitacaoTipoEspecificacao=" + especificacao, 400, 690);
			}
		
	}
	
	
//-->
</SCRIPT>


</head>

<body leftmargin="5" topmargin="5"
	onload="setarFoco('${requestScope.nomeCampo}'); limitTextArea(document.forms[0].observacao, 400, document.getElementById('utilizado'), document.getElementById('limite'));  habilitarDesabilitarSenhaAtendimento();verificarIndicadorProcessoAdmJud('${sessionScope.indicadorProcessoAdmJud}','${sessionScope.permissaoAlterarProcessoAdmJud}');habilitaCampoNumeroProcessoAgencia();">


<html:form action="/atualizarRegistroAtendimentoWizardAction"
	method="post">

	<html:hidden property="idImovel"/>

	<html:hidden property="idMunicipio"/>
	<html:hidden property="cdBairro"/>
	<html:hidden property="idBairroArea"/>
	<html:hidden name="sistemaParametro" property="parmId"/>

	<jsp:include
		page="/jsp/util/wizard/navegacao_abas_wizard_valida_avancar.jsp?numeroPagina=1" />


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
					<td class="parabg">Atualizar RA - Registro de Atendimento</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
			</table>
			<p>&nbsp;</p>

			<table width="100%" border="0">
				<tr>
					<td colspan="2">Para atualizar o RA - Registro de Atendimento, informe
					os dados gerais abaixo:</td>
				</tr>
				<tr>
					<td HEIGHT="30" WIDTH="80"><strong>Numero do RA:<font
						color="#FF0000">*</font></strong></td>
					<td><html:text property="numeroRA" size="11" maxlength="10"
						readonly="true" /></td>
				</tr>
				<tr>
					<td HEIGHT="30" WIDTH="80"><strong>Tipo:<font color="#FF0000">*</font></strong></td>
					<td><html:radio property="tipo" value="1" tabindex="1"
						disabled="true" /><strong>on-line <html:radio property="tipo"
						value="2" tabindex="2" disabled="true" />manual</strong></td>
				</tr>
				<tr>
		      		<td HEIGHT="30"><strong>Número Manual:</strong></td>
		        	<td>
						<html:text property="numeroAtendimentoManual" size="12" maxlength="11" readonly="true"/>
					</td>
		        </tr>
				<tr>
					<td HEIGHT="30"><strong>Data do Atendimento:<font color="#FF0000">*</font></strong></td>
					<td><logic:equal name="AtualizarRegistroAtendimentoActionForm"
						property="tipo" value="1">
						<html:text property="dataAtendimento" size="11" maxlength="10"
							tabindex="3" onkeyup="mascaraData(this, event)" readonly="true" />
						<img border="0"
							src="<bean:message key='caminho.imagens'/>calendario.gif"
							width="20" border="0" alt="Exibir Calendário" tabindex="4" />
					</logic:equal> <logic:notEqual
						name="AtualizarRegistroAtendimentoActionForm" property="tipo"
						value="1">
						<html:text property="dataAtendimento" size="11" maxlength="10"
							tabindex="3" onkeyup="mascaraData(this, event)" />
						<a
							href="javascript:abrirCalendario('AtualizarRegistroAtendimentoActionForm', 'dataAtendimento');">
						<img border="0"
							src="<bean:message key='caminho.imagens'/>calendario.gif"
							width="20" border="0" alt="Exibir Calendário" tabindex="4" /></a>
					</logic:notEqual> <strong>&nbsp;(dd/mm/aaaa)</strong></td>
				</tr>
				<tr>
					<td HEIGHT="30"><strong>Hora:<font color="#FF0000">*</font></strong></td>
					<td><logic:equal name="AtualizarRegistroAtendimentoActionForm"
						property="tipo" value="1">
						<html:text property="hora" size="10" maxlength="5" tabindex="5"
							onkeyup="mascaraHora(this, event)" readonly="true" />
					</logic:equal> <logic:notEqual
						name="AtualizarRegistroAtendimentoActionForm" property="tipo"
						value="1">
						<html:text property="hora" size="10" maxlength="5" tabindex="5"
							onkeyup="mascaraHora(this, event)" />
					</logic:notEqual> <strong>&nbsp;(hh:mm)</strong></td>
				</tr>
				<tr>
					<td HEIGHT="30"><strong>Tempo de Espera:</strong></td>
					<td><html:text property="tempoEsperaInicial" size="10"
						maxlength="5" tabindex="6"
						onkeyup="if (mascaraHora(this, event)){carregarTempoEsperaFinal();}" />
					<strong>&nbsp;(hh:mm)</strong> &nbsp;&nbsp;&nbsp; 
					<logic:equal name="AtualizarRegistroAtendimentoActionForm" property="tipo" value="1">
						<html:text property="tempoEsperaFinal" size="10" maxlength="5"
							tabindex="7" onkeyup="mascaraHora(this, event)" readonly="true" />
					</logic:equal>
					<logic:notEqual name="AtualizarRegistroAtendimentoActionForm" property="tipo" value="1">
						<html:text property="tempoEsperaFinal" size="10" maxlength="5"
							tabindex="7" onkeyup="mascaraHora(this, event)" />
					</logic:notEqual> <strong>&nbsp;(hh:mm)</strong></td>
				</tr>
				<tr>
					<td HEIGHT="30"><strong>Unidade de Atendimento:<font
						color="#FF0000">*</font></strong></td>
					<td><html:text property="unidade" size="6" maxlength="8"
						tabindex="8"
						onkeypress="validaEnterComMensagem(event, 'atualizarRegistroAtendimentoWizardAction.do?action=exibirAtualizarRegistroAtendimentoDadosGeraisAction&pesquisarUnidade=OK', 'unidade', 'Unidade');"
						onkeyup="document.forms[0].descricaoUnidade.value = '';" /> <a
						href="javascript:chamarPopup('exibirPesquisarUnidadeOrganizacionalAction.do', 'unidadeOrganizacional', null, null, 275, 480, '',document.forms[0].unidade);";">
					<img src="<bean:message key='caminho.imagens'/>pesquisa.gif"
						width="23" height="21" alt="Pesquisar" border="0"></a> <logic:present
						name="corUnidade">

						<logic:equal name="corUnidade" value="exception">
							<html:text property="descricaoUnidade" size="45" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:equal>

						<logic:notEqual name="corUnidade" value="exception">
							<html:text property="descricaoUnidade" size="45" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEqual>

					</logic:present> <logic:notPresent name="corUnidade">

						<logic:empty name="AtualizarRegistroAtendimentoActionForm"
							property="unidade">
							<html:text property="descricaoUnidade" value="" size="45"
								readonly="true"
								style="background-color:#EFEFEF; border:0; color: #ff0000" />
						</logic:empty>
						<logic:notEmpty name="AtualizarRegistroAtendimentoActionForm"
							property="unidade">
							<html:text property="descricaoUnidade" size="45" readonly="true"
								style="background-color:#EFEFEF; border:0; color: #000000" />
						</logic:notEmpty>


					</logic:notPresent> <a href="javascript:limpar(1);"> <img
						src="<bean:message key='caminho.imagens'/>limparcampo.gif"
						alt="Apagar" border="0"></a></td>
				</tr>
				<tr>
					<td HEIGHT="30"><strong>Meio de Solicitação:<font color="#FF0000">*</font></strong></td>
					<td><html:select property="meioSolicitacao" style="width: 200px;font-size:11px;"
						onchange="javascript:habilitarDesabilitarSenhaAtendimento();" tabindex="9">
						<html:option value="">&nbsp;</html:option>
						<html:options collection="colecaoMeioSolicitacao"
							labelProperty="descricao" property="id" />
					</html:select></td>
				</tr>
				<logic:equal name="sistemaParametro" property="parmId" scope="session" value="<%=SistemaParametro.INDICADOR_EMPRESA_ADA.toString()%>">
				<tr>
					<td HEIGHT="30"><strong>Senha de Atendimento:</strong></td>
					<td><html:text property="senhaAtendimento" size="5" maxlength="5"
						tabindex="10" readonly="true"/>
					</td>
				</tr>
				</logic:equal>
				<tr>
					<td HEIGHT="30"><strong>Tipo de Solicitação:<font color="#FF0000">*</font></strong></td>
					<td><logic:empty name="AtualizarRegistroAtendimentoActionForm"
						property="idOrdemServico">

						<html:select property="tipoSolicitacao" style="width: 350px;font-size:11px;"
							tabindex="11" onchange="carregarEspecificacao();">
							<html:option value="">&nbsp;</html:option>
							<html:options collection="colecaoSolicitacaoTipo"
								labelProperty="descricao" property="id" />
						</html:select>

					</logic:empty> <logic:notEmpty
						name="AtualizarRegistroAtendimentoActionForm"
						property="idOrdemServico">
						<html:select property="tipoSolicitacao" style="width: 350px;font-size:11px;"
							tabindex="11" disabled="true">
							<html:option value="">&nbsp;</html:option>
							<html:options collection="colecaoSolicitacaoTipo"
								labelProperty="descricao" property="id" />
						</html:select>
					</logic:notEmpty></td>
				</tr>
				<tr>
					<td HEIGHT="30"><strong>Especificação:<font color="#FF0000">*</font></strong></td>
					<td><logic:present name="colecaoSolicitacaoTipoEspecificacao">
						<logic:empty name="AtualizarRegistroAtendimentoActionForm"
							property="idOrdemServico">
							<html:select property="especificacao" style="width: 350px;font-size:11px;"
								tabindex="12" onchange="verificarDataAtendimento()">
								<html:option value="">&nbsp;</html:option>
								<html:options collection="colecaoSolicitacaoTipoEspecificacao"
									labelProperty="descricao" property="id" />
							</html:select>
						</logic:empty>
						<logic:notEmpty name="AtualizarRegistroAtendimentoActionForm"
							property="idOrdemServico">
							<html:select property="especificacao" style="width: 350px;font-size:11px;"
								tabindex="12" disabled="true">
								<html:option value="">&nbsp;</html:option>
								<html:options collection="colecaoSolicitacaoTipoEspecificacao"
									labelProperty="descricao" property="id" />
							</html:select>
						</logic:notEmpty>
					</logic:present> <logic:notPresent
						name="colecaoSolicitacaoTipoEspecificacao">
						<html:select property="especificacao" style="width: 350px;"
							tabindex="12" disabled="true">
							<html:option value="">&nbsp;</html:option>
						</html:select>
					</logic:notPresent></td>
					
					
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
					<td HEIGHT="30"><strong>Data Prevista:</strong></td>
					<td><html:text property="dataPrevista" size="16" maxlength="16"
						tabindex="13" onkeyup="mascaraData(this, event)" readonly="true" style="background-color:#EFEFEF; border:0; color: #000000"/>
					</td>
				</tr>
				<tr>
					<td HEIGHT="30"><strong>Observação do R.A:</strong></td>
					<td><html:textarea property="observacao" cols="40" rows="5" onkeyup="limitTextArea(document.forms[0].observacao, 400, document.getElementById('utilizado'), document.getElementById('limite'));"/><br>
					<strong><span id="utilizado">0</span>/<span id="limite">400</span></strong></td>
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
					<div align="right"><jsp:include
						page="/jsp/util/wizard/navegacao_botoes_wizard_valida_avancar_ra.jsp?numeroPagina=1" />
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

</body>
<!-- registro_atendimento_atualizar_dados_gerais.jsp -->
</html:html>

