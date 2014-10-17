<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@ page import="gcom.atendimentopublico.ordemservico.ServicoTipo" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">

<%@ page
	import="gcom.atendimentopublico.registroatendimento.AtendimentoMotivoEncerramento"%>
	
<%@ page
	import="gcom.atendimentopublico.ordemservico.OsReferidaRetornoTipo"%>	

<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
	
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>Calendario.js"></script>
	
<script language="JavaScript" src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script><html:javascript staticJavascript="false"  formName="EncerrarOrdemServicoActionForm" dynamicJavascript="false" />

<script language="JavaScript">

var bCancel = false;

    function validateEncerrarOrdemServicoActionForm(form) {
        if (bCancel)
      return true;
        else
      return validateCaracterEspecial(form) && validateRequired(form) && validateDate(form) && encerrar();
   }

    function caracteresespeciais () { 
     this.aa = new Array("dataEncerramento", "Data de Execução possui caracteres especiais.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
     this.ab = new Array("horaEncerramento", "Hora de Execução possui caracteres especiais.", new Function ("varName", " return this[varName];"));
    } 

    function required () { 
     this.aa = new Array("dataEncerramento", "Informe Data de Execução.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
     this.ab = new Array("horaEncerramento", "Informe Hora de Execução.", new Function ("varName", " return this[varName];"));
     this.ac = new Array("idMotivoEncerramento", "Informe Motivo de Encerramento.", new Function ("varName", " return this[varName];"));
    } 

    function DateValidations () { 
     this.aa = new Array("dataEncerramento", "Data de Execução inválida.", new Function ("varName", "this.datePattern='dd/MM/yyyy';  return this[varName];"));
    } 
	
	function extendeTabela(tabela,display){
		var form = document.forms[0];

		if(display){
 			eval('layerHide'+tabela).style.display = 'none';
 			eval('layerShow'+tabela).style.display = 'block';
		}else{
			eval('layerHide'+tabela).style.display = 'block';
 			eval('layerShow'+tabela).style.display = 'none';
		}
	}
	
	function carregarCampos() {
	
		var form = document.forms[0];

		var idServicoTipo = '${idServicoTipo}';
		var processoAutorizacaoServicosAssociados = '${processoAutorizacaoServicosAssociados}';
				
		redirecionarSubmit('exibirEncerrarOrdemServicoPopupAction.do?carregarCampos=OK&processoAutorizacaoServicosAssociados=' + processoAutorizacaoServicosAssociados + '&idServicoTipo=' + idServicoTipo);
	}
	
	function informarAtividade() {
		var form = document.forms[0];
		redirecionarSubmit('exibirEncerrarOrdemServicoPopupAction.do?redirecionarPagina=exibirManterDadosAtividades');
	}


	
	function informarOSFiscalizacao() {
		var form = document.forms[0];
		 redirecionarSubmit('exibirEncerrarOrdemServicoPopupAction.do?redirecionarPagina=exibirGerarOSInserirRA');
	}
	
	function encerrar(){
	 var form = document.forms[0];
	 var mensagem = '';
	 if(form.indicadorExecucao.value != ''){
	   if(form.indicadorExecucao.value == form.indicadorExecucaoSim.value){
		 if(form.tipoServicoReferenciaId.value != ''){
		  if(form.idTipoRetornoReferida.value == ''){
		   if(mensagem == ''){
		     mensagem = 'Informe Tipo Retorno Referida.';
		   }else{
		    mensagem = mensagem + '\nInforme Tipo Retorno Referida.';
		   }
		  }
		  if(form.indicadorDeferimento.value != '' 
		    && form.indicadorDeferimento.value == form.indicadorDeferimentoSim.value){
			  if(form.pavimento != null && form.indicadorPavimento.value != ''){
			    if(form.indicadorPavimento.value == form.indicadorPavimentoSim.value){
				  if(form.pavimento.value == ''){
				    if(mensagem == ''){
		             mensagem = 'Informe Pavimento.';
		            }else{
		             mensagem = mensagem + '\nInforme Pavimento.';
		            }
				  }else{
				   if(!testarCampoValorZero(document.EncerrarOrdemServicoActionForm.pavimento, 'Pavimento')){
				    return false;
				   }
				  }
				}
			 }
		 }
		 if(form.servicoTipoObrigatorio.value != ''){
			if(form.servicoTipoObrigatorio.value == "SIM"){ 
			 if(form.idServicoTipo.value == ''){
			   if(mensagem == ''){
			     mensagem = 'Informe Tipo de Serviço.';
			   }else{
			    mensagem = mensagem +  '\nInforme Tipo de Serviço.';
			   }
			 }}else{
				   if(!testarCampoValorZero(document.EncerrarOrdemServicoActionForm.idServicoTipo, 'Tipo de Serviço')){
				    return false;
				   }
			 }
			} 
		  }
		 }else{
			 if(form.pavimento != null && form.indicadorPavimento.value != ''){
				if(form.indicadorPavimento.value == form.indicadorPavimentoSim.value){
				  if(form.pavimento.value == ''){
				    if(mensagem == ''){
		     		  mensagem = 'Informe Pavimento.';
		   			}else{
		    		  mensagem = mensagem +  '\nInforme Pavimento.';
		   			}
				  }else{
				   if(!testarCampoValorZero(document.EncerrarOrdemServicoActionForm.pavimento, 'Pavimento')){
				    return false;
				   }
				  }
				}
			 }
		 }
	   }
	 if(form.indicadorVistoriaServicoTipo.value == '1'){	 
	  if((!form.codigoRetornoVistoriaOs[0].checked) && (!form.codigoRetornoVistoriaOs[1].checked)){
	     if(mensagem == ''){
		   mensagem = 'Informe Retorno Vistoria.';
		 }else{
		   mensagem = mensagem +  '\nInforme Retorno Vistoria.';
		 }
	  }
	 } 
	 if(mensagem == ''){
	   return true;
	 }else{
	  alert(mensagem);
	 return false;
	 }
	 
	}
	
	function validarForm(form){
     if(validateEncerrarOrdemServicoActionForm(form)){
	   	 if(form.abrePopupDados.value == "S"){
	  		redirecionarSubmit('exibirEncerrarOrdemServicoPopupAction.do?redirecionarPagina=inserirDadosEncerramentoOrdemServico');		
		  }else{
		  	submeterFormPadrao(form);
	  	  }
    }
   }
	
	function limparPesquisaServicoTipo(){
	 var form = document.forms[0];
	 form.idServicoTipo.value = '';
	 form.descricaoServicoTipo.value = '';
	}
	
	function carregarCamposEncerrarComExecucaoComReferencia(){
		
	 var form = document.forms[0];
	 redirecionarSubmit('exibirEncerrarOrdemServicoPopupAction.do?carregarCamposComReferencia=OK');
		
	}
	
	function telaAtencao(mensagem){
	 if(mensagem != ''){
	  alert(mensagem);
	 }
	}
	
	function retornarDadosEncerramento(idServicoAssociado) {
		redirecionarSubmit('encerrarOrdemServicoRetornarDadosAction.do?idServicoTipo=' + idServicoAssociado);
	}
	
</script>
</head>
<logic:present name="fecharPopup">
<body leftmargin="5" topmargin="5"
	onload="javascript:chamarReload('${sessionScope.retornoTela}');window.close();">

</logic:present>
<logic:notPresent name="fecharPopup">
<body leftmargin="5" topmargin="5"
	onload="window.focus();resizePageSemLink(700, 600);javascript:setarFoco('${requestScope.nomeCampo}');javascript:telaAtencao('${requestScope.atencaoIndeferimento}')">

</logic:notPresent>
<html:form action="/encerrarOrdemServicoPopupAction"
	name="EncerrarOrdemServicoActionForm"
	type="gcom.gui.atendimentopublico.ordemservico.EncerrarOrdemServicoActionForm"
	method="post">
	
	<INPUT TYPE="hidden" name="abrePopupDados"	value="${requestScope.abrePopupDados}" />

	<table width="100%" border="0" cellspacing="5" cellpadding="0">

		<tr>

			<td width="100%" valign="top" class="centercoltext">

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
					<td class="parabg">Encerrar Ordem de Serviço</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" />
					</td>
				</tr>
			</table>

			<p>&nbsp;</p>


			<!--Inicio da Tabela Dados Gerais da Ordem de Serviço -->
			<table width="100%" border="0">

				<tr>
					<td height="31" colspan="2">
					<input type="hidden" name="indicadorExecucaoSim"
							value="<%=AtendimentoMotivoEncerramento.INDICADOR_EXECUCAO_SIM%>" /> <input
							type="hidden" name="indicadorExecucaoNao"
							value="<%=AtendimentoMotivoEncerramento.INDICADOR_EXECUCAO_NAO%>" />
							<input
							type="hidden" name="indicadorPavimentoSim"
							value="<%=""+ServicoTipo.INDICADOR_PAVIMENTO_SIM %>"/> 
							<input
							type="hidden" name="indicadorDeferimentoSim"
							value="<%=""+OsReferidaRetornoTipo.INDICADOR_DEFERIMENTO_SIM %>"/> 
							
							<html:hidden property="indicadorExecucao"/> 
							<html:hidden property="indicadorPavimento"/> 
							<html:hidden property="tipoServicoReferenciaId"/> 
							<html:hidden property="indicadorDeferimento"/>
							<html:hidden property="servicoTipoObrigatorio"/>
							<html:hidden property="indicadorVistoriaServicoTipo"/> 
							
							
					<table width="100%" border="0" align="center">

						<tr bgcolor="#cbe5fe">

							<td align="center">
							<table width="100%" border="0" bgcolor="#99CCFF">

								<tr bgcolor="#99CCFF">
									<td height="18" colspan="2">
									<div id="layerHideDadosGerais" style="display:block">
									<table width="100%" border="0" bgcolor="#99CCFF">
										<tr bgcolor="#99CCFF">
											<td align="center"><span class="style2"> <a
												href="javascript:extendeTabela('DadosGerais',true);" /> <b>Dados
											Gerais da Ordem de Serviço</b> </a> </span></td>
										</tr>
									</table>
									</div>

									<div id="layerShowDadosGerais" style="display:none">

									<table width="100%" border="0" bgcolor="#99CCFF">

										<tr bgcolor="#99CCFF">
											<td align="center"><span class="style2"> <a
												href="javascript:extendeTabela('DadosGerais',false);" /> <b>Dados
											Gerais da Ordem de Serviço</b> </a> </span></td>
										</tr>



										<tr bgcolor="#cbe5fe">
											<td>
											<table border="0" width="100%">

												<tr>
													<td height="10" width="30%"><strong>N&uacute;mero do OS:</strong></td>

													<td width="69%"><html:text property="numeroOS"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="9"
														maxlength="9" /> &nbsp;&nbsp;&nbsp;&nbsp; <strong>Situa&ccedil;&atilde;o
													do OS:</strong> &nbsp;&nbsp; <html:text
														property="situacaoOS" readonly="true"
														style="background-color:#EFEFEF; border:0;" size="25"
														maxlength="25" /></td>
												</tr>
												<c:if
													test="${EncerrarOrdemServicoActionForm.numeroRA != null}">
													<tr>
														<td height="10" width="30%"><strong>N&uacute;mero do RA:</strong></td>

														<td width="69%"><html:text property="numeroRA"
															readonly="true"
															style="background-color:#EFEFEF; border:0;" size="9"
															maxlength="9" /> &nbsp;&nbsp;&nbsp;&nbsp; <strong>Situa&ccedil;&atilde;o
														do RA:</strong> &nbsp;&nbsp; <html:text
															property="situacaoRA" readonly="true"
															style="background-color:#EFEFEF; border:0;" size="25"
															maxlength="25" /></td>
													</tr>
												</c:if>

												<c:if
													test="${EncerrarOrdemServicoActionForm.numeroDocumentoCobranca != null}">
													<tr>
														<td width="30%"><strong>N&uacute;mero do Documento de
														Cobran&ccedil;a:</strong></td>
														<td width="69%"><html:text
															property="numeroDocumentoCobranca" readonly="true"
															style="background-color:#EFEFEF; border:0;" size="9"
															maxlength="9" /></td>
													</tr>
												</c:if>

												<tr>
													<td width="30%"><strong>Data da Gera&ccedil;&atilde;o:</strong></td>
													<td width="69%"><html:text property="dataGeracao"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="10"
														maxlength="10" /></td>
												</tr>

												<tr>
													<td height="10" width="30%"><strong>Tipo do Servi&ccedil;o:</strong></td>
													<td width="69%"><html:text property="tipoServicoOSId"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="4"
														maxlength="4" /> <html:text
														property="tipoServicoOSDescricao" readonly="true"
														style="background-color:#EFEFEF; border:0;" size="40"
														maxlength="40" /></td>
												</tr>
												<tr>
													<td height="10" width="30"><strong>Observa&ccedil;&atilde;o:</strong></td>
													<td width="69%"><strong> <html:textarea
														property="observacao" readonly="true"
														style="background-color:#EFEFEF; border:0;" cols="40" />
													</strong></td>
												</tr>

												<tr>
													<td height="10" width="30%"><strong>Valor do Servi&ccedil;o
													Original:</strong></td>

													<td width="69%"><html:text property="valorServicoOriginal"
														readonly="true"
														style="background-color:#EFEFEF; border:0; text-align:right;"
														size="9" /> &nbsp;&nbsp;&nbsp;&nbsp; <strong>Valor do
													Servi&ccedil;o Atual:</strong> &nbsp;&nbsp; <html:text
														property="valorServicoAtual" readonly="true"
														style="background-color:#EFEFEF; border:0; text-align:right;"
														size="9" /></td>
												</tr>

												<tr>
													<td width="30%"><strong>Prioridade Original:</strong></td>
													<td width="69%"><html:text property="prioridadeOriginal"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="48"
														maxlength="48" /></td>
												</tr>

												<tr>
													<td width="30%"><strong>Prioridade Atual:</strong></td>
													<td width="69%"><html:text property="prioridadeAtual"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="48"
														maxlength="48" /></td>
												</tr>


												<tr>
													<td width="30%"><strong>Unidade da Gera&ccedil;&atilde;o da
													OS:</strong></td>
													<td width="69%"><html:text property="unidadeGeracaoId"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="4"
														maxlength="4" /> <html:text
														property="unidadeGeracaoDescricao" readonly="true"
														style="background-color:#EFEFEF; border:0;" size="40"
														maxlength="40" /></td>
												</tr>

												<tr>
													<td width="30%"><strong>Usu&aacute;rio da
													Gera&ccedil;&atilde;o da OS:</strong></td>
													<td width="69%"><html:text property="usuarioGeracaoId"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="4"
														maxlength="4" /> <html:text property="usuarioGeracaoNome"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="40"
														maxlength="40" /></td>
												</tr>

												<tr>
													<td width="30%"><strong>Data da &Uacute;ltima
													Emiss&atilde;o:</strong></td>
													<td width="69%"><html:text property="dataUltimaEmissao"
														readonly="true"
														style="background-color:#EFEFEF; border:0;" size="9" /></td>
												</tr>

											</table>
											</td>
										</tr>
									</table>
									</div>
									</td>
								</tr>
							</table>
							</td>
						</tr>

						<!-- Dados do OS referencia -->
						<logic:notEmpty name="osReferencia">
							<tr>
								<td>
								<div id="layerHideOSReferencia" style="display:block">
								<table width="100%" border="0" bgcolor="#99CCFF">
									<tr bgcolor="#99CCFF">
										<td align="center"><span class="style2"> <a
											href="javascript:extendeTabela('OSReferencia',true);" /> <b>Ordem
										de Serviço de Referência</b> </a> </span></td>
									</tr>
								</table>
								</div>

								<div id="layerShowOSReferencia" style="display:none">

								<table width="100%" border="0" bgcolor="#99CCFF">

									<tr bgcolor="#99CCFF">
										<td align="center"><span class="style2"> <a
											href="javascript:extendeTabela('OSReferencia',false);" /> <b>Ordem
										de Serviço de Referência</b> </a> </span></td>
									</tr>

									<tr bgcolor="#cbe5fe">

										<td>
										<table border="0" width="100%">

											<tr>
												<td width="32%"><strong>Número da OS Referência:</strong></td>

												<td width="67%"><input type="text" name="numeroOSReferencia"
													readonly="true" style="background-color:#EFEFEF; border:0;"
													size="10" maxlength="10" value="${osReferencia.id}" /></td>
											</tr>
											<tr>
												<td width="32%"><strong>Tipo de Serviço da OS Referência:</strong></td>

												<td width="67%"><html:text
													property="tipoServicoReferenciaDescricao" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="40"
													maxlength="40" /></td>
											</tr>
											<tr>
												<td width="32%"><strong>Situação da OS Referência:</strong></td>

												<td width="67%"><input type="text"
													name="situacaoOSReferencia" readonly="true"
													style="background-color:#EFEFEF; border:0;" size="25"
													maxlength="25" value="${osReferencia.descricaoSituacao}" /></td>
											</tr>


										</table>
										</td>
									</tr>

								</table>
								</div>

								</td>
							</tr>
						</logic:notEmpty>
						
						<!-- Dados do Serviço tipo referencia -->
						<logic:notEmpty name="EncerrarOrdemServicoActionForm"
								property="servicoTipoReferenciaOS">
							<tr>
								<td>
								<div id="layerHideServicoTipoReferencia" style="display:block">
								<table width="100%" border="0" bgcolor="#99CCFF">
									<tr bgcolor="#99CCFF">
										<td align="center"><span class="style2"> <a
											href="javascript:extendeTabela('ServicoTipoReferencia',true);" /> <b>Tipo de Serviço de Referência
											</b> </a> </span></td>
									</tr>
								</table>
								</div>

								<div id="layerShowServicoTipoReferencia" style="display:none">

								<table width="100%" border="0" bgcolor="#99CCFF">

									<tr bgcolor="#99CCFF">
										<td align="center"><span class="style2"> <a
											href="javascript:extendeTabela('ServicoTipoReferencia',false);" /> <b>Tipo de Serviço de Referência
											</b> </a> </span></td>
									</tr>

									<tr bgcolor="#cbe5fe">

										<td>
										<table border="0" width="100%">

											<tr>
												<td width="32%"><strong>Tipo de Serviço de Referência:</strong></td>

												<td width="67%"><html:text property="servicoTipoReferenciaOSDescricao"
													readonly="true" style="background-color:#EFEFEF; border:0;"
													size="30" maxlength="30"/></td>
											</tr>

										</table>
										</td>
									</tr>

								</table>
								</div>

								</td>
							</tr>
						</logic:notEmpty>


						<!-- Dados do encerramento da OS -->

						<tr>
							<td>
							<table width="100%" border="0" bgcolor="#99CCFF">
								<tr bgcolor="#99CCFF">
									<td align="center"><b>Dados do Encerramento da Ordem de Serviço</b></td>
								</tr>
								<tr bgcolor="#cbe5fe">

									<td>
									<table border="0" width="100%">
										<tr>
											<td><strong>Data de Execução:<font color="#ff0000">*</font></strong></td>

											<td><html:text property="dataEncerramento" size="10"
												maxlength="10" tabindex="1"onkeyup="mascaraData(this, event);"/>
														<a href="javascript:abrirCalendario('EncerrarOrdemServicoActionForm', 'dataEncerramento');" >
														  <img border="0" src="<bean:message key="caminho.imagens"/>calendario.gif"
															width="20" border="0" align="absmiddle" alt="Exibir Calendário"/></a>
											</td>
										</tr>
										<tr>
											<td><strong>Hora de Execução:<font color="#ff0000">*</font></strong></td>
											<td>
												<html:text property="horaEncerramento" size="5" maxlength="5" tabindex="1" onkeyup="mascaraHora(this, event);"/><strong>(hh:mm)</strong>
											</td>
										</tr>											
										<tr>
											<td width="30%"><strong>Motivo de Encerramento:<font color="#ff0000">*</font></strong></td>

											<td width="69%"><html:select property="idMotivoEncerramento"
												tabindex="2" onchange="carregarCampos();">
												<html:option value="">&nbsp;</html:option>
												<html:options collection="colecaoAtendimentoMotivoEncerrado"
													labelProperty="descricao" property="id" />
											</html:select></td>
										</tr>
										
										<tr>
											<td><strong>Data de Encerramento:</strong></td>
											
											<td><html:text property="tmEncerramento" size="10" readonly="true" disabled="disabled" style="background-color:#EFEFEF; border:0;" maxlength="10" onkeyup="mascaraData(this, event);"/>
											</td>
										</tr>
										
										<!-- 4.5  indicador execução igual a não -->
										<logic:notEmpty name="EncerrarOrdemServicoActionForm"
											property="indicadorExecucao">
											<logic:equal name="EncerrarOrdemServicoActionForm"
												property="indicadorExecucao"
												value="<%=""+AtendimentoMotivoEncerramento.INDICADOR_EXECUCAO_NAO%>">
												<tr>
													<td width="30%"><strong>Parecer do Encerramento:</strong></td>
													<td width="69%"><html:textarea
														property="observacaoEncerramento" cols="50" 
														onkeyup=" validarTamanhoMaximoTextArea(this,200);"/></td>
												</tr>

											</logic:equal>
											<!-- 4.6  indicador execução igual a sim -->
											<logic:equal name="EncerrarOrdemServicoActionForm"
												property="indicadorExecucao"
												value="<%=""+AtendimentoMotivoEncerramento.INDICADOR_EXECUCAO_SIM%>">
												<!-- 4.6.1  id do tipo de serviço referência igual a nulo -->
												<logic:empty name="EncerrarOrdemServicoActionForm"
													property="tipoServicoReferenciaId">
													<tr>
														<td width="30%"><strong>Parecer do Encerramento:</strong></td>
														<td width="69%"><html:textarea
															property="observacaoEncerramento" cols="50"
															onkeyup=" validarTamanhoMaximoTextArea(this,200);" /></td>
													</tr>
													<logic:notEmpty name="EncerrarOrdemServicoActionForm"
														property="indicadorPavimento">
														<logic:notEqual name="EncerrarOrdemServicoActionForm"
														 property="indicadorPavimento" value="<%=""+ServicoTipo.INDICADOR_PAVIMENTO_NAO %>">
													     <tr>
														  <td width="30%"><strong>Pavimento:<font
												             color="#ff0000">*</font></strong></td>
														  <td width="69%"><html:text property="dimensao1" size="6"
															maxlength="5" onkeyup="javascript:formataValorMonetario(this, 5)" />&nbsp;<strong>x</strong>&nbsp;<html:text property="dimensao2" size="6"
															maxlength="5" onkeyup="javascript:formataValorMonetario(this, 5)" />&nbsp;<strong>x</strong>&nbsp;<html:text property="dimensao3" size="6"
															maxlength="5" onkeyup="javascript:formataValorMonetario(this, 5)" />&nbsp;</td>
													     </tr>
													    </logic:notEqual>
													</logic:notEmpty>
												</logic:empty>

												<!-- 4.6.2  id do tipo de serviço referência diferente de nulo -->
												<logic:notEmpty name="EncerrarOrdemServicoActionForm"
													property="tipoServicoReferenciaId">
													<tr>
														<td width="30%"><strong>Parecer do Encerramento:</strong></td>
														<td width="69%"><html:textarea
															property="observacaoEncerramento" cols="50"
															onkeyup=" validarTamanhoMaximoTextArea(this,200);" /></td>
													</tr>
													<tr>
															<td width="30%"><strong>Tipo de Retorno Referida:<font
												                 color="#ff0000">*</font></strong></td>

															<td width="69%"><html:select
																property="idTipoRetornoReferida" tabindex="2" onchange="carregarCamposEncerrarComExecucaoComReferencia();">
																<html:option value="">&nbsp;</html:option>
																<html:options
																	collection="colecaoOSReferidaRetornoTipo"
																	labelProperty="descricao" property="id" />
															</html:select></td>
													</tr>
													<logic:notEmpty name="EncerrarOrdemServicoActionForm" property="indicadorDeferimento">
													  <logic:equal name="EncerrarOrdemServicoActionForm" property="indicadorDeferimento" value="<%=""+OsReferidaRetornoTipo.INDICADOR_DEFERIMENTO_SIM %>">
													      <logic:notEmpty name="EncerrarOrdemServicoActionForm"
													 	   property="indicadorPavimento">
														   <logic:equal name="EncerrarOrdemServicoActionForm"
														     property="indicadorPavimento" value="<%=""+ServicoTipo.INDICADOR_PAVIMENTO_SIM %>">
													        <tr>
															  <td width="30%"><strong>Pavimento:<font
												                 color="#ff0000">*</font></strong></td>
															  <td width="69%"><html:text property="dimensao1" size="6"
																 maxlength="5" onkeyup="javascript:formataValorMonetario(this, 5)"/>&nbsp;<strong>x</strong>&nbsp;<html:text property="dimensao2" size="6"
															maxlength="5" onkeyup="javascript:formataValorMonetario(this, 5)" />&nbsp;<strong>x</strong>&nbsp;<html:text property="dimensao3" size="6"
															maxlength="5" onkeyup="javascript:formataValorMonetario(this, 5)" />&nbsp;</td>
													        </tr>
													      </logic:equal>
													     </logic:notEmpty>
													    </logic:equal> 
													</logic:notEmpty>
													<logic:notEmpty name="EncerrarOrdemServicoActionForm" property="servicoTipoObrigatorio">
													      <logic:equal name="EncerrarOrdemServicoActionForm" property="servicoTipoObrigatorio" value="SIM">
													         <tr>
											                   <td width="30%"><strong>Tipo de Serviço:<font
												                 color="#ff0000">*</font></strong></td>
												               <td width="69%">
												                  <logic:present name="colecaoServicoTipo">  
												                    <html:select property="idServicoTipo"
													                 tabindex="2" >
													                  <html:option value="">&nbsp;</html:option>
													                  <html:options collection="colecaoServicoTipo"
														               labelProperty="descricao" property="id" />
												                      </html:select>
												                   </logic:present>
												                   <logic:notPresent name="colecaoServicoTipo">
												                   <html:text maxlength="4" property="idServicoTipo" size="4" tabindex="7"
																		onkeypress="javascript:validaEnterComMensagem(event, 'exibirEncerrarOrdemServicoPopupAction.do?pesquisaServicoTipo=1', 'idServicoTipo', 'Serviço Tipo');" onkeyup="document.forms[0].descricaoServicoTipo.value='';"/>
																	<a href="javascript:redirecionarSubmit('recuperarPesquisarDadosEncerrarPopupOSAction.do?caminhoRetornoTelaPesquisaTipoServico=exibirEncerrarOrdemServicoPopupAction.do?pesquisaServicoTipo=1');">
																		<img width="23" height="21" border="0" src="<bean:message key="caminho.imagens"/>pesquisa.gif"
																			title="Pesquisar Serviço Tipo" /></a>
																	<logic:present name="idServicoTipoNaoEncontrado">
																		<logic:equal name="idServicoTipoNaoEncontrado" value="exception">
																			<html:text property="descricaoServicoTipo" size="40" maxlength="30" readonly="true"
																				style="background-color:#EFEFEF; border:0; color: #ff0000" />
																		</logic:equal>
																		<logic:notEqual name="idServicoTipoNaoEncontrado" value="exception">
																			<html:text property="descricaoServicoTipo" size="40" maxlength="30" readonly="true"
																				style="background-color:#EFEFEF; border:0; color: #000000" />
																		</logic:notEqual>
																	</logic:present> 
																	<logic:notPresent name="idServicoTipoNaoEncontrado">
																		<logic:empty name="EncerrarOrdemServicoActionForm" property="idServicoTipo">
																			<html:text property="descricaoServicoTipo" value="" size="40" maxlength="30" readonly="true"
																				style="background-color:#EFEFEF; border:0; color: #ff0000" />
																		</logic:empty>
																		<logic:notEmpty name="EncerrarOrdemServicoActionForm" property="idServicoTipo">
																			<html:text property="descricaoServicoTipo" size="40" maxlength="30" readonly="true"
																				style="background-color:#EFEFEF; border:0; color: #000000" />
																		</logic:notEmpty>
																	</logic:notPresent>
																	<a href="javascript:limparPesquisaServicoTipo();">
																		<img src="<bean:message key="caminho.imagens"/>limparcampo.gif"
																		border="0" title="Apagar" /></a>
												                   </logic:notPresent>
												               </td>
												                  
												                    
										                       </tr>
													   
													      </logic:equal>
													</logic:notEmpty>
												</logic:notEmpty>

                                            </logic:equal>
										</logic:notEmpty>
										<logic:notEmpty name="EncerrarOrdemServicoActionForm" property="indicadorVistoriaServicoTipo">
										 <logic:equal name="EncerrarOrdemServicoActionForm" property="indicadorVistoriaServicoTipo" value="<%=""+ServicoTipo.INDICADOR_VISTORIA_SIM%>">
										  <tr>
											<td width="30%"><strong>Retorno Vistoria:<font
												color="#ff0000">*</font></strong></td>

											<td width="69%"><html:radio property="codigoRetornoVistoriaOs" value="1"/><strong>Deferido </strong>&nbsp;&nbsp;
											                <html:radio property="codigoRetornoVistoriaOs" value="2"/><strong>Indeferido </strong>
											</td>
										  </tr>
										 </logic:equal> 
										</logic:notEmpty>
									</table>
									</td>
								</tr>


							</table>


							</td>

						</tr>



					</table>

					</td>
				</tr>

				<tr>
					<td>
					<table width="100%">
						<tr>
							<td>
							<div><input name="ButtonVoltar" type="button"
								class="bottonRightCol" value="Voltar"
								onclick="javascript:history.back();"></div>
							</td>
							
							<td>
							<c:choose>
				        		<c:when test="${empty processoAutorizacaoServicosAssociados}">
									<div align="right">
									<table>
									<tr>
									 <td>
									   <input name="ButtonOSFiscalizacao" type="button"
												class="bottonRightCol" value="Gerar OS Fiscalização"
												onclick="javascript:informarOSFiscalizacao();">
									</td>
									<logic:notEmpty name="EncerrarOrdemServicoActionForm"
											property="indicadorExecucao">
										<logic:equal name="EncerrarOrdemServicoActionForm"
											property="indicadorExecucao"
											value="<%=""+AtendimentoMotivoEncerramento.INDICADOR_EXECUCAO_SIM%>">
											<td><input name="ButtonAtividade" type="button"
												class="bottonRightCol" value="Informar Atividades"
												onclick="javascript:informarAtividade();">
											<td>	
												<input name="ButtonEncerrar" type="button"
										         class="bottonRightCol" value="Encerrar"
										         onclick="javascript:validarForm(document.forms[0]);">
											</td>
										</logic:equal>	
										<logic:notEqual name="EncerrarOrdemServicoActionForm"
											property="indicadorExecucao"
											value="<%=""+AtendimentoMotivoEncerramento.INDICADOR_EXECUCAO_SIM%>">
											<td><input name="ButtonEncerrar" type="button"
										class="bottonRightCol" value="Encerrar"
										onclick="javascript:validarForm(document.forms[0]);"></td>
										</logic:notEqual>
									</logic:notEmpty>
									<logic:empty name="EncerrarOrdemServicoActionForm"
											property="indicadorExecucao">
									<td><input name="ButtonEncerrar" type="button"
										class="bottonRightCol" value="Encerrar"
										onclick="javascript:validarForm(document.forms[0]);"></td>
									</logic:empty>
								  </tr>	
								</table>	
								</div>
							</c:when>
							<c:otherwise>
								<div align="right">
									<input name="ButtonEncerrar" type="button" class="bottonRightCol" value="Encerrar"
										onclick="retornarDadosEncerramento(${idServicoTipo});">
								</div>
							</c:otherwise>
						
						</c:choose>
					   </td>
					  </tr>
					</table>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<!-- Fim do Corpo - Sávio Luiz -->
</html:form>
</body>
</html:html>
